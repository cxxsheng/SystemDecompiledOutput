package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class HalException extends java.lang.RuntimeException {
    public final int errorCode;

    public HalException(int i, @android.annotation.NonNull java.lang.String str) {
        super(str);
        this.errorCode = i;
    }

    public HalException(int i) {
        this.errorCode = i;
    }

    @Override // java.lang.Throwable
    @android.annotation.NonNull
    public java.lang.String toString() {
        return super.toString() + " (code " + this.errorCode + ")";
    }
}
