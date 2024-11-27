package com.android.internal.org.bouncycastle.math.ec.custom.sec;

/* loaded from: classes4.dex */
public class SecP192R1Field {
    private static final long M = 4294967295L;
    private static final int P5 = -1;
    private static final int PExt11 = -1;
    static final int[] P = {-1, -1, -2, -1, -1, -1};
    private static final int[] PExt = {1, 0, 2, 0, 1, 0, -2, -1, -3, -1, -1, -1};
    private static final int[] PExtInv = {-1, -1, -3, -1, -2, -1, 1, 0, 2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat192.add(iArr, iArr2, iArr3) != 0 || (iArr3[5] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat192.gte(iArr3, P))) {
            addPInvTo(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if ((com.android.internal.org.bouncycastle.math.raw.Nat.add(12, iArr, iArr2, iArr3) != 0 || (iArr3[11] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat.gte(12, iArr3, PExt))) && com.android.internal.org.bouncycastle.math.raw.Nat.addTo(PExtInv.length, PExtInv, iArr3) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.incAt(12, iArr3, PExtInv.length);
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat.inc(6, iArr, iArr2) != 0 || (iArr2[5] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat192.gte(iArr2, P))) {
            addPInvTo(iArr2);
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
        long j = iArr[6] & 4294967295L;
        long j2 = iArr[7] & 4294967295L;
        long j3 = (iArr[10] & 4294967295L) + j;
        long j4 = (iArr[11] & 4294967295L) + j2;
        long j5 = (iArr[0] & 4294967295L) + j3 + 0;
        int i = (int) j5;
        long j6 = (j5 >> 32) + (iArr[1] & 4294967295L) + j4;
        iArr2[1] = (int) j6;
        long j7 = j3 + (iArr[8] & 4294967295L);
        long j8 = j4 + (iArr[9] & 4294967295L);
        long j9 = (j6 >> 32) + (iArr[2] & 4294967295L) + j7;
        long j10 = j9 & 4294967295L;
        long j11 = (j9 >> 32) + (iArr[3] & 4294967295L) + j8;
        iArr2[3] = (int) j11;
        long j12 = (j11 >> 32) + (iArr[4] & 4294967295L) + (j7 - j);
        iArr2[4] = (int) j12;
        long j13 = (j12 >> 32) + (iArr[5] & 4294967295L) + (j8 - j2);
        iArr2[5] = (int) j13;
        long j14 = j13 >> 32;
        long j15 = j10 + j14;
        long j16 = j14 + (i & 4294967295L);
        iArr2[0] = (int) j16;
        long j17 = j16 >> 32;
        if (j17 != 0) {
            long j18 = j17 + (4294967295L & iArr2[1]);
            iArr2[1] = (int) j18;
            j15 += j18 >> 32;
        }
        iArr2[2] = (int) j15;
        if (((j15 >> 32) != 0 && com.android.internal.org.bouncycastle.math.raw.Nat.incAt(6, iArr2, 3) != 0) || (iArr2[5] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat192.gte(iArr2, P))) {
            addPInvTo(iArr2);
        }
    }

    public static void reduce32(int i, int[] iArr) {
        long j;
        if (i == 0) {
            j = 0;
        } else {
            long j2 = i & 4294967295L;
            long j3 = (iArr[0] & 4294967295L) + j2 + 0;
            iArr[0] = (int) j3;
            long j4 = j3 >> 32;
            if (j4 != 0) {
                long j5 = j4 + (iArr[1] & 4294967295L);
                iArr[1] = (int) j5;
                j4 = j5 >> 32;
            }
            long j6 = j4 + (4294967295L & iArr[2]) + j2;
            iArr[2] = (int) j6;
            j = j6 >> 32;
        }
        if ((j != 0 && com.android.internal.org.bouncycastle.math.raw.Nat.incAt(6, iArr, 3) != 0) || (iArr[5] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat192.gte(iArr, P))) {
            addPInvTo(iArr);
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
            subPInvFrom(iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat.sub(12, iArr, iArr2, iArr3) != 0 && com.android.internal.org.bouncycastle.math.raw.Nat.subFrom(PExtInv.length, PExtInv, iArr3) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.decAt(12, iArr3, PExtInv.length);
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (com.android.internal.org.bouncycastle.math.raw.Nat.shiftUpBit(6, iArr, 0, iArr2) != 0 || (iArr2[5] == -1 && com.android.internal.org.bouncycastle.math.raw.Nat192.gte(iArr2, P))) {
            addPInvTo(iArr2);
        }
    }

    private static void addPInvTo(int[] iArr) {
        long j = (iArr[0] & 4294967295L) + 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (iArr[1] & 4294967295L);
            iArr[1] = (int) j3;
            j2 = j3 >> 32;
        }
        long j4 = j2 + (4294967295L & iArr[2]) + 1;
        iArr[2] = (int) j4;
        if ((j4 >> 32) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.incAt(6, iArr, 3);
        }
    }

    private static void subPInvFrom(int[] iArr) {
        long j = (iArr[0] & 4294967295L) - 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (iArr[1] & 4294967295L);
            iArr[1] = (int) j3;
            j2 = j3 >> 32;
        }
        long j4 = j2 + ((4294967295L & iArr[2]) - 1);
        iArr[2] = (int) j4;
        if ((j4 >> 32) != 0) {
            com.android.internal.org.bouncycastle.math.raw.Nat.decAt(6, iArr, 3);
        }
    }
}
