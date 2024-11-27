package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

/* loaded from: classes4.dex */
public class BCRSAPrivateKey implements java.security.interfaces.RSAPrivateKey, com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier {
    private static java.math.BigInteger ZERO = java.math.BigInteger.valueOf(0);
    static final long serialVersionUID = 5110188922551353628L;
    protected transient com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier;
    private byte[] algorithmIdentifierEnc;
    protected transient com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl attrCarrier;
    protected java.math.BigInteger modulus;
    protected java.math.BigInteger privateExponent;
    protected transient com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters rsaPrivateKey;

    BCRSAPrivateKey(com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters rSAKeyParameters) {
        this.algorithmIdentifierEnc = getEncoding(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey.DEFAULT_ALGORITHM_IDENTIFIER);
        this.algorithmIdentifier = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey.DEFAULT_ALGORITHM_IDENTIFIER;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.modulus = rSAKeyParameters.getModulus();
        this.privateExponent = rSAKeyParameters.getExponent();
        this.rsaPrivateKey = rSAKeyParameters;
    }

    BCRSAPrivateKey(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters rSAKeyParameters) {
        this.algorithmIdentifierEnc = getEncoding(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey.DEFAULT_ALGORITHM_IDENTIFIER);
        this.algorithmIdentifier = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey.DEFAULT_ALGORITHM_IDENTIFIER;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithmIdentifier = algorithmIdentifier;
        this.algorithmIdentifierEnc = getEncoding(algorithmIdentifier);
        this.modulus = rSAKeyParameters.getModulus();
        this.privateExponent = rSAKeyParameters.getExponent();
        this.rsaPrivateKey = rSAKeyParameters;
    }

    BCRSAPrivateKey(java.security.spec.RSAPrivateKeySpec rSAPrivateKeySpec) {
        this.algorithmIdentifierEnc = getEncoding(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey.DEFAULT_ALGORITHM_IDENTIFIER);
        this.algorithmIdentifier = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey.DEFAULT_ALGORITHM_IDENTIFIER;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.modulus = rSAPrivateKeySpec.getModulus();
        this.privateExponent = rSAPrivateKeySpec.getPrivateExponent();
        this.rsaPrivateKey = new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(true, this.modulus, this.privateExponent);
    }

    BCRSAPrivateKey(java.security.interfaces.RSAPrivateKey rSAPrivateKey) {
        this.algorithmIdentifierEnc = getEncoding(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey.DEFAULT_ALGORITHM_IDENTIFIER);
        this.algorithmIdentifier = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey.DEFAULT_ALGORITHM_IDENTIFIER;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.modulus = rSAPrivateKey.getModulus();
        this.privateExponent = rSAPrivateKey.getPrivateExponent();
        this.rsaPrivateKey = new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(true, this.modulus, this.privateExponent);
    }

    BCRSAPrivateKey(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKey rSAPrivateKey) {
        this.algorithmIdentifierEnc = getEncoding(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey.DEFAULT_ALGORITHM_IDENTIFIER);
        this.algorithmIdentifier = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey.DEFAULT_ALGORITHM_IDENTIFIER;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithmIdentifier = algorithmIdentifier;
        this.algorithmIdentifierEnc = getEncoding(algorithmIdentifier);
        this.modulus = rSAPrivateKey.getModulus();
        this.privateExponent = rSAPrivateKey.getPrivateExponent();
        this.rsaPrivateKey = new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(true, this.modulus, this.privateExponent);
    }

    @Override // java.security.interfaces.RSAKey
    public java.math.BigInteger getModulus() {
        return this.modulus;
    }

    @Override // java.security.interfaces.RSAPrivateKey
    public java.math.BigInteger getPrivateExponent() {
        return this.privateExponent;
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        if (this.algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            return "RSASSA-PSS";
        }
        return android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA;
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return "PKCS#8";
    }

    com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters engineGetKeyParameters() {
        return this.rsaPrivateKey;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil.getEncodedPrivateKeyInfo(this.algorithmIdentifier, new com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKey(getModulus(), ZERO, getPrivateExponent(), ZERO, ZERO, ZERO, ZERO, ZERO));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof java.security.interfaces.RSAPrivateKey)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        java.security.interfaces.RSAPrivateKey rSAPrivateKey = (java.security.interfaces.RSAPrivateKey) obj;
        return getModulus().equals(rSAPrivateKey.getModulus()) && getPrivateExponent().equals(rSAPrivateKey.getPrivateExponent());
    }

    public int hashCode() {
        return getModulus().hashCode() ^ getPrivateExponent().hashCode();
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
        if (this.algorithmIdentifierEnc == null) {
            this.algorithmIdentifierEnc = getEncoding(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey.DEFAULT_ALGORITHM_IDENTIFIER);
        }
        this.algorithmIdentifier = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(this.algorithmIdentifierEnc);
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.rsaPrivateKey = new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(true, this.modulus, this.privateExponent);
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append("RSA Private Key [").append(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil.generateKeyFingerprint(getModulus())).append("],[]").append(lineSeparator);
        stringBuffer.append("            modulus: ").append(getModulus().toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    private static byte[] getEncoding(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        try {
            return algorithmIdentifier.getEncoded();
        } catch (java.io.IOException e) {
            return null;
        }
    }
}
