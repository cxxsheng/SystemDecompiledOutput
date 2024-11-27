package com.android.server.am;

/* loaded from: classes.dex */
public class CacheOomRanker {

    @com.android.internal.annotations.VisibleForTesting
    static final float DEFAULT_OOM_RE_RANKING_LRU_WEIGHT = 0.35f;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_OOM_RE_RANKING_NUMBER_TO_RE_RANK = 8;

    @com.android.internal.annotations.VisibleForTesting
    static final float DEFAULT_OOM_RE_RANKING_RSS_WEIGHT = 0.15f;

    @com.android.internal.annotations.VisibleForTesting
    static final float DEFAULT_OOM_RE_RANKING_USES_WEIGHT = 0.5f;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_PRESERVE_TOP_N_APPS = 3;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_RSS_UPDATE_RATE_MS = 10000;

    @com.android.internal.annotations.VisibleForTesting
    static final boolean DEFAULT_USE_FREQUENT_RSS = true;
    private static final boolean DEFAULT_USE_OOM_RE_RANKING = false;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_OOM_RE_RANKING_LRU_WEIGHT = "oom_re_ranking_lru_weight";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_OOM_RE_RANKING_NUMBER_TO_RE_RANK = "oom_re_ranking_number_to_re_rank";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_OOM_RE_RANKING_PRESERVE_TOP_N_APPS = "oom_re_ranking_preserve_top_n_apps";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_OOM_RE_RANKING_RSS_UPDATE_RATE_MS = "oom_re_ranking_rss_update_rate_ms";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_OOM_RE_RANKING_RSS_WEIGHT = "oom_re_ranking_rss_weight";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_OOM_RE_RANKING_USES_WEIGHT = "oom_re_ranking_uses_weight";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_OOM_RE_RANKING_USE_FREQUENT_RSS = "oom_re_ranking_rss_use_frequent_rss";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_USE_OOM_RE_RANKING = "use_oom_re_ranking";

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    private int[] mLruPositions;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    float mLruWeight;
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mOnFlagsChangedListener;
    private final java.lang.Object mPhenotypeFlagLock;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    int mPreserveTopNApps;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    private final com.android.server.am.CacheOomRanker.ProcessDependencies mProcessDependencies;
    private final java.lang.Object mProfilerLock;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    long mRssUpdateRateMs;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    float mRssWeight;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    private com.android.server.am.CacheOomRanker.RankedProcessRecord[] mScoredProcessRecords;
    private final com.android.server.am.ActivityManagerService mService;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean mUseFrequentRss;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    private boolean mUseOomReRanking;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    float mUsesWeight;
    private static final java.util.Comparator<com.android.server.am.CacheOomRanker.RankedProcessRecord> SCORED_PROCESS_RECORD_COMPARATOR = new com.android.server.am.CacheOomRanker.ScoreComparator();
    private static final java.util.Comparator<com.android.server.am.CacheOomRanker.RankedProcessRecord> CACHE_USE_COMPARATOR = new com.android.server.am.CacheOomRanker.CacheUseComparator();
    private static final java.util.Comparator<com.android.server.am.CacheOomRanker.RankedProcessRecord> RSS_COMPARATOR = new com.android.server.am.CacheOomRanker.RssComparator();
    private static final java.util.Comparator<com.android.server.am.CacheOomRanker.RankedProcessRecord> LAST_RSS_COMPARATOR = new com.android.server.am.CacheOomRanker.LastRssComparator();
    private static final java.util.Comparator<com.android.server.am.CacheOomRanker.RankedProcessRecord> LAST_ACTIVITY_TIME_COMPARATOR = new com.android.server.am.CacheOomRanker.LastActivityTimeComparator();

    interface ProcessDependencies {
        long[] getRss(int i);
    }

    private static class RankedProcessRecord {
        public com.android.server.am.ProcessRecord proc;
        public float score;

        private RankedProcessRecord() {
        }
    }

    CacheOomRanker(com.android.server.am.ActivityManagerService activityManagerService) {
        this(activityManagerService, new com.android.server.am.CacheOomRanker.ProcessDependenciesImpl());
    }

    @com.android.internal.annotations.VisibleForTesting
    CacheOomRanker(com.android.server.am.ActivityManagerService activityManagerService, com.android.server.am.CacheOomRanker.ProcessDependencies processDependencies) {
        this.mPhenotypeFlagLock = new java.lang.Object();
        this.mUseOomReRanking = false;
        this.mPreserveTopNApps = 3;
        this.mUseFrequentRss = true;
        this.mRssUpdateRateMs = 10000L;
        this.mLruWeight = DEFAULT_OOM_RE_RANKING_LRU_WEIGHT;
        this.mUsesWeight = 0.5f;
        this.mRssWeight = DEFAULT_OOM_RE_RANKING_RSS_WEIGHT;
        this.mOnFlagsChangedListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.am.CacheOomRanker.1
            public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                synchronized (com.android.server.am.CacheOomRanker.this.mPhenotypeFlagLock) {
                    try {
                        for (java.lang.String str : properties.getKeyset()) {
                            if (com.android.server.am.CacheOomRanker.KEY_USE_OOM_RE_RANKING.equals(str)) {
                                com.android.server.am.CacheOomRanker.this.updateUseOomReranking();
                            } else if (com.android.server.am.CacheOomRanker.KEY_OOM_RE_RANKING_NUMBER_TO_RE_RANK.equals(str)) {
                                com.android.server.am.CacheOomRanker.this.updateNumberToReRank();
                            } else if (com.android.server.am.CacheOomRanker.KEY_OOM_RE_RANKING_PRESERVE_TOP_N_APPS.equals(str)) {
                                com.android.server.am.CacheOomRanker.this.updatePreserveTopNApps();
                            } else if (com.android.server.am.CacheOomRanker.KEY_OOM_RE_RANKING_USE_FREQUENT_RSS.equals(str)) {
                                com.android.server.am.CacheOomRanker.this.updateUseFrequentRss();
                            } else if (com.android.server.am.CacheOomRanker.KEY_OOM_RE_RANKING_RSS_UPDATE_RATE_MS.equals(str)) {
                                com.android.server.am.CacheOomRanker.this.updateRssUpdateRateMs();
                            } else if (com.android.server.am.CacheOomRanker.KEY_OOM_RE_RANKING_LRU_WEIGHT.equals(str)) {
                                com.android.server.am.CacheOomRanker.this.updateLruWeight();
                            } else if (com.android.server.am.CacheOomRanker.KEY_OOM_RE_RANKING_USES_WEIGHT.equals(str)) {
                                com.android.server.am.CacheOomRanker.this.updateUsesWeight();
                            } else if (com.android.server.am.CacheOomRanker.KEY_OOM_RE_RANKING_RSS_WEIGHT.equals(str)) {
                                com.android.server.am.CacheOomRanker.this.updateRssWeight();
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.mProfilerLock = activityManagerService.mAppProfiler.mProfilerLock;
        this.mProcessDependencies = processDependencies;
    }

    public void init(java.util.concurrent.Executor executor) {
        android.provider.DeviceConfig.addOnPropertiesChangedListener("activity_manager", executor, this.mOnFlagsChangedListener);
        synchronized (this.mPhenotypeFlagLock) {
            updateUseOomReranking();
            updateNumberToReRank();
            updateLruWeight();
            updateUsesWeight();
            updateRssWeight();
        }
    }

    public boolean useOomReranking() {
        boolean z;
        synchronized (this.mPhenotypeFlagLock) {
            z = this.mUseOomReRanking;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateUseOomReranking() {
        this.mUseOomReRanking = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_USE_OOM_RE_RANKING, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateNumberToReRank() {
        int numberToReRank = getNumberToReRank();
        int i = android.provider.DeviceConfig.getInt("activity_manager", KEY_OOM_RE_RANKING_NUMBER_TO_RE_RANK, 8);
        if (numberToReRank != i) {
            this.mScoredProcessRecords = new com.android.server.am.CacheOomRanker.RankedProcessRecord[i];
            for (int i2 = 0; i2 < this.mScoredProcessRecords.length; i2++) {
                this.mScoredProcessRecords[i2] = new com.android.server.am.CacheOomRanker.RankedProcessRecord();
            }
            this.mLruPositions = new int[i];
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    int getNumberToReRank() {
        if (this.mScoredProcessRecords == null) {
            return 0;
        }
        return this.mScoredProcessRecords.length;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updatePreserveTopNApps() {
        int i = 3;
        int i2 = android.provider.DeviceConfig.getInt("activity_manager", KEY_OOM_RE_RANKING_PRESERVE_TOP_N_APPS, 3);
        if (i2 >= 0) {
            i = i2;
        } else {
            android.util.Slog.w("OomAdjuster", "Found negative value for preserveTopNApps, setting to default: " + i2);
        }
        this.mPreserveTopNApps = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateRssUpdateRateMs() {
        this.mRssUpdateRateMs = android.provider.DeviceConfig.getLong("activity_manager", KEY_OOM_RE_RANKING_RSS_UPDATE_RATE_MS, 10000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateUseFrequentRss() {
        this.mUseFrequentRss = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_OOM_RE_RANKING_USE_FREQUENT_RSS, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateLruWeight() {
        this.mLruWeight = android.provider.DeviceConfig.getFloat("activity_manager", KEY_OOM_RE_RANKING_LRU_WEIGHT, DEFAULT_OOM_RE_RANKING_LRU_WEIGHT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateUsesWeight() {
        this.mUsesWeight = android.provider.DeviceConfig.getFloat("activity_manager", KEY_OOM_RE_RANKING_USES_WEIGHT, 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateRssWeight() {
        this.mRssWeight = android.provider.DeviceConfig.getFloat("activity_manager", KEY_OOM_RE_RANKING_RSS_WEIGHT, DEFAULT_OOM_RE_RANKING_RSS_WEIGHT);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void reRankLruCachedAppsLSP(java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList, int i) {
        float f;
        float f2;
        float f3;
        int i2;
        boolean z;
        long j;
        int[] iArr;
        com.android.server.am.CacheOomRanker.RankedProcessRecord[] rankedProcessRecordArr;
        int i3;
        long j2;
        com.android.server.am.CacheOomRanker cacheOomRanker = this;
        synchronized (cacheOomRanker.mPhenotypeFlagLock) {
            f = cacheOomRanker.mLruWeight;
            f2 = cacheOomRanker.mUsesWeight;
            f3 = cacheOomRanker.mRssWeight;
            i2 = cacheOomRanker.mPreserveTopNApps;
            z = cacheOomRanker.mUseFrequentRss;
            j = cacheOomRanker.mRssUpdateRateMs;
            iArr = cacheOomRanker.mLruPositions;
            rankedProcessRecordArr = cacheOomRanker.mScoredProcessRecords;
        }
        if (iArr == null || rankedProcessRecordArr == null) {
            return;
        }
        int i4 = 0;
        int i5 = 0;
        while (i4 < i && i5 < rankedProcessRecordArr.length) {
            com.android.server.am.ProcessRecord processRecord = arrayList.get(i4);
            if (appCanBeReRanked(processRecord)) {
                rankedProcessRecordArr[i5].proc = processRecord;
                rankedProcessRecordArr[i5].score = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                iArr[i5] = i4;
                i5++;
            }
            i4++;
        }
        int i6 = 0;
        while (i4 < i && i6 < i2) {
            if (appCanBeReRanked(arrayList.get(i4))) {
                i6++;
            }
            i4++;
        }
        if (i6 < i2 && (i5 = i5 - (i2 - i6)) < 0) {
            i5 = 0;
        }
        if (z) {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            int i7 = 0;
            while (i7 < i5) {
                com.android.server.am.CacheOomRanker.RankedProcessRecord rankedProcessRecord = rankedProcessRecordArr[i7];
                long cacheOomRankerRssTimeMs = elapsedRealtime - rankedProcessRecord.proc.mState.getCacheOomRankerRssTimeMs();
                if (rankedProcessRecord.proc.mState.getCacheOomRankerRss() != 0 && cacheOomRankerRssTimeMs < j) {
                    j2 = j;
                } else {
                    j2 = j;
                    long[] rss = cacheOomRanker.mProcessDependencies.getRss(rankedProcessRecord.proc.getPid());
                    if (rss == null || rss.length == 0) {
                        android.util.Slog.e("OomAdjuster", "Process.getRss returned bad value, not re-ranking: " + java.util.Arrays.toString(rss));
                        return;
                    }
                    rankedProcessRecord.proc.mState.setCacheOomRankerRss(rss[0], elapsedRealtime);
                    rankedProcessRecord.proc.mProfile.setLastRss(rss[0]);
                }
                i7++;
                cacheOomRanker = this;
                j = j2;
            }
        }
        if (f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            java.util.Arrays.sort(rankedProcessRecordArr, 0, i5, LAST_ACTIVITY_TIME_COMPARATOR);
            addToScore(rankedProcessRecordArr, f);
        }
        if (f3 > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            if (z) {
                java.util.Arrays.sort(rankedProcessRecordArr, 0, i5, RSS_COMPARATOR);
            } else {
                synchronized (this.mService.mAppProfiler.mProfilerLock) {
                    java.util.Arrays.sort(rankedProcessRecordArr, 0, i5, LAST_RSS_COMPARATOR);
                }
            }
            addToScore(rankedProcessRecordArr, f3);
        }
        if (f2 <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            i3 = 0;
        } else {
            i3 = 0;
            java.util.Arrays.sort(rankedProcessRecordArr, 0, i5, CACHE_USE_COMPARATOR);
            addToScore(rankedProcessRecordArr, f2);
        }
        java.util.Arrays.sort(rankedProcessRecordArr, i3, i5, SCORED_PROCESS_RECORD_COMPARATOR);
        for (int i8 = i3; i8 < i5; i8++) {
            arrayList.set(iArr[i8], rankedProcessRecordArr[i8].proc);
            rankedProcessRecordArr[i8].proc = null;
        }
    }

    private static boolean appCanBeReRanked(com.android.server.am.ProcessRecord processRecord) {
        return (processRecord.isKilledByAm() || processRecord.getThread() == null || processRecord.mState.getCurAdj() < 1001) ? false : true;
    }

    private static void addToScore(com.android.server.am.CacheOomRanker.RankedProcessRecord[] rankedProcessRecordArr, float f) {
        for (int i = 1; i < rankedProcessRecordArr.length; i++) {
            rankedProcessRecordArr[i].score += i * f;
        }
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("CacheOomRanker settings");
        synchronized (this.mPhenotypeFlagLock) {
            printWriter.println("  use_oom_re_ranking=" + this.mUseOomReRanking);
            printWriter.println("  oom_re_ranking_number_to_re_rank=" + getNumberToReRank());
            printWriter.println("  oom_re_ranking_lru_weight=" + this.mLruWeight);
            printWriter.println("  oom_re_ranking_uses_weight=" + this.mUsesWeight);
            printWriter.println("  oom_re_ranking_rss_weight=" + this.mRssWeight);
        }
    }

    private static class ScoreComparator implements java.util.Comparator<com.android.server.am.CacheOomRanker.RankedProcessRecord> {
        private ScoreComparator() {
        }

        @Override // java.util.Comparator
        public int compare(com.android.server.am.CacheOomRanker.RankedProcessRecord rankedProcessRecord, com.android.server.am.CacheOomRanker.RankedProcessRecord rankedProcessRecord2) {
            return java.lang.Float.compare(rankedProcessRecord.score, rankedProcessRecord2.score);
        }
    }

    private static class LastActivityTimeComparator implements java.util.Comparator<com.android.server.am.CacheOomRanker.RankedProcessRecord> {
        private LastActivityTimeComparator() {
        }

        @Override // java.util.Comparator
        public int compare(com.android.server.am.CacheOomRanker.RankedProcessRecord rankedProcessRecord, com.android.server.am.CacheOomRanker.RankedProcessRecord rankedProcessRecord2) {
            return java.lang.Long.compare(rankedProcessRecord.proc.getLastActivityTime(), rankedProcessRecord2.proc.getLastActivityTime());
        }
    }

    private static class CacheUseComparator implements java.util.Comparator<com.android.server.am.CacheOomRanker.RankedProcessRecord> {
        private CacheUseComparator() {
        }

        @Override // java.util.Comparator
        public int compare(com.android.server.am.CacheOomRanker.RankedProcessRecord rankedProcessRecord, com.android.server.am.CacheOomRanker.RankedProcessRecord rankedProcessRecord2) {
            return java.lang.Long.compare(rankedProcessRecord.proc.mState.getCacheOomRankerUseCount(), rankedProcessRecord2.proc.mState.getCacheOomRankerUseCount());
        }
    }

    private static class RssComparator implements java.util.Comparator<com.android.server.am.CacheOomRanker.RankedProcessRecord> {
        private RssComparator() {
        }

        @Override // java.util.Comparator
        public int compare(com.android.server.am.CacheOomRanker.RankedProcessRecord rankedProcessRecord, com.android.server.am.CacheOomRanker.RankedProcessRecord rankedProcessRecord2) {
            return java.lang.Long.compare(rankedProcessRecord2.proc.mState.getCacheOomRankerRss(), rankedProcessRecord.proc.mState.getCacheOomRankerRss());
        }
    }

    private static class LastRssComparator implements java.util.Comparator<com.android.server.am.CacheOomRanker.RankedProcessRecord> {
        private LastRssComparator() {
        }

        @Override // java.util.Comparator
        public int compare(com.android.server.am.CacheOomRanker.RankedProcessRecord rankedProcessRecord, com.android.server.am.CacheOomRanker.RankedProcessRecord rankedProcessRecord2) {
            return java.lang.Long.compare(rankedProcessRecord2.proc.mProfile.getLastRss(), rankedProcessRecord.proc.mProfile.getLastRss());
        }
    }

    private static class ProcessDependenciesImpl implements com.android.server.am.CacheOomRanker.ProcessDependencies {
        private ProcessDependenciesImpl() {
        }

        @Override // com.android.server.am.CacheOomRanker.ProcessDependencies
        public long[] getRss(int i) {
            return android.os.Process.getRss(i);
        }
    }
}
