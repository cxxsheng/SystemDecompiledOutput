package org.apache.commons.math.analysis.interpolation;

/* loaded from: classes3.dex */
public class BicubicSplineInterpolatingFunction implements org.apache.commons.math.analysis.BivariateRealFunction {
    private static final double[][] AINV = {new double[]{1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d}, new double[]{0.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d}, new double[]{-3.0d, 3.0d, 0.0d, 0.0d, -2.0d, -1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d}, new double[]{2.0d, -2.0d, 0.0d, 0.0d, 1.0d, 1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d}, new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d}, new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 0.0d}, new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, -3.0d, 3.0d, 0.0d, 0.0d, -2.0d, -1.0d, 0.0d, 0.0d}, new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 2.0d, -2.0d, 0.0d, 0.0d, 1.0d, 1.0d, 0.0d, 0.0d}, new double[]{-3.0d, 0.0d, 3.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, -2.0d, 0.0d, -1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d}, new double[]{0.0d, 0.0d, 0.0d, 0.0d, -3.0d, 0.0d, 3.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, -2.0d, 0.0d, -1.0d, 0.0d}, new double[]{9.0d, -9.0d, -9.0d, 9.0d, 6.0d, 3.0d, -6.0d, -3.0d, 6.0d, -6.0d, 3.0d, -3.0d, 4.0d, 2.0d, 2.0d, 1.0d}, new double[]{-6.0d, 6.0d, 6.0d, -6.0d, -3.0d, -3.0d, 3.0d, 3.0d, -4.0d, 4.0d, -2.0d, 2.0d, -2.0d, -2.0d, -1.0d, -1.0d}, new double[]{2.0d, 0.0d, -2.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d}, new double[]{0.0d, 0.0d, 0.0d, 0.0d, 2.0d, 0.0d, -2.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 1.0d, 0.0d}, new double[]{-6.0d, 6.0d, 6.0d, -6.0d, -4.0d, -2.0d, 4.0d, 2.0d, -3.0d, 3.0d, -3.0d, 3.0d, -2.0d, -1.0d, -2.0d, -1.0d}, new double[]{4.0d, -4.0d, -4.0d, 4.0d, 2.0d, 2.0d, -2.0d, -2.0d, 2.0d, -2.0d, 2.0d, -2.0d, 1.0d, 1.0d, 1.0d, 1.0d}};
    private org.apache.commons.math.analysis.BivariateRealFunction[][][] partialDerivatives = null;
    private final org.apache.commons.math.analysis.interpolation.BicubicSplineFunction[][] splines;
    private final double[] xval;
    private final double[] yval;

    public BicubicSplineInterpolatingFunction(double[] dArr, double[] dArr2, double[][] dArr3, double[][] dArr4, double[][] dArr5, double[][] dArr6) throws org.apache.commons.math.DimensionMismatchException {
        char c = 2;
        int i = 0;
        char c2 = 1;
        int length = dArr.length;
        int length2 = dArr2.length;
        if (length == 0 || length2 == 0 || dArr3.length == 0 || dArr3[0].length == 0) {
            throw new org.apache.commons.math.exception.NoDataException();
        }
        if (length != dArr3.length) {
            throw new org.apache.commons.math.DimensionMismatchException(length, dArr3.length);
        }
        if (length != dArr4.length) {
            throw new org.apache.commons.math.DimensionMismatchException(length, dArr4.length);
        }
        if (length != dArr5.length) {
            throw new org.apache.commons.math.DimensionMismatchException(length, dArr5.length);
        }
        if (length != dArr6.length) {
            throw new org.apache.commons.math.DimensionMismatchException(length, dArr6.length);
        }
        org.apache.commons.math.util.MathUtils.checkOrder(dArr);
        org.apache.commons.math.util.MathUtils.checkOrder(dArr2);
        this.xval = (double[]) dArr.clone();
        this.yval = (double[]) dArr2.clone();
        int i2 = length - 1;
        int i3 = length2 - 1;
        this.splines = (org.apache.commons.math.analysis.interpolation.BicubicSplineFunction[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) org.apache.commons.math.analysis.interpolation.BicubicSplineFunction.class, i2, i3);
        int i4 = 0;
        while (i4 < i2) {
            if (dArr3[i4].length != length2) {
                throw new org.apache.commons.math.DimensionMismatchException(dArr3[i4].length, length2);
            }
            if (dArr4[i4].length != length2) {
                throw new org.apache.commons.math.DimensionMismatchException(dArr4[i4].length, length2);
            }
            if (dArr5[i4].length != length2) {
                throw new org.apache.commons.math.DimensionMismatchException(dArr5[i4].length, length2);
            }
            if (dArr6[i4].length != length2) {
                throw new org.apache.commons.math.DimensionMismatchException(dArr6[i4].length, length2);
            }
            int i5 = i4 + 1;
            int i6 = i;
            while (i6 < i3) {
                int i7 = i6 + 1;
                double d = dArr3[i4][i6];
                double d2 = dArr3[i5][i6];
                double d3 = dArr3[i4][i7];
                double d4 = dArr3[i5][i7];
                double d5 = dArr4[i4][i6];
                double d6 = dArr4[i5][i6];
                double d7 = dArr4[i4][i7];
                double d8 = dArr4[i5][i7];
                double d9 = dArr5[i4][i6];
                double d10 = dArr5[i5][i6];
                double d11 = dArr5[i4][i7];
                double d12 = dArr5[i5][i7];
                double d13 = dArr6[i4][i6];
                double d14 = dArr6[i5][i6];
                double d15 = dArr6[i4][i7];
                double d16 = dArr6[i5][i7];
                double[] dArr7 = new double[16];
                dArr7[i] = d;
                dArr7[c2] = d2;
                dArr7[2] = d3;
                dArr7[3] = d4;
                dArr7[4] = d5;
                dArr7[5] = d6;
                dArr7[6] = d7;
                dArr7[7] = d8;
                dArr7[8] = d9;
                dArr7[9] = d10;
                dArr7[10] = d11;
                dArr7[11] = d12;
                dArr7[12] = d13;
                dArr7[13] = d14;
                dArr7[14] = d15;
                dArr7[15] = d16;
                this.splines[i4][i6] = new org.apache.commons.math.analysis.interpolation.BicubicSplineFunction(computeSplineCoefficients(dArr7));
                i6 = i7;
                c = 2;
                i = 0;
                c2 = 1;
            }
            i4 = i5;
            i = 0;
            c2 = 1;
        }
    }

    @Override // org.apache.commons.math.analysis.BivariateRealFunction
    public double value(double d, double d2) {
        int searchIndex = searchIndex(d, this.xval);
        if (searchIndex == -1) {
            throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Double.valueOf(d), java.lang.Double.valueOf(this.xval[0]), java.lang.Double.valueOf(this.xval[this.xval.length - 1]));
        }
        int searchIndex2 = searchIndex(d2, this.yval);
        if (searchIndex2 == -1) {
            throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Double.valueOf(d2), java.lang.Double.valueOf(this.yval[0]), java.lang.Double.valueOf(this.yval[this.yval.length - 1]));
        }
        return this.splines[searchIndex][searchIndex2].value((d - this.xval[searchIndex]) / (this.xval[searchIndex + 1] - this.xval[searchIndex]), (d2 - this.yval[searchIndex2]) / (this.yval[searchIndex2 + 1] - this.yval[searchIndex2]));
    }

    public double partialDerivativeX(double d, double d2) {
        return partialDerivative(0, d, d2);
    }

    public double partialDerivativeY(double d, double d2) {
        return partialDerivative(1, d, d2);
    }

    public double partialDerivativeXX(double d, double d2) {
        return partialDerivative(2, d, d2);
    }

    public double partialDerivativeYY(double d, double d2) {
        return partialDerivative(3, d, d2);
    }

    public double partialDerivativeXY(double d, double d2) {
        return partialDerivative(4, d, d2);
    }

    private double partialDerivative(int i, double d, double d2) {
        if (this.partialDerivatives == null) {
            computePartialDerivatives();
        }
        int searchIndex = searchIndex(d, this.xval);
        if (searchIndex == -1) {
            throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Double.valueOf(d), java.lang.Double.valueOf(this.xval[0]), java.lang.Double.valueOf(this.xval[this.xval.length - 1]));
        }
        int searchIndex2 = searchIndex(d2, this.yval);
        if (searchIndex2 == -1) {
            throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Double.valueOf(d2), java.lang.Double.valueOf(this.yval[0]), java.lang.Double.valueOf(this.yval[this.yval.length - 1]));
        }
        try {
            return this.partialDerivatives[i][searchIndex][searchIndex2].value((d - this.xval[searchIndex]) / (this.xval[searchIndex + 1] - this.xval[searchIndex]), (d2 - this.yval[searchIndex2]) / (this.yval[searchIndex2 + 1] - this.yval[searchIndex2]));
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private void computePartialDerivatives() {
        int length = this.xval.length - 1;
        int length2 = this.yval.length - 1;
        this.partialDerivatives = (org.apache.commons.math.analysis.BivariateRealFunction[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) org.apache.commons.math.analysis.BivariateRealFunction.class, 5, length, length2);
        for (int i = 0; i < length; i++) {
            for (int i2 = 0; i2 < length2; i2++) {
                org.apache.commons.math.analysis.interpolation.BicubicSplineFunction bicubicSplineFunction = this.splines[i][i2];
                this.partialDerivatives[0][i][i2] = bicubicSplineFunction.partialDerivativeX();
                this.partialDerivatives[1][i][i2] = bicubicSplineFunction.partialDerivativeY();
                this.partialDerivatives[2][i][i2] = bicubicSplineFunction.partialDerivativeXX();
                this.partialDerivatives[3][i][i2] = bicubicSplineFunction.partialDerivativeYY();
                this.partialDerivatives[4][i][i2] = bicubicSplineFunction.partialDerivativeXY();
            }
        }
    }

    private int searchIndex(double d, double[] dArr) {
        if (d < dArr[0]) {
            return -1;
        }
        int length = dArr.length;
        for (int i = 1; i < length; i++) {
            if (d <= dArr[i]) {
                return i - 1;
            }
        }
        return -1;
    }

    private double[] computeSplineCoefficients(double[] dArr) {
        double[] dArr2 = new double[16];
        for (int i = 0; i < 16; i++) {
            double[] dArr3 = AINV[i];
            double d = 0.0d;
            for (int i2 = 0; i2 < 16; i2++) {
                d += dArr3[i2] * dArr[i2];
            }
            dArr2[i] = d;
        }
        return dArr2;
    }
}
