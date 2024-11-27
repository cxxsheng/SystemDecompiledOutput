package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
public class CertificateFactory extends java.security.cert.CertificateFactorySpi {
    private static final com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil PEM_CERT_PARSER = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil("CERTIFICATE");
    private static final com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil PEM_CRL_PARSER = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil("CRL");
    private static final com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil PEM_PKCS7_PARSER = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil("PKCS7");
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper bcHelper = new com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper();
    private com.android.internal.org.bouncycastle.asn1.ASN1Set sData = null;
    private int sDataObjectCount = 0;
    private java.io.InputStream currentStream = null;
    private com.android.internal.org.bouncycastle.asn1.ASN1Set sCrlData = null;
    private int sCrlDataObjectCount = 0;
    private java.io.InputStream currentCrlStream = null;

    private java.security.cert.Certificate readDERCertificate(com.android.internal.org.bouncycastle.asn1.ASN1InputStream aSN1InputStream) throws java.io.IOException, java.security.cert.CertificateParsingException {
        return getCertificate(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1InputStream.readObject()));
    }

    private java.security.cert.Certificate readPEMCertificate(java.io.InputStream inputStream) throws java.io.IOException, java.security.cert.CertificateParsingException {
        return getCertificate(PEM_CERT_PARSER.readPEMObject(inputStream));
    }

    private java.security.cert.Certificate getCertificate(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) throws java.security.cert.CertificateParsingException {
        if (aSN1Sequence == null) {
            return null;
        }
        if (aSN1Sequence.size() > 1 && (aSN1Sequence.getObjectAt(0) instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) && aSN1Sequence.getObjectAt(0).equals(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.signedData)) {
            this.sData = com.android.internal.org.bouncycastle.asn1.pkcs.SignedData.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true)).getCertificates();
            return getCertificate();
        }
        return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateObject(this.bcHelper, com.android.internal.org.bouncycastle.asn1.x509.Certificate.getInstance(aSN1Sequence));
    }

    private java.security.cert.Certificate getCertificate() throws java.security.cert.CertificateParsingException {
        if (this.sData != null) {
            while (this.sDataObjectCount < this.sData.size()) {
                com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set = this.sData;
                int i = this.sDataObjectCount;
                this.sDataObjectCount = i + 1;
                com.android.internal.org.bouncycastle.asn1.ASN1Encodable objectAt = aSN1Set.getObjectAt(i);
                if (objectAt instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
                    return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateObject(this.bcHelper, com.android.internal.org.bouncycastle.asn1.x509.Certificate.getInstance(objectAt));
                }
            }
            return null;
        }
        return null;
    }

    protected java.security.cert.CRL createCRL(com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList) throws java.security.cert.CRLException {
        return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLObject(this.bcHelper, certificateList);
    }

    private java.security.cert.CRL readPEMCRL(java.io.InputStream inputStream) throws java.io.IOException, java.security.cert.CRLException {
        return getCRL(PEM_CRL_PARSER.readPEMObject(inputStream));
    }

    private java.security.cert.CRL readDERCRL(com.android.internal.org.bouncycastle.asn1.ASN1InputStream aSN1InputStream) throws java.io.IOException, java.security.cert.CRLException {
        return getCRL(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1InputStream.readObject()));
    }

    private java.security.cert.CRL getCRL(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) throws java.security.cert.CRLException {
        if (aSN1Sequence == null) {
            return null;
        }
        if (aSN1Sequence.size() > 1 && (aSN1Sequence.getObjectAt(0) instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) && aSN1Sequence.getObjectAt(0).equals(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.signedData)) {
            this.sCrlData = com.android.internal.org.bouncycastle.asn1.pkcs.SignedData.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true)).getCRLs();
            return getCRL();
        }
        return createCRL(com.android.internal.org.bouncycastle.asn1.x509.CertificateList.getInstance(aSN1Sequence));
    }

    private java.security.cert.CRL getCRL() throws java.security.cert.CRLException {
        if (this.sCrlData == null || this.sCrlDataObjectCount >= this.sCrlData.size()) {
            return null;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set = this.sCrlData;
        int i = this.sCrlDataObjectCount;
        this.sCrlDataObjectCount = i + 1;
        return createCRL(com.android.internal.org.bouncycastle.asn1.x509.CertificateList.getInstance(aSN1Set.getObjectAt(i)));
    }

    @Override // java.security.cert.CertificateFactorySpi
    public java.security.cert.Certificate engineGenerateCertificate(java.io.InputStream inputStream) throws java.security.cert.CertificateException {
        java.io.InputStream pushbackInputStream;
        if (this.currentStream == null) {
            this.currentStream = inputStream;
            this.sData = null;
            this.sDataObjectCount = 0;
        } else if (this.currentStream != inputStream) {
            this.currentStream = inputStream;
            this.sData = null;
            this.sDataObjectCount = 0;
        }
        try {
            if (this.sData != null) {
                if (this.sDataObjectCount != this.sData.size()) {
                    return getCertificate();
                }
                this.sData = null;
                this.sDataObjectCount = 0;
                return null;
            }
            if (inputStream.markSupported()) {
                pushbackInputStream = inputStream;
            } else {
                pushbackInputStream = new java.io.PushbackInputStream(inputStream);
            }
            if (inputStream.markSupported()) {
                pushbackInputStream.mark(1);
            }
            int read = pushbackInputStream.read();
            if (read == -1) {
                return null;
            }
            if (inputStream.markSupported()) {
                pushbackInputStream.reset();
            } else {
                ((java.io.PushbackInputStream) pushbackInputStream).unread(read);
            }
            if (read != 48) {
                return readPEMCertificate(pushbackInputStream);
            }
            return readDERCertificate(new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(pushbackInputStream));
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory.ExCertificateException("parsing issue: " + e.getMessage(), e);
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public java.util.Collection engineGenerateCertificates(java.io.InputStream inputStream) throws java.security.cert.CertificateException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (true) {
            java.security.cert.Certificate engineGenerateCertificate = engineGenerateCertificate(inputStream);
            if (engineGenerateCertificate != null) {
                arrayList.add(engineGenerateCertificate);
            } else {
                return arrayList;
            }
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public java.security.cert.CRL engineGenerateCRL(java.io.InputStream inputStream) throws java.security.cert.CRLException {
        if (this.currentCrlStream == null) {
            this.currentCrlStream = inputStream;
            this.sCrlData = null;
            this.sCrlDataObjectCount = 0;
        } else if (this.currentCrlStream != inputStream) {
            this.currentCrlStream = inputStream;
            this.sCrlData = null;
            this.sCrlDataObjectCount = 0;
        }
        try {
            if (this.sCrlData != null) {
                if (this.sCrlDataObjectCount != this.sCrlData.size()) {
                    return getCRL();
                }
                this.sCrlData = null;
                this.sCrlDataObjectCount = 0;
                return null;
            }
            if (!inputStream.markSupported()) {
                inputStream = new java.io.ByteArrayInputStream(com.android.internal.org.bouncycastle.util.io.Streams.readAll(inputStream));
            }
            inputStream.mark(1);
            int read = inputStream.read();
            if (read == -1) {
                return null;
            }
            inputStream.reset();
            if (read != 48) {
                return readPEMCRL(inputStream);
            }
            return readDERCRL(new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(inputStream, true));
        } catch (java.security.cert.CRLException e) {
            throw e;
        } catch (java.lang.Exception e2) {
            throw new java.security.cert.CRLException(e2.toString());
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public java.util.Collection engineGenerateCRLs(java.io.InputStream inputStream) throws java.security.cert.CRLException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(inputStream);
        while (true) {
            java.security.cert.CRL engineGenerateCRL = engineGenerateCRL(bufferedInputStream);
            if (engineGenerateCRL != null) {
                arrayList.add(engineGenerateCRL);
            } else {
                return arrayList;
            }
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public java.util.Iterator engineGetCertPathEncodings() {
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PKIXCertPath.certPathEncodings.iterator();
    }

    @Override // java.security.cert.CertificateFactorySpi
    public java.security.cert.CertPath engineGenerateCertPath(java.io.InputStream inputStream) throws java.security.cert.CertificateException {
        return engineGenerateCertPath(inputStream, "PkiPath");
    }

    @Override // java.security.cert.CertificateFactorySpi
    public java.security.cert.CertPath engineGenerateCertPath(java.io.InputStream inputStream, java.lang.String str) throws java.security.cert.CertificateException {
        return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PKIXCertPath(inputStream, str);
    }

    @Override // java.security.cert.CertificateFactorySpi
    public java.security.cert.CertPath engineGenerateCertPath(java.util.List list) throws java.security.cert.CertificateException {
        for (java.lang.Object obj : list) {
            if (obj != null && !(obj instanceof java.security.cert.X509Certificate)) {
                throw new java.security.cert.CertificateException("list contains non X509Certificate object while creating CertPath\n" + obj.toString());
            }
        }
        return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PKIXCertPath(list);
    }

    private class ExCertificateException extends java.security.cert.CertificateException {
        private java.lang.Throwable cause;

        public ExCertificateException(java.lang.Throwable th) {
            this.cause = th;
        }

        public ExCertificateException(java.lang.String str, java.lang.Throwable th) {
            super(str);
            this.cause = th;
        }

        @Override // java.lang.Throwable
        public java.lang.Throwable getCause() {
            return this.cause;
        }
    }
}
