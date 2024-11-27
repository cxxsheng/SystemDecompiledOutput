package com.android.server.pm;

/* loaded from: classes2.dex */
final class DeletePackageHelper {
    private static final boolean DEBUG_CLEAN_APKS = false;
    private static final boolean DEBUG_SD_INSTALL = false;
    private final com.android.server.pm.BroadcastHelper mBroadcastHelper;
    private final com.android.server.pm.PackageManagerService mPm;
    private final com.android.server.pm.RemovePackageHelper mRemovePackageHelper;
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal;

    DeletePackageHelper(com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.RemovePackageHelper removePackageHelper, com.android.server.pm.BroadcastHelper broadcastHelper) {
        this.mPm = packageManagerService;
        this.mUserManagerInternal = this.mPm.mInjector.getUserManagerInternal();
        this.mRemovePackageHelper = removePackageHelper;
        this.mBroadcastHelper = broadcastHelper;
    }

    /* JADX WARN: Finally extract failed */
    public int deletePackageX(java.lang.String str, long j, int i, int i2, boolean z) {
        int[] iArr;
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        android.util.SparseArray sparseArray;
        int i3;
        com.android.server.pm.pkg.AndroidPackage androidPackage2;
        int[] iArr2;
        boolean deletePackageLIF;
        int i4;
        com.android.server.pm.PackageSetting packageLPr;
        boolean z2;
        boolean z3;
        android.content.pm.SharedLibraryInfo sharedLibraryInfo;
        int i5;
        int i6;
        boolean z4;
        android.content.pm.UserInfo userInfo;
        com.android.server.pm.PackageRemovedInfo packageRemovedInfo = new com.android.server.pm.PackageRemovedInfo();
        int i7 = -1;
        int i8 = (i2 & 2) != 0 ? -1 : i;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
                com.android.server.pm.PackageSetting packageLPr2 = this.mPm.mSettings.getPackageLPr(str);
                if (packageLPr2 == null) {
                    android.util.Slog.w("PackageManager", "Not removing non-existent package " + str);
                    return -1;
                }
                if (j != -1 && packageLPr2.getVersionCode() != j) {
                    android.util.Slog.w("PackageManager", "Not removing package " + str + " with versionCode " + packageLPr2.getVersionCode() + " != " + j);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return -1;
                }
                if (com.android.server.pm.PackageManagerServiceUtils.isUpdatedSystemApp(packageLPr2) && (i2 & 4) == 0 && ((userInfo = this.mUserManagerInternal.getUserInfo(i)) == null || (!userInfo.isAdmin() && !this.mUserManagerInternal.getUserInfo(this.mUserManagerInternal.getProfileParentId(i)).isAdmin()))) {
                    android.util.Slog.w("PackageManager", "Not removing package " + str + " as only admin user (or their profile) may downgrade system apps");
                    android.util.EventLog.writeEvent(1397638484, "170646036", -1, str);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return -3;
                }
                com.android.server.pm.PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(str);
                com.android.server.pm.pkg.AndroidPackage androidPackage3 = this.mPm.mPackages.get(str);
                int[] userIds = this.mUserManagerInternal.getUserIds();
                boolean z5 = false;
                if (androidPackage3 != null) {
                    if (androidPackage3.getStaticSharedLibraryName() != null) {
                        sharedLibraryInfo = snapshotComputer.getSharedLibraryInfo(androidPackage3.getStaticSharedLibraryName(), androidPackage3.getStaticSharedLibraryVersion());
                    } else if (androidPackage3.getSdkLibraryName() == null) {
                        sharedLibraryInfo = null;
                    } else {
                        sharedLibraryInfo = snapshotComputer.getSharedLibraryInfo(androidPackage3.getSdkLibraryName(), androidPackage3.getSdkLibVersionMajor());
                    }
                    if (sharedLibraryInfo != null) {
                        boolean sdkLibIndependence = android.content.pm.Flags.sdkLibIndependence();
                        int length = userIds.length;
                        int i9 = 0;
                        while (i9 < length) {
                            int i10 = userIds[i9];
                            if (i8 != i7 && i8 != i10) {
                                i5 = i9;
                                i6 = length;
                            } else {
                                i5 = i9;
                                i6 = length;
                                android.util.Pair<java.util.List<android.content.pm.VersionedPackage>, java.util.List<java.lang.Boolean>> packagesUsingSharedLibrary = snapshotComputer.getPackagesUsingSharedLibrary(sharedLibraryInfo, 4202496L, 1000, i10);
                                java.util.List list = (java.util.List) packagesUsingSharedLibrary.first;
                                java.util.List list2 = (java.util.List) packagesUsingSharedLibrary.second;
                                if (androidPackage3.getSdkLibraryName() != null && !com.android.internal.util.ArrayUtils.isEmpty(list) && !com.android.internal.util.ArrayUtils.isEmpty(list2) && list.size() == list2.size() && sdkLibIndependence) {
                                    int i11 = 0;
                                    while (true) {
                                        if (i11 >= list.size()) {
                                            z4 = true;
                                            break;
                                        }
                                        if (((java.lang.Boolean) list2.get(i11)).booleanValue()) {
                                            i11++;
                                        } else {
                                            z4 = false;
                                            break;
                                        }
                                    }
                                } else {
                                    z4 = false;
                                }
                                if (!com.android.internal.util.ArrayUtils.isEmpty(list) && !z4) {
                                    android.util.Slog.w("PackageManager", "Not removing package " + androidPackage3.getManifestPackageName() + " hosting lib " + sharedLibraryInfo.getName() + " version " + sharedLibraryInfo.getLongVersion() + " used by " + list + " for user " + i10);
                                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                    return -6;
                                }
                            }
                            i9 = i5 + 1;
                            length = i6;
                            i7 = -1;
                        }
                    }
                }
                packageRemovedInfo.mOrigUsers = packageLPr2.queryInstalledUsers(userIds, true);
                if (!com.android.server.pm.PackageManagerServiceUtils.isUpdatedSystemApp(packageLPr2) || (i2 & 4) != 0) {
                    iArr = userIds;
                    androidPackage = null;
                    sparseArray = null;
                    i3 = i8;
                } else {
                    android.util.SparseArray sparseArray2 = new android.util.SparseArray();
                    int i12 = 0;
                    while (i12 < userIds.length) {
                        com.android.server.pm.pkg.PackageUserStateInternal readUserState = packageLPr2.readUserState(userIds[i12]);
                        sparseArray2.put(userIds[i12], new com.android.server.pm.DeletePackageHelper.TempUserState(readUserState.getEnabledState(), readUserState.getLastDisableAppCaller(), readUserState.isInstalled()));
                        i12++;
                        userIds = userIds;
                    }
                    iArr = userIds;
                    androidPackage = null;
                    sparseArray = sparseArray2;
                    i3 = -1;
                }
                boolean isInstallerPackage = this.mPm.mSettings.isInstallerPackage(str);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                synchronized (this.mPm.mInstallLock) {
                    try {
                        androidPackage2 = androidPackage;
                        iArr2 = iArr;
                        com.android.server.pm.PackageFreezer freezePackageForDelete = this.mPm.freezePackageForDelete(str, i3, i2, "deletePackageX", 13);
                        try {
                            deletePackageLIF = deletePackageLIF(str, android.os.UserHandle.of(i8), true, iArr2, i2 | Integer.MIN_VALUE, packageRemovedInfo, true);
                            if (freezePackageForDelete != null) {
                                freezePackageForDelete.close();
                            }
                            if (deletePackageLIF && androidPackage3 != null) {
                                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
                                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                                synchronized (packageManagerTracedLock2) {
                                    try {
                                        z3 = this.mPm.mPackages.get(androidPackage3.getPackageName()) != null;
                                    } finally {
                                    }
                                }
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                this.mPm.mInstantAppRegistry.onPackageUninstalled(androidPackage3, packageLPr2, packageRemovedInfo.mRemovedUsers, z3);
                            }
                            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mPm.mLock;
                            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                            synchronized (packageManagerTracedLock3) {
                                if (deletePackageLIF) {
                                    try {
                                        this.mPm.updateSequenceNumberLP(packageLPr2, packageRemovedInfo.mRemovedUsers);
                                        this.mPm.updateInstantAppInstallerLocked(str);
                                    } catch (java.lang.Throwable th) {
                                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                        throw th;
                                    }
                                }
                            }
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            android.app.ApplicationPackageManager.invalidateGetPackagesForUidCache();
                        } finally {
                        }
                    } catch (java.lang.Throwable th2) {
                        throw th2;
                    }
                }
                if (deletePackageLIF) {
                    this.mBroadcastHelper.sendPackageRemovedBroadcasts(packageRemovedInfo, this.mPm, (i2 & 8) == 0, z, (i2 & 16) != 0);
                    this.mBroadcastHelper.sendSystemPackageUpdatedBroadcasts(packageRemovedInfo);
                    com.android.server.pm.PackageMetrics.onUninstallSucceeded(packageRemovedInfo, i2, i8);
                }
                dalvik.system.VMRuntime.getRuntime().requestConcurrentGC();
                synchronized (this.mPm.mInstallLock) {
                    try {
                        if (packageRemovedInfo.mArgs != null) {
                            this.mRemovePackageHelper.cleanUpResources(packageRemovedInfo.mArgs.getPackageName(), packageRemovedInfo.mArgs.getCodeFile(), packageRemovedInfo.mArgs.getInstructionSets());
                        }
                        if (sparseArray == null) {
                            i4 = 1;
                        } else {
                            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock4 = this.mPm.mLock;
                            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                            synchronized (packageManagerTracedLock4) {
                                try {
                                    com.android.server.pm.PackageSetting packageSettingForMutation = this.mPm.getPackageSettingForMutation(str);
                                    if (packageSettingForMutation != null) {
                                        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageSettingForMutation.getPkg();
                                        boolean z6 = pkg != null && pkg.isEnabled();
                                        int i13 = 0;
                                        while (true) {
                                            int[] iArr3 = iArr2;
                                            if (i13 >= iArr3.length) {
                                                break;
                                            }
                                            com.android.server.pm.DeletePackageHelper.TempUserState tempUserState = (com.android.server.pm.DeletePackageHelper.TempUserState) sparseArray.get(iArr3[i13]);
                                            int i14 = tempUserState.enabledState;
                                            packageSettingForMutation.setEnabled(i14, iArr3[i13], tempUserState.lastDisableAppCaller);
                                            if (!z5 && tempUserState.installed) {
                                                if (i14 == 0 && z6) {
                                                    z2 = true;
                                                } else {
                                                    z2 = true;
                                                    if (i14 != 1) {
                                                    }
                                                }
                                                z5 = z2;
                                            }
                                            i13++;
                                            iArr2 = iArr3;
                                        }
                                        i4 = 1;
                                    } else {
                                        i4 = 1;
                                        android.util.Slog.w("PackageManager", "Missing PackageSetting after uninstalling the update for system app: " + str + ". This should not happen.");
                                    }
                                    this.mPm.mSettings.writeAllUsersPackageRestrictionsLPr();
                                } catch (java.lang.Throwable th3) {
                                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                    throw th3;
                                }
                            }
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        }
                        com.android.server.pm.pkg.AndroidPackage pkg2 = disabledSystemPkgLPr == null ? androidPackage2 : disabledSystemPkgLPr.getPkg();
                        if (pkg2 != null && pkg2.isStub()) {
                            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock5 = this.mPm.mLock;
                            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                            synchronized (packageManagerTracedLock5) {
                                try {
                                    packageLPr = this.mPm.mSettings.getPackageLPr(pkg2.getPackageName());
                                } finally {
                                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                }
                            }
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            if (packageLPr != null) {
                                if (z5) {
                                    if (com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION) {
                                        android.util.Slog.i("PackageManager", "Enabling system stub after removal; pkg: " + pkg2.getPackageName());
                                    }
                                    this.mPm.enableCompressedPackage(pkg2, packageLPr);
                                } else if (com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION) {
                                    android.util.Slog.i("PackageManager", "System stub disabled for all users, leaving uncompressed after removal; pkg: " + pkg2.getPackageName());
                                }
                            }
                        }
                    } catch (java.lang.Throwable th4) {
                        throw th4;
                    }
                }
                if (deletePackageLIF && isInstallerPackage) {
                    this.mPm.mInjector.getPackageInstallerService().onInstallerPackageDeleted(packageLPr2.getAppId(), i8);
                }
                if (deletePackageLIF) {
                    return i4;
                }
                return -1;
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
    }

    private void deleteArtDexoptArtifacts(java.lang.String str) {
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = com.android.server.pm.PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
        try {
            try {
                com.android.server.pm.DexOptHelper.getArtManagerLocal().deleteDexoptArtifacts(withFilteredSnapshot, str);
            } catch (java.lang.IllegalArgumentException | java.lang.IllegalStateException e) {
                android.util.Slog.w("PackageManager", e.toString());
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

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    public boolean deletePackageLIF(@android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle, boolean z, @android.annotation.NonNull int[] iArr, int i, @android.annotation.NonNull com.android.server.pm.PackageRemovedInfo packageRemovedInfo, boolean z2) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
                if (packageLPr == null) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return false;
                }
                com.android.server.pm.PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(packageLPr);
                if (com.android.server.pm.PackageManagerServiceUtils.isSystemApp(packageLPr) && this.mPm.checkPermission("android.permission.CONTROL_KEYGUARD", str, 0) == 0) {
                    android.util.Slog.w("PackageManager", "Attempt to delete keyguard system package " + str);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return false;
                }
                com.android.server.pm.DeletePackageAction mayDeletePackageLocked = mayDeletePackageLocked(packageRemovedInfo, packageLPr, disabledSystemPkgLPr, i, userHandle);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                if (mayDeletePackageLocked == null) {
                    return false;
                }
                try {
                    executeDeletePackageLIF(mayDeletePackageLocked, str, z, iArr, z2);
                    return true;
                } catch (com.android.server.pm.SystemDeleteException e) {
                    return false;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public static com.android.server.pm.DeletePackageAction mayDeletePackageLocked(@android.annotation.NonNull com.android.server.pm.PackageRemovedInfo packageRemovedInfo, com.android.server.pm.PackageSetting packageSetting, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting2, int i, android.os.UserHandle userHandle) {
        if (packageSetting == null) {
            return null;
        }
        if (com.android.server.pm.PackageManagerServiceUtils.isSystemApp(packageSetting)) {
            boolean z = (i & 4) != 0;
            boolean z2 = userHandle == null || userHandle.getIdentifier() == -1;
            if ((!z || z2) && packageSetting2 == null) {
                android.util.Slog.w("PackageManager", "Attempt to delete unknown system package " + packageSetting.getPkg().getPackageName());
                return null;
            }
        }
        return new com.android.server.pm.DeletePackageAction(packageSetting, packageSetting2, packageRemovedInfo, i, userHandle);
    }

    public void executeDeletePackage(com.android.server.pm.DeletePackageAction deletePackageAction, java.lang.String str, boolean z, @android.annotation.NonNull int[] iArr, boolean z2) throws com.android.server.pm.SystemDeleteException {
        synchronized (this.mPm.mInstallLock) {
            executeDeletePackageLIF(deletePackageAction, str, z, iArr, z2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0135 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x005a  */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [int] */
    /* JADX WARN: Type inference failed for: r4v8 */
    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void executeDeletePackageLIF(com.android.server.pm.DeletePackageAction deletePackageAction, java.lang.String str, boolean z, @android.annotation.NonNull int[] iArr, boolean z2) throws com.android.server.pm.SystemDeleteException {
        boolean z3;
        int[] iArr2;
        boolean z4;
        boolean z5;
        int length;
        ?? r4;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock;
        com.android.server.pm.PackageSetting packageSetting = deletePackageAction.mDeletingPs;
        com.android.server.pm.PackageRemovedInfo packageRemovedInfo = deletePackageAction.mRemovedInfo;
        android.os.UserHandle userHandle = deletePackageAction.mUser;
        int i = deletePackageAction.mFlags;
        boolean isSystemApp = com.android.server.pm.PackageManagerServiceUtils.isSystemApp(packageSetting);
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        for (int i2 : iArr) {
            sparseBooleanArray.put(i2, this.mPm.checkPermission("android.permission.SUSPEND_APPS", str, i2) == 0);
        }
        int identifier = userHandle == null ? -1 : userHandle.getIdentifier();
        if (!isSystemApp) {
            if (identifier != -1) {
                z3 = false;
                iArr2 = new int[]{identifier};
                packageRemovedInfo.mRemovedUsers = iArr2;
                packageRemovedInfo.populateBroadcastUsers(packageSetting);
                packageRemovedInfo.mDataRemoved = (i & 1) != 0 ? true : z3;
                packageRemovedInfo.mRemovedPackage = packageSetting.getPackageName();
                packageRemovedInfo.mInstallerPackageName = packageSetting.getInstallSource().mInstallerPackageName;
                packageRemovedInfo.mIsStaticSharedLib = (packageSetting.getPkg() != null || packageSetting.getPkg().getStaticSharedLibraryName() == null) ? z3 : true;
                packageRemovedInfo.mIsExternal = packageSetting.isExternalStorage();
                packageRemovedInfo.mRemovedPackageVersionCode = packageSetting.getVersionCode();
                if (!isSystemApp && (i & 4) == 0) {
                    z4 = true;
                } else if (identifier != -1) {
                    z4 = true;
                } else {
                    com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
                    com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                    synchronized (packageManagerTracedLock2) {
                        try {
                            markPackageUninstalledForUserLPw(packageSetting, userHandle, i);
                            if (!isSystemApp) {
                                boolean shouldKeepUninstalledPackageLPr = this.mPm.shouldKeepUninstalledPackageLPr(str);
                                if (packageSetting.isInstalledOnAnyOtherUser(this.mUserManagerInternal.getUserIds(), identifier) || shouldKeepUninstalledPackageLPr) {
                                    z5 = true;
                                } else {
                                    this.mPm.mSettings.writeKernelMappingLPr(packageSetting);
                                    z5 = z3;
                                }
                            } else {
                                z5 = true;
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    if (!z5) {
                        z4 = true;
                    } else {
                        this.mRemovePackageHelper.clearPackageStateForUserLIF(packageSetting, identifier, i);
                        packageRemovedInfo.mUid = packageSetting.getAppId();
                        packageRemovedInfo.mIsAppIdRemoved = true;
                        this.mPm.scheduleWritePackageRestrictions(userHandle);
                        return;
                    }
                }
                if (!isSystemApp) {
                    deleteInstalledSystemPackage(deletePackageAction, iArr, z2);
                    this.mPm.restoreDisabledSystemPackageLIF(deletePackageAction, iArr, z2);
                } else {
                    if (packageSetting.isIncremental()) {
                        deleteArtDexoptArtifacts(str);
                    }
                    deleteInstalledPackageLIF(packageSetting, identifier, z, i, iArr, packageRemovedInfo, z2);
                }
                com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
                int[] iArr3 = packageRemovedInfo.mRemovedUsers;
                length = iArr3.length;
                for (r4 = z3; r4 < length; r4++) {
                    int i3 = iArr3[r4];
                    if (sparseBooleanArray.get(i3)) {
                        this.mPm.unsuspendForSuspendingPackage(snapshotComputer, str, i3);
                        this.mPm.removeAllDistractingPackageRestrictions(snapshotComputer, i3);
                    }
                }
                packageManagerTracedLock = this.mPm.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        if (this.mPm.mPackages.get(packageSetting.getPackageName()) == null) {
                            z3 = z4;
                        }
                        packageRemovedInfo.mRemovedForAllUsers = z3;
                    } finally {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return;
            }
            z3 = false;
        } else {
            z3 = false;
        }
        iArr2 = packageSetting.queryUsersInstalledOrHasData(iArr);
        packageRemovedInfo.mRemovedUsers = iArr2;
        packageRemovedInfo.populateBroadcastUsers(packageSetting);
        packageRemovedInfo.mDataRemoved = (i & 1) != 0 ? true : z3;
        packageRemovedInfo.mRemovedPackage = packageSetting.getPackageName();
        packageRemovedInfo.mInstallerPackageName = packageSetting.getInstallSource().mInstallerPackageName;
        packageRemovedInfo.mIsStaticSharedLib = (packageSetting.getPkg() != null || packageSetting.getPkg().getStaticSharedLibraryName() == null) ? z3 : true;
        packageRemovedInfo.mIsExternal = packageSetting.isExternalStorage();
        packageRemovedInfo.mRemovedPackageVersionCode = packageSetting.getVersionCode();
        if (!isSystemApp) {
        }
        if (identifier != -1) {
        }
        if (!isSystemApp) {
        }
        com.android.server.pm.Computer snapshotComputer2 = this.mPm.snapshotComputer();
        int[] iArr32 = packageRemovedInfo.mRemovedUsers;
        length = iArr32.length;
        while (r4 < length) {
        }
        packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private void deleteInstalledPackageLIF(com.android.server.pm.PackageSetting packageSetting, int i, boolean z, int i2, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull com.android.server.pm.PackageRemovedInfo packageRemovedInfo, boolean z2) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                packageRemovedInfo.mUid = packageSetting.getAppId();
                packageRemovedInfo.mBroadcastAllowList = this.mPm.mAppsFilter.getVisibilityAllowList(this.mPm.snapshotComputer(), packageSetting, iArr, this.mPm.mSettings.getPackagesLocked());
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mRemovePackageHelper.removePackageDataLIF(packageSetting, i, iArr, packageRemovedInfo, i2, z2);
        if (z) {
            packageRemovedInfo.mArgs = new com.android.server.pm.CleanUpArgs(packageSetting.getName(), packageSetting.getPathString(), com.android.server.pm.InstructionSets.getAppDexInstructionSets(packageSetting.getPrimaryCpuAbiLegacy(), packageSetting.getSecondaryCpuAbiLegacy()));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    private void markPackageUninstalledForUserLPw(com.android.server.pm.PackageSetting packageSetting, android.os.UserHandle userHandle, int i) {
        int[] userIds;
        android.util.ArraySet<java.lang.String> arraySet;
        android.util.ArraySet<java.lang.String> arraySet2;
        com.android.server.pm.pkg.ArchiveState archiveState;
        long firstInstallTimeMillis;
        com.android.server.pm.PackageSetting packageSetting2 = packageSetting;
        int i2 = 0;
        boolean z = true;
        if (userHandle == null || userHandle.getIdentifier() == -1) {
            userIds = this.mUserManagerInternal.getUserIds();
        } else {
            userIds = new int[]{userHandle.getIdentifier()};
        }
        int length = userIds.length;
        while (i2 < length) {
            int i3 = userIds[i2];
            int i4 = i & 1;
            if (i4 == 0) {
                arraySet = null;
                arraySet2 = null;
            } else {
                arraySet = new android.util.ArraySet<>(packageSetting2.readUserState(i3).m6429getEnabledComponents());
                arraySet2 = new android.util.ArraySet<>(packageSetting2.readUserState(i3).m6428getDisabledComponents());
            }
            if (i4 == 0) {
                archiveState = null;
            } else {
                archiveState = packageSetting2.getUserStateOrDefault(i3).getArchiveState();
            }
            if (i4 == 0) {
                firstInstallTimeMillis = 0;
            } else {
                firstInstallTimeMillis = packageSetting2.getUserStateOrDefault(i3).getFirstInstallTimeMillis();
            }
            packageSetting.setUserState(i3, packageSetting2.getCeDataInode(i3), packageSetting2.getDeDataInode(i3), 0, false, true, true, false, 0, null, false, false, null, arraySet, arraySet2, 0, 0, null, null, firstInstallTimeMillis, 0, archiveState);
            i2++;
            packageSetting2 = packageSetting;
            length = length;
            userIds = userIds;
            z = z;
        }
        this.mPm.mSettings.writeKernelMappingLPr(packageSetting);
    }

    private void deleteInstalledSystemPackage(com.android.server.pm.DeletePackageAction deletePackageAction, @android.annotation.NonNull int[] iArr, boolean z) {
        int i;
        int i2 = deletePackageAction.mFlags;
        com.android.server.pm.PackageSetting packageSetting = deletePackageAction.mDeletingPs;
        com.android.server.pm.PackageRemovedInfo packageRemovedInfo = deletePackageAction.mRemovedInfo;
        int[] iArr2 = packageRemovedInfo.mOrigUsers;
        packageSetting.getPkg();
        com.android.server.pm.PackageSetting packageSetting2 = deletePackageAction.mDisabledPs;
        android.util.Slog.d("PackageManager", "Deleting system pkg from data partition");
        packageRemovedInfo.mIsRemovedPackageSystemUpdate = true;
        if (packageSetting2.getVersionCode() < packageSetting.getVersionCode() || packageSetting2.getAppId() != packageSetting.getAppId()) {
            i = i2 & (-2);
        } else {
            i = i2 | 1;
        }
        synchronized (this.mPm.mInstallLock) {
            deleteInstalledPackageLIF(packageSetting, -1, true, i, iArr, packageRemovedInfo, z);
        }
    }

    public void deletePackageVersionedInternal(android.content.pm.VersionedPackage versionedPackage, final android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, final int i, final int i2, boolean z) {
        final int callingUid = android.os.Binder.getCallingUid();
        this.mPm.mContext.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        final boolean canViewInstantApps = snapshotComputer.canViewInstantApps(callingUid, i);
        com.android.internal.util.Preconditions.checkNotNull(versionedPackage);
        com.android.internal.util.Preconditions.checkNotNull(iPackageDeleteObserver2);
        com.android.internal.util.Preconditions.checkArgumentInRange(versionedPackage.getLongVersionCode(), -1L, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, "versionCode must be >= -1");
        final java.lang.String packageName = versionedPackage.getPackageName();
        final long longVersionCode = versionedPackage.getLongVersionCode();
        try {
            if (((com.android.server.wm.ActivityTaskManagerInternal) this.mPm.mInjector.getLocalService(com.android.server.wm.ActivityTaskManagerInternal.class)).isBaseOfLockedTask(packageName)) {
                iPackageDeleteObserver2.onPackageDeleted(packageName, -7, (java.lang.String) null);
                android.util.EventLog.writeEvent(1397638484, "127605586", -1, "");
                return;
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        final java.lang.String resolveInternalPackageName = snapshotComputer.resolveInternalPackageName(packageName, longVersionCode);
        int callingUid2 = android.os.Binder.getCallingUid();
        if (!isOrphaned(snapshotComputer, resolveInternalPackageName) && !z && !isCallerAllowedToSilentlyUninstall(snapshotComputer, callingUid2, resolveInternalPackageName, i)) {
            this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.DeletePackageHelper.lambda$deletePackageVersionedInternal$0(packageName, iPackageDeleteObserver2);
                }
            });
            return;
        }
        int i3 = 0;
        final boolean z2 = (i2 & 2) != 0;
        int[] userIds = z2 ? this.mUserManagerInternal.getUserIds() : new int[]{i};
        if (android.os.UserHandle.getUserId(callingUid2) != i || (z2 && userIds.length > 1)) {
            this.mPm.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "deletePackage for user " + i);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int length = userIds.length;
            while (i3 < length) {
                int i4 = userIds[i3];
                int i5 = length;
                if (this.mPm.isPackageDeviceAdmin(packageName, i4)) {
                    this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.pm.DeletePackageHelper.lambda$deletePackageVersionedInternal$1(packageName, iPackageDeleteObserver2);
                        }
                    });
                    return;
                } else if (this.mPm.mProtectedPackages.isPackageDataProtected(i4, packageName)) {
                    this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.pm.DeletePackageHelper.lambda$deletePackageVersionedInternal$2(packageName, iPackageDeleteObserver2);
                        }
                    });
                    return;
                } else {
                    i3++;
                    length = i5;
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            if (this.mPm.isUserRestricted(i, "no_uninstall_apps")) {
                this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.DeletePackageHelper.lambda$deletePackageVersionedInternal$3(iPackageDeleteObserver2, packageName);
                    }
                });
            } else if (!z2 && snapshotComputer.getBlockUninstallForUser(resolveInternalPackageName, i)) {
                this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.DeletePackageHelper.lambda$deletePackageVersionedInternal$4(iPackageDeleteObserver2, packageName);
                    }
                });
            } else {
                final int[] iArr = userIds;
                this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.DeletePackageHelper.this.lambda$deletePackageVersionedInternal$5(resolveInternalPackageName, callingUid, canViewInstantApps, z2, longVersionCode, i, i2, iArr, iPackageDeleteObserver2, packageName);
                    }
                });
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deletePackageVersionedInternal$0(java.lang.String str, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2) {
        try {
            android.content.Intent intent = new android.content.Intent("android.intent.action.UNINSTALL_PACKAGE");
            intent.setData(android.net.Uri.fromParts(com.android.server.pm.Settings.ATTR_PACKAGE, str, null));
            intent.putExtra("android.content.pm.extra.CALLBACK", iPackageDeleteObserver2.asBinder());
            iPackageDeleteObserver2.onUserActionRequired(intent);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deletePackageVersionedInternal$1(java.lang.String str, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2) {
        try {
            android.util.Slog.w("PackageManager", "Not removing package " + str + ": has active device admin");
            iPackageDeleteObserver2.onPackageDeleted(str, -2, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deletePackageVersionedInternal$2(java.lang.String str, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2) {
        try {
            android.util.Slog.w("PackageManager", "Attempted to delete protected package: " + str);
            iPackageDeleteObserver2.onPackageDeleted(str, -1, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deletePackageVersionedInternal$3(android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, java.lang.String str) {
        try {
            iPackageDeleteObserver2.onPackageDeleted(str, -3, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deletePackageVersionedInternal$4(android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, java.lang.String str) {
        try {
            iPackageDeleteObserver2.onPackageDeleted(str, -4, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deletePackageVersionedInternal$5(java.lang.String str, int i, boolean z, boolean z2, long j, int i2, int i3, int[] iArr, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, java.lang.String str2) {
        boolean z3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str);
        if (packageStateInternal == null) {
            z3 = true;
        } else {
            z3 = !packageStateInternal.getUserStateOrDefault(android.os.UserHandle.getUserId(i)).isInstantApp() || z;
        }
        if (!z3) {
            i4 = -1;
        } else if (z2) {
            int[] blockUninstallForUsers = getBlockUninstallForUsers(snapshotComputer, str, iArr);
            if (com.android.internal.util.ArrayUtils.isEmpty(blockUninstallForUsers)) {
                i4 = deletePackageX(str, j, i2, i3, false);
            } else {
                int i9 = i3 & (-3);
                int length = iArr.length;
                int i10 = 0;
                while (i10 < length) {
                    int i11 = iArr[i10];
                    if (com.android.internal.util.ArrayUtils.contains(blockUninstallForUsers, i11)) {
                        i5 = i10;
                        i6 = length;
                    } else {
                        i5 = i10;
                        i6 = length;
                        int deletePackageX = deletePackageX(str, j, i11, i9, false);
                        if (deletePackageX != 1) {
                            android.util.Slog.w("PackageManager", "Package delete failed for user " + i11 + ", returnCode " + deletePackageX);
                        }
                    }
                    i10 = i5 + 1;
                    length = i6;
                }
                i4 = -4;
            }
        } else {
            i4 = deletePackageX(str, j, i2, i3, false);
            if (i4 == 1 && packageStateInternal != null) {
                int[] profileIds = this.mUserManagerInternal.getProfileIds(i2, true);
                int length2 = profileIds.length;
                int i12 = i4;
                int i13 = 0;
                while (i13 < length2) {
                    int i14 = profileIds[i13];
                    if (i14 == i2) {
                        i7 = i13;
                        i8 = length2;
                    } else if (this.mUserManagerInternal.getProfileParentId(i14) != i2) {
                        i7 = i13;
                        i8 = length2;
                    } else if (!packageStateInternal.getUserStateOrDefault(i14).isInstalled()) {
                        i7 = i13;
                        i8 = length2;
                    } else {
                        android.content.pm.UserProperties userProperties = this.mUserManagerInternal.getUserProperties(i14);
                        if (userProperties == null || !userProperties.getDeleteAppWithParent()) {
                            i7 = i13;
                            i8 = length2;
                        } else {
                            i7 = i13;
                            i8 = length2;
                            int deletePackageX2 = deletePackageX(str, j, i14, i3, false);
                            if (deletePackageX2 != 1) {
                                android.util.Slog.w("PackageManager", "Package delete failed for user " + i14 + ", returnCode " + deletePackageX2);
                                i12 = -8;
                            }
                        }
                    }
                    i13 = i7 + 1;
                    length2 = i8;
                }
                i4 = i12;
            }
        }
        try {
            iPackageDeleteObserver2.onPackageDeleted(str2, i4, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
            android.util.Log.i("PackageManager", "Observer no longer exists.");
        }
        this.mPm.schedulePruneUnusedStaticSharedLibraries(true);
    }

    private boolean isOrphaned(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        return packageStateInternal != null && packageStateInternal.getInstallSource().mIsOrphaned;
    }

    private boolean isCallerAllowedToSilentlyUninstall(@android.annotation.NonNull com.android.server.pm.Computer computer, int i, java.lang.String str, int i2) {
        if (com.android.server.pm.PackageManagerServiceUtils.isRootOrShell(i) || android.os.UserHandle.getAppId(i) == 1000) {
            return true;
        }
        int userId = android.os.UserHandle.getUserId(i);
        if (i == computer.getPackageUid(computer.getInstallerPackageName(str, i2), 0L, userId)) {
            return true;
        }
        for (java.lang.String str2 : this.mPm.mRequiredVerifierPackages) {
            if (i == computer.getPackageUid(str2, 0L, userId)) {
                return true;
            }
        }
        if (this.mPm.mRequiredUninstallerPackage == null || i != computer.getPackageUid(this.mPm.mRequiredUninstallerPackage, 0L, userId)) {
            return (this.mPm.mStorageManagerPackage != null && i == computer.getPackageUid(this.mPm.mStorageManagerPackage, 0L, userId)) || computer.checkUidPermission("android.permission.MANAGE_PROFILE_AND_DEVICE_OWNERS", i) == 0;
        }
        return true;
    }

    private int[] getBlockUninstallForUsers(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, int[] iArr) {
        int[] iArr2 = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        for (int i : iArr) {
            if (computer.getBlockUninstallForUser(str, i)) {
                iArr2 = com.android.internal.util.ArrayUtils.appendInt(iArr2, i);
            }
        }
        return iArr2;
    }

    private static class TempUserState {
        public final int enabledState;
        public final boolean installed;

        @android.annotation.Nullable
        public final java.lang.String lastDisableAppCaller;

        private TempUserState(int i, @android.annotation.Nullable java.lang.String str, boolean z) {
            this.enabledState = i;
            this.lastDisableAppCaller = str;
            this.installed = z;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    public void removeUnusedPackagesLPw(com.android.server.pm.UserManagerService userManagerService, final int i) {
        int[] userIds = userManagerService.getUserIds();
        int size = this.mPm.mSettings.getPackagesLocked().size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.pm.PackageSetting valueAt = this.mPm.mSettings.getPackagesLocked().valueAt(i2);
            if (valueAt.getPkg() != null) {
                final java.lang.String packageName = valueAt.getPkg().getPackageName();
                boolean z = true;
                if ((valueAt.getFlags() & 1) == 0 && android.text.TextUtils.isEmpty(valueAt.getPkg().getStaticSharedLibraryName()) && android.text.TextUtils.isEmpty(valueAt.getPkg().getSdkLibraryName())) {
                    boolean shouldKeepUninstalledPackageLPr = this.mPm.shouldKeepUninstalledPackageLPr(packageName);
                    if (!shouldKeepUninstalledPackageLPr) {
                        for (int i3 = 0; i3 < userIds.length; i3++) {
                            if (userIds[i3] != i && valueAt.getInstalled(userIds[i3])) {
                                break;
                            }
                        }
                    }
                    z = shouldKeepUninstalledPackageLPr;
                    if (!z) {
                        this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.pm.DeletePackageHelper.this.lambda$removeUnusedPackagesLPw$6(packageName, i);
                            }
                        });
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeUnusedPackagesLPw$6(java.lang.String str, int i) {
        deletePackageX(str, -1L, i, 0, true);
    }

    public void deleteExistingPackageAsUser(android.content.pm.VersionedPackage versionedPackage, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, int i) {
        int i2;
        this.mPm.mContext.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
        com.android.internal.util.Preconditions.checkNotNull(versionedPackage);
        com.android.internal.util.Preconditions.checkNotNull(iPackageDeleteObserver2);
        java.lang.String packageName = versionedPackage.getPackageName();
        long longVersionCode = versionedPackage.getLongVersionCode();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(this.mPm.snapshotComputer().resolveInternalPackageName(packageName, longVersionCode));
                if (packageLPr == null) {
                    i2 = 0;
                } else {
                    i2 = packageLPr.queryInstalledUsers(this.mUserManagerInternal.getUserIds(), true).length;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (i2 > 1) {
            deletePackageVersionedInternal(versionedPackage, iPackageDeleteObserver2, i, 0, true);
        } else {
            try {
                iPackageDeleteObserver2.onPackageDeleted(packageName, -1, (java.lang.String) null);
            } catch (android.os.RemoteException e) {
            }
        }
    }
}
