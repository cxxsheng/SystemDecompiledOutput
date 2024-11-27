package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

/* loaded from: classes4.dex */
public class RSAUtil {
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] rsaOids = {com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption, com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_ea_rsa, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSAES_OAEP, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS};

    public static boolean isRsaOid(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        for (int i = 0; i != rsaOids.length; i++) {
            if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) rsaOids[i])) {
                return true;
            }
        }
        return false;
    }

    static com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters generatePublicKeyParameter(java.security.interfaces.RSAPublicKey rSAPublicKey) {
        if (rSAPublicKey instanceof com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey) {
            return ((com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey) rSAPublicKey).engineGetKeyParameters();
        }
        return new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(false, rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
    }

    static com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters generatePrivateKeyParameter(java.security.interfaces.RSAPrivateKey rSAPrivateKey) {
        if (rSAPrivateKey instanceof com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateKey) {
            return ((com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateKey) rSAPrivateKey).engineGetKeyParameters();
        }
        if (rSAPrivateKey instanceof java.security.interfaces.RSAPrivateCrtKey) {
            java.security.interfaces.RSAPrivateCrtKey rSAPrivateCrtKey = (java.security.interfaces.RSAPrivateCrtKey) rSAPrivateKey;
            return new com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters(rSAPrivateCrtKey.getModulus(), rSAPrivateCrtKey.getPublicExponent(), rSAPrivateCrtKey.getPrivateExponent(), rSAPrivateCrtKey.getPrimeP(), rSAPrivateCrtKey.getPrimeQ(), rSAPrivateCrtKey.getPrimeExponentP(), rSAPrivateCrtKey.getPrimeExponentQ(), rSAPrivateCrtKey.getCrtCoefficient());
        }
        return new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(true, rSAPrivateKey.getModulus(), rSAPrivateKey.getPrivateExponent());
    }

    static java.lang.String generateKeyFingerprint(java.math.BigInteger bigInteger) {
        return new com.android.internal.org.bouncycastle.util.Fingerprint(bigInteger.toByteArray()).toString();
    }

    static java.lang.String generateExponentFingerprint(java.math.BigInteger bigInteger) {
        return new com.android.internal.org.bouncycastle.util.Fingerprint(bigInteger.toByteArray(), 32).toString();
    }
}
