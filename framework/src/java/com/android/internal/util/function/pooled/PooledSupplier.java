package com.android.internal.util.function.pooled;

/* loaded from: classes5.dex */
public interface PooledSupplier<T> extends com.android.internal.util.function.pooled.PooledLambda, java.util.function.Supplier<T>, com.android.internal.util.FunctionalUtils.ThrowingSupplier<T> {

    public interface OfDouble extends java.util.function.DoubleSupplier, com.android.internal.util.function.pooled.PooledLambda {
        @Override // 
        com.android.internal.util.function.pooled.PooledSupplier.OfDouble recycleOnUse();
    }

    public interface OfInt extends java.util.function.IntSupplier, com.android.internal.util.function.pooled.PooledLambda {
        @Override // com.android.internal.util.function.pooled.PooledSupplier.OfLong, com.android.internal.util.function.pooled.PooledSupplier.OfDouble
        com.android.internal.util.function.pooled.PooledSupplier.OfInt recycleOnUse();
    }

    public interface OfLong extends java.util.function.LongSupplier, com.android.internal.util.function.pooled.PooledLambda {
        @Override // com.android.internal.util.function.pooled.PooledSupplier.OfDouble
        com.android.internal.util.function.pooled.PooledSupplier.OfLong recycleOnUse();
    }

    com.android.internal.util.function.pooled.PooledRunnable asRunnable();

    @Override // com.android.internal.util.function.pooled.PooledRunnable, com.android.internal.util.function.pooled.PooledSupplier.OfInt, com.android.internal.util.function.pooled.PooledSupplier.OfLong, com.android.internal.util.function.pooled.PooledSupplier.OfDouble
    com.android.internal.util.function.pooled.PooledSupplier<T> recycleOnUse();
}
