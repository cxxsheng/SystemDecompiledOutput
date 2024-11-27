package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class PoissonDistributionImpl extends org.apache.commons.math.distribution.AbstractIntegerDistribution implements org.apache.commons.math.distribution.PoissonDistribution, java.io.Serializable {
    public static final double DEFAULT_EPSILON = 1.0E-12d;
    public static final int DEFAULT_MAX_ITERATIONS = 10000000;
    private static final long serialVersionUID = -3349935121172596109L;
    private double epsilon;
    private int maxIterations;
    private double mean;
    private org.apache.commons.math.distribution.NormalDistribution normal;

    public PoissonDistributionImpl(double d) {
        this(d, new org.apache.commons.math.distribution.NormalDistributionImpl());
    }

    public PoissonDistributionImpl(double d, double d2, int i) {
        this.maxIterations = DEFAULT_MAX_ITERATIONS;
        this.epsilon = 1.0E-12d;
        setMean(d);
        this.epsilon = d2;
        this.maxIterations = i;
    }

    public PoissonDistributionImpl(double d, double d2) {
        this.maxIterations = DEFAULT_MAX_ITERATIONS;
        this.epsilon = 1.0E-12d;
        setMean(d);
        this.epsilon = d2;
    }

    public PoissonDistributionImpl(double d, int i) {
        this.maxIterations = DEFAULT_MAX_ITERATIONS;
        this.epsilon = 1.0E-12d;
        setMean(d);
        this.maxIterations = i;
    }

    @java.lang.Deprecated
    public PoissonDistributionImpl(double d, org.apache.commons.math.distribution.NormalDistribution normalDistribution) {
        this.maxIterations = DEFAULT_MAX_ITERATIONS;
        this.epsilon = 1.0E-12d;
        setNormalAndMeanInternal(normalDistribution, d);
    }

    @Override // org.apache.commons.math.distribution.PoissonDistribution
    public double getMean() {
        return this.mean;
    }

    @Override // org.apache.commons.math.distribution.PoissonDistribution
    @java.lang.Deprecated
    public void setMean(double d) {
        setNormalAndMeanInternal(this.normal, d);
    }

    private void setNormalAndMeanInternal(org.apache.commons.math.distribution.NormalDistribution normalDistribution, double d) {
        if (d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_POISSON_MEAN, java.lang.Double.valueOf(d));
        }
        this.mean = d;
        this.normal = normalDistribution;
        this.normal.setMean(d);
        this.normal.setStandardDeviation(org.apache.commons.math.util.FastMath.sqrt(d));
    }

    @Override // org.apache.commons.math.distribution.IntegerDistribution
    public double probability(int i) {
        if (i < 0 || i == Integer.MAX_VALUE) {
            return 0.0d;
        }
        if (i == 0) {
            return org.apache.commons.math.util.FastMath.exp(-this.mean);
        }
        double d = i;
        return org.apache.commons.math.util.FastMath.exp((-org.apache.commons.math.distribution.SaddlePointExpansion.getStirlingError(d)) - org.apache.commons.math.distribution.SaddlePointExpansion.getDeviancePart(d, this.mean)) / org.apache.commons.math.util.FastMath.sqrt(d * 6.283185307179586d);
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution, org.apache.commons.math.distribution.IntegerDistribution
    public double cumulativeProbability(int i) throws org.apache.commons.math.MathException {
        if (i < 0) {
            return 0.0d;
        }
        if (i == Integer.MAX_VALUE) {
            return 1.0d;
        }
        return org.apache.commons.math.special.Gamma.regularizedGammaQ(i + 1.0d, this.mean, this.epsilon, this.maxIterations);
    }

    @Override // org.apache.commons.math.distribution.PoissonDistribution
    public double normalApproximateProbability(int i) throws org.apache.commons.math.MathException {
        return this.normal.cumulativeProbability(i + 0.5d);
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution
    public int sample() throws org.apache.commons.math.MathException {
        return (int) org.apache.commons.math.util.FastMath.min(this.randomData.nextPoisson(this.mean), 2147483647L);
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution
    protected int getDomainLowerBound(double d) {
        return 0;
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution
    protected int getDomainUpperBound(double d) {
        return Integer.MAX_VALUE;
    }

    @java.lang.Deprecated
    public void setNormal(org.apache.commons.math.distribution.NormalDistribution normalDistribution) {
        setNormalAndMeanInternal(normalDistribution, this.mean);
    }

    public int getSupportLowerBound() {
        return 0;
    }

    public int getSupportUpperBound() {
        return Integer.MAX_VALUE;
    }

    public double getNumericalVariance() {
        return getMean();
    }
}
