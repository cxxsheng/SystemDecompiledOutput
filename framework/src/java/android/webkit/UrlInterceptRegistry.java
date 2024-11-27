package android.webkit;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public final class UrlInterceptRegistry {
    private static final java.lang.String LOGTAG = "intercept";
    private static boolean mDisabled = false;
    private static java.util.LinkedList mHandlerList;

    private static synchronized java.util.LinkedList getHandlers() {
        java.util.LinkedList linkedList;
        synchronized (android.webkit.UrlInterceptRegistry.class) {
            if (mHandlerList == null) {
                mHandlerList = new java.util.LinkedList();
            }
            linkedList = mHandlerList;
        }
        return linkedList;
    }

    @java.lang.Deprecated
    public static synchronized void setUrlInterceptDisabled(boolean z) {
        synchronized (android.webkit.UrlInterceptRegistry.class) {
            mDisabled = z;
        }
    }

    @java.lang.Deprecated
    public static synchronized boolean urlInterceptDisabled() {
        boolean z;
        synchronized (android.webkit.UrlInterceptRegistry.class) {
            z = mDisabled;
        }
        return z;
    }

    @java.lang.Deprecated
    public static synchronized boolean registerHandler(android.webkit.UrlInterceptHandler urlInterceptHandler) {
        synchronized (android.webkit.UrlInterceptRegistry.class) {
            if (getHandlers().contains(urlInterceptHandler)) {
                return false;
            }
            getHandlers().addFirst(urlInterceptHandler);
            return true;
        }
    }

    @java.lang.Deprecated
    public static synchronized boolean unregisterHandler(android.webkit.UrlInterceptHandler urlInterceptHandler) {
        boolean remove;
        synchronized (android.webkit.UrlInterceptRegistry.class) {
            remove = getHandlers().remove(urlInterceptHandler);
        }
        return remove;
    }

    @java.lang.Deprecated
    public static synchronized android.webkit.CacheManager.CacheResult getSurrogate(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map) {
        synchronized (android.webkit.UrlInterceptRegistry.class) {
            if (urlInterceptDisabled()) {
                return null;
            }
            java.util.ListIterator listIterator = getHandlers().listIterator();
            while (listIterator.hasNext()) {
                android.webkit.CacheManager.CacheResult service = ((android.webkit.UrlInterceptHandler) listIterator.next()).service(str, map);
                if (service != null) {
                    return service;
                }
            }
            return null;
        }
    }

    @java.lang.Deprecated
    public static synchronized android.webkit.PluginData getPluginData(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map) {
        synchronized (android.webkit.UrlInterceptRegistry.class) {
            if (urlInterceptDisabled()) {
                return null;
            }
            java.util.ListIterator listIterator = getHandlers().listIterator();
            while (listIterator.hasNext()) {
                android.webkit.PluginData pluginData = ((android.webkit.UrlInterceptHandler) listIterator.next()).getPluginData(str, map);
                if (pluginData != null) {
                    return pluginData;
                }
            }
            return null;
        }
    }
}
