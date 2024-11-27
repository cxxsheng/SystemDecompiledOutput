package com.android.server.pm;

/* loaded from: classes2.dex */
public final class BackgroundDexOptService {
    private static final long CANCELLATION_WAIT_CHECK_INTERVAL_MS = 200;

    @com.android.internal.annotations.VisibleForTesting
    static final int JOB_IDLE_OPTIMIZE = 800;

    @com.android.internal.annotations.VisibleForTesting
    static final int JOB_POST_BOOT_UPDATE = 801;
    private static final int LOW_THRESHOLD_MULTIPLIER_FOR_DOWNGRADE = 2;
    public static final int STATUS_ABORT_BATTERY = 4;
    public static final int STATUS_ABORT_BY_CANCELLATION = 1;
    public static final int STATUS_ABORT_NO_SPACE_LEFT = 2;
    public static final int STATUS_ABORT_THERMAL = 3;
    public static final int STATUS_DEX_OPT_FAILED = 5;
    public static final int STATUS_FATAL_ERROR = 6;
    public static final int STATUS_OK = 0;
    public static final int STATUS_UNSPECIFIED = -1;
    private static final int THERMAL_CUTOFF_DEFAULT = 2;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.lang.Thread mDexOptCancellingThread;
    private final com.android.server.pm.DexOptHelper mDexOptHelper;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.lang.Thread mDexOptThread;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDisableJobSchedulerJobs;
    private final long mDowngradeUnusedAppsThresholdInMillis;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<java.lang.String> mFailedPackageNamesPrimary;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<java.lang.String> mFailedPackageNamesSecondary;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mFinishedPostBootUpdate;
    private final com.android.server.pm.BackgroundDexOptService.Injector mInjector;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<java.lang.String> mLastCancelledPackages;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastExecutionDurationMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastExecutionStartUptimeMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mLastExecutionStatus;
    private final java.lang.Object mLock;
    private final java.util.List<com.android.server.pm.BackgroundDexOptService.PackagesUpdatedListener> mPackagesUpdatedListeners;
    private final com.android.server.pm.dex.ArtStatsLogUtils.BackgroundDexoptJobStatsLogger mStatsLogger;
    private int mThermalStatusCutoff;
    private static final java.lang.String TAG = "BackgroundDexOptService";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final long IDLE_OPTIMIZATION_PERIOD = java.util.concurrent.TimeUnit.DAYS.toMillis(1);
    private static final android.content.ComponentName sDexoptServiceName = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.pm.BackgroundDexOptJobService.class.getName());

    public interface PackagesUpdatedListener {
        void onPackagesUpdated(android.util.ArraySet<java.lang.String> arraySet);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    public BackgroundDexOptService(android.content.Context context, com.android.server.pm.dex.DexManager dexManager, com.android.server.pm.PackageManagerService packageManagerService) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        this(new com.android.server.pm.BackgroundDexOptService.Injector(context, dexManager, packageManagerService));
    }

    @com.android.internal.annotations.VisibleForTesting
    public BackgroundDexOptService(com.android.server.pm.BackgroundDexOptService.Injector injector) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        this.mStatsLogger = new com.android.server.pm.dex.ArtStatsLogUtils.BackgroundDexoptJobStatsLogger();
        this.mLock = new java.lang.Object();
        this.mLastExecutionStatus = -1;
        this.mLastCancelledPackages = new android.util.ArraySet<>();
        this.mFailedPackageNamesPrimary = new android.util.ArraySet<>();
        this.mFailedPackageNamesSecondary = new android.util.ArraySet<>();
        this.mPackagesUpdatedListeners = new java.util.ArrayList();
        this.mThermalStatusCutoff = 2;
        com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        this.mInjector = injector;
        this.mDexOptHelper = this.mInjector.getDexOptHelper();
        com.android.server.LocalServices.addService(com.android.server.pm.BackgroundDexOptService.class, this);
        this.mDowngradeUnusedAppsThresholdInMillis = this.mInjector.getDowngradeUnusedAppsThresholdInMillis();
    }

    public void systemReady() throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        if (this.mInjector.isBackgroundDexOptDisabled()) {
            return;
        }
        this.mInjector.getContext().registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.pm.BackgroundDexOptService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                com.android.server.pm.BackgroundDexOptService.this.mInjector.getContext().unregisterReceiver(this);
                com.android.server.pm.BackgroundDexOptService.this.scheduleAJob(801);
                com.android.server.pm.BackgroundDexOptService.this.scheduleAJob(800);
                if (com.android.server.pm.BackgroundDexOptService.DEBUG) {
                    android.util.Slog.d(com.android.server.pm.BackgroundDexOptService.TAG, "BootBgDexopt scheduled");
                }
            }
        }, new android.content.IntentFilter("android.intent.action.BOOT_COMPLETED"));
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        boolean isBackgroundDexOptDisabled = this.mInjector.isBackgroundDexOptDisabled();
        indentingPrintWriter.print("enabled:");
        indentingPrintWriter.println(!isBackgroundDexOptDisabled);
        if (isBackgroundDexOptDisabled) {
            return;
        }
        synchronized (this.mLock) {
            indentingPrintWriter.print("mDexOptThread:");
            indentingPrintWriter.println(this.mDexOptThread);
            indentingPrintWriter.print("mDexOptCancellingThread:");
            indentingPrintWriter.println(this.mDexOptCancellingThread);
            indentingPrintWriter.print("mFinishedPostBootUpdate:");
            indentingPrintWriter.println(this.mFinishedPostBootUpdate);
            indentingPrintWriter.print("mDisableJobSchedulerJobs:");
            indentingPrintWriter.println(this.mDisableJobSchedulerJobs);
            indentingPrintWriter.print("mLastExecutionStatus:");
            indentingPrintWriter.println(this.mLastExecutionStatus);
            indentingPrintWriter.print("mLastExecutionStartUptimeMs:");
            indentingPrintWriter.println(this.mLastExecutionStartUptimeMs);
            indentingPrintWriter.print("mLastExecutionDurationMs:");
            indentingPrintWriter.println(this.mLastExecutionDurationMs);
            indentingPrintWriter.print("now:");
            indentingPrintWriter.println(android.os.SystemClock.elapsedRealtime());
            indentingPrintWriter.print("mLastCancelledPackages:");
            indentingPrintWriter.println(java.lang.String.join(",", this.mLastCancelledPackages));
            indentingPrintWriter.print("mFailedPackageNamesPrimary:");
            indentingPrintWriter.println(java.lang.String.join(",", this.mFailedPackageNamesPrimary));
            indentingPrintWriter.print("mFailedPackageNamesSecondary:");
            indentingPrintWriter.println(java.lang.String.join(",", this.mFailedPackageNamesSecondary));
        }
    }

    public static com.android.server.pm.BackgroundDexOptService getService() {
        return (com.android.server.pm.BackgroundDexOptService) com.android.server.LocalServices.getService(com.android.server.pm.BackgroundDexOptService.class);
    }

    public boolean runBackgroundDexoptJob(@android.annotation.Nullable java.util.List<java.lang.String> list) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        enforceRootOrShell();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                waitForDexOptThreadToFinishLocked();
                resetStatesForNewDexOptRunLocked(java.lang.Thread.currentThread());
            }
            com.android.server.pm.PackageManagerService packageManagerService = this.mInjector.getPackageManagerService();
            if (list == null) {
                list = this.mDexOptHelper.getOptimizablePackages(packageManagerService.snapshotComputer());
            }
            boolean runIdleOptimization = runIdleOptimization(packageManagerService, list, false);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            markDexOptCompleted();
            return runIdleOptimization;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            markDexOptCompleted();
            throw th;
        }
    }

    public void cancelBackgroundDexoptJob() throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        enforceRootOrShell();
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.BackgroundDexOptService$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                com.android.server.pm.BackgroundDexOptService.this.lambda$cancelBackgroundDexoptJob$0();
            }
        });
    }

    public void setDisableJobSchedulerJobs(boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        enforceRootOrShell();
        synchronized (this.mLock) {
            this.mDisableJobSchedulerJobs = z;
        }
    }

    public void addPackagesUpdatedListener(com.android.server.pm.BackgroundDexOptService.PackagesUpdatedListener packagesUpdatedListener) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        synchronized (this.mLock) {
            this.mPackagesUpdatedListeners.add(packagesUpdatedListener);
        }
    }

    public void removePackagesUpdatedListener(com.android.server.pm.BackgroundDexOptService.PackagesUpdatedListener packagesUpdatedListener) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        synchronized (this.mLock) {
            this.mPackagesUpdatedListeners.remove(packagesUpdatedListener);
        }
    }

    public void notifyPackageChanged(java.lang.String str) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        synchronized (this.mLock) {
            this.mFailedPackageNamesPrimary.remove(str);
            this.mFailedPackageNamesSecondary.remove(str);
        }
    }

    boolean onStartJob(final com.android.server.pm.BackgroundDexOptJobService backgroundDexOptJobService, final android.app.job.JobParameters jobParameters) {
        android.util.Slog.i(TAG, "onStartJob:" + jobParameters.getJobId());
        boolean z = jobParameters.getJobId() == 801;
        final com.android.server.pm.PackageManagerService packageManagerService = this.mInjector.getPackageManagerService();
        if (packageManagerService.isStorageLow()) {
            android.util.Slog.w(TAG, "Low storage, skipping this run");
            markPostBootUpdateCompleted(jobParameters);
            return false;
        }
        final java.util.List<java.lang.String> optimizablePackages = this.mDexOptHelper.getOptimizablePackages(packageManagerService.snapshotComputer());
        if (optimizablePackages.isEmpty()) {
            android.util.Slog.i(TAG, "No packages to optimize");
            markPostBootUpdateCompleted(jobParameters);
            return false;
        }
        this.mThermalStatusCutoff = this.mInjector.getDexOptThermalCutoff();
        synchronized (this.mLock) {
            try {
                if (this.mDisableJobSchedulerJobs) {
                    android.util.Slog.i(TAG, "JobScheduler invocations disabled");
                    return false;
                }
                if (this.mDexOptThread != null && this.mDexOptThread.isAlive()) {
                    return false;
                }
                if (!z && !this.mFinishedPostBootUpdate) {
                    return false;
                }
                try {
                    com.android.server.pm.BackgroundDexOptService.Injector injector = this.mInjector;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("BackgroundDexOptService_");
                    sb.append(z ? "PostBoot" : "Idle");
                    resetStatesForNewDexOptRunLocked(injector.createAndStartThread(sb.toString(), new java.lang.Runnable() { // from class: com.android.server.pm.BackgroundDexOptService$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.pm.BackgroundDexOptService.this.lambda$onStartJob$1(packageManagerService, optimizablePackages, jobParameters, backgroundDexOptJobService);
                        }
                    }));
                } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
                    android.util.Slog.wtf(TAG, e);
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00c6  */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ void lambda$onStartJob$1(com.android.server.pm.PackageManagerService packageManagerService, java.util.List list, android.app.job.JobParameters jobParameters, com.android.server.pm.BackgroundDexOptJobService backgroundDexOptJobService) {
        boolean z;
        java.lang.String str;
        ?? r1 = "dexopt finishing. jobid:";
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(TAG, 16384L);
        timingsTraceAndSlog.traceBegin("jobExecution");
        boolean z2 = true;
        try {
            try {
                boolean runIdleOptimization = runIdleOptimization(packageManagerService, list, jobParameters.getJobId() == 801);
                timingsTraceAndSlog.traceEnd();
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("dexopt finishing. jobid:");
                r1 = jobParameters.getJobId();
                sb.append((int) r1);
                sb.append(" completed:");
                sb.append(runIdleOptimization);
                android.util.Slog.i(TAG, sb.toString());
                writeStatsLog(jobParameters);
                if (jobParameters.getJobId() == 801 && runIdleOptimization) {
                    markPostBootUpdateCompleted(jobParameters);
                }
                if (runIdleOptimization) {
                    z2 = false;
                }
            } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
                android.util.Slog.wtf(TAG, e);
                timingsTraceAndSlog.traceEnd();
                android.util.Slog.i(TAG, "dexopt finishing. jobid:" + jobParameters.getJobId() + " completed:false");
                writeStatsLog(jobParameters);
                if (jobParameters.getJobId() == 801) {
                }
            } catch (java.lang.RuntimeException e2) {
                try {
                    throw e2;
                } catch (java.lang.Throwable th) {
                    th = th;
                    z = true;
                    str = r1;
                    timingsTraceAndSlog.traceEnd();
                    android.util.Slog.i(TAG, str + jobParameters.getJobId() + " completed:false");
                    writeStatsLog(jobParameters);
                    if (jobParameters.getJobId() != 801) {
                    }
                    if (z) {
                        z2 = false;
                    }
                    backgroundDexOptJobService.jobFinished(jobParameters, z2);
                    markDexOptCompleted();
                    throw th;
                }
            }
            backgroundDexOptJobService.jobFinished(jobParameters, z2);
            markDexOptCompleted();
        } catch (java.lang.Throwable th2) {
            th = th2;
            z = false;
            str = r1;
            timingsTraceAndSlog.traceEnd();
            android.util.Slog.i(TAG, str + jobParameters.getJobId() + " completed:false");
            writeStatsLog(jobParameters);
            if (jobParameters.getJobId() != 801) {
            }
            if (z) {
            }
            backgroundDexOptJobService.jobFinished(jobParameters, z2);
            markDexOptCompleted();
            throw th;
        }
    }

    boolean onStopJob(com.android.server.pm.BackgroundDexOptJobService backgroundDexOptJobService, android.app.job.JobParameters jobParameters) {
        android.util.Slog.i(TAG, "onStopJob:" + jobParameters.getJobId());
        this.mInjector.createAndStartThread("DexOptCancel", new java.lang.Runnable() { // from class: com.android.server.pm.BackgroundDexOptService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.BackgroundDexOptService.this.lambda$onStopJob$2();
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStopJob$2() {
        try {
            lambda$cancelBackgroundDexoptJob$0();
        } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cancelDexOptAndWaitForCompletion, reason: merged with bridge method [inline-methods] */
    public void lambda$cancelBackgroundDexoptJob$0() throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        synchronized (this.mLock) {
            try {
                if (this.mDexOptThread == null) {
                    return;
                }
                if (this.mDexOptCancellingThread != null && this.mDexOptCancellingThread.isAlive()) {
                    waitForDexOptThreadToFinishLocked();
                    return;
                }
                this.mDexOptCancellingThread = java.lang.Thread.currentThread();
                try {
                    controlDexOptBlockingLocked(true);
                    waitForDexOptThreadToFinishLocked();
                } finally {
                    this.mDexOptCancellingThread = null;
                    this.mDexOptThread = null;
                    controlDexOptBlockingLocked(false);
                    this.mLock.notifyAll();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void waitForDexOptThreadToFinishLocked() {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(TAG, 262144L);
        timingsTraceAndSlog.traceBegin("waitForDexOptThreadToFinishLocked");
        while (this.mDexOptThread != null && this.mDexOptThread.isAlive()) {
            try {
                this.mLock.wait(CANCELLATION_WAIT_CHECK_INTERVAL_MS);
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.w(TAG, "Interrupted while waiting for dexopt thread");
                java.lang.Thread.currentThread().interrupt();
            }
        }
        timingsTraceAndSlog.traceEnd();
    }

    private void markDexOptCompleted() {
        synchronized (this.mLock) {
            try {
                if (this.mDexOptThread != java.lang.Thread.currentThread()) {
                    throw new java.lang.IllegalStateException("Only mDexOptThread can mark completion, mDexOptThread:" + this.mDexOptThread + " current:" + java.lang.Thread.currentThread());
                }
                this.mDexOptThread = null;
                this.mLock.notifyAll();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void resetStatesForNewDexOptRunLocked(java.lang.Thread thread) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        this.mDexOptThread = thread;
        this.mLastCancelledPackages.clear();
        controlDexOptBlockingLocked(false);
    }

    private void enforceRootOrShell() {
        int callingUid = this.mInjector.getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            throw new java.lang.SecurityException("Should be shell or root user");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void controlDexOptBlockingLocked(boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        this.mInjector.getPackageManagerService();
        this.mDexOptHelper.controlDexOptBlocking(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleAJob(int i) {
        android.app.job.JobScheduler jobScheduler = this.mInjector.getJobScheduler();
        android.app.job.JobInfo.Builder requiresDeviceIdle = new android.app.job.JobInfo.Builder(i, sDexoptServiceName).setRequiresDeviceIdle(true);
        if (i == 800) {
            requiresDeviceIdle.setRequiresCharging(true).setPeriodic(IDLE_OPTIMIZATION_PERIOD);
        }
        jobScheduler.schedule(requiresDeviceIdle.build());
    }

    private long getLowStorageThreshold() {
        long dataDirStorageLowBytes = this.mInjector.getDataDirStorageLowBytes();
        if (dataDirStorageLowBytes == 0) {
            android.util.Slog.e(TAG, "Invalid low storage threshold");
        }
        return dataDirStorageLowBytes;
    }

    private void logStatus(int i) {
        switch (i) {
            case 0:
                android.util.Slog.i(TAG, "Idle optimizations completed.");
                break;
            case 1:
                android.util.Slog.w(TAG, "Idle optimizations aborted by cancellation.");
                break;
            case 2:
                android.util.Slog.w(TAG, "Idle optimizations aborted because of space constraints.");
                break;
            case 3:
                android.util.Slog.w(TAG, "Idle optimizations aborted by thermal throttling.");
                break;
            case 4:
                android.util.Slog.w(TAG, "Idle optimizations aborted by low battery.");
                break;
            case 5:
                android.util.Slog.w(TAG, "Idle optimizations failed from dexopt.");
                break;
            default:
                android.util.Slog.w(TAG, "Idle optimizations ended with unexpected code: " + i);
                break;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0048 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean runIdleOptimization(com.android.server.pm.PackageManagerService packageManagerService, java.util.List<java.lang.String> list, boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        int i;
        synchronized (this.mLock) {
            i = -1;
            this.mLastExecutionStatus = -1;
            this.mLastExecutionStartUptimeMs = android.os.SystemClock.uptimeMillis();
            this.mLastExecutionDurationMs = -1L;
        }
        try {
            i = idleOptimizePackages(packageManagerService, list, getLowStorageThreshold(), z);
            logStatus(i);
            boolean z2 = i == 0 || i == 5;
            synchronized (this.mLock) {
                this.mLastExecutionStatus = i;
                this.mLastExecutionDurationMs = android.os.SystemClock.uptimeMillis() - this.mLastExecutionStartUptimeMs;
            }
            return z2;
        } catch (java.lang.RuntimeException e) {
            try {
                throw e;
            } catch (java.lang.Throwable th) {
                th = th;
                i = 6;
                synchronized (this.mLock) {
                    this.mLastExecutionStatus = i;
                    this.mLastExecutionDurationMs = android.os.SystemClock.uptimeMillis() - this.mLastExecutionStartUptimeMs;
                }
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            synchronized (this.mLock) {
            }
        }
    }

    private long getDirectorySize(java.io.File file) {
        if (file.isDirectory()) {
            long j = 0;
            for (java.io.File file2 : file.listFiles()) {
                j += getDirectorySize(file2);
            }
            return j;
        }
        return file.length();
    }

    private long getPackageSize(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str) {
        android.content.pm.PackageInfo packageInfo = computer.getPackageInfo(str, 0L, 0);
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            return 0L;
        }
        java.io.File file = java.nio.file.Paths.get(packageInfo.applicationInfo.sourceDir, new java.lang.String[0]).toFile();
        if (file.isFile()) {
            file = file.getParentFile();
        }
        long directorySize = 0 + getDirectorySize(file);
        if (!com.android.internal.util.ArrayUtils.isEmpty(packageInfo.applicationInfo.splitSourceDirs)) {
            for (java.lang.String str2 : packageInfo.applicationInfo.splitSourceDirs) {
                java.io.File file2 = java.nio.file.Paths.get(str2, new java.lang.String[0]).toFile();
                if (file2.isFile()) {
                    file2 = file2.getParentFile();
                }
                if (!file.getAbsolutePath().equals(file2.getAbsolutePath())) {
                    directorySize += getDirectorySize(file2);
                }
            }
        }
        return directorySize;
    }

    private int idleOptimizePackages(com.android.server.pm.PackageManagerService packageManagerService, java.util.List<java.lang.String> list, long j, boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        java.util.List<java.lang.String> list2;
        java.util.List<java.lang.String> list3;
        int convertPackageDexOptimizerStatusToInternal;
        int reconcileSecondaryDexFiles;
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        try {
            boolean supportSecondaryDex = this.mInjector.supportSecondaryDex();
            if (supportSecondaryDex && (reconcileSecondaryDexFiles = reconcileSecondaryDexFiles()) != 0) {
                return reconcileSecondaryDexFiles;
            }
            boolean shouldDowngrade = shouldDowngrade(2 * j);
            if (DEBUG) {
                android.util.Slog.d(TAG, "Should Downgrade " + shouldDowngrade);
            }
            if (shouldDowngrade) {
                com.android.server.pm.Computer snapshotComputer = packageManagerService.snapshotComputer();
                java.util.Set<java.lang.String> unusedPackages = snapshotComputer.getUnusedPackages(this.mDowngradeUnusedAppsThresholdInMillis);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Unsused Packages " + java.lang.String.join(",", unusedPackages));
                }
                if (!unusedPackages.isEmpty()) {
                    for (java.lang.String str : unusedPackages) {
                        int abortIdleOptimizations = abortIdleOptimizations(-1L);
                        if (abortIdleOptimizations != 0) {
                            return abortIdleOptimizations;
                        }
                        int downgradePackage = downgradePackage(snapshotComputer, packageManagerService, str, true, z);
                        if (downgradePackage == 1) {
                            arraySet.add(str);
                        }
                        int convertPackageDexOptimizerStatusToInternal2 = convertPackageDexOptimizerStatusToInternal(downgradePackage);
                        if (convertPackageDexOptimizerStatusToInternal2 != 0) {
                            return convertPackageDexOptimizerStatusToInternal2;
                        }
                        if (supportSecondaryDex && (convertPackageDexOptimizerStatusToInternal = convertPackageDexOptimizerStatusToInternal(downgradePackage(snapshotComputer, packageManagerService, str, false, z))) != 0) {
                            return convertPackageDexOptimizerStatusToInternal;
                        }
                    }
                    java.util.ArrayList arrayList = new java.util.ArrayList(list);
                    arrayList.removeAll(unusedPackages);
                    list3 = arrayList;
                    return optimizePackages(list3, j, arraySet, z);
                }
                list2 = list;
            } else {
                list2 = list;
            }
            list3 = list2;
            return optimizePackages(list3, j, arraySet, z);
        } finally {
            notifyPinService(arraySet);
            notifyPackagesUpdated(arraySet);
        }
    }

    private int optimizePackages(java.util.List<java.lang.String> list, long j, android.util.ArraySet<java.lang.String> arraySet, boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        boolean supportSecondaryDex = this.mInjector.supportSecondaryDex();
        int i = 0;
        for (java.lang.String str : list) {
            int abortIdleOptimizations = abortIdleOptimizations(j);
            if (abortIdleOptimizations != 0) {
                return abortIdleOptimizations;
            }
            int optimizePackage = optimizePackage(str, true, z);
            if (optimizePackage == 2) {
                return 1;
            }
            if (optimizePackage == 1) {
                arraySet.add(str);
            } else if (optimizePackage == -1) {
                i = convertPackageDexOptimizerStatusToInternal(optimizePackage);
            }
            if (supportSecondaryDex) {
                int optimizePackage2 = optimizePackage(str, false, z);
                if (optimizePackage2 == 2) {
                    return 1;
                }
                if (optimizePackage2 == -1) {
                    i = convertPackageDexOptimizerStatusToInternal(optimizePackage2);
                }
            }
        }
        return i;
    }

    private int downgradePackage(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.server.pm.PackageManagerService packageManagerService, java.lang.String str, boolean z, boolean z2) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        int i;
        int performDexOptPrimary;
        if (DEBUG) {
            android.util.Slog.d(TAG, "Downgrading " + str);
        }
        if (isCancelling()) {
            return 2;
        }
        java.lang.String compilerFilterForReason = com.android.server.pm.PackageManagerServiceCompilerMapping.getCompilerFilterForReason(11);
        if (!dalvik.system.DexFile.isProfileGuidedCompilerFilter(compilerFilterForReason)) {
            i = 36;
        } else {
            i = 37;
        }
        if (!z2) {
            i |= 512;
        }
        long packageSize = getPackageSize(computer, str);
        if (z || com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str)) {
            if (!packageManagerService.canHaveOatDir(computer, str)) {
                packageManagerService.deleteOatArtifactsOfPackage(computer, str);
                performDexOptPrimary = 0;
            } else {
                performDexOptPrimary = performDexOptPrimary(str, 11, compilerFilterForReason, i);
            }
        } else {
            performDexOptPrimary = performDexOptSecondary(str, 11, compilerFilterForReason, i);
        }
        if (performDexOptPrimary == 1) {
            com.android.internal.util.FrameworkStatsLog.write(128, str, packageSize, getPackageSize(packageManagerService.snapshotComputer(), str), false);
        }
        return performDexOptPrimary;
    }

    private int reconcileSecondaryDexFiles() throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        for (java.lang.String str : this.mInjector.getDexManager().getAllPackagesWithSecondaryDexFiles()) {
            if (isCancelling()) {
                return 1;
            }
            this.mInjector.getDexManager().reconcileSecondaryDexFiles(str);
        }
        return 0;
    }

    private int optimizePackage(java.lang.String str, boolean z, boolean z2) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        int i;
        int i2 = z2 ? 2 : 9;
        java.lang.String compilerFilterForReason = com.android.server.pm.PackageManagerServiceCompilerMapping.getCompilerFilterForReason(i2);
        if (z2) {
            i = 4;
        } else {
            i = 517;
        }
        if (dalvik.system.DexFile.isProfileGuidedCompilerFilter(compilerFilterForReason)) {
            i |= 1;
        }
        if (z || com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str)) {
            return performDexOptPrimary(str, i2, compilerFilterForReason, i);
        }
        return performDexOptSecondary(str, i2, compilerFilterForReason, i);
    }

    private int performDexOptPrimary(java.lang.String str, int i, java.lang.String str2, int i2) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        final com.android.server.pm.dex.DexoptOptions dexoptOptions = new com.android.server.pm.dex.DexoptOptions(str, i, str2, null, i2);
        return trackPerformDexOpt(str, true, new com.android.internal.util.FunctionalUtils.ThrowingCheckedSupplier() { // from class: com.android.server.pm.BackgroundDexOptService$$ExternalSyntheticLambda1
            public final java.lang.Object get() {
                java.lang.Integer lambda$performDexOptPrimary$3;
                lambda$performDexOptPrimary$3 = com.android.server.pm.BackgroundDexOptService.this.lambda$performDexOptPrimary$3(dexoptOptions);
                return lambda$performDexOptPrimary$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$performDexOptPrimary$3(com.android.server.pm.dex.DexoptOptions dexoptOptions) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        return java.lang.Integer.valueOf(this.mDexOptHelper.performDexOptWithStatus(dexoptOptions));
    }

    private int performDexOptSecondary(java.lang.String str, int i, java.lang.String str2, int i2) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        final com.android.server.pm.dex.DexoptOptions dexoptOptions = new com.android.server.pm.dex.DexoptOptions(str, i, str2, null, i2 | 8);
        return trackPerformDexOpt(str, false, new com.android.internal.util.FunctionalUtils.ThrowingCheckedSupplier() { // from class: com.android.server.pm.BackgroundDexOptService$$ExternalSyntheticLambda2
            public final java.lang.Object get() {
                java.lang.Integer lambda$performDexOptSecondary$4;
                lambda$performDexOptSecondary$4 = com.android.server.pm.BackgroundDexOptService.this.lambda$performDexOptSecondary$4(dexoptOptions);
                return lambda$performDexOptSecondary$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$performDexOptSecondary$4(com.android.server.pm.dex.DexoptOptions dexoptOptions) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        int i;
        if (this.mDexOptHelper.performDexOpt(dexoptOptions)) {
            i = 1;
        } else {
            i = -1;
        }
        return java.lang.Integer.valueOf(i);
    }

    private int trackPerformDexOpt(java.lang.String str, boolean z, com.android.internal.util.FunctionalUtils.ThrowingCheckedSupplier<java.lang.Integer, com.android.server.pm.Installer.LegacyDexoptDisabledException> throwingCheckedSupplier) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<java.lang.String> arraySet = z ? this.mFailedPackageNamesPrimary : this.mFailedPackageNamesSecondary;
                if (arraySet.contains(str)) {
                    return 0;
                }
                int intValue = ((java.lang.Integer) throwingCheckedSupplier.get()).intValue();
                if (intValue == -1) {
                    synchronized (this.mLock) {
                        arraySet.add(str);
                    }
                } else if (intValue == 2) {
                    synchronized (this.mLock) {
                        this.mLastCancelledPackages.add(str);
                    }
                }
                return intValue;
            } finally {
            }
        }
    }

    private int convertPackageDexOptimizerStatusToInternal(int i) {
        switch (i) {
            case -1:
                break;
            case 0:
            case 1:
                break;
            case 2:
                break;
            default:
                android.util.Slog.e(TAG, "Unkknown error code from PackageDexOptimizer:" + i, new java.lang.RuntimeException());
                break;
        }
        return 5;
    }

    private int abortIdleOptimizations(long j) {
        if (isCancelling()) {
            return 1;
        }
        int currentThermalStatus = this.mInjector.getCurrentThermalStatus();
        if (DEBUG) {
            android.util.Log.d(TAG, "Thermal throttling status during bgdexopt: " + currentThermalStatus);
        }
        if (currentThermalStatus >= this.mThermalStatusCutoff) {
            return 3;
        }
        if (this.mInjector.isBatteryLevelLow()) {
            return 4;
        }
        long dataDirUsableSpace = this.mInjector.getDataDirUsableSpace();
        if (dataDirUsableSpace < j) {
            android.util.Slog.w(TAG, "Aborting background dex opt job due to low storage: " + dataDirUsableSpace);
            return 2;
        }
        return 0;
    }

    private boolean shouldDowngrade(long j) {
        if (this.mInjector.getDataDirUsableSpace() < j) {
            return true;
        }
        return false;
    }

    private boolean isCancelling() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDexOptCancellingThread != null;
        }
        return z;
    }

    private void markPostBootUpdateCompleted(android.app.job.JobParameters jobParameters) {
        if (jobParameters.getJobId() != 801) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (!this.mFinishedPostBootUpdate) {
                    this.mFinishedPostBootUpdate = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mInjector.getJobScheduler().cancel(801);
    }

    private void notifyPinService(android.util.ArraySet<java.lang.String> arraySet) {
        com.android.server.PinnerService pinnerService = this.mInjector.getPinnerService();
        if (pinnerService != null) {
            android.util.Slog.i(TAG, "Pinning optimized code " + arraySet);
            pinnerService.update(arraySet, false);
        }
    }

    private void notifyPackagesUpdated(android.util.ArraySet<java.lang.String> arraySet) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<com.android.server.pm.BackgroundDexOptService.PackagesUpdatedListener> it = this.mPackagesUpdatedListeners.iterator();
                while (it.hasNext()) {
                    it.next().onPackagesUpdated(arraySet);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void writeStatsLog(android.app.job.JobParameters jobParameters) {
        int i;
        long j;
        synchronized (this.mLock) {
            i = this.mLastExecutionStatus;
            j = this.mLastExecutionDurationMs;
        }
        this.mStatsLogger.write(i, jobParameters.getStopReason(), j);
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class Injector {
        private final android.content.Context mContext;
        private final java.io.File mDataDir = android.os.Environment.getDataDirectory();
        private final com.android.server.pm.dex.DexManager mDexManager;
        private final com.android.server.pm.PackageManagerService mPackageManagerService;

        Injector(android.content.Context context, com.android.server.pm.dex.DexManager dexManager, com.android.server.pm.PackageManagerService packageManagerService) {
            this.mContext = context;
            this.mDexManager = dexManager;
            this.mPackageManagerService = packageManagerService;
        }

        int getCallingUid() {
            return android.os.Binder.getCallingUid();
        }

        android.content.Context getContext() {
            return this.mContext;
        }

        com.android.server.pm.PackageManagerService getPackageManagerService() {
            return this.mPackageManagerService;
        }

        com.android.server.pm.DexOptHelper getDexOptHelper() {
            return new com.android.server.pm.DexOptHelper(getPackageManagerService());
        }

        android.app.job.JobScheduler getJobScheduler() {
            return (android.app.job.JobScheduler) this.mContext.getSystemService(android.app.job.JobScheduler.class);
        }

        com.android.server.pm.dex.DexManager getDexManager() {
            return this.mDexManager;
        }

        com.android.server.PinnerService getPinnerService() {
            return (com.android.server.PinnerService) com.android.server.LocalServices.getService(com.android.server.PinnerService.class);
        }

        boolean isBackgroundDexOptDisabled() {
            return android.os.SystemProperties.getBoolean("pm.dexopt.disable_bg_dexopt", false);
        }

        boolean isBatteryLevelLow() {
            return ((android.os.BatteryManagerInternal) com.android.server.LocalServices.getService(android.os.BatteryManagerInternal.class)).getBatteryLevelLow();
        }

        long getDowngradeUnusedAppsThresholdInMillis() {
            java.lang.String str = android.os.SystemProperties.get("pm.dexopt.downgrade_after_inactive_days");
            if (str == null || str.isEmpty()) {
                android.util.Slog.w(com.android.server.pm.BackgroundDexOptService.TAG, "SysProp pm.dexopt.downgrade_after_inactive_days not set");
                return com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            }
            return java.util.concurrent.TimeUnit.DAYS.toMillis(java.lang.Long.parseLong(str));
        }

        boolean supportSecondaryDex() {
            return android.os.SystemProperties.getBoolean("dalvik.vm.dexopt.secondary", false);
        }

        long getDataDirUsableSpace() {
            return this.mDataDir.getUsableSpace();
        }

        long getDataDirStorageLowBytes() {
            return ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).getStorageLowBytes(this.mDataDir);
        }

        int getCurrentThermalStatus() {
            try {
                return android.os.IThermalService.Stub.asInterface(android.os.ServiceManager.getService("thermalservice")).getCurrentThermalStatus();
            } catch (android.os.RemoteException e) {
                return 3;
            }
        }

        int getDexOptThermalCutoff() {
            return android.os.SystemProperties.getInt("dalvik.vm.dexopt.thermal-cutoff", 2);
        }

        java.lang.Thread createAndStartThread(java.lang.String str, java.lang.Runnable runnable) {
            java.lang.Thread thread = new java.lang.Thread(runnable, str);
            android.util.Slog.i(com.android.server.pm.BackgroundDexOptService.TAG, "Starting thread:" + str);
            thread.start();
            return thread;
        }
    }
}
