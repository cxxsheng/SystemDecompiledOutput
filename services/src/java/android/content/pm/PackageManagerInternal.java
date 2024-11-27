package android.content.pm;

/* loaded from: classes.dex */
public abstract class PackageManagerInternal {
    public static final int ENABLE_ROLLBACK_FAILED = -1;
    public static final int ENABLE_ROLLBACK_SUCCEEDED = 1;
    public static final java.lang.String EXTRA_ENABLE_ROLLBACK_SESSION_ID = "android.content.pm.extra.ENABLE_ROLLBACK_SESSION_ID";
    public static final java.lang.String EXTRA_ENABLE_ROLLBACK_TOKEN = "android.content.pm.extra.ENABLE_ROLLBACK_TOKEN";
    public static final int INTEGRITY_VERIFICATION_ALLOW = 1;
    public static final int INTEGRITY_VERIFICATION_REJECT = 0;
    public static final int RESOLVE_NON_BROWSER_ONLY = 1;
    public static final int RESOLVE_NON_RESOLVER_ONLY = 2;

    public interface ExternalSourcesPolicy {
        public static final int USER_BLOCKED = 1;
        public static final int USER_DEFAULT = 2;
        public static final int USER_TRUSTED = 0;

        int getPackageTrustedToInstallApps(java.lang.String str, int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface IntegrityVerificationResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PrivateResolveFlags {
    }

    public abstract void addIsolatedUid(int i, int i2);

    public abstract boolean canAccessComponent(int i, android.content.ComponentName componentName, int i2);

    public abstract boolean canAccessInstantApps(int i, int i2);

    public abstract boolean canQueryPackage(int i, @android.annotation.Nullable java.lang.String str);

    public abstract int checkUidSignaturesForAllUsers(int i, int i2);

    public abstract void clearBlockUninstallForUser(int i);

    @android.annotation.Nullable
    public abstract com.android.server.pm.pkg.mutate.PackageStateMutator.Result commitPackageStateMutation(@android.annotation.Nullable com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState initialState, @android.annotation.NonNull java.util.function.Consumer<com.android.server.pm.pkg.mutate.PackageStateMutator> consumer);

    public abstract long deleteOatArtifactsOfPackage(java.lang.String str);

    public abstract boolean filterAppAccess(int i, int i2);

    public abstract boolean filterAppAccess(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2);

    public abstract boolean filterAppAccess(@android.annotation.NonNull java.lang.String str, int i, int i2, boolean z);

    public abstract void finishPackageInstall(int i, boolean z);

    public abstract void flushPackageRestrictions(int i);

    public abstract void forEachInstalledPackage(@android.annotation.NonNull java.util.function.Consumer<com.android.server.pm.pkg.AndroidPackage> consumer, int i);

    public abstract void forEachPackage(java.util.function.Consumer<com.android.server.pm.pkg.AndroidPackage> consumer);

    public abstract void forEachPackageSetting(java.util.function.Consumer<com.android.server.pm.PackageSetting> consumer);

    public abstract void forEachPackageState(java.util.function.Consumer<com.android.server.pm.pkg.PackageStateInternal> consumer);

    public abstract void freeAllAppCacheAboveQuota(@android.annotation.NonNull java.lang.String str) throws java.io.IOException;

    public abstract void freeStorage(java.lang.String str, long j, int i) throws java.io.IOException;

    public abstract android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, long j, int i, int i2);

    @android.annotation.Nullable
    public abstract com.android.server.pm.pkg.AndroidPackage getAndroidPackage(@android.annotation.NonNull java.lang.String str);

    public abstract java.util.List<java.lang.String> getApksInApex(java.lang.String str);

    public abstract int getApplicationEnabledState(java.lang.String str, int i);

    public abstract android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, long j, int i, int i2);

    public abstract android.util.SparseArray<java.lang.String> getAppsWithSharedUserIds();

    public abstract long getCeDataInode(java.lang.String str, int i);

    public abstract int getComponentEnabledSetting(@android.annotation.NonNull android.content.ComponentName componentName, int i, int i2);

    public abstract android.content.ComponentName getDefaultHomeActivity(int i);

    public abstract android.util.ArraySet<java.lang.String> getDisabledComponents(java.lang.String str, int i);

    @android.annotation.Nullable
    public abstract com.android.server.pm.pkg.PackageStateInternal getDisabledSystemPackage(@android.annotation.NonNull java.lang.String str);

    @android.annotation.Nullable
    public abstract java.lang.String getDisabledSystemPackageName(@android.annotation.NonNull java.lang.String str);

    public abstract int getDistractingPackageRestrictions(java.lang.String str, int i);

    public abstract int[] getDistractingPackageRestrictionsAsUser(@android.annotation.NonNull java.lang.String[] strArr, int i);

    public abstract com.android.server.pm.dex.DynamicCodeLogger getDynamicCodeLogger();

    public abstract android.util.ArraySet<java.lang.String> getEnabledComponents(java.lang.String str, int i);

    public abstract android.content.pm.ParceledListSlice<android.content.pm.PackageInstaller.SessionInfo> getHistoricalSessions(int i);

    public abstract android.content.ComponentName getHomeActivitiesAsUser(java.util.List<android.content.pm.ResolveInfo> list, int i);

    public abstract android.content.pm.IncrementalStatesInfo getIncrementalStatesInfo(java.lang.String str, int i, int i2);

    public abstract java.util.List<android.content.pm.ApplicationInfo> getInstalledApplications(long j, int i, int i2);

    public abstract java.util.List<android.content.pm.ApplicationInfo> getInstalledApplicationsCrossUser(long j, int i, int i2);

    @android.annotation.Nullable
    public abstract java.lang.String getInstantAppPackageName(int i);

    @android.annotation.NonNull
    public abstract java.lang.String[] getKnownPackageNames(int i, int i2);

    public abstract com.android.server.pm.permission.LegacyPermissionSettings getLegacyPermissions();

    public abstract java.lang.Object getLegacyPermissionsState(int i);

    public abstract int getLegacyPermissionsVersion(int i);

    public abstract java.util.List<java.lang.String> getMimeGroup(java.lang.String str, java.lang.String str2);

    public abstract java.lang.String getNameForUid(int i);

    public abstract java.util.List<android.content.pm.PackageInfo> getOverlayPackages(int i);

    @android.annotation.Nullable
    public abstract com.android.server.pm.pkg.AndroidPackage getPackage(int i);

    @android.annotation.Nullable
    public abstract com.android.server.pm.pkg.AndroidPackage getPackage(@android.annotation.NonNull java.lang.String str);

    @android.annotation.NonNull
    public abstract com.android.server.pm.PackageArchiver getPackageArchiver();

    public abstract android.content.pm.PackageInfo getPackageInfo(java.lang.String str, long j, int i, int i2);

    @android.annotation.NonNull
    @java.lang.Deprecated
    public abstract com.android.server.pm.PackageList getPackageList(@android.annotation.Nullable android.content.pm.PackageManagerInternal.PackageListObserver packageListObserver);

    @android.annotation.Nullable
    public abstract com.android.server.pm.pkg.PackageStateInternal getPackageStateInternal(@android.annotation.NonNull java.lang.String str);

    @android.annotation.NonNull
    public abstract android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> getPackageStates();

    public abstract int getPackageTargetSdkVersion(java.lang.String str);

    public abstract int getPackageUid(java.lang.String str, long j, int i);

    @android.annotation.NonNull
    public abstract java.util.List<com.android.server.pm.pkg.AndroidPackage> getPackagesForAppId(int i);

    public abstract int[] getPermissionGids(java.lang.String str, int i);

    public abstract android.util.ArrayMap<java.lang.String, android.content.pm.ProcessInfo> getProcessesForUid(int i);

    public abstract java.lang.String getSetupWizardPackageName();

    @android.annotation.Nullable
    public abstract com.android.server.pm.pkg.SharedUserApi getSharedUserApi(int i);

    @android.annotation.NonNull
    public abstract android.util.ArraySet<com.android.server.pm.pkg.PackageStateInternal> getSharedUserPackages(int i);

    @android.annotation.NonNull
    public abstract java.lang.String[] getSharedUserPackagesForPackage(@android.annotation.NonNull java.lang.String str, int i);

    @android.annotation.Nullable
    public abstract android.content.pm.SuspendDialogInfo getSuspendedDialogInfo(java.lang.String str, android.content.pm.UserPackage userPackage, int i);

    public abstract android.os.Bundle getSuspendedPackageLauncherExtras(java.lang.String str, int i);

    public abstract android.content.pm.UserPackage getSuspendingPackage(java.lang.String str, int i);

    public abstract android.content.ComponentName getSystemUiServiceComponent();

    public abstract java.util.List<java.lang.String> getTargetPackageNames(int i);

    public abstract int getUidTargetSdkVersion(int i);

    @android.annotation.Nullable
    public abstract int[] getVisibilityAllowList(@android.annotation.NonNull java.lang.String str, int i);

    public abstract void grantImplicitAccess(int i, android.content.Intent intent, int i2, int i3, boolean z);

    public abstract void grantImplicitAccess(int i, android.content.Intent intent, int i2, int i3, boolean z, boolean z2);

    public abstract boolean hasInstantApplicationMetadata(java.lang.String str, int i);

    public abstract boolean hasSignatureCapability(int i, int i2, @android.content.pm.SigningDetails.CertCapabilities int i3);

    public abstract boolean isAdminSuspendingAnyPackages(int i);

    public abstract boolean isApexPackage(java.lang.String str);

    public abstract boolean isCallerInstallerOfRecord(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i);

    public abstract boolean isDataRestoreSafe(@android.annotation.NonNull android.content.pm.Signature signature, @android.annotation.NonNull java.lang.String str);

    public abstract boolean isDataRestoreSafe(@android.annotation.NonNull byte[] bArr, @android.annotation.NonNull java.lang.String str);

    public abstract boolean isEnabledAndMatches(@android.annotation.NonNull com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent, long j, int i);

    public abstract boolean isInstantApp(java.lang.String str, int i);

    public abstract boolean isInstantAppInstallerComponent(android.content.ComponentName componentName);

    public abstract boolean isPackageDataProtected(int i, java.lang.String str);

    public abstract boolean isPackageEphemeral(int i, java.lang.String str);

    public abstract boolean isPackageFrozen(@android.annotation.NonNull java.lang.String str, int i, int i2);

    public abstract boolean isPackagePersistent(java.lang.String str);

    public abstract boolean isPackageQuarantined(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract boolean isPackageStateProtected(java.lang.String str, int i);

    public abstract boolean isPackageStopped(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract boolean isPackageSuspended(java.lang.String str, int i);

    public abstract boolean isPermissionUpgradeNeeded(int i);

    public abstract boolean isPermissionsReviewRequired(java.lang.String str, int i);

    public abstract boolean isPlatformSigned(java.lang.String str);

    public abstract boolean isResolveActivityComponent(@android.annotation.NonNull android.content.pm.ComponentInfo componentInfo);

    public abstract boolean isSameApp(java.lang.String str, int i, int i2);

    public abstract boolean isSameApp(java.lang.String str, long j, int i, int i2);

    public abstract boolean isSystemPackage(@android.annotation.NonNull java.lang.String str);

    public abstract boolean isUidPrivileged(int i);

    public abstract boolean isUpgradingFromLowerThan(int i);

    @java.lang.Deprecated
    public abstract void legacyDumpProfiles(@android.annotation.NonNull java.lang.String str, boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException;

    @java.lang.Deprecated
    public abstract void legacyForceDexOpt(@android.annotation.NonNull java.lang.String str) throws com.android.server.pm.Installer.LegacyDexoptDisabledException;

    @java.lang.Deprecated
    public abstract void legacyReconcileSecondaryDexFiles(java.lang.String str) throws com.android.server.pm.Installer.LegacyDexoptDisabledException;

    public abstract void migrateLegacyObbData();

    public abstract void notifyComponentUsed(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull java.lang.String str3);

    public abstract void notifyPackageUse(java.lang.String str, int i);

    public abstract void onPackageProcessKilledForUninstall(java.lang.String str);

    public abstract void pruneInstantApps();

    public abstract java.util.List<android.content.pm.ResolveInfo> queryIntentActivities(android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i, int i2);

    public abstract java.util.List<android.content.pm.ResolveInfo> queryIntentReceivers(android.content.Intent intent, java.lang.String str, long j, int i, int i2, boolean z);

    public abstract java.util.List<android.content.pm.ResolveInfo> queryIntentServices(android.content.Intent intent, long j, int i, int i2);

    public abstract void reconcileAppsData(int i, int i2, boolean z);

    @android.annotation.NonNull
    public abstract com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState recordInitialState();

    public abstract boolean registerInstalledLoadingProgressCallback(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageManagerInternal.InstalledLoadingProgressCallback installedLoadingProgressCallback, int i);

    public abstract void removeAllDistractingPackageRestrictions(int i);

    public abstract void removeAllNonSystemPackageSuspensions(int i);

    public abstract void removeDistractingPackageRestrictions(java.lang.String str, int i);

    public abstract void removeIsolatedUid(int i);

    @android.annotation.Nullable
    public abstract java.lang.String removeLegacyDefaultBrowserPackageName(int i);

    public abstract void removeNonSystemPackageSuspensions(java.lang.String str, int i);

    @java.lang.Deprecated
    public abstract void removePackageListObserver(@android.annotation.NonNull android.content.pm.PackageManagerInternal.PackageListObserver packageListObserver);

    public abstract void requestChecksums(@android.annotation.NonNull java.lang.String str, boolean z, int i, int i2, @android.annotation.Nullable java.util.List list, @android.annotation.NonNull android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.os.Handler handler);

    public abstract void requestInstantAppResolutionPhaseTwo(android.content.pm.AuxiliaryResolveInfo auxiliaryResolveInfo, android.content.Intent intent, java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, boolean z, android.os.Bundle bundle, int i);

    public abstract android.content.pm.ProviderInfo resolveContentProvider(java.lang.String str, long j, int i, int i2);

    public abstract android.content.pm.ResolveInfo resolveIntent(android.content.Intent intent, java.lang.String str, long j, long j2, int i, boolean z, int i2);

    public abstract android.content.pm.ResolveInfo resolveIntentExported(android.content.Intent intent, java.lang.String str, long j, long j2, int i, boolean z, int i2, int i3);

    public abstract android.content.pm.ResolveInfo resolveService(android.content.Intent intent, java.lang.String str, long j, int i, int i2);

    public abstract void sendPackageDataClearedBroadcast(@android.annotation.NonNull java.lang.String str, int i, int i2, boolean z, boolean z2);

    public abstract void sendPackageRestartedBroadcast(@android.annotation.NonNull java.lang.String str, int i, int i2);

    public abstract void setDeviceAndProfileOwnerPackages(int i, java.lang.String str, android.util.SparseArray<java.lang.String> sparseArray);

    public abstract void setEnableRollbackCode(int i, int i2);

    public abstract void setEnabledOverlayPackages(int i, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, android.content.pm.overlay.OverlayPaths> arrayMap, @android.annotation.NonNull java.util.Set<java.lang.String> set, @android.annotation.NonNull java.util.Set<java.lang.String> set2);

    public abstract void setExternalSourcesPolicy(android.content.pm.PackageManagerInternal.ExternalSourcesPolicy externalSourcesPolicy);

    public abstract void setIntegrityVerificationResult(int i, int i2);

    public abstract void setKeepUninstalledPackages(java.util.List<java.lang.String> list);

    public abstract void setOwnerProtectedPackages(int i, @android.annotation.Nullable java.util.List<java.lang.String> list);

    public abstract void setPackageStoppedState(@android.annotation.NonNull java.lang.String str, boolean z, int i);

    public abstract java.lang.String[] setPackagesSuspendedByAdmin(int i, @android.annotation.NonNull java.lang.String[] strArr, boolean z);

    public abstract void setVisibilityLogging(java.lang.String str, boolean z);

    public abstract void shutdown();

    @android.annotation.NonNull
    public abstract com.android.server.pm.snapshot.PackageDataSnapshot snapshot();

    public abstract void uninstallApex(java.lang.String str, long j, int i, android.content.IntentSender intentSender, int i2);

    public abstract void unsuspendAdminSuspendedPackages(int i);

    public abstract void updateRuntimePermissionsFingerprint(int i);

    public abstract boolean userNeedsBadging(int i);

    public abstract boolean wasPackageEverLaunched(java.lang.String str, int i);

    public abstract void writePermissionSettings(@android.annotation.NonNull int[] iArr, boolean z);

    public abstract void writeSettings(boolean z);

    @java.lang.Deprecated
    public interface PackageListObserver {
        default void onPackageAdded(@android.annotation.NonNull java.lang.String str, int i) {
        }

        default void onPackageChanged(@android.annotation.NonNull java.lang.String str, int i) {
        }

        default void onPackageRemoved(@android.annotation.NonNull java.lang.String str, int i) {
        }
    }

    public void onDefaultSmsAppChanged(java.lang.String str, int i) {
    }

    public void onDefaultSimCallManagerAppChanged(java.lang.String str, int i) {
    }

    @android.annotation.NonNull
    public com.android.server.pm.PackageList getPackageList() {
        return getPackageList(null);
    }

    public boolean filterAppAccess(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        return filterAppAccess(str, i, i2, true);
    }

    public static abstract class InstalledLoadingProgressCallback {
        final android.content.pm.PackageManagerInternal.InstalledLoadingProgressCallback.LoadingProgressCallbackBinder mBinder = new android.content.pm.PackageManagerInternal.InstalledLoadingProgressCallback.LoadingProgressCallbackBinder();
        final java.util.concurrent.Executor mExecutor;

        public abstract void onLoadingProgressChanged(float f);

        public InstalledLoadingProgressCallback(@android.annotation.Nullable android.os.Handler handler) {
            this.mExecutor = new android.os.HandlerExecutor(handler == null ? new android.os.Handler(android.os.Looper.getMainLooper()) : handler);
        }

        @android.annotation.NonNull
        public final android.os.IBinder getBinder() {
            return this.mBinder;
        }

        private class LoadingProgressCallbackBinder extends android.content.pm.IPackageLoadingProgressCallback.Stub {
            private LoadingProgressCallbackBinder() {
            }

            public void onPackageLoadingProgressChanged(float f) {
                android.content.pm.PackageManagerInternal.InstalledLoadingProgressCallback.this.mExecutor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.BiConsumer() { // from class: android.content.pm.PackageManagerInternal$InstalledLoadingProgressCallback$LoadingProgressCallbackBinder$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((android.content.pm.PackageManagerInternal.InstalledLoadingProgressCallback) obj).onLoadingProgressChanged(((java.lang.Float) obj2).floatValue());
                    }
                }, android.content.pm.PackageManagerInternal.InstalledLoadingProgressCallback.this, java.lang.Float.valueOf(f)).recycleOnUse());
            }
        }
    }
}
