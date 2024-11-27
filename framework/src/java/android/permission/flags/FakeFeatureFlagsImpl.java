package android.permission.flags;

/* loaded from: classes3.dex */
public class FakeFeatureFlagsImpl implements android.permission.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.permission.flags.Flags.FLAG_ATTRIBUTION_SOURCE_CONSTRUCTOR, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_FACTORY_RESET_PREP_PERMISSION_APIS, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_IGNORE_PROCESS_TEXT, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_NEW_PERMISSION_GID_ENABLED, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_OP_ENABLE_MOBILE_DATA_BY_USER, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_RETAIL_DEMO_ROLE_ENABLED, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_SET_NEXT_ATTRIBUTION_SOURCE, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_VOICE_ACTIVATION_PERMISSION_APIS, false), java.util.Map.entry(android.permission.flags.Flags.FLAG_WALLET_ROLE_ENABLED, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.permission.flags.Flags.FLAG_ATTRIBUTION_SOURCE_CONSTRUCTOR, android.permission.flags.Flags.FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED, android.permission.flags.Flags.FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED, android.permission.flags.Flags.FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED, android.permission.flags.Flags.FLAG_FACTORY_RESET_PREP_PERMISSION_APIS, android.permission.flags.Flags.FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED, android.permission.flags.Flags.FLAG_IGNORE_PROCESS_TEXT, android.permission.flags.Flags.FLAG_NEW_PERMISSION_GID_ENABLED, android.permission.flags.Flags.FLAG_OP_ENABLE_MOBILE_DATA_BY_USER, android.permission.flags.Flags.FLAG_RETAIL_DEMO_ROLE_ENABLED, android.permission.flags.Flags.FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION, android.permission.flags.Flags.FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION, android.permission.flags.Flags.FLAG_SET_NEXT_ATTRIBUTION_SOURCE, android.permission.flags.Flags.FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE, android.permission.flags.Flags.FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED, android.permission.flags.Flags.FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED, android.permission.flags.Flags.FLAG_VOICE_ACTIVATION_PERMISSION_APIS, android.permission.flags.Flags.FLAG_WALLET_ROLE_ENABLED, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean attributionSourceConstructor() {
        return getValue(android.permission.flags.Flags.FLAG_ATTRIBUTION_SOURCE_CONSTRUCTOR);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean deviceAwarePermissionApisEnabled() {
        return getValue(android.permission.flags.Flags.FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean deviceAwarePermissionsEnabled() {
        return getValue(android.permission.flags.Flags.FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean enhancedConfirmationModeApisEnabled() {
        return getValue(android.permission.flags.Flags.FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean factoryResetPrepPermissionApis() {
        return getValue(android.permission.flags.Flags.FLAG_FACTORY_RESET_PREP_PERMISSION_APIS);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean getEmergencyRoleHolderApiEnabled() {
        return getValue(android.permission.flags.Flags.FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean ignoreProcessText() {
        return getValue(android.permission.flags.Flags.FLAG_IGNORE_PROCESS_TEXT);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean newPermissionGidEnabled() {
        return getValue(android.permission.flags.Flags.FLAG_NEW_PERMISSION_GID_ENABLED);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean opEnableMobileDataByUser() {
        return getValue(android.permission.flags.Flags.FLAG_OP_ENABLE_MOBILE_DATA_BY_USER);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean retailDemoRoleEnabled() {
        return getValue(android.permission.flags.Flags.FLAG_RETAIL_DEMO_ROLE_ENABLED);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean sensitiveNotificationAppProtection() {
        return getValue(android.permission.flags.Flags.FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean serverSideAttributionRegistration() {
        return getValue(android.permission.flags.Flags.FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean setNextAttributionSource() {
        return getValue(android.permission.flags.Flags.FLAG_SET_NEXT_ATTRIBUTION_SOURCE);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean shouldRegisterAttributionSource() {
        return getValue(android.permission.flags.Flags.FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean signaturePermissionAllowlistEnabled() {
        return getValue(android.permission.flags.Flags.FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean systemServerRoleControllerEnabled() {
        return getValue(android.permission.flags.Flags.FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean voiceActivationPermissionApis() {
        return getValue(android.permission.flags.Flags.FLAG_VOICE_ACTIVATION_PERMISSION_APIS);
    }

    @Override // android.permission.flags.FeatureFlags
    public boolean walletRoleEnabled() {
        return getValue(android.permission.flags.Flags.FLAG_WALLET_ROLE_ENABLED);
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
