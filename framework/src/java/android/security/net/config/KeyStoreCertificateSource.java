package android.security.net.config;

/* loaded from: classes3.dex */
class KeyStoreCertificateSource implements android.security.net.config.CertificateSource {
    private java.util.Set<java.security.cert.X509Certificate> mCertificates;
    private com.android.org.conscrypt.TrustedCertificateIndex mIndex;
    private final java.security.KeyStore mKeyStore;
    private final java.lang.Object mLock = new java.lang.Object();

    public KeyStoreCertificateSource(java.security.KeyStore keyStore) {
        this.mKeyStore = keyStore;
    }

    @Override // android.security.net.config.CertificateSource
    public java.util.Set<java.security.cert.X509Certificate> getCertificates() {
        ensureInitialized();
        return this.mCertificates;
    }

    private void ensureInitialized() {
        synchronized (this.mLock) {
            if (this.mCertificates != null) {
                return;
            }
            try {
                com.android.org.conscrypt.TrustedCertificateIndex trustedCertificateIndex = new com.android.org.conscrypt.TrustedCertificateIndex();
                android.util.ArraySet arraySet = new android.util.ArraySet(this.mKeyStore.size());
                java.util.Enumeration<java.lang.String> aliases = this.mKeyStore.aliases();
                while (aliases.hasMoreElements()) {
                    java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) this.mKeyStore.getCertificate(aliases.nextElement());
                    if (x509Certificate != null) {
                        arraySet.add(x509Certificate);
                        trustedCertificateIndex.index(x509Certificate);
                    }
                }
                this.mIndex = trustedCertificateIndex;
                this.mCertificates = arraySet;
            } catch (java.security.KeyStoreException e) {
                throw new java.lang.RuntimeException("Failed to load certificates from KeyStore", e);
            }
        }
    }

    @Override // android.security.net.config.CertificateSource
    public java.security.cert.X509Certificate findBySubjectAndPublicKey(java.security.cert.X509Certificate x509Certificate) {
        ensureInitialized();
        java.security.cert.TrustAnchor findBySubjectAndPublicKey = this.mIndex.findBySubjectAndPublicKey(x509Certificate);
        if (findBySubjectAndPublicKey == null) {
            return null;
        }
        return findBySubjectAndPublicKey.getTrustedCert();
    }

    @Override // android.security.net.config.CertificateSource
    public java.security.cert.X509Certificate findByIssuerAndSignature(java.security.cert.X509Certificate x509Certificate) {
        ensureInitialized();
        java.security.cert.TrustAnchor findByIssuerAndSignature = this.mIndex.findByIssuerAndSignature(x509Certificate);
        if (findByIssuerAndSignature == null) {
            return null;
        }
        return findByIssuerAndSignature.getTrustedCert();
    }

    @Override // android.security.net.config.CertificateSource
    public java.util.Set<java.security.cert.X509Certificate> findAllByIssuerAndSignature(java.security.cert.X509Certificate x509Certificate) {
        ensureInitialized();
        java.util.Set findAllByIssuerAndSignature = this.mIndex.findAllByIssuerAndSignature(x509Certificate);
        if (findAllByIssuerAndSignature.isEmpty()) {
            return java.util.Collections.emptySet();
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(findAllByIssuerAndSignature.size());
        java.util.Iterator it = findAllByIssuerAndSignature.iterator();
        while (it.hasNext()) {
            arraySet.add(((java.security.cert.TrustAnchor) it.next()).getTrustedCert());
        }
        return arraySet;
    }

    @Override // android.security.net.config.CertificateSource
    public void handleTrustStorageUpdate() {
    }
}
