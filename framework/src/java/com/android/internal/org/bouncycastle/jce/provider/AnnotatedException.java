package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class AnnotatedException extends java.lang.Exception implements com.android.internal.org.bouncycastle.jce.exception.ExtException {
    private java.lang.Throwable _underlyingException;

    public AnnotatedException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this._underlyingException = th;
    }

    public AnnotatedException(java.lang.String str) {
        this(str, null);
    }

    java.lang.Throwable getUnderlyingException() {
        return this._underlyingException;
    }

    @Override // java.lang.Throwable, com.android.internal.org.bouncycastle.jce.exception.ExtException
    public java.lang.Throwable getCause() {
        return this._underlyingException;
    }
}
