package com.android.server.hdmi;

/* loaded from: classes2.dex */
abstract class HdmiEarcLocalDevice extends com.android.server.hdmi.HdmiLocalDevice {
    private static final java.lang.String TAG = "HdmiEarcLocalDevice";

    @com.android.server.hdmi.Constants.EarcStatus
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected int mEarcStatus;

    protected abstract void handleEarcCapabilitiesReported(byte[] bArr);

    protected abstract void handleEarcStateChange(@com.android.server.hdmi.Constants.EarcStatus int i);

    protected HdmiEarcLocalDevice(com.android.server.hdmi.HdmiControlService hdmiControlService, int i) {
        super(hdmiControlService, i);
    }

    static com.android.server.hdmi.HdmiEarcLocalDevice create(com.android.server.hdmi.HdmiControlService hdmiControlService, int i) {
        switch (i) {
            case 0:
                return new com.android.server.hdmi.HdmiEarcLocalDeviceTx(hdmiControlService);
            default:
                return null;
        }
    }

    protected void disableDevice() {
    }

    protected void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
    }
}
