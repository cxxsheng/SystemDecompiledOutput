package org.apache.commons.math.analysis.polynomials;

/* loaded from: classes3.dex */
public class PolynomialFunction implements org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction, java.io.Serializable {
    private static final long serialVersionUID = -7726511984200295583L;
    private final double[] coefficients;

    public PolynomialFunction(double[] dArr) {
        int length = dArr.length;
        if (length == 0) {
            throw new org.apache.commons.math.exception.NoDataException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
        }
        while (length > 1 && dArr[length - 1] == 0.0d) {
            length--;
        }
        this.coefficients = new double[length];
        java.lang.System.arraycopy(dArr, 0, this.coefficients, 0, length);
    }

    @Override // org.apache.commons.math.analysis.UnivariateRealFunction
    public double value(double d) {
        return evaluate(this.coefficients, d);
    }

    public int degree() {
        return this.coefficients.length - 1;
    }

    public double[] getCoefficients() {
        return (double[]) this.coefficients.clone();
    }

    protected static double evaluate(double[] dArr, double d) {
        int length = dArr.length;
        if (length == 0) {
            throw new org.apache.commons.math.exception.NoDataException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
        }
        double d2 = dArr[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            d2 = (d2 * d) + dArr[i];
        }
        return d2;
    }

    public org.apache.commons.math.analysis.polynomials.PolynomialFunction add(org.apache.commons.math.analysis.polynomials.PolynomialFunction polynomialFunction) {
        int min = org.apache.commons.math.util.FastMath.min(this.coefficients.length, polynomialFunction.coefficients.length);
        int max = org.apache.commons.math.util.FastMath.max(this.coefficients.length, polynomialFunction.coefficients.length);
        double[] dArr = new double[max];
        for (int i = 0; i < min; i++) {
            dArr[i] = this.coefficients[i] + polynomialFunction.coefficients[i];
        }
        java.lang.System.arraycopy(this.coefficients.length < polynomialFunction.coefficients.length ? polynomialFunction.coefficients : this.coefficients, min, dArr, min, max - min);
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(dArr);
    }

    public org.apache.commons.math.analysis.polynomials.PolynomialFunction subtract(org.apache.commons.math.analysis.polynomials.PolynomialFunction polynomialFunction) {
        int min = org.apache.commons.math.util.FastMath.min(this.coefficients.length, polynomialFunction.coefficients.length);
        int max = org.apache.commons.math.util.FastMath.max(this.coefficients.length, polynomialFunction.coefficients.length);
        double[] dArr = new double[max];
        for (int i = 0; i < min; i++) {
            dArr[i] = this.coefficients[i] - polynomialFunction.coefficients[i];
        }
        if (this.coefficients.length < polynomialFunction.coefficients.length) {
            while (min < max) {
                dArr[min] = -polynomialFunction.coefficients[min];
                min++;
            }
        } else {
            java.lang.System.arraycopy(this.coefficients, min, dArr, min, max - min);
        }
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(dArr);
    }

    public org.apache.commons.math.analysis.polynomials.PolynomialFunction negate() {
        double[] dArr = new double[this.coefficients.length];
        for (int i = 0; i < this.coefficients.length; i++) {
            dArr[i] = -this.coefficients[i];
        }
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(dArr);
    }

    public org.apache.commons.math.analysis.polynomials.PolynomialFunction multiply(org.apache.commons.math.analysis.polynomials.PolynomialFunction polynomialFunction) {
        int length = (this.coefficients.length + polynomialFunction.coefficients.length) - 1;
        double[] dArr = new double[length];
        int i = 0;
        while (i < length) {
            dArr[i] = 0.0d;
            int i2 = i + 1;
            for (int max = org.apache.commons.math.util.FastMath.max(0, i2 - polynomialFunction.coefficients.length); max < org.apache.commons.math.util.FastMath.min(this.coefficients.length, i2); max++) {
                dArr[i] = dArr[i] + (this.coefficients[max] * polynomialFunction.coefficients[i - max]);
            }
            i = i2;
        }
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(dArr);
    }

    protected static double[] differentiate(double[] dArr) {
        int length = dArr.length;
        if (length == 0) {
            throw new org.apache.commons.math.exception.NoDataException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
        }
        if (length == 1) {
            return new double[]{0.0d};
        }
        int i = length - 1;
        double[] dArr2 = new double[i];
        while (i > 0) {
            dArr2[i - 1] = i * dArr[i];
            i--;
        }
        return dArr2;
    }

    public org.apache.commons.math.analysis.polynomials.PolynomialFunction polynomialDerivative() {
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(differentiate(this.coefficients));
    }

    @Override // org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction
    public org.apache.commons.math.analysis.UnivariateRealFunction derivative() {
        return polynomialDerivative();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (this.coefficients[0] == 0.0d) {
            if (this.coefficients.length == 1) {
                return "0";
            }
        } else {
            sb.append(java.lang.Double.toString(this.coefficients[0]));
        }
        for (int i = 1; i < this.coefficients.length; i++) {
            if (this.coefficients[i] != 0.0d) {
                if (sb.length() > 0) {
                    if (this.coefficients[i] < 0.0d) {
                        sb.append(" - ");
                    } else {
                        sb.append(" + ");
                    }
                } else if (this.coefficients[i] < 0.0d) {
                    sb.append("-");
                }
                double abs = org.apache.commons.math.util.FastMath.abs(this.coefficients[i]);
                if (abs - 1.0d != 0.0d) {
                    sb.append(java.lang.Double.toString(abs));
                    sb.append(' ');
                }
                sb.append("x");
                if (i > 1) {
                    sb.append('^');
                    sb.append(java.lang.Integer.toString(i));
                }
            }
        }
        return sb.toString();
    }

    public int hashCode() {
        return 31 + java.util.Arrays.hashCode(this.coefficients);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof org.apache.commons.math.analysis.polynomials.PolynomialFunction) && java.util.Arrays.equals(this.coefficients, ((org.apache.commons.math.analysis.polynomials.PolynomialFunction) obj).coefficients);
    }
}
