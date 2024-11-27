package org.apache.commons.math.analysis.interpolation;

/* compiled from: TricubicSplineInterpolatingFunction.java */
/* loaded from: classes3.dex */
class TricubicSplineFunction implements org.apache.commons.math.analysis.TrivariateRealFunction {
    private static final short N = 4;
    private final double[][][] a = (double[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, 4, 4, 4);

    public TricubicSplineFunction(double[] dArr) {
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                for (int i3 = 0; i3 < 4; i3++) {
                    this.a[i][i2][i3] = dArr[(((i3 * 4) + i2) * 4) + i];
                }
            }
        }
    }

    @Override // org.apache.commons.math.analysis.TrivariateRealFunction
    public double value(double d, double d2, double d3) {
        double d4 = 0.0d;
        if (d < 0.0d || d > 1.0d) {
            throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Double.valueOf(d), 0, 1);
        }
        if (d2 < 0.0d || d2 > 1.0d) {
            throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Double.valueOf(d2), 0, 1);
        }
        if (d3 < 0.0d || d3 > 1.0d) {
            throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Double.valueOf(d3), 0, 1);
        }
        double d5 = d * d;
        double[] dArr = {1.0d, d, d5, d5 * d};
        double d6 = d2 * d2;
        double[] dArr2 = {1.0d, d2, d6, d6 * d2};
        double d7 = d3 * d3;
        double[] dArr3 = {1.0d, d3, d7, d7 * d3};
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                for (int i3 = 0; i3 < 4; i3++) {
                    d4 += this.a[i][i2][i3] * dArr[i] * dArr2[i2] * dArr3[i3];
                }
            }
        }
        return d4;
    }
}
