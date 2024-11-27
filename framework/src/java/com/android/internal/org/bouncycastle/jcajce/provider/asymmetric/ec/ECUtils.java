package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec;

/* loaded from: classes4.dex */
class ECUtils {
    ECUtils() {
    }

    static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter generatePublicKeyParameter(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        return publicKey instanceof com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey ? ((com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey) publicKey).engineGetKeyParameters() : com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePublicKeyParameter(publicKey);
    }

    static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getDomainParametersFromGenSpec(java.security.spec.ECGenParameterSpec eCGenParameterSpec) {
        return getDomainParametersFromName(eCGenParameterSpec.getName());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters] */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getDomainParametersFromName(java.lang.String str) {
        try {
            if (str.charAt(0) >= '0' && str.charAt(0) <= '2') {
                str = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str));
            } else if (str.indexOf(32) > 0) {
                str = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByName(str.substring(str.indexOf(32) + 1));
            } else {
                str = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByName(str);
            }
            return str;
        } catch (java.lang.IllegalArgumentException e) {
            return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByName(str);
        }
    }

    static com.android.internal.org.bouncycastle.asn1.x9.X962Parameters getDomainParametersFromName(java.security.spec.ECParameterSpec eCParameterSpec, boolean z) {
        if (eCParameterSpec instanceof com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec) {
            com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec eCNamedCurveSpec = (com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec) eCParameterSpec;
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier namedCurveOid = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveOid(eCNamedCurveSpec.getName());
            if (namedCurveOid == null) {
                namedCurveOid = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(eCNamedCurveSpec.getName());
            }
            return new com.android.internal.org.bouncycastle.asn1.x9.X962Parameters(namedCurveOid);
        }
        if (eCParameterSpec == null) {
            return new com.android.internal.org.bouncycastle.asn1.x9.X962Parameters((com.android.internal.org.bouncycastle.asn1.ASN1Null) com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
        }
        com.android.internal.org.bouncycastle.math.ec.ECCurve convertCurve = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(eCParameterSpec.getCurve());
        return new com.android.internal.org.bouncycastle.asn1.x9.X962Parameters(new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(convertCurve, new com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(convertCurve, eCParameterSpec.getGenerator()), z), eCParameterSpec.getOrder(), java.math.BigInteger.valueOf(eCParameterSpec.getCofactor()), eCParameterSpec.getCurve().getSeed()));
    }
}
