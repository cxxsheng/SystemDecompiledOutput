package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public class RuntimeOperatorException extends java.lang.RuntimeException {
    private java.lang.Throwable cause;

    public RuntimeOperatorException(java.lang.String str) {
        super(str);
    }

    public RuntimeOperatorException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
