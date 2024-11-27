package com.android.server.pm;

/* loaded from: classes2.dex */
final class ScanPackageUtils {
    ScanPackageUtils() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2, types: [android.content.pm.SharedLibraryInfo] */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r8v8, types: [android.content.pm.SharedLibraryInfo] */
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock"})
    @com.android.internal.annotations.VisibleForTesting
    public static com.android.server.pm.ScanResult scanPackageOnlyLI(@android.annotation.NonNull com.android.server.pm.ScanRequest scanRequest, com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, boolean z, long j) throws com.android.server.pm.PackageManagerException {
        java.lang.String str;
        java.lang.String str2;
        java.io.File file;
        java.lang.String[] strArr;
        boolean z2;
        java.lang.String[] strArr2;
        android.os.UserHandle userHandle;
        com.android.server.pm.SharedUserSetting sharedUserSetting;
        boolean z3;
        java.util.List list;
        com.android.server.pm.PackageSetting packageSetting;
        com.android.server.pm.SharedUserSetting sharedUserSetting2;
        int i;
        int i2;
        com.android.server.pm.PackageAbiHelper packageAbiHelper;
        java.util.List list2;
        boolean z4;
        long firstInstallTimeMillis;
        ?? r7;
        java.lang.Object obj;
        java.lang.String str3;
        com.android.server.pm.PackageAbiHelper abiHelper = packageManagerServiceInjector.getAbiHelper();
        com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage = scanRequest.mParsedPackage;
        com.android.server.pm.PackageSetting packageSetting2 = scanRequest.mPkgSetting;
        com.android.server.pm.PackageSetting packageSetting3 = scanRequest.mDisabledPkgSetting;
        com.android.server.pm.PackageSetting packageSetting4 = scanRequest.mOriginalPkgSetting;
        int i3 = scanRequest.mParseFlags;
        int i4 = scanRequest.mScanFlags;
        java.lang.String str4 = scanRequest.mRealPkgName;
        com.android.server.pm.SharedUserSetting sharedUserSetting3 = scanRequest.mOldSharedUserSetting;
        com.android.server.pm.SharedUserSetting sharedUserSetting4 = scanRequest.mSharedUserSetting;
        android.os.UserHandle userHandle2 = scanRequest.mUser;
        boolean z5 = scanRequest.mIsPlatformPackage;
        java.io.File file2 = new java.io.File(parsedPackage.getPath());
        boolean z6 = (i4 & 4096) != 0;
        boolean z7 = (67108864 & i4) != 0;
        if (z6) {
            str = null;
            str2 = null;
        } else if (packageSetting2 != null) {
            if (packageSetting2.getPkg() != null && packageSetting2.getPkg().isStub()) {
                z6 = true;
                str = null;
                str2 = null;
            } else {
                str = packageSetting2.getPrimaryCpuAbiLegacy();
                str2 = packageSetting2.getSecondaryCpuAbiLegacy();
            }
        } else {
            z6 = true;
            str = null;
            str2 = null;
        }
        if (packageSetting2 != null && sharedUserSetting3 != sharedUserSetting4) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Package ");
            sb.append(parsedPackage.getPackageName());
            sb.append(" shared user changed from ");
            java.lang.String str5 = "<nothing>";
            if (sharedUserSetting3 != null) {
                str3 = "<nothing>";
                str5 = sharedUserSetting3.name;
            } else {
                str3 = "<nothing>";
            }
            sb.append(str5);
            sb.append(" to ");
            sb.append(sharedUserSetting4 != null ? sharedUserSetting4.name : str3);
            sb.append("; replacing with new");
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb.toString());
            packageSetting2 = null;
        }
        if (parsedPackage.getUsesSdkLibraries().isEmpty()) {
            file = file2;
            strArr = null;
        } else {
            strArr = new java.lang.String[parsedPackage.getUsesSdkLibraries().size()];
            file = file2;
            parsedPackage.getUsesSdkLibraries().toArray(strArr);
        }
        if (parsedPackage.getUsesStaticLibraries().isEmpty()) {
            z2 = z5;
            strArr2 = null;
        } else {
            java.lang.String[] strArr3 = new java.lang.String[parsedPackage.getUsesStaticLibraries().size()];
            z2 = z5;
            parsedPackage.getUsesStaticLibraries().toArray(strArr3);
            strArr2 = strArr3;
        }
        java.util.UUID generateNewId = packageManagerServiceInjector.getDomainVerificationManagerInternal().generateNewId();
        boolean z8 = packageSetting2 == null;
        if (z8) {
            z3 = z2;
            userHandle = userHandle2;
            sharedUserSetting = sharedUserSetting4;
            packageSetting = com.android.server.pm.Settings.createNewSetting(parsedPackage.getPackageName(), packageSetting4, packageSetting3, str4, sharedUserSetting4, file, parsedPackage.getNativeLibraryRootDir(), com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawPrimaryCpuAbi(parsedPackage), com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawSecondaryCpuAbi(parsedPackage), parsedPackage.getLongVersionCode(), com.android.server.pm.parsing.PackageInfoUtils.appInfoFlags((com.android.server.pm.pkg.AndroidPackage) parsedPackage, (com.android.server.pm.pkg.PackageStateInternal) null), com.android.server.pm.parsing.PackageInfoUtils.appInfoPrivateFlags((com.android.server.pm.pkg.AndroidPackage) parsedPackage, (com.android.server.pm.pkg.PackageStateInternal) null), userHandle, true, (i4 & 8192) != 0, (32768 & i4) != 0, (134217728 & i4) != 0, com.android.server.pm.UserManagerService.getInstance(), strArr, parsedPackage.getUsesSdkLibrariesVersionsMajor(), parsedPackage.getUsesSdkLibrariesOptional(), strArr2, parsedPackage.getUsesStaticLibrariesVersions(), parsedPackage.getMimeGroups(), generateNewId, parsedPackage.getTargetSdkVersion(), parsedPackage.getRestrictUpdateHash());
            list = null;
        } else {
            userHandle = userHandle2;
            sharedUserSetting = sharedUserSetting4;
            java.io.File file3 = file;
            z3 = z2;
            list = null;
            com.android.server.pm.PackageSetting packageSetting5 = new com.android.server.pm.PackageSetting(packageSetting2);
            packageSetting5.setPkg(parsedPackage);
            com.android.server.pm.Settings.updatePackageSetting(packageSetting5, packageSetting3, sharedUserSetting3, sharedUserSetting, file3, parsedPackage.getNativeLibraryDir(), packageSetting5.getPrimaryCpuAbi(), packageSetting5.getSecondaryCpuAbi(), com.android.server.pm.parsing.PackageInfoUtils.appInfoFlags((com.android.server.pm.pkg.AndroidPackage) parsedPackage, (com.android.server.pm.pkg.PackageStateInternal) packageSetting5), com.android.server.pm.parsing.PackageInfoUtils.appInfoPrivateFlags((com.android.server.pm.pkg.AndroidPackage) parsedPackage, (com.android.server.pm.pkg.PackageStateInternal) packageSetting5), com.android.server.pm.UserManagerService.getInstance(), strArr, parsedPackage.getUsesSdkLibrariesVersionsMajor(), parsedPackage.getUsesSdkLibrariesOptional(), strArr2, parsedPackage.getUsesStaticLibrariesVersions(), parsedPackage.getMimeGroups(), generateNewId, parsedPackage.getTargetSdkVersion(), parsedPackage.getRestrictUpdateHash(), (i4 & 1024) != 0);
            packageSetting = packageSetting5;
        }
        if (z8 && packageSetting4 != null) {
            parsedPackage.setPackageName(packageSetting4.getPackageName());
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "New package " + packageSetting.getRealName() + " renamed to replace old package " + packageSetting.getPackageName());
        }
        int identifier = userHandle == null ? 0 : userHandle.getIdentifier();
        if (!z8) {
            setInstantAppForUser(packageManagerServiceInjector, packageSetting, identifier, (i4 & 8192) != 0, (i4 & 16384) != 0);
        }
        if (packageSetting3 != null || ((i4 & 4) != 0 && packageSetting != null && packageSetting.isSystem())) {
            packageSetting.getPkgState().setUpdatedSystemApp(true);
        }
        packageSetting.getTransientState().setSeInfo(com.android.server.pm.SELinuxMMAC.getSeInfo((com.android.server.pm.pkg.PackageState) packageSetting, (com.android.server.pm.pkg.AndroidPackage) parsedPackage, (com.android.server.pm.pkg.SharedUserApi) sharedUserSetting, packageManagerServiceInjector.getCompatibility()));
        if (packageSetting.isSystem()) {
            configurePackageComponents(parsedPackage);
        }
        java.lang.String deriveAbiOverride = com.android.server.pm.PackageManagerServiceUtils.deriveAbiOverride(scanRequest.mCpuAbiOverride);
        boolean isSystem = packageSetting.isSystem();
        boolean isUpdatedSystemApp = packageSetting.isUpdatedSystemApp();
        java.io.File appLib32InstallDir = getAppLib32InstallDir();
        if (z7) {
            sharedUserSetting2 = sharedUserSetting3;
            i = i4;
            i2 = i3;
            packageAbiHelper = abiHelper;
        } else {
            if ((i4 & 4) == 0) {
                if (z6) {
                    android.os.Trace.traceBegin(262144L, "derivePackageAbi");
                    sharedUserSetting2 = sharedUserSetting3;
                    i = i4;
                    i2 = i3;
                    try {
                        android.util.Pair<com.android.server.pm.PackageAbiHelper.Abis, com.android.server.pm.PackageAbiHelper.NativeLibraryPaths> derivePackageAbi = abiHelper.derivePackageAbi(parsedPackage, isSystem, isUpdatedSystemApp, deriveAbiOverride, appLib32InstallDir);
                        ((com.android.server.pm.PackageAbiHelper.Abis) derivePackageAbi.first).applyTo(parsedPackage);
                        ((com.android.server.pm.PackageAbiHelper.NativeLibraryPaths) derivePackageAbi.second).applyTo(parsedPackage);
                        android.os.Trace.traceEnd(262144L);
                        java.lang.String rawPrimaryCpuAbi = com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawPrimaryCpuAbi(parsedPackage);
                        if (!isSystem || isUpdatedSystemApp || rawPrimaryCpuAbi != null) {
                            packageAbiHelper = abiHelper;
                        } else {
                            packageAbiHelper = abiHelper;
                            com.android.server.pm.PackageAbiHelper.Abis bundledAppAbis = packageAbiHelper.getBundledAppAbis(parsedPackage);
                            bundledAppAbis.applyTo(parsedPackage);
                            bundledAppAbis.applyTo(packageSetting);
                            packageAbiHelper.deriveNativeLibraryPaths(parsedPackage, isSystem, isUpdatedSystemApp, appLib32InstallDir).applyTo(parsedPackage);
                        }
                    } catch (java.lang.Throwable th) {
                        android.os.Trace.traceEnd(262144L);
                        throw th;
                    }
                } else {
                    sharedUserSetting2 = sharedUserSetting3;
                    i = i4;
                    i2 = i3;
                    packageAbiHelper = abiHelper;
                    parsedPackage.setPrimaryCpuAbi(str).setSecondaryCpuAbi(str2);
                    packageAbiHelper.deriveNativeLibraryPaths(parsedPackage, isSystem, isUpdatedSystemApp, appLib32InstallDir).applyTo(parsedPackage);
                }
            } else {
                sharedUserSetting2 = sharedUserSetting3;
                i = i4;
                i2 = i3;
                packageAbiHelper = abiHelper;
                if ((i & 256) != 0) {
                    parsedPackage.setPrimaryCpuAbi(packageSetting.getPrimaryCpuAbiLegacy()).setSecondaryCpuAbi(packageSetting.getSecondaryCpuAbiLegacy());
                }
                packageAbiHelper.deriveNativeLibraryPaths(parsedPackage, isSystem, isUpdatedSystemApp, appLib32InstallDir).applyTo(parsedPackage);
            }
            if (z3) {
                parsedPackage.setPrimaryCpuAbi(dalvik.system.VMRuntime.getRuntime().is64Bit() ? android.os.Build.SUPPORTED_64_BIT_ABIS[0] : android.os.Build.SUPPORTED_32_BIT_ABIS[0]);
            }
        }
        if ((i & 1) == 0 && (i & 4) != 0 && deriveAbiOverride == null) {
            android.util.Slog.w("PackageManager", "Ignoring persisted ABI override for package " + parsedPackage.getPackageName());
        }
        packageSetting.setPrimaryCpuAbi(com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawPrimaryCpuAbi(parsedPackage)).setSecondaryCpuAbi(com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawSecondaryCpuAbi(parsedPackage)).setCpuAbiOverride(deriveAbiOverride);
        packageSetting.setLegacyNativeLibraryPath(parsedPackage.getNativeLibraryRootDir());
        if ((i & 16) == 0 && sharedUserSetting2 != null) {
            list2 = applyAdjustedAbiToSharedUser(sharedUserSetting2, parsedPackage, packageAbiHelper.getAdjustedAbiForSharedUser(sharedUserSetting2.getPackageStates(), parsedPackage));
        } else {
            list2 = list;
        }
        parsedPackage.setFactoryTest(z && parsedPackage.getRequestedPermissions().contains("android.permission.FACTORY_TEST"));
        if (!isSystem) {
            z4 = true;
        } else {
            z4 = true;
            packageSetting.setIsOrphaned(true);
        }
        long lastModifiedTime = com.android.server.pm.PackageManagerServiceUtils.getLastModifiedTime(parsedPackage);
        if (identifier == -1) {
            firstInstallTimeMillis = com.android.server.pm.pkg.PackageStateUtils.getEarliestFirstInstallTime(packageSetting.getUserStates());
        } else {
            firstInstallTimeMillis = packageSetting.readUserState(identifier).getFirstInstallTimeMillis();
        }
        if (j != 0) {
            if (firstInstallTimeMillis == 0) {
                packageSetting.setFirstInstallTime(j, identifier).setLastUpdateTime(j);
            } else if ((i & 8) != 0) {
                packageSetting.setLastUpdateTime(j);
            }
        } else if (firstInstallTimeMillis == 0) {
            packageSetting.setFirstInstallTime(lastModifiedTime, identifier).setLastUpdateTime(lastModifiedTime);
        } else if ((i2 & 16) != 0 && lastModifiedTime != packageSetting.getLastModifiedTime()) {
            packageSetting.setLastUpdateTime(lastModifiedTime);
        }
        packageSetting.setLastModifiedTime(lastModifiedTime);
        packageSetting.setPkg(parsedPackage).setFlags(com.android.server.pm.parsing.PackageInfoUtils.appInfoFlags((com.android.server.pm.pkg.AndroidPackage) parsedPackage, (com.android.server.pm.pkg.PackageStateInternal) packageSetting)).setPrivateFlags(com.android.server.pm.parsing.PackageInfoUtils.appInfoPrivateFlags((com.android.server.pm.pkg.AndroidPackage) parsedPackage, (com.android.server.pm.pkg.PackageStateInternal) packageSetting));
        if (parsedPackage.getLongVersionCode() != packageSetting.getVersionCode()) {
            packageSetting.setLongVersionCode(parsedPackage.getLongVersionCode());
        }
        java.lang.String volumeUuid = parsedPackage.getVolumeUuid();
        if (!java.util.Objects.equals(volumeUuid, packageSetting.getVolumeUuid())) {
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            sb2.append("Update");
            sb2.append(packageSetting.isSystem() ? " system" : "");
            sb2.append(" package ");
            sb2.append(parsedPackage.getPackageName());
            sb2.append(" volume from ");
            sb2.append(packageSetting.getVolumeUuid());
            sb2.append(" to ");
            sb2.append(volumeUuid);
            android.util.Slog.i("PackageManager", sb2.toString());
            packageSetting.setVolumeUuid(volumeUuid);
        }
        if (android.text.TextUtils.isEmpty(parsedPackage.getSdkLibraryName())) {
            r7 = list;
        } else {
            r7 = com.android.server.pm.parsing.pkg.AndroidPackageUtils.createSharedLibraryForSdk(parsedPackage);
        }
        if (android.text.TextUtils.isEmpty(parsedPackage.getStaticSharedLibraryName())) {
            obj = list;
        } else {
            obj = com.android.server.pm.parsing.pkg.AndroidPackageUtils.createSharedLibraryForStatic(parsedPackage);
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(parsedPackage.getLibraryNames())) {
            java.util.List arrayList = new java.util.ArrayList(parsedPackage.getLibraryNames().size());
            java.util.Iterator it = parsedPackage.getLibraryNames().iterator();
            while (it.hasNext()) {
                arrayList.add(com.android.server.pm.parsing.pkg.AndroidPackageUtils.createSharedLibraryForDynamic(parsedPackage, (java.lang.String) it.next()));
            }
            list = arrayList;
        }
        return new com.android.server.pm.ScanResult(scanRequest, packageSetting, list2, !z8, -1, r7, obj, list);
    }

    public static int adjustScanFlagsWithPackageSetting(int i, com.android.server.pm.PackageSetting packageSetting, com.android.server.pm.PackageSetting packageSetting2, android.os.UserHandle userHandle) {
        if ((i & 4) != 0 && packageSetting2 == null && packageSetting != null && packageSetting.isSystem()) {
            packageSetting2 = packageSetting;
        }
        if (packageSetting2 != null) {
            i |= 65536;
            if ((packageSetting2.getPrivateFlags() & 8) != 0) {
                i |= 131072;
            }
            if ((packageSetting2.getPrivateFlags() & 131072) != 0) {
                i |= 262144;
            }
            if ((packageSetting2.getPrivateFlags() & 262144) != 0) {
                i |= 524288;
            }
            if ((packageSetting2.getPrivateFlags() & 524288) != 0) {
                i |= 1048576;
            }
            if ((packageSetting2.getPrivateFlags() & 2097152) != 0) {
                i |= 2097152;
            }
            if ((packageSetting2.getPrivateFlags() & 1073741824) != 0) {
                i |= 4194304;
            }
        }
        if (packageSetting != null) {
            int identifier = userHandle == null ? 0 : userHandle.getIdentifier();
            if (packageSetting.getInstantApp(identifier)) {
                i |= 8192;
            }
            if (packageSetting.getVirtualPreload(identifier)) {
                return i | 32768;
            }
            return i;
        }
        return i;
    }

    public static void assertCodePolicy(com.android.server.pm.pkg.AndroidPackage androidPackage) throws com.android.server.pm.PackageManagerException {
        if (androidPackage.isDeclaredHavingCode() && !apkHasCode(androidPackage.getBaseApkPath())) {
            throw new com.android.server.pm.PackageManagerException(-2, "Package " + androidPackage.getBaseApkPath() + " code is missing");
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(androidPackage.getSplitCodePaths())) {
            for (int i = 0; i < androidPackage.getSplitCodePaths().length; i++) {
                if (((androidPackage.getSplitFlags()[i] & 4) != 0) && !apkHasCode(androidPackage.getSplitCodePaths()[i])) {
                    throw new com.android.server.pm.PackageManagerException(-2, "Package " + androidPackage.getSplitCodePaths()[i] + " code is missing");
                }
            }
        }
    }

    public static void assertStaticSharedLibraryIsValid(com.android.server.pm.pkg.AndroidPackage androidPackage, int i) throws com.android.server.pm.PackageManagerException {
        if (androidPackage.getTargetSdkVersion() < 26) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Packages declaring static-shared libs must target O SDK or higher", -22);
        }
        if ((i & 8192) != 0) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Packages declaring static-shared libs cannot be instant apps", -23);
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(androidPackage.getOriginalPackages())) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Packages declaring static-shared libs cannot be renamed", -24);
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(androidPackage.getLibraryNames())) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Packages declaring static-shared libs cannot declare dynamic libs", -25);
        }
        if (androidPackage.getSharedUserId() != null) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Packages declaring static-shared libs cannot declare shared users", -26);
        }
        if (!androidPackage.getActivities().isEmpty()) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Static shared libs cannot declare activities", -27);
        }
        if (!androidPackage.getServices().isEmpty()) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Static shared libs cannot declare services", -28);
        }
        if (!androidPackage.getProviders().isEmpty()) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Static shared libs cannot declare content providers", -29);
        }
        if (!androidPackage.getReceivers().isEmpty()) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Static shared libs cannot declare broadcast receivers", -30);
        }
        if (!androidPackage.getPermissionGroups().isEmpty()) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Static shared libs cannot declare permission groups", -31);
        }
        if (!androidPackage.getAttributions().isEmpty()) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Static shared libs cannot declare features", -32);
        }
        if (!androidPackage.getPermissions().isEmpty()) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Static shared libs cannot declare permissions", -33);
        }
        if (!androidPackage.getProtectedBroadcasts().isEmpty()) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Static shared libs cannot declare protected broadcasts", -34);
        }
        if (androidPackage.getOverlayTarget() != null) {
            throw com.android.server.pm.PackageManagerException.ofInternalError("Static shared libs cannot be overlay targets", -35);
        }
    }

    public static void assertProcessesAreValid(com.android.server.pm.pkg.AndroidPackage androidPackage) throws com.android.server.pm.PackageManagerException {
        java.util.Map processes = androidPackage.getProcesses();
        if (!processes.isEmpty()) {
            if (!processes.containsKey(androidPackage.getProcessName())) {
                throw new com.android.server.pm.PackageManagerException(-122, "Can't install because application tag's process attribute " + androidPackage.getProcessName() + " (in package " + androidPackage.getPackageName() + ") is not included in the <processes> list");
            }
            assertPackageProcesses(androidPackage, androidPackage.getActivities(), processes, com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY);
            assertPackageProcesses(androidPackage, androidPackage.getServices(), processes, com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE);
            assertPackageProcesses(androidPackage, androidPackage.getReceivers(), processes, "receiver");
            assertPackageProcesses(androidPackage, androidPackage.getProviders(), processes, "provider");
        }
    }

    private static <T extends com.android.internal.pm.pkg.component.ParsedMainComponent> void assertPackageProcesses(com.android.server.pm.pkg.AndroidPackage androidPackage, java.util.List<T> list, java.util.Map<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess> map, java.lang.String str) throws com.android.server.pm.PackageManagerException {
        if (list == null) {
            return;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            T t = list.get(size);
            if (!map.containsKey(t.getProcessName())) {
                throw new com.android.server.pm.PackageManagerException(-122, "Can't install because " + str + " " + t.getClassName() + "'s process attribute " + t.getProcessName() + " (in package " + androidPackage.getPackageName() + ") is not included in the <processes> list");
            }
        }
    }

    public static void assertMinSignatureSchemeIsValid(com.android.server.pm.pkg.AndroidPackage androidPackage, int i) throws com.android.server.pm.PackageManagerException {
        int minimumSignatureSchemeVersionForTargetSdk = android.util.apk.ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(androidPackage.getTargetSdkVersion());
        if (androidPackage.getSigningDetails().getSignatureSchemeVersion() < minimumSignatureSchemeVersionForTargetSdk) {
            throw new com.android.server.pm.PackageManagerException(com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks.GEOFENCE_STATUS_ERROR_INVALID_TRANSITION, "No signature found in package of version " + minimumSignatureSchemeVersionForTargetSdk + " or newer for package " + androidPackage.getPackageName());
        }
    }

    @android.annotation.Nullable
    public static java.lang.String getRealPackageName(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable java.lang.String str, boolean z) {
        if (isPackageRenamed(androidPackage, str)) {
            return com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRealPackageOrNull(androidPackage, z);
        }
        return null;
    }

    public static boolean isPackageRenamed(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable java.lang.String str) {
        return androidPackage.getOriginalPackages().contains(str);
    }

    public static void ensurePackageRenamed(@android.annotation.NonNull com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, @android.annotation.NonNull java.lang.String str) {
        if (!parsedPackage.getOriginalPackages().contains(str) || parsedPackage.getPackageName().equals(str)) {
            return;
        }
        parsedPackage.setPackageName(str);
    }

    public static boolean apkHasCode(java.lang.String str) {
        android.util.jar.StrictJarFile strictJarFile;
        android.util.jar.StrictJarFile strictJarFile2 = null;
        try {
            strictJarFile = new android.util.jar.StrictJarFile(str, false, false);
        } catch (java.io.IOException e) {
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            boolean z = strictJarFile.findEntry("classes.dex") != null;
            try {
                strictJarFile.close();
            } catch (java.io.IOException e2) {
            }
            return z;
        } catch (java.io.IOException e3) {
            strictJarFile2 = strictJarFile;
            if (strictJarFile2 != null) {
                try {
                    strictJarFile2.close();
                } catch (java.io.IOException e4) {
                }
            }
            return false;
        } catch (java.lang.Throwable th2) {
            th = th2;
            strictJarFile2 = strictJarFile;
            if (strictJarFile2 != null) {
                try {
                    strictJarFile2.close();
                } catch (java.io.IOException e5) {
                }
            }
            throw th;
        }
    }

    public static void configurePackageComponents(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> componentsEnabledStates = com.android.server.SystemConfig.getInstance().getComponentsEnabledStates(androidPackage.getPackageName());
        if (componentsEnabledStates == null) {
            return;
        }
        for (int size = com.android.internal.util.ArrayUtils.size(androidPackage.getActivities()) - 1; size >= 0; size--) {
            com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = (com.android.internal.pm.pkg.component.ParsedActivity) androidPackage.getActivities().get(size);
            java.lang.Boolean bool = componentsEnabledStates.get(parsedActivity.getName());
            if (bool != null) {
                com.android.internal.pm.pkg.component.ComponentMutateUtils.setEnabled(parsedActivity, bool.booleanValue());
            }
        }
        for (int size2 = com.android.internal.util.ArrayUtils.size(androidPackage.getReceivers()) - 1; size2 >= 0; size2--) {
            com.android.internal.pm.pkg.component.ParsedActivity parsedActivity2 = (com.android.internal.pm.pkg.component.ParsedActivity) androidPackage.getReceivers().get(size2);
            java.lang.Boolean bool2 = componentsEnabledStates.get(parsedActivity2.getName());
            if (bool2 != null) {
                com.android.internal.pm.pkg.component.ComponentMutateUtils.setEnabled(parsedActivity2, bool2.booleanValue());
            }
        }
        for (int size3 = com.android.internal.util.ArrayUtils.size(androidPackage.getProviders()) - 1; size3 >= 0; size3--) {
            com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = (com.android.internal.pm.pkg.component.ParsedProvider) androidPackage.getProviders().get(size3);
            java.lang.Boolean bool3 = componentsEnabledStates.get(parsedProvider.getName());
            if (bool3 != null) {
                com.android.internal.pm.pkg.component.ComponentMutateUtils.setEnabled(parsedProvider, bool3.booleanValue());
            }
        }
        for (int size4 = com.android.internal.util.ArrayUtils.size(androidPackage.getServices()) - 1; size4 >= 0; size4--) {
            com.android.internal.pm.pkg.component.ParsedService parsedService = (com.android.internal.pm.pkg.component.ParsedService) androidPackage.getServices().get(size4);
            java.lang.Boolean bool4 = componentsEnabledStates.get(parsedService.getName());
            if (bool4 != null) {
                com.android.internal.pm.pkg.component.ComponentMutateUtils.setEnabled(parsedService, bool4.booleanValue());
            }
        }
    }

    public static int getVendorPartitionVersion() {
        java.lang.String str = android.os.SystemProperties.get("ro.vndk.version");
        if (!str.isEmpty()) {
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.NumberFormatException e) {
                if (com.android.internal.util.ArrayUtils.contains(android.os.Build.VERSION.ACTIVE_CODENAMES, str)) {
                    return 10000;
                }
                return 28;
            }
        }
        return 28;
    }

    public static void applyPolicy(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, int i, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) {
        boolean z2;
        boolean z3 = false;
        if ((65536 & i) != 0) {
            parsedPackage.setSystem(true);
            if (parsedPackage.isDirectBootAware()) {
                parsedPackage.setAllComponentsDirectBootAware(true);
            }
            if (com.android.server.pm.PackageManagerServiceUtils.compressedFileExists(parsedPackage.getPath())) {
                parsedPackage.setStub(true);
            }
            z2 = true;
        } else {
            parsedPackage.clearProtectedBroadcasts().setCoreApp(false).setPersistent(false).setDefaultToDeviceProtectedStorage(false).setDirectBootAware(false).capPermissionPriorities();
            z2 = z;
        }
        int i2 = 131072 & i;
        if (i2 == 0) {
            parsedPackage.markNotActivitiesAsNotExportedIfSingleUser();
        }
        parsedPackage.setApex((67108864 & i) != 0);
        parsedPackage.setPrivileged(i2 != 0).setOem((262144 & i) != 0).setVendor((524288 & i) != 0).setProduct((1048576 & i) != 0).setSystemExt((2097152 & i) != 0).setOdm((i & 4194304) != 0);
        if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(parsedPackage.getPackageName()) || (androidPackage != null && com.android.server.pm.PackageManagerServiceUtils.compareSignatures(androidPackage.getSigningDetails(), parsedPackage.getSigningDetails()) == 0)) {
            z3 = true;
        }
        parsedPackage.setSignedWithPlatformKey(z3);
        if (!z2) {
            parsedPackage.clearOriginalPackages().clearAdoptPermissions();
        }
        com.android.server.pm.parsing.library.PackageBackwardCompatibility.modifySharedLibraries(parsedPackage, z2, z);
    }

    public static java.util.List<java.lang.String> applyAdjustedAbiToSharedUser(com.android.server.pm.SharedUserSetting sharedUserSetting, com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, java.lang.String str) {
        if (parsedPackage != null) {
            parsedPackage.setPrimaryCpuAbi(str);
        }
        com.android.server.utils.WatchedArraySet<com.android.server.pm.PackageSetting> packageSettings = sharedUserSetting.getPackageSettings();
        java.util.ArrayList arrayList = null;
        for (int i = 0; i < packageSettings.size(); i++) {
            com.android.server.pm.PackageSetting valueAt = packageSettings.valueAt(i);
            if ((parsedPackage == null || !parsedPackage.getPackageName().equals(valueAt.getPackageName())) && valueAt.getPrimaryCpuAbiLegacy() == null) {
                valueAt.setPrimaryCpuAbi(str);
                valueAt.onChanged();
                if (valueAt.getPkg() != null && !android.text.TextUtils.equals(str, com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawPrimaryCpuAbi(valueAt.getPkg()))) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(valueAt.getPathString());
                }
            }
        }
        return arrayList;
    }

    public static void collectCertificatesLI(com.android.server.pm.PackageSetting packageSetting, com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, com.android.server.pm.Settings.VersionInfo versionInfo, boolean z, boolean z2, boolean z3) throws com.android.server.pm.PackageManagerException {
        long lastModifiedTime;
        if (z3) {
            lastModifiedTime = new java.io.File(parsedPackage.getPath()).lastModified();
        } else {
            lastModifiedTime = com.android.server.pm.PackageManagerServiceUtils.getLastModifiedTime(parsedPackage);
        }
        if (packageSetting != null && !z && packageSetting.getPathString().equals(parsedPackage.getPath()) && packageSetting.getLastModifiedTime() == lastModifiedTime && !com.android.server.pm.ReconcilePackageUtils.isCompatSignatureUpdateNeeded(versionInfo) && !com.android.server.pm.ReconcilePackageUtils.isRecoverSignatureUpdateNeeded(versionInfo)) {
            if (packageSetting.getSigningDetails().getSignatures() != null && packageSetting.getSigningDetails().getSignatures().length != 0 && packageSetting.getSigningDetails().getSignatureSchemeVersion() != 0) {
                parsedPackage.setSigningDetails(new android.content.pm.SigningDetails(packageSetting.getSigningDetails()));
                return;
            }
            android.util.Slog.w("PackageManager", "PackageSetting for " + packageSetting.getPackageName() + " is missing signatures.  Collecting certs again to recover them.");
        } else {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(parsedPackage.getPath());
            sb.append(" changed; collecting certs");
            sb.append(z ? " (forced)" : "");
            android.util.Slog.i("PackageManager", sb.toString());
        }
        try {
            android.os.Trace.traceBegin(262144L, "collectCertificates");
            android.content.pm.parsing.result.ParseResult signingDetails = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.getSigningDetails(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing(), parsedPackage, z2);
            if (signingDetails.isError()) {
                throw new com.android.server.pm.PackageManagerException(signingDetails.getErrorCode(), signingDetails.getErrorMessage(), signingDetails.getException());
            }
            parsedPackage.setSigningDetails((android.content.pm.SigningDetails) signingDetails.getResult());
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    public static void setInstantAppForUser(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageSetting packageSetting, int i, boolean z, boolean z2) {
        if (!z && !z2) {
            return;
        }
        if (i != -1) {
            if (z && !packageSetting.getInstantApp(i)) {
                packageSetting.setInstantApp(true, i);
                return;
            } else {
                if (z2 && packageSetting.getInstantApp(i)) {
                    packageSetting.setInstantApp(false, i);
                    return;
                }
                return;
            }
        }
        for (int i2 : packageManagerServiceInjector.getUserManagerInternal().getUserIds()) {
            if (z && !packageSetting.getInstantApp(i2)) {
                packageSetting.setInstantApp(true, i2);
            } else if (z2 && packageSetting.getInstantApp(i2)) {
                packageSetting.setInstantApp(false, i2);
            }
        }
    }

    public static java.io.File getAppLib32InstallDir() {
        return new java.io.File(android.os.Environment.getDataDirectory(), "app-lib");
    }
}
