package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class NormalDistributionImpl extends org.apache.commons.math.distribution.AbstractContinuousDistribution implements org.apache.commons.math.distribution.NormalDistribution, java.io.Serializable {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final double SQRT2PI = org.apache.commons.math.util.FastMath.sqrt(6.283185307179586d);
    private static final long serialVersionUID = 8589540077390120676L;
    private double mean;
    private final double solverAbsoluteAccuracy;
    private double standardDeviation;

    public NormalDistributionImpl(double d, double d2) {
        this(d, d2, 1.0E-9d);
    }

    public NormalDistributionImpl(double d, double d2, double d3) {
        this.mean = SQRT2PI;
        this.standardDeviation = 1.0d;
        setMeanInternal(d);
        setStandardDeviationInternal(d2);
        this.solverAbsoluteAccuracy = d3;
    }

    public NormalDistributionImpl() {
        this(SQRT2PI, 1.0d);
    }

    @Override // org.apache.commons.math.distribution.NormalDistribution
    public double getMean() {
        return this.mean;
    }

    @Override // org.apache.commons.math.distribution.NormalDistribution
    @java.lang.Deprecated
    public void setMean(double d) {
        setMeanInternal(d);
    }

    private void setMeanInternal(double d) {
        this.mean = d;
    }

    @Override // org.apache.commons.math.distribution.NormalDistribution
    public double getStandardDeviation() {
        return this.standardDeviation;
    }

    @Override // org.apache.commons.math.distribution.NormalDistribution
    @java.lang.Deprecated
    public void setStandardDeviation(double d) {
        setStandardDeviationInternal(d);
    }

    private void setStandardDeviationInternal(double d) {
        if (d <= SQRT2PI) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_STANDARD_DEVIATION, java.lang.Double.valueOf(d));
        }
        this.standardDeviation = d;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.math.distribution.NormalDistribution, org.apache.commons.math.distribution.HasDensity
    @java.lang.Deprecated
    public double density(java.lang.Double d) {
        return density(d.doubleValue());
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    public double density(double d) {
        double d2 = d - this.mean;
        return org.apache.commons.math.util.FastMath.exp(((-d2) * d2) / ((this.standardDeviation * 2.0d) * this.standardDeviation)) / (this.standardDeviation * SQRT2PI);
    }

    @Override // org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d) throws org.apache.commons.math.MathException {
        double d2 = d - this.mean;
        if (org.apache.commons.math.util.FastMath.abs(d2) <= this.standardDeviation * 40.0d) {
            return (org.apache.commons.math.special.Erf.erf(d2 / (this.standardDeviation * org.apache.commons.math.util.FastMath.sqrt(2.0d))) + 1.0d) * 0.5d;
        }
        if (d2 < SQRT2PI) {
            return SQRT2PI;
        }
        return 1.0d;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution, org.apache.commons.math.distribution.ContinuousDistribution
    public double inverseCumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d == SQRT2PI) {
            return Double.NEGATIVE_INFINITY;
        }
        if (d == 1.0d) {
            return Double.POSITIVE_INFINITY;
        }
        return super.inverseCumulativeProbability(d);
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    public double sample() throws org.apache.commons.math.MathException {
        return this.randomData.nextGaussian(this.mean, this.standardDeviation);
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainLowerBound(double d) {
        if (d < 0.5d) {
            return -1.7976931348623157E308d;
        }
        return this.mean;
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
            return this.mean - this.standardDeviation;
        }
        if (d > 0.5d) {
            return this.mean + this.standardDeviation;
        }
        return this.mean;
    }

    public double getSupportLowerBound() {
        return Double.NEGATIVE_INFINITY;
    }

    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    public double getNumericalVariance() {
        double standardDeviation = getStandardDeviation();
        return standardDeviation * standardDeviation;
    }
}
