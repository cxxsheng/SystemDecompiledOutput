package org.apache.commons.math;

/* loaded from: classes3.dex */
public class ArgumentOutsideDomainException extends org.apache.commons.math.FunctionEvaluationException {
    private static final long serialVersionUID = -4965972841162580234L;

    public ArgumentOutsideDomainException(double d, double d2, double d3) {
        super(d, org.apache.commons.math.exception.util.LocalizedFormats.ARGUMENT_OUTSIDE_DOMAIN, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2), java.lang.Double.valueOf(d3));
    }
}
