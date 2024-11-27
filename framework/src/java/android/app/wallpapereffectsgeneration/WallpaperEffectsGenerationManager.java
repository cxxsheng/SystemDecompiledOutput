package android.app.wallpapereffectsgeneration;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class WallpaperEffectsGenerationManager {
    private final android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager mService;

    public interface CinematicEffectListener {
        void onCinematicEffectGenerated(android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse);
    }

    public WallpaperEffectsGenerationManager(android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager iWallpaperEffectsGenerationManager) {
        this.mService = iWallpaperEffectsGenerationManager;
    }

    @android.annotation.SystemApi
    public void generateCinematicEffect(android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest, java.util.concurrent.Executor executor, android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager.CinematicEffectListener cinematicEffectListener) {
        try {
            this.mService.generateCinematicEffect(cinematicEffectRequest, new android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager.CinematicEffectListenerWrapper(cinematicEffectListener, executor));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CinematicEffectListenerWrapper extends android.app.wallpapereffectsgeneration.ICinematicEffectListener.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager.CinematicEffectListener mListener;

        CinematicEffectListenerWrapper(android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager.CinematicEffectListener cinematicEffectListener, java.util.concurrent.Executor executor) {
            this.mListener = cinematicEffectListener;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCinematicEffectGenerated$0(android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) {
            this.mListener.onCinematicEffectGenerated(cinematicEffectResponse);
        }

        @Override // android.app.wallpapereffectsgeneration.ICinematicEffectListener
        public void onCinematicEffectGenerated(final android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager$CinematicEffectListenerWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.wallpapereffectsgeneration.WallpaperEffectsGenerationManager.CinematicEffectListenerWrapper.this.lambda$onCinematicEffectGenerated$0(cinematicEffectResponse);
                }
            });
        }
    }
}
