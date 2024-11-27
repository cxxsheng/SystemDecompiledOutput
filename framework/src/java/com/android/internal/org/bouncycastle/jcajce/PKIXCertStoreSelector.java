package com.android.internal.org.bouncycastle.jcajce;

/* loaded from: classes4.dex */
public class PKIXCertStoreSelector<T extends java.security.cert.Certificate> implements com.android.internal.org.bouncycastle.util.Selector<T> {
    private final java.security.cert.CertSelector baseSelector;

    public static class Builder {
        private final java.security.cert.CertSelector baseSelector;

        public Builder(java.security.cert.CertSelector certSelector) {
            this.baseSelector = (java.security.cert.CertSelector) certSelector.clone();
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector<? extends java.security.cert.Certificate> build() {
            return new com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector<>(this.baseSelector);
        }
    }

    private PKIXCertStoreSelector(java.security.cert.CertSelector certSelector) {
        this.baseSelector = certSelector;
    }

    public java.security.cert.Certificate getCertificate() {
        if (this.baseSelector instanceof java.security.cert.X509CertSelector) {
            return ((java.security.cert.X509CertSelector) this.baseSelector).getCertificate();
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(java.security.cert.Certificate certificate) {
        return this.baseSelector.match(certificate);
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public java.lang.Object clone() {
        return new com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector(this.baseSelector);
    }

    public static java.util.Collection<? extends java.security.cert.Certificate> getCertificates(com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector pKIXCertStoreSelector, java.security.cert.CertStore certStore) throws java.security.cert.CertStoreException {
        return certStore.getCertificates(new com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector.SelectorClone(pKIXCertStoreSelector));
    }

    private static class SelectorClone extends java.security.cert.X509CertSelector {
        private final com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector selector;

        SelectorClone(com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector pKIXCertStoreSelector) {
            this.selector = pKIXCertStoreSelector;
            if (pKIXCertStoreSelector.baseSelector instanceof java.security.cert.X509CertSelector) {
                java.security.cert.X509CertSelector x509CertSelector = (java.security.cert.X509CertSelector) pKIXCertStoreSelector.baseSelector;
                setAuthorityKeyIdentifier(x509CertSelector.getAuthorityKeyIdentifier());
                setBasicConstraints(x509CertSelector.getBasicConstraints());
                setCertificate(x509CertSelector.getCertificate());
                setCertificateValid(x509CertSelector.getCertificateValid());
                setKeyUsage(x509CertSelector.getKeyUsage());
                setMatchAllSubjectAltNames(x509CertSelector.getMatchAllSubjectAltNames());
                setPrivateKeyValid(x509CertSelector.getPrivateKeyValid());
                setSerialNumber(x509CertSelector.getSerialNumber());
                setSubjectKeyIdentifier(x509CertSelector.getSubjectKeyIdentifier());
                setSubjectPublicKey(x509CertSelector.getSubjectPublicKey());
                try {
                    setExtendedKeyUsage(x509CertSelector.getExtendedKeyUsage());
                    setIssuer(x509CertSelector.getIssuerAsBytes());
                    setNameConstraints(x509CertSelector.getNameConstraints());
                    setPathToNames(x509CertSelector.getPathToNames());
                    setPolicy(x509CertSelector.getPolicy());
                    setSubject(x509CertSelector.getSubjectAsBytes());
                    setSubjectAlternativeNames(x509CertSelector.getSubjectAlternativeNames());
                    setSubjectPublicKeyAlgID(x509CertSelector.getSubjectPublicKeyAlgID());
                } catch (java.io.IOException e) {
                    throw new java.lang.IllegalStateException("base selector invalid: " + e.getMessage(), e);
                }
            }
        }

        @Override // java.security.cert.X509CertSelector, java.security.cert.CertSelector
        public boolean match(java.security.cert.Certificate certificate) {
            return this.selector == null ? certificate != null : this.selector.match(certificate);
        }
    }
}
