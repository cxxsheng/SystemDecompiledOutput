package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
class ReasonsMask {
    static final com.android.internal.org.bouncycastle.jce.provider.ReasonsMask allReasons = new com.android.internal.org.bouncycastle.jce.provider.ReasonsMask(33023);
    private int _reasons;

    ReasonsMask(com.android.internal.org.bouncycastle.asn1.x509.ReasonFlags reasonFlags) {
        this._reasons = reasonFlags.intValue();
    }

    private ReasonsMask(int i) {
        this._reasons = i;
    }

    ReasonsMask() {
        this(0);
    }

    void addReasons(com.android.internal.org.bouncycastle.jce.provider.ReasonsMask reasonsMask) {
        this._reasons = reasonsMask.getReasons() | this._reasons;
    }

    boolean isAllReasons() {
        return this._reasons == allReasons._reasons;
    }

    com.android.internal.org.bouncycastle.jce.provider.ReasonsMask intersect(com.android.internal.org.bouncycastle.jce.provider.ReasonsMask reasonsMask) {
        com.android.internal.org.bouncycastle.jce.provider.ReasonsMask reasonsMask2 = new com.android.internal.org.bouncycastle.jce.provider.ReasonsMask();
        reasonsMask2.addReasons(new com.android.internal.org.bouncycastle.jce.provider.ReasonsMask(reasonsMask.getReasons() & this._reasons));
        return reasonsMask2;
    }

    boolean hasNewReasons(com.android.internal.org.bouncycastle.jce.provider.ReasonsMask reasonsMask) {
        return ((reasonsMask.getReasons() ^ this._reasons) | this._reasons) != 0;
    }

    int getReasons() {
        return this._reasons;
    }
}
