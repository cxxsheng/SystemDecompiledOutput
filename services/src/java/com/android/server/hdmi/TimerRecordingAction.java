package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class TimerRecordingAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int STATE_WAITING_FOR_TIMER_STATUS = 1;
    private static final java.lang.String TAG = "TimerRecordingAction";
    private static final int TIMER_STATUS_TIMEOUT_MS = 120000;
    private final byte[] mRecordSource;
    private final int mRecorderAddress;
    private final int mSourceType;

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public /* bridge */ /* synthetic */ void addCallback(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super.addCallback(iHdmiControlCallback);
    }

    TimerRecordingAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, int i2, byte[] bArr) {
        super(hdmiCecLocalDevice);
        this.mRecorderAddress = i;
        this.mSourceType = i2;
        this.mRecordSource = bArr;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        sendTimerMessage();
        return true;
    }

    private void sendTimerMessage() {
        com.android.server.hdmi.HdmiCecMessage buildSetDigitalTimer;
        switch (this.mSourceType) {
            case 1:
                buildSetDigitalTimer = com.android.server.hdmi.HdmiCecMessageBuilder.buildSetDigitalTimer(getSourceAddress(), this.mRecorderAddress, this.mRecordSource);
                break;
            case 2:
                buildSetDigitalTimer = com.android.server.hdmi.HdmiCecMessageBuilder.buildSetAnalogueTimer(getSourceAddress(), this.mRecorderAddress, this.mRecordSource);
                break;
            case 3:
                buildSetDigitalTimer = com.android.server.hdmi.HdmiCecMessageBuilder.buildSetExternalTimer(getSourceAddress(), this.mRecorderAddress, this.mRecordSource);
                break;
            default:
                tv().announceTimerRecordingResult(this.mRecorderAddress, 2);
                finish();
                return;
        }
        sendCommand(buildSetDigitalTimer, new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.TimerRecordingAction.1
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public void onSendCompleted(int i) {
                if (i != 0) {
                    com.android.server.hdmi.TimerRecordingAction.this.tv().announceTimerRecordingResult(com.android.server.hdmi.TimerRecordingAction.this.mRecorderAddress, 1);
                    com.android.server.hdmi.TimerRecordingAction.this.finish();
                } else {
                    com.android.server.hdmi.TimerRecordingAction.this.mState = 1;
                    com.android.server.hdmi.TimerRecordingAction.this.addTimer(com.android.server.hdmi.TimerRecordingAction.this.mState, com.android.server.hdmi.TimerRecordingAction.TIMER_STATUS_TIMEOUT_MS);
                }
            }
        });
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1 || hdmiCecMessage.getSource() != this.mRecorderAddress) {
            return false;
        }
        switch (hdmiCecMessage.getOpcode()) {
            case 0:
                return handleFeatureAbort(hdmiCecMessage);
            case 53:
                return handleTimerStatus(hdmiCecMessage);
            default:
                return false;
        }
    }

    private boolean handleTimerStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        byte[] params = hdmiCecMessage.getParams();
        if (params.length == 1 || params.length == 3) {
            tv().announceTimerRecordingResult(this.mRecorderAddress, bytesToInt(params));
            android.util.Slog.i(TAG, "Received [Timer Status Data]:" + java.util.Arrays.toString(params));
        } else {
            android.util.Slog.w(TAG, "Invalid [Timer Status Data]:" + java.util.Arrays.toString(params));
        }
        finish();
        return true;
    }

    private boolean handleFeatureAbort(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        byte[] params = hdmiCecMessage.getParams();
        int i = params[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
        switch (i) {
            case 52:
            case 151:
            case 162:
                android.util.Slog.i(TAG, "[Feature Abort] for " + i + " reason:" + (params[1] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE));
                tv().announceTimerRecordingResult(this.mRecorderAddress, 1);
                finish();
                return true;
            default:
                return false;
        }
    }

    private static int bytesToInt(byte[] bArr) {
        if (bArr.length > 4) {
            throw new java.lang.IllegalArgumentException("Invalid data size:" + java.util.Arrays.toString(bArr));
        }
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            i |= (bArr[i2] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) << ((3 - i2) * 8);
        }
        return i;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState != i) {
            android.util.Slog.w(TAG, "Timeout in invalid state:[Expected:" + this.mState + ", Actual:" + i + "]");
            return;
        }
        tv().announceTimerRecordingResult(this.mRecorderAddress, 1);
        finish();
    }
}
