package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public final class IdleController extends com.android.server.job.controllers.RestrictingController implements com.android.server.job.controllers.idle.IdlenessListener {
    private static final java.lang.String TAG = "JobScheduler.IdleController";
    private final com.android.server.job.controllers.FlexibilityController mFlexibilityController;
    com.android.server.job.controllers.idle.IdlenessTracker mIdleTracker;
    final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mTrackedTasks;

    public IdleController(com.android.server.job.JobSchedulerService jobSchedulerService, com.android.server.job.controllers.FlexibilityController flexibilityController) {
        super(jobSchedulerService);
        this.mTrackedTasks = new android.util.ArraySet<>();
        initIdleStateTracker();
        this.mFlexibilityController = flexibilityController;
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.hasIdleConstraint()) {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            this.mTrackedTasks.add(jobStatus);
            jobStatus.setTrackingController(8);
            jobStatus.setIdleConstraintSatisfied(millis, this.mIdleTracker.isIdle());
        }
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public void startTrackingRestrictedJobLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        maybeStartTrackingJobLocked(jobStatus, null);
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(8)) {
            this.mTrackedTasks.remove(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public void stopTrackingRestrictedJobLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (!jobStatus.hasIdleConstraint()) {
            maybeStopTrackingJobLocked(jobStatus, null);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void processConstantLocked(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str) {
        this.mIdleTracker.processConstant(properties, str);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onBatteryStateChangedLocked() {
        this.mIdleTracker.onBatteryStateChanged(this.mService.isBatteryCharging(), this.mService.isBatteryNotLow());
    }

    @Override // com.android.server.job.controllers.idle.IdlenessListener
    public void reportNewIdleState(boolean z) {
        synchronized (this.mLock) {
            try {
                logDeviceWideConstraintStateToStatsd(4, z);
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                this.mFlexibilityController.setConstraintSatisfied(4, z, millis);
                for (int size = this.mTrackedTasks.size() - 1; size >= 0; size--) {
                    this.mTrackedTasks.valueAt(size).setIdleConstraintSatisfied(millis, z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mStateChangedListener.onControllerStateChanged(this.mTrackedTasks);
    }

    private void initIdleStateTracker() {
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            this.mIdleTracker = new com.android.server.job.controllers.idle.CarIdlenessTracker();
        } else {
            this.mIdleTracker = new com.android.server.job.controllers.idle.DeviceIdlenessTracker();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void startTrackingLocked() {
        this.mIdleTracker.startTracking(this.mContext, this.mService, this);
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpConstants(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println();
        indentingPrintWriter.println("IdleController:");
        indentingPrintWriter.increaseIndent();
        this.mIdleTracker.dumpConstants(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        indentingPrintWriter.println("Currently idle: " + this.mIdleTracker.isIdle());
        indentingPrintWriter.println("Idleness tracker:");
        this.mIdleTracker.dump(indentingPrintWriter);
        indentingPrintWriter.println();
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
        long start2 = protoOutputStream.start(1146756268038L);
        protoOutputStream.write(1133871366145L, this.mIdleTracker.isIdle());
        this.mIdleTracker.dump(protoOutputStream, 1146756268035L);
        for (int i = 0; i < this.mTrackedTasks.size(); i++) {
            com.android.server.job.controllers.JobStatus valueAt = this.mTrackedTasks.valueAt(i);
            if (predicate.test(valueAt)) {
                long start3 = protoOutputStream.start(2246267895810L);
                valueAt.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1120986464258L, valueAt.getSourceUid());
                protoOutputStream.end(start3);
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }
}
