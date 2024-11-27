package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class DevicePowerStatusAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int STATE_WAITING_FOR_REPORT_POWER_STATUS = 1;
    private static final java.lang.String TAG = "DevicePowerStatusAction";
    private int mRetriesOnTimeout;
    private final int mTargetAddress;

    static com.android.server.hdmi.DevicePowerStatusAction create(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        if (hdmiCecLocalDevice == null || iHdmiControlCallback == null) {
            android.util.Slog.e(TAG, "Wrong arguments");
            return null;
        }
        return new com.android.server.hdmi.DevicePowerStatusAction(hdmiCecLocalDevice, i, iHdmiControlCallback);
    }

    private DevicePowerStatusAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDevice, iHdmiControlCallback);
        this.mRetriesOnTimeout = 1;
        this.mTargetAddress = i;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo;
        int devicePowerStatus;
        com.android.server.hdmi.HdmiControlService hdmiControlService = localDevice().mService;
        if (hdmiControlService.getCecVersion() >= 6 && (cecDeviceInfo = hdmiControlService.getHdmiCecNetwork().getCecDeviceInfo(this.mTargetAddress)) != null && cecDeviceInfo.getCecVersion() >= 6 && (devicePowerStatus = cecDeviceInfo.getDevicePowerStatus()) != -1) {
            finishWithCallback(devicePowerStatus);
            return true;
        }
        queryDevicePowerStatus();
        this.mState = 1;
        addTimer(this.mState, 2000);
        return true;
    }

    private void queryDevicePowerStatus() {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveDevicePowerStatus(getSourceAddress(), this.mTargetAddress), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.DevicePowerStatusAction$$ExternalSyntheticLambda0
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public final void onSendCompleted(int i) {
                com.android.server.hdmi.DevicePowerStatusAction.this.lambda$queryDevicePowerStatus$0(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDevicePowerStatus$0(int i) {
        if (i == 1) {
            finishWithCallback(-1);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1 || this.mTargetAddress != hdmiCecMessage.getSource() || hdmiCecMessage.getOpcode() != 144) {
            return false;
        }
        finishWithCallback(hdmiCecMessage.getParams()[0]);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState == i && i == 1) {
            if (this.mRetriesOnTimeout > 0) {
                this.mRetriesOnTimeout--;
                start();
            } else {
                finishWithCallback(-1);
            }
        }
    }
}
