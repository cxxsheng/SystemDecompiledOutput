package com.android.server.oemlock;

/* loaded from: classes2.dex */
class VendorLockHidl extends com.android.server.oemlock.OemLock {
    private static final java.lang.String TAG = "OemLock";
    private android.content.Context mContext;
    private android.hardware.oemlock.V1_0.IOemLock mOemLock = getOemLockHalService();

    static android.hardware.oemlock.V1_0.IOemLock getOemLockHalService() {
        try {
            return android.hardware.oemlock.V1_0.IOemLock.getService(true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.util.NoSuchElementException e2) {
            android.util.Slog.i(TAG, "OemLock Hidl HAL not present on device");
            return null;
        }
    }

    VendorLockHidl(android.content.Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.oemlock.OemLock
    @android.annotation.Nullable
    java.lang.String getLockName() {
        final java.lang.String[] strArr = new java.lang.String[1];
        final java.lang.Integer[] numArr = new java.lang.Integer[1];
        try {
            this.mOemLock.getName(new android.hardware.oemlock.V1_0.IOemLock.getNameCallback() { // from class: com.android.server.oemlock.VendorLockHidl$$ExternalSyntheticLambda1
                @Override // android.hardware.oemlock.V1_0.IOemLock.getNameCallback
                public final void onValues(int i, java.lang.String str) {
                    com.android.server.oemlock.VendorLockHidl.lambda$getLockName$0(numArr, strArr, i, str);
                }
            });
            switch (numArr[0].intValue()) {
                case 0:
                    return strArr[0];
                case 1:
                    android.util.Slog.e(TAG, "Failed to get OEM lock name.");
                    return null;
                default:
                    android.util.Slog.e(TAG, "Unknown return value indicates code is out of sync with HAL");
                    return null;
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to get name from HAL", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getLockName$0(java.lang.Integer[] numArr, java.lang.String[] strArr, int i, java.lang.String str) {
        numArr[0] = java.lang.Integer.valueOf(i);
        strArr[0] = str;
    }

    @Override // com.android.server.oemlock.OemLock
    void setOemUnlockAllowedByCarrier(boolean z, @android.annotation.Nullable byte[] bArr) {
        try {
            java.util.ArrayList<java.lang.Byte> byteArrayList = toByteArrayList(bArr);
            switch (this.mOemLock.setOemUnlockAllowedByCarrier(z, byteArrayList)) {
                case 0:
                    android.util.Slog.i(TAG, "Updated carrier allows OEM lock state to: " + z);
                    return;
                case 1:
                    break;
                case 2:
                    if (byteArrayList.isEmpty()) {
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
        final java.lang.Boolean[] boolArr = new java.lang.Boolean[1];
        final java.lang.Integer[] numArr = new java.lang.Integer[1];
        try {
            this.mOemLock.isOemUnlockAllowedByCarrier(new android.hardware.oemlock.V1_0.IOemLock.isOemUnlockAllowedByCarrierCallback() { // from class: com.android.server.oemlock.VendorLockHidl$$ExternalSyntheticLambda0
                @Override // android.hardware.oemlock.V1_0.IOemLock.isOemUnlockAllowedByCarrierCallback
                public final void onValues(int i, boolean z) {
                    com.android.server.oemlock.VendorLockHidl.lambda$isOemUnlockAllowedByCarrier$1(numArr, boolArr, i, z);
                }
            });
            switch (numArr[0].intValue()) {
                case 0:
                    return boolArr[0].booleanValue();
                case 1:
                    break;
                default:
                    android.util.Slog.e(TAG, "Unknown return value indicates code is out of sync with HAL");
                    break;
            }
            throw new java.lang.RuntimeException("Failed to get carrier OEM unlock state");
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to get carrier state from HAL");
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$isOemUnlockAllowedByCarrier$1(java.lang.Integer[] numArr, java.lang.Boolean[] boolArr, int i, boolean z) {
        numArr[0] = java.lang.Integer.valueOf(i);
        boolArr[0] = java.lang.Boolean.valueOf(z);
    }

    @Override // com.android.server.oemlock.OemLock
    void setOemUnlockAllowedByDevice(boolean z) {
        try {
            switch (this.mOemLock.setOemUnlockAllowedByDevice(z)) {
                case 0:
                    android.util.Slog.i(TAG, "Updated device allows OEM lock state to: " + z);
                    return;
                case 1:
                    break;
                default:
                    android.util.Slog.e(TAG, "Unknown return value indicates code is out of sync with HAL");
                    break;
            }
            throw new java.lang.RuntimeException("Failed to set device OEM unlock state");
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to set device state with HAL", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    boolean isOemUnlockAllowedByDevice() {
        final java.lang.Boolean[] boolArr = new java.lang.Boolean[1];
        final java.lang.Integer[] numArr = new java.lang.Integer[1];
        try {
            this.mOemLock.isOemUnlockAllowedByDevice(new android.hardware.oemlock.V1_0.IOemLock.isOemUnlockAllowedByDeviceCallback() { // from class: com.android.server.oemlock.VendorLockHidl$$ExternalSyntheticLambda2
                @Override // android.hardware.oemlock.V1_0.IOemLock.isOemUnlockAllowedByDeviceCallback
                public final void onValues(int i, boolean z) {
                    com.android.server.oemlock.VendorLockHidl.lambda$isOemUnlockAllowedByDevice$2(numArr, boolArr, i, z);
                }
            });
            switch (numArr[0].intValue()) {
                case 0:
                    return boolArr[0].booleanValue();
                case 1:
                    break;
                default:
                    android.util.Slog.e(TAG, "Unknown return value indicates code is out of sync with HAL");
                    break;
            }
            throw new java.lang.RuntimeException("Failed to get device OEM unlock state");
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to get devie state from HAL");
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$isOemUnlockAllowedByDevice$2(java.lang.Integer[] numArr, java.lang.Boolean[] boolArr, int i, boolean z) {
        numArr[0] = java.lang.Integer.valueOf(i);
        boolArr[0] = java.lang.Boolean.valueOf(z);
    }

    private java.util.ArrayList<java.lang.Byte> toByteArrayList(byte[] bArr) {
        if (bArr == null) {
            return new java.util.ArrayList<>();
        }
        java.util.ArrayList<java.lang.Byte> arrayList = new java.util.ArrayList<>(bArr.length);
        for (byte b : bArr) {
            arrayList.add(java.lang.Byte.valueOf(b));
        }
        return arrayList;
    }
}
