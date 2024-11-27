package com.android.server.searchui;

/* loaded from: classes2.dex */
public class SearchUiPerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.searchui.SearchUiPerUserService, com.android.server.searchui.SearchUiManagerService> implements com.android.server.searchui.RemoteSearchUiService.RemoteSearchUiServiceCallbacks {
    private static final java.lang.String TAG = com.android.server.searchui.SearchUiPerUserService.class.getSimpleName();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.searchui.RemoteSearchUiService mRemoteService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.app.search.SearchSessionId, com.android.server.searchui.SearchUiPerUserService.SearchSessionInfo> mSessionInfos;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mZombie;

    protected SearchUiPerUserService(com.android.server.searchui.SearchUiManagerService searchUiManagerService, java.lang.Object obj, int i) {
        super(searchUiManagerService, obj, i);
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
            updateRemoteServiceLocked();
        }
        return updateLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onCreateSearchSessionLocked(@android.annotation.NonNull final android.app.search.SearchContext searchContext, @android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull android.os.IBinder iBinder) {
        if (resolveService(searchSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda1
            public final void run(android.os.IInterface iInterface) {
                ((android.service.search.ISearchUiService) iInterface).onCreateSearchSession(searchContext, searchSessionId);
            }
        }) && !this.mSessionInfos.containsKey(searchSessionId)) {
            com.android.server.searchui.SearchUiPerUserService.SearchSessionInfo searchSessionInfo = new com.android.server.searchui.SearchUiPerUserService.SearchSessionInfo(searchSessionId, searchContext, iBinder, new android.os.IBinder.DeathRecipient() { // from class: com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda2
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.searchui.SearchUiPerUserService.this.lambda$onCreateSearchSessionLocked$1(searchSessionId);
                }
            });
            if (searchSessionInfo.linkToDeath()) {
                this.mSessionInfos.put(searchSessionId, searchSessionInfo);
            } else {
                onDestroyLocked(searchSessionId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateSearchSessionLocked$1(android.app.search.SearchSessionId searchSessionId) {
        synchronized (this.mLock) {
            onDestroyLocked(searchSessionId);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifyLocked(@android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull final android.app.search.Query query, @android.annotation.NonNull final android.app.search.SearchTargetEvent searchTargetEvent) {
        if (this.mSessionInfos.get(searchSessionId) == null) {
            return;
        }
        resolveService(searchSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda0
            public final void run(android.os.IInterface iInterface) {
                ((android.service.search.ISearchUiService) iInterface).onNotifyEvent(searchSessionId, query, searchTargetEvent);
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void queryLocked(@android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull final android.app.search.Query query, @android.annotation.NonNull final android.app.search.ISearchCallback iSearchCallback) {
        if (this.mSessionInfos.get(searchSessionId) == null) {
            return;
        }
        resolveService(searchSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda6
            public final void run(android.os.IInterface iInterface) {
                ((android.service.search.ISearchUiService) iInterface).onQuery(searchSessionId, query, iSearchCallback);
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void registerEmptyQueryResultUpdateCallbackLocked(@android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull final android.app.search.ISearchCallback iSearchCallback) {
        com.android.server.searchui.SearchUiPerUserService.SearchSessionInfo searchSessionInfo = this.mSessionInfos.get(searchSessionId);
        if (searchSessionInfo != null && resolveService(searchSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda3
            public final void run(android.os.IInterface iInterface) {
                ((android.service.search.ISearchUiService) iInterface).onRegisterEmptyQueryResultUpdateCallback(searchSessionId, iSearchCallback);
            }
        })) {
            searchSessionInfo.addCallbackLocked(iSearchCallback);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void unregisterEmptyQueryResultUpdateCallbackLocked(@android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull final android.app.search.ISearchCallback iSearchCallback) {
        com.android.server.searchui.SearchUiPerUserService.SearchSessionInfo searchSessionInfo = this.mSessionInfos.get(searchSessionId);
        if (searchSessionInfo != null && resolveService(searchSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda4
            public final void run(android.os.IInterface iInterface) {
                ((android.service.search.ISearchUiService) iInterface).onUnregisterEmptyQueryResultUpdateCallback(searchSessionId, iSearchCallback);
            }
        })) {
            searchSessionInfo.removeCallbackLocked(iSearchCallback);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onDestroyLocked(@android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId) {
        if (isDebug()) {
            android.util.Slog.d(TAG, "onDestroyLocked(): sessionId=" + searchSessionId);
        }
        com.android.server.searchui.SearchUiPerUserService.SearchSessionInfo remove = this.mSessionInfos.remove(searchSessionId);
        if (remove == null) {
            return;
        }
        resolveService(searchSessionId, new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda5
            public final void run(android.os.IInterface iInterface) {
                ((android.service.search.ISearchUiService) iInterface).onDestroy(searchSessionId);
            }
        });
        remove.destroy();
    }

    @Override // com.android.server.searchui.RemoteSearchUiService.RemoteSearchUiServiceCallbacks
    public void onFailureOrTimeout(boolean z) {
        if (isDebug()) {
            android.util.Slog.d(TAG, "onFailureOrTimeout(): timed out=" + z);
        }
    }

    @Override // com.android.server.searchui.RemoteSearchUiService.RemoteSearchUiServiceCallbacks
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

    public void onServiceDied(com.android.server.searchui.RemoteSearchUiService remoteSearchUiService) {
        if (isDebug()) {
            android.util.Slog.w(TAG, "onServiceDied(): service=" + remoteSearchUiService);
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
        for (com.android.server.searchui.SearchUiPerUserService.SearchSessionInfo searchSessionInfo : this.mSessionInfos.values()) {
            searchSessionInfo.resurrectSessionLocked(this, searchSessionInfo.mToken);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    protected boolean resolveService(@android.annotation.NonNull android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull com.android.internal.infra.AbstractRemoteService.AsyncRequest<android.service.search.ISearchUiService> asyncRequest) {
        com.android.server.searchui.RemoteSearchUiService remoteServiceLocked = getRemoteServiceLocked();
        if (remoteServiceLocked != null) {
            remoteServiceLocked.executeOnResolvedService(asyncRequest);
        }
        return remoteServiceLocked != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public com.android.server.searchui.RemoteSearchUiService getRemoteServiceLocked() {
        if (this.mRemoteService == null) {
            java.lang.String componentNameLocked = getComponentNameLocked();
            if (componentNameLocked == null) {
                if (((com.android.server.searchui.SearchUiManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "getRemoteServiceLocked(): not set");
                    return null;
                }
                return null;
            }
            this.mRemoteService = new com.android.server.searchui.RemoteSearchUiService(getContext(), "android.service.search.SearchUiService", android.content.ComponentName.unflattenFromString(componentNameLocked), this.mUserId, this, ((com.android.server.searchui.SearchUiManagerService) this.mMaster).isBindInstantServiceAllowed(), ((com.android.server.searchui.SearchUiManagerService) this.mMaster).verbose);
        }
        return this.mRemoteService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SearchSessionInfo {
        private static final boolean DEBUG = true;
        private final android.os.RemoteCallbackList<android.app.search.ISearchCallback> mCallbacks = new android.os.RemoteCallbackList<>();

        @android.annotation.NonNull
        final android.os.IBinder.DeathRecipient mDeathRecipient;

        @android.annotation.NonNull
        private final android.app.search.SearchContext mSearchContext;

        @android.annotation.NonNull
        private final android.app.search.SearchSessionId mSessionId;

        @android.annotation.NonNull
        final android.os.IBinder mToken;

        SearchSessionInfo(@android.annotation.NonNull android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull android.app.search.SearchContext searchContext, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder.DeathRecipient deathRecipient) {
            android.util.Slog.d(com.android.server.searchui.SearchUiPerUserService.TAG, "Creating SearchSessionInfo for session Id=" + searchSessionId);
            this.mSessionId = searchSessionId;
            this.mSearchContext = searchContext;
            this.mToken = iBinder;
            this.mDeathRecipient = deathRecipient;
        }

        void addCallbackLocked(android.app.search.ISearchCallback iSearchCallback) {
            android.util.Slog.d(com.android.server.searchui.SearchUiPerUserService.TAG, "Storing callback for session Id=" + this.mSessionId + " and callback=" + iSearchCallback.asBinder());
            this.mCallbacks.register(iSearchCallback);
        }

        void removeCallbackLocked(android.app.search.ISearchCallback iSearchCallback) {
            android.util.Slog.d(com.android.server.searchui.SearchUiPerUserService.TAG, "Removing callback for session Id=" + this.mSessionId + " and callback=" + iSearchCallback.asBinder());
            this.mCallbacks.unregister(iSearchCallback);
        }

        boolean linkToDeath() {
            try {
                this.mToken.linkToDeath(this.mDeathRecipient, 0);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.searchui.SearchUiPerUserService.TAG, "Caller is dead before session can be started, sessionId: " + this.mSessionId);
                return false;
            }
        }

        void destroy() {
            android.util.Slog.d(com.android.server.searchui.SearchUiPerUserService.TAG, "Removing all callbacks for session Id=" + this.mSessionId + " and " + this.mCallbacks.getRegisteredCallbackCount() + " callbacks.");
            if (this.mToken != null) {
                this.mToken.unlinkToDeath(this.mDeathRecipient, 0);
            }
            this.mCallbacks.kill();
        }

        void resurrectSessionLocked(final com.android.server.searchui.SearchUiPerUserService searchUiPerUserService, android.os.IBinder iBinder) {
            int registeredCallbackCount = this.mCallbacks.getRegisteredCallbackCount();
            android.util.Slog.d(com.android.server.searchui.SearchUiPerUserService.TAG, "Resurrecting remote service (" + searchUiPerUserService.getRemoteServiceLocked() + ") for session Id=" + this.mSessionId + " and " + registeredCallbackCount + " callbacks.");
            searchUiPerUserService.onCreateSearchSessionLocked(this.mSearchContext, this.mSessionId, iBinder);
            this.mCallbacks.broadcast(new java.util.function.Consumer() { // from class: com.android.server.searchui.SearchUiPerUserService$SearchSessionInfo$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.searchui.SearchUiPerUserService.SearchSessionInfo.this.lambda$resurrectSessionLocked$0(searchUiPerUserService, (android.app.search.ISearchCallback) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$resurrectSessionLocked$0(com.android.server.searchui.SearchUiPerUserService searchUiPerUserService, android.app.search.ISearchCallback iSearchCallback) {
            searchUiPerUserService.registerEmptyQueryResultUpdateCallbackLocked(this.mSessionId, iSearchCallback);
        }
    }
}
