package com.android.server.hdmi;

/* loaded from: classes2.dex */
class HdmiCecPowerStatusController {
    private final com.android.server.hdmi.HdmiControlService mHdmiControlService;
    private int mPowerStatus = 1;

    HdmiCecPowerStatusController(com.android.server.hdmi.HdmiControlService hdmiControlService) {
        this.mHdmiControlService = hdmiControlService;
    }

    int getPowerStatus() {
        return this.mPowerStatus;
    }

    boolean isPowerStatusOn() {
        return this.mPowerStatus == 0;
    }

    boolean isPowerStatusStandby() {
        return this.mPowerStatus == 1;
    }

    boolean isPowerStatusTransientToOn() {
        return this.mPowerStatus == 2;
    }

    boolean isPowerStatusTransientToStandby() {
        return this.mPowerStatus == 3;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setPowerStatus(int i) {
        setPowerStatus(i, true);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setPowerStatus(int i, boolean z) {
        if (i == this.mPowerStatus) {
            return;
        }
        this.mPowerStatus = i;
        if (z && this.mHdmiControlService.getCecVersion() >= 6) {
            sendReportPowerStatus(this.mPowerStatus);
        }
    }

    private void sendReportPowerStatus(int i) {
        java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = this.mHdmiControlService.getAllCecLocalDevices().iterator();
        while (it.hasNext()) {
            this.mHdmiControlService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportPowerStatus(it.next().getDeviceInfo().getLogicalAddress(), 15, i));
        }
    }
}
