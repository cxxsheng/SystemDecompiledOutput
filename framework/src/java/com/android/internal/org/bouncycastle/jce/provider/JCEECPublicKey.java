package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class JCEECPublicKey implements java.security.interfaces.ECPublicKey, com.android.internal.org.bouncycastle.jce.interfaces.ECPublicKey, com.android.internal.org.bouncycastle.jce.interfaces.ECPointEncoder {
    private java.lang.String algorithm;
    private java.security.spec.ECParameterSpec ecSpec;
    private com.android.internal.org.bouncycastle.math.ec.ECPoint q;
    private boolean withCompression;

    public JCEECPublicKey(java.lang.String str, com.android.internal.org.bouncycastle.jce.provider.JCEECPublicKey jCEECPublicKey) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.algorithm = str;
        this.q = jCEECPublicKey.q;
        this.ecSpec = jCEECPublicKey.ecSpec;
        this.withCompression = jCEECPublicKey.withCompression;
    }

    public JCEECPublicKey(java.lang.String str, java.security.spec.ECPublicKeySpec eCPublicKeySpec) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.algorithm = str;
        this.ecSpec = eCPublicKeySpec.getParams();
        this.q = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(this.ecSpec, eCPublicKeySpec.getW());
    }

    public JCEECPublicKey(java.lang.String str, com.android.internal.org.bouncycastle.jce.spec.ECPublicKeySpec eCPublicKeySpec) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.algorithm = str;
        this.q = eCPublicKeySpec.getQ();
        if (eCPublicKeySpec.getParams() != null) {
            this.ecSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(eCPublicKeySpec.getParams().getCurve(), eCPublicKeySpec.getParams().getSeed()), eCPublicKeySpec.getParams());
            return;
        }
        if (this.q.getCurve() == null) {
            this.q = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve().createPoint(this.q.getAffineXCoord().toBigInteger(), this.q.getAffineYCoord().toBigInteger());
        }
        this.ecSpec = null;
    }

    public JCEECPublicKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters eCPublicKeyParameters, java.security.spec.ECParameterSpec eCParameterSpec) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.algorithm = str;
        this.q = eCPublicKeyParameters.getQ();
        if (eCParameterSpec == null) {
            this.ecSpec = createSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters);
        } else {
            this.ecSpec = eCParameterSpec;
        }
    }

    public JCEECPublicKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters eCPublicKeyParameters, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.algorithm = str;
        this.q = eCPublicKeyParameters.getQ();
        if (eCParameterSpec == null) {
            this.ecSpec = createSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters);
        } else {
            this.ecSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
        }
    }

    public JCEECPublicKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters eCPublicKeyParameters) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.algorithm = str;
        this.q = eCPublicKeyParameters.getQ();
        this.ecSpec = null;
    }

    private java.security.spec.ECParameterSpec createSpec(java.security.spec.EllipticCurve ellipticCurve, com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters eCDomainParameters) {
        return new java.security.spec.ECParameterSpec(ellipticCurve, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(eCDomainParameters.getG()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    public JCEECPublicKey(java.security.interfaces.ECPublicKey eCPublicKey) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        this.algorithm = eCPublicKey.getAlgorithm();
        this.ecSpec = eCPublicKey.getParams();
        this.q = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(this.ecSpec, eCPublicKey.getW());
    }

    JCEECPublicKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.algorithm = android.security.keystore.KeyProperties.KEY_ALGORITHM_EC;
        populateFromPubKeyInfo(subjectPublicKeyInfo);
    }

    private void populateFromPubKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve;
        com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters = com.android.internal.org.bouncycastle.asn1.x9.X962Parameters.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
        if (x962Parameters.isNamedCurve()) {
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) x962Parameters.getParameters();
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters namedCurveByOid = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
            curve = namedCurveByOid.getCurve();
            this.ecSpec = new com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getCurveName(aSN1ObjectIdentifier), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(curve, namedCurveByOid.getSeed()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(namedCurveByOid.getG()), namedCurveByOid.getN(), namedCurveByOid.getH());
        } else if (x962Parameters.isImplicitlyCA()) {
            this.ecSpec = null;
            curve = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve();
        } else {
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters x9ECParameters = com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters.getInstance(x962Parameters.getParameters());
            curve = x9ECParameters.getCurve();
            this.ecSpec = new java.security.spec.ECParameterSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(curve, x9ECParameters.getSeed()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), x9ECParameters.getH().intValue());
        }
        byte[] bytes = subjectPublicKeyInfo.getPublicKeyData().getBytes();
        com.android.internal.org.bouncycastle.asn1.ASN1OctetString dEROctetString = new com.android.internal.org.bouncycastle.asn1.DEROctetString(bytes);
        if (bytes[0] == 4 && bytes[1] == bytes.length - 2 && ((bytes[2] == 2 || bytes[2] == 3) && new com.android.internal.org.bouncycastle.asn1.x9.X9IntegerConverter().getByteLength(curve) >= bytes.length - 3)) {
            try {
                dEROctetString = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bytes);
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("error recovering public key");
            }
        }
        this.q = new com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint(curve, dEROctetString).getPoint();
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
        com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters;
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
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil.getEncodedSubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey, x962Parameters), getQ().getEncoded(this.withCompression)));
    }

    private void extractBytes(byte[] bArr, int i, java.math.BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length < 32) {
            byte[] bArr2 = new byte[32];
            java.lang.System.arraycopy(byteArray, 0, bArr2, 32 - byteArray.length, byteArray.length);
            byteArray = bArr2;
        }
        for (int i2 = 0; i2 != 32; i2++) {
            bArr[i + i2] = byteArray[(byteArray.length - 1) - i2];
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

    @Override // java.security.interfaces.ECPublicKey
    public java.security.spec.ECPoint getW() {
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(this.q);
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.ECPublicKey
    public com.android.internal.org.bouncycastle.math.ec.ECPoint getQ() {
        if (this.ecSpec == null) {
            return this.q.getDetachedPoint();
        }
        return this.q;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint engineGetQ() {
        return this.q;
    }

    com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        if (this.ecSpec != null) {
            return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(this.ecSpec);
        }
        return com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append("EC Public Key").append(lineSeparator);
        stringBuffer.append("            X: ").append(this.q.getAffineXCoord().toBigInteger().toString(16)).append(lineSeparator);
        stringBuffer.append("            Y: ").append(this.q.getAffineYCoord().toBigInteger().toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.ECPointEncoder
    public void setPointFormat(java.lang.String str) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.jce.provider.JCEECPublicKey)) {
            return false;
        }
        com.android.internal.org.bouncycastle.jce.provider.JCEECPublicKey jCEECPublicKey = (com.android.internal.org.bouncycastle.jce.provider.JCEECPublicKey) obj;
        return engineGetQ().equals(jCEECPublicKey.engineGetQ()) && engineGetSpec().equals(jCEECPublicKey.engineGetSpec());
    }

    public int hashCode() {
        return engineGetQ().hashCode() ^ engineGetSpec().hashCode();
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        populateFromPubKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray((byte[]) objectInputStream.readObject())));
        this.algorithm = (java.lang.String) objectInputStream.readObject();
        this.withCompression = objectInputStream.readBoolean();
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.writeObject(getEncoded());
        objectOutputStream.writeObject(this.algorithm);
        objectOutputStream.writeBoolean(this.withCompression);
    }
}
