package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class RecoverableException extends java.lang.RuntimeException {
    public final int errorCode;

    public RecoverableException(int i, @android.annotation.NonNull java.lang.String str) {
        super(str);
        this.errorCode = i;
    }

    public RecoverableException(int i) {
        this.errorCode = i;
    }

    @Override // java.lang.Throwable
    @android.annotation.NonNull
    public java.lang.String toString() {
        return super.toString() + " (code " + this.errorCode + ")";
    }
}
