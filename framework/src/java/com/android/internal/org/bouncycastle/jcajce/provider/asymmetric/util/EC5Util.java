package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes4.dex */
public class EC5Util {
    private static java.util.Map customCurves = new java.util.HashMap();

    static {
        java.util.Enumeration names = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getNames();
        while (names.hasMoreElements()) {
            java.lang.String str = (java.lang.String) names.nextElement();
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters byName = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByName(str);
            if (byName != null) {
                customCurves.put(byName.getCurve(), com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getByName(str).getCurve());
            }
        }
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECCurve getCurve(com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration, com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters) {
        java.util.Set acceptableNamedCurves = providerConfiguration.getAcceptableNamedCurves();
        if (x962Parameters.isNamedCurve()) {
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(x962Parameters.getParameters());
            if (acceptableNamedCurves.isEmpty() || acceptableNamedCurves.contains(aSN1ObjectIdentifier)) {
                com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters namedCurveByOid = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
                if (namedCurveByOid == null) {
                    namedCurveByOid = (com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters) providerConfiguration.getAdditionalECParameters().get(aSN1ObjectIdentifier);
                }
                return namedCurveByOid.getCurve();
            }
            throw new java.lang.IllegalStateException("named curve not acceptable");
        }
        if (x962Parameters.isImplicitlyCA()) {
            return providerConfiguration.getEcImplicitlyCa().getCurve();
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(x962Parameters.getParameters());
        if (acceptableNamedCurves.isEmpty()) {
            if (aSN1Sequence.size() > 3) {
                return com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters.getInstance(aSN1Sequence).getCurve();
            }
            throw new java.lang.IllegalStateException("GOST is not supported");
        }
        throw new java.lang.IllegalStateException("encoded parameters not acceptable");
    }

    public static com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters getDomainParameters(com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration, java.security.spec.ECParameterSpec eCParameterSpec) {
        if (eCParameterSpec == null) {
            com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec ecImplicitlyCa = providerConfiguration.getEcImplicitlyCa();
            return new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
        }
        return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getDomainParameters(providerConfiguration, convertSpec(eCParameterSpec));
    }

    public static java.security.spec.ECParameterSpec convertToSpec(com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters, com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve) {
        if (x962Parameters.isNamedCurve()) {
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) x962Parameters.getParameters();
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters namedCurveByOid = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
            if (namedCurveByOid == null) {
                java.util.Map additionalECParameters = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getAdditionalECParameters();
                if (!additionalECParameters.isEmpty()) {
                    namedCurveByOid = (com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters) additionalECParameters.get(aSN1ObjectIdentifier);
                }
            }
            return new com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getCurveName(aSN1ObjectIdentifier), convertCurve(eCCurve, namedCurveByOid.getSeed()), convertPoint(namedCurveByOid.getG()), namedCurveByOid.getN(), namedCurveByOid.getH());
        }
        if (x962Parameters.isImplicitlyCA()) {
            return null;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(x962Parameters.getParameters());
        if (aSN1Sequence.size() <= 3) {
            return null;
        }
        com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters x9ECParameters = com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters.getInstance(aSN1Sequence);
        java.security.spec.EllipticCurve convertCurve = convertCurve(eCCurve, x9ECParameters.getSeed());
        if (x9ECParameters.getH() != null) {
            return new java.security.spec.ECParameterSpec(convertCurve, convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), x9ECParameters.getH().intValue());
        }
        return new java.security.spec.ECParameterSpec(convertCurve, convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), 1);
    }

    public static java.security.spec.ECParameterSpec convertToSpec(com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters x9ECParameters) {
        return new java.security.spec.ECParameterSpec(convertCurve(x9ECParameters.getCurve(), null), convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), x9ECParameters.getH().intValue());
    }

    public static java.security.spec.ECParameterSpec convertToSpec(com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters eCDomainParameters) {
        return new java.security.spec.ECParameterSpec(convertCurve(eCDomainParameters.getCurve(), null), convertPoint(eCDomainParameters.getG()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    public static java.security.spec.EllipticCurve convertCurve(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, byte[] bArr) {
        return new java.security.spec.EllipticCurve(convertField(eCCurve.getField()), eCCurve.getA().toBigInteger(), eCCurve.getB().toBigInteger(), null);
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECCurve convertCurve(java.security.spec.EllipticCurve ellipticCurve) {
        java.security.spec.ECField field = ellipticCurve.getField();
        java.math.BigInteger a = ellipticCurve.getA();
        java.math.BigInteger b = ellipticCurve.getB();
        if (field instanceof java.security.spec.ECFieldFp) {
            com.android.internal.org.bouncycastle.math.ec.ECCurve.Fp fp = new com.android.internal.org.bouncycastle.math.ec.ECCurve.Fp(((java.security.spec.ECFieldFp) field).getP(), a, b);
            if (customCurves.containsKey(fp)) {
                return (com.android.internal.org.bouncycastle.math.ec.ECCurve) customCurves.get(fp);
            }
            return fp;
        }
        java.security.spec.ECFieldF2m eCFieldF2m = (java.security.spec.ECFieldF2m) field;
        int m = eCFieldF2m.getM();
        int[] convertMidTerms = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.convertMidTerms(eCFieldF2m.getMidTermsOfReductionPolynomial());
        return new com.android.internal.org.bouncycastle.math.ec.ECCurve.F2m(m, convertMidTerms[0], convertMidTerms[1], convertMidTerms[2], a, b);
    }

    public static java.security.spec.ECField convertField(com.android.internal.org.bouncycastle.math.field.FiniteField finiteField) {
        if (com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.isFpField(finiteField)) {
            return new java.security.spec.ECFieldFp(finiteField.getCharacteristic());
        }
        com.android.internal.org.bouncycastle.math.field.Polynomial minimalPolynomial = ((com.android.internal.org.bouncycastle.math.field.PolynomialExtensionField) finiteField).getMinimalPolynomial();
        int[] exponentsPresent = minimalPolynomial.getExponentsPresent();
        return new java.security.spec.ECFieldF2m(minimalPolynomial.getDegree(), com.android.internal.org.bouncycastle.util.Arrays.reverse(com.android.internal.org.bouncycastle.util.Arrays.copyOfRange(exponentsPresent, 1, exponentsPresent.length - 1)));
    }

    public static java.security.spec.ECParameterSpec convertSpec(java.security.spec.EllipticCurve ellipticCurve, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        java.security.spec.ECPoint convertPoint = convertPoint(eCParameterSpec.getG());
        if (eCParameterSpec instanceof com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec) {
            return new com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec(((com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec) eCParameterSpec).getName(), ellipticCurve, convertPoint, eCParameterSpec.getN(), eCParameterSpec.getH());
        }
        return new java.security.spec.ECParameterSpec(ellipticCurve, convertPoint, eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
    }

    public static com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec convertSpec(java.security.spec.ECParameterSpec eCParameterSpec) {
        com.android.internal.org.bouncycastle.math.ec.ECCurve convertCurve = convertCurve(eCParameterSpec.getCurve());
        com.android.internal.org.bouncycastle.math.ec.ECPoint convertPoint = convertPoint(convertCurve, eCParameterSpec.getGenerator());
        java.math.BigInteger order = eCParameterSpec.getOrder();
        java.math.BigInteger valueOf = java.math.BigInteger.valueOf(eCParameterSpec.getCofactor());
        byte[] seed = eCParameterSpec.getCurve().getSeed();
        if (eCParameterSpec instanceof com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec) {
            return new com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec(((com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec) eCParameterSpec).getName(), convertCurve, convertPoint, order, valueOf, seed);
        }
        return new com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec(convertCurve, convertPoint, order, valueOf, seed);
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint convertPoint(java.security.spec.ECParameterSpec eCParameterSpec, java.security.spec.ECPoint eCPoint) {
        return convertPoint(convertCurve(eCParameterSpec.getCurve()), eCPoint);
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint convertPoint(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, java.security.spec.ECPoint eCPoint) {
        return eCCurve.createPoint(eCPoint.getAffineX(), eCPoint.getAffineY());
    }

    public static java.security.spec.ECPoint convertPoint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        com.android.internal.org.bouncycastle.math.ec.ECPoint normalize = eCPoint.normalize();
        return new java.security.spec.ECPoint(normalize.getAffineXCoord().toBigInteger(), normalize.getAffineYCoord().toBigInteger());
    }
}
