package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class UnmodifiableSparseArray<E> {
    private static final java.lang.String TAG = "ImmutableSparseArray";
    private final android.util.SparseArray<E> mArray;

    public UnmodifiableSparseArray(android.util.SparseArray<E> sparseArray) {
        this.mArray = sparseArray;
    }

    public int size() {
        return this.mArray.size();
    }

    public E get(int i) {
        return this.mArray.get(i);
    }

    public E get(int i, E e) {
        return this.mArray.get(i, e);
    }

    public int keyAt(int i) {
        return this.mArray.keyAt(i);
    }

    public E valueAt(int i) {
        return this.mArray.valueAt(i);
    }

    public int indexOfValue(E e) {
        return this.mArray.indexOfValue(e);
    }

    public java.lang.String toString() {
        return this.mArray.toString();
    }
}
