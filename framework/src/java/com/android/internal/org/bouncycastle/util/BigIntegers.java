package com.android.internal.org.bouncycastle.util;

/* loaded from: classes4.dex */
public final class BigIntegers {
    private static final int MAX_ITERATIONS = 1000;
    public static final java.math.BigInteger ZERO = java.math.BigInteger.valueOf(0);
    public static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    public static final java.math.BigInteger TWO = java.math.BigInteger.valueOf(2);
    private static final java.math.BigInteger THREE = java.math.BigInteger.valueOf(3);
    private static final java.math.BigInteger SMALL_PRIMES_PRODUCT = new java.math.BigInteger("8138e8a0fcf3a4e84a771d40fd305d7f4aa59306d7251de54d98af8fe95729a1f73d893fa424cd2edc8636a6c3285e022b0e3866a565ae8108eed8591cd4fe8d2ce86165a978d719ebf647f362d33fca29cd179fb42401cbaf3df0c614056f9c8f3cfd51e474afb6bc6974f78db8aba8e9e517fded658591ab7502bd41849462f", 16);
    private static final int MAX_SMALL = java.math.BigInteger.valueOf(743).bitLength();

    public static byte[] asUnsignedByteArray(java.math.BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray[0] == 0 && byteArray.length != 1) {
            int length = byteArray.length - 1;
            byte[] bArr = new byte[length];
            java.lang.System.arraycopy(byteArray, 1, bArr, 0, length);
            return bArr;
        }
        return byteArray;
    }

    public static byte[] asUnsignedByteArray(int i, java.math.BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length == i) {
            return byteArray;
        }
        int i2 = 0;
        if (byteArray[0] == 0 && byteArray.length != 1) {
            i2 = 1;
        }
        int length = byteArray.length - i2;
        if (length > i) {
            throw new java.lang.IllegalArgumentException("standard length exceeded for value");
        }
        byte[] bArr = new byte[i];
        java.lang.System.arraycopy(byteArray, i2, bArr, i - length, length);
        return bArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0012, code lost:
    
        if (r3.length != 1) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void asUnsignedByteArray(java.math.BigInteger bigInteger, byte[] bArr, int i, int i2) {
        int i3;
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length == i2) {
            java.lang.System.arraycopy(byteArray, 0, bArr, i, i2);
            return;
        }
        if (byteArray[0] == 0) {
            i3 = 1;
        }
        i3 = 0;
        int length = byteArray.length - i3;
        if (length > i2) {
            throw new java.lang.IllegalArgumentException("standard length exceeded for value");
        }
        int i4 = (i2 - length) + i;
        com.android.internal.org.bouncycastle.util.Arrays.fill(bArr, i, i4, (byte) 0);
        java.lang.System.arraycopy(byteArray, i3, bArr, i4, length);
    }

    public static java.math.BigInteger createRandomInRange(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.security.SecureRandom secureRandom) {
        int compareTo = bigInteger.compareTo(bigInteger2);
        if (compareTo >= 0) {
            if (compareTo > 0) {
                throw new java.lang.IllegalArgumentException("'min' may not be greater than 'max'");
            }
            return bigInteger;
        }
        if (bigInteger.bitLength() > bigInteger2.bitLength() / 2) {
            return createRandomInRange(ZERO, bigInteger2.subtract(bigInteger), secureRandom).add(bigInteger);
        }
        for (int i = 0; i < 1000; i++) {
            java.math.BigInteger createRandomBigInteger = createRandomBigInteger(bigInteger2.bitLength(), secureRandom);
            if (createRandomBigInteger.compareTo(bigInteger) >= 0 && createRandomBigInteger.compareTo(bigInteger2) <= 0) {
                return createRandomBigInteger;
            }
        }
        return createRandomBigInteger(bigInteger2.subtract(bigInteger).bitLength() - 1, secureRandom).add(bigInteger);
    }

    public static java.math.BigInteger fromUnsignedByteArray(byte[] bArr) {
        return new java.math.BigInteger(1, bArr);
    }

    public static java.math.BigInteger fromUnsignedByteArray(byte[] bArr, int i, int i2) {
        if (i != 0 || i2 != bArr.length) {
            byte[] bArr2 = new byte[i2];
            java.lang.System.arraycopy(bArr, i, bArr2, 0, i2);
            bArr = bArr2;
        }
        return new java.math.BigInteger(1, bArr);
    }

    public static int intValueExact(java.math.BigInteger bigInteger) {
        if (bigInteger.bitLength() > 31) {
            throw new java.lang.ArithmeticException("BigInteger out of int range");
        }
        return bigInteger.intValue();
    }

    public static long longValueExact(java.math.BigInteger bigInteger) {
        if (bigInteger.bitLength() > 63) {
            throw new java.lang.ArithmeticException("BigInteger out of long range");
        }
        return bigInteger.longValue();
    }

    public static java.math.BigInteger modOddInverse(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        if (!bigInteger.testBit(0)) {
            throw new java.lang.IllegalArgumentException("'M' must be odd");
        }
        if (bigInteger.signum() != 1) {
            throw new java.lang.ArithmeticException("BigInteger: modulus not positive");
        }
        if (bigInteger2.signum() < 0 || bigInteger2.compareTo(bigInteger) >= 0) {
            bigInteger2 = bigInteger2.mod(bigInteger);
        }
        int bitLength = bigInteger.bitLength();
        int[] fromBigInteger = com.android.internal.org.bouncycastle.math.raw.Nat.fromBigInteger(bitLength, bigInteger);
        int[] fromBigInteger2 = com.android.internal.org.bouncycastle.math.raw.Nat.fromBigInteger(bitLength, bigInteger2);
        int length = fromBigInteger.length;
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat.create(length);
        if (com.android.internal.org.bouncycastle.math.raw.Mod.modOddInverse(fromBigInteger, fromBigInteger2, create) == 0) {
            throw new java.lang.ArithmeticException("BigInteger not invertible.");
        }
        return com.android.internal.org.bouncycastle.math.raw.Nat.toBigInteger(length, create);
    }

    public static java.math.BigInteger modOddInverseVar(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        if (!bigInteger.testBit(0)) {
            throw new java.lang.IllegalArgumentException("'M' must be odd");
        }
        if (bigInteger.signum() != 1) {
            throw new java.lang.ArithmeticException("BigInteger: modulus not positive");
        }
        if (bigInteger.equals(ONE)) {
            return ZERO;
        }
        if (bigInteger2.signum() < 0 || bigInteger2.compareTo(bigInteger) >= 0) {
            bigInteger2 = bigInteger2.mod(bigInteger);
        }
        if (bigInteger2.equals(ONE)) {
            return ONE;
        }
        int bitLength = bigInteger.bitLength();
        int[] fromBigInteger = com.android.internal.org.bouncycastle.math.raw.Nat.fromBigInteger(bitLength, bigInteger);
        int[] fromBigInteger2 = com.android.internal.org.bouncycastle.math.raw.Nat.fromBigInteger(bitLength, bigInteger2);
        int length = fromBigInteger.length;
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat.create(length);
        if (!com.android.internal.org.bouncycastle.math.raw.Mod.modOddInverseVar(fromBigInteger, fromBigInteger2, create)) {
            throw new java.lang.ArithmeticException("BigInteger not invertible.");
        }
        return com.android.internal.org.bouncycastle.math.raw.Nat.toBigInteger(length, create);
    }

    public static int getUnsignedByteLength(java.math.BigInteger bigInteger) {
        if (bigInteger.equals(ZERO)) {
            return 1;
        }
        return (bigInteger.bitLength() + 7) / 8;
    }

    public static java.math.BigInteger createRandomBigInteger(int i, java.security.SecureRandom secureRandom) {
        return new java.math.BigInteger(1, createRandom(i, secureRandom));
    }

    public static java.math.BigInteger createRandomPrime(int i, int i2, java.security.SecureRandom secureRandom) {
        java.math.BigInteger bigInteger;
        if (i < 2) {
            throw new java.lang.IllegalArgumentException("bitLength < 2");
        }
        if (i == 2) {
            return secureRandom.nextInt() < 0 ? TWO : THREE;
        }
        do {
            byte[] createRandom = createRandom(i, secureRandom);
            createRandom[0] = (byte) (((byte) (1 << (7 - ((createRandom.length * 8) - i)))) | createRandom[0]);
            int length = createRandom.length - 1;
            createRandom[length] = (byte) (createRandom[length] | 1);
            bigInteger = new java.math.BigInteger(1, createRandom);
            if (i > MAX_SMALL) {
                while (!bigInteger.gcd(SMALL_PRIMES_PRODUCT).equals(ONE)) {
                    bigInteger = bigInteger.add(TWO);
                }
            }
        } while (!bigInteger.isProbablePrime(i2));
        return bigInteger;
    }

    private static byte[] createRandom(int i, java.security.SecureRandom secureRandom) throws java.lang.IllegalArgumentException {
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("bitLength must be at least 1");
        }
        int i2 = (i + 7) / 8;
        byte[] bArr = new byte[i2];
        secureRandom.nextBytes(bArr);
        bArr[0] = (byte) (bArr[0] & ((byte) (255 >>> ((i2 * 8) - i))));
        return bArr;
    }
}
