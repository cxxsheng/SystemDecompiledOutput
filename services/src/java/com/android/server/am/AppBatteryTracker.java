package com.android.server.am;

/* loaded from: classes.dex */
final class AppBatteryTracker extends com.android.server.am.BaseAppStateTracker<com.android.server.am.AppBatteryTracker.AppBatteryPolicy> implements com.android.server.am.AppRestrictionController.UidBatteryUsageProvider {
    static final com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage BATTERY_USAGE_NONE = new com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage();
    static final long BATTERY_USAGE_STATS_POLLING_INTERVAL_MS_DEBUG = 2000;
    static final long BATTERY_USAGE_STATS_POLLING_INTERVAL_MS_LONG = 1800000;
    static final long BATTERY_USAGE_STATS_POLLING_MIN_INTERVAL_MS_DEBUG = 2000;
    static final long BATTERY_USAGE_STATS_POLLING_MIN_INTERVAL_MS_LONG = 300000;
    static final boolean DEBUG_BACKGROUND_BATTERY_TRACKER = false;
    static final boolean DEBUG_BACKGROUND_BATTERY_TRACKER_VERBOSE = false;
    static final java.lang.String TAG = "ActivityManager";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mActiveUserIdStates;
    private final long mBatteryUsageStatsPollingIntervalMs;
    private final long mBatteryUsageStatsPollingMinIntervalMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mBatteryUsageStatsUpdatePending;
    private final java.lang.Runnable mBgBatteryUsageStatsCheck;
    private final java.lang.Runnable mBgBatteryUsageStatsPolling;
    private final android.util.SparseArray<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> mDebugUidPercentages;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastBatteryUsageSamplingTs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastReportTime;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> mLastUidBatteryUsage;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastUidBatteryUsageStartTs;
    private final android.util.SparseArray<com.android.server.am.AppBatteryTracker.BatteryUsage> mTmpUidBatteryUsage;
    private final android.util.SparseArray<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> mTmpUidBatteryUsage2;
    private final android.util.SparseArray<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> mTmpUidBatteryUsageInWindow;
    private final android.util.ArraySet<android.os.UserHandle> mTmpUserIds;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.am.AppBatteryTracker.BatteryUsage> mUidBatteryUsage;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> mUidBatteryUsageInWindow;

    AppBatteryTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController) {
        this(context, appRestrictionController, null, null);
    }

    AppBatteryTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController, java.lang.reflect.Constructor<? extends com.android.server.am.BaseAppStateTracker.Injector<com.android.server.am.AppBatteryTracker.AppBatteryPolicy>> constructor, java.lang.Object obj) {
        super(context, appRestrictionController, constructor, obj);
        this.mBgBatteryUsageStatsPolling = new java.lang.Runnable() { // from class: com.android.server.am.AppBatteryTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.AppBatteryTracker.this.updateBatteryUsageStatsAndCheck();
            }
        };
        this.mBgBatteryUsageStatsCheck = new java.lang.Runnable() { // from class: com.android.server.am.AppBatteryTracker$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.AppBatteryTracker.this.checkBatteryUsageStats();
            }
        };
        this.mActiveUserIdStates = new android.util.SparseBooleanArray();
        this.mUidBatteryUsage = new android.util.SparseArray<>();
        this.mUidBatteryUsageInWindow = new android.util.SparseArray<>();
        this.mLastUidBatteryUsage = new android.util.SparseArray<>();
        this.mTmpUidBatteryUsage = new android.util.SparseArray<>();
        this.mTmpUidBatteryUsage2 = new android.util.SparseArray<>();
        this.mTmpUidBatteryUsageInWindow = new android.util.SparseArray<>();
        this.mTmpUserIds = new android.util.ArraySet<>();
        this.mLastReportTime = 0L;
        this.mDebugUidPercentages = new android.util.SparseArray<>();
        if (constructor == null) {
            this.mBatteryUsageStatsPollingIntervalMs = 1800000L;
            this.mBatteryUsageStatsPollingMinIntervalMs = 300000L;
        } else {
            this.mBatteryUsageStatsPollingIntervalMs = 2000L;
            this.mBatteryUsageStatsPollingMinIntervalMs = 2000L;
        }
        this.mInjector.setPolicy(new com.android.server.am.AppBatteryTracker.AppBatteryPolicy(this.mInjector, this));
    }

    @Override // com.android.server.am.BaseAppStateTracker
    @com.android.server.am.AppRestrictionController.TrackerType
    int getType() {
        return 1;
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onSystemReady() {
        super.onSystemReady();
        com.android.server.pm.UserManagerInternal userManagerInternal = this.mInjector.getUserManagerInternal();
        for (int i : userManagerInternal.getUserIds()) {
            if (userManagerInternal.isUserRunning(i)) {
                synchronized (this.mLock) {
                    this.mActiveUserIdStates.put(i, true);
                }
            }
        }
        scheduleBatteryUsageStatsUpdateIfNecessary(this.mBatteryUsageStatsPollingIntervalMs);
    }

    private void scheduleBatteryUsageStatsUpdateIfNecessary(long j) {
        if (((com.android.server.am.AppBatteryTracker.AppBatteryPolicy) this.mInjector.getPolicy()).isEnabled()) {
            synchronized (this.mLock) {
                try {
                    if (!this.mBgHandler.hasCallbacks(this.mBgBatteryUsageStatsPolling)) {
                        this.mBgHandler.postDelayed(this.mBgBatteryUsageStatsPolling, j);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            logAppBatteryTrackerIfNeeded();
        }
    }

    private void logAppBatteryTrackerIfNeeded() {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        synchronized (this.mLock) {
            try {
                if (elapsedRealtime - this.mLastReportTime < ((com.android.server.am.AppBatteryTracker.AppBatteryPolicy) this.mInjector.getPolicy()).mBgCurrentDrainWindowMs) {
                    return;
                }
                this.mLastReportTime = elapsedRealtime;
                updateBatteryUsageStatsIfNecessary(this.mInjector.currentTimeMillis(), true);
                synchronized (this.mLock) {
                    try {
                        int size = this.mUidBatteryUsageInWindow.size();
                        for (int i = 0; i < size; i++) {
                            int keyAt = this.mUidBatteryUsageInWindow.keyAt(i);
                            if ((android.os.UserHandle.isCore(keyAt) || android.os.UserHandle.isApp(keyAt)) && !BATTERY_USAGE_NONE.equals(this.mUidBatteryUsageInWindow.valueAt(i))) {
                                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO, keyAt, 0, 0, 0, (byte[]) null, getTrackerInfoForStatsd(keyAt), (byte[]) null, (byte[]) null, 0, 0, 0, android.app.ActivityManager.isLowRamDeviceStatic(), 0);
                            }
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    byte[] getTrackerInfoForStatsd(int i) {
        com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage;
        synchronized (this.mLock) {
            immutableBatteryUsage = this.mUidBatteryUsageInWindow.get(i);
        }
        if (immutableBatteryUsage == null) {
            return null;
        }
        com.android.server.am.AppBatteryTracker.BatteryUsage calcPercentage = immutableBatteryUsage.calcPercentage(i, (com.android.server.am.AppBatteryTracker.AppBatteryPolicy) this.mInjector.getPolicy());
        double d = calcPercentage.mPercentage[0] + calcPercentage.mPercentage[1] + calcPercentage.mPercentage[2] + calcPercentage.mPercentage[3] + calcPercentage.mPercentage[4];
        double d2 = calcPercentage.mPercentage[2];
        double d3 = calcPercentage.mPercentage[3];
        double d4 = calcPercentage.mPercentage[1];
        double d5 = calcPercentage.mPercentage[4];
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        protoOutputStream.write(1120986464257L, d * 10000.0d);
        protoOutputStream.write(1120986464258L, d2 * 10000.0d);
        protoOutputStream.write(1120986464259L, d3 * 10000.0d);
        protoOutputStream.write(1120986464260L, d4 * 10000.0d);
        protoOutputStream.write(1120986464261L, d5 * 10000.0d);
        protoOutputStream.flush();
        return protoOutputStream.getBytes();
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onUserStarted(int i) {
        synchronized (this.mLock) {
            this.mActiveUserIdStates.put(i, true);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onUserStopped(int i) {
        synchronized (this.mLock) {
            this.mActiveUserIdStates.put(i, false);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                this.mActiveUserIdStates.delete(i);
                for (int size = this.mUidBatteryUsage.size() - 1; size >= 0; size--) {
                    if (android.os.UserHandle.getUserId(this.mUidBatteryUsage.keyAt(size)) == i) {
                        this.mUidBatteryUsage.removeAt(size);
                    }
                }
                for (int size2 = this.mUidBatteryUsageInWindow.size() - 1; size2 >= 0; size2--) {
                    if (android.os.UserHandle.getUserId(this.mUidBatteryUsageInWindow.keyAt(size2)) == i) {
                        this.mUidBatteryUsageInWindow.removeAt(size2);
                    }
                }
                ((com.android.server.am.AppBatteryTracker.AppBatteryPolicy) this.mInjector.getPolicy()).onUserRemovedLocked(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onUidRemoved(int i) {
        synchronized (this.mLock) {
            this.mUidBatteryUsage.delete(i);
            this.mUidBatteryUsageInWindow.delete(i);
            ((com.android.server.am.AppBatteryTracker.AppBatteryPolicy) this.mInjector.getPolicy()).onUidRemovedLocked(i);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onUserInteractionStarted(java.lang.String str, int i) {
        ((com.android.server.am.AppBatteryTracker.AppBatteryPolicy) this.mInjector.getPolicy()).onUserInteractionStarted(str, i);
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onBackgroundRestrictionChanged(int i, java.lang.String str, boolean z) {
        ((com.android.server.am.AppBatteryTracker.AppBatteryPolicy) this.mInjector.getPolicy()).onBackgroundRestrictionChanged(i, str, z);
    }

    @Override // com.android.server.am.AppRestrictionController.UidBatteryUsageProvider
    @android.annotation.NonNull
    public com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage getUidBatteryUsage(int i) {
        com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage;
        boolean updateBatteryUsageStatsIfNecessary = updateBatteryUsageStatsIfNecessary(this.mInjector.currentTimeMillis(), false);
        synchronized (this.mLock) {
            if (updateBatteryUsageStatsIfNecessary) {
                try {
                    this.mBgHandler.removeCallbacks(this.mBgBatteryUsageStatsPolling);
                    scheduleBgBatteryUsageStatsCheck();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage = this.mUidBatteryUsage.get(i);
            immutableBatteryUsage = batteryUsage != null ? new com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage(batteryUsage) : BATTERY_USAGE_NONE;
        }
        return immutableBatteryUsage;
    }

    private void scheduleBgBatteryUsageStatsCheck() {
        if (!this.mBgHandler.hasCallbacks(this.mBgBatteryUsageStatsCheck)) {
            this.mBgHandler.post(this.mBgBatteryUsageStatsCheck);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBatteryUsageStatsAndCheck() {
        long currentTimeMillis = this.mInjector.currentTimeMillis();
        if (updateBatteryUsageStatsIfNecessary(currentTimeMillis, false)) {
            checkBatteryUsageStats();
            return;
        }
        synchronized (this.mLock) {
            scheduleBatteryUsageStatsUpdateIfNecessary((this.mLastBatteryUsageSamplingTs + this.mBatteryUsageStatsPollingMinIntervalMs) - currentTimeMillis);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkBatteryUsageStats() {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        com.android.server.am.AppBatteryTracker.AppBatteryPolicy appBatteryPolicy = (com.android.server.am.AppBatteryTracker.AppBatteryPolicy) this.mInjector.getPolicy();
        try {
            android.util.SparseArray<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> sparseArray = this.mTmpUidBatteryUsageInWindow;
            synchronized (this.mLock) {
                copyUidBatteryUsage(this.mUidBatteryUsageInWindow, sparseArray);
            }
            long max = java.lang.Math.max(0L, elapsedRealtime - appBatteryPolicy.mBgCurrentDrainWindowMs);
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                int keyAt = sparseArray.keyAt(i);
                com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage valueAt = sparseArray.valueAt(i);
                appBatteryPolicy.handleUidBatteryUsage(keyAt, valueAt.mutate().subtract(this.mAppRestrictionController.getUidBatteryExemptedUsageSince(keyAt, max, elapsedRealtime, appBatteryPolicy.mBgCurrentDrainExemptedTypes)).calcPercentage(keyAt, appBatteryPolicy).unmutate());
            }
            int size2 = this.mDebugUidPercentages.size();
            for (int i2 = 0; i2 < size2; i2++) {
                appBatteryPolicy.handleUidBatteryUsage(this.mDebugUidPercentages.keyAt(i2), this.mDebugUidPercentages.valueAt(i2));
            }
            scheduleBatteryUsageStatsUpdateIfNecessary(this.mBatteryUsageStatsPollingIntervalMs);
        } catch (java.lang.Throwable th) {
            scheduleBatteryUsageStatsUpdateIfNecessary(this.mBatteryUsageStatsPollingIntervalMs);
            throw th;
        }
    }

    private boolean updateBatteryUsageStatsIfNecessary(long j, boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                if (this.mLastBatteryUsageSamplingTs + this.mBatteryUsageStatsPollingMinIntervalMs >= j && !z) {
                    return false;
                }
                if (this.mBatteryUsageStatsUpdatePending) {
                    try {
                        this.mLock.wait();
                    } catch (java.lang.InterruptedException e) {
                    }
                    z2 = false;
                } else {
                    this.mBatteryUsageStatsUpdatePending = true;
                    z2 = true;
                }
                if (z2) {
                    updateBatteryUsageStatsOnce(j);
                    synchronized (this.mLock) {
                        this.mLastBatteryUsageSamplingTs = j;
                        this.mBatteryUsageStatsUpdatePending = false;
                        this.mLock.notifyAll();
                    }
                }
                return true;
            } finally {
            }
        }
    }

    private void updateBatteryUsageStatsOnce(long j) {
        boolean z;
        long j2;
        long j3;
        long j4;
        long j5;
        com.android.server.am.AppBatteryTracker.AppBatteryPolicy appBatteryPolicy = (com.android.server.am.AppBatteryTracker.AppBatteryPolicy) this.mInjector.getPolicy();
        android.util.ArraySet<android.os.UserHandle> arraySet = this.mTmpUserIds;
        android.util.SparseArray<com.android.server.am.AppBatteryTracker.BatteryUsage> sparseArray = this.mTmpUidBatteryUsage;
        android.os.BatteryStatsInternal batteryStatsInternal = this.mInjector.getBatteryStatsInternal();
        long j6 = appBatteryPolicy.mBgCurrentDrainWindowMs;
        sparseArray.clear();
        arraySet.clear();
        synchronized (this.mLock) {
            try {
                z = true;
                for (int size = this.mActiveUserIdStates.size() - 1; size >= 0; size--) {
                    arraySet.add(android.os.UserHandle.of(this.mActiveUserIdStates.keyAt(size)));
                    if (!this.mActiveUserIdStates.valueAt(size)) {
                        this.mActiveUserIdStates.removeAt(size);
                    }
                }
            } finally {
            }
        }
        android.os.BatteryUsageStats updateBatteryUsageStatsOnceInternal = updateBatteryUsageStatsOnceInternal(0L, sparseArray, new android.os.BatteryUsageStatsQuery.Builder().includeProcessStateData().setMaxStatsAgeMs(0L), arraySet, batteryStatsInternal);
        long statsStartTimestamp = updateBatteryUsageStatsOnceInternal != null ? updateBatteryUsageStatsOnceInternal.getStatsStartTimestamp() : 0L;
        long statsEndTimestamp = (updateBatteryUsageStatsOnceInternal != null ? updateBatteryUsageStatsOnceInternal.getStatsEndTimestamp() : j) - statsStartTimestamp;
        if (statsEndTimestamp < j6) {
            j2 = j6;
        } else {
            synchronized (this.mLock) {
                j2 = j6;
                copyUidBatteryUsage(sparseArray, this.mUidBatteryUsageInWindow, (j6 * 1.0d) / statsEndTimestamp);
            }
            z = false;
        }
        this.mTmpUidBatteryUsage2.clear();
        copyUidBatteryUsage(sparseArray, this.mTmpUidBatteryUsage2);
        synchronized (this.mLock) {
            j3 = this.mLastUidBatteryUsageStartTs;
            this.mLastUidBatteryUsageStartTs = statsStartTimestamp;
        }
        if (statsStartTimestamp > j3 && j3 > 0) {
            android.os.BatteryUsageStats updateBatteryUsageStatsOnceInternal2 = updateBatteryUsageStatsOnceInternal(0L, sparseArray, new android.os.BatteryUsageStatsQuery.Builder().includeProcessStateData().aggregateSnapshots(j3, statsStartTimestamp), arraySet, batteryStatsInternal);
            j4 = statsEndTimestamp + (statsStartTimestamp - j3);
            try {
                if (updateBatteryUsageStatsOnceInternal2 != null) {
                    updateBatteryUsageStatsOnceInternal2.close();
                } else {
                    android.util.Slog.w(TAG, "Stat was null");
                }
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Failed to close a stat");
            }
        } else {
            j4 = statsEndTimestamp;
        }
        if (!z || j4 < j2) {
            j5 = j2;
        } else {
            synchronized (this.mLock) {
                j5 = j2;
                copyUidBatteryUsage(sparseArray, this.mUidBatteryUsageInWindow, (j5 * 1.0d) / j4);
            }
            z = false;
        }
        synchronized (this.mLock) {
            try {
                int size2 = sparseArray.size();
                for (int i = 0; i < size2; i++) {
                    int keyAt = sparseArray.keyAt(i);
                    int indexOfKey = this.mUidBatteryUsage.indexOfKey(keyAt);
                    com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage = this.mLastUidBatteryUsage.get(keyAt, BATTERY_USAGE_NONE);
                    com.android.server.am.AppBatteryTracker.BatteryUsage valueAt = sparseArray.valueAt(i);
                    if (indexOfKey >= 0) {
                        this.mUidBatteryUsage.valueAt(indexOfKey).subtract(immutableBatteryUsage).add(valueAt);
                    } else {
                        this.mUidBatteryUsage.put(keyAt, valueAt);
                    }
                }
                copyUidBatteryUsage(this.mTmpUidBatteryUsage2, this.mLastUidBatteryUsage);
            } finally {
            }
        }
        this.mTmpUidBatteryUsage2.clear();
        if (z) {
            long j7 = j - j5;
            long j8 = j3 - 1;
            updateBatteryUsageStatsOnceInternal(j8 - j7, sparseArray, new android.os.BatteryUsageStatsQuery.Builder().includeProcessStateData().aggregateSnapshots(j7, j8), arraySet, batteryStatsInternal);
            synchronized (this.mLock) {
                copyUidBatteryUsage(sparseArray, this.mUidBatteryUsageInWindow);
            }
        }
        try {
            if (updateBatteryUsageStatsOnceInternal != null) {
                updateBatteryUsageStatsOnceInternal.close();
            } else {
                android.util.Slog.w(TAG, "Stat was null");
            }
        } catch (java.io.IOException e2) {
            android.util.Slog.w(TAG, "Failed to close a stat");
        }
    }

    private android.os.BatteryUsageStats updateBatteryUsageStatsOnceInternal(long j, android.util.SparseArray<com.android.server.am.AppBatteryTracker.BatteryUsage> sparseArray, android.os.BatteryUsageStatsQuery.Builder builder, android.util.ArraySet<android.os.UserHandle> arraySet, android.os.BatteryStatsInternal batteryStatsInternal) {
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            builder.addUser(arraySet.valueAt(i));
        }
        java.util.List<android.os.BatteryUsageStats> batteryUsageStats = batteryStatsInternal.getBatteryUsageStats(java.util.Arrays.asList(builder.build()));
        if (com.android.internal.util.ArrayUtils.isEmpty(batteryUsageStats)) {
            return null;
        }
        android.os.BatteryUsageStats batteryUsageStats2 = batteryUsageStats.get(0);
        for (int i2 = 1; i2 < batteryUsageStats.size(); i2++) {
            try {
                if (batteryUsageStats.get(i2) == null) {
                    android.util.Slog.w(TAG, "Stat was null");
                } else {
                    batteryUsageStats.get(i2).close();
                }
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Failed to close a stat in BatteryUsageStats List");
            }
        }
        java.util.List<android.os.UidBatteryConsumer> uidBatteryConsumers = batteryUsageStats2.getUidBatteryConsumers();
        if (uidBatteryConsumers != null) {
            double min = j > 0 ? java.lang.Math.min((j * 1.0d) / (batteryUsageStats2.getStatsEndTimestamp() - batteryUsageStats2.getStatsStartTimestamp()), 1.0d) : 1.0d;
            com.android.server.am.AppBatteryTracker.AppBatteryPolicy appBatteryPolicy = (com.android.server.am.AppBatteryTracker.AppBatteryPolicy) this.mInjector.getPolicy();
            for (android.os.UidBatteryConsumer uidBatteryConsumer : uidBatteryConsumers) {
                int uid = uidBatteryConsumer.getUid();
                if (!android.os.UserHandle.isIsolated(uid)) {
                    int appIdFromSharedAppGid = android.os.UserHandle.getAppIdFromSharedAppGid(uid);
                    if (appIdFromSharedAppGid > 0) {
                        uid = android.os.UserHandle.getUid(0, appIdFromSharedAppGid);
                    }
                    com.android.server.am.AppBatteryTracker.BatteryUsage scale = new com.android.server.am.AppBatteryTracker.BatteryUsage(uidBatteryConsumer, appBatteryPolicy).scale(min);
                    int indexOfKey = sparseArray.indexOfKey(uid);
                    if (indexOfKey < 0) {
                        sparseArray.put(uid, scale);
                    } else {
                        sparseArray.valueAt(indexOfKey).add(scale);
                    }
                }
            }
        }
        return batteryUsageStats2;
    }

    private static void copyUidBatteryUsage(android.util.SparseArray<? extends com.android.server.am.AppBatteryTracker.BatteryUsage> sparseArray, android.util.SparseArray<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> sparseArray2) {
        sparseArray2.clear();
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            sparseArray2.put(sparseArray.keyAt(size), new com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage(sparseArray.valueAt(size)));
        }
    }

    private static void copyUidBatteryUsage(android.util.SparseArray<? extends com.android.server.am.AppBatteryTracker.BatteryUsage> sparseArray, android.util.SparseArray<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> sparseArray2, double d) {
        sparseArray2.clear();
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            sparseArray2.put(sparseArray.keyAt(size), new com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage(sparseArray.valueAt(size), d));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCurrentDrainMonitorEnabled(boolean z) {
        if (z) {
            if (!this.mBgHandler.hasCallbacks(this.mBgBatteryUsageStatsPolling)) {
                this.mBgHandler.postDelayed(this.mBgBatteryUsageStatsPolling, this.mBatteryUsageStatsPollingIntervalMs);
                return;
            }
            return;
        }
        this.mBgHandler.removeCallbacks(this.mBgBatteryUsageStatsPolling);
        synchronized (this.mLock) {
            if (this.mBatteryUsageStatsUpdatePending) {
                try {
                    this.mLock.wait();
                } catch (java.lang.InterruptedException e) {
                }
            }
            this.mUidBatteryUsage.clear();
            this.mUidBatteryUsageInWindow.clear();
            this.mLastUidBatteryUsage.clear();
            this.mLastBatteryUsageSamplingTs = 0L;
            this.mLastUidBatteryUsageStartTs = 0L;
        }
    }

    void setDebugUidPercentage(int[] iArr, double[][] dArr) {
        this.mDebugUidPercentages.clear();
        for (int i = 0; i < iArr.length; i++) {
            this.mDebugUidPercentages.put(iArr[i], new com.android.server.am.AppBatteryTracker.BatteryUsage().setPercentage(dArr[i]).unmutate());
        }
        scheduleBgBatteryUsageStatsCheck();
    }

    void clearDebugUidPercentage() {
        this.mDebugUidPercentages.clear();
        scheduleBgBatteryUsageStatsCheck();
    }

    @com.android.internal.annotations.VisibleForTesting
    void reset() {
        synchronized (this.mLock) {
            this.mUidBatteryUsage.clear();
            this.mUidBatteryUsageInWindow.clear();
            this.mLastUidBatteryUsage.clear();
            this.mLastBatteryUsageSamplingTs = 0L;
            this.mLastUidBatteryUsageStartTs = 0L;
        }
        this.mBgHandler.removeCallbacks(this.mBgBatteryUsageStatsPolling);
        updateBatteryUsageStatsAndCheck();
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.println("APP BATTERY STATE TRACKER:");
        updateBatteryUsageStatsIfNecessary(this.mInjector.currentTimeMillis(), true);
        scheduleBgBatteryUsageStatsCheck();
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        this.mBgHandler.getLooper().getQueue().addIdleHandler(new android.os.MessageQueue.IdleHandler() { // from class: com.android.server.am.AppBatteryTracker$$ExternalSyntheticLambda2
            @Override // android.os.MessageQueue.IdleHandler
            public final boolean queueIdle() {
                boolean lambda$dump$0;
                lambda$dump$0 = com.android.server.am.AppBatteryTracker.lambda$dump$0(countDownLatch);
                return lambda$dump$0;
            }
        });
        try {
            countDownLatch.await();
        } catch (java.lang.InterruptedException e) {
        }
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> sparseArray = this.mUidBatteryUsageInWindow;
                printWriter.print("  " + str);
                printWriter.print("  Last battery usage start=");
                android.util.TimeUtils.dumpTime(printWriter, this.mLastUidBatteryUsageStartTs);
                printWriter.println();
                printWriter.print("  " + str);
                printWriter.print("Battery usage over last ");
                java.lang.String str2 = "    " + str;
                com.android.server.am.AppBatteryTracker.AppBatteryPolicy appBatteryPolicy = (com.android.server.am.AppBatteryTracker.AppBatteryPolicy) this.mInjector.getPolicy();
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                long max = java.lang.Math.max(0L, elapsedRealtime - appBatteryPolicy.mBgCurrentDrainWindowMs);
                printWriter.println(android.util.TimeUtils.formatDuration(elapsedRealtime - max));
                if (sparseArray.size() == 0) {
                    printWriter.print(str2);
                    printWriter.println("(none)");
                } else {
                    int i = 0;
                    for (int size = sparseArray.size(); i < size; size = size) {
                        int keyAt = sparseArray.keyAt(i);
                        com.android.server.am.AppBatteryTracker.BatteryUsage calcPercentage = sparseArray.valueAt(i).calcPercentage(keyAt, appBatteryPolicy);
                        com.android.server.am.AppBatteryTracker.BatteryUsage calcPercentage2 = this.mAppRestrictionController.getUidBatteryExemptedUsageSince(keyAt, max, elapsedRealtime, appBatteryPolicy.mBgCurrentDrainExemptedTypes).calcPercentage(keyAt, appBatteryPolicy);
                        com.android.server.am.AppBatteryTracker.BatteryUsage calcPercentage3 = new com.android.server.am.AppBatteryTracker.BatteryUsage(calcPercentage).subtract(calcPercentage2).calcPercentage(keyAt, appBatteryPolicy);
                        printWriter.format("%s%s: [%s] %s (%s) | %s (%s) | %s (%s) | %s\n", str2, android.os.UserHandle.formatUid(keyAt), android.os.PowerExemptionManager.reasonCodeToString(appBatteryPolicy.shouldExemptUid(keyAt)), calcPercentage.toString(), calcPercentage.percentageToString(), calcPercentage2.toString(), calcPercentage2.percentageToString(), calcPercentage3.toString(), calcPercentage3.percentageToString(), this.mUidBatteryUsage.get(keyAt, BATTERY_USAGE_NONE).toString());
                        i++;
                        sparseArray = sparseArray;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        super.dump(printWriter, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$dump$0(java.util.concurrent.CountDownLatch countDownLatch) {
        countDownLatch.countDown();
        return false;
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void dumpAsProto(android.util.proto.ProtoOutputStream protoOutputStream, int i) {
        updateBatteryUsageStatsIfNecessary(this.mInjector.currentTimeMillis(), true);
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> sparseArray = this.mUidBatteryUsageInWindow;
                if (i != -1) {
                    com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage = sparseArray.get(i);
                    if (immutableBatteryUsage != null) {
                        dumpUidStats(protoOutputStream, i, immutableBatteryUsage);
                    }
                } else {
                    int size = sparseArray.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        dumpUidStats(protoOutputStream, sparseArray.keyAt(i2), sparseArray.valueAt(i2));
                    }
                }
            } finally {
            }
        }
    }

    private void dumpUidStats(android.util.proto.ProtoOutputStream protoOutputStream, int i, com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage) {
        if (batteryUsage.mUsage == null) {
            return;
        }
        double usagePowerMah = batteryUsage.getUsagePowerMah(1);
        double usagePowerMah2 = batteryUsage.getUsagePowerMah(2);
        double usagePowerMah3 = batteryUsage.getUsagePowerMah(3);
        double usagePowerMah4 = batteryUsage.getUsagePowerMah(4);
        if (usagePowerMah == 0.0d && usagePowerMah2 == 0.0d && usagePowerMah3 == 0.0d) {
            return;
        }
        long start = protoOutputStream.start(2246267895809L);
        protoOutputStream.write(1120986464257L, i);
        dumpProcessStateStats(protoOutputStream, 1, usagePowerMah);
        dumpProcessStateStats(protoOutputStream, 2, usagePowerMah2);
        dumpProcessStateStats(protoOutputStream, 3, usagePowerMah3);
        dumpProcessStateStats(protoOutputStream, 4, usagePowerMah4);
        protoOutputStream.end(start);
    }

    private void dumpProcessStateStats(android.util.proto.ProtoOutputStream protoOutputStream, int i, double d) {
        if (d == 0.0d) {
            return;
        }
        long start = protoOutputStream.start(2246267895810L);
        protoOutputStream.write(1159641169921L, i);
        protoOutputStream.write(1103806595075L, d);
        protoOutputStream.end(start);
    }

    static class BatteryUsage {
        static final int BATTERY_USAGE_COUNT = 5;
        static final int BATTERY_USAGE_INDEX_BACKGROUND = 2;
        static final int BATTERY_USAGE_INDEX_CACHED = 4;
        static final int BATTERY_USAGE_INDEX_FOREGROUND = 1;
        static final int BATTERY_USAGE_INDEX_FOREGROUND_SERVICE = 3;
        static final int BATTERY_USAGE_INDEX_UNSPECIFIED = 0;
        static final android.os.BatteryConsumer.Dimensions[] BATT_DIMENS = {new android.os.BatteryConsumer.Dimensions(-1, 0), new android.os.BatteryConsumer.Dimensions(-1, 1), new android.os.BatteryConsumer.Dimensions(-1, 2), new android.os.BatteryConsumer.Dimensions(-1, 3), new android.os.BatteryConsumer.Dimensions(-1, 4)};

        @android.annotation.Nullable
        double[] mPercentage;

        @android.annotation.NonNull
        double[] mUsage;

        BatteryUsage() {
            this(0.0d, 0.0d, 0.0d, 0.0d, 0.0d);
        }

        BatteryUsage(double d, double d2, double d3, double d4, double d5) {
            this.mUsage = new double[]{d, d2, d3, d4, d5};
        }

        BatteryUsage(@android.annotation.NonNull double[] dArr) {
            this.mUsage = dArr;
        }

        BatteryUsage(@android.annotation.NonNull com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage, double d) {
            this(batteryUsage);
            scaleInternal(d);
        }

        BatteryUsage(@android.annotation.NonNull com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage) {
            this.mUsage = new double[batteryUsage.mUsage.length];
            setToInternal(batteryUsage);
        }

        BatteryUsage(@android.annotation.NonNull android.os.UidBatteryConsumer uidBatteryConsumer, @android.annotation.NonNull com.android.server.am.AppBatteryTracker.AppBatteryPolicy appBatteryPolicy) {
            android.os.BatteryConsumer.Dimensions[] dimensionsArr = appBatteryPolicy.mBatteryDimensions;
            this.mUsage = new double[]{getConsumedPowerNoThrow(uidBatteryConsumer, dimensionsArr[0]), getConsumedPowerNoThrow(uidBatteryConsumer, dimensionsArr[1]), getConsumedPowerNoThrow(uidBatteryConsumer, dimensionsArr[2]), getConsumedPowerNoThrow(uidBatteryConsumer, dimensionsArr[3]), getConsumedPowerNoThrow(uidBatteryConsumer, dimensionsArr[4])};
        }

        com.android.server.am.AppBatteryTracker.BatteryUsage setTo(@android.annotation.NonNull com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage) {
            return setToInternal(batteryUsage);
        }

        private com.android.server.am.AppBatteryTracker.BatteryUsage setToInternal(@android.annotation.NonNull com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage) {
            java.lang.System.arraycopy(batteryUsage.mUsage, 0, this.mUsage, 0, batteryUsage.mUsage.length);
            if (batteryUsage.mPercentage != null) {
                this.mPercentage = new double[batteryUsage.mPercentage.length];
                java.lang.System.arraycopy(batteryUsage.mPercentage, 0, this.mPercentage, 0, batteryUsage.mPercentage.length);
            } else {
                this.mPercentage = null;
            }
            return this;
        }

        com.android.server.am.AppBatteryTracker.BatteryUsage add(@android.annotation.NonNull com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage) {
            for (int i = 0; i < batteryUsage.mUsage.length; i++) {
                double[] dArr = this.mUsage;
                dArr[i] = dArr[i] + batteryUsage.mUsage[i];
            }
            return this;
        }

        com.android.server.am.AppBatteryTracker.BatteryUsage subtract(@android.annotation.NonNull com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage) {
            for (int i = 0; i < batteryUsage.mUsage.length; i++) {
                this.mUsage[i] = java.lang.Math.max(0.0d, this.mUsage[i] - batteryUsage.mUsage[i]);
            }
            return this;
        }

        com.android.server.am.AppBatteryTracker.BatteryUsage scale(double d) {
            return scaleInternal(d);
        }

        private com.android.server.am.AppBatteryTracker.BatteryUsage scaleInternal(double d) {
            for (int i = 0; i < this.mUsage.length; i++) {
                double[] dArr = this.mUsage;
                dArr[i] = dArr[i] * d;
            }
            return this;
        }

        com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage unmutate() {
            return new com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage(this);
        }

        com.android.server.am.AppBatteryTracker.BatteryUsage calcPercentage(int i, @android.annotation.NonNull com.android.server.am.AppBatteryTracker.AppBatteryPolicy appBatteryPolicy) {
            if (this.mPercentage == null || this.mPercentage.length != this.mUsage.length) {
                this.mPercentage = new double[this.mUsage.length];
            }
            appBatteryPolicy.calcPercentage(i, this.mUsage, this.mPercentage);
            return this;
        }

        com.android.server.am.AppBatteryTracker.BatteryUsage setPercentage(@android.annotation.NonNull double[] dArr) {
            this.mPercentage = dArr;
            return this;
        }

        double[] getPercentage() {
            return this.mPercentage;
        }

        java.lang.String percentageToString() {
            return formatBatteryUsagePercentage(this.mPercentage);
        }

        public java.lang.String toString() {
            return formatBatteryUsage(this.mUsage);
        }

        double getUsagePowerMah(int i) {
            switch (i) {
                case 1:
                    return this.mUsage[1];
                case 2:
                    return this.mUsage[2];
                case 3:
                    return this.mUsage[3];
                case 4:
                    return this.mUsage[4];
                default:
                    return 0.0d;
            }
        }

        boolean isValid() {
            for (int i = 0; i < this.mUsage.length; i++) {
                if (this.mUsage[i] < 0.0d) {
                    return false;
                }
            }
            return true;
        }

        boolean isEmpty() {
            for (int i = 0; i < this.mUsage.length; i++) {
                if (this.mUsage[i] > 0.0d) {
                    return false;
                }
            }
            return true;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage = (com.android.server.am.AppBatteryTracker.BatteryUsage) obj;
            for (int i = 0; i < this.mUsage.length; i++) {
                if (java.lang.Double.compare(this.mUsage[i], batteryUsage.mUsage[i]) != 0) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int i = 0;
            for (int i2 = 0; i2 < this.mUsage.length; i2++) {
                i = (i * 31) + java.lang.Double.hashCode(this.mUsage[i2]);
            }
            return i;
        }

        private static java.lang.String formatBatteryUsage(double[] dArr) {
            return java.lang.String.format("%.3f %.3f %.3f %.3f %.3f mAh", java.lang.Double.valueOf(dArr[0]), java.lang.Double.valueOf(dArr[1]), java.lang.Double.valueOf(dArr[2]), java.lang.Double.valueOf(dArr[3]), java.lang.Double.valueOf(dArr[4]));
        }

        static java.lang.String formatBatteryUsagePercentage(double[] dArr) {
            return java.lang.String.format("%4.2f%% %4.2f%% %4.2f%% %4.2f%% %4.2f%%", java.lang.Double.valueOf(dArr[0]), java.lang.Double.valueOf(dArr[1]), java.lang.Double.valueOf(dArr[2]), java.lang.Double.valueOf(dArr[3]), java.lang.Double.valueOf(dArr[4]));
        }

        private static double getConsumedPowerNoThrow(android.os.UidBatteryConsumer uidBatteryConsumer, android.os.BatteryConsumer.Dimensions dimensions) {
            try {
                return uidBatteryConsumer.getConsumedPower(dimensions);
            } catch (java.lang.IllegalArgumentException e) {
                return 0.0d;
            }
        }
    }

    static final class ImmutableBatteryUsage extends com.android.server.am.AppBatteryTracker.BatteryUsage {
        ImmutableBatteryUsage() {
        }

        ImmutableBatteryUsage(double d, double d2, double d3, double d4, double d5) {
            super(d, d2, d3, d4, d5);
        }

        ImmutableBatteryUsage(@android.annotation.NonNull double[] dArr) {
            super(dArr);
        }

        ImmutableBatteryUsage(@android.annotation.NonNull com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage, double d) {
            super(batteryUsage, d);
        }

        ImmutableBatteryUsage(@android.annotation.NonNull com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage) {
            super(batteryUsage);
        }

        ImmutableBatteryUsage(@android.annotation.NonNull android.os.UidBatteryConsumer uidBatteryConsumer, @android.annotation.NonNull com.android.server.am.AppBatteryTracker.AppBatteryPolicy appBatteryPolicy) {
            super(uidBatteryConsumer, appBatteryPolicy);
        }

        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        com.android.server.am.AppBatteryTracker.BatteryUsage setTo(@android.annotation.NonNull com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage) {
            throw new java.lang.RuntimeException("Readonly");
        }

        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        com.android.server.am.AppBatteryTracker.BatteryUsage add(@android.annotation.NonNull com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage) {
            throw new java.lang.RuntimeException("Readonly");
        }

        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        com.android.server.am.AppBatteryTracker.BatteryUsage subtract(@android.annotation.NonNull com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage) {
            throw new java.lang.RuntimeException("Readonly");
        }

        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        com.android.server.am.AppBatteryTracker.BatteryUsage scale(double d) {
            throw new java.lang.RuntimeException("Readonly");
        }

        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        com.android.server.am.AppBatteryTracker.BatteryUsage setPercentage(@android.annotation.NonNull double[] dArr) {
            throw new java.lang.RuntimeException("Readonly");
        }

        com.android.server.am.AppBatteryTracker.BatteryUsage mutate() {
            return new com.android.server.am.AppBatteryTracker.BatteryUsage(this);
        }
    }

    static final class AppBatteryPolicy extends com.android.server.am.BaseAppStatePolicy<com.android.server.am.AppBatteryTracker> {
        static final int BATTERY_USAGE_TYPE_BACKGROUND = 4;
        static final int BATTERY_USAGE_TYPE_CACHED = 16;
        static final int BATTERY_USAGE_TYPE_FOREGROUND = 2;
        static final int BATTERY_USAGE_TYPE_FOREGROUND_SERVICE = 8;
        static final int BATTERY_USAGE_TYPE_UNSPECIFIED = 1;
        static final boolean DEFAULT_BG_CURRENT_DRAIN_DECOUPLE_THRESHOLD = true;
        static final int DEFAULT_BG_CURRENT_DRAIN_POWER_COMPONENTS = -1;
        static final int INDEX_HIGH_CURRENT_DRAIN_THRESHOLD = 1;
        static final int INDEX_REGULAR_CURRENT_DRAIN_THRESHOLD = 0;
        static final java.lang.String KEY_BG_CURRENT_DRAIN_AUTO_RESTRICT_ABUSIVE_APPS_ENABLED = "bg_current_drain_auto_restrict_abusive_apps_enabled";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_DECOUPLE_THRESHOLDS = "bg_current_drain_decouple_thresholds";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_EVENT_DURATION_BASED_THRESHOLD_ENABLED = "bg_current_drain_event_duration_based_threshold_enabled";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_EXEMPTED_TYPES = "bg_current_drain_exempted_types";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_BY_BG_LOCATION = "bg_current_drain_high_threshold_by_bg_location";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_TO_BG_RESTRICTED = "bg_current_drain_high_threshold_to_bg_restricted";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_TO_RESTRICTED_BUCKET = "bg_current_drain_high_threshold_to_restricted_bucket";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_INTERACTION_GRACE_PERIOD = "bg_current_drain_interaction_grace_period";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_LOCATION_MIN_DURATION = "bg_current_drain_location_min_duration";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_MEDIA_PLAYBACK_MIN_DURATION = "bg_current_drain_media_playback_min_duration";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_MONITOR_ENABLED = "bg_current_drain_monitor_enabled";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_POWER_COMPONENTS = "bg_current_drain_power_components";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_THRESHOLD_TO_BG_RESTRICTED = "bg_current_drain_threshold_to_bg_restricted";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_THRESHOLD_TO_RESTRICTED_BUCKET = "bg_current_drain_threshold_to_restricted_bucket";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_TYPES_TO_BG_RESTRICTED = "bg_current_drain_types_to_bg_restricted";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_TYPES_TO_RESTRICTED_BUCKET = "bg_current_drain_types_to_restricted_bucket";
        static final java.lang.String KEY_BG_CURRENT_DRAIN_WINDOW = "bg_current_drain_window";
        private static final int TIME_STAMP_INDEX_BG_RESTRICTED = 1;
        private static final int TIME_STAMP_INDEX_LAST = 2;
        private static final int TIME_STAMP_INDEX_RESTRICTED_BUCKET = 0;
        volatile android.os.BatteryConsumer.Dimensions[] mBatteryDimensions;
        private int mBatteryFullChargeMah;
        volatile boolean mBgCurrentDrainAutoRestrictAbusiveAppsEnabled;
        volatile float[] mBgCurrentDrainBgRestrictedThreshold;
        volatile int mBgCurrentDrainBgRestrictedTypes;
        volatile boolean mBgCurrentDrainDecoupleThresholds;
        volatile boolean mBgCurrentDrainEventDurationBasedThresholdEnabled;
        volatile int mBgCurrentDrainExemptedTypes;
        volatile boolean mBgCurrentDrainHighThresholdByBgLocation;
        volatile long mBgCurrentDrainInteractionGracePeriodMs;
        volatile long mBgCurrentDrainLocationMinDuration;
        volatile long mBgCurrentDrainMediaPlaybackMinDuration;
        volatile int mBgCurrentDrainPowerComponents;
        volatile float[] mBgCurrentDrainRestrictedBucketThreshold;
        volatile int mBgCurrentDrainRestrictedBucketTypes;
        volatile long mBgCurrentDrainWindowMs;
        final boolean mDefaultBgCurrentDrainAutoRestrictAbusiveAppsEnabled;
        final float mDefaultBgCurrentDrainBgRestrictedHighThreshold;
        final float mDefaultBgCurrentDrainBgRestrictedThreshold;
        final boolean mDefaultBgCurrentDrainEventDurationBasedThresholdEnabled;
        final int mDefaultBgCurrentDrainExemptedTypes;
        final boolean mDefaultBgCurrentDrainHighThresholdByBgLocation;
        final long mDefaultBgCurrentDrainInteractionGracePeriodMs;
        final long mDefaultBgCurrentDrainLocationMinDuration;
        final long mDefaultBgCurrentDrainMediaPlaybackMinDuration;
        final int mDefaultBgCurrentDrainPowerComponent;
        final float mDefaultBgCurrentDrainRestrictedBucket;
        final float mDefaultBgCurrentDrainRestrictedBucketHighThreshold;
        final int mDefaultBgCurrentDrainTypesToBgRestricted;
        final long mDefaultBgCurrentDrainWindowMs;
        final int mDefaultCurrentDrainTypesToRestrictedBucket;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseArray<android.util.Pair<long[], com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[]>> mHighBgBatteryPackages;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseLongArray mLastInteractionTime;

        @android.annotation.NonNull
        private final java.lang.Object mLock;

        AppBatteryPolicy(@android.annotation.NonNull com.android.server.am.BaseAppStateTracker.Injector injector, @android.annotation.NonNull com.android.server.am.AppBatteryTracker appBatteryTracker) {
            super(injector, appBatteryTracker, KEY_BG_CURRENT_DRAIN_MONITOR_ENABLED, appBatteryTracker.mContext.getResources().getBoolean(android.R.bool.config_bg_current_drain_monitor_enabled));
            this.mBgCurrentDrainRestrictedBucketThreshold = new float[2];
            this.mBgCurrentDrainBgRestrictedThreshold = new float[2];
            this.mHighBgBatteryPackages = new android.util.SparseArray<>();
            this.mLastInteractionTime = new android.util.SparseLongArray();
            this.mLock = appBatteryTracker.mLock;
            android.content.res.Resources resources = appBatteryTracker.mContext.getResources();
            float[] floatArray = getFloatArray(resources.obtainTypedArray(android.R.array.config_bg_current_drain_threshold_to_restricted_bucket));
            this.mDefaultBgCurrentDrainRestrictedBucket = android.app.ActivityManager.isLowRamDeviceStatic() ? floatArray[1] : floatArray[0];
            float[] floatArray2 = getFloatArray(resources.obtainTypedArray(android.R.array.config_bg_current_drain_threshold_to_bg_restricted));
            this.mDefaultBgCurrentDrainBgRestrictedThreshold = android.app.ActivityManager.isLowRamDeviceStatic() ? floatArray2[1] : floatArray2[0];
            this.mDefaultBgCurrentDrainWindowMs = resources.getInteger(android.R.integer.config_bg_current_drain_types_to_restricted_bucket) * 1000;
            this.mDefaultBgCurrentDrainInteractionGracePeriodMs = this.mDefaultBgCurrentDrainWindowMs;
            float[] floatArray3 = getFloatArray(resources.obtainTypedArray(android.R.array.config_bg_current_drain_high_threshold_to_restricted_bucket));
            this.mDefaultBgCurrentDrainRestrictedBucketHighThreshold = android.app.ActivityManager.isLowRamDeviceStatic() ? floatArray3[1] : floatArray3[0];
            float[] floatArray4 = getFloatArray(resources.obtainTypedArray(android.R.array.config_bg_current_drain_high_threshold_to_bg_restricted));
            this.mDefaultBgCurrentDrainBgRestrictedHighThreshold = android.app.ActivityManager.isLowRamDeviceStatic() ? floatArray4[1] : floatArray4[0];
            this.mDefaultBgCurrentDrainMediaPlaybackMinDuration = resources.getInteger(android.R.integer.config_bg_current_drain_location_min_duration) * 1000;
            this.mDefaultBgCurrentDrainLocationMinDuration = resources.getInteger(android.R.integer.config_bg_current_drain_exempted_types) * 1000;
            this.mDefaultBgCurrentDrainEventDurationBasedThresholdEnabled = resources.getBoolean(android.R.bool.config_bg_current_drain_event_duration_based_threshold_enabled);
            this.mDefaultBgCurrentDrainAutoRestrictAbusiveAppsEnabled = resources.getBoolean(android.R.bool.config_bg_current_drain_auto_restrict_abusive_apps);
            this.mDefaultCurrentDrainTypesToRestrictedBucket = resources.getInteger(android.R.integer.config_bg_current_drain_types_to_bg_restricted);
            this.mDefaultBgCurrentDrainTypesToBgRestricted = resources.getInteger(android.R.integer.config_bg_current_drain_power_components);
            this.mDefaultBgCurrentDrainPowerComponent = resources.getInteger(android.R.integer.config_bg_current_drain_media_playback_min_duration);
            this.mDefaultBgCurrentDrainExemptedTypes = resources.getInteger(android.R.integer.config_batterySaver_full_soundTriggerMode);
            this.mDefaultBgCurrentDrainHighThresholdByBgLocation = resources.getBoolean(android.R.bool.config_bg_current_drain_high_threshold_by_bg_location);
            this.mBgCurrentDrainRestrictedBucketThreshold[0] = this.mDefaultBgCurrentDrainRestrictedBucket;
            this.mBgCurrentDrainRestrictedBucketThreshold[1] = this.mDefaultBgCurrentDrainRestrictedBucketHighThreshold;
            this.mBgCurrentDrainBgRestrictedThreshold[0] = this.mDefaultBgCurrentDrainBgRestrictedThreshold;
            this.mBgCurrentDrainBgRestrictedThreshold[1] = this.mDefaultBgCurrentDrainBgRestrictedHighThreshold;
            this.mBgCurrentDrainWindowMs = this.mDefaultBgCurrentDrainWindowMs;
            this.mBgCurrentDrainInteractionGracePeriodMs = this.mDefaultBgCurrentDrainInteractionGracePeriodMs;
            this.mBgCurrentDrainMediaPlaybackMinDuration = this.mDefaultBgCurrentDrainMediaPlaybackMinDuration;
            this.mBgCurrentDrainLocationMinDuration = this.mDefaultBgCurrentDrainLocationMinDuration;
        }

        static float[] getFloatArray(android.content.res.TypedArray typedArray) {
            int length = typedArray.length();
            float[] fArr = new float[length];
            for (int i = 0; i < length; i++) {
                fArr[i] = typedArray.getFloat(i, Float.NaN);
            }
            typedArray.recycle();
            return fArr;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // com.android.server.am.BaseAppStatePolicy
        public void onPropertiesChanged(java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -1969771998:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_EVENT_DURATION_BASED_THRESHOLD_ENABLED)) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -1881058465:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_DECOUPLE_THRESHOLDS)) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -531697693:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_MEDIA_PLAYBACK_MIN_DURATION)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -523630921:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_POWER_COMPONENTS)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -494951532:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_TO_RESTRICTED_BUCKET)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 50590052:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_LOCATION_MIN_DURATION)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 101017819:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_TO_BG_RESTRICTED)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 129921652:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_AUTO_RESTRICT_ABUSIVE_APPS_ENABLED)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 399258641:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_BY_BG_LOCATION)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 517972572:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_INTERACTION_GRACE_PERIOD)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 655159543:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_TYPES_TO_RESTRICTED_BUCKET)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 718752671:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_EXEMPTED_TYPES)) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 1136582590:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_TYPES_TO_BG_RESTRICTED)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1362995852:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_THRESHOLD_TO_BG_RESTRICTED)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1869456581:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_THRESHOLD_TO_RESTRICTED_BUCKET)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1961864407:
                    if (str.equals(KEY_BG_CURRENT_DRAIN_WINDOW)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    updateCurrentDrainThreshold();
                    break;
                case '\b':
                    updateBgCurrentDrainAutoRestrictAbusiveAppsEnabled();
                    break;
                case '\t':
                    updateCurrentDrainWindow();
                    break;
                case '\n':
                    updateCurrentDrainInteractionGracePeriod();
                    break;
                case 11:
                    updateCurrentDrainMediaPlaybackMinDuration();
                    break;
                case '\f':
                    updateCurrentDrainLocationMinDuration();
                    break;
                case '\r':
                    updateCurrentDrainEventDurationBasedThresholdEnabled();
                    break;
                case 14:
                    updateCurrentDrainExemptedTypes();
                    break;
                case 15:
                    updateCurrentDrainDecoupleThresholds();
                    break;
                default:
                    super.onPropertiesChanged(str);
                    break;
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        void updateTrackerEnabled() {
            if (this.mBatteryFullChargeMah > 0) {
                super.updateTrackerEnabled();
            } else {
                this.mTrackerEnabled = false;
                onTrackerEnabled(false);
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onTrackerEnabled(boolean z) {
            ((com.android.server.am.AppBatteryTracker) this.mTracker).onCurrentDrainMonitorEnabled(z);
        }

        private void updateCurrentDrainThreshold() {
            this.mBgCurrentDrainRestrictedBucketThreshold[0] = android.provider.DeviceConfig.getFloat("activity_manager", KEY_BG_CURRENT_DRAIN_THRESHOLD_TO_RESTRICTED_BUCKET, this.mDefaultBgCurrentDrainRestrictedBucket);
            this.mBgCurrentDrainRestrictedBucketThreshold[1] = android.provider.DeviceConfig.getFloat("activity_manager", KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_TO_RESTRICTED_BUCKET, this.mDefaultBgCurrentDrainRestrictedBucketHighThreshold);
            this.mBgCurrentDrainBgRestrictedThreshold[0] = android.provider.DeviceConfig.getFloat("activity_manager", KEY_BG_CURRENT_DRAIN_THRESHOLD_TO_BG_RESTRICTED, this.mDefaultBgCurrentDrainBgRestrictedThreshold);
            this.mBgCurrentDrainBgRestrictedThreshold[1] = android.provider.DeviceConfig.getFloat("activity_manager", KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_TO_BG_RESTRICTED, this.mDefaultBgCurrentDrainBgRestrictedHighThreshold);
            this.mBgCurrentDrainRestrictedBucketTypes = android.provider.DeviceConfig.getInt("activity_manager", KEY_BG_CURRENT_DRAIN_TYPES_TO_RESTRICTED_BUCKET, this.mDefaultCurrentDrainTypesToRestrictedBucket);
            this.mBgCurrentDrainBgRestrictedTypes = android.provider.DeviceConfig.getInt("activity_manager", KEY_BG_CURRENT_DRAIN_TYPES_TO_BG_RESTRICTED, this.mDefaultBgCurrentDrainTypesToBgRestricted);
            this.mBgCurrentDrainPowerComponents = android.provider.DeviceConfig.getInt("activity_manager", KEY_BG_CURRENT_DRAIN_POWER_COMPONENTS, this.mDefaultBgCurrentDrainPowerComponent);
            if (this.mBgCurrentDrainPowerComponents == -1) {
                this.mBatteryDimensions = com.android.server.am.AppBatteryTracker.BatteryUsage.BATT_DIMENS;
            } else {
                this.mBatteryDimensions = new android.os.BatteryConsumer.Dimensions[5];
                for (int i = 0; i < 5; i++) {
                    this.mBatteryDimensions[i] = new android.os.BatteryConsumer.Dimensions(this.mBgCurrentDrainPowerComponents, i);
                }
            }
            this.mBgCurrentDrainHighThresholdByBgLocation = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_BY_BG_LOCATION, this.mDefaultBgCurrentDrainHighThresholdByBgLocation);
        }

        private void updateCurrentDrainWindow() {
            this.mBgCurrentDrainWindowMs = android.provider.DeviceConfig.getLong("activity_manager", KEY_BG_CURRENT_DRAIN_WINDOW, this.mDefaultBgCurrentDrainWindowMs);
        }

        private void updateCurrentDrainInteractionGracePeriod() {
            this.mBgCurrentDrainInteractionGracePeriodMs = android.provider.DeviceConfig.getLong("activity_manager", KEY_BG_CURRENT_DRAIN_INTERACTION_GRACE_PERIOD, this.mDefaultBgCurrentDrainInteractionGracePeriodMs);
        }

        private void updateCurrentDrainMediaPlaybackMinDuration() {
            this.mBgCurrentDrainMediaPlaybackMinDuration = android.provider.DeviceConfig.getLong("activity_manager", KEY_BG_CURRENT_DRAIN_MEDIA_PLAYBACK_MIN_DURATION, this.mDefaultBgCurrentDrainMediaPlaybackMinDuration);
        }

        private void updateCurrentDrainLocationMinDuration() {
            this.mBgCurrentDrainLocationMinDuration = android.provider.DeviceConfig.getLong("activity_manager", KEY_BG_CURRENT_DRAIN_LOCATION_MIN_DURATION, this.mDefaultBgCurrentDrainLocationMinDuration);
        }

        private void updateCurrentDrainEventDurationBasedThresholdEnabled() {
            this.mBgCurrentDrainEventDurationBasedThresholdEnabled = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_BG_CURRENT_DRAIN_EVENT_DURATION_BASED_THRESHOLD_ENABLED, this.mDefaultBgCurrentDrainEventDurationBasedThresholdEnabled);
        }

        private void updateCurrentDrainExemptedTypes() {
            this.mBgCurrentDrainExemptedTypes = android.provider.DeviceConfig.getInt("activity_manager", KEY_BG_CURRENT_DRAIN_EXEMPTED_TYPES, this.mDefaultBgCurrentDrainExemptedTypes);
        }

        private void updateCurrentDrainDecoupleThresholds() {
            this.mBgCurrentDrainDecoupleThresholds = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_BG_CURRENT_DRAIN_DECOUPLE_THRESHOLDS, true);
        }

        private void updateBgCurrentDrainAutoRestrictAbusiveAppsEnabled() {
            this.mBgCurrentDrainAutoRestrictAbusiveAppsEnabled = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_BG_CURRENT_DRAIN_AUTO_RESTRICT_ABUSIVE_APPS_ENABLED, this.mDefaultBgCurrentDrainAutoRestrictAbusiveAppsEnabled);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onSystemReady() {
            this.mBatteryFullChargeMah = this.mInjector.getBatteryManagerInternal().getBatteryFullCharge() / 1000;
            super.onSystemReady();
            updateCurrentDrainThreshold();
            updateCurrentDrainWindow();
            updateCurrentDrainInteractionGracePeriod();
            updateCurrentDrainMediaPlaybackMinDuration();
            updateCurrentDrainLocationMinDuration();
            updateCurrentDrainEventDurationBasedThresholdEnabled();
            updateCurrentDrainExemptedTypes();
            updateCurrentDrainDecoupleThresholds();
            updateBgCurrentDrainAutoRestrictAbusiveAppsEnabled();
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public int getProposedRestrictionLevel(java.lang.String str, int i, int i2) {
            int i3;
            boolean z = false;
            if (i2 <= 30) {
                return 0;
            }
            synchronized (this.mLock) {
                try {
                    android.util.Pair<long[], com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[]> pair = this.mHighBgBatteryPackages.get(i);
                    if (pair != null) {
                        long j = this.mLastInteractionTime.get(i, 0L);
                        long[] jArr = (long[]) pair.first;
                        boolean z2 = jArr[0] > j + this.mBgCurrentDrainInteractionGracePeriodMs;
                        if (((com.android.server.am.AppBatteryTracker) this.mTracker).mAppRestrictionController.isAutoRestrictAbusiveAppEnabled() && this.mBgCurrentDrainAutoRestrictAbusiveAppsEnabled) {
                            z = true;
                        }
                        if (z2 && z) {
                            i3 = 40;
                        } else {
                            i3 = 30;
                        }
                        if (i2 > 50) {
                            if (jArr[1] > 0) {
                                i3 = 50;
                            }
                            return i3;
                        }
                        if (i2 == 50) {
                            return i3;
                        }
                    }
                    return 30;
                } finally {
                }
            }
        }

        double[] calcPercentage(int i, double[] dArr, double[] dArr2) {
            com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage = i > 0 ? (com.android.server.am.AppBatteryTracker.BatteryUsage) ((com.android.server.am.AppBatteryTracker) this.mTracker).mDebugUidPercentages.get(i) : null;
            double[] percentage = batteryUsage != null ? batteryUsage.getPercentage() : null;
            for (int i2 = 0; i2 < dArr.length; i2++) {
                dArr2[i2] = percentage != null ? percentage[i2] : (dArr[i2] / this.mBatteryFullChargeMah) * 100.0d;
            }
            return dArr2;
        }

        private double sumPercentageOfTypes(double[] dArr, int i) {
            int highestOneBit = java.lang.Integer.highestOneBit(i);
            double d = 0.0d;
            while (highestOneBit != 0) {
                d += dArr[java.lang.Integer.numberOfTrailingZeros(highestOneBit)];
                i &= ~highestOneBit;
                highestOneBit = java.lang.Integer.highestOneBit(i);
            }
            return d;
        }

        private static java.lang.String batteryUsageTypesToString(int i) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("[");
            int highestOneBit = java.lang.Integer.highestOneBit(i);
            boolean z = false;
            while (highestOneBit != 0) {
                if (z) {
                    sb.append('|');
                }
                switch (highestOneBit) {
                    case 1:
                        sb.append("UNSPECIFIED");
                        break;
                    case 2:
                        sb.append("FOREGROUND");
                        break;
                    case 4:
                        sb.append("BACKGROUND");
                        break;
                    case 8:
                        sb.append("FOREGROUND_SERVICE");
                        break;
                    case 16:
                        sb.append("CACHED");
                        break;
                    default:
                        return "[UNKNOWN(" + java.lang.Integer.toHexString(i) + ")]";
                }
                i &= ~highestOneBit;
                highestOneBit = java.lang.Integer.highestOneBit(i);
                z = true;
            }
            sb.append("]");
            return sb.toString();
        }

        void handleUidBatteryUsage(int i, com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage) {
            boolean z;
            boolean z2;
            long[] jArr;
            com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[] immutableBatteryUsageArr;
            if (shouldExemptUid(i) == -1) {
                double sumPercentageOfTypes = sumPercentageOfTypes(immutableBatteryUsage.getPercentage(), this.mBgCurrentDrainRestrictedBucketTypes);
                double sumPercentageOfTypes2 = sumPercentageOfTypes(immutableBatteryUsage.getPercentage(), this.mBgCurrentDrainBgRestrictedTypes);
                synchronized (this.mLock) {
                    try {
                        int restrictionLevel = ((com.android.server.am.AppBatteryTracker) this.mTracker).mAppRestrictionController.getRestrictionLevel(i);
                        if (restrictionLevel >= 50) {
                            return;
                        }
                        long j = this.mLastInteractionTime.get(i, 0L);
                        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                        int currentDrainThresholdIndex = getCurrentDrainThresholdIndex(i, elapsedRealtime, this.mBgCurrentDrainWindowMs);
                        int indexOfKey = this.mHighBgBatteryPackages.indexOfKey(i);
                        boolean z3 = this.mBgCurrentDrainDecoupleThresholds;
                        double d = this.mBgCurrentDrainRestrictedBucketThreshold[currentDrainThresholdIndex];
                        double d2 = this.mBgCurrentDrainBgRestrictedThreshold[currentDrainThresholdIndex];
                        boolean z4 = false;
                        if (indexOfKey < 0) {
                            if (sumPercentageOfTypes < d) {
                                z2 = false;
                                jArr = null;
                                immutableBatteryUsageArr = null;
                            } else {
                                if (elapsedRealtime <= j + this.mBgCurrentDrainInteractionGracePeriodMs) {
                                    jArr = null;
                                    immutableBatteryUsageArr = null;
                                } else {
                                    jArr = new long[]{elapsedRealtime, 0};
                                    immutableBatteryUsageArr = new com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[2];
                                    immutableBatteryUsageArr[0] = immutableBatteryUsage;
                                    this.mHighBgBatteryPackages.put(i, android.util.Pair.create(jArr, immutableBatteryUsageArr));
                                    z4 = true;
                                }
                                z2 = true;
                            }
                            if (z3 && sumPercentageOfTypes2 >= d2) {
                                if (jArr == null) {
                                    jArr = new long[2];
                                    immutableBatteryUsageArr = new com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[2];
                                    this.mHighBgBatteryPackages.put(i, android.util.Pair.create(jArr, immutableBatteryUsageArr));
                                }
                                jArr[1] = elapsedRealtime;
                                immutableBatteryUsageArr[1] = immutableBatteryUsage;
                                z2 = true;
                                z4 = true;
                            }
                        } else {
                            android.util.Pair<long[], com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[]> valueAt = this.mHighBgBatteryPackages.valueAt(indexOfKey);
                            long[] jArr2 = (long[]) valueAt.first;
                            long j2 = jArr2[0];
                            if (sumPercentageOfTypes < d) {
                                z = false;
                                z2 = false;
                            } else {
                                if (elapsedRealtime <= j + this.mBgCurrentDrainInteractionGracePeriodMs) {
                                    z = false;
                                } else {
                                    if (j2 == 0) {
                                        jArr2[0] = elapsedRealtime;
                                        ((com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[]) valueAt.second)[0] = immutableBatteryUsage;
                                    }
                                    z = true;
                                }
                                z2 = true;
                            }
                            if (sumPercentageOfTypes2 >= d2) {
                                if (z3 || (restrictionLevel == 40 && elapsedRealtime > j2 + this.mBgCurrentDrainWindowMs)) {
                                    z4 = true;
                                }
                                if (z4) {
                                    jArr2[1] = elapsedRealtime;
                                    ((com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[]) valueAt.second)[1] = immutableBatteryUsage;
                                }
                                z2 = true;
                            } else {
                                jArr2[1] = 0;
                                ((com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[]) valueAt.second)[1] = null;
                                z4 = z;
                            }
                        }
                        if (z2 && z4) {
                            ((com.android.server.am.AppBatteryTracker) this.mTracker).mAppRestrictionController.refreshAppRestrictionLevelForUid(i, 1536, 2, true);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        private int getCurrentDrainThresholdIndex(int i, long j, long j2) {
            if (hasMediaPlayback(i, j, j2) || hasLocation(i, j, j2)) {
                return 1;
            }
            return 0;
        }

        private boolean hasMediaPlayback(int i, long j, long j2) {
            return this.mBgCurrentDrainEventDurationBasedThresholdEnabled && ((com.android.server.am.AppBatteryTracker) this.mTracker).mAppRestrictionController.getCompositeMediaPlaybackDurations(i, j, j2) >= this.mBgCurrentDrainMediaPlaybackMinDuration;
        }

        private boolean hasLocation(int i, long j, long j2) {
            if (!this.mBgCurrentDrainHighThresholdByBgLocation) {
                return false;
            }
            if (((com.android.server.am.AppBatteryTracker) this.mTracker).mContext.checkPermission("android.permission.ACCESS_BACKGROUND_LOCATION", -1, i) == 0) {
                return true;
            }
            if (this.mBgCurrentDrainEventDurationBasedThresholdEnabled) {
                return ((com.android.server.am.AppBatteryTracker) this.mTracker).mAppRestrictionController.getForegroundServiceTotalDurationsSince(i, java.lang.Math.max(0L, j - j2), j, 8) >= this.mBgCurrentDrainLocationMinDuration;
            }
            return false;
        }

        void onUserInteractionStarted(java.lang.String str, int i) {
            int indexOfKey;
            boolean z;
            synchronized (this.mLock) {
                try {
                    this.mLastInteractionTime.put(i, android.os.SystemClock.elapsedRealtime());
                    if (((com.android.server.am.AppBatteryTracker) this.mTracker).mAppRestrictionController.getRestrictionLevel(i, str) != 50 && (indexOfKey = this.mHighBgBatteryPackages.indexOfKey(i)) >= 0) {
                        this.mHighBgBatteryPackages.removeAt(indexOfKey);
                        z = true;
                    }
                    z = false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z) {
                ((com.android.server.am.AppBatteryTracker) this.mTracker).mAppRestrictionController.refreshAppRestrictionLevelForUid(i, 768, 3, true);
            }
        }

        void onBackgroundRestrictionChanged(int i, java.lang.String str, boolean z) {
            if (z) {
                return;
            }
            synchronized (this.mLock) {
                try {
                    android.util.Pair<long[], com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[]> pair = this.mHighBgBatteryPackages.get(i);
                    if (pair != null) {
                        ((long[]) pair.first)[1] = 0;
                        ((com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[]) pair.second)[1] = null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        void reset() {
            this.mHighBgBatteryPackages.clear();
            this.mLastInteractionTime.clear();
            ((com.android.server.am.AppBatteryTracker) this.mTracker).reset();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void onUserRemovedLocked(int i) {
            for (int size = this.mHighBgBatteryPackages.size() - 1; size >= 0; size--) {
                if (android.os.UserHandle.getUserId(this.mHighBgBatteryPackages.keyAt(size)) == i) {
                    this.mHighBgBatteryPackages.removeAt(size);
                }
            }
            for (int size2 = this.mLastInteractionTime.size() - 1; size2 >= 0; size2--) {
                if (android.os.UserHandle.getUserId(this.mLastInteractionTime.keyAt(size2)) == i) {
                    this.mLastInteractionTime.removeAt(size2);
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void onUidRemovedLocked(int i) {
            this.mHighBgBatteryPackages.remove(i);
            this.mLastInteractionTime.delete(i);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.println("APP BATTERY TRACKER POLICY SETTINGS:");
            java.lang.String str2 = "  " + str;
            super.dump(printWriter, str2);
            if (isEnabled()) {
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_THRESHOLD_TO_RESTRICTED_BUCKET);
                printWriter.print('=');
                char c = 0;
                printWriter.println(this.mBgCurrentDrainRestrictedBucketThreshold[0]);
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_TO_RESTRICTED_BUCKET);
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainRestrictedBucketThreshold[1]);
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_THRESHOLD_TO_BG_RESTRICTED);
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainBgRestrictedThreshold[0]);
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_TO_BG_RESTRICTED);
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainBgRestrictedThreshold[1]);
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_WINDOW);
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainWindowMs);
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_INTERACTION_GRACE_PERIOD);
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainInteractionGracePeriodMs);
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_MEDIA_PLAYBACK_MIN_DURATION);
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainMediaPlaybackMinDuration);
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_LOCATION_MIN_DURATION);
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainLocationMinDuration);
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_EVENT_DURATION_BASED_THRESHOLD_ENABLED);
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainEventDurationBasedThresholdEnabled);
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_AUTO_RESTRICT_ABUSIVE_APPS_ENABLED);
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainAutoRestrictAbusiveAppsEnabled);
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_TYPES_TO_RESTRICTED_BUCKET);
                printWriter.print('=');
                printWriter.println(batteryUsageTypesToString(this.mBgCurrentDrainRestrictedBucketTypes));
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_TYPES_TO_BG_RESTRICTED);
                printWriter.print('=');
                printWriter.println(batteryUsageTypesToString(this.mBgCurrentDrainBgRestrictedTypes));
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_POWER_COMPONENTS);
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainPowerComponents);
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_EXEMPTED_TYPES);
                printWriter.print('=');
                printWriter.println(com.android.server.am.BaseAppStateTracker.stateTypesToString(this.mBgCurrentDrainExemptedTypes));
                printWriter.print(str2);
                printWriter.print(KEY_BG_CURRENT_DRAIN_HIGH_THRESHOLD_BY_BG_LOCATION);
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainHighThresholdByBgLocation);
                printWriter.print(str2);
                printWriter.print("Full charge capacity=");
                printWriter.print(this.mBatteryFullChargeMah);
                printWriter.println(" mAh");
                printWriter.print(str2);
                printWriter.println("Excessive current drain detected:");
                synchronized (this.mLock) {
                    try {
                        int size = this.mHighBgBatteryPackages.size();
                        java.lang.String str3 = "  " + str2;
                        if (size > 0) {
                            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                            int i = 0;
                            while (i < size) {
                                int keyAt = this.mHighBgBatteryPackages.keyAt(i);
                                android.util.Pair<long[], com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[]> valueAt = this.mHighBgBatteryPackages.valueAt(i);
                                long[] jArr = (long[]) valueAt.first;
                                com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[] immutableBatteryUsageArr = (com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage[]) valueAt.second;
                                int currentDrainThresholdIndex = getCurrentDrainThresholdIndex(keyAt, elapsedRealtime, this.mBgCurrentDrainWindowMs);
                                printWriter.format("%s%s: (threshold=%4.2f%%/%4.2f%%) %s / %s\n", str3, android.os.UserHandle.formatUid(keyAt), java.lang.Float.valueOf(this.mBgCurrentDrainRestrictedBucketThreshold[currentDrainThresholdIndex]), java.lang.Float.valueOf(this.mBgCurrentDrainBgRestrictedThreshold[currentDrainThresholdIndex]), formatHighBgBatteryRecord(jArr[c], elapsedRealtime, immutableBatteryUsageArr[c]), formatHighBgBatteryRecord(jArr[1], elapsedRealtime, immutableBatteryUsageArr[1]));
                                i++;
                                str3 = str3;
                                c = 0;
                            }
                        } else {
                            printWriter.print(str3);
                            printWriter.println("(none)");
                        }
                    } finally {
                    }
                }
            }
        }

        private java.lang.String formatHighBgBatteryRecord(long j, long j2, com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage) {
            if (j > 0 && immutableBatteryUsage != null) {
                return java.lang.String.format("%s %s (%s)", android.util.TimeUtils.formatTime(j, j2), immutableBatteryUsage.toString(), immutableBatteryUsage.percentageToString());
            }
            return "0";
        }
    }
}
