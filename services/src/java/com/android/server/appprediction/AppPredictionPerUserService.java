package com.android.server.appprediction;

/* loaded from: classes.dex */
public class AppPredictionPerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.appprediction.AppPredictionPerUserService, com.android.server.appprediction.AppPredictionManagerService> implements com.android.server.appprediction.RemoteAppPredictionService.RemoteAppPredictionServiceCallbacks {
    private static final java.lang.String PREDICT_USING_PEOPLE_SERVICE_PREFIX = "predict_using_people_service_";
    private static final java.lang.String REMOTE_APP_PREDICTOR_KEY = "remote_app_predictor";
    private static final java.lang.String TAG = com.android.server.appprediction.AppPredictionPerUserService.class.getSimpleName();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.appprediction.RemoteAppPredictionService mRemoteService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.app.prediction.AppPredictionSessionId, com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo> mSessionInfos;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mZombie;

    protected AppPredictionPerUserService(com.android.server.appprediction.AppPredictionManagerService appPredictionManagerService, java.lang.Object obj, int i) {
        super(appPredictionManagerService, obj, i);
        this.mSessionInfos = new android.util.ArrayMap<>();
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            return android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Could not get service for " + componentName);
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean updateLocked(boolean z) {
        boolean updateLocked = super.updateLocked(z);
        if (updateLocked && !isEnabledLocked()) {
            this.mRemoteService = null;
        }
        return updateLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onCreatePredictionSessionLocked(@android.annotation.NonNull final android.app.prediction.AppPredictionContext appPredictionContext, @android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull android.os.IBinder iBinder) {
        boolean z;
        boolean z2 = android.provider.DeviceConfig.getBoolean("systemui", PREDICT_USING_PEOPLE_SERVICE_PREFIX + appPredictionContext.getUiSurface(), false);
        if (appPredictionContext.getExtras() != null && appPredictionContext.getExtras().getBoolean(REMOTE_APP_PREDICTOR_KEY, false) && android.provider.DeviceConfig.getBoolean("systemui", "dark_launch_remote_prediction_service_enabled", false)) {
            z = false;
        } else {
            z = z2;
        }
        if (resolveService(appPredictionSessionId, true, z, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda1
            public final void run(android.os.IInterface iInterface) {
                ((android.service.appprediction.IPredictionService) iInterface).onCreatePredictionSession(appPredictionContext, appPredictionSessionId);
            }
        }) && !this.mSessionInfos.containsKey(appPredictionSessionId)) {
            com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo = new com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo(appPredictionSessionId, appPredictionContext, z, iBinder, new android.os.IBinder.DeathRecipient() { // from class: com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda2
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.appprediction.AppPredictionPerUserService.this.lambda$onCreatePredictionSessionLocked$1(appPredictionSessionId);
                }
            });
            if (appPredictionSessionInfo.linkToDeath()) {
                this.mSessionInfos.put(appPredictionSessionId, appPredictionSessionInfo);
            } else {
                onDestroyPredictionSessionLocked(appPredictionSessionId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreatePredictionSessionLocked$1(android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
        synchronized (this.mLock) {
            onDestroyPredictionSessionLocked(appPredictionSessionId);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifyAppTargetEventLocked(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final android.app.prediction.AppTargetEvent appTargetEvent) {
        com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo = this.mSessionInfos.get(appPredictionSessionId);
        if (appPredictionSessionInfo == null) {
            return;
        }
        resolveService(appPredictionSessionId, false, appPredictionSessionInfo.mUsesPeopleService, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda7
            public final void run(android.os.IInterface iInterface) {
                ((android.service.appprediction.IPredictionService) iInterface).notifyAppTargetEvent(appPredictionSessionId, appTargetEvent);
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifyLaunchLocationShownLocked(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final android.content.pm.ParceledListSlice parceledListSlice) {
        com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo = this.mSessionInfos.get(appPredictionSessionId);
        if (appPredictionSessionInfo == null) {
            return;
        }
        resolveService(appPredictionSessionId, false, appPredictionSessionInfo.mUsesPeopleService, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda9
            public final void run(android.os.IInterface iInterface) {
                ((android.service.appprediction.IPredictionService) iInterface).notifyLaunchLocationShown(appPredictionSessionId, str, parceledListSlice);
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void sortAppTargetsLocked(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final android.content.pm.ParceledListSlice parceledListSlice, @android.annotation.NonNull final android.app.prediction.IPredictionCallback iPredictionCallback) {
        com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo = this.mSessionInfos.get(appPredictionSessionId);
        if (appPredictionSessionInfo == null) {
            return;
        }
        resolveService(appPredictionSessionId, true, appPredictionSessionInfo.mUsesPeopleService, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda0
            public final void run(android.os.IInterface iInterface) {
                ((android.service.appprediction.IPredictionService) iInterface).sortAppTargets(appPredictionSessionId, parceledListSlice, iPredictionCallback);
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void registerPredictionUpdatesLocked(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final android.app.prediction.IPredictionCallback iPredictionCallback) {
        com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo = this.mSessionInfos.get(appPredictionSessionId);
        if (appPredictionSessionInfo != null && resolveService(appPredictionSessionId, true, appPredictionSessionInfo.mUsesPeopleService, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda4
            public final void run(android.os.IInterface iInterface) {
                ((android.service.appprediction.IPredictionService) iInterface).registerPredictionUpdates(appPredictionSessionId, iPredictionCallback);
            }
        })) {
            appPredictionSessionInfo.addCallbackLocked(iPredictionCallback);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void unregisterPredictionUpdatesLocked(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final android.app.prediction.IPredictionCallback iPredictionCallback) {
        com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo = this.mSessionInfos.get(appPredictionSessionId);
        if (appPredictionSessionInfo != null && resolveService(appPredictionSessionId, false, appPredictionSessionInfo.mUsesPeopleService, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda5
            public final void run(android.os.IInterface iInterface) {
                ((android.service.appprediction.IPredictionService) iInterface).unregisterPredictionUpdates(appPredictionSessionId, iPredictionCallback);
            }
        })) {
            appPredictionSessionInfo.removeCallbackLocked(iPredictionCallback);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void requestPredictionUpdateLocked(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
        com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo = this.mSessionInfos.get(appPredictionSessionId);
        if (appPredictionSessionInfo == null) {
            return;
        }
        resolveService(appPredictionSessionId, true, appPredictionSessionInfo.mUsesPeopleService, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda8
            public final void run(android.os.IInterface iInterface) {
                ((android.service.appprediction.IPredictionService) iInterface).requestPredictionUpdate(appPredictionSessionId);
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onDestroyPredictionSessionLocked(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
        if (isDebug()) {
            android.util.Slog.d(TAG, "onDestroyPredictionSessionLocked(): sessionId=" + appPredictionSessionId);
        }
        com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo remove = this.mSessionInfos.remove(appPredictionSessionId);
        if (remove == null) {
            return;
        }
        resolveService(appPredictionSessionId, false, remove.mUsesPeopleService, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda3
            public final void run(android.os.IInterface iInterface) {
                ((android.service.appprediction.IPredictionService) iInterface).onDestroyPredictionSession(appPredictionSessionId);
            }
        });
        remove.destroy();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void requestServiceFeaturesLocked(@android.annotation.NonNull final android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull final android.os.IRemoteCallback iRemoteCallback) {
        com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo = this.mSessionInfos.get(appPredictionSessionId);
        if (appPredictionSessionInfo == null) {
            return;
        }
        resolveService(appPredictionSessionId, true, appPredictionSessionInfo.mUsesPeopleService, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.appprediction.AppPredictionPerUserService$$ExternalSyntheticLambda6
            public final void run(android.os.IInterface iInterface) {
                ((android.service.appprediction.IPredictionService) iInterface).requestServiceFeatures(appPredictionSessionId, iRemoteCallback);
            }
        });
    }

    @Override // com.android.server.appprediction.RemoteAppPredictionService.RemoteAppPredictionServiceCallbacks
    public void onFailureOrTimeout(boolean z) {
        if (isDebug()) {
            android.util.Slog.d(TAG, "onFailureOrTimeout(): timed out=" + z);
        }
    }

    @Override // com.android.server.appprediction.RemoteAppPredictionService.RemoteAppPredictionServiceCallbacks
    public void onConnectedStateChanged(boolean z) {
        if (isDebug()) {
            android.util.Slog.d(TAG, "onConnectedStateChanged(): connected=" + z);
        }
        if (z) {
            synchronized (this.mLock) {
                try {
                    if (this.mZombie) {
                        if (this.mRemoteService == null) {
                            android.util.Slog.w(TAG, "Cannot resurrect sessions because remote service is null");
                        } else {
                            this.mZombie = false;
                            resurrectSessionsLocked();
                        }
                    }
                } finally {
                }
            }
        }
    }

    public void onServiceDied(com.android.server.appprediction.RemoteAppPredictionService remoteAppPredictionService) {
        if (isDebug()) {
            android.util.Slog.w(TAG, "onServiceDied(): service=" + remoteAppPredictionService);
        }
        synchronized (this.mLock) {
            this.mZombie = true;
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
        synchronized (this.mLock) {
            this.mZombie = true;
        }
        this.mRemoteService = getRemoteServiceLocked();
        if (this.mRemoteService != null) {
            if (isDebug()) {
                android.util.Slog.d(TAG, "Rebinding to the new remote service.");
            }
            this.mRemoteService.reconnect();
        }
    }

    private void resurrectSessionsLocked() {
        int size = this.mSessionInfos.size();
        if (isDebug()) {
            android.util.Slog.d(TAG, "Resurrecting remote service (" + this.mRemoteService + ") on " + size + " sessions.");
        }
        for (com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo appPredictionSessionInfo : this.mSessionInfos.values()) {
            appPredictionSessionInfo.resurrectSessionLocked(this, appPredictionSessionInfo.mToken);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    protected boolean resolveService(@android.annotation.NonNull android.app.prediction.AppPredictionSessionId appPredictionSessionId, boolean z, boolean z2, @android.annotation.NonNull com.android.internal.infra.AbstractRemoteService.AsyncRequest<android.service.appprediction.IPredictionService> asyncRequest) {
        if (z2) {
            android.service.appprediction.IPredictionService iPredictionService = (android.service.appprediction.IPredictionService) com.android.server.LocalServices.getService(com.android.server.people.PeopleServiceInternal.class);
            if (iPredictionService != null) {
                try {
                    asyncRequest.run(iPredictionService);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Failed to invoke service:" + iPredictionService, e);
                }
            }
            return iPredictionService != null;
        }
        com.android.server.appprediction.RemoteAppPredictionService remoteServiceLocked = getRemoteServiceLocked();
        if (remoteServiceLocked != null) {
            if (z) {
                remoteServiceLocked.executeOnResolvedService(asyncRequest);
            } else {
                remoteServiceLocked.scheduleOnResolvedService(asyncRequest);
            }
        }
        return remoteServiceLocked != null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.appprediction.RemoteAppPredictionService getRemoteServiceLocked() {
        if (this.mRemoteService == null) {
            java.lang.String componentNameLocked = getComponentNameLocked();
            if (componentNameLocked == null) {
                if (((com.android.server.appprediction.AppPredictionManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "getRemoteServiceLocked(): not set");
                    return null;
                }
                return null;
            }
            this.mRemoteService = new com.android.server.appprediction.RemoteAppPredictionService(getContext(), "android.service.appprediction.AppPredictionService", android.content.ComponentName.unflattenFromString(componentNameLocked), this.mUserId, this, ((com.android.server.appprediction.AppPredictionManagerService) this.mMaster).isBindInstantServiceAllowed(), ((com.android.server.appprediction.AppPredictionManagerService) this.mMaster).verbose);
        }
        return this.mRemoteService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class AppPredictionSessionInfo {
        private static final boolean DEBUG = false;
        private final android.os.RemoteCallbackList<android.app.prediction.IPredictionCallback> mCallbacks = new android.os.RemoteCallbackList<>();

        @android.annotation.NonNull
        final android.os.IBinder.DeathRecipient mDeathRecipient;

        @android.annotation.NonNull
        private final android.app.prediction.AppPredictionContext mPredictionContext;

        @android.annotation.NonNull
        private final android.app.prediction.AppPredictionSessionId mSessionId;

        @android.annotation.NonNull
        final android.os.IBinder mToken;
        private final boolean mUsesPeopleService;

        AppPredictionSessionInfo(@android.annotation.NonNull android.app.prediction.AppPredictionSessionId appPredictionSessionId, @android.annotation.NonNull android.app.prediction.AppPredictionContext appPredictionContext, boolean z, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder.DeathRecipient deathRecipient) {
            this.mSessionId = appPredictionSessionId;
            this.mPredictionContext = appPredictionContext;
            this.mUsesPeopleService = z;
            this.mToken = iBinder;
            this.mDeathRecipient = deathRecipient;
        }

        void addCallbackLocked(android.app.prediction.IPredictionCallback iPredictionCallback) {
            this.mCallbacks.register(iPredictionCallback);
        }

        void removeCallbackLocked(android.app.prediction.IPredictionCallback iPredictionCallback) {
            this.mCallbacks.unregister(iPredictionCallback);
        }

        boolean linkToDeath() {
            try {
                this.mToken.linkToDeath(this.mDeathRecipient, 0);
                return true;
            } catch (android.os.RemoteException e) {
                return false;
            }
        }

        void destroy() {
            if (this.mToken != null) {
                this.mToken.unlinkToDeath(this.mDeathRecipient, 0);
            }
            this.mCallbacks.kill();
        }

        void resurrectSessionLocked(final com.android.server.appprediction.AppPredictionPerUserService appPredictionPerUserService, android.os.IBinder iBinder) {
            this.mCallbacks.getRegisteredCallbackCount();
            appPredictionPerUserService.onCreatePredictionSessionLocked(this.mPredictionContext, this.mSessionId, iBinder);
            this.mCallbacks.broadcast(new java.util.function.Consumer() { // from class: com.android.server.appprediction.AppPredictionPerUserService$AppPredictionSessionInfo$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.appprediction.AppPredictionPerUserService.AppPredictionSessionInfo.this.lambda$resurrectSessionLocked$0(appPredictionPerUserService, (android.app.prediction.IPredictionCallback) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$resurrectSessionLocked$0(com.android.server.appprediction.AppPredictionPerUserService appPredictionPerUserService, android.app.prediction.IPredictionCallback iPredictionCallback) {
            appPredictionPerUserService.registerPredictionUpdatesLocked(this.mSessionId, iPredictionCallback);
        }
    }
}
