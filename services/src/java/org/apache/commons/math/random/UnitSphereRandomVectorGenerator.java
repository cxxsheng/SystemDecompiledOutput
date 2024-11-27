package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public class UnitSphereRandomVectorGenerator implements org.apache.commons.math.random.RandomVectorGenerator {
    private final int dimension;
    private final org.apache.commons.math.random.RandomGenerator rand;

    public UnitSphereRandomVectorGenerator(int i, org.apache.commons.math.random.RandomGenerator randomGenerator) {
        this.dimension = i;
        this.rand = randomGenerator;
    }

    public UnitSphereRandomVectorGenerator(int i) {
        this(i, new org.apache.commons.math.random.MersenneTwister());
    }

    @Override // org.apache.commons.math.random.RandomVectorGenerator
    public double[] nextVector() {
        int i;
        double d;
        double[] dArr = new double[this.dimension];
        do {
            d = 0.0d;
            for (int i2 = 0; i2 < this.dimension; i2++) {
                double nextDouble = (this.rand.nextDouble() * 2.0d) - 1.0d;
                dArr[i2] = nextDouble;
                d += nextDouble * nextDouble;
            }
        } while (d > 1.0d);
        double sqrt = 1.0d / org.apache.commons.math.util.FastMath.sqrt(d);
        for (i = 0; i < this.dimension; i++) {
            dArr[i] = dArr[i] * sqrt;
        }
        return dArr;
    }
}
