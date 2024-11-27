package com.android.server.storage;

/* loaded from: classes2.dex */
public class DiskStatsLoggingService extends android.app.job.JobService {
    public static final java.lang.String DUMPSYS_CACHE_PATH = "/data/system/diskstats_cache.json";
    private static final int JOB_DISKSTATS_LOGGING = 1145656139;
    private static final java.lang.String TAG = "DiskStatsLogService";
    private static android.content.ComponentName sDiskStatsLoggingService = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.storage.DiskStatsLoggingService.class.getName());

    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        if (!isCharging(this) || !isDumpsysTaskEnabled(getContentResolver())) {
            jobFinished(jobParameters, true);
            return false;
        }
        android.os.storage.VolumeInfo primaryStorageCurrentVolume = getPackageManager().getPrimaryStorageCurrentVolume();
        if (primaryStorageCurrentVolume == null) {
            return false;
        }
        com.android.server.storage.AppCollector appCollector = new com.android.server.storage.AppCollector(this, primaryStorageCurrentVolume);
        android.os.Environment.UserEnvironment userEnvironment = new android.os.Environment.UserEnvironment(android.os.UserHandle.myUserId());
        com.android.server.storage.DiskStatsLoggingService.LogRunnable logRunnable = new com.android.server.storage.DiskStatsLoggingService.LogRunnable();
        logRunnable.setDownloadsDirectory(userEnvironment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS));
        logRunnable.setSystemSize(com.android.server.storage.FileCollector.getSystemSize(this));
        logRunnable.setLogOutputFile(new java.io.File(DUMPSYS_CACHE_PATH));
        logRunnable.setAppCollector(appCollector);
        logRunnable.setJobService(this, jobParameters);
        logRunnable.setContext(this);
        android.os.AsyncTask.execute(logRunnable);
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        return false;
    }

    public static void schedule(android.content.Context context) {
        ((android.app.job.JobScheduler) context.getSystemService("jobscheduler")).schedule(new android.app.job.JobInfo.Builder(JOB_DISKSTATS_LOGGING, sDiskStatsLoggingService).setRequiresDeviceIdle(true).setRequiresCharging(true).setPeriodic(java.util.concurrent.TimeUnit.DAYS.toMillis(1L)).build());
    }

    private static boolean isCharging(android.content.Context context) {
        android.os.BatteryManager batteryManager = (android.os.BatteryManager) context.getSystemService("batterymanager");
        if (batteryManager != null) {
            return batteryManager.isCharging();
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isDumpsysTaskEnabled(android.content.ContentResolver contentResolver) {
        return android.provider.Settings.Global.getInt(contentResolver, "enable_diskstats_logging", 1) != 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    static class LogRunnable implements java.lang.Runnable {
        private static final long TIMEOUT_MILLIS = java.util.concurrent.TimeUnit.MINUTES.toMillis(10);
        private com.android.server.storage.AppCollector mCollector;
        private android.content.Context mContext;
        private java.io.File mDownloadsDirectory;
        private android.app.job.JobService mJobService;
        private java.io.File mOutputFile;
        private android.app.job.JobParameters mParams;
        private long mSystemSize;

        LogRunnable() {
        }

        public void setDownloadsDirectory(java.io.File file) {
            this.mDownloadsDirectory = file;
        }

        public void setAppCollector(com.android.server.storage.AppCollector appCollector) {
            this.mCollector = appCollector;
        }

        public void setLogOutputFile(java.io.File file) {
            this.mOutputFile = file;
        }

        public void setSystemSize(long j) {
            this.mSystemSize = j;
        }

        public void setContext(android.content.Context context) {
            this.mContext = context;
        }

        public void setJobService(android.app.job.JobService jobService, android.app.job.JobParameters jobParameters) {
            this.mJobService = jobService;
            this.mParams = jobParameters;
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z = true;
            try {
                com.android.server.storage.FileCollector.MeasurementResult measurementResult = com.android.server.storage.FileCollector.getMeasurementResult(this.mContext);
                com.android.server.storage.FileCollector.MeasurementResult measurementResult2 = com.android.server.storage.FileCollector.getMeasurementResult(this.mDownloadsDirectory);
                java.util.List<android.content.pm.PackageStats> packageStats = this.mCollector.getPackageStats(TIMEOUT_MILLIS);
                if (packageStats == null) {
                    android.util.Log.w(com.android.server.storage.DiskStatsLoggingService.TAG, "Timed out while fetching package stats.");
                } else {
                    logToFile(measurementResult, measurementResult2, packageStats, this.mSystemSize);
                    z = false;
                }
                finishJob(z);
            } catch (java.lang.IllegalStateException e) {
                android.util.Log.e(com.android.server.storage.DiskStatsLoggingService.TAG, "Error while measuring storage", e);
                finishJob(true);
            }
        }

        private void logToFile(com.android.server.storage.FileCollector.MeasurementResult measurementResult, com.android.server.storage.FileCollector.MeasurementResult measurementResult2, java.util.List<android.content.pm.PackageStats> list, long j) {
            com.android.server.storage.DiskStatsFileLogger diskStatsFileLogger = new com.android.server.storage.DiskStatsFileLogger(measurementResult, measurementResult2, list, j);
            try {
                this.mOutputFile.createNewFile();
                diskStatsFileLogger.dumpToFile(this.mOutputFile);
            } catch (java.io.IOException e) {
                android.util.Log.e(com.android.server.storage.DiskStatsLoggingService.TAG, "Exception while writing opportunistic disk file cache.", e);
            }
        }

        private void finishJob(boolean z) {
            if (this.mJobService != null) {
                this.mJobService.jobFinished(this.mParams, z);
            }
        }
    }
}
