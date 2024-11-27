package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class CMSException extends java.lang.Exception {
    java.lang.Exception e;

    public CMSException(java.lang.String str) {
        super(str);
    }

    public CMSException(java.lang.String str, java.lang.Exception exc) {
        super(str);
        this.e = exc;
    }

    public java.lang.Exception getUnderlyingException() {
        return this.e;
    }

    @Override // java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.e;
    }
}
