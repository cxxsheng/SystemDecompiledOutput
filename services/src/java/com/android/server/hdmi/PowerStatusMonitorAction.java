package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class PowerStatusMonitorAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int INVALID_POWER_STATUS = -2;
    private static final int MONITORING_INTERVAL_MS = 60000;
    private static final int REPORT_POWER_STATUS_TIMEOUT_MS = 5000;
    private static final int STATE_WAIT_FOR_NEXT_MONITORING = 2;
    private static final int STATE_WAIT_FOR_REPORT_POWER_STATUS = 1;
    private static final java.lang.String TAG = "PowerStatusMonitorAction";
    private final android.util.SparseIntArray mPowerStatus;

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public /* bridge */ /* synthetic */ void addCallback(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super.addCallback(iHdmiControlCallback);
    }

    PowerStatusMonitorAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
        super(hdmiCecLocalDevice);
        this.mPowerStatus = new android.util.SparseIntArray();
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        queryPowerStatus();
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mState == 1 && hdmiCecMessage.getOpcode() == 144) {
            return handleReportPowerStatus(hdmiCecMessage);
        }
        return false;
    }

    private boolean handleReportPowerStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        int source = hdmiCecMessage.getSource();
        if (this.mPowerStatus.get(source, -2) == -2) {
            return false;
        }
        updatePowerStatus(source, hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE, true);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        switch (this.mState) {
            case 1:
                handleTimeout();
                break;
            case 2:
                queryPowerStatus();
                break;
        }
    }

    private void handleTimeout() {
        for (int i = 0; i < this.mPowerStatus.size(); i++) {
            updatePowerStatus(this.mPowerStatus.keyAt(i), -1, false);
        }
        this.mPowerStatus.clear();
        this.mState = 2;
    }

    private void resetPowerStatus(java.util.List<android.hardware.hdmi.HdmiDeviceInfo> list) {
        this.mPowerStatus.clear();
        for (android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo : list) {
            if (localDevice().mService.getCecVersion() < 6 || hdmiDeviceInfo.getCecVersion() < 6) {
                this.mPowerStatus.append(hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getDevicePowerStatus());
            }
        }
    }

    private void queryPowerStatus() {
        java.util.List<android.hardware.hdmi.HdmiDeviceInfo> deviceInfoList = localDevice().mService.getHdmiCecNetwork().getDeviceInfoList(false);
        resetPowerStatus(deviceInfoList);
        for (android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo : deviceInfoList) {
            if (localDevice().mService.getCecVersion() < 6 || hdmiDeviceInfo.getCecVersion() < 6) {
                final int logicalAddress = hdmiDeviceInfo.getLogicalAddress();
                sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveDevicePowerStatus(getSourceAddress(), logicalAddress), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.PowerStatusMonitorAction.1
                    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                    public void onSendCompleted(int i) {
                        if (i != 0) {
                            com.android.server.hdmi.PowerStatusMonitorAction.this.updatePowerStatus(logicalAddress, -1, true);
                        }
                    }
                });
            }
        }
        this.mState = 1;
        addTimer(2, 60000);
        addTimer(1, 5000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePowerStatus(int i, int i2, boolean z) {
        localDevice().mService.getHdmiCecNetwork().updateDevicePowerStatus(i, i2);
        if (z) {
            this.mPowerStatus.delete(i);
        }
    }
}
