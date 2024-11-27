package com.android.server.people;

/* loaded from: classes2.dex */
public class PeopleService extends com.android.server.SystemService {
    private static final java.lang.String TAG = "PeopleService";
    private com.android.server.people.PeopleService.ConversationListenerHelper mLazyConversationListenerHelper;
    private com.android.server.people.data.DataManager mLazyDataManager;
    private android.content.pm.PackageManagerInternal mPackageManagerInternal;

    @com.android.internal.annotations.VisibleForTesting
    final android.os.IBinder mService;

    public PeopleService(android.content.Context context) {
        super(context);
        this.mService = new android.app.people.IPeopleManager.Stub() { // from class: com.android.server.people.PeopleService.1
            public android.app.people.ConversationChannel getConversation(java.lang.String str, int i, java.lang.String str2) {
                com.android.server.people.PeopleService.this.enforceSystemRootOrSystemUI(com.android.server.people.PeopleService.this.getContext(), "get conversation");
                return com.android.server.people.PeopleService.this.getDataManager().getConversation(str, i, str2);
            }

            public android.content.pm.ParceledListSlice<android.app.people.ConversationChannel> getRecentConversations() {
                com.android.server.people.PeopleService.this.enforceSystemRootOrSystemUI(com.android.server.people.PeopleService.this.getContext(), "get recent conversations");
                return new android.content.pm.ParceledListSlice<>(com.android.server.people.PeopleService.this.getDataManager().getRecentConversations(android.os.Binder.getCallingUserHandle().getIdentifier()));
            }

            public void removeRecentConversation(java.lang.String str, int i, java.lang.String str2) {
                com.android.server.people.PeopleService.enforceSystemOrRoot("remove a recent conversation");
                com.android.server.people.PeopleService.this.getDataManager().removeRecentConversation(str, i, str2, android.os.Binder.getCallingUserHandle().getIdentifier());
            }

            public void removeAllRecentConversations() {
                com.android.server.people.PeopleService.enforceSystemOrRoot("remove all recent conversations");
                com.android.server.people.PeopleService.this.getDataManager().removeAllRecentConversations(android.os.Binder.getCallingUserHandle().getIdentifier());
            }

            public boolean isConversation(java.lang.String str, int i, java.lang.String str2) {
                enforceHasReadPeopleDataPermission();
                com.android.server.people.PeopleService.this.handleIncomingUser(i);
                return com.android.server.people.PeopleService.this.getDataManager().isConversation(str, i, str2);
            }

            private void enforceHasReadPeopleDataPermission() throws java.lang.SecurityException {
                if (com.android.server.people.PeopleService.this.getContext().checkCallingPermission("android.permission.READ_PEOPLE_DATA") != 0) {
                    throw new java.lang.SecurityException("Caller doesn't have READ_PEOPLE_DATA permission.");
                }
            }

            public long getLastInteraction(java.lang.String str, int i, java.lang.String str2) {
                com.android.server.people.PeopleService.this.enforceSystemRootOrSystemUI(com.android.server.people.PeopleService.this.getContext(), "get last interaction");
                return com.android.server.people.PeopleService.this.getDataManager().getLastInteraction(str, i, str2);
            }

            public void addOrUpdateStatus(java.lang.String str, int i, java.lang.String str2, android.app.people.ConversationStatus conversationStatus) {
                com.android.server.people.PeopleService.this.handleIncomingUser(i);
                com.android.server.people.PeopleService.this.checkCallerIsSameApp(str);
                if (conversationStatus.getStartTimeMillis() > java.lang.System.currentTimeMillis()) {
                    throw new java.lang.IllegalArgumentException("Start time must be in the past");
                }
                com.android.server.people.PeopleService.this.getDataManager().addOrUpdateStatus(str, i, str2, conversationStatus);
            }

            public void clearStatus(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) {
                com.android.server.people.PeopleService.this.handleIncomingUser(i);
                com.android.server.people.PeopleService.this.checkCallerIsSameApp(str);
                com.android.server.people.PeopleService.this.getDataManager().clearStatus(str, i, str2, str3);
            }

            public void clearStatuses(java.lang.String str, int i, java.lang.String str2) {
                com.android.server.people.PeopleService.this.handleIncomingUser(i);
                com.android.server.people.PeopleService.this.checkCallerIsSameApp(str);
                com.android.server.people.PeopleService.this.getDataManager().clearStatuses(str, i, str2);
            }

            public android.content.pm.ParceledListSlice<android.app.people.ConversationStatus> getStatuses(java.lang.String str, int i, java.lang.String str2) {
                com.android.server.people.PeopleService.this.handleIncomingUser(i);
                if (!com.android.server.people.PeopleService.isSystemOrRoot()) {
                    com.android.server.people.PeopleService.this.checkCallerIsSameApp(str);
                }
                return new android.content.pm.ParceledListSlice<>(com.android.server.people.PeopleService.this.getDataManager().getStatuses(str, i, str2));
            }

            public void registerConversationListener(java.lang.String str, int i, java.lang.String str2, android.app.people.IConversationListener iConversationListener) {
                com.android.server.people.PeopleService.this.enforceSystemRootOrSystemUI(com.android.server.people.PeopleService.this.getContext(), "register conversation listener");
                com.android.server.people.PeopleService.this.getConversationListenerHelper().addConversationListener(new com.android.server.people.PeopleService.ListenerKey(str, java.lang.Integer.valueOf(i), str2), iConversationListener);
            }

            public void unregisterConversationListener(android.app.people.IConversationListener iConversationListener) {
                com.android.server.people.PeopleService.this.enforceSystemRootOrSystemUI(com.android.server.people.PeopleService.this.getContext(), "unregister conversation listener");
                com.android.server.people.PeopleService.this.getConversationListenerHelper().removeConversationListener(iConversationListener);
            }
        };
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.people.PeopleService.ConversationListenerHelper getConversationListenerHelper() {
        if (this.mLazyConversationListenerHelper == null) {
            initLazyStuff();
        }
        return this.mLazyConversationListenerHelper;
    }

    private synchronized void initLazyStuff() {
        if (this.mLazyDataManager == null) {
            this.mLazyDataManager = new com.android.server.people.data.DataManager(getContext());
            this.mLazyDataManager.initialize();
            this.mLazyConversationListenerHelper = new com.android.server.people.PeopleService.ConversationListenerHelper();
            this.mLazyDataManager.addConversationsListener(this.mLazyConversationListenerHelper);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.people.data.DataManager getDataManager() {
        if (this.mLazyDataManager == null) {
            initLazyStuff();
        }
        return this.mLazyDataManager;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        onStart(false);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void onStart(boolean z) {
        if (!z) {
            publishBinderService("people", this.mService);
        }
        publishLocalService(com.android.server.people.PeopleServiceInternal.class, new com.android.server.people.PeopleService.LocalService());
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocked(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        getDataManager().onUserUnlocked(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        getDataManager().onUserStopping(targetUser.getUserIdentifier());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void enforceSystemOrRoot(java.lang.String str) {
        if (!isSystemOrRoot()) {
            throw new java.lang.SecurityException("Only system may " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSystemOrRoot() {
        int callingUid = android.os.Binder.getCallingUid();
        return android.os.UserHandle.isSameApp(callingUid, 1000) || callingUid == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int handleIncomingUser(int i) {
        try {
            return android.app.ActivityManager.getService().handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, true, true, "", (java.lang.String) null);
        } catch (android.os.RemoteException e) {
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCallerIsSameApp(java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (this.mPackageManagerInternal.getPackageUid(str, 0L, android.os.UserHandle.getUserId(callingUid)) != callingUid) {
            throw new java.lang.SecurityException("Calling uid " + callingUid + " cannot query eventsfor package " + str);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void enforceSystemRootOrSystemUI(android.content.Context context, java.lang.String str) {
        if (isSystemOrRoot()) {
            return;
        }
        context.enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", str);
    }

    public interface ConversationsListener {
        default void onConversationsUpdate(@android.annotation.NonNull java.util.List<android.app.people.ConversationChannel> list) {
        }
    }

    public static class ConversationListenerHelper implements com.android.server.people.PeopleService.ConversationsListener {

        @com.android.internal.annotations.VisibleForTesting
        final android.os.RemoteCallbackList<android.app.people.IConversationListener> mListeners = new android.os.RemoteCallbackList<>();

        ConversationListenerHelper() {
        }

        public synchronized void addConversationListener(com.android.server.people.PeopleService.ListenerKey listenerKey, android.app.people.IConversationListener iConversationListener) {
            this.mListeners.unregister(iConversationListener);
            this.mListeners.register(iConversationListener, listenerKey);
        }

        public synchronized void removeConversationListener(android.app.people.IConversationListener iConversationListener) {
            this.mListeners.unregister(iConversationListener);
        }

        @Override // com.android.server.people.PeopleService.ConversationsListener
        public void onConversationsUpdate(java.util.List<android.app.people.ConversationChannel> list) {
            int beginBroadcast = this.mListeners.beginBroadcast();
            if (beginBroadcast == 0) {
                return;
            }
            java.util.HashMap hashMap = new java.util.HashMap();
            for (android.app.people.ConversationChannel conversationChannel : list) {
                hashMap.put(getListenerKey(conversationChannel), conversationChannel);
            }
            for (int i = 0; i < beginBroadcast; i++) {
                com.android.server.people.PeopleService.ListenerKey listenerKey = (com.android.server.people.PeopleService.ListenerKey) this.mListeners.getBroadcastCookie(i);
                if (hashMap.containsKey(listenerKey)) {
                    try {
                        this.mListeners.getBroadcastItem(i).onConversationUpdate((android.app.people.ConversationChannel) hashMap.get(listenerKey));
                    } catch (android.os.RemoteException e) {
                    }
                }
            }
            this.mListeners.finishBroadcast();
        }

        private com.android.server.people.PeopleService.ListenerKey getListenerKey(android.app.people.ConversationChannel conversationChannel) {
            android.content.pm.ShortcutInfo shortcutInfo = conversationChannel.getShortcutInfo();
            return new com.android.server.people.PeopleService.ListenerKey(shortcutInfo.getPackage(), java.lang.Integer.valueOf(shortcutInfo.getUserId()), shortcutInfo.getId());
        }
    }

    private static class ListenerKey {
        private final java.lang.String mPackageName;
        private final java.lang.String mShortcutId;
        private final java.lang.Integer mUserId;

        ListenerKey(java.lang.String str, java.lang.Integer num, java.lang.String str2) {
            this.mPackageName = str;
            this.mUserId = num;
            this.mShortcutId = str2;
        }

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        public java.lang.Integer getUserId() {
            return this.mUserId;
        }

        public java.lang.String getShortcutId() {
            return this.mShortcutId;
        }

        public boolean equals(java.lang.Object obj) {
            com.android.server.people.PeopleService.ListenerKey listenerKey = (com.android.server.people.PeopleService.ListenerKey) obj;
            return listenerKey.getPackageName().equals(this.mPackageName) && java.util.Objects.equals(listenerKey.getUserId(), this.mUserId) && listenerKey.getShortcutId().equals(this.mShortcutId);
        }

        public int hashCode() {
            return this.mPackageName.hashCode() + this.mUserId.hashCode() + this.mShortcutId.hashCode();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class LocalService extends com.android.server.people.PeopleServiceInternal {
        private java.util.Map<android.app.prediction.AppPredictionSessionId, com.android.server.people.SessionInfo> mSessions = new android.util.ArrayMap();

        LocalService() {
        }

        public void onCreatePredictionSession(android.app.prediction.AppPredictionContext appPredictionContext, android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
            this.mSessions.put(appPredictionSessionId, new com.android.server.people.SessionInfo(appPredictionContext, com.android.server.people.PeopleService.this.getDataManager(), appPredictionSessionId.getUserId(), com.android.server.people.PeopleService.this.getContext()));
        }

        public void notifyAppTargetEvent(android.app.prediction.AppPredictionSessionId appPredictionSessionId, final android.app.prediction.AppTargetEvent appTargetEvent) {
            runForSession(appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.people.PeopleService$LocalService$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.PeopleService.LocalService.lambda$notifyAppTargetEvent$0(appTargetEvent, (com.android.server.people.SessionInfo) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$notifyAppTargetEvent$0(android.app.prediction.AppTargetEvent appTargetEvent, com.android.server.people.SessionInfo sessionInfo) {
            sessionInfo.getPredictor().onAppTargetEvent(appTargetEvent);
        }

        public void notifyLaunchLocationShown(android.app.prediction.AppPredictionSessionId appPredictionSessionId, final java.lang.String str, final android.content.pm.ParceledListSlice parceledListSlice) {
            runForSession(appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.people.PeopleService$LocalService$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.PeopleService.LocalService.lambda$notifyLaunchLocationShown$1(str, parceledListSlice, (com.android.server.people.SessionInfo) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$notifyLaunchLocationShown$1(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, com.android.server.people.SessionInfo sessionInfo) {
            sessionInfo.getPredictor().onLaunchLocationShown(str, parceledListSlice.getList());
        }

        public void sortAppTargets(android.app.prediction.AppPredictionSessionId appPredictionSessionId, final android.content.pm.ParceledListSlice parceledListSlice, final android.app.prediction.IPredictionCallback iPredictionCallback) {
            runForSession(appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.people.PeopleService$LocalService$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.PeopleService.LocalService.this.lambda$sortAppTargets$3(parceledListSlice, iPredictionCallback, (com.android.server.people.SessionInfo) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sortAppTargets$3(android.content.pm.ParceledListSlice parceledListSlice, final android.app.prediction.IPredictionCallback iPredictionCallback, com.android.server.people.SessionInfo sessionInfo) {
            sessionInfo.getPredictor().onSortAppTargets(parceledListSlice.getList(), new java.util.function.Consumer() { // from class: com.android.server.people.PeopleService$LocalService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.PeopleService.LocalService.this.lambda$sortAppTargets$2(iPredictionCallback, (java.util.List) obj);
                }
            });
        }

        public void registerPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, final android.app.prediction.IPredictionCallback iPredictionCallback) {
            runForSession(appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.people.PeopleService$LocalService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.people.SessionInfo) obj).addCallback(iPredictionCallback);
                }
            });
        }

        public void unregisterPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, final android.app.prediction.IPredictionCallback iPredictionCallback) {
            runForSession(appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.people.PeopleService$LocalService$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.people.SessionInfo) obj).removeCallback(iPredictionCallback);
                }
            });
        }

        public void requestPredictionUpdate(android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
            runForSession(appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.people.PeopleService$LocalService$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.PeopleService.LocalService.lambda$requestPredictionUpdate$6((com.android.server.people.SessionInfo) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$requestPredictionUpdate$6(com.android.server.people.SessionInfo sessionInfo) {
            sessionInfo.getPredictor().onRequestPredictionUpdate();
        }

        public void onDestroyPredictionSession(final android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
            runForSession(appPredictionSessionId, new java.util.function.Consumer() { // from class: com.android.server.people.PeopleService$LocalService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.PeopleService.LocalService.this.lambda$onDestroyPredictionSession$7(appPredictionSessionId, (com.android.server.people.SessionInfo) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDestroyPredictionSession$7(android.app.prediction.AppPredictionSessionId appPredictionSessionId, com.android.server.people.SessionInfo sessionInfo) {
            sessionInfo.onDestroy();
            this.mSessions.remove(appPredictionSessionId);
        }

        @Override // com.android.server.people.PeopleServiceInternal
        public void pruneDataForUser(int i, @android.annotation.NonNull android.os.CancellationSignal cancellationSignal) {
            com.android.server.people.PeopleService.this.getDataManager().pruneDataForUser(i, cancellationSignal);
        }

        @Override // com.android.server.people.PeopleServiceInternal
        @android.annotation.Nullable
        public byte[] getBackupPayload(int i) {
            return com.android.server.people.PeopleService.this.getDataManager().getBackupPayload(i);
        }

        @Override // com.android.server.people.PeopleServiceInternal
        public void restore(int i, @android.annotation.NonNull byte[] bArr) {
            com.android.server.people.PeopleService.this.getDataManager().restore(i, bArr);
        }

        public void requestServiceFeatures(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.os.IRemoteCallback iRemoteCallback) {
        }

        @com.android.internal.annotations.VisibleForTesting
        com.android.server.people.SessionInfo getSessionInfo(android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
            return this.mSessions.get(appPredictionSessionId);
        }

        private void runForSession(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.util.function.Consumer<com.android.server.people.SessionInfo> consumer) {
            com.android.server.people.SessionInfo sessionInfo = this.mSessions.get(appPredictionSessionId);
            if (sessionInfo == null) {
                android.util.Slog.e(com.android.server.people.PeopleService.TAG, "Failed to find the session: " + appPredictionSessionId);
                return;
            }
            consumer.accept(sessionInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: invokePredictionCallback, reason: merged with bridge method [inline-methods] */
        public void lambda$sortAppTargets$2(android.app.prediction.IPredictionCallback iPredictionCallback, java.util.List<android.app.prediction.AppTarget> list) {
            try {
                iPredictionCallback.onResult(new android.content.pm.ParceledListSlice(list));
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.people.PeopleService.TAG, "Failed to calling callback" + e);
            }
        }
    }
}
