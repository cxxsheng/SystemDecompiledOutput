package com.android.server.people.data;

/* loaded from: classes2.dex */
public class DataManager {
    private static final boolean DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_CACHED_RECENT_SHORTCUTS = 30;
    private static final long QUERY_EVENTS_MAX_AGE_MS = 300000;
    private static final long RECENT_NOTIFICATIONS_MAX_AGE_MS = 864000000;
    private static final java.lang.String TAG = "DataManager";
    private static final long USAGE_STATS_QUERY_INTERVAL_SEC = 120;
    private final android.util.SparseArray<android.content.BroadcastReceiver> mBroadcastReceivers;
    private android.database.ContentObserver mCallLogContentObserver;
    private final android.util.SparseArray<android.database.ContentObserver> mContactsContentObservers;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<com.android.server.people.PeopleService.ConversationsListener> mConversationsListeners;
    private final android.os.Handler mHandler;
    private final com.android.server.people.data.DataManager.Injector mInjector;
    private final java.lang.Object mLock;
    private android.database.ContentObserver mMmsSmsContentObserver;
    private final android.util.SparseArray<com.android.server.people.data.DataManager.NotificationListener> mNotificationListeners;
    private com.android.server.notification.NotificationManagerInternal mNotificationManagerInternal;
    private android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final android.util.SparseArray<com.android.internal.content.PackageMonitor> mPackageMonitors;
    private final java.util.concurrent.ScheduledExecutorService mScheduledExecutor;
    private android.content.pm.ShortcutServiceInternal mShortcutServiceInternal;
    private com.android.server.people.data.ConversationStatusExpirationBroadcastReceiver mStatusExpReceiver;
    private final android.util.SparseArray<java.util.concurrent.ScheduledFuture<?>> mUsageStatsQueryFutures;
    private final android.util.SparseArray<com.android.server.people.data.UserData> mUserDataArray;
    private android.os.UserManager mUserManager;

    public DataManager(android.content.Context context) {
        this(context, new com.android.server.people.data.DataManager.Injector(), com.android.internal.os.BackgroundThread.get().getLooper());
    }

    DataManager(android.content.Context context, com.android.server.people.data.DataManager.Injector injector, android.os.Looper looper) {
        this.mLock = new java.lang.Object();
        this.mUserDataArray = new android.util.SparseArray<>();
        this.mBroadcastReceivers = new android.util.SparseArray<>();
        this.mContactsContentObservers = new android.util.SparseArray<>();
        this.mUsageStatsQueryFutures = new android.util.SparseArray<>();
        this.mNotificationListeners = new android.util.SparseArray<>();
        this.mPackageMonitors = new android.util.SparseArray<>();
        this.mConversationsListeners = new java.util.ArrayList(1);
        this.mContext = context;
        this.mInjector = injector;
        this.mScheduledExecutor = this.mInjector.createScheduledExecutor();
        this.mHandler = new android.os.Handler(looper);
    }

    public void initialize() {
        this.mShortcutServiceInternal = (android.content.pm.ShortcutServiceInternal) com.android.server.LocalServices.getService(android.content.pm.ShortcutServiceInternal.class);
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mNotificationManagerInternal = (com.android.server.notification.NotificationManagerInternal) com.android.server.LocalServices.getService(com.android.server.notification.NotificationManagerInternal.class);
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        this.mShortcutServiceInternal.addShortcutChangeCallback(new com.android.server.people.data.DataManager.ShortcutServiceCallback());
        this.mStatusExpReceiver = new com.android.server.people.data.ConversationStatusExpirationBroadcastReceiver();
        this.mContext.registerReceiver(this.mStatusExpReceiver, com.android.server.people.data.ConversationStatusExpirationBroadcastReceiver.getFilter(), 4);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.ACTION_SHUTDOWN");
        this.mContext.registerReceiver(new com.android.server.people.data.DataManager.ShutdownBroadcastReceiver(), intentFilter);
    }

    public void onUserUnlocked(final int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.people.data.UserData userData = this.mUserDataArray.get(i);
                if (userData == null) {
                    userData = new com.android.server.people.data.UserData(i, this.mScheduledExecutor);
                    this.mUserDataArray.put(i, userData);
                }
                userData.setUserUnlocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mScheduledExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.people.data.DataManager.this.lambda$onUserUnlocked$0(i);
            }
        });
    }

    public void onUserStopping(final int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.people.data.UserData userData = this.mUserDataArray.get(i);
                if (userData != null) {
                    userData.setUserStopped();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mScheduledExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.people.data.DataManager.this.lambda$onUserStopping$1(i);
            }
        });
    }

    void forPackagesInProfile(int i, java.util.function.Consumer<com.android.server.people.data.PackageData> consumer) {
        java.util.Iterator it = this.mUserManager.getEnabledProfiles(i).iterator();
        while (it.hasNext()) {
            com.android.server.people.data.UserData unlockedUserData = getUnlockedUserData(((android.content.pm.UserInfo) it.next()).id);
            if (unlockedUserData != null) {
                unlockedUserData.forAllPackages(consumer);
            }
        }
    }

    @android.annotation.Nullable
    public com.android.server.people.data.PackageData getPackage(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.people.data.UserData unlockedUserData = getUnlockedUserData(i);
        if (unlockedUserData != null) {
            return unlockedUserData.getPackageData(str);
        }
        return null;
    }

    @android.annotation.Nullable
    public android.content.pm.ShortcutInfo getShortcut(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        java.util.List<android.content.pm.ShortcutInfo> shortcuts = getShortcuts(str, i, java.util.Collections.singletonList(str2));
        if (shortcuts != null && !shortcuts.isEmpty()) {
            return shortcuts.get(0);
        }
        return null;
    }

    public java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> getShareShortcuts(@android.annotation.NonNull android.content.IntentFilter intentFilter, int i) {
        return this.mShortcutServiceInternal.getShareTargets(this.mContext.getPackageName(), intentFilter, i);
    }

    @android.annotation.Nullable
    public android.app.people.ConversationChannel getConversation(java.lang.String str, int i, java.lang.String str2) {
        com.android.server.people.data.PackageData packageData;
        com.android.server.people.data.UserData unlockedUserData = getUnlockedUserData(i);
        if (unlockedUserData != null && (packageData = unlockedUserData.getPackageData(str)) != null) {
            return getConversationChannel(str, i, str2, packageData.getConversationInfo(str2));
        }
        return null;
    }

    com.android.server.people.data.ConversationInfo getConversationInfo(java.lang.String str, int i, java.lang.String str2) {
        com.android.server.people.data.PackageData packageData;
        com.android.server.people.data.UserData unlockedUserData = getUnlockedUserData(i);
        if (unlockedUserData != null && (packageData = unlockedUserData.getPackageData(str)) != null) {
            return packageData.getConversationInfo(str2);
        }
        return null;
    }

    @android.annotation.Nullable
    private android.app.people.ConversationChannel getConversationChannel(java.lang.String str, int i, java.lang.String str2, com.android.server.people.data.ConversationInfo conversationInfo) {
        return getConversationChannel(getShortcut(str, i, str2), conversationInfo, str, i, str2);
    }

    @android.annotation.Nullable
    private android.app.people.ConversationChannel getConversationChannel(android.content.pm.ShortcutInfo shortcutInfo, com.android.server.people.data.ConversationInfo conversationInfo, final java.lang.String str, final int i, final java.lang.String str2) {
        android.app.NotificationChannelGroup notificationChannelGroup;
        if (conversationInfo == null || conversationInfo.isDemoted()) {
            return null;
        }
        if (shortcutInfo == null) {
            android.util.Slog.e(TAG, "Shortcut no longer found");
            this.mInjector.getBackgroundExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.people.data.DataManager.this.lambda$getConversationChannel$2(str, i, str2);
                }
            });
            return null;
        }
        int packageUid = this.mPackageManagerInternal.getPackageUid(str, 0L, i);
        android.app.NotificationChannel notificationChannel = this.mNotificationManagerInternal.getNotificationChannel(str, packageUid, conversationInfo.getNotificationChannelId());
        if (notificationChannel == null) {
            notificationChannelGroup = null;
        } else {
            notificationChannelGroup = this.mNotificationManagerInternal.getNotificationChannelGroup(str, packageUid, notificationChannel.getId());
        }
        return new android.app.people.ConversationChannel(shortcutInfo, packageUid, notificationChannel, notificationChannelGroup, conversationInfo.getLastEventTimestamp(), hasActiveNotifications(str, i, str2), false, getStatuses(conversationInfo));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getConversationChannel$2(java.lang.String str, int i, java.lang.String str2) {
        removeConversations(str, i, java.util.Set.of(str2));
    }

    public java.util.List<android.app.people.ConversationChannel> getRecentConversations(int i) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        forPackagesInProfile(i, new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.DataManager.this.lambda$getRecentConversations$4(arrayList, (com.android.server.people.data.PackageData) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRecentConversations$4(final java.util.List list, final com.android.server.people.data.PackageData packageData) {
        packageData.forAllConversations(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.DataManager.this.lambda$getRecentConversations$3(packageData, list, (com.android.server.people.data.ConversationInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRecentConversations$3(com.android.server.people.data.PackageData packageData, java.util.List list, com.android.server.people.data.ConversationInfo conversationInfo) {
        if (!isCachedRecentConversation(conversationInfo)) {
            return;
        }
        android.app.people.ConversationChannel conversationChannel = getConversationChannel(packageData.getPackageName(), packageData.getUserId(), conversationInfo.getShortcutId(), conversationInfo);
        if (conversationChannel == null || conversationChannel.getNotificationChannel() == null) {
            return;
        }
        list.add(conversationChannel);
    }

    public void removeRecentConversation(java.lang.String str, int i, java.lang.String str2, int i2) {
        if (!hasActiveNotifications(str, i, str2)) {
            this.mShortcutServiceInternal.uncacheShortcuts(i2, this.mContext.getPackageName(), str, java.util.Collections.singletonList(str2), i, 16384);
        }
    }

    public void removeAllRecentConversations(int i) {
        pruneOldRecentConversations(i, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
    }

    public void pruneOldRecentConversations(final int i, final long j) {
        forPackagesInProfile(i, new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.DataManager.this.lambda$pruneOldRecentConversations$6(j, i, (com.android.server.people.data.PackageData) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pruneOldRecentConversations$6(final long j, int i, com.android.server.people.data.PackageData packageData) {
        final java.lang.String packageName = packageData.getPackageName();
        final int userId = packageData.getUserId();
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        packageData.forAllConversations(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.DataManager.this.lambda$pruneOldRecentConversations$5(j, packageName, userId, arrayList, (com.android.server.people.data.ConversationInfo) obj);
            }
        });
        if (!arrayList.isEmpty()) {
            this.mShortcutServiceInternal.uncacheShortcuts(i, this.mContext.getPackageName(), packageName, arrayList, userId, 16384);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pruneOldRecentConversations$5(long j, java.lang.String str, int i, java.util.List list, com.android.server.people.data.ConversationInfo conversationInfo) {
        java.lang.String shortcutId = conversationInfo.getShortcutId();
        if (isCachedRecentConversation(conversationInfo) && j - conversationInfo.getLastEventTimestamp() > RECENT_NOTIFICATIONS_MAX_AGE_MS && !hasActiveNotifications(str, i, shortcutId)) {
            list.add(shortcutId);
        }
    }

    public void pruneExpiredConversationStatuses(int i, final long j) {
        forPackagesInProfile(i, new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.DataManager.this.lambda$pruneExpiredConversationStatuses$8(j, (com.android.server.people.data.PackageData) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pruneExpiredConversationStatuses$8(final long j, final com.android.server.people.data.PackageData packageData) {
        final com.android.server.people.data.ConversationStore conversationStore = packageData.getConversationStore();
        packageData.forAllConversations(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.DataManager.this.lambda$pruneExpiredConversationStatuses$7(j, conversationStore, packageData, (com.android.server.people.data.ConversationInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pruneExpiredConversationStatuses$7(long j, com.android.server.people.data.ConversationStore conversationStore, com.android.server.people.data.PackageData packageData, com.android.server.people.data.ConversationInfo conversationInfo) {
        com.android.server.people.data.ConversationInfo.Builder builder = new com.android.server.people.data.ConversationInfo.Builder(conversationInfo);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.app.people.ConversationStatus conversationStatus : conversationInfo.getStatuses()) {
            if (conversationStatus.getEndTimeMillis() < 0 || j < conversationStatus.getEndTimeMillis()) {
                arrayList.add(conversationStatus);
            }
        }
        builder.setStatuses(arrayList);
        updateConversationStoreThenNotifyListeners(conversationStore, builder.build(), packageData.getPackageName(), packageData.getUserId());
    }

    public boolean isConversation(java.lang.String str, int i, java.lang.String str2) {
        android.app.people.ConversationChannel conversation = getConversation(str, i, str2);
        return (conversation == null || conversation.getShortcutInfo() == null || android.text.TextUtils.isEmpty(conversation.getShortcutInfo().getLabel())) ? false : true;
    }

    public long getLastInteraction(java.lang.String str, int i, java.lang.String str2) {
        com.android.server.people.data.ConversationInfo conversationInfo;
        com.android.server.people.data.PackageData packageData = getPackage(str, i);
        if (packageData != null && (conversationInfo = packageData.getConversationInfo(str2)) != null) {
            return conversationInfo.getLastEventTimestamp();
        }
        return 0L;
    }

    public void addOrUpdateStatus(java.lang.String str, int i, java.lang.String str2, android.app.people.ConversationStatus conversationStatus) {
        com.android.server.people.data.ConversationStore conversationStoreOrThrow = getConversationStoreOrThrow(str, i);
        com.android.server.people.data.ConversationInfo.Builder builder = new com.android.server.people.data.ConversationInfo.Builder(getConversationInfoOrThrow(conversationStoreOrThrow, str2));
        builder.addOrUpdateStatus(conversationStatus);
        updateConversationStoreThenNotifyListeners(conversationStoreOrThrow, builder.build(), str, i);
        if (conversationStatus.getEndTimeMillis() >= 0) {
            this.mStatusExpReceiver.scheduleExpiration(this.mContext, i, str, str2, conversationStatus);
        }
    }

    public void clearStatus(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) {
        com.android.server.people.data.ConversationStore conversationStoreOrThrow = getConversationStoreOrThrow(str, i);
        com.android.server.people.data.ConversationInfo.Builder builder = new com.android.server.people.data.ConversationInfo.Builder(getConversationInfoOrThrow(conversationStoreOrThrow, str2));
        builder.clearStatus(str3);
        updateConversationStoreThenNotifyListeners(conversationStoreOrThrow, builder.build(), str, i);
    }

    public void clearStatuses(java.lang.String str, int i, java.lang.String str2) {
        com.android.server.people.data.ConversationStore conversationStoreOrThrow = getConversationStoreOrThrow(str, i);
        com.android.server.people.data.ConversationInfo.Builder builder = new com.android.server.people.data.ConversationInfo.Builder(getConversationInfoOrThrow(conversationStoreOrThrow, str2));
        builder.setStatuses(null);
        updateConversationStoreThenNotifyListeners(conversationStoreOrThrow, builder.build(), str, i);
    }

    @android.annotation.NonNull
    public java.util.List<android.app.people.ConversationStatus> getStatuses(java.lang.String str, int i, java.lang.String str2) {
        return getStatuses(getConversationInfoOrThrow(getConversationStoreOrThrow(str, i), str2));
    }

    @android.annotation.NonNull
    private java.util.List<android.app.people.ConversationStatus> getStatuses(com.android.server.people.data.ConversationInfo conversationInfo) {
        java.util.Collection<android.app.people.ConversationStatus> statuses = conversationInfo.getStatuses();
        if (statuses != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList(statuses.size());
            arrayList.addAll(statuses);
            return arrayList;
        }
        return new java.util.ArrayList();
    }

    @android.annotation.NonNull
    private com.android.server.people.data.ConversationStore getConversationStoreOrThrow(java.lang.String str, int i) {
        com.android.server.people.data.PackageData packageData = getPackage(str, i);
        if (packageData == null) {
            throw new java.lang.IllegalArgumentException("No settings exist for package " + str);
        }
        com.android.server.people.data.ConversationStore conversationStore = packageData.getConversationStore();
        if (conversationStore == null) {
            throw new java.lang.IllegalArgumentException("No conversations exist for package " + str);
        }
        return conversationStore;
    }

    @android.annotation.NonNull
    private com.android.server.people.data.ConversationInfo getConversationInfoOrThrow(com.android.server.people.data.ConversationStore conversationStore, java.lang.String str) {
        com.android.server.people.data.ConversationInfo conversation = conversationStore.getConversation(str);
        if (conversation == null) {
            throw new java.lang.IllegalArgumentException("Conversation does not exist");
        }
        return conversation;
    }

    public void reportShareTargetEvent(@android.annotation.NonNull android.app.prediction.AppTargetEvent appTargetEvent, @android.annotation.NonNull android.content.IntentFilter intentFilter) {
        com.android.server.people.data.UserData unlockedUserData;
        com.android.server.people.data.EventHistoryImpl orCreateEventHistory;
        android.app.prediction.AppTarget target = appTargetEvent.getTarget();
        if (target == null || appTargetEvent.getAction() != 1 || (unlockedUserData = getUnlockedUserData(target.getUser().getIdentifier())) == null) {
            return;
        }
        com.android.server.people.data.PackageData orCreatePackageData = unlockedUserData.getOrCreatePackageData(target.getPackageName());
        int mimeTypeToShareEventType = mimeTypeToShareEventType(intentFilter.getDataType(0));
        if ("direct_share".equals(appTargetEvent.getLaunchLocation())) {
            if (target.getShortcutInfo() == null) {
                return;
            }
            java.lang.String id = target.getShortcutInfo().getId();
            if ("chooser_target".equals(id)) {
                return;
            }
            if (orCreatePackageData.getConversationStore().getConversation(id) == null) {
                addOrUpdateConversationInfo(target.getShortcutInfo());
            }
            orCreateEventHistory = orCreatePackageData.getEventStore().getOrCreateEventHistory(0, id);
        } else {
            orCreateEventHistory = orCreatePackageData.getEventStore().getOrCreateEventHistory(4, target.getClassName());
        }
        orCreateEventHistory.addEvent(new com.android.server.people.data.Event(java.lang.System.currentTimeMillis(), mimeTypeToShareEventType));
    }

    @android.annotation.NonNull
    public java.util.List<android.app.usage.UsageEvents.Event> queryAppMovingToForegroundEvents(int i, long j, long j2) {
        return com.android.server.people.data.UsageStatsQueryHelper.queryAppMovingToForegroundEvents(i, j, j2);
    }

    @android.annotation.NonNull
    public java.util.Map<java.lang.String, com.android.server.people.data.AppUsageStatsData> queryAppUsageStats(int i, long j, long j2, java.util.Set<java.lang.String> set) {
        return com.android.server.people.data.UsageStatsQueryHelper.queryAppUsageStats(i, j, j2, set);
    }

    public void pruneDataForUser(final int i, @android.annotation.NonNull final android.os.CancellationSignal cancellationSignal) {
        com.android.server.people.data.UserData unlockedUserData = getUnlockedUserData(i);
        if (unlockedUserData == null || cancellationSignal.isCanceled()) {
            return;
        }
        pruneUninstalledPackageData(unlockedUserData);
        unlockedUserData.forAllPackages(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.DataManager.this.lambda$pruneDataForUser$9(cancellationSignal, i, (com.android.server.people.data.PackageData) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pruneDataForUser$9(android.os.CancellationSignal cancellationSignal, int i, com.android.server.people.data.PackageData packageData) {
        if (cancellationSignal.isCanceled()) {
            return;
        }
        packageData.getEventStore().pruneOldEvents();
        if (!packageData.isDefaultDialer()) {
            packageData.getEventStore().deleteEventHistories(2);
        }
        if (!packageData.isDefaultSmsApp()) {
            packageData.getEventStore().deleteEventHistories(3);
        }
        packageData.pruneOrphanEvents();
        pruneExpiredConversationStatuses(i, java.lang.System.currentTimeMillis());
        pruneOldRecentConversations(i, java.lang.System.currentTimeMillis());
        cleanupCachedShortcuts(i, 30);
    }

    @android.annotation.Nullable
    public byte[] getBackupPayload(int i) {
        com.android.server.people.data.UserData unlockedUserData = getUnlockedUserData(i);
        if (unlockedUserData == null) {
            return null;
        }
        return unlockedUserData.getBackupPayload();
    }

    public void restore(int i, @android.annotation.NonNull byte[] bArr) {
        com.android.server.people.data.UserData unlockedUserData = getUnlockedUserData(i);
        if (unlockedUserData == null) {
            return;
        }
        unlockedUserData.restore(bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setupUser, reason: merged with bridge method [inline-methods] */
    public void lambda$onUserUnlocked$0(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.people.data.UserData unlockedUserData = getUnlockedUserData(i);
                if (unlockedUserData == null) {
                    return;
                }
                unlockedUserData.loadUserData();
                updateDefaultDialer(unlockedUserData);
                updateDefaultSmsApp(unlockedUserData);
                byte b = 0;
                byte b2 = 0;
                byte b3 = 0;
                byte b4 = 0;
                byte b5 = 0;
                byte b6 = 0;
                this.mUsageStatsQueryFutures.put(i, this.mScheduledExecutor.scheduleAtFixedRate(new com.android.server.people.data.DataManager.UsageStatsQueryRunnable(i), 1L, 120L, java.util.concurrent.TimeUnit.SECONDS));
                android.content.IntentFilter intentFilter = new android.content.IntentFilter();
                intentFilter.addAction("android.telecom.action.DEFAULT_DIALER_CHANGED");
                intentFilter.addAction("android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL");
                if (this.mBroadcastReceivers.get(i) == null) {
                    com.android.server.people.data.DataManager.PerUserBroadcastReceiver perUserBroadcastReceiver = new com.android.server.people.data.DataManager.PerUserBroadcastReceiver(i);
                    this.mBroadcastReceivers.put(i, perUserBroadcastReceiver);
                    this.mContext.registerReceiverAsUser(perUserBroadcastReceiver, android.os.UserHandle.of(i), intentFilter, null, null);
                }
                com.android.server.people.data.DataManager.ContactsContentObserver contactsContentObserver = new com.android.server.people.data.DataManager.ContactsContentObserver(com.android.internal.os.BackgroundThread.getHandler());
                this.mContactsContentObservers.put(i, contactsContentObserver);
                this.mContext.getContentResolver().registerContentObserver(android.provider.ContactsContract.Contacts.CONTENT_URI, true, contactsContentObserver, i);
                com.android.server.people.data.DataManager.NotificationListener notificationListener = new com.android.server.people.data.DataManager.NotificationListener(i);
                this.mNotificationListeners.put(i, notificationListener);
                try {
                    notificationListener.registerAsSystemService(this.mContext, new android.content.ComponentName(this.mContext, getClass()), i);
                } catch (android.os.RemoteException e) {
                }
                if (this.mPackageMonitors.get(i) == null) {
                    com.android.server.people.data.DataManager.PerUserPackageMonitor perUserPackageMonitor = new com.android.server.people.data.DataManager.PerUserPackageMonitor();
                    perUserPackageMonitor.register(this.mContext, (android.os.Looper) null, android.os.UserHandle.of(i), true);
                    this.mPackageMonitors.put(i, perUserPackageMonitor);
                }
                if (i == 0) {
                    this.mCallLogContentObserver = new com.android.server.people.data.DataManager.CallLogContentObserver(com.android.internal.os.BackgroundThread.getHandler());
                    this.mContext.getContentResolver().registerContentObserver(android.provider.CallLog.CONTENT_URI, true, this.mCallLogContentObserver, 0);
                    this.mMmsSmsContentObserver = new com.android.server.people.data.DataManager.MmsSmsContentObserver(com.android.internal.os.BackgroundThread.getHandler());
                    this.mContext.getContentResolver().registerContentObserver(android.provider.Telephony.MmsSms.CONTENT_URI, false, this.mMmsSmsContentObserver, 0);
                }
                com.android.server.people.data.DataMaintenanceService.scheduleJob(this.mContext, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cleanupUser, reason: merged with bridge method [inline-methods] */
    public void lambda$onUserStopping$1(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.people.data.UserData userData = this.mUserDataArray.get(i);
                if (userData == null || userData.isUnlocked()) {
                    return;
                }
                android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
                if (this.mUsageStatsQueryFutures.indexOfKey(i) >= 0) {
                    this.mUsageStatsQueryFutures.get(i).cancel(true);
                }
                if (this.mBroadcastReceivers.indexOfKey(i) >= 0) {
                    this.mContext.unregisterReceiver(this.mBroadcastReceivers.get(i));
                }
                if (this.mContactsContentObservers.indexOfKey(i) >= 0) {
                    contentResolver.unregisterContentObserver(this.mContactsContentObservers.get(i));
                }
                if (this.mNotificationListeners.indexOfKey(i) >= 0) {
                    try {
                        this.mNotificationListeners.get(i).unregisterAsSystemService();
                    } catch (android.os.RemoteException e) {
                    }
                }
                if (this.mPackageMonitors.indexOfKey(i) >= 0) {
                    this.mPackageMonitors.get(i).unregister();
                }
                if (i == 0) {
                    if (this.mCallLogContentObserver != null) {
                        contentResolver.unregisterContentObserver(this.mCallLogContentObserver);
                        this.mCallLogContentObserver = null;
                    }
                    if (this.mMmsSmsContentObserver != null) {
                        contentResolver.unregisterContentObserver(this.mMmsSmsContentObserver);
                        this.mCallLogContentObserver = null;
                    }
                }
                com.android.server.people.data.DataMaintenanceService.cancelJob(this.mContext, i);
            } finally {
            }
        }
    }

    public int mimeTypeToShareEventType(java.lang.String str) {
        if (str == null) {
            return 7;
        }
        if (str.startsWith("text/")) {
            return 4;
        }
        if (str.startsWith("image/")) {
            return 5;
        }
        if (!str.startsWith("video/")) {
            return 7;
        }
        return 6;
    }

    private void pruneUninstalledPackageData(@android.annotation.NonNull com.android.server.people.data.UserData userData) {
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        this.mPackageManagerInternal.forEachInstalledPackage(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.DataManager.lambda$pruneUninstalledPackageData$10(arraySet, (com.android.server.pm.pkg.AndroidPackage) obj);
            }
        }, userData.getUserId());
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        userData.forAllPackages(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.DataManager.lambda$pruneUninstalledPackageData$11(arraySet, arrayList, (com.android.server.people.data.PackageData) obj);
            }
        });
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            userData.deletePackageData((java.lang.String) it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pruneUninstalledPackageData$10(java.util.Set set, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        set.add(androidPackage.getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pruneUninstalledPackageData$11(java.util.Set set, java.util.List list, com.android.server.people.data.PackageData packageData) {
        if (!set.contains(packageData.getPackageName())) {
            list.add(packageData.getPackageName());
        }
    }

    private java.util.List<android.content.pm.ShortcutInfo> getShortcuts(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable java.util.List<java.lang.String> list) {
        return this.mShortcutServiceInternal.getShortcuts(0, this.mContext.getPackageName(), 0L, str, list, (java.util.List) null, (android.content.ComponentName) null, 3091, i, android.os.Process.myPid(), android.os.Process.myUid());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forAllUnlockedUsers(java.util.function.Consumer<com.android.server.people.data.UserData> consumer) {
        for (int i = 0; i < this.mUserDataArray.size(); i++) {
            com.android.server.people.data.UserData userData = this.mUserDataArray.get(this.mUserDataArray.keyAt(i));
            if (userData.isUnlocked()) {
                consumer.accept(userData);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public com.android.server.people.data.UserData getUnlockedUserData(int i) {
        com.android.server.people.data.UserData userData = this.mUserDataArray.get(i);
        if (userData == null || !userData.isUnlocked()) {
            return null;
        }
        return userData;
    }

    private void updateDefaultDialer(@android.annotation.NonNull com.android.server.people.data.UserData userData) {
        java.lang.String str;
        android.telecom.TelecomManager telecomManager = (android.telecom.TelecomManager) this.mContext.getSystemService(android.telecom.TelecomManager.class);
        if (telecomManager != null) {
            str = telecomManager.getDefaultDialerPackage(new android.os.UserHandle(userData.getUserId()));
        } else {
            str = null;
        }
        userData.setDefaultDialer(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDefaultSmsApp(@android.annotation.NonNull com.android.server.people.data.UserData userData) {
        android.content.ComponentName defaultSmsApplicationAsUser = com.android.internal.telephony.SmsApplication.getDefaultSmsApplicationAsUser(this.mContext, false, android.os.UserHandle.of(userData.getUserId()));
        userData.setDefaultSmsApp(defaultSmsApplicationAsUser != null ? defaultSmsApplicationAsUser.getPackageName() : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public com.android.server.people.data.PackageData getPackageIfConversationExists(android.service.notification.StatusBarNotification statusBarNotification, java.util.function.Consumer<com.android.server.people.data.ConversationInfo> consumer) {
        com.android.server.people.data.PackageData packageData;
        com.android.server.people.data.ConversationInfo conversation;
        java.lang.String shortcutId = statusBarNotification.getNotification().getShortcutId();
        if (shortcutId == null || (packageData = getPackage(statusBarNotification.getPackageName(), statusBarNotification.getUser().getIdentifier())) == null || (conversation = packageData.getConversationStore().getConversation(shortcutId)) == null) {
            return null;
        }
        consumer.accept(conversation);
        return packageData;
    }

    private boolean isCachedRecentConversation(com.android.server.people.data.ConversationInfo conversationInfo) {
        return isEligibleForCleanUp(conversationInfo) && conversationInfo.getLastEventTimestamp() > 0;
    }

    private boolean isEligibleForCleanUp(com.android.server.people.data.ConversationInfo conversationInfo) {
        return conversationInfo.isShortcutCachedForNotification() && java.util.Objects.equals(conversationInfo.getNotificationChannelId(), conversationInfo.getParentNotificationChannelId());
    }

    private boolean hasActiveNotifications(java.lang.String str, int i, java.lang.String str2) {
        com.android.server.people.data.DataManager.NotificationListener notificationListener = this.mNotificationListeners.get(i);
        return notificationListener != null && notificationListener.hasActiveNotifications(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupCachedShortcuts(int i, int i2) {
        com.android.server.people.data.UserData unlockedUserData = getUnlockedUserData(i);
        if (unlockedUserData == null) {
            return;
        }
        final java.util.ArrayList<android.util.Pair> arrayList = new java.util.ArrayList();
        unlockedUserData.forAllPackages(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.DataManager.this.lambda$cleanupCachedShortcuts$13(arrayList, (com.android.server.people.data.PackageData) obj);
            }
        });
        if (arrayList.size() <= i2) {
            return;
        }
        int size = arrayList.size() - i2;
        java.util.PriorityQueue priorityQueue = new java.util.PriorityQueue(size + 1, java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda12
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(java.lang.Object obj) {
                long lambda$cleanupCachedShortcuts$14;
                lambda$cleanupCachedShortcuts$14 = com.android.server.people.data.DataManager.lambda$cleanupCachedShortcuts$14((android.util.Pair) obj);
                return lambda$cleanupCachedShortcuts$14;
            }
        }).reversed());
        for (android.util.Pair pair : arrayList) {
            if (!hasActiveNotifications((java.lang.String) pair.first, i, ((com.android.server.people.data.ConversationInfo) pair.second).getShortcutId())) {
                priorityQueue.offer(pair);
                if (priorityQueue.size() > size) {
                    priorityQueue.poll();
                }
            }
        }
        while (!priorityQueue.isEmpty()) {
            android.util.Pair pair2 = (android.util.Pair) priorityQueue.poll();
            this.mShortcutServiceInternal.uncacheShortcuts(i, this.mContext.getPackageName(), (java.lang.String) pair2.first, java.util.Collections.singletonList(((com.android.server.people.data.ConversationInfo) pair2.second).getShortcutId()), i, 16384);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanupCachedShortcuts$13(final java.util.List list, final com.android.server.people.data.PackageData packageData) {
        packageData.forAllConversations(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.people.data.DataManager.this.lambda$cleanupCachedShortcuts$12(list, packageData, (com.android.server.people.data.ConversationInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanupCachedShortcuts$12(java.util.List list, com.android.server.people.data.PackageData packageData, com.android.server.people.data.ConversationInfo conversationInfo) {
        if (isEligibleForCleanUp(conversationInfo)) {
            list.add(android.util.Pair.create(packageData.getPackageName(), conversationInfo));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ long lambda$cleanupCachedShortcuts$14(android.util.Pair pair) {
        return java.lang.Math.max(((com.android.server.people.data.ConversationInfo) pair.second).getLastEventTimestamp(), ((com.android.server.people.data.ConversationInfo) pair.second).getCreationTimestamp());
    }

    @com.android.internal.annotations.VisibleForTesting
    void addOrUpdateConversationInfo(@android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo) {
        com.android.server.people.data.ConversationInfo.Builder creationTimestamp;
        com.android.server.people.data.UserData unlockedUserData = getUnlockedUserData(shortcutInfo.getUserId());
        if (unlockedUserData == null) {
            return;
        }
        com.android.server.people.data.ConversationStore conversationStore = unlockedUserData.getOrCreatePackageData(shortcutInfo.getPackage()).getConversationStore();
        com.android.server.people.data.ConversationInfo conversation = conversationStore.getConversation(shortcutInfo.getId());
        if (conversation != null) {
            creationTimestamp = new com.android.server.people.data.ConversationInfo.Builder(conversation);
        } else {
            creationTimestamp = new com.android.server.people.data.ConversationInfo.Builder().setCreationTimestamp(java.lang.System.currentTimeMillis());
        }
        creationTimestamp.setShortcutId(shortcutInfo.getId());
        creationTimestamp.setLocusId(shortcutInfo.getLocusId());
        creationTimestamp.setShortcutFlags(shortcutInfo.getFlags());
        creationTimestamp.setContactUri(null);
        creationTimestamp.setContactPhoneNumber(null);
        creationTimestamp.setContactStarred(false);
        if (shortcutInfo.getPersons() != null && shortcutInfo.getPersons().length != 0) {
            android.app.Person person = shortcutInfo.getPersons()[0];
            creationTimestamp.setPersonImportant(person.isImportant());
            creationTimestamp.setPersonBot(person.isBot());
            java.lang.String uri = person.getUri();
            if (uri != null) {
                com.android.server.people.data.ContactsQueryHelper createContactsQueryHelper = this.mInjector.createContactsQueryHelper(this.mContext);
                if (createContactsQueryHelper.query(uri)) {
                    creationTimestamp.setContactUri(createContactsQueryHelper.getContactUri());
                    creationTimestamp.setContactStarred(createContactsQueryHelper.isStarred());
                    creationTimestamp.setContactPhoneNumber(createContactsQueryHelper.getPhoneNumber());
                }
            }
        }
        updateConversationStoreThenNotifyListeners(conversationStore, creationTimestamp.build(), shortcutInfo);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.database.ContentObserver getContactsContentObserverForTesting(int i) {
        return this.mContactsContentObservers.get(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.database.ContentObserver getCallLogContentObserverForTesting() {
        return this.mCallLogContentObserver;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.database.ContentObserver getMmsSmsContentObserverForTesting() {
        return this.mMmsSmsContentObserver;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.people.data.DataManager.NotificationListener getNotificationListenerServiceForTesting(int i) {
        return this.mNotificationListeners.get(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.internal.content.PackageMonitor getPackageMonitorForTesting(int i) {
        return this.mPackageMonitors.get(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.people.data.UserData getUserDataForTesting(int i) {
        return this.mUserDataArray.get(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ContactsContentObserver extends android.database.ContentObserver {
        private long mLastUpdatedTimestamp;

        private ContactsContentObserver(android.os.Handler handler) {
            super(handler);
            this.mLastUpdatedTimestamp = java.lang.System.currentTimeMillis();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            com.android.server.people.data.ContactsQueryHelper createContactsQueryHelper = com.android.server.people.data.DataManager.this.mInjector.createContactsQueryHelper(com.android.server.people.data.DataManager.this.mContext);
            if (!createContactsQueryHelper.querySince(this.mLastUpdatedTimestamp)) {
                return;
            }
            final android.net.Uri contactUri = createContactsQueryHelper.getContactUri();
            final com.android.server.people.data.DataManager.ContactsContentObserver.ConversationSelector conversationSelector = new com.android.server.people.data.DataManager.ContactsContentObserver.ConversationSelector();
            com.android.server.people.data.UserData unlockedUserData = com.android.server.people.data.DataManager.this.getUnlockedUserData(i);
            if (unlockedUserData == null) {
                return;
            }
            unlockedUserData.forAllPackages(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$ContactsContentObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.data.DataManager.ContactsContentObserver.lambda$onChange$0(contactUri, conversationSelector, (com.android.server.people.data.PackageData) obj);
                }
            });
            if (conversationSelector.mConversationInfo == null) {
                return;
            }
            com.android.server.people.data.ConversationInfo.Builder builder = new com.android.server.people.data.ConversationInfo.Builder(conversationSelector.mConversationInfo);
            builder.setContactStarred(createContactsQueryHelper.isStarred());
            builder.setContactPhoneNumber(createContactsQueryHelper.getPhoneNumber());
            com.android.server.people.data.DataManager.this.updateConversationStoreThenNotifyListeners(conversationSelector.mConversationStore, builder.build(), conversationSelector.mPackageName, i);
            this.mLastUpdatedTimestamp = createContactsQueryHelper.getLastUpdatedTimestamp();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onChange$0(android.net.Uri uri, com.android.server.people.data.DataManager.ContactsContentObserver.ConversationSelector conversationSelector, com.android.server.people.data.PackageData packageData) {
            com.android.server.people.data.ConversationInfo conversationByContactUri = packageData.getConversationStore().getConversationByContactUri(uri);
            if (conversationByContactUri != null) {
                conversationSelector.mConversationStore = packageData.getConversationStore();
                conversationSelector.mConversationInfo = conversationByContactUri;
                conversationSelector.mPackageName = packageData.getPackageName();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class ConversationSelector {
            private com.android.server.people.data.ConversationInfo mConversationInfo;
            private com.android.server.people.data.ConversationStore mConversationStore;
            private java.lang.String mPackageName;

            private ConversationSelector() {
                this.mConversationStore = null;
                this.mConversationInfo = null;
                this.mPackageName = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CallLogContentObserver extends android.database.ContentObserver implements java.util.function.BiConsumer<java.lang.String, com.android.server.people.data.Event> {
        private final com.android.server.people.data.CallLogQueryHelper mCallLogQueryHelper;
        private long mLastCallTimestamp;

        private CallLogContentObserver(android.os.Handler handler) {
            super(handler);
            this.mCallLogQueryHelper = com.android.server.people.data.DataManager.this.mInjector.createCallLogQueryHelper(com.android.server.people.data.DataManager.this.mContext, this);
            this.mLastCallTimestamp = java.lang.System.currentTimeMillis() - 300000;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (this.mCallLogQueryHelper.querySince(this.mLastCallTimestamp)) {
                this.mLastCallTimestamp = this.mCallLogQueryHelper.getLastCallTimestamp();
            }
        }

        @Override // java.util.function.BiConsumer
        public void accept(final java.lang.String str, final com.android.server.people.data.Event event) {
            com.android.server.people.data.DataManager.this.forAllUnlockedUsers(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$CallLogContentObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.data.DataManager.CallLogContentObserver.lambda$accept$0(str, event, (com.android.server.people.data.UserData) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$accept$0(java.lang.String str, com.android.server.people.data.Event event, com.android.server.people.data.UserData userData) {
            com.android.server.people.data.PackageData defaultDialer = userData.getDefaultDialer();
            if (defaultDialer == null || defaultDialer.getConversationStore().getConversationByPhoneNumber(str) == null) {
                return;
            }
            defaultDialer.getEventStore().getOrCreateEventHistory(2, str).addEvent(event);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MmsSmsContentObserver extends android.database.ContentObserver implements java.util.function.BiConsumer<java.lang.String, com.android.server.people.data.Event> {
        private long mLastMmsTimestamp;
        private long mLastSmsTimestamp;
        private final com.android.server.people.data.MmsQueryHelper mMmsQueryHelper;
        private final com.android.server.people.data.SmsQueryHelper mSmsQueryHelper;

        private MmsSmsContentObserver(android.os.Handler handler) {
            super(handler);
            this.mMmsQueryHelper = com.android.server.people.data.DataManager.this.mInjector.createMmsQueryHelper(com.android.server.people.data.DataManager.this.mContext, this);
            this.mSmsQueryHelper = com.android.server.people.data.DataManager.this.mInjector.createSmsQueryHelper(com.android.server.people.data.DataManager.this.mContext, this);
            long currentTimeMillis = java.lang.System.currentTimeMillis() - 300000;
            this.mLastMmsTimestamp = currentTimeMillis;
            this.mLastSmsTimestamp = currentTimeMillis;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (this.mMmsQueryHelper.querySince(this.mLastMmsTimestamp)) {
                this.mLastMmsTimestamp = this.mMmsQueryHelper.getLastMessageTimestamp();
            }
            if (this.mSmsQueryHelper.querySince(this.mLastSmsTimestamp)) {
                this.mLastSmsTimestamp = this.mSmsQueryHelper.getLastMessageTimestamp();
            }
        }

        @Override // java.util.function.BiConsumer
        public void accept(final java.lang.String str, final com.android.server.people.data.Event event) {
            com.android.server.people.data.DataManager.this.forAllUnlockedUsers(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$MmsSmsContentObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.data.DataManager.MmsSmsContentObserver.lambda$accept$0(str, event, (com.android.server.people.data.UserData) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$accept$0(java.lang.String str, com.android.server.people.data.Event event, com.android.server.people.data.UserData userData) {
            com.android.server.people.data.PackageData defaultSmsApp = userData.getDefaultSmsApp();
            if (defaultSmsApp == null || defaultSmsApp.getConversationStore().getConversationByPhoneNumber(str) == null) {
                return;
            }
            defaultSmsApp.getEventStore().getOrCreateEventHistory(3, str).addEvent(event);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ShortcutServiceCallback implements android.content.pm.LauncherApps.ShortcutChangeCallback {
        private ShortcutServiceCallback() {
        }

        public void onShortcutsAddedOrUpdated(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.util.List<android.content.pm.ShortcutInfo> list, @android.annotation.NonNull final android.os.UserHandle userHandle) {
            com.android.server.people.data.DataManager.this.mInjector.getBackgroundExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.people.data.DataManager$ShortcutServiceCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.people.data.DataManager.ShortcutServiceCallback.this.lambda$onShortcutsAddedOrUpdated$0(str, userHandle, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onShortcutsAddedOrUpdated$0(java.lang.String str, android.os.UserHandle userHandle, java.util.List list) {
            com.android.server.people.data.PackageData packageData = com.android.server.people.data.DataManager.this.getPackage(str, userHandle.getIdentifier());
            java.util.Iterator it = list.iterator();
            boolean z = false;
            while (it.hasNext()) {
                android.content.pm.ShortcutInfo shortcutInfo = (android.content.pm.ShortcutInfo) it.next();
                if (com.android.server.notification.ShortcutHelper.isConversationShortcut(shortcutInfo, com.android.server.people.data.DataManager.this.mShortcutServiceInternal, userHandle.getIdentifier())) {
                    if (shortcutInfo.isCached()) {
                        com.android.server.people.data.ConversationInfo conversationInfo = packageData != null ? packageData.getConversationInfo(shortcutInfo.getId()) : null;
                        if (conversationInfo == null || !conversationInfo.isShortcutCachedForNotification()) {
                            z = true;
                        }
                    }
                    com.android.server.people.data.DataManager.this.addOrUpdateConversationInfo(shortcutInfo);
                }
            }
            if (z) {
                com.android.server.people.data.DataManager.this.cleanupCachedShortcuts(userHandle.getIdentifier(), 30);
            }
        }

        public void onShortcutsRemoved(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.util.List<android.content.pm.ShortcutInfo> list, @android.annotation.NonNull final android.os.UserHandle userHandle) {
            com.android.server.people.data.DataManager.this.mInjector.getBackgroundExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.people.data.DataManager$ShortcutServiceCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.people.data.DataManager.ShortcutServiceCallback.this.lambda$onShortcutsRemoved$1(list, str, userHandle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onShortcutsRemoved$1(java.util.List list, java.lang.String str, android.os.UserHandle userHandle) {
            java.util.HashSet hashSet = new java.util.HashSet();
            java.util.Iterator it = list.iterator();
            while (it.hasNext()) {
                hashSet.add(((android.content.pm.ShortcutInfo) it.next()).getId());
            }
            com.android.server.people.data.DataManager.this.removeConversations(str, userHandle.getIdentifier(), hashSet);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeConversations(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int i, @android.annotation.NonNull java.util.Set<java.lang.String> set) {
        com.android.server.people.data.PackageData packageData = getPackage(str, i);
        if (packageData != null) {
            java.util.Iterator<java.lang.String> it = set.iterator();
            while (it.hasNext()) {
                packageData.deleteDataForConversation(it.next());
            }
        }
        try {
            this.mNotificationManagerInternal.onConversationRemoved(str, this.mContext.getPackageManager().getPackageUidAsUser(str, i), set);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Package not found when removing conversation: " + str, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class NotificationListener extends android.service.notification.NotificationListenerService {

        @com.android.internal.annotations.GuardedBy({"this"})
        private final java.util.Map<android.util.Pair<java.lang.String, java.lang.String>, java.util.Set<java.lang.String>> mActiveNotifKeys;
        private final int mUserId;

        private NotificationListener(int i) {
            this.mActiveNotifKeys = new android.util.ArrayMap();
            this.mUserId = i;
        }

        @Override // android.service.notification.NotificationListenerService
        public void onNotificationPosted(final android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap) {
            if (statusBarNotification.getUser().getIdentifier() != this.mUserId) {
                return;
            }
            final java.lang.String shortcutId = statusBarNotification.getNotification().getShortcutId();
            com.android.server.people.data.PackageData packageIfConversationExists = com.android.server.people.data.DataManager.this.getPackageIfConversationExists(statusBarNotification, new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$NotificationListener$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.data.DataManager.NotificationListener.this.lambda$onNotificationPosted$1(statusBarNotification, shortcutId, (com.android.server.people.data.ConversationInfo) obj);
                }
            });
            if (packageIfConversationExists != null) {
                android.service.notification.NotificationListenerService.Ranking ranking = new android.service.notification.NotificationListenerService.Ranking();
                rankingMap.getRanking(statusBarNotification.getKey(), ranking);
                com.android.server.people.data.ConversationInfo conversationInfo = packageIfConversationExists.getConversationInfo(shortcutId);
                if (conversationInfo == null) {
                    return;
                }
                com.android.server.people.data.ConversationInfo.Builder notificationChannelId = new com.android.server.people.data.ConversationInfo.Builder(conversationInfo).setLastEventTimestamp(statusBarNotification.getPostTime()).setNotificationChannelId(ranking.getChannel().getId());
                if (!android.text.TextUtils.isEmpty(ranking.getChannel().getParentChannelId())) {
                    notificationChannelId.setParentNotificationChannelId(ranking.getChannel().getParentChannelId());
                } else {
                    notificationChannelId.setParentNotificationChannelId(statusBarNotification.getNotification().getChannelId());
                }
                packageIfConversationExists.getConversationStore().addOrUpdate(notificationChannelId.build());
                packageIfConversationExists.getEventStore().getOrCreateEventHistory(0, shortcutId).addEvent(new com.android.server.people.data.Event(statusBarNotification.getPostTime(), 2));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNotificationPosted$1(android.service.notification.StatusBarNotification statusBarNotification, java.lang.String str, com.android.server.people.data.ConversationInfo conversationInfo) {
            synchronized (this) {
                this.mActiveNotifKeys.computeIfAbsent(android.util.Pair.create(statusBarNotification.getPackageName(), str), new java.util.function.Function() { // from class: com.android.server.people.data.DataManager$NotificationListener$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        java.util.Set lambda$onNotificationPosted$0;
                        lambda$onNotificationPosted$0 = com.android.server.people.data.DataManager.NotificationListener.lambda$onNotificationPosted$0((android.util.Pair) obj);
                        return lambda$onNotificationPosted$0;
                    }
                }).add(statusBarNotification.getKey());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.util.Set lambda$onNotificationPosted$0(android.util.Pair pair) {
            return new java.util.HashSet();
        }

        @Override // android.service.notification.NotificationListenerService
        public synchronized void onNotificationRemoved(final android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap, int i) {
            if (statusBarNotification.getUser().getIdentifier() != this.mUserId) {
                return;
            }
            final java.lang.String shortcutId = statusBarNotification.getNotification().getShortcutId();
            com.android.server.people.data.PackageData packageIfConversationExists = com.android.server.people.data.DataManager.this.getPackageIfConversationExists(statusBarNotification, new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$NotificationListener$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.data.DataManager.NotificationListener.this.lambda$onNotificationRemoved$3(statusBarNotification, shortcutId, (com.android.server.people.data.ConversationInfo) obj);
                }
            });
            if (i != 1 || packageIfConversationExists == null) {
                return;
            }
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            com.android.server.people.data.ConversationInfo conversationInfo = packageIfConversationExists.getConversationInfo(shortcutId);
            if (conversationInfo == null) {
                return;
            }
            packageIfConversationExists.getConversationStore().addOrUpdate(new com.android.server.people.data.ConversationInfo.Builder(conversationInfo).setLastEventTimestamp(currentTimeMillis).build());
            packageIfConversationExists.getEventStore().getOrCreateEventHistory(0, shortcutId).addEvent(new com.android.server.people.data.Event(currentTimeMillis, 3));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNotificationRemoved$3(android.service.notification.StatusBarNotification statusBarNotification, java.lang.String str, com.android.server.people.data.ConversationInfo conversationInfo) {
            android.util.Pair<java.lang.String, java.lang.String> create = android.util.Pair.create(statusBarNotification.getPackageName(), str);
            synchronized (this) {
                try {
                    java.util.Set<java.lang.String> computeIfAbsent = this.mActiveNotifKeys.computeIfAbsent(create, new java.util.function.Function() { // from class: com.android.server.people.data.DataManager$NotificationListener$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            java.util.Set lambda$onNotificationRemoved$2;
                            lambda$onNotificationRemoved$2 = com.android.server.people.data.DataManager.NotificationListener.lambda$onNotificationRemoved$2((android.util.Pair) obj);
                            return lambda$onNotificationRemoved$2;
                        }
                    });
                    computeIfAbsent.remove(statusBarNotification.getKey());
                    if (computeIfAbsent.isEmpty()) {
                        this.mActiveNotifKeys.remove(create);
                        com.android.server.people.data.DataManager.this.cleanupCachedShortcuts(this.mUserId, 30);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.util.Set lambda$onNotificationRemoved$2(android.util.Pair pair) {
            return new java.util.HashSet();
        }

        @Override // android.service.notification.NotificationListenerService
        public void onNotificationChannelModified(java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel, int i) {
            com.android.server.people.data.ConversationStore conversationStore;
            com.android.server.people.data.ConversationInfo conversation;
            if (userHandle.getIdentifier() != this.mUserId) {
                return;
            }
            com.android.server.people.data.PackageData packageData = com.android.server.people.data.DataManager.this.getPackage(str, userHandle.getIdentifier());
            java.lang.String conversationId = notificationChannel.getConversationId();
            if (packageData == null || conversationId == null || (conversation = (conversationStore = packageData.getConversationStore()).getConversation(conversationId)) == null) {
                return;
            }
            com.android.server.people.data.ConversationInfo.Builder builder = new com.android.server.people.data.ConversationInfo.Builder(conversation);
            switch (i) {
                case 1:
                case 2:
                    builder.setNotificationChannelId(notificationChannel.getId());
                    builder.setImportant(notificationChannel.isImportantConversation());
                    builder.setDemoted(notificationChannel.isDemoted());
                    builder.setNotificationSilenced(notificationChannel.getImportance() <= 2);
                    builder.setBubbled(notificationChannel.canBubble());
                    break;
                case 3:
                    builder.setNotificationChannelId(null);
                    builder.setImportant(false);
                    builder.setDemoted(false);
                    builder.setNotificationSilenced(false);
                    builder.setBubbled(false);
                    break;
            }
            com.android.server.people.data.DataManager.this.updateConversationStoreThenNotifyListeners(conversationStore, builder.build(), str, packageData.getUserId());
        }

        synchronized boolean hasActiveNotifications(java.lang.String str, java.lang.String str2) {
            return this.mActiveNotifKeys.containsKey(android.util.Pair.create(str, str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class UsageStatsQueryRunnable implements java.lang.Runnable, com.android.server.people.data.UsageStatsQueryHelper.EventListener {
        private long mLastEventTimestamp;
        private final com.android.server.people.data.UsageStatsQueryHelper mUsageStatsQueryHelper;

        private UsageStatsQueryRunnable(final int i) {
            this.mUsageStatsQueryHelper = com.android.server.people.data.DataManager.this.mInjector.createUsageStatsQueryHelper(i, new java.util.function.Function() { // from class: com.android.server.people.data.DataManager$UsageStatsQueryRunnable$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    com.android.server.people.data.PackageData lambda$new$0;
                    lambda$new$0 = com.android.server.people.data.DataManager.UsageStatsQueryRunnable.this.lambda$new$0(i, (java.lang.String) obj);
                    return lambda$new$0;
                }
            }, this);
            this.mLastEventTimestamp = java.lang.System.currentTimeMillis() - 300000;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.server.people.data.PackageData lambda$new$0(int i, java.lang.String str) {
            return com.android.server.people.data.DataManager.this.getPackage(str, i);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mUsageStatsQueryHelper.querySince(this.mLastEventTimestamp)) {
                this.mLastEventTimestamp = this.mUsageStatsQueryHelper.getLastEventTimestamp();
            }
        }

        @Override // com.android.server.people.data.UsageStatsQueryHelper.EventListener
        public void onEvent(com.android.server.people.data.PackageData packageData, com.android.server.people.data.ConversationInfo conversationInfo, com.android.server.people.data.Event event) {
            if (event.getType() == 13) {
                com.android.server.people.data.DataManager.this.updateConversationStoreThenNotifyListeners(packageData.getConversationStore(), new com.android.server.people.data.ConversationInfo.Builder(conversationInfo).setLastEventTimestamp(event.getTimestamp()).build(), packageData.getPackageName(), packageData.getUserId());
            }
        }
    }

    public void addConversationsListener(@android.annotation.NonNull com.android.server.people.PeopleService.ConversationsListener conversationsListener) {
        synchronized (this.mLock) {
            java.util.List<com.android.server.people.PeopleService.ConversationsListener> list = this.mConversationsListeners;
            java.util.Objects.requireNonNull(conversationsListener);
            list.add(conversationsListener);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateConversationStoreThenNotifyListeners(com.android.server.people.data.ConversationStore conversationStore, com.android.server.people.data.ConversationInfo conversationInfo, java.lang.String str, int i) {
        conversationStore.addOrUpdate(conversationInfo);
        android.app.people.ConversationChannel conversationChannel = getConversationChannel(str, i, conversationInfo.getShortcutId(), conversationInfo);
        if (conversationChannel != null) {
            notifyConversationsListeners(java.util.Arrays.asList(conversationChannel));
        }
    }

    private void updateConversationStoreThenNotifyListeners(com.android.server.people.data.ConversationStore conversationStore, com.android.server.people.data.ConversationInfo conversationInfo, @android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo) {
        conversationStore.addOrUpdate(conversationInfo);
        android.app.people.ConversationChannel conversationChannel = getConversationChannel(shortcutInfo, conversationInfo, shortcutInfo.getPackage(), shortcutInfo.getUserId(), shortcutInfo.getId());
        if (conversationChannel != null) {
            notifyConversationsListeners(java.util.Arrays.asList(conversationChannel));
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void notifyConversationsListeners(@android.annotation.Nullable final java.util.List<android.app.people.ConversationChannel> list) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.people.data.DataManager.this.lambda$notifyConversationsListeners$15(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyConversationsListeners$15(java.util.List list) {
        java.util.ArrayList arrayList;
        try {
            synchronized (this.mLock) {
                arrayList = new java.util.ArrayList(this.mConversationsListeners);
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((com.android.server.people.PeopleService.ConversationsListener) it.next()).onConversationsUpdate(list);
            }
        } catch (java.lang.Exception e) {
        }
    }

    private class PerUserBroadcastReceiver extends android.content.BroadcastReceiver {
        private final int mUserId;

        private PerUserBroadcastReceiver(int i) {
            this.mUserId = i;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.people.data.UserData unlockedUserData = com.android.server.people.data.DataManager.this.getUnlockedUserData(this.mUserId);
            if (unlockedUserData == null) {
                return;
            }
            if ("android.telecom.action.DEFAULT_DIALER_CHANGED".equals(intent.getAction())) {
                unlockedUserData.setDefaultDialer(intent.getStringExtra("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME"));
            } else if ("android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL".equals(intent.getAction())) {
                com.android.server.people.data.DataManager.this.updateDefaultSmsApp(unlockedUserData);
            }
        }
    }

    private class PerUserPackageMonitor extends com.android.internal.content.PackageMonitor {
        private PerUserPackageMonitor() {
        }

        public void onPackageRemoved(java.lang.String str, int i) {
            super.onPackageRemoved(str, i);
            com.android.server.people.data.UserData unlockedUserData = com.android.server.people.data.DataManager.this.getUnlockedUserData(getChangingUserId());
            if (unlockedUserData != null) {
                unlockedUserData.deletePackageData(str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ShutdownBroadcastReceiver extends android.content.BroadcastReceiver {
        private ShutdownBroadcastReceiver() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onReceive$0(com.android.server.people.data.UserData userData) {
            userData.forAllPackages(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$ShutdownBroadcastReceiver$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.people.data.PackageData) obj).saveToDisk();
                }
            });
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.people.data.DataManager.this.forAllUnlockedUsers(new java.util.function.Consumer() { // from class: com.android.server.people.data.DataManager$ShutdownBroadcastReceiver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.people.data.DataManager.ShutdownBroadcastReceiver.lambda$onReceive$0((com.android.server.people.data.UserData) obj);
                }
            });
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        java.util.concurrent.ScheduledExecutorService createScheduledExecutor() {
            return java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
        }

        java.util.concurrent.Executor getBackgroundExecutor() {
            return com.android.internal.os.BackgroundThread.getExecutor();
        }

        com.android.server.people.data.ContactsQueryHelper createContactsQueryHelper(android.content.Context context) {
            return new com.android.server.people.data.ContactsQueryHelper(context);
        }

        com.android.server.people.data.CallLogQueryHelper createCallLogQueryHelper(android.content.Context context, java.util.function.BiConsumer<java.lang.String, com.android.server.people.data.Event> biConsumer) {
            return new com.android.server.people.data.CallLogQueryHelper(context, biConsumer);
        }

        com.android.server.people.data.MmsQueryHelper createMmsQueryHelper(android.content.Context context, java.util.function.BiConsumer<java.lang.String, com.android.server.people.data.Event> biConsumer) {
            return new com.android.server.people.data.MmsQueryHelper(context, biConsumer);
        }

        com.android.server.people.data.SmsQueryHelper createSmsQueryHelper(android.content.Context context, java.util.function.BiConsumer<java.lang.String, com.android.server.people.data.Event> biConsumer) {
            return new com.android.server.people.data.SmsQueryHelper(context, biConsumer);
        }

        com.android.server.people.data.UsageStatsQueryHelper createUsageStatsQueryHelper(int i, java.util.function.Function<java.lang.String, com.android.server.people.data.PackageData> function, com.android.server.people.data.UsageStatsQueryHelper.EventListener eventListener) {
            return new com.android.server.people.data.UsageStatsQueryHelper(i, function, eventListener);
        }
    }
}
