package org.apache.commons.math.analysis.interpolation;

/* loaded from: classes3.dex */
public class LoessInterpolator implements org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator, java.io.Serializable {
    public static final double DEFAULT_ACCURACY = 1.0E-12d;
    public static final double DEFAULT_BANDWIDTH = 0.3d;
    public static final int DEFAULT_ROBUSTNESS_ITERS = 2;
    private static final long serialVersionUID = 5204927143605193821L;
    private final double accuracy;
    private final double bandwidth;
    private final int robustnessIters;

    public LoessInterpolator() {
        this.bandwidth = 0.3d;
        this.robustnessIters = 2;
        this.accuracy = 1.0E-12d;
    }

    public LoessInterpolator(double d, int i) throws org.apache.commons.math.MathException {
        this(d, i, 1.0E-12d);
    }

    public LoessInterpolator(double d, int i, double d2) throws org.apache.commons.math.MathException {
        if (d < 0.0d || d > 1.0d) {
            throw new org.apache.commons.math.MathException(org.apache.commons.math.exception.util.LocalizedFormats.BANDWIDTH_OUT_OF_INTERVAL, java.lang.Double.valueOf(d));
        }
        this.bandwidth = d;
        if (i < 0) {
            throw new org.apache.commons.math.MathException(org.apache.commons.math.exception.util.LocalizedFormats.NEGATIVE_ROBUSTNESS_ITERATIONS, java.lang.Integer.valueOf(i));
        }
        this.robustnessIters = i;
        this.accuracy = d2;
    }

    @Override // org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator
    public final org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction interpolate(double[] dArr, double[] dArr2) throws org.apache.commons.math.MathException {
        return new org.apache.commons.math.analysis.interpolation.SplineInterpolator().interpolate(dArr, smooth(dArr, dArr2));
    }

    public final double[] smooth(double[] dArr, double[] dArr2, double[] dArr3) throws org.apache.commons.math.MathException {
        int i;
        int i2 = 0;
        int i3 = 1;
        if (dArr.length != dArr2.length) {
            throw new org.apache.commons.math.MathException(org.apache.commons.math.exception.util.LocalizedFormats.MISMATCHED_LOESS_ABSCISSA_ORDINATE_ARRAYS, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr2.length));
        }
        int length = dArr.length;
        if (length == 0) {
            throw new org.apache.commons.math.MathException(org.apache.commons.math.exception.util.LocalizedFormats.LOESS_EXPECTS_AT_LEAST_ONE_POINT, new java.lang.Object[0]);
        }
        checkAllFiniteReal(dArr, org.apache.commons.math.exception.util.LocalizedFormats.NON_REAL_FINITE_ABSCISSA);
        checkAllFiniteReal(dArr2, org.apache.commons.math.exception.util.LocalizedFormats.NON_REAL_FINITE_ORDINATE);
        checkAllFiniteReal(dArr3, org.apache.commons.math.exception.util.LocalizedFormats.NON_REAL_FINITE_WEIGHT);
        checkStrictlyIncreasing(dArr);
        if (length == 1) {
            return new double[]{dArr2[0]};
        }
        if (length == 2) {
            return new double[]{dArr2[0], dArr2[1]};
        }
        double d = length;
        int i4 = (int) (this.bandwidth * d);
        if (i4 < 2) {
            throw new org.apache.commons.math.MathException(org.apache.commons.math.exception.util.LocalizedFormats.TOO_SMALL_BANDWIDTH, java.lang.Integer.valueOf(length), java.lang.Double.valueOf(2.0d / d), java.lang.Double.valueOf(this.bandwidth));
        }
        double[] dArr4 = new double[length];
        double[] dArr5 = new double[length];
        double[] dArr6 = new double[length];
        double[] dArr7 = new double[length];
        java.util.Arrays.fill(dArr7, 1.0d);
        int i5 = 0;
        while (i5 <= this.robustnessIters) {
            int[] iArr = {i2, i4 - 1};
            int i6 = i2;
            while (true) {
                double d2 = 0.0d;
                if (i6 >= length) {
                    break;
                }
                double d3 = dArr[i6];
                if (i6 > 0) {
                    updateBandwidthInterval(dArr, dArr3, i6, iArr);
                }
                int i7 = iArr[i2];
                int i8 = iArr[i3];
                if (dArr[i6] - dArr[i7] > dArr[i8] - dArr[i6]) {
                    i = i7;
                } else {
                    i = i8;
                }
                double abs = org.apache.commons.math.util.FastMath.abs(1.0d / (dArr[i] - d3));
                double d4 = 0.0d;
                double d5 = 0.0d;
                double d6 = 0.0d;
                double d7 = 0.0d;
                double d8 = 0.0d;
                while (i7 <= i8) {
                    double d9 = dArr[i7];
                    double d10 = dArr2[i7];
                    double tricube = tricube((i7 < i6 ? d3 - d9 : d9 - d3) * abs) * dArr7[i7] * dArr3[i7];
                    double d11 = d9 * tricube;
                    d5 += tricube;
                    d4 += d11;
                    d8 += d9 * d11;
                    d6 += tricube * d10;
                    d7 += d10 * d11;
                    i7 += i3;
                }
                double d12 = d4 / d5;
                double d13 = d6 / d5;
                double d14 = d7 / d5;
                double d15 = (d8 / d5) - (d12 * d12);
                int i9 = length;
                if (org.apache.commons.math.util.FastMath.sqrt(org.apache.commons.math.util.FastMath.abs(d15)) >= this.accuracy) {
                    d2 = (d14 - (d12 * d13)) / d15;
                }
                dArr4[i6] = (d2 * d3) + (d13 - (d12 * d2));
                dArr5[i6] = org.apache.commons.math.util.FastMath.abs(dArr2[i6] - dArr4[i6]);
                i6++;
                length = i9;
                i2 = 0;
                i3 = 1;
            }
            int i10 = length;
            if (i5 == this.robustnessIters) {
                break;
            }
            java.lang.System.arraycopy(dArr5, 0, dArr6, 0, i10);
            java.util.Arrays.sort(dArr6);
            double d16 = dArr6[i10 / 2];
            if (org.apache.commons.math.util.FastMath.abs(d16) < this.accuracy) {
                break;
            }
            for (int i11 = 0; i11 < i10; i11++) {
                double d17 = dArr5[i11] / (6.0d * d16);
                if (d17 < 1.0d) {
                    double d18 = 1.0d - (d17 * d17);
                    dArr7[i11] = d18 * d18;
                } else {
                    dArr7[i11] = 0.0d;
                }
            }
            i5++;
            length = i10;
            i3 = 1;
            i2 = 0;
        }
        return dArr4;
    }

    public final double[] smooth(double[] dArr, double[] dArr2) throws org.apache.commons.math.MathException {
        if (dArr.length != dArr2.length) {
            throw new org.apache.commons.math.MathException(org.apache.commons.math.exception.util.LocalizedFormats.MISMATCHED_LOESS_ABSCISSA_ORDINATE_ARRAYS, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr2.length));
        }
        double[] dArr3 = new double[dArr.length];
        java.util.Arrays.fill(dArr3, 1.0d);
        return smooth(dArr, dArr2, dArr3);
    }

    private static void updateBandwidthInterval(double[] dArr, double[] dArr2, int i, int[] iArr) {
        int i2 = iArr[0];
        int nextNonzero = nextNonzero(dArr2, iArr[1]);
        if (nextNonzero < dArr.length && dArr[nextNonzero] - dArr[i] < dArr[i] - dArr[i2]) {
            iArr[0] = nextNonzero(dArr2, iArr[0]);
            iArr[1] = nextNonzero;
        }
    }

    private static int nextNonzero(double[] dArr, int i) {
        do {
            i++;
            if (i >= dArr.length) {
                break;
            }
        } while (dArr[i] == 0.0d);
        return i;
    }

    private static double tricube(double d) {
        double d2 = 1.0d - ((d * d) * d);
        return d2 * d2 * d2;
    }

    private static void checkAllFiniteReal(double[] dArr, org.apache.commons.math.exception.util.Localizable localizable) throws org.apache.commons.math.MathException {
        for (int i = 0; i < dArr.length; i++) {
            double d = dArr[i];
            if (java.lang.Double.isInfinite(d) || java.lang.Double.isNaN(d)) {
                throw new org.apache.commons.math.MathException(localizable, java.lang.Integer.valueOf(i), java.lang.Double.valueOf(d));
            }
        }
    }

    private static void checkStrictlyIncreasing(double[] dArr) throws org.apache.commons.math.MathException {
        for (int i = 0; i < dArr.length; i++) {
            if (i >= 1) {
                int i2 = i - 1;
                if (dArr[i2] >= dArr[i]) {
                    throw new org.apache.commons.math.MathException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_ORDER_ABSCISSA_ARRAY, java.lang.Integer.valueOf(i2), java.lang.Double.valueOf(dArr[i2]), java.lang.Integer.valueOf(i), java.lang.Double.valueOf(dArr[i]));
                }
            }
        }
    }
}
