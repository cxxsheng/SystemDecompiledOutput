package android.os;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class ArtModuleServiceManager {

    public static final class ServiceRegisterer {
        private final java.lang.String mServiceName;

        public ServiceRegisterer(java.lang.String str) {
            this.mServiceName = str;
        }

        public android.os.IBinder waitForService() {
            return android.os.ServiceManager.waitForService(this.mServiceName);
        }
    }

    public android.os.ArtModuleServiceManager.ServiceRegisterer getArtdServiceRegisterer() {
        return new android.os.ArtModuleServiceManager.ServiceRegisterer("artd");
    }

    public android.os.ArtModuleServiceManager.ServiceRegisterer getArtdPreRebootServiceRegisterer() {
        return new android.os.ArtModuleServiceManager.ServiceRegisterer("artd_pre_reboot");
    }

    public android.os.ArtModuleServiceManager.ServiceRegisterer getDexoptChrootSetupServiceRegisterer() {
        return new android.os.ArtModuleServiceManager.ServiceRegisterer("dexopt_chroot_setup");
    }
}
