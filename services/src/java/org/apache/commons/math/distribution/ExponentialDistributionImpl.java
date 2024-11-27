package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class ExponentialDistributionImpl extends org.apache.commons.math.distribution.AbstractContinuousDistribution implements org.apache.commons.math.distribution.ExponentialDistribution, java.io.Serializable {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = 2401296428283614780L;
    private double mean;
    private final double solverAbsoluteAccuracy;

    public ExponentialDistributionImpl(double d) {
        this(d, 1.0E-9d);
    }

    public ExponentialDistributionImpl(double d, double d2) {
        setMeanInternal(d);
        this.solverAbsoluteAccuracy = d2;
    }

    @Override // org.apache.commons.math.distribution.ExponentialDistribution
    @java.lang.Deprecated
    public void setMean(double d) {
        setMeanInternal(d);
    }

    private void setMeanInternal(double d) {
        if (d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_MEAN, java.lang.Double.valueOf(d));
        }
        this.mean = d;
    }

    @Override // org.apache.commons.math.distribution.ExponentialDistribution
    public double getMean() {
        return this.mean;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.math.distribution.ExponentialDistribution, org.apache.commons.math.distribution.HasDensity
    @java.lang.Deprecated
    public double density(java.lang.Double d) {
        return density(d.doubleValue());
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    public double density(double d) {
        if (d < 0.0d) {
            return 0.0d;
        }
        return org.apache.commons.math.util.FastMath.exp((-d) / this.mean) / this.mean;
    }

    @Override // org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d <= 0.0d) {
            return 0.0d;
        }
        return 1.0d - org.apache.commons.math.util.FastMath.exp((-d) / this.mean);
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution, org.apache.commons.math.distribution.ContinuousDistribution
    public double inverseCumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d < 0.0d || d > 1.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_RANGE_SIMPLE, java.lang.Double.valueOf(d), java.lang.Double.valueOf(0.0d), java.lang.Double.valueOf(1.0d));
        }
        if (d == 1.0d) {
            return Double.POSITIVE_INFINITY;
        }
        return org.apache.commons.math.util.FastMath.log(1.0d - d) * (-this.mean);
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    public double sample() throws org.apache.commons.math.MathException {
        return this.randomData.nextExponential(this.mean);
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainLowerBound(double d) {
        return 0.0d;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainUpperBound(double d) {
        if (d < 0.5d) {
            return this.mean;
        }
        return Double.MAX_VALUE;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getInitialDomain(double d) {
        if (d < 0.5d) {
            return this.mean * 0.5d;
        }
        return this.mean;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getSupportLowerBound() {
        return 0.0d;
    }

    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    public double getNumericalMean() {
        return getMean();
    }

    public double getNumericalVariance() {
        double mean = getMean();
        return mean * mean;
    }
}
