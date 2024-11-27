package android.security.keystore;

/* loaded from: classes3.dex */
class DelegatingX509Certificate extends java.security.cert.X509Certificate {
    private final java.security.cert.X509Certificate mDelegate;

    DelegatingX509Certificate(java.security.cert.X509Certificate x509Certificate) {
        this.mDelegate = x509Certificate;
    }

    @Override // java.security.cert.X509Extension
    public java.util.Set<java.lang.String> getCriticalExtensionOIDs() {
        return this.mDelegate.getCriticalExtensionOIDs();
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(java.lang.String str) {
        return this.mDelegate.getExtensionValue(str);
    }

    @Override // java.security.cert.X509Extension
    public java.util.Set<java.lang.String> getNonCriticalExtensionOIDs() {
        return this.mDelegate.getNonCriticalExtensionOIDs();
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        return this.mDelegate.hasUnsupportedCriticalExtension();
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity() throws java.security.cert.CertificateExpiredException, java.security.cert.CertificateNotYetValidException {
        this.mDelegate.checkValidity();
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity(java.util.Date date) throws java.security.cert.CertificateExpiredException, java.security.cert.CertificateNotYetValidException {
        this.mDelegate.checkValidity(date);
    }

    @Override // java.security.cert.X509Certificate
    public int getBasicConstraints() {
        return this.mDelegate.getBasicConstraints();
    }

    @Override // java.security.cert.X509Certificate
    public java.security.Principal getIssuerDN() {
        return this.mDelegate.getIssuerDN();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getIssuerUniqueID() {
        return this.mDelegate.getIssuerUniqueID();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getKeyUsage() {
        return this.mDelegate.getKeyUsage();
    }

    @Override // java.security.cert.X509Certificate
    public java.util.Date getNotAfter() {
        return this.mDelegate.getNotAfter();
    }

    @Override // java.security.cert.X509Certificate
    public java.util.Date getNotBefore() {
        return this.mDelegate.getNotBefore();
    }

    @Override // java.security.cert.X509Certificate
    public java.math.BigInteger getSerialNumber() {
        return this.mDelegate.getSerialNumber();
    }

    @Override // java.security.cert.X509Certificate
    public java.lang.String getSigAlgName() {
        return this.mDelegate.getSigAlgName();
    }

    @Override // java.security.cert.X509Certificate
    public java.lang.String getSigAlgOID() {
        return this.mDelegate.getSigAlgOID();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSigAlgParams() {
        return this.mDelegate.getSigAlgParams();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSignature() {
        return this.mDelegate.getSignature();
    }

    @Override // java.security.cert.X509Certificate
    public java.security.Principal getSubjectDN() {
        return this.mDelegate.getSubjectDN();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getSubjectUniqueID() {
        return this.mDelegate.getSubjectUniqueID();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getTBSCertificate() throws java.security.cert.CertificateEncodingException {
        return this.mDelegate.getTBSCertificate();
    }

    @Override // java.security.cert.X509Certificate
    public int getVersion() {
        return this.mDelegate.getVersion();
    }

    @Override // java.security.cert.Certificate
    public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
        return this.mDelegate.getEncoded();
    }

    @Override // java.security.cert.Certificate
    public java.security.PublicKey getPublicKey() {
        return this.mDelegate.getPublicKey();
    }

    @Override // java.security.cert.Certificate
    public java.lang.String toString() {
        return this.mDelegate.toString();
    }

    @Override // java.security.cert.Certificate
    public void verify(java.security.PublicKey publicKey) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException {
        this.mDelegate.verify(publicKey);
    }

    @Override // java.security.cert.Certificate
    public void verify(java.security.PublicKey publicKey, java.lang.String str) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException {
        this.mDelegate.verify(publicKey, str);
    }

    @Override // java.security.cert.X509Certificate
    public java.util.List<java.lang.String> getExtendedKeyUsage() throws java.security.cert.CertificateParsingException {
        return this.mDelegate.getExtendedKeyUsage();
    }

    @Override // java.security.cert.X509Certificate
    public java.util.Collection<java.util.List<?>> getIssuerAlternativeNames() throws java.security.cert.CertificateParsingException {
        return this.mDelegate.getIssuerAlternativeNames();
    }

    @Override // java.security.cert.X509Certificate
    public javax.security.auth.x500.X500Principal getIssuerX500Principal() {
        return this.mDelegate.getIssuerX500Principal();
    }

    @Override // java.security.cert.X509Certificate
    public java.util.Collection<java.util.List<?>> getSubjectAlternativeNames() throws java.security.cert.CertificateParsingException {
        return this.mDelegate.getSubjectAlternativeNames();
    }

    @Override // java.security.cert.X509Certificate
    public javax.security.auth.x500.X500Principal getSubjectX500Principal() {
        return this.mDelegate.getSubjectX500Principal();
    }
}
