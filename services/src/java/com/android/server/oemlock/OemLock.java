package com.android.server.oemlock;

/* loaded from: classes2.dex */
abstract class OemLock {
    OemLock() {
    }

    @android.annotation.Nullable
    abstract java.lang.String getLockName();

    abstract boolean isOemUnlockAllowedByCarrier();

    abstract boolean isOemUnlockAllowedByDevice();

    abstract void setOemUnlockAllowedByCarrier(boolean z, @android.annotation.Nullable byte[] bArr);

    abstract void setOemUnlockAllowedByDevice(boolean z);
}
