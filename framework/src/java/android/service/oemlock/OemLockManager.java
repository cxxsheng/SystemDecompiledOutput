package android.service.oemlock;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class OemLockManager {
    private android.service.oemlock.IOemLockService mService;

    public OemLockManager(android.service.oemlock.IOemLockService iOemLockService) {
        this.mService = iOemLockService;
    }

    public java.lang.String getLockName() {
        try {
            return this.mService.getLockName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) {
        try {
            this.mService.setOemUnlockAllowedByCarrier(z, bArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOemUnlockAllowedByCarrier() {
        try {
            return this.mService.isOemUnlockAllowedByCarrier();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setOemUnlockAllowedByUser(boolean z) {
        try {
            this.mService.setOemUnlockAllowedByUser(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOemUnlockAllowedByUser() {
        try {
            return this.mService.isOemUnlockAllowedByUser();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOemUnlockAllowed() {
        try {
            return this.mService.isOemUnlockAllowed();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isDeviceOemUnlocked() {
        try {
            return this.mService.isDeviceOemUnlocked();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
