package android.security.net.config;

/* loaded from: classes3.dex */
public class ResourceCertificateSource implements android.security.net.config.CertificateSource {
    private java.util.Set<java.security.cert.X509Certificate> mCertificates;
    private android.content.Context mContext;
    private com.android.org.conscrypt.TrustedCertificateIndex mIndex;
    private final java.lang.Object mLock = new java.lang.Object();
    private final int mResourceId;

    public ResourceCertificateSource(int i, android.content.Context context) {
        this.mResourceId = i;
        this.mContext = context;
    }

    private void ensureInitialized() {
        synchronized (this.mLock) {
            if (this.mCertificates != null) {
                return;
            }
            android.util.ArraySet arraySet = new android.util.ArraySet();
            java.io.InputStream inputStream = null;
            try {
                try {
                    java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
                    java.io.InputStream openRawResource = this.mContext.getResources().openRawResource(this.mResourceId);
                    try {
                        java.util.Collection<? extends java.security.cert.Certificate> generateCertificates = certificateFactory.generateCertificates(openRawResource);
                        libcore.io.IoUtils.closeQuietly(openRawResource);
                        com.android.org.conscrypt.TrustedCertificateIndex trustedCertificateIndex = new com.android.org.conscrypt.TrustedCertificateIndex();
                        for (java.security.cert.Certificate certificate : generateCertificates) {
                            arraySet.add((java.security.cert.X509Certificate) certificate);
                            trustedCertificateIndex.index((java.security.cert.X509Certificate) certificate);
                        }
                        this.mCertificates = arraySet;
                        this.mIndex = trustedCertificateIndex;
                        this.mContext = null;
                    } catch (java.security.cert.CertificateException e) {
                        e = e;
                        inputStream = openRawResource;
                        throw new java.lang.RuntimeException("Failed to load trust anchors from id " + this.mResourceId, e);
                    } catch (java.lang.Throwable th) {
                        th = th;
                        inputStream = openRawResource;
                        libcore.io.IoUtils.closeQuietly(inputStream);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                }
            } catch (java.security.cert.CertificateException e2) {
                e = e2;
            }
        }
    }

    @Override // android.security.net.config.CertificateSource
    public java.util.Set<java.security.cert.X509Certificate> getCertificates() {
        ensureInitialized();
        return this.mCertificates;
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
