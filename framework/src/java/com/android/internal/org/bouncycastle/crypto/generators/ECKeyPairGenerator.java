package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class ECKeyPairGenerator implements com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator, com.android.internal.org.bouncycastle.math.ec.ECConstants {
    com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters params;
    java.security.SecureRandom random;

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public void init(com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters keyGenerationParameters) {
        com.android.internal.org.bouncycastle.crypto.params.ECKeyGenerationParameters eCKeyGenerationParameters = (com.android.internal.org.bouncycastle.crypto.params.ECKeyGenerationParameters) keyGenerationParameters;
        this.random = eCKeyGenerationParameters.getRandom();
        this.params = eCKeyGenerationParameters.getDomainParameters();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair generateKeyPair() {
        java.math.BigInteger n = this.params.getN();
        int bitLength = n.bitLength();
        int i = bitLength >>> 2;
        while (true) {
            java.math.BigInteger createRandomBigInteger = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomBigInteger(bitLength, this.random);
            if (createRandomBigInteger.compareTo(ONE) >= 0 && createRandomBigInteger.compareTo(n) < 0 && com.android.internal.org.bouncycastle.math.ec.WNafUtil.getNafWeight(createRandomBigInteger) >= i) {
                return new com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair((com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) new com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters(createBasePointMultiplier().multiply(this.params.getG(), createRandomBigInteger), this.params), (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) new com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters(createRandomBigInteger, this.params));
            }
        }
    }

    protected com.android.internal.org.bouncycastle.math.ec.ECMultiplier createBasePointMultiplier() {
        return new com.android.internal.org.bouncycastle.math.ec.FixedPointCombMultiplier();
    }
}
