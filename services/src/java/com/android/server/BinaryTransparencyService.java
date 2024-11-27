package com.android.server;

/* loaded from: classes.dex */
public class BinaryTransparencyService extends com.android.server.SystemService {
    static final java.lang.String APEX_PRELOAD_LOCATION_ERROR = "could-not-be-determined";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String BINARY_HASH_ERROR = "SHA256HashError";
    private static final boolean DEBUG = false;
    static final int DIGEST_ALGORITHM_CHUNKED_SHA256 = 1;
    static final int DIGEST_ALGORITHM_CHUNKED_SHA512 = 2;
    static final int DIGEST_ALGORITHM_SHA256 = 4;
    static final int DIGEST_ALGORITHM_UNKNOWN = 0;
    static final int DIGEST_ALGORITHM_VERITY_CHUNKED_SHA256 = 3;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_ENABLE_BIOMETRIC_PROPERTY_VERIFICATION = "enable_biometric_property_verification";
    public static final long LOG_MBA_INFO = 245692487;
    static final int MBA_STATUS_ERROR = 0;
    static final int MBA_STATUS_NEW_INSTALL = 3;
    static final int MBA_STATUS_PRELOADED = 1;
    static final int MBA_STATUS_UPDATED_NEW_INSTALL = 4;
    static final int MBA_STATUS_UPDATED_PRELOAD = 2;
    static final long RECORD_MEASUREMENTS_COOLDOWN_MS = 86400000;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String SYSPROP_NAME_VBETA_DIGEST = "ro.boot.vbmeta.digest";
    private static final java.lang.String TAG = "TransparencyService";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String VBMETA_DIGEST_UNAVAILABLE = "vbmeta-digest-unavailable";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String VBMETA_DIGEST_UNINITIALIZED = "vbmeta-digest-uninitialized";
    private static final com.android.modules.expresslog.Histogram digestAllPackagesLatency = new com.android.modules.expresslog.Histogram("binary_transparency.value_digest_all_packages_latency_uniform", new com.android.modules.expresslog.Histogram.UniformOptions(50, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 500.0f));
    private com.android.server.BinaryTransparencyService.BiometricLogger mBiometricLogger;
    private final android.content.Context mContext;
    private long mMeasurementsLastRecordedMs;
    private android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final com.android.server.BinaryTransparencyService.BinaryTransparencyServiceImpl mServiceImpl;
    private java.lang.String mVbmetaDigest;

    final class BinaryTransparencyServiceImpl extends com.android.internal.os.IBinaryTransparencyService.Stub {
        BinaryTransparencyServiceImpl() {
        }

        public java.lang.String getSignedImageInfo() {
            return com.android.server.BinaryTransparencyService.this.mVbmetaDigest;
        }

        private java.lang.String[] computePackageSignerSha256Digests(@android.annotation.Nullable android.content.pm.SigningInfo signingInfo) {
            if (signingInfo == null) {
                android.util.Slog.e(com.android.server.BinaryTransparencyService.TAG, "signingInfo is null");
                return null;
            }
            android.content.pm.Signature[] apkContentsSigners = signingInfo.getApkContentsSigners();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.content.pm.Signature signature : apkContentsSigners) {
                arrayList.add(libcore.util.HexEncoding.encodeToString(android.util.PackageUtils.computeSha256DigestBytes(signature.toByteArray()), false));
            }
            return (java.lang.String[]) arrayList.toArray(new java.lang.String[1]);
        }

        @android.annotation.NonNull
        private java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAppInfo(com.android.server.pm.pkg.PackageState packageState, int i) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.lang.String packageName = packageState.getPackageName();
            long versionCode = packageState.getVersionCode();
            java.lang.String[] computePackageSignerSha256Digests = computePackageSignerSha256Digests(packageState.getSigningInfo());
            for (com.android.server.pm.pkg.AndroidPackageSplit androidPackageSplit : packageState.getAndroidPackage().getSplits()) {
                com.android.internal.os.IBinaryTransparencyService.AppInfo appInfo = new com.android.internal.os.IBinaryTransparencyService.AppInfo();
                appInfo.packageName = packageName;
                appInfo.longVersion = versionCode;
                appInfo.splitName = androidPackageSplit.getName();
                appInfo.signerDigests = computePackageSignerSha256Digests;
                appInfo.mbaStatus = i;
                com.android.server.BinaryTransparencyService.Digest measureApk = measureApk(androidPackageSplit.getPath());
                appInfo.digest = measureApk.value();
                appInfo.digestAlgorithm = measureApk.algorithm();
                arrayList.add(appInfo);
            }
            com.android.internal.os.IBinaryTransparencyService.AppInfo appInfo2 = (com.android.internal.os.IBinaryTransparencyService.AppInfo) arrayList.get(0);
            android.content.pm.InstallSourceInfo installSourceInfo = com.android.server.BinaryTransparencyService.this.getInstallSourceInfo(packageState.getPackageName());
            if (installSourceInfo != null) {
                appInfo2.initiator = installSourceInfo.getInitiatingPackageName();
                android.content.pm.SigningInfo initiatingPackageSigningInfo = installSourceInfo.getInitiatingPackageSigningInfo();
                if (initiatingPackageSigningInfo != null) {
                    appInfo2.initiatorSignerDigests = computePackageSignerSha256Digests(initiatingPackageSigningInfo);
                }
                appInfo2.installer = installSourceInfo.getInstallingPackageName();
                appInfo2.originator = installSourceInfo.getOriginatingPackageName();
            }
            return arrayList;
        }

        @android.annotation.Nullable
        private com.android.server.BinaryTransparencyService.Digest measureApk(@android.annotation.NonNull java.lang.String str) {
            java.util.Map<java.lang.Integer, byte[]> computeApkContentDigest = computeApkContentDigest(str);
            if (computeApkContentDigest == null) {
                android.util.Slog.d(com.android.server.BinaryTransparencyService.TAG, "Failed to compute content digest for " + str);
            } else {
                int i = 1;
                if (computeApkContentDigest.containsKey(1)) {
                    return new com.android.server.BinaryTransparencyService.Digest(i, computeApkContentDigest.get(1));
                }
                int i2 = 2;
                if (computeApkContentDigest.containsKey(2)) {
                    return new com.android.server.BinaryTransparencyService.Digest(i2, computeApkContentDigest.get(2));
                }
            }
            return new com.android.server.BinaryTransparencyService.Digest(4, android.util.PackageUtils.computeSha256DigestForLargeFileAsBytes(str, android.util.PackageUtils.createLargeFileBuffer()));
        }

        public void recordMeasurementsForAllPackages() {
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            if (currentTimeMillis - com.android.server.BinaryTransparencyService.this.mMeasurementsLastRecordedMs < 86400000) {
                android.util.Slog.d(com.android.server.BinaryTransparencyService.TAG, "Skip measurement since the last measurement was only taken at " + com.android.server.BinaryTransparencyService.this.mMeasurementsLastRecordedMs + " within the cooldown period");
                return;
            }
            android.util.Slog.d(com.android.server.BinaryTransparencyService.TAG, "Measurement was last taken at " + com.android.server.BinaryTransparencyService.this.mMeasurementsLastRecordedMs + " and is now updated to: " + currentTimeMillis);
            com.android.server.BinaryTransparencyService.this.mMeasurementsLastRecordedMs = currentTimeMillis;
            android.os.Bundle bundle = new android.os.Bundle();
            for (com.android.internal.os.IBinaryTransparencyService.ApexInfo apexInfo : collectAllApexInfo(false)) {
                bundle.putBoolean(apexInfo.packageName, true);
                recordApexInfo(apexInfo);
            }
            for (com.android.internal.os.IBinaryTransparencyService.AppInfo appInfo : collectAllUpdatedPreloadInfo(bundle)) {
                bundle.putBoolean(appInfo.packageName, true);
                writeAppInfoToLog(appInfo);
            }
            if (android.app.compat.CompatChanges.isChangeEnabled(com.android.server.BinaryTransparencyService.LOG_MBA_INFO)) {
                for (com.android.internal.os.IBinaryTransparencyService.AppInfo appInfo2 : collectAllSilentInstalledMbaInfo(bundle)) {
                    bundle.putBoolean(appInfo2.packageName, true);
                    writeAppInfoToLog(appInfo2);
                }
            }
            com.android.server.BinaryTransparencyService.digestAllPackagesLatency.logSample(java.lang.System.currentTimeMillis() - currentTimeMillis);
        }

        public java.util.List<com.android.internal.os.IBinaryTransparencyService.ApexInfo> collectAllApexInfo(boolean z) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.content.pm.PackageInfo packageInfo : com.android.server.BinaryTransparencyService.this.getCurrentInstalledApexs()) {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = com.android.server.BinaryTransparencyService.this.mPackageManagerInternal.getPackageStateInternal(packageInfo.packageName);
                if (packageStateInternal == null) {
                    android.util.Slog.w(com.android.server.BinaryTransparencyService.TAG, "Package state is unavailable, ignoring the APEX " + packageInfo.packageName);
                } else {
                    com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateInternal.getAndroidPackage();
                    if (androidPackage == null) {
                        android.util.Slog.w(com.android.server.BinaryTransparencyService.TAG, "Skipping the missing APK in " + androidPackage.getPath());
                    } else {
                        com.android.server.BinaryTransparencyService.Digest measureApk = measureApk(androidPackage.getPath());
                        if (measureApk == null) {
                            android.util.Slog.w(com.android.server.BinaryTransparencyService.TAG, "Skipping the missing APEX in " + androidPackage.getPath());
                        } else {
                            com.android.internal.os.IBinaryTransparencyService.ApexInfo apexInfo = new com.android.internal.os.IBinaryTransparencyService.ApexInfo();
                            apexInfo.packageName = packageStateInternal.getPackageName();
                            apexInfo.longVersion = packageStateInternal.getVersionCode();
                            apexInfo.digest = measureApk.value();
                            apexInfo.digestAlgorithm = measureApk.algorithm();
                            apexInfo.signerDigests = computePackageSignerSha256Digests(packageStateInternal.getSigningInfo());
                            if (z) {
                                apexInfo.moduleName = com.android.server.BinaryTransparencyService.this.apexPackageNameToModuleName(packageStateInternal.getPackageName());
                            }
                            arrayList.add(apexInfo);
                        }
                    }
                }
            }
            return arrayList;
        }

        public java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllUpdatedPreloadInfo(final android.os.Bundle bundle) {
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            com.android.server.BinaryTransparencyService.this.mContext.getPackageManager();
            com.android.server.BinaryTransparencyService.this.mPackageManagerInternal.forEachPackageState(new java.util.function.Consumer() { // from class: com.android.server.BinaryTransparencyService$BinaryTransparencyServiceImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.BinaryTransparencyService.BinaryTransparencyServiceImpl.this.lambda$collectAllUpdatedPreloadInfo$0(bundle, arrayList, (com.android.server.pm.pkg.PackageStateInternal) obj);
                }
            });
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$collectAllUpdatedPreloadInfo$0(android.os.Bundle bundle, java.util.ArrayList arrayList, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
            if (!packageStateInternal.isUpdatedSystemApp() || bundle.containsKey(packageStateInternal.getPackageName())) {
                return;
            }
            android.util.Slog.d(com.android.server.BinaryTransparencyService.TAG, "Preload " + packageStateInternal.getPackageName() + " at " + packageStateInternal.getPath() + " has likely been updated.");
            arrayList.addAll(collectAppInfo(packageStateInternal, 2));
        }

        public java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllSilentInstalledMbaInfo(android.os.Bundle bundle) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.content.pm.PackageInfo packageInfo : com.android.server.BinaryTransparencyService.this.getNewlyInstalledMbas()) {
                if (!bundle.containsKey(packageInfo.packageName)) {
                    com.android.server.pm.pkg.PackageStateInternal packageStateInternal = com.android.server.BinaryTransparencyService.this.mPackageManagerInternal.getPackageStateInternal(packageInfo.packageName);
                    if (packageStateInternal == null) {
                        android.util.Slog.w(com.android.server.BinaryTransparencyService.TAG, "Package state is unavailable, ignoring the package " + packageInfo.packageName);
                    } else {
                        arrayList.addAll(collectAppInfo(packageStateInternal, 3));
                    }
                }
            }
            return arrayList;
        }

        private void recordApexInfo(com.android.internal.os.IBinaryTransparencyService.ApexInfo apexInfo) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.APEX_INFO_GATHERED, apexInfo.packageName, apexInfo.longVersion, apexInfo.digest != null ? libcore.util.HexEncoding.encodeToString(apexInfo.digest, false) : null, apexInfo.digestAlgorithm, apexInfo.signerDigests);
        }

        private void writeAppInfoToLog(com.android.internal.os.IBinaryTransparencyService.AppInfo appInfo) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.MOBILE_BUNDLED_APP_INFO_GATHERED, appInfo.packageName, appInfo.longVersion, appInfo.digest != null ? libcore.util.HexEncoding.encodeToString(appInfo.digest, false) : null, appInfo.digestAlgorithm, appInfo.signerDigests, appInfo.mbaStatus, appInfo.initiator, appInfo.initiatorSignerDigests, appInfo.installer, appInfo.originator, appInfo.splitName);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.util.Map<java.lang.Integer, byte[]> computeApkContentDigest(java.lang.String str) {
            android.content.pm.parsing.result.ParseResult verifySignaturesInternal = android.util.apk.ApkSignatureVerifier.verifySignaturesInternal(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing(), str, 2, false);
            if (verifySignaturesInternal.isError()) {
                android.util.Slog.e(com.android.server.BinaryTransparencyService.TAG, "Failed to compute content digest for " + str + " due to: " + verifySignaturesInternal.getErrorMessage());
                return null;
            }
            return ((android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests) verifySignaturesInternal.getResult()).contentDigests;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            new android.os.ShellCommand() { // from class: com.android.server.BinaryTransparencyService.BinaryTransparencyServiceImpl.1
                private int printSignedImageInfo() {
                    java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                    boolean z = false;
                    while (true) {
                        java.lang.String nextOption = getNextOption();
                        char c = 65535;
                        if (nextOption == null) {
                            java.lang.String signedImageInfo = com.android.server.BinaryTransparencyService.BinaryTransparencyServiceImpl.this.getSignedImageInfo();
                            outPrintWriter.println("Image Info:");
                            outPrintWriter.println(android.os.Build.FINGERPRINT);
                            outPrintWriter.println(signedImageInfo);
                            outPrintWriter.println("");
                            if (z) {
                                if (com.android.server.BinaryTransparencyService.this.mContext.getPackageManager() == null) {
                                    outPrintWriter.println("ERROR: Failed to obtain an instance of package manager.");
                                    return -1;
                                }
                                outPrintWriter.println("Other partitions:");
                                for (android.os.Build.Partition partition : android.os.Build.getFingerprintedPartitions()) {
                                    outPrintWriter.println("Name: " + partition.getName());
                                    outPrintWriter.println("Fingerprint: " + partition.getFingerprint());
                                    outPrintWriter.println("Build time (ms): " + partition.getBuildTimeMillis());
                                }
                            }
                            return 0;
                        }
                        switch (nextOption.hashCode()) {
                            case 1492:
                                if (nextOption.equals("-a")) {
                                    c = 0;
                                    break;
                                }
                                break;
                        }
                        z = true;
                        switch (c) {
                            case 0:
                            default:
                                outPrintWriter.println("ERROR: Unknown option: " + nextOption);
                                return 1;
                        }
                    }
                }

                private void printPackageMeasurements(android.content.pm.PackageInfo packageInfo, boolean z, java.io.PrintWriter printWriter) {
                    java.util.Map computeApkContentDigest = com.android.server.BinaryTransparencyService.BinaryTransparencyServiceImpl.this.computeApkContentDigest(packageInfo.applicationInfo.sourceDir);
                    if (computeApkContentDigest == null) {
                        printWriter.println("ERROR: Failed to compute package content digest for " + packageInfo.applicationInfo.sourceDir);
                        return;
                    }
                    if (z) {
                        printWriter.print(android.util.PackageUtils.computeSha256DigestForLargeFile(packageInfo.applicationInfo.sourceDir, android.util.PackageUtils.createLargeFileBuffer()) + ",");
                    }
                    for (java.util.Map.Entry entry : computeApkContentDigest.entrySet()) {
                        java.lang.Integer num = (java.lang.Integer) entry.getKey();
                        byte[] bArr = (byte[]) entry.getValue();
                        printWriter.print(com.android.server.BinaryTransparencyService.this.translateContentDigestAlgorithmIdToString(num.intValue()));
                        printWriter.print(":");
                        printWriter.print(libcore.util.HexEncoding.encodeToString(bArr, false));
                        printWriter.print("\n");
                    }
                }

                private void printPackageInstallationInfo(android.content.pm.PackageInfo packageInfo, boolean z, java.io.PrintWriter printWriter) {
                    printWriter.println("--- Package Installation Info ---");
                    printWriter.println("Current install location: " + packageInfo.applicationInfo.sourceDir);
                    if (packageInfo.applicationInfo.sourceDir.startsWith("/data/apex/")) {
                        java.lang.String originalApexPreinstalledLocation = com.android.server.BinaryTransparencyService.this.getOriginalApexPreinstalledLocation(packageInfo.packageName);
                        printWriter.println("|--> Pre-installed package install location: " + originalApexPreinstalledLocation);
                        if (!originalApexPreinstalledLocation.equals(com.android.server.BinaryTransparencyService.APEX_PRELOAD_LOCATION_ERROR)) {
                            if (z) {
                                printWriter.println("|--> Pre-installed package SHA-256 digest: " + android.util.PackageUtils.computeSha256DigestForLargeFile(originalApexPreinstalledLocation, android.util.PackageUtils.createLargeFileBuffer()));
                            }
                            java.util.Map computeApkContentDigest = com.android.server.BinaryTransparencyService.BinaryTransparencyServiceImpl.this.computeApkContentDigest(originalApexPreinstalledLocation);
                            if (computeApkContentDigest == null) {
                                printWriter.println("|--> ERROR: Failed to compute package content digest for " + originalApexPreinstalledLocation);
                            } else {
                                for (java.util.Map.Entry entry : computeApkContentDigest.entrySet()) {
                                    java.lang.Integer num = (java.lang.Integer) entry.getKey();
                                    printWriter.println("|--> Pre-installed package content digest: " + libcore.util.HexEncoding.encodeToString((byte[]) entry.getValue(), false));
                                    printWriter.println("|--> Pre-installed package content digest algorithm: " + com.android.server.BinaryTransparencyService.this.translateContentDigestAlgorithmIdToString(num.intValue()));
                                }
                            }
                        }
                    }
                    printWriter.println("First install time (ms): " + packageInfo.firstInstallTime);
                    printWriter.println("Last update time (ms):   " + packageInfo.lastUpdateTime);
                    printWriter.println("Is preloaded: " + (packageInfo.firstInstallTime == packageInfo.lastUpdateTime));
                    android.content.pm.InstallSourceInfo installSourceInfo = com.android.server.BinaryTransparencyService.this.getInstallSourceInfo(packageInfo.packageName);
                    if (installSourceInfo == null) {
                        printWriter.println("ERROR: Unable to obtain installSourceInfo of " + packageInfo.packageName);
                    } else {
                        printWriter.println("Installation initiated by: " + installSourceInfo.getInitiatingPackageName());
                        printWriter.println("Installation done by: " + installSourceInfo.getInstallingPackageName());
                        printWriter.println("Installation originating from: " + installSourceInfo.getOriginatingPackageName());
                    }
                    if (packageInfo.isApex) {
                        printWriter.println("Is an active APEX: " + packageInfo.isActiveApex);
                    }
                }

                private void printPackageSignerDetails(android.content.pm.SigningInfo signingInfo, java.io.PrintWriter printWriter) {
                    if (signingInfo == null) {
                        printWriter.println("ERROR: Package's signingInfo is null.");
                        return;
                    }
                    printWriter.println("--- Package Signer Info ---");
                    printWriter.println("Has multiple signers: " + signingInfo.hasMultipleSigners());
                    printWriter.println("Signing key has been rotated: " + signingInfo.hasPastSigningCertificates());
                    android.content.pm.Signature[] apkContentsSigners = signingInfo.getApkContentsSigners();
                    int length = apkContentsSigners.length;
                    for (int i = 0; i < length; i++) {
                        android.content.pm.Signature signature = apkContentsSigners[i];
                        java.lang.String encodeToString = libcore.util.HexEncoding.encodeToString(android.util.PackageUtils.computeSha256DigestBytes(signature.toByteArray()), false);
                        printWriter.println("Signer cert's SHA256-digest: " + encodeToString);
                        try {
                            printWriter.println("Signing key algorithm: " + signature.getPublicKey().getAlgorithm());
                        } catch (java.security.cert.CertificateException e) {
                            android.util.Slog.e("ShellCommand", "Failed to obtain public key of signer for cert with hash: " + encodeToString, e);
                        }
                    }
                    if (!signingInfo.hasMultipleSigners() && signingInfo.hasPastSigningCertificates()) {
                        printWriter.println("== Signing Cert Lineage (Excluding The Most Recent) ==");
                        printWriter.println("(Certs are sorted in the order of rotation, beginning with the original signing cert)");
                        android.content.pm.Signature[] signingCertificateHistory = signingInfo.getSigningCertificateHistory();
                        int i2 = 0;
                        while (i2 < signingCertificateHistory.length - 1) {
                            android.content.pm.Signature signature2 = signingCertificateHistory[i2];
                            java.lang.String encodeToString2 = libcore.util.HexEncoding.encodeToString(android.util.PackageUtils.computeSha256DigestBytes(signature2.toByteArray()), false);
                            java.lang.StringBuilder sb = new java.lang.StringBuilder();
                            sb.append("  ++ Signer cert #");
                            i2++;
                            sb.append(i2);
                            sb.append(" ++");
                            printWriter.println(sb.toString());
                            printWriter.println("  Cert SHA256-digest: " + encodeToString2);
                            try {
                                printWriter.println("  Signing key algorithm: " + signature2.getPublicKey().getAlgorithm());
                            } catch (java.security.cert.CertificateException e2) {
                                android.util.Slog.e("ShellCommand", "Failed to obtain public key of signer for cert with hash: " + encodeToString2, e2);
                            }
                        }
                    }
                }

                private void printModuleDetails(android.content.pm.ModuleInfo moduleInfo, java.io.PrintWriter printWriter) {
                    printWriter.println("--- Module Details ---");
                    printWriter.println("Module name: " + ((java.lang.Object) moduleInfo.getName()));
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("Module visibility: ");
                    sb.append(moduleInfo.isHidden() ? "hidden" : com.android.server.wm.ActivityTaskManagerService.DUMP_VISIBLE_ACTIVITIES);
                    printWriter.println(sb.toString());
                }

                private void printAppDetails(android.content.pm.PackageInfo packageInfo, boolean z, java.io.PrintWriter printWriter) {
                    printWriter.println("--- App Details ---");
                    printWriter.println("Name: " + packageInfo.applicationInfo.name);
                    printWriter.println("Label: " + ((java.lang.Object) com.android.server.BinaryTransparencyService.this.mContext.getPackageManager().getApplicationLabel(packageInfo.applicationInfo)));
                    printWriter.println("Description: " + ((java.lang.Object) packageInfo.applicationInfo.loadDescription(com.android.server.BinaryTransparencyService.this.mContext.getPackageManager())));
                    printWriter.println("Has code: " + packageInfo.applicationInfo.hasCode());
                    printWriter.println("Is enabled: " + packageInfo.applicationInfo.enabled);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("Is suspended: ");
                    int i = 0;
                    sb.append((packageInfo.applicationInfo.flags & 1073741824) != 0);
                    printWriter.println(sb.toString());
                    printWriter.println("Compile SDK version: " + packageInfo.compileSdkVersion);
                    printWriter.println("Target SDK version: " + packageInfo.applicationInfo.targetSdkVersion);
                    printWriter.println("Is privileged: " + packageInfo.applicationInfo.isPrivilegedApp());
                    printWriter.println("Is a stub: " + packageInfo.isStub);
                    printWriter.println("Is a core app: " + packageInfo.coreApp);
                    printWriter.println("SEInfo: " + packageInfo.applicationInfo.seInfo);
                    printWriter.println("Component factory: " + packageInfo.applicationInfo.appComponentFactory);
                    printWriter.println("Process name: " + packageInfo.applicationInfo.processName);
                    printWriter.println("Task affinity: " + packageInfo.applicationInfo.taskAffinity);
                    printWriter.println("UID: " + packageInfo.applicationInfo.uid);
                    printWriter.println("Shared UID: " + packageInfo.sharedUserId);
                    if (z) {
                        printWriter.println("== App's Shared Libraries ==");
                        java.util.List sharedLibraryInfos = packageInfo.applicationInfo.getSharedLibraryInfos();
                        if (sharedLibraryInfos == null || sharedLibraryInfos.isEmpty()) {
                            printWriter.println("<none>");
                        }
                        while (i < sharedLibraryInfos.size()) {
                            android.content.pm.SharedLibraryInfo sharedLibraryInfo = (android.content.pm.SharedLibraryInfo) sharedLibraryInfos.get(i);
                            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                            sb2.append("  ++ Library #");
                            i++;
                            sb2.append(i);
                            sb2.append(" ++");
                            printWriter.println(sb2.toString());
                            printWriter.println("  Lib name: " + sharedLibraryInfo.getName());
                            long longVersion = sharedLibraryInfo.getLongVersion();
                            printWriter.print("  Lib version: ");
                            if (longVersion == -1) {
                                printWriter.print("undefined");
                            } else {
                                printWriter.print(longVersion);
                            }
                            printWriter.print("\n");
                            printWriter.println("  Lib package name (if available): " + sharedLibraryInfo.getPackageName());
                            printWriter.println("  Lib path: " + sharedLibraryInfo.getPath());
                            printWriter.print("  Lib type: ");
                            switch (sharedLibraryInfo.getType()) {
                                case 0:
                                    printWriter.print("built-in");
                                    break;
                                case 1:
                                    printWriter.print("dynamic");
                                    break;
                                case 2:
                                    printWriter.print("static");
                                    break;
                                case 3:
                                    printWriter.print("SDK");
                                    break;
                                default:
                                    printWriter.print("undefined");
                                    break;
                            }
                            printWriter.print("\n");
                            printWriter.println("  Is a native lib: " + sharedLibraryInfo.isNative());
                        }
                    }
                }

                private void printHeadersHelper(@android.annotation.NonNull java.lang.String str, boolean z, @android.annotation.NonNull java.io.PrintWriter printWriter) {
                    printWriter.print(str + " Info [Format: package_name,package_version,");
                    if (z) {
                        printWriter.print("package_sha256_digest,");
                    }
                    printWriter.print("content_digest_algorithm:content_digest]:\n");
                }

                private int printAllApexs() {
                    java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                    boolean z = false;
                    boolean z2 = false;
                    boolean z3 = true;
                    while (true) {
                        java.lang.String nextOption = getNextOption();
                        char c = 65535;
                        if (nextOption != null) {
                            switch (nextOption.hashCode()) {
                                case 1506:
                                    if (nextOption.equals("-o")) {
                                        c = 2;
                                        break;
                                    }
                                    break;
                                case 1513:
                                    if (nextOption.equals("-v")) {
                                        c = 0;
                                        break;
                                    }
                                    break;
                                case 43009159:
                                    if (nextOption.equals("--old")) {
                                        c = 3;
                                        break;
                                    }
                                    break;
                                case 967085338:
                                    if (nextOption.equals("--no-headers")) {
                                        c = 4;
                                        break;
                                    }
                                    break;
                                case 1737088994:
                                    if (nextOption.equals("--verbose")) {
                                        c = 1;
                                        break;
                                    }
                                    break;
                            }
                            switch (c) {
                                case 0:
                                case 1:
                                    z = true;
                                    break;
                                case 2:
                                case 3:
                                    z2 = true;
                                    break;
                                case 4:
                                    z3 = false;
                                    break;
                                default:
                                    outPrintWriter.println("ERROR: Unknown option: " + nextOption);
                                    return 1;
                            }
                        } else {
                            android.content.pm.PackageManager packageManager = com.android.server.BinaryTransparencyService.this.mContext.getPackageManager();
                            if (packageManager == null) {
                                outPrintWriter.println("ERROR: Failed to obtain an instance of package manager.");
                                return -1;
                            }
                            if (!z && z3) {
                                printHeadersHelper("APEX", z2, outPrintWriter);
                            }
                            for (android.content.pm.PackageInfo packageInfo : com.android.server.BinaryTransparencyService.this.getCurrentInstalledApexs()) {
                                if (z && z3) {
                                    printHeadersHelper("APEX", z2, outPrintWriter);
                                }
                                outPrintWriter.print(packageInfo.packageName + "," + packageInfo.getLongVersionCode() + ",");
                                printPackageMeasurements(packageInfo, z2, outPrintWriter);
                                if (z) {
                                    try {
                                        android.content.pm.ModuleInfo moduleInfo = packageManager.getModuleInfo(packageInfo.packageName, 0);
                                        outPrintWriter.println("Is a module: true");
                                        printModuleDetails(moduleInfo, outPrintWriter);
                                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                        outPrintWriter.println("Is a module: false");
                                    }
                                    printPackageInstallationInfo(packageInfo, z2, outPrintWriter);
                                    printPackageSignerDetails(packageInfo.signingInfo, outPrintWriter);
                                    outPrintWriter.println("");
                                }
                            }
                            return 0;
                        }
                    }
                }

                private int printAllModules() {
                    boolean z;
                    android.content.pm.PackageInfo packageInfo;
                    java.lang.StringBuilder sb;
                    java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                    boolean z2 = true;
                    boolean z3 = false;
                    boolean z4 = false;
                    while (true) {
                        java.lang.String nextOption = getNextOption();
                        char c = 65535;
                        if (nextOption != null) {
                            switch (nextOption.hashCode()) {
                                case 1506:
                                    if (nextOption.equals("-o")) {
                                        c = 2;
                                        break;
                                    }
                                    break;
                                case 1513:
                                    if (nextOption.equals("-v")) {
                                        c = 0;
                                        break;
                                    }
                                    break;
                                case 43009159:
                                    if (nextOption.equals("--old")) {
                                        c = 3;
                                        break;
                                    }
                                    break;
                                case 967085338:
                                    if (nextOption.equals("--no-headers")) {
                                        c = 4;
                                        break;
                                    }
                                    break;
                                case 1737088994:
                                    if (nextOption.equals("--verbose")) {
                                        c = 1;
                                        break;
                                    }
                                    break;
                            }
                            switch (c) {
                                case 0:
                                case 1:
                                    z3 = true;
                                    break;
                                case 2:
                                case 3:
                                    z4 = true;
                                    break;
                                case 4:
                                    z2 = false;
                                    break;
                                default:
                                    outPrintWriter.println("ERROR: Unknown option: " + nextOption);
                                    return 1;
                            }
                        } else {
                            android.content.pm.PackageManager packageManager = com.android.server.BinaryTransparencyService.this.mContext.getPackageManager();
                            if (packageManager == null) {
                                outPrintWriter.println("ERROR: Failed to obtain an instance of package manager.");
                                return -1;
                            }
                            if (!z3 && z2) {
                                printHeadersHelper("Module", z4, outPrintWriter);
                            }
                            for (android.content.pm.ModuleInfo moduleInfo : packageManager.getInstalledModules(131072)) {
                                java.lang.String packageName = moduleInfo.getPackageName();
                                if (z3 && z2) {
                                    printHeadersHelper("Module", z4, outPrintWriter);
                                }
                                try {
                                    packageInfo = packageManager.getPackageInfo(packageName, 1207959552);
                                    outPrintWriter.print(packageInfo.packageName + ",");
                                    sb = new java.lang.StringBuilder();
                                    z = z3;
                                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                    z = z3;
                                }
                                try {
                                    sb.append(packageInfo.getLongVersionCode());
                                    sb.append(",");
                                    outPrintWriter.print(sb.toString());
                                    printPackageMeasurements(packageInfo, z4, outPrintWriter);
                                    if (z) {
                                        printModuleDetails(moduleInfo, outPrintWriter);
                                        printPackageInstallationInfo(packageInfo, z4, outPrintWriter);
                                        printPackageSignerDetails(packageInfo.signingInfo, outPrintWriter);
                                        outPrintWriter.println("");
                                    }
                                    z3 = z;
                                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                                    outPrintWriter.println(packageName + ",ERROR:Unable to find PackageInfo for this module.");
                                    if (z) {
                                        printModuleDetails(moduleInfo, outPrintWriter);
                                        outPrintWriter.println("");
                                    }
                                    z3 = z;
                                }
                            }
                            return 0;
                        }
                    }
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                private int printAllMbas() {
                    char c;
                    java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                    boolean z = true;
                    boolean z2 = false;
                    boolean z3 = false;
                    boolean z4 = false;
                    boolean z5 = false;
                    while (true) {
                        java.lang.String nextOption = getNextOption();
                        if (nextOption != null) {
                            switch (nextOption.hashCode()) {
                                case 1503:
                                    if (nextOption.equals("-l")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1506:
                                    if (nextOption.equals("-o")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1513:
                                    if (nextOption.equals("-v")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 43009159:
                                    if (nextOption.equals("--old")) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 705409647:
                                    if (nextOption.equals("--preloads-only")) {
                                        c = 6;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 967085338:
                                    if (nextOption.equals("--no-headers")) {
                                        c = 5;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1737088994:
                                    if (nextOption.equals("--verbose")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            switch (c) {
                                case 0:
                                case 1:
                                    z2 = true;
                                    break;
                                case 2:
                                    z5 = true;
                                    break;
                                case 3:
                                case 4:
                                    z4 = true;
                                    break;
                                case 5:
                                    z = false;
                                    break;
                                case 6:
                                    z3 = true;
                                    break;
                                default:
                                    outPrintWriter.println("ERROR: Unknown option: " + nextOption);
                                    return 1;
                            }
                        } else {
                            java.lang.String str = "Preload";
                            if (!z2 && z) {
                                if (z3) {
                                    printHeadersHelper("Preload", z4, outPrintWriter);
                                } else {
                                    printHeadersHelper("MBA", z4, outPrintWriter);
                                }
                            }
                            android.content.pm.PackageManager packageManager = com.android.server.BinaryTransparencyService.this.mContext.getPackageManager();
                            for (android.content.pm.PackageInfo packageInfo : packageManager.getInstalledPackages(android.content.pm.PackageManager.PackageInfoFlags.of(136314880L))) {
                                if (packageInfo.signingInfo == null) {
                                    try {
                                        packageManager.getPackageInfo(packageInfo.packageName, android.content.pm.PackageManager.PackageInfoFlags.of(134348800L));
                                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                        android.util.Slog.e("ShellCommand", "Failed to obtain an updated PackageInfo of " + packageInfo.packageName);
                                    }
                                }
                                if (z2 && z) {
                                    printHeadersHelper(str, z4, outPrintWriter);
                                }
                                outPrintWriter.print(packageInfo.packageName + ",");
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                java.lang.String str2 = str;
                                android.content.pm.PackageManager packageManager2 = packageManager;
                                sb.append(packageInfo.getLongVersionCode());
                                sb.append(",");
                                outPrintWriter.print(sb.toString());
                                printPackageMeasurements(packageInfo, z4, outPrintWriter);
                                if (z2) {
                                    printAppDetails(packageInfo, z5, outPrintWriter);
                                    printPackageInstallationInfo(packageInfo, z4, outPrintWriter);
                                    printPackageSignerDetails(packageInfo.signingInfo, outPrintWriter);
                                    outPrintWriter.println("");
                                }
                                str = str2;
                                packageManager = packageManager2;
                            }
                            if (z3) {
                                return 0;
                            }
                            for (android.content.pm.PackageInfo packageInfo2 : com.android.server.BinaryTransparencyService.this.getNewlyInstalledMbas()) {
                                if (z2 && z) {
                                    printHeadersHelper("MBA", z4, outPrintWriter);
                                }
                                outPrintWriter.print(packageInfo2.packageName + ",");
                                outPrintWriter.print(packageInfo2.getLongVersionCode() + ",");
                                printPackageMeasurements(packageInfo2, z4, outPrintWriter);
                                if (z2) {
                                    printAppDetails(packageInfo2, z5, outPrintWriter);
                                    printPackageInstallationInfo(packageInfo2, z4, outPrintWriter);
                                    printPackageSignerDetails(packageInfo2.signingInfo, outPrintWriter);
                                    outPrintWriter.println("");
                                }
                            }
                            return 0;
                        }
                    }
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Code restructure failed: missing block: B:40:0x0062, code lost:
                
                    if (r6.equals("image_info") != false) goto L35;
                 */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public int onCommand(java.lang.String str) {
                    boolean z;
                    if (str == null) {
                        return handleDefaultCommands(str);
                    }
                    java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                    char c = 0;
                    switch (str.hashCode()) {
                        case 102230:
                            if (str.equals("get")) {
                                z = false;
                                break;
                            }
                        default:
                            z = -1;
                            break;
                    }
                    switch (z) {
                        case false:
                            java.lang.String nextArg = getNextArg();
                            if (nextArg == null) {
                                printHelpMenu();
                                break;
                            } else {
                                switch (nextArg.hashCode()) {
                                    case -1443097326:
                                        break;
                                    case -1195140447:
                                        if (nextArg.equals("module_info")) {
                                            c = 2;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 636812193:
                                        if (nextArg.equals("mba_info")) {
                                            c = 3;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 1366866347:
                                        if (nextArg.equals("apex_info")) {
                                            c = 1;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    default:
                                        c = 65535;
                                        break;
                                }
                                switch (c) {
                                    case 0:
                                        break;
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                    default:
                                        outPrintWriter.println(java.lang.String.format("ERROR: Unknown info type '%s'", nextArg));
                                        break;
                                }
                            }
                    }
                    return handleDefaultCommands(str);
                }

                private void printHelpMenu() {
                    java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                    outPrintWriter.println("Transparency manager (transparency) commands:");
                    outPrintWriter.println("  help");
                    outPrintWriter.println("    Print this help text.");
                    outPrintWriter.println("");
                    outPrintWriter.println("  get image_info [-a]");
                    outPrintWriter.println("    Print information about loaded image (firmware). Options:");
                    outPrintWriter.println("        -a: lists all other identifiable partitions.");
                    outPrintWriter.println("");
                    outPrintWriter.println("  get apex_info [-o] [-v] [--no-headers]");
                    outPrintWriter.println("    Print information about installed APEXs on device.");
                    outPrintWriter.println("      -o: also uses the old digest scheme (SHA256) to compute APEX hashes. WARNING: This can be a very slow and CPU-intensive computation.");
                    outPrintWriter.println("      -v: lists more verbose information about each APEX.");
                    outPrintWriter.println("      --no-headers: does not print the header if specified.");
                    outPrintWriter.println("");
                    outPrintWriter.println("  get module_info [-o] [-v] [--no-headers]");
                    outPrintWriter.println("    Print information about installed modules on device.");
                    outPrintWriter.println("      -o: also uses the old digest scheme (SHA256) to compute module hashes. WARNING: This can be a very slow and CPU-intensive computation.");
                    outPrintWriter.println("      -v: lists more verbose information about each module.");
                    outPrintWriter.println("      --no-headers: does not print the header if specified.");
                    outPrintWriter.println("");
                    outPrintWriter.println("  get mba_info [-o] [-v] [-l] [--no-headers] [--preloads-only]");
                    outPrintWriter.println("    Print information about installed mobile bundle apps (MBAs on device).");
                    outPrintWriter.println("      -o: also uses the old digest scheme (SHA256) to compute MBA hashes. WARNING: This can be a very slow and CPU-intensive computation.");
                    outPrintWriter.println("      -v: lists more verbose information about each app.");
                    outPrintWriter.println("      -l: lists shared library info. (This option only works when -v option is also specified)");
                    outPrintWriter.println("      --no-headers: does not print the header if specified.");
                    outPrintWriter.println("      --preloads-only: lists only preloaded apps. This options can also be combined with others.");
                    outPrintWriter.println("");
                }

                public void onHelp() {
                    printHelpMenu();
                }
            }.exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class BiometricLogger {
        private static final java.lang.String TAG = "BiometricLogger";
        private static final com.android.server.BinaryTransparencyService.BiometricLogger sInstance = new com.android.server.BinaryTransparencyService.BiometricLogger();

        private BiometricLogger() {
        }

        public static com.android.server.BinaryTransparencyService.BiometricLogger getInstance() {
            return sInstance;
        }

        public void logStats(int i, int i2, int i3, int i4, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BIOMETRIC_PROPERTIES_COLLECTED, i, i2, i3, i4, str, str2, str3, str4, str5);
        }
    }

    public BinaryTransparencyService(android.content.Context context) {
        this(context, com.android.server.BinaryTransparencyService.BiometricLogger.getInstance());
    }

    @com.android.internal.annotations.VisibleForTesting
    BinaryTransparencyService(android.content.Context context, com.android.server.BinaryTransparencyService.BiometricLogger biometricLogger) {
        super(context);
        this.mContext = context;
        this.mServiceImpl = new com.android.server.BinaryTransparencyService.BinaryTransparencyServiceImpl();
        this.mVbmetaDigest = VBMETA_DIGEST_UNINITIALIZED;
        this.mMeasurementsLastRecordedMs = 0L;
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mBiometricLogger = biometricLogger;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        try {
            publishBinderService("transparency", this.mServiceImpl);
            android.util.Slog.i(TAG, "Started BinaryTransparencyService");
        } catch (java.lang.Throwable th) {
            android.util.Slog.e(TAG, "Failed to start BinaryTransparencyService.", th);
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 1000) {
            android.util.Slog.i(TAG, "Boot completed. Getting boot integrity data.");
            collectBootIntegrityInfo();
            android.util.Slog.i(TAG, "Boot completed. Collecting biometric system properties.");
            collectBiometricProperties();
            android.util.Slog.i(TAG, "Scheduling measurements to be taken.");
            com.android.server.BinaryTransparencyService.UpdateMeasurementsJobService.scheduleBinaryMeasurements(this.mContext, this);
            registerAllPackageUpdateObservers();
        }
    }

    public static class UpdateMeasurementsJobService extends android.app.job.JobService {
        private static final int DO_BINARY_MEASUREMENTS_JOB_ID = 1740526926;
        private static long sTimeLastRanMs = 0;

        @Override // android.app.job.JobService
        public boolean onStartJob(final android.app.job.JobParameters jobParameters) {
            android.util.Slog.d(com.android.server.BinaryTransparencyService.TAG, "Job to update binary measurements started.");
            if (jobParameters.getJobId() != DO_BINARY_MEASUREMENTS_JOB_ID) {
                return false;
            }
            java.util.concurrent.Executors.defaultThreadFactory().newThread(new java.lang.Runnable() { // from class: com.android.server.BinaryTransparencyService$UpdateMeasurementsJobService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.BinaryTransparencyService.UpdateMeasurementsJobService.this.lambda$onStartJob$0(jobParameters);
                }
            }).start();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStartJob$0(android.app.job.JobParameters jobParameters) {
            try {
                com.android.internal.os.IBinaryTransparencyService.Stub.asInterface(android.os.ServiceManager.getService("transparency")).recordMeasurementsForAllPackages();
                sTimeLastRanMs = java.lang.System.currentTimeMillis();
                jobFinished(jobParameters, false);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.BinaryTransparencyService.TAG, "Taking binary measurements was interrupted.", e);
            }
        }

        @Override // android.app.job.JobService
        public boolean onStopJob(android.app.job.JobParameters jobParameters) {
            return false;
        }

        @android.annotation.SuppressLint({"DefaultLocale"})
        static void scheduleBinaryMeasurements(android.content.Context context, com.android.server.BinaryTransparencyService binaryTransparencyService) {
            android.util.Slog.i(com.android.server.BinaryTransparencyService.TAG, "Scheduling binary content-digest computation job");
            android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class);
            if (jobScheduler == null) {
                android.util.Slog.e(com.android.server.BinaryTransparencyService.TAG, "Failed to obtain an instance of JobScheduler.");
                return;
            }
            if (jobScheduler.getPendingJob(DO_BINARY_MEASUREMENTS_JOB_ID) != null) {
                android.util.Slog.d(com.android.server.BinaryTransparencyService.TAG, "A measurement job has already been scheduled.");
                return;
            }
            long j = 0;
            if (sTimeLastRanMs != 0) {
                j = java.lang.Math.max(0L, java.lang.Math.min(86400000 - (java.lang.System.currentTimeMillis() - sTimeLastRanMs), 86400000L));
                android.util.Slog.d(com.android.server.BinaryTransparencyService.TAG, "Scheduling the next measurement to be done at least " + j + "ms from now.");
            }
            if (jobScheduler.schedule(new android.app.job.JobInfo.Builder(DO_BINARY_MEASUREMENTS_JOB_ID, new android.content.ComponentName(context, (java.lang.Class<?>) com.android.server.BinaryTransparencyService.UpdateMeasurementsJobService.class)).setRequiresDeviceIdle(true).setRequiresCharging(true).setMinimumLatency(j).build()) != 1) {
                android.util.Slog.e(com.android.server.BinaryTransparencyService.TAG, "Failed to schedule job to measure binaries.");
            } else {
                android.util.Slog.d(com.android.server.BinaryTransparencyService.TAG, android.text.TextUtils.formatSimple("Job %d to measure binaries was scheduled successfully.", new java.lang.Object[]{java.lang.Integer.valueOf(DO_BINARY_MEASUREMENTS_JOB_ID)}));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int toFingerprintSensorType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            default:
                return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int toFaceSensorType(int i) {
        switch (i) {
            case 1:
                return 6;
            case 2:
                return 7;
            default:
                return 0;
        }
    }

    private int toSensorStrength(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            default:
                return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logBiometricProperties(android.hardware.biometrics.SensorProperties sensorProperties, int i, int i2) {
        int sensorId = sensorProperties.getSensorId();
        int sensorStrength = toSensorStrength(sensorProperties.getSensorStrength());
        for (android.hardware.biometrics.SensorProperties.ComponentInfo componentInfo : sensorProperties.getComponentInfo()) {
            this.mBiometricLogger.logStats(sensorId, i, i2, sensorStrength, componentInfo.getComponentId().trim(), componentInfo.getHardwareVersion().trim(), componentInfo.getFirmwareVersion().trim(), componentInfo.getSerialNumber().trim(), componentInfo.getSoftwareVersion().trim());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void collectBiometricProperties() {
        android.hardware.fingerprint.FingerprintManager fingerprintManager;
        if (!android.provider.DeviceConfig.getBoolean("biometrics", KEY_ENABLE_BIOMETRIC_PROPERTY_VERIFICATION, true)) {
            return;
        }
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.hardware.face.FaceManager faceManager = null;
        if (packageManager != null && packageManager.hasSystemFeature("android.hardware.fingerprint")) {
            fingerprintManager = (android.hardware.fingerprint.FingerprintManager) this.mContext.getSystemService(android.hardware.fingerprint.FingerprintManager.class);
        } else {
            fingerprintManager = null;
        }
        if (packageManager != null && packageManager.hasSystemFeature("android.hardware.biometrics.face")) {
            faceManager = (android.hardware.face.FaceManager) this.mContext.getSystemService(android.hardware.face.FaceManager.class);
        }
        if (fingerprintManager != null) {
            fingerprintManager.addAuthenticatorsRegisteredCallback(new android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.server.BinaryTransparencyService.1
                public void onAllAuthenticatorsRegistered(java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list) {
                    java.util.Iterator<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> it = list.iterator();
                    while (it.hasNext()) {
                        android.hardware.fingerprint.FingerprintSensorProperties from = android.hardware.fingerprint.FingerprintSensorProperties.from(it.next());
                        com.android.server.BinaryTransparencyService.this.logBiometricProperties(from, 1, com.android.server.BinaryTransparencyService.this.toFingerprintSensorType(from.getSensorType()));
                    }
                }
            });
        }
        if (faceManager != null) {
            faceManager.addAuthenticatorsRegisteredCallback(new android.hardware.face.IFaceAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.server.BinaryTransparencyService.2
                public void onAllAuthenticatorsRegistered(java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) {
                    java.util.Iterator<android.hardware.face.FaceSensorPropertiesInternal> it = list.iterator();
                    while (it.hasNext()) {
                        android.hardware.face.FaceSensorProperties from = android.hardware.face.FaceSensorProperties.from(it.next());
                        com.android.server.BinaryTransparencyService.this.logBiometricProperties(from, 4, com.android.server.BinaryTransparencyService.this.toFaceSensorType(from.getSensorType()));
                    }
                }
            });
        }
    }

    private void collectBootIntegrityInfo() {
        this.mVbmetaDigest = android.os.SystemProperties.get(SYSPROP_NAME_VBETA_DIGEST, VBMETA_DIGEST_UNAVAILABLE);
        android.util.Slog.d(TAG, java.lang.String.format("VBMeta Digest: %s", this.mVbmetaDigest));
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.VBMETA_DIGEST_REPORTED, this.mVbmetaDigest);
        if (android.security.Flags.binaryTransparencySepolicyHash()) {
            com.android.server.IoThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.BinaryTransparencyService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.BinaryTransparencyService.this.lambda$collectBootIntegrityInfo$0();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collectBootIntegrityInfo$0() {
        java.lang.String str;
        byte[] computeSha256DigestForLargeFileAsBytes = android.util.PackageUtils.computeSha256DigestForLargeFileAsBytes("/sys/fs/selinux/policy", android.util.PackageUtils.createLargeFileBuffer());
        if (computeSha256DigestForLargeFileAsBytes == null) {
            str = null;
        } else {
            str = libcore.util.HexEncoding.encodeToString(computeSha256DigestForLargeFileAsBytes, false);
            android.util.Slog.d(TAG, "sepolicy hash: " + str);
        }
        com.android.internal.util.FrameworkStatsLog.write(775, str, this.mVbmetaDigest);
    }

    private void registerApkAndNonStagedApexUpdateListener() {
        android.util.Slog.d(TAG, "Registering APK & Non-Staged APEX updates...");
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiver(new com.android.server.BinaryTransparencyService.PackageUpdatedReceiver(), intentFilter);
    }

    private void registerStagedApexUpdateObserver() {
        android.util.Slog.d(TAG, "Registering APEX updates...");
        android.content.pm.IPackageManagerNative asInterface = android.content.pm.IPackageManagerNative.Stub.asInterface(android.os.ServiceManager.getService("package_native"));
        if (asInterface == null) {
            android.util.Slog.e(TAG, "IPackageManagerNative is null");
            return;
        }
        try {
            asInterface.registerStagedApexObserver(new android.content.pm.IStagedApexObserver.Stub() { // from class: com.android.server.BinaryTransparencyService.3
                public void onApexStaged(android.content.pm.ApexStagedEvent apexStagedEvent) throws android.os.RemoteException {
                    android.util.Slog.d(com.android.server.BinaryTransparencyService.TAG, "A new APEX has been staged for update. There are currently " + apexStagedEvent.stagedApexModuleNames.length + " APEX(s) staged for update. Scheduling measurement...");
                    com.android.server.BinaryTransparencyService.UpdateMeasurementsJobService.scheduleBinaryMeasurements(com.android.server.BinaryTransparencyService.this.mContext, com.android.server.BinaryTransparencyService.this);
                }
            });
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to register a StagedApexObserver.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPackagePreloaded(java.lang.String str) {
        try {
            this.mContext.getPackageManager().getPackageInfo(str, android.content.pm.PackageManager.PackageInfoFlags.of(2097152L));
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPackageAnApex(java.lang.String str) {
        try {
            return this.mContext.getPackageManager().getPackageInfo(str, android.content.pm.PackageManager.PackageInfoFlags.of(1073741824L)).isApex;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private class PackageUpdatedReceiver extends android.content.BroadcastReceiver {
        private PackageUpdatedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (!intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
                return;
            }
            android.net.Uri data = intent.getData();
            if (data == null) {
                android.util.Slog.e(com.android.server.BinaryTransparencyService.TAG, "Shouldn't happen: intent data is null!");
                return;
            }
            if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                android.util.Slog.d(com.android.server.BinaryTransparencyService.TAG, "Not an update. Skipping...");
                return;
            }
            java.lang.String schemeSpecificPart = data.getSchemeSpecificPart();
            if (com.android.server.BinaryTransparencyService.this.isPackagePreloaded(schemeSpecificPart) || com.android.server.BinaryTransparencyService.this.isPackageAnApex(schemeSpecificPart)) {
                android.util.Slog.d(com.android.server.BinaryTransparencyService.TAG, schemeSpecificPart + " was updated. Scheduling measurement...");
                com.android.server.BinaryTransparencyService.UpdateMeasurementsJobService.scheduleBinaryMeasurements(com.android.server.BinaryTransparencyService.this.mContext, com.android.server.BinaryTransparencyService.this);
            }
        }
    }

    private void registerAllPackageUpdateObservers() {
        registerApkAndNonStagedApexUpdateListener();
        registerStagedApexUpdateObserver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String translateContentDigestAlgorithmIdToString(int i) {
        switch (i) {
            case 1:
                return "CHUNKED_SHA256";
            case 2:
                return "CHUNKED_SHA512";
            case 3:
                return "VERITY_CHUNKED_SHA256";
            case 4:
                return "SHA256";
            default:
                return "UNKNOWN_ALGO_ID(" + i + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public java.util.List<android.content.pm.PackageInfo> getCurrentInstalledApexs() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            android.util.Slog.e(TAG, "Error obtaining an instance of PackageManager.");
            return arrayList;
        }
        java.util.List<android.content.pm.PackageInfo> installedPackages = packageManager.getInstalledPackages(android.content.pm.PackageManager.PackageInfoFlags.of(1207959552L));
        if (installedPackages == null) {
            android.util.Slog.e(TAG, "Error obtaining installed packages (including APEX)");
            return arrayList;
        }
        return (java.util.List) installedPackages.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.BinaryTransparencyService$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean z;
                z = ((android.content.pm.PackageInfo) obj).isApex;
                return z;
            }
        }).collect(java.util.stream.Collectors.toList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public android.content.pm.InstallSourceInfo getInstallSourceInfo(java.lang.String str) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            android.util.Slog.e(TAG, "Error obtaining an instance of PackageManager.");
            return null;
        }
        try {
            return packageManager.getInstallSourceInfo(str);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public java.lang.String getOriginalApexPreinstalledLocation(java.lang.String str) {
        try {
            java.lang.String apexPackageNameToModuleName = apexPackageNameToModuleName(str);
            for (android.apex.ApexInfo apexInfo : android.apex.IApexService.Stub.asInterface(android.os.Binder.allowBlocking(android.os.ServiceManager.waitForService("apexservice"))).getAllPackages()) {
                if (apexPackageNameToModuleName.equals(apexInfo.moduleName)) {
                    return apexInfo.preinstalledModulePath;
                }
            }
            return APEX_PRELOAD_LOCATION_ERROR;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to get package list from apexservice", e);
            return APEX_PRELOAD_LOCATION_ERROR;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String apexPackageNameToModuleName(java.lang.String str) {
        return com.android.server.pm.ApexManager.getInstance().getApexModuleNameForPackageName(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public java.util.List<android.content.pm.PackageInfo> getNewlyInstalledMbas() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.pm.IBackgroundInstallControlService asInterface = android.content.pm.IBackgroundInstallControlService.Stub.asInterface(android.os.ServiceManager.getService("background_install_control"));
        if (asInterface == null) {
            android.util.Slog.e(TAG, "Failed to obtain an IBinder instance of IBackgroundInstallControlService");
            return arrayList;
        }
        try {
            return asInterface.getBackgroundInstalledPackages(134348800L, 0).getList();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to get a list of MBAs.", e);
            return arrayList;
        }
    }

    private static final class Digest extends java.lang.Record {
        private final int algorithm;
        private final byte[] value;

        private Digest(int algorithm, byte[] value) {
            this.algorithm = algorithm;
            this.value = value;
        }

        public int algorithm() {
            return this.algorithm;
        }

        @Override // java.lang.Record
        public final boolean equals(java.lang.Object obj) {
            return (boolean) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "equals", java.lang.invoke.MethodType.methodType(java.lang.Boolean.TYPE, com.android.server.BinaryTransparencyService.Digest.class, java.lang.Object.class), com.android.server.BinaryTransparencyService.Digest.class, "algorithm;value", "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->algorithm:I", "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->value:[B").dynamicInvoker().invoke(this, obj) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "hashCode", java.lang.invoke.MethodType.methodType(java.lang.Integer.TYPE, com.android.server.BinaryTransparencyService.Digest.class), com.android.server.BinaryTransparencyService.Digest.class, "algorithm;value", "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->algorithm:I", "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->value:[B").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final java.lang.String toString() {
            return (java.lang.String) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "toString", java.lang.invoke.MethodType.methodType(java.lang.String.class, com.android.server.BinaryTransparencyService.Digest.class), com.android.server.BinaryTransparencyService.Digest.class, "algorithm;value", "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->algorithm:I", "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->value:[B").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public byte[] value() {
            return this.value;
        }
    }
}
