package android.app.job;

/* loaded from: classes.dex */
public final class Flags {
    private static android.app.job.FeatureFlags FEATURE_FLAGS = new android.app.job.FeatureFlagsImpl();
    public static final java.lang.String FLAG_BACKUP_JOBS_EXEMPTION = "android.app.job.backup_jobs_exemption";
    public static final java.lang.String FLAG_ENFORCE_MINIMUM_TIME_WINDOWS = "android.app.job.enforce_minimum_time_windows";
    public static final java.lang.String FLAG_JOB_DEBUG_INFO_APIS = "android.app.job.job_debug_info_apis";

    public static boolean backupJobsExemption() {
        return FEATURE_FLAGS.backupJobsExemption();
    }

    public static boolean enforceMinimumTimeWindows() {
        return FEATURE_FLAGS.enforceMinimumTimeWindows();
    }

    public static boolean jobDebugInfoApis() {
        return FEATURE_FLAGS.jobDebugInfoApis();
    }
}
