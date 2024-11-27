package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class ArcInitiationActionFromAvr extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int STATE_ARC_INITIATED = 2;
    private static final int STATE_WAITING_FOR_INITIATE_ARC_RESPONSE = 1;
    private static final int TIMEOUT_MS = 1000;

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public /* bridge */ /* synthetic */ void addCallback(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super.addCallback(iHdmiControlCallback);
    }

    ArcInitiationActionFromAvr(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
        super(hdmiCecLocalDevice);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        audioSystem().setArcStatus(true);
        this.mState = 1;
        addTimer(this.mState, 1000);
        sendInitiateArc();
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1) {
            return false;
        }
        switch (hdmiCecMessage.getOpcode()) {
            case 0:
                if ((hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) == 192) {
                    audioSystem().setArcStatus(false);
                    finish();
                    break;
                }
                break;
            case 193:
                this.mState = 2;
                finish();
                break;
            case 194:
                audioSystem().setArcStatus(false);
                finish();
                break;
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState != i) {
        }
        switch (this.mState) {
            case 1:
                handleInitiateArcTimeout();
                break;
        }
    }

    protected void sendInitiateArc() {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildInitiateArc(getSourceAddress(), 0), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.ArcInitiationActionFromAvr$$ExternalSyntheticLambda0
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public final void onSendCompleted(int i) {
                com.android.server.hdmi.ArcInitiationActionFromAvr.this.lambda$sendInitiateArc$0(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendInitiateArc$0(int i) {
        if (i != 0) {
            audioSystem().setArcStatus(false);
            finish();
        }
    }

    private void handleInitiateArcTimeout() {
        com.android.server.hdmi.HdmiLogger.debug("handleInitiateArcTimeout", new java.lang.Object[0]);
        finish();
    }
}
