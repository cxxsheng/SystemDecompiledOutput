package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class MatrixIndexException extends org.apache.commons.math.MathRuntimeException {
    private static final long serialVersionUID = 8120540015829487660L;

    @java.lang.Deprecated
    public MatrixIndexException(java.lang.String str, java.lang.Object... objArr) {
        this(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public MatrixIndexException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
    }
}
