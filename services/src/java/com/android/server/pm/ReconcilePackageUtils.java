package com.android.server.pm;

/* loaded from: classes2.dex */
final class ReconcilePackageUtils {
    private static final boolean ALLOW_NON_PRELOADS_SYSTEM_SHAREDUIDS;

    ReconcilePackageUtils() {
    }

    static {
        ALLOW_NON_PRELOADS_SYSTEM_SHAREDUIDS = android.os.Build.IS_DEBUGGABLE || !android.content.pm.Flags.restrictNonpreloadsSystemShareduids();
    }

    public static java.util.List<com.android.server.pm.ReconciledPackage> reconcilePackages(java.util.List<com.android.server.pm.InstallRequest> list, java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> map, java.util.Map<java.lang.String, com.android.server.pm.Settings.VersionInfo> map2, com.android.server.pm.SharedLibrariesImpl sharedLibrariesImpl, com.android.server.pm.KeySetManagerService keySetManagerService, com.android.server.pm.Settings settings, com.android.server.SystemConfig systemConfig) throws com.android.server.pm.ReconcileFailure {
        com.android.server.pm.DeletePackageAction deletePackageAction;
        java.util.ArrayList arrayList;
        boolean z;
        boolean z2;
        android.content.pm.SigningDetails signingDetails;
        boolean z3;
        boolean z4;
        android.content.pm.SigningDetails signingDetails2;
        android.content.pm.SigningDetails signingDetails3;
        android.content.pm.SigningDetails signingDetails4;
        com.android.server.pm.ReconciledPackage reconciledPackage;
        com.android.server.pm.KeySetManagerService keySetManagerService2 = keySetManagerService;
        java.util.ArrayList arrayList2 = new java.util.ArrayList(list.size());
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(map.size() + list.size());
        arrayMap.putAll(map);
        java.util.Map<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> arrayMap2 = new android.util.ArrayMap<>();
        for (com.android.server.pm.InstallRequest installRequest : list) {
            installRequest.onReconcileStarted();
            arrayMap.put(installRequest.getScannedPackageSetting().getPackageName(), installRequest.getParsedPackage());
            java.util.List<android.content.pm.SharedLibraryInfo> allowedSharedLibInfos = sharedLibrariesImpl.getAllowedSharedLibInfos(installRequest);
            if (allowedSharedLibInfos != null) {
                for (android.content.pm.SharedLibraryInfo sharedLibraryInfo : allowedSharedLibInfos) {
                    if (!com.android.server.pm.SharedLibraryUtils.addSharedLibraryToPackageVersionMap(arrayMap2, sharedLibraryInfo)) {
                        throw com.android.server.pm.ReconcileFailure.ofInternalError("Shared Library " + sharedLibraryInfo.getName() + " is being installed twice in this set!", -6);
                    }
                }
            }
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = map.get(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
        for (com.android.server.pm.InstallRequest installRequest2 : list) {
            java.lang.String packageName = installRequest2.getParsedPackage().getPackageName();
            java.util.List<android.content.pm.SharedLibraryInfo> allowedSharedLibInfos2 = sharedLibrariesImpl.getAllowedSharedLibInfos(installRequest2);
            if (!installRequest2.isInstallReplace() || installRequest2.isInstallSystem()) {
                deletePackageAction = null;
            } else {
                com.android.server.pm.DeletePackageAction mayDeletePackageLocked = com.android.server.pm.DeletePackageHelper.mayDeletePackageLocked(installRequest2.getRemovedInfo(), installRequest2.getOriginalPackageSetting(), installRequest2.getDisabledPackageSetting(), ((installRequest2.getScanFlags() & 1024) == 0 ? 0 : 8) | 1, null);
                if (mayDeletePackageLocked == null) {
                    throw new com.android.server.pm.ReconcileFailure(-10, "May not delete " + packageName + " to replace");
                }
                deletePackageAction = mayDeletePackageLocked;
            }
            int scanFlags = installRequest2.getScanFlags();
            int parseFlags = installRequest2.getParseFlags();
            com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage = installRequest2.getParsedPackage();
            com.android.server.pm.PackageSetting disabledPackageSetting = installRequest2.getDisabledPackageSetting();
            com.android.server.pm.PackageSetting staticSharedLibLatestVersionSetting = installRequest2.getStaticSharedLibraryInfo() == null ? null : sharedLibrariesImpl.getStaticSharedLibLatestVersionSetting(installRequest2);
            com.android.server.pm.PackageSetting scannedPackageSetting = staticSharedLibLatestVersionSetting != null ? staticSharedLibLatestVersionSetting : installRequest2.getScannedPackageSetting();
            android.content.pm.SigningDetails signingDetails5 = parsedPackage != null ? parsedPackage.getSigningDetails() : null;
            boolean z5 = (parseFlags & 16) != 0;
            boolean z6 = (scanFlags & 67108864) != 0;
            java.util.Map<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> map3 = arrayMap2;
            com.android.server.pm.SharedUserSetting sharedUserSettingLPr = settings.getSharedUserSettingLPr(scannedPackageSetting);
            if (keySetManagerService2.shouldCheckUpgradeKeySetLocked(scannedPackageSetting, sharedUserSettingLPr, scanFlags)) {
                if (!keySetManagerService2.checkUpgradeKeySetLocked(scannedPackageSetting, parsedPackage)) {
                    if (!z5) {
                        throw new com.android.server.pm.ReconcileFailure(-7, "Package " + parsedPackage.getPackageName() + " upgrade keys do not match the previously installed version");
                    }
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "System package " + parsedPackage.getPackageName() + " signature changed; retaining data.");
                }
                arrayList = arrayList2;
                signingDetails = signingDetails5;
                z4 = false;
                z3 = false;
            } else {
                try {
                    try {
                        com.android.server.pm.Settings.VersionInfo versionInfo = map2.get(packageName);
                        z = com.android.server.pm.PackageManagerServiceUtils.verifySignatures(scannedPackageSetting, sharedUserSettingLPr, disabledPackageSetting, signingDetails5, isCompatSignatureUpdateNeeded(versionInfo), isRecoverSignatureUpdateNeeded(versionInfo), installRequest2.isRollback());
                        try {
                            if (installRequest2.isInstallSystem() || z5 || z6) {
                                arrayList = arrayList2;
                                signingDetails2 = signingDetails5;
                            } else {
                                signingDetails2 = signingDetails5;
                                if (signingDetails2 == null || androidPackage == null) {
                                    arrayList = arrayList2;
                                } else if (androidPackage.getSigningDetails() == null) {
                                    arrayList = arrayList2;
                                } else if (androidPackage.getSigningDetails().checkCapability(signingDetails2, 4)) {
                                    android.util.Slog.d("PackageManager", "Non-preload app associated with system signature: " + scannedPackageSetting.getPackageName());
                                    if (sharedUserSettingLPr == null || ALLOW_NON_PRELOADS_SYSTEM_SHAREDUIDS) {
                                        arrayList = arrayList2;
                                    } else {
                                        java.util.Map<java.lang.String, java.lang.String> packageToSharedUidAllowList = systemConfig.getPackageToSharedUidAllowList();
                                        java.lang.String str = packageToSharedUidAllowList.get(scannedPackageSetting.getPackageName());
                                        if (str != null) {
                                            arrayList = arrayList2;
                                            try {
                                                if (sharedUserSettingLPr.name.equals(str)) {
                                                }
                                            } catch (com.android.server.pm.PackageManagerException e) {
                                                e = e;
                                                if (!z5) {
                                                    throw new com.android.server.pm.ReconcileFailure(e);
                                                }
                                                android.content.pm.SigningDetails signingDetails6 = parsedPackage.getSigningDetails();
                                                if (sharedUserSettingLPr == null) {
                                                    z2 = false;
                                                } else {
                                                    if (sharedUserSettingLPr.signaturesChanged != null && !com.android.server.pm.PackageManagerServiceUtils.canJoinSharedUserId(parsedPackage.getPackageName(), parsedPackage.getSigningDetails(), sharedUserSettingLPr, 2)) {
                                                        if (android.os.SystemProperties.getInt("ro.product.first_api_level", 0) <= 29) {
                                                            throw new com.android.server.pm.ReconcileFailure(-104, "Signature mismatch for shared user: " + sharedUserSettingLPr);
                                                        }
                                                        throw new java.lang.IllegalStateException("Signature mismatch on system package " + parsedPackage.getPackageName() + " for shared user " + sharedUserSettingLPr);
                                                    }
                                                    sharedUserSettingLPr.signatures.mSigningDetails = parsedPackage.getSigningDetails();
                                                    sharedUserSettingLPr.signaturesChanged = java.lang.Boolean.TRUE;
                                                    z2 = true;
                                                }
                                                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "System package " + parsedPackage.getPackageName() + " signature changed; retaining data.");
                                                signingDetails = signingDetails6;
                                                z3 = z2;
                                                z4 = z;
                                                reconciledPackage = new com.android.server.pm.ReconciledPackage(list, map, installRequest2, deletePackageAction, allowedSharedLibInfos2, signingDetails, z3, z4);
                                                if ((installRequest2.getScanFlags() & 16) == 0) {
                                                    try {
                                                        reconciledPackage.mCollectedSharedLibraryInfos = sharedLibrariesImpl.collectSharedLibraryInfos(installRequest2.getParsedPackage(), arrayMap, map3);
                                                    } catch (com.android.server.pm.PackageManagerException e2) {
                                                        throw new com.android.server.pm.ReconcileFailure(e2.error, e2.getMessage());
                                                    }
                                                }
                                                installRequest2.onReconcileFinished();
                                                java.util.ArrayList arrayList3 = arrayList;
                                                arrayList3.add(reconciledPackage);
                                                arrayList2 = arrayList3;
                                                arrayMap2 = map3;
                                                keySetManagerService2 = keySetManagerService;
                                            }
                                        } else {
                                            arrayList = arrayList2;
                                        }
                                        java.lang.String str2 = "Non-preload app " + scannedPackageSetting.getPackageName() + " signed with platform signature and joining shared uid: " + sharedUserSettingLPr.name;
                                        android.util.Slog.e("PackageManager", str2 + ", allowList: " + packageToSharedUidAllowList);
                                        throw new com.android.server.pm.ReconcileFailure(-107, str2);
                                    }
                                } else {
                                    arrayList = arrayList2;
                                }
                            }
                            if (sharedUserSettingLPr != null) {
                                android.content.pm.SigningDetails signingDetails7 = sharedUserSettingLPr.signatures.mSigningDetails;
                                android.content.pm.SigningDetails mergeLineageWith = signingDetails7.mergeLineageWith(signingDetails2);
                                if (mergeLineageWith != signingDetails7) {
                                    for (com.android.server.pm.pkg.AndroidPackage androidPackage2 : sharedUserSettingLPr.getPackages()) {
                                        if (androidPackage2.getPackageName() != null) {
                                            signingDetails4 = signingDetails2;
                                            if (!androidPackage2.getPackageName().equals(parsedPackage.getPackageName())) {
                                                mergeLineageWith = mergeLineageWith.mergeLineageWith(androidPackage2.getSigningDetails(), 2);
                                            }
                                        } else {
                                            signingDetails4 = signingDetails2;
                                        }
                                        signingDetails2 = signingDetails4;
                                    }
                                    signingDetails3 = signingDetails2;
                                    sharedUserSettingLPr.signatures.mSigningDetails = mergeLineageWith;
                                } else {
                                    signingDetails3 = signingDetails2;
                                }
                                if (sharedUserSettingLPr.signaturesChanged == null) {
                                    sharedUserSettingLPr.signaturesChanged = java.lang.Boolean.FALSE;
                                }
                            } else {
                                signingDetails3 = signingDetails2;
                            }
                            z4 = z;
                            signingDetails = signingDetails3;
                            z3 = false;
                        } catch (com.android.server.pm.PackageManagerException e3) {
                            e = e3;
                            arrayList = arrayList2;
                        }
                    } catch (java.lang.IllegalArgumentException e4) {
                        throw new java.lang.RuntimeException("Signing certificates comparison made on incomparable signing details but somehow passed verifySignatures!", e4);
                    }
                } catch (com.android.server.pm.PackageManagerException e5) {
                    e = e5;
                    arrayList = arrayList2;
                    z = false;
                }
            }
            reconciledPackage = new com.android.server.pm.ReconciledPackage(list, map, installRequest2, deletePackageAction, allowedSharedLibInfos2, signingDetails, z3, z4);
            if ((installRequest2.getScanFlags() & 16) == 0 && (installRequest2.getParseFlags() & 16) == 0) {
                reconciledPackage.mCollectedSharedLibraryInfos = sharedLibrariesImpl.collectSharedLibraryInfos(installRequest2.getParsedPackage(), arrayMap, map3);
            }
            installRequest2.onReconcileFinished();
            java.util.ArrayList arrayList32 = arrayList;
            arrayList32.add(reconciledPackage);
            arrayList2 = arrayList32;
            arrayMap2 = map3;
            keySetManagerService2 = keySetManagerService;
        }
        return arrayList2;
    }

    public static boolean isCompatSignatureUpdateNeeded(com.android.server.pm.Settings.VersionInfo versionInfo) {
        return versionInfo.databaseVersion < 2;
    }

    public static boolean isRecoverSignatureUpdateNeeded(com.android.server.pm.Settings.VersionInfo versionInfo) {
        return versionInfo.databaseVersion < 3;
    }
}
