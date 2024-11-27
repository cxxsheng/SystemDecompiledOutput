package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public class UncorrelatedRandomVectorGenerator implements org.apache.commons.math.random.RandomVectorGenerator {
    private final org.apache.commons.math.random.NormalizedRandomGenerator generator;
    private final double[] mean;
    private final double[] standardDeviation;

    public UncorrelatedRandomVectorGenerator(double[] dArr, double[] dArr2, org.apache.commons.math.random.NormalizedRandomGenerator normalizedRandomGenerator) {
        if (dArr.length != dArr2.length) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(dArr.length, dArr2.length);
        }
        this.mean = (double[]) dArr.clone();
        this.standardDeviation = (double[]) dArr2.clone();
        this.generator = normalizedRandomGenerator;
    }

    public UncorrelatedRandomVectorGenerator(int i, org.apache.commons.math.random.NormalizedRandomGenerator normalizedRandomGenerator) {
        this.mean = new double[i];
        this.standardDeviation = new double[i];
        java.util.Arrays.fill(this.standardDeviation, 1.0d);
        this.generator = normalizedRandomGenerator;
    }

    @Override // org.apache.commons.math.random.RandomVectorGenerator
    public double[] nextVector() {
        int length = this.mean.length;
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            dArr[i] = this.mean[i] + (this.standardDeviation[i] * this.generator.nextNormalizedDouble());
        }
        return dArr;
    }
}
