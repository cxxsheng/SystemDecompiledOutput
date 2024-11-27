package com.android.server.utils;

/* loaded from: classes2.dex */
public class WatchedArrayMap<K, V> extends com.android.server.utils.WatchableImpl implements java.util.Map<K, V>, com.android.server.utils.Snappable {
    private final com.android.server.utils.Watcher mObserver;
    private final android.util.ArrayMap<K, V> mStorage;
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
        if (this.mWatching && (obj instanceof com.android.server.utils.Watchable) && !this.mStorage.containsValue(obj)) {
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

    public WatchedArrayMap() {
        this(0, false);
    }

    public WatchedArrayMap(int i) {
        this(i, false);
    }

    public WatchedArrayMap(int i, boolean z) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArrayMap.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArrayMap.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new android.util.ArrayMap<>(i, z);
    }

    public WatchedArrayMap(@android.annotation.Nullable java.util.Map<? extends K, ? extends V> map) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArrayMap.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArrayMap.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new android.util.ArrayMap<>();
        if (map != null) {
            putAll(map);
        }
    }

    public WatchedArrayMap(@android.annotation.NonNull android.util.ArrayMap<K, V> arrayMap) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArrayMap.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArrayMap.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new android.util.ArrayMap<>(arrayMap);
    }

    public WatchedArrayMap(@android.annotation.NonNull com.android.server.utils.WatchedArrayMap<K, V> watchedArrayMap) {
        this.mWatching = false;
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.utils.WatchedArrayMap.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.utils.WatchedArrayMap.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new android.util.ArrayMap<>(watchedArrayMap.mStorage);
    }

    public void copyFrom(@android.annotation.NonNull android.util.ArrayMap<K, V> arrayMap) {
        clear();
        int size = arrayMap.size();
        this.mStorage.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            put(arrayMap.keyAt(i), arrayMap.valueAt(i));
        }
    }

    public void copyTo(@android.annotation.NonNull android.util.ArrayMap<K, V> arrayMap) {
        arrayMap.clear();
        int size = size();
        arrayMap.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            arrayMap.put(keyAt(i), valueAt(i));
        }
    }

    public android.util.ArrayMap<K, V> untrackedStorage() {
        return this.mStorage;
    }

    @Override // java.util.Map
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

    @Override // java.util.Map
    public boolean containsKey(java.lang.Object obj) {
        return this.mStorage.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(java.lang.Object obj) {
        return this.mStorage.containsValue(obj);
    }

    @Override // java.util.Map
    public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
        return java.util.Collections.unmodifiableSet(this.mStorage.entrySet());
    }

    @Override // java.util.Map
    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (obj instanceof com.android.server.utils.WatchedArrayMap) {
            return this.mStorage.equals(((com.android.server.utils.WatchedArrayMap) obj).mStorage);
        }
        return false;
    }

    @Override // java.util.Map
    public V get(java.lang.Object obj) {
        return this.mStorage.get(obj);
    }

    @Override // java.util.Map
    public int hashCode() {
        return this.mStorage.hashCode();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.mStorage.isEmpty();
    }

    @Override // java.util.Map
    public java.util.Set<K> keySet() {
        return java.util.Collections.unmodifiableSet(this.mStorage.keySet());
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        V put = this.mStorage.put(k, v);
        registerChild(v);
        onChanged();
        return put;
    }

    @Override // java.util.Map
    public void putAll(@android.annotation.NonNull java.util.Map<? extends K, ? extends V> map) {
        for (java.util.Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public V remove(@android.annotation.NonNull java.lang.Object obj) {
        V remove = this.mStorage.remove(obj);
        unregisterChildIf(remove);
        onChanged();
        return remove;
    }

    @Override // java.util.Map
    public int size() {
        return this.mStorage.size();
    }

    @Override // java.util.Map
    public java.util.Collection<V> values() {
        return java.util.Collections.unmodifiableCollection(this.mStorage.values());
    }

    public K keyAt(int i) {
        return this.mStorage.keyAt(i);
    }

    public V valueAt(int i) {
        return this.mStorage.valueAt(i);
    }

    public int indexOfKey(K k) {
        return this.mStorage.indexOfKey(k);
    }

    public int indexOfValue(V v) {
        return this.mStorage.indexOfValue(v);
    }

    public V setValueAt(int i, V v) {
        V valueAt = this.mStorage.setValueAt(i, v);
        if (v != valueAt) {
            unregisterChildIf(valueAt);
            registerChild(v);
            onChanged();
        }
        return valueAt;
    }

    public V removeAt(int i) {
        V removeAt = this.mStorage.removeAt(i);
        unregisterChildIf(removeAt);
        onChanged();
        return removeAt;
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.utils.WatchedArrayMap<K, V> snapshot() {
        com.android.server.utils.WatchedArrayMap<K, V> watchedArrayMap = new com.android.server.utils.WatchedArrayMap<>();
        snapshot(watchedArrayMap, this);
        return watchedArrayMap;
    }

    public void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedArrayMap<K, V> watchedArrayMap) {
        snapshot(this, watchedArrayMap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedArrayMap<K, V> watchedArrayMap, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<K, V> watchedArrayMap2) {
        if (watchedArrayMap.size() != 0) {
            throw new java.lang.IllegalArgumentException("snapshot destination is not empty");
        }
        int size = watchedArrayMap2.size();
        ((com.android.server.utils.WatchedArrayMap) watchedArrayMap).mStorage.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            java.lang.Object maybeSnapshot = com.android.server.utils.Snapshots.maybeSnapshot(watchedArrayMap2.valueAt(i));
            ((com.android.server.utils.WatchedArrayMap) watchedArrayMap).mStorage.put(watchedArrayMap2.keyAt(i), maybeSnapshot);
        }
        watchedArrayMap.seal();
    }
}
