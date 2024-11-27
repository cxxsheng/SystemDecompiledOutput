package org.apache.commons.math.exception;

/* loaded from: classes3.dex */
public class NumberIsTooLargeException extends org.apache.commons.math.exception.MathIllegalNumberException {
    private static final long serialVersionUID = 4330003017885151975L;
    private final boolean boundIsAllowed;
    private final java.lang.Number max;

    public NumberIsTooLargeException(java.lang.Number number, java.lang.Number number2, boolean z) {
        this(null, number, number2, z);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NumberIsTooLargeException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Number number, java.lang.Number number2, boolean z) {
        super(localizable, r0, number, number2);
        org.apache.commons.math.exception.util.LocalizedFormats localizedFormats;
        if (z) {
            localizedFormats = org.apache.commons.math.exception.util.LocalizedFormats.NUMBER_TOO_LARGE;
        } else {
            localizedFormats = org.apache.commons.math.exception.util.LocalizedFormats.NUMBER_TOO_LARGE_BOUND_EXCLUDED;
        }
        this.max = number2;
        this.boundIsAllowed = z;
    }

    public boolean getBoundIsAllowed() {
        return this.boundIsAllowed;
    }

    public java.lang.Number getMax() {
        return this.max;
    }
}
