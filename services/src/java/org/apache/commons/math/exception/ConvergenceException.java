package org.apache.commons.math.exception;

/* loaded from: classes3.dex */
public class ConvergenceException extends org.apache.commons.math.exception.MathIllegalStateException {
    private static final long serialVersionUID = 4330003017885151975L;

    public ConvergenceException() {
        this(null);
    }

    public ConvergenceException(org.apache.commons.math.exception.util.Localizable localizable) {
        this(localizable, org.apache.commons.math.exception.util.LocalizedFormats.CONVERGENCE_FAILED, null);
    }

    public ConvergenceException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, org.apache.commons.math.exception.util.LocalizedFormats.CONVERGENCE_FAILED, objArr);
    }
}
