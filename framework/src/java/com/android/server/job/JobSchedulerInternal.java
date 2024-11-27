package com.android.server.job;

/* loaded from: classes5.dex */
public interface JobSchedulerInternal {
    void addBackingUpUid(int i);

    void cancelJobsForUid(int i, boolean z, int i2, int i3, java.lang.String str);

    void clearAllBackingUpUids();

    java.lang.String getCloudMediaProviderPackage(int i);

    com.android.server.job.JobSchedulerInternal.JobStorePersistStats getPersistStats();

    java.util.List<android.app.job.JobInfo> getSystemScheduledOwnJobs(java.lang.String str);

    boolean hasRunBackupJobsPermission(java.lang.String str, int i);

    boolean isAppConsideredBuggy(int i, java.lang.String str, int i2, java.lang.String str2);

    boolean isNotificationAssociatedWithAnyUserInitiatedJobs(int i, int i2, java.lang.String str);

    boolean isNotificationChannelAssociatedWithAnyUserInitiatedJobs(java.lang.String str, int i, java.lang.String str2);

    void removeBackingUpUid(int i);

    void reportAppUsage(java.lang.String str, int i);

    public static class JobStorePersistStats {
        public int countAllJobsLoaded;
        public int countAllJobsSaved;
        public int countSystemServerJobsLoaded;
        public int countSystemServerJobsSaved;
        public int countSystemSyncManagerJobsLoaded;
        public int countSystemSyncManagerJobsSaved;

        public JobStorePersistStats() {
            this.countAllJobsLoaded = -1;
            this.countSystemServerJobsLoaded = -1;
            this.countSystemSyncManagerJobsLoaded = -1;
            this.countAllJobsSaved = -1;
            this.countSystemServerJobsSaved = -1;
            this.countSystemSyncManagerJobsSaved = -1;
        }

        public JobStorePersistStats(com.android.server.job.JobSchedulerInternal.JobStorePersistStats jobStorePersistStats) {
            this.countAllJobsLoaded = -1;
            this.countSystemServerJobsLoaded = -1;
            this.countSystemSyncManagerJobsLoaded = -1;
            this.countAllJobsSaved = -1;
            this.countSystemServerJobsSaved = -1;
            this.countSystemSyncManagerJobsSaved = -1;
            this.countAllJobsLoaded = jobStorePersistStats.countAllJobsLoaded;
            this.countSystemServerJobsLoaded = jobStorePersistStats.countSystemServerJobsLoaded;
            this.countSystemSyncManagerJobsLoaded = jobStorePersistStats.countSystemSyncManagerJobsLoaded;
            this.countAllJobsSaved = jobStorePersistStats.countAllJobsSaved;
            this.countSystemServerJobsSaved = jobStorePersistStats.countSystemServerJobsSaved;
            this.countSystemSyncManagerJobsSaved = jobStorePersistStats.countSystemSyncManagerJobsSaved;
        }

        public java.lang.String toString() {
            return "FirstLoad: " + this.countAllJobsLoaded + "/" + this.countSystemServerJobsLoaded + "/" + this.countSystemSyncManagerJobsLoaded + " LastSave: " + this.countAllJobsSaved + "/" + this.countSystemServerJobsSaved + "/" + this.countSystemSyncManagerJobsSaved;
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            long start2 = protoOutputStream.start(1146756268033L);
            protoOutputStream.write(1120986464257L, this.countAllJobsLoaded);
            protoOutputStream.write(1120986464258L, this.countSystemServerJobsLoaded);
            protoOutputStream.write(1120986464259L, this.countSystemSyncManagerJobsLoaded);
            protoOutputStream.end(start2);
            long start3 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1120986464257L, this.countAllJobsSaved);
            protoOutputStream.write(1120986464258L, this.countSystemServerJobsSaved);
            protoOutputStream.write(1120986464259L, this.countSystemSyncManagerJobsSaved);
            protoOutputStream.end(start3);
            protoOutputStream.end(start);
        }
    }
}
