package com.android.internal.org.bouncycastle.math.ec.custom.sec;

/* loaded from: classes4.dex */
public class SecP256K1Field {
    private static final int P7 = -1;
    private static final int PExt15 = -1;
    private static final int PInv33 = 977;
    static final int[] P = {-977, -2, -1, -1, -1, -1, -1, -1};
    private static final int[] PExt = {954529, 1954, 1, 0, 0, 0, 0, 0, -1954, -3, -1, -1, -1, -1, -1, -1};
    private static final int[] PExtInv = {-954529, -1955, -2, -1, -1, -1, -1, -1, 1953, 2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat256.add(iArr, iArr2, iArr3) != 0 || (iArr3[7] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat256.gte(iArr3, P))) {
            com.android.internal.org.bouncycastle.math.raw.Nat.add33To(8, PInv33, iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if ((com.android.internal.org.bouncycastle.math.raw.Nat.add(16, iArr, iArr2, iArr3) != 0 || (iArr3[15] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat.gte(16, iArr3, PExt))) && com.android.internal.org.bouncycastle.math.raw.Nat.addTo(PExtInv.length, PExtInv, iArr3) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.incAt(16, iArr3, PExtInv.length);
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat.inc(8, iArr, iArr2) != 0 || (iArr2[7] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat256.gte(iArr2, P))) {
            com.android.internal.org.bouncycastle.math.raw.Nat.add33To(8, PInv33, iArr2);
        }
    }

    public static int[] fromBigInteger(java.math.BigInteger bigInteger) {
        int[] fromBigInteger = com.android.internal.org.bouncycastle.math.raw.Nat256.fromBigInteger(bigInteger);
        if (fromBigInteger[7] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat256.gte(fromBigInteger, P)) {
            com.android.internal.org.bouncycastle.math.raw.Nat256.subFrom(P, fromBigInteger);
        }
        return fromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.shiftDownBit(8, iArr, 0, iArr2);
        } else {
            com.android.internal.org.bouncycastle.math.raw.Nat.shiftDownBit(8, iArr2, com.android.internal.org.bouncycastle.math.raw.Nat256.add(iArr, P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        com.android.internal.org.bouncycastle.math.raw.Mod.checkedModOddInverse(P, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            i |= iArr[i2];
        }
        return (((i >>> 1) | (i & 1)) - 1) >> 31;
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] createExt = com.android.internal.org.bouncycastle.math.raw.Nat256.createExt();
        com.android.internal.org.bouncycastle.math.raw.Nat256.mul(iArr, iArr2, createExt);
        reduce(createExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if ((com.android.internal.org.bouncycastle.math.raw.Nat256.mulAddTo(iArr, iArr2, iArr3) != 0 || (iArr3[15] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat.gte(16, iArr3, PExt))) && com.android.internal.org.bouncycastle.math.raw.Nat.addTo(PExtInv.length, PExtInv, iArr3) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.incAt(16, iArr3, PExtInv.length);
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat256.sub(P, P, iArr2);
        } else {
            com.android.internal.org.bouncycastle.math.raw.Nat256.sub(P, iArr, iArr2);
        }
    }

    public static void random(java.security.SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[32];
        do {
            secureRandom.nextBytes(bArr);
            com.android.internal.org.bouncycastle.util.Pack.littleEndianToInt(bArr, 0, iArr, 0, 8);
        } while (com.android.internal.org.bouncycastle.math.raw.Nat.lessThan(8, iArr, P) == 0);
    }

    public static void randomMult(java.security.SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat256.mul33DWordAdd(PInv33, com.android.internal.org.bouncycastle.math.raw.Nat256.mul33Add(PInv33, iArr, 8, iArr, 0, iArr2, 0), iArr2, 0) != 0 || (iArr2[7] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat256.gte(iArr2, P))) {
            com.android.internal.org.bouncycastle.math.raw.Nat.add33To(8, PInv33, iArr2);
        }
    }

    public static void reduce32(int i, int[] iArr) {
        if ((i != 0 && com.android.internal.org.bouncycastle.math.raw.Nat256.mul33WordAdd(PInv33, i, iArr, 0) != 0) || (iArr[7] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat256.gte(iArr, P))) {
            com.android.internal.org.bouncycastle.math.raw.Nat.add33To(8, PInv33, iArr);
        }
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] createExt = com.android.internal.org.bouncycastle.math.raw.Nat256.createExt();
        com.android.internal.org.bouncycastle.math.raw.Nat256.square(iArr, createExt);
        reduce(createExt, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] createExt = com.android.internal.org.bouncycastle.math.raw.Nat256.createExt();
        com.android.internal.org.bouncycastle.math.raw.Nat256.square(iArr, createExt);
        reduce(createExt, iArr2);
        while (true) {
            i--;
            if (i > 0) {
                com.android.internal.org.bouncycastle.math.raw.Nat256.square(iArr2, createExt);
                reduce(createExt, iArr2);
            } else {
                return;
            }
        }
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat256.sub(iArr, iArr2, iArr3) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.sub33From(8, PInv33, iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat.sub(16, iArr, iArr2, iArr3) != 0 && com.android.internal.org.bouncycastle.math.raw.Nat.subFrom(PExtInv.length, PExtInv, iArr3) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.decAt(16, iArr3, PExtInv.length);
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat.shiftUpBit(8, iArr, 0, iArr2) != 0 || (iArr2[7] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat256.gte(iArr2, P))) {
            com.android.internal.org.bouncycastle.math.raw.Nat.add33To(8, PInv33, iArr2);
        }
    }
}
