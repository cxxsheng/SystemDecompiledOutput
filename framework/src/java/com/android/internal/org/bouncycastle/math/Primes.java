package com.android.internal.org.bouncycastle.math;

/* loaded from: classes4.dex */
public abstract class Primes {
    public static final int SMALL_FACTOR_LIMIT = 211;
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private static final java.math.BigInteger TWO = java.math.BigInteger.valueOf(2);
    private static final java.math.BigInteger THREE = java.math.BigInteger.valueOf(3);

    public static class MROutput {
        private java.math.BigInteger factor;
        private boolean provablyComposite;

        /* JADX INFO: Access modifiers changed from: private */
        public static com.android.internal.org.bouncycastle.math.Primes.MROutput probablyPrime() {
            return new com.android.internal.org.bouncycastle.math.Primes.MROutput(false, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static com.android.internal.org.bouncycastle.math.Primes.MROutput provablyCompositeWithFactor(java.math.BigInteger bigInteger) {
            return new com.android.internal.org.bouncycastle.math.Primes.MROutput(true, bigInteger);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static com.android.internal.org.bouncycastle.math.Primes.MROutput provablyCompositeNotPrimePower() {
            return new com.android.internal.org.bouncycastle.math.Primes.MROutput(true, null);
        }

        private MROutput(boolean z, java.math.BigInteger bigInteger) {
            this.provablyComposite = z;
            this.factor = bigInteger;
        }

        public java.math.BigInteger getFactor() {
            return this.factor;
        }

        public boolean isProvablyComposite() {
            return this.provablyComposite;
        }

        public boolean isNotPrimePower() {
            return this.provablyComposite && this.factor == null;
        }
    }

    public static class STOutput {
        private java.math.BigInteger prime;
        private int primeGenCounter;
        private byte[] primeSeed;

        private STOutput(java.math.BigInteger bigInteger, byte[] bArr, int i) {
            this.prime = bigInteger;
            this.primeSeed = bArr;
            this.primeGenCounter = i;
        }

        public java.math.BigInteger getPrime() {
            return this.prime;
        }

        public byte[] getPrimeSeed() {
            return this.primeSeed;
        }

        public int getPrimeGenCounter() {
            return this.primeGenCounter;
        }
    }

    public static com.android.internal.org.bouncycastle.math.Primes.STOutput generateSTRandomPrime(com.android.internal.org.bouncycastle.crypto.Digest digest, int i, byte[] bArr) {
        if (digest == null) {
            throw new java.lang.IllegalArgumentException("'hash' cannot be null");
        }
        if (i < 2) {
            throw new java.lang.IllegalArgumentException("'length' must be >= 2");
        }
        if (bArr == null || bArr.length == 0) {
            throw new java.lang.IllegalArgumentException("'inputSeed' cannot be null or empty");
        }
        return implSTRandomPrime(digest, i, com.android.internal.org.bouncycastle.util.Arrays.clone(bArr));
    }

    public static com.android.internal.org.bouncycastle.math.Primes.MROutput enhancedMRProbablePrimeTest(java.math.BigInteger bigInteger, java.security.SecureRandom secureRandom, int i) {
        boolean z;
        java.math.BigInteger bigInteger2;
        checkCandidate(bigInteger, "candidate");
        if (secureRandom == null) {
            throw new java.lang.IllegalArgumentException("'random' cannot be null");
        }
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("'iterations' must be > 0");
        }
        if (bigInteger.bitLength() == 2) {
            return com.android.internal.org.bouncycastle.math.Primes.MROutput.probablyPrime();
        }
        if (!bigInteger.testBit(0)) {
            return com.android.internal.org.bouncycastle.math.Primes.MROutput.provablyCompositeWithFactor(TWO);
        }
        java.math.BigInteger subtract = bigInteger.subtract(ONE);
        java.math.BigInteger subtract2 = bigInteger.subtract(TWO);
        int lowestSetBit = subtract.getLowestSetBit();
        java.math.BigInteger shiftRight = subtract.shiftRight(lowestSetBit);
        for (int i2 = 0; i2 < i; i2++) {
            java.math.BigInteger createRandomInRange = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomInRange(TWO, subtract2, secureRandom);
            java.math.BigInteger gcd = createRandomInRange.gcd(bigInteger);
            if (gcd.compareTo(ONE) > 0) {
                return com.android.internal.org.bouncycastle.math.Primes.MROutput.provablyCompositeWithFactor(gcd);
            }
            java.math.BigInteger modPow = createRandomInRange.modPow(shiftRight, bigInteger);
            if (!modPow.equals(ONE) && !modPow.equals(subtract)) {
                int i3 = 1;
                while (true) {
                    if (i3 >= lowestSetBit) {
                        z = false;
                        bigInteger2 = modPow;
                        break;
                    }
                    bigInteger2 = modPow.modPow(TWO, bigInteger);
                    if (bigInteger2.equals(subtract)) {
                        z = true;
                        break;
                    }
                    if (!bigInteger2.equals(ONE)) {
                        i3++;
                        modPow = bigInteger2;
                    } else {
                        z = false;
                        break;
                    }
                }
                if (!z) {
                    if (!bigInteger2.equals(ONE)) {
                        modPow = bigInteger2.modPow(TWO, bigInteger);
                        if (modPow.equals(ONE)) {
                            modPow = bigInteger2;
                        }
                    }
                    java.math.BigInteger gcd2 = modPow.subtract(ONE).gcd(bigInteger);
                    if (gcd2.compareTo(ONE) > 0) {
                        return com.android.internal.org.bouncycastle.math.Primes.MROutput.provablyCompositeWithFactor(gcd2);
                    }
                    return com.android.internal.org.bouncycastle.math.Primes.MROutput.provablyCompositeNotPrimePower();
                }
            }
        }
        return com.android.internal.org.bouncycastle.math.Primes.MROutput.probablyPrime();
    }

    public static boolean hasAnySmallFactors(java.math.BigInteger bigInteger) {
        checkCandidate(bigInteger, "candidate");
        return implHasAnySmallFactors(bigInteger);
    }

    public static boolean isMRProbablePrime(java.math.BigInteger bigInteger, java.security.SecureRandom secureRandom, int i) {
        checkCandidate(bigInteger, "candidate");
        if (secureRandom == null) {
            throw new java.lang.IllegalArgumentException("'random' cannot be null");
        }
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("'iterations' must be > 0");
        }
        if (bigInteger.bitLength() == 2) {
            return true;
        }
        if (!bigInteger.testBit(0)) {
            return false;
        }
        java.math.BigInteger subtract = bigInteger.subtract(ONE);
        java.math.BigInteger subtract2 = bigInteger.subtract(TWO);
        int lowestSetBit = subtract.getLowestSetBit();
        java.math.BigInteger shiftRight = subtract.shiftRight(lowestSetBit);
        for (int i2 = 0; i2 < i; i2++) {
            if (!implMRProbablePrimeToBase(bigInteger, subtract, shiftRight, lowestSetBit, com.android.internal.org.bouncycastle.util.BigIntegers.createRandomInRange(TWO, subtract2, secureRandom))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMRProbablePrimeToBase(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        checkCandidate(bigInteger, "candidate");
        checkCandidate(bigInteger2, "base");
        if (bigInteger2.compareTo(bigInteger.subtract(ONE)) >= 0) {
            throw new java.lang.IllegalArgumentException("'base' must be < ('candidate' - 1)");
        }
        if (bigInteger.bitLength() == 2) {
            return true;
        }
        java.math.BigInteger subtract = bigInteger.subtract(ONE);
        int lowestSetBit = subtract.getLowestSetBit();
        return implMRProbablePrimeToBase(bigInteger, subtract, subtract.shiftRight(lowestSetBit), lowestSetBit, bigInteger2);
    }

    private static void checkCandidate(java.math.BigInteger bigInteger, java.lang.String str) {
        if (bigInteger == null || bigInteger.signum() < 1 || bigInteger.bitLength() < 2) {
            throw new java.lang.IllegalArgumentException("'" + str + "' must be non-null and >= 2");
        }
    }

    private static boolean implHasAnySmallFactors(java.math.BigInteger bigInteger) {
        int intValue = bigInteger.mod(java.math.BigInteger.valueOf(223092870)).intValue();
        if (intValue % 2 == 0 || intValue % 3 == 0 || intValue % 5 == 0 || intValue % 7 == 0 || intValue % 11 == 0 || intValue % 13 == 0 || intValue % 17 == 0 || intValue % 19 == 0 || intValue % 23 == 0) {
            return true;
        }
        int intValue2 = bigInteger.mod(java.math.BigInteger.valueOf(58642669)).intValue();
        if (intValue2 % 29 == 0 || intValue2 % 31 == 0 || intValue2 % 37 == 0 || intValue2 % 41 == 0 || intValue2 % 43 == 0) {
            return true;
        }
        int intValue3 = bigInteger.mod(java.math.BigInteger.valueOf(600662303)).intValue();
        if (intValue3 % 47 == 0 || intValue3 % 53 == 0 || intValue3 % 59 == 0 || intValue3 % 61 == 0 || intValue3 % 67 == 0) {
            return true;
        }
        int intValue4 = bigInteger.mod(java.math.BigInteger.valueOf(33984931)).intValue();
        if (intValue4 % 71 == 0 || intValue4 % 73 == 0 || intValue4 % 79 == 0 || intValue4 % 83 == 0) {
            return true;
        }
        int intValue5 = bigInteger.mod(java.math.BigInteger.valueOf(89809099)).intValue();
        if (intValue5 % 89 == 0 || intValue5 % 97 == 0 || intValue5 % 101 == 0 || intValue5 % 103 == 0) {
            return true;
        }
        int intValue6 = bigInteger.mod(java.math.BigInteger.valueOf(167375713)).intValue();
        if (intValue6 % 107 == 0 || intValue6 % 109 == 0 || intValue6 % 113 == 0 || intValue6 % 127 == 0) {
            return true;
        }
        int intValue7 = bigInteger.mod(java.math.BigInteger.valueOf(371700317)).intValue();
        if (intValue7 % 131 == 0 || intValue7 % 137 == 0 || intValue7 % 139 == 0 || intValue7 % 149 == 0) {
            return true;
        }
        int intValue8 = bigInteger.mod(java.math.BigInteger.valueOf(645328247)).intValue();
        if (intValue8 % 151 == 0 || intValue8 % 157 == 0 || intValue8 % 163 == 0 || intValue8 % 167 == 0) {
            return true;
        }
        int intValue9 = bigInteger.mod(java.math.BigInteger.valueOf(1070560157)).intValue();
        if (intValue9 % 173 == 0 || intValue9 % 179 == 0 || intValue9 % 181 == 0 || intValue9 % 191 == 0) {
            return true;
        }
        int intValue10 = bigInteger.mod(java.math.BigInteger.valueOf(1596463769)).intValue();
        return intValue10 % 193 == 0 || intValue10 % 197 == 0 || intValue10 % 199 == 0 || intValue10 % 211 == 0;
    }

    private static boolean implMRProbablePrimeToBase(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, int i, java.math.BigInteger bigInteger4) {
        java.math.BigInteger modPow = bigInteger4.modPow(bigInteger3, bigInteger);
        if (modPow.equals(ONE) || modPow.equals(bigInteger2)) {
            return true;
        }
        for (int i2 = 1; i2 < i; i2++) {
            modPow = modPow.modPow(TWO, bigInteger);
            if (modPow.equals(bigInteger2)) {
                return true;
            }
            if (modPow.equals(ONE)) {
                return false;
            }
        }
        return false;
    }

    private static com.android.internal.org.bouncycastle.math.Primes.STOutput implSTRandomPrime(com.android.internal.org.bouncycastle.crypto.Digest digest, int i, byte[] bArr) {
        com.android.internal.org.bouncycastle.math.Primes.STOutputIA sTOutputIA;
        int digestSize = digest.getDigestSize();
        com.android.internal.org.bouncycastle.math.Primes.STOutputIA sTOutputIA2 = null;
        int i2 = 1;
        if (i >= 33) {
            com.android.internal.org.bouncycastle.math.Primes.STOutput implSTRandomPrime = implSTRandomPrime(digest, (i + 3) / 2, bArr);
            java.math.BigInteger prime = implSTRandomPrime.getPrime();
            byte[] primeSeed = implSTRandomPrime.getPrimeSeed();
            int primeGenCounter = implSTRandomPrime.getPrimeGenCounter();
            int i3 = i - 1;
            int i4 = (i3 / (digestSize * 8)) + 1;
            java.math.BigInteger bit = hashGen(digest, primeSeed, i4).mod(ONE.shiftLeft(i3)).setBit(i3);
            java.math.BigInteger shiftLeft = prime.shiftLeft(1);
            java.math.BigInteger shiftLeft2 = bit.subtract(ONE).divide(shiftLeft).add(ONE).shiftLeft(1);
            java.math.BigInteger add = shiftLeft2.multiply(prime).add(ONE);
            int i5 = primeGenCounter;
            int i6 = 0;
            while (true) {
                if (add.bitLength() > i) {
                    shiftLeft2 = ONE.shiftLeft(i3).subtract(ONE).divide(shiftLeft).add(ONE).shiftLeft(i2);
                    add = shiftLeft2.multiply(prime).add(ONE);
                }
                i5 += i2;
                if (!implHasAnySmallFactors(add)) {
                    java.math.BigInteger add2 = hashGen(digest, primeSeed, i4).mod(add.subtract(THREE)).add(TWO);
                    java.math.BigInteger add3 = shiftLeft2.add(java.math.BigInteger.valueOf(i6));
                    java.math.BigInteger modPow = add2.modPow(add3, add);
                    if (!add.gcd(modPow.subtract(ONE)).equals(ONE) || !modPow.modPow(prime, add).equals(ONE)) {
                        sTOutputIA = null;
                        shiftLeft2 = add3;
                        i6 = 0;
                    } else {
                        return new com.android.internal.org.bouncycastle.math.Primes.STOutput(add, primeSeed, i5);
                    }
                } else {
                    sTOutputIA = sTOutputIA2;
                    inc(primeSeed, i4);
                }
                if (i5 >= (i * 4) + primeGenCounter) {
                    throw new java.lang.IllegalStateException("Too many iterations in Shawe-Taylor Random_Prime Routine");
                }
                i6 += 2;
                add = add.add(shiftLeft);
                sTOutputIA2 = sTOutputIA;
                i2 = 1;
            }
        } else {
            byte[] bArr2 = new byte[digestSize];
            byte[] bArr3 = new byte[digestSize];
            int i7 = 0;
            do {
                hash(digest, bArr, bArr2, 0);
                inc(bArr, 1);
                hash(digest, bArr, bArr3, 0);
                inc(bArr, 1);
                i7++;
                long extract32 = (((extract32(bArr2) ^ extract32(bArr3)) & ((-1) >>> (32 - i))) | (1 << (i - 1)) | 1) & 4294967295L;
                if (isPrime32(extract32)) {
                    return new com.android.internal.org.bouncycastle.math.Primes.STOutput(java.math.BigInteger.valueOf(extract32), bArr, i7);
                }
            } while (i7 <= i * 4);
            throw new java.lang.IllegalStateException("Too many iterations in Shawe-Taylor Random_Prime Routine");
        }
    }

    private static int extract32(byte[] bArr) {
        int min = java.lang.Math.min(4, bArr.length);
        int i = 0;
        int i2 = 0;
        while (i < min) {
            int i3 = i + 1;
            i2 |= (bArr[bArr.length - i3] & 255) << (i * 8);
            i = i3;
        }
        return i2;
    }

    private static void hash(com.android.internal.org.bouncycastle.crypto.Digest digest, byte[] bArr, byte[] bArr2, int i) {
        digest.update(bArr, 0, bArr.length);
        digest.doFinal(bArr2, i);
    }

    private static java.math.BigInteger hashGen(com.android.internal.org.bouncycastle.crypto.Digest digest, byte[] bArr, int i) {
        int digestSize = digest.getDigestSize();
        int i2 = i * digestSize;
        byte[] bArr2 = new byte[i2];
        for (int i3 = 0; i3 < i; i3++) {
            i2 -= digestSize;
            hash(digest, bArr, bArr2, i2);
            inc(bArr, 1);
        }
        return new java.math.BigInteger(1, bArr2);
    }

    private static void inc(byte[] bArr, int i) {
        int length = bArr.length;
        while (i > 0) {
            length--;
            if (length >= 0) {
                int i2 = i + (bArr[length] & 255);
                bArr[length] = (byte) i2;
                i = i2 >>> 8;
            } else {
                return;
            }
        }
    }

    private static boolean isPrime32(long j) {
        if ((j >>> 32) != 0) {
            throw new java.lang.IllegalArgumentException("Size limit exceeded");
        }
        if (j <= 5) {
            return j == 2 || j == 3 || j == 5;
        }
        if ((1 & j) == 0 || j % 3 == 0 || j % 5 == 0) {
            return false;
        }
        long[] jArr = {1, 7, 11, 13, 17, 19, 23, 29};
        long j2 = 0;
        int i = 1;
        while (true) {
            if (i >= 8) {
                j2 += 30;
                if (j2 * j2 >= j) {
                    return true;
                }
                i = 0;
            } else {
                if (j % (jArr[i] + j2) == 0) {
                    return j < 30;
                }
                i++;
            }
        }
    }
}
