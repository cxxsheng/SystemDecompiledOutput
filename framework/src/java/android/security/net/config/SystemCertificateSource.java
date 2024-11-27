package android.security.net.config;

/* loaded from: classes3.dex */
public final class SystemCertificateSource extends android.security.net.config.DirectoryCertificateSource {
    private final java.io.File mUserRemovedCaDir;

    private static class NoPreloadHolder {
        private static final android.security.net.config.SystemCertificateSource INSTANCE = new android.security.net.config.SystemCertificateSource();

        private NoPreloadHolder() {
        }
    }

    @Override // android.security.net.config.DirectoryCertificateSource, android.security.net.config.CertificateSource
    public /* bridge */ /* synthetic */ java.util.Set findAllByIssuerAndSignature(java.security.cert.X509Certificate x509Certificate) {
        return super.findAllByIssuerAndSignature(x509Certificate);
    }

    @Override // android.security.net.config.DirectoryCertificateSource, android.security.net.config.CertificateSource
    public /* bridge */ /* synthetic */ java.security.cert.X509Certificate findByIssuerAndSignature(java.security.cert.X509Certificate x509Certificate) {
        return super.findByIssuerAndSignature(x509Certificate);
    }

    @Override // android.security.net.config.DirectoryCertificateSource, android.security.net.config.CertificateSource
    public /* bridge */ /* synthetic */ java.security.cert.X509Certificate findBySubjectAndPublicKey(java.security.cert.X509Certificate x509Certificate) {
        return super.findBySubjectAndPublicKey(x509Certificate);
    }

    @Override // android.security.net.config.DirectoryCertificateSource, android.security.net.config.CertificateSource
    public /* bridge */ /* synthetic */ java.util.Set getCertificates() {
        return super.getCertificates();
    }

    @Override // android.security.net.config.DirectoryCertificateSource, android.security.net.config.CertificateSource
    public /* bridge */ /* synthetic */ void handleTrustStorageUpdate() {
        super.handleTrustStorageUpdate();
    }

    private SystemCertificateSource() {
        super(getDirectory());
        this.mUserRemovedCaDir = new java.io.File(android.os.Environment.getUserConfigDirectory(android.os.UserHandle.myUserId()), "cacerts-removed");
    }

    private static java.io.File getDirectory() {
        if (java.lang.System.getProperty("system.certs.enabled") != null && java.lang.System.getProperty("system.certs.enabled").equals("true")) {
            return new java.io.File(java.lang.System.getenv("ANDROID_ROOT") + "/etc/security/cacerts");
        }
        java.io.File file = new java.io.File("/apex/com.android.conscrypt/cacerts");
        if (file.exists() && file.list().length != 0) {
            return file;
        }
        return new java.io.File(java.lang.System.getenv("ANDROID_ROOT") + "/etc/security/cacerts");
    }

    public static android.security.net.config.SystemCertificateSource getInstance() {
        return android.security.net.config.SystemCertificateSource.NoPreloadHolder.INSTANCE;
    }

    @Override // android.security.net.config.DirectoryCertificateSource
    protected boolean isCertMarkedAsRemoved(java.lang.String str) {
        return new java.io.File(this.mUserRemovedCaDir, str).exists();
    }
}
