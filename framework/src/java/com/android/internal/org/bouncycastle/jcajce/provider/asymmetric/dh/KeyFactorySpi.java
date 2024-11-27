package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh;

/* loaded from: classes4.dex */
public class KeyFactorySpi extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi {
    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.spec.KeySpec engineGetKeySpec(java.security.Key key, java.lang.Class cls) throws java.security.spec.InvalidKeySpecException {
        if (cls.isAssignableFrom(javax.crypto.spec.DHPrivateKeySpec.class) && (key instanceof javax.crypto.interfaces.DHPrivateKey)) {
            javax.crypto.interfaces.DHPrivateKey dHPrivateKey = (javax.crypto.interfaces.DHPrivateKey) key;
            return new javax.crypto.spec.DHPrivateKeySpec(dHPrivateKey.getX(), dHPrivateKey.getParams().getP(), dHPrivateKey.getParams().getG());
        }
        if (cls.isAssignableFrom(javax.crypto.spec.DHPublicKeySpec.class) && (key instanceof javax.crypto.interfaces.DHPublicKey)) {
            javax.crypto.interfaces.DHPublicKey dHPublicKey = (javax.crypto.interfaces.DHPublicKey) key;
            return new javax.crypto.spec.DHPublicKeySpec(dHPublicKey.getY(), dHPublicKey.getParams().getP(), dHPublicKey.getParams().getG());
        }
        return super.engineGetKeySpec(key, cls);
    }

    @Override // java.security.KeyFactorySpi
    protected java.security.Key engineTranslateKey(java.security.Key key) throws java.security.InvalidKeyException {
        if (key instanceof javax.crypto.interfaces.DHPublicKey) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey((javax.crypto.interfaces.DHPublicKey) key);
        }
        if (key instanceof javax.crypto.interfaces.DHPrivateKey) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPrivateKey((javax.crypto.interfaces.DHPrivateKey) key);
        }
        throw new java.security.InvalidKeyException("key type unknown");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.PrivateKey engineGeneratePrivate(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        if (keySpec instanceof javax.crypto.spec.DHPrivateKeySpec) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPrivateKey((javax.crypto.spec.DHPrivateKeySpec) keySpec);
        }
        return super.engineGeneratePrivate(keySpec);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.PublicKey engineGeneratePublic(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        if (keySpec instanceof javax.crypto.spec.DHPublicKeySpec) {
            try {
                return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey((javax.crypto.spec.DHPublicKeySpec) keySpec);
            } catch (java.lang.IllegalArgumentException e) {
                throw new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ExtendedInvalidKeySpecException(e.getMessage(), e);
            }
        }
        return super.engineGeneratePublic(keySpec);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
    public java.security.PrivateKey generatePrivate(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement)) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPrivateKey(privateKeyInfo);
        }
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.dhpublicnumber)) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPrivateKey(privateKeyInfo);
        }
        throw new java.io.IOException("algorithm identifier " + algorithm + " in key not recognised");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
    public java.security.PublicKey generatePublic(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = subjectPublicKeyInfo.getAlgorithm().getAlgorithm();
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement)) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey(subjectPublicKeyInfo);
        }
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.dhpublicnumber)) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey(subjectPublicKeyInfo);
        }
        throw new java.io.IOException("algorithm identifier " + algorithm + " in key not recognised");
    }
}
