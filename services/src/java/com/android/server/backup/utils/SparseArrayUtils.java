package com.android.server.backup.utils;

/* loaded from: classes.dex */
public final class SparseArrayUtils {
    private SparseArrayUtils() {
    }

    public static <V> java.util.HashSet<V> union(android.util.SparseArray<java.util.HashSet<V>> sparseArray) {
        java.util.HashSet<V> hashSet = new java.util.HashSet<>();
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            java.util.HashSet<V> valueAt = sparseArray.valueAt(i);
            if (valueAt != null) {
                hashSet.addAll(valueAt);
            }
        }
        return hashSet;
    }
}
