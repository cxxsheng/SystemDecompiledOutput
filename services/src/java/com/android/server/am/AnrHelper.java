package com.android.server.am;

/* loaded from: classes.dex */
class AnrHelper {
    private static final int DEFAULT_THREAD_KEEP_ALIVE_SECOND = 10;
    private static final java.lang.String TAG = "ActivityManager";

    @com.android.internal.annotations.GuardedBy({"mAnrRecords"})
    private final java.util.ArrayList<com.android.server.am.AnrHelper.AnrRecord> mAnrRecords;
    private final java.util.concurrent.ExecutorService mAuxiliaryTaskExecutor;
    private final java.util.concurrent.ExecutorService mEarlyDumpExecutor;
    private long mLastAnrTimeMs;

    @com.android.internal.annotations.GuardedBy({"mAnrRecords"})
    private int mProcessingPid;
    private final java.util.concurrent.atomic.AtomicBoolean mRunning;
    private final com.android.server.am.ActivityManagerService mService;
    private final java.util.Set<java.lang.Integer> mTempDumpedPids;
    private static final long EXPIRED_REPORT_TIME_MS = java.util.concurrent.TimeUnit.SECONDS.toMillis(10);
    private static final long CONSECUTIVE_ANR_TIME_MS = java.util.concurrent.TimeUnit.MINUTES.toMillis(2);
    private static final long SELF_ONLY_AFTER_BOOT_MS = java.util.concurrent.TimeUnit.MINUTES.toMillis(10);
    private static final java.util.concurrent.ThreadFactory sDefaultThreadFactory = new java.util.concurrent.ThreadFactory() { // from class: com.android.server.am.AnrHelper$$ExternalSyntheticLambda0
        @Override // java.util.concurrent.ThreadFactory
        public final java.lang.Thread newThread(java.lang.Runnable runnable) {
            java.lang.Thread lambda$static$0;
            lambda$static$0 = com.android.server.am.AnrHelper.lambda$static$0(runnable);
            return lambda$static$0;
        }
    };
    private static final java.util.concurrent.ThreadFactory sMainProcessDumpThreadFactory = new java.util.concurrent.ThreadFactory() { // from class: com.android.server.am.AnrHelper$$ExternalSyntheticLambda1
        @Override // java.util.concurrent.ThreadFactory
        public final java.lang.Thread newThread(java.lang.Runnable runnable) {
            java.lang.Thread lambda$static$1;
            lambda$static$1 = com.android.server.am.AnrHelper.lambda$static$1(runnable);
            return lambda$static$1;
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Thread lambda$static$0(java.lang.Runnable runnable) {
        return new java.lang.Thread(runnable, "AnrAuxiliaryTaskExecutor");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Thread lambda$static$1(java.lang.Runnable runnable) {
        return new java.lang.Thread(runnable, "AnrMainProcessDumpThread");
    }

    AnrHelper(com.android.server.am.ActivityManagerService activityManagerService) {
        this(activityManagerService, makeExpiringThreadPoolWithSize(1, sDefaultThreadFactory), makeExpiringThreadPoolWithSize(2, sMainProcessDumpThreadFactory));
    }

    @com.android.internal.annotations.VisibleForTesting
    AnrHelper(com.android.server.am.ActivityManagerService activityManagerService, java.util.concurrent.ExecutorService executorService, java.util.concurrent.ExecutorService executorService2) {
        this.mAnrRecords = new java.util.ArrayList<>();
        this.mTempDumpedPids = java.util.Collections.synchronizedSet(new android.util.ArraySet());
        this.mRunning = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.mLastAnrTimeMs = 0L;
        this.mProcessingPid = -1;
        this.mService = activityManagerService;
        this.mAuxiliaryTaskExecutor = executorService;
        this.mEarlyDumpExecutor = executorService2;
    }

    void appNotResponding(com.android.server.am.ProcessRecord processRecord, com.android.internal.os.TimeoutRecord timeoutRecord) {
        appNotResponding(processRecord, null, null, null, null, false, timeoutRecord, false);
    }

    void appNotResponding(com.android.server.am.ProcessRecord processRecord, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, java.lang.String str2, com.android.server.wm.WindowProcessController windowProcessController, boolean z, final com.android.internal.os.TimeoutRecord timeoutRecord, boolean z2) {
        try {
            timeoutRecord.mLatencyTracker.appNotRespondingStarted();
            final int i = processRecord.mPid;
            timeoutRecord.mLatencyTracker.waitingOnAnrRecordLockStarted();
            synchronized (this.mAnrRecords) {
                timeoutRecord.mLatencyTracker.waitingOnAnrRecordLockEnded();
                if (i == 0) {
                    android.util.Slog.i(TAG, "Skip zero pid ANR, process=" + processRecord.processName);
                    return;
                }
                if (this.mProcessingPid == i) {
                    android.util.Slog.i(TAG, "Skip duplicated ANR, pid=" + i + " " + timeoutRecord.mReason);
                    return;
                }
                if (!this.mTempDumpedPids.add(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.i(TAG, "Skip ANR being predumped, pid=" + i + " " + timeoutRecord.mReason);
                    return;
                }
                for (int size = this.mAnrRecords.size() - 1; size >= 0; size--) {
                    if (this.mAnrRecords.get(size).mPid == i) {
                        android.util.Slog.i(TAG, "Skip queued ANR, pid=" + i + " " + timeoutRecord.mReason);
                        return;
                    }
                }
                timeoutRecord.mLatencyTracker.earlyDumpRequestSubmittedWithSize(this.mTempDumpedPids.size());
                java.util.concurrent.Future submit = this.mEarlyDumpExecutor.submit(new java.util.concurrent.Callable() { // from class: com.android.server.am.AnrHelper$$ExternalSyntheticLambda2
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        java.io.File lambda$appNotResponding$2;
                        lambda$appNotResponding$2 = com.android.server.am.AnrHelper.this.lambda$appNotResponding$2(i, timeoutRecord);
                        return lambda$appNotResponding$2;
                    }
                });
                timeoutRecord.mLatencyTracker.anrRecordPlacingOnQueueWithSize(this.mAnrRecords.size());
                this.mAnrRecords.add(new com.android.server.am.AnrHelper.AnrRecord(processRecord, str, applicationInfo, str2, windowProcessController, z, timeoutRecord, z2, submit));
                startAnrConsumerIfNeeded();
            }
        } finally {
            timeoutRecord.mLatencyTracker.appNotRespondingEnded();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.io.File lambda$appNotResponding$2(int i, com.android.internal.os.TimeoutRecord timeoutRecord) throws java.lang.Exception {
        java.io.File dumpStackTracesTempFile = com.android.server.am.StackTracesDumpHelper.dumpStackTracesTempFile(i, timeoutRecord.mLatencyTracker);
        this.mTempDumpedPids.remove(java.lang.Integer.valueOf(i));
        return dumpStackTracesTempFile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnrConsumerIfNeeded() {
        if (this.mRunning.compareAndSet(false, true)) {
            new com.android.server.am.AnrHelper.AnrConsumerThread().start();
        }
    }

    private static java.util.concurrent.ThreadPoolExecutor makeExpiringThreadPoolWithSize(int i, java.util.concurrent.ThreadFactory threadFactory) {
        java.util.concurrent.ThreadPoolExecutor threadPoolExecutor = new java.util.concurrent.ThreadPoolExecutor(i, i, 10L, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.LinkedBlockingQueue(), threadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    private class AnrConsumerThread extends java.lang.Thread {
        AnrConsumerThread() {
            super("AnrConsumer");
        }

        private com.android.server.am.AnrHelper.AnrRecord next() {
            synchronized (com.android.server.am.AnrHelper.this.mAnrRecords) {
                try {
                    if (com.android.server.am.AnrHelper.this.mAnrRecords.isEmpty()) {
                        return null;
                    }
                    com.android.server.am.AnrHelper.AnrRecord anrRecord = (com.android.server.am.AnrHelper.AnrRecord) com.android.server.am.AnrHelper.this.mAnrRecords.remove(0);
                    com.android.server.am.AnrHelper.this.mProcessingPid = anrRecord.mPid;
                    anrRecord.mTimeoutRecord.mLatencyTracker.anrRecordsQueueSizeWhenPopped(com.android.server.am.AnrHelper.this.mAnrRecords.size());
                    return anrRecord;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                com.android.server.am.AnrHelper.AnrRecord next = next();
                if (next == null) {
                    break;
                }
                com.android.server.am.AnrHelper.this.scheduleBinderHeavyHitterAutoSamplerIfNecessary();
                int i = next.mApp.mPid;
                if (i != next.mPid) {
                    android.util.Slog.i(com.android.server.am.AnrHelper.TAG, "Skip ANR with mismatched pid=" + next.mPid + ", current pid=" + i);
                } else {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    long j = uptimeMillis - next.mTimestamp;
                    boolean z = j > com.android.server.am.AnrHelper.EXPIRED_REPORT_TIME_MS || uptimeMillis < com.android.server.am.AnrHelper.SELF_ONLY_AFTER_BOOT_MS;
                    next.appNotResponding(z);
                    long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("Completed ANR of ");
                    sb.append(next.mApp.processName);
                    sb.append(" in ");
                    sb.append(uptimeMillis2 - uptimeMillis);
                    sb.append("ms, latency ");
                    sb.append(j);
                    sb.append(z ? "ms (expired, only dump ANR app)" : "ms");
                    android.util.Slog.d(com.android.server.am.AnrHelper.TAG, sb.toString());
                }
            }
            com.android.server.am.AnrHelper.this.mRunning.set(false);
            synchronized (com.android.server.am.AnrHelper.this.mAnrRecords) {
                try {
                    com.android.server.am.AnrHelper.this.mProcessingPid = -1;
                    if (!com.android.server.am.AnrHelper.this.mAnrRecords.isEmpty()) {
                        com.android.server.am.AnrHelper.this.startAnrConsumerIfNeeded();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleBinderHeavyHitterAutoSamplerIfNecessary() {
        try {
            android.os.Trace.traceBegin(64L, "scheduleBinderHeavyHitterAutoSamplerIfNecessary()");
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (this.mLastAnrTimeMs + CONSECUTIVE_ANR_TIME_MS > uptimeMillis) {
                this.mService.scheduleBinderHeavyHitterAutoSampler();
            }
            this.mLastAnrTimeMs = uptimeMillis;
            android.os.Trace.traceEnd(64L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(64L);
            throw th;
        }
    }

    private class AnrRecord {
        final boolean mAboveSystem;
        final java.lang.String mActivityShortComponentName;
        final com.android.server.am.ProcessRecord mApp;
        final android.content.pm.ApplicationInfo mAppInfo;
        final java.util.concurrent.Future<java.io.File> mFirstPidFilePromise;
        final boolean mIsContinuousAnr;
        final com.android.server.wm.WindowProcessController mParentProcess;
        final java.lang.String mParentShortComponentName;
        final int mPid;
        final com.android.internal.os.TimeoutRecord mTimeoutRecord;
        final long mTimestamp = android.os.SystemClock.uptimeMillis();
        final int mUid;

        AnrRecord(com.android.server.am.ProcessRecord processRecord, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, java.lang.String str2, com.android.server.wm.WindowProcessController windowProcessController, boolean z, com.android.internal.os.TimeoutRecord timeoutRecord, boolean z2, java.util.concurrent.Future<java.io.File> future) {
            this.mApp = processRecord;
            this.mPid = processRecord.mPid;
            this.mUid = processRecord.uid;
            this.mActivityShortComponentName = str;
            this.mParentShortComponentName = str2;
            this.mTimeoutRecord = timeoutRecord;
            this.mAppInfo = applicationInfo;
            this.mParentProcess = windowProcessController;
            this.mAboveSystem = z;
            this.mIsContinuousAnr = z2;
            this.mFirstPidFilePromise = future;
        }

        void appNotResponding(boolean z) {
            try {
                this.mTimeoutRecord.mLatencyTracker.anrProcessingStarted();
                this.mApp.mErrorState.appNotResponding(this.mActivityShortComponentName, this.mAppInfo, this.mParentShortComponentName, this.mParentProcess, this.mAboveSystem, this.mTimeoutRecord, com.android.server.am.AnrHelper.this.mAuxiliaryTaskExecutor, z, this.mIsContinuousAnr, this.mFirstPidFilePromise);
            } finally {
                this.mTimeoutRecord.mLatencyTracker.anrProcessingEnded();
            }
        }
    }
}
