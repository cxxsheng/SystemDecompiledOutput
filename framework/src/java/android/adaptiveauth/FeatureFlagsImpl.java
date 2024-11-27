package android.adaptiveauth;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements android.adaptiveauth.FeatureFlags {
    @Override // android.adaptiveauth.FeatureFlags
    public boolean enableAdaptiveAuth() {
        return false;
    }

    @Override // android.adaptiveauth.FeatureFlags
    public boolean reportBiometricAuthAttempts() {
        return false;
    }
}
