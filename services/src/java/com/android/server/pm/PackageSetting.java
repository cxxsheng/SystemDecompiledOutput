package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageSetting extends com.android.server.pm.SettingBase implements com.android.server.pm.pkg.PackageStateInternal {
    private int categoryOverride;

    @android.annotation.NonNull
    private com.android.server.pm.InstallSource installSource;

    @android.annotation.NonNull
    private com.android.server.pm.PackageKeySetData keySetData;
    private long lastUpdateTime;

    @android.annotation.Nullable
    @java.lang.Deprecated
    private java.lang.String legacyNativeLibraryPath;
    private int mAppId;

    @android.annotation.Nullable
    private java.lang.String mAppMetadataFilePath;
    private int mAppMetadataSource;
    private int mBooleans;

    @android.annotation.Nullable
    private java.lang.String mCpuAbiOverride;

    @android.annotation.NonNull
    private java.util.UUID mDomainSetId;
    private long mLastModifiedTime;
    private long mLoadingCompletedTime;
    private float mLoadingProgress;

    @android.annotation.NonNull
    private java.lang.String mName;

    @android.annotation.Nullable
    private java.util.LinkedHashSet<java.io.File> mOldPaths;

    @android.annotation.NonNull
    private java.io.File mPath;

    @android.annotation.NonNull
    private java.lang.String mPathString;

    @android.annotation.Nullable
    private java.lang.String mPrimaryCpuAbi;

    @android.annotation.Nullable
    private java.lang.String mRealName;

    @android.annotation.Nullable
    private byte[] mRestrictUpdateHash;

    @android.annotation.Nullable
    private java.lang.String mSecondaryCpuAbi;
    private int mSharedUserAppId;

    @android.annotation.NonNull
    private final com.android.server.utils.SnapshotCache<com.android.server.pm.PackageSetting> mSnapshot;
    private int mTargetSdkVersion;

    @android.annotation.NonNull
    private final android.util.SparseArray<com.android.server.pm.pkg.PackageUserStateImpl> mUserStates;

    @android.annotation.Nullable
    private java.util.Map<java.lang.String, java.util.Set<java.lang.String>> mimeGroups;

    @android.annotation.Nullable
    private com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg;

    @android.annotation.NonNull
    private final com.android.server.pm.pkg.PackageStateUnserialized pkgState;

    @android.annotation.NonNull
    private com.android.server.pm.PackageSignatures signatures;

    @android.annotation.Nullable
    private java.lang.String[] usesSdkLibraries;

    @android.annotation.Nullable
    private boolean[] usesSdkLibrariesOptional;

    @android.annotation.Nullable
    private long[] usesSdkLibrariesVersionsMajor;

    @android.annotation.Nullable
    private java.lang.String[] usesStaticLibraries;

    @android.annotation.Nullable
    private long[] usesStaticLibrariesVersions;
    private long versionCode;

    @android.annotation.Nullable
    private java.lang.String volumeUuid;

    private static class Booleans {
        private static final int FORCE_QUERYABLE_OVERRIDE = 4;
        private static final int INSTALL_PERMISSION_FIXED = 1;
        private static final int PENDING_RESTORE = 16;
        private static final int SCANNED_AS_STOPPED_SYSTEM_APP = 8;
        private static final int UPDATE_AVAILABLE = 2;

        public @interface Flags {
        }

        private Booleans() {
        }
    }

    private void setBoolean(@com.android.server.pm.PackageSetting.Booleans.Flags int i, boolean z) {
        if (z) {
            this.mBooleans = i | this.mBooleans;
        } else {
            this.mBooleans = (~i) & this.mBooleans;
        }
    }

    private boolean getBoolean(@com.android.server.pm.PackageSetting.Booleans.Flags int i) {
        return (i & this.mBooleans) != 0;
    }

    private com.android.server.utils.SnapshotCache<com.android.server.pm.PackageSetting> makeCache() {
        return new com.android.server.utils.SnapshotCache<com.android.server.pm.PackageSetting>(this, this) { // from class: com.android.server.pm.PackageSetting.1
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.PackageSetting createSnapshot() {
                return new com.android.server.pm.PackageSetting((com.android.server.pm.PackageSetting) this.mSource, true);
            }
        };
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public PackageSetting(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull java.io.File file, int i, int i2, @android.annotation.NonNull java.util.UUID uuid) {
        super(i, i2);
        this.keySetData = new com.android.server.pm.PackageKeySetData();
        this.mUserStates = new android.util.SparseArray<>();
        this.categoryOverride = -1;
        this.pkgState = new com.android.server.pm.pkg.PackageStateUnserialized(this);
        this.mAppMetadataSource = 0;
        this.mName = str;
        this.mRealName = str2;
        this.mPath = file;
        this.mPathString = file.toString();
        this.signatures = new com.android.server.pm.PackageSignatures();
        this.installSource = com.android.server.pm.InstallSource.EMPTY;
        this.mDomainSetId = uuid;
        this.mSnapshot = makeCache();
    }

    PackageSetting(com.android.server.pm.PackageSetting packageSetting) {
        this(packageSetting, false);
    }

    PackageSetting(com.android.server.pm.PackageSetting packageSetting, java.lang.String str) {
        this(packageSetting, false);
        this.mRealName = str;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public PackageSetting(@android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting, boolean z) {
        super(packageSetting);
        this.keySetData = new com.android.server.pm.PackageKeySetData();
        this.mUserStates = new android.util.SparseArray<>();
        this.categoryOverride = -1;
        this.pkgState = new com.android.server.pm.pkg.PackageStateUnserialized(this);
        this.mAppMetadataSource = 0;
        copyPackageSetting(packageSetting, z);
        if (z) {
            this.mSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        } else {
            this.mSnapshot = makeCache();
        }
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.pm.PackageSetting snapshot() {
        return this.mSnapshot.snapshot();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.List<android.content.pm.UserInfo> list, com.android.server.pm.permission.LegacyPermissionDataProvider legacyPermissionDataProvider) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mRealName != null ? this.mRealName : this.mName);
        protoOutputStream.write(1120986464258L, this.mAppId);
        protoOutputStream.write(1120986464259L, this.versionCode);
        protoOutputStream.write(1112396529670L, this.lastUpdateTime);
        protoOutputStream.write(1138166333447L, this.installSource.mInstallerPackageName);
        if (this.pkg != null) {
            protoOutputStream.write(1138166333444L, this.pkg.getVersionName());
            long start2 = protoOutputStream.start(2246267895816L);
            protoOutputStream.write(1138166333441L, "base");
            protoOutputStream.write(1120986464258L, this.pkg.getBaseRevisionCode());
            protoOutputStream.end(start2);
            for (int i = 0; i < this.pkg.getSplitNames().length; i++) {
                long start3 = protoOutputStream.start(2246267895816L);
                protoOutputStream.write(1138166333441L, this.pkg.getSplitNames()[i]);
                protoOutputStream.write(1120986464258L, this.pkg.getSplitRevisionCodes()[i]);
                protoOutputStream.end(start3);
            }
            long start4 = protoOutputStream.start(1146756268042L);
            protoOutputStream.write(1138166333441L, this.installSource.mInitiatingPackageName);
            protoOutputStream.write(1138166333442L, this.installSource.mOriginatingPackageName);
            protoOutputStream.write(1138166333443L, this.installSource.mUpdateOwnerPackageName);
            protoOutputStream.end(start4);
        }
        protoOutputStream.write(1133871366146L, isLoading());
        writeUsersInfoToProto(protoOutputStream, 2246267895817L);
        writePackageUserPermissionsProto(protoOutputStream, 2246267895820L, list, legacyPermissionDataProvider);
        protoOutputStream.end(start);
    }

    public com.android.server.pm.PackageSetting setAppId(int i) {
        this.mAppId = i;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setCpuAbiOverride(java.lang.String str) {
        this.mCpuAbiOverride = str;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setFirstInstallTimeFromReplaced(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            long firstInstallTimeMillis = packageStateInternal.getUserStateOrDefault(i).getFirstInstallTimeMillis();
            if (firstInstallTimeMillis != 0) {
                modifyUserState(i).setFirstInstallTimeMillis(firstInstallTimeMillis);
            }
        }
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setFirstInstallTime(long j, int i) {
        if (i == -1) {
            int size = this.mUserStates.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mUserStates.valueAt(i2).setFirstInstallTimeMillis(j);
            }
        } else {
            modifyUserState(i).setFirstInstallTimeMillis(j);
        }
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setForceQueryableOverride(boolean z) {
        setBoolean(4, z);
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setInstallerPackage(@android.annotation.Nullable java.lang.String str, int i) {
        this.installSource = this.installSource.setInstallerPackage(str, i);
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setUpdateOwnerPackage(@android.annotation.Nullable java.lang.String str) {
        this.installSource = this.installSource.setUpdateOwnerPackageName(str);
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setInstallSource(com.android.server.pm.InstallSource installSource) {
        java.util.Objects.requireNonNull(installSource);
        this.installSource = installSource;
        onChanged();
        return this;
    }

    com.android.server.pm.PackageSetting removeInstallerPackage(@android.annotation.Nullable java.lang.String str) {
        this.installSource = this.installSource.removeInstallerPackage(str);
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setIsOrphaned(boolean z) {
        this.installSource = this.installSource.setIsOrphaned(z);
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setKeySetData(com.android.server.pm.PackageKeySetData packageKeySetData) {
        this.keySetData = packageKeySetData;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setLastModifiedTime(long j) {
        this.mLastModifiedTime = j;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setLastUpdateTime(long j) {
        this.lastUpdateTime = j;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setLongVersionCode(long j) {
        this.versionCode = j;
        onChanged();
        return this;
    }

    public boolean setMimeGroup(java.lang.String str, android.util.ArraySet<java.lang.String> arraySet) {
        java.util.Set<java.lang.String> set = this.mimeGroups == null ? null : this.mimeGroups.get(str);
        if (set == null) {
            throw new java.lang.IllegalArgumentException("Unknown MIME group " + str + " for package " + this.mName);
        }
        boolean z = !arraySet.equals(set);
        this.mimeGroups.put(str, arraySet);
        if (z) {
            onChanged();
        }
        return z;
    }

    public com.android.server.pm.PackageSetting setPkg(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        this.pkg = (com.android.internal.pm.parsing.pkg.AndroidPackageInternal) androidPackage;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setPkgStateLibraryFiles(@android.annotation.NonNull java.util.Collection<java.lang.String> collection) {
        java.util.List<java.lang.String> usesLibraryFiles = getUsesLibraryFiles();
        if (usesLibraryFiles.size() != collection.size() || !usesLibraryFiles.containsAll(collection)) {
            this.pkgState.setUsesLibraryFiles(new java.util.ArrayList(collection));
            onChanged();
        }
        return this;
    }

    public com.android.server.pm.PackageSetting setPrimaryCpuAbi(java.lang.String str) {
        this.mPrimaryCpuAbi = str;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setSecondaryCpuAbi(java.lang.String str) {
        this.mSecondaryCpuAbi = str;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setSignatures(com.android.server.pm.PackageSignatures packageSignatures) {
        this.signatures = packageSignatures;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setVolumeUuid(java.lang.String str) {
        this.volumeUuid = str;
        onChanged();
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isExternalStorage() {
        return (getFlags() & 262144) != 0;
    }

    public com.android.server.pm.PackageSetting setUpdateAvailable(boolean z) {
        setBoolean(2, z);
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setSharedUserAppId(int i) {
        this.mSharedUserAppId = i;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setTargetSdkVersion(int i) {
        this.mTargetSdkVersion = i;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setRestrictUpdateHash(byte[] bArr) {
        this.mRestrictUpdateHash = bArr;
        onChanged();
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public int getSharedUserAppId() {
        return this.mSharedUserAppId;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean hasSharedUser() {
        return this.mSharedUserAppId > 0;
    }

    public com.android.server.pm.PackageSetting setPendingRestore(boolean z) {
        setBoolean(16, z);
        onChanged();
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isPendingRestore() {
        return getBoolean(16);
    }

    public java.lang.String toString() {
        return "PackageSetting{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.mName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mAppId + "}";
    }

    private void copyMimeGroups(@android.annotation.Nullable java.util.Map<java.lang.String, java.util.Set<java.lang.String>> map) {
        if (map == null) {
            this.mimeGroups = null;
            return;
        }
        this.mimeGroups = new android.util.ArrayMap(map.size());
        for (java.lang.String str : map.keySet()) {
            java.util.Set<java.lang.String> set = map.get(str);
            if (set != null) {
                this.mimeGroups.put(str, new android.util.ArraySet(set));
            } else {
                this.mimeGroups.put(str, new android.util.ArraySet());
            }
        }
    }

    public void updateFrom(com.android.server.pm.PackageSetting packageSetting) {
        copyPackageSetting(packageSetting, false);
        updateMimeGroups(packageSetting.mimeGroups != null ? packageSetting.mimeGroups.keySet() : null);
        onChanged();
    }

    com.android.server.pm.PackageSetting updateMimeGroups(@android.annotation.Nullable java.util.Set<java.lang.String> set) {
        if (set == null) {
            this.mimeGroups = null;
            return this;
        }
        if (this.mimeGroups == null) {
            this.mimeGroups = java.util.Collections.emptyMap();
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(set.size());
        for (java.lang.String str : set) {
            if (this.mimeGroups.containsKey(str)) {
                arrayMap.put(str, this.mimeGroups.get(str));
            } else {
                arrayMap.put(str, new android.util.ArraySet());
            }
        }
        onChanged();
        this.mimeGroups = arrayMap;
        return this;
    }

    @Override // com.android.server.pm.SettingBase, com.android.server.pm.pkg.PackageStateInternal
    @java.lang.Deprecated
    public com.android.server.pm.permission.LegacyPermissionState getLegacyPermissionState() {
        return super.getLegacyPermissionState();
    }

    public com.android.server.pm.PackageSetting setInstallPermissionsFixed(boolean z) {
        setBoolean(1, z);
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isPrivileged() {
        return (getPrivateFlags() & 8) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isOem() {
        return (getPrivateFlags() & 131072) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isVendor() {
        return (getPrivateFlags() & 262144) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isProduct() {
        return (getPrivateFlags() & 524288) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isRequiredForSystemUser() {
        return (getPrivateFlags() & 512) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isSystemExt() {
        return (getPrivateFlags() & 2097152) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isOdm() {
        return (getPrivateFlags() & 1073741824) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isSystem() {
        return (getFlags() & 1) != 0;
    }

    public boolean isRequestLegacyExternalStorage() {
        return (getPrivateFlags() & 536870912) != 0;
    }

    public boolean isUserDataFragile() {
        return (getPrivateFlags() & 16777216) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public android.content.pm.SigningDetails getSigningDetails() {
        return this.signatures.mSigningDetails;
    }

    public com.android.server.pm.PackageSetting setSigningDetails(android.content.pm.SigningDetails signingDetails) {
        this.signatures.mSigningDetails = signingDetails;
        onChanged();
        return this;
    }

    public void copyPackageSetting(com.android.server.pm.PackageSetting packageSetting, boolean z) {
        java.lang.String[] strArr;
        long[] jArr;
        boolean[] zArr;
        java.lang.String[] strArr2;
        super.copySettingBase(packageSetting);
        this.mBooleans = packageSetting.mBooleans;
        this.mSharedUserAppId = packageSetting.mSharedUserAppId;
        this.mLoadingProgress = packageSetting.mLoadingProgress;
        this.mLoadingCompletedTime = packageSetting.mLoadingCompletedTime;
        this.legacyNativeLibraryPath = packageSetting.legacyNativeLibraryPath;
        this.mName = packageSetting.mName;
        this.mRealName = packageSetting.mRealName;
        this.mAppId = packageSetting.mAppId;
        this.pkg = packageSetting.pkg;
        this.mPath = packageSetting.mPath;
        this.mPathString = packageSetting.mPathString;
        this.mOldPaths = packageSetting.mOldPaths == null ? null : new java.util.LinkedHashSet<>(packageSetting.mOldPaths);
        this.mPrimaryCpuAbi = packageSetting.mPrimaryCpuAbi;
        this.mSecondaryCpuAbi = packageSetting.mSecondaryCpuAbi;
        this.mCpuAbiOverride = packageSetting.mCpuAbiOverride;
        this.mLastModifiedTime = packageSetting.mLastModifiedTime;
        this.lastUpdateTime = packageSetting.lastUpdateTime;
        this.versionCode = packageSetting.versionCode;
        this.signatures = packageSetting.signatures;
        this.keySetData = new com.android.server.pm.PackageKeySetData(packageSetting.keySetData);
        this.installSource = packageSetting.installSource;
        this.volumeUuid = packageSetting.volumeUuid;
        this.categoryOverride = packageSetting.categoryOverride;
        this.mDomainSetId = packageSetting.mDomainSetId;
        this.mAppMetadataFilePath = packageSetting.mAppMetadataFilePath;
        this.mAppMetadataSource = packageSetting.mAppMetadataSource;
        this.mTargetSdkVersion = packageSetting.mTargetSdkVersion;
        this.mRestrictUpdateHash = packageSetting.mRestrictUpdateHash == null ? null : (byte[]) packageSetting.mRestrictUpdateHash.clone();
        if (packageSetting.usesSdkLibraries != null) {
            strArr = (java.lang.String[]) java.util.Arrays.copyOf(packageSetting.usesSdkLibraries, packageSetting.usesSdkLibraries.length);
        } else {
            strArr = null;
        }
        this.usesSdkLibraries = strArr;
        if (packageSetting.usesSdkLibrariesVersionsMajor != null) {
            jArr = java.util.Arrays.copyOf(packageSetting.usesSdkLibrariesVersionsMajor, packageSetting.usesSdkLibrariesVersionsMajor.length);
        } else {
            jArr = null;
        }
        this.usesSdkLibrariesVersionsMajor = jArr;
        if (packageSetting.usesSdkLibrariesOptional != null) {
            zArr = java.util.Arrays.copyOf(packageSetting.usesSdkLibrariesOptional, packageSetting.usesSdkLibrariesOptional.length);
        } else {
            zArr = null;
        }
        this.usesSdkLibrariesOptional = zArr;
        if (packageSetting.usesStaticLibraries != null) {
            strArr2 = (java.lang.String[]) java.util.Arrays.copyOf(packageSetting.usesStaticLibraries, packageSetting.usesStaticLibraries.length);
        } else {
            strArr2 = null;
        }
        this.usesStaticLibraries = strArr2;
        this.usesStaticLibrariesVersions = packageSetting.usesStaticLibrariesVersions != null ? java.util.Arrays.copyOf(packageSetting.usesStaticLibrariesVersions, packageSetting.usesStaticLibrariesVersions.length) : null;
        this.mUserStates.clear();
        for (int i = 0; i < packageSetting.mUserStates.size(); i++) {
            if (z) {
                this.mUserStates.put(packageSetting.mUserStates.keyAt(i), packageSetting.mUserStates.valueAt(i).snapshot());
            } else {
                com.android.server.pm.pkg.PackageUserStateImpl valueAt = packageSetting.mUserStates.valueAt(i);
                valueAt.setWatchable(this);
                this.mUserStates.put(packageSetting.mUserStates.keyAt(i), valueAt);
            }
        }
        copyMimeGroups(packageSetting.mimeGroups);
        this.pkgState.updateFrom(packageSetting.pkgState);
        onChanged();
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.pm.pkg.PackageUserStateImpl modifyUserState(int i) {
        com.android.server.pm.pkg.PackageUserStateImpl packageUserStateImpl = this.mUserStates.get(i);
        if (packageUserStateImpl == null) {
            com.android.server.pm.pkg.PackageUserStateImpl packageUserStateImpl2 = new com.android.server.pm.pkg.PackageUserStateImpl(this);
            this.mUserStates.put(i, packageUserStateImpl2);
            onChanged();
            return packageUserStateImpl2;
        }
        return packageUserStateImpl;
    }

    public com.android.server.pm.pkg.PackageUserStateImpl getOrCreateUserState(int i) {
        com.android.server.pm.pkg.PackageUserStateImpl packageUserStateImpl = this.mUserStates.get(i);
        if (packageUserStateImpl == null) {
            com.android.server.pm.pkg.PackageUserStateImpl packageUserStateImpl2 = new com.android.server.pm.pkg.PackageUserStateImpl(this);
            this.mUserStates.put(i, packageUserStateImpl2);
            return packageUserStateImpl2;
        }
        return packageUserStateImpl;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateInternal readUserState(int i) {
        com.android.server.pm.pkg.PackageUserStateImpl packageUserStateImpl = this.mUserStates.get(i);
        if (packageUserStateImpl == null) {
            return com.android.server.pm.pkg.PackageUserStateInternal.DEFAULT;
        }
        return packageUserStateImpl;
    }

    void setEnabled(int i, int i2, java.lang.String str) {
        modifyUserState(i2).setEnabledState(i).setLastDisableAppCaller(str);
        onChanged();
    }

    int getEnabled(int i) {
        return readUserState(i).getEnabledState();
    }

    void setInstalled(boolean z, int i) {
        modifyUserState(i).setInstalled(z);
        onChanged();
    }

    void setArchiveState(com.android.server.pm.pkg.ArchiveState archiveState, int i) {
        modifyUserState(i).setArchiveState(archiveState);
        onChanged();
    }

    boolean getInstalled(int i) {
        return readUserState(i).isInstalled();
    }

    boolean isArchived(int i) {
        return com.android.server.pm.PackageArchiver.isArchived(readUserState(i));
    }

    int getInstallReason(int i) {
        return readUserState(i).getInstallReason();
    }

    void setInstallReason(int i, int i2) {
        modifyUserState(i2).setInstallReason(i);
        onChanged();
    }

    int getUninstallReason(int i) {
        return readUserState(i).getUninstallReason();
    }

    void setUninstallReason(int i, int i2) {
        modifyUserState(i2).setUninstallReason(i);
        onChanged();
    }

    @android.annotation.NonNull
    android.content.pm.overlay.OverlayPaths getOverlayPaths(int i) {
        return readUserState(i).getOverlayPaths();
    }

    boolean setOverlayPathsForLibrary(java.lang.String str, android.content.pm.overlay.OverlayPaths overlayPaths, int i) {
        boolean sharedLibraryOverlayPaths = modifyUserState(i).setSharedLibraryOverlayPaths(str, overlayPaths);
        onChanged();
        return sharedLibraryOverlayPaths;
    }

    boolean isInstalledOnAnyOtherUser(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 != i && readUserState(i2).isInstalled()) {
                return true;
            }
        }
        return false;
    }

    boolean hasDataOnAnyOtherUser(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 != i && readUserState(i2).dataExists()) {
                return true;
            }
        }
        return false;
    }

    int[] queryInstalledUsers(int[] iArr, boolean z) {
        int i = 0;
        for (int i2 : iArr) {
            if (getInstalled(i2) == z) {
                i++;
            }
        }
        int[] iArr2 = new int[i];
        int i3 = 0;
        for (int i4 : iArr) {
            if (getInstalled(i4) == z) {
                iArr2[i3] = i4;
                i3++;
            }
        }
        return iArr2;
    }

    int[] queryUsersInstalledOrHasData(int[] iArr) {
        int i = 0;
        for (int i2 : iArr) {
            if (getInstalled(i2) || readUserState(i2).dataExists()) {
                i++;
            }
        }
        int[] iArr2 = new int[i];
        int i3 = 0;
        for (int i4 : iArr) {
            if (getInstalled(i4) || readUserState(i4).dataExists()) {
                iArr2[i3] = i4;
                i3++;
            }
        }
        return iArr2;
    }

    long getCeDataInode(int i) {
        return readUserState(i).getCeDataInode();
    }

    long getDeDataInode(int i) {
        return readUserState(i).getDeDataInode();
    }

    void setCeDataInode(long j, int i) {
        modifyUserState(i).setCeDataInode(j);
        onChanged();
    }

    void setDeDataInode(long j, int i) {
        modifyUserState(i).setDeDataInode(j);
        onChanged();
    }

    boolean getStopped(int i) {
        return readUserState(i).isStopped();
    }

    void setStopped(boolean z, int i) {
        modifyUserState(i).setStopped(z);
        onChanged();
    }

    public com.android.server.pm.PackageSetting setScannedAsStoppedSystemApp(boolean z) {
        setBoolean(8, z);
        onChanged();
        return this;
    }

    boolean getNotLaunched(int i) {
        return readUserState(i).isNotLaunched();
    }

    void setNotLaunched(boolean z, int i) {
        modifyUserState(i).setNotLaunched(z);
        onChanged();
    }

    boolean getHidden(int i) {
        return readUserState(i).isHidden();
    }

    void setHidden(boolean z, int i) {
        modifyUserState(i).setHidden(z);
        onChanged();
    }

    int getDistractionFlags(int i) {
        return readUserState(i).getDistractionFlags();
    }

    void setDistractionFlags(int i, int i2) {
        modifyUserState(i2).setDistractionFlags(i);
        onChanged();
    }

    public boolean getInstantApp(int i) {
        return readUserState(i).isInstantApp();
    }

    void setInstantApp(boolean z, int i) {
        modifyUserState(i).setInstantApp(z);
        onChanged();
    }

    boolean getVirtualPreload(int i) {
        return readUserState(i).isVirtualPreload();
    }

    void setVirtualPreload(boolean z, int i) {
        modifyUserState(i).setVirtualPreload(z);
        onChanged();
    }

    void setUserState(int i, long j, long j2, int i2, boolean z, boolean z2, boolean z3, boolean z4, int i3, android.util.ArrayMap<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> arrayMap, boolean z5, boolean z6, java.lang.String str, android.util.ArraySet<java.lang.String> arraySet, android.util.ArraySet<java.lang.String> arraySet2, int i4, int i5, java.lang.String str2, java.lang.String str3, long j3, int i6, com.android.server.pm.pkg.ArchiveState archiveState) {
        modifyUserState(i).setSuspendParams(arrayMap).setCeDataInode(j).setDeDataInode(j2).setEnabledState(i2).setInstalled(z).setStopped(z2).setNotLaunched(z3).setHidden(z4).setDistractionFlags(i3).setLastDisableAppCaller(str).setEnabledComponents(arraySet).setDisabledComponents(arraySet2).setInstallReason(i4).setUninstallReason(i5).setInstantApp(z5).setVirtualPreload(z6).setHarmfulAppWarning(str2).setSplashScreenTheme(str3).setFirstInstallTimeMillis(j3).setMinAspectRatio(i6).setArchiveState(archiveState);
        onChanged();
    }

    void setUserState(int i, com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal) {
        setUserState(i, packageUserStateInternal.getCeDataInode(), packageUserStateInternal.getDeDataInode(), packageUserStateInternal.getEnabledState(), packageUserStateInternal.isInstalled(), packageUserStateInternal.isStopped(), packageUserStateInternal.isNotLaunched(), packageUserStateInternal.isHidden(), packageUserStateInternal.getDistractionFlags(), packageUserStateInternal.getSuspendParams() == null ? null : packageUserStateInternal.getSuspendParams().untrackedStorage(), packageUserStateInternal.isInstantApp(), packageUserStateInternal.isVirtualPreload(), packageUserStateInternal.getLastDisableAppCaller(), packageUserStateInternal.getEnabledComponentsNoCopy() == null ? null : packageUserStateInternal.getEnabledComponentsNoCopy().untrackedStorage(), packageUserStateInternal.getDisabledComponentsNoCopy() == null ? null : packageUserStateInternal.getDisabledComponentsNoCopy().untrackedStorage(), packageUserStateInternal.getInstallReason(), packageUserStateInternal.getUninstallReason(), packageUserStateInternal.getHarmfulAppWarning(), packageUserStateInternal.getSplashScreenTheme(), packageUserStateInternal.getFirstInstallTimeMillis(), packageUserStateInternal.getMinAspectRatio(), packageUserStateInternal.getArchiveState());
    }

    com.android.server.utils.WatchedArraySet<java.lang.String> getEnabledComponents(int i) {
        return readUserState(i).getEnabledComponentsNoCopy();
    }

    com.android.server.utils.WatchedArraySet<java.lang.String> getDisabledComponents(int i) {
        return readUserState(i).getDisabledComponentsNoCopy();
    }

    void setEnabledComponents(com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet, int i) {
        modifyUserState(i).setEnabledComponents(watchedArraySet);
        onChanged();
    }

    void setDisabledComponents(com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet, int i) {
        modifyUserState(i).setDisabledComponents(watchedArraySet);
        onChanged();
    }

    void setEnabledComponentsCopy(com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet, int i) {
        modifyUserState(i).setEnabledComponents(watchedArraySet != null ? watchedArraySet.untrackedStorage() : null);
        onChanged();
    }

    void setDisabledComponentsCopy(com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet, int i) {
        modifyUserState(i).setDisabledComponents(watchedArraySet != null ? watchedArraySet.untrackedStorage() : null);
        onChanged();
    }

    com.android.server.pm.pkg.PackageUserStateImpl modifyUserStateComponents(int i, boolean z, boolean z2) {
        boolean z3;
        com.android.server.pm.pkg.PackageUserStateImpl modifyUserState = modifyUserState(i);
        boolean z4 = true;
        if (z && modifyUserState.getDisabledComponentsNoCopy() == null) {
            modifyUserState.setDisabledComponents(new android.util.ArraySet<>(1));
            z3 = true;
        } else {
            z3 = false;
        }
        if (z2 && modifyUserState.getEnabledComponentsNoCopy() == null) {
            modifyUserState.setEnabledComponents(new android.util.ArraySet<>(1));
        } else {
            z4 = z3;
        }
        if (z4) {
            onChanged();
        }
        return modifyUserState;
    }

    void addDisabledComponent(java.lang.String str, int i) {
        modifyUserStateComponents(i, true, false).getDisabledComponentsNoCopy().add(str);
        onChanged();
    }

    void addEnabledComponent(java.lang.String str, int i) {
        modifyUserStateComponents(i, false, true).getEnabledComponentsNoCopy().add(str);
        onChanged();
    }

    boolean enableComponentLPw(java.lang.String str, int i) {
        com.android.server.pm.pkg.PackageUserStateImpl modifyUserStateComponents = modifyUserStateComponents(i, false, true);
        boolean add = modifyUserStateComponents.getEnabledComponentsNoCopy().add(str) | (modifyUserStateComponents.getDisabledComponentsNoCopy() != null ? modifyUserStateComponents.getDisabledComponentsNoCopy().remove(str) : false);
        if (add) {
            onChanged();
        }
        return add;
    }

    boolean disableComponentLPw(java.lang.String str, int i) {
        com.android.server.pm.pkg.PackageUserStateImpl modifyUserStateComponents = modifyUserStateComponents(i, true, false);
        boolean add = modifyUserStateComponents.getDisabledComponentsNoCopy().add(str) | (modifyUserStateComponents.getEnabledComponentsNoCopy() != null ? modifyUserStateComponents.getEnabledComponentsNoCopy().remove(str) : false);
        if (add) {
            onChanged();
        }
        return add;
    }

    boolean restoreComponentLPw(java.lang.String str, int i) {
        com.android.server.pm.pkg.PackageUserStateImpl modifyUserStateComponents = modifyUserStateComponents(i, true, true);
        boolean remove = (modifyUserStateComponents.getDisabledComponentsNoCopy() != null ? modifyUserStateComponents.getDisabledComponentsNoCopy().remove(str) : false) | (modifyUserStateComponents.getEnabledComponentsNoCopy() != null ? modifyUserStateComponents.getEnabledComponentsNoCopy().remove(str) : false);
        if (remove) {
            onChanged();
        }
        return remove;
    }

    void restoreComponentSettings(int i) {
        com.android.server.pm.pkg.PackageUserStateImpl modifyUserStateComponents = modifyUserStateComponents(i, true, true);
        com.android.server.utils.WatchedArraySet<java.lang.String> enabledComponentsNoCopy = modifyUserStateComponents.getEnabledComponentsNoCopy();
        com.android.server.utils.WatchedArraySet<java.lang.String> disabledComponentsNoCopy = modifyUserStateComponents.getDisabledComponentsNoCopy();
        boolean z = false;
        for (int size = enabledComponentsNoCopy.size() - 1; size >= 0; size--) {
            if (!com.android.server.pm.parsing.pkg.AndroidPackageUtils.hasComponentClassName(this.pkg, enabledComponentsNoCopy.valueAt(size))) {
                enabledComponentsNoCopy.removeAt(size);
                z = true;
            }
        }
        for (int size2 = disabledComponentsNoCopy.size() - 1; size2 >= 0; size2--) {
            if (!com.android.server.pm.parsing.pkg.AndroidPackageUtils.hasComponentClassName(this.pkg, disabledComponentsNoCopy.valueAt(size2))) {
                disabledComponentsNoCopy.removeAt(size2);
                z = true;
            }
        }
        if (z) {
            onChanged();
        }
    }

    int getCurrentEnabledStateLPr(java.lang.String str, int i) {
        com.android.server.pm.pkg.PackageUserStateInternal readUserState = readUserState(i);
        if (readUserState.getEnabledComponentsNoCopy() != null && readUserState.getEnabledComponentsNoCopy().contains(str)) {
            return 1;
        }
        if (readUserState.getDisabledComponentsNoCopy() != null && readUserState.getDisabledComponentsNoCopy().contains(str)) {
            return 2;
        }
        return 0;
    }

    void removeUser(int i) {
        this.mUserStates.delete(i);
        onChanged();
    }

    public int[] getNotInstalledUserIds() {
        int size = this.mUserStates.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (!this.mUserStates.valueAt(i2).isInstalled()) {
                i++;
            }
        }
        if (i == 0) {
            return libcore.util.EmptyArray.INT;
        }
        int[] iArr = new int[i];
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            if (!this.mUserStates.valueAt(i4).isInstalled()) {
                iArr[i3] = this.mUserStates.keyAt(i4);
                i3++;
            }
        }
        return iArr;
    }

    void writePackageUserPermissionsProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.List<android.content.pm.UserInfo> list, com.android.server.pm.permission.LegacyPermissionDataProvider legacyPermissionDataProvider) {
        for (android.content.pm.UserInfo userInfo : list) {
            long start = protoOutputStream.start(2246267895820L);
            protoOutputStream.write(1120986464257L, userInfo.id);
            for (com.android.server.pm.permission.LegacyPermissionState.PermissionState permissionState : legacyPermissionDataProvider.getLegacyPermissionState(this.mAppId).getPermissionStates(userInfo.id)) {
                if (permissionState.isGranted()) {
                    protoOutputStream.write(2237677961218L, permissionState.getName());
                }
            }
            protoOutputStream.end(start);
        }
    }

    protected void writeUsersInfoToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        int i;
        int size = this.mUserStates.size();
        for (int i2 = 0; i2 < size; i2++) {
            long start = protoOutputStream.start(j);
            int keyAt = this.mUserStates.keyAt(i2);
            com.android.server.pm.pkg.PackageUserStateImpl valueAt = this.mUserStates.valueAt(i2);
            protoOutputStream.write(1120986464257L, keyAt);
            if (valueAt.isInstantApp()) {
                i = 2;
            } else if (valueAt.isInstalled()) {
                i = 1;
            } else {
                i = 0;
            }
            protoOutputStream.write(1159641169922L, i);
            protoOutputStream.write(1133871366147L, valueAt.isHidden());
            protoOutputStream.write(1120986464266L, valueAt.getDistractionFlags());
            protoOutputStream.write(1133871366148L, valueAt.isSuspended());
            if (valueAt.isSuspended()) {
                for (int i3 = 0; i3 < valueAt.getSuspendParams().size(); i3++) {
                    protoOutputStream.write(2237677961225L, valueAt.getSuspendParams().keyAt(i3).packageName);
                }
            }
            protoOutputStream.write(1133871366149L, valueAt.isStopped());
            protoOutputStream.write(1133871366150L, !valueAt.isNotLaunched());
            protoOutputStream.write(1159641169927L, valueAt.getEnabledState());
            protoOutputStream.write(1138166333448L, valueAt.getLastDisableAppCaller());
            protoOutputStream.write(1120986464267L, valueAt.getFirstInstallTimeMillis());
            writeArchiveState(protoOutputStream, valueAt.getArchiveState());
            protoOutputStream.end(start);
        }
    }

    private static void writeArchiveState(android.util.proto.ProtoOutputStream protoOutputStream, com.android.server.pm.pkg.ArchiveState archiveState) {
        if (archiveState == null) {
            return;
        }
        long start = protoOutputStream.start(1146756268044L);
        for (com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo archiveActivityInfo : archiveState.getActivityInfos()) {
            long start2 = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1138166333441L, archiveActivityInfo.getTitle());
            protoOutputStream.write(1138166333444L, archiveActivityInfo.getOriginalComponentName().flattenToString());
            if (archiveActivityInfo.getIconBitmap() != null) {
                protoOutputStream.write(1138166333442L, archiveActivityInfo.getIconBitmap().toAbsolutePath().toString());
            }
            if (archiveActivityInfo.getMonochromeIconBitmap() != null) {
                protoOutputStream.write(1138166333443L, archiveActivityInfo.getMonochromeIconBitmap().toAbsolutePath().toString());
            }
            protoOutputStream.end(start2);
        }
        protoOutputStream.write(1138166333442L, archiveState.getInstallerTitle());
        protoOutputStream.end(start);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public com.android.server.pm.PackageSetting setPath(@android.annotation.NonNull java.io.File file) {
        this.mPath = file;
        this.mPathString = file.toString();
        onChanged();
        return this;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public com.android.server.pm.PackageSetting addOldPath(@android.annotation.NonNull java.io.File file) {
        if (this.mOldPaths == null) {
            this.mOldPaths = new java.util.LinkedHashSet<>();
        }
        this.mOldPaths.add(file);
        onChanged();
        return this;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public com.android.server.pm.PackageSetting removeOldPath(@android.annotation.Nullable java.io.File file) {
        if (file == null || this.mOldPaths == null) {
            return this;
        }
        if (this.mOldPaths.remove(file)) {
            onChanged();
        }
        return this;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public boolean overrideNonLocalizedLabelAndIcon(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Integer num, int i) {
        boolean overrideLabelAndIcon = modifyUserState(i).overrideLabelAndIcon(componentName, str, num);
        onChanged();
        return overrideLabelAndIcon;
    }

    public void resetOverrideComponentLabelIcon(int i) {
        modifyUserState(i).resetOverrideComponentLabelIcon();
        onChanged();
    }

    @android.annotation.Nullable
    public java.lang.String getSplashScreenTheme(int i) {
        return readUserState(i).getSplashScreenTheme();
    }

    public boolean isIncremental() {
        return android.os.incremental.IncrementalManager.isIncrementalPath(this.mPathString);
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public boolean isLoading() {
        return java.lang.Math.abs(1.0f - this.mLoadingProgress) >= 1.0E-8f;
    }

    public com.android.server.pm.PackageSetting setLoadingProgress(float f) {
        if (this.mLoadingProgress < f) {
            this.mLoadingProgress = f;
            onChanged();
        }
        return this;
    }

    public com.android.server.pm.PackageSetting setLoadingCompletedTime(long j) {
        this.mLoadingCompletedTime = j;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setAppMetadataFilePath(java.lang.String str) {
        this.mAppMetadataFilePath = str;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setAppMetadataSource(int i) {
        this.mAppMetadataSource = i;
        onChanged();
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public long getVersionCode() {
        return this.versionCode;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.Nullable
    public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getMimeGroups() {
        return com.android.internal.util.CollectionUtils.isEmpty(this.mimeGroups) ? java.util.Collections.emptyMap() : java.util.Collections.unmodifiableMap(this.mimeGroups);
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public java.lang.String getPackageName() {
        return this.mName;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.Nullable
    public com.android.server.pm.pkg.AndroidPackage getAndroidPackage() {
        return getPkg();
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public android.content.pm.SigningInfo getSigningInfo() {
        return new android.content.pm.SigningInfo(this.signatures.mSigningDetails);
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public java.lang.String[] getUsesSdkLibraries() {
        return this.usesSdkLibraries == null ? libcore.util.EmptyArray.STRING : this.usesSdkLibraries;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public long[] getUsesSdkLibrariesVersionsMajor() {
        return this.usesSdkLibrariesVersionsMajor == null ? libcore.util.EmptyArray.LONG : this.usesSdkLibrariesVersionsMajor;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public boolean[] getUsesSdkLibrariesOptional() {
        return this.usesSdkLibrariesOptional == null ? libcore.util.EmptyArray.BOOLEAN : this.usesSdkLibrariesOptional;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public java.lang.String[] getUsesStaticLibraries() {
        return this.usesStaticLibraries == null ? libcore.util.EmptyArray.STRING : this.usesStaticLibraries;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public long[] getUsesStaticLibrariesVersions() {
        return this.usesStaticLibrariesVersions == null ? libcore.util.EmptyArray.LONG : this.usesStaticLibrariesVersions;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.pkg.SharedLibrary> getSharedLibraryDependencies() {
        return java.util.Collections.unmodifiableList(this.pkgState.getUsesLibraryInfos());
    }

    @android.annotation.NonNull
    public com.android.server.pm.PackageSetting addUsesLibraryInfo(@android.annotation.NonNull android.content.pm.SharedLibraryInfo sharedLibraryInfo) {
        this.pkgState.addUsesLibraryInfo(new com.android.server.pm.pkg.SharedLibraryWrapper(sharedLibraryInfo));
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public java.util.List<java.lang.String> getUsesLibraryFiles() {
        return java.util.Collections.unmodifiableList(this.pkgState.getUsesLibraryFiles());
    }

    @android.annotation.NonNull
    public com.android.server.pm.PackageSetting addUsesLibraryFile(java.lang.String str) {
        this.pkgState.addUsesLibraryFile(str);
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isHiddenUntilInstalled() {
        return this.pkgState.isHiddenUntilInstalled();
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public long[] getLastPackageUsageTime() {
        return this.pkgState.getLastPackageUsageTimeInMills();
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isUpdatedSystemApp() {
        return this.pkgState.isUpdatedSystemApp();
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isApkInUpdatedApex() {
        return this.pkgState.isApkInUpdatedApex();
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.Nullable
    public java.lang.String getApexModuleName() {
        return this.pkgState.getApexModuleName();
    }

    public com.android.server.pm.PackageSetting setDomainSetId(@android.annotation.NonNull java.util.UUID uuid) {
        this.mDomainSetId = uuid;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setCategoryOverride(int i) {
        this.categoryOverride = i;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setLegacyNativeLibraryPath(java.lang.String str) {
        this.legacyNativeLibraryPath = str;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setMimeGroups(@android.annotation.Nullable java.util.Map<java.lang.String, java.util.Set<java.lang.String>> map) {
        if (map != null) {
            copyMimeGroups(map);
            onChanged();
        }
        return this;
    }

    public com.android.server.pm.PackageSetting setUsesSdkLibraries(java.lang.String[] strArr) {
        this.usesSdkLibraries = strArr;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setUsesSdkLibrariesVersionsMajor(long[] jArr) {
        this.usesSdkLibrariesVersionsMajor = jArr;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setUsesSdkLibrariesOptional(boolean[] zArr) {
        this.usesSdkLibrariesOptional = zArr;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setUsesStaticLibraries(java.lang.String[] strArr) {
        this.usesStaticLibraries = strArr;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setUsesStaticLibrariesVersions(long[] jArr) {
        this.usesStaticLibrariesVersions = jArr;
        onChanged();
        return this;
    }

    public com.android.server.pm.PackageSetting setApexModuleName(@android.annotation.Nullable java.lang.String str) {
        this.pkgState.setApexModuleName(str);
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageStateUnserialized getTransientState() {
        return this.pkgState;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal, com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public android.util.SparseArray<? extends com.android.server.pm.pkg.PackageUserStateInternal> getUserStates() {
        return this.mUserStates;
    }

    public com.android.server.pm.PackageSetting addMimeTypes(java.lang.String str, java.util.Set<java.lang.String> set) {
        if (this.mimeGroups == null) {
            this.mimeGroups = new android.util.ArrayMap();
        }
        java.util.Set<java.lang.String> set2 = this.mimeGroups.get(str);
        if (set2 == null) {
            set2 = new android.util.ArraySet<>();
            this.mimeGroups.put(str, set2);
        }
        set2.addAll(set);
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserState getStateForUser(@android.annotation.NonNull android.os.UserHandle userHandle) {
        com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal = getUserStates().get(userHandle.getIdentifier());
        return packageUserStateInternal == null ? com.android.server.pm.pkg.PackageUserState.DEFAULT : packageUserStateInternal;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.Nullable
    public java.lang.String getPrimaryCpuAbi() {
        if (android.text.TextUtils.isEmpty(this.mPrimaryCpuAbi) && this.pkg != null) {
            return com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawPrimaryCpuAbi(this.pkg);
        }
        return this.mPrimaryCpuAbi;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.Nullable
    public java.lang.String getSecondaryCpuAbi() {
        if (android.text.TextUtils.isEmpty(this.mSecondaryCpuAbi) && this.pkg != null) {
            return com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawSecondaryCpuAbi(this.pkg);
        }
        return this.mSecondaryCpuAbi;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.Nullable
    public java.lang.String getSeInfo() {
        java.lang.String overrideSeInfo = getTransientState().getOverrideSeInfo();
        if (!android.text.TextUtils.isEmpty(overrideSeInfo)) {
            return overrideSeInfo;
        }
        return getTransientState().getSeInfo();
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    @android.annotation.Nullable
    public java.lang.String getPrimaryCpuAbiLegacy() {
        return this.mPrimaryCpuAbi;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    @android.annotation.Nullable
    public java.lang.String getSecondaryCpuAbiLegacy() {
        return this.mSecondaryCpuAbi;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public int getHiddenApiEnforcementPolicy() {
        return com.android.server.pm.parsing.pkg.AndroidPackageUtils.getHiddenApiEnforcementPolicy(getAndroidPackage(), this);
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isApex() {
        return getAndroidPackage() != null && getAndroidPackage().isApex();
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isForceQueryableOverride() {
        return getBoolean(4);
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isUpdateAvailable() {
        return getBoolean(2);
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isInstallPermissionsFixed() {
        return getBoolean(1);
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isDefaultToDeviceProtectedStorage() {
        return (getPrivateFlags() & 32) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isPersistent() {
        return (getFlags() & 8) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public boolean isScannedAsStoppedSystemApp() {
        return getBoolean(8);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public java.lang.String getLegacyNativeLibraryPath() {
        return this.legacyNativeLibraryPath;
    }

    @android.annotation.NonNull
    public java.lang.String getName() {
        return this.mName;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    @android.annotation.Nullable
    public java.lang.String getRealName() {
        return this.mRealName;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public int getAppId() {
        return this.mAppId;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    @android.annotation.Nullable
    public com.android.internal.pm.parsing.pkg.AndroidPackageInternal getPkg() {
        return this.pkg;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.NonNull
    public java.io.File getPath() {
        return this.mPath;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    @android.annotation.NonNull
    public java.lang.String getPathString() {
        return this.mPathString;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    @android.annotation.Nullable
    public java.util.LinkedHashSet<java.io.File> getOldPaths() {
        return this.mOldPaths;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public float getLoadingProgress() {
        return this.mLoadingProgress;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public long getLoadingCompletedTime() {
        return this.mLoadingCompletedTime;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.Nullable
    public java.lang.String getCpuAbiOverride() {
        return this.mCpuAbiOverride;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public long getLastModifiedTime() {
        return this.mLastModifiedTime;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    @android.annotation.NonNull
    public com.android.server.pm.PackageSignatures getSignatures() {
        return this.signatures;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    @android.annotation.NonNull
    public com.android.server.pm.PackageKeySetData getKeySetData() {
        return this.keySetData;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    @android.annotation.NonNull
    public com.android.server.pm.InstallSource getInstallSource() {
        return this.installSource;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.Nullable
    public java.lang.String getVolumeUuid() {
        return this.volumeUuid;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public int getCategoryOverride() {
        return this.categoryOverride;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageStateUnserialized getPkgState() {
        return this.pkgState;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    @android.annotation.NonNull
    public java.util.UUID getDomainSetId() {
        return this.mDomainSetId;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    @android.annotation.Nullable
    public java.lang.String getAppMetadataFilePath() {
        return this.mAppMetadataFilePath;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public int getAppMetadataSource() {
        return this.mAppMetadataSource;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public int getTargetSdkVersion() {
        return this.mTargetSdkVersion;
    }

    @Override // com.android.server.pm.pkg.PackageState
    @android.annotation.Nullable
    public byte[] getRestrictUpdateHash() {
        return this.mRestrictUpdateHash;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
