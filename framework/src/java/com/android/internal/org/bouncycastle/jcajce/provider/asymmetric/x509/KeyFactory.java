package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
public class KeyFactory extends java.security.KeyFactorySpi {
    @Override // java.security.KeyFactorySpi
    protected java.security.PrivateKey engineGeneratePrivate(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        if (keySpec instanceof java.security.spec.PKCS8EncodedKeySpec) {
            try {
                com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo = com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(((java.security.spec.PKCS8EncodedKeySpec) keySpec).getEncoded());
                java.security.PrivateKey privateKey = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.getPrivateKey(privateKeyInfo);
                if (privateKey != null) {
                    return privateKey;
                }
                throw new java.security.spec.InvalidKeySpecException("no factory found for OID: " + privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm());
            } catch (java.lang.Exception e) {
                throw new java.security.spec.InvalidKeySpecException(e.toString());
            }
        }
        throw new java.security.spec.InvalidKeySpecException("Unknown KeySpec type: " + keySpec.getClass().getName());
    }

    @Override // java.security.KeyFactorySpi
    protected java.security.PublicKey engineGeneratePublic(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        if (keySpec instanceof java.security.spec.X509EncodedKeySpec) {
            try {
                com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo = com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(((java.security.spec.X509EncodedKeySpec) keySpec).getEncoded());
                java.security.PublicKey publicKey = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.getPublicKey(subjectPublicKeyInfo);
                if (publicKey != null) {
                    return publicKey;
                }
                throw new java.security.spec.InvalidKeySpecException("no factory found for OID: " + subjectPublicKeyInfo.getAlgorithm().getAlgorithm());
            } catch (java.lang.Exception e) {
                throw new java.security.spec.InvalidKeySpecException(e.toString());
            }
        }
        throw new java.security.spec.InvalidKeySpecException("Unknown KeySpec type: " + keySpec.getClass().getName());
    }

    @Override // java.security.KeyFactorySpi
    protected java.security.spec.KeySpec engineGetKeySpec(java.security.Key key, java.lang.Class cls) throws java.security.spec.InvalidKeySpecException {
        if (cls.isAssignableFrom(java.security.spec.PKCS8EncodedKeySpec.class) && key.getFormat().equals("PKCS#8")) {
            return new java.security.spec.PKCS8EncodedKeySpec(key.getEncoded());
        }
        if (cls.isAssignableFrom(java.security.spec.X509EncodedKeySpec.class) && key.getFormat().equals("X.509")) {
            return new java.security.spec.X509EncodedKeySpec(key.getEncoded());
        }
        throw new java.security.spec.InvalidKeySpecException("not implemented yet " + key + " " + cls);
    }

    @Override // java.security.KeyFactorySpi
    protected java.security.Key engineTranslateKey(java.security.Key key) throws java.security.InvalidKeyException {
        throw new java.security.InvalidKeyException("not implemented yet " + key);
    }
}
