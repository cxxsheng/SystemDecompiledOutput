package org.apache.commons.math.analysis.interpolation;

/* compiled from: BicubicSplineInterpolatingFunction.java */
/* loaded from: classes3.dex */
class BicubicSplineFunction implements org.apache.commons.math.analysis.BivariateRealFunction {
    private static final short N = 4;
    private final double[][] a = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, 4, 4);
    private org.apache.commons.math.analysis.BivariateRealFunction partialDerivativeX;
    private org.apache.commons.math.analysis.BivariateRealFunction partialDerivativeXX;
    private org.apache.commons.math.analysis.BivariateRealFunction partialDerivativeXY;
    private org.apache.commons.math.analysis.BivariateRealFunction partialDerivativeY;
    private org.apache.commons.math.analysis.BivariateRealFunction partialDerivativeYY;

    public BicubicSplineFunction(double[] dArr) {
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                this.a[i][i2] = dArr[(i2 * 4) + i];
            }
        }
    }

    @Override // org.apache.commons.math.analysis.BivariateRealFunction
    public double value(double d, double d2) {
        if (d < 0.0d || d > 1.0d) {
            throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Double.valueOf(d), 0, 1);
        }
        if (d2 < 0.0d || d2 > 1.0d) {
            throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Double.valueOf(d2), 0, 1);
        }
        double d3 = d * d;
        double[] dArr = {1.0d, d, d3, d3 * d};
        double d4 = d2 * d2;
        return apply(dArr, new double[]{1.0d, d2, d4, d4 * d2}, this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double apply(double[] dArr, double[] dArr2, double[][] dArr3) {
        double d = 0.0d;
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                d += dArr3[i][i2] * dArr[i] * dArr2[i2];
            }
        }
        return d;
    }

    public org.apache.commons.math.analysis.BivariateRealFunction partialDerivativeX() {
        if (this.partialDerivativeX == null) {
            computePartialDerivatives();
        }
        return this.partialDerivativeX;
    }

    public org.apache.commons.math.analysis.BivariateRealFunction partialDerivativeY() {
        if (this.partialDerivativeY == null) {
            computePartialDerivatives();
        }
        return this.partialDerivativeY;
    }

    public org.apache.commons.math.analysis.BivariateRealFunction partialDerivativeXX() {
        if (this.partialDerivativeXX == null) {
            computePartialDerivatives();
        }
        return this.partialDerivativeXX;
    }

    public org.apache.commons.math.analysis.BivariateRealFunction partialDerivativeYY() {
        if (this.partialDerivativeYY == null) {
            computePartialDerivatives();
        }
        return this.partialDerivativeYY;
    }

    public org.apache.commons.math.analysis.BivariateRealFunction partialDerivativeXY() {
        if (this.partialDerivativeXY == null) {
            computePartialDerivatives();
        }
        return this.partialDerivativeXY;
    }

    private void computePartialDerivatives() {
        final double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, 4, 4);
        final double[][] dArr2 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, 4, 4);
        final double[][] dArr3 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, 4, 4);
        final double[][] dArr4 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, 4, 4);
        final double[][] dArr5 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, 4, 4);
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                double d = this.a[i][i2];
                dArr[i][i2] = i * d;
                double d2 = i2;
                dArr2[i][i2] = d * d2;
                dArr3[i][i2] = (i - 1) * dArr[i][i2];
                dArr4[i][i2] = (i2 - 1) * dArr2[i][i2];
                dArr5[i][i2] = d2 * dArr[i][i2];
            }
        }
        this.partialDerivativeX = new org.apache.commons.math.analysis.BivariateRealFunction() { // from class: org.apache.commons.math.analysis.interpolation.BicubicSplineFunction.1
            @Override // org.apache.commons.math.analysis.BivariateRealFunction
            public double value(double d3, double d4) {
                double d5 = d4 * d4;
                return org.apache.commons.math.analysis.interpolation.BicubicSplineFunction.this.apply(new double[]{0.0d, 1.0d, d3, d3 * d3}, new double[]{1.0d, d4, d5, d5 * d4}, dArr);
            }
        };
        this.partialDerivativeY = new org.apache.commons.math.analysis.BivariateRealFunction() { // from class: org.apache.commons.math.analysis.interpolation.BicubicSplineFunction.2
            @Override // org.apache.commons.math.analysis.BivariateRealFunction
            public double value(double d3, double d4) {
                double d5 = d3 * d3;
                return org.apache.commons.math.analysis.interpolation.BicubicSplineFunction.this.apply(new double[]{1.0d, d3, d5, d5 * d3}, new double[]{0.0d, 1.0d, d4, d4 * d4}, dArr2);
            }
        };
        this.partialDerivativeXX = new org.apache.commons.math.analysis.BivariateRealFunction() { // from class: org.apache.commons.math.analysis.interpolation.BicubicSplineFunction.3
            @Override // org.apache.commons.math.analysis.BivariateRealFunction
            public double value(double d3, double d4) {
                double[] dArr6 = {0.0d, 0.0d, 1.0d, d3};
                double d5 = d4 * d4;
                return org.apache.commons.math.analysis.interpolation.BicubicSplineFunction.this.apply(dArr6, new double[]{1.0d, d4, d5, d5 * d4}, dArr3);
            }
        };
        this.partialDerivativeYY = new org.apache.commons.math.analysis.BivariateRealFunction() { // from class: org.apache.commons.math.analysis.interpolation.BicubicSplineFunction.4
            @Override // org.apache.commons.math.analysis.BivariateRealFunction
            public double value(double d3, double d4) {
                double d5 = d3 * d3;
                return org.apache.commons.math.analysis.interpolation.BicubicSplineFunction.this.apply(new double[]{1.0d, d3, d5, d5 * d3}, new double[]{0.0d, 0.0d, 1.0d, d4}, dArr4);
            }
        };
        this.partialDerivativeXY = new org.apache.commons.math.analysis.BivariateRealFunction() { // from class: org.apache.commons.math.analysis.interpolation.BicubicSplineFunction.5
            @Override // org.apache.commons.math.analysis.BivariateRealFunction
            public double value(double d3, double d4) {
                return org.apache.commons.math.analysis.interpolation.BicubicSplineFunction.this.apply(new double[]{0.0d, 1.0d, d3, d3 * d3}, new double[]{0.0d, 1.0d, d4, d4 * d4}, dArr5);
            }
        };
    }
}
