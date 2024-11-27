package com.android.server.usage;

/* loaded from: classes2.dex */
public class UsageStatsIdleService extends android.app.job.JobService {
    private static final java.lang.String PRUNE_JOB_NS = "usagestats_prune";
    private static final java.lang.String UPDATE_MAPPINGS_JOB_NS = "usagestats_mapping";
    private static final java.lang.String USER_ID_KEY = "user_id";

    static void schedulePruneJob(android.content.Context context, int i) {
        android.content.ComponentName componentName = new android.content.ComponentName(context.getPackageName(), com.android.server.usage.UsageStatsIdleService.class.getName());
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putInt(USER_ID_KEY, i);
        scheduleJobInternal(context, new android.app.job.JobInfo.Builder(i, componentName).setRequiresDeviceIdle(true).setExtras(persistableBundle).setPersisted(true).build(), PRUNE_JOB_NS, i);
    }

    static void scheduleUpdateMappingsJob(android.content.Context context, int i) {
        android.content.ComponentName componentName = new android.content.ComponentName(context.getPackageName(), com.android.server.usage.UsageStatsIdleService.class.getName());
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putInt(USER_ID_KEY, i);
        scheduleJobInternal(context, new android.app.job.JobInfo.Builder(i, componentName).setPersisted(true).setMinimumLatency(java.util.concurrent.TimeUnit.DAYS.toMillis(1L)).setOverrideDeadline(java.util.concurrent.TimeUnit.DAYS.toMillis(2L)).setExtras(persistableBundle).build(), UPDATE_MAPPINGS_JOB_NS, i);
    }

    private static void scheduleJobInternal(android.content.Context context, android.app.job.JobInfo jobInfo, java.lang.String str, int i) {
        android.app.job.JobScheduler forNamespace = ((android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class)).forNamespace(str);
        if (!jobInfo.equals(forNamespace.getPendingJob(i))) {
            forNamespace.cancel(i);
            forNamespace.schedule(jobInfo);
        }
    }

    static void cancelPruneJob(android.content.Context context, int i) {
        cancelJobInternal(context, PRUNE_JOB_NS, i);
    }

    static void cancelUpdateMappingsJob(android.content.Context context, int i) {
        cancelJobInternal(context, UPDATE_MAPPINGS_JOB_NS, i);
    }

    private static void cancelJobInternal(android.content.Context context, java.lang.String str, int i) {
        android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class);
        if (jobScheduler != null) {
            jobScheduler.forNamespace(str).cancel(i);
        }
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(final android.app.job.JobParameters jobParameters) {
        final int i = jobParameters.getExtras().getInt(USER_ID_KEY, -1);
        if (i == -1) {
            return false;
        }
        android.os.AsyncTask.execute(new java.lang.Runnable() { // from class: com.android.server.usage.UsageStatsIdleService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.usage.UsageStatsIdleService.this.lambda$onStartJob$0(jobParameters, i);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStartJob$0(android.app.job.JobParameters jobParameters, int i) {
        android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
        if (UPDATE_MAPPINGS_JOB_NS.equals(jobParameters.getJobNamespace())) {
            jobFinished(jobParameters, !usageStatsManagerInternal.updatePackageMappingsData(i));
        } else {
            jobFinished(jobParameters, !usageStatsManagerInternal.pruneUninstalledPackagesData(i));
        }
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        return false;
    }
}
