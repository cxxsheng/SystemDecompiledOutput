package android.widget.flags;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements android.widget.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.widget.flags.Flags.FLAG_CALL_STYLE_SET_DATA_ASYNC, false), java.util.Map.entry(android.widget.flags.Flags.FLAG_CONVERSATION_STYLE_SET_AVATAR_ASYNC, false), java.util.Map.entry(android.widget.flags.Flags.FLAG_ENABLE_PLATFORM_WIDGET_DIFFERENTIAL_MOTION_FLING, false), java.util.Map.entry(android.widget.flags.Flags.FLAG_NOTIF_LINEARLAYOUT_OPTIMIZED, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.widget.flags.Flags.FLAG_CALL_STYLE_SET_DATA_ASYNC, android.widget.flags.Flags.FLAG_CONVERSATION_STYLE_SET_AVATAR_ASYNC, android.widget.flags.Flags.FLAG_ENABLE_PLATFORM_WIDGET_DIFFERENTIAL_MOTION_FLING, android.widget.flags.Flags.FLAG_NOTIF_LINEARLAYOUT_OPTIMIZED, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean callStyleSetDataAsync() {
        return getValue(android.widget.flags.Flags.FLAG_CALL_STYLE_SET_DATA_ASYNC);
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean conversationStyleSetAvatarAsync() {
        return getValue(android.widget.flags.Flags.FLAG_CONVERSATION_STYLE_SET_AVATAR_ASYNC);
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean enablePlatformWidgetDifferentialMotionFling() {
        return getValue(android.widget.flags.Flags.FLAG_ENABLE_PLATFORM_WIDGET_DIFFERENTIAL_MOTION_FLING);
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean notifLinearlayoutOptimized() {
        return getValue(android.widget.flags.Flags.FLAG_NOTIF_LINEARLAYOUT_OPTIMIZED);
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
