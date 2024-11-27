package org.apache.commons.math.analysis.polynomials;

/* loaded from: classes3.dex */
public class PolynomialFunctionLagrangeForm implements org.apache.commons.math.analysis.UnivariateRealFunction {
    private double[] coefficients;
    private boolean coefficientsComputed;
    private final double[] x;
    private final double[] y;

    public PolynomialFunctionLagrangeForm(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        verifyInterpolationArray(dArr, dArr2);
        this.x = new double[dArr.length];
        this.y = new double[dArr2.length];
        java.lang.System.arraycopy(dArr, 0, this.x, 0, dArr.length);
        java.lang.System.arraycopy(dArr2, 0, this.y, 0, dArr2.length);
        this.coefficientsComputed = false;
    }

    @Override // org.apache.commons.math.analysis.UnivariateRealFunction
    public double value(double d) throws org.apache.commons.math.FunctionEvaluationException {
        try {
            return evaluate(this.x, this.y, d);
        } catch (org.apache.commons.math.DuplicateSampleAbscissaException e) {
            throw new org.apache.commons.math.FunctionEvaluationException(d, e.getSpecificPattern(), e.getGeneralPattern(), e.getArguments());
        }
    }

    public int degree() {
        return this.x.length - 1;
    }

    public double[] getInterpolatingPoints() {
        double[] dArr = new double[this.x.length];
        java.lang.System.arraycopy(this.x, 0, dArr, 0, this.x.length);
        return dArr;
    }

    public double[] getInterpolatingValues() {
        double[] dArr = new double[this.y.length];
        java.lang.System.arraycopy(this.y, 0, dArr, 0, this.y.length);
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

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0072, code lost:
    
        if (r8 >= ((r10 + 1) * 0.5d)) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0074, code lost:
    
        r9 = r2[r8];
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x007d, code lost:
    
        r5 = r5 + r9;
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0078, code lost:
    
        r8 = r8 - 1;
        r9 = r3[r8];
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static double evaluate(double[] dArr, double[] dArr2, double d) throws org.apache.commons.math.DuplicateSampleAbscissaException, java.lang.IllegalArgumentException {
        verifyInterpolationArray(dArr, dArr2);
        int length = dArr.length;
        double[] dArr3 = new double[length];
        double[] dArr4 = new double[length];
        double d2 = Double.POSITIVE_INFINITY;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            dArr3[i2] = dArr2[i2];
            dArr4[i2] = dArr2[i2];
            double abs = org.apache.commons.math.util.FastMath.abs(d - dArr[i2]);
            if (abs < d2) {
                i = i2;
                d2 = abs;
            }
        }
        double d3 = dArr2[i];
        int i3 = 1;
        while (i3 < length) {
            int i4 = 0;
            while (true) {
                if (i4 >= length - i3) {
                    break;
                }
                double d4 = dArr[i4] - d;
                int i5 = i3 + i4;
                double d5 = dArr[i5] - d;
                double d6 = dArr[i4] - dArr[i5];
                if (d6 == 0.0d) {
                    throw new org.apache.commons.math.DuplicateSampleAbscissaException(dArr[i3], i3, i5);
                }
                int i6 = i4 + 1;
                double d7 = (dArr3[i6] - dArr4[i4]) / d6;
                dArr3[i4] = d4 * d7;
                dArr4[i4] = d5 * d7;
                i4 = i6;
            }
        }
        return d3;
    }

    protected void computeCoefficients() throws java.lang.ArithmeticException {
        int degree = degree() + 1;
        this.coefficients = new double[degree];
        int i = 0;
        for (int i2 = 0; i2 < degree; i2++) {
            this.coefficients[i2] = 0.0d;
        }
        double[] dArr = new double[degree + 1];
        dArr[0] = 1.0d;
        int i3 = 0;
        while (i3 < degree) {
            for (int i4 = i3; i4 > 0; i4--) {
                dArr[i4] = dArr[i4 - 1] - (dArr[i4] * this.x[i3]);
            }
            dArr[0] = dArr[0] * (-this.x[i3]);
            i3++;
            dArr[i3] = 1.0d;
        }
        double[] dArr2 = new double[degree];
        int i5 = 0;
        while (i5 < degree) {
            double d = 1.0d;
            for (int i6 = i; i6 < degree; i6++) {
                if (i5 != i6) {
                    d *= this.x[i5] - this.x[i6];
                }
            }
            if (d == 0.0d) {
                for (int i7 = 0; i7 < degree; i7++) {
                    if (i5 != i7 && this.x[i5] == this.x[i7]) {
                        throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.IDENTICAL_ABSCISSAS_DIVISION_BY_ZERO, java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i7), java.lang.Double.valueOf(this.x[i5]));
                    }
                }
            }
            double d2 = this.y[i5] / d;
            int i8 = degree - 1;
            dArr2[i8] = dArr[degree];
            double[] dArr3 = this.coefficients;
            dArr3[i8] = dArr3[i8] + (dArr2[i8] * d2);
            for (int i9 = degree - 2; i9 >= 0; i9--) {
                int i10 = i9 + 1;
                dArr2[i9] = dArr[i10] + (dArr2[i10] * this.x[i5]);
                double[] dArr4 = this.coefficients;
                dArr4[i9] = dArr4[i9] + (dArr2[i9] * d2);
            }
            i5++;
            i = 0;
        }
        this.coefficientsComputed = true;
    }

    public static void verifyInterpolationArray(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        if (dArr.length != dArr2.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr2.length));
        }
        if (dArr.length < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.WRONG_NUMBER_OF_POINTS, 2, java.lang.Integer.valueOf(dArr.length));
        }
    }
}
