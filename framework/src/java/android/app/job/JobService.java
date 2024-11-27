package android.app.job;

/* loaded from: classes.dex */
public abstract class JobService extends android.app.Service {
    public static final int JOB_END_NOTIFICATION_POLICY_DETACH = 0;
    public static final int JOB_END_NOTIFICATION_POLICY_REMOVE = 1;
    public static final java.lang.String PERMISSION_BIND = "android.permission.BIND_JOB_SERVICE";
    private static final java.lang.String TAG = "JobService";
    private android.app.job.JobServiceEngine mEngine;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface JobEndNotificationPolicy {
    }

    public abstract boolean onStartJob(android.app.job.JobParameters jobParameters);

    public abstract boolean onStopJob(android.app.job.JobParameters jobParameters);

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (this.mEngine == null) {
            this.mEngine = new android.app.job.JobServiceEngine(this) { // from class: android.app.job.JobService.1
                @Override // android.app.job.JobServiceEngine
                public boolean onStartJob(android.app.job.JobParameters jobParameters) {
                    return android.app.job.JobService.this.onStartJob(jobParameters);
                }

                @Override // android.app.job.JobServiceEngine
                public boolean onStopJob(android.app.job.JobParameters jobParameters) {
                    return android.app.job.JobService.this.onStopJob(jobParameters);
                }

                @Override // android.app.job.JobServiceEngine
                public long getTransferredDownloadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) {
                    if (jobWorkItem == null) {
                        return android.app.job.JobService.this.getTransferredDownloadBytes(jobParameters);
                    }
                    return android.app.job.JobService.this.getTransferredDownloadBytes(jobParameters, jobWorkItem);
                }

                @Override // android.app.job.JobServiceEngine
                public long getTransferredUploadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) {
                    if (jobWorkItem == null) {
                        return android.app.job.JobService.this.getTransferredUploadBytes(jobParameters);
                    }
                    return android.app.job.JobService.this.getTransferredUploadBytes(jobParameters, jobWorkItem);
                }

                @Override // android.app.job.JobServiceEngine
                public void onNetworkChanged(android.app.job.JobParameters jobParameters) {
                    android.app.job.JobService.this.onNetworkChanged(jobParameters);
                }
            };
        }
        return this.mEngine.getBinder();
    }

    public final void jobFinished(android.app.job.JobParameters jobParameters, boolean z) {
        this.mEngine.jobFinished(jobParameters, z);
    }

    public void onNetworkChanged(android.app.job.JobParameters jobParameters) {
        android.util.Log.w(TAG, "onNetworkChanged() not implemented in " + getClass().getName() + ". Must override in a subclass.");
    }

    public final void updateEstimatedNetworkBytes(android.app.job.JobParameters jobParameters, long j, long j2) {
        this.mEngine.updateEstimatedNetworkBytes(jobParameters, null, j, j2);
    }

    public final void updateEstimatedNetworkBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem, long j, long j2) {
        this.mEngine.updateEstimatedNetworkBytes(jobParameters, jobWorkItem, j, j2);
    }

    public final void updateTransferredNetworkBytes(android.app.job.JobParameters jobParameters, long j, long j2) {
        this.mEngine.updateTransferredNetworkBytes(jobParameters, null, j, j2);
    }

    public final void updateTransferredNetworkBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem, long j, long j2) {
        this.mEngine.updateTransferredNetworkBytes(jobParameters, jobWorkItem, j, j2);
    }

    public long getTransferredDownloadBytes(android.app.job.JobParameters jobParameters) {
        if (android.compat.Compatibility.isChangeEnabled(android.app.job.JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public long getTransferredUploadBytes(android.app.job.JobParameters jobParameters) {
        if (android.compat.Compatibility.isChangeEnabled(android.app.job.JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public long getTransferredDownloadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) {
        if (jobWorkItem == null) {
            return getTransferredDownloadBytes(jobParameters);
        }
        if (android.compat.Compatibility.isChangeEnabled(android.app.job.JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public long getTransferredUploadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) {
        if (jobWorkItem == null) {
            return getTransferredUploadBytes(jobParameters);
        }
        if (android.compat.Compatibility.isChangeEnabled(android.app.job.JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public final void setNotification(android.app.job.JobParameters jobParameters, int i, android.app.Notification notification, int i2) {
        this.mEngine.setNotification(jobParameters, i, notification, i2);
    }
}
