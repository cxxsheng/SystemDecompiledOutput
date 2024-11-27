package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
class X509SignatureUtil {
    private static final com.android.internal.org.bouncycastle.asn1.ASN1Null derNull = com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE;

    X509SignatureUtil() {
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
        if (parameters != null && !derNull.equals(parameters) && algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA2)) {
            return getDigestAlgName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(parameters).getObjectAt(0))) + "withECDSA";
        }
        return algorithmIdentifier.getAlgorithm().getId();
    }

    private static java.lang.String getDigestAlgName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return android.security.keystore.KeyProperties.DIGEST_MD5;
        }
        if (com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA1";
        }
        if (com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA224";
        }
        if (com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA256";
        }
        if (com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA384";
        }
        if (com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA512";
        }
        return aSN1ObjectIdentifier.getId();
    }
}
