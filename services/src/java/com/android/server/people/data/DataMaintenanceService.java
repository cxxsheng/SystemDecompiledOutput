package com.android.server.people.data;

/* loaded from: classes2.dex */
public class DataMaintenanceService extends android.app.job.JobService {
    private static final int BASE_JOB_ID = 204561367;
    private static final long JOB_RUN_INTERVAL = java.util.concurrent.TimeUnit.HOURS.toMillis(24);
    private android.os.CancellationSignal mSignal;

    static void scheduleJob(android.content.Context context, int i) {
        int jobId = getJobId(i);
        android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class);
        if (jobScheduler.getPendingJob(jobId) == null) {
            jobScheduler.schedule(new android.app.job.JobInfo.Builder(jobId, new android.content.ComponentName(context, (java.lang.Class<?>) com.android.server.people.data.DataMaintenanceService.class)).setRequiresDeviceIdle(true).setPeriodic(JOB_RUN_INTERVAL).build());
        }
    }

    static void cancelJob(android.content.Context context, int i) {
        ((android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class)).cancel(getJobId(i));
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(final android.app.job.JobParameters jobParameters) {
        final int userId = getUserId(jobParameters.getJobId());
        this.mSignal = new android.os.CancellationSignal();
        new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.people.data.DataMaintenanceService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.people.data.DataMaintenanceService.this.lambda$onStartJob$0(userId, jobParameters);
            }
        }).start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStartJob$0(int i, android.app.job.JobParameters jobParameters) {
        ((com.android.server.people.PeopleServiceInternal) com.android.server.LocalServices.getService(com.android.server.people.PeopleServiceInternal.class)).pruneDataForUser(i, this.mSignal);
        jobFinished(jobParameters, this.mSignal.isCanceled());
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        if (this.mSignal != null) {
            this.mSignal.cancel();
            return false;
        }
        return false;
    }

    private static int getJobId(int i) {
        return i + BASE_JOB_ID;
    }

    private static int getUserId(int i) {
        return i - BASE_JOB_ID;
    }
}
