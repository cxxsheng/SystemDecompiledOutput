package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
class OrdinalGenerator<T> {

    @android.annotation.NonNull
    private final java.util.function.Function<T, T> mCanonicalizationFunction;
    private final android.util.ArraySet<T> mKnownIds = new android.util.ArraySet<>();

    OrdinalGenerator(@android.annotation.NonNull java.util.function.Function<T, T> function) {
        java.util.Objects.requireNonNull(function);
        this.mCanonicalizationFunction = function;
    }

    int ordinal(T t) {
        T apply = this.mCanonicalizationFunction.apply(t);
        int indexOf = this.mKnownIds.indexOf(apply);
        if (indexOf < 0) {
            int size = this.mKnownIds.size();
            this.mKnownIds.add(apply);
            return size;
        }
        return indexOf;
    }

    int[] ordinals(java.util.List<T> list) {
        int size = list.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ordinal(list.get(i));
        }
        return iArr;
    }
}
