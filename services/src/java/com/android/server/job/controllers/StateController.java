package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public abstract class StateController {
    private static final java.lang.String TAG = "JobScheduler.SC";
    protected final com.android.server.job.JobSchedulerService.Constants mConstants;
    protected final android.content.Context mContext;
    protected final java.lang.Object mLock;
    protected final com.android.server.job.JobSchedulerService mService;
    protected final com.android.server.job.StateChangedListener mStateChangedListener;

    public abstract void dumpControllerStateLocked(android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate);

    public abstract void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2);

    public abstract void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2);

    StateController(com.android.server.job.JobSchedulerService jobSchedulerService) {
        this.mService = jobSchedulerService;
        this.mStateChangedListener = jobSchedulerService;
        this.mContext = jobSchedulerService.getTestableContext();
        this.mLock = jobSchedulerService.getLock();
        this.mConstants = jobSchedulerService.getConstants();
    }

    public void startTrackingLocked() {
    }

    public void onSystemServicesReady() {
    }

    public void prepareForExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
    }

    public void unprepareFromExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
    }

    public void rescheduleForFailureLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
    }

    public void prepareForUpdatedConstantsLocked() {
    }

    public void processConstantLocked(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str) {
    }

    public void onConstantsUpdatedLocked() {
    }

    public void onAppRemovedLocked(java.lang.String str, int i) {
    }

    public void onUserAddedLocked(int i) {
    }

    public void onUserRemovedLocked(int i) {
    }

    public void evaluateStateLocked(com.android.server.job.controllers.JobStatus jobStatus) {
    }

    public void reevaluateStateLocked(int i) {
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onBatteryStateChangedLocked() {
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onUidBiasChangedLocked(int i, int i2, int i3) {
    }

    protected boolean wouldBeReadyWithConstraintLocked(com.android.server.job.controllers.JobStatus jobStatus, int i) {
        boolean wouldBeReadyWithConstraint = jobStatus.wouldBeReadyWithConstraint(i);
        if (com.android.server.job.JobSchedulerService.DEBUG) {
            android.util.Slog.v(TAG, "wouldBeReadyWithConstraintLocked: " + jobStatus.toShortString() + " constraint=" + i + " readyWithConstraint=" + wouldBeReadyWithConstraint);
        }
        if (!wouldBeReadyWithConstraint) {
            return false;
        }
        return this.mService.areComponentsInPlaceLocked(jobStatus);
    }

    protected void logDeviceWideConstraintStateToStatsd(int i, boolean z) {
        int i2;
        int protoConstraint = com.android.server.job.controllers.JobStatus.getProtoConstraint(i);
        if (z) {
            i2 = 2;
        } else {
            i2 = 1;
        }
        com.android.internal.util.FrameworkStatsLog.write(514, protoConstraint, i2);
    }

    public void dumpControllerStateLocked(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
    }

    public void dumpConstants(android.util.IndentingPrintWriter indentingPrintWriter) {
    }

    public void dumpConstants(android.util.proto.ProtoOutputStream protoOutputStream) {
    }

    static java.lang.String packageToString(int i, java.lang.String str) {
        return "<" + i + ">" + str;
    }
}
