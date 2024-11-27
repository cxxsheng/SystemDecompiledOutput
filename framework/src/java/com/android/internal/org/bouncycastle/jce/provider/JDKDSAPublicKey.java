package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class JDKDSAPublicKey implements java.security.interfaces.DSAPublicKey {
    private static final long serialVersionUID = 1752452449903495175L;
    private java.security.interfaces.DSAParams dsaSpec;
    private java.math.BigInteger y;

    JDKDSAPublicKey(java.security.spec.DSAPublicKeySpec dSAPublicKeySpec) {
        this.y = dSAPublicKeySpec.getY();
        this.dsaSpec = new java.security.spec.DSAParameterSpec(dSAPublicKeySpec.getP(), dSAPublicKeySpec.getQ(), dSAPublicKeySpec.getG());
    }

    JDKDSAPublicKey(java.security.interfaces.DSAPublicKey dSAPublicKey) {
        this.y = dSAPublicKey.getY();
        this.dsaSpec = dSAPublicKey.getParams();
    }

    JDKDSAPublicKey(com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters dSAPublicKeyParameters) {
        this.y = dSAPublicKeyParameters.getY();
        this.dsaSpec = new java.security.spec.DSAParameterSpec(dSAPublicKeyParameters.getParameters().getP(), dSAPublicKeyParameters.getParameters().getQ(), dSAPublicKeyParameters.getParameters().getG());
    }

    JDKDSAPublicKey(java.math.BigInteger bigInteger, java.security.spec.DSAParameterSpec dSAParameterSpec) {
        this.y = bigInteger;
        this.dsaSpec = dSAParameterSpec;
    }

    JDKDSAPublicKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        try {
            this.y = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) subjectPublicKeyInfo.parsePublicKey()).getValue();
            if (isNotNull(subjectPublicKeyInfo.getAlgorithm().getParameters())) {
                com.android.internal.org.bouncycastle.asn1.x509.DSAParameter dSAParameter = com.android.internal.org.bouncycastle.asn1.x509.DSAParameter.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
                this.dsaSpec = new java.security.spec.DSAParameterSpec(dSAParameter.getP(), dSAParameter.getQ(), dSAParameter.getG());
            }
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("invalid info structure in DSA public key");
        }
    }

    private boolean isNotNull(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        return (aSN1Encodable == null || com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE.equals(aSN1Encodable)) ? false : true;
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        return "DSA";
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return "X.509";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        try {
            if (this.dsaSpec == null) {
                return new com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.y)).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            }
            return new com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa, new com.android.internal.org.bouncycastle.asn1.x509.DSAParameter(this.dsaSpec.getP(), this.dsaSpec.getQ(), this.dsaSpec.getG())), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.y)).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            return null;
        }
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
        stringBuffer.append("DSA Public Key").append(lineSeparator);
        stringBuffer.append("            y: ").append(getY().toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    public int hashCode() {
        return ((getY().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getQ().hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof java.security.interfaces.DSAPublicKey)) {
            return false;
        }
        java.security.interfaces.DSAPublicKey dSAPublicKey = (java.security.interfaces.DSAPublicKey) obj;
        return getY().equals(dSAPublicKey.getY()) && getParams().getG().equals(dSAPublicKey.getParams().getG()) && getParams().getP().equals(dSAPublicKey.getParams().getP()) && getParams().getQ().equals(dSAPublicKey.getParams().getQ());
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        this.y = (java.math.BigInteger) objectInputStream.readObject();
        this.dsaSpec = new java.security.spec.DSAParameterSpec((java.math.BigInteger) objectInputStream.readObject(), (java.math.BigInteger) objectInputStream.readObject(), (java.math.BigInteger) objectInputStream.readObject());
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.writeObject(this.y);
        objectOutputStream.writeObject(this.dsaSpec.getP());
        objectOutputStream.writeObject(this.dsaSpec.getQ());
        objectOutputStream.writeObject(this.dsaSpec.getG());
    }
}
