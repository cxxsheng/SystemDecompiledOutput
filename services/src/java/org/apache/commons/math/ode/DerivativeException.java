package org.apache.commons.math.ode;

/* loaded from: classes3.dex */
public class DerivativeException extends org.apache.commons.math.MathException {
    private static final long serialVersionUID = 5666710788967425123L;

    public DerivativeException(java.lang.String str, java.lang.Object... objArr) {
        this(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public DerivativeException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
    }

    public DerivativeException(java.lang.Throwable th) {
        super(th);
    }
}
