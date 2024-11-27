package com.android.server.power.feature.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static com.android.server.power.feature.flags.FeatureFlags FEATURE_FLAGS = new com.android.server.power.feature.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ENABLE_EARLY_SCREEN_TIMEOUT_DETECTOR = "com.android.server.power.feature.flags.enable_early_screen_timeout_detector";

    public static boolean enableEarlyScreenTimeoutDetector() {
        return FEATURE_FLAGS.enableEarlyScreenTimeoutDetector();
    }
}
