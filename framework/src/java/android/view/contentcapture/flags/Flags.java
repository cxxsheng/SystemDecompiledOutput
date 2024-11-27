package android.view.contentcapture.flags;

/* loaded from: classes4.dex */
public final class Flags {
    private static android.view.contentcapture.flags.FeatureFlags FEATURE_FLAGS = new android.view.contentcapture.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_RUN_ON_BACKGROUND_THREAD_ENABLED = "android.view.contentcapture.flags.run_on_background_thread_enabled";

    public static boolean runOnBackgroundThreadEnabled() {
        return FEATURE_FLAGS.runOnBackgroundThreadEnabled();
    }
}
