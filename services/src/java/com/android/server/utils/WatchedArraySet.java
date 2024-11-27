package com.android.server.utils;

/* loaded from: classes2.dex */
public class WatchedArraySet<E> extends com.android.server.utils.WatchableImpl implements com.android.server.utils.Snappable {
    private final com.android.server.utils.Watcher mObserver;
    private final android.util.ArraySet<E> mStorage;
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
        if (this.mWatching && (obj instanceof com.android.server.utils.Watchable) && !this.mStorage.contains(obj)) {
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

    public WatchedArraySet() {
        this(0, false);
    }

    public WatchedArraySet(int i) {
        this(i, false);
    }

    public WatchedArraySet(int i, boolean z) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArraySet.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArraySet.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new android.util.ArraySet<>(i, z);
    }

    public WatchedArraySet(@android.annotation.Nullable E[] eArr) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArraySet.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArraySet.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new android.util.ArraySet<>(eArr);
    }

    public WatchedArraySet(@android.annotation.NonNull android.util.ArraySet<E> arraySet) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArraySet.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArraySet.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new android.util.ArraySet<>((android.util.ArraySet) arraySet);
    }

    public WatchedArraySet(@android.annotation.NonNull com.android.server.utils.WatchedArraySet<E> watchedArraySet) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArraySet.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArraySet.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new android.util.ArraySet<>((android.util.ArraySet) watchedArraySet.mStorage);
    }

    public void copyFrom(@android.annotation.NonNull android.util.ArraySet<E> arraySet) {
        clear();
        int size = arraySet.size();
        this.mStorage.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            add(arraySet.valueAt(i));
        }
    }

    public void copyTo(@android.annotation.NonNull android.util.ArraySet<E> arraySet) {
        arraySet.clear();
        int size = size();
        arraySet.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            arraySet.add(valueAt(i));
        }
    }

    public android.util.ArraySet<E> untrackedStorage() {
        return this.mStorage;
    }

    public void clear() {
        if (this.mWatching) {
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                unregisterChild(this.mStorage.valueAt(i));
            }
        }
        this.mStorage.clear();
        onChanged();
    }

    public boolean contains(java.lang.Object obj) {
        return this.mStorage.contains(obj);
    }

    public int indexOf(java.lang.Object obj) {
        return this.mStorage.indexOf(obj);
    }

    public E valueAt(int i) {
        return this.mStorage.valueAt(i);
    }

    public boolean isEmpty() {
        return this.mStorage.isEmpty();
    }

    public boolean add(E e) {
        boolean add = this.mStorage.add(e);
        registerChild(e);
        onChanged();
        return add;
    }

    public void append(E e) {
        this.mStorage.append(e);
        registerChild(e);
        onChanged();
    }

    public void addAll(java.util.Collection<? extends E> collection) {
        this.mStorage.addAll(collection);
        onChanged();
    }

    public void addAll(com.android.server.utils.WatchedArraySet<? extends E> watchedArraySet) {
        int size = watchedArraySet.size();
        for (int i = 0; i < size; i++) {
            add(watchedArraySet.valueAt(i));
        }
    }

    public boolean remove(java.lang.Object obj) {
        if (this.mStorage.remove(obj)) {
            unregisterChildIf(obj);
            onChanged();
            return true;
        }
        return false;
    }

    public E removeAt(int i) {
        E removeAt = this.mStorage.removeAt(i);
        unregisterChildIf(removeAt);
        onChanged();
        return removeAt;
    }

    public boolean removeAll(android.util.ArraySet<? extends E> arraySet) {
        int size = arraySet.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            z = remove(arraySet.valueAt(i)) || z;
        }
        return z;
    }

    public int size() {
        return this.mStorage.size();
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (obj instanceof com.android.server.utils.WatchedArraySet) {
            return this.mStorage.equals(((com.android.server.utils.WatchedArraySet) obj).mStorage);
        }
        return this.mStorage.equals(obj);
    }

    public int hashCode() {
        return this.mStorage.hashCode();
    }

    public java.lang.String toString() {
        return this.mStorage.toString();
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.utils.WatchedArraySet<E> snapshot() {
        com.android.server.utils.WatchedArraySet<E> watchedArraySet = new com.android.server.utils.WatchedArraySet<>();
        snapshot(watchedArraySet, this);
        return watchedArraySet;
    }

    public void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedArraySet<E> watchedArraySet) {
        snapshot(this, watchedArraySet);
    }

    public static <E> void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedArraySet<E> watchedArraySet, @android.annotation.NonNull com.android.server.utils.WatchedArraySet<E> watchedArraySet2) {
        if (watchedArraySet.size() != 0) {
            throw new java.lang.IllegalArgumentException("snapshot destination is not empty");
        }
        int size = watchedArraySet2.size();
        ((com.android.server.utils.WatchedArraySet) watchedArraySet).mStorage.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            ((com.android.server.utils.WatchedArraySet) watchedArraySet).mStorage.append(com.android.server.utils.Snapshots.maybeSnapshot(watchedArraySet2.valueAt(i)));
        }
        watchedArraySet.seal();
    }
}
