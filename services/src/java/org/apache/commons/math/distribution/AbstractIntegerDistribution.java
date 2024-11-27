package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public abstract class AbstractIntegerDistribution extends org.apache.commons.math.distribution.AbstractDistribution implements org.apache.commons.math.distribution.IntegerDistribution, java.io.Serializable {
    private static final long serialVersionUID = -1146319659338487221L;
    protected final org.apache.commons.math.random.RandomDataImpl randomData = new org.apache.commons.math.random.RandomDataImpl();

    @Override // org.apache.commons.math.distribution.IntegerDistribution
    public abstract double cumulativeProbability(int i) throws org.apache.commons.math.MathException;

    protected abstract int getDomainLowerBound(double d);

    protected abstract int getDomainUpperBound(double d);

    protected AbstractIntegerDistribution() {
    }

    @Override // org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d) throws org.apache.commons.math.MathException {
        return cumulativeProbability((int) org.apache.commons.math.util.FastMath.floor(d));
    }

    @Override // org.apache.commons.math.distribution.AbstractDistribution, org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d, double d2) throws org.apache.commons.math.MathException {
        if (d > d2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.LOWER_ENDPOINT_ABOVE_UPPER_ENDPOINT, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2));
        }
        if (org.apache.commons.math.util.FastMath.floor(d) < d) {
            return cumulativeProbability(((int) org.apache.commons.math.util.FastMath.floor(d)) + 1, (int) org.apache.commons.math.util.FastMath.floor(d2));
        }
        return cumulativeProbability((int) org.apache.commons.math.util.FastMath.floor(d), (int) org.apache.commons.math.util.FastMath.floor(d2));
    }

    @Override // org.apache.commons.math.distribution.DiscreteDistribution
    public double probability(double d) {
        if (org.apache.commons.math.util.FastMath.floor(d) == d) {
            return probability((int) d);
        }
        return 0.0d;
    }

    @Override // org.apache.commons.math.distribution.IntegerDistribution
    public double cumulativeProbability(int i, int i2) throws org.apache.commons.math.MathException {
        if (i > i2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.LOWER_ENDPOINT_ABOVE_UPPER_ENDPOINT, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        return cumulativeProbability(i2) - cumulativeProbability(i - 1);
    }

    @Override // org.apache.commons.math.distribution.IntegerDistribution
    public int inverseCumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d < 0.0d || d > 1.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_RANGE_SIMPLE, java.lang.Double.valueOf(d), java.lang.Double.valueOf(0.0d), java.lang.Double.valueOf(1.0d));
        }
        int domainLowerBound = getDomainLowerBound(d);
        int domainUpperBound = getDomainUpperBound(d);
        while (domainLowerBound < domainUpperBound) {
            int i = ((domainUpperBound - domainLowerBound) / 2) + domainLowerBound;
            if (checkedCumulativeProbability(i) > d) {
                if (i == domainUpperBound) {
                    domainUpperBound--;
                } else {
                    domainUpperBound = i;
                }
            } else if (i == domainLowerBound) {
                domainLowerBound++;
            } else {
                domainLowerBound = i;
            }
        }
        double checkedCumulativeProbability = checkedCumulativeProbability(domainLowerBound);
        while (checkedCumulativeProbability > d) {
            domainLowerBound--;
            checkedCumulativeProbability = checkedCumulativeProbability(domainLowerBound);
        }
        return domainLowerBound;
    }

    public void reseedRandomGenerator(long j) {
        this.randomData.reSeed(j);
    }

    public int sample() throws org.apache.commons.math.MathException {
        return this.randomData.nextInversionDeviate(this);
    }

    public int[] sample(int i) throws org.apache.commons.math.MathException {
        if (i <= 0) {
            org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_SAMPLE_SIZE, java.lang.Integer.valueOf(i));
        }
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = sample();
        }
        return iArr;
    }

    private double checkedCumulativeProbability(int i) throws org.apache.commons.math.MathException {
        double cumulativeProbability = cumulativeProbability(i);
        if (java.lang.Double.isNaN(cumulativeProbability)) {
            throw new org.apache.commons.math.MathException(org.apache.commons.math.exception.util.LocalizedFormats.DISCRETE_CUMULATIVE_PROBABILITY_RETURNED_NAN, java.lang.Integer.valueOf(i));
        }
        return cumulativeProbability;
    }

    public boolean isSupportLowerBoundInclusive() {
        return true;
    }

    public boolean isSupportUpperBoundInclusive() {
        return true;
    }
}
