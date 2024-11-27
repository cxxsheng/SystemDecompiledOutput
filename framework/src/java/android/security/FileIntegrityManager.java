package android.security;

/* loaded from: classes3.dex */
public final class FileIntegrityManager {
    private final android.content.Context mContext;
    private final android.security.IFileIntegrityService mService;

    public FileIntegrityManager(android.content.Context context, android.security.IFileIntegrityService iFileIntegrityService) {
        this.mContext = context;
        this.mService = iFileIntegrityService;
    }

    public boolean isApkVeritySupported() {
        try {
            return this.mService.isApkVeritySupported();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setupFsVerity(java.io.File file) throws java.io.IOException {
        if (!file.isAbsolute()) {
            throw new java.lang.IllegalArgumentException("Expect an absolute path");
        }
        try {
            android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(file, 805306368);
            try {
                android.os.IInstalld.IFsveritySetupAuthToken createAuthToken = this.mService.createAuthToken(open);
                if (open != null) {
                    open.close();
                }
                try {
                    int i = this.mService.setupFsverity(createAuthToken, file.getPath(), this.mContext.getPackageName());
                    if (i != 0) {
                        new android.system.ErrnoException("setupFsVerity", i).rethrowAsIOException();
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
            }
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public byte[] getFsVerityDigest(java.io.File file) throws java.io.IOException {
        return com.android.internal.security.VerityUtils.getFsverityDigest(file.getPath());
    }

    @java.lang.Deprecated
    public boolean isAppSourceCertificateTrusted(java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertificateEncodingException {
        try {
            return this.mService.isAppSourceCertificateTrusted(x509Certificate.getEncoded(), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
