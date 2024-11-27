package org.apache.commons.math.analysis.interpolation;

/* loaded from: classes3.dex */
public class TricubicSplineInterpolator implements org.apache.commons.math.analysis.interpolation.TrivariateRealGridInterpolator {
    @Override // org.apache.commons.math.analysis.interpolation.TrivariateRealGridInterpolator
    public org.apache.commons.math.analysis.interpolation.TricubicSplineInterpolatingFunction interpolate(double[] dArr, double[] dArr2, double[] dArr3, double[][][] dArr4) throws org.apache.commons.math.MathException {
        int i;
        int i2;
        double[] dArr5 = dArr2;
        if (dArr.length == 0 || dArr5.length == 0 || dArr3.length == 0 || dArr4.length == 0) {
            throw new org.apache.commons.math.exception.NoDataException();
        }
        if (dArr.length != dArr4.length) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(dArr.length, dArr4.length);
        }
        org.apache.commons.math.util.MathUtils.checkOrder(dArr);
        org.apache.commons.math.util.MathUtils.checkOrder(dArr2);
        org.apache.commons.math.util.MathUtils.checkOrder(dArr3);
        int length = dArr.length;
        int length2 = dArr5.length;
        int length3 = dArr3.length;
        int i3 = 0;
        double[][][] dArr6 = (double[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length3, length, length2);
        double[][][] dArr7 = (double[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length2, length3, length);
        int i4 = 0;
        while (i4 < length) {
            if (dArr4[i4].length != length2) {
                throw new org.apache.commons.math.exception.DimensionMismatchException(dArr4[i4].length, length2);
            }
            for (int i5 = i3; i5 < length2; i5++) {
                if (dArr4[i4][i5].length != length3) {
                    throw new org.apache.commons.math.exception.DimensionMismatchException(dArr4[i4][i5].length, length3);
                }
                for (int i6 = 0; i6 < length3; i6++) {
                    double d = dArr4[i4][i5][i6];
                    dArr6[i6][i4][i5] = d;
                    dArr7[i5][i6][i4] = d;
                }
            }
            i4++;
            i3 = 0;
        }
        org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolator bicubicSplineInterpolator = new org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolator();
        org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction[] bicubicSplineInterpolatingFunctionArr = new org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction[length];
        for (int i7 = 0; i7 < length; i7++) {
            bicubicSplineInterpolatingFunctionArr[i7] = bicubicSplineInterpolator.interpolate(dArr5, dArr3, dArr4[i7]);
        }
        org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction[] bicubicSplineInterpolatingFunctionArr2 = new org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction[length2];
        for (int i8 = 0; i8 < length2; i8++) {
            bicubicSplineInterpolatingFunctionArr2[i8] = bicubicSplineInterpolator.interpolate(dArr3, dArr, dArr7[i8]);
        }
        org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction[] bicubicSplineInterpolatingFunctionArr3 = new org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction[length3];
        for (int i9 = 0; i9 < length3; i9++) {
            bicubicSplineInterpolatingFunctionArr3[i9] = bicubicSplineInterpolator.interpolate(dArr, dArr5, dArr6[i9]);
        }
        double[][][] dArr8 = (double[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2, length3);
        double[][][] dArr9 = (double[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2, length3);
        double[][][] dArr10 = (double[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2, length3);
        int i10 = 0;
        while (i10 < length3) {
            org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction bicubicSplineInterpolatingFunction = bicubicSplineInterpolatingFunctionArr3[i10];
            org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction[] bicubicSplineInterpolatingFunctionArr4 = bicubicSplineInterpolatingFunctionArr3;
            for (int i11 = 0; i11 < length; i11++) {
                double d2 = dArr[i11];
                int i12 = 0;
                while (i12 < length2) {
                    int i13 = length;
                    double d3 = dArr5[i12];
                    dArr8[i11][i12][i10] = bicubicSplineInterpolatingFunction.partialDerivativeX(d2, d3);
                    dArr9[i11][i12][i10] = bicubicSplineInterpolatingFunction.partialDerivativeY(d2, d3);
                    dArr10[i11][i12][i10] = bicubicSplineInterpolatingFunction.partialDerivativeXY(d2, d3);
                    i12++;
                    length2 = length2;
                    length = i13;
                }
            }
            i10++;
            bicubicSplineInterpolatingFunctionArr3 = bicubicSplineInterpolatingFunctionArr4;
        }
        int i14 = length;
        int i15 = length2;
        double[][][] dArr11 = (double[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i14, i15, length3);
        double[][][] dArr12 = (double[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i14, i15, length3);
        int i16 = 0;
        while (true) {
            i = i14;
            if (i16 >= i) {
                break;
            }
            org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction bicubicSplineInterpolatingFunction2 = bicubicSplineInterpolatingFunctionArr[i16];
            int i17 = 0;
            while (true) {
                i2 = i15;
                if (i17 < i2) {
                    org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction[] bicubicSplineInterpolatingFunctionArr5 = bicubicSplineInterpolatingFunctionArr;
                    double d4 = dArr5[i17];
                    double[][][] dArr13 = dArr10;
                    for (int i18 = 0; i18 < length3; i18++) {
                        double d5 = dArr3[i18];
                        dArr11[i16][i17][i18] = bicubicSplineInterpolatingFunction2.partialDerivativeY(d4, d5);
                        dArr12[i16][i17][i18] = bicubicSplineInterpolatingFunction2.partialDerivativeXY(d4, d5);
                    }
                    i17++;
                    dArr5 = dArr2;
                    bicubicSplineInterpolatingFunctionArr = bicubicSplineInterpolatingFunctionArr5;
                    dArr10 = dArr13;
                    i15 = i2;
                }
            }
            i16++;
            dArr5 = dArr2;
            i14 = i;
            i15 = i2;
        }
        int i19 = i15;
        double[][][] dArr14 = dArr10;
        double[][][] dArr15 = (double[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i19, length3);
        for (int i20 = 0; i20 < i19; i20++) {
            org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction bicubicSplineInterpolatingFunction3 = bicubicSplineInterpolatingFunctionArr2[i20];
            for (int i21 = 0; i21 < length3; i21++) {
                double d6 = dArr3[i21];
                int i22 = 0;
                while (i22 < i) {
                    dArr15[i22][i20][i21] = bicubicSplineInterpolatingFunction3.partialDerivativeXY(d6, dArr[i22]);
                    i22++;
                    bicubicSplineInterpolatingFunctionArr2 = bicubicSplineInterpolatingFunctionArr2;
                    dArr12 = dArr12;
                }
            }
        }
        double[][][] dArr16 = dArr12;
        int i23 = 0;
        double[][][] dArr17 = (double[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i19, length3);
        int i24 = 0;
        while (i24 < i) {
            int nextIndex = nextIndex(i24, i);
            int previousIndex = previousIndex(i24);
            int i25 = i23;
            while (i25 < i19) {
                int nextIndex2 = nextIndex(i25, i19);
                int previousIndex2 = previousIndex(i25);
                while (i23 < length3) {
                    int nextIndex3 = nextIndex(i23, length3);
                    int previousIndex3 = previousIndex(i23);
                    dArr17[i24][i25][i23] = (((((((dArr4[nextIndex][nextIndex2][nextIndex3] - dArr4[nextIndex][previousIndex2][nextIndex3]) - dArr4[previousIndex][nextIndex2][nextIndex3]) + dArr4[previousIndex][previousIndex2][nextIndex3]) - dArr4[nextIndex][nextIndex2][previousIndex3]) + dArr4[nextIndex][previousIndex2][previousIndex3]) + dArr4[previousIndex][nextIndex2][previousIndex3]) - dArr4[previousIndex][previousIndex2][previousIndex3]) / (((dArr[nextIndex] - dArr[previousIndex]) * (dArr2[nextIndex2] - dArr2[previousIndex2])) * (dArr3[nextIndex3] - dArr3[previousIndex3]));
                    i23++;
                    i19 = i19;
                }
                i25++;
                i19 = i19;
                i23 = 0;
            }
            i24++;
            i19 = i19;
            i23 = 0;
        }
        return new org.apache.commons.math.analysis.interpolation.TricubicSplineInterpolatingFunction(dArr, dArr2, dArr3, dArr4, dArr8, dArr9, dArr11, dArr14, dArr15, dArr16, dArr17);
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
