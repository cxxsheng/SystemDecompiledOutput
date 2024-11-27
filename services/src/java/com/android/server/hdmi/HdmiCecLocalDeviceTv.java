package com.android.server.hdmi;

/* loaded from: classes2.dex */
public final class HdmiCecLocalDeviceTv extends com.android.server.hdmi.HdmiCecLocalDevice {
    private static final java.lang.String TAG = "HdmiCecLocalDeviceTv";

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private boolean mArcEstablished;
    private final android.util.SparseBooleanArray mArcFeatureEnabled;
    private final com.android.server.hdmi.DelayedMessageBuffer mDelayedMessageBuffer;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mPrevPortId;
    private final java.lang.Runnable mResetSkipRoutingControlRunnable;
    private com.android.server.hdmi.SelectRequestBuffer mSelectRequestBuffer;
    private boolean mSkipRoutingControl;
    private final android.os.Handler mSkipRoutingControlHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSystemAudioControlFeatureEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSystemAudioMute;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mSystemAudioVolume;
    private final android.media.tv.TvInputManager.TvInputCallback mTvInputCallback;
    private final java.util.HashMap<java.lang.String, java.lang.Integer> mTvInputs;

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public /* bridge */ /* synthetic */ java.util.concurrent.ArrayBlockingQueue getActiveSourceHistory() {
        return super.getActiveSourceHistory();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    public /* bridge */ /* synthetic */ void invokeStandbyCompletedCallback(com.android.server.hdmi.HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback) {
        super.invokeStandbyCompletedCallback(standbyCompletedCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mSkipRoutingControl = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void addTvInput(java.lang.String str, int i) {
        assertRunOnServiceThread();
        this.mTvInputs.put(str, java.lang.Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void removeTvInput(java.lang.String str) {
        assertRunOnServiceThread();
        this.mTvInputs.remove(str);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected boolean isInputReady(int i) {
        assertRunOnServiceThread();
        return this.mTvInputs.containsValue(java.lang.Integer.valueOf(i));
    }

    HdmiCecLocalDeviceTv(com.android.server.hdmi.HdmiControlService hdmiControlService) {
        super(hdmiControlService, 0);
        this.mArcEstablished = false;
        this.mArcFeatureEnabled = new android.util.SparseBooleanArray();
        this.mSystemAudioVolume = -1;
        this.mSystemAudioMute = false;
        this.mResetSkipRoutingControlRunnable = new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceTv$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.hdmi.HdmiCecLocalDeviceTv.this.lambda$new$0();
            }
        };
        this.mDelayedMessageBuffer = new com.android.server.hdmi.DelayedMessageBuffer(this);
        this.mTvInputCallback = new android.media.tv.TvInputManager.TvInputCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceTv.1
            @Override // android.media.tv.TvInputManager.TvInputCallback
            public void onInputAdded(java.lang.String str) {
                android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo;
                android.media.tv.TvInputInfo tvInputInfo = com.android.server.hdmi.HdmiCecLocalDeviceTv.this.mService.getTvInputManager().getTvInputInfo(str);
                if (tvInputInfo == null || (hdmiDeviceInfo = tvInputInfo.getHdmiDeviceInfo()) == null) {
                    return;
                }
                com.android.server.hdmi.HdmiCecLocalDeviceTv.this.addTvInput(str, hdmiDeviceInfo.getId());
                if (hdmiDeviceInfo.isCecDevice()) {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv.this.processDelayedActiveSource(hdmiDeviceInfo.getLogicalAddress());
                }
            }

            @Override // android.media.tv.TvInputManager.TvInputCallback
            public void onInputRemoved(java.lang.String str) {
                com.android.server.hdmi.HdmiCecLocalDeviceTv.this.removeTvInput(str);
            }
        };
        this.mTvInputs = new java.util.HashMap<>();
        this.mPrevPortId = -1;
        this.mSystemAudioControlFeatureEnabled = hdmiControlService.getHdmiCecConfig().getIntValue("system_audio_control") == 1;
        this.mStandbyHandler = new com.android.server.hdmi.HdmiCecStandbyModeHandler(hdmiControlService, this);
        this.mSkipRoutingControlHandler = new android.os.Handler(hdmiControlService.getServiceLooper());
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void onAddressAllocated(int i, int i2) {
        assertRunOnServiceThread();
        for (android.hardware.hdmi.HdmiPortInfo hdmiPortInfo : this.mService.getPortInfo()) {
            this.mArcFeatureEnabled.put(hdmiPortInfo.getId(), hdmiPortInfo.isArcSupported());
        }
        this.mService.registerTvInputCallback(this.mTvInputCallback);
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportPhysicalAddressCommand(getDeviceInfo().getLogicalAddress(), this.mService.getPhysicalAddress(), this.mDeviceType));
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildDeviceVendorIdCommand(getDeviceInfo().getLogicalAddress(), this.mService.getVendorId()));
        this.mService.getHdmiCecNetwork().addCecSwitch(this.mService.getHdmiCecNetwork().getPhysicalAddress());
        this.mTvInputs.clear();
        boolean z = false;
        this.mSkipRoutingControl = i2 == 3;
        this.mSkipRoutingControlHandler.removeCallbacks(this.mResetSkipRoutingControlRunnable);
        if (this.mSkipRoutingControl) {
            this.mSkipRoutingControlHandler.postDelayed(this.mResetSkipRoutingControlRunnable, 2000L);
        }
        if (i2 != 0 && i2 != 1) {
            z = true;
        }
        launchRoutingControl(z);
        resetSelectRequestBuffer();
        launchDeviceDiscovery();
        startQueuedActions();
        if (!this.mDelayedMessageBuffer.isBuffered(130)) {
            addAndStartAction(new com.android.server.hdmi.RequestActiveSourceAction(this, new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceTv.2
                public void onComplete(int i3) {
                    if (!com.android.server.hdmi.HdmiCecLocalDeviceTv.this.mService.getLocalActiveSource().isValid() && i3 != 0) {
                        com.android.server.hdmi.HdmiCecLocalDeviceTv.this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildActiveSource(com.android.server.hdmi.HdmiCecLocalDeviceTv.this.getDeviceInfo().getLogicalAddress(), com.android.server.hdmi.HdmiCecLocalDeviceTv.this.getDeviceInfo().getPhysicalAddress()));
                        com.android.server.hdmi.HdmiCecLocalDeviceTv.this.updateActiveSource(com.android.server.hdmi.HdmiCecLocalDeviceTv.this.getDeviceInfo().getLogicalAddress(), com.android.server.hdmi.HdmiCecLocalDeviceTv.this.getDeviceInfo().getPhysicalAddress(), "RequestActiveSourceAction#finishWithCallback()");
                    }
                }
            }));
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void setSelectRequestBuffer(com.android.server.hdmi.SelectRequestBuffer selectRequestBuffer) {
        assertRunOnServiceThread();
        this.mSelectRequestBuffer = selectRequestBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void resetSelectRequestBuffer() {
        assertRunOnServiceThread();
        setSelectRequestBuffer(com.android.server.hdmi.SelectRequestBuffer.EMPTY_BUFFER);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int getPreferredAddress() {
        return 0;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected void setPreferredAddress(int i) {
        android.util.Slog.w(TAG, "Preferred addres will not be stored for TV");
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected int dispatchMessage(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (this.mService.isPowerStandby() && !this.mService.isWakeUpMessageReceived() && this.mStandbyHandler.handleCommand(hdmiCecMessage)) {
            return -1;
        }
        return super.onMessage(hdmiCecMessage);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void deviceSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo deviceInfo = this.mService.getHdmiCecNetwork().getDeviceInfo(i);
        if (deviceInfo == null) {
            invokeCallback(iHdmiControlCallback, 3);
            return;
        }
        int logicalAddress = deviceInfo.getLogicalAddress();
        if (isAlreadyActiveSource(deviceInfo, logicalAddress, iHdmiControlCallback)) {
            return;
        }
        removeAction(com.android.server.hdmi.RequestActiveSourceAction.class);
        if (logicalAddress == 0) {
            handleSelectInternalSource();
            setActiveSource(logicalAddress, this.mService.getPhysicalAddress(), "HdmiCecLocalDeviceTv#deviceSelect()");
            setActivePath(this.mService.getPhysicalAddress());
            invokeCallback(iHdmiControlCallback, 0);
            return;
        }
        if (!this.mService.isCecControlEnabled()) {
            setActiveSource(deviceInfo, "HdmiCecLocalDeviceTv#deviceSelect()");
            invokeCallback(iHdmiControlCallback, 6);
        } else {
            removeAction(com.android.server.hdmi.DeviceSelectActionFromTv.class);
            addAndStartAction(new com.android.server.hdmi.DeviceSelectActionFromTv(this, deviceInfo, iHdmiControlCallback));
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void handleSelectInternalSource() {
        assertRunOnServiceThread();
        if (this.mService.isCecControlEnabled() && getActiveSource().logicalAddress != getDeviceInfo().getLogicalAddress()) {
            updateActiveSource(getDeviceInfo().getLogicalAddress(), this.mService.getPhysicalAddress(), "HdmiCecLocalDeviceTv#handleSelectInternalSource()");
            if (this.mSkipRoutingControl) {
                this.mSkipRoutingControl = false;
            } else {
                this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildActiveSource(getDeviceInfo().getLogicalAddress(), this.mService.getPhysicalAddress()));
            }
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void updateActiveSource(int i, int i2, java.lang.String str) {
        assertRunOnServiceThread();
        updateActiveSource(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource.of(i, i2), str);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void updateActiveSource(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource, java.lang.String str) {
        assertRunOnServiceThread();
        if (getActiveSource().equals(activeSource)) {
            return;
        }
        setActiveSource(activeSource, str);
        int i = activeSource.logicalAddress;
        if (this.mService.getHdmiCecNetwork().getCecDeviceInfo(i) != null && i != getDeviceInfo().getLogicalAddress() && this.mService.pathToPortId(activeSource.physicalAddress) == getActivePortId()) {
            setPrevPortId(getActivePortId());
        }
    }

    int getPrevPortId() {
        int i;
        synchronized (this.mLock) {
            i = this.mPrevPortId;
        }
        return i;
    }

    void setPrevPortId(int i) {
        synchronized (this.mLock) {
            this.mPrevPortId = i;
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void updateActiveInput(int i, boolean z) {
        assertRunOnServiceThread();
        setActivePath(i);
        if (z) {
            android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = this.mService.getHdmiCecNetwork().getCecDeviceInfo(getActiveSource().logicalAddress);
            if (cecDeviceInfo == null && (cecDeviceInfo = this.mService.getDeviceInfoByPort(getActivePortId())) == null) {
                cecDeviceInfo = android.hardware.hdmi.HdmiDeviceInfo.hardwarePort(i, getActivePortId());
            }
            this.mService.invokeInputChangeListener(cecDeviceInfo);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void doManualPortSwitching(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        if (!this.mService.isValidPortId(i)) {
            invokeCallback(iHdmiControlCallback, 6);
            return;
        }
        if (i == getActivePortId()) {
            invokeCallback(iHdmiControlCallback, 0);
            return;
        }
        getActiveSource().invalidate();
        if (!this.mService.isCecControlEnabled()) {
            setActivePortId(i);
            invokeCallback(iHdmiControlCallback, 6);
            return;
        }
        int physicalAddress = (getActivePortId() == -1 || getActivePortId() == 0) ? getDeviceInfo().getPhysicalAddress() : this.mService.portIdToPath(getActivePortId());
        setActivePath(physicalAddress);
        if (this.mSkipRoutingControl) {
            this.mSkipRoutingControl = false;
        } else {
            startRoutingControl(physicalAddress, this.mService.portIdToPath(i), iHdmiControlCallback);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void startRoutingControl(int i, int i2, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        if (i == i2) {
            return;
        }
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildRoutingChange(getDeviceInfo().getLogicalAddress(), i, i2));
        removeAction(com.android.server.hdmi.RoutingControlAction.class);
        addAndStartAction(new com.android.server.hdmi.RoutingControlAction(this, i2, iHdmiControlCallback));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    int getPowerStatus() {
        assertRunOnServiceThread();
        return this.mService.getPowerStatus();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int findKeyReceiverAddress() {
        if (getActiveSource().isValid()) {
            return getActiveSource().logicalAddress;
        }
        android.hardware.hdmi.HdmiDeviceInfo deviceInfoByPath = this.mService.getHdmiCecNetwork().getDeviceInfoByPath(getActivePath());
        if (deviceInfoByPath != null) {
            return deviceInfoByPath.getLogicalAddress();
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int findAudioReceiverAddress() {
        return 5;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleActiveSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int source = hdmiCecMessage.getSource();
        int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(hdmiCecMessage.getParams());
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = this.mService.getHdmiCecNetwork().getCecDeviceInfo(source);
        if (cecDeviceInfo == null) {
            if (!handleNewDeviceAtTheTailOfActivePath(twoBytesToInt)) {
                com.android.server.hdmi.HdmiLogger.debug("Device info %X not found; buffering the command", java.lang.Integer.valueOf(source));
                this.mDelayedMessageBuffer.add(hdmiCecMessage);
                return -1;
            }
            return -1;
        }
        if (isInputReady(cecDeviceInfo.getId()) || cecDeviceInfo.getDeviceType() == 5) {
            this.mService.getHdmiCecNetwork().updateDevicePowerStatus(source, 0);
            com.android.server.hdmi.ActiveSourceHandler.create(this, null).process(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource.of(source, twoBytesToInt), cecDeviceInfo.getDeviceType());
            return -1;
        }
        com.android.server.hdmi.HdmiLogger.debug("Input not ready for device: %X; buffering the command", java.lang.Integer.valueOf(cecDeviceInfo.getId()));
        this.mDelayedMessageBuffer.add(hdmiCecMessage);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleStandby(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (getActiveSource().logicalAddress != hdmiCecMessage.getSource()) {
            android.util.Slog.d(TAG, "<Standby> was not sent by the current active source, ignoring. Current active source has logical address " + getActiveSource().logicalAddress);
            return -1;
        }
        return super.handleStandby(hdmiCecMessage);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleInactiveSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (getActiveSource().logicalAddress != hdmiCecMessage.getSource() || isProhibitMode()) {
            return -1;
        }
        int prevPortId = getPrevPortId();
        if (prevPortId != -1) {
            android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = this.mService.getHdmiCecNetwork().getCecDeviceInfo(hdmiCecMessage.getSource());
            if (cecDeviceInfo == null || this.mService.pathToPortId(cecDeviceInfo.getPhysicalAddress()) == prevPortId) {
                return -1;
            }
            doManualPortSwitching(prevPortId, null);
            setPrevPortId(-1);
        } else {
            getActiveSource().invalidate();
            setActivePath(com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL);
            this.mService.invokeInputChangeListener(android.hardware.hdmi.HdmiDeviceInfo.INACTIVE_DEVICE);
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRequestActiveSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (getDeviceInfo().getLogicalAddress() == getActiveSource().logicalAddress) {
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildActiveSource(getDeviceInfo().getLogicalAddress(), getActivePath()));
            return -1;
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleGetMenuLanguage(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (!broadcastMenuLanguage(this.mService.getLanguage())) {
            android.util.Slog.w(TAG, "Failed to respond to <Get Menu Language>: " + hdmiCecMessage.toString());
            return -1;
        }
        return -1;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean broadcastMenuLanguage(java.lang.String str) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiCecMessage buildSetMenuLanguageCommand = com.android.server.hdmi.HdmiCecMessageBuilder.buildSetMenuLanguageCommand(getDeviceInfo().getLogicalAddress(), str);
        if (buildSetMenuLanguageCommand != null) {
            this.mService.sendCecCommand(buildSetMenuLanguageCommand);
            return true;
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int handleReportPhysicalAddress(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        super.handleReportPhysicalAddress(hdmiCecMessage);
        int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(hdmiCecMessage.getParams());
        int source = hdmiCecMessage.getSource();
        byte b = hdmiCecMessage.getParams()[2];
        if (!this.mService.getHdmiCecNetwork().isInDeviceList(source, twoBytesToInt)) {
            handleNewDeviceAtTheTailOfActivePath(twoBytesToInt);
        }
        startNewDeviceAction(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource.of(source, twoBytesToInt), b);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int handleTimerStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int handleRecordStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -1;
    }

    void startNewDeviceAction(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource, int i) {
        java.util.Iterator it = getActions(com.android.server.hdmi.NewDeviceAction.class).iterator();
        while (it.hasNext()) {
            if (((com.android.server.hdmi.NewDeviceAction) it.next()).isActionOf(activeSource)) {
                return;
            }
        }
        addAndStartAction(new com.android.server.hdmi.NewDeviceAction(this, activeSource.logicalAddress, activeSource.physicalAddress, i));
    }

    private boolean handleNewDeviceAtTheTailOfActivePath(int i) {
        if (isTailOfActivePath(i, getActivePath())) {
            int portIdToPath = this.mService.portIdToPath(getActivePortId());
            setActivePath(portIdToPath);
            startRoutingControl(getActivePath(), portIdToPath, null);
            return true;
        }
        return false;
    }

    static boolean isTailOfActivePath(int i, int i2) {
        if (i2 == 0) {
            return false;
        }
        for (int i3 = 12; i3 >= 0; i3 -= 4) {
            int i4 = (i2 >> i3) & 15;
            if (i4 == 0) {
                return true;
            }
            if (((i >> i3) & 15) != i4) {
                return false;
            }
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRoutingChange(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        byte[] params = hdmiCecMessage.getParams();
        if (com.android.server.hdmi.HdmiUtils.isAffectingActiveRoutingPath(getActivePath(), com.android.server.hdmi.HdmiUtils.twoBytesToInt(params))) {
            getActiveSource().invalidate();
            removeAction(com.android.server.hdmi.RoutingControlAction.class);
            addAndStartAction(new com.android.server.hdmi.RoutingControlAction(this, com.android.server.hdmi.HdmiUtils.twoBytesToInt(params, 2), null));
            return -1;
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleReportAudioStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (this.mService.getHdmiCecVolumeControl() == 0) {
            return 4;
        }
        setAudioStatus(com.android.server.hdmi.HdmiUtils.isAudioStatusMute(hdmiCecMessage), com.android.server.hdmi.HdmiUtils.getAudioStatusVolume(hdmiCecMessage));
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleTextViewOn(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (getAutoWakeup()) {
            this.mService.wakeUp();
            return -1;
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleImageViewOn(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        return handleTextViewOn(hdmiCecMessage);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void launchDeviceDiscovery() {
        assertRunOnServiceThread();
        addAndStartAction(new com.android.server.hdmi.DeviceDiscoveryAction(this, new com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceTv.3
            @Override // com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback
            public void onDeviceDiscoveryDone(java.util.List<android.hardware.hdmi.HdmiDeviceInfo> list) {
                java.util.Iterator<android.hardware.hdmi.HdmiDeviceInfo> it = list.iterator();
                while (it.hasNext()) {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv.this.mService.getHdmiCecNetwork().addCecDevice(it.next());
                }
                com.android.server.hdmi.HdmiCecLocalDeviceTv.this.mSelectRequestBuffer.process();
                com.android.server.hdmi.HdmiCecLocalDeviceTv.this.resetSelectRequestBuffer();
                if (com.android.server.hdmi.HdmiCecLocalDeviceTv.this.getActions(com.android.server.hdmi.HotplugDetectionAction.class).isEmpty()) {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv.this.addAndStartAction(new com.android.server.hdmi.HotplugDetectionAction(com.android.server.hdmi.HdmiCecLocalDeviceTv.this));
                }
                if (com.android.server.hdmi.HdmiCecLocalDeviceTv.this.getActions(com.android.server.hdmi.PowerStatusMonitorAction.class).isEmpty()) {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv.this.addAndStartAction(new com.android.server.hdmi.PowerStatusMonitorAction(com.android.server.hdmi.HdmiCecLocalDeviceTv.this));
                }
                android.hardware.hdmi.HdmiDeviceInfo avrDeviceInfo = com.android.server.hdmi.HdmiCecLocalDeviceTv.this.getAvrDeviceInfo();
                if (avrDeviceInfo != null) {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv.this.onNewAvrAdded(avrDeviceInfo);
                } else {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv.this.setSystemAudioMode(false);
                }
            }
        }));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void onNewAvrAdded(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        assertRunOnServiceThread();
        addAndStartAction(new com.android.server.hdmi.SystemAudioAutoInitiationAction(this, hdmiDeviceInfo.getLogicalAddress()));
        if (!isDirectConnectAddress(hdmiDeviceInfo.getPhysicalAddress())) {
            startArcAction(false);
        } else if (isConnected(hdmiDeviceInfo.getPortId()) && isArcFeatureEnabled(hdmiDeviceInfo.getPortId()) && !hasAction(com.android.server.hdmi.SetArcTransmissionStateAction.class)) {
            startArcAction(true);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void changeSystemAudioMode(boolean z, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        if (!this.mService.isCecControlEnabled() || hasAction(com.android.server.hdmi.DeviceDiscoveryAction.class)) {
            setSystemAudioMode(false);
            invokeCallback(iHdmiControlCallback, 6);
            return;
        }
        android.hardware.hdmi.HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo == null) {
            setSystemAudioMode(false);
            invokeCallback(iHdmiControlCallback, 3);
        } else {
            addAndStartAction(new com.android.server.hdmi.SystemAudioActionFromTv(this, avrDeviceInfo.getLogicalAddress(), z, iHdmiControlCallback));
        }
    }

    void setSystemAudioMode(boolean z) {
        if (!isSystemAudioControlFeatureEnabled() && z) {
            com.android.server.hdmi.HdmiLogger.debug("Cannot turn on system audio mode because the System Audio Control feature is disabled.", new java.lang.Object[0]);
            return;
        }
        com.android.server.hdmi.HdmiLogger.debug("System Audio Mode change[old:%b new:%b]", java.lang.Boolean.valueOf(this.mService.isSystemAudioActivated()), java.lang.Boolean.valueOf(z));
        updateAudioManagerForSystemAudio(z);
        synchronized (this.mLock) {
            try {
                if (this.mService.isSystemAudioActivated() != z) {
                    this.mService.setSystemAudioActivated(z);
                    this.mService.announceSystemAudioModeChange(z);
                }
                if (z && !this.mArcEstablished) {
                    startArcAction(true);
                } else if (!z) {
                    startArcAction(false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateAudioManagerForSystemAudio(boolean z) {
        com.android.server.hdmi.HdmiLogger.debug("[A]UpdateSystemAudio mode[on=%b] output=[%X]", java.lang.Boolean.valueOf(z), java.lang.Integer.valueOf(this.mService.getAudioManager().setHdmiSystemAudioSupported(z)));
    }

    boolean isSystemAudioActivated() {
        if (!hasSystemAudioDevice()) {
            return false;
        }
        return this.mService.isSystemAudioActivated();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setSystemAudioControlFeatureEnabled(boolean z) {
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            this.mSystemAudioControlFeatureEnabled = z;
        }
        if (hasSystemAudioDevice()) {
            changeSystemAudioMode(z, null);
        }
    }

    boolean isSystemAudioControlFeatureEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSystemAudioControlFeatureEnabled;
        }
        return z;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void enableArc(java.util.List<byte[]> list) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiLogger.debug("Set Arc Status[old:%b new:true]", java.lang.Boolean.valueOf(this.mArcEstablished));
        enableAudioReturnChannel(true);
        notifyArcStatusToAudioService(true, list);
        this.mArcEstablished = true;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void disableArc() {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiLogger.debug("Set Arc Status[old:%b new:false]", java.lang.Boolean.valueOf(this.mArcEstablished));
        enableAudioReturnChannel(false);
        notifyArcStatusToAudioService(false, new java.util.ArrayList());
        this.mArcEstablished = false;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void enableAudioReturnChannel(boolean z) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo != null && avrDeviceInfo.getPortId() != -1) {
            this.mService.enableAudioReturnChannel(avrDeviceInfo.getPortId(), z);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean isConnected(int i) {
        assertRunOnServiceThread();
        return this.mService.isConnected(i);
    }

    private void notifyArcStatusToAudioService(boolean z, java.util.List<byte[]> list) {
        this.mService.getAudioManager().setWiredDeviceConnectionState(new android.media.AudioDeviceAttributes(2, 10, "", "", new java.util.ArrayList(), (java.util.List) list.stream().map(new java.util.function.Function() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceTv$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.media.AudioDescriptor lambda$notifyArcStatusToAudioService$1;
                lambda$notifyArcStatusToAudioService$1 = com.android.server.hdmi.HdmiCecLocalDeviceTv.lambda$notifyArcStatusToAudioService$1((byte[]) obj);
                return lambda$notifyArcStatusToAudioService$1;
            }
        }).collect(java.util.stream.Collectors.toList())), z ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.media.AudioDescriptor lambda$notifyArcStatusToAudioService$1(byte[] bArr) {
        return new android.media.AudioDescriptor(1, 0, bArr);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean isArcEstablished() {
        assertRunOnServiceThread();
        if (this.mArcEstablished) {
            for (int i = 0; i < this.mArcFeatureEnabled.size(); i++) {
                if (this.mArcFeatureEnabled.valueAt(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void changeArcFeatureEnabled(int i, boolean z) {
        assertRunOnServiceThread();
        if (this.mArcFeatureEnabled.get(i) == z) {
            return;
        }
        this.mArcFeatureEnabled.put(i, z);
        android.hardware.hdmi.HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo == null || avrDeviceInfo.getPortId() != i) {
            return;
        }
        if (z && !this.mArcEstablished) {
            startArcAction(true);
        } else if (!z && this.mArcEstablished) {
            startArcAction(false);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean isArcFeatureEnabled(int i) {
        assertRunOnServiceThread();
        return this.mArcFeatureEnabled.get(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void startArcAction(boolean z) {
        startArcAction(z, null);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void startArcAction(boolean z, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo == null) {
            android.util.Slog.w(TAG, "Failed to start arc action; No AVR device.");
            invokeCallback(iHdmiControlCallback, 3);
            return;
        }
        if (!canStartArcUpdateAction(avrDeviceInfo.getLogicalAddress(), z)) {
            android.util.Slog.w(TAG, "Failed to start arc action; ARC configuration check failed.");
            if (z && !isConnectedToArcPort(avrDeviceInfo.getPhysicalAddress())) {
                displayOsd(1);
            }
            invokeCallback(iHdmiControlCallback, 6);
            return;
        }
        if (z && this.mService.earcBlocksArcConnection()) {
            android.util.Slog.i(TAG, "ARC connection blocked because eARC connection is established or being established.");
            invokeCallback(iHdmiControlCallback, 6);
            return;
        }
        if (z) {
            removeAction(com.android.server.hdmi.RequestArcTerminationAction.class);
            if (hasAction(com.android.server.hdmi.RequestArcInitiationAction.class)) {
                ((com.android.server.hdmi.RequestArcInitiationAction) getActions(com.android.server.hdmi.RequestArcInitiationAction.class).get(0)).addCallback(iHdmiControlCallback);
                return;
            } else {
                addAndStartAction(new com.android.server.hdmi.RequestArcInitiationAction(this, avrDeviceInfo.getLogicalAddress(), iHdmiControlCallback));
                return;
            }
        }
        removeAction(com.android.server.hdmi.RequestArcInitiationAction.class);
        if (hasAction(com.android.server.hdmi.RequestArcTerminationAction.class)) {
            ((com.android.server.hdmi.RequestArcTerminationAction) getActions(com.android.server.hdmi.RequestArcTerminationAction.class).get(0)).addCallback(iHdmiControlCallback);
        } else {
            addAndStartAction(new com.android.server.hdmi.RequestArcTerminationAction(this, avrDeviceInfo.getLogicalAddress(), iHdmiControlCallback));
        }
    }

    private boolean isDirectConnectAddress(int i) {
        return (61440 & i) == i;
    }

    void setAudioStatus(boolean z, int i) {
        if (!isSystemAudioActivated() || this.mService.getHdmiCecVolumeControl() == 0) {
            return;
        }
        synchronized (this.mLock) {
            this.mSystemAudioMute = z;
            this.mSystemAudioVolume = i;
            if (z) {
                i = 101;
            }
            displayOsd(2, i);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void changeVolume(int i, int i2, int i3) {
        assertRunOnServiceThread();
        if (getAvrDeviceInfo() == null || i2 == 0 || !isSystemAudioActivated() || this.mService.getHdmiCecVolumeControl() == 0) {
            return;
        }
        int scaleToCecVolume = com.android.server.hdmi.VolumeControlAction.scaleToCecVolume(i + i2, i3);
        synchronized (this.mLock) {
            try {
                if (scaleToCecVolume == this.mSystemAudioVolume) {
                    this.mService.setAudioStatus(false, com.android.server.hdmi.VolumeControlAction.scaleToCustomVolume(this.mSystemAudioVolume, i3));
                    return;
                }
                java.util.List actions = getActions(com.android.server.hdmi.VolumeControlAction.class);
                if (actions.isEmpty()) {
                    addAndStartAction(new com.android.server.hdmi.VolumeControlAction(this, getAvrDeviceInfo().getLogicalAddress(), i2 > 0));
                } else {
                    ((com.android.server.hdmi.VolumeControlAction) actions.get(0)).handleVolumeChange(i2 > 0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void changeMute(boolean z) {
        assertRunOnServiceThread();
        if (getAvrDeviceInfo() == null || this.mService.getHdmiCecVolumeControl() == 0) {
            return;
        }
        com.android.server.hdmi.HdmiLogger.debug("[A]:Change mute:%b", java.lang.Boolean.valueOf(z));
        synchronized (this.mLock) {
            try {
                if (this.mSystemAudioMute == z) {
                    com.android.server.hdmi.HdmiLogger.debug("No need to change mute.", new java.lang.Object[0]);
                } else if (!isSystemAudioActivated()) {
                    com.android.server.hdmi.HdmiLogger.debug("[A]:System audio is not activated.", new java.lang.Object[0]);
                } else {
                    removeAction(com.android.server.hdmi.VolumeControlAction.class);
                    sendUserControlPressedAndReleased(getAvrDeviceInfo().getLogicalAddress(), com.android.server.hdmi.HdmiCecKeycode.getMuteKey(z));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleInitiateArc(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (this.mService.earcBlocksArcConnection()) {
            android.util.Slog.i(TAG, "ARC connection blocked because eARC connection is established or being established.");
            return 1;
        }
        if (!canStartArcUpdateAction(hdmiCecMessage.getSource(), true)) {
            android.hardware.hdmi.HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
            if (avrDeviceInfo == null) {
                this.mDelayedMessageBuffer.add(hdmiCecMessage);
                return -1;
            }
            if (!isConnectedToArcPort(avrDeviceInfo.getPhysicalAddress())) {
                displayOsd(1);
                return 4;
            }
            return 4;
        }
        addAndStartAction(new com.android.server.hdmi.SetArcTransmissionStateAction(this, hdmiCecMessage.getSource(), true));
        return -1;
    }

    private boolean canStartArcUpdateAction(int i, boolean z) {
        android.hardware.hdmi.HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo == null || i != avrDeviceInfo.getLogicalAddress() || !isConnectedToArcPort(avrDeviceInfo.getPhysicalAddress())) {
            return false;
        }
        if (!z) {
            return true;
        }
        if (!isConnected(avrDeviceInfo.getPortId()) || !isArcFeatureEnabled(avrDeviceInfo.getPortId()) || !isDirectConnectAddress(avrDeviceInfo.getPhysicalAddress())) {
            return false;
        }
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleTerminateArc(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (this.mService.isPowerStandbyOrTransient()) {
            disableArc();
            return -1;
        }
        addAndStartAction(new com.android.server.hdmi.SetArcTransmissionStateAction(this, hdmiCecMessage.getSource(), false));
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleSetSystemAudioMode(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        boolean parseCommandParamSystemAudioStatus = com.android.server.hdmi.HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage);
        if (!isMessageForSystemAudio(hdmiCecMessage)) {
            if (getAvrDeviceInfo() == null) {
                this.mDelayedMessageBuffer.add(hdmiCecMessage);
            } else {
                com.android.server.hdmi.HdmiLogger.warning("Invalid <Set System Audio Mode> message:" + hdmiCecMessage, new java.lang.Object[0]);
                return 4;
            }
        } else if (parseCommandParamSystemAudioStatus && !isSystemAudioControlFeatureEnabled()) {
            com.android.server.hdmi.HdmiLogger.debug("Ignoring <Set System Audio Mode> message because the System Audio Control feature is disabled: %s", hdmiCecMessage);
            return 4;
        }
        removeAction(com.android.server.hdmi.SystemAudioAutoInitiationAction.class);
        addAndStartAction(new com.android.server.hdmi.SystemAudioActionFromAvr(this, hdmiCecMessage.getSource(), parseCommandParamSystemAudioStatus, null));
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleSystemAudioModeStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (!isMessageForSystemAudio(hdmiCecMessage)) {
            com.android.server.hdmi.HdmiLogger.warning("Invalid <System Audio Mode Status> message:" + hdmiCecMessage, new java.lang.Object[0]);
            return -1;
        }
        boolean isSystemAudioControlFeatureEnabled = isSystemAudioControlFeatureEnabled();
        boolean parseCommandParamSystemAudioStatus = com.android.server.hdmi.HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage);
        android.hardware.hdmi.HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo == null) {
            setSystemAudioMode(false);
        } else if (parseCommandParamSystemAudioStatus != isSystemAudioControlFeatureEnabled) {
            addAndStartAction(new com.android.server.hdmi.SystemAudioActionFromTv(this, avrDeviceInfo.getLogicalAddress(), isSystemAudioControlFeatureEnabled, null));
        } else {
            setSystemAudioMode(isSystemAudioControlFeatureEnabled);
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRecordTvScreen(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        java.util.List actions = getActions(com.android.server.hdmi.OneTouchRecordAction.class);
        if (!actions.isEmpty()) {
            if (((com.android.server.hdmi.OneTouchRecordAction) actions.get(0)).getRecorderAddress() != hdmiCecMessage.getSource()) {
                announceOneTouchRecordResult(hdmiCecMessage.getSource(), 48);
                return 2;
            }
            return 2;
        }
        int source = hdmiCecMessage.getSource();
        return startOneTouchRecord(source, this.mService.invokeRecordRequestListener(source));
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int handleTimerClearedStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        announceTimerRecordingResult(hdmiCecMessage.getSource(), hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int handleSetAudioVolumeLevel(com.android.server.hdmi.SetAudioVolumeLevelMessage setAudioVolumeLevelMessage) {
        if (this.mService.isSystemAudioActivated()) {
            return 1;
        }
        int audioVolumeLevel = setAudioVolumeLevelMessage.getAudioVolumeLevel();
        if (audioVolumeLevel >= 0 && audioVolumeLevel <= 100) {
            this.mService.setStreamMusicVolume(audioVolumeLevel, 0);
            return -1;
        }
        return -1;
    }

    void announceOneTouchRecordResult(int i, int i2) {
        this.mService.invokeOneTouchRecordResult(i, i2);
    }

    void announceTimerRecordingResult(int i, int i2) {
        this.mService.invokeTimerRecordingResult(i, i2);
    }

    void announceClearTimerRecordingResult(int i, int i2) {
        this.mService.invokeClearTimerRecordingResult(i, i2);
    }

    private boolean isMessageForSystemAudio(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return this.mService.isCecControlEnabled() && hdmiCecMessage.getSource() == 5 && (hdmiCecMessage.getDestination() == 0 || hdmiCecMessage.getDestination() == 15) && getAvrDeviceInfo() != null;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @android.annotation.Nullable
    android.hardware.hdmi.HdmiDeviceInfo getAvrDeviceInfo() {
        assertRunOnServiceThread();
        return this.mService.getHdmiCecNetwork().getCecDeviceInfo(5);
    }

    boolean hasSystemAudioDevice() {
        return getSafeAvrDeviceInfo() != null;
    }

    @android.annotation.Nullable
    android.hardware.hdmi.HdmiDeviceInfo getSafeAvrDeviceInfo() {
        return this.mService.getHdmiCecNetwork().getSafeCecDeviceInfo(5);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void handleRemoveActiveRoutingPath(int i) {
        assertRunOnServiceThread();
        if (isTailOfActivePath(i, getActivePath())) {
            startRoutingControl(getActivePath(), this.mService.portIdToPath(getActivePortId()), null);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void launchRoutingControl(boolean z) {
        assertRunOnServiceThread();
        if (getActivePortId() != -1 && getActivePortId() != 0) {
            if (!z && !isProhibitMode()) {
                int portIdToPath = this.mService.portIdToPath(getActivePortId());
                setActivePath(portIdToPath);
                startRoutingControl(getActivePath(), portIdToPath, null);
                return;
            }
            return;
        }
        int physicalAddress = this.mService.getPhysicalAddress();
        setActivePath(physicalAddress);
        if (!z && !this.mDelayedMessageBuffer.isBuffered(130)) {
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildActiveSource(getDeviceInfo().getLogicalAddress(), physicalAddress));
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void onHotplug(int i, boolean z) {
        android.hardware.hdmi.HdmiDeviceInfo avrDeviceInfo;
        assertRunOnServiceThread();
        if (!z) {
            this.mService.getHdmiCecNetwork().removeCecSwitches(i);
        }
        if ((!this.mService.isEarcEnabled() || !this.mService.isEarcSupported()) && (avrDeviceInfo = getAvrDeviceInfo()) != null && i == avrDeviceInfo.getPortId() && isConnectedToArcPort(avrDeviceInfo.getPhysicalAddress())) {
            com.android.server.hdmi.HdmiLogger.debug("Port ID:%d, 5v=%b", java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z));
            if (z) {
                if (this.mArcEstablished) {
                    enableAudioReturnChannel(true);
                }
            } else {
                enableAudioReturnChannel(false);
            }
        }
        java.util.List actions = getActions(com.android.server.hdmi.HotplugDetectionAction.class);
        if (!actions.isEmpty()) {
            ((com.android.server.hdmi.HotplugDetectionAction) actions.get(0)).pollAllDevicesNow();
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean getAutoWakeup() {
        assertRunOnServiceThread();
        return this.mService.getHdmiCecConfig().getIntValue("tv_wake_on_one_touch_play") == 1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void disableDevice(boolean z, com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback) {
        assertRunOnServiceThread();
        this.mService.unregisterTvInputCallback(this.mTvInputCallback);
        removeAction(com.android.server.hdmi.DeviceDiscoveryAction.class);
        removeAction(com.android.server.hdmi.HotplugDetectionAction.class);
        removeAction(com.android.server.hdmi.PowerStatusMonitorAction.class);
        removeAction(com.android.server.hdmi.OneTouchRecordAction.class);
        removeAction(com.android.server.hdmi.TimerRecordingAction.class);
        removeAction(com.android.server.hdmi.NewDeviceAction.class);
        removeAction(com.android.server.hdmi.RequestActiveSourceAction.class);
        if (z || !this.mService.isEarcEnabled()) {
            disableSystemAudioIfExist();
        }
        disableArcIfExist();
        super.disableDevice(z, pendingActionClearedCallback);
        clearDeviceInfoList();
        getActiveSource().invalidate();
        setActivePath(com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL);
        checkIfPendingActionsCleared();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void disableSystemAudioIfExist() {
        assertRunOnServiceThread();
        if (getAvrDeviceInfo() == null) {
            return;
        }
        removeAction(com.android.server.hdmi.SystemAudioActionFromAvr.class);
        removeAction(com.android.server.hdmi.SystemAudioActionFromTv.class);
        removeAction(com.android.server.hdmi.SystemAudioAutoInitiationAction.class);
        removeAction(com.android.server.hdmi.VolumeControlAction.class);
        if (!this.mService.isCecControlEnabled()) {
            setSystemAudioMode(false);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void forceDisableArcOnAllPins() {
        for (android.hardware.hdmi.HdmiPortInfo hdmiPortInfo : this.mService.getPortInfo()) {
            if (isArcFeatureEnabled(hdmiPortInfo.getId())) {
                this.mService.enableAudioReturnChannel(hdmiPortInfo.getId(), false);
            }
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void disableArcIfExist() {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo == null) {
            return;
        }
        removeAllRunningArcAction();
        if (!hasAction(com.android.server.hdmi.RequestArcTerminationAction.class) && isArcEstablished()) {
            addAndStartAction(new com.android.server.hdmi.RequestArcTerminationAction(this, avrDeviceInfo.getLogicalAddress()));
        }
        forceDisableArcOnAllPins();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void removeAllRunningArcAction() {
        removeAction(com.android.server.hdmi.RequestArcTerminationAction.class);
        removeAction(com.android.server.hdmi.RequestArcInitiationAction.class);
        removeAction(com.android.server.hdmi.SetArcTransmissionStateAction.class);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void onStandby(boolean z, int i, final com.android.server.hdmi.HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback) {
        assertRunOnServiceThread();
        if (!this.mService.isCecControlEnabled()) {
            invokeStandbyCompletedCallback(standbyCompletedCallback);
            return;
        }
        boolean z2 = this.mService.getHdmiCecConfig().getIntValue("tv_send_standby_on_sleep") == 1;
        if (!z && z2) {
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildStandby(getDeviceInfo().getLogicalAddress(), 15), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceTv.4
                @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                public void onSendCompleted(int i2) {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv.this.invokeStandbyCompletedCallback(standbyCompletedCallback);
                }
            });
        } else {
            invokeStandbyCompletedCallback(standbyCompletedCallback);
        }
    }

    boolean isProhibitMode() {
        return this.mService.isProhibitMode();
    }

    boolean isPowerStandbyOrTransient() {
        return this.mService.isPowerStandbyOrTransient();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void displayOsd(int i) {
        assertRunOnServiceThread();
        this.mService.displayOsd(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void displayOsd(int i, int i2) {
        assertRunOnServiceThread();
        this.mService.displayOsd(i, i2);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    int startOneTouchRecord(int i, byte[] bArr) {
        assertRunOnServiceThread();
        if (!this.mService.isCecControlEnabled()) {
            android.util.Slog.w(TAG, "Can not start one touch record. CEC control is disabled.");
            announceOneTouchRecordResult(i, 51);
            return 1;
        }
        if (!checkRecorder(i)) {
            android.util.Slog.w(TAG, "Invalid recorder address:" + i);
            announceOneTouchRecordResult(i, 49);
            return 1;
        }
        if (!checkRecordSource(bArr)) {
            android.util.Slog.w(TAG, "Invalid record source." + java.util.Arrays.toString(bArr));
            announceOneTouchRecordResult(i, 50);
            return 2;
        }
        addAndStartAction(new com.android.server.hdmi.OneTouchRecordAction(this, i, bArr));
        android.util.Slog.i(TAG, "Start new [One Touch Record]-Target:" + i + ", recordSource:" + java.util.Arrays.toString(bArr));
        return -1;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void stopOneTouchRecord(int i) {
        assertRunOnServiceThread();
        if (!this.mService.isCecControlEnabled()) {
            android.util.Slog.w(TAG, "Can not stop one touch record. CEC control is disabled.");
            announceOneTouchRecordResult(i, 51);
            return;
        }
        if (!checkRecorder(i)) {
            android.util.Slog.w(TAG, "Invalid recorder address:" + i);
            announceOneTouchRecordResult(i, 49);
            return;
        }
        removeAction(com.android.server.hdmi.OneTouchRecordAction.class);
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildRecordOff(getDeviceInfo().getLogicalAddress(), i));
        android.util.Slog.i(TAG, "Stop [One Touch Record]-Target:" + i);
    }

    private boolean checkRecorder(int i) {
        return this.mService.getHdmiCecNetwork().getCecDeviceInfo(i) != null && com.android.server.hdmi.HdmiUtils.isEligibleAddressForDevice(1, i);
    }

    private boolean checkRecordSource(byte[] bArr) {
        return bArr != null && android.hardware.hdmi.HdmiRecordSources.checkRecordSource(bArr);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void startTimerRecording(int i, int i2, byte[] bArr) {
        assertRunOnServiceThread();
        if (!this.mService.isCecControlEnabled()) {
            android.util.Slog.w(TAG, "Can not start one touch record. CEC control is disabled.");
            announceTimerRecordingResult(i, 3);
            return;
        }
        if (!checkRecorder(i)) {
            android.util.Slog.w(TAG, "Invalid recorder address:" + i);
            announceTimerRecordingResult(i, 1);
            return;
        }
        if (!checkTimerRecordingSource(i2, bArr)) {
            android.util.Slog.w(TAG, "Invalid record source." + java.util.Arrays.toString(bArr));
            announceTimerRecordingResult(i, 2);
            return;
        }
        addAndStartAction(new com.android.server.hdmi.TimerRecordingAction(this, i, i2, bArr));
        android.util.Slog.i(TAG, "Start [Timer Recording]-Target:" + i + ", SourceType:" + i2 + ", RecordSource:" + java.util.Arrays.toString(bArr));
    }

    private boolean checkTimerRecordingSource(int i, byte[] bArr) {
        return bArr != null && android.hardware.hdmi.HdmiTimerRecordSources.checkTimerRecordSource(i, bArr);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void clearTimerRecording(int i, int i2, byte[] bArr) {
        assertRunOnServiceThread();
        if (!this.mService.isCecControlEnabled()) {
            android.util.Slog.w(TAG, "Can not start one touch record. CEC control is disabled.");
            announceClearTimerRecordingResult(i, 162);
            return;
        }
        if (!checkRecorder(i)) {
            android.util.Slog.w(TAG, "Invalid recorder address:" + i);
            announceClearTimerRecordingResult(i, 160);
            return;
        }
        if (!checkTimerRecordingSource(i2, bArr)) {
            android.util.Slog.w(TAG, "Invalid record source." + java.util.Arrays.toString(bArr));
            announceClearTimerRecordingResult(i, 161);
            return;
        }
        sendClearTimerMessage(i, i2, bArr);
    }

    private void sendClearTimerMessage(final int i, int i2, byte[] bArr) {
        com.android.server.hdmi.HdmiCecMessage buildClearDigitalTimer;
        switch (i2) {
            case 1:
                buildClearDigitalTimer = com.android.server.hdmi.HdmiCecMessageBuilder.buildClearDigitalTimer(getDeviceInfo().getLogicalAddress(), i, bArr);
                break;
            case 2:
                buildClearDigitalTimer = com.android.server.hdmi.HdmiCecMessageBuilder.buildClearAnalogueTimer(getDeviceInfo().getLogicalAddress(), i, bArr);
                break;
            case 3:
                buildClearDigitalTimer = com.android.server.hdmi.HdmiCecMessageBuilder.buildClearExternalTimer(getDeviceInfo().getLogicalAddress(), i, bArr);
                break;
            default:
                android.util.Slog.w(TAG, "Invalid source type:" + i);
                announceClearTimerRecordingResult(i, 161);
                return;
        }
        this.mService.sendCecCommand(buildClearDigitalTimer, new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceTv.5
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public void onSendCompleted(int i3) {
                if (i3 != 0) {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv.this.announceClearTimerRecordingResult(i, 161);
                }
            }
        });
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int handleMenuStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.Constants.RcProfile
    protected int getRcProfile() {
        return 0;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected java.util.List<java.lang.Integer> getRcFeatures() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(java.lang.Integer.valueOf(this.mService.getHdmiCecConfig().getIntValue("rc_profile_tv")));
        return arrayList;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected android.hardware.hdmi.DeviceFeatures computeDeviceFeatures() {
        int i;
        java.util.Iterator<android.hardware.hdmi.HdmiPortInfo> it = this.mService.getPortInfo().iterator();
        while (true) {
            if (!it.hasNext()) {
                i = 0;
                break;
            }
            if (isArcFeatureEnabled(it.next().getId())) {
                i = 1;
                break;
            }
        }
        return android.hardware.hdmi.DeviceFeatures.NO_FEATURES_SUPPORTED.toBuilder().setRecordTvScreenSupport(1).setArcTxSupport(i).setSetAudioVolumeLevelSupport(1).build();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected void sendStandby(int i) {
        android.hardware.hdmi.HdmiDeviceInfo deviceInfo = this.mService.getHdmiCecNetwork().getDeviceInfo(i);
        if (deviceInfo == null) {
            return;
        }
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildStandby(getDeviceInfo().getLogicalAddress(), deviceInfo.getLogicalAddress()));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void processAllDelayedMessages() {
        assertRunOnServiceThread();
        this.mDelayedMessageBuffer.processAllMessages();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void processDelayedMessages(int i) {
        assertRunOnServiceThread();
        this.mDelayedMessageBuffer.processMessagesForDevice(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void processDelayedActiveSource(int i) {
        assertRunOnServiceThread();
        this.mDelayedMessageBuffer.processActiveSource(i);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        super.dump(indentingPrintWriter);
        indentingPrintWriter.println("mArcEstablished: " + this.mArcEstablished);
        indentingPrintWriter.println("mArcFeatureEnabled: " + this.mArcFeatureEnabled);
        indentingPrintWriter.println("mSystemAudioMute: " + this.mSystemAudioMute);
        indentingPrintWriter.println("mSystemAudioControlFeatureEnabled: " + this.mSystemAudioControlFeatureEnabled);
        indentingPrintWriter.println("mSkipRoutingControl: " + this.mSkipRoutingControl);
        indentingPrintWriter.println("mPrevPortId: " + this.mPrevPortId);
    }
}
