package com.android.server.hdmi;

/* loaded from: classes2.dex */
abstract class SystemAudioAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int MAX_SEND_RETRY_COUNT = 2;
    private static final int OFF_TIMEOUT_MS = 2000;
    private static final int ON_TIMEOUT_MS = 5000;
    private static final int STATE_CHECK_ROUTING_IN_PRGRESS = 1;
    private static final int STATE_WAIT_FOR_SET_SYSTEM_AUDIO_MODE = 2;
    private static final java.lang.String TAG = "SystemAudioAction";
    protected final int mAvrLogicalAddress;
    private int mSendRetryCount;
    protected boolean mTargetAudioStatus;

    SystemAudioAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, boolean z, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDevice, iHdmiControlCallback);
        this.mSendRetryCount = 0;
        com.android.server.hdmi.HdmiUtils.verifyAddressType(i, 5);
        this.mAvrLogicalAddress = i;
        this.mTargetAudioStatus = z;
    }

    protected void sendSystemAudioModeRequest() {
        java.util.List actions = getActions(com.android.server.hdmi.RoutingControlAction.class);
        if (!actions.isEmpty()) {
            this.mState = 1;
            ((com.android.server.hdmi.RoutingControlAction) actions.get(0)).addOnFinishedCallback(this, new java.lang.Runnable() { // from class: com.android.server.hdmi.SystemAudioAction.1
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.SystemAudioAction.this.sendSystemAudioModeRequestInternal();
                }
            });
        } else {
            sendSystemAudioModeRequestInternal();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSystemAudioModeRequestInternal() {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildSystemAudioModeRequest(getSourceAddress(), this.mAvrLogicalAddress, getSystemAudioModeRequestParam(), this.mTargetAudioStatus), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.SystemAudioAction.2
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public void onSendCompleted(int i) {
                if (i != 0) {
                    com.android.server.hdmi.HdmiLogger.debug("Failed to send <System Audio Mode Request>:" + i, new java.lang.Object[0]);
                    com.android.server.hdmi.SystemAudioAction.this.setSystemAudioMode(false);
                    com.android.server.hdmi.SystemAudioAction.this.finishWithCallback(7);
                }
            }
        });
        this.mState = 2;
        addTimer(this.mState, this.mTargetAudioStatus ? 5000 : 2000);
    }

    private int getSystemAudioModeRequestParam() {
        if (tv().getActiveSource().isValid()) {
            return tv().getActiveSource().physicalAddress;
        }
        int activePath = tv().getActivePath();
        if (activePath != 65535) {
            return activePath;
        }
        return 0;
    }

    private void handleSendSystemAudioModeRequestTimeout() {
        if (this.mTargetAudioStatus) {
            int i = this.mSendRetryCount;
            this.mSendRetryCount = i + 1;
            if (i < 2) {
                sendSystemAudioModeRequest();
                return;
            }
        }
        com.android.server.hdmi.HdmiLogger.debug("[T]:wait for <Set System Audio Mode>.", new java.lang.Object[0]);
        setSystemAudioMode(false);
        finishWithCallback(1);
    }

    protected void setSystemAudioMode(boolean z) {
        tv().setSystemAudioMode(z);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    final boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.getSource() != this.mAvrLogicalAddress) {
            return false;
        }
        switch (this.mState) {
            case 2:
                if (hdmiCecMessage.getOpcode() == 0 && (hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) == 112) {
                    com.android.server.hdmi.HdmiLogger.debug("Failed to start system audio mode request.", new java.lang.Object[0]);
                    setSystemAudioMode(false);
                    finishWithCallback(5);
                    break;
                } else if (hdmiCecMessage.getOpcode() == 114 && com.android.server.hdmi.HdmiUtils.checkCommandSource(hdmiCecMessage, this.mAvrLogicalAddress, TAG)) {
                    boolean parseCommandParamSystemAudioStatus = com.android.server.hdmi.HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage);
                    if (parseCommandParamSystemAudioStatus == this.mTargetAudioStatus) {
                        setSystemAudioMode(parseCommandParamSystemAudioStatus);
                        finish();
                        break;
                    } else {
                        com.android.server.hdmi.HdmiLogger.debug("Unexpected system audio mode request:" + parseCommandParamSystemAudioStatus, new java.lang.Object[0]);
                        finishWithCallback(5);
                        break;
                    }
                }
                break;
        }
        return false;
    }

    protected void removeSystemAudioActionInProgress() {
        removeActionExcept(com.android.server.hdmi.SystemAudioActionFromTv.class, this);
        removeActionExcept(com.android.server.hdmi.SystemAudioActionFromAvr.class, this);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    final void handleTimerEvent(int i) {
        if (this.mState != i) {
        }
        switch (this.mState) {
            case 2:
                handleSendSystemAudioModeRequestTimeout();
                break;
        }
    }
}
