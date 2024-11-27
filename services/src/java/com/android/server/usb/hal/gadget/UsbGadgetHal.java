package com.android.server.usb.hal.gadget;

/* loaded from: classes2.dex */
public interface UsbGadgetHal {
    void getCurrentUsbFunctions(long j);

    int getGadgetHalVersion() throws android.os.RemoteException;

    void getUsbSpeed(long j);

    void reset(long j);

    void setCurrentUsbFunctions(int i, long j, boolean z, int i2, long j2);
}
