package com.android.server.pm;

/* loaded from: classes2.dex */
public class GentleUpdateHelper {
    private static final int JOB_ID = 235306967;
    private static final long PENDING_CHECK_MILLIS = java.util.concurrent.TimeUnit.SECONDS.toMillis(10);
    private static final java.lang.String TAG = "GentleUpdateHelper";
    private final com.android.server.pm.AppStateHelper mAppStateHelper;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private boolean mHasPendingIdleJob;
    private final java.util.ArrayDeque<com.android.server.pm.GentleUpdateHelper.PendingInstallConstraintsCheck> mPendingChecks = new java.util.ArrayDeque<>();
    private final java.util.ArrayList<java.util.concurrent.CompletableFuture<java.lang.Boolean>> mPendingIdleFutures = new java.util.ArrayList<>();

    public static class Service extends android.app.job.JobService {
        @Override // android.app.job.JobService
        public boolean onStartJob(android.app.job.JobParameters jobParameters) {
            try {
                final com.android.server.pm.GentleUpdateHelper gentleUpdateHelper = android.app.ActivityThread.getPackageManager().getPackageInstaller().getGentleUpdateHelper();
                android.os.Handler handler = gentleUpdateHelper.mHandler;
                java.util.Objects.requireNonNull(gentleUpdateHelper);
                handler.post(new java.lang.Runnable() { // from class: com.android.server.pm.GentleUpdateHelper$Service$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.GentleUpdateHelper.this.runIdleJob();
                    }
                });
                return false;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.pm.GentleUpdateHelper.TAG, "Failed to get PackageInstallerService", e);
                return false;
            }
        }

        @Override // android.app.job.JobService
        public boolean onStopJob(android.app.job.JobParameters jobParameters) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PendingInstallConstraintsCheck {
        public final android.content.pm.PackageInstaller.InstallConstraints constraints;
        public final java.util.concurrent.CompletableFuture<android.content.pm.PackageInstaller.InstallConstraintsResult> future;
        private final long mFinishTime;
        public final java.util.List<java.lang.String> packageNames;

        PendingInstallConstraintsCheck(java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, java.util.concurrent.CompletableFuture<android.content.pm.PackageInstaller.InstallConstraintsResult> completableFuture, long j) {
            this.packageNames = list;
            this.constraints = installConstraints;
            this.future = completableFuture;
            this.mFinishTime = android.os.SystemClock.elapsedRealtime() + java.lang.Math.max(0L, java.lang.Math.min(com.android.server.usage.UnixCalendar.WEEK_IN_MILLIS, j));
        }

        public boolean isTimedOut() {
            return android.os.SystemClock.elapsedRealtime() >= this.mFinishTime;
        }

        public long getRemainingTimeMillis() {
            return java.lang.Math.max(this.mFinishTime - android.os.SystemClock.elapsedRealtime(), 0L);
        }

        void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.printPair(com.android.server.storage.DiskStatsFileLogger.PACKAGE_NAMES_KEY, this.packageNames);
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("finishTime", java.lang.Long.valueOf(this.mFinishTime));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("constraints notInCallRequired", java.lang.Boolean.valueOf(this.constraints.isNotInCallRequired()));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("constraints deviceIdleRequired", java.lang.Boolean.valueOf(this.constraints.isDeviceIdleRequired()));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("constraints appNotForegroundRequired", java.lang.Boolean.valueOf(this.constraints.isAppNotForegroundRequired()));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("constraints appNotInteractingRequired", java.lang.Boolean.valueOf(this.constraints.isAppNotInteractingRequired()));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("constraints appNotTopVisibleRequired", java.lang.Boolean.valueOf(this.constraints.isAppNotTopVisibleRequired()));
        }
    }

    GentleUpdateHelper(android.content.Context context, android.os.Looper looper, com.android.server.pm.AppStateHelper appStateHelper) {
        this.mContext = context;
        this.mHandler = new android.os.Handler(looper);
        this.mAppStateHelper = appStateHelper;
    }

    void systemReady() {
        android.app.ActivityManager activityManager = (android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class);
        activityManager.addOnUidImportanceListener(new android.app.ActivityManager.OnUidImportanceListener() { // from class: com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda5
            public final void onUidImportance(int i, int i2) {
                com.android.server.pm.GentleUpdateHelper.this.onUidImportance(i, i2);
            }
        }, 100);
        activityManager.addOnUidImportanceListener(new android.app.ActivityManager.OnUidImportanceListener() { // from class: com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda5
            public final void onUidImportance(int i, int i2) {
                com.android.server.pm.GentleUpdateHelper.this.onUidImportance(i, i2);
            }
        }, 125);
    }

    java.util.concurrent.CompletableFuture<android.content.pm.PackageInstaller.InstallConstraintsResult> checkInstallConstraints(final java.util.List<java.lang.String> list, final android.content.pm.PackageInstaller.InstallConstraints installConstraints, final long j) {
        final java.util.concurrent.CompletableFuture<android.content.pm.PackageInstaller.InstallConstraintsResult> completableFuture = new java.util.concurrent.CompletableFuture<>();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.GentleUpdateHelper.this.lambda$checkInstallConstraints$2(list, installConstraints, completableFuture, j);
            }
        });
        return completableFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkInstallConstraints$2(java.util.List list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, java.util.concurrent.CompletableFuture completableFuture, long j) {
        final com.android.server.pm.GentleUpdateHelper.PendingInstallConstraintsCheck pendingInstallConstraintsCheck = new com.android.server.pm.GentleUpdateHelper.PendingInstallConstraintsCheck(list, installConstraints, completableFuture, j);
        (installConstraints.isDeviceIdleRequired() ? checkDeviceIdle() : java.util.concurrent.CompletableFuture.completedFuture(false)).thenAccept(new java.util.function.Consumer() { // from class: com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.GentleUpdateHelper.this.lambda$checkInstallConstraints$1(pendingInstallConstraintsCheck, (java.lang.Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkInstallConstraints$1(final com.android.server.pm.GentleUpdateHelper.PendingInstallConstraintsCheck pendingInstallConstraintsCheck, java.lang.Boolean bool) {
        com.android.internal.util.Preconditions.checkState(this.mHandler.getLooper().isCurrentThread());
        if (!processPendingCheck(pendingInstallConstraintsCheck, bool.booleanValue())) {
            this.mPendingChecks.add(pendingInstallConstraintsCheck);
            scheduleIdleJob();
            this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.GentleUpdateHelper.this.lambda$checkInstallConstraints$0(pendingInstallConstraintsCheck);
                }
            }, pendingInstallConstraintsCheck.getRemainingTimeMillis());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkInstallConstraints$0(com.android.server.pm.GentleUpdateHelper.PendingInstallConstraintsCheck pendingInstallConstraintsCheck) {
        processPendingCheck(pendingInstallConstraintsCheck, false);
    }

    private java.util.concurrent.CompletableFuture<java.lang.Boolean> checkDeviceIdle() {
        final java.util.concurrent.CompletableFuture<java.lang.Boolean> completableFuture = new java.util.concurrent.CompletableFuture<>();
        this.mPendingIdleFutures.add(completableFuture);
        scheduleIdleJob();
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.GentleUpdateHelper.lambda$checkDeviceIdle$3(completableFuture);
            }
        }, PENDING_CHECK_MILLIS);
        return completableFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$checkDeviceIdle$3(java.util.concurrent.CompletableFuture completableFuture) {
        completableFuture.complete(false);
    }

    private void scheduleIdleJob() {
        if (android.os.SystemProperties.getBoolean("debug.pm.gentle_update_test.is_idle", false)) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.GentleUpdateHelper.this.runIdleJob();
                }
            });
        } else {
            if (this.mHasPendingIdleJob) {
                return;
            }
            this.mHasPendingIdleJob = true;
            ((android.app.job.JobScheduler) this.mContext.getSystemService(android.app.job.JobScheduler.class)).schedule(new android.app.job.JobInfo.Builder(JOB_ID, new android.content.ComponentName(this.mContext.getPackageName(), com.android.server.pm.GentleUpdateHelper.Service.class.getName())).setRequiresDeviceIdle(true).build());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runIdleJob() {
        this.mHasPendingIdleJob = false;
        processPendingChecksInIdle();
        java.util.Iterator<java.util.concurrent.CompletableFuture<java.lang.Boolean>> it = this.mPendingIdleFutures.iterator();
        while (it.hasNext()) {
            it.next().complete(true);
        }
        this.mPendingIdleFutures.clear();
    }

    private boolean areConstraintsSatisfied(java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, boolean z) {
        return (!installConstraints.isDeviceIdleRequired() || z) && !((installConstraints.isAppNotForegroundRequired() && this.mAppStateHelper.hasForegroundApp(list)) || ((installConstraints.isAppNotInteractingRequired() && this.mAppStateHelper.hasInteractingApp(list)) || ((installConstraints.isAppNotTopVisibleRequired() && this.mAppStateHelper.hasTopVisibleApp(list)) || (installConstraints.isNotInCallRequired() && this.mAppStateHelper.isInCall()))));
    }

    private boolean processPendingCheck(com.android.server.pm.GentleUpdateHelper.PendingInstallConstraintsCheck pendingInstallConstraintsCheck, boolean z) {
        java.util.concurrent.CompletableFuture<android.content.pm.PackageInstaller.InstallConstraintsResult> completableFuture = pendingInstallConstraintsCheck.future;
        if (completableFuture.isDone()) {
            return true;
        }
        boolean areConstraintsSatisfied = areConstraintsSatisfied(this.mAppStateHelper.getDependencyPackages(pendingInstallConstraintsCheck.packageNames), pendingInstallConstraintsCheck.constraints, z);
        if (areConstraintsSatisfied || pendingInstallConstraintsCheck.isTimedOut()) {
            completableFuture.complete(new android.content.pm.PackageInstaller.InstallConstraintsResult(areConstraintsSatisfied));
            return true;
        }
        return false;
    }

    private void processPendingChecksInIdle() {
        int size = this.mPendingChecks.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.GentleUpdateHelper.PendingInstallConstraintsCheck remove = this.mPendingChecks.remove();
            if (!processPendingCheck(remove, true)) {
                this.mPendingChecks.add(remove);
            }
        }
        if (!this.mPendingChecks.isEmpty()) {
            scheduleIdleJob();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onUidImportance, reason: merged with bridge method [inline-methods] */
    public void lambda$onUidImportance$4(java.lang.String str, int i) {
        int size = this.mPendingChecks.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.pm.GentleUpdateHelper.PendingInstallConstraintsCheck remove = this.mPendingChecks.remove();
            if (!this.mAppStateHelper.getDependencyPackages(remove.packageNames).contains(str) || !processPendingCheck(remove, false)) {
                this.mPendingChecks.add(remove);
            }
        }
        if (!this.mPendingChecks.isEmpty()) {
            scheduleIdleJob();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUidImportance(int i, final int i2) {
        try {
            final java.lang.String nameForUid = android.app.ActivityThread.getPackageManager().getNameForUid(i);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.GentleUpdateHelper.this.lambda$onUidImportance$4(nameForUid, i2);
                }
            });
        } catch (android.os.RemoteException e) {
        }
    }

    void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Gentle update with constraints info:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("hasPendingIdleJob", java.lang.Boolean.valueOf(this.mHasPendingIdleJob));
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("Num of PendingIdleFutures", java.lang.Integer.valueOf(this.mPendingIdleFutures.size()));
        indentingPrintWriter.println();
        java.util.ArrayDeque<com.android.server.pm.GentleUpdateHelper.PendingInstallConstraintsCheck> clone = this.mPendingChecks.clone();
        int size = clone.size();
        indentingPrintWriter.printPair("Num of PendingChecks", java.lang.Integer.valueOf(size));
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < size; i++) {
            indentingPrintWriter.print(i);
            indentingPrintWriter.print(":");
            clone.remove().dump(indentingPrintWriter);
            indentingPrintWriter.println();
        }
    }
}
