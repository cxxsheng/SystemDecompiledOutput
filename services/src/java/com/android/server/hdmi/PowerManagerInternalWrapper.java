package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class PowerManagerInternalWrapper {
    private static final java.lang.String TAG = "PowerManagerInternalWrapper";
    private android.os.PowerManagerInternal mPowerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);

    public boolean wasDeviceIdleFor(long j) {
        return this.mPowerManagerInternal.wasDeviceIdleFor(j);
    }
}
