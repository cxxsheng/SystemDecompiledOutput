package com.android.server;

/* loaded from: classes.dex */
public class PreloadsFileCacheExpirationJobService extends android.app.job.JobService {
    private static final boolean DEBUG = false;
    private static final int JOB_ID = 100500;
    private static final java.lang.String PERSIST_SYS_PRELOADS_FILE_CACHE_EXPIRED = "persist.sys.preloads.file_cache_expired";
    private static final java.lang.String TAG = "PreloadsFileCacheExpirationJobService";

    public static void schedule(android.content.Context context) {
        ((android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class)).schedule(new android.app.job.JobInfo.Builder(JOB_ID, new android.content.ComponentName(context, (java.lang.Class<?>) com.android.server.PreloadsFileCacheExpirationJobService.class)).setPersisted(true).setMinimumLatency(java.util.concurrent.TimeUnit.DAYS.toMillis(android.content.res.Resources.getSystem().getInteger(android.R.integer.config_jobSchedulerIdleWindowSlop))).setOverrideDeadline(java.util.concurrent.TimeUnit.DAYS.toMillis(r0 + 1)).build());
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        android.os.SystemProperties.set(PERSIST_SYS_PRELOADS_FILE_CACHE_EXPIRED, "1");
        android.util.Slog.i(TAG, "Set persist.sys.preloads.file_cache_expired=1");
        return false;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        return false;
    }
}
