package com.android.server.devicepolicy;

/* loaded from: classes.dex */
class ActiveAdmin {
    private static final java.lang.String ATTR_LAST_NETWORK_LOGGING_NOTIFICATION = "last-notification";
    private static final java.lang.String ATTR_NUM_NETWORK_LOGGING_NOTIFICATIONS = "num-notifications";
    private static final java.lang.String ATTR_PACKAGE_POLICY_MODE = "package-policy-type";
    private static final java.lang.String ATTR_VALUE = "value";
    static final int DEF_KEYGUARD_FEATURES_DISABLED = 0;
    static final int DEF_MAXIMUM_FAILED_PASSWORDS_FOR_WIPE = 0;
    static final int DEF_MAXIMUM_NETWORK_LOGGING_NOTIFICATIONS_SHOWN = 2;
    static final long DEF_MAXIMUM_TIME_TO_UNLOCK = 0;
    static final int DEF_ORGANIZATION_COLOR = android.graphics.Color.parseColor("#00796B");
    static final long DEF_PASSWORD_EXPIRATION_DATE = 0;
    static final long DEF_PASSWORD_EXPIRATION_TIMEOUT = 0;
    static final int DEF_PASSWORD_HISTORY_LENGTH = 0;
    private static final java.lang.String TAG_ACCOUNT_TYPE = "account-type";
    private static final java.lang.String TAG_ADMIN_CAN_GRANT_SENSORS_PERMISSIONS = "admin-can-grant-sensors-permissions";
    private static final java.lang.String TAG_ALWAYS_ON_VPN_LOCKDOWN = "vpn-lockdown";
    private static final java.lang.String TAG_ALWAYS_ON_VPN_PACKAGE = "vpn-package";
    private static final java.lang.String TAG_COMMON_CRITERIA_MODE = "common-criteria-mode";
    private static final java.lang.String TAG_CREDENTIAL_MANAGER_POLICY = "credential-manager-policy";
    private static final java.lang.String TAG_CROSS_PROFILE_CALENDAR_PACKAGES = "cross-profile-calendar-packages";
    private static final java.lang.String TAG_CROSS_PROFILE_CALENDAR_PACKAGES_NULL = "cross-profile-calendar-packages-null";
    private static final java.lang.String TAG_CROSS_PROFILE_CALLER_ID_POLICY = "caller-id-policy";
    private static final java.lang.String TAG_CROSS_PROFILE_CONTACTS_SEARCH_POLICY = "contacts-policy";
    private static final java.lang.String TAG_CROSS_PROFILE_PACKAGES = "cross-profile-packages";
    private static final java.lang.String TAG_CROSS_PROFILE_WIDGET_PROVIDERS = "cross-profile-widget-providers";
    private static final java.lang.String TAG_DEFAULT_ENABLED_USER_RESTRICTIONS = "default-enabled-user-restrictions";
    private static final java.lang.String TAG_DIALER_PACKAGE = "dialer_package";
    private static final java.lang.String TAG_DISABLE_ACCOUNT_MANAGEMENT = "disable-account-management";
    private static final java.lang.String TAG_DISABLE_BLUETOOTH_CONTACT_SHARING = "disable-bt-contacts-sharing";
    private static final java.lang.String TAG_DISABLE_CALLER_ID = "disable-caller-id";
    private static final java.lang.String TAG_DISABLE_CAMERA = "disable-camera";
    private static final java.lang.String TAG_DISABLE_CONTACTS_SEARCH = "disable-contacts-search";
    private static final java.lang.String TAG_DISABLE_KEYGUARD_FEATURES = "disable-keyguard-features";
    private static final java.lang.String TAG_DISABLE_SCREEN_CAPTURE = "disable-screen-capture";
    private static final java.lang.String TAG_ENCRYPTION_REQUESTED = "encryption-requested";
    private static final java.lang.String TAG_END_USER_SESSION_MESSAGE = "end_user_session_message";
    private static final java.lang.String TAG_ENROLLMENT_SPECIFIC_ID = "enrollment-specific-id";
    private static final java.lang.String TAG_FACTORY_RESET_PROTECTION_POLICY = "factory_reset_protection_policy";
    private static final java.lang.String TAG_FORCE_EPHEMERAL_USERS = "force_ephemeral_users";
    private static final java.lang.String TAG_GLOBAL_PROXY_EXCLUSION_LIST = "global-proxy-exclusion-list";
    private static final java.lang.String TAG_GLOBAL_PROXY_SPEC = "global-proxy-spec";
    private static final java.lang.String TAG_IS_LOGOUT_ENABLED = "is_logout_enabled";
    private static final java.lang.String TAG_IS_NETWORK_LOGGING_ENABLED = "is_network_logging_enabled";
    private static final java.lang.String TAG_KEEP_UNINSTALLED_PACKAGES = "keep-uninstalled-packages";
    private static final java.lang.String TAG_LONG_SUPPORT_MESSAGE = "long-support-message";
    private static final java.lang.String TAG_MANAGED_SUBSCRIPTIONS_POLICY = "managed_subscriptions_policy";
    private static final java.lang.String TAG_MANAGE_TRUST_AGENT_FEATURES = "manage-trust-agent-features";
    private static final java.lang.String TAG_MAX_FAILED_PASSWORD_WIPE = "max-failed-password-wipe";
    private static final java.lang.String TAG_MAX_TIME_TO_UNLOCK = "max-time-to-unlock";
    private static final java.lang.String TAG_METERED_DATA_DISABLED_PACKAGES = "metered_data_disabled_packages";
    private static final java.lang.String TAG_MIN_PASSWORD_LENGTH = "min-password-length";
    private static final java.lang.String TAG_MIN_PASSWORD_LETTERS = "min-password-letters";
    private static final java.lang.String TAG_MIN_PASSWORD_LOWERCASE = "min-password-lowercase";
    private static final java.lang.String TAG_MIN_PASSWORD_NONLETTER = "min-password-nonletter";
    private static final java.lang.String TAG_MIN_PASSWORD_NUMERIC = "min-password-numeric";
    private static final java.lang.String TAG_MIN_PASSWORD_SYMBOLS = "min-password-symbols";
    private static final java.lang.String TAG_MIN_PASSWORD_UPPERCASE = "min-password-uppercase";
    private static final java.lang.String TAG_MTE_POLICY = "mte-policy";
    private static final java.lang.String TAG_NEARBY_APP_STREAMING_POLICY = "nearby-app-streaming-policy";
    private static final java.lang.String TAG_NEARBY_NOTIFICATION_STREAMING_POLICY = "nearby-notification-streaming-policy";
    private static final java.lang.String TAG_ORGANIZATION_COLOR = "organization-color";
    private static final java.lang.String TAG_ORGANIZATION_ID = "organization-id";
    private static final java.lang.String TAG_ORGANIZATION_NAME = "organization-name";
    private static final java.lang.String TAG_PACKAGE_LIST_ITEM = "item";
    private static final java.lang.String TAG_PACKAGE_POLICY_PACKAGE_NAMES = "package-policy-packages";
    private static final java.lang.String TAG_PARENT_ADMIN = "parent-admin";
    private static final java.lang.String TAG_PASSWORD_COMPLEXITY = "password-complexity";
    private static final java.lang.String TAG_PASSWORD_EXPIRATION_DATE = "password-expiration-date";
    private static final java.lang.String TAG_PASSWORD_EXPIRATION_TIMEOUT = "password-expiration-timeout";
    private static final java.lang.String TAG_PASSWORD_HISTORY_LENGTH = "password-history-length";
    private static final java.lang.String TAG_PASSWORD_QUALITY = "password-quality";
    private static final java.lang.String TAG_PERMITTED_ACCESSIBILITY_SERVICES = "permitted-accessiblity-services";
    private static final java.lang.String TAG_PERMITTED_IMES = "permitted-imes";
    private static final java.lang.String TAG_PERMITTED_NOTIFICATION_LISTENERS = "permitted-notification-listeners";
    private static final java.lang.String TAG_POLICIES = "policies";
    private static final java.lang.String TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIG = "preferential_network_service_config";
    private static final java.lang.String TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIGS = "preferential_network_service_configs";
    private static final java.lang.String TAG_PREFERENTIAL_NETWORK_SERVICE_ENABLED = "preferential-network-service-enabled";
    private static final java.lang.String TAG_PROFILE_MAXIMUM_TIME_OFF = "profile-max-time-off";
    private static final java.lang.String TAG_PROFILE_OFF_DEADLINE = "profile-off-deadline";
    private static final java.lang.String TAG_PROTECTED_PACKAGES = "protected_packages";
    private static final java.lang.String TAG_PROVIDER = "provider";
    private static final java.lang.String TAG_REQUIRE_AUTO_TIME = "require_auto_time";
    private static final java.lang.String TAG_RESTRICTION = "restriction";
    private static final java.lang.String TAG_SHORT_SUPPORT_MESSAGE = "short-support-message";
    private static final java.lang.String TAG_SMS_PACKAGE = "sms_package";
    private static final java.lang.String TAG_SPECIFIES_GLOBAL_PROXY = "specifies-global-proxy";
    private static final java.lang.String TAG_SSID = "ssid";
    private static final java.lang.String TAG_SSID_ALLOWLIST = "ssid-allowlist";
    private static final java.lang.String TAG_SSID_DENYLIST = "ssid-denylist";
    private static final java.lang.String TAG_START_USER_SESSION_MESSAGE = "start_user_session_message";
    private static final java.lang.String TAG_STRONG_AUTH_UNLOCK_TIMEOUT = "strong-auth-unlock-timeout";
    private static final java.lang.String TAG_SUSPENDED_PACKAGES = "suspended-packages";
    private static final java.lang.String TAG_SUSPEND_PERSONAL_APPS = "suspend-personal-apps";
    private static final java.lang.String TAG_TEST_ONLY_ADMIN = "test-only-admin";
    private static final java.lang.String TAG_TRUST_AGENT_COMPONENT = "component";
    private static final java.lang.String TAG_TRUST_AGENT_COMPONENT_OPTIONS = "trust-agent-component-options";
    private static final java.lang.String TAG_USB_DATA_SIGNALING = "usb-data-signaling";
    private static final java.lang.String TAG_USER_RESTRICTIONS = "user-restrictions";
    private static final java.lang.String TAG_WIFI_MIN_SECURITY = "wifi-min-security";
    private static final boolean USB_DATA_SIGNALING_ENABLED_DEFAULT = true;
    final java.util.Set<java.lang.String> accountTypesWithManagementDisabled;
    java.util.List<java.lang.String> crossProfileWidgetProviders;
    final java.util.Set<java.lang.String> defaultEnabledRestrictionsAlreadySet;
    boolean disableBluetoothContactSharing;
    boolean disableCallerId;
    boolean disableCamera;
    boolean disableContactsSearch;
    boolean disableScreenCapture;
    int disabledKeyguardFeatures;
    boolean encryptionRequested;
    java.lang.String endUserSessionMessage;
    boolean forceEphemeralUsers;
    java.lang.String globalProxyExclusionList;
    java.lang.String globalProxySpec;
    android.app.admin.DeviceAdminInfo info;
    boolean isLogoutEnabled;
    boolean isNetworkLoggingEnabled;
    final boolean isParent;
    public final boolean isPermissionBased;
    java.util.List<java.lang.String> keepUninstalledPackages;
    long lastNetworkLoggingNotificationTimeMs;
    java.lang.CharSequence longSupportMessage;
    public boolean mAdminCanGrantSensorsPermissions;
    public boolean mAlwaysOnVpnLockdown;
    public java.lang.String mAlwaysOnVpnPackage;
    boolean mCommonCriteriaMode;
    android.app.admin.PackagePolicy mCredentialManagerPolicy;
    java.util.List<java.lang.String> mCrossProfileCalendarPackages;
    java.util.List<java.lang.String> mCrossProfilePackages;
    java.lang.String mDialerPackage;
    public java.lang.String mEnrollmentSpecificId;

    @android.annotation.Nullable
    android.app.admin.FactoryResetProtectionPolicy mFactoryResetProtectionPolicy;
    android.app.admin.PackagePolicy mManagedProfileCallerIdAccess;
    android.app.admin.PackagePolicy mManagedProfileContactsAccess;
    android.app.admin.ManagedSubscriptionsPolicy mManagedSubscriptionsPolicy;
    int mNearbyAppStreamingPolicy;
    int mNearbyNotificationStreamingPolicy;
    public java.lang.String mOrganizationId;
    int mPasswordComplexity;

    @android.annotation.NonNull
    android.app.admin.PasswordPolicy mPasswordPolicy;
    public java.util.List<android.app.admin.PreferentialNetworkServiceConfig> mPreferentialNetworkServiceConfigs;
    long mProfileMaximumTimeOffMillis;
    long mProfileOffDeadline;
    java.lang.String mSmsPackage;
    boolean mSuspendPersonalApps;
    boolean mUsbDataSignalingEnabled;
    int mWifiMinimumSecurityLevel;
    android.app.admin.WifiSsidPolicy mWifiSsidPolicy;
    int maximumFailedPasswordsForWipe;
    long maximumTimeToUnlock;
    java.util.List<java.lang.String> meteredDisabledPackages;
    int mtePolicy;
    int numNetworkLoggingNotifications;
    int organizationColor;
    java.lang.String organizationName;
    com.android.server.devicepolicy.ActiveAdmin parentAdmin;
    long passwordExpirationDate;
    long passwordExpirationTimeout;
    int passwordHistoryLength;
    java.util.List<java.lang.String> permittedAccessiblityServices;
    java.util.List<java.lang.String> permittedInputMethods;
    java.util.List<java.lang.String> permittedNotificationListeners;
    java.util.List<java.lang.String> protectedPackages;
    boolean requireAutoTime;
    java.lang.CharSequence shortSupportMessage;
    boolean specifiesGlobalProxy;
    java.lang.String startUserSessionMessage;
    long strongAuthUnlockTimeout;
    java.util.List<java.lang.String> suspendedPackages;
    boolean testOnlyAdmin;

    @android.annotation.NonNull
    android.util.ArrayMap<java.lang.String, com.android.server.devicepolicy.ActiveAdmin.TrustAgentInfo> trustAgentInfos;
    private final int userId;
    android.os.Bundle userRestrictions;

    static class TrustAgentInfo {
        public android.os.PersistableBundle options;

        TrustAgentInfo(android.os.PersistableBundle persistableBundle) {
            this.options = persistableBundle;
        }
    }

    ActiveAdmin(android.app.admin.DeviceAdminInfo deviceAdminInfo, boolean z) {
        this.passwordHistoryLength = 0;
        this.mPasswordPolicy = new android.app.admin.PasswordPolicy();
        this.mPasswordComplexity = 0;
        this.mNearbyNotificationStreamingPolicy = 3;
        this.mNearbyAppStreamingPolicy = 3;
        this.mFactoryResetProtectionPolicy = null;
        this.maximumTimeToUnlock = 0L;
        this.strongAuthUnlockTimeout = 0L;
        this.maximumFailedPasswordsForWipe = 0;
        this.passwordExpirationTimeout = 0L;
        this.passwordExpirationDate = 0L;
        this.disabledKeyguardFeatures = 0;
        this.encryptionRequested = false;
        this.testOnlyAdmin = false;
        this.disableCamera = false;
        this.disableCallerId = false;
        this.disableContactsSearch = false;
        this.disableBluetoothContactSharing = true;
        this.disableScreenCapture = false;
        this.requireAutoTime = false;
        this.forceEphemeralUsers = false;
        this.isNetworkLoggingEnabled = false;
        this.isLogoutEnabled = false;
        this.numNetworkLoggingNotifications = 0;
        this.lastNetworkLoggingNotificationTimeMs = 0L;
        this.mtePolicy = 0;
        this.accountTypesWithManagementDisabled = new android.util.ArraySet();
        this.specifiesGlobalProxy = false;
        this.globalProxySpec = null;
        this.globalProxyExclusionList = null;
        this.trustAgentInfos = new android.util.ArrayMap<>();
        this.defaultEnabledRestrictionsAlreadySet = new android.util.ArraySet();
        this.shortSupportMessage = null;
        this.longSupportMessage = null;
        this.organizationColor = DEF_ORGANIZATION_COLOR;
        this.organizationName = null;
        this.startUserSessionMessage = null;
        this.endUserSessionMessage = null;
        this.mCrossProfileCalendarPackages = java.util.Collections.emptyList();
        this.mCrossProfilePackages = java.util.Collections.emptyList();
        this.mSuspendPersonalApps = false;
        this.mProfileMaximumTimeOffMillis = 0L;
        this.mProfileOffDeadline = 0L;
        this.mManagedProfileCallerIdAccess = null;
        this.mManagedProfileContactsAccess = null;
        this.mCredentialManagerPolicy = null;
        this.mPreferentialNetworkServiceConfigs = java.util.List.of(android.app.admin.PreferentialNetworkServiceConfig.DEFAULT);
        this.mUsbDataSignalingEnabled = true;
        this.mWifiMinimumSecurityLevel = 0;
        this.userId = -1;
        this.info = deviceAdminInfo;
        this.isParent = z;
        this.isPermissionBased = false;
    }

    ActiveAdmin(int i, boolean z) {
        this.passwordHistoryLength = 0;
        this.mPasswordPolicy = new android.app.admin.PasswordPolicy();
        this.mPasswordComplexity = 0;
        this.mNearbyNotificationStreamingPolicy = 3;
        this.mNearbyAppStreamingPolicy = 3;
        this.mFactoryResetProtectionPolicy = null;
        this.maximumTimeToUnlock = 0L;
        this.strongAuthUnlockTimeout = 0L;
        this.maximumFailedPasswordsForWipe = 0;
        this.passwordExpirationTimeout = 0L;
        this.passwordExpirationDate = 0L;
        this.disabledKeyguardFeatures = 0;
        this.encryptionRequested = false;
        this.testOnlyAdmin = false;
        this.disableCamera = false;
        this.disableCallerId = false;
        this.disableContactsSearch = false;
        this.disableBluetoothContactSharing = true;
        this.disableScreenCapture = false;
        this.requireAutoTime = false;
        this.forceEphemeralUsers = false;
        this.isNetworkLoggingEnabled = false;
        this.isLogoutEnabled = false;
        this.numNetworkLoggingNotifications = 0;
        this.lastNetworkLoggingNotificationTimeMs = 0L;
        this.mtePolicy = 0;
        this.accountTypesWithManagementDisabled = new android.util.ArraySet();
        this.specifiesGlobalProxy = false;
        this.globalProxySpec = null;
        this.globalProxyExclusionList = null;
        this.trustAgentInfos = new android.util.ArrayMap<>();
        this.defaultEnabledRestrictionsAlreadySet = new android.util.ArraySet();
        this.shortSupportMessage = null;
        this.longSupportMessage = null;
        this.organizationColor = DEF_ORGANIZATION_COLOR;
        this.organizationName = null;
        this.startUserSessionMessage = null;
        this.endUserSessionMessage = null;
        this.mCrossProfileCalendarPackages = java.util.Collections.emptyList();
        this.mCrossProfilePackages = java.util.Collections.emptyList();
        this.mSuspendPersonalApps = false;
        this.mProfileMaximumTimeOffMillis = 0L;
        this.mProfileOffDeadline = 0L;
        this.mManagedProfileCallerIdAccess = null;
        this.mManagedProfileContactsAccess = null;
        this.mCredentialManagerPolicy = null;
        this.mPreferentialNetworkServiceConfigs = java.util.List.of(android.app.admin.PreferentialNetworkServiceConfig.DEFAULT);
        this.mUsbDataSignalingEnabled = true;
        this.mWifiMinimumSecurityLevel = 0;
        if (!z) {
            throw new java.lang.IllegalArgumentException("Can only pass true for permissionBased admin");
        }
        this.userId = i;
        this.isPermissionBased = z;
        this.isParent = false;
        this.info = null;
    }

    com.android.server.devicepolicy.ActiveAdmin getParentActiveAdmin() {
        com.android.internal.util.Preconditions.checkState(!this.isParent);
        if (this.parentAdmin == null) {
            this.parentAdmin = new com.android.server.devicepolicy.ActiveAdmin(this.info, true);
        }
        return this.parentAdmin;
    }

    boolean hasParentActiveAdmin() {
        return this.parentAdmin != null;
    }

    int getUid() {
        if (this.isPermissionBased) {
            return -1;
        }
        return this.info.getActivityInfo().applicationInfo.uid;
    }

    public android.os.UserHandle getUserHandle() {
        if (this.isPermissionBased) {
            return android.os.UserHandle.of(this.userId);
        }
        return android.os.UserHandle.of(android.os.UserHandle.getUserId(this.info.getActivityInfo().applicationInfo.uid));
    }

    void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        if (this.info != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_POLICIES);
            this.info.writePoliciesToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_POLICIES);
        }
        if (this.mPasswordPolicy.quality != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_PASSWORD_QUALITY, this.mPasswordPolicy.quality);
            if (this.mPasswordPolicy.length != 0) {
                writeAttributeValueToXml(typedXmlSerializer, TAG_MIN_PASSWORD_LENGTH, this.mPasswordPolicy.length);
            }
            if (this.mPasswordPolicy.upperCase != 0) {
                writeAttributeValueToXml(typedXmlSerializer, TAG_MIN_PASSWORD_UPPERCASE, this.mPasswordPolicy.upperCase);
            }
            if (this.mPasswordPolicy.lowerCase != 0) {
                writeAttributeValueToXml(typedXmlSerializer, TAG_MIN_PASSWORD_LOWERCASE, this.mPasswordPolicy.lowerCase);
            }
            if (this.mPasswordPolicy.letters != 1) {
                writeAttributeValueToXml(typedXmlSerializer, TAG_MIN_PASSWORD_LETTERS, this.mPasswordPolicy.letters);
            }
            if (this.mPasswordPolicy.numeric != 1) {
                writeAttributeValueToXml(typedXmlSerializer, TAG_MIN_PASSWORD_NUMERIC, this.mPasswordPolicy.numeric);
            }
            if (this.mPasswordPolicy.symbols != 1) {
                writeAttributeValueToXml(typedXmlSerializer, TAG_MIN_PASSWORD_SYMBOLS, this.mPasswordPolicy.symbols);
            }
            if (this.mPasswordPolicy.nonLetter > 0) {
                writeAttributeValueToXml(typedXmlSerializer, TAG_MIN_PASSWORD_NONLETTER, this.mPasswordPolicy.nonLetter);
            }
        }
        if (this.passwordHistoryLength != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_PASSWORD_HISTORY_LENGTH, this.passwordHistoryLength);
        }
        if (this.maximumTimeToUnlock != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_MAX_TIME_TO_UNLOCK, this.maximumTimeToUnlock);
        }
        if (this.strongAuthUnlockTimeout != 259200000) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_STRONG_AUTH_UNLOCK_TIMEOUT, this.strongAuthUnlockTimeout);
        }
        if (this.maximumFailedPasswordsForWipe != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_MAX_FAILED_PASSWORD_WIPE, this.maximumFailedPasswordsForWipe);
        }
        if (this.specifiesGlobalProxy) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_SPECIFIES_GLOBAL_PROXY, this.specifiesGlobalProxy);
            if (this.globalProxySpec != null) {
                writeAttributeValueToXml(typedXmlSerializer, TAG_GLOBAL_PROXY_SPEC, this.globalProxySpec);
            }
            if (this.globalProxyExclusionList != null) {
                writeAttributeValueToXml(typedXmlSerializer, TAG_GLOBAL_PROXY_EXCLUSION_LIST, this.globalProxyExclusionList);
            }
        }
        if (this.passwordExpirationTimeout != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_PASSWORD_EXPIRATION_TIMEOUT, this.passwordExpirationTimeout);
        }
        if (this.passwordExpirationDate != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_PASSWORD_EXPIRATION_DATE, this.passwordExpirationDate);
        }
        if (this.encryptionRequested) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_ENCRYPTION_REQUESTED, this.encryptionRequested);
        }
        if (this.testOnlyAdmin) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_TEST_ONLY_ADMIN, this.testOnlyAdmin);
        }
        if (this.disableCamera) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_DISABLE_CAMERA, this.disableCamera);
        }
        if (this.disableCallerId) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_DISABLE_CALLER_ID, this.disableCallerId);
        }
        if (this.disableContactsSearch) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_DISABLE_CONTACTS_SEARCH, this.disableContactsSearch);
        }
        if (!this.disableBluetoothContactSharing) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_DISABLE_BLUETOOTH_CONTACT_SHARING, this.disableBluetoothContactSharing);
        }
        if (this.disableScreenCapture) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_DISABLE_SCREEN_CAPTURE, this.disableScreenCapture);
        }
        if (this.requireAutoTime) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_REQUIRE_AUTO_TIME, this.requireAutoTime);
        }
        if (this.forceEphemeralUsers) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_FORCE_EPHEMERAL_USERS, this.forceEphemeralUsers);
        }
        if (this.isNetworkLoggingEnabled) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_IS_NETWORK_LOGGING_ENABLED);
            typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_VALUE, this.isNetworkLoggingEnabled);
            typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_NUM_NETWORK_LOGGING_NOTIFICATIONS, this.numNetworkLoggingNotifications);
            typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_LAST_NETWORK_LOGGING_NOTIFICATION, this.lastNetworkLoggingNotificationTimeMs);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_IS_NETWORK_LOGGING_ENABLED);
        }
        if (this.disabledKeyguardFeatures != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_DISABLE_KEYGUARD_FEATURES, this.disabledKeyguardFeatures);
        }
        if (!this.accountTypesWithManagementDisabled.isEmpty()) {
            writeAttributeValuesToXml(typedXmlSerializer, TAG_DISABLE_ACCOUNT_MANAGEMENT, TAG_ACCOUNT_TYPE, this.accountTypesWithManagementDisabled);
        }
        if (!this.trustAgentInfos.isEmpty()) {
            java.util.Set<java.util.Map.Entry<java.lang.String, com.android.server.devicepolicy.ActiveAdmin.TrustAgentInfo>> entrySet = this.trustAgentInfos.entrySet();
            typedXmlSerializer.startTag((java.lang.String) null, TAG_MANAGE_TRUST_AGENT_FEATURES);
            for (java.util.Map.Entry<java.lang.String, com.android.server.devicepolicy.ActiveAdmin.TrustAgentInfo> entry : entrySet) {
                com.android.server.devicepolicy.ActiveAdmin.TrustAgentInfo value = entry.getValue();
                typedXmlSerializer.startTag((java.lang.String) null, TAG_TRUST_AGENT_COMPONENT);
                typedXmlSerializer.attribute((java.lang.String) null, ATTR_VALUE, entry.getKey());
                if (value.options != null) {
                    typedXmlSerializer.startTag((java.lang.String) null, TAG_TRUST_AGENT_COMPONENT_OPTIONS);
                    try {
                        value.options.saveToXml(typedXmlSerializer);
                    } catch (org.xmlpull.v1.XmlPullParserException e) {
                        com.android.server.utils.Slogf.e("DevicePolicyManager", e, "Failed to save TrustAgent options", new java.lang.Object[0]);
                    }
                    typedXmlSerializer.endTag((java.lang.String) null, TAG_TRUST_AGENT_COMPONENT_OPTIONS);
                }
                typedXmlSerializer.endTag((java.lang.String) null, TAG_TRUST_AGENT_COMPONENT);
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_MANAGE_TRUST_AGENT_FEATURES);
        }
        if (this.crossProfileWidgetProviders != null && !this.crossProfileWidgetProviders.isEmpty()) {
            writeAttributeValuesToXml(typedXmlSerializer, TAG_CROSS_PROFILE_WIDGET_PROVIDERS, TAG_PROVIDER, this.crossProfileWidgetProviders);
        }
        writePackageListToXml(typedXmlSerializer, TAG_PERMITTED_ACCESSIBILITY_SERVICES, this.permittedAccessiblityServices);
        writePackageListToXml(typedXmlSerializer, TAG_PERMITTED_IMES, this.permittedInputMethods);
        writePackageListToXml(typedXmlSerializer, TAG_PERMITTED_NOTIFICATION_LISTENERS, this.permittedNotificationListeners);
        writePackageListToXml(typedXmlSerializer, TAG_KEEP_UNINSTALLED_PACKAGES, this.keepUninstalledPackages);
        writePackageListToXml(typedXmlSerializer, TAG_METERED_DATA_DISABLED_PACKAGES, this.meteredDisabledPackages);
        writePackageListToXml(typedXmlSerializer, TAG_PROTECTED_PACKAGES, this.protectedPackages);
        writePackageListToXml(typedXmlSerializer, TAG_SUSPENDED_PACKAGES, this.suspendedPackages);
        if (hasUserRestrictions()) {
            com.android.server.pm.UserRestrictionsUtils.writeRestrictions(typedXmlSerializer, this.userRestrictions, TAG_USER_RESTRICTIONS);
        }
        if (!this.defaultEnabledRestrictionsAlreadySet.isEmpty()) {
            writeAttributeValuesToXml(typedXmlSerializer, TAG_DEFAULT_ENABLED_USER_RESTRICTIONS, TAG_RESTRICTION, this.defaultEnabledRestrictionsAlreadySet);
        }
        if (!android.text.TextUtils.isEmpty(this.shortSupportMessage)) {
            writeTextToXml(typedXmlSerializer, TAG_SHORT_SUPPORT_MESSAGE, this.shortSupportMessage.toString());
        }
        if (!android.text.TextUtils.isEmpty(this.longSupportMessage)) {
            writeTextToXml(typedXmlSerializer, TAG_LONG_SUPPORT_MESSAGE, this.longSupportMessage.toString());
        }
        if (this.parentAdmin != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_PARENT_ADMIN);
            this.parentAdmin.writeToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_PARENT_ADMIN);
        }
        if (this.organizationColor != DEF_ORGANIZATION_COLOR) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_ORGANIZATION_COLOR, this.organizationColor);
        }
        if (this.organizationName != null) {
            writeTextToXml(typedXmlSerializer, TAG_ORGANIZATION_NAME, this.organizationName);
        }
        if (this.isLogoutEnabled) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_IS_LOGOUT_ENABLED, this.isLogoutEnabled);
        }
        if (this.startUserSessionMessage != null) {
            writeTextToXml(typedXmlSerializer, TAG_START_USER_SESSION_MESSAGE, this.startUserSessionMessage);
        }
        if (this.endUserSessionMessage != null) {
            writeTextToXml(typedXmlSerializer, TAG_END_USER_SESSION_MESSAGE, this.endUserSessionMessage);
        }
        if (this.mCrossProfileCalendarPackages == null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_CROSS_PROFILE_CALENDAR_PACKAGES_NULL);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_CROSS_PROFILE_CALENDAR_PACKAGES_NULL);
        } else {
            writePackageListToXml(typedXmlSerializer, TAG_CROSS_PROFILE_CALENDAR_PACKAGES, this.mCrossProfileCalendarPackages);
        }
        writePackageListToXml(typedXmlSerializer, TAG_CROSS_PROFILE_PACKAGES, this.mCrossProfilePackages);
        if (this.mFactoryResetProtectionPolicy != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_FACTORY_RESET_PROTECTION_POLICY);
            this.mFactoryResetProtectionPolicy.writeToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_FACTORY_RESET_PROTECTION_POLICY);
        }
        if (this.mSuspendPersonalApps) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_SUSPEND_PERSONAL_APPS, this.mSuspendPersonalApps);
        }
        if (this.mProfileMaximumTimeOffMillis != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_PROFILE_MAXIMUM_TIME_OFF, this.mProfileMaximumTimeOffMillis);
        }
        if (this.mProfileMaximumTimeOffMillis != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_PROFILE_OFF_DEADLINE, this.mProfileOffDeadline);
        }
        if (!android.text.TextUtils.isEmpty(this.mAlwaysOnVpnPackage)) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_ALWAYS_ON_VPN_PACKAGE, this.mAlwaysOnVpnPackage);
        }
        if (this.mAlwaysOnVpnLockdown) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_ALWAYS_ON_VPN_LOCKDOWN, this.mAlwaysOnVpnLockdown);
        }
        if (this.mCommonCriteriaMode) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_COMMON_CRITERIA_MODE, this.mCommonCriteriaMode);
        }
        if (this.mPasswordComplexity != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_PASSWORD_COMPLEXITY, this.mPasswordComplexity);
        }
        if (this.mNearbyNotificationStreamingPolicy != 3) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_NEARBY_NOTIFICATION_STREAMING_POLICY, this.mNearbyNotificationStreamingPolicy);
        }
        if (this.mNearbyAppStreamingPolicy != 3) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_NEARBY_APP_STREAMING_POLICY, this.mNearbyAppStreamingPolicy);
        }
        if (!android.text.TextUtils.isEmpty(this.mOrganizationId)) {
            writeTextToXml(typedXmlSerializer, TAG_ORGANIZATION_ID, this.mOrganizationId);
        }
        if (!android.text.TextUtils.isEmpty(this.mEnrollmentSpecificId)) {
            writeTextToXml(typedXmlSerializer, TAG_ENROLLMENT_SPECIFIC_ID, this.mEnrollmentSpecificId);
        }
        writeAttributeValueToXml(typedXmlSerializer, TAG_ADMIN_CAN_GRANT_SENSORS_PERMISSIONS, this.mAdminCanGrantSensorsPermissions);
        if (!this.mUsbDataSignalingEnabled) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_USB_DATA_SIGNALING, this.mUsbDataSignalingEnabled);
        }
        if (this.mWifiMinimumSecurityLevel != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_WIFI_MIN_SECURITY, this.mWifiMinimumSecurityLevel);
        }
        if (this.mWifiSsidPolicy != null) {
            java.util.List<java.lang.String> ssidsToStrings = ssidsToStrings(this.mWifiSsidPolicy.getSsids());
            if (this.mWifiSsidPolicy.getPolicyType() == 0) {
                writeAttributeValuesToXml(typedXmlSerializer, TAG_SSID_ALLOWLIST, TAG_SSID, ssidsToStrings);
            } else if (this.mWifiSsidPolicy.getPolicyType() == 1) {
                writeAttributeValuesToXml(typedXmlSerializer, TAG_SSID_DENYLIST, TAG_SSID, ssidsToStrings);
            }
        }
        if (!this.mPreferentialNetworkServiceConfigs.isEmpty()) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIGS);
            java.util.Iterator<android.app.admin.PreferentialNetworkServiceConfig> it = this.mPreferentialNetworkServiceConfigs.iterator();
            while (it.hasNext()) {
                it.next().writeToXml(typedXmlSerializer);
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIGS);
        }
        if (this.mtePolicy != 0) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_MTE_POLICY, this.mtePolicy);
        }
        writePackagePolicy(typedXmlSerializer, TAG_CROSS_PROFILE_CALLER_ID_POLICY, this.mManagedProfileCallerIdAccess);
        writePackagePolicy(typedXmlSerializer, TAG_CROSS_PROFILE_CONTACTS_SEARCH_POLICY, this.mManagedProfileContactsAccess);
        writePackagePolicy(typedXmlSerializer, TAG_CREDENTIAL_MANAGER_POLICY, this.mCredentialManagerPolicy);
        if (this.mManagedSubscriptionsPolicy != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_MANAGED_SUBSCRIPTIONS_POLICY);
            this.mManagedSubscriptionsPolicy.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_MANAGED_SUBSCRIPTIONS_POLICY);
        }
        if (!android.text.TextUtils.isEmpty(this.mDialerPackage)) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_DIALER_PACKAGE, this.mDialerPackage);
        }
        if (!android.text.TextUtils.isEmpty(this.mSmsPackage)) {
            writeAttributeValueToXml(typedXmlSerializer, TAG_SMS_PACKAGE, this.mSmsPackage);
        }
    }

    private void writePackagePolicy(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, android.app.admin.PackagePolicy packagePolicy) throws java.io.IOException {
        if (packagePolicy == null) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_PACKAGE_POLICY_MODE, packagePolicy.getPolicyType());
        writePackageListToXml(typedXmlSerializer, TAG_PACKAGE_POLICY_PACKAGE_NAMES, new java.util.ArrayList(packagePolicy.getPackageNames()));
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    private java.util.List<java.lang.String> ssidsToStrings(java.util.Set<android.net.wifi.WifiSsid> set) {
        return (java.util.List) set.stream().map(new java.util.function.Function() { // from class: com.android.server.devicepolicy.ActiveAdmin$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$ssidsToStrings$0;
                lambda$ssidsToStrings$0 = com.android.server.devicepolicy.ActiveAdmin.lambda$ssidsToStrings$0((android.net.wifi.WifiSsid) obj);
                return lambda$ssidsToStrings$0;
            }
        }).collect(java.util.stream.Collectors.toList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$ssidsToStrings$0(android.net.wifi.WifiSsid wifiSsid) {
        return new java.lang.String(wifiSsid.getBytes(), java.nio.charset.StandardCharsets.UTF_8);
    }

    void writeTextToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.text(str2);
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    void writePackageListToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.util.List<java.lang.String> list) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        if (list == null) {
            return;
        }
        writeAttributeValuesToXml(typedXmlSerializer, str, "item", list);
    }

    void writeAttributeValueToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_VALUE, str2);
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    void writeAttributeValueToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, int i) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_VALUE, i);
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    void writeAttributeValueToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, long j) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_VALUE, j);
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    void writeAttributeValueToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, boolean z) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_VALUE, z);
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    void writeAttributeValuesToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.lang.String str2, @android.annotation.NonNull java.util.Collection<java.lang.String> collection) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, str);
        for (java.lang.String str3 : collection) {
            typedXmlSerializer.startTag((java.lang.String) null, str2);
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_VALUE, str3);
            typedXmlSerializer.endTag((java.lang.String) null, str2);
        }
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    void readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    java.lang.String name = typedXmlPullParser.getName();
                    if (TAG_POLICIES.equals(name)) {
                        if (z) {
                            com.android.server.utils.Slogf.d("DevicePolicyManager", "Overriding device admin policies from XML.");
                            this.info.readPoliciesFromXml(typedXmlPullParser);
                        }
                    } else if (TAG_PASSWORD_QUALITY.equals(name)) {
                        this.mPasswordPolicy.quality = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_MIN_PASSWORD_LENGTH.equals(name)) {
                        this.mPasswordPolicy.length = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_PASSWORD_HISTORY_LENGTH.equals(name)) {
                        this.passwordHistoryLength = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_MIN_PASSWORD_UPPERCASE.equals(name)) {
                        this.mPasswordPolicy.upperCase = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_MIN_PASSWORD_LOWERCASE.equals(name)) {
                        this.mPasswordPolicy.lowerCase = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_MIN_PASSWORD_LETTERS.equals(name)) {
                        this.mPasswordPolicy.letters = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_MIN_PASSWORD_NUMERIC.equals(name)) {
                        this.mPasswordPolicy.numeric = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_MIN_PASSWORD_SYMBOLS.equals(name)) {
                        this.mPasswordPolicy.symbols = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_MIN_PASSWORD_NONLETTER.equals(name)) {
                        this.mPasswordPolicy.nonLetter = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_MAX_TIME_TO_UNLOCK.equals(name)) {
                        this.maximumTimeToUnlock = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_STRONG_AUTH_UNLOCK_TIMEOUT.equals(name)) {
                        this.strongAuthUnlockTimeout = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_MAX_FAILED_PASSWORD_WIPE.equals(name)) {
                        this.maximumFailedPasswordsForWipe = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_SPECIFIES_GLOBAL_PROXY.equals(name)) {
                        this.specifiesGlobalProxy = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_GLOBAL_PROXY_SPEC.equals(name)) {
                        this.globalProxySpec = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_GLOBAL_PROXY_EXCLUSION_LIST.equals(name)) {
                        this.globalProxyExclusionList = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_PASSWORD_EXPIRATION_TIMEOUT.equals(name)) {
                        this.passwordExpirationTimeout = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_PASSWORD_EXPIRATION_DATE.equals(name)) {
                        this.passwordExpirationDate = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_ENCRYPTION_REQUESTED.equals(name)) {
                        this.encryptionRequested = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_TEST_ONLY_ADMIN.equals(name)) {
                        this.testOnlyAdmin = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_DISABLE_CAMERA.equals(name)) {
                        this.disableCamera = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_DISABLE_CALLER_ID.equals(name)) {
                        this.disableCallerId = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_DISABLE_CONTACTS_SEARCH.equals(name)) {
                        this.disableContactsSearch = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_DISABLE_BLUETOOTH_CONTACT_SHARING.equals(name)) {
                        this.disableBluetoothContactSharing = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_DISABLE_SCREEN_CAPTURE.equals(name)) {
                        this.disableScreenCapture = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_REQUIRE_AUTO_TIME.equals(name)) {
                        this.requireAutoTime = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_FORCE_EPHEMERAL_USERS.equals(name)) {
                        this.forceEphemeralUsers = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_IS_NETWORK_LOGGING_ENABLED.equals(name)) {
                        this.isNetworkLoggingEnabled = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                        this.lastNetworkLoggingNotificationTimeMs = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_LAST_NETWORK_LOGGING_NOTIFICATION);
                        this.numNetworkLoggingNotifications = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_NUM_NETWORK_LOGGING_NOTIFICATIONS);
                    } else if (TAG_DISABLE_KEYGUARD_FEATURES.equals(name)) {
                        this.disabledKeyguardFeatures = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_DISABLE_ACCOUNT_MANAGEMENT.equals(name)) {
                        readAttributeValues(typedXmlPullParser, TAG_ACCOUNT_TYPE, this.accountTypesWithManagementDisabled);
                    } else if (TAG_MANAGE_TRUST_AGENT_FEATURES.equals(name)) {
                        this.trustAgentInfos = getAllTrustAgentInfos(typedXmlPullParser, name);
                    } else if (TAG_CROSS_PROFILE_WIDGET_PROVIDERS.equals(name)) {
                        this.crossProfileWidgetProviders = new java.util.ArrayList();
                        readAttributeValues(typedXmlPullParser, TAG_PROVIDER, this.crossProfileWidgetProviders);
                    } else if (TAG_PERMITTED_ACCESSIBILITY_SERVICES.equals(name)) {
                        this.permittedAccessiblityServices = readPackageList(typedXmlPullParser, name);
                    } else if (TAG_PERMITTED_IMES.equals(name)) {
                        this.permittedInputMethods = readPackageList(typedXmlPullParser, name);
                    } else if (TAG_PERMITTED_NOTIFICATION_LISTENERS.equals(name)) {
                        this.permittedNotificationListeners = readPackageList(typedXmlPullParser, name);
                    } else if (TAG_KEEP_UNINSTALLED_PACKAGES.equals(name)) {
                        this.keepUninstalledPackages = readPackageList(typedXmlPullParser, name);
                    } else if (TAG_METERED_DATA_DISABLED_PACKAGES.equals(name)) {
                        this.meteredDisabledPackages = readPackageList(typedXmlPullParser, name);
                    } else if (TAG_PROTECTED_PACKAGES.equals(name)) {
                        this.protectedPackages = readPackageList(typedXmlPullParser, name);
                    } else if (TAG_SUSPENDED_PACKAGES.equals(name)) {
                        this.suspendedPackages = readPackageList(typedXmlPullParser, name);
                    } else if (TAG_USER_RESTRICTIONS.equals(name)) {
                        this.userRestrictions = com.android.server.pm.UserRestrictionsUtils.readRestrictions(typedXmlPullParser);
                    } else if (TAG_DEFAULT_ENABLED_USER_RESTRICTIONS.equals(name)) {
                        readAttributeValues(typedXmlPullParser, TAG_RESTRICTION, this.defaultEnabledRestrictionsAlreadySet);
                    } else if (TAG_SHORT_SUPPORT_MESSAGE.equals(name)) {
                        if (typedXmlPullParser.next() == 4) {
                            this.shortSupportMessage = typedXmlPullParser.getText();
                        } else {
                            com.android.server.utils.Slogf.w("DevicePolicyManager", "Missing text when loading short support message");
                        }
                    } else if (TAG_LONG_SUPPORT_MESSAGE.equals(name)) {
                        if (typedXmlPullParser.next() == 4) {
                            this.longSupportMessage = typedXmlPullParser.getText();
                        } else {
                            com.android.server.utils.Slogf.w("DevicePolicyManager", "Missing text when loading long support message");
                        }
                    } else if (TAG_PARENT_ADMIN.equals(name)) {
                        com.android.internal.util.Preconditions.checkState(!this.isParent);
                        this.parentAdmin = new com.android.server.devicepolicy.ActiveAdmin(this.info, true);
                        this.parentAdmin.readFromXml(typedXmlPullParser, z);
                    } else if (TAG_ORGANIZATION_COLOR.equals(name)) {
                        this.organizationColor = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_ORGANIZATION_NAME.equals(name)) {
                        if (typedXmlPullParser.next() == 4) {
                            this.organizationName = typedXmlPullParser.getText();
                        } else {
                            com.android.server.utils.Slogf.w("DevicePolicyManager", "Missing text when loading organization name");
                        }
                    } else if (TAG_IS_LOGOUT_ENABLED.equals(name)) {
                        this.isLogoutEnabled = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_START_USER_SESSION_MESSAGE.equals(name)) {
                        if (typedXmlPullParser.next() == 4) {
                            this.startUserSessionMessage = typedXmlPullParser.getText();
                        } else {
                            com.android.server.utils.Slogf.w("DevicePolicyManager", "Missing text when loading start session message");
                        }
                    } else if (TAG_END_USER_SESSION_MESSAGE.equals(name)) {
                        if (typedXmlPullParser.next() == 4) {
                            this.endUserSessionMessage = typedXmlPullParser.getText();
                        } else {
                            com.android.server.utils.Slogf.w("DevicePolicyManager", "Missing text when loading end session message");
                        }
                    } else if (TAG_CROSS_PROFILE_CALENDAR_PACKAGES.equals(name)) {
                        this.mCrossProfileCalendarPackages = readPackageList(typedXmlPullParser, name);
                    } else if (TAG_CROSS_PROFILE_CALENDAR_PACKAGES_NULL.equals(name)) {
                        this.mCrossProfileCalendarPackages = null;
                    } else if (TAG_CROSS_PROFILE_PACKAGES.equals(name)) {
                        this.mCrossProfilePackages = readPackageList(typedXmlPullParser, name);
                    } else if (TAG_FACTORY_RESET_PROTECTION_POLICY.equals(name)) {
                        this.mFactoryResetProtectionPolicy = android.app.admin.FactoryResetProtectionPolicy.readFromXml(typedXmlPullParser);
                    } else if (TAG_SUSPEND_PERSONAL_APPS.equals(name)) {
                        this.mSuspendPersonalApps = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_PROFILE_MAXIMUM_TIME_OFF.equals(name)) {
                        this.mProfileMaximumTimeOffMillis = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_PROFILE_OFF_DEADLINE.equals(name)) {
                        this.mProfileOffDeadline = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_ALWAYS_ON_VPN_PACKAGE.equals(name)) {
                        this.mAlwaysOnVpnPackage = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_ALWAYS_ON_VPN_LOCKDOWN.equals(name)) {
                        this.mAlwaysOnVpnLockdown = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_PREFERENTIAL_NETWORK_SERVICE_ENABLED.equals(name)) {
                        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                        if (attributeBoolean) {
                            android.app.admin.PreferentialNetworkServiceConfig.Builder builder = new android.app.admin.PreferentialNetworkServiceConfig.Builder();
                            builder.setEnabled(attributeBoolean);
                            builder.setNetworkId(1);
                            this.mPreferentialNetworkServiceConfigs = java.util.List.of(builder.build());
                        }
                    } else if (TAG_COMMON_CRITERIA_MODE.equals(name)) {
                        this.mCommonCriteriaMode = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_PASSWORD_COMPLEXITY.equals(name)) {
                        this.mPasswordComplexity = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_NEARBY_NOTIFICATION_STREAMING_POLICY.equals(name)) {
                        this.mNearbyNotificationStreamingPolicy = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_NEARBY_APP_STREAMING_POLICY.equals(name)) {
                        this.mNearbyAppStreamingPolicy = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_ORGANIZATION_ID.equals(name)) {
                        if (typedXmlPullParser.next() == 4) {
                            this.mOrganizationId = typedXmlPullParser.getText();
                        } else {
                            com.android.server.utils.Slogf.w("DevicePolicyManager", "Missing Organization ID.");
                        }
                    } else if (TAG_ENROLLMENT_SPECIFIC_ID.equals(name)) {
                        if (typedXmlPullParser.next() == 4) {
                            this.mEnrollmentSpecificId = typedXmlPullParser.getText();
                        } else {
                            com.android.server.utils.Slogf.w("DevicePolicyManager", "Missing Enrollment-specific ID.");
                        }
                    } else if (TAG_ADMIN_CAN_GRANT_SENSORS_PERMISSIONS.equals(name)) {
                        this.mAdminCanGrantSensorsPermissions = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_USB_DATA_SIGNALING.equals(name)) {
                        this.mUsbDataSignalingEnabled = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, true);
                    } else if (TAG_WIFI_MIN_SECURITY.equals(name)) {
                        this.mWifiMinimumSecurityLevel = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_SSID_ALLOWLIST.equals(name)) {
                        this.mWifiSsidPolicy = new android.app.admin.WifiSsidPolicy(0, new android.util.ArraySet(readWifiSsids(typedXmlPullParser, TAG_SSID)));
                    } else if (TAG_SSID_DENYLIST.equals(name)) {
                        this.mWifiSsidPolicy = new android.app.admin.WifiSsidPolicy(1, new android.util.ArraySet(readWifiSsids(typedXmlPullParser, TAG_SSID)));
                    } else if (TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIGS.equals(name)) {
                        java.util.List<android.app.admin.PreferentialNetworkServiceConfig> preferentialNetworkServiceConfigs = getPreferentialNetworkServiceConfigs(typedXmlPullParser, name);
                        if (!preferentialNetworkServiceConfigs.isEmpty()) {
                            this.mPreferentialNetworkServiceConfigs = preferentialNetworkServiceConfigs;
                        }
                    } else if (TAG_MTE_POLICY.equals(name)) {
                        this.mtePolicy = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_CROSS_PROFILE_CALLER_ID_POLICY.equals(name)) {
                        this.mManagedProfileCallerIdAccess = readPackagePolicy(typedXmlPullParser);
                    } else if (TAG_CROSS_PROFILE_CONTACTS_SEARCH_POLICY.equals(name)) {
                        this.mManagedProfileContactsAccess = readPackagePolicy(typedXmlPullParser);
                    } else if (TAG_MANAGED_SUBSCRIPTIONS_POLICY.equals(name)) {
                        this.mManagedSubscriptionsPolicy = android.app.admin.ManagedSubscriptionsPolicy.readFromXml(typedXmlPullParser);
                    } else if (TAG_CREDENTIAL_MANAGER_POLICY.equals(name)) {
                        this.mCredentialManagerPolicy = readPackagePolicy(typedXmlPullParser);
                    } else if (TAG_DIALER_PACKAGE.equals(name)) {
                        this.mDialerPackage = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_SMS_PACKAGE.equals(name)) {
                        this.mSmsPackage = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE);
                    } else {
                        com.android.server.utils.Slogf.w("DevicePolicyManager", "Unknown admin tag: %s", name);
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    private android.app.admin.PackagePolicy readPackagePolicy(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return new android.app.admin.PackagePolicy(typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_PACKAGE_POLICY_MODE), new android.util.ArraySet(readPackageList(typedXmlPullParser, TAG_PACKAGE_POLICY_PACKAGE_NAMES)));
    }

    private java.util.List<android.net.wifi.WifiSsid> readWifiSsids(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        readAttributeValues(typedXmlPullParser, str, arrayList);
        return (java.util.List) arrayList.stream().map(new java.util.function.Function() { // from class: com.android.server.devicepolicy.ActiveAdmin$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.net.wifi.WifiSsid lambda$readWifiSsids$1;
                lambda$readWifiSsids$1 = com.android.server.devicepolicy.ActiveAdmin.lambda$readWifiSsids$1((java.lang.String) obj);
                return lambda$readWifiSsids$1;
            }
        }).collect(java.util.stream.Collectors.toList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.net.wifi.WifiSsid lambda$readWifiSsids$1(java.lang.String str) {
        return android.net.wifi.WifiSsid.fromBytes(str.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    private java.util.List<java.lang.String> readPackageList(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = typedXmlPullParser.getName();
                if ("item".equals(name)) {
                    java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE);
                    if (attributeValue != null) {
                        arrayList.add(attributeValue);
                    } else {
                        com.android.server.utils.Slogf.w("DevicePolicyManager", "Package name missing under %s", name);
                    }
                } else {
                    com.android.server.utils.Slogf.w("DevicePolicyManager", "Unknown tag under %s: ", str, name);
                }
            }
        }
        return arrayList;
    }

    private void readAttributeValues(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.util.Collection<java.lang.String> collection) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        collection.clear();
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    java.lang.String name = typedXmlPullParser.getName();
                    if (str.equals(name)) {
                        collection.add(typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE));
                    } else {
                        com.android.server.utils.Slogf.e("DevicePolicyManager", "Expected tag %s but found %s", str, name);
                    }
                }
            } else {
                return;
            }
        }
    }

    @android.annotation.NonNull
    private android.util.ArrayMap<java.lang.String, com.android.server.devicepolicy.ActiveAdmin.TrustAgentInfo> getAllTrustAgentInfos(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        android.util.ArrayMap<java.lang.String, com.android.server.devicepolicy.ActiveAdmin.TrustAgentInfo> arrayMap = new android.util.ArrayMap<>();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = typedXmlPullParser.getName();
                if (TAG_TRUST_AGENT_COMPONENT.equals(name)) {
                    arrayMap.put(typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE), getTrustAgentInfo(typedXmlPullParser, str));
                } else {
                    com.android.server.utils.Slogf.w("DevicePolicyManager", "Unknown tag under %s: %s", str, name);
                }
            }
        }
        return arrayMap;
    }

    private com.android.server.devicepolicy.ActiveAdmin.TrustAgentInfo getTrustAgentInfo(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        com.android.server.devicepolicy.ActiveAdmin.TrustAgentInfo trustAgentInfo = new com.android.server.devicepolicy.ActiveAdmin.TrustAgentInfo(null);
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = typedXmlPullParser.getName();
                if (TAG_TRUST_AGENT_COMPONENT_OPTIONS.equals(name)) {
                    trustAgentInfo.options = android.os.PersistableBundle.restoreFromXml(typedXmlPullParser);
                } else {
                    com.android.server.utils.Slogf.w("DevicePolicyManager", "Unknown tag under %s: %s", str, name);
                }
            }
        }
        return trustAgentInfo;
    }

    @android.annotation.NonNull
    private java.util.List<android.app.admin.PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = typedXmlPullParser.getName();
                if (TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIG.equals(name)) {
                    arrayList.add(android.app.admin.PreferentialNetworkServiceConfig.getPreferentialNetworkServiceConfig(typedXmlPullParser, str));
                } else {
                    com.android.server.utils.Slogf.w("DevicePolicyManager", "Unknown tag under %s: %s", str, name);
                }
            }
        }
        return arrayList;
    }

    boolean hasUserRestrictions() {
        return this.userRestrictions != null && this.userRestrictions.size() > 0;
    }

    android.os.Bundle ensureUserRestrictions() {
        if (this.userRestrictions == null) {
            this.userRestrictions = new android.os.Bundle();
        }
        return this.userRestrictions;
    }

    public void transfer(android.app.admin.DeviceAdminInfo deviceAdminInfo) {
        if (hasParentActiveAdmin()) {
            this.parentAdmin.info = deviceAdminInfo;
        }
        this.info = deviceAdminInfo;
    }

    android.os.Bundle addSyntheticRestrictions(android.os.Bundle bundle) {
        if (this.disableCamera) {
            bundle.putBoolean("no_camera", true);
        }
        if (this.requireAutoTime) {
            bundle.putBoolean("no_config_date_time", true);
        }
        return bundle;
    }

    static android.os.Bundle removeDeprecatedRestrictions(android.os.Bundle bundle) {
        java.util.Iterator<java.lang.String> it = com.android.server.pm.UserRestrictionsUtils.DEPRECATED_USER_RESTRICTIONS.iterator();
        while (it.hasNext()) {
            bundle.remove(it.next());
        }
        return bundle;
    }

    static android.os.Bundle filterRestrictions(android.os.Bundle bundle, java.util.function.Predicate<java.lang.String> predicate) {
        android.os.Bundle bundle2 = new android.os.Bundle();
        for (java.lang.String str : bundle.keySet()) {
            if (bundle.getBoolean(str) && predicate.test(str)) {
                bundle2.putBoolean(str, true);
            }
        }
        return bundle2;
    }

    android.os.Bundle getEffectiveRestrictions() {
        return addSyntheticRestrictions(removeDeprecatedRestrictions(new android.os.Bundle(ensureUserRestrictions())));
    }

    android.os.Bundle getLocalUserRestrictions(final int i) {
        return filterRestrictions(getEffectiveRestrictions(), new java.util.function.Predicate() { // from class: com.android.server.devicepolicy.ActiveAdmin$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isLocal;
                isLocal = com.android.server.pm.UserRestrictionsUtils.isLocal(i, (java.lang.String) obj);
                return isLocal;
            }
        });
    }

    android.os.Bundle getGlobalUserRestrictions(final int i) {
        return filterRestrictions(getEffectiveRestrictions(), new java.util.function.Predicate() { // from class: com.android.server.devicepolicy.ActiveAdmin$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isGlobal;
                isGlobal = com.android.server.pm.UserRestrictionsUtils.isGlobal(i, (java.lang.String) obj);
                return isGlobal;
            }
        });
    }

    void dumpPackagePolicy(final android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, android.app.admin.PackagePolicy packagePolicy) {
        indentingPrintWriter.print(str);
        indentingPrintWriter.println(":");
        if (packagePolicy != null) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("policyType=");
            indentingPrintWriter.println(packagePolicy.getPolicyType());
            indentingPrintWriter.println("packageNames:");
            indentingPrintWriter.increaseIndent();
            packagePolicy.getPackageNames().forEach(new java.util.function.Consumer() { // from class: com.android.server.devicepolicy.ActiveAdmin$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    indentingPrintWriter.println((java.lang.String) obj);
                }
            });
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
        }
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("uid=");
        indentingPrintWriter.println(getUid());
        indentingPrintWriter.print("testOnlyAdmin=");
        indentingPrintWriter.println(this.testOnlyAdmin);
        if (this.info != null) {
            indentingPrintWriter.println("policies:");
            java.util.ArrayList usedPolicies = this.info.getUsedPolicies();
            if (usedPolicies != null) {
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < usedPolicies.size(); i++) {
                    indentingPrintWriter.println(((android.app.admin.DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).tag);
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.print("passwordQuality=0x");
        indentingPrintWriter.println(java.lang.Integer.toHexString(this.mPasswordPolicy.quality));
        indentingPrintWriter.print("minimumPasswordLength=");
        indentingPrintWriter.println(this.mPasswordPolicy.length);
        indentingPrintWriter.print("passwordHistoryLength=");
        indentingPrintWriter.println(this.passwordHistoryLength);
        indentingPrintWriter.print("minimumPasswordUpperCase=");
        indentingPrintWriter.println(this.mPasswordPolicy.upperCase);
        indentingPrintWriter.print("minimumPasswordLowerCase=");
        indentingPrintWriter.println(this.mPasswordPolicy.lowerCase);
        indentingPrintWriter.print("minimumPasswordLetters=");
        indentingPrintWriter.println(this.mPasswordPolicy.letters);
        indentingPrintWriter.print("minimumPasswordNumeric=");
        indentingPrintWriter.println(this.mPasswordPolicy.numeric);
        indentingPrintWriter.print("minimumPasswordSymbols=");
        indentingPrintWriter.println(this.mPasswordPolicy.symbols);
        indentingPrintWriter.print("minimumPasswordNonLetter=");
        indentingPrintWriter.println(this.mPasswordPolicy.nonLetter);
        indentingPrintWriter.print("maximumTimeToUnlock=");
        indentingPrintWriter.println(this.maximumTimeToUnlock);
        indentingPrintWriter.print("strongAuthUnlockTimeout=");
        indentingPrintWriter.println(this.strongAuthUnlockTimeout);
        indentingPrintWriter.print("maximumFailedPasswordsForWipe=");
        indentingPrintWriter.println(this.maximumFailedPasswordsForWipe);
        indentingPrintWriter.print("specifiesGlobalProxy=");
        indentingPrintWriter.println(this.specifiesGlobalProxy);
        indentingPrintWriter.print("passwordExpirationTimeout=");
        indentingPrintWriter.println(this.passwordExpirationTimeout);
        indentingPrintWriter.print("passwordExpirationDate=");
        indentingPrintWriter.println(this.passwordExpirationDate);
        if (this.globalProxySpec != null) {
            indentingPrintWriter.print("globalProxySpec=");
            indentingPrintWriter.println(this.globalProxySpec);
        }
        if (this.globalProxyExclusionList != null) {
            indentingPrintWriter.print("globalProxyEclusionList=");
            indentingPrintWriter.println(this.globalProxyExclusionList);
        }
        indentingPrintWriter.print("encryptionRequested=");
        indentingPrintWriter.println(this.encryptionRequested);
        if (!android.app.admin.flags.Flags.dumpsysPolicyEngineMigrationEnabled()) {
            indentingPrintWriter.print("disableCamera=");
            indentingPrintWriter.println(this.disableCamera);
            indentingPrintWriter.print("disableScreenCapture=");
            indentingPrintWriter.println(this.disableScreenCapture);
            indentingPrintWriter.print("requireAutoTime=");
            indentingPrintWriter.println(this.requireAutoTime);
            if (this.permittedInputMethods != null) {
                indentingPrintWriter.print("permittedInputMethods=");
                indentingPrintWriter.println(this.permittedInputMethods);
            }
            indentingPrintWriter.println("userRestrictions:");
            com.android.server.pm.UserRestrictionsUtils.dumpRestrictions(indentingPrintWriter, "  ", this.userRestrictions);
        }
        if (!android.app.admin.flags.Flags.policyEngineMigrationV2Enabled() || !android.app.admin.flags.Flags.dumpsysPolicyEngineMigrationEnabled()) {
            indentingPrintWriter.print("mUsbDataSignaling=");
            indentingPrintWriter.println(this.mUsbDataSignalingEnabled);
        }
        indentingPrintWriter.print("disableCallerId=");
        indentingPrintWriter.println(this.disableCallerId);
        indentingPrintWriter.print("disableContactsSearch=");
        indentingPrintWriter.println(this.disableContactsSearch);
        indentingPrintWriter.print("disableBluetoothContactSharing=");
        indentingPrintWriter.println(this.disableBluetoothContactSharing);
        indentingPrintWriter.print("forceEphemeralUsers=");
        indentingPrintWriter.println(this.forceEphemeralUsers);
        indentingPrintWriter.print("isNetworkLoggingEnabled=");
        indentingPrintWriter.println(this.isNetworkLoggingEnabled);
        indentingPrintWriter.print("disabledKeyguardFeatures=");
        indentingPrintWriter.println(this.disabledKeyguardFeatures);
        indentingPrintWriter.print("crossProfileWidgetProviders=");
        indentingPrintWriter.println(this.crossProfileWidgetProviders);
        if (this.permittedAccessiblityServices != null) {
            indentingPrintWriter.print("permittedAccessibilityServices=");
            indentingPrintWriter.println(this.permittedAccessiblityServices);
        }
        if (this.permittedNotificationListeners != null) {
            indentingPrintWriter.print("permittedNotificationListeners=");
            indentingPrintWriter.println(this.permittedNotificationListeners);
        }
        if (this.keepUninstalledPackages != null) {
            indentingPrintWriter.print("keepUninstalledPackages=");
            indentingPrintWriter.println(this.keepUninstalledPackages);
        }
        if (this.meteredDisabledPackages != null) {
            indentingPrintWriter.print("meteredDisabledPackages=");
            indentingPrintWriter.println(this.meteredDisabledPackages);
        }
        if (this.protectedPackages != null) {
            indentingPrintWriter.print("protectedPackages=");
            indentingPrintWriter.println(this.protectedPackages);
        }
        if (this.suspendedPackages != null) {
            indentingPrintWriter.print("suspendedPackages=");
            indentingPrintWriter.println(this.suspendedPackages);
        }
        indentingPrintWriter.print("organizationColor=");
        indentingPrintWriter.println(this.organizationColor);
        if (this.organizationName != null) {
            indentingPrintWriter.print("organizationName=");
            indentingPrintWriter.println(this.organizationName);
        }
        indentingPrintWriter.print("defaultEnabledRestrictionsAlreadySet=");
        indentingPrintWriter.println(this.defaultEnabledRestrictionsAlreadySet);
        dumpPackagePolicy(indentingPrintWriter, "managedProfileCallerIdPolicy", this.mManagedProfileCallerIdAccess);
        dumpPackagePolicy(indentingPrintWriter, "managedProfileContactsPolicy", this.mManagedProfileContactsAccess);
        dumpPackagePolicy(indentingPrintWriter, "credentialManagerPolicy", this.mCredentialManagerPolicy);
        indentingPrintWriter.print("isParent=");
        indentingPrintWriter.println(this.isParent);
        if (this.parentAdmin != null) {
            indentingPrintWriter.println("parentAdmin:");
            indentingPrintWriter.increaseIndent();
            this.parentAdmin.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
        if (this.mCrossProfileCalendarPackages != null) {
            indentingPrintWriter.print("mCrossProfileCalendarPackages=");
            indentingPrintWriter.println(this.mCrossProfileCalendarPackages);
        }
        indentingPrintWriter.print("mCrossProfilePackages=");
        indentingPrintWriter.println(this.mCrossProfilePackages);
        indentingPrintWriter.print("mSuspendPersonalApps=");
        indentingPrintWriter.println(this.mSuspendPersonalApps);
        indentingPrintWriter.print("mProfileMaximumTimeOffMillis=");
        indentingPrintWriter.println(this.mProfileMaximumTimeOffMillis);
        indentingPrintWriter.print("mProfileOffDeadline=");
        indentingPrintWriter.println(this.mProfileOffDeadline);
        indentingPrintWriter.print("mAlwaysOnVpnPackage=");
        indentingPrintWriter.println(this.mAlwaysOnVpnPackage);
        indentingPrintWriter.print("mAlwaysOnVpnLockdown=");
        indentingPrintWriter.println(this.mAlwaysOnVpnLockdown);
        indentingPrintWriter.print("mCommonCriteriaMode=");
        indentingPrintWriter.println(this.mCommonCriteriaMode);
        indentingPrintWriter.print("mPasswordComplexity=");
        indentingPrintWriter.println(this.mPasswordComplexity);
        indentingPrintWriter.print("mNearbyNotificationStreamingPolicy=");
        indentingPrintWriter.println(this.mNearbyNotificationStreamingPolicy);
        indentingPrintWriter.print("mNearbyAppStreamingPolicy=");
        indentingPrintWriter.println(this.mNearbyAppStreamingPolicy);
        if (!android.text.TextUtils.isEmpty(this.mOrganizationId)) {
            indentingPrintWriter.print("mOrganizationId=");
            indentingPrintWriter.println(this.mOrganizationId);
        }
        if (!android.text.TextUtils.isEmpty(this.mEnrollmentSpecificId)) {
            indentingPrintWriter.print("mEnrollmentSpecificId=");
            indentingPrintWriter.println(this.mEnrollmentSpecificId);
        }
        indentingPrintWriter.print("mAdminCanGrantSensorsPermissions=");
        indentingPrintWriter.println(this.mAdminCanGrantSensorsPermissions);
        indentingPrintWriter.print("mWifiMinimumSecurityLevel=");
        indentingPrintWriter.println(this.mWifiMinimumSecurityLevel);
        if (this.mWifiSsidPolicy != null) {
            if (this.mWifiSsidPolicy.getPolicyType() == 0) {
                indentingPrintWriter.print("mSsidAllowlist=");
            } else {
                indentingPrintWriter.print("mSsidDenylist=");
            }
            indentingPrintWriter.println(ssidsToStrings(this.mWifiSsidPolicy.getSsids()));
        }
        if (this.mFactoryResetProtectionPolicy != null) {
            indentingPrintWriter.println("mFactoryResetProtectionPolicy:");
            indentingPrintWriter.increaseIndent();
            this.mFactoryResetProtectionPolicy.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
        if (this.mPreferentialNetworkServiceConfigs != null) {
            indentingPrintWriter.println("mPreferentialNetworkServiceConfigs:");
            indentingPrintWriter.increaseIndent();
            java.util.Iterator<android.app.admin.PreferentialNetworkServiceConfig> it = this.mPreferentialNetworkServiceConfigs.iterator();
            while (it.hasNext()) {
                it.next().dump(indentingPrintWriter);
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.print("mtePolicy=");
        indentingPrintWriter.println(this.mtePolicy);
        indentingPrintWriter.print("accountTypesWithManagementDisabled=");
        indentingPrintWriter.println(this.accountTypesWithManagementDisabled);
        if (this.mManagedSubscriptionsPolicy != null) {
            indentingPrintWriter.println("mManagedSubscriptionsPolicy:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(this.mManagedSubscriptionsPolicy);
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.print("mDialerPackage=");
        indentingPrintWriter.println(this.mDialerPackage);
        indentingPrintWriter.print("mSmsPackage=");
        indentingPrintWriter.println(this.mSmsPackage);
    }
}
