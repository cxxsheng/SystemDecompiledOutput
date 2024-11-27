package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public class Well19937c extends org.apache.commons.math.random.AbstractWell {
    private static final int K = 19937;
    private static final int M1 = 70;
    private static final int M2 = 179;
    private static final int M3 = 449;
    private static final long serialVersionUID = -7203498180754925124L;

    public Well19937c() {
        super(K, 70, 179, 449);
    }

    public Well19937c(int i) {
        super(K, 70, 179, 449, i);
    }

    public Well19937c(int[] iArr) {
        super(K, 70, 179, 449, iArr);
    }

    public Well19937c(long j) {
        super(K, 70, 179, 449, j);
    }

    @Override // org.apache.commons.math.random.AbstractWell, org.apache.commons.math.random.BitsStreamGenerator
    protected int next(int i) {
        int i2 = this.iRm1[this.index];
        int i3 = this.iRm2[this.index];
        int i4 = this.v[this.index];
        int i5 = this.v[this.i1[this.index]];
        int i6 = this.v[this.i2[this.index]];
        int i7 = this.v[this.i3[this.index]];
        int i8 = (i4 ^ (i4 << 25)) ^ (i5 ^ (i5 >>> 27));
        int i9 = (i6 >>> 9) ^ ((i7 >>> 1) ^ i7);
        int i10 = i8 ^ i9;
        int i11 = (((i8 ^ (i8 << 9)) ^ ((this.v[i2] & Integer.MIN_VALUE) ^ (this.v[i3] & Integer.MAX_VALUE))) ^ (i9 ^ (i9 << 21))) ^ ((i10 >>> 21) ^ i10);
        this.v[this.index] = i10;
        this.v[i2] = i11;
        int[] iArr = this.v;
        iArr[i3] = iArr[i3] & Integer.MIN_VALUE;
        this.index = i2;
        int i12 = ((i11 << 7) & (-462547200)) ^ i11;
        return (i12 ^ ((i12 << 15) & (-1685684224))) >>> (32 - i);
    }
}
