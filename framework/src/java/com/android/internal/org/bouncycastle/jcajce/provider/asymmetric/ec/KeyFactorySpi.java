package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec;

/* loaded from: classes4.dex */
public class KeyFactorySpi extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi implements com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter {
    java.lang.String algorithm;
    com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration configuration;

    KeyFactorySpi(java.lang.String str, com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration providerConfiguration) {
        this.algorithm = str;
        this.configuration = providerConfiguration;
    }

    @Override // java.security.KeyFactorySpi
    protected java.security.Key engineTranslateKey(java.security.Key key) throws java.security.InvalidKeyException {
        if (key instanceof java.security.interfaces.ECPublicKey) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey((java.security.interfaces.ECPublicKey) key, this.configuration);
        }
        if (key instanceof java.security.interfaces.ECPrivateKey) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey((java.security.interfaces.ECPrivateKey) key, this.configuration);
        }
        throw new java.security.InvalidKeyException("key type unknown");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.spec.KeySpec engineGetKeySpec(java.security.Key key, java.lang.Class cls) throws java.security.spec.InvalidKeySpecException {
        if ((cls.isAssignableFrom(java.security.spec.KeySpec.class) || cls.isAssignableFrom(java.security.spec.ECPublicKeySpec.class)) && (key instanceof java.security.interfaces.ECPublicKey)) {
            java.security.interfaces.ECPublicKey eCPublicKey = (java.security.interfaces.ECPublicKey) key;
            if (eCPublicKey.getParams() != null) {
                return new java.security.spec.ECPublicKeySpec(eCPublicKey.getW(), eCPublicKey.getParams());
            }
            com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec ecImplicitlyCa = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            return new java.security.spec.ECPublicKeySpec(eCPublicKey.getW(), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getSeed()), ecImplicitlyCa));
        }
        if ((cls.isAssignableFrom(java.security.spec.KeySpec.class) || cls.isAssignableFrom(java.security.spec.ECPrivateKeySpec.class)) && (key instanceof java.security.interfaces.ECPrivateKey)) {
            java.security.interfaces.ECPrivateKey eCPrivateKey = (java.security.interfaces.ECPrivateKey) key;
            if (eCPrivateKey.getParams() != null) {
                return new java.security.spec.ECPrivateKeySpec(eCPrivateKey.getS(), eCPrivateKey.getParams());
            }
            com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec ecImplicitlyCa2 = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            return new java.security.spec.ECPrivateKeySpec(eCPrivateKey.getS(), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(ecImplicitlyCa2.getCurve(), ecImplicitlyCa2.getSeed()), ecImplicitlyCa2));
        }
        if (cls.isAssignableFrom(com.android.internal.org.bouncycastle.jce.spec.ECPublicKeySpec.class) && (key instanceof java.security.interfaces.ECPublicKey)) {
            java.security.interfaces.ECPublicKey eCPublicKey2 = (java.security.interfaces.ECPublicKey) key;
            if (eCPublicKey2.getParams() != null) {
                return new com.android.internal.org.bouncycastle.jce.spec.ECPublicKeySpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(eCPublicKey2.getParams(), eCPublicKey2.getW()), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(eCPublicKey2.getParams()));
            }
            return new com.android.internal.org.bouncycastle.jce.spec.ECPublicKeySpec(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(eCPublicKey2.getParams(), eCPublicKey2.getW()), com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa());
        }
        if (cls.isAssignableFrom(com.android.internal.org.bouncycastle.jce.spec.ECPrivateKeySpec.class) && (key instanceof java.security.interfaces.ECPrivateKey)) {
            java.security.interfaces.ECPrivateKey eCPrivateKey2 = (java.security.interfaces.ECPrivateKey) key;
            if (eCPrivateKey2.getParams() != null) {
                return new com.android.internal.org.bouncycastle.jce.spec.ECPrivateKeySpec(eCPrivateKey2.getS(), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec(eCPrivateKey2.getParams()));
            }
            return new com.android.internal.org.bouncycastle.jce.spec.ECPrivateKeySpec(eCPrivateKey2.getS(), com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa());
        }
        return super.engineGetKeySpec(key, cls);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.PrivateKey engineGeneratePrivate(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        if (keySpec instanceof com.android.internal.org.bouncycastle.jce.spec.ECPrivateKeySpec) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey(this.algorithm, (com.android.internal.org.bouncycastle.jce.spec.ECPrivateKeySpec) keySpec, this.configuration);
        }
        if (keySpec instanceof java.security.spec.ECPrivateKeySpec) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey(this.algorithm, (java.security.spec.ECPrivateKeySpec) keySpec, this.configuration);
        }
        return super.engineGeneratePrivate(keySpec);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected java.security.PublicKey engineGeneratePublic(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
        try {
            if (keySpec instanceof com.android.internal.org.bouncycastle.jce.spec.ECPublicKeySpec) {
                return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey(this.algorithm, (com.android.internal.org.bouncycastle.jce.spec.ECPublicKeySpec) keySpec, this.configuration);
            }
            if (keySpec instanceof java.security.spec.ECPublicKeySpec) {
                return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey(this.algorithm, (java.security.spec.ECPublicKeySpec) keySpec, this.configuration);
            }
            return super.engineGeneratePublic(keySpec);
        } catch (java.lang.Exception e) {
            throw new java.security.spec.InvalidKeySpecException("invalid KeySpec: " + e.getMessage(), e);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
    public java.security.PrivateKey generatePrivate(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey)) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey(this.algorithm, privateKeyInfo, this.configuration);
        }
        throw new java.io.IOException("algorithm identifier " + algorithm + " in key not recognised");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
    public java.security.PublicKey generatePublic(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = subjectPublicKeyInfo.getAlgorithm().getAlgorithm();
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey)) {
            return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey(this.algorithm, subjectPublicKeyInfo, this.configuration);
        }
        throw new java.io.IOException("algorithm identifier " + algorithm + " in key not recognised");
    }

    public static class EC extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi {
        public EC() {
            super(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC, com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDSA extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi {
        public ECDSA() {
            super("ECDSA", com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDH extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi {
        public ECDH() {
            super("ECDH", com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDHC extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi {
        public ECDHC() {
            super("ECDHC", com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECMQV extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi {
        public ECMQV() {
            super("ECMQV", com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION);
        }
    }
}
