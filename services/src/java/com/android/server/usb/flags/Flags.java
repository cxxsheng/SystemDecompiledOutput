package com.android.server.usb.flags;

/* loaded from: classes2.dex */
public final class Flags {
    private static com.android.server.usb.flags.FeatureFlags FEATURE_FLAGS = new com.android.server.usb.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ALLOW_RESTRICTION_OF_OVERLAY_ACTIVITIES = "com.android.server.usb.flags.allow_restriction_of_overlay_activities";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean allowRestrictionOfOverlayActivities() {
        FEATURE_FLAGS.allowRestrictionOfOverlayActivities();
        return false;
    }
}
