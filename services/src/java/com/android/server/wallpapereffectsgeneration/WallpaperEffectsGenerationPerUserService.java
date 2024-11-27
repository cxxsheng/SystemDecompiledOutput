package com.android.server.wallpapereffectsgeneration;

/* loaded from: classes.dex */
public class WallpaperEffectsGenerationPerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService, com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService> implements com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService.RemoteWallpaperEffectsGenerationServiceCallback {
    private static final java.lang.String TAG = com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService.class.getSimpleName();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService.CinematicEffectListenerWrapper mCinematicEffectListenerWrapper;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService mRemoteService;

    protected WallpaperEffectsGenerationPerUserService(com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService wallpaperEffectsGenerationManagerService, java.lang.Object obj, int i) {
        super(wallpaperEffectsGenerationManagerService, obj, i);
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            android.content.pm.ServiceInfo serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
            if (!"android.permission.BIND_WALLPAPER_EFFECTS_GENERATION_SERVICE".equals(serviceInfo.permission)) {
                android.util.Slog.w(TAG, "WallpaperEffectsGenerationService from '" + serviceInfo.packageName + "' does not require permission android.permission.BIND_WALLPAPER_EFFECTS_GENERATION_SERVICE");
                throw new java.lang.SecurityException("Service does not require permission android.permission.BIND_WALLPAPER_EFFECTS_GENERATION_SERVICE");
            }
            return serviceInfo;
        } catch (android.os.RemoteException e) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Could not get service for " + componentName);
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean updateLocked(boolean z) {
        boolean updateLocked = super.updateLocked(z);
        updateRemoteServiceLocked();
        return updateLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onGenerateCinematicEffectLocked(@android.annotation.NonNull final android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest, @android.annotation.NonNull android.app.wallpapereffectsgeneration.ICinematicEffectListener iCinematicEffectListener) {
        android.app.wallpapereffectsgeneration.CinematicEffectResponse build;
        java.lang.String taskId = cinematicEffectRequest.getTaskId();
        if (this.mCinematicEffectListenerWrapper != null) {
            if (this.mCinematicEffectListenerWrapper.mTaskId.equals(taskId)) {
                build = new android.app.wallpapereffectsgeneration.CinematicEffectResponse.Builder(3, taskId).build();
            } else {
                build = new android.app.wallpapereffectsgeneration.CinematicEffectResponse.Builder(4, taskId).build();
            }
            try {
                iCinematicEffectListener.onCinematicEffectGenerated(build);
                return;
            } catch (android.os.RemoteException e) {
                if (isDebug()) {
                    android.util.Slog.w(TAG, "RemoteException invoking cinematic effect listener for task[" + this.mCinematicEffectListenerWrapper.mTaskId + "]");
                    return;
                }
                return;
            }
        }
        com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService ensureRemoteServiceLocked = ensureRemoteServiceLocked();
        if (ensureRemoteServiceLocked != null) {
            ensureRemoteServiceLocked.executeOnResolvedService(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService$$ExternalSyntheticLambda0
                public final void run(android.os.IInterface iInterface) {
                    ((android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService) iInterface).onGenerateCinematicEffect(cinematicEffectRequest);
                }
            });
            this.mCinematicEffectListenerWrapper = new com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService.CinematicEffectListenerWrapper(taskId, iCinematicEffectListener);
            return;
        }
        if (isDebug()) {
            android.util.Slog.d(TAG, "Remote service not found");
        }
        try {
            iCinematicEffectListener.onCinematicEffectGenerated(createErrorCinematicEffectResponse(taskId));
        } catch (android.os.RemoteException e2) {
            if (isDebug()) {
                android.util.Slog.d(TAG, "Failed to invoke cinematic effect listener for task [" + taskId + "]");
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onReturnCinematicEffectResponseLocked(@android.annotation.NonNull android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) {
        invokeCinematicListenerAndCleanup(cinematicEffectResponse);
    }

    public boolean isCallingUidAllowed(int i) {
        return getServiceUidLocked() == i;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateRemoteServiceLocked() {
        if (this.mRemoteService != null) {
            this.mRemoteService.destroy();
            this.mRemoteService = null;
        }
        if (this.mCinematicEffectListenerWrapper != null) {
            invokeCinematicListenerAndCleanup(createErrorCinematicEffectResponse(this.mCinematicEffectListenerWrapper.mTaskId));
        }
    }

    void onPackageUpdatedLocked() {
        if (isDebug()) {
            android.util.Slog.v(TAG, "onPackageUpdatedLocked()");
        }
        destroyAndRebindRemoteService();
    }

    void onPackageRestartedLocked() {
        if (isDebug()) {
            android.util.Slog.v(TAG, "onPackageRestartedLocked()");
        }
        destroyAndRebindRemoteService();
    }

    private void destroyAndRebindRemoteService() {
        if (this.mRemoteService == null) {
            return;
        }
        if (isDebug()) {
            android.util.Slog.d(TAG, "Destroying the old remote service.");
        }
        this.mRemoteService.destroy();
        this.mRemoteService = null;
        this.mRemoteService = ensureRemoteServiceLocked();
        if (this.mRemoteService != null) {
            if (isDebug()) {
                android.util.Slog.d(TAG, "Rebinding to the new remote service.");
            }
            this.mRemoteService.reconnect();
        }
        if (this.mCinematicEffectListenerWrapper != null) {
            invokeCinematicListenerAndCleanup(createErrorCinematicEffectResponse(this.mCinematicEffectListenerWrapper.mTaskId));
        }
    }

    private android.app.wallpapereffectsgeneration.CinematicEffectResponse createErrorCinematicEffectResponse(java.lang.String str) {
        return new android.app.wallpapereffectsgeneration.CinematicEffectResponse.Builder(0, str).build();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void invokeCinematicListenerAndCleanup(android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) {
        try {
            try {
                if (this.mCinematicEffectListenerWrapper != null && this.mCinematicEffectListenerWrapper.mListener != null) {
                    this.mCinematicEffectListenerWrapper.mListener.onCinematicEffectGenerated(cinematicEffectResponse);
                } else if (isDebug()) {
                    android.util.Slog.w(TAG, "Cinematic effect listener not found for task[" + this.mCinematicEffectListenerWrapper.mTaskId + "]");
                }
            } catch (android.os.RemoteException e) {
                if (isDebug()) {
                    android.util.Slog.w(TAG, "Error invoking cinematic effect listener for task[" + this.mCinematicEffectListenerWrapper.mTaskId + "]");
                }
            }
            this.mCinematicEffectListenerWrapper = null;
        } catch (java.lang.Throwable th) {
            this.mCinematicEffectListenerWrapper = null;
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService ensureRemoteServiceLocked() {
        if (this.mRemoteService == null) {
            android.content.ComponentName updateServiceInfoLocked = updateServiceInfoLocked();
            if (updateServiceInfoLocked == null) {
                if (((com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "ensureRemoteServiceLocked(): not set");
                    return null;
                }
                return null;
            }
            this.mRemoteService = new com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService(getContext(), updateServiceInfoLocked, this.mUserId, this, ((com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService) this.mMaster).isBindInstantServiceAllowed(), ((com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService) this.mMaster).verbose);
        }
        return this.mRemoteService;
    }

    public void onServiceDied(com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService remoteWallpaperEffectsGenerationService) {
        android.util.Slog.w(TAG, "remote wallpaper effects generation service died");
        updateRemoteServiceLocked();
    }

    @Override // com.android.server.wallpapereffectsgeneration.RemoteWallpaperEffectsGenerationService.RemoteWallpaperEffectsGenerationServiceCallback
    public void onConnectedStateChanged(boolean z) {
        if (!z) {
            android.util.Slog.w(TAG, "remote wallpaper effects generation service disconnected");
            updateRemoteServiceLocked();
        }
    }

    private static final class CinematicEffectListenerWrapper {

        @android.annotation.NonNull
        private final android.app.wallpapereffectsgeneration.ICinematicEffectListener mListener;

        @android.annotation.NonNull
        private final java.lang.String mTaskId;

        CinematicEffectListenerWrapper(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.app.wallpapereffectsgeneration.ICinematicEffectListener iCinematicEffectListener) {
            this.mTaskId = str;
            this.mListener = iCinematicEffectListener;
        }
    }
}
