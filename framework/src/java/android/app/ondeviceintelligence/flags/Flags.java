package android.app.ondeviceintelligence.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static android.app.ondeviceintelligence.flags.FeatureFlags FEATURE_FLAGS = new android.app.ondeviceintelligence.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ENABLE_ON_DEVICE_INTELLIGENCE = "android.app.ondeviceintelligence.flags.enable_on_device_intelligence";

    public static boolean enableOnDeviceIntelligence() {
        return FEATURE_FLAGS.enableOnDeviceIntelligence();
    }
}
