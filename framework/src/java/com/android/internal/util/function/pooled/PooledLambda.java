package com.android.internal.util.function.pooled;

/* loaded from: classes5.dex */
public interface PooledLambda {
    void recycle();

    com.android.internal.util.function.pooled.PooledLambda recycleOnUse();

    static <R> com.android.internal.util.function.pooled.ArgumentPlaceholder<R> __() {
        return (com.android.internal.util.function.pooled.ArgumentPlaceholder<R>) com.android.internal.util.function.pooled.ArgumentPlaceholder.INSTANCE;
    }

    static <R> com.android.internal.util.function.pooled.ArgumentPlaceholder<R> __(java.lang.Class<R> cls) {
        return __();
    }

    static <R> com.android.internal.util.function.pooled.PooledSupplier<R> obtainSupplier(R r) {
        com.android.internal.util.function.pooled.PooledLambdaImpl acquireConstSupplier = com.android.internal.util.function.pooled.PooledLambdaImpl.acquireConstSupplier(3);
        acquireConstSupplier.mFunc = r;
        return acquireConstSupplier;
    }

    static com.android.internal.util.function.pooled.PooledSupplier.OfInt obtainSupplier(int i) {
        com.android.internal.util.function.pooled.PooledLambdaImpl acquireConstSupplier = com.android.internal.util.function.pooled.PooledLambdaImpl.acquireConstSupplier(4);
        acquireConstSupplier.mConstValue = i;
        return acquireConstSupplier;
    }

    static com.android.internal.util.function.pooled.PooledSupplier.OfLong obtainSupplier(long j) {
        com.android.internal.util.function.pooled.PooledLambdaImpl acquireConstSupplier = com.android.internal.util.function.pooled.PooledLambdaImpl.acquireConstSupplier(5);
        acquireConstSupplier.mConstValue = j;
        return acquireConstSupplier;
    }

    static com.android.internal.util.function.pooled.PooledSupplier.OfDouble obtainSupplier(double d) {
        com.android.internal.util.function.pooled.PooledLambdaImpl acquireConstSupplier = com.android.internal.util.function.pooled.PooledLambdaImpl.acquireConstSupplier(6);
        acquireConstSupplier.mConstValue = java.lang.Double.doubleToRawLongBits(d);
        return acquireConstSupplier;
    }

    static <A> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(java.util.function.Consumer<? super A> consumer, A a) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, consumer, 1, 0, 1, a, null, null, null, null, null, null, null, null, null, null, null);
    }

    static <A> android.os.Message obtainMessage(java.util.function.Consumer<? super A> consumer, A a) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, consumer, 1, 0, 1, a, null, null, null, null, null, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(java.util.function.BiConsumer<? super A, ? super B> biConsumer, A a, B b) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, biConsumer, 2, 0, 1, a, b, null, null, null, null, null, null, null, null, null, null);
    }

    static <A, B> com.android.internal.util.function.pooled.PooledPredicate<A> obtainPredicate(java.util.function.BiPredicate<? super A, ? super B> biPredicate, com.android.internal.util.function.pooled.ArgumentPlaceholder<A> argumentPlaceholder, B b) {
        return (com.android.internal.util.function.pooled.PooledPredicate) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, biPredicate, 2, 1, 2, argumentPlaceholder, b, null, null, null, null, null, null, null, null, null, null);
    }

    static <A, B, C> com.android.internal.util.function.pooled.PooledPredicate<A> obtainPredicate(com.android.internal.util.function.TriPredicate<? super A, ? super B, ? super C> triPredicate, com.android.internal.util.function.pooled.ArgumentPlaceholder<A> argumentPlaceholder, B b, C c) {
        return (com.android.internal.util.function.pooled.PooledPredicate) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, triPredicate, 3, 1, 2, argumentPlaceholder, b, c, null, null, null, null, null, null, null, null, null);
    }

    static <A, B, C, D> com.android.internal.util.function.pooled.PooledPredicate<A> obtainPredicate(com.android.internal.util.function.QuadPredicate<? super A, ? super B, ? super C, ? super D> quadPredicate, com.android.internal.util.function.pooled.ArgumentPlaceholder<A> argumentPlaceholder, B b, C c, D d) {
        return (com.android.internal.util.function.pooled.PooledPredicate) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, quadPredicate, 4, 1, 2, argumentPlaceholder, b, c, d, null, null, null, null, null, null, null, null);
    }

    static <A, B, C, D, E> com.android.internal.util.function.pooled.PooledPredicate<A> obtainPredicate(com.android.internal.util.function.QuintPredicate<? super A, ? super B, ? super C, ? super D, ? super E> quintPredicate, com.android.internal.util.function.pooled.ArgumentPlaceholder<A> argumentPlaceholder, B b, C c, D d, E e) {
        return (com.android.internal.util.function.pooled.PooledPredicate) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, quintPredicate, 5, 1, 2, argumentPlaceholder, b, c, d, e, null, null, null, null, null, null, null);
    }

    static <A, B> com.android.internal.util.function.pooled.PooledPredicate<B> obtainPredicate(java.util.function.BiPredicate<? super A, ? super B> biPredicate, A a, com.android.internal.util.function.pooled.ArgumentPlaceholder<B> argumentPlaceholder) {
        return (com.android.internal.util.function.pooled.PooledPredicate) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, biPredicate, 2, 1, 2, a, argumentPlaceholder, null, null, null, null, null, null, null, null, null, null);
    }

    static <A, B> android.os.Message obtainMessage(java.util.function.BiConsumer<? super A, ? super B> biConsumer, A a, B b) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, biConsumer, 2, 0, 1, a, b, null, null, null, null, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(com.android.internal.util.function.TriConsumer<? super A, ? super B, ? super C> triConsumer, A a, B b, C c) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, triConsumer, 3, 0, 1, a, b, c, null, null, null, null, null, null, null, null, null);
    }

    static <A, B, C> android.os.Message obtainMessage(com.android.internal.util.function.TriConsumer<? super A, ? super B, ? super C> triConsumer, A a, B b, C c) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, triConsumer, 3, 0, 1, a, b, c, null, null, null, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(com.android.internal.util.function.QuadConsumer<? super A, ? super B, ? super C, ? super D> quadConsumer, A a, B b, C c, D d) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, quadConsumer, 4, 0, 1, a, b, c, d, null, null, null, null, null, null, null, null);
    }

    static <A, B, C, D> android.os.Message obtainMessage(com.android.internal.util.function.QuadConsumer<? super A, ? super B, ? super C, ? super D> quadConsumer, A a, B b, C c, D d) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, quadConsumer, 4, 0, 1, a, b, c, d, null, null, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(com.android.internal.util.function.QuintConsumer<? super A, ? super B, ? super C, ? super D, ? super E> quintConsumer, A a, B b, C c, D d, E e) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, quintConsumer, 5, 0, 1, a, b, c, d, e, null, null, null, null, null, null, null);
    }

    static <A, B, C, D, E> android.os.Message obtainMessage(com.android.internal.util.function.QuintConsumer<? super A, ? super B, ? super C, ? super D, ? super E> quintConsumer, A a, B b, C c, D d, E e) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, quintConsumer, 5, 0, 1, a, b, c, d, e, null, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(com.android.internal.util.function.HexConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> hexConsumer, A a, B b, C c, D d, E e, F f) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, hexConsumer, 6, 0, 1, a, b, c, d, e, f, null, null, null, null, null, null);
    }

    static <A, B, C, D, E, F> android.os.Message obtainMessage(com.android.internal.util.function.HexConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> hexConsumer, A a, B b, C c, D d, E e, F f) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, hexConsumer, 6, 0, 1, a, b, c, d, e, f, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(com.android.internal.util.function.HeptConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G> heptConsumer, A a, B b, C c, D d, E e, F f, G g) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, heptConsumer, 7, 0, 1, a, b, c, d, e, f, g, null, null, null, null, null);
    }

    static <A, B, C, D, E, F, G> android.os.Message obtainMessage(com.android.internal.util.function.HeptConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G> heptConsumer, A a, B b, C c, D d, E e, F f, G g) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, heptConsumer, 7, 0, 1, a, b, c, d, e, f, g, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G, H> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(com.android.internal.util.function.OctConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H> octConsumer, A a, B b, C c, D d, E e, F f, G g, H h) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, octConsumer, 8, 0, 1, a, b, c, d, e, f, g, h, null, null, null, null);
    }

    static <A, B, C, D, E, F, G, H> android.os.Message obtainMessage(com.android.internal.util.function.OctConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H> octConsumer, A a, B b, C c, D d, E e, F f, G g, H h) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, octConsumer, 8, 0, 1, a, b, c, d, e, f, g, h, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G, H, I> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(com.android.internal.util.function.NonaConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I> nonaConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, nonaConsumer, 9, 0, 1, a, b, c, d, e, f, g, h, i, null, null, null);
    }

    static <A, B, C, D, E, F, G, H, I> android.os.Message obtainMessage(com.android.internal.util.function.NonaConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I> nonaConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, nonaConsumer, 9, 0, 1, a, b, c, d, e, f, g, h, i, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G, H, I, J> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(com.android.internal.util.function.DecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J> decConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, decConsumer, 10, 0, 1, a, b, c, d, e, f, g, h, i, j, null, null);
    }

    static <A, B, C, D, E, F, G, H, I, J> android.os.Message obtainMessage(com.android.internal.util.function.DecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J> decConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, decConsumer, 10, 0, 1, a, b, c, d, e, f, g, h, i, j, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G, H, I, J, K> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(com.android.internal.util.function.UndecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J, ? super K> undecConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, undecConsumer, 11, 0, 1, a, b, c, d, e, f, g, h, i, j, k, null);
    }

    static <A, B, C, D, E, F, G, H, I, J, K> android.os.Message obtainMessage(com.android.internal.util.function.UndecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J, ? super K> undecConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, undecConsumer, 11, 0, 1, a, b, c, d, e, f, g, h, i, j, k, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G, H, I, J, K, L> com.android.internal.util.function.pooled.PooledRunnable obtainRunnable(com.android.internal.util.function.DodecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J, ? super K, ? super L> dodecConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l) {
        return (com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sPool, dodecConsumer, 12, 0, 1, a, b, c, d, e, f, g, h, i, j, k, l);
    }

    static <A, B, C, D, E, F, G, H, I, J, K, L> android.os.Message obtainMessage(com.android.internal.util.function.DodecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J, ? super K, ? super L> dodecConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l) {
        android.os.Message callback;
        synchronized (android.os.Message.sPoolSync) {
            callback = android.os.Message.obtain().setCallback(((com.android.internal.util.function.pooled.PooledRunnable) com.android.internal.util.function.pooled.PooledLambdaImpl.acquire(com.android.internal.util.function.pooled.PooledLambdaImpl.sMessageCallbacksPool, dodecConsumer, 12, 0, 1, a, b, c, d, e, f, g, h, i, j, k, l)).recycleOnUse());
        }
        return callback;
    }
}
