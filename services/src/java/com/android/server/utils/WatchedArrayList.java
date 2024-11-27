package com.android.server.utils;

/* loaded from: classes2.dex */
public class WatchedArrayList<E> extends com.android.server.utils.WatchableImpl implements com.android.server.utils.Snappable {
    private final com.android.server.utils.Watcher mObserver;
    private final java.util.ArrayList<E> mStorage;
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
                registerChild(this.mStorage.get(i));
            }
        }
    }

    @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
    public void unregisterObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        super.unregisterObserver(watcher);
        if (registeredObserverCount() == 0) {
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                unregisterChild(this.mStorage.get(i));
            }
            this.mWatching = false;
        }
    }

    public WatchedArrayList() {
        this(0);
    }

    public WatchedArrayList(int i) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArrayList.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArrayList.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new java.util.ArrayList<>(i);
    }

    public WatchedArrayList(@android.annotation.Nullable java.util.Collection<? extends E> collection) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArrayList.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArrayList.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new java.util.ArrayList<>();
        if (collection != null) {
            this.mStorage.addAll(collection);
        }
    }

    public WatchedArrayList(@android.annotation.NonNull java.util.ArrayList<E> arrayList) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArrayList.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArrayList.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new java.util.ArrayList<>(arrayList);
    }

    public WatchedArrayList(@android.annotation.NonNull com.android.server.utils.WatchedArrayList<E> watchedArrayList) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArrayList.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArrayList.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new java.util.ArrayList<>(watchedArrayList.mStorage);
    }

    public void copyFrom(@android.annotation.NonNull java.util.ArrayList<E> arrayList) {
        clear();
        int size = arrayList.size();
        this.mStorage.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            add(arrayList.get(i));
        }
    }

    public void copyTo(@android.annotation.NonNull java.util.ArrayList<E> arrayList) {
        arrayList.clear();
        int size = size();
        arrayList.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(get(i));
        }
    }

    public java.util.ArrayList<E> untrackedStorage() {
        return this.mStorage;
    }

    public boolean add(E e) {
        boolean add = this.mStorage.add(e);
        registerChild(e);
        onChanged();
        return add;
    }

    public void add(int i, E e) {
        this.mStorage.add(i, e);
        registerChild(e);
        onChanged();
    }

    public boolean addAll(java.util.Collection<? extends E> collection) {
        if (collection.size() > 0) {
            java.util.Iterator<? extends E> it = collection.iterator();
            while (it.hasNext()) {
                this.mStorage.add(it.next());
            }
            onChanged();
            return true;
        }
        return false;
    }

    public boolean addAll(int i, java.util.Collection<? extends E> collection) {
        if (collection.size() > 0) {
            java.util.Iterator<? extends E> it = collection.iterator();
            while (it.hasNext()) {
                this.mStorage.add(i, it.next());
                i++;
            }
            onChanged();
            return true;
        }
        return false;
    }

    public void clear() {
        if (this.mWatching) {
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                unregisterChild(this.mStorage.get(i));
            }
        }
        this.mStorage.clear();
        onChanged();
    }

    public boolean contains(java.lang.Object obj) {
        return this.mStorage.contains(obj);
    }

    public boolean containsAll(java.util.Collection<?> collection) {
        return this.mStorage.containsAll(collection);
    }

    public void ensureCapacity(int i) {
        this.mStorage.ensureCapacity(i);
    }

    public E get(int i) {
        return this.mStorage.get(i);
    }

    public int indexOf(java.lang.Object obj) {
        return this.mStorage.indexOf(obj);
    }

    public boolean isEmpty() {
        return this.mStorage.isEmpty();
    }

    public int lastIndexOf(java.lang.Object obj) {
        return this.mStorage.lastIndexOf(obj);
    }

    public E remove(int i) {
        E remove = this.mStorage.remove(i);
        unregisterChildIf(remove);
        onChanged();
        return remove;
    }

    public boolean remove(java.lang.Object obj) {
        if (this.mStorage.remove(obj)) {
            unregisterChildIf(obj);
            onChanged();
            return true;
        }
        return false;
    }

    public E set(int i, E e) {
        E e2 = this.mStorage.set(i, e);
        if (e != e2) {
            unregisterChildIf(e2);
            registerChild(e);
            onChanged();
        }
        return e2;
    }

    public int size() {
        return this.mStorage.size();
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (obj instanceof com.android.server.utils.WatchedArrayList) {
            return this.mStorage.equals(((com.android.server.utils.WatchedArrayList) obj).mStorage);
        }
        return false;
    }

    public int hashCode() {
        return this.mStorage.hashCode();
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.utils.WatchedArrayList<E> snapshot() {
        com.android.server.utils.WatchedArrayList<E> watchedArrayList = new com.android.server.utils.WatchedArrayList<>(size());
        snapshot(watchedArrayList, this);
        return watchedArrayList;
    }

    public void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedArrayList<E> watchedArrayList) {
        snapshot(this, watchedArrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedArrayList<E> watchedArrayList, @android.annotation.NonNull com.android.server.utils.WatchedArrayList<E> watchedArrayList2) {
        if (watchedArrayList.size() != 0) {
            throw new java.lang.IllegalArgumentException("snapshot destination is not empty");
        }
        int size = watchedArrayList2.size();
        ((com.android.server.utils.WatchedArrayList) watchedArrayList).mStorage.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            ((com.android.server.utils.WatchedArrayList) watchedArrayList).mStorage.add(com.android.server.utils.Snapshots.maybeSnapshot(watchedArrayList2.get(i)));
        }
        watchedArrayList.seal();
    }
}
