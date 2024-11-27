package com.android.internal.org.bouncycastle.util.io.pem;

/* loaded from: classes4.dex */
public class PemGenerationException extends java.io.IOException {
    private java.lang.Throwable cause;

    public PemGenerationException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    public PemGenerationException(java.lang.String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
