package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class BetaDistributionImpl extends org.apache.commons.math.distribution.AbstractContinuousDistribution implements org.apache.commons.math.distribution.BetaDistribution {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = -1221965979403477668L;
    private double alpha;
    private double beta;
    private final double solverAbsoluteAccuracy;
    private double z;

    public BetaDistributionImpl(double d, double d2, double d3) {
        this.alpha = d;
        this.beta = d2;
        this.z = Double.NaN;
        this.solverAbsoluteAccuracy = d3;
    }

    public BetaDistributionImpl(double d, double d2) {
        this(d, d2, 1.0E-9d);
    }

    @Override // org.apache.commons.math.distribution.BetaDistribution
    @java.lang.Deprecated
    public void setAlpha(double d) {
        this.alpha = d;
        this.z = Double.NaN;
    }

    @Override // org.apache.commons.math.distribution.BetaDistribution
    public double getAlpha() {
        return this.alpha;
    }

    @Override // org.apache.commons.math.distribution.BetaDistribution
    @java.lang.Deprecated
    public void setBeta(double d) {
        this.beta = d;
        this.z = Double.NaN;
    }

    @Override // org.apache.commons.math.distribution.BetaDistribution
    public double getBeta() {
        return this.beta;
    }

    private void recomputeZ() {
        if (java.lang.Double.isNaN(this.z)) {
            this.z = (org.apache.commons.math.special.Gamma.logGamma(this.alpha) + org.apache.commons.math.special.Gamma.logGamma(this.beta)) - org.apache.commons.math.special.Gamma.logGamma(this.alpha + this.beta);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.math.distribution.BetaDistribution, org.apache.commons.math.distribution.HasDensity
    @java.lang.Deprecated
    public double density(java.lang.Double d) {
        return density(d.doubleValue());
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    public double density(double d) {
        recomputeZ();
        if (d < 0.0d || d > 1.0d) {
            return 0.0d;
        }
        if (d == 0.0d) {
            if (this.alpha >= 1.0d) {
                return 0.0d;
            }
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_COMPUTE_BETA_DENSITY_AT_0_FOR_SOME_ALPHA, java.lang.Double.valueOf(this.alpha));
        }
        if (d == 1.0d) {
            if (this.beta >= 1.0d) {
                return 0.0d;
            }
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_COMPUTE_BETA_DENSITY_AT_1_FOR_SOME_BETA, java.lang.Double.valueOf(this.beta));
        }
        return org.apache.commons.math.util.FastMath.exp((((this.alpha - 1.0d) * org.apache.commons.math.util.FastMath.log(d)) + ((this.beta - 1.0d) * org.apache.commons.math.util.FastMath.log1p(-d))) - this.z);
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution, org.apache.commons.math.distribution.ContinuousDistribution
    public double inverseCumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d == 0.0d) {
            return 0.0d;
        }
        if (d == 1.0d) {
            return 1.0d;
        }
        return super.inverseCumulativeProbability(d);
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getInitialDomain(double d) {
        return d;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainLowerBound(double d) {
        return 0.0d;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainUpperBound(double d) {
        return 1.0d;
    }

    @Override // org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d <= 0.0d) {
            return 0.0d;
        }
        if (d >= 1.0d) {
            return 1.0d;
        }
        return org.apache.commons.math.special.Beta.regularizedBeta(d, this.alpha, this.beta);
    }

    @Override // org.apache.commons.math.distribution.AbstractDistribution, org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d, double d2) throws org.apache.commons.math.MathException {
        return cumulativeProbability(d2) - cumulativeProbability(d);
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getSupportLowerBound() {
        return 0.0d;
    }

    public double getSupportUpperBound() {
        return 1.0d;
    }

    public double getNumericalMean() {
        double alpha = getAlpha();
        return alpha / (getBeta() + alpha);
    }

    public double getNumericalVariance() {
        double alpha = getAlpha();
        double beta = getBeta();
        double d = alpha + beta;
        return (alpha * beta) / ((d * d) * (d + 1.0d));
    }
}
