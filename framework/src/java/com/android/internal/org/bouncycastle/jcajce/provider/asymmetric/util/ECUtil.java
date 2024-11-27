package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes4.dex */
public class ECUtil {
    static int[] convertMidTerms(int[] iArr) {
        int[] iArr2 = new int[3];
        if (iArr.length != 1) {
            if (iArr.length != 3) {
                throw new java.lang.IllegalArgumentException("Only Trinomials and pentanomials supported");
            }
            if (iArr[0] < iArr[1] && iArr[0] < iArr[2]) {
                iArr2[0] = iArr[0];
                if (iArr[1] < iArr[2]) {
                    iArr2[1] = iArr[1];
                    iArr2[2] = iArr[2];
                } else {
                    iArr2[1] = iArr[2];
                    iArr2[2] = iArr[1];
                }
            } else if (iArr[1] < iArr[2]) {
                iArr2[0] = iArr[1];
                if (iArr[0] < iArr[2]) {
                    iArr2[1] = iArr[0];
                    iArr2[2] = iArr[2];
                } else {
                    iArr2[1] = iArr[2];
                    iArr2[2] = iArr[0];
                }
            } else {
                iArr2[0] = iArr[2];
                if (iArr[0] < iArr[1]) {
                    iArr2[1] = iArr[0];
                    iArr2[2] = iArr[1];
                } else {
                    iArr2[1] = iArr[1];
                    iArr2[2] = iArr[0];
                }
            }
        } else {
            iArr2[0] = iArr[0];
        }
        return iArr2;
    }

    public static com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters getDomainParameters(com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        if (eCParameterSpec instanceof com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec) {
            com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec eCNamedCurveParameterSpec = (com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec) eCParameterSpec;
            return new com.android.internal.org.bouncycastle.crypto.params.ECNamedDomainParameters(getNamedCurveOid(eCNamedCurveParameterSpec.getName()), eCNamedCurveParameterSpec.getCurve(), eCNamedCurveParameterSpec.getG(), eCNamedCurveParameterSpec.getN(), eCNamedCurveParameterSpec.getH(), eCNamedCurveParameterSpec.getSeed());
        }
        if (eCParameterSpec == null) {
            com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec ecImplicitlyCa = providerConfiguration.getEcImplicitlyCa();
            return new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
        }
        return new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN(), eCParameterSpec.getH(), eCParameterSpec.getSeed());
    }

    public static com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters getDomainParameters(com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration, com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters) {
        if (x962Parameters.isNamedCurve()) {
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(x962Parameters.getParameters());
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters namedCurveByOid = getNamedCurveByOid(aSN1ObjectIdentifier);
            if (namedCurveByOid == null) {
                namedCurveByOid = (com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters) providerConfiguration.getAdditionalECParameters().get(aSN1ObjectIdentifier);
            }
            return new com.android.internal.org.bouncycastle.crypto.params.ECNamedDomainParameters(aSN1ObjectIdentifier, namedCurveByOid);
        }
        if (x962Parameters.isImplicitlyCA()) {
            com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec ecImplicitlyCa = providerConfiguration.getEcImplicitlyCa();
            return new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
        }
        com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters x9ECParameters = com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters.getInstance(x962Parameters.getParameters());
        return new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN(), x9ECParameters.getH(), x9ECParameters.getSeed());
    }

    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter generatePublicKeyParameter(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        if (publicKey instanceof com.android.internal.org.bouncycastle.jce.interfaces.ECPublicKey) {
            com.android.internal.org.bouncycastle.jce.interfaces.ECPublicKey eCPublicKey = (com.android.internal.org.bouncycastle.jce.interfaces.ECPublicKey) publicKey;
            com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec parameters = eCPublicKey.getParameters();
            return new com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters(eCPublicKey.getQ(), new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(parameters.getCurve(), parameters.getG(), parameters.getN(), parameters.getH(), parameters.getSeed()));
        }
        if (publicKey instanceof java.security.interfaces.ECPublicKey) {
            java.security.interfaces.ECPublicKey eCPublicKey2 = (java.security.interfaces.ECPublicKey) publicKey;
            com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec convertSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(eCPublicKey2.getParams());
            return new com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(eCPublicKey2.getParams(), eCPublicKey2.getW()), new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(convertSpec.getCurve(), convertSpec.getG(), convertSpec.getN(), convertSpec.getH(), convertSpec.getSeed()));
        }
        try {
            byte[] encoded = publicKey.getEncoded();
            if (encoded == null) {
                throw new java.security.InvalidKeyException("no encoding for EC public key");
            }
            java.security.PublicKey publicKey2 = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.getPublicKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(encoded));
            if (publicKey2 instanceof java.security.interfaces.ECPublicKey) {
                return generatePublicKeyParameter(publicKey2);
            }
            throw new java.security.InvalidKeyException("cannot identify EC public key.");
        } catch (java.lang.Exception e) {
            throw new java.security.InvalidKeyException("cannot identify EC public key: " + e.toString());
        }
    }

    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter generatePrivateKeyParameter(java.security.PrivateKey privateKey) throws java.security.InvalidKeyException {
        if (privateKey instanceof com.android.internal.org.bouncycastle.jce.interfaces.ECPrivateKey) {
            com.android.internal.org.bouncycastle.jce.interfaces.ECPrivateKey eCPrivateKey = (com.android.internal.org.bouncycastle.jce.interfaces.ECPrivateKey) privateKey;
            com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec parameters = eCPrivateKey.getParameters();
            if (parameters == null) {
                parameters = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            }
            if (eCPrivateKey.getParameters() instanceof com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec) {
                return new com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters(eCPrivateKey.getD(), new com.android.internal.org.bouncycastle.crypto.params.ECNamedDomainParameters(com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getOID(((com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec) eCPrivateKey.getParameters()).getName()), parameters.getCurve(), parameters.getG(), parameters.getN(), parameters.getH(), parameters.getSeed()));
            }
            return new com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters(eCPrivateKey.getD(), new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(parameters.getCurve(), parameters.getG(), parameters.getN(), parameters.getH(), parameters.getSeed()));
        }
        if (privateKey instanceof java.security.interfaces.ECPrivateKey) {
            java.security.interfaces.ECPrivateKey eCPrivateKey2 = (java.security.interfaces.ECPrivateKey) privateKey;
            com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec convertSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(eCPrivateKey2.getParams());
            return new com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters(eCPrivateKey2.getS(), new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(convertSpec.getCurve(), convertSpec.getG(), convertSpec.getN(), convertSpec.getH(), convertSpec.getSeed()));
        }
        try {
            byte[] encoded = privateKey.getEncoded();
            if (encoded == null) {
                throw new java.security.InvalidKeyException("no encoding for EC private key");
            }
            java.security.PrivateKey privateKey2 = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.getPrivateKey(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(encoded));
            if (privateKey2 instanceof java.security.interfaces.ECPrivateKey) {
                return generatePrivateKeyParameter(privateKey2);
            }
            throw new java.security.InvalidKeyException("can't identify EC private key.");
        } catch (java.lang.Exception e) {
            throw new java.security.InvalidKeyException("cannot identify EC private key: " + e.toString());
        }
    }

    public static int getOrderBitLength(com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        if (bigInteger == null) {
            com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec ecImplicitlyCa = providerConfiguration.getEcImplicitlyCa();
            if (ecImplicitlyCa == null) {
                return bigInteger2.bitLength();
            }
            return ecImplicitlyCa.getN().bitLength();
        }
        return bigInteger.bitLength();
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getNamedCurveOid(java.lang.String str) {
        int indexOf = str.indexOf(32);
        if (indexOf > 0) {
            str = str.substring(indexOf + 1);
        }
        try {
            if (str.charAt(0) >= '0' && str.charAt(0) <= '2') {
                return new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str);
            }
        } catch (java.lang.IllegalArgumentException e) {
        }
        return com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getOID(str);
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getNamedCurveOid(com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        java.util.Enumeration names = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getNames();
        while (names.hasMoreElements()) {
            java.lang.String str = (java.lang.String) names.nextElement();
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters byName = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByName(str);
            if (byName.getN().equals(eCParameterSpec.getN()) && byName.getH().equals(eCParameterSpec.getH()) && byName.getCurve().equals(eCParameterSpec.getCurve()) && byName.getG().equals(eCParameterSpec.getG())) {
                return com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getOID(str);
            }
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getNamedCurveByOid(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters byOID = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getByOID(aSN1ObjectIdentifier);
        if (byOID == null) {
            return com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByOID(aSN1ObjectIdentifier);
        }
        return byOID;
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getNamedCurveByName(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters byName = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getByName(str);
        if (byName == null) {
            return com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByName(str);
        }
        return byName;
    }

    public static java.lang.String getCurveName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getName(aSN1ObjectIdentifier);
    }

    public static java.lang.String privateKeyToString(java.lang.String str, java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        com.android.internal.org.bouncycastle.math.ec.ECPoint normalize = new com.android.internal.org.bouncycastle.math.ec.FixedPointCombMultiplier().multiply(eCParameterSpec.getG(), bigInteger).normalize();
        stringBuffer.append(str);
        stringBuffer.append(" Private Key [").append(generateKeyFingerprint(normalize, eCParameterSpec)).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).append(lineSeparator);
        stringBuffer.append("            X: ").append(normalize.getAffineXCoord().toBigInteger().toString(16)).append(lineSeparator);
        stringBuffer.append("            Y: ").append(normalize.getAffineYCoord().toBigInteger().toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    public static java.lang.String publicKeyToString(java.lang.String str, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append(str);
        stringBuffer.append(" Public Key [").append(generateKeyFingerprint(eCPoint, eCParameterSpec)).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).append(lineSeparator);
        stringBuffer.append("            X: ").append(eCPoint.getAffineXCoord().toBigInteger().toString(16)).append(lineSeparator);
        stringBuffer.append("            Y: ").append(eCPoint.getAffineYCoord().toBigInteger().toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    public static java.lang.String generateKeyFingerprint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = eCParameterSpec.getCurve();
        com.android.internal.org.bouncycastle.math.ec.ECPoint g = eCParameterSpec.getG();
        if (curve != null) {
            return new com.android.internal.org.bouncycastle.util.Fingerprint(com.android.internal.org.bouncycastle.util.Arrays.concatenate(eCPoint.getEncoded(false), curve.getA().getEncoded(), curve.getB().getEncoded(), g.getEncoded(false))).toString();
        }
        return new com.android.internal.org.bouncycastle.util.Fingerprint(eCPoint.getEncoded(false)).toString();
    }

    public static java.lang.String getNameFrom(final java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
        return (java.lang.String) java.security.AccessController.doPrivileged(new java.security.PrivilegedAction() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.1
            @Override // java.security.PrivilegedAction
            public java.lang.Object run() {
                try {
                    return algorithmParameterSpec.getClass().getMethod("getName", new java.lang.Class[0]).invoke(algorithmParameterSpec, new java.lang.Object[0]);
                } catch (java.lang.Exception e) {
                    return null;
                }
            }
        });
    }
}
