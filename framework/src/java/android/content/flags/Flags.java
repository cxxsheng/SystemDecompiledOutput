package android.content.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static android.content.flags.FeatureFlags FEATURE_FLAGS = new android.content.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ENABLE_BIND_PACKAGE_ISOLATED_PROCESS = "android.content.flags.enable_bind_package_isolated_process";

    public static boolean enableBindPackageIsolatedProcess() {
        return FEATURE_FLAGS.enableBindPackageIsolatedProcess();
    }
}
