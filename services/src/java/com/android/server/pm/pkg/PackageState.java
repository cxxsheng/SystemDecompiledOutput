package com.android.server.pm.pkg;

@android.processor.immutability.Immutable
@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public interface PackageState {
    @android.annotation.Nullable
    com.android.server.pm.pkg.AndroidPackage getAndroidPackage();

    @android.annotation.Nullable
    java.lang.String getApexModuleName();

    int getAppId();

    int getCategoryOverride();

    @android.annotation.Nullable
    java.lang.String getCpuAbiOverride();

    int getHiddenApiEnforcementPolicy();

    long getLastModifiedTime();

    @android.annotation.NonNull
    @android.processor.immutability.Immutable.Ignore
    long[] getLastPackageUsageTime();

    long getLastUpdateTime();

    @android.annotation.NonNull
    java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getMimeGroups();

    @android.annotation.NonNull
    java.lang.String getPackageName();

    @android.annotation.NonNull
    java.io.File getPath();

    @android.annotation.Nullable
    java.lang.String getPrimaryCpuAbi();

    @android.processor.immutability.Immutable.Ignore
    @android.annotation.Nullable
    byte[] getRestrictUpdateHash();

    @android.annotation.Nullable
    java.lang.String getSeInfo();

    @android.annotation.Nullable
    java.lang.String getSecondaryCpuAbi();

    @android.annotation.NonNull
    java.util.List<com.android.server.pm.pkg.SharedLibrary> getSharedLibraryDependencies();

    int getSharedUserAppId();

    @android.annotation.NonNull
    @android.processor.immutability.Immutable.Ignore
    android.content.pm.SigningInfo getSigningInfo();

    @android.annotation.NonNull
    com.android.server.pm.pkg.PackageUserState getStateForUser(@android.annotation.NonNull android.os.UserHandle userHandle);

    int getTargetSdkVersion();

    @android.annotation.NonNull
    @android.processor.immutability.Immutable.Ignore
    android.util.SparseArray<? extends com.android.server.pm.pkg.PackageUserState> getUserStates();

    @android.annotation.NonNull
    java.util.List<java.lang.String> getUsesLibraryFiles();

    @android.annotation.NonNull
    @android.processor.immutability.Immutable.Ignore
    java.lang.String[] getUsesSdkLibraries();

    @android.annotation.NonNull
    @android.processor.immutability.Immutable.Ignore
    boolean[] getUsesSdkLibrariesOptional();

    @android.annotation.NonNull
    @android.processor.immutability.Immutable.Ignore
    long[] getUsesSdkLibrariesVersionsMajor();

    @android.annotation.NonNull
    @android.processor.immutability.Immutable.Ignore
    java.lang.String[] getUsesStaticLibraries();

    @android.annotation.NonNull
    @android.processor.immutability.Immutable.Ignore
    long[] getUsesStaticLibrariesVersions();

    long getVersionCode();

    @android.annotation.Nullable
    java.lang.String getVolumeUuid();

    boolean hasSharedUser();

    boolean isApex();

    boolean isApkInUpdatedApex();

    boolean isDefaultToDeviceProtectedStorage();

    boolean isExternalStorage();

    boolean isForceQueryableOverride();

    boolean isHiddenUntilInstalled();

    boolean isInstallPermissionsFixed();

    boolean isOdm();

    boolean isOem();

    boolean isPendingRestore();

    boolean isPersistent();

    boolean isPrivileged();

    boolean isProduct();

    boolean isRequiredForSystemUser();

    boolean isScannedAsStoppedSystemApp();

    boolean isSystem();

    boolean isSystemExt();

    boolean isUpdateAvailable();

    boolean isUpdatedSystemApp();

    boolean isVendor();

    @android.annotation.NonNull
    default com.android.server.pm.pkg.PackageUserState getUserStateOrDefault(int i) {
        com.android.server.pm.pkg.PackageUserState packageUserState = getUserStates().get(i);
        return packageUserState == null ? com.android.server.pm.pkg.PackageUserState.DEFAULT : packageUserState;
    }
}
