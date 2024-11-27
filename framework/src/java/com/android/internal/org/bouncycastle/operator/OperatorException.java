package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public class OperatorException extends java.lang.Exception {
    private java.lang.Throwable cause;

    public OperatorException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    public OperatorException(java.lang.String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
