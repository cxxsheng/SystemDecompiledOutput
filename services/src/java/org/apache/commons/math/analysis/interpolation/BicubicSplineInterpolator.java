package org.apache.commons.math.analysis.interpolation;

/* loaded from: classes3.dex */
public class BicubicSplineInterpolator implements org.apache.commons.math.analysis.interpolation.BivariateRealGridInterpolator {
    @Override // org.apache.commons.math.analysis.interpolation.BivariateRealGridInterpolator
    public org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction interpolate(double[] dArr, double[] dArr2, double[][] dArr3) throws org.apache.commons.math.MathException, java.lang.IllegalArgumentException {
        if (dArr.length == 0 || dArr2.length == 0 || dArr3.length == 0) {
            throw new org.apache.commons.math.exception.NoDataException();
        }
        if (dArr.length != dArr3.length) {
            throw new org.apache.commons.math.DimensionMismatchException(dArr.length, dArr3.length);
        }
        org.apache.commons.math.util.MathUtils.checkOrder(dArr);
        org.apache.commons.math.util.MathUtils.checkOrder(dArr2);
        int length = dArr.length;
        int length2 = dArr2.length;
        int i = 0;
        double[][] dArr4 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length2, length);
        for (int i2 = 0; i2 < length; i2++) {
            if (dArr3[i2].length != length2) {
                throw new org.apache.commons.math.DimensionMismatchException(dArr3[i2].length, length2);
            }
            for (int i3 = 0; i3 < length2; i3++) {
                dArr4[i3][i2] = dArr3[i2][i3];
            }
        }
        org.apache.commons.math.analysis.interpolation.SplineInterpolator splineInterpolator = new org.apache.commons.math.analysis.interpolation.SplineInterpolator();
        org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction[] polynomialSplineFunctionArr = new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction[length2];
        for (int i4 = 0; i4 < length2; i4++) {
            polynomialSplineFunctionArr[i4] = splineInterpolator.interpolate(dArr, dArr4[i4]);
        }
        org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction[] polynomialSplineFunctionArr2 = new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction[length];
        for (int i5 = 0; i5 < length; i5++) {
            polynomialSplineFunctionArr2[i5] = splineInterpolator.interpolate(dArr2, dArr3[i5]);
        }
        double[][] dArr5 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2);
        int i6 = 0;
        while (i6 < length2) {
            org.apache.commons.math.analysis.UnivariateRealFunction derivative = polynomialSplineFunctionArr[i6].derivative();
            int i7 = i;
            while (i7 < length) {
                dArr5[i7][i6] = derivative.value(dArr[i7]);
                i7++;
                dArr5 = dArr5;
            }
            i6++;
            i = 0;
        }
        double[][] dArr6 = dArr5;
        double[][] dArr7 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2);
        for (int i8 = 0; i8 < length; i8++) {
            org.apache.commons.math.analysis.UnivariateRealFunction derivative2 = polynomialSplineFunctionArr2[i8].derivative();
            for (int i9 = 0; i9 < length2; i9++) {
                dArr7[i8][i9] = derivative2.value(dArr2[i9]);
            }
        }
        double[][] dArr8 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2);
        for (int i10 = 0; i10 < length; i10++) {
            int nextIndex = nextIndex(i10, length);
            int previousIndex = previousIndex(i10);
            for (int i11 = 0; i11 < length2; i11++) {
                int nextIndex2 = nextIndex(i11, length2);
                int previousIndex2 = previousIndex(i11);
                dArr8[i10][i11] = (((dArr3[nextIndex][nextIndex2] - dArr3[nextIndex][previousIndex2]) - dArr3[previousIndex][nextIndex2]) + dArr3[previousIndex][previousIndex2]) / ((dArr[nextIndex] - dArr[previousIndex]) * (dArr2[nextIndex2] - dArr2[previousIndex2]));
            }
        }
        return new org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction(dArr, dArr2, dArr3, dArr6, dArr7, dArr8);
    }

    private int nextIndex(int i, int i2) {
        int i3 = i + 1;
        return i3 < i2 ? i3 : i3 - 1;
    }

    private int previousIndex(int i) {
        int i2 = i - 1;
        if (i2 >= 0) {
            return i2;
        }
        return 0;
    }
}
