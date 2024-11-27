package com.android.server.integrity;

/* loaded from: classes2.dex */
public class AppIntegrityManagerServiceImpl extends android.content.integrity.IAppIntegrityManager.Stub {
    public static final java.lang.String ADB_INSTALLER = "adb";
    private static final java.lang.String ALLOWED_INSTALLERS_METADATA_NAME = "allowed-installers";
    private static final java.lang.String ALLOWED_INSTALLER_DELIMITER = ",";
    private static final java.lang.String BASE_APK_FILE = "base.apk";
    public static final boolean DEBUG_INTEGRITY_COMPONENT = false;
    private static final java.lang.String INSTALLER_PACKAGE_CERT_DELIMITER = "\\|";
    private static final java.util.Set<java.lang.String> PACKAGE_INSTALLER = new java.util.HashSet(java.util.Arrays.asList("com.google.android.packageinstaller", "com.android.packageinstaller"));
    private static final java.lang.String PACKAGE_MIME_TYPE = "application/vnd.android.package-archive";
    private static final java.lang.String TAG = "AppIntegrityManagerServiceImpl";
    private static final java.lang.String UNKNOWN_INSTALLER = "";
    private final android.content.Context mContext;
    private final com.android.server.integrity.engine.RuleEvaluationEngine mEvaluationEngine;
    private final android.os.Handler mHandler;
    private final com.android.server.integrity.IntegrityFileManager mIntegrityFileManager;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final java.util.function.Supplier<com.android.internal.pm.parsing.PackageParser2> mParserSupplier;

    public static com.android.server.integrity.AppIntegrityManagerServiceImpl create(android.content.Context context) {
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("AppIntegrityManagerServiceHandler");
        handlerThread.start();
        return new com.android.server.integrity.AppIntegrityManagerServiceImpl(context, (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class), new java.util.function.Supplier() { // from class: com.android.server.integrity.AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return com.android.server.pm.parsing.PackageParserUtils.forParsingFileWithDefaults();
            }
        }, com.android.server.integrity.engine.RuleEvaluationEngine.getRuleEvaluationEngine(), com.android.server.integrity.IntegrityFileManager.getInstance(), handlerThread.getThreadHandler());
    }

    @com.android.internal.annotations.VisibleForTesting
    AppIntegrityManagerServiceImpl(android.content.Context context, android.content.pm.PackageManagerInternal packageManagerInternal, java.util.function.Supplier<com.android.internal.pm.parsing.PackageParser2> supplier, com.android.server.integrity.engine.RuleEvaluationEngine ruleEvaluationEngine, com.android.server.integrity.IntegrityFileManager integrityFileManager, android.os.Handler handler) {
        this.mContext = context;
        this.mPackageManagerInternal = packageManagerInternal;
        this.mParserSupplier = supplier;
        this.mEvaluationEngine = ruleEvaluationEngine;
        this.mIntegrityFileManager = integrityFileManager;
        this.mHandler = handler;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_NEEDS_INTEGRITY_VERIFICATION");
        try {
            intentFilter.addDataType(PACKAGE_MIME_TYPE);
            this.mContext.registerReceiver(new com.android.server.integrity.AppIntegrityManagerServiceImpl.AnonymousClass1(), intentFilter, null, this.mHandler);
        } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
            throw new java.lang.RuntimeException("Mime type malformed: should never happen.", e);
        }
    }

    /* renamed from: com.android.server.integrity.AppIntegrityManagerServiceImpl$1, reason: invalid class name */
    class AnonymousClass1 extends android.content.BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, final android.content.Intent intent) {
            if (!"android.intent.action.PACKAGE_NEEDS_INTEGRITY_VERIFICATION".equals(intent.getAction())) {
                return;
            }
            com.android.server.integrity.AppIntegrityManagerServiceImpl.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.integrity.AppIntegrityManagerServiceImpl$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.integrity.AppIntegrityManagerServiceImpl.AnonymousClass1.this.lambda$onReceive$0(intent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(android.content.Intent intent) {
            com.android.server.integrity.AppIntegrityManagerServiceImpl.this.handleIntegrityVerification(intent);
        }
    }

    public void updateRuleSet(final java.lang.String str, final android.content.pm.ParceledListSlice<android.content.integrity.Rule> parceledListSlice, final android.content.IntentSender intentSender) {
        final java.lang.String callerPackageNameOrThrow = getCallerPackageNameOrThrow(android.os.Binder.getCallingUid());
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.integrity.AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.integrity.AppIntegrityManagerServiceImpl.this.lambda$updateRuleSet$0(str, callerPackageNameOrThrow, parceledListSlice, intentSender);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$updateRuleSet$0(java.lang.String str, java.lang.String str2, android.content.pm.ParceledListSlice parceledListSlice, android.content.IntentSender intentSender) {
        boolean z;
        try {
            this.mIntegrityFileManager.writeRules(str, str2, parceledListSlice.getList());
            z = 1;
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Error writing rules.", e);
            z = 0;
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.INTEGRITY_RULES_PUSHED, z, str2, str);
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.content.integrity.extra.STATUS", !z);
        try {
            intentSender.sendIntent(this.mContext, 0, intent, null, null);
        } catch (java.lang.Exception e2) {
            android.util.Slog.e(TAG, "Error sending status feedback.", e2);
        }
    }

    public java.lang.String getCurrentRuleSetVersion() {
        getCallerPackageNameOrThrow(android.os.Binder.getCallingUid());
        com.android.server.integrity.model.RuleMetadata readMetadata = this.mIntegrityFileManager.readMetadata();
        if (readMetadata != null && readMetadata.getVersion() != null) {
            return readMetadata.getVersion();
        }
        return "";
    }

    public java.lang.String getCurrentRuleSetProvider() {
        getCallerPackageNameOrThrow(android.os.Binder.getCallingUid());
        com.android.server.integrity.model.RuleMetadata readMetadata = this.mIntegrityFileManager.readMetadata();
        if (readMetadata != null && readMetadata.getRuleProvider() != null) {
            return readMetadata.getRuleProvider();
        }
        return "";
    }

    public android.content.pm.ParceledListSlice<android.content.integrity.Rule> getCurrentRules() {
        java.util.List<android.content.integrity.Rule> emptyList = java.util.Collections.emptyList();
        try {
            emptyList = this.mIntegrityFileManager.readRules(null);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Error getting current rules", e);
        }
        return new android.content.pm.ParceledListSlice<>(emptyList);
    }

    public java.util.List<java.lang.String> getWhitelistedRuleProviders() {
        return getAllowedRuleProviderSystemApps();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleIntegrityVerification(android.content.Intent intent) {
        int i;
        int intExtra = intent.getIntExtra("android.content.pm.extra.VERIFICATION_ID", -1);
        try {
            java.lang.String installerPackageName = getInstallerPackageName(intent);
            if (!integrityCheckIncludesRuleProvider() && isRuleProvider(installerPackageName)) {
                this.mPackageManagerInternal.setIntegrityVerificationResult(intExtra, 1);
                return;
            }
            java.lang.String stringExtra = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
            android.util.Pair<android.content.pm.SigningDetails, android.os.Bundle> packageSigningAndMetadata = getPackageSigningAndMetadata(intent.getData());
            if (packageSigningAndMetadata == null) {
                android.util.Slog.w(TAG, "Cannot parse package " + stringExtra);
                this.mPackageManagerInternal.setIntegrityVerificationResult(intExtra, 1);
                return;
            }
            android.content.pm.SigningDetails signingDetails = (android.content.pm.SigningDetails) packageSigningAndMetadata.first;
            java.util.List<java.lang.String> certificateFingerprint = getCertificateFingerprint(stringExtra, signingDetails);
            java.util.List<java.lang.String> certificateLineage = getCertificateLineage(stringExtra, signingDetails);
            java.util.List<java.lang.String> installerCertificateFingerprint = getInstallerCertificateFingerprint(installerPackageName);
            android.content.integrity.AppInstallMetadata.Builder builder = new android.content.integrity.AppInstallMetadata.Builder();
            builder.setPackageName(getPackageNameNormalized(stringExtra));
            builder.setAppCertificates(certificateFingerprint);
            builder.setAppCertificateLineage(certificateLineage);
            builder.setVersionCode(intent.getLongExtra("android.intent.extra.LONG_VERSION_CODE", -1L));
            builder.setInstallerName(getPackageNameNormalized(installerPackageName));
            builder.setInstallerCertificates(installerCertificateFingerprint);
            builder.setIsPreInstalled(isSystemApp(stringExtra));
            builder.setAllowedInstallersAndCert(getAllowedInstallers((android.os.Bundle) packageSigningAndMetadata.second));
            extractSourceStamp(intent.getData(), builder);
            android.content.integrity.AppInstallMetadata build = builder.build();
            com.android.server.integrity.model.IntegrityCheckResult evaluate = this.mEvaluationEngine.evaluate(build);
            if (!evaluate.getMatchedRules().isEmpty()) {
                android.util.Slog.i(TAG, java.lang.String.format("Integrity check of %s result: %s due to %s", stringExtra, evaluate.getEffect(), evaluate.getMatchedRules()));
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.INTEGRITY_CHECK_RESULT_REPORTED, stringExtra, certificateFingerprint.toString(), build.getVersionCode(), installerPackageName, evaluate.getLoggingResponse(), evaluate.isCausedByAppCertRule(), evaluate.isCausedByInstallerRule());
            android.content.pm.PackageManagerInternal packageManagerInternal = this.mPackageManagerInternal;
            if (evaluate.getEffect() == com.android.server.integrity.model.IntegrityCheckResult.Effect.ALLOW) {
                i = 1;
            } else {
                i = 0;
            }
            packageManagerInternal.setIntegrityVerificationResult(intExtra, i);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Invalid input to integrity verification", e);
            this.mPackageManagerInternal.setIntegrityVerificationResult(intExtra, 0);
        } catch (java.lang.Exception e2) {
            android.util.Slog.e(TAG, "Error handling integrity verification", e2);
            this.mPackageManagerInternal.setIntegrityVerificationResult(intExtra, 1);
        }
    }

    @android.annotation.Nullable
    private java.lang.String getInstallerPackageName(android.content.Intent intent) {
        java.lang.String stringExtra = intent.getStringExtra("android.content.pm.extra.VERIFICATION_INSTALLER_PACKAGE");
        if (com.android.server.pm.PackageManagerServiceUtils.isInstalledByAdb(stringExtra)) {
            return ADB_INSTALLER;
        }
        int intExtra = intent.getIntExtra("android.content.pm.extra.VERIFICATION_INSTALLER_UID", -1);
        if (intExtra < 0) {
            android.util.Slog.e(TAG, "Installer cannot be determined: installer: " + stringExtra + " installer UID: " + intExtra);
            return "";
        }
        if (!getPackageListForUid(intExtra).contains(stringExtra)) {
            return "";
        }
        if (PACKAGE_INSTALLER.contains(stringExtra)) {
            int intExtra2 = intent.getIntExtra("android.intent.extra.ORIGINATING_UID", -1);
            if (intExtra2 < 0) {
                android.util.Slog.e(TAG, "Installer is package installer but originating UID not found.");
                return "";
            }
            java.util.List<java.lang.String> packageListForUid = getPackageListForUid(intExtra2);
            if (packageListForUid.isEmpty()) {
                android.util.Slog.e(TAG, "No package found associated with originating UID " + intExtra2);
                return "";
            }
            return packageListForUid.get(0);
        }
        return stringExtra;
    }

    private java.lang.String getPackageNameNormalized(java.lang.String str) {
        if (str.length() <= 32) {
            return str;
        }
        try {
            return android.content.integrity.IntegrityUtils.getHexDigest(java.security.MessageDigest.getInstance("SHA-256").digest(str.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private java.util.List<java.lang.String> getInstallerCertificateFingerprint(java.lang.String str) {
        if (str.equals(ADB_INSTALLER) || str.equals("")) {
            return java.util.Collections.emptyList();
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInternal.getPackage(str);
        if (androidPackage == null) {
            android.util.Slog.w(TAG, "Installer package " + str + " not found.");
            return java.util.Collections.emptyList();
        }
        return getCertificateFingerprint(androidPackage.getPackageName(), androidPackage.getSigningDetails());
    }

    private java.util.List<java.lang.String> getCertificateFingerprint(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.SigningDetails signingDetails) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.content.pm.Signature signature : getSignatures(str, signingDetails)) {
            arrayList.add(getFingerprint(signature));
        }
        return arrayList;
    }

    private java.util.List<java.lang.String> getCertificateLineage(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.SigningDetails signingDetails) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.content.pm.Signature signature : getSignatureLineage(str, signingDetails)) {
            arrayList.add(getFingerprint(signature));
        }
        return arrayList;
    }

    private java.util.Map<java.lang.String, java.lang.String> getAllowedInstallers(@android.annotation.Nullable android.os.Bundle bundle) {
        java.lang.String string;
        java.util.HashMap hashMap = new java.util.HashMap();
        if (bundle != null && (string = bundle.getString(ALLOWED_INSTALLERS_METADATA_NAME)) != null) {
            for (java.lang.String str : string.split(ALLOWED_INSTALLER_DELIMITER)) {
                java.lang.String[] split = str.split(INSTALLER_PACKAGE_CERT_DELIMITER);
                if (split.length == 2) {
                    hashMap.put(getPackageNameNormalized(split[0]), split[1]);
                } else if (split.length == 1) {
                    hashMap.put(getPackageNameNormalized(split[0]), "");
                }
            }
        }
        return hashMap;
    }

    private void extractSourceStamp(android.net.Uri uri, android.content.integrity.AppInstallMetadata.Builder builder) {
        android.util.apk.SourceStampVerificationResult verify;
        java.io.File installationPath = getInstallationPath(uri);
        if (installationPath == null) {
            throw new java.lang.IllegalArgumentException("Installation path is null, package not found");
        }
        if (installationPath.isDirectory()) {
            try {
                java.util.stream.Stream<java.nio.file.Path> list = java.nio.file.Files.list(installationPath.toPath());
                try {
                    verify = android.util.apk.SourceStampVerifier.verify((java.util.List) list.map(new java.util.function.Function() { // from class: com.android.server.integrity.AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            java.lang.String lambda$extractSourceStamp$1;
                            lambda$extractSourceStamp$1 = com.android.server.integrity.AppIntegrityManagerServiceImpl.lambda$extractSourceStamp$1((java.nio.file.Path) obj);
                            return lambda$extractSourceStamp$1;
                        }
                    }).filter(new java.util.function.Predicate() { // from class: com.android.server.integrity.AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$extractSourceStamp$2;
                            lambda$extractSourceStamp$2 = com.android.server.integrity.AppIntegrityManagerServiceImpl.lambda$extractSourceStamp$2((java.lang.String) obj);
                            return lambda$extractSourceStamp$2;
                        }
                    }).collect(java.util.stream.Collectors.toList()));
                    list.close();
                } finally {
                }
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("Could not read APK directory");
            }
        } else {
            verify = android.util.apk.SourceStampVerifier.verify(installationPath.getAbsolutePath());
        }
        builder.setIsStampPresent(verify.isPresent());
        builder.setIsStampVerified(verify.isVerified());
        builder.setIsStampTrusted(verify.isVerified());
        if (verify.isVerified()) {
            try {
                builder.setStampCertificateHash(android.content.integrity.IntegrityUtils.getHexDigest(java.security.MessageDigest.getInstance("SHA-256").digest(((java.security.cert.X509Certificate) verify.getCertificate()).getEncoded())));
            } catch (java.security.NoSuchAlgorithmException | java.security.cert.CertificateEncodingException e2) {
                throw new java.lang.IllegalArgumentException("Error computing source stamp certificate digest", e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$extractSourceStamp$1(java.nio.file.Path path) {
        return path.toAbsolutePath().toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$extractSourceStamp$2(java.lang.String str) {
        return str.endsWith(".apk");
    }

    private static android.content.pm.Signature[] getSignatures(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.SigningDetails signingDetails) {
        android.content.pm.Signature[] signatures = signingDetails.getSignatures();
        if (signatures == null || signatures.length < 1) {
            throw new java.lang.IllegalArgumentException("Package signature not found in " + str);
        }
        return signatures;
    }

    private static android.content.pm.Signature[] getSignatureLineage(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.SigningDetails signingDetails) {
        android.content.pm.Signature[] signatures = getSignatures(str, signingDetails);
        android.content.pm.Signature[] pastSigningCertificates = signingDetails.getPastSigningCertificates();
        if (signatures.length == 1 && !com.android.internal.util.ArrayUtils.isEmpty(pastSigningCertificates)) {
            android.content.pm.Signature[] signatureArr = new android.content.pm.Signature[signatures.length + pastSigningCertificates.length];
            int i = 0;
            while (i < signatures.length) {
                signatureArr[i] = signatures[i];
                i++;
            }
            for (android.content.pm.Signature signature : pastSigningCertificates) {
                signatureArr[i] = signature;
                i++;
            }
            return signatureArr;
        }
        return signatures;
    }

    private static java.lang.String getFingerprint(android.content.pm.Signature signature) {
        java.security.cert.X509Certificate x509Certificate;
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(signature.toByteArray());
        try {
            java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X509");
            if (certificateFactory == null) {
                x509Certificate = null;
            } else {
                try {
                    x509Certificate = (java.security.cert.X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream);
                } catch (java.security.cert.CertificateException e) {
                    throw new java.lang.RuntimeException("Error getting X509Certificate", e);
                }
            }
            if (x509Certificate == null) {
                throw new java.lang.RuntimeException("X509 Certificate not found");
            }
            try {
                return android.content.integrity.IntegrityUtils.getHexDigest(java.security.MessageDigest.getInstance("SHA-256").digest(x509Certificate.getEncoded()));
            } catch (java.security.NoSuchAlgorithmException | java.security.cert.CertificateEncodingException e2) {
                throw new java.lang.IllegalArgumentException("Error error computing fingerprint", e2);
            }
        } catch (java.security.cert.CertificateException e3) {
            throw new java.lang.RuntimeException("Error getting CertificateFactory", e3);
        }
    }

    @android.annotation.Nullable
    private android.util.Pair<android.content.pm.SigningDetails, android.os.Bundle> getPackageSigningAndMetadata(android.net.Uri uri) {
        java.io.File installationPath = getInstallationPath(uri);
        if (installationPath == null) {
            throw new java.lang.IllegalArgumentException("Installation path is null, package not found");
        }
        try {
            com.android.internal.pm.parsing.PackageParser2 packageParser2 = this.mParserSupplier.get();
            try {
                com.android.internal.pm.parsing.pkg.ParsedPackage parsePackage = packageParser2.parsePackage(installationPath, 0, false);
                android.content.pm.parsing.result.ParseResult signingDetails = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.getSigningDetails(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing(), parsePackage, true);
                if (signingDetails.isError()) {
                    android.util.Slog.w(TAG, signingDetails.getErrorMessage(), signingDetails.getException());
                    packageParser2.close();
                    return null;
                }
                android.util.Pair<android.content.pm.SigningDetails, android.os.Bundle> create = android.util.Pair.create((android.content.pm.SigningDetails) signingDetails.getResult(), parsePackage.getMetaData());
                packageParser2.close();
                return create;
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Exception reading " + uri, e);
            return null;
        }
    }

    private android.content.pm.PackageInfo getMultiApkInfo(java.io.File file) {
        android.content.pm.PackageInfo packageArchiveInfo = this.mContext.getPackageManager().getPackageArchiveInfo(new java.io.File(file, BASE_APK_FILE).getAbsolutePath(), 134217856);
        if (packageArchiveInfo == null) {
            java.io.File[] listFiles = file.listFiles();
            int length = listFiles.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                java.io.File file2 = listFiles[i];
                if (!file2.isDirectory()) {
                    try {
                        packageArchiveInfo = this.mContext.getPackageManager().getPackageArchiveInfo(file2.getAbsolutePath(), 134217856);
                    } catch (java.lang.Exception e) {
                        android.util.Slog.w(TAG, "Exception reading " + file2, e);
                    }
                    if (packageArchiveInfo != null) {
                        android.util.Slog.i(TAG, "Found package info from " + file2);
                        break;
                    }
                }
                i++;
            }
        }
        if (packageArchiveInfo == null) {
            throw new java.lang.IllegalArgumentException("Base package info cannot be found from installation directory");
        }
        return packageArchiveInfo;
    }

    private java.io.File getInstallationPath(android.net.Uri uri) {
        if (uri == null) {
            throw new java.lang.IllegalArgumentException("Null data uri");
        }
        if (!"file".equalsIgnoreCase(uri.getScheme())) {
            throw new java.lang.IllegalArgumentException("Unsupported scheme for " + uri);
        }
        java.io.File file = new java.io.File(uri.getPath());
        if (!file.exists()) {
            throw new java.lang.IllegalArgumentException("Cannot find file for " + uri);
        }
        if (!file.canRead()) {
            throw new java.lang.IllegalArgumentException("Cannot read file for " + uri);
        }
        return file;
    }

    private java.lang.String getCallerPackageNameOrThrow(int i) {
        java.lang.String callingRulePusherPackageName = getCallingRulePusherPackageName(i);
        if (callingRulePusherPackageName == null) {
            throw new java.lang.SecurityException("Only system packages specified in config_integrityRuleProviderPackages are allowed to call this method.");
        }
        return callingRulePusherPackageName;
    }

    private java.lang.String getCallingRulePusherPackageName(int i) {
        java.util.List<java.lang.String> allowedRuleProviderSystemApps = getAllowedRuleProviderSystemApps();
        java.util.List<java.lang.String> packageListForUid = getPackageListForUid(i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : packageListForUid) {
            if (allowedRuleProviderSystemApps.contains(str)) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return (java.lang.String) arrayList.get(0);
    }

    private boolean isRuleProvider(java.lang.String str) {
        java.util.Iterator<java.lang.String> it = getAllowedRuleProviderSystemApps().iterator();
        while (it.hasNext()) {
            if (it.next().matches(str)) {
                return true;
            }
        }
        return false;
    }

    private java.util.List<java.lang.String> getAllowedRuleProviderSystemApps() {
        java.util.List<java.lang.String> asList = java.util.Arrays.asList(this.mContext.getResources().getStringArray(android.R.array.config_highAmbientBrightnessThresholdsOfFixedRefreshRate));
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : asList) {
            if (isSystemApp(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    private boolean isSystemApp(java.lang.String str) {
        try {
            android.content.pm.PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo(str, 0);
            if (packageInfo.applicationInfo != null) {
                return packageInfo.applicationInfo.isSystemApp();
            }
            return false;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private boolean integrityCheckIncludesRuleProvider() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "verify_integrity_for_rule_provider", 0) == 1;
    }

    private java.util.List<java.lang.String> getPackageListForUid(int i) {
        try {
            return java.util.Arrays.asList(this.mContext.getPackageManager().getPackagesForUid(i));
        } catch (java.lang.NullPointerException e) {
            android.util.Slog.w(TAG, java.lang.String.format("No packages were found for uid: %d", java.lang.Integer.valueOf(i)));
            return java.util.List.of();
        }
    }
}
