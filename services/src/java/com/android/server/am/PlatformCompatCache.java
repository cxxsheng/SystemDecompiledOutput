package com.android.server.am;

/* loaded from: classes.dex */
final class PlatformCompatCache {
    static final int CACHED_COMPAT_CHANGE_CAMERA_MICROPHONE_CAPABILITY = 1;
    static final long[] CACHED_COMPAT_CHANGE_IDS_MAPPING = {136274596, 136219221, 183972877};
    static final int CACHED_COMPAT_CHANGE_PROCESS_CAPABILITY = 0;
    static final int CACHED_COMPAT_CHANGE_USE_SHORT_FGS_USAGE_INTERACTION_TIME = 2;
    private static com.android.server.am.PlatformCompatCache sPlatformCompatCache;
    private final boolean mCacheEnabled;
    private final android.util.LongSparseArray<com.android.server.am.PlatformCompatCache.CacheItem> mCaches = new android.util.LongSparseArray<>();
    private final com.android.internal.compat.IPlatformCompat mIPlatformCompatProxy;
    private final com.android.server.compat.PlatformCompat mPlatformCompat;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface CachedCompatChangeId {
    }

    private PlatformCompatCache(long[] jArr) {
        android.os.IBinder service = android.os.ServiceManager.getService("platform_compat");
        if (service instanceof com.android.server.compat.PlatformCompat) {
            this.mPlatformCompat = (com.android.server.compat.PlatformCompat) android.os.ServiceManager.getService("platform_compat");
            for (long j : jArr) {
                this.mCaches.put(j, new com.android.server.am.PlatformCompatCache.CacheItem(this.mPlatformCompat, j));
            }
            this.mIPlatformCompatProxy = null;
            this.mCacheEnabled = true;
            return;
        }
        this.mIPlatformCompatProxy = com.android.internal.compat.IPlatformCompat.Stub.asInterface(service);
        this.mPlatformCompat = null;
        this.mCacheEnabled = false;
    }

    static com.android.server.am.PlatformCompatCache getInstance() {
        if (sPlatformCompatCache == null) {
            sPlatformCompatCache = new com.android.server.am.PlatformCompatCache(new long[]{136274596, 136219221, 183972877});
        }
        return sPlatformCompatCache;
    }

    private boolean isChangeEnabled(long j, android.content.pm.ApplicationInfo applicationInfo, boolean z) {
        try {
            return this.mCacheEnabled ? this.mCaches.get(j).isChangeEnabled(applicationInfo) : this.mIPlatformCompatProxy.isChangeEnabled(j, applicationInfo);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w("ActivityManager", "Error reading platform compat change " + j, e);
            return z;
        }
    }

    static boolean isChangeEnabled(int i, android.content.pm.ApplicationInfo applicationInfo, boolean z) {
        return getInstance().isChangeEnabled(CACHED_COMPAT_CHANGE_IDS_MAPPING[i], applicationInfo, z);
    }

    void invalidate(android.content.pm.ApplicationInfo applicationInfo) {
        for (int size = this.mCaches.size() - 1; size >= 0; size--) {
            this.mCaches.valueAt(size).invalidate(applicationInfo);
        }
    }

    void onApplicationInfoChanged(android.content.pm.ApplicationInfo applicationInfo) {
        for (int size = this.mCaches.size() - 1; size >= 0; size--) {
            this.mCaches.valueAt(size).onApplicationInfoChanged(applicationInfo);
        }
    }

    static class CacheItem implements com.android.server.compat.CompatChange.ChangeListener {
        private final long mChangeId;
        private final com.android.server.compat.PlatformCompat mPlatformCompat;
        private final java.lang.Object mLock = new java.lang.Object();
        private final android.util.ArrayMap<java.lang.String, android.util.Pair<java.lang.Boolean, java.lang.ref.WeakReference<android.content.pm.ApplicationInfo>>> mCache = new android.util.ArrayMap<>();

        CacheItem(com.android.server.compat.PlatformCompat platformCompat, long j) {
            this.mPlatformCompat = platformCompat;
            this.mChangeId = j;
            this.mPlatformCompat.registerListener(j, this);
        }

        boolean isChangeEnabled(android.content.pm.ApplicationInfo applicationInfo) {
            synchronized (this.mLock) {
                try {
                    int indexOfKey = this.mCache.indexOfKey(applicationInfo.packageName);
                    if (indexOfKey < 0) {
                        return fetchLocked(applicationInfo, indexOfKey);
                    }
                    android.util.Pair<java.lang.Boolean, java.lang.ref.WeakReference<android.content.pm.ApplicationInfo>> valueAt = this.mCache.valueAt(indexOfKey);
                    if (((java.lang.ref.WeakReference) valueAt.second).get() == applicationInfo) {
                        return ((java.lang.Boolean) valueAt.first).booleanValue();
                    }
                    return fetchLocked(applicationInfo, indexOfKey);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void invalidate(android.content.pm.ApplicationInfo applicationInfo) {
            synchronized (this.mLock) {
                this.mCache.remove(applicationInfo.packageName);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean fetchLocked(android.content.pm.ApplicationInfo applicationInfo, int i) {
            android.util.Pair<java.lang.Boolean, java.lang.ref.WeakReference<android.content.pm.ApplicationInfo>> pair = new android.util.Pair<>(java.lang.Boolean.valueOf(this.mPlatformCompat.isChangeEnabledInternalNoLogging(this.mChangeId, applicationInfo)), new java.lang.ref.WeakReference(applicationInfo));
            if (i >= 0) {
                this.mCache.setValueAt(i, pair);
            } else {
                this.mCache.put(applicationInfo.packageName, pair);
            }
            return ((java.lang.Boolean) pair.first).booleanValue();
        }

        void onApplicationInfoChanged(android.content.pm.ApplicationInfo applicationInfo) {
            synchronized (this.mLock) {
                try {
                    int indexOfKey = this.mCache.indexOfKey(applicationInfo.packageName);
                    if (indexOfKey >= 0) {
                        fetchLocked(applicationInfo, indexOfKey);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.compat.CompatChange.ChangeListener
        public void onCompatChange(java.lang.String str) {
            synchronized (this.mLock) {
                try {
                    int indexOfKey = this.mCache.indexOfKey(str);
                    if (indexOfKey >= 0) {
                        android.content.pm.ApplicationInfo applicationInfo = (android.content.pm.ApplicationInfo) ((java.lang.ref.WeakReference) this.mCache.valueAt(indexOfKey).second).get();
                        if (applicationInfo != null) {
                            fetchLocked(applicationInfo, indexOfKey);
                        } else {
                            this.mCache.removeAt(indexOfKey);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
