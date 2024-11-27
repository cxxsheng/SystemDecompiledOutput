package com.android.server.pm;

/* loaded from: classes2.dex */
public final class SharedUserSetting extends com.android.server.pm.SettingBase implements com.android.server.pm.pkg.SharedUserApi {
    int mAppId;
    final com.android.server.utils.WatchedArraySet<com.android.server.pm.PackageSetting> mDisabledPackages;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArraySet<com.android.server.pm.PackageSetting>> mDisabledPackagesSnapshot;
    private final com.android.server.utils.Watcher mObserver;
    private final com.android.server.utils.WatchedArraySet<com.android.server.pm.PackageSetting> mPackages;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArraySet<com.android.server.pm.PackageSetting>> mPackagesSnapshot;
    private final com.android.server.utils.SnapshotCache<com.android.server.pm.SharedUserSetting> mSnapshot;
    final java.lang.String name;
    final android.util.ArrayMap<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess> processes;
    int seInfoTargetSdkVersion;
    final com.android.server.pm.PackageSignatures signatures;
    java.lang.Boolean signaturesChanged;
    int uidFlags;
    int uidPrivateFlags;

    private com.android.server.utils.SnapshotCache<com.android.server.pm.SharedUserSetting> makeCache() {
        return new com.android.server.utils.SnapshotCache<com.android.server.pm.SharedUserSetting>(this, this) { // from class: com.android.server.pm.SharedUserSetting.2
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.SharedUserSetting createSnapshot() {
                return new com.android.server.pm.SharedUserSetting();
            }
        };
    }

    SharedUserSetting(java.lang.String str, int i, int i2) {
        super(i, i2);
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.SharedUserSetting.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.pm.SharedUserSetting.this.onChanged();
            }
        };
        this.signatures = new com.android.server.pm.PackageSignatures();
        this.uidFlags = i;
        this.uidPrivateFlags = i2;
        this.name = str;
        this.seInfoTargetSdkVersion = 10000;
        this.mPackages = new com.android.server.utils.WatchedArraySet<>();
        this.mPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mPackages, this.mPackages, "SharedUserSetting.packages");
        this.mDisabledPackages = new com.android.server.utils.WatchedArraySet<>();
        this.mDisabledPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mDisabledPackages, this.mDisabledPackages, "SharedUserSetting.mDisabledPackages");
        this.processes = new android.util.ArrayMap<>();
        registerObservers();
        this.mSnapshot = makeCache();
    }

    private SharedUserSetting(com.android.server.pm.SharedUserSetting sharedUserSetting) {
        super(sharedUserSetting);
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.SharedUserSetting.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.pm.SharedUserSetting.this.onChanged();
            }
        };
        this.signatures = new com.android.server.pm.PackageSignatures();
        this.name = sharedUserSetting.name;
        this.mAppId = sharedUserSetting.mAppId;
        this.uidFlags = sharedUserSetting.uidFlags;
        this.uidPrivateFlags = sharedUserSetting.uidPrivateFlags;
        this.mPackages = sharedUserSetting.mPackagesSnapshot.snapshot();
        this.mPackagesSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mDisabledPackages = sharedUserSetting.mDisabledPackagesSnapshot.snapshot();
        this.mDisabledPackagesSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.signatures.mSigningDetails = sharedUserSetting.signatures.mSigningDetails;
        this.signaturesChanged = sharedUserSetting.signaturesChanged;
        this.processes = new android.util.ArrayMap<>(sharedUserSetting.processes);
        this.mSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
    }

    private void registerObservers() {
        this.mPackages.registerObserver(this.mObserver);
        this.mDisabledPackages.registerObserver(this.mObserver);
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.pm.SharedUserSetting snapshot() {
        return this.mSnapshot.snapshot();
    }

    public java.lang.String toString() {
        return "SharedUserSetting{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.name + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mAppId + "}";
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mAppId);
        protoOutputStream.write(1138166333442L, this.name);
        protoOutputStream.end(start);
    }

    void addProcesses(java.util.Map<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess> map) {
        if (map != null) {
            java.util.Iterator<java.lang.String> it = map.keySet().iterator();
            while (it.hasNext()) {
                com.android.internal.pm.pkg.component.ParsedProcess parsedProcess = map.get(it.next());
                com.android.internal.pm.pkg.component.ParsedProcess parsedProcess2 = this.processes.get(parsedProcess.getName());
                if (parsedProcess2 == null) {
                    this.processes.put(parsedProcess.getName(), new com.android.internal.pm.pkg.component.ParsedProcessImpl(parsedProcess));
                } else {
                    com.android.internal.pm.pkg.component.ComponentMutateUtils.addStateFrom(parsedProcess2, parsedProcess);
                }
            }
            onChanged();
        }
    }

    boolean removePackage(com.android.server.pm.PackageSetting packageSetting) {
        if (!this.mPackages.remove(packageSetting)) {
            return false;
        }
        if ((getFlags() & packageSetting.getFlags()) != 0) {
            int i = this.uidFlags;
            for (int i2 = 0; i2 < this.mPackages.size(); i2++) {
                i |= this.mPackages.valueAt(i2).getFlags();
            }
            setFlags(i);
        }
        if ((packageSetting.getPrivateFlags() & getPrivateFlags()) != 0) {
            int i3 = this.uidPrivateFlags;
            for (int i4 = 0; i4 < this.mPackages.size(); i4++) {
                i3 |= this.mPackages.valueAt(i4).getPrivateFlags();
            }
            setPrivateFlags(i3);
        }
        updateProcesses();
        onChanged();
        return true;
    }

    void addPackage(com.android.server.pm.PackageSetting packageSetting) {
        if (this.mPackages.size() == 0 && packageSetting.getPkg() != null) {
            this.seInfoTargetSdkVersion = packageSetting.getPkg().getTargetSdkVersion();
        }
        if (this.mPackages.add(packageSetting)) {
            setFlags(getFlags() | packageSetting.getFlags());
            setPrivateFlags(getPrivateFlags() | packageSetting.getPrivateFlags());
            onChanged();
        }
        if (packageSetting.getPkg() != null) {
            addProcesses(packageSetting.getPkg().getProcesses());
        }
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.pkg.AndroidPackage> getPackages() {
        if (this.mPackages == null || this.mPackages.size() == 0) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mPackages.size());
        for (int i = 0; i < this.mPackages.size(); i++) {
            com.android.server.pm.PackageSetting valueAt = this.mPackages.valueAt(i);
            if (valueAt != null && valueAt.getPkg() != null) {
                arrayList.add(valueAt.getPkg());
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public boolean isPrivileged() {
        return (getPrivateFlags() & 8) != 0;
    }

    public boolean isSingleUser() {
        if (this.mPackages.size() != 1 || this.mDisabledPackages.size() > 1) {
            return false;
        }
        if (this.mDisabledPackages.size() != 1) {
            return true;
        }
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = this.mDisabledPackages.valueAt(0).getPkg();
        return pkg != null && pkg.isLeavingSharedUser();
    }

    public void fixSeInfoLocked() {
        if (this.mPackages == null || this.mPackages.size() == 0) {
            return;
        }
        for (int i = 0; i < this.mPackages.size(); i++) {
            com.android.server.pm.PackageSetting valueAt = this.mPackages.valueAt(i);
            if (valueAt != null && valueAt.getPkg() != null && valueAt.getPkg().getTargetSdkVersion() < this.seInfoTargetSdkVersion) {
                this.seInfoTargetSdkVersion = valueAt.getPkg().getTargetSdkVersion();
                onChanged();
            }
        }
        for (int i2 = 0; i2 < this.mPackages.size(); i2++) {
            com.android.server.pm.PackageSetting valueAt2 = this.mPackages.valueAt(i2);
            if (valueAt2 != null && valueAt2.getPkg() != null) {
                valueAt2.getPkgState().setOverrideSeInfo(com.android.server.pm.SELinuxMMAC.getSeInfo((com.android.server.pm.pkg.PackageState) valueAt2, (com.android.server.pm.pkg.AndroidPackage) valueAt2.getPkg(), isPrivileged() | valueAt2.isPrivileged(), this.seInfoTargetSdkVersion));
                onChanged();
            }
        }
    }

    public void updateProcesses() {
        this.processes.clear();
        for (int size = this.mPackages.size() - 1; size >= 0; size--) {
            com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = this.mPackages.valueAt(size).getPkg();
            if (pkg != null) {
                addProcesses(pkg.getProcesses());
            }
        }
    }

    public int[] getNotInstalledUserIds() {
        int[] iArr = null;
        for (int i = 0; i < this.mPackages.size(); i++) {
            int[] notInstalledUserIds = this.mPackages.valueAt(i).getNotInstalledUserIds();
            if (iArr == null) {
                iArr = notInstalledUserIds;
            } else {
                int[] iArr2 = iArr;
                for (int i2 : iArr) {
                    if (!com.android.internal.util.ArrayUtils.contains(notInstalledUserIds, i2)) {
                        iArr2 = com.android.internal.util.ArrayUtils.removeInt(iArr2, i2);
                    }
                }
                iArr = iArr2;
            }
        }
        return iArr == null ? libcore.util.EmptyArray.INT : iArr;
    }

    public com.android.server.pm.SharedUserSetting updateFrom(com.android.server.pm.SharedUserSetting sharedUserSetting) {
        super.copySettingBase(sharedUserSetting);
        this.mAppId = sharedUserSetting.mAppId;
        this.uidFlags = sharedUserSetting.uidFlags;
        this.uidPrivateFlags = sharedUserSetting.uidPrivateFlags;
        this.seInfoTargetSdkVersion = sharedUserSetting.seInfoTargetSdkVersion;
        this.mPackages.clear();
        this.mPackages.addAll(sharedUserSetting.mPackages);
        this.signaturesChanged = sharedUserSetting.signaturesChanged;
        if (sharedUserSetting.processes != null) {
            int size = sharedUserSetting.processes.size();
            this.processes.clear();
            this.processes.ensureCapacity(size);
            for (int i = 0; i < size; i++) {
                com.android.internal.pm.pkg.component.ParsedProcess parsedProcessImpl = new com.android.internal.pm.pkg.component.ParsedProcessImpl(sharedUserSetting.processes.valueAt(i));
                this.processes.put(parsedProcessImpl.getName(), parsedProcessImpl);
            }
        } else {
            this.processes.clear();
        }
        onChanged();
        return this;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    @android.annotation.NonNull
    public java.lang.String getName() {
        return this.name;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public int getAppId() {
        return this.mAppId;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public int getUidFlags() {
        return this.uidFlags;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public int getPrivateUidFlags() {
        return this.uidPrivateFlags;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public int getSeInfoTargetSdkVersion() {
        return this.seInfoTargetSdkVersion;
    }

    public com.android.server.utils.WatchedArraySet<com.android.server.pm.PackageSetting> getPackageSettings() {
        return this.mPackages;
    }

    public com.android.server.utils.WatchedArraySet<com.android.server.pm.PackageSetting> getDisabledPackageSettings() {
        return this.mDisabledPackages;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    @android.annotation.NonNull
    public android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> getPackageStates() {
        return this.mPackages.untrackedStorage();
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    @android.annotation.NonNull
    public android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> getDisabledPackageStates() {
        return this.mDisabledPackages.untrackedStorage();
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    @android.annotation.NonNull
    public android.content.pm.SigningDetails getSigningDetails() {
        return this.signatures.mSigningDetails;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess> getProcesses() {
        return this.processes;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    @android.annotation.NonNull
    public com.android.server.pm.permission.LegacyPermissionState getSharedUserLegacyPermissionState() {
        return super.getLegacyPermissionState();
    }
}
