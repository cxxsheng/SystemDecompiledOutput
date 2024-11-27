package com.android.internal.foldables.flags;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements com.android.internal.foldables.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.internal.foldables.flags.Flags.FLAG_FOLD_GRACE_PERIOD_ENABLED, false), java.util.Map.entry(com.android.internal.foldables.flags.Flags.FLAG_FOLD_LOCK_SETTING_ENABLED, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.internal.foldables.flags.Flags.FLAG_FOLD_GRACE_PERIOD_ENABLED, com.android.internal.foldables.flags.Flags.FLAG_FOLD_LOCK_SETTING_ENABLED, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.internal.foldables.flags.FeatureFlags
    public boolean foldGracePeriodEnabled() {
        return getValue(com.android.internal.foldables.flags.Flags.FLAG_FOLD_GRACE_PERIOD_ENABLED);
    }

    @Override // com.android.internal.foldables.flags.FeatureFlags
    public boolean foldLockSettingEnabled() {
        return getValue(com.android.internal.foldables.flags.Flags.FLAG_FOLD_LOCK_SETTING_ENABLED);
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
