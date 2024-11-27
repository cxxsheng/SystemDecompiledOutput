package com.android.internal.util.function.pooled;

/* loaded from: classes5.dex */
public interface PooledPredicate<T> extends com.android.internal.util.function.pooled.PooledLambda, java.util.function.Predicate<T> {
    @Override // com.android.internal.util.function.pooled.PooledLambda, com.android.internal.util.function.pooled.PooledSupplier, com.android.internal.util.function.pooled.PooledRunnable, com.android.internal.util.function.pooled.PooledSupplier.OfInt, com.android.internal.util.function.pooled.PooledSupplier.OfLong, com.android.internal.util.function.pooled.PooledSupplier.OfDouble
    com.android.internal.util.function.pooled.PooledPredicate<T> recycleOnUse();
}
