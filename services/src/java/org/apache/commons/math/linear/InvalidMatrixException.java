package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class InvalidMatrixException extends org.apache.commons.math.MathRuntimeException {
    private static final long serialVersionUID = -2068020346562029801L;

    @java.lang.Deprecated
    public InvalidMatrixException(java.lang.String str, java.lang.Object... objArr) {
        this(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public InvalidMatrixException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
    }

    public InvalidMatrixException(java.lang.Throwable th) {
        super(th);
    }
}
