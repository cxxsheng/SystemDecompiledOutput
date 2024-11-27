package com.android.server.notification;

/* loaded from: classes2.dex */
public class NotificationBitmapJobService extends android.app.job.JobService {
    static final int BASE_JOB_ID = 290381858;
    static final java.lang.String TAG = "NotificationBitmapJob";

    static void scheduleJob(android.content.Context context) {
        if (context == null) {
            return;
        }
        try {
            if (((android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class)).forNamespace(TAG).schedule(new android.app.job.JobInfo.Builder(BASE_JOB_ID, new android.content.ComponentName(context, (java.lang.Class<?>) com.android.server.notification.NotificationBitmapJobService.class)).setRequiresDeviceIdle(true).setMinimumLatency(getRunAfterMs()).build()) != 1) {
                android.util.Slog.e(TAG, "Failed to schedule bitmap removal job");
            }
        } catch (java.lang.Throwable th) {
            android.util.Slog.wtf(TAG, "Failed bitmap removal job", th);
        }
    }

    private static long getRunAfterMs() {
        java.time.ZoneId systemDefault = java.time.ZoneId.systemDefault();
        java.time.ZonedDateTime atZone = java.time.Instant.now().atZone(systemDefault);
        java.time.ZonedDateTime of = java.time.ZonedDateTime.of(atZone.toLocalDate(), java.time.LocalTime.of(2, 0), systemDefault);
        return getTimeUntilRemoval(atZone, of, of.plusDays(1L));
    }

    @com.android.internal.annotations.VisibleForTesting
    static long getTimeUntilRemoval(java.time.ZonedDateTime zonedDateTime, java.time.ZonedDateTime zonedDateTime2, java.time.ZonedDateTime zonedDateTime3) {
        if (java.time.Duration.between(zonedDateTime, zonedDateTime2).isNegative()) {
            return java.time.Duration.between(zonedDateTime, zonedDateTime3).toMillis();
        }
        return java.time.Duration.between(zonedDateTime, zonedDateTime2).toMillis();
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(final android.app.job.JobParameters jobParameters) {
        new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationBitmapJobService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.notification.NotificationBitmapJobService.this.lambda$onStartJob$0(jobParameters);
            }
        }).start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStartJob$0(android.app.job.JobParameters jobParameters) {
        ((com.android.server.notification.NotificationManagerInternal) com.android.server.LocalServices.getService(com.android.server.notification.NotificationManagerInternal.class)).removeBitmaps();
        scheduleJob(this);
        jobFinished(jobParameters, false);
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        return false;
    }

    @Override // android.app.Service, android.content.ContextWrapper
    @com.android.internal.annotations.VisibleForTesting
    protected void attachBaseContext(android.content.Context context) {
        super.attachBaseContext(context);
    }
}
