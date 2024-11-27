package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public class HypergeometricDistributionImpl extends org.apache.commons.math.distribution.AbstractIntegerDistribution implements org.apache.commons.math.distribution.HypergeometricDistribution, java.io.Serializable {
    private static final long serialVersionUID = -436928820673516179L;
    private int numberOfSuccesses;
    private int populationSize;
    private int sampleSize;

    public HypergeometricDistributionImpl(int i, int i2, int i3) {
        if (i2 > i) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NUMBER_OF_SUCCESS_LARGER_THAN_POPULATION_SIZE, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i));
        }
        if (i3 > i) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.SAMPLE_SIZE_LARGER_THAN_POPULATION_SIZE, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i));
        }
        setPopulationSizeInternal(i);
        setSampleSizeInternal(i3);
        setNumberOfSuccessesInternal(i2);
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution, org.apache.commons.math.distribution.IntegerDistribution
    public double cumulativeProbability(int i) {
        int[] domain = getDomain(this.populationSize, this.numberOfSuccesses, this.sampleSize);
        if (i < domain[0]) {
            return 0.0d;
        }
        if (i >= domain[1]) {
            return 1.0d;
        }
        return innerCumulativeProbability(domain[0], i, 1, this.populationSize, this.numberOfSuccesses, this.sampleSize);
    }

    private int[] getDomain(int i, int i2, int i3) {
        return new int[]{getLowerDomain(i, i2, i3), getUpperDomain(i2, i3)};
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution
    protected int getDomainLowerBound(double d) {
        return getLowerDomain(this.populationSize, this.numberOfSuccesses, this.sampleSize);
    }

    @Override // org.apache.commons.math.distribution.AbstractIntegerDistribution
    protected int getDomainUpperBound(double d) {
        return getUpperDomain(this.sampleSize, this.numberOfSuccesses);
    }

    private int getLowerDomain(int i, int i2, int i3) {
        return org.apache.commons.math.util.FastMath.max(0, i2 - (i - i3));
    }

    @Override // org.apache.commons.math.distribution.HypergeometricDistribution
    public int getNumberOfSuccesses() {
        return this.numberOfSuccesses;
    }

    @Override // org.apache.commons.math.distribution.HypergeometricDistribution
    public int getPopulationSize() {
        return this.populationSize;
    }

    @Override // org.apache.commons.math.distribution.HypergeometricDistribution
    public int getSampleSize() {
        return this.sampleSize;
    }

    private int getUpperDomain(int i, int i2) {
        return org.apache.commons.math.util.FastMath.min(i2, i);
    }

    @Override // org.apache.commons.math.distribution.IntegerDistribution
    public double probability(int i) {
        int[] domain = getDomain(this.populationSize, this.numberOfSuccesses, this.sampleSize);
        if (i < domain[0] || i > domain[1]) {
            return 0.0d;
        }
        double d = this.sampleSize / this.populationSize;
        double d2 = (this.populationSize - this.sampleSize) / this.populationSize;
        return org.apache.commons.math.util.FastMath.exp((org.apache.commons.math.distribution.SaddlePointExpansion.logBinomialProbability(i, this.numberOfSuccesses, d, d2) + org.apache.commons.math.distribution.SaddlePointExpansion.logBinomialProbability(this.sampleSize - i, this.populationSize - this.numberOfSuccesses, d, d2)) - org.apache.commons.math.distribution.SaddlePointExpansion.logBinomialProbability(this.sampleSize, this.populationSize, d, d2));
    }

    private double probability(int i, int i2, int i3, int i4) {
        return org.apache.commons.math.util.FastMath.exp((org.apache.commons.math.util.MathUtils.binomialCoefficientLog(i2, i4) + org.apache.commons.math.util.MathUtils.binomialCoefficientLog(i - i2, i3 - i4)) - org.apache.commons.math.util.MathUtils.binomialCoefficientLog(i, i3));
    }

    @Override // org.apache.commons.math.distribution.HypergeometricDistribution
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

    @Override // org.apache.commons.math.distribution.HypergeometricDistribution
    @java.lang.Deprecated
    public void setPopulationSize(int i) {
        setPopulationSizeInternal(i);
    }

    private void setPopulationSizeInternal(int i) {
        if (i <= 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_POPULATION_SIZE, java.lang.Integer.valueOf(i));
        }
        this.populationSize = i;
    }

    @Override // org.apache.commons.math.distribution.HypergeometricDistribution
    @java.lang.Deprecated
    public void setSampleSize(int i) {
        setSampleSizeInternal(i);
    }

    private void setSampleSizeInternal(int i) {
        if (i < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_SAMPLE_SIZE, java.lang.Integer.valueOf(i));
        }
        this.sampleSize = i;
    }

    public double upperCumulativeProbability(int i) {
        int[] domain = getDomain(this.populationSize, this.numberOfSuccesses, this.sampleSize);
        if (i < domain[0]) {
            return 1.0d;
        }
        if (i > domain[1]) {
            return 0.0d;
        }
        return innerCumulativeProbability(domain[1], i, -1, this.populationSize, this.numberOfSuccesses, this.sampleSize);
    }

    private double innerCumulativeProbability(int i, int i2, int i3, int i4, int i5, int i6) {
        double probability = probability(i4, i5, i6, i);
        while (i != i2) {
            i += i3;
            probability += probability(i4, i5, i6, i);
        }
        return probability;
    }

    public int getSupportLowerBound() {
        return org.apache.commons.math.util.FastMath.max(0, (getSampleSize() + getNumberOfSuccesses()) - getPopulationSize());
    }

    public int getSupportUpperBound() {
        return org.apache.commons.math.util.FastMath.min(getNumberOfSuccesses(), getSampleSize());
    }

    protected double getNumericalMean() {
        return (getSampleSize() * getNumberOfSuccesses()) / getPopulationSize();
    }

    public double getNumericalVariance() {
        double populationSize = getPopulationSize();
        double numberOfSuccesses = getNumberOfSuccesses();
        double sampleSize = getSampleSize();
        return (((sampleSize * numberOfSuccesses) * (populationSize - sampleSize)) * (populationSize - numberOfSuccesses)) / ((populationSize * populationSize) * (populationSize - 1.0d));
    }
}
