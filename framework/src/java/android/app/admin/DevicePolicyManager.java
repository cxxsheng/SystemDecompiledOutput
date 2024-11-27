package android.app.admin;

/* loaded from: classes.dex */
public class DevicePolicyManager {

    @android.annotation.SystemApi
    public static final java.lang.String ACCOUNT_FEATURE_DEVICE_OR_PROFILE_OWNER_ALLOWED = "android.account.DEVICE_OR_PROFILE_OWNER_ALLOWED";

    @android.annotation.SystemApi
    public static final java.lang.String ACCOUNT_FEATURE_DEVICE_OR_PROFILE_OWNER_DISALLOWED = "android.account.DEVICE_OR_PROFILE_OWNER_DISALLOWED";
    public static final java.lang.String ACTION_ADD_DEVICE_ADMIN = "android.app.action.ADD_DEVICE_ADMIN";
    public static final java.lang.String ACTION_ADMIN_POLICY_COMPLIANCE = "android.app.action.ADMIN_POLICY_COMPLIANCE";
    public static final java.lang.String ACTION_APPLICATION_DELEGATION_SCOPES_CHANGED = "android.app.action.APPLICATION_DELEGATION_SCOPES_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_BIND_SECONDARY_LOCKSCREEN_SERVICE = "android.app.action.BIND_SECONDARY_LOCKSCREEN_SERVICE";
    public static final java.lang.String ACTION_BUGREPORT_SHARING_ACCEPTED = "com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED";
    public static final java.lang.String ACTION_BUGREPORT_SHARING_DECLINED = "com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED";
    public static final java.lang.String ACTION_CHECK_POLICY_COMPLIANCE = "android.app.action.CHECK_POLICY_COMPLIANCE";
    public static final java.lang.String ACTION_DATA_SHARING_RESTRICTION_APPLIED = "android.app.action.DATA_SHARING_RESTRICTION_APPLIED";
    public static final java.lang.String ACTION_DEVICE_ADMIN_SERVICE = "android.app.action.DEVICE_ADMIN_SERVICE";
    public static final java.lang.String ACTION_DEVICE_FINANCING_STATE_CHANGED = "android.app.admin.action.DEVICE_FINANCING_STATE_CHANGED";
    public static final java.lang.String ACTION_DEVICE_OWNER_CHANGED = "android.app.action.DEVICE_OWNER_CHANGED";
    public static final java.lang.String ACTION_DEVICE_POLICY_CONSTANTS_CHANGED = "android.app.action.DEVICE_POLICY_CONSTANTS_CHANGED";
    public static final java.lang.String ACTION_DEVICE_POLICY_MANAGER_STATE_CHANGED = "android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED";
    public static final java.lang.String ACTION_DEVICE_POLICY_RESOURCE_UPDATED = "android.app.action.DEVICE_POLICY_RESOURCE_UPDATED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_ESTABLISH_NETWORK_CONNECTION = "android.app.action.ESTABLISH_NETWORK_CONNECTION";
    public static final java.lang.String ACTION_GET_PROVISIONING_MODE = "android.app.action.GET_PROVISIONING_MODE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_LOST_MODE_LOCATION_UPDATE = "android.app.action.LOST_MODE_LOCATION_UPDATE";
    public static final java.lang.String ACTION_MANAGED_PROFILE_PROVISIONED = "android.app.action.MANAGED_PROFILE_PROVISIONED";
    public static final java.lang.String ACTION_PROFILE_OWNER_CHANGED = "android.app.action.PROFILE_OWNER_CHANGED";
    public static final java.lang.String ACTION_PROVISIONING_COMPLETED = "android.app.action.PROVISIONING_COMPLETED";
    public static final java.lang.String ACTION_PROVISIONING_SUCCESSFUL = "android.app.action.PROVISIONING_SUCCESSFUL";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_PROVISION_FINALIZATION = "android.app.action.PROVISION_FINALIZATION";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_PROVISION_FINANCED_DEVICE = "android.app.action.PROVISION_FINANCED_DEVICE";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_PROVISION_MANAGED_DEVICE = "android.app.action.PROVISION_MANAGED_DEVICE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE = "android.app.action.PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE";
    public static final java.lang.String ACTION_PROVISION_MANAGED_PROFILE = "android.app.action.PROVISION_MANAGED_PROFILE";
    public static final java.lang.String ACTION_PROVISION_MANAGED_USER = "android.app.action.PROVISION_MANAGED_USER";
    public static final java.lang.String ACTION_REMOTE_BUGREPORT_DISPATCH = "android.intent.action.REMOTE_BUGREPORT_DISPATCH";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_RESET_PROTECTION_POLICY_CHANGED = "android.app.action.RESET_PROTECTION_POLICY_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_ROLE_HOLDER_PROVISION_FINALIZATION = "android.app.action.ROLE_HOLDER_PROVISION_FINALIZATION";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_ROLE_HOLDER_PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE = "android.app.action.ROLE_HOLDER_PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_ROLE_HOLDER_PROVISION_MANAGED_PROFILE = "android.app.action.ROLE_HOLDER_PROVISION_MANAGED_PROFILE";
    public static final java.lang.String ACTION_SET_NEW_PARENT_PROFILE_PASSWORD = "android.app.action.SET_NEW_PARENT_PROFILE_PASSWORD";
    public static final java.lang.String ACTION_SET_NEW_PASSWORD = "android.app.action.SET_NEW_PASSWORD";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_SET_PROFILE_OWNER = "android.app.action.SET_PROFILE_OWNER";
    public static final java.lang.String ACTION_SHOW_DEVICE_MONITORING_DIALOG = "android.app.action.SHOW_DEVICE_MONITORING_DIALOG";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String ACTION_SHOW_NEW_USER_DISCLAIMER = "android.app.action.SHOW_NEW_USER_DISCLAIMER";
    public static final java.lang.String ACTION_START_ENCRYPTION = "android.app.action.START_ENCRYPTION";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String ACTION_STATE_USER_SETUP_COMPLETE = "android.app.action.STATE_USER_SETUP_COMPLETE";
    public static final java.lang.String ACTION_SYSTEM_UPDATE_POLICY_CHANGED = "android.app.action.SYSTEM_UPDATE_POLICY_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_UPDATE_DEVICE_POLICY_MANAGEMENT_ROLE_HOLDER = "android.app.action.UPDATE_DEVICE_POLICY_MANAGEMENT_ROLE_HOLDER";
    public static final java.lang.String ADD_ISFINANCED_DEVICE_FLAG = "add-isfinanced-device";
    public static final boolean ADD_ISFINANCED_FEVICE_DEFAULT = true;
    public static final int CONTENT_PROTECTION_DISABLED = 1;
    public static final int CONTENT_PROTECTION_ENABLED = 2;
    public static final int CONTENT_PROTECTION_NOT_CONTROLLED_BY_POLICY = 0;
    public static final long DEFAULT_STRONG_AUTH_TIMEOUT_MS = 259200000;
    public static final java.lang.String DELEGATION_APP_RESTRICTIONS = "delegation-app-restrictions";
    public static final java.lang.String DELEGATION_BLOCK_UNINSTALL = "delegation-block-uninstall";
    public static final java.lang.String DELEGATION_CERT_INSTALL = "delegation-cert-install";
    public static final java.lang.String DELEGATION_CERT_SELECTION = "delegation-cert-selection";
    public static final java.lang.String DELEGATION_ENABLE_SYSTEM_APP = "delegation-enable-system-app";
    public static final java.lang.String DELEGATION_INSTALL_EXISTING_PACKAGE = "delegation-install-existing-package";
    public static final java.lang.String DELEGATION_KEEP_UNINSTALLED_PACKAGES = "delegation-keep-uninstalled-packages";
    public static final java.lang.String DELEGATION_NETWORK_LOGGING = "delegation-network-logging";
    public static final java.lang.String DELEGATION_PACKAGE_ACCESS = "delegation-package-access";
    public static final java.lang.String DELEGATION_PERMISSION_GRANT = "delegation-permission-grant";
    public static final java.lang.String DELEGATION_SECURITY_LOGGING = "delegation-security-logging";
    public static final boolean DEPRECATE_USERMANAGERINTERNAL_DEVICEPOLICY_DEFAULT = true;
    public static final java.lang.String DEPRECATE_USERMANAGERINTERNAL_DEVICEPOLICY_FLAG = "deprecate_usermanagerinternal_devicepolicy";
    public static final int DEVICE_OWNER_TYPE_DEFAULT = 0;
    public static final int DEVICE_OWNER_TYPE_FINANCED = 1;

    @java.lang.Deprecated
    public static final int ENCRYPTION_STATUS_ACTIVATING = 2;
    public static final int ENCRYPTION_STATUS_ACTIVE = 3;
    public static final int ENCRYPTION_STATUS_ACTIVE_DEFAULT_KEY = 4;
    public static final int ENCRYPTION_STATUS_ACTIVE_PER_USER = 5;
    public static final int ENCRYPTION_STATUS_INACTIVE = 1;
    public static final int ENCRYPTION_STATUS_UNSUPPORTED = 0;
    public static final int ERROR_PACKAGE_NAME_NOT_FOUND = 1;
    public static final int ERROR_VPN_PACKAGE_NOT_FOUND = 1;

    @android.annotation.SystemApi
    public static final int EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION = 2;

    @android.annotation.SystemApi
    public static final int EXEMPT_FROM_DISMISSIBLE_NOTIFICATIONS = 1;

    @android.annotation.SystemApi
    public static final int EXEMPT_FROM_HIBERNATION = 3;

    @android.annotation.SystemApi
    public static final int EXEMPT_FROM_POWER_RESTRICTIONS = 4;

    @android.annotation.SystemApi
    public static final int EXEMPT_FROM_SUSPENSION = 0;
    public static final java.lang.String EXTRA_ADD_EXPLANATION = "android.app.extra.ADD_EXPLANATION";
    public static final java.lang.String EXTRA_BUGREPORT_NOTIFICATION_TYPE = "android.app.extra.bugreport_notification_type";
    public static final java.lang.String EXTRA_DELEGATION_SCOPES = "android.app.extra.DELEGATION_SCOPES";
    public static final java.lang.String EXTRA_DEVICE_ADMIN = "android.app.extra.DEVICE_ADMIN";
    public static final java.lang.String EXTRA_DEVICE_PASSWORD_REQUIREMENT_ONLY = "android.app.extra.DEVICE_PASSWORD_REQUIREMENT_ONLY";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_FORCE_UPDATE_ROLE_HOLDER = "android.app.extra.FORCE_UPDATE_ROLE_HOLDER";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_LOST_MODE_LOCATION = "android.app.extra.LOST_MODE_LOCATION";
    public static final java.lang.String EXTRA_PASSWORD_COMPLEXITY = "android.app.extra.PASSWORD_COMPLEXITY";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROFILE_OWNER_NAME = "android.app.extra.PROFILE_OWNER_NAME";
    public static final java.lang.String EXTRA_PROVISIONING_ACCOUNT_TO_MIGRATE = "android.app.extra.PROVISIONING_ACCOUNT_TO_MIGRATE";
    public static final java.lang.String EXTRA_PROVISIONING_ACTION = "android.app.extra.PROVISIONING_ACTION";
    public static final java.lang.String EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE = "android.app.extra.PROVISIONING_ADMIN_EXTRAS_BUNDLE";
    public static final java.lang.String EXTRA_PROVISIONING_ALLOWED_PROVISIONING_MODES = "android.app.extra.PROVISIONING_ALLOWED_PROVISIONING_MODES";
    public static final java.lang.String EXTRA_PROVISIONING_ALLOW_OFFLINE = "android.app.extra.PROVISIONING_ALLOW_OFFLINE";
    public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME = "android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME";
    public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE = "android.app.extra.PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE";
    public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM";
    public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_COOKIE_HEADER = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_COOKIE_HEADER";
    public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_ICON_URI = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_ICON_URI";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_LABEL = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_LABEL";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME";
    public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM = "android.app.extra.PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM";
    public static final java.lang.String EXTRA_PROVISIONING_DISCLAIMERS = "android.app.extra.PROVISIONING_DISCLAIMERS";
    public static final java.lang.String EXTRA_PROVISIONING_DISCLAIMER_CONTENT = "android.app.extra.PROVISIONING_DISCLAIMER_CONTENT";
    public static final java.lang.String EXTRA_PROVISIONING_DISCLAIMER_HEADER = "android.app.extra.PROVISIONING_DISCLAIMER_HEADER";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PROVISIONING_EMAIL_ADDRESS = "android.app.extra.PROVISIONING_EMAIL_ADDRESS";
    public static final java.lang.String EXTRA_PROVISIONING_IMEI = "android.app.extra.PROVISIONING_IMEI";
    public static final java.lang.String EXTRA_PROVISIONING_KEEP_ACCOUNT_ON_MIGRATION = "android.app.extra.PROVISIONING_KEEP_ACCOUNT_ON_MIGRATION";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PROVISIONING_KEEP_SCREEN_ON = "android.app.extra.PROVISIONING_KEEP_SCREEN_ON";
    public static final java.lang.String EXTRA_PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED = "android.app.extra.PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED";
    public static final java.lang.String EXTRA_PROVISIONING_LOCALE = "android.app.extra.PROVISIONING_LOCALE";
    public static final java.lang.String EXTRA_PROVISIONING_LOCAL_TIME = "android.app.extra.PROVISIONING_LOCAL_TIME";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PROVISIONING_LOGO_URI = "android.app.extra.PROVISIONING_LOGO_URI";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PROVISIONING_MAIN_COLOR = "android.app.extra.PROVISIONING_MAIN_COLOR";
    public static final java.lang.String EXTRA_PROVISIONING_MODE = "android.app.extra.PROVISIONING_MODE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROVISIONING_ORGANIZATION_NAME = "android.app.extra.PROVISIONING_ORGANIZATION_NAME";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROVISIONING_RETURN_BEFORE_POLICY_COMPLIANCE = "android.app.extra.PROVISIONING_RETURN_BEFORE_POLICY_COMPLIANCE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROVISIONING_ROLE_HOLDER_CUSTOM_USER_CONSENT_INTENT = "android.app.extra.PROVISIONING_ROLE_HOLDER_CUSTOM_USER_CONSENT_INTENT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROVISIONING_ROLE_HOLDER_EXTRAS_BUNDLE = "android.app.extra.PROVISIONING_ROLE_HOLDER_EXTRAS_BUNDLE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROVISIONING_ROLE_HOLDER_PACKAGE_DOWNLOAD_COOKIE_HEADER = "android.app.extra.PROVISIONING_ROLE_HOLDER_PACKAGE_DOWNLOAD_COOKIE_HEADER";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROVISIONING_ROLE_HOLDER_PACKAGE_DOWNLOAD_LOCATION = "android.app.extra.PROVISIONING_ROLE_HOLDER_PACKAGE_DOWNLOAD_LOCATION";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROVISIONING_ROLE_HOLDER_SIGNATURE_CHECKSUM = "android.app.extra.PROVISIONING_ROLE_HOLDER_SIGNATURE_CHECKSUM";
    public static final java.lang.String EXTRA_PROVISIONING_SENSORS_PERMISSION_GRANT_OPT_OUT = "android.app.extra.PROVISIONING_SENSORS_PERMISSION_GRANT_OPT_OUT";
    public static final java.lang.String EXTRA_PROVISIONING_SERIAL_NUMBER = "android.app.extra.PROVISIONING_SERIAL_NUMBER";
    public static final java.lang.String EXTRA_PROVISIONING_SHOULD_LAUNCH_RESULT_INTENT = "android.app.extra.PROVISIONING_SHOULD_LAUNCH_RESULT_INTENT";
    public static final java.lang.String EXTRA_PROVISIONING_SKIP_EDUCATION_SCREENS = "android.app.extra.PROVISIONING_SKIP_EDUCATION_SCREENS";
    public static final java.lang.String EXTRA_PROVISIONING_SKIP_ENCRYPTION = "android.app.extra.PROVISIONING_SKIP_ENCRYPTION";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROVISIONING_SKIP_OWNERSHIP_DISCLAIMER = "android.app.extra.PROVISIONING_SKIP_OWNERSHIP_DISCLAIMER";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PROVISIONING_SKIP_USER_CONSENT = "android.app.extra.PROVISIONING_SKIP_USER_CONSENT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROVISIONING_SUPPORTED_MODES = "android.app.extra.PROVISIONING_SUPPORTED_MODES";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROVISIONING_SUPPORT_URL = "android.app.extra.PROVISIONING_SUPPORT_URL";
    public static final java.lang.String EXTRA_PROVISIONING_TIME_ZONE = "android.app.extra.PROVISIONING_TIME_ZONE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PROVISIONING_TRIGGER = "android.app.extra.PROVISIONING_TRIGGER";
    public static final java.lang.String EXTRA_PROVISIONING_USE_MOBILE_DATA = "android.app.extra.PROVISIONING_USE_MOBILE_DATA";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_ANONYMOUS_IDENTITY = "android.app.extra.PROVISIONING_WIFI_ANONYMOUS_IDENTITY";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_CA_CERTIFICATE = "android.app.extra.PROVISIONING_WIFI_CA_CERTIFICATE";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_DOMAIN = "android.app.extra.PROVISIONING_WIFI_DOMAIN";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_EAP_METHOD = "android.app.extra.PROVISIONING_WIFI_EAP_METHOD";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_HIDDEN = "android.app.extra.PROVISIONING_WIFI_HIDDEN";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_IDENTITY = "android.app.extra.PROVISIONING_WIFI_IDENTITY";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_PAC_URL = "android.app.extra.PROVISIONING_WIFI_PAC_URL";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_PASSWORD = "android.app.extra.PROVISIONING_WIFI_PASSWORD";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_PHASE2_AUTH = "android.app.extra.PROVISIONING_WIFI_PHASE2_AUTH";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_PROXY_BYPASS = "android.app.extra.PROVISIONING_WIFI_PROXY_BYPASS";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_PROXY_HOST = "android.app.extra.PROVISIONING_WIFI_PROXY_HOST";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_PROXY_PORT = "android.app.extra.PROVISIONING_WIFI_PROXY_PORT";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_SECURITY_TYPE = "android.app.extra.PROVISIONING_WIFI_SECURITY_TYPE";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_SSID = "android.app.extra.PROVISIONING_WIFI_SSID";
    public static final java.lang.String EXTRA_PROVISIONING_WIFI_USER_CERTIFICATE = "android.app.extra.PROVISIONING_WIFI_USER_CERTIFICATE";
    public static final java.lang.String EXTRA_REMOTE_BUGREPORT_HASH = "android.intent.extra.REMOTE_BUGREPORT_HASH";
    public static final java.lang.String EXTRA_REMOTE_BUGREPORT_NONCE = "android.intent.extra.REMOTE_BUGREPORT_NONCE";
    public static final java.lang.String EXTRA_RESOURCE_IDS = "android.app.extra.RESOURCE_IDS";
    public static final java.lang.String EXTRA_RESOURCE_TYPE = "android.app.extra.RESOURCE_TYPE";
    public static final int EXTRA_RESOURCE_TYPE_DRAWABLE = 1;
    public static final int EXTRA_RESOURCE_TYPE_STRING = 2;

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_RESTRICTION = "android.app.extra.RESTRICTION";
    public static final java.lang.String EXTRA_RESULT_LAUNCH_INTENT = "android.app.extra.RESULT_LAUNCH_INTENT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ROLE_HOLDER_PROVISIONING_INITIATOR_PACKAGE = "android.app.extra.ROLE_HOLDER_PROVISIONING_INITIATOR_PACKAGE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ROLE_HOLDER_STATE = "android.app.extra.ROLE_HOLDER_STATE";
    public static final java.lang.String EXTRA_ROLE_HOLDER_UPDATE_FAILURE_STRATEGY = "android.app.extra.ROLE_HOLDER_UPDATE_FAILURE_STRATEGY";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ROLE_HOLDER_UPDATE_RESULT_CODE = "android.app.extra.ROLE_HOLDER_UPDATE_RESULT_CODE";
    public static final int FLAG_EVICT_CREDENTIAL_ENCRYPTION_KEY = 1;
    public static final int FLAG_MANAGED_CAN_ACCESS_PARENT = 2;
    public static final int FLAG_PARENT_CAN_ACCESS_MANAGED = 1;

    @android.annotation.SystemApi
    public static final int FLAG_SUPPORTED_MODES_DEVICE_OWNER = 4;

    @android.annotation.SystemApi
    public static final int FLAG_SUPPORTED_MODES_ORGANIZATION_OWNED = 1;

    @android.annotation.SystemApi
    public static final int FLAG_SUPPORTED_MODES_PERSONALLY_OWNED = 2;
    public static final int ID_TYPE_BASE_INFO = 1;
    public static final int ID_TYPE_IMEI = 4;
    public static final int ID_TYPE_INDIVIDUAL_ATTESTATION = 16;
    public static final int ID_TYPE_MEID = 8;
    public static final int ID_TYPE_SERIAL = 2;
    public static final int INSTALLKEY_REQUEST_CREDENTIALS_ACCESS = 1;
    public static final int INSTALLKEY_SET_USER_SELECTABLE = 2;
    public static final long IS_DEVICE_OWNER_USER_AWARE = 307233716;
    public static final int KEYGUARD_DISABLE_BIOMETRICS = 416;
    public static final int KEYGUARD_DISABLE_FACE = 128;
    public static final int KEYGUARD_DISABLE_FEATURES_ALL = Integer.MAX_VALUE;
    public static final int KEYGUARD_DISABLE_FEATURES_NONE = 0;
    public static final int KEYGUARD_DISABLE_FINGERPRINT = 32;
    public static final int KEYGUARD_DISABLE_IRIS = 256;

    @java.lang.Deprecated
    public static final int KEYGUARD_DISABLE_REMOTE_INPUT = 64;
    public static final int KEYGUARD_DISABLE_SECURE_CAMERA = 2;
    public static final int KEYGUARD_DISABLE_SECURE_NOTIFICATIONS = 4;
    public static final int KEYGUARD_DISABLE_SHORTCUTS_ALL = 512;
    public static final int KEYGUARD_DISABLE_TRUST_AGENTS = 16;
    public static final int KEYGUARD_DISABLE_UNREDACTED_NOTIFICATIONS = 8;
    public static final int KEYGUARD_DISABLE_WIDGETS_ALL = 1;
    public static final int KEY_GEN_STRONGBOX_UNAVAILABLE = 1;
    public static final int LEAVE_ALL_SYSTEM_APPS_ENABLED = 16;
    public static final int LOCK_TASK_FEATURE_BLOCK_ACTIVITY_START_IN_TASK = 64;
    public static final int LOCK_TASK_FEATURE_GLOBAL_ACTIONS = 16;
    public static final int LOCK_TASK_FEATURE_HOME = 4;
    public static final int LOCK_TASK_FEATURE_KEYGUARD = 32;
    public static final int LOCK_TASK_FEATURE_NONE = 0;
    public static final int LOCK_TASK_FEATURE_NOTIFICATIONS = 2;
    public static final int LOCK_TASK_FEATURE_OVERVIEW = 8;
    public static final int LOCK_TASK_FEATURE_SYSTEM_INFO = 1;
    public static final int MAKE_USER_DEMO = 4;
    public static final int MAKE_USER_EPHEMERAL = 2;
    public static final int MAX_PASSWORD_LENGTH = 64;
    public static final java.lang.String MIME_TYPE_PROVISIONING_NFC = "application/com.android.managedprovisioning";
    public static final int MTE_DISABLED = 2;
    public static final int MTE_ENABLED = 1;
    public static final int MTE_NOT_CONTROLLED_BY_POLICY = 0;
    public static final int NEARBY_STREAMING_DISABLED = 1;
    public static final int NEARBY_STREAMING_ENABLED = 2;
    public static final int NEARBY_STREAMING_NOT_CONTROLLED_BY_POLICY = 0;
    public static final int NEARBY_STREAMING_SAME_MANAGED_ACCOUNT_ONLY = 3;
    public static final int NON_ORG_OWNED_PROFILE_KEYGUARD_FEATURES_AFFECT_OWNER = 432;
    public static final int NOTIFICATION_BUGREPORT_ACCEPTED_NOT_FINISHED = 2;
    public static final int NOTIFICATION_BUGREPORT_FINISHED_NOT_ACCEPTED = 3;
    public static final int NOTIFICATION_BUGREPORT_STARTED = 1;
    public static final int OPERATION_CLEAR_APPLICATION_USER_DATA = 23;
    public static final int OPERATION_CREATE_AND_MANAGE_USER = 5;
    public static final int OPERATION_INSTALL_CA_CERT = 24;
    public static final int OPERATION_INSTALL_KEY_PAIR = 25;
    public static final int OPERATION_INSTALL_SYSTEM_UPDATE = 26;
    public static final int OPERATION_LOCK_NOW = 1;
    public static final int OPERATION_LOGOUT_USER = 9;
    public static final int OPERATION_REBOOT = 7;
    public static final int OPERATION_REMOVE_ACTIVE_ADMIN = 27;
    public static final int OPERATION_REMOVE_KEY_PAIR = 28;
    public static final int OPERATION_REMOVE_USER = 6;
    public static final int OPERATION_REQUEST_BUGREPORT = 29;
    public static final int OPERATION_SAFETY_REASON_DRIVING_DISTRACTION = 1;
    public static final int OPERATION_SAFETY_REASON_NONE = -1;
    public static final int OPERATION_SET_ALWAYS_ON_VPN_PACKAGE = 30;
    public static final int OPERATION_SET_APPLICATION_HIDDEN = 15;
    public static final int OPERATION_SET_APPLICATION_RESTRICTIONS = 16;
    public static final int OPERATION_SET_CAMERA_DISABLED = 31;
    public static final int OPERATION_SET_CONTENT_PROTECTION_POLICY = 41;
    public static final int OPERATION_SET_FACTORY_RESET_PROTECTION_POLICY = 32;
    public static final int OPERATION_SET_GLOBAL_PRIVATE_DNS = 33;
    public static final int OPERATION_SET_KEEP_UNINSTALLED_PACKAGES = 17;
    public static final int OPERATION_SET_KEYGUARD_DISABLED = 12;
    public static final int OPERATION_SET_LOCK_TASK_FEATURES = 18;
    public static final int OPERATION_SET_LOCK_TASK_PACKAGES = 19;
    public static final int OPERATION_SET_LOGOUT_ENABLED = 34;
    public static final int OPERATION_SET_MASTER_VOLUME_MUTED = 35;
    public static final int OPERATION_SET_OVERRIDE_APNS_ENABLED = 36;
    public static final int OPERATION_SET_PACKAGES_SUSPENDED = 20;
    public static final int OPERATION_SET_PERMISSION_GRANT_STATE = 37;
    public static final int OPERATION_SET_PERMISSION_POLICY = 38;
    public static final int OPERATION_SET_RESTRICTIONS_PROVIDER = 39;
    public static final int OPERATION_SET_STATUS_BAR_DISABLED = 13;
    public static final int OPERATION_SET_SYSTEM_SETTING = 11;
    public static final int OPERATION_SET_SYSTEM_UPDATE_POLICY = 14;
    public static final int OPERATION_SET_TRUST_AGENT_CONFIGURATION = 21;
    public static final int OPERATION_SET_USER_CONTROL_DISABLED_PACKAGES = 22;
    public static final int OPERATION_SET_USER_RESTRICTION = 10;
    public static final int OPERATION_START_USER_IN_BACKGROUND = 3;
    public static final int OPERATION_STOP_USER = 4;
    public static final int OPERATION_SWITCH_USER = 2;
    public static final int OPERATION_UNINSTALL_CA_CERT = 40;
    public static final int OPERATION_WIPE_DATA = 8;
    public static final int ORG_OWNED_PROFILE_KEYGUARD_FEATURES_PARENT_ONLY = 518;
    public static final int PASSWORD_COMPLEXITY_HIGH = 327680;
    public static final int PASSWORD_COMPLEXITY_LOW = 65536;
    public static final int PASSWORD_COMPLEXITY_MEDIUM = 196608;
    public static final int PASSWORD_COMPLEXITY_NONE = 0;
    public static final int PASSWORD_QUALITY_ALPHABETIC = 262144;
    public static final int PASSWORD_QUALITY_ALPHANUMERIC = 327680;
    public static final int PASSWORD_QUALITY_BIOMETRIC_WEAK = 32768;
    public static final int PASSWORD_QUALITY_COMPLEX = 393216;
    public static final int PASSWORD_QUALITY_MANAGED = 524288;
    public static final int PASSWORD_QUALITY_NUMERIC = 131072;
    public static final int PASSWORD_QUALITY_NUMERIC_COMPLEX = 196608;
    public static final int PASSWORD_QUALITY_SOMETHING = 65536;
    public static final int PASSWORD_QUALITY_UNSPECIFIED = 0;
    public static final int PERMISSION_GRANT_STATE_DEFAULT = 0;
    public static final int PERMISSION_GRANT_STATE_DENIED = 2;
    public static final int PERMISSION_GRANT_STATE_GRANTED = 1;
    public static final int PERMISSION_POLICY_AUTO_DENY = 2;
    public static final int PERMISSION_POLICY_AUTO_GRANT = 1;
    public static final int PERMISSION_POLICY_PROMPT = 0;
    public static final int PERSONAL_APPS_NOT_SUSPENDED = 0;
    public static final int PERSONAL_APPS_SUSPENDED_EXPLICITLY = 1;
    public static final int PERSONAL_APPS_SUSPENDED_PROFILE_TIMEOUT = 2;
    public static final java.lang.String POLICY_DISABLE_CAMERA = "policy_disable_camera";
    public static final java.lang.String POLICY_DISABLE_SCREEN_CAPTURE = "policy_disable_screen_capture";
    public static final java.lang.String POLICY_SUSPEND_PACKAGES = "policy_suspend_packages";
    public static final boolean PREFERENTIAL_NETWORK_SERVICE_ENABLED_DEFAULT = false;
    private static final java.lang.String PREFIX_OPERATION = "OPERATION_";
    private static final java.lang.String PREFIX_OPERATION_SAFETY_REASON = "OPERATION_SAFETY_REASON_";
    public static final int PRIVATE_DNS_MODE_OFF = 1;
    public static final int PRIVATE_DNS_MODE_OPPORTUNISTIC = 2;
    public static final int PRIVATE_DNS_MODE_PROVIDER_HOSTNAME = 3;
    public static final int PRIVATE_DNS_MODE_UNKNOWN = 0;
    public static final int PRIVATE_DNS_SET_ERROR_FAILURE_SETTING = 2;
    public static final int PRIVATE_DNS_SET_ERROR_HOST_NOT_SERVING = 1;
    public static final int PRIVATE_DNS_SET_NO_ERROR = 0;
    public static final int PROFILE_KEYGUARD_FEATURES_AFFECT_OWNER = 950;
    public static final int PROVISIONING_MODE_FULLY_MANAGED_DEVICE = 1;
    public static final int PROVISIONING_MODE_MANAGED_PROFILE = 2;
    public static final int PROVISIONING_MODE_MANAGED_PROFILE_ON_PERSONAL_DEVICE = 3;

    @android.annotation.SystemApi
    public static final int PROVISIONING_TRIGGER_CLOUD_ENROLLMENT = 1;

    @android.annotation.SystemApi
    public static final int PROVISIONING_TRIGGER_MANAGED_ACCOUNT = 4;

    @android.annotation.SystemApi
    public static final int PROVISIONING_TRIGGER_NFC = 5;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int PROVISIONING_TRIGGER_PERSISTENT_DEVICE_OWNER = 3;

    @android.annotation.SystemApi
    public static final int PROVISIONING_TRIGGER_QR_CODE = 2;

    @android.annotation.SystemApi
    public static final int PROVISIONING_TRIGGER_UNSPECIFIED = 0;

    @android.annotation.SystemApi
    public static final java.lang.String REQUIRED_APP_MANAGED_DEVICE = "android.app.REQUIRED_APP_MANAGED_DEVICE";

    @android.annotation.SystemApi
    public static final java.lang.String REQUIRED_APP_MANAGED_PROFILE = "android.app.REQUIRED_APP_MANAGED_PROFILE";

    @android.annotation.SystemApi
    public static final java.lang.String REQUIRED_APP_MANAGED_USER = "android.app.REQUIRED_APP_MANAGED_USER";
    public static final int RESET_PASSWORD_DO_NOT_ASK_CREDENTIALS_ON_BOOT = 2;
    public static final int RESET_PASSWORD_REQUIRE_ENTRY = 1;

    @android.annotation.SystemApi
    public static final int RESULT_DEVICE_OWNER_SET = 123;

    @android.annotation.SystemApi
    public static final int RESULT_UPDATE_DEVICE_POLICY_MANAGEMENT_ROLE_HOLDER_PROVISIONING_DISABLED = 3;

    @android.annotation.SystemApi
    public static final int RESULT_UPDATE_DEVICE_POLICY_MANAGEMENT_ROLE_HOLDER_RECOVERABLE_ERROR = 1;

    @android.annotation.SystemApi
    public static final int RESULT_UPDATE_DEVICE_POLICY_MANAGEMENT_ROLE_HOLDER_UNRECOVERABLE_ERROR = 2;

    @android.annotation.SystemApi
    public static final int RESULT_UPDATE_ROLE_HOLDER = 2;

    @android.annotation.SystemApi
    public static final int RESULT_WORK_PROFILE_CREATED = 122;
    public static final int ROLE_HOLDER_UPDATE_FAILURE_STRATEGY_FAIL_PROVISIONING = 1;
    public static final int ROLE_HOLDER_UPDATE_FAILURE_STRATEGY_FALLBACK_TO_PLATFORM_PROVISIONING = 2;
    public static final int SKIP_SETUP_WIZARD = 1;

    @android.annotation.SystemApi
    public static final int STATE_USER_PROFILE_COMPLETE = 4;

    @android.annotation.SystemApi
    public static final int STATE_USER_PROFILE_FINALIZED = 5;

    @android.annotation.SystemApi
    public static final int STATE_USER_SETUP_COMPLETE = 2;

    @android.annotation.SystemApi
    public static final int STATE_USER_SETUP_FINALIZED = 3;

    @android.annotation.SystemApi
    public static final int STATE_USER_SETUP_INCOMPLETE = 1;

    @android.annotation.SystemApi
    public static final int STATE_USER_UNMANAGED = 0;

    @android.annotation.SystemApi
    public static final int STATUS_ACCOUNTS_NOT_EMPTY = 6;

    @android.annotation.SystemApi
    public static final int STATUS_CANNOT_ADD_MANAGED_PROFILE = 11;

    @android.annotation.SystemApi
    public static final int STATUS_DEVICE_ADMIN_NOT_SUPPORTED = 13;

    @android.annotation.SystemApi
    public static final int STATUS_HAS_DEVICE_OWNER = 1;

    @android.annotation.SystemApi
    public static final int STATUS_HAS_PAIRED = 8;

    @android.annotation.SystemApi
    public static final int STATUS_HEADLESS_ONLY_SYSTEM_USER = 17;

    @android.annotation.SystemApi
    public static final int STATUS_HEADLESS_SYSTEM_USER_MODE_NOT_SUPPORTED = 16;

    @android.annotation.SystemApi
    public static final int STATUS_MANAGED_USERS_NOT_SUPPORTED = 9;

    @android.annotation.SystemApi
    public static final int STATUS_NONSYSTEM_USER_EXISTS = 5;

    @android.annotation.SystemApi
    public static final int STATUS_NOT_SYSTEM_USER = 7;

    @android.annotation.SystemApi
    public static final int STATUS_OK = 0;

    @android.annotation.SystemApi
    public static final int STATUS_PROVISIONING_NOT_ALLOWED_FOR_NON_DEVELOPER_USERS = 15;

    @java.lang.Deprecated
    public static final int STATUS_SPLIT_SYSTEM_USER_DEVICE_SYSTEM_USER = 14;

    @android.annotation.SystemApi
    public static final int STATUS_SYSTEM_USER = 10;

    @android.annotation.SystemApi
    public static final int STATUS_UNKNOWN_ERROR = -1;

    @android.annotation.SystemApi
    public static final int STATUS_USER_HAS_PROFILE_OWNER = 2;

    @android.annotation.SystemApi
    public static final int STATUS_USER_NOT_RUNNING = 3;

    @android.annotation.SystemApi
    public static final int STATUS_USER_SETUP_COMPLETED = 4;
    public static final int WIFI_SECURITY_ENTERPRISE_192 = 3;
    public static final int WIFI_SECURITY_ENTERPRISE_EAP = 2;
    public static final int WIFI_SECURITY_OPEN = 0;
    public static final int WIFI_SECURITY_PERSONAL = 1;
    public static final int WIPE_EUICC = 4;
    public static final int WIPE_EXTERNAL_STORAGE = 1;
    public static final int WIPE_RESET_PROTECTION_DATA = 2;
    public static final int WIPE_SILENTLY = 8;
    private final android.content.Context mContext;
    private final android.os.IpcDataCache<java.lang.Void, java.lang.CharSequence> mGetDeviceOwnerOrganizationNameCache;
    private android.os.IpcDataCache<android.util.Pair<android.content.ComponentName, java.lang.Integer>, java.lang.Integer> mGetKeyGuardDisabledFeaturesCache;
    private final android.os.IpcDataCache<java.lang.Integer, java.lang.CharSequence> mGetOrganizationNameForUserCache;
    private final android.os.IpcDataCache<android.os.UserHandle, android.content.ComponentName> mGetProfileOwnerOrDeviceOwnerSupervisionComponentCache;
    private android.os.IpcDataCache<java.lang.Void, java.lang.Boolean> mHasDeviceOwnerCache;
    private android.os.IpcDataCache<android.content.ComponentName, java.lang.Boolean> mIsNetworkLoggingEnabledCache;
    private final android.os.IpcDataCache<java.lang.Void, java.lang.Boolean> mIsOrganizationOwnedDeviceWithManagedProfileCache;
    private final boolean mParentInstance;
    private final android.app.admin.DevicePolicyResourcesManager mResourcesManager;
    private final android.app.admin.IDevicePolicyManager mService;
    private static java.lang.String TAG = "DevicePolicyManager";
    private static final android.os.IpcDataCache.Config sDpmCaches = new android.os.IpcDataCache.Config(8, "system_server", "DevicePolicyManagerCaches");

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ApplicationExemptionConstants {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttestationIdType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ContentProtectionPolicy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CreateAndManageUserFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceOwnerType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DevicePolicyOperation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InstallUpdateCallbackErrorConstants {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LockNowFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LockTaskFeature {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MtePolicy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NearbyStreamingPolicy {
    }

    public interface OnClearApplicationUserDataListener {
        void onApplicationUserDataCleared(java.lang.String str, boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OperationSafetyReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PasswordComplexity {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PermissionGrantState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PersonalAppsSuspensionReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PrivateDnsMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PrivateDnsModeErrorCodes {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProvisioningConfiguration {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProvisioningPrecondition {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProvisioningTrigger {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RoleHolderUpdateFailureStrategy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SystemSettingsWhitelist {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserProvisioningState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WifiSecurity {
    }

    public DevicePolicyManager(android.content.Context context, android.app.admin.IDevicePolicyManager iDevicePolicyManager) {
        this(context, iDevicePolicyManager, false);
    }

    protected DevicePolicyManager(android.content.Context context, android.app.admin.IDevicePolicyManager iDevicePolicyManager, boolean z) {
        this.mGetKeyGuardDisabledFeaturesCache = new android.os.IpcDataCache<>(sDpmCaches.child("getKeyguardDisabledFeatures"), new android.os.IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda2
            @Override // android.os.IpcDataCache.RemoteCall
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$new$2;
                lambda$new$2 = android.app.admin.DevicePolicyManager.this.lambda$new$2((android.util.Pair) obj);
                return lambda$new$2;
            }
        });
        this.mHasDeviceOwnerCache = new android.os.IpcDataCache<>(sDpmCaches.child("hasDeviceOwner"), new android.os.IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda3
            @Override // android.os.IpcDataCache.RemoteCall
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$new$3;
                lambda$new$3 = android.app.admin.DevicePolicyManager.this.lambda$new$3((java.lang.Void) obj);
                return lambda$new$3;
            }
        });
        this.mGetProfileOwnerOrDeviceOwnerSupervisionComponentCache = new android.os.IpcDataCache<>(sDpmCaches.child("getProfileOwnerOrDeviceOwnerSupervisionComponent"), new android.os.IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda4
            @Override // android.os.IpcDataCache.RemoteCall
            public final java.lang.Object apply(java.lang.Object obj) {
                android.content.ComponentName lambda$new$4;
                lambda$new$4 = android.app.admin.DevicePolicyManager.this.lambda$new$4((android.os.UserHandle) obj);
                return lambda$new$4;
            }
        });
        this.mIsOrganizationOwnedDeviceWithManagedProfileCache = new android.os.IpcDataCache<>(sDpmCaches.child("isOrganizationOwnedDeviceWithManagedProfile"), new android.os.IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda5
            @Override // android.os.IpcDataCache.RemoteCall
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Object lambda$new$5;
                lambda$new$5 = android.app.admin.DevicePolicyManager.this.lambda$new$5(obj);
                return lambda$new$5;
            }
        });
        this.mGetDeviceOwnerOrganizationNameCache = new android.os.IpcDataCache<>(sDpmCaches.child("getDeviceOwnerOrganizationName"), new android.os.IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda6
            @Override // android.os.IpcDataCache.RemoteCall
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Object lambda$new$8;
                lambda$new$8 = android.app.admin.DevicePolicyManager.this.lambda$new$8(obj);
                return lambda$new$8;
            }
        });
        this.mGetOrganizationNameForUserCache = new android.os.IpcDataCache<>(sDpmCaches.child("getOrganizationNameForUser"), new android.os.IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda7
            @Override // android.os.IpcDataCache.RemoteCall
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.CharSequence lambda$new$9;
                lambda$new$9 = android.app.admin.DevicePolicyManager.this.lambda$new$9((java.lang.Integer) obj);
                return lambda$new$9;
            }
        });
        this.mIsNetworkLoggingEnabledCache = new android.os.IpcDataCache<>(sDpmCaches.child("isNetworkLoggingEnabled"), new android.os.IpcDataCache.RemoteCall() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda8
            @Override // android.os.IpcDataCache.RemoteCall
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$new$10;
                lambda$new$10 = android.app.admin.DevicePolicyManager.this.lambda$new$10((android.content.ComponentName) obj);
                return lambda$new$10;
            }
        });
        this.mContext = context;
        this.mService = iDevicePolicyManager;
        this.mParentInstance = z;
        this.mResourcesManager = new android.app.admin.DevicePolicyResourcesManager(context, iDevicePolicyManager);
    }

    private final android.app.admin.IDevicePolicyManager getService() {
        return this.mService;
    }

    private final boolean isParentInstance() {
        return this.mParentInstance;
    }

    private final android.content.Context getContext() {
        return this.mContext;
    }

    protected int myUserId() {
        return this.mContext.getUserId();
    }

    public static abstract class InstallSystemUpdateCallback {
        public static final int UPDATE_ERROR_BATTERY_LOW = 5;
        public static final int UPDATE_ERROR_FILE_NOT_FOUND = 4;
        public static final int UPDATE_ERROR_INCORRECT_OS_VERSION = 2;
        public static final int UPDATE_ERROR_UNKNOWN = 1;
        public static final int UPDATE_ERROR_UPDATE_FILE_INVALID = 3;

        public void onInstallUpdateError(int i, java.lang.String str) {
        }
    }

    public static java.lang.String operationToString(int i) {
        return android.util.DebugUtils.constantToString(android.app.admin.DevicePolicyManager.class, PREFIX_OPERATION, i);
    }

    public void setMtePolicy(int i) {
        throwIfParentInstance("setMtePolicy");
        if (this.mService != null) {
            try {
                this.mService.setMtePolicy(i, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getMtePolicy() {
        throwIfParentInstance("setMtePolicy");
        if (this.mService != null) {
            try {
                return this.mService.getMtePolicy(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public static boolean isMtePolicyEnforced() {
        return com.android.internal.os.Zygote.nativeSupportsMemoryTagging();
    }

    public void setContentProtectionPolicy(android.content.ComponentName componentName, int i) {
        throwIfParentInstance("setContentProtectionPolicy");
        if (this.mService != null) {
            try {
                this.mService.setContentProtectionPolicy(componentName, this.mContext.getPackageName(), i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getContentProtectionPolicy(android.content.ComponentName componentName) {
        throwIfParentInstance("getContentProtectionPolicy");
        if (this.mService != null) {
            try {
                return this.mService.getContentProtectionPolicy(componentName, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 1;
    }

    public static void invalidateBinderCaches() {
        sDpmCaches.invalidateCache();
    }

    public static void disableLocalCaches() {
        sDpmCaches.disableAllForCurrentProcess();
    }

    public static java.lang.String operationSafetyReasonToString(int i) {
        return android.util.DebugUtils.constantToString(android.app.admin.DevicePolicyManager.class, PREFIX_OPERATION_SAFETY_REASON, i);
    }

    public static boolean isValidOperationSafetyReason(int i) {
        return i == 1;
    }

    public boolean isSafeOperation(int i) {
        throwIfParentInstance("isSafeOperation");
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.isSafeOperation(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void acknowledgeNewUserDisclaimer() {
        if (this.mService != null) {
            try {
                this.mService.acknowledgeNewUserDisclaimer(this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isNewUserDisclaimerAcknowledged() {
        if (this.mService != null) {
            try {
                return this.mService.isNewUserDisclaimerAcknowledged(this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isAdminActive(android.content.ComponentName componentName) {
        throwIfParentInstance("isAdminActive");
        return isAdminActiveAsUser(componentName, myUserId());
    }

    public boolean isAdminActiveAsUser(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.isAdminActive(componentName, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isRemovingAdmin(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.isRemovingAdmin(componentName, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.util.List<android.content.ComponentName> getActiveAdmins() {
        throwIfParentInstance("getActiveAdmins");
        return getActiveAdminsAsUser(myUserId());
    }

    public java.util.List<android.content.ComponentName> getActiveAdminsAsUser(int i) {
        if (this.mService != null) {
            try {
                return this.mService.getActiveAdmins(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public boolean packageHasActiveAdmins(java.lang.String str) {
        return packageHasActiveAdmins(str, myUserId());
    }

    public boolean packageHasActiveAdmins(java.lang.String str, int i) {
        if (this.mService != null) {
            try {
                return this.mService.packageHasActiveAdmins(str, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void removeActiveAdmin(android.content.ComponentName componentName) {
        throwIfParentInstance("removeActiveAdmin");
        if (this.mService != null) {
            try {
                this.mService.removeActiveAdmin(componentName, myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean hasGrantedPolicy(android.content.ComponentName componentName, int i) {
        throwIfParentInstance("hasGrantedPolicy");
        if (this.mService != null) {
            try {
                return this.mService.hasGrantedPolicy(componentName, i, myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @java.lang.Deprecated
    public void setPasswordQuality(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                this.mService.setPasswordQuality(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public int getPasswordQuality(android.content.ComponentName componentName) {
        return getPasswordQuality(componentName, myUserId());
    }

    public int getPasswordQuality(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordQuality(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    @java.lang.Deprecated
    public void setPasswordMinimumLength(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                this.mService.setPasswordMinimumLength(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public int getPasswordMinimumLength(android.content.ComponentName componentName) {
        return getPasswordMinimumLength(componentName, myUserId());
    }

    public int getPasswordMinimumLength(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordMinimumLength(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    @java.lang.Deprecated
    public void setPasswordMinimumUpperCase(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                this.mService.setPasswordMinimumUpperCase(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public int getPasswordMinimumUpperCase(android.content.ComponentName componentName) {
        return getPasswordMinimumUpperCase(componentName, myUserId());
    }

    public int getPasswordMinimumUpperCase(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordMinimumUpperCase(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    @java.lang.Deprecated
    public void setPasswordMinimumLowerCase(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                this.mService.setPasswordMinimumLowerCase(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public int getPasswordMinimumLowerCase(android.content.ComponentName componentName) {
        return getPasswordMinimumLowerCase(componentName, myUserId());
    }

    public int getPasswordMinimumLowerCase(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordMinimumLowerCase(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    @java.lang.Deprecated
    public void setPasswordMinimumLetters(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                this.mService.setPasswordMinimumLetters(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public int getPasswordMinimumLetters(android.content.ComponentName componentName) {
        return getPasswordMinimumLetters(componentName, myUserId());
    }

    public int getPasswordMinimumLetters(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordMinimumLetters(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    @java.lang.Deprecated
    public void setPasswordMinimumNumeric(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                this.mService.setPasswordMinimumNumeric(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public int getPasswordMinimumNumeric(android.content.ComponentName componentName) {
        return getPasswordMinimumNumeric(componentName, myUserId());
    }

    public int getPasswordMinimumNumeric(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordMinimumNumeric(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    @java.lang.Deprecated
    public void setPasswordMinimumSymbols(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                this.mService.setPasswordMinimumSymbols(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public int getPasswordMinimumSymbols(android.content.ComponentName componentName) {
        return getPasswordMinimumSymbols(componentName, myUserId());
    }

    public int getPasswordMinimumSymbols(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordMinimumSymbols(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    @java.lang.Deprecated
    public void setPasswordMinimumNonLetter(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                this.mService.setPasswordMinimumNonLetter(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public int getPasswordMinimumNonLetter(android.content.ComponentName componentName) {
        return getPasswordMinimumNonLetter(componentName, myUserId());
    }

    public int getPasswordMinimumNonLetter(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordMinimumNonLetter(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public android.app.admin.PasswordMetrics getPasswordMinimumMetrics(int i) {
        return getPasswordMinimumMetrics(i, false);
    }

    public android.app.admin.PasswordMetrics getPasswordMinimumMetrics(int i, boolean z) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordMinimumMetrics(i, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public void setPasswordHistoryLength(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                this.mService.setPasswordHistoryLength(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setPasswordExpirationTimeout(android.content.ComponentName componentName, long j) {
        if (this.mService != null) {
            try {
                this.mService.setPasswordExpirationTimeout(componentName, this.mContext.getPackageName(), j, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long getPasswordExpirationTimeout(android.content.ComponentName componentName) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordExpirationTimeout(componentName, myUserId(), this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0L;
    }

    public long getPasswordExpiration(android.content.ComponentName componentName) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordExpiration(componentName, myUserId(), this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0L;
    }

    public int getPasswordHistoryLength(android.content.ComponentName componentName) {
        return getPasswordHistoryLength(componentName, myUserId());
    }

    public int getPasswordHistoryLength(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getPasswordHistoryLength(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public int getPasswordMaximumLength(int i) {
        if (!this.mContext.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_SECURE_LOCK_SCREEN)) {
            return 0;
        }
        return 64;
    }

    public boolean isActivePasswordSufficient() {
        if (this.mService != null) {
            try {
                return this.mService.isActivePasswordSufficient(this.mContext.getPackageName(), myUserId(), this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isActivePasswordSufficientForDeviceRequirement() {
        if (!this.mParentInstance) {
            throw new java.lang.SecurityException("only callable on the parent instance");
        }
        if (this.mService != null) {
            try {
                return this.mService.isActivePasswordSufficientForDeviceRequirement();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int getPasswordComplexity() {
        if (this.mService == null) {
            return 0;
        }
        try {
            return this.mService.getPasswordComplexity(this.mParentInstance);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRequiredPasswordComplexity(int i) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.setRequiredPasswordComplexity(this.mContext.getPackageName(), i, this.mParentInstance);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRequiredPasswordComplexity() {
        if (this.mService == null) {
            return 0;
        }
        try {
            return this.mService.getRequiredPasswordComplexity(this.mContext.getPackageName(), this.mParentInstance);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAggregatedPasswordComplexityForUser(int i) {
        return getAggregatedPasswordComplexityForUser(i, false);
    }

    public int getAggregatedPasswordComplexityForUser(int i, boolean z) {
        if (this.mService == null) {
            return 0;
        }
        try {
            return this.mService.getAggregatedPasswordComplexityForUser(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUsingUnifiedPassword(android.content.ComponentName componentName) {
        throwIfParentInstance("isUsingUnifiedPassword");
        if (this.mService != null) {
            try {
                return this.mService.isUsingUnifiedPassword(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public boolean isPasswordSufficientAfterProfileUnification(int i, int i2) {
        if (this.mService != null) {
            try {
                return this.mService.isPasswordSufficientAfterProfileUnification(i, i2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int getCurrentFailedPasswordAttempts() {
        return getCurrentFailedPasswordAttempts(myUserId());
    }

    public int getCurrentFailedPasswordAttempts(int i) {
        if (this.mService != null) {
            try {
                return this.mService.getCurrentFailedPasswordAttempts(this.mContext.getPackageName(), i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public boolean getDoNotAskCredentialsOnBoot() {
        if (this.mService != null) {
            try {
                return this.mService.getDoNotAskCredentialsOnBoot();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setMaximumFailedPasswordsForWipe(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                this.mService.setMaximumFailedPasswordsForWipe(componentName, this.mContext.getPackageName(), i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getMaximumFailedPasswordsForWipe(android.content.ComponentName componentName) {
        return getMaximumFailedPasswordsForWipe(componentName, myUserId());
    }

    public int getMaximumFailedPasswordsForWipe(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getMaximumFailedPasswordsForWipe(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public int getProfileWithMinimumFailedPasswordsForWipe(int i) {
        if (this.mService != null) {
            try {
                return this.mService.getProfileWithMinimumFailedPasswordsForWipe(i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -10000;
    }

    @java.lang.Deprecated
    public boolean resetPassword(java.lang.String str, int i) {
        throwIfParentInstance("resetPassword");
        if (this.mService != null) {
            try {
                return this.mService.resetPassword(str, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean setResetPasswordToken(android.content.ComponentName componentName, byte[] bArr) {
        throwIfParentInstance("setResetPasswordToken");
        if (this.mService != null) {
            try {
                return this.mService.setResetPasswordToken(componentName, this.mContext.getPackageName(), bArr);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean clearResetPasswordToken(android.content.ComponentName componentName) {
        throwIfParentInstance("clearResetPasswordToken");
        if (this.mService != null) {
            try {
                return this.mService.clearResetPasswordToken(componentName, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isResetPasswordTokenActive(android.content.ComponentName componentName) {
        throwIfParentInstance("isResetPasswordTokenActive");
        if (this.mService != null) {
            try {
                return this.mService.isResetPasswordTokenActive(componentName, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean resetPasswordWithToken(android.content.ComponentName componentName, java.lang.String str, byte[] bArr, int i) {
        throwIfParentInstance("resetPassword");
        if (this.mService != null) {
            try {
                return this.mService.resetPasswordWithToken(componentName, this.mContext.getPackageName(), str, bArr, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setMaximumTimeToLock(android.content.ComponentName componentName, long j) {
        if (this.mService != null) {
            try {
                this.mService.setMaximumTimeToLock(componentName, this.mContext.getPackageName(), j, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long getMaximumTimeToLock(android.content.ComponentName componentName) {
        return getMaximumTimeToLock(componentName, myUserId());
    }

    public long getMaximumTimeToLock(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getMaximumTimeToLock(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0L;
    }

    public void setRequiredStrongAuthTimeout(android.content.ComponentName componentName, long j) {
        if (this.mService != null) {
            try {
                this.mService.setRequiredStrongAuthTimeout(componentName, this.mContext.getPackageName(), j, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long getRequiredStrongAuthTimeout(android.content.ComponentName componentName) {
        return getRequiredStrongAuthTimeout(componentName, myUserId());
    }

    public long getRequiredStrongAuthTimeout(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getRequiredStrongAuthTimeout(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return DEFAULT_STRONG_AUTH_TIMEOUT_MS;
    }

    public void lockNow() {
        lockNow(0);
    }

    public void lockNow(int i) {
        if (this.mService != null) {
            try {
                this.mService.lockNow(i, this.mContext.getPackageName(), this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void wipeData(int i) {
        wipeDataInternal(i, "", false);
    }

    public void wipeData(int i, java.lang.CharSequence charSequence) {
        java.util.Objects.requireNonNull(charSequence, "reason string is null");
        com.android.internal.util.Preconditions.checkStringNotEmpty(charSequence, "reason string is empty");
        com.android.internal.util.Preconditions.checkArgument((i & 8) == 0, "WIPE_SILENTLY cannot be set");
        wipeDataInternal(i, charSequence.toString(), false);
    }

    public void wipeDevice(int i) {
        wipeDataInternal(i, "", true);
    }

    private void wipeDataInternal(int i, java.lang.String str, boolean z) {
        if (this.mService != null) {
            try {
                this.mService.wipeDataWithReason(this.mContext.getPackageName(), i, str, this.mParentInstance, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setFactoryResetProtectionPolicy(android.content.ComponentName componentName, android.app.admin.FactoryResetProtectionPolicy factoryResetProtectionPolicy) {
        throwIfParentInstance("setFactoryResetProtectionPolicy");
        if (this.mService != null) {
            try {
                this.mService.setFactoryResetProtectionPolicy(componentName, this.mContext.getPackageName(), factoryResetProtectionPolicy);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.app.admin.FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(android.content.ComponentName componentName) {
        throwIfParentInstance("getFactoryResetProtectionPolicy");
        if (this.mService != null) {
            try {
                return this.mService.getFactoryResetProtectionPolicy(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public void sendLostModeLocationUpdate(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        throwIfParentInstance("sendLostModeLocationUpdate");
        if (this.mService == null) {
            executeCallback(com.android.internal.infra.AndroidFuture.completedFuture(false), executor, consumer);
            return;
        }
        try {
            com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture = new com.android.internal.infra.AndroidFuture<>();
            this.mService.sendLostModeLocationUpdate(androidFuture);
            executeCallback(androidFuture, executor, consumer);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void executeCallback(com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture, final java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        androidFuture.whenComplete(new java.util.function.BiConsumer() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                executor.execute(new java.lang.Runnable() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.admin.DevicePolicyManager.lambda$executeCallback$0(r1, r2, r3);
                    }
                });
            }
        });
    }

    static /* synthetic */ void lambda$executeCallback$0(java.lang.Throwable th, java.util.function.Consumer consumer, java.lang.Boolean bool) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (th != null) {
                consumer.accept(false);
            } else {
                consumer.accept(bool);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.content.ComponentName setGlobalProxy(android.content.ComponentName componentName, java.net.Proxy proxy, java.util.List<java.lang.String> list) {
        java.lang.String str;
        throwIfParentInstance("setGlobalProxy");
        if (proxy == null) {
            throw new java.lang.NullPointerException();
        }
        java.lang.String str2 = null;
        if (this.mService == null) {
            return null;
        }
        try {
            if (proxy.equals(java.net.Proxy.NO_PROXY)) {
                str = null;
            } else {
                if (!proxy.type().equals(java.net.Proxy.Type.HTTP)) {
                    throw new java.lang.IllegalArgumentException();
                }
                android.util.Pair<java.lang.String, java.lang.String> proxyParameters = getProxyParameters(proxy, list);
                str2 = proxyParameters.first;
                str = proxyParameters.second;
            }
            return this.mService.setGlobalProxy(componentName, str2, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.util.Pair<java.lang.String, java.lang.String> getProxyParameters(java.net.Proxy proxy, java.util.List<java.lang.String> list) {
        java.util.List list2;
        java.net.InetSocketAddress inetSocketAddress = (java.net.InetSocketAddress) proxy.address();
        java.lang.String hostName = inetSocketAddress.getHostName();
        int port = inetSocketAddress.getPort();
        if (list == null) {
            list2 = java.util.Collections.emptyList();
        } else {
            java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
            java.util.Iterator<java.lang.String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().trim());
            }
            list2 = arrayList;
        }
        android.net.ProxyInfo buildDirectProxy = android.net.ProxyInfo.buildDirectProxy(hostName, port, list2);
        if (port == 0 || android.text.TextUtils.isEmpty(hostName) || !buildDirectProxy.isValid()) {
            throw new java.lang.IllegalArgumentException();
        }
        return new android.util.Pair<>(hostName + ":" + port, android.text.TextUtils.join(",", list2));
    }

    public void setRecommendedGlobalProxy(android.content.ComponentName componentName, android.net.ProxyInfo proxyInfo) {
        throwIfParentInstance("setRecommendedGlobalProxy");
        if (this.mService != null) {
            try {
                this.mService.setRecommendedGlobalProxy(componentName, proxyInfo);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.content.ComponentName getGlobalProxyAdmin() {
        if (this.mService != null) {
            try {
                return this.mService.getGlobalProxyAdmin(myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @java.lang.Deprecated
    public int setStorageEncryption(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setStorageEncryption");
        if (this.mService != null) {
            try {
                return this.mService.setStorageEncryption(componentName, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    @java.lang.Deprecated
    public boolean getStorageEncryption(android.content.ComponentName componentName) {
        throwIfParentInstance("getStorageEncryption");
        if (this.mService != null) {
            try {
                return this.mService.getStorageEncryption(componentName, myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int getStorageEncryptionStatus() {
        throwIfParentInstance("getStorageEncryptionStatus");
        return getStorageEncryptionStatus(myUserId());
    }

    public int getStorageEncryptionStatus(int i) {
        if (this.mService != null) {
            try {
                return this.mService.getStorageEncryptionStatus(this.mContext.getPackageName(), i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public boolean approveCaCert(java.lang.String str, int i, boolean z) {
        if (this.mService != null) {
            try {
                return this.mService.approveCaCert(str, i, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isCaCertApproved(java.lang.String str, int i) {
        if (this.mService != null) {
            try {
                return this.mService.isCaCertApproved(str, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean installCaCert(android.content.ComponentName componentName, byte[] bArr) {
        throwIfParentInstance("installCaCert");
        if (this.mService != null) {
            try {
                return this.mService.installCaCert(componentName, this.mContext.getPackageName(), bArr);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void uninstallCaCert(android.content.ComponentName componentName, byte[] bArr) {
        throwIfParentInstance("uninstallCaCert");
        if (this.mService != null) {
            try {
                this.mService.uninstallCaCerts(componentName, this.mContext.getPackageName(), new java.lang.String[]{getCaCertAlias(bArr)});
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.security.cert.CertificateException e2) {
                android.util.Log.w(TAG, "Unable to parse certificate", e2);
            }
        }
    }

    public java.util.List<byte[]> getInstalledCaCerts(android.content.ComponentName componentName) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        throwIfParentInstance("getInstalledCaCerts");
        if (this.mService != null) {
            try {
                this.mService.enforceCanManageCaCerts(componentName, this.mContext.getPackageName());
                com.android.org.conscrypt.TrustedCertificateStore trustedCertificateStore = new com.android.org.conscrypt.TrustedCertificateStore();
                for (java.lang.String str : trustedCertificateStore.userAliases()) {
                    try {
                        arrayList.add(trustedCertificateStore.getCertificate(str).getEncoded());
                    } catch (java.security.cert.CertificateException e) {
                        android.util.Log.w(TAG, "Could not encode certificate: " + str, e);
                    }
                }
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
        return arrayList;
    }

    public void uninstallAllUserCaCerts(android.content.ComponentName componentName) {
        throwIfParentInstance("uninstallAllUserCaCerts");
        if (this.mService != null) {
            try {
                this.mService.uninstallCaCerts(componentName, this.mContext.getPackageName(), (java.lang.String[]) new com.android.org.conscrypt.TrustedCertificateStore().userAliases().toArray(new java.lang.String[0]));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean hasCaCertInstalled(android.content.ComponentName componentName, byte[] bArr) {
        throwIfParentInstance("hasCaCertInstalled");
        if (this.mService != null) {
            try {
                this.mService.enforceCanManageCaCerts(componentName, this.mContext.getPackageName());
                return getCaCertAlias(bArr) != null;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.security.cert.CertificateException e2) {
                android.util.Log.w(TAG, "Could not parse certificate", e2);
            }
        }
        return false;
    }

    public boolean installKeyPair(android.content.ComponentName componentName, java.security.PrivateKey privateKey, java.security.cert.Certificate certificate, java.lang.String str) {
        return installKeyPair(componentName, privateKey, new java.security.cert.Certificate[]{certificate}, str, false);
    }

    public boolean installKeyPair(android.content.ComponentName componentName, java.security.PrivateKey privateKey, java.security.cert.Certificate[] certificateArr, java.lang.String str, boolean z) {
        int i;
        if (!z) {
            i = 2;
        } else {
            i = 3;
        }
        return installKeyPair(componentName, privateKey, certificateArr, str, i);
    }

    public boolean installKeyPair(android.content.ComponentName componentName, java.security.PrivateKey privateKey, java.security.cert.Certificate[] certificateArr, java.lang.String str, int i) {
        byte[] bArr;
        throwIfParentInstance("installKeyPair");
        boolean z = (i & 1) == 1;
        boolean z2 = (i & 2) == 2;
        try {
            byte[] convertToPem = android.security.Credentials.convertToPem(certificateArr[0]);
            if (certificateArr.length <= 1) {
                bArr = null;
            } else {
                bArr = android.security.Credentials.convertToPem((java.security.cert.Certificate[]) java.util.Arrays.copyOfRange(certificateArr, 1, certificateArr.length));
            }
            return this.mService.installKeyPair(componentName, this.mContext.getPackageName(), ((java.security.spec.PKCS8EncodedKeySpec) java.security.KeyFactory.getInstance(privateKey.getAlgorithm()).getKeySpec(privateKey, java.security.spec.PKCS8EncodedKeySpec.class)).getEncoded(), convertToPem, bArr, str, z, z2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.io.IOException | java.security.cert.CertificateException e2) {
            android.util.Log.w(TAG, "Could not pem-encode certificate", e2);
            return false;
        } catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException e3) {
            android.util.Log.w(TAG, "Failed to obtain private key material", e3);
            return false;
        }
    }

    public boolean removeKeyPair(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("removeKeyPair");
        try {
            return this.mService.removeKeyPair(componentName, this.mContext.getPackageName(), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasKeyPair(java.lang.String str) {
        throwIfParentInstance("hasKeyPair");
        try {
            return this.mService.hasKeyPair(this.mContext.getPackageName(), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.security.AttestedKeyPair generateKeyPair(android.content.ComponentName componentName, java.lang.String str, android.security.keystore.KeyGenParameterSpec keyGenParameterSpec, int i) {
        java.security.cert.X509Certificate[] x509CertificateArr;
        throwIfParentInstance("generateKeyPair");
        try {
            android.security.keystore.ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec = new android.security.keystore.ParcelableKeyGenParameterSpec(keyGenParameterSpec);
            android.security.keymaster.KeymasterCertificateChain keymasterCertificateChain = new android.security.keymaster.KeymasterCertificateChain();
            if (!this.mService.generateKeyPair(componentName, this.mContext.getPackageName(), str, parcelableKeyGenParameterSpec, i, keymasterCertificateChain)) {
                android.util.Log.e(TAG, "Error generating key via DevicePolicyManagerService.");
                return null;
            }
            java.lang.String keystoreAlias = keyGenParameterSpec.getKeystoreAlias();
            java.security.KeyPair keyPair = android.security.KeyChain.getKeyPair(this.mContext, keystoreAlias);
            try {
                if (!android.security.keystore.AttestationUtils.isChainValid(keymasterCertificateChain)) {
                    x509CertificateArr = null;
                } else {
                    x509CertificateArr = android.security.keystore.AttestationUtils.parseCertificateChain(keymasterCertificateChain);
                }
                return new android.security.AttestedKeyPair(keyPair, x509CertificateArr);
            } catch (android.security.keystore.KeyAttestationException e) {
                android.util.Log.e(TAG, "Error parsing attestation chain for alias " + keystoreAlias, e);
                this.mService.removeKeyPair(componentName, this.mContext.getPackageName(), keystoreAlias);
                return null;
            }
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e3) {
            android.util.Log.w(TAG, java.lang.String.format("Key Generation failure: %d", java.lang.Integer.valueOf(e3.errorCode)));
            switch (e3.errorCode) {
                case 1:
                    throw new android.security.keystore.StrongBoxUnavailableException("No StrongBox for key generation.");
                default:
                    throw new java.lang.RuntimeException(java.lang.String.format("Unknown error while generating key: %d", java.lang.Integer.valueOf(e3.errorCode)));
            }
        } catch (android.security.KeyChainException e4) {
            android.util.Log.w(TAG, "Failed to generate key", e4);
            return null;
        } catch (java.lang.InterruptedException e5) {
            android.util.Log.w(TAG, "Interrupted while generating key", e5);
            java.lang.Thread.currentThread().interrupt();
            return null;
        }
    }

    public boolean grantKeyPairToApp(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) {
        throwIfParentInstance("grantKeyPairToApp");
        try {
            return this.mService.setKeyGrantForApp(componentName, this.mContext.getPackageName(), str, str2, true);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public java.util.Map<java.lang.Integer, java.util.Set<java.lang.String>> getKeyPairGrants(java.lang.String str) {
        throwIfParentInstance("getKeyPairGrants");
        try {
            return this.mService.getKeyPairGrants(this.mContext.getPackageName(), str).getPackagesByUid();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public boolean revokeKeyPairFromApp(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) {
        throwIfParentInstance("revokeKeyPairFromApp");
        try {
            return this.mService.setKeyGrantForApp(componentName, this.mContext.getPackageName(), str, str2, false);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean grantKeyPairToWifiAuth(java.lang.String str) {
        throwIfParentInstance("grantKeyPairToWifiAuth");
        try {
            return this.mService.setKeyGrantToWifiAuth(this.mContext.getPackageName(), str, true);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean revokeKeyPairFromWifiAuth(java.lang.String str) {
        throwIfParentInstance("revokeKeyPairFromWifiAuth");
        try {
            return this.mService.setKeyGrantToWifiAuth(this.mContext.getPackageName(), str, false);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isKeyPairGrantedToWifiAuth(java.lang.String str) {
        throwIfParentInstance("isKeyPairGrantedToWifiAuth");
        try {
            return this.mService.isKeyPairGrantedToWifiAuth(this.mContext.getPackageName(), str);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isDeviceIdAttestationSupported() {
        return this.mContext.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_DEVICE_ID_ATTESTATION);
    }

    public boolean isUniqueDeviceAttestationSupported() {
        return this.mContext.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_DEVICE_UNIQUE_ATTESTATION);
    }

    public boolean setKeyPairCertificate(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.security.cert.Certificate> list, boolean z) {
        byte[] bArr;
        throwIfParentInstance("setKeyPairCertificate");
        try {
            byte[] convertToPem = android.security.Credentials.convertToPem(list.get(0));
            if (list.size() <= 1) {
                bArr = null;
            } else {
                bArr = android.security.Credentials.convertToPem((java.security.cert.Certificate[]) list.subList(1, list.size()).toArray(new java.security.cert.Certificate[0]));
            }
            return this.mService.setKeyPairCertificate(componentName, this.mContext.getPackageName(), str, convertToPem, bArr, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.io.IOException | java.security.cert.CertificateException e2) {
            android.util.Log.w(TAG, "Could not pem-encode certificate", e2);
            return false;
        }
    }

    private static java.lang.String getCaCertAlias(byte[] bArr) throws java.security.cert.CertificateException {
        return new com.android.org.conscrypt.TrustedCertificateStore().getCertificateAlias((java.security.cert.X509Certificate) java.security.cert.CertificateFactory.getInstance("X.509").generateCertificate(new java.io.ByteArrayInputStream(bArr)));
    }

    @java.lang.Deprecated
    public void setCertInstallerPackage(android.content.ComponentName componentName, java.lang.String str) throws java.lang.SecurityException {
        throwIfParentInstance("setCertInstallerPackage");
        if (this.mService != null) {
            try {
                this.mService.setCertInstallerPackage(componentName, str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public java.lang.String getCertInstallerPackage(android.content.ComponentName componentName) throws java.lang.SecurityException {
        throwIfParentInstance("getCertInstallerPackage");
        if (this.mService != null) {
            try {
                return this.mService.getCertInstallerPackage(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public void setDelegatedScopes(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list) {
        throwIfParentInstance("setDelegatedScopes");
        if (this.mService != null) {
            try {
                this.mService.setDelegatedScopes(componentName, str, list);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.util.List<java.lang.String> getDelegatedScopes(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("getDelegatedScopes");
        if (this.mService != null) {
            try {
                return this.mService.getDelegatedScopes(componentName, str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public java.util.List<java.lang.String> getDelegatePackages(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("getDelegatePackages");
        if (this.mService != null) {
            try {
                return this.mService.getDelegatePackages(componentName, str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public void setAlwaysOnVpnPackage(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.content.pm.PackageManager.NameNotFoundException {
        setAlwaysOnVpnPackage(componentName, str, z, java.util.Collections.emptySet());
    }

    public void setAlwaysOnVpnPackage(android.content.ComponentName componentName, java.lang.String str, boolean z, java.util.Set<java.lang.String> set) throws android.content.pm.PackageManager.NameNotFoundException {
        throwIfParentInstance("setAlwaysOnVpnPackage");
        if (this.mService != null) {
            try {
                this.mService.setAlwaysOnVpnPackage(componentName, str, z, set == null ? null : new java.util.ArrayList(set));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (android.os.ServiceSpecificException e2) {
                switch (e2.errorCode) {
                    case 1:
                        throw new android.content.pm.PackageManager.NameNotFoundException(e2.getMessage());
                    default:
                        throw new java.lang.RuntimeException("Unknown error setting always-on VPN: " + e2.errorCode, e2);
                }
            }
        }
    }

    public boolean isAlwaysOnVpnLockdownEnabled(android.content.ComponentName componentName) {
        throwIfParentInstance("isAlwaysOnVpnLockdownEnabled");
        if (this.mService != null) {
            try {
                return this.mService.isAlwaysOnVpnLockdownEnabled(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isAlwaysOnVpnLockdownEnabled() {
        throwIfParentInstance("isAlwaysOnVpnLockdownEnabled");
        if (this.mService != null) {
            try {
                return this.mService.isAlwaysOnVpnLockdownEnabledForUser(myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.util.Set<java.lang.String> getAlwaysOnVpnLockdownWhitelist(android.content.ComponentName componentName) {
        throwIfParentInstance("getAlwaysOnVpnLockdownWhitelist");
        if (this.mService == null) {
            return null;
        }
        try {
            java.util.List<java.lang.String> alwaysOnVpnLockdownAllowlist = this.mService.getAlwaysOnVpnLockdownAllowlist(componentName);
            return alwaysOnVpnLockdownAllowlist != null ? new java.util.HashSet(alwaysOnVpnLockdownAllowlist) : null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getAlwaysOnVpnPackage(android.content.ComponentName componentName) {
        throwIfParentInstance("getAlwaysOnVpnPackage");
        if (this.mService != null) {
            try {
                return this.mService.getAlwaysOnVpnPackage(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public java.lang.String getAlwaysOnVpnPackage() {
        throwIfParentInstance("getAlwaysOnVpnPackage");
        if (this.mService != null) {
            try {
                return this.mService.getAlwaysOnVpnPackageForUser(myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public void setCameraDisabled(android.content.ComponentName componentName, boolean z) {
        if (this.mService != null) {
            try {
                this.mService.setCameraDisabled(componentName, this.mContext.getPackageName(), z, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean getCameraDisabled(android.content.ComponentName componentName) {
        return getCameraDisabled(componentName, myUserId());
    }

    public boolean getCameraDisabled(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getCameraDisabled(componentName, this.mContext.getPackageName(), i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean requestBugreport(android.content.ComponentName componentName) {
        throwIfParentInstance("requestBugreport");
        if (this.mService != null) {
            try {
                return this.mService.requestBugreport(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setScreenCaptureDisabled(android.content.ComponentName componentName, boolean z) {
        if (this.mService != null) {
            try {
                this.mService.setScreenCaptureDisabled(componentName, this.mContext.getPackageName(), z, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean getScreenCaptureDisabled(android.content.ComponentName componentName) {
        return getScreenCaptureDisabled(componentName, myUserId());
    }

    public boolean getScreenCaptureDisabled(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getScreenCaptureDisabled(componentName, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setNearbyNotificationStreamingPolicy(int i) {
        throwIfParentInstance("setNearbyNotificationStreamingPolicy");
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.setNearbyNotificationStreamingPolicy(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getNearbyNotificationStreamingPolicy() {
        return getNearbyNotificationStreamingPolicy(myUserId());
    }

    public int getNearbyNotificationStreamingPolicy(int i) {
        throwIfParentInstance("getNearbyNotificationStreamingPolicy");
        if (this.mService == null) {
            return 0;
        }
        try {
            return this.mService.getNearbyNotificationStreamingPolicy(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNearbyAppStreamingPolicy(int i) {
        throwIfParentInstance("setNearbyAppStreamingPolicy");
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.setNearbyAppStreamingPolicy(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getNearbyAppStreamingPolicy() {
        return getNearbyAppStreamingPolicy(myUserId());
    }

    public int getNearbyAppStreamingPolicy(int i) {
        throwIfParentInstance("getNearbyAppStreamingPolicy");
        if (this.mService == null) {
            return 0;
        }
        try {
            return this.mService.getNearbyAppStreamingPolicy(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void setAutoTimeRequired(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setAutoTimeRequired");
        if (this.mService != null) {
            try {
                this.mService.setAutoTimeRequired(componentName, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public boolean getAutoTimeRequired() {
        throwIfParentInstance("getAutoTimeRequired");
        if (this.mService != null) {
            try {
                return this.mService.getAutoTimeRequired();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setAutoTimeEnabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setAutoTimeEnabled");
        if (this.mService != null) {
            try {
                this.mService.setAutoTimeEnabled(componentName, this.mContext.getPackageName(), z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean getAutoTimeEnabled(android.content.ComponentName componentName) {
        throwIfParentInstance("getAutoTimeEnabled");
        if (this.mService != null) {
            try {
                return this.mService.getAutoTimeEnabled(componentName, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setAutoTimeZoneEnabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setAutoTimeZone");
        if (this.mService != null) {
            try {
                this.mService.setAutoTimeZoneEnabled(componentName, this.mContext.getPackageName(), z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean getAutoTimeZoneEnabled(android.content.ComponentName componentName) {
        throwIfParentInstance("getAutoTimeZone");
        if (this.mService != null) {
            try {
                return this.mService.getAutoTimeZoneEnabled(componentName, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setForceEphemeralUsers(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setForceEphemeralUsers");
        if (this.mService != null) {
            try {
                this.mService.setForceEphemeralUsers(componentName, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean getForceEphemeralUsers(android.content.ComponentName componentName) {
        throwIfParentInstance("getForceEphemeralUsers");
        if (this.mService != null) {
            try {
                return this.mService.getForceEphemeralUsers(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setKeyguardDisabledFeatures(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                this.mService.setKeyguardDisabledFeatures(componentName, this.mContext.getPackageName(), i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getKeyguardDisabledFeatures(android.content.ComponentName componentName) {
        return getKeyguardDisabledFeatures(componentName, myUserId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ java.lang.Integer lambda$new$2(android.util.Pair pair) throws android.os.RemoteException {
        return java.lang.Integer.valueOf(getService().getKeyguardDisabledFeatures((android.content.ComponentName) pair.first, ((java.lang.Integer) pair.second).intValue(), isParentInstance()));
    }

    public int getKeyguardDisabledFeatures(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            return this.mGetKeyGuardDisabledFeaturesCache.query(new android.util.Pair<>(componentName, java.lang.Integer.valueOf(i))).intValue();
        }
        return 0;
    }

    public void setActiveAdmin(android.content.ComponentName componentName, boolean z, int i) {
        if (this.mService != null) {
            try {
                this.mService.setActiveAdmin(componentName, z, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setActiveAdmin(android.content.ComponentName componentName, boolean z) {
        setActiveAdmin(componentName, z, myUserId());
    }

    public void getRemoveWarning(android.content.ComponentName componentName, android.os.RemoteCallback remoteCallback) {
        if (this.mService != null) {
            try {
                this.mService.getRemoveWarning(componentName, remoteCallback, myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportPasswordChanged(android.app.admin.PasswordMetrics passwordMetrics, int i) {
        if (this.mService != null) {
            try {
                this.mService.reportPasswordChanged(passwordMetrics, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportFailedPasswordAttempt(int i) {
        if (this.mService != null) {
            try {
                this.mService.reportFailedPasswordAttempt(i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportSuccessfulPasswordAttempt(int i) {
        if (this.mService != null) {
            try {
                this.mService.reportSuccessfulPasswordAttempt(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportFailedBiometricAttempt(int i) {
        if (this.mService != null) {
            try {
                this.mService.reportFailedBiometricAttempt(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportSuccessfulBiometricAttempt(int i) {
        if (this.mService != null) {
            try {
                this.mService.reportSuccessfulBiometricAttempt(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportKeyguardDismissed(int i) {
        if (this.mService != null) {
            try {
                this.mService.reportKeyguardDismissed(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportKeyguardSecured(int i) {
        if (this.mService != null) {
            try {
                this.mService.reportKeyguardSecured(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean setDeviceOwner(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.setDeviceOwner(componentName, i, true);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean setDeviceOwnerOnly(android.content.ComponentName componentName, int i) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setDeviceOwner(componentName, i, false);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isDeviceOwnerApp(java.lang.String str) {
        throwIfParentInstance("isDeviceOwnerApp");
        if (android.permission.flags.Flags.systemServerRoleControllerEnabled() && android.app.compat.CompatChanges.isChangeEnabled(IS_DEVICE_OWNER_USER_AWARE)) {
            return isDeviceOwnerAppOnContextUser(str);
        }
        return isDeviceOwnerAppOnCallingUser(str);
    }

    public boolean isDeviceOwnerAppOnCallingUser(java.lang.String str) {
        return isDeviceOwnerAppOnAnyUserInner(str, true);
    }

    public boolean isDeviceOwnerAppOnAnyUser(java.lang.String str) {
        return isDeviceOwnerAppOnAnyUserInner(str, false);
    }

    public android.content.ComponentName getDeviceOwnerComponentOnCallingUser() {
        return getDeviceOwnerComponentInner(true);
    }

    @android.annotation.SystemApi
    public android.content.ComponentName getDeviceOwnerComponentOnAnyUser() {
        return getDeviceOwnerComponentInner(false);
    }

    private boolean isDeviceOwnerAppOnAnyUserInner(java.lang.String str, boolean z) {
        android.content.ComponentName deviceOwnerComponentInner;
        if (str == null || (deviceOwnerComponentInner = getDeviceOwnerComponentInner(z)) == null) {
            return false;
        }
        return str.equals(deviceOwnerComponentInner.getPackageName());
    }

    private boolean isDeviceOwnerAppOnContextUser(java.lang.String str) {
        android.content.ComponentName deviceOwnerComponentOnUser;
        if (str == null) {
            return false;
        }
        if (this.mService == null) {
            deviceOwnerComponentOnUser = null;
        } else {
            try {
                deviceOwnerComponentOnUser = this.mService.getDeviceOwnerComponentOnUser(myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (deviceOwnerComponentOnUser == null) {
            return false;
        }
        return str.equals(deviceOwnerComponentOnUser.getPackageName());
    }

    private android.content.ComponentName getDeviceOwnerComponentInner(boolean z) {
        if (this.mService != null) {
            try {
                return this.mService.getDeviceOwnerComponent(z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public android.os.UserHandle getDeviceOwnerUser() {
        if (this.mService != null) {
            try {
                int deviceOwnerUserId = this.mService.getDeviceOwnerUserId();
                if (deviceOwnerUserId != -10000) {
                    return android.os.UserHandle.of(deviceOwnerUserId);
                }
                return null;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public int getDeviceOwnerUserId() {
        if (this.mService != null) {
            try {
                return this.mService.getDeviceOwnerUserId();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -10000;
    }

    @java.lang.Deprecated
    public void clearDeviceOwnerApp(java.lang.String str) {
        throwIfParentInstance("clearDeviceOwnerApp");
        if (this.mService != null) {
            try {
                this.mService.clearDeviceOwner(str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getDeviceOwner() {
        throwIfParentInstance("getDeviceOwner");
        android.content.ComponentName deviceOwnerComponentOnCallingUser = getDeviceOwnerComponentOnCallingUser();
        if (deviceOwnerComponentOnCallingUser != null) {
            return deviceOwnerComponentOnCallingUser.getPackageName();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$new$3(java.lang.Void r1) throws android.os.RemoteException {
        return java.lang.Boolean.valueOf(getService().hasDeviceOwner());
    }

    @android.annotation.SystemApi
    public boolean isDeviceManaged() {
        return this.mHasDeviceOwnerCache.query(null).booleanValue();
    }

    @android.annotation.SystemApi
    public java.lang.String getDeviceOwnerNameOnAnyUser() {
        throwIfParentInstance("getDeviceOwnerNameOnAnyUser");
        if (this.mService != null) {
            try {
                return this.mService.getDeviceOwnerName();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean setActiveProfileOwner(android.content.ComponentName componentName, java.lang.String str) throws java.lang.IllegalArgumentException {
        throwIfParentInstance("setActiveProfileOwner");
        if (this.mService == null) {
            return false;
        }
        try {
            int myUserId = myUserId();
            this.mService.setActiveAdmin(componentName, false, myUserId);
            return this.mService.setProfileOwner(componentName, myUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void clearProfileOwner(android.content.ComponentName componentName) {
        throwIfParentInstance("clearProfileOwner");
        if (this.mService != null) {
            try {
                this.mService.clearProfileOwner(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean hasUserSetupCompleted() {
        if (this.mService != null) {
            try {
                return this.mService.hasUserSetupCompleted();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public boolean setProfileOwner(android.content.ComponentName componentName, int i) throws java.lang.IllegalArgumentException {
        if (this.mService != null) {
            try {
                return this.mService.setProfileOwner(componentName, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setDeviceOwnerLockScreenInfo(android.content.ComponentName componentName, java.lang.CharSequence charSequence) {
        throwIfParentInstance("setDeviceOwnerLockScreenInfo");
        if (this.mService != null) {
            try {
                this.mService.setDeviceOwnerLockScreenInfo(componentName, charSequence);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.lang.CharSequence getDeviceOwnerLockScreenInfo() {
        throwIfParentInstance("getDeviceOwnerLockScreenInfo");
        if (this.mService != null) {
            try {
                return this.mService.getDeviceOwnerLockScreenInfo();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public java.lang.String[] setPackagesSuspended(android.content.ComponentName componentName, java.lang.String[] strArr, boolean z) {
        throwIfParentInstance("setPackagesSuspended");
        if (this.mService != null) {
            try {
                return this.mService.setPackagesSuspended(componentName, this.mContext.getPackageName(), strArr, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return strArr;
    }

    public boolean isPackageSuspended(android.content.ComponentName componentName, java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throwIfParentInstance("isPackageSuspended");
        if (this.mService != null) {
            try {
                return this.mService.isPackageSuspended(componentName, this.mContext.getPackageName(), str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.lang.IllegalArgumentException e2) {
                android.util.Log.e(TAG, "IllegalArgumentException checking isPackageSuspended", e2);
                throw new android.content.pm.PackageManager.NameNotFoundException(str);
            }
        }
        return false;
    }

    public void setProfileEnabled(android.content.ComponentName componentName) {
        throwIfParentInstance("setProfileEnabled");
        if (this.mService != null) {
            try {
                this.mService.setProfileEnabled(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setProfileName(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("setProfileName");
        if (this.mService != null) {
            try {
                this.mService.setProfileName(componentName, str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isProfileOwnerApp(java.lang.String str) {
        throwIfParentInstance("isProfileOwnerApp");
        if (this.mService == null) {
            return false;
        }
        try {
            android.content.ComponentName profileOwnerAsUser = this.mService.getProfileOwnerAsUser(myUserId());
            if (profileOwnerAsUser != null) {
                return profileOwnerAsUser.getPackageName().equals(str);
            }
            return false;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.content.ComponentName getProfileOwner() throws java.lang.IllegalArgumentException {
        throwIfParentInstance("getProfileOwner");
        return getProfileOwnerAsUser(this.mContext.getUserId());
    }

    public android.content.ComponentName getProfileOwnerAsUser(android.os.UserHandle userHandle) {
        if (this.mService != null) {
            try {
                return this.mService.getProfileOwnerAsUser(userHandle.getIdentifier());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public android.content.ComponentName getProfileOwnerAsUser(int i) {
        if (this.mService != null) {
            try {
                return this.mService.getProfileOwnerAsUser(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.ComponentName lambda$new$4(android.os.UserHandle userHandle) throws android.os.RemoteException {
        return getService().getProfileOwnerOrDeviceOwnerSupervisionComponent(userHandle);
    }

    public android.content.ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(android.os.UserHandle userHandle) {
        if (this.mService != null) {
            return this.mGetProfileOwnerOrDeviceOwnerSupervisionComponentCache.query(userHandle);
        }
        return null;
    }

    public boolean isSupervisionComponent(android.content.ComponentName componentName) {
        if (this.mService != null) {
            try {
                return getService().isSupervisionComponent(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.lang.String getProfileOwnerName() throws java.lang.IllegalArgumentException {
        if (this.mService != null) {
            try {
                return this.mService.getProfileOwnerName(this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public java.lang.String getProfileOwnerNameAsUser(int i) throws java.lang.IllegalArgumentException {
        throwIfParentInstance("getProfileOwnerNameAsUser");
        if (this.mService != null) {
            try {
                return this.mService.getProfileOwnerName(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Object lambda$new$5(java.lang.Object obj) throws android.os.RemoteException {
        return java.lang.Boolean.valueOf(getService().isOrganizationOwnedDeviceWithManagedProfile());
    }

    public boolean isOrganizationOwnedDeviceWithManagedProfile() {
        throwIfParentInstance("isOrganizationOwnedDeviceWithManagedProfile");
        if (this.mService != null) {
            return this.mIsOrganizationOwnedDeviceWithManagedProfileCache.query(null).booleanValue();
        }
        return false;
    }

    public boolean hasDeviceIdentifierAccess(java.lang.String str, int i, int i2) {
        throwIfParentInstance("hasDeviceIdentifierAccess");
        if (str == null || this.mService == null) {
            return false;
        }
        try {
            return this.mService.checkDeviceIdentifierAccess(str, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addPersistentPreferredActivity(android.content.ComponentName componentName, android.content.IntentFilter intentFilter, android.content.ComponentName componentName2) {
        throwIfParentInstance("addPersistentPreferredActivity");
        if (this.mService != null) {
            try {
                this.mService.addPersistentPreferredActivity(componentName, this.mContext.getPackageName(), intentFilter, componentName2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void clearPackagePersistentPreferredActivities(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("clearPackagePersistentPreferredActivities");
        if (this.mService != null) {
            try {
                this.mService.clearPackagePersistentPreferredActivities(componentName, this.mContext.getPackageName(), str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setDefaultSmsApplication(android.content.ComponentName componentName, java.lang.String str) {
        if (this.mService != null) {
            try {
                this.mService.setDefaultSmsApplication(componentName, this.mContext.getPackageName(), str, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setDefaultDialerApplication(java.lang.String str) {
        throwIfParentInstance("setDefaultDialerApplication");
        if (this.mService != null) {
            try {
                this.mService.setDefaultDialerApplication(str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public void setApplicationRestrictionsManagingPackage(android.content.ComponentName componentName, java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throwIfParentInstance("setApplicationRestrictionsManagingPackage");
        if (this.mService != null) {
            try {
                if (!this.mService.setApplicationRestrictionsManagingPackage(componentName, str)) {
                    throw new android.content.pm.PackageManager.NameNotFoundException(str);
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public java.lang.String getApplicationRestrictionsManagingPackage(android.content.ComponentName componentName) {
        throwIfParentInstance("getApplicationRestrictionsManagingPackage");
        if (this.mService != null) {
            try {
                return this.mService.getApplicationRestrictionsManagingPackage(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @java.lang.Deprecated
    public boolean isCallerApplicationRestrictionsManagingPackage() {
        throwIfParentInstance("isCallerApplicationRestrictionsManagingPackage");
        if (this.mService != null) {
            try {
                return this.mService.isCallerApplicationRestrictionsManagingPackage(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setApplicationRestrictions(android.content.ComponentName componentName, java.lang.String str, android.os.Bundle bundle) {
        throwIfParentInstance("setApplicationRestrictions");
        if (this.mService != null) {
            try {
                this.mService.setApplicationRestrictions(componentName, this.mContext.getPackageName(), str, bundle);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setTrustAgentConfiguration(android.content.ComponentName componentName, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle) {
        if (this.mService != null) {
            try {
                this.mService.setTrustAgentConfiguration(componentName, this.mContext.getPackageName(), componentName2, persistableBundle, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.util.List<android.os.PersistableBundle> getTrustAgentConfiguration(android.content.ComponentName componentName, android.content.ComponentName componentName2) {
        return getTrustAgentConfiguration(componentName, componentName2, myUserId());
    }

    public java.util.List<android.os.PersistableBundle> getTrustAgentConfiguration(android.content.ComponentName componentName, android.content.ComponentName componentName2, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getTrustAgentConfiguration(componentName, componentName2, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return new java.util.ArrayList();
    }

    @java.lang.Deprecated
    public void setCrossProfileCallerIdDisabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setCrossProfileCallerIdDisabled");
        if (this.mService != null) {
            try {
                this.mService.setCrossProfileCallerIdDisabled(componentName, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public boolean getCrossProfileCallerIdDisabled(android.content.ComponentName componentName) {
        throwIfParentInstance("getCrossProfileCallerIdDisabled");
        if (this.mService != null) {
            try {
                return this.mService.getCrossProfileCallerIdDisabled(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @java.lang.Deprecated
    public boolean getCrossProfileCallerIdDisabled(android.os.UserHandle userHandle) {
        if (this.mService != null) {
            try {
                return this.mService.getCrossProfileCallerIdDisabledForUser(userHandle.getIdentifier());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setCredentialManagerPolicy(android.app.admin.PackagePolicy packagePolicy) {
        throwIfParentInstance("setCredentialManagerPolicy");
        if (this.mService != null) {
            try {
                this.mService.setCredentialManagerPolicy(packagePolicy);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.app.admin.PackagePolicy getCredentialManagerPolicy() {
        throwIfParentInstance("getCredentialManagerPolicy");
        if (this.mService != null) {
            try {
                return this.mService.getCredentialManagerPolicy(myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public void setManagedProfileCallerIdAccessPolicy(android.app.admin.PackagePolicy packagePolicy) {
        throwIfParentInstance("setManagedProfileCallerIdAccessPolicy");
        if (this.mService != null) {
            try {
                this.mService.setManagedProfileCallerIdAccessPolicy(packagePolicy);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.app.admin.PackagePolicy getManagedProfileCallerIdAccessPolicy() {
        throwIfParentInstance("getManagedProfileCallerIdAccessPolicy");
        if (this.mService != null) {
            try {
                return this.mService.getManagedProfileCallerIdAccessPolicy();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public boolean hasManagedProfileCallerIdAccess(android.os.UserHandle userHandle, java.lang.String str) {
        if (this.mService == null) {
            return true;
        }
        try {
            return this.mService.hasManagedProfileCallerIdAccess(userHandle.getIdentifier(), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setManagedProfileContactsAccessPolicy(android.app.admin.PackagePolicy packagePolicy) {
        throwIfParentInstance("setManagedProfileContactsAccessPolicy");
        if (this.mService != null) {
            try {
                this.mService.setManagedProfileContactsAccessPolicy(packagePolicy);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.app.admin.PackagePolicy getManagedProfileContactsAccessPolicy() {
        throwIfParentInstance("getManagedProfileContactsAccessPolicy");
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getManagedProfileContactsAccessPolicy();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public boolean hasManagedProfileContactsAccess(android.os.UserHandle userHandle, java.lang.String str) {
        if (this.mService != null) {
            try {
                return this.mService.hasManagedProfileContactsAccess(userHandle.getIdentifier(), str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @java.lang.Deprecated
    public void setCrossProfileContactsSearchDisabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setCrossProfileContactsSearchDisabled");
        if (this.mService != null) {
            try {
                this.mService.setCrossProfileContactsSearchDisabled(componentName, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public boolean getCrossProfileContactsSearchDisabled(android.content.ComponentName componentName) {
        throwIfParentInstance("getCrossProfileContactsSearchDisabled");
        if (this.mService != null) {
            try {
                return this.mService.getCrossProfileContactsSearchDisabled(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @java.lang.Deprecated
    public boolean getCrossProfileContactsSearchDisabled(android.os.UserHandle userHandle) {
        if (this.mService != null) {
            try {
                return this.mService.getCrossProfileContactsSearchDisabledForUser(userHandle.getIdentifier());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void startManagedQuickContact(java.lang.String str, long j, boolean z, long j2, android.content.Intent intent) {
        if (this.mService != null) {
            try {
                this.mService.startManagedQuickContact(str, j, z, j2, intent);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void startManagedQuickContact(java.lang.String str, long j, android.content.Intent intent) {
        startManagedQuickContact(str, j, false, 0L, intent);
    }

    public void setBluetoothContactSharingDisabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setBluetoothContactSharingDisabled");
        if (this.mService != null) {
            try {
                this.mService.setBluetoothContactSharingDisabled(componentName, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean getBluetoothContactSharingDisabled(android.content.ComponentName componentName) {
        throwIfParentInstance("getBluetoothContactSharingDisabled");
        if (this.mService != null) {
            try {
                return this.mService.getBluetoothContactSharingDisabled(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    @android.annotation.SystemApi
    public boolean getBluetoothContactSharingDisabled(android.os.UserHandle userHandle) {
        if (this.mService != null) {
            try {
                return this.mService.getBluetoothContactSharingDisabledForUser(userHandle.getIdentifier());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public void addCrossProfileIntentFilter(android.content.ComponentName componentName, android.content.IntentFilter intentFilter, int i) {
        throwIfParentInstance("addCrossProfileIntentFilter");
        if (this.mService != null) {
            try {
                this.mService.addCrossProfileIntentFilter(componentName, this.mContext.getPackageName(), intentFilter, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void clearCrossProfileIntentFilters(android.content.ComponentName componentName) {
        throwIfParentInstance("clearCrossProfileIntentFilters");
        if (this.mService != null) {
            try {
                this.mService.clearCrossProfileIntentFilters(componentName, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean setPermittedAccessibilityServices(android.content.ComponentName componentName, java.util.List<java.lang.String> list) {
        throwIfParentInstance("setPermittedAccessibilityServices");
        if (this.mService != null) {
            try {
                return this.mService.setPermittedAccessibilityServices(componentName, list);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.util.List<java.lang.String> getPermittedAccessibilityServices(android.content.ComponentName componentName) {
        throwIfParentInstance("getPermittedAccessibilityServices");
        if (this.mService != null) {
            try {
                return this.mService.getPermittedAccessibilityServices(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public boolean isAccessibilityServicePermittedByAdmin(android.content.ComponentName componentName, java.lang.String str, int i) {
        if (this.mService != null) {
            try {
                return this.mService.isAccessibilityServicePermittedByAdmin(componentName, str, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> getPermittedAccessibilityServices(int i) {
        throwIfParentInstance("getPermittedAccessibilityServices");
        if (this.mService != null) {
            try {
                return this.mService.getPermittedAccessibilityServicesForUser(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public boolean setPermittedInputMethods(android.content.ComponentName componentName, java.util.List<java.lang.String> list) {
        if (this.mService != null) {
            try {
                return this.mService.setPermittedInputMethods(componentName, this.mContext.getPackageName(), list, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.util.List<java.lang.String> getPermittedInputMethods(android.content.ComponentName componentName) {
        if (this.mService != null) {
            try {
                return this.mService.getPermittedInputMethods(componentName, this.mContext.getPackageName(), this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public boolean isInputMethodPermittedByAdmin(android.content.ComponentName componentName, java.lang.String str, int i) {
        if (this.mService != null) {
            try {
                return this.mService.isInputMethodPermittedByAdmin(componentName, str, i, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> getPermittedInputMethodsForCurrentUser() {
        throwIfParentInstance("getPermittedInputMethodsForCurrentUser");
        if (this.mService != null) {
            try {
                return this.mService.getPermittedInputMethodsAsUser(android.os.UserHandle.myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public java.util.List<java.lang.String> getPermittedInputMethods() {
        throwIfParentInstance("getPermittedInputMethods");
        if (this.mService != null) {
            try {
                return this.mService.getPermittedInputMethodsAsUser(myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public boolean setPermittedCrossProfileNotificationListeners(android.content.ComponentName componentName, java.util.List<java.lang.String> list) {
        throwIfParentInstance("setPermittedCrossProfileNotificationListeners");
        if (this.mService != null) {
            try {
                return this.mService.setPermittedCrossProfileNotificationListeners(componentName, list);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.util.List<java.lang.String> getPermittedCrossProfileNotificationListeners(android.content.ComponentName componentName) {
        throwIfParentInstance("getPermittedCrossProfileNotificationListeners");
        if (this.mService != null) {
            try {
                return this.mService.getPermittedCrossProfileNotificationListeners(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public boolean isNotificationListenerServicePermitted(java.lang.String str, int i) {
        if (this.mService != null) {
            try {
                return this.mService.isNotificationListenerServicePermitted(str, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public java.util.List<java.lang.String> getKeepUninstalledPackages(android.content.ComponentName componentName) {
        throwIfParentInstance("getKeepUninstalledPackages");
        if (this.mService != null) {
            try {
                return this.mService.getKeepUninstalledPackages(componentName, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public void setKeepUninstalledPackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) {
        throwIfParentInstance("setKeepUninstalledPackages");
        if (this.mService != null) {
            try {
                this.mService.setKeepUninstalledPackages(componentName, this.mContext.getPackageName(), list);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.os.UserHandle createAndManageUser(android.content.ComponentName componentName, java.lang.String str, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle, int i) {
        throwIfParentInstance("createAndManageUser");
        try {
            return this.mService.createAndManageUser(componentName, str, componentName2, persistableBundle, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.os.UserManager.UserOperationException(e2.getMessage(), e2.errorCode);
        }
    }

    public boolean removeUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) {
        throwIfParentInstance("removeUser");
        try {
            return this.mService.removeUser(componentName, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean switchUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) {
        throwIfParentInstance("switchUser");
        try {
            return this.mService.switchUser(componentName, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int startUserInBackground(android.content.ComponentName componentName, android.os.UserHandle userHandle) {
        throwIfParentInstance("startUserInBackground");
        try {
            return this.mService.startUserInBackground(componentName, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int stopUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) {
        throwIfParentInstance("stopUser");
        try {
            return this.mService.stopUser(componentName, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int logoutUser(android.content.ComponentName componentName) {
        throwIfParentInstance("logoutUser");
        try {
            return this.mService.logoutUser(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setManagedSubscriptionsPolicy(android.app.admin.ManagedSubscriptionsPolicy managedSubscriptionsPolicy) {
        throwIfParentInstance("setManagedSubscriptionsPolicy");
        try {
            this.mService.setManagedSubscriptionsPolicy(managedSubscriptionsPolicy);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.admin.ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy() {
        throwIfParentInstance("getManagedSubscriptionsPolicy");
        try {
            return this.mService.getManagedSubscriptionsPolicy();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public int logoutUser() {
        try {
            return this.mService.logoutUserInternal();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public android.os.UserHandle getLogoutUser() {
        try {
            int logoutUserId = this.mService.getLogoutUserId();
            if (logoutUserId == -10000) {
                return null;
            }
            return android.os.UserHandle.of(logoutUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.os.UserHandle> getSecondaryUsers(android.content.ComponentName componentName) {
        throwIfParentInstance("getSecondaryUsers");
        try {
            return this.mService.getSecondaryUsers(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isEphemeralUser(android.content.ComponentName componentName) {
        throwIfParentInstance("isEphemeralUser");
        try {
            return this.mService.isEphemeralUser(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.Bundle getApplicationRestrictions(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("getApplicationRestrictions");
        if (this.mService != null) {
            try {
                return this.mService.getApplicationRestrictions(componentName, this.mContext.getPackageName(), str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public void addUserRestriction(android.content.ComponentName componentName, java.lang.String str) {
        if (this.mService != null) {
            try {
                this.mService.setUserRestriction(componentName, this.mContext.getPackageName(), str, true, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void addUserRestrictionGlobally(java.lang.String str) {
        throwIfParentInstance("addUserRestrictionGlobally");
        if (this.mService != null) {
            try {
                this.mService.setUserRestrictionGlobally(this.mContext.getPackageName(), str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void clearUserRestriction(android.content.ComponentName componentName, java.lang.String str) {
        if (this.mService != null) {
            try {
                this.mService.setUserRestriction(componentName, this.mContext.getPackageName(), str, false, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.os.Bundle getUserRestrictions(android.content.ComponentName componentName) {
        android.os.Bundle userRestrictions;
        if (this.mService == null) {
            userRestrictions = null;
        } else {
            try {
                userRestrictions = this.mService.getUserRestrictions(componentName, this.mContext.getPackageName(), this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return userRestrictions == null ? new android.os.Bundle() : userRestrictions;
    }

    public android.os.Bundle getUserRestrictionsGlobally() {
        android.os.Bundle userRestrictionsGlobally;
        throwIfParentInstance("createAdminSupportIntent");
        if (this.mService == null) {
            userRestrictionsGlobally = null;
        } else {
            try {
                userRestrictionsGlobally = this.mService.getUserRestrictionsGlobally(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return userRestrictionsGlobally == null ? new android.os.Bundle() : userRestrictionsGlobally;
    }

    public android.content.Intent createAdminSupportIntent(java.lang.String str) {
        throwIfParentInstance("createAdminSupportIntent");
        if (this.mService == null) {
            return null;
        }
        try {
            android.content.Intent createAdminSupportIntent = this.mService.createAdminSupportIntent(str);
            if (createAdminSupportIntent != null) {
                createAdminSupportIntent.prepareToEnterProcess(32, this.mContext.getAttributionSource());
                return createAdminSupportIntent;
            }
            return createAdminSupportIntent;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.Bundle getEnforcingAdminAndUserDetails(int i, java.lang.String str) {
        if (this.mService != null) {
            try {
                return this.mService.getEnforcingAdminAndUserDetails(i, str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public java.util.Set<android.app.admin.EnforcingAdmin> getEnforcingAdminsForRestriction(int i, java.lang.String str) {
        if (this.mService != null) {
            try {
                return new java.util.HashSet(this.mService.getEnforcingAdminsForRestriction(i, str));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public boolean setApplicationHidden(android.content.ComponentName componentName, java.lang.String str, boolean z) {
        if (this.mService != null) {
            try {
                return this.mService.setApplicationHidden(componentName, this.mContext.getPackageName(), str, z, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isApplicationHidden(android.content.ComponentName componentName, java.lang.String str) {
        if (this.mService != null) {
            try {
                return this.mService.isApplicationHidden(componentName, this.mContext.getPackageName(), str, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void enableSystemApp(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("enableSystemApp");
        if (this.mService != null) {
            try {
                this.mService.enableSystemApp(componentName, this.mContext.getPackageName(), str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int enableSystemApp(android.content.ComponentName componentName, android.content.Intent intent) {
        throwIfParentInstance("enableSystemApp");
        if (this.mService != null) {
            try {
                return this.mService.enableSystemAppWithIntent(componentName, this.mContext.getPackageName(), intent);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public boolean installExistingPackage(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("installExistingPackage");
        if (this.mService != null) {
            try {
                return this.mService.installExistingPackage(componentName, this.mContext.getPackageName(), str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setAccountManagementDisabled(android.content.ComponentName componentName, java.lang.String str, boolean z) {
        if (this.mService != null) {
            try {
                this.mService.setAccountManagementDisabled(componentName, this.mContext.getPackageName(), str, z, this.mParentInstance);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.lang.String[] getAccountTypesWithManagementDisabled() {
        return getAccountTypesWithManagementDisabledAsUser(myUserId(), this.mParentInstance);
    }

    public java.lang.String[] getAccountTypesWithManagementDisabledAsUser(int i) {
        return getAccountTypesWithManagementDisabledAsUser(i, false);
    }

    public java.lang.String[] getAccountTypesWithManagementDisabledAsUser(int i, boolean z) {
        if (this.mService != null) {
            try {
                return this.mService.getAccountTypesWithManagementDisabledAsUser(i, this.mContext.getPackageName(), z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public void setSecondaryLockscreenEnabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setSecondaryLockscreenEnabled");
        if (this.mService != null) {
            try {
                this.mService.setSecondaryLockscreenEnabled(componentName, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public boolean isSecondaryLockscreenEnabled(android.os.UserHandle userHandle) {
        throwIfParentInstance("isSecondaryLockscreenEnabled");
        if (this.mService != null) {
            try {
                return this.mService.isSecondaryLockscreenEnabled(userHandle);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setLockTaskPackages(android.content.ComponentName componentName, java.lang.String[] strArr) throws java.lang.SecurityException {
        throwIfParentInstance("setLockTaskPackages");
        if (this.mService != null) {
            try {
                this.mService.setLockTaskPackages(componentName, this.mContext.getPackageName(), strArr);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.lang.String[] getLockTaskPackages(android.content.ComponentName componentName) {
        throwIfParentInstance("getLockTaskPackages");
        if (this.mService != null) {
            try {
                return this.mService.getLockTaskPackages(componentName, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return new java.lang.String[0];
    }

    public boolean isLockTaskPermitted(java.lang.String str) {
        throwIfParentInstance("isLockTaskPermitted");
        if (this.mService != null) {
            try {
                return this.mService.isLockTaskPermitted(str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setLockTaskFeatures(android.content.ComponentName componentName, int i) {
        throwIfParentInstance("setLockTaskFeatures");
        if (this.mService != null) {
            try {
                this.mService.setLockTaskFeatures(componentName, this.mContext.getPackageName(), i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getLockTaskFeatures(android.content.ComponentName componentName) {
        throwIfParentInstance("getLockTaskFeatures");
        if (this.mService != null) {
            try {
                return this.mService.getLockTaskFeatures(componentName, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public void setPreferentialNetworkServiceEnabled(boolean z) {
        throwIfParentInstance("setPreferentialNetworkServiceEnabled");
        android.app.admin.PreferentialNetworkServiceConfig.Builder builder = new android.app.admin.PreferentialNetworkServiceConfig.Builder();
        builder.setEnabled(z);
        if (z) {
            builder.setNetworkId(1);
        }
        setPreferentialNetworkServiceConfigs(java.util.List.of(builder.build()));
    }

    public boolean isPreferentialNetworkServiceEnabled() {
        throwIfParentInstance("isPreferentialNetworkServiceEnabled");
        return getPreferentialNetworkServiceConfigs().stream().anyMatch(new java.util.function.Predicate() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isEnabled;
                isEnabled = ((android.app.admin.PreferentialNetworkServiceConfig) obj).isEnabled();
                return isEnabled;
            }
        });
    }

    public void setPreferentialNetworkServiceConfigs(java.util.List<android.app.admin.PreferentialNetworkServiceConfig> list) {
        throwIfParentInstance("setPreferentialNetworkServiceConfigs");
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.setPreferentialNetworkServiceConfigs(list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.admin.PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs() {
        throwIfParentInstance("getPreferentialNetworkServiceConfigs");
        if (this.mService == null) {
            return java.util.List.of(android.app.admin.PreferentialNetworkServiceConfig.DEFAULT);
        }
        try {
            return this.mService.getPreferentialNetworkServiceConfigs();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setGlobalSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) {
        throwIfParentInstance("setGlobalSetting");
        if (this.mService != null) {
            try {
                this.mService.setGlobalSetting(componentName, str, str2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setSystemSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) {
        throwIfParentInstance("setSystemSetting");
        if (this.mService != null) {
            try {
                this.mService.setSystemSetting(componentName, str, str2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setConfiguredNetworksLockdownState(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setConfiguredNetworksLockdownState");
        if (this.mService != null) {
            try {
                this.mService.setConfiguredNetworksLockdownState(componentName, this.mContext.getPackageName(), z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean hasLockdownAdminConfiguredNetworks(android.content.ComponentName componentName) {
        throwIfParentInstance("hasLockdownAdminConfiguredNetworks");
        if (this.mService != null) {
            try {
                return this.mService.hasLockdownAdminConfiguredNetworks(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean setTime(android.content.ComponentName componentName, long j) {
        throwIfParentInstance("setTime");
        if (this.mService != null) {
            try {
                return this.mService.setTime(componentName, this.mContext.getPackageName(), j);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean setTimeZone(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("setTimeZone");
        if (this.mService != null) {
            try {
                return this.mService.setTimeZone(componentName, this.mContext.getPackageName(), str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setLocationEnabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setLocationEnabled");
        if (this.mService != null) {
            try {
                this.mService.setLocationEnabled(componentName, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setSecureSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) {
        throwIfParentInstance("setSecureSetting");
        if (this.mService != null) {
            try {
                this.mService.setSecureSetting(componentName, str, str2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setRestrictionsProvider(android.content.ComponentName componentName, android.content.ComponentName componentName2) {
        throwIfParentInstance("setRestrictionsProvider");
        if (this.mService != null) {
            try {
                this.mService.setRestrictionsProvider(componentName, componentName2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setMasterVolumeMuted(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setMasterVolumeMuted");
        if (this.mService != null) {
            try {
                this.mService.setMasterVolumeMuted(componentName, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isMasterVolumeMuted(android.content.ComponentName componentName) {
        throwIfParentInstance("isMasterVolumeMuted");
        if (this.mService != null) {
            try {
                return this.mService.isMasterVolumeMuted(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setUninstallBlocked(android.content.ComponentName componentName, java.lang.String str, boolean z) {
        throwIfParentInstance("setUninstallBlocked");
        if (this.mService != null) {
            try {
                this.mService.setUninstallBlocked(componentName, this.mContext.getPackageName(), str, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isUninstallBlocked(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("isUninstallBlocked");
        if (this.mService != null) {
            try {
                return this.mService.isUninstallBlocked(str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean addCrossProfileWidgetProvider(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("addCrossProfileWidgetProvider");
        if (this.mService != null) {
            try {
                return this.mService.addCrossProfileWidgetProvider(componentName, this.mContext.getPackageName(), str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean removeCrossProfileWidgetProvider(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("removeCrossProfileWidgetProvider");
        if (this.mService != null) {
            try {
                return this.mService.removeCrossProfileWidgetProvider(componentName, this.mContext.getPackageName(), str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.util.List<java.lang.String> getCrossProfileWidgetProviders(android.content.ComponentName componentName) {
        throwIfParentInstance("getCrossProfileWidgetProviders");
        if (this.mService != null) {
            try {
                java.util.List<java.lang.String> crossProfileWidgetProviders = this.mService.getCrossProfileWidgetProviders(componentName, this.mContext.getPackageName());
                if (crossProfileWidgetProviders != null) {
                    return crossProfileWidgetProviders;
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptyList();
    }

    public void setUserIcon(android.content.ComponentName componentName, android.graphics.Bitmap bitmap) {
        throwIfParentInstance("setUserIcon");
        try {
            this.mService.setUserIcon(componentName, bitmap);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSystemUpdatePolicy(android.content.ComponentName componentName, android.app.admin.SystemUpdatePolicy systemUpdatePolicy) {
        throwIfParentInstance("setSystemUpdatePolicy");
        if (this.mService != null) {
            try {
                this.mService.setSystemUpdatePolicy(componentName, this.mContext.getPackageName(), systemUpdatePolicy);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.app.admin.SystemUpdatePolicy getSystemUpdatePolicy() {
        throwIfParentInstance("getSystemUpdatePolicy");
        if (this.mService != null) {
            try {
                return this.mService.getSystemUpdatePolicy();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public void clearSystemUpdatePolicyFreezePeriodRecord() {
        throwIfParentInstance("clearSystemUpdatePolicyFreezePeriodRecord");
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.clearSystemUpdatePolicyFreezePeriodRecord();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setKeyguardDisabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setKeyguardDisabled");
        try {
            return this.mService.setKeyguardDisabled(componentName, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setStatusBarDisabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setStatusBarDisabled");
        try {
            return this.mService.setStatusBarDisabled(componentName, this.mContext.getPackageName(), z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isStatusBarDisabled() {
        throwIfParentInstance("isStatusBarDisabled");
        try {
            return this.mService.isStatusBarDisabled(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void notifyPendingSystemUpdate(long j) {
        throwIfParentInstance("notifyPendingSystemUpdate");
        if (this.mService != null) {
            try {
                this.mService.notifyPendingSystemUpdate(android.app.admin.SystemUpdateInfo.of(j));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public void notifyPendingSystemUpdate(long j, boolean z) {
        throwIfParentInstance("notifyPendingSystemUpdate");
        if (this.mService != null) {
            try {
                this.mService.notifyPendingSystemUpdate(android.app.admin.SystemUpdateInfo.of(j, z));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.app.admin.SystemUpdateInfo getPendingSystemUpdate(android.content.ComponentName componentName) {
        throwIfParentInstance("getPendingSystemUpdate");
        try {
            return this.mService.getPendingSystemUpdate(componentName, this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPermissionPolicy(android.content.ComponentName componentName, int i) {
        throwIfParentInstance("setPermissionPolicy");
        try {
            this.mService.setPermissionPolicy(componentName, this.mContext.getPackageName(), i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPermissionPolicy(android.content.ComponentName componentName) {
        throwIfParentInstance("getPermissionPolicy");
        try {
            return this.mService.getPermissionPolicy(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setPermissionGrantState(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, int i) {
        throwIfParentInstance("setPermissionGrantState");
        try {
            final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
            this.mService.setPermissionGrantState(componentName, this.mContext.getPackageName(), str, str2, i, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda10
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(android.os.Bundle bundle) {
                    completableFuture.complete(java.lang.Boolean.valueOf(r1 != null));
                }
            }));
            com.android.internal.os.BackgroundThread.getHandler().sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda11
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((java.util.concurrent.CompletableFuture) obj).complete((java.lang.Boolean) obj2);
                }
            }, completableFuture, false), 20000L);
            return ((java.lang.Boolean) completableFuture.get()).booleanValue();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    public int getPermissionGrantState(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) {
        throwIfParentInstance("getPermissionGrantState");
        try {
            return this.mService.getPermissionGrantState(componentName, this.mContext.getPackageName(), str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isProvisioningAllowed(java.lang.String str) {
        throwIfParentInstance("isProvisioningAllowed");
        try {
            return this.mService.isProvisioningAllowed(str, this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int checkProvisioningPrecondition(java.lang.String str, java.lang.String str2) {
        try {
            return this.mService.checkProvisioningPrecondition(str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isManagedProfile(android.content.ComponentName componentName) {
        throwIfParentInstance("isManagedProfile");
        try {
            return this.mService.isManagedProfile(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getWifiMacAddress(android.content.ComponentName componentName) {
        throwIfParentInstance("getWifiMacAddress");
        try {
            return this.mService.getWifiMacAddress(componentName, this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reboot(android.content.ComponentName componentName) {
        throwIfParentInstance("reboot");
        try {
            this.mService.reboot(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setShortSupportMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) {
        throwIfParentInstance("setShortSupportMessage");
        if (this.mService != null) {
            try {
                this.mService.setShortSupportMessage(componentName, this.mContext.getPackageName(), charSequence);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.lang.CharSequence getShortSupportMessage(android.content.ComponentName componentName) {
        throwIfParentInstance("getShortSupportMessage");
        if (this.mService != null) {
            try {
                return this.mService.getShortSupportMessage(componentName, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public void setLongSupportMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) {
        throwIfParentInstance("setLongSupportMessage");
        if (this.mService != null) {
            try {
                this.mService.setLongSupportMessage(componentName, charSequence);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.lang.CharSequence getLongSupportMessage(android.content.ComponentName componentName) {
        throwIfParentInstance("getLongSupportMessage");
        if (this.mService != null) {
            try {
                return this.mService.getLongSupportMessage(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public java.lang.CharSequence getShortSupportMessageForUser(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getShortSupportMessageForUser(componentName, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public java.lang.CharSequence getLongSupportMessageForUser(android.content.ComponentName componentName, int i) {
        if (this.mService != null) {
            try {
                return this.mService.getLongSupportMessageForUser(componentName, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public android.app.admin.DevicePolicyManager getParentProfileInstance(android.content.ComponentName componentName) {
        throwIfParentInstance("getParentProfileInstance");
        try {
            if (!this.mService.isManagedProfile(componentName)) {
                throw new java.lang.SecurityException("The current user does not have a parent profile.");
            }
            return new android.app.admin.DevicePolicyManager(this.mContext, this.mService, true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSecurityLoggingEnabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setSecurityLoggingEnabled");
        try {
            this.mService.setSecurityLoggingEnabled(componentName, this.mContext.getPackageName(), z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSecurityLoggingEnabled(android.content.ComponentName componentName) {
        throwIfParentInstance("isSecurityLoggingEnabled");
        try {
            return this.mService.isSecurityLoggingEnabled(componentName, this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setAuditLogEnabled(boolean z) {
        throwIfParentInstance("setAuditLogEnabled");
        try {
            this.mService.setAuditLogEnabled(this.mContext.getPackageName(), z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isAuditLogEnabled() {
        throwIfParentInstance("isAuditLogEnabled");
        try {
            return this.mService.isAuditLogEnabled(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @android.annotation.SystemApi
    public void setAuditLogEventCallback(java.util.concurrent.Executor executor, java.util.function.Consumer<java.util.List<android.app.admin.SecurityLog.SecurityEvent>> consumer) {
        android.app.admin.DevicePolicyManager.AnonymousClass1 anonymousClass1;
        throwIfParentInstance("setAuditLogEventCallback");
        if (consumer == null) {
            anonymousClass1 = null;
        } else {
            anonymousClass1 = new android.app.admin.DevicePolicyManager.AnonymousClass1(executor, consumer);
        }
        try {
            this.mService.setAuditLogEventsCallback(this.mContext.getPackageName(), anonymousClass1);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.admin.DevicePolicyManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.app.admin.IAuditLogEventsCallback.Stub {
        final /* synthetic */ java.util.function.Consumer val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass1(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // android.app.admin.IAuditLogEventsCallback
        public void onNewAuditLogEvents(final java.util.List<android.app.admin.SecurityLog.SecurityEvent> list) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.app.admin.DevicePolicyManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(list);
                }
            });
        }
    }

    public java.util.List<android.app.admin.SecurityLog.SecurityEvent> retrieveSecurityLogs(android.content.ComponentName componentName) {
        throwIfParentInstance("retrieveSecurityLogs");
        try {
            android.content.pm.ParceledListSlice retrieveSecurityLogs = this.mService.retrieveSecurityLogs(componentName, this.mContext.getPackageName());
            if (retrieveSecurityLogs != null) {
                return retrieveSecurityLogs.getList();
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long forceNetworkLogs() {
        if (this.mService == null) {
            return -1L;
        }
        try {
            return this.mService.forceNetworkLogs();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long forceSecurityLogs() {
        if (this.mService == null) {
            return 0L;
        }
        try {
            return this.mService.forceSecurityLogs();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.admin.DevicePolicyManager getParentProfileInstance(android.content.pm.UserInfo userInfo) {
        this.mContext.checkSelfPermission(android.Manifest.permission.MANAGE_PROFILE_AND_DEVICE_OWNERS);
        if (!userInfo.isManagedProfile()) {
            throw new java.lang.SecurityException("The user " + userInfo.id + " does not have a parent profile.");
        }
        return new android.app.admin.DevicePolicyManager(this.mContext, this.mService, true);
    }

    public java.util.List<java.lang.String> setMeteredDataDisabledPackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) {
        throwIfParentInstance("setMeteredDataDisabled");
        if (this.mService != null) {
            try {
                return this.mService.setMeteredDataDisabledPackages(componentName, list);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return list;
    }

    public java.util.List<java.lang.String> getMeteredDataDisabledPackages(android.content.ComponentName componentName) {
        throwIfParentInstance("getMeteredDataDisabled");
        if (this.mService != null) {
            try {
                return this.mService.getMeteredDataDisabledPackages(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return new java.util.ArrayList();
    }

    public boolean isMeteredDataDisabledPackageForUser(android.content.ComponentName componentName, java.lang.String str, int i) {
        throwIfParentInstance("getMeteredDataDisabledForUser");
        if (this.mService != null) {
            try {
                return this.mService.isMeteredDataDisabledPackageForUser(componentName, str, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.util.List<android.app.admin.SecurityLog.SecurityEvent> retrievePreRebootSecurityLogs(android.content.ComponentName componentName) {
        throwIfParentInstance("retrievePreRebootSecurityLogs");
        try {
            android.content.pm.ParceledListSlice retrievePreRebootSecurityLogs = this.mService.retrievePreRebootSecurityLogs(componentName, this.mContext.getPackageName());
            if (retrievePreRebootSecurityLogs != null) {
                return retrievePreRebootSecurityLogs.getList();
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void setOrganizationColor(android.content.ComponentName componentName, int i) {
        throwIfParentInstance("setOrganizationColor");
        try {
            this.mService.setOrganizationColor(componentName, i | (-16777216));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void setOrganizationColorForUser(int i, int i2) {
        try {
            this.mService.setOrganizationColorForUser(i | (-16777216), i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int getOrganizationColor(android.content.ComponentName componentName) {
        throwIfParentInstance("getOrganizationColor");
        try {
            return this.mService.getOrganizationColor(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int getOrganizationColorForUser(int i) {
        try {
            return this.mService.getOrganizationColorForUser(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setOrganizationName(android.content.ComponentName componentName, java.lang.CharSequence charSequence) {
        throwIfParentInstance("setOrganizationName");
        try {
            this.mService.setOrganizationName(componentName, this.mContext.getPackageName(), charSequence);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.CharSequence getOrganizationName(android.content.ComponentName componentName) {
        throwIfParentInstance("getOrganizationName");
        try {
            return this.mService.getOrganizationName(componentName, this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Object lambda$new$8(java.lang.Object obj) throws android.os.RemoteException {
        return getService().getDeviceOwnerOrganizationName();
    }

    @android.annotation.SystemApi
    public java.lang.CharSequence getDeviceOwnerOrganizationName() {
        return this.mGetDeviceOwnerOrganizationNameCache.query(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.CharSequence lambda$new$9(java.lang.Integer num) throws android.os.RemoteException {
        return getService().getOrganizationNameForUser(num.intValue());
    }

    public java.lang.CharSequence getOrganizationNameForUser(int i) {
        return this.mGetOrganizationNameForUserCache.query(java.lang.Integer.valueOf(i));
    }

    @android.annotation.SystemApi
    public int getUserProvisioningState() {
        throwIfParentInstance("getUserProvisioningState");
        if (this.mService != null) {
            try {
                return this.mService.getUserProvisioningState(this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public void setUserProvisioningState(int i, int i2) {
        if (this.mService != null) {
            try {
                this.mService.setUserProvisioningState(i, i2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public void setUserProvisioningState(int i, android.os.UserHandle userHandle) {
        setUserProvisioningState(i, userHandle.getIdentifier());
    }

    public void setAffiliationIds(android.content.ComponentName componentName, java.util.Set<java.lang.String> set) {
        throwIfParentInstance("setAffiliationIds");
        if (set == null) {
            throw new java.lang.IllegalArgumentException("ids must not be null");
        }
        try {
            this.mService.setAffiliationIds(componentName, new java.util.ArrayList(set));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Set<java.lang.String> getAffiliationIds(android.content.ComponentName componentName) {
        throwIfParentInstance("getAffiliationIds");
        try {
            return new android.util.ArraySet(this.mService.getAffiliationIds(componentName));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAffiliatedUser() {
        throwIfParentInstance("isAffiliatedUser");
        try {
            return this.mService.isCallingUserAffiliated();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAffiliatedUser(int i) {
        try {
            return this.mService.isAffiliatedUser(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUninstallInQueue(java.lang.String str) {
        try {
            return this.mService.isUninstallInQueue(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void uninstallPackageWithActiveAdmins(java.lang.String str) {
        try {
            this.mService.uninstallPackageWithActiveAdmins(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceRemoveActiveAdmin(android.content.ComponentName componentName, int i) {
        try {
            this.mService.forceRemoveActiveAdmin(componentName, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isDeviceProvisioned() {
        try {
            return this.mService.isDeviceProvisioned();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setDeviceProvisioningConfigApplied() {
        try {
            this.mService.setDeviceProvisioningConfigApplied();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isDeviceProvisioningConfigApplied() {
        try {
            return this.mService.isDeviceProvisioningConfigApplied();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceUpdateUserSetupComplete(int i) {
        try {
            this.mService.forceUpdateUserSetupComplete(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void throwIfParentInstance(java.lang.String str) {
        if (this.mParentInstance) {
            throw new java.lang.SecurityException(str + " cannot be called on the parent instance");
        }
    }

    public void setBackupServiceEnabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setBackupServiceEnabled");
        try {
            this.mService.setBackupServiceEnabled(componentName, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBackupServiceEnabled(android.content.ComponentName componentName) {
        throwIfParentInstance("isBackupServiceEnabled");
        try {
            return this.mService.isBackupServiceEnabled(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNetworkLoggingEnabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setNetworkLoggingEnabled");
        try {
            this.mService.setNetworkLoggingEnabled(componentName, this.mContext.getPackageName(), z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$new$10(android.content.ComponentName componentName) throws android.os.RemoteException {
        return java.lang.Boolean.valueOf(getService().isNetworkLoggingEnabled(componentName, getContext().getPackageName()));
    }

    public boolean isNetworkLoggingEnabled(android.content.ComponentName componentName) {
        throwIfParentInstance("isNetworkLoggingEnabled");
        return this.mIsNetworkLoggingEnabledCache.query(componentName).booleanValue();
    }

    public java.util.List<android.app.admin.NetworkEvent> retrieveNetworkLogs(android.content.ComponentName componentName, long j) {
        throwIfParentInstance("retrieveNetworkLogs");
        try {
            return this.mService.retrieveNetworkLogs(componentName, this.mContext.getPackageName(), j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean bindDeviceAdminServiceAsUser(android.content.ComponentName componentName, android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i, android.os.UserHandle userHandle) {
        throwIfParentInstance("bindDeviceAdminServiceAsUser");
        try {
            android.app.IServiceConnection serviceDispatcher = this.mContext.getServiceDispatcher(serviceConnection, this.mContext.getMainThreadHandler(), java.lang.Integer.toUnsignedLong(i));
            intent.prepareToLeaveProcess(this.mContext);
            return this.mService.bindDeviceAdminServiceAsUser(componentName, this.mContext.getIApplicationThread(), this.mContext.getActivityToken(), intent, serviceDispatcher, java.lang.Integer.toUnsignedLong(i), userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean bindDeviceAdminServiceAsUser(android.content.ComponentName componentName, android.content.Intent intent, android.content.ServiceConnection serviceConnection, android.content.Context.BindServiceFlags bindServiceFlags, android.os.UserHandle userHandle) {
        throwIfParentInstance("bindDeviceAdminServiceAsUser");
        try {
            android.app.IServiceConnection serviceDispatcher = this.mContext.getServiceDispatcher(serviceConnection, this.mContext.getMainThreadHandler(), bindServiceFlags.getValue());
            intent.prepareToLeaveProcess(this.mContext);
            return this.mService.bindDeviceAdminServiceAsUser(componentName, this.mContext.getIApplicationThread(), this.mContext.getActivityToken(), intent, serviceDispatcher, bindServiceFlags.getValue(), userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.os.UserHandle> getBindDeviceAdminTargetUsers(android.content.ComponentName componentName) {
        throwIfParentInstance("getBindDeviceAdminTargetUsers");
        try {
            return this.mService.getBindDeviceAdminTargetUsers(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getLastSecurityLogRetrievalTime() {
        try {
            return this.mService.getLastSecurityLogRetrievalTime();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getLastBugReportRequestTime() {
        try {
            return this.mService.getLastBugReportRequestTime();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getLastNetworkLogRetrievalTime() {
        try {
            return this.mService.getLastNetworkLogRetrievalTime();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCurrentInputMethodSetByOwner() {
        try {
            return this.mService.isCurrentInputMethodSetByOwner();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<java.lang.String> getOwnerInstalledCaCerts(android.os.UserHandle userHandle) {
        try {
            return this.mService.getOwnerInstalledCaCerts(userHandle).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isFactoryResetProtectionPolicySupported() {
        try {
            return this.mService.isFactoryResetProtectionPolicySupported();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearApplicationUserData(android.content.ComponentName componentName, java.lang.String str, java.util.concurrent.Executor executor, android.app.admin.DevicePolicyManager.OnClearApplicationUserDataListener onClearApplicationUserDataListener) {
        throwIfParentInstance("clearAppData");
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onClearApplicationUserDataListener);
        try {
            this.mService.clearApplicationUserData(componentName, str, new android.app.admin.DevicePolicyManager.AnonymousClass2(executor, onClearApplicationUserDataListener));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.admin.DevicePolicyManager$2, reason: invalid class name */
    class AnonymousClass2 extends android.content.pm.IPackageDataObserver.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.app.admin.DevicePolicyManager.OnClearApplicationUserDataListener val$listener;

        AnonymousClass2(java.util.concurrent.Executor executor, android.app.admin.DevicePolicyManager.OnClearApplicationUserDataListener onClearApplicationUserDataListener) {
            this.val$executor = executor;
            this.val$listener = onClearApplicationUserDataListener;
        }

        @Override // android.content.pm.IPackageDataObserver
        public void onRemoveCompleted(final java.lang.String str, final boolean z) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.app.admin.DevicePolicyManager.OnClearApplicationUserDataListener onClearApplicationUserDataListener = this.val$listener;
            executor.execute(new java.lang.Runnable() { // from class: android.app.admin.DevicePolicyManager$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.admin.DevicePolicyManager.OnClearApplicationUserDataListener.this.onApplicationUserDataCleared(str, z);
                }
            });
        }
    }

    public void setLogoutEnabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setLogoutEnabled");
        try {
            this.mService.setLogoutEnabled(componentName, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isLogoutEnabled() {
        throwIfParentInstance("isLogoutEnabled");
        try {
            return this.mService.isLogoutEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Set<java.lang.String> getDisallowedSystemApps(android.content.ComponentName componentName, int i, java.lang.String str) {
        try {
            return new android.util.ArraySet(this.mService.getDisallowedSystemApps(componentName, i, str));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void transferOwnership(android.content.ComponentName componentName, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle) {
        throwIfParentInstance("transferOwnership");
        try {
            this.mService.transferOwnership(componentName, componentName2, persistableBundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setStartUserSessionMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) {
        throwIfParentInstance("setStartUserSessionMessage");
        try {
            this.mService.setStartUserSessionMessage(componentName, charSequence);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setEndUserSessionMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) {
        throwIfParentInstance("setEndUserSessionMessage");
        try {
            this.mService.setEndUserSessionMessage(componentName, charSequence);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.CharSequence getStartUserSessionMessage(android.content.ComponentName componentName) {
        throwIfParentInstance("getStartUserSessionMessage");
        try {
            return this.mService.getStartUserSessionMessage(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.CharSequence getEndUserSessionMessage(android.content.ComponentName componentName) {
        throwIfParentInstance("getEndUserSessionMessage");
        try {
            return this.mService.getEndUserSessionMessage(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int addOverrideApn(android.content.ComponentName componentName, android.telephony.data.ApnSetting apnSetting) {
        throwIfParentInstance("addOverrideApn");
        if (this.mService != null) {
            try {
                return this.mService.addOverrideApn(componentName, apnSetting);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public boolean updateOverrideApn(android.content.ComponentName componentName, int i, android.telephony.data.ApnSetting apnSetting) {
        throwIfParentInstance("updateOverrideApn");
        if (this.mService != null) {
            try {
                return this.mService.updateOverrideApn(componentName, i, apnSetting);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean removeOverrideApn(android.content.ComponentName componentName, int i) {
        throwIfParentInstance("removeOverrideApn");
        if (this.mService != null) {
            try {
                return this.mService.removeOverrideApn(componentName, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.util.List<android.telephony.data.ApnSetting> getOverrideApns(android.content.ComponentName componentName) {
        throwIfParentInstance("getOverrideApns");
        if (this.mService != null) {
            try {
                return this.mService.getOverrideApns(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptyList();
    }

    public void setOverrideApnsEnabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setOverrideApnEnabled");
        if (this.mService != null) {
            try {
                this.mService.setOverrideApnsEnabled(componentName, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isOverrideApnEnabled(android.content.ComponentName componentName) {
        throwIfParentInstance("isOverrideApnEnabled");
        if (this.mService != null) {
            try {
                return this.mService.isOverrideApnEnabled(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public android.os.PersistableBundle getTransferOwnershipBundle() {
        throwIfParentInstance("getTransferOwnershipBundle");
        try {
            return this.mService.getTransferOwnershipBundle();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setGlobalPrivateDnsModeOpportunistic(android.content.ComponentName componentName) {
        throwIfParentInstance("setGlobalPrivateDnsModeOpportunistic");
        if (this.mService == null) {
            return 2;
        }
        try {
            return this.mService.setGlobalPrivateDns(componentName, 2, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setGlobalPrivateDnsModeSpecifiedHost(android.content.ComponentName componentName, java.lang.String str) {
        throwIfParentInstance("setGlobalPrivateDnsModeSpecifiedHost");
        java.util.Objects.requireNonNull(str, "dns resolver is null");
        if (this.mService == null) {
            return 2;
        }
        if (com.android.internal.net.NetworkUtilsInternal.isWeaklyValidatedHostname(str) && !android.net.PrivateDnsConnectivityChecker.canConnectToPrivateDnsServer(str)) {
            return 1;
        }
        try {
            return this.mService.setGlobalPrivateDns(componentName, 3, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void installSystemUpdate(android.content.ComponentName componentName, android.net.Uri uri, final java.util.concurrent.Executor executor, final android.app.admin.DevicePolicyManager.InstallSystemUpdateCallback installSystemUpdateCallback) {
        throwIfParentInstance("installUpdate");
        if (this.mService == null) {
            return;
        }
        try {
            android.os.ParcelFileDescriptor openFileDescriptor = this.mContext.getContentResolver().openFileDescriptor(uri, "r");
            try {
                this.mService.installUpdateFromFile(componentName, this.mContext.getPackageName(), openFileDescriptor, new android.app.admin.StartInstallingUpdateCallback.Stub() { // from class: android.app.admin.DevicePolicyManager.3
                    @Override // android.app.admin.StartInstallingUpdateCallback
                    public void onStartInstallingUpdateError(int i, java.lang.String str) {
                        android.app.admin.DevicePolicyManager.this.executeCallback(i, str, executor, installSystemUpdateCallback);
                    }
                });
                if (openFileDescriptor != null) {
                    openFileDescriptor.close();
                }
            } catch (java.lang.Throwable th) {
                if (openFileDescriptor != null) {
                    try {
                        openFileDescriptor.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.io.FileNotFoundException e2) {
            android.util.Log.w(TAG, e2);
            executeCallback(4, android.util.Log.getStackTraceString(e2), executor, installSystemUpdateCallback);
        } catch (java.io.IOException e3) {
            android.util.Log.w(TAG, e3);
            executeCallback(1, android.util.Log.getStackTraceString(e3), executor, installSystemUpdateCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeCallback(final int i, final java.lang.String str, java.util.concurrent.Executor executor, final android.app.admin.DevicePolicyManager.InstallSystemUpdateCallback installSystemUpdateCallback) {
        executor.execute(new java.lang.Runnable() { // from class: android.app.admin.DevicePolicyManager$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                android.app.admin.DevicePolicyManager.InstallSystemUpdateCallback.this.onInstallUpdateError(i, str);
            }
        });
    }

    public int getGlobalPrivateDnsMode(android.content.ComponentName componentName) {
        throwIfParentInstance("setGlobalPrivateDns");
        if (this.mService == null) {
            return 0;
        }
        try {
            return this.mService.getGlobalPrivateDnsMode(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getGlobalPrivateDnsHost(android.content.ComponentName componentName) {
        throwIfParentInstance("setGlobalPrivateDns");
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getGlobalPrivateDnsHost(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setProfileOwnerCanAccessDeviceIds(android.content.ComponentName componentName) {
        if (this.mContext.getApplicationInfo().targetSdkVersion > 29) {
            throw new java.lang.UnsupportedOperationException("This method is deprecated. use markProfileOwnerOnOrganizationOwnedDevice instead.");
        }
        setProfileOwnerOnOrganizationOwnedDevice(componentName, true);
    }

    public void setProfileOwnerOnOrganizationOwnedDevice(android.content.ComponentName componentName, boolean z) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.setProfileOwnerOnOrganizationOwnedDevice(componentName, myUserId(), z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void setCrossProfileCalendarPackages(android.content.ComponentName componentName, java.util.Set<java.lang.String> set) {
        throwIfParentInstance("setCrossProfileCalendarPackages");
        if (this.mService != null) {
            try {
                this.mService.setCrossProfileCalendarPackages(componentName, set == null ? null : new java.util.ArrayList(set));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public java.util.Set<java.lang.String> getCrossProfileCalendarPackages(android.content.ComponentName componentName) {
        throwIfParentInstance("getCrossProfileCalendarPackages");
        if (this.mService != null) {
            try {
                java.util.List<java.lang.String> crossProfileCalendarPackages = this.mService.getCrossProfileCalendarPackages(componentName);
                if (crossProfileCalendarPackages == null) {
                    return null;
                }
                return new android.util.ArraySet(crossProfileCalendarPackages);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptySet();
    }

    public boolean isPackageAllowedToAccessCalendar(java.lang.String str) {
        throwIfParentInstance("isPackageAllowedToAccessCalendar");
        if (this.mService != null) {
            try {
                return this.mService.isPackageAllowedToAccessCalendarForUser(str, myUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.util.Set<java.lang.String> getCrossProfileCalendarPackages() {
        throwIfParentInstance("getCrossProfileCalendarPackages");
        if (this.mService != null) {
            try {
                java.util.List<java.lang.String> crossProfileCalendarPackagesForUser = this.mService.getCrossProfileCalendarPackagesForUser(myUserId());
                if (crossProfileCalendarPackagesForUser == null) {
                    return null;
                }
                return new android.util.ArraySet(crossProfileCalendarPackagesForUser);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptySet();
    }

    public void setCrossProfilePackages(android.content.ComponentName componentName, java.util.Set<java.lang.String> set) {
        throwIfParentInstance("setCrossProfilePackages");
        if (this.mService != null) {
            try {
                this.mService.setCrossProfilePackages(componentName, new java.util.ArrayList(set));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.util.Set<java.lang.String> getCrossProfilePackages(android.content.ComponentName componentName) {
        throwIfParentInstance("getCrossProfilePackages");
        if (this.mService != null) {
            try {
                return new android.util.ArraySet(this.mService.getCrossProfilePackages(componentName));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptySet();
    }

    public java.util.Set<java.lang.String> getAllCrossProfilePackages() {
        throwIfParentInstance("getAllCrossProfilePackages");
        if (this.mService != null) {
            try {
                return new android.util.ArraySet(this.mService.getAllCrossProfilePackages(this.mContext.getUserId()));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptySet();
    }

    public java.util.Set<java.lang.String> getDefaultCrossProfilePackages() {
        throwIfParentInstance("getDefaultCrossProfilePackages");
        if (this.mService != null) {
            try {
                return new android.util.ArraySet(this.mService.getDefaultCrossProfilePackages());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptySet();
    }

    @android.annotation.SystemApi
    public boolean isManagedKiosk() {
        throwIfParentInstance("isManagedKiosk");
        if (this.mService != null) {
            try {
                return this.mService.isManagedKiosk();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public boolean isUnattendedManagedKiosk() {
        throwIfParentInstance("isUnattendedManagedKiosk");
        if (this.mService != null) {
            try {
                return this.mService.isUnattendedManagedKiosk();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean startViewCalendarEventInManagedProfile(long j, long j2, long j3, boolean z, int i) {
        throwIfParentInstance("startViewCalendarEventInManagedProfile");
        if (this.mService != null) {
            try {
                return this.mService.startViewCalendarEventInManagedProfile(this.mContext.getPackageName(), j, j2, j3, z, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public void setApplicationExemptions(java.lang.String str, java.util.Set<java.lang.Integer> set) throws android.content.pm.PackageManager.NameNotFoundException {
        throwIfParentInstance("setApplicationExemptions");
        if (this.mService != null) {
            try {
                this.mService.setApplicationExemptions(this.mContext.getPackageName(), str, com.android.internal.util.ArrayUtils.convertToIntArray((android.util.ArraySet<java.lang.Integer>) new android.util.ArraySet(set)));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (android.os.ServiceSpecificException e2) {
                switch (e2.errorCode) {
                    case 1:
                        throw new android.content.pm.PackageManager.NameNotFoundException(e2.getMessage());
                    default:
                        throw new java.lang.RuntimeException("Unknown error setting application exemptions: " + e2.errorCode, e2);
                }
            }
        }
    }

    @android.annotation.SystemApi
    public java.util.Set<java.lang.Integer> getApplicationExemptions(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throwIfParentInstance("getApplicationExemptions");
        if (this.mService == null) {
            return java.util.Collections.emptySet();
        }
        try {
            return intArrayToSet(this.mService.getApplicationExemptions(str));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            switch (e2.errorCode) {
                case 1:
                    throw new android.content.pm.PackageManager.NameNotFoundException(e2.getMessage());
                default:
                    throw new java.lang.RuntimeException("Unknown error getting application exemptions: " + e2.errorCode, e2);
            }
        }
    }

    private java.util.Set<java.lang.Integer> intArrayToSet(int[] iArr) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int i : iArr) {
            arraySet.add(java.lang.Integer.valueOf(i));
        }
        return arraySet;
    }

    public void setUserControlDisabledPackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) {
        throwIfParentInstance("setUserControlDisabledPackages");
        if (this.mService != null) {
            try {
                this.mService.setUserControlDisabledPackages(componentName, this.mContext.getPackageName(), list);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.util.List<java.lang.String> getUserControlDisabledPackages(android.content.ComponentName componentName) {
        throwIfParentInstance("getUserControlDisabledPackages");
        if (this.mService != null) {
            try {
                return this.mService.getUserControlDisabledPackages(componentName, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptyList();
    }

    public void setCommonCriteriaModeEnabled(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setCommonCriteriaModeEnabled");
        if (this.mService != null) {
            try {
                this.mService.setCommonCriteriaModeEnabled(componentName, this.mContext.getPackageName(), z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isCommonCriteriaModeEnabled(android.content.ComponentName componentName) {
        throwIfParentInstance("isCommonCriteriaModeEnabled");
        if (this.mService != null) {
            try {
                return this.mService.isCommonCriteriaModeEnabled(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int getPersonalAppsSuspendedReasons(android.content.ComponentName componentName) {
        throwIfParentInstance("getPersonalAppsSuspendedReasons");
        if (this.mService != null) {
            try {
                return this.mService.getPersonalAppsSuspendedReasons(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public void setPersonalAppsSuspended(android.content.ComponentName componentName, boolean z) {
        throwIfParentInstance("setPersonalAppsSuspended");
        if (this.mService != null) {
            try {
                this.mService.setPersonalAppsSuspended(componentName, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setManagedProfileMaximumTimeOff(android.content.ComponentName componentName, long j) {
        throwIfParentInstance("setManagedProfileMaximumTimeOff");
        if (this.mService != null) {
            try {
                this.mService.setManagedProfileMaximumTimeOff(componentName, j);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long getManagedProfileMaximumTimeOff(android.content.ComponentName componentName) {
        throwIfParentInstance("getManagedProfileMaximumTimeOff");
        if (this.mService != null) {
            try {
                return this.mService.getManagedProfileMaximumTimeOff(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0L;
    }

    public void acknowledgeDeviceCompliant() {
        throwIfParentInstance("acknowledgeDeviceCompliant");
        if (this.mService != null) {
            try {
                this.mService.acknowledgeDeviceCompliant();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isComplianceAcknowledgementRequired() {
        throwIfParentInstance("isComplianceAcknowledgementRequired");
        if (this.mService != null) {
            try {
                return this.mService.isComplianceAcknowledgementRequired();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean canProfileOwnerResetPasswordWhenLocked(int i) {
        if (this.mService != null) {
            try {
                return this.mService.canProfileOwnerResetPasswordWhenLocked(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setNextOperationSafety(int i, int i2) {
        if (this.mService != null) {
            try {
                this.mService.setNextOperationSafety(i, i2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.lang.String getEnrollmentSpecificId() {
        throwIfParentInstance("getEnrollmentSpecificId");
        if (this.mService == null) {
            return "";
        }
        try {
            return this.mService.getEnrollmentSpecificId(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setOrganizationId(java.lang.String str) {
        throwIfParentInstance("setOrganizationId");
        setOrganizationIdForUser(this.mContext.getPackageName(), str, myUserId());
    }

    public void setOrganizationIdForUser(java.lang.String str, java.lang.String str2, int i) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.setOrganizationIdForUser(str, str2, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearOrganizationId() {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.clearOrganizationIdForUser(myUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.UserHandle createAndProvisionManagedProfile(android.app.admin.ManagedProfileProvisioningParams managedProfileProvisioningParams) throws android.app.admin.ProvisioningException {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.createAndProvisionManagedProfile(managedProfileProvisioningParams, this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.app.admin.ProvisioningException(e2, e2.errorCode, getErrorMessage(e2));
        }
    }

    @android.annotation.SystemApi
    public void finalizeWorkProfileProvisioning(android.os.UserHandle userHandle, android.accounts.Account account) {
        java.util.Objects.requireNonNull(userHandle, "managedProfileUser can't be null");
        if (this.mService == null) {
            throw new java.lang.IllegalStateException("Could not find DevicePolicyManagerService");
        }
        try {
            this.mService.finalizeWorkProfileProvisioning(userHandle, account);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private java.lang.String getErrorMessage(android.os.ServiceSpecificException serviceSpecificException) {
        return null;
    }

    @android.annotation.SystemApi
    public void provisionFullyManagedDevice(android.app.admin.FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams) throws android.app.admin.ProvisioningException {
        if (this.mService != null) {
            try {
                this.mService.provisionFullyManagedDevice(fullyManagedDeviceProvisioningParams, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (android.os.ServiceSpecificException e2) {
                throw new android.app.admin.ProvisioningException(e2, e2.errorCode, getErrorMessage(e2));
            }
        }
    }

    public void resetDefaultCrossProfileIntentFilters(int i) {
        if (this.mService != null) {
            try {
                this.mService.resetDefaultCrossProfileIntentFilters(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean canAdminGrantSensorsPermissions() {
        throwIfParentInstance("canAdminGrantSensorsPermissions");
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.canAdminGrantSensorsPermissions();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDeviceOwnerType(android.content.ComponentName componentName, int i) {
        throwIfParentInstance("setDeviceOwnerType");
        if (this.mService != null) {
            try {
                this.mService.setDeviceOwnerType(componentName, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public int getDeviceOwnerType(android.content.ComponentName componentName) {
        throwIfParentInstance("getDeviceOwnerType");
        if (this.mService != null) {
            try {
                return this.mService.getDeviceOwnerType(componentName);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public boolean isFinancedDevice() {
        return isDeviceManaged() && getDeviceOwnerType(getDeviceOwnerComponentOnAnyUser()) == 1;
    }

    public void setUsbDataSignalingEnabled(boolean z) {
        throwIfParentInstance("setUsbDataSignalingEnabled");
        if (this.mService != null) {
            try {
                this.mService.setUsbDataSignalingEnabled(this.mContext.getPackageName(), z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isUsbDataSignalingEnabled() {
        throwIfParentInstance("isUsbDataSignalingEnabled");
        if (this.mService != null) {
            try {
                return this.mService.isUsbDataSignalingEnabled(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public boolean canUsbDataSignalingBeDisabled() {
        throwIfParentInstance("canUsbDataSignalingBeDisabled");
        if (this.mService != null) {
            try {
                return this.mService.canUsbDataSignalingBeDisabled();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.util.List<android.os.UserHandle> listForegroundAffiliatedUsers() {
        if (this.mService == null) {
            return java.util.Collections.emptyList();
        }
        try {
            return this.mService.listForegroundAffiliatedUsers();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Set<java.lang.String> getPolicyExemptApps() {
        if (this.mService == null) {
            return java.util.Collections.emptySet();
        }
        try {
            return new java.util.HashSet(this.mService.listPolicyExemptApps());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.content.Intent createProvisioningIntentFromNfcIntent(android.content.Intent intent) {
        return android.app.admin.ProvisioningIntentHelper.createProvisioningIntentFromNfcIntent(intent);
    }

    public void setMinimumRequiredWifiSecurityLevel(int i) {
        throwIfParentInstance("setMinimumRequiredWifiSecurityLevel");
        if (this.mService != null) {
            try {
                this.mService.setMinimumRequiredWifiSecurityLevel(this.mContext.getPackageName(), i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getMinimumRequiredWifiSecurityLevel() {
        throwIfParentInstance("getMinimumRequiredWifiSecurityLevel");
        if (this.mService == null) {
            return 0;
        }
        try {
            return this.mService.getMinimumRequiredWifiSecurityLevel();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiSsidPolicy(android.app.admin.WifiSsidPolicy wifiSsidPolicy) {
        throwIfParentInstance("setWifiSsidPolicy");
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.setWifiSsidPolicy(this.mContext.getPackageName(), wifiSsidPolicy);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.admin.WifiSsidPolicy getWifiSsidPolicy() {
        throwIfParentInstance("getWifiSsidPolicy");
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getWifiSsidPolicy(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isTheftDetectionTriggered() {
        throwIfParentInstance("isTheftDetectionTriggered");
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.isTheftDetectionTriggered(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.admin.DevicePolicyResourcesManager getResources() {
        return this.mResourcesManager;
    }

    @android.annotation.SystemApi
    public boolean isDpcDownloaded() {
        throwIfParentInstance("isDpcDownloaded");
        if (this.mService != null) {
            try {
                return this.mService.isDpcDownloaded();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public void setDpcDownloaded(boolean z) {
        throwIfParentInstance("setDpcDownloaded");
        if (this.mService != null) {
            try {
                this.mService.setDpcDownloaded(z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.lang.String getDevicePolicyManagementRoleHolderPackage() {
        return extractPackageNameFromDeviceManagerConfig(this.mContext.getString(17039421));
    }

    public java.lang.String getDevicePolicyManagementRoleHolderUpdaterPackage() {
        java.lang.String string = this.mContext.getString(com.android.internal.R.string.config_devicePolicyManagementUpdater);
        if (android.text.TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    @android.annotation.SystemApi
    public java.util.List<android.os.UserHandle> getPolicyManagedProfiles(android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(userHandle);
        if (this.mService != null) {
            try {
                return this.mService.getPolicyManagedProfiles(userHandle);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptyList();
    }

    private java.lang.String extractPackageNameFromDeviceManagerConfig(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.contains(":")) {
            return str.split(":")[0];
        }
        return str;
    }

    public void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState() {
        if (this.mService != null) {
            try {
                this.mService.resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void calculateHasIncompatibleAccounts() {
        if (this.mService != null) {
            try {
                this.mService.calculateHasIncompatibleAccounts();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public boolean shouldAllowBypassingDevicePolicyManagementRoleQualification() {
        if (this.mService != null) {
            try {
                return this.mService.shouldAllowBypassingDevicePolicyManagementRoleQualification();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public android.app.admin.DevicePolicyState getDevicePolicyState() {
        if (this.mService != null) {
            try {
                return this.mService.getDevicePolicyState();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public boolean triggerDevicePolicyEngineMigration(boolean z) {
        if (this.mService != null) {
            try {
                return this.mService.triggerDevicePolicyEngineMigration(z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isDeviceFinanced() {
        throwIfParentInstance("isDeviceFinanced");
        if (this.mService != null) {
            try {
                return this.mService.isDeviceFinanced(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public java.lang.String getFinancedDeviceKioskRoleHolder() {
        if (this.mService != null) {
            try {
                return this.mService.getFinancedDeviceKioskRoleHolder(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public boolean isOnboardingBugreportV2FlagEnabled() {
        return android.app.admin.flags.Flags.onboardingBugreportV2Enabled();
    }

    public java.util.Set<java.lang.Integer> getSubscriptionsIds() {
        throwIfParentInstance("getSubscriptionsIds");
        if (this.mService != null) {
            try {
                return intArrayToSet(this.mService.getSubscriptionIds(this.mContext.getPackageName()));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return new java.util.HashSet();
    }

    @android.annotation.SystemApi
    public void setMaxPolicyStorageLimit(int i) {
        if (this.mService != null) {
            try {
                this.mService.setMaxPolicyStorageLimit(this.mContext.getPackageName(), i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public int getMaxPolicyStorageLimit() {
        if (this.mService != null) {
            try {
                return this.mService.getMaxPolicyStorageLimit(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public boolean requireSecureKeyguard() {
        return requireSecureKeyguard(android.os.UserHandle.myUserId());
    }

    public boolean requireSecureKeyguard(int i) {
        if (this.mService != null) {
            try {
                return this.mService.requireSecureKeyguard(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failed to get secure keyguard requirement");
                return true;
            }
        }
        return true;
    }
}
