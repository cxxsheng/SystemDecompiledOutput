package android.app.job;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements android.app.job.FeatureFlags {
    @Override // android.app.job.FeatureFlags
    public boolean backupJobsExemption() {
        return false;
    }

    @Override // android.app.job.FeatureFlags
    public boolean enforceMinimumTimeWindows() {
        return false;
    }

    @Override // android.app.job.FeatureFlags
    public boolean jobDebugInfoApis() {
        return false;
    }
}
