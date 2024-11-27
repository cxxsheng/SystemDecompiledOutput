package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public final class BatteryController extends com.android.server.job.controllers.RestrictingController {
    private static final boolean DEBUG;
    private static final java.lang.String TAG = "JobScheduler.Battery";
    private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mChangedJobs;
    private final com.android.server.job.controllers.FlexibilityController mFlexibilityController;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Boolean mLastReportedStatsdBatteryNotLow;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Boolean mLastReportedStatsdStablePower;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mTopStartedJobs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mTrackedTasks;

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    public BatteryController(com.android.server.job.JobSchedulerService jobSchedulerService, com.android.server.job.controllers.FlexibilityController flexibilityController) {
        super(jobSchedulerService);
        this.mTrackedTasks = new android.util.ArraySet<>();
        this.mTopStartedJobs = new android.util.ArraySet<>();
        this.mChangedJobs = new android.util.ArraySet<>();
        this.mLastReportedStatsdBatteryNotLow = null;
        this.mLastReportedStatsdStablePower = null;
        this.mFlexibilityController = flexibilityController;
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.hasPowerConstraint()) {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            this.mTrackedTasks.add(jobStatus);
            jobStatus.setTrackingController(1);
            if (jobStatus.hasChargingConstraint()) {
                if (hasTopExemptionLocked(jobStatus)) {
                    jobStatus.setChargingConstraintSatisfied(millis, this.mService.isPowerConnected());
                } else {
                    jobStatus.setChargingConstraintSatisfied(millis, this.mService.isBatteryCharging() && this.mService.isBatteryNotLow());
                }
            }
            jobStatus.setBatteryNotLowConstraintSatisfied(millis, this.mService.isBatteryNotLow());
        }
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public void startTrackingRestrictedJobLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        maybeStartTrackingJobLocked(jobStatus, null);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void prepareForExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (!jobStatus.hasPowerConstraint()) {
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Prepping for " + jobStatus.toShortString());
        }
        if (this.mService.getUidBias(jobStatus.getSourceUid()) == 40) {
            if (DEBUG) {
                android.util.Slog.d(TAG, jobStatus.toShortString() + " is top started job");
            }
            this.mTopStartedJobs.add(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void unprepareFromExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        this.mTopStartedJobs.remove(jobStatus);
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(1)) {
            this.mTrackedTasks.remove(jobStatus);
            this.mTopStartedJobs.remove(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public void stopTrackingRestrictedJobLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (!jobStatus.hasPowerConstraint()) {
            maybeStopTrackingJobLocked(jobStatus, null);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onBatteryStateChangedLocked() {
        com.android.server.AppSchedulingModuleThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.job.controllers.BatteryController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.job.controllers.BatteryController.this.lambda$onBatteryStateChangedLocked$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBatteryStateChangedLocked$0() {
        synchronized (this.mLock) {
            maybeReportNewChargingStateLocked();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onUidBiasChangedLocked(int i, int i2, int i3) {
        if (i2 == 40 || i3 == 40) {
            maybeReportNewChargingStateLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean hasTopExemptionLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        return this.mService.getUidBias(jobStatus.getSourceUid()) == 40 || this.mTopStartedJobs.contains(jobStatus);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeReportNewChargingStateLocked() {
        boolean isPowerConnected = this.mService.isPowerConnected();
        boolean z = this.mService.isBatteryCharging() && this.mService.isBatteryNotLow();
        boolean isBatteryNotLow = this.mService.isBatteryNotLow();
        if (DEBUG) {
            android.util.Slog.d(TAG, "maybeReportNewChargingStateLocked: " + isPowerConnected + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + z + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + isBatteryNotLow);
        }
        if (this.mLastReportedStatsdStablePower == null || this.mLastReportedStatsdStablePower.booleanValue() != z) {
            logDeviceWideConstraintStateToStatsd(1, z);
            this.mLastReportedStatsdStablePower = java.lang.Boolean.valueOf(z);
        }
        if (this.mLastReportedStatsdBatteryNotLow == null || this.mLastReportedStatsdBatteryNotLow.booleanValue() != isBatteryNotLow) {
            logDeviceWideConstraintStateToStatsd(2, isBatteryNotLow);
            this.mLastReportedStatsdBatteryNotLow = java.lang.Boolean.valueOf(isBatteryNotLow);
        }
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        this.mFlexibilityController.setConstraintSatisfied(1, this.mService.isBatteryCharging(), millis);
        this.mFlexibilityController.setConstraintSatisfied(2, isBatteryNotLow, millis);
        for (int size = this.mTrackedTasks.size() - 1; size >= 0; size--) {
            com.android.server.job.controllers.JobStatus valueAt = this.mTrackedTasks.valueAt(size);
            if (valueAt.hasChargingConstraint()) {
                if (hasTopExemptionLocked(valueAt) && valueAt.getEffectivePriority() >= 300) {
                    if (valueAt.setChargingConstraintSatisfied(millis, isPowerConnected)) {
                        this.mChangedJobs.add(valueAt);
                    }
                } else if (valueAt.setChargingConstraintSatisfied(millis, z)) {
                    this.mChangedJobs.add(valueAt);
                }
            }
            if (valueAt.hasBatteryNotLowConstraint() && valueAt.setBatteryNotLowConstraintSatisfied(millis, isBatteryNotLow)) {
                this.mChangedJobs.add(valueAt);
            }
        }
        if (z || isBatteryNotLow) {
            this.mStateChangedListener.onRunJobNow(null);
        } else if (this.mChangedJobs.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(this.mChangedJobs);
        }
        this.mChangedJobs.clear();
    }

    @com.android.internal.annotations.VisibleForTesting
    android.util.ArraySet<com.android.server.job.controllers.JobStatus> getTrackedJobs() {
        return this.mTrackedTasks;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.util.ArraySet<com.android.server.job.controllers.JobStatus> getTopStartedJobs() {
        return this.mTopStartedJobs;
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Stable power: ");
        sb.append(this.mService.isBatteryCharging() && this.mService.isBatteryNotLow());
        indentingPrintWriter.println(sb.toString());
        indentingPrintWriter.println("Not low: " + this.mService.isBatteryNotLow());
        for (int i = 0; i < this.mTrackedTasks.size(); i++) {
            com.android.server.job.controllers.JobStatus valueAt = this.mTrackedTasks.valueAt(i);
            if (predicate.test(valueAt)) {
                indentingPrintWriter.print("#");
                valueAt.printUniqueId(indentingPrintWriter);
                indentingPrintWriter.print(" from ");
                android.os.UserHandle.formatUid(indentingPrintWriter, valueAt.getSourceUid());
                indentingPrintWriter.println();
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1133871366145L, this.mService.isBatteryCharging() && this.mService.isBatteryNotLow());
        protoOutputStream.write(1133871366146L, this.mService.isBatteryNotLow());
        for (int i = 0; i < this.mTrackedTasks.size(); i++) {
            com.android.server.job.controllers.JobStatus valueAt = this.mTrackedTasks.valueAt(i);
            if (predicate.test(valueAt)) {
                long start3 = protoOutputStream.start(2246267895813L);
                valueAt.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1120986464258L, valueAt.getSourceUid());
                protoOutputStream.end(start3);
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }
}
