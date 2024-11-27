package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public class AttributeCertificateIssuer implements java.security.cert.CertSelector, com.android.internal.org.bouncycastle.util.Selector {
    final com.android.internal.org.bouncycastle.asn1.ASN1Encodable form;

    public AttributeCertificateIssuer(com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer attCertIssuer) {
        this.form = attCertIssuer.getIssuer();
    }

    public AttributeCertificateIssuer(javax.security.auth.x500.X500Principal x500Principal) throws java.io.IOException {
        this(new com.android.internal.org.bouncycastle.jce.X509Principal(x500Principal.getEncoded()));
    }

    public AttributeCertificateIssuer(com.android.internal.org.bouncycastle.jce.X509Principal x509Principal) {
        this.form = new com.android.internal.org.bouncycastle.asn1.x509.V2Form(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(new com.android.internal.org.bouncycastle.asn1.DERSequence(new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(x509Principal))));
    }

    private java.lang.Object[] getNames() {
        com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames;
        if (this.form instanceof com.android.internal.org.bouncycastle.asn1.x509.V2Form) {
            generalNames = ((com.android.internal.org.bouncycastle.asn1.x509.V2Form) this.form).getIssuerName();
        } else {
            generalNames = (com.android.internal.org.bouncycastle.asn1.x509.GeneralNames) this.form;
        }
        com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = generalNames.getNames();
        java.util.ArrayList arrayList = new java.util.ArrayList(names.length);
        for (int i = 0; i != names.length; i++) {
            if (names[i].getTagNo() == 4) {
                try {
                    arrayList.add(new javax.security.auth.x500.X500Principal(names[i].getName().toASN1Primitive().getEncoded()));
                } catch (java.io.IOException e) {
                    throw new java.lang.RuntimeException("badly formed Name object");
                }
            }
        }
        return arrayList.toArray(new java.lang.Object[arrayList.size()]);
    }

    public java.security.Principal[] getPrincipals() {
        java.lang.Object[] names = getNames();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i != names.length; i++) {
            if (names[i] instanceof java.security.Principal) {
                arrayList.add(names[i]);
            }
        }
        return (java.security.Principal[]) arrayList.toArray(new java.security.Principal[arrayList.size()]);
    }

    private boolean matchesDN(javax.security.auth.x500.X500Principal x500Principal, com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames) {
        com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = generalNames.getNames();
        for (int i = 0; i != names.length; i++) {
            com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName = names[i];
            if (generalName.getTagNo() == 4) {
                try {
                    if (new javax.security.auth.x500.X500Principal(generalName.getName().toASN1Primitive().getEncoded()).equals(x500Principal)) {
                        return true;
                    }
                } catch (java.io.IOException e) {
                }
            }
        }
        return false;
    }

    @Override // java.security.cert.CertSelector, com.android.internal.org.bouncycastle.util.Selector
    public java.lang.Object clone() {
        return new com.android.internal.org.bouncycastle.x509.AttributeCertificateIssuer(com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer.getInstance(this.form));
    }

    @Override // java.security.cert.CertSelector
    public boolean match(java.security.cert.Certificate certificate) {
        if (!(certificate instanceof java.security.cert.X509Certificate)) {
            return false;
        }
        java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) certificate;
        if (this.form instanceof com.android.internal.org.bouncycastle.asn1.x509.V2Form) {
            com.android.internal.org.bouncycastle.asn1.x509.V2Form v2Form = (com.android.internal.org.bouncycastle.asn1.x509.V2Form) this.form;
            if (v2Form.getBaseCertificateID() != null) {
                return v2Form.getBaseCertificateID().getSerial().hasValue(x509Certificate.getSerialNumber()) && matchesDN(x509Certificate.getIssuerX500Principal(), v2Form.getBaseCertificateID().getIssuer());
            }
            if (matchesDN(x509Certificate.getSubjectX500Principal(), v2Form.getIssuerName())) {
                return true;
            }
        } else {
            if (matchesDN(x509Certificate.getSubjectX500Principal(), (com.android.internal.org.bouncycastle.asn1.x509.GeneralNames) this.form)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.x509.AttributeCertificateIssuer)) {
            return false;
        }
        return this.form.equals(((com.android.internal.org.bouncycastle.x509.AttributeCertificateIssuer) obj).form);
    }

    public int hashCode() {
        return this.form.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(java.lang.Object obj) {
        if (!(obj instanceof java.security.cert.X509Certificate)) {
            return false;
        }
        return match((java.security.cert.Certificate) obj);
    }
}
