package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class JCEECPrivateKey implements java.security.interfaces.ECPrivateKey, com.android.internal.org.bouncycastle.jce.interfaces.ECPrivateKey, com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier, com.android.internal.org.bouncycastle.jce.interfaces.ECPointEncoder {
    private java.lang.String algorithm;
    private com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl attrCarrier;
    private java.math.BigInteger d;
    private java.security.spec.ECParameterSpec ecSpec;
    private com.android.internal.org.bouncycastle.asn1.DERBitString publicKey;
    private boolean withCompression;

    protected JCEECPrivateKey() {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
    }

    public JCEECPrivateKey(java.security.interfaces.ECPrivateKey eCPrivateKey) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.d = eCPrivateKey.getS();
        this.algorithm = eCPrivateKey.getAlgorithm();
        this.ecSpec = eCPrivateKey.getParams();
    }

    public JCEECPrivateKey(java.lang.String str, com.android.internal.org.bouncycastle.jce.spec.ECPrivateKeySpec eCPrivateKeySpec) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = eCPrivateKeySpec.getD();
        if (eCPrivateKeySpec.getParams() != null) {
            this.ecSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams());
        } else {
            this.ecSpec = null;
        }
    }

    public JCEECPrivateKey(java.lang.String str, java.security.spec.ECPrivateKeySpec eCPrivateKeySpec) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = eCPrivateKeySpec.getS();
        this.ecSpec = eCPrivateKeySpec.getParams();
    }

    public JCEECPrivateKey(java.lang.String str, com.android.internal.org.bouncycastle.jce.provider.JCEECPrivateKey jCEECPrivateKey) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = jCEECPrivateKey.d;
        this.ecSpec = jCEECPrivateKey.ecSpec;
        this.withCompression = jCEECPrivateKey.withCompression;
        this.attrCarrier = jCEECPrivateKey.attrCarrier;
        this.publicKey = jCEECPrivateKey.publicKey;
    }

    public JCEECPrivateKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters eCPrivateKeyParameters, com.android.internal.org.bouncycastle.jce.provider.JCEECPublicKey jCEECPublicKey, java.security.spec.ECParameterSpec eCParameterSpec) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
            this.ecSpec = new java.security.spec.ECParameterSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.ecSpec = eCParameterSpec;
        }
        this.publicKey = getPublicKeyDetails(jCEECPublicKey);
    }

    public JCEECPrivateKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters eCPrivateKeyParameters, com.android.internal.org.bouncycastle.jce.provider.JCEECPublicKey jCEECPublicKey, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
            this.ecSpec = new java.security.spec.ECParameterSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.ecSpec = new java.security.spec.ECParameterSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(eCParameterSpec.getG()), eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
        }
        this.publicKey = getPublicKeyDetails(jCEECPublicKey);
    }

    public JCEECPrivateKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters eCPrivateKeyParameters) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.d = eCPrivateKeyParameters.getD();
        this.ecSpec = null;
    }

    JCEECPrivateKey(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        populateFromPrivKeyInfo(privateKeyInfo);
    }

    private void populateFromPrivKeyInfo(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters = com.android.internal.org.bouncycastle.asn1.x9.X962Parameters.getInstance(privateKeyInfo.getPrivateKeyAlgorithm().getParameters());
        if (x962Parameters.isNamedCurve()) {
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(x962Parameters.getParameters());
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters namedCurveByOid = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
            this.ecSpec = new com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getCurveName(aSN1ObjectIdentifier), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(namedCurveByOid.getCurve(), namedCurveByOid.getSeed()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(namedCurveByOid.getG()), namedCurveByOid.getN(), namedCurveByOid.getH());
        } else if (x962Parameters.isImplicitlyCA()) {
            this.ecSpec = null;
        } else {
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters x9ECParameters = com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters.getInstance(x962Parameters.getParameters());
            this.ecSpec = new java.security.spec.ECParameterSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(x9ECParameters.getCurve(), x9ECParameters.getSeed()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), x9ECParameters.getH().intValue());
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable parsePrivateKey = privateKeyInfo.parsePrivateKey();
        if (parsePrivateKey instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer) {
            this.d = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(parsePrivateKey).getValue();
            return;
        }
        com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKeyStructure eCPrivateKeyStructure = new com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKeyStructure((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) parsePrivateKey);
        this.d = eCPrivateKeyStructure.getKey();
        this.publicKey = eCPrivateKeyStructure.getPublicKey();
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
        com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters;
        com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKeyStructure eCPrivateKeyStructure;
        if (this.ecSpec instanceof com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec) {
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier namedCurveOid = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveOid(((com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec) this.ecSpec).getName());
            if (namedCurveOid == null) {
                namedCurveOid = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(((com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec) this.ecSpec).getName());
            }
            x962Parameters = new com.android.internal.org.bouncycastle.asn1.x9.X962Parameters(namedCurveOid);
        } else if (this.ecSpec == null) {
            x962Parameters = new com.android.internal.org.bouncycastle.asn1.x9.X962Parameters((com.android.internal.org.bouncycastle.asn1.ASN1Null) com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
        } else {
            com.android.internal.org.bouncycastle.math.ec.ECCurve convertCurve = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(this.ecSpec.getCurve());
            x962Parameters = new com.android.internal.org.bouncycastle.asn1.x9.X962Parameters(new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(convertCurve, new com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(convertCurve, this.ecSpec.getGenerator()), this.withCompression), this.ecSpec.getOrder(), java.math.BigInteger.valueOf(this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
        }
        if (this.publicKey != null) {
            eCPrivateKeyStructure = new com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKeyStructure(getS(), this.publicKey, x962Parameters);
        } else {
            eCPrivateKeyStructure = new com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKeyStructure(getS(), x962Parameters);
        }
        try {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey, x962Parameters.toASN1Primitive()), eCPrivateKeyStructure.toASN1Primitive()).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
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
        return com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
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
        if (!(obj instanceof com.android.internal.org.bouncycastle.jce.provider.JCEECPrivateKey)) {
            return false;
        }
        com.android.internal.org.bouncycastle.jce.provider.JCEECPrivateKey jCEECPrivateKey = (com.android.internal.org.bouncycastle.jce.provider.JCEECPrivateKey) obj;
        return getD().equals(jCEECPrivateKey.getD()) && engineGetSpec().equals(jCEECPrivateKey.engineGetSpec());
    }

    public int hashCode() {
        return getD().hashCode() ^ engineGetSpec().hashCode();
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append("EC Private Key").append(lineSeparator);
        stringBuffer.append("             S: ").append(this.d.toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    private com.android.internal.org.bouncycastle.asn1.DERBitString getPublicKeyDetails(com.android.internal.org.bouncycastle.jce.provider.JCEECPublicKey jCEECPublicKey) {
        try {
            return com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(jCEECPublicKey.getEncoded())).getPublicKeyData();
        } catch (java.io.IOException e) {
            return null;
        }
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        populateFromPrivKeyInfo(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray((byte[]) objectInputStream.readObject())));
        this.algorithm = (java.lang.String) objectInputStream.readObject();
        this.withCompression = objectInputStream.readBoolean();
        this.attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
        this.attrCarrier.readObject(objectInputStream);
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.writeObject(getEncoded());
        objectOutputStream.writeObject(this.algorithm);
        objectOutputStream.writeBoolean(this.withCompression);
        this.attrCarrier.writeObject(objectOutputStream);
    }
}
