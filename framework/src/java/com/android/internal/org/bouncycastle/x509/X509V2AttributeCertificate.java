package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public class X509V2AttributeCertificate implements com.android.internal.org.bouncycastle.x509.X509AttributeCertificate {
    private com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate cert;
    private java.util.Date notAfter;
    private java.util.Date notBefore;

    private static com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate getObject(java.io.InputStream inputStream) throws java.io.IOException {
        try {
            return com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate.getInstance(new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(inputStream).readObject());
        } catch (java.io.IOException e) {
            throw e;
        } catch (java.lang.Exception e2) {
            throw new java.io.IOException("exception decoding certificate structure: " + e2.toString());
        }
    }

    public X509V2AttributeCertificate(java.io.InputStream inputStream) throws java.io.IOException {
        this(getObject(inputStream));
    }

    public X509V2AttributeCertificate(byte[] bArr) throws java.io.IOException {
        this(new java.io.ByteArrayInputStream(bArr));
    }

    X509V2AttributeCertificate(com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate attributeCertificate) throws java.io.IOException {
        this.cert = attributeCertificate;
        try {
            this.notAfter = attributeCertificate.getAcinfo().getAttrCertValidityPeriod().getNotAfterTime().getDate();
            this.notBefore = attributeCertificate.getAcinfo().getAttrCertValidityPeriod().getNotBeforeTime().getDate();
        } catch (java.text.ParseException e) {
            throw new java.io.IOException("invalid data structure in certificate!");
        }
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public int getVersion() {
        return this.cert.getAcinfo().getVersion().intValueExact() + 1;
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public java.math.BigInteger getSerialNumber() {
        return this.cert.getAcinfo().getSerialNumber().getValue();
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public com.android.internal.org.bouncycastle.x509.AttributeCertificateHolder getHolder() {
        return new com.android.internal.org.bouncycastle.x509.AttributeCertificateHolder((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) this.cert.getAcinfo().getHolder().toASN1Primitive());
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public com.android.internal.org.bouncycastle.x509.AttributeCertificateIssuer getIssuer() {
        return new com.android.internal.org.bouncycastle.x509.AttributeCertificateIssuer(this.cert.getAcinfo().getIssuer());
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public java.util.Date getNotBefore() {
        return this.notBefore;
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public java.util.Date getNotAfter() {
        return this.notAfter;
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public boolean[] getIssuerUniqueID() {
        com.android.internal.org.bouncycastle.asn1.DERBitString issuerUniqueID = this.cert.getAcinfo().getIssuerUniqueID();
        if (issuerUniqueID != null) {
            byte[] bytes = issuerUniqueID.getBytes();
            int length = (bytes.length * 8) - issuerUniqueID.getPadBits();
            boolean[] zArr = new boolean[length];
            for (int i = 0; i != length; i++) {
                zArr[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
            }
            return zArr;
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public void checkValidity() throws java.security.cert.CertificateExpiredException, java.security.cert.CertificateNotYetValidException {
        checkValidity(new java.util.Date());
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public void checkValidity(java.util.Date date) throws java.security.cert.CertificateExpiredException, java.security.cert.CertificateNotYetValidException {
        if (date.after(getNotAfter())) {
            throw new java.security.cert.CertificateExpiredException("certificate expired on " + getNotAfter());
        }
        if (date.before(getNotBefore())) {
            throw new java.security.cert.CertificateNotYetValidException("certificate not valid till " + getNotBefore());
        }
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public byte[] getSignature() {
        return this.cert.getSignatureValue().getOctets();
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public final void verify(java.security.PublicKey publicKey, java.lang.String str) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException {
        if (!this.cert.getSignatureAlgorithm().equals(this.cert.getAcinfo().getSignature())) {
            throw new java.security.cert.CertificateException("Signature algorithm in certificate info not same as outer certificate");
        }
        java.security.Signature signature = java.security.Signature.getInstance(this.cert.getSignatureAlgorithm().getAlgorithm().getId(), str);
        signature.initVerify(publicKey);
        try {
            signature.update(this.cert.getAcinfo().getEncoded());
            if (!signature.verify(getSignature())) {
                throw new java.security.InvalidKeyException("Public key presented not for certificate signature");
            }
        } catch (java.io.IOException e) {
            throw new java.security.SignatureException("Exception encoding certificate info object");
        }
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public byte[] getEncoded() throws java.io.IOException {
        return this.cert.getEncoded();
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.cert.getAcinfo().getExtensions();
        if (extensions != null && (extension = extensions.getExtension(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str))) != null) {
            try {
                return extension.getExtnValue().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException("error encoding " + e.toString());
            }
        }
        return null;
    }

    private java.util.Set getExtensionOIDs(boolean z) {
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.cert.getAcinfo().getExtensions();
        if (extensions != null) {
            java.util.HashSet hashSet = new java.util.HashSet();
            java.util.Enumeration oids = extensions.oids();
            while (oids.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.nextElement();
                if (extensions.getExtension(aSN1ObjectIdentifier).isCritical() == z) {
                    hashSet.add(aSN1ObjectIdentifier.getId());
                }
            }
            return hashSet;
        }
        return null;
    }

    @Override // java.security.cert.X509Extension
    public java.util.Set getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    @Override // java.security.cert.X509Extension
    public java.util.Set getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        java.util.Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        return (criticalExtensionOIDs == null || criticalExtensionOIDs.isEmpty()) ? false : true;
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public com.android.internal.org.bouncycastle.x509.X509Attribute[] getAttributes() {
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence attributes = this.cert.getAcinfo().getAttributes();
        com.android.internal.org.bouncycastle.x509.X509Attribute[] x509AttributeArr = new com.android.internal.org.bouncycastle.x509.X509Attribute[attributes.size()];
        for (int i = 0; i != attributes.size(); i++) {
            x509AttributeArr[i] = new com.android.internal.org.bouncycastle.x509.X509Attribute(attributes.getObjectAt(i));
        }
        return x509AttributeArr;
    }

    @Override // com.android.internal.org.bouncycastle.x509.X509AttributeCertificate
    public com.android.internal.org.bouncycastle.x509.X509Attribute[] getAttributes(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence attributes = this.cert.getAcinfo().getAttributes();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i != attributes.size(); i++) {
            com.android.internal.org.bouncycastle.x509.X509Attribute x509Attribute = new com.android.internal.org.bouncycastle.x509.X509Attribute(attributes.getObjectAt(i));
            if (x509Attribute.getOID().equals(str)) {
                arrayList.add(x509Attribute);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return (com.android.internal.org.bouncycastle.x509.X509Attribute[]) arrayList.toArray(new com.android.internal.org.bouncycastle.x509.X509Attribute[arrayList.size()]);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.x509.X509AttributeCertificate)) {
            return false;
        }
        try {
            return com.android.internal.org.bouncycastle.util.Arrays.areEqual(getEncoded(), ((com.android.internal.org.bouncycastle.x509.X509AttributeCertificate) obj).getEncoded());
        } catch (java.io.IOException e) {
            return false;
        }
    }

    public int hashCode() {
        try {
            return com.android.internal.org.bouncycastle.util.Arrays.hashCode(getEncoded());
        } catch (java.io.IOException e) {
            return 0;
        }
    }
}
