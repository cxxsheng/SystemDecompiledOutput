package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class HdmiCecLocalDevicePlayback extends com.android.server.hdmi.HdmiCecLocalDeviceSource {

    @com.android.internal.annotations.VisibleForTesting
    static final long STANDBY_AFTER_HOTPLUG_OUT_DELAY_MS = 30000;
    private static final java.lang.String TAG = "HdmiCecLocalDevicePlayback";
    private android.os.Handler mDelayedStandbyHandler;

    @com.android.internal.annotations.VisibleForTesting
    protected android.sysprop.HdmiProperties.playback_device_action_on_routing_control_values mPlaybackDeviceActionOnRoutingControl;
    private com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock mWakeLock;

    private interface ActiveWakeLock {
        void acquire();

        boolean isHeld();

        void release();
    }

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

    HdmiCecLocalDevicePlayback(com.android.server.hdmi.HdmiControlService hdmiControlService) {
        super(hdmiControlService, 4);
        this.mPlaybackDeviceActionOnRoutingControl = (android.sysprop.HdmiProperties.playback_device_action_on_routing_control_values) android.sysprop.HdmiProperties.playback_device_action_on_routing_control().orElse(android.sysprop.HdmiProperties.playback_device_action_on_routing_control_values.NONE);
        this.mDelayedStandbyHandler = new android.os.Handler(hdmiControlService.getServiceLooper());
        this.mStandbyHandler = new com.android.server.hdmi.HdmiCecStandbyModeHandler(hdmiControlService, this);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void onAddressAllocated(int i, int i2) {
        assertRunOnServiceThread();
        if (i2 == 0) {
            this.mService.setAndBroadcastActiveSource(this.mService.getPhysicalAddress(), getDeviceInfo().getDeviceType(), 15, "HdmiCecLocalDevicePlayback#onAddressAllocated()");
        }
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportPhysicalAddressCommand(getDeviceInfo().getLogicalAddress(), this.mService.getPhysicalAddress(), this.mDeviceType));
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildDeviceVendorIdCommand(getDeviceInfo().getLogicalAddress(), this.mService.getVendorId()));
        buildAndSendSetOsdName(0);
        if (this.mService.audioSystem() == null) {
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveSystemAudioModeStatus(getDeviceInfo().getLogicalAddress(), 5), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDevicePlayback.1
                @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                public void onSendCompleted(int i3) {
                    if (i3 == 1) {
                        com.android.server.hdmi.HdmiLogger.debug("AVR did not respond to <Give System Audio Mode Status>", new java.lang.Object[0]);
                        com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.mService.setSystemAudioActivated(false);
                    }
                }
            });
        }
        launchDeviceDiscovery();
        startQueuedActions();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void launchDeviceDiscovery() {
        assertRunOnServiceThread();
        clearDeviceInfoList();
        if (hasAction(com.android.server.hdmi.DeviceDiscoveryAction.class)) {
            android.util.Slog.i(TAG, "Device Discovery Action is in progress. Restarting.");
            removeAction(com.android.server.hdmi.DeviceDiscoveryAction.class);
        }
        addAndStartAction(new com.android.server.hdmi.DeviceDiscoveryAction(this, new com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDevicePlayback.2
            @Override // com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback
            public void onDeviceDiscoveryDone(java.util.List<android.hardware.hdmi.HdmiDeviceInfo> list) {
                java.util.Iterator<android.hardware.hdmi.HdmiDeviceInfo> it = list.iterator();
                while (it.hasNext()) {
                    com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.mService.getHdmiCecNetwork().addCecDevice(it.next());
                }
                java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it2 = com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.mService.getAllCecLocalDevices().iterator();
                while (it2.hasNext()) {
                    com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.mService.getHdmiCecNetwork().addCecDevice(it2.next().getDeviceInfo());
                }
                if (com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.getActions(com.android.server.hdmi.HotplugDetectionAction.class).isEmpty()) {
                    com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.addAndStartAction(new com.android.server.hdmi.HotplugDetectionAction(com.android.server.hdmi.HdmiCecLocalDevicePlayback.this));
                }
            }
        }));
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int getPreferredAddress() {
        assertRunOnServiceThread();
        return android.os.SystemProperties.getInt("persist.sys.hdmi.addr.playback", 15);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void setPreferredAddress(int i) {
        assertRunOnServiceThread();
        this.mService.writeStringSystemProperty("persist.sys.hdmi.addr.playback", java.lang.String.valueOf(i));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void deviceSelect(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        if (i == getDeviceInfo().getId()) {
            this.mService.oneTouchPlay(iHdmiControlCallback);
            return;
        }
        android.hardware.hdmi.HdmiDeviceInfo deviceInfo = this.mService.getHdmiCecNetwork().getDeviceInfo(i);
        if (deviceInfo == null) {
            invokeCallback(iHdmiControlCallback, 3);
            return;
        }
        if (isAlreadyActiveSource(deviceInfo, deviceInfo.getLogicalAddress(), iHdmiControlCallback)) {
            return;
        }
        if (!this.mService.isCecControlEnabled()) {
            setActiveSource(deviceInfo, "HdmiCecLocalDevicePlayback#deviceSelect()");
            invokeCallback(iHdmiControlCallback, 6);
        } else {
            removeAction(com.android.server.hdmi.DeviceSelectActionFromPlayback.class);
            addAndStartAction(new com.android.server.hdmi.DeviceSelectActionFromPlayback(this, deviceInfo, iHdmiControlCallback));
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource, com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void onHotplug(int i, boolean z) {
        assertRunOnServiceThread();
        this.mCecMessageCache.flushAll();
        if (z) {
            this.mDelayedStandbyHandler.removeCallbacksAndMessages(null);
            return;
        }
        getWakeLock().release();
        this.mService.getHdmiCecNetwork().removeDevicesConnectedToPort(i);
        this.mDelayedStandbyHandler.removeCallbacksAndMessages(null);
        this.mDelayedStandbyHandler.postDelayed(new com.android.server.hdmi.HdmiCecLocalDevicePlayback.DelayedStandbyRunnable(), 30000L);
    }

    private class DelayedStandbyRunnable implements java.lang.Runnable {
        private DelayedStandbyRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.mService.getPowerManagerInternal().wasDeviceIdleFor(30000L)) {
                com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.mService.standby();
            } else {
                com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.mDelayedStandbyHandler.postDelayed(com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.new DelayedStandbyRunnable(), 30000L);
            }
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void onStandby(boolean z, int i, final com.android.server.hdmi.HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback) {
        assertRunOnServiceThread();
        if (!this.mService.isCecControlEnabled()) {
            invokeStandbyCompletedCallback(standbyCompletedCallback);
        }
        boolean isActiveSource = isActiveSource();
        char c = 65535;
        this.mService.setActiveSource(-1, com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL, "HdmiCecLocalDevicePlayback#onStandby()");
        if (!isActiveSource) {
            invokeStandbyCompletedCallback(standbyCompletedCallback);
            return;
        }
        com.android.server.hdmi.HdmiControlService.SendMessageCallback sendMessageCallback = new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDevicePlayback.3
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public void onSendCompleted(int i2) {
                com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.invokeStandbyCompletedCallback(standbyCompletedCallback);
            }
        };
        if (z) {
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildInactiveSource(getDeviceInfo().getLogicalAddress(), this.mService.getPhysicalAddress()), sendMessageCallback);
            return;
        }
        switch (i) {
            case 0:
                java.lang.String stringValue = this.mService.getHdmiCecConfig().getStringValue("power_control_mode");
                switch (stringValue.hashCode()) {
                    case -1744153479:
                        if (stringValue.equals("to_tv_and_audio_system")) {
                            c = 1;
                            break;
                        }
                        break;
                    case -1618876223:
                        if (stringValue.equals("broadcast")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 3387192:
                        if (stringValue.equals("none")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 110530246:
                        if (stringValue.equals("to_tv")) {
                            c = 0;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildStandby(getDeviceInfo().getLogicalAddress(), 0), sendMessageCallback);
                        break;
                    case 1:
                        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildStandby(getDeviceInfo().getLogicalAddress(), 0));
                        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildStandby(getDeviceInfo().getLogicalAddress(), 5), sendMessageCallback);
                        break;
                    case 2:
                        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildStandby(getDeviceInfo().getLogicalAddress(), 15), sendMessageCallback);
                        break;
                    case 3:
                        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildInactiveSource(getDeviceInfo().getLogicalAddress(), this.mService.getPhysicalAddress()), sendMessageCallback);
                        break;
                }
            case 1:
                this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildStandby(getDeviceInfo().getLogicalAddress(), 15), sendMessageCallback);
                break;
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void onInitializeCecComplete(int i) {
        if (i != 2 || this.mService.getHdmiCecConfig().getStringValue("power_control_mode").equals("none")) {
            return;
        }
        oneTouchPlay(new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: com.android.server.hdmi.HdmiCecLocalDevicePlayback.4
            public void onComplete(int i2) {
                if (i2 != 0) {
                    android.util.Slog.w(com.android.server.hdmi.HdmiCecLocalDevicePlayback.TAG, "Failed to complete One Touch Play. result=" + i2);
                }
            }
        });
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource, com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void setActiveSource(int i, int i2, java.lang.String str) {
        assertRunOnServiceThread();
        super.setActiveSource(i, i2, str);
        if (isActiveSource()) {
            getWakeLock().acquire();
        } else {
            getWakeLock().release();
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock getWakeLock() {
        assertRunOnServiceThread();
        if (this.mWakeLock == null) {
            if (android.os.SystemProperties.getBoolean("persist.sys.hdmi.keep_awake", true)) {
                this.mWakeLock = new com.android.server.hdmi.HdmiCecLocalDevicePlayback.SystemWakeLock();
            } else {
                this.mWakeLock = new com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock() { // from class: com.android.server.hdmi.HdmiCecLocalDevicePlayback.5
                    @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
                    public void acquire() {
                    }

                    @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
                    public void release() {
                    }

                    @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
                    public boolean isHeld() {
                        return false;
                    }
                };
                com.android.server.hdmi.HdmiLogger.debug("No wakelock is used to keep the display on.", new java.lang.Object[0]);
            }
        }
        return this.mWakeLock;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected boolean canGoToStandby() {
        return !getWakeLock().isHeld();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void onActiveSourceLost() {
        char c;
        assertRunOnServiceThread();
        this.mService.pauseActiveMediaSessions();
        java.lang.String stringValue = this.mService.getHdmiCecConfig().getStringValue("power_state_change_on_active_source_lost");
        switch (stringValue.hashCode()) {
            case -1129124284:
                if (stringValue.equals("standby_now")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3387192:
                if (stringValue.equals("none")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mService.standby();
                break;
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleUserControlPressed(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        wakeUpIfActiveSource();
        return super.handleUserControlPressed(hdmiCecMessage);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleSetMenuLanguage(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (this.mService.getHdmiCecConfig().getIntValue("set_menu_language") == 0) {
            return 0;
        }
        try {
            java.lang.String str = new java.lang.String(hdmiCecMessage.getParams(), 0, 3, "US-ASCII");
            java.lang.String localeToMenuLanguage = com.android.server.hdmi.HdmiControlService.localeToMenuLanguage(this.mService.getContext().getResources().getConfiguration().locale);
            com.android.server.hdmi.HdmiLogger.debug("handleSetMenuLanguage " + str + " cur:" + localeToMenuLanguage, new java.lang.Object[0]);
            if (localeToMenuLanguage.equals(str)) {
                return -1;
            }
            for (com.android.internal.app.LocalePicker.LocaleInfo localeInfo : com.android.internal.app.LocalePicker.getAllAssetLocales(this.mService.getContext(), false)) {
                if (com.android.server.hdmi.HdmiControlService.localeToMenuLanguage(localeInfo.getLocale()).equals(str)) {
                    startSetMenuLanguageActivity(localeInfo.getLocale());
                    return -1;
                }
            }
            android.util.Slog.w(TAG, "Can't handle <Set Menu Language> of " + str);
            return 3;
        } catch (java.io.UnsupportedEncodingException e) {
            android.util.Slog.w(TAG, "Can't handle <Set Menu Language>", e);
            return 3;
        }
    }

    private void startSetMenuLanguageActivity(java.util.Locale locale) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                android.content.Context context = this.mService.getContext();
                android.content.Intent intent = new android.content.Intent();
                intent.putExtra("android.hardware.hdmi.extra.LOCALE", locale.toLanguageTag());
                intent.setComponent(android.content.ComponentName.unflattenFromString(context.getResources().getString(android.R.string.config_factoryResetPackage)));
                intent.addFlags(268435456);
                context.startActivityAsUser(intent, context.getUser());
            } catch (android.content.ActivityNotFoundException e) {
                android.util.Slog.e(TAG, "unable to start HdmiCecSetMenuLanguageActivity");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int handleSetSystemAudioMode(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        boolean parseCommandParamSystemAudioStatus;
        if (hdmiCecMessage.getDestination() == 15 && hdmiCecMessage.getSource() == 5 && this.mService.audioSystem() == null && this.mService.isSystemAudioActivated() != (parseCommandParamSystemAudioStatus = com.android.server.hdmi.HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage))) {
            this.mService.setSystemAudioActivated(parseCommandParamSystemAudioStatus);
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int handleSystemAudioModeStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        boolean parseCommandParamSystemAudioStatus;
        if (hdmiCecMessage.getDestination() == getDeviceInfo().getLogicalAddress() && hdmiCecMessage.getSource() == 5 && this.mService.isSystemAudioActivated() != (parseCommandParamSystemAudioStatus = com.android.server.hdmi.HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage))) {
            this.mService.setSystemAudioActivated(parseCommandParamSystemAudioStatus);
            return -1;
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource, com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRoutingChange(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        handleRoutingChangeAndInformation(com.android.server.hdmi.HdmiUtils.twoBytesToInt(hdmiCecMessage.getParams(), 2), hdmiCecMessage);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource, com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRoutingInformation(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(hdmiCecMessage.getParams());
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = this.mService.getHdmiCecNetwork().getCecDeviceInfo(hdmiCecMessage.getSource());
        if (cecDeviceInfo != null && cecDeviceInfo.getLogicalAddress() != 0 && cecDeviceInfo.getPhysicalAddress() == twoBytesToInt) {
            android.util.Slog.d(TAG, "<Routing Information> is ignored, it is pointing to the same physical address as the message sender");
            return -1;
        }
        handleRoutingChangeAndInformation(twoBytesToInt, hdmiCecMessage);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void handleRoutingChangeAndInformation(int i, com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (com.android.server.hdmi.HdmiUtils.isInActiveRoutingPath(this.mService.getPhysicalAddress(), i) && i != 0 && isActiveSource()) {
        }
        if (i != this.mService.getPhysicalAddress()) {
            setActiveSource(i, "HdmiCecLocalDevicePlayback#handleRoutingChangeAndInformation()");
            return;
        }
        if (!isActiveSource()) {
            setActiveSource(i, "HdmiCecLocalDevicePlayback#handleRoutingChangeAndInformation()");
        }
        switch (com.android.server.hdmi.HdmiCecLocalDevicePlayback.AnonymousClass6.$SwitchMap$android$sysprop$HdmiProperties$playback_device_action_on_routing_control_values[this.mPlaybackDeviceActionOnRoutingControl.ordinal()]) {
            case 1:
                setAndBroadcastActiveSource(hdmiCecMessage, i, "HdmiCecLocalDevicePlayback#handleRoutingChangeAndInformation()");
                break;
            case 2:
                this.mService.wakeUp();
                break;
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiCecLocalDevicePlayback$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$android$sysprop$HdmiProperties$playback_device_action_on_routing_control_values = new int[android.sysprop.HdmiProperties.playback_device_action_on_routing_control_values.values().length];

        static {
            try {
                $SwitchMap$android$sysprop$HdmiProperties$playback_device_action_on_routing_control_values[android.sysprop.HdmiProperties.playback_device_action_on_routing_control_values.WAKE_UP_AND_SEND_ACTIVE_SOURCE.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$playback_device_action_on_routing_control_values[android.sysprop.HdmiProperties.playback_device_action_on_routing_control_values.WAKE_UP_ONLY.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$playback_device_action_on_routing_control_values[android.sysprop.HdmiProperties.playback_device_action_on_routing_control_values.NONE.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected void preprocessBufferedMessages(java.util.List<com.android.server.hdmi.HdmiCecMessage> list) {
        for (com.android.server.hdmi.HdmiCecMessage hdmiCecMessage : list) {
            if (hdmiCecMessage.getOpcode() == 128 || hdmiCecMessage.getOpcode() == 134 || hdmiCecMessage.getOpcode() == 130) {
                removeAction(com.android.server.hdmi.ActiveSourceAction.class);
                removeAction(com.android.server.hdmi.OneTouchPlayAction.class);
                return;
            }
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int findKeyReceiverAddress() {
        return 0;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int findAudioReceiverAddress() {
        if (this.mService.isSystemAudioActivated()) {
            return 5;
        }
        return 0;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource, com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void disableDevice(boolean z, com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback) {
        assertRunOnServiceThread();
        removeAction(com.android.server.hdmi.DeviceDiscoveryAction.class);
        removeAction(com.android.server.hdmi.HotplugDetectionAction.class);
        removeAction(com.android.server.hdmi.NewDeviceAction.class);
        super.disableDevice(z, pendingActionClearedCallback);
        clearDeviceInfoList();
        checkIfPendingActionsCleared();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        super.dump(indentingPrintWriter);
        indentingPrintWriter.println("isActiveSource(): " + isActiveSource());
    }

    private class SystemWakeLock implements com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock {
        private final com.android.server.hdmi.WakeLockWrapper mWakeLock;

        public SystemWakeLock() {
            this.mWakeLock = com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.mService.getPowerManager().newWakeLock(1, com.android.server.hdmi.HdmiCecLocalDevicePlayback.TAG);
            this.mWakeLock.setReferenceCounted(false);
        }

        @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
        public void acquire() {
            this.mWakeLock.acquire();
            com.android.server.hdmi.HdmiLogger.debug("active source: %b. Wake lock acquired", java.lang.Boolean.valueOf(com.android.server.hdmi.HdmiCecLocalDevicePlayback.this.isActiveSource()));
        }

        @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
        public void release() {
            this.mWakeLock.release();
            com.android.server.hdmi.HdmiLogger.debug("Wake lock released", new java.lang.Object[0]);
        }

        @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
        public boolean isHeld() {
            return this.mWakeLock.isHeld();
        }
    }
}
