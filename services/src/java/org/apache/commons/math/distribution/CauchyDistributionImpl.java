package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class CauchyDistributionImpl extends org.apache.commons.math.distribution.AbstractContinuousDistribution implements org.apache.commons.math.distribution.CauchyDistribution, java.io.Serializable {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = 8589540077390120676L;
    private double median;
    private double scale;
    private final double solverAbsoluteAccuracy;

    public CauchyDistributionImpl() {
        this(0.0d, 1.0d);
    }

    public CauchyDistributionImpl(double d, double d2) {
        this(d, d2, 1.0E-9d);
    }

    public CauchyDistributionImpl(double d, double d2, double d3) {
        this.median = 0.0d;
        this.scale = 1.0d;
        setMedianInternal(d);
        setScaleInternal(d2);
        this.solverAbsoluteAccuracy = d3;
    }

    @Override // org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d) {
        return (org.apache.commons.math.util.FastMath.atan((d - this.median) / this.scale) / 3.141592653589793d) + 0.5d;
    }

    @Override // org.apache.commons.math.distribution.CauchyDistribution
    public double getMedian() {
        return this.median;
    }

    @Override // org.apache.commons.math.distribution.CauchyDistribution
    public double getScale() {
        return this.scale;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    public double density(double d) {
        double d2 = d - this.median;
        return (this.scale / ((d2 * d2) + (this.scale * this.scale))) * 0.3183098861837907d;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution, org.apache.commons.math.distribution.ContinuousDistribution
    public double inverseCumulativeProbability(double d) {
        if (d < 0.0d || d > 1.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_RANGE_SIMPLE, java.lang.Double.valueOf(d), java.lang.Double.valueOf(0.0d), java.lang.Double.valueOf(1.0d));
        }
        if (d == 0.0d) {
            return Double.NEGATIVE_INFINITY;
        }
        if (d == 1.0d) {
            return Double.POSITIVE_INFINITY;
        }
        return this.median + (this.scale * org.apache.commons.math.util.FastMath.tan((d - 0.5d) * 3.141592653589793d));
    }

    @Override // org.apache.commons.math.distribution.CauchyDistribution
    @java.lang.Deprecated
    public void setMedian(double d) {
        setMedianInternal(d);
    }

    private void setMedianInternal(double d) {
        this.median = d;
    }

    @Override // org.apache.commons.math.distribution.CauchyDistribution
    @java.lang.Deprecated
    public void setScale(double d) {
        setScaleInternal(d);
    }

    private void setScaleInternal(double d) {
        if (d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_SCALE, java.lang.Double.valueOf(d));
        }
        this.scale = d;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainLowerBound(double d) {
        if (d < 0.5d) {
            return -1.7976931348623157E308d;
        }
        return this.median;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getDomainUpperBound(double d) {
        if (d < 0.5d) {
            return this.median;
        }
        return Double.MAX_VALUE;
    }

    @Override // org.apache.commons.math.distribution.AbstractContinuousDistribution
    protected double getInitialDomain(double d) {
        if (d < 0.5d) {
            return this.median - this.scale;
        }
        if (d > 0.5d) {
            return this.median + this.scale;
        }
        return this.median;
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
        return Double.NaN;
    }

    public double getNumericalVariance() {
        return Double.NaN;
    }
}
