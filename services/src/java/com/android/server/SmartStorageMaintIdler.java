package com.android.server;

/* loaded from: classes.dex */
public class SmartStorageMaintIdler extends android.app.job.JobService {
    private static final int SMART_MAINT_JOB_ID = 2808;
    private static final android.content.ComponentName SMART_STORAGE_MAINT_SERVICE = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.SmartStorageMaintIdler.class.getName());
    private static final java.lang.String TAG = "SmartStorageMaintIdler";
    private android.app.job.JobParameters mJobParams;
    private final java.util.concurrent.atomic.AtomicBoolean mStarted = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final java.lang.Runnable mFinishCallback = new java.lang.Runnable() { // from class: com.android.server.SmartStorageMaintIdler.1
        @Override // java.lang.Runnable
        public void run() {
            android.util.Slog.i(com.android.server.SmartStorageMaintIdler.TAG, "Got smart storage maintenance service completion callback");
            if (com.android.server.SmartStorageMaintIdler.this.mStarted.get()) {
                com.android.server.SmartStorageMaintIdler.this.jobFinished(com.android.server.SmartStorageMaintIdler.this.mJobParams, false);
                com.android.server.SmartStorageMaintIdler.this.mStarted.set(false);
            }
            com.android.server.SmartStorageMaintIdler.scheduleSmartIdlePass(com.android.server.SmartStorageMaintIdler.this, com.android.server.StorageManagerService.sSmartIdleMaintPeriod);
        }
    };

    @Override // android.app.job.JobService
    public boolean onStartJob(final android.app.job.JobParameters jobParameters) {
        final com.android.server.StorageManagerService storageManagerService = com.android.server.StorageManagerService.sSelf;
        if (!this.mStarted.compareAndSet(false, true)) {
            return false;
        }
        new java.lang.Thread() { // from class: com.android.server.SmartStorageMaintIdler.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                com.android.server.SmartStorageMaintIdler.this.mJobParams = jobParameters;
                if (storageManagerService != null) {
                    storageManagerService.runSmartIdleMaint(com.android.server.SmartStorageMaintIdler.this.mFinishCallback);
                } else {
                    com.android.server.SmartStorageMaintIdler.this.mStarted.set(false);
                }
            }
        }.start();
        return storageManagerService != null;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        this.mStarted.set(false);
        return false;
    }

    public static void scheduleSmartIdlePass(android.content.Context context, int i) {
        com.android.server.StorageManagerService storageManagerService = com.android.server.StorageManagerService.sSelf;
        if (storageManagerService == null || storageManagerService.isPassedLifetimeThresh()) {
            return;
        }
        android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class);
        long millis = java.util.concurrent.TimeUnit.MINUTES.toMillis(i);
        android.app.job.JobInfo.Builder builder = new android.app.job.JobInfo.Builder(2808, SMART_STORAGE_MAINT_SERVICE);
        builder.setMinimumLatency(millis);
        jobScheduler.schedule(builder.build());
    }
}
