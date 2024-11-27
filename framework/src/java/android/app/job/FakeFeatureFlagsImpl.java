package android.app.job;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.app.job.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.app.job.Flags.FLAG_BACKUP_JOBS_EXEMPTION, false), java.util.Map.entry(android.app.job.Flags.FLAG_ENFORCE_MINIMUM_TIME_WINDOWS, false), java.util.Map.entry(android.app.job.Flags.FLAG_JOB_DEBUG_INFO_APIS, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.app.job.Flags.FLAG_BACKUP_JOBS_EXEMPTION, android.app.job.Flags.FLAG_ENFORCE_MINIMUM_TIME_WINDOWS, android.app.job.Flags.FLAG_JOB_DEBUG_INFO_APIS, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.app.job.FeatureFlags
    public boolean backupJobsExemption() {
        return getValue(android.app.job.Flags.FLAG_BACKUP_JOBS_EXEMPTION);
    }

    @Override // android.app.job.FeatureFlags
    public boolean enforceMinimumTimeWindows() {
        return getValue(android.app.job.Flags.FLAG_ENFORCE_MINIMUM_TIME_WINDOWS);
    }

    @Override // android.app.job.FeatureFlags
    public boolean jobDebugInfoApis() {
        return getValue(android.app.job.Flags.FLAG_JOB_DEBUG_INFO_APIS);
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
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
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

    private boolean isOptimizationEnabled() {
        return false;
    }
}
