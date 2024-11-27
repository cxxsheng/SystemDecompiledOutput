package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class RequestActiveSourceAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int MAX_SEND_RETRY_COUNT = 1;
    private static final int STATE_WAIT_FOR_ACTIVE_SOURCE = 1;
    private static final java.lang.String TAG = "RequestActiveSourceAction";
    private int mSendRetryCount;

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public /* bridge */ /* synthetic */ void addCallback(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super.addCallback(iHdmiControlCallback);
    }

    RequestActiveSourceAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDevice, iHdmiControlCallback);
        this.mSendRetryCount = 0;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        android.util.Slog.v(TAG, "RequestActiveSourceAction started.");
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildRequestActiveSource(getSourceAddress()));
        this.mState = 1;
        addTimer(this.mState, 2000);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.getOpcode() == 130) {
            finishWithCallback(0);
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState == i && this.mState == 1) {
            int i2 = this.mSendRetryCount;
            this.mSendRetryCount = i2 + 1;
            if (i2 < 1) {
                sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildRequestActiveSource(getSourceAddress()));
                addTimer(this.mState, 2000);
            } else {
                finishWithCallback(1);
            }
        }
    }
}
