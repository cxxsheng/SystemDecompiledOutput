package android.app.smartspace.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static android.app.smartspace.flags.FeatureFlags FEATURE_FLAGS = new android.app.smartspace.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ACCESS_SMARTSPACE = "android.app.smartspace.flags.access_smartspace";
    public static final java.lang.String FLAG_REMOTE_VIEWS = "android.app.smartspace.flags.remote_views";

    public static boolean accessSmartspace() {
        return FEATURE_FLAGS.accessSmartspace();
    }

    public static boolean remoteViews() {
        return FEATURE_FLAGS.remoteViews();
    }
}
