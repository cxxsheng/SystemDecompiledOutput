package com.android.server.security;

/* loaded from: classes2.dex */
public class FileIntegrityService extends com.android.server.SystemService {
    private static final int MAX_SIGNATURE_FILE_SIZE_BYTES = 8192;
    private static final java.lang.String TAG = "FileIntegrityService";
    private static java.security.cert.CertificateFactory sCertFactory;
    private final android.os.IBinder mService;

    @com.android.internal.annotations.GuardedBy({"mTrustedCertificates"})
    private final java.util.ArrayList<java.security.cert.X509Certificate> mTrustedCertificates;

    public static com.android.server.security.FileIntegrityService getService() {
        return (com.android.server.security.FileIntegrityService) com.android.server.LocalServices.getService(com.android.server.security.FileIntegrityService.class);
    }

    public FileIntegrityService(android.content.Context context) {
        super(context);
        this.mTrustedCertificates = new java.util.ArrayList<>();
        this.mService = new android.security.IFileIntegrityService.Stub() { // from class: com.android.server.security.FileIntegrityService.1
            public boolean isApkVeritySupported() {
                return com.android.internal.security.VerityUtils.isFsVeritySupported();
            }

            public boolean isAppSourceCertificateTrusted(@android.annotation.Nullable byte[] bArr, @android.annotation.NonNull java.lang.String str) {
                boolean contains;
                checkCallerPermission(str);
                if (android.security.Flags.deprecateFsvSig()) {
                    return false;
                }
                try {
                    if (!com.android.internal.security.VerityUtils.isFsVeritySupported()) {
                        return false;
                    }
                    if (bArr == null) {
                        android.util.Slog.w(com.android.server.security.FileIntegrityService.TAG, "Received a null certificate");
                        return false;
                    }
                    synchronized (com.android.server.security.FileIntegrityService.this.mTrustedCertificates) {
                        contains = com.android.server.security.FileIntegrityService.this.mTrustedCertificates.contains(com.android.server.security.FileIntegrityService.toCertificate(bArr));
                    }
                    return contains;
                } catch (java.security.cert.CertificateException e) {
                    android.util.Slog.e(com.android.server.security.FileIntegrityService.TAG, "Failed to convert the certificate: " + e);
                    return false;
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
                new com.android.server.security.FileIntegrityService.FileIntegrityServiceShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }

            private void checkCallerPackageName(java.lang.String str) {
                int callingUid = android.os.Binder.getCallingUid();
                if (callingUid != ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackageUid(str, 0L, android.os.UserHandle.getUserId(callingUid))) {
                    throw new java.lang.SecurityException("Calling uid " + callingUid + " does not own package " + str);
                }
            }

            private void checkCallerPermission(java.lang.String str) {
                checkCallerPackageName(str);
                if (com.android.server.security.FileIntegrityService.this.getContext().checkCallingPermission("android.permission.INSTALL_PACKAGES") != 0 && ((android.app.AppOpsManager) com.android.server.security.FileIntegrityService.this.getContext().getSystemService(android.app.AppOpsManager.class)).checkOpNoThrow(66, android.os.Binder.getCallingUid(), str) != 0) {
                    throw new java.lang.SecurityException("Caller should have INSTALL_PACKAGES or REQUEST_INSTALL_PACKAGES");
                }
            }

            public android.os.IInstalld.IFsveritySetupAuthToken createAuthToken(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                java.util.Objects.requireNonNull(parcelFileDescriptor);
                try {
                    android.os.IInstalld.IFsveritySetupAuthToken createFsveritySetupAuthToken = com.android.server.security.FileIntegrityService.this.getStorageManagerInternal().createFsveritySetupAuthToken(parcelFileDescriptor, android.os.Binder.getCallingUid());
                    parcelFileDescriptor.close();
                    return createFsveritySetupAuthToken;
                } catch (java.io.IOException e) {
                    throw new android.os.RemoteException(e);
                }
            }

            public int setupFsverity(android.os.IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                java.util.Objects.requireNonNull(iFsveritySetupAuthToken);
                java.util.Objects.requireNonNull(str);
                java.util.Objects.requireNonNull(str2);
                checkCallerPackageName(str2);
                try {
                    return com.android.server.security.FileIntegrityService.this.getStorageManagerInternal().enableFsverity(iFsveritySetupAuthToken, str, str2);
                } catch (java.io.IOException e) {
                    throw new android.os.RemoteException(e);
                }
            }
        };
        try {
            sCertFactory = java.security.cert.CertificateFactory.getInstance("X.509");
        } catch (java.security.cert.CertificateException e) {
            android.util.Slog.wtf(TAG, "Cannot get an instance of X.509 certificate factory");
        }
        com.android.server.LocalServices.addService(com.android.server.security.FileIntegrityService.class, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.storage.StorageManagerInternal getStorageManagerInternal() {
        return (android.os.storage.StorageManagerInternal) com.android.server.LocalServices.getService(android.os.storage.StorageManagerInternal.class);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        loadAllCertificates();
        publishBinderService("file_integrity", this.mService);
    }

    public boolean verifyPkcs7DetachedSignature(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        if (java.nio.file.Files.size(java.nio.file.Paths.get(str, new java.lang.String[0])) > 8192) {
            throw new java.lang.SecurityException("Signature file is unexpectedly large: " + str);
        }
        byte[] readAllBytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(str, new java.lang.String[0]));
        byte[] fsverityDigest = com.android.internal.security.VerityUtils.getFsverityDigest(str2);
        synchronized (this.mTrustedCertificates) {
            try {
                java.util.Iterator<java.security.cert.X509Certificate> it = this.mTrustedCertificates.iterator();
                while (it.hasNext()) {
                    try {
                    } catch (java.security.cert.CertificateEncodingException e) {
                        android.util.Slog.w(TAG, "Ignoring ill-formed certificate: " + e);
                    }
                    if (com.android.internal.security.VerityUtils.verifyPkcs7DetachedSignature(readAllBytes, fsverityDigest, new java.io.ByteArrayInputStream(it.next().getEncoded()))) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void loadAllCertificates() {
        loadCertificatesFromDirectory(android.os.Environment.getRootDirectory().toPath().resolve("etc/security/fsverity"));
        loadCertificatesFromDirectory(android.os.Environment.getProductDirectory().toPath().resolve("etc/security/fsverity"));
    }

    private void loadCertificatesFromDirectory(java.nio.file.Path path) {
        try {
            java.io.File[] listFiles = path.toFile().listFiles();
            if (listFiles == null) {
                return;
            }
            for (java.io.File file : listFiles) {
                collectCertificate(java.nio.file.Files.readAllBytes(file.toPath()));
            }
        } catch (java.io.IOException e) {
            android.util.Slog.wtf(TAG, "Failed to load fs-verity certificate from " + path, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void collectCertificate(@android.annotation.NonNull byte[] bArr) {
        try {
            synchronized (this.mTrustedCertificates) {
                this.mTrustedCertificates.add(toCertificate(bArr));
            }
        } catch (java.security.cert.CertificateException e) {
            android.util.Slog.e(TAG, "Invalid certificate, ignored: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public static java.security.cert.X509Certificate toCertificate(@android.annotation.NonNull byte[] bArr) throws java.security.cert.CertificateException {
        java.security.cert.Certificate generateCertificate = sCertFactory.generateCertificate(new java.io.ByteArrayInputStream(bArr));
        if (!(generateCertificate instanceof java.security.cert.X509Certificate)) {
            throw new java.security.cert.CertificateException("Expected to contain an X.509 certificate");
        }
        return (java.security.cert.X509Certificate) generateCertificate;
    }

    private class FileIntegrityServiceShellCommand extends android.os.ShellCommand {
        private FileIntegrityServiceShellCommand() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int onCommand(java.lang.String str) {
            boolean z;
            if (!android.os.Build.IS_DEBUGGABLE) {
                return -1;
            }
            if (str == null) {
                return handleDefaultCommands(str);
            }
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            switch (str.hashCode()) {
                case -1932837641:
                    if (str.equals("append-cert")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case 755125490:
                    if (str.equals("remove-last-cert")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    java.lang.String nextArg = getNextArg();
                    if (nextArg == null) {
                        outPrintWriter.println("Invalid argument");
                        outPrintWriter.println("");
                        onHelp();
                        return -1;
                    }
                    android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(nextArg, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
                    if (openFileForSystem == null) {
                        outPrintWriter.println("Cannot open the file");
                        return -1;
                    }
                    try {
                        com.android.server.security.FileIntegrityService.this.collectCertificate(new android.os.ParcelFileDescriptor.AutoCloseInputStream(openFileForSystem).readAllBytes());
                        outPrintWriter.println("Certificate is added successfully");
                        return 0;
                    } catch (java.io.IOException e) {
                        outPrintWriter.println("Failed to add certificate: " + e);
                        return -1;
                    }
                case true:
                    synchronized (com.android.server.security.FileIntegrityService.this.mTrustedCertificates) {
                        try {
                            if (com.android.server.security.FileIntegrityService.this.mTrustedCertificates.size() == 0) {
                                outPrintWriter.println("Certificate list is already empty");
                                return -1;
                            }
                            com.android.server.security.FileIntegrityService.this.mTrustedCertificates.remove(com.android.server.security.FileIntegrityService.this.mTrustedCertificates.size() - 1);
                            outPrintWriter.println("Certificate is removed successfully");
                            return 0;
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                default:
                    outPrintWriter.println("Unknown action");
                    outPrintWriter.println("");
                    onHelp();
                    return -1;
            }
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("File integrity service commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  append-cert path/to/cert.der");
            outPrintWriter.println("    Add the DER-encoded certificate (only in debug builds)");
            outPrintWriter.println("  remove-last-cert");
            outPrintWriter.println("    Remove the last certificate in the key list (only in debug builds)");
            outPrintWriter.println("");
        }
    }
}
