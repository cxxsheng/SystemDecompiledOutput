package android.webkit;

/* loaded from: classes4.dex */
public abstract class CookieManager {
    public abstract boolean acceptCookie();

    public abstract boolean acceptThirdPartyCookies(android.webkit.WebView webView);

    @android.annotation.SystemApi
    protected abstract boolean allowFileSchemeCookiesImpl();

    public abstract void flush();

    public abstract java.lang.String getCookie(java.lang.String str);

    @android.annotation.SystemApi
    public abstract java.lang.String getCookie(java.lang.String str, boolean z);

    public abstract boolean hasCookies();

    @android.annotation.SystemApi
    public abstract boolean hasCookies(boolean z);

    @java.lang.Deprecated
    public abstract void removeAllCookie();

    public abstract void removeAllCookies(android.webkit.ValueCallback<java.lang.Boolean> valueCallback);

    @java.lang.Deprecated
    public abstract void removeExpiredCookie();

    @java.lang.Deprecated
    public abstract void removeSessionCookie();

    public abstract void removeSessionCookies(android.webkit.ValueCallback<java.lang.Boolean> valueCallback);

    public abstract void setAcceptCookie(boolean z);

    @android.annotation.SystemApi
    protected abstract void setAcceptFileSchemeCookiesImpl(boolean z);

    public abstract void setAcceptThirdPartyCookies(android.webkit.WebView webView, boolean z);

    public abstract void setCookie(java.lang.String str, java.lang.String str2);

    public abstract void setCookie(java.lang.String str, java.lang.String str2, android.webkit.ValueCallback<java.lang.Boolean> valueCallback);

    @java.lang.Deprecated
    public CookieManager() {
    }

    protected java.lang.Object clone() throws java.lang.CloneNotSupportedException {
        throw new java.lang.CloneNotSupportedException("doesn't implement Cloneable");
    }

    public static android.webkit.CookieManager getInstance() {
        return android.webkit.WebViewFactory.getProvider().getCookieManager();
    }

    @android.annotation.SystemApi
    public synchronized java.lang.String getCookie(android.net.WebAddress webAddress) {
        return getCookie(webAddress.toString());
    }

    public static boolean allowFileSchemeCookies() {
        return getInstance().allowFileSchemeCookiesImpl();
    }

    @java.lang.Deprecated
    public static void setAcceptFileSchemeCookies(boolean z) {
        getInstance().setAcceptFileSchemeCookiesImpl(z);
    }
}
