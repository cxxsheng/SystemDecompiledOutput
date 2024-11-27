package android.net.vcn;

/* loaded from: classes2.dex */
public class FakeFeatureFlagsImpl implements android.net.vcn.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.net.vcn.Flags.FLAG_NETWORK_METRIC_MONITOR, false), java.util.Map.entry(android.net.vcn.Flags.FLAG_SAFE_MODE_CONFIG, false), java.util.Map.entry(android.net.vcn.Flags.FLAG_SAFE_MODE_TIMEOUT_CONFIG, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.net.vcn.Flags.FLAG_NETWORK_METRIC_MONITOR, android.net.vcn.Flags.FLAG_SAFE_MODE_CONFIG, android.net.vcn.Flags.FLAG_SAFE_MODE_TIMEOUT_CONFIG, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean networkMetricMonitor() {
        return getValue(android.net.vcn.Flags.FLAG_NETWORK_METRIC_MONITOR);
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean safeModeConfig() {
        return getValue(android.net.vcn.Flags.FLAG_SAFE_MODE_CONFIG);
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean safeModeTimeoutConfig() {
        return getValue(android.net.vcn.Flags.FLAG_SAFE_MODE_TIMEOUT_CONFIG);
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
