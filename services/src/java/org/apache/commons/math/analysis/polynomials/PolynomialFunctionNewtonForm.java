package org.apache.commons.math.analysis.polynomials;

/* loaded from: classes3.dex */
public class PolynomialFunctionNewtonForm implements org.apache.commons.math.analysis.UnivariateRealFunction {
    private final double[] a;
    private final double[] c;
    private double[] coefficients;
    private boolean coefficientsComputed;

    public PolynomialFunctionNewtonForm(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        verifyInputArray(dArr, dArr2);
        this.a = new double[dArr.length];
        this.c = new double[dArr2.length];
        java.lang.System.arraycopy(dArr, 0, this.a, 0, dArr.length);
        java.lang.System.arraycopy(dArr2, 0, this.c, 0, dArr2.length);
        this.coefficientsComputed = false;
    }

    @Override // org.apache.commons.math.analysis.UnivariateRealFunction
    public double value(double d) throws org.apache.commons.math.FunctionEvaluationException {
        return evaluate(this.a, this.c, d);
    }

    public int degree() {
        return this.c.length;
    }

    public double[] getNewtonCoefficients() {
        double[] dArr = new double[this.a.length];
        java.lang.System.arraycopy(this.a, 0, dArr, 0, this.a.length);
        return dArr;
    }

    public double[] getCenters() {
        double[] dArr = new double[this.c.length];
        java.lang.System.arraycopy(this.c, 0, dArr, 0, this.c.length);
        return dArr;
    }

    public double[] getCoefficients() {
        if (!this.coefficientsComputed) {
            computeCoefficients();
        }
        double[] dArr = new double[this.coefficients.length];
        java.lang.System.arraycopy(this.coefficients, 0, dArr, 0, this.coefficients.length);
        return dArr;
    }

    public static double evaluate(double[] dArr, double[] dArr2, double d) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        verifyInputArray(dArr, dArr2);
        int length = dArr2.length;
        double d2 = dArr[length];
        for (int i = length - 1; i >= 0; i--) {
            d2 = dArr[i] + ((d - dArr2[i]) * d2);
        }
        return d2;
    }

    protected void computeCoefficients() {
        int degree = degree();
        this.coefficients = new double[degree + 1];
        for (int i = 0; i <= degree; i++) {
            this.coefficients[i] = 0.0d;
        }
        this.coefficients[0] = this.a[degree];
        for (int i2 = degree - 1; i2 >= 0; i2--) {
            for (int i3 = degree - i2; i3 > 0; i3--) {
                this.coefficients[i3] = this.coefficients[i3 - 1] - (this.c[i2] * this.coefficients[i3]);
            }
            this.coefficients[0] = this.a[i2] - (this.c[i2] * this.coefficients[0]);
        }
        this.coefficientsComputed = true;
    }

    protected static void verifyInputArray(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        if (dArr.length < 1 || dArr2.length < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY, new java.lang.Object[0]);
        }
        if (dArr.length != dArr2.length + 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.ARRAY_SIZES_SHOULD_HAVE_DIFFERENCE_1, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr2.length));
        }
    }
}
