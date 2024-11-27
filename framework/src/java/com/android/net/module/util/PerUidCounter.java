package com.android.net.module.util;

/* loaded from: classes5.dex */
public class PerUidCounter {
    private final int mMaxCountPerUid;
    final android.util.SparseIntArray mUidToCount = new android.util.SparseIntArray();

    public PerUidCounter(int i) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("Maximum counter value must be positive");
        }
        this.mMaxCountPerUid = i;
    }

    public synchronized void incrementCountOrThrow(int i) {
        long j = this.mUidToCount.get(i, 0) + 1;
        if (j > this.mMaxCountPerUid) {
            throw new java.lang.IllegalStateException("Uid " + i + " exceeded its allowed limit");
        }
        this.mUidToCount.put(i, (int) j);
    }

    public synchronized void decrementCountOrThrow(int i) {
        int i2 = this.mUidToCount.get(i, 0) - 1;
        if (i2 < 0) {
            throw new java.lang.IllegalStateException("BUG: too small count " + i2 + " for UID " + i);
        }
        if (i2 == 0) {
            this.mUidToCount.delete(i);
        } else {
            this.mUidToCount.put(i, i2);
        }
    }

    public synchronized int get(int i) {
        return this.mUidToCount.get(i, 0);
    }
}
