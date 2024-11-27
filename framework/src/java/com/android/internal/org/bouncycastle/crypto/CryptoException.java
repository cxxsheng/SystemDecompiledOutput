package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public class CryptoException extends java.lang.Exception {
    private java.lang.Throwable cause;

    public CryptoException() {
    }

    public CryptoException(java.lang.String str) {
        super(str);
    }

    public CryptoException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
