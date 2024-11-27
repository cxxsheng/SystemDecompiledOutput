package android.app.job;

/* loaded from: classes.dex */
public abstract class JobServiceEngine {
    private static final int MSG_EXECUTE_JOB = 0;
    private static final int MSG_GET_TRANSFERRED_DOWNLOAD_BYTES = 3;
    private static final int MSG_GET_TRANSFERRED_UPLOAD_BYTES = 4;
    private static final int MSG_INFORM_OF_NETWORK_CHANGE = 8;
    private static final int MSG_JOB_FINISHED = 2;
    private static final int MSG_SET_NOTIFICATION = 7;
    private static final int MSG_STOP_JOB = 1;
    private static final int MSG_UPDATE_ESTIMATED_NETWORK_BYTES = 6;
    private static final int MSG_UPDATE_TRANSFERRED_NETWORK_BYTES = 5;
    private static final java.lang.String TAG = "JobServiceEngine";
    private final android.app.job.IJobService mBinder = new android.app.job.JobServiceEngine.JobInterface(this);
    android.app.job.JobServiceEngine.JobHandler mHandler;

    public abstract boolean onStartJob(android.app.job.JobParameters jobParameters);

    public abstract boolean onStopJob(android.app.job.JobParameters jobParameters);

    static final class JobInterface extends android.app.job.IJobService.Stub {
        final java.lang.ref.WeakReference<android.app.job.JobServiceEngine> mService;

        JobInterface(android.app.job.JobServiceEngine jobServiceEngine) {
            this.mService = new java.lang.ref.WeakReference<>(jobServiceEngine);
        }

        @Override // android.app.job.IJobService
        public void getTransferredDownloadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException {
            android.app.job.JobServiceEngine jobServiceEngine = this.mService.get();
            if (jobServiceEngine != null) {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = jobParameters;
                obtain.arg2 = jobWorkItem;
                jobServiceEngine.mHandler.obtainMessage(3, obtain).sendToTarget();
            }
        }

        @Override // android.app.job.IJobService
        public void getTransferredUploadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException {
            android.app.job.JobServiceEngine jobServiceEngine = this.mService.get();
            if (jobServiceEngine != null) {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = jobParameters;
                obtain.arg2 = jobWorkItem;
                jobServiceEngine.mHandler.obtainMessage(4, obtain).sendToTarget();
            }
        }

        @Override // android.app.job.IJobService
        public void startJob(android.app.job.JobParameters jobParameters) throws android.os.RemoteException {
            android.app.job.JobServiceEngine jobServiceEngine = this.mService.get();
            if (jobServiceEngine != null) {
                android.os.Message.obtain(jobServiceEngine.mHandler, 0, jobParameters).sendToTarget();
            }
        }

        @Override // android.app.job.IJobService
        public void onNetworkChanged(android.app.job.JobParameters jobParameters) throws android.os.RemoteException {
            android.app.job.JobServiceEngine jobServiceEngine = this.mService.get();
            if (jobServiceEngine != null) {
                jobServiceEngine.mHandler.removeMessages(8);
                jobServiceEngine.mHandler.obtainMessage(8, jobParameters).sendToTarget();
            }
        }

        @Override // android.app.job.IJobService
        public void stopJob(android.app.job.JobParameters jobParameters) throws android.os.RemoteException {
            android.app.job.JobServiceEngine jobServiceEngine = this.mService.get();
            if (jobServiceEngine != null) {
                android.os.Message.obtain(jobServiceEngine.mHandler, 1, jobParameters).sendToTarget();
            }
        }
    }

    class JobHandler extends android.os.Handler {
        JobHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    android.app.job.JobParameters jobParameters = (android.app.job.JobParameters) message.obj;
                    try {
                        ackStartMessage(jobParameters, android.app.job.JobServiceEngine.this.onStartJob(jobParameters));
                        return;
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(android.app.job.JobServiceEngine.TAG, "Error while executing job: " + jobParameters.getJobId());
                        throw new java.lang.RuntimeException(e);
                    }
                case 1:
                    android.app.job.JobParameters jobParameters2 = (android.app.job.JobParameters) message.obj;
                    try {
                        ackStopMessage(jobParameters2, android.app.job.JobServiceEngine.this.onStopJob(jobParameters2));
                        return;
                    } catch (java.lang.Exception e2) {
                        android.util.Log.e(android.app.job.JobServiceEngine.TAG, "Application unable to handle onStopJob.", e2);
                        throw new java.lang.RuntimeException(e2);
                    }
                case 2:
                    android.app.job.JobParameters jobParameters3 = (android.app.job.JobParameters) message.obj;
                    boolean z = message.arg2 == 1;
                    android.app.job.IJobCallback callback = jobParameters3.getCallback();
                    if (callback != null) {
                        try {
                            callback.jobFinished(jobParameters3.getJobId(), z);
                            return;
                        } catch (android.os.RemoteException e3) {
                            android.util.Log.e(android.app.job.JobServiceEngine.TAG, "Error reporting job finish to system: binder has goneaway.");
                            return;
                        }
                    }
                    android.util.Log.e(android.app.job.JobServiceEngine.TAG, "finishJob() called for a nonexistent job id.");
                    return;
                case 3:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.app.job.JobParameters jobParameters4 = (android.app.job.JobParameters) someArgs.arg1;
                    android.app.job.JobWorkItem jobWorkItem = (android.app.job.JobWorkItem) someArgs.arg2;
                    try {
                        ackGetTransferredDownloadBytesMessage(jobParameters4, jobWorkItem, android.app.job.JobServiceEngine.this.getTransferredDownloadBytes(jobParameters4, jobWorkItem));
                        someArgs.recycle();
                        return;
                    } catch (java.lang.Exception e4) {
                        android.util.Log.e(android.app.job.JobServiceEngine.TAG, "Application unable to handle getTransferredDownloadBytes.", e4);
                        throw new java.lang.RuntimeException(e4);
                    }
                case 4:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    android.app.job.JobParameters jobParameters5 = (android.app.job.JobParameters) someArgs2.arg1;
                    android.app.job.JobWorkItem jobWorkItem2 = (android.app.job.JobWorkItem) someArgs2.arg2;
                    try {
                        ackGetTransferredUploadBytesMessage(jobParameters5, jobWorkItem2, android.app.job.JobServiceEngine.this.getTransferredUploadBytes(jobParameters5, jobWorkItem2));
                        someArgs2.recycle();
                        return;
                    } catch (java.lang.Exception e5) {
                        android.util.Log.e(android.app.job.JobServiceEngine.TAG, "Application unable to handle getTransferredUploadBytes.", e5);
                        throw new java.lang.RuntimeException(e5);
                    }
                case 5:
                    com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                    android.app.job.JobParameters jobParameters6 = (android.app.job.JobParameters) someArgs3.arg1;
                    android.app.job.IJobCallback callback2 = jobParameters6.getCallback();
                    if (callback2 != null) {
                        try {
                            callback2.updateTransferredNetworkBytes(jobParameters6.getJobId(), (android.app.job.JobWorkItem) someArgs3.arg2, someArgs3.argl1, someArgs3.argl2);
                        } catch (android.os.RemoteException e6) {
                            android.util.Log.e(android.app.job.JobServiceEngine.TAG, "Error updating data transfer progress to system: binder has gone away.");
                        }
                    } else {
                        android.util.Log.e(android.app.job.JobServiceEngine.TAG, "updateDataTransferProgress() called for a nonexistent job id.");
                    }
                    someArgs3.recycle();
                    return;
                case 6:
                    com.android.internal.os.SomeArgs someArgs4 = (com.android.internal.os.SomeArgs) message.obj;
                    android.app.job.JobParameters jobParameters7 = (android.app.job.JobParameters) someArgs4.arg1;
                    android.app.job.IJobCallback callback3 = jobParameters7.getCallback();
                    if (callback3 != null) {
                        try {
                            callback3.updateEstimatedNetworkBytes(jobParameters7.getJobId(), (android.app.job.JobWorkItem) someArgs4.arg2, someArgs4.argl1, someArgs4.argl2);
                        } catch (android.os.RemoteException e7) {
                            android.util.Log.e(android.app.job.JobServiceEngine.TAG, "Error updating estimated transfer size to system: binder has gone away.");
                        }
                    } else {
                        android.util.Log.e(android.app.job.JobServiceEngine.TAG, "updateEstimatedNetworkBytes() called for a nonexistent job id.");
                    }
                    someArgs4.recycle();
                    return;
                case 7:
                    com.android.internal.os.SomeArgs someArgs5 = (com.android.internal.os.SomeArgs) message.obj;
                    android.app.job.JobParameters jobParameters8 = (android.app.job.JobParameters) someArgs5.arg1;
                    android.app.Notification notification = (android.app.Notification) someArgs5.arg2;
                    android.app.job.IJobCallback callback4 = jobParameters8.getCallback();
                    if (callback4 != null) {
                        try {
                            callback4.setNotification(jobParameters8.getJobId(), someArgs5.argi1, notification, someArgs5.argi2);
                        } catch (android.os.RemoteException e8) {
                            android.util.Log.e(android.app.job.JobServiceEngine.TAG, "Error providing notification: binder has gone away.");
                        }
                    } else {
                        android.util.Log.e(android.app.job.JobServiceEngine.TAG, "setNotification() called for a nonexistent job.");
                    }
                    someArgs5.recycle();
                    return;
                case 8:
                    android.app.job.JobParameters jobParameters9 = (android.app.job.JobParameters) message.obj;
                    try {
                        android.app.job.JobServiceEngine.this.onNetworkChanged(jobParameters9);
                        return;
                    } catch (java.lang.Exception e9) {
                        android.util.Log.e(android.app.job.JobServiceEngine.TAG, "Error while executing job: " + jobParameters9.getJobId());
                        throw new java.lang.RuntimeException(e9);
                    }
                default:
                    android.util.Log.e(android.app.job.JobServiceEngine.TAG, "Unrecognised message received.");
                    return;
            }
        }

        private void ackGetTransferredDownloadBytesMessage(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem, long j) {
            android.app.job.IJobCallback callback = jobParameters.getCallback();
            int jobId = jobParameters.getJobId();
            int workId = jobWorkItem == null ? -1 : jobWorkItem.getWorkId();
            if (callback == null) {
                if (android.util.Log.isLoggable(android.app.job.JobServiceEngine.TAG, 3)) {
                    android.util.Log.d(android.app.job.JobServiceEngine.TAG, "Attempting to ack a job that has already been processed.");
                }
            } else {
                try {
                    callback.acknowledgeGetTransferredDownloadBytesMessage(jobId, workId, j);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.app.job.JobServiceEngine.TAG, "System unreachable for returning progress.");
                }
            }
        }

        private void ackGetTransferredUploadBytesMessage(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem, long j) {
            android.app.job.IJobCallback callback = jobParameters.getCallback();
            int jobId = jobParameters.getJobId();
            int workId = jobWorkItem == null ? -1 : jobWorkItem.getWorkId();
            if (callback == null) {
                if (android.util.Log.isLoggable(android.app.job.JobServiceEngine.TAG, 3)) {
                    android.util.Log.d(android.app.job.JobServiceEngine.TAG, "Attempting to ack a job that has already been processed.");
                }
            } else {
                try {
                    callback.acknowledgeGetTransferredUploadBytesMessage(jobId, workId, j);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.app.job.JobServiceEngine.TAG, "System unreachable for returning progress.");
                }
            }
        }

        private void ackStartMessage(android.app.job.JobParameters jobParameters, boolean z) {
            android.app.job.IJobCallback callback = jobParameters.getCallback();
            int jobId = jobParameters.getJobId();
            if (callback == null) {
                if (android.util.Log.isLoggable(android.app.job.JobServiceEngine.TAG, 3)) {
                    android.util.Log.d(android.app.job.JobServiceEngine.TAG, "Attempting to ack a job that has already been processed.");
                }
            } else {
                try {
                    callback.acknowledgeStartMessage(jobId, z);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.app.job.JobServiceEngine.TAG, "System unreachable for starting job.");
                }
            }
        }

        private void ackStopMessage(android.app.job.JobParameters jobParameters, boolean z) {
            android.app.job.IJobCallback callback = jobParameters.getCallback();
            int jobId = jobParameters.getJobId();
            if (callback == null) {
                if (android.util.Log.isLoggable(android.app.job.JobServiceEngine.TAG, 3)) {
                    android.util.Log.d(android.app.job.JobServiceEngine.TAG, "Attempting to ack a job that has already been processed.");
                }
            } else {
                try {
                    callback.acknowledgeStopMessage(jobId, z);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.app.job.JobServiceEngine.TAG, "System unreachable for stopping job.");
                }
            }
        }
    }

    public JobServiceEngine(android.app.Service service) {
        this.mHandler = new android.app.job.JobServiceEngine.JobHandler(service.getMainLooper());
    }

    public final android.os.IBinder getBinder() {
        return this.mBinder.asBinder();
    }

    public void jobFinished(android.app.job.JobParameters jobParameters, boolean z) {
        if (jobParameters == null) {
            throw new java.lang.NullPointerException("params");
        }
        android.os.Message obtain = android.os.Message.obtain(this.mHandler, 2, jobParameters);
        obtain.arg2 = z ? 1 : 0;
        obtain.sendToTarget();
    }

    public void onNetworkChanged(android.app.job.JobParameters jobParameters) {
        android.util.Log.w(TAG, "onNetworkChanged() not implemented. Must override in a subclass.");
    }

    public long getTransferredDownloadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) {
        if (android.compat.Compatibility.isChangeEnabled(android.app.job.JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public long getTransferredUploadBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem) {
        if (android.compat.Compatibility.isChangeEnabled(android.app.job.JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public void updateTransferredNetworkBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem, long j, long j2) {
        if (jobParameters == null) {
            throw new java.lang.NullPointerException("params");
        }
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = jobParameters;
        obtain.arg2 = jobWorkItem;
        obtain.argl1 = j;
        obtain.argl2 = j2;
        this.mHandler.obtainMessage(5, obtain).sendToTarget();
    }

    public void updateEstimatedNetworkBytes(android.app.job.JobParameters jobParameters, android.app.job.JobWorkItem jobWorkItem, long j, long j2) {
        if (jobParameters == null) {
            throw new java.lang.NullPointerException("params");
        }
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = jobParameters;
        obtain.arg2 = jobWorkItem;
        obtain.argl1 = j;
        obtain.argl2 = j2;
        this.mHandler.obtainMessage(6, obtain).sendToTarget();
    }

    public void setNotification(android.app.job.JobParameters jobParameters, int i, android.app.Notification notification, int i2) {
        if (jobParameters == null) {
            throw new java.lang.NullPointerException("params");
        }
        if (notification == null) {
            throw new java.lang.NullPointerException("notification");
        }
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = jobParameters;
        obtain.arg2 = notification;
        obtain.argi1 = i;
        obtain.argi2 = i2;
        this.mHandler.obtainMessage(7, obtain).sendToTarget();
    }
}
