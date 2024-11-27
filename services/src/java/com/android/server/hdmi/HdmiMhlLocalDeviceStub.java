package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class HdmiMhlLocalDeviceStub {
    private static final android.hardware.hdmi.HdmiDeviceInfo INFO = android.hardware.hdmi.HdmiDeviceInfo.mhlDevice(com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL, -1, -1, -1);
    private final int mPortId;
    private final com.android.server.hdmi.HdmiControlService mService;

    protected HdmiMhlLocalDeviceStub(com.android.server.hdmi.HdmiControlService hdmiControlService, int i) {
        this.mService = hdmiControlService;
        this.mPortId = i;
    }

    void onDeviceRemoved() {
    }

    android.hardware.hdmi.HdmiDeviceInfo getInfo() {
        return INFO;
    }

    void setBusMode(int i) {
    }

    void onBusOvercurrentDetected(boolean z) {
    }

    void setDeviceStatusChange(int i, int i2) {
    }

    int getPortId() {
        return this.mPortId;
    }

    void turnOn(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
    }

    void sendKeyEvent(int i, boolean z) {
    }

    void sendStandby() {
    }
}
