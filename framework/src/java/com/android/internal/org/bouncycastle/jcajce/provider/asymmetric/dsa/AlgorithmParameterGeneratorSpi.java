package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa;

/* loaded from: classes4.dex */
public class AlgorithmParameterGeneratorSpi extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi {
    protected com.android.internal.org.bouncycastle.crypto.params.DSAParameterGenerationParameters params;
    protected java.security.SecureRandom random;
    protected int strength = 1024;

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(int i, java.security.SecureRandom secureRandom) {
        if (i < 512 || i > 3072) {
            throw new java.security.InvalidParameterException("strength must be from 512 - 3072");
        }
        if (i <= 1024 && i % 64 != 0) {
            throw new java.security.InvalidParameterException("strength must be a multiple of 64 below 1024 bits.");
        }
        if (i > 1024 && i % 1024 != 0) {
            throw new java.security.InvalidParameterException("strength must be a multiple of 1024 above 1024 bits.");
        }
        this.strength = i;
        this.random = secureRandom;
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidAlgorithmParameterException {
        throw new java.security.InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DSA parameter generation.");
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected java.security.AlgorithmParameters engineGenerateParameters() {
        com.android.internal.org.bouncycastle.crypto.generators.DSAParametersGenerator dSAParametersGenerator;
        if (this.strength <= 1024) {
            dSAParametersGenerator = new com.android.internal.org.bouncycastle.crypto.generators.DSAParametersGenerator();
        } else {
            dSAParametersGenerator = new com.android.internal.org.bouncycastle.crypto.generators.DSAParametersGenerator(new com.android.internal.org.bouncycastle.crypto.digests.SHA256Digest());
        }
        if (this.random == null) {
            this.random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
        }
        int defaultCertainty = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator.getDefaultCertainty(this.strength);
        if (this.strength == 1024) {
            this.params = new com.android.internal.org.bouncycastle.crypto.params.DSAParameterGenerationParameters(1024, 160, defaultCertainty, this.random);
            dSAParametersGenerator.init(this.params);
        } else if (this.strength > 1024) {
            this.params = new com.android.internal.org.bouncycastle.crypto.params.DSAParameterGenerationParameters(this.strength, 256, defaultCertainty, this.random);
            dSAParametersGenerator.init(this.params);
        } else {
            dSAParametersGenerator.init(this.strength, defaultCertainty, this.random);
        }
        com.android.internal.org.bouncycastle.crypto.params.DSAParameters generateParameters = dSAParametersGenerator.generateParameters();
        try {
            java.security.AlgorithmParameters createParametersInstance = createParametersInstance("DSA");
            createParametersInstance.init(new java.security.spec.DSAParameterSpec(generateParameters.getP(), generateParameters.getQ(), generateParameters.getG()));
            return createParametersInstance;
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }
}
