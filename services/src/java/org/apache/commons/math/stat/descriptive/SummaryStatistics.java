package org.apache.commons.math.stat.descriptive;

/* loaded from: classes3.dex */
public class SummaryStatistics implements org.apache.commons.math.stat.descriptive.StatisticalSummary, java.io.Serializable {
    private static final long serialVersionUID = -2021321786743555871L;
    protected long n = 0;
    protected org.apache.commons.math.stat.descriptive.moment.SecondMoment secondMoment = new org.apache.commons.math.stat.descriptive.moment.SecondMoment();
    protected org.apache.commons.math.stat.descriptive.summary.Sum sum = new org.apache.commons.math.stat.descriptive.summary.Sum();
    protected org.apache.commons.math.stat.descriptive.summary.SumOfSquares sumsq = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
    protected org.apache.commons.math.stat.descriptive.rank.Min min = new org.apache.commons.math.stat.descriptive.rank.Min();
    protected org.apache.commons.math.stat.descriptive.rank.Max max = new org.apache.commons.math.stat.descriptive.rank.Max();
    protected org.apache.commons.math.stat.descriptive.summary.SumOfLogs sumLog = new org.apache.commons.math.stat.descriptive.summary.SumOfLogs();
    protected org.apache.commons.math.stat.descriptive.moment.GeometricMean geoMean = new org.apache.commons.math.stat.descriptive.moment.GeometricMean(this.sumLog);
    protected org.apache.commons.math.stat.descriptive.moment.Mean mean = new org.apache.commons.math.stat.descriptive.moment.Mean();
    protected org.apache.commons.math.stat.descriptive.moment.Variance variance = new org.apache.commons.math.stat.descriptive.moment.Variance();
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumImpl = this.sum;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumsqImpl = this.sumsq;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic minImpl = this.min;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic maxImpl = this.max;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumLogImpl = this.sumLog;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic geoMeanImpl = this.geoMean;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic meanImpl = this.mean;
    private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic varianceImpl = this.variance;

    public SummaryStatistics() {
    }

    public SummaryStatistics(org.apache.commons.math.stat.descriptive.SummaryStatistics summaryStatistics) {
        copy(summaryStatistics, this);
    }

    public org.apache.commons.math.stat.descriptive.StatisticalSummary getSummary() {
        return new org.apache.commons.math.stat.descriptive.StatisticalSummaryValues(getMean(), getVariance(), getN(), getMax(), getMin(), getSum());
    }

    public void addValue(double d) {
        this.sumImpl.increment(d);
        this.sumsqImpl.increment(d);
        this.minImpl.increment(d);
        this.maxImpl.increment(d);
        this.sumLogImpl.increment(d);
        this.secondMoment.increment(d);
        if (!(this.meanImpl instanceof org.apache.commons.math.stat.descriptive.moment.Mean)) {
            this.meanImpl.increment(d);
        }
        if (!(this.varianceImpl instanceof org.apache.commons.math.stat.descriptive.moment.Variance)) {
            this.varianceImpl.increment(d);
        }
        if (!(this.geoMeanImpl instanceof org.apache.commons.math.stat.descriptive.moment.GeometricMean)) {
            this.geoMeanImpl.increment(d);
        }
        this.n++;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public long getN() {
        return this.n;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getSum() {
        return this.sumImpl.getResult();
    }

    public double getSumsq() {
        return this.sumsqImpl.getResult();
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMean() {
        if (this.mean == this.meanImpl) {
            return new org.apache.commons.math.stat.descriptive.moment.Mean(this.secondMoment).getResult();
        }
        return this.meanImpl.getResult();
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getStandardDeviation() {
        if (getN() <= 0) {
            return Double.NaN;
        }
        if (getN() > 1) {
            return org.apache.commons.math.util.FastMath.sqrt(getVariance());
        }
        return 0.0d;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getVariance() {
        if (this.varianceImpl == this.variance) {
            return new org.apache.commons.math.stat.descriptive.moment.Variance(this.secondMoment).getResult();
        }
        return this.varianceImpl.getResult();
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMax() {
        return this.maxImpl.getResult();
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMin() {
        return this.minImpl.getResult();
    }

    public double getGeometricMean() {
        return this.geoMeanImpl.getResult();
    }

    public double getSumOfLogs() {
        return this.sumLogImpl.getResult();
    }

    public double getSecondMoment() {
        return this.secondMoment.getResult();
    }

    public java.lang.String toString() {
        return "SummaryStatistics:\nn: " + getN() + "\nmin: " + getMin() + "\nmax: " + getMax() + "\nmean: " + getMean() + "\ngeometric mean: " + getGeometricMean() + "\nvariance: " + getVariance() + "\nsum of squares: " + getSumsq() + "\nstandard deviation: " + getStandardDeviation() + "\nsum of logs: " + getSumOfLogs() + "\n";
    }

    public void clear() {
        this.n = 0L;
        this.minImpl.clear();
        this.maxImpl.clear();
        this.sumImpl.clear();
        this.sumLogImpl.clear();
        this.sumsqImpl.clear();
        this.geoMeanImpl.clear();
        this.secondMoment.clear();
        if (this.meanImpl != this.mean) {
            this.meanImpl.clear();
        }
        if (this.varianceImpl != this.variance) {
            this.varianceImpl.clear();
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.stat.descriptive.SummaryStatistics)) {
            return false;
        }
        org.apache.commons.math.stat.descriptive.SummaryStatistics summaryStatistics = (org.apache.commons.math.stat.descriptive.SummaryStatistics) obj;
        return org.apache.commons.math.util.MathUtils.equalsIncludingNaN(summaryStatistics.getGeometricMean(), getGeometricMean()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(summaryStatistics.getMax(), getMax()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(summaryStatistics.getMean(), getMean()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(summaryStatistics.getMin(), getMin()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN((float) summaryStatistics.getN(), (float) getN()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(summaryStatistics.getSum(), getSum()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(summaryStatistics.getSumsq(), getSumsq()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(summaryStatistics.getVariance(), getVariance());
    }

    public int hashCode() {
        return ((((((((((((((((org.apache.commons.math.util.MathUtils.hash(getGeometricMean()) + 31) * 31) + org.apache.commons.math.util.MathUtils.hash(getGeometricMean())) * 31) + org.apache.commons.math.util.MathUtils.hash(getMax())) * 31) + org.apache.commons.math.util.MathUtils.hash(getMean())) * 31) + org.apache.commons.math.util.MathUtils.hash(getMin())) * 31) + org.apache.commons.math.util.MathUtils.hash(getN())) * 31) + org.apache.commons.math.util.MathUtils.hash(getSum())) * 31) + org.apache.commons.math.util.MathUtils.hash(getSumsq())) * 31) + org.apache.commons.math.util.MathUtils.hash(getVariance());
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumImpl() {
        return this.sumImpl;
    }

    public void setSumImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        checkEmpty();
        this.sumImpl = storelessUnivariateStatistic;
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumsqImpl() {
        return this.sumsqImpl;
    }

    public void setSumsqImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        checkEmpty();
        this.sumsqImpl = storelessUnivariateStatistic;
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMinImpl() {
        return this.minImpl;
    }

    public void setMinImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        checkEmpty();
        this.minImpl = storelessUnivariateStatistic;
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMaxImpl() {
        return this.maxImpl;
    }

    public void setMaxImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        checkEmpty();
        this.maxImpl = storelessUnivariateStatistic;
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumLogImpl() {
        return this.sumLogImpl;
    }

    public void setSumLogImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        checkEmpty();
        this.sumLogImpl = storelessUnivariateStatistic;
        this.geoMean.setSumLogImpl(storelessUnivariateStatistic);
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getGeoMeanImpl() {
        return this.geoMeanImpl;
    }

    public void setGeoMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        checkEmpty();
        this.geoMeanImpl = storelessUnivariateStatistic;
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMeanImpl() {
        return this.meanImpl;
    }

    public void setMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        checkEmpty();
        this.meanImpl = storelessUnivariateStatistic;
    }

    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getVarianceImpl() {
        return this.varianceImpl;
    }

    public void setVarianceImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        checkEmpty();
        this.varianceImpl = storelessUnivariateStatistic;
    }

    private void checkEmpty() {
        if (this.n > 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.VALUES_ADDED_BEFORE_CONFIGURING_STATISTIC, java.lang.Long.valueOf(this.n));
        }
    }

    public org.apache.commons.math.stat.descriptive.SummaryStatistics copy() {
        org.apache.commons.math.stat.descriptive.SummaryStatistics summaryStatistics = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        copy(this, summaryStatistics);
        return summaryStatistics;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.SummaryStatistics summaryStatistics, org.apache.commons.math.stat.descriptive.SummaryStatistics summaryStatistics2) {
        summaryStatistics2.maxImpl = summaryStatistics.maxImpl.copy();
        summaryStatistics2.meanImpl = summaryStatistics.meanImpl.copy();
        summaryStatistics2.minImpl = summaryStatistics.minImpl.copy();
        summaryStatistics2.sumImpl = summaryStatistics.sumImpl.copy();
        summaryStatistics2.varianceImpl = summaryStatistics.varianceImpl.copy();
        summaryStatistics2.sumLogImpl = summaryStatistics.sumLogImpl.copy();
        summaryStatistics2.sumsqImpl = summaryStatistics.sumsqImpl.copy();
        if (summaryStatistics.getGeoMeanImpl() instanceof org.apache.commons.math.stat.descriptive.moment.GeometricMean) {
            summaryStatistics2.geoMeanImpl = new org.apache.commons.math.stat.descriptive.moment.GeometricMean((org.apache.commons.math.stat.descriptive.summary.SumOfLogs) summaryStatistics2.sumLogImpl);
        } else {
            summaryStatistics2.geoMeanImpl = summaryStatistics.geoMeanImpl.copy();
        }
        org.apache.commons.math.stat.descriptive.moment.SecondMoment.copy(summaryStatistics.secondMoment, summaryStatistics2.secondMoment);
        summaryStatistics2.n = summaryStatistics.n;
        if (summaryStatistics.geoMean == summaryStatistics.geoMeanImpl) {
            summaryStatistics2.geoMean = (org.apache.commons.math.stat.descriptive.moment.GeometricMean) summaryStatistics2.geoMeanImpl;
        } else {
            org.apache.commons.math.stat.descriptive.moment.GeometricMean.copy(summaryStatistics.geoMean, summaryStatistics2.geoMean);
        }
        if (summaryStatistics.max == summaryStatistics.maxImpl) {
            summaryStatistics2.max = (org.apache.commons.math.stat.descriptive.rank.Max) summaryStatistics2.maxImpl;
        } else {
            org.apache.commons.math.stat.descriptive.rank.Max.copy(summaryStatistics.max, summaryStatistics2.max);
        }
        if (summaryStatistics.mean == summaryStatistics.meanImpl) {
            summaryStatistics2.mean = (org.apache.commons.math.stat.descriptive.moment.Mean) summaryStatistics2.meanImpl;
        } else {
            org.apache.commons.math.stat.descriptive.moment.Mean.copy(summaryStatistics.mean, summaryStatistics2.mean);
        }
        if (summaryStatistics.min == summaryStatistics.minImpl) {
            summaryStatistics2.min = (org.apache.commons.math.stat.descriptive.rank.Min) summaryStatistics2.minImpl;
        } else {
            org.apache.commons.math.stat.descriptive.rank.Min.copy(summaryStatistics.min, summaryStatistics2.min);
        }
        if (summaryStatistics.sum == summaryStatistics.sumImpl) {
            summaryStatistics2.sum = (org.apache.commons.math.stat.descriptive.summary.Sum) summaryStatistics2.sumImpl;
        } else {
            org.apache.commons.math.stat.descriptive.summary.Sum.copy(summaryStatistics.sum, summaryStatistics2.sum);
        }
        if (summaryStatistics.variance == summaryStatistics.varianceImpl) {
            summaryStatistics2.variance = (org.apache.commons.math.stat.descriptive.moment.Variance) summaryStatistics2.varianceImpl;
        } else {
            org.apache.commons.math.stat.descriptive.moment.Variance.copy(summaryStatistics.variance, summaryStatistics2.variance);
        }
        if (summaryStatistics.sumLog == summaryStatistics.sumLogImpl) {
            summaryStatistics2.sumLog = (org.apache.commons.math.stat.descriptive.summary.SumOfLogs) summaryStatistics2.sumLogImpl;
        } else {
            org.apache.commons.math.stat.descriptive.summary.SumOfLogs.copy(summaryStatistics.sumLog, summaryStatistics2.sumLog);
        }
        if (summaryStatistics.sumsq == summaryStatistics.sumsqImpl) {
            summaryStatistics2.sumsq = (org.apache.commons.math.stat.descriptive.summary.SumOfSquares) summaryStatistics2.sumsqImpl;
        } else {
            org.apache.commons.math.stat.descriptive.summary.SumOfSquares.copy(summaryStatistics.sumsq, summaryStatistics2.sumsq);
        }
    }
}
