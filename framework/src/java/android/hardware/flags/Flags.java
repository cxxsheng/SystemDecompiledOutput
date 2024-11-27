package android.hardware.flags;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.hardware.flags.FeatureFlags FEATURE_FLAGS = new android.hardware.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_OVERLAYPROPERTIES_CLASS_API = "android.hardware.flags.overlayproperties_class_api";

    public static boolean overlaypropertiesClassApi() {
        return FEATURE_FLAGS.overlaypropertiesClassApi();
    }
}
