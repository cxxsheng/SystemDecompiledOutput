package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class PowerStatsScheduler {
    private final long mAggregatedPowerStatsSpanDuration;
    private final com.android.server.power.stats.PowerStatsScheduler.AlarmScheduler mAlarmScheduler;
    private final com.android.internal.os.Clock mClock;
    private final java.util.function.Supplier<java.lang.Long> mEarliestAvailableBatteryHistoryTimeMs;
    private boolean mEnablePeriodicPowerStatsCollection;
    private final android.os.Handler mHandler;
    private long mLastSavedSpanEndMonotonicTime;
    private final com.android.internal.os.MonotonicClock mMonotonicClock;
    private final long mPowerStatsAggregationPeriod;
    private final com.android.server.power.stats.PowerStatsAggregator mPowerStatsAggregator;
    private final java.lang.Runnable mPowerStatsCollector;
    private final com.android.server.power.stats.PowerStatsStore mPowerStatsStore;
    private static final long MINUTE_IN_MILLIS = java.util.concurrent.TimeUnit.MINUTES.toMillis(1);
    private static final long HOUR_IN_MILLIS = java.util.concurrent.TimeUnit.HOURS.toMillis(1);

    public interface AlarmScheduler {
        void scheduleAlarm(long j, java.lang.String str, android.app.AlarmManager.OnAlarmListener onAlarmListener, android.os.Handler handler);
    }

    public PowerStatsScheduler(java.lang.Runnable runnable, com.android.server.power.stats.PowerStatsAggregator powerStatsAggregator, long j, long j2, com.android.server.power.stats.PowerStatsStore powerStatsStore, com.android.server.power.stats.PowerStatsScheduler.AlarmScheduler alarmScheduler, com.android.internal.os.Clock clock, com.android.internal.os.MonotonicClock monotonicClock, java.util.function.Supplier<java.lang.Long> supplier, android.os.Handler handler) {
        this.mPowerStatsAggregator = powerStatsAggregator;
        this.mAggregatedPowerStatsSpanDuration = j;
        this.mPowerStatsAggregationPeriod = j2;
        this.mPowerStatsStore = powerStatsStore;
        this.mAlarmScheduler = alarmScheduler;
        this.mClock = clock;
        this.mMonotonicClock = monotonicClock;
        this.mHandler = handler;
        this.mPowerStatsCollector = runnable;
        this.mEarliestAvailableBatteryHistoryTimeMs = supplier;
    }

    public void start(boolean z) {
        this.mEnablePeriodicPowerStatsCollection = z;
        if (this.mEnablePeriodicPowerStatsCollection) {
            schedulePowerStatsAggregation();
            scheduleNextPowerStatsAggregation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleNextPowerStatsAggregation() {
        this.mAlarmScheduler.scheduleAlarm(this.mClock.elapsedRealtime() + this.mPowerStatsAggregationPeriod, "PowerStats", new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.power.stats.PowerStatsScheduler$$ExternalSyntheticLambda5
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                com.android.server.power.stats.PowerStatsScheduler.this.lambda$scheduleNextPowerStatsAggregation$0();
            }
        }, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleNextPowerStatsAggregation$0() {
        schedulePowerStatsAggregation();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.stats.PowerStatsScheduler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.PowerStatsScheduler.this.scheduleNextPowerStatsAggregation();
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    public void schedulePowerStatsAggregation() {
        this.mPowerStatsCollector.run();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.stats.PowerStatsScheduler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.PowerStatsScheduler.this.aggregateAndStorePowerStats();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aggregateAndStorePowerStats() {
        long j;
        long currentTimeMillis = this.mClock.currentTimeMillis();
        long monotonicTime = this.mMonotonicClock.monotonicTime();
        long lastSavedSpanEndMonotonicTime = getLastSavedSpanEndMonotonicTime();
        if (lastSavedSpanEndMonotonicTime >= 0) {
            j = lastSavedSpanEndMonotonicTime;
        } else {
            j = this.mEarliestAvailableBatteryHistoryTimeMs.get().longValue();
        }
        long alignToWallClock = alignToWallClock(j + this.mAggregatedPowerStatsSpanDuration, this.mAggregatedPowerStatsSpanDuration, monotonicTime, currentTimeMillis);
        while (true) {
            long j2 = j;
            j = alignToWallClock;
            if (j <= monotonicTime) {
                this.mPowerStatsAggregator.aggregatePowerStats(j2, j, new java.util.function.Consumer() { // from class: com.android.server.power.stats.PowerStatsScheduler$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.power.stats.PowerStatsScheduler.this.lambda$aggregateAndStorePowerStats$1((com.android.server.power.stats.AggregatedPowerStats) obj);
                    }
                });
                alignToWallClock = this.mAggregatedPowerStatsSpanDuration + j;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$aggregateAndStorePowerStats$1(com.android.server.power.stats.AggregatedPowerStats aggregatedPowerStats) {
        storeAggregatedPowerStats(aggregatedPowerStats);
        this.mLastSavedSpanEndMonotonicTime = aggregatedPowerStats.getStartTime() + aggregatedPowerStats.getDuration();
    }

    public void aggregateAndDumpPowerStats(java.io.PrintWriter printWriter) {
        if (this.mHandler.getLooper().isCurrentThread()) {
            throw new java.lang.IllegalStateException("Should not be executed on the bg handler thread.");
        }
        schedulePowerStatsAggregation();
        awaitCompletion();
        final android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.stats.PowerStatsScheduler$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.PowerStatsScheduler.this.lambda$aggregateAndDumpPowerStats$3(indentingPrintWriter);
            }
        });
        awaitCompletion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$aggregateAndDumpPowerStats$3(final android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mPowerStatsStore.dump(indentingPrintWriter);
        this.mPowerStatsAggregator.aggregatePowerStats(getLastSavedSpanEndMonotonicTime(), -1L, new java.util.function.Consumer() { // from class: com.android.server.power.stats.PowerStatsScheduler$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.power.stats.PowerStatsScheduler.lambda$aggregateAndDumpPowerStats$2(indentingPrintWriter, (com.android.server.power.stats.AggregatedPowerStats) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$aggregateAndDumpPowerStats$2(android.util.IndentingPrintWriter indentingPrintWriter, com.android.server.power.stats.AggregatedPowerStats aggregatedPowerStats) {
        com.android.server.power.stats.PowerStatsSpan createPowerStatsSpan = com.android.server.power.stats.PowerStatsStore.createPowerStatsSpan(aggregatedPowerStats);
        if (createPowerStatsSpan != null) {
            createPowerStatsSpan.dump(indentingPrintWriter);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static long alignToWallClock(long j, long j2, long j3, long j4) {
        long j5 = j4 + (j - j3);
        if (j2 >= MINUTE_IN_MILLIS && java.util.concurrent.TimeUnit.HOURS.toMillis(1L) % j2 == 0) {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTimeInMillis((MINUTE_IN_MILLIS + j5) - 1);
            calendar.set(13, 0);
            calendar.set(14, 0);
            int i = (int) (j2 / MINUTE_IN_MILLIS);
            calendar.set(12, (((calendar.get(12) + i) - 1) / i) * i);
            return j + (calendar.getTimeInMillis() - j5);
        }
        if (j2 >= HOUR_IN_MILLIS && java.util.concurrent.TimeUnit.DAYS.toMillis(1L) % j2 == 0) {
            java.util.Calendar calendar2 = java.util.Calendar.getInstance();
            calendar2.setTimeInMillis((HOUR_IN_MILLIS + j5) - 1);
            calendar2.set(12, 0);
            calendar2.set(13, 0);
            calendar2.set(14, 0);
            int i2 = (int) (j2 / HOUR_IN_MILLIS);
            calendar2.set(11, (((calendar2.get(11) + i2) - 1) / i2) * i2);
            return j + (calendar2.getTimeInMillis() - j5);
        }
        return j;
    }

    private long getLastSavedSpanEndMonotonicTime() {
        if (this.mLastSavedSpanEndMonotonicTime != 0) {
            return this.mLastSavedSpanEndMonotonicTime;
        }
        this.mLastSavedSpanEndMonotonicTime = -1L;
        for (com.android.server.power.stats.PowerStatsSpan.Metadata metadata : this.mPowerStatsStore.getTableOfContents()) {
            if (metadata.getSections().contains(com.android.server.power.stats.AggregatedPowerStatsSection.TYPE)) {
                for (com.android.server.power.stats.PowerStatsSpan.TimeFrame timeFrame : metadata.getTimeFrames()) {
                    long j = timeFrame.startMonotonicTime + timeFrame.duration;
                    if (j > this.mLastSavedSpanEndMonotonicTime) {
                        this.mLastSavedSpanEndMonotonicTime = j;
                    }
                }
            }
        }
        return this.mLastSavedSpanEndMonotonicTime;
    }

    private void storeAggregatedPowerStats(com.android.server.power.stats.AggregatedPowerStats aggregatedPowerStats) {
        this.mPowerStatsStore.storeAggregatedPowerStats(aggregatedPowerStats);
    }

    private void awaitCompletion() {
        android.os.ConditionVariable conditionVariable = new android.os.ConditionVariable();
        android.os.Handler handler = this.mHandler;
        java.util.Objects.requireNonNull(conditionVariable);
        handler.post(new com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda11(conditionVariable));
        conditionVariable.block();
    }
}
