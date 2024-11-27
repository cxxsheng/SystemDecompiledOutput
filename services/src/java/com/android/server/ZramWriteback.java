package com.android.server;

/* loaded from: classes.dex */
public final class ZramWriteback extends android.app.job.JobService {
    private static final java.lang.String BDEV_SYS = "/sys/block/zram%d/backing_dev";
    private static final boolean DEBUG = false;
    private static final java.lang.String FIRST_WB_DELAY_PROP = "ro.zram.first_wb_delay_mins";
    private static final java.lang.String FORCE_WRITEBACK_PROP = "zram.force_writeback";
    private static final java.lang.String IDLE_SYS = "/sys/block/zram%d/idle";
    private static final java.lang.String IDLE_SYS_ALL_PAGES = "all";
    private static final java.lang.String MARK_IDLE_DELAY_PROP = "ro.zram.mark_idle_delay_mins";
    private static final int MARK_IDLE_JOB_ID = 811;
    private static final int MAX_ZRAM_DEVICES = 256;
    private static final java.lang.String PERIODIC_WB_DELAY_PROP = "ro.zram.periodic_wb_delay_hours";
    private static final java.lang.String TAG = "ZramWriteback";
    private static final int WB_STATS_MAX_FILE_SIZE = 128;
    private static final java.lang.String WB_STATS_SYS = "/sys/block/zram%d/bd_stat";
    private static final java.lang.String WB_SYS = "/sys/block/zram%d/writeback";
    private static final java.lang.String WB_SYS_IDLE_PAGES = "idle";
    private static final int WRITEBACK_IDLE_JOB_ID = 812;
    private static final android.content.ComponentName sZramWriteback = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.ZramWriteback.class.getName());
    private static int sZramDeviceId = 0;

    private void markPagesAsIdle() {
        java.lang.String format = java.lang.String.format(IDLE_SYS, java.lang.Integer.valueOf(sZramDeviceId));
        try {
            android.os.FileUtils.stringToFile(new java.io.File(format), IDLE_SYS_ALL_PAGES);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write to " + format);
        }
    }

    private void flushIdlePages() {
        java.lang.String format = java.lang.String.format(WB_SYS, java.lang.Integer.valueOf(sZramDeviceId));
        try {
            android.os.FileUtils.stringToFile(new java.io.File(format), WB_SYS_IDLE_PAGES);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write to " + format);
        }
    }

    private int getWrittenPageCount() {
        java.lang.String format = java.lang.String.format(WB_STATS_SYS, java.lang.Integer.valueOf(sZramDeviceId));
        try {
            return java.lang.Integer.parseInt(android.os.FileUtils.readTextFile(new java.io.File(format), 128, "").trim().split("\\s+")[2], 10);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to read writeback stats from " + format);
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void markAndFlushPages() {
        int writtenPageCount = getWrittenPageCount();
        flushIdlePages();
        markPagesAsIdle();
        if (writtenPageCount != -1) {
            android.util.Slog.i(TAG, "Total pages written to disk is " + (getWrittenPageCount() - writtenPageCount));
        }
    }

    private static boolean isWritebackEnabled() {
        try {
            if ("none".equals(android.os.FileUtils.readTextFile(new java.io.File(java.lang.String.format(BDEV_SYS, java.lang.Integer.valueOf(sZramDeviceId))), 128, "").trim())) {
                android.util.Slog.w(TAG, "Writeback device is not set");
                return false;
            }
            return true;
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Writeback is not enabled on zram");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void schedNextWriteback(android.content.Context context) {
        ((android.app.job.JobScheduler) context.getSystemService("jobscheduler")).schedule(new android.app.job.JobInfo.Builder(WRITEBACK_IDLE_JOB_ID, sZramWriteback).setMinimumLatency(java.util.concurrent.TimeUnit.HOURS.toMillis(android.os.SystemProperties.getInt(PERIODIC_WB_DELAY_PROP, 24))).setRequiresDeviceIdle(!android.os.SystemProperties.getBoolean(FORCE_WRITEBACK_PROP, false)).build());
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(final android.app.job.JobParameters jobParameters) {
        if (!isWritebackEnabled()) {
            jobFinished(jobParameters, false);
            return false;
        }
        if (jobParameters.getJobId() == MARK_IDLE_JOB_ID) {
            markPagesAsIdle();
            jobFinished(jobParameters, false);
            return false;
        }
        new java.lang.Thread("ZramWriteback_WritebackIdlePages") { // from class: com.android.server.ZramWriteback.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                com.android.server.ZramWriteback.this.markAndFlushPages();
                com.android.server.ZramWriteback.schedNextWriteback(com.android.server.ZramWriteback.this);
                com.android.server.ZramWriteback.this.jobFinished(jobParameters, false);
            }
        }.start();
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        return false;
    }

    public static void scheduleZramWriteback(android.content.Context context) {
        int i = android.os.SystemProperties.getInt(MARK_IDLE_DELAY_PROP, 20);
        int i2 = android.os.SystemProperties.getInt(FIRST_WB_DELAY_PROP, 180);
        boolean z = android.os.SystemProperties.getBoolean(FORCE_WRITEBACK_PROP, false);
        android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) context.getSystemService("jobscheduler");
        jobScheduler.schedule(new android.app.job.JobInfo.Builder(MARK_IDLE_JOB_ID, sZramWriteback).setMinimumLatency(java.util.concurrent.TimeUnit.MINUTES.toMillis(i)).build());
        jobScheduler.schedule(new android.app.job.JobInfo.Builder(WRITEBACK_IDLE_JOB_ID, sZramWriteback).setMinimumLatency(java.util.concurrent.TimeUnit.MINUTES.toMillis(i2)).setRequiresDeviceIdle(!z).build());
    }
}
