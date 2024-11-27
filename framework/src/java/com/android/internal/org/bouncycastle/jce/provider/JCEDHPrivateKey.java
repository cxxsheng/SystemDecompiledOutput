package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class JCEDHPrivateKey implements javax.crypto.interfaces.DHPrivateKey, com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 311058815616901812L;
    private com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
    private javax.crypto.spec.DHParameterSpec dhSpec;
    private com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo info;
    java.math.BigInteger x;

    protected JCEDHPrivateKey() {
    }

    JCEDHPrivateKey(javax.crypto.interfaces.DHPrivateKey dHPrivateKey) {
        this.x = dHPrivateKey.getX();
        this.dhSpec = dHPrivateKey.getParams();
    }

    JCEDHPrivateKey(javax.crypto.spec.DHPrivateKeySpec dHPrivateKeySpec) {
        this.x = dHPrivateKeySpec.getX();
        this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHPrivateKeySpec.getP(), dHPrivateKeySpec.getG());
    }

    JCEDHPrivateKey(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(privateKeyInfo.getPrivateKeyAlgorithm().getParameters());
        com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(privateKeyInfo.parsePrivateKey());
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
        this.info = privateKeyInfo;
        this.x = aSN1Integer.getValue();
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement)) {
            com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter dHParameter = com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter.getInstance(aSN1Sequence);
            if (dHParameter.getL() != null) {
                this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHParameter.getP(), dHParameter.getG(), dHParameter.getL().intValue());
                return;
            } else {
                this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHParameter.getP(), dHParameter.getG());
                return;
            }
        }
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.dhpublicnumber)) {
            com.android.internal.org.bouncycastle.asn1.x9.DHDomainParameters dHDomainParameters = com.android.internal.org.bouncycastle.asn1.x9.DHDomainParameters.getInstance(aSN1Sequence);
            this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHDomainParameters.getP().getValue(), dHDomainParameters.getG().getValue());
            return;
        }
        throw new java.lang.IllegalArgumentException("unknown algorithm type: " + algorithm);
    }

    JCEDHPrivateKey(com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters dHPrivateKeyParameters) {
        this.x = dHPrivateKeyParameters.getX();
        this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHPrivateKeyParameters.getParameters().getP(), dHPrivateKeyParameters.getParameters().getG(), dHPrivateKeyParameters.getParameters().getL());
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
        try {
            if (this.info != null) {
                return this.info.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            }
            return new com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement, new com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL())), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getX())).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            return null;
        }
    }

    @Override // javax.crypto.interfaces.DHKey
    public javax.crypto.spec.DHParameterSpec getParams() {
        return this.dhSpec;
    }

    @Override // javax.crypto.interfaces.DHPrivateKey
    public java.math.BigInteger getX() {
        return this.x;
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        this.x = (java.math.BigInteger) objectInputStream.readObject();
        this.dhSpec = new javax.crypto.spec.DHParameterSpec((java.math.BigInteger) objectInputStream.readObject(), (java.math.BigInteger) objectInputStream.readObject(), objectInputStream.readInt());
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.writeObject(getX());
        objectOutputStream.writeObject(this.dhSpec.getP());
        objectOutputStream.writeObject(this.dhSpec.getG());
        objectOutputStream.writeInt(this.dhSpec.getL());
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
}
