package android.media;

/* loaded from: classes2.dex */
public class MediaHTTPService extends android.media.IMediaHTTPService.Stub {
    private static final java.lang.String TAG = "MediaHTTPService";
    private java.util.List<java.net.HttpCookie> mCookies;
    private final java.lang.Object mCookieStoreInitializedLock = new java.lang.Object();
    private boolean mCookieStoreInitialized = false;

    public MediaHTTPService(java.util.List<java.net.HttpCookie> list) {
        this.mCookies = list;
        android.util.Log.v(TAG, "MediaHTTPService(" + this + "): Cookies: " + list);
    }

    @Override // android.media.IMediaHTTPService
    public android.media.IMediaHTTPConnection makeHTTPConnection() {
        synchronized (this.mCookieStoreInitializedLock) {
            if (!this.mCookieStoreInitialized) {
                java.net.CookieHandler cookieHandler = java.net.CookieHandler.getDefault();
                if (cookieHandler == null) {
                    cookieHandler = new java.net.CookieManager();
                    java.net.CookieHandler.setDefault(cookieHandler);
                    android.util.Log.v(TAG, "makeHTTPConnection: CookieManager created: " + cookieHandler);
                } else {
                    android.util.Log.v(TAG, "makeHTTPConnection: CookieHandler (" + cookieHandler + ") exists.");
                }
                if (this.mCookies != null) {
                    if (cookieHandler instanceof java.net.CookieManager) {
                        java.net.CookieStore cookieStore = ((java.net.CookieManager) cookieHandler).getCookieStore();
                        java.util.Iterator<java.net.HttpCookie> it = this.mCookies.iterator();
                        while (it.hasNext()) {
                            try {
                                cookieStore.add(null, it.next());
                            } catch (java.lang.Exception e) {
                                android.util.Log.v(TAG, "makeHTTPConnection: CookieStore.add" + e);
                            }
                        }
                    } else {
                        android.util.Log.w(TAG, "makeHTTPConnection: The installed CookieHandler is not a CookieManager. Can’t add the provided cookies to the cookie store.");
                    }
                }
                this.mCookieStoreInitialized = true;
                android.util.Log.v(TAG, "makeHTTPConnection(" + this + "): cookieHandler: " + cookieHandler + " Cookies: " + this.mCookies);
            }
        }
        return new android.media.MediaHTTPConnection();
    }

    static android.os.IBinder createHttpServiceBinderIfNecessary(java.lang.String str) {
        return createHttpServiceBinderIfNecessary(str, null);
    }

    static android.os.IBinder createHttpServiceBinderIfNecessary(java.lang.String str, java.util.List<java.net.HttpCookie> list) {
        if (str.startsWith("http://") || str.startsWith("https://")) {
            return new android.media.MediaHTTPService(list).asBinder();
        }
        if (str.startsWith("widevine://")) {
            android.util.Log.d(TAG, "Widevine classic is no longer supported");
            return null;
        }
        return null;
    }
}
