package com.android.server.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static com.android.server.flags.FeatureFlags FEATURE_FLAGS = new com.android.server.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_DISABLE_SYSTEM_COMPACTION = "com.android.server.flags.disable_system_compaction";
    public static final java.lang.String FLAG_PIN_WEBVIEW = "com.android.server.flags.pin_webview";

    public static boolean disableSystemCompaction() {
        return FEATURE_FLAGS.disableSystemCompaction();
    }

    public static boolean pinWebview() {
        return FEATURE_FLAGS.pinWebview();
    }
}
