package com.android.server.smartspace;

/* loaded from: classes2.dex */
public class SmartspacePerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.smartspace.SmartspacePerUserService, com.android.server.smartspace.SmartspaceManagerService> implements com.android.server.smartspace.RemoteSmartspaceService.RemoteSmartspaceServiceCallbacks {
    private static final java.lang.String TAG = com.android.server.smartspace.SmartspacePerUserService.class.getSimpleName();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.smartspace.RemoteSmartspaceService mRemoteService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.app.smartspace.SmartspaceSessionId, com.android.server.smartspace.SmartspacePerUserService.SmartspaceSessionInfo> mSessionInfos;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mZombie;

    protected SmartspacePerUserService(com.android.server.smartspace.SmartspaceManagerService smartspaceManagerService, java.lang.Object obj, int i) {
        super(smartspaceManagerService, obj, i);
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
        if (updateLocked) {
            if (isEnabledLocked()) {
                resurrectSessionsLocked();
            } else {
                updateRemoteServiceLocked();
            }
        }
        return updateLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onCreateSmartspaceSessionLocked(@android.annotation.NonNull final android.app.smartspace.SmartspaceConfig smartspaceConfig, @android.annotation.NonNull final android.app.smartspace.SmartspaceSessionId smartspaceSessionId, @android.annotation.NonNull android.os.IBinder iBinder) {
        if (resolveService(smartspaceSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda2
            public final void run(android.os.IInterface iInterface) {
                ((android.service.smartspace.ISmartspaceService) iInterface).onCreateSmartspaceSession(smartspaceConfig, smartspaceSessionId);
            }
        }) && !this.mSessionInfos.containsKey(smartspaceSessionId)) {
            com.android.server.smartspace.SmartspacePerUserService.SmartspaceSessionInfo smartspaceSessionInfo = new com.android.server.smartspace.SmartspacePerUserService.SmartspaceSessionInfo(smartspaceSessionId, smartspaceConfig, iBinder, new android.os.IBinder.DeathRecipient() { // from class: com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda3
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.smartspace.SmartspacePerUserService.this.lambda$onCreateSmartspaceSessionLocked$1(smartspaceSessionId);
                }
            });
            if (smartspaceSessionInfo.linkToDeath()) {
                this.mSessionInfos.put(smartspaceSessionId, smartspaceSessionInfo);
            } else {
                onDestroyLocked(smartspaceSessionId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateSmartspaceSessionLocked$1(android.app.smartspace.SmartspaceSessionId smartspaceSessionId) {
        synchronized (this.mLock) {
            onDestroyLocked(smartspaceSessionId);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifySmartspaceEventLocked(@android.annotation.NonNull final android.app.smartspace.SmartspaceSessionId smartspaceSessionId, @android.annotation.NonNull final android.app.smartspace.SmartspaceTargetEvent smartspaceTargetEvent) {
        if (this.mSessionInfos.get(smartspaceSessionId) == null) {
            return;
        }
        resolveService(smartspaceSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda1
            public final void run(android.os.IInterface iInterface) {
                ((android.service.smartspace.ISmartspaceService) iInterface).notifySmartspaceEvent(smartspaceSessionId, smartspaceTargetEvent);
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void requestSmartspaceUpdateLocked(@android.annotation.NonNull final android.app.smartspace.SmartspaceSessionId smartspaceSessionId) {
        if (this.mSessionInfos.get(smartspaceSessionId) == null) {
            return;
        }
        resolveService(smartspaceSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda6
            public final void run(android.os.IInterface iInterface) {
                ((android.service.smartspace.ISmartspaceService) iInterface).requestSmartspaceUpdate(smartspaceSessionId);
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void registerSmartspaceUpdatesLocked(@android.annotation.NonNull final android.app.smartspace.SmartspaceSessionId smartspaceSessionId, @android.annotation.NonNull final android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
        com.android.server.smartspace.SmartspacePerUserService.SmartspaceSessionInfo smartspaceSessionInfo = this.mSessionInfos.get(smartspaceSessionId);
        if (smartspaceSessionInfo != null && resolveService(smartspaceSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda5
            public final void run(android.os.IInterface iInterface) {
                ((android.service.smartspace.ISmartspaceService) iInterface).registerSmartspaceUpdates(smartspaceSessionId, iSmartspaceCallback);
            }
        })) {
            smartspaceSessionInfo.addCallbackLocked(iSmartspaceCallback);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void unregisterSmartspaceUpdatesLocked(@android.annotation.NonNull final android.app.smartspace.SmartspaceSessionId smartspaceSessionId, @android.annotation.NonNull final android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
        com.android.server.smartspace.SmartspacePerUserService.SmartspaceSessionInfo smartspaceSessionInfo = this.mSessionInfos.get(smartspaceSessionId);
        if (smartspaceSessionInfo != null && resolveService(smartspaceSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda4
            public final void run(android.os.IInterface iInterface) {
                ((android.service.smartspace.ISmartspaceService) iInterface).unregisterSmartspaceUpdates(smartspaceSessionId, iSmartspaceCallback);
            }
        })) {
            smartspaceSessionInfo.removeCallbackLocked(iSmartspaceCallback);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onDestroyLocked(@android.annotation.NonNull final android.app.smartspace.SmartspaceSessionId smartspaceSessionId) {
        if (isDebug()) {
            android.util.Slog.d(TAG, "onDestroyLocked(): sessionId=" + smartspaceSessionId);
        }
        com.android.server.smartspace.SmartspacePerUserService.SmartspaceSessionInfo remove = this.mSessionInfos.remove(smartspaceSessionId);
        if (remove == null) {
            return;
        }
        resolveService(smartspaceSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda0
            public final void run(android.os.IInterface iInterface) {
                ((android.service.smartspace.ISmartspaceService) iInterface).onDestroySmartspaceSession(smartspaceSessionId);
            }
        });
        remove.destroy();
    }

    @Override // com.android.server.smartspace.RemoteSmartspaceService.RemoteSmartspaceServiceCallbacks
    public void onFailureOrTimeout(boolean z) {
        if (isDebug()) {
            android.util.Slog.d(TAG, "onFailureOrTimeout(): timed out=" + z);
        }
    }

    @Override // com.android.server.smartspace.RemoteSmartspaceService.RemoteSmartspaceServiceCallbacks
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

    public void onServiceDied(com.android.server.smartspace.RemoteSmartspaceService remoteSmartspaceService) {
        if (isDebug()) {
            android.util.Slog.w(TAG, "onServiceDied(): service=" + remoteSmartspaceService);
        }
        synchronized (this.mLock) {
            this.mZombie = true;
        }
        updateRemoteServiceLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateRemoteServiceLocked() {
        if (this.mRemoteService != null) {
            this.mRemoteService.destroy();
            this.mRemoteService = null;
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
        for (com.android.server.smartspace.SmartspacePerUserService.SmartspaceSessionInfo smartspaceSessionInfo : this.mSessionInfos.values()) {
            smartspaceSessionInfo.resurrectSessionLocked(this, smartspaceSessionInfo.mToken);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    protected boolean resolveService(@android.annotation.NonNull android.app.smartspace.SmartspaceSessionId smartspaceSessionId, @android.annotation.NonNull com.android.internal.infra.AbstractRemoteService.AsyncRequest<android.service.smartspace.ISmartspaceService> asyncRequest) {
        com.android.server.smartspace.RemoteSmartspaceService remoteServiceLocked = getRemoteServiceLocked();
        if (remoteServiceLocked != null) {
            remoteServiceLocked.executeOnResolvedService(asyncRequest);
        }
        return remoteServiceLocked != null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.smartspace.RemoteSmartspaceService getRemoteServiceLocked() {
        if (this.mRemoteService == null) {
            java.lang.String componentNameLocked = getComponentNameLocked();
            if (componentNameLocked == null) {
                if (((com.android.server.smartspace.SmartspaceManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "getRemoteServiceLocked(): not set");
                    return null;
                }
                return null;
            }
            this.mRemoteService = new com.android.server.smartspace.RemoteSmartspaceService(getContext(), "android.service.smartspace.SmartspaceService", android.content.ComponentName.unflattenFromString(componentNameLocked), this.mUserId, this, ((com.android.server.smartspace.SmartspaceManagerService) this.mMaster).isBindInstantServiceAllowed(), ((com.android.server.smartspace.SmartspaceManagerService) this.mMaster).verbose);
        }
        return this.mRemoteService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SmartspaceSessionInfo {
        private static final boolean DEBUG = false;
        private final android.os.RemoteCallbackList<android.app.smartspace.ISmartspaceCallback> mCallbacks = new android.os.RemoteCallbackList<>();

        @android.annotation.NonNull
        final android.os.IBinder.DeathRecipient mDeathRecipient;

        @android.annotation.NonNull
        private final android.app.smartspace.SmartspaceSessionId mSessionId;

        @android.annotation.NonNull
        private final android.app.smartspace.SmartspaceConfig mSmartspaceConfig;

        @android.annotation.NonNull
        final android.os.IBinder mToken;

        SmartspaceSessionInfo(@android.annotation.NonNull android.app.smartspace.SmartspaceSessionId smartspaceSessionId, @android.annotation.NonNull android.app.smartspace.SmartspaceConfig smartspaceConfig, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder.DeathRecipient deathRecipient) {
            this.mSessionId = smartspaceSessionId;
            this.mSmartspaceConfig = smartspaceConfig;
            this.mToken = iBinder;
            this.mDeathRecipient = deathRecipient;
        }

        void addCallbackLocked(android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
            this.mCallbacks.register(iSmartspaceCallback);
        }

        void removeCallbackLocked(android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
            this.mCallbacks.unregister(iSmartspaceCallback);
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

        void resurrectSessionLocked(final com.android.server.smartspace.SmartspacePerUserService smartspacePerUserService, android.os.IBinder iBinder) {
            this.mCallbacks.getRegisteredCallbackCount();
            smartspacePerUserService.onCreateSmartspaceSessionLocked(this.mSmartspaceConfig, this.mSessionId, iBinder);
            this.mCallbacks.broadcast(new java.util.function.Consumer() { // from class: com.android.server.smartspace.SmartspacePerUserService$SmartspaceSessionInfo$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.smartspace.SmartspacePerUserService.SmartspaceSessionInfo.this.lambda$resurrectSessionLocked$0(smartspacePerUserService, (android.app.smartspace.ISmartspaceCallback) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$resurrectSessionLocked$0(com.android.server.smartspace.SmartspacePerUserService smartspacePerUserService, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
            smartspacePerUserService.registerSmartspaceUpdatesLocked(this.mSessionId, iSmartspaceCallback);
        }
    }
}
