package org.apache.commons.math.stat.descriptive.summary;

/* loaded from: classes3.dex */
public class SumOfSquares extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable {
    private static final long serialVersionUID = 1460986908574398008L;
    private long n;
    private double value;

    public SumOfSquares() {
        this.n = 0L;
        this.value = Double.NaN;
    }

    public SumOfSquares(org.apache.commons.math.stat.descriptive.summary.SumOfSquares sumOfSquares) {
        copy(sumOfSquares, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        if (this.n == 0) {
            this.value = d * d;
        } else {
            this.value += d * d;
        }
        this.n++;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        return this.value;
    }

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public long getN() {
        return this.n;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void clear() {
        this.value = Double.NaN;
        this.n = 0L;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        if (!test(dArr, i, i2)) {
            return Double.NaN;
        }
        double d = 0.0d;
        for (int i3 = i; i3 < i + i2; i3++) {
            d += dArr[i3] * dArr[i3];
        }
        return d;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.summary.SumOfSquares copy() {
        org.apache.commons.math.stat.descriptive.summary.SumOfSquares sumOfSquares = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
        copy(this, sumOfSquares);
        return sumOfSquares;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.summary.SumOfSquares sumOfSquares, org.apache.commons.math.stat.descriptive.summary.SumOfSquares sumOfSquares2) {
        sumOfSquares2.setData(sumOfSquares.getDataRef());
        sumOfSquares2.n = sumOfSquares.n;
        sumOfSquares2.value = sumOfSquares.value;
    }
}
