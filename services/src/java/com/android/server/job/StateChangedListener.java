package com.android.server.job;

/* loaded from: classes2.dex */
public interface StateChangedListener {
    void onControllerStateChanged(@android.annotation.Nullable android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet);

    void onDeviceIdleStateChanged(boolean z);

    void onNetworkChanged(com.android.server.job.controllers.JobStatus jobStatus, android.net.Network network);

    void onRestrictedBucketChanged(@android.annotation.NonNull java.util.List<com.android.server.job.controllers.JobStatus> list);

    void onRestrictionStateChanged(@android.annotation.NonNull com.android.server.job.restrictions.JobRestriction jobRestriction, boolean z);

    void onRunJobNow(com.android.server.job.controllers.JobStatus jobStatus);
}
