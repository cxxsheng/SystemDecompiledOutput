package com.android.internal.org.bouncycastle.cert;

/* loaded from: classes4.dex */
public class X509CertificateHolder implements com.android.internal.org.bouncycastle.util.Encodable, java.io.Serializable {
    private static final long serialVersionUID = 20170722001L;
    private transient com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions;
    private transient com.android.internal.org.bouncycastle.asn1.x509.Certificate x509Certificate;

    private static com.android.internal.org.bouncycastle.asn1.x509.Certificate parseBytes(byte[] bArr) throws java.io.IOException {
        try {
            return com.android.internal.org.bouncycastle.asn1.x509.Certificate.getInstance(com.android.internal.org.bouncycastle.cert.CertUtils.parseNonEmptyASN1(bArr));
        } catch (java.lang.ClassCastException e) {
            throw new com.android.internal.org.bouncycastle.cert.CertIOException("malformed data: " + e.getMessage(), e);
        } catch (java.lang.IllegalArgumentException e2) {
            throw new com.android.internal.org.bouncycastle.cert.CertIOException("malformed data: " + e2.getMessage(), e2);
        }
    }

    public X509CertificateHolder(byte[] bArr) throws java.io.IOException {
        this(parseBytes(bArr));
    }

    public X509CertificateHolder(com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate) {
        init(certificate);
    }

    private void init(com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate) {
        this.x509Certificate = certificate;
        this.extensions = certificate.getTBSCertificate().getExtensions();
    }

    public int getVersionNumber() {
        return this.x509Certificate.getVersionNumber();
    }

    public int getVersion() {
        return this.x509Certificate.getVersionNumber();
    }

    public boolean hasExtensions() {
        return this.extensions != null;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extension getExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (this.extensions != null) {
            return this.extensions.getExtension(aSN1ObjectIdentifier);
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extensions getExtensions() {
        return this.extensions;
    }

    public java.util.List getExtensionOIDs() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.getExtensionOIDs(this.extensions);
    }

    public java.util.Set getCriticalExtensionOIDs() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.getCriticalExtensionOIDs(this.extensions);
    }

    public java.util.Set getNonCriticalExtensionOIDs() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.getNonCriticalExtensionOIDs(this.extensions);
    }

    public java.math.BigInteger getSerialNumber() {
        return this.x509Certificate.getSerialNumber().getValue();
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuer() {
        return com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(this.x509Certificate.getIssuer());
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getSubject() {
        return com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(this.x509Certificate.getSubject());
    }

    public java.util.Date getNotBefore() {
        return this.x509Certificate.getStartDate().getDate();
    }

    public java.util.Date getNotAfter() {
        return this.x509Certificate.getEndDate().getDate();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.x509Certificate.getSubjectPublicKeyInfo();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Certificate toASN1Structure() {
        return this.x509Certificate;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignatureAlgorithm() {
        return this.x509Certificate.getSignatureAlgorithm();
    }

    public byte[] getSignature() {
        return this.x509Certificate.getSignature().getOctets();
    }

    public boolean isValidOn(java.util.Date date) {
        return (date.before(this.x509Certificate.getStartDate().getDate()) || date.after(this.x509Certificate.getEndDate().getDate())) ? false : true;
    }

    public boolean isSignatureValid(com.android.internal.org.bouncycastle.operator.ContentVerifierProvider contentVerifierProvider) throws com.android.internal.org.bouncycastle.cert.CertException {
        com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate tBSCertificate = this.x509Certificate.getTBSCertificate();
        if (!com.android.internal.org.bouncycastle.cert.CertUtils.isAlgIdEqual(tBSCertificate.getSignature(), this.x509Certificate.getSignatureAlgorithm())) {
            throw new com.android.internal.org.bouncycastle.cert.CertException("signature invalid - algorithm identifier mismatch");
        }
        try {
            com.android.internal.org.bouncycastle.operator.ContentVerifier contentVerifier = contentVerifierProvider.get(tBSCertificate.getSignature());
            java.io.OutputStream outputStream = contentVerifier.getOutputStream();
            tBSCertificate.encodeTo(outputStream, com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            outputStream.close();
            return contentVerifier.verify(getSignature());
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.cert.CertException("unable to process signature: " + e.getMessage(), e);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.cert.X509CertificateHolder)) {
            return false;
        }
        return this.x509Certificate.equals(((com.android.internal.org.bouncycastle.cert.X509CertificateHolder) obj).x509Certificate);
    }

    public int hashCode() {
        return this.x509Certificate.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() throws java.io.IOException {
        return this.x509Certificate.getEncoded();
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        objectInputStream.defaultReadObject();
        init(com.android.internal.org.bouncycastle.asn1.x509.Certificate.getInstance(objectInputStream.readObject()));
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }
}
