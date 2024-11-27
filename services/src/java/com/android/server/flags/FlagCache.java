package com.android.server.flags;

/* loaded from: classes.dex */
public class FlagCache<V> {
    private final java.util.function.Function<java.lang.String, java.util.HashMap<java.lang.String, V>> mNewHashMap = new java.util.function.Function() { // from class: com.android.server.flags.FlagCache$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final java.lang.Object apply(java.lang.Object obj) {
            java.util.HashMap lambda$new$0;
            lambda$new$0 = com.android.server.flags.FlagCache.lambda$new$0((java.lang.String) obj);
            return lambda$new$0;
        }
    };
    final java.util.Map<java.lang.String, java.util.Map<java.lang.String, V>> mCache = new java.util.HashMap();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.HashMap lambda$new$0(java.lang.String str) {
        return new java.util.HashMap();
    }

    FlagCache() {
    }

    boolean containsNamespace(java.lang.String str) {
        boolean containsKey;
        synchronized (this.mCache) {
            containsKey = this.mCache.containsKey(str);
        }
        return containsKey;
    }

    boolean contains(java.lang.String str, java.lang.String str2) {
        boolean z;
        synchronized (this.mCache) {
            try {
                java.util.Map<java.lang.String, V> map = this.mCache.get(str);
                z = map != null && map.containsKey(str2);
            } finally {
            }
        }
        return z;
    }

    boolean setIfChanged(java.lang.String str, java.lang.String str2, V v) {
        synchronized (this.mCache) {
            try {
                java.util.Map<java.lang.String, V> computeIfAbsent = this.mCache.computeIfAbsent(str, this.mNewHashMap);
                V v2 = computeIfAbsent.get(str2);
                if (v2 != null && v2.equals(v)) {
                    return false;
                }
                computeIfAbsent.put(str2, v);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    V getOrSet(java.lang.String str, java.lang.String str2, V v) {
        synchronized (this.mCache) {
            V putIfAbsent = this.mCache.computeIfAbsent(str, this.mNewHashMap).putIfAbsent(str2, v);
            if (putIfAbsent != null) {
                v = putIfAbsent;
            }
        }
        return v;
    }

    V getOrNull(java.lang.String str, java.lang.String str2) {
        synchronized (this.mCache) {
            try {
                java.util.Map<java.lang.String, V> map = this.mCache.get(str);
                if (map == null) {
                    return null;
                }
                return map.get(str2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
