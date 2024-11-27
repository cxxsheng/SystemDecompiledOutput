package com.android.internal.logging;

/* loaded from: classes4.dex */
public class InstanceIdSequence {
    protected final int mInstanceIdMax;
    private final java.util.Random mRandom = new java.security.SecureRandom();

    public InstanceIdSequence(int i) {
        this.mInstanceIdMax = java.lang.Math.min(java.lang.Math.max(1, i), 1048576);
    }

    public com.android.internal.logging.InstanceId newInstanceId() {
        return newInstanceIdInternal(this.mRandom.nextInt(this.mInstanceIdMax) + 1);
    }

    protected com.android.internal.logging.InstanceId newInstanceIdInternal(int i) {
        return new com.android.internal.logging.InstanceId(i);
    }
}
