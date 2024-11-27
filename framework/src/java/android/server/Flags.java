package android.server;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.server.FeatureFlags FEATURE_FLAGS = new android.server.FeatureFlagsImpl();
    public static final java.lang.String FLAG_TELEMETRY_APIS_SERVICE = "android.server.telemetry_apis_service";

    public static boolean telemetryApisService() {
        return FEATURE_FLAGS.telemetryApisService();
    }
}
