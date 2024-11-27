package com.android.server.net;

/* loaded from: classes2.dex */
public final class Flags {
    private static com.android.server.net.FeatureFlags FEATURE_FLAGS = new com.android.server.net.FeatureFlagsImpl();
    public static final java.lang.String FLAG_NETWORK_BLOCKED_FOR_TOP_SLEEPING_AND_ABOVE = "com.android.server.net.network_blocked_for_top_sleeping_and_above";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean networkBlockedForTopSleepingAndAbove() {
        FEATURE_FLAGS.networkBlockedForTopSleepingAndAbove();
        return false;
    }
}
