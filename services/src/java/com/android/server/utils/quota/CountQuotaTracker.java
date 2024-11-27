package com.android.server.utils.quota;

/* loaded from: classes2.dex */
public class CountQuotaTracker extends com.android.server.utils.quota.QuotaTracker {
    private static final boolean DEBUG = false;
    private static final int MSG_CLEAN_UP_EVENTS = 1;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<com.android.server.utils.quota.Category, java.lang.Long> mCategoryCountWindowSizesMs;
    private java.util.function.Function<java.lang.Void, com.android.server.utils.quota.CountQuotaTracker.ExecutionStats> mCreateExecutionStats;
    private java.util.function.Function<java.lang.Void, android.util.LongArrayQueue> mCreateLongArrayQueue;
    private final com.android.server.utils.quota.CountQuotaTracker.DeleteEventTimesFunctor mDeleteOldEventTimesFunctor;
    private final com.android.server.utils.quota.CountQuotaTracker.EarliestEventTimeFunctor mEarliestEventTimeFunctor;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.app.AlarmManager.OnAlarmListener mEventCleanupAlarmListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.utils.quota.UptcMap<android.util.LongArrayQueue> mEventTimes;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.utils.quota.UptcMap<com.android.server.utils.quota.CountQuotaTracker.ExecutionStats> mExecutionStatsCache;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<com.android.server.utils.quota.Category, java.lang.Integer> mMaxCategoryCounts;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mMaxPeriodMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mNextCleanupTimeElapsed;
    private static final java.lang.String TAG = com.android.server.utils.quota.CountQuotaTracker.class.getSimpleName();
    private static final java.lang.String ALARM_TAG_CLEANUP = com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER + TAG + ".cleanup*";

    @Override // com.android.server.utils.quota.QuotaTracker
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    public /* bridge */ /* synthetic */ boolean isWithinQuota(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        return super.isWithinQuota(i, str, str2);
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    public /* bridge */ /* synthetic */ void registerQuotaChangeListener(com.android.server.utils.quota.QuotaChangeListener quotaChangeListener) {
        super.registerQuotaChangeListener(quotaChangeListener);
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    public /* bridge */ /* synthetic */ void setEnabled(boolean z) {
        super.setEnabled(z);
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    public /* bridge */ /* synthetic */ void setQuotaFree(int i, @android.annotation.NonNull java.lang.String str, boolean z) {
        super.setQuotaFree(i, str, z);
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    public /* bridge */ /* synthetic */ void setQuotaFree(boolean z) {
        super.setQuotaFree(z);
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    public /* bridge */ /* synthetic */ void unregisterQuotaChangeListener(com.android.server.utils.quota.QuotaChangeListener quotaChangeListener) {
        super.unregisterQuotaChangeListener(quotaChangeListener);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class ExecutionStats {
        public int countInWindow;
        public int countLimit;
        public long expirationTimeElapsed;
        public long inQuotaTimeElapsed;
        public long windowSizeMs;

        ExecutionStats() {
        }

        public java.lang.String toString() {
            return "expirationTime=" + this.expirationTimeElapsed + ", windowSizeMs=" + this.windowSizeMs + ", countLimit=" + this.countLimit + ", countInWindow=" + this.countInWindow + ", inQuotaTime=" + this.inQuotaTimeElapsed;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.utils.quota.CountQuotaTracker.ExecutionStats)) {
                return false;
            }
            com.android.server.utils.quota.CountQuotaTracker.ExecutionStats executionStats = (com.android.server.utils.quota.CountQuotaTracker.ExecutionStats) obj;
            return this.expirationTimeElapsed == executionStats.expirationTimeElapsed && this.windowSizeMs == executionStats.windowSizeMs && this.countLimit == executionStats.countLimit && this.countInWindow == executionStats.countInWindow && this.inQuotaTimeElapsed == executionStats.inQuotaTimeElapsed;
        }

        public int hashCode() {
            return ((((((((0 + java.lang.Long.hashCode(this.expirationTimeElapsed)) * 31) + java.lang.Long.hashCode(this.windowSizeMs)) * 31) + this.countLimit) * 31) + this.countInWindow) * 31) + java.lang.Long.hashCode(this.inQuotaTimeElapsed);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    public CountQuotaTracker(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.utils.quota.Categorizer categorizer) {
        this(context, categorizer, new com.android.server.utils.quota.QuotaTracker.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    CountQuotaTracker(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.utils.quota.Categorizer categorizer, com.android.server.utils.quota.QuotaTracker.Injector injector) {
        super(context, categorizer, injector);
        this.mEventTimes = new com.android.server.utils.quota.UptcMap<>();
        this.mExecutionStatsCache = new com.android.server.utils.quota.UptcMap<>();
        this.mNextCleanupTimeElapsed = 0L;
        this.mEventCleanupAlarmListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda6
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                com.android.server.utils.quota.CountQuotaTracker.this.lambda$new$0();
            }
        };
        this.mCategoryCountWindowSizesMs = new android.util.ArrayMap<>();
        this.mMaxCategoryCounts = new android.util.ArrayMap<>();
        this.mMaxPeriodMs = 0L;
        this.mEarliestEventTimeFunctor = new com.android.server.utils.quota.CountQuotaTracker.EarliestEventTimeFunctor();
        this.mDeleteOldEventTimesFunctor = new com.android.server.utils.quota.CountQuotaTracker.DeleteEventTimesFunctor();
        this.mCreateLongArrayQueue = new java.util.function.Function() { // from class: com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.util.LongArrayQueue lambda$new$4;
                lambda$new$4 = com.android.server.utils.quota.CountQuotaTracker.lambda$new$4((java.lang.Void) obj);
                return lambda$new$4;
            }
        };
        this.mCreateExecutionStats = new java.util.function.Function() { // from class: com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.utils.quota.CountQuotaTracker.ExecutionStats lambda$new$5;
                lambda$new$5 = com.android.server.utils.quota.CountQuotaTracker.lambda$new$5((java.lang.Void) obj);
                return lambda$new$5;
            }
        };
        this.mHandler = new com.android.server.utils.quota.CountQuotaTracker.CqtHandler(context.getMainLooper());
    }

    public boolean noteEvent(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        synchronized (this.mLock) {
            try {
                if (!isEnabledLocked() || isQuotaFreeLocked(i, str)) {
                    return true;
                }
                long elapsedRealtime = this.mInjector.getElapsedRealtime();
                android.util.LongArrayQueue orCreate = this.mEventTimes.getOrCreate(i, str, str2, this.mCreateLongArrayQueue);
                orCreate.addLast(elapsedRealtime);
                com.android.server.utils.quota.CountQuotaTracker.ExecutionStats executionStatsLocked = getExecutionStatsLocked(i, str, str2);
                executionStatsLocked.countInWindow++;
                executionStatsLocked.expirationTimeElapsed = java.lang.Math.min(executionStatsLocked.expirationTimeElapsed, executionStatsLocked.windowSizeMs + elapsedRealtime);
                if (executionStatsLocked.countInWindow == executionStatsLocked.countLimit) {
                    long j = elapsedRealtime - executionStatsLocked.windowSizeMs;
                    while (orCreate.size() > 0 && orCreate.peekFirst() < j) {
                        orCreate.removeFirst();
                    }
                    executionStatsLocked.inQuotaTimeElapsed = orCreate.peekFirst() + executionStatsLocked.windowSizeMs;
                    postQuotaStatusChanged(i, str, str2);
                } else if (executionStatsLocked.countLimit > 9 && executionStatsLocked.countInWindow == (executionStatsLocked.countLimit * 4) / 5) {
                    android.util.Slog.w(TAG, com.android.server.utils.quota.Uptc.string(i, str, str2) + " has reached 80% of it's count limit of " + executionStatsLocked.countLimit);
                    maybeScheduleCleanupAlarmLocked();
                    return isWithinQuotaLocked(executionStatsLocked);
                }
                maybeScheduleCleanupAlarmLocked();
                return isWithinQuotaLocked(executionStatsLocked);
            } finally {
            }
        }
    }

    public void setCountLimit(@android.annotation.NonNull com.android.server.utils.quota.Category category, int i, long j) {
        if (i < 0 || j < 0) {
            throw new java.lang.IllegalArgumentException("Limit and window size must be nonnegative.");
        }
        synchronized (this.mLock) {
            try {
                java.lang.Integer put = this.mMaxCategoryCounts.put(category, java.lang.Integer.valueOf(i));
                long max = java.lang.Math.max(20000L, java.lang.Math.min(j, com.android.server.usage.UnixCalendar.MONTH_IN_MILLIS));
                java.lang.Long put2 = this.mCategoryCountWindowSizesMs.put(category, java.lang.Long.valueOf(max));
                if (put == null || put2 == null || put.intValue() != i || put2.longValue() != max) {
                    this.mDeleteOldEventTimesFunctor.updateMaxPeriod();
                    this.mMaxPeriodMs = this.mDeleteOldEventTimesFunctor.mMaxPeriodMs;
                    invalidateAllExecutionStatsLocked();
                    scheduleQuotaCheck();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getLimit(@android.annotation.NonNull com.android.server.utils.quota.Category category) {
        int intValue;
        synchronized (this.mLock) {
            try {
                java.lang.Integer num = this.mMaxCategoryCounts.get(category);
                if (num == null) {
                    throw new java.lang.IllegalArgumentException("Limit for " + category + " not defined");
                }
                intValue = num.intValue();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return intValue;
    }

    public long getWindowSizeMs(@android.annotation.NonNull com.android.server.utils.quota.Category category) {
        long longValue;
        synchronized (this.mLock) {
            try {
                java.lang.Long l = this.mCategoryCountWindowSizesMs.get(category);
                if (l == null) {
                    throw new java.lang.IllegalArgumentException("Limit for " + category + " not defined");
                }
                longValue = l.longValue();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return longValue;
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void dropEverythingLocked() {
        this.mExecutionStatsCache.clear();
        this.mEventTimes.clear();
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    android.os.Handler getHandler() {
        return this.mHandler;
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    @com.android.internal.annotations.GuardedBy({"mLock"})
    long getInQuotaTimeElapsedLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        return getExecutionStatsLocked(i, str, str2).inQuotaTimeElapsed;
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void handleRemovedAppLocked(int i, @android.annotation.NonNull java.lang.String str) {
        if (str == null) {
            android.util.Slog.wtf(TAG, "Told app removed but given null package name.");
        } else {
            this.mEventTimes.delete(i, str);
            this.mExecutionStatsCache.delete(i, str);
        }
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void handleRemovedUserLocked(int i) {
        this.mEventTimes.delete(i);
        this.mExecutionStatsCache.delete(i);
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isWithinQuotaLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        if (isEnabledLocked() && !isQuotaFreeLocked(i, str)) {
            return isWithinQuotaLocked(getExecutionStatsLocked(i, str, str2));
        }
        return true;
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void maybeUpdateAllQuotaStatusLocked() {
        final com.android.server.utils.quota.UptcMap uptcMap = new com.android.server.utils.quota.UptcMap();
        this.mEventTimes.forEach(new com.android.server.utils.quota.UptcMap.UptcDataConsumer() { // from class: com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda0
            @Override // com.android.server.utils.quota.UptcMap.UptcDataConsumer
            public final void accept(int i, java.lang.String str, java.lang.String str2, java.lang.Object obj) {
                com.android.server.utils.quota.CountQuotaTracker.this.lambda$maybeUpdateAllQuotaStatusLocked$1(uptcMap, i, str, str2, (android.util.LongArrayQueue) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$maybeUpdateAllQuotaStatusLocked$1(com.android.server.utils.quota.UptcMap uptcMap, int i, java.lang.String str, java.lang.String str2, android.util.LongArrayQueue longArrayQueue) {
        if (!uptcMap.contains(i, str, str2)) {
            maybeUpdateStatusForUptcLocked(i, str, str2);
            uptcMap.add(i, str, str2, java.lang.Boolean.TRUE);
        }
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    void maybeUpdateQuotaStatus(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        synchronized (this.mLock) {
            maybeUpdateStatusForUptcLocked(i, str, str2);
        }
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onQuotaFreeChangedLocked(boolean z) {
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onQuotaFreeChangedLocked(int i, @android.annotation.NonNull java.lang.String str, boolean z) {
        maybeUpdateStatusForPkgLocked(i, str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isWithinQuotaLocked(@android.annotation.NonNull com.android.server.utils.quota.CountQuotaTracker.ExecutionStats executionStats) {
        return isUnderCountQuotaLocked(executionStats);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isUnderCountQuotaLocked(@android.annotation.NonNull com.android.server.utils.quota.CountQuotaTracker.ExecutionStats executionStats) {
        return executionStats.countInWindow < executionStats.countLimit;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.utils.quota.CountQuotaTracker.ExecutionStats getExecutionStatsLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        return getExecutionStatsLocked(i, str, str2, true);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.utils.quota.CountQuotaTracker.ExecutionStats getExecutionStatsLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, boolean z) {
        com.android.server.utils.quota.CountQuotaTracker.ExecutionStats orCreate = this.mExecutionStatsCache.getOrCreate(i, str, str2, this.mCreateExecutionStats);
        if (z) {
            com.android.server.utils.quota.Category category = this.mCategorizer.getCategory(i, str, str2);
            long longValue = this.mCategoryCountWindowSizesMs.getOrDefault(category, java.lang.Long.valueOf(com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME)).longValue();
            int intValue = this.mMaxCategoryCounts.getOrDefault(category, Integer.MAX_VALUE).intValue();
            if (orCreate.expirationTimeElapsed <= this.mInjector.getElapsedRealtime() || orCreate.windowSizeMs != longValue || orCreate.countLimit != intValue) {
                orCreate.windowSizeMs = longValue;
                orCreate.countLimit = intValue;
                updateExecutionStatsLocked(i, str, str2, orCreate);
            }
        }
        return orCreate;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void updateExecutionStatsLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull com.android.server.utils.quota.CountQuotaTracker.ExecutionStats executionStats) {
        executionStats.countInWindow = 0;
        if (executionStats.countLimit == 0) {
            executionStats.inQuotaTimeElapsed = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        } else {
            executionStats.inQuotaTimeElapsed = 0L;
        }
        long elapsedRealtime = this.mInjector.getElapsedRealtime();
        executionStats.expirationTimeElapsed = this.mMaxPeriodMs + elapsedRealtime;
        android.util.LongArrayQueue longArrayQueue = this.mEventTimes.get(i, str, str2);
        if (longArrayQueue == null) {
            return;
        }
        long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME - elapsedRealtime;
        long j2 = elapsedRealtime - executionStats.windowSizeMs;
        for (int size = longArrayQueue.size() - 1; size >= 0; size--) {
            long j3 = longArrayQueue.get(size);
            if (j3 < j2) {
                break;
            }
            executionStats.countInWindow++;
            j = java.lang.Math.min(j, j3 - j2);
            if (executionStats.countInWindow >= executionStats.countLimit) {
                executionStats.inQuotaTimeElapsed = java.lang.Math.max(executionStats.inQuotaTimeElapsed, j3 + executionStats.windowSizeMs);
            }
        }
        executionStats.expirationTimeElapsed = elapsedRealtime + j;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void invalidateAllExecutionStatsLocked() {
        final long elapsedRealtime = this.mInjector.getElapsedRealtime();
        this.mExecutionStatsCache.forEach(new java.util.function.Consumer() { // from class: com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.utils.quota.CountQuotaTracker.lambda$invalidateAllExecutionStatsLocked$2(elapsedRealtime, (com.android.server.utils.quota.CountQuotaTracker.ExecutionStats) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$invalidateAllExecutionStatsLocked$2(long j, com.android.server.utils.quota.CountQuotaTracker.ExecutionStats executionStats) {
        if (executionStats != null) {
            executionStats.expirationTimeElapsed = j;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void invalidateAllExecutionStatsLocked(int i, @android.annotation.NonNull java.lang.String str) {
        android.util.ArrayMap<java.lang.String, com.android.server.utils.quota.CountQuotaTracker.ExecutionStats> arrayMap = this.mExecutionStatsCache.get(i, str);
        if (arrayMap != null) {
            long elapsedRealtime = this.mInjector.getElapsedRealtime();
            int size = arrayMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.utils.quota.CountQuotaTracker.ExecutionStats valueAt = arrayMap.valueAt(i2);
                if (valueAt != null) {
                    valueAt.expirationTimeElapsed = elapsedRealtime;
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void invalidateExecutionStatsLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        com.android.server.utils.quota.CountQuotaTracker.ExecutionStats executionStats = this.mExecutionStatsCache.get(i, str, str2);
        if (executionStats != null) {
            executionStats.expirationTimeElapsed = this.mInjector.getElapsedRealtime();
        }
    }

    private static final class EarliestEventTimeFunctor implements java.util.function.Consumer<android.util.LongArrayQueue> {
        long earliestTimeElapsed;

        private EarliestEventTimeFunctor() {
            this.earliestTimeElapsed = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }

        @Override // java.util.function.Consumer
        public void accept(android.util.LongArrayQueue longArrayQueue) {
            if (longArrayQueue != null && longArrayQueue.size() > 0) {
                this.earliestTimeElapsed = java.lang.Math.min(this.earliestTimeElapsed, longArrayQueue.get(0));
            }
        }

        void reset() {
            this.earliestTimeElapsed = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void maybeScheduleCleanupAlarmLocked() {
        long j;
        if (this.mNextCleanupTimeElapsed > this.mInjector.getElapsedRealtime()) {
            return;
        }
        this.mEarliestEventTimeFunctor.reset();
        this.mEventTimes.forEach(this.mEarliestEventTimeFunctor);
        long j2 = this.mEarliestEventTimeFunctor.earliestTimeElapsed;
        if (j2 == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            return;
        }
        long j3 = j2 + this.mMaxPeriodMs;
        if (j3 - this.mNextCleanupTimeElapsed > 600000) {
            j = j3;
        } else {
            j = j3 + 600000;
        }
        this.mNextCleanupTimeElapsed = j;
        scheduleAlarm(3, j, ALARM_TAG_CLEANUP, this.mEventCleanupAlarmListener);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean maybeUpdateStatusForPkgLocked(final int i, @android.annotation.NonNull final java.lang.String str) {
        final com.android.server.utils.quota.UptcMap uptcMap = new com.android.server.utils.quota.UptcMap();
        if (!this.mEventTimes.contains(i, str)) {
            return false;
        }
        android.util.ArrayMap<java.lang.String, android.util.LongArrayQueue> arrayMap = this.mEventTimes.get(i, str);
        if (arrayMap == null) {
            android.util.Slog.wtf(TAG, "Events map was null even though mEventTimes said it contained " + com.android.server.utils.quota.Uptc.string(i, str, null));
            return false;
        }
        final boolean[] zArr = {false};
        arrayMap.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.utils.quota.CountQuotaTracker.this.lambda$maybeUpdateStatusForPkgLocked$3(uptcMap, i, str, zArr, (java.lang.String) obj, (android.util.LongArrayQueue) obj2);
            }
        });
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$maybeUpdateStatusForPkgLocked$3(com.android.server.utils.quota.UptcMap uptcMap, int i, java.lang.String str, boolean[] zArr, java.lang.String str2, android.util.LongArrayQueue longArrayQueue) {
        if (!uptcMap.contains(i, str, str2)) {
            zArr[0] = zArr[0] | maybeUpdateStatusForUptcLocked(i, str, str2);
            uptcMap.add(i, str, str2, java.lang.Boolean.TRUE);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean maybeUpdateStatusForUptcLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        boolean z;
        boolean isWithinQuotaLocked = isWithinQuotaLocked(getExecutionStatsLocked(i, str, str2, false));
        if (!isEnabledLocked() || isQuotaFreeLocked(i, str)) {
            z = true;
        } else {
            z = isWithinQuotaLocked(getExecutionStatsLocked(i, str, str2, true));
        }
        if (!z) {
            maybeScheduleStartAlarmLocked(i, str, str2);
        } else {
            cancelScheduledStartAlarmLocked(i, str, str2);
        }
        if (isWithinQuotaLocked == z) {
            return false;
        }
        postQuotaStatusChanged(i, str, str2);
        return true;
    }

    private final class DeleteEventTimesFunctor implements java.util.function.Consumer<android.util.LongArrayQueue> {
        private long mMaxPeriodMs;

        private DeleteEventTimesFunctor() {
        }

        @Override // java.util.function.Consumer
        public void accept(android.util.LongArrayQueue longArrayQueue) {
            if (longArrayQueue != null) {
                while (longArrayQueue.size() > 0 && longArrayQueue.peekFirst() <= com.android.server.utils.quota.CountQuotaTracker.this.mInjector.getElapsedRealtime() - this.mMaxPeriodMs) {
                    longArrayQueue.removeFirst();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateMaxPeriod() {
            long j = 0;
            for (int size = com.android.server.utils.quota.CountQuotaTracker.this.mCategoryCountWindowSizesMs.size() - 1; size >= 0; size--) {
                j = java.lang.Long.max(j, ((java.lang.Long) com.android.server.utils.quota.CountQuotaTracker.this.mCategoryCountWindowSizesMs.valueAt(size)).longValue());
            }
            this.mMaxPeriodMs = j;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void deleteObsoleteEventsLocked() {
        this.mEventTimes.forEach(this.mDeleteOldEventTimesFunctor);
    }

    private class CqtHandler extends android.os.Handler {
        CqtHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            synchronized (com.android.server.utils.quota.CountQuotaTracker.this.mLock) {
                switch (message.what) {
                    case 1:
                        com.android.server.utils.quota.CountQuotaTracker.this.deleteObsoleteEventsLocked();
                        com.android.server.utils.quota.CountQuotaTracker.this.maybeScheduleCleanupAlarmLocked();
                        break;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.util.LongArrayQueue lambda$new$4(java.lang.Void r0) {
        return new android.util.LongArrayQueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.utils.quota.CountQuotaTracker.ExecutionStats lambda$new$5(java.lang.Void r0) {
        return new com.android.server.utils.quota.CountQuotaTracker.ExecutionStats();
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.util.LongArrayQueue getEvents(int i, java.lang.String str, java.lang.String str2) {
        return this.mEventTimes.get(i, str, str2);
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    public void dump(final android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print(TAG);
        indentingPrintWriter.println(":");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                super.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Instantaneous events:");
                indentingPrintWriter.increaseIndent();
                this.mEventTimes.forEach(new com.android.server.utils.quota.UptcMap.UptcDataConsumer() { // from class: com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda4
                    @Override // com.android.server.utils.quota.UptcMap.UptcDataConsumer
                    public final void accept(int i, java.lang.String str, java.lang.String str2, java.lang.Object obj) {
                        com.android.server.utils.quota.CountQuotaTracker.lambda$dump$6(indentingPrintWriter, i, str, str2, (android.util.LongArrayQueue) obj);
                    }
                });
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Cached execution stats:");
                indentingPrintWriter.increaseIndent();
                this.mExecutionStatsCache.forEach(new com.android.server.utils.quota.UptcMap.UptcDataConsumer() { // from class: com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda5
                    @Override // com.android.server.utils.quota.UptcMap.UptcDataConsumer
                    public final void accept(int i, java.lang.String str, java.lang.String str2, java.lang.Object obj) {
                        com.android.server.utils.quota.CountQuotaTracker.lambda$dump$7(indentingPrintWriter, i, str, str2, (com.android.server.utils.quota.CountQuotaTracker.ExecutionStats) obj);
                    }
                });
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Limits:");
                indentingPrintWriter.increaseIndent();
                int size = this.mCategoryCountWindowSizesMs.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.utils.quota.Category keyAt = this.mCategoryCountWindowSizesMs.keyAt(i);
                    indentingPrintWriter.print(keyAt);
                    indentingPrintWriter.print(": ");
                    indentingPrintWriter.print(this.mMaxCategoryCounts.get(keyAt));
                    indentingPrintWriter.print(" events in ");
                    indentingPrintWriter.println(android.util.TimeUtils.formatDuration(this.mCategoryCountWindowSizesMs.get(keyAt).longValue()));
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$6(android.util.IndentingPrintWriter indentingPrintWriter, int i, java.lang.String str, java.lang.String str2, android.util.LongArrayQueue longArrayQueue) {
        if (longArrayQueue.size() > 0) {
            indentingPrintWriter.print(com.android.server.utils.quota.Uptc.string(i, str, str2));
            indentingPrintWriter.println(":");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print(longArrayQueue.get(0));
            for (int i2 = 1; i2 < longArrayQueue.size(); i2++) {
                indentingPrintWriter.print(", ");
                indentingPrintWriter.print(longArrayQueue.get(i2));
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$7(android.util.IndentingPrintWriter indentingPrintWriter, int i, java.lang.String str, java.lang.String str2, com.android.server.utils.quota.CountQuotaTracker.ExecutionStats executionStats) {
        if (executionStats != null) {
            indentingPrintWriter.print(com.android.server.utils.quota.Uptc.string(i, str, str2));
            indentingPrintWriter.println(":");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(executionStats);
            indentingPrintWriter.decreaseIndent();
        }
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    public void dump(final android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        synchronized (this.mLock) {
            try {
                super.dump(protoOutputStream, 1146756268033L);
                for (int i = 0; i < this.mCategoryCountWindowSizesMs.size(); i++) {
                    com.android.server.utils.quota.Category keyAt = this.mCategoryCountWindowSizesMs.keyAt(i);
                    long start2 = protoOutputStream.start(2246267895810L);
                    keyAt.dumpDebug(protoOutputStream, 1146756268033L);
                    protoOutputStream.write(1120986464258L, this.mMaxCategoryCounts.get(keyAt).intValue());
                    protoOutputStream.write(1112396529667L, this.mCategoryCountWindowSizesMs.get(keyAt).longValue());
                    protoOutputStream.end(start2);
                }
                this.mExecutionStatsCache.forEach(new com.android.server.utils.quota.UptcMap.UptcDataConsumer() { // from class: com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda1
                    @Override // com.android.server.utils.quota.UptcMap.UptcDataConsumer
                    public final void accept(int i2, java.lang.String str, java.lang.String str2, java.lang.Object obj) {
                        com.android.server.utils.quota.CountQuotaTracker.this.lambda$dump$8(protoOutputStream, i2, str, str2, (com.android.server.utils.quota.CountQuotaTracker.ExecutionStats) obj);
                    }
                });
                protoOutputStream.end(start);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$8(android.util.proto.ProtoOutputStream protoOutputStream, int i, java.lang.String str, java.lang.String str2, com.android.server.utils.quota.CountQuotaTracker.ExecutionStats executionStats) {
        boolean isIndividualQuotaFreeLocked = isIndividualQuotaFreeLocked(i, str);
        long start = protoOutputStream.start(2246267895811L);
        new com.android.server.utils.quota.Uptc(i, str, str2).dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1133871366146L, isIndividualQuotaFreeLocked);
        android.util.LongArrayQueue longArrayQueue = this.mEventTimes.get(i, str, str2);
        if (longArrayQueue != null) {
            for (int size = longArrayQueue.size() - 1; size >= 0; size--) {
                long start2 = protoOutputStream.start(2246267895811L);
                protoOutputStream.write(1112396529665L, longArrayQueue.get(size));
                protoOutputStream.end(start2);
            }
        }
        long start3 = protoOutputStream.start(2246267895812L);
        protoOutputStream.write(1112396529665L, executionStats.expirationTimeElapsed);
        protoOutputStream.write(1112396529666L, executionStats.windowSizeMs);
        protoOutputStream.write(1120986464259L, executionStats.countLimit);
        protoOutputStream.write(1120986464260L, executionStats.countInWindow);
        protoOutputStream.write(1112396529669L, executionStats.inQuotaTimeElapsed);
        protoOutputStream.end(start3);
        protoOutputStream.end(start);
    }
}
