package com.android.server.pm;

/* loaded from: classes2.dex */
public class SnapshotStatistics {
    private static final int REBUILD_LATENCY_BUCKET_LESS_THAN_100_MILLIS = 100;
    private static final int REBUILD_LATENCY_BUCKET_LESS_THAN_10_MILLIS = 10;
    private static final int REBUILD_LATENCY_BUCKET_LESS_THAN_1_MILLIS = 1;
    private static final int REBUILD_LATENCY_BUCKET_LESS_THAN_20_MILLIS = 20;
    private static final int REBUILD_LATENCY_BUCKET_LESS_THAN_2_MILLIS = 2;
    private static final int REBUILD_LATENCY_BUCKET_LESS_THAN_50_MILLIS = 50;
    private static final int REBUILD_LATENCY_BUCKET_LESS_THAN_5_MILLIS = 5;
    private static final int REUSE_COUNT_BUCKET_LESS_THAN_1 = 1;
    private static final int REUSE_COUNT_BUCKET_LESS_THAN_10 = 10;
    private static final int REUSE_COUNT_BUCKET_LESS_THAN_100 = 100;
    private static final int REUSE_COUNT_BUCKET_LESS_THAN_1000 = 1000;
    private static final int REUSE_COUNT_BUCKET_LESS_THAN_10000 = 10000;
    public static final int SNAPSHOT_BIG_BUILD_TIME_US = 10000;
    public static final int SNAPSHOT_BUILD_REPORT_LIMIT = 10;
    private static final long SNAPSHOT_LOG_INTERVAL_US = java.util.concurrent.TimeUnit.DAYS.toMicros(1);
    public static final int SNAPSHOT_REPORTABLE_BUILD_TIME_US = 30000;
    public static final int SNAPSHOT_SHORT_LIFETIME = 5;
    public static final int SNAPSHOT_TICK_INTERVAL_MS = 60000;
    private static final int US_IN_MS = 1000;
    private android.os.Handler mHandler;
    private long mLastLogTimeUs;
    private final com.android.server.pm.SnapshotStatistics.Stats[] mLong;
    private int mPackageCount;
    private final com.android.server.pm.SnapshotStatistics.Stats[] mShort;
    private final java.lang.Object mLock = new java.lang.Object();
    private int mEventsReported = 0;
    private final com.android.server.pm.SnapshotStatistics.BinMap mTimeBins = new com.android.server.pm.SnapshotStatistics.BinMap(new int[]{1, 2, 5, 10, 20, 50, 100});
    private final com.android.server.pm.SnapshotStatistics.BinMap mUseBins = new com.android.server.pm.SnapshotStatistics.BinMap(new int[]{1, 10, 100, 1000, 10000});

    private int usToMs(int i) {
        return i / 1000;
    }

    private static class BinMap {
        private final int mCount;
        private final int mMaxBin;
        private final int[] mUserKey;

        BinMap(int[] iArr) {
            this.mUserKey = java.util.Arrays.copyOf(iArr, iArr.length);
            this.mCount = this.mUserKey.length + 1;
            this.mMaxBin = this.mUserKey[this.mUserKey.length - 1] + 1;
        }

        public int getBin(int i) {
            if (i >= 0 && i < this.mMaxBin) {
                for (int i2 = 0; i2 < this.mUserKey.length; i2++) {
                    if (i <= this.mUserKey[i2]) {
                        return i2;
                    }
                }
                return 0;
            }
            if (i < this.mMaxBin) {
                return 0;
            }
            return this.mUserKey.length;
        }

        public int count() {
            return this.mCount;
        }

        public int[] userKeys() {
            return this.mUserKey;
        }
    }

    public class Stats {
        public int mBigBuilds;
        public int mMaxBuildTimeUs;
        public int mMaxUsedCount;
        public int mShortLived;
        public long mStartTimeUs;
        public long mStopTimeUs;
        public final int[] mTimes;
        public int mTotalBuilds;
        public long mTotalTimeUs;
        public int mTotalUsed;
        public final int[] mUsed;

        /* JADX INFO: Access modifiers changed from: private */
        public void rebuild(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            this.mTotalBuilds++;
            int[] iArr = this.mTimes;
            iArr[i3] = iArr[i3] + 1;
            if (i2 >= 0) {
                this.mTotalUsed += i2;
                int[] iArr2 = this.mUsed;
                iArr2[i4] = iArr2[i4] + 1;
            }
            this.mTotalTimeUs += i;
            if (z) {
                this.mBigBuilds++;
            }
            if (z2) {
                this.mShortLived++;
            }
            if (this.mMaxBuildTimeUs < i) {
                this.mMaxBuildTimeUs = i;
            }
            if (this.mMaxUsedCount < i2) {
                this.mMaxUsedCount = i2;
            }
        }

        private Stats(long j) {
            this.mStartTimeUs = 0L;
            this.mStopTimeUs = 0L;
            this.mTotalBuilds = 0;
            this.mTotalUsed = 0;
            this.mBigBuilds = 0;
            this.mShortLived = 0;
            this.mTotalTimeUs = 0L;
            this.mMaxBuildTimeUs = 0;
            this.mMaxUsedCount = 0;
            this.mStartTimeUs = j;
            this.mTimes = new int[com.android.server.pm.SnapshotStatistics.this.mTimeBins.count()];
            this.mUsed = new int[com.android.server.pm.SnapshotStatistics.this.mUseBins.count()];
        }

        private Stats(com.android.server.pm.SnapshotStatistics.Stats stats) {
            this.mStartTimeUs = 0L;
            this.mStopTimeUs = 0L;
            this.mTotalBuilds = 0;
            this.mTotalUsed = 0;
            this.mBigBuilds = 0;
            this.mShortLived = 0;
            this.mTotalTimeUs = 0L;
            this.mMaxBuildTimeUs = 0;
            this.mMaxUsedCount = 0;
            this.mStartTimeUs = stats.mStartTimeUs;
            this.mStopTimeUs = stats.mStopTimeUs;
            this.mTimes = java.util.Arrays.copyOf(stats.mTimes, stats.mTimes.length);
            this.mUsed = java.util.Arrays.copyOf(stats.mUsed, stats.mUsed.length);
            this.mTotalBuilds = stats.mTotalBuilds;
            this.mTotalUsed = stats.mTotalUsed;
            this.mBigBuilds = stats.mBigBuilds;
            this.mShortLived = stats.mShortLived;
            this.mTotalTimeUs = stats.mTotalTimeUs;
            this.mMaxBuildTimeUs = stats.mMaxBuildTimeUs;
            this.mMaxUsedCount = stats.mMaxUsedCount;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void complete(long j) {
            this.mStopTimeUs = j;
        }

        private java.lang.String durationToString(long j) {
            int i = (int) (j / 1000000);
            int i2 = i / 60;
            int i3 = i % 60;
            int i4 = i2 / 60;
            int i5 = i2 % 60;
            int i6 = i4 / 24;
            int i7 = i4 % 24;
            if (i6 != 0) {
                return android.text.TextUtils.formatSimple("%2d:%02d:%02d:%02d", new java.lang.Object[]{java.lang.Integer.valueOf(i6), java.lang.Integer.valueOf(i7), java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i3)});
            }
            return i7 != 0 ? android.text.TextUtils.formatSimple("%2s %02d:%02d:%02d", new java.lang.Object[]{"", java.lang.Integer.valueOf(i7), java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i3)}) : android.text.TextUtils.formatSimple("%2s %2s %2d:%02d", new java.lang.Object[]{"", "", java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i3)});
        }

        private void dumpPrefix(java.io.PrintWriter printWriter, java.lang.String str, long j, boolean z, java.lang.String str2) {
            printWriter.print(str + " ");
            if (z) {
                printWriter.format(java.util.Locale.US, "%-23s", str2);
                return;
            }
            printWriter.format(java.util.Locale.US, "%11s", durationToString(j - this.mStartTimeUs));
            if (this.mStopTimeUs != 0) {
                printWriter.format(java.util.Locale.US, " %11s", durationToString(j - this.mStopTimeUs));
            } else {
                printWriter.format(java.util.Locale.US, " %11s", "now");
            }
        }

        private void dumpStats(java.io.PrintWriter printWriter, java.lang.String str, long j, boolean z) {
            dumpPrefix(printWriter, str, j, z, "Summary stats");
            if (z) {
                printWriter.format(java.util.Locale.US, "  %10s  %10s  %10s  %10s  %10s  %10s", "TotBlds", "TotUsed", "BigBlds", "ShortLvd", "TotTime", "MaxTime");
            } else {
                printWriter.format(java.util.Locale.US, "  %10d  %10d  %10d  %10d  %10d  %10d", java.lang.Integer.valueOf(this.mTotalBuilds), java.lang.Integer.valueOf(this.mTotalUsed), java.lang.Integer.valueOf(this.mBigBuilds), java.lang.Integer.valueOf(this.mShortLived), java.lang.Long.valueOf(this.mTotalTimeUs / 1000), java.lang.Integer.valueOf(this.mMaxBuildTimeUs / 1000));
            }
            printWriter.println();
        }

        private void dumpTimes(java.io.PrintWriter printWriter, java.lang.String str, long j, boolean z) {
            dumpPrefix(printWriter, str, j, z, "Build times");
            int i = 0;
            if (z) {
                int[] userKeys = com.android.server.pm.SnapshotStatistics.this.mTimeBins.userKeys();
                while (i < userKeys.length) {
                    printWriter.format(java.util.Locale.US, "  %10s", android.text.TextUtils.formatSimple("<= %dms", new java.lang.Object[]{java.lang.Integer.valueOf(userKeys[i])}));
                    i++;
                }
                printWriter.format(java.util.Locale.US, "  %10s", android.text.TextUtils.formatSimple("> %dms", new java.lang.Object[]{java.lang.Integer.valueOf(userKeys[userKeys.length - 1])}));
            } else {
                while (i < this.mTimes.length) {
                    printWriter.format(java.util.Locale.US, "  %10d", java.lang.Integer.valueOf(this.mTimes[i]));
                    i++;
                }
            }
            printWriter.println();
        }

        private void dumpUsage(java.io.PrintWriter printWriter, java.lang.String str, long j, boolean z) {
            dumpPrefix(printWriter, str, j, z, "Use counters");
            int i = 0;
            if (z) {
                int[] userKeys = com.android.server.pm.SnapshotStatistics.this.mUseBins.userKeys();
                while (i < userKeys.length) {
                    printWriter.format(java.util.Locale.US, "  %10s", android.text.TextUtils.formatSimple("<= %d", new java.lang.Object[]{java.lang.Integer.valueOf(userKeys[i])}));
                    i++;
                }
                printWriter.format(java.util.Locale.US, "  %10s", android.text.TextUtils.formatSimple("> %d", new java.lang.Object[]{java.lang.Integer.valueOf(userKeys[userKeys.length - 1])}));
            } else {
                while (i < this.mUsed.length) {
                    printWriter.format(java.util.Locale.US, "  %10d", java.lang.Integer.valueOf(this.mUsed[i]));
                    i++;
                }
            }
            printWriter.println();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(java.io.PrintWriter printWriter, java.lang.String str, long j, boolean z, java.lang.String str2) {
            if (str2.equals("stats")) {
                dumpStats(printWriter, str, j, z);
                return;
            }
            if (str2.equals("times")) {
                dumpTimes(printWriter, str, j, z);
            } else {
                if (str2.equals("usage")) {
                    dumpUsage(printWriter, str, j, z);
                    return;
                }
                throw new java.lang.IllegalArgumentException("unrecognized choice: " + str2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void logSnapshotStatistics(int i) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PACKAGE_MANAGER_SNAPSHOT_REPORTED, this.mTimes, this.mUsed, this.mMaxBuildTimeUs, this.mMaxUsedCount, this.mTotalBuilds == 0 ? 0L : this.mTotalTimeUs / this.mTotalBuilds, this.mTotalBuilds == 0 ? 0 : this.mTotalUsed / this.mTotalBuilds, i);
        }
    }

    public SnapshotStatistics() {
        this.mHandler = null;
        long currentTimeMicro = android.os.SystemClock.currentTimeMicro();
        this.mLong = new com.android.server.pm.SnapshotStatistics.Stats[2];
        this.mLong[0] = new com.android.server.pm.SnapshotStatistics.Stats(currentTimeMicro);
        this.mShort = new com.android.server.pm.SnapshotStatistics.Stats[10];
        this.mShort[0] = new com.android.server.pm.SnapshotStatistics.Stats(currentTimeMicro);
        this.mLastLogTimeUs = currentTimeMicro;
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: com.android.server.pm.SnapshotStatistics.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                com.android.server.pm.SnapshotStatistics.this.handleMessage(message);
            }
        };
        scheduleTick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessage(@android.annotation.Nullable android.os.Message message) {
        tick();
        scheduleTick();
    }

    private void scheduleTick() {
        this.mHandler.sendEmptyMessageDelayed(0, 60000L);
    }

    public final void rebuild(long j, long j2, int i, int i2) {
        boolean z;
        int i3 = (int) (j2 - j);
        synchronized (this.mLock) {
            try {
                this.mPackageCount = i2;
                int bin = this.mTimeBins.getBin(i3 / 1000);
                int bin2 = this.mUseBins.getBin(i);
                z = true;
                boolean z2 = i3 >= 10000;
                boolean z3 = i <= 5;
                this.mShort[0].rebuild(i3, i, bin, bin2, z2, z3);
                this.mLong[0].rebuild(i3, i, bin, bin2, z2, z3);
                if (i3 >= 30000) {
                    int i4 = this.mEventsReported;
                    this.mEventsReported = i4 + 1;
                    if (i4 < 10) {
                    }
                }
                z = false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            com.android.server.EventLogTags.writePmSnapshotRebuild(i3 / 1000, i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void shift(com.android.server.pm.SnapshotStatistics.Stats[] statsArr, long j) {
        statsArr[0].complete(j);
        for (int length = statsArr.length - 1; length > 0; length--) {
            statsArr[length] = statsArr[length - 1];
        }
        statsArr[0] = new com.android.server.pm.SnapshotStatistics.Stats(j);
    }

    private void tick() {
        synchronized (this.mLock) {
            try {
                long currentTimeMicro = android.os.SystemClock.currentTimeMicro();
                if (currentTimeMicro - this.mLastLogTimeUs > SNAPSHOT_LOG_INTERVAL_US) {
                    shift(this.mLong, currentTimeMicro);
                    this.mLastLogTimeUs = currentTimeMicro;
                    this.mLong[this.mLong.length - 1].logSnapshotStatistics(this.mPackageCount);
                }
                shift(this.mShort, currentTimeMicro);
                this.mEventsReported = 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dump(java.io.PrintWriter printWriter, java.lang.String str, long j, com.android.server.pm.SnapshotStatistics.Stats[] statsArr, com.android.server.pm.SnapshotStatistics.Stats[] statsArr2, java.lang.String str2) {
        statsArr[0].dump(printWriter, str, j, true, str2);
        for (int i = 0; i < statsArr2.length; i++) {
            if (statsArr2[i] != null) {
                statsArr2[i].dump(printWriter, str, j, false, str2);
            }
        }
        for (int i2 = 0; i2 < statsArr.length; i2++) {
            if (statsArr[i2] != null) {
                statsArr[i2].dump(printWriter, str, j, false, str2);
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, long j, int i, boolean z) {
        com.android.server.pm.SnapshotStatistics.Stats[] statsArr;
        com.android.server.pm.SnapshotStatistics.Stats[] statsArr2;
        synchronized (this.mLock) {
            statsArr = (com.android.server.pm.SnapshotStatistics.Stats[]) java.util.Arrays.copyOf(this.mLong, this.mLong.length);
            statsArr[0] = new com.android.server.pm.SnapshotStatistics.Stats(statsArr[0]);
            statsArr2 = (com.android.server.pm.SnapshotStatistics.Stats[]) java.util.Arrays.copyOf(this.mShort, this.mShort.length);
            statsArr2[0] = new com.android.server.pm.SnapshotStatistics.Stats(statsArr2[0]);
        }
        printWriter.format(java.util.Locale.US, "%s Unrecorded-hits: %d", str, java.lang.Integer.valueOf(i));
        printWriter.println();
        dump(printWriter, str, j, statsArr, statsArr2, "stats");
        if (z) {
            return;
        }
        printWriter.println();
        dump(printWriter, str, j, statsArr, statsArr2, "times");
        printWriter.println();
        dump(printWriter, str, j, statsArr, statsArr2, "usage");
    }
}
