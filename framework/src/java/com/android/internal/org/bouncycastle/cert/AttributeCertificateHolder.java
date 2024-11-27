package com.android.internal.org.bouncycastle.cert;

/* loaded from: classes4.dex */
public class AttributeCertificateHolder implements com.android.internal.org.bouncycastle.util.Selector {
    private static com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestCalculatorProvider;
    final com.android.internal.org.bouncycastle.asn1.x509.Holder holder;

    AttributeCertificateHolder(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.holder = com.android.internal.org.bouncycastle.asn1.x509.Holder.getInstance(aSN1Sequence);
    }

    public AttributeCertificateHolder(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, java.math.BigInteger bigInteger) {
        this.holder = new com.android.internal.org.bouncycastle.asn1.x509.Holder(new com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial(generateGeneralNames(x500Name), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger)));
    }

    public AttributeCertificateHolder(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) {
        this.holder = new com.android.internal.org.bouncycastle.asn1.x509.Holder(new com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial(generateGeneralNames(x509CertificateHolder.getIssuer()), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(x509CertificateHolder.getSerialNumber())));
    }

    public AttributeCertificateHolder(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        this.holder = new com.android.internal.org.bouncycastle.asn1.x509.Holder(generateGeneralNames(x500Name));
    }

    public AttributeCertificateHolder(int i, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier2, byte[] bArr) {
        this.holder = new com.android.internal.org.bouncycastle.asn1.x509.Holder(new com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo(i, aSN1ObjectIdentifier2, new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier), com.android.internal.org.bouncycastle.util.Arrays.clone(bArr)));
    }

    public int getDigestedObjectType() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getDigestedObjectType().intValueExact();
        }
        return -1;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getDigestAlgorithm() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getDigestAlgorithm();
        }
        return null;
    }

    public byte[] getObjectDigest() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getObjectDigest().getBytes();
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getOtherObjectTypeID() {
        if (this.holder.getObjectDigestInfo() != null) {
            new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(this.holder.getObjectDigestInfo().getOtherObjectTypeID().getId());
            return null;
        }
        return null;
    }

    private com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generateGeneralNames(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        return new com.android.internal.org.bouncycastle.asn1.x509.GeneralNames(new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(x500Name));
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

    private com.android.internal.org.bouncycastle.asn1.x500.X500Name[] getPrincipals(com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] generalNameArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList(generalNameArr.length);
        for (int i = 0; i != generalNameArr.length; i++) {
            if (generalNameArr[i].getTagNo() == 4) {
                arrayList.add(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(generalNameArr[i].getName()));
            }
        }
        return (com.android.internal.org.bouncycastle.asn1.x500.X500Name[]) arrayList.toArray(new com.android.internal.org.bouncycastle.asn1.x500.X500Name[arrayList.size()]);
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name[] getEntityNames() {
        if (this.holder.getEntityName() != null) {
            return getPrincipals(this.holder.getEntityName().getNames());
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name[] getIssuer() {
        if (this.holder.getBaseCertificateID() != null) {
            return getPrincipals(this.holder.getBaseCertificateID().getIssuer().getNames());
        }
        return null;
    }

    public java.math.BigInteger getSerialNumber() {
        if (this.holder.getBaseCertificateID() != null) {
            return this.holder.getBaseCertificateID().getSerial().getValue();
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public java.lang.Object clone() {
        return new com.android.internal.org.bouncycastle.cert.AttributeCertificateHolder((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) this.holder.toASN1Primitive());
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.cert.X509CertificateHolder)) {
            return false;
        }
        com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder = (com.android.internal.org.bouncycastle.cert.X509CertificateHolder) obj;
        if (this.holder.getBaseCertificateID() != null) {
            return this.holder.getBaseCertificateID().getSerial().hasValue(x509CertificateHolder.getSerialNumber()) && matchesDN(x509CertificateHolder.getIssuer(), this.holder.getBaseCertificateID().getIssuer());
        }
        if (this.holder.getEntityName() != null && matchesDN(x509CertificateHolder.getSubject(), this.holder.getEntityName())) {
            return true;
        }
        if (this.holder.getObjectDigestInfo() == null) {
            return false;
        }
        try {
            com.android.internal.org.bouncycastle.operator.DigestCalculator digestCalculator = digestCalculatorProvider.get(this.holder.getObjectDigestInfo().getDigestAlgorithm());
            java.io.OutputStream outputStream = digestCalculator.getOutputStream();
            switch (getDigestedObjectType()) {
                case 0:
                    outputStream.write(x509CertificateHolder.getSubjectPublicKeyInfo().getEncoded());
                    break;
                case 1:
                    outputStream.write(x509CertificateHolder.getEncoded());
                    break;
            }
            outputStream.close();
            com.android.internal.org.bouncycastle.util.Arrays.areEqual(digestCalculator.getDigest(), getObjectDigest());
            return false;
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.cert.AttributeCertificateHolder)) {
            return false;
        }
        return this.holder.equals(((com.android.internal.org.bouncycastle.cert.AttributeCertificateHolder) obj).holder);
    }

    public int hashCode() {
        return this.holder.hashCode();
    }

    public static void setDigestCalculatorProvider(com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestCalculatorProvider2) {
        digestCalculatorProvider = digestCalculatorProvider2;
    }
}
