package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class MatrixVisitorException extends org.apache.commons.math.MathRuntimeException {
    private static final long serialVersionUID = 3814333035048617048L;

    public MatrixVisitorException(java.lang.String str, java.lang.Object[] objArr) {
        super(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public MatrixVisitorException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object[] objArr) {
        super(localizable, objArr);
    }
}
