package com.android.server;

/* loaded from: classes.dex */
public class AppFuseMountException extends java.lang.Exception {
    public AppFuseMountException(java.lang.String str) {
        super(str);
    }

    public AppFuseMountException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }

    public java.lang.IllegalArgumentException rethrowAsParcelableException() {
        throw new java.lang.IllegalStateException(getMessage(), this);
    }
}
