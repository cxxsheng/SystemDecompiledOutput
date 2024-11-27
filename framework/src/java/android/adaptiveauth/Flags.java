package android.adaptiveauth;

/* loaded from: classes.dex */
public final class Flags {
    private static android.adaptiveauth.FeatureFlags FEATURE_FLAGS = new android.adaptiveauth.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ENABLE_ADAPTIVE_AUTH = "android.adaptiveauth.enable_adaptive_auth";
    public static final java.lang.String FLAG_REPORT_BIOMETRIC_AUTH_ATTEMPTS = "android.adaptiveauth.report_biometric_auth_attempts";

    public static boolean enableAdaptiveAuth() {
        return FEATURE_FLAGS.enableAdaptiveAuth();
    }

    public static boolean reportBiometricAuthAttempts() {
        return FEATURE_FLAGS.reportBiometricAuthAttempts();
    }
}
