package com.android.server.pm;

/* loaded from: classes2.dex */
public abstract class IPackageManagerBase extends android.content.pm.IPackageManager.Stub {

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.pm.DexOptHelper mDexOptHelper;

    @android.annotation.NonNull
    private final com.android.server.pm.DomainVerificationConnection mDomainVerificationConnection;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationManagerInternal mDomainVerificationManager;

    @android.annotation.NonNull
    private final com.android.server.pm.PackageInstallerService mInstallerService;

    @android.annotation.Nullable
    private final android.content.ComponentName mInstantAppResolverSettingsComponent;

    @android.annotation.NonNull
    private final com.android.server.pm.ModuleInfoProvider mModuleInfoProvider;

    @android.annotation.NonNull
    private final com.android.server.pm.PackageProperty mPackageProperty;

    @android.annotation.NonNull
    private final com.android.server.pm.PreferredActivityHelper mPreferredActivityHelper;

    @android.annotation.NonNull
    private final android.content.ComponentName mResolveComponentName;

    @android.annotation.NonNull
    private final com.android.server.pm.ResolveIntentHelper mResolveIntentHelper;

    @android.annotation.NonNull
    private final com.android.server.pm.PackageManagerService mService;

    @android.annotation.Nullable
    private final java.lang.String mServicesExtensionPackageName;

    @android.annotation.Nullable
    private final java.lang.String mSharedSystemSharedLibraryPackageName;

    public IPackageManagerBase(@android.annotation.NonNull com.android.server.pm.PackageManagerService packageManagerService, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.DexOptHelper dexOptHelper, @android.annotation.NonNull com.android.server.pm.ModuleInfoProvider moduleInfoProvider, @android.annotation.NonNull com.android.server.pm.PreferredActivityHelper preferredActivityHelper, @android.annotation.NonNull com.android.server.pm.ResolveIntentHelper resolveIntentHelper, @android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationManagerInternal domainVerificationManagerInternal, @android.annotation.NonNull com.android.server.pm.DomainVerificationConnection domainVerificationConnection, @android.annotation.NonNull com.android.server.pm.PackageInstallerService packageInstallerService, @android.annotation.NonNull com.android.server.pm.PackageProperty packageProperty, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.Nullable android.content.ComponentName componentName2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        this.mService = packageManagerService;
        this.mContext = context;
        this.mDexOptHelper = dexOptHelper;
        this.mModuleInfoProvider = moduleInfoProvider;
        this.mPreferredActivityHelper = preferredActivityHelper;
        this.mResolveIntentHelper = resolveIntentHelper;
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        this.mDomainVerificationConnection = domainVerificationConnection;
        this.mInstallerService = packageInstallerService;
        this.mPackageProperty = packageProperty;
        this.mResolveComponentName = componentName;
        this.mInstantAppResolverSettingsComponent = componentName2;
        this.mServicesExtensionPackageName = str;
        this.mSharedSystemSharedLibraryPackageName = str2;
    }

    protected com.android.server.pm.Computer snapshot() {
        return this.mService.snapshotComputer();
    }

    @java.lang.Deprecated
    public final boolean activitySupportsIntentAsUser(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str, int i) {
        return snapshot().activitySupportsIntentAsUser(this.mResolveComponentName, componentName, intent, str, i);
    }

    @java.lang.Deprecated
    public final void addCrossProfileIntentFilter(android.content.IntentFilter intentFilter, java.lang.String str, int i, int i2, int i3) {
        this.mService.addCrossProfileIntentFilter(snapshot(), new com.android.server.pm.WatchedIntentFilter(intentFilter), str, i, i2, i3);
    }

    @java.lang.Deprecated
    public final boolean addPermission(android.content.pm.PermissionInfo permissionInfo) {
        return ((android.permission.PermissionManager) this.mContext.getSystemService(android.permission.PermissionManager.class)).addPermission(permissionInfo, false);
    }

    @java.lang.Deprecated
    public final boolean addPermissionAsync(android.content.pm.PermissionInfo permissionInfo) {
        return ((android.permission.PermissionManager) this.mContext.getSystemService(android.permission.PermissionManager.class)).addPermission(permissionInfo, true);
    }

    @java.lang.Deprecated
    public final void addPersistentPreferredActivity(android.content.IntentFilter intentFilter, android.content.ComponentName componentName, int i) {
        this.mPreferredActivityHelper.addPersistentPreferredActivity(new com.android.server.pm.WatchedIntentFilter(intentFilter), componentName, i);
    }

    @java.lang.Deprecated
    public final void addPreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2, boolean z) {
        this.mPreferredActivityHelper.addPreferredActivity(snapshot(), new com.android.server.pm.WatchedIntentFilter(intentFilter), i, componentNameArr, componentName, true, i2, "Adding preferred", z);
    }

    @java.lang.Deprecated
    public final boolean canForwardTo(@android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, int i, int i2) {
        return snapshot().canForwardTo(intent, str, i, i2);
    }

    @java.lang.Deprecated
    public final boolean canRequestPackageInstalls(java.lang.String str, int i) {
        return snapshot().canRequestPackageInstalls(str, android.os.Binder.getCallingUid(), i, true);
    }

    @java.lang.Deprecated
    public final java.lang.String[] canonicalToCurrentPackageNames(java.lang.String[] strArr) {
        return snapshot().canonicalToCurrentPackageNames(strArr);
    }

    @java.lang.Deprecated
    public final int checkPermission(java.lang.String str, java.lang.String str2, int i) {
        return this.mService.checkPermission(str, str2, i);
    }

    @java.lang.Deprecated
    public final int checkSignatures(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        return snapshot().checkSignatures(str, str2, i);
    }

    @java.lang.Deprecated
    public final int checkUidPermission(java.lang.String str, int i) {
        return snapshot().checkUidPermission(str, i);
    }

    @java.lang.Deprecated
    public final int checkUidSignatures(int i, int i2) {
        return snapshot().checkUidSignatures(i, i2);
    }

    @java.lang.Deprecated
    public final void clearPackagePersistentPreferredActivities(java.lang.String str, int i) {
        this.mPreferredActivityHelper.clearPackagePersistentPreferredActivities(str, i);
    }

    @java.lang.Deprecated
    public final void clearPersistentPreferredActivity(android.content.IntentFilter intentFilter, int i) {
        this.mPreferredActivityHelper.clearPersistentPreferredActivity(intentFilter, i);
    }

    @java.lang.Deprecated
    public final void clearPackagePreferredActivities(java.lang.String str) {
        this.mPreferredActivityHelper.clearPackagePreferredActivities(snapshot(), str);
    }

    @java.lang.Deprecated
    public final java.lang.String[] currentToCanonicalPackageNames(java.lang.String[] strArr) {
        return snapshot().currentToCanonicalPackageNames(strArr);
    }

    @java.lang.Deprecated
    public final void deleteExistingPackageAsUser(android.content.pm.VersionedPackage versionedPackage, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, int i) {
        this.mService.deleteExistingPackageAsUser(versionedPackage, iPackageDeleteObserver2, i);
    }

    @java.lang.Deprecated
    public final void deletePackageAsUser(java.lang.String str, int i, android.content.pm.IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) {
        deletePackageVersioned(new android.content.pm.VersionedPackage(str, i), new android.content.pm.PackageManager.LegacyPackageDeleteObserver(iPackageDeleteObserver).getBinder(), i2, i3);
    }

    @java.lang.Deprecated
    public final void deletePackageVersioned(android.content.pm.VersionedPackage versionedPackage, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) {
        this.mService.deletePackageVersioned(versionedPackage, iPackageDeleteObserver2, i, i2);
    }

    @java.lang.Deprecated
    public final android.content.pm.ResolveInfo findPersistentPreferredActivity(android.content.Intent intent, int i) {
        return this.mPreferredActivityHelper.findPersistentPreferredActivity(snapshot(), intent, i);
    }

    @java.lang.Deprecated
    public final android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, long j, int i) {
        return snapshot().getActivityInfo(componentName, j, i);
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.IntentFilter> getAllIntentFilters(@android.annotation.NonNull java.lang.String str) {
        return snapshot().getAllIntentFilters(str);
    }

    @java.lang.Deprecated
    public final java.util.List<java.lang.String> getAllPackages() {
        return snapshot().getAllPackages();
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final java.lang.String[] getAppOpPermissionPackages(@android.annotation.NonNull java.lang.String str, int i) {
        return snapshot().getAppOpPermissionPackages(str, i);
    }

    @java.lang.Deprecated
    public final java.lang.String getAppPredictionServicePackageName() {
        return this.mService.mAppPredictionServicePackage;
    }

    @java.lang.Deprecated
    public final int getApplicationEnabledSetting(@android.annotation.NonNull java.lang.String str, int i) {
        return snapshot().getApplicationEnabledSetting(str, i);
    }

    @java.lang.Deprecated
    public final boolean getApplicationHiddenSettingAsUser(@android.annotation.NonNull java.lang.String str, int i) {
        return snapshot().getApplicationHiddenSettingAsUser(str, i);
    }

    @java.lang.Deprecated
    public final android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, long j, int i) {
        return snapshot().getApplicationInfo(str, j, i);
    }

    @java.lang.Deprecated
    public final android.content.pm.dex.IArtManager getArtManager() {
        return this.mService.mArtManagerService;
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final java.lang.String getAttentionServicePackageName() {
        return this.mService.ensureSystemPackageName(snapshot(), this.mService.getPackageFromComponentString(android.R.string.config_datause_iface));
    }

    @java.lang.Deprecated
    public final boolean getBlockUninstallForUser(@android.annotation.NonNull java.lang.String str, int i) {
        return snapshot().getBlockUninstallForUser(str, i);
    }

    @java.lang.Deprecated
    public final int getComponentEnabledSetting(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
        return snapshot().getComponentEnabledSetting(componentName, android.os.Binder.getCallingUid(), i);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.SharedLibraryInfo> getDeclaredSharedLibraries(@android.annotation.NonNull java.lang.String str, long j, @android.annotation.NonNull int i) {
        return snapshot().getDeclaredSharedLibraries(str, j, i);
    }

    @java.lang.Deprecated
    public final byte[] getDefaultAppsBackup(int i) {
        return this.mPreferredActivityHelper.getDefaultAppsBackup(i);
    }

    @java.lang.Deprecated
    public final java.lang.String getDefaultTextClassifierPackageName() {
        return this.mService.mDefaultTextClassifierPackage;
    }

    @java.lang.Deprecated
    public final int getFlagsForUid(int i) {
        return snapshot().getFlagsForUid(i);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final java.lang.CharSequence getHarmfulAppWarning(@android.annotation.NonNull java.lang.String str, int i) {
        return snapshot().getHarmfulAppWarning(str, i);
    }

    @java.lang.Deprecated
    public final android.content.ComponentName getHomeActivities(java.util.List<android.content.pm.ResolveInfo> list) {
        com.android.server.pm.Computer snapshot = snapshot();
        if (snapshot.getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
            return null;
        }
        return snapshot.getHomeActivitiesAsUser(list, android.os.UserHandle.getCallingUserId());
    }

    @java.lang.Deprecated
    public final java.lang.String getIncidentReportApproverPackageName() {
        return this.mService.mIncidentReportApproverPackage;
    }

    @java.lang.Deprecated
    public final int getInstallLocation() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "default_install_location", 0);
    }

    @java.lang.Deprecated
    public final int getInstallReason(@android.annotation.NonNull java.lang.String str, int i) {
        return snapshot().getInstallReason(str, i);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final android.content.pm.InstallSourceInfo getInstallSourceInfo(@android.annotation.NonNull java.lang.String str, int i) {
        return snapshot().getInstallSourceInfo(str, i);
    }

    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.ApplicationInfo> getInstalledApplications(long j, int i) {
        return new android.content.pm.ParceledListSlice<>(snapshot().getInstalledApplications(j, i, android.os.Binder.getCallingUid(), false));
    }

    @java.lang.Deprecated
    public final java.util.List<android.content.pm.ModuleInfo> getInstalledModules(int i) {
        return this.mModuleInfoProvider.getInstalledModules(i);
    }

    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.PackageInfo> getInstalledPackages(long j, int i) {
        return snapshot().getInstalledPackages(j, i);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final java.lang.String getInstallerPackageName(@android.annotation.NonNull java.lang.String str) {
        return snapshot().getInstallerPackageName(str, android.os.UserHandle.getCallingUserId());
    }

    @java.lang.Deprecated
    public final android.content.ComponentName getInstantAppInstallerComponent() {
        com.android.server.pm.Computer snapshot = snapshot();
        if (snapshot.getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
            return null;
        }
        return snapshot.getInstantAppInstallerComponent();
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final android.content.ComponentName getInstantAppResolverComponent() {
        com.android.server.pm.Computer snapshot = snapshot();
        if (snapshot.getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
            return null;
        }
        return this.mService.getInstantAppResolver(snapshot);
    }

    @java.lang.Deprecated
    public final android.content.ComponentName getInstantAppResolverSettingsComponent() {
        return this.mInstantAppResolverSettingsComponent;
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final android.content.pm.InstrumentationInfo getInstrumentationInfoAsUser(@android.annotation.NonNull android.content.ComponentName componentName, int i, int i2) {
        return snapshot().getInstrumentationInfoAsUser(componentName, i, i2);
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.IntentFilterVerificationInfo> getIntentFilterVerifications(java.lang.String str) {
        return android.content.pm.ParceledListSlice.emptyList();
    }

    @java.lang.Deprecated
    public final int getIntentVerificationStatus(java.lang.String str, int i) {
        return this.mDomainVerificationManager.getLegacyState(str, i);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final android.content.pm.KeySet getKeySetByAlias(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        return snapshot().getKeySetByAlias(str, str2);
    }

    @java.lang.Deprecated
    public final android.content.pm.ModuleInfo getModuleInfo(java.lang.String str, int i) {
        return this.mModuleInfoProvider.getModuleInfo(str, i);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final java.lang.String getNameForUid(int i) {
        return snapshot().getNameForUid(i);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final java.lang.String[] getNamesForUids(@android.annotation.NonNull int[] iArr) {
        return snapshot().getNamesForUids(iArr);
    }

    @java.lang.Deprecated
    public final int[] getPackageGids(java.lang.String str, long j, int i) {
        return snapshot().getPackageGids(str, j, i);
    }

    @java.lang.Deprecated
    public final android.content.pm.PackageInfo getPackageInfo(java.lang.String str, long j, int i) {
        return snapshot().getPackageInfo(str, j, i);
    }

    @java.lang.Deprecated
    public final android.content.pm.PackageInfo getPackageInfoVersioned(android.content.pm.VersionedPackage versionedPackage, long j, int i) {
        return snapshot().getPackageInfoInternal(versionedPackage.getPackageName(), versionedPackage.getLongVersionCode(), j, android.os.Binder.getCallingUid(), i);
    }

    @java.lang.Deprecated
    public final android.content.pm.IPackageInstaller getPackageInstaller() {
        if (com.android.server.pm.PackageManagerServiceUtils.isSystemOrRoot()) {
            return this.mInstallerService;
        }
        if (snapshot().getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
            android.util.Log.w("PackageManager", "Returning null PackageInstaller for InstantApps");
            return null;
        }
        return this.mInstallerService;
    }

    @java.lang.Deprecated
    public final void getPackageSizeInfo(java.lang.String str, int i, android.content.pm.IPackageStatsObserver iPackageStatsObserver) {
        throw new java.lang.UnsupportedOperationException("Shame on you for calling the hidden API getPackageSizeInfo(). Shame!");
    }

    @java.lang.Deprecated
    public final int getPackageUid(@android.annotation.NonNull java.lang.String str, long j, int i) {
        return snapshot().getPackageUid(str, j, i);
    }

    @java.lang.Deprecated
    public final java.lang.String[] getPackagesForUid(int i) {
        snapshot().enforceCrossUserOrProfilePermission(android.os.Binder.getCallingUid(), android.os.UserHandle.getUserId(i), false, false, "getPackagesForUid");
        return snapshot().getPackagesForUid(i);
    }

    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.PackageInfo> getPackagesHoldingPermissions(@android.annotation.NonNull java.lang.String[] strArr, long j, int i) {
        return snapshot().getPackagesHoldingPermissions(strArr, j, i);
    }

    @java.lang.Deprecated
    public final android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) {
        return this.mService.getPermissionGroupInfo(str, i);
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.ApplicationInfo> getPersistentApplications(int i) {
        com.android.server.pm.Computer snapshot = snapshot();
        if (snapshot.getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
            return android.content.pm.ParceledListSlice.emptyList();
        }
        return new android.content.pm.ParceledListSlice<>(snapshot.getPersistentApplications(isSafeMode(), i));
    }

    @java.lang.Deprecated
    public final int getPreferredActivities(java.util.List<android.content.IntentFilter> list, java.util.List<android.content.ComponentName> list2, java.lang.String str) {
        return this.mPreferredActivityHelper.getPreferredActivities(snapshot(), list, list2, str);
    }

    @java.lang.Deprecated
    public final byte[] getPreferredActivityBackup(int i) {
        return this.mPreferredActivityHelper.getPreferredActivityBackup(i);
    }

    @java.lang.Deprecated
    public final int getPrivateFlagsForUid(int i) {
        return snapshot().getPrivateFlagsForUid(i);
    }

    @java.lang.Deprecated
    public final android.content.pm.PackageManager.Property getPropertyAsUser(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.pm.Computer snapshot = snapshot();
        snapshot.enforceCrossUserOrProfilePermission(callingUid, i, false, false, "getPropertyAsUser");
        if (snapshot.getPackageStateForInstalledAndFiltered(str2, callingUid, i) == null) {
            return null;
        }
        return this.mPackageProperty.getProperty(str, str2, str3);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final android.content.pm.ProviderInfo getProviderInfo(@android.annotation.NonNull android.content.ComponentName componentName, long j, int i) {
        return snapshot().getProviderInfo(componentName, j, i);
    }

    @java.lang.Deprecated
    public final android.content.pm.ActivityInfo getReceiverInfo(android.content.ComponentName componentName, long j, int i) {
        return snapshot().getReceiverInfo(componentName, j, i);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final java.lang.String getRotationResolverPackageName() {
        return this.mService.ensureSystemPackageName(snapshot(), this.mService.getPackageFromComponentString(android.R.string.config_defaultNetworkRecommendationProviderPackage));
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final android.content.pm.ServiceInfo getServiceInfo(@android.annotation.NonNull android.content.ComponentName componentName, long j, int i) {
        return snapshot().getServiceInfo(componentName, j, i);
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final java.lang.String getServicesSystemSharedLibraryPackageName() {
        return this.mServicesExtensionPackageName;
    }

    @java.lang.Deprecated
    public final java.lang.String getSetupWizardPackageName() {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Non-system caller");
        }
        return this.mService.mSetupWizardPackage;
    }

    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.SharedLibraryInfo> getSharedLibraries(java.lang.String str, long j, int i) {
        return snapshot().getSharedLibraries(str, j, i);
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final java.lang.String getSharedSystemSharedLibraryPackageName() {
        return this.mSharedSystemSharedLibraryPackageName;
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final android.content.pm.KeySet getSigningKeySet(@android.annotation.NonNull java.lang.String str) {
        return snapshot().getSigningKeySet(str);
    }

    @java.lang.Deprecated
    public final java.lang.String getSdkSandboxPackageName() {
        return this.mService.getSdkSandboxPackageName();
    }

    @java.lang.Deprecated
    public final java.lang.String getSystemCaptionsServicePackageName() {
        return this.mService.ensureSystemPackageName(snapshot(), this.mService.getPackageFromComponentString(android.R.string.config_defaultProfcollectReportUploaderAction));
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public final java.lang.String[] getSystemSharedLibraryNames() {
        android.util.ArrayMap<java.lang.String, java.lang.String> systemSharedLibraryNamesAndPaths = snapshot().getSystemSharedLibraryNamesAndPaths();
        if (systemSharedLibraryNamesAndPaths.isEmpty()) {
            return null;
        }
        int size = systemSharedLibraryNamesAndPaths.size();
        java.lang.String[] strArr = new java.lang.String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = systemSharedLibraryNamesAndPaths.keyAt(i);
        }
        return strArr;
    }

    @java.lang.Deprecated
    public final java.util.Map<java.lang.String, java.lang.String> getSystemSharedLibraryNamesAndPaths() {
        return snapshot().getSystemSharedLibraryNamesAndPaths();
    }

    @java.lang.Deprecated
    public final java.lang.String getSystemTextClassifierPackageName() {
        return this.mService.mSystemTextClassifierPackageName;
    }

    @java.lang.Deprecated
    public final int getTargetSdkVersion(@android.annotation.NonNull java.lang.String str) {
        return snapshot().getTargetSdkVersion(str);
    }

    @java.lang.Deprecated
    public final int getUidForSharedUser(@android.annotation.NonNull java.lang.String str) {
        return snapshot().getUidForSharedUser(str);
    }

    @android.annotation.SuppressLint({"MissingPermission"})
    @java.lang.Deprecated
    public final java.lang.String getWellbeingPackageName() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return (java.lang.String) com.android.internal.util.CollectionUtils.firstOrNull(((android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class)).getRoleHolders("android.app.role.SYSTEM_WELLBEING"));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.SuppressLint({"MissingPermission"})
    @java.lang.Deprecated
    public final void grantRuntimePermission(java.lang.String str, java.lang.String str2, int i) {
        ((android.permission.PermissionManager) this.mContext.getSystemService(android.permission.PermissionManager.class)).grantRuntimePermission(str, str2, android.os.UserHandle.of(i));
    }

    @java.lang.Deprecated
    public final boolean hasSigningCertificate(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr, int i) {
        return snapshot().hasSigningCertificate(str, bArr, i);
    }

    @java.lang.Deprecated
    public final boolean hasSystemFeature(java.lang.String str, int i) {
        return this.mService.hasSystemFeature(str, i);
    }

    @java.lang.Deprecated
    public final boolean hasSystemUidErrors() {
        return false;
    }

    @java.lang.Deprecated
    public final boolean hasUidSigningCertificate(int i, @android.annotation.NonNull byte[] bArr, int i2) {
        return snapshot().hasUidSigningCertificate(i, bArr, i2);
    }

    @java.lang.Deprecated
    public final boolean isDeviceUpgrading() {
        return this.mService.isDeviceUpgrading();
    }

    @java.lang.Deprecated
    public final boolean isFirstBoot() {
        return this.mService.isFirstBoot();
    }

    @java.lang.Deprecated
    public final boolean isInstantApp(java.lang.String str, int i) {
        return snapshot().isInstantApp(str, i);
    }

    @java.lang.Deprecated
    public final boolean isPackageAvailable(java.lang.String str, int i) {
        return snapshot().isPackageAvailable(str, i);
    }

    @java.lang.Deprecated
    public final boolean isPackageDeviceAdminOnAnyUser(java.lang.String str) {
        return this.mService.isPackageDeviceAdminOnAnyUser(snapshot(), str);
    }

    @java.lang.Deprecated
    public final boolean isPackageSignedByKeySet(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.KeySet keySet) {
        return snapshot().isPackageSignedByKeySet(str, keySet);
    }

    @java.lang.Deprecated
    public final boolean isPackageSignedByKeySetExactly(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.KeySet keySet) {
        return snapshot().isPackageSignedByKeySetExactly(str, keySet);
    }

    @java.lang.Deprecated
    public final boolean isPackageSuspendedForUser(@android.annotation.NonNull java.lang.String str, int i) {
        try {
            return snapshot().isPackageSuspendedForUser(str, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.IllegalArgumentException("Unknown target package: " + str);
        }
    }

    @java.lang.Deprecated
    public final boolean isPackageQuarantinedForUser(@android.annotation.NonNull java.lang.String str, int i) {
        try {
            return snapshot().isPackageQuarantinedForUser(str, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.IllegalArgumentException("Unknown target package: " + str);
        }
    }

    public final boolean isPackageStoppedForUser(@android.annotation.NonNull java.lang.String str, int i) {
        try {
            return snapshot().isPackageStoppedForUser(str, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.IllegalArgumentException("Unknown target package: " + str);
        }
    }

    @java.lang.Deprecated
    public final boolean isSafeMode() {
        return this.mService.getSafeMode();
    }

    @java.lang.Deprecated
    public final boolean isStorageLow() {
        return this.mService.isStorageLow();
    }

    @java.lang.Deprecated
    public final boolean isUidPrivileged(int i) {
        return snapshot().isUidPrivileged(i);
    }

    @java.lang.Deprecated
    public final boolean performDexOptMode(java.lang.String str, boolean z, java.lang.String str2, boolean z2, boolean z3, java.lang.String str3) {
        com.android.server.pm.Computer snapshot = snapshot();
        if (!z) {
            android.util.Log.w("PackageManager", "Ignored checkProfiles=false flag");
        }
        return this.mDexOptHelper.performDexOptMode(snapshot, str, str2, z2, z3, str3);
    }

    @java.lang.Deprecated
    public final boolean performDexOptSecondary(java.lang.String str, java.lang.String str2, boolean z) {
        return this.mDexOptHelper.performDexOptSecondary(str, str2, z);
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.ResolveInfo> queryIntentActivities(android.content.Intent intent, java.lang.String str, long j, int i) {
        try {
            android.os.Trace.traceBegin(262144L, "queryIntentActivities");
            return new android.content.pm.ParceledListSlice<>(snapshot().queryIntentActivitiesInternal(intent, str, j, i));
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.ProviderInfo> queryContentProviders(@android.annotation.Nullable java.lang.String str, int i, long j, @android.annotation.Nullable java.lang.String str2) {
        return snapshot().queryContentProviders(str, i, j, str2);
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.InstrumentationInfo> queryInstrumentationAsUser(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        return snapshot().queryInstrumentationAsUser(str, i, i2);
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.ResolveInfo> queryIntentActivityOptions(android.content.ComponentName componentName, android.content.Intent[] intentArr, java.lang.String[] strArr, android.content.Intent intent, java.lang.String str, long j, int i) {
        return new android.content.pm.ParceledListSlice<>(this.mResolveIntentHelper.queryIntentActivityOptionsInternal(snapshot(), componentName, intentArr, strArr, intent, str, j, i));
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.ResolveInfo> queryIntentContentProviders(android.content.Intent intent, java.lang.String str, long j, int i) {
        return new android.content.pm.ParceledListSlice<>(this.mResolveIntentHelper.queryIntentContentProvidersInternal(snapshot(), intent, str, j, i));
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.ResolveInfo> queryIntentReceivers(android.content.Intent intent, java.lang.String str, long j, int i) {
        return new android.content.pm.ParceledListSlice<>(this.mResolveIntentHelper.queryIntentReceiversInternal(snapshot(), intent, str, j, i, android.os.Binder.getCallingUid()));
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final android.content.pm.ParceledListSlice<android.content.pm.ResolveInfo> queryIntentServices(android.content.Intent intent, java.lang.String str, long j, int i) {
        return new android.content.pm.ParceledListSlice<>(snapshot().queryIntentServicesInternal(intent, str, j, i, android.os.Binder.getCallingUid(), false));
    }

    @java.lang.Deprecated
    public final void querySyncProviders(java.util.List<java.lang.String> list, java.util.List<android.content.pm.ProviderInfo> list2) {
        snapshot().querySyncProviders(isSafeMode(), list, list2);
    }

    @java.lang.Deprecated
    public final void removePermission(java.lang.String str) {
        ((android.permission.PermissionManager) this.mContext.getSystemService(android.permission.PermissionManager.class)).removePermission(str);
    }

    @java.lang.Deprecated
    public final void replacePreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2) {
        this.mPreferredActivityHelper.replacePreferredActivity(snapshot(), new com.android.server.pm.WatchedIntentFilter(intentFilter), i, componentNameArr, componentName, i2);
    }

    @java.lang.Deprecated
    public final android.content.pm.ProviderInfo resolveContentProvider(java.lang.String str, long j, int i) {
        return snapshot().resolveContentProvider(str, j, i, android.os.Binder.getCallingUid());
    }

    @java.lang.Deprecated
    public final void resetApplicationPreferences(int i) {
        this.mPreferredActivityHelper.resetApplicationPreferences(i);
    }

    @java.lang.Deprecated
    public final android.content.pm.ResolveInfo resolveIntent(android.content.Intent intent, java.lang.String str, long j, int i) {
        return this.mResolveIntentHelper.resolveIntentInternal(snapshot(), intent, str, j, 0L, i, false, android.os.Binder.getCallingUid());
    }

    @java.lang.Deprecated
    public final android.content.pm.ResolveInfo resolveService(android.content.Intent intent, java.lang.String str, long j, int i) {
        return this.mResolveIntentHelper.resolveServiceInternal(snapshot(), intent, str, j, i, android.os.Binder.getCallingUid());
    }

    @java.lang.Deprecated
    public final void restoreDefaultApps(byte[] bArr, int i) {
        this.mPreferredActivityHelper.restoreDefaultApps(bArr, i);
    }

    @java.lang.Deprecated
    public final void restorePreferredActivities(byte[] bArr, int i) {
        this.mPreferredActivityHelper.restorePreferredActivities(bArr, i);
    }

    @java.lang.Deprecated
    public final void setHomeActivity(android.content.ComponentName componentName, int i) {
        this.mPreferredActivityHelper.setHomeActivity(snapshot(), componentName, i);
    }

    @java.lang.Deprecated
    public final void setLastChosenActivity(android.content.Intent intent, java.lang.String str, int i, android.content.IntentFilter intentFilter, int i2, android.content.ComponentName componentName) {
        this.mPreferredActivityHelper.setLastChosenActivity(snapshot(), intent, str, i, new com.android.server.pm.WatchedIntentFilter(intentFilter), i2, componentName);
    }

    @java.lang.Deprecated
    public final boolean updateIntentVerificationStatus(java.lang.String str, int i, int i2) {
        return this.mDomainVerificationManager.setLegacyUserState(str, i2, i);
    }

    @java.lang.Deprecated
    public final void verifyIntentFilter(int i, int i2, java.util.List<java.lang.String> list) {
        com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1.queueLegacyVerifyResult(this.mContext, this.mDomainVerificationConnection, i, i2, list, android.os.Binder.getCallingUid());
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public final boolean[] canPackageQuery(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String[] strArr, int i) {
        return snapshot().canPackageQuery(str, strArr, i);
    }

    @java.lang.Deprecated
    public final void deletePreloadsFileCache() throws android.os.RemoteException {
        this.mService.deletePreloadsFileCache();
    }

    @java.lang.Deprecated
    public final void setSystemAppHiddenUntilInstalled(java.lang.String str, boolean z) throws android.os.RemoteException {
        this.mService.setSystemAppHiddenUntilInstalled(snapshot(), str, z);
    }

    @java.lang.Deprecated
    public final boolean setSystemAppInstallState(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
        return this.mService.setSystemAppInstallState(snapshot(), str, z, i);
    }

    @java.lang.Deprecated
    public final void finishPackageInstall(int i, boolean z) throws android.os.RemoteException {
        this.mService.finishPackageInstall(i, z);
    }
}
