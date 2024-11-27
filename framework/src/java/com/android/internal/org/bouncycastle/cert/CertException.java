package com.android.internal.org.bouncycastle.cert;

/* loaded from: classes4.dex */
public class CertException extends java.lang.Exception {
    private java.lang.Throwable cause;

    public CertException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    public CertException(java.lang.String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
