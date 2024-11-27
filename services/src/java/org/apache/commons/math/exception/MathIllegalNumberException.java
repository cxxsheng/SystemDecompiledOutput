package org.apache.commons.math.exception;

/* loaded from: classes3.dex */
public class MathIllegalNumberException extends org.apache.commons.math.exception.MathIllegalArgumentException {
    private static final long serialVersionUID = -7447085893598031110L;
    private final java.lang.Number argument;

    protected MathIllegalNumberException(org.apache.commons.math.exception.util.Localizable localizable, org.apache.commons.math.exception.util.Localizable localizable2, java.lang.Number number, java.lang.Object... objArr) {
        super(localizable, localizable2, number, objArr);
        this.argument = number;
    }

    protected MathIllegalNumberException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Number number, java.lang.Object... objArr) {
        super(localizable, number, objArr);
        this.argument = number;
    }

    public java.lang.Number getArgument() {
        return this.argument;
    }
}
