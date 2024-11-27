package android.media;

/* loaded from: classes2.dex */
public class MediaFrameworkPlatformInitializer {
    private static volatile android.media.MediaServiceManager sMediaServiceManager;

    private MediaFrameworkPlatformInitializer() {
    }

    public static void setMediaServiceManager(android.media.MediaServiceManager mediaServiceManager) {
        com.android.internal.util.Preconditions.checkState(sMediaServiceManager == null, "setMediaServiceManager called twice!");
        sMediaServiceManager = (android.media.MediaServiceManager) java.util.Objects.requireNonNull(mediaServiceManager);
    }

    public static android.media.MediaServiceManager getMediaServiceManager() {
        return sMediaServiceManager;
    }

    public static void registerServiceWrappers() {
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.MEDIA_SESSION_SERVICE, android.media.session.MediaSessionManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder() { // from class: android.media.MediaFrameworkPlatformInitializer$$ExternalSyntheticLambda0
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
            public final java.lang.Object createService(android.content.Context context) {
                return android.media.MediaFrameworkPlatformInitializer.lambda$registerServiceWrappers$0(context);
            }
        });
    }

    static /* synthetic */ android.media.session.MediaSessionManager lambda$registerServiceWrappers$0(android.content.Context context) {
        return new android.media.session.MediaSessionManager(context);
    }
}
