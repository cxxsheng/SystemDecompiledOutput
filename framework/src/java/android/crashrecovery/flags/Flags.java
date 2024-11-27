package android.crashrecovery.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static android.crashrecovery.flags.FeatureFlags FEATURE_FLAGS = new android.crashrecovery.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ENABLE_CRASHRECOVERY = "android.crashrecovery.flags.enable_crashrecovery";
    public static final java.lang.String FLAG_RECOVERABILITY_DETECTION = "android.crashrecovery.flags.recoverability_detection";

    public static boolean enableCrashrecovery() {
        return FEATURE_FLAGS.enableCrashrecovery();
    }

    public static boolean recoverabilityDetection() {
        return FEATURE_FLAGS.recoverabilityDetection();
    }
}
