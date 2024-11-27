package com.android.internal.pm.pkg.parsing;

/* loaded from: classes5.dex */
public interface ParsingPackage {
    com.android.internal.pm.pkg.parsing.ParsingPackage addActivity(com.android.internal.pm.pkg.component.ParsedActivity parsedActivity);

    com.android.internal.pm.pkg.parsing.ParsingPackage addAdoptPermission(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage addApexSystemService(com.android.internal.pm.pkg.component.ParsedApexSystemService parsedApexSystemService);

    com.android.internal.pm.pkg.parsing.ParsingPackage addAttribution(com.android.internal.pm.pkg.component.ParsedAttribution parsedAttribution);

    com.android.internal.pm.pkg.parsing.ParsingPackage addConfigPreference(android.content.pm.ConfigurationInfo configurationInfo);

    com.android.internal.pm.pkg.parsing.ParsingPackage addFeatureGroup(android.content.pm.FeatureGroupInfo featureGroupInfo);

    com.android.internal.pm.pkg.parsing.ParsingPackage addImplicitPermission(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage addInstrumentation(com.android.internal.pm.pkg.component.ParsedInstrumentation parsedInstrumentation);

    com.android.internal.pm.pkg.parsing.ParsingPackage addKeySet(java.lang.String str, java.security.PublicKey publicKey);

    com.android.internal.pm.pkg.parsing.ParsingPackage addLibraryName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage addOriginalPackage(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage addOverlayable(java.lang.String str, java.lang.String str2);

    com.android.internal.pm.pkg.parsing.ParsingPackage addPermission(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission);

    com.android.internal.pm.pkg.parsing.ParsingPackage addPermissionGroup(com.android.internal.pm.pkg.component.ParsedPermissionGroup parsedPermissionGroup);

    com.android.internal.pm.pkg.parsing.ParsingPackage addPreferredActivityFilter(java.lang.String str, com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo);

    com.android.internal.pm.pkg.parsing.ParsingPackage addProperty(android.content.pm.PackageManager.Property property);

    com.android.internal.pm.pkg.parsing.ParsingPackage addProtectedBroadcast(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage addProvider(com.android.internal.pm.pkg.component.ParsedProvider parsedProvider);

    com.android.internal.pm.pkg.parsing.ParsingPackage addQueriesIntent(android.content.Intent intent);

    com.android.internal.pm.pkg.parsing.ParsingPackage addQueriesPackage(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage addQueriesProvider(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage addReceiver(com.android.internal.pm.pkg.component.ParsedActivity parsedActivity);

    com.android.internal.pm.pkg.parsing.ParsingPackage addReqFeature(android.content.pm.FeatureInfo featureInfo);

    com.android.internal.pm.pkg.parsing.ParsingPackage addService(com.android.internal.pm.pkg.component.ParsedService parsedService);

    com.android.internal.pm.pkg.parsing.ParsingPackage addUsesLibrary(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage addUsesNativeLibrary(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage addUsesOptionalLibrary(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage addUsesOptionalNativeLibrary(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage addUsesPermission(com.android.internal.pm.pkg.component.ParsedUsesPermission parsedUsesPermission);

    com.android.internal.pm.pkg.parsing.ParsingPackage addUsesSdkLibrary(java.lang.String str, long j, java.lang.String[] strArr, boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage addUsesStaticLibrary(java.lang.String str, long j, java.lang.String[] strArr);

    com.android.internal.pm.pkg.parsing.ParsingPackage asSplit(java.lang.String[] strArr, java.lang.String[] strArr2, int[] iArr, android.util.SparseArray<int[]> sparseArray);

    java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> getActivities();

    java.util.List<com.android.internal.pm.pkg.component.ParsedAttribution> getAttributions();

    java.lang.String getBaseApkPath();

    java.lang.String getClassLoaderName();

    java.util.List<android.content.pm.ConfigurationInfo> getConfigPreferences();

    java.util.List<com.android.internal.pm.pkg.component.ParsedInstrumentation> getInstrumentations();

    java.util.Map<java.lang.String, android.util.ArraySet<java.security.PublicKey>> getKeySetMapping();

    java.util.List<java.lang.String> getLibraryNames();

    float getMaxAspectRatio();

    int getMaxSdkVersion();

    android.os.Bundle getMetaData();

    float getMinAspectRatio();

    int getMinSdkVersion();

    java.lang.String getPackageName();

    java.lang.String getPermission();

    java.util.List<com.android.internal.pm.pkg.component.ParsedPermission> getPermissions();

    java.lang.String getProcessName();

    java.util.List<com.android.internal.pm.pkg.component.ParsedProvider> getProviders();

    java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> getReceivers();

    java.util.Set<java.lang.String> getRequestedPermissions();

    java.lang.Boolean getResizeableActivity();

    java.lang.String getSdkLibraryName();

    java.util.List<com.android.internal.pm.pkg.component.ParsedService> getServices();

    java.lang.String getSharedUserId();

    java.lang.String[] getSplitCodePaths();

    java.lang.String[] getSplitNames();

    java.lang.String getStaticSharedLibraryName();

    int getTargetSdkVersion();

    java.lang.String getTaskAffinity();

    int getUiOptions();

    java.util.List<java.lang.String> getUsesLibraries();

    java.util.List<java.lang.String> getUsesNativeLibraries();

    java.util.List<com.android.internal.pm.pkg.component.ParsedUsesPermission> getUsesPermissions();

    java.util.List<java.lang.String> getUsesSdkLibraries();

    long[] getUsesSdkLibrariesVersionsMajor();

    java.util.List<java.lang.String> getUsesStaticLibraries();

    java.lang.String getZygotePreloadName();

    com.android.internal.pm.parsing.pkg.ParsedPackage hideAsParsed();

    boolean isAllowCrossUidActivitySwitchFromBelow();

    boolean isAnyDensity();

    boolean isBackupAllowed();

    boolean isExtraLargeScreensSupported();

    boolean isHardwareAccelerated();

    boolean isLargeScreensSupported();

    boolean isNormalScreensSupported();

    boolean isProfileable();

    boolean isProfileableByShell();

    boolean isResizeable();

    boolean isResizeableActivityViaSdkVersion();

    boolean isSaveStateDisallowed();

    boolean isSmallScreensSupported();

    boolean isStaticSharedLibrary();

    boolean isTaskReparentingAllowed();

    com.android.internal.pm.pkg.parsing.ParsingPackage removeUsesOptionalLibrary(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage removeUsesOptionalNativeLibrary(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage set32BitAbiPreferred(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setAllowAudioPlaybackCapture(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setAllowCrossUidActivitySwitchFromBelow(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setAllowNativeHeapPointerTagging(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setAnyDensity(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setAppComponentFactory(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setAppMetadataFileInApk(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setApplicationClassName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setAttributionsAreUserVisible(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setAutoRevokePermissions(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setBackupAgentName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setBackupAllowed(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setBackupInForeground(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setBannerResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setBaseRevisionCode(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setCategory(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setClassLoaderName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setClearUserDataAllowed(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setClearUserDataOnFailedRestoreAllowed(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setCleartextTrafficAllowed(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setCompatibleWidthLimitDp(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setCompileSdkVersion(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setCompileSdkVersionCodeName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setCrossProfile(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setDataExtractionRulesResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setDebuggable(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setDeclaredHavingCode(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setDefaultToDeviceProtectedStorage(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setDescriptionResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setDirectBootAware(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setEmergencyInstaller(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setEnabled(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setExternalStorage(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setExtraLargeScreensSupported(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setExtractNativeLibrariesRequested(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setForceQueryable(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setFullBackupContentResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setFullBackupOnly(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setGame(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setGwpAsanMode(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setHardwareAccelerated(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setHasDomainUrls(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setIconResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setInstallLocation(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setIsolatedSplitLoading(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setKillAfterRestoreAllowed(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setKnownActivityEmbeddingCerts(java.util.Set<java.lang.String> set);

    com.android.internal.pm.pkg.parsing.ParsingPackage setLabelResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setLargeHeap(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setLargeScreensSupported(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setLargestWidthLimitDp(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setLeavingSharedUser(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setLocaleConfigResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setLogoResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setManageSpaceActivityName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setMaxAspectRatio(float f);

    com.android.internal.pm.pkg.parsing.ParsingPackage setMaxSdkVersion(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setMemtagMode(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setMetaData(android.os.Bundle bundle);

    com.android.internal.pm.pkg.parsing.ParsingPackage setMinAspectRatio(float f);

    com.android.internal.pm.pkg.parsing.ParsingPackage setMinExtensionVersions(android.util.SparseIntArray sparseIntArray);

    com.android.internal.pm.pkg.parsing.ParsingPackage setMinSdkVersion(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setMultiArch(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setNativeHeapZeroInitialized(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setNetworkSecurityConfigResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setNonLocalizedLabel(java.lang.CharSequence charSequence);

    com.android.internal.pm.pkg.parsing.ParsingPackage setNonSdkApiRequested(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setNormalScreensSupported(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setOnBackInvokedCallbackEnabled(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setOverlayCategory(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setOverlayIsStatic(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setOverlayPriority(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setOverlayTarget(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setOverlayTargetOverlayableName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setPartiallyDirectBootAware(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setPermission(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setPersistent(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setPreserveLegacyExternalStorage(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setProcessName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setProcesses(java.util.Map<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess> map);

    com.android.internal.pm.pkg.parsing.ParsingPackage setProfileable(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setProfileableByShell(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setRequestForegroundServiceExemption(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setRequestLegacyExternalStorage(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setRequestRawExternalStorageAccess(java.lang.Boolean bool);

    com.android.internal.pm.pkg.parsing.ParsingPackage setRequiredAccountType(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setRequiredForAllUsers(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setRequiresSmallestWidthDp(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setResetEnabledSettingsOnAppDataCleared(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setResizeable(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setResizeableActivity(java.lang.Boolean bool);

    com.android.internal.pm.pkg.parsing.ParsingPackage setResizeableActivityViaSdkVersion(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setResourceOverlay(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setRestoreAnyVersion(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setRestrictUpdateHash(byte[] bArr);

    com.android.internal.pm.pkg.parsing.ParsingPackage setRestrictedAccountType(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setRoundIconResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setRtlSupported(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setSaveStateDisallowed(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setSdkLibVersionMajor(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setSdkLibrary(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setSdkLibraryName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setSharedUserId(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setSharedUserLabelResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setSigningDetails(android.content.pm.SigningDetails signingDetails);

    com.android.internal.pm.pkg.parsing.ParsingPackage setSmallScreensSupported(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setSplitClassLoaderName(int i, java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setSplitHasCode(int i, boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setStaticSharedLibrary(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setStaticSharedLibraryName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setStaticSharedLibraryVersion(long j);

    com.android.internal.pm.pkg.parsing.ParsingPackage setTargetSandboxVersion(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setTargetSdkVersion(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setTaskAffinity(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setTaskReparentingAllowed(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setTestOnly(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setThemeResourceId(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setUiOptions(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setUpdatableSystem(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setUpgradeKeySets(java.util.Set<java.lang.String> set);

    com.android.internal.pm.pkg.parsing.ParsingPackage setUseEmbeddedDex(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setUserDataFragile(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setVersionCode(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setVersionCodeMajor(int i);

    com.android.internal.pm.pkg.parsing.ParsingPackage setVersionName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setVisibleToInstantApps(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setVmSafeMode(boolean z);

    com.android.internal.pm.pkg.parsing.ParsingPackage setVolumeUuid(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage setZygotePreloadName(java.lang.String str);

    com.android.internal.pm.pkg.parsing.ParsingPackage sortActivities();

    com.android.internal.pm.pkg.parsing.ParsingPackage sortReceivers();

    com.android.internal.pm.pkg.parsing.ParsingPackage sortServices();
}
