package com.android.server.pm;

/* loaded from: classes2.dex */
final class InstallPackageHelper {
    private final com.android.server.pm.ApexManager mApexManager;
    private final com.android.server.pm.AppDataHelper mAppDataHelper;
    private final com.android.server.pm.dex.ArtManagerService mArtManagerService;
    private final com.android.server.pm.BroadcastHelper mBroadcastHelper;
    private final android.content.Context mContext;
    private final com.android.server.pm.DeletePackageHelper mDeletePackageHelper;
    private final com.android.server.pm.dex.DexManager mDexManager;
    private final android.os.incremental.IncrementalManager mIncrementalManager;
    private final com.android.server.pm.PackageManagerServiceInjector mInjector;
    private final com.android.server.pm.PackageAbiHelper mPackageAbiHelper;
    private final com.android.server.pm.PackageDexOptimizer mPackageDexOptimizer;
    private final com.android.server.pm.PackageManagerService mPm;
    private final com.android.server.pm.RemovePackageHelper mRemovePackageHelper;
    private final com.android.server.pm.SharedLibrariesImpl mSharedLibraries;
    private final com.android.server.pm.UpdateOwnershipHelper mUpdateOwnershipHelper;

    InstallPackageHelper(com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.AppDataHelper appDataHelper, com.android.server.pm.RemovePackageHelper removePackageHelper, com.android.server.pm.DeletePackageHelper deletePackageHelper, com.android.server.pm.BroadcastHelper broadcastHelper) {
        this.mPm = packageManagerService;
        this.mInjector = packageManagerService.mInjector;
        this.mAppDataHelper = appDataHelper;
        this.mBroadcastHelper = broadcastHelper;
        this.mRemovePackageHelper = removePackageHelper;
        this.mDeletePackageHelper = deletePackageHelper;
        this.mIncrementalManager = packageManagerService.mInjector.getIncrementalManager();
        this.mApexManager = packageManagerService.mInjector.getApexManager();
        this.mDexManager = packageManagerService.mInjector.getDexManager();
        this.mArtManagerService = packageManagerService.mInjector.getArtManagerService();
        this.mContext = packageManagerService.mInjector.getContext();
        this.mPackageDexOptimizer = packageManagerService.mInjector.getPackageDexOptimizer();
        this.mPackageAbiHelper = packageManagerService.mInjector.getAbiHelper();
        this.mSharedLibraries = packageManagerService.mInjector.getSharedLibrariesImpl();
        this.mUpdateOwnershipHelper = packageManagerService.mInjector.getUpdateOwnershipHelper();
    }

    /* JADX WARN: Type inference failed for: r11v14, types: [boolean] */
    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    private com.android.server.pm.pkg.AndroidPackage commitReconciledScanResultLocked(@android.annotation.NonNull com.android.server.pm.ReconciledPackage reconciledPackage, int[] iArr) {
        final com.android.server.pm.PackageSetting packageSetting;
        java.lang.String str;
        com.android.server.pm.SharedUserSetting sharedUserSetting;
        int i;
        int userId;
        java.lang.String str2;
        com.android.server.pm.InstallRequest installRequest = reconciledPackage.mInstallRequest;
        com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage = installRequest.getParsedPackage();
        if (parsedPackage != null && com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(parsedPackage.getPackageName())) {
            parsedPackage.setVersionCode(this.mPm.getSdkVersion()).setVersionCodeMajor(0);
        }
        int scanFlags = installRequest.getScanFlags();
        com.android.server.pm.PackageSetting scanRequestOldPackageSetting = installRequest.getScanRequestOldPackageSetting();
        com.android.server.pm.PackageSetting scanRequestOriginalPackageSetting = installRequest.getScanRequestOriginalPackageSetting();
        java.lang.String realPackageName = installRequest.getRealPackageName();
        java.util.List<java.lang.String> changedAbiCodePath = com.android.server.pm.DexOptHelper.useArtService() ? null : installRequest.getChangedAbiCodePath();
        if (installRequest.getScanRequestPackageSetting() != null) {
            com.android.server.pm.SharedUserSetting sharedUserSettingLPr = this.mPm.mSettings.getSharedUserSettingLPr(installRequest.getScanRequestPackageSetting());
            com.android.server.pm.SharedUserSetting sharedUserSettingLPr2 = this.mPm.mSettings.getSharedUserSettingLPr(installRequest.getScannedPackageSetting());
            if (sharedUserSettingLPr != null && sharedUserSettingLPr != sharedUserSettingLPr2) {
                sharedUserSettingLPr.removePackage(installRequest.getScanRequestPackageSetting());
                if (this.mPm.mSettings.checkAndPruneSharedUserLPw(sharedUserSettingLPr, false)) {
                    installRequest.setRemovedAppId(sharedUserSettingLPr.mAppId);
                }
            }
        }
        if (installRequest.isExistingSettingCopied()) {
            packageSetting = installRequest.getScanRequestPackageSetting();
            packageSetting.updateFrom(installRequest.getScannedPackageSetting());
        } else {
            com.android.server.pm.PackageSetting scannedPackageSetting = installRequest.getScannedPackageSetting();
            if (scanRequestOriginalPackageSetting != null) {
                this.mPm.mSettings.addRenamedPackageLPw(com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRealPackageOrNull(parsedPackage, scannedPackageSetting.isSystem()), scanRequestOriginalPackageSetting.getPackageName());
                this.mPm.mTransferredPackages.add(scanRequestOriginalPackageSetting.getPackageName());
            } else {
                this.mPm.mSettings.removeRenamedPackageLPw(parsedPackage.getPackageName());
            }
            packageSetting = scannedPackageSetting;
        }
        com.android.server.pm.SharedUserSetting sharedUserSettingLPr3 = this.mPm.mSettings.getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr3 != null) {
            sharedUserSettingLPr3.addPackage(packageSetting);
            if (parsedPackage.isLeavingSharedUser() && com.android.server.pm.SharedUidMigration.applyStrategy(2) && sharedUserSettingLPr3.isSingleUser()) {
                this.mPm.mSettings.convertSharedUserSettingsLPw(sharedUserSettingLPr3);
            }
        }
        if (installRequest.isForceQueryableOverride()) {
            packageSetting.setForceQueryableOverride(true);
        }
        com.android.server.pm.InstallSource installSource = installRequest.getInstallSource();
        boolean z = (67108864 & scanFlags) != 0;
        boolean z2 = scanRequestOldPackageSetting != null;
        java.lang.String str3 = z2 ? scanRequestOldPackageSetting.getInstallSource().mUpdateOwnerPackageName : null;
        if (z || !packageSetting.isSystem()) {
            str = null;
        } else {
            str = this.mPm.mInjector.getSystemConfig().getSystemAppUpdateOwnerPackageName(parsedPackage.getPackageName());
        }
        java.util.List<java.lang.String> list = changedAbiCodePath;
        boolean isUpdateOwnershipDenylisted = this.mUpdateOwnershipHelper.isUpdateOwnershipDenylisted(parsedPackage.getPackageName());
        boolean z3 = str3 != null;
        if (installSource != null) {
            if (com.android.server.pm.PackageManagerServiceUtils.isInstalledByAdb(installSource.mInitiatingPackageName)) {
                sharedUserSetting = sharedUserSettingLPr3;
            } else {
                sharedUserSetting = sharedUserSettingLPr3;
                com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(installSource.mInitiatingPackageName);
                if (packageLPr != null) {
                    installSource = installSource.setInitiatingPackageSignatures(packageLPr.getSignatures());
                }
            }
            if (!z) {
                if (installSource.mInstallerPackageUid != -1) {
                    userId = android.os.UserHandle.getUserId(installSource.mInstallerPackageUid);
                } else {
                    userId = installRequest.getUserId();
                }
                boolean z4 = z2 && (userId < 0 ? scanRequestOldPackageSetting.getNotInstalledUserIds().length <= android.os.UserManager.isHeadlessSystemUserMode() : scanRequestOldPackageSetting.getInstalled(userId));
                boolean z5 = (installRequest.getInstallFlags() & 33554432) != 0;
                boolean equals = android.text.TextUtils.equals(str3, installSource.mInstallerPackageName);
                boolean isUpdateOwnershipDenyListProvider = this.mUpdateOwnershipHelper.isUpdateOwnershipDenyListProvider(installSource.mUpdateOwnerPackageName);
                if (!z4) {
                    if (!z5 || isUpdateOwnershipDenylisted) {
                        str2 = null;
                    } else if (isUpdateOwnershipDenyListProvider) {
                        str2 = null;
                    } else if ((!z3 && z2) || (z3 && !equals)) {
                        installSource = installSource.setUpdateOwnerPackageName(null);
                    }
                    installSource = installSource.setUpdateOwnerPackageName(str2);
                } else if (!equals || !z3) {
                    installSource = installSource.setUpdateOwnerPackageName(null);
                }
            }
            packageSetting.setInstallSource(installSource);
        } else {
            sharedUserSetting = sharedUserSettingLPr3;
            if (packageSetting.isSystem()) {
                boolean z6 = z3 && android.text.TextUtils.equals(str3, str);
                if (!z2 || z6) {
                    packageSetting.setUpdateOwnerPackage(str);
                } else {
                    packageSetting.setUpdateOwnerPackage(null);
                }
            }
        }
        if ((8388608 & scanFlags) == 0) {
            i = 1;
        } else {
            boolean z7 = (scanFlags & 33554432) != 0;
            i = 1;
            packageSetting.getPkgState().setApkInUpdatedApex(!z7);
        }
        packageSetting.getPkgState().setApexModuleName(installRequest.getApexModuleName());
        parsedPackage.setUid(packageSetting.getAppId());
        com.android.server.pm.pkg.AndroidPackage hideAsFinal = parsedPackage.hideAsFinal();
        this.mPm.mSettings.writeUserRestrictionsLPw(packageSetting, scanRequestOldPackageSetting);
        if (realPackageName != null) {
            this.mPm.mTransferredPackages.add(hideAsFinal.getPackageName());
        }
        if (reconciledPackage.mCollectedSharedLibraryInfos != null || (scanRequestOldPackageSetting != null && !scanRequestOldPackageSetting.getSharedLibraryDependencies().isEmpty())) {
            this.mSharedLibraries.executeSharedLibrariesUpdate(hideAsFinal, packageSetting, null, null, reconciledPackage.mCollectedSharedLibraryInfos, iArr);
        }
        com.android.server.pm.KeySetManagerService keySetManagerService = this.mPm.mSettings.getKeySetManagerService();
        if (reconciledPackage.mRemoveAppKeySetData) {
            keySetManagerService.removeAppKeySetDataLPw(hideAsFinal.getPackageName());
        }
        if (reconciledPackage.mSharedUserSignaturesChanged) {
            com.android.server.pm.SharedUserSetting sharedUserSetting2 = sharedUserSetting;
            sharedUserSetting2.signaturesChanged = java.lang.Boolean.TRUE;
            sharedUserSetting2.signatures.mSigningDetails = reconciledPackage.mSigningDetails;
        }
        packageSetting.setSigningDetails(reconciledPackage.mSigningDetails);
        if (list != null && list.size() > 0) {
            int size = list.size() - i;
            while (size >= 0) {
                java.util.List<java.lang.String> list2 = list;
                java.lang.String str4 = list2.get(size);
                try {
                    synchronized (this.mPm.mInstallLock) {
                        this.mPm.mInstaller.rmdex(str4, com.android.server.pm.InstructionSets.getDexCodeInstructionSet(com.android.server.pm.InstructionSets.getPreferredInstructionSet()));
                    }
                } catch (com.android.server.pm.Installer.InstallerException e) {
                } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e2) {
                    throw new java.lang.RuntimeException(e2);
                }
                size--;
                list = list2;
            }
        }
        int userId2 = installRequest.getUserId();
        commitPackageSettings(hideAsFinal, packageSetting, scanRequestOldPackageSetting, reconciledPackage);
        if (packageSetting.getInstantApp(userId2)) {
            this.mPm.mInstantAppRegistry.addInstantApp(userId2, packageSetting.getAppId());
        }
        if (!android.os.incremental.IncrementalManager.isIncrementalPath(packageSetting.getPathString())) {
            packageSetting.setLoadingProgress(1.0f);
        }
        if (com.android.server.pm.UpdateOwnershipHelper.hasValidOwnershipDenyList(packageSetting)) {
            this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.InstallPackageHelper.this.lambda$commitReconciledScanResultLocked$0(packageSetting);
                }
            });
        }
        return hideAsFinal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleUpdateOwnerDenyList, reason: merged with bridge method [inline-methods] */
    public void lambda$commitReconciledScanResultLocked$0(com.android.server.pm.PackageSetting packageSetting) {
        android.util.ArraySet<java.lang.String> readUpdateOwnerDenyList = this.mUpdateOwnershipHelper.readUpdateOwnerDenyList(packageSetting);
        if (readUpdateOwnerDenyList != null && !readUpdateOwnerDenyList.isEmpty()) {
            this.mUpdateOwnershipHelper.addToUpdateOwnerDenyList(packageSetting.getPackageName(), readUpdateOwnerDenyList);
            com.android.server.SystemConfig systemConfig = com.android.server.SystemConfig.getInstance();
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    java.util.Iterator<java.lang.String> it = readUpdateOwnerDenyList.iterator();
                    while (it.hasNext()) {
                        java.lang.String next = it.next();
                        com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(next);
                        if (packageLPr != null && systemConfig.getSystemAppUpdateOwnerPackageName(next) == null) {
                            packageLPr.setUpdateOwnerPackage(null);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
    }

    private void commitPackageSettings(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting2, com.android.server.pm.ReconciledPackage reconciledPackage) {
        java.lang.String packageName = androidPackage.getPackageName();
        com.android.server.pm.InstallRequest installRequest = reconciledPackage.mInstallRequest;
        com.android.server.pm.pkg.AndroidPackage scanRequestOldPackage = installRequest.getScanRequestOldPackage();
        int scanFlags = installRequest.getScanFlags();
        boolean z = (installRequest.getParseFlags() & Integer.MIN_VALUE) != 0;
        if (this.mPm.mCustomResolverComponentName != null && this.mPm.mCustomResolverComponentName.getPackageName().equals(androidPackage.getPackageName())) {
            this.mPm.setUpCustomResolverActivity(androidPackage, packageSetting);
        }
        if (androidPackage.getPackageName().equals(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME)) {
            this.mPm.setPlatformPackage(androidPackage, packageSetting);
        }
        boolean z2 = z;
        java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> commitSharedLibraryChanges = this.mSharedLibraries.commitSharedLibraryChanges(androidPackage, packageSetting, reconciledPackage.mAllowedSharedLibraryInfos, reconciledPackage.getCombinedAvailablePackages(), scanFlags);
        installRequest.setLibraryConsumers(commitSharedLibraryChanges);
        if ((scanFlags & 16) == 0 && (scanFlags & 1024) == 0 && (scanFlags & 2048) == 0) {
            this.mPm.snapshotComputer().checkPackageFrozen(packageName);
        }
        boolean isInstallReplace = installRequest.isInstallReplace();
        if (commitSharedLibraryChanges != null && (androidPackage.getStaticSharedLibraryName() == null || isInstallReplace)) {
            for (int i = 0; i < commitSharedLibraryChanges.size(); i++) {
                com.android.server.pm.pkg.AndroidPackage androidPackage2 = commitSharedLibraryChanges.get(i);
                this.mPm.killApplication(androidPackage2.getPackageName(), androidPackage2.getUid(), "update lib", 12);
            }
        }
        android.os.Trace.traceBegin(262144L, "updateSettings");
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPm.mSettings.insertPackageSettingLPw(packageSetting, androidPackage);
                this.mPm.mPackages.put(androidPackage.getPackageName(), androidPackage);
                if ((8388608 & scanFlags) != 0) {
                    this.mApexManager.registerApkInApex(androidPackage);
                }
                if ((this.mPm.isDeviceUpgrading() && packageSetting.isSystem()) || isInstallReplace) {
                    for (int i2 : this.mPm.mUserManager.getUserIds()) {
                        packageSetting.restoreComponentSettings(i2);
                    }
                }
                if ((67108864 & scanFlags) == 0) {
                    this.mPm.mSettings.getKeySetManagerService().addScannedPackageLPw(androidPackage);
                }
                com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
                this.mPm.mComponentResolver.addAllComponents(androidPackage, z2, this.mPm.mSetupWizardPackage, snapshotComputer);
                this.mPm.mAppsFilter.addPackage(snapshotComputer, packageSetting, isInstallReplace, (scanFlags & 1024) != 0);
                this.mPm.addAllPackageProperties(androidPackage);
                if (packageSetting2 == null || packageSetting2.getPkg() == null) {
                    this.mPm.mDomainVerificationManager.addPackage(packageSetting, installRequest.getPreVerifiedDomains());
                } else {
                    this.mPm.mDomainVerificationManager.migrateState(packageSetting2, packageSetting, installRequest.getPreVerifiedDomains());
                }
                int size = com.android.internal.util.ArrayUtils.size(androidPackage.getInstrumentations());
                java.lang.StringBuilder sb = null;
                for (int i3 = 0; i3 < size; i3++) {
                    com.android.internal.pm.pkg.component.ParsedInstrumentation parsedInstrumentation = (com.android.internal.pm.pkg.component.ParsedInstrumentation) androidPackage.getInstrumentations().get(i3);
                    com.android.internal.pm.pkg.component.ComponentMutateUtils.setPackageName(parsedInstrumentation, androidPackage.getPackageName());
                    this.mPm.addInstrumentation(parsedInstrumentation.getComponentName(), parsedInstrumentation);
                    if (z2) {
                        if (sb == null) {
                            sb = new java.lang.StringBuilder(256);
                        } else {
                            sb.append(' ');
                        }
                        sb.append(parsedInstrumentation.getName());
                    }
                }
                java.util.List protectedBroadcasts = androidPackage.getProtectedBroadcasts();
                if (!protectedBroadcasts.isEmpty()) {
                    synchronized (this.mPm.mProtectedBroadcasts) {
                        this.mPm.mProtectedBroadcasts.addAll(protectedBroadcasts);
                    }
                }
                this.mPm.mPermissionManager.onPackageAdded(packageSetting, (scanFlags & 8192) != 0, scanRequestOldPackage);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        android.os.Trace.traceEnd(262144L);
    }

    /* JADX WARN: Finally extract failed */
    public android.util.Pair<java.lang.Integer, android.content.IntentSender> installExistingPackageAsUser(@android.annotation.Nullable final java.lang.String str, final int i, int i2, int i3, @android.annotation.Nullable java.util.List<java.lang.String> list, @android.annotation.Nullable android.content.IntentSender intentSender) {
        long j;
        long j2;
        boolean z;
        boolean z2;
        final android.content.IntentSender intentSender2 = intentSender;
        int callingUid = android.os.Binder.getCallingUid();
        if (this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") != 0 && this.mContext.checkCallingOrSelfPermission("com.android.permission.INSTALL_EXISTING_PACKAGES") != 0) {
            throw new java.lang.SecurityException("Neither user " + callingUid + " nor current process has android.permission.INSTALL_PACKAGES.");
        }
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        snapshotComputer.enforceCrossUserPermission(callingUid, i, true, true, "installExistingPackage for user " + i);
        if (this.mPm.isUserRestricted(i, "no_install_apps")) {
            return android.util.Pair.create(-111, intentSender2);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        boolean z3 = (i2 & 2048) != 0;
        boolean z4 = (i2 & 16384) != 0;
        try {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            try {
                try {
                    synchronized (packageManagerTracedLock) {
                        try {
                            com.android.server.pm.Computer snapshotComputer2 = this.mPm.snapshotComputer();
                            com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
                            if (packageLPr == null) {
                                j2 = clearCallingIdentity;
                            } else {
                                if (packageLPr.getPkg() != null) {
                                    if (z3 && (packageLPr.isSystem() || packageLPr.isUpdatedSystemApp())) {
                                        android.util.Pair<java.lang.Integer, android.content.IntentSender> create = android.util.Pair.create(-3, intentSender2);
                                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        return create;
                                    }
                                    if (!snapshotComputer2.canViewInstantApps(callingUid, android.os.UserHandle.getUserId(callingUid))) {
                                        int[] userIds = this.mPm.mUserManager.getUserIds();
                                        int length = userIds.length;
                                        int i4 = 0;
                                        boolean z5 = false;
                                        while (i4 < length) {
                                            int i5 = length;
                                            z5 = !packageLPr.getInstantApp(userIds[i4]);
                                            if (z5) {
                                                break;
                                            }
                                            i4++;
                                            length = i5;
                                        }
                                        if (!z5) {
                                            android.util.Pair<java.lang.Integer, android.content.IntentSender> create2 = android.util.Pair.create(-3, intentSender2);
                                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                            return create2;
                                        }
                                    }
                                    if (packageLPr.getInstalled(i)) {
                                        j = clearCallingIdentity;
                                        z = z4 && packageLPr.getInstantApp(i);
                                    } else {
                                        packageLPr.setInstalled(true, i);
                                        packageLPr.setHidden(false, i);
                                        packageLPr.setInstallReason(i3, i);
                                        packageLPr.setUninstallReason(0, i);
                                        j = clearCallingIdentity;
                                        packageLPr.setFirstInstallTime(java.lang.System.currentTimeMillis(), i);
                                        this.mPm.mInstallerService.mPackageArchiver.clearArchiveState(packageLPr, i);
                                        this.mPm.mSettings.writePackageRestrictionsLPr(i);
                                        this.mPm.mSettings.writeKernelMappingLPr(packageLPr);
                                        z = true;
                                    }
                                    com.android.server.pm.ScanPackageUtils.setInstantAppForUser(this.mPm.mInjector, packageLPr, i, z3, z4);
                                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                    if (z) {
                                        java.lang.String str2 = packageLPr.getInstallSource().mUpdateOwnerPackageName;
                                        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) this.mInjector.getLocalService(android.app.admin.DevicePolicyManagerInternal.class);
                                        boolean z6 = devicePolicyManagerInternal != null && devicePolicyManagerInternal.isUserOrganizationManaged(i);
                                        if (!snapshotComputer.isCallerSameApp(str2, callingUid) && (!packageLPr.isSystem() || !z6)) {
                                            packageLPr.setUpdateOwnerPackage(null);
                                        }
                                        if (packageLPr.getPkg() != null) {
                                            com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams.Builder builder = new com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams.Builder();
                                            if ((4194304 & i2) != 0) {
                                                builder.setAllowlistedRestrictedPermissions(new java.util.ArrayList(packageLPr.getPkg().getRequestedPermissions()));
                                            }
                                            this.mPm.mPermissionManager.onPackageInstalled(packageLPr.getPkg(), -1, builder.build(), i);
                                            synchronized (this.mPm.mInstallLock) {
                                                z2 = false;
                                                this.mAppDataHelper.prepareAppDataPostCommitLIF(packageLPr, 0, new int[]{i});
                                            }
                                        } else {
                                            z2 = false;
                                        }
                                        this.mBroadcastHelper.sendPackageAddedForUser(this.mPm.snapshotComputer(), str, packageLPr, i, packageLPr.getPkg() == null ? true : z2, 0, this.mPm.mAppPredictionServicePackage);
                                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
                                        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                                        synchronized (packageManagerTracedLock2) {
                                            try {
                                                this.mPm.updateSequenceNumberLP(packageLPr, new int[]{i});
                                            } catch (java.lang.Throwable th) {
                                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                                throw th;
                                            }
                                        }
                                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                        restoreAndPostInstall(new com.android.server.pm.InstallRequest(i, 1, packageLPr.getPkg(), new int[]{i}, new java.lang.Runnable() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                com.android.server.pm.InstallPackageHelper.this.lambda$installExistingPackageAsUser$1(str, i, intentSender2);
                                            }
                                        }));
                                        intentSender2 = null;
                                    }
                                    android.os.Binder.restoreCallingIdentity(j);
                                    return android.util.Pair.create(1, intentSender2);
                                }
                                j2 = clearCallingIdentity;
                            }
                            android.util.Pair<java.lang.Integer, android.content.IntentSender> create3 = android.util.Pair.create(-3, intentSender2);
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            android.os.Binder.restoreCallingIdentity(j2);
                            return create3;
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    android.os.Binder.restoreCallingIdentity(j);
                    throw th;
                }
            } catch (java.lang.Throwable th4) {
                th = th4;
            }
        } catch (java.lang.Throwable th5) {
            th = th5;
            j = clearCallingIdentity;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$installExistingPackageAsUser$1(java.lang.String str, int i, android.content.IntentSender intentSender) {
        this.mPm.restorePermissionsAndUpdateRolesForNewUserInstall(str, i);
        if (intentSender != null) {
            onInstallComplete(1, this.mContext, intentSender);
        }
    }

    static void onInstallComplete(int i, android.content.Context context, android.content.IntentSender intentSender) {
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.content.pm.extra.STATUS", android.content.pm.PackageManager.installStatusToPublicStatus(i));
        try {
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(context, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (android.content.IntentSender.SendIntentException e) {
        }
    }

    public void restoreAndPostInstall(final com.android.server.pm.InstallRequest installRequest) {
        boolean z;
        int userId = installRequest.getUserId();
        boolean isUpdate = installRequest.isUpdate();
        com.android.server.pm.PackageSetting packageSetting = null;
        if (installRequest.getPkg() != null && !installRequest.isArchived()) {
            if (!isUpdate) {
                z = true;
            } else {
                java.lang.String packageName = installRequest.getPkg().getPackageName();
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        packageSetting = this.mPm.mSettings.getPackageLPr(packageName);
                        z = packageSetting != null && packageSetting.isPendingRestore();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        } else {
            z = false;
        }
        if (this.mPm.mNextInstallToken < 0) {
            this.mPm.mNextInstallToken = 1;
        }
        com.android.server.pm.PackageManagerService packageManagerService = this.mPm;
        int i = packageManagerService.mNextInstallToken;
        packageManagerService.mNextInstallToken = i + 1;
        this.mPm.mRunningInstalls.put(i, installRequest);
        boolean z2 = installRequest.getReturnCode() == 1;
        if (z2 && z) {
            installRequest.closeFreezer();
            z = performBackupManagerRestore(userId, i, installRequest);
        }
        if (z2 && !z && isUpdate) {
            z = performRollbackManagerRestore(userId, i, installRequest);
        }
        if (z2 && !installRequest.hasPostInstallRunnable()) {
            final boolean z3 = packageSetting != null && packageSetting.isPendingRestore();
            installRequest.setPostInstallRunnable(new java.lang.Runnable() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.InstallPackageHelper.this.lambda$restoreAndPostInstall$2(z3, installRequest);
                }
            });
        }
        if (z) {
            if (packageSetting != null) {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock2) {
                    try {
                        packageSetting.setPendingRestore(false);
                    } finally {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return;
            }
            return;
        }
        android.os.Trace.asyncTraceBegin(262144L, "postInstall", i);
        this.mPm.mHandler.sendMessage(this.mPm.mHandler.obtainMessage(9, i, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restoreAndPostInstall$2(boolean z, com.android.server.pm.InstallRequest installRequest) {
        int[] firstTimeBroadcastUserIds;
        if (z) {
            firstTimeBroadcastUserIds = installRequest.getUpdateBroadcastUserIds();
        } else {
            firstTimeBroadcastUserIds = installRequest.getFirstTimeBroadcastUserIds();
        }
        for (int i : firstTimeBroadcastUserIds) {
            this.mPm.restorePermissionsAndUpdateRolesForNewUserInstall(installRequest.getName(), i);
        }
    }

    private boolean performBackupManagerRestore(int i, int i2, com.android.server.pm.InstallRequest installRequest) {
        if (installRequest.getPkg() == null) {
            return false;
        }
        android.app.backup.IBackupManager iBackupManager = this.mInjector.getIBackupManager();
        if (iBackupManager == null) {
            android.util.Slog.e("PackageManager", "Backup Manager not found!");
            return false;
        }
        if (i == -1) {
            i = 0;
        }
        android.os.Trace.asyncTraceBegin(262144L, "restore", i2);
        try {
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Exception e2) {
            android.util.Slog.e("PackageManager", "Exception trying to enqueue restore", e2);
            return false;
        }
        if (iBackupManager.isUserReadyForBackup(i)) {
            iBackupManager.restoreAtInstallForUser(i, installRequest.getPkg().getPackageName(), i2);
            return true;
        }
        android.util.Slog.w("PackageManager", "User " + i + " is not ready. Restore at install didn't take place.");
        return false;
    }

    private boolean performRollbackManagerRestore(int i, int i2, com.android.server.pm.InstallRequest installRequest) {
        com.android.server.pm.PackageSetting packageLPr;
        int[] iArr;
        long j;
        int i3;
        if (installRequest.getPkg() == null) {
            return false;
        }
        java.lang.String packageName = installRequest.getPkg().getPackageName();
        int[] userIds = this.mPm.mUserManager.getUserIds();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                packageLPr = this.mPm.mSettings.getPackageLPr(packageName);
                if (packageLPr != null) {
                    i3 = packageLPr.getAppId();
                    j = packageLPr.getCeDataInode(i);
                    iArr = packageLPr.queryInstalledUsers(userIds, true);
                } else {
                    iArr = new int[0];
                    j = -1;
                    i3 = -1;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        int installFlags = installRequest.getInstallFlags();
        boolean z = ((262144 & installFlags) == 0 && (installFlags & 128) == 0) ? false : true;
        if (packageLPr == null || !z) {
            return false;
        }
        ((com.android.server.rollback.RollbackManagerInternal) this.mInjector.getLocalService(com.android.server.rollback.RollbackManagerInternal.class)).snapshotAndRestoreUserData(packageName, android.os.UserHandle.toUserHandles(iArr), i3, j, packageLPr.getSeInfo(), i2);
        return true;
    }

    void installPackagesTraced(java.util.List<com.android.server.pm.InstallRequest> list) {
        synchronized (this.mPm.mInstallLock) {
            try {
                try {
                    android.os.Trace.traceBegin(262144L, "installPackages");
                    installPackagesLI(list);
                } finally {
                    android.os.Trace.traceEnd(262144L);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:204:0x04a0  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x04f6  */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v49, types: [com.android.internal.pm.parsing.pkg.ParsedPackage, com.android.server.pm.pkg.AndroidPackage] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void installPackagesLI(java.util.List<com.android.server.pm.InstallRequest> list) {
        long j;
        boolean z;
        ?? r2;
        long j2;
        long j3;
        com.android.server.pm.InstallRequest installRequest;
        long j4;
        com.android.server.pm.InstallRequest installRequest2;
        android.util.ArraySet arraySet = new android.util.ArraySet(list.size());
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(list.size());
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap(list.size());
        com.android.server.criticalevents.CriticalEventLog.getInstance().logInstallPackagesStarted();
        long j5 = 262144;
        try {
            android.os.Trace.traceBegin(262144L, "installPackagesLI");
            for (com.android.server.pm.InstallRequest installRequest3 : list) {
                try {
                    try {
                        android.os.Trace.traceBegin(j5, "preparePackage");
                        installRequest3.onPrepareStarted();
                        preparePackageLI(installRequest3);
                        installRequest3.onPrepareFinished();
                        android.os.Trace.traceEnd(j5);
                        com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage = installRequest3.getParsedPackage();
                        if (parsedPackage == null) {
                            installRequest3.setError(-116, "Failed to obtain package to scan");
                            for (com.android.server.pm.InstallRequest installRequest4 : list) {
                                if (installRequest4.getParsedPackage() != null && ((java.lang.Boolean) arrayMap2.getOrDefault(installRequest4.getParsedPackage().getPackageName(), false)).booleanValue()) {
                                    cleanUpAppIdCreation(installRequest4);
                                }
                            }
                            for (com.android.server.pm.InstallRequest installRequest5 : list) {
                                installRequest5.closeFreezer();
                                if (installRequest5.getReturnCode() == 1) {
                                    installRequest5.setReturnCode(0);
                                }
                            }
                            android.os.Trace.traceEnd(j5);
                            return;
                        }
                        installRequest3.setReturnCode(1);
                        java.lang.String packageName = parsedPackage.getPackageName();
                        try {
                            installRequest3.onScanStarted();
                            installRequest2 = installRequest3;
                            j3 = j5;
                        } catch (com.android.server.pm.PackageManagerException e) {
                            e = e;
                            installRequest2 = installRequest3;
                            j3 = j5;
                        }
                        try {
                            try {
                                installRequest2.setScanResult(scanPackageTracedLI(installRequest3.getParsedPackage(), installRequest3.getParseFlags(), installRequest3.getScanFlags(), java.lang.System.currentTimeMillis(), installRequest3.getUser(), installRequest3.getAbiOverride()));
                                installRequest2.onScanFinished();
                                if (!arraySet.add(packageName)) {
                                    installRequest2.setError(-5, "Duplicate package " + packageName + " in multi-package install request.");
                                    for (com.android.server.pm.InstallRequest installRequest6 : list) {
                                        if (installRequest6.getParsedPackage() != null && ((java.lang.Boolean) arrayMap2.getOrDefault(installRequest6.getParsedPackage().getPackageName(), false)).booleanValue()) {
                                            cleanUpAppIdCreation(installRequest6);
                                        }
                                    }
                                    for (com.android.server.pm.InstallRequest installRequest7 : list) {
                                        installRequest7.closeFreezer();
                                        if (installRequest7.getReturnCode() == 1) {
                                            installRequest7.setReturnCode(0);
                                        }
                                    }
                                    android.os.Trace.traceEnd(j3);
                                    return;
                                }
                                r2 = parsedPackage;
                                if (!checkNoAppStorageIsConsistent(installRequest2.getScanRequestOldPackage(), r2)) {
                                    installRequest2.setError(-7, "Update attempted to change value of android.internal.PROPERTY_NO_APP_DATA_STORAGE");
                                    for (com.android.server.pm.InstallRequest installRequest8 : list) {
                                        if (installRequest8.getParsedPackage() != null && ((java.lang.Boolean) arrayMap2.getOrDefault(installRequest8.getParsedPackage().getPackageName(), false)).booleanValue()) {
                                            cleanUpAppIdCreation(installRequest8);
                                        }
                                    }
                                    for (com.android.server.pm.InstallRequest installRequest9 : list) {
                                        installRequest9.closeFreezer();
                                        if (installRequest9.getReturnCode() == 1) {
                                            installRequest9.setReturnCode(0);
                                        }
                                    }
                                    android.os.Trace.traceEnd(j3);
                                    return;
                                }
                                boolean z2 = (installRequest2.getScanFlags() & 67108864) != 0;
                                boolean isSdkLibrary = r2.isSdkLibrary();
                                if (z2 || (isSdkLibrary && android.content.pm.Flags.disallowSdkLibsToBeApps())) {
                                    installRequest2.getScannedPackageSetting().setAppId(-1);
                                } else {
                                    arrayMap2.put(packageName, java.lang.Boolean.valueOf(optimisticallyRegisterAppId(installRequest2)));
                                }
                                arrayMap.put(packageName, this.mPm.getSettingsVersionForPackage(r2));
                                j5 = j3;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                z = false;
                                j = j3;
                                if (z) {
                                    for (com.android.server.pm.InstallRequest installRequest10 : list) {
                                        if (installRequest10.getParsedPackage() != null && ((java.lang.Boolean) arrayMap2.getOrDefault(installRequest10.getParsedPackage().getPackageName(), false)).booleanValue()) {
                                            cleanUpAppIdCreation(installRequest10);
                                        }
                                    }
                                    for (com.android.server.pm.InstallRequest installRequest11 : list) {
                                        installRequest11.closeFreezer();
                                        if (installRequest11.getReturnCode() == 1) {
                                            installRequest11.setReturnCode(0);
                                        }
                                    }
                                } else {
                                    for (com.android.server.pm.InstallRequest installRequest12 : list) {
                                        if (installRequest12.getDataLoaderType() == 2 && installRequest12.getSignatureSchemeVersion() == 4) {
                                            java.lang.String baseApkPath = installRequest12.getPkg().getBaseApkPath();
                                            java.lang.String[] splitCodePaths = installRequest12.getPkg().getSplitCodePaths();
                                            android.net.Uri originUri = installRequest12.getOriginUri();
                                            com.android.server.pm.PackageManagerService packageManagerService = this.mPm;
                                            int i = packageManagerService.mPendingVerificationToken;
                                            packageManagerService.mPendingVerificationToken = i + 1;
                                            com.android.server.pm.VerificationUtils.broadcastPackageVerified(i, originUri, 1, com.android.server.pm.PackageManagerServiceUtils.buildVerificationRootHashString(baseApkPath, splitCodePaths), installRequest12.getDataLoaderType(), installRequest12.getUser(), this.mContext);
                                        }
                                    }
                                }
                                android.os.Trace.traceEnd(j);
                                throw th;
                            }
                        } catch (com.android.server.pm.PackageManagerException e2) {
                            e = e2;
                            installRequest2.setError("Scanning Failed.", e);
                            for (com.android.server.pm.InstallRequest installRequest13 : list) {
                                if (installRequest13.getParsedPackage() != null && ((java.lang.Boolean) arrayMap2.getOrDefault(installRequest13.getParsedPackage().getPackageName(), false)).booleanValue()) {
                                    cleanUpAppIdCreation(installRequest13);
                                }
                            }
                            for (com.android.server.pm.InstallRequest installRequest14 : list) {
                                installRequest14.closeFreezer();
                                if (installRequest14.getReturnCode() == 1) {
                                    installRequest14.setReturnCode(0);
                                }
                            }
                            android.os.Trace.traceEnd(j3);
                            return;
                        }
                    } catch (com.android.server.pm.PrepareFailure e3) {
                        installRequest = installRequest3;
                        j4 = j5;
                        try {
                            installRequest.setError(e3.error, e3.getMessage());
                            installRequest.setOriginPackage(e3.mConflictingPackage);
                            installRequest.setOriginPermission(e3.mConflictingPermission);
                            installRequest.onPrepareFinished();
                            android.os.Trace.traceEnd(j4);
                            for (com.android.server.pm.InstallRequest installRequest15 : list) {
                                if (installRequest15.getParsedPackage() != null && ((java.lang.Boolean) arrayMap2.getOrDefault(installRequest15.getParsedPackage().getPackageName(), false)).booleanValue()) {
                                    cleanUpAppIdCreation(installRequest15);
                                }
                            }
                            for (com.android.server.pm.InstallRequest installRequest16 : list) {
                                installRequest16.closeFreezer();
                                if (installRequest16.getReturnCode() == 1) {
                                    installRequest16.setReturnCode(0);
                                }
                            }
                            android.os.Trace.traceEnd(j4);
                            return;
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            installRequest.onPrepareFinished();
                            android.os.Trace.traceEnd(j4);
                            throw th;
                        }
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        installRequest = installRequest3;
                        j4 = j5;
                        installRequest.onPrepareFinished();
                        android.os.Trace.traceEnd(j4);
                        throw th;
                    }
                } catch (java.lang.Throwable th4) {
                    th = th4;
                    j3 = j5;
                }
            }
            long j6 = j5;
            try {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                try {
                    synchronized (packageManagerTracedLock) {
                        try {
                            android.os.Trace.traceBegin(j6, "reconcilePackages");
                        } catch (com.android.server.pm.ReconcileFailure e4) {
                            e = e4;
                            j2 = j6;
                        } catch (java.lang.Throwable th5) {
                            th = th5;
                            j2 = j6;
                        }
                        try {
                            java.util.List<com.android.server.pm.ReconciledPackage> reconcilePackages = com.android.server.pm.ReconcilePackageUtils.reconcilePackages(list, java.util.Collections.unmodifiableMap(this.mPm.mPackages), arrayMap, this.mSharedLibraries, this.mPm.mSettings.getKeySetManagerService(), this.mPm.mSettings, this.mPm.mInjector.getSystemConfig());
                            try {
                                android.os.Trace.traceEnd(j6);
                                if (android.content.pm.Flags.improveInstallFreeze()) {
                                    try {
                                        java.util.Iterator<com.android.server.pm.ReconciledPackage> it = reconcilePackages.iterator();
                                        while (it.hasNext()) {
                                            com.android.server.pm.InstallRequest installRequest17 = it.next().mInstallRequest;
                                            installRequest17.setFreezer(freezePackageForInstall(installRequest17.getParsedPackage().getPackageName(), -1, installRequest17.getInstallFlags(), "installPackageLI", 16, installRequest17));
                                        }
                                    } catch (java.lang.Throwable th6) {
                                        th = th6;
                                        z = false;
                                        j = j6;
                                        while (true) {
                                            try {
                                                try {
                                                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                                    throw th;
                                                } catch (java.lang.Throwable th7) {
                                                    th = th7;
                                                    if (z) {
                                                    }
                                                    android.os.Trace.traceEnd(j);
                                                    throw th;
                                                }
                                            } catch (java.lang.Throwable th8) {
                                                th = th8;
                                            }
                                        }
                                    }
                                }
                                j = j6;
                                try {
                                    android.os.Trace.traceBegin(j, "commitPackages");
                                    commitPackagesLocked(reconcilePackages, this.mPm.mUserManager.getUserIds());
                                    try {
                                        android.os.Trace.traceEnd(j);
                                        try {
                                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                            executePostCommitStepsLIF(reconcilePackages);
                                            for (com.android.server.pm.InstallRequest installRequest18 : list) {
                                                if (installRequest18.getDataLoaderType() == 2 && installRequest18.getSignatureSchemeVersion() == 4) {
                                                    java.lang.String baseApkPath2 = installRequest18.getPkg().getBaseApkPath();
                                                    java.lang.String[] splitCodePaths2 = installRequest18.getPkg().getSplitCodePaths();
                                                    android.net.Uri originUri2 = installRequest18.getOriginUri();
                                                    com.android.server.pm.PackageManagerService packageManagerService2 = this.mPm;
                                                    int i2 = packageManagerService2.mPendingVerificationToken;
                                                    packageManagerService2.mPendingVerificationToken = i2 + 1;
                                                    com.android.server.pm.VerificationUtils.broadcastPackageVerified(i2, originUri2, 1, com.android.server.pm.PackageManagerServiceUtils.buildVerificationRootHashString(baseApkPath2, splitCodePaths2), installRequest18.getDataLoaderType(), installRequest18.getUser(), this.mContext);
                                                }
                                            }
                                            android.os.Trace.traceEnd(j);
                                        } catch (java.lang.Throwable th9) {
                                            th = th9;
                                            z = true;
                                            if (z) {
                                            }
                                            android.os.Trace.traceEnd(j);
                                            throw th;
                                        }
                                    } catch (java.lang.Throwable th10) {
                                        th = th10;
                                        z = true;
                                        j = j;
                                        while (true) {
                                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                            throw th;
                                        }
                                    }
                                } catch (java.lang.Throwable th11) {
                                    android.os.Trace.traceEnd(j);
                                    throw th11;
                                }
                            } catch (java.lang.Throwable th12) {
                                th = th12;
                                r2 = j6;
                                z = false;
                                j = r2;
                                while (true) {
                                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                    throw th;
                                }
                            }
                        } catch (com.android.server.pm.ReconcileFailure e5) {
                            e = e5;
                            j2 = j6;
                            try {
                                java.util.Iterator<com.android.server.pm.InstallRequest> it2 = list.iterator();
                                while (it2.hasNext()) {
                                    it2.next().setError("Reconciliation failed...", e);
                                }
                                android.os.Trace.traceEnd(j2);
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                for (com.android.server.pm.InstallRequest installRequest19 : list) {
                                    if (installRequest19.getParsedPackage() != null && ((java.lang.Boolean) arrayMap2.getOrDefault(installRequest19.getParsedPackage().getPackageName(), false)).booleanValue()) {
                                        cleanUpAppIdCreation(installRequest19);
                                    }
                                }
                                for (com.android.server.pm.InstallRequest installRequest20 : list) {
                                    installRequest20.closeFreezer();
                                    if (installRequest20.getReturnCode() == 1) {
                                        installRequest20.setReturnCode(0);
                                    }
                                }
                                android.os.Trace.traceEnd(j2);
                            } catch (java.lang.Throwable th13) {
                                th = th13;
                                android.os.Trace.traceEnd(j2);
                                throw th;
                            }
                        } catch (java.lang.Throwable th14) {
                            th = th14;
                            j2 = j6;
                            android.os.Trace.traceEnd(j2);
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th15) {
                    th = th15;
                }
            } catch (java.lang.Throwable th16) {
                th = th16;
                j = j6;
                z = false;
                if (z) {
                }
                android.os.Trace.traceEnd(j);
                throw th;
            }
        } catch (java.lang.Throwable th17) {
            th = th17;
            j = j5;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private boolean checkNoAppStorageIsConsistent(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.pkg.AndroidPackage androidPackage2) {
        if (androidPackage == null) {
            return true;
        }
        android.content.pm.PackageManager.Property property = (android.content.pm.PackageManager.Property) androidPackage.getProperties().get("android.internal.PROPERTY_NO_APP_DATA_STORAGE");
        android.content.pm.PackageManager.Property property2 = (android.content.pm.PackageManager.Property) androidPackage2.getProperties().get("android.internal.PROPERTY_NO_APP_DATA_STORAGE");
        if (property == null || !property.getBoolean()) {
            if (property2 == null || !property2.getBoolean()) {
                return true;
            }
            return false;
        }
        if (property2 != null && property2.getBoolean()) {
            return true;
        }
        return false;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:450:0x0bc3
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0c7a  */
    /* JADX WARN: Removed duplicated region for block: B:280:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:623:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:628:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:629:0x00f8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x026c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v13 */
    /* JADX WARN: Type inference failed for: r11v15 */
    /* JADX WARN: Type inference failed for: r11v16 */
    /* JADX WARN: Type inference failed for: r16v11, types: [com.android.server.pm.PackageAbiHelper] */
    /* JADX WARN: Type inference failed for: r16v32, types: [boolean] */
    /* JADX WARN: Type inference failed for: r16v33, types: [com.android.server.pm.PackageSetting] */
    /* JADX WARN: Type inference failed for: r16v8 */
    /* JADX WARN: Type inference failed for: r1v112 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r3v96, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v23, types: [com.android.server.pm.PackageAbiHelper$Abis] */
    /* JADX WARN: Type inference failed for: r4v73, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v53 */
    /* JADX WARN: Type inference failed for: r7v54 */
    /* JADX WARN: Type inference failed for: r7v55 */
    /* JADX WARN: Type inference failed for: r7v64 */
    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void preparePackageLI(com.android.server.pm.InstallRequest r34) throws com.android.server.pm.PrepareFailure {
        /*
            Method dump skipped, instructions count: 3267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.InstallPackageHelper.preparePackageLI(com.android.server.pm.InstallRequest):void");
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private void doRenameLI(com.android.server.pm.InstallRequest installRequest, com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage) throws com.android.server.pm.PrepareFailure {
        int returnCode = installRequest.getReturnCode();
        java.lang.String returnMsg = installRequest.getReturnMsg();
        if (installRequest.isInstallMove()) {
            if (returnCode != 1) {
                this.mRemovePackageHelper.cleanUpForMoveInstall(installRequest.getMoveToUuid(), installRequest.getMovePackageName(), installRequest.getMoveFromCodePath());
                throw new com.android.server.pm.PrepareFailure(returnCode, returnMsg);
            }
            return;
        }
        if (returnCode != 1) {
            this.mRemovePackageHelper.removeCodePath(installRequest.getCodeFile());
            throw new com.android.server.pm.PrepareFailure(returnCode, returnMsg);
        }
        java.io.File resolveTargetDir = resolveTargetDir(installRequest.getInstallFlags(), installRequest.getCodeFile());
        java.io.File codeFile = installRequest.getCodeFile();
        java.io.File nextCodePath = com.android.server.pm.PackageManagerServiceUtils.getNextCodePath(resolveTargetDir, parsedPackage.getPackageName());
        boolean z = this.mPm.mIncrementalManager != null && android.os.incremental.IncrementalManager.isIncrementalPath(codeFile.getAbsolutePath());
        try {
            com.android.server.pm.PackageManagerServiceUtils.makeDirRecursive(nextCodePath.getParentFile(), 505);
            if (z) {
                this.mPm.mIncrementalManager.linkCodePath(codeFile, nextCodePath);
            } else {
                android.system.Os.rename(codeFile.getAbsolutePath(), nextCodePath.getAbsolutePath());
            }
            if (!z && !android.os.SELinux.restoreconRecursive(nextCodePath)) {
                android.util.Slog.w("PackageManager", "Failed to restorecon");
                throw new com.android.server.pm.PrepareFailure(-20, "Failed to restorecon");
            }
            installRequest.setCodeFile(nextCodePath);
            try {
                parsedPackage.setPath(nextCodePath.getCanonicalPath());
                parsedPackage.setBaseApkPath(android.os.FileUtils.rewriteAfterRename(codeFile, nextCodePath, parsedPackage.getBaseApkPath()));
                parsedPackage.setSplitCodePaths(android.os.FileUtils.rewriteAfterRename(codeFile, nextCodePath, parsedPackage.getSplitCodePaths()));
            } catch (java.io.IOException e) {
                android.util.Slog.e("PackageManager", "Failed to get path: " + nextCodePath, e);
                throw new com.android.server.pm.PrepareFailure(-20, "Failed to get path: " + nextCodePath);
            }
        } catch (android.system.ErrnoException | java.io.IOException e2) {
            android.util.Slog.w("PackageManager", "Failed to rename", e2);
            throw new com.android.server.pm.PrepareFailure(-4, "Failed to rename");
        }
    }

    private java.io.File resolveTargetDir(int i, java.io.File file) {
        if ((i & 2097152) != 0) {
            return android.os.Environment.getDataAppDirectory(null);
        }
        return file.getParentFile();
    }

    private static boolean cannotInstallWithBadPermissionGroups(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage) {
        return parsedPackage.getTargetSdkVersion() >= 31;
    }

    private boolean doesSignatureMatchForPermissions(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, int i) {
        com.android.server.pm.PackageSetting packageLPr;
        com.android.server.pm.KeySetManagerService keySetManagerService;
        com.android.server.pm.SharedUserSetting sharedUserSettingLPr;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                packageLPr = this.mPm.mSettings.getPackageLPr(str);
                keySetManagerService = this.mPm.mSettings.getKeySetManagerService();
                sharedUserSettingLPr = this.mPm.mSettings.getSharedUserSettingLPr(packageLPr);
            } finally {
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        android.content.pm.SigningDetails signingDetails = packageLPr == null ? android.content.pm.SigningDetails.UNKNOWN : packageLPr.getSigningDetails();
        if (str.equals(parsedPackage.getPackageName()) && keySetManagerService.shouldCheckUpgradeKeySetLocked(packageLPr, sharedUserSettingLPr, i)) {
            return keySetManagerService.checkUpgradeKeySetLocked(packageLPr, parsedPackage);
        }
        if (signingDetails.checkCapability(parsedPackage.getSigningDetails(), 4)) {
            return true;
        }
        if (parsedPackage.getSigningDetails().checkCapability(signingDetails, 4)) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock2) {
                try {
                    packageLPr.setSigningDetails(parsedPackage.getSigningDetails());
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            return true;
        }
        return false;
    }

    private void setUpFsVerity(com.android.server.pm.pkg.AndroidPackage androidPackage) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.PrepareFailure, java.io.IOException, java.security.DigestException, java.security.NoSuchAlgorithmException {
        if (!com.android.server.pm.PackageManagerServiceUtils.isApkVerityEnabled()) {
            return;
        }
        if (android.os.incremental.IncrementalManager.isIncrementalPath(androidPackage.getPath()) && android.os.incremental.IncrementalManager.getVersion() < 2) {
            return;
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        arrayMap.put(androidPackage.getBaseApkPath(), com.android.internal.security.VerityUtils.getFsveritySignatureFilePath(androidPackage.getBaseApkPath()));
        java.lang.String buildDexMetadataPathForApk = android.content.pm.dex.DexMetadataHelper.buildDexMetadataPathForApk(androidPackage.getBaseApkPath());
        if (new java.io.File(buildDexMetadataPathForApk).exists()) {
            arrayMap.put(buildDexMetadataPathForApk, com.android.internal.security.VerityUtils.getFsveritySignatureFilePath(buildDexMetadataPathForApk));
        }
        for (java.lang.String str : androidPackage.getSplitCodePaths()) {
            arrayMap.put(str, com.android.internal.security.VerityUtils.getFsveritySignatureFilePath(str));
            java.lang.String buildDexMetadataPathForApk2 = android.content.pm.dex.DexMetadataHelper.buildDexMetadataPathForApk(str);
            if (new java.io.File(buildDexMetadataPathForApk2).exists()) {
                arrayMap.put(buildDexMetadataPathForApk2, com.android.internal.security.VerityUtils.getFsveritySignatureFilePath(buildDexMetadataPathForApk2));
            }
        }
        com.android.server.security.FileIntegrityService service = com.android.server.security.FileIntegrityService.getService();
        for (java.util.Map.Entry entry : arrayMap.entrySet()) {
            try {
                java.lang.String str2 = (java.lang.String) entry.getKey();
                if (!com.android.internal.security.VerityUtils.hasFsverity(str2)) {
                    java.lang.String str3 = (java.lang.String) entry.getValue();
                    if (new java.io.File(str3).exists()) {
                        com.android.internal.security.VerityUtils.setUpFsverity(str2);
                        if (!service.verifyPkcs7DetachedSignature(str3, str2)) {
                            throw new com.android.server.pm.PrepareFailure(-118, "fs-verity signature does not verify against a known key");
                        }
                    } else {
                        continue;
                    }
                }
            } catch (java.io.IOException e) {
                throw new com.android.server.pm.PrepareFailure(-118, "Failed to enable fs-verity: " + e);
            }
        }
    }

    private com.android.server.pm.PackageFreezer freezePackageForInstall(java.lang.String str, int i, int i2, java.lang.String str2, int i3, com.android.server.pm.InstallRequest installRequest) {
        if ((i2 & 4096) != 0) {
            return new com.android.server.pm.PackageFreezer(this.mPm, installRequest);
        }
        return this.mPm.freezePackage(str, i, str2, i3, installRequest);
    }

    private static void updateDigest(java.security.MessageDigest messageDigest, java.io.File file) throws java.io.IOException {
        java.security.DigestInputStream digestInputStream = new java.security.DigestInputStream(new java.io.FileInputStream(file), messageDigest);
        do {
            try {
            } catch (java.lang.Throwable th) {
                try {
                    digestInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } while (digestInputStream.read() != -1);
        digestInputStream.close();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0168 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00be  */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v9 */
    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void commitPackagesLocked(java.util.List<com.android.server.pm.ReconciledPackage> list, @android.annotation.NonNull int[] iArr) {
        ?? r14;
        com.android.server.pm.PackageSetting packageLPr;
        int i;
        int i2;
        for (com.android.server.pm.ReconciledPackage reconciledPackage : list) {
            com.android.server.pm.InstallRequest installRequest = reconciledPackage.mInstallRequest;
            com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage = installRequest.getParsedPackage();
            java.lang.String packageName = parsedPackage.getPackageName();
            installRequest.onCommitStarted();
            if (!installRequest.isInstallReplace()) {
                r14 = 1;
            } else {
                com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPm.mPackages.get(packageName);
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(packageName);
                installRequest.setScannedPackageSettingFirstInstallTimeFromReplaced(packageStateInternal, iArr);
                installRequest.setScannedPackageSettingLastUpdateTime(java.lang.System.currentTimeMillis());
                installRequest.getRemovedInfo().mBroadcastAllowList = this.mPm.mAppsFilter.getVisibilityAllowList(this.mPm.snapshotComputer(), installRequest.getScannedPackageSetting(), iArr, this.mPm.mSettings.getPackagesLocked());
                if (installRequest.isInstallSystem()) {
                    this.mRemovePackageHelper.removePackage(androidPackage, true);
                    if (!disableSystemPackageLPw(androidPackage)) {
                        installRequest.getRemovedInfo().mArgs = new com.android.server.pm.CleanUpArgs(packageName, androidPackage.getPath(), com.android.server.pm.InstructionSets.getAppDexInstructionSets(packageStateInternal.getPrimaryCpuAbi(), packageStateInternal.getSecondaryCpuAbi()));
                        r14 = 1;
                    } else {
                        installRequest.getRemovedInfo().mArgs = null;
                        r14 = 1;
                    }
                } else {
                    try {
                        i = 1;
                        try {
                            this.mDeletePackageHelper.executeDeletePackage(reconciledPackage.mDeletePackageAction, packageName, true, iArr, false);
                            i2 = i;
                        } catch (com.android.server.pm.SystemDeleteException e) {
                            e = e;
                            i2 = i;
                            if (this.mPm.mIsEngBuild) {
                                throw new java.lang.RuntimeException("Unexpected failure", e);
                            }
                            r14 = i2;
                            if (installRequest.getReturnCode() == i2) {
                            }
                            updateSettingsLI(commitReconciledScanResultLocked(reconciledPackage, iArr), iArr, installRequest);
                            packageLPr = this.mPm.mSettings.getPackageLPr(packageName);
                            if (packageLPr != 0) {
                            }
                            if (installRequest.getReturnCode() != r14) {
                            }
                            installRequest.onCommitFinished();
                        }
                    } catch (com.android.server.pm.SystemDeleteException e2) {
                        e = e2;
                        i = 1;
                    }
                    r14 = i2;
                    if (installRequest.getReturnCode() == i2) {
                        com.android.server.pm.PackageSetting packageLPr2 = this.mPm.mSettings.getPackageLPr(parsedPackage.getPackageName());
                        r14 = i2;
                        if (packageLPr2 != null) {
                            installRequest.getRemovedInfo().mRemovedForAllUsers = this.mPm.mPackages.get(packageLPr2.getPackageName()) == null ? i2 == true ? 1 : 0 : false;
                            r14 = i2;
                        }
                    }
                }
            }
            updateSettingsLI(commitReconciledScanResultLocked(reconciledPackage, iArr), iArr, installRequest);
            packageLPr = this.mPm.mSettings.getPackageLPr(packageName);
            if (packageLPr != 0) {
                installRequest.setNewUsers(packageLPr.queryInstalledUsers(iArr, r14));
                packageLPr.setUpdateAvailable(false);
                java.io.File file = new java.io.File(packageLPr.getPath(), com.android.server.pm.PackageManagerService.APP_METADATA_FILE_NAME);
                if (file.exists()) {
                    packageLPr.setAppMetadataFilePath(file.getAbsolutePath());
                    if (android.content.pm.Flags.aslInApkAppMetadataSource()) {
                        packageLPr.setAppMetadataSource(2);
                    }
                } else if (android.content.pm.Flags.aslInApkAppMetadataSource() && parsedPackage.isAppMetadataFileInApk()) {
                    packageLPr.setAppMetadataFilePath(file.getAbsolutePath());
                    packageLPr.setAppMetadataSource(r14);
                } else {
                    packageLPr.setAppMetadataFilePath(null);
                }
            }
            if (installRequest.getReturnCode() != r14) {
                this.mPm.markPackageAsArchivedIfNeeded(packageLPr, installRequest.getArchivedPackage(), installRequest.getNewUsers());
                this.mPm.updateSequenceNumberLP(packageLPr, installRequest.getNewUsers());
                this.mPm.updateInstantAppInstallerLocked(packageName);
            }
            installRequest.onCommitFinished();
        }
        android.app.ApplicationPackageManager.invalidateGetPackagesForUidCache();
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    private boolean disableSystemPackageLPw(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return this.mPm.mSettings.disableSystemPackageLPw(androidPackage.getPackageName(), true);
    }

    private void updateSettingsLI(com.android.server.pm.pkg.AndroidPackage androidPackage, int[] iArr, com.android.server.pm.InstallRequest installRequest) {
        updateSettingsInternalLI(androidPackage, iArr, installRequest);
    }

    /* JADX WARN: Removed duplicated region for block: B:158:0x0161 A[Catch: all -> 0x0086, TryCatch #0 {all -> 0x0086, blocks: (B:13:0x005d, B:15:0x0067, B:18:0x0070, B:20:0x0076, B:22:0x007a, B:26:0x0083, B:27:0x0080, B:32:0x008d, B:34:0x0091, B:37:0x00a0, B:39:0x00a4, B:41:0x00ac, B:43:0x00ba, B:44:0x00c6, B:46:0x00cc, B:48:0x00d6, B:52:0x00fe, B:53:0x00e4, B:57:0x00f3, B:64:0x010d, B:66:0x0117, B:67:0x011b, B:68:0x0173, B:70:0x0189, B:72:0x0192, B:74:0x01a0, B:76:0x01c2, B:78:0x01c8, B:81:0x01d1, B:83:0x01de, B:87:0x01fc, B:89:0x0200, B:91:0x020c, B:93:0x0212, B:95:0x0215, B:99:0x0225, B:101:0x022f, B:103:0x0233, B:104:0x0243, B:106:0x0247, B:108:0x024f, B:110:0x0255, B:114:0x0258, B:118:0x0272, B:119:0x027f, B:121:0x0285, B:123:0x0295, B:124:0x02a3, B:128:0x02b2, B:130:0x02c2, B:131:0x02c5, B:132:0x02bc, B:134:0x0299, B:136:0x02a0, B:138:0x0218, B:140:0x0222, B:142:0x0127, B:144:0x012b, B:146:0x013e, B:152:0x0152, B:154:0x016e, B:156:0x0157, B:158:0x0161, B:159:0x0165, B:163:0x02db, B:164:0x02fd), top: B:12:0x005d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateSettingsInternalLI(com.android.server.pm.pkg.AndroidPackage androidPackage, int[] iArr, com.android.server.pm.InstallRequest installRequest) {
        boolean z;
        java.util.List<java.lang.String> allowlistedRestrictedPermissions;
        com.android.server.pm.PackageSetting packageLPr;
        android.os.Trace.traceBegin(262144L, "updateSettingsInternal");
        java.lang.String packageName = androidPackage.getPackageName();
        int[] originUsers = installRequest.getOriginUsers();
        int installReason = installRequest.getInstallReason();
        java.lang.String installerPackageName = installRequest.getInstallerPackageName();
        int userId = installRequest.getUserId();
        if (userId != -1 && userId != -2 && !this.mPm.mUserManager.exists(userId)) {
            installRequest.setError(com.android.server.pm.PackageManagerException.ofInternalError("User " + userId + " doesn't exist or has been removed", -38));
            return;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.PackageSetting packageLPr2 = this.mPm.mSettings.getPackageLPr(packageName);
                if (packageLPr2 != null) {
                    if (packageLPr2.isSystem()) {
                        if (originUsers != null && !installRequest.isApplicationEnabledSettingPersistent()) {
                            for (int i : originUsers) {
                                if (userId == -1 || userId == i) {
                                    packageLPr2.setEnabled(0, i, installerPackageName);
                                }
                            }
                        }
                        if (iArr != null && originUsers != null) {
                            for (int i2 : iArr) {
                                packageLPr2.setInstalled(com.android.internal.util.ArrayUtils.contains(originUsers, i2), i2);
                            }
                        }
                        if (iArr != null) {
                            for (int i3 : iArr) {
                                packageLPr2.resetOverrideComponentLabelIcon(i3);
                            }
                        }
                    }
                    if (!packageLPr2.getPkgState().getUsesLibraryInfos().isEmpty()) {
                        java.util.Iterator<com.android.server.pm.pkg.SharedLibraryWrapper> it = packageLPr2.getPkgState().getUsesLibraryInfos().iterator();
                        while (it.hasNext()) {
                            com.android.server.pm.pkg.SharedLibraryWrapper next = it.next();
                            int length = iArr.length;
                            int i4 = 0;
                            while (i4 < length) {
                                int i5 = iArr[i4];
                                java.util.Iterator<com.android.server.pm.pkg.SharedLibraryWrapper> it2 = it;
                                int i6 = length;
                                if (next.getType() == 1 && (packageLPr = this.mPm.mSettings.getPackageLPr(next.getPackageName())) != null) {
                                    packageLPr2.setOverlayPathsForLibrary(next.getName(), packageLPr.getOverlayPaths(i5), i5);
                                }
                                i4++;
                                it = it2;
                                length = i6;
                            }
                        }
                    }
                    if (userId != -1) {
                        packageLPr2.setInstalled(true, userId);
                        if (!installRequest.isApplicationEnabledSettingPersistent()) {
                            packageLPr2.setEnabled(0, userId, installerPackageName);
                        }
                        this.mPm.mInstallerService.mPackageArchiver.clearArchiveState(packageLPr2, userId);
                    } else if (iArr != null) {
                        int length2 = iArr.length;
                        int i7 = 0;
                        while (i7 < length2) {
                            int i8 = iArr[i7];
                            boolean contains = com.android.internal.util.ArrayUtils.contains(originUsers, i8);
                            int i9 = length2;
                            if (!this.mPm.isUserRestricted(i8, "no_install_apps") && !this.mPm.isUserRestricted(i8, "no_debugging_features")) {
                                z = false;
                                if (!contains || !z) {
                                    packageLPr2.setInstalled(true, i8);
                                    if (!installRequest.isApplicationEnabledSettingPersistent()) {
                                        packageLPr2.setEnabled(0, i8, installerPackageName);
                                    }
                                    this.mPm.mInstallerService.mPackageArchiver.clearArchiveState(packageLPr2, i8);
                                } else {
                                    packageLPr2.setInstalled(false, i8);
                                }
                                i7++;
                                length2 = i9;
                            }
                            z = true;
                            if (!contains) {
                            }
                            packageLPr2.setInstalled(true, i8);
                            if (!installRequest.isApplicationEnabledSettingPersistent()) {
                            }
                            this.mPm.mInstallerService.mPackageArchiver.clearArchiveState(packageLPr2, i8);
                            i7++;
                            length2 = i9;
                        }
                    }
                    this.mPm.mSettings.addInstallerPackageNames(packageLPr2.getInstallSource());
                    android.util.ArraySet arraySet = new android.util.ArraySet();
                    if (installRequest.getRemovedInfo() != null && installRequest.getRemovedInfo().mInstallReasons != null) {
                        int size = installRequest.getRemovedInfo().mInstallReasons.size();
                        for (int i10 = 0; i10 < size; i10++) {
                            int keyAt = installRequest.getRemovedInfo().mInstallReasons.keyAt(i10);
                            packageLPr2.setInstallReason(installRequest.getRemovedInfo().mInstallReasons.valueAt(i10), keyAt);
                            arraySet.add(java.lang.Integer.valueOf(keyAt));
                        }
                    }
                    if (installRequest.getRemovedInfo() != null && installRequest.getRemovedInfo().mUninstallReasons != null) {
                        for (int i11 = 0; i11 < installRequest.getRemovedInfo().mUninstallReasons.size(); i11++) {
                            packageLPr2.setUninstallReason(installRequest.getRemovedInfo().mUninstallReasons.valueAt(i11), installRequest.getRemovedInfo().mUninstallReasons.keyAt(i11));
                        }
                    }
                    if (userId == -1) {
                        for (int i12 : iArr) {
                            if (!arraySet.contains(java.lang.Integer.valueOf(i12)) && packageLPr2.getInstalled(i12)) {
                                packageLPr2.setInstallReason(installReason, i12);
                            }
                        }
                    } else if (!arraySet.contains(java.lang.Integer.valueOf(userId))) {
                        packageLPr2.setInstallReason(installReason, userId);
                    }
                    java.lang.String pathString = packageLPr2.getPathString();
                    if (android.os.incremental.IncrementalManager.isIncrementalPath(pathString) && this.mIncrementalManager != null) {
                        this.mIncrementalManager.registerLoadingProgressCallback(pathString, new com.android.server.pm.IncrementalProgressListener(packageLPr2.getPackageName(), this.mPm));
                    }
                    for (int i13 : iArr) {
                        if (packageLPr2.getInstalled(i13)) {
                            packageLPr2.setUninstallReason(0, i13);
                        }
                    }
                    this.mPm.mSettings.writeKernelMappingLPr(packageLPr2);
                    com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams.Builder builder = new com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams.Builder();
                    if ((installRequest.getInstallFlags() & 256) != 0) {
                        android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap = new android.util.ArrayMap<>();
                        java.util.Iterator it3 = androidPackage.getRequestedPermissions().iterator();
                        while (it3.hasNext()) {
                            arrayMap.put((java.lang.String) it3.next(), 1);
                        }
                        builder.setPermissionStates(arrayMap);
                    } else {
                        android.util.ArrayMap<java.lang.String, java.lang.Integer> permissionStates = installRequest.getPermissionStates();
                        if (permissionStates != null) {
                            builder.setPermissionStates(permissionStates);
                        }
                    }
                    if ((installRequest.getInstallFlags() & 4194304) != 0) {
                        allowlistedRestrictedPermissions = new java.util.ArrayList<>(androidPackage.getRequestedPermissions());
                    } else {
                        allowlistedRestrictedPermissions = installRequest.getAllowlistedRestrictedPermissions();
                    }
                    if (allowlistedRestrictedPermissions != null) {
                        builder.setAllowlistedRestrictedPermissions(allowlistedRestrictedPermissions);
                    }
                    builder.setAutoRevokePermissionsMode(installRequest.getAutoRevokePermissionsMode());
                    this.mPm.mPermissionManager.onPackageInstalled(androidPackage, installRequest.getPreviousAppId(), builder.build(), userId);
                }
                installRequest.setName(packageName);
                installRequest.setAppId(androidPackage.getUid());
                installRequest.setPkg(androidPackage);
                installRequest.setReturnCode(1);
                android.os.Trace.traceBegin(262144L, "writeSettings");
                this.mPm.writeSettingsLPrTEMP();
                android.os.Trace.traceEnd(262144L);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        android.os.Trace.traceEnd(262144L);
    }

    private void enableRestrictedSettings(java.lang.String str, int i, int i2) {
        ((android.app.AppOpsManager) this.mPm.mContext.getSystemService(android.app.AppOpsManager.class)).setMode(119, android.os.UserHandle.getUid(i2, i), str, 2);
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private void executePostCommitStepsLIF(java.util.List<com.android.server.pm.ReconciledPackage> list) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<com.android.server.pm.ReconciledPackage> it = list.iterator();
        while (it.hasNext()) {
            com.android.server.pm.InstallRequest installRequest = it.next().mInstallRequest;
            com.android.server.pm.PackageSetting scannedPackageSetting = installRequest.getScannedPackageSetting();
            java.lang.String packageName = scannedPackageSetting.getPackageName();
            java.lang.String pathString = scannedPackageSetting.getPathString();
            com.android.server.pm.pkg.AndroidPackage pkg = scannedPackageSetting.getPkg();
            if (this.mIncrementalManager != null && android.os.incremental.IncrementalManager.isIncrementalPath(pathString)) {
                android.os.incremental.IncrementalStorage openStorage = this.mIncrementalManager.openStorage(pathString);
                if (openStorage == null) {
                    throw new java.lang.IllegalArgumentException("Install: null storage for incremental package " + packageName);
                }
                arraySet.add(openStorage);
            }
            this.mAppDataHelper.prepareAppDataPostCommitLIF(scannedPackageSetting, 0, installRequest.getNewUsers());
            if (installRequest.isClearCodeCache()) {
                this.mAppDataHelper.clearAppDataLIF(scannedPackageSetting.getPkg(), -1, 39);
            }
            if (installRequest.isInstallReplace() && pkg != null) {
                this.mDexManager.notifyPackageUpdated(packageName, pkg.getBaseApkPath(), pkg.getSplitCodePaths());
            }
            if (!com.android.server.pm.DexOptHelper.useArtService() && pkg != null) {
                try {
                    this.mArtManagerService.prepareAppProfiles(pkg, this.mPm.resolveUserIds(installRequest.getUserId()), true);
                } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }
            com.android.server.pm.dex.DexoptOptions dexoptOptionsByInstallRequest = com.android.server.pm.DexOptHelper.getDexoptOptionsByInstallRequest(installRequest, this.mDexManager);
            if (com.android.server.pm.DexOptHelper.shouldPerformDexopt(installRequest, dexoptOptionsByInstallRequest, this.mContext)) {
                android.os.Trace.traceBegin(262144L, "dexopt");
                com.android.server.pm.PackageSetting realPackageSetting = installRequest.getRealPackageSetting();
                realPackageSetting.getPkgState().setUpdatedSystemApp(installRequest.getScannedPackageSetting().isUpdatedSystemApp());
                if (com.android.server.pm.DexOptHelper.useArtService()) {
                    installRequest.onDexoptFinished(com.android.server.pm.DexOptHelper.dexoptPackageUsingArtService(installRequest, dexoptOptionsByInstallRequest));
                } else {
                    try {
                        this.mPackageDexOptimizer.performDexOpt(pkg, realPackageSetting, null, this.mPm.getOrCreateCompilerPackageStats(pkg), this.mDexManager.getPackageUseInfoOrDefault(packageName), dexoptOptionsByInstallRequest);
                    } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e2) {
                        throw new java.lang.RuntimeException(e2);
                    }
                }
                android.os.Trace.traceEnd(262144L);
            }
            if (!com.android.server.pm.DexOptHelper.useArtService()) {
                try {
                    com.android.server.pm.BackgroundDexOptService.getService().notifyPackageChanged(packageName);
                } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e3) {
                    throw new java.lang.RuntimeException(e3);
                }
            }
        }
        com.android.server.pm.PackageManagerServiceUtils.waitForNativeBinariesExtractionForIncremental(arraySet);
    }

    android.util.Pair<java.lang.Integer, java.lang.String> verifyReplacingVersionCode(android.content.pm.PackageInfoLite packageInfoLite, long j, int i) {
        if ((131072 & i) != 0) {
            return verifyReplacingVersionCodeForApex(packageInfoLite, j, i);
        }
        java.lang.String str = packageInfoLite.packageName;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.internal.pm.parsing.pkg.AndroidPackageInternal androidPackageInternal = (com.android.server.pm.pkg.AndroidPackage) this.mPm.mPackages.get(str);
                com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
                if (androidPackageInternal == null && packageLPr != null) {
                    androidPackageInternal = packageLPr.getPkg();
                }
                if (j != -1) {
                    if (androidPackageInternal == null) {
                        java.lang.String str2 = "Required installed version code was " + j + " but package is not installed";
                        android.util.Slog.w("PackageManager", str2);
                        android.util.Pair<java.lang.Integer, java.lang.String> create = android.util.Pair.create(-121, str2);
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        return create;
                    }
                    if (androidPackageInternal.getLongVersionCode() != j) {
                        java.lang.String str3 = "Required installed version code was " + j + " but actual installed version is " + androidPackageInternal.getLongVersionCode();
                        android.util.Slog.w("PackageManager", str3);
                        android.util.Pair<java.lang.Integer, java.lang.String> create2 = android.util.Pair.create(-121, str3);
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        return create2;
                    }
                }
                if (androidPackageInternal != null && !androidPackageInternal.isSdkLibrary()) {
                    if (!com.android.server.pm.PackageManagerServiceUtils.isDowngradePermitted(i, androidPackageInternal.isDebuggable())) {
                        try {
                            com.android.server.pm.PackageManagerServiceUtils.checkDowngrade(androidPackageInternal, packageInfoLite);
                        } catch (com.android.server.pm.PackageManagerException e) {
                            java.lang.String str4 = "Downgrade detected: " + e.getMessage();
                            android.util.Slog.w("PackageManager", str4);
                            android.util.Pair<java.lang.Integer, java.lang.String> create3 = android.util.Pair.create(-25, str4);
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            return create3;
                        }
                    } else if (packageLPr.isSystem()) {
                        com.android.server.pm.PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(packageLPr);
                        if (disabledSystemPkgLPr != null) {
                            androidPackageInternal = disabledSystemPkgLPr.getPkg();
                        }
                        if (!android.os.Build.IS_DEBUGGABLE && !androidPackageInternal.isDebuggable()) {
                            try {
                                com.android.server.pm.PackageManagerServiceUtils.checkDowngrade(androidPackageInternal, packageInfoLite);
                            } catch (com.android.server.pm.PackageManagerException e2) {
                                java.lang.String str5 = "System app: " + str + " cannot be downgraded to older than its preloaded version on the system image. " + e2.getMessage();
                                android.util.Slog.w("PackageManager", str5);
                                android.util.Pair<java.lang.Integer, java.lang.String> create4 = android.util.Pair.create(-25, str5);
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                return create4;
                            }
                        }
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return android.util.Pair.create(1, null);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    private android.util.Pair<java.lang.Integer, java.lang.String> verifyReplacingVersionCodeForApex(android.content.pm.PackageInfoLite packageInfoLite, long j, int i) {
        java.lang.String str = packageInfoLite.packageName;
        android.content.pm.PackageInfo packageInfo = this.mPm.snapshotComputer().getPackageInfo(str, 1073741824L, 0);
        if (packageInfo == null) {
            java.lang.String str2 = "Attempting to install new APEX package " + str;
            android.util.Slog.w("PackageManager", str2);
            return android.util.Pair.create(-23, str2);
        }
        long longVersionCode = packageInfo.getLongVersionCode();
        if (j != -1 && longVersionCode != j) {
            java.lang.String str3 = "Installed version of APEX package " + str + " does not match required. Active version: " + longVersionCode + " required: " + j;
            android.util.Slog.w("PackageManager", str3);
            return android.util.Pair.create(-121, str3);
        }
        boolean z = (packageInfo.applicationInfo.flags & 2) != 0;
        long longVersionCode2 = packageInfoLite.getLongVersionCode();
        if (!com.android.server.pm.PackageManagerServiceUtils.isDowngradePermitted(i, z) && longVersionCode2 < longVersionCode) {
            java.lang.String str4 = "Downgrade of APEX package " + str + " is not allowed. Active version: " + longVersionCode + " attempted: " + longVersionCode2;
            android.util.Slog.w("PackageManager", str4);
            return android.util.Pair.create(-25, str4);
        }
        return android.util.Pair.create(1, null);
    }

    int getUidForVerifier(android.content.pm.VerifierInfo verifierInfo) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPm.mPackages.get(verifierInfo.packageName);
                if (androidPackage == null) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return -1;
                }
                if (androidPackage.getSigningDetails().getSignatures().length != 1) {
                    android.util.Slog.i("PackageManager", "Verifier package " + verifierInfo.packageName + " has more than one signature; ignoring");
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return -1;
                }
                try {
                    if (!java.util.Arrays.equals(verifierInfo.publicKey.getEncoded(), androidPackage.getSigningDetails().getSignatures()[0].getPublicKey().getEncoded())) {
                        android.util.Slog.i("PackageManager", "Verifier package " + verifierInfo.packageName + " does not have the expected public key; ignoring");
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        return -1;
                    }
                    int uid = androidPackage.getUid();
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return uid;
                } catch (java.security.cert.CertificateException e) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return -1;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    public void sendPendingBroadcasts() {
        int i;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                android.util.SparseArray<android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>>> copiedMap = this.mPm.mPendingBroadcasts.copiedMap();
                int size = copiedMap.size();
                int i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    i2 += copiedMap.valueAt(i3).size();
                }
                if (i2 == 0) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                java.lang.String[] strArr = new java.lang.String[i2];
                java.util.ArrayList<java.lang.String>[] arrayListArr = new java.util.ArrayList[i2];
                int[] iArr = new int[i2];
                int i4 = 0;
                for (int i5 = 0; i5 < size; i5++) {
                    int keyAt = copiedMap.keyAt(i5);
                    android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>> valueAt = copiedMap.valueAt(i5);
                    int size2 = com.android.internal.util.CollectionUtils.size(valueAt);
                    for (int i6 = 0; i6 < size2; i6++) {
                        strArr[i4] = valueAt.keyAt(i6);
                        arrayListArr[i4] = valueAt.valueAt(i6);
                        com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(strArr[i4]);
                        if (packageLPr != null) {
                            i = android.os.UserHandle.getUid(keyAt, packageLPr.getAppId());
                        } else {
                            i = -1;
                        }
                        iArr[i4] = i;
                        i4++;
                    }
                }
                this.mPm.mPendingBroadcasts.clear();
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
                for (int i7 = 0; i7 < i4; i7++) {
                    this.mBroadcastHelper.sendPackageChangedBroadcast(snapshotComputer, strArr[i7], true, arrayListArr[i7], iArr[i7], null);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    void handlePackagePostInstall(com.android.server.pm.InstallRequest installRequest, boolean z) {
        com.android.server.pm.CleanUpArgs cleanUpArgs;
        boolean z2 = (installRequest.getInstallFlags() & 4096) == 0;
        boolean z3 = installRequest.getReturnCode() == 1;
        boolean isUpdate = installRequest.isUpdate();
        boolean isArchived = installRequest.isArchived();
        final java.lang.String name = installRequest.getName();
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = z3 ? snapshotComputer.getPackageStateInternal(name) : null;
        boolean z4 = packageStateInternal == null || (packageStateInternal.isSystem() && !packageStateInternal.getPath().getPath().equals(installRequest.getPkg().getPath()));
        if (z3 && z4) {
            android.util.Slog.e("PackageManager", name + " was removed before handlePackagePostInstall could be executed");
            installRequest.setReturnCode(-23);
            installRequest.setReturnMessage("Package was removed before install could complete.");
            this.mRemovePackageHelper.cleanUpResources(name, installRequest.getOldCodeFile(), installRequest.getOldInstructionSet());
            this.mPm.notifyInstallObserver(installRequest);
            return;
        }
        if (z3) {
            this.mPm.mPerUidReadTimeoutsCache = null;
            this.mPm.notifyInstantAppPackageInstalled(installRequest.getPkg().getPackageName(), installRequest.getNewUsers());
            final int[] firstTimeBroadcastUserIds = installRequest.getFirstTimeBroadcastUserIds();
            if (installRequest.getPkg().getStaticSharedLibraryName() == null) {
                this.mPm.mProcessLoggingHandler.invalidateBaseApkHash(installRequest.getPkg().getBaseApkPath());
            }
            this.mBroadcastHelper.sendPostInstallBroadcasts(this.mPm.snapshotComputer(), installRequest, name, this.mPm.mRequiredPermissionControllerPackage, this.mPm.mRequiredVerifierPackages, this.mPm.mRequiredInstallerPackage, this.mPm, z, z2, isUpdate, isArchived);
            if (installRequest.isAllNewUsers() && !isUpdate) {
                this.mPm.notifyPackageAdded(name, installRequest.getAppId());
            } else {
                this.mPm.notifyPackageChanged(name, installRequest.getAppId());
            }
            if (installRequest.getPackageSource() == 3 || installRequest.getPackageSource() == 4) {
                final int appId = installRequest.getAppId();
                this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.InstallPackageHelper.this.lambda$handlePackagePostInstall$3(firstTimeBroadcastUserIds, name, appId);
                    }
                });
            }
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.UNKNOWN_SOURCES_ENABLED, getUnknownSourcesSettings());
            if (installRequest.getRemovedInfo() == null) {
                cleanUpArgs = null;
            } else {
                cleanUpArgs = installRequest.getRemovedInfo().mArgs;
            }
            if (cleanUpArgs != null) {
                if (!z2) {
                    this.mPm.scheduleDeferredNoKillPostDelete(cleanUpArgs);
                    if (android.content.pm.Flags.improveInstallDontKill()) {
                        synchronized (this.mPm.mInstallLock) {
                            com.android.server.pm.PackageManagerServiceUtils.linkFilesToOldDirs(this.mPm.mInstaller, name, packageStateInternal.getPath(), packageStateInternal.getOldPaths());
                        }
                    }
                } else {
                    this.mRemovePackageHelper.cleanUpResources(name, cleanUpArgs.getCodeFile(), cleanUpArgs.getInstructionSets());
                }
            } else {
                dalvik.system.VMRuntime.getRuntime().requestConcurrentGC();
            }
            if (isArchived) {
                com.android.server.pm.PackageRemovedInfo packageRemovedInfo = new com.android.server.pm.PackageRemovedInfo();
                packageRemovedInfo.mRemovedPackage = name;
                packageRemovedInfo.mInstallerPackageName = installRequest.getInstallerPackageName();
                packageRemovedInfo.mRemovedUsers = firstTimeBroadcastUserIds;
                packageRemovedInfo.mBroadcastUsers = firstTimeBroadcastUserIds;
                packageRemovedInfo.mUid = installRequest.getAppId();
                packageRemovedInfo.mIsAppIdRemoved = true;
                packageRemovedInfo.mRemovedPackageVersionCode = installRequest.getPkg().getLongVersionCode();
                packageRemovedInfo.mRemovedForAllUsers = true;
                this.mBroadcastHelper.sendPackageRemovedBroadcasts(packageRemovedInfo, this.mPm, false, false, true);
            } else {
                for (int i : firstTimeBroadcastUserIds) {
                    android.content.pm.PackageInfo packageInfo = snapshotComputer.getPackageInfo(name, 0L, i);
                    if (packageInfo != null) {
                        this.mDexManager.notifyPackageInstalled(packageInfo, i);
                    }
                }
            }
        }
        if (z3 && isUpdate) {
            if (z2) {
                this.mPm.scheduleDeferredPendingKillInstallObserver(installRequest);
            } else {
                this.mPm.scheduleDeferredNoKillInstallObserver(installRequest);
            }
        } else {
            this.mPm.notifyInstallObserver(installRequest);
        }
        this.mPm.schedulePruneUnusedStaticSharedLibraries(true);
        if (installRequest.getTraceMethod() != null) {
            android.os.Trace.asyncTraceEnd(262144L, installRequest.getTraceMethod(), installRequest.getTraceCookie());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handlePackagePostInstall$3(int[] iArr, java.lang.String str, int i) {
        for (int i2 : iArr) {
            enableRestrictedSettings(str, i, i2);
        }
    }

    private int getUnknownSourcesSettings() {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "install_non_market_apps", -1, 0);
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock", "mPm.mInstallLock"})
    void installSystemStubPackages(@android.annotation.NonNull java.util.List<java.lang.String> list, int i) {
        int size = list.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            java.lang.String str = list.get(size);
            if (this.mPm.mSettings.isDisabledSystemPackageLPr(str)) {
                list.remove(size);
            } else {
                com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPm.mPackages.get(str);
                if (androidPackage == null) {
                    list.remove(size);
                } else {
                    com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
                    if (packageLPr != null && packageLPr.getEnabled(0) == 3) {
                        list.remove(size);
                    } else {
                        try {
                            installStubPackageLI(androidPackage, 0, i);
                            packageLPr.setEnabled(0, 0, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                            list.remove(size);
                        } catch (com.android.server.pm.PackageManagerException e) {
                            android.util.Slog.e("PackageManager", "Failed to parse uncompressed system package: " + e.getMessage());
                        }
                    }
                }
            }
        }
        for (int size2 = list.size() - 1; size2 >= 0; size2 += -1) {
            java.lang.String str2 = list.get(size2);
            this.mPm.mSettings.getPackageLPr(str2).setEnabled(2, 0, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(6, "Stub disabled; pkg: " + str2);
        }
    }

    boolean enableCompressedPackage(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting) {
        com.android.server.pm.PackageFreezer freezePackage;
        int defParseFlags = this.mPm.getDefParseFlags() | Integer.MIN_VALUE | 64;
        synchronized (this.mPm.mInstallLock) {
            try {
                freezePackage = this.mPm.freezePackage(androidPackage.getPackageName(), -1, "setEnabledSetting", 16, null);
                try {
                    com.android.server.pm.pkg.AndroidPackage installStubPackageLI = installStubPackageLI(androidPackage, defParseFlags, 0);
                    this.mAppDataHelper.prepareAppDataAfterInstallLIF(installStubPackageLI);
                    com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
                    com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                    synchronized (packageManagerTracedLock) {
                        try {
                            try {
                                this.mSharedLibraries.updateSharedLibraries(installStubPackageLI, packageSetting, null, null, java.util.Collections.unmodifiableMap(this.mPm.mPackages));
                            } catch (com.android.server.pm.PackageManagerException e) {
                                android.util.Slog.w("PackageManager", "updateAllSharedLibrariesLPw failed: ", e);
                            }
                            this.mPm.mPermissionManager.onPackageInstalled(installStubPackageLI, -1, com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams.DEFAULT, -1);
                            this.mPm.writeSettingsLPrTEMP();
                        } finally {
                        }
                    }
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    if (freezePackage != null) {
                        freezePackage.close();
                    }
                    this.mAppDataHelper.clearAppDataLIF(installStubPackageLI, -1, 39);
                    this.mDexManager.notifyPackageUpdated(installStubPackageLI.getPackageName(), installStubPackageLI.getBaseApkPath(), installStubPackageLI.getSplitCodePaths());
                } finally {
                }
            } catch (com.android.server.pm.PackageManagerException e2) {
                try {
                    try {
                        freezePackage = this.mPm.freezePackage(androidPackage.getPackageName(), -1, "setEnabledSetting", 16, null);
                    } catch (com.android.server.pm.PackageManagerException e3) {
                        android.util.Slog.wtf("PackageManager", "Failed to restore system package:" + androidPackage.getPackageName(), e3);
                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
                        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                        synchronized (packageManagerTracedLock2) {
                            try {
                                com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                                if (packageLPr != null) {
                                    packageLPr.setEnabled(2, 0, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                                }
                                this.mPm.writeSettingsLPrTEMP();
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                return false;
                            } finally {
                            }
                        }
                    }
                    try {
                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mPm.mLock;
                        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                        synchronized (packageManagerTracedLock3) {
                            try {
                                this.mPm.mSettings.enableSystemPackageLPw(androidPackage.getPackageName());
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                installPackageFromSystemLIF(androidPackage.getPath(), this.mPm.mUserManager.getUserIds(), null, true);
                                if (freezePackage != null) {
                                    freezePackage.close();
                                }
                                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock4 = this.mPm.mLock;
                                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                                synchronized (packageManagerTracedLock4) {
                                    try {
                                        com.android.server.pm.PackageSetting packageLPr2 = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                                        if (packageLPr2 != null) {
                                            packageLPr2.setEnabled(2, 0, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                                        }
                                        this.mPm.writeSettingsLPrTEMP();
                                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                        return false;
                                    } catch (java.lang.Throwable th) {
                                        throw th;
                                    }
                                }
                            } finally {
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            }
                        }
                    } finally {
                    }
                } catch (java.lang.Throwable th2) {
                    com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock5 = this.mPm.mLock;
                    com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                    synchronized (packageManagerTracedLock5) {
                        try {
                            com.android.server.pm.PackageSetting packageLPr3 = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                            if (packageLPr3 != null) {
                                packageLPr3.setEnabled(2, 0, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                            }
                            this.mPm.writeSettingsLPrTEMP();
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            throw th2;
                        } finally {
                        }
                    }
                }
            }
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private com.android.server.pm.pkg.AndroidPackage installStubPackageLI(com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2) throws com.android.server.pm.PackageManagerException {
        if (com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION) {
            android.util.Slog.i("PackageManager", "Uncompressing system stub; pkg: " + androidPackage.getPackageName());
        }
        java.io.File decompressPackage = decompressPackage(androidPackage.getPackageName(), androidPackage.getPath());
        if (decompressPackage == null) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Unable to decompress stub at " + androidPackage.getPath(), -11);
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPm.mSettings.disableSystemPackageLPw(androidPackage.getPackageName(), true);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mRemovePackageHelper.removePackage(androidPackage, true);
        try {
            return initPackageTracedLI(decompressPackage, i, i2);
        } catch (com.android.server.pm.PackageManagerException e) {
            android.util.Slog.w("PackageManager", "Failed to install compressed system package:" + androidPackage.getPackageName(), e);
            this.mRemovePackageHelper.removeCodePath(decompressPackage);
            throw e;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private java.io.File decompressPackage(java.lang.String str, java.lang.String str2) {
        if (!com.android.server.pm.PackageManagerServiceUtils.compressedFileExists(str2)) {
            if (com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION) {
                android.util.Slog.i("PackageManager", "No files to decompress at: " + str2);
            }
            return null;
        }
        java.io.File nextCodePath = com.android.server.pm.PackageManagerServiceUtils.getNextCodePath(android.os.Environment.getDataAppDirectory(null), str);
        int decompressFiles = com.android.server.pm.PackageManagerServiceUtils.decompressFiles(str2, nextCodePath, str);
        if (decompressFiles == 1) {
            decompressFiles = com.android.server.pm.PackageManagerServiceUtils.extractNativeBinaries(nextCodePath, str);
        }
        if (decompressFiles == 1) {
            if (!this.mPm.isSystemReady()) {
                if (this.mPm.mReleaseOnSystemReady == null) {
                    this.mPm.mReleaseOnSystemReady = new java.util.ArrayList();
                }
                this.mPm.mReleaseOnSystemReady.add(nextCodePath);
            } else {
                com.android.internal.content.F2fsUtils.releaseCompressedBlocks(this.mContext.getContentResolver(), nextCodePath);
            }
            return nextCodePath;
        }
        if (!nextCodePath.exists()) {
            return null;
        }
        this.mRemovePackageHelper.removeCodePath(nextCodePath);
        return null;
    }

    public void restoreDisabledSystemPackageLIF(com.android.server.pm.DeletePackageAction deletePackageAction, @android.annotation.NonNull int[] iArr, boolean z) throws com.android.server.pm.SystemDeleteException {
        com.android.server.pm.PackageSetting packageSetting = deletePackageAction.mDeletingPs;
        com.android.server.pm.PackageRemovedInfo packageRemovedInfo = deletePackageAction.mRemovedInfo;
        com.android.server.pm.PackageSetting packageSetting2 = deletePackageAction.mDisabledPs;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPm.mSettings.enableSystemPackageLPw(packageSetting2.getPkg().getPackageName());
                com.android.server.pm.PackageManagerServiceUtils.removeNativeBinariesLI(packageSetting);
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        try {
            try {
                synchronized (this.mPm.mInstallLock) {
                    installPackageFromSystemLIF(packageSetting2.getPathString(), iArr, packageRemovedInfo == null ? null : packageRemovedInfo.mOrigUsers, z);
                }
                if (packageSetting2.getPkg().isStub()) {
                    com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
                    com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                    synchronized (packageManagerTracedLock2) {
                        try {
                            disableStubPackage(deletePackageAction, packageSetting, iArr);
                        } finally {
                        }
                    }
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                }
            } catch (java.lang.Throwable th) {
                if (packageSetting2.getPkg().isStub()) {
                    com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mPm.mLock;
                    com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                    synchronized (packageManagerTracedLock3) {
                        try {
                            disableStubPackage(deletePackageAction, packageSetting, iArr);
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        } finally {
                        }
                    }
                }
                throw th;
            }
        } catch (com.android.server.pm.PackageManagerException e) {
            android.util.Slog.w("PackageManager", "Failed to restore system package:" + packageSetting.getPackageName() + ": " + e.getMessage());
            throw new com.android.server.pm.SystemDeleteException(e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    private void disableStubPackage(com.android.server.pm.DeletePackageAction deletePackageAction, com.android.server.pm.PackageSetting packageSetting, @android.annotation.NonNull int[] iArr) {
        com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(packageSetting.getPackageName());
        if (packageLPr != null) {
            int identifier = deletePackageAction.mUser == null ? -1 : deletePackageAction.mUser.getIdentifier();
            if (identifier == -1) {
                for (int i : iArr) {
                    packageLPr.setEnabled(2, i, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                }
                return;
            }
            if (identifier >= 0) {
                packageLPr.setEnabled(2, identifier, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private void installPackageFromSystemLIF(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int[] iArr, @android.annotation.Nullable int[] iArr2, boolean z) throws com.android.server.pm.PackageManagerException {
        java.io.File file = new java.io.File(str);
        com.android.server.pm.pkg.AndroidPackage initPackageTracedLI = initPackageTracedLI(file, this.mPm.getDefParseFlags() | 1 | 16, this.mPm.getSystemPackageScanFlags(file));
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                try {
                    this.mSharedLibraries.updateSharedLibraries(initPackageTracedLI, this.mPm.mSettings.getPackageLPr(initPackageTracedLI.getPackageName()), null, null, java.util.Collections.unmodifiableMap(this.mPm.mPackages));
                } catch (com.android.server.pm.PackageManagerException e) {
                    android.util.Slog.e("PackageManager", "updateAllSharedLibrariesLPw failed: " + e.getMessage());
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        setPackageInstalledForSystemPackage(initPackageTracedLI, iArr, iArr2, z);
        this.mAppDataHelper.prepareAppDataAfterInstallLIF(initPackageTracedLI);
    }

    private void setPackageInstalledForSystemPackage(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull int[] iArr, @android.annotation.Nullable int[] iArr2, boolean z) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                boolean z2 = iArr2 != null;
                if (z2) {
                    boolean z3 = false;
                    for (int i : iArr) {
                        boolean contains = com.android.internal.util.ArrayUtils.contains(iArr2, i);
                        if (contains != packageLPr.getInstalled(i)) {
                            z3 = true;
                        }
                        packageLPr.setInstalled(contains, i);
                        if (contains) {
                            packageLPr.setUninstallReason(0, i);
                        }
                    }
                    this.mPm.mSettings.writeAllUsersPackageRestrictionsLPr();
                    if (z3) {
                        this.mPm.mSettings.writeKernelMappingLPr(packageLPr);
                    }
                }
                this.mPm.mPermissionManager.onPackageInstalled(androidPackage, -1, com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams.DEFAULT, -1);
                for (int i2 : iArr) {
                    if (z2) {
                        this.mPm.mSettings.writePermissionStateForUserLPr(i2, false);
                    }
                }
                if (z) {
                    this.mPm.writeSettingsLPrTEMP();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    public void prepareSystemPackageCleanUp(com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting> watchedArrayMap, java.util.List<java.lang.String> list, android.util.ArrayMap<java.lang.String, java.io.File> arrayMap, int[] iArr) {
        for (int size = watchedArrayMap.size() - 1; size >= 0; size--) {
            com.android.server.pm.PackageSetting valueAt = watchedArrayMap.valueAt(size);
            java.lang.String packageName = valueAt.getPackageName();
            if (valueAt.isSystem()) {
                com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPm.mPackages.get(packageName);
                com.android.server.pm.PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(packageName);
                if (androidPackage != null) {
                    if (!androidPackage.isApex() && disabledSystemPkgLPr != null) {
                        com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "Expecting better updated system app for " + packageName + "; removing system app.  Last known codePath=" + valueAt.getPathString() + ", versionCode=" + valueAt.getVersionCode() + "; scanned versionCode=" + androidPackage.getLongVersionCode());
                        this.mRemovePackageHelper.removePackage(androidPackage, true);
                        arrayMap.put(valueAt.getPackageName(), valueAt.getPath());
                    }
                } else if (disabledSystemPkgLPr == null) {
                    com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "System package " + packageName + " no longer exists; its data will be wiped");
                    this.mRemovePackageHelper.removePackageData(valueAt, iArr);
                } else if (disabledSystemPkgLPr.getPath() == null || !disabledSystemPkgLPr.getPath().exists() || disabledSystemPkgLPr.getPkg() == null) {
                    list.add(packageName);
                } else {
                    arrayMap.put(disabledSystemPkgLPr.getPackageName(), disabledSystemPkgLPr.getPath());
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    public void cleanupDisabledPackageSettings(java.util.List<java.lang.String> list, int[] iArr, int i) {
        java.lang.String str;
        for (int size = list.size() - 1; size >= 0; size--) {
            java.lang.String str2 = list.get(size);
            com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPm.mPackages.get(str2);
            this.mPm.mSettings.removeDisabledSystemPackageLPw(str2);
            if (androidPackage == null) {
                str = "Updated system package " + str2 + " no longer exists; removing its data";
            } else {
                java.lang.String str3 = "Updated system package " + str2 + " no longer exists; rescanning package on data";
                this.mRemovePackageHelper.removePackage(androidPackage, true);
                com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str2);
                if (packageLPr != null) {
                    packageLPr.getPkgState().setUpdatedSystemApp(false);
                }
                try {
                    java.io.File file = new java.io.File(androidPackage.getPath());
                    synchronized (this.mPm.mInstallLock) {
                        initPackageTracedLI(file, 0, i);
                    }
                } catch (com.android.server.pm.PackageManagerException e) {
                    android.util.Slog.e("PackageManager", "Failed to parse updated, ex-system package: " + e.getMessage());
                }
                str = str3;
            }
            com.android.server.pm.PackageSetting packageLPr2 = this.mPm.mSettings.getPackageLPr(str2);
            if (packageLPr2 != null && this.mPm.mPackages.get(str2) == null) {
                this.mRemovePackageHelper.removePackageData(packageLPr2, iArr);
            }
            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, str);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock", "mPm.mLock"})
    public java.util.List<com.android.server.pm.ApexManager.ScanResult> scanApexPackages(android.apex.ApexInfo[] apexInfoArr, int i, int i2, com.android.internal.pm.parsing.PackageParser2 packageParser2, java.util.concurrent.ExecutorService executorService) {
        int i3;
        int i4;
        if (apexInfoArr == null) {
            return java.util.Collections.EMPTY_LIST;
        }
        com.android.server.pm.ParallelPackageParser parallelPackageParser = new com.android.server.pm.ParallelPackageParser(packageParser2, executorService);
        final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (android.apex.ApexInfo apexInfo : apexInfoArr) {
            java.io.File file = new java.io.File(apexInfo.modulePath);
            parallelPackageParser.submit(file, i);
            arrayMap.put(file, apexInfo);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(arrayMap.size());
        for (int i5 = 0; i5 < arrayMap.size(); i5++) {
            arrayList.add(parallelPackageParser.take());
        }
        java.util.Collections.sort(arrayList, new java.util.Comparator() { // from class: com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$scanApexPackages$4;
                lambda$scanApexPackages$4 = com.android.server.pm.InstallPackageHelper.lambda$scanApexPackages$4(arrayMap, (com.android.server.pm.ParallelPackageParser.ParseResult) obj, (com.android.server.pm.ParallelPackageParser.ParseResult) obj2);
                return lambda$scanApexPackages$4;
            }
        });
        java.util.ArrayList arrayList2 = new java.util.ArrayList(arrayMap.size());
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            com.android.server.pm.ParallelPackageParser.ParseResult parseResult = (com.android.server.pm.ParallelPackageParser.ParseResult) arrayList.get(i6);
            java.lang.Throwable th = parseResult.throwable;
            android.apex.ApexInfo apexInfo2 = (android.apex.ApexInfo) arrayMap.get(parseResult.scanFile);
            int systemPackageScanFlags = i2 | 67108864 | this.mPm.getSystemPackageScanFlags(parseResult.scanFile);
            if (apexInfo2.isFactory) {
                i3 = systemPackageScanFlags;
                i4 = i;
            } else {
                i4 = i & (-17);
                i3 = systemPackageScanFlags | 4;
            }
            if (th == null) {
                try {
                    addForInitLI(parseResult.parsedPackage, i4, i3, null, new com.android.server.pm.ApexManager.ActiveApexInfo(apexInfo2));
                    com.android.internal.pm.parsing.pkg.AndroidPackageInternal hideAsFinal = parseResult.parsedPackage.hideAsFinal();
                    if (apexInfo2.isFactory && !apexInfo2.isActive) {
                        disableSystemPackageLPw(hideAsFinal);
                    }
                    arrayList2.add(new com.android.server.pm.ApexManager.ScanResult(apexInfo2, hideAsFinal, hideAsFinal.getPackageName()));
                } catch (com.android.server.pm.PackageManagerException e) {
                    throw new java.lang.IllegalStateException("Failed to scan: " + apexInfo2.modulePath, e);
                }
            } else {
                if (th instanceof com.android.server.pm.PackageManagerException) {
                    throw new java.lang.IllegalStateException("Unable to parse: " + apexInfo2.modulePath, th);
                }
                throw new java.lang.IllegalStateException("Unexpected exception occurred while parsing " + apexInfo2.modulePath, th);
            }
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$scanApexPackages$4(android.util.ArrayMap arrayMap, com.android.server.pm.ParallelPackageParser.ParseResult parseResult, com.android.server.pm.ParallelPackageParser.ParseResult parseResult2) {
        return java.lang.Boolean.compare(((android.apex.ApexInfo) arrayMap.get(parseResult2.scanFile)).isFactory, ((android.apex.ApexInfo) arrayMap.get(parseResult.scanFile)).isFactory);
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock", "mPm.mLock"})
    public void installPackagesFromDir(java.io.File file, int i, int i2, com.android.internal.pm.parsing.PackageParser2 packageParser2, java.util.concurrent.ExecutorService executorService, @android.annotation.Nullable com.android.server.pm.ApexManager.ActiveApexInfo activeApexInfo) {
        int i3;
        java.lang.String str;
        long j;
        java.lang.String str2;
        com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage;
        android.os.UserHandle userHandle;
        java.io.File[] listFiles = file.listFiles();
        if (com.android.internal.util.ArrayUtils.isEmpty(listFiles)) {
            android.util.Log.d("PackageManager", "No files in app dir " + file);
            return;
        }
        com.android.server.pm.ParallelPackageParser parallelPackageParser = new com.android.server.pm.ParallelPackageParser(packageParser2, executorService);
        int length = listFiles.length;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            if (i5 >= length) {
                break;
            }
            java.io.File file2 = listFiles[i5];
            if ((android.content.pm.parsing.ApkLiteParseUtils.isApkFile(file2) || file2.isDirectory()) && !com.android.server.pm.PackageInstallerService.isStageName(file2.getName())) {
                if ((i2 & 16777216) != 0) {
                    com.android.server.pm.parsing.PackageCacher packageCacher = new com.android.server.pm.parsing.PackageCacher(this.mPm.getCacheDir(), this.mPm.mPackageParserCallback);
                    android.util.Log.w("PackageManager", "Dropping cache of " + file2.getAbsolutePath());
                    packageCacher.cleanCachedResult(file2);
                }
                parallelPackageParser.submit(file2, i);
                i6++;
            }
            i5++;
        }
        int i7 = i6;
        while (i7 > 0) {
            com.android.server.pm.ParallelPackageParser.ParseResult take = parallelPackageParser.take();
            java.lang.Throwable th = take.throwable;
            if (th == null) {
                try {
                    android.os.Trace.traceBegin(262144L, "addForInitLI");
                    parsedPackage = take.parsedPackage;
                    userHandle = new android.os.UserHandle(i4);
                    j = 262144;
                    str2 = ": ";
                } catch (com.android.server.pm.PackageManagerException e) {
                    e = e;
                    j = 262144;
                    str2 = ": ";
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    j = 262144;
                }
                try {
                    try {
                        addForInitLI(parsedPackage, i, i2, userHandle, activeApexInfo);
                        android.os.Trace.traceEnd(262144L);
                        str = null;
                        i3 = 1;
                    } catch (com.android.server.pm.PackageManagerException e2) {
                        e = e2;
                        i3 = e.error;
                        str = "Failed to scan " + take.scanFile + str2 + e.getMessage();
                        android.util.Slog.w("PackageManager", str);
                        android.os.Trace.traceEnd(j);
                        if ((i2 & 8388608) != 0) {
                            this.mApexManager.reportErrorWithApkInApex(file.getAbsolutePath(), str);
                        }
                        if ((i2 & 65536) == 0) {
                            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "Deleting invalid package at " + take.scanFile);
                            this.mRemovePackageHelper.removeCodePath(take.scanFile);
                        }
                        i7--;
                        i4 = 0;
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    android.os.Trace.traceEnd(j);
                    throw th;
                }
            } else {
                if (!(th instanceof com.android.server.pm.PackageManagerException)) {
                    throw new java.lang.IllegalStateException("Unexpected exception occurred while parsing " + take.scanFile, th);
                }
                com.android.server.pm.PackageManagerException packageManagerException = (com.android.server.pm.PackageManagerException) th;
                i3 = packageManagerException.error;
                str = "Failed to parse " + take.scanFile + ": " + packageManagerException.getMessage();
                android.util.Slog.w("PackageManager", str);
            }
            if ((i2 & 8388608) != 0 && i3 != 1) {
                this.mApexManager.reportErrorWithApkInApex(file.getAbsolutePath(), str);
            }
            if ((i2 & 65536) == 0 && i3 != 1) {
                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "Deleting invalid package at " + take.scanFile);
                this.mRemovePackageHelper.removeCodePath(take.scanFile);
            }
            i7--;
            i4 = 0;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    public void checkExistingBetterPackages(android.util.ArrayMap<java.lang.String, java.io.File> arrayMap, java.util.List<java.lang.String> list, int i, int i2) {
        for (int i3 = 0; i3 < arrayMap.size(); i3++) {
            java.lang.String keyAt = arrayMap.keyAt(i3);
            if (!this.mPm.mPackages.containsKey(keyAt)) {
                java.io.File valueAt = arrayMap.valueAt(i3);
                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "Expected better " + keyAt + " but never showed up; reverting to system");
                android.util.Pair<java.lang.Integer, java.lang.Integer> systemPackageRescanFlagsAndReparseFlags = this.mPm.getSystemPackageRescanFlagsAndReparseFlags(valueAt, i, i2);
                int intValue = ((java.lang.Integer) systemPackageRescanFlagsAndReparseFlags.first).intValue();
                int intValue2 = ((java.lang.Integer) systemPackageRescanFlagsAndReparseFlags.second).intValue();
                if (intValue == 0) {
                    android.util.Slog.e("PackageManager", "Ignoring unexpected fallback path " + valueAt);
                } else {
                    this.mPm.mSettings.enableSystemPackageLPw(keyAt);
                    try {
                        synchronized (this.mPm.mInstallLock) {
                            try {
                                if (initPackageTracedLI(valueAt, intValue2, intValue).isStub()) {
                                    list.add(keyAt);
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                    } catch (com.android.server.pm.PackageManagerException e) {
                        android.util.Slog.e("PackageManager", "Failed to parse original system package: " + e.getMessage());
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    public com.android.server.pm.pkg.AndroidPackage initPackageTracedLI(java.io.File file, int i, int i2) throws com.android.server.pm.PackageManagerException {
        android.os.Trace.traceBegin(262144L, "scanPackage [" + file.toString() + "]");
        try {
            return initPackageLI(file, i, i2);
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private com.android.server.pm.pkg.AndroidPackage initPackageLI(java.io.File file, int i, int i2) throws com.android.server.pm.PackageManagerException {
        android.os.Trace.traceBegin(262144L, "parsePackage");
        try {
            try {
                com.android.internal.pm.parsing.PackageParser2 scanningPackageParser = this.mPm.mInjector.getScanningPackageParser();
                try {
                    com.android.internal.pm.parsing.pkg.ParsedPackage parsePackage = scanningPackageParser.parsePackage(file, i, false);
                    scanningPackageParser.close();
                    android.os.Trace.traceEnd(262144L);
                    return addForInitLI(parsePackage, i, i2, new android.os.UserHandle(0), null);
                } catch (java.lang.Throwable th) {
                    if (scanningPackageParser != null) {
                        try {
                            scanningPackageParser.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (com.android.internal.pm.parsing.PackageParserException e) {
                throw new com.android.server.pm.PackageManagerException(e.error, e.getMessage(), (java.lang.Throwable) e);
            }
        } catch (java.lang.Throwable th3) {
            android.os.Trace.traceEnd(262144L);
            throw th3;
        }
    }

    /* JADX WARN: Finally extract failed */
    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private com.android.server.pm.pkg.AndroidPackage addForInitLI(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, int i, int i2, @android.annotation.Nullable android.os.UserHandle userHandle, @android.annotation.Nullable com.android.server.pm.ApexManager.ActiveApexInfo activeApexInfo) throws com.android.server.pm.PackageManagerException {
        com.android.server.pm.PackageSetting disabledSystemPkgLPr;
        java.lang.String str;
        boolean z;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            if (activeApexInfo == null) {
                try {
                    if (parsedPackage.isStaticSharedLibrary()) {
                        com.android.server.pm.PackageManagerService.renameStaticSharedLibraryPackage(parsedPackage);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(parsedPackage.getPackageName());
            if (activeApexInfo != null && disabledSystemPkgLPr != null) {
                disabledSystemPkgLPr.setApexModuleName(activeApexInfo.apexModuleName);
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        android.util.Pair<com.android.server.pm.ScanResult, java.lang.Boolean> scanSystemPackageLI = scanSystemPackageLI(parsedPackage, i, i2, userHandle);
        com.android.server.pm.ScanResult scanResult = (com.android.server.pm.ScanResult) scanSystemPackageLI.first;
        boolean booleanValue = ((java.lang.Boolean) scanSystemPackageLI.second).booleanValue();
        com.android.server.pm.InstallRequest installRequest = new com.android.server.pm.InstallRequest(parsedPackage, i, i2, userHandle, scanResult, disabledSystemPkgLPr);
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock2) {
            try {
                com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(parsedPackage.getPackageName());
                if (packageLPr == null) {
                    str = null;
                } else {
                    str = packageLPr.getApexModuleName();
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (activeApexInfo != null) {
            installRequest.setApexModuleName(activeApexInfo.apexModuleName);
        } else if (disabledSystemPkgLPr != null) {
            installRequest.setApexModuleName(disabledSystemPkgLPr.getApexModuleName());
        } else if (str != null) {
            installRequest.setApexModuleName(str);
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock3) {
            boolean z2 = false;
            try {
                try {
                    java.util.List<com.android.server.pm.ReconciledPackage> reconcilePackages = com.android.server.pm.ReconcilePackageUtils.reconcilePackages(java.util.Collections.singletonList(installRequest), this.mPm.mPackages, java.util.Collections.singletonMap(scanResult.mPkgSetting.getPackageName(), this.mPm.getSettingsVersionForPackage(parsedPackage)), this.mSharedLibraries, this.mPm.mSettings.getKeySetManagerService(), this.mPm.mSettings, this.mPm.mInjector.getSystemConfig());
                    if ((i2 & 67108864) == 0) {
                        z = optimisticallyRegisterAppId(installRequest);
                    } else {
                        installRequest.setScannedPackageSettingAppId(-1);
                        z = false;
                    }
                    try {
                        commitReconciledScanResultLocked(reconcilePackages.get(0), this.mPm.mUserManager.getUserIds());
                    } catch (com.android.server.pm.PackageManagerException e) {
                        e = e;
                        z2 = z;
                        if (z2) {
                            cleanUpAppIdCreation(installRequest);
                        }
                        throw e;
                    }
                } finally {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                }
            } catch (com.android.server.pm.PackageManagerException e2) {
                e = e2;
            }
        }
        if (booleanValue) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock4 = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock4) {
                try {
                    this.mPm.mSettings.disableSystemPackageLPw(parsedPackage.getPackageName(), true);
                } finally {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        if (this.mIncrementalManager != null && android.os.incremental.IncrementalManager.isIncrementalPath(parsedPackage.getPath()) && scanResult.mPkgSetting != null && scanResult.mPkgSetting.isLoading()) {
            this.mIncrementalManager.registerLoadingProgressCallback(parsedPackage.getPath(), new com.android.server.pm.IncrementalProgressListener(parsedPackage.getPackageName(), this.mPm));
        }
        return scanResult.mPkgSetting.getPkg();
    }

    private boolean optimisticallyRegisterAppId(@android.annotation.NonNull com.android.server.pm.InstallRequest installRequest) throws com.android.server.pm.PackageManagerException {
        boolean registerAppIdLPw;
        if (!installRequest.isExistingSettingCopied() || installRequest.needsNewAppId()) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    registerAppIdLPw = this.mPm.mSettings.registerAppIdLPw(installRequest.getScannedPackageSetting(), installRequest.needsNewAppId());
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            return registerAppIdLPw;
        }
        return false;
    }

    private void cleanUpAppIdCreation(@android.annotation.NonNull com.android.server.pm.InstallRequest installRequest) {
        if (installRequest.getScannedPackageSetting() != null && installRequest.getScannedPackageSetting().getAppId() > 0) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    this.mPm.mSettings.removeAppIdLPw(installRequest.getScannedPackageSetting().getAppId());
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private com.android.server.pm.ScanResult scanPackageTracedLI(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, int i, int i2, long j, @android.annotation.Nullable android.os.UserHandle userHandle, java.lang.String str) throws com.android.server.pm.PackageManagerException {
        android.os.Trace.traceBegin(262144L, "scanPackage");
        try {
            return scanPackageNewLI(parsedPackage, i, i2, j, userHandle, str);
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    private com.android.server.pm.ScanRequest prepareInitialScanRequest(@android.annotation.NonNull com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, int i, int i2, @android.annotation.Nullable android.os.UserHandle userHandle, java.lang.String str) throws com.android.server.pm.PackageManagerException {
        com.android.server.pm.pkg.AndroidPackage platformPackage;
        java.lang.String realPackageName;
        com.android.server.pm.PackageSetting originalPackageLocked;
        com.android.server.pm.PackageSetting packageLPr;
        com.android.server.pm.PackageSetting disabledSystemPkgLPr;
        boolean isLeavingSharedUser;
        com.android.server.pm.SharedUserSetting sharedUserSetting;
        com.android.server.pm.SharedUserSetting sharedUserSetting2;
        boolean z;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                platformPackage = this.mPm.getPlatformPackage();
                boolean isSystem = com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.isSystem(parsedPackage);
                java.lang.String renamedPackageLPr = this.mPm.mSettings.getRenamedPackageLPr(com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRealPackageOrNull(parsedPackage, isSystem));
                realPackageName = com.android.server.pm.ScanPackageUtils.getRealPackageName(parsedPackage, renamedPackageLPr, isSystem);
                if (realPackageName != null) {
                    com.android.server.pm.ScanPackageUtils.ensurePackageRenamed(parsedPackage, renamedPackageLPr);
                }
                originalPackageLocked = getOriginalPackageLocked(parsedPackage, renamedPackageLPr);
                packageLPr = this.mPm.mSettings.getPackageLPr(parsedPackage.getPackageName());
                if (this.mPm.mTransferredPackages.contains(parsedPackage.getPackageName())) {
                    android.util.Slog.w("PackageManager", "Package " + parsedPackage.getPackageName() + " was transferred to another, but its .apk remains");
                }
                disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(parsedPackage.getPackageName());
                if (packageLPr != null && packageLPr.hasSharedUser()) {
                    isLeavingSharedUser = false;
                } else {
                    isLeavingSharedUser = parsedPackage.isLeavingSharedUser();
                }
                if (!isLeavingSharedUser && parsedPackage.getSharedUserId() != null) {
                    sharedUserSetting = this.mPm.mSettings.getSharedUserLPw(parsedPackage.getSharedUserId(), 0, 0, true);
                } else {
                    sharedUserSetting = null;
                }
                if (packageLPr == null) {
                    sharedUserSetting2 = null;
                } else {
                    sharedUserSetting2 = this.mPm.mSettings.getSharedUserSettingLPr(packageLPr);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (platformPackage == null || !platformPackage.getPackageName().equals(parsedPackage.getPackageName())) {
            z = false;
        } else {
            z = true;
        }
        return new com.android.server.pm.ScanRequest(parsedPackage, sharedUserSetting2, packageLPr == null ? null : packageLPr.getPkg(), packageLPr, sharedUserSetting, disabledSystemPkgLPr, originalPackageLocked, realPackageName, i, i2, z, userHandle, str);
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    private com.android.server.pm.ScanResult scanPackageNewLI(@android.annotation.NonNull com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, int i, int i2, long j, @android.annotation.Nullable android.os.UserHandle userHandle, java.lang.String str) throws com.android.server.pm.PackageManagerException {
        boolean z;
        com.android.server.pm.ScanResult scanPackageOnlyLI;
        com.android.server.pm.ScanRequest prepareInitialScanRequest = prepareInitialScanRequest(parsedPackage, i, i2, userHandle, str);
        com.android.server.pm.PackageSetting packageSetting = prepareInitialScanRequest.mPkgSetting;
        com.android.server.pm.PackageSetting packageSetting2 = prepareInitialScanRequest.mDisabledPkgSetting;
        if (packageSetting != null) {
            z = packageSetting.isUpdatedSystemApp();
        } else {
            z = packageSetting2 != null;
        }
        int adjustScanFlags = adjustScanFlags(i2, packageSetting, packageSetting2, userHandle, parsedPackage);
        com.android.server.pm.ScanPackageUtils.applyPolicy(parsedPackage, adjustScanFlags, this.mPm.getPlatformPackage(), z);
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                assertPackageIsValid(parsedPackage, i, adjustScanFlags);
                scanPackageOnlyLI = com.android.server.pm.ScanPackageUtils.scanPackageOnlyLI(new com.android.server.pm.ScanRequest(parsedPackage, prepareInitialScanRequest.mOldSharedUserSetting, prepareInitialScanRequest.mOldPkg, packageSetting, prepareInitialScanRequest.mSharedUserSetting, packageSetting2, prepareInitialScanRequest.mOriginalPkgSetting, prepareInitialScanRequest.mRealPkgName, i, i2, prepareInitialScanRequest.mIsPlatformPackage, userHandle, str), this.mPm.mInjector, this.mPm.mFactoryTest, j);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return scanPackageOnlyLI;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:128:0x03ec  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x03f3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0427  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x042d  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0277  */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1 */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r19v0 */
    /* JADX WARN: Type inference failed for: r19v1 */
    /* JADX WARN: Type inference failed for: r19v4 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2, types: [int] */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.util.Pair<com.android.server.pm.ScanResult, java.lang.Boolean> scanSystemPackageLI(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, int i, int i2, @android.annotation.Nullable android.os.UserHandle userHandle) throws com.android.server.pm.PackageManagerException {
        com.android.server.pm.PackageSetting packageSetting;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock;
        com.android.server.pm.PackageSetting packageSetting2;
        com.android.server.pm.ScanRequest scanRequest;
        ?? r8;
        int i3;
        boolean z;
        boolean z2;
        com.android.server.pm.PackageSetting packageSetting3;
        boolean z3;
        int i4;
        java.lang.String packageName;
        boolean z4 = (i & 16) != 0;
        com.android.server.pm.ScanRequest prepareInitialScanRequest = prepareInitialScanRequest(parsedPackage, i, i2, userHandle, null);
        com.android.server.pm.PackageSetting packageSetting4 = prepareInitialScanRequest.mPkgSetting;
        com.android.server.pm.PackageSetting packageSetting5 = prepareInitialScanRequest.mOriginalPkgSetting;
        com.android.server.pm.PackageSetting packageSetting6 = packageSetting5 == null ? packageSetting4 : packageSetting5;
        ?? r17 = packageSetting6 != null;
        java.lang.String packageName2 = r17 != false ? packageSetting6.getPackageName() : parsedPackage.getPackageName();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock2) {
            try {
            } catch (java.lang.Throwable th) {
                th = th;
            }
            try {
                boolean isDeviceUpgrading = this.mPm.isDeviceUpgrading();
                if (z4 && r17 == false && this.mPm.mSettings.getDisabledSystemPkgLPr(packageName2) != null) {
                    android.util.Slog.w("PackageManager", "Inconsistent package setting of updated system app for " + packageName2 + ". To recover it, enable the system app and install it as non-updated system app.");
                    this.mPm.mSettings.removeDisabledSystemPackageLPw(packageName2);
                }
                com.android.server.pm.PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(packageName2);
                ?? r19 = disabledSystemPkgLPr != null;
                if (z4 && r19 == true) {
                    packageSetting = disabledSystemPkgLPr;
                    packageManagerTracedLock = packageManagerTracedLock2;
                    packageSetting2 = packageSetting6;
                    scanRequest = prepareInitialScanRequest;
                    com.android.server.pm.ScanRequest scanRequest2 = new com.android.server.pm.ScanRequest(parsedPackage, this.mPm.mSettings.getSharedUserSettingLPr(disabledSystemPkgLPr), null, disabledSystemPkgLPr, prepareInitialScanRequest.mSharedUserSetting, null, null, null, i, i2, prepareInitialScanRequest.mIsPlatformPackage, userHandle, null);
                    i3 = i2;
                    r8 = 1;
                    r8 = 1;
                    r8 = 1;
                    com.android.server.pm.ScanPackageUtils.applyPolicy(parsedPackage, i3, this.mPm.getPlatformPackage(), true);
                    com.android.server.pm.ScanResult scanPackageOnlyLI = com.android.server.pm.ScanPackageUtils.scanPackageOnlyLI(scanRequest2, this.mPm.mInjector, this.mPm.mFactoryTest, -1L);
                    if (scanPackageOnlyLI.mExistingSettingCopied && scanPackageOnlyLI.mRequest.mPkgSetting != null) {
                        scanPackageOnlyLI.mRequest.mPkgSetting.updateFrom(scanPackageOnlyLI.mPkgSetting);
                    }
                } else {
                    packageSetting = disabledSystemPkgLPr;
                    packageManagerTracedLock = packageManagerTracedLock2;
                    packageSetting2 = packageSetting6;
                    scanRequest = prepareInitialScanRequest;
                    r8 = 1;
                    i3 = i2;
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                boolean z5 = (!r17 == true || packageSetting2.getPathString().equals(parsedPackage.getPath())) ? false : r8;
                boolean z6 = (!r17 == true || parsedPackage.getLongVersionCode() <= packageSetting2.getVersionCode()) ? false : r8;
                if (r17 != false) {
                    com.android.server.pm.ScanRequest scanRequest3 = scanRequest;
                    if (scanRequest3.mOldSharedUserSetting != scanRequest3.mSharedUserSetting) {
                        z = r8;
                        z2 = (!z4 && r19 == true && z5 && (z6 || z)) ? r8 : false;
                        if (z2) {
                            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mPm.mLock;
                            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                            synchronized (packageManagerTracedLock3) {
                                try {
                                    this.mPm.mPackages.remove(packageSetting2.getPackageName());
                                } finally {
                                }
                            }
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "System package updated; name: " + packageSetting2.getPackageName() + "; " + packageSetting2.getVersionCode() + " --> " + parsedPackage.getLongVersionCode() + "; " + packageSetting2.getPathString() + " --> " + parsedPackage.getPath());
                            this.mRemovePackageHelper.cleanUpResources(packageSetting2.getPackageName(), new java.io.File(packageSetting2.getPathString()), com.android.server.pm.InstructionSets.getAppDexInstructionSets(packageSetting2.getPrimaryCpuAbiLegacy(), packageSetting2.getSecondaryCpuAbiLegacy()));
                            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock4 = this.mPm.mLock;
                            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                            synchronized (packageManagerTracedLock4) {
                                try {
                                    this.mPm.mSettings.enableSystemPackageLPw(packageSetting2.getPackageName());
                                } finally {
                                }
                            }
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        }
                        if (!z4 && r19 == true && !z2) {
                            if (needSignatureMatchToSystem(parsedPackage.getPackageName())) {
                                android.content.pm.parsing.result.ParseResult signingDetails = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.getSigningDetails(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing(), parsedPackage, false);
                                if (signingDetails.isError()) {
                                    throw new com.android.server.pm.PrepareFailure("Failed collect during scanSystemPackageLI", signingDetails.getException());
                                }
                                packageSetting.setSigningDetails((android.content.pm.SigningDetails) signingDetails.getResult());
                            }
                            parsedPackage.hideAsFinal();
                            java.lang.StringBuilder sb = new java.lang.StringBuilder();
                            sb.append("Package ");
                            sb.append(parsedPackage.getPackageName());
                            sb.append(" at ");
                            sb.append(parsedPackage.getPath());
                            sb.append(" ignored: updated version ");
                            sb.append(r17 != false ? java.lang.String.valueOf(packageSetting2.getVersionCode()) : "unknown");
                            sb.append(" better than this ");
                            sb.append(parsedPackage.getLongVersionCode());
                            throw com.android.server.pm.PackageManagerException.ofInternalError(sb.toString(), -12);
                        }
                        packageSetting3 = packageSetting2;
                        com.android.server.pm.ScanPackageUtils.collectCertificatesLI(packageSetting3, parsedPackage, this.mPm.getSettingsVersionForPackage(parsedPackage), !z4 ? isDeviceUpgrading : (r17 == true && needSignatureMatchToSystem(packageSetting2.getPackageName())) ? r8 : false, z4, this.mPm.isPreNMR1Upgrade());
                        maybeClearProfilesForUpgradesLI(packageSetting3, parsedPackage);
                        if (z4 && r19 == false && r17 != false && !packageSetting3.isSystem()) {
                            if (parsedPackage.getSigningDetails().checkCapability(packageSetting3.getSigningDetails(), (int) r8) && !packageSetting3.getSigningDetails().checkCapability(parsedPackage.getSigningDetails(), 8)) {
                                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "System package signature mismatch; name: " + packageSetting3.getPackageName());
                                com.android.server.pm.PackageFreezer freezePackage = this.mPm.freezePackage(parsedPackage.getPackageName(), -1, "scanPackageInternalLI", 13, null);
                                try {
                                    this.mDeletePackageHelper.deletePackageLIF(parsedPackage.getPackageName(), null, true, this.mPm.mUserManager.getUserIds(), 0, new com.android.server.pm.PackageRemovedInfo(), false);
                                    if (freezePackage != null) {
                                        freezePackage.close();
                                    }
                                } finally {
                                }
                            } else {
                                if (z6 && !z) {
                                    com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(4, "System package disabled; name: " + packageSetting3.getPackageName() + "; old: " + packageSetting3.getPathString() + " @ " + packageSetting3.getVersionCode() + "; new: " + parsedPackage.getPath() + " @ " + parsedPackage.getLongVersionCode());
                                    z3 = r8;
                                    boolean z7 = (67108864 & i3) != 0 ? r8 : false;
                                    if (this.mPm.mShouldStopSystemPackagesByDefault && z4 && r17 == false && !z7 && !parsedPackage.isOverlayIsStatic()) {
                                        packageName = parsedPackage.getPackageName();
                                        if (!com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.contentEquals(packageName) && !this.mPm.mInitialNonStoppedSystemPackages.contains(packageName) && hasLauncherEntry(parsedPackage)) {
                                            i4 = 134217728 | i3;
                                            return new android.util.Pair<>(scanPackageNewLI(parsedPackage, i, i4 | 2, android.content.pm.Flags.fixSystemAppsFirstInstallTime() ? java.lang.System.currentTimeMillis() : 0L, userHandle, null), java.lang.Boolean.valueOf(z3));
                                        }
                                    }
                                    i4 = i3;
                                    return new android.util.Pair<>(scanPackageNewLI(parsedPackage, i, i4 | 2, android.content.pm.Flags.fixSystemAppsFirstInstallTime() ? java.lang.System.currentTimeMillis() : 0L, userHandle, null), java.lang.Boolean.valueOf(z3));
                                }
                                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "System package enabled; name: " + packageSetting3.getPackageName() + "; " + packageSetting3.getVersionCode() + " --> " + parsedPackage.getLongVersionCode() + "; " + packageSetting3.getPathString() + " --> " + parsedPackage.getPath());
                                this.mRemovePackageHelper.cleanUpResources(packageSetting3.getPackageName(), new java.io.File(packageSetting3.getPathString()), com.android.server.pm.InstructionSets.getAppDexInstructionSets(packageSetting3.getPrimaryCpuAbiLegacy(), packageSetting3.getSecondaryCpuAbiLegacy()));
                            }
                        }
                        z3 = false;
                        if ((67108864 & i3) != 0) {
                        }
                        if (this.mPm.mShouldStopSystemPackagesByDefault) {
                            packageName = parsedPackage.getPackageName();
                            if (!com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.contentEquals(packageName)) {
                                i4 = 134217728 | i3;
                                return new android.util.Pair<>(scanPackageNewLI(parsedPackage, i, i4 | 2, android.content.pm.Flags.fixSystemAppsFirstInstallTime() ? java.lang.System.currentTimeMillis() : 0L, userHandle, null), java.lang.Boolean.valueOf(z3));
                            }
                        }
                        i4 = i3;
                        return new android.util.Pair<>(scanPackageNewLI(parsedPackage, i, i4 | 2, android.content.pm.Flags.fixSystemAppsFirstInstallTime() ? java.lang.System.currentTimeMillis() : 0L, userHandle, null), java.lang.Boolean.valueOf(z3));
                    }
                }
                z = false;
                if (!z4) {
                }
                if (z2) {
                }
                if (!z4) {
                }
                packageSetting3 = packageSetting2;
                com.android.server.pm.ScanPackageUtils.collectCertificatesLI(packageSetting3, parsedPackage, this.mPm.getSettingsVersionForPackage(parsedPackage), !z4 ? isDeviceUpgrading : (r17 == true && needSignatureMatchToSystem(packageSetting2.getPackageName())) ? r8 : false, z4, this.mPm.isPreNMR1Upgrade());
                maybeClearProfilesForUpgradesLI(packageSetting3, parsedPackage);
                if (z4) {
                    if (parsedPackage.getSigningDetails().checkCapability(packageSetting3.getSigningDetails(), (int) r8)) {
                    }
                    if (z6) {
                    }
                    com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "System package enabled; name: " + packageSetting3.getPackageName() + "; " + packageSetting3.getVersionCode() + " --> " + parsedPackage.getLongVersionCode() + "; " + packageSetting3.getPathString() + " --> " + parsedPackage.getPath());
                    this.mRemovePackageHelper.cleanUpResources(packageSetting3.getPackageName(), new java.io.File(packageSetting3.getPathString()), com.android.server.pm.InstructionSets.getAppDexInstructionSets(packageSetting3.getPrimaryCpuAbiLegacy(), packageSetting3.getSecondaryCpuAbiLegacy()));
                }
                z3 = false;
                if ((67108864 & i3) != 0) {
                }
                if (this.mPm.mShouldStopSystemPackagesByDefault) {
                }
                i4 = i3;
                return new android.util.Pair<>(scanPackageNewLI(parsedPackage, i, i4 | 2, android.content.pm.Flags.fixSystemAppsFirstInstallTime() ? java.lang.System.currentTimeMillis() : 0L, userHandle, null), java.lang.Boolean.valueOf(z3));
            } catch (java.lang.Throwable th2) {
                th = th2;
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        throw th;
    }

    private static boolean hasLauncherEntry(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage) {
        java.util.HashSet hashSet = new java.util.HashSet();
        hashSet.add("android.intent.category.LAUNCHER");
        java.util.List activities = parsedPackage.getActivities();
        for (int i = 0; i < activities.size(); i++) {
            com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = (com.android.internal.pm.pkg.component.ParsedActivity) activities.get(i);
            if (parsedActivity.isEnabled() && parsedActivity.isExported()) {
                java.util.List intents = parsedActivity.getIntents();
                for (int i2 = 0; i2 < intents.size(); i2++) {
                    android.content.IntentFilter intentFilter = ((com.android.internal.pm.pkg.component.ParsedIntentInfo) intents.get(i2)).getIntentFilter();
                    if (intentFilter != null && intentFilter.matchCategories(hashSet) == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean needSignatureMatchToSystem(java.lang.String str) {
        if (!android.security.Flags.extendVbChainToUpdatedApk()) {
            return false;
        }
        return this.mPm.mInjector.getSystemConfig().getPreinstallPackagesWithStrictSignatureCheck().contains(str);
    }

    private void maybeClearProfilesForUpgradesLI(@android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (packageSetting == null || !this.mPm.isDeviceUpgrading() || packageSetting.getVersionCode() == androidPackage.getLongVersionCode()) {
            return;
        }
        this.mAppDataHelper.clearAppProfilesLIF(androidPackage);
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    @android.annotation.Nullable
    private com.android.server.pm.PackageSetting getOriginalPackageLocked(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable java.lang.String str) {
        if (com.android.server.pm.ScanPackageUtils.isPackageRenamed(androidPackage, str)) {
            return null;
        }
        for (int size = com.android.internal.util.ArrayUtils.size(androidPackage.getOriginalPackages()) - 1; size >= 0; size--) {
            com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr((java.lang.String) androidPackage.getOriginalPackages().get(size));
            if (packageLPr != null && verifyPackageUpdateLPr(packageLPr, androidPackage)) {
                if (this.mPm.mSettings.getSharedUserSettingLPr(packageLPr) != null) {
                    java.lang.String str2 = this.mPm.mSettings.getSharedUserSettingLPr(packageLPr).name;
                    if (!str2.equals(androidPackage.getSharedUserId())) {
                        android.util.Slog.w("PackageManager", "Unable to migrate data from " + packageLPr.getPackageName() + " to " + androidPackage.getPackageName() + ": old shared user settings name " + str2 + " differs from " + androidPackage.getSharedUserId());
                    }
                }
                return packageLPr;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    private boolean verifyPackageUpdateLPr(com.android.server.pm.PackageSetting packageSetting, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if ((packageSetting.getFlags() & 1) == 0) {
            android.util.Slog.w("PackageManager", "Unable to update from " + packageSetting.getPackageName() + " to " + androidPackage.getPackageName() + ": old package not in system partition");
            return false;
        }
        if (this.mPm.mPackages.get(packageSetting.getPackageName()) == null) {
            return true;
        }
        android.util.Slog.w("PackageManager", "Unable to update from " + packageSetting.getPackageName() + " to " + androidPackage.getPackageName() + ": old package still exists");
        return false;
    }

    private void assertPackageIsValid(com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2) throws com.android.server.pm.PackageManagerException {
        if ((i & 64) != 0) {
            com.android.server.pm.ScanPackageUtils.assertCodePolicy(androidPackage);
        }
        if (androidPackage.getPath() == null) {
            throw new com.android.server.pm.PackageManagerException(-2, "Code and resource paths haven't been set correctly");
        }
        boolean z = (i2 & 16) == 0;
        boolean z2 = (i2 & 4096) != 0;
        boolean z3 = (67108864 & i2) != 0;
        if ((z || z2) && this.mPm.snapshotComputer().isApexPackage(androidPackage.getPackageName()) && !z3) {
            throw new com.android.server.pm.PackageManagerException(-5, androidPackage.getPackageName() + " is an APEX package and can't be installed as an APK.");
        }
        this.mPm.mSettings.getKeySetManagerService().assertScannedPackageValid(androidPackage);
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                if (androidPackage.getPackageName().equals(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME) && this.mPm.getCoreAndroidApplication() != null) {
                    android.util.Slog.w("PackageManager", "*************************************************");
                    android.util.Slog.w("PackageManager", "Core android package being redefined.  Skipping.");
                    android.util.Slog.w("PackageManager", " codePath=" + androidPackage.getPath());
                    android.util.Slog.w("PackageManager", "*************************************************");
                    throw new com.android.server.pm.PackageManagerException(-5, "Core android package being redefined.  Skipping.");
                }
                int i3 = i2 & 4;
                if (i3 == 0 && this.mPm.mPackages.containsKey(androidPackage.getPackageName())) {
                    throw new com.android.server.pm.PackageManagerException(-5, "Application package " + androidPackage.getPackageName() + " already installed.  Skipping duplicate.");
                }
                if (androidPackage.isStaticSharedLibrary()) {
                    if (i3 == 0 && this.mPm.mPackages.containsKey(androidPackage.getManifestPackageName())) {
                        throw com.android.server.pm.PackageManagerException.ofInternalError("Duplicate static shared lib provider package", -13);
                    }
                    com.android.server.pm.ScanPackageUtils.assertStaticSharedLibraryIsValid(androidPackage, i2);
                    assertStaticSharedLibraryVersionCodeIsValid(androidPackage);
                }
                if ((i2 & 128) != 0) {
                    if (this.mPm.isExpectingBetter(androidPackage.getPackageName())) {
                        android.util.Slog.w("PackageManager", "Relax SCAN_REQUIRE_KNOWN requirement for package " + androidPackage.getPackageName());
                    } else {
                        com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                        if (packageLPr != null) {
                            if (!androidPackage.getPath().equals(packageLPr.getPathString())) {
                                throw new com.android.server.pm.PackageManagerException(-23, "Application package " + androidPackage.getPackageName() + " found at " + androidPackage.getPath() + " but expected at " + packageLPr.getPathString() + "; ignoring.");
                            }
                        } else {
                            throw new com.android.server.pm.PackageManagerException(-19, "Application package " + androidPackage.getPackageName() + " not found; ignoring.");
                        }
                    }
                }
                if (i3 != 0) {
                    this.mPm.mComponentResolver.assertProvidersNotDefined(androidPackage);
                }
                com.android.server.pm.ScanPackageUtils.assertProcessesAreValid(androidPackage);
                assertPackageWithSharedUserIdIsPrivileged(androidPackage);
                if (androidPackage.getOverlayTarget() != null) {
                    assertOverlayIsValid(androidPackage, i, i2);
                }
                com.android.server.pm.ScanPackageUtils.assertMinSignatureSchemeIsValid(androidPackage, i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    private void assertStaticSharedLibraryVersionCodeIsValid(com.android.server.pm.pkg.AndroidPackage androidPackage) throws com.android.server.pm.PackageManagerException {
        com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> sharedLibraryInfos = this.mSharedLibraries.getSharedLibraryInfos(androidPackage.getStaticSharedLibraryName());
        long j = Long.MIN_VALUE;
        long j2 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        if (sharedLibraryInfos != null) {
            int size = sharedLibraryInfos.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                android.content.pm.SharedLibraryInfo valueAt = sharedLibraryInfos.valueAt(i);
                long longVersionCode = valueAt.getDeclaringPackage().getLongVersionCode();
                if (valueAt.getLongVersion() >= androidPackage.getStaticSharedLibraryVersion()) {
                    if (valueAt.getLongVersion() > androidPackage.getStaticSharedLibraryVersion()) {
                        j2 = java.lang.Math.min(j2, longVersionCode - 1);
                    } else {
                        j = longVersionCode;
                        j2 = j;
                        break;
                    }
                } else {
                    j = java.lang.Math.max(j, longVersionCode + 1);
                }
                i++;
            }
        }
        if (androidPackage.getLongVersionCode() < j || androidPackage.getLongVersionCode() > j2) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Static shared lib version codes must be ordered as lib versions", -14);
        }
    }

    private void assertOverlayIsValid(com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2) throws com.android.server.pm.PackageManagerException {
        com.android.server.pm.PackageSetting packageLPr;
        com.android.server.pm.PackageSetting packageLPr2;
        com.android.server.pm.PackageSetting packageLPr3;
        if ((65536 & i2) != 0) {
            if ((i & 16) == 0) {
                if (!this.mPm.isOverlayMutable(androidPackage.getPackageName())) {
                    throw com.android.server.pm.PackageManagerException.ofInternalError("Overlay " + androidPackage.getPackageName() + " is static and cannot be upgraded.", -15);
                }
                return;
            }
            if ((524288 & i2) != 0) {
                if (androidPackage.getTargetSdkVersion() < com.android.server.pm.ScanPackageUtils.getVendorPartitionVersion()) {
                    android.util.Slog.w("PackageManager", "System overlay " + androidPackage.getPackageName() + " targets an SDK below the required SDK level of vendor overlays (" + com.android.server.pm.ScanPackageUtils.getVendorPartitionVersion() + "). This will become an install error in a future release");
                    return;
                }
                return;
            }
            if (androidPackage.getTargetSdkVersion() < android.os.Build.VERSION.SDK_INT) {
                android.util.Slog.w("PackageManager", "System overlay " + androidPackage.getPackageName() + " targets an SDK below the required SDK level of system overlays (" + android.os.Build.VERSION.SDK_INT + "). This will become an install error in a future release");
                return;
            }
            return;
        }
        if (androidPackage.getTargetSdkVersion() < 29) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    packageLPr3 = this.mPm.mSettings.getPackageLPr(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            if (!com.android.server.pm.PackageManagerServiceUtils.comparePackageSignatures(packageLPr3, androidPackage.getSigningDetails())) {
                throw com.android.server.pm.PackageManagerException.ofInternalError("Overlay " + androidPackage.getPackageName() + " must target Q or later, or be signed with the platform certificate", -16);
            }
        }
        if (androidPackage.getOverlayTargetOverlayableName() == null) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock2) {
                try {
                    packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getOverlayTarget());
                } finally {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            if (packageLPr != null && !com.android.server.pm.PackageManagerServiceUtils.comparePackageSignatures(packageLPr, androidPackage.getSigningDetails())) {
                if (this.mPm.mOverlayConfigSignaturePackage == null) {
                    throw com.android.server.pm.PackageManagerException.ofInternalError("Overlay " + androidPackage.getPackageName() + " and target " + androidPackage.getOverlayTarget() + " signed with different certificates, and the overlay lacks <overlay android:targetName>", -17);
                }
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mPm.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock3) {
                    try {
                        packageLPr2 = this.mPm.mSettings.getPackageLPr(this.mPm.mOverlayConfigSignaturePackage);
                    } finally {
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                if (!com.android.server.pm.PackageManagerServiceUtils.comparePackageSignatures(packageLPr2, androidPackage.getSigningDetails())) {
                    throw com.android.server.pm.PackageManagerException.ofInternalError("Overlay " + androidPackage.getPackageName() + " signed with a different certificate than both the reference package and target " + androidPackage.getOverlayTarget() + ", and the overlay lacks <overlay android:targetName>", -18);
                }
            }
        }
    }

    private void assertPackageWithSharedUserIdIsPrivileged(com.android.server.pm.pkg.AndroidPackage androidPackage) throws com.android.server.pm.PackageManagerException {
        com.android.server.pm.PackageSetting packageLPr;
        if (!com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.isPrivileged(androidPackage) && androidPackage.getSharedUserId() != null && !androidPackage.isLeavingSharedUser()) {
            com.android.server.pm.SharedUserSetting sharedUserSetting = null;
            try {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        sharedUserSetting = this.mPm.mSettings.getSharedUserLPw(androidPackage.getSharedUserId(), 0, 0, false);
                    } finally {
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            } catch (com.android.server.pm.PackageManagerException e) {
            }
            if (sharedUserSetting != null && sharedUserSetting.isPrivileged()) {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock2) {
                    try {
                        packageLPr = this.mPm.mSettings.getPackageLPr(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                    } finally {
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                if (!com.android.server.pm.PackageManagerServiceUtils.comparePackageSignatures(packageLPr, androidPackage.getSigningDetails())) {
                    throw com.android.server.pm.PackageManagerException.ofInternalError("Apps that share a user with a privileged app must themselves be marked as privileged. " + androidPackage.getPackageName() + " shares privileged user " + androidPackage.getSharedUserId() + ".", -19);
                }
            }
        }
    }

    private int adjustScanFlags(int i, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting2, android.os.UserHandle userHandle, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        com.android.server.pm.SharedUserSetting sharedUserSetting;
        int adjustScanFlagsWithPackageSetting = com.android.server.pm.ScanPackageUtils.adjustScanFlagsWithPackageSetting(i, packageSetting, packageSetting2, userHandle);
        boolean z = (524288 & adjustScanFlagsWithPackageSetting) != 0 && com.android.server.pm.ScanPackageUtils.getVendorPartitionVersion() < 28;
        if ((adjustScanFlagsWithPackageSetting & 131072) == 0 && !com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.isPrivileged(androidPackage) && androidPackage.getSharedUserId() != null && !z && !androidPackage.isLeavingSharedUser()) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    try {
                        sharedUserSetting = this.mPm.mSettings.getSharedUserLPw(androidPackage.getSharedUserId(), 0, 0, false);
                    } catch (com.android.server.pm.PackageManagerException e) {
                        sharedUserSetting = null;
                    }
                    if (sharedUserSetting != null && sharedUserSetting.isPrivileged() && com.android.server.pm.PackageManagerServiceUtils.compareSignatures(this.mPm.mSettings.getPackageLPr(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME).getSigningDetails(), androidPackage.getSigningDetails()) != 0) {
                        adjustScanFlagsWithPackageSetting |= 131072;
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        return adjustScanFlagsWithPackageSetting;
    }
}
