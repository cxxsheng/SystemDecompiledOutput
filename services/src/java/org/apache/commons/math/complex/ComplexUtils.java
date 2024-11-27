package org.apache.commons.math.complex;

/* loaded from: classes3.dex */
public class ComplexUtils {
    private ComplexUtils() {
    }

    public static org.apache.commons.math.complex.Complex polar2Complex(double d, double d2) {
        if (d < 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NEGATIVE_COMPLEX_MODULE, java.lang.Double.valueOf(d));
        }
        return new org.apache.commons.math.complex.Complex(org.apache.commons.math.util.FastMath.cos(d2) * d, d * org.apache.commons.math.util.FastMath.sin(d2));
    }
}
