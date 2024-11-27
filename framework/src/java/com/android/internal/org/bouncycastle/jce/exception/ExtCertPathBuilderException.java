package com.android.internal.org.bouncycastle.jce.exception;

/* loaded from: classes4.dex */
public class ExtCertPathBuilderException extends java.security.cert.CertPathBuilderException implements com.android.internal.org.bouncycastle.jce.exception.ExtException {
    private java.lang.Throwable cause;

    public ExtCertPathBuilderException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    public ExtCertPathBuilderException(java.lang.String str, java.lang.Throwable th, java.security.cert.CertPath certPath, int i) {
        super(str, th);
        this.cause = th;
    }

    @Override // java.lang.Throwable, com.android.internal.org.bouncycastle.jce.exception.ExtException
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
