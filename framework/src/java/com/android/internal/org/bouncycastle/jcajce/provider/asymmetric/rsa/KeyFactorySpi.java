package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

/* loaded from: classes4.dex */
public class KeyFactorySpi extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi {
    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.spec.KeySpec engineGetKeySpec(java.security.Key key, java.lang.Class cls) throws java.security.spec.InvalidKeySpecException {
        if ((cls.isAssignableFrom(java.security.spec.KeySpec.class) || cls.isAssignableFrom(java.security.spec.RSAPublicKeySpec.class)) && (key instanceof java.security.interfaces.RSAPublicKey)) {
            java.security.interfaces.RSAPublicKey rSAPublicKey = (java.security.interfaces.RSAPublicKey) key;
            return new java.security.spec.RSAPublicKeySpec(rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
        }
        if ((cls.isAssignableFrom(java.security.spec.KeySpec.class) || cls.isAssignableFrom(java.security.spec.RSAPrivateCrtKeySpec.class)) && (key instanceof java.security.interfaces.RSAPrivateCrtKey)) {
            java.security.interfaces.RSAPrivateCrtKey rSAPrivateCrtKey = (java.security.interfaces.RSAPrivateCrtKey) key;
            return new java.security.spec.RSAPrivateCrtKeySpec(rSAPrivateCrtKey.getModulus(), rSAPrivateCrtKey.getPublicExponent(), rSAPrivateCrtKey.getPrivateExponent(), rSAPrivateCrtKey.getPrimeP(), rSAPrivateCrtKey.getPrimeQ(), rSAPrivateCrtKey.getPrimeExponentP(), rSAPrivateCrtKey.getPrimeExponentQ(), rSAPrivateCrtKey.getCrtCoefficient());
        }
        if ((cls.isAssignableFrom(java.security.spec.KeySpec.class) || cls.isAssignableFrom(java.security.spec.RSAPrivateKeySpec.class)) && (key instanceof java.security.interfaces.RSAPrivateKey)) {
            java.security.interfaces.RSAPrivateKey rSAPrivateKey = (java.security.interfaces.RSAPrivateKey) key;
            return new java.security.spec.RSAPrivateKeySpec(rSAPrivateKey.getModulus(), rSAPrivateKey.getPrivateExponent());
        }
        return super.engineGetKeySpec(key, cls);
    }

    @Override // java.security.KeyFactorySpi
    protected java.security.Key engineTranslateKey(java.security.Key key) throws java.security.InvalidKeyException {
        if (key instanceof java.security.interfaces.RSAPublicKey) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey((java.security.interfaces.RSAPublicKey) key);
        }
        if (key instanceof java.security.interfaces.RSAPrivateCrtKey) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey((java.security.interfaces.RSAPrivateCrtKey) key);
        }
        if (key instanceof java.security.interfaces.RSAPrivateKey) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateKey((java.security.interfaces.RSAPrivateKey) key);
        }
        throw new java.security.InvalidKeyException("key type unknown");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.PrivateKey engineGeneratePrivate(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        if (keySpec instanceof java.security.spec.PKCS8EncodedKeySpec) {
            try {
                return generatePrivate(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(((java.security.spec.PKCS8EncodedKeySpec) keySpec).getEncoded()));
            } catch (java.lang.Exception e) {
                try {
                    return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey(com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(((java.security.spec.PKCS8EncodedKeySpec) keySpec).getEncoded()));
                } catch (java.lang.Exception e2) {
                    throw new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ExtendedInvalidKeySpecException("unable to process key spec: " + e.toString(), e);
                }
            }
        }
        if (keySpec instanceof java.security.spec.RSAPrivateCrtKeySpec) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey((java.security.spec.RSAPrivateCrtKeySpec) keySpec);
        }
        if (keySpec instanceof java.security.spec.RSAPrivateKeySpec) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateKey((java.security.spec.RSAPrivateKeySpec) keySpec);
        }
        throw new java.security.spec.InvalidKeySpecException("unknown KeySpec type: " + keySpec.getClass().getName());
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.PublicKey engineGeneratePublic(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        if (keySpec instanceof java.security.spec.RSAPublicKeySpec) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey((java.security.spec.RSAPublicKeySpec) keySpec);
        }
        return super.engineGeneratePublic(keySpec);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
    public java.security.PrivateKey generatePrivate(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
        if (com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil.isRsaOid(algorithm)) {
            com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKey rSAPrivateKey = com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(privateKeyInfo.parsePrivateKey());
            if (rSAPrivateKey.getCoefficient().intValue() == 0) {
                return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateKey(privateKeyInfo.getPrivateKeyAlgorithm(), rSAPrivateKey);
            }
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey(privateKeyInfo);
        }
        throw new java.io.IOException("algorithm identifier " + algorithm + " in key not recognised");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
    public java.security.PublicKey generatePublic(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = subjectPublicKeyInfo.getAlgorithm().getAlgorithm();
        if (com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil.isRsaOid(algorithm)) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey(subjectPublicKeyInfo);
        }
        throw new java.io.IOException("algorithm identifier " + algorithm + " in key not recognised");
    }
}
