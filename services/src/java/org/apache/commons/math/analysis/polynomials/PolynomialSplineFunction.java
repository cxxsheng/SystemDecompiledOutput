package org.apache.commons.math.analysis.polynomials;

/* loaded from: classes3.dex */
public class PolynomialSplineFunction implements org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction {
    private final double[] knots;
    private final int n;
    private final org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomials;

    public PolynomialSplineFunction(double[] dArr, org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomialFunctionArr) {
        if (dArr.length < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_ENOUGH_POINTS_IN_SPLINE_PARTITION, 2, java.lang.Integer.valueOf(dArr.length));
        }
        if (dArr.length - 1 != polynomialFunctionArr.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.POLYNOMIAL_INTERPOLANTS_MISMATCH_SEGMENTS, java.lang.Integer.valueOf(polynomialFunctionArr.length), java.lang.Integer.valueOf(dArr.length));
        }
        if (!isStrictlyIncreasing(dArr)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_STRICTLY_INCREASING_KNOT_VALUES, new java.lang.Object[0]);
        }
        this.n = dArr.length - 1;
        this.knots = new double[this.n + 1];
        java.lang.System.arraycopy(dArr, 0, this.knots, 0, this.n + 1);
        this.polynomials = new org.apache.commons.math.analysis.polynomials.PolynomialFunction[this.n];
        java.lang.System.arraycopy(polynomialFunctionArr, 0, this.polynomials, 0, this.n);
    }

    @Override // org.apache.commons.math.analysis.UnivariateRealFunction
    public double value(double d) throws org.apache.commons.math.ArgumentOutsideDomainException {
        if (d < this.knots[0] || d > this.knots[this.n]) {
            throw new org.apache.commons.math.ArgumentOutsideDomainException(d, this.knots[0], this.knots[this.n]);
        }
        int binarySearch = java.util.Arrays.binarySearch(this.knots, d);
        if (binarySearch < 0) {
            binarySearch = (-binarySearch) - 2;
        }
        if (binarySearch >= this.polynomials.length) {
            binarySearch--;
        }
        return this.polynomials[binarySearch].value(d - this.knots[binarySearch]);
    }

    @Override // org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction
    public org.apache.commons.math.analysis.UnivariateRealFunction derivative() {
        return polynomialSplineDerivative();
    }

    public org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction polynomialSplineDerivative() {
        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomialFunctionArr = new org.apache.commons.math.analysis.polynomials.PolynomialFunction[this.n];
        for (int i = 0; i < this.n; i++) {
            polynomialFunctionArr[i] = this.polynomials[i].polynomialDerivative();
        }
        return new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction(this.knots, polynomialFunctionArr);
    }

    public int getN() {
        return this.n;
    }

    public org.apache.commons.math.analysis.polynomials.PolynomialFunction[] getPolynomials() {
        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomialFunctionArr = new org.apache.commons.math.analysis.polynomials.PolynomialFunction[this.n];
        java.lang.System.arraycopy(this.polynomials, 0, polynomialFunctionArr, 0, this.n);
        return polynomialFunctionArr;
    }

    public double[] getKnots() {
        double[] dArr = new double[this.n + 1];
        java.lang.System.arraycopy(this.knots, 0, dArr, 0, this.n + 1);
        return dArr;
    }

    private static boolean isStrictlyIncreasing(double[] dArr) {
        for (int i = 1; i < dArr.length; i++) {
            if (dArr[i - 1] >= dArr[i]) {
                return false;
            }
        }
        return true;
    }
}
