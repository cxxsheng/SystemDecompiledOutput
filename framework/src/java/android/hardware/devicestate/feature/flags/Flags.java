package android.hardware.devicestate.feature.flags;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.hardware.devicestate.feature.flags.FeatureFlags FEATURE_FLAGS = new android.hardware.devicestate.feature.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_DEVICE_STATE_PROPERTY_API = "android.hardware.devicestate.feature.flags.device_state_property_api";

    public static boolean deviceStatePropertyApi() {
        return FEATURE_FLAGS.deviceStatePropertyApi();
    }
}
