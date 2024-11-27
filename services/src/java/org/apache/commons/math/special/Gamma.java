package org.apache.commons.math.special;

/* loaded from: classes3.dex */
public class Gamma {
    private static final double C_LIMIT = 49.0d;
    private static final double DEFAULT_EPSILON = 1.0E-14d;
    public static final double GAMMA = 0.5772156649015329d;
    private static final double S_LIMIT = 1.0E-5d;
    private static final double[] LANCZOS = {0.9999999999999971d, 57.15623566586292d, -59.59796035547549d, 14.136097974741746d, -0.4919138160976202d, 3.399464998481189E-5d, 4.652362892704858E-5d, -9.837447530487956E-5d, 1.580887032249125E-4d, -2.1026444172410488E-4d, 2.1743961811521265E-4d, -1.643181065367639E-4d, 8.441822398385275E-5d, -2.6190838401581408E-5d, 3.6899182659531625E-6d};
    private static final double HALF_LOG_2_PI = org.apache.commons.math.util.FastMath.log(6.283185307179586d) * 0.5d;

    private Gamma() {
    }

    public static double logGamma(double d) {
        if (!java.lang.Double.isNaN(d)) {
            double d2 = HALF_LOG_2_PI;
            if (d > HALF_LOG_2_PI) {
                for (int length = LANCZOS.length - 1; length > 0; length--) {
                    d2 += LANCZOS[length] / (length + d);
                }
                double d3 = 4.7421875d + d + 0.5d;
                return (((0.5d + d) * org.apache.commons.math.util.FastMath.log(d3)) - d3) + HALF_LOG_2_PI + org.apache.commons.math.util.FastMath.log((d2 + LANCZOS[0]) / d);
            }
        }
        return Double.NaN;
    }

    public static double regularizedGammaP(double d, double d2) throws org.apache.commons.math.MathException {
        return regularizedGammaP(d, d2, DEFAULT_EPSILON, Integer.MAX_VALUE);
    }

    public static double regularizedGammaP(double d, double d2, double d3, int i) throws org.apache.commons.math.MathException {
        if (!java.lang.Double.isNaN(d) && !java.lang.Double.isNaN(d2)) {
            double d4 = HALF_LOG_2_PI;
            if (d > HALF_LOG_2_PI && d2 >= HALF_LOG_2_PI) {
                if (d2 == HALF_LOG_2_PI) {
                    return HALF_LOG_2_PI;
                }
                if (d2 >= d + 1.0d) {
                    return 1.0d - regularizedGammaQ(d, d2, d3, i);
                }
                double d5 = 1.0d / d;
                double d6 = d5;
                while (org.apache.commons.math.util.FastMath.abs(d5 / d6) > d3 && d4 < i && d6 < Double.POSITIVE_INFINITY) {
                    d4 += 1.0d;
                    d5 *= d2 / (d + d4);
                    d6 += d5;
                }
                if (d4 >= i) {
                    throw new org.apache.commons.math.MaxIterationsExceededException(i);
                }
                if (java.lang.Double.isInfinite(d6)) {
                    return 1.0d;
                }
                return org.apache.commons.math.util.FastMath.exp(((-d2) + (org.apache.commons.math.util.FastMath.log(d2) * d)) - logGamma(d)) * d6;
            }
        }
        return Double.NaN;
    }

    public static double regularizedGammaQ(double d, double d2) throws org.apache.commons.math.MathException {
        return regularizedGammaQ(d, d2, DEFAULT_EPSILON, Integer.MAX_VALUE);
    }

    public static double regularizedGammaQ(final double d, double d2, double d3, int i) throws org.apache.commons.math.MathException {
        if (java.lang.Double.isNaN(d) || java.lang.Double.isNaN(d2) || d <= HALF_LOG_2_PI || d2 < HALF_LOG_2_PI) {
            return Double.NaN;
        }
        if (d2 == HALF_LOG_2_PI) {
            return 1.0d;
        }
        if (d2 < d + 1.0d) {
            return 1.0d - regularizedGammaP(d, d2, d3, i);
        }
        return (1.0d / new org.apache.commons.math.util.ContinuedFraction() { // from class: org.apache.commons.math.special.Gamma.1
            @Override // org.apache.commons.math.util.ContinuedFraction
            protected double getA(int i2, double d4) {
                return (((i2 * 2.0d) + 1.0d) - d) + d4;
            }

            @Override // org.apache.commons.math.util.ContinuedFraction
            protected double getB(int i2, double d4) {
                double d5 = i2;
                return d5 * (d - d5);
            }
        }.evaluate(d2, d3, i)) * org.apache.commons.math.util.FastMath.exp(((-d2) + (org.apache.commons.math.util.FastMath.log(d2) * d)) - logGamma(d));
    }

    public static double digamma(double d) {
        if (d > HALF_LOG_2_PI && d <= S_LIMIT) {
            return (-0.5772156649015329d) - (1.0d / d);
        }
        if (d >= C_LIMIT) {
            double d2 = 1.0d / (d * d);
            return (org.apache.commons.math.util.FastMath.log(d) - (0.5d / d)) - (d2 * (((0.008333333333333333d - (d2 / 252.0d)) * d2) + 0.08333333333333333d));
        }
        return digamma(d + 1.0d) - (1.0d / d);
    }

    public static double trigamma(double d) {
        if (d > HALF_LOG_2_PI && d <= S_LIMIT) {
            return 1.0d / (d * d);
        }
        if (d >= C_LIMIT) {
            double d2 = 1.0d / (d * d);
            return (1.0d / d) + (d2 / 2.0d) + ((d2 / d) * (0.16666666666666666d - (d2 * ((d2 / 42.0d) + 0.03333333333333333d))));
        }
        return trigamma(d + 1.0d) + (1.0d / (d * d));
    }
}
