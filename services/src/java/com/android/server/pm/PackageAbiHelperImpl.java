package com.android.server.pm;

/* loaded from: classes2.dex */
final class PackageAbiHelperImpl implements com.android.server.pm.PackageAbiHelper {

    @android.annotation.Nullable
    private static java.lang.String[] sNativelySupported32BitAbis = null;

    @android.annotation.Nullable
    private static java.lang.String[] sNativelySupported64BitAbis = null;

    PackageAbiHelperImpl() {
    }

    private static java.lang.String calculateBundledApkRoot(java.lang.String str) {
        java.io.File canonicalFile;
        java.io.File file = new java.io.File(str);
        if (android.os.FileUtils.contains(android.os.Environment.getRootDirectory(), file)) {
            canonicalFile = android.os.Environment.getRootDirectory();
        } else if (android.os.FileUtils.contains(android.os.Environment.getOemDirectory(), file)) {
            canonicalFile = android.os.Environment.getOemDirectory();
        } else if (android.os.FileUtils.contains(android.os.Environment.getVendorDirectory(), file)) {
            canonicalFile = android.os.Environment.getVendorDirectory();
        } else if (android.os.FileUtils.contains(android.os.Environment.getOdmDirectory(), file)) {
            canonicalFile = android.os.Environment.getOdmDirectory();
        } else if (android.os.FileUtils.contains(android.os.Environment.getProductDirectory(), file)) {
            canonicalFile = android.os.Environment.getProductDirectory();
        } else if (android.os.FileUtils.contains(android.os.Environment.getSystemExtDirectory(), file)) {
            canonicalFile = android.os.Environment.getSystemExtDirectory();
        } else if (android.os.FileUtils.contains(android.os.Environment.getOdmDirectory(), file)) {
            canonicalFile = android.os.Environment.getOdmDirectory();
        } else if (android.os.FileUtils.contains(android.os.Environment.getApexDirectory(), file)) {
            java.lang.String[] split = file.getAbsolutePath().split(java.io.File.separator);
            if (split.length > 2) {
                canonicalFile = new java.io.File(split[1] + java.io.File.separator + split[2]);
            } else {
                android.util.Slog.w("PackageManager", "Can't canonicalize code path " + file);
                canonicalFile = android.os.Environment.getApexDirectory();
            }
        } else {
            try {
                canonicalFile = file.getCanonicalFile();
                java.io.File parentFile = canonicalFile.getParentFile();
                while (true) {
                    java.io.File parentFile2 = parentFile.getParentFile();
                    if (parentFile2 == null) {
                        break;
                    }
                    canonicalFile = parentFile;
                    parentFile = parentFile2;
                }
                android.util.Slog.w("PackageManager", "Unrecognized code path " + file + " - using " + canonicalFile);
            } catch (java.io.IOException e) {
                android.util.Slog.w("PackageManager", "Can't canonicalize code path " + file);
                return android.os.Environment.getRootDirectory().getPath();
            }
        }
        return canonicalFile.getPath();
    }

    private static java.lang.String deriveCodePathName(java.lang.String str) {
        if (str == null) {
            return null;
        }
        java.io.File file = new java.io.File(str);
        java.lang.String name = file.getName();
        if (file.isDirectory()) {
            return name;
        }
        if (name.endsWith(".apk") || name.endsWith(".tmp")) {
            return name.substring(0, name.lastIndexOf(46));
        }
        android.util.Slog.w("PackageManager", "Odd, " + str + " doesn't look like an APK");
        return null;
    }

    private static void maybeThrowExceptionForMultiArchCopy(java.lang.String str, int i, boolean z) throws com.android.server.pm.PackageManagerException {
        if (i < 0) {
            if (i != -114 && i != -113) {
                throw new com.android.server.pm.PackageManagerException(i, str);
            }
            if (z && i == -113) {
                throw new com.android.server.pm.PackageManagerException(-131, "The multiArch app's native libs don't support all the natively supported ABIs of the device.");
            }
        }
    }

    @Override // com.android.server.pm.PackageAbiHelper
    public com.android.server.pm.PackageAbiHelper.NativeLibraryPaths deriveNativeLibraryPaths(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, boolean z2, java.io.File file) {
        return deriveNativeLibraryPaths(new com.android.server.pm.PackageAbiHelper.Abis(com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawPrimaryCpuAbi(androidPackage), com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawSecondaryCpuAbi(androidPackage)), file, androidPackage.getPath(), androidPackage.getBaseApkPath(), z, z2);
    }

    private static com.android.server.pm.PackageAbiHelper.NativeLibraryPaths deriveNativeLibraryPaths(com.android.server.pm.PackageAbiHelper.Abis abis, java.io.File file, java.lang.String str, java.lang.String str2, boolean z, boolean z2) {
        java.lang.String absolutePath;
        java.lang.String absolutePath2;
        java.io.File file2 = new java.io.File(str);
        boolean z3 = false;
        boolean z4 = z && !z2;
        java.lang.String str3 = null;
        if (android.content.pm.parsing.ApkLiteParseUtils.isApkFile(file2)) {
            if (z4) {
                java.lang.String calculateBundledApkRoot = calculateBundledApkRoot(str2);
                boolean is64BitInstructionSet = dalvik.system.VMRuntime.is64BitInstructionSet(com.android.server.pm.InstructionSets.getPrimaryInstructionSet(abis));
                java.lang.String deriveCodePathName = deriveCodePathName(str);
                absolutePath = android.os.Environment.buildPath(new java.io.File(calculateBundledApkRoot), new java.lang.String[]{is64BitInstructionSet ? "lib64" : "lib", deriveCodePathName}).getAbsolutePath();
                if (abis.secondary != null) {
                    str3 = android.os.Environment.buildPath(new java.io.File(calculateBundledApkRoot), new java.lang.String[]{is64BitInstructionSet ? "lib" : "lib64", deriveCodePathName}).getAbsolutePath();
                }
            } else {
                absolutePath = new java.io.File(file, deriveCodePathName(str)).getAbsolutePath();
            }
            absolutePath2 = absolutePath;
        } else {
            absolutePath = new java.io.File(file2, "lib").getAbsolutePath();
            absolutePath2 = new java.io.File(absolutePath, com.android.server.pm.InstructionSets.getPrimaryInstructionSet(abis)).getAbsolutePath();
            if (abis.secondary != null) {
                str3 = new java.io.File(absolutePath, dalvik.system.VMRuntime.getInstructionSet(abis.secondary)).getAbsolutePath();
                z3 = true;
            } else {
                z3 = true;
            }
        }
        return new com.android.server.pm.PackageAbiHelper.NativeLibraryPaths(absolutePath, z3, absolutePath2, str3);
    }

    @Override // com.android.server.pm.PackageAbiHelper
    public com.android.server.pm.PackageAbiHelper.Abis getBundledAppAbis(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return getBundledAppAbi(androidPackage, calculateBundledApkRoot(androidPackage.getBaseApkPath()), deriveCodePathName(androidPackage.getPath()));
    }

    private com.android.server.pm.PackageAbiHelper.Abis getBundledAppAbi(com.android.server.pm.pkg.AndroidPackage androidPackage, java.lang.String str, java.lang.String str2) {
        boolean z;
        boolean z2;
        java.lang.String str3;
        java.io.File file = new java.io.File(androidPackage.getPath());
        if (android.content.pm.parsing.ApkLiteParseUtils.isApkFile(file)) {
            z = new java.io.File(str, new java.io.File("lib64", str2).getPath()).exists();
            z2 = new java.io.File(str, new java.io.File("lib", str2).getPath()).exists();
        } else {
            java.io.File file2 = new java.io.File(file, "lib");
            if (!com.android.internal.util.ArrayUtils.isEmpty(android.os.Build.SUPPORTED_64_BIT_ABIS) && !android.text.TextUtils.isEmpty(android.os.Build.SUPPORTED_64_BIT_ABIS[0])) {
                z = new java.io.File(file2, dalvik.system.VMRuntime.getInstructionSet(android.os.Build.SUPPORTED_64_BIT_ABIS[0])).exists();
            } else {
                z = false;
            }
            if (!com.android.internal.util.ArrayUtils.isEmpty(android.os.Build.SUPPORTED_32_BIT_ABIS) && !android.text.TextUtils.isEmpty(android.os.Build.SUPPORTED_32_BIT_ABIS[0])) {
                z2 = new java.io.File(file2, dalvik.system.VMRuntime.getInstructionSet(android.os.Build.SUPPORTED_32_BIT_ABIS[0])).exists();
            } else {
                z2 = false;
            }
        }
        java.lang.String str4 = null;
        if (z && !z2) {
            str4 = android.os.Build.SUPPORTED_64_BIT_ABIS[0];
            str3 = null;
        } else if (z2 && !z) {
            str4 = android.os.Build.SUPPORTED_32_BIT_ABIS[0];
            str3 = null;
        } else if (z2 && z) {
            if (!androidPackage.isMultiArch()) {
                android.util.Slog.e("PackageManager", "Package " + androidPackage + " has multiple bundled libs, but is not multiarch.");
            }
            if (dalvik.system.VMRuntime.is64BitInstructionSet(com.android.server.pm.InstructionSets.getPreferredInstructionSet())) {
                str4 = android.os.Build.SUPPORTED_64_BIT_ABIS[0];
                str3 = android.os.Build.SUPPORTED_32_BIT_ABIS[0];
            } else {
                str4 = android.os.Build.SUPPORTED_32_BIT_ABIS[0];
                str3 = android.os.Build.SUPPORTED_64_BIT_ABIS[0];
            }
        } else {
            str3 = null;
        }
        return new com.android.server.pm.PackageAbiHelper.Abis(str4, str3);
    }

    @android.annotation.NonNull
    private static java.lang.String[] getNativelySupportedAbis(@android.annotation.NonNull java.lang.String[] strArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : strArr) {
            if (android.text.TextUtils.isEmpty(android.os.SystemProperties.get("ro.dalvik.vm.isa." + dalvik.system.VMRuntime.getInstructionSet(str)))) {
                arrayList.add(str);
            }
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
    }

    private static java.lang.String[] getNativelySupported32BitAbis() {
        if (sNativelySupported32BitAbis != null) {
            return sNativelySupported32BitAbis;
        }
        sNativelySupported32BitAbis = getNativelySupportedAbis(android.os.Build.SUPPORTED_32_BIT_ABIS);
        return sNativelySupported32BitAbis;
    }

    private static java.lang.String[] getNativelySupported64BitAbis() {
        if (sNativelySupported64BitAbis != null) {
            return sNativelySupported64BitAbis;
        }
        sNativelySupported64BitAbis = getNativelySupportedAbis(android.os.Build.SUPPORTED_64_BIT_ABIS);
        return sNativelySupported64BitAbis;
    }

    @Override // com.android.server.pm.PackageAbiHelper
    public android.util.Pair<com.android.server.pm.PackageAbiHelper.Abis, com.android.server.pm.PackageAbiHelper.NativeLibraryPaths> derivePackageAbi(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, boolean z2, java.lang.String str, java.io.File file) throws com.android.server.pm.PackageManagerException {
        java.lang.AutoCloseable autoCloseable;
        java.lang.String str2;
        java.lang.AutoCloseable autoCloseable2;
        java.lang.String str3;
        java.lang.String str4;
        boolean z3;
        long j;
        int findSupportedAbi;
        int i;
        int i2;
        int i3;
        long j2;
        int findSupportedAbi2;
        com.android.server.pm.PackageAbiHelper.NativeLibraryPaths deriveNativeLibraryPaths = deriveNativeLibraryPaths(new com.android.server.pm.PackageAbiHelper.Abis(com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawPrimaryCpuAbi(androidPackage), com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawSecondaryCpuAbi(androidPackage)), file, androidPackage.getPath(), androidPackage.getBaseApkPath(), z, z2);
        boolean shouldExtractLibs = shouldExtractLibs(androidPackage, z, z2);
        java.lang.String str5 = deriveNativeLibraryPaths.nativeLibraryRootDir;
        boolean z4 = deriveNativeLibraryPaths.nativeLibraryRootRequiresIsa;
        boolean isIncrementalPath = android.os.incremental.IncrementalManager.isIncrementalPath(androidPackage.getPath());
        try {
            autoCloseable2 = com.android.server.pm.parsing.pkg.AndroidPackageUtils.createNativeLibraryHandle(androidPackage);
            try {
                try {
                    java.io.File file2 = new java.io.File(str5);
                    if (androidPackage.isMultiArch()) {
                        boolean z5 = android.content.pm.Flags.forceMultiArchNativeLibsMatch() && androidPackage.getTargetSdkVersion() >= 10000 && str == null;
                        java.lang.String[] nativelySupported32BitAbis = z5 ? getNativelySupported32BitAbis() : android.os.Build.SUPPORTED_32_BIT_ABIS;
                        java.lang.String[] nativelySupported64BitAbis = z5 ? getNativelySupported64BitAbis() : android.os.Build.SUPPORTED_64_BIT_ABIS;
                        boolean z6 = nativelySupported32BitAbis.length > 0;
                        boolean z7 = nativelySupported64BitAbis.length > 0;
                        if (z6) {
                            if (shouldExtractLibs) {
                                android.os.Trace.traceBegin(262144L, "copyNativeBinaries");
                                findSupportedAbi2 = com.android.internal.content.NativeLibraryHelper.copyNativeBinariesForSupportedAbi(autoCloseable2, file2, nativelySupported32BitAbis, z4, isIncrementalPath);
                            } else {
                                android.os.Trace.traceBegin(262144L, "findSupportedAbi");
                                findSupportedAbi2 = com.android.internal.content.NativeLibraryHelper.findSupportedAbi(autoCloseable2, nativelySupported32BitAbis);
                            }
                            android.os.Trace.traceEnd(262144L);
                            i = findSupportedAbi2;
                        } else {
                            i = -114;
                        }
                        if (i >= 0 && com.android.server.pm.parsing.pkg.AndroidPackageUtils.isLibrary(androidPackage) && shouldExtractLibs) {
                            throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Shared library native lib extraction not supported");
                        }
                        maybeThrowExceptionForMultiArchCopy("Error unpackaging 32 bit native libs for multiarch app.", i, z5 && z6);
                        if (z7) {
                            if (shouldExtractLibs) {
                                i2 = i;
                                android.os.Trace.traceBegin(262144L, "copyNativeBinaries");
                                i3 = com.android.internal.content.NativeLibraryHelper.copyNativeBinariesForSupportedAbi(autoCloseable2, file2, nativelySupported64BitAbis, z4, isIncrementalPath);
                                j2 = 262144;
                            } else {
                                i2 = i;
                                j2 = 262144;
                                android.os.Trace.traceBegin(262144L, "findSupportedAbi");
                                i3 = com.android.internal.content.NativeLibraryHelper.findSupportedAbi(autoCloseable2, nativelySupported64BitAbis);
                            }
                            android.os.Trace.traceEnd(j2);
                        } else {
                            i2 = i;
                            i3 = -114;
                        }
                        maybeThrowExceptionForMultiArchCopy("Error unpackaging 64 bit native libs for multiarch app.", i3, z5 && z7);
                        if (i3 < 0) {
                            str2 = null;
                        } else {
                            if (shouldExtractLibs && com.android.server.pm.parsing.pkg.AndroidPackageUtils.isLibrary(androidPackage)) {
                                throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Shared library native lib extraction not supported");
                            }
                            str2 = nativelySupported64BitAbis[i3];
                        }
                        if (i2 >= 0) {
                            try {
                                str3 = nativelySupported32BitAbis[i2];
                                if (i3 < 0) {
                                    str4 = null;
                                } else if (androidPackage.is32BitAbiPreferred()) {
                                    str4 = str2;
                                } else {
                                    str4 = str3;
                                    str3 = str2;
                                }
                            } catch (java.io.IOException e) {
                                e = e;
                                android.util.Slog.e("PackageManager", "Unable to get canonical file " + e.toString());
                                libcore.io.IoUtils.closeQuietly(autoCloseable2);
                                str3 = str2;
                                str4 = null;
                                com.android.server.pm.PackageAbiHelper.Abis abis = new com.android.server.pm.PackageAbiHelper.Abis(str3, str4);
                                return new android.util.Pair<>(abis, deriveNativeLibraryPaths(abis, file, androidPackage.getPath(), androidPackage.getBaseApkPath(), z, z2));
                            }
                        } else {
                            str3 = str2;
                            str4 = null;
                        }
                    } else {
                        java.lang.String[] strArr = str != null ? new java.lang.String[]{str} : android.os.Build.SUPPORTED_ABIS;
                        if (android.os.Build.SUPPORTED_64_BIT_ABIS.length <= 0 || str != null || !com.android.internal.content.NativeLibraryHelper.hasRenderscriptBitcode(autoCloseable2)) {
                            z3 = false;
                        } else {
                            if (android.os.Build.SUPPORTED_32_BIT_ABIS.length <= 0) {
                                throw new com.android.server.pm.PackageManagerException(-16, "Apps that contain RenderScript with target API level < 21 are not supported on 64-bit only platforms");
                            }
                            strArr = android.os.Build.SUPPORTED_32_BIT_ABIS;
                            z3 = true;
                        }
                        if (shouldExtractLibs) {
                            android.os.Trace.traceBegin(262144L, "copyNativeBinaries");
                            findSupportedAbi = com.android.internal.content.NativeLibraryHelper.copyNativeBinariesForSupportedAbi(autoCloseable2, file2, strArr, z4, isIncrementalPath);
                            j = 262144;
                        } else {
                            j = 262144;
                            android.os.Trace.traceBegin(262144L, "findSupportedAbi");
                            findSupportedAbi = com.android.internal.content.NativeLibraryHelper.findSupportedAbi(autoCloseable2, strArr);
                        }
                        android.os.Trace.traceEnd(j);
                        if (findSupportedAbi < 0 && findSupportedAbi != -114) {
                            throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Error unpackaging native libs for app, errorCode=" + findSupportedAbi);
                        }
                        if (findSupportedAbi >= 0) {
                            if (com.android.server.pm.parsing.pkg.AndroidPackageUtils.isLibrary(androidPackage)) {
                                throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Shared library with native libs must be multiarch");
                            }
                            str3 = strArr[findSupportedAbi];
                            str4 = null;
                        } else if (findSupportedAbi == -114 && str != null) {
                            str3 = str;
                            str4 = null;
                        } else if (z3) {
                            str3 = strArr[0];
                            str4 = null;
                        } else {
                            str3 = null;
                            str4 = null;
                        }
                    }
                    libcore.io.IoUtils.closeQuietly(autoCloseable2);
                } catch (java.lang.Throwable th) {
                    th = th;
                    autoCloseable = autoCloseable2;
                    libcore.io.IoUtils.closeQuietly(autoCloseable);
                    throw th;
                }
            } catch (java.io.IOException e2) {
                e = e2;
                str2 = null;
            }
        } catch (java.io.IOException e3) {
            e = e3;
            str2 = null;
            autoCloseable2 = null;
        } catch (java.lang.Throwable th2) {
            th = th2;
            autoCloseable = null;
        }
        com.android.server.pm.PackageAbiHelper.Abis abis2 = new com.android.server.pm.PackageAbiHelper.Abis(str3, str4);
        return new android.util.Pair<>(abis2, deriveNativeLibraryPaths(abis2, file, androidPackage.getPath(), androidPackage.getBaseApkPath(), z, z2));
    }

    private boolean shouldExtractLibs(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, boolean z2) {
        boolean z3 = !com.android.server.pm.parsing.pkg.AndroidPackageUtils.isLibrary(androidPackage) && androidPackage.isExtractNativeLibrariesRequested();
        if (!z || z2) {
            return z3;
        }
        return false;
    }

    @Override // com.android.server.pm.PackageAbiHelper
    @android.annotation.Nullable
    public java.lang.String getAdjustedAbiForSharedUser(android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> arraySet, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        java.lang.String str;
        java.lang.String rawPrimaryCpuAbi;
        if (androidPackage != null && (rawPrimaryCpuAbi = com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawPrimaryCpuAbi(androidPackage)) != null) {
            str = dalvik.system.VMRuntime.getInstructionSet(rawPrimaryCpuAbi);
        } else {
            str = null;
        }
        java.util.Iterator<? extends com.android.server.pm.pkg.PackageStateInternal> it = arraySet.iterator();
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = null;
        while (it.hasNext()) {
            com.android.server.pm.pkg.PackageStateInternal next = it.next();
            if (androidPackage == null || !androidPackage.getPackageName().equals(next.getPackageName())) {
                if (next.getPrimaryCpuAbiLegacy() != null) {
                    java.lang.String instructionSet = dalvik.system.VMRuntime.getInstructionSet(next.getPrimaryCpuAbiLegacy());
                    if (str != null && !str.equals(instructionSet)) {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append("Instruction set mismatch, ");
                        sb.append(packageStateInternal == null ? "[caller]" : packageStateInternal);
                        sb.append(" requires ");
                        sb.append(str);
                        sb.append(" whereas ");
                        sb.append(next);
                        sb.append(" requires ");
                        sb.append(instructionSet);
                        android.util.Slog.w("PackageManager", sb.toString());
                    }
                    if (str == null) {
                        packageStateInternal = next;
                        str = instructionSet;
                    }
                }
            }
        }
        if (str == null) {
            return null;
        }
        if (packageStateInternal != null) {
            return packageStateInternal.getPrimaryCpuAbiLegacy();
        }
        return com.android.server.pm.parsing.pkg.AndroidPackageUtils.getRawPrimaryCpuAbi(androidPackage);
    }
}
