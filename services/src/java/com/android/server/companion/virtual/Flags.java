package com.android.server.companion.virtual;

/* loaded from: classes.dex */
public final class Flags {
    private static com.android.server.companion.virtual.FeatureFlags FEATURE_FLAGS = new com.android.server.companion.virtual.FeatureFlagsImpl();
    public static final java.lang.String FLAG_DUMP_HISTORY = "com.android.server.companion.virtual.dump_history";

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean dumpHistory() {
        FEATURE_FLAGS.dumpHistory();
        return true;
    }
}
