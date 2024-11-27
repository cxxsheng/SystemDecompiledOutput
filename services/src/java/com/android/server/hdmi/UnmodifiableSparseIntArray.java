package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class UnmodifiableSparseIntArray {
    private static final java.lang.String TAG = "ImmutableSparseIntArray";
    private final android.util.SparseIntArray mArray;

    public UnmodifiableSparseIntArray(android.util.SparseIntArray sparseIntArray) {
        this.mArray = sparseIntArray;
    }

    public int size() {
        return this.mArray.size();
    }

    public int get(int i) {
        return this.mArray.get(i);
    }

    public int get(int i, int i2) {
        return this.mArray.get(i, i2);
    }

    public int keyAt(int i) {
        return this.mArray.keyAt(i);
    }

    public int valueAt(int i) {
        return this.mArray.valueAt(i);
    }

    public int indexOfValue(int i) {
        return this.mArray.indexOfValue(i);
    }

    public java.lang.String toString() {
        return this.mArray.toString();
    }
}
