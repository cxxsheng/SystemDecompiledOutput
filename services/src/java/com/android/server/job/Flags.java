package com.android.server.job;

/* loaded from: classes2.dex */
public final class Flags {
    private static com.android.server.job.FeatureFlags FEATURE_FLAGS = new com.android.server.job.FeatureFlagsImpl();
    public static final java.lang.String FLAG_BATCH_ACTIVE_BUCKET_JOBS = "com.android.server.job.batch_active_bucket_jobs";
    public static final java.lang.String FLAG_BATCH_CONNECTIVITY_JOBS_PER_NETWORK = "com.android.server.job.batch_connectivity_jobs_per_network";
    public static final java.lang.String FLAG_DO_NOT_FORCE_RUSH_EXECUTION_AT_BOOT = "com.android.server.job.do_not_force_rush_execution_at_boot";
    public static final java.lang.String FLAG_RELAX_PREFETCH_CONNECTIVITY_CONSTRAINT_ONLY_ON_CHARGER = "com.android.server.job.relax_prefetch_connectivity_constraint_only_on_charger";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean batchActiveBucketJobs() {
        FEATURE_FLAGS.batchActiveBucketJobs();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean batchConnectivityJobsPerNetwork() {
        FEATURE_FLAGS.batchConnectivityJobsPerNetwork();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean doNotForceRushExecutionAtBoot() {
        FEATURE_FLAGS.doNotForceRushExecutionAtBoot();
        return false;
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean relaxPrefetchConnectivityConstraintOnlyOnCharger() {
        FEATURE_FLAGS.relaxPrefetchConnectivityConstraintOnlyOnCharger();
        return true;
    }
}
