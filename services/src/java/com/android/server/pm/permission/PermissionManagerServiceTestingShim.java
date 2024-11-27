package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public class PermissionManagerServiceTestingShim implements com.android.server.pm.permission.PermissionManagerServiceInterface {
    private com.android.server.pm.permission.PermissionManagerServiceInterface mNewImplementation;
    private com.android.server.pm.permission.PermissionManagerServiceInterface mOldImplementation;

    public PermissionManagerServiceTestingShim(com.android.server.pm.permission.PermissionManagerServiceInterface permissionManagerServiceInterface, com.android.server.pm.permission.PermissionManagerServiceInterface permissionManagerServiceInterface2) {
        this.mOldImplementation = permissionManagerServiceInterface;
        this.mNewImplementation = permissionManagerServiceInterface2;
    }

    private void signalImplDifference(java.lang.String str) {
    }

    @android.annotation.Nullable
    public byte[] backupRuntimePermissions(int i) {
        byte[] backupRuntimePermissions = this.mOldImplementation.backupRuntimePermissions(i);
        byte[] backupRuntimePermissions2 = this.mNewImplementation.backupRuntimePermissions(i);
        if (!java.util.Arrays.equals(backupRuntimePermissions, backupRuntimePermissions2)) {
            signalImplDifference("backupRuntimePermissions");
        }
        return backupRuntimePermissions2;
    }

    public void restoreRuntimePermissions(@android.annotation.NonNull byte[] bArr, int i) {
        this.mOldImplementation.backupRuntimePermissions(i);
        this.mNewImplementation.backupRuntimePermissions(i);
    }

    public void restoreDelayedRuntimePermissions(@android.annotation.NonNull java.lang.String str, int i) {
        this.mOldImplementation.restoreDelayedRuntimePermissions(str, i);
        this.mNewImplementation.restoreDelayedRuntimePermissions(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        this.mOldImplementation.dump(fileDescriptor, printWriter, strArr);
        this.mNewImplementation.dump(fileDescriptor, printWriter, strArr);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<android.content.pm.PermissionGroupInfo> getAllPermissionGroups(int i) {
        java.util.List<android.content.pm.PermissionGroupInfo> allPermissionGroups = this.mOldImplementation.getAllPermissionGroups(i);
        java.util.List<android.content.pm.PermissionGroupInfo> allPermissionGroups2 = this.mNewImplementation.getAllPermissionGroups(i);
        if (!java.util.Objects.equals(allPermissionGroups, allPermissionGroups2)) {
            signalImplDifference("getAllPermissionGroups");
        }
        return allPermissionGroups2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) {
        android.content.pm.PermissionGroupInfo permissionGroupInfo = this.mOldImplementation.getPermissionGroupInfo(str, i);
        android.content.pm.PermissionGroupInfo permissionGroupInfo2 = this.mNewImplementation.getPermissionGroupInfo(str, i);
        if (!java.util.Objects.equals(permissionGroupInfo, permissionGroupInfo2)) {
            signalImplDifference("getPermissionGroupInfo");
        }
        return permissionGroupInfo2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public android.content.pm.PermissionInfo getPermissionInfo(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        android.content.pm.PermissionInfo permissionInfo = this.mOldImplementation.getPermissionInfo(str, i, str2);
        android.content.pm.PermissionInfo permissionInfo2 = this.mNewImplementation.getPermissionInfo(str, i, str2);
        if (!java.util.Objects.equals(permissionInfo, permissionInfo2)) {
            signalImplDifference("getPermissionInfo");
        }
        return permissionInfo2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup(java.lang.String str, int i) {
        java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup = this.mOldImplementation.queryPermissionsByGroup(str, i);
        java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup2 = this.mNewImplementation.queryPermissionsByGroup(str, i);
        if (!java.util.Objects.equals(queryPermissionsByGroup, queryPermissionsByGroup2)) {
            signalImplDifference("queryPermissionsByGroup");
        }
        return queryPermissionsByGroup2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addPermission(android.content.pm.PermissionInfo permissionInfo, boolean z) {
        boolean addPermission = this.mOldImplementation.addPermission(permissionInfo, z);
        boolean addPermission2 = this.mNewImplementation.addPermission(permissionInfo, z);
        if (!java.util.Objects.equals(java.lang.Boolean.valueOf(addPermission), java.lang.Boolean.valueOf(addPermission2))) {
            signalImplDifference("addPermission");
        }
        return addPermission2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removePermission(java.lang.String str) {
        this.mOldImplementation.removePermission(str);
        this.mNewImplementation.removePermission(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int getPermissionFlags(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        int permissionFlags = this.mOldImplementation.getPermissionFlags(str, str2, str3, i);
        int permissionFlags2 = this.mNewImplementation.getPermissionFlags(str, str2, str3, i);
        if (!java.util.Objects.equals(java.lang.Integer.valueOf(permissionFlags), java.lang.Integer.valueOf(permissionFlags2))) {
            signalImplDifference("getPermissionFlags");
        }
        return permissionFlags2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, java.lang.String str3, int i3) {
        this.mOldImplementation.updatePermissionFlags(str, str2, i, i2, z, str3, i3);
        this.mNewImplementation.updatePermissionFlags(str, str2, i, i2, z, str3, i3);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlagsForAllApps(int i, int i2, int i3) {
        this.mOldImplementation.updatePermissionFlagsForAllApps(i, i2, i3);
        this.mNewImplementation.updatePermissionFlagsForAllApps(i, i2, i3);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void addOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.mOldImplementation.addOnPermissionsChangeListener(iOnPermissionsChangeListener);
        this.mNewImplementation.addOnPermissionsChangeListener(iOnPermissionsChangeListener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removeOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.mOldImplementation.removeOnPermissionsChangeListener(iOnPermissionsChangeListener);
        this.mNewImplementation.removeOnPermissionsChangeListener(iOnPermissionsChangeListener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addAllowlistedRestrictedPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2) {
        boolean addAllowlistedRestrictedPermission = this.mOldImplementation.addAllowlistedRestrictedPermission(str, str2, i, i2);
        boolean addAllowlistedRestrictedPermission2 = this.mNewImplementation.addAllowlistedRestrictedPermission(str, str2, i, i2);
        if (!java.util.Objects.equals(java.lang.Boolean.valueOf(addAllowlistedRestrictedPermission), java.lang.Boolean.valueOf(addAllowlistedRestrictedPermission2))) {
            signalImplDifference("addAllowlistedRestrictedPermission");
        }
        return addAllowlistedRestrictedPermission2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<java.lang.String> getAllowlistedRestrictedPermissions(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        java.util.List<java.lang.String> allowlistedRestrictedPermissions = this.mOldImplementation.getAllowlistedRestrictedPermissions(str, i, i2);
        java.util.List<java.lang.String> allowlistedRestrictedPermissions2 = this.mNewImplementation.getAllowlistedRestrictedPermissions(str, i, i2);
        if (!java.util.Objects.equals(allowlistedRestrictedPermissions, allowlistedRestrictedPermissions2)) {
            signalImplDifference("getAllowlistedRestrictedPermissions");
        }
        return allowlistedRestrictedPermissions2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean removeAllowlistedRestrictedPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2) {
        boolean removeAllowlistedRestrictedPermission = this.mOldImplementation.removeAllowlistedRestrictedPermission(str, str2, i, i2);
        boolean removeAllowlistedRestrictedPermission2 = this.mNewImplementation.removeAllowlistedRestrictedPermission(str, str2, i, i2);
        if (!java.util.Objects.equals(java.lang.Boolean.valueOf(removeAllowlistedRestrictedPermission), java.lang.Boolean.valueOf(removeAllowlistedRestrictedPermission2))) {
            signalImplDifference("removeAllowlistedRestrictedPermission");
        }
        return removeAllowlistedRestrictedPermission2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void grantRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        this.mOldImplementation.grantRuntimePermission(str, str2, str3, i);
        this.mNewImplementation.grantRuntimePermission(str, str2, str3, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) {
        this.mOldImplementation.revokeRuntimePermission(str, str2, str3, i, str4);
        this.mNewImplementation.revokeRuntimePermission(str, str2, str3, i, str4);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokePostNotificationPermissionWithoutKillForTest(java.lang.String str, int i) {
        this.mOldImplementation.revokePostNotificationPermissionWithoutKillForTest(str, i);
        this.mNewImplementation.revokePostNotificationPermissionWithoutKillForTest(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean shouldShowRequestPermissionRationale(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        boolean shouldShowRequestPermissionRationale = this.mOldImplementation.shouldShowRequestPermissionRationale(str, str2, str3, i);
        boolean shouldShowRequestPermissionRationale2 = this.mNewImplementation.shouldShowRequestPermissionRationale(str, str2, str3, i);
        if (!java.util.Objects.equals(java.lang.Boolean.valueOf(shouldShowRequestPermissionRationale), java.lang.Boolean.valueOf(shouldShowRequestPermissionRationale2))) {
            signalImplDifference("shouldShowRequestPermissionRationale");
        }
        return shouldShowRequestPermissionRationale2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        boolean isPermissionRevokedByPolicy = this.mOldImplementation.isPermissionRevokedByPolicy(str, str2, str3, i);
        boolean isPermissionRevokedByPolicy2 = this.mNewImplementation.isPermissionRevokedByPolicy(str, str2, str3, i);
        if (!java.util.Objects.equals(java.lang.Boolean.valueOf(isPermissionRevokedByPolicy), java.lang.Boolean.valueOf(isPermissionRevokedByPolicy2))) {
            signalImplDifference("isPermissionRevokedByPolicy");
        }
        return isPermissionRevokedByPolicy2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> getSplitPermissions() {
        java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> splitPermissions = this.mOldImplementation.getSplitPermissions();
        java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> splitPermissions2 = this.mNewImplementation.getSplitPermissions();
        if (!java.util.Objects.equals(splitPermissions, splitPermissions2)) {
            signalImplDifference("getSplitPermissions");
        }
        return splitPermissions2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        int checkPermission = this.mOldImplementation.checkPermission(str, str2, str3, i);
        int checkPermission2 = this.mNewImplementation.checkPermission(str, str2, str3, i);
        if (!java.util.Objects.equals(java.lang.Integer.valueOf(checkPermission), java.lang.Integer.valueOf(checkPermission2))) {
            signalImplDifference("checkPermission");
        }
        return checkPermission2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkUidPermission(int i, java.lang.String str, java.lang.String str2) {
        int checkUidPermission = this.mOldImplementation.checkUidPermission(i, str, str2);
        int checkUidPermission2 = this.mNewImplementation.checkUidPermission(i, str, str2);
        if (!java.util.Objects.equals(java.lang.Integer.valueOf(checkUidPermission), java.lang.Integer.valueOf(checkUidPermission2))) {
            signalImplDifference("checkUidPermission");
        }
        return checkUidPermission2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> getAllPermissionStates(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        return this.mNewImplementation.getAllPermissionStates(str, str2, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getAllAppOpPermissionPackages() {
        java.util.Map<java.lang.String, java.util.Set<java.lang.String>> allAppOpPermissionPackages = this.mOldImplementation.getAllAppOpPermissionPackages();
        java.util.Map<java.lang.String, java.util.Set<java.lang.String>> allAppOpPermissionPackages2 = this.mNewImplementation.getAllAppOpPermissionPackages();
        if (!java.util.Objects.equals(allAppOpPermissionPackages, allAppOpPermissionPackages2)) {
            signalImplDifference("getAllAppOpPermissionPackages");
        }
        return allAppOpPermissionPackages2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionsReviewRequired(@android.annotation.NonNull java.lang.String str, int i) {
        boolean isPermissionsReviewRequired = this.mOldImplementation.isPermissionsReviewRequired(str, i);
        boolean isPermissionsReviewRequired2 = this.mNewImplementation.isPermissionsReviewRequired(str, i);
        if (!java.util.Objects.equals(java.lang.Boolean.valueOf(isPermissionsReviewRequired), java.lang.Boolean.valueOf(isPermissionsReviewRequired2))) {
            signalImplDifference("isPermissionsReviewRequired");
        }
        return isPermissionsReviewRequired2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissions(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        this.mOldImplementation.resetRuntimePermissions(androidPackage, i);
        this.mNewImplementation.resetRuntimePermissions(androidPackage, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissionsForUser(int i) {
        this.mOldImplementation.resetRuntimePermissionsForUser(i);
        this.mNewImplementation.resetRuntimePermissionsForUser(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionStateTEMP() {
        this.mOldImplementation.readLegacyPermissionStateTEMP();
        this.mNewImplementation.readLegacyPermissionStateTEMP();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionStateTEMP() {
        this.mOldImplementation.writeLegacyPermissionStateTEMP();
        this.mNewImplementation.writeLegacyPermissionStateTEMP();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.Set<java.lang.String> getInstalledPermissions(java.lang.String str) {
        java.util.Set<java.lang.String> installedPermissions = this.mOldImplementation.getInstalledPermissions(str);
        java.util.Set<java.lang.String> installedPermissions2 = this.mNewImplementation.getInstalledPermissions(str);
        if (!java.util.Objects.equals(installedPermissions, installedPermissions2)) {
            signalImplDifference("getInstalledPermissions");
        }
        return installedPermissions2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.Set<java.lang.String> getGrantedPermissions(@android.annotation.NonNull java.lang.String str, int i) {
        java.util.Set<java.lang.String> grantedPermissions = this.mOldImplementation.getGrantedPermissions(str, i);
        java.util.Set<java.lang.String> grantedPermissions2 = this.mNewImplementation.getGrantedPermissions(str, i);
        if (!java.util.Objects.equals(grantedPermissions, grantedPermissions2)) {
            signalImplDifference("getGrantedPermissions");
        }
        return grantedPermissions2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public int[] getPermissionGids(@android.annotation.NonNull java.lang.String str, int i) {
        int[] permissionGids = this.mOldImplementation.getPermissionGids(str, i);
        int[] permissionGids2 = this.mNewImplementation.getPermissionGids(str, i);
        if (!java.util.Arrays.equals(permissionGids, permissionGids2)) {
            signalImplDifference("getPermissionGids");
        }
        return permissionGids2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.lang.String[] getAppOpPermissionPackages(@android.annotation.NonNull java.lang.String str) {
        java.lang.String[] appOpPermissionPackages = this.mOldImplementation.getAppOpPermissionPackages(str);
        java.lang.String[] appOpPermissionPackages2 = this.mNewImplementation.getAppOpPermissionPackages(str);
        if (!java.util.Arrays.equals(appOpPermissionPackages, appOpPermissionPackages2)) {
            signalImplDifference("getAppOpPermissionPackages");
        }
        return appOpPermissionPackages2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.Nullable
    public com.android.server.pm.permission.Permission getPermissionTEMP(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.permission.Permission permissionTEMP = this.mOldImplementation.getPermissionTEMP(str);
        com.android.server.pm.permission.Permission permissionTEMP2 = this.mNewImplementation.getPermissionTEMP(str);
        if (!java.util.Objects.equals(permissionTEMP, permissionTEMP2)) {
            signalImplDifference("getPermissionTEMP");
        }
        return permissionTEMP2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtection(int i) {
        java.util.List<android.content.pm.PermissionInfo> allPermissionsWithProtection = this.mOldImplementation.getAllPermissionsWithProtection(i);
        java.util.List<android.content.pm.PermissionInfo> allPermissionsWithProtection2 = this.mNewImplementation.getAllPermissionsWithProtection(i);
        if (!java.util.Objects.equals(allPermissionsWithProtection, allPermissionsWithProtection2)) {
            signalImplDifference("getAllPermissionsWithProtection");
        }
        return allPermissionsWithProtection2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtectionFlags(int i) {
        java.util.List<android.content.pm.PermissionInfo> allPermissionsWithProtectionFlags = this.mOldImplementation.getAllPermissionsWithProtectionFlags(i);
        java.util.List<android.content.pm.PermissionInfo> allPermissionsWithProtectionFlags2 = this.mNewImplementation.getAllPermissionsWithProtectionFlags(i);
        if (!java.util.Objects.equals(allPermissionsWithProtectionFlags, allPermissionsWithProtectionFlags2)) {
            signalImplDifference("getAllPermissionsWithProtectionFlags");
        }
        return allPermissionsWithProtectionFlags2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.permission.LegacyPermission> getLegacyPermissions() {
        java.util.List<com.android.server.pm.permission.LegacyPermission> legacyPermissions = this.mOldImplementation.getLegacyPermissions();
        java.util.List<com.android.server.pm.permission.LegacyPermission> legacyPermissions2 = this.mNewImplementation.getLegacyPermissions();
        if (!java.util.Objects.equals(legacyPermissions, legacyPermissions2)) {
            signalImplDifference("getLegacyPermissions");
        }
        return legacyPermissions2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public com.android.server.pm.permission.LegacyPermissionState getLegacyPermissionState(int i) {
        com.android.server.pm.permission.LegacyPermissionState legacyPermissionState = this.mOldImplementation.getLegacyPermissionState(i);
        com.android.server.pm.permission.LegacyPermissionState legacyPermissionState2 = this.mNewImplementation.getLegacyPermissionState(i);
        if (!java.util.Objects.equals(legacyPermissionState, legacyPermissionState2)) {
            signalImplDifference("getLegacyPermissionState");
        }
        return legacyPermissionState2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
        this.mOldImplementation.readLegacyPermissionsTEMP(legacyPermissionSettings);
        this.mNewImplementation.readLegacyPermissionsTEMP(legacyPermissionSettings);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
        this.mOldImplementation.writeLegacyPermissionsTEMP(legacyPermissionSettings);
        this.mNewImplementation.writeLegacyPermissionsTEMP(legacyPermissionSettings);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.Nullable
    public java.lang.String getDefaultPermissionGrantFingerprint(int i) {
        java.lang.String defaultPermissionGrantFingerprint = this.mOldImplementation.getDefaultPermissionGrantFingerprint(i);
        java.lang.String defaultPermissionGrantFingerprint2 = this.mNewImplementation.getDefaultPermissionGrantFingerprint(i);
        if (java.util.Objects.equals(defaultPermissionGrantFingerprint, android.os.Build.FINGERPRINT) != java.util.Objects.equals(defaultPermissionGrantFingerprint2, android.os.Build.FINGERPRINT)) {
            signalImplDifference("getDefaultPermissionGrantFingerprint");
        }
        return defaultPermissionGrantFingerprint2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void setDefaultPermissionGrantFingerprint(@android.annotation.NonNull java.lang.String str, int i) {
        this.mOldImplementation.setDefaultPermissionGrantFingerprint(str, i);
        this.mNewImplementation.setDefaultPermissionGrantFingerprint(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onSystemReady() {
        this.mOldImplementation.onSystemReady();
        this.mNewImplementation.onSystemReady();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onStorageVolumeMounted(@android.annotation.NonNull java.lang.String str, boolean z) {
        this.mOldImplementation.onStorageVolumeMounted(str, z);
        this.mNewImplementation.onStorageVolumeMounted(str, z);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public int[] getGidsForUid(int i) {
        int[] gidsForUid = this.mOldImplementation.getGidsForUid(i);
        int[] gidsForUid2 = this.mNewImplementation.getGidsForUid(i);
        if (!java.util.Arrays.equals(gidsForUid, gidsForUid2)) {
            signalImplDifference("getGidsForUid");
        }
        return gidsForUid2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserCreated(int i) {
        this.mOldImplementation.onUserCreated(i);
        this.mNewImplementation.onUserCreated(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserRemoved(int i) {
        this.mOldImplementation.onUserRemoved(i);
        this.mNewImplementation.onUserRemoved(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageAdded(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, boolean z, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage) {
        this.mOldImplementation.onPackageAdded(packageState, z, androidPackage);
        this.mNewImplementation.onPackageAdded(packageState, z, androidPackage);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageInstalled(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int i2) {
        this.mOldImplementation.onPackageInstalled(androidPackage, i, packageInstalledParams, i2);
        this.mNewImplementation.onPackageInstalled(androidPackage, i, packageInstalledParams, i2);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageRemoved(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        this.mOldImplementation.onPackageRemoved(androidPackage);
        this.mNewImplementation.onPackageRemoved(androidPackage);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageUninstalled(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.AndroidPackage> list, int i2) {
        this.mOldImplementation.onPackageUninstalled(str, i, packageState, androidPackage, list, i2);
        this.mNewImplementation.onPackageUninstalled(str, i, packageState, androidPackage, list, i2);
    }
}
