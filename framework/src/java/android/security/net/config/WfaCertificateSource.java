package android.security.net.config;

/* loaded from: classes3.dex */
public final class WfaCertificateSource extends android.security.net.config.DirectoryCertificateSource {
    private static final java.lang.String CACERTS_WFA_PATH = "/apex/com.android.wifi/etc/security/cacerts_wfa";

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

    private static class NoPreloadHolder {
        private static final android.security.net.config.WfaCertificateSource INSTANCE = new android.security.net.config.WfaCertificateSource();

        private NoPreloadHolder() {
        }
    }

    private WfaCertificateSource() {
        super(new java.io.File(CACERTS_WFA_PATH));
    }

    public static android.security.net.config.WfaCertificateSource getInstance() {
        return android.security.net.config.WfaCertificateSource.NoPreloadHolder.INSTANCE;
    }

    @Override // android.security.net.config.DirectoryCertificateSource
    protected boolean isCertMarkedAsRemoved(java.lang.String str) {
        return false;
    }
}
