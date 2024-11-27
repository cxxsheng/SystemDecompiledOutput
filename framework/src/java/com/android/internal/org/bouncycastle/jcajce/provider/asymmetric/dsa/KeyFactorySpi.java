package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa;

/* loaded from: classes4.dex */
public class KeyFactorySpi extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi {
    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.spec.KeySpec engineGetKeySpec(java.security.Key key, java.lang.Class cls) throws java.security.spec.InvalidKeySpecException {
        if (cls.isAssignableFrom(java.security.spec.DSAPublicKeySpec.class) && (key instanceof java.security.interfaces.DSAPublicKey)) {
            java.security.interfaces.DSAPublicKey dSAPublicKey = (java.security.interfaces.DSAPublicKey) key;
            return new java.security.spec.DSAPublicKeySpec(dSAPublicKey.getY(), dSAPublicKey.getParams().getP(), dSAPublicKey.getParams().getQ(), dSAPublicKey.getParams().getG());
        }
        if (cls.isAssignableFrom(java.security.spec.DSAPrivateKeySpec.class) && (key instanceof java.security.interfaces.DSAPrivateKey)) {
            java.security.interfaces.DSAPrivateKey dSAPrivateKey = (java.security.interfaces.DSAPrivateKey) key;
            return new java.security.spec.DSAPrivateKeySpec(dSAPrivateKey.getX(), dSAPrivateKey.getParams().getP(), dSAPrivateKey.getParams().getQ(), dSAPrivateKey.getParams().getG());
        }
        return super.engineGetKeySpec(key, cls);
    }

    @Override // java.security.KeyFactorySpi
    protected java.security.Key engineTranslateKey(java.security.Key key) throws java.security.InvalidKeyException {
        if (key instanceof java.security.interfaces.DSAPublicKey) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPublicKey((java.security.interfaces.DSAPublicKey) key);
        }
        if (key instanceof java.security.interfaces.DSAPrivateKey) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPrivateKey((java.security.interfaces.DSAPrivateKey) key);
        }
        throw new java.security.InvalidKeyException("key type unknown");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
    public java.security.PrivateKey generatePrivate(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
        if (com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.isDsaOid(algorithm)) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPrivateKey(privateKeyInfo);
        }
        throw new java.io.IOException("algorithm identifier " + algorithm + " in key not recognised");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
    public java.security.PublicKey generatePublic(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = subjectPublicKeyInfo.getAlgorithm().getAlgorithm();
        if (com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.isDsaOid(algorithm)) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPublicKey(subjectPublicKeyInfo);
        }
        throw new java.io.IOException("algorithm identifier " + algorithm + " in key not recognised");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.PrivateKey engineGeneratePrivate(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        if (keySpec instanceof java.security.spec.DSAPrivateKeySpec) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPrivateKey((java.security.spec.DSAPrivateKeySpec) keySpec);
        }
        return super.engineGeneratePrivate(keySpec);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.PublicKey engineGeneratePublic(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        if (keySpec instanceof java.security.spec.DSAPublicKeySpec) {
            try {
                return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPublicKey((java.security.spec.DSAPublicKeySpec) keySpec);
            } catch (java.lang.Exception e) {
                throw new java.security.spec.InvalidKeySpecException("invalid KeySpec: " + e.getMessage()) { // from class: com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyFactorySpi.1
                    @Override // java.lang.Throwable
                    public java.lang.Throwable getCause() {
                        return e;
                    }
                };
            }
        }
        return super.engineGeneratePublic(keySpec);
    }
}
