package android.net;

/* loaded from: classes2.dex */
public class PacProxySelector extends java.net.ProxySelector {
    private static final java.lang.String PROXY = "PROXY ";
    public static final java.lang.String PROXY_SERVICE = "com.android.net.IProxyService";
    private static final java.lang.String SOCKS = "SOCKS ";
    private static final java.lang.String TAG = "PacProxySelector";
    private final java.util.List<java.net.Proxy> mDefaultList;
    private com.android.net.IProxyService mProxyService = com.android.net.IProxyService.Stub.asInterface(android.os.ServiceManager.getService("com.android.net.IProxyService"));

    public PacProxySelector() {
        if (this.mProxyService == null) {
            android.util.Log.e(TAG, "PacProxyService: no proxy service");
        }
        this.mDefaultList = com.google.android.collect.Lists.newArrayList(java.net.Proxy.NO_PROXY);
    }

    @Override // java.net.ProxySelector
    public java.util.List<java.net.Proxy> select(java.net.URI uri) {
        java.lang.String host;
        java.lang.String str;
        if (this.mProxyService == null) {
            this.mProxyService = com.android.net.IProxyService.Stub.asInterface(android.os.ServiceManager.getService("com.android.net.IProxyService"));
        }
        if (this.mProxyService == null) {
            android.util.Log.e(TAG, "select: no proxy service return NO_PROXY");
            return com.google.android.collect.Lists.newArrayList(java.net.Proxy.NO_PROXY);
        }
        try {
            if (!android.content.IntentFilter.SCHEME_HTTP.equalsIgnoreCase(uri.getScheme())) {
                uri = new java.net.URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), "/", null, null);
            }
            host = uri.toURL().toString();
        } catch (java.net.MalformedURLException e) {
            host = uri.getHost();
        } catch (java.net.URISyntaxException e2) {
            host = uri.getHost();
        }
        try {
            str = this.mProxyService.resolvePacFile(uri.getHost(), host);
        } catch (java.lang.Exception e3) {
            android.util.Log.e(TAG, "Error resolving PAC File", e3);
            str = null;
        }
        if (str == null) {
            return this.mDefaultList;
        }
        return parseResponse(str);
    }

    private static java.util.List<java.net.Proxy> parseResponse(java.lang.String str) {
        java.net.Proxy proxyFromHostPort;
        java.lang.String[] split = str.split(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
        java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
        for (java.lang.String str2 : split) {
            java.lang.String trim = str2.trim();
            if (trim.equals("DIRECT")) {
                newArrayList.add(java.net.Proxy.NO_PROXY);
            } else if (trim.startsWith(PROXY)) {
                java.net.Proxy proxyFromHostPort2 = proxyFromHostPort(java.net.Proxy.Type.HTTP, trim.substring(PROXY.length()));
                if (proxyFromHostPort2 != null) {
                    newArrayList.add(proxyFromHostPort2);
                }
            } else if (trim.startsWith(SOCKS) && (proxyFromHostPort = proxyFromHostPort(java.net.Proxy.Type.SOCKS, trim.substring(SOCKS.length()))) != null) {
                newArrayList.add(proxyFromHostPort);
            }
        }
        if (newArrayList.size() == 0) {
            newArrayList.add(java.net.Proxy.NO_PROXY);
        }
        return newArrayList;
    }

    private static java.net.Proxy proxyFromHostPort(java.net.Proxy.Type type, java.lang.String str) {
        try {
            java.lang.String[] split = str.split(":");
            return new java.net.Proxy(type, java.net.InetSocketAddress.createUnresolved(split[0], java.lang.Integer.parseInt(split[1])));
        } catch (java.lang.ArrayIndexOutOfBoundsException | java.lang.NumberFormatException e) {
            android.util.Log.d(TAG, "Unable to parse proxy " + str + " " + e);
            return null;
        }
    }

    @Override // java.net.ProxySelector
    public void connectFailed(java.net.URI uri, java.net.SocketAddress socketAddress, java.io.IOException iOException) {
    }
}
