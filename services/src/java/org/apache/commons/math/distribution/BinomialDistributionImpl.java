package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class BinomialDistributionImpl extends org.apache.commons.math.distribution.AbstractIntegerDistribution implements org.apache.commons.math.distribution.BinomialDistribution, java.io.Serializable {
    private static final long serialVersionUID = 6751309484392813623L;
    private int numberOfTrials;
    private double probabilityOfSuccess;

    public BinomialDistributionImpl(int i, double d) {
        setNumberOfTrialsInternal(i);
        setProbabilityOfSuccessInternal(d);
    }

    @Override // org.apache.commons.math.distribution.BinomialDistribution
    public int getNumberOfTrials() {
        return this.numberOfTrials;
    }

    @Override // org.apache.commons.math.distribution.BinomialDistribution
    public double getProbabilityOfSuccess() {
        return this.probabilityOfSuccess;
    }

    @Override // org.apache.commons.math.distribution.BinomialDistribution
    @java.lang.Deprecated
    public void setNumberOfTrials(int i) {
        setNumberOfTrialsInternal(i);
    }

    private void setNumberOfTrialsInternal(int i) {
        if (i < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NEGATIVE_NUMBER_OF_TRIALS, java.lang.Integer.valueOf(i));
        }
        this.numberOfTrials = i;
    }

    @Override // org.apache.commons.math.distribution.BinomialDistribution
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
        return this.numberOfTrials;
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution, org.apache.commons.math.distribution.IntegerDistribution
    public double cumulativeProbability(int i) throws org.apache.commons.math.MathException {
        if (i < 0) {
            return 0.0d;
        }
        if (i >= this.numberOfTrials) {
            return 1.0d;
        }
        return 1.0d - org.apache.commons.math.special.Beta.regularizedBeta(getProbabilityOfSuccess(), i + 1.0d, this.numberOfTrials - i);
    }

    @Override // org.apache.commons.math.distribution.IntegerDistribution
    public double probability(int i) {
        if (i < 0 || i > this.numberOfTrials) {
            return 0.0d;
        }
        return org.apache.commons.math.util.FastMath.exp(org.apache.commons.math.distribution.SaddlePointExpansion.logBinomialProbability(i, this.numberOfTrials, this.probabilityOfSuccess, 1.0d - this.probabilityOfSuccess));
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
        return getNumberOfTrials();
    }

    public double getNumericalMean() {
        return getNumberOfTrials() * getProbabilityOfSuccess();
    }

    public double getNumericalVariance() {
        double probabilityOfSuccess = getProbabilityOfSuccess();
        return getNumberOfTrials() * probabilityOfSuccess * (1.0d - probabilityOfSuccess);
    }
}
