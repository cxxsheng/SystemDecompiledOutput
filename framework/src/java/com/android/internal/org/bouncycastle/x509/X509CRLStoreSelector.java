package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public class X509CRLStoreSelector extends java.security.cert.X509CRLSelector implements com.android.internal.org.bouncycastle.util.Selector {
    private com.android.internal.org.bouncycastle.x509.X509AttributeCertificate attrCertChecking;
    private boolean deltaCRLIndicator = false;
    private boolean completeCRLEnabled = false;
    private java.math.BigInteger maxBaseCRLNumber = null;
    private byte[] issuingDistributionPoint = null;
    private boolean issuingDistributionPointEnabled = false;

    public boolean isIssuingDistributionPointEnabled() {
        return this.issuingDistributionPointEnabled;
    }

    public void setIssuingDistributionPointEnabled(boolean z) {
        this.issuingDistributionPointEnabled = z;
    }

    public void setAttrCertificateChecking(com.android.internal.org.bouncycastle.x509.X509AttributeCertificate x509AttributeCertificate) {
        this.attrCertChecking = x509AttributeCertificate;
    }

    public com.android.internal.org.bouncycastle.x509.X509AttributeCertificate getAttrCertificateChecking() {
        return this.attrCertChecking;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(java.lang.Object obj) {
        com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer;
        if (!(obj instanceof java.security.cert.X509CRL)) {
            return false;
        }
        java.security.cert.X509CRL x509crl = (java.security.cert.X509CRL) obj;
        try {
            byte[] extensionValue = x509crl.getExtensionValue(com.android.internal.org.bouncycastle.asn1.x509.Extension.deltaCRLIndicator.getId());
            if (extensionValue == null) {
                aSN1Integer = null;
            } else {
                aSN1Integer = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(com.android.internal.org.bouncycastle.x509.extension.X509ExtensionUtil.fromExtensionValue(extensionValue));
            }
            if (isDeltaCRLIndicatorEnabled() && aSN1Integer == null) {
                return false;
            }
            if (isCompleteCRLEnabled() && aSN1Integer != null) {
                return false;
            }
            if (aSN1Integer != null && this.maxBaseCRLNumber != null && aSN1Integer.getPositiveValue().compareTo(this.maxBaseCRLNumber) == 1) {
                return false;
            }
            if (this.issuingDistributionPointEnabled) {
                byte[] extensionValue2 = x509crl.getExtensionValue(com.android.internal.org.bouncycastle.asn1.x509.Extension.issuingDistributionPoint.getId());
                if (this.issuingDistributionPoint == null) {
                    if (extensionValue2 != null) {
                        return false;
                    }
                } else if (!com.android.internal.org.bouncycastle.util.Arrays.areEqual(extensionValue2, this.issuingDistributionPoint)) {
                    return false;
                }
            }
            return super.match((java.security.cert.CRL) x509crl);
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    @Override // java.security.cert.X509CRLSelector, java.security.cert.CRLSelector
    public boolean match(java.security.cert.CRL crl) {
        return match((java.lang.Object) crl);
    }

    public boolean isDeltaCRLIndicatorEnabled() {
        return this.deltaCRLIndicator;
    }

    public void setDeltaCRLIndicatorEnabled(boolean z) {
        this.deltaCRLIndicator = z;
    }

    public static com.android.internal.org.bouncycastle.x509.X509CRLStoreSelector getInstance(java.security.cert.X509CRLSelector x509CRLSelector) {
        if (x509CRLSelector == null) {
            throw new java.lang.IllegalArgumentException("cannot create from null selector");
        }
        com.android.internal.org.bouncycastle.x509.X509CRLStoreSelector x509CRLStoreSelector = new com.android.internal.org.bouncycastle.x509.X509CRLStoreSelector();
        x509CRLStoreSelector.setCertificateChecking(x509CRLSelector.getCertificateChecking());
        x509CRLStoreSelector.setDateAndTime(x509CRLSelector.getDateAndTime());
        try {
            x509CRLStoreSelector.setIssuerNames(x509CRLSelector.getIssuerNames());
            x509CRLStoreSelector.setIssuers(x509CRLSelector.getIssuers());
            x509CRLStoreSelector.setMaxCRLNumber(x509CRLSelector.getMaxCRL());
            x509CRLStoreSelector.setMinCRLNumber(x509CRLSelector.getMinCRL());
            return x509CRLStoreSelector;
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException(e.getMessage());
        }
    }

    @Override // java.security.cert.X509CRLSelector, java.security.cert.CRLSelector, com.android.internal.org.bouncycastle.util.Selector
    public java.lang.Object clone() {
        com.android.internal.org.bouncycastle.x509.X509CRLStoreSelector x509CRLStoreSelector = getInstance(this);
        x509CRLStoreSelector.deltaCRLIndicator = this.deltaCRLIndicator;
        x509CRLStoreSelector.completeCRLEnabled = this.completeCRLEnabled;
        x509CRLStoreSelector.maxBaseCRLNumber = this.maxBaseCRLNumber;
        x509CRLStoreSelector.attrCertChecking = this.attrCertChecking;
        x509CRLStoreSelector.issuingDistributionPointEnabled = this.issuingDistributionPointEnabled;
        x509CRLStoreSelector.issuingDistributionPoint = com.android.internal.org.bouncycastle.util.Arrays.clone(this.issuingDistributionPoint);
        return x509CRLStoreSelector;
    }

    public boolean isCompleteCRLEnabled() {
        return this.completeCRLEnabled;
    }

    public void setCompleteCRLEnabled(boolean z) {
        this.completeCRLEnabled = z;
    }

    public java.math.BigInteger getMaxBaseCRLNumber() {
        return this.maxBaseCRLNumber;
    }

    public void setMaxBaseCRLNumber(java.math.BigInteger bigInteger) {
        this.maxBaseCRLNumber = bigInteger;
    }

    public byte[] getIssuingDistributionPoint() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.issuingDistributionPoint);
    }

    public void setIssuingDistributionPoint(byte[] bArr) {
        this.issuingDistributionPoint = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
    }
}
