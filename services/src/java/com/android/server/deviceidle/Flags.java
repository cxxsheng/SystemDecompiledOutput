package com.android.server.deviceidle;

/* loaded from: classes.dex */
public final class Flags {
    private static com.android.server.deviceidle.FeatureFlags FEATURE_FLAGS = new com.android.server.deviceidle.FeatureFlagsImpl();
    public static final java.lang.String FLAG_DISABLE_WAKELOCKS_IN_LIGHT_IDLE = "com.android.server.deviceidle.disable_wakelocks_in_light_idle";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean disableWakelocksInLightIdle() {
        FEATURE_FLAGS.disableWakelocksInLightIdle();
        return false;
    }
}
