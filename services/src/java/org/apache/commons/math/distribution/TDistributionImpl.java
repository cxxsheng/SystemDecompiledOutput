package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class TDistributionImpl extends org.apache.commons.math.distribution.AbstractContinuousDistribution implements org.apache.commons.math.distribution.TDistribution, java.io.Serializable {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = -5852615386664158222L;
    private double degreesOfFreedom;
    private final double solverAbsoluteAccuracy;

    public TDistributionImpl(double d, double d2) {
        setDegreesOfFreedomInternal(d);
        this.solverAbsoluteAccuracy = d2;
    }

    public TDistributionImpl(double d) {
        this(d, 1.0E-9d);
    }

    @Override // org.apache.commons.math.distribution.TDistribution
    @java.lang.Deprecated
    public void setDegreesOfFreedom(double d) {
        setDegreesOfFreedomInternal(d);
    }

    private void setDegreesOfFreedomInternal(double d) {
        if (d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_DEGREES_OF_FREEDOM, java.lang.Double.valueOf(d));
        }
        this.degreesOfFreedom = d;
    }

    @Override // org.apache.commons.math.distribution.TDistribution
    public double getDegreesOfFreedom() {
        return this.degreesOfFreedom;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    public double density(double d) {
        double d2 = this.degreesOfFreedom;
        double d3 = (d2 + 1.0d) / 2.0d;
        return org.apache.commons.math.util.FastMath.exp(((org.apache.commons.math.special.Gamma.logGamma(d3) - ((org.apache.commons.math.util.FastMath.log(3.141592653589793d) + org.apache.commons.math.util.FastMath.log(d2)) * 0.5d)) - org.apache.commons.math.special.Gamma.logGamma(d2 / 2.0d)) - (d3 * org.apache.commons.math.util.FastMath.log(((d * d) / d2) + 1.0d)));
    }

    @Override // org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d == 0.0d) {
            return 0.5d;
        }
        double regularizedBeta = org.apache.commons.math.special.Beta.regularizedBeta(this.degreesOfFreedom / (this.degreesOfFreedom + (d * d)), this.degreesOfFreedom * 0.5d, 0.5d);
        if (d < 0.0d) {
            return 0.5d * regularizedBeta;
        }
        return 1.0d - (regularizedBeta * 0.5d);
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution, org.apache.commons.math.distribution.ContinuousDistribution
    public double inverseCumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d == 0.0d) {
            return Double.NEGATIVE_INFINITY;
        }
        if (d == 1.0d) {
            return Double.POSITIVE_INFINITY;
        }
        return super.inverseCumulativeProbability(d);
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainLowerBound(double d) {
        return -1.7976931348623157E308d;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainUpperBound(double d) {
        return Double.MAX_VALUE;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getInitialDomain(double d) {
        return 0.0d;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getSupportLowerBound() {
        return Double.NEGATIVE_INFINITY;
    }

    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    public double getNumericalMean() {
        if (getDegreesOfFreedom() > 1.0d) {
            return 0.0d;
        }
        return Double.NaN;
    }

    public double getNumericalVariance() {
        double degreesOfFreedom = getDegreesOfFreedom();
        if (degreesOfFreedom > 2.0d) {
            return degreesOfFreedom / (degreesOfFreedom - 2.0d);
        }
        if (degreesOfFreedom > 1.0d && degreesOfFreedom <= 2.0d) {
            return Double.POSITIVE_INFINITY;
        }
        return Double.NaN;
    }
}
