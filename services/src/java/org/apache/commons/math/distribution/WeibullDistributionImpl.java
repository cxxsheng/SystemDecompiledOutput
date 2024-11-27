package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class WeibullDistributionImpl extends org.apache.commons.math.distribution.AbstractContinuousDistribution implements org.apache.commons.math.distribution.WeibullDistribution, java.io.Serializable {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = 8589540077390120676L;
    private double numericalMean;
    private boolean numericalMeanIsCalculated;
    private double numericalVariance;
    private boolean numericalVarianceIsCalculated;
    private double scale;
    private double shape;
    private final double solverAbsoluteAccuracy;

    public WeibullDistributionImpl(double d, double d2) {
        this(d, d2, 1.0E-9d);
    }

    public WeibullDistributionImpl(double d, double d2, double d3) {
        this.numericalMean = Double.NaN;
        this.numericalMeanIsCalculated = false;
        this.numericalVariance = Double.NaN;
        this.numericalVarianceIsCalculated = false;
        setShapeInternal(d);
        setScaleInternal(d2);
        this.solverAbsoluteAccuracy = d3;
    }

    @Override // org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d) {
        if (d <= 0.0d) {
            return 0.0d;
        }
        return 1.0d - org.apache.commons.math.util.FastMath.exp(-org.apache.commons.math.util.FastMath.pow(d / this.scale, this.shape));
    }

    @Override // org.apache.commons.math.distribution.WeibullDistribution
    public double getShape() {
        return this.shape;
    }

    @Override // org.apache.commons.math.distribution.WeibullDistribution
    public double getScale() {
        return this.scale;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    public double density(double d) {
        if (d < 0.0d) {
            return 0.0d;
        }
        double d2 = d / this.scale;
        double pow = org.apache.commons.math.util.FastMath.pow(d2, this.shape - 1.0d);
        return (this.shape / this.scale) * pow * org.apache.commons.math.util.FastMath.exp(-(d2 * pow));
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution, org.apache.commons.math.distribution.ContinuousDistribution
    public double inverseCumulativeProbability(double d) {
        if (d < 0.0d || d > 1.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_RANGE_SIMPLE, java.lang.Double.valueOf(d), java.lang.Double.valueOf(0.0d), java.lang.Double.valueOf(1.0d));
        }
        if (d == 0.0d) {
            return 0.0d;
        }
        if (d == 1.0d) {
            return Double.POSITIVE_INFINITY;
        }
        return this.scale * org.apache.commons.math.util.FastMath.pow(-org.apache.commons.math.util.FastMath.log(1.0d - d), 1.0d / this.shape);
    }

    @Override // org.apache.commons.math.distribution.WeibullDistribution
    @java.lang.Deprecated
    public void setShape(double d) {
        setShapeInternal(d);
        invalidateParameterDependentMoments();
    }

    private void setShapeInternal(double d) {
        if (d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_SHAPE, java.lang.Double.valueOf(d));
        }
        this.shape = d;
    }

    @Override // org.apache.commons.math.distribution.WeibullDistribution
    @java.lang.Deprecated
    public void setScale(double d) {
        setScaleInternal(d);
        invalidateParameterDependentMoments();
    }

    private void setScaleInternal(double d) {
        if (d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_SCALE, java.lang.Double.valueOf(d));
        }
        this.scale = d;
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
        return org.apache.commons.math.util.FastMath.pow(this.scale * org.apache.commons.math.util.FastMath.log(2.0d), 1.0d / this.shape);
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

    protected double calculateNumericalMean() {
        return getScale() * org.apache.commons.math.util.FastMath.exp(org.apache.commons.math.special.Gamma.logGamma((1.0d / getShape()) + 1.0d));
    }

    private double calculateNumericalVariance() {
        double shape = getShape();
        double scale = getScale();
        double numericalMean = getNumericalMean();
        return ((scale * scale) * org.apache.commons.math.util.FastMath.exp(org.apache.commons.math.special.Gamma.logGamma((2.0d / shape) + 1.0d))) - (numericalMean * numericalMean);
    }

    public double getNumericalMean() {
        if (!this.numericalMeanIsCalculated) {
            this.numericalMean = calculateNumericalMean();
            this.numericalMeanIsCalculated = true;
        }
        return this.numericalMean;
    }

    public double getNumericalVariance() {
        if (!this.numericalVarianceIsCalculated) {
            this.numericalVariance = calculateNumericalVariance();
            this.numericalVarianceIsCalculated = true;
        }
        return this.numericalVariance;
    }

    private void invalidateParameterDependentMoments() {
        this.numericalMeanIsCalculated = false;
        this.numericalVarianceIsCalculated = false;
    }
}
