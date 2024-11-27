package android.os;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class BluetoothServiceManager {
    public static final java.lang.String BLUETOOTH_MANAGER_SERVICE = "bluetooth_manager";

    public static final class ServiceRegisterer {
        private final java.lang.String mServiceName;

        public ServiceRegisterer(java.lang.String str) {
            this.mServiceName = str;
        }

        public void register(android.os.IBinder iBinder) {
            android.os.ServiceManager.addService(this.mServiceName, iBinder);
        }

        public android.os.IBinder get() {
            return android.os.ServiceManager.getService(this.mServiceName);
        }

        public android.os.IBinder getOrThrow() throws android.os.BluetoothServiceManager.ServiceNotFoundException {
            try {
                return android.os.ServiceManager.getServiceOrThrow(this.mServiceName);
            } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                throw new android.os.BluetoothServiceManager.ServiceNotFoundException(this.mServiceName);
            }
        }

        public android.os.IBinder tryGet() {
            return android.os.ServiceManager.checkService(this.mServiceName);
        }
    }

    public static class ServiceNotFoundException extends android.os.ServiceManager.ServiceNotFoundException {
        public ServiceNotFoundException(java.lang.String str) {
            super(str);
        }
    }

    public android.os.BluetoothServiceManager.ServiceRegisterer getBluetoothManagerServiceRegisterer() {
        return new android.os.BluetoothServiceManager.ServiceRegisterer(BLUETOOTH_MANAGER_SERVICE);
    }
}
