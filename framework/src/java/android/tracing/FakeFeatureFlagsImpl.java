package android.tracing;

/* loaded from: classes3.dex */
public class FakeFeatureFlagsImpl implements android.tracing.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.tracing.Flags.FLAG_PERFETTO_PROTOLOG_TRACING, false), java.util.Map.entry(android.tracing.Flags.FLAG_PERFETTO_TRANSITION_TRACING, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.tracing.Flags.FLAG_PERFETTO_PROTOLOG_TRACING, android.tracing.Flags.FLAG_PERFETTO_TRANSITION_TRACING, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoProtologTracing() {
        return getValue(android.tracing.Flags.FLAG_PERFETTO_PROTOLOG_TRACING);
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoTransitionTracing() {
        return getValue(android.tracing.Flags.FLAG_PERFETTO_TRANSITION_TRACING);
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
