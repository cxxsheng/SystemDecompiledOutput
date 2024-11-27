package org.apache.commons.math.ode;

/* loaded from: classes3.dex */
public class IntegratorException extends org.apache.commons.math.MathException {
    private static final long serialVersionUID = -1607588949778036796L;

    @java.lang.Deprecated
    public IntegratorException(java.lang.String str, java.lang.Object... objArr) {
        super(str, objArr);
    }

    public IntegratorException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
    }

    public IntegratorException(java.lang.Throwable th) {
        super(th);
    }
}
