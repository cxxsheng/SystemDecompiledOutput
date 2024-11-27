package org.apache.commons.math.exception;

/* loaded from: classes3.dex */
public class OutOfRangeException extends org.apache.commons.math.exception.MathIllegalNumberException {
    private static final long serialVersionUID = 111601815794403609L;
    private final java.lang.Number hi;
    private final java.lang.Number lo;

    public OutOfRangeException(java.lang.Number number, java.lang.Number number2, java.lang.Number number3) {
        super(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_RANGE_SIMPLE, number, number2, number3);
        this.lo = number2;
        this.hi = number3;
    }

    public java.lang.Number getLo() {
        return this.lo;
    }

    public java.lang.Number getHi() {
        return this.hi;
    }
}
