package org.apache.commons.math.stat.descriptive;

/* loaded from: classes3.dex */
public class DescriptiveStatistics implements org.apache.commons.math.stat.descriptive.StatisticalSummary, java.io.Serializable {
    public static final int INFINITE_WINDOW = -1;
    private static final java.lang.String SET_QUANTILE_METHOD_NAME = "setQuantile";
    private static final long serialVersionUID = 4133067267405273064L;
    protected org.apache.commons.math.util.ResizableDoubleArray eDA;
    private org.apache.commons.math.stat.descriptive.UnivariateStatistic geometricMeanImpl;
    private org.apache.commons.math.stat.descriptive.UnivariateStatistic kurtosisImpl;
    private org.apache.commons.math.stat.descriptive.UnivariateStatistic maxImpl;
    private org.apache.commons.math.stat.descriptive.UnivariateStatistic meanImpl;
    private org.apache.commons.math.stat.descriptive.UnivariateStatistic minImpl;
    private org.apache.commons.math.stat.descriptive.UnivariateStatistic percentileImpl;
    private org.apache.commons.math.stat.descriptive.UnivariateStatistic skewnessImpl;
    private org.apache.commons.math.stat.descriptive.UnivariateStatistic sumImpl;
    private org.apache.commons.math.stat.descriptive.UnivariateStatistic sumsqImpl;
    private org.apache.commons.math.stat.descriptive.UnivariateStatistic varianceImpl;
    protected int windowSize;

    public DescriptiveStatistics() {
        this.windowSize = -1;
        this.eDA = new org.apache.commons.math.util.ResizableDoubleArray();
        this.meanImpl = new org.apache.commons.math.stat.descriptive.moment.Mean();
        this.geometricMeanImpl = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
        this.kurtosisImpl = new org.apache.commons.math.stat.descriptive.moment.Kurtosis();
        this.maxImpl = new org.apache.commons.math.stat.descriptive.rank.Max();
        this.minImpl = new org.apache.commons.math.stat.descriptive.rank.Min();
        this.percentileImpl = new org.apache.commons.math.stat.descriptive.rank.Percentile();
        this.skewnessImpl = new org.apache.commons.math.stat.descriptive.moment.Skewness();
        this.varianceImpl = new org.apache.commons.math.stat.descriptive.moment.Variance();
        this.sumsqImpl = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
        this.sumImpl = new org.apache.commons.math.stat.descriptive.summary.Sum();
    }

    public DescriptiveStatistics(int i) {
        this.windowSize = -1;
        this.eDA = new org.apache.commons.math.util.ResizableDoubleArray();
        this.meanImpl = new org.apache.commons.math.stat.descriptive.moment.Mean();
        this.geometricMeanImpl = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
        this.kurtosisImpl = new org.apache.commons.math.stat.descriptive.moment.Kurtosis();
        this.maxImpl = new org.apache.commons.math.stat.descriptive.rank.Max();
        this.minImpl = new org.apache.commons.math.stat.descriptive.rank.Min();
        this.percentileImpl = new org.apache.commons.math.stat.descriptive.rank.Percentile();
        this.skewnessImpl = new org.apache.commons.math.stat.descriptive.moment.Skewness();
        this.varianceImpl = new org.apache.commons.math.stat.descriptive.moment.Variance();
        this.sumsqImpl = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
        this.sumImpl = new org.apache.commons.math.stat.descriptive.summary.Sum();
        setWindowSize(i);
    }

    public DescriptiveStatistics(double[] dArr) {
        this.windowSize = -1;
        this.eDA = new org.apache.commons.math.util.ResizableDoubleArray();
        this.meanImpl = new org.apache.commons.math.stat.descriptive.moment.Mean();
        this.geometricMeanImpl = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
        this.kurtosisImpl = new org.apache.commons.math.stat.descriptive.moment.Kurtosis();
        this.maxImpl = new org.apache.commons.math.stat.descriptive.rank.Max();
        this.minImpl = new org.apache.commons.math.stat.descriptive.rank.Min();
        this.percentileImpl = new org.apache.commons.math.stat.descriptive.rank.Percentile();
        this.skewnessImpl = new org.apache.commons.math.stat.descriptive.moment.Skewness();
        this.varianceImpl = new org.apache.commons.math.stat.descriptive.moment.Variance();
        this.sumsqImpl = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
        this.sumImpl = new org.apache.commons.math.stat.descriptive.summary.Sum();
        if (dArr != null) {
            this.eDA = new org.apache.commons.math.util.ResizableDoubleArray(dArr);
        }
    }

    public DescriptiveStatistics(org.apache.commons.math.stat.descriptive.DescriptiveStatistics descriptiveStatistics) {
        this.windowSize = -1;
        this.eDA = new org.apache.commons.math.util.ResizableDoubleArray();
        this.meanImpl = new org.apache.commons.math.stat.descriptive.moment.Mean();
        this.geometricMeanImpl = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
        this.kurtosisImpl = new org.apache.commons.math.stat.descriptive.moment.Kurtosis();
        this.maxImpl = new org.apache.commons.math.stat.descriptive.rank.Max();
        this.minImpl = new org.apache.commons.math.stat.descriptive.rank.Min();
        this.percentileImpl = new org.apache.commons.math.stat.descriptive.rank.Percentile();
        this.skewnessImpl = new org.apache.commons.math.stat.descriptive.moment.Skewness();
        this.varianceImpl = new org.apache.commons.math.stat.descriptive.moment.Variance();
        this.sumsqImpl = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
        this.sumImpl = new org.apache.commons.math.stat.descriptive.summary.Sum();
        copy(descriptiveStatistics, this);
    }

    public void addValue(double d) {
        if (this.windowSize == -1) {
            this.eDA.addElement(d);
        } else if (getN() == this.windowSize) {
            this.eDA.addElementRolling(d);
        } else if (getN() < this.windowSize) {
            this.eDA.addElement(d);
        }
    }

    public void removeMostRecentValue() {
        this.eDA.discardMostRecentElements(1);
    }

    public double replaceMostRecentValue(double d) {
        return this.eDA.substituteMostRecentElement(d);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMean() {
        return apply(this.meanImpl);
    }

    public double getGeometricMean() {
        return apply(this.geometricMeanImpl);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getVariance() {
        return apply(this.varianceImpl);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getStandardDeviation() {
        if (getN() <= 0) {
            return Double.NaN;
        }
        if (getN() > 1) {
            return org.apache.commons.math.util.FastMath.sqrt(getVariance());
        }
        return 0.0d;
    }

    public double getSkewness() {
        return apply(this.skewnessImpl);
    }

    public double getKurtosis() {
        return apply(this.kurtosisImpl);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMax() {
        return apply(this.maxImpl);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMin() {
        return apply(this.minImpl);
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public long getN() {
        return this.eDA.getNumElements();
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getSum() {
        return apply(this.sumImpl);
    }

    public double getSumsq() {
        return apply(this.sumsqImpl);
    }

    public void clear() {
        this.eDA.clear();
    }

    public int getWindowSize() {
        return this.windowSize;
    }

    public void setWindowSize(int i) {
        if (i < 1 && i != -1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_WINDOW_SIZE, java.lang.Integer.valueOf(i));
        }
        this.windowSize = i;
        if (i != -1 && i < this.eDA.getNumElements()) {
            this.eDA.discardFrontElements(this.eDA.getNumElements() - i);
        }
    }

    public double[] getValues() {
        return this.eDA.getElements();
    }

    public double[] getSortedValues() {
        double[] values = getValues();
        java.util.Arrays.sort(values);
        return values;
    }

    public double getElement(int i) {
        return this.eDA.getElement(i);
    }

    public double getPercentile(double d) {
        if (this.percentileImpl instanceof org.apache.commons.math.stat.descriptive.rank.Percentile) {
            ((org.apache.commons.math.stat.descriptive.rank.Percentile) this.percentileImpl).setQuantile(d);
        } else {
            try {
                this.percentileImpl.getClass().getMethod(SET_QUANTILE_METHOD_NAME, java.lang.Double.TYPE).invoke(this.percentileImpl, java.lang.Double.valueOf(d));
            } catch (java.lang.IllegalAccessException e) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.PERCENTILE_IMPLEMENTATION_CANNOT_ACCESS_METHOD, SET_QUANTILE_METHOD_NAME, this.percentileImpl.getClass().getName());
            } catch (java.lang.NoSuchMethodException e2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.PERCENTILE_IMPLEMENTATION_UNSUPPORTED_METHOD, this.percentileImpl.getClass().getName(), SET_QUANTILE_METHOD_NAME);
            } catch (java.lang.reflect.InvocationTargetException e3) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(e3.getCause());
            }
        }
        return apply(this.percentileImpl);
    }

    public java.lang.String toString() {
        return "DescriptiveStatistics:\nn: " + getN() + "\nmin: " + getMin() + "\nmax: " + getMax() + "\nmean: " + getMean() + "\nstd dev: " + getStandardDeviation() + "\nmedian: " + getPercentile(50.0d) + "\nskewness: " + getSkewness() + "\nkurtosis: " + getKurtosis() + "\n";
    }

    public double apply(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        return univariateStatistic.evaluate(this.eDA.getInternalValues(), this.eDA.start(), this.eDA.getNumElements());
    }

    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getMeanImpl() {
        return this.meanImpl;
    }

    public synchronized void setMeanImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        this.meanImpl = univariateStatistic;
    }

    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getGeometricMeanImpl() {
        return this.geometricMeanImpl;
    }

    public synchronized void setGeometricMeanImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        this.geometricMeanImpl = univariateStatistic;
    }

    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getKurtosisImpl() {
        return this.kurtosisImpl;
    }

    public synchronized void setKurtosisImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        this.kurtosisImpl = univariateStatistic;
    }

    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getMaxImpl() {
        return this.maxImpl;
    }

    public synchronized void setMaxImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        this.maxImpl = univariateStatistic;
    }

    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getMinImpl() {
        return this.minImpl;
    }

    public synchronized void setMinImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        this.minImpl = univariateStatistic;
    }

    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getPercentileImpl() {
        return this.percentileImpl;
    }

    public synchronized void setPercentileImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        try {
            try {
                univariateStatistic.getClass().getMethod(SET_QUANTILE_METHOD_NAME, java.lang.Double.TYPE).invoke(univariateStatistic, java.lang.Double.valueOf(50.0d));
                this.percentileImpl = univariateStatistic;
            } catch (java.lang.NoSuchMethodException e) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.PERCENTILE_IMPLEMENTATION_UNSUPPORTED_METHOD, univariateStatistic.getClass().getName(), SET_QUANTILE_METHOD_NAME);
            } catch (java.lang.reflect.InvocationTargetException e2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(e2.getCause());
            }
        } catch (java.lang.IllegalAccessException e3) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.PERCENTILE_IMPLEMENTATION_CANNOT_ACCESS_METHOD, SET_QUANTILE_METHOD_NAME, univariateStatistic.getClass().getName());
        }
    }

    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getSkewnessImpl() {
        return this.skewnessImpl;
    }

    public synchronized void setSkewnessImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        this.skewnessImpl = univariateStatistic;
    }

    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getVarianceImpl() {
        return this.varianceImpl;
    }

    public synchronized void setVarianceImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        this.varianceImpl = univariateStatistic;
    }

    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getSumsqImpl() {
        return this.sumsqImpl;
    }

    public synchronized void setSumsqImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        this.sumsqImpl = univariateStatistic;
    }

    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getSumImpl() {
        return this.sumImpl;
    }

    public synchronized void setSumImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        this.sumImpl = univariateStatistic;
    }

    public org.apache.commons.math.stat.descriptive.DescriptiveStatistics copy() {
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics descriptiveStatistics = new org.apache.commons.math.stat.descriptive.DescriptiveStatistics();
        copy(this, descriptiveStatistics);
        return descriptiveStatistics;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.DescriptiveStatistics descriptiveStatistics, org.apache.commons.math.stat.descriptive.DescriptiveStatistics descriptiveStatistics2) {
        descriptiveStatistics2.eDA = descriptiveStatistics.eDA.copy();
        descriptiveStatistics2.windowSize = descriptiveStatistics.windowSize;
        descriptiveStatistics2.maxImpl = descriptiveStatistics.maxImpl.copy();
        descriptiveStatistics2.meanImpl = descriptiveStatistics.meanImpl.copy();
        descriptiveStatistics2.minImpl = descriptiveStatistics.minImpl.copy();
        descriptiveStatistics2.sumImpl = descriptiveStatistics.sumImpl.copy();
        descriptiveStatistics2.varianceImpl = descriptiveStatistics.varianceImpl.copy();
        descriptiveStatistics2.sumsqImpl = descriptiveStatistics.sumsqImpl.copy();
        descriptiveStatistics2.geometricMeanImpl = descriptiveStatistics.geometricMeanImpl.copy();
        descriptiveStatistics2.kurtosisImpl = descriptiveStatistics.kurtosisImpl;
        descriptiveStatistics2.skewnessImpl = descriptiveStatistics.skewnessImpl;
        descriptiveStatistics2.percentileImpl = descriptiveStatistics.percentileImpl;
    }
}
