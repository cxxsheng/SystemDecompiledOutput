package com.android.server.backup;

/* loaded from: classes.dex */
public class FullBackupJob extends android.app.job.JobService {

    @com.android.internal.annotations.VisibleForTesting
    public static final int MAX_JOB_ID = 52419896;

    @com.android.internal.annotations.VisibleForTesting
    public static final int MIN_JOB_ID = 52418896;
    private static final java.lang.String USER_ID_EXTRA_KEY = "userId";
    private static android.content.ComponentName sIdleService = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.backup.FullBackupJob.class.getName());

    @com.android.internal.annotations.GuardedBy({"mParamsForUser"})
    private final android.util.SparseArray<android.app.job.JobParameters> mParamsForUser = new android.util.SparseArray<>();

    public static void schedule(int i, android.content.Context context, long j, com.android.server.backup.UserBackupManagerService userBackupManagerService) {
        if (userBackupManagerService.isFrameworkSchedulingEnabled()) {
            android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) context.getSystemService("jobscheduler");
            android.app.job.JobInfo.Builder builder = new android.app.job.JobInfo.Builder(getJobIdForUserId(i), sIdleService);
            com.android.server.backup.BackupManagerConstants constants = userBackupManagerService.getConstants();
            synchronized (constants) {
                builder.setRequiredNetworkType(constants.getFullBackupRequiredNetworkType()).setRequiresCharging(constants.getFullBackupRequireCharging());
            }
            if (j > 0) {
                builder.setMinimumLatency(j);
            }
            if (!context.getPackageManager().hasSystemFeature("android.hardware.type.watch")) {
                builder.setRequiresDeviceIdle(true);
            }
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putInt("userId", i);
            builder.setTransientExtras(bundle);
            jobScheduler.schedule(builder.build());
        }
    }

    public static void cancel(int i, android.content.Context context) {
        ((android.app.job.JobScheduler) context.getSystemService("jobscheduler")).cancel(getJobIdForUserId(i));
    }

    public void finishBackupPass(int i) {
        synchronized (this.mParamsForUser) {
            try {
                android.app.job.JobParameters jobParameters = this.mParamsForUser.get(i);
                if (jobParameters != null) {
                    jobFinished(jobParameters, false);
                    this.mParamsForUser.remove(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        int i = jobParameters.getTransientExtras().getInt("userId");
        synchronized (this.mParamsForUser) {
            this.mParamsForUser.put(i, jobParameters);
        }
        return com.android.server.backup.BackupManagerService.getInstance().beginFullBackup(i, this);
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        int i = jobParameters.getTransientExtras().getInt("userId");
        synchronized (this.mParamsForUser) {
            try {
                if (this.mParamsForUser.removeReturnOld(i) == null) {
                    return false;
                }
                com.android.server.backup.BackupManagerService.getInstance().endFullBackup(i);
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static int getJobIdForUserId(int i) {
        return com.android.server.backup.JobIdManager.getJobIdForUserId(52418896, MAX_JOB_ID, i);
    }
}
