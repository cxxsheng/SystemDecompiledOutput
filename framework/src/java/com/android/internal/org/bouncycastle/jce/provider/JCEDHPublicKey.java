package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class JCEDHPublicKey implements javax.crypto.interfaces.DHPublicKey {
    static final long serialVersionUID = -216691575254424324L;
    private javax.crypto.spec.DHParameterSpec dhSpec;
    private com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo info;
    private java.math.BigInteger y;

    JCEDHPublicKey(javax.crypto.spec.DHPublicKeySpec dHPublicKeySpec) {
        this.y = dHPublicKeySpec.getY();
        this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHPublicKeySpec.getP(), dHPublicKeySpec.getG());
    }

    JCEDHPublicKey(javax.crypto.interfaces.DHPublicKey dHPublicKey) {
        this.y = dHPublicKey.getY();
        this.dhSpec = dHPublicKey.getParams();
    }

    JCEDHPublicKey(com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters dHPublicKeyParameters) {
        this.y = dHPublicKeyParameters.getY();
        this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHPublicKeyParameters.getParameters().getP(), dHPublicKeyParameters.getParameters().getG(), dHPublicKeyParameters.getParameters().getL());
    }

    JCEDHPublicKey(java.math.BigInteger bigInteger, javax.crypto.spec.DHParameterSpec dHParameterSpec) {
        this.y = bigInteger;
        this.dhSpec = dHParameterSpec;
    }

    JCEDHPublicKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.info = subjectPublicKeyInfo;
        try {
            this.y = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) subjectPublicKeyInfo.parsePublicKey()).getValue();
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(subjectPublicKeyInfo.getAlgorithmId().getParameters());
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = subjectPublicKeyInfo.getAlgorithmId().getAlgorithm();
            if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement) || isPKCSParam(aSN1Sequence)) {
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
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("invalid info structure in DH public key");
        }
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        return "DH";
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return "X.509";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        if (this.info != null) {
            return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil.getEncodedSubjectPublicKeyInfo(this.info);
        }
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil.getEncodedSubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement, new com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL())), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.y));
    }

    @Override // javax.crypto.interfaces.DHKey
    public javax.crypto.spec.DHParameterSpec getParams() {
        return this.dhSpec;
    }

    @Override // javax.crypto.interfaces.DHPublicKey
    public java.math.BigInteger getY() {
        return this.y;
    }

    private boolean isPKCSParam(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 2) {
            return true;
        }
        if (aSN1Sequence.size() > 3) {
            return false;
        }
        return com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(2)).getValue().compareTo(java.math.BigInteger.valueOf((long) com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).getValue().bitLength())) <= 0;
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        this.y = (java.math.BigInteger) objectInputStream.readObject();
        this.dhSpec = new javax.crypto.spec.DHParameterSpec((java.math.BigInteger) objectInputStream.readObject(), (java.math.BigInteger) objectInputStream.readObject(), objectInputStream.readInt());
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.writeObject(getY());
        objectOutputStream.writeObject(this.dhSpec.getP());
        objectOutputStream.writeObject(this.dhSpec.getG());
        objectOutputStream.writeInt(this.dhSpec.getL());
    }
}
