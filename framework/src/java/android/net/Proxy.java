package android.net;

/* loaded from: classes2.dex */
public final class Proxy {

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PROXY_INFO = "android.intent.extra.PROXY_INFO";
    public static final java.lang.String PROXY_CHANGE_ACTION = "android.intent.action.PROXY_CHANGE";
    private static final java.lang.String TAG = "Proxy";
    private static android.net.ConnectivityManager sConnectivityManager = null;
    private static final java.net.ProxySelector sDefaultProxySelector = java.net.ProxySelector.getDefault();

    public static final java.net.Proxy getProxy(android.content.Context context, java.lang.String str) {
        if (str != null && !isLocalHost("")) {
            java.util.List<java.net.Proxy> select = java.net.ProxySelector.getDefault().select(java.net.URI.create(str));
            if (select.size() > 0) {
                return select.get(0);
            }
        }
        return java.net.Proxy.NO_PROXY;
    }

    @java.lang.Deprecated
    public static final java.lang.String getHost(android.content.Context context) {
        java.net.Proxy proxy = getProxy(context, null);
        if (proxy == java.net.Proxy.NO_PROXY) {
            return null;
        }
        try {
            return ((java.net.InetSocketAddress) proxy.address()).getHostName();
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    @java.lang.Deprecated
    public static final int getPort(android.content.Context context) {
        java.net.Proxy proxy = getProxy(context, null);
        if (proxy == java.net.Proxy.NO_PROXY) {
            return -1;
        }
        try {
            return ((java.net.InetSocketAddress) proxy.address()).getPort();
        } catch (java.lang.Exception e) {
            return -1;
        }
    }

    @java.lang.Deprecated
    public static final java.lang.String getDefaultHost() {
        java.lang.String property = java.lang.System.getProperty("http.proxyHost");
        if (android.text.TextUtils.isEmpty(property)) {
            return null;
        }
        return property;
    }

    @java.lang.Deprecated
    public static final int getDefaultPort() {
        if (getDefaultHost() == null) {
            return -1;
        }
        try {
            return java.lang.Integer.parseInt(java.lang.System.getProperty("http.proxyPort"));
        } catch (java.lang.NumberFormatException e) {
            return -1;
        }
    }

    private static final boolean isLocalHost(java.lang.String str) {
        if (str != null && str != null) {
            try {
                if (str.equalsIgnoreCase("localhost")) {
                    return true;
                }
                if (android.net.InetAddresses.parseNumericAddress(str).isLoopbackAddress()) {
                    return true;
                }
            } catch (java.lang.IllegalArgumentException e) {
            }
        }
        return false;
    }

    @java.lang.Deprecated
    public static void setHttpProxySystemProperty(android.net.ProxyInfo proxyInfo) {
        setHttpProxyConfiguration(proxyInfo);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static void setHttpProxyConfiguration(android.net.ProxyInfo proxyInfo) {
        java.lang.String str;
        java.lang.String str2;
        android.net.Uri uri;
        java.lang.String str3;
        android.net.Uri uri2 = android.net.Uri.EMPTY;
        if (proxyInfo == null) {
            str = null;
            str2 = null;
            uri = uri2;
            str3 = null;
        } else {
            str3 = proxyInfo.getHost();
            str = java.lang.Integer.toString(proxyInfo.getPort());
            str2 = com.android.net.module.util.ProxyUtils.exclusionListAsString(proxyInfo.getExclusionList());
            uri = proxyInfo.getPacFileUrl();
        }
        setHttpProxyConfiguration(str3, str, str2, uri);
    }

    public static void setHttpProxyConfiguration(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.Uri uri) {
        if (str3 != null) {
            str3 = str3.replace(",", android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        }
        if (str != null) {
            java.lang.System.setProperty("http.proxyHost", str);
            java.lang.System.setProperty("https.proxyHost", str);
        } else {
            java.lang.System.clearProperty("http.proxyHost");
            java.lang.System.clearProperty("https.proxyHost");
        }
        if (str2 != null) {
            java.lang.System.setProperty("http.proxyPort", str2);
            java.lang.System.setProperty("https.proxyPort", str2);
        } else {
            java.lang.System.clearProperty("http.proxyPort");
            java.lang.System.clearProperty("https.proxyPort");
        }
        if (str3 != null) {
            java.lang.System.setProperty("http.nonProxyHosts", str3);
            java.lang.System.setProperty("https.nonProxyHosts", str3);
        } else {
            java.lang.System.clearProperty("http.nonProxyHosts");
            java.lang.System.clearProperty("https.nonProxyHosts");
        }
        if (!android.net.Uri.EMPTY.equals(uri)) {
            java.net.ProxySelector.setDefault(new android.net.PacProxySelector());
        } else {
            java.net.ProxySelector.setDefault(sDefaultProxySelector);
        }
    }
}
