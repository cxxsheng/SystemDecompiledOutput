package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class DHParametersGenerator {
    private static final java.math.BigInteger TWO = java.math.BigInteger.valueOf(2);
    private int certainty;
    private java.security.SecureRandom random;
    private int size;

    public void init(int i, int i2, java.security.SecureRandom secureRandom) {
        this.size = i;
        this.certainty = i2;
        this.random = secureRandom;
    }

    public com.android.internal.org.bouncycastle.crypto.params.DHParameters generateParameters() {
        java.math.BigInteger[] generateSafePrimes = com.android.internal.org.bouncycastle.crypto.generators.DHParametersHelper.generateSafePrimes(this.size, this.certainty, this.random);
        java.math.BigInteger bigInteger = generateSafePrimes[0];
        java.math.BigInteger bigInteger2 = generateSafePrimes[1];
        return new com.android.internal.org.bouncycastle.crypto.params.DHParameters(bigInteger, com.android.internal.org.bouncycastle.crypto.generators.DHParametersHelper.selectGenerator(bigInteger, bigInteger2, this.random), bigInteger2, TWO, (com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters) null);
    }
}
