package android.hardware.biometrics;

/* loaded from: classes.dex */
public final class Flags {
    private static android.hardware.biometrics.FeatureFlags FEATURE_FLAGS = new android.hardware.biometrics.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT = "android.hardware.biometrics.add_key_agreement_crypto_object";
    public static final java.lang.String FLAG_CUSTOM_BIOMETRIC_PROMPT = "android.hardware.biometrics.custom_biometric_prompt";
    public static final java.lang.String FLAG_GET_OP_ID_CRYPTO_OBJECT = "android.hardware.biometrics.get_op_id_crypto_object";
    public static final java.lang.String FLAG_LAST_AUTHENTICATION_TIME = "android.hardware.biometrics.last_authentication_time";

    public static boolean addKeyAgreementCryptoObject() {
        return FEATURE_FLAGS.addKeyAgreementCryptoObject();
    }

    public static boolean customBiometricPrompt() {
        return FEATURE_FLAGS.customBiometricPrompt();
    }

    public static boolean getOpIdCryptoObject() {
        return FEATURE_FLAGS.getOpIdCryptoObject();
    }

    public static boolean lastAuthenticationTime() {
        return FEATURE_FLAGS.lastAuthenticationTime();
    }
}
