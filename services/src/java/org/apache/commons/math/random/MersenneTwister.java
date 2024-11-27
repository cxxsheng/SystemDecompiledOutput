package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public class MersenneTwister extends org.apache.commons.math.random.BitsStreamGenerator implements java.io.Serializable {
    private static final int M = 397;
    private static final int[] MAG01 = {0, -1727483681};
    private static final int N = 624;
    private static final long serialVersionUID = 8661194735290153518L;
    private int[] mt;
    private int mti;

    public MersenneTwister() {
        this.mt = new int[N];
        setSeed(java.lang.System.currentTimeMillis());
    }

    public MersenneTwister(int i) {
        this.mt = new int[N];
        setSeed(i);
    }

    public MersenneTwister(int[] iArr) {
        this.mt = new int[N];
        setSeed(iArr);
    }

    public MersenneTwister(long j) {
        this.mt = new int[N];
        setSeed(j);
    }

    @Override // org.apache.commons.math.random.BitsStreamGenerator, org.apache.commons.math.random.RandomGenerator
    public void setSeed(int i) {
        long j = i;
        this.mt[0] = (int) j;
        this.mti = 1;
        while (this.mti < N) {
            j = (((j ^ (j >> 30)) * 1812433253) + this.mti) & 4294967295L;
            this.mt[this.mti] = (int) j;
            this.mti++;
        }
    }

    @Override // org.apache.commons.math.random.BitsStreamGenerator, org.apache.commons.math.random.RandomGenerator
    public void setSeed(int[] iArr) {
        if (iArr == null) {
            setSeed(java.lang.System.currentTimeMillis());
            return;
        }
        setSeed(19650218);
        int i = 1;
        int i2 = 0;
        for (int max = org.apache.commons.math.util.FastMath.max(N, iArr.length); max != 0; max--) {
            long j = (this.mt[i] & 2147483647L) | (this.mt[i] < 0 ? 2147483648L : 0L);
            long j2 = (this.mt[r15] & 2147483647L) | (this.mt[i - 1] < 0 ? 2147483648L : 0L);
            this.mt[i] = (int) (((((j2 ^ (j2 >> 30)) * 1664525) ^ j) + iArr[i2] + i2) & 4294967295L);
            i++;
            i2++;
            if (i >= N) {
                this.mt[0] = this.mt[623];
                i = 1;
            }
            if (i2 >= iArr.length) {
                i2 = 0;
            }
        }
        for (int i3 = 623; i3 != 0; i3--) {
            long j3 = (this.mt[i] & 2147483647L) | (this.mt[i] < 0 ? 2147483648L : 0L);
            long j4 = (this.mt[r7] & 2147483647L) | (this.mt[i - 1] < 0 ? 2147483648L : 0L);
            this.mt[i] = (int) (((j3 ^ ((j4 ^ (j4 >> 30)) * 1566083941)) - i) & 4294967295L);
            i++;
            if (i >= N) {
                this.mt[0] = this.mt[623];
                i = 1;
            }
        }
        this.mt[0] = Integer.MIN_VALUE;
    }

    @Override // org.apache.commons.math.random.BitsStreamGenerator, org.apache.commons.math.random.RandomGenerator
    public void setSeed(long j) {
        setSeed(new int[]{(int) (j >>> 32), (int) (j & 4294967295L)});
    }

    @Override // org.apache.commons.math.random.BitsStreamGenerator
    protected int next(int i) {
        int i2;
        if (this.mti >= N) {
            int i3 = this.mt[0];
            int i4 = 0;
            while (true) {
                i2 = com.android.internal.util.FrameworkStatsLog.CAMERA_ACTION_EVENT;
                if (i4 >= 227) {
                    break;
                }
                int i5 = i4 + 1;
                int i6 = this.mt[i5];
                int i7 = (i3 & Integer.MIN_VALUE) | (Integer.MAX_VALUE & i6);
                this.mt[i4] = MAG01[i7 & 1] ^ (this.mt[i4 + M] ^ (i7 >>> 1));
                i3 = i6;
                i4 = i5;
            }
            while (i2 < 623) {
                int i8 = i2 + 1;
                int i9 = this.mt[i8];
                int i10 = (i3 & Integer.MIN_VALUE) | (i9 & Integer.MAX_VALUE);
                this.mt[i2] = MAG01[i10 & 1] ^ (this.mt[i2 - 227] ^ (i10 >>> 1));
                i3 = i9;
                i2 = i8;
            }
            int i11 = (i3 & Integer.MIN_VALUE) | (this.mt[0] & Integer.MAX_VALUE);
            this.mt[623] = MAG01[i11 & 1] ^ (this.mt[396] ^ (i11 >>> 1));
            this.mti = 0;
        }
        int[] iArr = this.mt;
        int i12 = this.mti;
        this.mti = i12 + 1;
        int i13 = iArr[i12];
        int i14 = i13 ^ (i13 >>> 11);
        int i15 = i14 ^ ((i14 << 7) & (-1658038656));
        int i16 = i15 ^ ((i15 << 15) & (-272236544));
        return (i16 ^ (i16 >>> 18)) >>> (32 - i);
    }
}
