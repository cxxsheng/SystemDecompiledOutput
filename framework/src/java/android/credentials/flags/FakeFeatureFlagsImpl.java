package android.credentials.flags;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.credentials.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.credentials.flags.Flags.FLAG_CLEAR_CREDENTIALS_API_FIX_ENABLED, false), java.util.Map.entry(android.credentials.flags.Flags.FLAG_CLEAR_SESSION_ENABLED, false), java.util.Map.entry(android.credentials.flags.Flags.FLAG_CONFIGURABLE_SELECTOR_UI_ENABLED, false), java.util.Map.entry(android.credentials.flags.Flags.FLAG_CREDMAN_BIOMETRIC_API_ENABLED, false), java.util.Map.entry(android.credentials.flags.Flags.FLAG_HYBRID_FILTER_FIX_ENABLED, false), java.util.Map.entry(android.credentials.flags.Flags.FLAG_INSTANT_APPS_ENABLED, false), java.util.Map.entry(android.credentials.flags.Flags.FLAG_NEW_FRAMEWORK_METRICS, false), java.util.Map.entry(android.credentials.flags.Flags.FLAG_NEW_SETTINGS_INTENTS, false), java.util.Map.entry(android.credentials.flags.Flags.FLAG_NEW_SETTINGS_UI, false), java.util.Map.entry(android.credentials.flags.Flags.FLAG_SELECTOR_UI_IMPROVEMENTS_ENABLED, false), java.util.Map.entry(android.credentials.flags.Flags.FLAG_SETTINGS_ACTIVITY_ENABLED, false), java.util.Map.entry(android.credentials.flags.Flags.FLAG_WEAR_CREDENTIAL_MANAGER_ENABLED, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.credentials.flags.Flags.FLAG_CLEAR_CREDENTIALS_API_FIX_ENABLED, android.credentials.flags.Flags.FLAG_CLEAR_SESSION_ENABLED, android.credentials.flags.Flags.FLAG_CONFIGURABLE_SELECTOR_UI_ENABLED, android.credentials.flags.Flags.FLAG_CREDMAN_BIOMETRIC_API_ENABLED, android.credentials.flags.Flags.FLAG_HYBRID_FILTER_FIX_ENABLED, android.credentials.flags.Flags.FLAG_INSTANT_APPS_ENABLED, android.credentials.flags.Flags.FLAG_NEW_FRAMEWORK_METRICS, android.credentials.flags.Flags.FLAG_NEW_SETTINGS_INTENTS, android.credentials.flags.Flags.FLAG_NEW_SETTINGS_UI, android.credentials.flags.Flags.FLAG_SELECTOR_UI_IMPROVEMENTS_ENABLED, android.credentials.flags.Flags.FLAG_SETTINGS_ACTIVITY_ENABLED, android.credentials.flags.Flags.FLAG_WEAR_CREDENTIAL_MANAGER_ENABLED, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean clearCredentialsApiFixEnabled() {
        return getValue(android.credentials.flags.Flags.FLAG_CLEAR_CREDENTIALS_API_FIX_ENABLED);
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean clearSessionEnabled() {
        return getValue(android.credentials.flags.Flags.FLAG_CLEAR_SESSION_ENABLED);
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean configurableSelectorUiEnabled() {
        return getValue(android.credentials.flags.Flags.FLAG_CONFIGURABLE_SELECTOR_UI_ENABLED);
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean credmanBiometricApiEnabled() {
        return getValue(android.credentials.flags.Flags.FLAG_CREDMAN_BIOMETRIC_API_ENABLED);
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean hybridFilterFixEnabled() {
        return getValue(android.credentials.flags.Flags.FLAG_HYBRID_FILTER_FIX_ENABLED);
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean instantAppsEnabled() {
        return getValue(android.credentials.flags.Flags.FLAG_INSTANT_APPS_ENABLED);
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean newFrameworkMetrics() {
        return getValue(android.credentials.flags.Flags.FLAG_NEW_FRAMEWORK_METRICS);
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean newSettingsIntents() {
        return getValue(android.credentials.flags.Flags.FLAG_NEW_SETTINGS_INTENTS);
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean newSettingsUi() {
        return getValue(android.credentials.flags.Flags.FLAG_NEW_SETTINGS_UI);
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean selectorUiImprovementsEnabled() {
        return getValue(android.credentials.flags.Flags.FLAG_SELECTOR_UI_IMPROVEMENTS_ENABLED);
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean settingsActivityEnabled() {
        return getValue(android.credentials.flags.Flags.FLAG_SETTINGS_ACTIVITY_ENABLED);
    }

    @Override // android.credentials.flags.FeatureFlags
    public boolean wearCredentialManagerEnabled() {
        return getValue(android.credentials.flags.Flags.FLAG_WEAR_CREDENTIAL_MANAGER_ENABLED);
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
