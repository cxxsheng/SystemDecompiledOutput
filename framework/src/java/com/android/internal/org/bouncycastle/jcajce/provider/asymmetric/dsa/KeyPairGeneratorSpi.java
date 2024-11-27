package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa;

/* loaded from: classes4.dex */
public class KeyPairGeneratorSpi extends java.security.KeyPairGenerator {
    com.android.internal.org.bouncycastle.crypto.generators.DSAKeyPairGenerator engine;
    boolean initialised;
    com.android.internal.org.bouncycastle.crypto.params.DSAKeyGenerationParameters param;
    java.security.SecureRandom random;
    int strength;
    private static java.util.Hashtable params = new java.util.Hashtable();
    private static java.lang.Object lock = new java.lang.Object();

    public KeyPairGeneratorSpi() {
        super("DSA");
        this.engine = new com.android.internal.org.bouncycastle.crypto.generators.DSAKeyPairGenerator();
        this.strength = 1024;
        this.random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
        this.initialised = false;
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(int i, java.security.SecureRandom secureRandom) {
        if (i < 512 || i > 4096 || ((i < 1024 && i % 64 != 0) || (i >= 1024 && i % 1024 != 0))) {
            throw new java.security.InvalidParameterException("strength must be from 512 - 4096 and a multiple of 1024 above 1024");
        }
        if (secureRandom == null) {
            secureRandom = new java.security.SecureRandom();
        }
        java.security.spec.DSAParameterSpec dSADefaultParameters = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION.getDSADefaultParameters(i);
        if (dSADefaultParameters != null) {
            this.param = new com.android.internal.org.bouncycastle.crypto.params.DSAKeyGenerationParameters(secureRandom, new com.android.internal.org.bouncycastle.crypto.params.DSAParameters(dSADefaultParameters.getP(), dSADefaultParameters.getQ(), dSADefaultParameters.getG()));
            this.engine.init(this.param);
            this.initialised = true;
        } else {
            this.strength = i;
            this.random = secureRandom;
            this.initialised = false;
        }
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof java.security.spec.DSAParameterSpec)) {
            throw new java.security.InvalidAlgorithmParameterException("parameter object not a DSAParameterSpec");
        }
        java.security.spec.DSAParameterSpec dSAParameterSpec = (java.security.spec.DSAParameterSpec) algorithmParameterSpec;
        if (secureRandom == null) {
            secureRandom = new java.security.SecureRandom();
        }
        this.param = new com.android.internal.org.bouncycastle.crypto.params.DSAKeyGenerationParameters(secureRandom, new com.android.internal.org.bouncycastle.crypto.params.DSAParameters(dSAParameterSpec.getP(), dSAParameterSpec.getQ(), dSAParameterSpec.getG()));
        this.engine.init(this.param);
        this.initialised = true;
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public java.security.KeyPair generateKeyPair() {
        com.android.internal.org.bouncycastle.crypto.generators.DSAParametersGenerator dSAParametersGenerator;
        if (!this.initialised) {
            java.lang.Integer valueOf = com.android.internal.org.bouncycastle.util.Integers.valueOf(this.strength);
            if (params.containsKey(valueOf)) {
                this.param = (com.android.internal.org.bouncycastle.crypto.params.DSAKeyGenerationParameters) params.get(valueOf);
            } else {
                synchronized (lock) {
                    if (params.containsKey(valueOf)) {
                        this.param = (com.android.internal.org.bouncycastle.crypto.params.DSAKeyGenerationParameters) params.get(valueOf);
                    } else {
                        int defaultCertainty = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator.getDefaultCertainty(this.strength);
                        if (this.strength == 1024) {
                            dSAParametersGenerator = new com.android.internal.org.bouncycastle.crypto.generators.DSAParametersGenerator();
                            if (com.android.internal.org.bouncycastle.util.Properties.isOverrideSet("com.android.internal.org.bouncycastle.dsa.FIPS186-2for1024bits")) {
                                dSAParametersGenerator.init(this.strength, defaultCertainty, this.random);
                            } else {
                                dSAParametersGenerator.init(new com.android.internal.org.bouncycastle.crypto.params.DSAParameterGenerationParameters(1024, 160, defaultCertainty, this.random));
                            }
                        } else if (this.strength > 1024) {
                            com.android.internal.org.bouncycastle.crypto.params.DSAParameterGenerationParameters dSAParameterGenerationParameters = new com.android.internal.org.bouncycastle.crypto.params.DSAParameterGenerationParameters(this.strength, 256, defaultCertainty, this.random);
                            com.android.internal.org.bouncycastle.crypto.generators.DSAParametersGenerator dSAParametersGenerator2 = new com.android.internal.org.bouncycastle.crypto.generators.DSAParametersGenerator(new com.android.internal.org.bouncycastle.crypto.digests.SHA256Digest());
                            dSAParametersGenerator2.init(dSAParameterGenerationParameters);
                            dSAParametersGenerator = dSAParametersGenerator2;
                        } else {
                            dSAParametersGenerator = new com.android.internal.org.bouncycastle.crypto.generators.DSAParametersGenerator();
                            dSAParametersGenerator.init(this.strength, defaultCertainty, this.random);
                        }
                        this.param = new com.android.internal.org.bouncycastle.crypto.params.DSAKeyGenerationParameters(this.random, dSAParametersGenerator.generateParameters());
                        params.put(valueOf, this.param);
                    }
                }
            }
            this.engine.init(this.param);
            this.initialised = true;
        }
        com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair generateKeyPair = this.engine.generateKeyPair();
        return new java.security.KeyPair(new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPublicKey((com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters) generateKeyPair.getPublic()), new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPrivateKey((com.android.internal.org.bouncycastle.crypto.params.DSAPrivateKeyParameters) generateKeyPair.getPrivate()));
    }
}
