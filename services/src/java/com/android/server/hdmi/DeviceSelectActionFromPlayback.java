package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class DeviceSelectActionFromPlayback extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int LOOP_COUNTER_MAX = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_WAIT_FOR_ACTIVE_SOURCE_MESSAGE_AFTER_ROUTING_CHANGE = 4;

    @com.android.internal.annotations.VisibleForTesting
    private static final int STATE_WAIT_FOR_ACTIVE_SOURCE_MESSAGE_AFTER_SET_STREAM_PATH = 5;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_WAIT_FOR_DEVICE_POWER_ON = 3;
    private static final int STATE_WAIT_FOR_DEVICE_TO_TRANSIT_TO_STANDBY = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_WAIT_FOR_REPORT_POWER_STATUS = 1;
    private static final java.lang.String TAG = "DeviceSelectActionFromPlayback";
    private static final int TIMEOUT_POWER_ON_MS = 5000;
    private static final int TIMEOUT_TRANSIT_TO_STANDBY_MS = 5000;
    private final com.android.server.hdmi.HdmiCecMessage mGivePowerStatus;
    private final boolean mIsCec20;
    private int mPowerStatusCounter;
    private final android.hardware.hdmi.HdmiDeviceInfo mTarget;

    DeviceSelectActionFromPlayback(com.android.server.hdmi.HdmiCecLocalDevicePlayback hdmiCecLocalDevicePlayback, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        this(hdmiCecLocalDevicePlayback, hdmiDeviceInfo, iHdmiControlCallback, hdmiCecLocalDevicePlayback.getDeviceInfo().getCecVersion() >= 6 && hdmiDeviceInfo.getCecVersion() >= 6);
    }

    @com.android.internal.annotations.VisibleForTesting
    DeviceSelectActionFromPlayback(com.android.server.hdmi.HdmiCecLocalDevicePlayback hdmiCecLocalDevicePlayback, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback, boolean z) {
        super(hdmiCecLocalDevicePlayback, iHdmiControlCallback);
        this.mPowerStatusCounter = 0;
        this.mTarget = hdmiDeviceInfo;
        this.mGivePowerStatus = com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveDevicePowerStatus(getSourceAddress(), getTargetAddress());
        this.mIsCec20 = z;
    }

    int getTargetAddress() {
        return this.mTarget.getLogicalAddress();
    }

    private int getTargetPath() {
        return this.mTarget.getPhysicalAddress();
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean start() {
        int i;
        sendRoutingChange();
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
                this.mState = 4;
                addTimer(this.mState, 2000);
                return true;
            }
        }
        this.mState = 1;
        addTimer(this.mState, 2000);
        return true;
    }

    private void queryDevicePowerStatus() {
        sendCommand(this.mGivePowerStatus, new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.DeviceSelectActionFromPlayback.1
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public void onSendCompleted(int i) {
                if (i != 0) {
                    com.android.server.hdmi.DeviceSelectActionFromPlayback.this.finishWithCallback(7);
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
        if (opcode == 130) {
            finishWithCallback(0);
            return true;
        }
        if (this.mState == 1 && opcode == 144) {
            return handleReportPowerStatus(params[0]);
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
                    sendRoutingChange();
                    this.mState = 3;
                    addTimer(this.mState, 5000);
                    break;
                } else {
                    selectDevice();
                    break;
                }
            case 2:
                if (this.mPowerStatusCounter < 2) {
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

    private void selectDevice() {
        sendRoutingChange();
        this.mState = 4;
        addTimer(this.mState, 2000);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState != i) {
            android.util.Slog.w(TAG, "Timer in a wrong state. Ignored.");
        }
        switch (this.mState) {
            case 1:
                selectDevice();
                addTimer(this.mState, 2000);
                break;
            case 3:
                this.mPowerStatusCounter++;
                queryDevicePowerStatus();
                this.mState = 1;
                addTimer(this.mState, 2000);
                break;
            case 4:
                sendSetStreamPath();
                this.mState = 5;
                addTimer(this.mState, 2000);
                break;
            case 5:
                finishWithCallback(1);
                break;
        }
    }

    private void sendRoutingChange() {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildRoutingChange(getSourceAddress(), playback().getActiveSource().physicalAddress, getTargetPath()));
    }

    private void sendSetStreamPath() {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildSetStreamPath(getSourceAddress(), getTargetPath()));
    }
}
