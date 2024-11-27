package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes2.dex */
public interface Computer extends com.android.server.pm.snapshot.PackageDataSnapshot {
    boolean activitySupportsIntentAsUser(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull android.content.ComponentName componentName2, @android.annotation.NonNull android.content.Intent intent, java.lang.String str, int i);

    java.util.List<android.content.pm.ResolveInfo> applyPostResolutionFilter(@android.annotation.NonNull java.util.List<android.content.pm.ResolveInfo> list, java.lang.String str, boolean z, int i, boolean z2, int i2, android.content.Intent intent);

    boolean canAccessComponent(int i, @android.annotation.NonNull android.content.ComponentName componentName, int i2);

    boolean canForwardTo(@android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, int i, int i2);

    @android.annotation.NonNull
    boolean[] canPackageQuery(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String[] strArr, int i);

    boolean canQueryPackage(int i, @android.annotation.Nullable java.lang.String str);

    boolean canRequestPackageInstalls(@android.annotation.NonNull java.lang.String str, int i, int i2, boolean z);

    boolean canViewInstantApps(int i, int i2);

    @android.annotation.NonNull
    java.lang.String[] canonicalToCurrentPackageNames(@android.annotation.NonNull java.lang.String[] strArr);

    void checkPackageFrozen(@android.annotation.NonNull java.lang.String str);

    int checkSignatures(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i);

    int checkUidPermission(java.lang.String str, int i);

    int checkUidSignatures(int i, int i2);

    int checkUidSignaturesForAllUsers(int i, int i2);

    android.content.pm.ResolveInfo createForwardingResolveInfoUnchecked(com.android.server.pm.WatchedIntentFilter watchedIntentFilter, int i, int i2);

    @android.annotation.NonNull
    java.lang.String[] currentToCanonicalPackageNames(@android.annotation.NonNull java.lang.String[] strArr);

    void dump(int i, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, com.android.server.pm.DumpState dumpState);

    void dumpKeySet(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.pm.DumpState dumpState);

    void dumpPackages(java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, boolean z);

    void dumpPackagesProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream);

    void dumpPermissions(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, @android.annotation.NonNull com.android.server.pm.DumpState dumpState);

    void dumpSharedLibrariesProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream);

    void dumpSharedUsers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, boolean z);

    void dumpSharedUsersProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream);

    void enforceCrossUserOrProfilePermission(int i, int i2, boolean z, boolean z2, java.lang.String str);

    void enforceCrossUserPermission(int i, int i2, boolean z, boolean z2, java.lang.String str);

    void enforceCrossUserPermission(int i, int i2, boolean z, boolean z2, boolean z3, java.lang.String str);

    boolean filterAppAccess(int i, int i2);

    boolean filterAppAccess(com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2);

    boolean filterAppAccess(java.lang.String str, int i, int i2, boolean z);

    @android.annotation.NonNull
    java.lang.String[] filterOnlySystemPackages(@android.annotation.Nullable java.lang.String... strArr);

    boolean filterSharedLibPackage(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2, long j);

    android.content.pm.ResolveInfo findPersistentPreferredActivity(android.content.Intent intent, java.lang.String str, long j, java.util.List<android.content.pm.ResolveInfo> list, boolean z, int i);

    com.android.server.pm.PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityInternal(android.content.Intent intent, java.lang.String str, long j, java.util.List<android.content.pm.ResolveInfo> list, boolean z, boolean z2, boolean z3, int i, boolean z4);

    @android.annotation.NonNull
    java.util.List<com.android.server.pm.pkg.PackageStateInternal> findSharedNonSystemLibraries(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal);

    android.content.pm.ApplicationInfo generateApplicationInfoFromSettings(java.lang.String str, long j, int i, int i2);

    android.content.pm.PackageInfo generatePackageInfo(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, long j, int i);

    android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, long j, int i);

    android.content.pm.ActivityInfo getActivityInfoCrossProfile(android.content.ComponentName componentName, long j, int i);

    android.content.pm.ActivityInfo getActivityInfoInternal(android.content.ComponentName componentName, long j, int i, int i2);

    java.lang.String[] getAllAvailablePackageNames();

    @android.annotation.NonNull
    android.content.pm.ParceledListSlice<android.content.IntentFilter> getAllIntentFilters(@android.annotation.NonNull java.lang.String str);

    @android.annotation.NonNull
    java.util.List<java.lang.String> getAllPackages();

    @android.annotation.NonNull
    java.lang.String[] getAppOpPermissionPackages(@android.annotation.NonNull java.lang.String str, int i);

    int getApplicationEnabledSetting(@android.annotation.NonNull java.lang.String str, int i);

    boolean getApplicationHiddenSettingAsUser(@android.annotation.NonNull java.lang.String str, int i);

    android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, long j, int i);

    android.content.pm.ApplicationInfo getApplicationInfoInternal(java.lang.String str, long j, int i, int i2);

    @android.annotation.NonNull
    android.util.SparseArray<java.lang.String> getAppsWithSharedUserIds();

    boolean getBlockUninstall(int i, @android.annotation.NonNull java.lang.String str);

    boolean getBlockUninstallForUser(@android.annotation.NonNull java.lang.String str, int i);

    int getComponentEnabledSetting(@android.annotation.NonNull android.content.ComponentName componentName, int i, int i2);

    int getComponentEnabledSettingInternal(@android.annotation.NonNull android.content.ComponentName componentName, int i, int i2);

    @android.annotation.NonNull
    com.android.server.pm.resolution.ComponentResolverApi getComponentResolver();

    com.android.server.pm.CrossProfileDomainInfo getCrossProfileDomainPreferredLpr(android.content.Intent intent, java.lang.String str, long j, int i, int i2);

    @android.annotation.Nullable
    android.content.pm.ParceledListSlice<android.content.pm.SharedLibraryInfo> getDeclaredSharedLibraries(@android.annotation.NonNull java.lang.String str, long j, int i);

    android.content.ComponentName getDefaultHomeActivity(int i);

    @android.annotation.Nullable
    com.android.server.pm.pkg.PackageStateInternal getDisabledSystemPackage(@android.annotation.NonNull java.lang.String str);

    @android.annotation.NonNull
    android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> getDisabledSystemPackageStates();

    int getFlagsForUid(int i);

    @android.annotation.NonNull
    com.android.server.utils.WatchedArrayMap<java.lang.String, java.lang.Integer> getFrozenPackages();

    @android.annotation.Nullable
    android.content.pm.ProviderInfo getGrantImplicitAccessProviderInfo(int i, @android.annotation.NonNull java.lang.String str);

    @android.annotation.Nullable
    java.lang.CharSequence getHarmfulAppWarning(@android.annotation.NonNull java.lang.String str, int i);

    android.content.ComponentName getHomeActivitiesAsUser(java.util.List<android.content.pm.ResolveInfo> list, int i);

    android.content.Intent getHomeIntent();

    int getInstallReason(@android.annotation.NonNull java.lang.String str, int i);

    @android.annotation.Nullable
    android.content.pm.InstallSourceInfo getInstallSourceInfo(@android.annotation.NonNull java.lang.String str, int i);

    @android.annotation.NonNull
    java.util.List<android.content.pm.ApplicationInfo> getInstalledApplications(long j, int i, int i2, boolean z);

    android.content.pm.ParceledListSlice<android.content.pm.PackageInfo> getInstalledPackages(long j, int i);

    @android.annotation.Nullable
    java.lang.String getInstallerPackageName(@android.annotation.NonNull java.lang.String str, int i);

    @android.annotation.Nullable
    android.content.ComponentName getInstantAppInstallerComponent();

    @android.annotation.Nullable
    android.content.pm.ResolveInfo getInstantAppInstallerInfo();

    java.lang.String getInstantAppPackageName(int i);

    @android.annotation.Nullable
    android.content.pm.InstrumentationInfo getInstrumentationInfoAsUser(@android.annotation.NonNull android.content.ComponentName componentName, int i, int i2);

    @android.annotation.Nullable
    android.content.pm.KeySet getKeySetByAlias(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2);

    java.util.List<com.android.server.pm.CrossProfileIntentFilter> getMatchingCrossProfileIntentFilters(android.content.Intent intent, java.lang.String str, int i);

    @android.annotation.Nullable
    java.lang.String getNameForUid(int i);

    @android.annotation.Nullable
    java.lang.String[] getNamesForUids(@android.annotation.NonNull int[] iArr);

    @android.annotation.NonNull
    android.util.ArraySet<java.lang.String> getNotifyPackagesForReplacedReceived(@android.annotation.NonNull java.lang.String[] strArr);

    com.android.server.pm.pkg.AndroidPackage getPackage(int i);

    com.android.server.pm.pkg.AndroidPackage getPackage(java.lang.String str);

    @android.annotation.NonNull
    int[] getPackageGids(@android.annotation.NonNull java.lang.String str, long j, int i);

    android.content.pm.PackageInfo getPackageInfo(java.lang.String str, long j, int i);

    android.content.pm.PackageInfo getPackageInfoInternal(java.lang.String str, long j, long j2, int i, int i2);

    @android.annotation.Nullable
    android.util.Pair<com.android.server.pm.pkg.PackageStateInternal, com.android.server.pm.pkg.SharedUserApi> getPackageOrSharedUser(int i);

    int getPackageStartability(boolean z, @android.annotation.NonNull java.lang.String str, int i, int i2);

    com.android.server.pm.pkg.PackageStateInternal getPackageStateFiltered(@android.annotation.NonNull java.lang.String str, int i, int i2);

    @android.annotation.Nullable
    com.android.server.pm.pkg.PackageStateInternal getPackageStateForInstalledAndFiltered(@android.annotation.NonNull java.lang.String str, int i, int i2);

    com.android.server.pm.pkg.PackageStateInternal getPackageStateInternal(java.lang.String str);

    com.android.server.pm.pkg.PackageStateInternal getPackageStateInternal(java.lang.String str, int i);

    @android.annotation.NonNull
    android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> getPackageStates();

    int getPackageUid(@android.annotation.NonNull java.lang.String str, long j, int i);

    int getPackageUidInternal(java.lang.String str, long j, int i, int i2);

    @android.annotation.NonNull
    java.util.List<com.android.server.pm.pkg.AndroidPackage> getPackagesForAppId(int i);

    java.lang.String[] getPackagesForUid(int i);

    @android.annotation.NonNull
    android.content.pm.ParceledListSlice<android.content.pm.PackageInfo> getPackagesHoldingPermissions(@android.annotation.NonNull java.lang.String[] strArr, long j, int i);

    @android.annotation.NonNull
    android.util.Pair<java.util.List<android.content.pm.VersionedPackage>, java.util.List<java.lang.Boolean>> getPackagesUsingSharedLibrary(@android.annotation.NonNull android.content.pm.SharedLibraryInfo sharedLibraryInfo, long j, int i, int i2);

    @android.annotation.NonNull
    java.util.List<android.content.pm.ApplicationInfo> getPersistentApplications(boolean z, int i);

    com.android.server.pm.PreferredIntentResolver getPreferredActivities(int i);

    int getPrivateFlagsForUid(int i);

    @android.annotation.Nullable
    android.util.ArrayMap<java.lang.String, android.content.pm.ProcessInfo> getProcessesForUid(int i);

    android.content.pm.UserInfo getProfileParent(int i);

    @android.annotation.Nullable
    android.content.pm.ProviderInfo getProviderInfo(@android.annotation.NonNull android.content.ComponentName componentName, long j, int i);

    @android.annotation.Nullable
    android.content.pm.ActivityInfo getReceiverInfo(@android.annotation.NonNull android.content.ComponentName componentName, long j, int i);

    @android.annotation.Nullable
    java.lang.String getRenamedPackage(@android.annotation.NonNull java.lang.String str);

    android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName, long j, int i);

    @android.annotation.Nullable
    android.content.pm.ParceledListSlice<android.content.pm.SharedLibraryInfo> getSharedLibraries(@android.annotation.NonNull java.lang.String str, long j, int i);

    @android.annotation.NonNull
    com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> getSharedLibraries();

    android.content.pm.SharedLibraryInfo getSharedLibraryInfo(java.lang.String str, long j);

    @android.annotation.Nullable
    com.android.server.pm.pkg.SharedUserApi getSharedUser(int i);

    @android.annotation.NonNull
    android.util.ArraySet<com.android.server.pm.pkg.PackageStateInternal> getSharedUserPackages(int i);

    @android.annotation.NonNull
    java.lang.String[] getSharedUserPackagesForPackage(@android.annotation.NonNull java.lang.String str, int i);

    @android.annotation.NonNull
    android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.SharedUserApi> getSharedUsers();

    android.content.pm.SigningDetails getSigningDetails(int i);

    android.content.pm.SigningDetails getSigningDetails(@android.annotation.NonNull java.lang.String str);

    @android.annotation.Nullable
    android.content.pm.KeySet getSigningKeySet(@android.annotation.NonNull java.lang.String str);

    android.util.ArrayMap<java.lang.String, java.lang.String> getSystemSharedLibraryNamesAndPaths();

    int getTargetSdkVersion(@android.annotation.NonNull java.lang.String str);

    int getUidForSharedUser(@android.annotation.NonNull java.lang.String str);

    int getUidTargetSdkVersion(int i);

    @android.annotation.NonNull
    java.util.Set<java.lang.String> getUnusedPackages(long j);

    @android.annotation.NonNull
    android.content.pm.UserInfo[] getUserInfos();

    int getVersion();

    @android.annotation.Nullable
    int[] getVisibilityAllowList(@android.annotation.NonNull java.lang.String str, int i);

    @android.annotation.Nullable
    android.util.SparseArray<int[]> getVisibilityAllowLists(@android.annotation.NonNull java.lang.String str, int[] iArr);

    @android.annotation.NonNull
    java.util.List<? extends com.android.server.pm.pkg.PackageStateInternal> getVolumePackages(@android.annotation.NonNull java.lang.String str);

    boolean hasSigningCertificate(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr, int i);

    boolean hasUidSigningCertificate(int i, @android.annotation.NonNull byte[] bArr, int i2);

    boolean isApexPackage(java.lang.String str);

    boolean isApplicationEffectivelyEnabled(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle);

    boolean isCallerInstallerOfRecord(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i);

    boolean isCallerSameApp(java.lang.String str, int i);

    boolean isCallerSameApp(java.lang.String str, int i, boolean z);

    boolean isComponentEffectivelyEnabled(@android.annotation.NonNull android.content.pm.ComponentInfo componentInfo, @android.annotation.NonNull android.os.UserHandle userHandle);

    boolean isComponentVisibleToInstantApp(@android.annotation.Nullable android.content.ComponentName componentName);

    boolean isComponentVisibleToInstantApp(@android.annotation.Nullable android.content.ComponentName componentName, int i);

    boolean isImplicitImageCaptureIntentAndNotSetByDpc(android.content.Intent intent, int i, java.lang.String str, long j);

    boolean isInstallDisabledForPackage(@android.annotation.NonNull java.lang.String str, int i, int i2);

    boolean isInstantApp(java.lang.String str, int i);

    boolean isInstantAppInternal(java.lang.String str, int i, int i2);

    boolean isPackageAvailable(java.lang.String str, int i);

    boolean isPackageQuarantinedForUser(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    boolean isPackageSignedByKeySet(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.KeySet keySet);

    boolean isPackageSignedByKeySetExactly(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.KeySet keySet);

    boolean isPackageStoppedForUser(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    boolean isPackageSuspendedForUser(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    boolean isSameProfileGroup(int i, int i2);

    boolean isSuspendingAnyPackages(@android.annotation.NonNull java.lang.String str, int i, int i2);

    boolean isUidPrivileged(int i);

    @android.annotation.NonNull
    android.content.pm.ParceledListSlice<android.content.pm.ProviderInfo> queryContentProviders(@android.annotation.Nullable java.lang.String str, int i, long j, @android.annotation.Nullable java.lang.String str2);

    @android.annotation.NonNull
    android.content.pm.ParceledListSlice<android.content.pm.InstrumentationInfo> queryInstrumentationAsUser(@android.annotation.NonNull java.lang.String str, int i, int i2);

    @android.annotation.NonNull
    java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal(android.content.Intent intent, java.lang.String str, long j, int i);

    @android.annotation.NonNull
    java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal(android.content.Intent intent, java.lang.String str, long j, int i, int i2);

    @android.annotation.NonNull
    java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal(android.content.Intent intent, java.lang.String str, long j, long j2, int i, int i2, boolean z, boolean z2);

    @android.annotation.NonNull
    com.android.server.pm.QueryIntentActivitiesResult queryIntentActivitiesInternalBody(android.content.Intent intent, java.lang.String str, long j, int i, int i2, boolean z, boolean z2, java.lang.String str2, java.lang.String str3);

    @android.annotation.NonNull
    java.util.List<android.content.pm.ResolveInfo> queryIntentServicesInternal(android.content.Intent intent, java.lang.String str, long j, int i, int i2, boolean z);

    void querySyncProviders(boolean z, @android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.NonNull java.util.List<android.content.pm.ProviderInfo> list2);

    @android.annotation.Nullable
    android.content.pm.ProviderInfo resolveContentProvider(@android.annotation.NonNull java.lang.String str, long j, int i, int i2);

    java.lang.String resolveExternalPackageName(com.android.server.pm.pkg.AndroidPackage androidPackage);

    java.lang.String resolveInternalPackageName(java.lang.String str, long j);

    boolean shouldFilterApplication(@android.annotation.NonNull com.android.server.pm.SharedUserSetting sharedUserSetting, int i, int i2);

    boolean shouldFilterApplication(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2);

    boolean shouldFilterApplication(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, @android.annotation.Nullable android.content.ComponentName componentName, int i2, int i3);

    boolean shouldFilterApplication(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, @android.annotation.Nullable android.content.ComponentName componentName, int i2, int i3, boolean z);

    boolean shouldFilterApplicationIncludingUninstalled(@android.annotation.NonNull com.android.server.pm.SharedUserSetting sharedUserSetting, int i, int i2);

    boolean shouldFilterApplicationIncludingUninstalled(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2);

    boolean shouldFilterApplicationIncludingUninstalledNotArchived(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2);

    long updateFlagsForApplication(long j, int i);

    long updateFlagsForComponent(long j, int i);

    long updateFlagsForPackage(long j, int i);

    long updateFlagsForResolve(long j, int i, int i2, boolean z, boolean z2);

    long updateFlagsForResolve(long j, int i, int i2, boolean z, boolean z2, boolean z3);

    com.android.server.pm.Computer use();

    default int getUsed() {
        return 0;
    }
}
