package com.android.server.utils.quota;

/* loaded from: classes2.dex */
abstract class QuotaTracker {
    private static final boolean DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final long MAX_WINDOW_SIZE_MS = 2592000000L;

    @com.android.internal.annotations.VisibleForTesting
    static final long MIN_WINDOW_SIZE_MS = 20000;
    private final android.app.AlarmManager mAlarmManager;
    final com.android.server.utils.quota.Categorizer mCategorizer;
    protected final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.utils.quota.QuotaTracker.InQuotaAlarmQueue mInQuotaAlarmQueue;
    protected final com.android.server.utils.quota.QuotaTracker.Injector mInjector;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsQuotaFree;
    private static final java.lang.String TAG = com.android.server.utils.quota.QuotaTracker.class.getSimpleName();
    private static final java.lang.String ALARM_TAG_QUOTA_CHECK = com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER + TAG + ".quota_check*";
    final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<com.android.server.utils.quota.QuotaChangeListener> mQuotaChangeListeners = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, java.lang.Boolean> mFreeQuota = new android.util.SparseArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsEnabled = true;
    private final android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.utils.quota.QuotaTracker.1
        private java.lang.String getPackageName(android.content.Intent intent) {
            android.net.Uri data = intent.getData();
            if (data != null) {
                return data.getSchemeSpecificPart();
            }
            return null;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            boolean z;
            if (intent == null || intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                return;
            }
            java.lang.String action = intent.getAction();
            if (action == null) {
                android.util.Slog.e(com.android.server.utils.quota.QuotaTracker.TAG, "Received intent with null action");
                return;
            }
            switch (action.hashCode()) {
                case -2061058799:
                    if (action.equals("android.intent.action.USER_REMOVED")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 1580442797:
                    if (action.equals("android.intent.action.PACKAGE_FULLY_REMOVED")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                    synchronized (com.android.server.utils.quota.QuotaTracker.this.mLock) {
                        com.android.server.utils.quota.QuotaTracker.this.onAppRemovedLocked(android.os.UserHandle.getUserId(intExtra), getPackageName(intent));
                    }
                    return;
                case true:
                    int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    synchronized (com.android.server.utils.quota.QuotaTracker.this.mLock) {
                        com.android.server.utils.quota.QuotaTracker.this.onUserRemovedLocked(intExtra2);
                    }
                    return;
                default:
                    return;
            }
        }
    };

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void dropEverythingLocked();

    @android.annotation.NonNull
    abstract android.os.Handler getHandler();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract long getInQuotaTimeElapsedLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void handleRemovedAppLocked(int i, @android.annotation.NonNull java.lang.String str);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void handleRemovedUserLocked(int i);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract boolean isWithinQuotaLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void maybeUpdateAllQuotaStatusLocked();

    abstract void maybeUpdateQuotaStatus(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void onQuotaFreeChangedLocked(int i, @android.annotation.NonNull java.lang.String str, boolean z);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    abstract void onQuotaFreeChangedLocked(boolean z);

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        long getElapsedRealtime() {
            return android.os.SystemClock.elapsedRealtime();
        }

        boolean isAlarmManagerReady() {
            return ((com.android.server.SystemServiceManager) com.android.server.LocalServices.getService(com.android.server.SystemServiceManager.class)).isBootCompleted();
        }
    }

    QuotaTracker(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.utils.quota.Categorizer categorizer, @android.annotation.NonNull com.android.server.utils.quota.QuotaTracker.Injector injector) {
        this.mCategorizer = categorizer;
        this.mContext = context;
        this.mInjector = injector;
        this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
        this.mInQuotaAlarmQueue = new com.android.server.utils.quota.QuotaTracker.InQuotaAlarmQueue(this.mContext, com.android.server.FgThread.getHandler().getLooper());
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        context.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter, null, com.android.internal.os.BackgroundThread.getHandler());
        context.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, new android.content.IntentFilter("android.intent.action.USER_REMOVED"), null, com.android.internal.os.BackgroundThread.getHandler());
    }

    public void clear() {
        synchronized (this.mLock) {
            this.mInQuotaAlarmQueue.removeAllAlarms();
            this.mFreeQuota.clear();
            dropEverythingLocked();
        }
    }

    public boolean isWithinQuota(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        boolean isWithinQuotaLocked;
        synchronized (this.mLock) {
            isWithinQuotaLocked = isWithinQuotaLocked(i, str, str2);
        }
        return isWithinQuotaLocked;
    }

    public void setQuotaFree(int i, @android.annotation.NonNull java.lang.String str, boolean z) {
        synchronized (this.mLock) {
            try {
                if (((java.lang.Boolean) this.mFreeQuota.getOrDefault(i, str, java.lang.Boolean.FALSE)).booleanValue() != z) {
                    this.mFreeQuota.add(i, str, java.lang.Boolean.valueOf(z));
                    onQuotaFreeChangedLocked(i, str, z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setQuotaFree(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mIsQuotaFree == z) {
                    return;
                }
                this.mIsQuotaFree = z;
                if (this.mIsEnabled) {
                    onQuotaFreeChangedLocked(this.mIsQuotaFree);
                    scheduleQuotaCheck();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void registerQuotaChangeListener(com.android.server.utils.quota.QuotaChangeListener quotaChangeListener) {
        synchronized (this.mLock) {
            try {
                if (this.mQuotaChangeListeners.add(quotaChangeListener) && this.mQuotaChangeListeners.size() == 1) {
                    scheduleQuotaCheck();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterQuotaChangeListener(com.android.server.utils.quota.QuotaChangeListener quotaChangeListener) {
        synchronized (this.mLock) {
            this.mQuotaChangeListeners.remove(quotaChangeListener);
        }
    }

    public void setEnabled(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mIsEnabled == z) {
                    return;
                }
                this.mIsEnabled = z;
                if (!this.mIsEnabled) {
                    clear();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isEnabledLocked() {
        return this.mIsEnabled;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isQuotaFreeLocked() {
        return this.mIsQuotaFree;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isQuotaFreeLocked(int i, @android.annotation.NonNull java.lang.String str) {
        return this.mIsQuotaFree || ((java.lang.Boolean) this.mFreeQuota.getOrDefault(i, str, java.lang.Boolean.FALSE)).booleanValue();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isIndividualQuotaFreeLocked(int i, @android.annotation.NonNull java.lang.String str) {
        return ((java.lang.Boolean) this.mFreeQuota.getOrDefault(i, str, java.lang.Boolean.FALSE)).booleanValue();
    }

    void scheduleAlarm(final int i, final long j, final java.lang.String str, final android.app.AlarmManager.OnAlarmListener onAlarmListener) {
        com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.utils.quota.QuotaTracker$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.utils.quota.QuotaTracker.this.lambda$scheduleAlarm$0(i, j, str, onAlarmListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleAlarm$0(int i, long j, java.lang.String str, android.app.AlarmManager.OnAlarmListener onAlarmListener) {
        if (this.mInjector.isAlarmManagerReady()) {
            this.mAlarmManager.set(i, j, str, onAlarmListener, getHandler());
        } else {
            android.util.Slog.w(TAG, "Alarm not scheduled because boot isn't completed");
        }
    }

    void cancelAlarm(final android.app.AlarmManager.OnAlarmListener onAlarmListener) {
        com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.utils.quota.QuotaTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.utils.quota.QuotaTracker.this.lambda$cancelAlarm$1(onAlarmListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelAlarm$1(android.app.AlarmManager.OnAlarmListener onAlarmListener) {
        if (this.mInjector.isAlarmManagerReady()) {
            this.mAlarmManager.cancel(onAlarmListener);
        } else {
            android.util.Slog.w(TAG, "Alarm not cancelled because boot isn't completed");
        }
    }

    void scheduleQuotaCheck() {
        com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.utils.quota.QuotaTracker$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.utils.quota.QuotaTracker.this.lambda$scheduleQuotaCheck$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleQuotaCheck$2() {
        synchronized (this.mLock) {
            try {
                if (this.mQuotaChangeListeners.size() > 0) {
                    maybeUpdateAllQuotaStatusLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onAppRemovedLocked(int i, @android.annotation.NonNull java.lang.String str) {
        if (str == null) {
            android.util.Slog.wtf(TAG, "Told app removed but given null package name.");
            return;
        }
        this.mInQuotaAlarmQueue.removeAlarms(i, str);
        this.mFreeQuota.delete(i, str);
        handleRemovedAppLocked(i, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onUserRemovedLocked(int i) {
        this.mInQuotaAlarmQueue.removeAlarmsForUserId(i);
        this.mFreeQuota.delete(i);
        handleRemovedUserLocked(i);
    }

    void postQuotaStatusChanged(final int i, @android.annotation.NonNull final java.lang.String str, @android.annotation.Nullable final java.lang.String str2) {
        com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.utils.quota.QuotaTracker$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.utils.quota.QuotaTracker.this.lambda$postQuotaStatusChanged$3(i, str, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postQuotaStatusChanged$3(int i, java.lang.String str, java.lang.String str2) {
        com.android.server.utils.quota.QuotaChangeListener[] quotaChangeListenerArr;
        synchronized (this.mLock) {
            quotaChangeListenerArr = (com.android.server.utils.quota.QuotaChangeListener[]) this.mQuotaChangeListeners.toArray(new com.android.server.utils.quota.QuotaChangeListener[this.mQuotaChangeListeners.size()]);
        }
        for (com.android.server.utils.quota.QuotaChangeListener quotaChangeListener : quotaChangeListenerArr) {
            quotaChangeListener.onQuotaStateChanged(i, str, str2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void maybeScheduleStartAlarmLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        if (this.mQuotaChangeListeners.size() == 0) {
            return;
        }
        com.android.server.utils.quota.Uptc.string(i, str, str2);
        if (isWithinQuota(i, str, str2)) {
            this.mInQuotaAlarmQueue.removeAlarmForKey(new com.android.server.utils.quota.Uptc(i, str, str2));
            maybeUpdateQuotaStatus(i, str, str2);
        } else {
            this.mInQuotaAlarmQueue.addAlarm(new com.android.server.utils.quota.Uptc(i, str, str2), getInQuotaTimeElapsedLocked(i, str, str2));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void cancelScheduledStartAlarmLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        this.mInQuotaAlarmQueue.removeAlarmForKey(new com.android.server.utils.quota.Uptc(i, str, str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    class InQuotaAlarmQueue extends com.android.server.utils.AlarmQueue<com.android.server.utils.quota.Uptc> {
        private InQuotaAlarmQueue(android.content.Context context, android.os.Looper looper) {
            super(context, looper, com.android.server.utils.quota.QuotaTracker.ALARM_TAG_QUOTA_CHECK, "In quota", false, 0L);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.utils.AlarmQueue
        public boolean isForUser(@android.annotation.NonNull com.android.server.utils.quota.Uptc uptc, int i) {
            return i == uptc.userId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$removeAlarms$0(int i, java.lang.String str, com.android.server.utils.quota.Uptc uptc) {
            return i == uptc.userId && str.equals(uptc.packageName);
        }

        void removeAlarms(final int i, @android.annotation.NonNull final java.lang.String str) {
            removeAlarmsIf(new java.util.function.Predicate() { // from class: com.android.server.utils.quota.QuotaTracker$InQuotaAlarmQueue$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeAlarms$0;
                    lambda$removeAlarms$0 = com.android.server.utils.quota.QuotaTracker.InQuotaAlarmQueue.lambda$removeAlarms$0(i, str, (com.android.server.utils.quota.Uptc) obj);
                    return lambda$removeAlarms$0;
                }
            });
        }

        @Override // com.android.server.utils.AlarmQueue
        protected void processExpiredAlarms(@android.annotation.NonNull android.util.ArraySet<com.android.server.utils.quota.Uptc> arraySet) {
            for (int i = 0; i < arraySet.size(); i++) {
                final com.android.server.utils.quota.Uptc valueAt = arraySet.valueAt(i);
                com.android.server.utils.quota.QuotaTracker.this.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.utils.quota.QuotaTracker$InQuotaAlarmQueue$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.utils.quota.QuotaTracker.InQuotaAlarmQueue.this.lambda$processExpiredAlarms$1(valueAt);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$processExpiredAlarms$1(com.android.server.utils.quota.Uptc uptc) {
            com.android.server.utils.quota.QuotaTracker.this.maybeUpdateQuotaStatus(uptc.userId, uptc.packageName, uptc.tag);
        }
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("QuotaTracker:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Is enabled: " + this.mIsEnabled);
                indentingPrintWriter.println("Is global quota free: " + this.mIsQuotaFree);
                indentingPrintWriter.println("Current elapsed time: " + this.mInjector.getElapsedRealtime());
                indentingPrintWriter.println();
                indentingPrintWriter.println();
                this.mInQuotaAlarmQueue.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Per-app free quota:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mFreeQuota.numMaps(); i++) {
                    int keyAt = this.mFreeQuota.keyAt(i);
                    for (int i2 = 0; i2 < this.mFreeQuota.numElementsForKey(keyAt); i2++) {
                        java.lang.String str = (java.lang.String) this.mFreeQuota.keyAt(i, i2);
                        indentingPrintWriter.print(com.android.server.utils.quota.Uptc.string(keyAt, str, null));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mFreeQuota.get(keyAt, str));
                    }
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        synchronized (this.mLock) {
            protoOutputStream.write(1133871366145L, this.mIsEnabled);
            protoOutputStream.write(1133871366146L, this.mIsQuotaFree);
            protoOutputStream.write(1112396529667L, this.mInjector.getElapsedRealtime());
        }
        protoOutputStream.end(start);
    }
}
