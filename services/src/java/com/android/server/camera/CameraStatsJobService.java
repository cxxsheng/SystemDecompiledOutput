package com.android.server.camera;

/* loaded from: classes.dex */
public class CameraStatsJobService extends android.app.job.JobService {
    private static final int CAMERA_REPORTING_JOB_ID = 13254266;
    private static final java.lang.String TAG = "CameraStatsJobService";
    private static android.content.ComponentName sCameraStatsJobServiceName = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.camera.CameraStatsJobService.class.getName());

    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        com.android.server.camera.CameraServiceProxy cameraServiceProxy = (com.android.server.camera.CameraServiceProxy) com.android.server.LocalServices.getService(com.android.server.camera.CameraServiceProxy.class);
        if (cameraServiceProxy == null) {
            android.util.Slog.w(TAG, "Can't collect camera usage stats - no camera service proxy found");
            return false;
        }
        cameraServiceProxy.dumpUsageEvents();
        return false;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        return false;
    }

    public static void schedule(android.content.Context context) {
        android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) context.getSystemService("jobscheduler");
        if (jobScheduler == null) {
            android.util.Slog.e(TAG, "Can't collect camera usage stats - no Job Scheduler");
        } else {
            jobScheduler.schedule(new android.app.job.JobInfo.Builder(CAMERA_REPORTING_JOB_ID, sCameraStatsJobServiceName).setMinimumLatency(java.util.concurrent.TimeUnit.DAYS.toMillis(1L)).setRequiresDeviceIdle(true).build());
        }
    }
}
