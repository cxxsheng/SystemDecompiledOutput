package android.app.job;

/* loaded from: classes.dex */
public abstract class JobScheduler {
    public static final int PENDING_JOB_REASON_APP = 1;
    public static final int PENDING_JOB_REASON_APP_STANDBY = 2;
    public static final int PENDING_JOB_REASON_BACKGROUND_RESTRICTION = 3;
    public static final int PENDING_JOB_REASON_CONSTRAINT_BATTERY_NOT_LOW = 4;
    public static final int PENDING_JOB_REASON_CONSTRAINT_CHARGING = 5;
    public static final int PENDING_JOB_REASON_CONSTRAINT_CONNECTIVITY = 6;
    public static final int PENDING_JOB_REASON_CONSTRAINT_CONTENT_TRIGGER = 7;
    public static final int PENDING_JOB_REASON_CONSTRAINT_DEVICE_IDLE = 8;
    public static final int PENDING_JOB_REASON_CONSTRAINT_MINIMUM_LATENCY = 9;
    public static final int PENDING_JOB_REASON_CONSTRAINT_PREFETCH = 10;
    public static final int PENDING_JOB_REASON_CONSTRAINT_STORAGE_NOT_LOW = 11;
    public static final int PENDING_JOB_REASON_DEVICE_STATE = 12;
    public static final int PENDING_JOB_REASON_EXECUTING = -1;
    public static final int PENDING_JOB_REASON_INVALID_JOB_ID = -2;
    public static final int PENDING_JOB_REASON_JOB_SCHEDULER_OPTIMIZATION = 13;
    public static final int PENDING_JOB_REASON_QUOTA = 14;
    public static final int PENDING_JOB_REASON_UNDEFINED = 0;
    public static final int PENDING_JOB_REASON_USER = 15;
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    public static final long THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION = 255371817;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PendingJobReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Result {
    }

    public abstract void cancel(int i);

    public abstract void cancelAll();

    public abstract int enqueue(android.app.job.JobInfo jobInfo, android.app.job.JobWorkItem jobWorkItem);

    public abstract java.util.List<android.app.job.JobSnapshot> getAllJobSnapshots();

    public abstract java.util.List<android.app.job.JobInfo> getAllPendingJobs();

    public abstract android.app.job.JobInfo getPendingJob(int i);

    public abstract java.util.List<android.app.job.JobInfo> getStartedJobs();

    public abstract void notePendingUserRequestedAppStop(java.lang.String str, int i, java.lang.String str2);

    public abstract void registerUserVisibleJobObserver(android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver);

    public abstract int schedule(android.app.job.JobInfo jobInfo);

    @android.annotation.SystemApi
    public abstract int scheduleAsPackage(android.app.job.JobInfo jobInfo, java.lang.String str, int i, java.lang.String str2);

    public abstract void unregisterUserVisibleJobObserver(android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver);

    public android.app.job.JobScheduler forNamespace(java.lang.String str) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public java.lang.String getNamespace() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public static java.lang.String sanitizeNamespace(java.lang.String str) {
        if (str == null) {
            return null;
        }
        return str.trim().intern();
    }

    public void cancelInAllNamespaces() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public java.util.Map<java.lang.String, java.util.List<android.app.job.JobInfo>> getPendingJobsInAllNamespaces() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public int getPendingJobReason(int i) {
        return 0;
    }

    public boolean canRunUserInitiatedJobs() {
        return false;
    }

    public boolean hasRunUserInitiatedJobsPermission(java.lang.String str, int i) {
        return false;
    }
}
