package com.android.server.pm;

/* loaded from: classes2.dex */
public final class StorageEventHelper extends android.os.storage.StorageEventListener {
    private final com.android.server.pm.BroadcastHelper mBroadcastHelper;
    private final com.android.server.pm.DeletePackageHelper mDeletePackageHelper;

    @com.android.internal.annotations.GuardedBy({"mLoadedVolumes"})
    final android.util.ArraySet<java.lang.String> mLoadedVolumes = new android.util.ArraySet<>();
    private final com.android.server.pm.PackageManagerService mPm;
    private final com.android.server.pm.RemovePackageHelper mRemovePackageHelper;

    public StorageEventHelper(com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.DeletePackageHelper deletePackageHelper, com.android.server.pm.RemovePackageHelper removePackageHelper) {
        this.mPm = packageManagerService;
        this.mBroadcastHelper = new com.android.server.pm.BroadcastHelper(this.mPm.mInjector);
        this.mDeletePackageHelper = deletePackageHelper;
        this.mRemovePackageHelper = removePackageHelper;
    }

    public void onVolumeStateChanged(android.os.storage.VolumeInfo volumeInfo, int i, int i2) {
        if (volumeInfo.type == 1) {
            if (volumeInfo.state != 2) {
                if (volumeInfo.state == 5) {
                    unloadPrivatePackages(volumeInfo);
                }
            } else {
                java.lang.String fsUuid = volumeInfo.getFsUuid();
                this.mPm.mUserManager.reconcileUsers(fsUuid);
                reconcileApps(this.mPm.snapshotComputer(), fsUuid);
                this.mPm.mInstallerService.onPrivateVolumeMounted(fsUuid);
                loadPrivatePackages(volumeInfo);
            }
        }
    }

    public void onVolumeForgotten(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            android.util.Slog.e("PackageManager", "Forgetting internal storage is probably a mistake; ignoring");
            return;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal : this.mPm.mSettings.getVolumePackagesLPr(str)) {
                    android.util.Slog.d("PackageManager", "Destroying " + packageStateInternal.getPackageName() + " because volume was forgotten");
                    this.mPm.deletePackageVersioned(new android.content.pm.VersionedPackage(packageStateInternal.getPackageName(), -1), new android.content.pm.PackageManager.LegacyPackageDeleteObserver((android.content.pm.IPackageDeleteObserver) null).getBinder(), 0, 2);
                    com.android.internal.policy.AttributeCache.instance().removePackage(packageStateInternal.getPackageName());
                }
                this.mPm.mSettings.onVolumeForgotten(str);
                this.mPm.writeSettingsLPrTEMP();
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    private void loadPrivatePackages(final android.os.storage.VolumeInfo volumeInfo) {
        this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.StorageEventHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.StorageEventHelper.this.lambda$loadPrivatePackages$0(volumeInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(10:34|(2:59|(2:61|62)(2:63|50))(2:38|39)|40|41|121|47|48|49|50|32) */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x012c, code lost:
    
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x012e, code lost:
    
        android.util.Slog.w("PackageManager", "Failed to prepare storage: " + r10);
     */
    /* renamed from: loadPrivatePackagesInner, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void lambda$loadPrivatePackages$0(android.os.storage.VolumeInfo volumeInfo) {
        com.android.server.pm.Settings.VersionInfo findOrCreateVersion;
        java.util.List<? extends com.android.server.pm.pkg.PackageStateInternal> volumePackagesLPr;
        int i;
        java.lang.String str = volumeInfo.fsUuid;
        if (android.text.TextUtils.isEmpty(str)) {
            android.util.Slog.e("PackageManager", "Loading internal storage is probably a mistake; ignoring");
            return;
        }
        com.android.server.pm.AppDataHelper appDataHelper = new com.android.server.pm.AppDataHelper(this.mPm);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> arrayList2 = new java.util.ArrayList<>();
        int defParseFlags = this.mPm.getDefParseFlags() | 8;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                findOrCreateVersion = this.mPm.mSettings.findOrCreateVersion(str);
                volumePackagesLPr = this.mPm.mSettings.getVolumePackagesLPr(str);
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal : volumePackagesLPr) {
            arrayList.add(this.mPm.freezePackage(packageStateInternal.getPackageName(), -1, "loadPrivatePackagesInner", 13, null));
            synchronized (this.mPm.mInstallLock) {
                try {
                    arrayList2.add(this.mPm.initPackageTracedLI(packageStateInternal.getPath(), defParseFlags, 512));
                } catch (com.android.server.pm.PackageManagerException e) {
                    android.util.Slog.w("PackageManager", "Failed to scan " + packageStateInternal.getPath() + ": " + e.getMessage());
                }
                if (!android.content.pm.PackagePartitions.FINGERPRINT.equals(findOrCreateVersion.fingerprint)) {
                    appDataHelper.clearAppDataLIF(packageStateInternal.getPkg(), -1, 131111);
                }
            }
        }
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) this.mPm.mInjector.getSystemService(android.os.storage.StorageManager.class);
        com.android.server.pm.UserManagerInternal userManagerInternal = this.mPm.mInjector.getUserManagerInternal();
        android.os.storage.StorageManagerInternal storageManagerInternal = (android.os.storage.StorageManagerInternal) this.mPm.mInjector.getLocalService(android.os.storage.StorageManagerInternal.class);
        for (android.content.pm.UserInfo userInfo : this.mPm.mUserManager.getUsers(false)) {
            if (android.os.storage.StorageManager.isCeStorageUnlocked(userInfo.id) && storageManagerInternal.isCeStoragePrepared(userInfo.id)) {
                i = 3;
            } else if (userManagerInternal.isUserRunning(userInfo.id)) {
                i = 1;
            } else {
                continue;
            }
            storageManager.prepareUserStorage(str, userInfo.id, i);
            synchronized (this.mPm.mInstallLock) {
                appDataHelper.reconcileAppsDataLI(str, userInfo.id, i, true);
            }
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock2) {
            try {
                boolean z = !android.content.pm.PackagePartitions.FINGERPRINT.equals(findOrCreateVersion.fingerprint);
                if (z) {
                    com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(4, "Partitions fingerprint changed from " + findOrCreateVersion.fingerprint + " to " + android.content.pm.PackagePartitions.FINGERPRINT + "; regranting permissions for " + str);
                }
                this.mPm.mPermissionManager.onStorageVolumeMounted(str, z);
                findOrCreateVersion.forceCurrent();
                this.mPm.writeSettingsLPrTEMP();
            } finally {
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((com.android.server.pm.PackageFreezer) it.next()).close();
        }
        this.mBroadcastHelper.sendResourcesChangedBroadcastAndNotify(this.mPm.snapshotComputer(), true, false, arrayList2);
        synchronized (this.mLoadedVolumes) {
            this.mLoadedVolumes.add(volumeInfo.getId());
        }
    }

    private void unloadPrivatePackages(final android.os.storage.VolumeInfo volumeInfo) {
        this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.StorageEventHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.StorageEventHelper.this.lambda$unloadPrivatePackages$1(volumeInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: unloadPrivatePackagesInner, reason: merged with bridge method [inline-methods] */
    public void lambda$unloadPrivatePackages$1(android.os.storage.VolumeInfo volumeInfo) {
        java.lang.String str = volumeInfo.fsUuid;
        if (android.text.TextUtils.isEmpty(str)) {
            android.util.Slog.e("PackageManager", "Unloading internal storage is probably a mistake; ignoring");
            return;
        }
        int[] userIds = this.mPm.mUserManager.getUserIds();
        java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> arrayList = new java.util.ArrayList<>();
        synchronized (this.mPm.mInstallLock) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal : this.mPm.mSettings.getVolumePackagesLPr(str)) {
                        if (packageStateInternal.getPkg() != null) {
                            com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
                            com.android.server.pm.PackageFreezer freezePackageForDelete = this.mPm.freezePackageForDelete(packageStateInternal.getPackageName(), -1, 1, "unloadPrivatePackagesInner", 13);
                            try {
                                if (this.mDeletePackageHelper.deletePackageLIF(packageStateInternal.getPackageName(), null, false, userIds, 1, new com.android.server.pm.PackageRemovedInfo(), false)) {
                                    arrayList.add(pkg);
                                } else {
                                    android.util.Slog.w("PackageManager", "Failed to unload " + packageStateInternal.getPath());
                                }
                                if (freezePackageForDelete != null) {
                                    freezePackageForDelete.close();
                                }
                                com.android.internal.policy.AttributeCache.instance().removePackage(packageStateInternal.getPackageName());
                            } finally {
                            }
                        }
                    }
                    this.mPm.writeSettingsLPrTEMP();
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        this.mBroadcastHelper.sendResourcesChangedBroadcastAndNotify(this.mPm.snapshotComputer(), false, false, arrayList);
        synchronized (this.mLoadedVolumes) {
            this.mLoadedVolumes.remove(volumeInfo.getId());
        }
        android.app.ResourcesManager.getInstance().invalidatePath(volumeInfo.getPath().getAbsolutePath());
        for (int i = 0; i < 3; i++) {
            java.lang.System.gc();
            java.lang.System.runFinalization();
        }
    }

    public void reconcileApps(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str) {
        java.util.List<java.lang.String> collectAbsoluteCodePaths = collectAbsoluteCodePaths(computer);
        java.util.ArrayList arrayList = null;
        for (java.io.File file : android.os.FileUtils.listFilesOrEmpty(android.os.Environment.getDataAppDirectory(str))) {
            boolean z = true;
            if ((android.content.pm.parsing.ApkLiteParseUtils.isApkFile(file) || file.isDirectory()) && !com.android.server.pm.PackageInstallerService.isStageName(file.getName())) {
                java.lang.String absolutePath = file.getAbsolutePath();
                int size = collectAbsoluteCodePaths.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        z = false;
                        break;
                    } else if (collectAbsoluteCodePaths.get(i).startsWith(absolutePath)) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (!z) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(file);
                }
            }
        }
        if (arrayList != null) {
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                java.io.File file2 = (java.io.File) arrayList.get(i2);
                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "Destroying orphaned at " + file2);
                this.mRemovePackageHelper.removeCodePath(file2);
            }
        }
    }

    private java.util.List<java.lang.String> collectAbsoluteCodePaths(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = computer.getPackageStates();
        int size = packageStates.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(packageStates.valueAt(i).getPath().getAbsolutePath());
        }
        return arrayList;
    }

    public void dumpLoadedVolumes(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState) {
        if (dumpState.onTitlePrinted()) {
            printWriter.println();
        }
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ", 120);
        indentingPrintWriter.println();
        indentingPrintWriter.println("Loaded volumes:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLoadedVolumes) {
            try {
                if (this.mLoadedVolumes.size() == 0) {
                    indentingPrintWriter.println("(none)");
                } else {
                    for (int i = 0; i < this.mLoadedVolumes.size(); i++) {
                        indentingPrintWriter.println(this.mLoadedVolumes.valueAt(i));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }
}
