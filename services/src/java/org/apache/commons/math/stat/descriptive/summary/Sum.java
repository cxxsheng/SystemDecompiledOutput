package org.apache.commons.math.stat.descriptive.summary;

/* loaded from: classes3.dex */
public class Sum extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable {
    private static final long serialVersionUID = -8231831954703408316L;
    private long n;
    private double value;

    public Sum() {
        this.n = 0L;
        this.value = Double.NaN;
    }

    public Sum(org.apache.commons.math.stat.descriptive.summary.Sum sum) {
        copy(sum, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        if (this.n == 0) {
            this.value = d;
        } else {
            this.value += d;
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
            d += dArr[i3];
        }
        return d;
    }

    public double evaluate(double[] dArr, double[] dArr2, int i, int i2) {
        if (!test(dArr, dArr2, i, i2)) {
            return Double.NaN;
        }
        double d = 0.0d;
        for (int i3 = i; i3 < i + i2; i3++) {
            d += dArr[i3] * dArr2[i3];
        }
        return d;
    }

    public double evaluate(double[] dArr, double[] dArr2) {
        return evaluate(dArr, dArr2, 0, dArr.length);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.summary.Sum copy() {
        org.apache.commons.math.stat.descriptive.summary.Sum sum = new org.apache.commons.math.stat.descriptive.summary.Sum();
        copy(this, sum);
        return sum;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.summary.Sum sum, org.apache.commons.math.stat.descriptive.summary.Sum sum2) {
        sum2.setData(sum.getDataRef());
        sum2.n = sum.n;
        sum2.value = sum.value;
    }
}
