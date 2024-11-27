package com.android.internal.org.bouncycastle.cert;

/* loaded from: classes4.dex */
public class AttributeCertificateIssuer implements com.android.internal.org.bouncycastle.util.Selector {
    final com.android.internal.org.bouncycastle.asn1.ASN1Encodable form;

    public AttributeCertificateIssuer(com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer attCertIssuer) {
        this.form = attCertIssuer.getIssuer();
    }

    public AttributeCertificateIssuer(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        this.form = new com.android.internal.org.bouncycastle.asn1.x509.V2Form(new com.android.internal.org.bouncycastle.asn1.x509.GeneralNames(new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(x500Name)));
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name[] getNames() {
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
                arrayList.add(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(names[i].getName()));
            }
        }
        return (com.android.internal.org.bouncycastle.asn1.x500.X500Name[]) arrayList.toArray(new com.android.internal.org.bouncycastle.asn1.x500.X500Name[arrayList.size()]);
    }

    private boolean matchesDN(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames) {
        com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = generalNames.getNames();
        for (int i = 0; i != names.length; i++) {
            com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName = names[i];
            if (generalName.getTagNo() == 4 && com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(generalName.getName()).equals(x500Name)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public java.lang.Object clone() {
        return new com.android.internal.org.bouncycastle.cert.AttributeCertificateIssuer(com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer.getInstance(this.form));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.cert.AttributeCertificateIssuer)) {
            return false;
        }
        return this.form.equals(((com.android.internal.org.bouncycastle.cert.AttributeCertificateIssuer) obj).form);
    }

    public int hashCode() {
        return this.form.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.cert.X509CertificateHolder)) {
            return false;
        }
        com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder = (com.android.internal.org.bouncycastle.cert.X509CertificateHolder) obj;
        if (this.form instanceof com.android.internal.org.bouncycastle.asn1.x509.V2Form) {
            com.android.internal.org.bouncycastle.asn1.x509.V2Form v2Form = (com.android.internal.org.bouncycastle.asn1.x509.V2Form) this.form;
            if (v2Form.getBaseCertificateID() != null) {
                return v2Form.getBaseCertificateID().getSerial().hasValue(x509CertificateHolder.getSerialNumber()) && matchesDN(x509CertificateHolder.getIssuer(), v2Form.getBaseCertificateID().getIssuer());
            }
            if (matchesDN(x509CertificateHolder.getSubject(), v2Form.getIssuerName())) {
                return true;
            }
        } else {
            if (matchesDN(x509CertificateHolder.getSubject(), (com.android.internal.org.bouncycastle.asn1.x509.GeneralNames) this.form)) {
                return true;
            }
        }
        return false;
    }
}
