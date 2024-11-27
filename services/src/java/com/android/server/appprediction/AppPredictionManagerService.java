package com.android.server.appprediction;

/* loaded from: classes.dex */
public class AppPredictionManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.appprediction.AppPredictionManagerService, com.android.server.appprediction.AppPredictionPerUserService> {
    private static final int MAX_TEMP_SERVICE_DURATION_MS = 120000;
    private static final java.lang.String TAG = com.android.server.appprediction.AppPredictionManagerService.class.getSimpleName();
    private com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;

    public AppPredictionManagerService(android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_customVpnConfirmDialogComponent), null, 17);
        this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.appprediction.AppPredictionPerUserService newServiceLocked(int i, boolean z) {
        return new com.android.server.appprediction.AppPredictionPerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("app_prediction", new com.android.server.appprediction.AppPredictionManagerService.PredictionManagerServiceStub());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_APP_PREDICTIONS", TAG);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageUpdatedLocked(int i) {
        com.android.server.appprediction.AppPredictionPerUserService peekServiceForUserLocked = peekServiceForUserLocked(i);
        if (peekServiceForUserLocked != null) {
            peekServiceForUserLocked.onPackageUpdatedLocked();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageRestartedLocked(int i) {
        com.android.server.appprediction.AppPredictionPerUserService peekServiceForUserLocked = peekServiceForUserLocked(i);
        if (peekServiceForUserLocked != null) {
            peekServiceForUserLocked.onPackageRestartedLocked();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected int getMaximumTemporaryServiceDurationMs() {
        return MAX_TEMP_SERVICE_DURATION_MS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PredictionManagerServiceStub extends android.app.prediction.IPredictionManager.Stub {
        private PredictionManagerServiceStub() {
        }

        public void createPredictionSession(@android.annotation.NonNull final android.app.prediction.AppPredictionContext appPredictionContext, @android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final android.os.IBinder iBinder) {
            runForUserLocked("createPredictionSession", appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.appprediction.AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appprediction.AppPredictionPerUserService) obj).onCreatePredictionSessionLocked(appPredictionContext, appPredictionSessionId, iBinder);
                }
            });
        }

        public void notifyAppTargetEvent(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final android.app.prediction.AppTargetEvent appTargetEvent) {
            runForUserLocked("notifyAppTargetEvent", appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.appprediction.AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appprediction.AppPredictionPerUserService) obj).notifyAppTargetEventLocked(appPredictionSessionId, appTargetEvent);
                }
            });
        }

        public void notifyLaunchLocationShown(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final android.content.pm.ParceledListSlice parceledListSlice) {
            runForUserLocked("notifyLaunchLocationShown", appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.appprediction.AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appprediction.AppPredictionPerUserService) obj).notifyLaunchLocationShownLocked(appPredictionSessionId, str, parceledListSlice);
                }
            });
        }

        public void sortAppTargets(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final android.content.pm.ParceledListSlice parceledListSlice, final android.app.prediction.IPredictionCallback iPredictionCallback) {
            runForUserLocked("sortAppTargets", appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.appprediction.AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appprediction.AppPredictionPerUserService) obj).sortAppTargetsLocked(appPredictionSessionId, parceledListSlice, iPredictionCallback);
                }
            });
        }

        public void registerPredictionUpdates(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final android.app.prediction.IPredictionCallback iPredictionCallback) {
            runForUserLocked("registerPredictionUpdates", appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.appprediction.AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appprediction.AppPredictionPerUserService) obj).registerPredictionUpdatesLocked(appPredictionSessionId, iPredictionCallback);
                }
            });
        }

        public void unregisterPredictionUpdates(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final android.app.prediction.IPredictionCallback iPredictionCallback) {
            runForUserLocked("unregisterPredictionUpdates", appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.appprediction.AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appprediction.AppPredictionPerUserService) obj).unregisterPredictionUpdatesLocked(appPredictionSessionId, iPredictionCallback);
                }
            });
        }

        public void requestPredictionUpdate(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
            runForUserLocked("requestPredictionUpdate", appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.appprediction.AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appprediction.AppPredictionPerUserService) obj).requestPredictionUpdateLocked(appPredictionSessionId);
                }
            });
        }

        public void onDestroyPredictionSession(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
            runForUserLocked("onDestroyPredictionSession", appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.appprediction.AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appprediction.AppPredictionPerUserService) obj).onDestroyPredictionSessionLocked(appPredictionSessionId);
                }
            });
        }

        public void requestServiceFeatures(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, final android.os.IRemoteCallback iRemoteCallback) {
            runForUserLocked("requestServiceFeatures", appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.appprediction.AppPredictionManagerService$PredictionManagerServiceStub$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.appprediction.AppPredictionPerUserService) obj).requestServiceFeaturesLocked(appPredictionSessionId, iRemoteCallback);
                }
            });
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) {
            new com.android.server.appprediction.AppPredictionManagerServiceShellCommand(com.android.server.appprediction.AppPredictionManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        private void runForUserLocked(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull java.util.function.Consumer<com.android.server.appprediction.AppPredictionPerUserService> consumer) {
            int handleIncomingUser = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), appPredictionSessionId.getUserId(), false, 0, (java.lang.String) null, (java.lang.String) null);
            if (com.android.server.appprediction.AppPredictionManagerService.this.getContext().checkCallingPermission("android.permission.PACKAGE_USAGE_STATS") != 0 && !((com.android.server.infra.AbstractMasterSystemService) com.android.server.appprediction.AppPredictionManagerService.this).mServiceNameResolver.isTemporary(handleIncomingUser) && !com.android.server.appprediction.AppPredictionManagerService.this.mActivityTaskManagerInternal.isCallerRecents(android.os.Binder.getCallingUid()) && android.os.Binder.getCallingUid() != 1000) {
                java.lang.String str2 = "Permission Denial: " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " expected caller to hold PACKAGE_USAGE_STATS permission";
                android.util.Slog.w(com.android.server.appprediction.AppPredictionManagerService.TAG, str2);
                throw new java.lang.SecurityException(str2);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.appprediction.AppPredictionManagerService.this).mLock) {
                    consumer.accept((com.android.server.appprediction.AppPredictionPerUserService) com.android.server.appprediction.AppPredictionManagerService.this.getServiceForUserLocked(handleIncomingUser));
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }
}
