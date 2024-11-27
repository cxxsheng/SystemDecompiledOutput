package com.android.server.job;

/* loaded from: classes2.dex */
public final class JobServiceContext implements android.content.ServiceConnection {
    private static final long ANR_PRE_UDC_APIS_ON_SLOW_RESPONSES = 258236856;
    private static final long EXECUTION_DURATION_STAMP_PERIOD_MILLIS = 300000;
    private static final int MSG_TIMEOUT = 0;
    public static final int NO_PREFERRED_UID = -1;
    private static final java.lang.String TAG = "JobServiceContext";
    static final int VERB_BINDING = 0;
    static final int VERB_EXECUTING = 2;
    static final int VERB_FINISHED = 4;
    static final int VERB_STARTING = 1;
    static final int VERB_STOPPING = 3;
    private boolean mAwaitingNotification;
    private final com.android.internal.app.IBatteryStats mBatteryStats;
    private final android.os.Handler mCallbackHandler;
    private boolean mCancelled;
    private final com.android.server.job.JobCompletedListener mCompletedListener;
    private final android.content.Context mContext;
    private java.lang.String mDeathMarkDebugReason;
    private int mDeathMarkInternalStopReason;
    private long mEstimatedDownloadBytes;
    private long mEstimatedUploadBytes;
    private long mExecutionStartTimeElapsed;
    private long mInitialDownloadedBytesFromCalling;
    private long mInitialDownloadedBytesFromSource;
    private long mInitialUploadedBytesFromCalling;
    private long mInitialUploadedBytesFromSource;
    private final com.android.server.job.JobConcurrencyManager mJobConcurrencyManager;
    private final com.android.server.job.JobPackageTracker mJobPackageTracker;
    private long mLastExecutionDurationStampTimeElapsed;
    private long mLastUnsuccessfulFinishElapsed;
    private final java.lang.Object mLock;
    private long mMaxExecutionTimeMillis;
    private long mMinExecutionGuaranteeMillis;
    private final com.android.server.job.JobNotificationCoordinator mNotificationCoordinator;
    private android.app.job.JobParameters mParams;
    private java.lang.String mPendingDebugStopReason;
    private int mPendingInternalStopReason;
    private android.net.Network mPendingNetworkChange;
    private final android.os.PowerManager mPowerManager;
    private boolean mPreviousJobHadSuccessfulFinish;
    private com.android.server.job.JobServiceContext.JobCallback mRunningCallback;
    private com.android.server.job.controllers.JobStatus mRunningJob;
    private int mRunningJobWorkType;
    private final com.android.server.job.JobSchedulerService mService;
    public java.lang.String mStoppedReason;
    public long mStoppedTime;
    private long mTimeoutElapsed;
    private long mTransferredDownloadBytes;
    private long mTransferredUploadBytes;
    private android.os.PowerManager.WakeLock mWakeLock;
    android.app.job.IJobService service;
    private static final boolean DEBUG = com.android.server.job.JobSchedulerService.DEBUG;
    private static final boolean DEBUG_STANDBY = com.android.server.job.JobSchedulerService.DEBUG_STANDBY;
    private static final long OP_BIND_TIMEOUT_MILLIS = android.os.Build.HW_TIMEOUT_MULTIPLIER * 18000;
    private static final long OP_TIMEOUT_MILLIS = android.os.Build.HW_TIMEOUT_MULTIPLIER * com.android.server.EventLogTags.JOB_DEFERRED_EXECUTION;
    private static final long NOTIFICATION_TIMEOUT_MILLIS = android.os.Build.HW_TIMEOUT_MULTIPLIER * com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
    private static final com.android.modules.expresslog.Histogram sEnqueuedJwiAtJobStart = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_w_uid_enqueued_work_items_at_job_start", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(20, 1, 3.0f, 1.4f));
    private static final com.android.modules.expresslog.Histogram sTransferredNetworkDownloadKBHighWaterMarkLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_transferred_network_download_kilobytes_high_water_mark", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
    private static final com.android.modules.expresslog.Histogram sTransferredNetworkUploadKBHighWaterMarkLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_transferred_network_upload_kilobytes_high_water_mark", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
    private static final com.android.modules.expresslog.Histogram sUpdatedEstimatedNetworkDownloadKBLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_updated_estimated_network_download_kilobytes", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
    private static final com.android.modules.expresslog.Histogram sUpdatedEstimatedNetworkUploadKBLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_updated_estimated_network_upload_kilobytes", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
    private static final java.lang.String[] VERB_STRINGS = {"VERB_BINDING", "VERB_STARTING", "VERB_EXECUTING", "VERB_STOPPING", "VERB_FINISHED"};
    private int mPendingStopReason = 0;
    private int mDeathMarkStopReason = 0;
    private final android.app.ActivityManagerInternal mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
    private final com.android.server.tare.EconomyManagerInternal mEconomyManagerInternal = (com.android.server.tare.EconomyManagerInternal) com.android.server.LocalServices.getService(com.android.server.tare.EconomyManagerInternal.class);
    private final android.app.usage.UsageStatsManagerInternal mUsageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mAvailable = true;

    @com.android.internal.annotations.VisibleForTesting
    int mVerb = 4;
    private int mPreferredUid = -1;

    final class JobCallback extends android.app.job.IJobCallback.Stub {
        public java.lang.String mStoppedReason;
        public long mStoppedTime;

        JobCallback() {
        }

        public void acknowledgeGetTransferredDownloadBytesMessage(int i, int i2, long j) {
            com.android.server.job.JobServiceContext.this.doAcknowledgeGetTransferredDownloadBytesMessage(this, i, i2, j);
        }

        public void acknowledgeGetTransferredUploadBytesMessage(int i, int i2, long j) {
            com.android.server.job.JobServiceContext.this.doAcknowledgeGetTransferredUploadBytesMessage(this, i, i2, j);
        }

        public void acknowledgeStartMessage(int i, boolean z) {
            com.android.server.job.JobServiceContext.this.doAcknowledgeStartMessage(this, i, z);
        }

        public void acknowledgeStopMessage(int i, boolean z) {
            com.android.server.job.JobServiceContext.this.doAcknowledgeStopMessage(this, i, z);
        }

        public android.app.job.JobWorkItem dequeueWork(int i) {
            return com.android.server.job.JobServiceContext.this.doDequeueWork(this, i);
        }

        public boolean completeWork(int i, int i2) {
            return com.android.server.job.JobServiceContext.this.doCompleteWork(this, i, i2);
        }

        public void jobFinished(int i, boolean z) {
            com.android.server.job.JobServiceContext.this.doJobFinished(this, i, z);
        }

        public void updateEstimatedNetworkBytes(int i, android.app.job.JobWorkItem jobWorkItem, long j, long j2) {
            com.android.server.job.JobServiceContext.this.doUpdateEstimatedNetworkBytes(this, i, jobWorkItem, j, j2);
        }

        public void updateTransferredNetworkBytes(int i, android.app.job.JobWorkItem jobWorkItem, long j, long j2) {
            com.android.server.job.JobServiceContext.this.doUpdateTransferredNetworkBytes(this, i, jobWorkItem, j, j2);
        }

        public void setNotification(int i, int i2, android.app.Notification notification, int i3) {
            com.android.server.job.JobServiceContext.this.doSetNotification(this, i, i2, notification, i3);
        }
    }

    JobServiceContext(com.android.server.job.JobSchedulerService jobSchedulerService, com.android.server.job.JobConcurrencyManager jobConcurrencyManager, com.android.server.job.JobNotificationCoordinator jobNotificationCoordinator, com.android.internal.app.IBatteryStats iBatteryStats, com.android.server.job.JobPackageTracker jobPackageTracker, android.os.Looper looper) {
        this.mContext = jobSchedulerService.getContext();
        this.mLock = jobSchedulerService.getLock();
        this.mService = jobSchedulerService;
        this.mBatteryStats = iBatteryStats;
        this.mJobPackageTracker = jobPackageTracker;
        this.mCallbackHandler = new com.android.server.job.JobServiceContext.JobServiceHandler(looper);
        this.mJobConcurrencyManager = jobConcurrencyManager;
        this.mNotificationCoordinator = jobNotificationCoordinator;
        this.mCompletedListener = jobSchedulerService;
        this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x020a A[Catch: all -> 0x0015, TryCatch #1 {all -> 0x0015, blocks: (B:4:0x0007, B:6:0x000c, B:7:0x0013, B:10:0x0018, B:12:0x0033, B:15:0x0046, B:17:0x004b, B:18:0x005d, B:20:0x0062, B:21:0x0074, B:23:0x007e, B:24:0x0085, B:26:0x00f4, B:28:0x0100, B:29:0x0124, B:31:0x0183, B:33:0x0189, B:35:0x0190, B:40:0x01c6, B:43:0x020a, B:45:0x020e, B:46:0x022c, B:47:0x0245, B:49:0x0247, B:51:0x0278, B:53:0x0287, B:55:0x028d, B:56:0x02a0, B:58:0x040a, B:60:0x0435, B:61:0x0449, B:63:0x046d, B:64:0x0483, B:66:0x0489, B:67:0x04a1, B:68:0x04c2, B:70:0x04c8, B:72:0x04d7, B:73:0x04e6, B:74:0x0507, B:78:0x0297, B:79:0x0282, B:82:0x01dc, B:84:0x01a4, B:86:0x01aa, B:90:0x01b6), top: B:3:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0247 A[Catch: all -> 0x0015, TryCatch #1 {all -> 0x0015, blocks: (B:4:0x0007, B:6:0x000c, B:7:0x0013, B:10:0x0018, B:12:0x0033, B:15:0x0046, B:17:0x004b, B:18:0x005d, B:20:0x0062, B:21:0x0074, B:23:0x007e, B:24:0x0085, B:26:0x00f4, B:28:0x0100, B:29:0x0124, B:31:0x0183, B:33:0x0189, B:35:0x0190, B:40:0x01c6, B:43:0x020a, B:45:0x020e, B:46:0x022c, B:47:0x0245, B:49:0x0247, B:51:0x0278, B:53:0x0287, B:55:0x028d, B:56:0x02a0, B:58:0x040a, B:60:0x0435, B:61:0x0449, B:63:0x046d, B:64:0x0483, B:66:0x0489, B:67:0x04a1, B:68:0x04c2, B:70:0x04c8, B:72:0x04d7, B:73:0x04e6, B:74:0x0507, B:78:0x0297, B:79:0x0282, B:82:0x01dc, B:84:0x01a4, B:86:0x01aa, B:90:0x01b6), top: B:3:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean executeRunnableJob(com.android.server.job.controllers.JobStatus jobStatus, int i) {
        android.net.Uri[] uriArr;
        java.lang.String[] strArr;
        boolean z;
        boolean z2;
        boolean z3;
        java.lang.String[] strArr2;
        long j;
        synchronized (this.mLock) {
            try {
                if (this.mAvailable) {
                    this.mPreferredUid = -1;
                    this.mRunningJob = jobStatus;
                    this.mRunningJobWorkType = i;
                    this.mRunningCallback = new com.android.server.job.JobServiceContext.JobCallback();
                    this.mPendingNetworkChange = null;
                    boolean z4 = jobStatus.hasDeadlineConstraint() && jobStatus.getLatestRunTimeElapsed() < com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                    if (jobStatus.changedUris == null) {
                        uriArr = null;
                    } else {
                        android.net.Uri[] uriArr2 = new android.net.Uri[jobStatus.changedUris.size()];
                        jobStatus.changedUris.toArray(uriArr2);
                        uriArr = uriArr2;
                    }
                    if (jobStatus.changedAuthorities == null) {
                        strArr = null;
                    } else {
                        java.lang.String[] strArr3 = new java.lang.String[jobStatus.changedAuthorities.size()];
                        jobStatus.changedAuthorities.toArray(strArr3);
                        strArr = strArr3;
                    }
                    android.app.job.JobInfo job = jobStatus.getJob();
                    this.mParams = new android.app.job.JobParameters(this.mRunningCallback, jobStatus.getNamespace(), jobStatus.getJobId(), job.getExtras(), job.getTransientExtras(), job.getClipData(), job.getClipGrantFlags(), z4, jobStatus.shouldTreatAsExpeditedJob(), jobStatus.shouldTreatAsUserInitiatedJob(), uriArr, strArr, canGetNetworkInformation(jobStatus) ? jobStatus.network : null);
                    this.mExecutionStartTimeElapsed = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                    this.mLastExecutionDurationStampTimeElapsed = this.mExecutionStartTimeElapsed;
                    this.mMinExecutionGuaranteeMillis = this.mService.getMinJobExecutionGuaranteeMs(jobStatus);
                    this.mMaxExecutionTimeMillis = java.lang.Math.max(this.mService.getMaxJobExecutionTimeMs(jobStatus), this.mMinExecutionGuaranteeMillis);
                    this.mEstimatedDownloadBytes = jobStatus.getEstimatedNetworkDownloadBytes();
                    this.mEstimatedUploadBytes = jobStatus.getEstimatedNetworkUploadBytes();
                    this.mTransferredUploadBytes = 0L;
                    this.mTransferredDownloadBytes = 0L;
                    this.mAwaitingNotification = jobStatus.isUserVisibleJob();
                    long whenStandbyDeferred = jobStatus.getWhenStandbyDeferred();
                    if (whenStandbyDeferred > 0) {
                        long j2 = this.mExecutionStartTimeElapsed - whenStandbyDeferred;
                        android.util.EventLog.writeEvent(com.android.server.EventLogTags.JOB_DEFERRED_EXECUTION, j2);
                        if (DEBUG_STANDBY) {
                            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
                            sb.append("Starting job deferred for standby by ");
                            android.util.TimeUtils.formatDuration(j2, sb);
                            sb.append(" ms : ");
                            sb.append(jobStatus.toShortString());
                            android.util.Slog.v(TAG, sb.toString());
                        }
                    }
                    jobStatus.clearPersistedUtcTimes();
                    this.mWakeLock = this.mPowerManager.newWakeLock(1, jobStatus.getWakelockTag());
                    this.mWakeLock.setWorkSource(this.mService.deriveWorkSource(jobStatus.getSourceUid(), jobStatus.getSourcePackageName()));
                    this.mWakeLock.setReferenceCounted(false);
                    this.mWakeLock.acquire();
                    this.mEconomyManagerInternal.noteInstantaneousEvent(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName(), getStartActionId(jobStatus), java.lang.String.valueOf(jobStatus.getJobId()));
                    this.mVerb = 0;
                    scheduleOpTimeOutLocked();
                    android.content.Intent flags = new android.content.Intent().setComponent(jobStatus.getServiceComponent()).setFlags(4);
                    try {
                        if (jobStatus.shouldTreatAsUserInitiatedJob() && !jobStatus.isUserBgRestricted()) {
                            if (!jobStatus.hasConnectivityConstraint()) {
                                j = 98305;
                            } else {
                                j = 4295196673L;
                            }
                            z = true;
                        } else if (jobStatus.shouldTreatAsExpeditedJob() || jobStatus.shouldTreatAsUserInitiatedJob()) {
                            if (!jobStatus.hasConnectivityConstraint()) {
                                j = 98309;
                                z = false;
                            } else {
                                j = 229381;
                                z = false;
                            }
                        } else {
                            j = 33029;
                            z = false;
                        }
                        try {
                            z2 = this.mContext.bindServiceAsUser(flags, this, android.content.Context.BindServiceFlags.of(j), android.os.UserHandle.of(jobStatus.getUserId()));
                            z3 = z;
                        } catch (java.lang.SecurityException e) {
                            e = e;
                            android.util.Slog.w(TAG, "Job service " + jobStatus.getServiceComponent().getShortClassName() + " cannot be executed: " + e.getMessage());
                            z2 = false;
                            z3 = z;
                            if (z2) {
                            }
                        }
                    } catch (java.lang.SecurityException e2) {
                        e = e2;
                        z = false;
                    }
                    if (z2) {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, jobStatus.getServiceComponent().getShortClassName() + " unavailable.");
                        }
                        this.mContext.unbindService(this);
                        this.mRunningJob = null;
                        this.mRunningJobWorkType = 0;
                        this.mRunningCallback = null;
                        this.mParams = null;
                        this.mExecutionStartTimeElapsed = 0L;
                        this.mWakeLock.release();
                        this.mVerb = 4;
                        removeOpTimeOutLocked();
                        return false;
                    }
                    this.mJobPackageTracker.noteActive(jobStatus);
                    int sourceUid = jobStatus.getSourceUid();
                    this.mInitialDownloadedBytesFromSource = android.net.TrafficStats.getUidRxBytes(sourceUid);
                    this.mInitialUploadedBytesFromSource = android.net.TrafficStats.getUidTxBytes(sourceUid);
                    this.mInitialDownloadedBytesFromCalling = android.net.TrafficStats.getUidRxBytes(jobStatus.getUid());
                    this.mInitialUploadedBytesFromCalling = android.net.TrafficStats.getUidTxBytes(jobStatus.getUid());
                    int[] iArr = jobStatus.isProxyJob() ? new int[]{sourceUid, jobStatus.getUid()} : new int[]{sourceUid};
                    if (jobStatus.isProxyJob()) {
                        strArr2 = new java.lang.String[]{null, jobStatus.getSourceTag()};
                    } else {
                        strArr2 = new java.lang.String[]{jobStatus.getSourceTag()};
                    }
                    boolean z5 = z3;
                    com.android.internal.util.FrameworkStatsLog.write(8, iArr, strArr2, jobStatus.getBatteryName(), 1, -1, jobStatus.getStandbyBucket(), jobStatus.getLoggingJobId(), jobStatus.hasChargingConstraint(), jobStatus.hasBatteryNotLowConstraint(), jobStatus.hasStorageNotLowConstraint(), jobStatus.hasTimingDelayConstraint(), jobStatus.hasDeadlineConstraint(), jobStatus.hasIdleConstraint(), jobStatus.hasConnectivityConstraint(), jobStatus.hasContentTriggerConstraint(), jobStatus.isRequestedExpeditedJob(), jobStatus.shouldTreatAsExpeditedJob(), 0, jobStatus.getJob().isPrefetch(), jobStatus.getJob().getPriority(), jobStatus.getEffectivePriority(), jobStatus.getNumPreviousAttempts(), jobStatus.getJob().getMaxExecutionDelayMillis(), z4, jobStatus.isConstraintSatisfied(1), jobStatus.isConstraintSatisfied(2), jobStatus.isConstraintSatisfied(8), jobStatus.isConstraintSatisfied(Integer.MIN_VALUE), jobStatus.isConstraintSatisfied(4), jobStatus.isConstraintSatisfied(268435456), jobStatus.isConstraintSatisfied(67108864), this.mExecutionStartTimeElapsed - jobStatus.enqueueTime, jobStatus.getJob().isUserInitiated(), jobStatus.shouldTreatAsUserInitiatedJob(), jobStatus.getJob().isPeriodic(), jobStatus.getJob().getMinLatencyMillis(), jobStatus.getEstimatedNetworkDownloadBytes(), jobStatus.getEstimatedNetworkUploadBytes(), jobStatus.getWorkCount(), android.app.ActivityManager.processStateAmToProto(this.mService.getUidProcState(jobStatus.getUid())), jobStatus.getNamespaceHash(), 0L, 0L, 0L, 0L, jobStatus.getJob().getIntervalMillis(), jobStatus.getJob().getFlexMillis(), jobStatus.hasFlexibilityConstraint(), jobStatus.isConstraintSatisfied(2097152), jobStatus.canApplyTransportAffinities(), jobStatus.getNumAppliedFlexibleConstraints(), jobStatus.getNumDroppedFlexibleConstraints(), jobStatus.getFilteredTraceTag(), jobStatus.getFilteredDebugTags());
                    sEnqueuedJwiAtJobStart.logSampleWithUid(jobStatus.getUid(), jobStatus.getWorkCount());
                    java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
                    if (android.os.Trace.isTagEnabled(524288L)) {
                        java.lang.String packageName = jobStatus.getServiceComponent().getPackageName();
                        java.lang.String str = "*job*<" + jobStatus.getSourceUid() + ">" + sourcePackageName;
                        if (!sourcePackageName.equals(packageName)) {
                            str = str + ":" + packageName;
                        }
                        java.lang.String str2 = str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + jobStatus.getServiceComponent().getShortClassName();
                        if (!packageName.equals(jobStatus.serviceProcessName)) {
                            str2 = str2 + "$" + jobStatus.serviceProcessName;
                        }
                        if (jobStatus.getNamespace() != null) {
                            str2 = str2 + "@" + jobStatus.getNamespace();
                        }
                        android.os.Trace.asyncTraceForTrackBegin(524288L, com.android.server.job.JobSchedulerService.TAG, str2 + "#" + jobStatus.getJobId(), getId());
                    }
                    if (jobStatus.getAppTraceTag() != null) {
                        android.os.Trace.asyncTraceForTrackBegin(4096L, com.android.server.job.JobSchedulerService.TAG, jobStatus.getAppTraceTag(), jobStatus.getJobId());
                    }
                    try {
                        this.mBatteryStats.noteJobStart(jobStatus.getBatteryName(), jobStatus.getSourceUid());
                    } catch (android.os.RemoteException e3) {
                    }
                    this.mUsageStatsManagerInternal.setLastJobRunTime(sourcePackageName, jobStatus.getSourceUserId(), this.mExecutionStartTimeElapsed);
                    this.mAvailable = false;
                    this.mStoppedReason = null;
                    this.mStoppedTime = 0L;
                    jobStatus.startedAsExpeditedJob = jobStatus.shouldTreatAsExpeditedJob();
                    jobStatus.startedAsUserInitiatedJob = jobStatus.shouldTreatAsUserInitiatedJob();
                    jobStatus.startedWithForegroundFlag = z5;
                    return true;
                }
                android.util.Slog.e(TAG, "Starting new runnable but context is unavailable > Error.");
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean canGetNetworkInformation(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.getJob().getRequiredNetwork() == null) {
            return false;
        }
        int uid = jobStatus.getUid();
        return !android.app.compat.CompatChanges.isChangeEnabled(271850009L, uid) || hasPermissionForDelivery(uid, jobStatus.getServiceComponent().getPackageName(), "android.permission.ACCESS_NETWORK_STATE");
    }

    private boolean hasPermissionForDelivery(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        return android.content.PermissionChecker.checkPermissionForDataDelivery(this.mContext, str2, -1, i, str, (java.lang.String) null, "network info via JS") == 0;
    }

    private static int getStartActionId(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        switch (jobStatus.getEffectivePriority()) {
            case 100:
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MIN_START;
            case 200:
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_LOW_START;
            case 300:
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_START;
            case 400:
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_HIGH_START;
            case 500:
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MAX_START;
            default:
                android.util.Slog.wtf(TAG, "Unknown priority: " + android.app.job.JobInfo.getPriorityString(jobStatus.getEffectivePriority()));
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_START;
        }
    }

    @android.annotation.Nullable
    com.android.server.job.controllers.JobStatus getRunningJobLocked() {
        return this.mRunningJob;
    }

    int getRunningJobWorkType() {
        return this.mRunningJobWorkType;
    }

    private java.lang.String getRunningJobNameLocked() {
        return this.mRunningJob != null ? this.mRunningJob.toShortString() : "<null>";
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void cancelExecutingJobLocked(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        doCancelLocked(i, i2, str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void markForProcessDeathLocked(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        if (this.mVerb == 4) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Too late to mark for death (verb=" + this.mVerb + "), ignoring.");
                return;
            }
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Marking " + this.mRunningJob.toShortString() + " for death because " + i + ":" + str);
        }
        this.mDeathMarkStopReason = i;
        this.mDeathMarkInternalStopReason = i2;
        this.mDeathMarkDebugReason = str;
        if (this.mParams.getStopReason() == 0) {
            this.mParams.setStopReason(i, i2, str);
        }
    }

    int getPreferredUid() {
        return this.mPreferredUid;
    }

    void clearPreferredUid() {
        this.mPreferredUid = -1;
    }

    int getId() {
        return hashCode();
    }

    long getExecutionStartTimeElapsed() {
        return this.mExecutionStartTimeElapsed;
    }

    long getTimeoutElapsed() {
        return this.mTimeoutElapsed;
    }

    long getRemainingGuaranteedTimeMs(long j) {
        return java.lang.Math.max(0L, (this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis) - j);
    }

    void informOfNetworkChangeLocked(android.net.Network network) {
        if (network != null && this.mRunningJob != null && !canGetNetworkInformation(this.mRunningJob)) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Skipping network change call because of missing permissions");
                return;
            }
            return;
        }
        if (this.mVerb != 2) {
            android.util.Slog.w(TAG, "Sending onNetworkChanged for a job that isn't started. " + this.mRunningJob);
            if (this.mVerb == 0 || this.mVerb == 1) {
                this.mPendingNetworkChange = network;
                return;
            }
            return;
        }
        try {
            this.mParams.setNetwork(network);
            this.mPendingNetworkChange = null;
            this.service.onNetworkChanged(this.mParams);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error sending onNetworkChanged to client.", e);
            closeAndCleanupJobLocked(true, "host crashed when trying to inform of network change");
        }
    }

    boolean isWithinExecutionGuaranteeTime() {
        return com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() < this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean stopIfExecutingLocked(java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, boolean z, int i2, int i3, int i4) {
        com.android.server.job.controllers.JobStatus runningJobLocked = getRunningJobLocked();
        if (runningJobLocked == null) {
            return false;
        }
        if (i == -1 || i == runningJobLocked.getUserId()) {
            if ((str == null || str.equals(runningJobLocked.getSourcePackageName())) && java.util.Objects.equals(str2, runningJobLocked.getNamespace())) {
                if ((!z || i2 == runningJobLocked.getJobId()) && this.mVerb == 2) {
                    this.mParams.setStopReason(i3, i4, "stop from shell");
                    sendStopMessageLocked("stop from shell");
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    android.util.Pair<java.lang.Long, java.lang.Long> getEstimatedNetworkBytes() {
        return android.util.Pair.create(java.lang.Long.valueOf(this.mEstimatedDownloadBytes), java.lang.Long.valueOf(this.mEstimatedUploadBytes));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    android.util.Pair<java.lang.Long, java.lang.Long> getTransferredNetworkBytes() {
        return android.util.Pair.create(java.lang.Long.valueOf(this.mTransferredDownloadBytes), java.lang.Long.valueOf(this.mTransferredUploadBytes));
    }

    void doJobFinished(com.android.server.job.JobServiceContext.JobCallback jobCallback, int i, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (!verifyCallerLocked(jobCallback)) {
                    return;
                }
                this.mParams.setStopReason(0, 10, "app called jobFinished");
                doCallbackLocked(z, "app called jobFinished");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAcknowledgeGetTransferredDownloadBytesMessage(com.android.server.job.JobServiceContext.JobCallback jobCallback, int i, int i2, long j) {
        synchronized (this.mLock) {
            try {
                if (verifyCallerLocked(jobCallback)) {
                    this.mTransferredDownloadBytes = j;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAcknowledgeGetTransferredUploadBytesMessage(com.android.server.job.JobServiceContext.JobCallback jobCallback, int i, int i2, long j) {
        synchronized (this.mLock) {
            try {
                if (verifyCallerLocked(jobCallback)) {
                    this.mTransferredUploadBytes = j;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void doAcknowledgeStopMessage(com.android.server.job.JobServiceContext.JobCallback jobCallback, int i, boolean z) {
        doCallback(jobCallback, z, null);
    }

    void doAcknowledgeStartMessage(com.android.server.job.JobServiceContext.JobCallback jobCallback, int i, boolean z) {
        doCallback(jobCallback, z, "finished start");
    }

    android.app.job.JobWorkItem doDequeueWork(com.android.server.job.JobServiceContext.JobCallback jobCallback, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (!assertCallerLocked(jobCallback)) {
                    return null;
                }
                if (this.mVerb == 3 || this.mVerb == 4) {
                    return null;
                }
                android.app.job.JobWorkItem dequeueWorkLocked = this.mRunningJob.dequeueWorkLocked();
                if (dequeueWorkLocked == null && !this.mRunningJob.hasExecutingWorkLocked()) {
                    this.mParams.setStopReason(0, 10, "last work dequeued");
                    doCallbackLocked(false, "last work dequeued");
                } else if (dequeueWorkLocked != null) {
                    this.mService.mJobs.touchJob(this.mRunningJob);
                }
                return dequeueWorkLocked;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    boolean doCompleteWork(com.android.server.job.JobServiceContext.JobCallback jobCallback, int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (!assertCallerLocked(jobCallback)) {
                    return true;
                }
                if (this.mRunningJob.completeWorkLocked(i2)) {
                    this.mService.mJobs.touchJob(this.mRunningJob);
                    return true;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUpdateEstimatedNetworkBytes(com.android.server.job.JobServiceContext.JobCallback jobCallback, int i, @android.annotation.Nullable android.app.job.JobWorkItem jobWorkItem, long j, long j2) {
        synchronized (this.mLock) {
            try {
                if (verifyCallerLocked(jobCallback)) {
                    com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_bytes_updated", this.mRunningJob.getUid());
                    sUpdatedEstimatedNetworkDownloadKBLogger.logSample(com.android.server.job.JobSchedulerService.safelyScaleBytesToKBForHistogram(j));
                    sUpdatedEstimatedNetworkUploadKBLogger.logSample(com.android.server.job.JobSchedulerService.safelyScaleBytesToKBForHistogram(j2));
                    if (this.mEstimatedDownloadBytes != -1 && j != -1) {
                        if (this.mEstimatedDownloadBytes < j) {
                            com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_download_bytes_increased", this.mRunningJob.getUid());
                        } else if (this.mEstimatedDownloadBytes > j) {
                            com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_download_bytes_decreased", this.mRunningJob.getUid());
                        }
                    }
                    if (this.mEstimatedUploadBytes != -1 && j2 != -1) {
                        if (this.mEstimatedUploadBytes < j2) {
                            com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_upload_bytes_increased", this.mRunningJob.getUid());
                        } else if (this.mEstimatedUploadBytes > j2) {
                            com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_upload_bytes_decreased", this.mRunningJob.getUid());
                        }
                    }
                    this.mEstimatedDownloadBytes = j;
                    this.mEstimatedUploadBytes = j2;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUpdateTransferredNetworkBytes(com.android.server.job.JobServiceContext.JobCallback jobCallback, int i, @android.annotation.Nullable android.app.job.JobWorkItem jobWorkItem, long j, long j2) {
        synchronized (this.mLock) {
            try {
                if (verifyCallerLocked(jobCallback)) {
                    com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_bytes_updated", this.mRunningJob.getUid());
                    sTransferredNetworkDownloadKBHighWaterMarkLogger.logSample(com.android.server.job.JobSchedulerService.safelyScaleBytesToKBForHistogram(j));
                    sTransferredNetworkUploadKBHighWaterMarkLogger.logSample(com.android.server.job.JobSchedulerService.safelyScaleBytesToKBForHistogram(j2));
                    if (this.mTransferredDownloadBytes != -1 && j != -1) {
                        if (this.mTransferredDownloadBytes < j) {
                            com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_download_bytes_increased", this.mRunningJob.getUid());
                        } else if (this.mTransferredDownloadBytes > j) {
                            com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_download_bytes_decreased", this.mRunningJob.getUid());
                        }
                    }
                    if (this.mTransferredUploadBytes != -1 && j2 != -1) {
                        if (this.mTransferredUploadBytes < j2) {
                            com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_upload_bytes_increased", this.mRunningJob.getUid());
                        } else if (this.mTransferredUploadBytes > j2) {
                            com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_upload_bytes_decreased", this.mRunningJob.getUid());
                        }
                    }
                    this.mTransferredDownloadBytes = j;
                    this.mTransferredUploadBytes = j2;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSetNotification(com.android.server.job.JobServiceContext.JobCallback jobCallback, int i, int i2, android.app.Notification notification, int i3) {
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (!verifyCallerLocked(jobCallback)) {
                    return;
                }
                if (callingUid != this.mRunningJob.getUid()) {
                    android.util.Slog.wtfStack(TAG, "Calling UID isn't the same as running job's UID...");
                    throw new java.lang.SecurityException("Can't post notification on behalf of another app");
                }
                this.mNotificationCoordinator.enqueueNotification(this, this.mRunningJob.getServiceComponent().getPackageName(), callingPid, callingUid, i2, notification, i3);
                if (this.mAwaitingNotification) {
                    this.mAwaitingNotification = false;
                    if (this.mVerb == 2) {
                        scheduleOpTimeOutLocked();
                    }
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                com.android.server.job.controllers.JobStatus jobStatus = this.mRunningJob;
                if (jobStatus == null || !componentName.equals(jobStatus.getServiceComponent())) {
                    closeAndCleanupJobLocked(true, "connected for different component");
                } else {
                    this.service = android.app.job.IJobService.Stub.asInterface(iBinder);
                    doServiceBoundLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                if (this.mDeathMarkStopReason != 0) {
                    this.mParams.setStopReason(this.mDeathMarkStopReason, this.mDeathMarkInternalStopReason, this.mDeathMarkDebugReason);
                } else if (this.mRunningJob != null) {
                    com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_unexpected_service_disconnects", this.mRunningJob.getUid());
                }
                closeAndCleanupJobLocked(true, "unexpectedly disconnected");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                if (this.mRunningJob == null) {
                    android.util.Slog.e(TAG, "Binding died for " + componentName.getPackageName() + " but no running job on this context");
                } else if (this.mRunningJob.getServiceComponent().equals(componentName)) {
                    android.util.Slog.e(TAG, "Binding died for " + this.mRunningJob.getSourceUserId() + ":" + componentName.getPackageName());
                } else {
                    android.util.Slog.e(TAG, "Binding died for " + componentName.getPackageName() + " but context is running a different job");
                }
                closeAndCleanupJobLocked(true, "binding died");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                if (this.mRunningJob == null) {
                    android.util.Slog.wtf(TAG, "Got null binding for " + componentName.getPackageName() + " but no running job on this context");
                } else if (this.mRunningJob.getServiceComponent().equals(componentName)) {
                    android.util.Slog.wtf(TAG, "Got null binding for " + this.mRunningJob.getSourceUserId() + ":" + componentName.getPackageName());
                } else {
                    android.util.Slog.wtf(TAG, "Got null binding for " + componentName.getPackageName() + " but context is running a different job");
                }
                closeAndCleanupJobLocked(false, "null binding");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean verifyCallerLocked(com.android.server.job.JobServiceContext.JobCallback jobCallback) {
        if (this.mRunningCallback != jobCallback) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Stale callback received, ignoring.");
                return false;
            }
            return false;
        }
        return true;
    }

    private boolean assertCallerLocked(com.android.server.job.JobServiceContext.JobCallback jobCallback) {
        if (!verifyCallerLocked(jobCallback)) {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            if (!this.mPreviousJobHadSuccessfulFinish && millis - this.mLastUnsuccessfulFinishElapsed < 15000) {
                return false;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("Caller no longer running");
            if (jobCallback.mStoppedReason != null) {
                sb.append(", last stopped ");
                android.util.TimeUtils.formatDuration(millis - jobCallback.mStoppedTime, sb);
                sb.append(" because: ");
                sb.append(jobCallback.mStoppedReason);
            }
            throw new java.lang.SecurityException(sb.toString());
        }
        return true;
    }

    private class JobServiceHandler extends android.os.Handler {
        JobServiceHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    synchronized (com.android.server.job.JobServiceContext.this.mLock) {
                        try {
                            if (message.obj == com.android.server.job.JobServiceContext.this.mRunningCallback) {
                                com.android.server.job.JobServiceContext.this.handleOpTimeoutLocked();
                            } else {
                                com.android.server.job.JobServiceContext.JobCallback jobCallback = (com.android.server.job.JobServiceContext.JobCallback) message.obj;
                                java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
                                sb.append("Ignoring timeout of no longer active job");
                                if (jobCallback.mStoppedReason != null) {
                                    sb.append(", stopped ");
                                    android.util.TimeUtils.formatDuration(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() - jobCallback.mStoppedTime, sb);
                                    sb.append(" because: ");
                                    sb.append(jobCallback.mStoppedReason);
                                }
                                android.util.Slog.w(com.android.server.job.JobServiceContext.TAG, sb.toString());
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    return;
                default:
                    android.util.Slog.e(com.android.server.job.JobServiceContext.TAG, "Unrecognised message: " + message);
                    return;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void doServiceBoundLocked() {
        removeOpTimeOutLocked();
        handleServiceBoundLocked();
    }

    void doCallback(com.android.server.job.JobServiceContext.JobCallback jobCallback, boolean z, java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (!verifyCallerLocked(jobCallback)) {
                    return;
                }
                doCallbackLocked(z, str);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void doCallbackLocked(boolean z, java.lang.String str) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "doCallback of : " + this.mRunningJob + " v:" + VERB_STRINGS[this.mVerb]);
        }
        removeOpTimeOutLocked();
        if (this.mVerb == 1) {
            handleStartedLocked(z);
            return;
        }
        if (this.mVerb == 2 || this.mVerb == 3) {
            handleFinishedLocked(z, str);
        } else if (DEBUG) {
            android.util.Slog.d(TAG, "Unrecognised callback: " + this.mRunningJob);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void doCancelLocked(int i, int i2, @android.annotation.Nullable java.lang.String str) {
        if (this.mVerb == 4 || this.mVerb == 3) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Too late to process cancel for context (verb=" + this.mVerb + "), ignoring.");
                return;
            }
            return;
        }
        if (this.mRunningJob.startedAsExpeditedJob && i == 10) {
            if (com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() < this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis) {
                this.mPendingStopReason = i;
                this.mPendingInternalStopReason = i2;
                this.mPendingDebugStopReason = str;
                return;
            }
        }
        this.mParams.setStopReason(i, i2, str);
        if (i == 2) {
            this.mPreferredUid = this.mRunningJob != null ? this.mRunningJob.getUid() : -1;
        }
        handleCancelLocked(str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void handleServiceBoundLocked() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "handleServiceBound for " + getRunningJobNameLocked());
        }
        if (this.mVerb != 0) {
            android.util.Slog.e(TAG, "Sending onStartJob for a job that isn't pending. " + VERB_STRINGS[this.mVerb]);
            closeAndCleanupJobLocked(false, "started job not pending");
            return;
        }
        if (this.mCancelled) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Job cancelled while waiting for bind to complete. " + this.mRunningJob);
            }
            closeAndCleanupJobLocked(true, "cancelled while waiting for bind");
            return;
        }
        try {
            this.mVerb = 1;
            scheduleOpTimeOutLocked();
            this.service.startJob(this.mParams);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Error sending onStart message to '" + this.mRunningJob.getServiceComponent().getShortClassName() + "' ", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void handleStartedLocked(boolean z) {
        switch (this.mVerb) {
            case 1:
                this.mVerb = 2;
                if (!z) {
                    handleFinishedLocked(false, "onStartJob returned false");
                    break;
                } else if (this.mCancelled) {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "Job cancelled while waiting for onStartJob to complete.");
                    }
                    handleCancelLocked(null);
                    break;
                } else {
                    scheduleOpTimeOutLocked();
                    if (this.mPendingNetworkChange != null && !java.util.Objects.equals(this.mParams.getNetwork(), this.mPendingNetworkChange)) {
                        informOfNetworkChangeLocked(this.mPendingNetworkChange);
                    }
                    if (this.mRunningJob.isUserVisibleJob()) {
                        this.mService.informObserversOfUserVisibleJobChange(this, this.mRunningJob, true);
                        break;
                    }
                }
                break;
            default:
                android.util.Slog.e(TAG, "Handling started job but job wasn't starting! Was " + VERB_STRINGS[this.mVerb] + ".");
                break;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void handleFinishedLocked(boolean z, java.lang.String str) {
        switch (this.mVerb) {
            case 2:
            case 3:
                closeAndCleanupJobLocked(z, str);
                break;
            default:
                android.util.Slog.e(TAG, "Got an execution complete message for a job that wasn't beingexecuted. Was " + VERB_STRINGS[this.mVerb] + ".");
                break;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void handleCancelLocked(@android.annotation.Nullable java.lang.String str) {
        if (com.android.server.job.JobSchedulerService.DEBUG) {
            android.util.Slog.d(TAG, "Handling cancel for: " + this.mRunningJob.getJobId() + " " + VERB_STRINGS[this.mVerb]);
        }
        switch (this.mVerb) {
            case 0:
            case 1:
                this.mCancelled = true;
                applyStoppedReasonLocked(str);
                break;
            case 2:
                sendStopMessageLocked(str);
                break;
            case 3:
                break;
            default:
                android.util.Slog.e(TAG, "Cancelling a job without a valid verb: " + this.mVerb);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void handleOpTimeoutLocked() {
        switch (this.mVerb) {
            case 0:
                onSlowAppResponseLocked(true, true, "job_scheduler.value_cntr_w_uid_slow_app_response_binding", "timed out while binding", "Timed out while trying to bind", false);
                break;
            case 1:
                onSlowAppResponseLocked(false, true, "job_scheduler.value_cntr_w_uid_slow_app_response_on_start_job", "timed out while starting", "No response to onStartJob", android.app.compat.CompatChanges.isChangeEnabled(ANR_PRE_UDC_APIS_ON_SLOW_RESPONSES, this.mRunningJob.getUid()));
                break;
            case 2:
                if (this.mPendingStopReason != 0) {
                    if (this.mService.isReadyToBeExecutedLocked(this.mRunningJob, false)) {
                        this.mPendingStopReason = 0;
                        this.mPendingInternalStopReason = 0;
                        this.mPendingDebugStopReason = null;
                    } else {
                        android.util.Slog.i(TAG, "JS was waiting to stop this job. Sending onStop: " + getRunningJobNameLocked());
                        this.mParams.setStopReason(this.mPendingStopReason, this.mPendingInternalStopReason, this.mPendingDebugStopReason);
                        sendStopMessageLocked(this.mPendingDebugStopReason);
                        break;
                    }
                }
                long j = this.mExecutionStartTimeElapsed + this.mMaxExecutionTimeMillis;
                long j2 = this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis;
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                if (millis < j) {
                    if (millis < j2) {
                        if (this.mAwaitingNotification) {
                            onSlowAppResponseLocked(true, true, "job_scheduler.value_cntr_w_uid_slow_app_response_set_notification", "timed out while stopping", "required notification not provided", true);
                            break;
                        } else {
                            long j3 = millis - this.mLastExecutionDurationStampTimeElapsed;
                            if (j3 < 300000) {
                                android.util.Slog.e(TAG, "Unexpected op timeout while EXECUTING");
                            }
                            this.mRunningJob.incrementCumulativeExecutionTime(j3);
                            this.mService.mJobs.touchJob(this.mRunningJob);
                            this.mLastExecutionDurationStampTimeElapsed = millis;
                            scheduleOpTimeOutLocked();
                            break;
                        }
                    } else {
                        java.lang.String shouldStopRunningJobLocked = this.mJobConcurrencyManager.shouldStopRunningJobLocked(this);
                        if (shouldStopRunningJobLocked != null) {
                            android.util.Slog.i(TAG, "Stopping client after min execution time: " + getRunningJobNameLocked() + " because " + shouldStopRunningJobLocked);
                            this.mParams.setStopReason(4, 3, shouldStopRunningJobLocked);
                            sendStopMessageLocked(shouldStopRunningJobLocked);
                            break;
                        } else {
                            android.util.Slog.i(TAG, "Letting " + getRunningJobNameLocked() + " continue to run past min execution time");
                            scheduleOpTimeOutLocked();
                            break;
                        }
                    }
                } else {
                    android.util.Slog.i(TAG, "Client timed out while executing (no jobFinished received). Sending onStop: " + getRunningJobNameLocked());
                    this.mParams.setStopReason(3, 3, "client timed out");
                    sendStopMessageLocked("timeout while executing");
                    break;
                }
            case 3:
                onSlowAppResponseLocked(true, false, "job_scheduler.value_cntr_w_uid_slow_app_response_on_stop_job", "timed out while stopping", "No response to onStopJob", android.app.compat.CompatChanges.isChangeEnabled(ANR_PRE_UDC_APIS_ON_SLOW_RESPONSES, this.mRunningJob.getUid()));
                break;
            default:
                android.util.Slog.e(TAG, "Handling timeout for an invalid job state: " + getRunningJobNameLocked() + ", dropping.");
                closeAndCleanupJobLocked(false, "invalid timeout");
                break;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void sendStopMessageLocked(@android.annotation.Nullable java.lang.String str) {
        removeOpTimeOutLocked();
        if (this.mVerb != 2) {
            android.util.Slog.e(TAG, "Sending onStopJob for a job that isn't started. " + this.mRunningJob);
            closeAndCleanupJobLocked(false, str);
            return;
        }
        try {
            applyStoppedReasonLocked(str);
            this.mVerb = 3;
            scheduleOpTimeOutLocked();
            this.service.stopJob(this.mParams);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error sending onStopJob to client.", e);
            closeAndCleanupJobLocked(true, "host crashed when trying to stop");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onSlowAppResponseLocked(boolean z, boolean z2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, boolean z3) {
        android.util.Slog.w(TAG, str3 + " for " + getRunningJobNameLocked());
        com.android.modules.expresslog.Counter.logIncrementWithUid(str, this.mRunningJob.getUid());
        if (z2) {
            this.mParams.setStopReason(0, 12, str2);
        }
        if (z3) {
            this.mActivityManagerInternal.appNotResponding(this.mRunningJob.serviceProcessName, this.mRunningJob.getUid(), com.android.internal.os.TimeoutRecord.forJobService(str3));
        }
        closeAndCleanupJobLocked(z, str2);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void closeAndCleanupJobLocked(boolean z, @android.annotation.Nullable java.lang.String str) {
        int i;
        int i2;
        java.lang.String[] strArr;
        if (this.mVerb == 4) {
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Cleaning up " + this.mRunningJob.toShortString() + " reschedule=" + z + " reason=" + str);
        }
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        applyStoppedReasonLocked(str);
        com.android.server.job.controllers.JobStatus jobStatus = this.mRunningJob;
        jobStatus.incrementCumulativeExecutionTime(millis - this.mLastExecutionDurationStampTimeElapsed);
        int stopReason = this.mParams.getStopReason();
        int internalStopReasonCode = this.mParams.getInternalStopReasonCode();
        if (this.mDeathMarkStopReason != 0) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Job marked for death because of " + android.app.job.JobParameters.getInternalReasonCodeDescription(this.mDeathMarkInternalStopReason) + ": " + this.mDeathMarkDebugReason);
            }
            i = this.mDeathMarkStopReason;
            i2 = this.mDeathMarkInternalStopReason;
        } else {
            i = stopReason;
            i2 = internalStopReasonCode;
        }
        this.mPreviousJobHadSuccessfulFinish = internalStopReasonCode == 10;
        if (!this.mPreviousJobHadSuccessfulFinish) {
            this.mLastUnsuccessfulFinishElapsed = millis;
        }
        this.mJobPackageTracker.noteInactive(jobStatus, internalStopReasonCode, str);
        int sourceUid = jobStatus.getSourceUid();
        int[] iArr = jobStatus.isProxyJob() ? new int[]{sourceUid, jobStatus.getUid()} : new int[]{sourceUid};
        if (jobStatus.isProxyJob()) {
            strArr = new java.lang.String[]{null, jobStatus.getSourceTag()};
        } else {
            strArr = new java.lang.String[]{jobStatus.getSourceTag()};
        }
        int i3 = i2;
        com.android.internal.util.FrameworkStatsLog.write(8, iArr, strArr, jobStatus.getBatteryName(), 0, internalStopReasonCode, jobStatus.getStandbyBucket(), jobStatus.getLoggingJobId(), jobStatus.hasChargingConstraint(), jobStatus.hasBatteryNotLowConstraint(), jobStatus.hasStorageNotLowConstraint(), jobStatus.hasTimingDelayConstraint(), jobStatus.hasDeadlineConstraint(), jobStatus.hasIdleConstraint(), jobStatus.hasConnectivityConstraint(), jobStatus.hasContentTriggerConstraint(), jobStatus.isRequestedExpeditedJob(), jobStatus.startedAsExpeditedJob, stopReason, jobStatus.getJob().isPrefetch(), jobStatus.getJob().getPriority(), jobStatus.getEffectivePriority(), jobStatus.getNumPreviousAttempts(), jobStatus.getJob().getMaxExecutionDelayMillis(), this.mParams.isOverrideDeadlineExpired(), jobStatus.isConstraintSatisfied(1), jobStatus.isConstraintSatisfied(2), jobStatus.isConstraintSatisfied(8), jobStatus.isConstraintSatisfied(Integer.MIN_VALUE), jobStatus.isConstraintSatisfied(4), jobStatus.isConstraintSatisfied(268435456), jobStatus.isConstraintSatisfied(67108864), this.mExecutionStartTimeElapsed - jobStatus.enqueueTime, jobStatus.getJob().isUserInitiated(), jobStatus.startedAsUserInitiatedJob, jobStatus.getJob().isPeriodic(), jobStatus.getJob().getMinLatencyMillis(), jobStatus.getEstimatedNetworkDownloadBytes(), jobStatus.getEstimatedNetworkUploadBytes(), jobStatus.getWorkCount(), android.app.ActivityManager.processStateAmToProto(this.mService.getUidProcState(jobStatus.getUid())), jobStatus.getNamespaceHash(), android.net.TrafficStats.getUidRxBytes(jobStatus.getSourceUid()) - this.mInitialDownloadedBytesFromSource, android.net.TrafficStats.getUidTxBytes(jobStatus.getSourceUid()) - this.mInitialUploadedBytesFromSource, android.net.TrafficStats.getUidRxBytes(jobStatus.getUid()) - this.mInitialDownloadedBytesFromCalling, android.net.TrafficStats.getUidTxBytes(jobStatus.getUid()) - this.mInitialUploadedBytesFromCalling, jobStatus.getJob().getIntervalMillis(), jobStatus.getJob().getFlexMillis(), jobStatus.hasFlexibilityConstraint(), jobStatus.isConstraintSatisfied(2097152), jobStatus.canApplyTransportAffinities(), jobStatus.getNumAppliedFlexibleConstraints(), jobStatus.getNumDroppedFlexibleConstraints(), jobStatus.getFilteredTraceTag(), jobStatus.getFilteredDebugTags());
        if (android.os.Trace.isTagEnabled(524288L)) {
            android.os.Trace.asyncTraceForTrackEnd(524288L, com.android.server.job.JobSchedulerService.TAG, getId());
        }
        if (jobStatus.getAppTraceTag() != null) {
            android.os.Trace.asyncTraceForTrackEnd(4096L, com.android.server.job.JobSchedulerService.TAG, jobStatus.getJobId());
        }
        try {
            this.mBatteryStats.noteJobFinish(this.mRunningJob.getBatteryName(), this.mRunningJob.getSourceUid(), internalStopReasonCode);
        } catch (android.os.RemoteException e) {
        }
        if (stopReason == 3) {
            this.mEconomyManagerInternal.noteInstantaneousEvent(this.mRunningJob.getSourceUserId(), this.mRunningJob.getSourcePackageName(), com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_TIMEOUT, java.lang.String.valueOf(this.mRunningJob.getJobId()));
        }
        this.mNotificationCoordinator.removeNotificationAssociation(this, i, jobStatus);
        if (this.mWakeLock != null) {
            this.mWakeLock.release();
        }
        int i4 = this.mRunningJobWorkType;
        this.mContext.unbindService(this);
        this.mWakeLock = null;
        this.mRunningJob = null;
        this.mRunningJobWorkType = 0;
        this.mRunningCallback = null;
        this.mParams = null;
        this.mVerb = 4;
        this.mCancelled = false;
        this.service = null;
        this.mAvailable = true;
        this.mDeathMarkStopReason = 0;
        this.mDeathMarkInternalStopReason = 0;
        this.mDeathMarkDebugReason = null;
        this.mLastExecutionDurationStampTimeElapsed = 0L;
        this.mPendingStopReason = 0;
        this.mPendingInternalStopReason = 0;
        this.mPendingDebugStopReason = null;
        this.mPendingNetworkChange = null;
        removeOpTimeOutLocked();
        if (jobStatus.isUserVisibleJob()) {
            this.mService.informObserversOfUserVisibleJobChange(this, jobStatus, false);
        }
        this.mCompletedListener.onJobCompletedLocked(jobStatus, i, i3, z);
        this.mJobConcurrencyManager.onJobCompletedLocked(this, jobStatus, i4);
    }

    private void applyStoppedReasonLocked(@android.annotation.Nullable java.lang.String str) {
        if (str != null && this.mStoppedReason == null) {
            this.mStoppedReason = str;
            this.mStoppedTime = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            if (this.mRunningCallback != null) {
                this.mRunningCallback.mStoppedReason = this.mStoppedReason;
                this.mRunningCallback.mStoppedTime = this.mStoppedTime;
            }
        }
    }

    private void scheduleOpTimeOutLocked() {
        long j;
        long j2;
        removeOpTimeOutLocked();
        switch (this.mVerb) {
            case 0:
                j = OP_BIND_TIMEOUT_MILLIS;
                break;
            case 1:
            default:
                j = OP_TIMEOUT_MILLIS;
                break;
            case 2:
                long j3 = this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis;
                long j4 = this.mExecutionStartTimeElapsed + this.mMaxExecutionTimeMillis;
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                if (millis < j3) {
                    j2 = j3 - millis;
                } else {
                    j2 = j4 - millis;
                }
                if (this.mAwaitingNotification) {
                    j2 = java.lang.Math.min(j2, NOTIFICATION_TIMEOUT_MILLIS);
                }
                j = java.lang.Math.min(j2, 300000L);
                break;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Scheduling time out for '" + this.mRunningJob.getServiceComponent().getShortClassName() + "' jId: " + this.mParams.getJobId() + ", in " + (j / 1000) + " s");
        }
        this.mCallbackHandler.sendMessageDelayed(this.mCallbackHandler.obtainMessage(0, this.mRunningCallback), j);
        this.mTimeoutElapsed = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() + j;
    }

    private void removeOpTimeOutLocked() {
        this.mCallbackHandler.removeMessages(0);
    }

    void dumpLocked(android.util.IndentingPrintWriter indentingPrintWriter, long j) {
        if (this.mRunningJob == null) {
            if (this.mStoppedReason != null) {
                indentingPrintWriter.print("inactive since ");
                android.util.TimeUtils.formatDuration(this.mStoppedTime, j, indentingPrintWriter);
                indentingPrintWriter.print(", stopped because: ");
                indentingPrintWriter.println(this.mStoppedReason);
                return;
            }
            indentingPrintWriter.println("inactive");
            return;
        }
        indentingPrintWriter.println(this.mRunningJob.toShortString());
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("Running for: ");
        android.util.TimeUtils.formatDuration(j - this.mExecutionStartTimeElapsed, indentingPrintWriter);
        indentingPrintWriter.print(", timeout at: ");
        android.util.TimeUtils.formatDuration(this.mTimeoutElapsed - j, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.print("Remaining execution limits: [");
        android.util.TimeUtils.formatDuration((this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis) - j, indentingPrintWriter);
        indentingPrintWriter.print(", ");
        android.util.TimeUtils.formatDuration((this.mExecutionStartTimeElapsed + this.mMaxExecutionTimeMillis) - j, indentingPrintWriter);
        indentingPrintWriter.print("]");
        if (this.mPendingStopReason != 0) {
            indentingPrintWriter.print(" Pending stop because ");
            indentingPrintWriter.print(this.mPendingStopReason);
            indentingPrintWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            indentingPrintWriter.print(this.mPendingInternalStopReason);
            indentingPrintWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            indentingPrintWriter.print(this.mPendingDebugStopReason);
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }
}
