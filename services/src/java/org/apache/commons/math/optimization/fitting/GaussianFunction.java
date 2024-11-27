package org.apache.commons.math.optimization.fitting;

/* loaded from: classes3.dex */
public class GaussianFunction implements org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction, java.io.Serializable {
    private static final long serialVersionUID = -3195385616125629512L;
    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public GaussianFunction(double d, double d2, double d3, double d4) {
        if (d4 == 0.0d) {
            throw new org.apache.commons.math.exception.ZeroException();
        }
        this.a = d;
        this.b = d2;
        this.c = d3;
        this.d = d4;
    }

    public GaussianFunction(double[] dArr) {
        if (dArr == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INPUT_ARRAY);
        }
        if (dArr.length != 4) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(4, dArr.length);
        }
        if (dArr[3] == 0.0d) {
            throw new org.apache.commons.math.exception.ZeroException();
        }
        this.a = dArr[0];
        this.b = dArr[1];
        this.c = dArr[2];
        this.d = dArr[3];
    }

    @Override // org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction
    public org.apache.commons.math.analysis.UnivariateRealFunction derivative() {
        return new org.apache.commons.math.optimization.fitting.GaussianDerivativeFunction(this.b, this.c, this.d);
    }

    @Override // org.apache.commons.math.analysis.UnivariateRealFunction
    public double value(double d) {
        double d2 = d - this.c;
        return this.a + (this.b * java.lang.Math.exp(((-d2) * d2) / ((this.d * this.d) * 2.0d)));
    }

    public double getA() {
        return this.a;
    }

    public double getB() {
        return this.b;
    }

    public double getC() {
        return this.c;
    }

    public double getD() {
        return this.d;
    }
}
