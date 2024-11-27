package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
class ExtCRLException extends java.security.cert.CRLException {
    java.lang.Throwable cause;

    ExtCRLException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
