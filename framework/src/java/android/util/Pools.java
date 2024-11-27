package android.util;

/* loaded from: classes3.dex */
public final class Pools {

    public interface Pool<T> {
        T acquire();

        boolean release(T t);
    }

    private Pools() {
    }

    public static class SimplePool<T> implements android.util.Pools.Pool<T> {
        private final java.lang.Object[] mPool;
        private int mPoolSize;

        public SimplePool(int i) {
            if (i <= 0) {
                throw new java.lang.IllegalArgumentException("The max pool size must be > 0");
            }
            this.mPool = new java.lang.Object[i];
        }

        @Override // android.util.Pools.Pool
        public T acquire() {
            if (this.mPoolSize <= 0) {
                return null;
            }
            int i = this.mPoolSize - 1;
            T t = (T) this.mPool[i];
            this.mPool[i] = null;
            this.mPoolSize--;
            return t;
        }

        @Override // android.util.Pools.Pool
        public boolean release(T t) {
            if (isInPool(t)) {
                throw new java.lang.IllegalStateException("Already in the pool!");
            }
            if (this.mPoolSize < this.mPool.length) {
                this.mPool[this.mPoolSize] = t;
                this.mPoolSize++;
                return true;
            }
            return false;
        }

        private boolean isInPool(T t) {
            for (int i = 0; i < this.mPoolSize; i++) {
                if (this.mPool[i] == t) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class SynchronizedPool<T> extends android.util.Pools.SimplePool<T> {
        private final java.lang.Object mLock;

        public SynchronizedPool(int i, java.lang.Object obj) {
            super(i);
            this.mLock = obj;
        }

        public SynchronizedPool(int i) {
            this(i, new java.lang.Object());
        }

        @Override // android.util.Pools.SimplePool, android.util.Pools.Pool
        public T acquire() {
            T t;
            synchronized (this.mLock) {
                t = (T) super.acquire();
            }
            return t;
        }

        @Override // android.util.Pools.SimplePool, android.util.Pools.Pool
        public boolean release(T t) {
            boolean release;
            synchronized (this.mLock) {
                release = super.release(t);
            }
            return release;
        }
    }
}
