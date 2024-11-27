package com.android.wm.shell;

/* loaded from: classes3.dex */
public final class Flags {
    private static com.android.wm.shell.FeatureFlags FEATURE_FLAGS = new com.android.wm.shell.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ENABLE_APP_PAIRS = "com.android.wm.shell.enable_app_pairs";
    public static final java.lang.String FLAG_ENABLE_BUBBLES_LONG_PRESS_NAV_HANDLE = "com.android.wm.shell.enable_bubbles_long_press_nav_handle";
    public static final java.lang.String FLAG_ENABLE_BUBBLE_BAR = "com.android.wm.shell.enable_bubble_bar";
    public static final java.lang.String FLAG_ENABLE_LEFT_RIGHT_SPLIT_IN_PORTRAIT = "com.android.wm.shell.enable_left_right_split_in_portrait";
    public static final java.lang.String FLAG_ENABLE_NEW_BUBBLE_ANIMATIONS = "com.android.wm.shell.enable_new_bubble_animations";
    public static final java.lang.String FLAG_ENABLE_PIP2_IMPLEMENTATION = "com.android.wm.shell.enable_pip2_implementation";
    public static final java.lang.String FLAG_ENABLE_PIP_UMO_EXPERIENCE = "com.android.wm.shell.enable_pip_umo_experience";
    public static final java.lang.String FLAG_ENABLE_SPLIT_CONTEXTUAL = "com.android.wm.shell.enable_split_contextual";
    public static final java.lang.String FLAG_ENABLE_TASKBAR_NAVBAR_UNIFICATION = "com.android.wm.shell.enable_taskbar_navbar_unification";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean enableAppPairs() {
        FEATURE_FLAGS.enableAppPairs();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean enableBubbleBar() {
        FEATURE_FLAGS.enableBubbleBar();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean enableBubblesLongPressNavHandle() {
        FEATURE_FLAGS.enableBubblesLongPressNavHandle();
        return false;
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean enableLeftRightSplitInPortrait() {
        FEATURE_FLAGS.enableLeftRightSplitInPortrait();
        return true;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean enableNewBubbleAnimations() {
        FEATURE_FLAGS.enableNewBubbleAnimations();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean enablePip2Implementation() {
        FEATURE_FLAGS.enablePip2Implementation();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean enablePipUmoExperience() {
        FEATURE_FLAGS.enablePipUmoExperience();
        return false;
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean enableSplitContextual() {
        FEATURE_FLAGS.enableSplitContextual();
        return true;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean enableTaskbarNavbarUnification() {
        FEATURE_FLAGS.enableTaskbarNavbarUnification();
        return false;
    }
}
