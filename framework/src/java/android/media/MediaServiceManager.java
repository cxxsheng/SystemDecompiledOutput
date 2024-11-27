package android.media;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes2.dex */
public class MediaServiceManager {
    private static final java.lang.String MEDIA_COMMUNICATION_SERVICE = "media_communication";
    private static final java.lang.String MEDIA_SESSION_SERVICE = "media_session";
    private static final java.lang.String MEDIA_TRANSCODING_SERVICE = "media.transcoding";

    public static final class ServiceRegisterer {
        private final boolean mLazyStart;
        private final java.lang.String mServiceName;

        public ServiceRegisterer(java.lang.String str, boolean z) {
            this.mServiceName = str;
            this.mLazyStart = z;
        }

        public ServiceRegisterer(java.lang.String str) {
            this(str, false);
        }

        public android.os.IBinder get() {
            if (this.mLazyStart) {
                return android.os.ServiceManager.waitForService(this.mServiceName);
            }
            return android.os.ServiceManager.getService(this.mServiceName);
        }
    }

    public android.media.MediaServiceManager.ServiceRegisterer getMediaSessionServiceRegisterer() {
        return new android.media.MediaServiceManager.ServiceRegisterer("media_session");
    }

    public android.media.MediaServiceManager.ServiceRegisterer getMediaTranscodingServiceRegisterer() {
        return new android.media.MediaServiceManager.ServiceRegisterer(MEDIA_TRANSCODING_SERVICE, true);
    }

    public android.media.MediaServiceManager.ServiceRegisterer getMediaCommunicationServiceRegisterer() {
        return new android.media.MediaServiceManager.ServiceRegisterer("media_communication");
    }
}
