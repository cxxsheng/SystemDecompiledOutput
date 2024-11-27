package com.android.server.hdmi;

/* loaded from: classes2.dex */
abstract class HdmiLocalDevice {
    private static final java.lang.String TAG = "HdmiLocalDevice";
    protected final int mDeviceType;
    protected final java.lang.Object mLock;
    protected final com.android.server.hdmi.HdmiControlService mService;

    protected HdmiLocalDevice(com.android.server.hdmi.HdmiControlService hdmiControlService, int i) {
        this.mService = hdmiControlService;
        this.mDeviceType = i;
        this.mLock = hdmiControlService.getServiceLock();
    }
}
