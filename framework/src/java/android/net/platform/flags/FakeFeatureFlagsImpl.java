package android.net.platform.flags;

/* loaded from: classes2.dex */
public class FakeFeatureFlagsImpl implements android.net.platform.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.net.platform.flags.Flags.FLAG_IPSEC_TRANSFORM_STATE, false), java.util.Map.entry(android.net.platform.flags.Flags.FLAG_POWERED_OFF_FINDING_PLATFORM, false), java.util.Map.entry(android.net.platform.flags.Flags.FLAG_REGISTER_NSD_OFFLOAD_ENGINE, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.net.platform.flags.Flags.FLAG_IPSEC_TRANSFORM_STATE, android.net.platform.flags.Flags.FLAG_POWERED_OFF_FINDING_PLATFORM, android.net.platform.flags.Flags.FLAG_REGISTER_NSD_OFFLOAD_ENGINE, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.net.platform.flags.FeatureFlags
    public boolean ipsecTransformState() {
        return getValue(android.net.platform.flags.Flags.FLAG_IPSEC_TRANSFORM_STATE);
    }

    @Override // android.net.platform.flags.FeatureFlags
    public boolean poweredOffFindingPlatform() {
        return getValue(android.net.platform.flags.Flags.FLAG_POWERED_OFF_FINDING_PLATFORM);
    }

    @Override // android.net.platform.flags.FeatureFlags
    public boolean registerNsdOffloadEngine() {
        return getValue(android.net.platform.flags.Flags.FLAG_REGISTER_NSD_OFFLOAD_ENGINE);
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
