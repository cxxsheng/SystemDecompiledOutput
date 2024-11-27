package android.service.controls.flags;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.service.controls.flags.FeatureFlags FEATURE_FLAGS = new android.service.controls.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_HOME_PANEL_DREAM = "android.service.controls.flags.home_panel_dream";

    public static boolean homePanelDream() {
        return FEATURE_FLAGS.homePanelDream();
    }
}
