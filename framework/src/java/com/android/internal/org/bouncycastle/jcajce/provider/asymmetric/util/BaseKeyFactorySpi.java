package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes4.dex */
public abstract class BaseKeyFactorySpi extends java.security.KeyFactorySpi implements com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter {
    @Override // java.security.KeyFactorySpi
    protected java.security.PrivateKey engineGeneratePrivate(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        if (keySpec instanceof java.security.spec.PKCS8EncodedKeySpec) {
            try {
                return generatePrivate(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(((java.security.spec.PKCS8EncodedKeySpec) keySpec).getEncoded()));
            } catch (java.lang.Exception e) {
                throw new java.security.spec.InvalidKeySpecException("encoded key spec not recognized: " + e.getMessage());
            }
        }
        throw new java.security.spec.InvalidKeySpecException("key spec not recognized");
    }

    @Override // java.security.KeyFactorySpi
    protected java.security.PublicKey engineGeneratePublic(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        if (keySpec instanceof java.security.spec.X509EncodedKeySpec) {
            try {
                return generatePublic(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(((java.security.spec.X509EncodedKeySpec) keySpec).getEncoded()));
            } catch (java.lang.Exception e) {
                throw new java.security.spec.InvalidKeySpecException("encoded key spec not recognized: " + e.getMessage());
            }
        }
        throw new java.security.spec.InvalidKeySpecException("key spec not recognized");
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
}
