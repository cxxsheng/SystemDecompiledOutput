package com.android.internal.org.bouncycastle.jcajce;

/* loaded from: classes4.dex */
public class PKIXCRLStoreSelector<T extends java.security.cert.CRL> implements com.android.internal.org.bouncycastle.util.Selector<T> {
    private final java.security.cert.CRLSelector baseSelector;
    private final boolean completeCRLEnabled;
    private final boolean deltaCRLIndicator;
    private final byte[] issuingDistributionPoint;
    private final boolean issuingDistributionPointEnabled;
    private final java.math.BigInteger maxBaseCRLNumber;

    public static class Builder {
        private final java.security.cert.CRLSelector baseSelector;
        private boolean deltaCRLIndicator = false;
        private boolean completeCRLEnabled = false;
        private java.math.BigInteger maxBaseCRLNumber = null;
        private byte[] issuingDistributionPoint = null;
        private boolean issuingDistributionPointEnabled = false;

        public Builder(java.security.cert.CRLSelector cRLSelector) {
            this.baseSelector = (java.security.cert.CRLSelector) cRLSelector.clone();
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector.Builder setCompleteCRLEnabled(boolean z) {
            this.completeCRLEnabled = z;
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector.Builder setDeltaCRLIndicatorEnabled(boolean z) {
            this.deltaCRLIndicator = z;
            return this;
        }

        public void setMaxBaseCRLNumber(java.math.BigInteger bigInteger) {
            this.maxBaseCRLNumber = bigInteger;
        }

        public void setIssuingDistributionPointEnabled(boolean z) {
            this.issuingDistributionPointEnabled = z;
        }

        public void setIssuingDistributionPoint(byte[] bArr) {
            this.issuingDistributionPoint = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector<? extends java.security.cert.CRL> build() {
            return new com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector<>(this);
        }
    }

    private PKIXCRLStoreSelector(com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector.Builder builder) {
        this.baseSelector = builder.baseSelector;
        this.deltaCRLIndicator = builder.deltaCRLIndicator;
        this.completeCRLEnabled = builder.completeCRLEnabled;
        this.maxBaseCRLNumber = builder.maxBaseCRLNumber;
        this.issuingDistributionPoint = builder.issuingDistributionPoint;
        this.issuingDistributionPointEnabled = builder.issuingDistributionPointEnabled;
    }

    public boolean isIssuingDistributionPointEnabled() {
        return this.issuingDistributionPointEnabled;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(java.security.cert.CRL crl) {
        com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer;
        if (!(crl instanceof java.security.cert.X509CRL)) {
            return this.baseSelector.match(crl);
        }
        java.security.cert.X509CRL x509crl = (java.security.cert.X509CRL) crl;
        try {
            byte[] extensionValue = x509crl.getExtensionValue(com.android.internal.org.bouncycastle.asn1.x509.Extension.deltaCRLIndicator.getId());
            if (extensionValue == null) {
                aSN1Integer = null;
            } else {
                aSN1Integer = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(extensionValue).getOctets());
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
            return this.baseSelector.match(crl);
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    public boolean isDeltaCRLIndicatorEnabled() {
        return this.deltaCRLIndicator;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public java.lang.Object clone() {
        return this;
    }

    public boolean isCompleteCRLEnabled() {
        return this.completeCRLEnabled;
    }

    public java.math.BigInteger getMaxBaseCRLNumber() {
        return this.maxBaseCRLNumber;
    }

    public byte[] getIssuingDistributionPoint() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.issuingDistributionPoint);
    }

    public java.security.cert.X509Certificate getCertificateChecking() {
        if (this.baseSelector instanceof java.security.cert.X509CRLSelector) {
            return ((java.security.cert.X509CRLSelector) this.baseSelector).getCertificateChecking();
        }
        return null;
    }

    public static java.util.Collection<? extends java.security.cert.CRL> getCRLs(com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector pKIXCRLStoreSelector, java.security.cert.CertStore certStore) throws java.security.cert.CertStoreException {
        return certStore.getCRLs(new com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector.SelectorClone(pKIXCRLStoreSelector));
    }

    private static class SelectorClone extends java.security.cert.X509CRLSelector {
        private final com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector selector;

        SelectorClone(com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector pKIXCRLStoreSelector) {
            this.selector = pKIXCRLStoreSelector;
            if (pKIXCRLStoreSelector.baseSelector instanceof java.security.cert.X509CRLSelector) {
                java.security.cert.X509CRLSelector x509CRLSelector = (java.security.cert.X509CRLSelector) pKIXCRLStoreSelector.baseSelector;
                setCertificateChecking(x509CRLSelector.getCertificateChecking());
                setDateAndTime(x509CRLSelector.getDateAndTime());
                setIssuers(x509CRLSelector.getIssuers());
                setMinCRLNumber(x509CRLSelector.getMinCRL());
                setMaxCRLNumber(x509CRLSelector.getMaxCRL());
            }
        }

        @Override // java.security.cert.X509CRLSelector, java.security.cert.CRLSelector
        public boolean match(java.security.cert.CRL crl) {
            return this.selector == null ? crl != null : this.selector.match(crl);
        }
    }
}
