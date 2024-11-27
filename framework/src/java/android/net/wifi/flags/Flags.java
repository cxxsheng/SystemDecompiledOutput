package android.net.wifi.flags;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.net.wifi.flags.FeatureFlags FEATURE_FLAGS = new android.net.wifi.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_GET_DEVICE_CROSS_AKM_ROAMING_SUPPORT = "android.net.wifi.flags.get_device_cross_akm_roaming_support";

    public static boolean getDeviceCrossAkmRoamingSupport() {
        return FEATURE_FLAGS.getDeviceCrossAkmRoamingSupport();
    }
}
