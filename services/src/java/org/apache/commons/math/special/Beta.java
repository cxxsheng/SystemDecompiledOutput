package org.apache.commons.math.special;

/* loaded from: classes3.dex */
public class Beta {
    private static final double DEFAULT_EPSILON = 1.0E-14d;

    private Beta() {
    }

    public static double regularizedBeta(double d, double d2, double d3) throws org.apache.commons.math.MathException {
        return regularizedBeta(d, d2, d3, DEFAULT_EPSILON, Integer.MAX_VALUE);
    }

    public static double regularizedBeta(double d, double d2, double d3, double d4) throws org.apache.commons.math.MathException {
        return regularizedBeta(d, d2, d3, d4, Integer.MAX_VALUE);
    }

    public static double regularizedBeta(double d, double d2, double d3, int i) throws org.apache.commons.math.MathException {
        return regularizedBeta(d, d2, d3, DEFAULT_EPSILON, i);
    }

    public static double regularizedBeta(double d, final double d2, final double d3, double d4, int i) throws org.apache.commons.math.MathException {
        if (java.lang.Double.isNaN(d) || java.lang.Double.isNaN(d2) || java.lang.Double.isNaN(d3) || d < 0.0d || d > 1.0d || d2 <= 0.0d || d3 <= 0.0d) {
            return Double.NaN;
        }
        if (d <= (d2 + 1.0d) / ((d2 + d3) + 2.0d)) {
            return (org.apache.commons.math.util.FastMath.exp((((org.apache.commons.math.util.FastMath.log(d) * d2) + (org.apache.commons.math.util.FastMath.log(1.0d - d) * d3)) - org.apache.commons.math.util.FastMath.log(d2)) - logBeta(d2, d3, d4, i)) * 1.0d) / new org.apache.commons.math.util.ContinuedFraction() { // from class: org.apache.commons.math.special.Beta.1
                @Override // org.apache.commons.math.util.ContinuedFraction
                protected double getB(int i2, double d5) {
                    if (i2 % 2 == 0) {
                        double d6 = i2 / 2.0d;
                        double d7 = (d3 - d6) * d6 * d5;
                        double d8 = d6 * 2.0d;
                        return d7 / (((d2 + d8) - 1.0d) * (d2 + d8));
                    }
                    double d9 = (i2 - 1.0d) / 2.0d;
                    double d10 = -((d2 + d9) * (d2 + d3 + d9) * d5);
                    double d11 = d9 * 2.0d;
                    return d10 / ((d2 + d11) * ((d2 + d11) + 1.0d));
                }

                @Override // org.apache.commons.math.util.ContinuedFraction
                protected double getA(int i2, double d5) {
                    return 1.0d;
                }
            }.evaluate(d, d4, i);
        }
        return 1.0d - regularizedBeta(1.0d - d, d3, d2, d4, i);
    }

    public static double logBeta(double d, double d2) {
        return logBeta(d, d2, DEFAULT_EPSILON, Integer.MAX_VALUE);
    }

    public static double logBeta(double d, double d2, double d3, int i) {
        if (java.lang.Double.isNaN(d) || java.lang.Double.isNaN(d2) || d <= 0.0d || d2 <= 0.0d) {
            return Double.NaN;
        }
        return (org.apache.commons.math.special.Gamma.logGamma(d) + org.apache.commons.math.special.Gamma.logGamma(d2)) - org.apache.commons.math.special.Gamma.logGamma(d + d2);
    }
}
