package com.android.server.am;

/* loaded from: classes.dex */
public class AppProfiler {
    private static final java.lang.String ACTION_HEAP_DUMP_FINISHED = "com.android.internal.intent.action.HEAP_DUMP_FINISHED";
    static final java.lang.String ACTIVITY_START_PSS_DEFER_CONFIG = "activity_start_pss_defer";
    static final long BATTERY_STATS_TIME = 1800000;
    private static final java.lang.String EXTRA_HEAP_DUMP_IS_USER_INITIATED = "com.android.internal.extra.heap_dump.IS_USER_INITIATED";
    private static final java.lang.String EXTRA_HEAP_DUMP_PROCESS_NAME = "com.android.internal.extra.heap_dump.PROCESS_NAME";
    private static final java.lang.String EXTRA_HEAP_DUMP_REPORT_PACKAGE = "com.android.internal.extra.heap_dump.REPORT_PACKAGE";
    private static final java.lang.String EXTRA_HEAP_DUMP_SIZE_BYTES = "com.android.internal.extra.heap_dump.SIZE_BYTES";
    static final long MONITOR_CPU_MAX_TIME = 268435455;
    static final long MONITOR_CPU_MIN_TIME = 5000;
    static final boolean MONITOR_CPU_USAGE = true;
    static final boolean MONITOR_THREAD_CPU_USAGE = false;
    private static final java.lang.String TAG = "ActivityManager";
    static final java.lang.String TAG_OOM_ADJ = "ActivityManager";
    static final java.lang.String TAG_PSS = "ActivityManager";
    static final java.lang.String TAG_RSS = "ActivityManager";
    private final android.os.Handler mBgHandler;

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean mHasHomeProcess;

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean mHasPreviousProcess;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mLastNumProcesses;
    private final com.android.server.am.LowMemDetector mLowMemDetector;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private int mMemWatchDumpPid;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private java.lang.String mMemWatchDumpProcName;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private int mMemWatchDumpUid;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private android.net.Uri mMemWatchDumpUri;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private boolean mMemWatchIsUserInitiated;
    final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    private final com.android.server.am.ActivityManagerService mService;
    private volatile long mPssDeferralTime = 0;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private final java.util.ArrayList<com.android.server.am.ProcessProfileRecord> mPendingPssOrRssProfiles = new java.util.ArrayList<>();
    private final java.util.concurrent.atomic.AtomicInteger mActivityStartingNesting = new java.util.concurrent.atomic.AtomicInteger(0);

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mLastFullPssTime = android.os.SystemClock.uptimeMillis();

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private boolean mFullPssOrRssPending = false;
    private volatile boolean mTestPssOrRssMode = false;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mAllowLowerMemLevel = false;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mLastMemoryLevel = 0;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mMemFactorOverride = -1;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private long mLowRamTimeSinceLastIdle = 0;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private long mLowRamStartTime = 0;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private long mLastMemUsageReportTime = 0;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private final java.util.ArrayList<com.android.server.am.ProcessRecord> mProcessesToGc = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    @android.annotation.Nullable
    private java.util.Map<java.lang.String, java.lang.String> mAppAgentMap = null;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private int mProfileType = 0;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private final com.android.server.am.AppProfiler.ProfileData mProfileData = new com.android.server.am.AppProfiler.ProfileData();

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private final com.android.internal.app.ProcessMap<android.util.Pair<java.lang.Long, java.lang.String>> mMemWatchProcesses = new com.android.internal.app.ProcessMap<>();
    private final com.android.internal.os.ProcessCpuTracker mProcessCpuTracker = new com.android.internal.os.ProcessCpuTracker(false);
    private final java.util.concurrent.atomic.AtomicLong mLastCpuTime = new java.util.concurrent.atomic.AtomicLong(0);
    private final java.util.concurrent.atomic.AtomicBoolean mProcessCpuMutexFree = new java.util.concurrent.atomic.AtomicBoolean(true);
    private final java.util.concurrent.CountDownLatch mProcessCpuInitLatch = new java.util.concurrent.CountDownLatch(1);
    private volatile long mLastWriteTime = 0;
    final com.android.server.am.AppProfiler.CachedAppsWatermarkData mCachedAppsWatermarkData = new com.android.server.am.AppProfiler.CachedAppsWatermarkData();
    final java.lang.Object mProfilerLock = new java.lang.Object();
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mPssDelayConfigListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.am.AppProfiler.1
        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            if (properties.getKeyset().contains(com.android.server.am.AppProfiler.ACTIVITY_START_PSS_DEFER_CONFIG)) {
                com.android.server.am.AppProfiler.this.mPssDeferralTime = properties.getLong(com.android.server.am.AppProfiler.ACTIVITY_START_PSS_DEFER_CONFIG, 0L);
            }
        }
    };
    private final java.lang.Thread mProcessCpuThread = new com.android.server.am.AppProfiler.ProcessCpuThread("CpuTracker");

    private class ProfileData {
        private java.lang.String mProfileApp;
        private com.android.server.am.ProcessRecord mProfileProc;
        private android.app.ProfilerInfo mProfilerInfo;

        private ProfileData() {
            this.mProfileApp = null;
            this.mProfileProc = null;
            this.mProfilerInfo = null;
        }

        void setProfileApp(java.lang.String str) {
            this.mProfileApp = str;
            if (com.android.server.am.AppProfiler.this.mService.mAtmInternal != null) {
                com.android.server.am.AppProfiler.this.mService.mAtmInternal.setProfileApp(str);
            }
        }

        java.lang.String getProfileApp() {
            return this.mProfileApp;
        }

        void setProfileProc(com.android.server.am.ProcessRecord processRecord) {
            this.mProfileProc = processRecord;
            if (com.android.server.am.AppProfiler.this.mService.mAtmInternal != null) {
                com.android.server.am.AppProfiler.this.mService.mAtmInternal.setProfileProc(processRecord == null ? null : processRecord.getWindowProcessController());
            }
        }

        com.android.server.am.ProcessRecord getProfileProc() {
            return this.mProfileProc;
        }

        void setProfilerInfo(android.app.ProfilerInfo profilerInfo) {
            this.mProfilerInfo = profilerInfo;
            if (com.android.server.am.AppProfiler.this.mService.mAtmInternal != null) {
                com.android.server.am.AppProfiler.this.mService.mAtmInternal.setProfilerInfo(profilerInfo);
            }
        }

        android.app.ProfilerInfo getProfilerInfo() {
            return this.mProfilerInfo;
        }
    }

    class CachedAppsWatermarkData {

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mAverageFrozenTimeInSeconds;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mBinderProxySnapshot;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        private long[] mCachedAppFrozenDurations;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mCachedAppHighWatermark;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mCachedInKb;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        private long mEarliestFrozenTimestamp;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mFreeInKb;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mKernelInKb;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        private long mLatestFrozenTimestamp;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mLongestFrozenTimeInSeconds;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mMeanFrozenTimeInSeconds;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mNumOfFrozenApps;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mShortestFrozenTimeInSeconds;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        private long mTotalFrozenDurations;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mUptimeInSeconds;

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        int mZramInKb;

        CachedAppsWatermarkData() {
        }

        @com.android.internal.annotations.GuardedBy({"mProcLock"})
        void updateCachedAppsHighWatermarkIfNecessaryLocked(int i, long j) {
            if (i > this.mCachedAppHighWatermark) {
                this.mCachedAppHighWatermark = i;
                this.mUptimeInSeconds = (int) (j / 1000);
                com.android.server.am.AppProfiler.this.mService.mHandler.removeMessages(79);
                com.android.server.am.AppProfiler.this.mService.mHandler.obtainMessage(79, java.lang.Long.valueOf(j)).sendToTarget();
            }
        }

        void updateCachedAppsSnapshot(final long j) {
            com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = com.android.server.am.AppProfiler.this.mProcLock;
            com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    this.mEarliestFrozenTimestamp = j;
                    this.mLatestFrozenTimestamp = 0L;
                    this.mTotalFrozenDurations = 0L;
                    this.mNumOfFrozenApps = 0;
                    int lruSizeLOSP = com.android.server.am.AppProfiler.this.mService.mProcessList.getLruSizeLOSP();
                    if (this.mCachedAppFrozenDurations == null || this.mCachedAppFrozenDurations.length < lruSizeLOSP) {
                        this.mCachedAppFrozenDurations = new long[java.lang.Math.max(lruSizeLOSP, com.android.server.am.AppProfiler.this.mService.mConstants.CUR_MAX_CACHED_PROCESSES)];
                    }
                    com.android.server.am.AppProfiler.this.mService.mProcessList.forEachLruProcessesLOSP(true, new java.util.function.Consumer() { // from class: com.android.server.am.AppProfiler$CachedAppsWatermarkData$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.am.AppProfiler.CachedAppsWatermarkData.this.lambda$updateCachedAppsSnapshot$0(j, (com.android.server.am.ProcessRecord) obj);
                        }
                    });
                    if (this.mNumOfFrozenApps > 0) {
                        this.mLongestFrozenTimeInSeconds = (int) ((j - this.mEarliestFrozenTimestamp) / 1000);
                        this.mShortestFrozenTimeInSeconds = (int) ((j - this.mLatestFrozenTimestamp) / 1000);
                        this.mAverageFrozenTimeInSeconds = (int) ((this.mTotalFrozenDurations / this.mNumOfFrozenApps) / 1000);
                        this.mMeanFrozenTimeInSeconds = (int) (com.android.internal.util.QuickSelect.select(this.mCachedAppFrozenDurations, 0, this.mNumOfFrozenApps, this.mNumOfFrozenApps / 2) / 1000);
                    }
                    this.mBinderProxySnapshot = 0;
                    android.util.SparseIntArray nGetBinderProxyPerUidCounts = com.android.internal.os.BinderInternal.nGetBinderProxyPerUidCounts();
                    if (nGetBinderProxyPerUidCounts != null) {
                        int size = nGetBinderProxyPerUidCounts.size();
                        for (int i = 0; i < size; i++) {
                            if (com.android.server.am.AppProfiler.this.mService.mProcessList.getUidRecordLOSP(nGetBinderProxyPerUidCounts.keyAt(i)) != null) {
                                this.mBinderProxySnapshot += nGetBinderProxyPerUidCounts.valueAt(i);
                            }
                        }
                    }
                    com.android.internal.util.MemInfoReader memInfoReader = new com.android.internal.util.MemInfoReader();
                    memInfoReader.readMemInfo();
                    this.mFreeInKb = (int) memInfoReader.getFreeSizeKb();
                    this.mCachedInKb = (int) memInfoReader.getCachedSizeKb();
                    this.mZramInKb = (int) memInfoReader.getZramTotalSizeKb();
                    this.mKernelInKb = (int) memInfoReader.getKernelUsedSizeKb();
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateCachedAppsSnapshot$0(long j, com.android.server.am.ProcessRecord processRecord) {
            if (processRecord.mOptRecord.isFrozen()) {
                long freezeUnfreezeTime = processRecord.mOptRecord.getFreezeUnfreezeTime();
                if (freezeUnfreezeTime < this.mEarliestFrozenTimestamp) {
                    this.mEarliestFrozenTimestamp = freezeUnfreezeTime;
                }
                if (freezeUnfreezeTime > this.mLatestFrozenTimestamp) {
                    this.mLatestFrozenTimestamp = freezeUnfreezeTime;
                }
                long j2 = j - freezeUnfreezeTime;
                this.mTotalFrozenDurations += j2;
                long[] jArr = this.mCachedAppFrozenDurations;
                int i = this.mNumOfFrozenApps;
                this.mNumOfFrozenApps = i + 1;
                jArr[i] = j2;
            }
        }

        @android.annotation.NonNull
        android.util.StatsEvent getCachedAppsHighWatermarkStats(int i, boolean z) {
            android.util.StatsEvent buildStatsEvent;
            com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = com.android.server.am.AppProfiler.this.mProcLock;
            com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    buildStatsEvent = com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, this.mCachedAppHighWatermark, this.mUptimeInSeconds, this.mBinderProxySnapshot, this.mFreeInKb, this.mCachedInKb, this.mZramInKb, this.mKernelInKb, this.mNumOfFrozenApps, this.mLongestFrozenTimeInSeconds, this.mShortestFrozenTimeInSeconds, this.mMeanFrozenTimeInSeconds, this.mAverageFrozenTimeInSeconds);
                    if (z) {
                        this.mCachedAppHighWatermark = 0;
                        this.mUptimeInSeconds = 0;
                        this.mBinderProxySnapshot = 0;
                        this.mFreeInKb = 0;
                        this.mCachedInKb = 0;
                        this.mZramInKb = 0;
                        this.mKernelInKb = 0;
                        this.mNumOfFrozenApps = 0;
                        this.mLongestFrozenTimeInSeconds = 0;
                        this.mShortestFrozenTimeInSeconds = 0;
                        this.mMeanFrozenTimeInSeconds = 0;
                        this.mAverageFrozenTimeInSeconds = 0;
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
            return buildStatsEvent;
        }
    }

    private class BgHandler extends android.os.Handler {
        static final int COLLECT_PSS_BG_MSG = 1;
        static final int DEFER_PSS_MSG = 2;
        static final int MEMORY_PRESSURE_CHANGED = 4;
        static final int STOP_DEFERRING_PSS_MSG = 3;

        BgHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    if (com.android.server.am.AppProfiler.this.isProfilingPss()) {
                        com.android.server.am.AppProfiler.this.collectPssInBackground();
                        return;
                    } else {
                        com.android.server.am.AppProfiler.this.collectRssInBackground();
                        return;
                    }
                case 2:
                    com.android.server.am.AppProfiler.this.deferPssForActivityStart();
                    return;
                case 3:
                    com.android.server.am.AppProfiler.this.stopDeferPss();
                    return;
                case 4:
                    com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.AppProfiler.this.mService;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            com.android.server.am.AppProfiler.this.handleMemoryPressureChangedLocked(message.arg1, message.arg2);
                        } catch (java.lang.Throwable th) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:107:? -> B:103:0x01bf). Please report as a decompilation issue!!! */
    public void collectPssInBackground() {
        int i;
        long[] jArr;
        com.android.internal.util.MemInfoReader memInfoReader;
        com.android.server.am.ProcessProfileRecord remove;
        int pssProcState;
        int pssStatType;
        long lastPssTime;
        int i2;
        java.lang.Object obj;
        long[] jArr2;
        int i3;
        java.lang.Object obj2;
        java.util.List stats;
        long j;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        synchronized (this.mProfilerLock) {
            try {
                i = 0;
                jArr = 0;
                if (!this.mFullPssOrRssPending) {
                    memInfoReader = null;
                } else {
                    this.mFullPssOrRssPending = false;
                    memInfoReader = new com.android.internal.util.MemInfoReader();
                }
            } finally {
            }
        }
        if (memInfoReader != null) {
            updateCpuStatsNow();
            synchronized (this.mProcessCpuTracker) {
                stats = this.mProcessCpuTracker.getStats(new com.android.internal.os.ProcessCpuTracker.FilterStats() { // from class: com.android.server.am.AppProfiler$$ExternalSyntheticLambda4
                    public final boolean needed(com.android.internal.os.ProcessCpuTracker.Stats stats2) {
                        boolean lambda$collectPssInBackground$0;
                        lambda$collectPssInBackground$0 = com.android.server.am.AppProfiler.lambda$collectPssInBackground$0(stats2);
                        return lambda$collectPssInBackground$0;
                    }
                });
            }
            if (this.mService.mConstants.APP_PROFILER_PSS_PROFILING_DISABLED) {
                j = 0;
            } else {
                int size = stats.size();
                long j2 = 0;
                for (int i4 = 0; i4 < size; i4++) {
                    synchronized (this.mService.mPidsSelfLocked) {
                        try {
                            if (this.mService.mPidsSelfLocked.indexOfKey(((com.android.internal.os.ProcessCpuTracker.Stats) stats.get(i4)).pid) < 0) {
                                j2 += android.os.Debug.getPss(((com.android.internal.os.ProcessCpuTracker.Stats) stats.get(i4)).pid, null, null);
                            }
                        } finally {
                        }
                    }
                }
                j = j2;
            }
            memInfoReader.readMemInfo();
            synchronized (this.mService.mProcessStats.mLock) {
                long cachedSizeKb = memInfoReader.getCachedSizeKb();
                long freeSizeKb = memInfoReader.getFreeSizeKb();
                long zramTotalSizeKb = memInfoReader.getZramTotalSizeKb();
                long kernelUsedSizeKb = memInfoReader.getKernelUsedSizeKb();
                com.android.server.am.EventLogTags.writeAmMeminfo(cachedSizeKb * 1024, freeSizeKb * 1024, zramTotalSizeKb * 1024, kernelUsedSizeKb * 1024, j * 1024);
                this.mService.mProcessStats.addSysMemUsageLocked(cachedSizeKb, freeSizeKb, zramTotalSizeKb, kernelUsedSizeKb, j);
            }
        }
        long[] jArr3 = new long[3];
        int i5 = 0;
        while (true) {
            synchronized (this.mProfilerLock) {
                try {
                    if (this.mPendingPssOrRssProfiles.size() <= 0) {
                        break;
                    }
                    remove = this.mPendingPssOrRssProfiles.remove(i);
                    pssProcState = remove.getPssProcState();
                    pssStatType = remove.getPssStatType();
                    lastPssTime = remove.getLastPssTime();
                    long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
                    if (remove.getThread() != null && pssProcState == remove.getSetProcState() && 1000 + lastPssTime < uptimeMillis2) {
                        i2 = remove.getPid();
                    } else {
                        remove.abortNextPssTime();
                        remove = jArr;
                        i2 = i;
                    }
                } finally {
                }
            }
            if (remove == null) {
                obj = jArr;
                jArr2 = jArr3;
                i3 = i;
            } else {
                long currentThreadTimeMillis = android.os.SystemClock.currentThreadTimeMillis();
                long pss = (((remove.mApp.mOptRecord == null || !remove.mApp.mOptRecord.skipPSSCollectionBecauseFrozen()) && !this.mService.isCameraActiveForUid(remove.mApp.uid) && !this.mService.mConstants.APP_PROFILER_PSS_PROFILING_DISABLED) ? i : 1) != 0 ? 0L : android.os.Debug.getPss(i2, jArr3, jArr);
                long currentThreadTimeMillis2 = android.os.SystemClock.currentThreadTimeMillis();
                java.lang.Object obj3 = this.mProfilerLock;
                synchronized (obj3) {
                    if (pss != 0) {
                        try {
                            if (remove.getThread() != null) {
                                if (remove.getSetProcState() != pssProcState) {
                                    obj2 = obj3;
                                    obj = jArr;
                                    jArr2 = jArr3;
                                    i3 = i;
                                } else if (remove.getPid() != i2 || remove.getLastPssTime() != lastPssTime) {
                                    obj2 = obj3;
                                    obj = jArr;
                                    jArr2 = jArr3;
                                    i3 = i;
                                } else {
                                    int i6 = i5 + 1;
                                    remove.commitNextPssTime();
                                    long j3 = currentThreadTimeMillis2 - currentThreadTimeMillis;
                                    long j4 = pss;
                                    obj2 = obj3;
                                    obj = jArr;
                                    jArr2 = jArr3;
                                    i3 = i;
                                    try {
                                        recordPssSampleLPf(remove, pssProcState, j4, jArr3[i], jArr3[1], jArr3[2], pssStatType, j3, android.os.SystemClock.uptimeMillis());
                                        i5 = i6;
                                    } catch (java.lang.Throwable th) {
                                        th = th;
                                        throw th;
                                    }
                                }
                                remove.abortNextPssTime();
                            }
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            obj2 = obj3;
                            throw th;
                        }
                    }
                    obj2 = obj3;
                    obj = jArr;
                    jArr2 = jArr3;
                    i3 = i;
                    remove.abortNextPssTime();
                }
            }
            jArr = obj;
            i = i3;
            jArr3 = jArr2;
        }
        if (this.mTestPssOrRssMode) {
            android.util.Slog.d("ActivityManager", "Collected pss of " + i5 + " processes in " + (android.os.SystemClock.uptimeMillis() - uptimeMillis) + "ms");
        }
        this.mPendingPssOrRssProfiles.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$collectPssInBackground$0(com.android.internal.os.ProcessCpuTracker.Stats stats) {
        return stats.vsize > 0 && stats.uid < 10000;
    }

    boolean isProfilingPss() {
        return !android.os.Flags.removeAppProfilerPssCollection() || this.mService.mConstants.mForceEnablePssProfiling;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void collectRssInBackground() {
        int i;
        com.android.internal.util.MemInfoReader memInfoReader;
        com.android.server.am.ProcessProfileRecord remove;
        int pssProcState;
        int pssStatType;
        long lastPssTime;
        int i2;
        java.util.List stats;
        long j;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        synchronized (this.mProfilerLock) {
            try {
                i = 0;
                if (!this.mFullPssOrRssPending) {
                    memInfoReader = null;
                } else {
                    this.mFullPssOrRssPending = false;
                    memInfoReader = new com.android.internal.util.MemInfoReader();
                }
            } finally {
            }
        }
        if (memInfoReader != null) {
            updateCpuStatsNow();
            synchronized (this.mProcessCpuTracker) {
                stats = this.mProcessCpuTracker.getStats(new com.android.internal.os.ProcessCpuTracker.FilterStats() { // from class: com.android.server.am.AppProfiler$$ExternalSyntheticLambda3
                    public final boolean needed(com.android.internal.os.ProcessCpuTracker.Stats stats2) {
                        boolean lambda$collectRssInBackground$1;
                        lambda$collectRssInBackground$1 = com.android.server.am.AppProfiler.lambda$collectRssInBackground$1(stats2);
                        return lambda$collectRssInBackground$1;
                    }
                });
            }
            if (this.mService.mConstants.APP_PROFILER_PSS_PROFILING_DISABLED) {
                j = 0;
            } else {
                int size = stats.size();
                long j2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    synchronized (this.mService.mPidsSelfLocked) {
                        try {
                            if (this.mService.mPidsSelfLocked.indexOfKey(((com.android.internal.os.ProcessCpuTracker.Stats) stats.get(i3)).pid) < 0) {
                                j2 += android.os.Debug.getRss(((com.android.internal.os.ProcessCpuTracker.Stats) stats.get(i3)).pid, null);
                            }
                        } finally {
                        }
                    }
                }
                j = j2;
            }
            memInfoReader.readMemInfo();
            synchronized (this.mService.mProcessStats.mLock) {
                long cachedSizeKb = memInfoReader.getCachedSizeKb();
                long freeSizeKb = memInfoReader.getFreeSizeKb();
                long zramTotalSizeKb = memInfoReader.getZramTotalSizeKb();
                long kernelUsedSizeKb = memInfoReader.getKernelUsedSizeKb();
                com.android.server.am.EventLogTags.writeAmMeminfo(cachedSizeKb * 1024, freeSizeKb * 1024, zramTotalSizeKb * 1024, kernelUsedSizeKb * 1024, j * 1024);
                this.mService.mProcessStats.addSysMemUsageLocked(cachedSizeKb, freeSizeKb, zramTotalSizeKb, kernelUsedSizeKb, j);
            }
        }
        int i4 = 0;
        while (true) {
            synchronized (this.mProfilerLock) {
                try {
                    if (this.mPendingPssOrRssProfiles.size() <= 0) {
                        break;
                    }
                    remove = this.mPendingPssOrRssProfiles.remove(i);
                    pssProcState = remove.getPssProcState();
                    pssStatType = remove.getPssStatType();
                    lastPssTime = remove.getLastPssTime();
                    long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
                    if (remove.getThread() != null && pssProcState == remove.getSetProcState() && lastPssTime + 1000 < uptimeMillis2) {
                        i2 = remove.getPid();
                    } else {
                        remove.abortNextPssTime();
                        i2 = i;
                        remove = null;
                    }
                } finally {
                }
            }
            if (remove != null) {
                long currentThreadTimeMillis = android.os.SystemClock.currentThreadTimeMillis();
                long rss = (remove.mApp.mOptRecord != null && remove.mApp.mOptRecord.skipPSSCollectionBecauseFrozen()) || this.mService.isCameraActiveForUid(remove.mApp.uid) || this.mService.mConstants.APP_PROFILER_PSS_PROFILING_DISABLED ? 0L : android.os.Debug.getRss(i2, null);
                long currentThreadTimeMillis2 = android.os.SystemClock.currentThreadTimeMillis();
                synchronized (this.mProfilerLock) {
                    if (rss != 0) {
                        try {
                            if (remove.getThread() != null && remove.getSetProcState() == pssProcState && remove.getPid() == i2 && remove.getLastPssTime() == lastPssTime) {
                                remove.commitNextPssTime();
                                recordRssSampleLPf(remove, pssProcState, rss, pssStatType, currentThreadTimeMillis2 - currentThreadTimeMillis, android.os.SystemClock.uptimeMillis());
                                i4++;
                            }
                        } finally {
                        }
                    }
                    remove.abortNextPssTime();
                }
            }
            i = 0;
        }
        if (this.mTestPssOrRssMode) {
            android.util.Slog.d("ActivityManager", "Collected rss of " + i4 + " processes in " + (android.os.SystemClock.uptimeMillis() - uptimeMillis) + "ms");
        }
        this.mPendingPssOrRssProfiles.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$collectRssInBackground$1(com.android.internal.os.ProcessCpuTracker.Stats stats) {
        return stats.vsize > 0 && stats.uid < 10000;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void updateNextPssTimeLPf(int i, com.android.server.am.ProcessProfileRecord processProfileRecord, long j, boolean z) {
        if (!z && ((j <= processProfileRecord.getNextPssTime() && j <= java.lang.Math.max(processProfileRecord.getLastPssTime() + 3600000, processProfileRecord.getLastStateTime() + com.android.server.am.ProcessList.minTimeFromStateChange(this.mTestPssOrRssMode))) || !requestPssLPf(processProfileRecord, i))) {
            return;
        }
        processProfileRecord.setNextPssTime(processProfileRecord.computeNextPssTime(i, this.mTestPssOrRssMode, this.mService.mAtmInternal.isSleeping(), j));
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private void recordPssSampleLPf(com.android.server.am.ProcessProfileRecord processProfileRecord, int i, long j, long j2, long j3, long j4, int i2, long j5, long j6) {
        java.lang.Long l;
        com.android.server.am.ProcessRecord processRecord = processProfileRecord.mApp;
        long j7 = j * 1024;
        com.android.server.am.EventLogTags.writeAmPss(processProfileRecord.getPid(), processRecord.uid, processRecord.processName, j7, j2 * 1024, j3 * 1024, j4 * 1024, i2, i, j5);
        processProfileRecord.setLastPssTime(j6);
        processProfileRecord.addPss(j, j2, j4, true, i2, j5);
        if (processProfileRecord.getInitialIdlePssOrRss() == 0) {
            processProfileRecord.setInitialIdlePssOrRss(j);
        }
        processProfileRecord.setLastPss(j);
        processProfileRecord.setLastSwapPss(j3);
        if (i >= 14) {
            processProfileRecord.setLastCachedPss(j);
            processProfileRecord.setLastCachedSwapPss(j3);
        }
        processProfileRecord.setLastRss(j4);
        android.util.SparseArray sparseArray = (android.util.SparseArray) this.mMemWatchProcesses.getMap().get(processRecord.processName);
        if (sparseArray != null) {
            android.util.Pair pair = (android.util.Pair) sparseArray.get(processRecord.uid);
            if (pair == null) {
                pair = (android.util.Pair) sparseArray.get(0);
            }
            if (pair != null) {
                l = (java.lang.Long) pair.first;
                if (l == null && j7 >= l.longValue() && processProfileRecord.getThread() != null && this.mMemWatchDumpProcName == null) {
                    if (android.os.Build.IS_DEBUGGABLE || processRecord.isDebuggable()) {
                        android.util.Slog.w("ActivityManager", "Process " + processRecord + " exceeded pss limit " + l + "; reporting");
                        startHeapDumpLPf(processProfileRecord, false);
                        return;
                    }
                    android.util.Slog.w("ActivityManager", "Process " + processRecord + " exceeded pss limit " + l + ", but debugging not enabled");
                    return;
                }
                return;
            }
        }
        l = null;
        if (l == null) {
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private void recordRssSampleLPf(com.android.server.am.ProcessProfileRecord processProfileRecord, int i, long j, int i2, long j2, long j3) {
        java.lang.Long l;
        com.android.server.am.ProcessRecord processRecord = processProfileRecord.mApp;
        com.android.server.am.EventLogTags.writeAmPss(processProfileRecord.getPid(), processRecord.uid, processRecord.processName, 0L, 0L, 0L, j * 1024, i2, i, j2);
        processProfileRecord.setLastPssTime(j3);
        processProfileRecord.addPss(0L, 0L, j, true, i2, j2);
        if (processProfileRecord.getInitialIdlePssOrRss() == 0) {
            processProfileRecord.setInitialIdlePssOrRss(j);
        }
        processProfileRecord.setLastRss(j);
        if (i >= 14) {
            processProfileRecord.setLastCachedRss(j);
        }
        android.util.SparseArray sparseArray = (android.util.SparseArray) this.mMemWatchProcesses.getMap().get(processRecord.processName);
        if (sparseArray != null) {
            android.util.Pair pair = (android.util.Pair) sparseArray.get(processRecord.uid);
            if (pair == null) {
                pair = (android.util.Pair) sparseArray.get(0);
            }
            if (pair != null) {
                l = (java.lang.Long) pair.first;
                if (l == null && android.os.Debug.getPss(processProfileRecord.getPid(), null, null) * 1024 >= l.longValue() && processProfileRecord.getThread() != null && this.mMemWatchDumpProcName == null) {
                    if (android.os.Build.IS_DEBUGGABLE || processRecord.isDebuggable()) {
                        android.util.Slog.w("ActivityManager", "Process " + processRecord + " exceeded pss limit " + l + "; reporting");
                        startHeapDumpLPf(processProfileRecord, false);
                        return;
                    }
                    android.util.Slog.w("ActivityManager", "Process " + processRecord + " exceeded pss limit " + l + ", but debugging not enabled");
                    return;
                }
                return;
            }
        }
        l = null;
        if (l == null) {
        }
    }

    private final class RecordPssRunnable implements java.lang.Runnable {
        private final android.content.ContentResolver mContentResolver;
        private final android.net.Uri mDumpUri;
        private final com.android.server.am.ProcessProfileRecord mProfile;

        RecordPssRunnable(com.android.server.am.ProcessProfileRecord processProfileRecord, android.net.Uri uri, android.content.ContentResolver contentResolver) {
            this.mProfile = processProfileRecord;
            this.mDumpUri = uri;
            this.mContentResolver = contentResolver;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                android.os.ParcelFileDescriptor openFileDescriptor = this.mContentResolver.openFileDescriptor(this.mDumpUri, "rw");
                try {
                    android.app.IApplicationThread thread = this.mProfile.getThread();
                    if (thread != null) {
                        try {
                            thread.dumpHeap(true, false, false, this.mDumpUri.getPath(), openFileDescriptor, (android.os.RemoteCallback) null);
                        } catch (android.os.RemoteException e) {
                        }
                    }
                    if (openFileDescriptor != null) {
                        openFileDescriptor.close();
                    }
                } finally {
                }
            } catch (java.io.IOException e2) {
                android.util.Slog.e("ActivityManager", "Failed to dump heap", e2);
                com.android.server.am.AppProfiler.this.abortHeapDump(this.mProfile.mApp.processName);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void startHeapDumpLPf(com.android.server.am.ProcessProfileRecord processProfileRecord, boolean z) {
        com.android.server.am.ProcessRecord processRecord = processProfileRecord.mApp;
        this.mMemWatchDumpProcName = processRecord.processName;
        this.mMemWatchDumpUri = makeHeapDumpUri(processRecord.processName);
        this.mMemWatchDumpPid = processProfileRecord.getPid();
        this.mMemWatchDumpUid = processRecord.uid;
        this.mMemWatchIsUserInitiated = z;
        try {
            com.android.internal.os.BackgroundThread.getHandler().post(new com.android.server.am.AppProfiler.RecordPssRunnable(processProfileRecord, this.mMemWatchDumpUri, this.mService.mContext.createPackageContextAsUser(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, 0, android.os.UserHandle.getUserHandleForUid(this.mMemWatchDumpUid)).getContentResolver()));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.RuntimeException("android package not found.");
        }
    }

    void dumpHeapFinished(java.lang.String str, int i) {
        synchronized (this.mProfilerLock) {
            try {
                if (i != this.mMemWatchDumpPid) {
                    android.util.Slog.w("ActivityManager", "dumpHeapFinished: Calling pid " + android.os.Binder.getCallingPid() + " does not match last pid " + this.mMemWatchDumpPid);
                    return;
                }
                if (this.mMemWatchDumpUri == null || !this.mMemWatchDumpUri.getPath().equals(str)) {
                    android.util.Slog.w("ActivityManager", "dumpHeapFinished: Calling path " + str + " does not match last path " + this.mMemWatchDumpUri);
                    return;
                }
                this.mService.mHandler.sendEmptyMessage(50);
                java.lang.Runtime.getRuntime().gc();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void handlePostDumpHeapNotification() {
        int i;
        java.lang.String str;
        long j;
        java.lang.String str2;
        boolean z;
        synchronized (this.mProfilerLock) {
            try {
                i = this.mMemWatchDumpUid;
                str = this.mMemWatchDumpProcName;
                android.util.Pair pair = (android.util.Pair) this.mMemWatchProcesses.get(str, i);
                if (pair == null) {
                    pair = (android.util.Pair) this.mMemWatchProcesses.get(str, 0);
                }
                if (pair != null) {
                    j = ((java.lang.Long) pair.first).longValue();
                    str2 = (java.lang.String) pair.second;
                } else {
                    j = 0;
                    str2 = null;
                }
                z = this.mMemWatchIsUserInitiated;
                this.mMemWatchDumpUri = null;
                this.mMemWatchDumpProcName = null;
                this.mMemWatchDumpPid = -1;
                this.mMemWatchDumpUid = -1;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (str == null) {
            return;
        }
        android.content.Intent intent = new android.content.Intent(ACTION_HEAP_DUMP_FINISHED);
        intent.setPackage(com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME);
        intent.putExtra("android.intent.extra.UID", i);
        intent.putExtra(EXTRA_HEAP_DUMP_IS_USER_INITIATED, z);
        intent.putExtra(EXTRA_HEAP_DUMP_SIZE_BYTES, j);
        intent.putExtra(EXTRA_HEAP_DUMP_REPORT_PACKAGE, str2);
        intent.putExtra(EXTRA_HEAP_DUMP_PROCESS_NAME, str);
        this.mService.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.getUserHandleForUid(i));
    }

    void setDumpHeapDebugLimit(java.lang.String str, int i, long j, java.lang.String str2) {
        synchronized (this.mProfilerLock) {
            try {
                if (j > 0) {
                    this.mMemWatchProcesses.put(str, i, new android.util.Pair(java.lang.Long.valueOf(j), str2));
                } else if (i != 0) {
                    this.mMemWatchProcesses.remove(str, i);
                } else {
                    this.mMemWatchProcesses.getMap().remove(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortHeapDump(java.lang.String str) {
        android.os.Message obtainMessage = this.mService.mHandler.obtainMessage(51);
        obtainMessage.obj = str;
        this.mService.mHandler.sendMessage(obtainMessage);
    }

    void handleAbortDumpHeap(java.lang.String str) {
        if (str != null) {
            synchronized (this.mProfilerLock) {
                try {
                    if (str.equals(this.mMemWatchDumpProcName)) {
                        this.mMemWatchDumpProcName = null;
                        this.mMemWatchDumpUri = null;
                        this.mMemWatchDumpPid = -1;
                        this.mMemWatchDumpUid = -1;
                    }
                } finally {
                }
            }
        }
    }

    private static android.net.Uri makeHeapDumpUri(java.lang.String str) {
        return android.net.Uri.parse("content://com.android.shell.heapdump/" + str + "_javaheap.bin");
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private boolean requestPssLPf(com.android.server.am.ProcessProfileRecord processProfileRecord, int i) {
        if (this.mPendingPssOrRssProfiles.contains(processProfileRecord)) {
            return false;
        }
        if (this.mPendingPssOrRssProfiles.size() == 0) {
            long j = 0;
            if (this.mPssDeferralTime > 0 && this.mActivityStartingNesting.get() > 0) {
                j = this.mPssDeferralTime;
            }
            this.mBgHandler.sendEmptyMessageDelayed(1, j);
        }
        processProfileRecord.setPssProcState(i);
        processProfileRecord.setPssStatType(0);
        this.mPendingPssOrRssProfiles.add(processProfileRecord);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private void deferPssIfNeededLPf() {
        if (this.mPendingPssOrRssProfiles.size() > 0) {
            this.mBgHandler.removeMessages(1);
            this.mBgHandler.sendEmptyMessageDelayed(1, this.mPssDeferralTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deferPssForActivityStart() {
        if (this.mPssDeferralTime > 0) {
            synchronized (this.mProfilerLock) {
                deferPssIfNeededLPf();
            }
            this.mActivityStartingNesting.getAndIncrement();
            this.mBgHandler.sendEmptyMessageDelayed(3, this.mPssDeferralTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopDeferPss() {
        int decrementAndGet = this.mActivityStartingNesting.decrementAndGet();
        if (decrementAndGet <= 0 && decrementAndGet < 0) {
            android.util.Slog.wtf("ActivityManager", "Activity start nesting undercount!");
            this.mActivityStartingNesting.incrementAndGet();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void requestPssAllProcsLPr(final long j, final boolean z, final boolean z2) {
        synchronized (this.mProfilerLock) {
            if (!z) {
                try {
                    if (j < this.mLastFullPssTime + (z2 ? this.mService.mConstants.FULL_PSS_LOWERED_INTERVAL : this.mService.mConstants.FULL_PSS_MIN_INTERVAL)) {
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mLastFullPssTime = j;
            this.mFullPssOrRssPending = true;
            for (int size = this.mPendingPssOrRssProfiles.size() - 1; size >= 0; size--) {
                this.mPendingPssOrRssProfiles.get(size).abortNextPssTime();
            }
            this.mPendingPssOrRssProfiles.ensureCapacity(this.mService.mProcessList.getLruSizeLOSP());
            this.mPendingPssOrRssProfiles.clear();
            this.mService.mProcessList.forEachLruProcessesLOSP(false, new java.util.function.Consumer() { // from class: com.android.server.am.AppProfiler$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.AppProfiler.this.lambda$requestPssAllProcsLPr$2(z2, z, j, (com.android.server.am.ProcessRecord) obj);
                }
            });
            if (!this.mBgHandler.hasMessages(1)) {
                this.mBgHandler.sendEmptyMessage(1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestPssAllProcsLPr$2(boolean z, boolean z2, long j, com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.ProcessProfileRecord processProfileRecord = processRecord.mProfile;
        if (processProfileRecord.getThread() == null || processProfileRecord.getSetProcState() == 20) {
            return;
        }
        long lastStateTime = processProfileRecord.getLastStateTime();
        if (z || ((z2 && j > 1000 + lastStateTime) || j > lastStateTime + 1200000)) {
            processProfileRecord.setPssProcState(processProfileRecord.getSetProcState());
            processProfileRecord.setPssStatType(z2 ? 2 : 1);
            updateNextPssTimeLPf(processProfileRecord.getSetProcState(), processProfileRecord, j, true);
            this.mPendingPssOrRssProfiles.add(processProfileRecord);
        }
    }

    void setTestPssMode(boolean z) {
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                this.mTestPssOrRssMode = z;
                if (z) {
                    requestPssAllProcsLPr(android.os.SystemClock.uptimeMillis(), true, true);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    boolean getTestPssMode() {
        return this.mTestPssOrRssMode;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int getLastMemoryLevelLocked() {
        if (this.mMemFactorOverride != -1) {
            return this.mMemFactorOverride;
        }
        return this.mLastMemoryLevel;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isLastMemoryLevelNormal() {
        return this.mMemFactorOverride != -1 ? this.mMemFactorOverride <= 0 : this.mLastMemoryLevel <= 0;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void updateLowRamTimestampLPr(long j) {
        this.mLowRamTimeSinceLastIdle = 0L;
        if (this.mLowRamStartTime != 0) {
            this.mLowRamStartTime = j;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setAllowLowerMemLevelLocked(boolean z) {
        this.mAllowLowerMemLevel = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setMemFactorOverrideLocked(int i) {
        this.mMemFactorOverride = i;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    boolean updateLowMemStateLSP(int i, int i2, int i3, long j) {
        int i4;
        boolean z;
        final boolean memFactorLocked;
        final int memFactorLocked2;
        if (this.mLowMemDetector != null && this.mLowMemDetector.isAvailable()) {
            i4 = this.mLowMemDetector.getMemFactor();
        } else if (i <= this.mService.mConstants.CUR_TRIM_CACHED_PROCESSES && i2 <= this.mService.mConstants.CUR_TRIM_EMPTY_PROCESSES) {
            int i5 = i + i2;
            if (i5 <= 3) {
                i4 = 3;
            } else if (i5 <= 5) {
                i4 = 2;
            } else {
                i4 = 1;
            }
        } else {
            i4 = 0;
        }
        boolean z2 = this.mMemFactorOverride != -1;
        if (z2) {
            i4 = this.mMemFactorOverride;
        }
        if (i4 > this.mLastMemoryLevel && !z2 && (!this.mAllowLowerMemLevel || this.mService.mProcessList.getLruSizeLOSP() >= this.mLastNumProcesses)) {
            i4 = this.mLastMemoryLevel;
        }
        int i6 = 15;
        if (i4 != this.mLastMemoryLevel) {
            com.android.server.am.EventLogTags.writeAmMemFactor(i4, this.mLastMemoryLevel);
            com.android.internal.util.FrameworkStatsLog.write(15, i4);
            this.mBgHandler.obtainMessage(4, this.mLastMemoryLevel, i4).sendToTarget();
        }
        this.mCachedAppsWatermarkData.updateCachedAppsHighWatermarkIfNecessaryLocked(i + i2, j);
        synchronized (this.mService.mProcessStats.mLock) {
            try {
                com.android.server.am.ProcessStatsService processStatsService = this.mService.mProcessStats;
                if (this.mService.mAtmInternal != null && this.mService.mAtmInternal.isSleeping()) {
                    z = false;
                    memFactorLocked = processStatsService.setMemFactorLocked(i4, z, android.os.SystemClock.uptimeMillis());
                    memFactorLocked2 = this.mService.mProcessStats.getMemFactorLocked();
                }
                z = true;
                memFactorLocked = processStatsService.setMemFactorLocked(i4, z, android.os.SystemClock.uptimeMillis());
                memFactorLocked2 = this.mService.mProcessStats.getMemFactorLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mLastMemoryLevel = i4;
        this.mLastNumProcesses = this.mService.mProcessList.getLruSizeLOSP();
        if (this.mService.mConstants.USE_MODERN_TRIM) {
            this.mService.mProcessList.forEachLruProcessesLOSP(true, new java.util.function.Consumer() { // from class: com.android.server.am.AppProfiler$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.AppProfiler.lambda$updateLowMemStateLSP$3((com.android.server.am.ProcessRecord) obj);
                }
            });
            return false;
        }
        if (i4 == 0) {
            if (this.mLowRamStartTime != 0) {
                this.mLowRamTimeSinceLastIdle += j - this.mLowRamStartTime;
                this.mLowRamStartTime = 0L;
            }
            this.mService.mProcessList.forEachLruProcessesLOSP(true, new java.util.function.Consumer() { // from class: com.android.server.am.AppProfiler$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.AppProfiler.this.lambda$updateLowMemStateLSP$5(memFactorLocked, memFactorLocked2, (com.android.server.am.ProcessRecord) obj);
                }
            });
        } else {
            if (this.mLowRamStartTime == 0) {
                this.mLowRamStartTime = j;
            }
            switch (i4) {
                case 2:
                    i6 = 10;
                    break;
                case 3:
                    break;
                default:
                    i6 = 5;
                    break;
            }
            int i7 = i3 / 3;
            int i8 = this.mHasHomeProcess ? 3 : 2;
            if (this.mHasPreviousProcess) {
                i8++;
            }
            final int i9 = i7 < i8 ? i8 : i7;
            final int[] iArr = {0};
            final int[] iArr2 = {80};
            final int i10 = i6;
            this.mService.mProcessList.forEachLruProcessesLOSP(true, new java.util.function.Consumer() { // from class: com.android.server.am.AppProfiler$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.AppProfiler.this.lambda$updateLowMemStateLSP$4(memFactorLocked, memFactorLocked2, iArr2, iArr, i9, i10, (com.android.server.am.ProcessRecord) obj);
                }
            });
        }
        return memFactorLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateLowMemStateLSP$3(com.android.server.am.ProcessRecord processRecord) {
        android.app.IApplicationThread thread;
        com.android.server.am.ProcessProfileRecord processProfileRecord = processRecord.mProfile;
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        if (processStateRecord.hasProcStateChanged()) {
            processStateRecord.setProcStateChanged(false);
        }
        int curProcState = processRecord.mState.getCurProcState();
        if (((curProcState >= 7 && curProcState < 16) || processRecord.mState.isSystemNoUi()) && processRecord.mProfile.hasPendingUiClean() && (thread = processRecord.getThread()) != null) {
            try {
                thread.scheduleTrimMemory(20);
                processRecord.mProfile.setPendingUiClean(false);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateLowMemStateLSP$4(boolean z, int i, int[] iArr, int[] iArr2, int i2, int i3, com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.ProcessProfileRecord processProfileRecord = processRecord.mProfile;
        processProfileRecord.getTrimMemoryLevel();
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        int curProcState = processStateRecord.getCurProcState();
        if (z || processStateRecord.hasProcStateChanged()) {
            this.mService.setProcessTrackerStateLOSP(processRecord, i);
            processStateRecord.setProcStateChanged(false);
        }
        trimMemoryUiHiddenIfNecessaryLSP(processRecord);
        if (curProcState >= 14 && !processRecord.isKilledByAm()) {
            scheduleTrimMemoryLSP(processRecord, iArr[0], "Trimming memory of ");
            processProfileRecord.setTrimMemoryLevel(iArr[0]);
            iArr2[0] = iArr2[0] + 1;
            if (iArr2[0] >= i2) {
                iArr2[0] = 0;
                switch (iArr[0]) {
                    case 60:
                        iArr[0] = 40;
                        break;
                    case 80:
                        iArr[0] = 60;
                        break;
                }
            }
            return;
        }
        if (curProcState == 13 && !processRecord.isKilledByAm()) {
            scheduleTrimMemoryLSP(processRecord, 40, "Trimming memory of heavy-weight ");
            processProfileRecord.setTrimMemoryLevel(40);
        } else {
            scheduleTrimMemoryLSP(processRecord, i3, "Trimming memory of fg ");
            processProfileRecord.setTrimMemoryLevel(i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateLowMemStateLSP$5(boolean z, int i, com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.ProcessProfileRecord processProfileRecord = processRecord.mProfile;
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        if (z || processStateRecord.hasProcStateChanged()) {
            this.mService.setProcessTrackerStateLOSP(processRecord, i);
            processStateRecord.setProcStateChanged(false);
        }
        trimMemoryUiHiddenIfNecessaryLSP(processRecord);
        processProfileRecord.setTrimMemoryLevel(0);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void trimMemoryUiHiddenIfNecessaryLSP(com.android.server.am.ProcessRecord processRecord) {
        if ((processRecord.mState.getCurProcState() >= 7 || processRecord.mState.isSystemNoUi()) && processRecord.mProfile.hasPendingUiClean()) {
            scheduleTrimMemoryLSP(processRecord, 20, "Trimming memory of bg-ui ");
            processRecord.mProfile.setPendingUiClean(false);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void scheduleTrimMemoryLSP(com.android.server.am.ProcessRecord processRecord, int i, java.lang.String str) {
        android.app.IApplicationThread thread;
        if (processRecord.mProfile.getTrimMemoryLevel() < i && (thread = processRecord.getThread()) != null) {
            try {
                this.mService.mOomAdjuster.mCachedAppOptimizer.unfreezeTemporarily(processRecord, 13);
                thread.scheduleTrimMemory(i);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    long getLowRamTimeSinceIdleLPr(long j) {
        return this.mLowRamTimeSinceLastIdle + (this.mLowRamStartTime > 0 ? j - this.mLowRamStartTime : 0L);
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private void performAppGcLPf(com.android.server.am.ProcessRecord processRecord) {
        try {
            com.android.server.am.ProcessProfileRecord processProfileRecord = processRecord.mProfile;
            processProfileRecord.setLastRequestedGc(android.os.SystemClock.uptimeMillis());
            android.app.IApplicationThread thread = processProfileRecord.getThread();
            if (thread != null) {
                if (processProfileRecord.getReportLowMemory()) {
                    processProfileRecord.setReportLowMemory(false);
                    thread.scheduleLowMemory();
                } else {
                    thread.processInBackground();
                }
            }
        } catch (java.lang.Exception e) {
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private void performAppGcsLPf() {
        if (this.mProcessesToGc.size() <= 0) {
            return;
        }
        while (this.mProcessesToGc.size() > 0) {
            com.android.server.am.ProcessRecord remove = this.mProcessesToGc.remove(0);
            com.android.server.am.ProcessProfileRecord processProfileRecord = remove.mProfile;
            if (processProfileRecord.getCurRawAdj() > 200 || processProfileRecord.getReportLowMemory()) {
                if (processProfileRecord.getLastRequestedGc() + this.mService.mConstants.GC_MIN_INTERVAL <= android.os.SystemClock.uptimeMillis()) {
                    performAppGcLPf(remove);
                    scheduleAppGcsLPf();
                    return;
                } else {
                    addProcessToGcListLPf(remove);
                    scheduleAppGcsLPf();
                }
            }
        }
        scheduleAppGcsLPf();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    final void performAppGcsIfAppropriateLocked() {
        synchronized (this.mProfilerLock) {
            try {
                if (this.mService.canGcNowLocked()) {
                    performAppGcsLPf();
                } else {
                    scheduleAppGcsLPf();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    final void scheduleAppGcsLPf() {
        this.mService.mHandler.removeMessages(5);
        if (this.mProcessesToGc.size() > 0) {
            com.android.server.am.ProcessRecord processRecord = this.mProcessesToGc.get(0);
            android.os.Message obtainMessage = this.mService.mHandler.obtainMessage(5);
            long lastRequestedGc = processRecord.mProfile.getLastRequestedGc() + this.mService.mConstants.GC_MIN_INTERVAL;
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (lastRequestedGc < this.mService.mConstants.GC_TIMEOUT + uptimeMillis) {
                lastRequestedGc = this.mService.mConstants.GC_TIMEOUT + uptimeMillis;
            }
            this.mService.mHandler.sendMessageAtTime(obtainMessage, lastRequestedGc);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private void addProcessToGcListLPf(com.android.server.am.ProcessRecord processRecord) {
        boolean z = true;
        int size = this.mProcessesToGc.size() - 1;
        while (true) {
            if (size < 0) {
                z = false;
                break;
            } else if (this.mProcessesToGc.get(size).mProfile.getLastRequestedGc() >= processRecord.mProfile.getLastRequestedGc()) {
                size--;
            } else {
                this.mProcessesToGc.add(size + 1, processRecord);
                break;
            }
        }
        if (!z) {
            this.mProcessesToGc.add(0, processRecord);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    final void doLowMemReportIfNeededLocked(final com.android.server.am.ProcessRecord processRecord) {
        if (!this.mService.mProcessList.haveBackgroundProcessLOSP()) {
            boolean z = android.os.Build.IS_DEBUGGABLE;
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (z) {
                if (uptimeMillis < this.mLastMemUsageReportTime + com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS) {
                    z = false;
                } else {
                    this.mLastMemUsageReportTime = uptimeMillis;
                }
            }
            int lruSizeLOSP = this.mService.mProcessList.getLruSizeLOSP();
            final java.util.ArrayList arrayList = z ? new java.util.ArrayList(lruSizeLOSP) : null;
            com.android.server.am.EventLogTags.writeAmLowMemory(lruSizeLOSP);
            this.mService.mProcessList.forEachLruProcessesLOSP(false, new java.util.function.Consumer() { // from class: com.android.server.am.AppProfiler$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.AppProfiler.this.lambda$doLowMemReportIfNeededLocked$6(processRecord, arrayList, uptimeMillis, (com.android.server.am.ProcessRecord) obj);
                }
            });
            if (z) {
                this.mService.mHandler.sendMessage(this.mService.mHandler.obtainMessage(33, arrayList));
            }
        }
        synchronized (this.mProfilerLock) {
            scheduleAppGcsLPf();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doLowMemReportIfNeededLocked$6(com.android.server.am.ProcessRecord processRecord, java.util.ArrayList arrayList, long j, com.android.server.am.ProcessRecord processRecord2) {
        if (processRecord2 == processRecord || processRecord2.getThread() == null) {
            return;
        }
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord2.mState;
        if (arrayList != null) {
            arrayList.add(new com.android.server.am.ProcessMemInfo(processRecord2.processName, processRecord2.getPid(), processStateRecord.getSetAdj(), processStateRecord.getSetProcState(), processStateRecord.getAdjType(), processStateRecord.makeAdjReason()));
        }
        com.android.server.am.ProcessProfileRecord processProfileRecord = processRecord2.mProfile;
        if (processProfileRecord.getLastLowMemory() + this.mService.mConstants.GC_MIN_INTERVAL <= j) {
            synchronized (this.mProfilerLock) {
                try {
                    if (processStateRecord.getSetAdj() <= 400) {
                        processProfileRecord.setLastRequestedGc(0L);
                    } else {
                        processProfileRecord.setLastRequestedGc(processProfileRecord.getLastLowMemory());
                    }
                    processProfileRecord.setReportLowMemory(true);
                    processProfileRecord.setLastLowMemory(j);
                    this.mProcessesToGc.remove(processRecord2);
                    addProcessToGcListLPf(processRecord2);
                } finally {
                }
            }
        }
    }

    void reportMemUsage(java.util.ArrayList<com.android.server.am.ProcessMemInfo> arrayList) {
        long j;
        long j2;
        long j3;
        int i;
        int i2;
        int i3;
        long[] jArr;
        int i4;
        java.util.List<com.android.internal.os.ProcessCpuTracker.Stats> list;
        java.util.ArrayList<com.android.server.am.ProcessMemInfo> arrayList2 = arrayList;
        android.util.SparseArray sparseArray = new android.util.SparseArray(arrayList.size());
        int size = arrayList.size();
        for (int i5 = 0; i5 < size; i5++) {
            com.android.server.am.ProcessMemInfo processMemInfo = arrayList2.get(i5);
            sparseArray.put(processMemInfo.pid, processMemInfo);
        }
        updateCpuStatsNow();
        long[] jArr2 = new long[4];
        long[] jArr3 = new long[2];
        java.util.List<com.android.internal.os.ProcessCpuTracker.Stats> cpuStats = getCpuStats(new java.util.function.Predicate() { // from class: com.android.server.am.AppProfiler$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$reportMemUsage$7;
                lambda$reportMemUsage$7 = com.android.server.am.AppProfiler.lambda$reportMemUsage$7((com.android.internal.os.ProcessCpuTracker.Stats) obj);
                return lambda$reportMemUsage$7;
            }
        });
        int size2 = cpuStats.size();
        long j4 = 0;
        long j5 = 0;
        long j6 = 0;
        int i6 = 0;
        while (i6 < size2) {
            com.android.internal.os.ProcessCpuTracker.Stats stats = cpuStats.get(i6);
            long pss = android.os.Debug.getPss(stats.pid, jArr3, jArr2);
            if (pss <= j4) {
                i4 = size2;
                list = cpuStats;
            } else if (sparseArray.indexOfKey(stats.pid) >= 0) {
                i4 = size2;
                list = cpuStats;
            } else {
                com.android.server.am.ProcessMemInfo processMemInfo2 = new com.android.server.am.ProcessMemInfo(stats.name, stats.pid, -1000, -1, "native", null);
                processMemInfo2.pss = pss;
                i4 = size2;
                processMemInfo2.swapPss = jArr3[1];
                list = cpuStats;
                processMemInfo2.memtrack = jArr2[0];
                j5 += jArr2[1];
                j6 += jArr2[2];
                arrayList2.add(processMemInfo2);
            }
            i6++;
            cpuStats = list;
            j4 = 0;
            size2 = i4;
        }
        int size3 = arrayList.size();
        int i7 = 0;
        long j7 = 0;
        long j8 = 0;
        long j9 = 0;
        while (i7 < size3) {
            com.android.server.am.ProcessMemInfo processMemInfo3 = arrayList2.get(i7);
            int i8 = size3;
            if (processMemInfo3.pss != 0) {
                i2 = i7;
                i3 = i8;
                jArr = jArr3;
            } else {
                processMemInfo3.pss = android.os.Debug.getPss(processMemInfo3.pid, jArr3, jArr2);
                i2 = i7;
                processMemInfo3.swapPss = jArr3[1];
                i3 = i8;
                jArr = jArr3;
                processMemInfo3.memtrack = jArr2[0];
                j5 += jArr2[1];
                j6 += jArr2[2];
            }
            j7 += processMemInfo3.pss;
            j9 += processMemInfo3.swapPss;
            j8 += processMemInfo3.memtrack;
            jArr3 = jArr;
            size3 = i3;
            i7 = i2 + 1;
            arrayList2 = arrayList;
        }
        java.util.Collections.sort(arrayList, new java.util.Comparator<com.android.server.am.ProcessMemInfo>() { // from class: com.android.server.am.AppProfiler.2
            @Override // java.util.Comparator
            public int compare(com.android.server.am.ProcessMemInfo processMemInfo4, com.android.server.am.ProcessMemInfo processMemInfo5) {
                if (processMemInfo4.oomAdj != processMemInfo5.oomAdj) {
                    return processMemInfo4.oomAdj < processMemInfo5.oomAdj ? -1 : 1;
                }
                if (processMemInfo4.pss != processMemInfo5.pss) {
                    return processMemInfo4.pss < processMemInfo5.pss ? 1 : -1;
                }
                return 0;
            }
        });
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder(128);
        sb.append("Low on memory -- ");
        com.android.server.am.ActivityManagerService.appendMemBucket(sb, j7, "total", false);
        com.android.server.am.ActivityManagerService.appendMemBucket(sb2, j7, "total", true);
        java.lang.StringBuilder sb3 = new java.lang.StringBuilder(1024);
        java.lang.StringBuilder sb4 = new java.lang.StringBuilder(1024);
        java.lang.StringBuilder sb5 = new java.lang.StringBuilder(1024);
        int size4 = arrayList.size();
        int i9 = Integer.MIN_VALUE;
        int i10 = 0;
        boolean z = true;
        long j10 = 0;
        long j11 = 0;
        long j12 = 0;
        while (true) {
            j = j6;
            if (i10 >= size4) {
                break;
            }
            com.android.server.am.ProcessMemInfo processMemInfo4 = arrayList.get(i10);
            long j13 = j5;
            if (processMemInfo4.oomAdj < 900) {
                j2 = j10;
            } else {
                j2 = j10 + processMemInfo4.pss;
            }
            long j14 = j2;
            if (processMemInfo4.oomAdj == -1000) {
                j3 = j8;
            } else if (processMemInfo4.oomAdj < 500 || processMemInfo4.oomAdj == 600 || processMemInfo4.oomAdj == 700) {
                if (i9 != processMemInfo4.oomAdj) {
                    i9 = processMemInfo4.oomAdj;
                    if (processMemInfo4.oomAdj <= 0) {
                        sb.append(" / ");
                    }
                    if (processMemInfo4.oomAdj >= 0) {
                        if (z) {
                            sb2.append(":");
                            z = false;
                        }
                        sb2.append("\n\t at ");
                    } else {
                        sb2.append("$");
                    }
                } else {
                    sb.append(" ");
                    sb2.append("$");
                }
                if (processMemInfo4.oomAdj > 0) {
                    j3 = j8;
                } else {
                    j3 = j8;
                    com.android.server.am.ActivityManagerService.appendMemBucket(sb, processMemInfo4.pss, processMemInfo4.name, false);
                }
                com.android.server.am.ActivityManagerService.appendMemBucket(sb2, processMemInfo4.pss, processMemInfo4.name, true);
                if (processMemInfo4.oomAdj >= 0 && ((i = i10 + 1) >= size4 || arrayList.get(i).oomAdj != i9)) {
                    sb2.append("(");
                    for (int i11 = 0; i11 < com.android.server.am.ActivityManagerService.DUMP_MEM_OOM_ADJ.length; i11++) {
                        if (com.android.server.am.ActivityManagerService.DUMP_MEM_OOM_ADJ[i11] == processMemInfo4.oomAdj) {
                            sb2.append(com.android.server.am.ActivityManagerService.DUMP_MEM_OOM_LABEL[i11]);
                            sb2.append(":");
                            sb2.append(com.android.server.am.ActivityManagerService.DUMP_MEM_OOM_ADJ[i11]);
                        }
                    }
                    sb2.append(")");
                }
            } else {
                j3 = j8;
            }
            boolean z2 = z;
            com.android.server.am.ActivityManagerService.appendMemInfo(sb3, processMemInfo4);
            if (processMemInfo4.oomAdj != -1000) {
                if (j11 > 0) {
                    com.android.server.am.ActivityManagerService.appendBasicMemEntry(sb4, -1000, -1, j11, j12, "(Other native)");
                    sb4.append('\n');
                    j11 = 0;
                }
                com.android.server.am.ActivityManagerService.appendMemInfo(sb5, processMemInfo4);
            } else if (processMemInfo4.pss >= 512) {
                com.android.server.am.ActivityManagerService.appendMemInfo(sb4, processMemInfo4);
            } else {
                j11 += processMemInfo4.pss;
                j12 += processMemInfo4.memtrack;
            }
            i10++;
            z = z2;
            j6 = j;
            j5 = j13;
            j10 = j14;
            j8 = j3;
        }
        long j15 = j8;
        long j16 = j5;
        sb5.append("           ");
        com.android.server.am.ProcessList.appendRamKb(sb5, j7);
        sb5.append(": TOTAL");
        if (j15 > 0) {
            sb5.append(" (");
            sb5.append(com.android.server.am.ActivityManagerService.stringifyKBSize(j15));
            sb5.append(" memtrack)");
        }
        sb5.append("\n");
        com.android.internal.util.MemInfoReader memInfoReader = new com.android.internal.util.MemInfoReader();
        memInfoReader.readMemInfo();
        long[] rawInfo = memInfoReader.getRawInfo();
        java.lang.StringBuilder sb6 = new java.lang.StringBuilder(1024);
        android.os.Debug.getMemInfo(rawInfo);
        sb6.append("  MemInfo: ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[5]));
        sb6.append(" slab, ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[4]));
        sb6.append(" shmem, ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[12]));
        sb6.append(" vm alloc, ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[13]));
        sb6.append(" page tables ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[14]));
        sb6.append(" kernel stack\n");
        sb6.append("           ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[2]));
        sb6.append(" buffers, ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[3]));
        sb6.append(" cached, ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[11]));
        sb6.append(" mapped, ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[1]));
        sb6.append(" free\n");
        if (rawInfo[10] != 0) {
            sb6.append("  ZRAM: ");
            sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[10]));
            sb6.append(" RAM, ");
            sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[8]));
            sb6.append(" swap total, ");
            sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(rawInfo[9]));
            sb6.append(" swap free\n");
        }
        long[] ksmInfo = com.android.server.am.ActivityManagerService.getKsmInfo();
        if (ksmInfo[1] != 0 || ksmInfo[0] != 0 || ksmInfo[2] != 0 || ksmInfo[3] != 0) {
            sb6.append("  KSM: ");
            sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(ksmInfo[1]));
            sb6.append(" saved from shared ");
            sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(ksmInfo[0]));
            sb6.append("\n       ");
            sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(ksmInfo[2]));
            sb6.append(" unshared; ");
            sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(ksmInfo[3]));
            sb6.append(" volatile\n");
        }
        sb6.append("  Free RAM: ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(j10 + memInfoReader.getCachedSizeKb() + memInfoReader.getFreeSizeKb()));
        sb6.append("\n");
        long kernelUsedSizeKb = memInfoReader.getKernelUsedSizeKb();
        long ionHeapsSizeKb = android.os.Debug.getIonHeapsSizeKb();
        long ionPoolsSizeKb = android.os.Debug.getIonPoolsSizeKb();
        long dmabufMappedSizeKb = android.os.Debug.getDmabufMappedSizeKb();
        if (ionHeapsSizeKb >= 0 && ionPoolsSizeKb >= 0) {
            sb6.append("       ION: ");
            sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(ionHeapsSizeKb + ionPoolsSizeKb));
            sb6.append("\n");
            kernelUsedSizeKb += ionHeapsSizeKb - dmabufMappedSizeKb;
            j7 = (j7 - j16) + dmabufMappedSizeKb;
        } else {
            long dmabufTotalExportedKb = android.os.Debug.getDmabufTotalExportedKb();
            if (dmabufTotalExportedKb >= 0) {
                sb6.append("DMA-BUF: ");
                sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(dmabufTotalExportedKb));
                sb6.append("\n");
                kernelUsedSizeKb += dmabufTotalExportedKb - dmabufMappedSizeKb;
                j7 = (j7 - j16) + dmabufMappedSizeKb;
            }
            long dmabufHeapTotalExportedKb = android.os.Debug.getDmabufHeapTotalExportedKb();
            if (dmabufHeapTotalExportedKb >= 0) {
                sb6.append("DMA-BUF Heap: ");
                sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(dmabufHeapTotalExportedKb));
                sb6.append("\n");
            }
            long dmabufHeapPoolsSizeKb = android.os.Debug.getDmabufHeapPoolsSizeKb();
            if (dmabufHeapPoolsSizeKb >= 0) {
                sb6.append("DMA-BUF Heaps pool: ");
                sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(dmabufHeapPoolsSizeKb));
                sb6.append("\n");
            }
        }
        long gpuTotalUsageKb = android.os.Debug.getGpuTotalUsageKb();
        if (gpuTotalUsageKb >= 0) {
            long gpuPrivateMemoryKb = android.os.Debug.getGpuPrivateMemoryKb();
            if (gpuPrivateMemoryKb >= 0) {
                sb6.append("      GPU: ");
                sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(gpuTotalUsageKb));
                sb6.append(" (");
                sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(gpuTotalUsageKb - gpuPrivateMemoryKb));
                sb6.append(" dmabuf + ");
                sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(gpuPrivateMemoryKb));
                sb6.append(" private)\n");
                j7 -= j;
                kernelUsedSizeKb += gpuPrivateMemoryKb;
            } else {
                sb6.append("       GPU: ");
                sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(gpuTotalUsageKb));
                sb6.append("\n");
            }
        }
        sb6.append("  Used RAM: ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize((j7 - j10) + kernelUsedSizeKb));
        sb6.append("\n");
        sb6.append("  Lost RAM: ");
        sb6.append(com.android.server.am.ActivityManagerService.stringifyKBSize(((((memInfoReader.getTotalSizeKb() - (j7 - j9)) - memInfoReader.getFreeSizeKb()) - memInfoReader.getCachedSizeKb()) - kernelUsedSizeKb) - memInfoReader.getZramTotalSizeKb()));
        sb6.append("\n");
        android.util.Slog.i("ActivityManager", "Low on memory:");
        android.util.Slog.i("ActivityManager", sb4.toString());
        android.util.Slog.i("ActivityManager", sb5.toString());
        android.util.Slog.i("ActivityManager", sb6.toString());
        java.lang.StringBuilder sb7 = new java.lang.StringBuilder(1024);
        sb7.append("Low on memory:");
        sb7.append((java.lang.CharSequence) sb2);
        sb7.append('\n');
        sb7.append((java.lang.CharSequence) sb3);
        sb7.append((java.lang.CharSequence) sb5);
        sb7.append('\n');
        sb7.append((java.lang.CharSequence) sb6);
        sb7.append('\n');
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                java.io.PrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(stringWriter, false, 256);
                java.lang.String[] strArr = new java.lang.String[0];
                fastPrintWriter.println();
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        this.mService.mProcessList.dumpProcessesLSP(null, fastPrintWriter, strArr, 0, false, null, -1);
                    } catch (java.lang.Throwable th) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                fastPrintWriter.println();
                this.mService.mServices.newServiceDumperLocked(null, fastPrintWriter, strArr, 0, false, null).dumpLocked();
                fastPrintWriter.println();
                this.mService.mAtmInternal.dump(com.android.server.wm.ActivityTaskManagerService.DUMP_ACTIVITIES_CMD, null, fastPrintWriter, strArr, 0, false, false, null, -1);
                fastPrintWriter.flush();
            } finally {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        sb7.append(stringWriter.toString());
        com.android.internal.util.FrameworkStatsLog.write(81);
        this.mService.addErrorToDropBox("lowmem", null, "system_server", null, null, null, sb.toString(), sb7.toString(), null, null, null, null, null, null);
        com.android.server.am.ActivityManagerService activityManagerService2 = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService2) {
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                if (this.mLastMemUsageReportTime < uptimeMillis) {
                    this.mLastMemUsageReportTime = uptimeMillis;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$reportMemUsage$7(com.android.internal.os.ProcessCpuTracker.Stats stats) {
        return stats.vsize > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mService"})
    public void handleMemoryPressureChangedLocked(int i, int i2) {
        this.mService.mServices.rescheduleServiceRestartOnMemoryPressureIfNeededLocked(i, i2, "mem-pressure-event", android.os.SystemClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private void stopProfilerLPf(com.android.server.am.ProcessRecord processRecord, int i) {
        android.app.IApplicationThread thread;
        if (processRecord == null || processRecord == this.mProfileData.getProfileProc()) {
            processRecord = this.mProfileData.getProfileProc();
            i = this.mProfileType;
            clearProfilerLPf();
        }
        if (processRecord == null || (thread = processRecord.mProfile.getThread()) == null) {
            return;
        }
        try {
            thread.profilerControl(false, (android.app.ProfilerInfo) null, i);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Process disappeared");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void clearProfilerLPf() {
        if (this.mProfileData.getProfilerInfo() != null && this.mProfileData.getProfilerInfo().profileFd != null) {
            try {
                this.mProfileData.getProfilerInfo().profileFd.close();
            } catch (java.io.IOException e) {
            }
        }
        this.mProfileData.setProfileApp(null);
        this.mProfileData.setProfileProc(null);
        this.mProfileData.setProfilerInfo(null);
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void clearProfilerLPf(com.android.server.am.ProcessRecord processRecord) {
        if (this.mProfileData.getProfileProc() == null || this.mProfileData.getProfilerInfo() == null || this.mProfileData.getProfileProc() != processRecord) {
            return;
        }
        clearProfilerLPf();
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    boolean profileControlLPf(com.android.server.am.ProcessRecord processRecord, boolean z, android.app.ProfilerInfo profilerInfo, int i) {
        android.os.ParcelFileDescriptor parcelFileDescriptor;
        try {
            try {
                if (z) {
                    stopProfilerLPf(null, 0);
                    this.mService.setProfileApp(processRecord.info, processRecord.processName, profilerInfo, processRecord.isSdkSandbox ? processRecord.getClientInfoForSdkSandbox() : null);
                    this.mProfileData.setProfileProc(processRecord);
                    this.mProfileType = i;
                    try {
                        parcelFileDescriptor = profilerInfo.profileFd.dup();
                    } catch (java.io.IOException e) {
                        parcelFileDescriptor = null;
                    }
                    profilerInfo.profileFd = parcelFileDescriptor;
                    processRecord.mProfile.getThread().profilerControl(z, profilerInfo, i);
                    try {
                        this.mProfileData.getProfilerInfo().profileFd.close();
                    } catch (java.io.IOException e2) {
                    }
                    this.mProfileData.getProfilerInfo().profileFd = null;
                    if (processRecord.getPid() == com.android.server.am.ActivityManagerService.MY_PID) {
                        profilerInfo = null;
                    }
                } else {
                    stopProfilerLPf(processRecord, i);
                    if (profilerInfo != null && profilerInfo.profileFd != null) {
                        try {
                            profilerInfo.profileFd.close();
                        } catch (java.io.IOException e3) {
                        }
                    }
                }
                if (profilerInfo == null || profilerInfo.profileFd == null) {
                    return true;
                }
                try {
                    profilerInfo.profileFd.close();
                    return true;
                } catch (java.io.IOException e4) {
                    return true;
                }
            } catch (android.os.RemoteException e5) {
                throw new java.lang.IllegalStateException("Process disappeared");
            }
        } catch (java.lang.Throwable th) {
            if (profilerInfo != null && profilerInfo.profileFd != null) {
                try {
                    profilerInfo.profileFd.close();
                } catch (java.io.IOException e6) {
                }
            }
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setProfileAppLPf(java.lang.String str, android.app.ProfilerInfo profilerInfo) {
        this.mProfileData.setProfileApp(str);
        if (this.mProfileData.getProfilerInfo() != null && this.mProfileData.getProfilerInfo().profileFd != null) {
            try {
                this.mProfileData.getProfilerInfo().profileFd.close();
            } catch (java.io.IOException e) {
            }
        }
        this.mProfileData.setProfilerInfo(new android.app.ProfilerInfo(profilerInfo));
        this.mProfileType = 0;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setProfileProcLPf(com.android.server.am.ProcessRecord processRecord) {
        this.mProfileData.setProfileProc(processRecord);
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setAgentAppLPf(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        if (str2 == null) {
            if (this.mAppAgentMap != null) {
                this.mAppAgentMap.remove(str);
                if (this.mAppAgentMap.isEmpty()) {
                    this.mAppAgentMap = null;
                    return;
                }
                return;
            }
            return;
        }
        if (this.mAppAgentMap == null) {
            this.mAppAgentMap = new java.util.HashMap();
        }
        if (this.mAppAgentMap.size() >= 100) {
            android.util.Slog.e("ActivityManager", "App agent map has too many entries, cannot add " + str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str2);
            return;
        }
        this.mAppAgentMap.put(str, str2);
    }

    void updateCpuStats() {
        if (this.mLastCpuTime.get() < android.os.SystemClock.uptimeMillis() - MONITOR_CPU_MIN_TIME && this.mProcessCpuMutexFree.compareAndSet(true, false)) {
            synchronized (this.mProcessCpuThread) {
                this.mProcessCpuThread.notify();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void updateCpuStatsNow() {
        com.android.server.power.stats.BatteryStatsImpl activeStatistics;
        long j;
        com.android.server.am.ActivityManagerService.PidMap pidMap;
        int i;
        long j2;
        com.android.internal.os.ProcessCpuTracker.Stats stats;
        long j3;
        com.android.server.am.ProcessProfileRecord processProfileRecord;
        boolean z = true;
        int i2 = 0;
        boolean z2 = this.mService.mSystemReady && android.util.FeatureFlagUtils.isEnabled(this.mService.mContext, "settings_enable_monitor_phantom_procs");
        synchronized (this.mProcessCpuTracker) {
            try {
                this.mProcessCpuMutexFree.set(false);
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                if (this.mLastCpuTime.get() < uptimeMillis - MONITOR_CPU_MIN_TIME) {
                    this.mLastCpuTime.set(uptimeMillis);
                    this.mProcessCpuTracker.update();
                    if (this.mProcessCpuTracker.hasGoodLastStats()) {
                        if ("true".equals(android.os.SystemProperties.get("events.cpu"))) {
                            int lastUserTime = this.mProcessCpuTracker.getLastUserTime();
                            int lastSystemTime = this.mProcessCpuTracker.getLastSystemTime();
                            int lastIoWaitTime = this.mProcessCpuTracker.getLastIoWaitTime();
                            int lastIrqTime = this.mProcessCpuTracker.getLastIrqTime();
                            int lastSoftIrqTime = this.mProcessCpuTracker.getLastSoftIrqTime();
                            int i3 = lastUserTime + lastSystemTime + lastIoWaitTime + lastIrqTime + lastSoftIrqTime;
                            int lastIdleTime = this.mProcessCpuTracker.getLastIdleTime() + i3;
                            if (lastIdleTime == 0) {
                                lastIdleTime = 1;
                            }
                            com.android.server.am.EventLogTags.writeCpu((i3 * 100) / lastIdleTime, (lastUserTime * 100) / lastIdleTime, (lastSystemTime * 100) / lastIdleTime, (lastIoWaitTime * 100) / lastIdleTime, (lastIrqTime * 100) / lastIdleTime, (lastSoftIrqTime * 100) / lastIdleTime);
                        }
                        if (z2 && z) {
                            this.mService.mPhantomProcessList.updateProcessCpuStatesLocked(this.mProcessCpuTracker);
                        }
                        activeStatistics = this.mService.mBatteryStatsService.getActiveStatistics();
                        synchronized (activeStatistics) {
                            if (z) {
                                if (activeStatistics.startAddingCpuStatsLocked()) {
                                    int countStats = this.mProcessCpuTracker.countStats();
                                    long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                                    long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
                                    com.android.server.am.ActivityManagerService.PidMap pidMap2 = this.mService.mPidsSelfLocked;
                                    synchronized (pidMap2) {
                                        int i4 = 0;
                                        int i5 = 0;
                                        while (i2 < countStats) {
                                            try {
                                                com.android.internal.os.ProcessCpuTracker.Stats stats2 = this.mProcessCpuTracker.getStats(i2);
                                                if (stats2.working) {
                                                    com.android.server.am.ProcessRecord processRecord = this.mService.mPidsSelfLocked.get(stats2.pid);
                                                    int i6 = i4 + stats2.rel_utime;
                                                    int i7 = i5 + stats2.rel_stime;
                                                    if (processRecord != null) {
                                                        com.android.server.am.ProcessProfileRecord processProfileRecord2 = processRecord.mProfile;
                                                        com.android.server.power.stats.BatteryStatsImpl.Uid.Proc curProcBatteryStats = processProfileRecord2.getCurProcBatteryStats();
                                                        if (curProcBatteryStats == null || !curProcBatteryStats.isActive()) {
                                                            i = countStats;
                                                            processProfileRecord = processProfileRecord2;
                                                            j2 = uptimeMillis;
                                                            pidMap = pidMap2;
                                                            stats = stats2;
                                                            try {
                                                                curProcBatteryStats = activeStatistics.getProcessStatsLocked(processRecord.info.uid, processRecord.processName, elapsedRealtime, uptimeMillis2);
                                                                processProfileRecord.setCurProcBatteryStats(curProcBatteryStats);
                                                            } catch (java.lang.Throwable th) {
                                                                th = th;
                                                                throw th;
                                                            }
                                                        } else {
                                                            i = countStats;
                                                            j2 = uptimeMillis;
                                                            processProfileRecord = processProfileRecord2;
                                                            pidMap = pidMap2;
                                                            stats = stats2;
                                                        }
                                                        curProcBatteryStats.addCpuTimeLocked(stats.rel_utime, stats.rel_stime);
                                                        processProfileRecord.mLastCpuTime.compareAndSet(0L, processProfileRecord.mCurCpuTime.addAndGet(stats.rel_utime + stats.rel_stime));
                                                    } else {
                                                        i = countStats;
                                                        j2 = uptimeMillis;
                                                        pidMap = pidMap2;
                                                        stats = stats2;
                                                        com.android.server.power.stats.BatteryStatsImpl.Uid.Proc proc = (com.android.server.power.stats.BatteryStatsImpl.Uid.Proc) stats.batteryStats;
                                                        if (proc == null || !proc.isActive()) {
                                                            proc = activeStatistics.getProcessStatsLocked(stats.uid, stats.name, elapsedRealtime, uptimeMillis2);
                                                            stats.batteryStats = proc;
                                                        }
                                                        proc.addCpuTimeLocked(stats.rel_utime, stats.rel_stime);
                                                    }
                                                    j3 = elapsedRealtime;
                                                    com.android.server.am.EventLogTags.writeAmCpu(stats.pid, stats.uid, stats.baseName, stats.rel_uptime, stats.rel_utime, stats.rel_stime);
                                                    i4 = i6;
                                                    i5 = i7;
                                                } else {
                                                    i = countStats;
                                                    j2 = uptimeMillis;
                                                    pidMap = pidMap2;
                                                    j3 = elapsedRealtime;
                                                }
                                                i2++;
                                                countStats = i;
                                                pidMap2 = pidMap;
                                                uptimeMillis = j2;
                                                elapsedRealtime = j3;
                                            } catch (java.lang.Throwable th2) {
                                                th = th2;
                                                pidMap = pidMap2;
                                            }
                                        }
                                        j = uptimeMillis;
                                        activeStatistics.addCpuStatsLocked(i4, i5, this.mProcessCpuTracker.getLastUserTime(), this.mProcessCpuTracker.getLastSystemTime(), this.mProcessCpuTracker.getLastIoWaitTime(), this.mProcessCpuTracker.getLastIrqTime(), this.mProcessCpuTracker.getLastSoftIrqTime(), this.mProcessCpuTracker.getLastIdleTime());
                                    }
                                } else {
                                    j = uptimeMillis;
                                }
                                activeStatistics.finishAddingCpuStatsLocked();
                            } else {
                                j = uptimeMillis;
                            }
                            if (this.mLastWriteTime < j - 1800000) {
                                this.mLastWriteTime = j;
                                this.mService.mBatteryStatsService.scheduleWriteToDisk();
                            }
                        }
                    }
                }
                z = false;
                if (z2) {
                    this.mService.mPhantomProcessList.updateProcessCpuStatesLocked(this.mProcessCpuTracker);
                }
                activeStatistics = this.mService.mBatteryStatsService.getActiveStatistics();
                synchronized (activeStatistics) {
                }
            } finally {
            }
        }
    }

    long getCpuTimeForPid(int i) {
        return this.mProcessCpuTracker.getCpuTimeForPid(i);
    }

    long getCpuDelayTimeForPid(int i) {
        return this.mProcessCpuTracker.getCpuDelayTimeForPid(i);
    }

    java.util.List<com.android.internal.os.ProcessCpuTracker.Stats> getCpuStats(final java.util.function.Predicate<com.android.internal.os.ProcessCpuTracker.Stats> predicate) {
        java.util.List<com.android.internal.os.ProcessCpuTracker.Stats> stats;
        synchronized (this.mProcessCpuTracker) {
            stats = this.mProcessCpuTracker.getStats(new com.android.internal.os.ProcessCpuTracker.FilterStats() { // from class: com.android.server.am.AppProfiler$$ExternalSyntheticLambda8
                public final boolean needed(com.android.internal.os.ProcessCpuTracker.Stats stats2) {
                    boolean test;
                    test = predicate.test(stats2);
                    return test;
                }
            });
        }
        return stats;
    }

    void forAllCpuStats(java.util.function.Consumer<com.android.internal.os.ProcessCpuTracker.Stats> consumer) {
        synchronized (this.mProcessCpuTracker) {
            try {
                int countStats = this.mProcessCpuTracker.countStats();
                for (int i = 0; i < countStats; i++) {
                    consumer.accept(this.mProcessCpuTracker.getStats(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class ProcessCpuThread extends java.lang.Thread {
        ProcessCpuThread(java.lang.String str) {
            super(str);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            synchronized (com.android.server.am.AppProfiler.this.mProcessCpuTracker) {
                com.android.server.am.AppProfiler.this.mProcessCpuInitLatch.countDown();
                com.android.server.am.AppProfiler.this.mProcessCpuTracker.init();
            }
            while (true) {
                try {
                    try {
                        synchronized (this) {
                            try {
                                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                                long j = (com.android.server.am.AppProfiler.this.mLastCpuTime.get() + com.android.server.am.AppProfiler.MONITOR_CPU_MAX_TIME) - uptimeMillis;
                                long j2 = (com.android.server.am.AppProfiler.this.mLastWriteTime + 1800000) - uptimeMillis;
                                if (j2 < j) {
                                    j = j2;
                                }
                                if (j > 0) {
                                    com.android.server.am.AppProfiler.this.mProcessCpuMutexFree.set(true);
                                    wait(j);
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                    } catch (java.lang.InterruptedException e) {
                    }
                    com.android.server.am.AppProfiler.this.updateCpuStatsNow();
                } catch (java.lang.Exception e2) {
                    android.util.Slog.e("ActivityManager", "Unexpected exception collecting process stats", e2);
                }
            }
        }
    }

    class CpuBinder extends android.os.Binder {
        private final com.android.server.utils.PriorityDump.PriorityDumper mPriorityDumper = new com.android.server.utils.PriorityDump.PriorityDumper() { // from class: com.android.server.am.AppProfiler.CpuBinder.1
            @Override // com.android.server.utils.PriorityDump.PriorityDumper
            public void dumpCritical(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
                if (!com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(com.android.server.am.AppProfiler.this.mService.mContext, "cpuinfo", printWriter)) {
                    return;
                }
                synchronized (com.android.server.am.AppProfiler.this.mProcessCpuTracker) {
                    try {
                        if (z) {
                            com.android.server.am.AppProfiler.this.mProcessCpuTracker.dumpProto(fileDescriptor);
                        } else {
                            printWriter.print(com.android.server.am.AppProfiler.this.mProcessCpuTracker.printCurrentLoad());
                            printWriter.print(com.android.server.am.AppProfiler.this.mProcessCpuTracker.printCurrentState(android.os.SystemClock.uptimeMillis()));
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };

        CpuBinder() {
        }

        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            com.android.server.utils.PriorityDump.dump(this.mPriorityDumper, fileDescriptor, printWriter, strArr);
        }
    }

    void setCpuInfoService() {
        android.os.ServiceManager.addService("cpuinfo", new com.android.server.am.AppProfiler.CpuBinder(), false, 1);
    }

    AppProfiler(com.android.server.am.ActivityManagerService activityManagerService, android.os.Looper looper, com.android.server.am.LowMemDetector lowMemDetector) {
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.mBgHandler = new com.android.server.am.AppProfiler.BgHandler(looper);
        this.mLowMemDetector = lowMemDetector;
    }

    void retrieveSettings() {
        long j = android.provider.DeviceConfig.getLong("activity_manager", ACTIVITY_START_PSS_DEFER_CONFIG, 0L);
        android.provider.DeviceConfig.addOnPropertiesChangedListener("activity_manager", android.app.ActivityThread.currentApplication().getMainExecutor(), this.mPssDelayConfigListener);
        this.mPssDeferralTime = j;
    }

    void onActivityManagerInternalAdded() {
        this.mProcessCpuThread.start();
        try {
            this.mProcessCpuInitLatch.await();
        } catch (java.lang.InterruptedException e) {
            android.util.Slog.wtf("ActivityManager", "Interrupted wait during start", e);
            java.lang.Thread.currentThread().interrupt();
            throw new java.lang.IllegalStateException("Interrupted wait during start");
        }
    }

    void onActivityLaunched() {
        if (this.mPssDeferralTime > 0) {
            this.mBgHandler.sendMessageAtFrontOfQueue(this.mBgHandler.obtainMessage(2));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004f A[Catch: all -> 0x0049, TryCatch #1 {all -> 0x0049, blocks: (B:4:0x000e, B:6:0x0019, B:8:0x0025, B:10:0x0032, B:12:0x003c, B:17:0x004f, B:18:0x005c, B:20:0x0066, B:22:0x0093, B:24:0x0097, B:26:0x009f, B:28:0x00a5, B:30:0x00af, B:31:0x00ca, B:33:0x00ce, B:35:0x00dc, B:37:0x00e0, B:39:0x00f4, B:41:0x00fc, B:42:0x00ff, B:96:0x0076, B:98:0x007a), top: B:3:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0066 A[Catch: all -> 0x0049, TryCatch #1 {all -> 0x0049, blocks: (B:4:0x000e, B:6:0x0019, B:8:0x0025, B:10:0x0032, B:12:0x003c, B:17:0x004f, B:18:0x005c, B:20:0x0066, B:22:0x0093, B:24:0x0097, B:26:0x009f, B:28:0x00a5, B:30:0x00af, B:31:0x00ca, B:33:0x00ce, B:35:0x00dc, B:37:0x00e0, B:39:0x00f4, B:41:0x00fc, B:42:0x00ff, B:96:0x0076, B:98:0x007a), top: B:3:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0097 A[Catch: all -> 0x0049, TryCatch #1 {all -> 0x0049, blocks: (B:4:0x000e, B:6:0x0019, B:8:0x0025, B:10:0x0032, B:12:0x003c, B:17:0x004f, B:18:0x005c, B:20:0x0066, B:22:0x0093, B:24:0x0097, B:26:0x009f, B:28:0x00a5, B:30:0x00af, B:31:0x00ca, B:33:0x00ce, B:35:0x00dc, B:37:0x00e0, B:39:0x00f4, B:41:0x00fc, B:42:0x00ff, B:96:0x0076, B:98:0x007a), top: B:3:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00af A[Catch: all -> 0x0049, TryCatch #1 {all -> 0x0049, blocks: (B:4:0x000e, B:6:0x0019, B:8:0x0025, B:10:0x0032, B:12:0x003c, B:17:0x004f, B:18:0x005c, B:20:0x0066, B:22:0x0093, B:24:0x0097, B:26:0x009f, B:28:0x00a5, B:30:0x00af, B:31:0x00ca, B:33:0x00ce, B:35:0x00dc, B:37:0x00e0, B:39:0x00f4, B:41:0x00fc, B:42:0x00ff, B:96:0x0076, B:98:0x007a), top: B:3:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00ca A[Catch: all -> 0x0049, TryCatch #1 {all -> 0x0049, blocks: (B:4:0x000e, B:6:0x0019, B:8:0x0025, B:10:0x0032, B:12:0x003c, B:17:0x004f, B:18:0x005c, B:20:0x0066, B:22:0x0093, B:24:0x0097, B:26:0x009f, B:28:0x00a5, B:30:0x00af, B:31:0x00ca, B:33:0x00ce, B:35:0x00dc, B:37:0x00e0, B:39:0x00f4, B:41:0x00fc, B:42:0x00ff, B:96:0x0076, B:98:0x007a), top: B:3:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00dc A[Catch: all -> 0x0049, TryCatch #1 {all -> 0x0049, blocks: (B:4:0x000e, B:6:0x0019, B:8:0x0025, B:10:0x0032, B:12:0x003c, B:17:0x004f, B:18:0x005c, B:20:0x0066, B:22:0x0093, B:24:0x0097, B:26:0x009f, B:28:0x00a5, B:30:0x00af, B:31:0x00ca, B:33:0x00ce, B:35:0x00dc, B:37:0x00e0, B:39:0x00f4, B:41:0x00fc, B:42:0x00ff, B:96:0x0076, B:98:0x007a), top: B:3:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x005b  */
    @com.android.internal.annotations.GuardedBy({"mService"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    android.app.ProfilerInfo setupProfilerInfoLocked(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread, com.android.server.am.ProcessRecord processRecord, com.android.server.am.ActiveInstrumentation activeInstrumentation) throws java.io.IOException, android.os.RemoteException {
        android.app.ProfilerInfo profilerInfo;
        android.app.ProfilerInfo profilerInfo2;
        boolean z;
        java.lang.String str = processRecord.processName;
        synchronized (this.mProfilerLock) {
            try {
                if (this.mProfileData.getProfileApp() != null && this.mProfileData.getProfileApp().equals(str)) {
                    this.mProfileData.setProfileProc(processRecord);
                    if (this.mProfileData.getProfilerInfo() != null) {
                        if (this.mProfileData.getProfilerInfo().profileFile == null && !this.mProfileData.getProfilerInfo().attachAgentDuringBind) {
                            z = false;
                            profilerInfo = !z ? new android.app.ProfilerInfo(this.mProfileData.getProfilerInfo()) : null;
                            profilerInfo2 = this.mProfileData.getProfilerInfo().agent != null ? this.mProfileData.getProfilerInfo().agent : null;
                            if (this.mAppAgentMap != null) {
                            }
                            if (profilerInfo != null) {
                            }
                        }
                        z = true;
                        if (!z) {
                        }
                        profilerInfo = !z ? new android.app.ProfilerInfo(this.mProfileData.getProfilerInfo()) : null;
                        profilerInfo2 = this.mProfileData.getProfilerInfo().agent != null ? this.mProfileData.getProfilerInfo().agent : null;
                        if (this.mAppAgentMap != null) {
                        }
                        if (profilerInfo != null) {
                        }
                    }
                    profilerInfo2 = null;
                    if (this.mAppAgentMap != null) {
                    }
                    if (profilerInfo != null) {
                    }
                } else {
                    if (activeInstrumentation != null && activeInstrumentation.mProfileFile != null) {
                        profilerInfo = new android.app.ProfilerInfo(activeInstrumentation.mProfileFile, (android.os.ParcelFileDescriptor) null, 0, false, false, (java.lang.String) null, false, 0);
                        profilerInfo2 = null;
                        if (this.mAppAgentMap != null && this.mAppAgentMap.containsKey(str) && processRecord.isDebuggable()) {
                            this.mAppAgentMap.get(str);
                            if (profilerInfo != null) {
                                profilerInfo = new android.app.ProfilerInfo((java.lang.String) null, (android.os.ParcelFileDescriptor) null, 0, false, false, this.mAppAgentMap.get(str), true, 0);
                            } else if (profilerInfo.agent == null) {
                                profilerInfo = profilerInfo.setAgent(this.mAppAgentMap.get(str), true);
                            }
                        }
                        if (profilerInfo != null && profilerInfo.profileFd != null) {
                            profilerInfo.profileFd = profilerInfo.profileFd.dup();
                            if (android.text.TextUtils.equals(this.mProfileData.getProfileApp(), str) && this.mProfileData.getProfilerInfo() != null) {
                                clearProfilerLPf();
                            }
                        }
                    }
                    profilerInfo2 = null;
                    if (this.mAppAgentMap != null) {
                        this.mAppAgentMap.get(str);
                        if (profilerInfo != null) {
                        }
                    }
                    if (profilerInfo != null) {
                        profilerInfo.profileFd = profilerInfo.profileFd.dup();
                        if (android.text.TextUtils.equals(this.mProfileData.getProfileApp(), str)) {
                            clearProfilerLPf();
                        }
                    }
                }
            } finally {
            }
        }
        if (this.mService.mActiveInstrumentation.size() > 0 && activeInstrumentation == null) {
            for (int size = this.mService.mActiveInstrumentation.size() - 1; size >= 0 && processRecord.getActiveInstrumentation() == null; size--) {
                com.android.server.am.ActiveInstrumentation activeInstrumentation2 = this.mService.mActiveInstrumentation.get(size);
                if (!activeInstrumentation2.mFinished && activeInstrumentation2.mTargetInfo.uid == processRecord.uid) {
                    com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                    com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                    synchronized (activityManagerGlobalLock) {
                        try {
                            if (activeInstrumentation2.mTargetProcesses.length == 0) {
                                if (activeInstrumentation2.mTargetInfo.packageName.equals(processRecord.info.packageName)) {
                                    processRecord.setActiveInstrumentation(activeInstrumentation2);
                                    activeInstrumentation2.mRunningProcesses.add(processRecord);
                                }
                            } else {
                                java.lang.String[] strArr = activeInstrumentation2.mTargetProcesses;
                                int length = strArr.length;
                                int i = 0;
                                while (true) {
                                    if (i >= length) {
                                        break;
                                    }
                                    if (!strArr[i].equals(processRecord.processName)) {
                                        i++;
                                    } else {
                                        processRecord.setActiveInstrumentation(activeInstrumentation2);
                                        activeInstrumentation2.mRunningProcesses.add(processRecord);
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
                }
            }
        }
        if (profilerInfo2 != null) {
            iApplicationThread.attachAgent(profilerInfo2);
        }
        if (processRecord.isDebuggable()) {
            iApplicationThread.attachStartupAgents(processRecord.info.dataDir);
        }
        return profilerInfo;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void onCleanupApplicationRecordLocked(com.android.server.am.ProcessRecord processRecord) {
        synchronized (this.mProfilerLock) {
            com.android.server.am.ProcessProfileRecord processProfileRecord = processRecord.mProfile;
            this.mProcessesToGc.remove(processRecord);
            this.mPendingPssOrRssProfiles.remove(processProfileRecord);
            processProfileRecord.abortNextPssTime();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void onAppDiedLocked(com.android.server.am.ProcessRecord processRecord) {
        synchronized (this.mProfilerLock) {
            try {
                if (this.mProfileData.getProfileProc() == processRecord) {
                    clearProfilerLPf();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    boolean dumpMemWatchProcessesLPf(java.io.PrintWriter printWriter, boolean z) {
        if (this.mMemWatchProcesses.getMap().size() > 0) {
            printWriter.println("  Mem watch processes:");
            android.util.ArrayMap map = this.mMemWatchProcesses.getMap();
            for (int size = map.size() - 1; size >= 0; size--) {
                java.lang.String str = (java.lang.String) map.keyAt(size);
                android.util.SparseArray sparseArray = (android.util.SparseArray) map.valueAt(size);
                for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
                    if (z) {
                        printWriter.println();
                        z = false;
                    }
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("    ");
                    sb.append(str);
                    sb.append('/');
                    android.os.UserHandle.formatUid(sb, sparseArray.keyAt(size2));
                    android.util.Pair pair = (android.util.Pair) sparseArray.valueAt(size2);
                    sb.append(": ");
                    android.util.DebugUtils.sizeValueToString(((java.lang.Long) pair.first).longValue(), sb);
                    if (pair.second != null) {
                        sb.append(", report to ");
                        sb.append((java.lang.String) pair.second);
                    }
                    printWriter.println(sb.toString());
                }
            }
            printWriter.print("  mMemWatchDumpProcName=");
            printWriter.println(this.mMemWatchDumpProcName);
            printWriter.print("  mMemWatchDumpUri=");
            printWriter.println(this.mMemWatchDumpUri);
            printWriter.print("  mMemWatchDumpPid=");
            printWriter.println(this.mMemWatchDumpPid);
            printWriter.print("  mMemWatchDumpUid=");
            printWriter.println(this.mMemWatchDumpUid);
            printWriter.print("  mMemWatchIsUserInitiated=");
            printWriter.println(this.mMemWatchIsUserInitiated);
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean dumpProfileDataLocked(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        if ((this.mProfileData.getProfileApp() != null || this.mProfileData.getProfileProc() != null || (this.mProfileData.getProfilerInfo() != null && (this.mProfileData.getProfilerInfo().profileFile != null || this.mProfileData.getProfilerInfo().profileFd != null))) && (str == null || str.equals(this.mProfileData.getProfileApp()))) {
            if (z) {
                printWriter.println();
                z = false;
            }
            printWriter.println("  mProfileApp=" + this.mProfileData.getProfileApp() + " mProfileProc=" + this.mProfileData.getProfileProc());
            if (this.mProfileData.getProfilerInfo() != null) {
                printWriter.println("  mProfileFile=" + this.mProfileData.getProfilerInfo().profileFile + " mProfileFd=" + this.mProfileData.getProfilerInfo().profileFd);
                printWriter.println("  mSamplingInterval=" + this.mProfileData.getProfilerInfo().samplingInterval + " mAutoStopProfiler=" + this.mProfileData.getProfilerInfo().autoStopProfiler + " mStreamingOutput=" + this.mProfileData.getProfilerInfo().streamingOutput + " mClockType=" + this.mProfileData.getProfilerInfo().clockType);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("  mProfileType=");
                sb.append(this.mProfileType);
                printWriter.println(sb.toString());
            }
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void dumpLastMemoryLevelLocked(java.io.PrintWriter printWriter) {
        switch (this.mLastMemoryLevel) {
            case 0:
                printWriter.println("normal)");
                break;
            case 1:
                printWriter.println("moderate)");
                break;
            case 2:
                printWriter.println("low)");
                break;
            case 3:
                printWriter.println("critical)");
                break;
            default:
                printWriter.print(this.mLastMemoryLevel);
                printWriter.println(")");
                break;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void dumpMemoryLevelsLocked(java.io.PrintWriter printWriter) {
        printWriter.println("  mAllowLowerMemLevel=" + this.mAllowLowerMemLevel + " mLastMemoryLevel=" + this.mLastMemoryLevel + " mLastNumProcesses=" + this.mLastNumProcesses);
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void writeMemWatchProcessToProtoLPf(android.util.proto.ProtoOutputStream protoOutputStream) {
        if (this.mMemWatchProcesses.getMap().size() > 0) {
            long start = protoOutputStream.start(1146756268064L);
            android.util.ArrayMap map = this.mMemWatchProcesses.getMap();
            for (int i = 0; i < map.size(); i++) {
                java.lang.String str = (java.lang.String) map.keyAt(i);
                android.util.SparseArray sparseArray = (android.util.SparseArray) map.valueAt(i);
                long start2 = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1138166333441L, str);
                for (int size = sparseArray.size() - 1; size >= 0; size--) {
                    long start3 = protoOutputStream.start(2246267895810L);
                    android.util.Pair pair = (android.util.Pair) sparseArray.valueAt(size);
                    protoOutputStream.write(1120986464257L, sparseArray.keyAt(size));
                    protoOutputStream.write(1138166333442L, android.util.DebugUtils.sizeValueToString(((java.lang.Long) pair.first).longValue(), new java.lang.StringBuilder()));
                    protoOutputStream.write(1138166333443L, (java.lang.String) pair.second);
                    protoOutputStream.end(start3);
                }
                protoOutputStream.end(start2);
            }
            long start4 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1138166333441L, this.mMemWatchDumpProcName);
            protoOutputStream.write(1138166333446L, this.mMemWatchDumpUri.toString());
            protoOutputStream.write(1120986464259L, this.mMemWatchDumpPid);
            protoOutputStream.write(1120986464260L, this.mMemWatchDumpUid);
            protoOutputStream.write(1133871366149L, this.mMemWatchIsUserInitiated);
            protoOutputStream.end(start4);
            protoOutputStream.end(start);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void writeProfileDataToProtoLocked(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.String str) {
        if (this.mProfileData.getProfileApp() == null && this.mProfileData.getProfileProc() == null) {
            if (this.mProfileData.getProfilerInfo() != null) {
                if (this.mProfileData.getProfilerInfo().profileFile == null && this.mProfileData.getProfilerInfo().profileFd == null) {
                    return;
                }
            } else {
                return;
            }
        }
        if (str == null || str.equals(this.mProfileData.getProfileApp())) {
            long start = protoOutputStream.start(1146756268066L);
            protoOutputStream.write(1138166333441L, this.mProfileData.getProfileApp());
            this.mProfileData.getProfileProc().dumpDebug(protoOutputStream, 1146756268034L);
            if (this.mProfileData.getProfilerInfo() != null) {
                this.mProfileData.getProfilerInfo().dumpDebug(protoOutputStream, 1146756268035L);
                protoOutputStream.write(1120986464260L, this.mProfileType);
            }
            protoOutputStream.end(start);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void writeMemoryLevelsToProtoLocked(android.util.proto.ProtoOutputStream protoOutputStream) {
        protoOutputStream.write(1133871366199L, this.mAllowLowerMemLevel);
        protoOutputStream.write(1120986464312L, this.mLastMemoryLevel);
        protoOutputStream.write(1120986464313L, this.mLastNumProcesses);
    }

    void printCurrentCpuState(java.lang.StringBuilder sb, long j) {
        synchronized (this.mProcessCpuTracker) {
            sb.append(this.mProcessCpuTracker.printCurrentState(j, 10));
        }
    }

    android.util.Pair<java.lang.String, java.lang.String> getAppProfileStatsForDebugging(long j, int i) {
        java.lang.String printCurrentLoad;
        java.lang.String printCurrentState;
        synchronized (this.mProcessCpuTracker) {
            updateCpuStatsNow();
            printCurrentLoad = this.mProcessCpuTracker.printCurrentLoad();
            printCurrentState = this.mProcessCpuTracker.printCurrentState(j);
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 > i) {
                break;
            }
            int indexOf = printCurrentState.indexOf(10, i3);
            if (indexOf == -1) {
                i3 = printCurrentState.length();
                break;
            }
            i3 = indexOf + 1;
            i2++;
        }
        return new android.util.Pair<>(printCurrentLoad, printCurrentState.substring(0, i3));
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void writeProcessesToGcToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.lang.String str) {
        if (this.mProcessesToGc.size() > 0) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            int size = this.mProcessesToGc.size();
            for (int i = 0; i < size; i++) {
                com.android.server.am.ProcessRecord processRecord = this.mProcessesToGc.get(i);
                if (str == null || str.equals(processRecord.info.packageName)) {
                    long start = protoOutputStream.start(j);
                    com.android.server.am.ProcessProfileRecord processProfileRecord = processRecord.mProfile;
                    processRecord.dumpDebug(protoOutputStream, 1146756268033L);
                    protoOutputStream.write(1133871366146L, processProfileRecord.getReportLowMemory());
                    protoOutputStream.write(1112396529667L, uptimeMillis);
                    protoOutputStream.write(1112396529668L, processProfileRecord.getLastRequestedGc());
                    protoOutputStream.write(1112396529669L, processProfileRecord.getLastLowMemory());
                    protoOutputStream.end(start);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    boolean dumpProcessesToGc(java.io.PrintWriter printWriter, boolean z, java.lang.String str) {
        if (this.mProcessesToGc.size() > 0) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            int size = this.mProcessesToGc.size();
            boolean z2 = false;
            for (int i = 0; i < size; i++) {
                com.android.server.am.ProcessRecord processRecord = this.mProcessesToGc.get(i);
                if (str == null || str.equals(processRecord.info.packageName)) {
                    if (!z2) {
                        if (z) {
                            printWriter.println();
                        }
                        printWriter.println("  Processes that are waiting to GC:");
                        z = true;
                        z2 = true;
                    }
                    printWriter.print("    Process ");
                    printWriter.println(processRecord);
                    com.android.server.am.ProcessProfileRecord processProfileRecord = processRecord.mProfile;
                    printWriter.print("      lowMem=");
                    printWriter.print(processProfileRecord.getReportLowMemory());
                    printWriter.print(", last gced=");
                    printWriter.print(uptimeMillis - processProfileRecord.getLastRequestedGc());
                    printWriter.print(" ms ago, last lowMem=");
                    printWriter.print(uptimeMillis - processProfileRecord.getLastLowMemory());
                    printWriter.println(" ms ago");
                }
            }
        }
        return z;
    }
}
