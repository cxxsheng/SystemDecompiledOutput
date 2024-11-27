package com.android.server;

/* loaded from: classes.dex */
public class PruneInstantAppsJobService extends android.app.job.JobService {
    private static final boolean DEBUG = false;
    private static final int JOB_ID = 765123;
    private static final long PRUNE_INSTANT_APPS_PERIOD_MILLIS = java.util.concurrent.TimeUnit.DAYS.toMillis(1);

    public static void schedule(android.content.Context context) {
        ((android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class)).schedule(new android.app.job.JobInfo.Builder(JOB_ID, new android.content.ComponentName(context.getPackageName(), com.android.server.PruneInstantAppsJobService.class.getName())).setRequiresDeviceIdle(true).setPeriodic(PRUNE_INSTANT_APPS_PERIOD_MILLIS).build());
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(final android.app.job.JobParameters jobParameters) {
        android.os.AsyncTask.execute(new java.lang.Runnable() { // from class: com.android.server.PruneInstantAppsJobService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.PruneInstantAppsJobService.this.lambda$onStartJob$0(jobParameters);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStartJob$0(android.app.job.JobParameters jobParameters) {
        ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).pruneInstantApps();
        jobFinished(jobParameters, false);
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        return false;
    }
}
