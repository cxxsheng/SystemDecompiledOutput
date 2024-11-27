package org.apache.commons.math.analysis.interpolation;

/* loaded from: classes3.dex */
public class SmoothingPolynomialBicubicSplineInterpolator extends org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolator {
    private final org.apache.commons.math.optimization.fitting.PolynomialFitter xFitter;
    private final org.apache.commons.math.optimization.fitting.PolynomialFitter yFitter;

    public SmoothingPolynomialBicubicSplineInterpolator() {
        this(3);
    }

    public SmoothingPolynomialBicubicSplineInterpolator(int i) {
        this(i, i);
    }

    public SmoothingPolynomialBicubicSplineInterpolator(int i, int i2) {
        this.xFitter = new org.apache.commons.math.optimization.fitting.PolynomialFitter(i, new org.apache.commons.math.optimization.general.GaussNewtonOptimizer(false));
        this.yFitter = new org.apache.commons.math.optimization.fitting.PolynomialFitter(i2, new org.apache.commons.math.optimization.general.GaussNewtonOptimizer(false));
    }

    @Override // org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolator, org.apache.commons.math.analysis.interpolation.BivariateRealGridInterpolator
    public org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction interpolate(double[] dArr, double[] dArr2, double[][] dArr3) throws org.apache.commons.math.MathException {
        if (dArr.length == 0 || dArr2.length == 0 || dArr3.length == 0) {
            throw new org.apache.commons.math.exception.NoDataException();
        }
        if (dArr.length != dArr3.length) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(dArr.length, dArr3.length);
        }
        int length = dArr.length;
        int length2 = dArr2.length;
        for (int i = 0; i < length; i++) {
            if (dArr3[i].length != length2) {
                throw new org.apache.commons.math.exception.DimensionMismatchException(dArr3[i].length, length2);
            }
        }
        org.apache.commons.math.util.MathUtils.checkOrder(dArr);
        org.apache.commons.math.util.MathUtils.checkOrder(dArr2);
        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomialFunctionArr = new org.apache.commons.math.analysis.polynomials.PolynomialFunction[length2];
        for (int i2 = 0; i2 < length2; i2++) {
            this.xFitter.clearObservations();
            for (int i3 = 0; i3 < length; i3++) {
                this.xFitter.addObservedPoint(1.0d, dArr[i3], dArr3[i3][i2]);
            }
            polynomialFunctionArr[i2] = this.xFitter.fit();
        }
        double[][] dArr4 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2);
        for (int i4 = 0; i4 < length2; i4++) {
            org.apache.commons.math.analysis.polynomials.PolynomialFunction polynomialFunction = polynomialFunctionArr[i4];
            for (int i5 = 0; i5 < length; i5++) {
                dArr4[i5][i4] = polynomialFunction.value(dArr[i5]);
            }
        }
        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomialFunctionArr2 = new org.apache.commons.math.analysis.polynomials.PolynomialFunction[length];
        for (int i6 = 0; i6 < length; i6++) {
            this.yFitter.clearObservations();
            for (int i7 = 0; i7 < length2; i7++) {
                this.yFitter.addObservedPoint(1.0d, dArr2[i7], dArr4[i6][i7]);
            }
            polynomialFunctionArr2[i6] = this.yFitter.fit();
        }
        double[][] dArr5 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length2);
        for (int i8 = 0; i8 < length; i8++) {
            org.apache.commons.math.analysis.polynomials.PolynomialFunction polynomialFunction2 = polynomialFunctionArr2[i8];
            for (int i9 = 0; i9 < length2; i9++) {
                dArr5[i8][i9] = polynomialFunction2.value(dArr2[i9]);
            }
        }
        return super.interpolate(dArr, dArr2, dArr5);
    }
}
