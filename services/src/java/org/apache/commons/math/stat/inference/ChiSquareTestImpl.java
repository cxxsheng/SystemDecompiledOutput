package org.apache.commons.math.stat.inference;

/* loaded from: classes3.dex */
public class ChiSquareTestImpl implements org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest {
    private org.apache.commons.math.distribution.ChiSquaredDistribution distribution;

    public ChiSquareTestImpl() {
        this(new org.apache.commons.math.distribution.ChiSquaredDistributionImpl(1.0d));
    }

    public ChiSquareTestImpl(org.apache.commons.math.distribution.ChiSquaredDistribution chiSquaredDistribution) {
        setDistribution(chiSquaredDistribution);
    }

    @Override // org.apache.commons.math.stat.inference.ChiSquareTest
    public double chiSquare(double[] dArr, long[] jArr) throws java.lang.IllegalArgumentException {
        double d;
        boolean z;
        double d2;
        double d3;
        if (dArr.length < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(dArr.length), 2);
        }
        if (dArr.length != jArr.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(jArr.length));
        }
        checkPositive(dArr);
        checkNonNegative(jArr);
        double d4 = 0.0d;
        double d5 = 0.0d;
        double d6 = 0.0d;
        for (int i = 0; i < jArr.length; i++) {
            d5 += dArr[i];
            d6 += jArr[i];
        }
        if (org.apache.commons.math.util.FastMath.abs(d5 - d6) <= 1.0E-5d) {
            d = 1.0d;
            z = false;
        } else {
            d = d6 / d5;
            z = true;
        }
        for (int i2 = 0; i2 < jArr.length; i2++) {
            if (z) {
                double d7 = jArr[i2] - (dArr[i2] * d);
                d2 = d7 * d7;
                d3 = dArr[i2] * d;
            } else {
                double d8 = jArr[i2] - dArr[i2];
                d2 = d8 * d8;
                d3 = dArr[i2];
            }
            d4 += d2 / d3;
        }
        return d4;
    }

    @Override // org.apache.commons.math.stat.inference.ChiSquareTest
    public double chiSquareTest(double[] dArr, long[] jArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        this.distribution.setDegreesOfFreedom(dArr.length - 1.0d);
        return 1.0d - this.distribution.cumulativeProbability(chiSquare(dArr, jArr));
    }

    @Override // org.apache.commons.math.stat.inference.ChiSquareTest
    public boolean chiSquareTest(double[] dArr, long[] jArr, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        if (d <= 0.0d || d > 0.5d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, java.lang.Double.valueOf(d), 0, java.lang.Double.valueOf(0.5d));
        }
        return chiSquareTest(dArr, jArr) < d;
    }

    @Override // org.apache.commons.math.stat.inference.ChiSquareTest
    public double chiSquare(long[][] jArr) throws java.lang.IllegalArgumentException {
        checkArray(jArr);
        int length = jArr.length;
        int i = 0;
        int length2 = jArr[0].length;
        double[] dArr = new double[length];
        double[] dArr2 = new double[length2];
        double d = 0.0d;
        double d2 = 0.0d;
        for (int i2 = 0; i2 < length; i2++) {
            for (int i3 = 0; i3 < length2; i3++) {
                dArr[i2] = dArr[i2] + jArr[i2][i3];
                dArr2[i3] = dArr2[i3] + jArr[i2][i3];
                d2 += jArr[i2][i3];
            }
        }
        int i4 = 0;
        while (i4 < length) {
            int i5 = i;
            while (i5 < length2) {
                double d3 = (dArr[i4] * dArr2[i5]) / d2;
                d += ((jArr[i4][i5] - d3) * (jArr[i4][i5] - d3)) / d3;
                i5++;
                length2 = length2;
            }
            i4++;
            i = 0;
        }
        return d;
    }

    @Override // org.apache.commons.math.stat.inference.ChiSquareTest
    public double chiSquareTest(long[][] jArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        checkArray(jArr);
        this.distribution.setDegreesOfFreedom((jArr.length - 1.0d) * (jArr[0].length - 1.0d));
        return 1.0d - this.distribution.cumulativeProbability(chiSquare(jArr));
    }

    @Override // org.apache.commons.math.stat.inference.ChiSquareTest
    public boolean chiSquareTest(long[][] jArr, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        if (d <= 0.0d || d > 0.5d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, java.lang.Double.valueOf(d), java.lang.Double.valueOf(0.0d), java.lang.Double.valueOf(0.5d));
        }
        return chiSquareTest(jArr) < d;
    }

    @Override // org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest
    public double chiSquareDataSetsComparison(long[] jArr, long[] jArr2) throws java.lang.IllegalArgumentException {
        double d;
        double d2;
        if (jArr.length < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(jArr.length), 2);
        }
        if (jArr.length != jArr2.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(jArr.length), java.lang.Integer.valueOf(jArr2.length));
        }
        checkNonNegative(jArr);
        checkNonNegative(jArr2);
        long j = 0;
        long j2 = 0;
        for (int i = 0; i < jArr.length; i++) {
            j += jArr[i];
            j2 += jArr2[i];
        }
        if (j == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OBSERVED_COUNTS_ALL_ZERO, 1);
        }
        if (j2 == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OBSERVED_COUNTS_ALL_ZERO, 2);
        }
        boolean z = j != j2;
        double d3 = 0.0d;
        if (!z) {
            d = 0.0d;
        } else {
            d = org.apache.commons.math.util.FastMath.sqrt(j / j2);
        }
        for (int i2 = 0; i2 < jArr.length; i2++) {
            if (jArr[i2] == 0 && jArr2[i2] == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OBSERVED_COUNTS_BOTTH_ZERO_FOR_ENTRY, java.lang.Integer.valueOf(i2));
            }
            double d4 = jArr[i2];
            double d5 = jArr2[i2];
            if (z) {
                d2 = (d4 / d) - (d5 * d);
            } else {
                d2 = d4 - d5;
            }
            d3 += (d2 * d2) / (d4 + d5);
        }
        return d3;
    }

    @Override // org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest
    public double chiSquareTestDataSetsComparison(long[] jArr, long[] jArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        this.distribution.setDegreesOfFreedom(jArr.length - 1.0d);
        return 1.0d - this.distribution.cumulativeProbability(chiSquareDataSetsComparison(jArr, jArr2));
    }

    @Override // org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest
    public boolean chiSquareTestDataSetsComparison(long[] jArr, long[] jArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        if (d <= 0.0d || d > 0.5d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, java.lang.Double.valueOf(d), java.lang.Double.valueOf(0.0d), java.lang.Double.valueOf(0.5d));
        }
        return chiSquareTestDataSetsComparison(jArr, jArr2) < d;
    }

    private void checkArray(long[][] jArr) throws java.lang.IllegalArgumentException {
        if (jArr.length < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(jArr.length), 2);
        }
        if (jArr[0].length < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(jArr[0].length), 2);
        }
        checkRectangular(jArr);
        checkNonNegative(jArr);
    }

    private void checkRectangular(long[][] jArr) {
        for (int i = 1; i < jArr.length; i++) {
            if (jArr[i].length != jArr[0].length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(jArr[i].length), java.lang.Integer.valueOf(jArr[0].length));
            }
        }
    }

    private void checkPositive(double[] dArr) throws java.lang.IllegalArgumentException {
        for (int i = 0; i < dArr.length; i++) {
            if (dArr[i] <= 0.0d) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_ELEMENT_AT_INDEX, java.lang.Integer.valueOf(i), java.lang.Double.valueOf(dArr[i]));
            }
        }
    }

    private void checkNonNegative(long[] jArr) throws java.lang.IllegalArgumentException {
        for (int i = 0; i < jArr.length; i++) {
            if (jArr[i] < 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NEGATIVE_ELEMENT_AT_INDEX, java.lang.Integer.valueOf(i), java.lang.Long.valueOf(jArr[i]));
            }
        }
    }

    private void checkNonNegative(long[][] jArr) throws java.lang.IllegalArgumentException {
        for (int i = 0; i < jArr.length; i++) {
            for (int i2 = 0; i2 < jArr[i].length; i2++) {
                if (jArr[i][i2] < 0) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NEGATIVE_ELEMENT_AT_2D_INDEX, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Long.valueOf(jArr[i][i2]));
                }
            }
        }
    }

    public void setDistribution(org.apache.commons.math.distribution.ChiSquaredDistribution chiSquaredDistribution) {
        this.distribution = chiSquaredDistribution;
    }
}
