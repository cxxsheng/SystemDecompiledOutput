package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class VectorialCovariance implements java.io.Serializable {
    private static final long serialVersionUID = 4118372414238930270L;
    private final boolean isBiasCorrected;
    private long n = 0;
    private final double[] productsSums;
    private final double[] sums;

    public VectorialCovariance(int i, boolean z) {
        this.sums = new double[i];
        this.productsSums = new double[(i * (i + 1)) / 2];
        this.isBiasCorrected = z;
    }

    public void increment(double[] dArr) throws org.apache.commons.math.DimensionMismatchException {
        if (dArr.length != this.sums.length) {
            throw new org.apache.commons.math.DimensionMismatchException(dArr.length, this.sums.length);
        }
        int i = 0;
        for (int i2 = 0; i2 < dArr.length; i2++) {
            double[] dArr2 = this.sums;
            dArr2[i2] = dArr2[i2] + dArr[i2];
            int i3 = 0;
            while (i3 <= i2) {
                double[] dArr3 = this.productsSums;
                dArr3[i] = dArr3[i] + (dArr[i2] * dArr[i3]);
                i3++;
                i++;
            }
        }
        this.n++;
    }

    public org.apache.commons.math.linear.RealMatrix getResult() {
        int length = this.sums.length;
        org.apache.commons.math.linear.RealMatrix createRealMatrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length);
        if (this.n > 1) {
            double d = 1.0d / (this.n * (this.isBiasCorrected ? this.n - 1 : this.n));
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = 0;
                while (i3 <= i2) {
                    double d2 = ((this.n * this.productsSums[i]) - (this.sums[i2] * this.sums[i3])) * d;
                    createRealMatrix.setEntry(i2, i3, d2);
                    createRealMatrix.setEntry(i3, i2, d2);
                    i3++;
                    i++;
                }
            }
        }
        return createRealMatrix;
    }

    public long getN() {
        return this.n;
    }

    public void clear() {
        this.n = 0L;
        java.util.Arrays.fill(this.sums, 0.0d);
        java.util.Arrays.fill(this.productsSums, 0.0d);
    }

    public int hashCode() {
        return (((((((this.isBiasCorrected ? 1231 : 1237) + 31) * 31) + ((int) (this.n ^ (this.n >>> 32)))) * 31) + java.util.Arrays.hashCode(this.productsSums)) * 31) + java.util.Arrays.hashCode(this.sums);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.stat.descriptive.moment.VectorialCovariance)) {
            return false;
        }
        org.apache.commons.math.stat.descriptive.moment.VectorialCovariance vectorialCovariance = (org.apache.commons.math.stat.descriptive.moment.VectorialCovariance) obj;
        return this.isBiasCorrected == vectorialCovariance.isBiasCorrected && this.n == vectorialCovariance.n && java.util.Arrays.equals(this.productsSums, vectorialCovariance.productsSums) && java.util.Arrays.equals(this.sums, vectorialCovariance.sums);
    }
}
