package android.hardware.radio;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.hardware.radio.FeatureFlags FEATURE_FLAGS = new android.hardware.radio.FeatureFlagsImpl();
    public static final java.lang.String FLAG_HD_RADIO_IMPROVED = "android.hardware.radio.hd_radio_improved";

    public static boolean hdRadioImproved() {
        return FEATURE_FLAGS.hdRadioImproved();
    }
}
