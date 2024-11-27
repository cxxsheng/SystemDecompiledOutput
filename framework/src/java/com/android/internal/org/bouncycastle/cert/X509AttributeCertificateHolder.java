package com.android.internal.org.bouncycastle.cert;

/* loaded from: classes4.dex */
public class X509AttributeCertificateHolder implements com.android.internal.org.bouncycastle.util.Encodable, java.io.Serializable {
    private static com.android.internal.org.bouncycastle.asn1.x509.Attribute[] EMPTY_ARRAY = new com.android.internal.org.bouncycastle.asn1.x509.Attribute[0];
    private static final long serialVersionUID = 20170722001L;
    private transient com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate attrCert;
    private transient com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions;

    private static com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate parseBytes(byte[] bArr) throws java.io.IOException {
        try {
            return com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate.getInstance(com.android.internal.org.bouncycastle.cert.CertUtils.parseNonEmptyASN1(bArr));
        } catch (java.lang.ClassCastException e) {
            throw new com.android.internal.org.bouncycastle.cert.CertIOException("malformed data: " + e.getMessage(), e);
        } catch (java.lang.IllegalArgumentException e2) {
            throw new com.android.internal.org.bouncycastle.cert.CertIOException("malformed data: " + e2.getMessage(), e2);
        }
    }

    public X509AttributeCertificateHolder(byte[] bArr) throws java.io.IOException {
        this(parseBytes(bArr));
    }

    public X509AttributeCertificateHolder(com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate attributeCertificate) {
        init(attributeCertificate);
    }

    private void init(com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate attributeCertificate) {
        this.attrCert = attributeCertificate;
        this.extensions = attributeCertificate.getAcinfo().getExtensions();
    }

    @Override // com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() throws java.io.IOException {
        return this.attrCert.getEncoded();
    }

    public int getVersion() {
        return this.attrCert.getAcinfo().getVersion().intValueExact() + 1;
    }

    public java.math.BigInteger getSerialNumber() {
        return this.attrCert.getAcinfo().getSerialNumber().getValue();
    }

    public com.android.internal.org.bouncycastle.cert.AttributeCertificateHolder getHolder() {
        return new com.android.internal.org.bouncycastle.cert.AttributeCertificateHolder((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) this.attrCert.getAcinfo().getHolder().toASN1Primitive());
    }

    public com.android.internal.org.bouncycastle.cert.AttributeCertificateIssuer getIssuer() {
        return new com.android.internal.org.bouncycastle.cert.AttributeCertificateIssuer(this.attrCert.getAcinfo().getIssuer());
    }

    public java.util.Date getNotBefore() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.recoverDate(this.attrCert.getAcinfo().getAttrCertValidityPeriod().getNotBeforeTime());
    }

    public java.util.Date getNotAfter() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.recoverDate(this.attrCert.getAcinfo().getAttrCertValidityPeriod().getNotAfterTime());
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Attribute[] getAttributes() {
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence attributes = this.attrCert.getAcinfo().getAttributes();
        com.android.internal.org.bouncycastle.asn1.x509.Attribute[] attributeArr = new com.android.internal.org.bouncycastle.asn1.x509.Attribute[attributes.size()];
        for (int i = 0; i != attributes.size(); i++) {
            attributeArr[i] = com.android.internal.org.bouncycastle.asn1.x509.Attribute.getInstance(attributes.getObjectAt(i));
        }
        return attributeArr;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Attribute[] getAttributes(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence attributes = this.attrCert.getAcinfo().getAttributes();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i != attributes.size(); i++) {
            com.android.internal.org.bouncycastle.asn1.x509.Attribute attribute = com.android.internal.org.bouncycastle.asn1.x509.Attribute.getInstance(attributes.getObjectAt(i));
            if (attribute.getAttrType().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
                arrayList.add(attribute);
            }
        }
        if (arrayList.size() == 0) {
            return EMPTY_ARRAY;
        }
        return (com.android.internal.org.bouncycastle.asn1.x509.Attribute[]) arrayList.toArray(new com.android.internal.org.bouncycastle.asn1.x509.Attribute[arrayList.size()]);
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

    public boolean[] getIssuerUniqueID() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.bitStringToBoolean(this.attrCert.getAcinfo().getIssuerUniqueID());
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignatureAlgorithm() {
        return this.attrCert.getSignatureAlgorithm();
    }

    public byte[] getSignature() {
        return this.attrCert.getSignatureValue().getOctets();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate toASN1Structure() {
        return this.attrCert;
    }

    public boolean isValidOn(java.util.Date date) {
        com.android.internal.org.bouncycastle.asn1.x509.AttCertValidityPeriod attrCertValidityPeriod = this.attrCert.getAcinfo().getAttrCertValidityPeriod();
        return (date.before(com.android.internal.org.bouncycastle.cert.CertUtils.recoverDate(attrCertValidityPeriod.getNotBeforeTime())) || date.after(com.android.internal.org.bouncycastle.cert.CertUtils.recoverDate(attrCertValidityPeriod.getNotAfterTime()))) ? false : true;
    }

    public boolean isSignatureValid(com.android.internal.org.bouncycastle.operator.ContentVerifierProvider contentVerifierProvider) throws com.android.internal.org.bouncycastle.cert.CertException {
        com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo acinfo = this.attrCert.getAcinfo();
        if (!com.android.internal.org.bouncycastle.cert.CertUtils.isAlgIdEqual(acinfo.getSignature(), this.attrCert.getSignatureAlgorithm())) {
            throw new com.android.internal.org.bouncycastle.cert.CertException("signature invalid - algorithm identifier mismatch");
        }
        try {
            com.android.internal.org.bouncycastle.operator.ContentVerifier contentVerifier = contentVerifierProvider.get(acinfo.getSignature());
            java.io.OutputStream outputStream = contentVerifier.getOutputStream();
            acinfo.encodeTo(outputStream, com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
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
        if (!(obj instanceof com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder)) {
            return false;
        }
        return this.attrCert.equals(((com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder) obj).attrCert);
    }

    public int hashCode() {
        return this.attrCert.hashCode();
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        objectInputStream.defaultReadObject();
        init(com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate.getInstance(objectInputStream.readObject()));
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }
}
