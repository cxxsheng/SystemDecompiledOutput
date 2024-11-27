package com.android.server.wm.utils;

/* loaded from: classes3.dex */
public class RotationCache<T, R> {
    private final android.util.SparseArray<R> mCache = new android.util.SparseArray<>(4);
    private T mCachedFor;
    private final com.android.server.wm.utils.RotationCache.RotationDependentComputation<T, R> mComputation;

    @java.lang.FunctionalInterface
    public interface RotationDependentComputation<T, R> {
        R compute(T t, int i);
    }

    public RotationCache(com.android.server.wm.utils.RotationCache.RotationDependentComputation<T, R> rotationDependentComputation) {
        this.mComputation = rotationDependentComputation;
    }

    public R getOrCompute(T t, int i) {
        if (t != this.mCachedFor) {
            this.mCache.clear();
            this.mCachedFor = t;
        }
        int indexOfKey = this.mCache.indexOfKey(i);
        if (indexOfKey >= 0) {
            return this.mCache.valueAt(indexOfKey);
        }
        R compute = this.mComputation.compute(t, i);
        this.mCache.put(i, compute);
        return compute;
    }
}
