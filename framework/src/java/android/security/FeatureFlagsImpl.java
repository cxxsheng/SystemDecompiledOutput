package android.security;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements android.security.FeatureFlags {
    @Override // android.security.FeatureFlags
    public boolean asmRestrictionsEnabled() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean asmToastsEnabled() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean binaryTransparencySepolicyHash() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean blockNullActionIntents() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean certificateTransparencyConfiguration() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean contentUriPermissionApis() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean deprecateFsvSig() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean enforceIntentFilterMatch() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean extendEcmToAllSettings() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean extendVbChainToUpdatedApk() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean fixUnlockedDeviceRequiredKeysV2() {
        return true;
    }

    @Override // android.security.FeatureFlags
    public boolean frpEnforcement() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean fsverityApi() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean keyinfoUnlockedDeviceRequired() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean mgf1DigestSetterV2() {
        return false;
    }

    @Override // android.security.FeatureFlags
    public boolean reportPrimaryAuthAttempts() {
        return false;
    }
}
