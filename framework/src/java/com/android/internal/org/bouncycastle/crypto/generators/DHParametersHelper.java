package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
class DHParametersHelper {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(com.android.internal.org.bouncycastle.crypto.generators.DHParametersHelper.class.getName());
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private static final java.math.BigInteger TWO = java.math.BigInteger.valueOf(2);

    DHParametersHelper() {
    }

    static java.math.BigInteger[] generateSafePrimes(int i, int i2, java.security.SecureRandom secureRandom) {
        logger.info("Generating safe primes. This may take a long time.");
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        int i3 = i - 1;
        int i4 = i >>> 2;
        int i5 = 0;
        while (true) {
            i5++;
            java.math.BigInteger createRandomPrime = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomPrime(i3, 2, secureRandom);
            java.math.BigInteger add = createRandomPrime.shiftLeft(1).add(ONE);
            if (add.isProbablePrime(i2) && (i2 <= 2 || createRandomPrime.isProbablePrime(i2 - 2))) {
                if (com.android.internal.org.bouncycastle.math.ec.WNafUtil.getNafWeight(add) >= i4) {
                    logger.info("Generated safe primes: " + i5 + " tries took " + (java.lang.System.currentTimeMillis() - currentTimeMillis) + "ms");
                    return new java.math.BigInteger[]{add, createRandomPrime};
                }
            }
        }
    }

    static java.math.BigInteger selectGenerator(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.security.SecureRandom secureRandom) {
        java.math.BigInteger modPow;
        java.math.BigInteger subtract = bigInteger.subtract(TWO);
        do {
            modPow = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomInRange(TWO, subtract, secureRandom).modPow(TWO, bigInteger);
        } while (modPow.equals(ONE));
        return modPow;
    }
}
