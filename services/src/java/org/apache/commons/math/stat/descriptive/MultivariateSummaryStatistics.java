package org.apache.commons.math.stat.descriptive;

/* loaded from: classes3.dex */
public class MultivariateSummaryStatistics implements org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary, java.io.Serializable {
    private static final long serialVersionUID = 2271900808994826718L;
    private org.apache.commons.math.stat.descriptive.moment.VectorialCovariance covarianceImpl;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] geoMeanImpl;
    private int k;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] maxImpl;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] meanImpl;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] minImpl;
    private long n = 0;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] sumImpl;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] sumLogImpl;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] sumSqImpl;

    public MultivariateSummaryStatistics(int i, boolean z) {
        this.k = i;
        this.sumImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[i];
        this.sumSqImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[i];
        this.minImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[i];
        this.maxImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[i];
        this.sumLogImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[i];
        this.geoMeanImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[i];
        this.meanImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.sumImpl[i2] = new org.apache.commons.math.stat.descriptive.summary.Sum();
            this.sumSqImpl[i2] = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
            this.minImpl[i2] = new org.apache.commons.math.stat.descriptive.rank.Min();
            this.maxImpl[i2] = new org.apache.commons.math.stat.descriptive.rank.Max();
            this.sumLogImpl[i2] = new org.apache.commons.math.stat.descriptive.summary.SumOfLogs();
            this.geoMeanImpl[i2] = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
            this.meanImpl[i2] = new org.apache.commons.math.stat.descriptive.moment.Mean();
        }
        this.covarianceImpl = new org.apache.commons.math.stat.descriptive.moment.VectorialCovariance(i, z);
    }

    public void addValue(double[] dArr) throws org.apache.commons.math.DimensionMismatchException {
        checkDimension(dArr.length);
        for (int i = 0; i < this.k; i++) {
            double d = dArr[i];
            this.sumImpl[i].increment(d);
            this.sumSqImpl[i].increment(d);
            this.minImpl[i].increment(d);
            this.maxImpl[i].increment(d);
            this.sumLogImpl[i].increment(d);
            this.geoMeanImpl[i].increment(d);
            this.meanImpl[i].increment(d);
        }
        this.covarianceImpl.increment(dArr);
        this.n++;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary
    public int getDimension() {
        return this.k;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary
    public long getN() {
        return this.n;
    }

    private double[] getResults(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] storelessUnivariateStatisticArr) {
        int length = storelessUnivariateStatisticArr.length;
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            dArr[i] = storelessUnivariateStatisticArr[i].getResult();
        }
        return dArr;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary
    public double[] getSum() {
        return getResults(this.sumImpl);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary
    public double[] getSumSq() {
        return getResults(this.sumSqImpl);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary
    public double[] getSumLog() {
        return getResults(this.sumLogImpl);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary
    public double[] getMean() {
        return getResults(this.meanImpl);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary
    public double[] getStandardDeviation() {
        double[] dArr = new double[this.k];
        if (getN() < 1) {
            java.util.Arrays.fill(dArr, Double.NaN);
        } else if (getN() < 2) {
            java.util.Arrays.fill(dArr, 0.0d);
        } else {
            org.apache.commons.math.linear.RealMatrix result = this.covarianceImpl.getResult();
            for (int i = 0; i < this.k; i++) {
                dArr[i] = org.apache.commons.math.util.FastMath.sqrt(result.getEntry(i, i));
            }
        }
        return dArr;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary
    public org.apache.commons.math.linear.RealMatrix getCovariance() {
        return this.covarianceImpl.getResult();
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary
    public double[] getMax() {
        return getResults(this.maxImpl);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary
    public double[] getMin() {
        return getResults(this.minImpl);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary
    public double[] getGeometricMean() {
        return getResults(this.geoMeanImpl);
    }

    public java.lang.String toString() {
        java.lang.String property = java.lang.System.getProperty("line.separator");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("MultivariateSummaryStatistics:" + property);
        sb.append("n: " + getN() + property);
        append(sb, getMin(), "min: ", ", ", property);
        append(sb, getMax(), "max: ", ", ", property);
        append(sb, getMean(), "mean: ", ", ", property);
        append(sb, getGeometricMean(), "geometric mean: ", ", ", property);
        append(sb, getSumSq(), "sum of squares: ", ", ", property);
        append(sb, getSumLog(), "sum of logarithms: ", ", ", property);
        append(sb, getStandardDeviation(), "standard deviation: ", ", ", property);
        sb.append("covariance: " + getCovariance().toString() + property);
        return sb.toString();
    }

    private void append(java.lang.StringBuilder sb, double[] dArr, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        sb.append(str);
        for (int i = 0; i < dArr.length; i++) {
            if (i > 0) {
                sb.append(str2);
            }
            sb.append(dArr[i]);
        }
        sb.append(str3);
    }

    public void clear() {
        this.n = 0L;
        for (int i = 0; i < this.k; i++) {
            this.minImpl[i].clear();
            this.maxImpl[i].clear();
            this.sumImpl[i].clear();
            this.sumLogImpl[i].clear();
            this.sumSqImpl[i].clear();
            this.geoMeanImpl[i].clear();
            this.meanImpl[i].clear();
        }
        this.covarianceImpl.clear();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.stat.descriptive.MultivariateSummaryStatistics)) {
            return false;
        }
        org.apache.commons.math.stat.descriptive.MultivariateSummaryStatistics multivariateSummaryStatistics = (org.apache.commons.math.stat.descriptive.MultivariateSummaryStatistics) obj;
        return org.apache.commons.math.util.MathUtils.equalsIncludingNaN(multivariateSummaryStatistics.getGeometricMean(), getGeometricMean()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(multivariateSummaryStatistics.getMax(), getMax()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(multivariateSummaryStatistics.getMean(), getMean()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(multivariateSummaryStatistics.getMin(), getMin()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN((float) multivariateSummaryStatistics.getN(), (float) getN()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(multivariateSummaryStatistics.getSum(), getSum()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(multivariateSummaryStatistics.getSumSq(), getSumSq()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(multivariateSummaryStatistics.getSumLog(), getSumLog()) && multivariateSummaryStatistics.getCovariance().equals(getCovariance());
    }

    public int hashCode() {
        return ((((((((((((((((((org.apache.commons.math.util.MathUtils.hash(getGeometricMean()) + 31) * 31) + org.apache.commons.math.util.MathUtils.hash(getGeometricMean())) * 31) + org.apache.commons.math.util.MathUtils.hash(getMax())) * 31) + org.apache.commons.math.util.MathUtils.hash(getMean())) * 31) + org.apache.commons.math.util.MathUtils.hash(getMin())) * 31) + org.apache.commons.math.util.MathUtils.hash(getN())) * 31) + org.apache.commons.math.util.MathUtils.hash(getSum())) * 31) + org.apache.commons.math.util.MathUtils.hash(getSumSq())) * 31) + org.apache.commons.math.util.MathUtils.hash(getSumLog())) * 31) + getCovariance().hashCode();
    }

    private void setImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] storelessUnivariateStatisticArr, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] storelessUnivariateStatisticArr2) throws org.apache.commons.math.DimensionMismatchException, java.lang.IllegalStateException {
        checkEmpty();
        checkDimension(storelessUnivariateStatisticArr.length);
        java.lang.System.arraycopy(storelessUnivariateStatisticArr, 0, storelessUnivariateStatisticArr2, 0, storelessUnivariateStatisticArr.length);
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getSumImpl() {
        return (org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[]) this.sumImpl.clone();
    }

    public void setSumImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] storelessUnivariateStatisticArr) throws org.apache.commons.math.DimensionMismatchException {
        setImpl(storelessUnivariateStatisticArr, this.sumImpl);
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getSumsqImpl() {
        return (org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[]) this.sumSqImpl.clone();
    }

    public void setSumsqImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] storelessUnivariateStatisticArr) throws org.apache.commons.math.DimensionMismatchException {
        setImpl(storelessUnivariateStatisticArr, this.sumSqImpl);
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getMinImpl() {
        return (org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[]) this.minImpl.clone();
    }

    public void setMinImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] storelessUnivariateStatisticArr) throws org.apache.commons.math.DimensionMismatchException {
        setImpl(storelessUnivariateStatisticArr, this.minImpl);
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getMaxImpl() {
        return (org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[]) this.maxImpl.clone();
    }

    public void setMaxImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] storelessUnivariateStatisticArr) throws org.apache.commons.math.DimensionMismatchException {
        setImpl(storelessUnivariateStatisticArr, this.maxImpl);
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getSumLogImpl() {
        return (org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[]) this.sumLogImpl.clone();
    }

    public void setSumLogImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] storelessUnivariateStatisticArr) throws org.apache.commons.math.DimensionMismatchException {
        setImpl(storelessUnivariateStatisticArr, this.sumLogImpl);
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getGeoMeanImpl() {
        return (org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[]) this.geoMeanImpl.clone();
    }

    public void setGeoMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] storelessUnivariateStatisticArr) throws org.apache.commons.math.DimensionMismatchException {
        setImpl(storelessUnivariateStatisticArr, this.geoMeanImpl);
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getMeanImpl() {
        return (org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[]) this.meanImpl.clone();
    }

    public void setMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] storelessUnivariateStatisticArr) throws org.apache.commons.math.DimensionMismatchException {
        setImpl(storelessUnivariateStatisticArr, this.meanImpl);
    }

    private void checkEmpty() {
        if (this.n > 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.VALUES_ADDED_BEFORE_CONFIGURING_STATISTIC, java.lang.Long.valueOf(this.n));
        }
    }

    private void checkDimension(int i) throws org.apache.commons.math.DimensionMismatchException {
        if (i != this.k) {
            throw new org.apache.commons.math.DimensionMismatchException(i, this.k);
        }
    }
}
