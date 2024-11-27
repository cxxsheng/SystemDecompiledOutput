package org.apache.commons.math.util;

/* loaded from: classes3.dex */
public abstract class ContinuedFraction {
    private static final double DEFAULT_EPSILON = 1.0E-8d;

    protected abstract double getA(int i, double d);

    protected abstract double getB(int i, double d);

    protected ContinuedFraction() {
    }

    public double evaluate(double d) throws org.apache.commons.math.MathException {
        return evaluate(d, DEFAULT_EPSILON, Integer.MAX_VALUE);
    }

    public double evaluate(double d, double d2) throws org.apache.commons.math.MathException {
        return evaluate(d, d2, Integer.MAX_VALUE);
    }

    public double evaluate(double d, int i) throws org.apache.commons.math.MathException {
        return evaluate(d, DEFAULT_EPSILON, i);
    }

    public double evaluate(double d, double d2, int i) throws org.apache.commons.math.MathException {
        int i2;
        double d3;
        int i3 = 0;
        double a = getA(0, d);
        double d4 = a / 1.0d;
        double d5 = 0.0d;
        double d6 = Double.MAX_VALUE;
        int i4 = 0;
        double d7 = 1.0d;
        double d8 = 1.0d;
        double d9 = 0.0d;
        while (i4 < i && d6 > d2) {
            i4++;
            double a2 = getA(i4, d);
            double b = getB(i4, d);
            double d10 = (a2 * a) + (b * d7);
            double d11 = (a2 * d8) + (b * d9);
            if (java.lang.Double.isInfinite(d10) || java.lang.Double.isInfinite(d11)) {
                double max = org.apache.commons.math.util.FastMath.max(a2, b);
                if (max <= d5) {
                    throw new org.apache.commons.math.ConvergenceException(org.apache.commons.math.exception.util.LocalizedFormats.CONTINUED_FRACTION_INFINITY_DIVERGENCE, java.lang.Double.valueOf(d));
                }
                i2 = 1;
                double d12 = 1.0d;
                while (true) {
                    if (i3 >= 5) {
                        d3 = 0.0d;
                        break;
                    }
                    double d13 = d12 * max;
                    if (a2 != 0.0d && a2 > b) {
                        double d14 = b / d13;
                        d10 = (a / d12) + (d14 * d7);
                        d11 = (d8 / d12) + (d14 * d9);
                        d3 = 0.0d;
                    } else {
                        d3 = 0.0d;
                        if (b != 0.0d) {
                            double d15 = a2 / d13;
                            double d16 = (d15 * a) + (d7 / d12);
                            double d17 = (d15 * d8) + (d9 / d12);
                            d10 = d16;
                            d11 = d17;
                        }
                    }
                    i2 = (java.lang.Double.isInfinite(d10) || java.lang.Double.isInfinite(d11)) ? 1 : 0;
                    if (i2 == 0) {
                        break;
                    }
                    i3++;
                    d12 = d13;
                }
            } else {
                i2 = i3;
                d3 = d5;
            }
            if (i2 != 0) {
                throw new org.apache.commons.math.ConvergenceException(org.apache.commons.math.exception.util.LocalizedFormats.CONTINUED_FRACTION_INFINITY_DIVERGENCE, java.lang.Double.valueOf(d));
            }
            double d18 = d10 / d11;
            if (java.lang.Double.isNaN(d18)) {
                throw new org.apache.commons.math.ConvergenceException(org.apache.commons.math.exception.util.LocalizedFormats.CONTINUED_FRACTION_NAN_DIVERGENCE, java.lang.Double.valueOf(d));
            }
            d6 = org.apache.commons.math.util.FastMath.abs((d18 / d4) - 1.0d);
            d7 = a;
            d4 = d18;
            d9 = d8;
            a = d10;
            d8 = d11;
            d5 = d3;
            i3 = 0;
        }
        if (i4 >= i) {
            throw new org.apache.commons.math.MaxIterationsExceededException(i, org.apache.commons.math.exception.util.LocalizedFormats.NON_CONVERGENT_CONTINUED_FRACTION, java.lang.Double.valueOf(d));
        }
        return d4;
    }
}
