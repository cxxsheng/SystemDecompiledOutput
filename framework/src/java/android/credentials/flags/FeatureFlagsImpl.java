package android.credentials.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements android.credentials.flags.FeatureFlags {
    @Override // android.credentials.flags.FeatureFlags
    public boolean clearCredentialsApiFixEnabled() {
        return false;
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean clearSessionEnabled() {
        return true;
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean configurableSelectorUiEnabled() {
        return false;
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean credmanBiometricApiEnabled() {
        return false;
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean hybridFilterFixEnabled() {
        return false;
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean instantAppsEnabled() {
        return true;
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean newFrameworkMetrics() {
        return false;
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean newSettingsIntents() {
        return true;
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean newSettingsUi() {
        return false;
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean selectorUiImprovementsEnabled() {
        return false;
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean settingsActivityEnabled() {
        return false;
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean wearCredentialManagerEnabled() {
        return false;
    }
}
