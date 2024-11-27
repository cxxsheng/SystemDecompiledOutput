package android.media.tv.flags;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.media.tv.flags.FeatureFlags FEATURE_FLAGS = new android.media.tv.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_BROADCAST_VISIBILITY_TYPES = "android.media.tv.flags.broadcast_visibility_types";
    public static final java.lang.String FLAG_ENABLE_AD_SERVICE_FW = "android.media.tv.flags.enable_ad_service_fw";
    public static final java.lang.String FLAG_TIAF_V_APIS = "android.media.tv.flags.tiaf_v_apis";

    public static boolean broadcastVisibilityTypes() {
        return FEATURE_FLAGS.broadcastVisibilityTypes();
    }

    public static boolean enableAdServiceFw() {
        return FEATURE_FLAGS.enableAdServiceFw();
    }

    public static boolean tiafVApis() {
        return FEATURE_FLAGS.tiafVApis();
    }
}
