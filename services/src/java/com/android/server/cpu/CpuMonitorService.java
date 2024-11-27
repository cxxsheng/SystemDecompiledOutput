package com.android.server.cpu;

/* loaded from: classes.dex */
public final class CpuMonitorService extends com.android.server.SystemService {
    private static final long CACHE_DURATION_MILLISECONDS;
    static final long DEFAULT_MONITORING_INTERVAL_MILLISECONDS = -1;
    private static final long LATEST_AVAILABILITY_DURATION_MILLISECONDS;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<com.android.server.cpu.CpuMonitorInternal.CpuAvailabilityCallback, com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo> mAvailabilityCallbackInfosByCallbacksByCpuset;
    private final android.content.Context mContext;
    private final com.android.server.cpu.CpuInfoReader mCpuInfoReader;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.cpu.CpuMonitorService.CpusetInfo> mCpusetInfosByCpuset;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mCurrentMonitoringIntervalMillis;
    private final long mDebugMonitoringIntervalMillis;
    private android.os.Handler mHandler;
    private final android.os.HandlerThread mHandlerThread;
    private final long mLatestAvailabilityDurationMillis;
    private final com.android.server.cpu.CpuMonitorInternal mLocalService;
    private final java.lang.Object mLock;
    private final java.lang.Runnable mMonitorCpuStats;
    private final long mNormalMonitoringIntervalMillis;
    private final boolean mShouldDebugMonitor;
    static final java.lang.String TAG = com.android.server.cpu.CpuMonitorService.class.getSimpleName();
    static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final long NORMAL_MONITORING_INTERVAL_MILLISECONDS = java.util.concurrent.TimeUnit.SECONDS.toMillis(5);
    private static final long DEBUG_MONITORING_INTERVAL_MILLISECONDS = java.util.concurrent.TimeUnit.MINUTES.toMillis(1);

    static {
        CACHE_DURATION_MILLISECONDS = (android.os.Build.IS_USERDEBUG || android.os.Build.IS_ENG) ? java.util.concurrent.TimeUnit.MINUTES.toMillis(30L) : java.util.concurrent.TimeUnit.MINUTES.toMillis(10L);
        LATEST_AVAILABILITY_DURATION_MILLISECONDS = java.util.concurrent.TimeUnit.SECONDS.toMillis(30L);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CpuMonitorService(android.content.Context context) {
        this(context, r2, r3, r4, NORMAL_MONITORING_INTERVAL_MILLISECONDS, DEBUG_MONITORING_INTERVAL_MILLISECONDS, LATEST_AVAILABILITY_DURATION_MILLISECONDS);
        com.android.server.cpu.CpuInfoReader cpuInfoReader = new com.android.server.cpu.CpuInfoReader();
        boolean z = true;
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(TAG, 10, true);
        if (!android.os.Build.IS_USERDEBUG && !android.os.Build.IS_ENG) {
            z = false;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    CpuMonitorService(android.content.Context context, com.android.server.cpu.CpuInfoReader cpuInfoReader, android.os.HandlerThread handlerThread, boolean z, long j, long j2, long j3) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mMonitorCpuStats = new java.lang.Runnable() { // from class: com.android.server.cpu.CpuMonitorService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.cpu.CpuMonitorService.this.monitorCpuStats();
            }
        };
        this.mCurrentMonitoringIntervalMillis = -1L;
        this.mLocalService = new com.android.server.cpu.CpuMonitorInternal() { // from class: com.android.server.cpu.CpuMonitorService.1
            @Override // com.android.server.cpu.CpuMonitorInternal
            public void addCpuAvailabilityCallback(java.util.concurrent.Executor executor, com.android.server.cpu.CpuAvailabilityMonitoringConfig cpuAvailabilityMonitoringConfig, com.android.server.cpu.CpuMonitorInternal.CpuAvailabilityCallback cpuAvailabilityCallback) {
                com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo newCallbackInfoLocked;
                java.util.Objects.requireNonNull(cpuAvailabilityCallback, "Callback must be non-null");
                java.util.Objects.requireNonNull(cpuAvailabilityMonitoringConfig, "Config must be non-null");
                synchronized (com.android.server.cpu.CpuMonitorService.this.mLock) {
                    for (int i = 0; i < com.android.server.cpu.CpuMonitorService.this.mAvailabilityCallbackInfosByCallbacksByCpuset.numMaps(); i++) {
                        try {
                            com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo cpuAvailabilityCallbackInfo = (com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo) com.android.server.cpu.CpuMonitorService.this.mAvailabilityCallbackInfosByCallbacksByCpuset.delete(com.android.server.cpu.CpuMonitorService.this.mAvailabilityCallbackInfosByCallbacksByCpuset.keyAt(i), cpuAvailabilityCallback);
                            if (cpuAvailabilityCallbackInfo != null) {
                                com.android.server.utils.Slogf.i(com.android.server.cpu.CpuMonitorService.TAG, "Overwriting the existing %s", cpuAvailabilityCallbackInfo);
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    newCallbackInfoLocked = com.android.server.cpu.CpuMonitorService.this.newCallbackInfoLocked(cpuAvailabilityMonitoringConfig, cpuAvailabilityCallback, executor);
                }
                com.android.server.cpu.CpuMonitorService.this.asyncNotifyMonitoringIntervalChangeToClient(newCallbackInfoLocked);
                if (com.android.server.cpu.CpuMonitorService.DEBUG) {
                    com.android.server.utils.Slogf.d(com.android.server.cpu.CpuMonitorService.TAG, "Successfully added %s", newCallbackInfoLocked);
                }
            }

            @Override // com.android.server.cpu.CpuMonitorInternal
            public void removeCpuAvailabilityCallback(com.android.server.cpu.CpuMonitorInternal.CpuAvailabilityCallback cpuAvailabilityCallback) {
                synchronized (com.android.server.cpu.CpuMonitorService.this.mLock) {
                    for (int i = 0; i < com.android.server.cpu.CpuMonitorService.this.mAvailabilityCallbackInfosByCallbacksByCpuset.numMaps(); i++) {
                        try {
                            com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo cpuAvailabilityCallbackInfo = (com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo) com.android.server.cpu.CpuMonitorService.this.mAvailabilityCallbackInfosByCallbacksByCpuset.delete(com.android.server.cpu.CpuMonitorService.this.mAvailabilityCallbackInfosByCallbacksByCpuset.keyAt(i), cpuAvailabilityCallback);
                            if (cpuAvailabilityCallbackInfo != null) {
                                if (com.android.server.cpu.CpuMonitorService.DEBUG) {
                                    com.android.server.utils.Slogf.d(com.android.server.cpu.CpuMonitorService.TAG, "Successfully removed %s", cpuAvailabilityCallbackInfo);
                                }
                                com.android.server.cpu.CpuMonitorService.this.checkAndStopMonitoringLocked();
                                return;
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "CpuAvailabilityCallback was not previously added. Ignoring the remove request");
                }
            }
        };
        this.mContext = context;
        this.mHandlerThread = handlerThread;
        this.mShouldDebugMonitor = z;
        this.mNormalMonitoringIntervalMillis = j;
        this.mDebugMonitoringIntervalMillis = j2;
        this.mLatestAvailabilityDurationMillis = j3;
        this.mCpuInfoReader = cpuInfoReader;
        this.mCpusetInfosByCpuset = new android.util.SparseArray<>(2);
        this.mCpusetInfosByCpuset.append(1, new com.android.server.cpu.CpuMonitorService.CpusetInfo(1));
        this.mCpusetInfosByCpuset.append(2, new com.android.server.cpu.CpuMonitorService.CpusetInfo(2));
        this.mAvailabilityCallbackInfosByCallbacksByCpuset = new android.util.SparseArrayMap<>();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        if (!this.mCpuInfoReader.init() || this.mCpuInfoReader.readCpuInfos() == null) {
            com.android.server.utils.Slogf.wtf(TAG, "Failed to initialize CPU info reader. This happens when the CPU frequency stats are not available or the sysfs interface has changed in the Kernel. Cannot monitor CPU without these stats. Terminating CPU monitor service");
            return;
        }
        this.mHandlerThread.start();
        this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper());
        publishLocalService(com.android.server.cpu.CpuMonitorInternal.class, this.mLocalService);
        publishBinderService("cpu_monitor", new com.android.server.cpu.CpuMonitorService.CpuMonitorBinder(), false, 1);
        com.android.server.Watchdog.getInstance().addThread(this.mHandler);
        synchronized (this.mLock) {
            try {
                if (this.mShouldDebugMonitor && !this.mHandler.hasCallbacks(this.mMonitorCpuStats)) {
                    this.mCurrentMonitoringIntervalMillis = this.mDebugMonitoringIntervalMillis;
                    com.android.server.utils.Slogf.i(TAG, "Starting debug monitoring");
                    this.mHandler.post(this.mMonitorCpuStats);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    long getCurrentMonitoringIntervalMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.mCurrentMonitoringIntervalMillis;
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDump(final android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("*%s*\n", new java.lang.Object[]{com.android.server.cpu.CpuMonitorService.class.getSimpleName()});
        indentingPrintWriter.increaseIndent();
        this.mCpuInfoReader.dump(indentingPrintWriter);
        indentingPrintWriter.printf("mShouldDebugMonitor = %s\n", new java.lang.Object[]{this.mShouldDebugMonitor ? "Yes" : "No"});
        indentingPrintWriter.printf("mNormalMonitoringIntervalMillis = %d\n", new java.lang.Object[]{java.lang.Long.valueOf(this.mNormalMonitoringIntervalMillis)});
        indentingPrintWriter.printf("mDebugMonitoringIntervalMillis = %d\n", new java.lang.Object[]{java.lang.Long.valueOf(this.mDebugMonitoringIntervalMillis)});
        indentingPrintWriter.printf("mLatestAvailabilityDurationMillis = %d\n", new java.lang.Object[]{java.lang.Long.valueOf(this.mLatestAvailabilityDurationMillis)});
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf("mCurrentMonitoringIntervalMillis = %d\n", new java.lang.Object[]{java.lang.Long.valueOf(this.mCurrentMonitoringIntervalMillis)});
                if (hasClientCallbacksLocked()) {
                    indentingPrintWriter.println("CPU availability change callbacks:");
                    indentingPrintWriter.increaseIndent();
                    this.mAvailabilityCallbackInfosByCallbacksByCpuset.forEach(new java.util.function.Consumer() { // from class: com.android.server.cpu.CpuMonitorService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.cpu.CpuMonitorService.lambda$doDump$0(indentingPrintWriter, (com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo) obj);
                        }
                    });
                    indentingPrintWriter.decreaseIndent();
                }
                if (this.mCpusetInfosByCpuset.size() > 0) {
                    indentingPrintWriter.println("Cpuset infos:");
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0; i < this.mCpusetInfosByCpuset.size(); i++) {
                        indentingPrintWriter.printf("%s\n", new java.lang.Object[]{this.mCpusetInfosByCpuset.valueAt(i)});
                    }
                    indentingPrintWriter.decreaseIndent();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$doDump$0(android.util.IndentingPrintWriter indentingPrintWriter, com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo cpuAvailabilityCallbackInfo) {
        indentingPrintWriter.printf("%s\n", new java.lang.Object[]{cpuAvailabilityCallbackInfo});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void monitorCpuStats() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        this.mHandler.removeCallbacks(this.mMonitorCpuStats);
        android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuInfo> readCpuInfos = this.mCpuInfoReader.readCpuInfos();
        if (readCpuInfos == null) {
            com.android.server.utils.Slogf.wtf(TAG, "Failed to read CPU info from device");
            synchronized (this.mLock) {
                stopMonitoringCpuStatsLocked();
            }
            return;
        }
        synchronized (this.mLock) {
            for (int i = 0; i < readCpuInfos.size(); i++) {
                try {
                    com.android.server.cpu.CpuInfoReader.CpuInfo valueAt = readCpuInfos.valueAt(i);
                    for (int i2 = 0; i2 < this.mCpusetInfosByCpuset.size(); i2++) {
                        this.mCpusetInfosByCpuset.valueAt(i2).appendCpuInfo(uptimeMillis, valueAt);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            for (int i3 = 0; i3 < this.mCpusetInfosByCpuset.size(); i3++) {
                com.android.server.cpu.CpuMonitorService.CpusetInfo valueAt2 = this.mCpusetInfosByCpuset.valueAt(i3);
                valueAt2.populateLatestCpuAvailabilityInfo(uptimeMillis, this.mLatestAvailabilityDurationMillis);
                checkClientThresholdsAndNotifyLocked(valueAt2);
            }
            if (this.mCurrentMonitoringIntervalMillis > 0 && (hasClientCallbacksLocked() || this.mShouldDebugMonitor)) {
                this.mHandler.postAtTime(this.mMonitorCpuStats, uptimeMillis + this.mCurrentMonitoringIntervalMillis);
            } else {
                stopMonitoringCpuStatsLocked();
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void checkClientThresholdsAndNotifyLocked(com.android.server.cpu.CpuMonitorService.CpusetInfo cpusetInfo) {
        int prevCpuAvailabilityPercent = cpusetInfo.getPrevCpuAvailabilityPercent();
        com.android.server.cpu.CpuAvailabilityInfo latestCpuAvailabilityInfo = cpusetInfo.getLatestCpuAvailabilityInfo();
        if (latestCpuAvailabilityInfo == null || prevCpuAvailabilityPercent < 0 || this.mAvailabilityCallbackInfosByCallbacksByCpuset.numElementsForKey(cpusetInfo.cpuset) == 0) {
            return;
        }
        for (int i = 0; i < this.mAvailabilityCallbackInfosByCallbacksByCpuset.numMaps(); i++) {
            for (int i2 = 0; i2 < this.mAvailabilityCallbackInfosByCallbacksByCpuset.numElementsForKeyAt(i); i2++) {
                com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo cpuAvailabilityCallbackInfo = (com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo) this.mAvailabilityCallbackInfosByCallbacksByCpuset.valueAt(i, i2);
                if (cpuAvailabilityCallbackInfo.config.cpuset == cpusetInfo.cpuset && didCrossAnyThreshold(prevCpuAvailabilityPercent, latestCpuAvailabilityInfo.latestAvgAvailabilityPercent, cpuAvailabilityCallbackInfo.config.getThresholds())) {
                    asyncNotifyCpuAvailabilityToClient(latestCpuAvailabilityInfo, cpuAvailabilityCallbackInfo);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void asyncNotifyMonitoringIntervalChangeToClient(com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo cpuAvailabilityCallbackInfo) {
        if (cpuAvailabilityCallbackInfo.executor == null) {
            this.mHandler.post(cpuAvailabilityCallbackInfo.notifyMonitoringIntervalChangeRunnable);
        } else {
            cpuAvailabilityCallbackInfo.executor.execute(cpuAvailabilityCallbackInfo.notifyMonitoringIntervalChangeRunnable);
        }
    }

    private void asyncNotifyCpuAvailabilityToClient(com.android.server.cpu.CpuAvailabilityInfo cpuAvailabilityInfo, com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo cpuAvailabilityCallbackInfo) {
        cpuAvailabilityCallbackInfo.notifyCpuAvailabilityChangeRunnable.prepare(cpuAvailabilityInfo);
        if (cpuAvailabilityCallbackInfo.executor == null) {
            this.mHandler.post(cpuAvailabilityCallbackInfo.notifyCpuAvailabilityChangeRunnable);
        } else {
            cpuAvailabilityCallbackInfo.executor.execute(cpuAvailabilityCallbackInfo.notifyCpuAvailabilityChangeRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo newCallbackInfoLocked(com.android.server.cpu.CpuAvailabilityMonitoringConfig cpuAvailabilityMonitoringConfig, com.android.server.cpu.CpuMonitorInternal.CpuAvailabilityCallback cpuAvailabilityCallback, java.util.concurrent.Executor executor) {
        com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo cpuAvailabilityCallbackInfo = new com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo(this, cpuAvailabilityMonitoringConfig, cpuAvailabilityCallback, executor);
        java.lang.String cpusetString = com.android.server.cpu.CpuAvailabilityMonitoringConfig.toCpusetString(cpuAvailabilityCallbackInfo.config.cpuset);
        com.android.server.cpu.CpuMonitorService.CpusetInfo cpusetInfo = this.mCpusetInfosByCpuset.get(cpuAvailabilityCallbackInfo.config.cpuset);
        com.android.internal.util.Preconditions.checkState(cpusetInfo != null, "Missing cpuset info for cpuset %s", new java.lang.Object[]{cpusetString});
        boolean hasClientCallbacksLocked = hasClientCallbacksLocked();
        this.mAvailabilityCallbackInfosByCallbacksByCpuset.add(cpuAvailabilityCallbackInfo.config.cpuset, cpuAvailabilityCallbackInfo.callback, cpuAvailabilityCallbackInfo);
        if (DEBUG) {
            com.android.server.utils.Slogf.d(TAG, "Added a CPU availability callback: %s", cpuAvailabilityCallbackInfo);
        }
        com.android.server.cpu.CpuAvailabilityInfo latestCpuAvailabilityInfo = cpusetInfo.getLatestCpuAvailabilityInfo();
        if (latestCpuAvailabilityInfo != null) {
            asyncNotifyCpuAvailabilityToClient(latestCpuAvailabilityInfo, cpuAvailabilityCallbackInfo);
        }
        if (hasClientCallbacksLocked && this.mHandler.hasCallbacks(this.mMonitorCpuStats)) {
            return cpuAvailabilityCallbackInfo;
        }
        this.mHandler.removeCallbacks(this.mMonitorCpuStats);
        this.mCurrentMonitoringIntervalMillis = this.mNormalMonitoringIntervalMillis;
        this.mHandler.post(this.mMonitorCpuStats);
        return cpuAvailabilityCallbackInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void checkAndStopMonitoringLocked() {
        if (hasClientCallbacksLocked()) {
            return;
        }
        if (this.mShouldDebugMonitor) {
            if (DEBUG) {
                com.android.server.utils.Slogf.e(TAG, "Switching to debug monitoring");
            }
            this.mCurrentMonitoringIntervalMillis = this.mDebugMonitoringIntervalMillis;
            return;
        }
        stopMonitoringCpuStatsLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean hasClientCallbacksLocked() {
        for (int i = 0; i < this.mAvailabilityCallbackInfosByCallbacksByCpuset.numMaps(); i++) {
            if (this.mAvailabilityCallbackInfosByCallbacksByCpuset.numElementsForKeyAt(i) > 0) {
                return true;
            }
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void stopMonitoringCpuStatsLocked() {
        this.mHandler.removeCallbacks(this.mMonitorCpuStats);
        this.mCurrentMonitoringIntervalMillis = -1L;
        for (int i = 0; i < this.mCpusetInfosByCpuset.size(); i++) {
            this.mCpusetInfosByCpuset.valueAt(i).clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean containsCpuset(int i, int i2) {
        switch (i2) {
            case 1:
                if ((i & 1) == 0) {
                    break;
                }
                break;
            case 2:
                if ((i & 2) == 0) {
                    break;
                }
                break;
            default:
                com.android.server.utils.Slogf.wtf(TAG, "Provided invalid expectedCpuset %d", java.lang.Integer.valueOf(i2));
                break;
        }
        return false;
    }

    private static boolean didCrossAnyThreshold(int i, int i2, android.util.IntArray intArray) {
        if (i == i2) {
            return false;
        }
        for (int i3 = 0; i3 < intArray.size(); i3++) {
            int i4 = intArray.get(i3);
            if (i < i4 && i2 >= i4) {
                return true;
            }
            if (i >= i4 && i2 < i4) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CpuAvailabilityCallbackInfo {
        public final com.android.server.cpu.CpuMonitorInternal.CpuAvailabilityCallback callback;
        public final com.android.server.cpu.CpuAvailabilityMonitoringConfig config;

        @android.annotation.Nullable
        public final java.util.concurrent.Executor executor;
        public final com.android.server.cpu.CpuMonitorService service;
        public final java.lang.Runnable notifyMonitoringIntervalChangeRunnable = new java.lang.Runnable() { // from class: com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo.this.callback.onMonitoringIntervalChanged(com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo.this.service.getCurrentMonitoringIntervalMillis());
            }
        };
        public final com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo.NotifyCpuAvailabilityChangeRunnable notifyCpuAvailabilityChangeRunnable = new com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo.NotifyCpuAvailabilityChangeRunnable();

        CpuAvailabilityCallbackInfo(com.android.server.cpu.CpuMonitorService cpuMonitorService, com.android.server.cpu.CpuAvailabilityMonitoringConfig cpuAvailabilityMonitoringConfig, com.android.server.cpu.CpuMonitorInternal.CpuAvailabilityCallback cpuAvailabilityCallback, @android.annotation.Nullable java.util.concurrent.Executor executor) {
            this.service = cpuMonitorService;
            this.config = cpuAvailabilityMonitoringConfig;
            this.callback = cpuAvailabilityCallback;
            this.executor = executor;
        }

        public java.lang.String toString() {
            return "CpuAvailabilityCallbackInfo{config = " + this.config + ", callback = " + this.callback + ", mExecutor = " + this.executor + '}';
        }

        private final class NotifyCpuAvailabilityChangeRunnable implements java.lang.Runnable {

            @com.android.internal.annotations.GuardedBy({"mLock"})
            private com.android.server.cpu.CpuAvailabilityInfo mCpuAvailabilityInfo;
            private final java.lang.Object mLock;

            private NotifyCpuAvailabilityChangeRunnable() {
                this.mLock = new java.lang.Object();
            }

            public void prepare(com.android.server.cpu.CpuAvailabilityInfo cpuAvailabilityInfo) {
                synchronized (this.mLock) {
                    this.mCpuAvailabilityInfo = cpuAvailabilityInfo;
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                synchronized (this.mLock) {
                    com.android.server.cpu.CpuMonitorService.CpuAvailabilityCallbackInfo.this.callback.onAvailabilityChanged(this.mCpuAvailabilityInfo);
                }
            }
        }
    }

    private final class CpuMonitorBinder extends android.os.Binder {
        private final com.android.server.utils.PriorityDump.PriorityDumper mPriorityDumper;

        private CpuMonitorBinder() {
            this.mPriorityDumper = new com.android.server.utils.PriorityDump.PriorityDumper() { // from class: com.android.server.cpu.CpuMonitorService.CpuMonitorBinder.1
                @Override // com.android.server.utils.PriorityDump.PriorityDumper
                public void dumpCritical(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
                    if (!com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(com.android.server.cpu.CpuMonitorService.this.mContext, com.android.server.cpu.CpuMonitorService.TAG, printWriter) || z) {
                        return;
                    }
                    android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
                    try {
                        com.android.server.cpu.CpuMonitorService.this.doDump(indentingPrintWriter);
                        indentingPrintWriter.close();
                    } catch (java.lang.Throwable th) {
                        try {
                            indentingPrintWriter.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
            };
        }

        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            com.android.server.utils.PriorityDump.dump(this.mPriorityDumper, fileDescriptor, printWriter, strArr);
        }
    }

    private static final class CpusetInfo {
        public final int cpuset;

        @android.annotation.Nullable
        private com.android.server.cpu.CpuAvailabilityInfo mLatestCpuAvailabilityInfo;
        private final android.util.LongSparseArray<com.android.server.cpu.CpuMonitorService.CpusetInfo.Snapshot> mSnapshotsByUptime = new android.util.LongSparseArray<>();

        CpusetInfo(int i) {
            this.cpuset = i;
        }

        public void appendCpuInfo(long j, com.android.server.cpu.CpuInfoReader.CpuInfo cpuInfo) {
            if (!com.android.server.cpu.CpuMonitorService.containsCpuset(cpuInfo.cpusetCategories, this.cpuset)) {
                return;
            }
            com.android.server.cpu.CpuMonitorService.CpusetInfo.Snapshot snapshot = this.mSnapshotsByUptime.get(j);
            if (snapshot == null) {
                snapshot = new com.android.server.cpu.CpuMonitorService.CpusetInfo.Snapshot(j);
                this.mSnapshotsByUptime.append(j, snapshot);
                if (this.mSnapshotsByUptime.size() > 0 && j - this.mSnapshotsByUptime.valueAt(0).uptimeMillis > com.android.server.cpu.CpuMonitorService.CACHE_DURATION_MILLISECONDS) {
                    this.mSnapshotsByUptime.removeAt(0);
                }
            }
            snapshot.appendCpuInfo(cpuInfo);
        }

        @android.annotation.Nullable
        public com.android.server.cpu.CpuAvailabilityInfo getLatestCpuAvailabilityInfo() {
            return this.mLatestCpuAvailabilityInfo;
        }

        public void populateLatestCpuAvailabilityInfo(long j, long j2) {
            int size = this.mSnapshotsByUptime.size();
            if (size == 0) {
                this.mLatestCpuAvailabilityInfo = null;
                return;
            }
            com.android.server.cpu.CpuMonitorService.CpusetInfo.Snapshot valueAt = this.mSnapshotsByUptime.valueAt(size - 1);
            if (valueAt.uptimeMillis != j) {
                if (com.android.server.cpu.CpuMonitorService.DEBUG) {
                    com.android.server.utils.Slogf.d(com.android.server.cpu.CpuMonitorService.TAG, "Skipping stale CPU availability information for cpuset %s", com.android.server.cpu.CpuAvailabilityMonitoringConfig.toCpusetString(this.cpuset));
                }
                this.mLatestCpuAvailabilityInfo = null;
            } else {
                if (this.mLatestCpuAvailabilityInfo != null && this.mLatestCpuAvailabilityInfo.dataTimestampUptimeMillis == valueAt.uptimeMillis) {
                    return;
                }
                this.mLatestCpuAvailabilityInfo = new com.android.server.cpu.CpuAvailabilityInfo(this.cpuset, valueAt.uptimeMillis, valueAt.getAverageAvailableCpuFreqPercent(), getCumulativeAvgAvailabilityPercent(j - j2), j2);
            }
        }

        public int getPrevCpuAvailabilityPercent() {
            int size = this.mSnapshotsByUptime.size();
            if (size >= 2) {
                return this.mSnapshotsByUptime.valueAt(size - 2).getAverageAvailableCpuFreqPercent();
            }
            return -1;
        }

        private int getCumulativeAvgAvailabilityPercent(long j) {
            int size = this.mSnapshotsByUptime.size() - 1;
            long j2 = 0;
            long j3 = Long.MAX_VALUE;
            int i = 0;
            long j4 = 0;
            while (true) {
                if (size < 0) {
                    break;
                }
                com.android.server.cpu.CpuMonitorService.CpusetInfo.Snapshot valueAt = this.mSnapshotsByUptime.valueAt(size);
                long j5 = valueAt.uptimeMillis;
                if (valueAt.uptimeMillis <= j) {
                    j3 = j5;
                    break;
                }
                i++;
                j2 += valueAt.totalNormalizedAvailableCpuFreqKHz;
                j4 += valueAt.totalOnlineMaxCpuFreqKHz;
                size--;
                j3 = j5;
            }
            if (j3 > j || i < 2) {
                return -1;
            }
            return (int) ((j2 * 100.0d) / j4);
        }

        public void clear() {
            this.mLatestCpuAvailabilityInfo = null;
            this.mSnapshotsByUptime.clear();
        }

        public java.lang.String toString() {
            return "CpusetInfo{cpuset = " + com.android.server.cpu.CpuAvailabilityMonitoringConfig.toCpusetString(this.cpuset) + ", mSnapshotsByUptime = " + this.mSnapshotsByUptime + ", mLatestCpuAvailabilityInfo = " + this.mLatestCpuAvailabilityInfo + '}';
        }

        private static final class Snapshot {
            public long totalNormalizedAvailableCpuFreqKHz;
            public int totalOfflineCpus;
            public long totalOfflineMaxCpuFreqKHz;
            public int totalOnlineCpus;
            public long totalOnlineMaxCpuFreqKHz;
            public final long uptimeMillis;

            Snapshot(long j) {
                this.uptimeMillis = j;
            }

            public void appendCpuInfo(com.android.server.cpu.CpuInfoReader.CpuInfo cpuInfo) {
                if (!cpuInfo.isOnline) {
                    this.totalOfflineCpus++;
                    this.totalOfflineMaxCpuFreqKHz += cpuInfo.maxCpuFreqKHz;
                } else {
                    this.totalOnlineCpus++;
                    this.totalNormalizedAvailableCpuFreqKHz += cpuInfo.getNormalizedAvailableCpuFreqKHz();
                    this.totalOnlineMaxCpuFreqKHz += cpuInfo.maxCpuFreqKHz;
                }
            }

            public int getAverageAvailableCpuFreqPercent() {
                int i = (int) ((this.totalNormalizedAvailableCpuFreqKHz * 100.0d) / this.totalOnlineMaxCpuFreqKHz);
                if (i < 0) {
                    com.android.server.utils.Slogf.wtf(com.android.server.cpu.CpuMonitorService.TAG, "Computed negative CPU availability percent(%d) for %s ", java.lang.Integer.valueOf(i), toString());
                    return 0;
                }
                return i;
            }

            public java.lang.String toString() {
                return "Snapshot{uptimeMillis = " + this.uptimeMillis + ", totalOnlineCpus = " + this.totalOnlineCpus + ", totalOfflineCpus = " + this.totalOfflineCpus + ", totalNormalizedAvailableCpuFreqKHz = " + this.totalNormalizedAvailableCpuFreqKHz + ", totalOnlineMaxCpuFreqKHz = " + this.totalOnlineMaxCpuFreqKHz + ", totalOfflineMaxCpuFreqKHz = " + this.totalOfflineMaxCpuFreqKHz + '}';
            }
        }
    }
}
