package android.service.dreams;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.service.dreams.FeatureFlags FEATURE_FLAGS = new android.service.dreams.FeatureFlagsImpl();
    public static final java.lang.String FLAG_DREAM_OVERLAY_HOST = "android.service.dreams.dream_overlay_host";

    public static boolean dreamOverlayHost() {
        return FEATURE_FLAGS.dreamOverlayHost();
    }
}
