package com.android.server.utils;

/* loaded from: classes2.dex */
public abstract class SnapshotCache<T> extends com.android.server.utils.Watcher {
    private static final boolean ENABLED = true;
    private static final java.util.WeakHashMap<com.android.server.utils.SnapshotCache, java.lang.Void> sCaches = new java.util.WeakHashMap<>();
    private volatile boolean mSealed;
    private volatile T mSnapshot;
    protected final T mSource;
    private final com.android.server.utils.SnapshotCache.Statistics mStatistics;

    public abstract T createSnapshot();

    private static class Statistics {
        final java.lang.String mName;
        private final java.util.concurrent.atomic.AtomicInteger mReused = new java.util.concurrent.atomic.AtomicInteger(0);
        private final java.util.concurrent.atomic.AtomicInteger mRebuilt = new java.util.concurrent.atomic.AtomicInteger(0);

        Statistics(@android.annotation.NonNull java.lang.String str) {
            this.mName = str;
        }
    }

    public SnapshotCache(@android.annotation.Nullable T t, @android.annotation.NonNull com.android.server.utils.Watchable watchable, @android.annotation.Nullable java.lang.String str) {
        this.mSnapshot = null;
        this.mSealed = false;
        this.mSource = t;
        watchable.registerObserver(this);
        if (str != null) {
            this.mStatistics = new com.android.server.utils.SnapshotCache.Statistics(str);
            sCaches.put(this, null);
        } else {
            this.mStatistics = null;
        }
    }

    public SnapshotCache(@android.annotation.Nullable T t, @android.annotation.NonNull com.android.server.utils.Watchable watchable) {
        this(t, watchable, null);
    }

    public SnapshotCache() {
        this.mSnapshot = null;
        this.mSealed = false;
        this.mSource = null;
        this.mSealed = true;
        this.mStatistics = null;
    }

    @Override // com.android.server.utils.Watcher
    public final void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
        if (this.mSealed) {
            throw new java.lang.IllegalStateException("attempt to change a sealed object");
        }
        this.mSnapshot = null;
    }

    public final void seal() {
        this.mSealed = true;
    }

    public final T snapshot() {
        T t = this.mSnapshot;
        if (t == null) {
            t = createSnapshot();
            this.mSnapshot = t;
            if (this.mStatistics != null) {
                this.mStatistics.mRebuilt.incrementAndGet();
            }
        } else if (this.mStatistics != null) {
            this.mStatistics.mReused.incrementAndGet();
        }
        return t;
    }

    public static class Sealed<T> extends com.android.server.utils.SnapshotCache<T> {
        @Override // com.android.server.utils.SnapshotCache
        public T createSnapshot() {
            throw new java.lang.UnsupportedOperationException("cannot snapshot a sealed snaphot");
        }
    }

    public static class Auto<T extends com.android.server.utils.Snappable<T>> extends com.android.server.utils.SnapshotCache<T> {
        public Auto(@android.annotation.NonNull T t, @android.annotation.NonNull com.android.server.utils.Watchable watchable, @android.annotation.Nullable java.lang.String str) {
            super(t, watchable, str);
        }

        public Auto(@android.annotation.NonNull T t, @android.annotation.NonNull com.android.server.utils.Watchable watchable) {
            this(t, watchable, null);
        }

        @Override // com.android.server.utils.SnapshotCache
        public T createSnapshot() {
            return (T) ((com.android.server.utils.Snappable) this.mSource).snapshot();
        }
    }
}
