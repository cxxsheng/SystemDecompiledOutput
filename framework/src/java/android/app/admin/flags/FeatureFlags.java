package android.app.admin.flags;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean allowQueryingProfileType();

    boolean assistContentUserRestrictionEnabled();

    boolean backupServiceSecurityLogEventEnabled();

    boolean coexistenceMigrationForNonEmmManagementEnabled();

    boolean crossUserSuspensionEnabled();

    boolean dedicatedDeviceControlApiEnabled();

    boolean dedicatedDeviceControlEnabled();

    boolean defaultSmsPersonalAppSuspensionFixEnabled();

    boolean devicePolicySizeTrackingEnabled();

    boolean deviceTheftApiEnabled();

    boolean deviceTheftImplEnabled();

    boolean dumpsysPolicyEngineMigrationEnabled();

    boolean esimManagementEnabled();

    boolean esimManagementUxEnabled();

    boolean headlessDeviceOwnerSingleUserEnabled();

    boolean isMtePolicyEnforced();

    boolean onboardingBugreportV2Enabled();

    boolean permissionMigrationForZeroTrustApiEnabled();

    boolean permissionMigrationForZeroTrustImplEnabled();

    boolean policyEngineMigrationV2Enabled();

    boolean quietModeCredentialBugFix();

    boolean securityLogV2Enabled();
}
