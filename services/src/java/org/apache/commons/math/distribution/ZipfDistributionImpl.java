package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class ZipfDistributionImpl extends org.apache.commons.math.distribution.AbstractIntegerDistribution implements org.apache.commons.math.distribution.ZipfDistribution, java.io.Serializable {
    private static final long serialVersionUID = -140627372283420404L;
    private double exponent;
    private int numberOfElements;

    public ZipfDistributionImpl(int i, double d) throws java.lang.IllegalArgumentException {
        setNumberOfElementsInternal(i);
        setExponentInternal(d);
    }

    @Override // org.apache.commons.math.distribution.ZipfDistribution
    public int getNumberOfElements() {
        return this.numberOfElements;
    }

    @Override // org.apache.commons.math.distribution.ZipfDistribution
    @java.lang.Deprecated
    public void setNumberOfElements(int i) {
        setNumberOfElementsInternal(i);
    }

    private void setNumberOfElementsInternal(int i) throws java.lang.IllegalArgumentException {
        if (i <= 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(i), 0);
        }
        this.numberOfElements = i;
    }

    @Override // org.apache.commons.math.distribution.ZipfDistribution
    public double getExponent() {
        return this.exponent;
    }

    @Override // org.apache.commons.math.distribution.ZipfDistribution
    @java.lang.Deprecated
    public void setExponent(double d) {
        setExponentInternal(d);
    }

    private void setExponentInternal(double d) throws java.lang.IllegalArgumentException {
        if (d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_EXPONENT, java.lang.Double.valueOf(d));
        }
        this.exponent = d;
    }

    @Override // org.apache.commons.math.distribution.IntegerDistribution
    public double probability(int i) {
        if (i <= 0 || i > this.numberOfElements) {
            return 0.0d;
        }
        return (1.0d / org.apache.commons.math.util.FastMath.pow(i, this.exponent)) / generalizedHarmonic(this.numberOfElements, this.exponent);
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution, org.apache.commons.math.distribution.IntegerDistribution
    public double cumulativeProbability(int i) {
        if (i <= 0) {
            return 0.0d;
        }
        if (i >= this.numberOfElements) {
            return 1.0d;
        }
        return generalizedHarmonic(i, this.exponent) / generalizedHarmonic(this.numberOfElements, this.exponent);
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution
    protected int getDomainLowerBound(double d) {
        return 0;
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution
    protected int getDomainUpperBound(double d) {
        return this.numberOfElements;
    }

    private double generalizedHarmonic(int i, double d) {
        double d2 = 0.0d;
        while (i > 0) {
            d2 += 1.0d / org.apache.commons.math.util.FastMath.pow(i, d);
            i--;
        }
        return d2;
    }

    public int getSupportLowerBound() {
        return 1;
    }

    public int getSupportUpperBound() {
        return getNumberOfElements();
    }

    protected double getNumericalMean() {
        int numberOfElements = getNumberOfElements();
        double exponent = getExponent();
        return generalizedHarmonic(numberOfElements, exponent - 1.0d) / generalizedHarmonic(numberOfElements, exponent);
    }

    protected double getNumericalVariance() {
        int numberOfElements = getNumberOfElements();
        double exponent = getExponent();
        double generalizedHarmonic = generalizedHarmonic(numberOfElements, exponent - 2.0d);
        double generalizedHarmonic2 = generalizedHarmonic(numberOfElements, exponent - 1.0d);
        double generalizedHarmonic3 = generalizedHarmonic(numberOfElements, exponent);
        return (generalizedHarmonic / generalizedHarmonic3) - ((generalizedHarmonic2 * generalizedHarmonic2) / (generalizedHarmonic3 * generalizedHarmonic3));
    }
}
