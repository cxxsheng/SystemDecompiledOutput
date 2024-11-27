package org.apache.commons.math.stat.descriptive.summary;

/* loaded from: classes3.dex */
public class SumOfLogs extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable {
    private static final long serialVersionUID = -370076995648386763L;
    private int n;
    private double value;

    public SumOfLogs() {
        this.value = 0.0d;
        this.n = 0;
    }

    public SumOfLogs(org.apache.commons.math.stat.descriptive.summary.SumOfLogs sumOfLogs) {
        copy(sumOfLogs, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        this.value += org.apache.commons.math.util.FastMath.log(d);
        this.n++;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        if (this.n > 0) {
            return this.value;
        }
        return Double.NaN;
    }

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public long getN() {
        return this.n;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void clear() {
        this.value = 0.0d;
        this.n = 0;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        if (!test(dArr, i, i2)) {
            return Double.NaN;
        }
        double d = 0.0d;
        for (int i3 = i; i3 < i + i2; i3++) {
            d += org.apache.commons.math.util.FastMath.log(dArr[i3]);
        }
        return d;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.summary.SumOfLogs copy() {
        org.apache.commons.math.stat.descriptive.summary.SumOfLogs sumOfLogs = new org.apache.commons.math.stat.descriptive.summary.SumOfLogs();
        copy(this, sumOfLogs);
        return sumOfLogs;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.summary.SumOfLogs sumOfLogs, org.apache.commons.math.stat.descriptive.summary.SumOfLogs sumOfLogs2) {
        sumOfLogs2.setData(sumOfLogs.getDataRef());
        sumOfLogs2.n = sumOfLogs.n;
        sumOfLogs2.value = sumOfLogs.value;
    }
}
