package com.android.server.pm;

/* loaded from: classes2.dex */
public class AppDataHelper {
    private static final boolean DEBUG_APP_DATA = false;
    private final com.android.server.pm.dex.ArtManagerService mArtManagerService;
    private final com.android.server.pm.PackageManagerServiceInjector mInjector;
    private final com.android.server.pm.Installer mInstaller;
    private final com.android.server.pm.PackageManagerService mPm;

    AppDataHelper(com.android.server.pm.PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
        this.mInjector = this.mPm.mInjector;
        this.mInstaller = this.mInjector.getInstaller();
        this.mArtManagerService = this.mInjector.getArtManagerService();
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    public void prepareAppDataAfterInstallLIF(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        com.android.server.pm.PackageSetting packageLPr;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        prepareAppDataPostCommitLIF(packageLPr, 0, getInstalledUsersForPackage(packageLPr));
    }

    private int[] getInstalledUsersForPackage(com.android.server.pm.PackageSetting packageSetting) {
        java.util.List<android.content.pm.UserInfo> users = this.mInjector.getUserManagerInternal().getUsers(false);
        int[] iArr = new int[users.size()];
        int size = users.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = users.get(i2).id;
            if (packageSetting.getInstalled(i3)) {
                iArr[i] = i3;
                i++;
            }
        }
        return java.util.Arrays.copyOf(iArr, i);
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    public void prepareAppDataPostCommitLIF(final com.android.server.pm.PackageSetting packageSetting, int i, int[] iArr) {
        int i2;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPm.mSettings.writeKernelMappingLPr(packageSetting);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (packageSetting.getPkg() != null && !shouldHaveAppStorage(packageSetting.getPkg())) {
            android.util.Slog.w("PackageManager", "Skipping preparing app data for " + packageSetting.getPackageName());
            return;
        }
        com.android.server.pm.Installer.Batch batch = new com.android.server.pm.Installer.Batch();
        final com.android.server.pm.UserManagerInternal userManagerInternal = this.mInjector.getUserManagerInternal();
        final android.os.storage.StorageManagerInternal storageManagerInternal = (android.os.storage.StorageManagerInternal) this.mInjector.getLocalService(android.os.storage.StorageManagerInternal.class);
        for (final int i3 : iArr) {
            if (android.os.storage.StorageManager.isCeStorageUnlocked(i3) && storageManagerInternal.isCeStoragePrepared(i3)) {
                i2 = 3;
            } else if (userManagerInternal.isUserRunning(i3)) {
                i2 = 1;
            }
            prepareAppData(batch, packageSetting, i, i3, i2).thenRun(new java.lang.Runnable() { // from class: com.android.server.pm.AppDataHelper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.AppDataHelper.lambda$prepareAppDataPostCommitLIF$0(com.android.server.pm.UserManagerInternal.this, i3, packageSetting, storageManagerInternal);
                }
            });
        }
        executeBatchLI(batch);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$prepareAppDataPostCommitLIF$0(com.android.server.pm.UserManagerInternal userManagerInternal, int i, com.android.server.pm.PackageSetting packageSetting, android.os.storage.StorageManagerInternal storageManagerInternal) {
        if (userManagerInternal.isUserUnlockingOrUnlocked(i)) {
            storageManagerInternal.prepareAppDataAfterInstall(packageSetting.getPackageName(), android.os.UserHandle.getUid(i, packageSetting.getAppId()));
        }
    }

    private void executeBatchLI(@android.annotation.NonNull com.android.server.pm.Installer.Batch batch) {
        try {
            batch.execute(this.mInstaller);
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.w("PackageManager", "Failed to execute pending operations", e);
        }
    }

    private void prepareAppDataAndMigrate(@android.annotation.NonNull com.android.server.pm.Installer.Batch batch, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, final int i, final int i2, final boolean z) {
        final com.android.server.pm.PackageSetting packageLPr;
        if (androidPackage == null) {
            android.util.Slog.wtf("PackageManager", "Package was null!", new java.lang.Throwable());
            return;
        }
        if (!shouldHaveAppStorage(androidPackage)) {
            android.util.Slog.w("PackageManager", "Skipping preparing app data for " + androidPackage.getPackageName());
            return;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        prepareAppData(batch, packageLPr, -1, i, i2).thenRun(new java.lang.Runnable() { // from class: com.android.server.pm.AppDataHelper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.AppDataHelper.this.lambda$prepareAppDataAndMigrate$1(z, packageLPr, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepareAppDataAndMigrate$1(boolean z, com.android.server.pm.PackageSetting packageSetting, int i, int i2) {
        if (z && maybeMigrateAppDataLIF(packageSetting, i)) {
            com.android.server.pm.Installer.Batch batch = new com.android.server.pm.Installer.Batch();
            prepareAppData(batch, packageSetting, -1, i, i2);
            executeBatchLI(batch);
        }
    }

    @android.annotation.NonNull
    private java.util.concurrent.CompletableFuture<?> prepareAppData(@android.annotation.NonNull com.android.server.pm.Installer.Batch batch, @android.annotation.NonNull final com.android.server.pm.PackageSetting packageSetting, int i, final int i2, final int i3) {
        java.lang.String seinfoUser;
        final java.lang.String packageName = packageSetting.getPackageName();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                seinfoUser = com.android.server.pm.pkg.SELinuxUtil.getSeinfoUser(packageSetting.readUserState(i2));
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        final com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageSetting.getPkg();
        final java.lang.String volumeUuid = packageSetting.getVolumeUuid();
        int appId = packageSetting.getAppId();
        java.lang.String seInfo = packageSetting.getSeInfo();
        com.android.internal.util.Preconditions.checkNotNull(seInfo);
        final android.os.CreateAppDataArgs buildCreateAppDataArgs = com.android.server.pm.Installer.buildCreateAppDataArgs(volumeUuid, packageName, i2, i3, appId, seInfo + seinfoUser, packageSetting.getTargetSdkVersion(), packageSetting.getUsesSdkLibraries().length > 0);
        buildCreateAppDataArgs.previousAppId = i;
        return batch.createAppData(buildCreateAppDataArgs).whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.pm.AppDataHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.pm.AppDataHelper.this.lambda$prepareAppData$2(packageName, volumeUuid, i2, i3, buildCreateAppDataArgs, pkg, packageSetting, (android.os.CreateAppDataResult) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepareAppData$2(java.lang.String str, java.lang.String str2, int i, int i2, android.os.CreateAppDataArgs createAppDataArgs, com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.PackageSetting packageSetting, android.os.CreateAppDataResult createAppDataResult, java.lang.Throwable th) {
        if (th != null) {
            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "Failed to create app data for " + str + ", but trying to recover: " + th);
            destroyAppDataLeafLIF(str, str2, i, i2);
            try {
                createAppDataResult = this.mInstaller.createAppData(createAppDataArgs);
                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(3, "Recovery succeeded!");
            } catch (com.android.server.pm.Installer.InstallerException e) {
                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(3, "Recovery failed!");
            }
        }
        if (!com.android.server.pm.DexOptHelper.useArtService() && androidPackage != null && (this.mPm.isDeviceUpgrading() || this.mPm.isFirstBoot() || i != 0)) {
            try {
                this.mArtManagerService.prepareAppProfiles(androidPackage, i, false);
            } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e2) {
                throw new java.lang.RuntimeException(e2);
            }
        }
        long j = createAppDataResult.ceDataInode;
        long j2 = createAppDataResult.deDataInode;
        if ((i2 & 2) != 0 && j != -1) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    packageSetting.setCeDataInode(j, i);
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        if ((i2 & 1) != 0 && j2 != -1) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock2) {
                try {
                    packageSetting.setDeDataInode(j2, i);
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        if (androidPackage != null) {
            prepareAppDataContentsLeafLIF(androidPackage, packageSetting, i, i2);
        }
    }

    public void prepareAppDataContentsLIF(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2) {
        if (androidPackage == null) {
            android.util.Slog.wtf("PackageManager", "Package was null!", new java.lang.Throwable());
        } else {
            prepareAppDataContentsLeafLIF(androidPackage, packageStateInternal, i, i2);
        }
    }

    private void prepareAppDataContentsLeafLIF(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2) {
        java.lang.String volumeUuid = androidPackage.getVolumeUuid();
        java.lang.String packageName = androidPackage.getPackageName();
        if ((i2 & 2) != 0) {
            java.lang.String rawPrimaryCpuAbi = packageStateInternal == null ? com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawPrimaryCpuAbi(androidPackage) : packageStateInternal.getPrimaryCpuAbi();
            if (rawPrimaryCpuAbi != null && !dalvik.system.VMRuntime.is64BitAbi(rawPrimaryCpuAbi)) {
                java.lang.String nativeLibraryDir = androidPackage.getNativeLibraryDir();
                if (!new java.io.File(nativeLibraryDir).exists()) {
                    return;
                }
                try {
                    this.mInstaller.linkNativeLibraryDirectory(volumeUuid, packageName, nativeLibraryDir, i);
                } catch (com.android.server.pm.Installer.InstallerException e) {
                    android.util.Slog.e("PackageManager", "Failed to link native for " + packageName + ": " + e);
                }
            }
        }
    }

    private boolean maybeMigrateAppDataLIF(@android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting, int i) {
        if (packageSetting.isSystem() && !android.os.storage.StorageManager.isFileEncrypted()) {
            try {
                this.mInstaller.migrateAppData(packageSetting.getVolumeUuid(), packageSetting.getPackageName(), i, packageSetting.isDefaultToDeviceProtectedStorage() ? 1 : 2);
            } catch (com.android.server.pm.Installer.InstallerException e) {
                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "Failed to migrate " + packageSetting.getPackageName() + ": " + e.getMessage());
            }
            return true;
        }
        return false;
    }

    @android.annotation.NonNull
    public void reconcileAppsData(int i, int i2, boolean z) {
        java.util.Iterator it = ((android.os.storage.StorageManager) this.mInjector.getSystemService(android.os.storage.StorageManager.class)).getWritablePrivateVolumes().iterator();
        while (it.hasNext()) {
            java.lang.String fsUuid = ((android.os.storage.VolumeInfo) it.next()).getFsUuid();
            synchronized (this.mPm.mInstallLock) {
                reconcileAppsDataLI(fsUuid, i, i2, z);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    void reconcileAppsDataLI(java.lang.String str, int i, int i2, boolean z) {
        reconcileAppsDataLI(str, i, i2, z, false);
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private java.util.List<java.lang.String> reconcileAppsDataLI(java.lang.String str, int i, int i2, boolean z, boolean z2) {
        java.util.ArrayList arrayList;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        com.android.server.pm.Computer computer;
        java.lang.String str5;
        java.lang.String str6;
        java.util.ArrayList arrayList2;
        java.util.ArrayList arrayList3;
        java.lang.String str7;
        java.lang.String str8;
        int i3;
        int i4;
        java.util.ArrayList arrayList4;
        java.lang.String str9;
        java.io.File[] fileArr;
        java.lang.String str10;
        java.lang.String str11;
        com.android.server.pm.Computer computer2;
        java.lang.String str12;
        int i5;
        int i6;
        java.lang.String str13 = "PackageManager";
        android.util.Slog.v("PackageManager", "reconcileAppsData for " + str + " u" + i + " 0x" + java.lang.Integer.toHexString(i2) + " migrateAppData=" + z);
        java.util.ArrayList arrayList5 = z2 ? new java.util.ArrayList() : null;
        try {
            this.mInstaller.cleanupInvalidPackageDirs(str, i, i2);
        } catch (com.android.server.pm.Installer.InstallerException e) {
            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "Failed to cleanup deleted dirs: " + e);
        }
        java.io.File dataUserCeDirectory = android.os.Environment.getDataUserCeDirectory(str, i);
        java.io.File dataUserDeDirectory = android.os.Environment.getDataUserDeDirectory(str, i);
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        java.lang.String str14 = "Failed to destroy: ";
        java.lang.String str15 = " due to: ";
        java.lang.String str16 = "Destroying ";
        if ((i2 & 2) == 0) {
            arrayList = arrayList5;
            str2 = "PackageManager";
            str3 = "Destroying ";
            str4 = " due to: ";
            computer = snapshotComputer;
            str5 = "Failed to destroy: ";
        } else {
            if (android.os.storage.StorageManager.isFileEncrypted() && !android.os.storage.StorageManager.isCeStorageUnlocked(i)) {
                throw new java.lang.RuntimeException("Yikes, someone asked us to reconcile CE storage while " + i + " was still locked; this would have caused massive data loss!");
            }
            java.io.File[] listFilesOrEmpty = android.os.FileUtils.listFilesOrEmpty(dataUserCeDirectory);
            int length = listFilesOrEmpty.length;
            int i7 = 0;
            while (i7 < length) {
                java.io.File file = listFilesOrEmpty[i7];
                java.lang.String name = file.getName();
                try {
                    assertPackageStorageValid(snapshotComputer, str, name, i);
                    arrayList4 = arrayList5;
                    str9 = str13;
                    i5 = i7;
                    i6 = length;
                    fileArr = listFilesOrEmpty;
                    str10 = str16;
                    str11 = str15;
                    computer2 = snapshotComputer;
                    str12 = str14;
                } catch (com.android.server.pm.PackageManagerException e2) {
                    int i8 = i7;
                    com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, str16 + file + str15 + e2);
                    try {
                        i5 = i8;
                        i6 = length;
                        fileArr = listFilesOrEmpty;
                        str10 = str16;
                        arrayList4 = arrayList5;
                        str11 = str15;
                        str9 = str13;
                        computer2 = snapshotComputer;
                        str12 = str14;
                    } catch (com.android.server.pm.Installer.InstallerException e3) {
                        e = e3;
                        arrayList4 = arrayList5;
                        str9 = str13;
                        fileArr = listFilesOrEmpty;
                        str10 = str16;
                        str11 = str15;
                        computer2 = snapshotComputer;
                        str12 = str14;
                        i5 = i8;
                        i6 = length;
                    }
                    try {
                        this.mInstaller.destroyAppData(str, name, i, 2, 0L);
                    } catch (com.android.server.pm.Installer.InstallerException e4) {
                        e = e4;
                        com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, str12 + e);
                        i7 = i5 + 1;
                        str14 = str12;
                        snapshotComputer = computer2;
                        str16 = str10;
                        length = i6;
                        listFilesOrEmpty = fileArr;
                        arrayList5 = arrayList4;
                        str13 = str9;
                        str15 = str11;
                    }
                }
                i7 = i5 + 1;
                str14 = str12;
                snapshotComputer = computer2;
                str16 = str10;
                length = i6;
                listFilesOrEmpty = fileArr;
                arrayList5 = arrayList4;
                str13 = str9;
                str15 = str11;
            }
            arrayList = arrayList5;
            str2 = str13;
            str3 = str16;
            str4 = str15;
            computer = snapshotComputer;
            str5 = str14;
        }
        if ((i2 & 1) != 0) {
            java.io.File[] listFilesOrEmpty2 = android.os.FileUtils.listFilesOrEmpty(dataUserDeDirectory);
            int length2 = listFilesOrEmpty2.length;
            int i9 = 0;
            while (i9 < length2) {
                java.io.File file2 = listFilesOrEmpty2[i9];
                java.lang.String name2 = file2.getName();
                try {
                    assertPackageStorageValid(computer, str, name2, i);
                    i3 = length2;
                    i4 = i9;
                    str8 = str4;
                } catch (com.android.server.pm.PackageManagerException e5) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(str3);
                    sb.append(file2);
                    java.lang.String str17 = str4;
                    sb.append(str17);
                    sb.append(e5);
                    com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, sb.toString());
                    try {
                        str8 = str17;
                        i3 = length2;
                        i4 = i9;
                    } catch (com.android.server.pm.Installer.InstallerException e6) {
                        e = e6;
                        str8 = str17;
                        i3 = length2;
                        i4 = i9;
                    }
                    try {
                        this.mInstaller.destroyAppData(str, name2, i, 1, 0L);
                    } catch (com.android.server.pm.Installer.InstallerException e7) {
                        e = e7;
                        com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, str5 + e);
                        i9 = i4 + 1;
                        str4 = str8;
                        length2 = i3;
                    }
                }
                i9 = i4 + 1;
                str4 = str8;
                length2 = i3;
            }
        }
        android.os.Trace.traceBegin(262144L, "prepareAppDataAndMigrate");
        com.android.server.pm.Installer.Batch batch = new com.android.server.pm.Installer.Batch();
        int i10 = 0;
        for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal : computer.getVolumePackages(str)) {
            java.lang.String packageName = packageStateInternal.getPackageName();
            if (packageStateInternal.getPkg() == null) {
                str6 = str2;
                android.util.Slog.w(str6, "Odd, missing scanned package " + packageName);
                arrayList2 = arrayList;
            } else {
                str6 = str2;
                if (!z2 || packageStateInternal.getPkg().isCoreApp()) {
                    java.util.ArrayList arrayList6 = arrayList;
                    if (!packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                        arrayList3 = arrayList6;
                        str7 = str6;
                    } else {
                        arrayList3 = arrayList6;
                        str7 = str6;
                        prepareAppDataAndMigrate(batch, packageStateInternal.getPkg(), i, i2, z);
                        i10++;
                    }
                    arrayList = arrayList3;
                    str2 = str7;
                } else {
                    arrayList2 = arrayList;
                    arrayList2.add(packageName);
                }
            }
            arrayList = arrayList2;
            str2 = str6;
        }
        java.util.ArrayList arrayList7 = arrayList;
        executeBatchLI(batch);
        android.os.Trace.traceEnd(262144L);
        android.util.Slog.v(str2, "reconcileAppsData finished " + i10 + " packages");
        return arrayList7;
    }

    private void assertPackageStorageValid(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, java.lang.String str2, int i) throws com.android.server.pm.PackageManagerException {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str2);
        if (packageStateInternal == null) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Package " + str2 + " is unknown", -7);
        }
        if (!android.text.TextUtils.equals(str, packageStateInternal.getVolumeUuid())) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Package " + str2 + " found on unknown volume " + str + "; expected volume " + packageStateInternal.getVolumeUuid(), -8);
        }
        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        if (!userStateOrDefault.isInstalled() && !userStateOrDefault.dataExists()) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Package " + str2 + " not installed for user " + i + " or was deleted without DELETE_KEEP_DATA", -9);
        }
        if (packageStateInternal.getPkg() != null && !shouldHaveAppStorage(packageStateInternal.getPkg())) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Package " + str2 + " shouldn't have storage", -10);
        }
    }

    public java.util.concurrent.Future<?> fixAppsDataOnBoot() {
        final int i;
        if (android.os.storage.StorageManager.isFileEncrypted()) {
            i = 1;
        } else {
            i = 3;
        }
        final java.util.List<java.lang.String> reconcileAppsDataLI = reconcileAppsDataLI(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, 0, i, true, true);
        return com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.pm.AppDataHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.AppDataHelper.this.lambda$fixAppsDataOnBoot$3(reconcileAppsDataLI, i);
            }
        }, "prepareAppData");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fixAppsDataOnBoot$3(java.util.List list, int i) {
        android.util.TimingsTraceLog timingsTraceLog = new android.util.TimingsTraceLog("SystemServerTimingAsync", 262144L);
        timingsTraceLog.traceBegin("AppDataFixup");
        try {
            this.mInstaller.fixupAppData(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, 3);
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.w("PackageManager", "Trouble fixing GIDs", e);
        }
        timingsTraceLog.traceEnd();
        timingsTraceLog.traceBegin("AppDataPrepare");
        if (list == null || list.isEmpty()) {
            return;
        }
        com.android.server.pm.Installer.Batch batch = new com.android.server.pm.Installer.Batch();
        java.util.Iterator it = list.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal((java.lang.String) it.next());
            if (packageStateInternal != null && packageStateInternal.getUserStateOrDefault(0).isInstalled()) {
                prepareAppDataAndMigrate(batch, packageStateInternal.getPkg(), 0, i, true);
                i2++;
            }
        }
        synchronized (this.mPm.mInstallLock) {
            executeBatchLI(batch);
        }
        timingsTraceLog.traceEnd();
        android.util.Slog.i("PackageManager", "Deferred reconcileAppsData finished " + i2 + " packages");
    }

    void clearAppDataLIF(com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2) {
        if (androidPackage == null) {
            return;
        }
        clearAppDataLeafLIF(androidPackage.getPackageName(), androidPackage.getVolumeUuid(), i, i2);
        if ((131072 & i2) == 0) {
            clearAppProfilesLIF(androidPackage);
        }
    }

    void clearAppDataLeafLIF(java.lang.String str, java.lang.String str2, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(str);
        for (int i3 : this.mPm.resolveUserIds(i)) {
            try {
                this.mInstaller.clearAppData(str2, str, i3, i2, packageStateInternal != null ? packageStateInternal.getUserStateOrDefault(i3).getCeDataInode() : 0L);
            } catch (com.android.server.pm.Installer.InstallerException e) {
                android.util.Slog.w("PackageManager", java.lang.String.valueOf(e));
            }
        }
    }

    void clearAppProfilesLIF(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (androidPackage == null) {
            android.util.Slog.wtf("PackageManager", "Package was null!", new java.lang.Throwable());
        } else {
            if (com.android.server.pm.DexOptHelper.useArtService()) {
                destroyAppProfilesWithArtService(androidPackage.getPackageName());
                return;
            }
            try {
                this.mArtManagerService.clearAppProfiles(androidPackage);
            } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public void destroyAppDataLIF(com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2) {
        if (androidPackage == null) {
            android.util.Slog.wtf("PackageManager", "Package was null!", new java.lang.Throwable());
        } else {
            destroyAppDataLeafLIF(androidPackage.getPackageName(), androidPackage.getVolumeUuid(), i, i2);
        }
    }

    private void destroyAppDataLeafLIF(java.lang.String str, java.lang.String str2, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(str);
        for (int i3 : this.mPm.resolveUserIds(i)) {
            try {
                this.mInstaller.destroyAppData(str2, str, i3, i2, packageStateInternal != null ? packageStateInternal.getUserStateOrDefault(i3).getCeDataInode() : 0L);
            } catch (com.android.server.pm.Installer.InstallerException e) {
                android.util.Slog.w("PackageManager", java.lang.String.valueOf(e));
            }
            this.mPm.getDexManager().notifyPackageDataDestroyed(str, i);
            this.mPm.getDynamicCodeLogger().notifyPackageDataDestroyed(str, i);
        }
    }

    void destroyAppProfilesLIF(java.lang.String str) {
        if (com.android.server.pm.DexOptHelper.useArtService()) {
            destroyAppProfilesWithArtService(str);
            return;
        }
        try {
            this.mInstaller.destroyAppProfiles(str);
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.w("PackageManager", java.lang.String.valueOf(e));
        } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    private void destroyAppProfilesWithArtService(java.lang.String str) {
        if (!com.android.server.pm.DexOptHelper.artManagerLocalIsInitialized()) {
            return;
        }
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = com.android.server.pm.PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
        try {
            try {
                com.android.server.pm.DexOptHelper.getArtManagerLocal().clearAppProfiles(withFilteredSnapshot, str);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.w("PackageManager", e);
            }
            if (withFilteredSnapshot != null) {
                withFilteredSnapshot.close();
            }
        } catch (java.lang.Throwable th) {
            if (withFilteredSnapshot != null) {
                try {
                    withFilteredSnapshot.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private boolean shouldHaveAppStorage(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.content.pm.PackageManager.Property property = (android.content.pm.PackageManager.Property) androidPackage.getProperties().get("android.internal.PROPERTY_NO_APP_DATA_STORAGE");
        return (property == null || !property.getBoolean()) && androidPackage.getUid() >= 0;
    }

    public void clearKeystoreData(int i, int i2) {
        if (i2 < 0) {
            return;
        }
        int length = this.mPm.resolveUserIds(i).length;
        for (int i3 = 0; i3 < length; i3++) {
            android.security.AndroidKeyStoreMaintenance.clearNamespace(0, android.os.UserHandle.getUid(r6[i3], i2));
        }
    }
}
