package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh;

/* loaded from: classes4.dex */
public class KeyPairGeneratorSpi extends java.security.KeyPairGenerator {
    com.android.internal.org.bouncycastle.crypto.generators.DHBasicKeyPairGenerator engine;
    boolean initialised;
    com.android.internal.org.bouncycastle.crypto.params.DHKeyGenerationParameters param;
    java.security.SecureRandom random;
    int strength;
    private static java.util.Hashtable params = new java.util.Hashtable();
    private static java.lang.Object lock = new java.lang.Object();

    public KeyPairGeneratorSpi() {
        super("DH");
        this.engine = new com.android.internal.org.bouncycastle.crypto.generators.DHBasicKeyPairGenerator();
        this.strength = 2048;
        this.random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
        this.initialised = false;
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(int i, java.security.SecureRandom secureRandom) {
        this.strength = i;
        this.random = secureRandom;
        this.initialised = false;
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof javax.crypto.spec.DHParameterSpec)) {
            throw new java.security.InvalidAlgorithmParameterException("parameter object not a DHParameterSpec");
        }
        try {
            this.param = convertParams(secureRandom, (javax.crypto.spec.DHParameterSpec) algorithmParameterSpec);
            this.engine.init(this.param);
            this.initialised = true;
        } catch (java.lang.IllegalArgumentException e) {
            throw new java.security.InvalidAlgorithmParameterException(e.getMessage(), e);
        }
    }

    private com.android.internal.org.bouncycastle.crypto.params.DHKeyGenerationParameters convertParams(java.security.SecureRandom secureRandom, javax.crypto.spec.DHParameterSpec dHParameterSpec) {
        return new com.android.internal.org.bouncycastle.crypto.params.DHKeyGenerationParameters(secureRandom, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(dHParameterSpec.getP(), dHParameterSpec.getG(), null, dHParameterSpec.getL()));
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public java.security.KeyPair generateKeyPair() {
        if (!this.initialised) {
            java.lang.Integer valueOf = com.android.internal.org.bouncycastle.util.Integers.valueOf(this.strength);
            if (params.containsKey(valueOf)) {
                this.param = (com.android.internal.org.bouncycastle.crypto.params.DHKeyGenerationParameters) params.get(valueOf);
            } else {
                javax.crypto.spec.DHParameterSpec dHDefaultParameters = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getDHDefaultParameters(this.strength);
                if (dHDefaultParameters != null) {
                    this.param = convertParams(this.random, dHDefaultParameters);
                } else {
                    synchronized (lock) {
                        if (params.containsKey(valueOf)) {
                            this.param = (com.android.internal.org.bouncycastle.crypto.params.DHKeyGenerationParameters) params.get(valueOf);
                        } else {
                            com.android.internal.org.bouncycastle.crypto.generators.DHParametersGenerator dHParametersGenerator = new com.android.internal.org.bouncycastle.crypto.generators.DHParametersGenerator();
                            dHParametersGenerator.init(this.strength, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator.getDefaultCertainty(this.strength), this.random);
                            this.param = new com.android.internal.org.bouncycastle.crypto.params.DHKeyGenerationParameters(this.random, dHParametersGenerator.generateParameters());
                            params.put(valueOf, this.param);
                        }
                    }
                }
            }
            this.engine.init(this.param);
            this.initialised = true;
        }
        com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair generateKeyPair = this.engine.generateKeyPair();
        return new java.security.KeyPair(new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey((com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters) generateKeyPair.getPublic()), new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPrivateKey((com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters) generateKeyPair.getPrivate()));
    }
}
