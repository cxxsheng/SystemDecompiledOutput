package com.android.internal.org.bouncycastle.cert;

/* loaded from: classes4.dex */
public class X509v3CertificateBuilder {
    private com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator extGenerator;
    private com.android.internal.org.bouncycastle.asn1.x509.V3TBSCertificateGenerator tbsGen;

    public X509v3CertificateBuilder(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, java.math.BigInteger bigInteger, java.util.Date date, java.util.Date date2, com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name2, com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this(x500Name, bigInteger, new com.android.internal.org.bouncycastle.asn1.x509.Time(date), new com.android.internal.org.bouncycastle.asn1.x509.Time(date2), x500Name2, subjectPublicKeyInfo);
    }

    public X509v3CertificateBuilder(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, java.math.BigInteger bigInteger, java.util.Date date, java.util.Date date2, java.util.Locale locale, com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name2, com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this(x500Name, bigInteger, new com.android.internal.org.bouncycastle.asn1.x509.Time(date, locale), new com.android.internal.org.bouncycastle.asn1.x509.Time(date2, locale), x500Name2, subjectPublicKeyInfo);
    }

    public X509v3CertificateBuilder(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.asn1.x509.Time time, com.android.internal.org.bouncycastle.asn1.x509.Time time2, com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name2, com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.tbsGen = new com.android.internal.org.bouncycastle.asn1.x509.V3TBSCertificateGenerator();
        this.tbsGen.setSerialNumber(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger));
        this.tbsGen.setIssuer(x500Name);
        this.tbsGen.setStartDate(time);
        this.tbsGen.setEndDate(time2);
        this.tbsGen.setSubject(x500Name2);
        this.tbsGen.setSubjectPublicKeyInfo(subjectPublicKeyInfo);
        this.extGenerator = new com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator();
    }

    public X509v3CertificateBuilder(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) {
        this.tbsGen = new com.android.internal.org.bouncycastle.asn1.x509.V3TBSCertificateGenerator();
        this.tbsGen.setSerialNumber(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(x509CertificateHolder.getSerialNumber()));
        this.tbsGen.setIssuer(x509CertificateHolder.getIssuer());
        this.tbsGen.setStartDate(new com.android.internal.org.bouncycastle.asn1.x509.Time(x509CertificateHolder.getNotBefore()));
        this.tbsGen.setEndDate(new com.android.internal.org.bouncycastle.asn1.x509.Time(x509CertificateHolder.getNotAfter()));
        this.tbsGen.setSubject(x509CertificateHolder.getSubject());
        this.tbsGen.setSubjectPublicKeyInfo(x509CertificateHolder.getSubjectPublicKeyInfo());
        this.extGenerator = new com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator();
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = x509CertificateHolder.getExtensions();
        java.util.Enumeration oids = extensions.oids();
        while (oids.hasMoreElements()) {
            this.extGenerator.addExtension(extensions.getExtension((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.nextElement()));
        }
    }

    public boolean hasExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return doGetExtension(aSN1ObjectIdentifier) != null;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extension getExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return doGetExtension(aSN1ObjectIdentifier);
    }

    private com.android.internal.org.bouncycastle.asn1.x509.Extension doGetExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.extGenerator.generate().getExtension(aSN1ObjectIdentifier);
    }

    public com.android.internal.org.bouncycastle.cert.X509v3CertificateBuilder setSubjectUniqueID(boolean[] zArr) {
        this.tbsGen.setSubjectUniqueID(booleanToBitString(zArr));
        return this;
    }

    public com.android.internal.org.bouncycastle.cert.X509v3CertificateBuilder setIssuerUniqueID(boolean[] zArr) {
        this.tbsGen.setIssuerUniqueID(booleanToBitString(zArr));
        return this;
    }

    public com.android.internal.org.bouncycastle.cert.X509v3CertificateBuilder addExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws com.android.internal.org.bouncycastle.cert.CertIOException {
        try {
            this.extGenerator.addExtension(aSN1ObjectIdentifier, z, aSN1Encodable);
            return this;
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.cert.CertIOException("cannot encode extension: " + e.getMessage(), e);
        }
    }

    public com.android.internal.org.bouncycastle.cert.X509v3CertificateBuilder addExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension extension) throws com.android.internal.org.bouncycastle.cert.CertIOException {
        this.extGenerator.addExtension(extension);
        return this;
    }

    public com.android.internal.org.bouncycastle.cert.X509v3CertificateBuilder addExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, byte[] bArr) throws com.android.internal.org.bouncycastle.cert.CertIOException {
        this.extGenerator.addExtension(aSN1ObjectIdentifier, z, bArr);
        return this;
    }

    public com.android.internal.org.bouncycastle.cert.X509v3CertificateBuilder replaceExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws com.android.internal.org.bouncycastle.cert.CertIOException {
        try {
            this.extGenerator = com.android.internal.org.bouncycastle.cert.CertUtils.doReplaceExtension(this.extGenerator, new com.android.internal.org.bouncycastle.asn1.x509.Extension(aSN1ObjectIdentifier, z, aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER)));
            return this;
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.cert.CertIOException("cannot encode extension: " + e.getMessage(), e);
        }
    }

    public com.android.internal.org.bouncycastle.cert.X509v3CertificateBuilder replaceExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension extension) throws com.android.internal.org.bouncycastle.cert.CertIOException {
        this.extGenerator = com.android.internal.org.bouncycastle.cert.CertUtils.doReplaceExtension(this.extGenerator, extension);
        return this;
    }

    public com.android.internal.org.bouncycastle.cert.X509v3CertificateBuilder replaceExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, byte[] bArr) throws com.android.internal.org.bouncycastle.cert.CertIOException {
        this.extGenerator = com.android.internal.org.bouncycastle.cert.CertUtils.doReplaceExtension(this.extGenerator, new com.android.internal.org.bouncycastle.asn1.x509.Extension(aSN1ObjectIdentifier, z, bArr));
        return this;
    }

    public com.android.internal.org.bouncycastle.cert.X509v3CertificateBuilder removeExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.extGenerator = com.android.internal.org.bouncycastle.cert.CertUtils.doRemoveExtension(this.extGenerator, aSN1ObjectIdentifier);
        return this;
    }

    public com.android.internal.org.bouncycastle.cert.X509v3CertificateBuilder copyAndAddExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension = x509CertificateHolder.toASN1Structure().getTBSCertificate().getExtensions().getExtension(aSN1ObjectIdentifier);
        if (extension == null) {
            throw new java.lang.NullPointerException("extension " + aSN1ObjectIdentifier + " not present");
        }
        this.extGenerator.addExtension(aSN1ObjectIdentifier, z, extension.getExtnValue().getOctets());
        return this;
    }

    public com.android.internal.org.bouncycastle.cert.X509CertificateHolder build(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner) {
        this.tbsGen.setSignature(contentSigner.getAlgorithmIdentifier());
        if (!this.extGenerator.isEmpty()) {
            this.tbsGen.setExtensions(this.extGenerator.generate());
        }
        try {
            com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate generateTBSCertificate = this.tbsGen.generateTBSCertificate();
            return new com.android.internal.org.bouncycastle.cert.X509CertificateHolder(generateStructure(generateTBSCertificate, contentSigner.getAlgorithmIdentifier(), generateSig(contentSigner, generateTBSCertificate)));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("cannot produce certificate signature");
        }
    }

    private static byte[] generateSig(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, com.android.internal.org.bouncycastle.asn1.ASN1Object aSN1Object) throws java.io.IOException {
        java.io.OutputStream outputStream = contentSigner.getOutputStream();
        aSN1Object.encodeTo(outputStream, com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        outputStream.close();
        return contentSigner.getSignature();
    }

    private static com.android.internal.org.bouncycastle.asn1.x509.Certificate generateStructure(com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate tBSCertificate, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector.add(tBSCertificate);
        aSN1EncodableVector.add(algorithmIdentifier);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr));
        return com.android.internal.org.bouncycastle.asn1.x509.Certificate.getInstance(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector));
    }

    static com.android.internal.org.bouncycastle.asn1.DERBitString booleanToBitString(boolean[] zArr) {
        byte[] bArr = new byte[(zArr.length + 7) / 8];
        for (int i = 0; i != zArr.length; i++) {
            int i2 = i / 8;
            bArr[i2] = (byte) (bArr[i2] | (zArr[i] ? 1 << (7 - (i % 8)) : 0));
        }
        int length = zArr.length % 8;
        if (length == 0) {
            return new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr, 8 - length);
    }
}
