package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class SetArcTransmissionStateAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int STATE_WAITING_TIMEOUT = 1;
    private static final java.lang.String TAG = "SetArcTransmissionStateAction";
    private final int mAvrAddress;
    private final boolean mEnabled;

    SetArcTransmissionStateAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, boolean z) {
        super(hdmiCecLocalDevice);
        com.android.server.hdmi.HdmiUtils.verifyAddressType(getSourceAddress(), 0);
        com.android.server.hdmi.HdmiUtils.verifyAddressType(i, 5);
        this.mAvrAddress = i;
        this.mEnabled = z;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        if (this.mEnabled) {
            addAndStartAction(new com.android.server.hdmi.RequestSadAction(localDevice(), 5, new com.android.server.hdmi.RequestSadAction.RequestSadCallback() { // from class: com.android.server.hdmi.SetArcTransmissionStateAction.1
                @Override // com.android.server.hdmi.RequestSadAction.RequestSadCallback
                public void onRequestSadDone(java.util.List<byte[]> list) {
                    android.util.Slog.i(com.android.server.hdmi.SetArcTransmissionStateAction.TAG, "Enabling ARC");
                    com.android.server.hdmi.SetArcTransmissionStateAction.this.tv().enableArc(list);
                    com.android.server.hdmi.SetArcTransmissionStateAction.this.mState = 1;
                    com.android.server.hdmi.SetArcTransmissionStateAction.this.addTimer(com.android.server.hdmi.SetArcTransmissionStateAction.this.mState, 2000);
                    com.android.server.hdmi.SetArcTransmissionStateAction.this.sendReportArcInitiated();
                }
            }));
            return true;
        }
        disableArc();
        finish();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendReportArcInitiated() {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportArcInitiated(getSourceAddress(), this.mAvrAddress), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.SetArcTransmissionStateAction.2
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public void onSendCompleted(int i) {
                switch (i) {
                    case 1:
                        com.android.server.hdmi.SetArcTransmissionStateAction.this.disableArc();
                        com.android.server.hdmi.HdmiLogger.debug("Failed to send <Report Arc Initiated>.", new java.lang.Object[0]);
                        com.android.server.hdmi.SetArcTransmissionStateAction.this.finish();
                        break;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableArc() {
        android.util.Slog.i(TAG, "Disabling ARC");
        tv().disableArc();
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportArcTerminated(getSourceAddress(), this.mAvrAddress));
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1 || hdmiCecMessage.getOpcode() != 0 || (hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) != 193) {
            return false;
        }
        com.android.server.hdmi.HdmiLogger.debug("Feature aborted for <Report Arc Initiated>", new java.lang.Object[0]);
        disableArc();
        finish();
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState != i || this.mState != 1) {
            return;
        }
        finish();
    }
}
