package com.android.server.utils;

/* loaded from: classes2.dex */
public class Snapshots {
    public static <T> T maybeSnapshot(T t) {
        if (t instanceof com.android.server.utils.Snappable) {
            return (T) ((com.android.server.utils.Snappable) t).snapshot();
        }
        return t;
    }

    public static <E> void copy(@android.annotation.NonNull android.util.SparseArray<E> sparseArray, @android.annotation.NonNull android.util.SparseArray<E> sparseArray2) {
        if (sparseArray.size() != 0) {
            throw new java.lang.IllegalArgumentException("copy destination is not empty");
        }
        int size = sparseArray2.size();
        for (int i = 0; i < size; i++) {
            sparseArray.put(sparseArray2.keyAt(i), sparseArray2.valueAt(i));
        }
    }

    public static <E> void copy(@android.annotation.NonNull android.util.SparseSetArray<E> sparseSetArray, @android.annotation.NonNull android.util.SparseSetArray<E> sparseSetArray2) {
        if (sparseSetArray.size() != 0) {
            throw new java.lang.IllegalArgumentException("copy destination is not empty");
        }
        int size = sparseSetArray2.size();
        for (int i = 0; i < size; i++) {
            int sizeAt = sparseSetArray2.sizeAt(i);
            for (int i2 = 0; i2 < sizeAt; i2++) {
                sparseSetArray.add(sparseSetArray2.keyAt(i), sparseSetArray2.valueAt(i, i2));
            }
        }
    }

    public static void snapshot(@android.annotation.NonNull android.util.SparseIntArray sparseIntArray, @android.annotation.NonNull android.util.SparseIntArray sparseIntArray2) {
        if (sparseIntArray.size() != 0) {
            throw new java.lang.IllegalArgumentException("snapshot destination is not empty");
        }
        int size = sparseIntArray2.size();
        for (int i = 0; i < size; i++) {
            sparseIntArray.put(sparseIntArray2.keyAt(i), sparseIntArray2.valueAt(i));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E extends com.android.server.utils.Snappable<E>> void snapshot(@android.annotation.NonNull android.util.SparseArray<E> sparseArray, @android.annotation.NonNull android.util.SparseArray<E> sparseArray2) {
        if (sparseArray.size() != 0) {
            throw new java.lang.IllegalArgumentException("snapshot destination is not empty");
        }
        int size = sparseArray2.size();
        for (int i = 0; i < size; i++) {
            sparseArray.put(sparseArray2.keyAt(i), (com.android.server.utils.Snappable) sparseArray2.valueAt(i).snapshot());
        }
    }

    public static <E extends com.android.server.utils.Snappable<E>> void snapshot(@android.annotation.NonNull android.util.SparseSetArray<E> sparseSetArray, @android.annotation.NonNull android.util.SparseSetArray<E> sparseSetArray2) {
        if (sparseSetArray.size() != 0) {
            throw new java.lang.IllegalArgumentException("snapshot destination is not empty");
        }
        int size = sparseSetArray2.size();
        for (int i = 0; i < size; i++) {
            int sizeAt = sparseSetArray2.sizeAt(i);
            for (int i2 = 0; i2 < sizeAt; i2++) {
                sparseSetArray.add(sparseSetArray2.keyAt(i), (com.android.server.utils.Snappable) ((com.android.server.utils.Snappable) sparseSetArray2.valueAt(i, i2)).snapshot());
            }
        }
    }
}
