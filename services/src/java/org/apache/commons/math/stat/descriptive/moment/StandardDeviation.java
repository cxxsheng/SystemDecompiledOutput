package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class StandardDeviation extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable {
    private static final long serialVersionUID = 5728716329662425188L;
    private org.apache.commons.math.stat.descriptive.moment.Variance variance;

    public StandardDeviation() {
        this.variance = null;
        this.variance = new org.apache.commons.math.stat.descriptive.moment.Variance();
    }

    public StandardDeviation(org.apache.commons.math.stat.descriptive.moment.SecondMoment secondMoment) {
        this.variance = null;
        this.variance = new org.apache.commons.math.stat.descriptive.moment.Variance(secondMoment);
    }

    public StandardDeviation(org.apache.commons.math.stat.descriptive.moment.StandardDeviation standardDeviation) {
        this.variance = null;
        copy(standardDeviation, this);
    }

    public StandardDeviation(boolean z) {
        this.variance = null;
        this.variance = new org.apache.commons.math.stat.descriptive.moment.Variance(z);
    }

    public StandardDeviation(boolean z, org.apache.commons.math.stat.descriptive.moment.SecondMoment secondMoment) {
        this.variance = null;
        this.variance = new org.apache.commons.math.stat.descriptive.moment.Variance(z, secondMoment);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        this.variance.increment(d);
    }

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public long getN() {
        return this.variance.getN();
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        return org.apache.commons.math.util.FastMath.sqrt(this.variance.getResult());
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void clear() {
        this.variance.clear();
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr) {
        return org.apache.commons.math.util.FastMath.sqrt(this.variance.evaluate(dArr));
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        return org.apache.commons.math.util.FastMath.sqrt(this.variance.evaluate(dArr, i, i2));
    }

    public double evaluate(double[] dArr, double d, int i, int i2) {
        return org.apache.commons.math.util.FastMath.sqrt(this.variance.evaluate(dArr, d, i, i2));
    }

    public double evaluate(double[] dArr, double d) {
        return org.apache.commons.math.util.FastMath.sqrt(this.variance.evaluate(dArr, d));
    }

    public boolean isBiasCorrected() {
        return this.variance.isBiasCorrected();
    }

    public void setBiasCorrected(boolean z) {
        this.variance.setBiasCorrected(z);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.moment.StandardDeviation copy() {
        org.apache.commons.math.stat.descriptive.moment.StandardDeviation standardDeviation = new org.apache.commons.math.stat.descriptive.moment.StandardDeviation();
        copy(this, standardDeviation);
        return standardDeviation;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.moment.StandardDeviation standardDeviation, org.apache.commons.math.stat.descriptive.moment.StandardDeviation standardDeviation2) {
        standardDeviation2.setData(standardDeviation.getDataRef());
        standardDeviation2.variance = standardDeviation.variance.copy();
    }
}
