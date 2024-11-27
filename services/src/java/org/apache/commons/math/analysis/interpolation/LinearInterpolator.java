package org.apache.commons.math.analysis.interpolation;

/* loaded from: classes3.dex */
public class LinearInterpolator implements org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator {
    @Override // org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator
    public org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction interpolate(double[] dArr, double[] dArr2) {
        if (dArr.length != dArr2.length) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(dArr.length, dArr2.length);
        }
        if (dArr.length < 2) {
            throw new org.apache.commons.math.exception.NumberIsTooSmallException(org.apache.commons.math.exception.util.LocalizedFormats.NUMBER_OF_POINTS, java.lang.Integer.valueOf(dArr.length), 2, true);
        }
        int length = dArr.length - 1;
        org.apache.commons.math.util.MathUtils.checkOrder(dArr);
        double[] dArr3 = new double[length];
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            dArr3[i] = (dArr2[i2] - dArr2[i]) / (dArr[i2] - dArr[i]);
            i = i2;
        }
        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomialFunctionArr = new org.apache.commons.math.analysis.polynomials.PolynomialFunction[length];
        for (int i3 = 0; i3 < length; i3++) {
            polynomialFunctionArr[i3] = new org.apache.commons.math.analysis.polynomials.PolynomialFunction(new double[]{dArr2[i3], dArr3[i3]});
        }
        return new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction(dArr, polynomialFunctionArr);
    }
}
