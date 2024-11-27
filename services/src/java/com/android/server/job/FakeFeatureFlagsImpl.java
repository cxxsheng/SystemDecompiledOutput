package com.android.server.job;

/* loaded from: classes2.dex */
public class FakeFeatureFlagsImpl implements com.android.server.job.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.server.job.Flags.FLAG_BATCH_ACTIVE_BUCKET_JOBS, false), java.util.Map.entry(com.android.server.job.Flags.FLAG_BATCH_CONNECTIVITY_JOBS_PER_NETWORK, false), java.util.Map.entry(com.android.server.job.Flags.FLAG_DO_NOT_FORCE_RUSH_EXECUTION_AT_BOOT, false), java.util.Map.entry(com.android.server.job.Flags.FLAG_RELAX_PREFETCH_CONNECTIVITY_CONSTRAINT_ONLY_ON_CHARGER, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.server.job.Flags.FLAG_BATCH_ACTIVE_BUCKET_JOBS, com.android.server.job.Flags.FLAG_BATCH_CONNECTIVITY_JOBS_PER_NETWORK, com.android.server.job.Flags.FLAG_DO_NOT_FORCE_RUSH_EXECUTION_AT_BOOT, com.android.server.job.Flags.FLAG_RELAX_PREFETCH_CONNECTIVITY_CONSTRAINT_ONLY_ON_CHARGER, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.server.job.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean batchActiveBucketJobs() {
        return getValue(com.android.server.job.Flags.FLAG_BATCH_ACTIVE_BUCKET_JOBS);
    }

    @Override // com.android.server.job.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean batchConnectivityJobsPerNetwork() {
        return getValue(com.android.server.job.Flags.FLAG_BATCH_CONNECTIVITY_JOBS_PER_NETWORK);
    }

    @Override // com.android.server.job.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean doNotForceRushExecutionAtBoot() {
        return getValue(com.android.server.job.Flags.FLAG_DO_NOT_FORCE_RUSH_EXECUTION_AT_BOOT);
    }

    @Override // com.android.server.job.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean relaxPrefetchConnectivityConstraintOnlyOnCharger() {
        return getValue(com.android.server.job.Flags.FLAG_RELAX_PREFETCH_CONNECTIVITY_CONSTRAINT_ONLY_ON_CHARGER);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str)) {
            isOptimizationEnabled();
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    private boolean isOptimizationEnabled() {
        return false;
    }
}
