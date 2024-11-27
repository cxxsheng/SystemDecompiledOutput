package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class GeometricMean extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable {
    private static final long serialVersionUID = -8178734905303459453L;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumOfLogs;

    public GeometricMean() {
        this.sumOfLogs = new org.apache.commons.math.stat.descriptive.summary.SumOfLogs();
    }

    public GeometricMean(org.apache.commons.math.stat.descriptive.moment.GeometricMean geometricMean) {
        copy(geometricMean, this);
    }

    public GeometricMean(org.apache.commons.math.stat.descriptive.summary.SumOfLogs sumOfLogs) {
        this.sumOfLogs = sumOfLogs;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.moment.GeometricMean copy() {
        org.apache.commons.math.stat.descriptive.moment.GeometricMean geometricMean = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
        copy(this, geometricMean);
        return geometricMean;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        this.sumOfLogs.increment(d);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        if (this.sumOfLogs.getN() > 0) {
            return org.apache.commons.math.util.FastMath.exp(this.sumOfLogs.getResult() / this.sumOfLogs.getN());
        }
        return Double.NaN;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void clear() {
        this.sumOfLogs.clear();
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        return org.apache.commons.math.util.FastMath.exp(this.sumOfLogs.evaluate(dArr, i, i2) / i2);
    }

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public long getN() {
        return this.sumOfLogs.getN();
    }

    public void setSumLogImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        checkEmpty();
        this.sumOfLogs = storelessUnivariateStatistic;
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumLogImpl() {
        return this.sumOfLogs;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.moment.GeometricMean geometricMean, org.apache.commons.math.stat.descriptive.moment.GeometricMean geometricMean2) {
        geometricMean2.setData(geometricMean.getDataRef());
        geometricMean2.sumOfLogs = geometricMean.sumOfLogs.copy();
    }

    private void checkEmpty() {
        if (getN() > 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.VALUES_ADDED_BEFORE_CONFIGURING_STATISTIC, java.lang.Long.valueOf(getN()));
        }
    }
}
