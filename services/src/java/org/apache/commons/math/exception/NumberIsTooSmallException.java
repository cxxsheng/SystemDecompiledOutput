package org.apache.commons.math.exception;

/* loaded from: classes3.dex */
public class NumberIsTooSmallException extends org.apache.commons.math.exception.MathIllegalNumberException {
    private static final long serialVersionUID = -6100997100383932834L;
    private final boolean boundIsAllowed;
    private final java.lang.Number min;

    public NumberIsTooSmallException(java.lang.Number number, java.lang.Number number2, boolean z) {
        this(null, number, number2, z);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NumberIsTooSmallException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Number number, java.lang.Number number2, boolean z) {
        super(localizable, r0, number, number2);
        org.apache.commons.math.exception.util.LocalizedFormats localizedFormats;
        if (z) {
            localizedFormats = org.apache.commons.math.exception.util.LocalizedFormats.NUMBER_TOO_SMALL;
        } else {
            localizedFormats = org.apache.commons.math.exception.util.LocalizedFormats.NUMBER_TOO_SMALL_BOUND_EXCLUDED;
        }
        this.min = number2;
        this.boundIsAllowed = z;
    }

    public boolean getBoundIsAllowed() {
        return this.boundIsAllowed;
    }

    public java.lang.Number getMin() {
        return this.min;
    }
}
