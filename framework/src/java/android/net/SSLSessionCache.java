package android.net;

/* loaded from: classes2.dex */
public final class SSLSessionCache {
    private static final java.lang.String TAG = "SSLSessionCache";
    final com.android.org.conscrypt.SSLClientSessionCache mSessionCache;

    public static void install(android.net.SSLSessionCache sSLSessionCache, javax.net.ssl.SSLContext sSLContext) {
        com.android.org.conscrypt.ClientSessionContext clientSessionContext = sSLContext.getClientSessionContext();
        if (clientSessionContext instanceof com.android.org.conscrypt.ClientSessionContext) {
            clientSessionContext.setPersistentCache(sSLSessionCache == null ? null : sSLSessionCache.mSessionCache);
            return;
        }
        throw new java.lang.IllegalArgumentException("Incompatible SSLContext: " + sSLContext);
    }

    public SSLSessionCache(java.lang.Object obj) {
        this.mSessionCache = (com.android.org.conscrypt.SSLClientSessionCache) obj;
    }

    public SSLSessionCache(java.io.File file) throws java.io.IOException {
        this.mSessionCache = com.android.org.conscrypt.FileClientSessionCache.usingDirectory(file);
    }

    public SSLSessionCache(android.content.Context context) {
        com.android.org.conscrypt.SSLClientSessionCache sSLClientSessionCache;
        java.io.File dir = context.getDir("sslcache", 0);
        try {
            sSLClientSessionCache = com.android.org.conscrypt.FileClientSessionCache.usingDirectory(dir);
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, "Unable to create SSL session cache in " + dir, e);
            sSLClientSessionCache = null;
        }
        this.mSessionCache = sSLClientSessionCache;
    }
}
