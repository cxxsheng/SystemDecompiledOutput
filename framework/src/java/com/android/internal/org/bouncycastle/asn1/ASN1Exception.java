package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1Exception extends java.io.IOException {
    private java.lang.Throwable cause;

    ASN1Exception(java.lang.String str) {
        super(str);
    }

    ASN1Exception(java.lang.String str, java.lang.Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.cause;
    }
}
