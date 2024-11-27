package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
class X509SignatureUtil {
    private static final java.util.Map<com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier, java.lang.String> algNames = new java.util.HashMap();
    private static final com.android.internal.org.bouncycastle.asn1.ASN1Null derNull;

    X509SignatureUtil() {
    }

    static {
        algNames.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.dsaWithSHA1, "SHA1withDSA");
        algNames.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa_with_sha1, "SHA1withDSA");
        derNull = com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE;
    }

    static boolean isCompositeAlgorithm(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        return com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.id_alg_composite.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) algorithmIdentifier.getAlgorithm());
    }

    static void setSignatureParameters(java.security.Signature signature, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.security.NoSuchAlgorithmException, java.security.SignatureException, java.security.InvalidKeyException {
        if (aSN1Encodable != null && !derNull.equals(aSN1Encodable)) {
            java.security.AlgorithmParameters algorithmParameters = java.security.AlgorithmParameters.getInstance(signature.getAlgorithm(), signature.getProvider());
            try {
                algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded());
                if (signature.getAlgorithm().endsWith("MGF1")) {
                    try {
                        signature.setParameter(algorithmParameters.getParameterSpec(java.security.spec.PSSParameterSpec.class));
                    } catch (java.security.GeneralSecurityException e) {
                        throw new java.security.SignatureException("Exception extracting parameters: " + e.getMessage());
                    }
                }
            } catch (java.io.IOException e2) {
                throw new java.security.SignatureException("IOException decoding parameters: " + e2.getMessage());
            }
        }
    }

    static java.lang.String getSignatureName(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable parameters = algorithmIdentifier.getParameters();
        if (parameters != null && !derNull.equals(parameters)) {
            if (algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS)) {
                return getDigestAlgName(com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams.getInstance(parameters).getHashAlgorithm().getAlgorithm()) + "withRSAandMGF1";
            }
            if (algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA2)) {
                return getDigestAlgName((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(parameters).getObjectAt(0)) + "withECDSA";
            }
        }
        java.lang.String str = algNames.get(algorithmIdentifier.getAlgorithm());
        if (str != null) {
            return str;
        }
        return findAlgName(algorithmIdentifier.getAlgorithm());
    }

    private static java.lang.String getDigestAlgName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.String digestName = com.android.internal.org.bouncycastle.jcajce.util.MessageDigestUtils.getDigestName(aSN1ObjectIdentifier);
        int indexOf = digestName.indexOf(45);
        if (indexOf > 0 && !digestName.startsWith("SHA3")) {
            return digestName.substring(0, indexOf) + digestName.substring(indexOf + 1);
        }
        return digestName;
    }

    private static java.lang.String findAlgName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.String lookupAlg;
        java.lang.String lookupAlg2;
        java.security.Provider provider = java.security.Security.getProvider(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
        if (provider != null && (lookupAlg2 = lookupAlg(provider, aSN1ObjectIdentifier)) != null) {
            return lookupAlg2;
        }
        java.security.Provider[] providers = java.security.Security.getProviders();
        for (int i = 0; i != providers.length; i++) {
            if (provider != providers[i] && (lookupAlg = lookupAlg(providers[i], aSN1ObjectIdentifier)) != null) {
                return lookupAlg;
            }
        }
        return aSN1ObjectIdentifier.getId();
    }

    private static java.lang.String lookupAlg(java.security.Provider provider, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.String property = provider.getProperty("Alg.Alias.Signature." + aSN1ObjectIdentifier);
        if (property != null) {
            return property;
        }
        java.lang.String property2 = provider.getProperty("Alg.Alias.Signature.OID." + aSN1ObjectIdentifier);
        if (property2 != null) {
            return property2;
        }
        return null;
    }

    static void prettyPrintSignature(byte[] bArr, java.lang.StringBuffer stringBuffer, java.lang.String str) {
        if (bArr.length > 20) {
            stringBuffer.append("            Signature: ").append(com.android.internal.org.bouncycastle.util.encoders.Hex.toHexString(bArr, 0, 20)).append(str);
            for (int i = 20; i < bArr.length; i += 20) {
                if (i < bArr.length - 20) {
                    stringBuffer.append("                       ").append(com.android.internal.org.bouncycastle.util.encoders.Hex.toHexString(bArr, i, 20)).append(str);
                } else {
                    stringBuffer.append("                       ").append(com.android.internal.org.bouncycastle.util.encoders.Hex.toHexString(bArr, i, bArr.length - i)).append(str);
                }
            }
            return;
        }
        stringBuffer.append("            Signature: ").append(com.android.internal.org.bouncycastle.util.encoders.Hex.toHexString(bArr)).append(str);
    }
}
