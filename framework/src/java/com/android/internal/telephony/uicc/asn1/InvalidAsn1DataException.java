package com.android.internal.telephony.uicc.asn1;

/* loaded from: classes5.dex */
public class InvalidAsn1DataException extends java.lang.Exception {
    private final int mTag;

    public InvalidAsn1DataException(int i, java.lang.String str) {
        super(str);
        this.mTag = i;
    }

    public InvalidAsn1DataException(int i, java.lang.String str, java.lang.Throwable th) {
        super(str, th);
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
