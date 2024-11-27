package com.android.ims;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class ImsException extends java.lang.Exception {
    private int mCode;

    public ImsException() {
    }

    public ImsException(java.lang.String str, int i) {
        super(str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        this.mCode = i;
    }

    public ImsException(java.lang.String str, java.lang.Throwable th, int i) {
        super(str, th);
        this.mCode = i;
    }

    public int getCode() {
        return this.mCode;
    }
}
