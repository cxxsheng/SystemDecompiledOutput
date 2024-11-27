package com.android.server.selinux;

/* loaded from: classes2.dex */
public class SelinuxAuditLogsService extends android.app.job.JobService {
    private static final java.lang.String SELINUX_AUDIT_NAMESPACE = "SelinuxAuditLogsNamespace";
    private static final java.lang.String TAG = "SelinuxAuditLogs";
    static final int AUDITD_TAG_CODE = android.util.EventLog.getTagCode("auditd");
    private static final int SELINUX_AUDIT_JOB_ID = 25327386;
    private static final android.app.job.JobInfo SELINUX_AUDIT_JOB = new android.app.job.JobInfo.Builder(SELINUX_AUDIT_JOB_ID, new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.selinux.SelinuxAuditLogsService.class.getName())).setPeriodic(java.util.concurrent.TimeUnit.DAYS.toMillis(1)).setRequiresDeviceIdle(true).setRequiresCharging(true).setRequiresBatteryNotLow(true).build();
    private static final java.util.concurrent.ExecutorService EXECUTOR_SERVICE = java.util.concurrent.Executors.newSingleThreadExecutor();
    private static final java.util.concurrent.atomic.AtomicReference<java.lang.Boolean> IS_RUNNING = new java.util.concurrent.atomic.AtomicReference<>(false);
    private static final com.android.server.selinux.SelinuxAuditLogsCollector AUDIT_LOGS_COLLECTOR = new com.android.server.selinux.SelinuxAuditLogsCollector(new com.android.server.selinux.RateLimiter(java.time.Duration.ofMillis(10)), new com.android.server.selinux.QuotaLimiter(50000));

    public static void schedule(android.content.Context context) {
        if (!com.android.sdksandbox.flags.Flags.selinuxSdkSandboxAudit()) {
            android.util.Log.d(TAG, "SelinuxAuditLogsService not enabled");
        } else if (AUDITD_TAG_CODE == -1) {
            android.util.Log.e(TAG, "auditd is not a registered tag on this system");
        } else if (((android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class)).forNamespace(SELINUX_AUDIT_NAMESPACE).schedule(SELINUX_AUDIT_JOB) == 0) {
            android.util.Log.e(TAG, "SelinuxAuditLogsService could not be started.");
        }
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        if (jobParameters.getJobId() != SELINUX_AUDIT_JOB_ID) {
            android.util.Log.e(TAG, "The job id does not match the expected selinux job id.");
            return false;
        }
        AUDIT_LOGS_COLLECTOR.mStopRequested.set(false);
        IS_RUNNING.set(true);
        EXECUTOR_SERVICE.execute(new com.android.server.selinux.SelinuxAuditLogsService.LogsCollectorJob(this, jobParameters));
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        if (jobParameters.getJobId() != SELINUX_AUDIT_JOB_ID) {
            return false;
        }
        AUDIT_LOGS_COLLECTOR.mStopRequested.set(true);
        return IS_RUNNING.get().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class LogsCollectorJob implements java.lang.Runnable {
        private final android.app.job.JobService mAuditLogService;
        private final android.app.job.JobParameters mParams;

        LogsCollectorJob(android.app.job.JobService jobService, android.app.job.JobParameters jobParameters) {
            this.mAuditLogService = jobService;
            this.mParams = jobParameters;
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.selinux.SelinuxAuditLogsService.IS_RUNNING.updateAndGet(new java.util.function.UnaryOperator() { // from class: com.android.server.selinux.SelinuxAuditLogsService$LogsCollectorJob$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.Boolean lambda$run$0;
                    lambda$run$0 = com.android.server.selinux.SelinuxAuditLogsService.LogsCollectorJob.this.lambda$run$0((java.lang.Boolean) obj);
                    return lambda$run$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Boolean lambda$run$0(java.lang.Boolean bool) {
            boolean collect = com.android.server.selinux.SelinuxAuditLogsService.AUDIT_LOGS_COLLECTOR.collect(com.android.server.selinux.SelinuxAuditLogsService.AUDITD_TAG_CODE);
            if (collect) {
                this.mAuditLogService.jobFinished(this.mParams, false);
            }
            return java.lang.Boolean.valueOf(!collect);
        }
    }
}
