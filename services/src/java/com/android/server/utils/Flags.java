package com.android.server.utils;

/* loaded from: classes2.dex */
public final class Flags {
    private static com.android.server.utils.FeatureFlags FEATURE_FLAGS = new com.android.server.utils.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ANR_TIMER_SERVICE = "com.android.server.utils.anr_timer_service";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean anrTimerService() {
        FEATURE_FLAGS.anrTimerService();
        return false;
    }
}
