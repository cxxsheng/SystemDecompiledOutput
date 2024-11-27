package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
class X509CRLObject extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLImpl {
    private final java.lang.Object cacheLock;
    private volatile int hashValue;
    private volatile boolean hashValueSet;
    private com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal internalCRLValue;

    X509CRLObject(com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper, com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList) throws java.security.cert.CRLException {
        super(jcaJceHelper, certificateList, createSigAlgName(certificateList), createSigAlgParams(certificateList), isIndirectCRL(certificateList));
        this.cacheLock = new java.lang.Object();
    }

    @Override // java.security.cert.X509CRL
    public boolean equals(java.lang.Object obj) {
        com.android.internal.org.bouncycastle.asn1.DERBitString signature;
        if (this == obj) {
            return true;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLObject) {
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLObject x509CRLObject = (com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLObject) obj;
            if (this.hashValueSet && x509CRLObject.hashValueSet) {
                if (this.hashValue != x509CRLObject.hashValue) {
                    return false;
                }
            } else if ((this.internalCRLValue == null || x509CRLObject.internalCRLValue == null) && (signature = this.c.getSignature()) != null && !signature.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) x509CRLObject.c.getSignature())) {
                return false;
            }
        }
        return getInternalCRL().equals(obj);
    }

    @Override // java.security.cert.X509CRL
    public int hashCode() {
        if (!this.hashValueSet) {
            this.hashValue = getInternalCRL().hashCode();
            this.hashValueSet = true;
        }
        return this.hashValue;
    }

    private com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal getInternalCRL() {
        byte[] bArr;
        com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal x509CRLInternal;
        synchronized (this.cacheLock) {
            if (this.internalCRLValue != null) {
                return this.internalCRLValue;
            }
            try {
                bArr = getEncoded();
            } catch (java.security.cert.CRLException e) {
                bArr = null;
            }
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal x509CRLInternal2 = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal(this.bcHelper, this.c, this.sigAlgName, this.sigAlgParams, this.isIndirect, bArr);
            synchronized (this.cacheLock) {
                if (this.internalCRLValue == null) {
                    this.internalCRLValue = x509CRLInternal2;
                }
                x509CRLInternal = this.internalCRLValue;
            }
            return x509CRLInternal;
        }
    }

    private static java.lang.String createSigAlgName(com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList) throws java.security.cert.CRLException {
        try {
            return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.getSignatureName(certificateList.getSignatureAlgorithm());
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CRLException("CRL contents invalid: " + e);
        }
    }

    private static byte[] createSigAlgParams(com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList) throws java.security.cert.CRLException {
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable parameters = certificateList.getSignatureAlgorithm().getParameters();
            if (parameters == null) {
                return null;
            }
            return parameters.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CRLException("CRL contents invalid: " + e);
        }
    }

    private static boolean isIndirectCRL(com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList) throws java.security.cert.CRLException {
        try {
            byte[] extensionOctets = getExtensionOctets(certificateList, com.android.internal.org.bouncycastle.asn1.x509.Extension.issuingDistributionPoint.getId());
            if (extensionOctets == null) {
                return false;
            }
            return com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint.getInstance(extensionOctets).isIndirectCRL();
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.ExtCRLException("Exception reading IssuingDistributionPoint", e);
        }
    }
}
