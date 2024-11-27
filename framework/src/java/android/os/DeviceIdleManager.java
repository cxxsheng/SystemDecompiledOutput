package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class DeviceIdleManager {
    private final android.content.Context mContext;
    private final android.os.IDeviceIdleController mService;

    public DeviceIdleManager(android.content.Context context, android.os.IDeviceIdleController iDeviceIdleController) {
        this.mContext = context;
        this.mService = iDeviceIdleController;
    }

    android.os.IDeviceIdleController getService() {
        return this.mService;
    }

    public void endIdle(java.lang.String str) {
        try {
            this.mService.exitIdle(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String[] getSystemPowerWhitelistExceptIdle() {
        try {
            return this.mService.getSystemPowerWhitelistExceptIdle();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String[] getSystemPowerWhitelist() {
        try {
            return this.mService.getSystemPowerWhitelist();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
