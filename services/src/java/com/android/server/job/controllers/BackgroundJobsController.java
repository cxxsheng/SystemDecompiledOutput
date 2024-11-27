package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public final class BackgroundJobsController extends com.android.server.job.controllers.StateController {
    private static final boolean DEBUG;
    static final int KNOWN_ACTIVE = 1;
    static final int KNOWN_INACTIVE = 2;
    private static final java.lang.String TAG = "JobScheduler.Background";
    static final int UNKNOWN = 0;
    private final android.app.ActivityManagerInternal mActivityManagerInternal;
    private final com.android.server.AppStateTrackerImpl mAppStateTracker;
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private final com.android.server.AppStateTrackerImpl.Listener mForceAppStandbyListener;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, java.lang.Boolean> mPackageStoppedState;
    private final com.android.server.job.controllers.BackgroundJobsController.UpdateJobFunctor mUpdateJobFunctor;

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    public BackgroundJobsController(com.android.server.job.JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mPackageStoppedState = new android.util.SparseArrayMap<>();
        this.mUpdateJobFunctor = new com.android.server.job.controllers.BackgroundJobsController.UpdateJobFunctor();
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.job.controllers.BackgroundJobsController.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                java.lang.String packageName = com.android.server.job.JobSchedulerService.getPackageName(intent);
                char c = 65535;
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                java.lang.String action = intent.getAction();
                if (intExtra == -1) {
                    android.util.Slog.e(com.android.server.job.controllers.BackgroundJobsController.TAG, "Didn't get package UID in intent (" + action + ")");
                    return;
                }
                if (com.android.server.job.controllers.BackgroundJobsController.DEBUG) {
                    android.util.Slog.d(com.android.server.job.controllers.BackgroundJobsController.TAG, "Got " + action + " for " + intExtra + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + packageName);
                }
                switch (action.hashCode()) {
                    case -757780528:
                        if (action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 928080374:
                        if (action.equals("android.intent.action.PACKAGE_UNSTOPPED")) {
                            c = 1;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        synchronized (com.android.server.job.controllers.BackgroundJobsController.this.mLock) {
                            com.android.server.job.controllers.BackgroundJobsController.this.mPackageStoppedState.delete(intExtra, packageName);
                            com.android.server.job.controllers.BackgroundJobsController.this.updateJobRestrictionsForUidLocked(intExtra, false);
                        }
                        return;
                    case 1:
                        synchronized (com.android.server.job.controllers.BackgroundJobsController.this.mLock) {
                            com.android.server.job.controllers.BackgroundJobsController.this.mPackageStoppedState.add(intExtra, packageName, java.lang.Boolean.FALSE);
                            com.android.server.job.controllers.BackgroundJobsController.this.updateJobRestrictionsLocked(intExtra, 0);
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        this.mForceAppStandbyListener = new com.android.server.AppStateTrackerImpl.Listener() { // from class: com.android.server.job.controllers.BackgroundJobsController.2
            @Override // com.android.server.AppStateTrackerImpl.Listener
            public void updateAllJobs() {
                synchronized (com.android.server.job.controllers.BackgroundJobsController.this.mLock) {
                    com.android.server.job.controllers.BackgroundJobsController.this.updateAllJobRestrictionsLocked();
                }
            }

            @Override // com.android.server.AppStateTrackerImpl.Listener
            public void updateJobsForUid(int i, boolean z) {
                synchronized (com.android.server.job.controllers.BackgroundJobsController.this.mLock) {
                    com.android.server.job.controllers.BackgroundJobsController.this.updateJobRestrictionsForUidLocked(i, z);
                }
            }

            @Override // com.android.server.AppStateTrackerImpl.Listener
            public void updateJobsForUidPackage(int i, java.lang.String str, boolean z) {
                synchronized (com.android.server.job.controllers.BackgroundJobsController.this.mLock) {
                    com.android.server.job.controllers.BackgroundJobsController.this.updateJobRestrictionsForUidLocked(i, z);
                }
            }
        };
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        java.util.Objects.requireNonNull(activityManagerInternal);
        this.mActivityManagerInternal = activityManagerInternal;
        com.android.server.AppStateTracker appStateTracker = (com.android.server.AppStateTracker) com.android.server.LocalServices.getService(com.android.server.AppStateTracker.class);
        java.util.Objects.requireNonNull(appStateTracker);
        this.mAppStateTracker = (com.android.server.AppStateTrackerImpl) appStateTracker;
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void startTrackingLocked() {
        this.mAppStateTracker.addListener(this.mForceAppStandbyListener);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addAction("android.intent.action.PACKAGE_UNSTOPPED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        updateSingleJobRestrictionLocked(jobStatus, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis(), 0);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void evaluateStateLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.isRequestedExpeditedJob()) {
            updateSingleJobRestrictionLocked(jobStatus, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis(), 0);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onAppRemovedLocked(java.lang.String str, int i) {
        this.mPackageStoppedState.delete(i, str);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onUserRemovedLocked(int i) {
        for (int numMaps = this.mPackageStoppedState.numMaps() - 1; numMaps >= 0; numMaps--) {
            if (android.os.UserHandle.getUserId(this.mPackageStoppedState.keyAt(numMaps)) == i) {
                this.mPackageStoppedState.deleteAt(numMaps);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpControllerStateLocked(final android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        indentingPrintWriter.println("Aconfig flags:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("android.content.pm.stay_stopped", java.lang.Boolean.valueOf(android.content.pm.Flags.stayStopped()));
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mAppStateTracker.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.println("Stopped packages:");
        indentingPrintWriter.increaseIndent();
        this.mPackageStoppedState.forEach(new android.util.SparseArrayMap.TriConsumer() { // from class: com.android.server.job.controllers.BackgroundJobsController$$ExternalSyntheticLambda0
            public final void accept(int i, java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.job.controllers.BackgroundJobsController.lambda$dumpControllerStateLocked$0(indentingPrintWriter, i, (java.lang.String) obj, (java.lang.Boolean) obj2);
            }
        });
        indentingPrintWriter.println();
        this.mService.getJobStore().forEachJob(predicate, new java.util.function.Consumer() { // from class: com.android.server.job.controllers.BackgroundJobsController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.controllers.BackgroundJobsController.this.lambda$dumpControllerStateLocked$1(indentingPrintWriter, (com.android.server.job.controllers.JobStatus) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpControllerStateLocked$0(android.util.IndentingPrintWriter indentingPrintWriter, int i, java.lang.String str, java.lang.Boolean bool) {
        indentingPrintWriter.print(i);
        indentingPrintWriter.print(":");
        indentingPrintWriter.print(str);
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(bool);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpControllerStateLocked$1(android.util.IndentingPrintWriter indentingPrintWriter, com.android.server.job.controllers.JobStatus jobStatus) {
        int sourceUid = jobStatus.getSourceUid();
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        indentingPrintWriter.print("#");
        jobStatus.printUniqueId(indentingPrintWriter);
        indentingPrintWriter.print(" from ");
        android.os.UserHandle.formatUid(indentingPrintWriter, sourceUid);
        indentingPrintWriter.print(this.mAppStateTracker.isUidActive(sourceUid) ? " active" : " idle");
        if (this.mAppStateTracker.isUidPowerSaveExempt(sourceUid) || this.mAppStateTracker.isUidTempPowerSaveExempt(sourceUid)) {
            indentingPrintWriter.print(", exempted");
        }
        indentingPrintWriter.print(": ");
        indentingPrintWriter.print(sourcePackageName);
        indentingPrintWriter.print(" [RUN_ANY_IN_BACKGROUND ");
        indentingPrintWriter.print(this.mAppStateTracker.isRunAnyInBackgroundAppOpsAllowed(sourceUid, sourcePackageName) ? "allowed]" : "disallowed]");
        if ((jobStatus.satisfiedConstraints & 4194304) != 0) {
            indentingPrintWriter.println(" RUNNABLE");
        } else {
            indentingPrintWriter.println(" WAITING");
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpControllerStateLocked(final android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268033L);
        this.mAppStateTracker.dumpProto(protoOutputStream, 1146756268033L);
        this.mService.getJobStore().forEachJob(predicate, new java.util.function.Consumer() { // from class: com.android.server.job.controllers.BackgroundJobsController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.controllers.BackgroundJobsController.this.lambda$dumpControllerStateLocked$2(protoOutputStream, (com.android.server.job.controllers.JobStatus) obj);
            }
        });
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpControllerStateLocked$2(android.util.proto.ProtoOutputStream protoOutputStream, com.android.server.job.controllers.JobStatus jobStatus) {
        long start = protoOutputStream.start(2246267895810L);
        jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
        int sourceUid = jobStatus.getSourceUid();
        protoOutputStream.write(1120986464258L, sourceUid);
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        protoOutputStream.write(1138166333443L, sourcePackageName);
        protoOutputStream.write(1133871366148L, this.mAppStateTracker.isUidActive(sourceUid));
        protoOutputStream.write(1133871366149L, this.mAppStateTracker.isUidPowerSaveExempt(sourceUid) || this.mAppStateTracker.isUidTempPowerSaveExempt(sourceUid));
        protoOutputStream.write(1133871366150L, this.mAppStateTracker.isRunAnyInBackgroundAppOpsAllowed(sourceUid, sourcePackageName));
        protoOutputStream.write(1133871366151L, (jobStatus.satisfiedConstraints & 4194304) != 0);
        protoOutputStream.end(start);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateAllJobRestrictionsLocked() {
        updateJobRestrictionsLocked(-1, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateJobRestrictionsForUidLocked(int i, boolean z) {
        updateJobRestrictionsLocked(i, z ? 1 : 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateJobRestrictionsLocked(int i, int i2) {
        this.mUpdateJobFunctor.prepare(i2);
        long elapsedRealtimeNanos = DEBUG ? android.os.SystemClock.elapsedRealtimeNanos() : 0L;
        com.android.server.job.JobStore jobStore = this.mService.getJobStore();
        if (i > 0) {
            jobStore.forEachJobForSourceUid(i, this.mUpdateJobFunctor);
        } else {
            jobStore.forEachJob(this.mUpdateJobFunctor);
        }
        long elapsedRealtimeNanos2 = DEBUG ? android.os.SystemClock.elapsedRealtimeNanos() - elapsedRealtimeNanos : 0L;
        if (DEBUG) {
            android.util.Slog.d(TAG, java.lang.String.format("Job status updated: %d/%d checked/total jobs, %d us", java.lang.Integer.valueOf(this.mUpdateJobFunctor.mCheckedCount), java.lang.Integer.valueOf(this.mUpdateJobFunctor.mTotalCount), java.lang.Long.valueOf(elapsedRealtimeNanos2 / 1000)));
        }
        if (this.mUpdateJobFunctor.mChangedJobs.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(this.mUpdateJobFunctor.mChangedJobs);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isPackageStoppedLocked(java.lang.String str, int i) {
        if (this.mPackageStoppedState.contains(i, str)) {
            return ((java.lang.Boolean) this.mPackageStoppedState.get(i, str)).booleanValue();
        }
        try {
            boolean isPackageStopped = this.mPackageManagerInternal.isPackageStopped(str, i);
            if (DEBUG) {
                android.util.Slog.d(TAG, "Pulled stopped state of " + str + " (" + i + "): " + isPackageStopped);
            }
            this.mPackageStoppedState.add(i, str, java.lang.Boolean.valueOf(isPackageStopped));
            return isPackageStopped;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Couldn't determine stopped state for unknown package: " + str);
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean updateSingleJobRestrictionLocked(com.android.server.job.controllers.JobStatus jobStatus, long j, int i) {
        boolean isPackageStoppedLocked;
        boolean z;
        int sourceUid = jobStatus.getSourceUid();
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        boolean isPackageStoppedLocked2 = isPackageStoppedLocked(jobStatus.getSourcePackageName(), jobStatus.getSourceUid());
        if (!jobStatus.isProxyJob()) {
            isPackageStoppedLocked = isPackageStoppedLocked2;
        } else {
            isPackageStoppedLocked = isPackageStoppedLocked(jobStatus.getCallingPackageName(), jobStatus.getUid());
        }
        boolean z2 = android.content.pm.Flags.stayStopped() && (isPackageStoppedLocked || isPackageStoppedLocked2);
        boolean z3 = z2 || !(this.mActivityManagerInternal.isBgAutoRestrictedBucketFeatureFlagEnabled() || this.mAppStateTracker.isRunAnyInBackgroundAppOpsAllowed(sourceUid, sourcePackageName));
        boolean z4 = (z2 || (jobStatus.startedWithForegroundFlag && z3 && this.mService.getUidProcState(sourceUid) > 5) || this.mAppStateTracker.areJobsRestricted(sourceUid, sourcePackageName, jobStatus.canRunInBatterySaver())) ? false : true;
        if (i == 0) {
            z = this.mAppStateTracker.isUidActive(sourceUid);
        } else {
            z = i == 1;
        }
        if (z && jobStatus.getStandbyBucket() == 4) {
            jobStatus.maybeLogBucketMismatch();
        }
        return jobStatus.setUidActive(z) | jobStatus.setBackgroundNotRestrictedConstraintSatisfied(j, z4, z3);
    }

    private final class UpdateJobFunctor implements java.util.function.Consumer<com.android.server.job.controllers.JobStatus> {
        int mActiveState;
        final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mChangedJobs;
        int mCheckedCount;
        int mTotalCount;
        long mUpdateTimeElapsed;

        private UpdateJobFunctor() {
            this.mChangedJobs = new android.util.ArraySet<>();
            this.mTotalCount = 0;
            this.mCheckedCount = 0;
            this.mUpdateTimeElapsed = 0L;
        }

        void prepare(int i) {
            this.mActiveState = i;
            this.mUpdateTimeElapsed = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            this.mChangedJobs.clear();
            this.mTotalCount = 0;
            this.mCheckedCount = 0;
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.job.controllers.JobStatus jobStatus) {
            this.mTotalCount++;
            this.mCheckedCount++;
            if (com.android.server.job.controllers.BackgroundJobsController.this.updateSingleJobRestrictionLocked(jobStatus, this.mUpdateTimeElapsed, this.mActiveState)) {
                this.mChangedJobs.add(jobStatus);
            }
        }
    }
}
