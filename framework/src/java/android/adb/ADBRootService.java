package android.adb;

/* loaded from: classes.dex */
public class ADBRootService {
    private static final java.lang.String ADB_ROOT_SERVICE = "adbroot_service";
    private static final java.lang.String TAG = "ADBRootService";
    private final android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.adb.ADBRootService.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (android.adb.ADBRootService.this.mService != null) {
                android.adb.ADBRootService.this.mService.asBinder().unlinkToDeath(this, 0);
            }
            android.adb.ADBRootService.this.mService = null;
        }
    };
    private android.adbroot.IADBRootService mService;

    private synchronized android.adbroot.IADBRootService getService() throws android.os.RemoteException {
        if (this.mService != null) {
            return this.mService;
        }
        android.os.IBinder service = android.os.ServiceManager.getService(ADB_ROOT_SERVICE);
        if (service != null) {
            service.linkToDeath(this.mDeathRecipient, 0);
            this.mService = android.adbroot.IADBRootService.Stub.asInterface(service);
            return this.mService;
        }
        android.util.Slog.e(TAG, "Unable to acquire ADBRootService");
        return null;
    }

    public boolean isSupported() {
        try {
            android.adbroot.IADBRootService service = getService();
            if (service != null) {
                return service.isSupported();
            }
            return false;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setEnabled(boolean z) {
        try {
            android.adbroot.IADBRootService service = getService();
            if (service != null) {
                service.setEnabled(z);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getEnabled() {
        try {
            android.adbroot.IADBRootService service = getService();
            if (service != null) {
                return service.getEnabled();
            }
            return false;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
