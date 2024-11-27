package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public class PermissionManagerServiceTracingDecorator implements com.android.server.pm.permission.PermissionManagerServiceInterface {
    private static final long TRACE_TAG = 262144;

    @android.annotation.NonNull
    private final com.android.server.pm.permission.PermissionManagerServiceInterface mService;

    public PermissionManagerServiceTracingDecorator(@android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInterface permissionManagerServiceInterface) {
        this.mService = permissionManagerServiceInterface;
    }

    @android.annotation.Nullable
    public byte[] backupRuntimePermissions(int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#backupRuntimePermissions");
        try {
            return this.mService.backupRuntimePermissions(i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    public void restoreRuntimePermissions(@android.annotation.NonNull byte[] bArr, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#restoreRuntimePermissions");
        try {
            this.mService.restoreRuntimePermissions(bArr, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    public void restoreDelayedRuntimePermissions(@android.annotation.NonNull java.lang.String str, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#restoreDelayedRuntimePermissions");
        try {
            this.mService.restoreDelayedRuntimePermissions(str, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#dump");
        try {
            this.mService.dump(fileDescriptor, printWriter, strArr);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<android.content.pm.PermissionGroupInfo> getAllPermissionGroups(int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getAllPermissionGroups");
        try {
            return this.mService.getAllPermissionGroups(i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getPermissionGroupInfo");
        try {
            return this.mService.getPermissionGroupInfo(str, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public android.content.pm.PermissionInfo getPermissionInfo(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getPermissionInfo");
        try {
            return this.mService.getPermissionInfo(str, i, str2);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup(java.lang.String str, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#queryPermissionsByGroup");
        try {
            return this.mService.queryPermissionsByGroup(str, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addPermission(android.content.pm.PermissionInfo permissionInfo, boolean z) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#addPermission");
        try {
            return this.mService.addPermission(permissionInfo, z);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removePermission(java.lang.String str) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#removePermission");
        try {
            this.mService.removePermission(str);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int getPermissionFlags(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getPermissionFlags");
        try {
            return this.mService.getPermissionFlags(str, str2, str3, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, java.lang.String str3, int i3) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#updatePermissionFlags");
        try {
            this.mService.updatePermissionFlags(str, str2, i, i2, z, str3, i3);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlagsForAllApps(int i, int i2, int i3) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#updatePermissionFlagsForAllApps");
        try {
            this.mService.updatePermissionFlagsForAllApps(i, i2, i3);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void addOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#addOnPermissionsChangeListener");
        try {
            this.mService.addOnPermissionsChangeListener(iOnPermissionsChangeListener);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removeOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#removeOnPermissionsChangeListener");
        try {
            this.mService.removeOnPermissionsChangeListener(iOnPermissionsChangeListener);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addAllowlistedRestrictedPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#addAllowlistedRestrictedPermission");
        try {
            return this.mService.addAllowlistedRestrictedPermission(str, str2, i, i2);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<java.lang.String> getAllowlistedRestrictedPermissions(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getAllowlistedRestrictedPermissions");
        try {
            return this.mService.getAllowlistedRestrictedPermissions(str, i, i2);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean removeAllowlistedRestrictedPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#removeAllowlistedRestrictedPermission");
        try {
            return this.mService.removeAllowlistedRestrictedPermission(str, str2, i, i2);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void grantRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#grantRuntimePermission");
        try {
            this.mService.grantRuntimePermission(str, str2, str3, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#revokeRuntimePermission");
        try {
            this.mService.revokeRuntimePermission(str, str2, str3, i, str4);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokePostNotificationPermissionWithoutKillForTest(java.lang.String str, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#revokePostNotificationPermissionWithoutKillForTest");
        try {
            this.mService.revokePostNotificationPermissionWithoutKillForTest(str, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean shouldShowRequestPermissionRationale(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#shouldShowRequestPermissionRationale");
        try {
            return this.mService.shouldShowRequestPermissionRationale(str, str2, str3, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#isPermissionRevokedByPolicy");
        try {
            return this.mService.isPermissionRevokedByPolicy(str, str2, str3, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> getSplitPermissions() {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getSplitPermissions");
        try {
            return this.mService.getSplitPermissions();
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#checkPermission");
        try {
            return this.mService.checkPermission(str, str2, str3, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkUidPermission(int i, java.lang.String str, java.lang.String str2) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#checkUidPermission");
        try {
            return this.mService.checkUidPermission(i, str, str2);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> getAllPermissionStates(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getAllPermissionStates");
        try {
            return this.mService.getAllPermissionStates(str, str2, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getAllAppOpPermissionPackages() {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getAllAppOpPermissionPackages");
        try {
            return this.mService.getAllAppOpPermissionPackages();
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionsReviewRequired(@android.annotation.NonNull java.lang.String str, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#isPermissionsReviewRequired");
        try {
            return this.mService.isPermissionsReviewRequired(str, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissions(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#resetRuntimePermissions");
        try {
            this.mService.resetRuntimePermissions(androidPackage, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissionsForUser(int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#resetRuntimePermissionsForUser");
        try {
            this.mService.resetRuntimePermissionsForUser(i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionStateTEMP() {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#readLegacyPermissionStateTEMP");
        try {
            this.mService.readLegacyPermissionStateTEMP();
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionStateTEMP() {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#writeLegacyPermissionStateTEMP");
        try {
            this.mService.writeLegacyPermissionStateTEMP();
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.Set<java.lang.String> getInstalledPermissions(@android.annotation.NonNull java.lang.String str) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getInstalledPermissions");
        try {
            return this.mService.getInstalledPermissions(str);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.Set<java.lang.String> getGrantedPermissions(@android.annotation.NonNull java.lang.String str, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getGrantedPermissions");
        try {
            return this.mService.getGrantedPermissions(str, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public int[] getPermissionGids(@android.annotation.NonNull java.lang.String str, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getPermissionGids");
        try {
            return this.mService.getPermissionGids(str, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.lang.String[] getAppOpPermissionPackages(@android.annotation.NonNull java.lang.String str) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getAppOpPermissionPackages");
        try {
            return this.mService.getAppOpPermissionPackages(str);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.Nullable
    public com.android.server.pm.permission.Permission getPermissionTEMP(@android.annotation.NonNull java.lang.String str) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getPermissionTEMP");
        try {
            return this.mService.getPermissionTEMP(str);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtection(int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getAllPermissionsWithProtection");
        try {
            return this.mService.getAllPermissionsWithProtection(i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtectionFlags(int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getAllPermissionsWithProtectionFlags");
        try {
            return this.mService.getAllPermissionsWithProtectionFlags(i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.permission.LegacyPermission> getLegacyPermissions() {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getLegacyPermissions");
        try {
            return this.mService.getLegacyPermissions();
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public com.android.server.pm.permission.LegacyPermissionState getLegacyPermissionState(int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getLegacyPermissionState");
        try {
            return this.mService.getLegacyPermissionState(i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#readLegacyPermissionsTEMP");
        try {
            this.mService.readLegacyPermissionsTEMP(legacyPermissionSettings);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#writeLegacyPermissionsTEMP");
        try {
            this.mService.writeLegacyPermissionsTEMP(legacyPermissionSettings);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.Nullable
    public java.lang.String getDefaultPermissionGrantFingerprint(int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getDefaultPermissionGrantFingerprint");
        try {
            return this.mService.getDefaultPermissionGrantFingerprint(i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void setDefaultPermissionGrantFingerprint(@android.annotation.NonNull java.lang.String str, int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#setDefaultPermissionGrantFingerprint");
        try {
            this.mService.setDefaultPermissionGrantFingerprint(str, i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onSystemReady() {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#onSystemReady");
        try {
            this.mService.onSystemReady();
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onStorageVolumeMounted(@android.annotation.NonNull java.lang.String str, boolean z) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#onStorageVolumeMounted");
        try {
            this.mService.onStorageVolumeMounted(str, z);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public int[] getGidsForUid(int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#getGidsForUid");
        try {
            return this.mService.getGidsForUid(i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserCreated(int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#onUserCreated");
        try {
            this.mService.onUserCreated(i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserRemoved(int i) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#onUserRemoved");
        try {
            this.mService.onUserRemoved(i);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageAdded(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, boolean z, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#onPackageAdded");
        try {
            this.mService.onPackageAdded(packageState, z, androidPackage);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageInstalled(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int i2) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#onPackageInstalled");
        try {
            this.mService.onPackageInstalled(androidPackage, i, packageInstalledParams, i2);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageRemoved(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#onPackageRemoved");
        try {
            this.mService.onPackageRemoved(androidPackage);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageUninstalled(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.AndroidPackage> list, int i2) {
        android.os.Trace.traceBegin(TRACE_TAG, "TaggedTracingPermissionManagerServiceImpl#onPackageUninstalled");
        try {
            this.mService.onPackageUninstalled(str, i, packageState, androidPackage, list, i2);
        } finally {
            android.os.Trace.traceEnd(TRACE_TAG);
        }
    }
}
