package org.apache.commons.math.analysis.interpolation;

/* loaded from: classes3.dex */
public class SplineInterpolator implements org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator {
    @Override // org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator
    public org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction interpolate(double[] dArr, double[] dArr2) {
        if (dArr.length != dArr2.length) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(dArr.length, dArr2.length);
        }
        if (dArr.length < 3) {
            throw new org.apache.commons.math.exception.NumberIsTooSmallException(org.apache.commons.math.exception.util.LocalizedFormats.NUMBER_OF_POINTS, java.lang.Integer.valueOf(dArr.length), 3, true);
        }
        int length = dArr.length - 1;
        org.apache.commons.math.util.MathUtils.checkOrder(dArr);
        double[] dArr3 = new double[length];
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            dArr3[i] = dArr[i2] - dArr[i];
            i = i2;
        }
        double[] dArr4 = new double[length];
        int i3 = length + 1;
        double[] dArr5 = new double[i3];
        dArr4[0] = 0.0d;
        dArr5[0] = 0.0d;
        int i4 = 1;
        while (i4 < length) {
            int i5 = i4 + 1;
            int i6 = i4 - 1;
            double d = ((dArr[i5] - dArr[i6]) * 2.0d) - (dArr3[i6] * dArr4[i6]);
            dArr4[i4] = dArr3[i4] / d;
            dArr5[i4] = ((((((dArr2[i5] * dArr3[i6]) - (dArr2[i4] * (dArr[i5] - dArr[i6]))) + (dArr2[i6] * dArr3[i4])) * 3.0d) / (dArr3[i6] * dArr3[i4])) - (dArr3[i6] * dArr5[i6])) / d;
            i4 = i5;
        }
        double[] dArr6 = new double[length];
        double[] dArr7 = new double[i3];
        double[] dArr8 = new double[length];
        dArr5[length] = 0.0d;
        dArr7[length] = 0.0d;
        for (int i7 = length - 1; i7 >= 0; i7--) {
            int i8 = i7 + 1;
            dArr7[i7] = dArr5[i7] - (dArr4[i7] * dArr7[i8]);
            dArr6[i7] = ((dArr2[i8] - dArr2[i7]) / dArr3[i7]) - ((dArr3[i7] * (dArr7[i8] + (dArr7[i7] * 2.0d))) / 3.0d);
            dArr8[i7] = (dArr7[i8] - dArr7[i7]) / (dArr3[i7] * 3.0d);
        }
        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomialFunctionArr = new org.apache.commons.math.analysis.polynomials.PolynomialFunction[length];
        for (int i9 = 0; i9 < length; i9++) {
            polynomialFunctionArr[i9] = new org.apache.commons.math.analysis.polynomials.PolynomialFunction(new double[]{dArr2[i9], dArr6[i9], dArr7[i9], dArr8[i9]});
        }
        return new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction(dArr, polynomialFunctionArr);
    }
}
