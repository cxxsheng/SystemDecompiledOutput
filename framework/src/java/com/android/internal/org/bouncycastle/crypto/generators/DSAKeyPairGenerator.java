package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class DSAKeyPairGenerator implements com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator {
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private com.android.internal.org.bouncycastle.crypto.params.DSAKeyGenerationParameters param;

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public void init(com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters keyGenerationParameters) {
        this.param = (com.android.internal.org.bouncycastle.crypto.params.DSAKeyGenerationParameters) keyGenerationParameters;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair generateKeyPair() {
        com.android.internal.org.bouncycastle.crypto.params.DSAParameters parameters = this.param.getParameters();
        java.math.BigInteger generatePrivateKey = generatePrivateKey(parameters.getQ(), this.param.getRandom());
        return new com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair((com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) new com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters(calculatePublicKey(parameters.getP(), parameters.getG(), generatePrivateKey), parameters), (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) new com.android.internal.org.bouncycastle.crypto.params.DSAPrivateKeyParameters(generatePrivateKey, parameters));
    }

    private static java.math.BigInteger generatePrivateKey(java.math.BigInteger bigInteger, java.security.SecureRandom secureRandom) {
        java.math.BigInteger createRandomInRange;
        int bitLength = bigInteger.bitLength() >>> 2;
        do {
            createRandomInRange = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomInRange(ONE, bigInteger.subtract(ONE), secureRandom);
        } while (com.android.internal.org.bouncycastle.math.ec.WNafUtil.getNafWeight(createRandomInRange) < bitLength);
        return createRandomInRange;
    }

    private static java.math.BigInteger calculatePublicKey(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3) {
        return bigInteger2.modPow(bigInteger3, bigInteger);
    }
}
