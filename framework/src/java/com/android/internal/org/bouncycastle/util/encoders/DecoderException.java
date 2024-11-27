package com.android.internal.org.bouncycastle.util.encoders;

/* loaded from: classes4.dex */
public class DecoderException extends java.lang.IllegalStateException {
    private java.lang.Throwable cause;

    DecoderException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
