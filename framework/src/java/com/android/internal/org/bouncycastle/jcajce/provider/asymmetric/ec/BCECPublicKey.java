package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec;

/* loaded from: classes4.dex */
public class BCECPublicKey implements java.security.interfaces.ECPublicKey, com.android.internal.org.bouncycastle.jce.interfaces.ECPublicKey, com.android.internal.org.bouncycastle.jce.interfaces.ECPointEncoder {
    static final long serialVersionUID = 2422789860422731812L;
    private java.lang.String algorithm;
    private transient com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration configuration;
    private transient com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters ecPublicKey;
    private transient java.security.spec.ECParameterSpec ecSpec;
    private boolean withCompression;

    public BCECPublicKey(java.lang.String str, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey bCECPublicKey) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.algorithm = str;
        this.ecPublicKey = bCECPublicKey.ecPublicKey;
        this.ecSpec = bCECPublicKey.ecSpec;
        this.withCompression = bCECPublicKey.withCompression;
        this.configuration = bCECPublicKey.configuration;
    }

    public BCECPublicKey(java.lang.String str, java.security.spec.ECPublicKeySpec eCPublicKeySpec, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.algorithm = str;
        this.ecSpec = eCPublicKeySpec.getParams();
        this.ecPublicKey = new com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(this.ecSpec, eCPublicKeySpec.getW()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.getDomainParameters(providerConfiguration, eCPublicKeySpec.getParams()));
        this.configuration = providerConfiguration;
    }

    public BCECPublicKey(java.lang.String str, com.android.internal.org.bouncycastle.jce.spec.ECPublicKeySpec eCPublicKeySpec, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.algorithm = str;
        if (eCPublicKeySpec.getParams() != null) {
            java.security.spec.EllipticCurve convertCurve = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(eCPublicKeySpec.getParams().getCurve(), eCPublicKeySpec.getParams().getSeed());
            this.ecPublicKey = new com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters(eCPublicKeySpec.getQ(), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getDomainParameters(providerConfiguration, eCPublicKeySpec.getParams()));
            this.ecSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(convertCurve, eCPublicKeySpec.getParams());
        } else {
            this.ecPublicKey = new com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters(providerConfiguration.getEcImplicitlyCa().getCurve().createPoint(eCPublicKeySpec.getQ().getAffineXCoord().toBigInteger(), eCPublicKeySpec.getQ().getAffineYCoord().toBigInteger()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.getDomainParameters(providerConfiguration, null));
            this.ecSpec = null;
        }
        this.configuration = providerConfiguration;
    }

    public BCECPublicKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters eCPublicKeyParameters, java.security.spec.ECParameterSpec eCParameterSpec, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.algorithm = str;
        this.ecPublicKey = eCPublicKeyParameters;
        if (eCParameterSpec == null) {
            this.ecSpec = createSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters);
        } else {
            this.ecSpec = eCParameterSpec;
        }
        this.configuration = providerConfiguration;
    }

    public BCECPublicKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters eCPublicKeyParameters, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.algorithm = str;
        if (eCParameterSpec == null) {
            this.ecSpec = createSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters);
        } else {
            this.ecSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
        }
        this.ecPublicKey = eCPublicKeyParameters;
        this.configuration = providerConfiguration;
    }

    public BCECPublicKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters eCPublicKeyParameters, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.algorithm = str;
        this.ecPublicKey = eCPublicKeyParameters;
        this.ecSpec = null;
        this.configuration = providerConfiguration;
    }

    public BCECPublicKey(java.security.interfaces.ECPublicKey eCPublicKey, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.algorithm = eCPublicKey.getAlgorithm();
        this.ecSpec = eCPublicKey.getParams();
        this.ecPublicKey = new com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(this.ecSpec, eCPublicKey.getW()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.getDomainParameters(providerConfiguration, eCPublicKey.getParams()));
        this.configuration = providerConfiguration;
    }

    BCECPublicKey(java.lang.String str, com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.algorithm = str;
        this.configuration = providerConfiguration;
        populateFromPubKeyInfo(subjectPublicKeyInfo);
    }

    private java.security.spec.ECParameterSpec createSpec(java.security.spec.EllipticCurve ellipticCurve, com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters eCDomainParameters) {
        return new java.security.spec.ECParameterSpec(ellipticCurve, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(eCDomainParameters.getG()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    private void populateFromPubKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters = com.android.internal.org.bouncycastle.asn1.x9.X962Parameters.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.getCurve(this.configuration, x962Parameters);
        this.ecSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertToSpec(x962Parameters, curve);
        byte[] bytes = subjectPublicKeyInfo.getPublicKeyData().getBytes();
        com.android.internal.org.bouncycastle.asn1.ASN1OctetString dEROctetString = new com.android.internal.org.bouncycastle.asn1.DEROctetString(bytes);
        if (bytes[0] == 4 && bytes[1] == bytes.length - 2 && ((bytes[2] == 2 || bytes[2] == 3) && new com.android.internal.org.bouncycastle.asn1.x9.X9IntegerConverter().getByteLength(curve) >= bytes.length - 3)) {
            try {
                dEROctetString = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bytes);
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("error recovering public key");
            }
        }
        this.ecPublicKey = new com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters(new com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint(curve, dEROctetString).getPoint(), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getDomainParameters(this.configuration, x962Parameters));
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        return this.algorithm;
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return "X.509";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        boolean z = this.withCompression || com.android.internal.org.bouncycastle.util.Properties.isOverrideSet("com.android.internal.org.bouncycastle.ec.enable_pc");
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil.getEncodedSubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils.getDomainParametersFromName(this.ecSpec, z)), this.ecPublicKey.getQ().getEncoded(z));
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

    @Override // java.security.interfaces.ECPublicKey
    public java.security.spec.ECPoint getW() {
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(this.ecPublicKey.getQ());
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.ECPublicKey
    public com.android.internal.org.bouncycastle.math.ec.ECPoint getQ() {
        com.android.internal.org.bouncycastle.math.ec.ECPoint q = this.ecPublicKey.getQ();
        if (this.ecSpec == null) {
            return q.getDetachedPoint();
        }
        return q;
    }

    com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters engineGetKeyParameters() {
        return this.ecPublicKey;
    }

    com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        if (this.ecSpec != null) {
            return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(this.ecSpec);
        }
        return this.configuration.getEcImplicitlyCa();
    }

    public java.lang.String toString() {
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.publicKeyToString(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC, this.ecPublicKey.getQ(), engineGetSpec());
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.ECPointEncoder
    public void setPointFormat(java.lang.String str) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey)) {
            return false;
        }
        com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey bCECPublicKey = (com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey) obj;
        return this.ecPublicKey.getQ().equals(bCECPublicKey.ecPublicKey.getQ()) && engineGetSpec().equals(bCECPublicKey.engineGetSpec());
    }

    public int hashCode() {
        return this.ecPublicKey.getQ().hashCode() ^ engineGetSpec().hashCode();
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        objectInputStream.defaultReadObject();
        byte[] bArr = (byte[]) objectInputStream.readObject();
        this.configuration = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION;
        populateFromPubKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bArr)));
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }
}
