package com.android.internal.org.bouncycastle.math.ec.custom.sec;

/* loaded from: classes4.dex */
public class SecP521R1Field {
    static final int[] P = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 511};
    private static final int P16 = 511;

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        int add = com.android.internal.org.bouncycastle.math.raw.Nat.add(16, iArr, iArr2, iArr3) + iArr[16] + iArr2[16];
        if (add > 511 || (add == 511 && com.android.internal.org.bouncycastle.math.raw.Nat.eq(16, iArr3, P))) {
            add = (add + com.android.internal.org.bouncycastle.math.raw.Nat.inc(16, iArr3)) & 511;
        }
        iArr3[16] = add;
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        int inc = com.android.internal.org.bouncycastle.math.raw.Nat.inc(16, iArr, iArr2) + iArr[16];
        if (inc > 511 || (inc == 511 && com.android.internal.org.bouncycastle.math.raw.Nat.eq(16, iArr2, P))) {
            inc = (inc + com.android.internal.org.bouncycastle.math.raw.Nat.inc(16, iArr2)) & 511;
        }
        iArr2[16] = inc;
    }

    public static int[] fromBigInteger(java.math.BigInteger bigInteger) {
        int[] fromBigInteger = com.android.internal.org.bouncycastle.math.raw.Nat.fromBigInteger(521, bigInteger);
        if (com.android.internal.org.bouncycastle.math.raw.Nat.eq(17, fromBigInteger, P)) {
            com.android.internal.org.bouncycastle.math.raw.Nat.zero(17, fromBigInteger);
        }
        return fromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        int i = iArr[16];
        iArr2[16] = (com.android.internal.org.bouncycastle.math.raw.Nat.shiftDownBit(16, iArr, i, iArr2) >>> 23) | (i >>> 1);
    }

    public static void inv(int[] iArr, int[] iArr2) {
        com.android.internal.org.bouncycastle.math.raw.Mod.checkedModOddInverse(P, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 17; i2++) {
            i |= iArr[i2];
        }
        return (((i >>> 1) | (i & 1)) - 1) >> 31;
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat.create(33);
        implMultiply(iArr, iArr2, create);
        reduce(create, iArr3);
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.sub(17, P, P, iArr2);
        } else {
            com.android.internal.org.bouncycastle.math.raw.Nat.sub(17, P, iArr, iArr2);
        }
    }

    public static void random(java.security.SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[68];
        do {
            secureRandom.nextBytes(bArr);
            com.android.internal.org.bouncycastle.util.Pack.littleEndianToInt(bArr, 0, iArr, 0, 17);
            iArr[16] = iArr[16] & 511;
        } while (com.android.internal.org.bouncycastle.math.raw.Nat.lessThan(17, iArr, P) == 0);
    }

    public static void randomMult(java.security.SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        int i = iArr[32];
        int shiftDownBits = (com.android.internal.org.bouncycastle.math.raw.Nat.shiftDownBits(16, iArr, 16, 9, i, iArr2, 0) >>> 23) + (i >>> 9) + com.android.internal.org.bouncycastle.math.raw.Nat.addTo(16, iArr, iArr2);
        if (shiftDownBits > 511 || (shiftDownBits == 511 && com.android.internal.org.bouncycastle.math.raw.Nat.eq(16, iArr2, P))) {
            shiftDownBits = (shiftDownBits + com.android.internal.org.bouncycastle.math.raw.Nat.inc(16, iArr2)) & 511;
        }
        iArr2[16] = shiftDownBits;
    }

    public static void reduce23(int[] iArr) {
        int i = iArr[16];
        int addWordTo = com.android.internal.org.bouncycastle.math.raw.Nat.addWordTo(16, i >>> 9, iArr) + (i & 511);
        if (addWordTo > 511 || (addWordTo == 511 && com.android.internal.org.bouncycastle.math.raw.Nat.eq(16, iArr, P))) {
            addWordTo = (addWordTo + com.android.internal.org.bouncycastle.math.raw.Nat.inc(16, iArr)) & 511;
        }
        iArr[16] = addWordTo;
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat.create(33);
        implSquare(iArr, create);
        reduce(create, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat.create(33);
        implSquare(iArr, create);
        reduce(create, iArr2);
        while (true) {
            i--;
            if (i > 0) {
                implSquare(iArr2, create);
                reduce(create, iArr2);
            } else {
                return;
            }
        }
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        int sub = (com.android.internal.org.bouncycastle.math.raw.Nat.sub(16, iArr, iArr2, iArr3) + iArr[16]) - iArr2[16];
        if (sub < 0) {
            sub = (sub + com.android.internal.org.bouncycastle.math.raw.Nat.dec(16, iArr3)) & 511;
        }
        iArr3[16] = sub;
    }

    public static void twice(int[] iArr, int[] iArr2) {
        int i = iArr[16];
        iArr2[16] = (com.android.internal.org.bouncycastle.math.raw.Nat.shiftUpBit(16, iArr, i << 23, iArr2) | (i << 1)) & 511;
    }

    protected static void implMultiply(int[] iArr, int[] iArr2, int[] iArr3) {
        com.android.internal.org.bouncycastle.math.raw.Nat512.mul(iArr, iArr2, iArr3);
        int i = iArr[16];
        int i2 = iArr2[16];
        iArr3[32] = com.android.internal.org.bouncycastle.math.raw.Nat.mul31BothAdd(16, i, iArr2, i2, iArr, iArr3, 16) + (i * i2);
    }

    protected static void implSquare(int[] iArr, int[] iArr2) {
        com.android.internal.org.bouncycastle.math.raw.Nat512.square(iArr, iArr2);
        int i = iArr[16];
        iArr2[32] = com.android.internal.org.bouncycastle.math.raw.Nat.mulWordAddTo(16, i << 1, iArr, 0, iArr2, 16) + (i * i);
    }
}
