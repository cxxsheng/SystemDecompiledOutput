package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public final class QuotaController extends com.android.server.job.controllers.StateController {
    private static final java.lang.String ALARM_TAG_CLEANUP = "*job.cleanup*";
    private static final java.lang.String ALARM_TAG_QUOTA_CHECK = "*job.quota_check*";
    private static final boolean DEBUG;
    private static final long MAX_PERIOD_MS = 86400000;
    private static final int MSG_CHECK_PACKAGE = 2;
    private static final int MSG_CLEAN_UP_SESSIONS = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int MSG_END_GRACE_PERIOD = 6;
    private static final int MSG_PROCESS_USAGE_EVENT = 5;

    @com.android.internal.annotations.VisibleForTesting
    static final int MSG_REACHED_EJ_QUOTA = 4;

    @com.android.internal.annotations.VisibleForTesting
    static final int MSG_REACHED_QUOTA = 0;
    private static final int MSG_UID_PROCESS_STATE_CHANGED = 3;
    private static final int SYSTEM_APP_CHECK_FLAGS = 4993024;
    private static final java.lang.String TAG = "JobScheduler.Quota";
    private final android.app.AlarmManager mAlarmManager;
    private final long[] mAllowedTimePerPeriodMs;
    private final com.android.server.job.controllers.BackgroundJobsController mBackgroundJobsController;
    private final long[] mBucketPeriodsMs;
    private final com.android.server.job.controllers.ConnectivityController mConnectivityController;
    private final java.util.function.Consumer<java.util.List<com.android.server.job.controllers.QuotaController.TimedEvent>> mDeleteOldEventsFunctor;
    private long mEJGracePeriodTempAllowlistMs;
    private long mEJGracePeriodTopAppMs;
    private long mEJLimitWindowSizeMs;
    private final long[] mEJLimitsMs;
    private final android.util.SparseArrayMap<java.lang.String, com.android.server.job.controllers.QuotaController.Timer> mEJPkgTimers;
    private long mEJRewardInteractionMs;
    private long mEJRewardNotificationSeenMs;
    private long mEJRewardTopAppMs;
    private final android.util.SparseArrayMap<java.lang.String, com.android.server.job.controllers.QuotaController.ShrinkableDebits> mEJStats;
    private final android.util.SparseArrayMap<java.lang.String, java.util.List<com.android.server.job.controllers.QuotaController.TimedEvent>> mEJTimingSessions;
    private long mEJTopAppTimeChunkSizeMs;
    private final com.android.server.job.controllers.QuotaController.EarliestEndTimeFunctor mEarliestEndTimeFunctor;
    private long mEjLimitAdditionInstallerMs;
    private long mEjLimitAdditionSpecialMs;
    private final android.util.SparseArrayMap<java.lang.String, com.android.server.job.controllers.QuotaController.ExecutionStats[]> mExecutionStatsCache;
    private final android.util.SparseBooleanArray mForegroundUids;
    private final com.android.server.job.controllers.QuotaController.QcHandler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.job.controllers.QuotaController.InQuotaAlarmQueue mInQuotaAlarmQueue;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsEnabled;
    private final int[] mMaxBucketJobCounts;
    private final int[] mMaxBucketSessionCounts;
    private long mMaxExecutionTimeIntoQuotaMs;
    private long mMaxExecutionTimeMs;
    private int mMaxJobCountPerRateLimitingWindow;
    private int mMaxSessionCountPerRateLimitingWindow;
    private long mNextCleanupTimeElapsed;
    private final android.util.SparseArrayMap<java.lang.String, com.android.server.job.controllers.QuotaController.Timer> mPkgTimers;
    private final com.android.server.job.controllers.QuotaController.QcConstants mQcConstants;
    private long mQuotaBufferMs;
    private long mQuotaBumpAdditionalDurationMs;
    private int mQuotaBumpAdditionalJobCount;
    private int mQuotaBumpAdditionalSessionCount;
    private int mQuotaBumpLimit;
    private long mQuotaBumpWindowSizeMs;
    private long mRateLimitingWindowMs;
    private final android.app.AlarmManager.OnAlarmListener mSessionCleanupAlarmListener;
    private final android.util.SparseSetArray<java.lang.String> mSystemInstallers;
    private final android.util.SparseBooleanArray mTempAllowlistCache;
    private final android.util.SparseLongArray mTempAllowlistGraceCache;
    private final com.android.server.job.controllers.QuotaController.TimedEventTooOldPredicate mTimedEventTooOld;
    private final com.android.server.job.controllers.QuotaController.TimerChargingUpdateFunctor mTimerChargingUpdateFunctor;
    private final android.util.SparseArrayMap<java.lang.String, java.util.List<com.android.server.job.controllers.QuotaController.TimedEvent>> mTimingEvents;
    private long mTimingSessionCoalescingDurationMs;
    private final android.util.SparseBooleanArray mTopAppCache;
    private final android.util.SparseLongArray mTopAppGraceCache;
    private final android.util.SparseArrayMap<java.lang.String, com.android.server.job.controllers.QuotaController.TopAppTimer> mTopAppTrackers;
    private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mTopStartedJobs;
    private final android.util.SparseArrayMap<java.lang.String, android.util.ArraySet<com.android.server.job.controllers.JobStatus>> mTrackedJobs;
    private final com.android.server.job.controllers.QuotaController.UidConstraintUpdater mUpdateUidConstraints;

    @com.android.internal.annotations.VisibleForTesting
    interface TimedEvent {
        void dump(android.util.IndentingPrintWriter indentingPrintWriter);

        long getEndTimeElapsed();
    }

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int hashLong(long j) {
        return (int) (j ^ (j >>> 32));
    }

    @com.android.internal.annotations.VisibleForTesting
    static class ExecutionStats {
        public long allowedTimePerPeriodMs;
        public int bgJobCountInMaxPeriod;
        public int bgJobCountInWindow;
        public long executionTimeInMaxPeriodMs;
        public long executionTimeInWindowMs;
        public long expirationTimeElapsed;
        public long inQuotaTimeElapsed;
        public int jobCountInRateLimitingWindow;
        public int jobCountLimit;
        public long jobRateLimitExpirationTimeElapsed;
        public int sessionCountInRateLimitingWindow;
        public int sessionCountInWindow;
        public int sessionCountLimit;
        public long sessionRateLimitExpirationTimeElapsed;
        public long windowSizeMs;

        ExecutionStats() {
        }

        public java.lang.String toString() {
            return "expirationTime=" + this.expirationTimeElapsed + ", allowedTimePerPeriodMs=" + this.allowedTimePerPeriodMs + ", windowSizeMs=" + this.windowSizeMs + ", jobCountLimit=" + this.jobCountLimit + ", sessionCountLimit=" + this.sessionCountLimit + ", executionTimeInWindow=" + this.executionTimeInWindowMs + ", bgJobCountInWindow=" + this.bgJobCountInWindow + ", executionTimeInMaxPeriod=" + this.executionTimeInMaxPeriodMs + ", bgJobCountInMaxPeriod=" + this.bgJobCountInMaxPeriod + ", sessionCountInWindow=" + this.sessionCountInWindow + ", inQuotaTime=" + this.inQuotaTimeElapsed + ", rateLimitJobCountExpirationTime=" + this.jobRateLimitExpirationTimeElapsed + ", rateLimitJobCountWindow=" + this.jobCountInRateLimitingWindow + ", rateLimitSessionCountExpirationTime=" + this.sessionRateLimitExpirationTimeElapsed + ", rateLimitSessionCountWindow=" + this.sessionCountInRateLimitingWindow;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.job.controllers.QuotaController.ExecutionStats)) {
                return false;
            }
            com.android.server.job.controllers.QuotaController.ExecutionStats executionStats = (com.android.server.job.controllers.QuotaController.ExecutionStats) obj;
            return this.expirationTimeElapsed == executionStats.expirationTimeElapsed && this.allowedTimePerPeriodMs == executionStats.allowedTimePerPeriodMs && this.windowSizeMs == executionStats.windowSizeMs && this.jobCountLimit == executionStats.jobCountLimit && this.sessionCountLimit == executionStats.sessionCountLimit && this.executionTimeInWindowMs == executionStats.executionTimeInWindowMs && this.bgJobCountInWindow == executionStats.bgJobCountInWindow && this.executionTimeInMaxPeriodMs == executionStats.executionTimeInMaxPeriodMs && this.sessionCountInWindow == executionStats.sessionCountInWindow && this.bgJobCountInMaxPeriod == executionStats.bgJobCountInMaxPeriod && this.inQuotaTimeElapsed == executionStats.inQuotaTimeElapsed && this.jobRateLimitExpirationTimeElapsed == executionStats.jobRateLimitExpirationTimeElapsed && this.jobCountInRateLimitingWindow == executionStats.jobCountInRateLimitingWindow && this.sessionRateLimitExpirationTimeElapsed == executionStats.sessionRateLimitExpirationTimeElapsed && this.sessionCountInRateLimitingWindow == executionStats.sessionCountInRateLimitingWindow;
        }

        public int hashCode() {
            return ((((((((((((((((((((((((((((0 + com.android.server.job.controllers.QuotaController.hashLong(this.expirationTimeElapsed)) * 31) + com.android.server.job.controllers.QuotaController.hashLong(this.allowedTimePerPeriodMs)) * 31) + com.android.server.job.controllers.QuotaController.hashLong(this.windowSizeMs)) * 31) + com.android.server.job.controllers.QuotaController.hashLong(this.jobCountLimit)) * 31) + com.android.server.job.controllers.QuotaController.hashLong(this.sessionCountLimit)) * 31) + com.android.server.job.controllers.QuotaController.hashLong(this.executionTimeInWindowMs)) * 31) + this.bgJobCountInWindow) * 31) + com.android.server.job.controllers.QuotaController.hashLong(this.executionTimeInMaxPeriodMs)) * 31) + this.bgJobCountInMaxPeriod) * 31) + this.sessionCountInWindow) * 31) + com.android.server.job.controllers.QuotaController.hashLong(this.inQuotaTimeElapsed)) * 31) + com.android.server.job.controllers.QuotaController.hashLong(this.jobRateLimitExpirationTimeElapsed)) * 31) + this.jobCountInRateLimitingWindow) * 31) + com.android.server.job.controllers.QuotaController.hashLong(this.sessionRateLimitExpirationTimeElapsed)) * 31) + this.sessionCountInRateLimitingWindow;
        }
    }

    private class QcUidObserver extends android.app.UidObserver {
        private QcUidObserver() {
        }

        public void onUidStateChanged(int i, int i2, long j, int i3) {
            com.android.server.job.controllers.QuotaController.this.mHandler.obtainMessage(3, i, i2).sendToTarget();
        }
    }

    public QuotaController(@android.annotation.NonNull com.android.server.job.JobSchedulerService jobSchedulerService, @android.annotation.NonNull com.android.server.job.controllers.BackgroundJobsController backgroundJobsController, @android.annotation.NonNull com.android.server.job.controllers.ConnectivityController connectivityController) {
        super(jobSchedulerService);
        this.mTrackedJobs = new android.util.SparseArrayMap<>();
        this.mPkgTimers = new android.util.SparseArrayMap<>();
        this.mEJPkgTimers = new android.util.SparseArrayMap<>();
        this.mTimingEvents = new android.util.SparseArrayMap<>();
        this.mEJTimingSessions = new android.util.SparseArrayMap<>();
        this.mExecutionStatsCache = new android.util.SparseArrayMap<>();
        this.mEJStats = new android.util.SparseArrayMap<>();
        this.mTopAppTrackers = new android.util.SparseArrayMap<>();
        this.mForegroundUids = new android.util.SparseBooleanArray();
        this.mTopStartedJobs = new android.util.ArraySet<>();
        this.mTempAllowlistCache = new android.util.SparseBooleanArray();
        this.mTempAllowlistGraceCache = new android.util.SparseLongArray();
        this.mTopAppCache = new android.util.SparseBooleanArray();
        this.mTopAppGraceCache = new android.util.SparseLongArray();
        this.mAllowedTimePerPeriodMs = new long[]{600000, 600000, 600000, 600000, 0, 600000, 600000};
        this.mMaxExecutionTimeMs = 14400000L;
        this.mQuotaBufferMs = 30000L;
        this.mMaxExecutionTimeIntoQuotaMs = this.mMaxExecutionTimeMs - this.mQuotaBufferMs;
        this.mRateLimitingWindowMs = 60000L;
        this.mMaxJobCountPerRateLimitingWindow = 20;
        this.mMaxSessionCountPerRateLimitingWindow = 20;
        this.mNextCleanupTimeElapsed = 0L;
        this.mSessionCleanupAlarmListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.job.controllers.QuotaController.1
            @Override // android.app.AlarmManager.OnAlarmListener
            public void onAlarm() {
                com.android.server.job.controllers.QuotaController.this.mHandler.obtainMessage(1).sendToTarget();
            }
        };
        this.mBucketPeriodsMs = new long[]{600000, com.android.server.usage.AppStandbyController.ConstantsObserver.DEFAULT_SYSTEM_UPDATE_TIMEOUT, 28800000, 86400000, 0, 86400000, 600000};
        this.mMaxBucketJobCounts = new int[]{75, 120, 200, 48, 0, 10, 75};
        this.mMaxBucketSessionCounts = new int[]{75, 10, 8, 3, 0, 1, 75};
        this.mTimingSessionCoalescingDurationMs = 5000L;
        this.mEJLimitsMs = new long[]{1800000, 1800000, 600000, 600000, 0, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS, 3600000};
        this.mEjLimitAdditionInstallerMs = 1800000L;
        this.mEjLimitAdditionSpecialMs = 900000L;
        this.mEJLimitWindowSizeMs = 86400000L;
        this.mEJTopAppTimeChunkSizeMs = 30000L;
        this.mEJRewardTopAppMs = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
        this.mEJRewardInteractionMs = 15000L;
        this.mEJRewardNotificationSeenMs = 0L;
        this.mEJGracePeriodTempAllowlistMs = 180000L;
        this.mEJGracePeriodTopAppMs = 60000L;
        this.mQuotaBumpAdditionalDurationMs = 60000L;
        this.mQuotaBumpAdditionalJobCount = 2;
        this.mQuotaBumpAdditionalSessionCount = 1;
        this.mQuotaBumpWindowSizeMs = 28800000L;
        this.mQuotaBumpLimit = 8;
        this.mSystemInstallers = new android.util.SparseSetArray<>();
        byte b = 0;
        byte b2 = 0;
        this.mEarliestEndTimeFunctor = new com.android.server.job.controllers.QuotaController.EarliestEndTimeFunctor();
        this.mTimerChargingUpdateFunctor = new com.android.server.job.controllers.QuotaController.TimerChargingUpdateFunctor();
        this.mUpdateUidConstraints = new com.android.server.job.controllers.QuotaController.UidConstraintUpdater();
        this.mTimedEventTooOld = new com.android.server.job.controllers.QuotaController.TimedEventTooOldPredicate();
        this.mDeleteOldEventsFunctor = new java.util.function.Consumer() { // from class: com.android.server.job.controllers.QuotaController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.controllers.QuotaController.this.lambda$new$2((java.util.List) obj);
            }
        };
        this.mHandler = new com.android.server.job.controllers.QuotaController.QcHandler(com.android.server.AppSchedulingModuleThread.get().getLooper());
        this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
        this.mQcConstants = new com.android.server.job.controllers.QuotaController.QcConstants();
        this.mBackgroundJobsController = backgroundJobsController;
        this.mConnectivityController = connectivityController;
        this.mIsEnabled = !this.mConstants.USE_TARE_POLICY;
        this.mInQuotaAlarmQueue = new com.android.server.job.controllers.QuotaController.InQuotaAlarmQueue(this.mContext, com.android.server.AppSchedulingModuleThread.get().getLooper());
        ((com.android.server.usage.AppStandbyInternal) com.android.server.LocalServices.getService(com.android.server.usage.AppStandbyInternal.class)).addListener(new com.android.server.job.controllers.QuotaController.StandbyTracker());
        ((android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class)).registerListener(new com.android.server.job.controllers.QuotaController.UsageEventTracker());
        ((com.android.server.PowerAllowlistInternal) com.android.server.LocalServices.getService(com.android.server.PowerAllowlistInternal.class)).registerTempAllowlistChangeListener(new com.android.server.job.controllers.QuotaController.TempAllowlistTracker());
        try {
            android.app.ActivityManager.getService().registerUidObserver(new com.android.server.job.controllers.QuotaController.QcUidObserver(), 1, 4, (java.lang.String) null);
            android.app.ActivityManager.getService().registerUidObserver(new com.android.server.job.controllers.QuotaController.QcUidObserver(), 1, 2, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void onSystemServicesReady() {
        synchronized (this.mLock) {
            cacheInstallerPackagesLocked(0);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        int sourceUserId = jobStatus.getSourceUserId();
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        android.util.ArraySet arraySet = (android.util.ArraySet) this.mTrackedJobs.get(sourceUserId, sourcePackageName);
        if (arraySet == null) {
            arraySet = new android.util.ArraySet();
            this.mTrackedJobs.add(sourceUserId, sourcePackageName, arraySet);
        }
        arraySet.add(jobStatus);
        jobStatus.setTrackingController(64);
        boolean isWithinQuotaLocked = isWithinQuotaLocked(jobStatus);
        boolean z = false;
        boolean z2 = jobStatus.isRequestedExpeditedJob() && isWithinEJQuotaLocked(jobStatus);
        setConstraintSatisfied(jobStatus, millis, isWithinQuotaLocked, z2);
        if (jobStatus.isRequestedExpeditedJob()) {
            setExpeditedQuotaApproved(jobStatus, millis, z2);
            z = !z2;
        }
        if (!isWithinQuotaLocked || z) {
            maybeScheduleStartAlarmLocked(sourceUserId, sourcePackageName, jobStatus.getEffectiveStandbyBucket());
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r9v0 ??, still in use, count: 1, list:
          (r9v0 ?? I:java.lang.Object) from 0x007f: INVOKE (r8v0 ?? I:android.util.SparseArrayMap), (r0v4 ?? I:int), (r1v1 ?? I:java.lang.Object), (r9v0 ?? I:java.lang.Object) VIRTUAL call: android.util.SparseArrayMap.add(int, java.lang.Object, java.lang.Object):java.lang.Object (LINE:638)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void prepareForExecutionLocked(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r9v0 ??, still in use, count: 1, list:
          (r9v0 ?? I:java.lang.Object) from 0x007f: INVOKE (r8v0 ?? I:android.util.SparseArrayMap), (r0v4 ?? I:int), (r1v1 ?? I:java.lang.Object), (r9v0 ?? I:java.lang.Object) VIRTUAL call: android.util.SparseArrayMap.add(int, java.lang.Object, java.lang.Object):java.lang.Object (LINE:638)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r11v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:238)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
        */

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void unprepareFromExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        com.android.server.job.controllers.QuotaController.Timer timer;
        com.android.server.job.controllers.QuotaController.Timer timer2 = (com.android.server.job.controllers.QuotaController.Timer) this.mPkgTimers.get(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName());
        if (timer2 != null) {
            timer2.stopTrackingJob(jobStatus);
        }
        if (jobStatus.isRequestedExpeditedJob() && (timer = (com.android.server.job.controllers.QuotaController.Timer) this.mEJPkgTimers.get(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName())) != null) {
            timer.stopTrackingJob(jobStatus);
        }
        this.mTopStartedJobs.remove(jobStatus);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(64)) {
            unprepareFromExecutionLocked(jobStatus);
            int sourceUserId = jobStatus.getSourceUserId();
            java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
            android.util.ArraySet arraySet = (android.util.ArraySet) this.mTrackedJobs.get(sourceUserId, sourcePackageName);
            if (arraySet != null && arraySet.remove(jobStatus) && arraySet.size() == 0) {
                this.mInQuotaAlarmQueue.removeAlarmForKey(android.content.pm.UserPackage.of(sourceUserId, sourcePackageName));
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void onAppRemovedLocked(java.lang.String str, int i) {
        if (str == null) {
            android.util.Slog.wtf(TAG, "Told app removed but given null package name.");
            return;
        }
        clearAppStatsLocked(android.os.UserHandle.getUserId(i), str);
        if (this.mService.getPackagesForUidLocked(i) == null) {
            this.mForegroundUids.delete(i);
            this.mTempAllowlistCache.delete(i);
            this.mTempAllowlistGraceCache.delete(i);
            this.mTopAppCache.delete(i);
            this.mTopAppGraceCache.delete(i);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void onUserAddedLocked(int i) {
        cacheInstallerPackagesLocked(i);
    }

    @Override // com.android.server.job.controllers.StateController
    public void onUserRemovedLocked(int i) {
        this.mTrackedJobs.delete(i);
        this.mPkgTimers.delete(i);
        this.mEJPkgTimers.delete(i);
        this.mTimingEvents.delete(i);
        this.mEJTimingSessions.delete(i);
        this.mInQuotaAlarmQueue.removeAlarmsForUserId(i);
        this.mExecutionStatsCache.delete(i);
        this.mEJStats.delete(i);
        this.mSystemInstallers.remove(i);
        this.mTopAppTrackers.delete(i);
    }

    @Override // com.android.server.job.controllers.StateController
    public void onBatteryStateChangedLocked() {
        handleNewChargingStateLocked();
    }

    public void clearAppStatsLocked(int i, @android.annotation.NonNull java.lang.String str) {
        this.mTrackedJobs.delete(i, str);
        com.android.server.job.controllers.QuotaController.Timer timer = (com.android.server.job.controllers.QuotaController.Timer) this.mPkgTimers.delete(i, str);
        if (timer != null && timer.isActive()) {
            android.util.Slog.e(TAG, "clearAppStats called before Timer turned off.");
            timer.dropEverythingLocked();
        }
        com.android.server.job.controllers.QuotaController.Timer timer2 = (com.android.server.job.controllers.QuotaController.Timer) this.mEJPkgTimers.delete(i, str);
        if (timer2 != null && timer2.isActive()) {
            android.util.Slog.e(TAG, "clearAppStats called before EJ Timer turned off.");
            timer2.dropEverythingLocked();
        }
        this.mTimingEvents.delete(i, str);
        this.mEJTimingSessions.delete(i, str);
        this.mInQuotaAlarmQueue.removeAlarmForKey(android.content.pm.UserPackage.of(i, str));
        this.mExecutionStatsCache.delete(i, str);
        this.mEJStats.delete(i, str);
        this.mTopAppTrackers.delete(i, str);
    }

    private void cacheInstallerPackagesLocked(int i) {
        java.util.List installedPackagesAsUser = this.mContext.getPackageManager().getInstalledPackagesAsUser(SYSTEM_APP_CHECK_FLAGS, i);
        for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
            android.content.pm.PackageInfo packageInfo = (android.content.pm.PackageInfo) installedPackagesAsUser.get(size);
            android.content.pm.ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (com.android.internal.util.jobs.ArrayUtils.indexOf(packageInfo.requestedPermissions, "android.permission.INSTALL_PACKAGES") >= 0 && applicationInfo != null && this.mContext.checkPermission("android.permission.INSTALL_PACKAGES", -1, applicationInfo.uid) == 0) {
                this.mSystemInstallers.add(android.os.UserHandle.getUserId(applicationInfo.uid), packageInfo.packageName);
            }
        }
    }

    private boolean isUidInForeground(int i) {
        boolean z;
        if (android.os.UserHandle.isCore(i)) {
            return true;
        }
        synchronized (this.mLock) {
            z = this.mForegroundUids.get(i);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTopStartedJobLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        return this.mTopStartedJobs.contains(jobStatus);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public long getMaxJobExecutionTimeMsLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        boolean z = true;
        if (!jobStatus.shouldTreatAsExpeditedJob()) {
            if (this.mService.isBatteryCharging()) {
                return this.mConstants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
            }
            boolean z2 = this.mTopAppCache.get(jobStatus.getSourceUid()) || isTopStartedJobLocked(jobStatus) || isUidInForeground(jobStatus.getSourceUid());
            if (jobStatus.getEffectivePriority() < 400 && (jobStatus.getFlags() & 2) == 0) {
                z = false;
            }
            if (z2 && z) {
                return this.mConstants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
            }
            return getTimeUntilQuotaConsumedLocked(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName());
        }
        if (this.mService.isBatteryCharging()) {
            return this.mConstants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
        }
        if (jobStatus.getEffectiveStandbyBucket() == 6) {
            return java.lang.Math.max(this.mEJLimitsMs[6] / 2, getTimeUntilEJQuotaConsumedLocked(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName()));
        }
        if (this.mTopAppCache.get(jobStatus.getSourceUid()) || isTopStartedJobLocked(jobStatus)) {
            return java.lang.Math.max(this.mEJLimitsMs[0] / 2, getTimeUntilEJQuotaConsumedLocked(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName()));
        }
        if (isUidInForeground(jobStatus.getSourceUid())) {
            return java.lang.Math.max(this.mEJLimitsMs[1] / 2, getTimeUntilEJQuotaConsumedLocked(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName()));
        }
        return getTimeUntilEJQuotaConsumedLocked(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasTempAllowlistExemptionLocked(int i, int i2, long j) {
        if (i2 == 5 || i2 == 4) {
            return false;
        }
        return this.mTempAllowlistCache.get(i) || j < this.mTempAllowlistGraceCache.get(i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isWithinEJQuotaLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        if (!this.mIsEnabled || isQuotaFreeLocked(jobStatus.getEffectiveStandbyBucket()) || isTopStartedJobLocked(jobStatus) || isUidInForeground(jobStatus.getSourceUid())) {
            return true;
        }
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (hasTempAllowlistExemptionLocked(jobStatus.getSourceUid(), jobStatus.getEffectiveStandbyBucket(), millis)) {
            return true;
        }
        return (this.mTopAppCache.get(jobStatus.getSourceUid()) || (millis > this.mTopAppGraceCache.get(jobStatus.getSourceUid()) ? 1 : (millis == this.mTopAppGraceCache.get(jobStatus.getSourceUid()) ? 0 : -1)) < 0) || 0 < getRemainingEJExecutionTimeLocked(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName());
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.job.controllers.QuotaController.ShrinkableDebits getEJDebitsLocked(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.job.controllers.QuotaController.ShrinkableDebits shrinkableDebits = (com.android.server.job.controllers.QuotaController.ShrinkableDebits) this.mEJStats.get(i, str);
        if (shrinkableDebits == null) {
            com.android.server.job.controllers.QuotaController.ShrinkableDebits shrinkableDebits2 = new com.android.server.job.controllers.QuotaController.ShrinkableDebits(com.android.server.job.JobSchedulerService.standbyBucketForPackage(str, i, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis()));
            this.mEJStats.add(i, str, shrinkableDebits2);
            return shrinkableDebits2;
        }
        return shrinkableDebits;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isWithinQuotaLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        if (this.mIsEnabled) {
            return jobStatus.shouldTreatAsUserInitiatedJob() || isTopStartedJobLocked(jobStatus) || isUidInForeground(jobStatus.getSourceUid()) || isWithinQuotaLocked(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName(), jobStatus.getEffectiveStandbyBucket());
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isQuotaFreeLocked(int i) {
        return this.mService.isBatteryCharging() && i != 5;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean isWithinQuotaLocked(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        if (!this.mIsEnabled) {
            return true;
        }
        if (i2 == 4) {
            return false;
        }
        if (isQuotaFreeLocked(i2)) {
            return true;
        }
        com.android.server.job.controllers.QuotaController.ExecutionStats executionStatsLocked = getExecutionStatsLocked(i, str, i2);
        return getRemainingExecutionTimeLocked(executionStatsLocked) > 0 && isUnderJobCountQuotaLocked(executionStatsLocked, i2) && isUnderSessionCountQuotaLocked(executionStatsLocked, i2);
    }

    private boolean isUnderJobCountQuotaLocked(@android.annotation.NonNull com.android.server.job.controllers.QuotaController.ExecutionStats executionStats, int i) {
        return ((executionStats.jobRateLimitExpirationTimeElapsed > com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() ? 1 : (executionStats.jobRateLimitExpirationTimeElapsed == com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() ? 0 : -1)) <= 0 || executionStats.jobCountInRateLimitingWindow < this.mMaxJobCountPerRateLimitingWindow) && executionStats.bgJobCountInWindow < executionStats.jobCountLimit;
    }

    private boolean isUnderSessionCountQuotaLocked(@android.annotation.NonNull com.android.server.job.controllers.QuotaController.ExecutionStats executionStats, int i) {
        return ((executionStats.sessionRateLimitExpirationTimeElapsed > com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() ? 1 : (executionStats.sessionRateLimitExpirationTimeElapsed == com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() ? 0 : -1)) <= 0 || executionStats.sessionCountInRateLimitingWindow < this.mMaxSessionCountPerRateLimitingWindow) && executionStats.sessionCountInWindow < executionStats.sessionCountLimit;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getRemainingExecutionTimeLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        return getRemainingExecutionTimeLocked(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName(), jobStatus.getEffectiveStandbyBucket());
    }

    @com.android.internal.annotations.VisibleForTesting
    long getRemainingExecutionTimeLocked(int i, @android.annotation.NonNull java.lang.String str) {
        return getRemainingExecutionTimeLocked(i, str, com.android.server.job.JobSchedulerService.standbyBucketForPackage(str, i, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis()));
    }

    private long getRemainingExecutionTimeLocked(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        if (i2 == 4) {
            return 0L;
        }
        return getRemainingExecutionTimeLocked(getExecutionStatsLocked(i, str, i2));
    }

    private long getRemainingExecutionTimeLocked(@android.annotation.NonNull com.android.server.job.controllers.QuotaController.ExecutionStats executionStats) {
        return java.lang.Math.min(executionStats.allowedTimePerPeriodMs - executionStats.executionTimeInWindowMs, this.mMaxExecutionTimeMs - executionStats.executionTimeInMaxPeriodMs);
    }

    @com.android.internal.annotations.VisibleForTesting
    long getRemainingEJExecutionTimeLocked(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.job.controllers.QuotaController.ShrinkableDebits eJDebitsLocked = getEJDebitsLocked(i, str);
        if (eJDebitsLocked.getStandbyBucketLocked() == 4) {
            return 0L;
        }
        long eJLimitMsLocked = getEJLimitMsLocked(i, str, eJDebitsLocked.getStandbyBucketLocked()) - eJDebitsLocked.getTallyLocked();
        java.util.List list = (java.util.List) this.mEJTimingSessions.get(i, str);
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        long j = millis - this.mEJLimitWindowSizeMs;
        if (list != null) {
            while (true) {
                if (list.size() <= 0) {
                    break;
                }
                com.android.server.job.controllers.QuotaController.TimingSession timingSession = (com.android.server.job.controllers.QuotaController.TimingSession) list.get(0);
                if (timingSession.endTimeElapsed < j) {
                    long j2 = timingSession.endTimeElapsed - timingSession.startTimeElapsed;
                    eJLimitMsLocked += j2;
                    eJDebitsLocked.transactLocked(-j2);
                    list.remove(0);
                } else if (timingSession.startTimeElapsed < j) {
                    eJLimitMsLocked += j - timingSession.startTimeElapsed;
                }
            }
        }
        com.android.server.job.controllers.QuotaController.TopAppTimer topAppTimer = (com.android.server.job.controllers.QuotaController.TopAppTimer) this.mTopAppTrackers.get(i, str);
        if (topAppTimer != null && topAppTimer.isActive()) {
            eJLimitMsLocked += topAppTimer.getPendingReward(millis);
        }
        com.android.server.job.controllers.QuotaController.Timer timer = (com.android.server.job.controllers.QuotaController.Timer) this.mEJPkgTimers.get(i, str);
        if (timer == null) {
            return eJLimitMsLocked;
        }
        return eJLimitMsLocked - timer.getCurrentDuration(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
    }

    private long getEJLimitMsLocked(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        long j = this.mEJLimitsMs[i2];
        if (this.mSystemInstallers.contains(i, str)) {
            return j + this.mEjLimitAdditionInstallerMs;
        }
        return j;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getTimeUntilQuotaConsumedLocked(int i, @android.annotation.NonNull java.lang.String str) {
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        int standbyBucketForPackage = com.android.server.job.JobSchedulerService.standbyBucketForPackage(str, i, millis);
        if (standbyBucketForPackage == 4) {
            return 0L;
        }
        java.util.List<com.android.server.job.controllers.QuotaController.TimedEvent> list = (java.util.List) this.mTimingEvents.get(i, str);
        com.android.server.job.controllers.QuotaController.ExecutionStats executionStatsLocked = getExecutionStatsLocked(i, str, standbyBucketForPackage);
        if (list == null || list.size() == 0) {
            if (executionStatsLocked.windowSizeMs == this.mAllowedTimePerPeriodMs[standbyBucketForPackage]) {
                return this.mMaxExecutionTimeMs;
            }
            return this.mAllowedTimePerPeriodMs[standbyBucketForPackage];
        }
        long j = millis - executionStatsLocked.windowSizeMs;
        long j2 = millis - 86400000;
        long j3 = this.mAllowedTimePerPeriodMs[standbyBucketForPackage] - executionStatsLocked.executionTimeInWindowMs;
        long j4 = this.mMaxExecutionTimeMs - executionStatsLocked.executionTimeInMaxPeriodMs;
        if (executionStatsLocked.windowSizeMs == this.mAllowedTimePerPeriodMs[standbyBucketForPackage]) {
            return calculateTimeUntilQuotaConsumedLocked(list, j2, j4, false);
        }
        return java.lang.Math.min(calculateTimeUntilQuotaConsumedLocked(list, j2, j4, false), calculateTimeUntilQuotaConsumedLocked(list, j, j3, true));
    }

    private long calculateTimeUntilQuotaConsumedLocked(@android.annotation.NonNull java.util.List<com.android.server.job.controllers.QuotaController.TimedEvent> list, long j, long j2, boolean z) {
        long j3;
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() - this.mQuotaBumpWindowSizeMs;
        int size = list.size();
        if (z) {
            int i = 0;
            j3 = j2;
            for (int i2 = size - 1; i2 >= 0; i2--) {
                com.android.server.job.controllers.QuotaController.TimedEvent timedEvent = list.get(i2);
                if (timedEvent instanceof com.android.server.job.controllers.QuotaController.QuotaBump) {
                    if (timedEvent.getEndTimeElapsed() < millis) {
                        break;
                    }
                    int i3 = i + 1;
                    if (i >= this.mQuotaBumpLimit) {
                        break;
                    }
                    j3 += this.mQuotaBumpAdditionalDurationMs;
                    i = i3;
                }
            }
        } else {
            j3 = j2;
        }
        long j4 = 0;
        long j5 = j;
        for (int i4 = 0; i4 < size; i4++) {
            com.android.server.job.controllers.QuotaController.TimedEvent timedEvent2 = list.get(i4);
            if (!(timedEvent2 instanceof com.android.server.job.controllers.QuotaController.QuotaBump)) {
                com.android.server.job.controllers.QuotaController.TimingSession timingSession = (com.android.server.job.controllers.QuotaController.TimingSession) timedEvent2;
                if (timingSession.endTimeElapsed < j) {
                    continue;
                } else if (timingSession.startTimeElapsed <= j) {
                    j4 += timingSession.endTimeElapsed - j;
                    j5 = timingSession.endTimeElapsed;
                } else {
                    long j6 = timingSession.startTimeElapsed - j5;
                    if (j6 > j3) {
                        break;
                    }
                    j4 += (timingSession.endTimeElapsed - timingSession.startTimeElapsed) + j6;
                    j3 -= j6;
                    j5 = timingSession.endTimeElapsed;
                }
            }
        }
        long j7 = j4 + j3;
        if (j7 > this.mMaxExecutionTimeMs) {
            android.util.Slog.wtf(TAG, "Calculated quota consumed time too high: " + j7);
        }
        return j7;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getTimeUntilEJQuotaConsumedLocked(int i, @android.annotation.NonNull java.lang.String str) {
        java.util.List list;
        long j;
        long j2;
        long remainingEJExecutionTimeLocked = getRemainingEJExecutionTimeLocked(i, str);
        java.util.List list2 = (java.util.List) this.mEJTimingSessions.get(i, str);
        if (list2 == null || list2.size() == 0) {
            return remainingEJExecutionTimeLocked;
        }
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        long eJLimitMsLocked = getEJLimitMsLocked(i, str, getEJDebitsLocked(i, str).getStandbyBucketLocked());
        long max = java.lang.Math.max(0L, millis - this.mEJLimitWindowSizeMs);
        int i2 = 0;
        long j3 = 0;
        long j4 = 0;
        while (i2 < list2.size()) {
            com.android.server.job.controllers.QuotaController.TimingSession timingSession = (com.android.server.job.controllers.QuotaController.TimingSession) list2.get(i2);
            if (timingSession.endTimeElapsed < max) {
                remainingEJExecutionTimeLocked += timingSession.endTimeElapsed - timingSession.startTimeElapsed;
                list2.remove(i2);
                i2--;
                list = list2;
                j = max;
                j2 = 0;
            } else if (timingSession.startTimeElapsed < max) {
                j4 = timingSession.endTimeElapsed - max;
                list = list2;
                j = max;
                j2 = 0;
            } else {
                long endTimeElapsed = timingSession.startTimeElapsed - (i2 == 0 ? max : ((com.android.server.job.controllers.QuotaController.TimedEvent) list2.get(i2 - 1)).getEndTimeElapsed());
                long min = java.lang.Math.min(remainingEJExecutionTimeLocked, endTimeElapsed);
                j3 += min;
                if (min != endTimeElapsed) {
                    list = list2;
                    j = max;
                } else {
                    list = list2;
                    j = max;
                    j4 += timingSession.endTimeElapsed - timingSession.startTimeElapsed;
                }
                remainingEJExecutionTimeLocked -= min;
                j2 = 0;
                if (remainingEJExecutionTimeLocked <= 0) {
                    break;
                }
            }
            i2++;
            list2 = list;
            max = j;
        }
        return java.lang.Math.min(eJLimitMsLocked, j3 + j4 + remainingEJExecutionTimeLocked);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.job.controllers.QuotaController.ExecutionStats getExecutionStatsLocked(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        return getExecutionStatsLocked(i, str, i2, true);
    }

    @android.annotation.NonNull
    private com.android.server.job.controllers.QuotaController.ExecutionStats getExecutionStatsLocked(int i, @android.annotation.NonNull java.lang.String str, int i2, boolean z) {
        if (i2 == 4) {
            android.util.Slog.wtf(TAG, "getExecutionStatsLocked called for a NEVER app.");
            return new com.android.server.job.controllers.QuotaController.ExecutionStats();
        }
        com.android.server.job.controllers.QuotaController.ExecutionStats[] executionStatsArr = (com.android.server.job.controllers.QuotaController.ExecutionStats[]) this.mExecutionStatsCache.get(i, str);
        if (executionStatsArr == null) {
            executionStatsArr = new com.android.server.job.controllers.QuotaController.ExecutionStats[this.mBucketPeriodsMs.length];
            this.mExecutionStatsCache.add(i, str, executionStatsArr);
        }
        com.android.server.job.controllers.QuotaController.ExecutionStats executionStats = executionStatsArr[i2];
        if (executionStats == null) {
            executionStats = new com.android.server.job.controllers.QuotaController.ExecutionStats();
            executionStatsArr[i2] = executionStats;
        }
        if (z) {
            long j = this.mAllowedTimePerPeriodMs[i2];
            long j2 = this.mBucketPeriodsMs[i2];
            int i3 = this.mMaxBucketJobCounts[i2];
            int i4 = this.mMaxBucketSessionCounts[i2];
            com.android.server.job.controllers.QuotaController.Timer timer = (com.android.server.job.controllers.QuotaController.Timer) this.mPkgTimers.get(i, str);
            if ((timer != null && timer.isActive()) || executionStats.expirationTimeElapsed <= com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() || executionStats.allowedTimePerPeriodMs != j || executionStats.windowSizeMs != j2 || executionStats.jobCountLimit != i3 || executionStats.sessionCountLimit != i4) {
                executionStats.allowedTimePerPeriodMs = j;
                executionStats.windowSizeMs = j2;
                executionStats.jobCountLimit = i3;
                executionStats.sessionCountLimit = i4;
                updateExecutionStatsLocked(i, str, executionStats);
            }
        }
        return executionStats;
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateExecutionStatsLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.job.controllers.QuotaController.ExecutionStats executionStats) {
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        executionStats.executionTimeInWindowMs = 0L;
        executionStats.bgJobCountInWindow = 0;
        executionStats.executionTimeInMaxPeriodMs = 0L;
        executionStats.bgJobCountInMaxPeriod = 0;
        executionStats.sessionCountInWindow = 0;
        if (executionStats.jobCountLimit == 0 || executionStats.sessionCountLimit == 0) {
            executionStats.inQuotaTimeElapsed = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        } else {
            executionStats.inQuotaTimeElapsed = 0L;
        }
        long j7 = executionStats.allowedTimePerPeriodMs - this.mQuotaBufferMs;
        com.android.server.job.controllers.QuotaController.Timer timer = (com.android.server.job.controllers.QuotaController.Timer) this.mPkgTimers.get(i, str);
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        executionStats.expirationTimeElapsed = millis + 86400000;
        if (timer != null && timer.isActive()) {
            long currentDuration = timer.getCurrentDuration(millis);
            executionStats.executionTimeInMaxPeriodMs = currentDuration;
            executionStats.executionTimeInWindowMs = currentDuration;
            int bgJobCount = timer.getBgJobCount();
            executionStats.bgJobCountInMaxPeriod = bgJobCount;
            executionStats.bgJobCountInWindow = bgJobCount;
            executionStats.expirationTimeElapsed = millis;
            if (executionStats.executionTimeInWindowMs >= j7) {
                executionStats.inQuotaTimeElapsed = java.lang.Math.max(executionStats.inQuotaTimeElapsed, (millis - j7) + executionStats.windowSizeMs);
            }
            if (executionStats.executionTimeInMaxPeriodMs >= this.mMaxExecutionTimeIntoQuotaMs) {
                executionStats.inQuotaTimeElapsed = java.lang.Math.max(executionStats.inQuotaTimeElapsed, (millis - this.mMaxExecutionTimeIntoQuotaMs) + 86400000);
            }
            if (executionStats.bgJobCountInWindow >= executionStats.jobCountLimit) {
                executionStats.inQuotaTimeElapsed = java.lang.Math.max(executionStats.inQuotaTimeElapsed, executionStats.windowSizeMs + millis);
            }
        }
        java.util.List list = (java.util.List) this.mTimingEvents.get(i, str);
        if (list == null || list.size() == 0) {
            return;
        }
        long j8 = millis - executionStats.windowSizeMs;
        long j9 = millis - 86400000;
        long j10 = millis - this.mQuotaBumpWindowSizeMs;
        int size = list.size() - 1;
        int i2 = 0;
        long j11 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        while (true) {
            if (size < 0) {
                j = j9;
                j2 = j7;
                break;
            }
            com.android.server.job.controllers.QuotaController.TimedEvent timedEvent = (com.android.server.job.controllers.QuotaController.TimedEvent) list.get(size);
            if (timedEvent.getEndTimeElapsed() >= j10) {
                j = j9;
                if (i2 >= this.mQuotaBumpLimit) {
                    j2 = j7;
                    break;
                }
                if (timedEvent instanceof com.android.server.job.controllers.QuotaController.QuotaBump) {
                    j6 = j7;
                    executionStats.allowedTimePerPeriodMs += this.mQuotaBumpAdditionalDurationMs;
                    executionStats.jobCountLimit += this.mQuotaBumpAdditionalJobCount;
                    executionStats.sessionCountLimit += this.mQuotaBumpAdditionalSessionCount;
                    i2++;
                    j11 = java.lang.Math.min(j11, timedEvent.getEndTimeElapsed() - j10);
                } else {
                    j6 = j7;
                }
                size--;
                j9 = j;
                j7 = j6;
            } else {
                j = j9;
                j2 = j7;
                break;
            }
        }
        com.android.server.job.controllers.QuotaController.TimingSession timingSession = null;
        int i3 = 0;
        int i4 = size;
        while (i4 >= 0) {
            com.android.server.job.controllers.QuotaController.TimedEvent timedEvent2 = (com.android.server.job.controllers.QuotaController.TimedEvent) list.get(i4);
            if (timedEvent2 instanceof com.android.server.job.controllers.QuotaController.QuotaBump) {
                j3 = j8;
            } else {
                com.android.server.job.controllers.QuotaController.TimingSession timingSession2 = (com.android.server.job.controllers.QuotaController.TimingSession) timedEvent2;
                if (j8 >= timingSession2.endTimeElapsed) {
                    j3 = j8;
                } else {
                    if (j8 < timingSession2.startTimeElapsed) {
                        j5 = timingSession2.startTimeElapsed;
                        j11 = java.lang.Math.min(j11, timingSession2.startTimeElapsed - j8);
                    } else {
                        j5 = j8;
                        j11 = 0;
                    }
                    j3 = j8;
                    executionStats.executionTimeInWindowMs += timingSession2.endTimeElapsed - j5;
                    executionStats.bgJobCountInWindow += timingSession2.bgJobCount;
                    if (executionStats.executionTimeInWindowMs >= j2) {
                        executionStats.inQuotaTimeElapsed = java.lang.Math.max(executionStats.inQuotaTimeElapsed, ((j5 + executionStats.executionTimeInWindowMs) - j2) + executionStats.windowSizeMs);
                    }
                    if (executionStats.bgJobCountInWindow >= executionStats.jobCountLimit) {
                        executionStats.inQuotaTimeElapsed = java.lang.Math.max(executionStats.inQuotaTimeElapsed, timingSession2.endTimeElapsed + executionStats.windowSizeMs);
                    }
                    if (!(timingSession != null && timingSession.startTimeElapsed - timingSession2.endTimeElapsed <= this.mTimingSessionCoalescingDurationMs) && (i3 = i3 + 1) >= executionStats.sessionCountLimit) {
                        executionStats.inQuotaTimeElapsed = java.lang.Math.max(executionStats.inQuotaTimeElapsed, timingSession2.endTimeElapsed + executionStats.windowSizeMs);
                    }
                }
                if (j >= timingSession2.startTimeElapsed) {
                    if (j >= timingSession2.endTimeElapsed) {
                        break;
                    }
                    executionStats.executionTimeInMaxPeriodMs += timingSession2.endTimeElapsed - j;
                    executionStats.bgJobCountInMaxPeriod += timingSession2.bgJobCount;
                    if (executionStats.executionTimeInMaxPeriodMs >= this.mMaxExecutionTimeIntoQuotaMs) {
                        executionStats.inQuotaTimeElapsed = java.lang.Math.max(executionStats.inQuotaTimeElapsed, ((j + executionStats.executionTimeInMaxPeriodMs) - this.mMaxExecutionTimeIntoQuotaMs) + 86400000);
                    }
                    j4 = 0;
                } else {
                    executionStats.executionTimeInMaxPeriodMs += timingSession2.endTimeElapsed - timingSession2.startTimeElapsed;
                    executionStats.bgJobCountInMaxPeriod += timingSession2.bgJobCount;
                    j4 = java.lang.Math.min(j11, timingSession2.startTimeElapsed - j);
                    if (executionStats.executionTimeInMaxPeriodMs >= this.mMaxExecutionTimeIntoQuotaMs) {
                        executionStats.inQuotaTimeElapsed = java.lang.Math.max(executionStats.inQuotaTimeElapsed, ((timingSession2.startTimeElapsed + executionStats.executionTimeInMaxPeriodMs) - this.mMaxExecutionTimeIntoQuotaMs) + 86400000);
                    }
                }
                j11 = j4;
                timingSession = timingSession2;
            }
            i4--;
            j8 = j3;
        }
        executionStats.expirationTimeElapsed = millis + j11;
        executionStats.sessionCountInWindow = i3;
    }

    @com.android.internal.annotations.VisibleForTesting
    void invalidateAllExecutionStatsLocked() {
        final long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        this.mExecutionStatsCache.forEach(new java.util.function.Consumer() { // from class: com.android.server.job.controllers.QuotaController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.controllers.QuotaController.lambda$invalidateAllExecutionStatsLocked$0(millis, (com.android.server.job.controllers.QuotaController.ExecutionStats[]) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$invalidateAllExecutionStatsLocked$0(long j, com.android.server.job.controllers.QuotaController.ExecutionStats[] executionStatsArr) {
        if (executionStatsArr != null) {
            for (com.android.server.job.controllers.QuotaController.ExecutionStats executionStats : executionStatsArr) {
                if (executionStats != null) {
                    executionStats.expirationTimeElapsed = j;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void invalidateAllExecutionStatsLocked(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.job.controllers.QuotaController.ExecutionStats[] executionStatsArr = (com.android.server.job.controllers.QuotaController.ExecutionStats[]) this.mExecutionStatsCache.get(i, str);
        if (executionStatsArr != null) {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            for (com.android.server.job.controllers.QuotaController.ExecutionStats executionStats : executionStatsArr) {
                if (executionStats != null) {
                    executionStats.expirationTimeElapsed = millis;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void incrementJobCountLocked(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        com.android.server.job.controllers.QuotaController.ExecutionStats[] executionStatsArr = (com.android.server.job.controllers.QuotaController.ExecutionStats[]) this.mExecutionStatsCache.get(i, str);
        if (executionStatsArr == null) {
            executionStatsArr = new com.android.server.job.controllers.QuotaController.ExecutionStats[this.mBucketPeriodsMs.length];
            this.mExecutionStatsCache.add(i, str, executionStatsArr);
        }
        for (int i3 = 0; i3 < executionStatsArr.length; i3++) {
            com.android.server.job.controllers.QuotaController.ExecutionStats executionStats = executionStatsArr[i3];
            if (executionStats == null) {
                executionStats = new com.android.server.job.controllers.QuotaController.ExecutionStats();
                executionStatsArr[i3] = executionStats;
            }
            if (executionStats.jobRateLimitExpirationTimeElapsed <= millis) {
                executionStats.jobRateLimitExpirationTimeElapsed = this.mRateLimitingWindowMs + millis;
                executionStats.jobCountInRateLimitingWindow = 0;
            }
            executionStats.jobCountInRateLimitingWindow += i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void incrementTimingSessionCountLocked(int i, @android.annotation.NonNull java.lang.String str) {
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        com.android.server.job.controllers.QuotaController.ExecutionStats[] executionStatsArr = (com.android.server.job.controllers.QuotaController.ExecutionStats[]) this.mExecutionStatsCache.get(i, str);
        if (executionStatsArr == null) {
            executionStatsArr = new com.android.server.job.controllers.QuotaController.ExecutionStats[this.mBucketPeriodsMs.length];
            this.mExecutionStatsCache.add(i, str, executionStatsArr);
        }
        for (int i2 = 0; i2 < executionStatsArr.length; i2++) {
            com.android.server.job.controllers.QuotaController.ExecutionStats executionStats = executionStatsArr[i2];
            if (executionStats == null) {
                executionStats = new com.android.server.job.controllers.QuotaController.ExecutionStats();
                executionStatsArr[i2] = executionStats;
            }
            if (executionStats.sessionRateLimitExpirationTimeElapsed <= millis) {
                executionStats.sessionRateLimitExpirationTimeElapsed = this.mRateLimitingWindowMs + millis;
                executionStats.sessionCountInRateLimitingWindow = 0;
            }
            executionStats.sessionCountInRateLimitingWindow++;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void saveTimingSession(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.job.controllers.QuotaController.TimingSession timingSession, boolean z) {
        saveTimingSession(i, str, timingSession, z, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveTimingSession(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.job.controllers.QuotaController.TimingSession timingSession, boolean z, long j) {
        synchronized (this.mLock) {
            try {
                android.util.SparseArrayMap<java.lang.String, java.util.List<com.android.server.job.controllers.QuotaController.TimedEvent>> sparseArrayMap = z ? this.mEJTimingSessions : this.mTimingEvents;
                java.util.List list = (java.util.List) sparseArrayMap.get(i, str);
                if (list == null) {
                    list = new java.util.ArrayList();
                    sparseArrayMap.add(i, str, list);
                }
                list.add(timingSession);
                if (z) {
                    getEJDebitsLocked(i, str).transactLocked((timingSession.endTimeElapsed - timingSession.startTimeElapsed) + j);
                } else {
                    invalidateAllExecutionStatsLocked(i, str);
                    maybeScheduleCleanupAlarmLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void grantRewardForInstantEvent(int i, @android.annotation.NonNull java.lang.String str, long j) {
        if (j == 0) {
            return;
        }
        synchronized (this.mLock) {
            try {
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                if (transactQuotaLocked(i, str, millis, getEJDebitsLocked(i, str), j)) {
                    this.mStateChangedListener.onControllerStateChanged(maybeUpdateConstraintForPkgLocked(millis, i, str));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean transactQuotaLocked(int i, @android.annotation.NonNull java.lang.String str, long j, @android.annotation.NonNull com.android.server.job.controllers.QuotaController.ShrinkableDebits shrinkableDebits, long j2) {
        com.android.server.job.controllers.QuotaController.Timer timer;
        long tallyLocked = shrinkableDebits.getTallyLocked();
        long transactLocked = shrinkableDebits.transactLocked(-j2);
        if (DEBUG) {
            android.util.Slog.d(TAG, "debits overflowed by " + transactLocked);
        }
        boolean z = tallyLocked != shrinkableDebits.getTallyLocked();
        if (transactLocked != 0 && (timer = (com.android.server.job.controllers.QuotaController.Timer) this.mEJPkgTimers.get(i, str)) != null && timer.isActive()) {
            timer.updateDebitAdjustment(j, transactLocked);
            return true;
        }
        return z;
    }

    private final class EarliestEndTimeFunctor implements java.util.function.Consumer<java.util.List<com.android.server.job.controllers.QuotaController.TimedEvent>> {
        public long earliestEndElapsed;

        private EarliestEndTimeFunctor() {
            this.earliestEndElapsed = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }

        @Override // java.util.function.Consumer
        public void accept(java.util.List<com.android.server.job.controllers.QuotaController.TimedEvent> list) {
            if (list != null && list.size() > 0) {
                this.earliestEndElapsed = java.lang.Math.min(this.earliestEndElapsed, list.get(0).getEndTimeElapsed());
            }
        }

        void reset() {
            this.earliestEndElapsed = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void maybeScheduleCleanupAlarmLocked() {
        long j;
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (this.mNextCleanupTimeElapsed > millis) {
            if (DEBUG) {
                android.util.Slog.v(TAG, "Not scheduling cleanup since there's already one at " + this.mNextCleanupTimeElapsed + " (in " + (this.mNextCleanupTimeElapsed - millis) + "ms)");
                return;
            }
            return;
        }
        this.mEarliestEndTimeFunctor.reset();
        this.mTimingEvents.forEach(this.mEarliestEndTimeFunctor);
        this.mEJTimingSessions.forEach(this.mEarliestEndTimeFunctor);
        long j2 = this.mEarliestEndTimeFunctor.earliestEndElapsed;
        if (j2 == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Didn't find a time to schedule cleanup");
                return;
            }
            return;
        }
        long j3 = j2 + 86400000;
        if (j3 - this.mNextCleanupTimeElapsed > 600000) {
            j = j3;
        } else {
            j = this.mNextCleanupTimeElapsed + 600000;
        }
        this.mNextCleanupTimeElapsed = j;
        this.mAlarmManager.set(3, j, ALARM_TAG_CLEANUP, this.mSessionCleanupAlarmListener, this.mHandler);
        if (DEBUG) {
            android.util.Slog.d(TAG, "Scheduled next cleanup for " + this.mNextCleanupTimeElapsed);
        }
    }

    private class TimerChargingUpdateFunctor implements java.util.function.Consumer<com.android.server.job.controllers.QuotaController.Timer> {
        private boolean mIsCharging;
        private long mNowElapsed;

        private TimerChargingUpdateFunctor() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStatus(long j, boolean z) {
            this.mNowElapsed = j;
            this.mIsCharging = z;
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.job.controllers.QuotaController.Timer timer) {
            if (com.android.server.job.JobSchedulerService.standbyBucketForPackage(timer.mPkg.packageName, timer.mPkg.userId, this.mNowElapsed) != 5) {
                timer.onStateChangedLocked(this.mNowElapsed, this.mIsCharging);
            }
        }
    }

    private void handleNewChargingStateLocked() {
        this.mTimerChargingUpdateFunctor.setStatus(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis(), this.mService.isBatteryCharging());
        if (DEBUG) {
            android.util.Slog.d(TAG, "handleNewChargingStateLocked: " + this.mService.isBatteryCharging());
        }
        this.mEJPkgTimers.forEach(this.mTimerChargingUpdateFunctor);
        this.mPkgTimers.forEach(this.mTimerChargingUpdateFunctor);
        com.android.server.AppSchedulingModuleThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.job.controllers.QuotaController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.job.controllers.QuotaController.this.lambda$handleNewChargingStateLocked$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleNewChargingStateLocked$1() {
        synchronized (this.mLock) {
            maybeUpdateAllConstraintsLocked();
        }
    }

    private void maybeUpdateAllConstraintsLocked() {
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = new android.util.ArraySet<>();
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        for (int i = 0; i < this.mTrackedJobs.numMaps(); i++) {
            int keyAt = this.mTrackedJobs.keyAt(i);
            for (int i2 = 0; i2 < this.mTrackedJobs.numElementsForKey(keyAt); i2++) {
                arraySet.addAll((android.util.ArraySet<? extends com.android.server.job.controllers.JobStatus>) maybeUpdateConstraintForPkgLocked(millis, keyAt, (java.lang.String) this.mTrackedJobs.keyAt(i, i2)));
            }
        }
        if (arraySet.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(arraySet);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public android.util.ArraySet<com.android.server.job.controllers.JobStatus> maybeUpdateConstraintForPkgLocked(long j, int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.job.controllers.JobStatus jobStatus;
        boolean z;
        int i2;
        boolean z2;
        android.util.ArraySet arraySet = (android.util.ArraySet) this.mTrackedJobs.get(i, str);
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet2 = new android.util.ArraySet<>();
        if (arraySet == null || arraySet.size() == 0) {
            return arraySet2;
        }
        boolean z3 = false;
        int standbyBucket = ((com.android.server.job.controllers.JobStatus) arraySet.valueAt(0)).getStandbyBucket();
        boolean isWithinQuotaLocked = isWithinQuotaLocked(i, str, standbyBucket);
        boolean z4 = true;
        int size = arraySet.size() - 1;
        boolean z5 = false;
        while (size >= 0) {
            com.android.server.job.controllers.JobStatus jobStatus2 = (com.android.server.job.controllers.JobStatus) arraySet.valueAt(size);
            boolean z6 = (jobStatus2.isRequestedExpeditedJob() && isWithinEJQuotaLocked(jobStatus2)) ? z4 : z3;
            if (isTopStartedJobLocked(jobStatus2)) {
                if (!jobStatus2.setQuotaConstraintSatisfied(j, z4)) {
                    jobStatus = jobStatus2;
                    z = z6;
                    i2 = size;
                    z2 = z4;
                } else {
                    arraySet2.add(jobStatus2);
                    jobStatus = jobStatus2;
                    z = z6;
                    i2 = size;
                    z2 = z4;
                }
            } else {
                if (standbyBucket == 6 || standbyBucket == 0) {
                    jobStatus = jobStatus2;
                    z = z6;
                    i2 = size;
                    z2 = z4;
                } else if (standbyBucket != jobStatus2.getEffectiveStandbyBucket()) {
                    jobStatus = jobStatus2;
                    z = z6;
                    i2 = size;
                    z2 = z4;
                } else {
                    jobStatus = jobStatus2;
                    z = z6;
                    i2 = size;
                    z2 = z4;
                    if (setConstraintSatisfied(jobStatus2, j, isWithinQuotaLocked, z)) {
                        arraySet2.add(jobStatus);
                    }
                }
                if (setConstraintSatisfied(jobStatus, j, isWithinQuotaLocked(jobStatus), z)) {
                    arraySet2.add(jobStatus);
                }
            }
            if (jobStatus.isRequestedExpeditedJob()) {
                boolean z7 = z;
                if (setExpeditedQuotaApproved(jobStatus, j, z7)) {
                    arraySet2.add(jobStatus);
                }
                z5 |= !z7;
            }
            size = i2 - 1;
            z4 = z2;
            z3 = false;
        }
        if (!isWithinQuotaLocked || z5) {
            maybeScheduleStartAlarmLocked(i, str, standbyBucket);
        } else {
            this.mInQuotaAlarmQueue.removeAlarmForKey(android.content.pm.UserPackage.of(i, str));
        }
        return arraySet2;
    }

    private class UidConstraintUpdater implements java.util.function.Consumer<com.android.server.job.controllers.JobStatus> {
        public final android.util.ArraySet<com.android.server.job.controllers.JobStatus> changedJobs;
        private final android.util.SparseArrayMap<java.lang.String, java.lang.Integer> mToScheduleStartAlarms;
        long mUpdateTimeElapsed;

        private UidConstraintUpdater() {
            this.mToScheduleStartAlarms = new android.util.SparseArrayMap<>();
            this.changedJobs = new android.util.ArraySet<>();
            this.mUpdateTimeElapsed = 0L;
        }

        void prepare() {
            this.mUpdateTimeElapsed = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            this.changedJobs.clear();
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.job.controllers.JobStatus jobStatus) {
            boolean z;
            if (jobStatus.isRequestedExpeditedJob()) {
                z = com.android.server.job.controllers.QuotaController.this.isWithinEJQuotaLocked(jobStatus);
            } else {
                z = false;
            }
            if (com.android.server.job.controllers.QuotaController.this.setConstraintSatisfied(jobStatus, this.mUpdateTimeElapsed, com.android.server.job.controllers.QuotaController.this.isWithinQuotaLocked(jobStatus), z)) {
                this.changedJobs.add(jobStatus);
            }
            if (com.android.server.job.controllers.QuotaController.this.setExpeditedQuotaApproved(jobStatus, this.mUpdateTimeElapsed, z)) {
                this.changedJobs.add(jobStatus);
            }
            int sourceUserId = jobStatus.getSourceUserId();
            java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
            int standbyBucket = jobStatus.getStandbyBucket();
            if (z && com.android.server.job.controllers.QuotaController.this.isWithinQuotaLocked(sourceUserId, sourcePackageName, standbyBucket)) {
                com.android.server.job.controllers.QuotaController.this.mInQuotaAlarmQueue.removeAlarmForKey(android.content.pm.UserPackage.of(sourceUserId, sourcePackageName));
            } else {
                this.mToScheduleStartAlarms.add(sourceUserId, sourcePackageName, java.lang.Integer.valueOf(standbyBucket));
            }
        }

        void postProcess() {
            for (int i = 0; i < this.mToScheduleStartAlarms.numMaps(); i++) {
                int keyAt = this.mToScheduleStartAlarms.keyAt(i);
                for (int i2 = 0; i2 < this.mToScheduleStartAlarms.numElementsForKey(keyAt); i2++) {
                    java.lang.String str = (java.lang.String) this.mToScheduleStartAlarms.keyAt(i, i2);
                    com.android.server.job.controllers.QuotaController.this.maybeScheduleStartAlarmLocked(keyAt, str, ((java.lang.Integer) this.mToScheduleStartAlarms.get(keyAt, str)).intValue());
                }
            }
        }

        void reset() {
            this.mToScheduleStartAlarms.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.util.ArraySet<com.android.server.job.controllers.JobStatus> maybeUpdateConstraintForUidLocked(int i) {
        this.mUpdateUidConstraints.prepare();
        this.mService.getJobStore().forEachJobForSourceUid(i, this.mUpdateUidConstraints);
        this.mUpdateUidConstraints.postProcess();
        this.mUpdateUidConstraints.reset();
        return this.mUpdateUidConstraints.changedJobs;
    }

    @com.android.internal.annotations.VisibleForTesting
    void maybeScheduleStartAlarmLocked(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        java.lang.String str2;
        java.lang.String str3;
        long j;
        long j2;
        long j3;
        if (i2 == 4) {
            return;
        }
        android.util.ArraySet arraySet = (android.util.ArraySet) this.mTrackedJobs.get(i, str);
        if (arraySet == null) {
            str2 = TAG;
        } else {
            if (arraySet.size() != 0) {
                com.android.server.job.controllers.QuotaController.ExecutionStats executionStatsLocked = getExecutionStatsLocked(i, str, i2);
                boolean isUnderJobCountQuotaLocked = isUnderJobCountQuotaLocked(executionStatsLocked, i2);
                boolean isUnderSessionCountQuotaLocked = isUnderSessionCountQuotaLocked(executionStatsLocked, i2);
                long remainingEJExecutionTimeLocked = getRemainingEJExecutionTimeLocked(i, str);
                boolean z = executionStatsLocked.executionTimeInWindowMs < this.mAllowedTimePerPeriodMs[i2] && executionStatsLocked.executionTimeInMaxPeriodMs < this.mMaxExecutionTimeMs && isUnderJobCountQuotaLocked && isUnderSessionCountQuotaLocked;
                if (z && remainingEJExecutionTimeLocked > 0) {
                    if (DEBUG) {
                        android.util.Slog.e(TAG, "maybeScheduleStartAlarmLocked called for " + com.android.server.job.controllers.StateController.packageToString(i, str) + " even though it already has " + getRemainingExecutionTimeLocked(i, str, i2) + "ms in its quota.");
                    }
                    this.mInQuotaAlarmQueue.removeAlarmForKey(android.content.pm.UserPackage.of(i, str));
                    this.mHandler.obtainMessage(2, i, 0, str).sendToTarget();
                    return;
                }
                long j4 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                if (z) {
                    str3 = TAG;
                    j = Long.MAX_VALUE;
                } else {
                    j = executionStatsLocked.inQuotaTimeElapsed;
                    if (isUnderJobCountQuotaLocked || executionStatsLocked.bgJobCountInWindow >= executionStatsLocked.jobCountLimit) {
                        str3 = TAG;
                    } else {
                        str3 = TAG;
                        j = java.lang.Math.max(j, executionStatsLocked.jobRateLimitExpirationTimeElapsed);
                    }
                    if (!isUnderSessionCountQuotaLocked && executionStatsLocked.sessionCountInWindow < executionStatsLocked.sessionCountLimit) {
                        j = java.lang.Math.max(j, executionStatsLocked.sessionRateLimitExpirationTimeElapsed);
                    }
                }
                if (remainingEJExecutionTimeLocked <= 0) {
                    long eJLimitMsLocked = getEJLimitMsLocked(i, str, i2) - this.mQuotaBufferMs;
                    com.android.server.job.controllers.QuotaController.Timer timer = (com.android.server.job.controllers.QuotaController.Timer) this.mEJPkgTimers.get(i, str);
                    if (timer == null || !timer.isActive()) {
                        j3 = 0;
                    } else {
                        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                        long currentDuration = timer.getCurrentDuration(millis) + 0;
                        if (currentDuration >= eJLimitMsLocked) {
                            j3 = currentDuration;
                            j4 = (millis - eJLimitMsLocked) + this.mEJLimitWindowSizeMs;
                        } else {
                            j3 = currentDuration;
                        }
                    }
                    java.util.List list = (java.util.List) this.mEJTimingSessions.get(i, str);
                    if (list != null) {
                        int size = list.size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            com.android.server.job.controllers.QuotaController.TimingSession timingSession = (com.android.server.job.controllers.QuotaController.TimingSession) list.get(size);
                            j3 += timingSession.endTimeElapsed - timingSession.startTimeElapsed;
                            if (j3 >= eJLimitMsLocked) {
                                j4 = timingSession.startTimeElapsed + (j3 - eJLimitMsLocked) + this.mEJLimitWindowSizeMs;
                                break;
                            }
                            size--;
                        }
                        j2 = j4;
                    } else {
                        if ((timer == null || !timer.isActive()) && z) {
                            android.util.Slog.wtf(str3, com.android.server.job.controllers.StateController.packageToString(i, str) + " has 0 EJ quota without running anything");
                            return;
                        }
                        j2 = j4;
                    }
                } else {
                    j2 = Long.MAX_VALUE;
                }
                long min = java.lang.Math.min(j, j2);
                if (min <= com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis()) {
                    long millis2 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                    android.util.Slog.wtf(str3, "In quota time is " + (millis2 - min) + "ms old. Now=" + millis2 + ", inQuotaTime=" + min + ": " + executionStatsLocked);
                    min = com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS + millis2;
                }
                this.mInQuotaAlarmQueue.addAlarm(android.content.pm.UserPackage.of(i, str), min);
                return;
            }
            str2 = TAG;
        }
        android.util.Slog.e(str2, "maybeScheduleStartAlarmLocked called for " + com.android.server.job.controllers.StateController.packageToString(i, str) + " that has no jobs");
        this.mInQuotaAlarmQueue.removeAlarmForKey(android.content.pm.UserPackage.of(i, str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setConstraintSatisfied(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, long j, boolean z, boolean z2) {
        if (jobStatus.startedAsExpeditedJob) {
            z = z2;
        } else if (!this.mService.isCurrentlyRunningLocked(jobStatus)) {
            z = z2 || z;
        }
        if (!z && jobStatus.getWhenStandbyDeferred() == 0) {
            jobStatus.setWhenStandbyDeferred(j);
        }
        return jobStatus.setQuotaConstraintSatisfied(j, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setExpeditedQuotaApproved(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, long j, boolean z) {
        if (jobStatus.setExpeditedJobQuotaApproved(j, z)) {
            this.mBackgroundJobsController.evaluateStateLocked(jobStatus);
            this.mConnectivityController.evaluateStateLocked(jobStatus);
            if (z && jobStatus.isReady()) {
                this.mStateChangedListener.onRunJobNow(jobStatus);
                return true;
            }
            return true;
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class TimingSession implements com.android.server.job.controllers.QuotaController.TimedEvent {
        public final int bgJobCount;
        public final long endTimeElapsed;
        private final int mHashCode;
        public final long startTimeElapsed;

        TimingSession(long j, long j2, int i) {
            this.startTimeElapsed = j;
            this.endTimeElapsed = j2;
            this.bgJobCount = i;
            this.mHashCode = ((((0 + com.android.server.job.controllers.QuotaController.hashLong(this.startTimeElapsed)) * 31) + com.android.server.job.controllers.QuotaController.hashLong(this.endTimeElapsed)) * 31) + i;
        }

        @Override // com.android.server.job.controllers.QuotaController.TimedEvent
        public long getEndTimeElapsed() {
            return this.endTimeElapsed;
        }

        public java.lang.String toString() {
            return "TimingSession{" + this.startTimeElapsed + "->" + this.endTimeElapsed + ", " + this.bgJobCount + "}";
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.job.controllers.QuotaController.TimingSession)) {
                return false;
            }
            com.android.server.job.controllers.QuotaController.TimingSession timingSession = (com.android.server.job.controllers.QuotaController.TimingSession) obj;
            return this.startTimeElapsed == timingSession.startTimeElapsed && this.endTimeElapsed == timingSession.endTimeElapsed && this.bgJobCount == timingSession.bgJobCount;
        }

        public int hashCode() {
            return this.mHashCode;
        }

        @Override // com.android.server.job.controllers.QuotaController.TimedEvent
        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.print(this.startTimeElapsed);
            indentingPrintWriter.print(" -> ");
            indentingPrintWriter.print(this.endTimeElapsed);
            indentingPrintWriter.print(" (");
            indentingPrintWriter.print(this.endTimeElapsed - this.startTimeElapsed);
            indentingPrintWriter.print("), ");
            indentingPrintWriter.print(this.bgJobCount);
            indentingPrintWriter.print(" bg jobs.");
            indentingPrintWriter.println();
        }

        public void dump(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, this.startTimeElapsed);
            protoOutputStream.write(1112396529666L, this.endTimeElapsed);
            protoOutputStream.write(1120986464259L, this.bgJobCount);
            protoOutputStream.end(start);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class QuotaBump implements com.android.server.job.controllers.QuotaController.TimedEvent {
        public final long eventTimeElapsed;

        QuotaBump(long j) {
            this.eventTimeElapsed = j;
        }

        @Override // com.android.server.job.controllers.QuotaController.TimedEvent
        public long getEndTimeElapsed() {
            return this.eventTimeElapsed;
        }

        @Override // com.android.server.job.controllers.QuotaController.TimedEvent
        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.print("Quota bump @ ");
            indentingPrintWriter.print(this.eventTimeElapsed);
            indentingPrintWriter.println();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class ShrinkableDebits {
        private long mDebitTally = 0;
        private int mStandbyBucket;

        ShrinkableDebits(int i) {
            this.mStandbyBucket = i;
        }

        long getTallyLocked() {
            return this.mDebitTally;
        }

        long transactLocked(long j) {
            long j2;
            if (j >= 0 || java.lang.Math.abs(j) <= this.mDebitTally) {
                j2 = 0;
            } else {
                j2 = this.mDebitTally + j;
            }
            this.mDebitTally = java.lang.Math.max(0L, this.mDebitTally + j);
            return j2;
        }

        void setStandbyBucketLocked(int i) {
            this.mStandbyBucket = i;
        }

        int getStandbyBucketLocked() {
            return this.mStandbyBucket;
        }

        public java.lang.String toString() {
            return "ShrinkableDebits { debit tally: " + this.mDebitTally + ", bucket: " + this.mStandbyBucket + " }";
        }

        void dumpLocked(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println(toString());
        }
    }

    private final class Timer {
        private int mBgJobCount;
        private long mDebitAdjustment;
        private final android.content.pm.UserPackage mPkg;
        private final boolean mRegularJobTimer;
        private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mRunningBgJobs = new android.util.ArraySet<>();
        private long mStartTimeElapsed;
        private final int mUid;

        Timer(int i, int i2, java.lang.String str, boolean z) {
            this.mPkg = android.content.pm.UserPackage.of(i2, str);
            this.mUid = i;
            this.mRegularJobTimer = z;
        }

        void startTrackingJobLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
            if (jobStatus.shouldTreatAsUserInitiatedJob()) {
                if (com.android.server.job.controllers.QuotaController.DEBUG) {
                    android.util.Slog.v(com.android.server.job.controllers.QuotaController.TAG, "Timer ignoring " + jobStatus.toShortString() + " because it's user-initiated");
                    return;
                }
                return;
            }
            if (com.android.server.job.controllers.QuotaController.this.isTopStartedJobLocked(jobStatus)) {
                if (com.android.server.job.controllers.QuotaController.DEBUG) {
                    android.util.Slog.v(com.android.server.job.controllers.QuotaController.TAG, "Timer ignoring " + jobStatus.toShortString() + " because isTop");
                    return;
                }
                return;
            }
            if (com.android.server.job.controllers.QuotaController.DEBUG) {
                android.util.Slog.v(com.android.server.job.controllers.QuotaController.TAG, "Starting to track " + jobStatus.toShortString());
            }
            if (this.mRunningBgJobs.add(jobStatus) && shouldTrackLocked()) {
                this.mBgJobCount++;
                if (this.mRegularJobTimer) {
                    com.android.server.job.controllers.QuotaController.this.incrementJobCountLocked(this.mPkg.userId, this.mPkg.packageName, 1);
                }
                if (this.mRunningBgJobs.size() == 1) {
                    this.mStartTimeElapsed = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                    this.mDebitAdjustment = 0L;
                    if (this.mRegularJobTimer) {
                        com.android.server.job.controllers.QuotaController.this.invalidateAllExecutionStatsLocked(this.mPkg.userId, this.mPkg.packageName);
                    }
                    scheduleCutoff();
                }
            }
        }

        void stopTrackingJob(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
            if (com.android.server.job.controllers.QuotaController.DEBUG) {
                android.util.Slog.v(com.android.server.job.controllers.QuotaController.TAG, "Stopping tracking of " + jobStatus.toShortString());
            }
            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                try {
                    if (this.mRunningBgJobs.size() == 0) {
                        if (com.android.server.job.controllers.QuotaController.DEBUG) {
                            android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, "Timer isn't tracking any jobs but still told to stop");
                        }
                        return;
                    }
                    long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                    int standbyBucketForPackage = com.android.server.job.JobSchedulerService.standbyBucketForPackage(this.mPkg.packageName, this.mPkg.userId, millis);
                    if (this.mRunningBgJobs.remove(jobStatus) && this.mRunningBgJobs.size() == 0 && !com.android.server.job.controllers.QuotaController.this.isQuotaFreeLocked(standbyBucketForPackage)) {
                        emitSessionLocked(millis);
                        cancelCutoff();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void updateDebitAdjustment(long j, long j2) {
            this.mDebitAdjustment = java.lang.Math.max(this.mDebitAdjustment + j2, this.mStartTimeElapsed - j);
        }

        void dropEverythingLocked() {
            this.mRunningBgJobs.clear();
            cancelCutoff();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void emitSessionLocked(long j) {
            if (this.mBgJobCount <= 0) {
                return;
            }
            com.android.server.job.controllers.QuotaController.this.saveTimingSession(this.mPkg.userId, this.mPkg.packageName, new com.android.server.job.controllers.QuotaController.TimingSession(this.mStartTimeElapsed, j, this.mBgJobCount), !this.mRegularJobTimer, this.mDebitAdjustment);
            this.mBgJobCount = 0;
            cancelCutoff();
            if (this.mRegularJobTimer) {
                com.android.server.job.controllers.QuotaController.this.incrementTimingSessionCountLocked(this.mPkg.userId, this.mPkg.packageName);
            }
        }

        public boolean isActive() {
            boolean z;
            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                z = this.mBgJobCount > 0;
            }
            return z;
        }

        boolean isRunning(com.android.server.job.controllers.JobStatus jobStatus) {
            return this.mRunningBgJobs.contains(jobStatus);
        }

        long getCurrentDuration(long j) {
            long j2;
            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                j2 = !isActive() ? 0L : (j - this.mStartTimeElapsed) + this.mDebitAdjustment;
            }
            return j2;
        }

        int getBgJobCount() {
            int i;
            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                i = this.mBgJobCount;
            }
            return i;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean shouldTrackLocked() {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            int standbyBucketForPackage = com.android.server.job.JobSchedulerService.standbyBucketForPackage(this.mPkg.packageName, this.mPkg.userId, millis);
            boolean z = !this.mRegularJobTimer && com.android.server.job.controllers.QuotaController.this.hasTempAllowlistExemptionLocked(this.mUid, standbyBucketForPackage, millis);
            boolean z2 = !this.mRegularJobTimer && (com.android.server.job.controllers.QuotaController.this.mTopAppCache.get(this.mUid) || millis < com.android.server.job.controllers.QuotaController.this.mTopAppGraceCache.get(this.mUid));
            if (com.android.server.job.controllers.QuotaController.DEBUG) {
                android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, "quotaFree=" + com.android.server.job.controllers.QuotaController.this.isQuotaFreeLocked(standbyBucketForPackage) + " isFG=" + com.android.server.job.controllers.QuotaController.this.mForegroundUids.get(this.mUid) + " tempEx=" + z + " topEx=" + z2);
            }
            return (com.android.server.job.controllers.QuotaController.this.isQuotaFreeLocked(standbyBucketForPackage) || com.android.server.job.controllers.QuotaController.this.mForegroundUids.get(this.mUid) || z || z2) ? false : true;
        }

        void onStateChangedLocked(long j, boolean z) {
            if (z) {
                emitSessionLocked(j);
                return;
            }
            if (!isActive() && shouldTrackLocked() && this.mRunningBgJobs.size() > 0) {
                this.mStartTimeElapsed = j;
                this.mDebitAdjustment = 0L;
                this.mBgJobCount = this.mRunningBgJobs.size();
                if (this.mRegularJobTimer) {
                    com.android.server.job.controllers.QuotaController.this.incrementJobCountLocked(this.mPkg.userId, this.mPkg.packageName, this.mBgJobCount);
                    com.android.server.job.controllers.QuotaController.this.invalidateAllExecutionStatsLocked(this.mPkg.userId, this.mPkg.packageName);
                }
                scheduleCutoff();
            }
        }

        void rescheduleCutoff() {
            cancelCutoff();
            scheduleCutoff();
        }

        private void scheduleCutoff() {
            long timeUntilEJQuotaConsumedLocked;
            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                try {
                    if (isActive()) {
                        android.os.Message obtainMessage = com.android.server.job.controllers.QuotaController.this.mHandler.obtainMessage(this.mRegularJobTimer ? 0 : 4, this.mPkg);
                        if (this.mRegularJobTimer) {
                            timeUntilEJQuotaConsumedLocked = com.android.server.job.controllers.QuotaController.this.getTimeUntilQuotaConsumedLocked(this.mPkg.userId, this.mPkg.packageName);
                        } else {
                            timeUntilEJQuotaConsumedLocked = com.android.server.job.controllers.QuotaController.this.getTimeUntilEJQuotaConsumedLocked(this.mPkg.userId, this.mPkg.packageName);
                        }
                        if (com.android.server.job.controllers.QuotaController.DEBUG) {
                            java.lang.StringBuilder sb = new java.lang.StringBuilder();
                            sb.append(this.mRegularJobTimer ? "Regular job" : "EJ");
                            sb.append(" for ");
                            sb.append(this.mPkg);
                            sb.append(" has ");
                            sb.append(timeUntilEJQuotaConsumedLocked);
                            sb.append("ms left.");
                            android.util.Slog.i(com.android.server.job.controllers.QuotaController.TAG, sb.toString());
                        }
                        com.android.server.job.controllers.QuotaController.this.mHandler.sendMessageDelayed(obtainMessage, timeUntilEJQuotaConsumedLocked);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void cancelCutoff() {
            com.android.server.job.controllers.QuotaController.this.mHandler.removeMessages(this.mRegularJobTimer ? 0 : 4, this.mPkg);
        }

        public void dump(android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
            indentingPrintWriter.print("Timer<");
            indentingPrintWriter.print(this.mRegularJobTimer ? "REG" : "EJ");
            indentingPrintWriter.print(">{");
            indentingPrintWriter.print(this.mPkg);
            indentingPrintWriter.print("} ");
            if (isActive()) {
                indentingPrintWriter.print("started at ");
                indentingPrintWriter.print(this.mStartTimeElapsed);
                indentingPrintWriter.print(" (");
                indentingPrintWriter.print(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() - this.mStartTimeElapsed);
                indentingPrintWriter.print("ms ago)");
            } else {
                indentingPrintWriter.print("NOT active");
            }
            indentingPrintWriter.print(", ");
            indentingPrintWriter.print(this.mBgJobCount);
            indentingPrintWriter.print(" running bg jobs");
            if (!this.mRegularJobTimer) {
                indentingPrintWriter.print(" (debit adj=");
                indentingPrintWriter.print(this.mDebitAdjustment);
                indentingPrintWriter.print(")");
            }
            indentingPrintWriter.println();
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mRunningBgJobs.size(); i++) {
                com.android.server.job.controllers.JobStatus valueAt = this.mRunningBgJobs.valueAt(i);
                if (predicate.test(valueAt)) {
                    indentingPrintWriter.println(valueAt.toShortString());
                }
            }
            indentingPrintWriter.decreaseIndent();
        }

        public void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1133871366146L, isActive());
            protoOutputStream.write(1112396529667L, this.mStartTimeElapsed);
            protoOutputStream.write(1120986464260L, this.mBgJobCount);
            for (int i = 0; i < this.mRunningBgJobs.size(); i++) {
                com.android.server.job.controllers.JobStatus valueAt = this.mRunningBgJobs.valueAt(i);
                if (predicate.test(valueAt)) {
                    valueAt.writeToShortProto(protoOutputStream, 2246267895813L);
                }
            }
            protoOutputStream.end(start);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class TopAppTimer {
        private final android.util.SparseArray<android.app.usage.UsageEvents.Event> mActivities = new android.util.SparseArray<>();
        private final android.content.pm.UserPackage mPkg;
        private long mStartTimeElapsed;

        TopAppTimer(int i, java.lang.String str) {
            this.mPkg = android.content.pm.UserPackage.of(i, str);
        }

        private int calculateTimeChunks(long j) {
            long j2 = j - this.mStartTimeElapsed;
            int i = (int) (j2 / com.android.server.job.controllers.QuotaController.this.mEJTopAppTimeChunkSizeMs);
            if (j2 % com.android.server.job.controllers.QuotaController.this.mEJTopAppTimeChunkSizeMs >= 1000) {
                return i + 1;
            }
            return i;
        }

        long getPendingReward(long j) {
            return com.android.server.job.controllers.QuotaController.this.mEJRewardTopAppMs * calculateTimeChunks(j);
        }

        void processEventLocked(@android.annotation.NonNull android.app.usage.UsageEvents.Event event) {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            switch (event.getEventType()) {
                case 1:
                    if (this.mActivities.size() == 0) {
                        this.mStartTimeElapsed = millis;
                    }
                    this.mActivities.put(event.mInstanceId, event);
                    break;
                case 2:
                case 23:
                case 24:
                    if (((android.app.usage.UsageEvents.Event) this.mActivities.removeReturnOld(event.mInstanceId)) != null && this.mActivities.size() == 0) {
                        long pendingReward = getPendingReward(millis);
                        if (com.android.server.job.controllers.QuotaController.DEBUG) {
                            android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, "Crediting " + this.mPkg + " " + pendingReward + "ms for " + calculateTimeChunks(millis) + " time chunks");
                        }
                        if (com.android.server.job.controllers.QuotaController.this.transactQuotaLocked(this.mPkg.userId, this.mPkg.packageName, millis, com.android.server.job.controllers.QuotaController.this.getEJDebitsLocked(this.mPkg.userId, this.mPkg.packageName), pendingReward)) {
                            com.android.server.job.controllers.QuotaController.this.mStateChangedListener.onControllerStateChanged(com.android.server.job.controllers.QuotaController.this.maybeUpdateConstraintForPkgLocked(millis, this.mPkg.userId, this.mPkg.packageName));
                            break;
                        }
                    }
                    break;
            }
        }

        boolean isActive() {
            boolean z;
            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                z = this.mActivities.size() > 0;
            }
            return z;
        }

        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.print("TopAppTimer{");
            indentingPrintWriter.print(this.mPkg);
            indentingPrintWriter.print("} ");
            if (isActive()) {
                indentingPrintWriter.print("started at ");
                indentingPrintWriter.print(this.mStartTimeElapsed);
                indentingPrintWriter.print(" (");
                indentingPrintWriter.print(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() - this.mStartTimeElapsed);
                indentingPrintWriter.print("ms ago)");
            } else {
                indentingPrintWriter.print("NOT active");
            }
            indentingPrintWriter.println();
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mActivities.size(); i++) {
                indentingPrintWriter.println(this.mActivities.valueAt(i).getClassName());
            }
            indentingPrintWriter.decreaseIndent();
        }

        public void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1133871366146L, isActive());
            protoOutputStream.write(1112396529667L, this.mStartTimeElapsed);
            protoOutputStream.write(1120986464260L, this.mActivities.size());
            protoOutputStream.end(start);
        }
    }

    final class StandbyTracker extends com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener {
        StandbyTracker() {
        }

        public void onAppIdleStateChanged(final java.lang.String str, final int i, boolean z, final int i2, int i3) {
            com.android.server.AppSchedulingModuleThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.job.controllers.QuotaController$StandbyTracker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.job.controllers.QuotaController.StandbyTracker.this.lambda$onAppIdleStateChanged$0(i2, i, str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAppIdleStateChanged$0(int i, int i2, java.lang.String str) {
            com.android.server.job.controllers.QuotaController.this.updateStandbyBucket(i2, str, com.android.server.job.JobSchedulerService.standbyBucketToBucketIndex(i));
        }

        public void triggerTemporaryQuotaBump(java.lang.String str, int i) {
            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                java.util.List list = (java.util.List) com.android.server.job.controllers.QuotaController.this.mTimingEvents.get(i, str);
                if (list == null || list.size() == 0) {
                    return;
                }
                list.add(new com.android.server.job.controllers.QuotaController.QuotaBump(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis()));
                com.android.server.job.controllers.QuotaController.this.invalidateAllExecutionStatsLocked(i, str);
                com.android.server.job.controllers.QuotaController.this.mHandler.obtainMessage(2, i, 0, str).sendToTarget();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateStandbyBucket(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        if (DEBUG) {
            android.util.Slog.i(TAG, "Moving pkg " + com.android.server.job.controllers.StateController.packageToString(i, str) + " to bucketIndex " + i2);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                com.android.server.job.controllers.QuotaController.ShrinkableDebits shrinkableDebits = (com.android.server.job.controllers.QuotaController.ShrinkableDebits) this.mEJStats.get(i, str);
                if (shrinkableDebits != null) {
                    shrinkableDebits.setStandbyBucketLocked(i2);
                }
                android.util.ArraySet arraySet = (android.util.ArraySet) this.mTrackedJobs.get(i, str);
                if (arraySet == null || arraySet.size() == 0) {
                    return;
                }
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    com.android.server.job.controllers.JobStatus jobStatus = (com.android.server.job.controllers.JobStatus) arraySet.valueAt(size);
                    if ((i2 == 5 || jobStatus.getStandbyBucket() == 5) && i2 != jobStatus.getStandbyBucket()) {
                        arrayList.add(jobStatus);
                    }
                    jobStatus.setStandbyBucket(i2);
                }
                com.android.server.job.controllers.QuotaController.Timer timer = (com.android.server.job.controllers.QuotaController.Timer) this.mPkgTimers.get(i, str);
                if (timer != null && timer.isActive()) {
                    timer.rescheduleCutoff();
                }
                com.android.server.job.controllers.QuotaController.Timer timer2 = (com.android.server.job.controllers.QuotaController.Timer) this.mEJPkgTimers.get(i, str);
                if (timer2 != null && timer2.isActive()) {
                    timer2.rescheduleCutoff();
                }
                this.mStateChangedListener.onControllerStateChanged(maybeUpdateConstraintForPkgLocked(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis(), i, str));
                if (arrayList.size() > 0) {
                    this.mStateChangedListener.onRestrictedBucketChanged(arrayList);
                }
            } finally {
            }
        }
    }

    final class UsageEventTracker implements android.app.usage.UsageStatsManagerInternal.UsageEventListener {
        UsageEventTracker() {
        }

        @Override // android.app.usage.UsageStatsManagerInternal.UsageEventListener
        public void onUsageEvent(int i, @android.annotation.NonNull android.app.usage.UsageEvents.Event event) {
            switch (event.getEventType()) {
                case 1:
                case 2:
                case 7:
                case 9:
                case 10:
                case 12:
                case 23:
                case 24:
                    com.android.server.job.controllers.QuotaController.this.mHandler.obtainMessage(5, i, 0, event).sendToTarget();
                    break;
                default:
                    if (com.android.server.job.controllers.QuotaController.DEBUG) {
                        android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, "Dropping event " + event.getEventType());
                        break;
                    }
                    break;
            }
        }
    }

    final class TempAllowlistTracker implements com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener {
        TempAllowlistTracker() {
        }

        public void onAppAdded(int i) {
            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                try {
                    long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                    com.android.server.job.controllers.QuotaController.this.mTempAllowlistCache.put(i, true);
                    android.util.ArraySet<java.lang.String> packagesForUidLocked = com.android.server.job.controllers.QuotaController.this.mService.getPackagesForUidLocked(i);
                    if (packagesForUidLocked != null) {
                        int userId = android.os.UserHandle.getUserId(i);
                        for (int size = packagesForUidLocked.size() - 1; size >= 0; size--) {
                            com.android.server.job.controllers.QuotaController.Timer timer = (com.android.server.job.controllers.QuotaController.Timer) com.android.server.job.controllers.QuotaController.this.mEJPkgTimers.get(userId, packagesForUidLocked.valueAt(size));
                            if (timer != null) {
                                timer.onStateChangedLocked(millis, true);
                            }
                        }
                        android.util.ArraySet<com.android.server.job.controllers.JobStatus> maybeUpdateConstraintForUidLocked = com.android.server.job.controllers.QuotaController.this.maybeUpdateConstraintForUidLocked(i);
                        if (maybeUpdateConstraintForUidLocked.size() > 0) {
                            com.android.server.job.controllers.QuotaController.this.mStateChangedListener.onControllerStateChanged(maybeUpdateConstraintForUidLocked);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onAppRemoved(int i) {
            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() + com.android.server.job.controllers.QuotaController.this.mEJGracePeriodTempAllowlistMs;
                com.android.server.job.controllers.QuotaController.this.mTempAllowlistCache.delete(i);
                com.android.server.job.controllers.QuotaController.this.mTempAllowlistGraceCache.put(i, millis);
                com.android.server.job.controllers.QuotaController.this.mHandler.sendMessageDelayed(com.android.server.job.controllers.QuotaController.this.mHandler.obtainMessage(6, i, 0), com.android.server.job.controllers.QuotaController.this.mEJGracePeriodTempAllowlistMs);
            }
        }
    }

    private static final class TimedEventTooOldPredicate implements java.util.function.Predicate<com.android.server.job.controllers.QuotaController.TimedEvent> {
        private long mNowElapsed;

        private TimedEventTooOldPredicate() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateNow() {
            this.mNowElapsed = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        }

        @Override // java.util.function.Predicate
        public boolean test(com.android.server.job.controllers.QuotaController.TimedEvent timedEvent) {
            return timedEvent.getEndTimeElapsed() <= this.mNowElapsed - 86400000;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(java.util.List list) {
        if (list != null) {
            list.removeIf(this.mTimedEventTooOld);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void deleteObsoleteSessionsLocked() {
        this.mTimedEventTooOld.updateNow();
        this.mTimingEvents.forEach(this.mDeleteOldEventsFunctor);
        for (int i = 0; i < this.mEJTimingSessions.numMaps(); i++) {
            int keyAt = this.mEJTimingSessions.keyAt(i);
            for (int i2 = 0; i2 < this.mEJTimingSessions.numElementsForKey(keyAt); i2++) {
                java.lang.String str = (java.lang.String) this.mEJTimingSessions.keyAt(i, i2);
                com.android.server.job.controllers.QuotaController.ShrinkableDebits eJDebitsLocked = getEJDebitsLocked(keyAt, str);
                java.util.List list = (java.util.List) this.mEJTimingSessions.get(keyAt, str);
                if (list != null) {
                    while (list.size() > 0) {
                        com.android.server.job.controllers.QuotaController.TimingSession timingSession = (com.android.server.job.controllers.QuotaController.TimingSession) list.get(0);
                        if (this.mTimedEventTooOld.test((com.android.server.job.controllers.QuotaController.TimedEvent) timingSession)) {
                            eJDebitsLocked.transactLocked(-(timingSession.endTimeElapsed - timingSession.startTimeElapsed));
                            list.remove(0);
                        }
                    }
                }
            }
        }
    }

    private class QcHandler extends android.os.Handler {
        QcHandler(android.os.Looper looper) {
            super(looper);
        }

        /* JADX WARN: Code restructure failed: missing block: B:162:0x010b, code lost:
        
            r13 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:165:0x0443, code lost:
        
            throw r13;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x0071, code lost:
        
            r13 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x010a, code lost:
        
            throw r13;
         */
        /* JADX WARN: Removed duplicated region for block: B:107:0x0340 A[Catch: all -> 0x026c, Merged into TryCatch #2 {all -> 0x010b, all -> 0x0071, all -> 0x0188, all -> 0x026c, blocks: (B:4:0x0005, B:5:0x000a, B:7:0x0440, B:10:0x000f, B:11:0x0015, B:55:0x010a, B:56:0x010e, B:58:0x011e, B:59:0x0144, B:60:0x0148, B:63:0x014c, B:64:0x0158, B:65:0x0164, B:66:0x0168, B:77:0x0192, B:78:0x0193, B:80:0x019d, B:81:0x01b8, B:83:0x01c8, B:85:0x01ce, B:86:0x01e4, B:87:0x01fd, B:89:0x0211, B:90:0x0230, B:91:0x0235, B:92:0x0247, B:140:0x034b, B:141:0x034c, B:143:0x0358, B:144:0x0372, B:145:0x0387, B:147:0x038d, B:148:0x0394, B:149:0x03a0, B:151:0x03aa, B:152:0x03c5, B:154:0x03d5, B:156:0x03db, B:157:0x03f1, B:158:0x0409, B:160:0x041d, B:161:0x043c, B:13:0x0016, B:15:0x0022, B:17:0x0030, B:19:0x0044, B:22:0x0054, B:24:0x005a, B:25:0x0074, B:27:0x0090, B:29:0x009b, B:31:0x00af, B:33:0x00b2, B:36:0x00b5, B:38:0x00c1, B:39:0x00c8, B:41:0x00cb, B:43:0x00d1, B:44:0x00e7, B:47:0x00ea, B:49:0x00f0, B:50:0x0106, B:68:0x0169, B:70:0x0177, B:71:0x018a, B:72:0x018d, B:96:0x024b, B:98:0x0269, B:100:0x026f, B:101:0x02da, B:103:0x02e6, B:105:0x0334, B:107:0x0340, B:108:0x0347, B:110:0x02f2, B:112:0x02fc, B:114:0x0303, B:116:0x0317, B:117:0x031a, B:119:0x032e, B:121:0x0331, B:125:0x027c, B:128:0x028b, B:129:0x02a2, B:131:0x02ae, B:133:0x02d7, B:136:0x0296), top: B:3:0x0005 }] */
        /* JADX WARN: Removed duplicated region for block: B:114:0x0303 A[Catch: all -> 0x026c, Merged into TryCatch #2 {all -> 0x010b, all -> 0x0071, all -> 0x0188, all -> 0x026c, blocks: (B:4:0x0005, B:5:0x000a, B:7:0x0440, B:10:0x000f, B:11:0x0015, B:55:0x010a, B:56:0x010e, B:58:0x011e, B:59:0x0144, B:60:0x0148, B:63:0x014c, B:64:0x0158, B:65:0x0164, B:66:0x0168, B:77:0x0192, B:78:0x0193, B:80:0x019d, B:81:0x01b8, B:83:0x01c8, B:85:0x01ce, B:86:0x01e4, B:87:0x01fd, B:89:0x0211, B:90:0x0230, B:91:0x0235, B:92:0x0247, B:140:0x034b, B:141:0x034c, B:143:0x0358, B:144:0x0372, B:145:0x0387, B:147:0x038d, B:148:0x0394, B:149:0x03a0, B:151:0x03aa, B:152:0x03c5, B:154:0x03d5, B:156:0x03db, B:157:0x03f1, B:158:0x0409, B:160:0x041d, B:161:0x043c, B:13:0x0016, B:15:0x0022, B:17:0x0030, B:19:0x0044, B:22:0x0054, B:24:0x005a, B:25:0x0074, B:27:0x0090, B:29:0x009b, B:31:0x00af, B:33:0x00b2, B:36:0x00b5, B:38:0x00c1, B:39:0x00c8, B:41:0x00cb, B:43:0x00d1, B:44:0x00e7, B:47:0x00ea, B:49:0x00f0, B:50:0x0106, B:68:0x0169, B:70:0x0177, B:71:0x018a, B:72:0x018d, B:96:0x024b, B:98:0x0269, B:100:0x026f, B:101:0x02da, B:103:0x02e6, B:105:0x0334, B:107:0x0340, B:108:0x0347, B:110:0x02f2, B:112:0x02fc, B:114:0x0303, B:116:0x0317, B:117:0x031a, B:119:0x032e, B:121:0x0331, B:125:0x027c, B:128:0x028b, B:129:0x02a2, B:131:0x02ae, B:133:0x02d7, B:136:0x0296), top: B:3:0x0005 }] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void handleMessage(android.os.Message message) {
            boolean z;
            boolean z2;
            android.util.ArraySet<java.lang.String> packagesForUidLocked;
            int size;
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> maybeUpdateConstraintForUidLocked;
            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                try {
                    switch (message.what) {
                        case 0:
                            android.content.pm.UserPackage userPackage = (android.content.pm.UserPackage) message.obj;
                            if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, "Checking if " + userPackage + " has reached its quota.");
                            }
                            if (com.android.server.job.controllers.QuotaController.this.getRemainingExecutionTimeLocked(userPackage.userId, userPackage.packageName) <= 50) {
                                if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, userPackage + " has reached its quota.");
                                }
                                com.android.server.job.controllers.QuotaController.this.mStateChangedListener.onControllerStateChanged(com.android.server.job.controllers.QuotaController.this.maybeUpdateConstraintForPkgLocked(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis(), userPackage.userId, userPackage.packageName));
                            } else {
                                android.os.Message obtainMessage = obtainMessage(0, userPackage);
                                long timeUntilQuotaConsumedLocked = com.android.server.job.controllers.QuotaController.this.getTimeUntilQuotaConsumedLocked(userPackage.userId, userPackage.packageName);
                                if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, userPackage + " has " + timeUntilQuotaConsumedLocked + "ms left.");
                                }
                                sendMessageDelayed(obtainMessage, timeUntilQuotaConsumedLocked);
                            }
                            break;
                        case 1:
                            if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, "Cleaning up timing sessions.");
                            }
                            com.android.server.job.controllers.QuotaController.this.deleteObsoleteSessionsLocked();
                            com.android.server.job.controllers.QuotaController.this.maybeScheduleCleanupAlarmLocked();
                            break;
                        case 2:
                            java.lang.String str = (java.lang.String) message.obj;
                            int i = message.arg1;
                            if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, "Checking pkg " + com.android.server.job.controllers.StateController.packageToString(i, str));
                            }
                            com.android.server.job.controllers.QuotaController.this.mStateChangedListener.onControllerStateChanged(com.android.server.job.controllers.QuotaController.this.maybeUpdateConstraintForPkgLocked(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis(), i, str));
                            break;
                        case 3:
                            int i2 = message.arg1;
                            int i3 = message.arg2;
                            int userId = android.os.UserHandle.getUserId(i2);
                            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                                if (i3 <= 2) {
                                    com.android.server.job.controllers.QuotaController.this.mTopAppCache.put(i2, true);
                                    com.android.server.job.controllers.QuotaController.this.mTopAppGraceCache.delete(i2);
                                    if (!com.android.server.job.controllers.QuotaController.this.mForegroundUids.get(i2)) {
                                        com.android.server.job.controllers.QuotaController.this.mForegroundUids.put(i2, true);
                                        z = true;
                                        if ((com.android.server.job.controllers.QuotaController.this.mPkgTimers.indexOfKey(userId) < 0 || com.android.server.job.controllers.QuotaController.this.mEJPkgTimers.indexOfKey(userId) >= 0) && (packagesForUidLocked = com.android.server.job.controllers.QuotaController.this.mService.getPackagesForUidLocked(i2)) != null) {
                                            for (size = packagesForUidLocked.size() - 1; size >= 0; size--) {
                                                com.android.server.job.controllers.QuotaController.Timer timer = (com.android.server.job.controllers.QuotaController.Timer) com.android.server.job.controllers.QuotaController.this.mEJPkgTimers.get(userId, packagesForUidLocked.valueAt(size));
                                                if (timer != null) {
                                                    timer.onStateChangedLocked(millis, z);
                                                }
                                                com.android.server.job.controllers.QuotaController.Timer timer2 = (com.android.server.job.controllers.QuotaController.Timer) com.android.server.job.controllers.QuotaController.this.mPkgTimers.get(userId, packagesForUidLocked.valueAt(size));
                                                if (timer2 != null) {
                                                    timer2.onStateChangedLocked(millis, z);
                                                }
                                            }
                                        }
                                        maybeUpdateConstraintForUidLocked = com.android.server.job.controllers.QuotaController.this.maybeUpdateConstraintForUidLocked(i2);
                                        if (maybeUpdateConstraintForUidLocked.size() > 0) {
                                            com.android.server.job.controllers.QuotaController.this.mStateChangedListener.onControllerStateChanged(maybeUpdateConstraintForUidLocked);
                                        }
                                    }
                                } else {
                                    if (i3 <= 4) {
                                        z2 = !com.android.server.job.controllers.QuotaController.this.mForegroundUids.get(i2);
                                        com.android.server.job.controllers.QuotaController.this.mForegroundUids.put(i2, true);
                                        z = true;
                                    } else {
                                        com.android.server.job.controllers.QuotaController.this.mForegroundUids.delete(i2);
                                        z = false;
                                        z2 = true;
                                    }
                                    if (com.android.server.job.controllers.QuotaController.this.mTopAppCache.get(i2)) {
                                        long j = com.android.server.job.controllers.QuotaController.this.mEJGracePeriodTopAppMs + millis;
                                        com.android.server.job.controllers.QuotaController.this.mTopAppCache.delete(i2);
                                        com.android.server.job.controllers.QuotaController.this.mTopAppGraceCache.put(i2, j);
                                        sendMessageDelayed(obtainMessage(6, i2, 0), com.android.server.job.controllers.QuotaController.this.mEJGracePeriodTopAppMs);
                                    }
                                    if (!z2) {
                                    }
                                    if (com.android.server.job.controllers.QuotaController.this.mPkgTimers.indexOfKey(userId) < 0) {
                                    }
                                    while (size >= 0) {
                                    }
                                    maybeUpdateConstraintForUidLocked = com.android.server.job.controllers.QuotaController.this.maybeUpdateConstraintForUidLocked(i2);
                                    if (maybeUpdateConstraintForUidLocked.size() > 0) {
                                    }
                                }
                            }
                            break;
                        case 4:
                            android.content.pm.UserPackage userPackage2 = (android.content.pm.UserPackage) message.obj;
                            if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, "Checking if " + userPackage2 + " has reached its EJ quota.");
                            }
                            if (com.android.server.job.controllers.QuotaController.this.getRemainingEJExecutionTimeLocked(userPackage2.userId, userPackage2.packageName) <= 0) {
                                if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, userPackage2 + " has reached its EJ quota.");
                                }
                                com.android.server.job.controllers.QuotaController.this.mStateChangedListener.onControllerStateChanged(com.android.server.job.controllers.QuotaController.this.maybeUpdateConstraintForPkgLocked(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis(), userPackage2.userId, userPackage2.packageName));
                            } else {
                                android.os.Message obtainMessage2 = obtainMessage(4, userPackage2);
                                long timeUntilEJQuotaConsumedLocked = com.android.server.job.controllers.QuotaController.this.getTimeUntilEJQuotaConsumedLocked(userPackage2.userId, userPackage2.packageName);
                                if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, userPackage2 + " has " + timeUntilEJQuotaConsumedLocked + "ms left for EJ");
                                }
                                sendMessageDelayed(obtainMessage2, timeUntilEJQuotaConsumedLocked);
                            }
                            break;
                        case 5:
                            int i4 = message.arg1;
                            android.app.usage.UsageEvents.Event event = (android.app.usage.UsageEvents.Event) message.obj;
                            java.lang.String packageName = event.getPackageName();
                            if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, "Processing event " + event.getEventType() + " for " + com.android.server.job.controllers.StateController.packageToString(i4, packageName));
                            }
                            switch (event.getEventType()) {
                                case 1:
                                case 2:
                                case 23:
                                case 24:
                                    synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                                        com.android.server.job.controllers.QuotaController.TopAppTimer topAppTimer = (com.android.server.job.controllers.QuotaController.TopAppTimer) com.android.server.job.controllers.QuotaController.this.mTopAppTrackers.get(i4, packageName);
                                        if (topAppTimer == null) {
                                            topAppTimer = com.android.server.job.controllers.QuotaController.this.new TopAppTimer(i4, packageName);
                                            com.android.server.job.controllers.QuotaController.this.mTopAppTrackers.add(i4, packageName, topAppTimer);
                                        }
                                        topAppTimer.processEventLocked(event);
                                    }
                                    break;
                                case 7:
                                case 9:
                                case 12:
                                    com.android.server.job.controllers.QuotaController.this.grantRewardForInstantEvent(i4, packageName, com.android.server.job.controllers.QuotaController.this.mEJRewardInteractionMs);
                                    break;
                                case 10:
                                    com.android.server.job.controllers.QuotaController.this.grantRewardForInstantEvent(i4, packageName, com.android.server.job.controllers.QuotaController.this.mEJRewardNotificationSeenMs);
                                    break;
                            }
                        case 6:
                            int i5 = message.arg1;
                            synchronized (com.android.server.job.controllers.QuotaController.this.mLock) {
                                if (com.android.server.job.controllers.QuotaController.this.mTempAllowlistCache.get(i5) || com.android.server.job.controllers.QuotaController.this.mTopAppCache.get(i5)) {
                                    if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                        android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, i5 + " is still allowed");
                                    }
                                } else {
                                    long millis2 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                                    if (millis2 >= com.android.server.job.controllers.QuotaController.this.mTempAllowlistGraceCache.get(i5) && millis2 >= com.android.server.job.controllers.QuotaController.this.mTopAppGraceCache.get(i5)) {
                                        if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                            android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, i5 + " is now out of grace period");
                                        }
                                        com.android.server.job.controllers.QuotaController.this.mTempAllowlistGraceCache.delete(i5);
                                        com.android.server.job.controllers.QuotaController.this.mTopAppGraceCache.delete(i5);
                                        android.util.ArraySet<java.lang.String> packagesForUidLocked2 = com.android.server.job.controllers.QuotaController.this.mService.getPackagesForUidLocked(i5);
                                        if (packagesForUidLocked2 != null) {
                                            int userId2 = android.os.UserHandle.getUserId(i5);
                                            for (int size2 = packagesForUidLocked2.size() - 1; size2 >= 0; size2--) {
                                                com.android.server.job.controllers.QuotaController.Timer timer3 = (com.android.server.job.controllers.QuotaController.Timer) com.android.server.job.controllers.QuotaController.this.mEJPkgTimers.get(userId2, packagesForUidLocked2.valueAt(size2));
                                                if (timer3 != null) {
                                                    timer3.onStateChangedLocked(millis2, false);
                                                }
                                            }
                                            android.util.ArraySet<com.android.server.job.controllers.JobStatus> maybeUpdateConstraintForUidLocked2 = com.android.server.job.controllers.QuotaController.this.maybeUpdateConstraintForUidLocked(i5);
                                            if (maybeUpdateConstraintForUidLocked2.size() > 0) {
                                                com.android.server.job.controllers.QuotaController.this.mStateChangedListener.onControllerStateChanged(maybeUpdateConstraintForUidLocked2);
                                            }
                                        }
                                    }
                                    if (com.android.server.job.controllers.QuotaController.DEBUG) {
                                        android.util.Slog.d(com.android.server.job.controllers.QuotaController.TAG, i5 + " is still in grace period");
                                    }
                                }
                            }
                            break;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                } finally {
                }
            }
        }
    }

    private class InQuotaAlarmQueue extends com.android.server.utils.AlarmQueue<android.content.pm.UserPackage> {
        private InQuotaAlarmQueue(android.content.Context context, android.os.Looper looper) {
            super(context, looper, com.android.server.job.controllers.QuotaController.ALARM_TAG_QUOTA_CHECK, "In quota", false, 60000L);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.utils.AlarmQueue
        public boolean isForUser(@android.annotation.NonNull android.content.pm.UserPackage userPackage, int i) {
            return userPackage.userId == i;
        }

        @Override // com.android.server.utils.AlarmQueue
        protected void processExpiredAlarms(@android.annotation.NonNull android.util.ArraySet<android.content.pm.UserPackage> arraySet) {
            for (int i = 0; i < arraySet.size(); i++) {
                android.content.pm.UserPackage valueAt = arraySet.valueAt(i);
                com.android.server.job.controllers.QuotaController.this.mHandler.obtainMessage(2, valueAt.userId, 0, valueAt.packageName).sendToTarget();
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void prepareForUpdatedConstantsLocked() {
        this.mQcConstants.mShouldReevaluateConstraints = false;
        this.mQcConstants.mRateLimitingConstantsUpdated = false;
        this.mQcConstants.mExecutionPeriodConstantsUpdated = false;
        this.mQcConstants.mEJLimitConstantsUpdated = false;
        this.mQcConstants.mQuotaBumpConstantsUpdated = false;
    }

    @Override // com.android.server.job.controllers.StateController
    public void processConstantLocked(android.provider.DeviceConfig.Properties properties, java.lang.String str) {
        this.mQcConstants.processConstantLocked(properties, str);
    }

    @Override // com.android.server.job.controllers.StateController
    public void onConstantsUpdatedLocked() {
        if (this.mQcConstants.mShouldReevaluateConstraints || this.mIsEnabled == this.mConstants.USE_TARE_POLICY) {
            this.mIsEnabled = !this.mConstants.USE_TARE_POLICY;
            com.android.server.AppSchedulingModuleThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.job.controllers.QuotaController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.job.controllers.QuotaController.this.lambda$onConstantsUpdatedLocked$3();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConstantsUpdatedLocked$3() {
        synchronized (this.mLock) {
            invalidateAllExecutionStatsLocked();
            maybeUpdateAllConstraintsLocked();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class QcConstants {
        private static final long DEFAULT_ALLOWED_TIME_PER_PERIOD_ACTIVE_MS = 600000;
        private static final long DEFAULT_ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS = 600000;
        private static final long DEFAULT_ALLOWED_TIME_PER_PERIOD_FREQUENT_MS = 600000;
        private static final long DEFAULT_ALLOWED_TIME_PER_PERIOD_RARE_MS = 600000;
        private static final long DEFAULT_ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS = 600000;
        private static final long DEFAULT_ALLOWED_TIME_PER_PERIOD_WORKING_MS = 600000;
        private static final long DEFAULT_EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS = 180000;
        private static final long DEFAULT_EJ_GRACE_PERIOD_TOP_APP_MS = 60000;
        private static final long DEFAULT_EJ_LIMIT_ACTIVE_MS = 1800000;
        private static final long DEFAULT_EJ_LIMIT_ADDITION_INSTALLER_MS = 1800000;
        private static final long DEFAULT_EJ_LIMIT_ADDITION_SPECIAL_MS = 900000;
        private static final long DEFAULT_EJ_LIMIT_EXEMPTED_MS = 3600000;
        private static final long DEFAULT_EJ_LIMIT_FREQUENT_MS = 600000;
        private static final long DEFAULT_EJ_LIMIT_RARE_MS = 600000;
        private static final long DEFAULT_EJ_LIMIT_RESTRICTED_MS = 300000;
        private static final long DEFAULT_EJ_LIMIT_WORKING_MS = 1800000;
        private static final long DEFAULT_EJ_REWARD_INTERACTION_MS = 15000;
        private static final long DEFAULT_EJ_REWARD_NOTIFICATION_SEEN_MS = 0;
        private static final long DEFAULT_EJ_REWARD_TOP_APP_MS = 10000;
        private static final long DEFAULT_EJ_TOP_APP_TIME_CHUNK_SIZE_MS = 30000;
        private static final long DEFAULT_EJ_WINDOW_SIZE_MS = 86400000;
        private static final long DEFAULT_IN_QUOTA_BUFFER_MS = 30000;
        private static final long DEFAULT_MAX_EXECUTION_TIME_MS = 14400000;
        private static final int DEFAULT_MAX_JOB_COUNT_ACTIVE = 75;
        private static final int DEFAULT_MAX_JOB_COUNT_EXEMPTED = 75;
        private static final int DEFAULT_MAX_JOB_COUNT_FREQUENT = 200;
        private static final int DEFAULT_MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW = 20;
        private static final int DEFAULT_MAX_JOB_COUNT_RARE = 48;
        private static final int DEFAULT_MAX_JOB_COUNT_RESTRICTED = 10;
        private static final int DEFAULT_MAX_JOB_COUNT_WORKING = 120;
        private static final int DEFAULT_MAX_SESSION_COUNT_ACTIVE = 75;
        private static final int DEFAULT_MAX_SESSION_COUNT_EXEMPTED = 75;
        private static final int DEFAULT_MAX_SESSION_COUNT_FREQUENT = 8;
        private static final int DEFAULT_MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW = 20;
        private static final int DEFAULT_MAX_SESSION_COUNT_RARE = 3;
        private static final int DEFAULT_MAX_SESSION_COUNT_RESTRICTED = 1;
        private static final int DEFAULT_MAX_SESSION_COUNT_WORKING = 10;
        private static final long DEFAULT_MIN_QUOTA_CHECK_DELAY_MS = 60000;
        private static final long DEFAULT_QUOTA_BUMP_ADDITIONAL_DURATION_MS = 60000;
        private static final int DEFAULT_QUOTA_BUMP_ADDITIONAL_JOB_COUNT = 2;
        private static final int DEFAULT_QUOTA_BUMP_ADDITIONAL_SESSION_COUNT = 1;
        private static final int DEFAULT_QUOTA_BUMP_LIMIT = 8;
        private static final long DEFAULT_QUOTA_BUMP_WINDOW_SIZE_MS = 28800000;
        private static final long DEFAULT_RATE_LIMITING_WINDOW_MS = 60000;
        private static final long DEFAULT_TIMING_SESSION_COALESCING_DURATION_MS = 5000;
        private static final long DEFAULT_WINDOW_SIZE_ACTIVE_MS = 600000;
        private static final long DEFAULT_WINDOW_SIZE_EXEMPTED_MS = 600000;
        private static final long DEFAULT_WINDOW_SIZE_FREQUENT_MS = 28800000;
        private static final long DEFAULT_WINDOW_SIZE_RARE_MS = 86400000;
        private static final long DEFAULT_WINDOW_SIZE_RESTRICTED_MS = 86400000;
        private static final long DEFAULT_WINDOW_SIZE_WORKING_MS = 7200000;

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_ALLOWED_TIME_PER_PERIOD_ACTIVE_MS = "qc_allowed_time_per_period_active_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS = "qc_allowed_time_per_period_exempted_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_ALLOWED_TIME_PER_PERIOD_FREQUENT_MS = "qc_allowed_time_per_period_frequent_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_ALLOWED_TIME_PER_PERIOD_RARE_MS = "qc_allowed_time_per_period_rare_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS = "qc_allowed_time_per_period_restricted_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_ALLOWED_TIME_PER_PERIOD_WORKING_MS = "qc_allowed_time_per_period_working_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS = "qc_ej_grace_period_temp_allowlist_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_GRACE_PERIOD_TOP_APP_MS = "qc_ej_grace_period_top_app_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_LIMIT_ACTIVE_MS = "qc_ej_limit_active_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_LIMIT_ADDITION_INSTALLER_MS = "qc_ej_limit_addition_installer_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_LIMIT_ADDITION_SPECIAL_MS = "qc_ej_limit_addition_special_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_LIMIT_EXEMPTED_MS = "qc_ej_limit_exempted_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_LIMIT_FREQUENT_MS = "qc_ej_limit_frequent_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_LIMIT_RARE_MS = "qc_ej_limit_rare_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_LIMIT_RESTRICTED_MS = "qc_ej_limit_restricted_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_LIMIT_WORKING_MS = "qc_ej_limit_working_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_REWARD_INTERACTION_MS = "qc_ej_reward_interaction_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_REWARD_NOTIFICATION_SEEN_MS = "qc_ej_reward_notification_seen_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_REWARD_TOP_APP_MS = "qc_ej_reward_top_app_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_TOP_APP_TIME_CHUNK_SIZE_MS = "qc_ej_top_app_time_chunk_size_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_EJ_WINDOW_SIZE_MS = "qc_ej_window_size_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_IN_QUOTA_BUFFER_MS = "qc_in_quota_buffer_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_EXECUTION_TIME_MS = "qc_max_execution_time_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_JOB_COUNT_ACTIVE = "qc_max_job_count_active";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_JOB_COUNT_EXEMPTED = "qc_max_job_count_exempted";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_JOB_COUNT_FREQUENT = "qc_max_job_count_frequent";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW = "qc_max_job_count_per_rate_limiting_window";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_JOB_COUNT_RARE = "qc_max_job_count_rare";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_JOB_COUNT_RESTRICTED = "qc_max_job_count_restricted";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_JOB_COUNT_WORKING = "qc_max_job_count_working";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_SESSION_COUNT_ACTIVE = "qc_max_session_count_active";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_SESSION_COUNT_EXEMPTED = "qc_max_session_count_exempted";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_SESSION_COUNT_FREQUENT = "qc_max_session_count_frequent";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW = "qc_max_session_count_per_rate_limiting_window";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_SESSION_COUNT_RARE = "qc_max_session_count_rare";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_SESSION_COUNT_RESTRICTED = "qc_max_session_count_restricted";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_SESSION_COUNT_WORKING = "qc_max_session_count_working";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MIN_QUOTA_CHECK_DELAY_MS = "qc_min_quota_check_delay_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_QUOTA_BUMP_ADDITIONAL_DURATION_MS = "qc_quota_bump_additional_duration_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_QUOTA_BUMP_ADDITIONAL_JOB_COUNT = "qc_quota_bump_additional_job_count";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_QUOTA_BUMP_ADDITIONAL_SESSION_COUNT = "qc_quota_bump_additional_session_count";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_QUOTA_BUMP_LIMIT = "qc_quota_bump_limit";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_QUOTA_BUMP_WINDOW_SIZE_MS = "qc_quota_bump_window_size_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_RATE_LIMITING_WINDOW_MS = "qc_rate_limiting_window_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_TIMING_SESSION_COALESCING_DURATION_MS = "qc_timing_session_coalescing_duration_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_WINDOW_SIZE_ACTIVE_MS = "qc_window_size_active_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_WINDOW_SIZE_EXEMPTED_MS = "qc_window_size_exempted_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_WINDOW_SIZE_FREQUENT_MS = "qc_window_size_frequent_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_WINDOW_SIZE_RARE_MS = "qc_window_size_rare_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_WINDOW_SIZE_RESTRICTED_MS = "qc_window_size_restricted_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_WINDOW_SIZE_WORKING_MS = "qc_window_size_working_ms";
        private static final int MIN_BUCKET_JOB_COUNT = 10;
        private static final int MIN_BUCKET_SESSION_COUNT = 1;
        private static final long MIN_MAX_EXECUTION_TIME_MS = 3600000;
        private static final int MIN_MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW = 10;
        private static final int MIN_MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW = 10;
        private static final long MIN_RATE_LIMITING_WINDOW_MS = 30000;
        private static final java.lang.String QC_CONSTANT_PREFIX = "qc_";
        private boolean mShouldReevaluateConstraints = false;
        private boolean mRateLimitingConstantsUpdated = false;
        private boolean mExecutionPeriodConstantsUpdated = false;
        private boolean mEJLimitConstantsUpdated = false;
        private boolean mQuotaBumpConstantsUpdated = false;
        public long ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS = 600000;
        public long ALLOWED_TIME_PER_PERIOD_ACTIVE_MS = 600000;
        public long ALLOWED_TIME_PER_PERIOD_WORKING_MS = 600000;
        public long ALLOWED_TIME_PER_PERIOD_FREQUENT_MS = 600000;
        public long ALLOWED_TIME_PER_PERIOD_RARE_MS = 600000;
        public long ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS = 600000;
        public long IN_QUOTA_BUFFER_MS = 30000;
        public long WINDOW_SIZE_EXEMPTED_MS = 600000;
        public long WINDOW_SIZE_ACTIVE_MS = 600000;
        public long WINDOW_SIZE_WORKING_MS = 7200000;
        public long WINDOW_SIZE_FREQUENT_MS = 28800000;
        public long WINDOW_SIZE_RARE_MS = 86400000;
        public long WINDOW_SIZE_RESTRICTED_MS = 86400000;
        public long MAX_EXECUTION_TIME_MS = 14400000;
        public int MAX_JOB_COUNT_EXEMPTED = 75;
        public int MAX_JOB_COUNT_ACTIVE = 75;
        public int MAX_JOB_COUNT_WORKING = 120;
        public int MAX_JOB_COUNT_FREQUENT = 200;
        public int MAX_JOB_COUNT_RARE = 48;
        public int MAX_JOB_COUNT_RESTRICTED = 10;
        public long RATE_LIMITING_WINDOW_MS = 60000;
        public int MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW = 20;
        public int MAX_SESSION_COUNT_EXEMPTED = 75;
        public int MAX_SESSION_COUNT_ACTIVE = 75;
        public int MAX_SESSION_COUNT_WORKING = 10;
        public int MAX_SESSION_COUNT_FREQUENT = 8;
        public int MAX_SESSION_COUNT_RARE = 3;
        public int MAX_SESSION_COUNT_RESTRICTED = 1;
        public int MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW = 20;
        public long TIMING_SESSION_COALESCING_DURATION_MS = DEFAULT_TIMING_SESSION_COALESCING_DURATION_MS;
        public long MIN_QUOTA_CHECK_DELAY_MS = 60000;
        public long EJ_LIMIT_EXEMPTED_MS = 3600000;
        public long EJ_LIMIT_ACTIVE_MS = 1800000;
        public long EJ_LIMIT_WORKING_MS = 1800000;
        public long EJ_LIMIT_FREQUENT_MS = 600000;
        public long EJ_LIMIT_RARE_MS = 600000;
        public long EJ_LIMIT_RESTRICTED_MS = 300000;
        public long EJ_LIMIT_ADDITION_SPECIAL_MS = DEFAULT_EJ_LIMIT_ADDITION_SPECIAL_MS;
        public long EJ_LIMIT_ADDITION_INSTALLER_MS = 1800000;
        public long EJ_WINDOW_SIZE_MS = 86400000;
        public long EJ_TOP_APP_TIME_CHUNK_SIZE_MS = 30000;
        public long EJ_REWARD_TOP_APP_MS = 10000;
        public long EJ_REWARD_INTERACTION_MS = DEFAULT_EJ_REWARD_INTERACTION_MS;
        public long EJ_REWARD_NOTIFICATION_SEEN_MS = 0;
        public long EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS = 180000;
        public long EJ_GRACE_PERIOD_TOP_APP_MS = 60000;
        public long QUOTA_BUMP_ADDITIONAL_DURATION_MS = 60000;
        public int QUOTA_BUMP_ADDITIONAL_JOB_COUNT = 2;
        public int QUOTA_BUMP_ADDITIONAL_SESSION_COUNT = 1;
        public long QUOTA_BUMP_WINDOW_SIZE_MS = 28800000;
        public int QUOTA_BUMP_LIMIT = 8;

        QcConstants() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void processConstantLocked(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -1952749138:
                    if (str.equals(KEY_EJ_LIMIT_ACTIVE_MS)) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -1719823663:
                    if (str.equals(KEY_ALLOWED_TIME_PER_PERIOD_ACTIVE_MS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1683576133:
                    if (str.equals(KEY_WINDOW_SIZE_FREQUENT_MS)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1525098678:
                    if (str.equals(KEY_QUOTA_BUMP_WINDOW_SIZE_MS)) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case -1515776932:
                    if (str.equals(KEY_ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -1507602138:
                    if (str.equals(KEY_EJ_LIMIT_FREQUENT_MS)) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -1495638658:
                    if (str.equals(KEY_EJ_LIMIT_ADDITION_SPECIAL_MS)) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case -1436524327:
                    if (str.equals(KEY_EJ_REWARD_NOTIFICATION_SEEN_MS)) {
                        c = '.';
                        break;
                    }
                    c = 65535;
                    break;
                case -1412574464:
                    if (str.equals(KEY_MAX_JOB_COUNT_ACTIVE)) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                case -1409079211:
                    if (str.equals(KEY_EJ_TOP_APP_TIME_CHUNK_SIZE_MS)) {
                        c = '+';
                        break;
                    }
                    c = 65535;
                    break;
                case -1301522660:
                    if (str.equals(KEY_MAX_JOB_COUNT_RARE)) {
                        c = '!';
                        break;
                    }
                    c = 65535;
                    break;
                case -1253638898:
                    if (str.equals(KEY_WINDOW_SIZE_RESTRICTED_MS)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -1004520055:
                    if (str.equals(KEY_ALLOWED_TIME_PER_PERIOD_FREQUENT_MS)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -947372170:
                    if (str.equals(KEY_EJ_REWARD_INTERACTION_MS)) {
                        c = '-';
                        break;
                    }
                    c = 65535;
                    break;
                case -947005713:
                    if (str.equals(KEY_RATE_LIMITING_WINDOW_MS)) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -911626004:
                    if (str.equals(KEY_MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW)) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -861283784:
                    if (str.equals(KEY_MAX_JOB_COUNT_EXEMPTED)) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case -743649451:
                    if (str.equals(KEY_MAX_JOB_COUNT_RESTRICTED)) {
                        c = '\"';
                        break;
                    }
                    c = 65535;
                    break;
                case -615999962:
                    if (str.equals(KEY_QUOTA_BUMP_LIMIT)) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case -473591193:
                    if (str.equals(KEY_WINDOW_SIZE_RARE_MS)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -144699320:
                    if (str.equals(KEY_MAX_JOB_COUNT_FREQUENT)) {
                        c = ' ';
                        break;
                    }
                    c = 65535;
                    break;
                case 202838626:
                    if (str.equals(KEY_ALLOWED_TIME_PER_PERIOD_WORKING_MS)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 224532750:
                    if (str.equals(KEY_QUOTA_BUMP_ADDITIONAL_DURATION_MS)) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case 319829733:
                    if (str.equals(KEY_MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW)) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 353645753:
                    if (str.equals(KEY_EJ_LIMIT_RESTRICTED_MS)) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 353674834:
                    if (str.equals(KEY_EJ_LIMIT_RARE_MS)) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 515924943:
                    if (str.equals(KEY_EJ_LIMIT_ADDITION_INSTALLER_MS)) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 542719401:
                    if (str.equals(KEY_MAX_EXECUTION_TIME_MS)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 659682264:
                    if (str.equals(KEY_EJ_GRACE_PERIOD_TOP_APP_MS)) {
                        c = '0';
                        break;
                    }
                    c = 65535;
                    break;
                case 1012217584:
                    if (str.equals(KEY_WINDOW_SIZE_WORKING_MS)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1029123626:
                    if (str.equals(KEY_QUOTA_BUMP_ADDITIONAL_JOB_COUNT)) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case 1070239943:
                    if (str.equals(KEY_MAX_SESSION_COUNT_ACTIVE)) {
                        c = '$';
                        break;
                    }
                    c = 65535;
                    break;
                case 1072854979:
                    if (str.equals(KEY_QUOTA_BUMP_ADDITIONAL_SESSION_COUNT)) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case 1185201205:
                    if (str.equals(KEY_ALLOWED_TIME_PER_PERIOD_RARE_MS)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1211719583:
                    if (str.equals(KEY_EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS)) {
                        c = '/';
                        break;
                    }
                    c = 65535;
                    break;
                case 1232643386:
                    if (str.equals(KEY_MIN_QUOTA_CHECK_DELAY_MS)) {
                        c = '*';
                        break;
                    }
                    c = 65535;
                    break;
                case 1415707953:
                    if (str.equals(KEY_IN_QUOTA_BUFFER_MS)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1416512063:
                    if (str.equals(KEY_MAX_SESSION_COUNT_EXEMPTED)) {
                        c = '#';
                        break;
                    }
                    c = 65535;
                    break;
                case 1504661904:
                    if (str.equals(KEY_MAX_SESSION_COUNT_WORKING)) {
                        c = '%';
                        break;
                    }
                    c = 65535;
                    break;
                case 1510141337:
                    if (str.equals(KEY_ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1572083493:
                    if (str.equals(KEY_EJ_LIMIT_WORKING_MS)) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 1737007281:
                    if (str.equals(KEY_EJ_REWARD_TOP_APP_MS)) {
                        c = ',';
                        break;
                    }
                    c = 65535;
                    break;
                case 1846826615:
                    if (str.equals(KEY_MAX_JOB_COUNT_WORKING)) {
                        c = 31;
                        break;
                    }
                    c = 65535;
                    break;
                case 1908515971:
                    if (str.equals(KEY_WINDOW_SIZE_ACTIVE_MS)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1921715463:
                    if (str.equals(KEY_TIMING_SESSION_COALESCING_DURATION_MS)) {
                        c = ')';
                        break;
                    }
                    c = 65535;
                    break;
                case 1988481858:
                    if (str.equals(KEY_EJ_WINDOW_SIZE_MS)) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case 2079805852:
                    if (str.equals(KEY_MAX_SESSION_COUNT_RESTRICTED)) {
                        c = '(';
                        break;
                    }
                    c = 65535;
                    break;
                case 2084297379:
                    if (str.equals(KEY_MAX_SESSION_COUNT_RARE)) {
                        c = '\'';
                        break;
                    }
                    c = 65535;
                    break;
                case 2133096527:
                    if (str.equals(KEY_MAX_SESSION_COUNT_FREQUENT)) {
                        c = '&';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case '\b':
                case '\t':
                case '\n':
                case 11:
                case '\f':
                    updateExecutionPeriodConstantsLocked();
                    break;
                case '\r':
                case 14:
                case 15:
                    updateRateLimitingConstantsLocked();
                    break;
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    updateEJLimitConstantsLocked();
                    break;
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                    updateQuotaBumpConstantsLocked();
                    break;
                case 29:
                    this.MAX_JOB_COUNT_EXEMPTED = properties.getInt(str, 75);
                    int max = java.lang.Math.max(10, this.MAX_JOB_COUNT_EXEMPTED);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[6] != max) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[6] = max;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case 30:
                    this.MAX_JOB_COUNT_ACTIVE = properties.getInt(str, 75);
                    int max2 = java.lang.Math.max(10, this.MAX_JOB_COUNT_ACTIVE);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[0] != max2) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[0] = max2;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case 31:
                    this.MAX_JOB_COUNT_WORKING = properties.getInt(str, 120);
                    int max3 = java.lang.Math.max(10, this.MAX_JOB_COUNT_WORKING);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[1] != max3) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[1] = max3;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case ' ':
                    this.MAX_JOB_COUNT_FREQUENT = properties.getInt(str, 200);
                    int max4 = java.lang.Math.max(10, this.MAX_JOB_COUNT_FREQUENT);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[2] != max4) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[2] = max4;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '!':
                    this.MAX_JOB_COUNT_RARE = properties.getInt(str, 48);
                    int max5 = java.lang.Math.max(10, this.MAX_JOB_COUNT_RARE);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[3] != max5) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[3] = max5;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '\"':
                    this.MAX_JOB_COUNT_RESTRICTED = properties.getInt(str, 10);
                    int max6 = java.lang.Math.max(10, this.MAX_JOB_COUNT_RESTRICTED);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[5] != max6) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketJobCounts[5] = max6;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '#':
                    this.MAX_SESSION_COUNT_EXEMPTED = properties.getInt(str, 75);
                    int max7 = java.lang.Math.max(1, this.MAX_SESSION_COUNT_EXEMPTED);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[6] != max7) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[6] = max7;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '$':
                    this.MAX_SESSION_COUNT_ACTIVE = properties.getInt(str, 75);
                    int max8 = java.lang.Math.max(1, this.MAX_SESSION_COUNT_ACTIVE);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[0] != max8) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[0] = max8;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '%':
                    this.MAX_SESSION_COUNT_WORKING = properties.getInt(str, 10);
                    int max9 = java.lang.Math.max(1, this.MAX_SESSION_COUNT_WORKING);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[1] != max9) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[1] = max9;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '&':
                    this.MAX_SESSION_COUNT_FREQUENT = properties.getInt(str, 8);
                    int max10 = java.lang.Math.max(1, this.MAX_SESSION_COUNT_FREQUENT);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[2] != max10) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[2] = max10;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '\'':
                    this.MAX_SESSION_COUNT_RARE = properties.getInt(str, 3);
                    int max11 = java.lang.Math.max(1, this.MAX_SESSION_COUNT_RARE);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[3] != max11) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[3] = max11;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '(':
                    this.MAX_SESSION_COUNT_RESTRICTED = properties.getInt(str, 1);
                    int max12 = java.lang.Math.max(0, this.MAX_SESSION_COUNT_RESTRICTED);
                    if (com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[5] != max12) {
                        com.android.server.job.controllers.QuotaController.this.mMaxBucketSessionCounts[5] = max12;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case ')':
                    this.TIMING_SESSION_COALESCING_DURATION_MS = properties.getLong(str, DEFAULT_TIMING_SESSION_COALESCING_DURATION_MS);
                    long min = java.lang.Math.min(DEFAULT_EJ_LIMIT_ADDITION_SPECIAL_MS, java.lang.Math.max(0L, this.TIMING_SESSION_COALESCING_DURATION_MS));
                    if (com.android.server.job.controllers.QuotaController.this.mTimingSessionCoalescingDurationMs != min) {
                        com.android.server.job.controllers.QuotaController.this.mTimingSessionCoalescingDurationMs = min;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '*':
                    this.MIN_QUOTA_CHECK_DELAY_MS = properties.getLong(str, 60000L);
                    com.android.server.job.controllers.QuotaController.this.mInQuotaAlarmQueue.setMinTimeBetweenAlarmsMs(java.lang.Math.min(DEFAULT_EJ_LIMIT_ADDITION_SPECIAL_MS, java.lang.Math.max(0L, this.MIN_QUOTA_CHECK_DELAY_MS)));
                    break;
                case '+':
                    this.EJ_TOP_APP_TIME_CHUNK_SIZE_MS = properties.getLong(str, 30000L);
                    long min2 = java.lang.Math.min(DEFAULT_EJ_LIMIT_ADDITION_SPECIAL_MS, java.lang.Math.max(1L, this.EJ_TOP_APP_TIME_CHUNK_SIZE_MS));
                    if (com.android.server.job.controllers.QuotaController.this.mEJTopAppTimeChunkSizeMs != min2) {
                        com.android.server.job.controllers.QuotaController.this.mEJTopAppTimeChunkSizeMs = min2;
                        if (com.android.server.job.controllers.QuotaController.this.mEJTopAppTimeChunkSizeMs < com.android.server.job.controllers.QuotaController.this.mEJRewardTopAppMs) {
                            android.util.Slog.w(com.android.server.job.controllers.QuotaController.TAG, "EJ top app time chunk less than reward: " + com.android.server.job.controllers.QuotaController.this.mEJTopAppTimeChunkSizeMs + " vs " + com.android.server.job.controllers.QuotaController.this.mEJRewardTopAppMs);
                            break;
                        }
                    }
                    break;
                case ',':
                    this.EJ_REWARD_TOP_APP_MS = properties.getLong(str, 10000L);
                    long min3 = java.lang.Math.min(DEFAULT_EJ_LIMIT_ADDITION_SPECIAL_MS, java.lang.Math.max(10000L, this.EJ_REWARD_TOP_APP_MS));
                    if (com.android.server.job.controllers.QuotaController.this.mEJRewardTopAppMs != min3) {
                        com.android.server.job.controllers.QuotaController.this.mEJRewardTopAppMs = min3;
                        if (com.android.server.job.controllers.QuotaController.this.mEJTopAppTimeChunkSizeMs < com.android.server.job.controllers.QuotaController.this.mEJRewardTopAppMs) {
                            android.util.Slog.w(com.android.server.job.controllers.QuotaController.TAG, "EJ top app time chunk less than reward: " + com.android.server.job.controllers.QuotaController.this.mEJTopAppTimeChunkSizeMs + " vs " + com.android.server.job.controllers.QuotaController.this.mEJRewardTopAppMs);
                            break;
                        }
                    }
                    break;
                case '-':
                    this.EJ_REWARD_INTERACTION_MS = properties.getLong(str, DEFAULT_EJ_REWARD_INTERACTION_MS);
                    com.android.server.job.controllers.QuotaController.this.mEJRewardInteractionMs = java.lang.Math.min(DEFAULT_EJ_LIMIT_ADDITION_SPECIAL_MS, java.lang.Math.max(DEFAULT_TIMING_SESSION_COALESCING_DURATION_MS, this.EJ_REWARD_INTERACTION_MS));
                    break;
                case '.':
                    this.EJ_REWARD_NOTIFICATION_SEEN_MS = properties.getLong(str, 0L);
                    com.android.server.job.controllers.QuotaController.this.mEJRewardNotificationSeenMs = java.lang.Math.min(300000L, java.lang.Math.max(0L, this.EJ_REWARD_NOTIFICATION_SEEN_MS));
                    break;
                case '/':
                    this.EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS = properties.getLong(str, 180000L);
                    com.android.server.job.controllers.QuotaController.this.mEJGracePeriodTempAllowlistMs = java.lang.Math.min(3600000L, java.lang.Math.max(0L, this.EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS));
                    break;
                case '0':
                    this.EJ_GRACE_PERIOD_TOP_APP_MS = properties.getLong(str, 60000L);
                    com.android.server.job.controllers.QuotaController.this.mEJGracePeriodTopAppMs = java.lang.Math.min(3600000L, java.lang.Math.max(0L, this.EJ_GRACE_PERIOD_TOP_APP_MS));
                    break;
            }
        }

        private void updateExecutionPeriodConstantsLocked() {
            if (!this.mExecutionPeriodConstantsUpdated) {
                this.mExecutionPeriodConstantsUpdated = true;
                android.provider.DeviceConfig.Properties properties = android.provider.DeviceConfig.getProperties("jobscheduler", new java.lang.String[]{KEY_ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS, KEY_ALLOWED_TIME_PER_PERIOD_ACTIVE_MS, KEY_ALLOWED_TIME_PER_PERIOD_WORKING_MS, KEY_ALLOWED_TIME_PER_PERIOD_FREQUENT_MS, KEY_ALLOWED_TIME_PER_PERIOD_RARE_MS, KEY_ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS, KEY_IN_QUOTA_BUFFER_MS, KEY_MAX_EXECUTION_TIME_MS, KEY_WINDOW_SIZE_EXEMPTED_MS, KEY_WINDOW_SIZE_ACTIVE_MS, KEY_WINDOW_SIZE_WORKING_MS, KEY_WINDOW_SIZE_FREQUENT_MS, KEY_WINDOW_SIZE_RARE_MS, KEY_WINDOW_SIZE_RESTRICTED_MS});
                this.ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS = properties.getLong(KEY_ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS, 600000L);
                this.ALLOWED_TIME_PER_PERIOD_ACTIVE_MS = properties.getLong(KEY_ALLOWED_TIME_PER_PERIOD_ACTIVE_MS, 600000L);
                this.ALLOWED_TIME_PER_PERIOD_WORKING_MS = properties.getLong(KEY_ALLOWED_TIME_PER_PERIOD_WORKING_MS, 600000L);
                this.ALLOWED_TIME_PER_PERIOD_FREQUENT_MS = properties.getLong(KEY_ALLOWED_TIME_PER_PERIOD_FREQUENT_MS, 600000L);
                this.ALLOWED_TIME_PER_PERIOD_RARE_MS = properties.getLong(KEY_ALLOWED_TIME_PER_PERIOD_RARE_MS, 600000L);
                this.ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS = properties.getLong(KEY_ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS, 600000L);
                this.IN_QUOTA_BUFFER_MS = properties.getLong(KEY_IN_QUOTA_BUFFER_MS, 30000L);
                this.MAX_EXECUTION_TIME_MS = properties.getLong(KEY_MAX_EXECUTION_TIME_MS, 14400000L);
                this.WINDOW_SIZE_EXEMPTED_MS = properties.getLong(KEY_WINDOW_SIZE_EXEMPTED_MS, 600000L);
                this.WINDOW_SIZE_ACTIVE_MS = properties.getLong(KEY_WINDOW_SIZE_ACTIVE_MS, 600000L);
                this.WINDOW_SIZE_WORKING_MS = properties.getLong(KEY_WINDOW_SIZE_WORKING_MS, 7200000L);
                this.WINDOW_SIZE_FREQUENT_MS = properties.getLong(KEY_WINDOW_SIZE_FREQUENT_MS, 28800000L);
                this.WINDOW_SIZE_RARE_MS = properties.getLong(KEY_WINDOW_SIZE_RARE_MS, 86400000L);
                this.WINDOW_SIZE_RESTRICTED_MS = properties.getLong(KEY_WINDOW_SIZE_RESTRICTED_MS, 86400000L);
                long max = java.lang.Math.max(3600000L, java.lang.Math.min(86400000L, this.MAX_EXECUTION_TIME_MS));
                if (com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeMs != max) {
                    com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeMs = max;
                    com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeIntoQuotaMs = com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeMs - com.android.server.job.controllers.QuotaController.this.mQuotaBufferMs;
                    this.mShouldReevaluateConstraints = true;
                }
                long min = java.lang.Math.min(com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeMs, java.lang.Math.max(60000L, this.ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS));
                long min2 = java.lang.Math.min(com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, min);
                if (com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[6] != min) {
                    com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[6] = min;
                    this.mShouldReevaluateConstraints = true;
                }
                long min3 = java.lang.Math.min(com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeMs, java.lang.Math.max(60000L, this.ALLOWED_TIME_PER_PERIOD_ACTIVE_MS));
                long min4 = java.lang.Math.min(min2, min3);
                if (com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[0] != min3) {
                    com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[0] = min3;
                    this.mShouldReevaluateConstraints = true;
                }
                long min5 = java.lang.Math.min(com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeMs, java.lang.Math.max(60000L, this.ALLOWED_TIME_PER_PERIOD_WORKING_MS));
                long min6 = java.lang.Math.min(min4, min5);
                if (com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[1] != min5) {
                    com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[1] = min5;
                    this.mShouldReevaluateConstraints = true;
                }
                long min7 = java.lang.Math.min(com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeMs, java.lang.Math.max(60000L, this.ALLOWED_TIME_PER_PERIOD_FREQUENT_MS));
                long min8 = java.lang.Math.min(min6, min7);
                if (com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[2] != min7) {
                    com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[2] = min7;
                    this.mShouldReevaluateConstraints = true;
                }
                long min9 = java.lang.Math.min(com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeMs, java.lang.Math.max(60000L, this.ALLOWED_TIME_PER_PERIOD_RARE_MS));
                long min10 = java.lang.Math.min(min8, min9);
                if (com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[3] != min9) {
                    com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[3] = min9;
                    this.mShouldReevaluateConstraints = true;
                }
                long min11 = java.lang.Math.min(com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeMs, java.lang.Math.max(60000L, this.ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS));
                long min12 = java.lang.Math.min(min10, min11);
                if (com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[5] != min11) {
                    com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[5] = min11;
                    this.mShouldReevaluateConstraints = true;
                }
                long max2 = java.lang.Math.max(0L, java.lang.Math.min(min12, java.lang.Math.min(300000L, this.IN_QUOTA_BUFFER_MS)));
                if (com.android.server.job.controllers.QuotaController.this.mQuotaBufferMs != max2) {
                    com.android.server.job.controllers.QuotaController.this.mQuotaBufferMs = max2;
                    com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeIntoQuotaMs = com.android.server.job.controllers.QuotaController.this.mMaxExecutionTimeMs - com.android.server.job.controllers.QuotaController.this.mQuotaBufferMs;
                    this.mShouldReevaluateConstraints = true;
                }
                long max3 = java.lang.Math.max(com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[6], java.lang.Math.min(86400000L, this.WINDOW_SIZE_EXEMPTED_MS));
                if (com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[6] != max3) {
                    com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[6] = max3;
                    this.mShouldReevaluateConstraints = true;
                }
                long max4 = java.lang.Math.max(com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[0], java.lang.Math.min(86400000L, this.WINDOW_SIZE_ACTIVE_MS));
                if (com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[0] != max4) {
                    com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[0] = max4;
                    this.mShouldReevaluateConstraints = true;
                }
                long max5 = java.lang.Math.max(com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[1], java.lang.Math.min(86400000L, this.WINDOW_SIZE_WORKING_MS));
                if (com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[1] != max5) {
                    com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[1] = max5;
                    this.mShouldReevaluateConstraints = true;
                }
                long max6 = java.lang.Math.max(com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[2], java.lang.Math.min(86400000L, this.WINDOW_SIZE_FREQUENT_MS));
                if (com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[2] != max6) {
                    com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[2] = max6;
                    this.mShouldReevaluateConstraints = true;
                }
                long max7 = java.lang.Math.max(com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[3], java.lang.Math.min(86400000L, this.WINDOW_SIZE_RARE_MS));
                if (com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[3] != max7) {
                    com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[3] = max7;
                    this.mShouldReevaluateConstraints = true;
                }
                long max8 = java.lang.Math.max(com.android.server.job.controllers.QuotaController.this.mAllowedTimePerPeriodMs[5], java.lang.Math.min(com.android.server.usage.UnixCalendar.WEEK_IN_MILLIS, this.WINDOW_SIZE_RESTRICTED_MS));
                if (com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[5] != max8) {
                    com.android.server.job.controllers.QuotaController.this.mBucketPeriodsMs[5] = max8;
                    this.mShouldReevaluateConstraints = true;
                }
            }
        }

        private void updateRateLimitingConstantsLocked() {
            if (this.mRateLimitingConstantsUpdated) {
                return;
            }
            this.mRateLimitingConstantsUpdated = true;
            android.provider.DeviceConfig.Properties properties = android.provider.DeviceConfig.getProperties("jobscheduler", new java.lang.String[]{KEY_RATE_LIMITING_WINDOW_MS, KEY_MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW, KEY_MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW});
            this.RATE_LIMITING_WINDOW_MS = properties.getLong(KEY_RATE_LIMITING_WINDOW_MS, 60000L);
            this.MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW = properties.getInt(KEY_MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW, 20);
            this.MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW = properties.getInt(KEY_MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW, 20);
            long min = java.lang.Math.min(86400000L, java.lang.Math.max(30000L, this.RATE_LIMITING_WINDOW_MS));
            if (com.android.server.job.controllers.QuotaController.this.mRateLimitingWindowMs != min) {
                com.android.server.job.controllers.QuotaController.this.mRateLimitingWindowMs = min;
                this.mShouldReevaluateConstraints = true;
            }
            int max = java.lang.Math.max(10, this.MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW);
            if (com.android.server.job.controllers.QuotaController.this.mMaxJobCountPerRateLimitingWindow != max) {
                com.android.server.job.controllers.QuotaController.this.mMaxJobCountPerRateLimitingWindow = max;
                this.mShouldReevaluateConstraints = true;
            }
            int max2 = java.lang.Math.max(10, this.MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW);
            if (com.android.server.job.controllers.QuotaController.this.mMaxSessionCountPerRateLimitingWindow != max2) {
                com.android.server.job.controllers.QuotaController.this.mMaxSessionCountPerRateLimitingWindow = max2;
                this.mShouldReevaluateConstraints = true;
            }
        }

        private void updateEJLimitConstantsLocked() {
            if (this.mEJLimitConstantsUpdated) {
                return;
            }
            this.mEJLimitConstantsUpdated = true;
            android.provider.DeviceConfig.Properties properties = android.provider.DeviceConfig.getProperties("jobscheduler", new java.lang.String[]{KEY_EJ_LIMIT_EXEMPTED_MS, KEY_EJ_LIMIT_ACTIVE_MS, KEY_EJ_LIMIT_WORKING_MS, KEY_EJ_LIMIT_FREQUENT_MS, KEY_EJ_LIMIT_RARE_MS, KEY_EJ_LIMIT_RESTRICTED_MS, KEY_EJ_LIMIT_ADDITION_SPECIAL_MS, KEY_EJ_LIMIT_ADDITION_INSTALLER_MS, KEY_EJ_WINDOW_SIZE_MS});
            this.EJ_LIMIT_EXEMPTED_MS = properties.getLong(KEY_EJ_LIMIT_EXEMPTED_MS, 3600000L);
            this.EJ_LIMIT_ACTIVE_MS = properties.getLong(KEY_EJ_LIMIT_ACTIVE_MS, 1800000L);
            this.EJ_LIMIT_WORKING_MS = properties.getLong(KEY_EJ_LIMIT_WORKING_MS, 1800000L);
            this.EJ_LIMIT_FREQUENT_MS = properties.getLong(KEY_EJ_LIMIT_FREQUENT_MS, 600000L);
            this.EJ_LIMIT_RARE_MS = properties.getLong(KEY_EJ_LIMIT_RARE_MS, 600000L);
            this.EJ_LIMIT_RESTRICTED_MS = properties.getLong(KEY_EJ_LIMIT_RESTRICTED_MS, 300000L);
            this.EJ_LIMIT_ADDITION_INSTALLER_MS = properties.getLong(KEY_EJ_LIMIT_ADDITION_INSTALLER_MS, 1800000L);
            this.EJ_LIMIT_ADDITION_SPECIAL_MS = properties.getLong(KEY_EJ_LIMIT_ADDITION_SPECIAL_MS, DEFAULT_EJ_LIMIT_ADDITION_SPECIAL_MS);
            this.EJ_WINDOW_SIZE_MS = properties.getLong(KEY_EJ_WINDOW_SIZE_MS, 86400000L);
            long max = java.lang.Math.max(3600000L, java.lang.Math.min(86400000L, this.EJ_WINDOW_SIZE_MS));
            if (com.android.server.job.controllers.QuotaController.this.mEJLimitWindowSizeMs != max) {
                com.android.server.job.controllers.QuotaController.this.mEJLimitWindowSizeMs = max;
                this.mShouldReevaluateConstraints = true;
            }
            long max2 = java.lang.Math.max(DEFAULT_EJ_LIMIT_ADDITION_SPECIAL_MS, java.lang.Math.min(max, this.EJ_LIMIT_EXEMPTED_MS));
            if (com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[6] != max2) {
                com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[6] = max2;
                this.mShouldReevaluateConstraints = true;
            }
            long max3 = java.lang.Math.max(DEFAULT_EJ_LIMIT_ADDITION_SPECIAL_MS, java.lang.Math.min(max2, this.EJ_LIMIT_ACTIVE_MS));
            if (com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[0] != max3) {
                com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[0] = max3;
                this.mShouldReevaluateConstraints = true;
            }
            long max4 = java.lang.Math.max(DEFAULT_EJ_LIMIT_ADDITION_SPECIAL_MS, java.lang.Math.min(max3, this.EJ_LIMIT_WORKING_MS));
            if (com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[1] != max4) {
                com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[1] = max4;
                this.mShouldReevaluateConstraints = true;
            }
            long max5 = java.lang.Math.max(600000L, java.lang.Math.min(max4, this.EJ_LIMIT_FREQUENT_MS));
            if (com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[2] != max5) {
                com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[2] = max5;
                this.mShouldReevaluateConstraints = true;
            }
            long max6 = java.lang.Math.max(600000L, java.lang.Math.min(max5, this.EJ_LIMIT_RARE_MS));
            if (com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[3] != max6) {
                com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[3] = max6;
                this.mShouldReevaluateConstraints = true;
            }
            long max7 = java.lang.Math.max(300000L, java.lang.Math.min(max6, this.EJ_LIMIT_RESTRICTED_MS));
            if (com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[5] != max7) {
                com.android.server.job.controllers.QuotaController.this.mEJLimitsMs[5] = max7;
                this.mShouldReevaluateConstraints = true;
            }
            long j = max - max3;
            long max8 = java.lang.Math.max(0L, java.lang.Math.min(j, this.EJ_LIMIT_ADDITION_INSTALLER_MS));
            if (com.android.server.job.controllers.QuotaController.this.mEjLimitAdditionInstallerMs != max8) {
                com.android.server.job.controllers.QuotaController.this.mEjLimitAdditionInstallerMs = max8;
                this.mShouldReevaluateConstraints = true;
            }
            long max9 = java.lang.Math.max(0L, java.lang.Math.min(j, this.EJ_LIMIT_ADDITION_SPECIAL_MS));
            if (com.android.server.job.controllers.QuotaController.this.mEjLimitAdditionSpecialMs != max9) {
                com.android.server.job.controllers.QuotaController.this.mEjLimitAdditionSpecialMs = max9;
                this.mShouldReevaluateConstraints = true;
            }
        }

        private void updateQuotaBumpConstantsLocked() {
            if (this.mQuotaBumpConstantsUpdated) {
                return;
            }
            this.mQuotaBumpConstantsUpdated = true;
            android.provider.DeviceConfig.Properties properties = android.provider.DeviceConfig.getProperties("jobscheduler", new java.lang.String[]{KEY_QUOTA_BUMP_ADDITIONAL_DURATION_MS, KEY_QUOTA_BUMP_ADDITIONAL_JOB_COUNT, KEY_QUOTA_BUMP_ADDITIONAL_SESSION_COUNT, KEY_QUOTA_BUMP_WINDOW_SIZE_MS, KEY_QUOTA_BUMP_LIMIT});
            this.QUOTA_BUMP_ADDITIONAL_DURATION_MS = properties.getLong(KEY_QUOTA_BUMP_ADDITIONAL_DURATION_MS, 60000L);
            this.QUOTA_BUMP_ADDITIONAL_JOB_COUNT = properties.getInt(KEY_QUOTA_BUMP_ADDITIONAL_JOB_COUNT, 2);
            this.QUOTA_BUMP_ADDITIONAL_SESSION_COUNT = properties.getInt(KEY_QUOTA_BUMP_ADDITIONAL_SESSION_COUNT, 1);
            this.QUOTA_BUMP_WINDOW_SIZE_MS = properties.getLong(KEY_QUOTA_BUMP_WINDOW_SIZE_MS, 28800000L);
            this.QUOTA_BUMP_LIMIT = properties.getInt(KEY_QUOTA_BUMP_LIMIT, 8);
            long max = java.lang.Math.max(3600000L, java.lang.Math.min(86400000L, this.QUOTA_BUMP_WINDOW_SIZE_MS));
            if (com.android.server.job.controllers.QuotaController.this.mQuotaBumpWindowSizeMs != max) {
                com.android.server.job.controllers.QuotaController.this.mQuotaBumpWindowSizeMs = max;
                this.mShouldReevaluateConstraints = true;
            }
            int max2 = java.lang.Math.max(0, this.QUOTA_BUMP_LIMIT);
            if (com.android.server.job.controllers.QuotaController.this.mQuotaBumpLimit != max2) {
                com.android.server.job.controllers.QuotaController.this.mQuotaBumpLimit = max2;
                this.mShouldReevaluateConstraints = true;
            }
            int max3 = java.lang.Math.max(0, this.QUOTA_BUMP_ADDITIONAL_JOB_COUNT);
            if (com.android.server.job.controllers.QuotaController.this.mQuotaBumpAdditionalJobCount != max3) {
                com.android.server.job.controllers.QuotaController.this.mQuotaBumpAdditionalJobCount = max3;
                this.mShouldReevaluateConstraints = true;
            }
            int max4 = java.lang.Math.max(0, this.QUOTA_BUMP_ADDITIONAL_SESSION_COUNT);
            if (com.android.server.job.controllers.QuotaController.this.mQuotaBumpAdditionalSessionCount != max4) {
                com.android.server.job.controllers.QuotaController.this.mQuotaBumpAdditionalSessionCount = max4;
                this.mShouldReevaluateConstraints = true;
            }
            long max5 = java.lang.Math.max(0L, java.lang.Math.min(600000L, this.QUOTA_BUMP_ADDITIONAL_DURATION_MS));
            if (com.android.server.job.controllers.QuotaController.this.mQuotaBumpAdditionalDurationMs != max5) {
                com.android.server.job.controllers.QuotaController.this.mQuotaBumpAdditionalDurationMs = max5;
                this.mShouldReevaluateConstraints = true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println();
            indentingPrintWriter.println("QuotaController:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print(KEY_ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS, java.lang.Long.valueOf(this.ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS)).println();
            indentingPrintWriter.print(KEY_ALLOWED_TIME_PER_PERIOD_ACTIVE_MS, java.lang.Long.valueOf(this.ALLOWED_TIME_PER_PERIOD_ACTIVE_MS)).println();
            indentingPrintWriter.print(KEY_ALLOWED_TIME_PER_PERIOD_WORKING_MS, java.lang.Long.valueOf(this.ALLOWED_TIME_PER_PERIOD_WORKING_MS)).println();
            indentingPrintWriter.print(KEY_ALLOWED_TIME_PER_PERIOD_FREQUENT_MS, java.lang.Long.valueOf(this.ALLOWED_TIME_PER_PERIOD_FREQUENT_MS)).println();
            indentingPrintWriter.print(KEY_ALLOWED_TIME_PER_PERIOD_RARE_MS, java.lang.Long.valueOf(this.ALLOWED_TIME_PER_PERIOD_RARE_MS)).println();
            indentingPrintWriter.print(KEY_ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS, java.lang.Long.valueOf(this.ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS)).println();
            indentingPrintWriter.print(KEY_IN_QUOTA_BUFFER_MS, java.lang.Long.valueOf(this.IN_QUOTA_BUFFER_MS)).println();
            indentingPrintWriter.print(KEY_WINDOW_SIZE_EXEMPTED_MS, java.lang.Long.valueOf(this.WINDOW_SIZE_EXEMPTED_MS)).println();
            indentingPrintWriter.print(KEY_WINDOW_SIZE_ACTIVE_MS, java.lang.Long.valueOf(this.WINDOW_SIZE_ACTIVE_MS)).println();
            indentingPrintWriter.print(KEY_WINDOW_SIZE_WORKING_MS, java.lang.Long.valueOf(this.WINDOW_SIZE_WORKING_MS)).println();
            indentingPrintWriter.print(KEY_WINDOW_SIZE_FREQUENT_MS, java.lang.Long.valueOf(this.WINDOW_SIZE_FREQUENT_MS)).println();
            indentingPrintWriter.print(KEY_WINDOW_SIZE_RARE_MS, java.lang.Long.valueOf(this.WINDOW_SIZE_RARE_MS)).println();
            indentingPrintWriter.print(KEY_WINDOW_SIZE_RESTRICTED_MS, java.lang.Long.valueOf(this.WINDOW_SIZE_RESTRICTED_MS)).println();
            indentingPrintWriter.print(KEY_MAX_EXECUTION_TIME_MS, java.lang.Long.valueOf(this.MAX_EXECUTION_TIME_MS)).println();
            indentingPrintWriter.print(KEY_MAX_JOB_COUNT_EXEMPTED, java.lang.Integer.valueOf(this.MAX_JOB_COUNT_EXEMPTED)).println();
            indentingPrintWriter.print(KEY_MAX_JOB_COUNT_ACTIVE, java.lang.Integer.valueOf(this.MAX_JOB_COUNT_ACTIVE)).println();
            indentingPrintWriter.print(KEY_MAX_JOB_COUNT_WORKING, java.lang.Integer.valueOf(this.MAX_JOB_COUNT_WORKING)).println();
            indentingPrintWriter.print(KEY_MAX_JOB_COUNT_FREQUENT, java.lang.Integer.valueOf(this.MAX_JOB_COUNT_FREQUENT)).println();
            indentingPrintWriter.print(KEY_MAX_JOB_COUNT_RARE, java.lang.Integer.valueOf(this.MAX_JOB_COUNT_RARE)).println();
            indentingPrintWriter.print(KEY_MAX_JOB_COUNT_RESTRICTED, java.lang.Integer.valueOf(this.MAX_JOB_COUNT_RESTRICTED)).println();
            indentingPrintWriter.print(KEY_RATE_LIMITING_WINDOW_MS, java.lang.Long.valueOf(this.RATE_LIMITING_WINDOW_MS)).println();
            indentingPrintWriter.print(KEY_MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW, java.lang.Integer.valueOf(this.MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW)).println();
            indentingPrintWriter.print(KEY_MAX_SESSION_COUNT_EXEMPTED, java.lang.Integer.valueOf(this.MAX_SESSION_COUNT_EXEMPTED)).println();
            indentingPrintWriter.print(KEY_MAX_SESSION_COUNT_ACTIVE, java.lang.Integer.valueOf(this.MAX_SESSION_COUNT_ACTIVE)).println();
            indentingPrintWriter.print(KEY_MAX_SESSION_COUNT_WORKING, java.lang.Integer.valueOf(this.MAX_SESSION_COUNT_WORKING)).println();
            indentingPrintWriter.print(KEY_MAX_SESSION_COUNT_FREQUENT, java.lang.Integer.valueOf(this.MAX_SESSION_COUNT_FREQUENT)).println();
            indentingPrintWriter.print(KEY_MAX_SESSION_COUNT_RARE, java.lang.Integer.valueOf(this.MAX_SESSION_COUNT_RARE)).println();
            indentingPrintWriter.print(KEY_MAX_SESSION_COUNT_RESTRICTED, java.lang.Integer.valueOf(this.MAX_SESSION_COUNT_RESTRICTED)).println();
            indentingPrintWriter.print(KEY_MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW, java.lang.Integer.valueOf(this.MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW)).println();
            indentingPrintWriter.print(KEY_TIMING_SESSION_COALESCING_DURATION_MS, java.lang.Long.valueOf(this.TIMING_SESSION_COALESCING_DURATION_MS)).println();
            indentingPrintWriter.print(KEY_MIN_QUOTA_CHECK_DELAY_MS, java.lang.Long.valueOf(this.MIN_QUOTA_CHECK_DELAY_MS)).println();
            indentingPrintWriter.print(KEY_EJ_LIMIT_EXEMPTED_MS, java.lang.Long.valueOf(this.EJ_LIMIT_EXEMPTED_MS)).println();
            indentingPrintWriter.print(KEY_EJ_LIMIT_ACTIVE_MS, java.lang.Long.valueOf(this.EJ_LIMIT_ACTIVE_MS)).println();
            indentingPrintWriter.print(KEY_EJ_LIMIT_WORKING_MS, java.lang.Long.valueOf(this.EJ_LIMIT_WORKING_MS)).println();
            indentingPrintWriter.print(KEY_EJ_LIMIT_FREQUENT_MS, java.lang.Long.valueOf(this.EJ_LIMIT_FREQUENT_MS)).println();
            indentingPrintWriter.print(KEY_EJ_LIMIT_RARE_MS, java.lang.Long.valueOf(this.EJ_LIMIT_RARE_MS)).println();
            indentingPrintWriter.print(KEY_EJ_LIMIT_RESTRICTED_MS, java.lang.Long.valueOf(this.EJ_LIMIT_RESTRICTED_MS)).println();
            indentingPrintWriter.print(KEY_EJ_LIMIT_ADDITION_INSTALLER_MS, java.lang.Long.valueOf(this.EJ_LIMIT_ADDITION_INSTALLER_MS)).println();
            indentingPrintWriter.print(KEY_EJ_LIMIT_ADDITION_SPECIAL_MS, java.lang.Long.valueOf(this.EJ_LIMIT_ADDITION_SPECIAL_MS)).println();
            indentingPrintWriter.print(KEY_EJ_WINDOW_SIZE_MS, java.lang.Long.valueOf(this.EJ_WINDOW_SIZE_MS)).println();
            indentingPrintWriter.print(KEY_EJ_TOP_APP_TIME_CHUNK_SIZE_MS, java.lang.Long.valueOf(this.EJ_TOP_APP_TIME_CHUNK_SIZE_MS)).println();
            indentingPrintWriter.print(KEY_EJ_REWARD_TOP_APP_MS, java.lang.Long.valueOf(this.EJ_REWARD_TOP_APP_MS)).println();
            indentingPrintWriter.print(KEY_EJ_REWARD_INTERACTION_MS, java.lang.Long.valueOf(this.EJ_REWARD_INTERACTION_MS)).println();
            indentingPrintWriter.print(KEY_EJ_REWARD_NOTIFICATION_SEEN_MS, java.lang.Long.valueOf(this.EJ_REWARD_NOTIFICATION_SEEN_MS)).println();
            indentingPrintWriter.print(KEY_EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS, java.lang.Long.valueOf(this.EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS)).println();
            indentingPrintWriter.print(KEY_EJ_GRACE_PERIOD_TOP_APP_MS, java.lang.Long.valueOf(this.EJ_GRACE_PERIOD_TOP_APP_MS)).println();
            indentingPrintWriter.print(KEY_QUOTA_BUMP_ADDITIONAL_DURATION_MS, java.lang.Long.valueOf(this.QUOTA_BUMP_ADDITIONAL_DURATION_MS)).println();
            indentingPrintWriter.print(KEY_QUOTA_BUMP_ADDITIONAL_JOB_COUNT, java.lang.Integer.valueOf(this.QUOTA_BUMP_ADDITIONAL_JOB_COUNT)).println();
            indentingPrintWriter.print(KEY_QUOTA_BUMP_ADDITIONAL_SESSION_COUNT, java.lang.Integer.valueOf(this.QUOTA_BUMP_ADDITIONAL_SESSION_COUNT)).println();
            indentingPrintWriter.print(KEY_QUOTA_BUMP_WINDOW_SIZE_MS, java.lang.Long.valueOf(this.QUOTA_BUMP_WINDOW_SIZE_MS)).println();
            indentingPrintWriter.print(KEY_QUOTA_BUMP_LIMIT, java.lang.Integer.valueOf(this.QUOTA_BUMP_LIMIT)).println();
            indentingPrintWriter.decreaseIndent();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268056L);
            protoOutputStream.write(1112396529666L, this.IN_QUOTA_BUFFER_MS);
            protoOutputStream.write(1112396529667L, this.WINDOW_SIZE_ACTIVE_MS);
            protoOutputStream.write(1112396529668L, this.WINDOW_SIZE_WORKING_MS);
            protoOutputStream.write(1112396529669L, this.WINDOW_SIZE_FREQUENT_MS);
            protoOutputStream.write(1112396529670L, this.WINDOW_SIZE_RARE_MS);
            protoOutputStream.write(1112396529684L, this.WINDOW_SIZE_RESTRICTED_MS);
            protoOutputStream.write(1112396529671L, this.MAX_EXECUTION_TIME_MS);
            protoOutputStream.write(1120986464264L, this.MAX_JOB_COUNT_ACTIVE);
            protoOutputStream.write(1120986464265L, this.MAX_JOB_COUNT_WORKING);
            protoOutputStream.write(1120986464266L, this.MAX_JOB_COUNT_FREQUENT);
            protoOutputStream.write(1120986464267L, this.MAX_JOB_COUNT_RARE);
            protoOutputStream.write(1120986464277L, this.MAX_JOB_COUNT_RESTRICTED);
            protoOutputStream.write(1120986464275L, this.RATE_LIMITING_WINDOW_MS);
            protoOutputStream.write(1120986464268L, this.MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW);
            protoOutputStream.write(1120986464269L, this.MAX_SESSION_COUNT_ACTIVE);
            protoOutputStream.write(1120986464270L, this.MAX_SESSION_COUNT_WORKING);
            protoOutputStream.write(1120986464271L, this.MAX_SESSION_COUNT_FREQUENT);
            protoOutputStream.write(1120986464272L, this.MAX_SESSION_COUNT_RARE);
            protoOutputStream.write(1120986464278L, this.MAX_SESSION_COUNT_RESTRICTED);
            protoOutputStream.write(1120986464273L, this.MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW);
            protoOutputStream.write(1112396529682L, this.TIMING_SESSION_COALESCING_DURATION_MS);
            protoOutputStream.write(1112396529687L, this.MIN_QUOTA_CHECK_DELAY_MS);
            protoOutputStream.write(1112396529688L, this.EJ_LIMIT_ACTIVE_MS);
            protoOutputStream.write(1112396529689L, this.EJ_LIMIT_WORKING_MS);
            protoOutputStream.write(1112396529690L, this.EJ_LIMIT_FREQUENT_MS);
            protoOutputStream.write(1112396529691L, this.EJ_LIMIT_RARE_MS);
            protoOutputStream.write(1112396529692L, this.EJ_LIMIT_RESTRICTED_MS);
            protoOutputStream.write(1112396529693L, this.EJ_WINDOW_SIZE_MS);
            protoOutputStream.write(1112396529694L, this.EJ_TOP_APP_TIME_CHUNK_SIZE_MS);
            protoOutputStream.write(1112396529695L, this.EJ_REWARD_TOP_APP_MS);
            protoOutputStream.write(1112396529696L, this.EJ_REWARD_INTERACTION_MS);
            protoOutputStream.write(1112396529697L, this.EJ_REWARD_NOTIFICATION_SEEN_MS);
            protoOutputStream.end(start);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    long[] getAllowedTimePerPeriodMs() {
        return this.mAllowedTimePerPeriodMs;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    int[] getBucketMaxJobCounts() {
        return this.mMaxBucketJobCounts;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    int[] getBucketMaxSessionCounts() {
        return this.mMaxBucketSessionCounts;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    long[] getBucketWindowSizes() {
        return this.mBucketPeriodsMs;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    android.util.SparseBooleanArray getForegroundUids() {
        return this.mForegroundUids;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    android.os.Handler getHandler() {
        return this.mHandler;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getEJGracePeriodTempAllowlistMs() {
        return this.mEJGracePeriodTempAllowlistMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getEJGracePeriodTopAppMs() {
        return this.mEJGracePeriodTopAppMs;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    long[] getEJLimitsMs() {
        return this.mEJLimitsMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getEjLimitAdditionInstallerMs() {
        return this.mEjLimitAdditionInstallerMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getEjLimitAdditionSpecialMs() {
        return this.mEjLimitAdditionSpecialMs;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    long getEJLimitWindowSizeMs() {
        return this.mEJLimitWindowSizeMs;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    long getEJRewardInteractionMs() {
        return this.mEJRewardInteractionMs;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    long getEJRewardNotificationSeenMs() {
        return this.mEJRewardNotificationSeenMs;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    long getEJRewardTopAppMs() {
        return this.mEJRewardTopAppMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    java.util.List<com.android.server.job.controllers.QuotaController.TimedEvent> getEJTimingSessions(int i, java.lang.String str) {
        return (java.util.List) this.mEJTimingSessions.get(i, str);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    long getEJTopAppTimeChunkSizeMs() {
        return this.mEJTopAppTimeChunkSizeMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getInQuotaBufferMs() {
        return this.mQuotaBufferMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getMaxExecutionTimeMs() {
        return this.mMaxExecutionTimeMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getMaxJobCountPerRateLimitingWindow() {
        return this.mMaxJobCountPerRateLimitingWindow;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getMaxSessionCountPerRateLimitingWindow() {
        return this.mMaxSessionCountPerRateLimitingWindow;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getMinQuotaCheckDelayMs() {
        return this.mInQuotaAlarmQueue.getMinTimeBetweenAlarmsMs();
    }

    @com.android.internal.annotations.VisibleForTesting
    long getRateLimitingWindowMs() {
        return this.mRateLimitingWindowMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getTimingSessionCoalescingDurationMs() {
        return this.mTimingSessionCoalescingDurationMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    java.util.List<com.android.server.job.controllers.QuotaController.TimedEvent> getTimingSessions(int i, java.lang.String str) {
        return (java.util.List) this.mTimingEvents.get(i, str);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.job.controllers.QuotaController.QcConstants getQcConstants() {
        return this.mQcConstants;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getQuotaBumpAdditionDurationMs() {
        return this.mQuotaBumpAdditionalDurationMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getQuotaBumpAdditionJobCount() {
        return this.mQuotaBumpAdditionalJobCount;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getQuotaBumpAdditionSessionCount() {
        return this.mQuotaBumpAdditionalSessionCount;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getQuotaBumpLimit() {
        return this.mQuotaBumpLimit;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getQuotaBumpWindowSizeMs() {
        return this.mQuotaBumpWindowSizeMs;
    }

    @Override // com.android.server.job.controllers.StateController
    @dalvik.annotation.optimization.NeverCompile
    public void dumpControllerStateLocked(final android.util.IndentingPrintWriter indentingPrintWriter, final java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        indentingPrintWriter.println("Is enabled: " + this.mIsEnabled);
        indentingPrintWriter.println("Current elapsed time: " + com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
        indentingPrintWriter.println();
        indentingPrintWriter.print("Foreground UIDs: ");
        indentingPrintWriter.println(this.mForegroundUids.toString());
        indentingPrintWriter.println();
        indentingPrintWriter.print("Cached top apps: ");
        indentingPrintWriter.println(this.mTopAppCache.toString());
        indentingPrintWriter.print("Cached top app grace period: ");
        indentingPrintWriter.println(this.mTopAppGraceCache.toString());
        indentingPrintWriter.print("Cached temp allowlist: ");
        indentingPrintWriter.println(this.mTempAllowlistCache.toString());
        indentingPrintWriter.print("Cached temp allowlist grace period: ");
        indentingPrintWriter.println(this.mTempAllowlistGraceCache.toString());
        indentingPrintWriter.println();
        indentingPrintWriter.println("Special apps:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("System installers={");
        for (int i = 0; i < this.mSystemInstallers.size(); i++) {
            if (i > 0) {
                indentingPrintWriter.print(", ");
            }
            indentingPrintWriter.print(this.mSystemInstallers.keyAt(i));
            indentingPrintWriter.print("->");
            indentingPrintWriter.print(this.mSystemInstallers.get(i));
        }
        indentingPrintWriter.println("}");
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mTrackedJobs.forEach(new java.util.function.Consumer() { // from class: com.android.server.job.controllers.QuotaController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.controllers.QuotaController.this.lambda$dumpControllerStateLocked$4(predicate, indentingPrintWriter, (android.util.ArraySet) obj);
            }
        });
        indentingPrintWriter.println();
        for (int i2 = 0; i2 < this.mPkgTimers.numMaps(); i2++) {
            int keyAt = this.mPkgTimers.keyAt(i2);
            for (int i3 = 0; i3 < this.mPkgTimers.numElementsForKey(keyAt); i3++) {
                java.lang.String str = (java.lang.String) this.mPkgTimers.keyAt(i2, i3);
                ((com.android.server.job.controllers.QuotaController.Timer) this.mPkgTimers.valueAt(i2, i3)).dump(indentingPrintWriter, predicate);
                indentingPrintWriter.println();
                java.util.List list = (java.util.List) this.mTimingEvents.get(keyAt, str);
                if (list != null) {
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("Saved events:");
                    indentingPrintWriter.increaseIndent();
                    for (int size = list.size() - 1; size >= 0; size--) {
                        ((com.android.server.job.controllers.QuotaController.TimedEvent) list.get(size)).dump(indentingPrintWriter);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
            }
        }
        indentingPrintWriter.println();
        for (int i4 = 0; i4 < this.mEJPkgTimers.numMaps(); i4++) {
            int keyAt2 = this.mEJPkgTimers.keyAt(i4);
            for (int i5 = 0; i5 < this.mEJPkgTimers.numElementsForKey(keyAt2); i5++) {
                java.lang.String str2 = (java.lang.String) this.mEJPkgTimers.keyAt(i4, i5);
                ((com.android.server.job.controllers.QuotaController.Timer) this.mEJPkgTimers.valueAt(i4, i5)).dump(indentingPrintWriter, predicate);
                indentingPrintWriter.println();
                java.util.List list2 = (java.util.List) this.mEJTimingSessions.get(keyAt2, str2);
                if (list2 != null) {
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("Saved sessions:");
                    indentingPrintWriter.increaseIndent();
                    for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                        ((com.android.server.job.controllers.QuotaController.TimedEvent) list2.get(size2)).dump(indentingPrintWriter);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
            }
        }
        indentingPrintWriter.println();
        this.mTopAppTrackers.forEach(new java.util.function.Consumer() { // from class: com.android.server.job.controllers.QuotaController$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.job.controllers.QuotaController.TopAppTimer) obj).dump(indentingPrintWriter);
            }
        });
        indentingPrintWriter.println();
        indentingPrintWriter.println("Cached execution stats:");
        indentingPrintWriter.increaseIndent();
        for (int i6 = 0; i6 < this.mExecutionStatsCache.numMaps(); i6++) {
            int keyAt3 = this.mExecutionStatsCache.keyAt(i6);
            for (int i7 = 0; i7 < this.mExecutionStatsCache.numElementsForKey(keyAt3); i7++) {
                java.lang.String str3 = (java.lang.String) this.mExecutionStatsCache.keyAt(i6, i7);
                com.android.server.job.controllers.QuotaController.ExecutionStats[] executionStatsArr = (com.android.server.job.controllers.QuotaController.ExecutionStats[]) this.mExecutionStatsCache.valueAt(i6, i7);
                indentingPrintWriter.println(com.android.server.job.controllers.StateController.packageToString(keyAt3, str3));
                indentingPrintWriter.increaseIndent();
                for (int i8 = 0; i8 < executionStatsArr.length; i8++) {
                    com.android.server.job.controllers.QuotaController.ExecutionStats executionStats = executionStatsArr[i8];
                    if (executionStats != null) {
                        indentingPrintWriter.print(com.android.server.job.controllers.JobStatus.bucketName(i8));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(executionStats);
                    }
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("EJ debits:");
        indentingPrintWriter.increaseIndent();
        for (int i9 = 0; i9 < this.mEJStats.numMaps(); i9++) {
            int keyAt4 = this.mEJStats.keyAt(i9);
            for (int i10 = 0; i10 < this.mEJStats.numElementsForKey(keyAt4); i10++) {
                java.lang.String str4 = (java.lang.String) this.mEJStats.keyAt(i9, i10);
                com.android.server.job.controllers.QuotaController.ShrinkableDebits shrinkableDebits = (com.android.server.job.controllers.QuotaController.ShrinkableDebits) this.mEJStats.valueAt(i9, i10);
                indentingPrintWriter.print(com.android.server.job.controllers.StateController.packageToString(keyAt4, str4));
                indentingPrintWriter.print(": ");
                shrinkableDebits.dumpLocked(indentingPrintWriter);
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mInQuotaAlarmQueue.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpControllerStateLocked$4(java.util.function.Predicate predicate, android.util.IndentingPrintWriter indentingPrintWriter, android.util.ArraySet arraySet) {
        for (int i = 0; i < arraySet.size(); i++) {
            com.android.server.job.controllers.JobStatus jobStatus = (com.android.server.job.controllers.JobStatus) arraySet.valueAt(i);
            if (predicate.test(jobStatus)) {
                indentingPrintWriter.print("#");
                jobStatus.printUniqueId(indentingPrintWriter);
                indentingPrintWriter.print(" from ");
                android.os.UserHandle.formatUid(indentingPrintWriter, jobStatus.getSourceUid());
                if (this.mTopStartedJobs.contains(jobStatus)) {
                    indentingPrintWriter.print(" (TOP)");
                }
                indentingPrintWriter.println();
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print(com.android.server.job.controllers.JobStatus.bucketName(jobStatus.getEffectiveStandbyBucket()));
                indentingPrintWriter.print(", ");
                if (jobStatus.shouldTreatAsExpeditedJob()) {
                    indentingPrintWriter.print("within EJ quota");
                } else if (jobStatus.startedAsExpeditedJob) {
                    indentingPrintWriter.print("out of EJ quota");
                } else if (jobStatus.isConstraintSatisfied(16777216)) {
                    indentingPrintWriter.print("within regular quota");
                } else {
                    indentingPrintWriter.print("not within quota");
                }
                indentingPrintWriter.print(", ");
                if (jobStatus.shouldTreatAsExpeditedJob()) {
                    indentingPrintWriter.print(getRemainingEJExecutionTimeLocked(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName()));
                    indentingPrintWriter.print("ms remaining in EJ quota");
                } else if (jobStatus.startedAsExpeditedJob) {
                    indentingPrintWriter.print("should be stopped after min execution time");
                } else {
                    indentingPrintWriter.print(getRemainingExecutionTimeLocked(jobStatus));
                    indentingPrintWriter.print("ms remaining in quota");
                }
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(final android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        long j2;
        long j3;
        int i;
        long j4;
        final java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate2 = predicate;
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268041L);
        protoOutputStream.write(1133871366145L, this.mService.isBatteryCharging());
        protoOutputStream.write(1112396529670L, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
        for (int i2 = 0; i2 < this.mForegroundUids.size(); i2++) {
            protoOutputStream.write(2220498092035L, this.mForegroundUids.keyAt(i2));
        }
        this.mTrackedJobs.forEach(new java.util.function.Consumer() { // from class: com.android.server.job.controllers.QuotaController$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.controllers.QuotaController.this.lambda$dumpControllerStateLocked$6(predicate2, protoOutputStream, (android.util.ArraySet) obj);
            }
        });
        int i3 = 0;
        while (i3 < this.mPkgTimers.numMaps()) {
            int keyAt = this.mPkgTimers.keyAt(i3);
            int i4 = 0;
            while (i4 < this.mPkgTimers.numElementsForKey(keyAt)) {
                java.lang.String str = (java.lang.String) this.mPkgTimers.keyAt(i3, i4);
                long start3 = protoOutputStream.start(2246267895813L);
                ((com.android.server.job.controllers.QuotaController.Timer) this.mPkgTimers.valueAt(i3, i4)).dump(protoOutputStream, 1146756268034L, predicate2);
                com.android.server.job.controllers.QuotaController.Timer timer = (com.android.server.job.controllers.QuotaController.Timer) this.mEJPkgTimers.get(keyAt, str);
                if (timer != null) {
                    timer.dump(protoOutputStream, 1146756268038L, predicate2);
                }
                java.util.List list = (java.util.List) this.mTimingEvents.get(keyAt, str);
                if (list == null) {
                    j2 = start;
                } else {
                    int size = list.size() - 1;
                    while (size >= 0) {
                        com.android.server.job.controllers.QuotaController.TimedEvent timedEvent = (com.android.server.job.controllers.QuotaController.TimedEvent) list.get(size);
                        if (!(timedEvent instanceof com.android.server.job.controllers.QuotaController.TimingSession)) {
                            j4 = start;
                        } else {
                            j4 = start;
                            ((com.android.server.job.controllers.QuotaController.TimingSession) timedEvent).dump(protoOutputStream, 2246267895811L);
                        }
                        size--;
                        start = j4;
                    }
                    j2 = start;
                }
                com.android.server.job.controllers.QuotaController.ExecutionStats[] executionStatsArr = (com.android.server.job.controllers.QuotaController.ExecutionStats[]) this.mExecutionStatsCache.get(keyAt, str);
                if (executionStatsArr != null) {
                    int i5 = 0;
                    while (i5 < executionStatsArr.length) {
                        com.android.server.job.controllers.QuotaController.ExecutionStats executionStats = executionStatsArr[i5];
                        if (executionStats == null) {
                            j3 = start2;
                            i = i3;
                        } else {
                            long start4 = protoOutputStream.start(2246267895812L);
                            j3 = start2;
                            protoOutputStream.write(1159641169921L, i5);
                            i = i3;
                            protoOutputStream.write(1112396529666L, executionStats.expirationTimeElapsed);
                            protoOutputStream.write(1112396529667L, executionStats.windowSizeMs);
                            protoOutputStream.write(1120986464270L, executionStats.jobCountLimit);
                            protoOutputStream.write(1120986464271L, executionStats.sessionCountLimit);
                            protoOutputStream.write(1112396529668L, executionStats.executionTimeInWindowMs);
                            protoOutputStream.write(1120986464261L, executionStats.bgJobCountInWindow);
                            protoOutputStream.write(1112396529670L, executionStats.executionTimeInMaxPeriodMs);
                            protoOutputStream.write(1120986464263L, executionStats.bgJobCountInMaxPeriod);
                            protoOutputStream.write(1120986464267L, executionStats.sessionCountInWindow);
                            protoOutputStream.write(1112396529672L, executionStats.inQuotaTimeElapsed);
                            protoOutputStream.write(1112396529673L, executionStats.jobRateLimitExpirationTimeElapsed);
                            protoOutputStream.write(1120986464266L, executionStats.jobCountInRateLimitingWindow);
                            protoOutputStream.write(1112396529676L, executionStats.sessionRateLimitExpirationTimeElapsed);
                            protoOutputStream.write(1120986464269L, executionStats.sessionCountInRateLimitingWindow);
                            protoOutputStream.end(start4);
                        }
                        i5++;
                        i3 = i;
                        start2 = j3;
                    }
                }
                protoOutputStream.end(start3);
                i4++;
                predicate2 = predicate;
                i3 = i3;
                start = j2;
                start2 = start2;
            }
            i3++;
            predicate2 = predicate;
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpControllerStateLocked$6(java.util.function.Predicate predicate, android.util.proto.ProtoOutputStream protoOutputStream, android.util.ArraySet arraySet) {
        for (int i = 0; i < arraySet.size(); i++) {
            com.android.server.job.controllers.JobStatus jobStatus = (com.android.server.job.controllers.JobStatus) arraySet.valueAt(i);
            if (predicate.test(jobStatus)) {
                long start = protoOutputStream.start(2246267895812L);
                jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1120986464258L, jobStatus.getSourceUid());
                protoOutputStream.write(1159641169923L, jobStatus.getEffectiveStandbyBucket());
                protoOutputStream.write(1133871366148L, this.mTopStartedJobs.contains(jobStatus));
                protoOutputStream.write(1133871366149L, jobStatus.isConstraintSatisfied(16777216));
                protoOutputStream.write(1112396529670L, getRemainingExecutionTimeLocked(jobStatus));
                protoOutputStream.write(1133871366151L, jobStatus.isRequestedExpeditedJob());
                protoOutputStream.write(1133871366152L, jobStatus.isExpeditedQuotaApproved());
                protoOutputStream.end(start);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpConstants(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mQcConstants.dump(indentingPrintWriter);
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpConstants(android.util.proto.ProtoOutputStream protoOutputStream) {
        this.mQcConstants.dump(protoOutputStream);
    }
}
