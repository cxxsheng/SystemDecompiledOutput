package com.android.server.pm;

/* loaded from: classes2.dex */
final class RemovePackageHelper {
    private final com.android.server.pm.AppDataHelper mAppDataHelper;
    private final com.android.server.pm.BroadcastHelper mBroadcastHelper;
    private final android.os.incremental.IncrementalManager mIncrementalManager;
    private final com.android.server.pm.Installer mInstaller;
    private final com.android.server.pm.permission.PermissionManagerServiceInternal mPermissionManager;
    private final com.android.server.pm.PackageManagerService mPm;
    private final com.android.server.pm.SharedLibrariesImpl mSharedLibraries;

    RemovePackageHelper(com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.AppDataHelper appDataHelper, com.android.server.pm.BroadcastHelper broadcastHelper) {
        this.mPm = packageManagerService;
        this.mIncrementalManager = this.mPm.mInjector.getIncrementalManager();
        this.mInstaller = this.mPm.mInjector.getInstaller();
        this.mPermissionManager = this.mPm.mInjector.getPermissionManagerServiceInternal();
        this.mSharedLibraries = this.mPm.mInjector.getSharedLibrariesImpl();
        this.mAppDataHelper = appDataHelper;
        this.mBroadcastHelper = broadcastHelper;
    }

    public void removeCodePath(java.io.File file) {
        synchronized (this.mPm.mInstallLock) {
            removeCodePathLI(file);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private void removeCodePathLI(java.io.File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            java.io.File parentFile = file.getParentFile();
            boolean startsWith = parentFile.getName().startsWith("~~");
            try {
                if (this.mIncrementalManager != null && android.os.incremental.IncrementalManager.isIncrementalPath(file.getAbsolutePath())) {
                    if (startsWith) {
                        this.mIncrementalManager.rmPackageDir(parentFile);
                    } else {
                        this.mIncrementalManager.rmPackageDir(file);
                    }
                }
                java.lang.String name = file.getName();
                this.mInstaller.rmPackageDir(name, file.getAbsolutePath());
                if (startsWith) {
                    this.mInstaller.rmPackageDir(name, parentFile.getAbsolutePath());
                    removeCachedResult(parentFile);
                    return;
                }
                return;
            } catch (com.android.server.pm.Installer.InstallerException e) {
                android.util.Slog.w("PackageManager", "Failed to remove code path", e);
                return;
            }
        }
        file.delete();
    }

    private void removeCachedResult(@android.annotation.NonNull java.io.File file) {
        if (this.mPm.getCacheDir() == null) {
            return;
        }
        new com.android.server.pm.parsing.PackageCacher(this.mPm.getCacheDir()).cleanCachedResult(file);
    }

    public void removePackage(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) {
        synchronized (this.mPm.mInstallLock) {
            removePackageLI(androidPackage, z);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private void removePackageLI(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(androidPackage.getPackageName());
        if (packageStateInternal != null) {
            removePackageLI(packageStateInternal.getPackageName(), z);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private void removePackageLI(java.lang.String str, boolean z) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.pkg.AndroidPackage remove = this.mPm.mPackages.remove(str);
                if (remove != null) {
                    cleanPackageDataStructuresLILPw(remove, com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.isSystem(remove), z);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    private void cleanPackageDataStructuresLILPw(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, boolean z2) {
        this.mPm.mComponentResolver.removeAllComponents(androidPackage, z2);
        this.mPermissionManager.onPackageRemoved(androidPackage);
        this.mPm.getPackageProperty().removeAllProperties(androidPackage);
        int size = com.android.internal.util.ArrayUtils.size(androidPackage.getInstrumentations());
        for (int i = 0; i < size; i++) {
            this.mPm.getInstrumentation().remove(((com.android.internal.pm.pkg.component.ParsedInstrumentation) androidPackage.getInstrumentations().get(i)).getComponentName());
        }
        if (z) {
            int size2 = androidPackage.getLibraryNames().size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.mSharedLibraries.removeSharedLibrary((java.lang.String) androidPackage.getLibraryNames().get(i2), 0L);
            }
        }
        if (androidPackage.getSdkLibraryName() != null) {
            this.mSharedLibraries.removeSharedLibrary(androidPackage.getSdkLibraryName(), androidPackage.getSdkLibVersionMajor());
        }
        if (androidPackage.getStaticSharedLibraryName() != null) {
            this.mSharedLibraries.removeSharedLibrary(androidPackage.getStaticSharedLibraryName(), androidPackage.getStaticSharedLibraryVersion());
        }
    }

    public void clearPackageStateForUserLIF(final com.android.server.pm.PackageSetting packageSetting, final int i, int i2) {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        com.android.server.pm.SharedUserSetting sharedUserSettingLPr;
        com.android.server.pm.pkg.AndroidPackage buildFakeForDeletion;
        java.lang.String packageName = packageSetting.getPackageName();
        this.mAppDataHelper.destroyAppProfilesLIF(packageName);
        if ((i2 & 1) != 0) {
            return;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                androidPackage = this.mPm.mPackages.get(packageName);
                sharedUserSettingLPr = this.mPm.mSettings.getSharedUserSettingLPr(packageSetting);
            } finally {
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (androidPackage != null) {
            buildFakeForDeletion = androidPackage;
        } else {
            buildFakeForDeletion = com.android.internal.pm.parsing.pkg.PackageImpl.buildFakeForDeletion(packageName, packageSetting.getVolumeUuid());
        }
        this.mAppDataHelper.destroyAppDataLIF(buildFakeForDeletion, i, 7);
        if (i != -1) {
            packageSetting.setCeDataInode(-1L, i);
            packageSetting.setDeDataInode(-1L, i);
        }
        final com.android.server.pm.PreferredActivityHelper preferredActivityHelper = new com.android.server.pm.PreferredActivityHelper(this.mPm, this.mBroadcastHelper);
        if (i == -1) {
            this.mPm.mDomainVerificationManager.clearPackage(packageName);
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock2) {
                try {
                    this.mPm.mSettings.getKeySetManagerService().removeAppKeySetDataLPw(packageName);
                    this.mPm.mInjector.getUpdateOwnershipHelper().removeUpdateOwnerDenyList(packageName);
                    com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
                    this.mPm.mAppsFilter.removePackage(snapshotComputer, snapshotComputer.getPackageStateInternal(packageName));
                    final android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
                    this.mPm.clearPackagePreferredActivitiesLPw(packageName, sparseBooleanArray, -1);
                    this.mPm.mInjector.getBackgroundHandler().post(new java.lang.Runnable() { // from class: com.android.server.pm.RemovePackageHelper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.pm.RemovePackageHelper.this.lambda$clearPackageStateForUserLIF$0(sparseBooleanArray, preferredActivityHelper);
                        }
                    });
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        } else {
            this.mPm.mDomainVerificationManager.clearPackageForUser(packageName, i);
            preferredActivityHelper.clearPackagePreferredActivities(packageName, i);
            this.mPermissionManager.onPackageUninstalled(packageName, packageSetting.getAppId(), packageSetting, androidPackage, sharedUserSettingLPr != null ? sharedUserSettingLPr.getPackages() : java.util.Collections.emptyList(), i);
        }
        this.mPm.mInjector.getBackgroundHandler().post(new java.lang.Runnable() { // from class: com.android.server.pm.RemovePackageHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.RemovePackageHelper.this.lambda$clearPackageStateForUserLIF$1(packageSetting, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearPackageStateForUserLIF$0(android.util.SparseBooleanArray sparseBooleanArray, com.android.server.pm.PreferredActivityHelper preferredActivityHelper) {
        if (sparseBooleanArray.size() > 0) {
            preferredActivityHelper.updateDefaultHomeNotLocked(this.mPm.snapshotComputer(), sparseBooleanArray);
            this.mBroadcastHelper.sendPreferredActivityChangedBroadcast(-1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearPackageStateForUserLIF$1(com.android.server.pm.PackageSetting packageSetting, int i) {
        try {
            android.os.Trace.traceBegin(262144L, "clearKeystoreData:" + packageSetting.getAppId() + " for user: " + i);
            this.mAppDataHelper.clearKeystoreData(i, packageSetting.getAppId());
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    public void removePackageData(com.android.server.pm.PackageSetting packageSetting, @android.annotation.NonNull int[] iArr) {
        synchronized (this.mPm.mInstallLock) {
            removePackageDataLIF(packageSetting, -1, iArr, new com.android.server.pm.PackageRemovedInfo(), 0, false);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    public void removePackageDataLIF(com.android.server.pm.PackageSetting packageSetting, int i, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull com.android.server.pm.PackageRemovedInfo packageRemovedInfo, int i2, boolean z) {
        java.lang.String packageName = packageSetting.getPackageName();
        com.android.server.pm.pkg.AndroidPackage pkg = packageSetting.getPkg();
        clearPackageStateForUserLIF(packageSetting, i, i2);
        boolean z2 = false;
        removePackageLI(packageName, (Integer.MIN_VALUE & i2) != 0);
        if (!packageSetting.isSystem()) {
            packageSetting.setPkg(null);
        }
        if (shouldDeletePackageSetting(packageSetting, i, iArr, i2)) {
            new android.util.SparseBooleanArray();
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    packageRemovedInfo.mIsAppIdRemoved = this.mPm.mSettings.removePackageAndAppIdLPw(packageName);
                    if (!this.mPm.mSettings.isDisabledSystemPackageLPr(packageName)) {
                        com.android.server.pm.SharedUserSetting sharedUserSettingLPr = this.mPm.mSettings.getSharedUserSettingLPr(packageSetting);
                        this.mPermissionManager.onPackageUninstalled(packageName, packageSetting.getAppId(), packageSetting, pkg, sharedUserSettingLPr != null ? sharedUserSettingLPr.getPackages() : java.util.Collections.emptyList(), -1);
                        if (sharedUserSettingLPr != null) {
                            this.mPm.mSettings.checkAndConvertSharedUserSettingsLPw(sharedUserSettingLPr);
                        }
                    }
                    this.mPm.mSettings.removeRenamedPackageLPw(packageSetting.getRealName());
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        } else if (!packageSetting.isSystem() && !packageRemovedInfo.mIsUpdate && packageRemovedInfo.mRemovedUsers != null && !packageSetting.isExternalStorage()) {
            java.lang.System.currentTimeMillis();
            for (int i3 : packageRemovedInfo.mRemovedUsers) {
                packageSetting.setInstalled(false, i3);
            }
        }
        if (packageRemovedInfo.mOrigUsers != null && packageSetting.isSystem()) {
            boolean z3 = false;
            for (int i4 : iArr) {
                boolean contains = com.android.internal.util.ArrayUtils.contains(packageRemovedInfo.mOrigUsers, i4);
                if (contains != packageSetting.getInstalled(i4)) {
                    z3 = true;
                }
                packageSetting.setInstalled(contains, i4);
                if (contains) {
                    packageSetting.setUninstallReason(0, i4);
                }
            }
            z2 = z3;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock2) {
            if (z) {
                try {
                    this.mPm.writeSettingsLPrTEMP();
                } finally {
                }
            }
            if (z2) {
                this.mPm.mSettings.writeKernelMappingLPr(packageSetting);
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    private static boolean shouldDeletePackageSetting(com.android.server.pm.PackageSetting packageSetting, int i, int[] iArr, int i2) {
        if ((i2 & 1) != 0) {
            return false;
        }
        return i == -1 || !packageSetting.hasDataOnAnyOtherUser(iArr, i);
    }

    void cleanUpResources(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.io.File file, @android.annotation.Nullable java.lang.String[] strArr) {
        synchronized (this.mPm.mInstallLock) {
            cleanUpResourcesLI(file, strArr);
        }
        if (str == null) {
            return;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
                if (packageLPr != null) {
                    packageLPr.removeOldPath(file);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private void cleanUpResourcesLI(@android.annotation.Nullable java.io.File file, @android.annotation.Nullable java.lang.String[] strArr) {
        java.util.List<java.lang.String> list = java.util.Collections.EMPTY_LIST;
        if (file != null && file.exists()) {
            android.content.pm.parsing.result.ParseResult parsePackageLite = android.content.pm.parsing.ApkLiteParseUtils.parsePackageLite(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing().reset(), file, 0);
            if (parsePackageLite.isSuccess()) {
                list = ((android.content.pm.parsing.PackageLite) parsePackageLite.getResult()).getAllApkPaths();
            }
        }
        removeCodePathLI(file);
        removeDexFilesLI(list, strArr);
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private void removeDexFilesLI(@android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.Nullable java.lang.String[] strArr) {
        if (!list.isEmpty()) {
            if (strArr == null) {
                throw new java.lang.IllegalStateException("instructionSet == null");
            }
            if (!com.android.server.pm.DexOptHelper.useArtService()) {
                java.lang.String[] dexCodeInstructionSets = com.android.server.pm.InstructionSets.getDexCodeInstructionSets(strArr);
                for (java.lang.String str : list) {
                    for (java.lang.String str2 : dexCodeInstructionSets) {
                        try {
                            this.mPm.mInstaller.rmdex(str, str2);
                        } catch (com.android.server.pm.Installer.InstallerException e) {
                        } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e2) {
                            throw new java.lang.RuntimeException(e2);
                        }
                    }
                }
            }
        }
    }

    void cleanUpForMoveInstall(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.io.File file = new java.io.File(android.os.Environment.getDataAppDirectory(str), new java.io.File(str3).getName());
        android.util.Slog.d("PackageManager", "Cleaning up " + str2 + " on " + str);
        int[] userIds = this.mPm.mUserManager.getUserIds();
        synchronized (this.mPm.mInstallLock) {
            for (int i : userIds) {
                try {
                    this.mPm.mInstaller.destroyAppData(str, str2, i, 131075, 0L);
                } catch (com.android.server.pm.Installer.InstallerException e) {
                    android.util.Slog.w("PackageManager", java.lang.String.valueOf(e));
                }
            }
            removeCodePathLI(file);
        }
    }
}
