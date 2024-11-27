package com.android.server;

/* loaded from: classes.dex */
public class MountServiceIdler extends android.app.job.JobService {
    private static final java.lang.String TAG = "MountServiceIdler";
    private java.lang.Runnable mFinishCallback = new java.lang.Runnable() { // from class: com.android.server.MountServiceIdler.1
        @Override // java.lang.Runnable
        public void run() {
            android.util.Slog.i(com.android.server.MountServiceIdler.TAG, "Got mount service completion callback");
            synchronized (com.android.server.MountServiceIdler.this.mFinishCallback) {
                try {
                    if (com.android.server.MountServiceIdler.this.mStarted) {
                        com.android.server.MountServiceIdler.this.jobFinished(com.android.server.MountServiceIdler.this.mJobParams, false);
                        com.android.server.MountServiceIdler.this.mStarted = false;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.MountServiceIdler.scheduleIdlePass(com.android.server.MountServiceIdler.this);
        }
    };
    private android.app.job.JobParameters mJobParams;
    private boolean mStarted;
    private static android.content.ComponentName sIdleService = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.MountServiceIdler.class.getName());
    private static int MOUNT_JOB_ID = 808;

    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        try {
            android.app.ActivityManager.getService().performIdleMaintenance();
        } catch (android.os.RemoteException e) {
        }
        this.mJobParams = jobParameters;
        com.android.server.StorageManagerService storageManagerService = com.android.server.StorageManagerService.sSelf;
        if (storageManagerService != null) {
            synchronized (this.mFinishCallback) {
                this.mStarted = true;
            }
            storageManagerService.runIdleMaint(this.mFinishCallback);
        }
        return storageManagerService != null;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        com.android.server.StorageManagerService storageManagerService = com.android.server.StorageManagerService.sSelf;
        if (storageManagerService != null) {
            storageManagerService.abortIdleMaint(this.mFinishCallback);
            synchronized (this.mFinishCallback) {
                this.mStarted = false;
            }
        }
        return false;
    }

    public static void scheduleIdlePass(android.content.Context context) {
        long currentTimeMillis;
        android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) context.getSystemService("jobscheduler");
        long timeInMillis = offsetFromTodayMidnight(0, 3).getTimeInMillis();
        long timeInMillis2 = offsetFromTodayMidnight(0, 4).getTimeInMillis();
        long timeInMillis3 = offsetFromTodayMidnight(1, 3).getTimeInMillis();
        if (java.lang.System.currentTimeMillis() > timeInMillis && java.lang.System.currentTimeMillis() < timeInMillis2) {
            currentTimeMillis = java.util.concurrent.TimeUnit.SECONDS.toMillis(10L);
        } else {
            currentTimeMillis = timeInMillis3 - java.lang.System.currentTimeMillis();
        }
        android.app.job.JobInfo.Builder builder = new android.app.job.JobInfo.Builder(MOUNT_JOB_ID, sIdleService);
        builder.setRequiresDeviceIdle(true);
        builder.setRequiresBatteryNotLow(true);
        builder.setRequiresCharging(true);
        builder.setMinimumLatency(currentTimeMillis);
        jobScheduler.schedule(builder.build());
    }

    private static java.util.Calendar offsetFromTodayMidnight(int i, int i2) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(java.lang.System.currentTimeMillis());
        calendar.set(11, i2);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(5, i);
        return calendar;
    }
}
