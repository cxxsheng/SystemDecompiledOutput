package org.apache.commons.math.stat;

/* loaded from: classes3.dex */
public final class StatUtils {
    private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic SUM = new org.apache.commons.math.stat.descriptive.summary.Sum();
    private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic SUM_OF_SQUARES = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
    private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic PRODUCT = new org.apache.commons.math.stat.descriptive.summary.Product();
    private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic SUM_OF_LOGS = new org.apache.commons.math.stat.descriptive.summary.SumOfLogs();
    private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic MIN = new org.apache.commons.math.stat.descriptive.rank.Min();
    private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic MAX = new org.apache.commons.math.stat.descriptive.rank.Max();
    private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic MEAN = new org.apache.commons.math.stat.descriptive.moment.Mean();
    private static final org.apache.commons.math.stat.descriptive.moment.Variance VARIANCE = new org.apache.commons.math.stat.descriptive.moment.Variance();
    private static final org.apache.commons.math.stat.descriptive.rank.Percentile PERCENTILE = new org.apache.commons.math.stat.descriptive.rank.Percentile();
    private static final org.apache.commons.math.stat.descriptive.moment.GeometricMean GEOMETRIC_MEAN = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();

    private StatUtils() {
    }

    public static double sum(double[] dArr) {
        return SUM.evaluate(dArr);
    }

    public static double sum(double[] dArr, int i, int i2) {
        return SUM.evaluate(dArr, i, i2);
    }

    public static double sumSq(double[] dArr) {
        return SUM_OF_SQUARES.evaluate(dArr);
    }

    public static double sumSq(double[] dArr, int i, int i2) {
        return SUM_OF_SQUARES.evaluate(dArr, i, i2);
    }

    public static double product(double[] dArr) {
        return PRODUCT.evaluate(dArr);
    }

    public static double product(double[] dArr, int i, int i2) {
        return PRODUCT.evaluate(dArr, i, i2);
    }

    public static double sumLog(double[] dArr) {
        return SUM_OF_LOGS.evaluate(dArr);
    }

    public static double sumLog(double[] dArr, int i, int i2) {
        return SUM_OF_LOGS.evaluate(dArr, i, i2);
    }

    public static double mean(double[] dArr) {
        return MEAN.evaluate(dArr);
    }

    public static double mean(double[] dArr, int i, int i2) {
        return MEAN.evaluate(dArr, i, i2);
    }

    public static double geometricMean(double[] dArr) {
        return GEOMETRIC_MEAN.evaluate(dArr);
    }

    public static double geometricMean(double[] dArr, int i, int i2) {
        return GEOMETRIC_MEAN.evaluate(dArr, i, i2);
    }

    public static double variance(double[] dArr) {
        return VARIANCE.evaluate(dArr);
    }

    public static double variance(double[] dArr, int i, int i2) {
        return VARIANCE.evaluate(dArr, i, i2);
    }

    public static double variance(double[] dArr, double d, int i, int i2) {
        return VARIANCE.evaluate(dArr, d, i, i2);
    }

    public static double variance(double[] dArr, double d) {
        return VARIANCE.evaluate(dArr, d);
    }

    public static double max(double[] dArr) {
        return MAX.evaluate(dArr);
    }

    public static double max(double[] dArr, int i, int i2) {
        return MAX.evaluate(dArr, i, i2);
    }

    public static double min(double[] dArr) {
        return MIN.evaluate(dArr);
    }

    public static double min(double[] dArr, int i, int i2) {
        return MIN.evaluate(dArr, i, i2);
    }

    public static double percentile(double[] dArr, double d) {
        return PERCENTILE.evaluate(dArr, d);
    }

    public static double percentile(double[] dArr, int i, int i2, double d) {
        return PERCENTILE.evaluate(dArr, i, i2, d);
    }

    public static double sumDifference(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        int length = dArr.length;
        if (length != dArr2.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(length), java.lang.Integer.valueOf(dArr2.length));
        }
        if (length < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(dArr2.length), 1);
        }
        double d = 0.0d;
        for (int i = 0; i < length; i++) {
            d += dArr[i] - dArr2[i];
        }
        return d;
    }

    public static double meanDifference(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        return sumDifference(dArr, dArr2) / dArr.length;
    }

    public static double varianceDifference(double[] dArr, double[] dArr2, double d) throws java.lang.IllegalArgumentException {
        int length = dArr.length;
        if (length != dArr2.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(length), java.lang.Integer.valueOf(dArr2.length));
        }
        if (length < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(length), 2);
        }
        double d2 = 0.0d;
        double d3 = 0.0d;
        for (int i = 0; i < length; i++) {
            double d4 = (dArr[i] - dArr2[i]) - d;
            d2 += d4 * d4;
            d3 += d4;
        }
        return (d2 - ((d3 * d3) / length)) / (length - 1);
    }

    public static double[] normalize(double[] dArr) {
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics descriptiveStatistics = new org.apache.commons.math.stat.descriptive.DescriptiveStatistics();
        for (double d : dArr) {
            descriptiveStatistics.addValue(d);
        }
        double mean = descriptiveStatistics.getMean();
        double standardDeviation = descriptiveStatistics.getStandardDeviation();
        double[] dArr2 = new double[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            dArr2[i] = (dArr[i] - mean) / standardDeviation;
        }
        return dArr2;
    }
}
