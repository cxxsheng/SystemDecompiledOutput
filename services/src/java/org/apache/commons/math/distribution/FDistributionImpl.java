package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class FDistributionImpl extends org.apache.commons.math.distribution.AbstractContinuousDistribution implements org.apache.commons.math.distribution.FDistribution, java.io.Serializable {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = -8516354193418641566L;
    private double denominatorDegreesOfFreedom;
    private double numeratorDegreesOfFreedom;
    private final double solverAbsoluteAccuracy;

    public FDistributionImpl(double d, double d2) {
        this(d, d2, 1.0E-9d);
    }

    public FDistributionImpl(double d, double d2, double d3) {
        setNumeratorDegreesOfFreedomInternal(d);
        setDenominatorDegreesOfFreedomInternal(d2);
        this.solverAbsoluteAccuracy = d3;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    public double density(double d) {
        double d2 = this.numeratorDegreesOfFreedom / 2.0d;
        double d3 = this.denominatorDegreesOfFreedom / 2.0d;
        double log = org.apache.commons.math.util.FastMath.log(d);
        double log2 = org.apache.commons.math.util.FastMath.log(this.numeratorDegreesOfFreedom);
        double log3 = org.apache.commons.math.util.FastMath.log(this.denominatorDegreesOfFreedom);
        double log4 = org.apache.commons.math.util.FastMath.log((this.numeratorDegreesOfFreedom * d) + this.denominatorDegreesOfFreedom);
        return org.apache.commons.math.util.FastMath.exp(((((((log2 * d2) + (d2 * log)) - log) + (log3 * d3)) - (d2 * log4)) - (log4 * d3)) - org.apache.commons.math.special.Beta.logBeta(d2, d3));
    }

    @Override // org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d <= 0.0d) {
            return 0.0d;
        }
        double d2 = this.numeratorDegreesOfFreedom;
        double d3 = this.denominatorDegreesOfFreedom;
        double d4 = d * d2;
        return org.apache.commons.math.special.Beta.regularizedBeta(d4 / (d3 + d4), d2 * 0.5d, d3 * 0.5d);
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
        return 0.0d;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainUpperBound(double d) {
        return Double.MAX_VALUE;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getInitialDomain(double d) {
        double d2 = this.denominatorDegreesOfFreedom;
        if (d2 <= 2.0d) {
            return 1.0d;
        }
        return d2 / (d2 - 2.0d);
    }

    @Override // org.apache.commons.math.distribution.FDistribution
    @java.lang.Deprecated
    public void setNumeratorDegreesOfFreedom(double d) {
        setNumeratorDegreesOfFreedomInternal(d);
    }

    private void setNumeratorDegreesOfFreedomInternal(double d) {
        if (d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_DEGREES_OF_FREEDOM, java.lang.Double.valueOf(d));
        }
        this.numeratorDegreesOfFreedom = d;
    }

    @Override // org.apache.commons.math.distribution.FDistribution
    public double getNumeratorDegreesOfFreedom() {
        return this.numeratorDegreesOfFreedom;
    }

    @Override // org.apache.commons.math.distribution.FDistribution
    @java.lang.Deprecated
    public void setDenominatorDegreesOfFreedom(double d) {
        setDenominatorDegreesOfFreedomInternal(d);
    }

    private void setDenominatorDegreesOfFreedomInternal(double d) {
        if (d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_DEGREES_OF_FREEDOM, java.lang.Double.valueOf(d));
        }
        this.denominatorDegreesOfFreedom = d;
    }

    @Override // org.apache.commons.math.distribution.FDistribution
    public double getDenominatorDegreesOfFreedom() {
        return this.denominatorDegreesOfFreedom;
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
        double denominatorDegreesOfFreedom = getDenominatorDegreesOfFreedom();
        if (denominatorDegreesOfFreedom > 2.0d) {
            return denominatorDegreesOfFreedom / (denominatorDegreesOfFreedom - 2.0d);
        }
        return Double.NaN;
    }

    public double getNumericalVariance() {
        double denominatorDegreesOfFreedom = getDenominatorDegreesOfFreedom();
        if (denominatorDegreesOfFreedom > 4.0d) {
            double numeratorDegreesOfFreedom = getNumeratorDegreesOfFreedom();
            double d = denominatorDegreesOfFreedom - 2.0d;
            return (((denominatorDegreesOfFreedom * denominatorDegreesOfFreedom) * 2.0d) * ((numeratorDegreesOfFreedom + denominatorDegreesOfFreedom) - 2.0d)) / ((numeratorDegreesOfFreedom * (d * d)) * (denominatorDegreesOfFreedom - 4.0d));
        }
        return Double.NaN;
    }
}
