package com.android.server.pm;

/* loaded from: classes2.dex */
abstract class PackageManagerInternalBase extends android.content.pm.PackageManagerInternal {

    @android.annotation.NonNull
    private final com.android.server.pm.PackageManagerService mService;

    @android.annotation.NonNull
    protected abstract com.android.server.pm.ApexManager getApexManager();

    @android.annotation.NonNull
    protected abstract com.android.server.pm.AppDataHelper getAppDataHelper();

    @android.annotation.NonNull
    protected abstract android.content.Context getContext();

    @android.annotation.NonNull
    protected abstract com.android.server.pm.dex.DexManager getDexManager();

    @android.annotation.NonNull
    protected abstract com.android.server.pm.DistractingPackageHelper getDistractingPackageHelper();

    @android.annotation.NonNull
    protected abstract com.android.server.pm.InstantAppRegistry getInstantAppRegistry();

    @android.annotation.NonNull
    protected abstract com.android.server.pm.PackageObserverHelper getPackageObserverHelper();

    @android.annotation.NonNull
    protected abstract com.android.server.pm.permission.PermissionManagerServiceInternal getPermissionManager();

    @android.annotation.NonNull
    protected abstract com.android.server.pm.ProtectedPackages getProtectedPackages();

    @android.annotation.NonNull
    protected abstract com.android.server.pm.ResolveIntentHelper getResolveIntentHelper();

    @android.annotation.NonNull
    protected abstract com.android.server.pm.SuspendPackageHelper getSuspendPackageHelper();

    @android.annotation.NonNull
    protected abstract com.android.server.pm.UserNeedsBadgingCache getUserNeedsBadging();

    public PackageManagerInternalBase(@android.annotation.NonNull com.android.server.pm.PackageManagerService packageManagerService) {
        this.mService = packageManagerService;
    }

    @Override // android.content.pm.PackageManagerInternal
    public final com.android.server.pm.Computer snapshot() {
        return this.mService.snapshotComputer();
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final java.util.List<android.content.pm.ApplicationInfo> getInstalledApplications(long j, int i, int i2) {
        return snapshot().getInstalledApplications(j, i, i2, false);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final java.util.List<android.content.pm.ApplicationInfo> getInstalledApplicationsCrossUser(long j, int i, int i2) {
        return snapshot().getInstalledApplications(j, i, i2, true);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isInstantApp(java.lang.String str, int i) {
        return snapshot().isInstantApp(str, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final java.lang.String getInstantAppPackageName(int i) {
        return snapshot().getInstantAppPackageName(i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean filterAppAccess(com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2) {
        return snapshot().filterAppAccess(androidPackage, i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean filterAppAccess(java.lang.String str, int i, int i2, boolean z) {
        return snapshot().filterAppAccess(str, i, i2, z);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean filterAppAccess(int i, int i2) {
        return snapshot().filterAppAccess(i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @android.annotation.Nullable
    @java.lang.Deprecated
    public final int[] getVisibilityAllowList(@android.annotation.NonNull java.lang.String str, int i) {
        return snapshot().getVisibilityAllowList(str, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean canQueryPackage(int i, @android.annotation.Nullable java.lang.String str) {
        return snapshot().canQueryPackage(i, str);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final com.android.server.pm.pkg.AndroidPackage getPackage(java.lang.String str) {
        return snapshot().getPackage(str);
    }

    @Override // android.content.pm.PackageManagerInternal
    @android.annotation.Nullable
    @java.lang.Deprecated
    public final com.android.server.pm.pkg.AndroidPackage getAndroidPackage(@android.annotation.NonNull java.lang.String str) {
        return snapshot().getPackage(str);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final com.android.server.pm.pkg.AndroidPackage getPackage(int i) {
        return snapshot().getPackage(i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final java.util.List<com.android.server.pm.pkg.AndroidPackage> getPackagesForAppId(int i) {
        return snapshot().getPackagesForAppId(i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @android.annotation.Nullable
    @java.lang.Deprecated
    public final com.android.server.pm.pkg.PackageStateInternal getPackageStateInternal(java.lang.String str) {
        return snapshot().getPackageStateInternal(str);
    }

    @Override // android.content.pm.PackageManagerInternal
    @android.annotation.NonNull
    @java.lang.Deprecated
    public final android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> getPackageStates() {
        return snapshot().getPackageStates();
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void removePackageListObserver(android.content.pm.PackageManagerInternal.PackageListObserver packageListObserver) {
        getPackageObserverHelper().removeObserver(packageListObserver);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final com.android.server.pm.pkg.PackageStateInternal getDisabledSystemPackage(@android.annotation.NonNull java.lang.String str) {
        return snapshot().getDisabledSystemPackage(str);
    }

    @Override // android.content.pm.PackageManagerInternal
    @android.annotation.NonNull
    @java.lang.Deprecated
    public final java.lang.String[] getKnownPackageNames(int i, int i2) {
        return this.mService.getKnownPackageNamesInternal(snapshot(), i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void setKeepUninstalledPackages(java.util.List<java.lang.String> list) {
        this.mService.setKeepUninstalledPackagesInternal(snapshot(), list);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isPermissionsReviewRequired(java.lang.String str, int i) {
        return getPermissionManager().isPermissionsReviewRequired(str, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.pm.PackageInfo getPackageInfo(java.lang.String str, long j, int i, int i2) {
        return snapshot().getPackageInfoInternal(str, -1L, j, i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.os.Bundle getSuspendedPackageLauncherExtras(java.lang.String str, int i) {
        return getSuspendPackageHelper().getSuspendedPackageLauncherExtras(snapshot(), str, i, android.os.Binder.getCallingUid());
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isPackageSuspended(java.lang.String str, int i) {
        return getSuspendPackageHelper().isPackageSuspended(snapshot(), str, i, android.os.Binder.getCallingUid());
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void removeNonSystemPackageSuspensions(java.lang.String str, int i) {
        getSuspendPackageHelper().removeSuspensionsBySuspendingPackage(snapshot(), new java.lang.String[]{str}, new java.util.function.Predicate() { // from class: com.android.server.pm.PackageManagerInternalBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeNonSystemPackageSuspensions$0;
                lambda$removeNonSystemPackageSuspensions$0 = com.android.server.pm.PackageManagerInternalBase.lambda$removeNonSystemPackageSuspensions$0((android.content.pm.UserPackage) obj);
                return lambda$removeNonSystemPackageSuspensions$0;
            }
        }, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeNonSystemPackageSuspensions$0(android.content.pm.UserPackage userPackage) {
        return !com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(userPackage.packageName);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void removeDistractingPackageRestrictions(java.lang.String str, int i) {
        getDistractingPackageHelper().removeDistractingPackageRestrictions(snapshot(), new java.lang.String[]{str}, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void removeAllDistractingPackageRestrictions(int i) {
        this.mService.removeAllDistractingPackageRestrictions(snapshot(), i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.pm.UserPackage getSuspendingPackage(java.lang.String str, int i) {
        return getSuspendPackageHelper().getSuspendingPackage(snapshot(), str, i, android.os.Binder.getCallingUid());
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.pm.SuspendDialogInfo getSuspendedDialogInfo(java.lang.String str, android.content.pm.UserPackage userPackage, int i) {
        return getSuspendPackageHelper().getSuspendedDialogInfo(snapshot(), str, userPackage, i, android.os.Binder.getCallingUid());
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final int getDistractingPackageRestrictions(java.lang.String str, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return 0;
        }
        return packageStateInternal.getUserStateOrDefault(i).getDistractionFlags();
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final int getPackageUid(java.lang.String str, long j, int i) {
        return snapshot().getPackageUidInternal(str, j, i, 1000);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, long j, int i, int i2) {
        return snapshot().getApplicationInfoInternal(str, j, i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, long j, int i, int i2) {
        return snapshot().getActivityInfoInternal(componentName, j, i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final java.util.List<android.content.pm.ResolveInfo> queryIntentActivities(android.content.Intent intent, java.lang.String str, long j, int i, int i2) {
        return snapshot().queryIntentActivitiesInternal(intent, str, j, i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final java.util.List<android.content.pm.ResolveInfo> queryIntentReceivers(android.content.Intent intent, java.lang.String str, long j, int i, int i2, boolean z) {
        return getResolveIntentHelper().queryIntentReceiversInternal(snapshot(), intent, str, j, i2, i, z);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final java.util.List<android.content.pm.ResolveInfo> queryIntentServices(android.content.Intent intent, long j, int i, int i2) {
        return snapshot().queryIntentServicesInternal(intent, intent.resolveTypeIfNeeded(getContext().getContentResolver()), j, i2, i, false);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.ComponentName getHomeActivitiesAsUser(java.util.List<android.content.pm.ResolveInfo> list, int i) {
        return snapshot().getHomeActivitiesAsUser(list, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.ComponentName getDefaultHomeActivity(int i) {
        return snapshot().getDefaultHomeActivity(i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.ComponentName getSystemUiServiceComponent() {
        return android.content.ComponentName.unflattenFromString(getContext().getResources().getString(android.R.string.config_sensorUseStartedActivity));
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void setOwnerProtectedPackages(int i, @android.annotation.Nullable java.util.List<java.lang.String> list) {
        getProtectedPackages().setOwnerProtectedPackages(i, list);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isPackageDataProtected(int i, java.lang.String str) {
        return getProtectedPackages().isPackageDataProtected(i, str);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isPackageStateProtected(java.lang.String str, int i) {
        return getProtectedPackages().isPackageStateProtected(i, str);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isPackageEphemeral(int i, java.lang.String str) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        return packageStateInternal != null && packageStateInternal.getUserStateOrDefault(i).isInstantApp();
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean wasPackageEverLaunched(java.lang.String str, int i) {
        if (getPackageStateInternal(str) == null) {
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
        return !r0.getUserStateOrDefault(i).isNotLaunched();
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isEnabledAndMatches(com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent, long j, int i) {
        return com.android.server.pm.pkg.PackageStateUtils.isEnabledAndMatches(getPackageStateInternal(parsedMainComponent.getPackageName()), parsedMainComponent, j, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean userNeedsBadging(int i) {
        return getUserNeedsBadging().get(i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final java.lang.String getNameForUid(int i) {
        return snapshot().getNameForUid(i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void requestInstantAppResolutionPhaseTwo(android.content.pm.AuxiliaryResolveInfo auxiliaryResolveInfo, android.content.Intent intent, java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, boolean z, android.os.Bundle bundle, int i) {
        this.mService.requestInstantAppResolutionPhaseTwo(auxiliaryResolveInfo, intent, str, str2, str3, z, bundle, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void grantImplicitAccess(int i, android.content.Intent intent, int i2, int i3, boolean z) {
        grantImplicitAccess(i, intent, i2, i3, z, false);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void grantImplicitAccess(int i, android.content.Intent intent, int i2, int i3, boolean z, boolean z2) {
        this.mService.grantImplicitAccess(snapshot(), i, intent, i2, i3, z, z2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isInstantAppInstallerComponent(android.content.ComponentName componentName) {
        android.content.pm.ActivityInfo activityInfo = this.mService.mInstantAppInstallerActivity;
        return activityInfo != null && activityInfo.getComponentName().equals(componentName);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void pruneInstantApps() {
        getInstantAppRegistry().pruneInstantApps(snapshot());
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final java.lang.String getSetupWizardPackageName() {
        return this.mService.mSetupWizardPackage;
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.pm.ResolveInfo resolveIntent(android.content.Intent intent, java.lang.String str, long j, long j2, int i, boolean z, int i2) {
        return getResolveIntentHelper().resolveIntentInternal(snapshot(), intent, str, j, j2, i, z, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.pm.ResolveInfo resolveIntentExported(android.content.Intent intent, java.lang.String str, long j, long j2, int i, boolean z, int i2, int i3) {
        return getResolveIntentHelper().resolveIntentInternal(snapshot(), intent, str, j, j2, i, z, i2, true, i3);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.pm.ResolveInfo resolveService(android.content.Intent intent, java.lang.String str, long j, int i, int i2) {
        return getResolveIntentHelper().resolveServiceInternal(snapshot(), intent, str, j, i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.content.pm.ProviderInfo resolveContentProvider(java.lang.String str, long j, int i, int i2) {
        return snapshot().resolveContentProvider(str, j, i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final int getUidTargetSdkVersion(int i) {
        return snapshot().getUidTargetSdkVersion(i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final int getPackageTargetSdkVersion(java.lang.String str) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal != null && packageStateInternal.getPkg() != null) {
            return packageStateInternal.getPkg().getTargetSdkVersion();
        }
        return 10000;
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean canAccessInstantApps(int i, int i2) {
        return snapshot().canViewInstantApps(i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean canAccessComponent(int i, @android.annotation.NonNull android.content.ComponentName componentName, int i2) {
        return snapshot().canAccessComponent(i, componentName, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean hasInstantApplicationMetadata(java.lang.String str, int i) {
        return getInstantAppRegistry().hasInstantApplicationMetadata(str, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.util.SparseArray<java.lang.String> getAppsWithSharedUserIds() {
        return snapshot().getAppsWithSharedUserIds();
    }

    @Override // android.content.pm.PackageManagerInternal
    @android.annotation.NonNull
    @java.lang.Deprecated
    public final java.lang.String[] getSharedUserPackagesForPackage(java.lang.String str, int i) {
        return snapshot().getSharedUserPackagesForPackage(str, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.util.ArrayMap<java.lang.String, android.content.pm.ProcessInfo> getProcessesForUid(int i) {
        return snapshot().getProcessesForUid(i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final int[] getPermissionGids(java.lang.String str, int i) {
        return getPermissionManager().getPermissionGids(str, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void freeStorage(java.lang.String str, long j, int i) throws java.io.IOException {
        this.mService.freeStorage(str, j, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void freeAllAppCacheAboveQuota(@android.annotation.NonNull java.lang.String str) throws java.io.IOException {
        this.mService.freeAllAppCacheAboveQuota(str);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void forEachPackageSetting(java.util.function.Consumer<com.android.server.pm.PackageSetting> consumer) {
        this.mService.forEachPackageSetting(consumer);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void forEachPackageState(java.util.function.Consumer<com.android.server.pm.pkg.PackageStateInternal> consumer) {
        this.mService.forEachPackageState(snapshot(), consumer);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void forEachPackage(java.util.function.Consumer<com.android.server.pm.pkg.AndroidPackage> consumer) {
        this.mService.forEachPackage(snapshot(), consumer);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void forEachInstalledPackage(@android.annotation.NonNull java.util.function.Consumer<com.android.server.pm.pkg.AndroidPackage> consumer, int i) {
        this.mService.forEachInstalledPackage(snapshot(), consumer, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.util.ArraySet<java.lang.String> getEnabledComponents(java.lang.String str, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return new android.util.ArraySet<>();
        }
        return packageStateInternal.getUserStateOrDefault(i).m6429getEnabledComponents();
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final android.util.ArraySet<java.lang.String> getDisabledComponents(java.lang.String str, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return new android.util.ArraySet<>();
        }
        return packageStateInternal.getUserStateOrDefault(i).m6428getDisabledComponents();
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final int getApplicationEnabledState(java.lang.String str, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return 0;
        }
        return packageStateInternal.getUserStateOrDefault(i).getEnabledState();
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final int getComponentEnabledSetting(@android.annotation.NonNull android.content.ComponentName componentName, int i, int i2) {
        return snapshot().getComponentEnabledSettingInternal(componentName, i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void setEnableRollbackCode(int i, int i2) {
        this.mService.setEnableRollbackCode(i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void finishPackageInstall(int i, boolean z) {
        this.mService.finishPackageInstall(i, z);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isApexPackage(java.lang.String str) {
        return snapshot().isApexPackage(str);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final java.util.List<java.lang.String> getApksInApex(java.lang.String str) {
        return getApexManager().getApksInApex(str);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isCallerInstallerOfRecord(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        return snapshot().isCallerInstallerOfRecord(androidPackage, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final java.util.List<java.lang.String> getMimeGroup(java.lang.String str, java.lang.String str2) {
        return this.mService.getMimeGroupInternal(snapshot(), str, str2);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isSystemPackage(@android.annotation.NonNull java.lang.String str) {
        return str.equals(this.mService.ensureSystemPackageName(snapshot(), str));
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void unsuspendAdminSuspendedPackages(int i) {
        this.mService.unsuspendForSuspendingPackage(snapshot(), com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isAdminSuspendingAnyPackages(int i) {
        return snapshot().isSuspendingAnyPackages(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, i, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void requestChecksums(@android.annotation.NonNull java.lang.String str, boolean z, int i, int i2, @android.annotation.Nullable java.util.List list, @android.annotation.NonNull android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.os.Handler handler) {
        this.mService.requestChecksumsInternal(snapshot(), str, z, i, i2, list, iOnChecksumsReadyListener, i3, executor, handler);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final boolean isPackageFrozen(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        return snapshot().getPackageStartability(this.mService.getSafeMode(), str, i, i2) == 3;
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final long deleteOatArtifactsOfPackage(java.lang.String str) {
        return this.mService.deleteOatArtifactsOfPackage(snapshot(), str);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void reconcileAppsData(int i, int i2, boolean z) {
        getAppDataHelper().reconcileAppsData(i, i2, z);
    }

    @Override // android.content.pm.PackageManagerInternal
    @android.annotation.NonNull
    public android.util.ArraySet<com.android.server.pm.pkg.PackageStateInternal> getSharedUserPackages(int i) {
        return snapshot().getSharedUserPackages(i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @android.annotation.Nullable
    public com.android.server.pm.pkg.SharedUserApi getSharedUserApi(int i) {
        return snapshot().getSharedUser(i);
    }

    @Override // android.content.pm.PackageManagerInternal
    public boolean isUidPrivileged(int i) {
        return snapshot().isUidPrivileged(i);
    }

    @Override // android.content.pm.PackageManagerInternal
    public int checkUidSignaturesForAllUsers(int i, int i2) {
        return snapshot().checkUidSignaturesForAllUsers(i, i2);
    }

    @Override // android.content.pm.PackageManagerInternal
    public void setPackageStoppedState(@android.annotation.NonNull java.lang.String str, boolean z, int i) {
        this.mService.setPackageStoppedState(snapshot(), str, z, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    public void notifyComponentUsed(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull java.lang.String str3) {
        this.mService.notifyComponentUsed(snapshot(), str, i, str2, str3);
    }

    @Override // android.content.pm.PackageManagerInternal
    public boolean isPackageQuarantined(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return snapshot().isPackageQuarantinedForUser(str, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    public boolean isPackageStopped(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return snapshot().isPackageStoppedForUser(str, i);
    }

    @Override // android.content.pm.PackageManagerInternal
    @android.annotation.NonNull
    @java.lang.Deprecated
    public final com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState recordInitialState() {
        return this.mService.recordInitialState();
    }

    @Override // android.content.pm.PackageManagerInternal
    @android.annotation.Nullable
    @java.lang.Deprecated
    public final com.android.server.pm.pkg.mutate.PackageStateMutator.Result commitPackageStateMutation(@android.annotation.Nullable com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState initialState, @android.annotation.NonNull java.util.function.Consumer<com.android.server.pm.pkg.mutate.PackageStateMutator> consumer) {
        return this.mService.commitPackageStateMutation(initialState, consumer);
    }

    @Override // android.content.pm.PackageManagerInternal
    @java.lang.Deprecated
    public final void shutdown() {
        this.mService.shutdown();
    }
}
