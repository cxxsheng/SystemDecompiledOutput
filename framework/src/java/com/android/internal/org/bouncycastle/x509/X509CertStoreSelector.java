package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public class X509CertStoreSelector extends java.security.cert.X509CertSelector implements com.android.internal.org.bouncycastle.util.Selector {
    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(java.lang.Object obj) {
        if (!(obj instanceof java.security.cert.X509Certificate)) {
            return false;
        }
        return super.match((java.security.cert.Certificate) obj);
    }

    @Override // java.security.cert.X509CertSelector, java.security.cert.CertSelector
    public boolean match(java.security.cert.Certificate certificate) {
        return match((java.lang.Object) certificate);
    }

    @Override // java.security.cert.X509CertSelector, java.security.cert.CertSelector, com.android.internal.org.bouncycastle.util.Selector
    public java.lang.Object clone() {
        return (com.android.internal.org.bouncycastle.x509.X509CertStoreSelector) super.clone();
    }

    public static com.android.internal.org.bouncycastle.x509.X509CertStoreSelector getInstance(java.security.cert.X509CertSelector x509CertSelector) {
        if (x509CertSelector == null) {
            throw new java.lang.IllegalArgumentException("cannot create from null selector");
        }
        com.android.internal.org.bouncycastle.x509.X509CertStoreSelector x509CertStoreSelector = new com.android.internal.org.bouncycastle.x509.X509CertStoreSelector();
        x509CertStoreSelector.setAuthorityKeyIdentifier(x509CertSelector.getAuthorityKeyIdentifier());
        x509CertStoreSelector.setBasicConstraints(x509CertSelector.getBasicConstraints());
        x509CertStoreSelector.setCertificate(x509CertSelector.getCertificate());
        x509CertStoreSelector.setCertificateValid(x509CertSelector.getCertificateValid());
        x509CertStoreSelector.setMatchAllSubjectAltNames(x509CertSelector.getMatchAllSubjectAltNames());
        try {
            x509CertStoreSelector.setPathToNames(x509CertSelector.getPathToNames());
            x509CertStoreSelector.setExtendedKeyUsage(x509CertSelector.getExtendedKeyUsage());
            x509CertStoreSelector.setNameConstraints(x509CertSelector.getNameConstraints());
            x509CertStoreSelector.setPolicy(x509CertSelector.getPolicy());
            x509CertStoreSelector.setSubjectPublicKeyAlgID(x509CertSelector.getSubjectPublicKeyAlgID());
            x509CertStoreSelector.setSubjectAlternativeNames(x509CertSelector.getSubjectAlternativeNames());
            x509CertStoreSelector.setIssuer(x509CertSelector.getIssuer());
            x509CertStoreSelector.setKeyUsage(x509CertSelector.getKeyUsage());
            x509CertStoreSelector.setPrivateKeyValid(x509CertSelector.getPrivateKeyValid());
            x509CertStoreSelector.setSerialNumber(x509CertSelector.getSerialNumber());
            x509CertStoreSelector.setSubject(x509CertSelector.getSubject());
            x509CertStoreSelector.setSubjectKeyIdentifier(x509CertSelector.getSubjectKeyIdentifier());
            x509CertStoreSelector.setSubjectPublicKey(x509CertSelector.getSubjectPublicKey());
            return x509CertStoreSelector;
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("error in passed in selector: " + e);
        }
    }
}
