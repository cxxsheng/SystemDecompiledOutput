package android.hardware.biometrics;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements android.hardware.biometrics.FeatureFlags {
    @Override // android.hardware.biometrics.FeatureFlags
    public boolean addKeyAgreementCryptoObject() {
        return false;
    }

    @Override // android.hardware.biometrics.FeatureFlags
    public boolean customBiometricPrompt() {
        return false;
    }

    @Override // android.hardware.biometrics.FeatureFlags
    public boolean getOpIdCryptoObject() {
        return false;
    }

    @Override // android.hardware.biometrics.FeatureFlags
    public boolean lastAuthenticationTime() {
        return false;
    }
}
