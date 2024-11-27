package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageManagerServiceUtils {
    private static final boolean DEFAULT_PACKAGE_PARSER_CACHE_ENABLED = true;
    private static final long ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS = 161252188;
    private static final boolean FORCE_PACKAGE_PARSED_CACHE_ENABLED = false;
    private static final int FSVERITY_DISABLED = 0;
    private static final int FSVERITY_ENABLED = 2;
    private static final long MAX_CRITICAL_INFO_DUMP_SIZE = 3000000;
    public static final int SHARED_USER_ID_JOIN_TYPE_INSTALL = 0;
    public static final int SHARED_USER_ID_JOIN_TYPE_SYSTEM = 2;
    public static final int SHARED_USER_ID_JOIN_TYPE_UPDATE = 1;
    private static final boolean DEBUG = android.os.Build.IS_DEBUGGABLE;
    public static final java.util.function.Predicate<com.android.server.pm.pkg.PackageStateInternal> REMOVE_IF_APEX_PKG = new java.util.function.Predicate() { // from class: com.android.server.pm.PackageManagerServiceUtils$$ExternalSyntheticLambda0
        @Override // java.util.function.Predicate
        public final boolean test(java.lang.Object obj) {
            boolean lambda$static$0;
            lambda$static$0 = com.android.server.pm.PackageManagerServiceUtils.lambda$static$0((com.android.server.pm.pkg.PackageStateInternal) obj);
            return lambda$static$0;
        }
    };
    public static final java.util.function.Predicate<com.android.server.pm.pkg.PackageStateInternal> REMOVE_IF_NULL_PKG = new java.util.function.Predicate() { // from class: com.android.server.pm.PackageManagerServiceUtils$$ExternalSyntheticLambda1
        @Override // java.util.function.Predicate
        public final boolean test(java.lang.Object obj) {
            boolean lambda$static$1;
            lambda$static$1 = com.android.server.pm.PackageManagerServiceUtils.lambda$static$1((com.android.server.pm.pkg.PackageStateInternal) obj);
            return lambda$static$1;
        }
    };
    public static final java.lang.ThreadLocal<java.lang.Boolean> DISABLE_ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: com.android.server.pm.PackageManagerServiceUtils$$ExternalSyntheticLambda2
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            java.lang.Boolean lambda$static$2;
            lambda$static$2 = com.android.server.pm.PackageManagerServiceUtils.lambda$static$2();
            return lambda$static$2;
        }
    });

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SharedUserIdJoinType {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$static$0(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return packageStateInternal.getPkg().isApex();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$static$1(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return packageStateInternal.getPkg() == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$static$2() {
        return false;
    }

    @android.annotation.NonNull
    public static com.android.server.pm.PackageManagerLocal getPackageManagerLocal() {
        try {
            return (com.android.server.pm.PackageManagerLocal) com.android.server.LocalManagerRegistry.getManagerOrThrow(com.android.server.pm.PackageManagerLocal.class);
        } catch (com.android.server.LocalManagerRegistry.ManagerNotFoundException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public static boolean isUnusedSinceTimeInMillis(long j, long j2, long j3, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo, long j4, long j5) {
        boolean z = false;
        if (j2 - j < j3) {
            return false;
        }
        if (j2 - j5 < j3) {
            return false;
        }
        if (j2 - j4 < j3 && packageUseInfo.isAnyCodePathUsedByOtherApps()) {
            z = true;
        }
        return !z;
    }

    public static java.lang.String realpath(java.io.File file) throws java.io.IOException {
        try {
            return android.system.Os.realpath(file.getAbsolutePath());
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public static boolean checkISA(java.lang.String str) {
        for (java.lang.String str2 : android.os.Build.SUPPORTED_ABIS) {
            if (dalvik.system.VMRuntime.getInstructionSet(str2).equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static long getLastModifiedTime(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        java.io.File file = new java.io.File(androidPackage.getPath());
        if (!file.isDirectory()) {
            return file.lastModified();
        }
        long lastModified = new java.io.File(androidPackage.getBaseApkPath()).lastModified();
        for (int length = androidPackage.getSplitCodePaths().length - 1; length >= 0; length--) {
            lastModified = java.lang.Math.max(lastModified, new java.io.File(androidPackage.getSplitCodePaths()[length]).lastModified());
        }
        return lastModified;
    }

    private static java.io.File getSettingsProblemFile() {
        return new java.io.File(new java.io.File(android.os.Environment.getDataDirectory(), "system"), "uiderrors.txt");
    }

    public static void dumpCriticalInfo(android.util.proto.ProtoOutputStream protoOutputStream) {
        java.io.File settingsProblemFile = getSettingsProblemFile();
        long length = settingsProblemFile.length() - MAX_CRITICAL_INFO_DUMP_SIZE;
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(settingsProblemFile));
            if (length > 0) {
                try {
                    bufferedReader.skip(length);
                } finally {
                }
            }
            while (true) {
                java.lang.String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    if (!readLine.contains("ignored: updated version")) {
                        protoOutputStream.write(2237677961223L, readLine);
                    }
                } else {
                    bufferedReader.close();
                    return;
                }
            }
        } catch (java.io.IOException e) {
        }
    }

    public static void dumpCriticalInfo(java.io.PrintWriter printWriter, java.lang.String str) {
        java.io.File settingsProblemFile = getSettingsProblemFile();
        long length = settingsProblemFile.length() - MAX_CRITICAL_INFO_DUMP_SIZE;
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(settingsProblemFile));
            if (length > 0) {
                try {
                    bufferedReader.skip(length);
                } finally {
                }
            }
            while (true) {
                java.lang.String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    if (!readLine.contains("ignored: updated version")) {
                        if (str != null) {
                            printWriter.print(str);
                        }
                        printWriter.println(readLine);
                    }
                } else {
                    bufferedReader.close();
                    return;
                }
            }
        } catch (java.io.IOException e) {
        }
    }

    public static void logCriticalInfo(int i, java.lang.String str) {
        android.util.Slog.println(i, "PackageManager", str);
        com.android.server.EventLogTags.writePmCriticalInfo(str);
        try {
            java.io.File settingsProblemFile = getSettingsProblemFile();
            com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(settingsProblemFile, true));
            fastPrintWriter.println(new java.text.SimpleDateFormat().format(new java.util.Date(java.lang.System.currentTimeMillis())) + ": " + str);
            fastPrintWriter.close();
            android.os.FileUtils.setPermissions(settingsProblemFile.toString(), 508, -1, -1);
        } catch (java.io.IOException e) {
        }
    }

    public static void enforceShellRestriction(com.android.server.pm.UserManagerInternal userManagerInternal, java.lang.String str, int i, int i2) {
        if (i == 2000) {
            if (i2 >= 0 && userManagerInternal.hasUserRestriction(str, i2)) {
                throw new java.lang.SecurityException("Shell does not have permission to access user " + i2);
            }
            if (i2 < 0) {
                android.util.Slog.e("PackageManager", "Unable to check shell permission for user " + i2 + "\n\t" + android.os.Debug.getCallers(3));
            }
        }
    }

    public static void enforceSystemOrPhoneCaller(java.lang.String str, int i) {
        if (i != 1001 && i != 1000) {
            throw new java.lang.SecurityException("Cannot call " + str + " from UID " + i);
        }
    }

    public static java.lang.String deriveAbiOverride(java.lang.String str) {
        if ("-".equals(str)) {
            return null;
        }
        return str;
    }

    public static int compareSignatures(android.content.pm.SigningDetails signingDetails, android.content.pm.SigningDetails signingDetails2) {
        return compareSignatureArrays(signingDetails.getSignatures(), signingDetails2.getSignatures());
    }

    static int compareSignatureArrays(android.content.pm.Signature[] signatureArr, android.content.pm.Signature[] signatureArr2) {
        if (signatureArr == null) {
            if (signatureArr2 == null) {
                return 1;
            }
            return -1;
        }
        if (signatureArr2 == null) {
            return -2;
        }
        if (signatureArr.length != signatureArr2.length) {
            return -3;
        }
        if (signatureArr.length == 1) {
            return signatureArr[0].equals(signatureArr2[0]) ? 0 : -3;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.content.pm.Signature signature : signatureArr) {
            arraySet.add(signature);
        }
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        for (android.content.pm.Signature signature2 : signatureArr2) {
            arraySet2.add(signature2);
        }
        return arraySet.equals(arraySet2) ? 0 : -3;
    }

    public static boolean comparePackageSignatures(com.android.server.pm.PackageSetting packageSetting, android.content.pm.SigningDetails signingDetails) {
        android.content.pm.SigningDetails signingDetails2 = packageSetting.getSigningDetails();
        return signingDetails2 == android.content.pm.SigningDetails.UNKNOWN || compareSignatures(signingDetails2, signingDetails) == 0;
    }

    private static boolean matchSignaturesCompat(java.lang.String str, com.android.server.pm.PackageSignatures packageSignatures, android.content.pm.SigningDetails signingDetails) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.content.pm.Signature signature : packageSignatures.mSigningDetails.getSignatures()) {
            arraySet.add(signature);
        }
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        for (android.content.pm.Signature signature2 : signingDetails.getSignatures()) {
            try {
                for (android.content.pm.Signature signature3 : signature2.getChainSignatures()) {
                    arraySet2.add(signature3);
                }
            } catch (java.security.cert.CertificateEncodingException e) {
                arraySet2.add(signature2);
            }
        }
        if (arraySet2.equals(arraySet)) {
            packageSignatures.mSigningDetails = signingDetails;
            return true;
        }
        if (signingDetails.hasPastSigningCertificates()) {
            logCriticalInfo(4, "Existing package " + str + " has flattened signing certificate chain. Unable to install newer version with rotated signing certificate.");
        }
        return false;
    }

    private static boolean matchSignaturesRecover(java.lang.String str, android.content.pm.SigningDetails signingDetails, android.content.pm.SigningDetails signingDetails2, @android.content.pm.SigningDetails.CertCapabilities int i) {
        java.lang.String message;
        try {
        } catch (java.security.cert.CertificateException e) {
            message = e.getMessage();
        }
        if (signingDetails2.checkCapabilityRecover(signingDetails, i)) {
            logCriticalInfo(4, "Recovered effectively matching certificates for " + str);
            return true;
        }
        message = null;
        logCriticalInfo(4, "Failed to recover certificates for " + str + ": " + message);
        return false;
    }

    private static boolean matchSignatureInSystem(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.SigningDetails signingDetails, com.android.server.pm.PackageSetting packageSetting) {
        if (signingDetails.checkCapability(packageSetting.getSigningDetails(), 1) || packageSetting.getSigningDetails().checkCapability(signingDetails, 8)) {
            return true;
        }
        logCriticalInfo(6, "Updated system app mismatches cert on /system: " + str);
        return false;
    }

    static boolean isApkVerityEnabled() {
        if (android.security.Flags.deprecateFsvSig()) {
            return false;
        }
        return android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT >= 30 || android.os.SystemProperties.getInt("ro.apk_verity.mode", 0) == 2;
    }

    public static boolean verifySignatures(com.android.server.pm.PackageSetting packageSetting, @android.annotation.Nullable com.android.server.pm.SharedUserSetting sharedUserSetting, com.android.server.pm.PackageSetting packageSetting2, android.content.pm.SigningDetails signingDetails, boolean z, boolean z2, boolean z3) throws com.android.server.pm.PackageManagerException {
        boolean z4;
        java.lang.String packageName = packageSetting.getPackageName();
        boolean z5 = true;
        if (packageSetting.getSigningDetails().getSignatures() == null) {
            z4 = false;
        } else {
            boolean z6 = signingDetails.checkCapability(packageSetting.getSigningDetails(), 1) || packageSetting.getSigningDetails().checkCapability(signingDetails, 8);
            if (android.security.Flags.extendVbChainToUpdatedApk() && z6 && packageSetting2 != null && packageSetting2.getSigningDetails() != android.content.pm.SigningDetails.UNKNOWN) {
                z6 = matchSignatureInSystem(packageName, signingDetails, packageSetting2);
            }
            if (!z6 && z) {
                z6 = matchSignaturesCompat(packageName, packageSetting.getSignatures(), signingDetails);
                z4 = z6;
            } else {
                z4 = false;
            }
            if (!z6 && z2) {
                z6 = matchSignaturesRecover(packageName, packageSetting.getSigningDetails(), signingDetails, 1) || matchSignaturesRecover(packageName, signingDetails, packageSetting.getSigningDetails(), 8);
            }
            if (!z6 && z3) {
                z6 = packageSetting.getSigningDetails().hasAncestorOrSelf(signingDetails);
            }
            if (!z6) {
                throw new com.android.server.pm.PackageManagerException(-7, "Existing package " + packageName + " signatures do not match newer version; ignoring!");
            }
        }
        if (sharedUserSetting != null && sharedUserSetting.getSigningDetails() != android.content.pm.SigningDetails.UNKNOWN) {
            boolean canJoinSharedUserId = canJoinSharedUserId(packageName, signingDetails, sharedUserSetting, packageSetting.getSigningDetails().getSignatures() != null ? 1 : 0);
            if (!canJoinSharedUserId && z) {
                canJoinSharedUserId = matchSignaturesCompat(packageName, sharedUserSetting.signatures, signingDetails);
            }
            if (!canJoinSharedUserId && z2) {
                if (!matchSignaturesRecover(packageName, sharedUserSetting.signatures.mSigningDetails, signingDetails, 2) && !matchSignaturesRecover(packageName, signingDetails, sharedUserSetting.signatures.mSigningDetails, 2)) {
                    z5 = false;
                }
                z4 |= z5;
                canJoinSharedUserId = z5;
            }
            if (!canJoinSharedUserId) {
                throw new com.android.server.pm.PackageManagerException(-8, "Package " + packageName + " has no signatures that match those in shared user " + sharedUserSetting.name + "; ignoring!");
            }
            if (!signingDetails.hasCommonAncestor(sharedUserSetting.signatures.mSigningDetails)) {
                throw new com.android.server.pm.PackageManagerException(-8, "Package " + packageName + " has a signing lineage that diverges from the lineage of the sharedUserId");
            }
        }
        return z4;
    }

    public static boolean canJoinSharedUserId(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.SigningDetails signingDetails, @android.annotation.NonNull com.android.server.pm.SharedUserSetting sharedUserSetting, int i) {
        android.content.pm.SigningDetails signingDetails2 = sharedUserSetting.getSigningDetails();
        boolean z = signingDetails.checkCapability(signingDetails2, 2) || signingDetails2.checkCapability(signingDetails, 2);
        if (z && i != 0) {
            return true;
        }
        if (!z && signingDetails2.hasAncestor(signingDetails)) {
            return i == 2;
        }
        if (!z && signingDetails.hasAncestor(signingDetails2)) {
            return i != 0;
        }
        if (!z) {
            return false;
        }
        android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = sharedUserSetting.getPackageStates();
        if (signingDetails.hasPastSigningCertificates()) {
            java.util.Iterator<? extends com.android.server.pm.pkg.PackageStateInternal> it = packageStates.iterator();
            while (it.hasNext()) {
                com.android.server.pm.pkg.PackageStateInternal next = it.next();
                android.content.pm.SigningDetails signingDetails3 = next.getSigningDetails();
                if (signingDetails.hasAncestor(signingDetails3) && !signingDetails.checkCapability(signingDetails3, 2)) {
                    android.util.Slog.d("PackageManager", "Package " + str + " revoked the sharedUserId capability from the signing key used to sign " + next.getPackageName());
                    return false;
                }
            }
        }
        return true;
    }

    public static int extractNativeBinaries(java.io.File file, java.lang.String str) {
        java.io.File file2 = new java.io.File(file, "lib");
        java.lang.AutoCloseable autoCloseable = null;
        try {
            try {
                java.lang.AutoCloseable create = com.android.internal.content.NativeLibraryHelper.Handle.create(file);
                try {
                    int copyNativeBinariesWithOverride = com.android.internal.content.NativeLibraryHelper.copyNativeBinariesWithOverride(create, file2, (java.lang.String) null, false);
                    libcore.io.IoUtils.closeQuietly(create);
                    return copyNativeBinariesWithOverride;
                } catch (java.io.IOException e) {
                    autoCloseable = create;
                    logCriticalInfo(6, "Failed to extract native libraries; pkg: " + str);
                    libcore.io.IoUtils.closeQuietly(autoCloseable);
                    return android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT;
                } catch (java.lang.Throwable th) {
                    th = th;
                    autoCloseable = create;
                    libcore.io.IoUtils.closeQuietly(autoCloseable);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.io.IOException e2) {
        }
    }

    public static void removeNativeBinariesLI(com.android.server.pm.PackageSetting packageSetting) {
        if (packageSetting != null) {
            com.android.internal.content.NativeLibraryHelper.removeNativeBinariesLI(packageSetting.getLegacyNativeLibraryPath());
        }
    }

    public static void waitForNativeBinariesExtractionForIncremental(android.util.ArraySet<android.os.incremental.IncrementalStorage> arraySet) {
        if (!arraySet.isEmpty()) {
            try {
                com.android.server.Watchdog.getInstance().pauseWatchingCurrentThread("native_lib_extract");
                for (int i = 0; i < arraySet.size(); i++) {
                    ((android.os.incremental.IncrementalStorage) arraySet.valueAtUnchecked(i)).waitForNativeBinariesExtraction();
                }
            } finally {
                com.android.server.Watchdog.getInstance().resumeWatchingCurrentThread("native_lib_extract");
            }
        }
    }

    public static int decompressFiles(java.lang.String str, java.io.File file, java.lang.String str2) {
        java.io.File[] compressedFiles = getCompressedFiles(str);
        int i = 1;
        try {
            makeDirRecursive(file, 493);
            int i2 = 1;
            for (java.io.File file2 : compressedFiles) {
                try {
                    java.lang.String name = file2.getName();
                    java.lang.String substring = name.substring(0, name.length() - com.android.server.pm.PackageManagerService.COMPRESSED_EXTENSION.length());
                    i2 = decompressFile(file2, new java.io.File(file, substring));
                    if (i2 != 1) {
                        logCriticalInfo(6, "Failed to decompress; pkg: " + str2 + ", file: " + substring);
                        return i2;
                    }
                } catch (android.system.ErrnoException e) {
                    e = e;
                    i = i2;
                    logCriticalInfo(6, "Failed to decompress; pkg: " + str2 + ", err: " + e.errno);
                    return i;
                }
            }
            return i2;
        } catch (android.system.ErrnoException e2) {
            e = e2;
        }
    }

    public static int decompressFile(java.io.File file, java.io.File file2) throws android.system.ErrnoException {
        if (com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION) {
            android.util.Slog.i("PackageManager", "Decompress file; src: " + file.getAbsolutePath() + ", dst: " + file2.getAbsolutePath());
        }
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(file2);
        java.io.FileOutputStream fileOutputStream = null;
        try {
            java.util.zip.GZIPInputStream gZIPInputStream = new java.util.zip.GZIPInputStream(new java.io.FileInputStream(file));
            try {
                fileOutputStream = atomicFile.startWrite();
                android.os.FileUtils.copy(gZIPInputStream, fileOutputStream);
                fileOutputStream.flush();
                android.system.Os.fchmod(fileOutputStream.getFD(), com.android.internal.util.FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                atomicFile.finishWrite(fileOutputStream);
                gZIPInputStream.close();
                return 1;
            } finally {
            }
        } catch (java.io.IOException e) {
            logCriticalInfo(6, "Failed to decompress file; src: " + file.getAbsolutePath() + ", dst: " + file2.getAbsolutePath());
            atomicFile.failWrite(fileOutputStream);
            return android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT;
        }
    }

    public static java.io.File[] getCompressedFiles(java.lang.String str) {
        java.io.File file = new java.io.File(str);
        java.lang.String name = file.getName();
        int lastIndexOf = name.lastIndexOf(com.android.server.pm.PackageManagerService.STUB_SUFFIX);
        if (lastIndexOf < 0 || name.length() != com.android.server.pm.PackageManagerService.STUB_SUFFIX.length() + lastIndexOf) {
            return null;
        }
        java.io.File parentFile = file.getParentFile();
        if (parentFile == null) {
            android.util.Slog.e("PackageManager", "Unable to determine stub parent dir for codePath: " + str);
            return null;
        }
        java.io.File[] listFiles = new java.io.File(parentFile, name.substring(0, lastIndexOf)).listFiles(new java.io.FilenameFilter() { // from class: com.android.server.pm.PackageManagerServiceUtils.1
            @Override // java.io.FilenameFilter
            public boolean accept(java.io.File file2, java.lang.String str2) {
                return str2.toLowerCase().endsWith(com.android.server.pm.PackageManagerService.COMPRESSED_EXTENSION);
            }
        });
        if (com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION && listFiles != null && listFiles.length > 0) {
            android.util.Slog.i("PackageManager", "getCompressedFiles[" + str + "]: " + java.util.Arrays.toString(listFiles));
        }
        return listFiles;
    }

    public static boolean compressedFileExists(java.lang.String str) {
        java.io.File[] compressedFiles = getCompressedFiles(str);
        return compressedFiles != null && compressedFiles.length > 0;
    }

    public static android.content.pm.PackageInfoLite getMinimalPackageInfo(android.content.Context context, android.content.pm.parsing.PackageLite packageLite, java.lang.String str, int i, java.lang.String str2) {
        long calculateInstalledSize;
        android.content.pm.PackageInfoLite packageInfoLite = new android.content.pm.PackageInfoLite();
        if (str == null || packageLite == null) {
            android.util.Slog.i("PackageManager", "Invalid package file " + str);
            packageInfoLite.recommendedInstallLocation = -2;
            return packageInfoLite;
        }
        java.io.File file = new java.io.File(str);
        if (!com.android.server.pm.PackageInstallerSession.isArchivedInstallation(i)) {
            try {
                calculateInstalledSize = com.android.internal.content.InstallLocationUtils.calculateInstalledSize(packageLite, str2);
            } catch (java.io.IOException e) {
                if (!file.exists()) {
                    packageInfoLite.recommendedInstallLocation = -6;
                } else {
                    packageInfoLite.recommendedInstallLocation = -2;
                }
                return packageInfoLite;
            }
        } else {
            calculateInstalledSize = 0;
        }
        android.content.pm.PackageInstaller.SessionParams sessionParams = new android.content.pm.PackageInstaller.SessionParams(-1);
        sessionParams.appPackageName = packageLite.getPackageName();
        sessionParams.installLocation = packageLite.getInstallLocation();
        sessionParams.sizeBytes = calculateInstalledSize;
        sessionParams.installFlags = i;
        try {
            int resolveInstallLocation = com.android.internal.content.InstallLocationUtils.resolveInstallLocation(context, sessionParams);
            packageInfoLite.packageName = packageLite.getPackageName();
            packageInfoLite.splitNames = packageLite.getSplitNames();
            packageInfoLite.versionCode = packageLite.getVersionCode();
            packageInfoLite.versionCodeMajor = packageLite.getVersionCodeMajor();
            packageInfoLite.baseRevisionCode = packageLite.getBaseRevisionCode();
            packageInfoLite.splitRevisionCodes = packageLite.getSplitRevisionCodes();
            packageInfoLite.installLocation = packageLite.getInstallLocation();
            packageInfoLite.verifiers = packageLite.getVerifiers();
            packageInfoLite.recommendedInstallLocation = resolveInstallLocation;
            packageInfoLite.multiArch = packageLite.isMultiArch();
            packageInfoLite.debuggable = packageLite.isDebuggable();
            packageInfoLite.isSdkLibrary = packageLite.isIsSdkLibrary();
            return packageInfoLite;
        } catch (java.io.IOException e2) {
            throw new java.lang.IllegalStateException(e2);
        }
    }

    public static long calculateInstalledSize(java.lang.String str, java.lang.String str2) {
        try {
            android.content.pm.parsing.result.ParseResult parsePackageLite = android.content.pm.parsing.ApkLiteParseUtils.parsePackageLite(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing().reset(), new java.io.File(str), 0);
            if (parsePackageLite.isError()) {
                throw new com.android.server.pm.PackageManagerException(parsePackageLite.getErrorCode(), parsePackageLite.getErrorMessage(), parsePackageLite.getException());
            }
            return com.android.internal.content.InstallLocationUtils.calculateInstalledSize((android.content.pm.parsing.PackageLite) parsePackageLite.getResult(), str2);
        } catch (com.android.server.pm.PackageManagerException | java.io.IOException e) {
            android.util.Slog.w("PackageManager", "Failed to calculate installed size: " + e);
            return -1L;
        }
    }

    public static boolean isDowngradePermitted(int i, boolean z) {
        if ((i & 128) != 0) {
            return (android.os.Build.IS_DEBUGGABLE || z) || (i & 1048576) != 0;
        }
        return false;
    }

    public static int copyPackage(java.lang.String str, java.io.File file) {
        if (str == null) {
            return -3;
        }
        try {
            android.content.pm.parsing.result.ParseResult parsePackageLite = android.content.pm.parsing.ApkLiteParseUtils.parsePackageLite(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing().reset(), new java.io.File(str), 0);
            if (parsePackageLite.isError()) {
                android.util.Slog.w("PackageManager", "Failed to parse package at " + str);
                return parsePackageLite.getErrorCode();
            }
            android.content.pm.parsing.PackageLite packageLite = (android.content.pm.parsing.PackageLite) parsePackageLite.getResult();
            copyFile(packageLite.getBaseApkPath(), file, "base.apk");
            if (!com.android.internal.util.ArrayUtils.isEmpty(packageLite.getSplitNames())) {
                for (int i = 0; i < packageLite.getSplitNames().length; i++) {
                    copyFile(packageLite.getSplitApkPaths()[i], file, "split_" + packageLite.getSplitNames()[i] + ".apk");
                }
                return 1;
            }
            return 1;
        } catch (android.system.ErrnoException | java.io.IOException e) {
            android.util.Slog.w("PackageManager", "Failed to copy package at " + str + ": " + e);
            return -4;
        }
    }

    private static void copyFile(java.lang.String str, java.io.File file, java.lang.String str2) throws android.system.ErrnoException, java.io.IOException {
        java.io.FileInputStream fileInputStream;
        if (!android.os.FileUtils.isValidExtFilename(str2)) {
            throw new java.lang.IllegalArgumentException("Invalid filename: " + str2);
        }
        android.util.Slog.d("PackageManager", "Copying " + str + " to " + str2);
        java.io.File file2 = new java.io.File(file, str2);
        java.io.FileDescriptor open = android.system.Os.open(file2.getAbsolutePath(), android.system.OsConstants.O_RDWR | android.system.OsConstants.O_CREAT, com.android.internal.util.FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
        android.system.Os.chmod(file2.getAbsolutePath(), com.android.internal.util.FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
        java.io.FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new java.io.FileInputStream(str);
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            android.os.FileUtils.copy(fileInputStream.getFD(), open);
            libcore.io.IoUtils.closeQuietly(fileInputStream);
        } catch (java.lang.Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            libcore.io.IoUtils.closeQuietly(fileInputStream2);
            throw th;
        }
    }

    public static void makeDirRecursive(java.io.File file, int i) throws android.system.ErrnoException {
        java.nio.file.Path path = file.toPath();
        int nameCount = path.getNameCount();
        for (int i2 = 1; i2 <= nameCount; i2++) {
            java.io.File file2 = path.subpath(0, i2).toFile();
            if (!file2.exists()) {
                android.system.Os.mkdir(file2.getAbsolutePath(), i);
                android.system.Os.chmod(file2.getAbsolutePath(), i);
            }
        }
    }

    @android.annotation.NonNull
    public static java.lang.String buildVerificationRootHashString(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String[] strArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str.substring(str.lastIndexOf(java.io.File.separator) + 1));
        sb.append(":");
        byte[] rootHash = getRootHash(str);
        if (rootHash == null) {
            sb.append("0");
        } else {
            sb.append(com.android.internal.util.HexDump.toHexString(rootHash));
        }
        if (strArr == null || strArr.length == 0) {
            return sb.toString();
        }
        for (int length = strArr.length - 1; length >= 0; length--) {
            java.lang.String str2 = strArr[length];
            java.lang.String substring = str2.substring(str2.lastIndexOf(java.io.File.separator) + 1);
            byte[] rootHash2 = getRootHash(str2);
            sb.append(";");
            sb.append(substring);
            sb.append(":");
            if (rootHash2 == null) {
                sb.append("0");
            } else {
                sb.append(com.android.internal.util.HexDump.toHexString(rootHash2));
            }
        }
        return sb.toString();
    }

    @android.annotation.Nullable
    private static byte[] getRootHash(java.lang.String str) {
        try {
            byte[] unsafeGetFileSignature = android.os.incremental.IncrementalManager.unsafeGetFileSignature(str);
            if (unsafeGetFileSignature == null) {
                throw new java.io.IOException("File signature not present");
            }
            android.os.incremental.V4Signature readFrom = android.os.incremental.V4Signature.readFrom(unsafeGetFileSignature);
            if (readFrom.hashingInfo == null) {
                throw new java.io.IOException("Hashing info not present");
            }
            android.os.incremental.V4Signature.HashingInfo fromByteArray = android.os.incremental.V4Signature.HashingInfo.fromByteArray(readFrom.hashingInfo);
            if (com.android.internal.util.ArrayUtils.isEmpty(fromByteArray.rawRootHash)) {
                throw new java.io.IOException("Root has not present");
            }
            return com.android.server.pm.ApkChecksums.verityHashForFile(new java.io.File(str), fromByteArray.rawRootHash);
        } catch (java.io.IOException e) {
            android.util.Slog.i("PackageManager", "Could not obtain verity root hash", e);
            return null;
        }
    }

    public static boolean isSystemApp(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return (packageStateInternal.getFlags() & 1) != 0;
    }

    public static boolean isUpdatedSystemApp(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return (packageStateInternal.getFlags() & 128) != 0;
    }

    public static void applyEnforceIntentFilterMatching(com.android.server.compat.PlatformCompat platformCompat, com.android.server.pm.resolution.ComponentResolverApi componentResolverApi, java.util.List<android.content.pm.ResolveInfo> list, boolean z, android.content.Intent intent, java.lang.String str, int i) {
        com.android.internal.pm.pkg.component.ParsedActivity service;
        boolean z2;
        if (DISABLE_ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS.get().booleanValue() || android.app.ActivityManager.canAccessUnexportedComponents(i)) {
            return;
        }
        boolean z3 = android.security.Flags.enforceIntentFilterMatch() && platformCompat.isChangeEnabledByUidInternal(ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS, i);
        for (int size = list.size() - 1; size >= 0; size--) {
            android.content.pm.ComponentInfo componentInfo = list.get(size).getComponentInfo();
            if (!android.os.UserHandle.isSameApp(i, componentInfo.applicationInfo.uid)) {
                if (componentInfo instanceof android.content.pm.ActivityInfo) {
                    if (z) {
                        service = componentResolverApi.getReceiver(componentInfo.getComponentName());
                    } else {
                        service = componentResolverApi.getActivity(componentInfo.getComponentName());
                    }
                } else if (componentInfo instanceof android.content.pm.ServiceInfo) {
                    service = componentResolverApi.getService(componentInfo.getComponentName());
                } else {
                    throw new java.lang.IllegalArgumentException("Unsupported component type");
                }
                if (service != null && !service.getIntents().isEmpty()) {
                    int size2 = service.getIntents().size();
                    int i2 = 0;
                    while (true) {
                        if (i2 < size2) {
                            if (!com.android.server.IntentResolver.intentMatchesFilter(((com.android.internal.pm.pkg.component.ParsedIntentInfo) service.getIntents().get(i2)).getIntentFilter(), intent, str)) {
                                i2++;
                            } else {
                                z2 = true;
                                break;
                            }
                        } else {
                            z2 = false;
                            break;
                        }
                    }
                    if (!z2) {
                        com.android.server.am.ActivityManagerUtils.logUnsafeIntentEvent(3, i, intent, str, z3);
                        if (android.security.Flags.enforceIntentFilterMatch()) {
                            intent.addExtendedFlags(1);
                        }
                        if (z3) {
                            android.util.Slog.w("PackageManager", "Intent does not match component's intent filter: " + intent);
                            android.util.Slog.w("PackageManager", "Access blocked: " + service.getComponentName());
                            list.remove(size);
                        }
                    }
                }
            }
        }
    }

    public static boolean hasAnyDomainApproval(@android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationManagerInternal domainVerificationManagerInternal, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.NonNull android.content.Intent intent, long j, int i) {
        return domainVerificationManagerInternal.approvalLevelForDomain(packageStateInternal, intent, j, i) > 0;
    }

    public static android.content.Intent updateIntentForResolve(android.content.Intent intent) {
        if (intent.getSelector() != null) {
            return intent.getSelector();
        }
        return intent;
    }

    public static java.lang.String arrayToString(int[] iArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append('[');
        if (iArr != null) {
            for (int i = 0; i < iArr.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(iArr[i]);
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public static java.io.File getNextCodePath(java.io.File file, java.lang.String str) {
        java.io.File file2;
        java.security.SecureRandom secureRandom = new java.security.SecureRandom();
        byte[] bArr = new byte[16];
        do {
            secureRandom.nextBytes(bArr);
            file2 = new java.io.File(file, "~~" + android.util.Base64.encodeToString(bArr, 10));
        } while (file2.exists());
        secureRandom.nextBytes(bArr);
        java.io.File file3 = new java.io.File(file2, str + '-' + android.util.Base64.encodeToString(bArr, 10));
        if (DEBUG && !java.util.Objects.equals(tryParsePackageName(file3.getName()), str)) {
            throw new java.lang.RuntimeException("codepath is off: " + file3.getName() + " (" + str + ")");
        }
        return file3;
    }

    static java.lang.String tryParsePackageName(@android.annotation.NonNull java.lang.String str) throws java.lang.IllegalArgumentException {
        int indexOf = str.indexOf(45);
        if (indexOf == -1) {
            throw new java.lang.IllegalArgumentException("Not a valid package folder name");
        }
        return str.substring(0, indexOf);
    }

    public static int getPackageExternalStorageType(android.os.storage.VolumeInfo volumeInfo, boolean z) {
        android.os.storage.DiskInfo disk;
        if (volumeInfo != null && (disk = volumeInfo.getDisk()) != null) {
            if (disk.isSd()) {
                return 1;
            }
            if (disk.isUsb()) {
                return 2;
            }
            if (z) {
                return 3;
            }
            return 0;
        }
        return 0;
    }

    public static void enforceSystemOrRootOrShell(java.lang.String str) {
        if (!isSystemOrRootOrShell()) {
            throw new java.lang.SecurityException(str);
        }
    }

    public static boolean isSystemOrRootOrShell() {
        return isSystemOrRootOrShell(android.os.Binder.getCallingUid());
    }

    public static boolean isSystemOrRootOrShell(int i) {
        return i == 1000 || i == 0 || i == 2000;
    }

    public static boolean isSystemOrRoot() {
        return isSystemOrRoot(android.os.Binder.getCallingUid());
    }

    public static boolean isSystemOrRoot(int i) {
        return i == 1000 || i == 0;
    }

    public static boolean isAdoptedShell(int i, android.content.Context context) {
        return i != 1000 && context.checkCallingOrSelfPermission("com.android.permission.USE_SYSTEM_DATA_LOADERS") == 0;
    }

    public static boolean isRootOrShell(int i) {
        return i == 0 || i == 2000;
    }

    public static void enforceSystemOrRoot(java.lang.String str) {
        if (!isSystemOrRoot()) {
            throw new java.lang.SecurityException(str);
        }
    }

    @android.annotation.Nullable
    public static java.io.File preparePackageParserCache(boolean z, boolean z2, java.lang.String str) {
        if (z) {
            return null;
        }
        if (android.os.SystemProperties.getBoolean("pm.boot.disable_package_cache", false)) {
            android.util.Slog.i("PackageManager", "Disabling package parser cache due to system property.");
            return null;
        }
        java.io.File packageCacheDirectory = android.os.Environment.getPackageCacheDirectory();
        if (!android.os.FileUtils.createDir(packageCacheDirectory)) {
            return null;
        }
        java.lang.String str2 = android.content.pm.PackagePartitions.FINGERPRINT;
        for (java.io.File file : android.os.FileUtils.listFilesOrEmpty(packageCacheDirectory)) {
            if (java.util.Objects.equals(str2, file.getName())) {
                android.util.Slog.d("PackageManager", "Keeping known cache " + file.getName());
            } else {
                android.util.Slog.d("PackageManager", "Destroying unknown cache " + file.getName());
                android.os.FileUtils.deleteContentsAndDir(file);
            }
        }
        java.io.File createDir = android.os.FileUtils.createDir(packageCacheDirectory, str2);
        if (createDir == null) {
            android.util.Slog.wtf("PackageManager", "Cache directory cannot be created - wiping base dir " + packageCacheDirectory);
            android.os.FileUtils.deleteContentsAndDir(packageCacheDirectory);
            return null;
        }
        if (z2 && str.startsWith("eng.")) {
            if (createDir.lastModified() < new java.io.File(android.os.Environment.getRootDirectory(), "framework").lastModified()) {
                android.util.Slog.w("PackageManager", "Wiping cache directory because the system partition changed.");
                android.os.FileUtils.deleteContents(packageCacheDirectory);
                return android.os.FileUtils.createDir(packageCacheDirectory, str2);
            }
            return createDir;
        }
        return createDir;
    }

    public static void checkDowngrade(com.android.server.pm.pkg.AndroidPackage androidPackage, android.content.pm.PackageInfoLite packageInfoLite) throws com.android.server.pm.PackageManagerException {
        if (packageInfoLite.getLongVersionCode() < androidPackage.getLongVersionCode()) {
            throw new com.android.server.pm.PackageManagerException(-25, "Update version code " + packageInfoLite.versionCode + " is older than current " + androidPackage.getLongVersionCode());
        }
        if (packageInfoLite.getLongVersionCode() == androidPackage.getLongVersionCode()) {
            if (packageInfoLite.baseRevisionCode < androidPackage.getBaseRevisionCode()) {
                throw new com.android.server.pm.PackageManagerException(-25, "Update base revision code " + packageInfoLite.baseRevisionCode + " is older than current " + androidPackage.getBaseRevisionCode());
            }
            if (!com.android.internal.util.ArrayUtils.isEmpty(packageInfoLite.splitNames)) {
                for (int i = 0; i < packageInfoLite.splitNames.length; i++) {
                    java.lang.String str = packageInfoLite.splitNames[i];
                    int indexOf = com.android.internal.util.ArrayUtils.indexOf(androidPackage.getSplitNames(), str);
                    if (indexOf != -1 && packageInfoLite.splitRevisionCodes[i] < androidPackage.getSplitRevisionCodes()[indexOf]) {
                        throw new com.android.server.pm.PackageManagerException(-25, "Update split " + str + " revision code " + packageInfoLite.splitRevisionCodes[i] + " is older than current " + androidPackage.getSplitRevisionCodes()[indexOf]);
                    }
                }
            }
        }
    }

    public static boolean isInstalledByAdb(java.lang.String str) {
        return str == null || com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME.equals(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0025, code lost:
    
        r6 = new java.io.FileOutputStream(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x002a, code lost:
    
        android.os.FileUtils.copy(r1, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x002d, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003a, code lost:
    
        if (r7.length() <= com.android.server.pm.PackageInstallerSession.getAppMetadataSizeLimit()) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003c, code lost:
    
        r7.delete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0040, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0043, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0048, code lost:
    
        android.system.Os.chmod(r7.getAbsolutePath(), com.android.internal.util.FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0051, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0053, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005c, code lost:
    
        throw r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean extractAppMetadataFromApk(java.lang.String str, java.io.File file) {
        boolean z;
        try {
            java.util.zip.ZipInputStream zipInputStream = new java.util.zip.ZipInputStream(new java.io.FileInputStream(new java.io.File(str)));
            while (true) {
                try {
                    java.util.zip.ZipEntry nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry == null) {
                        z = false;
                        break;
                    }
                    if (nextEntry.getName().equals(com.android.server.pm.PackageManagerService.APP_METADATA_FILE_IN_APK_PATH)) {
                        break;
                    }
                } finally {
                }
            }
            zipInputStream.close();
            return z;
        } catch (java.lang.Exception e) {
            android.util.Slog.e("PackageManager", e.getMessage());
            return false;
        }
    }

    public static void linkFilesToOldDirs(@android.annotation.NonNull com.android.server.pm.Installer installer, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.File file, @android.annotation.Nullable java.util.Set<java.io.File> set) {
        java.io.File[] listFiles;
        if (set == null || set.isEmpty() || android.os.incremental.IncrementalManager.isIncrementalPath(file.getPath()) || (listFiles = file.listFiles()) == null || listFiles.length == 0) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.io.File file2 : listFiles) {
            if (!file2.isDirectory() && file2.toString().endsWith(".apk")) {
                arrayList.add(file2);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        java.io.File[] fileArr = (java.io.File[]) arrayList.toArray(new java.io.File[0]);
        for (java.io.File file3 : set) {
            if (file3.exists()) {
                linkFilesAndSetModes(installer, str, file, file3, fileArr, com.android.internal.util.FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                linkNativeLibraries(installer, str, file, file3, "lib");
                linkNativeLibraries(installer, str, file, file3, "lib64");
            }
        }
    }

    private static void linkNativeLibraries(@android.annotation.NonNull com.android.server.pm.Installer installer, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.io.File file2, @android.annotation.NonNull java.lang.String str2) {
        java.io.File file3 = new java.io.File(file, str2);
        if (!file3.exists()) {
            return;
        }
        java.io.File file4 = new java.io.File(file2, str2);
        if (!file4.exists()) {
            try {
                com.android.internal.content.NativeLibraryHelper.createNativeLibrarySubdir(file4);
            } catch (java.io.IOException e) {
                android.util.Slog.w("PackageManager", "Failed to create native library dir at <" + file4 + ">", e);
                return;
            }
        }
        java.io.File[] listFiles = file3.listFiles();
        if (listFiles == null) {
            return;
        }
        for (java.io.File file5 : listFiles) {
            java.io.File file6 = new java.io.File(file4, file5.getName());
            if (!file6.exists()) {
                try {
                    com.android.internal.content.NativeLibraryHelper.createNativeLibrarySubdir(file6);
                } catch (java.io.IOException e2) {
                    android.util.Slog.w("PackageManager", "Failed to create native library subdir at <" + file6 + ">", e2);
                }
            }
            java.io.File file7 = new java.io.File(file3, file5.getName());
            java.io.File[] listFiles2 = file7.listFiles();
            if (listFiles2 != null && listFiles2.length != 0) {
                linkFilesAndSetModes(installer, str, file7, file6, listFiles2, 493);
            }
        }
    }

    private static void linkFilesAndSetModes(@android.annotation.NonNull com.android.server.pm.Installer installer, java.lang.String str, @android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.io.File file2, @android.annotation.NonNull java.io.File[] fileArr, int i) {
        for (java.io.File file3 : fileArr) {
            java.lang.String name = file3.getName();
            java.io.File file4 = new java.io.File(file, name);
            java.io.File file5 = new java.io.File(file2, name);
            if (file5.exists()) {
                if (DEBUG) {
                    android.util.Slog.d("PackageManager", "Skipping existing linked file <" + file5 + ">");
                }
            } else {
                try {
                    installer.linkFile(str, name, file.getAbsolutePath(), file2.getAbsolutePath());
                    if (DEBUG) {
                        android.util.Slog.d("PackageManager", "Linked <" + file4 + "> to <" + file5 + ">");
                    }
                    try {
                        android.system.Os.chmod(file5.getAbsolutePath(), i);
                        if (!android.os.SELinux.restorecon(file5)) {
                            android.util.Slog.w("PackageManager", "Failed to restorecon for linked file <" + file5 + ">");
                        }
                    } catch (android.system.ErrnoException e) {
                        android.util.Slog.w("PackageManager", "Failed to set mode for linked file <" + file5 + ">", e);
                    }
                } catch (com.android.server.pm.Installer.InstallerException e2) {
                    android.util.Slog.w("PackageManager", "Failed to link native library <" + file4 + "> to <" + file5 + ">", e2);
                }
            }
        }
    }
}
