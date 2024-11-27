package org.apache.commons.math.analysis.interpolation;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class SmoothingBicubicSplineInterpolator implements org.apache.commons.math.analysis.interpolation.BivariateRealGridInterpolator {
    @Override // org.apache.commons.math.analysis.interpolation.BivariateRealGridInterpolator
    public org.apache.commons.math.analysis.BivariateRealFunction interpolate(double[] dArr, double[] dArr2, double[][] dArr3) throws org.apache.commons.math.MathException, java.lang.IllegalArgumentException {
        int i = 0;
        if (dArr.length == 0 || dArr2.length == 0 || dArr3.length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NO_DATA, new java.lang.Object[0]);
        }
        if (dArr.length != dArr3.length) {
            throw new org.apache.commons.math.DimensionMismatchException(dArr.length, dArr3.length);
        }
        org.apache.commons.math.util.MathUtils.checkOrder(dArr, org.apache.commons.math.util.MathUtils.OrderDirection.INCREASING, true);
        org.apache.commons.math.util.MathUtils.checkOrder(dArr2, org.apache.commons.math.util.MathUtils.OrderDirection.INCREASING, true);
        int length = dArr.length;
        int length2 = dArr2.length;
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
        double[][] dArr5 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2);
        int i5 = 0;
        while (i5 < length2) {
            org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction polynomialSplineFunction = polynomialSplineFunctionArr[i5];
            for (int i6 = i; i6 < length; i6++) {
                dArr5[i6][i5] = polynomialSplineFunction.value(dArr[i6]);
            }
            i5++;
            i = 0;
        }
        org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction[] polynomialSplineFunctionArr2 = new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction[length];
        for (int i7 = 0; i7 < length; i7++) {
            polynomialSplineFunctionArr2[i7] = splineInterpolator.interpolate(dArr2, dArr5[i7]);
        }
        double[][] dArr6 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2);
        for (int i8 = 0; i8 < length; i8++) {
            org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction polynomialSplineFunction2 = polynomialSplineFunctionArr2[i8];
            for (int i9 = 0; i9 < length2; i9++) {
                dArr6[i8][i9] = polynomialSplineFunction2.value(dArr2[i9]);
            }
        }
        double[][] dArr7 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2);
        for (int i10 = 0; i10 < length2; i10++) {
            org.apache.commons.math.analysis.UnivariateRealFunction derivative = polynomialSplineFunctionArr[i10].derivative();
            for (int i11 = 0; i11 < length; i11++) {
                dArr7[i11][i10] = derivative.value(dArr[i11]);
            }
        }
        double[][] dArr8 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2);
        for (int i12 = 0; i12 < length; i12++) {
            org.apache.commons.math.analysis.UnivariateRealFunction derivative2 = polynomialSplineFunctionArr2[i12].derivative();
            int i13 = 0;
            while (i13 < length2) {
                dArr8[i12][i13] = derivative2.value(dArr2[i13]);
                i13++;
                dArr8 = dArr8;
            }
        }
        double[][] dArr9 = dArr8;
        double[][] dArr10 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2);
        for (int i14 = 0; i14 < length; i14++) {
            int nextIndex = nextIndex(i14, length);
            int previousIndex = previousIndex(i14);
            for (int i15 = 0; i15 < length2; i15++) {
                int nextIndex2 = nextIndex(i15, length2);
                int previousIndex2 = previousIndex(i15);
                dArr10[i14][i15] = (((dArr6[nextIndex][nextIndex2] - dArr6[nextIndex][previousIndex2]) - dArr6[previousIndex][nextIndex2]) + dArr6[previousIndex][previousIndex2]) / ((dArr[nextIndex] - dArr[previousIndex]) * (dArr2[nextIndex2] - dArr2[previousIndex2]));
            }
        }
        return new org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction(dArr, dArr2, dArr6, dArr7, dArr9, dArr10);
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
