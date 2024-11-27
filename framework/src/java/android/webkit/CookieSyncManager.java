package android.webkit;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public final class CookieSyncManager extends android.webkit.WebSyncManager {
    private static boolean sGetInstanceAllowed = false;
    private static final java.lang.Object sLock = new java.lang.Object();
    private static android.webkit.CookieSyncManager sRef;

    @Override // android.webkit.WebSyncManager, java.lang.Runnable
    public /* bridge */ /* synthetic */ void run() {
        super.run();
    }

    private CookieSyncManager() {
        super(null, null);
    }

    public static android.webkit.CookieSyncManager getInstance() {
        android.webkit.CookieSyncManager cookieSyncManager;
        synchronized (sLock) {
            checkInstanceIsAllowed();
            if (sRef == null) {
                sRef = new android.webkit.CookieSyncManager();
            }
            cookieSyncManager = sRef;
        }
        return cookieSyncManager;
    }

    public static android.webkit.CookieSyncManager createInstance(android.content.Context context) {
        android.webkit.CookieSyncManager cookieSyncManager;
        synchronized (sLock) {
            try {
                if (context == null) {
                    throw new java.lang.IllegalArgumentException("Invalid context argument");
                }
                setGetInstanceIsAllowed();
                cookieSyncManager = getInstance();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return cookieSyncManager;
    }

    @Override // android.webkit.WebSyncManager
    @java.lang.Deprecated
    public void sync() {
        android.webkit.CookieManager.getInstance().flush();
    }

    @Override // android.webkit.WebSyncManager
    @java.lang.Deprecated
    protected void syncFromRamToFlash() {
        android.webkit.CookieManager.getInstance().flush();
    }

    @Override // android.webkit.WebSyncManager
    @java.lang.Deprecated
    public void resetSync() {
    }

    @Override // android.webkit.WebSyncManager
    @java.lang.Deprecated
    public void startSync() {
    }

    @Override // android.webkit.WebSyncManager
    @java.lang.Deprecated
    public void stopSync() {
    }

    static void setGetInstanceIsAllowed() {
        sGetInstanceAllowed = true;
    }

    private static void checkInstanceIsAllowed() {
        if (!sGetInstanceAllowed) {
            throw new java.lang.IllegalStateException("CookieSyncManager::createInstance() needs to be called before CookieSyncManager::getInstance()");
        }
    }
}
