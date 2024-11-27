package com.android.internal.org.bouncycastle.jcajce.provider.util;

/* loaded from: classes4.dex */
public class BadBlockException extends javax.crypto.BadPaddingException {
    private final java.lang.Throwable cause;

    public BadBlockException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
