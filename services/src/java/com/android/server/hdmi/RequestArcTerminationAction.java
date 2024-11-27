package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class RequestArcTerminationAction extends com.android.server.hdmi.RequestArcAction {
    private static final java.lang.String TAG = "RequestArcTerminationAction";

    RequestArcTerminationAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i) {
        super(hdmiCecLocalDevice, i);
    }

    RequestArcTerminationAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDevice, i, iHdmiControlCallback);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        this.mState = 1;
        addTimer(this.mState, 2000);
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildRequestArcTermination(getSourceAddress(), this.mAvrAddress), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.RequestArcTerminationAction.1
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public void onSendCompleted(int i) {
                if (i != 0) {
                    com.android.server.hdmi.RequestArcTerminationAction.this.disableArcTransmission();
                    com.android.server.hdmi.RequestArcTerminationAction.this.finishWithCallback(3);
                }
            }
        });
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1 || !com.android.server.hdmi.HdmiUtils.checkCommandSource(hdmiCecMessage, this.mAvrAddress, TAG)) {
            return false;
        }
        switch (hdmiCecMessage.getOpcode()) {
            case 0:
                if ((hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) == 196) {
                    disableArcTransmission();
                    finishWithCallback(3);
                    break;
                }
                break;
            case 197:
                finishWithCallback(0);
                break;
        }
        return false;
    }
}
