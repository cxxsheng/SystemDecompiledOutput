package android.service.appprediction.flags;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.service.appprediction.flags.FeatureFlags FEATURE_FLAGS = new android.service.appprediction.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_SERVICE_FEATURES_API = "android.service.appprediction.flags.service_features_api";

    public static boolean serviceFeaturesApi() {
        return FEATURE_FLAGS.serviceFeaturesApi();
    }
}
