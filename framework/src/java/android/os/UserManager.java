package android.os;

/* loaded from: classes3.dex */
public class UserManager {

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_CREATE_SUPERVISED_USER = "android.os.action.CREATE_SUPERVISED_USER";
    private static final java.lang.String ACTION_CREATE_USER = "android.os.action.CREATE_USER";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_USER_RESTRICTIONS_CHANGED = "android.os.action.USER_RESTRICTIONS_CHANGED";
    public static final java.lang.String ALLOW_PARENT_PROFILE_APP_LINKING = "allow_parent_profile_app_linking";
    public static final long ALWAYS_USE_CONTEXT_USER = 183155436;
    private static final java.lang.String CACHE_KEY_IS_USER_UNLOCKED_PROPERTY = "cache_key.is_user_unlocked";
    private static final java.lang.String CACHE_KEY_STATIC_USER_PROPERTIES = "cache_key.static_user_props";
    private static final java.lang.String CACHE_KEY_USER_PROPERTIES = "cache_key.user_properties";
    public static final java.lang.String DEV_CREATE_OVERRIDE_PROPERTY = "debug.user.creation_override";
    public static final java.lang.String DISALLOW_ADD_CLONE_PROFILE = "no_add_clone_profile";

    @java.lang.Deprecated
    public static final java.lang.String DISALLOW_ADD_MANAGED_PROFILE = "no_add_managed_profile";
    public static final java.lang.String DISALLOW_ADD_PRIVATE_PROFILE = "no_add_private_profile";
    public static final java.lang.String DISALLOW_ADD_USER = "no_add_user";
    public static final java.lang.String DISALLOW_ADD_WIFI_CONFIG = "no_add_wifi_config";
    public static final java.lang.String DISALLOW_ADJUST_VOLUME = "no_adjust_volume";
    public static final java.lang.String DISALLOW_AIRPLANE_MODE = "no_airplane_mode";
    public static final java.lang.String DISALLOW_AMBIENT_DISPLAY = "no_ambient_display";
    public static final java.lang.String DISALLOW_APPS_CONTROL = "no_control_apps";
    public static final java.lang.String DISALLOW_ASSIST_CONTENT = "no_assist_content";
    public static final java.lang.String DISALLOW_AUTOFILL = "no_autofill";
    public static final java.lang.String DISALLOW_BIOMETRIC = "disallow_biometric";
    public static final java.lang.String DISALLOW_BLUETOOTH = "no_bluetooth";
    public static final java.lang.String DISALLOW_BLUETOOTH_SHARING = "no_bluetooth_sharing";
    public static final java.lang.String DISALLOW_CAMERA = "no_camera";
    public static final java.lang.String DISALLOW_CAMERA_TOGGLE = "disallow_camera_toggle";
    public static final java.lang.String DISALLOW_CELLULAR_2G = "no_cellular_2g";
    public static final java.lang.String DISALLOW_CHANGE_WIFI_STATE = "no_change_wifi_state";
    public static final java.lang.String DISALLOW_CONFIG_BLUETOOTH = "no_config_bluetooth";
    public static final java.lang.String DISALLOW_CONFIG_BRIGHTNESS = "no_config_brightness";
    public static final java.lang.String DISALLOW_CONFIG_CELL_BROADCASTS = "no_config_cell_broadcasts";
    public static final java.lang.String DISALLOW_CONFIG_CREDENTIALS = "no_config_credentials";
    public static final java.lang.String DISALLOW_CONFIG_DATE_TIME = "no_config_date_time";
    public static final java.lang.String DISALLOW_CONFIG_DEFAULT_APPS = "disallow_config_default_apps";
    public static final java.lang.String DISALLOW_CONFIG_LOCALE = "no_config_locale";
    public static final java.lang.String DISALLOW_CONFIG_LOCATION = "no_config_location";
    public static final java.lang.String DISALLOW_CONFIG_MOBILE_NETWORKS = "no_config_mobile_networks";
    public static final java.lang.String DISALLOW_CONFIG_PRIVATE_DNS = "disallow_config_private_dns";
    public static final java.lang.String DISALLOW_CONFIG_SCREEN_TIMEOUT = "no_config_screen_timeout";
    public static final java.lang.String DISALLOW_CONFIG_TETHERING = "no_config_tethering";
    public static final java.lang.String DISALLOW_CONFIG_VPN = "no_config_vpn";
    public static final java.lang.String DISALLOW_CONFIG_WIFI = "no_config_wifi";
    public static final java.lang.String DISALLOW_CONTENT_CAPTURE = "no_content_capture";
    public static final java.lang.String DISALLOW_CONTENT_SUGGESTIONS = "no_content_suggestions";
    public static final java.lang.String DISALLOW_CREATE_WINDOWS = "no_create_windows";
    public static final java.lang.String DISALLOW_CROSS_PROFILE_COPY_PASTE = "no_cross_profile_copy_paste";
    public static final java.lang.String DISALLOW_DATA_ROAMING = "no_data_roaming";
    public static final java.lang.String DISALLOW_DEBUGGING_FEATURES = "no_debugging_features";
    public static final java.lang.String DISALLOW_FACTORY_RESET = "no_factory_reset";
    public static final java.lang.String DISALLOW_FUN = "no_fun";
    public static final java.lang.String DISALLOW_GRANT_ADMIN = "no_grant_admin";
    public static final java.lang.String DISALLOW_INSTALL_APPS = "no_install_apps";
    public static final java.lang.String DISALLOW_INSTALL_UNKNOWN_SOURCES = "no_install_unknown_sources";
    public static final java.lang.String DISALLOW_INSTALL_UNKNOWN_SOURCES_GLOBALLY = "no_install_unknown_sources_globally";
    public static final java.lang.String DISALLOW_MICROPHONE_TOGGLE = "disallow_microphone_toggle";
    public static final java.lang.String DISALLOW_MODIFY_ACCOUNTS = "no_modify_accounts";
    public static final java.lang.String DISALLOW_MOUNT_PHYSICAL_MEDIA = "no_physical_media";
    public static final java.lang.String DISALLOW_NEAR_FIELD_COMMUNICATION_RADIO = "no_near_field_communication_radio";
    public static final java.lang.String DISALLOW_NETWORK_RESET = "no_network_reset";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String DISALLOW_OEM_UNLOCK = "no_oem_unlock";
    public static final java.lang.String DISALLOW_OUTGOING_BEAM = "no_outgoing_beam";
    public static final java.lang.String DISALLOW_OUTGOING_CALLS = "no_outgoing_calls";
    public static final java.lang.String DISALLOW_PRINTING = "no_printing";
    public static final java.lang.String DISALLOW_RECORD_AUDIO = "no_record_audio";

    @java.lang.Deprecated
    public static final java.lang.String DISALLOW_REMOVE_MANAGED_PROFILE = "no_remove_managed_profile";
    public static final java.lang.String DISALLOW_REMOVE_USER = "no_remove_user";

    @android.annotation.SystemApi
    public static final java.lang.String DISALLOW_RUN_IN_BACKGROUND = "no_run_in_background";
    public static final java.lang.String DISALLOW_SAFE_BOOT = "no_safe_boot";
    public static final java.lang.String DISALLOW_SET_USER_ICON = "no_set_user_icon";
    public static final java.lang.String DISALLOW_SET_WALLPAPER = "no_set_wallpaper";
    public static final java.lang.String DISALLOW_SHARE_INTO_MANAGED_PROFILE = "no_sharing_into_profile";
    public static final java.lang.String DISALLOW_SHARE_LOCATION = "no_share_location";
    public static final java.lang.String DISALLOW_SHARING_ADMIN_CONFIGURED_WIFI = "no_sharing_admin_configured_wifi";
    public static final java.lang.String DISALLOW_SIM_GLOBALLY = "no_sim_globally";
    public static final java.lang.String DISALLOW_SMS = "no_sms";
    public static final java.lang.String DISALLOW_SYSTEM_ERROR_DIALOGS = "no_system_error_dialogs";
    public static final java.lang.String DISALLOW_THREAD_NETWORK = "no_thread_network";
    public static final java.lang.String DISALLOW_ULTRA_WIDEBAND_RADIO = "no_ultra_wideband_radio";
    public static final java.lang.String DISALLOW_UNIFIED_PASSWORD = "no_unified_password";
    public static final java.lang.String DISALLOW_UNINSTALL_APPS = "no_uninstall_apps";
    public static final java.lang.String DISALLOW_UNMUTE_DEVICE = "disallow_unmute_device";
    public static final java.lang.String DISALLOW_UNMUTE_MICROPHONE = "no_unmute_microphone";
    public static final java.lang.String DISALLOW_USB_FILE_TRANSFER = "no_usb_file_transfer";
    public static final java.lang.String DISALLOW_USER_SWITCH = "no_user_switch";
    public static final java.lang.String DISALLOW_WALLPAPER = "no_wallpaper";
    public static final java.lang.String DISALLOW_WIFI_DIRECT = "no_wifi_direct";
    public static final java.lang.String DISALLOW_WIFI_TETHERING = "no_wifi_tethering";
    public static final java.lang.String ENSURE_VERIFY_APPS = "ensure_verify_apps";
    public static final java.lang.String EXTRA_USER_ACCOUNT_NAME = "android.os.extra.USER_ACCOUNT_NAME";
    public static final java.lang.String EXTRA_USER_ACCOUNT_OPTIONS = "android.os.extra.USER_ACCOUNT_OPTIONS";
    public static final java.lang.String EXTRA_USER_ACCOUNT_TYPE = "android.os.extra.USER_ACCOUNT_TYPE";
    public static final java.lang.String EXTRA_USER_NAME = "android.os.extra.USER_NAME";
    public static final java.lang.String KEY_RESTRICTIONS_PENDING = "restrictions_pending";
    public static final int MAX_ACCOUNT_OPTIONS_LENGTH = 1000;
    public static final int MAX_ACCOUNT_STRING_LENGTH = 500;
    public static final int MAX_USER_NAME_LENGTH = 100;
    public static final int PIN_VERIFICATION_FAILED_INCORRECT = -3;
    public static final int PIN_VERIFICATION_FAILED_NOT_SET = -2;
    public static final int PIN_VERIFICATION_SUCCESS = -1;
    public static final int QUIET_MODE_DISABLE_DONT_ASK_CREDENTIAL = 2;
    public static final int QUIET_MODE_DISABLE_ONLY_IF_CREDENTIAL_NOT_REQUIRED = 1;

    @android.annotation.SystemApi
    public static final int REMOVE_RESULT_ALREADY_BEING_REMOVED = 2;

    @android.annotation.SystemApi
    public static final int REMOVE_RESULT_DEFERRED = 1;

    @android.annotation.SystemApi
    public static final int REMOVE_RESULT_ERROR_MAIN_USER_PERMANENT_ADMIN = -5;

    @android.annotation.SystemApi
    public static final int REMOVE_RESULT_ERROR_SYSTEM_USER = -4;

    @android.annotation.SystemApi
    public static final int REMOVE_RESULT_ERROR_UNKNOWN = -1;

    @android.annotation.SystemApi
    public static final int REMOVE_RESULT_ERROR_USER_NOT_FOUND = -3;

    @android.annotation.SystemApi
    public static final int REMOVE_RESULT_ERROR_USER_RESTRICTION = -2;

    @android.annotation.SystemApi
    public static final int REMOVE_RESULT_REMOVED = 0;
    public static final int REMOVE_RESULT_USER_IS_REMOVABLE = 3;

    @android.annotation.SystemApi
    public static final int RESTRICTION_NOT_SET = 0;

    @android.annotation.SystemApi
    public static final int RESTRICTION_SOURCE_DEVICE_OWNER = 2;

    @android.annotation.SystemApi
    public static final int RESTRICTION_SOURCE_PROFILE_OWNER = 4;

    @android.annotation.SystemApi
    public static final int RESTRICTION_SOURCE_SYSTEM = 1;

    @android.annotation.SystemApi
    public static final int SWITCHABILITY_STATUS_OK = 0;

    @android.annotation.SystemApi
    public static final int SWITCHABILITY_STATUS_SYSTEM_USER_LOCKED = 4;

    @android.annotation.SystemApi
    public static final int SWITCHABILITY_STATUS_USER_IN_CALL = 1;

    @android.annotation.SystemApi
    public static final int SWITCHABILITY_STATUS_USER_SWITCH_DISALLOWED = 2;
    public static final java.lang.String SYSTEM_USER_MODE_EMULATION_DEFAULT = "default";
    public static final java.lang.String SYSTEM_USER_MODE_EMULATION_FULL = "full";
    public static final java.lang.String SYSTEM_USER_MODE_EMULATION_HEADLESS = "headless";
    public static final java.lang.String SYSTEM_USER_MODE_EMULATION_PROPERTY = "persist.debug.user_mode_emulation";
    private static final java.lang.String TAG = "UserManager";
    public static final int USER_CREATION_FAILED_NOT_PERMITTED = 1;
    public static final int USER_CREATION_FAILED_NO_MORE_USERS = 2;
    public static final int USER_OPERATION_ERROR_CURRENT_USER = 4;
    public static final int USER_OPERATION_ERROR_LOW_STORAGE = 5;
    public static final int USER_OPERATION_ERROR_MANAGED_PROFILE = 2;
    public static final int USER_OPERATION_ERROR_MAX_RUNNING_USERS = 3;
    public static final int USER_OPERATION_ERROR_MAX_USERS = 6;
    public static final int USER_OPERATION_ERROR_UNKNOWN = 1;

    @android.annotation.SystemApi
    public static final int USER_OPERATION_ERROR_USER_ACCOUNT_ALREADY_EXISTS = 7;
    public static final int USER_OPERATION_SUCCESS = 0;
    public static final java.lang.String USER_TYPE_FULL_DEMO = "android.os.usertype.full.DEMO";

    @android.annotation.SystemApi
    public static final java.lang.String USER_TYPE_FULL_GUEST = "android.os.usertype.full.GUEST";
    public static final java.lang.String USER_TYPE_FULL_RESTRICTED = "android.os.usertype.full.RESTRICTED";

    @android.annotation.SystemApi
    public static final java.lang.String USER_TYPE_FULL_SECONDARY = "android.os.usertype.full.SECONDARY";

    @android.annotation.SystemApi
    public static final java.lang.String USER_TYPE_FULL_SYSTEM = "android.os.usertype.full.SYSTEM";
    public static final java.lang.String USER_TYPE_PROFILE_CLONE = "android.os.usertype.profile.CLONE";
    public static final java.lang.String USER_TYPE_PROFILE_COMMUNAL = "android.os.usertype.profile.COMMUNAL";
    public static final java.lang.String USER_TYPE_PROFILE_MANAGED = "android.os.usertype.profile.MANAGED";
    public static final java.lang.String USER_TYPE_PROFILE_PRIVATE = "android.os.usertype.profile.PRIVATE";
    public static final java.lang.String USER_TYPE_PROFILE_TEST = "android.os.usertype.profile.TEST";

    @android.annotation.SystemApi
    public static final java.lang.String USER_TYPE_SYSTEM_HEADLESS = "android.os.usertype.system.HEADLESS";
    private static java.lang.Boolean sIsHeadlessSystemUser = null;
    private final android.content.Context mContext;
    private final android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.Boolean> mIsUserUnlockedCache;
    private final android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.Boolean> mIsUserUnlockingOrUnlockedCache;
    private final android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.String> mProfileTypeCache;
    private java.lang.String mProfileTypeOfProcessUser = null;
    private final android.os.IUserManager mService;
    private final int mUserId;
    private final android.app.PropertyInvalidatedCache<java.lang.Integer, android.content.pm.UserProperties> mUserPropertiesCache;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface QuietModeFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RemoveResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserOperationResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserRestrictionKey {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserRestrictionSource {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserSwitchabilityResult {
    }

    public static class UserOperationException extends java.lang.RuntimeException {
        private final int mUserOperationResult;

        public UserOperationException(java.lang.String str, int i) {
            super(str);
            this.mUserOperationResult = i;
        }

        public int getUserOperationResult() {
            return this.mUserOperationResult;
        }

        public static android.os.UserManager.UserOperationException from(android.os.ServiceSpecificException serviceSpecificException) {
            return new android.os.UserManager.UserOperationException(serviceSpecificException.getMessage(), serviceSpecificException.errorCode);
        }
    }

    private <T> T returnNullOrThrowUserOperationException(android.os.ServiceSpecificException serviceSpecificException, boolean z) throws android.os.UserManager.UserOperationException {
        if (z) {
            throw android.os.UserManager.UserOperationException.from(serviceSpecificException);
        }
        return null;
    }

    public static class CheckedUserOperationException extends android.util.AndroidException {
        private final int mUserOperationResult;

        public CheckedUserOperationException(java.lang.String str, int i) {
            super(str);
            this.mUserOperationResult = i;
        }

        public int getUserOperationResult() {
            return this.mUserOperationResult;
        }

        public android.os.ServiceSpecificException toServiceSpecificException() {
            return new android.os.ServiceSpecificException(this.mUserOperationResult, getMessage());
        }
    }

    private int getContextUserIfAppropriate() {
        if (android.app.compat.CompatChanges.isChangeEnabled(ALWAYS_USE_CONTEXT_USER)) {
            return this.mUserId;
        }
        int myUserId = android.os.UserHandle.myUserId();
        if (myUserId != this.mUserId) {
            android.util.Log.w(TAG, "Using the calling user " + myUserId + ", rather than the specified context user " + this.mUserId + ", because API is only UserHandleAware on higher targetSdkVersions.", new java.lang.Throwable());
        }
        return myUserId;
    }

    public static android.os.UserManager get(android.content.Context context) {
        return (android.os.UserManager) context.getSystemService("user");
    }

    public UserManager(android.content.Context context, android.os.IUserManager iUserManager) {
        int i = 32;
        java.lang.String str = CACHE_KEY_IS_USER_UNLOCKED_PROPERTY;
        this.mIsUserUnlockedCache = new android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.Boolean>(i, str) { // from class: android.os.UserManager.1
            @Override // android.app.PropertyInvalidatedCache
            public java.lang.Boolean recompute(java.lang.Integer num) {
                try {
                    return java.lang.Boolean.valueOf(android.os.UserManager.this.mService.isUserUnlocked(num.intValue()));
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean bypass(java.lang.Integer num) {
                return num.intValue() < 0;
            }
        };
        this.mIsUserUnlockingOrUnlockedCache = new android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.Boolean>(i, str) { // from class: android.os.UserManager.2
            @Override // android.app.PropertyInvalidatedCache
            public java.lang.Boolean recompute(java.lang.Integer num) {
                try {
                    return java.lang.Boolean.valueOf(android.os.UserManager.this.mService.isUserUnlockingOrUnlocked(num.intValue()));
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean bypass(java.lang.Integer num) {
                return num.intValue() < 0;
            }
        };
        this.mProfileTypeCache = new android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.String>(i, CACHE_KEY_STATIC_USER_PROPERTIES) { // from class: android.os.UserManager.3
            @Override // android.app.PropertyInvalidatedCache
            public java.lang.String recompute(java.lang.Integer num) {
                try {
                    java.lang.String profileType = android.os.UserManager.this.mService.getProfileType(num.intValue());
                    return profileType != null ? profileType.intern() : profileType;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean bypass(java.lang.Integer num) {
                return num.intValue() < 0;
            }
        };
        this.mUserPropertiesCache = new android.app.PropertyInvalidatedCache<java.lang.Integer, android.content.pm.UserProperties>(16, CACHE_KEY_USER_PROPERTIES) { // from class: android.os.UserManager.4
            @Override // android.app.PropertyInvalidatedCache
            public android.content.pm.UserProperties recompute(java.lang.Integer num) {
                try {
                    return android.os.UserManager.this.mService.getUserPropertiesCopy(num.intValue());
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean bypass(java.lang.Integer num) {
                return num.intValue() < 0;
            }
        };
        this.mService = iUserManager;
        android.content.Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext == null ? context : applicationContext;
        this.mUserId = context.getUserId();
    }

    public static boolean supportsMultipleUsers() {
        return getMaxSupportedUsers() > 1 && android.os.SystemProperties.getBoolean("fw.show_multiuserui", android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_enableMultiUserUI));
    }

    public static boolean isGuestUserAlwaysEphemeral() {
        return android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_guestUserEphemeral);
    }

    public static boolean isGuestUserAllowEphemeralStateChange() {
        return android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_guestUserAllowEphemeralStateChange);
    }

    public static boolean isCommunalProfileEnabled() {
        return android.os.SystemProperties.getBoolean("persist.fw.omnipresent_communal_user", android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_omnipresentCommunalUser));
    }

    public static boolean isMultipleAdminEnabled() {
        return android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_enableMultipleAdmins);
    }

    public static boolean isHeadlessSystemUserMode() {
        if (sIsHeadlessSystemUser == null) {
            try {
                sIsHeadlessSystemUser = java.lang.Boolean.valueOf(android.os.IUserManager.Stub.asInterface(android.os.ServiceManager.getService("user")).isHeadlessSystemUserMode());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return sIsHeadlessSystemUser.booleanValue();
    }

    @java.lang.Deprecated
    public boolean canSwitchUsers() {
        try {
            return this.mService.getUserSwitchability(this.mUserId) == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getUserSwitchability() {
        return getUserSwitchability(android.os.UserHandle.of(getContextUserIfAppropriate()));
    }

    public int getUserSwitchability(android.os.UserHandle userHandle) {
        try {
            return this.mService.getUserSwitchability(userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int getUserHandle() {
        return getContextUserIfAppropriate();
    }

    @java.lang.Deprecated
    public int getProcessUserId() {
        return android.os.UserHandle.myUserId();
    }

    public java.lang.String getUserType() {
        android.content.pm.UserInfo userInfo = getUserInfo(this.mUserId);
        return userInfo == null ? "" : userInfo.userType;
    }

    public java.lang.String getUserName() {
        if (android.os.UserHandle.myUserId() == this.mUserId) {
            try {
                return this.mService.getUserName();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.content.pm.UserInfo userInfo = getUserInfo(this.mUserId);
        if (userInfo != null && userInfo.name != null) {
            return userInfo.name;
        }
        return "";
    }

    @android.annotation.SystemApi
    public boolean isUserNameSet() {
        try {
            return this.mService.isUserNameSet(getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserAGoat() {
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 30) {
            return false;
        }
        return this.mContext.getPackageManager().isPackageAvailable("com.coffeestainstudios.goatsimulator");
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isPrimaryUser() {
        android.content.pm.UserInfo userInfo = getUserInfo(getContextUserIfAppropriate());
        return userInfo != null && userInfo.isPrimary();
    }

    public boolean isSystemUser() {
        return getContextUserIfAppropriate() == 0;
    }

    @android.annotation.SystemApi
    public boolean isMainUser() {
        android.content.pm.UserInfo userInfo = getUserInfo(this.mUserId);
        return userInfo != null && userInfo.isMain();
    }

    @android.annotation.SystemApi
    public android.os.UserHandle getMainUser() {
        try {
            int mainUserId = this.mService.getMainUserId();
            if (mainUserId == -10000) {
                return null;
            }
            return android.os.UserHandle.of(mainUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.UserHandle getCommunalProfile() {
        try {
            int communalProfileId = this.mService.getCommunalProfileId();
            if (communalProfileId == -10000) {
                return null;
            }
            return android.os.UserHandle.of(communalProfileId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCommunalProfile() {
        return isCommunalProfile(this.mUserId);
    }

    private boolean isCommunalProfile(int i) {
        return isUserTypeCommunalProfile(getProfileType(i));
    }

    public boolean isAdminUser() {
        try {
            return this.mService.isAdminUser(getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserAdmin(int i) {
        android.content.pm.UserInfo userInfo = getUserInfo(i);
        return userInfo != null && userInfo.isAdmin();
    }

    public boolean isForegroundUserAdmin() {
        try {
            return this.mService.isForegroundUserAdmin();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isUserOfType(java.lang.String str) {
        try {
            return this.mService.isUserOfType(this.mUserId, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isUserTypeManagedProfile(java.lang.String str) {
        return USER_TYPE_PROFILE_MANAGED.equals(str);
    }

    public static boolean isUserTypeGuest(java.lang.String str) {
        return USER_TYPE_FULL_GUEST.equals(str);
    }

    public static boolean isUserTypeRestricted(java.lang.String str) {
        return USER_TYPE_FULL_RESTRICTED.equals(str);
    }

    public static boolean isUserTypeDemo(java.lang.String str) {
        return USER_TYPE_FULL_DEMO.equals(str);
    }

    public static boolean isUserTypeCloneProfile(java.lang.String str) {
        return USER_TYPE_PROFILE_CLONE.equals(str);
    }

    public static boolean isUserTypeCommunalProfile(java.lang.String str) {
        return USER_TYPE_PROFILE_COMMUNAL.equals(str);
    }

    public static boolean isUserTypePrivateProfile(java.lang.String str) {
        return USER_TYPE_PROFILE_PRIVATE.equals(str);
    }

    @java.lang.Deprecated
    public boolean isLinkedUser() {
        return isRestrictedProfile();
    }

    @android.annotation.SystemApi
    public boolean isRestrictedProfile() {
        try {
            return this.mService.isRestricted(getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isRestrictedProfile(android.os.UserHandle userHandle) {
        try {
            return this.mService.isRestricted(userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean canHaveRestrictedProfile() {
        try {
            return this.mService.canHaveRestrictedProfile(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean hasRestrictedProfiles() {
        try {
            return this.mService.hasRestrictedProfiles(getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.UserHandle getRestrictedProfileParent() {
        int i;
        android.content.pm.UserInfo userInfo = getUserInfo(this.mUserId);
        if (userInfo == null || !userInfo.isRestricted() || (i = userInfo.restrictedProfileParentId) == -10000) {
            return null;
        }
        return android.os.UserHandle.of(i);
    }

    public boolean isGuestUser(int i) {
        android.content.pm.UserInfo userInfo = getUserInfo(i);
        return userInfo != null && userInfo.isGuest();
    }

    @android.annotation.SystemApi
    public boolean isGuestUser() {
        android.content.pm.UserInfo userInfo = getUserInfo(getContextUserIfAppropriate());
        return userInfo != null && userInfo.isGuest();
    }

    public boolean isDemoUser() {
        try {
            return this.mService.isDemoUser(getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isProfile() {
        return isProfile(this.mUserId);
    }

    public boolean isProfile(int i) {
        java.lang.String profileType = getProfileType(i);
        return (profileType == null || profileType.equals("")) ? false : true;
    }

    private java.lang.String getProfileType() {
        return getProfileType(this.mUserId);
    }

    private java.lang.String getProfileType(int i) {
        if (i == android.os.UserHandle.myUserId()) {
            if (this.mProfileTypeOfProcessUser != null) {
                return this.mProfileTypeOfProcessUser;
            }
            try {
                java.lang.String profileType = this.mService.getProfileType(i);
                if (profileType != null) {
                    java.lang.String intern = profileType.intern();
                    this.mProfileTypeOfProcessUser = intern;
                    return intern;
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mProfileTypeCache.query(java.lang.Integer.valueOf(i));
    }

    public boolean isManagedProfile() {
        return isManagedProfile(getContextUserIfAppropriate());
    }

    @android.annotation.SystemApi
    public boolean isManagedProfile(int i) {
        return isUserTypeManagedProfile(getProfileType(i));
    }

    @android.annotation.SystemApi
    public boolean isCloneProfile() {
        return isUserTypeCloneProfile(getProfileType());
    }

    @android.annotation.SystemApi
    public boolean isPrivateProfile() {
        return isUserTypePrivateProfile(getProfileType());
    }

    public boolean isEphemeralUser() {
        return isUserEphemeral(this.mUserId);
    }

    public boolean isUserEphemeral(int i) {
        android.content.pm.UserInfo userInfo = getUserInfo(i);
        return userInfo != null && userInfo.isEphemeral();
    }

    public boolean isUserRunning(android.os.UserHandle userHandle) {
        return isUserRunning(userHandle.getIdentifier());
    }

    public boolean isUserRunning(int i) {
        try {
            return this.mService.isUserRunning(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserRunningOrStopping(android.os.UserHandle userHandle) {
        try {
            return android.app.ActivityManager.getService().isUserRunning(userHandle.getIdentifier(), 1);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserForeground() {
        try {
            return this.mService.isUserForeground(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isVisibleBackgroundUsersEnabled() {
        return android.os.SystemProperties.getBoolean("fw.visible_bg_users", android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_multiuserVisibleBackgroundUsers));
    }

    public boolean isVisibleBackgroundUsersSupported() {
        return isVisibleBackgroundUsersEnabled();
    }

    public static boolean isVisibleBackgroundUsersOnDefaultDisplayEnabled() {
        return android.os.SystemProperties.getBoolean("fw.visible_bg_users_on_default_display", android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_multiuserVisibleBackgroundUsersOnDefaultDisplay));
    }

    public boolean isVisibleBackgroundUsersOnDefaultDisplaySupported() {
        return isVisibleBackgroundUsersOnDefaultDisplayEnabled();
    }

    @android.annotation.SystemApi
    public boolean isUserVisible() {
        try {
            return this.mService.isUserVisible(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.Set<android.os.UserHandle> getVisibleUsers() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        try {
            int[] visibleUsers = this.mService.getVisibleUsers();
            if (visibleUsers != null) {
                for (int i : visibleUsers) {
                    arraySet.add(android.os.UserHandle.of(i));
                }
            }
            return arraySet;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMainDisplayIdAssignedToUser() {
        try {
            return this.mService.getMainDisplayIdAssignedToUser();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserUnlocked() {
        return isUserUnlocked(getContextUserIfAppropriate());
    }

    public boolean isUserUnlocked(android.os.UserHandle userHandle) {
        return isUserUnlocked(userHandle.getIdentifier());
    }

    public boolean isUserUnlocked(int i) {
        return this.mIsUserUnlockedCache.query(java.lang.Integer.valueOf(i)).booleanValue();
    }

    public void disableIsUserUnlockedCache() {
        this.mIsUserUnlockedCache.disableLocal();
        this.mIsUserUnlockingOrUnlockedCache.disableLocal();
    }

    public static final void invalidateIsUserUnlockedCache() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_IS_USER_UNLOCKED_PROPERTY);
    }

    @android.annotation.SystemApi
    public boolean isUserUnlockingOrUnlocked(android.os.UserHandle userHandle) {
        return isUserUnlockingOrUnlocked(userHandle.getIdentifier());
    }

    public boolean isUserUnlockingOrUnlocked(int i) {
        return this.mIsUserUnlockingOrUnlockedCache.query(java.lang.Integer.valueOf(i)).booleanValue();
    }

    public long getUserStartRealtime() {
        if (getContextUserIfAppropriate() != android.os.UserHandle.myUserId()) {
            throw new java.lang.IllegalArgumentException("Calling from a context differing from the calling user is not currently supported.");
        }
        try {
            return this.mService.getUserStartRealtime();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getUserUnlockRealtime() {
        if (getContextUserIfAppropriate() != android.os.UserHandle.myUserId()) {
            throw new java.lang.IllegalArgumentException("Calling from a context differing from the calling user is not currently supported.");
        }
        try {
            return this.mService.getUserUnlockRealtime();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.pm.UserInfo getUserInfo(int i) {
        try {
            return this.mService.getUserInfo(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.content.pm.UserProperties getUserProperties(android.os.UserHandle userHandle) {
        return this.mUserPropertiesCache.query(java.lang.Integer.valueOf(userHandle.getIdentifier()));
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public int getUserRestrictionSource(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mService.getUserRestrictionSource(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.os.UserManager.EnforcingUser> getUserRestrictionSources(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mService.getUserRestrictionSources(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.Bundle getUserRestrictions() {
        try {
            return this.mService.getUserRestrictions(getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.Bundle getUserRestrictions(android.os.UserHandle userHandle) {
        try {
            return this.mService.getUserRestrictions(userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasBaseUserRestriction(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mService.hasBaseUserRestriction(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void setUserRestrictions(android.os.Bundle bundle) {
        throw new java.lang.UnsupportedOperationException("This method is no longer supported");
    }

    @java.lang.Deprecated
    public void setUserRestrictions(android.os.Bundle bundle, android.os.UserHandle userHandle) {
        throw new java.lang.UnsupportedOperationException("This method is no longer supported");
    }

    @java.lang.Deprecated
    public void setUserRestriction(java.lang.String str, boolean z) {
        try {
            this.mService.setUserRestriction(str, z, getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void setUserRestriction(java.lang.String str, boolean z, android.os.UserHandle userHandle) {
        try {
            this.mService.setUserRestriction(str, z, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasUserRestriction(java.lang.String str) {
        return hasUserRestrictionForUser(str, getContextUserIfAppropriate());
    }

    @java.lang.Deprecated
    public boolean hasUserRestriction(java.lang.String str, android.os.UserHandle userHandle) {
        return hasUserRestrictionForUser(str, userHandle);
    }

    @android.annotation.SystemApi
    public boolean hasUserRestrictionForUser(java.lang.String str, android.os.UserHandle userHandle) {
        return hasUserRestrictionForUser(str, userHandle.getIdentifier());
    }

    private boolean hasUserRestrictionForUser(java.lang.String str, int i) {
        try {
            return this.mService.hasUserRestriction(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasUserRestrictionOnAnyUser(java.lang.String str) {
        try {
            return this.mService.hasUserRestrictionOnAnyUser(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSettingRestrictedForUser(java.lang.String str, int i, java.lang.String str2, int i2) {
        try {
            return this.mService.isSettingRestrictedForUser(str, i, str2, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addUserRestrictionsListener(android.os.IUserRestrictionsListener iUserRestrictionsListener) {
        try {
            this.mService.addUserRestrictionsListener(iUserRestrictionsListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getSerialNumberForUser(android.os.UserHandle userHandle) {
        return getUserSerialNumber(userHandle.getIdentifier());
    }

    public android.os.UserHandle getUserForSerialNumber(long j) {
        int userHandle = getUserHandle((int) j);
        if (userHandle >= 0) {
            return new android.os.UserHandle(userHandle);
        }
        return null;
    }

    @java.lang.Deprecated
    public android.content.pm.UserInfo createUser(java.lang.String str, int i) {
        return createUser(str, android.content.pm.UserInfo.getDefaultUserType(i), i);
    }

    public android.content.pm.UserInfo createUser(java.lang.String str, java.lang.String str2, int i) {
        try {
            return this.mService.createUserWithThrow(str, str2, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public android.os.NewUserResponse createUser(android.os.NewUserRequest newUserRequest) {
        try {
            return new android.os.NewUserResponse(this.mService.createUserWithAttributes(newUserRequest.getName(), newUserRequest.getUserType(), newUserRequest.getFlags(), newUserRequest.getUserIcon(), newUserRequest.getAccountName(), newUserRequest.getAccountType(), newUserRequest.getAccountOptions()), 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            android.util.Log.w(TAG, "Exception while creating user " + newUserRequest, e2);
            return new android.os.NewUserResponse(null, e2.errorCode);
        }
    }

    @java.lang.Deprecated
    public android.content.pm.UserInfo preCreateUser(java.lang.String str) throws android.os.UserManager.UserOperationException {
        android.util.Log.w(TAG, "preCreateUser(): Pre-created user is deprecated.");
        try {
            return this.mService.preCreateUserWithThrow(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw android.os.UserManager.UserOperationException.from(e2);
        }
    }

    public android.content.pm.UserInfo createGuest(android.content.Context context) {
        try {
            android.content.pm.UserInfo createUserWithThrow = this.mService.createUserWithThrow(null, USER_TYPE_FULL_GUEST, 0);
            android.provider.Settings.Secure.putStringForUser(context.getContentResolver(), android.provider.Settings.Secure.SKIP_FIRST_USE_HINTS, "1", createUserWithThrow.id);
            if (isGuestUserAllowEphemeralStateChange()) {
                if ((android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.REMOVE_GUEST_ON_EXIT, 1) == 1) && !createUserWithThrow.isEphemeral()) {
                    setUserEphemeral(createUserWithThrow.id, true);
                }
            }
            return createUserWithThrow;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            return null;
        }
    }

    @java.lang.Deprecated
    public android.content.pm.UserInfo findCurrentGuestUser() {
        try {
            java.util.List<android.content.pm.UserInfo> guestUsers = this.mService.getGuestUsers();
            if (guestUsers.size() == 0) {
                return null;
            }
            return guestUsers.get(0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.UserInfo> getGuestUsers() {
        try {
            return this.mService.getGuestUsers();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.UserHandle createProfile(java.lang.String str, java.lang.String str2, java.util.Set<java.lang.String> set) throws android.os.UserManager.UserOperationException {
        try {
            return this.mService.createProfileForUserWithThrow(str, str2, 0, this.mUserId, (java.lang.String[]) set.toArray(new java.lang.String[set.size()])).getUserHandle();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            return (android.os.UserHandle) returnNullOrThrowUserOperationException(e2, this.mContext.getApplicationInfo().targetSdkVersion >= 30);
        }
    }

    @java.lang.Deprecated
    public android.content.pm.UserInfo createProfileForUser(java.lang.String str, int i, int i2) {
        return createProfileForUser(str, android.content.pm.UserInfo.getDefaultUserType(i), i, i2, null);
    }

    public android.content.pm.UserInfo createProfileForUser(java.lang.String str, java.lang.String str2, int i, int i2) {
        return createProfileForUser(str, str2, i, i2, null);
    }

    public android.content.pm.UserInfo createProfileForUser(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String[] strArr) {
        try {
            return this.mService.createProfileForUserWithThrow(str, str2, i, i2, strArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            return null;
        }
    }

    public android.content.pm.UserInfo createProfileForUserEvenWhenDisallowed(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String[] strArr) {
        try {
            return this.mService.createProfileForUserEvenWhenDisallowedWithThrow(str, str2, i, i2, strArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            return null;
        }
    }

    public android.content.pm.UserInfo createRestrictedProfile(java.lang.String str) {
        try {
            int i = this.mUserId;
            android.content.pm.UserInfo createRestrictedProfileWithThrow = this.mService.createRestrictedProfileWithThrow(str, i);
            android.accounts.AccountManager.get(this.mContext).addSharedAccountsFromParentUser(android.os.UserHandle.of(i), android.os.UserHandle.of(createRestrictedProfileWithThrow.id));
            return createRestrictedProfileWithThrow;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            return null;
        }
    }

    public static android.content.Intent createUserCreationIntent(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.PersistableBundle persistableBundle) {
        android.content.Intent intent = new android.content.Intent(ACTION_CREATE_USER);
        if (str != null) {
            intent.putExtra(EXTRA_USER_NAME, str);
        }
        if (str2 != null && str3 == null) {
            throw new java.lang.IllegalArgumentException("accountType must be specified if accountName is specified");
        }
        if (str2 != null) {
            intent.putExtra(EXTRA_USER_ACCOUNT_NAME, str2);
        }
        if (str3 != null) {
            intent.putExtra(EXTRA_USER_ACCOUNT_TYPE, str3);
        }
        if (persistableBundle != null) {
            intent.putExtra(EXTRA_USER_ACCOUNT_OPTIONS, persistableBundle);
        }
        return intent;
    }

    public java.util.Set<java.lang.String> getPreInstallableSystemPackages(java.lang.String str) {
        try {
            java.lang.String[] preInstallableSystemPackages = this.mService.getPreInstallableSystemPackages(str);
            if (preInstallableSystemPackages == null) {
                return null;
            }
            return new android.util.ArraySet(preInstallableSystemPackages);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getSeedAccountName() {
        try {
            return this.mService.getSeedAccountName(getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getSeedAccountType() {
        try {
            return this.mService.getSeedAccountType(getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.PersistableBundle getSeedAccountOptions() {
        try {
            return this.mService.getSeedAccountOptions(getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSeedAccountData(int i, java.lang.String str, java.lang.String str2, android.os.PersistableBundle persistableBundle) {
        try {
            this.mService.setSeedAccountData(i, str, str2, persistableBundle, true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void clearSeedAccountData() {
        try {
            this.mService.clearSeedAccountData(getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean markGuestForDeletion(int i) {
        try {
            return this.mService.markGuestForDeletion(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserEnabled(int i) {
        try {
            this.mService.setUserEnabled(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserAdmin(int i) {
        try {
            this.mService.setUserAdmin(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void revokeUserAdmin(int i) {
        try {
            this.mService.revokeUserAdmin(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void evictCredentialEncryptionKey(int i) {
        try {
            this.mService.evictCredentialEncryptionKey(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserCount() {
        java.util.List<android.content.pm.UserInfo> users = getUsers();
        if (users != null) {
            return users.size();
        }
        return 1;
    }

    public java.util.List<android.content.pm.UserInfo> getUsers() {
        return getUsers(true, false, true);
    }

    public java.util.List<android.content.pm.UserInfo> getAliveUsers() {
        return getUsers(true, true, true);
    }

    @java.lang.Deprecated
    public java.util.List<android.content.pm.UserInfo> getUsers(boolean z) {
        return getUsers(true, z, true);
    }

    @java.lang.Deprecated
    public java.util.List<android.content.pm.UserInfo> getUsers(boolean z, boolean z2, boolean z3) {
        try {
            return this.mService.getUsers(z, z2, z3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.os.UserHandle> getUserHandles(boolean z) {
        java.util.List<android.content.pm.UserInfo> users = getUsers(true, z, true);
        java.util.ArrayList arrayList = new java.util.ArrayList(users.size());
        java.util.Iterator<android.content.pm.UserInfo> it = users.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getUserHandle());
        }
        return arrayList;
    }

    @android.annotation.SystemApi
    public long[] getSerialNumbersOfUsers(boolean z) {
        int size = getUsers(true, z, true).size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = r6.get(i).serialNumber;
        }
        return jArr;
    }

    public java.lang.String getUserAccount(int i) {
        try {
            return this.mService.getUserAccount(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserAccount(int i, java.lang.String str) {
        try {
            this.mService.setUserAccount(i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.content.pm.UserInfo getPrimaryUser() {
        try {
            return this.mService.getPrimaryUser();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.UserHandle getPreviousForegroundUser() {
        try {
            int previousFullUserToEnterForeground = this.mService.getPreviousFullUserToEnterForeground();
            if (previousFullUserToEnterForeground == -10000) {
                return null;
            }
            return android.os.UserHandle.of(previousFullUserToEnterForeground);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public boolean canAddMoreUsers() {
        java.util.List<android.content.pm.UserInfo> aliveUsers = getAliveUsers();
        int size = aliveUsers.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (!aliveUsers.get(i2).isGuest()) {
                i++;
            }
        }
        return i < getMaxSupportedUsers();
    }

    public boolean canAddMoreUsers(java.lang.String str) {
        try {
            if (str.equals(USER_TYPE_FULL_GUEST)) {
                return this.mService.canAddMoreUsersOfType(str);
            }
            return canAddMoreUsers() && this.mService.canAddMoreUsersOfType(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getRemainingCreatableUserCount(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "userType must not be null");
        try {
            return this.mService.getRemainingCreatableUserCount(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getRemainingCreatableProfileCount(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "userType must not be null");
        try {
            return this.mService.getRemainingCreatableProfileCount(str, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canAddMoreManagedProfiles(int i, boolean z) {
        try {
            return this.mService.canAddMoreManagedProfiles(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canAddMoreProfilesToUser(java.lang.String str, int i) {
        try {
            return this.mService.canAddMoreProfilesToUser(str, i, false);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserTypeEnabled(java.lang.String str) {
        try {
            return this.mService.isUserTypeEnabled(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.UserInfo> getProfiles(int i) {
        try {
            return this.mService.getProfiles(i, false);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.UserInfo> getProfilesIncludingCommunal(int i) {
        android.content.pm.UserInfo userInfo;
        java.util.List<android.content.pm.UserInfo> profiles = getProfiles(i);
        android.os.UserHandle communalProfile = getCommunalProfile();
        if (communalProfile != null && (userInfo = getUserInfo(communalProfile.getIdentifier())) != null) {
            profiles.add(userInfo);
        }
        return profiles;
    }

    @android.annotation.SystemApi
    public boolean isSameProfileGroup(android.os.UserHandle userHandle, android.os.UserHandle userHandle2) {
        return isSameProfileGroup(userHandle.getIdentifier(), userHandle2.getIdentifier());
    }

    public boolean isSameProfileGroup(int i, int i2) {
        try {
            return this.mService.isSameProfileGroup(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public java.util.List<android.content.pm.UserInfo> getEnabledProfiles(int i) {
        try {
            return this.mService.getProfiles(i, true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.os.UserHandle> getUserProfiles() {
        return convertUserIdsToUserHandles(getProfileIds(getContextUserIfAppropriate(), true));
    }

    @android.annotation.SystemApi
    public java.util.List<android.os.UserHandle> getEnabledProfiles() {
        return getProfiles(true);
    }

    @android.annotation.SystemApi
    public java.util.List<android.os.UserHandle> getAllProfiles() {
        return getProfiles(false);
    }

    private java.util.List<android.os.UserHandle> getProfiles(boolean z) {
        return convertUserIdsToUserHandles(getProfileIds(this.mUserId, z));
    }

    private java.util.List<android.os.UserHandle> convertUserIdsToUserHandles(int[] iArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(android.os.UserHandle.of(i));
        }
        return arrayList;
    }

    public int[] getProfileIds(int i, boolean z) {
        try {
            return this.mService.getProfileIds(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getProfileIdsWithDisabled(int i) {
        return getProfileIds(i, false);
    }

    public int[] getEnabledProfileIds(int i) {
        return getProfileIds(i, true);
    }

    public int[] getProfileIdsExcludingHidden(int i, boolean z) {
        try {
            return this.mService.getProfileIdsExcludingHidden(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getCredentialOwnerProfile(int i) {
        try {
            return this.mService.getCredentialOwnerProfile(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.pm.UserInfo getProfileParent(int i) {
        try {
            return this.mService.getProfileParent(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.UserHandle getProfileParent(android.os.UserHandle userHandle) {
        android.content.pm.UserInfo profileParent = getProfileParent(userHandle.getIdentifier());
        if (profileParent == null) {
            return null;
        }
        return android.os.UserHandle.of(profileParent.id);
    }

    public boolean requestQuietModeEnabled(boolean z, android.os.UserHandle userHandle) {
        return requestQuietModeEnabled(z, userHandle, (android.content.IntentSender) null);
    }

    public boolean requestQuietModeEnabled(boolean z, android.os.UserHandle userHandle, int i) {
        return requestQuietModeEnabled(z, userHandle, null, i);
    }

    public boolean requestQuietModeEnabled(boolean z, android.os.UserHandle userHandle, android.content.IntentSender intentSender) {
        return requestQuietModeEnabled(z, userHandle, intentSender, 0);
    }

    public boolean requestQuietModeEnabled(boolean z, android.os.UserHandle userHandle, android.content.IntentSender intentSender, int i) {
        try {
            return this.mService.requestQuietModeEnabled(this.mContext.getPackageName(), z, userHandle.getIdentifier(), intentSender, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isQuietModeEnabled(android.os.UserHandle userHandle) {
        try {
            return this.mService.isQuietModeEnabled(userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasBadge(int i) {
        if (!isProfile(i)) {
            return false;
        }
        try {
            return this.mService.hasBadge(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasBadge() {
        return hasBadge(this.mUserId);
    }

    public int getUserBadgeColor(int i) {
        try {
            return android.content.res.Resources.getSystem().getColor(this.mService.getUserBadgeColorResId(i), null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserBadgeDarkColor(int i) {
        try {
            return android.content.res.Resources.getSystem().getColor(this.mService.getUserBadgeDarkColorResId(i), null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserIconBadgeResId(int i) {
        try {
            return this.mService.getUserIconBadgeResId(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserBadgeResId(int i) {
        try {
            return this.mService.getUserBadgeResId(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserBadgeNoBackgroundResId(int i) {
        try {
            return this.mService.getUserBadgeNoBackgroundResId(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserStatusBarIconResId(int i) {
        try {
            return this.mService.getUserStatusBarIconResId(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.graphics.drawable.Drawable getBadgedIconForUser(android.graphics.drawable.Drawable drawable, android.os.UserHandle userHandle) {
        return this.mContext.getPackageManager().getUserBadgedIcon(drawable, userHandle);
    }

    public android.graphics.drawable.Drawable getBadgedDrawableForUser(android.graphics.drawable.Drawable drawable, android.os.UserHandle userHandle, android.graphics.Rect rect, int i) {
        return this.mContext.getPackageManager().getUserBadgedDrawableForDensity(drawable, userHandle, rect, i);
    }

    @android.annotation.SystemApi
    public android.graphics.drawable.Drawable getUserBadge() {
        if (!isProfile(this.mUserId)) {
            throw new android.content.res.Resources.NotFoundException("No badge found for this user.");
        }
        if (isManagedProfile(this.mUserId)) {
            return ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getDrawable(android.app.admin.DevicePolicyResources.Drawables.WORK_PROFILE_ICON_BADGE, android.app.admin.DevicePolicyResources.Drawables.Style.SOLID_COLORED, new java.util.function.Supplier() { // from class: android.os.UserManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.graphics.drawable.Drawable lambda$getUserBadge$0;
                    lambda$getUserBadge$0 = android.os.UserManager.this.lambda$getUserBadge$0();
                    return lambda$getUserBadge$0;
                }
            });
        }
        return getDefaultUserBadge(this.mUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.graphics.drawable.Drawable lambda$getUserBadge$0() {
        return getDefaultUserBadge(this.mUserId);
    }

    private android.graphics.drawable.Drawable getDefaultUserBadge(int i) {
        return this.mContext.getResources().getDrawable(getUserBadgeResId(i), this.mContext.getTheme());
    }

    public java.lang.CharSequence getBadgedLabelForUser(final java.lang.CharSequence charSequence, android.os.UserHandle userHandle) {
        final int identifier = userHandle.getIdentifier();
        if (!hasBadge(identifier)) {
            return charSequence;
        }
        return ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(getUpdatableUserBadgedLabelId(identifier), new java.util.function.Supplier() { // from class: android.os.UserManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getBadgedLabelForUser$1;
                lambda$getBadgedLabelForUser$1 = android.os.UserManager.this.lambda$getBadgedLabelForUser$1(charSequence, identifier);
                return lambda$getBadgedLabelForUser$1;
            }
        }, charSequence);
    }

    private java.lang.String getUpdatableUserBadgedLabelId(int i) {
        return isManagedProfile(i) ? android.app.admin.DevicePolicyResources.Strings.Core.WORK_PROFILE_BADGED_LABEL : android.app.admin.DevicePolicyResources.UNDEFINED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultUserBadgedLabel, reason: merged with bridge method [inline-methods] */
    public java.lang.String lambda$getBadgedLabelForUser$1(java.lang.CharSequence charSequence, int i) {
        try {
            return android.content.res.Resources.getSystem().getString(this.mService.getUserBadgeLabelResId(i), charSequence);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getProfileLabel() {
        if (isManagedProfile(this.mUserId)) {
            return ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB, new java.util.function.Supplier() { // from class: android.os.UserManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getProfileLabel$2;
                    lambda$getProfileLabel$2 = android.os.UserManager.this.lambda$getProfileLabel$2();
                    return lambda$getProfileLabel$2;
                }
            });
        }
        return getDefaultProfileLabel(this.mUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getProfileLabel$2() {
        return getDefaultProfileLabel(this.mUserId);
    }

    private java.lang.String getDefaultProfileLabel(int i) {
        try {
            return android.content.res.Resources.getSystem().getString(this.mService.getProfileLabelResId(i));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isMediaSharedWithParent() {
        try {
            return getUserProperties(android.os.UserHandle.of(this.mUserId)).isMediaSharedWithParent();
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isCredentialSharableWithParent() {
        try {
            return getUserProperties(android.os.UserHandle.of(this.mUserId)).isCredentialShareableWithParent();
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

    public boolean removeUser(int i) {
        try {
            return this.mService.removeUser(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean removeUser(android.os.UserHandle userHandle) {
        if (userHandle == null) {
            throw new java.lang.IllegalArgumentException("user cannot be null");
        }
        return removeUser(userHandle.getIdentifier());
    }

    public boolean removeUserEvenWhenDisallowed(int i) {
        try {
            return this.mService.removeUserEvenWhenDisallowed(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int removeUserWhenPossible(android.os.UserHandle userHandle, boolean z) {
        try {
            return this.mService.removeUserWhenPossible(userHandle.getIdentifier(), z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public static boolean isRemoveResultSuccessful(int i) {
        return i >= 0;
    }

    public void setUserName(int i, java.lang.String str) {
        try {
            this.mService.setUserName(i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setUserEphemeral(int i, boolean z) {
        try {
            return this.mService.setUserEphemeral(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setUserName(java.lang.String str) {
        setUserName(this.mUserId, str);
    }

    public void setUserIcon(int i, android.graphics.Bitmap bitmap) {
        try {
            this.mService.setUserIcon(i, bitmap);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
        }
    }

    @android.annotation.SystemApi
    public void setUserIcon(android.graphics.Bitmap bitmap) throws android.os.UserManager.UserOperationException {
        setUserIcon(this.mUserId, bitmap);
    }

    public android.graphics.Bitmap getUserIcon(int i) {
        try {
            android.os.ParcelFileDescriptor userIcon = this.mService.getUserIcon(i);
            if (userIcon != null) {
                try {
                    return android.graphics.BitmapFactory.decodeFileDescriptor(userIcon.getFileDescriptor());
                } finally {
                    try {
                        userIcon.close();
                    } catch (java.io.IOException e) {
                    }
                }
            }
            return null;
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.graphics.Bitmap getUserIcon() {
        return getUserIcon(this.mUserId);
    }

    public static int getMaxSupportedUsers() {
        if (android.os.Build.ID.startsWith("JVP")) {
            return 1;
        }
        return java.lang.Math.max(1, android.os.SystemProperties.getInt("fw.max_users", android.content.res.Resources.getSystem().getInteger(com.android.internal.R.integer.config_multiuserMaximumUsers)));
    }

    public boolean isUserSwitcherEnabled() {
        return isUserSwitcherEnabled(true);
    }

    public boolean isUserSwitcherEnabled(boolean z) {
        try {
            return this.mService.isUserSwitcherEnabled(z, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isDeviceInDemoMode(android.content.Context context) {
        return android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.DEVICE_DEMO_MODE, 0) > 0;
    }

    public int getUserSerialNumber(int i) {
        try {
            return this.mService.getUserSerialNumber(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUserHandle(int i) {
        try {
            return this.mService.getUserHandle(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.Bundle getApplicationRestrictions(java.lang.String str) {
        try {
            return this.mService.getApplicationRestrictionsForUser(str, getContextUserIfAppropriate());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.Bundle getApplicationRestrictions(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mService.getApplicationRestrictionsForUser(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setApplicationRestrictions(java.lang.String str, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        try {
            this.mService.setApplicationRestrictions(str, bundle, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public boolean setRestrictionsChallenge(java.lang.String str) {
        return false;
    }

    public void setDefaultGuestRestrictions(android.os.Bundle bundle) {
        try {
            this.mService.setDefaultGuestRestrictions(bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.Bundle getDefaultGuestRestrictions() {
        try {
            return this.mService.getDefaultGuestRestrictions();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getUserCreationTime(android.os.UserHandle userHandle) {
        try {
            return this.mService.getUserCreationTime(userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean someUserHasSeedAccount(java.lang.String str, java.lang.String str2) {
        try {
            return this.mService.someUserHasSeedAccount(str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean someUserHasAccount(java.lang.String str, java.lang.String str2) {
        java.util.Objects.requireNonNull(str, "accountName must not be null");
        java.util.Objects.requireNonNull(str2, "accountType must not be null");
        try {
            return this.mService.someUserHasAccount(str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setBootUser(android.os.UserHandle userHandle) {
        try {
            this.mService.setBootUser(userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.UserHandle getBootUser() {
        try {
            return android.os.UserHandle.of(this.mService.getBootUser());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final void invalidateStaticUserProperties() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_STATIC_USER_PROPERTIES);
    }

    public static final void invalidateUserPropertiesCache() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_USER_PROPERTIES);
    }

    @android.annotation.SystemApi
    public static final class EnforcingUser implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.os.UserManager.EnforcingUser> CREATOR = new android.os.Parcelable.Creator<android.os.UserManager.EnforcingUser>() { // from class: android.os.UserManager.EnforcingUser.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.UserManager.EnforcingUser createFromParcel(android.os.Parcel parcel) {
                return new android.os.UserManager.EnforcingUser(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.UserManager.EnforcingUser[] newArray(int i) {
                return new android.os.UserManager.EnforcingUser[i];
            }
        };
        private final int userId;
        private final int userRestrictionSource;

        public EnforcingUser(int i, int i2) {
            this.userId = i;
            this.userRestrictionSource = i2;
        }

        private EnforcingUser(android.os.Parcel parcel) {
            this.userId = parcel.readInt();
            this.userRestrictionSource = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.userId);
            parcel.writeInt(this.userRestrictionSource);
        }

        public android.os.UserHandle getUserHandle() {
            return android.os.UserHandle.of(this.userId);
        }

        public int getUserRestrictionSource() {
            return this.userRestrictionSource;
        }
    }
}
