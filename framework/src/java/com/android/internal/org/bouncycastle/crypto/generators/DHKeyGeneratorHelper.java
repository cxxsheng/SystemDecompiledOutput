package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
class DHKeyGeneratorHelper {
    static final com.android.internal.org.bouncycastle.crypto.generators.DHKeyGeneratorHelper INSTANCE = new com.android.internal.org.bouncycastle.crypto.generators.DHKeyGeneratorHelper();
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private static final java.math.BigInteger TWO = java.math.BigInteger.valueOf(2);

    private DHKeyGeneratorHelper() {
    }

    java.math.BigInteger calculatePrivate(com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters, java.security.SecureRandom secureRandom) {
        java.math.BigInteger createRandomInRange;
        java.math.BigInteger bit;
        int l = dHParameters.getL();
        if (l != 0) {
            int i = l >>> 2;
            do {
                bit = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomBigInteger(l, secureRandom).setBit(l - 1);
            } while (com.android.internal.org.bouncycastle.math.ec.WNafUtil.getNafWeight(bit) < i);
            return bit;
        }
        java.math.BigInteger bigInteger = TWO;
        int m = dHParameters.getM();
        if (m != 0) {
            bigInteger = ONE.shiftLeft(m - 1);
        }
        java.math.BigInteger q = dHParameters.getQ();
        if (q == null) {
            q = dHParameters.getP();
        }
        java.math.BigInteger subtract = q.subtract(TWO);
        int bitLength = subtract.bitLength() >>> 2;
        do {
            createRandomInRange = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomInRange(bigInteger, subtract, secureRandom);
        } while (com.android.internal.org.bouncycastle.math.ec.WNafUtil.getNafWeight(createRandomInRange) < bitLength);
        return createRandomInRange;
    }

    java.math.BigInteger calculatePublic(com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters, java.math.BigInteger bigInteger) {
        return dHParameters.getG().modPow(bigInteger, dHParameters.getP());
    }
}
