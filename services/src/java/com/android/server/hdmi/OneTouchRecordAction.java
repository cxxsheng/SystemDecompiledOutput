package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class OneTouchRecordAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int RECORD_STATUS_TIMEOUT_MS = 120000;
    private static final int STATE_RECORDING_IN_PROGRESS = 2;
    private static final int STATE_WAITING_FOR_RECORD_STATUS = 1;
    private static final java.lang.String TAG = "OneTouchRecordAction";
    private final byte[] mRecordSource;
    private final int mRecorderAddress;

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public /* bridge */ /* synthetic */ void addCallback(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super.addCallback(iHdmiControlCallback);
    }

    OneTouchRecordAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, byte[] bArr) {
        super(hdmiCecLocalDevice);
        this.mRecorderAddress = i;
        this.mRecordSource = bArr;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        sendRecordOn();
        return true;
    }

    private void sendRecordOn() {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildRecordOn(getSourceAddress(), this.mRecorderAddress, this.mRecordSource), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.OneTouchRecordAction.1
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public void onSendCompleted(int i) {
                if (i != 0) {
                    com.android.server.hdmi.OneTouchRecordAction.this.tv().announceOneTouchRecordResult(com.android.server.hdmi.OneTouchRecordAction.this.mRecorderAddress, 49);
                    com.android.server.hdmi.OneTouchRecordAction.this.finish();
                }
            }
        });
        this.mState = 1;
        addTimer(this.mState, RECORD_STATUS_TIMEOUT_MS);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1 || this.mRecorderAddress != hdmiCecMessage.getSource()) {
            return false;
        }
        switch (hdmiCecMessage.getOpcode()) {
            case 10:
                return handleRecordStatus(hdmiCecMessage);
            default:
                return false;
        }
    }

    private boolean handleRecordStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.getSource() != this.mRecorderAddress) {
            return false;
        }
        byte b = hdmiCecMessage.getParams()[0];
        tv().announceOneTouchRecordResult(this.mRecorderAddress, b);
        android.util.Slog.i(TAG, "Got record status:" + ((int) b) + " from " + hdmiCecMessage.getSource());
        switch (b) {
            case 1:
            case 2:
            case 3:
            case 4:
                this.mState = 2;
                this.mActionTimer.clearTimerMessage();
                return true;
            default:
                finish();
                return true;
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState != i) {
            android.util.Slog.w(TAG, "Timeout in invalid state:[Expected:" + this.mState + ", Actual:" + i + "]");
            return;
        }
        tv().announceOneTouchRecordResult(this.mRecorderAddress, 49);
        finish();
    }

    int getRecorderAddress() {
        return this.mRecorderAddress;
    }
}
