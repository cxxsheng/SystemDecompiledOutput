package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public class AttributeCertificateHolder implements java.security.cert.CertSelector, com.android.internal.org.bouncycastle.util.Selector {
    final com.android.internal.org.bouncycastle.asn1.x509.Holder holder;

    AttributeCertificateHolder(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.holder = com.android.internal.org.bouncycastle.asn1.x509.Holder.getInstance(aSN1Sequence);
    }

    public AttributeCertificateHolder(com.android.internal.org.bouncycastle.jce.X509Principal x509Principal, java.math.BigInteger bigInteger) {
        this.holder = new com.android.internal.org.bouncycastle.asn1.x509.Holder(new com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(new com.android.internal.org.bouncycastle.asn1.DERSequence(new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(x509Principal))), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger)));
    }

    public AttributeCertificateHolder(javax.security.auth.x500.X500Principal x500Principal, java.math.BigInteger bigInteger) {
        this(com.android.internal.org.bouncycastle.x509.X509Util.convertPrincipal(x500Principal), bigInteger);
    }

    public AttributeCertificateHolder(java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertificateParsingException {
        try {
            this.holder = new com.android.internal.org.bouncycastle.asn1.x509.Holder(new com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial(generateGeneralNames(com.android.internal.org.bouncycastle.jce.PrincipalUtil.getIssuerX509Principal(x509Certificate)), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(x509Certificate.getSerialNumber())));
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CertificateParsingException(e.getMessage());
        }
    }

    public AttributeCertificateHolder(com.android.internal.org.bouncycastle.jce.X509Principal x509Principal) {
        this.holder = new com.android.internal.org.bouncycastle.asn1.x509.Holder(generateGeneralNames(x509Principal));
    }

    public AttributeCertificateHolder(javax.security.auth.x500.X500Principal x500Principal) {
        this(com.android.internal.org.bouncycastle.x509.X509Util.convertPrincipal(x500Principal));
    }

    public AttributeCertificateHolder(int i, java.lang.String str, java.lang.String str2, byte[] bArr) {
        this.holder = new com.android.internal.org.bouncycastle.asn1.x509.Holder(new com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo(i, new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str2), new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str)), com.android.internal.org.bouncycastle.util.Arrays.clone(bArr)));
    }

    public int getDigestedObjectType() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getDigestedObjectType().intValueExact();
        }
        return -1;
    }

    public java.lang.String getDigestAlgorithm() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getDigestAlgorithm().getAlgorithm().getId();
        }
        return null;
    }

    public byte[] getObjectDigest() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getObjectDigest().getBytes();
        }
        return null;
    }

    public java.lang.String getOtherObjectTypeID() {
        if (this.holder.getObjectDigestInfo() != null) {
            this.holder.getObjectDigestInfo().getOtherObjectTypeID().getId();
            return null;
        }
        return null;
    }

    private com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generateGeneralNames(com.android.internal.org.bouncycastle.jce.X509Principal x509Principal) {
        return com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(new com.android.internal.org.bouncycastle.asn1.DERSequence(new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(x509Principal)));
    }

    private boolean matchesDN(com.android.internal.org.bouncycastle.jce.X509Principal x509Principal, com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames) {
        com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = generalNames.getNames();
        for (int i = 0; i != names.length; i++) {
            com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName = names[i];
            if (generalName.getTagNo() == 4) {
                try {
                    if (new com.android.internal.org.bouncycastle.jce.X509Principal(generalName.getName().toASN1Primitive().getEncoded()).equals(x509Principal)) {
                        return true;
                    }
                } catch (java.io.IOException e) {
                }
            }
        }
        return false;
    }

    private java.lang.Object[] getNames(com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] generalNameArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList(generalNameArr.length);
        for (int i = 0; i != generalNameArr.length; i++) {
            if (generalNameArr[i].getTagNo() == 4) {
                try {
                    arrayList.add(new javax.security.auth.x500.X500Principal(generalNameArr[i].getName().toASN1Primitive().getEncoded()));
                } catch (java.io.IOException e) {
                    throw new java.lang.RuntimeException("badly formed Name object");
                }
            }
        }
        return arrayList.toArray(new java.lang.Object[arrayList.size()]);
    }

    private java.security.Principal[] getPrincipals(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames) {
        java.lang.Object[] names = getNames(generalNames.getNames());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i != names.length; i++) {
            if (names[i] instanceof java.security.Principal) {
                arrayList.add(names[i]);
            }
        }
        return (java.security.Principal[]) arrayList.toArray(new java.security.Principal[arrayList.size()]);
    }

    public java.security.Principal[] getEntityNames() {
        if (this.holder.getEntityName() != null) {
            return getPrincipals(this.holder.getEntityName());
        }
        return null;
    }

    public java.security.Principal[] getIssuer() {
        if (this.holder.getBaseCertificateID() != null) {
            return getPrincipals(this.holder.getBaseCertificateID().getIssuer());
        }
        return null;
    }

    public java.math.BigInteger getSerialNumber() {
        if (this.holder.getBaseCertificateID() != null) {
            return this.holder.getBaseCertificateID().getSerial().getValue();
        }
        return null;
    }

    @Override // java.security.cert.CertSelector, com.android.internal.org.bouncycastle.util.Selector
    public java.lang.Object clone() {
        return new com.android.internal.org.bouncycastle.x509.AttributeCertificateHolder((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) this.holder.toASN1Primitive());
    }

    @Override // java.security.cert.CertSelector
    public boolean match(java.security.cert.Certificate certificate) {
        if (!(certificate instanceof java.security.cert.X509Certificate)) {
            return false;
        }
        java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) certificate;
        try {
            if (this.holder.getBaseCertificateID() != null) {
                return this.holder.getBaseCertificateID().getSerial().hasValue(x509Certificate.getSerialNumber()) && matchesDN(com.android.internal.org.bouncycastle.jce.PrincipalUtil.getIssuerX509Principal(x509Certificate), this.holder.getBaseCertificateID().getIssuer());
            }
            if (this.holder.getEntityName() != null && matchesDN(com.android.internal.org.bouncycastle.jce.PrincipalUtil.getSubjectX509Principal(x509Certificate), this.holder.getEntityName())) {
                return true;
            }
            if (this.holder.getObjectDigestInfo() == null) {
                return false;
            }
            try {
                java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance(getDigestAlgorithm(), com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
                switch (getDigestedObjectType()) {
                    case 0:
                        messageDigest.update(certificate.getPublicKey().getEncoded());
                        break;
                    case 1:
                        messageDigest.update(certificate.getEncoded());
                        break;
                }
                com.android.internal.org.bouncycastle.util.Arrays.areEqual(messageDigest.digest(), getObjectDigest());
                return false;
            } catch (java.lang.Exception e) {
                return false;
            }
        } catch (java.security.cert.CertificateEncodingException e2) {
            return false;
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.x509.AttributeCertificateHolder)) {
            return false;
        }
        return this.holder.equals(((com.android.internal.org.bouncycastle.x509.AttributeCertificateHolder) obj).holder);
    }

    public int hashCode() {
        return this.holder.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(java.lang.Object obj) {
        if (!(obj instanceof java.security.cert.X509Certificate)) {
            return false;
        }
        return match((java.security.cert.Certificate) obj);
    }
}
