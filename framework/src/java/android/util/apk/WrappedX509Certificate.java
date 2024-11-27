package android.util.apk;

/* loaded from: classes3.dex */
class WrappedX509Certificate extends java.security.cert.X509Certificate {
    private final java.security.cert.X509Certificate mWrapped;

    WrappedX509Certificate(java.security.cert.X509Certificate x509Certificate) {
        this.mWrapped = x509Certificate;
    }

    @Override // java.security.cert.X509Extension
    public java.util.Set<java.lang.String> getCriticalExtensionOIDs() {
        return this.mWrapped.getCriticalExtensionOIDs();
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(java.lang.String str) {
        return this.mWrapped.getExtensionValue(str);
    }

    @Override // java.security.cert.X509Extension
    public java.util.Set<java.lang.String> getNonCriticalExtensionOIDs() {
        return this.mWrapped.getNonCriticalExtensionOIDs();
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        return this.mWrapped.hasUnsupportedCriticalExtension();
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity() throws java.security.cert.CertificateExpiredException, java.security.cert.CertificateNotYetValidException {
        this.mWrapped.checkValidity();
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity(java.util.Date date) throws java.security.cert.CertificateExpiredException, java.security.cert.CertificateNotYetValidException {
        this.mWrapped.checkValidity(date);
    }

    @Override // java.security.cert.X509Certificate
    public int getVersion() {
        return this.mWrapped.getVersion();
    }

    @Override // java.security.cert.X509Certificate
    public java.math.BigInteger getSerialNumber() {
        return this.mWrapped.getSerialNumber();
    }

    @Override // java.security.cert.X509Certificate
    public java.security.Principal getIssuerDN() {
        return this.mWrapped.getIssuerDN();
    }

    @Override // java.security.cert.X509Certificate
    public java.security.Principal getSubjectDN() {
        return this.mWrapped.getSubjectDN();
    }

    @Override // java.security.cert.X509Certificate
    public java.util.Date getNotBefore() {
        return this.mWrapped.getNotBefore();
    }

    @Override // java.security.cert.X509Certificate
    public java.util.Date getNotAfter() {
        return this.mWrapped.getNotAfter();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getTBSCertificate() throws java.security.cert.CertificateEncodingException {
        return this.mWrapped.getTBSCertificate();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSignature() {
        return this.mWrapped.getSignature();
    }

    @Override // java.security.cert.X509Certificate
    public java.lang.String getSigAlgName() {
        return this.mWrapped.getSigAlgName();
    }

    @Override // java.security.cert.X509Certificate
    public java.lang.String getSigAlgOID() {
        return this.mWrapped.getSigAlgOID();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSigAlgParams() {
        return this.mWrapped.getSigAlgParams();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getIssuerUniqueID() {
        return this.mWrapped.getIssuerUniqueID();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getSubjectUniqueID() {
        return this.mWrapped.getSubjectUniqueID();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getKeyUsage() {
        return this.mWrapped.getKeyUsage();
    }

    @Override // java.security.cert.X509Certificate
    public int getBasicConstraints() {
        return this.mWrapped.getBasicConstraints();
    }

    @Override // java.security.cert.Certificate
    public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
        return this.mWrapped.getEncoded();
    }

    @Override // java.security.cert.Certificate
    public void verify(java.security.PublicKey publicKey) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException {
        this.mWrapped.verify(publicKey);
    }

    @Override // java.security.cert.Certificate
    public void verify(java.security.PublicKey publicKey, java.lang.String str) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException {
        this.mWrapped.verify(publicKey, str);
    }

    @Override // java.security.cert.Certificate
    public java.lang.String toString() {
        return this.mWrapped.toString();
    }

    @Override // java.security.cert.Certificate
    public java.security.PublicKey getPublicKey() {
        return this.mWrapped.getPublicKey();
    }
}
