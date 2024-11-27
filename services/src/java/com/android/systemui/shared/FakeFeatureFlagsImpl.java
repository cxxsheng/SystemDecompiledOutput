package com.android.systemui.shared;

/* loaded from: classes3.dex */
public class FakeFeatureFlagsImpl implements com.android.systemui.shared.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.systemui.shared.Flags.FLAG_ENABLE_HOME_DELAY, false), java.util.Map.entry(com.android.systemui.shared.Flags.FLAG_EXAMPLE_SHARED_FLAG, false), java.util.Map.entry(com.android.systemui.shared.Flags.FLAG_RETURN_ANIMATION_FRAMEWORK_LIBRARY, false), java.util.Map.entry(com.android.systemui.shared.Flags.FLAG_SHADE_ALLOW_BACK_GESTURE, false), java.util.Map.entry(com.android.systemui.shared.Flags.FLAG_SIDEFPS_CONTROLLER_REFACTOR, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.systemui.shared.Flags.FLAG_ENABLE_HOME_DELAY, com.android.systemui.shared.Flags.FLAG_EXAMPLE_SHARED_FLAG, com.android.systemui.shared.Flags.FLAG_RETURN_ANIMATION_FRAMEWORK_LIBRARY, com.android.systemui.shared.Flags.FLAG_SHADE_ALLOW_BACK_GESTURE, com.android.systemui.shared.Flags.FLAG_SIDEFPS_CONTROLLER_REFACTOR, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.systemui.shared.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableHomeDelay() {
        return getValue(com.android.systemui.shared.Flags.FLAG_ENABLE_HOME_DELAY);
    }

    @Override // com.android.systemui.shared.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean exampleSharedFlag() {
        return getValue(com.android.systemui.shared.Flags.FLAG_EXAMPLE_SHARED_FLAG);
    }

    @Override // com.android.systemui.shared.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean returnAnimationFrameworkLibrary() {
        return getValue(com.android.systemui.shared.Flags.FLAG_RETURN_ANIMATION_FRAMEWORK_LIBRARY);
    }

    @Override // com.android.systemui.shared.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean shadeAllowBackGesture() {
        return getValue(com.android.systemui.shared.Flags.FLAG_SHADE_ALLOW_BACK_GESTURE);
    }

    @Override // com.android.systemui.shared.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean sidefpsControllerRefactor() {
        return getValue(com.android.systemui.shared.Flags.FLAG_SIDEFPS_CONTROLLER_REFACTOR);
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
        if (this.mReadOnlyFlagsSet.contains(str)) {
            isOptimizationEnabled();
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

    @com.android.aconfig.annotations.AssumeTrueForR8
    private boolean isOptimizationEnabled() {
        return false;
    }
}
