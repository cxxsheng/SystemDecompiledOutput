package android.provider;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class DeviceConfigInitializer {
    private static android.provider.DeviceConfigServiceManager sDeviceConfigServiceManager;
    private static final java.lang.Object sLock = new java.lang.Object();

    private DeviceConfigInitializer() {
    }

    public static void setDeviceConfigServiceManager(android.provider.DeviceConfigServiceManager deviceConfigServiceManager) {
        synchronized (sLock) {
            if (sDeviceConfigServiceManager != null) {
                throw new java.lang.IllegalStateException("setDeviceConfigServiceManager called twice!");
            }
            java.util.Objects.requireNonNull(deviceConfigServiceManager, "serviceManager must not be null");
            sDeviceConfigServiceManager = deviceConfigServiceManager;
        }
    }

    public static android.provider.DeviceConfigServiceManager getDeviceConfigServiceManager() {
        android.provider.DeviceConfigServiceManager deviceConfigServiceManager;
        synchronized (sLock) {
            deviceConfigServiceManager = sDeviceConfigServiceManager;
        }
        return deviceConfigServiceManager;
    }
}
