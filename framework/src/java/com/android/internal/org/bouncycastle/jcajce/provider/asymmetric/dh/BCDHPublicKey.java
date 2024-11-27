package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh;

/* loaded from: classes4.dex */
public class BCDHPublicKey implements javax.crypto.interfaces.DHPublicKey {
    static final long serialVersionUID = -216691575254424324L;
    private transient com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters dhPublicKey;
    private transient javax.crypto.spec.DHParameterSpec dhSpec;
    private transient com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo info;
    private java.math.BigInteger y;

    BCDHPublicKey(javax.crypto.spec.DHPublicKeySpec dHPublicKeySpec) {
        this.y = dHPublicKeySpec.getY();
        if (dHPublicKeySpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.DHExtendedPublicKeySpec) {
            this.dhSpec = ((com.android.internal.org.bouncycastle.jcajce.spec.DHExtendedPublicKeySpec) dHPublicKeySpec).getParams();
        } else {
            this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHPublicKeySpec.getP(), dHPublicKeySpec.getG());
        }
        if (this.dhSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) {
            this.dhPublicKey = new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(this.y, ((com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) this.dhSpec).getDomainParameters());
        } else {
            this.dhPublicKey = new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(this.y, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(dHPublicKeySpec.getP(), dHPublicKeySpec.getG()));
        }
    }

    BCDHPublicKey(javax.crypto.interfaces.DHPublicKey dHPublicKey) {
        this.y = dHPublicKey.getY();
        this.dhSpec = dHPublicKey.getParams();
        if (this.dhSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) {
            this.dhPublicKey = new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(this.y, ((com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) this.dhSpec).getDomainParameters());
        } else {
            this.dhPublicKey = new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(this.y, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
        }
    }

    BCDHPublicKey(com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters dHPublicKeyParameters) {
        this.y = dHPublicKeyParameters.getY();
        this.dhSpec = new com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec(dHPublicKeyParameters.getParameters());
        this.dhPublicKey = dHPublicKeyParameters;
    }

    BCDHPublicKey(java.math.BigInteger bigInteger, javax.crypto.spec.DHParameterSpec dHParameterSpec) {
        this.y = bigInteger;
        this.dhSpec = dHParameterSpec;
        if (dHParameterSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) {
            this.dhPublicKey = new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(bigInteger, ((com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) dHParameterSpec).getDomainParameters());
        } else {
            this.dhPublicKey = new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(bigInteger, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(dHParameterSpec.getP(), dHParameterSpec.getG()));
        }
    }

    public BCDHPublicKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.info = subjectPublicKeyInfo;
        try {
            this.y = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) subjectPublicKeyInfo.parsePublicKey()).getValue();
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = subjectPublicKeyInfo.getAlgorithm().getAlgorithm();
            if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement) || isPKCSParam(aSN1Sequence)) {
                com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter dHParameter = com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter.getInstance(aSN1Sequence);
                if (dHParameter.getL() != null) {
                    this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHParameter.getP(), dHParameter.getG(), dHParameter.getL().intValue());
                    this.dhPublicKey = new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(this.y, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(this.dhSpec.getP(), this.dhSpec.getG(), null, this.dhSpec.getL()));
                    return;
                } else {
                    this.dhSpec = new javax.crypto.spec.DHParameterSpec(dHParameter.getP(), dHParameter.getG());
                    this.dhPublicKey = new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(this.y, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
                    return;
                }
            }
            if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.dhpublicnumber)) {
                com.android.internal.org.bouncycastle.asn1.x9.DomainParameters domainParameters = com.android.internal.org.bouncycastle.asn1.x9.DomainParameters.getInstance(aSN1Sequence);
                com.android.internal.org.bouncycastle.asn1.x9.ValidationParams validationParams = domainParameters.getValidationParams();
                if (validationParams != null) {
                    this.dhPublicKey = new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(this.y, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(domainParameters.getP(), domainParameters.getG(), domainParameters.getQ(), domainParameters.getJ(), new com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters(validationParams.getSeed(), validationParams.getPgenCounter().intValue())));
                } else {
                    this.dhPublicKey = new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(this.y, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(domainParameters.getP(), domainParameters.getG(), domainParameters.getQ(), domainParameters.getJ(), (com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters) null));
                }
                this.dhSpec = new com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec(this.dhPublicKey.getParameters());
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
        com.android.internal.org.bouncycastle.asn1.x9.ValidationParams validationParams;
        if (this.info != null) {
            return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil.getEncodedSubjectPublicKeyInfo(this.info);
        }
        if ((this.dhSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) && ((com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) this.dhSpec).getQ() != null) {
            com.android.internal.org.bouncycastle.crypto.params.DHParameters domainParameters = ((com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) this.dhSpec).getDomainParameters();
            com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters validationParameters = domainParameters.getValidationParameters();
            if (validationParameters == null) {
                validationParams = null;
            } else {
                validationParams = new com.android.internal.org.bouncycastle.asn1.x9.ValidationParams(validationParameters.getSeed(), validationParameters.getCounter());
            }
            return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil.getEncodedSubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.dhpublicnumber, new com.android.internal.org.bouncycastle.asn1.x9.DomainParameters(domainParameters.getP(), domainParameters.getG(), domainParameters.getQ(), domainParameters.getJ(), validationParams).toASN1Primitive()), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.y));
        }
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil.getEncodedSubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement, new com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL()).toASN1Primitive()), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.y));
    }

    public java.lang.String toString() {
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.DHUtil.publicKeyToString("DH", this.y, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
    }

    @Override // javax.crypto.interfaces.DHKey
    public javax.crypto.spec.DHParameterSpec getParams() {
        return this.dhSpec;
    }

    @Override // javax.crypto.interfaces.DHPublicKey
    public java.math.BigInteger getY() {
        return this.y;
    }

    public com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters engineGetKeyParameters() {
        return this.dhPublicKey;
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

    public int hashCode() {
        return ((getY().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getL();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof javax.crypto.interfaces.DHPublicKey)) {
            return false;
        }
        javax.crypto.interfaces.DHPublicKey dHPublicKey = (javax.crypto.interfaces.DHPublicKey) obj;
        return getY().equals(dHPublicKey.getY()) && getParams().getG().equals(dHPublicKey.getParams().getG()) && getParams().getP().equals(dHPublicKey.getParams().getP()) && getParams().getL() == dHPublicKey.getParams().getL();
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.dhSpec = new javax.crypto.spec.DHParameterSpec((java.math.BigInteger) objectInputStream.readObject(), (java.math.BigInteger) objectInputStream.readObject(), objectInputStream.readInt());
        this.info = null;
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.dhSpec.getP());
        objectOutputStream.writeObject(this.dhSpec.getG());
        objectOutputStream.writeInt(this.dhSpec.getL());
    }
}
