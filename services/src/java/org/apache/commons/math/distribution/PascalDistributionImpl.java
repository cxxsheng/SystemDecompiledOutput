package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class PascalDistributionImpl extends org.apache.commons.math.distribution.AbstractIntegerDistribution implements org.apache.commons.math.distribution.PascalDistribution, java.io.Serializable {
    private static final long serialVersionUID = 6751309484392813623L;
    private int numberOfSuccesses;
    private double probabilityOfSuccess;

    public PascalDistributionImpl(int i, double d) {
        setNumberOfSuccessesInternal(i);
        setProbabilityOfSuccessInternal(d);
    }

    @Override // org.apache.commons.math.distribution.PascalDistribution
    public int getNumberOfSuccesses() {
        return this.numberOfSuccesses;
    }

    @Override // org.apache.commons.math.distribution.PascalDistribution
    public double getProbabilityOfSuccess() {
        return this.probabilityOfSuccess;
    }

    @Override // org.apache.commons.math.distribution.PascalDistribution
    @java.lang.Deprecated
    public void setNumberOfSuccesses(int i) {
        setNumberOfSuccessesInternal(i);
    }

    private void setNumberOfSuccessesInternal(int i) {
        if (i < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NEGATIVE_NUMBER_OF_SUCCESSES, java.lang.Integer.valueOf(i));
        }
        this.numberOfSuccesses = i;
    }

    @Override // org.apache.commons.math.distribution.PascalDistribution
    @java.lang.Deprecated
    public void setProbabilityOfSuccess(double d) {
        setProbabilityOfSuccessInternal(d);
    }

    private void setProbabilityOfSuccessInternal(double d) {
        if (d < 0.0d || d > 1.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_RANGE_SIMPLE, java.lang.Double.valueOf(d), java.lang.Double.valueOf(0.0d), java.lang.Double.valueOf(1.0d));
        }
        this.probabilityOfSuccess = d;
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution
    protected int getDomainLowerBound(double d) {
        return -1;
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution
    protected int getDomainUpperBound(double d) {
        return 2147483646;
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution, org.apache.commons.math.distribution.IntegerDistribution
    public double cumulativeProbability(int i) throws org.apache.commons.math.MathException {
        if (i < 0) {
            return 0.0d;
        }
        return org.apache.commons.math.special.Beta.regularizedBeta(this.probabilityOfSuccess, this.numberOfSuccesses, i + 1);
    }

    @Override // org.apache.commons.math.distribution.IntegerDistribution
    public double probability(int i) {
        if (i < 0) {
            return 0.0d;
        }
        return org.apache.commons.math.util.MathUtils.binomialCoefficientDouble((this.numberOfSuccesses + i) - 1, this.numberOfSuccesses - 1) * org.apache.commons.math.util.FastMath.pow(this.probabilityOfSuccess, this.numberOfSuccesses) * org.apache.commons.math.util.FastMath.pow(1.0d - this.probabilityOfSuccess, i);
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution, org.apache.commons.math.distribution.IntegerDistribution
    public int inverseCumulativeProbability(double d) throws org.apache.commons.math.MathException {
        if (d == 0.0d) {
            return -1;
        }
        if (d == 1.0d) {
            return Integer.MAX_VALUE;
        }
        return super.inverseCumulativeProbability(d);
    }

    public int getSupportLowerBound() {
        return 0;
    }

    public int getSupportUpperBound() {
        return Integer.MAX_VALUE;
    }

    public double getNumericalMean() {
        double probabilityOfSuccess = getProbabilityOfSuccess();
        return (getNumberOfSuccesses() * probabilityOfSuccess) / (1.0d - probabilityOfSuccess);
    }

    public double getNumericalVariance() {
        double probabilityOfSuccess = getProbabilityOfSuccess();
        double d = 1.0d - probabilityOfSuccess;
        return (getNumberOfSuccesses() * probabilityOfSuccess) / (d * d);
    }
}
