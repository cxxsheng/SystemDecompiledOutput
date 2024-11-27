package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public abstract class AbstractWell extends org.apache.commons.math.random.BitsStreamGenerator implements java.io.Serializable {
    private static final long serialVersionUID = -817701723016583596L;
    protected final int[] i1;
    protected final int[] i2;
    protected final int[] i3;
    protected final int[] iRm1;
    protected final int[] iRm2;
    protected int index;
    protected final int[] v;

    @Override // org.apache.commons.math.random.BitsStreamGenerator
    protected abstract int next(int i);

    protected AbstractWell(int i, int i2, int i3, int i4) {
        this(i, i2, i3, i4, java.lang.System.currentTimeMillis());
    }

    protected AbstractWell(int i, int i2, int i3, int i4, int i5) {
        this(i, i2, i3, i4, new int[]{i5});
    }

    protected AbstractWell(int i, int i2, int i3, int i4, int[] iArr) {
        int i5 = ((i + 32) - 1) / 32;
        this.v = new int[i5];
        this.index = 0;
        this.iRm1 = new int[i5];
        this.iRm2 = new int[i5];
        this.i1 = new int[i5];
        this.i2 = new int[i5];
        this.i3 = new int[i5];
        for (int i6 = 0; i6 < i5; i6++) {
            int i7 = i6 + i5;
            this.iRm1[i6] = (i7 - 1) % i5;
            this.iRm2[i6] = (i7 - 2) % i5;
            this.i1[i6] = (i6 + i2) % i5;
            this.i2[i6] = (i6 + i3) % i5;
            this.i3[i6] = (i6 + i4) % i5;
        }
        setSeed(iArr);
    }

    protected AbstractWell(int i, int i2, int i3, int i4, long j) {
        this(i, i2, i3, i4, new int[]{(int) (j >>> 32), (int) (j & 4294967295L)});
    }

    @Override // org.apache.commons.math.random.BitsStreamGenerator, org.apache.commons.math.random.RandomGenerator
    public void setSeed(int i) {
        setSeed(new int[]{i});
    }

    @Override // org.apache.commons.math.random.BitsStreamGenerator, org.apache.commons.math.random.RandomGenerator
    public void setSeed(int[] iArr) {
        if (iArr == null) {
            setSeed(java.lang.System.currentTimeMillis());
            return;
        }
        java.lang.System.arraycopy(iArr, 0, this.v, 0, java.lang.Math.min(iArr.length, this.v.length));
        if (iArr.length < this.v.length) {
            for (int length = iArr.length; length < this.v.length; length++) {
                long j = this.v[length - iArr.length];
                this.v[length] = (int) ((((j ^ (j >> 30)) * 1812433253) + length) & 4294967295L);
            }
        }
        this.index = 0;
    }

    @Override // org.apache.commons.math.random.BitsStreamGenerator, org.apache.commons.math.random.RandomGenerator
    public void setSeed(long j) {
        setSeed(new int[]{(int) (j >>> 32), (int) (j & 4294967295L)});
    }
}
