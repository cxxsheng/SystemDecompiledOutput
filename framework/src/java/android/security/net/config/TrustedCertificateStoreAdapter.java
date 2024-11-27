package android.security.net.config;

/* loaded from: classes3.dex */
public class TrustedCertificateStoreAdapter extends com.android.org.conscrypt.TrustedCertificateStore {
    private final android.security.net.config.NetworkSecurityConfig mConfig;

    public TrustedCertificateStoreAdapter(android.security.net.config.NetworkSecurityConfig networkSecurityConfig) {
        this.mConfig = networkSecurityConfig;
    }

    public java.security.cert.X509Certificate findIssuer(java.security.cert.X509Certificate x509Certificate) {
        android.security.net.config.TrustAnchor findTrustAnchorByIssuerAndSignature = this.mConfig.findTrustAnchorByIssuerAndSignature(x509Certificate);
        if (findTrustAnchorByIssuerAndSignature == null) {
            return null;
        }
        return findTrustAnchorByIssuerAndSignature.certificate;
    }

    public java.util.Set<java.security.cert.X509Certificate> findAllIssuers(java.security.cert.X509Certificate x509Certificate) {
        return this.mConfig.findAllCertificatesByIssuerAndSignature(x509Certificate);
    }

    public java.security.cert.X509Certificate getTrustAnchor(java.security.cert.X509Certificate x509Certificate) {
        android.security.net.config.TrustAnchor findTrustAnchorBySubjectAndPublicKey = this.mConfig.findTrustAnchorBySubjectAndPublicKey(x509Certificate);
        if (findTrustAnchorBySubjectAndPublicKey == null) {
            return null;
        }
        return findTrustAnchorBySubjectAndPublicKey.certificate;
    }

    public boolean isUserAddedCertificate(java.security.cert.X509Certificate x509Certificate) {
        android.security.net.config.TrustAnchor findTrustAnchorBySubjectAndPublicKey = this.mConfig.findTrustAnchorBySubjectAndPublicKey(x509Certificate);
        if (findTrustAnchorBySubjectAndPublicKey == null) {
            return false;
        }
        return findTrustAnchorBySubjectAndPublicKey.overridesPins;
    }

    public java.io.File getCertificateFile(java.io.File file, java.security.cert.X509Certificate x509Certificate) {
        throw new java.lang.UnsupportedOperationException();
    }

    public java.security.cert.Certificate getCertificate(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    public java.security.cert.Certificate getCertificate(java.lang.String str, boolean z) {
        throw new java.lang.UnsupportedOperationException();
    }

    public java.util.Date getCreationDate(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    public java.util.Set<java.lang.String> aliases() {
        throw new java.lang.UnsupportedOperationException();
    }

    public java.util.Set<java.lang.String> userAliases() {
        throw new java.lang.UnsupportedOperationException();
    }

    public java.util.Set<java.lang.String> allSystemAliases() {
        throw new java.lang.UnsupportedOperationException();
    }

    public boolean containsAlias(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    public java.lang.String getCertificateAlias(java.security.cert.Certificate certificate) {
        throw new java.lang.UnsupportedOperationException();
    }

    public java.lang.String getCertificateAlias(java.security.cert.Certificate certificate, boolean z) {
        throw new java.lang.UnsupportedOperationException();
    }
}
