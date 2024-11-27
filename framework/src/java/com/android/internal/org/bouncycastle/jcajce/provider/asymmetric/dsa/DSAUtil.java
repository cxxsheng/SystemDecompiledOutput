package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa;

/* loaded from: classes4.dex */
public class DSAUtil {
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] dsaOids = {com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa, com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.dsaWithSHA1, com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa_with_sha1};

    public static boolean isDsaOid(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        for (int i = 0; i != dsaOids.length; i++) {
            if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) dsaOids[i])) {
                return true;
            }
        }
        return false;
    }

    static com.android.internal.org.bouncycastle.crypto.params.DSAParameters toDSAParameters(java.security.interfaces.DSAParams dSAParams) {
        if (dSAParams != null) {
            return new com.android.internal.org.bouncycastle.crypto.params.DSAParameters(dSAParams.getP(), dSAParams.getQ(), dSAParams.getG());
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter generatePublicKeyParameter(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        if (publicKey instanceof com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPublicKey) {
            return ((com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPublicKey) publicKey).engineGetKeyParameters();
        }
        if (publicKey instanceof java.security.interfaces.DSAPublicKey) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPublicKey((java.security.interfaces.DSAPublicKey) publicKey).engineGetKeyParameters();
        }
        try {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPublicKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(publicKey.getEncoded())).engineGetKeyParameters();
        } catch (java.lang.Exception e) {
            throw new java.security.InvalidKeyException("can't identify DSA public key: " + publicKey.getClass().getName());
        }
    }

    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter generatePrivateKeyParameter(java.security.PrivateKey privateKey) throws java.security.InvalidKeyException {
        if (privateKey instanceof java.security.interfaces.DSAPrivateKey) {
            java.security.interfaces.DSAPrivateKey dSAPrivateKey = (java.security.interfaces.DSAPrivateKey) privateKey;
            return new com.android.internal.org.bouncycastle.crypto.params.DSAPrivateKeyParameters(dSAPrivateKey.getX(), new com.android.internal.org.bouncycastle.crypto.params.DSAParameters(dSAPrivateKey.getParams().getP(), dSAPrivateKey.getParams().getQ(), dSAPrivateKey.getParams().getG()));
        }
        throw new java.security.InvalidKeyException("can't identify DSA private key.");
    }

    static java.lang.String generateKeyFingerprint(java.math.BigInteger bigInteger, java.security.interfaces.DSAParams dSAParams) {
        return new com.android.internal.org.bouncycastle.util.Fingerprint(com.android.internal.org.bouncycastle.util.Arrays.concatenate(bigInteger.toByteArray(), dSAParams.getP().toByteArray(), dSAParams.getQ().toByteArray(), dSAParams.getG().toByteArray())).toString();
    }
}
