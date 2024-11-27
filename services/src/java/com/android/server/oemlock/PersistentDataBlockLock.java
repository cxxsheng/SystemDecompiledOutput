package com.android.server.oemlock;

/* loaded from: classes2.dex */
class PersistentDataBlockLock extends com.android.server.oemlock.OemLock {
    private static final java.lang.String TAG = "OemLock";
    private android.content.Context mContext;

    PersistentDataBlockLock(android.content.Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.oemlock.OemLock
    @android.annotation.Nullable
    java.lang.String getLockName() {
        return "";
    }

    @Override // com.android.server.oemlock.OemLock
    void setOemUnlockAllowedByCarrier(boolean z, @android.annotation.Nullable byte[] bArr) {
        if (bArr != null) {
            android.util.Slog.w(TAG, "Signature provided but is not being used");
        }
        android.os.UserManager.get(this.mContext).setUserRestriction("no_oem_unlock", !z, android.os.UserHandle.SYSTEM);
        if (!z) {
            disallowUnlockIfNotUnlocked();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    boolean isOemUnlockAllowedByCarrier() {
        return !android.os.UserManager.get(this.mContext).hasUserRestriction("no_oem_unlock", android.os.UserHandle.SYSTEM);
    }

    @Override // com.android.server.oemlock.OemLock
    void setOemUnlockAllowedByDevice(boolean z) {
        android.service.persistentdata.PersistentDataBlockManager persistentDataBlockManager = (android.service.persistentdata.PersistentDataBlockManager) this.mContext.getSystemService("persistent_data_block");
        if (persistentDataBlockManager == null) {
            android.util.Slog.w(TAG, "PersistentDataBlock is not supported on this device");
        } else {
            persistentDataBlockManager.setOemUnlockEnabled(z);
        }
    }

    @Override // com.android.server.oemlock.OemLock
    boolean isOemUnlockAllowedByDevice() {
        android.service.persistentdata.PersistentDataBlockManager persistentDataBlockManager = (android.service.persistentdata.PersistentDataBlockManager) this.mContext.getSystemService("persistent_data_block");
        if (persistentDataBlockManager == null) {
            android.util.Slog.w(TAG, "PersistentDataBlock is not supported on this device");
            return false;
        }
        return persistentDataBlockManager.getOemUnlockEnabled();
    }

    private void disallowUnlockIfNotUnlocked() {
        android.service.persistentdata.PersistentDataBlockManager persistentDataBlockManager = (android.service.persistentdata.PersistentDataBlockManager) this.mContext.getSystemService("persistent_data_block");
        if (persistentDataBlockManager == null) {
            android.util.Slog.w(TAG, "PersistentDataBlock is not supported on this device");
        } else if (persistentDataBlockManager.getFlashLockState() != 0) {
            persistentDataBlockManager.setOemUnlockEnabled(false);
        }
    }
}
