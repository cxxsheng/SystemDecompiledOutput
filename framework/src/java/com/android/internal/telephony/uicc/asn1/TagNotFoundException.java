package com.android.internal.telephony.uicc.asn1;

/* loaded from: classes5.dex */
public class TagNotFoundException extends java.lang.Exception {
    private final int mTag;

    public TagNotFoundException(int i) {
        this.mTag = i;
    }

    public int getTag() {
        return this.mTag;
    }

    @Override // java.lang.Throwable
    public java.lang.String getMessage() {
        return super.getMessage() + " (tag=" + this.mTag + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
