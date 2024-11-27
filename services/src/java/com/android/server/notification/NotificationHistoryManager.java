package com.android.server.notification;

/* loaded from: classes2.dex */
public class NotificationHistoryManager {
    private static final boolean DEBUG = com.android.server.notification.NotificationManagerService.DBG;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String DIRECTORY_PER_USER = "notification_history";
    private static final java.lang.String TAG = "NotificationHistory";
    private final android.content.Context mContext;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.notification.NotificationHistoryManager.SettingsObserver mSettingsObserver;
    private final android.os.UserManager mUserManager;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.notification.NotificationHistoryDatabase> mUserState = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mUserUnlockedStates = new android.util.SparseBooleanArray();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.List<java.lang.String>> mUserPendingPackageRemovals = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mHistoryEnabled = new android.util.SparseBooleanArray();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mUserPendingHistoryDisables = new android.util.SparseBooleanArray();

    public NotificationHistoryManager(android.content.Context context, android.os.Handler handler) {
        this.mContext = context;
        this.mUserManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        this.mSettingsObserver = new com.android.server.notification.NotificationHistoryManager.SettingsObserver(handler);
    }

    @com.android.internal.annotations.VisibleForTesting
    void onDestroy() {
        this.mSettingsObserver.stopObserving();
    }

    void onBootPhaseAppsCanStart() {
        try {
            com.android.server.notification.NotificationHistoryJobService.scheduleJob(this.mContext);
        } catch (java.lang.Throwable th) {
            android.util.Slog.e(TAG, "Failed to schedule cleanup job", th);
        }
        this.mSettingsObserver.observe();
    }

    void onUserUnlocked(int i) {
        synchronized (this.mLock) {
            try {
                this.mUserUnlockedStates.put(i, true);
                com.android.server.notification.NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(i);
                if (userHistoryAndInitializeIfNeededLocked == null) {
                    android.util.Slog.i(TAG, "Attempted to unlock gone/disabled user " + i);
                    return;
                }
                java.util.List<java.lang.String> list = this.mUserPendingPackageRemovals.get(i);
                if (list != null) {
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        userHistoryAndInitializeIfNeededLocked.onPackageRemoved(list.get(i2));
                    }
                    this.mUserPendingPackageRemovals.remove(i);
                }
                if (this.mUserPendingHistoryDisables.get(i)) {
                    disableHistory(userHistoryAndInitializeIfNeededLocked, i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onUserAdded(int i) {
        this.mSettingsObserver.update(null, i);
    }

    public void onUserStopped(int i) {
        synchronized (this.mLock) {
            this.mUserUnlockedStates.put(i, false);
            this.mUserState.put(i, null);
        }
    }

    public void onUserRemoved(int i) {
        synchronized (this.mLock) {
            this.mUserPendingPackageRemovals.remove(i);
            this.mHistoryEnabled.put(i, false);
            this.mUserPendingHistoryDisables.put(i, false);
            onUserStopped(i);
        }
    }

    public void onPackageRemoved(int i, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.get(i, false)) {
                    if (this.mHistoryEnabled.get(i, false)) {
                        java.util.List<java.lang.String> list = this.mUserPendingPackageRemovals.get(i, new java.util.ArrayList());
                        list.add(str);
                        this.mUserPendingPackageRemovals.put(i, list);
                    }
                    return;
                }
                com.android.server.notification.NotificationHistoryDatabase notificationHistoryDatabase = this.mUserState.get(i);
                if (notificationHistoryDatabase == null) {
                    return;
                }
                notificationHistoryDatabase.onPackageRemoved(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void cleanupHistoryFiles() {
        com.android.server.notification.NotificationHistoryDatabase notificationHistoryDatabase;
        synchronized (this.mLock) {
            try {
                int size = this.mUserUnlockedStates.size();
                for (int i = 0; i < size; i++) {
                    if (this.mUserUnlockedStates.valueAt(i) && (notificationHistoryDatabase = this.mUserState.get(this.mUserUnlockedStates.keyAt(i))) != null) {
                        notificationHistoryDatabase.prune();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void deleteNotificationHistoryItem(java.lang.String str, int i, long j) {
        synchronized (this.mLock) {
            try {
                int userId = android.os.UserHandle.getUserId(i);
                com.android.server.notification.NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(userId);
                if (userHistoryAndInitializeIfNeededLocked == null) {
                    android.util.Slog.w(TAG, "Attempted to remove notif for locked/gone/disabled user " + userId);
                    return;
                }
                userHistoryAndInitializeIfNeededLocked.deleteNotificationHistoryItem(str, j);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void deleteConversations(java.lang.String str, int i, java.util.Set<java.lang.String> set) {
        synchronized (this.mLock) {
            try {
                int userId = android.os.UserHandle.getUserId(i);
                com.android.server.notification.NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(userId);
                if (userHistoryAndInitializeIfNeededLocked == null) {
                    android.util.Slog.w(TAG, "Attempted to remove conversation for locked/gone/disabled user " + userId);
                    return;
                }
                userHistoryAndInitializeIfNeededLocked.deleteConversations(str, set);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void deleteNotificationChannel(java.lang.String str, int i, java.lang.String str2) {
        synchronized (this.mLock) {
            try {
                int userId = android.os.UserHandle.getUserId(i);
                com.android.server.notification.NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(userId);
                if (userHistoryAndInitializeIfNeededLocked == null) {
                    android.util.Slog.w(TAG, "Attempted to remove channel for locked/gone/disabled user " + userId);
                    return;
                }
                userHistoryAndInitializeIfNeededLocked.deleteNotificationChannel(str, str2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void triggerWriteToDisk() {
        com.android.server.notification.NotificationHistoryDatabase notificationHistoryDatabase;
        synchronized (this.mLock) {
            try {
                int size = this.mUserState.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = this.mUserState.keyAt(i);
                    if (this.mUserUnlockedStates.get(keyAt) && (notificationHistoryDatabase = this.mUserState.get(keyAt)) != null) {
                        notificationHistoryDatabase.forceWriteToDisk();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void addNotification(@android.annotation.NonNull final android.app.NotificationHistory.HistoricalNotification historicalNotification) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationHistoryManager$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                com.android.server.notification.NotificationHistoryManager.this.lambda$addNotification$0(historicalNotification);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addNotification$0(android.app.NotificationHistory.HistoricalNotification historicalNotification) throws java.lang.Exception {
        synchronized (this.mLock) {
            try {
                com.android.server.notification.NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(historicalNotification.getUserId());
                if (userHistoryAndInitializeIfNeededLocked == null) {
                    android.util.Slog.w(TAG, "Attempted to add notif for locked/gone/disabled user " + historicalNotification.getUserId());
                    return;
                }
                userHistoryAndInitializeIfNeededLocked.addNotification(historicalNotification);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public android.app.NotificationHistory readNotificationHistory(int[] iArr) {
        synchronized (this.mLock) {
            try {
                android.app.NotificationHistory notificationHistory = new android.app.NotificationHistory();
                if (iArr == null) {
                    return notificationHistory;
                }
                for (int i : iArr) {
                    com.android.server.notification.NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(i);
                    if (userHistoryAndInitializeIfNeededLocked == null) {
                        android.util.Slog.i(TAG, "Attempted to read history for locked/gone/disabled user " + i);
                    } else {
                        notificationHistory.addNotificationsToWrite(userHistoryAndInitializeIfNeededLocked.readNotificationHistory());
                    }
                }
                return notificationHistory;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public android.app.NotificationHistory readFilteredNotificationHistory(int i, java.lang.String str, java.lang.String str2, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.notification.NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(i);
                if (userHistoryAndInitializeIfNeededLocked == null) {
                    android.util.Slog.i(TAG, "Attempted to read history for locked/gone/disabled user " + i);
                    return new android.app.NotificationHistory();
                }
                return userHistoryAndInitializeIfNeededLocked.readNotificationHistory(str, str2, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isHistoryEnabled(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mHistoryEnabled.get(i);
        }
        return z;
    }

    void onHistoryEnabledChanged(int i, boolean z) {
        synchronized (this.mLock) {
            if (z) {
                try {
                    this.mHistoryEnabled.put(i, z);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.notification.NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(i);
            if (userHistoryAndInitializeIfNeededLocked != null) {
                if (!z) {
                    disableHistory(userHistoryAndInitializeIfNeededLocked, i);
                }
            } else {
                this.mUserPendingHistoryDisables.put(i, !z);
            }
        }
    }

    private void disableHistory(com.android.server.notification.NotificationHistoryDatabase notificationHistoryDatabase, int i) {
        notificationHistoryDatabase.disableHistory();
        this.mUserPendingHistoryDisables.put(i, false);
        this.mHistoryEnabled.put(i, false);
        this.mUserState.put(i, null);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.notification.NotificationHistoryDatabase getUserHistoryAndInitializeIfNeededLocked(int i) {
        if (!this.mHistoryEnabled.get(i)) {
            if (DEBUG) {
                android.util.Slog.i(TAG, "History disabled for user " + i);
            }
            this.mUserState.put(i, null);
            return null;
        }
        com.android.server.notification.NotificationHistoryDatabase notificationHistoryDatabase = this.mUserState.get(i);
        if (notificationHistoryDatabase == null) {
            notificationHistoryDatabase = com.android.server.notification.NotificationHistoryDatabaseFactory.create(this.mContext, com.android.server.IoThread.getHandler(), new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), DIRECTORY_PER_USER));
            if (this.mUserUnlockedStates.get(i)) {
                try {
                    notificationHistoryDatabase.init();
                    this.mUserState.put(i, notificationHistoryDatabase);
                } catch (java.lang.Exception e) {
                    if (this.mUserManager.isUserUnlocked(i)) {
                        throw e;
                    }
                    android.util.Slog.w(TAG, "Attempted to initialize service for stopped or removed user " + i);
                    return null;
                }
            } else {
                android.util.Slog.w(TAG, "Attempted to initialize service for stopped or removed user " + i);
                return null;
            }
        }
        return notificationHistoryDatabase;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isUserUnlocked(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mUserUnlockedStates.get(i);
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean doesHistoryExistForUser(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mUserState.get(i) != null;
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void replaceNotificationHistoryDatabase(int i, com.android.server.notification.NotificationHistoryDatabase notificationHistoryDatabase) {
        synchronized (this.mLock) {
            try {
                if (this.mUserState.get(i) != null) {
                    this.mUserState.put(i, notificationHistoryDatabase);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<java.lang.String> getPendingPackageRemovalsForUser(int i) {
        java.util.List<java.lang.String> list;
        synchronized (this.mLock) {
            list = this.mUserPendingPackageRemovals.get(i);
        }
        return list;
    }

    final class SettingsObserver extends android.database.ContentObserver {
        private final android.net.Uri NOTIFICATION_HISTORY_URI;

        SettingsObserver(android.os.Handler handler) {
            super(handler);
            this.NOTIFICATION_HISTORY_URI = android.provider.Settings.Secure.getUriFor("notification_history_enabled");
        }

        void observe() {
            com.android.server.notification.NotificationHistoryManager.this.mContext.getContentResolver().registerContentObserver(this.NOTIFICATION_HISTORY_URI, false, this, -1);
            synchronized (com.android.server.notification.NotificationHistoryManager.this.mLock) {
                try {
                    java.util.Iterator it = com.android.server.notification.NotificationHistoryManager.this.mUserManager.getUsers().iterator();
                    while (it.hasNext()) {
                        update(null, ((android.content.pm.UserInfo) it.next()).id);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void stopObserving() {
            com.android.server.notification.NotificationHistoryManager.this.mContext.getContentResolver().unregisterContentObserver(this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            update(uri, i);
        }

        public void update(android.net.Uri uri, int i) {
            android.content.ContentResolver contentResolver = com.android.server.notification.NotificationHistoryManager.this.mContext.getContentResolver();
            if (uri == null || this.NOTIFICATION_HISTORY_URI.equals(uri)) {
                com.android.server.notification.NotificationHistoryManager.this.onHistoryEnabledChanged(i, android.provider.Settings.Secure.getIntForUser(contentResolver, "notification_history_enabled", 0, i) != 0);
            }
        }
    }
}
