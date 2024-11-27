package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
public class BaseKeyGenerator extends javax.crypto.KeyGeneratorSpi {
    protected java.lang.String algName;
    protected int defaultKeySize;
    protected com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator engine;
    protected int keySize;
    protected boolean uninitialised = true;

    protected BaseKeyGenerator(java.lang.String str, int i, com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator cipherKeyGenerator) {
        this.algName = str;
        this.defaultKeySize = i;
        this.keySize = i;
        this.engine = cipherKeyGenerator;
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidAlgorithmParameterException {
        throw new java.security.InvalidAlgorithmParameterException("Not Implemented");
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(java.security.SecureRandom secureRandom) {
        if (secureRandom != null) {
            this.engine.init(new com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters(secureRandom, this.defaultKeySize));
            this.uninitialised = false;
        }
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(int i, java.security.SecureRandom secureRandom) {
        if (secureRandom == null) {
            try {
                secureRandom = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
            } catch (java.lang.IllegalArgumentException e) {
                throw new java.security.InvalidParameterException(e.getMessage());
            }
        }
        this.engine.init(new com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters(secureRandom, i));
        this.uninitialised = false;
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected javax.crypto.SecretKey engineGenerateKey() {
        if (this.uninitialised) {
            this.engine.init(new com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters(com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom(), this.defaultKeySize));
            this.uninitialised = false;
        }
        return new javax.crypto.spec.SecretKeySpec(this.engine.generateKey(), this.algName);
    }
}
