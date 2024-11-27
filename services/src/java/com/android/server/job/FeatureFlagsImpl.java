package com.android.server.job;

/* loaded from: classes2.dex */
public final class FeatureFlagsImpl implements com.android.server.job.FeatureFlags {
    @Override // com.android.server.job.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean batchActiveBucketJobs() {
        return false;
    }

    @Override // com.android.server.job.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean batchConnectivityJobsPerNetwork() {
        return false;
    }

    @Override // com.android.server.job.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean doNotForceRushExecutionAtBoot() {
        return false;
    }

    @Override // com.android.server.job.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean relaxPrefetchConnectivityConstraintOnlyOnCharger() {
        return true;
    }
}
