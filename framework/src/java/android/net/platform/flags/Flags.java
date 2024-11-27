package android.net.platform.flags;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.net.platform.flags.FeatureFlags FEATURE_FLAGS = new android.net.platform.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_IPSEC_TRANSFORM_STATE = "android.net.platform.flags.ipsec_transform_state";
    public static final java.lang.String FLAG_POWERED_OFF_FINDING_PLATFORM = "android.net.platform.flags.powered_off_finding_platform";
    public static final java.lang.String FLAG_REGISTER_NSD_OFFLOAD_ENGINE = "android.net.platform.flags.register_nsd_offload_engine";

    public static boolean ipsecTransformState() {
        return FEATURE_FLAGS.ipsecTransformState();
    }

    public static boolean poweredOffFindingPlatform() {
        return FEATURE_FLAGS.poweredOffFindingPlatform();
    }

    public static boolean registerNsdOffloadEngine() {
        return FEATURE_FLAGS.registerNsdOffloadEngine();
    }
}
