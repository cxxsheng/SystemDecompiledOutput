package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public class TareController extends com.android.server.job.controllers.StateController {
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_RUNNING_DEFAULT;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_RUNNING_HIGH;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_RUNNING_HIGH_EXPEDITED;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_RUNNING_LOW;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_RUNNING_MAX;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_RUNNING_MAX_EXPEDITED;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_RUNNING_MIN;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_START_DEFAULT;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_START_HIGH;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_START_HIGH_EXPEDITED;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_START_LOW;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_START_MAX;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_START_MAX_EXPEDITED;
    private static final com.android.server.tare.EconomyManagerInternal.ActionBill BILL_JOB_START_MIN;
    private static final boolean DEBUG;
    private static final java.lang.String TAG = "JobScheduler.TARE";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, android.util.ArrayMap<com.android.server.tare.EconomyManagerInternal.ActionBill, java.lang.Boolean>> mAffordabilityCache;
    private final com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener mAffordabilityChangeListener;
    private final com.android.server.job.controllers.BackgroundJobsController mBackgroundJobsController;
    private final com.android.server.job.controllers.ConnectivityController mConnectivityController;
    private final com.android.server.tare.EconomyManagerInternal mEconomyManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, android.util.ArrayMap<com.android.server.tare.EconomyManagerInternal.ActionBill, android.util.ArraySet<com.android.server.job.controllers.JobStatus>>> mRegisteredBillsAndJobs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mTopStartedJobs;

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
        BILL_JOB_START_MIN = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_START, 1, 0L), new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_RUNNING, 0, 120000L)));
        BILL_JOB_RUNNING_MIN = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_RUNNING, 0, 60000L)));
        BILL_JOB_START_LOW = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_START, 1, 0L), new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_RUNNING, 0, 60000L)));
        BILL_JOB_RUNNING_LOW = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_RUNNING, 0, 30000L)));
        BILL_JOB_START_DEFAULT = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_START, 1, 0L), new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_RUNNING, 0, 30000L)));
        BILL_JOB_RUNNING_DEFAULT = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_RUNNING, 0, 1000L)));
        BILL_JOB_START_HIGH = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_HIGH_START, 1, 0L), new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_HIGH_RUNNING, 0, 30000L)));
        BILL_JOB_RUNNING_HIGH = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_HIGH_RUNNING, 0, 1000L)));
        BILL_JOB_START_MAX = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MAX_START, 1, 0L), new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MAX_RUNNING, 0, 30000L)));
        BILL_JOB_RUNNING_MAX = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MAX_RUNNING, 0, 1000L)));
        BILL_JOB_START_MAX_EXPEDITED = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MAX_START, 1, 0L), new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MAX_RUNNING, 0, 30000L)));
        BILL_JOB_RUNNING_MAX_EXPEDITED = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MAX_RUNNING, 0, 1000L)));
        BILL_JOB_START_HIGH_EXPEDITED = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_HIGH_START, 1, 0L), new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_HIGH_RUNNING, 0, 30000L)));
        BILL_JOB_RUNNING_HIGH_EXPEDITED = new com.android.server.tare.EconomyManagerInternal.ActionBill(java.util.List.of(new com.android.server.tare.EconomyManagerInternal.AnticipatedAction(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_HIGH_RUNNING, 0, 1000L)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008f A[Catch: all -> 0x004f, TryCatch #0 {all -> 0x004f, blocks: (B:7:0x003a, B:9:0x0044, B:10:0x0051, B:12:0x0062, B:14:0x006a, B:15:0x0071, B:17:0x0077, B:19:0x007f, B:23:0x0089, B:25:0x008f, B:26:0x0092, B:28:0x0098, B:30:0x00a2, B:32:0x00a5, B:37:0x00a8, B:39:0x00ae, B:40:0x00b3), top: B:6:0x003a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ void lambda$new$0(int i, java.lang.String str, com.android.server.tare.EconomyManagerInternal.ActionBill actionBill, boolean z) {
        android.util.ArraySet arraySet;
        boolean z2;
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (DEBUG) {
            android.util.Slog.d(TAG, i + ":" + str + " affordability for " + getBillName(actionBill) + " changed to " + z);
        }
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mAffordabilityCache.get(i, str);
                if (arrayMap == null) {
                    arrayMap = new android.util.ArrayMap();
                    this.mAffordabilityCache.add(i, str, arrayMap);
                }
                arrayMap.put(actionBill, java.lang.Boolean.valueOf(z));
                android.util.ArrayMap arrayMap2 = (android.util.ArrayMap) this.mRegisteredBillsAndJobs.get(i, str);
                if (arrayMap2 != null && (arraySet = (android.util.ArraySet) arrayMap2.get(actionBill)) != null) {
                    android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet2 = new android.util.ArraySet<>();
                    for (int i2 = 0; i2 < arraySet.size(); i2++) {
                        com.android.server.job.controllers.JobStatus jobStatus = (com.android.server.job.controllers.JobStatus) arraySet.valueAt(i2);
                        if (!z && !hasEnoughWealthLocked(jobStatus)) {
                            z2 = false;
                            if (jobStatus.setTareWealthConstraintSatisfied(millis, z2)) {
                                arraySet2.add(jobStatus);
                            }
                            if (jobStatus.isRequestedExpeditedJob() && setExpeditedTareApproved(jobStatus, millis, canAffordExpeditedBillLocked(jobStatus))) {
                                arraySet2.add(jobStatus);
                            }
                        }
                        z2 = true;
                        if (jobStatus.setTareWealthConstraintSatisfied(millis, z2)) {
                        }
                        if (jobStatus.isRequestedExpeditedJob()) {
                            arraySet2.add(jobStatus);
                        }
                    }
                    if (arraySet2.size() > 0) {
                        this.mStateChangedListener.onControllerStateChanged(arraySet2);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public TareController(com.android.server.job.JobSchedulerService jobSchedulerService, @android.annotation.NonNull com.android.server.job.controllers.BackgroundJobsController backgroundJobsController, @android.annotation.NonNull com.android.server.job.controllers.ConnectivityController connectivityController) {
        super(jobSchedulerService);
        this.mAffordabilityCache = new android.util.SparseArrayMap<>();
        this.mRegisteredBillsAndJobs = new android.util.SparseArrayMap<>();
        this.mAffordabilityChangeListener = new com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener() { // from class: com.android.server.job.controllers.TareController$$ExternalSyntheticLambda2
            @Override // com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener
            public final void onAffordabilityChanged(int i, java.lang.String str, com.android.server.tare.EconomyManagerInternal.ActionBill actionBill, boolean z) {
                com.android.server.job.controllers.TareController.this.lambda$new$0(i, str, actionBill, z);
            }
        };
        this.mTopStartedJobs = new android.util.ArraySet<>();
        this.mBackgroundJobsController = backgroundJobsController;
        this.mConnectivityController = connectivityController;
        this.mEconomyManagerInternal = (com.android.server.tare.EconomyManagerInternal) com.android.server.LocalServices.getService(com.android.server.tare.EconomyManagerInternal.class);
        this.mIsEnabled = this.mConstants.USE_TARE_POLICY;
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (jobStatus.shouldTreatAsUserInitiatedJob()) {
            jobStatus.setTareWealthConstraintSatisfied(millis, true);
            return;
        }
        jobStatus.setTareWealthConstraintSatisfied(millis, hasEnoughWealthLocked(jobStatus));
        setExpeditedTareApproved(jobStatus, millis, jobStatus.isRequestedExpeditedJob() && canAffordExpeditedBillLocked(jobStatus));
        android.util.ArraySet<com.android.server.tare.EconomyManagerInternal.ActionBill> possibleStartBills = getPossibleStartBills(jobStatus);
        for (int i = 0; i < possibleStartBills.size(); i++) {
            addJobToBillList(jobStatus, possibleStartBills.valueAt(i));
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void prepareForExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.shouldTreatAsUserInitiatedJob()) {
            return;
        }
        int sourceUserId = jobStatus.getSourceUserId();
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mRegisteredBillsAndJobs.get(sourceUserId, sourcePackageName);
        if (arrayMap == null) {
            android.util.Slog.e(TAG, "Job is being prepared but doesn't have a pre-existing billToJobMap");
        } else {
            for (int i = 0; i < arrayMap.size(); i++) {
                removeJobFromBillList(jobStatus, (com.android.server.tare.EconomyManagerInternal.ActionBill) arrayMap.keyAt(i));
            }
        }
        if (this.mService.getUidBias(jobStatus.getSourceUid()) == 40) {
            if (DEBUG) {
                android.util.Slog.d(TAG, jobStatus.toShortString() + " is top started job");
            }
            this.mTopStartedJobs.add(jobStatus);
            return;
        }
        addJobToBillList(jobStatus, getRunningBill(jobStatus));
        this.mEconomyManagerInternal.noteOngoingEventStarted(sourceUserId, sourcePackageName, getRunningActionId(jobStatus), java.lang.String.valueOf(jobStatus.getJobId()));
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void unprepareFromExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.shouldTreatAsUserInitiatedJob()) {
            return;
        }
        int sourceUserId = jobStatus.getSourceUserId();
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        if (!this.mTopStartedJobs.remove(jobStatus)) {
            this.mEconomyManagerInternal.noteOngoingEventStopped(sourceUserId, sourcePackageName, getRunningActionId(jobStatus), java.lang.String.valueOf(jobStatus.getJobId()));
        }
        android.util.ArraySet<com.android.server.tare.EconomyManagerInternal.ActionBill> possibleStartBills = getPossibleStartBills(jobStatus);
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mRegisteredBillsAndJobs.get(sourceUserId, sourcePackageName);
        if (arrayMap == null) {
            android.util.Slog.e(TAG, "Job was just unprepared but didn't have a pre-existing billToJobMap");
        } else {
            for (int i = 0; i < arrayMap.size(); i++) {
                removeJobFromBillList(jobStatus, (com.android.server.tare.EconomyManagerInternal.ActionBill) arrayMap.keyAt(i));
            }
        }
        for (int i2 = 0; i2 < possibleStartBills.size(); i2++) {
            addJobToBillList(jobStatus, possibleStartBills.valueAt(i2));
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.shouldTreatAsUserInitiatedJob()) {
            return;
        }
        int sourceUserId = jobStatus.getSourceUserId();
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        if (!this.mTopStartedJobs.remove(jobStatus) && jobStatus.madeActive > 0) {
            this.mEconomyManagerInternal.noteOngoingEventStopped(sourceUserId, sourcePackageName, getRunningActionId(jobStatus), java.lang.String.valueOf(jobStatus.getJobId()));
        }
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mRegisteredBillsAndJobs.get(sourceUserId, sourcePackageName);
        if (arrayMap != null) {
            for (int i = 0; i < arrayMap.size(); i++) {
                removeJobFromBillList(jobStatus, (com.android.server.tare.EconomyManagerInternal.ActionBill) arrayMap.keyAt(i));
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onConstantsUpdatedLocked() {
        if (this.mIsEnabled != this.mConstants.USE_TARE_POLICY) {
            this.mIsEnabled = this.mConstants.USE_TARE_POLICY;
            com.android.server.AppSchedulingModuleThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.job.controllers.TareController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.job.controllers.TareController.this.lambda$onConstantsUpdatedLocked$2();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConstantsUpdatedLocked$2() {
        synchronized (this.mLock) {
            final long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            this.mService.getJobStore().forEachJob(new java.util.function.Consumer() { // from class: com.android.server.job.controllers.TareController$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.job.controllers.TareController.this.lambda$onConstantsUpdatedLocked$1(millis, (com.android.server.job.controllers.JobStatus) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConstantsUpdatedLocked$1(long j, com.android.server.job.controllers.JobStatus jobStatus) {
        if (!this.mIsEnabled) {
            jobStatus.setTareWealthConstraintSatisfied(j, true);
            setExpeditedTareApproved(jobStatus, j, true);
        } else {
            jobStatus.setTareWealthConstraintSatisfied(j, hasEnoughWealthLocked(jobStatus));
            setExpeditedTareApproved(jobStatus, j, jobStatus.isRequestedExpeditedJob() && canAffordExpeditedBillLocked(jobStatus));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean canScheduleEJ(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        if (!this.mIsEnabled) {
            return true;
        }
        if (jobStatus.getEffectivePriority() == 500) {
            return canAffordBillLocked(jobStatus, BILL_JOB_START_MAX_EXPEDITED);
        }
        return canAffordBillLocked(jobStatus, BILL_JOB_START_HIGH_EXPEDITED);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isTopStartedJobLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        return this.mTopStartedJobs.contains(jobStatus);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public long getMaxJobExecutionTimeMsLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        if (!this.mIsEnabled) {
            return this.mConstants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
        }
        return this.mEconomyManagerInternal.getMaxDurationMs(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName(), getRunningBill(jobStatus));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addJobToBillList(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
        int sourceUserId = jobStatus.getSourceUserId();
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mRegisteredBillsAndJobs.get(sourceUserId, sourcePackageName);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap();
            this.mRegisteredBillsAndJobs.add(sourceUserId, sourcePackageName, arrayMap);
        }
        android.util.ArraySet arraySet = (android.util.ArraySet) arrayMap.get(actionBill);
        if (arraySet == null) {
            arraySet = new android.util.ArraySet();
            arrayMap.put(actionBill, arraySet);
        }
        if (arraySet.add(jobStatus)) {
            this.mEconomyManagerInternal.registerAffordabilityChangeListener(sourceUserId, sourcePackageName, this.mAffordabilityChangeListener, actionBill);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void removeJobFromBillList(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
        int sourceUserId = jobStatus.getSourceUserId();
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mRegisteredBillsAndJobs.get(sourceUserId, sourcePackageName);
        if (arrayMap != null) {
            android.util.ArraySet arraySet = (android.util.ArraySet) arrayMap.get(actionBill);
            if (arraySet == null || (arraySet.remove(jobStatus) && arraySet.size() == 0)) {
                this.mEconomyManagerInternal.unregisterAffordabilityChangeListener(sourceUserId, sourcePackageName, this.mAffordabilityChangeListener, actionBill);
                android.util.ArrayMap arrayMap2 = (android.util.ArrayMap) this.mAffordabilityCache.get(sourceUserId, sourcePackageName);
                if (arrayMap2 != null) {
                    arrayMap2.remove(actionBill);
                }
            }
        }
    }

    @android.annotation.NonNull
    private android.util.ArraySet<com.android.server.tare.EconomyManagerInternal.ActionBill> getPossibleStartBills(com.android.server.job.controllers.JobStatus jobStatus) {
        android.util.ArraySet<com.android.server.tare.EconomyManagerInternal.ActionBill> arraySet = new android.util.ArraySet<>();
        if (jobStatus.isRequestedExpeditedJob()) {
            if (jobStatus.getEffectivePriority() == 500) {
                arraySet.add(BILL_JOB_START_MAX_EXPEDITED);
            } else {
                arraySet.add(BILL_JOB_START_HIGH_EXPEDITED);
            }
        }
        switch (jobStatus.getEffectivePriority()) {
            case 100:
                arraySet.add(BILL_JOB_START_MIN);
                return arraySet;
            case 200:
                arraySet.add(BILL_JOB_START_LOW);
                return arraySet;
            case 300:
                arraySet.add(BILL_JOB_START_DEFAULT);
                return arraySet;
            case 400:
                arraySet.add(BILL_JOB_START_HIGH);
                return arraySet;
            case 500:
                arraySet.add(BILL_JOB_START_MAX);
                return arraySet;
            default:
                android.util.Slog.wtf(TAG, "Unexpected priority: " + android.app.job.JobInfo.getPriorityString(jobStatus.getEffectivePriority()));
                return arraySet;
        }
    }

    @android.annotation.NonNull
    private com.android.server.tare.EconomyManagerInternal.ActionBill getRunningBill(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.shouldTreatAsExpeditedJob() || jobStatus.startedAsExpeditedJob) {
            if (jobStatus.getEffectivePriority() == 500) {
                return BILL_JOB_RUNNING_MAX_EXPEDITED;
            }
            return BILL_JOB_RUNNING_HIGH_EXPEDITED;
        }
        switch (jobStatus.getEffectivePriority()) {
            case 100:
                return BILL_JOB_RUNNING_MIN;
            case 200:
                return BILL_JOB_RUNNING_LOW;
            case 300:
                break;
            case 400:
                return BILL_JOB_RUNNING_HIGH;
            case 500:
                return BILL_JOB_RUNNING_MAX;
            default:
                android.util.Slog.wtf(TAG, "Got unexpected priority: " + jobStatus.getEffectivePriority());
                break;
        }
        return BILL_JOB_RUNNING_DEFAULT;
    }

    private static int getRunningActionId(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        switch (jobStatus.getEffectivePriority()) {
            case 100:
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MIN_RUNNING;
            case 200:
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_LOW_RUNNING;
            case 300:
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_RUNNING;
            case 400:
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_HIGH_RUNNING;
            case 500:
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MAX_RUNNING;
            default:
                android.util.Slog.wtf(TAG, "Unknown priority: " + android.app.job.JobInfo.getPriorityString(jobStatus.getEffectivePriority()));
                return com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_DEFAULT_RUNNING;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean canAffordBillLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
        if (!this.mIsEnabled || this.mService.getUidBias(jobStatus.getSourceUid()) == 40 || isTopStartedJobLocked(jobStatus)) {
            return true;
        }
        int sourceUserId = jobStatus.getSourceUserId();
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mAffordabilityCache.get(sourceUserId, sourcePackageName);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap();
            this.mAffordabilityCache.add(sourceUserId, sourcePackageName, arrayMap);
        }
        if (arrayMap.containsKey(actionBill)) {
            return ((java.lang.Boolean) arrayMap.get(actionBill)).booleanValue();
        }
        boolean canPayFor = this.mEconomyManagerInternal.canPayFor(sourceUserId, sourcePackageName, actionBill);
        arrayMap.put(actionBill, java.lang.Boolean.valueOf(canPayFor));
        return canPayFor;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean canAffordExpeditedBillLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        if (!this.mIsEnabled) {
            return true;
        }
        if (!jobStatus.isRequestedExpeditedJob()) {
            return false;
        }
        if (this.mService.getUidBias(jobStatus.getSourceUid()) == 40 || isTopStartedJobLocked(jobStatus)) {
            return true;
        }
        if (this.mService.isCurrentlyRunningLocked(jobStatus)) {
            return canAffordBillLocked(jobStatus, getRunningBill(jobStatus));
        }
        if (jobStatus.getEffectivePriority() == 500) {
            return canAffordBillLocked(jobStatus, BILL_JOB_START_MAX_EXPEDITED);
        }
        return canAffordBillLocked(jobStatus, BILL_JOB_START_HIGH_EXPEDITED);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean hasEnoughWealthLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        if (!this.mIsEnabled || jobStatus.shouldTreatAsUserInitiatedJob() || this.mService.getUidBias(jobStatus.getSourceUid()) == 40 || isTopStartedJobLocked(jobStatus)) {
            return true;
        }
        if (this.mService.isCurrentlyRunningLocked(jobStatus)) {
            return canAffordBillLocked(jobStatus, getRunningBill(jobStatus));
        }
        android.util.ArraySet<com.android.server.tare.EconomyManagerInternal.ActionBill> possibleStartBills = getPossibleStartBills(jobStatus);
        for (int i = 0; i < possibleStartBills.size(); i++) {
            if (canAffordBillLocked(jobStatus, possibleStartBills.valueAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean setExpeditedTareApproved(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, long j, boolean z) {
        if (jobStatus.setExpeditedJobTareApproved(j, z)) {
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

    @android.annotation.NonNull
    private java.lang.String getBillName(@android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
        if (actionBill.equals(BILL_JOB_START_MAX_EXPEDITED)) {
            return "EJ_MAX_START_BILL";
        }
        if (actionBill.equals(BILL_JOB_RUNNING_MAX_EXPEDITED)) {
            return "EJ_MAX_RUNNING_BILL";
        }
        if (actionBill.equals(BILL_JOB_START_HIGH_EXPEDITED)) {
            return "EJ_HIGH_START_BILL";
        }
        if (actionBill.equals(BILL_JOB_RUNNING_HIGH_EXPEDITED)) {
            return "EJ_HIGH_RUNNING_BILL";
        }
        if (actionBill.equals(BILL_JOB_START_HIGH)) {
            return "HIGH_START_BILL";
        }
        if (actionBill.equals(BILL_JOB_RUNNING_HIGH)) {
            return "HIGH_RUNNING_BILL";
        }
        if (actionBill.equals(BILL_JOB_START_DEFAULT)) {
            return "DEFAULT_START_BILL";
        }
        if (actionBill.equals(BILL_JOB_RUNNING_DEFAULT)) {
            return "DEFAULT_RUNNING_BILL";
        }
        if (actionBill.equals(BILL_JOB_START_LOW)) {
            return "LOW_START_BILL";
        }
        if (actionBill.equals(BILL_JOB_RUNNING_LOW)) {
            return "LOW_RUNNING_BILL";
        }
        if (actionBill.equals(BILL_JOB_START_MIN)) {
            return "MIN_START_BILL";
        }
        if (actionBill.equals(BILL_JOB_RUNNING_MIN)) {
            return "MIN_RUNNING_BILL";
        }
        return "UNKNOWN_BILL (" + actionBill + ")";
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(final android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        indentingPrintWriter.print("Is enabled: ");
        indentingPrintWriter.println(this.mIsEnabled);
        indentingPrintWriter.println("Affordability cache:");
        indentingPrintWriter.increaseIndent();
        this.mAffordabilityCache.forEach(new android.util.SparseArrayMap.TriConsumer() { // from class: com.android.server.job.controllers.TareController$$ExternalSyntheticLambda0
            public final void accept(int i, java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.job.controllers.TareController.this.lambda$dumpControllerStateLocked$3(indentingPrintWriter, i, (java.lang.String) obj, (android.util.ArrayMap) obj2);
            }
        });
        indentingPrintWriter.decreaseIndent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpControllerStateLocked$3(android.util.IndentingPrintWriter indentingPrintWriter, int i, java.lang.String str, android.util.ArrayMap arrayMap) {
        int size = arrayMap.size();
        if (size > 0) {
            indentingPrintWriter.print(i);
            indentingPrintWriter.print(":");
            indentingPrintWriter.print(str);
            indentingPrintWriter.println(":");
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < size; i2++) {
                indentingPrintWriter.print(getBillName((com.android.server.tare.EconomyManagerInternal.ActionBill) arrayMap.keyAt(i2)));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.println(arrayMap.valueAt(i2));
            }
            indentingPrintWriter.decreaseIndent();
        }
    }
}
