package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public class OperatorStreamException extends java.io.IOException {
    private java.lang.Throwable cause;

    public OperatorStreamException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
