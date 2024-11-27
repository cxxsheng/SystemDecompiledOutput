package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

/* loaded from: classes4.dex */
public class BCRSAPublicKey implements java.security.interfaces.RSAPublicKey {
    static final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier DEFAULT_ALGORITHM_IDENTIFIER = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    static final long serialVersionUID = 2675817738516720772L;
    private transient com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier;
    private java.math.BigInteger modulus;
    private java.math.BigInteger publicExponent;
    private transient com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters rsaPublicKey;

    BCRSAPublicKey(com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters rSAKeyParameters) {
        this(DEFAULT_ALGORITHM_IDENTIFIER, rSAKeyParameters);
    }

    BCRSAPublicKey(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters rSAKeyParameters) {
        this.algorithmIdentifier = algorithmIdentifier;
        this.modulus = rSAKeyParameters.getModulus();
        this.publicExponent = rSAKeyParameters.getExponent();
        this.rsaPublicKey = rSAKeyParameters;
    }

    BCRSAPublicKey(java.security.spec.RSAPublicKeySpec rSAPublicKeySpec) {
        this.algorithmIdentifier = DEFAULT_ALGORITHM_IDENTIFIER;
        this.modulus = rSAPublicKeySpec.getModulus();
        this.publicExponent = rSAPublicKeySpec.getPublicExponent();
        this.rsaPublicKey = new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(false, this.modulus, this.publicExponent);
    }

    BCRSAPublicKey(java.security.interfaces.RSAPublicKey rSAPublicKey) {
        this.algorithmIdentifier = DEFAULT_ALGORITHM_IDENTIFIER;
        this.modulus = rSAPublicKey.getModulus();
        this.publicExponent = rSAPublicKey.getPublicExponent();
        this.rsaPublicKey = new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(false, this.modulus, this.publicExponent);
    }

    BCRSAPublicKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        populateFromPublicKeyInfo(subjectPublicKeyInfo);
    }

    private void populateFromPublicKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        try {
            com.android.internal.org.bouncycastle.asn1.pkcs.RSAPublicKey rSAPublicKey = com.android.internal.org.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance(subjectPublicKeyInfo.parsePublicKey());
            this.algorithmIdentifier = subjectPublicKeyInfo.getAlgorithm();
            this.modulus = rSAPublicKey.getModulus();
            this.publicExponent = rSAPublicKey.getPublicExponent();
            this.rsaPublicKey = new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(false, this.modulus, this.publicExponent);
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("invalid info structure in RSA public key");
        }
    }

    @Override // java.security.interfaces.RSAKey
    public java.math.BigInteger getModulus() {
        return this.modulus;
    }

    @Override // java.security.interfaces.RSAPublicKey
    public java.math.BigInteger getPublicExponent() {
        return this.publicExponent;
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
        return "X.509";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil.getEncodedSubjectPublicKeyInfo(this.algorithmIdentifier, new com.android.internal.org.bouncycastle.asn1.pkcs.RSAPublicKey(getModulus(), getPublicExponent()));
    }

    com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters engineGetKeyParameters() {
        return this.rsaPublicKey;
    }

    public int hashCode() {
        return getModulus().hashCode() ^ getPublicExponent().hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof java.security.interfaces.RSAPublicKey)) {
            return false;
        }
        java.security.interfaces.RSAPublicKey rSAPublicKey = (java.security.interfaces.RSAPublicKey) obj;
        return getModulus().equals(rSAPublicKey.getModulus()) && getPublicExponent().equals(rSAPublicKey.getPublicExponent());
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append("RSA Public Key [").append(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil.generateKeyFingerprint(getModulus())).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).append(",[").append(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil.generateExponentFingerprint(getPublicExponent())).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).append(lineSeparator);
        stringBuffer.append("        modulus: ").append(getModulus().toString(16)).append(lineSeparator);
        stringBuffer.append("public exponent: ").append(getPublicExponent().toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        objectInputStream.defaultReadObject();
        try {
            this.algorithmIdentifier = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(objectInputStream.readObject());
        } catch (java.lang.Exception e) {
            this.algorithmIdentifier = DEFAULT_ALGORITHM_IDENTIFIER;
        }
        this.rsaPublicKey = new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(false, this.modulus, this.publicExponent);
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
        if (!this.algorithmIdentifier.equals(DEFAULT_ALGORITHM_IDENTIFIER)) {
            objectOutputStream.writeObject(this.algorithmIdentifier.getEncoded());
        }
    }
}
