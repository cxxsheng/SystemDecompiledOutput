package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public class X509V1CertificateGenerator {
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier sigAlgId;
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier sigOID;
    private java.lang.String signatureAlgorithm;
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper bcHelper = new com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper();
    private final com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory certificateFactory = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory();
    private com.android.internal.org.bouncycastle.asn1.x509.V1TBSCertificateGenerator tbsGen = new com.android.internal.org.bouncycastle.asn1.x509.V1TBSCertificateGenerator();

    public void reset() {
        this.tbsGen = new com.android.internal.org.bouncycastle.asn1.x509.V1TBSCertificateGenerator();
    }

    public void setSerialNumber(java.math.BigInteger bigInteger) {
        if (bigInteger.compareTo(java.math.BigInteger.ZERO) <= 0) {
            throw new java.lang.IllegalArgumentException("serial number must be a positive integer");
        }
        this.tbsGen.setSerialNumber(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger));
    }

    public void setIssuerDN(javax.security.auth.x500.X500Principal x500Principal) {
        try {
            this.tbsGen.setIssuer(new com.android.internal.org.bouncycastle.jce.X509Principal(x500Principal.getEncoded()));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("can't process principal: " + e);
        }
    }

    public void setIssuerDN(com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name) {
        this.tbsGen.setIssuer(x509Name);
    }

    public void setNotBefore(java.util.Date date) {
        this.tbsGen.setStartDate(new com.android.internal.org.bouncycastle.asn1.x509.Time(date));
    }

    public void setNotAfter(java.util.Date date) {
        this.tbsGen.setEndDate(new com.android.internal.org.bouncycastle.asn1.x509.Time(date));
    }

    public void setSubjectDN(javax.security.auth.x500.X500Principal x500Principal) {
        try {
            this.tbsGen.setSubject(new com.android.internal.org.bouncycastle.jce.X509Principal(x500Principal.getEncoded()));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("can't process principal: " + e);
        }
    }

    public void setSubjectDN(com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name) {
        this.tbsGen.setSubject(x509Name);
    }

    public void setPublicKey(java.security.PublicKey publicKey) {
        try {
            this.tbsGen.setSubjectPublicKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()));
        } catch (java.lang.Exception e) {
            throw new java.lang.IllegalArgumentException("unable to process key - " + e.toString());
        }
    }

    public void setSignatureAlgorithm(java.lang.String str) {
        this.signatureAlgorithm = str;
        try {
            this.sigOID = com.android.internal.org.bouncycastle.x509.X509Util.getAlgorithmOID(str);
            this.sigAlgId = com.android.internal.org.bouncycastle.x509.X509Util.getSigAlgID(this.sigOID, str);
            this.tbsGen.setSignature(this.sigAlgId);
        } catch (java.lang.Exception e) {
            throw new java.lang.IllegalArgumentException("Unknown signature type requested");
        }
    }

    public java.security.cert.X509Certificate generateX509Certificate(java.security.PrivateKey privateKey) throws java.lang.SecurityException, java.security.SignatureException, java.security.InvalidKeyException {
        try {
            return generateX509Certificate(privateKey, com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME, null);
        } catch (java.security.NoSuchProviderException e) {
            throw new java.lang.SecurityException("BC provider not installed!");
        }
    }

    public java.security.cert.X509Certificate generateX509Certificate(java.security.PrivateKey privateKey, java.security.SecureRandom secureRandom) throws java.lang.SecurityException, java.security.SignatureException, java.security.InvalidKeyException {
        try {
            return generateX509Certificate(privateKey, com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME, secureRandom);
        } catch (java.security.NoSuchProviderException e) {
            throw new java.lang.SecurityException("BC provider not installed!");
        }
    }

    public java.security.cert.X509Certificate generateX509Certificate(java.security.PrivateKey privateKey, java.lang.String str) throws java.security.NoSuchProviderException, java.lang.SecurityException, java.security.SignatureException, java.security.InvalidKeyException {
        return generateX509Certificate(privateKey, str, null);
    }

    public java.security.cert.X509Certificate generateX509Certificate(java.security.PrivateKey privateKey, java.lang.String str, java.security.SecureRandom secureRandom) throws java.security.NoSuchProviderException, java.lang.SecurityException, java.security.SignatureException, java.security.InvalidKeyException {
        try {
            return generate(privateKey, str, secureRandom);
        } catch (java.security.InvalidKeyException e) {
            throw e;
        } catch (java.security.NoSuchProviderException e2) {
            throw e2;
        } catch (java.security.SignatureException e3) {
            throw e3;
        } catch (java.security.GeneralSecurityException e4) {
            throw new java.lang.SecurityException("exception: " + e4);
        }
    }

    public java.security.cert.X509Certificate generate(java.security.PrivateKey privateKey) throws java.security.cert.CertificateEncodingException, java.lang.IllegalStateException, java.security.NoSuchAlgorithmException, java.security.SignatureException, java.security.InvalidKeyException {
        return generate(privateKey, (java.security.SecureRandom) null);
    }

    public java.security.cert.X509Certificate generate(java.security.PrivateKey privateKey, java.security.SecureRandom secureRandom) throws java.security.cert.CertificateEncodingException, java.lang.IllegalStateException, java.security.NoSuchAlgorithmException, java.security.SignatureException, java.security.InvalidKeyException {
        com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate generateTBSCertificate = this.tbsGen.generateTBSCertificate();
        try {
            return generateJcaObject(generateTBSCertificate, com.android.internal.org.bouncycastle.x509.X509Util.calculateSignature(this.sigOID, this.signatureAlgorithm, privateKey, secureRandom, generateTBSCertificate));
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.x509.ExtCertificateEncodingException("exception encoding TBS cert", e);
        }
    }

    public java.security.cert.X509Certificate generate(java.security.PrivateKey privateKey, java.lang.String str) throws java.security.cert.CertificateEncodingException, java.lang.IllegalStateException, java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException, java.security.SignatureException, java.security.InvalidKeyException {
        return generate(privateKey, str, null);
    }

    public java.security.cert.X509Certificate generate(java.security.PrivateKey privateKey, java.lang.String str, java.security.SecureRandom secureRandom) throws java.security.cert.CertificateEncodingException, java.lang.IllegalStateException, java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException, java.security.SignatureException, java.security.InvalidKeyException {
        com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate generateTBSCertificate = this.tbsGen.generateTBSCertificate();
        try {
            return generateJcaObject(generateTBSCertificate, com.android.internal.org.bouncycastle.x509.X509Util.calculateSignature(this.sigOID, this.signatureAlgorithm, str, privateKey, secureRandom, generateTBSCertificate));
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.x509.ExtCertificateEncodingException("exception encoding TBS cert", e);
        }
    }

    private java.security.cert.X509Certificate generateJcaObject(com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate tBSCertificate, byte[] bArr) throws java.security.cert.CertificateEncodingException {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector.add(tBSCertificate);
        aSN1EncodableVector.add(this.sigAlgId);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr));
        try {
            return (java.security.cert.X509Certificate) this.certificateFactory.engineGenerateCertificate(new java.io.ByteArrayInputStream(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER)));
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.x509.ExtCertificateEncodingException("exception producing certificate object", e);
        }
    }

    public java.util.Iterator getSignatureAlgNames() {
        return com.android.internal.org.bouncycastle.x509.X509Util.getAlgNames();
    }
}
