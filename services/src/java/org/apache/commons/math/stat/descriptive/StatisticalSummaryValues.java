package org.apache.commons.math.stat.descriptive;

/* loaded from: classes3.dex */
public class StatisticalSummaryValues implements java.io.Serializable, org.apache.commons.math.stat.descriptive.StatisticalSummary {
    private static final long serialVersionUID = -5108854841843722536L;
    private final double max;
    private final double mean;
    private final double min;
    private final long n;
    private final double sum;
    private final double variance;

    public StatisticalSummaryValues(double d, double d2, long j, double d3, double d4, double d5) {
        this.mean = d;
        this.variance = d2;
        this.n = j;
        this.max = d3;
        this.min = d4;
        this.sum = d5;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMax() {
        return this.max;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMean() {
        return this.mean;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMin() {
        return this.min;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public long getN() {
        return this.n;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getSum() {
        return this.sum;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getStandardDeviation() {
        return org.apache.commons.math.util.FastMath.sqrt(this.variance);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getVariance() {
        return this.variance;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.stat.descriptive.StatisticalSummaryValues)) {
            return false;
        }
        org.apache.commons.math.stat.descriptive.StatisticalSummaryValues statisticalSummaryValues = (org.apache.commons.math.stat.descriptive.StatisticalSummaryValues) obj;
        return org.apache.commons.math.util.MathUtils.equalsIncludingNaN(statisticalSummaryValues.getMax(), getMax()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(statisticalSummaryValues.getMean(), getMean()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(statisticalSummaryValues.getMin(), getMin()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN((float) statisticalSummaryValues.getN(), (float) getN()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(statisticalSummaryValues.getSum(), getSum()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN(statisticalSummaryValues.getVariance(), getVariance());
    }

    public int hashCode() {
        return ((((((((((org.apache.commons.math.util.MathUtils.hash(getMax()) + 31) * 31) + org.apache.commons.math.util.MathUtils.hash(getMean())) * 31) + org.apache.commons.math.util.MathUtils.hash(getMin())) * 31) + org.apache.commons.math.util.MathUtils.hash(getN())) * 31) + org.apache.commons.math.util.MathUtils.hash(getSum())) * 31) + org.apache.commons.math.util.MathUtils.hash(getVariance());
    }

    public java.lang.String toString() {
        return "StatisticalSummaryValues:\nn: " + getN() + "\nmin: " + getMin() + "\nmax: " + getMax() + "\nmean: " + getMean() + "\nstd dev: " + getStandardDeviation() + "\nvariance: " + getVariance() + "\nsum: " + getSum() + "\n";
    }
}
