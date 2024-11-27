package android.widget.flags;

/* loaded from: classes4.dex */
public final class FeatureFlagsImpl implements android.widget.flags.FeatureFlags {
    @Override // android.widget.flags.FeatureFlags
    public boolean callStyleSetDataAsync() {
        return false;
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean conversationStyleSetAvatarAsync() {
        return false;
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean enablePlatformWidgetDifferentialMotionFling() {
        return true;
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean notifLinearlayoutOptimized() {
        return false;
    }
}
