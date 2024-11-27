package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public abstract class AbstractRandomGenerator implements org.apache.commons.math.random.RandomGenerator {
    private double cachedNormalDeviate = Double.NaN;

    @Override // org.apache.commons.math.random.RandomGenerator
    public abstract double nextDouble();

    @Override // org.apache.commons.math.random.RandomGenerator
    public abstract void setSeed(long j);

    public void clear() {
        this.cachedNormalDeviate = Double.NaN;
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public void setSeed(int i) {
        setSeed(i);
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public void setSeed(int[] iArr) {
        long j = 0;
        for (int i : iArr) {
            j = (j * 4294967291L) + i;
        }
        setSeed(j);
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public void nextBytes(byte[] bArr) {
        int i = 0;
        while (i < bArr.length) {
            int nextInt = nextInt();
            int i2 = 0;
            while (i2 < 3) {
                if (i2 > 0) {
                    nextInt >>= 8;
                }
                int i3 = i + 1;
                bArr[i] = (byte) nextInt;
                if (i3 != bArr.length) {
                    i2++;
                    i = i3;
                } else {
                    return;
                }
            }
        }
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public int nextInt() {
        return (int) (nextDouble() * 2.147483647E9d);
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public int nextInt(int i) {
        if (i <= 0) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(java.lang.Integer.valueOf(i));
        }
        int nextDouble = (int) (nextDouble() * i);
        return nextDouble < i ? nextDouble : i - 1;
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public long nextLong() {
        return (long) (nextDouble() * 9.223372036854776E18d);
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public boolean nextBoolean() {
        return nextDouble() <= 0.5d;
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public float nextFloat() {
        return (float) nextDouble();
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public double nextGaussian() {
        if (!java.lang.Double.isNaN(this.cachedNormalDeviate)) {
            double d = this.cachedNormalDeviate;
            this.cachedNormalDeviate = Double.NaN;
            return d;
        }
        double d2 = 0.0d;
        double d3 = 0.0d;
        double d4 = 1.0d;
        while (d4 >= 1.0d) {
            double nextDouble = (nextDouble() * 2.0d) - 1.0d;
            d3 = (nextDouble() * 2.0d) - 1.0d;
            d4 = (nextDouble * nextDouble) + (d3 * d3);
            d2 = nextDouble;
        }
        if (d4 != 0.0d) {
            d4 = org.apache.commons.math.util.FastMath.sqrt((org.apache.commons.math.util.FastMath.log(d4) * (-2.0d)) / d4);
        }
        this.cachedNormalDeviate = d3 * d4;
        return d2 * d4;
    }
}
