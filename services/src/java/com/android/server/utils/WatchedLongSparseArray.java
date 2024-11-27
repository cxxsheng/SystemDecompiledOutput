package com.android.server.utils;

/* loaded from: classes2.dex */
public class WatchedLongSparseArray<E> extends com.android.server.utils.WatchableImpl implements com.android.server.utils.Snappable {
    private final com.android.server.utils.Watcher mObserver;
    private final android.util.LongSparseArray<E> mStorage;
    private volatile boolean mWatching;

    private void onChanged() {
        dispatchChange(this);
    }

    private void registerChild(java.lang.Object obj) {
        if (this.mWatching && (obj instanceof com.android.server.utils.Watchable)) {
            ((com.android.server.utils.Watchable) obj).registerObserver(this.mObserver);
        }
    }

    private void unregisterChild(java.lang.Object obj) {
        if (this.mWatching && (obj instanceof com.android.server.utils.Watchable)) {
            ((com.android.server.utils.Watchable) obj).unregisterObserver(this.mObserver);
        }
    }

    private void unregisterChildIf(java.lang.Object obj) {
        if (this.mWatching && (obj instanceof com.android.server.utils.Watchable) && this.mStorage.indexOfValue(obj) == -1) {
            ((com.android.server.utils.Watchable) obj).unregisterObserver(this.mObserver);
        }
    }

    @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
    public void registerObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        super.registerObserver(watcher);
        if (registeredObserverCount() == 1) {
            this.mWatching = true;
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                registerChild(this.mStorage.valueAt(i));
            }
        }
    }

    @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
    public void unregisterObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        super.unregisterObserver(watcher);
        if (registeredObserverCount() == 0) {
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                unregisterChild(this.mStorage.valueAt(i));
            }
            this.mWatching = false;
        }
    }

    public WatchedLongSparseArray() {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedLongSparseArray.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedLongSparseArray.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new android.util.LongSparseArray<>();
    }

    public WatchedLongSparseArray(int i) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedLongSparseArray.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedLongSparseArray.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new android.util.LongSparseArray<>(i);
    }

    public WatchedLongSparseArray(@android.annotation.NonNull android.util.LongSparseArray<E> longSparseArray) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedLongSparseArray.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedLongSparseArray.this.dispatchChange(watchable);
            }
        };
        this.mStorage = longSparseArray.clone();
    }

    public WatchedLongSparseArray(@android.annotation.NonNull com.android.server.utils.WatchedLongSparseArray<E> watchedLongSparseArray) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedLongSparseArray.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedLongSparseArray.this.dispatchChange(watchable);
            }
        };
        this.mStorage = watchedLongSparseArray.mStorage.clone();
    }

    public void copyFrom(@android.annotation.NonNull android.util.LongSparseArray<E> longSparseArray) {
        clear();
        int size = longSparseArray.size();
        for (int i = 0; i < size; i++) {
            put(longSparseArray.keyAt(i), longSparseArray.valueAt(i));
        }
    }

    public void copyTo(@android.annotation.NonNull android.util.LongSparseArray<E> longSparseArray) {
        longSparseArray.clear();
        int size = size();
        for (int i = 0; i < size; i++) {
            longSparseArray.put(keyAt(i), valueAt(i));
        }
    }

    public android.util.LongSparseArray<E> untrackedStorage() {
        return this.mStorage;
    }

    public E get(long j) {
        return this.mStorage.get(j);
    }

    public E get(long j, E e) {
        return this.mStorage.get(j, e);
    }

    public void delete(long j) {
        E e = this.mStorage.get(j, null);
        this.mStorage.delete(j);
        unregisterChildIf(e);
        onChanged();
    }

    public void remove(long j) {
        delete(j);
    }

    public void removeAt(int i) {
        E valueAt = this.mStorage.valueAt(i);
        this.mStorage.removeAt(i);
        unregisterChildIf(valueAt);
        onChanged();
    }

    public void put(long j, E e) {
        E e2 = this.mStorage.get(j);
        this.mStorage.put(j, e);
        unregisterChildIf(e2);
        registerChild(e);
        onChanged();
    }

    public int size() {
        return this.mStorage.size();
    }

    public long keyAt(int i) {
        return this.mStorage.keyAt(i);
    }

    public E valueAt(int i) {
        return this.mStorage.valueAt(i);
    }

    public void setValueAt(int i, E e) {
        E valueAt = this.mStorage.valueAt(i);
        this.mStorage.setValueAt(i, e);
        unregisterChildIf(valueAt);
        registerChild(e);
        onChanged();
    }

    public int indexOfKey(long j) {
        return this.mStorage.indexOfKey(j);
    }

    public int indexOfValue(E e) {
        return this.mStorage.indexOfValue(e);
    }

    public int indexOfValueByValue(E e) {
        return this.mStorage.indexOfValueByValue(e);
    }

    public void clear() {
        int size = this.mStorage.size();
        for (int i = 0; i < size; i++) {
            unregisterChild(this.mStorage.valueAt(i));
        }
        this.mStorage.clear();
        onChanged();
    }

    public void append(long j, E e) {
        this.mStorage.append(j, e);
        registerChild(e);
        onChanged();
    }

    public java.lang.String toString() {
        return this.mStorage.toString();
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.utils.WatchedLongSparseArray<E> snapshot() {
        com.android.server.utils.WatchedLongSparseArray<E> watchedLongSparseArray = new com.android.server.utils.WatchedLongSparseArray<>(size());
        snapshot(watchedLongSparseArray, this);
        return watchedLongSparseArray;
    }

    public void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedLongSparseArray<E> watchedLongSparseArray) {
        snapshot(this, watchedLongSparseArray);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedLongSparseArray<E> watchedLongSparseArray, @android.annotation.NonNull com.android.server.utils.WatchedLongSparseArray<E> watchedLongSparseArray2) {
        if (watchedLongSparseArray.size() != 0) {
            throw new java.lang.IllegalArgumentException("snapshot destination is not empty");
        }
        int size = watchedLongSparseArray2.size();
        for (int i = 0; i < size; i++) {
            java.lang.Object maybeSnapshot = com.android.server.utils.Snapshots.maybeSnapshot(watchedLongSparseArray2.valueAt(i));
            ((com.android.server.utils.WatchedLongSparseArray) watchedLongSparseArray).mStorage.put(watchedLongSparseArray2.keyAt(i), maybeSnapshot);
        }
        watchedLongSparseArray.seal();
    }
}
