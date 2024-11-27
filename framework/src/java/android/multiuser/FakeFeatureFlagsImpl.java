package android.multiuser;

/* loaded from: classes2.dex */
public class FakeFeatureFlagsImpl implements android.multiuser.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.multiuser.Flags.FLAG_ADD_UI_FOR_SOUNDS_FROM_BACKGROUND_USERS, false), java.util.Map.entry(android.multiuser.Flags.FLAG_ALLOW_RESOLVER_SHEET_FOR_PRIVATE_SPACE, false), java.util.Map.entry(android.multiuser.Flags.FLAG_AVATAR_SYNC, false), java.util.Map.entry(android.multiuser.Flags.FLAG_BIND_WALLPAPER_SERVICE_ON_ITS_OWN_THREAD_DURING_A_USER_SWITCH, false), java.util.Map.entry(android.multiuser.Flags.FLAG_DISABLE_PRIVATE_SPACE_ITEMS_ON_HOME, false), java.util.Map.entry(android.multiuser.Flags.FLAG_ENABLE_BIOMETRICS_TO_UNLOCK_PRIVATE_SPACE, false), java.util.Map.entry(android.multiuser.Flags.FLAG_ENABLE_HIDING_PROFILES, false), java.util.Map.entry(android.multiuser.Flags.FLAG_ENABLE_LAUNCHER_APPS_HIDDEN_PROFILE_CHECKS, false), java.util.Map.entry(android.multiuser.Flags.FLAG_ENABLE_PERMISSION_TO_ACCESS_HIDDEN_PROFILES, false), java.util.Map.entry(android.multiuser.Flags.FLAG_ENABLE_PRIVATE_SPACE_AUTOLOCK_ON_RESTARTS, false), java.util.Map.entry(android.multiuser.Flags.FLAG_ENABLE_PRIVATE_SPACE_FEATURES, false), java.util.Map.entry(android.multiuser.Flags.FLAG_ENABLE_PRIVATE_SPACE_INTENT_REDIRECTION, false), java.util.Map.entry(android.multiuser.Flags.FLAG_ENABLE_PS_SENSITIVE_NOTIFICATIONS_TOGGLE, false), java.util.Map.entry(android.multiuser.Flags.FLAG_ENABLE_SYSTEM_USER_ONLY_FOR_SERVICES_AND_PROVIDERS, false), java.util.Map.entry(android.multiuser.Flags.FLAG_HANDLE_INTERLEAVED_SETTINGS_FOR_PRIVATE_SPACE, false), java.util.Map.entry(android.multiuser.Flags.FLAG_MOVE_QUIET_MODE_OPERATIONS_TO_SEPARATE_THREAD, false), java.util.Map.entry(android.multiuser.Flags.FLAG_REORDER_WALLPAPER_DURING_USER_SWITCH, false), java.util.Map.entry(android.multiuser.Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML, false), java.util.Map.entry(android.multiuser.Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML_READ_ONLY, false), java.util.Map.entry(android.multiuser.Flags.FLAG_SET_POWER_MODE_DURING_USER_SWITCH, false), java.util.Map.entry(android.multiuser.Flags.FLAG_SHOW_SET_SCREEN_LOCK_DIALOG, false), java.util.Map.entry(android.multiuser.Flags.FLAG_START_USER_BEFORE_SCHEDULED_ALARMS, false), java.util.Map.entry(android.multiuser.Flags.FLAG_SUPPORT_AUTOLOCK_FOR_PRIVATE_SPACE, false), java.util.Map.entry(android.multiuser.Flags.FLAG_SUPPORT_COMMUNAL_PROFILE, false), java.util.Map.entry(android.multiuser.Flags.FLAG_SUPPORT_COMMUNAL_PROFILE_NEXTGEN, false), java.util.Map.entry(android.multiuser.Flags.FLAG_USE_ALL_CPUS_DURING_USER_SWITCH, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.multiuser.Flags.FLAG_ADD_UI_FOR_SOUNDS_FROM_BACKGROUND_USERS, android.multiuser.Flags.FLAG_ALLOW_RESOLVER_SHEET_FOR_PRIVATE_SPACE, android.multiuser.Flags.FLAG_AVATAR_SYNC, android.multiuser.Flags.FLAG_BIND_WALLPAPER_SERVICE_ON_ITS_OWN_THREAD_DURING_A_USER_SWITCH, android.multiuser.Flags.FLAG_DISABLE_PRIVATE_SPACE_ITEMS_ON_HOME, android.multiuser.Flags.FLAG_ENABLE_BIOMETRICS_TO_UNLOCK_PRIVATE_SPACE, android.multiuser.Flags.FLAG_ENABLE_HIDING_PROFILES, android.multiuser.Flags.FLAG_ENABLE_LAUNCHER_APPS_HIDDEN_PROFILE_CHECKS, android.multiuser.Flags.FLAG_ENABLE_PERMISSION_TO_ACCESS_HIDDEN_PROFILES, android.multiuser.Flags.FLAG_ENABLE_PRIVATE_SPACE_AUTOLOCK_ON_RESTARTS, android.multiuser.Flags.FLAG_ENABLE_PRIVATE_SPACE_FEATURES, android.multiuser.Flags.FLAG_ENABLE_PRIVATE_SPACE_INTENT_REDIRECTION, android.multiuser.Flags.FLAG_ENABLE_PS_SENSITIVE_NOTIFICATIONS_TOGGLE, android.multiuser.Flags.FLAG_ENABLE_SYSTEM_USER_ONLY_FOR_SERVICES_AND_PROVIDERS, android.multiuser.Flags.FLAG_HANDLE_INTERLEAVED_SETTINGS_FOR_PRIVATE_SPACE, android.multiuser.Flags.FLAG_MOVE_QUIET_MODE_OPERATIONS_TO_SEPARATE_THREAD, android.multiuser.Flags.FLAG_REORDER_WALLPAPER_DURING_USER_SWITCH, android.multiuser.Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML, android.multiuser.Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML_READ_ONLY, android.multiuser.Flags.FLAG_SET_POWER_MODE_DURING_USER_SWITCH, android.multiuser.Flags.FLAG_SHOW_SET_SCREEN_LOCK_DIALOG, android.multiuser.Flags.FLAG_START_USER_BEFORE_SCHEDULED_ALARMS, android.multiuser.Flags.FLAG_SUPPORT_AUTOLOCK_FOR_PRIVATE_SPACE, android.multiuser.Flags.FLAG_SUPPORT_COMMUNAL_PROFILE, android.multiuser.Flags.FLAG_SUPPORT_COMMUNAL_PROFILE_NEXTGEN, android.multiuser.Flags.FLAG_USE_ALL_CPUS_DURING_USER_SWITCH, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.multiuser.FeatureFlags
    public boolean addUiForSoundsFromBackgroundUsers() {
        return getValue(android.multiuser.Flags.FLAG_ADD_UI_FOR_SOUNDS_FROM_BACKGROUND_USERS);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean allowResolverSheetForPrivateSpace() {
        return getValue(android.multiuser.Flags.FLAG_ALLOW_RESOLVER_SHEET_FOR_PRIVATE_SPACE);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean avatarSync() {
        return getValue(android.multiuser.Flags.FLAG_AVATAR_SYNC);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch() {
        return getValue(android.multiuser.Flags.FLAG_BIND_WALLPAPER_SERVICE_ON_ITS_OWN_THREAD_DURING_A_USER_SWITCH);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean disablePrivateSpaceItemsOnHome() {
        return getValue(android.multiuser.Flags.FLAG_DISABLE_PRIVATE_SPACE_ITEMS_ON_HOME);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableBiometricsToUnlockPrivateSpace() {
        return getValue(android.multiuser.Flags.FLAG_ENABLE_BIOMETRICS_TO_UNLOCK_PRIVATE_SPACE);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableHidingProfiles() {
        return getValue(android.multiuser.Flags.FLAG_ENABLE_HIDING_PROFILES);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableLauncherAppsHiddenProfileChecks() {
        return getValue(android.multiuser.Flags.FLAG_ENABLE_LAUNCHER_APPS_HIDDEN_PROFILE_CHECKS);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePermissionToAccessHiddenProfiles() {
        return getValue(android.multiuser.Flags.FLAG_ENABLE_PERMISSION_TO_ACCESS_HIDDEN_PROFILES);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceAutolockOnRestarts() {
        return getValue(android.multiuser.Flags.FLAG_ENABLE_PRIVATE_SPACE_AUTOLOCK_ON_RESTARTS);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceFeatures() {
        return getValue(android.multiuser.Flags.FLAG_ENABLE_PRIVATE_SPACE_FEATURES);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePrivateSpaceIntentRedirection() {
        return getValue(android.multiuser.Flags.FLAG_ENABLE_PRIVATE_SPACE_INTENT_REDIRECTION);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enablePsSensitiveNotificationsToggle() {
        return getValue(android.multiuser.Flags.FLAG_ENABLE_PS_SENSITIVE_NOTIFICATIONS_TOGGLE);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean enableSystemUserOnlyForServicesAndProviders() {
        return getValue(android.multiuser.Flags.FLAG_ENABLE_SYSTEM_USER_ONLY_FOR_SERVICES_AND_PROVIDERS);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean handleInterleavedSettingsForPrivateSpace() {
        return getValue(android.multiuser.Flags.FLAG_HANDLE_INTERLEAVED_SETTINGS_FOR_PRIVATE_SPACE);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean moveQuietModeOperationsToSeparateThread() {
        return getValue(android.multiuser.Flags.FLAG_MOVE_QUIET_MODE_OPERATIONS_TO_SEPARATE_THREAD);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean reorderWallpaperDuringUserSwitch() {
        return getValue(android.multiuser.Flags.FLAG_REORDER_WALLPAPER_DURING_USER_SWITCH);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean saveGlobalAndGuestRestrictionsOnSystemUserXml() {
        return getValue(android.multiuser.Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly() {
        return getValue(android.multiuser.Flags.FLAG_SAVE_GLOBAL_AND_GUEST_RESTRICTIONS_ON_SYSTEM_USER_XML_READ_ONLY);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean setPowerModeDuringUserSwitch() {
        return getValue(android.multiuser.Flags.FLAG_SET_POWER_MODE_DURING_USER_SWITCH);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean showSetScreenLockDialog() {
        return getValue(android.multiuser.Flags.FLAG_SHOW_SET_SCREEN_LOCK_DIALOG);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean startUserBeforeScheduledAlarms() {
        return getValue(android.multiuser.Flags.FLAG_START_USER_BEFORE_SCHEDULED_ALARMS);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportAutolockForPrivateSpace() {
        return getValue(android.multiuser.Flags.FLAG_SUPPORT_AUTOLOCK_FOR_PRIVATE_SPACE);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportCommunalProfile() {
        return getValue(android.multiuser.Flags.FLAG_SUPPORT_COMMUNAL_PROFILE);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean supportCommunalProfileNextgen() {
        return getValue(android.multiuser.Flags.FLAG_SUPPORT_COMMUNAL_PROFILE_NEXTGEN);
    }

    @Override // android.multiuser.FeatureFlags
    public boolean useAllCpusDuringUserSwitch() {
        return getValue(android.multiuser.Flags.FLAG_USE_ALL_CPUS_DURING_USER_SWITCH);
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
