package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public final class Permission {
    private static final java.lang.String TAG = "Permission";
    public static final int TYPE_CONFIG = 1;
    public static final int TYPE_DYNAMIC = 2;
    public static final int TYPE_MANIFEST = 0;
    private boolean mDefinitionChanged;

    @android.annotation.NonNull
    private int[] mGids;
    private boolean mGidsPerUser;

    @android.annotation.NonNull
    private android.content.pm.PermissionInfo mPermissionInfo;
    private boolean mReconciled;
    private final int mType;
    private int mUid;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PermissionType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProtectionLevel {
    }

    public Permission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        this.mGids = libcore.util.EmptyArray.INT;
        this.mPermissionInfo = new android.content.pm.PermissionInfo();
        this.mPermissionInfo.name = str;
        this.mPermissionInfo.packageName = str2;
        this.mPermissionInfo.protectionLevel = 2;
        this.mType = i;
    }

    public Permission(@android.annotation.NonNull android.content.pm.PermissionInfo permissionInfo, int i) {
        this.mGids = libcore.util.EmptyArray.INT;
        this.mPermissionInfo = permissionInfo;
        this.mType = i;
    }

    public Permission(@android.annotation.NonNull android.content.pm.PermissionInfo permissionInfo, int i, boolean z, int i2, int[] iArr, boolean z2) {
        this(permissionInfo, i);
        this.mReconciled = z;
        this.mUid = i2;
        this.mGids = iArr;
        this.mGidsPerUser = z2;
    }

    @android.annotation.NonNull
    public android.content.pm.PermissionInfo getPermissionInfo() {
        return this.mPermissionInfo;
    }

    public void setPermissionInfo(@android.annotation.Nullable android.content.pm.PermissionInfo permissionInfo) {
        if (permissionInfo != null) {
            this.mPermissionInfo = permissionInfo;
        } else {
            android.content.pm.PermissionInfo permissionInfo2 = new android.content.pm.PermissionInfo();
            permissionInfo2.name = this.mPermissionInfo.name;
            permissionInfo2.packageName = this.mPermissionInfo.packageName;
            permissionInfo2.protectionLevel = this.mPermissionInfo.protectionLevel;
            this.mPermissionInfo = permissionInfo2;
        }
        this.mReconciled = permissionInfo != null;
    }

    @android.annotation.NonNull
    public java.lang.String getName() {
        return this.mPermissionInfo.name;
    }

    public int getProtectionLevel() {
        return this.mPermissionInfo.protectionLevel;
    }

    @android.annotation.NonNull
    public java.lang.String getPackageName() {
        return this.mPermissionInfo.packageName;
    }

    public int getType() {
        return this.mType;
    }

    public int getUid() {
        return this.mUid;
    }

    public boolean hasGids() {
        return this.mGids.length != 0;
    }

    @android.annotation.NonNull
    public int[] getRawGids() {
        return this.mGids;
    }

    public boolean areGidsPerUser() {
        return this.mGidsPerUser;
    }

    public void setGids(@android.annotation.NonNull int[] iArr, boolean z) {
        this.mGids = iArr;
        this.mGidsPerUser = z;
    }

    @android.annotation.NonNull
    public int[] computeGids(int i) {
        if (!this.mGidsPerUser) {
            return this.mGids.length != 0 ? (int[]) this.mGids.clone() : this.mGids;
        }
        int[] iArr = new int[this.mGids.length];
        for (int i2 = 0; i2 < this.mGids.length; i2++) {
            iArr[i2] = android.os.UserHandle.getUid(i, this.mGids[i2]);
        }
        return iArr;
    }

    public boolean isDefinitionChanged() {
        return this.mDefinitionChanged;
    }

    public void setDefinitionChanged(boolean z) {
        this.mDefinitionChanged = z;
    }

    public int calculateFootprint(@android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        if (this.mUid == permission.mUid) {
            return permission.mPermissionInfo.name.length() + permission.mPermissionInfo.calculateFootprint();
        }
        return 0;
    }

    public boolean isPermission(@android.annotation.NonNull com.android.internal.pm.pkg.component.ParsedPermission parsedPermission) {
        return this.mPermissionInfo != null && java.util.Objects.equals(this.mPermissionInfo.packageName, parsedPermission.getPackageName()) && java.util.Objects.equals(this.mPermissionInfo.name, parsedPermission.getName());
    }

    public boolean isDynamic() {
        return this.mType == 2;
    }

    public boolean isNormal() {
        return (this.mPermissionInfo.protectionLevel & 15) == 0;
    }

    public boolean isRuntime() {
        return (this.mPermissionInfo.protectionLevel & 15) == 1;
    }

    public boolean isRemoved() {
        return (this.mPermissionInfo.flags & 2) != 0;
    }

    public boolean isSoftRestricted() {
        return (this.mPermissionInfo.flags & 8) != 0;
    }

    public boolean isHardRestricted() {
        return (this.mPermissionInfo.flags & 4) != 0;
    }

    public boolean isHardOrSoftRestricted() {
        return (this.mPermissionInfo.flags & 12) != 0;
    }

    public boolean isImmutablyRestricted() {
        return (this.mPermissionInfo.flags & 16) != 0;
    }

    public boolean isSignature() {
        return (this.mPermissionInfo.protectionLevel & 15) == 2;
    }

    public boolean isInternal() {
        return (this.mPermissionInfo.protectionLevel & 15) == 4;
    }

    public boolean isAppOp() {
        return (this.mPermissionInfo.protectionLevel & 64) != 0;
    }

    public boolean isDevelopment() {
        return isSignature() && (this.mPermissionInfo.protectionLevel & 32) != 0;
    }

    public boolean isInstaller() {
        return (this.mPermissionInfo.protectionLevel & 256) != 0;
    }

    public boolean isInstant() {
        return (this.mPermissionInfo.protectionLevel & 4096) != 0;
    }

    public boolean isOem() {
        return (this.mPermissionInfo.protectionLevel & 16384) != 0;
    }

    public boolean isPre23() {
        return (this.mPermissionInfo.protectionLevel & 128) != 0;
    }

    public boolean isPreInstalled() {
        return (this.mPermissionInfo.protectionLevel & 1024) != 0;
    }

    public boolean isPrivileged() {
        return (this.mPermissionInfo.protectionLevel & 16) != 0;
    }

    public boolean isRuntimeOnly() {
        return (this.mPermissionInfo.protectionLevel & 8192) != 0;
    }

    public boolean isSetup() {
        return (this.mPermissionInfo.protectionLevel & 2048) != 0;
    }

    public boolean isVerifier() {
        return (this.mPermissionInfo.protectionLevel & 512) != 0;
    }

    public boolean isVendorPrivileged() {
        return (this.mPermissionInfo.protectionLevel & 32768) != 0;
    }

    public boolean isSystemTextClassifier() {
        return (this.mPermissionInfo.protectionLevel & 65536) != 0;
    }

    public boolean isConfigurator() {
        return (this.mPermissionInfo.protectionLevel & 524288) != 0;
    }

    public boolean isIncidentReportApprover() {
        return (this.mPermissionInfo.protectionLevel & 1048576) != 0;
    }

    public boolean isAppPredictor() {
        return (this.mPermissionInfo.protectionLevel & 2097152) != 0;
    }

    public boolean isCompanion() {
        return (this.mPermissionInfo.protectionLevel & 8388608) != 0;
    }

    public boolean isModule() {
        return (this.mPermissionInfo.protectionLevel & 4194304) != 0;
    }

    public boolean isRetailDemo() {
        return (this.mPermissionInfo.protectionLevel & 16777216) != 0;
    }

    public boolean isRecents() {
        return (this.mPermissionInfo.protectionLevel & 33554432) != 0;
    }

    public boolean isRole() {
        return (this.mPermissionInfo.protectionLevel & 67108864) != 0;
    }

    public boolean isKnownSigner() {
        return (this.mPermissionInfo.protectionLevel & 134217728) != 0;
    }

    public java.util.Set<java.lang.String> getKnownCerts() {
        return this.mPermissionInfo.knownCerts;
    }

    public void transfer(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        if (!str.equals(this.mPermissionInfo.packageName)) {
            return;
        }
        android.content.pm.PermissionInfo permissionInfo = new android.content.pm.PermissionInfo();
        permissionInfo.name = this.mPermissionInfo.name;
        permissionInfo.packageName = str2;
        permissionInfo.protectionLevel = this.mPermissionInfo.protectionLevel;
        this.mPermissionInfo = permissionInfo;
        this.mReconciled = false;
        this.mUid = 0;
        this.mGids = libcore.util.EmptyArray.INT;
        this.mGidsPerUser = false;
    }

    public boolean addToTree(int i, @android.annotation.NonNull android.content.pm.PermissionInfo permissionInfo, @android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        boolean z = (this.mPermissionInfo.protectionLevel == i && this.mReconciled && this.mUid == permission.mUid && java.util.Objects.equals(this.mPermissionInfo.packageName, permission.mPermissionInfo.packageName) && comparePermissionInfos(this.mPermissionInfo, permissionInfo)) ? false : true;
        this.mPermissionInfo = new android.content.pm.PermissionInfo(permissionInfo);
        this.mPermissionInfo.packageName = permission.mPermissionInfo.packageName;
        this.mPermissionInfo.protectionLevel = i;
        this.mReconciled = true;
        this.mUid = permission.mUid;
        return z;
    }

    public void updateDynamicPermission(@android.annotation.NonNull java.util.Collection<com.android.server.pm.permission.Permission> collection) {
        com.android.server.pm.permission.Permission findPermissionTree;
        if (this.mType == 2 && (findPermissionTree = findPermissionTree(collection, this.mPermissionInfo.name)) != null) {
            this.mPermissionInfo.packageName = findPermissionTree.mPermissionInfo.packageName;
            this.mReconciled = true;
            this.mUid = findPermissionTree.mUid;
        }
    }

    public static boolean isOverridingSystemPermission(@android.annotation.Nullable com.android.server.pm.permission.Permission permission, @android.annotation.NonNull android.content.pm.PermissionInfo permissionInfo, @android.annotation.NonNull android.content.pm.PackageManagerInternal packageManagerInternal) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        if (permission == null || java.util.Objects.equals(permission.mPermissionInfo.packageName, permissionInfo.packageName) || !permission.mReconciled || (packageStateInternal = packageManagerInternal.getPackageStateInternal(permission.mPermissionInfo.packageName)) == null) {
            return false;
        }
        return packageStateInternal.isSystem();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x008b  */
    @android.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.android.server.pm.permission.Permission createOrUpdate(@android.annotation.Nullable com.android.server.pm.permission.Permission permission, @android.annotation.NonNull android.content.pm.PermissionInfo permissionInfo, @android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.NonNull java.util.Collection<com.android.server.pm.permission.Permission> collection, boolean z) {
        boolean z2;
        if (permission != null && !java.util.Objects.equals(permission.mPermissionInfo.packageName, permissionInfo.packageName) && packageState.isSystem()) {
            if (permission.mType == 1 && !permission.mReconciled) {
                permission.mPermissionInfo = permissionInfo;
                permission.mReconciled = true;
                permission.mUid = packageState.getAppId();
            } else if (!z) {
                android.util.Slog.w(TAG, "New decl " + packageState + " of permission  " + permissionInfo.name + " is system; overriding " + permission.mPermissionInfo.packageName);
                permission = null;
                z2 = true;
                boolean z3 = (permission != null || permission.mType == 1 || permission.isInternal()) ? false : true;
                boolean z4 = (permission != null || permission.mType == 1 || permission.isRuntime()) ? false : true;
                if (permission == null) {
                    permission = new com.android.server.pm.permission.Permission(permissionInfo.name, permissionInfo.packageName, 0);
                }
                if (!permission.mReconciled) {
                    if (permission.mPermissionInfo.packageName == null || permission.mPermissionInfo.packageName.equals(permissionInfo.packageName)) {
                        com.android.server.pm.permission.Permission findPermissionTree = findPermissionTree(collection, permissionInfo.name);
                        if (findPermissionTree == null || findPermissionTree.mPermissionInfo.packageName.equals(permissionInfo.packageName)) {
                            permission.mPermissionInfo = permissionInfo;
                            permission.mReconciled = true;
                            permission.mUid = packageState.getAppId();
                        } else {
                            android.util.Slog.w(TAG, "Permission " + permissionInfo.name + " from package " + permissionInfo.packageName + " ignored: base tree " + findPermissionTree.mPermissionInfo.name + " is from package " + findPermissionTree.mPermissionInfo.packageName);
                        }
                    } else {
                        android.util.Slog.w(TAG, "Permission " + permissionInfo.name + " from package " + permissionInfo.packageName + " ignored: original from " + permission.mPermissionInfo.packageName);
                    }
                }
                if ((permission.isInternal() && (z2 || z3)) || (permission.isRuntime() && (z2 || z4))) {
                    permission.mDefinitionChanged = true;
                }
                return permission;
            }
        }
        z2 = false;
        if (permission != null) {
        }
        if (permission != null) {
        }
        if (permission == null) {
        }
        if (!permission.mReconciled) {
        }
        if (permission.isInternal()) {
            permission.mDefinitionChanged = true;
            return permission;
        }
        permission.mDefinitionChanged = true;
        return permission;
    }

    @android.annotation.NonNull
    public static com.android.server.pm.permission.Permission enforcePermissionTree(@android.annotation.NonNull java.util.Collection<com.android.server.pm.permission.Permission> collection, @android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.pm.permission.Permission findPermissionTree;
        if (str != null && (findPermissionTree = findPermissionTree(collection, str)) != null && findPermissionTree.getUid() == android.os.UserHandle.getAppId(i)) {
            return findPermissionTree;
        }
        throw new java.lang.SecurityException("Calling uid " + i + " is not allowed to add to or remove from the permission tree");
    }

    @android.annotation.Nullable
    private static com.android.server.pm.permission.Permission findPermissionTree(@android.annotation.NonNull java.util.Collection<com.android.server.pm.permission.Permission> collection, @android.annotation.NonNull java.lang.String str) {
        for (com.android.server.pm.permission.Permission permission : collection) {
            java.lang.String name = permission.getName();
            if (str.startsWith(name) && str.length() > name.length() && str.charAt(name.length()) == '.') {
                return permission;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    public java.lang.String getBackgroundPermission() {
        return this.mPermissionInfo.backgroundPermission;
    }

    @android.annotation.Nullable
    public java.lang.String getGroup() {
        return this.mPermissionInfo.group;
    }

    public int getProtection() {
        return this.mPermissionInfo.protectionLevel & 15;
    }

    public int getProtectionFlags() {
        return this.mPermissionInfo.protectionLevel & 65520;
    }

    @android.annotation.NonNull
    public android.content.pm.PermissionInfo generatePermissionInfo(int i) {
        return generatePermissionInfo(i, 10000);
    }

    @android.annotation.NonNull
    public android.content.pm.PermissionInfo generatePermissionInfo(int i, int i2) {
        android.content.pm.PermissionInfo permissionInfo;
        if (this.mPermissionInfo != null) {
            permissionInfo = new android.content.pm.PermissionInfo(this.mPermissionInfo);
            if ((i & 128) != 128) {
                permissionInfo.metaData = null;
            }
        } else {
            permissionInfo = new android.content.pm.PermissionInfo();
            permissionInfo.name = this.mPermissionInfo.name;
            permissionInfo.packageName = this.mPermissionInfo.packageName;
            permissionInfo.nonLocalizedLabel = this.mPermissionInfo.name;
        }
        permissionInfo.flags |= 1073741824;
        if (i2 >= 26) {
            permissionInfo.protectionLevel = this.mPermissionInfo.protectionLevel;
        } else {
            int i3 = this.mPermissionInfo.protectionLevel & 15;
            if (i3 == 2) {
                permissionInfo.protectionLevel = this.mPermissionInfo.protectionLevel;
            } else {
                permissionInfo.protectionLevel = i3;
            }
        }
        return permissionInfo;
    }

    private static boolean comparePermissionInfos(android.content.pm.PermissionInfo permissionInfo, android.content.pm.PermissionInfo permissionInfo2) {
        return permissionInfo.icon == permissionInfo2.icon && permissionInfo.logo == permissionInfo2.logo && permissionInfo.protectionLevel == permissionInfo2.protectionLevel && java.util.Objects.equals(permissionInfo.name, permissionInfo2.name) && java.util.Objects.equals(permissionInfo.nonLocalizedLabel, permissionInfo2.nonLocalizedLabel) && java.util.Objects.equals(permissionInfo.packageName, permissionInfo2.packageName);
    }
}
