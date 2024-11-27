package android.service.persistentdata;

/* loaded from: classes3.dex */
public class PersistentDataBlockManager {

    @android.annotation.SystemApi
    public static final int FLASH_LOCK_LOCKED = 1;

    @android.annotation.SystemApi
    public static final int FLASH_LOCK_UNKNOWN = -1;

    @android.annotation.SystemApi
    public static final int FLASH_LOCK_UNLOCKED = 0;
    private static final java.lang.String TAG = android.service.persistentdata.PersistentDataBlockManager.class.getSimpleName();
    private android.service.persistentdata.IPersistentDataBlockService sService;

    @android.annotation.SystemApi
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FlashLockState {
    }

    public PersistentDataBlockManager(android.service.persistentdata.IPersistentDataBlockService iPersistentDataBlockService) {
        this.sService = iPersistentDataBlockService;
    }

    @android.annotation.SystemApi
    public int write(byte[] bArr) {
        try {
            return this.sService.write(bArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public byte[] read() {
        try {
            return this.sService.read();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getDataBlockSize() {
        try {
            return this.sService.getDataBlockSize();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public long getMaximumDataBlockSize() {
        try {
            return this.sService.getMaximumDataBlockSize();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void wipe() {
        try {
            this.sService.wipe();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setOemUnlockEnabled(boolean z) {
        try {
            this.sService.setOemUnlockEnabled(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean getOemUnlockEnabled() {
        try {
            return this.sService.getOemUnlockEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getFlashLockState() {
        try {
            return this.sService.getFlashLockState();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getPersistentDataPackageName() {
        try {
            return this.sService.getPersistentDataPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isFactoryResetProtectionActive() {
        try {
            return this.sService.isFactoryResetProtectionActive();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean deactivateFactoryResetProtection(byte[] bArr) {
        try {
            return this.sService.deactivateFactoryResetProtection(bArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean setFactoryResetProtectionSecret(byte[] bArr) {
        try {
            return this.sService.setFactoryResetProtectionSecret(bArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
