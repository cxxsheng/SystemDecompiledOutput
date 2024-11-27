package com.android.server.hdmi;

/* loaded from: classes2.dex */
abstract class HdmiCecLocalDevice extends com.android.server.hdmi.HdmiLocalDevice {
    private static final int FOLLOWER_SAFETY_TIMEOUT = 550;
    private static final int MAX_HDMI_ACTIVE_SOURCE_HISTORY = 10;
    private static final int MSG_DISABLE_DEVICE_TIMEOUT = 1;
    private static final int MSG_USER_CONTROL_RELEASE_TIMEOUT = 2;
    private static final java.lang.String TAG = "HdmiCecLocalDevice";

    @com.android.internal.annotations.VisibleForTesting
    final java.util.ArrayList<com.android.server.hdmi.HdmiCecFeatureAction> mActions;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mActiveRoutingPath;
    private final java.util.concurrent.ArrayBlockingQueue<com.android.server.hdmi.HdmiCecController.Dumpable> mActiveSourceHistory;
    protected final com.android.server.hdmi.HdmiCecMessageCache mCecMessageCache;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.hdmi.HdmiDeviceInfo mDeviceInfo;
    private final android.os.Handler mHandler;
    protected int mLastKeyRepeatCount;
    protected int mLastKeycode;
    protected com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback mPendingActionClearedCallback;
    protected int mPreferredAddress;
    com.android.server.hdmi.HdmiCecStandbyModeHandler mStandbyHandler;

    interface PendingActionClearedCallback {
        void onCleared(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice);
    }

    interface StandbyCompletedCallback {
        void onStandbyCompleted();
    }

    protected abstract int getPreferredAddress();

    protected abstract java.util.List<java.lang.Integer> getRcFeatures();

    @com.android.server.hdmi.Constants.RcProfile
    protected abstract int getRcProfile();

    protected abstract void onAddressAllocated(int i, int i2);

    protected abstract void setPreferredAddress(int i);

    static class ActiveSource {
        int logicalAddress;
        int physicalAddress;

        public ActiveSource() {
            invalidate();
        }

        public ActiveSource(int i, int i2) {
            this.logicalAddress = i;
            this.physicalAddress = i2;
        }

        public static com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource of(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource) {
            return new com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource(activeSource.logicalAddress, activeSource.physicalAddress);
        }

        public static com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource of(int i, int i2) {
            return new com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource(i, i2);
        }

        public boolean isValid() {
            return com.android.server.hdmi.HdmiUtils.isValidAddress(this.logicalAddress);
        }

        public void invalidate() {
            this.logicalAddress = -1;
            this.physicalAddress = com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL;
        }

        public boolean equals(int i, int i2) {
            return this.logicalAddress == i && this.physicalAddress == i2;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource)) {
                return false;
            }
            com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource = (com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource) obj;
            return activeSource.logicalAddress == this.logicalAddress && activeSource.physicalAddress == this.physicalAddress;
        }

        public int hashCode() {
            return (this.logicalAddress * 29) + this.physicalAddress;
        }

        public java.lang.String toString() {
            java.lang.String format;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            if (this.logicalAddress == -1) {
                format = "invalid";
            } else {
                format = java.lang.String.format("0x%02x", java.lang.Integer.valueOf(this.logicalAddress));
            }
            sb.append("(");
            sb.append(format);
            java.lang.String format2 = this.physicalAddress != 65535 ? java.lang.String.format("0x%04x", java.lang.Integer.valueOf(this.physicalAddress)) : "invalid";
            sb.append(", ");
            sb.append(format2);
            sb.append(")");
            return sb.toString();
        }
    }

    protected HdmiCecLocalDevice(com.android.server.hdmi.HdmiControlService hdmiControlService, int i) {
        super(hdmiControlService, i);
        this.mLastKeycode = -1;
        this.mLastKeyRepeatCount = 0;
        this.mActiveSourceHistory = new java.util.concurrent.ArrayBlockingQueue<>(10);
        this.mCecMessageCache = new com.android.server.hdmi.HdmiCecMessageCache();
        this.mActions = new java.util.ArrayList<>();
        this.mHandler = new android.os.Handler() { // from class: com.android.server.hdmi.HdmiCecLocalDevice.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.server.hdmi.HdmiCecLocalDevice.this.handleDisableDeviceTimeout();
                        break;
                    case 2:
                        com.android.server.hdmi.HdmiCecLocalDevice.this.handleUserControlReleased();
                        break;
                }
            }
        };
    }

    static com.android.server.hdmi.HdmiCecLocalDevice create(com.android.server.hdmi.HdmiControlService hdmiControlService, int i) {
        switch (i) {
            case 0:
                return new com.android.server.hdmi.HdmiCecLocalDeviceTv(hdmiControlService);
            case 4:
                return new com.android.server.hdmi.HdmiCecLocalDevicePlayback(hdmiControlService);
            case 5:
                return new com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem(hdmiControlService);
            default:
                return null;
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void init() {
        assertRunOnServiceThread();
        this.mPreferredAddress = getPreferredAddress();
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
            handleDisableDeviceTimeout();
        }
        this.mPendingActionClearedCallback = null;
    }

    protected boolean isInputReady(int i) {
        return true;
    }

    protected boolean canGoToStandby() {
        return true;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected int dispatchMessage(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int destination = hdmiCecMessage.getDestination();
        if (destination != this.mDeviceInfo.getLogicalAddress() && destination != 15) {
            return -2;
        }
        if (this.mService.isPowerStandby() && !this.mService.isWakeUpMessageReceived() && this.mStandbyHandler.handleCommand(hdmiCecMessage)) {
            return -1;
        }
        this.mCecMessageCache.cacheMessage(hdmiCecMessage);
        return onMessage(hdmiCecMessage);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected boolean isAlreadyActiveSource(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource = getActiveSource();
        if (hdmiDeviceInfo.getDevicePowerStatus() != 0 || !activeSource.isValid() || i != activeSource.logicalAddress) {
            return false;
        }
        invokeCallback(iHdmiControlCallback, 0);
        return true;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void clearDeviceInfoList() {
        assertRunOnServiceThread();
        this.mService.getHdmiCecNetwork().clearDeviceList();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected final int onMessage(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (dispatchMessageToAction(hdmiCecMessage)) {
            return -1;
        }
        if (hdmiCecMessage instanceof com.android.server.hdmi.SetAudioVolumeLevelMessage) {
            return handleSetAudioVolumeLevel((com.android.server.hdmi.SetAudioVolumeLevelMessage) hdmiCecMessage);
        }
        switch (hdmiCecMessage.getOpcode()) {
            case 4:
                return handleImageViewOn(hdmiCecMessage);
            case 10:
                return handleRecordStatus(hdmiCecMessage);
            case 13:
                return handleTextViewOn(hdmiCecMessage);
            case 15:
                return handleRecordTvScreen(hdmiCecMessage);
            case 50:
                return handleSetMenuLanguage(hdmiCecMessage);
            case 53:
                return handleTimerStatus(hdmiCecMessage);
            case 54:
                return handleStandby(hdmiCecMessage);
            case 67:
                return handleTimerClearedStatus(hdmiCecMessage);
            case 68:
                return handleUserControlPressed(hdmiCecMessage);
            case 69:
                return handleUserControlReleased();
            case 70:
                return handleGiveOsdName(hdmiCecMessage);
            case 71:
                return handleSetOsdName(hdmiCecMessage);
            case 112:
                return handleSystemAudioModeRequest(hdmiCecMessage);
            case 113:
                return handleGiveAudioStatus(hdmiCecMessage);
            case 114:
                return handleSetSystemAudioMode(hdmiCecMessage);
            case 122:
                return handleReportAudioStatus(hdmiCecMessage);
            case 125:
                return handleGiveSystemAudioModeStatus(hdmiCecMessage);
            case 126:
                return handleSystemAudioModeStatus(hdmiCecMessage);
            case 128:
                return handleRoutingChange(hdmiCecMessage);
            case 129:
                return handleRoutingInformation(hdmiCecMessage);
            case 130:
                return handleActiveSource(hdmiCecMessage);
            case 131:
                return handleGivePhysicalAddress(hdmiCecMessage);
            case 132:
                return handleReportPhysicalAddress(hdmiCecMessage);
            case 133:
                return handleRequestActiveSource(hdmiCecMessage);
            case 134:
                return handleSetStreamPath(hdmiCecMessage);
            case 137:
                return handleVendorCommand(hdmiCecMessage);
            case 140:
                return handleGiveDeviceVendorId(hdmiCecMessage);
            case 141:
                return handleMenuRequest(hdmiCecMessage);
            case 142:
                return handleMenuStatus(hdmiCecMessage);
            case 143:
                return handleGiveDevicePowerStatus(hdmiCecMessage);
            case 144:
                return handleReportPowerStatus(hdmiCecMessage);
            case 145:
                return handleGetMenuLanguage(hdmiCecMessage);
            case 157:
                return handleInactiveSource(hdmiCecMessage);
            case 158:
                return handleCecVersion();
            case 159:
                return handleGetCecVersion(hdmiCecMessage);
            case 160:
                return handleVendorCommandWithId(hdmiCecMessage);
            case 163:
                return handleReportShortAudioDescriptor(hdmiCecMessage);
            case 164:
                return handleRequestShortAudioDescriptor(hdmiCecMessage);
            case 165:
                return handleGiveFeatures(hdmiCecMessage);
            case 192:
                return handleInitiateArc(hdmiCecMessage);
            case 193:
                return handleReportArcInitiate(hdmiCecMessage);
            case 194:
                return handleReportArcTermination(hdmiCecMessage);
            case 195:
                return handleRequestArcInitiate(hdmiCecMessage);
            case 196:
                return handleRequestArcTermination(hdmiCecMessage);
            case 197:
                return handleTerminateArc(hdmiCecMessage);
            default:
                return -2;
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private boolean dispatchMessageToAction(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        java.util.Iterator it = new java.util.ArrayList(this.mActions).iterator();
        boolean z = false;
        while (it.hasNext()) {
            z = z || ((com.android.server.hdmi.HdmiCecFeatureAction) it.next()).processCommand(hdmiCecMessage);
        }
        return z;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleGivePhysicalAddress(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int physicalAddress = this.mService.getPhysicalAddress();
        if (physicalAddress == 65535) {
            this.mService.maySendFeatureAbortCommand(hdmiCecMessage, 5);
            return -1;
        }
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportPhysicalAddressCommand(this.mDeviceInfo.getLogicalAddress(), physicalAddress, this.mDeviceType));
        return -1;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleGiveDeviceVendorId(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int vendorId = this.mService.getVendorId();
        if (vendorId == 1) {
            this.mService.maySendFeatureAbortCommand(hdmiCecMessage, 5);
            return -1;
        }
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildDeviceVendorIdCommand(this.mDeviceInfo.getLogicalAddress(), vendorId));
        return -1;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleGetCecVersion(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildCecVersion(hdmiCecMessage.getDestination(), hdmiCecMessage.getSource(), this.mService.getCecVersion()));
        return -1;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleCecVersion() {
        assertRunOnServiceThread();
        return -1;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleActiveSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleInactiveSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRequestActiveSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleGetMenuLanguage(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        android.util.Slog.w(TAG, "Only TV can handle <Get Menu Language>:" + hdmiCecMessage.toString());
        return -2;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleSetMenuLanguage(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        android.util.Slog.w(TAG, "Only Playback device can handle <Set Menu Language>:" + hdmiCecMessage.toString());
        return -2;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleGiveOsdName(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        buildAndSendSetOsdName(hdmiCecMessage.getSource());
        return -1;
    }

    protected void buildAndSendSetOsdName(int i) {
        final com.android.server.hdmi.HdmiCecMessage buildSetOsdNameCommand = com.android.server.hdmi.HdmiCecMessageBuilder.buildSetOsdNameCommand(this.mDeviceInfo.getLogicalAddress(), i, this.mDeviceInfo.getDisplayName());
        if (buildSetOsdNameCommand != null) {
            this.mService.sendCecCommand(buildSetOsdNameCommand, new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDevice.2
                @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                public void onSendCompleted(int i2) {
                    if (i2 != 0) {
                        com.android.server.hdmi.HdmiLogger.debug("Failed to send cec command " + buildSetOsdNameCommand, new java.lang.Object[0]);
                    }
                }
            });
            return;
        }
        android.util.Slog.w(TAG, "Failed to build <Get Osd Name>:" + this.mDeviceInfo.getDisplayName());
    }

    protected int handleRoutingChange(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleRoutingInformation(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleReportPhysicalAddress(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        int source = hdmiCecMessage.getSource();
        if (hasAction(com.android.server.hdmi.DeviceDiscoveryAction.class)) {
            android.util.Slog.i(TAG, "Ignored while Device Discovery Action is in progress: " + hdmiCecMessage);
            return -1;
        }
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = this.mService.getHdmiCecNetwork().getCecDeviceInfo(source);
        if (!this.mService.isTvDevice() && cecDeviceInfo != null && cecDeviceInfo.getDisplayName().equals(com.android.server.hdmi.HdmiUtils.getDefaultDeviceName(source))) {
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveOsdNameCommand(this.mDeviceInfo.getLogicalAddress(), source));
        }
        return -1;
    }

    protected int handleSystemAudioModeStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleGiveSystemAudioModeStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleSetSystemAudioMode(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleSystemAudioModeRequest(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleTerminateArc(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleInitiateArc(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleRequestArcInitiate(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleRequestArcTermination(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleReportArcInitiate(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleReportArcTermination(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleReportAudioStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleGiveAudioStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleRequestShortAudioDescriptor(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleReportShortAudioDescriptor(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleSetAudioVolumeLevel(com.android.server.hdmi.SetAudioVolumeLevelMessage setAudioVolumeLevelMessage) {
        return -2;
    }

    protected void preprocessBufferedMessages(java.util.List<com.android.server.hdmi.HdmiCecMessage> list) {
    }

    protected android.hardware.hdmi.DeviceFeatures computeDeviceFeatures() {
        return android.hardware.hdmi.DeviceFeatures.NO_FEATURES_SUPPORTED;
    }

    private void updateDeviceFeatures() {
        setDeviceInfo(getDeviceInfo().toBuilder().setDeviceFeatures(computeDeviceFeatures()).build());
    }

    protected final android.hardware.hdmi.DeviceFeatures getDeviceFeatures() {
        updateDeviceFeatures();
        return getDeviceInfo().getDeviceFeatures();
    }

    protected int handleGiveFeatures(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mService.getCecVersion() < 6) {
            return 0;
        }
        reportFeatures();
        return -1;
    }

    protected void reportFeatures() {
        int logicalAddress;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = this.mService.getAllCecLocalDevices().iterator();
        while (it.hasNext()) {
            arrayList.add(java.lang.Integer.valueOf(it.next().mDeviceType));
        }
        int rcProfile = getRcProfile();
        java.util.List<java.lang.Integer> rcFeatures = getRcFeatures();
        android.hardware.hdmi.DeviceFeatures deviceFeatures = getDeviceFeatures();
        synchronized (this.mLock) {
            logicalAddress = this.mDeviceInfo.getLogicalAddress();
        }
        this.mService.sendCecCommand(com.android.server.hdmi.ReportFeaturesMessage.build(logicalAddress, this.mService.getCecVersion(), arrayList, rcProfile, rcFeatures, deviceFeatures));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleStandby(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (this.mService.isCecControlEnabled() && !this.mService.isProhibitMode() && this.mService.isPowerOnOrTransient()) {
            this.mService.standby();
            return -1;
        }
        return 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0087  */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected int handleUserControlPressed(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        int i;
        assertRunOnServiceThread();
        this.mHandler.removeMessages(2);
        if (this.mService.isPowerOnOrTransient() && isPowerOffOrToggleCommand(hdmiCecMessage)) {
            this.mService.standby();
            return -1;
        }
        if (this.mService.isPowerStandbyOrTransient() && isPowerOnOrToggleCommand(hdmiCecMessage)) {
            this.mService.wakeUp();
            return -1;
        }
        if (this.mService.getHdmiCecVolumeControl() == 0 && isVolumeOrMuteCommand(hdmiCecMessage)) {
            return 4;
        }
        if (isPowerOffOrToggleCommand(hdmiCecMessage) || isPowerOnOrToggleCommand(hdmiCecMessage)) {
            return -1;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        byte[] params = hdmiCecMessage.getParams();
        int cecKeycodeAndParamsToAndroidKey = com.android.server.hdmi.HdmiCecKeycode.cecKeycodeAndParamsToAndroidKey(params);
        if (this.mLastKeycode != -1) {
            if (cecKeycodeAndParamsToAndroidKey == this.mLastKeycode) {
                i = this.mLastKeyRepeatCount + 1;
                this.mLastKeycode = cecKeycodeAndParamsToAndroidKey;
                this.mLastKeyRepeatCount = i;
                if (cecKeycodeAndParamsToAndroidKey == -1) {
                    injectKeyEvent(uptimeMillis, 0, cecKeycodeAndParamsToAndroidKey, i);
                    this.mHandler.sendMessageDelayed(android.os.Message.obtain(this.mHandler, 2), 550L);
                    return -1;
                }
                if (params.length > 0) {
                    return handleUnmappedCecKeycode(params[0]);
                }
                return 3;
            }
            injectKeyEvent(uptimeMillis, 1, this.mLastKeycode, 0);
        }
        i = 0;
        this.mLastKeycode = cecKeycodeAndParamsToAndroidKey;
        this.mLastKeyRepeatCount = i;
        if (cecKeycodeAndParamsToAndroidKey == -1) {
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleUnmappedCecKeycode(int i) {
        if (i == 101) {
            this.mService.getAudioManager().adjustStreamVolume(3, -100, 1);
            return -1;
        }
        if (i != 102) {
            return 3;
        }
        this.mService.getAudioManager().adjustStreamVolume(3, 100, 1);
        return -1;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleUserControlReleased() {
        assertRunOnServiceThread();
        this.mHandler.removeMessages(2);
        this.mLastKeyRepeatCount = 0;
        if (this.mLastKeycode != -1) {
            injectKeyEvent(android.os.SystemClock.uptimeMillis(), 1, this.mLastKeycode, 0);
            this.mLastKeycode = -1;
        }
        return -1;
    }

    static void injectKeyEvent(long j, int i, int i2, int i3) {
        android.view.KeyEvent obtain = android.view.KeyEvent.obtain(j, j, i, i2, i3, 0, -1, 0, 8, 33554433, null);
        android.hardware.input.InputManagerGlobal.getInstance().injectInputEvent(obtain, 0);
        obtain.recycle();
    }

    static boolean isPowerOnOrToggleCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        byte[] params = hdmiCecMessage.getParams();
        if (hdmiCecMessage.getOpcode() == 68) {
            return params[0] == 64 || params[0] == 109 || params[0] == 107;
        }
        return false;
    }

    static boolean isPowerOffOrToggleCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        byte[] params = hdmiCecMessage.getParams();
        if (hdmiCecMessage.getOpcode() == 68) {
            return params[0] == 108 || params[0] == 107;
        }
        return false;
    }

    static boolean isVolumeOrMuteCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        byte[] params = hdmiCecMessage.getParams();
        if (hdmiCecMessage.getOpcode() == 68) {
            return params[0] == 66 || params[0] == 65 || params[0] == 67 || params[0] == 101 || params[0] == 102;
        }
        return false;
    }

    protected int handleTextViewOn(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleImageViewOn(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleSetStreamPath(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleGiveDevicePowerStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportPowerStatus(this.mDeviceInfo.getLogicalAddress(), hdmiCecMessage.getSource(), this.mService.getPowerStatus()));
        return -1;
    }

    protected int handleMenuRequest(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportMenuStatus(this.mDeviceInfo.getLogicalAddress(), hdmiCecMessage.getSource(), 0));
        return -1;
    }

    protected int handleMenuStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleVendorCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (!this.mService.invokeVendorCommandListenersOnReceived(this.mDeviceType, hdmiCecMessage.getSource(), hdmiCecMessage.getDestination(), hdmiCecMessage.getParams(), false)) {
            return 4;
        }
        return -1;
    }

    protected int handleVendorCommandWithId(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        byte[] params = hdmiCecMessage.getParams();
        com.android.server.hdmi.HdmiUtils.threeBytesToInt(params);
        if (hdmiCecMessage.getDestination() == 15 || hdmiCecMessage.getSource() == 15) {
            android.util.Slog.v(TAG, "Wrong broadcast vendor command. Ignoring");
            return -1;
        }
        if (!this.mService.invokeVendorCommandListenersOnReceived(this.mDeviceType, hdmiCecMessage.getSource(), hdmiCecMessage.getDestination(), params, true)) {
            return 4;
        }
        return -1;
    }

    protected void sendStandby(int i) {
    }

    protected int handleSetOsdName(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -1;
    }

    protected int handleRecordTvScreen(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleTimerClearedStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleReportPowerStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -1;
    }

    protected int handleTimerStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    protected int handleRecordStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    final void handleAddressAllocated(int i, java.util.List<com.android.server.hdmi.HdmiCecMessage> list, int i2) {
        assertRunOnServiceThread();
        preprocessBufferedMessages(list);
        this.mPreferredAddress = i;
        updateDeviceFeatures();
        if (this.mService.getCecVersion() >= 6) {
            reportFeatures();
        }
        onAddressAllocated(i, i2);
        setPreferredAddress(i);
    }

    int getType() {
        return this.mDeviceType;
    }

    android.hardware.hdmi.HdmiDeviceInfo getDeviceInfo() {
        android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo;
        synchronized (this.mLock) {
            hdmiDeviceInfo = this.mDeviceInfo;
        }
        return hdmiDeviceInfo;
    }

    void setDeviceInfo(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        synchronized (this.mLock) {
            this.mDeviceInfo = hdmiDeviceInfo;
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean isAddressOf(int i) {
        assertRunOnServiceThread();
        return i == this.mDeviceInfo.getLogicalAddress();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void addAndStartAction(com.android.server.hdmi.HdmiCecFeatureAction hdmiCecFeatureAction) {
        assertRunOnServiceThread();
        this.mActions.add(hdmiCecFeatureAction);
        if (this.mService.isPowerStandby() || !this.mService.isAddressAllocated()) {
            if (hdmiCecFeatureAction.getClass() == com.android.server.hdmi.ResendCecCommandAction.class) {
                android.util.Slog.i(TAG, "Not ready to start ResendCecCommandAction. This action is cancelled.");
                removeAction(hdmiCecFeatureAction);
                return;
            } else {
                android.util.Slog.i(TAG, "Not ready to start action. Queued for deferred start:" + hdmiCecFeatureAction);
                return;
            }
        }
        hdmiCecFeatureAction.start();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void startNewAvbAudioStatusAction(int i) {
        assertRunOnServiceThread();
        removeAction(com.android.server.hdmi.AbsoluteVolumeAudioStatusAction.class);
        addAndStartAction(new com.android.server.hdmi.AbsoluteVolumeAudioStatusAction(this, i));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void removeAvbAudioStatusAction() {
        assertRunOnServiceThread();
        removeAction(com.android.server.hdmi.AbsoluteVolumeAudioStatusAction.class);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void updateAvbVolume(int i) {
        assertRunOnServiceThread();
        java.util.Iterator it = getActions(com.android.server.hdmi.AbsoluteVolumeAudioStatusAction.class).iterator();
        while (it.hasNext()) {
            ((com.android.server.hdmi.AbsoluteVolumeAudioStatusAction) it.next()).updateVolume(i);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void requestAndUpdateAvbAudioStatus() {
        assertRunOnServiceThread();
        java.util.Iterator it = getActions(com.android.server.hdmi.AbsoluteVolumeAudioStatusAction.class).iterator();
        while (it.hasNext()) {
            ((com.android.server.hdmi.AbsoluteVolumeAudioStatusAction) it.next()).requestAndUpdateAudioStatus();
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void querySetAudioVolumeLevelSupport(final int i) {
        assertRunOnServiceThread();
        if (this.mService.getCecVersion() >= 6) {
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveFeatures(getDeviceInfo().getLogicalAddress(), i));
        }
        if (getActions(com.android.server.hdmi.SetAudioVolumeLevelDiscoveryAction.class).stream().noneMatch(new java.util.function.Predicate() { // from class: com.android.server.hdmi.HdmiCecLocalDevice$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$querySetAudioVolumeLevelSupport$0;
                lambda$querySetAudioVolumeLevelSupport$0 = com.android.server.hdmi.HdmiCecLocalDevice.lambda$querySetAudioVolumeLevelSupport$0(i, (com.android.server.hdmi.SetAudioVolumeLevelDiscoveryAction) obj);
                return lambda$querySetAudioVolumeLevelSupport$0;
            }
        })) {
            addAndStartAction(new com.android.server.hdmi.SetAudioVolumeLevelDiscoveryAction(this, i, new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: com.android.server.hdmi.HdmiCecLocalDevice.3
                public void onComplete(int i2) {
                    if (i2 == 0) {
                        com.android.server.hdmi.HdmiCecLocalDevice.this.getService().checkAndUpdateAbsoluteVolumeBehavior();
                    }
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$querySetAudioVolumeLevelSupport$0(int i, com.android.server.hdmi.SetAudioVolumeLevelDiscoveryAction setAudioVolumeLevelDiscoveryAction) {
        return setAudioVolumeLevelDiscoveryAction.getTargetAddress() == i;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void startQueuedActions() {
        assertRunOnServiceThread();
        java.util.Iterator it = new java.util.ArrayList(this.mActions).iterator();
        while (it.hasNext()) {
            com.android.server.hdmi.HdmiCecFeatureAction hdmiCecFeatureAction = (com.android.server.hdmi.HdmiCecFeatureAction) it.next();
            if (!hdmiCecFeatureAction.started()) {
                android.util.Slog.i(TAG, "Starting queued action:" + hdmiCecFeatureAction);
                hdmiCecFeatureAction.start();
            }
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    <T extends com.android.server.hdmi.HdmiCecFeatureAction> boolean hasAction(java.lang.Class<T> cls) {
        assertRunOnServiceThread();
        java.util.Iterator<com.android.server.hdmi.HdmiCecFeatureAction> it = this.mActions.iterator();
        while (it.hasNext()) {
            if (it.next().getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    <T extends com.android.server.hdmi.HdmiCecFeatureAction> java.util.List<T> getActions(java.lang.Class<T> cls) {
        assertRunOnServiceThread();
        java.util.ArrayList arrayList = (java.util.List<T>) java.util.Collections.emptyList();
        java.util.Iterator<com.android.server.hdmi.HdmiCecFeatureAction> it = this.mActions.iterator();
        while (it.hasNext()) {
            com.android.server.hdmi.HdmiCecFeatureAction next = it.next();
            if (next.getClass().equals(cls)) {
                if (arrayList.isEmpty()) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.add(next);
            }
        }
        return (java.util.List<T>) arrayList;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void removeAction(com.android.server.hdmi.HdmiCecFeatureAction hdmiCecFeatureAction) {
        assertRunOnServiceThread();
        hdmiCecFeatureAction.finish(false);
        this.mActions.remove(hdmiCecFeatureAction);
        checkIfPendingActionsCleared();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    <T extends com.android.server.hdmi.HdmiCecFeatureAction> void removeAction(java.lang.Class<T> cls) {
        assertRunOnServiceThread();
        removeActionExcept(cls, null);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void removeAllActions() {
        assertRunOnServiceThread();
        java.util.Iterator<com.android.server.hdmi.HdmiCecFeatureAction> it = this.mActions.iterator();
        while (it.hasNext()) {
            it.next().finish(false);
        }
        this.mActions.clear();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    <T extends com.android.server.hdmi.HdmiCecFeatureAction> void removeActionExcept(java.lang.Class<T> cls, com.android.server.hdmi.HdmiCecFeatureAction hdmiCecFeatureAction) {
        assertRunOnServiceThread();
        java.util.Iterator<com.android.server.hdmi.HdmiCecFeatureAction> it = this.mActions.iterator();
        while (it.hasNext()) {
            com.android.server.hdmi.HdmiCecFeatureAction next = it.next();
            if (next != hdmiCecFeatureAction && next.getClass().equals(cls)) {
                next.finish(false);
                it.remove();
            }
        }
        checkIfPendingActionsCleared();
    }

    protected void checkIfPendingActionsCleared() {
        if (this.mActions.isEmpty() && this.mPendingActionClearedCallback != null) {
            com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback = this.mPendingActionClearedCallback;
            this.mPendingActionClearedCallback = null;
            pendingActionClearedCallback.onCleared(this);
        }
    }

    protected void assertRunOnServiceThread() {
        if (android.os.Looper.myLooper() != this.mService.getServiceLooper()) {
            throw new java.lang.IllegalStateException("Should run on service thread.");
        }
    }

    void onHotplug(int i, boolean z) {
    }

    final com.android.server.hdmi.HdmiControlService getService() {
        return this.mService;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    final boolean isConnectedToArcPort(int i) {
        assertRunOnServiceThread();
        return this.mService.isConnectedToArcPort(i);
    }

    com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource getActiveSource() {
        return this.mService.getLocalActiveSource();
    }

    void setActiveSource(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource, java.lang.String str) {
        setActiveSource(activeSource.logicalAddress, activeSource.physicalAddress, str);
    }

    void setActiveSource(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, java.lang.String str) {
        setActiveSource(hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getPhysicalAddress(), str);
    }

    void setActiveSource(int i, int i2, java.lang.String str) {
        this.mService.setActiveSource(i, i2, str);
        this.mService.setLastInputForMhl(-1);
    }

    int getActivePath() {
        int i;
        synchronized (this.mLock) {
            i = this.mActiveRoutingPath;
        }
        return i;
    }

    void setActivePath(int i) {
        synchronized (this.mLock) {
            this.mActiveRoutingPath = i;
        }
        this.mService.setActivePortId(pathToPortId(i));
    }

    int getActivePortId() {
        int pathToPortId;
        synchronized (this.mLock) {
            pathToPortId = this.mService.pathToPortId(this.mActiveRoutingPath);
        }
        return pathToPortId;
    }

    void setActivePortId(int i) {
        setActivePath(this.mService.portIdToPath(i));
    }

    int getPortId(int i) {
        return this.mService.pathToPortId(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    com.android.server.hdmi.HdmiCecMessageCache getCecMessageCache() {
        assertRunOnServiceThread();
        return this.mCecMessageCache;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    int pathToPortId(int i) {
        assertRunOnServiceThread();
        return this.mService.pathToPortId(i);
    }

    protected void onStandby(boolean z, int i, com.android.server.hdmi.HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback) {
    }

    protected void onStandby(boolean z, int i) {
        onStandby(z, i, null);
    }

    protected void onInitializeCecComplete(int i) {
    }

    protected void disableDevice(boolean z, final com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback) {
        removeAction(com.android.server.hdmi.SetAudioVolumeLevelDiscoveryAction.class);
        removeAction(com.android.server.hdmi.ActiveSourceAction.class);
        removeAction(com.android.server.hdmi.ResendCecCommandAction.class);
        this.mPendingActionClearedCallback = new com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDevice.4
            @Override // com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback
            public void onCleared(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
                com.android.server.hdmi.HdmiCecLocalDevice.this.mHandler.removeMessages(1);
                pendingActionClearedCallback.onCleared(hdmiCecLocalDevice);
            }
        };
        this.mHandler.sendMessageDelayed(android.os.Message.obtain(this.mHandler, 1), 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void handleDisableDeviceTimeout() {
        assertRunOnServiceThread();
        java.util.Iterator<com.android.server.hdmi.HdmiCecFeatureAction> it = this.mActions.iterator();
        while (it.hasNext()) {
            it.next().finish(false);
            it.remove();
        }
        if (this.mPendingActionClearedCallback != null) {
            this.mPendingActionClearedCallback.onCleared(this);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void sendKeyEvent(int i, boolean z) {
        assertRunOnServiceThread();
        if (!com.android.server.hdmi.HdmiCecKeycode.isSupportedKeycode(i)) {
            android.util.Slog.w(TAG, "Unsupported key: " + i);
            return;
        }
        java.util.List actions = getActions(com.android.server.hdmi.SendKeyAction.class);
        int findKeyReceiverAddress = findKeyReceiverAddress();
        if (findKeyReceiverAddress == -1 || findKeyReceiverAddress == this.mDeviceInfo.getLogicalAddress()) {
            android.util.Slog.w(TAG, "Discard key event: " + i + ", pressed:" + z + ", receiverAddr=" + findKeyReceiverAddress);
            return;
        }
        if (!actions.isEmpty()) {
            ((com.android.server.hdmi.SendKeyAction) actions.get(0)).processKeyEvent(i, z);
        } else if (z) {
            addAndStartAction(new com.android.server.hdmi.SendKeyAction(this, findKeyReceiverAddress, i));
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void sendVolumeKeyEvent(int i, boolean z) {
        assertRunOnServiceThread();
        if (this.mService.getHdmiCecVolumeControl() == 0) {
            return;
        }
        if (!com.android.server.hdmi.HdmiCecKeycode.isVolumeKeycode(i)) {
            android.util.Slog.w(TAG, "Not a volume key: " + i);
            return;
        }
        java.util.List actions = getActions(com.android.server.hdmi.SendKeyAction.class);
        final int findAudioReceiverAddress = findAudioReceiverAddress();
        if (findAudioReceiverAddress == -1 || this.mService.getAllCecLocalDevices().stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.hdmi.HdmiCecLocalDevice$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$sendVolumeKeyEvent$1;
                lambda$sendVolumeKeyEvent$1 = com.android.server.hdmi.HdmiCecLocalDevice.lambda$sendVolumeKeyEvent$1(findAudioReceiverAddress, (com.android.server.hdmi.HdmiCecLocalDevice) obj);
                return lambda$sendVolumeKeyEvent$1;
            }
        })) {
            android.util.Slog.w(TAG, "Discard volume key event: " + i + ", pressed:" + z + ", receiverAddr=" + findAudioReceiverAddress);
            return;
        }
        if (!actions.isEmpty()) {
            ((com.android.server.hdmi.SendKeyAction) actions.get(0)).processKeyEvent(i, z);
        } else if (z) {
            addAndStartAction(new com.android.server.hdmi.SendKeyAction(this, findAudioReceiverAddress, i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$sendVolumeKeyEvent$1(int i, com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
        return hdmiCecLocalDevice.getDeviceInfo().getLogicalAddress() == i;
    }

    protected int findKeyReceiverAddress() {
        android.util.Slog.w(TAG, "findKeyReceiverAddress is not implemented");
        return -1;
    }

    protected int findAudioReceiverAddress() {
        android.util.Slog.w(TAG, "findAudioReceiverAddress is not implemented");
        return -1;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void invokeCallback(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback, int i) {
        assertRunOnServiceThread();
        if (iHdmiControlCallback == null) {
            return;
        }
        try {
            iHdmiControlCallback.onComplete(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Invoking callback failed:" + e);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    public void invokeStandbyCompletedCallback(com.android.server.hdmi.HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback) {
        assertRunOnServiceThread();
        if (standbyCompletedCallback == null) {
            return;
        }
        standbyCompletedCallback.onStandbyCompleted();
    }

    void sendUserControlPressedAndReleased(int i, int i2) {
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildUserControlPressed(this.mDeviceInfo.getLogicalAddress(), i, i2));
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildUserControlReleased(this.mDeviceInfo.getLogicalAddress(), i));
    }

    void addActiveSourceHistoryItem(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource, boolean z, java.lang.String str) {
        com.android.server.hdmi.HdmiCecLocalDevice.ActiveSourceHistoryRecord activeSourceHistoryRecord = new com.android.server.hdmi.HdmiCecLocalDevice.ActiveSourceHistoryRecord(activeSource, z, str);
        if (!this.mActiveSourceHistory.offer(activeSourceHistoryRecord)) {
            this.mActiveSourceHistory.poll();
            this.mActiveSourceHistory.offer(activeSourceHistoryRecord);
        }
    }

    public java.util.concurrent.ArrayBlockingQueue<com.android.server.hdmi.HdmiCecController.Dumpable> getActiveSourceHistory() {
        return this.mActiveSourceHistory;
    }

    protected void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("mDeviceType: " + this.mDeviceType);
        indentingPrintWriter.println("mPreferredAddress: " + this.mPreferredAddress);
        indentingPrintWriter.println("mDeviceInfo: " + this.mDeviceInfo);
        indentingPrintWriter.println("mActiveSource: " + getActiveSource());
        indentingPrintWriter.println(java.lang.String.format("mActiveRoutingPath: 0x%04x", java.lang.Integer.valueOf(this.mActiveRoutingPath)));
    }

    protected int getActivePathOnSwitchFromActivePortId(@com.android.server.hdmi.Constants.LocalActivePort int i) {
        int physicalAddress = this.mService.getPhysicalAddress();
        int i2 = i << 8;
        for (int i3 = 3840; i3 > 15 && (physicalAddress & i3) != 0; i3 >>= 4) {
            i2 >>= 4;
        }
        return i2 | physicalAddress;
    }

    private static final class ActiveSourceHistoryRecord extends com.android.server.hdmi.HdmiCecController.Dumpable {
        private final com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource mActiveSource;
        private final java.lang.String mCaller;
        private final boolean mIsActiveSource;

        private ActiveSourceHistoryRecord(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource, boolean z, java.lang.String str) {
            this.mActiveSource = activeSource;
            this.mIsActiveSource = z;
            this.mCaller = str;
        }

        @Override // com.android.server.hdmi.HdmiCecController.Dumpable
        void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.text.SimpleDateFormat simpleDateFormat) {
            indentingPrintWriter.print("time=");
            indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date(this.mTime)));
            indentingPrintWriter.print(" active source=");
            indentingPrintWriter.print(this.mActiveSource);
            indentingPrintWriter.print(" isActiveSource=");
            indentingPrintWriter.print(this.mIsActiveSource);
            indentingPrintWriter.print(" from=");
            indentingPrintWriter.println(this.mCaller);
        }
    }
}
