package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public class PermissionManagerServiceLoggingDecorator implements com.android.server.pm.permission.PermissionManagerServiceInterface {
    private static final java.lang.String LOG_TAG = com.android.server.pm.permission.PermissionManagerServiceLoggingDecorator.class.getSimpleName();

    @android.annotation.NonNull
    private final com.android.server.pm.permission.PermissionManagerServiceInterface mService;

    public PermissionManagerServiceLoggingDecorator(@android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInterface permissionManagerServiceInterface) {
        this.mService = permissionManagerServiceInterface;
    }

    @android.annotation.Nullable
    public byte[] backupRuntimePermissions(int i) {
        android.util.Log.i(LOG_TAG, "backupRuntimePermissions(userId = " + i + ")");
        return this.mService.backupRuntimePermissions(i);
    }

    public void restoreRuntimePermissions(@android.annotation.NonNull byte[] bArr, int i) {
        android.util.Log.i(LOG_TAG, "restoreRuntimePermissions(backup = " + bArr + ", userId = " + i + ")");
        this.mService.restoreRuntimePermissions(bArr, i);
    }

    public void restoreDelayedRuntimePermissions(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.Log.i(LOG_TAG, "restoreDelayedRuntimePermissions(packageName = " + str + ", userId = " + i + ")");
        this.mService.restoreDelayedRuntimePermissions(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        android.util.Log.i(LOG_TAG, "dump(fd = " + fileDescriptor + ", pw = " + printWriter + ", args = " + java.util.Arrays.toString(strArr) + ")");
        this.mService.dump(fileDescriptor, printWriter, strArr);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<android.content.pm.PermissionGroupInfo> getAllPermissionGroups(int i) {
        android.util.Log.i(LOG_TAG, "getAllPermissionGroups(flags = " + i + ")");
        return this.mService.getAllPermissionGroups(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) {
        android.util.Log.i(LOG_TAG, "getPermissionGroupInfo(groupName = " + str + ", flags = " + i + ")");
        return this.mService.getPermissionGroupInfo(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public android.content.pm.PermissionInfo getPermissionInfo(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        android.util.Log.i(LOG_TAG, "getPermissionInfo(permName = " + str + ", flags = " + i + ", opPackageName = " + str2 + ")");
        return this.mService.getPermissionInfo(str, i, str2);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup(java.lang.String str, int i) {
        android.util.Log.i(LOG_TAG, "queryPermissionsByGroup(groupName = " + str + ", flags = " + i + ")");
        return this.mService.queryPermissionsByGroup(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addPermission(android.content.pm.PermissionInfo permissionInfo, boolean z) {
        android.util.Log.i(LOG_TAG, "addPermission(info = " + permissionInfo + ", async = " + z + ")");
        return this.mService.addPermission(permissionInfo, z);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removePermission(java.lang.String str) {
        android.util.Log.i(LOG_TAG, "removePermission(permName = " + str + ")");
        this.mService.removePermission(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int getPermissionFlags(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        android.util.Log.i(LOG_TAG, "getPermissionFlags(packageName = " + str + ", permName = " + str2 + ", deviceId = " + str3 + ", userId = " + i + ")");
        return this.mService.getPermissionFlags(str, str2, str3, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, java.lang.String str3, int i3) {
        android.util.Log.i(LOG_TAG, "updatePermissionFlags(packageName = " + str + ", permName = " + str2 + ", flagMask = " + i + ", flagValues = " + i2 + ", checkAdjustPolicyFlagPermission = " + z + ", deviceId = " + str3 + ", userId = " + i3 + ")");
        this.mService.updatePermissionFlags(str, str2, i, i2, z, str3, i3);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlagsForAllApps(int i, int i2, int i3) {
        android.util.Log.i(LOG_TAG, "updatePermissionFlagsForAllApps(flagMask = " + i + ", flagValues = " + i2 + ", userId = " + i3 + ")");
        this.mService.updatePermissionFlagsForAllApps(i, i2, i3);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void addOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        android.util.Log.i(LOG_TAG, "addOnPermissionsChangeListener(listener = " + iOnPermissionsChangeListener + ")");
        this.mService.addOnPermissionsChangeListener(iOnPermissionsChangeListener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removeOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        android.util.Log.i(LOG_TAG, "removeOnPermissionsChangeListener(listener = " + iOnPermissionsChangeListener + ")");
        this.mService.removeOnPermissionsChangeListener(iOnPermissionsChangeListener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addAllowlistedRestrictedPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2) {
        android.util.Log.i(LOG_TAG, "addAllowlistedRestrictedPermission(packageName = " + str + ", permName = " + str2 + ", flags = " + i + ", userId = " + i2 + ")");
        return this.mService.addAllowlistedRestrictedPermission(str, str2, i, i2);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<java.lang.String> getAllowlistedRestrictedPermissions(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        android.util.Log.i(LOG_TAG, "getAllowlistedRestrictedPermissions(packageName = " + str + ", flags = " + i + ", userId = " + i2 + ")");
        return this.mService.getAllowlistedRestrictedPermissions(str, i, i2);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean removeAllowlistedRestrictedPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2) {
        android.util.Log.i(LOG_TAG, "removeAllowlistedRestrictedPermission(packageName = " + str + ", permName = " + str2 + ", flags = " + i + ", userId = " + i2 + ")");
        return this.mService.removeAllowlistedRestrictedPermission(str, str2, i, i2);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void grantRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        android.util.Log.i(LOG_TAG, "grantRuntimePermission(packageName = " + str + ", permName = " + str2 + ", deviceId = " + str3 + ", userId = " + i + ")");
        this.mService.grantRuntimePermission(str, str2, str3, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) {
        android.util.Log.i(LOG_TAG, "revokeRuntimePermission(packageName = " + str + ", permName = " + str2 + ", deviceId = " + str3 + ", userId = " + i + ", reason = " + str4 + ")");
        this.mService.revokeRuntimePermission(str, str2, str3, i, str4);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokePostNotificationPermissionWithoutKillForTest(java.lang.String str, int i) {
        android.util.Log.i(LOG_TAG, "revokePostNotificationPermissionWithoutKillForTest(packageName = " + str + ", userId = " + i + ")");
        this.mService.revokePostNotificationPermissionWithoutKillForTest(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean shouldShowRequestPermissionRationale(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        android.util.Log.i(LOG_TAG, "shouldShowRequestPermissionRationale(packageName = " + str + ", permName = " + str2 + ", deviceId = " + str3 + ", userId = " + i + ")");
        return this.mService.shouldShowRequestPermissionRationale(str, str2, str3, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        android.util.Log.i(LOG_TAG, "isPermissionRevokedByPolicy(packageName = " + str + ", permName = " + str2 + ", deviceId = " + str3 + ", userId = " + i + ")");
        return this.mService.isPermissionRevokedByPolicy(str, str2, str3, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> getSplitPermissions() {
        android.util.Log.i(LOG_TAG, "getSplitPermissions()");
        return this.mService.getSplitPermissions();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        android.util.Log.i(LOG_TAG, "checkPermission(pkgName = " + str + ", permName = " + str2 + ", deviceId = " + str3 + ", userId = " + i + ")");
        return this.mService.checkPermission(str, str2, str3, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkUidPermission(int i, java.lang.String str, java.lang.String str2) {
        android.util.Log.i(LOG_TAG, "checkUidPermission(uid = " + i + ", permName = " + str + ", deviceId = " + str2 + ")");
        return this.mService.checkUidPermission(i, str, str2);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> getAllPermissionStates(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        android.util.Log.i(LOG_TAG, "getAllPermissionStates(packageName = " + str + ", deviceId = " + str2 + ", userId = " + i + ")");
        return this.mService.getAllPermissionStates(str, str2, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getAllAppOpPermissionPackages() {
        android.util.Log.i(LOG_TAG, "getAllAppOpPermissionPackages()");
        return this.mService.getAllAppOpPermissionPackages();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionsReviewRequired(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.Log.i(LOG_TAG, "isPermissionsReviewRequired(packageName = " + str + ", userId = " + i + ")");
        return this.mService.isPermissionsReviewRequired(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissions(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        android.util.Log.i(LOG_TAG, "resetRuntimePermissions(pkg = " + androidPackage + ", userId = " + i + ")");
        this.mService.resetRuntimePermissions(androidPackage, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissionsForUser(int i) {
        android.util.Log.i(LOG_TAG, "resetRuntimePermissionsForUser(userId = " + i + ")");
        this.mService.resetRuntimePermissionsForUser(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionStateTEMP() {
        android.util.Log.i(LOG_TAG, "readLegacyPermissionStateTEMP()");
        this.mService.readLegacyPermissionStateTEMP();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionStateTEMP() {
        android.util.Log.i(LOG_TAG, "writeLegacyPermissionStateTEMP()");
        this.mService.writeLegacyPermissionStateTEMP();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.Set<java.lang.String> getInstalledPermissions(@android.annotation.NonNull java.lang.String str) {
        android.util.Log.i(LOG_TAG, "getInstalledPermissions(packageName = " + str + ")");
        return this.mService.getInstalledPermissions(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.Set<java.lang.String> getGrantedPermissions(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.Log.i(LOG_TAG, "getGrantedPermissions(packageName = " + str + ", userId = " + i + ")");
        return this.mService.getGrantedPermissions(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public int[] getPermissionGids(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.Log.i(LOG_TAG, "getPermissionGids(permissionName = " + str + ", userId = " + i + ")");
        return this.mService.getPermissionGids(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.lang.String[] getAppOpPermissionPackages(@android.annotation.NonNull java.lang.String str) {
        android.util.Log.i(LOG_TAG, "getAppOpPermissionPackages(permissionName = " + str + ")");
        return this.mService.getAppOpPermissionPackages(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.Nullable
    public com.android.server.pm.permission.Permission getPermissionTEMP(@android.annotation.NonNull java.lang.String str) {
        android.util.Log.i(LOG_TAG, "getPermissionTEMP(permName = " + str + ")");
        return this.mService.getPermissionTEMP(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtection(int i) {
        android.util.Log.i(LOG_TAG, "getAllPermissionsWithProtection(protection = " + i + ")");
        return this.mService.getAllPermissionsWithProtection(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtectionFlags(int i) {
        android.util.Log.i(LOG_TAG, "getAllPermissionsWithProtectionFlags(protectionFlags = " + i + ")");
        return this.mService.getAllPermissionsWithProtectionFlags(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.permission.LegacyPermission> getLegacyPermissions() {
        android.util.Log.i(LOG_TAG, "getLegacyPermissions()");
        return this.mService.getLegacyPermissions();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public com.android.server.pm.permission.LegacyPermissionState getLegacyPermissionState(int i) {
        android.util.Log.i(LOG_TAG, "getLegacyPermissionState(appId = " + i + ")");
        return this.mService.getLegacyPermissionState(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
        android.util.Log.i(LOG_TAG, "readLegacyPermissionsTEMP(legacyPermissionSettings = " + legacyPermissionSettings + ")");
        this.mService.readLegacyPermissionsTEMP(legacyPermissionSettings);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
        android.util.Log.i(LOG_TAG, "writeLegacyPermissionsTEMP(legacyPermissionSettings = " + legacyPermissionSettings + ")");
        this.mService.writeLegacyPermissionsTEMP(legacyPermissionSettings);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.Nullable
    public java.lang.String getDefaultPermissionGrantFingerprint(int i) {
        android.util.Log.i(LOG_TAG, "getDefaultPermissionGrantFingerprint(userId = " + i + ")");
        return this.mService.getDefaultPermissionGrantFingerprint(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void setDefaultPermissionGrantFingerprint(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.Log.i(LOG_TAG, "setDefaultPermissionGrantFingerprint(fingerprint = " + str + ", userId = " + i + ")");
        this.mService.setDefaultPermissionGrantFingerprint(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onSystemReady() {
        android.util.Log.i(LOG_TAG, "onSystemReady()");
        this.mService.onSystemReady();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onStorageVolumeMounted(@android.annotation.NonNull java.lang.String str, boolean z) {
        android.util.Log.i(LOG_TAG, "onStorageVolumeMounted(volumeUuid = " + str + ", fingerprintChanged = " + z + ")");
        this.mService.onStorageVolumeMounted(str, z);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public int[] getGidsForUid(int i) {
        android.util.Log.i(LOG_TAG, "getGidsForUid(uid = " + i + ")");
        return this.mService.getGidsForUid(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserCreated(int i) {
        android.util.Log.i(LOG_TAG, "onUserCreated(userId = " + i + ")");
        this.mService.onUserCreated(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserRemoved(int i) {
        android.util.Log.i(LOG_TAG, "onUserRemoved(userId = " + i + ")");
        this.mService.onUserRemoved(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageAdded(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, boolean z, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.util.Log.i(LOG_TAG, "onPackageAdded(packageState = " + packageState + ", isInstantApp = " + z + ", oldPkg = " + androidPackage + ")");
        this.mService.onPackageAdded(packageState, z, androidPackage);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageInstalled(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int i2) {
        android.util.Log.i(LOG_TAG, "onPackageInstalled(pkg = " + androidPackage + ", previousAppId = " + i + ", params = " + packageInstalledParams + ", userId = " + i2 + ")");
        this.mService.onPackageInstalled(androidPackage, i, packageInstalledParams, i2);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageRemoved(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.util.Log.i(LOG_TAG, "onPackageRemoved(pkg = " + androidPackage + ")");
        this.mService.onPackageRemoved(androidPackage);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageUninstalled(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.AndroidPackage> list, int i2) {
        android.util.Log.i(LOG_TAG, "onPackageUninstalled(packageName = " + str + ", appId = " + i + ", packageState = " + packageState + ", pkg = " + androidPackage + ", sharedUserPkgs = " + list + ", userId = " + i2 + ")");
        this.mService.onPackageUninstalled(str, i, packageState, androidPackage, list, i2);
    }
}
