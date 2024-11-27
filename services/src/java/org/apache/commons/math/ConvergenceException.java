package org.apache.commons.math;

/* loaded from: classes3.dex */
public class ConvergenceException extends org.apache.commons.math.MathException {
    private static final long serialVersionUID = -1111352570797662604L;

    public ConvergenceException() {
        super(org.apache.commons.math.exception.util.LocalizedFormats.CONVERGENCE_FAILED, new java.lang.Object[0]);
    }

    @java.lang.Deprecated
    public ConvergenceException(java.lang.String str, java.lang.Object... objArr) {
        this(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public ConvergenceException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
    }

    public ConvergenceException(java.lang.Throwable th) {
        super(th);
    }

    @java.lang.Deprecated
    public ConvergenceException(java.lang.Throwable th, java.lang.String str, java.lang.Object... objArr) {
        this(th, new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public ConvergenceException(java.lang.Throwable th, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(th, localizable, objArr);
    }
}
