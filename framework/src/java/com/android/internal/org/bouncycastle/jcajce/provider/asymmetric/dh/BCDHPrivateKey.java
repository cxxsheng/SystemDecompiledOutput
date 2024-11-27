package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh;

/* loaded from: classes4.dex */
public class BCDHPrivateKey implements javax.crypto.interfaces.DHPrivateKey, com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 311058815616901812L;
    private transient com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
    private transient com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters dhPrivateKey;
    private transient javax.crypto.spec.DHParameterSpec dhSpec;
    private transient com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo info;
    private java.math.BigInteger x;

    protected BCDHPrivateKey() {
    }

    BCDHPrivateKey(javax.crypto.interfaces.DHPrivateKey dHPrivateKey) {
        this.x = dHPrivateKey.getX();
        this.dhSpec = dHPrivateKey.getParams();
    }

    BCDHPrivateKey(javax.crypto.spec.DHPrivateKeySpec dHPrivateKeySpec) {
        this.x = dHPrivateKeySpec.getX();
        if (dHPrivateKeySpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.DHExtendedPrivateKeySpec) {
            this.dhSpec = ((com.android.internal.org.bouncycastle.jcajce.spec.DHExtendedPrivateKeySpec) dHPrivateKeySpec).getParams();
        } else {
            this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHPrivateKeySpec.getP(), dHPrivateKeySpec.getG());
        }
    }

    public BCDHPrivateKey(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(privateKeyInfo.getPrivateKeyAlgorithm().getParameters());
        com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) privateKeyInfo.parsePrivateKey();
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
        this.info = privateKeyInfo;
        this.x = aSN1Integer.getValue();
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement)) {
            com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter dHParameter = com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter.getInstance(aSN1Sequence);
            if (dHParameter.getL() != null) {
                this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHParameter.getP(), dHParameter.getG(), dHParameter.getL().intValue());
                this.dhPrivateKey = new com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters(this.x, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(dHParameter.getP(), dHParameter.getG(), null, dHParameter.getL().intValue()));
                return;
            } else {
                this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHParameter.getP(), dHParameter.getG());
                this.dhPrivateKey = new com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters(this.x, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(dHParameter.getP(), dHParameter.getG()));
                return;
            }
        }
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.dhpublicnumber)) {
            com.android.internal.org.bouncycastle.asn1.x9.DomainParameters domainParameters = com.android.internal.org.bouncycastle.asn1.x9.DomainParameters.getInstance(aSN1Sequence);
            this.dhSpec = new com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec(domainParameters.getP(), domainParameters.getQ(), domainParameters.getG(), domainParameters.getJ(), 0);
            this.dhPrivateKey = new com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters(this.x, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(domainParameters.getP(), domainParameters.getG(), domainParameters.getQ(), domainParameters.getJ(), (com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters) null));
            return;
        }
        throw new java.lang.IllegalArgumentException("unknown algorithm type: " + algorithm);
    }

    BCDHPrivateKey(com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters dHPrivateKeyParameters) {
        this.x = dHPrivateKeyParameters.getX();
        this.dhSpec = new com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec(dHPrivateKeyParameters.getParameters());
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        return "DH";
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return "PKCS#8";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo;
        com.android.internal.org.bouncycastle.asn1.x9.ValidationParams validationParams;
        try {
            if (this.info != null) {
                return this.info.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            }
            if ((this.dhSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) && ((com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) this.dhSpec).getQ() != null) {
                com.android.internal.org.bouncycastle.crypto.params.DHParameters domainParameters = ((com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) this.dhSpec).getDomainParameters();
                com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters validationParameters = domainParameters.getValidationParameters();
                if (validationParameters == null) {
                    validationParams = null;
                } else {
                    validationParams = new com.android.internal.org.bouncycastle.asn1.x9.ValidationParams(validationParameters.getSeed(), validationParameters.getCounter());
                }
                privateKeyInfo = new com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.dhpublicnumber, new com.android.internal.org.bouncycastle.asn1.x9.DomainParameters(domainParameters.getP(), domainParameters.getG(), domainParameters.getQ(), domainParameters.getJ(), validationParams).toASN1Primitive()), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getX()));
            } else {
                privateKeyInfo = new com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement, new com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL()).toASN1Primitive()), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getX()));
            }
            return privateKeyInfo.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    public java.lang.String toString() {
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.DHUtil.privateKeyToString("DH", this.x, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
    }

    @Override // javax.crypto.interfaces.DHKey
    public javax.crypto.spec.DHParameterSpec getParams() {
        return this.dhSpec;
    }

    @Override // javax.crypto.interfaces.DHPrivateKey
    public java.math.BigInteger getX() {
        return this.x;
    }

    com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters engineGetKeyParameters() {
        if (this.dhPrivateKey != null) {
            return this.dhPrivateKey;
        }
        if (this.dhSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) {
            return new com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters(this.x, ((com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) this.dhSpec).getDomainParameters());
        }
        return new com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters(this.x, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(this.dhSpec.getP(), this.dhSpec.getG(), null, this.dhSpec.getL()));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof javax.crypto.interfaces.DHPrivateKey)) {
            return false;
        }
        javax.crypto.interfaces.DHPrivateKey dHPrivateKey = (javax.crypto.interfaces.DHPrivateKey) obj;
        return getX().equals(dHPrivateKey.getX()) && getParams().getG().equals(dHPrivateKey.getParams().getG()) && getParams().getP().equals(dHPrivateKey.getParams().getP()) && getParams().getL() == dHPrivateKey.getParams().getL();
    }

    public int hashCode() {
        return ((getX().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getL();
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public void setBagAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getBagAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(aSN1ObjectIdentifier);
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public java.util.Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.dhSpec = new javax.crypto.spec.DHParameterSpec((java.math.BigInteger) objectInputStream.readObject(), (java.math.BigInteger) objectInputStream.readObject(), objectInputStream.readInt());
        this.info = null;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.dhSpec.getP());
        objectOutputStream.writeObject(this.dhSpec.getG());
        objectOutputStream.writeInt(this.dhSpec.getL());
    }
}
