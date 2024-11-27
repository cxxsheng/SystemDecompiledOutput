package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

/* loaded from: classes4.dex */
public class KeyPairGeneratorSpi extends java.security.KeyPairGenerator {
    private static final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier PKCS_ALGID = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    private static final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier PSS_ALGID = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS);
    static final java.math.BigInteger defaultPublicExponent = java.math.BigInteger.valueOf(65537);
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algId;
    com.android.internal.org.bouncycastle.crypto.generators.RSAKeyPairGenerator engine;
    com.android.internal.org.bouncycastle.crypto.params.RSAKeyGenerationParameters param;

    public KeyPairGeneratorSpi(java.lang.String str, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        super(str);
        this.algId = algorithmIdentifier;
        this.engine = new com.android.internal.org.bouncycastle.crypto.generators.RSAKeyPairGenerator();
        this.param = new com.android.internal.org.bouncycastle.crypto.params.RSAKeyGenerationParameters(defaultPublicExponent, com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom(), 2048, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator.getDefaultCertainty(2048));
        this.engine.init(this.param);
    }

    public KeyPairGeneratorSpi() {
        this(android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA, PKCS_ALGID);
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(int i, java.security.SecureRandom secureRandom) {
        java.math.BigInteger bigInteger = defaultPublicExponent;
        if (secureRandom == null) {
            secureRandom = new java.security.SecureRandom();
        }
        this.param = new com.android.internal.org.bouncycastle.crypto.params.RSAKeyGenerationParameters(bigInteger, secureRandom, i, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator.getDefaultCertainty(i));
        this.engine.init(this.param);
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof java.security.spec.RSAKeyGenParameterSpec)) {
            throw new java.security.InvalidAlgorithmParameterException("parameter object not a RSAKeyGenParameterSpec");
        }
        java.security.spec.RSAKeyGenParameterSpec rSAKeyGenParameterSpec = (java.security.spec.RSAKeyGenParameterSpec) algorithmParameterSpec;
        java.math.BigInteger publicExponent = rSAKeyGenParameterSpec.getPublicExponent();
        if (secureRandom == null) {
            secureRandom = new java.security.SecureRandom();
        }
        this.param = new com.android.internal.org.bouncycastle.crypto.params.RSAKeyGenerationParameters(publicExponent, secureRandom, rSAKeyGenParameterSpec.getKeysize(), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator.getDefaultCertainty(2048));
        this.engine.init(this.param);
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public java.security.KeyPair generateKeyPair() {
        com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair generateKeyPair = this.engine.generateKeyPair();
        return new java.security.KeyPair(new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey(this.algId, (com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters) generateKeyPair.getPublic()), new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey(this.algId, (com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters) generateKeyPair.getPrivate()));
    }

    public static class PSS extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi {
        public PSS() {
            super("RSASSA-PSS", com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi.PSS_ALGID);
        }
    }
}
