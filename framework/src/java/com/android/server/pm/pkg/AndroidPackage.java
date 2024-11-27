package com.android.server.pm.pkg;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes5.dex */
public interface AndroidPackage {
    java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> getActivities();

    java.util.List<java.lang.String> getAdoptPermissions();

    java.util.List<com.android.internal.pm.pkg.component.ParsedApexSystemService> getApexSystemServices();

    java.lang.String getAppComponentFactory();

    java.lang.String getApplicationClassName();

    java.util.List<com.android.internal.pm.pkg.component.ParsedAttribution> getAttributions();

    int getAutoRevokePermissions();

    java.lang.String getBackupAgentName();

    int getBannerResourceId();

    @java.lang.Deprecated
    java.lang.String getBaseApkPath();

    int getBaseRevisionCode();

    int getCategory();

    java.lang.String getClassLoaderName();

    int getCompatibleWidthLimitDp();

    int getCompileSdkVersion();

    java.lang.String getCompileSdkVersionCodeName();

    java.util.List<android.content.pm.ConfigurationInfo> getConfigPreferences();

    int getDataExtractionRulesResourceId();

    int getDescriptionResourceId();

    java.lang.String getEmergencyInstaller();

    java.util.List<android.content.pm.FeatureGroupInfo> getFeatureGroups();

    int getFullBackupContentResourceId();

    int getGwpAsanMode();

    int getIconResourceId();

    java.util.Set<java.lang.String> getImplicitPermissions();

    int getInstallLocation();

    java.util.List<com.android.internal.pm.pkg.component.ParsedInstrumentation> getInstrumentations();

    java.util.Map<java.lang.String, android.util.ArraySet<java.security.PublicKey>> getKeySetMapping();

    java.util.Set<java.lang.String> getKnownActivityEmbeddingCerts();

    int getLabelResourceId();

    int getLargestWidthLimitDp();

    java.util.List<java.lang.String> getLibraryNames();

    int getLocaleConfigResourceId();

    int getLogoResourceId();

    long getLongVersionCode();

    java.lang.String getManageSpaceActivityName();

    java.lang.String getManifestPackageName();

    float getMaxAspectRatio();

    int getMaxSdkVersion();

    int getMemtagMode();

    android.os.Bundle getMetaData();

    java.util.Set<java.lang.String> getMimeGroups();

    float getMinAspectRatio();

    android.util.SparseIntArray getMinExtensionVersions();

    int getMinSdkVersion();

    int getNativeHeapZeroInitialized();

    java.lang.String getNativeLibraryDir();

    java.lang.String getNativeLibraryRootDir();

    int getNetworkSecurityConfigResourceId();

    java.lang.CharSequence getNonLocalizedLabel();

    java.util.List<java.lang.String> getOriginalPackages();

    java.lang.String getOverlayCategory();

    int getOverlayPriority();

    java.lang.String getOverlayTarget();

    java.lang.String getOverlayTargetOverlayableName();

    java.util.Map<java.lang.String, java.lang.String> getOverlayables();

    java.lang.String getPackageName();

    java.lang.String getPath();

    java.lang.String getPermission();

    java.util.List<com.android.internal.pm.pkg.component.ParsedPermissionGroup> getPermissionGroups();

    java.util.List<com.android.internal.pm.pkg.component.ParsedPermission> getPermissions();

    java.util.List<android.util.Pair<java.lang.String, com.android.internal.pm.pkg.component.ParsedIntentInfo>> getPreferredActivityFilters();

    java.lang.String getProcessName();

    java.util.Map<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess> getProcesses();

    java.util.Map<java.lang.String, android.content.pm.PackageManager.Property> getProperties();

    java.util.List<java.lang.String> getProtectedBroadcasts();

    java.util.List<com.android.internal.pm.pkg.component.ParsedProvider> getProviders();

    java.util.List<android.content.Intent> getQueriesIntents();

    java.util.List<java.lang.String> getQueriesPackages();

    java.util.Set<java.lang.String> getQueriesProviders();

    java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> getReceivers();

    java.util.List<android.content.pm.FeatureInfo> getRequestedFeatures();

    java.util.Set<java.lang.String> getRequestedPermissions();

    java.lang.String getRequiredAccountType();

    int getRequiresSmallestWidthDp();

    java.lang.Boolean getResizeableActivity();

    byte[] getRestrictUpdateHash();

    java.lang.String getRestrictedAccountType();

    int getRoundIconResourceId();

    int getSdkLibVersionMajor();

    java.lang.String getSdkLibraryName();

    java.lang.String getSecondaryNativeLibraryDir();

    java.util.List<com.android.internal.pm.pkg.component.ParsedService> getServices();

    java.lang.String getSharedUserId();

    int getSharedUserLabelResourceId();

    android.content.pm.SigningDetails getSigningDetails();

    java.lang.String[] getSplitClassLoaderNames();

    java.lang.String[] getSplitCodePaths();

    android.util.SparseArray<int[]> getSplitDependencies();

    int[] getSplitFlags();

    java.lang.String[] getSplitNames();

    int[] getSplitRevisionCodes();

    java.util.List<com.android.server.pm.pkg.AndroidPackageSplit> getSplits();

    java.lang.String getStaticSharedLibraryName();

    long getStaticSharedLibraryVersion();

    java.util.UUID getStorageUuid();

    int getTargetSandboxVersion();

    int getTargetSdkVersion();

    java.lang.String getTaskAffinity();

    int getThemeResourceId();

    int getUiOptions();

    @java.lang.Deprecated
    int getUid();

    java.util.Set<java.lang.String> getUpgradeKeySets();

    java.util.List<java.lang.String> getUsesLibraries();

    java.util.List<java.lang.String> getUsesNativeLibraries();

    java.util.List<java.lang.String> getUsesOptionalLibraries();

    java.util.List<java.lang.String> getUsesOptionalNativeLibraries();

    java.util.List<com.android.internal.pm.pkg.component.ParsedUsesPermission> getUsesPermissions();

    java.util.List<java.lang.String> getUsesSdkLibraries();

    java.lang.String[][] getUsesSdkLibrariesCertDigests();

    boolean[] getUsesSdkLibrariesOptional();

    long[] getUsesSdkLibrariesVersionsMajor();

    java.util.List<java.lang.String> getUsesStaticLibraries();

    java.lang.String[][] getUsesStaticLibrariesCertDigests();

    long[] getUsesStaticLibrariesVersions();

    java.lang.String getVersionName();

    java.lang.String getVolumeUuid();

    java.lang.String getZygotePreloadName();

    boolean hasPreserveLegacyExternalStorage();

    boolean hasRequestForegroundServiceExemption();

    java.lang.Boolean hasRequestRawExternalStorageAccess();

    boolean is32BitAbiPreferred();

    boolean isAllowAudioPlaybackCapture();

    boolean isAllowCrossUidActivitySwitchFromBelow();

    boolean isAllowNativeHeapPointerTagging();

    boolean isAnyDensity();

    boolean isApex();

    boolean isAttributionsUserVisible();

    boolean isBackupAllowed();

    boolean isBackupInForeground();

    boolean isClearUserDataAllowed();

    boolean isClearUserDataOnFailedRestoreAllowed();

    boolean isCleartextTrafficAllowed();

    boolean isCoreApp();

    boolean isCrossProfile();

    boolean isDebuggable();

    boolean isDeclaredHavingCode();

    boolean isDefaultToDeviceProtectedStorage();

    boolean isDirectBootAware();

    boolean isEnabled();

    boolean isExternalStorage();

    boolean isExtraLargeScreensSupported();

    boolean isExtractNativeLibrariesRequested();

    boolean isFactoryTest();

    boolean isForceQueryable();

    boolean isFullBackupOnly();

    @java.lang.Deprecated
    boolean isGame();

    boolean isHardwareAccelerated();

    boolean isHasDomainUrls();

    boolean isIsolatedSplitLoading();

    boolean isKillAfterRestoreAllowed();

    boolean isLargeHeap();

    boolean isLargeScreensSupported();

    boolean isLeavingSharedUser();

    boolean isMultiArch();

    boolean isNativeLibraryRootRequiresIsa();

    boolean isNonSdkApiRequested();

    boolean isNormalScreensSupported();

    boolean isOnBackInvokedCallbackEnabled();

    boolean isOverlayIsStatic();

    boolean isPartiallyDirectBootAware();

    boolean isPersistent();

    boolean isProfileable();

    boolean isProfileableByShell();

    boolean isRequestLegacyExternalStorage();

    boolean isRequiredForAllUsers();

    boolean isResetEnabledSettingsOnAppDataCleared();

    boolean isResizeable();

    boolean isResizeableActivityViaSdkVersion();

    boolean isResourceOverlay();

    boolean isRestoreAnyVersion();

    boolean isRtlSupported();

    boolean isSaveStateDisallowed();

    boolean isSdkLibrary();

    boolean isSignedWithPlatformKey();

    boolean isSmallScreensSupported();

    boolean isStaticSharedLibrary();

    boolean isStub();

    boolean isTaskReparentingAllowed();

    boolean isTestOnly();

    boolean isUpdatableSystem();

    boolean isUseEmbeddedDex();

    boolean isUserDataFragile();

    boolean isVisibleToInstantApps();

    boolean isVmSafeMode();
}
