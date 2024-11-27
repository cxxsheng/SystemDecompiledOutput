package android.content.pm;

/* loaded from: classes.dex */
public interface IPackageManager extends android.os.IInterface {
    boolean activitySupportsIntentAsUser(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str, int i) throws android.os.RemoteException;

    void addCrossProfileIntentFilter(android.content.IntentFilter intentFilter, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    boolean addPermission(android.content.pm.PermissionInfo permissionInfo) throws android.os.RemoteException;

    boolean addPermissionAsync(android.content.pm.PermissionInfo permissionInfo) throws android.os.RemoteException;

    void addPersistentPreferredActivity(android.content.IntentFilter intentFilter, android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    void addPreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2, boolean z) throws android.os.RemoteException;

    boolean canForwardTo(android.content.Intent intent, java.lang.String str, int i, int i2) throws android.os.RemoteException;

    boolean[] canPackageQuery(java.lang.String str, java.lang.String[] strArr, int i) throws android.os.RemoteException;

    boolean canRequestPackageInstalls(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String[] canonicalToCurrentPackageNames(java.lang.String[] strArr) throws android.os.RemoteException;

    void checkPackageStartable(java.lang.String str, int i) throws android.os.RemoteException;

    int checkPermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    int checkSignatures(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    int checkUidPermission(java.lang.String str, int i) throws android.os.RemoteException;

    int checkUidSignatures(int i, int i2) throws android.os.RemoteException;

    void clearApplicationProfileData(java.lang.String str) throws android.os.RemoteException;

    void clearApplicationUserData(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver, int i) throws android.os.RemoteException;

    void clearCrossProfileIntentFilters(int i, java.lang.String str) throws android.os.RemoteException;

    void clearPackagePersistentPreferredActivities(java.lang.String str, int i) throws android.os.RemoteException;

    void clearPackagePreferredActivities(java.lang.String str) throws android.os.RemoteException;

    void clearPersistentPreferredActivity(android.content.IntentFilter intentFilter, int i) throws android.os.RemoteException;

    java.lang.String[] currentToCanonicalPackageNames(java.lang.String[] strArr) throws android.os.RemoteException;

    void deleteApplicationCacheFiles(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException;

    void deleteApplicationCacheFilesAsUser(java.lang.String str, int i, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException;

    void deleteExistingPackageAsUser(android.content.pm.VersionedPackage versionedPackage, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, int i) throws android.os.RemoteException;

    @java.lang.Deprecated
    void deletePackageAsUser(java.lang.String str, int i, android.content.pm.IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) throws android.os.RemoteException;

    void deletePackageVersioned(android.content.pm.VersionedPackage versionedPackage, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) throws android.os.RemoteException;

    void deletePreloadsFileCache() throws android.os.RemoteException;

    void enterSafeMode() throws android.os.RemoteException;

    void extendVerificationTimeout(int i, int i2, long j) throws android.os.RemoteException;

    android.content.pm.ResolveInfo findPersistentPreferredActivity(android.content.Intent intent, int i) throws android.os.RemoteException;

    void finishPackageInstall(int i, boolean z) throws android.os.RemoteException;

    void flushPackageRestrictionsAsUser(int i) throws android.os.RemoteException;

    void freeStorage(java.lang.String str, long j, int i, android.content.IntentSender intentSender) throws android.os.RemoteException;

    void freeStorageAndNotify(java.lang.String str, long j, int i, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException;

    android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getAllIntentFilters(java.lang.String str) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAllPackages() throws android.os.RemoteException;

    android.os.ParcelFileDescriptor getAppMetadataFd(java.lang.String str, int i) throws android.os.RemoteException;

    int getAppMetadataSource(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String[] getAppOpPermissionPackages(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String getAppPredictionServicePackageName() throws android.os.RemoteException;

    int getApplicationEnabledSetting(java.lang.String str, int i) throws android.os.RemoteException;

    boolean getApplicationHiddenSettingAsUser(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, long j, int i) throws android.os.RemoteException;

    android.graphics.Bitmap getArchivedAppIcon(java.lang.String str, android.os.UserHandle userHandle, java.lang.String str2) throws android.os.RemoteException;

    android.content.pm.ArchivedPackageParcel getArchivedPackage(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.dex.IArtManager getArtManager() throws android.os.RemoteException;

    java.lang.String getAttentionServicePackageName() throws android.os.RemoteException;

    boolean getBlockUninstallForUser(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.ChangedPackages getChangedPackages(int i, int i2) throws android.os.RemoteException;

    int getComponentEnabledSetting(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getDeclaredSharedLibraries(java.lang.String str, long j, int i) throws android.os.RemoteException;

    byte[] getDefaultAppsBackup(int i) throws android.os.RemoteException;

    java.lang.String getDefaultTextClassifierPackageName() throws android.os.RemoteException;

    android.content.ComponentName getDomainVerificationAgent() throws android.os.RemoteException;

    byte[] getDomainVerificationBackup(int i) throws android.os.RemoteException;

    int getFlagsForUid(int i) throws android.os.RemoteException;

    java.lang.CharSequence getHarmfulAppWarning(java.lang.String str, int i) throws android.os.RemoteException;

    android.os.IBinder getHoldLockToken() throws android.os.RemoteException;

    android.content.ComponentName getHomeActivities(java.util.List<android.content.pm.ResolveInfo> list) throws android.os.RemoteException;

    java.lang.String getIncidentReportApproverPackageName() throws android.os.RemoteException;

    java.util.List<java.lang.String> getInitialNonStoppedSystemPackages() throws android.os.RemoteException;

    int getInstallLocation() throws android.os.RemoteException;

    int getInstallReason(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.InstallSourceInfo getInstallSourceInfo(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getInstalledApplications(long j, int i) throws android.os.RemoteException;

    java.util.List<android.content.pm.ModuleInfo> getInstalledModules(int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getInstalledPackages(long j, int i) throws android.os.RemoteException;

    java.lang.String getInstallerPackageName(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getInstantAppAndroidId(java.lang.String str, int i) throws android.os.RemoteException;

    byte[] getInstantAppCookie(java.lang.String str, int i) throws android.os.RemoteException;

    android.graphics.Bitmap getInstantAppIcon(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.ComponentName getInstantAppInstallerComponent() throws android.os.RemoteException;

    android.content.ComponentName getInstantAppResolverComponent() throws android.os.RemoteException;

    android.content.ComponentName getInstantAppResolverSettingsComponent() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getInstantApps(int i) throws android.os.RemoteException;

    android.content.pm.InstrumentationInfo getInstrumentationInfoAsUser(android.content.ComponentName componentName, int i, int i2) throws android.os.RemoteException;

    @java.lang.Deprecated
    android.content.pm.ParceledListSlice getIntentFilterVerifications(java.lang.String str) throws android.os.RemoteException;

    @java.lang.Deprecated
    int getIntentVerificationStatus(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.KeySet getKeySetByAlias(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.content.pm.ResolveInfo getLastChosenActivity(android.content.Intent intent, java.lang.String str, int i) throws android.os.RemoteException;

    android.content.IntentSender getLaunchIntentSenderForPackage(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getMimeGroup(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.content.pm.ModuleInfo getModuleInfo(java.lang.String str, int i) throws android.os.RemoteException;

    int getMoveStatus(int i) throws android.os.RemoteException;

    java.lang.String getNameForUid(int i) throws android.os.RemoteException;

    java.lang.String[] getNamesForUids(int[] iArr) throws android.os.RemoteException;

    int[] getPackageGids(java.lang.String str, long j, int i) throws android.os.RemoteException;

    android.content.pm.PackageInfo getPackageInfo(java.lang.String str, long j, int i) throws android.os.RemoteException;

    android.content.pm.PackageInfo getPackageInfoVersioned(android.content.pm.VersionedPackage versionedPackage, long j, int i) throws android.os.RemoteException;

    android.content.pm.IPackageInstaller getPackageInstaller() throws android.os.RemoteException;

    void getPackageSizeInfo(java.lang.String str, int i, android.content.pm.IPackageStatsObserver iPackageStatsObserver) throws android.os.RemoteException;

    int getPackageUid(java.lang.String str, long j, int i) throws android.os.RemoteException;

    java.lang.String[] getPackagesForUid(int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getPackagesHoldingPermissions(java.lang.String[] strArr, long j, int i) throws android.os.RemoteException;

    java.lang.String getPermissionControllerPackageName() throws android.os.RemoteException;

    android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getPersistentApplications(int i) throws android.os.RemoteException;

    int getPreferredActivities(java.util.List<android.content.IntentFilter> list, java.util.List<android.content.ComponentName> list2, java.lang.String str) throws android.os.RemoteException;

    byte[] getPreferredActivityBackup(int i) throws android.os.RemoteException;

    int getPrivateFlagsForUid(int i) throws android.os.RemoteException;

    android.content.pm.PackageManager.Property getPropertyAsUser(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException;

    android.content.pm.ProviderInfo getProviderInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException;

    android.content.pm.ActivityInfo getReceiverInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException;

    java.lang.String getRotationResolverPackageName() throws android.os.RemoteException;

    int getRuntimePermissionsVersion(int i) throws android.os.RemoteException;

    java.lang.String getSdkSandboxPackageName() throws android.os.RemoteException;

    android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException;

    java.lang.String getServicesSystemSharedLibraryPackageName() throws android.os.RemoteException;

    java.lang.String getSetupWizardPackageName() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getSharedLibraries(java.lang.String str, long j, int i) throws android.os.RemoteException;

    java.lang.String getSharedSystemSharedLibraryPackageName() throws android.os.RemoteException;

    android.content.pm.KeySet getSigningKeySet(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getSplashScreenTheme(java.lang.String str, int i) throws android.os.RemoteException;

    android.os.Bundle getSuspendedPackageAppExtras(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String getSuspendingPackage(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getSystemAvailableFeatures() throws android.os.RemoteException;

    java.lang.String getSystemCaptionsServicePackageName() throws android.os.RemoteException;

    @java.lang.Deprecated
    java.lang.String[] getSystemSharedLibraryNames() throws android.os.RemoteException;

    java.util.Map<java.lang.String, java.lang.String> getSystemSharedLibraryNamesAndPaths() throws android.os.RemoteException;

    java.lang.String getSystemTextClassifierPackageName() throws android.os.RemoteException;

    int getTargetSdkVersion(java.lang.String str) throws android.os.RemoteException;

    int getUidForSharedUser(java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] getUnsuspendablePackagesForUser(java.lang.String[] strArr, int i) throws android.os.RemoteException;

    int getUserMinAspectRatio(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.VerifierDeviceIdentity getVerifierDeviceIdentity() throws android.os.RemoteException;

    java.lang.String getWellbeingPackageName() throws android.os.RemoteException;

    void grantRuntimePermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    boolean hasSigningCertificate(java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException;

    boolean hasSystemFeature(java.lang.String str, int i) throws android.os.RemoteException;

    boolean hasSystemUidErrors() throws android.os.RemoteException;

    boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) throws android.os.RemoteException;

    void holdLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    int installExistingPackageAsUser(java.lang.String str, int i, int i2, int i3, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    boolean isAppArchivable(java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException;

    boolean isAutoRevokeWhitelisted(java.lang.String str) throws android.os.RemoteException;

    boolean isDeviceUpgrading() throws android.os.RemoteException;

    boolean isFirstBoot() throws android.os.RemoteException;

    boolean isInstantApp(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isPackageAvailable(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isPackageDeviceAdminOnAnyUser(java.lang.String str) throws android.os.RemoteException;

    boolean isPackageQuarantinedForUser(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isPackageSignedByKeySet(java.lang.String str, android.content.pm.KeySet keySet) throws android.os.RemoteException;

    boolean isPackageSignedByKeySetExactly(java.lang.String str, android.content.pm.KeySet keySet) throws android.os.RemoteException;

    boolean isPackageStateProtected(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isPackageStoppedForUser(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isPackageSuspendedForUser(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isProtectedBroadcast(java.lang.String str) throws android.os.RemoteException;

    boolean isSafeMode() throws android.os.RemoteException;

    boolean isStorageLow() throws android.os.RemoteException;

    boolean isUidPrivileged(int i) throws android.os.RemoteException;

    void logAppProcessStartIfNeeded(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, java.lang.String str4, int i2) throws android.os.RemoteException;

    void makeProviderVisible(int i, java.lang.String str) throws android.os.RemoteException;

    void makeUidVisible(int i, int i2) throws android.os.RemoteException;

    int movePackage(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int movePrimaryStorage(java.lang.String str) throws android.os.RemoteException;

    void notifyDexLoad(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map, java.lang.String str2) throws android.os.RemoteException;

    void notifyPackageUse(java.lang.String str, int i) throws android.os.RemoteException;

    void notifyPackagesReplacedReceived(java.lang.String[] strArr) throws android.os.RemoteException;

    void overrideLabelAndIcon(android.content.ComponentName componentName, java.lang.String str, int i, int i2) throws android.os.RemoteException;

    boolean performDexOptMode(java.lang.String str, boolean z, java.lang.String str2, boolean z2, boolean z3, java.lang.String str3) throws android.os.RemoteException;

    boolean performDexOptSecondary(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryContentProviders(java.lang.String str, int i, long j, java.lang.String str2) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryInstrumentationAsUser(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryIntentActivities(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryIntentActivityOptions(android.content.ComponentName componentName, android.content.Intent[] intentArr, java.lang.String[] strArr, android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryIntentContentProviders(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryIntentReceivers(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryIntentServices(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryProperty(java.lang.String str, int i) throws android.os.RemoteException;

    void querySyncProviders(java.util.List<java.lang.String> list, java.util.List<android.content.pm.ProviderInfo> list2) throws android.os.RemoteException;

    void registerDexModule(java.lang.String str, java.lang.String str2, boolean z, android.content.pm.IDexModuleRegisterCallback iDexModuleRegisterCallback) throws android.os.RemoteException;

    void registerMoveCallback(android.content.pm.IPackageMoveObserver iPackageMoveObserver) throws android.os.RemoteException;

    void registerPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException;

    void relinquishUpdateOwnership(java.lang.String str) throws android.os.RemoteException;

    boolean removeCrossProfileIntentFilter(android.content.IntentFilter intentFilter, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void removePermission(java.lang.String str) throws android.os.RemoteException;

    void replacePreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2) throws android.os.RemoteException;

    void requestPackageChecksums(java.lang.String str, boolean z, int i, int i2, java.util.List list, android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3) throws android.os.RemoteException;

    void resetApplicationPreferences(int i) throws android.os.RemoteException;

    android.content.pm.ProviderInfo resolveContentProvider(java.lang.String str, long j, int i) throws android.os.RemoteException;

    android.content.pm.ResolveInfo resolveIntent(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException;

    android.content.pm.ResolveInfo resolveService(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException;

    void restoreDefaultApps(byte[] bArr, int i) throws android.os.RemoteException;

    void restoreDomainVerification(byte[] bArr, int i) throws android.os.RemoteException;

    void restoreLabelAndIcon(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    void restorePreferredActivities(byte[] bArr, int i) throws android.os.RemoteException;

    void sendDeviceCustomizationReadyBroadcast() throws android.os.RemoteException;

    void setApplicationCategoryHint(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void setApplicationEnabledSetting(java.lang.String str, int i, int i2, int i3, java.lang.String str2) throws android.os.RemoteException;

    boolean setApplicationHiddenSettingAsUser(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    boolean setBlockUninstallForUser(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    void setComponentEnabledSetting(android.content.ComponentName componentName, int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException;

    void setComponentEnabledSettings(java.util.List<android.content.pm.PackageManager.ComponentEnabledSetting> list, int i, java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] setDistractingPackageRestrictionsAsUser(java.lang.String[] strArr, int i, int i2) throws android.os.RemoteException;

    void setHarmfulAppWarning(java.lang.String str, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException;

    void setHomeActivity(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    boolean setInstallLocation(int i) throws android.os.RemoteException;

    void setInstallerPackageName(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean setInstantAppCookie(java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException;

    void setKeepUninstalledPackages(java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void setLastChosenActivity(android.content.Intent intent, java.lang.String str, int i, android.content.IntentFilter intentFilter, int i2, android.content.ComponentName componentName) throws android.os.RemoteException;

    void setMimeGroup(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void setPackageStoppedState(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    java.lang.String[] setPackagesSuspendedAsUser(java.lang.String[] strArr, boolean z, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.content.pm.SuspendDialogInfo suspendDialogInfo, int i, java.lang.String str, int i2, int i3) throws android.os.RemoteException;

    boolean setRequiredForSystemUser(java.lang.String str, boolean z) throws android.os.RemoteException;

    void setRuntimePermissionsVersion(int i, int i2) throws android.os.RemoteException;

    void setSplashScreenTheme(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void setSystemAppHiddenUntilInstalled(java.lang.String str, boolean z) throws android.os.RemoteException;

    boolean setSystemAppInstallState(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    void setUpdateAvailable(java.lang.String str, boolean z) throws android.os.RemoteException;

    void setUserMinAspectRatio(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void unregisterMoveCallback(android.content.pm.IPackageMoveObserver iPackageMoveObserver) throws android.os.RemoteException;

    void unregisterPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    @java.lang.Deprecated
    boolean updateIntentVerificationStatus(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    @java.lang.Deprecated
    void verifyIntentFilter(int i, int i2, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void verifyPendingInstall(int i, int i2) throws android.os.RemoteException;

    boolean waitForHandler(long j, boolean z) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IPackageManager {
        @Override // android.content.pm.IPackageManager
        public void checkPackageStartable(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageAvailable(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.PackageInfo getPackageInfo(java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.PackageInfo getPackageInfoVersioned(android.content.pm.VersionedPackage versionedPackage, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getPackageUid(java.lang.String str, long j, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int[] getPackageGids(java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String[] currentToCanonicalPackageNames(java.lang.String[] strArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String[] canonicalToCurrentPackageNames(java.lang.String[] strArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getTargetSdkVersion(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean activitySupportsIntentAsUser(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ActivityInfo getReceiverInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ProviderInfo getProviderInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isProtectedBroadcast(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public int checkSignatures(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int checkUidSignatures(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public java.util.List<java.lang.String> getAllPackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String[] getPackagesForUid(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getNameForUid(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String[] getNamesForUids(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getUidForSharedUser(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int getFlagsForUid(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int getPrivateFlagsForUid(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isUidPrivileged(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ResolveInfo resolveIntent(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ResolveInfo findPersistentPreferredActivity(android.content.Intent intent, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean canForwardTo(android.content.Intent intent, java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice queryIntentActivities(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice queryIntentActivityOptions(android.content.ComponentName componentName, android.content.Intent[] intentArr, java.lang.String[] strArr, android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice queryIntentReceivers(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ResolveInfo resolveService(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice queryIntentServices(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice queryIntentContentProviders(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice getInstalledPackages(long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.os.ParcelFileDescriptor getAppMetadataFd(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice getPackagesHoldingPermissions(java.lang.String[] strArr, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice getInstalledApplications(long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice getPersistentApplications(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ProviderInfo resolveContentProvider(java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void querySyncProviders(java.util.List<java.lang.String> list, java.util.List<android.content.pm.ProviderInfo> list2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice queryContentProviders(java.lang.String str, int i, long j, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.InstrumentationInfo getInstrumentationInfoAsUser(android.content.ComponentName componentName, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice queryInstrumentationAsUser(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void finishPackageInstall(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setInstallerPackageName(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void relinquishUpdateOwnership(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setApplicationCategoryHint(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deletePackageAsUser(java.lang.String str, int i, android.content.pm.IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deletePackageVersioned(android.content.pm.VersionedPackage versionedPackage, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deleteExistingPackageAsUser(android.content.pm.VersionedPackage versionedPackage, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getInstallerPackageName(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.InstallSourceInfo getInstallSourceInfo(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void resetApplicationPreferences(int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ResolveInfo getLastChosenActivity(android.content.Intent intent, java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setLastChosenActivity(android.content.Intent intent, java.lang.String str, int i, android.content.IntentFilter intentFilter, int i2, android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void addPreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void replacePreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearPackagePreferredActivities(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int getPreferredActivities(java.util.List<android.content.IntentFilter> list, java.util.List<android.content.ComponentName> list2, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void addPersistentPreferredActivity(android.content.IntentFilter intentFilter, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearPackagePersistentPreferredActivities(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearPersistentPreferredActivity(android.content.IntentFilter intentFilter, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void addCrossProfileIntentFilter(android.content.IntentFilter intentFilter, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean removeCrossProfileIntentFilter(android.content.IntentFilter intentFilter, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void clearCrossProfileIntentFilters(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String[] setDistractingPackageRestrictionsAsUser(java.lang.String[] strArr, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String[] setPackagesSuspendedAsUser(java.lang.String[] strArr, boolean z, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.content.pm.SuspendDialogInfo suspendDialogInfo, int i, java.lang.String str, int i2, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String[] getUnsuspendablePackagesForUser(java.lang.String[] strArr, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageSuspendedForUser(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageQuarantinedForUser(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageStoppedForUser(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public android.os.Bundle getSuspendedPackageAppExtras(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getSuspendingPackage(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getPreferredActivityBackup(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void restorePreferredActivities(byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getDefaultAppsBackup(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void restoreDefaultApps(byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getDomainVerificationBackup(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void restoreDomainVerification(byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public android.content.ComponentName getHomeActivities(java.util.List<android.content.pm.ResolveInfo> list) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setHomeActivity(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void overrideLabelAndIcon(android.content.ComponentName componentName, java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void restoreLabelAndIcon(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setComponentEnabledSetting(android.content.ComponentName componentName, int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setComponentEnabledSettings(java.util.List<android.content.pm.PackageManager.ComponentEnabledSetting> list, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int getComponentEnabledSetting(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void setApplicationEnabledSetting(java.lang.String str, int i, int i2, int i3, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int getApplicationEnabledSetting(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void logAppProcessStartIfNeeded(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, java.lang.String str4, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void flushPackageRestrictionsAsUser(int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setPackageStoppedState(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void freeStorageAndNotify(java.lang.String str, long j, int i, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void freeStorage(java.lang.String str, long j, int i, android.content.IntentSender intentSender) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deleteApplicationCacheFiles(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deleteApplicationCacheFilesAsUser(java.lang.String str, int i, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearApplicationUserData(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearApplicationProfileData(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void getPackageSizeInfo(java.lang.String str, int i, android.content.pm.IPackageStatsObserver iPackageStatsObserver) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String[] getSystemSharedLibraryNames() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.util.Map<java.lang.String, java.lang.String> getSystemSharedLibraryNamesAndPaths() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice getSystemAvailableFeatures() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasSystemFeature(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public java.util.List<java.lang.String> getInitialNonStoppedSystemPackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void enterSafeMode() throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean isSafeMode() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasSystemUidErrors() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void notifyPackageUse(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void notifyDexLoad(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void registerDexModule(java.lang.String str, java.lang.String str2, boolean z, android.content.pm.IDexModuleRegisterCallback iDexModuleRegisterCallback) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean performDexOptMode(java.lang.String str, boolean z, java.lang.String str2, boolean z2, boolean z3, java.lang.String str3) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean performDexOptSecondary(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public int getMoveStatus(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void registerMoveCallback(android.content.pm.IPackageMoveObserver iPackageMoveObserver) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void unregisterMoveCallback(android.content.pm.IPackageMoveObserver iPackageMoveObserver) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int movePackage(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int movePrimaryStorage(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public boolean setInstallLocation(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public int getInstallLocation() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int installExistingPackageAsUser(java.lang.String str, int i, int i2, int i3, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void verifyPendingInstall(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void extendVerificationTimeout(int i, int i2, long j) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void verifyIntentFilter(int i, int i2, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int getIntentVerificationStatus(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public boolean updateIntentVerificationStatus(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice getIntentFilterVerifications(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice getAllIntentFilters(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.VerifierDeviceIdentity getVerifierDeviceIdentity() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isFirstBoot() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isDeviceUpgrading() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isStorageLow() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean setApplicationHiddenSettingAsUser(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean getApplicationHiddenSettingAsUser(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void setSystemAppHiddenUntilInstalled(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean setSystemAppInstallState(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.IPackageInstaller getPackageInstaller() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean setBlockUninstallForUser(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean getBlockUninstallForUser(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.KeySet getKeySetByAlias(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.KeySet getSigningKeySet(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageSignedByKeySet(java.lang.String str, android.content.pm.KeySet keySet) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageSignedByKeySetExactly(java.lang.String str, android.content.pm.KeySet keySet) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getPermissionControllerPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getSdkSandboxPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice getInstantApps(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getInstantAppCookie(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean setInstantAppCookie(java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public android.graphics.Bitmap getInstantAppIcon(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isInstantApp(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean setRequiredForSystemUser(java.lang.String str, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void setUpdateAvailable(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getServicesSystemSharedLibraryPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getSharedSystemSharedLibraryPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ChangedPackages getChangedPackages(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageDeviceAdminOnAnyUser(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public int getInstallReason(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice getSharedLibraries(java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice getDeclaredSharedLibraries(java.lang.String str, long j, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean canRequestPackageInstalls(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void deletePreloadsFileCache() throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public android.content.ComponentName getInstantAppResolverComponent() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.ComponentName getInstantAppResolverSettingsComponent() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.ComponentName getInstantAppInstallerComponent() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getInstantAppAndroidId(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.dex.IArtManager getArtManager() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setHarmfulAppWarning(java.lang.String str, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.CharSequence getHarmfulAppWarning(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasSigningCertificate(java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getDefaultTextClassifierPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getSystemTextClassifierPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getAttentionServicePackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getRotationResolverPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getWellbeingPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getAppPredictionServicePackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getSystemCaptionsServicePackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getSetupWizardPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getIncidentReportApproverPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageStateProtected(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void sendDeviceCustomizationReadyBroadcast() throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public java.util.List<android.content.pm.ModuleInfo> getInstalledModules(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ModuleInfo getModuleInfo(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getRuntimePermissionsVersion(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void setRuntimePermissionsVersion(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void notifyPackagesReplacedReceived(java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void requestPackageChecksums(java.lang.String str, boolean z, int i, int i2, java.util.List list, android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public android.content.IntentSender getLaunchIntentSenderForPackage(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String[] getAppOpPermissionPackages(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean addPermission(android.content.pm.PermissionInfo permissionInfo) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean addPermissionAsync(android.content.pm.PermissionInfo permissionInfo) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void removePermission(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int checkPermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void grantRuntimePermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int checkUidPermission(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void setMimeGroup(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public java.lang.String getSplashScreenTheme(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setSplashScreenTheme(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int getUserMinAspectRatio(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void setUserMinAspectRatio(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public java.util.List<java.lang.String> getMimeGroup(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isAutoRevokeWhitelisted(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void makeProviderVisible(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void makeUidVisible(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public android.os.IBinder getHoldLockToken() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void holdLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.PackageManager.Property getPropertyAsUser(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ParceledListSlice queryProperty(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setKeepUninstalledPackages(java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean[] canPackageQuery(java.lang.String str, java.lang.String[] strArr, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean waitForHandler(long j, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void registerPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void unregisterPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public android.content.pm.ArchivedPackageParcel getArchivedPackage(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public android.graphics.Bitmap getArchivedAppIcon(java.lang.String str, android.os.UserHandle userHandle, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isAppArchivable(java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public int getAppMetadataSource(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public android.content.ComponentName getDomainVerificationAgent() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IPackageManager {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.IPackageManager";
        static final int TRANSACTION_activitySupportsIntentAsUser = 12;
        static final int TRANSACTION_addCrossProfileIntentFilter = 65;
        static final int TRANSACTION_addPermission = 191;
        static final int TRANSACTION_addPermissionAsync = 192;
        static final int TRANSACTION_addPersistentPreferredActivity = 62;
        static final int TRANSACTION_addPreferredActivity = 58;
        static final int TRANSACTION_canForwardTo = 29;
        static final int TRANSACTION_canPackageQuery = 211;
        static final int TRANSACTION_canRequestPackageInstalls = 160;
        static final int TRANSACTION_canonicalToCurrentPackageNames = 8;
        static final int TRANSACTION_checkPackageStartable = 1;
        static final int TRANSACTION_checkPermission = 194;
        static final int TRANSACTION_checkSignatures = 17;
        static final int TRANSACTION_checkUidPermission = 196;
        static final int TRANSACTION_checkUidSignatures = 18;
        static final int TRANSACTION_clearApplicationProfileData = 99;
        static final int TRANSACTION_clearApplicationUserData = 98;
        static final int TRANSACTION_clearCrossProfileIntentFilters = 67;
        static final int TRANSACTION_clearPackagePersistentPreferredActivities = 63;
        static final int TRANSACTION_clearPackagePreferredActivities = 60;
        static final int TRANSACTION_clearPersistentPreferredActivity = 64;
        static final int TRANSACTION_currentToCanonicalPackageNames = 7;
        static final int TRANSACTION_deleteApplicationCacheFiles = 96;
        static final int TRANSACTION_deleteApplicationCacheFilesAsUser = 97;
        static final int TRANSACTION_deleteExistingPackageAsUser = 52;
        static final int TRANSACTION_deletePackageAsUser = 50;
        static final int TRANSACTION_deletePackageVersioned = 51;
        static final int TRANSACTION_deletePreloadsFileCache = 161;
        static final int TRANSACTION_enterSafeMode = 106;
        static final int TRANSACTION_extendVerificationTimeout = 123;
        static final int TRANSACTION_findPersistentPreferredActivity = 28;
        static final int TRANSACTION_finishPackageInstall = 46;
        static final int TRANSACTION_flushPackageRestrictionsAsUser = 92;
        static final int TRANSACTION_freeStorage = 95;
        static final int TRANSACTION_freeStorageAndNotify = 94;
        static final int TRANSACTION_getActivityInfo = 11;
        static final int TRANSACTION_getAllIntentFilters = 128;
        static final int TRANSACTION_getAllPackages = 19;
        static final int TRANSACTION_getAppMetadataFd = 37;
        static final int TRANSACTION_getAppMetadataSource = 218;
        static final int TRANSACTION_getAppOpPermissionPackages = 189;
        static final int TRANSACTION_getAppPredictionServicePackageName = 176;
        static final int TRANSACTION_getApplicationEnabledSetting = 90;
        static final int TRANSACTION_getApplicationHiddenSettingAsUser = 134;
        static final int TRANSACTION_getApplicationInfo = 9;
        static final int TRANSACTION_getArchivedAppIcon = 216;
        static final int TRANSACTION_getArchivedPackage = 215;
        static final int TRANSACTION_getArtManager = 166;
        static final int TRANSACTION_getAttentionServicePackageName = 173;
        static final int TRANSACTION_getBlockUninstallForUser = 139;
        static final int TRANSACTION_getChangedPackages = 155;
        static final int TRANSACTION_getComponentEnabledSetting = 88;
        static final int TRANSACTION_getDeclaredSharedLibraries = 159;
        static final int TRANSACTION_getDefaultAppsBackup = 78;
        static final int TRANSACTION_getDefaultTextClassifierPackageName = 171;
        static final int TRANSACTION_getDomainVerificationAgent = 219;
        static final int TRANSACTION_getDomainVerificationBackup = 80;
        static final int TRANSACTION_getFlagsForUid = 24;
        static final int TRANSACTION_getHarmfulAppWarning = 168;
        static final int TRANSACTION_getHoldLockToken = 206;
        static final int TRANSACTION_getHomeActivities = 82;
        static final int TRANSACTION_getIncidentReportApproverPackageName = 179;
        static final int TRANSACTION_getInitialNonStoppedSystemPackages = 105;
        static final int TRANSACTION_getInstallLocation = 120;
        static final int TRANSACTION_getInstallReason = 157;
        static final int TRANSACTION_getInstallSourceInfo = 54;
        static final int TRANSACTION_getInstalledApplications = 39;
        static final int TRANSACTION_getInstalledModules = 182;
        static final int TRANSACTION_getInstalledPackages = 36;
        static final int TRANSACTION_getInstallerPackageName = 53;
        static final int TRANSACTION_getInstantAppAndroidId = 165;
        static final int TRANSACTION_getInstantAppCookie = 147;
        static final int TRANSACTION_getInstantAppIcon = 149;
        static final int TRANSACTION_getInstantAppInstallerComponent = 164;
        static final int TRANSACTION_getInstantAppResolverComponent = 162;
        static final int TRANSACTION_getInstantAppResolverSettingsComponent = 163;
        static final int TRANSACTION_getInstantApps = 146;
        static final int TRANSACTION_getInstrumentationInfoAsUser = 44;
        static final int TRANSACTION_getIntentFilterVerifications = 127;
        static final int TRANSACTION_getIntentVerificationStatus = 125;
        static final int TRANSACTION_getKeySetByAlias = 140;
        static final int TRANSACTION_getLastChosenActivity = 56;
        static final int TRANSACTION_getLaunchIntentSenderForPackage = 188;
        static final int TRANSACTION_getMimeGroup = 202;
        static final int TRANSACTION_getModuleInfo = 183;
        static final int TRANSACTION_getMoveStatus = 114;
        static final int TRANSACTION_getNameForUid = 21;
        static final int TRANSACTION_getNamesForUids = 22;
        static final int TRANSACTION_getPackageGids = 6;
        static final int TRANSACTION_getPackageInfo = 3;
        static final int TRANSACTION_getPackageInfoVersioned = 4;
        static final int TRANSACTION_getPackageInstaller = 137;
        static final int TRANSACTION_getPackageSizeInfo = 100;
        static final int TRANSACTION_getPackageUid = 5;
        static final int TRANSACTION_getPackagesForUid = 20;
        static final int TRANSACTION_getPackagesHoldingPermissions = 38;
        static final int TRANSACTION_getPermissionControllerPackageName = 144;
        static final int TRANSACTION_getPermissionGroupInfo = 190;
        static final int TRANSACTION_getPersistentApplications = 40;
        static final int TRANSACTION_getPreferredActivities = 61;
        static final int TRANSACTION_getPreferredActivityBackup = 76;
        static final int TRANSACTION_getPrivateFlagsForUid = 25;
        static final int TRANSACTION_getPropertyAsUser = 208;
        static final int TRANSACTION_getProviderInfo = 15;
        static final int TRANSACTION_getReceiverInfo = 13;
        static final int TRANSACTION_getRotationResolverPackageName = 174;
        static final int TRANSACTION_getRuntimePermissionsVersion = 184;
        static final int TRANSACTION_getSdkSandboxPackageName = 145;
        static final int TRANSACTION_getServiceInfo = 14;
        static final int TRANSACTION_getServicesSystemSharedLibraryPackageName = 153;
        static final int TRANSACTION_getSetupWizardPackageName = 178;
        static final int TRANSACTION_getSharedLibraries = 158;
        static final int TRANSACTION_getSharedSystemSharedLibraryPackageName = 154;
        static final int TRANSACTION_getSigningKeySet = 141;
        static final int TRANSACTION_getSplashScreenTheme = 198;
        static final int TRANSACTION_getSuspendedPackageAppExtras = 74;
        static final int TRANSACTION_getSuspendingPackage = 75;
        static final int TRANSACTION_getSystemAvailableFeatures = 103;
        static final int TRANSACTION_getSystemCaptionsServicePackageName = 177;
        static final int TRANSACTION_getSystemSharedLibraryNames = 101;
        static final int TRANSACTION_getSystemSharedLibraryNamesAndPaths = 102;
        static final int TRANSACTION_getSystemTextClassifierPackageName = 172;
        static final int TRANSACTION_getTargetSdkVersion = 10;
        static final int TRANSACTION_getUidForSharedUser = 23;
        static final int TRANSACTION_getUnsuspendablePackagesForUser = 70;
        static final int TRANSACTION_getUserMinAspectRatio = 200;
        static final int TRANSACTION_getVerifierDeviceIdentity = 129;
        static final int TRANSACTION_getWellbeingPackageName = 175;
        static final int TRANSACTION_grantRuntimePermission = 195;
        static final int TRANSACTION_hasSigningCertificate = 169;
        static final int TRANSACTION_hasSystemFeature = 104;
        static final int TRANSACTION_hasSystemUidErrors = 108;
        static final int TRANSACTION_hasUidSigningCertificate = 170;
        static final int TRANSACTION_holdLock = 207;
        static final int TRANSACTION_installExistingPackageAsUser = 121;
        static final int TRANSACTION_isAppArchivable = 217;
        static final int TRANSACTION_isAutoRevokeWhitelisted = 203;
        static final int TRANSACTION_isDeviceUpgrading = 131;
        static final int TRANSACTION_isFirstBoot = 130;
        static final int TRANSACTION_isInstantApp = 150;
        static final int TRANSACTION_isPackageAvailable = 2;
        static final int TRANSACTION_isPackageDeviceAdminOnAnyUser = 156;
        static final int TRANSACTION_isPackageQuarantinedForUser = 72;
        static final int TRANSACTION_isPackageSignedByKeySet = 142;
        static final int TRANSACTION_isPackageSignedByKeySetExactly = 143;
        static final int TRANSACTION_isPackageStateProtected = 180;
        static final int TRANSACTION_isPackageStoppedForUser = 73;
        static final int TRANSACTION_isPackageSuspendedForUser = 71;
        static final int TRANSACTION_isProtectedBroadcast = 16;
        static final int TRANSACTION_isSafeMode = 107;
        static final int TRANSACTION_isStorageLow = 132;
        static final int TRANSACTION_isUidPrivileged = 26;
        static final int TRANSACTION_logAppProcessStartIfNeeded = 91;
        static final int TRANSACTION_makeProviderVisible = 204;
        static final int TRANSACTION_makeUidVisible = 205;
        static final int TRANSACTION_movePackage = 117;
        static final int TRANSACTION_movePrimaryStorage = 118;
        static final int TRANSACTION_notifyDexLoad = 110;
        static final int TRANSACTION_notifyPackageUse = 109;
        static final int TRANSACTION_notifyPackagesReplacedReceived = 186;
        static final int TRANSACTION_overrideLabelAndIcon = 84;
        static final int TRANSACTION_performDexOptMode = 112;
        static final int TRANSACTION_performDexOptSecondary = 113;
        static final int TRANSACTION_queryContentProviders = 43;
        static final int TRANSACTION_queryInstrumentationAsUser = 45;
        static final int TRANSACTION_queryIntentActivities = 30;
        static final int TRANSACTION_queryIntentActivityOptions = 31;
        static final int TRANSACTION_queryIntentContentProviders = 35;
        static final int TRANSACTION_queryIntentReceivers = 32;
        static final int TRANSACTION_queryIntentServices = 34;
        static final int TRANSACTION_queryProperty = 209;
        static final int TRANSACTION_querySyncProviders = 42;
        static final int TRANSACTION_registerDexModule = 111;
        static final int TRANSACTION_registerMoveCallback = 115;
        static final int TRANSACTION_registerPackageMonitorCallback = 213;
        static final int TRANSACTION_relinquishUpdateOwnership = 48;
        static final int TRANSACTION_removeCrossProfileIntentFilter = 66;
        static final int TRANSACTION_removePermission = 193;
        static final int TRANSACTION_replacePreferredActivity = 59;
        static final int TRANSACTION_requestPackageChecksums = 187;
        static final int TRANSACTION_resetApplicationPreferences = 55;
        static final int TRANSACTION_resolveContentProvider = 41;
        static final int TRANSACTION_resolveIntent = 27;
        static final int TRANSACTION_resolveService = 33;
        static final int TRANSACTION_restoreDefaultApps = 79;
        static final int TRANSACTION_restoreDomainVerification = 81;
        static final int TRANSACTION_restoreLabelAndIcon = 85;
        static final int TRANSACTION_restorePreferredActivities = 77;
        static final int TRANSACTION_sendDeviceCustomizationReadyBroadcast = 181;
        static final int TRANSACTION_setApplicationCategoryHint = 49;
        static final int TRANSACTION_setApplicationEnabledSetting = 89;
        static final int TRANSACTION_setApplicationHiddenSettingAsUser = 133;
        static final int TRANSACTION_setBlockUninstallForUser = 138;
        static final int TRANSACTION_setComponentEnabledSetting = 86;
        static final int TRANSACTION_setComponentEnabledSettings = 87;
        static final int TRANSACTION_setDistractingPackageRestrictionsAsUser = 68;
        static final int TRANSACTION_setHarmfulAppWarning = 167;
        static final int TRANSACTION_setHomeActivity = 83;
        static final int TRANSACTION_setInstallLocation = 119;
        static final int TRANSACTION_setInstallerPackageName = 47;
        static final int TRANSACTION_setInstantAppCookie = 148;
        static final int TRANSACTION_setKeepUninstalledPackages = 210;
        static final int TRANSACTION_setLastChosenActivity = 57;
        static final int TRANSACTION_setMimeGroup = 197;
        static final int TRANSACTION_setPackageStoppedState = 93;
        static final int TRANSACTION_setPackagesSuspendedAsUser = 69;
        static final int TRANSACTION_setRequiredForSystemUser = 151;
        static final int TRANSACTION_setRuntimePermissionsVersion = 185;
        static final int TRANSACTION_setSplashScreenTheme = 199;
        static final int TRANSACTION_setSystemAppHiddenUntilInstalled = 135;
        static final int TRANSACTION_setSystemAppInstallState = 136;
        static final int TRANSACTION_setUpdateAvailable = 152;
        static final int TRANSACTION_setUserMinAspectRatio = 201;
        static final int TRANSACTION_unregisterMoveCallback = 116;
        static final int TRANSACTION_unregisterPackageMonitorCallback = 214;
        static final int TRANSACTION_updateIntentVerificationStatus = 126;
        static final int TRANSACTION_verifyIntentFilter = 124;
        static final int TRANSACTION_verifyPendingInstall = 122;
        static final int TRANSACTION_waitForHandler = 212;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.content.pm.IPackageManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IPackageManager)) {
                return (android.content.pm.IPackageManager) queryLocalInterface;
            }
            return new android.content.pm.IPackageManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "checkPackageStartable";
                case 2:
                    return "isPackageAvailable";
                case 3:
                    return "getPackageInfo";
                case 4:
                    return "getPackageInfoVersioned";
                case 5:
                    return "getPackageUid";
                case 6:
                    return "getPackageGids";
                case 7:
                    return "currentToCanonicalPackageNames";
                case 8:
                    return "canonicalToCurrentPackageNames";
                case 9:
                    return "getApplicationInfo";
                case 10:
                    return "getTargetSdkVersion";
                case 11:
                    return "getActivityInfo";
                case 12:
                    return "activitySupportsIntentAsUser";
                case 13:
                    return "getReceiverInfo";
                case 14:
                    return "getServiceInfo";
                case 15:
                    return "getProviderInfo";
                case 16:
                    return "isProtectedBroadcast";
                case 17:
                    return "checkSignatures";
                case 18:
                    return "checkUidSignatures";
                case 19:
                    return "getAllPackages";
                case 20:
                    return "getPackagesForUid";
                case 21:
                    return "getNameForUid";
                case 22:
                    return "getNamesForUids";
                case 23:
                    return "getUidForSharedUser";
                case 24:
                    return "getFlagsForUid";
                case 25:
                    return "getPrivateFlagsForUid";
                case 26:
                    return "isUidPrivileged";
                case 27:
                    return "resolveIntent";
                case 28:
                    return "findPersistentPreferredActivity";
                case 29:
                    return "canForwardTo";
                case 30:
                    return "queryIntentActivities";
                case 31:
                    return "queryIntentActivityOptions";
                case 32:
                    return "queryIntentReceivers";
                case 33:
                    return "resolveService";
                case 34:
                    return "queryIntentServices";
                case 35:
                    return "queryIntentContentProviders";
                case 36:
                    return "getInstalledPackages";
                case 37:
                    return "getAppMetadataFd";
                case 38:
                    return "getPackagesHoldingPermissions";
                case 39:
                    return "getInstalledApplications";
                case 40:
                    return "getPersistentApplications";
                case 41:
                    return "resolveContentProvider";
                case 42:
                    return "querySyncProviders";
                case 43:
                    return "queryContentProviders";
                case 44:
                    return "getInstrumentationInfoAsUser";
                case 45:
                    return "queryInstrumentationAsUser";
                case 46:
                    return "finishPackageInstall";
                case 47:
                    return "setInstallerPackageName";
                case 48:
                    return "relinquishUpdateOwnership";
                case 49:
                    return "setApplicationCategoryHint";
                case 50:
                    return "deletePackageAsUser";
                case 51:
                    return "deletePackageVersioned";
                case 52:
                    return "deleteExistingPackageAsUser";
                case 53:
                    return "getInstallerPackageName";
                case 54:
                    return "getInstallSourceInfo";
                case 55:
                    return "resetApplicationPreferences";
                case 56:
                    return "getLastChosenActivity";
                case 57:
                    return "setLastChosenActivity";
                case 58:
                    return "addPreferredActivity";
                case 59:
                    return "replacePreferredActivity";
                case 60:
                    return "clearPackagePreferredActivities";
                case 61:
                    return "getPreferredActivities";
                case 62:
                    return "addPersistentPreferredActivity";
                case 63:
                    return "clearPackagePersistentPreferredActivities";
                case 64:
                    return "clearPersistentPreferredActivity";
                case 65:
                    return "addCrossProfileIntentFilter";
                case 66:
                    return "removeCrossProfileIntentFilter";
                case 67:
                    return "clearCrossProfileIntentFilters";
                case 68:
                    return "setDistractingPackageRestrictionsAsUser";
                case 69:
                    return "setPackagesSuspendedAsUser";
                case 70:
                    return "getUnsuspendablePackagesForUser";
                case 71:
                    return "isPackageSuspendedForUser";
                case 72:
                    return "isPackageQuarantinedForUser";
                case 73:
                    return "isPackageStoppedForUser";
                case 74:
                    return "getSuspendedPackageAppExtras";
                case 75:
                    return "getSuspendingPackage";
                case 76:
                    return "getPreferredActivityBackup";
                case 77:
                    return "restorePreferredActivities";
                case 78:
                    return "getDefaultAppsBackup";
                case 79:
                    return "restoreDefaultApps";
                case 80:
                    return "getDomainVerificationBackup";
                case 81:
                    return "restoreDomainVerification";
                case 82:
                    return "getHomeActivities";
                case 83:
                    return "setHomeActivity";
                case 84:
                    return "overrideLabelAndIcon";
                case 85:
                    return "restoreLabelAndIcon";
                case 86:
                    return "setComponentEnabledSetting";
                case 87:
                    return "setComponentEnabledSettings";
                case 88:
                    return "getComponentEnabledSetting";
                case 89:
                    return "setApplicationEnabledSetting";
                case 90:
                    return "getApplicationEnabledSetting";
                case 91:
                    return "logAppProcessStartIfNeeded";
                case 92:
                    return "flushPackageRestrictionsAsUser";
                case 93:
                    return "setPackageStoppedState";
                case 94:
                    return "freeStorageAndNotify";
                case 95:
                    return "freeStorage";
                case 96:
                    return "deleteApplicationCacheFiles";
                case 97:
                    return "deleteApplicationCacheFilesAsUser";
                case 98:
                    return "clearApplicationUserData";
                case 99:
                    return "clearApplicationProfileData";
                case 100:
                    return "getPackageSizeInfo";
                case 101:
                    return "getSystemSharedLibraryNames";
                case 102:
                    return "getSystemSharedLibraryNamesAndPaths";
                case 103:
                    return "getSystemAvailableFeatures";
                case 104:
                    return "hasSystemFeature";
                case 105:
                    return "getInitialNonStoppedSystemPackages";
                case 106:
                    return "enterSafeMode";
                case 107:
                    return "isSafeMode";
                case 108:
                    return "hasSystemUidErrors";
                case 109:
                    return "notifyPackageUse";
                case 110:
                    return "notifyDexLoad";
                case 111:
                    return "registerDexModule";
                case 112:
                    return "performDexOptMode";
                case 113:
                    return "performDexOptSecondary";
                case 114:
                    return "getMoveStatus";
                case 115:
                    return "registerMoveCallback";
                case 116:
                    return "unregisterMoveCallback";
                case 117:
                    return "movePackage";
                case 118:
                    return "movePrimaryStorage";
                case 119:
                    return "setInstallLocation";
                case 120:
                    return "getInstallLocation";
                case 121:
                    return "installExistingPackageAsUser";
                case 122:
                    return "verifyPendingInstall";
                case 123:
                    return "extendVerificationTimeout";
                case 124:
                    return "verifyIntentFilter";
                case 125:
                    return "getIntentVerificationStatus";
                case 126:
                    return "updateIntentVerificationStatus";
                case 127:
                    return "getIntentFilterVerifications";
                case 128:
                    return "getAllIntentFilters";
                case 129:
                    return "getVerifierDeviceIdentity";
                case 130:
                    return "isFirstBoot";
                case 131:
                    return "isDeviceUpgrading";
                case 132:
                    return "isStorageLow";
                case 133:
                    return "setApplicationHiddenSettingAsUser";
                case 134:
                    return "getApplicationHiddenSettingAsUser";
                case 135:
                    return "setSystemAppHiddenUntilInstalled";
                case 136:
                    return "setSystemAppInstallState";
                case 137:
                    return "getPackageInstaller";
                case 138:
                    return "setBlockUninstallForUser";
                case 139:
                    return "getBlockUninstallForUser";
                case 140:
                    return "getKeySetByAlias";
                case 141:
                    return "getSigningKeySet";
                case 142:
                    return "isPackageSignedByKeySet";
                case 143:
                    return "isPackageSignedByKeySetExactly";
                case 144:
                    return "getPermissionControllerPackageName";
                case 145:
                    return "getSdkSandboxPackageName";
                case 146:
                    return "getInstantApps";
                case 147:
                    return "getInstantAppCookie";
                case 148:
                    return "setInstantAppCookie";
                case 149:
                    return "getInstantAppIcon";
                case 150:
                    return "isInstantApp";
                case 151:
                    return "setRequiredForSystemUser";
                case 152:
                    return "setUpdateAvailable";
                case 153:
                    return "getServicesSystemSharedLibraryPackageName";
                case 154:
                    return "getSharedSystemSharedLibraryPackageName";
                case 155:
                    return "getChangedPackages";
                case 156:
                    return "isPackageDeviceAdminOnAnyUser";
                case 157:
                    return "getInstallReason";
                case 158:
                    return "getSharedLibraries";
                case 159:
                    return "getDeclaredSharedLibraries";
                case 160:
                    return "canRequestPackageInstalls";
                case 161:
                    return "deletePreloadsFileCache";
                case 162:
                    return "getInstantAppResolverComponent";
                case 163:
                    return "getInstantAppResolverSettingsComponent";
                case 164:
                    return "getInstantAppInstallerComponent";
                case 165:
                    return "getInstantAppAndroidId";
                case 166:
                    return "getArtManager";
                case 167:
                    return "setHarmfulAppWarning";
                case 168:
                    return "getHarmfulAppWarning";
                case 169:
                    return "hasSigningCertificate";
                case 170:
                    return "hasUidSigningCertificate";
                case 171:
                    return "getDefaultTextClassifierPackageName";
                case 172:
                    return "getSystemTextClassifierPackageName";
                case 173:
                    return "getAttentionServicePackageName";
                case 174:
                    return "getRotationResolverPackageName";
                case 175:
                    return "getWellbeingPackageName";
                case 176:
                    return "getAppPredictionServicePackageName";
                case 177:
                    return "getSystemCaptionsServicePackageName";
                case 178:
                    return "getSetupWizardPackageName";
                case 179:
                    return "getIncidentReportApproverPackageName";
                case 180:
                    return "isPackageStateProtected";
                case 181:
                    return "sendDeviceCustomizationReadyBroadcast";
                case 182:
                    return "getInstalledModules";
                case 183:
                    return "getModuleInfo";
                case 184:
                    return "getRuntimePermissionsVersion";
                case 185:
                    return "setRuntimePermissionsVersion";
                case 186:
                    return "notifyPackagesReplacedReceived";
                case 187:
                    return "requestPackageChecksums";
                case 188:
                    return "getLaunchIntentSenderForPackage";
                case 189:
                    return "getAppOpPermissionPackages";
                case 190:
                    return "getPermissionGroupInfo";
                case 191:
                    return "addPermission";
                case 192:
                    return "addPermissionAsync";
                case 193:
                    return "removePermission";
                case 194:
                    return "checkPermission";
                case 195:
                    return "grantRuntimePermission";
                case 196:
                    return "checkUidPermission";
                case 197:
                    return "setMimeGroup";
                case 198:
                    return "getSplashScreenTheme";
                case 199:
                    return "setSplashScreenTheme";
                case 200:
                    return "getUserMinAspectRatio";
                case 201:
                    return "setUserMinAspectRatio";
                case 202:
                    return "getMimeGroup";
                case 203:
                    return "isAutoRevokeWhitelisted";
                case 204:
                    return "makeProviderVisible";
                case 205:
                    return "makeUidVisible";
                case 206:
                    return "getHoldLockToken";
                case 207:
                    return "holdLock";
                case 208:
                    return "getPropertyAsUser";
                case 209:
                    return "queryProperty";
                case 210:
                    return "setKeepUninstalledPackages";
                case 211:
                    return "canPackageQuery";
                case 212:
                    return "waitForHandler";
                case 213:
                    return "registerPackageMonitorCallback";
                case 214:
                    return "unregisterPackageMonitorCallback";
                case 215:
                    return "getArchivedPackage";
                case 216:
                    return "getArchivedAppIcon";
                case 217:
                    return "isAppArchivable";
                case 218:
                    return "getAppMetadataSource";
                case 219:
                    return "getDomainVerificationAgent";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, final android.os.Parcel parcel, final android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    checkPackageStartable(readString, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageAvailable = isPackageAvailable(readString2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageAvailable);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.PackageInfo packageInfo = getPackageInfo(readString3, readLong, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packageInfo, 1);
                    return true;
                case 4:
                    android.content.pm.VersionedPackage versionedPackage = (android.content.pm.VersionedPackage) parcel.readTypedObject(android.content.pm.VersionedPackage.CREATOR);
                    long readLong2 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.PackageInfo packageInfoVersioned = getPackageInfoVersioned(versionedPackage, readLong2, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packageInfoVersioned, 1);
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    long readLong3 = parcel.readLong();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int packageUid = getPackageUid(readString4, readLong3, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageUid);
                    return true;
                case 6:
                    java.lang.String readString5 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] packageGids = getPackageGids(readString5, readLong4, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(packageGids);
                    return true;
                case 7:
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] currentToCanonicalPackageNames = currentToCanonicalPackageNames(createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(currentToCanonicalPackageNames);
                    return true;
                case 8:
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] canonicalToCurrentPackageNames = canonicalToCurrentPackageNames(createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(canonicalToCurrentPackageNames);
                    return true;
                case 9:
                    java.lang.String readString6 = parcel.readString();
                    long readLong5 = parcel.readLong();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ApplicationInfo applicationInfo = getApplicationInfo(readString6, readLong5, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationInfo, 1);
                    return true;
                case 10:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int targetSdkVersion = getTargetSdkVersion(readString7);
                    parcel2.writeNoException();
                    parcel2.writeInt(targetSdkVersion);
                    return true;
                case 11:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    long readLong6 = parcel.readLong();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ActivityInfo activityInfo = getActivityInfo(componentName, readLong6, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activityInfo, 1);
                    return true;
                case 12:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString8 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean activitySupportsIntentAsUser = activitySupportsIntentAsUser(componentName2, intent, readString8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activitySupportsIntentAsUser);
                    return true;
                case 13:
                    android.content.ComponentName componentName3 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    long readLong7 = parcel.readLong();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ActivityInfo receiverInfo = getReceiverInfo(componentName3, readLong7, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(receiverInfo, 1);
                    return true;
                case 14:
                    android.content.ComponentName componentName4 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    long readLong8 = parcel.readLong();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ServiceInfo serviceInfo = getServiceInfo(componentName4, readLong8, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfo, 1);
                    return true;
                case 15:
                    android.content.ComponentName componentName5 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    long readLong9 = parcel.readLong();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ProviderInfo providerInfo = getProviderInfo(componentName5, readLong9, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(providerInfo, 1);
                    return true;
                case 16:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProtectedBroadcast = isProtectedBroadcast(readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProtectedBroadcast);
                    return true;
                case 17:
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkSignatures = checkSignatures(readString10, readString11, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkSignatures);
                    return true;
                case 18:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkUidSignatures = checkUidSignatures(readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkUidSignatures);
                    return true;
                case 19:
                    java.util.List<java.lang.String> allPackages = getAllPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allPackages);
                    return true;
                case 20:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] packagesForUid = getPackagesForUid(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(packagesForUid);
                    return true;
                case 21:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String nameForUid = getNameForUid(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeString(nameForUid);
                    return true;
                case 22:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] namesForUids = getNamesForUids(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(namesForUids);
                    return true;
                case 23:
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int uidForSharedUser = getUidForSharedUser(readString12);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidForSharedUser);
                    return true;
                case 24:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int flagsForUid = getFlagsForUid(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeInt(flagsForUid);
                    return true;
                case 25:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int privateFlagsForUid = getPrivateFlagsForUid(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(privateFlagsForUid);
                    return true;
                case 26:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUidPrivileged = isUidPrivileged(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUidPrivileged);
                    return true;
                case 27:
                    android.content.Intent intent2 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString13 = parcel.readString();
                    long readLong10 = parcel.readLong();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ResolveInfo resolveIntent = resolveIntent(intent2, readString13, readLong10, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveIntent, 1);
                    return true;
                case 28:
                    android.content.Intent intent3 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ResolveInfo findPersistentPreferredActivity = findPersistentPreferredActivity(intent3, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(findPersistentPreferredActivity, 1);
                    return true;
                case 29:
                    android.content.Intent intent4 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString14 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canForwardTo = canForwardTo(intent4, readString14, readInt23, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canForwardTo);
                    return true;
                case 30:
                    android.content.Intent intent5 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString15 = parcel.readString();
                    long readLong11 = parcel.readLong();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryIntentActivities = queryIntentActivities(intent5, readString15, readLong11, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentActivities, 1);
                    return true;
                case 31:
                    android.content.ComponentName componentName6 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.content.Intent[] intentArr = (android.content.Intent[]) parcel.createTypedArray(android.content.Intent.CREATOR);
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    android.content.Intent intent6 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString16 = parcel.readString();
                    long readLong12 = parcel.readLong();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryIntentActivityOptions = queryIntentActivityOptions(componentName6, intentArr, createStringArray3, intent6, readString16, readLong12, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentActivityOptions, 1);
                    return true;
                case 32:
                    android.content.Intent intent7 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString17 = parcel.readString();
                    long readLong13 = parcel.readLong();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryIntentReceivers = queryIntentReceivers(intent7, readString17, readLong13, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentReceivers, 1);
                    return true;
                case 33:
                    android.content.Intent intent8 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString18 = parcel.readString();
                    long readLong14 = parcel.readLong();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ResolveInfo resolveService = resolveService(intent8, readString18, readLong14, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveService, 1);
                    return true;
                case 34:
                    android.content.Intent intent9 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString19 = parcel.readString();
                    long readLong15 = parcel.readLong();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryIntentServices = queryIntentServices(intent9, readString19, readLong15, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentServices, 1);
                    return true;
                case 35:
                    android.content.Intent intent10 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString20 = parcel.readString();
                    long readLong16 = parcel.readLong();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryIntentContentProviders = queryIntentContentProviders(intent10, readString20, readLong16, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentContentProviders, 1);
                    return true;
                case 36:
                    long readLong17 = parcel.readLong();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice installedPackages = getInstalledPackages(readLong17, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedPackages, 1);
                    return true;
                case 37:
                    java.lang.String readString21 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor appMetadataFd = getAppMetadataFd(readString21, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appMetadataFd, 1);
                    return true;
                case 38:
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    long readLong18 = parcel.readLong();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice packagesHoldingPermissions = getPackagesHoldingPermissions(createStringArray4, readLong18, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packagesHoldingPermissions, 1);
                    return true;
                case 39:
                    long readLong19 = parcel.readLong();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice installedApplications = getInstalledApplications(readLong19, readInt34);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedApplications, 1);
                    return true;
                case 40:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice persistentApplications = getPersistentApplications(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(persistentApplications, 1);
                    return true;
                case 41:
                    java.lang.String readString22 = parcel.readString();
                    long readLong20 = parcel.readLong();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ProviderInfo resolveContentProvider = resolveContentProvider(readString22, readLong20, readInt36);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveContentProvider, 1);
                    return true;
                case 42:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.content.pm.ProviderInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    querySyncProviders(createStringArrayList, createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeStringList(createStringArrayList);
                    parcel2.writeTypedList(createTypedArrayList, 1);
                    return true;
                case 43:
                    java.lang.String readString23 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    long readLong21 = parcel.readLong();
                    java.lang.String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryContentProviders = queryContentProviders(readString23, readInt37, readLong21, readString24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryContentProviders, 1);
                    return true;
                case 44:
                    android.content.ComponentName componentName7 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.InstrumentationInfo instrumentationInfoAsUser = getInstrumentationInfoAsUser(componentName7, readInt38, readInt39);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instrumentationInfoAsUser, 1);
                    return true;
                case 45:
                    java.lang.String readString25 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryInstrumentationAsUser = queryInstrumentationAsUser(readString25, readInt40, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryInstrumentationAsUser, 1);
                    return true;
                case 46:
                    int readInt42 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishPackageInstall(readInt42, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    java.lang.String readString26 = parcel.readString();
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setInstallerPackageName(readString26, readString27);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    java.lang.String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    relinquishUpdateOwnership(readString28);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    java.lang.String readString29 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    java.lang.String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setApplicationCategoryHint(readString29, readInt43, readString30);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    java.lang.String readString31 = parcel.readString();
                    int readInt44 = parcel.readInt();
                    android.content.pm.IPackageDeleteObserver asInterface = android.content.pm.IPackageDeleteObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deletePackageAsUser(readString31, readInt44, asInterface, readInt45, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    android.content.pm.VersionedPackage versionedPackage2 = (android.content.pm.VersionedPackage) parcel.readTypedObject(android.content.pm.VersionedPackage.CREATOR);
                    android.content.pm.IPackageDeleteObserver2 asInterface2 = android.content.pm.IPackageDeleteObserver2.Stub.asInterface(parcel.readStrongBinder());
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deletePackageVersioned(versionedPackage2, asInterface2, readInt47, readInt48);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    android.content.pm.VersionedPackage versionedPackage3 = (android.content.pm.VersionedPackage) parcel.readTypedObject(android.content.pm.VersionedPackage.CREATOR);
                    android.content.pm.IPackageDeleteObserver2 asInterface3 = android.content.pm.IPackageDeleteObserver2.Stub.asInterface(parcel.readStrongBinder());
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteExistingPackageAsUser(versionedPackage3, asInterface3, readInt49);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    java.lang.String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String installerPackageName = getInstallerPackageName(readString32);
                    parcel2.writeNoException();
                    parcel2.writeString(installerPackageName);
                    return true;
                case 54:
                    java.lang.String readString33 = parcel.readString();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.InstallSourceInfo installSourceInfo = getInstallSourceInfo(readString33, readInt50);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installSourceInfo, 1);
                    return true;
                case 55:
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetApplicationPreferences(readInt51);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    android.content.Intent intent11 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString34 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ResolveInfo lastChosenActivity = getLastChosenActivity(intent11, readString34, readInt52);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastChosenActivity, 1);
                    return true;
                case 57:
                    android.content.Intent intent12 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString35 = parcel.readString();
                    int readInt53 = parcel.readInt();
                    android.content.IntentFilter intentFilter = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    int readInt54 = parcel.readInt();
                    android.content.ComponentName componentName8 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLastChosenActivity(intent12, readString35, readInt53, intentFilter, readInt54, componentName8);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    android.content.IntentFilter intentFilter2 = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    int readInt55 = parcel.readInt();
                    android.content.ComponentName[] componentNameArr = (android.content.ComponentName[]) parcel.createTypedArray(android.content.ComponentName.CREATOR);
                    android.content.ComponentName componentName9 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt56 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    addPreferredActivity(intentFilter2, readInt55, componentNameArr, componentName9, readInt56, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    android.content.IntentFilter intentFilter3 = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    int readInt57 = parcel.readInt();
                    android.content.ComponentName[] componentNameArr2 = (android.content.ComponentName[]) parcel.createTypedArray(android.content.ComponentName.CREATOR);
                    android.content.ComponentName componentName10 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    replacePreferredActivity(intentFilter3, readInt57, componentNameArr2, componentName10, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    java.lang.String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearPackagePreferredActivities(readString36);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    java.util.ArrayList arrayList2 = new java.util.ArrayList();
                    java.lang.String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int preferredActivities = getPreferredActivities(arrayList, arrayList2, readString37);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredActivities);
                    parcel2.writeTypedList(arrayList, 1);
                    parcel2.writeTypedList(arrayList2, 1);
                    return true;
                case 62:
                    android.content.IntentFilter intentFilter4 = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    android.content.ComponentName componentName11 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPersistentPreferredActivity(intentFilter4, componentName11, readInt59);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    java.lang.String readString38 = parcel.readString();
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPackagePersistentPreferredActivities(readString38, readInt60);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    android.content.IntentFilter intentFilter5 = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPersistentPreferredActivity(intentFilter5, readInt61);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    android.content.IntentFilter intentFilter6 = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    java.lang.String readString39 = parcel.readString();
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addCrossProfileIntentFilter(intentFilter6, readString39, readInt62, readInt63, readInt64);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    android.content.IntentFilter intentFilter7 = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    java.lang.String readString40 = parcel.readString();
                    int readInt65 = parcel.readInt();
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeCrossProfileIntentFilter = removeCrossProfileIntentFilter(intentFilter7, readString40, readInt65, readInt66, readInt67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeCrossProfileIntentFilter);
                    return true;
                case 67:
                    int readInt68 = parcel.readInt();
                    java.lang.String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearCrossProfileIntentFilters(readInt68, readString41);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    java.lang.String[] createStringArray5 = parcel.createStringArray();
                    int readInt69 = parcel.readInt();
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] distractingPackageRestrictionsAsUser = setDistractingPackageRestrictionsAsUser(createStringArray5, readInt69, readInt70);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(distractingPackageRestrictionsAsUser);
                    return true;
                case 69:
                    java.lang.String[] createStringArray6 = parcel.createStringArray();
                    boolean readBoolean3 = parcel.readBoolean();
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    android.os.PersistableBundle persistableBundle2 = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    android.content.pm.SuspendDialogInfo suspendDialogInfo = (android.content.pm.SuspendDialogInfo) parcel.readTypedObject(android.content.pm.SuspendDialogInfo.CREATOR);
                    int readInt71 = parcel.readInt();
                    java.lang.String readString42 = parcel.readString();
                    int readInt72 = parcel.readInt();
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] packagesSuspendedAsUser = setPackagesSuspendedAsUser(createStringArray6, readBoolean3, persistableBundle, persistableBundle2, suspendDialogInfo, readInt71, readString42, readInt72, readInt73);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(packagesSuspendedAsUser);
                    return true;
                case 70:
                    java.lang.String[] createStringArray7 = parcel.createStringArray();
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] unsuspendablePackagesForUser = getUnsuspendablePackagesForUser(createStringArray7, readInt74);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(unsuspendablePackagesForUser);
                    return true;
                case 71:
                    java.lang.String readString43 = parcel.readString();
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageSuspendedForUser = isPackageSuspendedForUser(readString43, readInt75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageSuspendedForUser);
                    return true;
                case 72:
                    java.lang.String readString44 = parcel.readString();
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageQuarantinedForUser = isPackageQuarantinedForUser(readString44, readInt76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageQuarantinedForUser);
                    return true;
                case 73:
                    java.lang.String readString45 = parcel.readString();
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageStoppedForUser = isPackageStoppedForUser(readString45, readInt77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageStoppedForUser);
                    return true;
                case 74:
                    java.lang.String readString46 = parcel.readString();
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle suspendedPackageAppExtras = getSuspendedPackageAppExtras(readString46, readInt78);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(suspendedPackageAppExtras, 1);
                    return true;
                case 75:
                    java.lang.String readString47 = parcel.readString();
                    int readInt79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String suspendingPackage = getSuspendingPackage(readString47, readInt79);
                    parcel2.writeNoException();
                    parcel2.writeString(suspendingPackage);
                    return true;
                case 76:
                    int readInt80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] preferredActivityBackup = getPreferredActivityBackup(readInt80);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(preferredActivityBackup);
                    return true;
                case 77:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restorePreferredActivities(createByteArray, readInt81);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    int readInt82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] defaultAppsBackup = getDefaultAppsBackup(readInt82);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(defaultAppsBackup);
                    return true;
                case 79:
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreDefaultApps(createByteArray2, readInt83);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    int readInt84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] domainVerificationBackup = getDomainVerificationBackup(readInt84);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(domainVerificationBackup);
                    return true;
                case 81:
                    byte[] createByteArray3 = parcel.createByteArray();
                    int readInt85 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreDomainVerification(createByteArray3, readInt85);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    java.util.ArrayList arrayList3 = new java.util.ArrayList();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName homeActivities = getHomeActivities(arrayList3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(homeActivities, 1);
                    parcel2.writeTypedList(arrayList3, 1);
                    return true;
                case 83:
                    android.content.ComponentName componentName12 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHomeActivity(componentName12, readInt86);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    android.content.ComponentName componentName13 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString48 = parcel.readString();
                    int readInt87 = parcel.readInt();
                    int readInt88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overrideLabelAndIcon(componentName13, readString48, readInt87, readInt88);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    android.content.ComponentName componentName14 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreLabelAndIcon(componentName14, readInt89);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    android.content.ComponentName componentName15 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt90 = parcel.readInt();
                    int readInt91 = parcel.readInt();
                    int readInt92 = parcel.readInt();
                    java.lang.String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setComponentEnabledSetting(componentName15, readInt90, readInt91, readInt92, readString49);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.content.pm.PackageManager.ComponentEnabledSetting.CREATOR);
                    int readInt93 = parcel.readInt();
                    java.lang.String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setComponentEnabledSettings(createTypedArrayList2, readInt93, readString50);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    android.content.ComponentName componentName16 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int componentEnabledSetting = getComponentEnabledSetting(componentName16, readInt94);
                    parcel2.writeNoException();
                    parcel2.writeInt(componentEnabledSetting);
                    return true;
                case 89:
                    java.lang.String readString51 = parcel.readString();
                    int readInt95 = parcel.readInt();
                    int readInt96 = parcel.readInt();
                    int readInt97 = parcel.readInt();
                    java.lang.String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setApplicationEnabledSetting(readString51, readInt95, readInt96, readInt97, readString52);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    java.lang.String readString53 = parcel.readString();
                    int readInt98 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int applicationEnabledSetting = getApplicationEnabledSetting(readString53, readInt98);
                    parcel2.writeNoException();
                    parcel2.writeInt(applicationEnabledSetting);
                    return true;
                case 91:
                    java.lang.String readString54 = parcel.readString();
                    java.lang.String readString55 = parcel.readString();
                    int readInt99 = parcel.readInt();
                    java.lang.String readString56 = parcel.readString();
                    java.lang.String readString57 = parcel.readString();
                    int readInt100 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logAppProcessStartIfNeeded(readString54, readString55, readInt99, readString56, readString57, readInt100);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int readInt101 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    flushPackageRestrictionsAsUser(readInt101);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    java.lang.String readString58 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt102 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPackageStoppedState(readString58, readBoolean4, readInt102);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    java.lang.String readString59 = parcel.readString();
                    long readLong22 = parcel.readLong();
                    int readInt103 = parcel.readInt();
                    android.content.pm.IPackageDataObserver asInterface4 = android.content.pm.IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    freeStorageAndNotify(readString59, readLong22, readInt103, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    java.lang.String readString60 = parcel.readString();
                    long readLong23 = parcel.readLong();
                    int readInt104 = parcel.readInt();
                    android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    freeStorage(readString60, readLong23, readInt104, intentSender);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    java.lang.String readString61 = parcel.readString();
                    android.content.pm.IPackageDataObserver asInterface5 = android.content.pm.IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteApplicationCacheFiles(readString61, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    java.lang.String readString62 = parcel.readString();
                    int readInt105 = parcel.readInt();
                    android.content.pm.IPackageDataObserver asInterface6 = android.content.pm.IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteApplicationCacheFilesAsUser(readString62, readInt105, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    java.lang.String readString63 = parcel.readString();
                    android.content.pm.IPackageDataObserver asInterface7 = android.content.pm.IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearApplicationUserData(readString63, asInterface7, readInt106);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    java.lang.String readString64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearApplicationProfileData(readString64);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    java.lang.String readString65 = parcel.readString();
                    int readInt107 = parcel.readInt();
                    android.content.pm.IPackageStatsObserver asInterface8 = android.content.pm.IPackageStatsObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getPackageSizeInfo(readString65, readInt107, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    java.lang.String[] systemSharedLibraryNames = getSystemSharedLibraryNames();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(systemSharedLibraryNames);
                    return true;
                case 102:
                    java.util.Map<java.lang.String, java.lang.String> systemSharedLibraryNamesAndPaths = getSystemSharedLibraryNamesAndPaths();
                    parcel2.writeNoException();
                    if (systemSharedLibraryNamesAndPaths == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(systemSharedLibraryNamesAndPaths.size());
                        systemSharedLibraryNamesAndPaths.forEach(new java.util.function.BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.content.pm.IPackageManager.Stub.lambda$onTransact$0(android.os.Parcel.this, (java.lang.String) obj, (java.lang.String) obj2);
                            }
                        });
                    }
                    return true;
                case 103:
                    android.content.pm.ParceledListSlice systemAvailableFeatures = getSystemAvailableFeatures();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemAvailableFeatures, 1);
                    return true;
                case 104:
                    java.lang.String readString66 = parcel.readString();
                    int readInt108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasSystemFeature = hasSystemFeature(readString66, readInt108);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSystemFeature);
                    return true;
                case 105:
                    java.util.List<java.lang.String> initialNonStoppedSystemPackages = getInitialNonStoppedSystemPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(initialNonStoppedSystemPackages);
                    return true;
                case 106:
                    enterSafeMode();
                    parcel2.writeNoException();
                    return true;
                case 107:
                    boolean isSafeMode = isSafeMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSafeMode);
                    return true;
                case 108:
                    boolean hasSystemUidErrors = hasSystemUidErrors();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSystemUidErrors);
                    return true;
                case 109:
                    java.lang.String readString67 = parcel.readString();
                    int readInt109 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPackageUse(readString67, readInt109);
                    return true;
                case 110:
                    java.lang.String readString68 = parcel.readString();
                    int readInt110 = parcel.readInt();
                    final java.util.HashMap hashMap = readInt110 < 0 ? null : new java.util.HashMap();
                    java.util.stream.IntStream.range(0, readInt110).forEach(new java.util.function.IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), android.os.Parcel.this.readString());
                        }
                    });
                    java.lang.String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyDexLoad(readString68, hashMap, readString69);
                    return true;
                case 111:
                    java.lang.String readString70 = parcel.readString();
                    java.lang.String readString71 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    android.content.pm.IDexModuleRegisterCallback asInterface9 = android.content.pm.IDexModuleRegisterCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDexModule(readString70, readString71, readBoolean5, asInterface9);
                    return true;
                case 112:
                    java.lang.String readString72 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    java.lang.String readString73 = parcel.readString();
                    boolean readBoolean7 = parcel.readBoolean();
                    boolean readBoolean8 = parcel.readBoolean();
                    java.lang.String readString74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean performDexOptMode = performDexOptMode(readString72, readBoolean6, readString73, readBoolean7, readBoolean8, readString74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(performDexOptMode);
                    return true;
                case 113:
                    java.lang.String readString75 = parcel.readString();
                    java.lang.String readString76 = parcel.readString();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean performDexOptSecondary = performDexOptSecondary(readString75, readString76, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(performDexOptSecondary);
                    return true;
                case 114:
                    int readInt111 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int moveStatus = getMoveStatus(readInt111);
                    parcel2.writeNoException();
                    parcel2.writeInt(moveStatus);
                    return true;
                case 115:
                    android.content.pm.IPackageMoveObserver asInterface10 = android.content.pm.IPackageMoveObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerMoveCallback(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    android.content.pm.IPackageMoveObserver asInterface11 = android.content.pm.IPackageMoveObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterMoveCallback(asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 117:
                    java.lang.String readString77 = parcel.readString();
                    java.lang.String readString78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int movePackage = movePackage(readString77, readString78);
                    parcel2.writeNoException();
                    parcel2.writeInt(movePackage);
                    return true;
                case 118:
                    java.lang.String readString79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int movePrimaryStorage = movePrimaryStorage(readString79);
                    parcel2.writeNoException();
                    parcel2.writeInt(movePrimaryStorage);
                    return true;
                case 119:
                    int readInt112 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean installLocation = setInstallLocation(readInt112);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(installLocation);
                    return true;
                case 120:
                    int installLocation2 = getInstallLocation();
                    parcel2.writeNoException();
                    parcel2.writeInt(installLocation2);
                    return true;
                case 121:
                    java.lang.String readString80 = parcel.readString();
                    int readInt113 = parcel.readInt();
                    int readInt114 = parcel.readInt();
                    int readInt115 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int installExistingPackageAsUser = installExistingPackageAsUser(readString80, readInt113, readInt114, readInt115, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeInt(installExistingPackageAsUser);
                    return true;
                case 122:
                    int readInt116 = parcel.readInt();
                    int readInt117 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    verifyPendingInstall(readInt116, readInt117);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    int readInt118 = parcel.readInt();
                    int readInt119 = parcel.readInt();
                    long readLong24 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    extendVerificationTimeout(readInt118, readInt119, readLong24);
                    parcel2.writeNoException();
                    return true;
                case 124:
                    int readInt120 = parcel.readInt();
                    int readInt121 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    verifyIntentFilter(readInt120, readInt121, createStringArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 125:
                    java.lang.String readString81 = parcel.readString();
                    int readInt122 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int intentVerificationStatus = getIntentVerificationStatus(readString81, readInt122);
                    parcel2.writeNoException();
                    parcel2.writeInt(intentVerificationStatus);
                    return true;
                case 126:
                    java.lang.String readString82 = parcel.readString();
                    int readInt123 = parcel.readInt();
                    int readInt124 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean updateIntentVerificationStatus = updateIntentVerificationStatus(readString82, readInt123, readInt124);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateIntentVerificationStatus);
                    return true;
                case 127:
                    java.lang.String readString83 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice intentFilterVerifications = getIntentFilterVerifications(readString83);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentFilterVerifications, 1);
                    return true;
                case 128:
                    java.lang.String readString84 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice allIntentFilters = getAllIntentFilters(readString84);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allIntentFilters, 1);
                    return true;
                case 129:
                    android.content.pm.VerifierDeviceIdentity verifierDeviceIdentity = getVerifierDeviceIdentity();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifierDeviceIdentity, 1);
                    return true;
                case 130:
                    boolean isFirstBoot = isFirstBoot();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFirstBoot);
                    return true;
                case 131:
                    boolean isDeviceUpgrading = isDeviceUpgrading();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceUpgrading);
                    return true;
                case 132:
                    boolean isStorageLow = isStorageLow();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStorageLow);
                    return true;
                case 133:
                    java.lang.String readString85 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    int readInt125 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationHiddenSettingAsUser = setApplicationHiddenSettingAsUser(readString85, readBoolean10, readInt125);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationHiddenSettingAsUser);
                    return true;
                case 134:
                    java.lang.String readString86 = parcel.readString();
                    int readInt126 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean applicationHiddenSettingAsUser2 = getApplicationHiddenSettingAsUser(readString86, readInt126);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationHiddenSettingAsUser2);
                    return true;
                case 135:
                    java.lang.String readString87 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSystemAppHiddenUntilInstalled(readString87, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 136:
                    java.lang.String readString88 = parcel.readString();
                    boolean readBoolean12 = parcel.readBoolean();
                    int readInt127 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean systemAppInstallState = setSystemAppInstallState(readString88, readBoolean12, readInt127);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(systemAppInstallState);
                    return true;
                case 137:
                    android.content.pm.IPackageInstaller packageInstaller = getPackageInstaller();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(packageInstaller);
                    return true;
                case 138:
                    java.lang.String readString89 = parcel.readString();
                    boolean readBoolean13 = parcel.readBoolean();
                    int readInt128 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean blockUninstallForUser = setBlockUninstallForUser(readString89, readBoolean13, readInt128);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(blockUninstallForUser);
                    return true;
                case 139:
                    java.lang.String readString90 = parcel.readString();
                    int readInt129 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean blockUninstallForUser2 = getBlockUninstallForUser(readString90, readInt129);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(blockUninstallForUser2);
                    return true;
                case 140:
                    java.lang.String readString91 = parcel.readString();
                    java.lang.String readString92 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.KeySet keySetByAlias = getKeySetByAlias(readString91, readString92);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keySetByAlias, 1);
                    return true;
                case 141:
                    java.lang.String readString93 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.KeySet signingKeySet = getSigningKeySet(readString93);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(signingKeySet, 1);
                    return true;
                case 142:
                    java.lang.String readString94 = parcel.readString();
                    android.content.pm.KeySet keySet = (android.content.pm.KeySet) parcel.readTypedObject(android.content.pm.KeySet.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPackageSignedByKeySet = isPackageSignedByKeySet(readString94, keySet);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageSignedByKeySet);
                    return true;
                case 143:
                    java.lang.String readString95 = parcel.readString();
                    android.content.pm.KeySet keySet2 = (android.content.pm.KeySet) parcel.readTypedObject(android.content.pm.KeySet.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPackageSignedByKeySetExactly = isPackageSignedByKeySetExactly(readString95, keySet2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageSignedByKeySetExactly);
                    return true;
                case 144:
                    java.lang.String permissionControllerPackageName = getPermissionControllerPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(permissionControllerPackageName);
                    return true;
                case 145:
                    java.lang.String sdkSandboxPackageName = getSdkSandboxPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(sdkSandboxPackageName);
                    return true;
                case 146:
                    int readInt130 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice instantApps = getInstantApps(readInt130);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantApps, 1);
                    return true;
                case 147:
                    java.lang.String readString96 = parcel.readString();
                    int readInt131 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] instantAppCookie = getInstantAppCookie(readString96, readInt131);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(instantAppCookie);
                    return true;
                case 148:
                    java.lang.String readString97 = parcel.readString();
                    byte[] createByteArray4 = parcel.createByteArray();
                    int readInt132 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean instantAppCookie2 = setInstantAppCookie(readString97, createByteArray4, readInt132);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(instantAppCookie2);
                    return true;
                case 149:
                    java.lang.String readString98 = parcel.readString();
                    int readInt133 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.graphics.Bitmap instantAppIcon = getInstantAppIcon(readString98, readInt133);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantAppIcon, 1);
                    return true;
                case 150:
                    java.lang.String readString99 = parcel.readString();
                    int readInt134 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isInstantApp = isInstantApp(readString99, readInt134);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInstantApp);
                    return true;
                case 151:
                    java.lang.String readString100 = parcel.readString();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requiredForSystemUser = setRequiredForSystemUser(readString100, readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requiredForSystemUser);
                    return true;
                case 152:
                    java.lang.String readString101 = parcel.readString();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUpdateAvailable(readString101, readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 153:
                    java.lang.String servicesSystemSharedLibraryPackageName = getServicesSystemSharedLibraryPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(servicesSystemSharedLibraryPackageName);
                    return true;
                case 154:
                    java.lang.String sharedSystemSharedLibraryPackageName = getSharedSystemSharedLibraryPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(sharedSystemSharedLibraryPackageName);
                    return true;
                case 155:
                    int readInt135 = parcel.readInt();
                    int readInt136 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ChangedPackages changedPackages = getChangedPackages(readInt135, readInt136);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(changedPackages, 1);
                    return true;
                case 156:
                    java.lang.String readString102 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPackageDeviceAdminOnAnyUser = isPackageDeviceAdminOnAnyUser(readString102);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageDeviceAdminOnAnyUser);
                    return true;
                case 157:
                    java.lang.String readString103 = parcel.readString();
                    int readInt137 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int installReason = getInstallReason(readString103, readInt137);
                    parcel2.writeNoException();
                    parcel2.writeInt(installReason);
                    return true;
                case 158:
                    java.lang.String readString104 = parcel.readString();
                    long readLong25 = parcel.readLong();
                    int readInt138 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice sharedLibraries = getSharedLibraries(readString104, readLong25, readInt138);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sharedLibraries, 1);
                    return true;
                case 159:
                    java.lang.String readString105 = parcel.readString();
                    long readLong26 = parcel.readLong();
                    int readInt139 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice declaredSharedLibraries = getDeclaredSharedLibraries(readString105, readLong26, readInt139);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(declaredSharedLibraries, 1);
                    return true;
                case 160:
                    java.lang.String readString106 = parcel.readString();
                    int readInt140 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canRequestPackageInstalls = canRequestPackageInstalls(readString106, readInt140);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canRequestPackageInstalls);
                    return true;
                case 161:
                    deletePreloadsFileCache();
                    parcel2.writeNoException();
                    return true;
                case 162:
                    android.content.ComponentName instantAppResolverComponent = getInstantAppResolverComponent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantAppResolverComponent, 1);
                    return true;
                case 163:
                    android.content.ComponentName instantAppResolverSettingsComponent = getInstantAppResolverSettingsComponent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantAppResolverSettingsComponent, 1);
                    return true;
                case 164:
                    android.content.ComponentName instantAppInstallerComponent = getInstantAppInstallerComponent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(instantAppInstallerComponent, 1);
                    return true;
                case 165:
                    java.lang.String readString107 = parcel.readString();
                    int readInt141 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String instantAppAndroidId = getInstantAppAndroidId(readString107, readInt141);
                    parcel2.writeNoException();
                    parcel2.writeString(instantAppAndroidId);
                    return true;
                case 166:
                    android.content.pm.dex.IArtManager artManager = getArtManager();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(artManager);
                    return true;
                case 167:
                    java.lang.String readString108 = parcel.readString();
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt142 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHarmfulAppWarning(readString108, charSequence, readInt142);
                    parcel2.writeNoException();
                    return true;
                case 168:
                    java.lang.String readString109 = parcel.readString();
                    int readInt143 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence harmfulAppWarning = getHarmfulAppWarning(readString109, readInt143);
                    parcel2.writeNoException();
                    if (harmfulAppWarning != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(harmfulAppWarning, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 169:
                    java.lang.String readString110 = parcel.readString();
                    byte[] createByteArray5 = parcel.createByteArray();
                    int readInt144 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasSigningCertificate = hasSigningCertificate(readString110, createByteArray5, readInt144);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSigningCertificate);
                    return true;
                case 170:
                    int readInt145 = parcel.readInt();
                    byte[] createByteArray6 = parcel.createByteArray();
                    int readInt146 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasUidSigningCertificate = hasUidSigningCertificate(readInt145, createByteArray6, readInt146);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUidSigningCertificate);
                    return true;
                case 171:
                    java.lang.String defaultTextClassifierPackageName = getDefaultTextClassifierPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(defaultTextClassifierPackageName);
                    return true;
                case 172:
                    java.lang.String systemTextClassifierPackageName = getSystemTextClassifierPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(systemTextClassifierPackageName);
                    return true;
                case 173:
                    java.lang.String attentionServicePackageName = getAttentionServicePackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(attentionServicePackageName);
                    return true;
                case 174:
                    java.lang.String rotationResolverPackageName = getRotationResolverPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(rotationResolverPackageName);
                    return true;
                case 175:
                    java.lang.String wellbeingPackageName = getWellbeingPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(wellbeingPackageName);
                    return true;
                case 176:
                    java.lang.String appPredictionServicePackageName = getAppPredictionServicePackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(appPredictionServicePackageName);
                    return true;
                case 177:
                    java.lang.String systemCaptionsServicePackageName = getSystemCaptionsServicePackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(systemCaptionsServicePackageName);
                    return true;
                case 178:
                    java.lang.String setupWizardPackageName = getSetupWizardPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(setupWizardPackageName);
                    return true;
                case 179:
                    java.lang.String incidentReportApproverPackageName = getIncidentReportApproverPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(incidentReportApproverPackageName);
                    return true;
                case 180:
                    java.lang.String readString111 = parcel.readString();
                    int readInt147 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageStateProtected = isPackageStateProtected(readString111, readInt147);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageStateProtected);
                    return true;
                case 181:
                    sendDeviceCustomizationReadyBroadcast();
                    parcel2.writeNoException();
                    return true;
                case 182:
                    int readInt148 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.content.pm.ModuleInfo> installedModules = getInstalledModules(readInt148);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(installedModules, 1);
                    return true;
                case 183:
                    java.lang.String readString112 = parcel.readString();
                    int readInt149 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ModuleInfo moduleInfo = getModuleInfo(readString112, readInt149);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(moduleInfo, 1);
                    return true;
                case 184:
                    int readInt150 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int runtimePermissionsVersion = getRuntimePermissionsVersion(readInt150);
                    parcel2.writeNoException();
                    parcel2.writeInt(runtimePermissionsVersion);
                    return true;
                case 185:
                    int readInt151 = parcel.readInt();
                    int readInt152 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRuntimePermissionsVersion(readInt151, readInt152);
                    parcel2.writeNoException();
                    return true;
                case 186:
                    java.lang.String[] createStringArray8 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    notifyPackagesReplacedReceived(createStringArray8);
                    parcel2.writeNoException();
                    return true;
                case 187:
                    java.lang.String readString113 = parcel.readString();
                    boolean readBoolean16 = parcel.readBoolean();
                    int readInt153 = parcel.readInt();
                    int readInt154 = parcel.readInt();
                    java.util.ArrayList readArrayList = parcel.readArrayList(getClass().getClassLoader());
                    android.content.pm.IOnChecksumsReadyListener asInterface12 = android.content.pm.IOnChecksumsReadyListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt155 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestPackageChecksums(readString113, readBoolean16, readInt153, readInt154, readArrayList, asInterface12, readInt155);
                    parcel2.writeNoException();
                    return true;
                case 188:
                    java.lang.String readString114 = parcel.readString();
                    java.lang.String readString115 = parcel.readString();
                    java.lang.String readString116 = parcel.readString();
                    int readInt156 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.IntentSender launchIntentSenderForPackage = getLaunchIntentSenderForPackage(readString114, readString115, readString116, readInt156);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(launchIntentSenderForPackage, 1);
                    return true;
                case 189:
                    java.lang.String readString117 = parcel.readString();
                    int readInt157 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] appOpPermissionPackages = getAppOpPermissionPackages(readString117, readInt157);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(appOpPermissionPackages);
                    return true;
                case 190:
                    java.lang.String readString118 = parcel.readString();
                    int readInt158 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.PermissionGroupInfo permissionGroupInfo = getPermissionGroupInfo(readString118, readInt158);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionGroupInfo, 1);
                    return true;
                case 191:
                    android.content.pm.PermissionInfo permissionInfo = (android.content.pm.PermissionInfo) parcel.readTypedObject(android.content.pm.PermissionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addPermission = addPermission(permissionInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPermission);
                    return true;
                case 192:
                    android.content.pm.PermissionInfo permissionInfo2 = (android.content.pm.PermissionInfo) parcel.readTypedObject(android.content.pm.PermissionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addPermissionAsync = addPermissionAsync(permissionInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPermissionAsync);
                    return true;
                case 193:
                    java.lang.String readString119 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePermission(readString119);
                    parcel2.writeNoException();
                    return true;
                case 194:
                    java.lang.String readString120 = parcel.readString();
                    java.lang.String readString121 = parcel.readString();
                    int readInt159 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkPermission = checkPermission(readString120, readString121, readInt159);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPermission);
                    return true;
                case 195:
                    java.lang.String readString122 = parcel.readString();
                    java.lang.String readString123 = parcel.readString();
                    int readInt160 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantRuntimePermission(readString122, readString123, readInt160);
                    parcel2.writeNoException();
                    return true;
                case 196:
                    java.lang.String readString124 = parcel.readString();
                    int readInt161 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkUidPermission = checkUidPermission(readString124, readInt161);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkUidPermission);
                    return true;
                case 197:
                    java.lang.String readString125 = parcel.readString();
                    java.lang.String readString126 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setMimeGroup(readString125, readString126, createStringArrayList4);
                    parcel2.writeNoException();
                    return true;
                case 198:
                    java.lang.String readString127 = parcel.readString();
                    int readInt162 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String splashScreenTheme = getSplashScreenTheme(readString127, readInt162);
                    parcel2.writeNoException();
                    parcel2.writeString(splashScreenTheme);
                    return true;
                case 199:
                    java.lang.String readString128 = parcel.readString();
                    java.lang.String readString129 = parcel.readString();
                    int readInt163 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSplashScreenTheme(readString128, readString129, readInt163);
                    parcel2.writeNoException();
                    return true;
                case 200:
                    java.lang.String readString130 = parcel.readString();
                    int readInt164 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userMinAspectRatio = getUserMinAspectRatio(readString130, readInt164);
                    parcel2.writeNoException();
                    parcel2.writeInt(userMinAspectRatio);
                    return true;
                case 201:
                    java.lang.String readString131 = parcel.readString();
                    int readInt165 = parcel.readInt();
                    int readInt166 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserMinAspectRatio(readString131, readInt165, readInt166);
                    parcel2.writeNoException();
                    return true;
                case 202:
                    java.lang.String readString132 = parcel.readString();
                    java.lang.String readString133 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> mimeGroup = getMimeGroup(readString132, readString133);
                    parcel2.writeNoException();
                    parcel2.writeStringList(mimeGroup);
                    return true;
                case 203:
                    java.lang.String readString134 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAutoRevokeWhitelisted = isAutoRevokeWhitelisted(readString134);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAutoRevokeWhitelisted);
                    return true;
                case 204:
                    int readInt167 = parcel.readInt();
                    java.lang.String readString135 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    makeProviderVisible(readInt167, readString135);
                    parcel2.writeNoException();
                    return true;
                case 205:
                    int readInt168 = parcel.readInt();
                    int readInt169 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    makeUidVisible(readInt168, readInt169);
                    parcel2.writeNoException();
                    return true;
                case 206:
                    android.os.IBinder holdLockToken = getHoldLockToken();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(holdLockToken);
                    return true;
                case 207:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt170 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    holdLock(readStrongBinder, readInt170);
                    parcel2.writeNoException();
                    return true;
                case 208:
                    java.lang.String readString136 = parcel.readString();
                    java.lang.String readString137 = parcel.readString();
                    java.lang.String readString138 = parcel.readString();
                    int readInt171 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.PackageManager.Property propertyAsUser = getPropertyAsUser(readString136, readString137, readString138, readInt171);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(propertyAsUser, 1);
                    return true;
                case 209:
                    java.lang.String readString139 = parcel.readString();
                    int readInt172 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryProperty = queryProperty(readString139, readInt172);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryProperty, 1);
                    return true;
                case 210:
                    java.util.ArrayList<java.lang.String> createStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setKeepUninstalledPackages(createStringArrayList5);
                    parcel2.writeNoException();
                    return true;
                case 211:
                    java.lang.String readString140 = parcel.readString();
                    java.lang.String[] createStringArray9 = parcel.createStringArray();
                    int readInt173 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean[] canPackageQuery = canPackageQuery(readString140, createStringArray9, readInt173);
                    parcel2.writeNoException();
                    parcel2.writeBooleanArray(canPackageQuery);
                    return true;
                case 212:
                    long readLong27 = parcel.readLong();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean waitForHandler = waitForHandler(readLong27, readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(waitForHandler);
                    return true;
                case 213:
                    android.os.IRemoteCallback asInterface13 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt174 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPackageMonitorCallback(asInterface13, readInt174);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    android.os.IRemoteCallback asInterface14 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPackageMonitorCallback(asInterface14);
                    parcel2.writeNoException();
                    return true;
                case 215:
                    java.lang.String readString141 = parcel.readString();
                    int readInt175 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ArchivedPackageParcel archivedPackage = getArchivedPackage(readString141, readInt175);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(archivedPackage, 1);
                    return true;
                case 216:
                    java.lang.String readString142 = parcel.readString();
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString143 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.graphics.Bitmap archivedAppIcon = getArchivedAppIcon(readString142, userHandle, readString143);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(archivedAppIcon, 1);
                    return true;
                case 217:
                    java.lang.String readString144 = parcel.readString();
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAppArchivable = isAppArchivable(readString144, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppArchivable);
                    return true;
                case 218:
                    java.lang.String readString145 = parcel.readString();
                    int readInt176 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appMetadataSource = getAppMetadataSource(readString145, readInt176);
                    parcel2.writeNoException();
                    parcel2.writeInt(appMetadataSource);
                    return true;
                case 219:
                    android.content.ComponentName domainVerificationAgent = getDomainVerificationAgent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(domainVerificationAgent, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(android.os.Parcel parcel, java.lang.String str, java.lang.String str2) {
            parcel.writeString(str);
            parcel.writeString(str2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements android.content.pm.IPackageManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IPackageManager.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageManager
            public void checkPackageStartable(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageAvailable(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.PackageInfo getPackageInfo(java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.PackageInfo) obtain2.readTypedObject(android.content.pm.PackageInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.PackageInfo getPackageInfoVersioned(android.content.pm.VersionedPackage versionedPackage, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(versionedPackage, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.PackageInfo) obtain2.readTypedObject(android.content.pm.PackageInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPackageUid(java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int[] getPackageGids(java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String[] currentToCanonicalPackageNames(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String[] canonicalToCurrentPackageNames(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ApplicationInfo) obtain2.readTypedObject(android.content.pm.ApplicationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getTargetSdkVersion(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ActivityInfo) obtain2.readTypedObject(android.content.pm.ActivityInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean activitySupportsIntentAsUser(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ActivityInfo getReceiverInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ActivityInfo) obtain2.readTypedObject(android.content.pm.ActivityInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ServiceInfo) obtain2.readTypedObject(android.content.pm.ServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ProviderInfo getProviderInfo(android.content.ComponentName componentName, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ProviderInfo) obtain2.readTypedObject(android.content.pm.ProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isProtectedBroadcast(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkSignatures(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkUidSignatures(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.util.List<java.lang.String> getAllPackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String[] getPackagesForUid(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getNameForUid(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String[] getNamesForUids(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getUidForSharedUser(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getFlagsForUid(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPrivateFlagsForUid(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isUidPrivileged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ResolveInfo resolveIntent(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ResolveInfo) obtain2.readTypedObject(android.content.pm.ResolveInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ResolveInfo findPersistentPreferredActivity(android.content.Intent intent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ResolveInfo) obtain2.readTypedObject(android.content.pm.ResolveInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean canForwardTo(android.content.Intent intent, java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice queryIntentActivities(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice queryIntentActivityOptions(android.content.ComponentName componentName, android.content.Intent[] intentArr, java.lang.String[] strArr, android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedArray(intentArr, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice queryIntentReceivers(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ResolveInfo resolveService(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ResolveInfo) obtain2.readTypedObject(android.content.pm.ResolveInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice queryIntentServices(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice queryIntentContentProviders(android.content.Intent intent, java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice getInstalledPackages(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.os.ParcelFileDescriptor getAppMetadataFd(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice getPackagesHoldingPermissions(java.lang.String[] strArr, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice getInstalledApplications(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice getPersistentApplications(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ProviderInfo resolveContentProvider(java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ProviderInfo) obtain2.readTypedObject(android.content.pm.ProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void querySyncProviders(java.util.List<java.lang.String> list, java.util.List<android.content.pm.ProviderInfo> list2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readStringList(list);
                    obtain2.readTypedList(list2, android.content.pm.ProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice queryContentProviders(java.lang.String str, int i, long j, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.InstrumentationInfo getInstrumentationInfoAsUser(android.content.ComponentName componentName, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.InstrumentationInfo) obtain2.readTypedObject(android.content.pm.InstrumentationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice queryInstrumentationAsUser(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void finishPackageInstall(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setInstallerPackageName(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void relinquishUpdateOwnership(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setApplicationCategoryHint(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePackageAsUser(java.lang.String str, int i, android.content.pm.IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPackageDeleteObserver);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePackageVersioned(android.content.pm.VersionedPackage versionedPackage, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(versionedPackage, 0);
                    obtain.writeStrongInterface(iPackageDeleteObserver2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteExistingPackageAsUser(android.content.pm.VersionedPackage versionedPackage, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(versionedPackage, 0);
                    obtain.writeStrongInterface(iPackageDeleteObserver2);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getInstallerPackageName(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.InstallSourceInfo getInstallSourceInfo(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.InstallSourceInfo) obtain2.readTypedObject(android.content.pm.InstallSourceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void resetApplicationPreferences(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ResolveInfo getLastChosenActivity(android.content.Intent intent, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ResolveInfo) obtain2.readTypedObject(android.content.pm.ResolveInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setLastChosenActivity(android.content.Intent intent, java.lang.String str, int i, android.content.IntentFilter intentFilter, int i2, android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addPreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(componentNameArr, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void replacePreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(componentNameArr, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPackagePreferredActivities(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPreferredActivities(java.util.List<android.content.IntentFilter> list, java.util.List<android.content.ComponentName> list2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readTypedList(list, android.content.IntentFilter.CREATOR);
                    obtain2.readTypedList(list2, android.content.ComponentName.CREATOR);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addPersistentPreferredActivity(android.content.IntentFilter intentFilter, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPackagePersistentPreferredActivities(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPersistentPreferredActivity(android.content.IntentFilter intentFilter, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addCrossProfileIntentFilter(android.content.IntentFilter intentFilter, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean removeCrossProfileIntentFilter(android.content.IntentFilter intentFilter, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearCrossProfileIntentFilters(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String[] setDistractingPackageRestrictionsAsUser(java.lang.String[] strArr, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String[] setPackagesSuspendedAsUser(java.lang.String[] strArr, boolean z, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.content.pm.SuspendDialogInfo suspendDialogInfo, int i, java.lang.String str, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(persistableBundle2, 0);
                    obtain.writeTypedObject(suspendDialogInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String[] getUnsuspendablePackagesForUser(java.lang.String[] strArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSuspendedForUser(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageQuarantinedForUser(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageStoppedForUser(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.os.Bundle getSuspendedPackageAppExtras(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getSuspendingPackage(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getPreferredActivityBackup(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restorePreferredActivities(byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getDefaultAppsBackup(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreDefaultApps(byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getDomainVerificationBackup(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreDomainVerification(byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.ComponentName getHomeActivities(java.util.List<android.content.pm.ResolveInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    android.content.ComponentName componentName = (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                    obtain2.readTypedList(list, android.content.pm.ResolveInfo.CREATOR);
                    return componentName;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setHomeActivity(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void overrideLabelAndIcon(android.content.ComponentName componentName, java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreLabelAndIcon(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setComponentEnabledSetting(android.content.ComponentName componentName, int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setComponentEnabledSettings(java.util.List<android.content.pm.PackageManager.ComponentEnabledSetting> list, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getComponentEnabledSetting(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setApplicationEnabledSetting(java.lang.String str, int i, int i2, int i3, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str2);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getApplicationEnabledSetting(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void logAppProcessStartIfNeeded(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, java.lang.String str4, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void flushPackageRestrictionsAsUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setPackageStoppedState(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void freeStorageAndNotify(java.lang.String str, long j, int i, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void freeStorage(java.lang.String str, long j, int i, android.content.IntentSender intentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteApplicationCacheFiles(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteApplicationCacheFilesAsUser(java.lang.String str, int i, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearApplicationUserData(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    obtain.writeInt(i);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearApplicationProfileData(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void getPackageSizeInfo(java.lang.String str, int i, android.content.pm.IPackageStatsObserver iPackageStatsObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPackageStatsObserver);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String[] getSystemSharedLibraryNames() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.util.Map<java.lang.String, java.lang.String> getSystemSharedLibraryNamesAndPaths() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                final android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final java.util.HashMap hashMap = readInt < 0 ? null : new java.util.HashMap();
                    java.util.stream.IntStream.range(0, readInt).forEach(new java.util.function.IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            hashMap.put(r0.readString(), android.os.Parcel.this.readString());
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice getSystemAvailableFeatures() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSystemFeature(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.util.List<java.lang.String> getInitialNonStoppedSystemPackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void enterSafeMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isSafeMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSystemUidErrors() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyPackageUse(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(109, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyDexLoad(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map, java.lang.String str2) throws android.os.RemoteException {
                final android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new java.util.function.BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.content.pm.IPackageManager.Stub.Proxy.lambda$notifyDexLoad$1(android.os.Parcel.this, (java.lang.String) obj, (java.lang.String) obj2);
                            }
                        });
                    }
                    obtain.writeString(str2);
                    this.mRemote.transact(110, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$notifyDexLoad$1(android.os.Parcel parcel, java.lang.String str, java.lang.String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }

            @Override // android.content.pm.IPackageManager
            public void registerDexModule(java.lang.String str, java.lang.String str2, boolean z, android.content.pm.IDexModuleRegisterCallback iDexModuleRegisterCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iDexModuleRegisterCallback);
                    this.mRemote.transact(111, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean performDexOptMode(java.lang.String str, boolean z, java.lang.String str2, boolean z2, boolean z3, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeString(str3);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean performDexOptSecondary(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getMoveStatus(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void registerMoveCallback(android.content.pm.IPackageMoveObserver iPackageMoveObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPackageMoveObserver);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void unregisterMoveCallback(android.content.pm.IPackageMoveObserver iPackageMoveObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPackageMoveObserver);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int movePackage(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int movePrimaryStorage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setInstallLocation(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getInstallLocation() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int installExistingPackageAsUser(java.lang.String str, int i, int i2, int i3, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStringList(list);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void verifyPendingInstall(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void extendVerificationTimeout(int i, int i2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void verifyIntentFilter(int i, int i2, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringList(list);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getIntentVerificationStatus(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean updateIntentVerificationStatus(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice getIntentFilterVerifications(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice getAllIntentFilters(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.VerifierDeviceIdentity getVerifierDeviceIdentity() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.VerifierDeviceIdentity) obtain2.readTypedObject(android.content.pm.VerifierDeviceIdentity.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isFirstBoot() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isDeviceUpgrading() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isStorageLow() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setApplicationHiddenSettingAsUser(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean getApplicationHiddenSettingAsUser(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setSystemAppHiddenUntilInstalled(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setSystemAppInstallState(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.IPackageInstaller getPackageInstaller() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.content.pm.IPackageInstaller.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setBlockUninstallForUser(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean getBlockUninstallForUser(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.KeySet getKeySetByAlias(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.KeySet) obtain2.readTypedObject(android.content.pm.KeySet.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.KeySet getSigningKeySet(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.KeySet) obtain2.readTypedObject(android.content.pm.KeySet.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSignedByKeySet(java.lang.String str, android.content.pm.KeySet keySet) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(keySet, 0);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSignedByKeySetExactly(java.lang.String str, android.content.pm.KeySet keySet) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(keySet, 0);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getPermissionControllerPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getSdkSandboxPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice getInstantApps(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getInstantAppCookie(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setInstantAppCookie(java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.graphics.Bitmap getInstantAppIcon(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Bitmap) obtain2.readTypedObject(android.graphics.Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isInstantApp(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setRequiredForSystemUser(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setUpdateAvailable(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getServicesSystemSharedLibraryPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getSharedSystemSharedLibraryPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ChangedPackages getChangedPackages(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ChangedPackages) obtain2.readTypedObject(android.content.pm.ChangedPackages.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageDeviceAdminOnAnyUser(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getInstallReason(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice getSharedLibraries(java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice getDeclaredSharedLibraries(java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean canRequestPackageInstalls(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePreloadsFileCache() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.ComponentName getInstantAppResolverComponent() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.ComponentName getInstantAppResolverSettingsComponent() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.ComponentName getInstantAppInstallerComponent() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getInstantAppAndroidId(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.dex.IArtManager getArtManager() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.content.pm.dex.IArtManager.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setHarmfulAppWarning(java.lang.String str, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.CharSequence getHarmfulAppWarning(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSigningCertificate(java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getDefaultTextClassifierPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(171, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getSystemTextClassifierPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(172, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getAttentionServicePackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(173, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getRotationResolverPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getWellbeingPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getAppPredictionServicePackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getSystemCaptionsServicePackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getSetupWizardPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getIncidentReportApproverPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageStateProtected(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(180, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void sendDeviceCustomizationReadyBroadcast() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.util.List<android.content.pm.ModuleInfo> getInstalledModules(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(182, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.pm.ModuleInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ModuleInfo getModuleInfo(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ModuleInfo) obtain2.readTypedObject(android.content.pm.ModuleInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getRuntimePermissionsVersion(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(184, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setRuntimePermissionsVersion(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyPackagesReplacedReceived(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void requestPackageChecksums(java.lang.String str, boolean z, int i, int i2, java.util.List list, android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeList(list);
                    obtain.writeStrongInterface(iOnChecksumsReadyListener);
                    obtain.writeInt(i3);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.IntentSender getLaunchIntentSenderForPackage(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.IntentSender) obtain2.readTypedObject(android.content.IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String[] getAppOpPermissionPackages(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.PermissionGroupInfo) obtain2.readTypedObject(android.content.pm.PermissionGroupInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean addPermission(android.content.pm.PermissionInfo permissionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(permissionInfo, 0);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean addPermissionAsync(android.content.pm.PermissionInfo permissionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(permissionInfo, 0);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void removePermission(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkPermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void grantRuntimePermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkUidPermission(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setMimeGroup(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    this.mRemote.transact(197, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.lang.String getSplashScreenTheme(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setSplashScreenTheme(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getUserMinAspectRatio(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setUserMinAspectRatio(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public java.util.List<java.lang.String> getMimeGroup(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isAutoRevokeWhitelisted(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void makeProviderVisible(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void makeUidVisible(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.os.IBinder getHoldLockToken() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void holdLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(207, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.PackageManager.Property getPropertyAsUser(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.PackageManager.Property) obtain2.readTypedObject(android.content.pm.PackageManager.Property.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ParceledListSlice queryProperty(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setKeepUninstalledPackages(java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean[] canPackageQuery(java.lang.String str, java.lang.String[] strArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createBooleanArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean waitForHandler(long j, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void registerPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void unregisterPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.pm.ArchivedPackageParcel getArchivedPackage(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(215, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ArchivedPackageParcel) obtain2.readTypedObject(android.content.pm.ArchivedPackageParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.graphics.Bitmap getArchivedAppIcon(java.lang.String str, android.os.UserHandle userHandle, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Bitmap) obtain2.readTypedObject(android.graphics.Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isAppArchivable(java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getAppMetadataSource(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(218, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public android.content.ComponentName getDomainVerificationAgent() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(219, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getAppMetadataFd_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.GET_APP_METADATA, getCallingPid(), getCallingUid());
        }

        protected void removeCrossProfileIntentFilter_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.INTERACT_ACROSS_USERS_FULL, getCallingPid(), getCallingUid());
        }

        protected void clearCrossProfileIntentFilters_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.INTERACT_ACROSS_USERS_FULL, getCallingPid(), getCallingUid());
        }

        protected void freeStorageAndNotify_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CLEAR_APP_CACHE, getCallingPid(), getCallingUid());
        }

        protected void freeStorage_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CLEAR_APP_CACHE, getCallingPid(), getCallingUid());
        }

        protected void clearApplicationUserData_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CLEAR_APP_USER_DATA, getCallingPid(), getCallingUid());
        }

        protected void getMoveStatus_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void registerMoveCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void unregisterMoveCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void movePackage_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOVE_PACKAGE, getCallingPid(), getCallingUid());
        }

        protected void movePrimaryStorage_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOVE_PACKAGE, getCallingPid(), getCallingUid());
        }

        protected void setInstallLocation_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void getVerifierDeviceIdentity_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.PACKAGE_VERIFICATION_AGENT, getCallingPid(), getCallingUid());
        }

        protected void setApplicationHiddenSettingAsUser_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USERS, getCallingPid(), getCallingUid());
        }

        protected void setBlockUninstallForUser_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DELETE_PACKAGES, getCallingPid(), getCallingUid());
        }

        protected void setUpdateAvailable_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.INSTALL_PACKAGES, getCallingPid(), getCallingUid());
        }

        protected void getInstantAppAndroidId_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_INSTANT_APPS, getCallingPid(), getCallingUid());
        }

        protected void setUserMinAspectRatio_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.INSTALL_PACKAGES, getCallingPid(), getCallingUid());
        }

        protected void makeUidVisible_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MAKE_UID_VISIBLE, getCallingPid(), getCallingUid());
        }

        protected void getAppMetadataSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.GET_APP_METADATA, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 218;
        }
    }
}
