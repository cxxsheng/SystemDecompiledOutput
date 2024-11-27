package android.provider;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class DeviceConfigServiceManager {

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final class ServiceRegisterer {
        private final java.lang.String mServiceName;

        public ServiceRegisterer(java.lang.String str) {
            this.mServiceName = str;
        }

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        public void register(android.os.IBinder iBinder) {
            android.os.ServiceManager.addService(this.mServiceName, iBinder);
        }

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        public android.os.IBinder get() {
            return android.os.ServiceManager.getService(this.mServiceName);
        }

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        public android.os.IBinder getOrThrow() throws android.provider.DeviceConfigServiceManager.ServiceNotFoundException {
            try {
                return android.os.ServiceManager.getServiceOrThrow(this.mServiceName);
            } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                throw new android.provider.DeviceConfigServiceManager.ServiceNotFoundException(this.mServiceName);
            }
        }

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        public android.os.IBinder tryGet() {
            return android.os.ServiceManager.checkService(this.mServiceName);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static class ServiceNotFoundException extends android.os.ServiceManager.ServiceNotFoundException {
        public ServiceNotFoundException(java.lang.String str) {
            super(str);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public android.provider.DeviceConfigServiceManager.ServiceRegisterer getDeviceConfigUpdatableServiceRegisterer() {
        return new android.provider.DeviceConfigServiceManager.ServiceRegisterer("device_config_updatable");
    }
}
