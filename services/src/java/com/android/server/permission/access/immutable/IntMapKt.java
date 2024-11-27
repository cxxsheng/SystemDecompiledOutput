package com.android.server.permission.access.immutable;

/* compiled from: IntMap.kt */
/* loaded from: classes2.dex */
public final class IntMapKt {
    @org.jetbrains.annotations.Nullable
    public static final <T> T putReturnOld(@org.jetbrains.annotations.NotNull android.util.SparseArray<T> sparseArray, int key, T t) {
        int index = sparseArray.indexOfKey(key);
        if (index >= 0) {
            T valueAt = sparseArray.valueAt(index);
            sparseArray.setValueAt(index, t);
            return valueAt;
        }
        sparseArray.put(key, t);
        return null;
    }

    public static final <T> T removeAtReturnOld(@org.jetbrains.annotations.NotNull android.util.SparseArray<T> sparseArray, int index) {
        T valueAt = sparseArray.valueAt(index);
        sparseArray.removeAt(index);
        return valueAt;
    }

    public static final <T> void gc(@org.jetbrains.annotations.NotNull android.util.SparseArray<T> sparseArray) {
        sparseArray.size();
    }
}
