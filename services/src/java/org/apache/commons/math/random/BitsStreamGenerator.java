package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public abstract class BitsStreamGenerator implements org.apache.commons.math.random.RandomGenerator {
    private double nextGaussian = Double.NaN;

    protected abstract int next(int i);

    @Override // org.apache.commons.math.random.RandomGenerator
    public abstract void setSeed(int i);

    @Override // org.apache.commons.math.random.RandomGenerator
    public abstract void setSeed(long j);

    @Override // org.apache.commons.math.random.RandomGenerator
    public abstract void setSeed(int[] iArr);

    @Override // org.apache.commons.math.random.RandomGenerator
    public boolean nextBoolean() {
        return next(1) != 0;
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public void nextBytes(byte[] bArr) {
        int length = bArr.length - 3;
        int i = 0;
        while (i < length) {
            int next = next(32);
            bArr[i] = (byte) (next & 255);
            bArr[i + 1] = (byte) ((next >> 8) & 255);
            bArr[i + 2] = (byte) ((next >> 16) & 255);
            bArr[i + 3] = (byte) ((next >> 24) & 255);
            i += 4;
        }
        int next2 = next(32);
        while (i < bArr.length) {
            bArr[i] = (byte) (next2 & 255);
            next2 >>= 8;
            i++;
        }
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public double nextDouble() {
        return ((next(26) << 26) | next(26)) * 2.220446049250313E-16d;
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public float nextFloat() {
        return next(23) * 1.1920929E-7f;
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public double nextGaussian() {
        if (java.lang.Double.isNaN(this.nextGaussian)) {
            double nextDouble = nextDouble() * 6.283185307179586d;
            double sqrt = org.apache.commons.math.util.FastMath.sqrt(org.apache.commons.math.util.FastMath.log(nextDouble()) * (-2.0d));
            double cos = org.apache.commons.math.util.FastMath.cos(nextDouble) * sqrt;
            this.nextGaussian = sqrt * org.apache.commons.math.util.FastMath.sin(nextDouble);
            return cos;
        }
        double d = this.nextGaussian;
        this.nextGaussian = Double.NaN;
        return d;
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public int nextInt() {
        return next(32);
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public int nextInt(int i) throws java.lang.IllegalArgumentException {
        int next;
        if (i < 1) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(java.lang.Integer.valueOf(i));
        }
        int i2 = (i >> 1) | i;
        int i3 = i2 | (i2 >> 2);
        int i4 = i3 | (i3 >> 4);
        int i5 = i4 | (i4 >> 8);
        int i6 = i5 | (i5 >> 16);
        do {
            next = next(32) & i6;
        } while (next >= i);
        return next;
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public long nextLong() {
        return (next(32) << 32) | (next(32) & 4294967295L);
    }
}
