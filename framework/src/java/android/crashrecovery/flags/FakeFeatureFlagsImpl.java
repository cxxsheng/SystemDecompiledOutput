package android.crashrecovery.flags;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.crashrecovery.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.crashrecovery.flags.Flags.FLAG_ENABLE_CRASHRECOVERY, false), java.util.Map.entry(android.crashrecovery.flags.Flags.FLAG_RECOVERABILITY_DETECTION, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.crashrecovery.flags.Flags.FLAG_ENABLE_CRASHRECOVERY, android.crashrecovery.flags.Flags.FLAG_RECOVERABILITY_DETECTION, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.crashrecovery.flags.FeatureFlags
    public boolean enableCrashrecovery() {
        return getValue(android.crashrecovery.flags.Flags.FLAG_ENABLE_CRASHRECOVERY);
    }

    @Override // android.crashrecovery.flags.FeatureFlags
    public boolean recoverabilityDetection() {
        return getValue(android.crashrecovery.flags.Flags.FLAG_RECOVERABILITY_DETECTION);
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
