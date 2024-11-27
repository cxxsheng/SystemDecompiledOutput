package android.multiuser;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.multiuser.FeatureFlags FEATURE_FLAGS = new android.multiuser.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ADD_UI_FOR_SOUNDS_FROM_BACKGROUND_USERS = "android.multiuser.add_ui_for_sounds_from_background_users";
    public static final java.lang.String FLAG_ALLOW_RESOLVER_SHEET_FOR_PRIVATE_SPACE = "android.multiuser.allow_resolver_sheet_for_private_space";
    public static final java.lang.String FLAG_AVATAR_SYNC = "android.multiuser.avatar_sync";
    public static final java.lang.String FLAG_BIND_WALLPAPER_SERVICE_ON_ITS_OWN_THREAD_DURING_A_USER_SWITCH = "android.multiuser.bind_wallpaper_service_on_its_own_thread_during_a_user_switch";
    public static final java.lang.String FLAG_DISABLE_PRIVATE_SPACE_ITEMS_ON_HOME = "android.multiuser.disable_private_space_items_on_home";
    public static final java.lang.String FLAG_ENABLE_BIOMETRICS_TO_UNLOCK_PRIVATE_SPACE = "android.multiuser.enable_biometrics_to_unlock_private_space";
    public static final java.lang.String FLAG_ENABLE_HIDING_PROFILES = "android.multiuser.enable_hiding_profiles";
    public static final java.lang.String FLAG_ENABLE_LAUNCHER_APPS_HIDDEN_PROFILE_CHECKS = "android.multiuser.enable_launcher_apps_hidden_profile_checks";
    public static final java.lang.String FLAG_ENABLE_PERMISSION_TO_ACCESS_HIDDEN_PROFILES = "android.multiuser.enable_permission_to_access_hidden_profiles";
    public static final java.lang.String FLAG_ENABLE_PRIVATE_SPACE_AUTOLOCK_ON_RESTARTS = "android.multiuser.enable_private_space_autolock_on_restarts";
    public static final java.lang.String FLAG_ENABLE_PRIVATE_SPACE_FEATURES = "android.multiuser.enable_private_space_features";
    public static final java.lang.String FLAG_ENABLE_PRIVATE_SPACE_INTENT_REDIRECTION = "android.multiuser.enable_private_space_intent_redirection";
    public static final java.lang.String FLAG_ENABLE_PS_SENSITIVE_NOTIFICATIONS_TOGGLE = "android.multiuser.enable_ps_sensitive_notifications_toggle";
    public static final java.lang.String FLAG_ENABLE_SYSTEM_USER_ONLY_FOR_SERVICES_AND_PROVIDERS = "android.multiuser.enable_system_user_only_for_services_and_providers";
    public static final java.lang.String FLAG_HANDLE_INTERLEAVED_SETTINGS_FOR_PRIVATE_SPACE = "android.multiuser.handle_interleaved_settings_for_private_space";
    public static final java.lang.String FLAG_MOVE_QUIET_MODE_OPERATIONS_TO_SEPARATE_THREAD = "android.multiuser.move_quiet_mode_operations_to_separate_thread";
    public static final java.lang.String FLAG_REORDER_WALLPAPER_DURING_USER_SWITCH = "android.multiuser.reorder_wallpaper_during_user_switch";
    public static final java.lang.String FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML = "android.multiuser.save_global_and_guest_restrictions_on_system_user_xml";
    public static final java.lang.String FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML_READ_ONLY = "android.multiuser.save_global_and_guest_restrictions_on_system_user_xml_read_only";
    public static final java.lang.String FLAG_SET_POWER_MODE_DURING_USER_SWITCH = "android.multiuser.set_power_mode_during_user_switch";
    public static final java.lang.String FLAG_SHOW_SET_SCREEN_LOCK_DIALOG = "android.multiuser.show_set_screen_lock_dialog";
    public static final java.lang.String FLAG_START_USER_BEFORE_SCHEDULED_ALARMS = "android.multiuser.start_user_before_scheduled_alarms";
    public static final java.lang.String FLAG_SUPPORT_AUTOLOCK_FOR_PRIVATE_SPACE = "android.multiuser.support_autolock_for_private_space";
    public static final java.lang.String FLAG_SUPPORT_COMMUNAL_PROFILE = "android.multiuser.support_communal_profile";
    public static final java.lang.String FLAG_SUPPORT_COMMUNAL_PROFILE_NEXTGEN = "android.multiuser.support_communal_profile_nextgen";
    public static final java.lang.String FLAG_USE_ALL_CPUS_DURING_USER_SWITCH = "android.multiuser.use_all_cpus_during_user_switch";

    public static boolean addUiForSoundsFromBackgroundUsers() {
        return FEATURE_FLAGS.addUiForSoundsFromBackgroundUsers();
    }

    public static boolean allowResolverSheetForPrivateSpace() {
        return FEATURE_FLAGS.allowResolverSheetForPrivateSpace();
    }

    public static boolean avatarSync() {
        return FEATURE_FLAGS.avatarSync();
    }

    public static boolean bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch() {
        return FEATURE_FLAGS.bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch();
    }

    public static boolean disablePrivateSpaceItemsOnHome() {
        return FEATURE_FLAGS.disablePrivateSpaceItemsOnHome();
    }

    public static boolean enableBiometricsToUnlockPrivateSpace() {
        return FEATURE_FLAGS.enableBiometricsToUnlockPrivateSpace();
    }

    public static boolean enableHidingProfiles() {
        return FEATURE_FLAGS.enableHidingProfiles();
    }

    public static boolean enableLauncherAppsHiddenProfileChecks() {
        return FEATURE_FLAGS.enableLauncherAppsHiddenProfileChecks();
    }

    public static boolean enablePermissionToAccessHiddenProfiles() {
        return FEATURE_FLAGS.enablePermissionToAccessHiddenProfiles();
    }

    public static boolean enablePrivateSpaceAutolockOnRestarts() {
        return FEATURE_FLAGS.enablePrivateSpaceAutolockOnRestarts();
    }

    public static boolean enablePrivateSpaceFeatures() {
        return FEATURE_FLAGS.enablePrivateSpaceFeatures();
    }

    public static boolean enablePrivateSpaceIntentRedirection() {
        return FEATURE_FLAGS.enablePrivateSpaceIntentRedirection();
    }

    public static boolean enablePsSensitiveNotificationsToggle() {
        return FEATURE_FLAGS.enablePsSensitiveNotificationsToggle();
    }

    public static boolean enableSystemUserOnlyForServicesAndProviders() {
        return FEATURE_FLAGS.enableSystemUserOnlyForServicesAndProviders();
    }

    public static boolean handleInterleavedSettingsForPrivateSpace() {
        return FEATURE_FLAGS.handleInterleavedSettingsForPrivateSpace();
    }

    public static boolean moveQuietModeOperationsToSeparateThread() {
        return FEATURE_FLAGS.moveQuietModeOperationsToSeparateThread();
    }

    public static boolean reorderWallpaperDuringUserSwitch() {
        return FEATURE_FLAGS.reorderWallpaperDuringUserSwitch();
    }

    public static boolean saveGlobalAndGuestRestrictionsOnSystemUserXml() {
        return FEATURE_FLAGS.saveGlobalAndGuestRestrictionsOnSystemUserXml();
    }

    public static boolean saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly() {
        return FEATURE_FLAGS.saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly();
    }

    public static boolean setPowerModeDuringUserSwitch() {
        return FEATURE_FLAGS.setPowerModeDuringUserSwitch();
    }

    public static boolean showSetScreenLockDialog() {
        return FEATURE_FLAGS.showSetScreenLockDialog();
    }

    public static boolean startUserBeforeScheduledAlarms() {
        return FEATURE_FLAGS.startUserBeforeScheduledAlarms();
    }

    public static boolean supportAutolockForPrivateSpace() {
        return FEATURE_FLAGS.supportAutolockForPrivateSpace();
    }

    public static boolean supportCommunalProfile() {
        return FEATURE_FLAGS.supportCommunalProfile();
    }

    public static boolean supportCommunalProfileNextgen() {
        return FEATURE_FLAGS.supportCommunalProfileNextgen();
    }

    public static boolean useAllCpusDuringUserSwitch() {
        return FEATURE_FLAGS.useAllCpusDuringUserSwitch();
    }
}
