package com.google.android.mms.util;

/* loaded from: classes5.dex */
public abstract class AbstractCache<K, V> {
    private static final boolean DEBUG = false;
    private static final boolean LOCAL_LOGV = false;
    private static final int MAX_CACHED_ITEMS = 500;
    private static final java.lang.String TAG = "AbstractCache";
    private final java.util.HashMap<K, com.google.android.mms.util.AbstractCache.CacheEntry<V>> mCacheMap = new java.util.HashMap<>();

    protected AbstractCache() {
    }

    public boolean put(K k, V v) {
        if (this.mCacheMap.size() >= 500 || k == null) {
            return false;
        }
        com.google.android.mms.util.AbstractCache.CacheEntry<V> cacheEntry = new com.google.android.mms.util.AbstractCache.CacheEntry<>();
        cacheEntry.value = v;
        this.mCacheMap.put(k, cacheEntry);
        return true;
    }

    public V get(K k) {
        com.google.android.mms.util.AbstractCache.CacheEntry<V> cacheEntry;
        if (k != null && (cacheEntry = this.mCacheMap.get(k)) != null) {
            cacheEntry.hit++;
            return cacheEntry.value;
        }
        return null;
    }

    public V purge(K k) {
        com.google.android.mms.util.AbstractCache.CacheEntry<V> remove = this.mCacheMap.remove(k);
        if (remove != null) {
            return remove.value;
        }
        return null;
    }

    public void purgeAll() {
        this.mCacheMap.clear();
    }

    public int size() {
        return this.mCacheMap.size();
    }

    private static class CacheEntry<V> {
        int hit;
        V value;

        private CacheEntry() {
        }
    }
}
