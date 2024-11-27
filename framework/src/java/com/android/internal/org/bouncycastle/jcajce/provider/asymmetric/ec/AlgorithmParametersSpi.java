package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec;

/* loaded from: classes4.dex */
public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    private java.lang.String curveName;
    private java.security.spec.ECParameterSpec ecParameterSpec;

    protected boolean isASN1FormatString(java.lang.String str) {
        return str == null || str.equals("ASN.1");
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.spec.InvalidParameterSpecException {
        if (algorithmParameterSpec instanceof java.security.spec.ECGenParameterSpec) {
            java.security.spec.ECGenParameterSpec eCGenParameterSpec = (java.security.spec.ECGenParameterSpec) algorithmParameterSpec;
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters domainParametersFromGenSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils.getDomainParametersFromGenSpec(eCGenParameterSpec);
            if (domainParametersFromGenSpec == null) {
                throw new java.security.spec.InvalidParameterSpecException("EC curve name not recognized: " + eCGenParameterSpec.getName());
            }
            this.curveName = eCGenParameterSpec.getName();
            java.security.spec.ECParameterSpec convertToSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertToSpec(domainParametersFromGenSpec);
            this.ecParameterSpec = new com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec(this.curveName, convertToSpec.getCurve(), convertToSpec.getGenerator(), convertToSpec.getOrder(), java.math.BigInteger.valueOf(convertToSpec.getCofactor()));
            return;
        }
        if (algorithmParameterSpec instanceof java.security.spec.ECParameterSpec) {
            if (algorithmParameterSpec instanceof com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec) {
                this.curveName = ((com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec) algorithmParameterSpec).getName();
            } else {
                this.curveName = null;
            }
            this.ecParameterSpec = (java.security.spec.ECParameterSpec) algorithmParameterSpec;
            return;
        }
        throw new java.security.spec.InvalidParameterSpecException("AlgorithmParameterSpec class not recognized: " + algorithmParameterSpec.getClass().getName());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws java.io.IOException {
        engineInit(bArr, "ASN.1");
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr, java.lang.String str) throws java.io.IOException {
        if (isASN1FormatString(str)) {
            com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters = com.android.internal.org.bouncycastle.asn1.x9.X962Parameters.getInstance(bArr);
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.getCurve(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION, x962Parameters);
            if (x962Parameters.isNamedCurve()) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(x962Parameters.getParameters());
                this.curveName = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getName(aSN1ObjectIdentifier);
                if (this.curveName == null) {
                    this.curveName = aSN1ObjectIdentifier.getId();
                }
            }
            this.ecParameterSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertToSpec(x962Parameters, curve);
            return;
        }
        throw new java.io.IOException("Unknown encoded parameters format in AlgorithmParameters object: " + str);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected <T extends java.security.spec.AlgorithmParameterSpec> T engineGetParameterSpec(java.lang.Class<T> cls) throws java.security.spec.InvalidParameterSpecException {
        if (java.security.spec.ECParameterSpec.class.isAssignableFrom(cls) || cls == java.security.spec.AlgorithmParameterSpec.class) {
            return this.ecParameterSpec;
        }
        if (java.security.spec.ECGenParameterSpec.class.isAssignableFrom(cls)) {
            if (this.curveName != null) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier namedCurveOid = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveOid(this.curveName);
                if (namedCurveOid != null) {
                    return new java.security.spec.ECGenParameterSpec(namedCurveOid.getId());
                }
                return new java.security.spec.ECGenParameterSpec(this.curveName);
            }
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier namedCurveOid2 = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveOid(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(this.ecParameterSpec));
            if (namedCurveOid2 != null) {
                return new java.security.spec.ECGenParameterSpec(namedCurveOid2.getId());
            }
        }
        throw new java.security.spec.InvalidParameterSpecException("EC AlgorithmParameters cannot convert to " + cls.getName());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws java.io.IOException {
        return engineGetEncoded("ASN.1");
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(java.lang.String str) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters;
        if (isASN1FormatString(str)) {
            if (this.ecParameterSpec == null) {
                x962Parameters = new com.android.internal.org.bouncycastle.asn1.x9.X962Parameters((com.android.internal.org.bouncycastle.asn1.ASN1Null) com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
            } else if (this.curveName != null) {
                x962Parameters = new com.android.internal.org.bouncycastle.asn1.x9.X962Parameters(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveOid(this.curveName));
            } else {
                com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec convertSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(this.ecParameterSpec);
                x962Parameters = new com.android.internal.org.bouncycastle.asn1.x9.X962Parameters(new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(convertSpec.getCurve(), new com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint(convertSpec.getG(), false), convertSpec.getN(), convertSpec.getH(), convertSpec.getSeed()));
            }
            return x962Parameters.getEncoded();
        }
        throw new java.io.IOException("Unknown parameters format in AlgorithmParameters object: " + str);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected java.lang.String engineToString() {
        return "EC Parameters";
    }
}
