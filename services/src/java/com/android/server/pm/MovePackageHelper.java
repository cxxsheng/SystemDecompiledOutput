package com.android.server.pm;

/* loaded from: classes2.dex */
public final class MovePackageHelper {
    final com.android.server.pm.PackageManagerService mPm;

    public MovePackageHelper(com.android.server.pm.PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
    }

    public void movePackageInternal(final java.lang.String str, java.lang.String str2, final int i, int i2, android.os.UserHandle userHandle) throws com.android.server.pm.PackageManagerException {
        java.lang.String absolutePath;
        java.io.File dataAppDirectory;
        boolean z;
        long j;
        com.android.server.pm.MoveInfo moveInfo;
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) this.mPm.mInjector.getSystemService(android.os.storage.StorageManager.class);
        android.content.pm.PackageManager packageManager = this.mPm.mContext.getPackageManager();
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(str, i2, userHandle.getIdentifier());
        if (packageStateForInstalledAndFiltered == null || packageStateForInstalledAndFiltered.getPkg() == null) {
            throw new com.android.server.pm.PackageManagerException(-2, "Missing package");
        }
        int[] queryInstalledUsers = com.android.server.pm.pkg.PackageStateUtils.queryInstalledUsers(packageStateForInstalledAndFiltered, this.mPm.mUserManager.getUserIds(), true);
        if (queryInstalledUsers.length > 0) {
            android.os.UserHandle of = android.os.UserHandle.of(queryInstalledUsers[0]);
            for (int i3 : queryInstalledUsers) {
                if (snapshotComputer.shouldFilterApplicationIncludingUninstalled(packageStateForInstalledAndFiltered, i2, i3)) {
                    throw new com.android.server.pm.PackageManagerException(-2, "Missing package");
                }
            }
            com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateForInstalledAndFiltered.getPkg();
            if (packageStateForInstalledAndFiltered.isSystem()) {
                throw new com.android.server.pm.PackageManagerException(-3, "Cannot move system application");
            }
            boolean equals = "private".equals(str2);
            boolean z2 = this.mPm.mContext.getResources().getBoolean(android.R.bool.config_allow3rdPartyAppOnInternal);
            if (equals && !z2) {
                throw new com.android.server.pm.PackageManagerException(-9, "3rd party apps are not allowed on internal storage");
            }
            if (!new java.io.File(pkg.getPath()).isDirectory()) {
                throw new com.android.server.pm.PackageManagerException(-6, "Move only supported for modern cluster style installs");
            }
            java.lang.String volumeUuid = packageStateForInstalledAndFiltered.getVolumeUuid();
            if (java.util.Objects.equals(volumeUuid, str2)) {
                throw new com.android.server.pm.PackageManagerException(-6, "Package already moved to " + str2);
            }
            if (!pkg.isExternalStorage() && this.mPm.isPackageDeviceAdminOnAnyUser(snapshotComputer, str)) {
                throw new com.android.server.pm.PackageManagerException(-8, "Device admin cannot be moved");
            }
            if (snapshotComputer.getFrozenPackages().containsKey(str)) {
                throw new com.android.server.pm.PackageManagerException(-7, "Failed to move already frozen package");
            }
            final boolean isExternalStorage = pkg.isExternalStorage();
            java.io.File file = new java.io.File(pkg.getPath());
            com.android.server.pm.InstallSource installSource = packageStateForInstalledAndFiltered.getInstallSource();
            java.lang.String cpuAbiOverride = packageStateForInstalledAndFiltered.getCpuAbiOverride();
            int appId = android.os.UserHandle.getAppId(pkg.getUid());
            java.lang.String seInfo = packageStateForInstalledAndFiltered.getSeInfo();
            java.lang.String valueOf = java.lang.String.valueOf(packageManager.getApplicationLabel(com.android.server.pm.parsing.pkg.AndroidPackageUtils.generateAppInfoWithoutState(pkg)));
            int targetSdkVersion = pkg.getTargetSdkVersion();
            if (file.getParentFile().getName().startsWith("~~")) {
                absolutePath = file.getParentFile().getAbsolutePath();
            } else {
                absolutePath = file.getAbsolutePath();
            }
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    try {
                        final com.android.server.pm.PackageFreezer freezePackage = this.mPm.freezePackage(str, -1, "movePackageInternal", 10, null);
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        android.os.Bundle bundle = new android.os.Bundle();
                        bundle.putString("android.intent.extra.PACKAGE_NAME", str);
                        bundle.putString("android.intent.extra.TITLE", valueOf);
                        this.mPm.mMoveCallbacks.notifyCreated(i, bundle);
                        if (java.util.Objects.equals(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, str2)) {
                            dataAppDirectory = android.os.Environment.getDataAppDirectory(str2);
                            z = true;
                        } else if (java.util.Objects.equals("primary_physical", str2)) {
                            dataAppDirectory = storageManager.getPrimaryPhysicalVolume().getPath();
                            z = false;
                        } else {
                            android.os.storage.VolumeInfo findVolumeByUuid = storageManager.findVolumeByUuid(str2);
                            if (findVolumeByUuid == null || findVolumeByUuid.getType() != 1 || !findVolumeByUuid.isMountedWritable()) {
                                freezePackage.close();
                                throw new com.android.server.pm.PackageManagerException(-6, "Move location not mounted private volume");
                            }
                            dataAppDirectory = android.os.Environment.getDataAppDirectory(str2);
                            z = true;
                        }
                        if (z) {
                            for (int i4 : queryInstalledUsers) {
                                if (android.os.storage.StorageManager.isFileEncrypted() && !android.os.storage.StorageManager.isCeStorageUnlocked(i4)) {
                                    freezePackage.close();
                                    throw new com.android.server.pm.PackageManagerException(-10, "User " + i4 + " must be unlocked");
                                }
                            }
                        }
                        android.content.pm.PackageStats packageStats = new android.content.pm.PackageStats(null, -1);
                        synchronized (this.mPm.mInstallLock) {
                            try {
                                for (int i5 : queryInstalledUsers) {
                                    if (!getPackageSizeInfoLI(str, i5, packageStats)) {
                                        freezePackage.close();
                                        throw new com.android.server.pm.PackageManagerException(-6, "Failed to measure package size");
                                    }
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                        final long usableSpace = dataAppDirectory.getUsableSpace();
                        if (z) {
                            j = packageStats.codeSize + packageStats.dataSize;
                        } else {
                            j = packageStats.codeSize;
                        }
                        if (j > storageManager.getStorageBytesUntilLow(dataAppDirectory)) {
                            freezePackage.close();
                            throw new com.android.server.pm.PackageManagerException(-6, "Not enough free space to move");
                        }
                        try {
                            prepareUserStorageForMove(volumeUuid, str2, queryInstalledUsers);
                            this.mPm.mMoveCallbacks.notifyStatusChanged(i, 10);
                            final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
                            android.content.pm.IPackageInstallObserver2.Stub stub = new android.content.pm.IPackageInstallObserver2.Stub() { // from class: com.android.server.pm.MovePackageHelper.1
                                public void onUserActionRequired(android.content.Intent intent) throws android.os.RemoteException {
                                    freezePackage.close();
                                    throw new java.lang.IllegalStateException();
                                }

                                public void onPackageInstalled(java.lang.String str3, int i6, java.lang.String str4, android.os.Bundle bundle2) throws android.os.RemoteException {
                                    countDownLatch.countDown();
                                    freezePackage.close();
                                    switch (android.content.pm.PackageManager.installStatusToPublicStatus(i6)) {
                                        case 0:
                                            com.android.server.pm.MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(i, -100);
                                            com.android.server.pm.MovePackageHelper.this.logAppMovedStorage(str, isExternalStorage);
                                            break;
                                        case 6:
                                            com.android.server.pm.MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(i, -1);
                                            break;
                                        default:
                                            com.android.server.pm.MovePackageHelper.this.mPm.mMoveCallbacks.notifyStatusChanged(i, -6);
                                            break;
                                    }
                                }
                            };
                            if (z) {
                                final java.io.File file2 = dataAppDirectory;
                                final long j2 = j;
                                new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.pm.MovePackageHelper$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        com.android.server.pm.MovePackageHelper.this.lambda$movePackageInternal$0(countDownLatch, usableSpace, file2, j2, i);
                                    }
                                }).start();
                                moveInfo = new com.android.server.pm.MoveInfo(i, volumeUuid, str2, str, appId, seInfo, targetSdkVersion, absolutePath);
                            } else {
                                moveInfo = null;
                            }
                            com.android.server.pm.OriginInfo fromExistingFile = com.android.server.pm.OriginInfo.fromExistingFile(file);
                            android.content.pm.parsing.result.ParseResult parsePackageLite = android.content.pm.parsing.ApkLiteParseUtils.parsePackageLite(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing(), new java.io.File(fromExistingFile.mResolvedPath), 0);
                            new com.android.server.pm.InstallingSession(fromExistingFile, moveInfo, stub, 18, 0, installSource, str2, of, cpuAbiOverride, 0, parsePackageLite.isSuccess() ? (android.content.pm.parsing.PackageLite) parsePackageLite.getResult() : null, this.mPm).movePackage();
                            return;
                        } catch (java.lang.RuntimeException e) {
                            freezePackage.close();
                            throw new com.android.server.pm.PackageManagerException(-6, "Failed to prepare user storage while moving app");
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
        }
        throw new com.android.server.pm.PackageManagerException(-2, "Package is not installed for any user");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$movePackageInternal$0(java.util.concurrent.CountDownLatch countDownLatch, long j, java.io.File file, long j2, int i) {
        while (!countDownLatch.await(1L, java.util.concurrent.TimeUnit.SECONDS)) {
            this.mPm.mMoveCallbacks.notifyStatusChanged(i, ((int) android.util.MathUtils.constrain(((j - file.getUsableSpace()) * 80) / j2, 0L, 80L)) + 10);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logAppMovedStorage(java.lang.String str, boolean z) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPm.snapshotComputer().getPackage(str);
        if (androidPackage == null) {
            return;
        }
        int packageExternalStorageType = com.android.server.pm.PackageManagerServiceUtils.getPackageExternalStorageType(((android.os.storage.StorageManager) this.mPm.mInjector.getSystemService(android.os.storage.StorageManager.class)).findVolumeByUuid(android.os.storage.StorageManager.convert(androidPackage.getVolumeUuid()).toString()), androidPackage.isExternalStorage());
        if (!z && androidPackage.isExternalStorage()) {
            com.android.internal.util.FrameworkStatsLog.write(183, packageExternalStorageType, 1, str);
        } else if (z && !androidPackage.isExternalStorage()) {
            com.android.internal.util.FrameworkStatsLog.write(183, packageExternalStorageType, 2, str);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private boolean getPackageSizeInfoLI(java.lang.String str, int i, android.content.pm.PackageStats packageStats) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(str);
        if (packageStateInternal == null) {
            android.util.Slog.w("PackageManager", "Failed to find settings for " + str);
            return false;
        }
        try {
            this.mPm.mInstaller.getAppSize(packageStateInternal.getVolumeUuid(), new java.lang.String[]{str}, i, 0, packageStateInternal.getAppId(), new long[]{packageStateInternal.getUserStateOrDefault(i).getCeDataInode()}, new java.lang.String[]{packageStateInternal.getPathString()}, packageStats);
            if (com.android.server.pm.PackageManagerServiceUtils.isSystemApp(packageStateInternal) && !com.android.server.pm.PackageManagerServiceUtils.isUpdatedSystemApp(packageStateInternal)) {
                packageStats.codeSize = 0L;
            }
            packageStats.dataSize -= packageStats.cacheSize;
            return true;
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.w("PackageManager", java.lang.String.valueOf(e));
            return false;
        }
    }

    private void prepareUserStorageForMove(java.lang.String str, java.lang.String str2, int[] iArr) {
        android.os.storage.StorageManagerInternal storageManagerInternal = (android.os.storage.StorageManagerInternal) this.mPm.mInjector.getLocalService(android.os.storage.StorageManagerInternal.class);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i : iArr) {
            arrayList.add(this.mPm.mUserManager.getUserInfo(i));
        }
        storageManagerInternal.prepareUserStorageForMove(str, str2, arrayList);
    }

    public static class MoveCallbacks extends android.os.Handler {
        private static final int MSG_CREATED = 1;
        private static final int MSG_STATUS_CHANGED = 2;
        private final android.os.RemoteCallbackList<android.content.pm.IPackageMoveObserver> mCallbacks;
        public final android.util.SparseIntArray mLastStatus;

        public MoveCallbacks(android.os.Looper looper) {
            super(looper);
            this.mCallbacks = new android.os.RemoteCallbackList<>();
            this.mLastStatus = new android.util.SparseIntArray();
        }

        public void register(android.content.pm.IPackageMoveObserver iPackageMoveObserver) {
            this.mCallbacks.register(iPackageMoveObserver);
        }

        public void unregister(android.content.pm.IPackageMoveObserver iPackageMoveObserver) {
            this.mCallbacks.unregister(iPackageMoveObserver);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
            int beginBroadcast = this.mCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    invokeCallback(this.mCallbacks.getBroadcastItem(i), message.what, someArgs);
                } catch (android.os.RemoteException e) {
                }
            }
            this.mCallbacks.finishBroadcast();
            someArgs.recycle();
        }

        private void invokeCallback(android.content.pm.IPackageMoveObserver iPackageMoveObserver, int i, com.android.internal.os.SomeArgs someArgs) throws android.os.RemoteException {
            switch (i) {
                case 1:
                    iPackageMoveObserver.onCreated(someArgs.argi1, (android.os.Bundle) someArgs.arg2);
                    break;
                case 2:
                    iPackageMoveObserver.onStatusChanged(someArgs.argi1, someArgs.argi2, ((java.lang.Long) someArgs.arg3).longValue());
                    break;
            }
        }

        public void notifyCreated(int i, android.os.Bundle bundle) {
            android.util.Slog.v("PackageManager", "Move " + i + " created " + bundle.toString());
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.arg2 = bundle;
            obtainMessage(1, obtain).sendToTarget();
        }

        public void notifyStatusChanged(int i, int i2) {
            notifyStatusChanged(i, i2, -1L);
        }

        public void notifyStatusChanged(int i, int i2, long j) {
            android.util.Slog.v("PackageManager", "Move " + i + " status " + i2);
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.arg3 = java.lang.Long.valueOf(j);
            obtainMessage(2, obtain).sendToTarget();
            synchronized (this.mLastStatus) {
                this.mLastStatus.put(i, i2);
            }
        }
    }
}
