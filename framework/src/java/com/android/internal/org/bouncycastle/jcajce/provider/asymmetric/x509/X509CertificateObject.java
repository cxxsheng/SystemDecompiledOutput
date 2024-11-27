package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
class X509CertificateObject extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl implements com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier {
    private com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier attrCarrier;
    private final java.lang.Object cacheLock;
    private byte[] encoded;
    private volatile int hashValue;
    private volatile boolean hashValueSet;
    private com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal internalCertificateValue;
    private javax.security.auth.x500.X500Principal issuerValue;
    private java.security.PublicKey publicKeyValue;
    private javax.security.auth.x500.X500Principal subjectValue;
    private long[] validityValues;

    X509CertificateObject(com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper, com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate) throws java.security.cert.CertificateParsingException {
        super(jcaJceHelper, certificate, createBasicConstraints(certificate), createKeyUsage(certificate), createSigAlgName(certificate), createSigAlgParams(certificate));
        this.cacheLock = new java.lang.Object();
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl, java.security.cert.X509Certificate
    public void checkValidity(java.util.Date date) throws java.security.cert.CertificateExpiredException, java.security.cert.CertificateNotYetValidException {
        long time = date.getTime();
        long[] validityValues = getValidityValues();
        if (time > validityValues[1]) {
            throw new java.security.cert.CertificateExpiredException("certificate expired on " + this.c.getEndDate().getTime());
        }
        if (time < validityValues[0]) {
            throw new java.security.cert.CertificateNotYetValidException("certificate not valid till " + this.c.getStartDate().getTime());
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl, java.security.cert.X509Certificate
    public javax.security.auth.x500.X500Principal getIssuerX500Principal() {
        javax.security.auth.x500.X500Principal x500Principal;
        synchronized (this.cacheLock) {
            if (this.issuerValue != null) {
                return this.issuerValue;
            }
            javax.security.auth.x500.X500Principal issuerX500Principal = super.getIssuerX500Principal();
            synchronized (this.cacheLock) {
                if (this.issuerValue == null) {
                    this.issuerValue = issuerX500Principal;
                }
                x500Principal = this.issuerValue;
            }
            return x500Principal;
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl, java.security.cert.Certificate
    public java.security.PublicKey getPublicKey() {
        java.security.PublicKey publicKey;
        synchronized (this.cacheLock) {
            if (this.publicKeyValue != null) {
                return this.publicKeyValue;
            }
            java.security.PublicKey publicKey2 = super.getPublicKey();
            if (publicKey2 == null) {
                return null;
            }
            synchronized (this.cacheLock) {
                if (this.publicKeyValue == null) {
                    this.publicKeyValue = publicKey2;
                }
                publicKey = this.publicKeyValue;
            }
            return publicKey;
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl, java.security.cert.X509Certificate
    public javax.security.auth.x500.X500Principal getSubjectX500Principal() {
        javax.security.auth.x500.X500Principal x500Principal;
        synchronized (this.cacheLock) {
            if (this.subjectValue != null) {
                return this.subjectValue;
            }
            javax.security.auth.x500.X500Principal subjectX500Principal = super.getSubjectX500Principal();
            synchronized (this.cacheLock) {
                if (this.subjectValue == null) {
                    this.subjectValue = subjectX500Principal;
                }
                x500Principal = this.subjectValue;
            }
            return x500Principal;
        }
    }

    public long[] getValidityValues() {
        long[] jArr;
        synchronized (this.cacheLock) {
            if (this.validityValues != null) {
                return this.validityValues;
            }
            long[] jArr2 = {super.getNotBefore().getTime(), super.getNotAfter().getTime()};
            synchronized (this.cacheLock) {
                if (this.validityValues == null) {
                    this.validityValues = jArr2;
                }
                jArr = this.validityValues;
            }
            return jArr;
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl, java.security.cert.Certificate
    public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
        try {
            if (this.encoded == null) {
                this.encoded = this.c.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            }
            return this.encoded;
        } catch (java.io.IOException e) {
            throw new java.security.cert.CertificateEncodingException(e.toString());
        }
    }

    @Override // java.security.cert.Certificate
    public boolean equals(java.lang.Object obj) {
        com.android.internal.org.bouncycastle.asn1.DERBitString signature;
        if (obj == this) {
            return true;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateObject) {
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateObject x509CertificateObject = (com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateObject) obj;
            if (this.hashValueSet && x509CertificateObject.hashValueSet) {
                if (this.hashValue != x509CertificateObject.hashValue) {
                    return false;
                }
            } else if ((this.internalCertificateValue == null || x509CertificateObject.internalCertificateValue == null) && (signature = this.c.getSignature()) != null && !signature.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) x509CertificateObject.c.getSignature())) {
                return false;
            }
        }
        return getInternalCertificate().equals(obj);
    }

    @Override // java.security.cert.Certificate
    public int hashCode() {
        if (!this.hashValueSet) {
            this.hashValue = getInternalCertificate().hashCode();
            this.hashValueSet = true;
        }
        return this.hashValue;
    }

    public int originalHashCode() {
        try {
            byte[] encoded = getInternalCertificate().getEncoded();
            int i = 0;
            for (int i2 = 1; i2 < encoded.length; i2++) {
                i += encoded[i2] * i2;
            }
            return i;
        } catch (java.security.cert.CertificateEncodingException e) {
            return 0;
        }
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public void setBagAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getBagAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(aSN1ObjectIdentifier);
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public java.util.Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    private com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal getInternalCertificate() {
        byte[] bArr;
        com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal x509CertificateInternal;
        synchronized (this.cacheLock) {
            if (this.internalCertificateValue != null) {
                return this.internalCertificateValue;
            }
            try {
                bArr = getEncoded();
            } catch (java.security.cert.CertificateEncodingException e) {
                bArr = null;
            }
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal x509CertificateInternal2 = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal(this.bcHelper, this.c, this.basicConstraints, this.keyUsage, this.sigAlgName, this.sigAlgParams, bArr);
            synchronized (this.cacheLock) {
                if (this.internalCertificateValue == null) {
                    this.internalCertificateValue = x509CertificateInternal2;
                }
                x509CertificateInternal = this.internalCertificateValue;
            }
            return x509CertificateInternal;
        }
    }

    private static com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints createBasicConstraints(com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate) throws java.security.cert.CertificateParsingException {
        try {
            byte[] extensionOctets = getExtensionOctets(certificate, "2.5.29.19");
            if (extensionOctets == null) {
                return null;
            }
            return com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(extensionOctets));
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CertificateParsingException("cannot construct BasicConstraints: " + e);
        }
    }

    private static boolean[] createKeyUsage(com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate) throws java.security.cert.CertificateParsingException {
        try {
            byte[] extensionOctets = getExtensionOctets(certificate, "2.5.29.15");
            if (extensionOctets == null) {
                return null;
            }
            com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(extensionOctets));
            byte[] bytes = dERBitString.getBytes();
            int length = (bytes.length * 8) - dERBitString.getPadBits();
            int i = 9;
            if (length >= 9) {
                i = length;
            }
            boolean[] zArr = new boolean[i];
            for (int i2 = 0; i2 != length; i2++) {
                zArr[i2] = (bytes[i2 / 8] & (128 >>> (i2 % 8))) != 0;
            }
            return zArr;
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CertificateParsingException("cannot construct KeyUsage: " + e);
        }
    }

    private static java.lang.String createSigAlgName(com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate) throws java.security.cert.CertificateParsingException {
        try {
            return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.getSignatureName(certificate.getSignatureAlgorithm());
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CertificateParsingException("cannot construct SigAlgName: " + e);
        }
    }

    private static byte[] createSigAlgParams(com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate) throws java.security.cert.CertificateParsingException {
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable parameters = certificate.getSignatureAlgorithm().getParameters();
            if (parameters == null) {
                return null;
            }
            return parameters.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CertificateParsingException("cannot construct SigAlgParams: " + e);
        }
    }
}
