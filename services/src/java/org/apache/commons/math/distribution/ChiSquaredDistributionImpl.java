package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class ChiSquaredDistributionImpl extends org.apache.commons.math.distribution.AbstractContinuousDistribution implements org.apache.commons.math.distribution.ChiSquaredDistribution, java.io.Serializable {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = -8352658048349159782L;
    private org.apache.commons.math.distribution.GammaDistribution gamma;
    private final double solverAbsoluteAccuracy;

    public ChiSquaredDistributionImpl(double d) {
        this(d, new org.apache.commons.math.distribution.GammaDistributionImpl(d / 2.0d, 2.0d));
    }

    @java.lang.Deprecated
    public ChiSquaredDistributionImpl(double d, org.apache.commons.math.distribution.GammaDistribution gammaDistribution) {
        setGammaInternal(gammaDistribution);
        setDegreesOfFreedomInternal(d);
        this.solverAbsoluteAccuracy = 1.0E-9d;
    }

    public ChiSquaredDistributionImpl(double d, double d2) {
        this.gamma = new org.apache.commons.math.distribution.GammaDistributionImpl(d / 2.0d, 2.0d);
        setDegreesOfFreedomInternal(d);
        this.solverAbsoluteAccuracy = d2;
    }

    @Override // org.apache.commons.math.distribution.ChiSquaredDistribution
    @java.lang.Deprecated
    public void setDegreesOfFreedom(double d) {
        setDegreesOfFreedomInternal(d);
    }

    private void setDegreesOfFreedomInternal(double d) {
        this.gamma.setAlpha(d / 2.0d);
    }

    @Override // org.apache.commons.math.distribution.ChiSquaredDistribution
    public double getDegreesOfFreedom() {
        return this.gamma.getAlpha() * 2.0d;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.math.distribution.ChiSquaredDistribution, org.apache.commons.math.distribution.HasDensity
    @java.lang.Deprecated
    public double density(java.lang.Double d) {
        return density(d.doubleValue());
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    public double density(double d) {
        return this.gamma.density(java.lang.Double.valueOf(d));
    }

    @Override // org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d) throws org.apache.commons.math.MathException {
        return this.gamma.cumulativeProbability(d);
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

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainLowerBound(double d) {
        return this.gamma.getBeta() * Double.MIN_VALUE;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainUpperBound(double d) {
        if (d < 0.5d) {
            return getDegreesOfFreedom();
        }
        return Double.MAX_VALUE;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getInitialDomain(double d) {
        if (d < 0.5d) {
            return getDegreesOfFreedom() * 0.5d;
        }
        return getDegreesOfFreedom();
    }

    @java.lang.Deprecated
    public void setGamma(org.apache.commons.math.distribution.GammaDistribution gammaDistribution) {
        setGammaInternal(gammaDistribution);
    }

    private void setGammaInternal(org.apache.commons.math.distribution.GammaDistribution gammaDistribution) {
        this.gamma = gammaDistribution;
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
        return getDegreesOfFreedom();
    }

    public double getNumericalVariance() {
        return getDegreesOfFreedom() * 2.0d;
    }
}
