package com.android.server.hdmi;

/* loaded from: classes2.dex */
abstract class RequestArcAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    protected static final int STATE_WATING_FOR_REQUEST_ARC_REQUEST_RESPONSE = 1;
    private static final java.lang.String TAG = "RequestArcAction";
    protected final int mAvrAddress;

    RequestArcAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDevice, iHdmiControlCallback);
        com.android.server.hdmi.HdmiUtils.verifyAddressType(getSourceAddress(), 0);
        com.android.server.hdmi.HdmiUtils.verifyAddressType(i, 5);
        this.mAvrAddress = i;
    }

    RequestArcAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i) {
        this(hdmiCecLocalDevice, i, null);
    }

    protected final void disableArcTransmission() {
        addAndStartAction(new com.android.server.hdmi.SetArcTransmissionStateAction(localDevice(), this.mAvrAddress, false));
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    final void handleTimerEvent(int i) {
        if (this.mState != i || i != 1) {
            return;
        }
        com.android.server.hdmi.HdmiLogger.debug("[T] RequestArcAction.", new java.lang.Object[0]);
        disableArcTransmission();
        finishWithCallback(1);
    }
}
