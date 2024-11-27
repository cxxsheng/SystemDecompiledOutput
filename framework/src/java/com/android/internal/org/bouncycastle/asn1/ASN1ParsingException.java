package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1ParsingException extends java.lang.IllegalStateException {
    private java.lang.Throwable cause;

    public ASN1ParsingException(java.lang.String str) {
        super(str);
    }

    public ASN1ParsingException(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
