package android.multiuser;

/* loaded from: classes2.dex */
public interface FeatureFlags {
    boolean addUiForSoundsFromBackgroundUsers();

    boolean allowResolverSheetForPrivateSpace();

    boolean avatarSync();

    boolean bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch();

    boolean disablePrivateSpaceItemsOnHome();

    boolean enableBiometricsToUnlockPrivateSpace();

    boolean enableHidingProfiles();

    boolean enableLauncherAppsHiddenProfileChecks();

    boolean enablePermissionToAccessHiddenProfiles();

    boolean enablePrivateSpaceAutolockOnRestarts();

    boolean enablePrivateSpaceFeatures();

    boolean enablePrivateSpaceIntentRedirection();

    boolean enablePsSensitiveNotificationsToggle();

    boolean enableSystemUserOnlyForServicesAndProviders();

    boolean handleInterleavedSettingsForPrivateSpace();

    boolean moveQuietModeOperationsToSeparateThread();

    boolean reorderWallpaperDuringUserSwitch();

    boolean saveGlobalAndGuestRestrictionsOnSystemUserXml();

    boolean saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly();

    boolean setPowerModeDuringUserSwitch();

    boolean showSetScreenLockDialog();

    boolean startUserBeforeScheduledAlarms();

    boolean supportAutolockForPrivateSpace();

    boolean supportCommunalProfile();

    boolean supportCommunalProfileNextgen();

    boolean useAllCpusDuringUserSwitch();
}
