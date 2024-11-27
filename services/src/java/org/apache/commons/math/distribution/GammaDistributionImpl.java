package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class GammaDistributionImpl extends org.apache.commons.math.distribution.AbstractContinuousDistribution implements org.apache.commons.math.distribution.GammaDistribution, java.io.Serializable {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = -3239549463135430361L;
    private double alpha;
    private double beta;
    private final double solverAbsoluteAccuracy;

    public GammaDistributionImpl(double d, double d2) {
        this(d, d2, 1.0E-9d);
    }

    public GammaDistributionImpl(double d, double d2, double d3) {
        setAlphaInternal(d);
        setBetaInternal(d2);
        this.solverAbsoluteAccuracy = d3;
    }

    @Override // org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d <= 0.0d) {
            return 0.0d;
        }
        return org.apache.commons.math.special.Gamma.regularizedGammaP(this.alpha, d / this.beta);
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution, org.apache.commons.math.distribution.ContinuousDistribution
    public double inverseCumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d == 0.0d) {
            return 0.0d;
        }
        if (d == 1.0d) {
            return Double.POSITIVE_INFINITY;
        }
        return super.inverseCumulativeProbability(d);
    }

    @Override // org.apache.commons.math.distribution.GammaDistribution
    @java.lang.Deprecated
    public void setAlpha(double d) {
        setAlphaInternal(d);
    }

    private void setAlphaInternal(double d) {
        if (d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_ALPHA, java.lang.Double.valueOf(d));
        }
        this.alpha = d;
    }

    @Override // org.apache.commons.math.distribution.GammaDistribution
    public double getAlpha() {
        return this.alpha;
    }

    @Override // org.apache.commons.math.distribution.GammaDistribution
    @java.lang.Deprecated
    public void setBeta(double d) {
        setBetaInternal(d);
    }

    private void setBetaInternal(double d) {
        if (d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_BETA, java.lang.Double.valueOf(d));
        }
        this.beta = d;
    }

    @Override // org.apache.commons.math.distribution.GammaDistribution
    public double getBeta() {
        return this.beta;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    public double density(double d) {
        if (d < 0.0d) {
            return 0.0d;
        }
        return ((org.apache.commons.math.util.FastMath.pow(d / this.beta, this.alpha - 1.0d) / this.beta) * org.apache.commons.math.util.FastMath.exp((-d) / this.beta)) / org.apache.commons.math.util.FastMath.exp(org.apache.commons.math.special.Gamma.logGamma(this.alpha));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.math.distribution.GammaDistribution, org.apache.commons.math.distribution.HasDensity
    @java.lang.Deprecated
    public double density(java.lang.Double d) {
        return density(d.doubleValue());
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainLowerBound(double d) {
        return Double.MIN_VALUE;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainUpperBound(double d) {
        if (d < 0.5d) {
            return this.alpha * this.beta;
        }
        return Double.MAX_VALUE;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getInitialDomain(double d) {
        if (d < 0.5d) {
            return this.alpha * this.beta * 0.5d;
        }
        return this.alpha * this.beta;
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
        return getAlpha() * getBeta();
    }

    public double getNumericalVariance() {
        double beta = getBeta();
        return getAlpha() * beta * beta;
    }
}
