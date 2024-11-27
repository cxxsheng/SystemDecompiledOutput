package com.android.server.policy;

/* loaded from: classes2.dex */
public abstract class SoftRestrictedPermissionPolicy {
    private static final int FLAGS_PERMISSION_RESTRICTION_ANY_EXEMPT = 14336;
    private static final com.android.server.policy.SoftRestrictedPermissionPolicy DUMMY_POLICY = new com.android.server.policy.SoftRestrictedPermissionPolicy() { // from class: com.android.server.policy.SoftRestrictedPermissionPolicy.1
        @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
        public boolean mayGrantPermission() {
            return true;
        }
    };
    private static final java.util.HashSet<java.lang.String> sForcedScopedStorageAppWhitelist = new java.util.HashSet<>(java.util.Arrays.asList(getForcedScopedStorageAppWhitelist()));

    public abstract boolean mayGrantPermission();

    private static int getMinimumTargetSDK(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.pm.ApplicationInfo applicationInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        int i = applicationInfo.targetSdkVersion;
        java.lang.String[] packagesForUid = packageManager.getPackagesForUid(applicationInfo.uid);
        if (packagesForUid != null) {
            for (java.lang.String str : packagesForUid) {
                if (!str.equals(applicationInfo.packageName)) {
                    try {
                        i = java.lang.Integer.min(i, packageManager.getApplicationInfoAsUser(str, 0, userHandle).targetSdkVersion);
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    }
                }
            }
        }
        return i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @android.annotation.NonNull
    public static com.android.server.policy.SoftRestrictedPermissionPolicy forPermission(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str) {
        char c;
        final boolean z;
        final int i;
        final boolean z2;
        final boolean z3;
        final boolean z4;
        final boolean z5;
        final boolean z6;
        final boolean z7;
        final int i2;
        final boolean z8 = false;
        switch (str.hashCode()) {
            case -406040016:
                if (str.equals("android.permission.READ_EXTERNAL_STORAGE")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1365911975:
                if (str.equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (applicationInfo != null) {
                    android.content.pm.PackageManager packageManager = context.getPackageManager();
                    android.os.storage.StorageManagerInternal storageManagerInternal = (android.os.storage.StorageManagerInternal) com.android.server.LocalServices.getService(android.os.storage.StorageManagerInternal.class);
                    int permissionFlags = packageManager.getPermissionFlags(str, applicationInfo.packageName, userHandle);
                    boolean z9 = (permissionFlags & FLAGS_PERMISSION_RESTRICTION_ANY_EXEMPT) != 0;
                    boolean hasLegacyExternalStorage = storageManagerInternal.hasLegacyExternalStorage(applicationInfo.uid);
                    boolean hasUidRequestedLegacyExternalStorage = hasUidRequestedLegacyExternalStorage(applicationInfo.uid, context);
                    boolean hasWriteMediaStorageGrantedForUid = hasWriteMediaStorageGrantedForUid(applicationInfo.uid, context);
                    boolean hasPreserveLegacyExternalStorage = androidPackage.hasPreserveLegacyExternalStorage();
                    i = getMinimumTargetSDK(context, applicationInfo, userHandle);
                    z = z9;
                    z2 = (permissionFlags & 16384) != 0;
                    z3 = sForcedScopedStorageAppWhitelist.contains(applicationInfo.packageName);
                    z5 = hasLegacyExternalStorage;
                    z6 = hasUidRequestedLegacyExternalStorage;
                    z4 = hasWriteMediaStorageGrantedForUid;
                    z7 = hasPreserveLegacyExternalStorage;
                } else {
                    z = false;
                    i = 0;
                    z2 = false;
                    z3 = false;
                    z4 = false;
                    z5 = false;
                    z6 = false;
                    z7 = false;
                }
                return new com.android.server.policy.SoftRestrictedPermissionPolicy() { // from class: com.android.server.policy.SoftRestrictedPermissionPolicy.2
                    @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
                    public boolean mayGrantPermission() {
                        return z || i >= 29;
                    }

                    @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
                    public int getExtraAppOpCode() {
                        return 87;
                    }

                    @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
                    public boolean mayAllowExtraAppOp() {
                        if (z2 || z3 || i >= 30) {
                            return false;
                        }
                        return z4 || z5 || z6;
                    }

                    @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
                    public boolean mayDenyExtraAppOpIfGranted() {
                        if (i < 30) {
                            return !mayAllowExtraAppOp();
                        }
                        return z2 || z3 || !z7;
                    }
                };
            case 1:
                if (applicationInfo != null) {
                    boolean z10 = (context.getPackageManager().getPermissionFlags(str, applicationInfo.packageName, userHandle) & FLAGS_PERMISSION_RESTRICTION_ANY_EXEMPT) != 0;
                    i2 = getMinimumTargetSDK(context, applicationInfo, userHandle);
                    z8 = z10;
                } else {
                    i2 = 0;
                }
                return new com.android.server.policy.SoftRestrictedPermissionPolicy() { // from class: com.android.server.policy.SoftRestrictedPermissionPolicy.3
                    @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
                    public boolean mayGrantPermission() {
                        return z8 || i2 >= 29;
                    }
                };
            default:
                return DUMMY_POLICY;
        }
    }

    private static boolean hasUidRequestedLegacyExternalStorage(int i, @android.annotation.NonNull android.content.Context context) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        java.lang.String[] packagesForUid = packageManager.getPackagesForUid(i);
        if (packagesForUid == null) {
            return false;
        }
        android.os.UserHandle userHandleForUid = android.os.UserHandle.getUserHandleForUid(i);
        for (java.lang.String str : packagesForUid) {
            if (packageManager.getApplicationInfoAsUser(str, 0, userHandleForUid).hasRequestedLegacyExternalStorage()) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasWriteMediaStorageGrantedForUid(int i, @android.annotation.NonNull android.content.Context context) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        java.lang.String[] packagesForUid = packageManager.getPackagesForUid(i);
        if (packagesForUid == null) {
            return false;
        }
        for (java.lang.String str : packagesForUid) {
            if (packageManager.checkPermission("android.permission.WRITE_MEDIA_STORAGE", str) == 0) {
                return true;
            }
        }
        return false;
    }

    private static java.lang.String[] getForcedScopedStorageAppWhitelist() {
        java.lang.String string = android.provider.DeviceConfig.getString("storage_native_boot", "forced_scoped_storage_whitelist", "");
        if (string == null || string.equals("")) {
            return new java.lang.String[0];
        }
        return string.split(",");
    }

    public int getExtraAppOpCode() {
        return -1;
    }

    public boolean mayAllowExtraAppOp() {
        return false;
    }

    public boolean mayDenyExtraAppOpIfGranted() {
        return false;
    }
}
