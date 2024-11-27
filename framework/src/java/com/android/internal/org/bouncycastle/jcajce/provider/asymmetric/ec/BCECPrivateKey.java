package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec;

/* loaded from: classes4.dex */
public class BCECPrivateKey implements java.security.interfaces.ECPrivateKey, com.android.internal.org.bouncycastle.jce.interfaces.ECPrivateKey, com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier, com.android.internal.org.bouncycastle.jce.interfaces.ECPointEncoder {
    static final long serialVersionUID = 994553197664784084L;
    private java.lang.String algorithm;
    private transient com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl attrCarrier;
    private transient com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration configuration;
    private transient java.math.BigInteger d;
    private transient java.security.spec.ECParameterSpec ecSpec;
    private transient com.android.internal.org.bouncycastle.asn1.DERBitString publicKey;
    private boolean withCompression;

    protected BCECPrivateKey() {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
    }

    public BCECPrivateKey(java.security.interfaces.ECPrivateKey eCPrivateKey, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.d = eCPrivateKey.getS();
        this.algorithm = eCPrivateKey.getAlgorithm();
        this.ecSpec = eCPrivateKey.getParams();
        this.configuration = providerConfiguration;
    }

    public BCECPrivateKey(java.lang.String str, com.android.internal.org.bouncycastle.jce.spec.ECPrivateKeySpec eCPrivateKeySpec, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = eCPrivateKeySpec.getD();
        if (eCPrivateKeySpec.getParams() != null) {
            this.ecSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams());
        } else {
            this.ecSpec = null;
        }
        this.configuration = providerConfiguration;
    }

    public BCECPrivateKey(java.lang.String str, java.security.spec.ECPrivateKeySpec eCPrivateKeySpec, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = eCPrivateKeySpec.getS();
        this.ecSpec = eCPrivateKeySpec.getParams();
        this.configuration = providerConfiguration;
    }

    public BCECPrivateKey(java.lang.String str, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey bCECPrivateKey) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = bCECPrivateKey.d;
        this.ecSpec = bCECPrivateKey.ecSpec;
        this.withCompression = bCECPrivateKey.withCompression;
        this.attrCarrier = bCECPrivateKey.attrCarrier;
        this.publicKey = bCECPrivateKey.publicKey;
        this.configuration = bCECPrivateKey.configuration;
    }

    public BCECPrivateKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters eCPrivateKeyParameters, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey bCECPublicKey, java.security.spec.ECParameterSpec eCParameterSpec, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = eCPrivateKeyParameters.getD();
        this.configuration = providerConfiguration;
        if (eCParameterSpec == null) {
            com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
            this.ecSpec = new java.security.spec.ECParameterSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.ecSpec = eCParameterSpec;
        }
        this.publicKey = getPublicKeyDetails(bCECPublicKey);
    }

    public BCECPrivateKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters eCPrivateKeyParameters, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey bCECPublicKey, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = eCPrivateKeyParameters.getD();
        this.configuration = providerConfiguration;
        if (eCParameterSpec == null) {
            com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
            this.ecSpec = new java.security.spec.ECParameterSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.ecSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
        }
        try {
            this.publicKey = getPublicKeyDetails(bCECPublicKey);
        } catch (java.lang.Exception e) {
            this.publicKey = null;
        }
    }

    public BCECPrivateKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters eCPrivateKeyParameters, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = eCPrivateKeyParameters.getD();
        this.ecSpec = null;
        this.configuration = providerConfiguration;
    }

    BCECPrivateKey(java.lang.String str, com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) throws java.io.IOException {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.configuration = providerConfiguration;
        populateFromPrivKeyInfo(privateKeyInfo);
    }

    private void populateFromPrivKeyInfo(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters = com.android.internal.org.bouncycastle.asn1.x9.X962Parameters.getInstance(privateKeyInfo.getPrivateKeyAlgorithm().getParameters());
        this.ecSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertToSpec(x962Parameters, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.getCurve(this.configuration, x962Parameters));
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable parsePrivateKey = privateKeyInfo.parsePrivateKey();
        if (parsePrivateKey instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer) {
            this.d = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(parsePrivateKey).getValue();
            return;
        }
        com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKey eCPrivateKey = com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKey.getInstance(parsePrivateKey);
        this.d = eCPrivateKey.getKey();
        this.publicKey = eCPrivateKey.getPublicKey();
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        return this.algorithm;
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return "PKCS#8";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        int orderBitLength;
        com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKey eCPrivateKey;
        com.android.internal.org.bouncycastle.asn1.x9.X962Parameters domainParametersFromName = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils.getDomainParametersFromName(this.ecSpec, this.withCompression);
        if (this.ecSpec == null) {
            orderBitLength = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getOrderBitLength(this.configuration, null, getS());
        } else {
            orderBitLength = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getOrderBitLength(this.configuration, this.ecSpec.getOrder(), getS());
        }
        if (this.publicKey != null) {
            eCPrivateKey = new com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), this.publicKey, domainParametersFromName);
        } else {
            eCPrivateKey = new com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), domainParametersFromName);
        }
        try {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey, domainParametersFromName), eCPrivateKey).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            return null;
        }
    }

    @Override // java.security.interfaces.ECKey
    public java.security.spec.ECParameterSpec getParams() {
        return this.ecSpec;
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.ECKey
    public com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec getParameters() {
        if (this.ecSpec == null) {
            return null;
        }
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(this.ecSpec);
    }

    com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        if (this.ecSpec != null) {
            return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(this.ecSpec);
        }
        return this.configuration.getEcImplicitlyCa();
    }

    @Override // java.security.interfaces.ECPrivateKey
    public java.math.BigInteger getS() {
        return this.d;
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.ECPrivateKey
    public java.math.BigInteger getD() {
        return this.d;
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

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.ECPointEncoder
    public void setPointFormat(java.lang.String str) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey)) {
            return false;
        }
        com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey bCECPrivateKey = (com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey) obj;
        return getD().equals(bCECPrivateKey.getD()) && engineGetSpec().equals(bCECPrivateKey.engineGetSpec());
    }

    public int hashCode() {
        return getD().hashCode() ^ engineGetSpec().hashCode();
    }

    public java.lang.String toString() {
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.privateKeyToString(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC, this.d, engineGetSpec());
    }

    private com.android.internal.org.bouncycastle.asn1.DERBitString getPublicKeyDetails(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey bCECPublicKey) {
        try {
            return com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bCECPublicKey.getEncoded())).getPublicKeyData();
        } catch (java.io.IOException e) {
            return null;
        }
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        objectInputStream.defaultReadObject();
        byte[] bArr = (byte[]) objectInputStream.readObject();
        this.configuration = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION;
        populateFromPrivKeyInfo(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bArr)));
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }
}
