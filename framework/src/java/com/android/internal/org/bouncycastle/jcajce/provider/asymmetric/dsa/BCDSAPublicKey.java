package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa;

/* loaded from: classes4.dex */
public class BCDSAPublicKey implements java.security.interfaces.DSAPublicKey {
    private static java.math.BigInteger ZERO = java.math.BigInteger.valueOf(0);
    private static final long serialVersionUID = 1752452449903495175L;
    private transient java.security.interfaces.DSAParams dsaSpec;
    private transient com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters lwKeyParams;
    private java.math.BigInteger y;

    BCDSAPublicKey(java.security.spec.DSAPublicKeySpec dSAPublicKeySpec) {
        this.y = dSAPublicKeySpec.getY();
        this.dsaSpec = new java.security.spec.DSAParameterSpec(dSAPublicKeySpec.getP(), dSAPublicKeySpec.getQ(), dSAPublicKeySpec.getG());
        this.lwKeyParams = new com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters(this.y, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.toDSAParameters(this.dsaSpec));
    }

    BCDSAPublicKey(java.security.interfaces.DSAPublicKey dSAPublicKey) {
        this.y = dSAPublicKey.getY();
        this.dsaSpec = dSAPublicKey.getParams();
        this.lwKeyParams = new com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters(this.y, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.toDSAParameters(this.dsaSpec));
    }

    BCDSAPublicKey(com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters dSAPublicKeyParameters) {
        this.y = dSAPublicKeyParameters.getY();
        if (dSAPublicKeyParameters.getParameters() != null) {
            this.dsaSpec = new java.security.spec.DSAParameterSpec(dSAPublicKeyParameters.getParameters().getP(), dSAPublicKeyParameters.getParameters().getQ(), dSAPublicKeyParameters.getParameters().getG());
        } else {
            this.dsaSpec = null;
        }
        this.lwKeyParams = dSAPublicKeyParameters;
    }

    public BCDSAPublicKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        try {
            this.y = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) subjectPublicKeyInfo.parsePublicKey()).getValue();
            if (isNotNull(subjectPublicKeyInfo.getAlgorithm().getParameters())) {
                com.android.internal.org.bouncycastle.asn1.x509.DSAParameter dSAParameter = com.android.internal.org.bouncycastle.asn1.x509.DSAParameter.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
                this.dsaSpec = new java.security.spec.DSAParameterSpec(dSAParameter.getP(), dSAParameter.getQ(), dSAParameter.getG());
            } else {
                this.dsaSpec = null;
            }
            this.lwKeyParams = new com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters(this.y, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.toDSAParameters(this.dsaSpec));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("invalid info structure in DSA public key");
        }
    }

    private boolean isNotNull(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        return (aSN1Encodable == null || com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE.equals(aSN1Encodable.toASN1Primitive())) ? false : true;
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        return "DSA";
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return "X.509";
    }

    com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters engineGetKeyParameters() {
        return this.lwKeyParams;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        if (this.dsaSpec == null) {
            return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil.getEncodedSubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.y));
        }
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil.getEncodedSubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa, new com.android.internal.org.bouncycastle.asn1.x509.DSAParameter(this.dsaSpec.getP(), this.dsaSpec.getQ(), this.dsaSpec.getG()).toASN1Primitive()), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.y));
    }

    @Override // java.security.interfaces.DSAKey
    public java.security.interfaces.DSAParams getParams() {
        return this.dsaSpec;
    }

    @Override // java.security.interfaces.DSAPublicKey
    public java.math.BigInteger getY() {
        return this.y;
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append("DSA Public Key [").append(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.generateKeyFingerprint(this.y, getParams())).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).append(lineSeparator);
        stringBuffer.append("            Y: ").append(getY().toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    public int hashCode() {
        if (this.dsaSpec != null) {
            return ((getY().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getQ().hashCode();
        }
        return getY().hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof java.security.interfaces.DSAPublicKey)) {
            return false;
        }
        java.security.interfaces.DSAPublicKey dSAPublicKey = (java.security.interfaces.DSAPublicKey) obj;
        return this.dsaSpec != null ? getY().equals(dSAPublicKey.getY()) && dSAPublicKey.getParams() != null && getParams().getG().equals(dSAPublicKey.getParams().getG()) && getParams().getP().equals(dSAPublicKey.getParams().getP()) && getParams().getQ().equals(dSAPublicKey.getParams().getQ()) : getY().equals(dSAPublicKey.getY()) && dSAPublicKey.getParams() == null;
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        objectInputStream.defaultReadObject();
        java.math.BigInteger bigInteger = (java.math.BigInteger) objectInputStream.readObject();
        if (bigInteger.equals(ZERO)) {
            this.dsaSpec = null;
        } else {
            this.dsaSpec = new java.security.spec.DSAParameterSpec(bigInteger, (java.math.BigInteger) objectInputStream.readObject(), (java.math.BigInteger) objectInputStream.readObject());
        }
        this.lwKeyParams = new com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters(this.y, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.toDSAParameters(this.dsaSpec));
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
        if (this.dsaSpec == null) {
            objectOutputStream.writeObject(ZERO);
            return;
        }
        objectOutputStream.writeObject(this.dsaSpec.getP());
        objectOutputStream.writeObject(this.dsaSpec.getQ());
        objectOutputStream.writeObject(this.dsaSpec.getG());
    }
}
