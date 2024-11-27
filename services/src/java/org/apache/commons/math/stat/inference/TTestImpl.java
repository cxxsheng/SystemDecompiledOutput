package org.apache.commons.math.stat.inference;

/* loaded from: classes3.dex */
public class TTestImpl implements org.apache.commons.math.stat.inference.TTest {

    @java.lang.Deprecated
    private org.apache.commons.math.distribution.TDistribution distribution;

    public TTestImpl() {
        this(new org.apache.commons.math.distribution.TDistributionImpl(1.0d));
    }

    @java.lang.Deprecated
    public TTestImpl(org.apache.commons.math.distribution.TDistribution tDistribution) {
        setDistribution(tDistribution);
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double pairedT(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSampleData(dArr);
        checkSampleData(dArr2);
        double meanDifference = org.apache.commons.math.stat.StatUtils.meanDifference(dArr, dArr2);
        return t(meanDifference, 0.0d, org.apache.commons.math.stat.StatUtils.varianceDifference(dArr, dArr2, meanDifference), dArr.length);
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double pairedTTest(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        double meanDifference = org.apache.commons.math.stat.StatUtils.meanDifference(dArr, dArr2);
        return tTest(meanDifference, 0.0d, org.apache.commons.math.stat.StatUtils.varianceDifference(dArr, dArr2, meanDifference), dArr.length);
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public boolean pairedTTest(double[] dArr, double[] dArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSignificanceLevel(d);
        return pairedTTest(dArr, dArr2) < d;
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double t(double d, double[] dArr) throws java.lang.IllegalArgumentException {
        checkSampleData(dArr);
        return t(org.apache.commons.math.stat.StatUtils.mean(dArr), d, org.apache.commons.math.stat.StatUtils.variance(dArr), dArr.length);
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double t(double d, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary) throws java.lang.IllegalArgumentException {
        checkSampleData(statisticalSummary);
        return t(statisticalSummary.getMean(), d, statisticalSummary.getVariance(), statisticalSummary.getN());
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double homoscedasticT(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        checkSampleData(dArr);
        checkSampleData(dArr2);
        return homoscedasticT(org.apache.commons.math.stat.StatUtils.mean(dArr), org.apache.commons.math.stat.StatUtils.mean(dArr2), org.apache.commons.math.stat.StatUtils.variance(dArr), org.apache.commons.math.stat.StatUtils.variance(dArr2), dArr.length, dArr2.length);
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double t(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        checkSampleData(dArr);
        checkSampleData(dArr2);
        return t(org.apache.commons.math.stat.StatUtils.mean(dArr), org.apache.commons.math.stat.StatUtils.mean(dArr2), org.apache.commons.math.stat.StatUtils.variance(dArr), org.apache.commons.math.stat.StatUtils.variance(dArr2), dArr.length, dArr2.length);
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double t(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException {
        checkSampleData(statisticalSummary);
        checkSampleData(statisticalSummary2);
        return t(statisticalSummary.getMean(), statisticalSummary2.getMean(), statisticalSummary.getVariance(), statisticalSummary2.getVariance(), statisticalSummary.getN(), statisticalSummary2.getN());
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double homoscedasticT(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException {
        checkSampleData(statisticalSummary);
        checkSampleData(statisticalSummary2);
        return homoscedasticT(statisticalSummary.getMean(), statisticalSummary2.getMean(), statisticalSummary.getVariance(), statisticalSummary2.getVariance(), statisticalSummary.getN(), statisticalSummary2.getN());
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double tTest(double d, double[] dArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSampleData(dArr);
        return tTest(org.apache.commons.math.stat.StatUtils.mean(dArr), d, org.apache.commons.math.stat.StatUtils.variance(dArr), dArr.length);
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public boolean tTest(double d, double[] dArr, double d2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSignificanceLevel(d2);
        return tTest(d, dArr) < d2;
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double tTest(double d, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSampleData(statisticalSummary);
        return tTest(statisticalSummary.getMean(), d, statisticalSummary.getVariance(), statisticalSummary.getN());
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public boolean tTest(double d, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, double d2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSignificanceLevel(d2);
        return tTest(d, statisticalSummary) < d2;
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double tTest(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSampleData(dArr);
        checkSampleData(dArr2);
        return tTest(org.apache.commons.math.stat.StatUtils.mean(dArr), org.apache.commons.math.stat.StatUtils.mean(dArr2), org.apache.commons.math.stat.StatUtils.variance(dArr), org.apache.commons.math.stat.StatUtils.variance(dArr2), dArr.length, dArr2.length);
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double homoscedasticTTest(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSampleData(dArr);
        checkSampleData(dArr2);
        return homoscedasticTTest(org.apache.commons.math.stat.StatUtils.mean(dArr), org.apache.commons.math.stat.StatUtils.mean(dArr2), org.apache.commons.math.stat.StatUtils.variance(dArr), org.apache.commons.math.stat.StatUtils.variance(dArr2), dArr.length, dArr2.length);
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public boolean tTest(double[] dArr, double[] dArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSignificanceLevel(d);
        return tTest(dArr, dArr2) < d;
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public boolean homoscedasticTTest(double[] dArr, double[] dArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSignificanceLevel(d);
        return homoscedasticTTest(dArr, dArr2) < d;
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double tTest(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSampleData(statisticalSummary);
        checkSampleData(statisticalSummary2);
        return tTest(statisticalSummary.getMean(), statisticalSummary2.getMean(), statisticalSummary.getVariance(), statisticalSummary2.getVariance(), statisticalSummary.getN(), statisticalSummary2.getN());
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public double homoscedasticTTest(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSampleData(statisticalSummary);
        checkSampleData(statisticalSummary2);
        return homoscedasticTTest(statisticalSummary.getMean(), statisticalSummary2.getMean(), statisticalSummary.getVariance(), statisticalSummary2.getVariance(), statisticalSummary.getN(), statisticalSummary2.getN());
    }

    @Override // org.apache.commons.math.stat.inference.TTest
    public boolean tTest(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkSignificanceLevel(d);
        return tTest(statisticalSummary, statisticalSummary2) < d;
    }

    protected double df(double d, double d2, double d3, double d4) {
        double d5 = (d / d3) + (d2 / d4);
        return (d5 * d5) / (((d * d) / ((d3 * d3) * (d3 - 1.0d))) + ((d2 * d2) / ((d4 * d4) * (d4 - 1.0d))));
    }

    protected double t(double d, double d2, double d3, double d4) {
        return (d - d2) / org.apache.commons.math.util.FastMath.sqrt(d3 / d4);
    }

    protected double t(double d, double d2, double d3, double d4, double d5, double d6) {
        return (d - d2) / org.apache.commons.math.util.FastMath.sqrt((d3 / d5) + (d4 / d6));
    }

    protected double homoscedasticT(double d, double d2, double d3, double d4, double d5, double d6) {
        return (d - d2) / org.apache.commons.math.util.FastMath.sqrt(((((d5 - 1.0d) * d3) + ((d6 - 1.0d) * d4)) / ((d5 + d6) - 2.0d)) * ((1.0d / d5) + (1.0d / d6)));
    }

    protected double tTest(double d, double d2, double d3, double d4) throws org.apache.commons.math.MathException {
        double abs = org.apache.commons.math.util.FastMath.abs(t(d, d2, d3, d4));
        this.distribution.setDegreesOfFreedom(d4 - 1.0d);
        return this.distribution.cumulativeProbability(-abs) * 2.0d;
    }

    protected double tTest(double d, double d2, double d3, double d4, double d5, double d6) throws org.apache.commons.math.MathException {
        double abs = org.apache.commons.math.util.FastMath.abs(t(d, d2, d3, d4, d5, d6));
        this.distribution.setDegreesOfFreedom(df(d3, d4, d5, d6));
        return this.distribution.cumulativeProbability(-abs) * 2.0d;
    }

    protected double homoscedasticTTest(double d, double d2, double d3, double d4, double d5, double d6) throws org.apache.commons.math.MathException {
        double abs = org.apache.commons.math.util.FastMath.abs(homoscedasticT(d, d2, d3, d4, d5, d6));
        this.distribution.setDegreesOfFreedom((d5 + d6) - 2.0d);
        return this.distribution.cumulativeProbability(-abs) * 2.0d;
    }

    @java.lang.Deprecated
    public void setDistribution(org.apache.commons.math.distribution.TDistribution tDistribution) {
        this.distribution = tDistribution;
    }

    private void checkSignificanceLevel(double d) throws java.lang.IllegalArgumentException {
        if (d <= 0.0d || d > 0.5d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, java.lang.Double.valueOf(d), java.lang.Double.valueOf(0.0d), java.lang.Double.valueOf(0.5d));
        }
    }

    private void checkSampleData(double[] dArr) throws java.lang.IllegalArgumentException {
        if (dArr == null || dArr.length < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DATA_FOR_T_STATISTIC, java.lang.Integer.valueOf(dArr == null ? 0 : dArr.length));
        }
    }

    private void checkSampleData(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary) throws java.lang.IllegalArgumentException {
        if (statisticalSummary == null || statisticalSummary.getN() < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DATA_FOR_T_STATISTIC, java.lang.Long.valueOf(statisticalSummary == null ? 0L : statisticalSummary.getN()));
        }
    }
}
