package android.security;

/* loaded from: classes3.dex */
public class FakeFeatureFlagsImpl implements android.security.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.security.Flags.FLAG_ASM_RESTRICTIONS_ENABLED, false), java.util.Map.entry(android.security.Flags.FLAG_ASM_TOASTS_ENABLED, false), java.util.Map.entry(android.security.Flags.FLAG_BINARY_TRANSPARENCY_SEPOLICY_HASH, false), java.util.Map.entry(android.security.Flags.FLAG_BLOCK_NULL_ACTION_INTENTS, false), java.util.Map.entry(android.security.Flags.FLAG_CERTIFICATE_TRANSPARENCY_CONFIGURATION, false), java.util.Map.entry(android.security.Flags.FLAG_CONTENT_URI_PERMISSION_APIS, false), java.util.Map.entry(android.security.Flags.FLAG_DEPRECATE_FSV_SIG, false), java.util.Map.entry(android.security.Flags.FLAG_ENFORCE_INTENT_FILTER_MATCH, false), java.util.Map.entry(android.security.Flags.FLAG_EXTEND_ECM_TO_ALL_SETTINGS, false), java.util.Map.entry(android.security.Flags.FLAG_EXTEND_VB_CHAIN_TO_UPDATED_APK, false), java.util.Map.entry(android.security.Flags.FLAG_FIX_UNLOCKED_DEVICE_REQUIRED_KEYS_V2, false), java.util.Map.entry(android.security.Flags.FLAG_FRP_ENFORCEMENT, false), java.util.Map.entry(android.security.Flags.FLAG_FSVERITY_API, false), java.util.Map.entry(android.security.Flags.FLAG_KEYINFO_UNLOCKED_DEVICE_REQUIRED, false), java.util.Map.entry(android.security.Flags.FLAG_MGF1_DIGEST_SETTER_V2, false), java.util.Map.entry(android.security.Flags.FLAG_REPORT_PRIMARY_AUTH_ATTEMPTS, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.security.Flags.FLAG_ASM_RESTRICTIONS_ENABLED, android.security.Flags.FLAG_ASM_TOASTS_ENABLED, android.security.Flags.FLAG_BINARY_TRANSPARENCY_SEPOLICY_HASH, android.security.Flags.FLAG_BLOCK_NULL_ACTION_INTENTS, android.security.Flags.FLAG_CERTIFICATE_TRANSPARENCY_CONFIGURATION, android.security.Flags.FLAG_CONTENT_URI_PERMISSION_APIS, android.security.Flags.FLAG_DEPRECATE_FSV_SIG, android.security.Flags.FLAG_ENFORCE_INTENT_FILTER_MATCH, android.security.Flags.FLAG_EXTEND_ECM_TO_ALL_SETTINGS, android.security.Flags.FLAG_EXTEND_VB_CHAIN_TO_UPDATED_APK, android.security.Flags.FLAG_FIX_UNLOCKED_DEVICE_REQUIRED_KEYS_V2, android.security.Flags.FLAG_FRP_ENFORCEMENT, android.security.Flags.FLAG_FSVERITY_API, android.security.Flags.FLAG_KEYINFO_UNLOCKED_DEVICE_REQUIRED, android.security.Flags.FLAG_MGF1_DIGEST_SETTER_V2, android.security.Flags.FLAG_REPORT_PRIMARY_AUTH_ATTEMPTS, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.security.FeatureFlags
    public boolean asmRestrictionsEnabled() {
        return getValue(android.security.Flags.FLAG_ASM_RESTRICTIONS_ENABLED);
    }

    @Override // android.security.FeatureFlags
    public boolean asmToastsEnabled() {
        return getValue(android.security.Flags.FLAG_ASM_TOASTS_ENABLED);
    }

    @Override // android.security.FeatureFlags
    public boolean binaryTransparencySepolicyHash() {
        return getValue(android.security.Flags.FLAG_BINARY_TRANSPARENCY_SEPOLICY_HASH);
    }

    @Override // android.security.FeatureFlags
    public boolean blockNullActionIntents() {
        return getValue(android.security.Flags.FLAG_BLOCK_NULL_ACTION_INTENTS);
    }

    @Override // android.security.FeatureFlags
    public boolean certificateTransparencyConfiguration() {
        return getValue(android.security.Flags.FLAG_CERTIFICATE_TRANSPARENCY_CONFIGURATION);
    }

    @Override // android.security.FeatureFlags
    public boolean contentUriPermissionApis() {
        return getValue(android.security.Flags.FLAG_CONTENT_URI_PERMISSION_APIS);
    }

    @Override // android.security.FeatureFlags
    public boolean deprecateFsvSig() {
        return getValue(android.security.Flags.FLAG_DEPRECATE_FSV_SIG);
    }

    @Override // android.security.FeatureFlags
    public boolean enforceIntentFilterMatch() {
        return getValue(android.security.Flags.FLAG_ENFORCE_INTENT_FILTER_MATCH);
    }

    @Override // android.security.FeatureFlags
    public boolean extendEcmToAllSettings() {
        return getValue(android.security.Flags.FLAG_EXTEND_ECM_TO_ALL_SETTINGS);
    }

    @Override // android.security.FeatureFlags
    public boolean extendVbChainToUpdatedApk() {
        return getValue(android.security.Flags.FLAG_EXTEND_VB_CHAIN_TO_UPDATED_APK);
    }

    @Override // android.security.FeatureFlags
    public boolean fixUnlockedDeviceRequiredKeysV2() {
        return getValue(android.security.Flags.FLAG_FIX_UNLOCKED_DEVICE_REQUIRED_KEYS_V2);
    }

    @Override // android.security.FeatureFlags
    public boolean frpEnforcement() {
        return getValue(android.security.Flags.FLAG_FRP_ENFORCEMENT);
    }

    @Override // android.security.FeatureFlags
    public boolean fsverityApi() {
        return getValue(android.security.Flags.FLAG_FSVERITY_API);
    }

    @Override // android.security.FeatureFlags
    public boolean keyinfoUnlockedDeviceRequired() {
        return getValue(android.security.Flags.FLAG_KEYINFO_UNLOCKED_DEVICE_REQUIRED);
    }

    @Override // android.security.FeatureFlags
    public boolean mgf1DigestSetterV2() {
        return getValue(android.security.Flags.FLAG_MGF1_DIGEST_SETTER_V2);
    }

    @Override // android.security.FeatureFlags
    public boolean reportPrimaryAuthAttempts() {
        return getValue(android.security.Flags.FLAG_REPORT_PRIMARY_AUTH_ATTEMPTS);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    private boolean isOptimizationEnabled() {
        return false;
    }
}
