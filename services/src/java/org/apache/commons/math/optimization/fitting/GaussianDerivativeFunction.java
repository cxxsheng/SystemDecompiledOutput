package org.apache.commons.math.optimization.fitting;

/* loaded from: classes3.dex */
public class GaussianDerivativeFunction implements org.apache.commons.math.analysis.UnivariateRealFunction, java.io.Serializable {
    private static final long serialVersionUID = -6500229089670174766L;
    private final double b;
    private final double c;
    private final double d2;

    public GaussianDerivativeFunction(double d, double d2, double d3) {
        if (d3 == 0.0d) {
            throw new org.apache.commons.math.exception.ZeroException();
        }
        this.b = d;
        this.c = d2;
        this.d2 = d3 * d3;
    }

    public GaussianDerivativeFunction(double[] dArr) {
        if (dArr == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INPUT_ARRAY);
        }
        if (dArr.length != 3) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(3, dArr.length);
        }
        if (dArr[2] == 0.0d) {
            throw new org.apache.commons.math.exception.ZeroException();
        }
        this.b = dArr[0];
        this.c = dArr[1];
        this.d2 = dArr[2] * dArr[2];
    }

    @Override // org.apache.commons.math.analysis.UnivariateRealFunction
    public double value(double d) {
        double d2 = d - this.c;
        return ((-this.b) / this.d2) * d2 * java.lang.Math.exp((-(d2 * d2)) / (this.d2 * 2.0d));
    }
}
