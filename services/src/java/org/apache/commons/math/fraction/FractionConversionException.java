package org.apache.commons.math.fraction;

/* loaded from: classes3.dex */
public class FractionConversionException extends org.apache.commons.math.ConvergenceException {
    private static final long serialVersionUID = -4661812640132576263L;

    public FractionConversionException(double d, int i) {
        super(org.apache.commons.math.exception.util.LocalizedFormats.FAILED_FRACTION_CONVERSION, java.lang.Double.valueOf(d), java.lang.Integer.valueOf(i));
    }

    public FractionConversionException(double d, long j, long j2) {
        super(org.apache.commons.math.exception.util.LocalizedFormats.FRACTION_CONVERSION_OVERFLOW, java.lang.Double.valueOf(d), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2));
    }
}
