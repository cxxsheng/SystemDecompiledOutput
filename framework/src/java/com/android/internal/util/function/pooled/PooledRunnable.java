package com.android.internal.util.function.pooled;

/* loaded from: classes5.dex */
public interface PooledRunnable extends com.android.internal.util.function.pooled.PooledLambda, java.lang.Runnable, com.android.internal.util.FunctionalUtils.ThrowingRunnable, android.os.TraceNameSupplier {
    @Override // com.android.internal.util.function.pooled.PooledSupplier.OfInt, com.android.internal.util.function.pooled.PooledSupplier.OfLong, com.android.internal.util.function.pooled.PooledSupplier.OfDouble
    com.android.internal.util.function.pooled.PooledRunnable recycleOnUse();
}
