package android.net.vcn;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.net.vcn.FeatureFlags FEATURE_FLAGS = new android.net.vcn.FeatureFlagsImpl();
    public static final java.lang.String FLAG_NETWORK_METRIC_MONITOR = "android.net.vcn.network_metric_monitor";
    public static final java.lang.String FLAG_SAFE_MODE_CONFIG = "android.net.vcn.safe_mode_config";
    public static final java.lang.String FLAG_SAFE_MODE_TIMEOUT_CONFIG = "android.net.vcn.safe_mode_timeout_config";

    public static boolean networkMetricMonitor() {
        return FEATURE_FLAGS.networkMetricMonitor();
    }

    public static boolean safeModeConfig() {
        return FEATURE_FLAGS.safeModeConfig();
    }

    public static boolean safeModeTimeoutConfig() {
        return FEATURE_FLAGS.safeModeTimeoutConfig();
    }
}
