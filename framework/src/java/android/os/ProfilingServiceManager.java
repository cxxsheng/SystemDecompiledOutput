package android.os;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class ProfilingServiceManager {

    public static final class ServiceRegisterer {
        private final java.lang.String mServiceName;

        public ServiceRegisterer(java.lang.String str) {
            this.mServiceName = str;
        }

        public android.os.IBinder get() {
            return android.os.ServiceManager.getService(this.mServiceName);
        }

        public android.os.IBinder getOrThrow() throws android.os.ProfilingServiceManager.ServiceNotFoundException {
            try {
                return android.os.ServiceManager.getServiceOrThrow(this.mServiceName);
            } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                throw new android.os.ProfilingServiceManager.ServiceNotFoundException(this.mServiceName);
            }
        }
    }

    public static class ServiceNotFoundException extends android.os.ServiceManager.ServiceNotFoundException {
        public ServiceNotFoundException(java.lang.String str) {
            super(str);
        }
    }

    public android.os.ProfilingServiceManager.ServiceRegisterer getProfilingServiceRegisterer() {
        return new android.os.ProfilingServiceManager.ServiceRegisterer("profiling_service");
    }
}
