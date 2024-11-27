package android.app.admin.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements android.app.admin.flags.FeatureFlags {
    @Override // android.app.admin.flags.FeatureFlags
    public boolean allowQueryingProfileType() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean assistContentUserRestrictionEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean backupServiceSecurityLogEventEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean coexistenceMigrationForNonEmmManagementEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean crossUserSuspensionEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dedicatedDeviceControlApiEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dedicatedDeviceControlEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean defaultSmsPersonalAppSuspensionFixEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean devicePolicySizeTrackingEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean deviceTheftApiEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean deviceTheftImplEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean dumpsysPolicyEngineMigrationEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean esimManagementEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean esimManagementUxEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean headlessDeviceOwnerSingleUserEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean isMtePolicyEnforced() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean onboardingBugreportV2Enabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean permissionMigrationForZeroTrustApiEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean permissionMigrationForZeroTrustImplEnabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean policyEngineMigrationV2Enabled() {
        return false;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean quietModeCredentialBugFix() {
        return true;
    }

    @Override // android.app.admin.flags.FeatureFlags
    public boolean securityLogV2Enabled() {
        return false;
    }
}
