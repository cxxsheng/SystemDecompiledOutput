package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
class ExtCertificateEncodingException extends java.security.cert.CertificateEncodingException {
    java.lang.Throwable cause;

    ExtCertificateEncodingException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
