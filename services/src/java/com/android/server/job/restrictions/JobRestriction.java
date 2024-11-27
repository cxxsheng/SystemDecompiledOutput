package com.android.server.job.restrictions;

/* loaded from: classes2.dex */
public abstract class JobRestriction {
    private final int mInternalReason;
    private final int mPendingReason;
    final com.android.server.job.JobSchedulerService mService;
    private final int mStopReason;

    public abstract void dumpConstants(android.util.IndentingPrintWriter indentingPrintWriter);

    public abstract boolean isJobRestricted(com.android.server.job.controllers.JobStatus jobStatus);

    protected JobRestriction(com.android.server.job.JobSchedulerService jobSchedulerService, int i, int i2, int i3) {
        this.mService = jobSchedulerService;
        this.mPendingReason = i2;
        this.mStopReason = i;
        this.mInternalReason = i3;
    }

    public void onSystemServicesReady() {
    }

    public void dumpConstants(android.util.proto.ProtoOutputStream protoOutputStream) {
    }

    public final int getPendingReason() {
        return this.mPendingReason;
    }

    public final int getStopReason() {
        return this.mStopReason;
    }

    public final int getInternalReason() {
        return this.mInternalReason;
    }
}
