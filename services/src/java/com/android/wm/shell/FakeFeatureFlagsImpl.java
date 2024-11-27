package com.android.wm.shell;

/* loaded from: classes3.dex */
public class FakeFeatureFlagsImpl implements com.android.wm.shell.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.wm.shell.Flags.FLAG_ENABLE_APP_PAIRS, false), java.util.Map.entry(com.android.wm.shell.Flags.FLAG_ENABLE_BUBBLE_BAR, false), java.util.Map.entry(com.android.wm.shell.Flags.FLAG_ENABLE_BUBBLES_LONG_PRESS_NAV_HANDLE, false), java.util.Map.entry(com.android.wm.shell.Flags.FLAG_ENABLE_LEFT_RIGHT_SPLIT_IN_PORTRAIT, false), java.util.Map.entry(com.android.wm.shell.Flags.FLAG_ENABLE_NEW_BUBBLE_ANIMATIONS, false), java.util.Map.entry(com.android.wm.shell.Flags.FLAG_ENABLE_PIP2_IMPLEMENTATION, false), java.util.Map.entry(com.android.wm.shell.Flags.FLAG_ENABLE_PIP_UMO_EXPERIENCE, false), java.util.Map.entry(com.android.wm.shell.Flags.FLAG_ENABLE_SPLIT_CONTEXTUAL, false), java.util.Map.entry(com.android.wm.shell.Flags.FLAG_ENABLE_TASKBAR_NAVBAR_UNIFICATION, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.wm.shell.Flags.FLAG_ENABLE_APP_PAIRS, com.android.wm.shell.Flags.FLAG_ENABLE_BUBBLE_BAR, com.android.wm.shell.Flags.FLAG_ENABLE_BUBBLES_LONG_PRESS_NAV_HANDLE, com.android.wm.shell.Flags.FLAG_ENABLE_LEFT_RIGHT_SPLIT_IN_PORTRAIT, com.android.wm.shell.Flags.FLAG_ENABLE_NEW_BUBBLE_ANIMATIONS, com.android.wm.shell.Flags.FLAG_ENABLE_PIP2_IMPLEMENTATION, com.android.wm.shell.Flags.FLAG_ENABLE_PIP_UMO_EXPERIENCE, com.android.wm.shell.Flags.FLAG_ENABLE_SPLIT_CONTEXTUAL, com.android.wm.shell.Flags.FLAG_ENABLE_TASKBAR_NAVBAR_UNIFICATION, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.wm.shell.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableAppPairs() {
        return getValue(com.android.wm.shell.Flags.FLAG_ENABLE_APP_PAIRS);
    }

    @Override // com.android.wm.shell.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableBubbleBar() {
        return getValue(com.android.wm.shell.Flags.FLAG_ENABLE_BUBBLE_BAR);
    }

    @Override // com.android.wm.shell.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableBubblesLongPressNavHandle() {
        return getValue(com.android.wm.shell.Flags.FLAG_ENABLE_BUBBLES_LONG_PRESS_NAV_HANDLE);
    }

    @Override // com.android.wm.shell.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableLeftRightSplitInPortrait() {
        return getValue(com.android.wm.shell.Flags.FLAG_ENABLE_LEFT_RIGHT_SPLIT_IN_PORTRAIT);
    }

    @Override // com.android.wm.shell.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableNewBubbleAnimations() {
        return getValue(com.android.wm.shell.Flags.FLAG_ENABLE_NEW_BUBBLE_ANIMATIONS);
    }

    @Override // com.android.wm.shell.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enablePip2Implementation() {
        return getValue(com.android.wm.shell.Flags.FLAG_ENABLE_PIP2_IMPLEMENTATION);
    }

    @Override // com.android.wm.shell.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enablePipUmoExperience() {
        return getValue(com.android.wm.shell.Flags.FLAG_ENABLE_PIP_UMO_EXPERIENCE);
    }

    @Override // com.android.wm.shell.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableSplitContextual() {
        return getValue(com.android.wm.shell.Flags.FLAG_ENABLE_SPLIT_CONTEXTUAL);
    }

    @Override // com.android.wm.shell.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableTaskbarNavbarUnification() {
        return getValue(com.android.wm.shell.Flags.FLAG_ENABLE_TASKBAR_NAVBAR_UNIFICATION);
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
