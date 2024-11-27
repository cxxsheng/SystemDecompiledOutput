package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public interface PermissionManagerServiceInterface extends android.permission.PermissionManagerInternal {
    boolean addAllowlistedRestrictedPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2);

    void addOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener);

    boolean addPermission(android.content.pm.PermissionInfo permissionInfo, boolean z);

    int checkPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i);

    int checkUidPermission(int i, java.lang.String str, java.lang.String str2);

    void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr);

    java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getAllAppOpPermissionPackages();

    java.util.List<android.content.pm.PermissionGroupInfo> getAllPermissionGroups(int i);

    java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> getAllPermissionStates(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i);

    @android.annotation.NonNull
    java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtection(int i);

    @android.annotation.NonNull
    java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtectionFlags(int i);

    java.util.List<java.lang.String> getAllowlistedRestrictedPermissions(@android.annotation.NonNull java.lang.String str, int i, int i2);

    @android.annotation.NonNull
    java.lang.String[] getAppOpPermissionPackages(@android.annotation.NonNull java.lang.String str);

    @android.annotation.Nullable
    java.lang.String getDefaultPermissionGrantFingerprint(int i);

    @android.annotation.NonNull
    int[] getGidsForUid(int i);

    @android.annotation.NonNull
    java.util.Set<java.lang.String> getGrantedPermissions(@android.annotation.NonNull java.lang.String str, int i);

    @android.annotation.NonNull
    java.util.Set<java.lang.String> getInstalledPermissions(@android.annotation.NonNull java.lang.String str);

    @android.annotation.NonNull
    com.android.server.pm.permission.LegacyPermissionState getLegacyPermissionState(int i);

    @android.annotation.NonNull
    java.util.List<com.android.server.pm.permission.LegacyPermission> getLegacyPermissions();

    int getPermissionFlags(java.lang.String str, java.lang.String str2, java.lang.String str3, int i);

    @android.annotation.NonNull
    int[] getPermissionGids(@android.annotation.NonNull java.lang.String str, int i);

    android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i);

    android.content.pm.PermissionInfo getPermissionInfo(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2);

    @android.annotation.Nullable
    com.android.server.pm.permission.Permission getPermissionTEMP(@android.annotation.NonNull java.lang.String str);

    java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> getSplitPermissions();

    void grantRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i);

    boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2, java.lang.String str3, int i);

    boolean isPermissionsReviewRequired(@android.annotation.NonNull java.lang.String str, int i);

    void onPackageAdded(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, boolean z, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage);

    void onPackageInstalled(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int i2);

    void onPackageRemoved(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage);

    void onPackageUninstalled(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.AndroidPackage> list, int i2);

    void onStorageVolumeMounted(@android.annotation.NonNull java.lang.String str, boolean z);

    void onSystemReady();

    void onUserCreated(int i);

    void onUserRemoved(int i);

    java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup(java.lang.String str, int i);

    void readLegacyPermissionStateTEMP();

    void readLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings);

    boolean removeAllowlistedRestrictedPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2);

    void removeOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener);

    void removePermission(java.lang.String str);

    void resetRuntimePermissions(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i);

    void resetRuntimePermissionsForUser(int i);

    void revokePostNotificationPermissionWithoutKillForTest(java.lang.String str, int i);

    void revokeRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4);

    void setDefaultPermissionGrantFingerprint(@android.annotation.NonNull java.lang.String str, int i);

    boolean shouldShowRequestPermissionRationale(java.lang.String str, java.lang.String str2, java.lang.String str3, int i);

    void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, java.lang.String str3, int i3);

    void updatePermissionFlagsForAllApps(int i, int i2, int i3);

    void writeLegacyPermissionStateTEMP();

    void writeLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings);
}
