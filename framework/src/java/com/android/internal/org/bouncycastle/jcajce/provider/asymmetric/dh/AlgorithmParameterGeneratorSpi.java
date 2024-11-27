package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh;

/* loaded from: classes4.dex */
public class AlgorithmParameterGeneratorSpi extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi {
    protected java.security.SecureRandom random;
    protected int strength = 2048;
    private int l = 0;

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(int i, java.security.SecureRandom secureRandom) {
        this.strength = i;
        this.random = secureRandom;
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof javax.crypto.spec.DHGenParameterSpec)) {
            throw new java.security.InvalidAlgorithmParameterException("DH parameter generator requires a DHGenParameterSpec for initialisation");
        }
        javax.crypto.spec.DHGenParameterSpec dHGenParameterSpec = (javax.crypto.spec.DHGenParameterSpec) algorithmParameterSpec;
        this.strength = dHGenParameterSpec.getPrimeSize();
        this.l = dHGenParameterSpec.getExponentSize();
        this.random = secureRandom;
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected java.security.AlgorithmParameters engineGenerateParameters() {
        com.android.internal.org.bouncycastle.crypto.generators.DHParametersGenerator dHParametersGenerator = new com.android.internal.org.bouncycastle.crypto.generators.DHParametersGenerator();
        dHParametersGenerator.init(this.strength, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator.getDefaultCertainty(this.strength), com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom(this.random));
        com.android.internal.org.bouncycastle.crypto.params.DHParameters generateParameters = dHParametersGenerator.generateParameters();
        try {
            java.security.AlgorithmParameters createParametersInstance = createParametersInstance("DH");
            createParametersInstance.init(new javax.crypto.spec.DHParameterSpec(generateParameters.getP(), generateParameters.getG(), this.l));
            return createParametersInstance;
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }
}
