package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
abstract class LimitedInputStream extends java.io.InputStream {
    protected final java.io.InputStream _in;
    private int _limit;

    LimitedInputStream(java.io.InputStream inputStream, int i) {
        this._in = inputStream;
        this._limit = i;
    }

    int getLimit() {
        return this._limit;
    }

    protected void setParentEofDetect(boolean z) {
        if (this._in instanceof com.android.internal.org.bouncycastle.asn1.IndefiniteLengthInputStream) {
            ((com.android.internal.org.bouncycastle.asn1.IndefiniteLengthInputStream) this._in).setEofOn00(z);
        }
    }
}
