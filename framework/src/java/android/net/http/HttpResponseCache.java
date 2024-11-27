package android.net.http;

/* loaded from: classes2.dex */
public final class HttpResponseCache extends java.net.ResponseCache implements com.android.okhttp.internalandroidapi.HasCacheHolder, java.io.Closeable {
    private final com.android.okhttp.internalandroidapi.AndroidResponseCacheAdapter mDelegate;

    private HttpResponseCache(com.android.okhttp.internalandroidapi.AndroidResponseCacheAdapter androidResponseCacheAdapter) {
        this.mDelegate = androidResponseCacheAdapter;
    }

    public static android.net.http.HttpResponseCache getInstalled() {
        java.net.ResponseCache responseCache = java.net.ResponseCache.getDefault();
        if (responseCache instanceof android.net.http.HttpResponseCache) {
            return (android.net.http.HttpResponseCache) responseCache;
        }
        return null;
    }

    public static synchronized android.net.http.HttpResponseCache install(java.io.File file, long j) throws java.io.IOException {
        synchronized (android.net.http.HttpResponseCache.class) {
            java.net.ResponseCache responseCache = java.net.ResponseCache.getDefault();
            if (responseCache instanceof android.net.http.HttpResponseCache) {
                android.net.http.HttpResponseCache httpResponseCache = (android.net.http.HttpResponseCache) responseCache;
                if (httpResponseCache.getCacheHolder().isEquivalent(file, j)) {
                    return httpResponseCache;
                }
                httpResponseCache.close();
            }
            android.net.http.HttpResponseCache httpResponseCache2 = new android.net.http.HttpResponseCache(new com.android.okhttp.internalandroidapi.AndroidResponseCacheAdapter(com.android.okhttp.internalandroidapi.HasCacheHolder.CacheHolder.create(file, j)));
            java.net.ResponseCache.setDefault(httpResponseCache2);
            return httpResponseCache2;
        }
    }

    @Override // java.net.ResponseCache
    public java.net.CacheResponse get(java.net.URI uri, java.lang.String str, java.util.Map<java.lang.String, java.util.List<java.lang.String>> map) throws java.io.IOException {
        return this.mDelegate.get(uri, str, map);
    }

    @Override // java.net.ResponseCache
    public java.net.CacheRequest put(java.net.URI uri, java.net.URLConnection uRLConnection) throws java.io.IOException {
        return this.mDelegate.put(uri, uRLConnection);
    }

    public long size() {
        try {
            return this.mDelegate.getSize();
        } catch (java.io.IOException e) {
            return -1L;
        }
    }

    public long maxSize() {
        return this.mDelegate.getMaxSize();
    }

    public void flush() {
        try {
            this.mDelegate.flush();
        } catch (java.io.IOException e) {
        }
    }

    public int getNetworkCount() {
        return this.mDelegate.getNetworkCount();
    }

    public int getHitCount() {
        return this.mDelegate.getHitCount();
    }

    public int getRequestCount() {
        return this.mDelegate.getRequestCount();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        if (java.net.ResponseCache.getDefault() == this) {
            java.net.ResponseCache.setDefault(null);
        }
        this.mDelegate.close();
    }

    public void delete() throws java.io.IOException {
        if (java.net.ResponseCache.getDefault() == this) {
            java.net.ResponseCache.setDefault(null);
        }
        this.mDelegate.delete();
    }

    public com.android.okhttp.internalandroidapi.HasCacheHolder.CacheHolder getCacheHolder() {
        return this.mDelegate.getCacheHolder();
    }
}
