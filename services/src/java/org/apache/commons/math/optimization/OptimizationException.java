package org.apache.commons.math.optimization;

/* loaded from: classes3.dex */
public class OptimizationException extends org.apache.commons.math.ConvergenceException {
    private static final long serialVersionUID = -4605887730798282127L;

    @java.lang.Deprecated
    public OptimizationException(java.lang.String str, java.lang.Object... objArr) {
        this(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public OptimizationException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
    }

    public OptimizationException(java.lang.Throwable th) {
        super(th);
    }
}
