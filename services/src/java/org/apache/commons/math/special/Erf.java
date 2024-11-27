package org.apache.commons.math.special;

/* loaded from: classes3.dex */
public class Erf {
    private Erf() {
    }

    public static double erf(double d) throws org.apache.commons.math.MathException {
        if (org.apache.commons.math.util.FastMath.abs(d) > 40.0d) {
            return d > 0.0d ? 1.0d : -1.0d;
        }
        double regularizedGammaP = org.apache.commons.math.special.Gamma.regularizedGammaP(0.5d, d * d, 1.0E-15d, 10000);
        if (d < 0.0d) {
            return -regularizedGammaP;
        }
        return regularizedGammaP;
    }

    public static double erfc(double d) throws org.apache.commons.math.MathException {
        if (org.apache.commons.math.util.FastMath.abs(d) > 40.0d) {
            return d > 0.0d ? 0.0d : 2.0d;
        }
        double regularizedGammaQ = org.apache.commons.math.special.Gamma.regularizedGammaQ(0.5d, d * d, 1.0E-15d, 10000);
        return d < 0.0d ? 2.0d - regularizedGammaQ : regularizedGammaQ;
    }
}
