package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class PKIXNameConstraintValidatorException extends java.lang.Exception {
    private java.lang.Throwable cause;

    public PKIXNameConstraintValidatorException(java.lang.String str) {
        super(str);
    }

    public PKIXNameConstraintValidatorException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
