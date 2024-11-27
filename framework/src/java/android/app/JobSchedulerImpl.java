package android.app;

/* loaded from: classes.dex */
public class JobSchedulerImpl extends android.app.job.JobScheduler {
    android.app.job.IJobScheduler mBinder;
    private final android.content.Context mContext;
    private final java.lang.String mNamespace;

    public JobSchedulerImpl(android.content.Context context, android.app.job.IJobScheduler iJobScheduler) {
        this(context, iJobScheduler, null);
    }

    private JobSchedulerImpl(android.content.Context context, android.app.job.IJobScheduler iJobScheduler, java.lang.String str) {
        this.mContext = context;
        this.mBinder = iJobScheduler;
        this.mNamespace = str;
    }

    private JobSchedulerImpl(android.app.JobSchedulerImpl jobSchedulerImpl, java.lang.String str) {
        this(jobSchedulerImpl.mContext, jobSchedulerImpl.mBinder, str);
    }

    @Override // android.app.job.JobScheduler
    public android.app.job.JobScheduler forNamespace(java.lang.String str) {
        java.lang.String sanitizeNamespace = sanitizeNamespace(str);
        if (sanitizeNamespace == null) {
            throw new java.lang.NullPointerException("namespace cannot be null");
        }
        if (sanitizeNamespace.isEmpty()) {
            throw new java.lang.IllegalArgumentException("namespace cannot be empty");
        }
        return new android.app.JobSchedulerImpl(this, sanitizeNamespace);
    }

    @Override // android.app.job.JobScheduler
    public java.lang.String getNamespace() {
        return this.mNamespace;
    }

    @Override // android.app.job.JobScheduler
    public int schedule(android.app.job.JobInfo jobInfo) {
        try {
            return this.mBinder.schedule(this.mNamespace, jobInfo);
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    @Override // android.app.job.JobScheduler
    public int enqueue(android.app.job.JobInfo jobInfo, android.app.job.JobWorkItem jobWorkItem) {
        try {
            return this.mBinder.enqueue(this.mNamespace, jobInfo, jobWorkItem);
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    @Override // android.app.job.JobScheduler
    public int scheduleAsPackage(android.app.job.JobInfo jobInfo, java.lang.String str, int i, java.lang.String str2) {
        try {
            return this.mBinder.scheduleAsPackage(this.mNamespace, jobInfo, str, i, str2);
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    @Override // android.app.job.JobScheduler
    public void cancel(int i) {
        try {
            this.mBinder.cancel(this.mNamespace, i);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.app.job.JobScheduler
    public void cancelAll() {
        try {
            this.mBinder.cancelAllInNamespace(this.mNamespace);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.app.job.JobScheduler
    public void cancelInAllNamespaces() {
        try {
            this.mBinder.cancelAll();
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.app.job.JobScheduler
    public java.util.List<android.app.job.JobInfo> getAllPendingJobs() {
        try {
            return this.mBinder.getAllPendingJobsInNamespace(this.mNamespace).getList();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    @Override // android.app.job.JobScheduler
    public java.util.Map<java.lang.String, java.util.List<android.app.job.JobInfo>> getPendingJobsInAllNamespaces() {
        try {
            java.util.Map<java.lang.String, android.content.pm.ParceledListSlice<android.app.job.JobInfo>> allPendingJobs = this.mBinder.getAllPendingJobs();
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            for (java.lang.String str : allPendingJobs.keySet()) {
                arrayMap.put(str, allPendingJobs.get(str).getList());
            }
            return arrayMap;
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    @Override // android.app.job.JobScheduler
    public android.app.job.JobInfo getPendingJob(int i) {
        try {
            return this.mBinder.getPendingJob(this.mNamespace, i);
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    @Override // android.app.job.JobScheduler
    public int getPendingJobReason(int i) {
        try {
            return this.mBinder.getPendingJobReason(this.mNamespace, i);
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    @Override // android.app.job.JobScheduler
    public boolean canRunUserInitiatedJobs() {
        try {
            return this.mBinder.canRunUserInitiatedJobs(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @Override // android.app.job.JobScheduler
    public boolean hasRunUserInitiatedJobsPermission(java.lang.String str, int i) {
        try {
            return this.mBinder.hasRunUserInitiatedJobsPermission(str, i);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @Override // android.app.job.JobScheduler
    public java.util.List<android.app.job.JobInfo> getStartedJobs() {
        try {
            return this.mBinder.getStartedJobs();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    @Override // android.app.job.JobScheduler
    public java.util.List<android.app.job.JobSnapshot> getAllJobSnapshots() {
        try {
            return this.mBinder.getAllJobSnapshots().getList();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    @Override // android.app.job.JobScheduler
    public void registerUserVisibleJobObserver(android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver) {
        try {
            this.mBinder.registerUserVisibleJobObserver(iUserVisibleJobObserver);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.app.job.JobScheduler
    public void unregisterUserVisibleJobObserver(android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver) {
        try {
            this.mBinder.unregisterUserVisibleJobObserver(iUserVisibleJobObserver);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.app.job.JobScheduler
    public void notePendingUserRequestedAppStop(java.lang.String str, int i, java.lang.String str2) {
        try {
            this.mBinder.notePendingUserRequestedAppStop(str, i, str2);
        } catch (android.os.RemoteException e) {
        }
    }
}
