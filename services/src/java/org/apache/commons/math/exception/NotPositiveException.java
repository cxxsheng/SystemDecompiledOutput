package org.apache.commons.math.exception;

/* loaded from: classes3.dex */
public class NotPositiveException extends org.apache.commons.math.exception.NumberIsTooSmallException {
    private static final long serialVersionUID = -2250556892093726375L;

    public NotPositiveException(java.lang.Number number) {
        super(number, 0, true);
    }

    public NotPositiveException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Number number) {
        super(localizable, number, 0, true);
    }
}
