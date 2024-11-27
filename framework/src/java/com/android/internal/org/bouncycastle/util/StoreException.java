package com.android.internal.org.bouncycastle.util;

/* loaded from: classes4.dex */
public class StoreException extends java.lang.RuntimeException {
    private java.lang.Throwable _e;

    public StoreException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this._e = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this._e;
    }
}
