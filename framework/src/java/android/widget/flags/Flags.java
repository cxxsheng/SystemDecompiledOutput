package android.widget.flags;

/* loaded from: classes4.dex */
public final class Flags {
    private static android.widget.flags.FeatureFlags FEATURE_FLAGS = new android.widget.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_CALL_STYLE_SET_DATA_ASYNC = "android.widget.flags.call_style_set_data_async";
    public static final java.lang.String FLAG_CONVERSATION_STYLE_SET_AVATAR_ASYNC = "android.widget.flags.conversation_style_set_avatar_async";
    public static final java.lang.String FLAG_ENABLE_PLATFORM_WIDGET_DIFFERENTIAL_MOTION_FLING = "android.widget.flags.enable_platform_widget_differential_motion_fling";
    public static final java.lang.String FLAG_NOTIF_LINEARLAYOUT_OPTIMIZED = "android.widget.flags.notif_linearlayout_optimized";

    public static boolean callStyleSetDataAsync() {
        return FEATURE_FLAGS.callStyleSetDataAsync();
    }

    public static boolean conversationStyleSetAvatarAsync() {
        return FEATURE_FLAGS.conversationStyleSetAvatarAsync();
    }

    public static boolean enablePlatformWidgetDifferentialMotionFling() {
        return FEATURE_FLAGS.enablePlatformWidgetDifferentialMotionFling();
    }

    public static boolean notifLinearlayoutOptimized() {
        return FEATURE_FLAGS.notifLinearlayoutOptimized();
    }
}
