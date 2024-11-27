package com.android.server.oemlock;

/* loaded from: classes2.dex */
class VendorLockAidl extends com.android.server.oemlock.OemLock {
    private static final java.lang.String TAG = "OemLock";
    private android.hardware.oemlock.IOemLock mOemLock = getOemLockHalService();

    static android.hardware.oemlock.IOemLock getOemLockHalService() {
        return android.hardware.oemlock.IOemLock.Stub.asInterface(android.os.ServiceManager.waitForDeclaredService(android.hardware.oemlock.IOemLock.DESCRIPTOR + "/default"));
    }

    VendorLockAidl(android.content.Context context) {
    }

    @Override // com.android.server.oemlock.OemLock
    @android.annotation.Nullable
    java.lang.String getLockName() {
        try {
            return this.mOemLock.getName();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to get name from HAL", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    void setOemUnlockAllowedByCarrier(boolean z, @android.annotation.Nullable byte[] bArr) {
        int oemUnlockAllowedByCarrier;
        try {
            if (bArr == null) {
                oemUnlockAllowedByCarrier = this.mOemLock.setOemUnlockAllowedByCarrier(z, new byte[0]);
            } else {
                oemUnlockAllowedByCarrier = this.mOemLock.setOemUnlockAllowedByCarrier(z, bArr);
            }
            switch (oemUnlockAllowedByCarrier) {
                case 0:
                    android.util.Slog.i(TAG, "Updated carrier allows OEM lock state to: " + z);
                    return;
                case 1:
                    break;
                case 2:
                    if (bArr == null) {
                        throw new java.lang.IllegalArgumentException("Signature required for carrier unlock");
                    }
                    throw new java.lang.SecurityException("Invalid signature used in attempt to carrier unlock");
                default:
                    android.util.Slog.e(TAG, "Unknown return value indicates code is out of sync with HAL");
                    break;
            }
            throw new java.lang.RuntimeException("Failed to set carrier OEM unlock state");
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to set carrier state with HAL", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    boolean isOemUnlockAllowedByCarrier() {
        try {
            return this.mOemLock.isOemUnlockAllowedByCarrier();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to get carrier state from HAL");
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    void setOemUnlockAllowedByDevice(boolean z) {
        try {
            this.mOemLock.setOemUnlockAllowedByDevice(z);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to set device state with HAL", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    boolean isOemUnlockAllowedByDevice() {
        try {
            return this.mOemLock.isOemUnlockAllowedByDevice();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to get devie state from HAL");
            throw e.rethrowFromSystemServer();
        }
    }
}
