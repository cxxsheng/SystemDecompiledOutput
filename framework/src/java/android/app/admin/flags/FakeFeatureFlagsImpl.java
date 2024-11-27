package android.app.admin.flags;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.app.admin.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.app.admin.flags.Flags.FLAG_ALLOW_QUERYING_PROFILE_TYPE, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_COEXISTENCE_MIGRATION_FOR_NON_EMM_MANAGEMENT_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_CROSS_USER_SUSPENSION_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_DEDICATED_DEVICE_CONTROL_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_DEVICE_THEFT_API_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_DEVICE_THEFT_IMPL_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_DUMPSYS_POLICY_ENGINE_MIGRATION_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_ESIM_MANAGEMENT_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_ESIM_MANAGEMENT_UX_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_IS_MTE_POLICY_ENFORCED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_ONBOARDING_BUGREPORT_V2_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_IMPL_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX, false), java.util.Map.entry(android.app.admin.flags.Flags.FLAG_SECURITY_LOG_V2_ENABLED, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.app.admin.flags.Flags.FLAG_ALLOW_QUERYING_PROFILE_TYPE, android.app.admin.flags.Flags.FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED, android.app.admin.flags.Flags.FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED, android.app.admin.flags.Flags.FLAG_COEXISTENCE_MIGRATION_FOR_NON_EMM_MANAGEMENT_ENABLED, android.app.admin.flags.Flags.FLAG_CROSS_USER_SUSPENSION_ENABLED, android.app.admin.flags.Flags.FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED, android.app.admin.flags.Flags.FLAG_DEDICATED_DEVICE_CONTROL_ENABLED, android.app.admin.flags.Flags.FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED, android.app.admin.flags.Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED, android.app.admin.flags.Flags.FLAG_DEVICE_THEFT_API_ENABLED, android.app.admin.flags.Flags.FLAG_DEVICE_THEFT_IMPL_ENABLED, android.app.admin.flags.Flags.FLAG_DUMPSYS_POLICY_ENGINE_MIGRATION_ENABLED, android.app.admin.flags.Flags.FLAG_ESIM_MANAGEMENT_ENABLED, android.app.admin.flags.Flags.FLAG_ESIM_MANAGEMENT_UX_ENABLED, android.app.admin.flags.Flags.FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED, android.app.admin.flags.Flags.FLAG_IS_MTE_POLICY_ENFORCED, android.app.admin.flags.Flags.FLAG_ONBOARDING_BUGREPORT_V2_ENABLED, android.app.admin.flags.Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED, android.app.admin.flags.Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_IMPL_ENABLED, android.app.admin.flags.Flags.FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED, android.app.admin.flags.Flags.FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX, android.app.admin.flags.Flags.FLAG_SECURITY_LOG_V2_ENABLED, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean allowQueryingProfileType() {
        return getValue(android.app.admin.flags.Flags.FLAG_ALLOW_QUERYING_PROFILE_TYPE);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean assistContentUserRestrictionEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean backupServiceSecurityLogEventEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean coexistenceMigrationForNonEmmManagementEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_COEXISTENCE_MIGRATION_FOR_NON_EMM_MANAGEMENT_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean crossUserSuspensionEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_CROSS_USER_SUSPENSION_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dedicatedDeviceControlApiEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dedicatedDeviceControlEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_DEDICATED_DEVICE_CONTROL_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean defaultSmsPersonalAppSuspensionFixEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean devicePolicySizeTrackingEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean deviceTheftApiEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_DEVICE_THEFT_API_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean deviceTheftImplEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_DEVICE_THEFT_IMPL_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dumpsysPolicyEngineMigrationEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_DUMPSYS_POLICY_ENGINE_MIGRATION_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean esimManagementEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_ESIM_MANAGEMENT_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean esimManagementUxEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_ESIM_MANAGEMENT_UX_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessDeviceOwnerSingleUserEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean isMtePolicyEnforced() {
        return getValue(android.app.admin.flags.Flags.FLAG_IS_MTE_POLICY_ENFORCED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean onboardingBugreportV2Enabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_ONBOARDING_BUGREPORT_V2_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean permissionMigrationForZeroTrustApiEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean permissionMigrationForZeroTrustImplEnabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_IMPL_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean policyEngineMigrationV2Enabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean quietModeCredentialBugFix() {
        return getValue(android.app.admin.flags.Flags.FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX);
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean securityLogV2Enabled() {
        return getValue(android.app.admin.flags.Flags.FLAG_SECURITY_LOG_V2_ENABLED);
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
