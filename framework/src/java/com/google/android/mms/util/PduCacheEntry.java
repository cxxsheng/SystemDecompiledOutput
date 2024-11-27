package com.google.android.mms.util;

/* loaded from: classes5.dex */
public final class PduCacheEntry {
    private final int mMessageBox;
    private final com.google.android.mms.pdu.GenericPdu mPdu;
    private final long mThreadId;

    public PduCacheEntry(com.google.android.mms.pdu.GenericPdu genericPdu, int i, long j) {
        this.mPdu = genericPdu;
        this.mMessageBox = i;
        this.mThreadId = j;
    }

    public com.google.android.mms.pdu.GenericPdu getPdu() {
        return this.mPdu;
    }

    public int getMessageBox() {
        return this.mMessageBox;
    }

    public long getThreadId() {
        return this.mThreadId;
    }
}
