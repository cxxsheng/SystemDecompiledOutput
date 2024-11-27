package com.android.internal.org.bouncycastle.math.ec.custom.sec;

/* loaded from: classes4.dex */
public class SecP192K1Field {
    private static final int P5 = -1;
    private static final int PExt11 = -1;
    private static final int PInv33 = 4553;
    static final int[] P = {-4553, -2, -1, -1, -1, -1};
    private static final int[] PExt = {20729809, 9106, 1, 0, 0, 0, -9106, -3, -1, -1, -1, -1};
    private static final int[] PExtInv = {-20729809, -9107, -2, -1, -1, -1, 9105, 2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat192.add(iArr, iArr2, iArr3) != 0 || (iArr3[5] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat192.gte(iArr3, P))) {
            com.android.internal.org.bouncycastle.math.raw.Nat.add33To(6, PInv33, iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if ((com.android.internal.org.bouncycastle.math.raw.Nat.add(12, iArr, iArr2, iArr3) != 0 || (iArr3[11] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat.gte(12, iArr3, PExt))) && com.android.internal.org.bouncycastle.math.raw.Nat.addTo(PExtInv.length, PExtInv, iArr3) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.incAt(12, iArr3, PExtInv.length);
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat.inc(6, iArr, iArr2) != 0 || (iArr2[5] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat192.gte(iArr2, P))) {
            com.android.internal.org.bouncycastle.math.raw.Nat.add33To(6, PInv33, iArr2);
        }
    }

    public static int[] fromBigInteger(java.math.BigInteger bigInteger) {
        int[] fromBigInteger = com.android.internal.org.bouncycastle.math.raw.Nat192.fromBigInteger(bigInteger);
        if (fromBigInteger[5] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat192.gte(fromBigInteger, P)) {
            com.android.internal.org.bouncycastle.math.raw.Nat192.subFrom(P, fromBigInteger);
        }
        return fromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.shiftDownBit(6, iArr, 0, iArr2);
        } else {
            com.android.internal.org.bouncycastle.math.raw.Nat.shiftDownBit(6, iArr2, com.android.internal.org.bouncycastle.math.raw.Nat192.add(iArr, P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        com.android.internal.org.bouncycastle.math.raw.Mod.checkedModOddInverse(P, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 6; i2++) {
            i |= iArr[i2];
        }
        return (((i >>> 1) | (i & 1)) - 1) >> 31;
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] createExt = com.android.internal.org.bouncycastle.math.raw.Nat192.createExt();
        com.android.internal.org.bouncycastle.math.raw.Nat192.mul(iArr, iArr2, createExt);
        reduce(createExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if ((com.android.internal.org.bouncycastle.math.raw.Nat192.mulAddTo(iArr, iArr2, iArr3) != 0 || (iArr3[11] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat.gte(12, iArr3, PExt))) && com.android.internal.org.bouncycastle.math.raw.Nat.addTo(PExtInv.length, PExtInv, iArr3) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.incAt(12, iArr3, PExtInv.length);
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat192.sub(P, P, iArr2);
        } else {
            com.android.internal.org.bouncycastle.math.raw.Nat192.sub(P, iArr, iArr2);
        }
    }

    public static void random(java.security.SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[24];
        do {
            secureRandom.nextBytes(bArr);
            com.android.internal.org.bouncycastle.util.Pack.littleEndianToInt(bArr, 0, iArr, 0, 6);
        } while (com.android.internal.org.bouncycastle.math.raw.Nat.lessThan(6, iArr, P) == 0);
    }

    public static void randomMult(java.security.SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat192.mul33DWordAdd(PInv33, com.android.internal.org.bouncycastle.math.raw.Nat192.mul33Add(PInv33, iArr, 6, iArr, 0, iArr2, 0), iArr2, 0) != 0 || (iArr2[5] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat192.gte(iArr2, P))) {
            com.android.internal.org.bouncycastle.math.raw.Nat.add33To(6, PInv33, iArr2);
        }
    }

    public static void reduce32(int i, int[] iArr) {
        if ((i != 0 && com.android.internal.org.bouncycastle.math.raw.Nat192.mul33WordAdd(PInv33, i, iArr, 0) != 0) || (iArr[5] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat192.gte(iArr, P))) {
            com.android.internal.org.bouncycastle.math.raw.Nat.add33To(6, PInv33, iArr);
        }
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] createExt = com.android.internal.org.bouncycastle.math.raw.Nat192.createExt();
        com.android.internal.org.bouncycastle.math.raw.Nat192.square(iArr, createExt);
        reduce(createExt, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] createExt = com.android.internal.org.bouncycastle.math.raw.Nat192.createExt();
        com.android.internal.org.bouncycastle.math.raw.Nat192.square(iArr, createExt);
        reduce(createExt, iArr2);
        while (true) {
            i--;
            if (i > 0) {
                com.android.internal.org.bouncycastle.math.raw.Nat192.square(iArr2, createExt);
                reduce(createExt, iArr2);
            } else {
                return;
            }
        }
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat192.sub(iArr, iArr2, iArr3) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.sub33From(6, PInv33, iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat.sub(12, iArr, iArr2, iArr3) != 0 && com.android.internal.org.bouncycastle.math.raw.Nat.subFrom(PExtInv.length, PExtInv, iArr3) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.decAt(12, iArr3, PExtInv.length);
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat.shiftUpBit(6, iArr, 0, iArr2) != 0 || (iArr2[5] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat192.gte(iArr2, P))) {
            com.android.internal.org.bouncycastle.math.raw.Nat.add33To(6, PInv33, iArr2);
        }
    }
}
