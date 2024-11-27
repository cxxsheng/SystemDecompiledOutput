package android.speech.flags;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.speech.flags.FeatureFlags FEATURE_FLAGS = new android.speech.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_MULTILANG_EXTRA_LAUNCH = "android.speech.flags.multilang_extra_launch";

    public static boolean multilangExtraLaunch() {
        return FEATURE_FLAGS.multilangExtraLaunch();
    }
}
