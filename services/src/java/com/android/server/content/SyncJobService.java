package com.android.server.content;

/* loaded from: classes.dex */
public class SyncJobService extends android.app.job.JobService {
    private static final java.lang.String TAG = "SyncManager";

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static com.android.server.content.SyncJobService sInstance;
    private static final java.lang.Object sLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static final android.util.SparseArray<android.app.job.JobParameters> sJobParamsMap = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static final android.util.SparseBooleanArray sStartedSyncs = new android.util.SparseBooleanArray();

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static final android.util.SparseLongArray sJobStartUptimes = new android.util.SparseLongArray();
    private static final com.android.server.content.SyncLogger sLogger = com.android.server.content.SyncLogger.getInstance();

    private void updateInstance() {
        synchronized (com.android.server.content.SyncJobService.class) {
            sInstance = this;
        }
    }

    @android.annotation.Nullable
    private static com.android.server.content.SyncJobService getInstance() {
        com.android.server.content.SyncJobService syncJobService;
        synchronized (sLock) {
            try {
                if (sInstance == null) {
                    android.util.Slog.wtf("SyncManager", "sInstance == null");
                }
                syncJobService = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return syncJobService;
    }

    public static boolean isReady() {
        boolean z;
        synchronized (sLock) {
            z = sInstance != null;
        }
        return z;
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        updateInstance();
        sLogger.purgeOldLogs();
        com.android.server.content.SyncOperation maybeCreateFromJobExtras = com.android.server.content.SyncOperation.maybeCreateFromJobExtras(jobParameters.getExtras());
        if (maybeCreateFromJobExtras == null) {
            android.util.Slog.wtf("SyncManager", "Got invalid job " + jobParameters.getJobId());
            return false;
        }
        boolean readyToSync = com.android.server.content.SyncManager.readyToSync(maybeCreateFromJobExtras.target.userId);
        sLogger.log("onStartJob() jobid=", java.lang.Integer.valueOf(jobParameters.getJobId()), " op=", maybeCreateFromJobExtras, " readyToSync", java.lang.Boolean.valueOf(readyToSync));
        if (!readyToSync) {
            jobFinished(jobParameters, !maybeCreateFromJobExtras.isPeriodic);
            return true;
        }
        boolean isLoggable = android.util.Log.isLoggable("SyncManager", 2);
        synchronized (sLock) {
            int jobId = jobParameters.getJobId();
            sJobParamsMap.put(jobId, jobParameters);
            sStartedSyncs.delete(jobId);
            sJobStartUptimes.put(jobId, android.os.SystemClock.uptimeMillis());
        }
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = 10;
        if (isLoggable) {
            android.util.Slog.v("SyncManager", "Got start job message " + maybeCreateFromJobExtras.target);
        }
        obtain.obj = maybeCreateFromJobExtras;
        com.android.server.content.SyncManager.sendMessage(obtain);
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        int i;
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "onStopJob called " + jobParameters.getJobId() + ", reason: " + jobParameters.getInternalStopReasonCode());
        }
        com.android.server.content.SyncOperation maybeCreateFromJobExtras = com.android.server.content.SyncOperation.maybeCreateFromJobExtras(jobParameters.getExtras());
        if (maybeCreateFromJobExtras == null) {
            android.util.Slog.wtf("SyncManager", "Got invalid job " + jobParameters.getJobId());
            return false;
        }
        boolean readyToSync = com.android.server.content.SyncManager.readyToSync(maybeCreateFromJobExtras.target.userId);
        sLogger.log("onStopJob() ", sLogger.jobParametersToString(jobParameters), " readyToSync=", java.lang.Boolean.valueOf(readyToSync));
        synchronized (sLock) {
            try {
                int jobId = jobParameters.getJobId();
                sJobParamsMap.remove(jobId);
                long j = sJobStartUptimes.get(jobId);
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                if (uptimeMillis - j > 60000 && readyToSync && !sStartedSyncs.get(jobId)) {
                    wtf("Job " + jobId + " didn't start:  startUptime=" + j + " nowUptime=" + uptimeMillis + " params=" + jobParametersToString(jobParameters));
                }
                sStartedSyncs.delete(jobId);
                sJobStartUptimes.delete(jobId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = 11;
        obtain.obj = maybeCreateFromJobExtras;
        if (jobParameters.getInternalStopReasonCode() == 0) {
            i = 0;
        } else {
            i = 1;
        }
        obtain.arg1 = i;
        obtain.arg2 = jobParameters.getInternalStopReasonCode() != 3 ? 0 : 1;
        com.android.server.content.SyncManager.sendMessage(obtain);
        return false;
    }

    public static void callJobFinished(int i, boolean z, java.lang.String str) {
        com.android.server.content.SyncJobService syncJobService = getInstance();
        if (syncJobService != null) {
            syncJobService.callJobFinishedInner(i, z, str);
        }
    }

    public void callJobFinishedInner(int i, boolean z, java.lang.String str) {
        synchronized (sLock) {
            try {
                android.app.job.JobParameters jobParameters = sJobParamsMap.get(i);
                sLogger.log("callJobFinished()", " jobid=", java.lang.Integer.valueOf(i), " needsReschedule=", java.lang.Boolean.valueOf(z), " ", sLogger.jobParametersToString(jobParameters), " why=", str);
                if (jobParameters != null) {
                    jobFinished(jobParameters, z);
                    sJobParamsMap.remove(i);
                } else {
                    android.util.Slog.e("SyncManager", "Job params not found for " + java.lang.String.valueOf(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public static void markSyncStarted(int i) {
        synchronized (sLock) {
            sStartedSyncs.put(i, true);
        }
    }

    public static java.lang.String jobParametersToString(android.app.job.JobParameters jobParameters) {
        if (jobParameters == null) {
            return "job:null";
        }
        return "job:#" + jobParameters.getJobId() + ":sr=[" + jobParameters.getInternalStopReasonCode() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + jobParameters.getDebugStopReason() + "]:" + com.android.server.content.SyncOperation.maybeCreateFromJobExtras(jobParameters.getExtras());
    }

    private static void wtf(java.lang.String str) {
        sLogger.log(str);
        android.util.Slog.wtf("SyncManager", str);
    }
}
