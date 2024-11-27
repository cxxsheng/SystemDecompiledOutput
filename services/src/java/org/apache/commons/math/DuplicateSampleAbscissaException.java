package org.apache.commons.math;

/* loaded from: classes3.dex */
public class DuplicateSampleAbscissaException extends org.apache.commons.math.MathException {
    private static final long serialVersionUID = -2271007547170169872L;

    public DuplicateSampleAbscissaException(double d, int i, int i2) {
        super(org.apache.commons.math.exception.util.LocalizedFormats.DUPLICATED_ABSCISSA, java.lang.Double.valueOf(d), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    public double getDuplicateAbscissa() {
        return ((java.lang.Double) getArguments()[0]).doubleValue();
    }
}
