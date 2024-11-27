package com.android.net.thread.platform.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static com.android.net.thread.platform.flags.FeatureFlags FEATURE_FLAGS = new com.android.net.thread.platform.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_THREAD_ENABLED_PLATFORM = "com.android.net.thread.platform.flags.thread_enabled_platform";
    public static final java.lang.String FLAG_THREAD_USER_RESTRICTION_ENABLED = "com.android.net.thread.platform.flags.thread_user_restriction_enabled";

    public static boolean threadEnabledPlatform() {
        return FEATURE_FLAGS.threadEnabledPlatform();
    }

    public static boolean threadUserRestrictionEnabled() {
        return FEATURE_FLAGS.threadUserRestrictionEnabled();
    }
}
