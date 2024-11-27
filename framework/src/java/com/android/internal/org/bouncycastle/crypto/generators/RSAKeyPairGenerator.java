package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class RSAKeyPairGenerator implements com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator {
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private com.android.internal.org.bouncycastle.crypto.params.RSAKeyGenerationParameters param;

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public void init(com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters keyGenerationParameters) {
        this.param = (com.android.internal.org.bouncycastle.crypto.params.RSAKeyGenerationParameters) keyGenerationParameters;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair generateKeyPair() {
        java.math.BigInteger chooseRandomPrime;
        java.math.BigInteger multiply;
        java.math.BigInteger bigInteger;
        com.android.internal.org.bouncycastle.crypto.generators.RSAKeyPairGenerator rSAKeyPairGenerator = this;
        int strength = rSAKeyPairGenerator.param.getStrength();
        int i = (strength + 1) / 2;
        int i2 = strength - i;
        int i3 = strength / 2;
        int i4 = i3 - 100;
        int i5 = strength / 3;
        if (i4 < i5) {
            i4 = i5;
        }
        int i6 = strength >> 2;
        java.math.BigInteger pow = java.math.BigInteger.valueOf(2L).pow(i3);
        java.math.BigInteger shiftLeft = ONE.shiftLeft(strength - 1);
        java.math.BigInteger shiftLeft2 = ONE.shiftLeft(i4);
        com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair asymmetricCipherKeyPair = null;
        boolean z = false;
        while (!z) {
            java.math.BigInteger publicExponent = rSAKeyPairGenerator.param.getPublicExponent();
            java.math.BigInteger chooseRandomPrime2 = rSAKeyPairGenerator.chooseRandomPrime(i, publicExponent, shiftLeft);
            while (true) {
                chooseRandomPrime = rSAKeyPairGenerator.chooseRandomPrime(i2, publicExponent, shiftLeft);
                java.math.BigInteger abs = chooseRandomPrime.subtract(chooseRandomPrime2).abs();
                if (abs.bitLength() < i4 || abs.compareTo(shiftLeft2) <= 0) {
                    rSAKeyPairGenerator = this;
                    strength = strength;
                } else {
                    multiply = chooseRandomPrime2.multiply(chooseRandomPrime);
                    if (multiply.bitLength() != strength) {
                        chooseRandomPrime2 = chooseRandomPrime2.max(chooseRandomPrime);
                    } else {
                        if (com.android.internal.org.bouncycastle.math.ec.WNafUtil.getNafWeight(multiply) >= i6) {
                            break;
                        }
                        chooseRandomPrime2 = rSAKeyPairGenerator.chooseRandomPrime(i, publicExponent, shiftLeft);
                    }
                }
            }
            if (chooseRandomPrime2.compareTo(chooseRandomPrime) >= 0) {
                bigInteger = chooseRandomPrime;
            } else {
                bigInteger = chooseRandomPrime2;
                chooseRandomPrime2 = chooseRandomPrime;
            }
            java.math.BigInteger subtract = chooseRandomPrime2.subtract(ONE);
            java.math.BigInteger subtract2 = bigInteger.subtract(ONE);
            int i7 = strength;
            java.math.BigInteger modInverse = publicExponent.modInverse(subtract.divide(subtract.gcd(subtract2)).multiply(subtract2));
            if (modInverse.compareTo(pow) <= 0) {
                rSAKeyPairGenerator = this;
                strength = i7;
            } else {
                asymmetricCipherKeyPair = new com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair((com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(false, multiply, publicExponent), (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) new com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters(multiply, publicExponent, modInverse, chooseRandomPrime2, bigInteger, modInverse.remainder(subtract), modInverse.remainder(subtract2), com.android.internal.org.bouncycastle.util.BigIntegers.modOddInverse(chooseRandomPrime2, bigInteger)));
                z = true;
                strength = i7;
                rSAKeyPairGenerator = this;
            }
        }
        return asymmetricCipherKeyPair;
    }

    protected java.math.BigInteger chooseRandomPrime(int i, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        for (int i2 = 0; i2 != i * 5; i2++) {
            java.math.BigInteger createRandomPrime = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomPrime(i, 1, this.param.getRandom());
            if (!createRandomPrime.mod(bigInteger).equals(ONE) && createRandomPrime.multiply(createRandomPrime).compareTo(bigInteger2) >= 0 && isProbablePrime(createRandomPrime) && bigInteger.gcd(createRandomPrime.subtract(ONE)).equals(ONE)) {
                return createRandomPrime;
            }
        }
        throw new java.lang.IllegalStateException("unable to generate prime number for RSA key");
    }

    protected boolean isProbablePrime(java.math.BigInteger bigInteger) {
        return !com.android.internal.org.bouncycastle.math.Primes.hasAnySmallFactors(bigInteger) && com.android.internal.org.bouncycastle.math.Primes.isMRProbablePrime(bigInteger, this.param.getRandom(), getNumberOfIterations(bigInteger.bitLength(), this.param.getCertainty()));
    }

    private static int getNumberOfIterations(int i, int i2) {
        if (i >= 1536) {
            if (i2 <= 100) {
                return 3;
            }
            if (i2 <= 128) {
                return 4;
            }
            return 4 + (((i2 - 128) + 1) / 2);
        }
        if (i >= 1024) {
            if (i2 <= 100) {
                return 4;
            }
            if (i2 <= 112) {
                return 5;
            }
            return (((i2 - 112) + 1) / 2) + 5;
        }
        if (i < 512) {
            if (i2 <= 80) {
                return 40;
            }
            return 40 + (((i2 - 80) + 1) / 2);
        }
        if (i2 <= 80) {
            return 5;
        }
        if (i2 <= 100) {
            return 7;
        }
        return 7 + (((i2 - 100) + 1) / 2);
    }
}
