package android.security.net.config;

/* loaded from: classes3.dex */
public final class CertificatesEntryRef {
    private final boolean mOverridesPins;
    private final android.security.net.config.CertificateSource mSource;

    public CertificatesEntryRef(android.security.net.config.CertificateSource certificateSource, boolean z) {
        this.mSource = certificateSource;
        this.mOverridesPins = z;
    }

    boolean overridesPins() {
        return this.mOverridesPins;
    }

    public java.util.Set<android.security.net.config.TrustAnchor> getTrustAnchors() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<java.security.cert.X509Certificate> it = this.mSource.getCertificates().iterator();
        while (it.hasNext()) {
            arraySet.add(new android.security.net.config.TrustAnchor(it.next(), this.mOverridesPins));
        }
        return arraySet;
    }

    public android.security.net.config.TrustAnchor findBySubjectAndPublicKey(java.security.cert.X509Certificate x509Certificate) {
        java.security.cert.X509Certificate findBySubjectAndPublicKey = this.mSource.findBySubjectAndPublicKey(x509Certificate);
        if (findBySubjectAndPublicKey == null) {
            return null;
        }
        return new android.security.net.config.TrustAnchor(findBySubjectAndPublicKey, this.mOverridesPins);
    }

    public android.security.net.config.TrustAnchor findByIssuerAndSignature(java.security.cert.X509Certificate x509Certificate) {
        java.security.cert.X509Certificate findByIssuerAndSignature = this.mSource.findByIssuerAndSignature(x509Certificate);
        if (findByIssuerAndSignature == null) {
            return null;
        }
        return new android.security.net.config.TrustAnchor(findByIssuerAndSignature, this.mOverridesPins);
    }

    public java.util.Set<java.security.cert.X509Certificate> findAllCertificatesByIssuerAndSignature(java.security.cert.X509Certificate x509Certificate) {
        return this.mSource.findAllByIssuerAndSignature(x509Certificate);
    }

    public void handleTrustStorageUpdate() {
        this.mSource.handleTrustStorageUpdate();
    }
}
