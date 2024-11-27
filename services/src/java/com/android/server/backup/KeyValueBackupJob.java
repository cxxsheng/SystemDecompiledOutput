package com.android.server.backup;

/* loaded from: classes.dex */
public class KeyValueBackupJob extends android.app.job.JobService {
    private static final long MAX_DEFERRAL = 86400000;

    @com.android.internal.annotations.VisibleForTesting
    public static final int MAX_JOB_ID = 52418896;

    @com.android.internal.annotations.VisibleForTesting
    public static final int MIN_JOB_ID = 52417896;
    private static final java.lang.String TAG = "KeyValueBackupJob";
    private static final java.lang.String USER_ID_EXTRA_KEY = "userId";
    private static android.content.ComponentName sKeyValueJobService = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.backup.KeyValueBackupJob.class.getName());

    @com.android.internal.annotations.GuardedBy({"KeyValueBackupJob.class"})
    private static final android.util.SparseBooleanArray sScheduledForUserId = new android.util.SparseBooleanArray();

    @com.android.internal.annotations.GuardedBy({"KeyValueBackupJob.class"})
    private static final android.util.SparseLongArray sNextScheduledForUserId = new android.util.SparseLongArray();

    public static void schedule(int i, android.content.Context context, com.android.server.backup.UserBackupManagerService userBackupManagerService) {
        schedule(i, context, 0L, userBackupManagerService);
    }

    public static void schedule(int i, android.content.Context context, long j, com.android.server.backup.UserBackupManagerService userBackupManagerService) {
        long keyValueBackupIntervalMilliseconds;
        long keyValueBackupFuzzMilliseconds;
        int keyValueBackupRequiredNetworkType;
        boolean keyValueBackupRequireCharging;
        synchronized (com.android.server.backup.KeyValueBackupJob.class) {
            try {
                if (!sScheduledForUserId.get(i) && userBackupManagerService.isFrameworkSchedulingEnabled()) {
                    com.android.server.backup.BackupManagerConstants constants = userBackupManagerService.getConstants();
                    synchronized (constants) {
                        keyValueBackupIntervalMilliseconds = constants.getKeyValueBackupIntervalMilliseconds();
                        keyValueBackupFuzzMilliseconds = constants.getKeyValueBackupFuzzMilliseconds();
                        keyValueBackupRequiredNetworkType = constants.getKeyValueBackupRequiredNetworkType();
                        keyValueBackupRequireCharging = constants.getKeyValueBackupRequireCharging();
                    }
                    if (j <= 0) {
                        j = new java.util.Random().nextInt((int) keyValueBackupFuzzMilliseconds) + keyValueBackupIntervalMilliseconds;
                    }
                    android.util.Slog.v(TAG, "Scheduling k/v pass in " + ((j / 1000) / 60) + " minutes");
                    android.app.job.JobInfo.Builder overrideDeadline = new android.app.job.JobInfo.Builder(getJobIdForUserId(i), sKeyValueJobService).setMinimumLatency(j).setRequiredNetworkType(keyValueBackupRequiredNetworkType).setRequiresCharging(keyValueBackupRequireCharging).setOverrideDeadline(86400000L);
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putInt("userId", i);
                    overrideDeadline.setTransientExtras(bundle);
                    ((android.app.job.JobScheduler) context.getSystemService("jobscheduler")).schedule(overrideDeadline.build());
                    sScheduledForUserId.put(i, true);
                    sNextScheduledForUserId.put(i, java.lang.System.currentTimeMillis() + j);
                }
            } finally {
            }
        }
    }

    public static void cancel(int i, android.content.Context context) {
        synchronized (com.android.server.backup.KeyValueBackupJob.class) {
            ((android.app.job.JobScheduler) context.getSystemService("jobscheduler")).cancel(getJobIdForUserId(i));
            clearScheduledForUserId(i);
        }
    }

    public static long nextScheduled(int i) {
        long j;
        synchronized (com.android.server.backup.KeyValueBackupJob.class) {
            j = sNextScheduledForUserId.get(i);
        }
        return j;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static boolean isScheduled(int i) {
        boolean z;
        synchronized (com.android.server.backup.KeyValueBackupJob.class) {
            z = sScheduledForUserId.get(i);
        }
        return z;
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        int i = jobParameters.getTransientExtras().getInt("userId");
        synchronized (com.android.server.backup.KeyValueBackupJob.class) {
            clearScheduledForUserId(i);
        }
        try {
            com.android.server.backup.BackupManagerService.getInstance().backupNowForUser(i);
            return false;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"KeyValueBackupJob.class"})
    private static void clearScheduledForUserId(int i) {
        sScheduledForUserId.delete(i);
        sNextScheduledForUserId.delete(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    static int getJobIdForUserId(int i) {
        return com.android.server.backup.JobIdManager.getJobIdForUserId(MIN_JOB_ID, 52418896, i);
    }
}
