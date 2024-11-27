package com.android.server.wallpapereffectsgeneration;

/* loaded from: classes.dex */
public class WallpaperEffectsGenerationManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService, com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService> {
    private static final boolean DEBUG = false;
    private static final int MAX_TEMP_SERVICE_DURATION_MS = 120000;
    private static final java.lang.String TAG = com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService.class.getSimpleName();
    private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;

    public WallpaperEffectsGenerationManagerService(android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_defaultSearchSelectorPackageName), null, 17);
        this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService newServiceLocked(int i, boolean z) {
        return new com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("wallpaper_effects_generation", new com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService.WallpaperEffectsGenerationManagerStub());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_WALLPAPER_EFFECTS_GENERATION", TAG);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageUpdatedLocked(int i) {
        com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService peekServiceForUserLocked = peekServiceForUserLocked(i);
        if (peekServiceForUserLocked != null) {
            peekServiceForUserLocked.onPackageUpdatedLocked();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageRestartedLocked(int i) {
        com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService peekServiceForUserLocked = peekServiceForUserLocked(i);
        if (peekServiceForUserLocked != null) {
            peekServiceForUserLocked.onPackageRestartedLocked();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected int getMaximumTemporaryServiceDurationMs() {
        return MAX_TEMP_SERVICE_DURATION_MS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class WallpaperEffectsGenerationManagerStub extends android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager.Stub {
        private WallpaperEffectsGenerationManagerStub() {
        }

        public void generateCinematicEffect(@android.annotation.NonNull final android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest, @android.annotation.NonNull final android.app.wallpapereffectsgeneration.ICinematicEffectListener iCinematicEffectListener) {
            if (!runForUser("generateCinematicEffect", true, new java.util.function.Consumer() { // from class: com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService$WallpaperEffectsGenerationManagerStub$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService) obj).onGenerateCinematicEffectLocked(cinematicEffectRequest, iCinematicEffectListener);
                }
            })) {
                try {
                    iCinematicEffectListener.onCinematicEffectGenerated(new android.app.wallpapereffectsgeneration.CinematicEffectResponse.Builder(0, cinematicEffectRequest.getTaskId()).build());
                } catch (android.os.RemoteException e) {
                }
            }
        }

        public void returnCinematicEffectResponse(@android.annotation.NonNull final android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) {
            runForUser("returnCinematicResponse", false, new java.util.function.Consumer() { // from class: com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService$WallpaperEffectsGenerationManagerStub$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService) obj).onReturnCinematicEffectResponseLocked(cinematicEffectResponse);
                }
            });
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) {
            new com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerServiceShellCommand(com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        private boolean runForUser(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull boolean z, @android.annotation.NonNull java.util.function.Consumer<com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService> consumer) {
            boolean z2;
            int handleIncomingUser = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), android.os.Binder.getCallingUserHandle().getIdentifier(), false, 0, (java.lang.String) null, (java.lang.String) null);
            if (z && com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService.this.getContext().checkCallingPermission("android.permission.MANAGE_WALLPAPER_EFFECTS_GENERATION") != 0 && !((com.android.server.infra.AbstractMasterSystemService) com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService.this).mServiceNameResolver.isTemporary(handleIncomingUser) && !com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService.this.mActivityTaskManagerInternal.isCallerRecents(android.os.Binder.getCallingUid())) {
                java.lang.String str2 = "Permission Denial: Cannot call " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid();
                android.util.Slog.w(com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService.TAG, str2);
                throw new java.lang.SecurityException(str2);
            }
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService.this).mLock) {
                    try {
                        com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService wallpaperEffectsGenerationPerUserService = (com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationPerUserService) com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService.this.getServiceForUserLocked(handleIncomingUser);
                        if (wallpaperEffectsGenerationPerUserService == null) {
                            z2 = false;
                        } else {
                            if (!z && !wallpaperEffectsGenerationPerUserService.isCallingUidAllowed(callingUid)) {
                                java.lang.String str3 = "Permission Denial: cannot call " + str + ", uid[" + callingUid + "] doesn't match service implementation";
                                android.util.Slog.w(com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService.TAG, str3);
                                throw new java.lang.SecurityException(str3);
                            }
                            consumer.accept(wallpaperEffectsGenerationPerUserService);
                            z2 = true;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return z2;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }
}
