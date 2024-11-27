package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
abstract class ContextHubServiceTransaction {
    private boolean mIsComplete;
    private final java.lang.Integer mMessageSequenceNumber;
    private final java.lang.Long mNanoAppId;
    private final java.lang.String mPackage;
    private final int mTransactionId;
    private final int mTransactionType;

    abstract int onTransact();

    ContextHubServiceTransaction(int i, int i2, java.lang.String str) {
        this.mIsComplete = false;
        this.mTransactionId = i;
        this.mTransactionType = i2;
        this.mNanoAppId = null;
        this.mPackage = str;
        this.mMessageSequenceNumber = null;
    }

    ContextHubServiceTransaction(int i, int i2, long j, java.lang.String str) {
        this.mIsComplete = false;
        this.mTransactionId = i;
        this.mTransactionType = i2;
        this.mNanoAppId = java.lang.Long.valueOf(j);
        this.mPackage = str;
        this.mMessageSequenceNumber = null;
    }

    ContextHubServiceTransaction(int i, int i2, java.lang.String str, int i3) {
        this.mIsComplete = false;
        this.mTransactionId = i;
        this.mTransactionType = i2;
        this.mNanoAppId = null;
        this.mPackage = str;
        this.mMessageSequenceNumber = java.lang.Integer.valueOf(i3);
    }

    void onTransactionComplete(int i) {
    }

    void onQueryResponse(int i, java.util.List<android.hardware.location.NanoAppState> list) {
    }

    int getTransactionId() {
        return this.mTransactionId;
    }

    int getTransactionType() {
        return this.mTransactionType;
    }

    java.lang.Integer getMessageSequenceNumber() {
        return this.mMessageSequenceNumber;
    }

    long getTimeout(java.util.concurrent.TimeUnit timeUnit) {
        switch (this.mTransactionType) {
            case 0:
                return timeUnit.convert(30L, java.util.concurrent.TimeUnit.SECONDS);
            case 5:
                return timeUnit.convert(1000L, java.util.concurrent.TimeUnit.MILLISECONDS);
            default:
                return timeUnit.convert(5L, java.util.concurrent.TimeUnit.SECONDS);
        }
    }

    void setComplete() {
        this.mIsComplete = true;
    }

    boolean isComplete() {
        return this.mIsComplete;
    }

    public java.lang.String toString() {
        java.lang.String str = android.hardware.location.ContextHubTransaction.typeToString(this.mTransactionType, true) + " (";
        if (this.mNanoAppId != null) {
            str = str + "appId = 0x" + java.lang.Long.toHexString(this.mNanoAppId.longValue()) + ", ";
        }
        java.lang.String str2 = str + "package = " + this.mPackage;
        if (this.mMessageSequenceNumber != null) {
            str2 = str2 + ", messageSequenceNumber = " + this.mMessageSequenceNumber;
        }
        return str2 + ")";
    }
}
