package com.android.net.thread.platform.flags;

/* loaded from: classes5.dex */
public class FakeFeatureFlagsImpl implements com.android.net.thread.platform.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.net.thread.platform.flags.Flags.FLAG_THREAD_ENABLED_PLATFORM, false), java.util.Map.entry(com.android.net.thread.platform.flags.Flags.FLAG_THREAD_USER_RESTRICTION_ENABLED, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.net.thread.platform.flags.Flags.FLAG_THREAD_ENABLED_PLATFORM, com.android.net.thread.platform.flags.Flags.FLAG_THREAD_USER_RESTRICTION_ENABLED, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.net.thread.platform.flags.FeatureFlags
    public boolean threadEnabledPlatform() {
        return getValue(com.android.net.thread.platform.flags.Flags.FLAG_THREAD_ENABLED_PLATFORM);
    }

    @Override // com.android.net.thread.platform.flags.FeatureFlags
    public boolean threadUserRestrictionEnabled() {
        return getValue(com.android.net.thread.platform.flags.Flags.FLAG_THREAD_USER_RESTRICTION_ENABLED);
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