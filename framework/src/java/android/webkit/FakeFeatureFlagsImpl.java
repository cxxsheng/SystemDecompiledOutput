package android.webkit;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements android.webkit.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.webkit.Flags.FLAG_UPDATE_SERVICE_IPC_WRAPPER, false), java.util.Map.entry(android.webkit.Flags.FLAG_UPDATE_SERVICE_V2, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.webkit.Flags.FLAG_UPDATE_SERVICE_IPC_WRAPPER, android.webkit.Flags.FLAG_UPDATE_SERVICE_V2, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.webkit.FeatureFlags
    public boolean updateServiceIpcWrapper() {
        return getValue(android.webkit.Flags.FLAG_UPDATE_SERVICE_IPC_WRAPPER);
    }

    @Override // android.webkit.FeatureFlags
    public boolean updateServiceV2() {
        return getValue(android.webkit.Flags.FLAG_UPDATE_SERVICE_V2);
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
