package android.permission.flags;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.permission.flags.FeatureFlags FEATURE_FLAGS = new android.permission.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ATTRIBUTION_SOURCE_CONSTRUCTOR = "android.permission.flags.attribution_source_constructor";
    public static final java.lang.String FLAG_DEVICE_AWARE_PERMISSIONS_ENABLED = "android.permission.flags.device_aware_permissions_enabled";
    public static final java.lang.String FLAG_DEVICE_AWARE_PERMISSION_APIS_ENABLED = "android.permission.flags.device_aware_permission_apis_enabled";
    public static final java.lang.String FLAG_ENHANCED_CONFIRMATION_MODE_APIS_ENABLED = "android.permission.flags.enhanced_confirmation_mode_apis_enabled";
    public static final java.lang.String FLAG_FACTORY_RESET_PREP_PERMISSION_APIS = "android.permission.flags.factory_reset_prep_permission_apis";
    public static final java.lang.String FLAG_GET_EMERGENCY_ROLE_HOLDER_API_ENABLED = "android.permission.flags.get_emergency_role_holder_api_enabled";
    public static final java.lang.String FLAG_IGNORE_PROCESS_TEXT = "android.permission.flags.ignore_process_text";
    public static final java.lang.String FLAG_NEW_PERMISSION_GID_ENABLED = "android.permission.flags.new_permission_gid_enabled";
    public static final java.lang.String FLAG_OP_ENABLE_MOBILE_DATA_BY_USER = "android.permission.flags.op_enable_mobile_data_by_user";
    public static final java.lang.String FLAG_RETAIL_DEMO_ROLE_ENABLED = "android.permission.flags.retail_demo_role_enabled";
    public static final java.lang.String FLAG_SENSITIVE_NOTIFICATION_APP_PROTECTION = "android.permission.flags.sensitive_notification_app_protection";
    public static final java.lang.String FLAG_SERVER_SIDE_ATTRIBUTION_REGISTRATION = "android.permission.flags.server_side_attribution_registration";
    public static final java.lang.String FLAG_SET_NEXT_ATTRIBUTION_SOURCE = "android.permission.flags.set_next_attribution_source";
    public static final java.lang.String FLAG_SHOULD_REGISTER_ATTRIBUTION_SOURCE = "android.permission.flags.should_register_attribution_source";
    public static final java.lang.String FLAG_SIGNATURE_PERMISSION_ALLOWLIST_ENABLED = "android.permission.flags.signature_permission_allowlist_enabled";
    public static final java.lang.String FLAG_SYSTEM_SERVER_ROLE_CONTROLLER_ENABLED = "android.permission.flags.system_server_role_controller_enabled";
    public static final java.lang.String FLAG_VOICE_ACTIVATION_PERMISSION_APIS = "android.permission.flags.voice_activation_permission_apis";
    public static final java.lang.String FLAG_WALLET_ROLE_ENABLED = "android.permission.flags.wallet_role_enabled";

    public static boolean attributionSourceConstructor() {
        return FEATURE_FLAGS.attributionSourceConstructor();
    }

    public static boolean deviceAwarePermissionApisEnabled() {
        return FEATURE_FLAGS.deviceAwarePermissionApisEnabled();
    }

    public static boolean deviceAwarePermissionsEnabled() {
        return FEATURE_FLAGS.deviceAwarePermissionsEnabled();
    }

    public static boolean enhancedConfirmationModeApisEnabled() {
        return FEATURE_FLAGS.enhancedConfirmationModeApisEnabled();
    }

    public static boolean factoryResetPrepPermissionApis() {
        return FEATURE_FLAGS.factoryResetPrepPermissionApis();
    }

    public static boolean getEmergencyRoleHolderApiEnabled() {
        return FEATURE_FLAGS.getEmergencyRoleHolderApiEnabled();
    }

    public static boolean ignoreProcessText() {
        return FEATURE_FLAGS.ignoreProcessText();
    }

    public static boolean newPermissionGidEnabled() {
        return FEATURE_FLAGS.newPermissionGidEnabled();
    }

    public static boolean opEnableMobileDataByUser() {
        return FEATURE_FLAGS.opEnableMobileDataByUser();
    }

    public static boolean retailDemoRoleEnabled() {
        return FEATURE_FLAGS.retailDemoRoleEnabled();
    }

    public static boolean sensitiveNotificationAppProtection() {
        return FEATURE_FLAGS.sensitiveNotificationAppProtection();
    }

    public static boolean serverSideAttributionRegistration() {
        return FEATURE_FLAGS.serverSideAttributionRegistration();
    }

    public static boolean setNextAttributionSource() {
        return FEATURE_FLAGS.setNextAttributionSource();
    }

    public static boolean shouldRegisterAttributionSource() {
        return FEATURE_FLAGS.shouldRegisterAttributionSource();
    }

    public static boolean signaturePermissionAllowlistEnabled() {
        return FEATURE_FLAGS.signaturePermissionAllowlistEnabled();
    }

    public static boolean systemServerRoleControllerEnabled() {
        return FEATURE_FLAGS.systemServerRoleControllerEnabled();
    }

    public static boolean voiceActivationPermissionApis() {
        return FEATURE_FLAGS.voiceActivationPermissionApis();
    }

    public static boolean walletRoleEnabled() {
        return FEATURE_FLAGS.walletRoleEnabled();
    }
}
