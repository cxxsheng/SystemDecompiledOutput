package org.apache.commons.math.exception;

/* loaded from: classes3.dex */
public class NotStrictlyPositiveException extends org.apache.commons.math.exception.NumberIsTooSmallException {
    private static final long serialVersionUID = -7824848630829852237L;

    public NotStrictlyPositiveException(java.lang.Number number) {
        super(number, 0, false);
    }

    public NotStrictlyPositiveException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Number number) {
        super(localizable, number, 0, false);
    }
}
