package com.android.server.wallpapereffectsgeneration;

/* loaded from: classes.dex */
public class RemoteWallpaperEffectsGenerationService extends com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService<com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService, android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService> {
    private static final java.lang.String TAG = com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService.class.getSimpleName();
    private static final long TIMEOUT_IDLE_BIND_MILLIS = 120000;
    private static final long TIMEOUT_REMOTE_REQUEST_MILLIS = 2000;
    private final com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService.RemoteWallpaperEffectsGenerationServiceCallback mCallback;

    public interface RemoteWallpaperEffectsGenerationServiceCallback extends com.android.internal.infra.AbstractRemoteService.VultureCallback<com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService> {
        void onConnectedStateChanged(boolean z);
    }

    public RemoteWallpaperEffectsGenerationService(android.content.Context context, android.content.ComponentName componentName, int i, com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService.RemoteWallpaperEffectsGenerationServiceCallback remoteWallpaperEffectsGenerationServiceCallback, boolean z, boolean z2) {
        super(context, "android.service.wallpapereffectsgeneration.WallpaperEffectsGenerationService", componentName, i, remoteWallpaperEffectsGenerationServiceCallback, context.getMainThreadHandler(), z ? 4194304 : 0, z2, 1);
        this.mCallback = remoteWallpaperEffectsGenerationServiceCallback;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService getServiceInterface(android.os.IBinder iBinder) {
        return android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService.Stub.asInterface(iBinder);
    }

    protected long getTimeoutIdleBindMillis() {
        return 120000L;
    }

    protected long getRemoteRequestMillis() {
        return TIMEOUT_REMOTE_REQUEST_MILLIS;
    }

    public void reconnect() {
        super.scheduleBind();
    }

    public void scheduleOnResolvedService(@android.annotation.NonNull com.android.internal.infra.AbstractRemoteService.AsyncRequest<android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService> asyncRequest) {
        scheduleAsyncRequest(asyncRequest);
    }

    public void executeOnResolvedService(@android.annotation.NonNull com.android.internal.infra.AbstractRemoteService.AsyncRequest<android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService> asyncRequest) {
        executeAsyncRequest(asyncRequest);
    }

    protected void handleOnConnectedStateChanged(boolean z) {
        if (this.mCallback != null) {
            this.mCallback.onConnectedStateChanged(z);
        }
    }
}
