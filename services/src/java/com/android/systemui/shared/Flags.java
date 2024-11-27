package com.android.systemui.shared;

/* loaded from: classes3.dex */
public final class Flags {
    private static com.android.systemui.shared.FeatureFlags FEATURE_FLAGS = new com.android.systemui.shared.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ENABLE_HOME_DELAY = "com.android.systemui.shared.enable_home_delay";
    public static final java.lang.String FLAG_EXAMPLE_SHARED_FLAG = "com.android.systemui.shared.example_shared_flag";
    public static final java.lang.String FLAG_RETURN_ANIMATION_FRAMEWORK_LIBRARY = "com.android.systemui.shared.return_animation_framework_library";
    public static final java.lang.String FLAG_SHADE_ALLOW_BACK_GESTURE = "com.android.systemui.shared.shade_allow_back_gesture";
    public static final java.lang.String FLAG_SIDEFPS_CONTROLLER_REFACTOR = "com.android.systemui.shared.sidefps_controller_refactor";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean enableHomeDelay() {
        FEATURE_FLAGS.enableHomeDelay();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean exampleSharedFlag() {
        FEATURE_FLAGS.exampleSharedFlag();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean returnAnimationFrameworkLibrary() {
        FEATURE_FLAGS.returnAnimationFrameworkLibrary();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean shadeAllowBackGesture() {
        FEATURE_FLAGS.shadeAllowBackGesture();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean sidefpsControllerRefactor() {
        FEATURE_FLAGS.sidefpsControllerRefactor();
        return false;
    }
}
