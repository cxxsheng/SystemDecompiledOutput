package android.service.wallpapereffectsgeneration;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class WallpaperEffectsGenerationService extends android.app.Service {
    private static final boolean DEBUG = false;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.wallpapereffectsgeneration.WallpaperEffectsGenerationService";
    private static final java.lang.String TAG = "WallpaperEffectsGenerationService";
    private android.os.Handler mHandler;
    private final android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService mInterface = new android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService.Stub() { // from class: android.service.wallpapereffectsgeneration.WallpaperEffectsGenerationService.1
        @Override // android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService
        public void onGenerateCinematicEffect(android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest) {
            android.service.wallpapereffectsgeneration.WallpaperEffectsGenerationService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.wallpapereffectsgeneration.WallpaperEffectsGenerationService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.wallpapereffectsgeneration.WallpaperEffectsGenerationService) obj).onGenerateCinematicEffect((android.app.wallpapereffectsgeneration.CinematicEffectRequest) obj2);
                }
            }, android.service.wallpapereffectsgeneration.WallpaperEffectsGenerationService.this, cinematicEffectRequest));
        }
    };
    private android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager mService;

    public abstract void onGenerateCinematicEffect(android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest);

    public final void returnCinematicEffectResponse(android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) {
        try {
            this.mService.returnCinematicEffectResponse(cinematicEffectResponse);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
        this.mService = android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.WALLPAPER_EFFECTS_GENERATION_SERVICE));
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        android.util.Slog.w(TAG, "Tried to bind to wrong intent (should be android.service.wallpapereffectsgeneration.WallpaperEffectsGenerationService: " + intent);
        return null;
    }
}
