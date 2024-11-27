package com.android.server.am;

/* loaded from: classes.dex */
public final class CachedAppOptimizer {
    static final int ASYNC_RECEIVED_WHILE_FROZEN = 2;
    private static final java.lang.String ATRACE_COMPACTION_TRACK = "Compaction";
    private static final java.lang.String ATRACE_FREEZER_TRACK = "Freezer";
    static final int BINDER_ERROR_MSG = 8;
    private static final int COMPACT_ACTION_ANON_FLAG = 2;
    private static final int COMPACT_ACTION_FILE_FLAG = 1;
    static final double COMPACT_DOWNGRADE_FREE_SWAP_THRESHOLD = 0.2d;
    static final int COMPACT_NATIVE_MSG = 5;
    static final int COMPACT_PROCESS_MSG = 1;
    static final int COMPACT_SYSTEM_MSG = 2;
    static final int DEADLOCK_WATCHDOG_MSG = 7;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB = 8000;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB = 12000;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_COMPACT_THROTTLE_1 = 5000;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_COMPACT_THROTTLE_2 = 10000;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_COMPACT_THROTTLE_3 = 500;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_COMPACT_THROTTLE_4 = 10000;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_COMPACT_THROTTLE_5 = 600000;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_COMPACT_THROTTLE_6 = 600000;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ = 999;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ = 900;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_FREEZER_BINDER_ASYNC_THRESHOLD = 1024;

    @com.android.internal.annotations.VisibleForTesting
    static final boolean DEFAULT_FREEZER_BINDER_CALLBACK_ENABLED = true;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_FREEZER_BINDER_CALLBACK_THROTTLE = 10000;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_FREEZER_BINDER_DIVISOR = 4;

    @com.android.internal.annotations.VisibleForTesting
    static final boolean DEFAULT_FREEZER_BINDER_ENABLED = true;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_FREEZER_BINDER_OFFSET = 500;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_FREEZER_BINDER_THRESHOLD = 1000;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_FREEZER_DEBOUNCE_TIMEOUT = 10000;

    @com.android.internal.annotations.VisibleForTesting
    static final boolean DEFAULT_FREEZER_EXEMPT_INST_PKG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final float DEFAULT_STATSD_SAMPLE_RATE = 0.1f;

    @com.android.internal.annotations.VisibleForTesting
    static final boolean DEFAULT_USE_COMPACTION = true;

    @com.android.internal.annotations.VisibleForTesting
    static final boolean DEFAULT_USE_FREEZER = true;
    static final int DO_FREEZE = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final boolean ENABLE_SHARED_AND_CODE_COMPACT = false;
    private static final int FREEZE_BINDER_TIMEOUT_MS = 0;
    private static final int FREEZE_DEADLOCK_TIMEOUT_MS = 1000;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_FULL_DELTA_RSS_THROTTLE_KB = "compact_full_delta_rss_throttle_kb";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_FULL_RSS_THROTTLE_KB = "compact_full_rss_throttle_kb";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_PROC_STATE_THROTTLE = "compact_proc_state_throttle";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_STATSD_SAMPLE_RATE = "compact_statsd_sample_rate";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_THROTTLE_1 = "compact_throttle_1";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_THROTTLE_2 = "compact_throttle_2";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_THROTTLE_3 = "compact_throttle_3";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_THROTTLE_4 = "compact_throttle_4";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_THROTTLE_5 = "compact_throttle_5";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_THROTTLE_6 = "compact_throttle_6";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_THROTTLE_MAX_OOM_ADJ = "compact_throttle_max_oom_adj";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_COMPACT_THROTTLE_MIN_OOM_ADJ = "compact_throttle_min_oom_adj";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FREEZER_BINDER_ASYNC_THRESHOLD = "freeze_binder_async_threshold";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FREEZER_BINDER_CALLBACK_ENABLED = "freeze_binder_callback_enabled";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FREEZER_BINDER_CALLBACK_THROTTLE = "freeze_binder_callback_throttle";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FREEZER_BINDER_DIVISOR = "freeze_binder_divisor";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FREEZER_BINDER_ENABLED = "freeze_binder_enabled";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FREEZER_BINDER_OFFSET = "freeze_binder_offset";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FREEZER_BINDER_THRESHOLD = "freeze_binder_threshold";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FREEZER_DEBOUNCE_TIMEOUT = "freeze_debounce_timeout";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FREEZER_EXEMPT_INST_PKG = "freeze_exempt_inst_pkg";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FREEZER_STATSD_SAMPLE_RATE = "freeze_statsd_sample_rate";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_USE_COMPACTION = "use_compaction";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_USE_FREEZER = "use_freezer";
    static final int LAST_COMPACTED_ANY_PROCESS_STATS_HISTORY_SIZE = 20;
    static final int LAST_COMPACTION_FOR_PROCESS_STATS_SIZE = 256;
    static final int REPORT_UNFREEZE = 2;
    static final int REPORT_UNFREEZE_MSG = 4;
    private static final int RSS_ANON_INDEX = 2;
    private static final int RSS_FILE_INDEX = 1;
    private static final int RSS_SWAP_INDEX = 3;
    private static final int RSS_TOTAL_INDEX = 0;
    static final int SET_FROZEN_PROCESS_MSG = 3;
    static final int SYNC_RECEIVED_WHILE_FROZEN = 1;
    static final int TXNS_PENDING_WHILE_FROZEN = 4;
    static final int UID_FROZEN_STATE_CHANGED_MSG = 6;
    static final int UNFREEZE_REASON_ACTIVITY = 1;
    static final int UNFREEZE_REASON_ALLOWLIST = 10;
    static final int UNFREEZE_REASON_BACKUP = 22;
    static final int UNFREEZE_REASON_BINDER_TXNS = 18;
    static final int UNFREEZE_REASON_BIND_SERVICE = 4;
    static final int UNFREEZE_REASON_COMPONENT_DISABLED = 29;
    static final int UNFREEZE_REASON_EXECUTING_SERVICE = 27;
    static final int UNFREEZE_REASON_FEATURE_FLAGS = 19;
    static final int UNFREEZE_REASON_FILE_LOCKS = 16;
    static final int UNFREEZE_REASON_FILE_LOCK_CHECK_FAILURE = 17;
    static final int UNFREEZE_REASON_FINISH_RECEIVER = 2;
    static final int UNFREEZE_REASON_GET_PROVIDER = 7;
    static final int UNFREEZE_REASON_NONE = 0;
    static final int UNFREEZE_REASON_PING = 15;
    static final int UNFREEZE_REASON_PROCESS_BEGIN = 11;
    static final int UNFREEZE_REASON_PROCESS_END = 12;
    static final int UNFREEZE_REASON_REMOVE_PROVIDER = 8;
    static final int UNFREEZE_REASON_REMOVE_TASK = 24;
    static final int UNFREEZE_REASON_RESTRICTION_CHANGE = 28;
    static final int UNFREEZE_REASON_SHELL = 23;
    static final int UNFREEZE_REASON_SHORT_FGS_TIMEOUT = 20;
    static final int UNFREEZE_REASON_START_RECEIVER = 3;
    static final int UNFREEZE_REASON_START_SERVICE = 6;
    static final int UNFREEZE_REASON_STOP_SERVICE = 26;
    static final int UNFREEZE_REASON_SYSTEM_INIT = 21;
    static final int UNFREEZE_REASON_TRIM_MEMORY = 13;
    static final int UNFREEZE_REASON_UID_IDLE = 25;
    static final int UNFREEZE_REASON_UI_VISIBILITY = 9;
    static final int UNFREEZE_REASON_UNBIND_SERVICE = 5;
    private final com.android.server.am.ActivityManagerService mAm;
    final com.android.server.ServiceThread mCachedAppOptimizerThread;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile float mCompactStatsdSampleRate;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile long mCompactThrottleFullFull;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile long mCompactThrottleFullSome;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile long mCompactThrottleMaxOomAdj;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile long mCompactThrottleMinOomAdj;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile long mCompactThrottleSomeFull;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile long mCompactThrottleSomeSome;

    @com.android.internal.annotations.VisibleForTesting
    android.os.Handler mCompactionHandler;
    java.util.LinkedList<com.android.server.am.CachedAppOptimizer.SingleCompactionStats> mCompactionStatsHistory;
    private android.os.Handler mFreezeHandler;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile int mFreezerBinderAsyncThreshold;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile boolean mFreezerBinderCallbackEnabled;
    private long mFreezerBinderCallbackLast;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile long mFreezerBinderCallbackThrottle;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile long mFreezerBinderDivisor;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile boolean mFreezerBinderEnabled;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile int mFreezerBinderOffset;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile long mFreezerBinderThreshold;

    @com.android.internal.annotations.VisibleForTesting
    volatile long mFreezerDebounceTimeout;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mFreezerDisableCount;

    @com.android.internal.annotations.VisibleForTesting
    volatile boolean mFreezerExemptInstPkg;
    public final java.lang.Object mFreezerLock;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mFreezerOverride;

    @com.android.internal.annotations.VisibleForTesting
    volatile float mFreezerStatsdSampleRate;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private final android.util.SparseArray<com.android.server.am.ProcessRecord> mFrozenProcesses;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile long mFullAnonRssThrottleKb;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    volatile long mFullDeltaRssThrottleKb;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    @com.android.internal.annotations.VisibleForTesting
    java.util.LinkedHashMap<java.lang.Integer, com.android.server.am.CachedAppOptimizer.SingleCompactionStats> mLastCompactionStats;
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mOnFlagsChangedListener;
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mOnNativeBootFlagsChangedListener;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private final java.util.ArrayList<com.android.server.am.ProcessRecord> mPendingCompactionProcesses;
    private final java.util.LinkedHashMap<java.lang.String, com.android.server.am.CachedAppOptimizer.AggregatedProcessCompactionStats> mPerProcessCompactStats;
    private final java.util.EnumMap<com.android.server.am.CachedAppOptimizer.CompactSource, com.android.server.am.CachedAppOptimizer.AggregatedSourceCompactionStats> mPerSourceCompactStats;

    @com.android.internal.annotations.VisibleForTesting
    final java.lang.Object mPhenotypeFlagLock;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    private final com.android.internal.os.ProcLocksReader mProcLocksReader;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    @com.android.internal.annotations.VisibleForTesting
    final java.util.Set<java.lang.Integer> mProcStateThrottle;
    private final com.android.server.am.CachedAppOptimizer.ProcessDependencies mProcessDependencies;
    private final java.util.Random mRandom;
    private final com.android.server.am.CachedAppOptimizer.SettingsContentObserver mSettingsObserver;
    private long mSystemCompactionsPerformed;
    private long mSystemTotalMemFreed;
    private com.android.server.am.CachedAppOptimizer.PropertyChangedCallbackForTest mTestCallback;
    private long mTotalCompactionDowngrades;
    private java.util.EnumMap<com.android.server.am.CachedAppOptimizer.CancelCompactReason, java.lang.Integer> mTotalCompactionsCancelled;

    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    private volatile boolean mUseCompaction;
    private volatile boolean mUseFreezer;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String DEFAULT_COMPACT_PROC_STATE_THROTTLE = java.lang.String.valueOf(11);

    @com.android.internal.annotations.VisibleForTesting
    static final android.net.Uri CACHED_APP_FREEZER_ENABLED_URI = android.provider.Settings.Global.getUriFor("cached_apps_freezer");

    public enum CancelCompactReason {
        SCREEN_ON,
        OOM_IMPROVEMENT
    }

    public enum CompactProfile {
        NONE,
        SOME,
        ANON,
        FULL
    }

    public enum CompactSource {
        APP,
        SHELL
    }

    @com.android.internal.annotations.VisibleForTesting
    interface ProcessDependencies {
        long[] getRss(int i);

        void performCompaction(com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile, int i) throws java.io.IOException;
    }

    @com.android.internal.annotations.VisibleForTesting
    interface PropertyChangedCallbackForTest {
        void onPropertyChanged();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UnfreezeReason {
    }

    private static native void cancelCompaction();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void compactProcess(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void compactSystem();

    public static native int freezeBinder(int i, boolean z, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int getBinderFreezeInfo(int i);

    static native double getFreeSwapPercent();

    private static native java.lang.String getFreezerCheckPath();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getMemoryFreedCompaction();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getUsedZramMemory();

    private static native boolean isFreezerProfileValid();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long threadCpuTimeNs();

    private final class SettingsContentObserver extends android.database.ContentObserver {
        SettingsContentObserver() {
            super(com.android.server.am.CachedAppOptimizer.this.mAm.mHandler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (com.android.server.am.CachedAppOptimizer.CACHED_APP_FREEZER_ENABLED_URI.equals(uri)) {
                synchronized (com.android.server.am.CachedAppOptimizer.this.mPhenotypeFlagLock) {
                    com.android.server.am.CachedAppOptimizer.this.updateUseFreezer();
                }
            }
        }
    }

    class AggregatedCompactionStats {
        public long mFullCompactPerformed;
        public long mFullCompactRequested;
        public double mMaxCompactEfficiency;
        public long mProcCompactionsMiscThrottled;
        public long mProcCompactionsNoPidThrottled;
        public long mProcCompactionsOomAdjThrottled;
        public long mProcCompactionsRSSThrottled;
        public long mProcCompactionsTimeThrottled;
        public long mSomeCompactPerformed;
        public long mSomeCompactRequested;
        public long mSumOrigAnonRss;
        public long mTotalAnonMemFreedKBs;
        public long mTotalCpuTimeMillis;
        public long mTotalDeltaAnonRssKBs;
        public long mTotalZramConsumedKBs;

        AggregatedCompactionStats() {
        }

        public long getThrottledSome() {
            return this.mSomeCompactRequested - this.mSomeCompactPerformed;
        }

        public long getThrottledFull() {
            return this.mFullCompactRequested - this.mFullCompactPerformed;
        }

        public void addMemStats(long j, long j2, long j3, long j4, long j5) {
            double d = j3 / j4;
            if (d > this.mMaxCompactEfficiency) {
                this.mMaxCompactEfficiency = d;
            }
            this.mTotalDeltaAnonRssKBs += j;
            this.mTotalZramConsumedKBs += j2;
            this.mTotalAnonMemFreedKBs += j3;
            this.mSumOrigAnonRss += j4;
            this.mTotalCpuTimeMillis += j5;
        }

        public void dump(java.io.PrintWriter printWriter) {
            long j = this.mSomeCompactRequested + this.mFullCompactRequested;
            long j2 = this.mSomeCompactPerformed + this.mFullCompactPerformed;
            printWriter.println("    Performed / Requested:");
            printWriter.println("      Some: (" + this.mSomeCompactPerformed + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mSomeCompactRequested + ")");
            printWriter.println("      Full: (" + this.mFullCompactPerformed + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mFullCompactRequested + ")");
            long throttledSome = getThrottledSome();
            long throttledFull = getThrottledFull();
            long j3 = 0;
            if (throttledSome > 0 || throttledFull > 0) {
                printWriter.println("    Throttled:");
                printWriter.println("       Some: " + throttledSome + " Full: " + throttledFull);
                printWriter.println("    Throttled by Type:");
                long j4 = j - j2;
                printWriter.println("       NoPid: " + this.mProcCompactionsNoPidThrottled + " OomAdj: " + this.mProcCompactionsOomAdjThrottled + " Time: " + this.mProcCompactionsTimeThrottled + " RSS: " + this.mProcCompactionsRSSThrottled + " Misc: " + this.mProcCompactionsMiscThrottled + " Unaccounted: " + (((((j4 - this.mProcCompactionsNoPidThrottled) - this.mProcCompactionsOomAdjThrottled) - this.mProcCompactionsTimeThrottled) - this.mProcCompactionsRSSThrottled) - this.mProcCompactionsMiscThrottled));
                double d = (((double) j4) / ((double) j)) * 100.0d;
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("    Throttle Percentage: ");
                sb.append(d);
                printWriter.println(sb.toString());
            }
            if (this.mFullCompactPerformed > 0) {
                printWriter.println("    -----Memory Stats----");
                printWriter.println("    Total Delta Anon RSS (KB) : " + this.mTotalDeltaAnonRssKBs);
                printWriter.println("    Total Physical ZRAM Consumed (KB): " + this.mTotalZramConsumedKBs);
                printWriter.println("    Total Anon Memory Freed (KB): " + this.mTotalAnonMemFreedKBs);
                printWriter.println("    Avg Compaction Efficiency (Anon Freed/Anon RSS): " + (((double) this.mTotalAnonMemFreedKBs) / ((double) this.mSumOrigAnonRss)));
                printWriter.println("    Max Compaction Efficiency: " + this.mMaxCompactEfficiency);
                printWriter.println("    Avg Compression Ratio (1 - ZRAM Consumed/DeltaAnonRSS): " + (1.0d - (((double) this.mTotalZramConsumedKBs) / ((double) this.mTotalDeltaAnonRssKBs))));
                if (this.mFullCompactPerformed > 0) {
                    j3 = this.mTotalAnonMemFreedKBs / this.mFullCompactPerformed;
                }
                printWriter.println("    Avg Anon Mem Freed/Compaction (KB) : " + j3);
                printWriter.println("    Compaction Cost (ms/MB): " + (((double) this.mTotalCpuTimeMillis) / (((double) this.mTotalAnonMemFreedKBs) / 1024.0d)));
            }
        }
    }

    class AggregatedProcessCompactionStats extends com.android.server.am.CachedAppOptimizer.AggregatedCompactionStats {
        public final java.lang.String processName;

        AggregatedProcessCompactionStats(java.lang.String str) {
            super();
            this.processName = str;
        }
    }

    class AggregatedSourceCompactionStats extends com.android.server.am.CachedAppOptimizer.AggregatedCompactionStats {
        public final com.android.server.am.CachedAppOptimizer.CompactSource sourceType;

        AggregatedSourceCompactionStats(com.android.server.am.CachedAppOptimizer.CompactSource compactSource) {
            super();
            this.sourceType = compactSource;
        }
    }

    public CachedAppOptimizer(com.android.server.am.ActivityManagerService activityManagerService) {
        this(activityManagerService, null, new com.android.server.am.CachedAppOptimizer.DefaultProcessDependencies());
    }

    @com.android.internal.annotations.VisibleForTesting
    CachedAppOptimizer(com.android.server.am.ActivityManagerService activityManagerService, com.android.server.am.CachedAppOptimizer.PropertyChangedCallbackForTest propertyChangedCallbackForTest, com.android.server.am.CachedAppOptimizer.ProcessDependencies processDependencies) {
        this.mPendingCompactionProcesses = new java.util.ArrayList<>();
        this.mFrozenProcesses = new android.util.SparseArray<>();
        this.mFreezerLock = new java.lang.Object();
        this.mOnFlagsChangedListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.am.CachedAppOptimizer.1
            public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                synchronized (com.android.server.am.CachedAppOptimizer.this.mPhenotypeFlagLock) {
                    try {
                        for (java.lang.String str : properties.getKeyset()) {
                            if (com.android.server.am.CachedAppOptimizer.KEY_USE_COMPACTION.equals(str)) {
                                com.android.server.am.CachedAppOptimizer.this.updateUseCompaction();
                            } else {
                                if (!com.android.server.am.CachedAppOptimizer.KEY_COMPACT_THROTTLE_1.equals(str) && !com.android.server.am.CachedAppOptimizer.KEY_COMPACT_THROTTLE_2.equals(str) && !com.android.server.am.CachedAppOptimizer.KEY_COMPACT_THROTTLE_3.equals(str) && !com.android.server.am.CachedAppOptimizer.KEY_COMPACT_THROTTLE_4.equals(str) && !com.android.server.am.CachedAppOptimizer.KEY_COMPACT_THROTTLE_5.equals(str) && !com.android.server.am.CachedAppOptimizer.KEY_COMPACT_THROTTLE_6.equals(str)) {
                                    if (com.android.server.am.CachedAppOptimizer.KEY_COMPACT_STATSD_SAMPLE_RATE.equals(str)) {
                                        com.android.server.am.CachedAppOptimizer.this.updateCompactStatsdSampleRate();
                                    } else if (com.android.server.am.CachedAppOptimizer.KEY_FREEZER_STATSD_SAMPLE_RATE.equals(str)) {
                                        com.android.server.am.CachedAppOptimizer.this.updateFreezerStatsdSampleRate();
                                    } else if (com.android.server.am.CachedAppOptimizer.KEY_COMPACT_FULL_RSS_THROTTLE_KB.equals(str)) {
                                        com.android.server.am.CachedAppOptimizer.this.updateFullRssThrottle();
                                    } else if (com.android.server.am.CachedAppOptimizer.KEY_COMPACT_FULL_DELTA_RSS_THROTTLE_KB.equals(str)) {
                                        com.android.server.am.CachedAppOptimizer.this.updateFullDeltaRssThrottle();
                                    } else if (com.android.server.am.CachedAppOptimizer.KEY_COMPACT_PROC_STATE_THROTTLE.equals(str)) {
                                        com.android.server.am.CachedAppOptimizer.this.updateProcStateThrottle();
                                    } else if (com.android.server.am.CachedAppOptimizer.KEY_COMPACT_THROTTLE_MIN_OOM_ADJ.equals(str)) {
                                        com.android.server.am.CachedAppOptimizer.this.updateMinOomAdjThrottle();
                                    } else if (com.android.server.am.CachedAppOptimizer.KEY_COMPACT_THROTTLE_MAX_OOM_ADJ.equals(str)) {
                                        com.android.server.am.CachedAppOptimizer.this.updateMaxOomAdjThrottle();
                                    }
                                }
                                com.android.server.am.CachedAppOptimizer.this.updateCompactionThrottles();
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (com.android.server.am.CachedAppOptimizer.this.mTestCallback != null) {
                    com.android.server.am.CachedAppOptimizer.this.mTestCallback.onPropertyChanged();
                }
            }
        };
        this.mOnNativeBootFlagsChangedListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.am.CachedAppOptimizer.2
            public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                synchronized (com.android.server.am.CachedAppOptimizer.this.mPhenotypeFlagLock) {
                    try {
                        for (java.lang.String str : properties.getKeyset()) {
                            if (com.android.server.am.CachedAppOptimizer.KEY_FREEZER_DEBOUNCE_TIMEOUT.equals(str)) {
                                com.android.server.am.CachedAppOptimizer.this.updateFreezerDebounceTimeout();
                            } else if (com.android.server.am.CachedAppOptimizer.KEY_FREEZER_EXEMPT_INST_PKG.equals(str)) {
                                com.android.server.am.CachedAppOptimizer.this.updateFreezerExemptInstPkg();
                            } else if (com.android.server.am.CachedAppOptimizer.KEY_FREEZER_BINDER_ENABLED.equals(str) || com.android.server.am.CachedAppOptimizer.KEY_FREEZER_BINDER_DIVISOR.equals(str) || com.android.server.am.CachedAppOptimizer.KEY_FREEZER_BINDER_THRESHOLD.equals(str) || com.android.server.am.CachedAppOptimizer.KEY_FREEZER_BINDER_OFFSET.equals(str) || com.android.server.am.CachedAppOptimizer.KEY_FREEZER_BINDER_CALLBACK_ENABLED.equals(str) || com.android.server.am.CachedAppOptimizer.KEY_FREEZER_BINDER_CALLBACK_THROTTLE.equals(str) || com.android.server.am.CachedAppOptimizer.KEY_FREEZER_BINDER_ASYNC_THRESHOLD.equals(str)) {
                                com.android.server.am.CachedAppOptimizer.this.updateFreezerBinderState();
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (com.android.server.am.CachedAppOptimizer.this.mTestCallback != null) {
                    com.android.server.am.CachedAppOptimizer.this.mTestCallback.onPropertyChanged();
                }
            }
        };
        this.mPhenotypeFlagLock = new java.lang.Object();
        this.mCompactThrottleSomeSome = DEFAULT_COMPACT_THROTTLE_1;
        this.mCompactThrottleSomeFull = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
        this.mCompactThrottleFullSome = 500L;
        this.mCompactThrottleFullFull = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
        this.mCompactThrottleMinOomAdj = DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ;
        this.mCompactThrottleMaxOomAdj = DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ;
        this.mUseCompaction = true;
        this.mUseFreezer = false;
        this.mFreezerDisableCount = 1;
        this.mRandom = new java.util.Random();
        this.mCompactStatsdSampleRate = DEFAULT_STATSD_SAMPLE_RATE;
        this.mFreezerStatsdSampleRate = DEFAULT_STATSD_SAMPLE_RATE;
        this.mFullAnonRssThrottleKb = DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB;
        this.mFullDeltaRssThrottleKb = DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB;
        this.mFreezerBinderEnabled = true;
        this.mFreezerBinderDivisor = DEFAULT_FREEZER_BINDER_DIVISOR;
        this.mFreezerBinderOffset = 500;
        this.mFreezerBinderThreshold = 1000L;
        this.mFreezerBinderCallbackEnabled = true;
        this.mFreezerBinderCallbackThrottle = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
        this.mFreezerBinderAsyncThreshold = 1024;
        this.mFreezerOverride = false;
        this.mFreezerBinderCallbackLast = -1L;
        this.mFreezerDebounceTimeout = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
        this.mFreezerExemptInstPkg = false;
        this.mLastCompactionStats = new java.util.LinkedHashMap<java.lang.Integer, com.android.server.am.CachedAppOptimizer.SingleCompactionStats>() { // from class: com.android.server.am.CachedAppOptimizer.3
            @Override // java.util.LinkedHashMap
            protected boolean removeEldestEntry(java.util.Map.Entry<java.lang.Integer, com.android.server.am.CachedAppOptimizer.SingleCompactionStats> entry) {
                return size() > 256;
            }
        };
        this.mCompactionStatsHistory = new java.util.LinkedList<com.android.server.am.CachedAppOptimizer.SingleCompactionStats>() { // from class: com.android.server.am.CachedAppOptimizer.4
            @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
            public boolean add(com.android.server.am.CachedAppOptimizer.SingleCompactionStats singleCompactionStats) {
                if (size() >= 20) {
                    remove();
                }
                return super.add((com.android.server.am.CachedAppOptimizer.AnonymousClass4) singleCompactionStats);
            }
        };
        this.mPerProcessCompactStats = new java.util.LinkedHashMap<>(256);
        this.mPerSourceCompactStats = new java.util.EnumMap<>(com.android.server.am.CachedAppOptimizer.CompactSource.class);
        this.mTotalCompactionsCancelled = new java.util.EnumMap<>(com.android.server.am.CachedAppOptimizer.CancelCompactReason.class);
        this.mAm = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.mCachedAppOptimizerThread = new com.android.server.ServiceThread("CachedAppOptimizerThread", 2, true);
        this.mProcStateThrottle = new java.util.HashSet();
        this.mProcessDependencies = processDependencies;
        this.mTestCallback = propertyChangedCallbackForTest;
        this.mSettingsObserver = new com.android.server.am.CachedAppOptimizer.SettingsContentObserver();
        this.mProcLocksReader = new com.android.internal.os.ProcLocksReader();
    }

    public void init() {
        android.provider.DeviceConfig.addOnPropertiesChangedListener("activity_manager", android.app.ActivityThread.currentApplication().getMainExecutor(), this.mOnFlagsChangedListener);
        android.provider.DeviceConfig.addOnPropertiesChangedListener("activity_manager_native_boot", android.app.ActivityThread.currentApplication().getMainExecutor(), this.mOnNativeBootFlagsChangedListener);
        this.mAm.mContext.getContentResolver().registerContentObserver(CACHED_APP_FREEZER_ENABLED_URI, false, this.mSettingsObserver);
        synchronized (this.mPhenotypeFlagLock) {
            updateUseCompaction();
            updateCompactionThrottles();
            updateCompactStatsdSampleRate();
            updateFreezerStatsdSampleRate();
            updateFullRssThrottle();
            updateFullDeltaRssThrottle();
            updateProcStateThrottle();
            updateUseFreezer();
            updateMinOomAdjThrottle();
            updateMaxOomAdjThrottle();
        }
    }

    public boolean useCompaction() {
        boolean z;
        synchronized (this.mPhenotypeFlagLock) {
            z = this.mUseCompaction;
        }
        return z;
    }

    public boolean useFreezer() {
        boolean z;
        synchronized (this.mPhenotypeFlagLock) {
            z = this.mUseFreezer;
        }
        return z;
    }

    public boolean freezerExemptInstPkg() {
        boolean z;
        synchronized (this.mPhenotypeFlagLock) {
            try {
                z = this.mUseFreezer && this.mFreezerExemptInstPkg;
            } finally {
            }
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void dump(java.io.PrintWriter printWriter) {
        double d;
        printWriter.println("CachedAppOptimizer settings");
        synchronized (this.mPhenotypeFlagLock) {
            try {
                printWriter.println("  use_compaction=" + this.mUseCompaction);
                printWriter.println("  compact_throttle_1=" + this.mCompactThrottleSomeSome);
                printWriter.println("  compact_throttle_2=" + this.mCompactThrottleSomeFull);
                printWriter.println("  compact_throttle_3=" + this.mCompactThrottleFullSome);
                printWriter.println("  compact_throttle_4=" + this.mCompactThrottleFullFull);
                printWriter.println("  compact_throttle_min_oom_adj=" + this.mCompactThrottleMinOomAdj);
                printWriter.println("  compact_throttle_max_oom_adj=" + this.mCompactThrottleMaxOomAdj);
                printWriter.println("  compact_statsd_sample_rate=" + this.mCompactStatsdSampleRate);
                printWriter.println("  compact_full_rss_throttle_kb=" + this.mFullAnonRssThrottleKb);
                printWriter.println("  compact_full_delta_rss_throttle_kb=" + this.mFullDeltaRssThrottleKb);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("  compact_proc_state_throttle=");
                sb.append(java.util.Arrays.toString(this.mProcStateThrottle.toArray(new java.lang.Integer[0])));
                printWriter.println(sb.toString());
                printWriter.println(" Per-Process Compaction Stats");
                long j = 0;
                long j2 = 0;
                for (com.android.server.am.CachedAppOptimizer.AggregatedProcessCompactionStats aggregatedProcessCompactionStats : this.mPerProcessCompactStats.values()) {
                    printWriter.println("-----" + aggregatedProcessCompactionStats.processName + "-----");
                    j += aggregatedProcessCompactionStats.mSomeCompactPerformed;
                    j2 += aggregatedProcessCompactionStats.mFullCompactPerformed;
                    aggregatedProcessCompactionStats.dump(printWriter);
                    printWriter.println();
                }
                printWriter.println();
                printWriter.println(" Per-Source Compaction Stats");
                for (com.android.server.am.CachedAppOptimizer.AggregatedSourceCompactionStats aggregatedSourceCompactionStats : this.mPerSourceCompactStats.values()) {
                    printWriter.println("-----" + aggregatedSourceCompactionStats.sourceType + "-----");
                    aggregatedSourceCompactionStats.dump(printWriter);
                    printWriter.println();
                }
                printWriter.println();
                printWriter.println("Total Compactions Performed by profile: " + j + " some, " + j2 + " full");
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                sb2.append("Total compactions downgraded: ");
                sb2.append(this.mTotalCompactionDowngrades);
                printWriter.println(sb2.toString());
                printWriter.println("Total compactions cancelled by reason: ");
                for (com.android.server.am.CachedAppOptimizer.CancelCompactReason cancelCompactReason : this.mTotalCompactionsCancelled.keySet()) {
                    printWriter.println("    " + cancelCompactReason + ": " + this.mTotalCompactionsCancelled.get(cancelCompactReason));
                }
                printWriter.println();
                printWriter.println(" System Compaction Memory Stats");
                printWriter.println("    Compactions Performed: " + this.mSystemCompactionsPerformed);
                printWriter.println("    Total Memory Freed (KB): " + this.mSystemTotalMemFreed);
                if (this.mSystemCompactionsPerformed > 0) {
                    d = this.mSystemTotalMemFreed / this.mSystemCompactionsPerformed;
                } else {
                    d = 0.0d;
                }
                printWriter.println("    Avg Mem Freed per Compact (KB): " + d);
                printWriter.println();
                printWriter.println("  Tracking last compaction stats for " + this.mLastCompactionStats.size() + " processes.");
                printWriter.println("Last Compaction per process stats:");
                printWriter.println("    (ProcessName,Source,DeltaAnonRssKBs,ZramConsumedKBs,AnonMemFreedKBs,CompactEfficiency,CompactCost(ms/MB),procState,oomAdj,oomAdjReason)");
                java.util.Iterator<java.util.Map.Entry<java.lang.Integer, com.android.server.am.CachedAppOptimizer.SingleCompactionStats>> it = this.mLastCompactionStats.entrySet().iterator();
                while (it.hasNext()) {
                    it.next().getValue().dump(printWriter);
                }
                printWriter.println();
                printWriter.println("Last 20 Compactions Stats:");
                printWriter.println("    (ProcessName,Source,DeltaAnonRssKBs,ZramConsumedKBs,AnonMemFreedKBs,CompactEfficiency,CompactCost(ms/MB),procState,oomAdj,oomAdjReason)");
                java.util.Iterator<com.android.server.am.CachedAppOptimizer.SingleCompactionStats> it2 = this.mCompactionStatsHistory.iterator();
                while (it2.hasNext()) {
                    it2.next().dump(printWriter);
                }
                printWriter.println();
                printWriter.println("  use_freezer=" + this.mUseFreezer);
                printWriter.println("  freeze_statsd_sample_rate=" + this.mFreezerStatsdSampleRate);
                printWriter.println("  freeze_debounce_timeout=" + this.mFreezerDebounceTimeout);
                printWriter.println("  freeze_exempt_inst_pkg=" + this.mFreezerExemptInstPkg);
                printWriter.println("  freeze_binder_enabled=" + this.mFreezerBinderEnabled);
                printWriter.println("  freeze_binder_threshold=" + this.mFreezerBinderThreshold);
                printWriter.println("  freeze_binder_divisor=" + this.mFreezerBinderDivisor);
                printWriter.println("  freeze_binder_offset=" + this.mFreezerBinderOffset);
                printWriter.println("  freeze_binder_callback_enabled=" + this.mFreezerBinderCallbackEnabled);
                printWriter.println("  freeze_binder_callback_throttle=" + this.mFreezerBinderCallbackThrottle);
                printWriter.println("  freeze_binder_async_threshold=" + this.mFreezerBinderAsyncThreshold);
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        int size = this.mFrozenProcesses.size();
                        printWriter.println("  Apps frozen: " + size);
                        for (int i = 0; i < size; i++) {
                            com.android.server.am.ProcessRecord valueAt = this.mFrozenProcesses.valueAt(i);
                            java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                            sb3.append("    ");
                            sb3.append(valueAt.mOptRecord.getFreezeUnfreezeTime());
                            sb3.append(": ");
                            sb3.append(valueAt.getPid());
                            sb3.append(" ");
                            sb3.append(valueAt.processName);
                            sb3.append(valueAt.mOptRecord.isFreezeSticky() ? " (sticky)" : "");
                            printWriter.println(sb3.toString());
                        }
                        if (!this.mPendingCompactionProcesses.isEmpty()) {
                            printWriter.println("  Pending compactions:");
                            int size2 = this.mPendingCompactionProcesses.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                com.android.server.am.ProcessRecord processRecord = this.mPendingCompactionProcesses.get(i2);
                                printWriter.println("    pid: " + processRecord.getPid() + ". name: " + processRecord.processName + ". hasPendingCompact: " + processRecord.mOptRecord.hasPendingCompact());
                            }
                        }
                    } finally {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean compactApp(com.android.server.am.ProcessRecord processRecord, com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile, com.android.server.am.CachedAppOptimizer.CompactSource compactSource, boolean z) {
        processRecord.mOptRecord.setReqCompactSource(compactSource);
        processRecord.mOptRecord.setReqCompactProfile(compactProfile);
        com.android.server.am.CachedAppOptimizer.AggregatedSourceCompactionStats perSourceAggregatedCompactStat = getPerSourceAggregatedCompactStat(compactSource);
        com.android.server.am.CachedAppOptimizer.AggregatedProcessCompactionStats perProcessAggregatedCompactStat = getPerProcessAggregatedCompactStat(processRecord.processName);
        switch (com.android.server.am.CachedAppOptimizer.AnonymousClass5.$SwitchMap$com$android$server$am$CachedAppOptimizer$CompactProfile[compactProfile.ordinal()]) {
            case 1:
                perProcessAggregatedCompactStat.mSomeCompactRequested++;
                perSourceAggregatedCompactStat.mSomeCompactRequested++;
                break;
            case 2:
                perProcessAggregatedCompactStat.mFullCompactRequested++;
                perSourceAggregatedCompactStat.mFullCompactRequested++;
                break;
            default:
                android.util.Slog.e("ActivityManager", "Unimplemented compaction type, consider adding it.");
                return false;
        }
        if (processRecord.mOptRecord.hasPendingCompact()) {
            return false;
        }
        processRecord.mOptRecord.setHasPendingCompact(true);
        processRecord.mOptRecord.setForceCompact(z);
        this.mPendingCompactionProcesses.add(processRecord);
        this.mCompactionHandler.sendMessage(this.mCompactionHandler.obtainMessage(1, processRecord.mState.getCurAdj(), processRecord.mState.getSetProcState()));
        return true;
    }

    /* renamed from: com.android.server.am.CachedAppOptimizer$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$android$server$am$CachedAppOptimizer$CompactProfile = new int[com.android.server.am.CachedAppOptimizer.CompactProfile.values().length];

        static {
            try {
                $SwitchMap$com$android$server$am$CachedAppOptimizer$CompactProfile[com.android.server.am.CachedAppOptimizer.CompactProfile.SOME.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$server$am$CachedAppOptimizer$CompactProfile[com.android.server.am.CachedAppOptimizer.CompactProfile.FULL.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
        }
    }

    void compactNative(com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile, int i) {
        this.mCompactionHandler.sendMessage(this.mCompactionHandler.obtainMessage(5, i, compactProfile.ordinal()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.am.CachedAppOptimizer.AggregatedProcessCompactionStats getPerProcessAggregatedCompactStat(java.lang.String str) {
        if (str == null) {
            str = "";
        }
        com.android.server.am.CachedAppOptimizer.AggregatedProcessCompactionStats aggregatedProcessCompactionStats = this.mPerProcessCompactStats.get(str);
        if (aggregatedProcessCompactionStats == null) {
            com.android.server.am.CachedAppOptimizer.AggregatedProcessCompactionStats aggregatedProcessCompactionStats2 = new com.android.server.am.CachedAppOptimizer.AggregatedProcessCompactionStats(str);
            this.mPerProcessCompactStats.put(str, aggregatedProcessCompactionStats2);
            return aggregatedProcessCompactionStats2;
        }
        return aggregatedProcessCompactionStats;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.am.CachedAppOptimizer.AggregatedSourceCompactionStats getPerSourceAggregatedCompactStat(com.android.server.am.CachedAppOptimizer.CompactSource compactSource) {
        com.android.server.am.CachedAppOptimizer.AggregatedSourceCompactionStats aggregatedSourceCompactionStats = this.mPerSourceCompactStats.get(compactSource);
        if (aggregatedSourceCompactionStats == null) {
            com.android.server.am.CachedAppOptimizer.AggregatedSourceCompactionStats aggregatedSourceCompactionStats2 = new com.android.server.am.CachedAppOptimizer.AggregatedSourceCompactionStats(compactSource);
            this.mPerSourceCompactStats.put((java.util.EnumMap<com.android.server.am.CachedAppOptimizer.CompactSource, com.android.server.am.CachedAppOptimizer.AggregatedSourceCompactionStats>) compactSource, (com.android.server.am.CachedAppOptimizer.CompactSource) aggregatedSourceCompactionStats2);
            return aggregatedSourceCompactionStats2;
        }
        return aggregatedSourceCompactionStats;
    }

    void compactAllSystem() {
        if (useCompaction()) {
            android.os.Trace.instantForTrack(64L, ATRACE_COMPACTION_TRACK, "compactAllSystem");
            this.mCompactionHandler.sendMessage(this.mCompactionHandler.obtainMessage(2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateUseCompaction() {
        this.mUseCompaction = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_USE_COMPACTION, true);
        if (this.mUseCompaction && this.mCompactionHandler == null) {
            if (!this.mCachedAppOptimizerThread.isAlive()) {
                this.mCachedAppOptimizerThread.start();
            }
            this.mCompactionHandler = new com.android.server.am.CachedAppOptimizer.MemCompactionHandler();
            android.os.Process.setThreadGroupAndCpuset(this.mCachedAppOptimizerThread.getThreadId(), 2);
        }
    }

    public synchronized boolean enableFreezer(final boolean z) {
        if (!this.mUseFreezer) {
            return false;
        }
        if (z) {
            this.mFreezerDisableCount--;
            if (this.mFreezerDisableCount > 0) {
                return true;
            }
            if (this.mFreezerDisableCount < 0) {
                android.util.Slog.e("ActivityManager", "unbalanced call to enableFreezer, ignoring");
                this.mFreezerDisableCount = 0;
                return false;
            }
        } else {
            this.mFreezerDisableCount++;
            if (this.mFreezerDisableCount > 1) {
                return true;
            }
        }
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        this.mFreezerOverride = z ? false : true;
                        android.util.Slog.d("ActivityManager", "freezer override set to " + this.mFreezerOverride);
                        this.mAm.mProcessList.forEachLruProcessesLOSP(true, new java.util.function.Consumer() { // from class: com.android.server.am.CachedAppOptimizer$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.am.CachedAppOptimizer.this.lambda$enableFreezer$0(z, (com.android.server.am.ProcessRecord) obj);
                            }
                        });
                    } catch (java.lang.Throwable th) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (java.lang.Throwable th2) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enableFreezer$0(boolean z, com.android.server.am.ProcessRecord processRecord) {
        if (processRecord == null) {
            return;
        }
        com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        if (z && processCachedOptimizerRecord.hasFreezerOverride()) {
            freezeAppAsyncLSP(processRecord);
            processCachedOptimizerRecord.setFreezerOverride(false);
        }
        if (!z && processCachedOptimizerRecord.isFrozen()) {
            unfreezeAppLSP(processRecord, 19);
            processCachedOptimizerRecord.setFreezerOverride(true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isFreezerSupported() {
        java.io.FileReader fileReader;
        java.lang.Exception e;
        boolean z = false;
        java.io.FileReader fileReader2 = null;
        try {
            java.lang.String freezerCheckPath = getFreezerCheckPath();
            android.util.Slog.d("ActivityManager", "Checking cgroup freezer: " + freezerCheckPath);
            fileReader = new java.io.FileReader(freezerCheckPath);
            try {
                char read = (char) fileReader.read();
                if (read == '1' || read == '0') {
                    android.util.Slog.d("ActivityManager", "Checking binder freezer ioctl");
                    getBinderFreezeInfo(android.os.Process.myPid());
                    android.util.Slog.d("ActivityManager", "Checking freezer profiles");
                    z = isFreezerProfileValid();
                } else {
                    android.util.Slog.e("ActivityManager", "Unexpected value in cgroup.freeze");
                }
            } catch (java.io.FileNotFoundException e2) {
                fileReader2 = fileReader;
                android.util.Slog.w("ActivityManager", "File cgroup.freeze not present");
                fileReader = fileReader2;
                if (fileReader != null) {
                }
                android.util.Slog.d("ActivityManager", "Freezer supported: " + z);
                return z;
            } catch (java.lang.RuntimeException e3) {
                fileReader2 = fileReader;
                android.util.Slog.w("ActivityManager", "Unable to read freezer info");
                fileReader = fileReader2;
                if (fileReader != null) {
                }
                android.util.Slog.d("ActivityManager", "Freezer supported: " + z);
                return z;
            } catch (java.lang.Exception e4) {
                e = e4;
                android.util.Slog.w("ActivityManager", "Unable to read cgroup.freeze: " + e.toString());
                if (fileReader != null) {
                }
                android.util.Slog.d("ActivityManager", "Freezer supported: " + z);
                return z;
            }
        } catch (java.io.FileNotFoundException e5) {
        } catch (java.lang.RuntimeException e6) {
        } catch (java.lang.Exception e7) {
            fileReader = null;
            e = e7;
        }
        if (fileReader != null) {
            try {
                fileReader.close();
            } catch (java.io.IOException e8) {
                android.util.Slog.e("ActivityManager", "Exception closing cgroup.freeze: " + e8.toString());
            }
        }
        android.util.Slog.d("ActivityManager", "Freezer supported: " + z);
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateUseFreezer() {
        java.lang.String string = android.provider.Settings.Global.getString(this.mAm.mContext.getContentResolver(), "cached_apps_freezer");
        if (com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED.equals(string)) {
            this.mUseFreezer = false;
        } else if (com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED.equals(string) || android.provider.DeviceConfig.getBoolean("activity_manager_native_boot", KEY_USE_FREEZER, true)) {
            this.mUseFreezer = isFreezerSupported();
            updateFreezerDebounceTimeout();
            updateFreezerExemptInstPkg();
        } else {
            this.mUseFreezer = false;
        }
        final boolean z = this.mUseFreezer;
        this.mAm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.CachedAppOptimizer$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.CachedAppOptimizer.this.lambda$updateUseFreezer$1(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateUseFreezer$1(boolean z) {
        if (z) {
            android.util.Slog.d("ActivityManager", "Freezer enabled");
            enableFreezer(true);
            if (!this.mCachedAppOptimizerThread.isAlive()) {
                this.mCachedAppOptimizerThread.start();
            }
            if (this.mFreezeHandler == null) {
                this.mFreezeHandler = new com.android.server.am.CachedAppOptimizer.FreezeHandler();
            }
            android.os.Process.setThreadGroupAndCpuset(this.mCachedAppOptimizerThread.getThreadId(), 2);
            return;
        }
        android.util.Slog.d("ActivityManager", "Freezer disabled");
        enableFreezer(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateCompactionThrottles() {
        java.lang.String property = android.provider.DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_1);
        java.lang.String property2 = android.provider.DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_2);
        java.lang.String property3 = android.provider.DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_3);
        java.lang.String property4 = android.provider.DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_4);
        java.lang.String property5 = android.provider.DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_5);
        java.lang.String property6 = android.provider.DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_6);
        java.lang.String property7 = android.provider.DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_MIN_OOM_ADJ);
        java.lang.String property8 = android.provider.DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_MAX_OOM_ADJ);
        boolean z = true;
        if (!android.text.TextUtils.isEmpty(property) && !android.text.TextUtils.isEmpty(property2) && !android.text.TextUtils.isEmpty(property3) && !android.text.TextUtils.isEmpty(property4) && !android.text.TextUtils.isEmpty(property5) && !android.text.TextUtils.isEmpty(property6) && !android.text.TextUtils.isEmpty(property7) && !android.text.TextUtils.isEmpty(property8)) {
            try {
                this.mCompactThrottleSomeSome = java.lang.Integer.parseInt(property);
                this.mCompactThrottleSomeFull = java.lang.Integer.parseInt(property2);
                this.mCompactThrottleFullSome = java.lang.Integer.parseInt(property3);
                this.mCompactThrottleFullFull = java.lang.Integer.parseInt(property4);
                this.mCompactThrottleMinOomAdj = java.lang.Long.parseLong(property7);
                this.mCompactThrottleMaxOomAdj = java.lang.Long.parseLong(property8);
                z = false;
            } catch (java.lang.NumberFormatException e) {
            }
        }
        if (z) {
            this.mCompactThrottleSomeSome = DEFAULT_COMPACT_THROTTLE_1;
            this.mCompactThrottleSomeFull = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
            this.mCompactThrottleFullSome = 500L;
            this.mCompactThrottleFullFull = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
            this.mCompactThrottleMinOomAdj = DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ;
            this.mCompactThrottleMaxOomAdj = DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateCompactStatsdSampleRate() {
        this.mCompactStatsdSampleRate = android.provider.DeviceConfig.getFloat("activity_manager", KEY_COMPACT_STATSD_SAMPLE_RATE, DEFAULT_STATSD_SAMPLE_RATE);
        this.mCompactStatsdSampleRate = java.lang.Math.min(1.0f, java.lang.Math.max(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, this.mCompactStatsdSampleRate));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateFreezerStatsdSampleRate() {
        this.mFreezerStatsdSampleRate = android.provider.DeviceConfig.getFloat("activity_manager", KEY_FREEZER_STATSD_SAMPLE_RATE, DEFAULT_STATSD_SAMPLE_RATE);
        this.mFreezerStatsdSampleRate = java.lang.Math.min(1.0f, java.lang.Math.max(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, this.mFreezerStatsdSampleRate));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateFullRssThrottle() {
        this.mFullAnonRssThrottleKb = android.provider.DeviceConfig.getLong("activity_manager", KEY_COMPACT_FULL_RSS_THROTTLE_KB, DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB);
        if (this.mFullAnonRssThrottleKb < 0) {
            this.mFullAnonRssThrottleKb = DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateFullDeltaRssThrottle() {
        this.mFullDeltaRssThrottleKb = android.provider.DeviceConfig.getLong("activity_manager", KEY_COMPACT_FULL_DELTA_RSS_THROTTLE_KB, DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB);
        if (this.mFullDeltaRssThrottleKb < 0) {
            this.mFullDeltaRssThrottleKb = DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateProcStateThrottle() {
        java.lang.String string = android.provider.DeviceConfig.getString("activity_manager", KEY_COMPACT_PROC_STATE_THROTTLE, DEFAULT_COMPACT_PROC_STATE_THROTTLE);
        if (!parseProcStateThrottle(string)) {
            android.util.Slog.w("ActivityManager", "Unable to parse app compact proc state throttle \"" + string + "\" falling back to default.");
            if (!parseProcStateThrottle(DEFAULT_COMPACT_PROC_STATE_THROTTLE)) {
                android.util.Slog.wtf("ActivityManager", "Unable to parse default app compact proc state throttle " + DEFAULT_COMPACT_PROC_STATE_THROTTLE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateMinOomAdjThrottle() {
        this.mCompactThrottleMinOomAdj = android.provider.DeviceConfig.getLong("activity_manager", KEY_COMPACT_THROTTLE_MIN_OOM_ADJ, DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ);
        if (this.mCompactThrottleMinOomAdj < DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ) {
            this.mCompactThrottleMinOomAdj = DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateMaxOomAdjThrottle() {
        this.mCompactThrottleMaxOomAdj = android.provider.DeviceConfig.getLong("activity_manager", KEY_COMPACT_THROTTLE_MAX_OOM_ADJ, DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ);
        if (this.mCompactThrottleMaxOomAdj > DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ) {
            this.mCompactThrottleMaxOomAdj = DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateFreezerDebounceTimeout() {
        this.mFreezerDebounceTimeout = android.provider.DeviceConfig.getLong("activity_manager_native_boot", KEY_FREEZER_DEBOUNCE_TIMEOUT, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        if (this.mFreezerDebounceTimeout < 0) {
            this.mFreezerDebounceTimeout = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
        }
        android.util.Slog.d("ActivityManager", "Freezer timeout set to " + this.mFreezerDebounceTimeout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateFreezerExemptInstPkg() {
        this.mFreezerExemptInstPkg = android.provider.DeviceConfig.getBoolean("activity_manager_native_boot", KEY_FREEZER_EXEMPT_INST_PKG, false);
        android.util.Slog.d("ActivityManager", "Freezer exemption set to " + this.mFreezerExemptInstPkg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPhenotypeFlagLock"})
    public void updateFreezerBinderState() {
        this.mFreezerBinderEnabled = android.provider.DeviceConfig.getBoolean("activity_manager_native_boot", KEY_FREEZER_BINDER_ENABLED, true);
        this.mFreezerBinderDivisor = android.provider.DeviceConfig.getLong("activity_manager_native_boot", KEY_FREEZER_BINDER_DIVISOR, DEFAULT_FREEZER_BINDER_DIVISOR);
        this.mFreezerBinderOffset = android.provider.DeviceConfig.getInt("activity_manager_native_boot", KEY_FREEZER_BINDER_OFFSET, 500);
        this.mFreezerBinderThreshold = android.provider.DeviceConfig.getLong("activity_manager_native_boot", KEY_FREEZER_BINDER_THRESHOLD, 1000L);
        this.mFreezerBinderCallbackEnabled = android.provider.DeviceConfig.getBoolean("activity_manager_native_boot", KEY_FREEZER_BINDER_CALLBACK_ENABLED, true);
        this.mFreezerBinderCallbackThrottle = android.provider.DeviceConfig.getLong("activity_manager_native_boot", KEY_FREEZER_BINDER_CALLBACK_THROTTLE, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        this.mFreezerBinderAsyncThreshold = android.provider.DeviceConfig.getInt("activity_manager_native_boot", KEY_FREEZER_BINDER_ASYNC_THRESHOLD, 1024);
        android.util.Slog.d("ActivityManager", "Freezer binder state set to enabled=" + this.mFreezerBinderEnabled + ", divisor=" + this.mFreezerBinderDivisor + ", offset=" + this.mFreezerBinderOffset + ", threshold=" + this.mFreezerBinderThreshold + ", callback enabled=" + this.mFreezerBinderCallbackEnabled + ", callback throttle=" + this.mFreezerBinderCallbackThrottle + ", async threshold=" + this.mFreezerBinderAsyncThreshold);
    }

    private boolean parseProcStateThrottle(java.lang.String str) {
        java.lang.String[] split = android.text.TextUtils.split(str, ",");
        this.mProcStateThrottle.clear();
        for (java.lang.String str2 : split) {
            try {
                this.mProcStateThrottle.add(java.lang.Integer.valueOf(java.lang.Integer.parseInt(str2)));
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.e("ActivityManager", "Failed to parse default app compaction proc state: " + str2);
                return false;
            }
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private long updateEarliestFreezableTime(com.android.server.am.ProcessRecord processRecord, long j) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        processRecord.mOptRecord.setEarliestFreezableTime(java.lang.Math.max(processRecord.mOptRecord.getEarliestFreezableTime(), j + uptimeMillis));
        return processRecord.mOptRecord.getEarliestFreezableTime() - uptimeMillis;
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    void unfreezeTemporarily(com.android.server.am.ProcessRecord processRecord, int i) {
        unfreezeTemporarily(processRecord, i, this.mFreezerDebounceTimeout);
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    void unfreezeTemporarily(com.android.server.am.ProcessRecord processRecord, int i, long j) {
        if (this.mUseFreezer) {
            com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    long updateEarliestFreezableTime = updateEarliestFreezableTime(processRecord, j);
                    if (processRecord.mOptRecord.isFrozen() || processRecord.mOptRecord.isPendingFreeze()) {
                        unfreezeAppLSP(processRecord, i);
                        freezeAppAsyncLSP(processRecord, updateEarliestFreezableTime);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAm", "mProcLock"})
    void freezeAppAsyncLSP(com.android.server.am.ProcessRecord processRecord) {
        freezeAppAsyncLSP(processRecord, updateEarliestFreezableTime(processRecord, this.mFreezerDebounceTimeout));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mAm", "mProcLock"})
    public void freezeAppAsyncLSP(com.android.server.am.ProcessRecord processRecord, long j) {
        freezeAppAsyncInternalLSP(processRecord, j, false);
    }

    @com.android.internal.annotations.GuardedBy({"mAm", "mProcLock"})
    void freezeAppAsyncAtEarliestLSP(com.android.server.am.ProcessRecord processRecord) {
        freezeAppAsyncLSP(processRecord, updateEarliestFreezableTime(processRecord, 0L));
    }

    @com.android.internal.annotations.GuardedBy({"mAm", "mProcLock"})
    void freezeAppAsyncInternalLSP(com.android.server.am.ProcessRecord processRecord, long j, boolean z) {
        android.app.IApplicationThread thread;
        com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        if (processCachedOptimizerRecord.isPendingFreeze()) {
            return;
        }
        if (processCachedOptimizerRecord.isFreezeSticky() && !z) {
            return;
        }
        if (this.mAm.mConstants.USE_MODERN_TRIM && processRecord.mState.getSetAdj() >= 900 && (thread = processRecord.getThread()) != null) {
            try {
                thread.scheduleTrimMemory(40);
            } catch (android.os.RemoteException e) {
            }
        }
        reportProcessFreezableChangedLocked(processRecord);
        processRecord.mOptRecord.setLastUsedTimeout(j);
        this.mFreezeHandler.sendMessageDelayed(this.mFreezeHandler.obtainMessage(3, 1, 0, processRecord), j);
        processCachedOptimizerRecord.setPendingFreeze(true);
    }

    @com.android.internal.annotations.GuardedBy({"mAm", "mProcLock", "mFreezerLock"})
    void unfreezeAppInternalLSP(com.android.server.am.ProcessRecord processRecord, int i, boolean z) {
        boolean z2;
        boolean z3;
        int pid = processRecord.getPid();
        com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        if (processCachedOptimizerRecord.isFreezeSticky() && !z) {
            return;
        }
        if (!processCachedOptimizerRecord.isPendingFreeze()) {
            z2 = false;
        } else {
            this.mFreezeHandler.removeMessages(3, processRecord);
            processCachedOptimizerRecord.setPendingFreeze(false);
            reportProcessFreezableChangedLocked(processRecord);
            z2 = true;
        }
        com.android.server.am.UidRecord uidRecord = processRecord.getUidRecord();
        if (uidRecord != null && uidRecord.isFrozen()) {
            uidRecord.setFrozen(false);
            postUidFrozenMessage(uidRecord.getUid(), false);
        }
        processCachedOptimizerRecord.setFreezerOverride(false);
        if (pid == 0 || !processCachedOptimizerRecord.isFrozen()) {
            return;
        }
        try {
            if ((getBinderFreezeInfo(pid) & 1) == 0) {
                z3 = false;
            } else {
                android.util.Slog.d("ActivityManager", "pid " + pid + " " + processRecord.processName + " received sync transactions while frozen, killing");
                processRecord.killLocked("Sync transaction while in frozen state", 14, 20, true);
                z3 = true;
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.d("ActivityManager", "Unable to query binder frozen info for pid " + pid + " " + processRecord.processName + ". Killing it. Exception: " + e);
            processRecord.killLocked("Unable to query binder frozen stats", 14, 19, true);
            z3 = true;
        }
        if (z3) {
            return;
        }
        if (!z2) {
            reportProcessFreezableChangedLocked(processRecord);
        }
        long freezeUnfreezeTime = processCachedOptimizerRecord.getFreezeUnfreezeTime();
        try {
            freezeBinder(pid, false, 0);
            try {
                traceAppFreeze(processRecord.processName, pid, i);
                android.os.Process.setProcessFrozen(pid, processRecord.uid, false);
                processCachedOptimizerRecord.setFreezeUnfreezeTime(android.os.SystemClock.uptimeMillis());
                processCachedOptimizerRecord.setFrozen(false);
                this.mFrozenProcesses.delete(pid);
            } catch (java.lang.Exception e2) {
                android.util.Slog.e("ActivityManager", "Unable to unfreeze " + pid + " " + processRecord.processName + ". This might cause inconsistency or UI hangs.");
            }
            if (!processCachedOptimizerRecord.isFrozen()) {
                android.util.Slog.d("ActivityManager", "sync unfroze " + pid + " " + processRecord.processName + " for " + i);
                this.mFreezeHandler.sendMessage(this.mFreezeHandler.obtainMessage(4, pid, (int) java.lang.Math.min(processCachedOptimizerRecord.getFreezeUnfreezeTime() - freezeUnfreezeTime, 2147483647L), new android.util.Pair(processRecord, java.lang.Integer.valueOf(i))));
            }
        } catch (java.lang.RuntimeException e3) {
            android.util.Slog.e("ActivityManager", "Unable to unfreeze binder for " + pid + " " + processRecord.processName + ". Killing it");
            processRecord.killLocked("Unable to unfreeze", 14, 19, true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAm", "mProcLock"})
    void unfreezeAppLSP(com.android.server.am.ProcessRecord processRecord, int i) {
        synchronized (this.mFreezerLock) {
            unfreezeAppInternalLSP(processRecord, i, false);
        }
    }

    void unfreezeProcess(int i, int i2) {
        synchronized (this.mFreezerLock) {
            try {
                com.android.server.am.ProcessRecord processRecord = this.mFrozenProcesses.get(i);
                if (processRecord == null) {
                    return;
                }
                android.util.Slog.d("ActivityManager", "quick sync unfreeze " + i + " for " + i2);
                try {
                    freezeBinder(i, false, 0);
                    try {
                        traceAppFreeze(processRecord.processName, i, i2);
                        android.os.Process.setProcessFrozen(i, processRecord.uid, false);
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e("ActivityManager", "Unable to quick unfreeze " + i);
                    }
                } catch (java.lang.RuntimeException e2) {
                    android.util.Slog.e("ActivityManager", "Unable to quick unfreeze binder for " + i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void traceAppFreeze(java.lang.String str, int i, int i2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(i2 < 0 ? "Freeze " : "Unfreeze ");
        sb.append(str);
        sb.append(":");
        sb.append(i);
        sb.append(" ");
        sb.append(i2);
        android.os.Trace.instantForTrack(64L, ATRACE_FREEZER_TRACK, sb.toString());
    }

    @com.android.internal.annotations.GuardedBy({"mAm", "mProcLock"})
    void onCleanupApplicationRecordLocked(com.android.server.am.ProcessRecord processRecord) {
        if (this.mUseFreezer) {
            com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            boolean z = false;
            if (processCachedOptimizerRecord.isPendingFreeze()) {
                this.mFreezeHandler.removeMessages(3, processRecord);
                processCachedOptimizerRecord.setPendingFreeze(false);
            }
            com.android.server.am.UidRecord uidRecord = processRecord.getUidRecord();
            if (uidRecord != null) {
                if (uidRecord.getNumOfProcs() > 1 && uidRecord.areAllProcessesFrozen(processRecord)) {
                    z = true;
                }
                if (z != uidRecord.isFrozen()) {
                    uidRecord.setFrozen(z);
                    postUidFrozenMessage(uidRecord.getUid(), z);
                }
            }
            this.mFrozenProcesses.delete(processRecord.getPid());
        }
    }

    void onWakefulnessChanged(int i) {
        if (i == 1 && useCompaction()) {
            cancelAllCompactions(com.android.server.am.CachedAppOptimizer.CancelCompactReason.SCREEN_ON);
        }
    }

    void cancelAllCompactions(com.android.server.am.CachedAppOptimizer.CancelCompactReason cancelCompactReason) {
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            while (!this.mPendingCompactionProcesses.isEmpty()) {
                try {
                    cancelCompactionForProcess(this.mPendingCompactionProcesses.get(0), cancelCompactReason);
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            this.mPendingCompactionProcesses.clear();
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void cancelCompactionForProcess(com.android.server.am.ProcessRecord processRecord, com.android.server.am.CachedAppOptimizer.CancelCompactReason cancelCompactReason) {
        boolean z = false;
        if (this.mPendingCompactionProcesses.contains(processRecord)) {
            processRecord.mOptRecord.setHasPendingCompact(false);
            this.mPendingCompactionProcesses.remove(processRecord);
            z = true;
        }
        if (com.android.server.am.CachedAppOptimizer.DefaultProcessDependencies.mPidCompacting == processRecord.mPid) {
            cancelCompaction();
            z = true;
        }
        if (z) {
            if (this.mTotalCompactionsCancelled.containsKey(cancelCompactReason)) {
                this.mTotalCompactionsCancelled.put((java.util.EnumMap<com.android.server.am.CachedAppOptimizer.CancelCompactReason, java.lang.Integer>) cancelCompactReason, (com.android.server.am.CachedAppOptimizer.CancelCompactReason) java.lang.Integer.valueOf(this.mTotalCompactionsCancelled.get(cancelCompactReason).intValue() + 1));
            } else {
                this.mTotalCompactionsCancelled.put((java.util.EnumMap<com.android.server.am.CachedAppOptimizer.CancelCompactReason, java.lang.Integer>) cancelCompactReason, (com.android.server.am.CachedAppOptimizer.CancelCompactReason) 1);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void onOomAdjustChanged(int i, int i2, com.android.server.am.ProcessRecord processRecord) {
        if (useCompaction() && i2 < i && i2 < 900) {
            cancelCompactionForProcess(processRecord, com.android.server.am.CachedAppOptimizer.CancelCompactReason.OOM_IMPROVEMENT);
        }
    }

    void onProcessFrozen(com.android.server.am.ProcessRecord processRecord) {
        if (useCompaction()) {
            com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    compactApp(processRecord, com.android.server.am.CachedAppOptimizer.CompactProfile.FULL, com.android.server.am.CachedAppOptimizer.CompactSource.APP, false);
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
        processRecord.onProcessFrozen();
    }

    void onProcessFrozenCancelled(com.android.server.am.ProcessRecord processRecord) {
        processRecord.onProcessFrozenCancelled();
    }

    com.android.server.am.CachedAppOptimizer.CompactProfile resolveCompactionProfile(com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile) {
        if (compactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.FULL && getFreeSwapPercent() < COMPACT_DOWNGRADE_FREE_SWAP_THRESHOLD) {
            compactProfile = com.android.server.am.CachedAppOptimizer.CompactProfile.SOME;
            this.mTotalCompactionDowngrades++;
        }
        if (compactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.SOME) {
            return com.android.server.am.CachedAppOptimizer.CompactProfile.NONE;
        }
        if (compactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.FULL) {
            return com.android.server.am.CachedAppOptimizer.CompactProfile.ANON;
        }
        return compactProfile;
    }

    boolean isProcessFrozen(int i) {
        boolean contains;
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                contains = this.mFrozenProcesses.contains(i);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        return contains;
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class SingleCompactionStats {
        private static final float STATSD_SAMPLE_RATE = 0.1f;
        private static final java.util.Random mRandom = new java.util.Random();
        public long mAnonMemFreedKBs;
        public float mCpuTimeMillis;
        public long mDeltaAnonRssKBs;
        public int mOomAdj;
        public int mOomAdjReason;
        public long mOrigAnonRss;
        public int mProcState;
        public java.lang.String mProcessName;
        private final long[] mRssAfterCompaction;
        public com.android.server.am.CachedAppOptimizer.CompactSource mSourceType;
        public final int mUid;
        public long mZramConsumedKBs;

        SingleCompactionStats(long[] jArr, com.android.server.am.CachedAppOptimizer.CompactSource compactSource, java.lang.String str, long j, long j2, long j3, long j4, long j5, int i, int i2, int i3, int i4) {
            this.mRssAfterCompaction = jArr;
            this.mSourceType = compactSource;
            this.mProcessName = str;
            this.mUid = i4;
            this.mDeltaAnonRssKBs = j;
            this.mZramConsumedKBs = j2;
            this.mAnonMemFreedKBs = j3;
            this.mCpuTimeMillis = j5;
            this.mOrigAnonRss = j4;
            this.mProcState = i;
            this.mOomAdj = i2;
            this.mOomAdjReason = i3;
        }

        double getCompactEfficiency() {
            return this.mAnonMemFreedKBs / this.mOrigAnonRss;
        }

        double getCompactCost() {
            return (this.mCpuTimeMillis / this.mAnonMemFreedKBs) * 1024.0d;
        }

        long[] getRssAfterCompaction() {
            return this.mRssAfterCompaction;
        }

        void dump(java.io.PrintWriter printWriter) {
            printWriter.println("    (" + this.mProcessName + "," + this.mSourceType.name() + "," + this.mDeltaAnonRssKBs + "," + this.mZramConsumedKBs + "," + this.mAnonMemFreedKBs + "," + getCompactEfficiency() + "," + getCompactCost() + "," + this.mProcState + "," + this.mOomAdj + "," + com.android.server.am.OomAdjuster.oomAdjReasonToString(this.mOomAdjReason) + ")");
        }

        void sendStat() {
            if (mRandom.nextFloat() < STATSD_SAMPLE_RATE) {
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.APP_COMPACTED_V2, this.mUid, this.mProcState, this.mOomAdj, this.mDeltaAnonRssKBs, this.mZramConsumedKBs, this.mCpuTimeMillis, this.mOrigAnonRss, this.mOomAdjReason);
            }
        }
    }

    private final class MemCompactionHandler extends android.os.Handler {
        private MemCompactionHandler() {
            super(com.android.server.am.CachedAppOptimizer.this.mCachedAppOptimizerThread.getLooper());
        }

        private boolean shouldOomAdjThrottleCompaction(com.android.server.am.ProcessRecord processRecord) {
            java.lang.String str = processRecord.processName;
            if (processRecord.mState.getSetAdj() <= 200) {
                return true;
            }
            return false;
        }

        private boolean shouldTimeThrottleCompaction(com.android.server.am.ProcessRecord processRecord, long j, com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile, com.android.server.am.CachedAppOptimizer.CompactSource compactSource) {
            com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            com.android.server.am.CachedAppOptimizer.CompactProfile lastCompactProfile = processCachedOptimizerRecord.getLastCompactProfile();
            long lastCompactTime = processCachedOptimizerRecord.getLastCompactTime();
            if (lastCompactTime != 0 && compactSource == com.android.server.am.CachedAppOptimizer.CompactSource.APP) {
                if (compactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.SOME) {
                    return (lastCompactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.SOME && j - lastCompactTime < com.android.server.am.CachedAppOptimizer.this.mCompactThrottleSomeSome) || (lastCompactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.FULL && j - lastCompactTime < com.android.server.am.CachedAppOptimizer.this.mCompactThrottleSomeFull);
                }
                if (compactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.FULL) {
                    return (lastCompactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.SOME && j - lastCompactTime < com.android.server.am.CachedAppOptimizer.this.mCompactThrottleFullSome) || (lastCompactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.FULL && j - lastCompactTime < com.android.server.am.CachedAppOptimizer.this.mCompactThrottleFullFull);
                }
                return false;
            }
            return false;
        }

        private boolean shouldThrottleMiscCompaction(com.android.server.am.ProcessRecord processRecord, int i) {
            if (com.android.server.am.CachedAppOptimizer.this.mProcStateThrottle.contains(java.lang.Integer.valueOf(i))) {
                return true;
            }
            return false;
        }

        private boolean shouldRssThrottleCompaction(com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile, int i, java.lang.String str, long[] jArr) {
            long j = jArr[2];
            com.android.server.am.CachedAppOptimizer.SingleCompactionStats singleCompactionStats = com.android.server.am.CachedAppOptimizer.this.mLastCompactionStats.get(java.lang.Integer.valueOf(i));
            if (jArr[0] == 0 && jArr[1] == 0 && jArr[2] == 0 && jArr[3] == 0) {
                return true;
            }
            if (compactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.FULL) {
                if (com.android.server.am.CachedAppOptimizer.this.mFullAnonRssThrottleKb > 0 && j < com.android.server.am.CachedAppOptimizer.this.mFullAnonRssThrottleKb) {
                    return true;
                }
                if (singleCompactionStats != null && com.android.server.am.CachedAppOptimizer.this.mFullDeltaRssThrottleKb > 0) {
                    long[] rssAfterCompaction = singleCompactionStats.getRssAfterCompaction();
                    if (java.lang.Math.abs(jArr[1] - rssAfterCompaction[1]) + java.lang.Math.abs(jArr[2] - rssAfterCompaction[2]) + java.lang.Math.abs(jArr[3] - rssAfterCompaction[3]) <= com.android.server.am.CachedAppOptimizer.this.mFullDeltaRssThrottleKb) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord;
            com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile;
            com.android.server.am.ProcessRecord processRecord;
            long[] rss;
            char c;
            int i;
            switch (message.what) {
                case 1:
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    int i2 = message.arg1;
                    int i3 = message.arg2;
                    com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = com.android.server.am.CachedAppOptimizer.this.mProcLock;
                    com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                    synchronized (activityManagerGlobalLock) {
                        try {
                            if (com.android.server.am.CachedAppOptimizer.this.mPendingCompactionProcesses.isEmpty()) {
                                return;
                            }
                            com.android.server.am.ProcessRecord processRecord2 = (com.android.server.am.ProcessRecord) com.android.server.am.CachedAppOptimizer.this.mPendingCompactionProcesses.remove(0);
                            com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord2 = processRecord2.mOptRecord;
                            boolean isForceCompact = processCachedOptimizerRecord2.isForceCompact();
                            processCachedOptimizerRecord2.setForceCompact(false);
                            int pid = processRecord2.getPid();
                            java.lang.String str = processRecord2.processName;
                            processCachedOptimizerRecord2.setHasPendingCompact(false);
                            com.android.server.am.CachedAppOptimizer.CompactSource reqCompactSource = processCachedOptimizerRecord2.getReqCompactSource();
                            com.android.server.am.CachedAppOptimizer.CompactProfile reqCompactProfile = processCachedOptimizerRecord2.getReqCompactProfile();
                            com.android.server.am.CachedAppOptimizer.CompactProfile lastCompactProfile = processCachedOptimizerRecord2.getLastCompactProfile();
                            long lastCompactTime = processCachedOptimizerRecord2.getLastCompactTime();
                            int lastOomAdjChangeReason = processCachedOptimizerRecord2.getLastOomAdjChangeReason();
                            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                            com.android.server.am.CachedAppOptimizer.AggregatedSourceCompactionStats perSourceAggregatedCompactStat = com.android.server.am.CachedAppOptimizer.this.getPerSourceAggregatedCompactStat(processCachedOptimizerRecord2.getReqCompactSource());
                            com.android.server.am.CachedAppOptimizer.AggregatedProcessCompactionStats perProcessAggregatedCompactStat = com.android.server.am.CachedAppOptimizer.this.getPerProcessAggregatedCompactStat(str);
                            if (pid == 0) {
                                perSourceAggregatedCompactStat.mProcCompactionsNoPidThrottled++;
                                perProcessAggregatedCompactStat.mProcCompactionsNoPidThrottled++;
                                return;
                            }
                            if (isForceCompact) {
                                processCachedOptimizerRecord = processCachedOptimizerRecord2;
                                compactProfile = reqCompactProfile;
                                processRecord = processRecord2;
                                rss = com.android.server.am.CachedAppOptimizer.this.mProcessDependencies.getRss(pid);
                            } else {
                                if (shouldOomAdjThrottleCompaction(processRecord2)) {
                                    perProcessAggregatedCompactStat.mProcCompactionsOomAdjThrottled++;
                                    perSourceAggregatedCompactStat.mProcCompactionsOomAdjThrottled++;
                                    return;
                                }
                                processCachedOptimizerRecord = processCachedOptimizerRecord2;
                                if (shouldTimeThrottleCompaction(processRecord2, uptimeMillis, reqCompactProfile, reqCompactSource)) {
                                    perProcessAggregatedCompactStat.mProcCompactionsTimeThrottled++;
                                    perSourceAggregatedCompactStat.mProcCompactionsTimeThrottled++;
                                    return;
                                }
                                processRecord = processRecord2;
                                if (shouldThrottleMiscCompaction(processRecord, i3)) {
                                    perProcessAggregatedCompactStat.mProcCompactionsMiscThrottled++;
                                    perSourceAggregatedCompactStat.mProcCompactionsMiscThrottled++;
                                    return;
                                }
                                pid = pid;
                                rss = com.android.server.am.CachedAppOptimizer.this.mProcessDependencies.getRss(pid);
                                compactProfile = reqCompactProfile;
                                str = str;
                                if (shouldRssThrottleCompaction(compactProfile, pid, str, rss)) {
                                    perProcessAggregatedCompactStat.mProcCompactionsRSSThrottled++;
                                    perSourceAggregatedCompactStat.mProcCompactionsRSSThrottled++;
                                    return;
                                }
                            }
                            com.android.server.am.CachedAppOptimizer.CompactProfile resolveCompactionProfile = com.android.server.am.CachedAppOptimizer.this.resolveCompactionProfile(compactProfile);
                            try {
                                if (resolveCompactionProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.NONE) {
                                    return;
                                }
                                try {
                                    android.os.Trace.traceBegin(64L, "Compact " + resolveCompactionProfile.name() + ": " + str + " lastOomAdjReason: " + lastOomAdjChangeReason + " source: " + reqCompactSource.name());
                                    long usedZramMemory = com.android.server.am.CachedAppOptimizer.getUsedZramMemory();
                                    long threadCpuTimeNs = com.android.server.am.CachedAppOptimizer.threadCpuTimeNs();
                                    com.android.server.am.CachedAppOptimizer.this.mProcessDependencies.performCompaction(resolveCompactionProfile, pid);
                                    long threadCpuTimeNs2 = com.android.server.am.CachedAppOptimizer.threadCpuTimeNs();
                                    long[] rss2 = com.android.server.am.CachedAppOptimizer.this.mProcessDependencies.getRss(pid);
                                    com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile2 = compactProfile;
                                    long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
                                    long j = uptimeMillis2 - uptimeMillis;
                                    long j2 = threadCpuTimeNs2 - threadCpuTimeNs;
                                    long usedZramMemory2 = com.android.server.am.CachedAppOptimizer.getUsedZramMemory();
                                    long j3 = rss2[0] - rss[0];
                                    long j4 = rss2[1] - rss[1];
                                    long j5 = rss2[2] - rss[2];
                                    long j6 = rss2[3] - rss[3];
                                    switch (com.android.server.am.CachedAppOptimizer.AnonymousClass5.$SwitchMap$com$android$server$am$CachedAppOptimizer$CompactProfile[processCachedOptimizerRecord.getReqCompactProfile().ordinal()]) {
                                        case 1:
                                            c = 0;
                                            i = i2;
                                            perSourceAggregatedCompactStat.mSomeCompactPerformed++;
                                            perProcessAggregatedCompactStat.mSomeCompactPerformed++;
                                            break;
                                        case 2:
                                            perSourceAggregatedCompactStat.mFullCompactPerformed++;
                                            perProcessAggregatedCompactStat.mFullCompactPerformed++;
                                            long j7 = -j5;
                                            long j8 = usedZramMemory2 - usedZramMemory;
                                            long j9 = j7 - j8;
                                            long j10 = j2 / 1000000;
                                            long j11 = rss[2];
                                            long j12 = j7 > 0 ? j7 : 0L;
                                            long j13 = j8 > 0 ? j8 : 0L;
                                            long j14 = j9 > 0 ? j9 : 0L;
                                            perProcessAggregatedCompactStat.addMemStats(j12, j13, j14, j11, j10);
                                            perSourceAggregatedCompactStat.addMemStats(j12, j13, j14, j11, j10);
                                            c = 0;
                                            i = i2;
                                            com.android.server.am.CachedAppOptimizer.SingleCompactionStats singleCompactionStats = new com.android.server.am.CachedAppOptimizer.SingleCompactionStats(rss2, reqCompactSource, str, j12, j13, j14, j11, j10, i3, i, lastOomAdjChangeReason, processRecord.uid);
                                            com.android.server.am.CachedAppOptimizer.this.mLastCompactionStats.remove(java.lang.Integer.valueOf(pid));
                                            com.android.server.am.CachedAppOptimizer.this.mLastCompactionStats.put(java.lang.Integer.valueOf(pid), singleCompactionStats);
                                            com.android.server.am.CachedAppOptimizer.this.mCompactionStatsHistory.add(singleCompactionStats);
                                            if (!isForceCompact) {
                                                singleCompactionStats.sendStat();
                                                break;
                                            }
                                            break;
                                        default:
                                            android.util.Slog.wtf("ActivityManager", "Compaction: Unknown requested action");
                                            return;
                                    }
                                    android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_COMPACT, java.lang.Integer.valueOf(pid), str, resolveCompactionProfile.name(), java.lang.Long.valueOf(rss[c]), java.lang.Long.valueOf(rss[1]), java.lang.Long.valueOf(rss[2]), java.lang.Long.valueOf(rss[3]), java.lang.Long.valueOf(j3), java.lang.Long.valueOf(j4), java.lang.Long.valueOf(j5), java.lang.Long.valueOf(j6), java.lang.Long.valueOf(j), lastCompactProfile.name(), java.lang.Long.valueOf(lastCompactTime), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i3), java.lang.Long.valueOf(usedZramMemory), java.lang.Long.valueOf(usedZramMemory - usedZramMemory2));
                                    com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock2 = com.android.server.am.CachedAppOptimizer.this.mProcLock;
                                    com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                                    synchronized (activityManagerGlobalLock2) {
                                        com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord3 = processCachedOptimizerRecord;
                                        try {
                                            processCachedOptimizerRecord3.setLastCompactTime(uptimeMillis2);
                                            processCachedOptimizerRecord3.setLastCompactProfile(compactProfile2);
                                        } finally {
                                        }
                                    }
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                                } catch (java.lang.Exception e) {
                                    android.util.Slog.d("ActivityManager", "Exception occurred while compacting pid: " + str + ". Exception:" + e.getMessage());
                                }
                                return;
                            } finally {
                                android.os.Trace.traceEnd(64L);
                            }
                        } finally {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                        }
                    }
                case 2:
                    com.android.server.am.CachedAppOptimizer.this.mSystemCompactionsPerformed++;
                    android.os.Trace.traceBegin(64L, "compactSystem");
                    long memoryFreedCompaction = com.android.server.am.CachedAppOptimizer.getMemoryFreedCompaction();
                    com.android.server.am.CachedAppOptimizer.this.compactSystem();
                    long memoryFreedCompaction2 = com.android.server.am.CachedAppOptimizer.getMemoryFreedCompaction();
                    com.android.server.am.CachedAppOptimizer.this.mSystemTotalMemFreed += memoryFreedCompaction2 - memoryFreedCompaction;
                    android.os.Trace.traceEnd(64L);
                    return;
                case 3:
                case 4:
                default:
                    return;
                case 5:
                    int i4 = message.arg1;
                    com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile3 = com.android.server.am.CachedAppOptimizer.CompactProfile.values()[message.arg2];
                    android.util.Slog.d("ActivityManager", "Performing native compaction for pid=" + i4 + " type=" + compactProfile3.name());
                    android.os.Trace.traceBegin(64L, "compactSystem");
                    try {
                        com.android.server.am.CachedAppOptimizer.this.mProcessDependencies.performCompaction(compactProfile3, i4);
                    } catch (java.lang.Exception e2) {
                        android.util.Slog.d("ActivityManager", "Failed compacting native pid= " + i4);
                    }
                    android.os.Trace.traceEnd(64L);
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportOneUidFrozenStateChanged(int i, boolean z) {
        this.mAm.reportUidFrozenStateChanged(new int[]{i}, new int[]{z ? 1 : 2});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postUidFrozenMessage(int i, boolean z) {
        java.lang.Integer valueOf = java.lang.Integer.valueOf(i);
        this.mFreezeHandler.removeEqualMessages(6, valueOf);
        this.mFreezeHandler.sendMessage(this.mFreezeHandler.obtainMessage(6, z ? 1 : 0, 0, valueOf));
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    private void reportProcessFreezableChangedLocked(com.android.server.am.ProcessRecord processRecord) {
        this.mAm.onProcessFreezableChangedLocked(processRecord);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class FreezeHandler extends android.os.Handler implements com.android.internal.os.ProcLocksReader.ProcLocksReaderCallback {
        private FreezeHandler() {
            super(com.android.server.am.CachedAppOptimizer.this.mCachedAppOptimizerThread.getLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 3:
                    com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) message.obj;
                    com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.CachedAppOptimizer.this.mAm;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            freezeProcess(processRecord);
                        } catch (java.lang.Throwable th) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    if (processRecord.mOptRecord.isFrozen()) {
                        com.android.server.am.CachedAppOptimizer.this.onProcessFrozen(processRecord);
                        removeMessages(7);
                        sendEmptyMessageDelayed(7, 1000L);
                        return;
                    }
                    com.android.server.am.CachedAppOptimizer.this.onProcessFrozenCancelled(processRecord);
                    return;
                case 4:
                    int i = message.arg1;
                    int i2 = message.arg2;
                    android.util.Pair pair = (android.util.Pair) message.obj;
                    com.android.server.am.ProcessRecord processRecord2 = (com.android.server.am.ProcessRecord) pair.first;
                    reportUnfreeze(processRecord2, i, i2, processRecord2.processName, ((java.lang.Integer) pair.second).intValue());
                    return;
                case 5:
                default:
                    return;
                case 6:
                    com.android.server.am.CachedAppOptimizer.this.reportOneUidFrozenStateChanged(((java.lang.Integer) message.obj).intValue(), message.arg1 == 1);
                    return;
                case 7:
                    try {
                        com.android.server.am.CachedAppOptimizer.this.mProcLocksReader.handleBlockingFileLocks(this);
                        return;
                    } catch (java.io.IOException e) {
                        android.util.Slog.w("ActivityManager", "Unable to check file locks");
                        return;
                    }
                case 8:
                    android.util.IntArray intArray = new android.util.IntArray();
                    com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = com.android.server.am.CachedAppOptimizer.this.mProcLock;
                    com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                    synchronized (activityManagerGlobalLock) {
                        try {
                            int size = com.android.server.am.CachedAppOptimizer.this.mFrozenProcesses.size();
                            for (int i3 = 0; i3 < size; i3++) {
                                intArray.add(com.android.server.am.CachedAppOptimizer.this.mFrozenProcesses.keyAt(i3));
                            }
                        } catch (java.lang.Throwable th2) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                            throw th2;
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    com.android.server.am.CachedAppOptimizer.this.binderErrorInternal(intArray);
                    return;
            }
        }

        @com.android.internal.annotations.GuardedBy({"mAm", "mProcLock"})
        private void handleBinderFreezerFailure(final com.android.server.am.ProcessRecord processRecord, java.lang.String str) {
            if (!com.android.server.am.CachedAppOptimizer.this.mFreezerBinderEnabled) {
                com.android.server.am.CachedAppOptimizer.this.unfreezeAppLSP(processRecord, 18);
                com.android.server.am.CachedAppOptimizer.this.freezeAppAsyncLSP(processRecord);
                return;
            }
            if (processRecord.mOptRecord.getLastUsedTimeout() <= com.android.server.am.CachedAppOptimizer.this.mFreezerBinderThreshold) {
                android.util.Slog.d("ActivityManager", "Kill app due to repeated failure to freeze binder: " + processRecord.getPid() + " " + processRecord.processName);
                com.android.server.am.CachedAppOptimizer.this.mAm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.CachedAppOptimizer$FreezeHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.CachedAppOptimizer.FreezeHandler.this.lambda$handleBinderFreezerFailure$0(processRecord);
                    }
                });
                return;
            }
            long max = java.lang.Math.max((processRecord.mOptRecord.getLastUsedTimeout() / com.android.server.am.CachedAppOptimizer.this.mFreezerBinderDivisor) + (com.android.server.am.CachedAppOptimizer.this.mRandom.nextInt(com.android.server.am.CachedAppOptimizer.this.mFreezerBinderOffset * 2) - com.android.server.am.CachedAppOptimizer.this.mFreezerBinderOffset), com.android.server.am.CachedAppOptimizer.this.mFreezerBinderThreshold);
            android.util.Slog.d("ActivityManager", "Reschedule freeze for process " + processRecord.getPid() + " " + processRecord.processName + " (" + str + "), timeout=" + max);
            android.os.Trace.instantForTrack(64L, com.android.server.am.CachedAppOptimizer.ATRACE_FREEZER_TRACK, "Reschedule freeze " + processRecord.processName + ":" + processRecord.getPid() + " timeout=" + max + ", reason=" + str);
            com.android.server.am.CachedAppOptimizer.this.unfreezeAppLSP(processRecord, 18);
            com.android.server.am.CachedAppOptimizer.this.freezeAppAsyncLSP(processRecord, max);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleBinderFreezerFailure$0(com.android.server.am.ProcessRecord processRecord) {
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.CachedAppOptimizer.this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    if (processRecord.getThread() == null) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    } else {
                        processRecord.killLocked("excessive binder traffic during cached", 9, 7, true);
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mAm"})
        private void freezeProcess(final com.android.server.am.ProcessRecord processRecord) {
            processRecord.getPid();
            java.lang.String str = processRecord.processName;
            com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = com.android.server.am.CachedAppOptimizer.this.mProcLock;
            com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    if (processCachedOptimizerRecord.isPendingFreeze()) {
                        processCachedOptimizerRecord.setPendingFreeze(false);
                        int pid = processRecord.getPid();
                        if (com.android.server.am.CachedAppOptimizer.this.mFreezerOverride) {
                            processCachedOptimizerRecord.setFreezerOverride(true);
                            android.util.Slog.d("ActivityManager", "Skipping freeze for process " + pid + " " + str + " curAdj = " + processRecord.mState.getCurAdj() + "(override)");
                            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                            return;
                        }
                        if (pid == 0 || processCachedOptimizerRecord.isFrozen()) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                            return;
                        }
                        android.util.Slog.d("ActivityManager", "freezing " + pid + " " + str);
                        try {
                            if (com.android.server.am.CachedAppOptimizer.freezeBinder(pid, true, 0) != 0) {
                                handleBinderFreezerFailure(processRecord, "outstanding txns");
                                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                                return;
                            }
                        } catch (java.lang.RuntimeException e) {
                            android.util.Slog.e("ActivityManager", "Unable to freeze binder for " + pid + " " + str);
                            com.android.server.am.CachedAppOptimizer.this.mFreezeHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.CachedAppOptimizer$FreezeHandler$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    com.android.server.am.CachedAppOptimizer.FreezeHandler.this.lambda$freezeProcess$1(processRecord);
                                }
                            });
                        }
                        long freezeUnfreezeTime = processCachedOptimizerRecord.getFreezeUnfreezeTime();
                        try {
                            com.android.server.am.CachedAppOptimizer.traceAppFreeze(processRecord.processName, pid, -1);
                            android.os.Process.setProcessFrozen(pid, processRecord.uid, true);
                            processCachedOptimizerRecord.setFreezeUnfreezeTime(android.os.SystemClock.uptimeMillis());
                            processCachedOptimizerRecord.setFrozen(true);
                            processCachedOptimizerRecord.setHasCollectedFrozenPSS(false);
                            com.android.server.am.CachedAppOptimizer.this.mFrozenProcesses.put(pid, processRecord);
                        } catch (java.lang.Exception e2) {
                            android.util.Slog.w("ActivityManager", "Unable to freeze " + pid + " " + str);
                        }
                        long freezeUnfreezeTime2 = processCachedOptimizerRecord.getFreezeUnfreezeTime() - freezeUnfreezeTime;
                        boolean isFrozen = processCachedOptimizerRecord.isFrozen();
                        com.android.server.am.UidRecord uidRecord = processRecord.getUidRecord();
                        if (isFrozen && uidRecord != null && uidRecord.areAllProcessesFrozen()) {
                            uidRecord.setFrozen(true);
                            com.android.server.am.CachedAppOptimizer.this.postUidFrozenMessage(uidRecord.getUid(), true);
                        }
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                        if (!isFrozen) {
                            return;
                        }
                        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_FREEZE, java.lang.Integer.valueOf(pid), str);
                        if (com.android.server.am.CachedAppOptimizer.this.mRandom.nextFloat() < com.android.server.am.CachedAppOptimizer.this.mFreezerStatsdSampleRate) {
                            com.android.internal.util.FrameworkStatsLog.write(254, 1, pid, str, freezeUnfreezeTime2, 0, 0);
                        }
                        try {
                            if ((com.android.server.am.CachedAppOptimizer.getBinderFreezeInfo(pid) & 4) != 0) {
                                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock2 = com.android.server.am.CachedAppOptimizer.this.mProcLock;
                                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                                synchronized (activityManagerGlobalLock2) {
                                    try {
                                        handleBinderFreezerFailure(processRecord, "new pending txns");
                                    } finally {
                                    }
                                }
                                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                            }
                        } catch (java.lang.RuntimeException e3) {
                            android.util.Slog.e("ActivityManager", "Unable to freeze binder for " + pid + " " + str);
                            com.android.server.am.CachedAppOptimizer.this.mFreezeHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.CachedAppOptimizer$FreezeHandler$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    com.android.server.am.CachedAppOptimizer.FreezeHandler.this.lambda$freezeProcess$2(processRecord);
                                }
                            });
                        }
                    }
                } finally {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$freezeProcess$1(com.android.server.am.ProcessRecord processRecord) {
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.CachedAppOptimizer.this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    processRecord.killLocked("Unable to freeze binder interface", 14, 19, true);
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$freezeProcess$2(com.android.server.am.ProcessRecord processRecord) {
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.CachedAppOptimizer.this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    processRecord.killLocked("Unable to freeze binder interface", 14, 19, true);
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }

        private void reportUnfreeze(com.android.server.am.ProcessRecord processRecord, int i, int i2, java.lang.String str, int i3) {
            android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_UNFREEZE, java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i3));
            processRecord.onProcessUnfrozen();
            if (com.android.server.am.CachedAppOptimizer.this.mRandom.nextFloat() < com.android.server.am.CachedAppOptimizer.this.mFreezerStatsdSampleRate) {
                com.android.internal.util.FrameworkStatsLog.write(254, 2, i, str, i2, 0, i3);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mAm"})
        public void onBlockingFileLock(android.util.IntArray intArray) {
            com.android.server.am.ProcessRecord processRecord;
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.CachedAppOptimizer.this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = com.android.server.am.CachedAppOptimizer.this.mProcLock;
                    com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                    synchronized (activityManagerGlobalLock) {
                        try {
                            int i = intArray.get(0);
                            com.android.server.am.ProcessRecord processRecord2 = (com.android.server.am.ProcessRecord) com.android.server.am.CachedAppOptimizer.this.mFrozenProcesses.get(i);
                            if (processRecord2 != null) {
                                int i2 = 1;
                                while (true) {
                                    if (i2 >= intArray.size()) {
                                        break;
                                    }
                                    int i3 = intArray.get(i2);
                                    synchronized (com.android.server.am.CachedAppOptimizer.this.mAm.mPidsSelfLocked) {
                                        processRecord = com.android.server.am.CachedAppOptimizer.this.mAm.mPidsSelfLocked.get(i3);
                                    }
                                    if (processRecord == null || processRecord.mState.getCurAdj() >= 900) {
                                        i2++;
                                    } else {
                                        android.util.Slog.d("ActivityManager", processRecord2.processName + " (" + i + ") blocks " + processRecord.processName + " (" + i3 + ")");
                                        com.android.server.am.CachedAppOptimizer.this.unfreezeAppLSP(processRecord2, 16);
                                        break;
                                    }
                                }
                            }
                        } catch (java.lang.Throwable th) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                } catch (java.lang.Throwable th2) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th2;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    private static final class DefaultProcessDependencies implements com.android.server.am.CachedAppOptimizer.ProcessDependencies {
        public static volatile int mPidCompacting = -1;

        private DefaultProcessDependencies() {
        }

        @Override // com.android.server.am.CachedAppOptimizer.ProcessDependencies
        public long[] getRss(int i) {
            return android.os.Process.getRss(i);
        }

        @Override // com.android.server.am.CachedAppOptimizer.ProcessDependencies
        public void performCompaction(com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile, int i) throws java.io.IOException {
            mPidCompacting = i;
            if (compactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.FULL) {
                com.android.server.am.CachedAppOptimizer.compactProcess(i, 3);
            } else if (compactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.SOME) {
                com.android.server.am.CachedAppOptimizer.compactProcess(i, 1);
            } else if (compactProfile == com.android.server.am.CachedAppOptimizer.CompactProfile.ANON) {
                com.android.server.am.CachedAppOptimizer.compactProcess(i, 2);
            }
            mPidCompacting = -1;
        }
    }

    static int getUnfreezeReasonCodeFromOomAdjReason(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
            case 10:
                return 10;
            case 11:
                return 11;
            case 12:
                return 12;
            case 13:
                return 20;
            case 14:
                return 21;
            case 15:
                return 22;
            case 16:
                return 23;
            case 17:
                return 24;
            case 18:
                return 25;
            case 19:
                return 26;
            case 20:
                return 27;
            case 21:
                return 28;
            case 22:
                return 29;
            default:
                return 0;
        }
    }

    public void killProcess(final int i, final java.lang.String str, final int i2, final int i3) {
        this.mAm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.CachedAppOptimizer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.CachedAppOptimizer.this.lambda$killProcess$2(i, str, i2, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$killProcess$2(int i, java.lang.String str, int i2, int i3) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        com.android.server.am.ProcessRecord processRecord = this.mFrozenProcesses.get(i);
                        if (processRecord != null && processRecord.getThread() != null && !processRecord.isKilledByAm()) {
                            processRecord.killLocked(str, i2, i3, true);
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (java.lang.Throwable th2) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public void binderError(int i, com.android.server.am.ProcessRecord processRecord, int i2, int i3, int i4) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("pid ");
        sb.append(i);
        sb.append(" ");
        sb.append(processRecord == null ? "null" : processRecord.processName);
        sb.append(" sent binder code ");
        sb.append(i2);
        sb.append(" with flags ");
        sb.append(i3);
        sb.append(" to frozen apps and got error ");
        sb.append(i4);
        android.util.Slog.w("ActivityManager", sb.toString());
        if (!this.mUseFreezer || !this.mFreezerBinderCallbackEnabled) {
            return;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (uptimeMillis < this.mFreezerBinderCallbackLast + this.mFreezerBinderCallbackThrottle) {
            android.util.Slog.d("ActivityManager", "Too many transaction errors, throttling freezer binder callback.");
        } else {
            this.mFreezerBinderCallbackLast = uptimeMillis;
            this.mFreezeHandler.sendEmptyMessage(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void binderErrorInternal(android.util.IntArray intArray) {
        final android.util.ArraySet arraySet = this.mFreezerBinderAsyncThreshold < 0 ? null : new android.util.ArraySet();
        for (int i = 0; i < intArray.size(); i++) {
            int i2 = intArray.get(i);
            try {
                int binderFreezeInfo = getBinderFreezeInfo(i2);
                if ((binderFreezeInfo & 1) != 0) {
                    killProcess(i2, "Sync transaction while frozen", 14, 20);
                } else if ((binderFreezeInfo & 2) != 0 && arraySet != null) {
                    arraySet.add(java.lang.Integer.valueOf(i2));
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.w("ActivityManager", "Unable to query binder frozen stats for pid " + i2);
            }
        }
        if (arraySet == null || arraySet.size() == 0) {
            return;
        }
        com.android.internal.os.BinderfsStatsReader binderfsStatsReader = new com.android.internal.os.BinderfsStatsReader();
        java.util.Objects.requireNonNull(arraySet);
        binderfsStatsReader.handleFreeAsyncSpace(new java.util.function.Predicate() { // from class: com.android.server.am.CachedAppOptimizer$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return arraySet.contains((java.lang.Integer) obj);
            }
        }, new java.util.function.BiConsumer() { // from class: com.android.server.am.CachedAppOptimizer$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.am.CachedAppOptimizer.this.lambda$binderErrorInternal$3((java.lang.Integer) obj, (java.lang.Integer) obj2);
            }
        }, new java.util.function.Consumer() { // from class: com.android.server.am.CachedAppOptimizer$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.util.Slog.e("ActivityManager", "Unable to parse binderfs stats");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$binderErrorInternal$3(java.lang.Integer num, java.lang.Integer num2) {
        if (num2.intValue() < this.mFreezerBinderAsyncThreshold) {
            android.util.Slog.w("ActivityManager", "pid " + num + " has " + num2 + " free async space, killing");
            killProcess(num.intValue(), "Async binder space running out while frozen", 14, 31);
        }
    }
}
