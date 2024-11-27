package com.android.server.searchui;

/* loaded from: classes2.dex */
public class SearchUiManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.searchui.SearchUiManagerService, com.android.server.searchui.SearchUiPerUserService> {
    private static final boolean DEBUG = false;
    private static final int MAX_TEMP_SERVICE_DURATION_MS = 120000;
    private static final java.lang.String TAG = com.android.server.searchui.SearchUiManagerService.class.getSimpleName();
    private com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;

    public SearchUiManagerService(android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_defaultOnDeviceIntelligenceDeviceConfigNamespace), null, 17);
        this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.searchui.SearchUiPerUserService newServiceLocked(int i, boolean z) {
        return new com.android.server.searchui.SearchUiPerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("search_ui", new com.android.server.searchui.SearchUiManagerService.SearchUiManagerStub());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_SEARCH_UI", TAG);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageUpdatedLocked(int i) {
        com.android.server.searchui.SearchUiPerUserService peekServiceForUserLocked = peekServiceForUserLocked(i);
        if (peekServiceForUserLocked != null) {
            peekServiceForUserLocked.onPackageUpdatedLocked();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageRestartedLocked(int i) {
        com.android.server.searchui.SearchUiPerUserService peekServiceForUserLocked = peekServiceForUserLocked(i);
        if (peekServiceForUserLocked != null) {
            peekServiceForUserLocked.onPackageRestartedLocked();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected int getMaximumTemporaryServiceDurationMs() {
        return MAX_TEMP_SERVICE_DURATION_MS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SearchUiManagerStub extends android.app.search.ISearchUiManager.Stub {
        private SearchUiManagerStub() {
        }

        public void createSearchSession(@android.annotation.NonNull final android.app.search.SearchContext searchContext, @android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull final android.os.IBinder iBinder) {
            runForUserLocked("createSearchSession", searchSessionId, new java.util.function.Consumer() { // from class: com.android.server.searchui.SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.searchui.SearchUiPerUserService) obj).onCreateSearchSessionLocked(searchContext, searchSessionId, iBinder);
                }
            });
        }

        public void notifyEvent(@android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull final android.app.search.Query query, @android.annotation.NonNull final android.app.search.SearchTargetEvent searchTargetEvent) {
            runForUserLocked("notifyEvent", searchSessionId, new java.util.function.Consumer() { // from class: com.android.server.searchui.SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.searchui.SearchUiPerUserService) obj).notifyLocked(searchSessionId, query, searchTargetEvent);
                }
            });
        }

        public void query(@android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull final android.app.search.Query query, final android.app.search.ISearchCallback iSearchCallback) {
            runForUserLocked("query", searchSessionId, new java.util.function.Consumer() { // from class: com.android.server.searchui.SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.searchui.SearchUiPerUserService) obj).queryLocked(searchSessionId, query, iSearchCallback);
                }
            });
        }

        public void registerEmptyQueryResultUpdateCallback(@android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull final android.app.search.ISearchCallback iSearchCallback) {
            runForUserLocked("registerEmptyQueryResultUpdateCallback", searchSessionId, new java.util.function.Consumer() { // from class: com.android.server.searchui.SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.searchui.SearchUiPerUserService) obj).registerEmptyQueryResultUpdateCallbackLocked(searchSessionId, iSearchCallback);
                }
            });
        }

        public void unregisterEmptyQueryResultUpdateCallback(@android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull final android.app.search.ISearchCallback iSearchCallback) {
            runForUserLocked("unregisterEmptyQueryResultUpdateCallback", searchSessionId, new java.util.function.Consumer() { // from class: com.android.server.searchui.SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.searchui.SearchUiPerUserService) obj).unregisterEmptyQueryResultUpdateCallbackLocked(searchSessionId, iSearchCallback);
                }
            });
        }

        public void destroySearchSession(@android.annotation.NonNull final android.app.search.SearchSessionId searchSessionId) {
            runForUserLocked("destroySearchSession", searchSessionId, new java.util.function.Consumer() { // from class: com.android.server.searchui.SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.searchui.SearchUiPerUserService) obj).onDestroyLocked(searchSessionId);
                }
            });
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) {
            new com.android.server.searchui.SearchUiManagerServiceShellCommand(com.android.server.searchui.SearchUiManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        private void runForUserLocked(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.app.search.SearchSessionId searchSessionId, @android.annotation.NonNull java.util.function.Consumer<com.android.server.searchui.SearchUiPerUserService> consumer) {
            int handleIncomingUser = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), searchSessionId.getUserId(), false, 0, (java.lang.String) null, (java.lang.String) null);
            if (!((com.android.server.infra.AbstractMasterSystemService) com.android.server.searchui.SearchUiManagerService.this).mServiceNameResolver.isTemporary(handleIncomingUser) && !com.android.server.searchui.SearchUiManagerService.this.mActivityTaskManagerInternal.isCallerRecents(android.os.Binder.getCallingUid())) {
                java.lang.String str2 = "Permission Denial: " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid();
                android.util.Slog.w(com.android.server.searchui.SearchUiManagerService.TAG, str2);
                throw new java.lang.SecurityException(str2);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.searchui.SearchUiManagerService.this).mLock) {
                    consumer.accept((com.android.server.searchui.SearchUiPerUserService) com.android.server.searchui.SearchUiManagerService.this.getServiceForUserLocked(handleIncomingUser));
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }
}
