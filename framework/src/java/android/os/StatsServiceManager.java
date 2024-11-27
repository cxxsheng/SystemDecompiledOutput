package android.os;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class StatsServiceManager {

    public static final class ServiceRegisterer {
        private final java.lang.String mServiceName;

        public ServiceRegisterer(java.lang.String str) {
            this.mServiceName = str;
        }

        public android.os.IBinder get() {
            return android.os.ServiceManager.getService(this.mServiceName);
        }

        public android.os.IBinder getOrThrow() throws android.os.StatsServiceManager.ServiceNotFoundException {
            try {
                return android.os.ServiceManager.getServiceOrThrow(this.mServiceName);
            } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                throw new android.os.StatsServiceManager.ServiceNotFoundException(this.mServiceName);
            }
        }

        private android.os.IBinder tryGet() {
            return android.os.ServiceManager.checkService(this.mServiceName);
        }
    }

    public static class ServiceNotFoundException extends android.os.ServiceManager.ServiceNotFoundException {
        public ServiceNotFoundException(java.lang.String str) {
            super(str);
        }
    }

    public android.os.StatsServiceManager.ServiceRegisterer getStatsCompanionServiceRegisterer() {
        return new android.os.StatsServiceManager.ServiceRegisterer(android.content.Context.STATS_COMPANION_SERVICE);
    }

    public android.os.StatsServiceManager.ServiceRegisterer getStatsManagerServiceRegisterer() {
        return new android.os.StatsServiceManager.ServiceRegisterer(android.content.Context.STATS_MANAGER_SERVICE);
    }

    public android.os.StatsServiceManager.ServiceRegisterer getStatsdServiceRegisterer() {
        return new android.os.StatsServiceManager.ServiceRegisterer(android.content.Context.STATS_MANAGER);
    }
}
