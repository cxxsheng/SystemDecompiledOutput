package android.app.admin.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static android.app.admin.flags.FeatureFlags FEATURE_FLAGS = new android.app.admin.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ALLOW_QUERYING_PROFILE_TYPE = "android.app.admin.flags.allow_querying_profile_type";
    public static final java.lang.String FLAG_ASSIST_CONTENT_USER_RESTRICTION_ENABLED = "android.app.admin.flags.assist_content_user_restriction_enabled";
    public static final java.lang.String FLAG_BACKUP_SERVICE_SECURITY_LOG_EVENT_ENABLED = "android.app.admin.flags.backup_service_security_log_event_enabled";
    public static final java.lang.String FLAG_COEXISTENCE_MIGRATION_FOR_NON_EMM_MANAGEMENT_ENABLED = "android.app.admin.flags.coexistence_migration_for_non_emm_management_enabled";
    public static final java.lang.String FLAG_CROSS_USER_SUSPENSION_ENABLED = "android.app.admin.flags.cross_user_suspension_enabled";
    public static final java.lang.String FLAG_DEDICATED_DEVICE_CONTROL_API_ENABLED = "android.app.admin.flags.dedicated_device_control_api_enabled";
    public static final java.lang.String FLAG_DEDICATED_DEVICE_CONTROL_ENABLED = "android.app.admin.flags.dedicated_device_control_enabled";
    public static final java.lang.String FLAG_DEFAULT_SMS_PERSONAL_APP_SUSPENSION_FIX_ENABLED = "android.app.admin.flags.default_sms_personal_app_suspension_fix_enabled";
    public static final java.lang.String FLAG_DEVICE_POLICY_SIZE_TRACKING_ENABLED = "android.app.admin.flags.device_policy_size_tracking_enabled";
    public static final java.lang.String FLAG_DEVICE_THEFT_API_ENABLED = "android.app.admin.flags.device_theft_api_enabled";
    public static final java.lang.String FLAG_DEVICE_THEFT_IMPL_ENABLED = "android.app.admin.flags.device_theft_impl_enabled";
    public static final java.lang.String FLAG_DUMPSYS_POLICY_ENGINE_MIGRATION_ENABLED = "android.app.admin.flags.dumpsys_policy_engine_migration_enabled";
    public static final java.lang.String FLAG_ESIM_MANAGEMENT_ENABLED = "android.app.admin.flags.esim_management_enabled";
    public static final java.lang.String FLAG_ESIM_MANAGEMENT_UX_ENABLED = "android.app.admin.flags.esim_management_ux_enabled";
    public static final java.lang.String FLAG_HEADLESS_DEVICE_OWNER_SINGLE_USER_ENABLED = "android.app.admin.flags.headless_device_owner_single_user_enabled";
    public static final java.lang.String FLAG_IS_MTE_POLICY_ENFORCED = "android.app.admin.flags.is_mte_policy_enforced";
    public static final java.lang.String FLAG_ONBOARDING_BUGREPORT_V2_ENABLED = "android.app.admin.flags.onboarding_bugreport_v2_enabled";
    public static final java.lang.String FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_API_ENABLED = "android.app.admin.flags.permission_migration_for_zero_trust_api_enabled";
    public static final java.lang.String FLAG_PERMISSION_MIGRATION_FOR_ZERO_TRUST_IMPL_ENABLED = "android.app.admin.flags.permission_migration_for_zero_trust_impl_enabled";
    public static final java.lang.String FLAG_POLICY_ENGINE_MIGRATION_V2_ENABLED = "android.app.admin.flags.policy_engine_migration_v2_enabled";
    public static final java.lang.String FLAG_QUIET_MODE_CREDENTIAL_BUG_FIX = "android.app.admin.flags.quiet_mode_credential_bug_fix";
    public static final java.lang.String FLAG_SECURITY_LOG_V2_ENABLED = "android.app.admin.flags.security_log_v2_enabled";

    public static boolean allowQueryingProfileType() {
        return FEATURE_FLAGS.allowQueryingProfileType();
    }

    public static boolean assistContentUserRestrictionEnabled() {
        return FEATURE_FLAGS.assistContentUserRestrictionEnabled();
    }

    public static boolean backupServiceSecurityLogEventEnabled() {
        return FEATURE_FLAGS.backupServiceSecurityLogEventEnabled();
    }

    public static boolean coexistenceMigrationForNonEmmManagementEnabled() {
        return FEATURE_FLAGS.coexistenceMigrationForNonEmmManagementEnabled();
    }

    public static boolean crossUserSuspensionEnabled() {
        return FEATURE_FLAGS.crossUserSuspensionEnabled();
    }

    public static boolean dedicatedDeviceControlApiEnabled() {
        return FEATURE_FLAGS.dedicatedDeviceControlApiEnabled();
    }

    public static boolean dedicatedDeviceControlEnabled() {
        return FEATURE_FLAGS.dedicatedDeviceControlEnabled();
    }

    public static boolean defaultSmsPersonalAppSuspensionFixEnabled() {
        return FEATURE_FLAGS.defaultSmsPersonalAppSuspensionFixEnabled();
    }

    public static boolean devicePolicySizeTrackingEnabled() {
        return FEATURE_FLAGS.devicePolicySizeTrackingEnabled();
    }

    public static boolean deviceTheftApiEnabled() {
        return FEATURE_FLAGS.deviceTheftApiEnabled();
    }

    public static boolean deviceTheftImplEnabled() {
        return FEATURE_FLAGS.deviceTheftImplEnabled();
    }

    public static boolean dumpsysPolicyEngineMigrationEnabled() {
        return FEATURE_FLAGS.dumpsysPolicyEngineMigrationEnabled();
    }

    public static boolean esimManagementEnabled() {
        return FEATURE_FLAGS.esimManagementEnabled();
    }

    public static boolean esimManagementUxEnabled() {
        return FEATURE_FLAGS.esimManagementUxEnabled();
    }

    public static boolean headlessDeviceOwnerSingleUserEnabled() {
        return FEATURE_FLAGS.headlessDeviceOwnerSingleUserEnabled();
    }

    public static boolean isMtePolicyEnforced() {
        return FEATURE_FLAGS.isMtePolicyEnforced();
    }

    public static boolean onboardingBugreportV2Enabled() {
        return FEATURE_FLAGS.onboardingBugreportV2Enabled();
    }

    public static boolean permissionMigrationForZeroTrustApiEnabled() {
        return FEATURE_FLAGS.permissionMigrationForZeroTrustApiEnabled();
    }

    public static boolean permissionMigrationForZeroTrustImplEnabled() {
        return FEATURE_FLAGS.permissionMigrationForZeroTrustImplEnabled();
    }

    public static boolean policyEngineMigrationV2Enabled() {
        return FEATURE_FLAGS.policyEngineMigrationV2Enabled();
    }

    public static boolean quietModeCredentialBugFix() {
        return FEATURE_FLAGS.quietModeCredentialBugFix();
    }

    public static boolean securityLogV2Enabled() {
        return FEATURE_FLAGS.securityLogV2Enabled();
    }
}
