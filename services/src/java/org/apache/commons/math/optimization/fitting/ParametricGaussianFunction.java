package org.apache.commons.math.optimization.fitting;

/* loaded from: classes3.dex */
public class ParametricGaussianFunction implements org.apache.commons.math.optimization.fitting.ParametricRealFunction, java.io.Serializable {
    private static final long serialVersionUID = -3875578602503903233L;

    @Override // org.apache.commons.math.optimization.fitting.ParametricRealFunction
    public double value(double d, double[] dArr) throws org.apache.commons.math.exception.ZeroException {
        validateParameters(dArr);
        double d2 = dArr[0];
        double d3 = dArr[1];
        double d4 = dArr[2];
        double d5 = dArr[3];
        double d6 = d - d4;
        return d2 + (d3 * java.lang.Math.exp(((-d6) * d6) / ((d5 * d5) * 2.0d)));
    }

    @Override // org.apache.commons.math.optimization.fitting.ParametricRealFunction
    public double[] gradient(double d, double[] dArr) throws org.apache.commons.math.exception.ZeroException {
        validateParameters(dArr);
        double d2 = dArr[1];
        double d3 = dArr[2];
        double d4 = dArr[3];
        double d5 = d - d3;
        double d6 = d4 * d4;
        double exp = java.lang.Math.exp(((-d5) * d5) / (2.0d * d6));
        double d7 = ((d2 * exp) * d5) / d6;
        return new double[]{1.0d, exp, d7, (d5 * d7) / d4};
    }

    private void validateParameters(double[] dArr) throws org.apache.commons.math.exception.ZeroException {
        if (dArr == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INPUT_ARRAY);
        }
        if (dArr.length != 4) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(4, dArr.length);
        }
        if (dArr[3] == 0.0d) {
            throw new org.apache.commons.math.exception.ZeroException();
        }
    }
}
