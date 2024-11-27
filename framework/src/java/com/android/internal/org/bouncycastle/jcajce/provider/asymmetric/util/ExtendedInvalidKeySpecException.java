package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes4.dex */
public class ExtendedInvalidKeySpecException extends java.security.spec.InvalidKeySpecException {
    private java.lang.Throwable cause;

    public ExtendedInvalidKeySpecException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
