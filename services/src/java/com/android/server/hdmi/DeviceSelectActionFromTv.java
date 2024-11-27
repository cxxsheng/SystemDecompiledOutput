package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class DeviceSelectActionFromTv extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int LOOP_COUNTER_MAX = 20;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_WAIT_FOR_DEVICE_POWER_ON = 3;
    private static final int STATE_WAIT_FOR_DEVICE_TO_TRANSIT_TO_STANDBY = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_WAIT_FOR_REPORT_POWER_STATUS = 1;
    private static final java.lang.String TAG = "DeviceSelect";
    private static final int TIMEOUT_POWER_ON_MS = 5000;
    private static final int TIMEOUT_TRANSIT_TO_STANDBY_MS = 5000;
    private final com.android.server.hdmi.HdmiCecMessage mGivePowerStatus;
    private final boolean mIsCec20;
    private int mPowerStatusCounter;
    private final android.hardware.hdmi.HdmiDeviceInfo mTarget;

    DeviceSelectActionFromTv(com.android.server.hdmi.HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        this(hdmiCecLocalDeviceTv, hdmiDeviceInfo, iHdmiControlCallback, hdmiCecLocalDeviceTv.getDeviceInfo().getCecVersion() >= 6 && hdmiDeviceInfo.getCecVersion() >= 6);
    }

    @com.android.internal.annotations.VisibleForTesting
    DeviceSelectActionFromTv(com.android.server.hdmi.HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback, boolean z) {
        super(hdmiCecLocalDeviceTv, iHdmiControlCallback);
        this.mPowerStatusCounter = 0;
        this.mTarget = hdmiDeviceInfo;
        this.mGivePowerStatus = com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveDevicePowerStatus(getSourceAddress(), getTargetAddress());
        this.mIsCec20 = z;
    }

    int getTargetAddress() {
        return this.mTarget.getLogicalAddress();
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean start() {
        int i;
        sendSetStreamPath();
        if (!this.mIsCec20) {
            queryDevicePowerStatus();
        } else {
            android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = localDevice().mService.getHdmiCecNetwork().getCecDeviceInfo(getTargetAddress());
            if (cecDeviceInfo == null) {
                i = -1;
            } else {
                i = cecDeviceInfo.getDevicePowerStatus();
            }
            if (i == -1) {
                queryDevicePowerStatus();
            } else if (i == 0) {
                finishWithCallback(0);
                return true;
            }
        }
        this.mState = 1;
        addTimer(this.mState, 2000);
        return true;
    }

    private void queryDevicePowerStatus() {
        sendCommand(this.mGivePowerStatus, new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.DeviceSelectActionFromTv.1
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public void onSendCompleted(int i) {
                if (i != 0) {
                    com.android.server.hdmi.DeviceSelectActionFromTv.this.finishWithCallback(7);
                }
            }
        });
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.getSource() != getTargetAddress()) {
            return false;
        }
        int opcode = hdmiCecMessage.getOpcode();
        byte[] params = hdmiCecMessage.getParams();
        switch (this.mState) {
            case 1:
                if (opcode == 144) {
                    break;
                }
                break;
        }
        return false;
    }

    private boolean handleReportPowerStatus(int i) {
        switch (i) {
            case 0:
                selectDevice();
                break;
            case 1:
                if (this.mPowerStatusCounter == 0) {
                    turnOnDevice();
                    this.mState = 3;
                    addTimer(this.mState, 5000);
                    break;
                } else {
                    selectDevice();
                    break;
                }
            case 2:
                if (this.mPowerStatusCounter < 20) {
                    this.mState = 3;
                    addTimer(this.mState, 5000);
                    break;
                } else {
                    selectDevice();
                    break;
                }
            case 3:
                if (this.mPowerStatusCounter < 4) {
                    this.mState = 2;
                    addTimer(this.mState, 5000);
                    break;
                } else {
                    selectDevice();
                    break;
                }
        }
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public void handleTimerEvent(int i) {
        if (this.mState != i) {
            android.util.Slog.w(TAG, "Timer in a wrong state. Ignored.");
        }
        switch (this.mState) {
            case 1:
                if (tv().isPowerStandbyOrTransient()) {
                    finishWithCallback(6);
                    break;
                } else {
                    selectDevice();
                    break;
                }
            case 2:
            case 3:
                this.mPowerStatusCounter++;
                queryDevicePowerStatus();
                this.mState = 1;
                addTimer(this.mState, 2000);
                break;
        }
    }

    private void turnOnDevice() {
        if (!this.mIsCec20) {
            sendUserControlPressedAndReleased(this.mTarget.getLogicalAddress(), 64);
            sendUserControlPressedAndReleased(this.mTarget.getLogicalAddress(), 109);
        }
    }

    private void selectDevice() {
        if (!this.mIsCec20) {
            sendSetStreamPath();
        }
        finishWithCallback(0);
    }

    private void sendSetStreamPath() {
        tv().getActiveSource().invalidate();
        tv().setActivePath(this.mTarget.getPhysicalAddress());
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildSetStreamPath(getSourceAddress(), this.mTarget.getPhysicalAddress()));
    }
}
