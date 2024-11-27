package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
public interface PackageStateInternal extends com.android.server.pm.pkg.PackageState {
    @android.annotation.Nullable
    java.lang.String getAppMetadataFilePath();

    int getAppMetadataSource();

    @android.annotation.NonNull
    java.util.UUID getDomainSetId();

    int getFlags();

    @android.annotation.NonNull
    com.android.server.pm.InstallSource getInstallSource();

    @android.annotation.NonNull
    com.android.server.pm.PackageKeySetData getKeySetData();

    @android.annotation.NonNull
    com.android.server.pm.permission.LegacyPermissionState getLegacyPermissionState();

    long getLoadingCompletedTime();

    float getLoadingProgress();

    @android.annotation.Nullable
    java.util.Set<java.io.File> getOldPaths();

    @android.annotation.NonNull
    java.lang.String getPathString();

    @android.annotation.Nullable
    com.android.internal.pm.parsing.pkg.AndroidPackageInternal getPkg();

    @android.annotation.Nullable
    @java.lang.Deprecated
    java.lang.String getPrimaryCpuAbiLegacy();

    int getPrivateFlags();

    @android.annotation.Nullable
    java.lang.String getRealName();

    @android.annotation.Nullable
    java.lang.String getSecondaryCpuAbiLegacy();

    @android.annotation.NonNull
    android.content.pm.SigningDetails getSigningDetails();

    @android.annotation.NonNull
    com.android.server.pm.pkg.PackageStateUnserialized getTransientState();

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    android.util.SparseArray<? extends com.android.server.pm.pkg.PackageUserStateInternal> getUserStates();

    boolean isLoading();

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    default com.android.server.pm.pkg.PackageUserStateInternal getUserStateOrDefault(int i) {
        com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal = getUserStates().get(i);
        return packageUserStateInternal == null ? com.android.server.pm.pkg.PackageUserStateInternal.DEFAULT : packageUserStateInternal;
    }
}
