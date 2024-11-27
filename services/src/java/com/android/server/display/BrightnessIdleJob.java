package com.android.server.display;

/* loaded from: classes.dex */
public class BrightnessIdleJob extends android.app.job.JobService {
    private static final int JOB_ID = 3923512;

    public static void scheduleJob(android.content.Context context) {
        android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class);
        android.app.job.JobInfo pendingJob = jobScheduler.getPendingJob(JOB_ID);
        android.app.job.JobInfo build = new android.app.job.JobInfo.Builder(JOB_ID, new android.content.ComponentName(context, (java.lang.Class<?>) com.android.server.display.BrightnessIdleJob.class)).setRequiresDeviceIdle(true).setRequiresCharging(true).setPeriodic(java.util.concurrent.TimeUnit.HOURS.toMillis(24L)).build();
        if (pendingJob != null && !pendingJob.equals(build)) {
            jobScheduler.cancel(JOB_ID);
            pendingJob = null;
        }
        if (pendingJob == null) {
            jobScheduler.schedule(build);
        }
    }

    public static void cancelJob(android.content.Context context) {
        ((android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class)).cancel(JOB_ID);
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        if (com.android.server.display.BrightnessTracker.DEBUG) {
            android.util.Slog.d("BrightnessTracker", "Scheduled write of brightness events");
        }
        ((android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class)).persistBrightnessTrackerState();
        return false;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        return false;
    }
}
