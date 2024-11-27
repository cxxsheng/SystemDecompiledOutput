package com.android.internal.util.function.pooled;

/* loaded from: classes5.dex */
abstract class OmniFunction<A, B, C, D, E, F, G, H, I, J, K, R> implements java.util.function.BiFunction<A, B, R>, com.android.internal.util.function.TriFunction<A, B, C, R>, com.android.internal.util.function.QuadFunction<A, B, C, D, R>, com.android.internal.util.function.QuintFunction<A, B, C, D, E, R>, com.android.internal.util.function.HexFunction<A, B, C, D, E, F, R>, com.android.internal.util.function.HeptFunction<A, B, C, D, E, F, G, R>, com.android.internal.util.function.OctFunction<A, B, C, D, E, F, G, H, R>, com.android.internal.util.function.NonaFunction<A, B, C, D, E, F, G, H, I, R>, com.android.internal.util.function.DecFunction<A, B, C, D, E, F, G, H, I, J, R>, com.android.internal.util.function.UndecFunction<A, B, C, D, E, F, G, H, I, J, K, R>, java.util.function.BiConsumer<A, B>, com.android.internal.util.function.TriConsumer<A, B, C>, com.android.internal.util.function.QuadConsumer<A, B, C, D>, com.android.internal.util.function.QuintConsumer<A, B, C, D, E>, com.android.internal.util.function.HexConsumer<A, B, C, D, E, F>, com.android.internal.util.function.HeptConsumer<A, B, C, D, E, F, G>, com.android.internal.util.function.OctConsumer<A, B, C, D, E, F, G, H>, com.android.internal.util.function.NonaConsumer<A, B, C, D, E, F, G, H, I>, com.android.internal.util.function.DecConsumer<A, B, C, D, E, F, G, H, I, J>, com.android.internal.util.function.UndecConsumer<A, B, C, D, E, F, G, H, I, J, K>, com.android.internal.util.function.pooled.PooledPredicate<A>, java.util.function.BiPredicate<A, B>, com.android.internal.util.function.TriPredicate<A, B, C>, com.android.internal.util.function.QuadPredicate<A, B, C, D>, com.android.internal.util.function.QuintPredicate<A, B, C, D, E>, com.android.internal.util.function.pooled.PooledSupplier<R>, com.android.internal.util.function.pooled.PooledRunnable, com.android.internal.util.FunctionalUtils.ThrowingRunnable, com.android.internal.util.FunctionalUtils.ThrowingSupplier<R>, com.android.internal.util.function.pooled.PooledSupplier.OfInt, com.android.internal.util.function.pooled.PooledSupplier.OfLong, com.android.internal.util.function.pooled.PooledSupplier.OfDouble {
    @Override // java.util.function.BiFunction
    public abstract <V> com.android.internal.util.function.pooled.OmniFunction<A, B, C, D, E, F, G, H, I, J, K, V> andThen(java.util.function.Function<? super R, ? extends V> function);

    abstract R invoke(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k);

    @Override // java.util.function.Predicate, java.util.function.BiPredicate
    public abstract com.android.internal.util.function.pooled.OmniFunction<A, B, C, D, E, F, G, H, I, J, K, R> negate();

    @Override // com.android.internal.util.function.pooled.PooledPredicate, com.android.internal.util.function.pooled.PooledLambda, com.android.internal.util.function.pooled.PooledSupplier, com.android.internal.util.function.pooled.PooledRunnable, com.android.internal.util.function.pooled.PooledSupplier.OfInt, com.android.internal.util.function.pooled.PooledSupplier.OfLong, com.android.internal.util.function.pooled.PooledSupplier.OfDouble
    public abstract com.android.internal.util.function.pooled.OmniFunction<A, B, C, D, E, F, G, H, I, J, K, R> recycleOnUse();

    OmniFunction() {
    }

    @Override // java.util.function.BiFunction
    public R apply(A a, B b) {
        return invoke(a, b, null, null, null, null, null, null, null, null, null);
    }

    @Override // java.util.function.BiConsumer
    public void accept(A a, B b) {
        invoke(a, b, null, null, null, null, null, null, null, null, null);
    }

    @Override // java.lang.Runnable, com.android.internal.util.FunctionalUtils.ThrowingRunnable
    public void run() {
        invoke(null, null, null, null, null, null, null, null, null, null, null);
    }

    @Override // java.util.function.Supplier, com.android.internal.util.FunctionalUtils.ThrowingSupplier
    public R get() {
        return invoke(null, null, null, null, null, null, null, null, null, null, null);
    }

    @Override // com.android.internal.util.function.QuintPredicate
    public boolean test(A a, B b, C c, D d, E e) {
        return ((java.lang.Boolean) invoke(a, b, c, d, e, null, null, null, null, null, null)).booleanValue();
    }

    @Override // com.android.internal.util.function.QuadPredicate
    public boolean test(A a, B b, C c, D d) {
        return ((java.lang.Boolean) invoke(a, b, c, d, null, null, null, null, null, null, null)).booleanValue();
    }

    @Override // com.android.internal.util.function.TriPredicate
    public boolean test(A a, B b, C c) {
        return ((java.lang.Boolean) invoke(a, b, c, null, null, null, null, null, null, null, null)).booleanValue();
    }

    @Override // java.util.function.BiPredicate
    public boolean test(A a, B b) {
        return ((java.lang.Boolean) invoke(a, b, null, null, null, null, null, null, null, null, null)).booleanValue();
    }

    @Override // java.util.function.Predicate
    public boolean test(A a) {
        return ((java.lang.Boolean) invoke(a, null, null, null, null, null, null, null, null, null, null)).booleanValue();
    }

    @Override // com.android.internal.util.function.pooled.PooledSupplier
    public com.android.internal.util.function.pooled.PooledRunnable asRunnable() {
        return this;
    }

    @Override // com.android.internal.util.function.TriFunction
    public R apply(A a, B b, C c) {
        return invoke(a, b, c, null, null, null, null, null, null, null, null);
    }

    @Override // com.android.internal.util.function.TriConsumer
    public void accept(A a, B b, C c) {
        invoke(a, b, c, null, null, null, null, null, null, null, null);
    }

    @Override // com.android.internal.util.function.QuadFunction
    public R apply(A a, B b, C c, D d) {
        return invoke(a, b, c, d, null, null, null, null, null, null, null);
    }

    @Override // com.android.internal.util.function.QuintFunction
    public R apply(A a, B b, C c, D d, E e) {
        return invoke(a, b, c, d, e, null, null, null, null, null, null);
    }

    @Override // com.android.internal.util.function.HexFunction
    public R apply(A a, B b, C c, D d, E e, F f) {
        return invoke(a, b, c, d, e, f, null, null, null, null, null);
    }

    @Override // com.android.internal.util.function.HeptFunction
    public R apply(A a, B b, C c, D d, E e, F f, G g) {
        return invoke(a, b, c, d, e, f, g, null, null, null, null);
    }

    @Override // com.android.internal.util.function.OctFunction
    public R apply(A a, B b, C c, D d, E e, F f, G g, H h) {
        return invoke(a, b, c, d, e, f, g, h, null, null, null);
    }

    @Override // com.android.internal.util.function.NonaFunction
    public R apply(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return invoke(a, b, c, d, e, f, g, h, i, null, null);
    }

    @Override // com.android.internal.util.function.DecFunction
    public R apply(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j) {
        return invoke(a, b, c, d, e, f, g, h, i, j, null);
    }

    @Override // com.android.internal.util.function.UndecFunction
    public R apply(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k) {
        return invoke(a, b, c, d, e, f, g, h, i, j, k);
    }

    @Override // com.android.internal.util.function.QuadConsumer
    public void accept(A a, B b, C c, D d) {
        invoke(a, b, c, d, null, null, null, null, null, null, null);
    }

    @Override // com.android.internal.util.function.QuintConsumer
    public void accept(A a, B b, C c, D d, E e) {
        invoke(a, b, c, d, e, null, null, null, null, null, null);
    }

    @Override // com.android.internal.util.function.HexConsumer
    public void accept(A a, B b, C c, D d, E e, F f) {
        invoke(a, b, c, d, e, f, null, null, null, null, null);
    }

    @Override // com.android.internal.util.function.HeptConsumer
    public void accept(A a, B b, C c, D d, E e, F f, G g) {
        invoke(a, b, c, d, e, f, g, null, null, null, null);
    }

    @Override // com.android.internal.util.function.OctConsumer
    public void accept(A a, B b, C c, D d, E e, F f, G g, H h) {
        invoke(a, b, c, d, e, f, g, h, null, null, null);
    }

    @Override // com.android.internal.util.function.NonaConsumer
    public void accept(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        invoke(a, b, c, d, e, f, g, h, i, null, null);
    }

    @Override // com.android.internal.util.function.DecConsumer
    public void accept(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j) {
        invoke(a, b, c, d, e, f, g, h, i, j, null);
    }

    @Override // com.android.internal.util.function.UndecConsumer
    public void accept(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k) {
        invoke(a, b, c, d, e, f, g, h, i, j, k);
    }

    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
    public void runOrThrow() throws java.lang.Exception {
        run();
    }

    @Override // com.android.internal.util.FunctionalUtils.ThrowingSupplier
    public R getOrThrow() throws java.lang.Exception {
        return get();
    }
}
