package com.android.server.notification;

/* loaded from: classes2.dex */
public class ReviewNotificationPermissionsJobService extends android.app.job.JobService {

    @com.android.internal.annotations.VisibleForTesting
    protected static final int JOB_ID = 225373531;
    public static final java.lang.String TAG = "ReviewNotificationPermissionsJobService";

    public static void scheduleJob(android.content.Context context, long j) {
        ((android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class)).schedule(new android.app.job.JobInfo.Builder(JOB_ID, new android.content.ComponentName(context, (java.lang.Class<?>) com.android.server.notification.ReviewNotificationPermissionsJobService.class)).setPersisted(true).setMinimumLatency(j).build());
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        ((com.android.server.notification.NotificationManagerInternal) com.android.server.LocalServices.getService(com.android.server.notification.NotificationManagerInternal.class)).sendReviewPermissionsNotification();
        return false;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        return true;
    }
}
