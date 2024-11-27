package com.android.server.am;

/* loaded from: classes.dex */
final class ProcessProfileRecord {
    final com.android.server.am.ProcessRecord mApp;

    @com.android.internal.annotations.GuardedBy({"mService.mProcessStats.mLock"})
    private com.android.internal.app.procstats.ProcessState mBaseProcessTracker;
    private com.android.server.power.stats.BatteryStatsImpl.Uid.Proc mCurProcBatteryStats;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private int mCurRawAdj;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mInitialIdlePssOrRss;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mLastCachedPss;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mLastCachedRss;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mLastCachedSwapPss;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProfilerLock"})
    private long mLastLowMemory;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private android.os.Debug.MemoryInfo mLastMemInfo;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mLastMemInfoTime;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mLastPss;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mLastPssTime;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mLastRequestedGc;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mLastRss;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mLastStateTime;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mLastSwapPss;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private long mNextPssTime;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mPendingUiClean;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private int mPid;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    final java.lang.Object mProfilerLock;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private int mPssStatType;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private boolean mReportLowMemory;
    private final com.android.server.am.ActivityManagerService mService;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private int mSetAdj;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private int mSetProcState;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private android.app.IApplicationThread mThread;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mTrimMemoryLevel;

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private final com.android.server.am.ProcessList.ProcStateMemTracker mProcStateMemTracker = new com.android.server.am.ProcessList.ProcStateMemTracker();

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    private int mPssProcState = 20;
    final java.util.concurrent.atomic.AtomicLong mLastCpuTime = new java.util.concurrent.atomic.AtomicLong(0);
    final java.util.concurrent.atomic.AtomicLong mCurCpuTime = new java.util.concurrent.atomic.AtomicLong(0);
    final java.util.concurrent.atomic.AtomicLong mLastCpuDelayTime = new java.util.concurrent.atomic.AtomicLong(0);
    private java.util.concurrent.atomic.AtomicInteger mCurrentHostingComponentTypes = new java.util.concurrent.atomic.AtomicInteger(0);
    private java.util.concurrent.atomic.AtomicInteger mHistoricalHostingComponentTypes = new java.util.concurrent.atomic.AtomicInteger(0);

    ProcessProfileRecord(com.android.server.am.ProcessRecord processRecord) {
        this.mApp = processRecord;
        this.mService = processRecord.mService;
        this.mProcLock = this.mService.mProcLock;
        this.mProfilerLock = this.mService.mAppProfiler.mProfilerLock;
    }

    void init(long j) {
        this.mNextPssTime = j;
        this.mLastPssTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mService.mProcessStats.mLock"})
    com.android.internal.app.procstats.ProcessState getBaseProcessTracker() {
        return this.mBaseProcessTracker;
    }

    @com.android.internal.annotations.GuardedBy({"mService.mProcessStats.mLock"})
    void setBaseProcessTracker(com.android.internal.app.procstats.ProcessState processState) {
        this.mBaseProcessTracker = processState;
    }

    void onProcessFrozen() {
        synchronized (this.mService.mProcessStats.mLock) {
            try {
                com.android.internal.app.procstats.ProcessState processState = this.mBaseProcessTracker;
                if (processState != null) {
                    com.android.server.am.PackageList pkgList = this.mApp.getPkgList();
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    synchronized (pkgList) {
                        processState.onProcessFrozen(uptimeMillis, pkgList.getPackageListLocked());
                    }
                }
            } finally {
            }
        }
    }

    void onProcessUnfrozen() {
        synchronized (this.mService.mProcessStats.mLock) {
            try {
                com.android.internal.app.procstats.ProcessState processState = this.mBaseProcessTracker;
                if (processState != null) {
                    com.android.server.am.PackageList pkgList = this.mApp.getPkgList();
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    synchronized (pkgList) {
                        processState.onProcessUnfrozen(uptimeMillis, pkgList.getPackageListLocked());
                    }
                }
            } finally {
            }
        }
    }

    void onProcessActive(android.app.IApplicationThread iApplicationThread, final com.android.server.am.ProcessStatsService processStatsService) {
        if (this.mThread == null) {
            synchronized (this.mProfilerLock) {
                synchronized (processStatsService.mLock) {
                    final com.android.internal.app.procstats.ProcessState baseProcessTracker = getBaseProcessTracker();
                    com.android.server.am.PackageList pkgList = this.mApp.getPkgList();
                    if (baseProcessTracker != null) {
                        synchronized (pkgList) {
                            baseProcessTracker.setState(-1, processStatsService.getMemFactorLocked(), android.os.SystemClock.uptimeMillis(), pkgList.getPackageListLocked());
                        }
                        baseProcessTracker.makeInactive();
                    }
                    android.content.pm.ApplicationInfo applicationInfo = this.mApp.info;
                    final int uidForAttribution = getUidForAttribution(this.mApp);
                    final com.android.internal.app.procstats.ProcessState processStateLocked = processStatsService.getProcessStateLocked(applicationInfo.packageName, uidForAttribution, applicationInfo.longVersionCode, this.mApp.processName);
                    setBaseProcessTracker(processStateLocked);
                    processStateLocked.makeActive();
                    pkgList.forEachPackage(new java.util.function.BiConsumer() { // from class: com.android.server.am.ProcessProfileRecord$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            com.android.server.am.ProcessProfileRecord.this.lambda$onProcessActive$0(baseProcessTracker, processStatsService, uidForAttribution, processStateLocked, (java.lang.String) obj, (com.android.internal.app.procstats.ProcessStats.ProcessStateHolder) obj2);
                        }
                    });
                    this.mThread = iApplicationThread;
                }
            }
            return;
        }
        synchronized (this.mProfilerLock) {
            this.mThread = iApplicationThread;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProcessActive$0(com.android.internal.app.procstats.ProcessState processState, com.android.server.am.ProcessStatsService processStatsService, int i, com.android.internal.app.procstats.ProcessState processState2, java.lang.String str, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder processStateHolder) {
        if (processStateHolder.state != null && processStateHolder.state != processState) {
            processStateHolder.state.makeInactive();
        }
        processStatsService.updateProcessStateHolderLocked(processStateHolder, str, i, this.mApp.info.longVersionCode, this.mApp.processName);
        if (processStateHolder.state != processState2) {
            processStateHolder.state.makeActive();
        }
    }

    void onProcessInactive(com.android.server.am.ProcessStatsService processStatsService) {
        synchronized (this.mProfilerLock) {
            synchronized (processStatsService.mLock) {
                final com.android.internal.app.procstats.ProcessState baseProcessTracker = getBaseProcessTracker();
                if (baseProcessTracker != null) {
                    com.android.server.am.PackageList pkgList = this.mApp.getPkgList();
                    synchronized (pkgList) {
                        baseProcessTracker.setState(-1, processStatsService.getMemFactorLocked(), android.os.SystemClock.uptimeMillis(), pkgList.getPackageListLocked());
                    }
                    baseProcessTracker.makeInactive();
                    setBaseProcessTracker(null);
                    pkgList.forEachPackageProcessStats(new java.util.function.Consumer() { // from class: com.android.server.am.ProcessProfileRecord$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.am.ProcessProfileRecord.lambda$onProcessInactive$1(baseProcessTracker, (com.android.internal.app.procstats.ProcessStats.ProcessStateHolder) obj);
                        }
                    });
                }
                this.mThread = null;
            }
        }
        this.mCurrentHostingComponentTypes.set(0);
        this.mHistoricalHostingComponentTypes.set(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onProcessInactive$1(com.android.internal.app.procstats.ProcessState processState, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder processStateHolder) {
        if (processStateHolder.state != null && processStateHolder.state != processState) {
            processStateHolder.state.makeInactive();
        }
        processStateHolder.pkg = null;
        processStateHolder.state = null;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getLastPssTime() {
        return this.mLastPssTime;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setLastPssTime(long j) {
        this.mLastPssTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getNextPssTime() {
        return this.mNextPssTime;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setNextPssTime(long j) {
        this.mNextPssTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getInitialIdlePssOrRss() {
        return this.mInitialIdlePssOrRss;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setInitialIdlePssOrRss(long j) {
        this.mInitialIdlePssOrRss = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getLastPss() {
        return this.mLastPss;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setLastPss(long j) {
        this.mLastPss = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getLastCachedPss() {
        return this.mLastCachedPss;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setLastCachedPss(long j) {
        this.mLastCachedPss = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getLastCachedRss() {
        return this.mLastCachedRss;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setLastCachedRss(long j) {
        this.mLastCachedRss = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getLastSwapPss() {
        return this.mLastSwapPss;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setLastSwapPss(long j) {
        this.mLastSwapPss = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getLastCachedSwapPss() {
        return this.mLastCachedSwapPss;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setLastCachedSwapPss(long j) {
        this.mLastCachedSwapPss = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getLastRss() {
        return this.mLastRss;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setLastRss(long j) {
        this.mLastRss = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    android.os.Debug.MemoryInfo getLastMemInfo() {
        return this.mLastMemInfo;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setLastMemInfo(android.os.Debug.MemoryInfo memoryInfo) {
        this.mLastMemInfo = memoryInfo;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getLastMemInfoTime() {
        return this.mLastMemInfoTime;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setLastMemInfoTime(long j) {
        this.mLastMemInfoTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    int getPssProcState() {
        return this.mPssProcState;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setPssProcState(int i) {
        this.mPssProcState = i;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    int getPssStatType() {
        return this.mPssStatType;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setPssStatType(int i) {
        this.mPssStatType = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getTrimMemoryLevel() {
        return this.mTrimMemoryLevel;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setTrimMemoryLevel(int i) {
        this.mTrimMemoryLevel = i;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean hasPendingUiClean() {
        return this.mPendingUiClean;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setPendingUiClean(boolean z) {
        this.mPendingUiClean = z;
        this.mApp.getWindowProcessController().setPendingUiClean(z);
    }

    com.android.server.power.stats.BatteryStatsImpl.Uid.Proc getCurProcBatteryStats() {
        return this.mCurProcBatteryStats;
    }

    void setCurProcBatteryStats(com.android.server.power.stats.BatteryStatsImpl.Uid.Proc proc) {
        this.mCurProcBatteryStats = proc;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getLastRequestedGc() {
        return this.mLastRequestedGc;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setLastRequestedGc(long j) {
        this.mLastRequestedGc = j;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProfilerLock"})
    long getLastLowMemory() {
        return this.mLastLowMemory;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProfilerLock"})
    void setLastLowMemory(long j) {
        this.mLastLowMemory = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    boolean getReportLowMemory() {
        return this.mReportLowMemory;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setReportLowMemory(boolean z) {
        this.mReportLowMemory = z;
    }

    void addPss(long j, long j2, long j3, boolean z, int i, long j4) {
        synchronized (this.mService.mProcessStats.mLock) {
            try {
                com.android.internal.app.procstats.ProcessState processState = this.mBaseProcessTracker;
                if (processState != null) {
                    com.android.server.am.PackageList pkgList = this.mApp.getPkgList();
                    synchronized (pkgList) {
                        processState.addPss(j, j2, j3, z, i, j4, pkgList.getPackageListLocked());
                    }
                }
            } finally {
            }
        }
    }

    void reportExcessiveCpu() {
        synchronized (this.mService.mProcessStats.mLock) {
            try {
                com.android.internal.app.procstats.ProcessState processState = this.mBaseProcessTracker;
                if (processState != null) {
                    com.android.server.am.PackageList pkgList = this.mApp.getPkgList();
                    synchronized (pkgList) {
                        processState.reportExcessiveCpu(pkgList.getPackageListLocked());
                    }
                }
            } finally {
            }
        }
    }

    void setProcessTrackerState(int i, int i2) {
        synchronized (this.mService.mProcessStats.mLock) {
            try {
                com.android.internal.app.procstats.ProcessState processState = this.mBaseProcessTracker;
                if (processState != null && i != 20) {
                    com.android.server.am.PackageList pkgList = this.mApp.getPkgList();
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    synchronized (pkgList) {
                        processState.setState(i, i2, uptimeMillis, pkgList.getPackageListLocked());
                    }
                }
            } finally {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void commitNextPssTime() {
        commitNextPssTime(this.mProcStateMemTracker);
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void abortNextPssTime() {
        abortNextPssTime(this.mProcStateMemTracker);
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long computeNextPssTime(int i, boolean z, boolean z2, long j) {
        return com.android.server.am.ProcessList.computeNextPssTime(i, this.mProcStateMemTracker, z, z2, j, java.lang.Math.max(this.mService.mBootCompletedTimestamp, this.mService.mLastIdleTime) + this.mService.mConstants.FULL_PSS_MIN_INTERVAL);
    }

    private static void commitNextPssTime(com.android.server.am.ProcessList.ProcStateMemTracker procStateMemTracker) {
        if (procStateMemTracker.mPendingMemState >= 0) {
            procStateMemTracker.mHighestMem[procStateMemTracker.mPendingMemState] = procStateMemTracker.mPendingHighestMemState;
            procStateMemTracker.mScalingFactor[procStateMemTracker.mPendingMemState] = procStateMemTracker.mPendingScalingFactor;
            procStateMemTracker.mTotalHighestMem = procStateMemTracker.mPendingHighestMemState;
            procStateMemTracker.mPendingMemState = -1;
        }
    }

    private static void abortNextPssTime(com.android.server.am.ProcessList.ProcStateMemTracker procStateMemTracker) {
        procStateMemTracker.mPendingMemState = -1;
    }

    private static int getUidForAttribution(com.android.server.am.ProcessRecord processRecord) {
        if (android.os.Process.isIsolatedUid(processRecord.uid)) {
            return processRecord.info.uid;
        }
        return processRecord.uid;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    int getPid() {
        return this.mPid;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    void setPid(int i) {
        this.mPid = i;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    android.app.IApplicationThread getThread() {
        return this.mThread;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    int getSetProcState() {
        return this.mSetProcState;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    int getSetAdj() {
        return this.mSetAdj;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    int getCurRawAdj() {
        return this.mCurRawAdj;
    }

    @com.android.internal.annotations.GuardedBy({"mProfilerLock"})
    long getLastStateTime() {
        return this.mLastStateTime;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProfilerLock"})
    void updateProcState(com.android.server.am.ProcessStateRecord processStateRecord) {
        this.mSetProcState = processStateRecord.getCurProcState();
        this.mSetAdj = processStateRecord.getCurAdj();
        this.mCurRawAdj = processStateRecord.getCurRawAdj();
        this.mLastStateTime = processStateRecord.getLastStateTime();
    }

    void addHostingComponentType(@android.app.ProcessMemoryState.HostingComponentType int i) {
        this.mCurrentHostingComponentTypes.set(this.mCurrentHostingComponentTypes.get() | i);
        this.mHistoricalHostingComponentTypes.set(i | this.mHistoricalHostingComponentTypes.get());
    }

    void clearHostingComponentType(@android.app.ProcessMemoryState.HostingComponentType int i) {
        this.mCurrentHostingComponentTypes.set((~i) & this.mCurrentHostingComponentTypes.get());
    }

    @android.app.ProcessMemoryState.HostingComponentType
    int getCurrentHostingComponentTypes() {
        return this.mCurrentHostingComponentTypes.get();
    }

    @android.app.ProcessMemoryState.HostingComponentType
    int getHistoricalHostingComponentTypes() {
        return this.mHistoricalHostingComponentTypes.get();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void dumpPss(java.io.PrintWriter printWriter, java.lang.String str, long j) {
        synchronized (this.mProfilerLock) {
            try {
                if (this.mService.mAppProfiler.isProfilingPss()) {
                    printWriter.print(str);
                    printWriter.print("lastPssTime=");
                    android.util.TimeUtils.formatDuration(this.mLastPssTime, j, printWriter);
                    printWriter.print(" pssProcState=");
                    printWriter.print(this.mPssProcState);
                    printWriter.print(" pssStatType=");
                    printWriter.print(this.mPssStatType);
                    printWriter.print(" nextPssTime=");
                    android.util.TimeUtils.formatDuration(this.mNextPssTime, j, printWriter);
                    printWriter.println();
                    printWriter.print(str);
                    printWriter.print("lastPss=");
                    android.util.DebugUtils.printSizeValue(printWriter, this.mLastPss * 1024);
                    printWriter.print(" lastSwapPss=");
                    android.util.DebugUtils.printSizeValue(printWriter, this.mLastSwapPss * 1024);
                    printWriter.print(" lastCachedPss=");
                    android.util.DebugUtils.printSizeValue(printWriter, this.mLastCachedPss * 1024);
                    printWriter.print(" lastCachedSwapPss=");
                    android.util.DebugUtils.printSizeValue(printWriter, this.mLastCachedSwapPss * 1024);
                    printWriter.print(" lastRss=");
                    android.util.DebugUtils.printSizeValue(printWriter, this.mLastRss * 1024);
                } else {
                    printWriter.print(str);
                    printWriter.print("lastRssTime=");
                    android.util.TimeUtils.formatDuration(this.mLastPssTime, j, printWriter);
                    printWriter.print(" rssProcState=");
                    printWriter.print(this.mPssProcState);
                    printWriter.print(" rssStatType=");
                    printWriter.print(this.mPssStatType);
                    printWriter.print(" nextRssTime=");
                    android.util.TimeUtils.formatDuration(this.mNextPssTime, j, printWriter);
                    printWriter.println();
                    printWriter.print(str);
                    printWriter.print("lastRss=");
                    android.util.DebugUtils.printSizeValue(printWriter, this.mLastRss * 1024);
                    printWriter.print(" lastCachedRss=");
                    android.util.DebugUtils.printSizeValue(printWriter, this.mLastCachedRss * 1024);
                }
                printWriter.println();
                printWriter.print(str);
                printWriter.print("trimMemoryLevel=");
                printWriter.println(this.mTrimMemoryLevel);
                printWriter.print(str);
                printWriter.print("procStateMemTracker: ");
                this.mProcStateMemTracker.dumpLine(printWriter);
                printWriter.print(str);
                printWriter.print("lastRequestedGc=");
                android.util.TimeUtils.formatDuration(this.mLastRequestedGc, j, printWriter);
                printWriter.print(" lastLowMemory=");
                android.util.TimeUtils.formatDuration(this.mLastLowMemory, j, printWriter);
                printWriter.print(" reportLowMemory=");
                printWriter.println(this.mReportLowMemory);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.print(str);
        printWriter.print("currentHostingComponentTypes=0x");
        printWriter.print(java.lang.Integer.toHexString(getCurrentHostingComponentTypes()));
        printWriter.print(" historicalHostingComponentTypes=0x");
        printWriter.println(java.lang.Integer.toHexString(getHistoricalHostingComponentTypes()));
    }

    void dumpCputime(java.io.PrintWriter printWriter, java.lang.String str) {
        long j = this.mLastCpuTime.get();
        printWriter.print(str);
        printWriter.print("lastCpuTime=");
        printWriter.print(j);
        if (j > 0) {
            printWriter.print(" timeUsed=");
            android.util.TimeUtils.formatDuration(this.mCurCpuTime.get() - j, printWriter);
        }
        printWriter.println();
    }
}
