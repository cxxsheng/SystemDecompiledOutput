package com.android.server.hdmi;

/* loaded from: classes2.dex */
abstract class HdmiCecLocalDeviceSource extends com.android.server.hdmi.HdmiCecLocalDevice {
    private static final java.lang.String TAG = "HdmiCecLocalDeviceSource";
    protected boolean mIsSwitchDevice;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.server.hdmi.Constants.LocalActivePort
    protected int mLocalActivePort;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean mRoutingControlFeatureEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.server.hdmi.Constants.LocalActivePort
    private int mRoutingPort;

    protected HdmiCecLocalDeviceSource(com.android.server.hdmi.HdmiControlService hdmiControlService, int i) {
        super(hdmiControlService, i);
        this.mIsSwitchDevice = ((java.lang.Boolean) android.sysprop.HdmiProperties.is_switch().orElse(false)).booleanValue();
        this.mRoutingPort = 0;
        this.mLocalActivePort = 0;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void queryDisplayStatus(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        java.util.List actions = getActions(com.android.server.hdmi.DevicePowerStatusAction.class);
        if (!actions.isEmpty()) {
            android.util.Slog.i(TAG, "queryDisplayStatus already in progress");
            ((com.android.server.hdmi.DevicePowerStatusAction) actions.get(0)).addCallback(iHdmiControlCallback);
            return;
        }
        com.android.server.hdmi.DevicePowerStatusAction create = com.android.server.hdmi.DevicePowerStatusAction.create(this, 0, iHdmiControlCallback);
        if (create == null) {
            android.util.Slog.w(TAG, "Cannot initiate queryDisplayStatus");
            invokeCallback(iHdmiControlCallback, -1);
        } else {
            addAndStartAction(create);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void onHotplug(int i, boolean z) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiPortInfo portInfo = this.mService.getPortInfo(i);
        if (portInfo != null && portInfo.getType() == 1) {
            this.mCecMessageCache.flushAll();
        }
        if (z) {
            this.mService.wakeUp();
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void sendStandby(int i) {
        assertRunOnServiceThread();
        java.lang.String stringValue = this.mService.getHdmiCecConfig().getStringValue("power_control_mode");
        if (stringValue.equals("broadcast")) {
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildStandby(getDeviceInfo().getLogicalAddress(), 15));
            return;
        }
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildStandby(getDeviceInfo().getLogicalAddress(), 0));
        if (stringValue.equals("to_tv_and_audio_system")) {
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildStandby(getDeviceInfo().getLogicalAddress(), 5));
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void oneTouchPlay(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        java.util.List actions = getActions(com.android.server.hdmi.OneTouchPlayAction.class);
        if (!actions.isEmpty()) {
            android.util.Slog.i(TAG, "oneTouchPlay already in progress");
            ((com.android.server.hdmi.OneTouchPlayAction) actions.get(0)).addCallback(iHdmiControlCallback);
            return;
        }
        com.android.server.hdmi.OneTouchPlayAction create = com.android.server.hdmi.OneTouchPlayAction.create(this, 0, iHdmiControlCallback);
        if (create == null) {
            android.util.Slog.w(TAG, "Cannot initiate oneTouchPlay");
            invokeCallback(iHdmiControlCallback, 5);
        } else {
            addAndStartAction(create);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void toggleAndFollowTvPower() {
        assertRunOnServiceThread();
        if (this.mService.getPowerManager().isInteractive()) {
            this.mService.pauseActiveMediaSessions();
        } else {
            this.mService.wakeUp();
        }
        this.mService.queryDisplayStatus(new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceSource.1
            public void onComplete(int i) {
                if (i == -1) {
                    android.util.Slog.i(com.android.server.hdmi.HdmiCecLocalDeviceSource.TAG, "TV power toggle: TV power status unknown");
                    com.android.server.hdmi.HdmiCecLocalDeviceSource.this.sendUserControlPressedAndReleased(0, 107);
                    return;
                }
                if (i == 0 || i == 2) {
                    android.util.Slog.i(com.android.server.hdmi.HdmiCecLocalDeviceSource.TAG, "TV power toggle: turning off TV");
                    com.android.server.hdmi.HdmiCecLocalDeviceSource.this.sendStandby(0);
                    com.android.server.hdmi.HdmiCecLocalDeviceSource.this.mService.standby();
                } else if (i == 1 || i == 3) {
                    android.util.Slog.i(com.android.server.hdmi.HdmiCecLocalDeviceSource.TAG, "TV power toggle: turning on TV");
                    com.android.server.hdmi.HdmiCecLocalDeviceSource.this.oneTouchPlay(new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceSource.1.1
                        public void onComplete(int i2) {
                            if (i2 != 0) {
                                android.util.Slog.w(com.android.server.hdmi.HdmiCecLocalDeviceSource.TAG, "Failed to complete One Touch Play. result=" + i2);
                                com.android.server.hdmi.HdmiCecLocalDeviceSource.this.sendUserControlPressedAndReleased(0, 107);
                            }
                        }
                    });
                }
            }
        });
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void onActiveSourceLost() {
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setActiveSource(int i, int i2, java.lang.String str) {
        boolean isActiveSource = isActiveSource();
        super.setActiveSource(i, i2, str);
        if (isActiveSource && !isActiveSource()) {
            onActiveSourceLost();
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void setActiveSource(int i, java.lang.String str) {
        assertRunOnServiceThread();
        setActiveSource(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource.of(-1, i), str);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleActiveSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int source = hdmiCecMessage.getSource();
        int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(hdmiCecMessage.getParams());
        com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource of = com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource.of(source, twoBytesToInt);
        if (!getActiveSource().equals(of)) {
            setActiveSource(of, "HdmiCecLocalDeviceSource#handleActiveSource()");
        }
        updateDevicePowerStatus(source, 0);
        if (isRoutingControlFeatureEnabled()) {
            switchInputOnReceivingNewActivePath(twoBytesToInt);
            return -1;
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRequestActiveSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        maySendActiveSource(hdmiCecMessage.getSource());
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleSetStreamPath(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(hdmiCecMessage.getParams());
        if (twoBytesToInt == this.mService.getPhysicalAddress() && this.mService.isPlaybackDevice()) {
            setAndBroadcastActiveSource(hdmiCecMessage, twoBytesToInt, "HdmiCecLocalDeviceSource#handleSetStreamPath()");
        } else if (twoBytesToInt != this.mService.getPhysicalAddress() || !isActiveSource()) {
            setActiveSource(twoBytesToInt, "HdmiCecLocalDeviceSource#handleSetStreamPath()");
        }
        switchInputOnReceivingNewActivePath(twoBytesToInt);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRoutingChange(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(hdmiCecMessage.getParams(), 2);
        if (twoBytesToInt != this.mService.getPhysicalAddress() || !isActiveSource()) {
            setActiveSource(twoBytesToInt, "HdmiCecLocalDeviceSource#handleRoutingChange()");
        }
        if (!isRoutingControlFeatureEnabled()) {
            return 4;
        }
        handleRoutingChangeAndInformation(twoBytesToInt, hdmiCecMessage);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRoutingInformation(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(hdmiCecMessage.getParams());
        if (twoBytesToInt != this.mService.getPhysicalAddress() || !isActiveSource()) {
            setActiveSource(twoBytesToInt, "HdmiCecLocalDeviceSource#handleRoutingInformation()");
        }
        if (!isRoutingControlFeatureEnabled()) {
            return 4;
        }
        handleRoutingChangeAndInformation(twoBytesToInt, hdmiCecMessage);
        return -1;
    }

    protected void switchInputOnReceivingNewActivePath(int i) {
    }

    protected void handleRoutingChangeAndInformation(int i, com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void disableDevice(boolean z, com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback) {
        removeAction(com.android.server.hdmi.OneTouchPlayAction.class);
        removeAction(com.android.server.hdmi.DevicePowerStatusAction.class);
        super.disableDevice(z, pendingActionClearedCallback);
    }

    protected void updateDevicePowerStatus(int i, int i2) {
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.Constants.RcProfile
    protected int getRcProfile() {
        return 1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected java.util.List<java.lang.Integer> getRcFeatures() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.hdmi.HdmiCecConfig hdmiCecConfig = this.mService.getHdmiCecConfig();
        if (hdmiCecConfig.getIntValue("rc_profile_source_handles_root_menu") == 1) {
            arrayList.add(4);
        }
        if (hdmiCecConfig.getIntValue("rc_profile_source_handles_setup_menu") == 1) {
            arrayList.add(3);
        }
        if (hdmiCecConfig.getIntValue("rc_profile_source_handles_contents_menu") == 1) {
            arrayList.add(2);
        }
        if (hdmiCecConfig.getIntValue("rc_profile_source_handles_top_menu") == 1) {
            arrayList.add(1);
        }
        if (hdmiCecConfig.getIntValue("rc_profile_source_handles_media_context_sensitive_menu") == 1) {
            arrayList.add(0);
        }
        return arrayList;
    }

    protected void setAndBroadcastActiveSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, int i, java.lang.String str) {
        this.mService.setAndBroadcastActiveSource(i, getDeviceInfo().getDeviceType(), hdmiCecMessage.getSource(), str);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected boolean isActiveSource() {
        if (getDeviceInfo() == null) {
            return false;
        }
        return getActiveSource().equals(getDeviceInfo().getLogicalAddress(), getDeviceInfo().getPhysicalAddress());
    }

    protected void wakeUpIfActiveSource() {
        if (!isActiveSource()) {
            return;
        }
        this.mService.wakeUp();
    }

    protected void maySendActiveSource(int i) {
        if (!isActiveSource()) {
            return;
        }
        addAndStartAction(new com.android.server.hdmi.ActiveSourceAction(this, i));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void setRoutingPort(@com.android.server.hdmi.Constants.LocalActivePort int i) {
        synchronized (this.mLock) {
            this.mRoutingPort = i;
        }
    }

    @com.android.server.hdmi.Constants.LocalActivePort
    protected int getRoutingPort() {
        int i;
        synchronized (this.mLock) {
            i = this.mRoutingPort;
        }
        return i;
    }

    @com.android.server.hdmi.Constants.LocalActivePort
    protected int getLocalActivePort() {
        int i;
        synchronized (this.mLock) {
            i = this.mLocalActivePort;
        }
        return i;
    }

    protected void setLocalActivePort(@com.android.server.hdmi.Constants.LocalActivePort int i) {
        synchronized (this.mLock) {
            this.mLocalActivePort = i;
        }
    }

    boolean isRoutingControlFeatureEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mRoutingControlFeatureEnabled;
        }
        return z;
    }

    protected boolean isSwitchingToTheSameInput(@com.android.server.hdmi.Constants.LocalActivePort int i) {
        return i == getLocalActivePort();
    }
}
