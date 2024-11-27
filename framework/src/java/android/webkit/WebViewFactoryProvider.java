package android.webkit;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public interface WebViewFactoryProvider {

    public interface Statics {
        void clearClientCertPreferences(java.lang.Runnable runnable);

        void enableSlowWholeDocumentDraw();

        java.lang.String findAddress(java.lang.String str);

        void freeMemoryForTests();

        java.lang.String getDefaultUserAgent(android.content.Context context);

        android.net.Uri getSafeBrowsingPrivacyPolicyUrl();

        void initSafeBrowsing(android.content.Context context, android.webkit.ValueCallback<java.lang.Boolean> valueCallback);

        android.net.Uri[] parseFileChooserResult(int i, android.content.Intent intent);

        void setSafeBrowsingWhitelist(java.util.List<java.lang.String> list, android.webkit.ValueCallback<java.lang.Boolean> valueCallback);

        void setWebContentsDebuggingEnabled(boolean z);
    }

    android.webkit.WebViewProvider createWebView(android.webkit.WebView webView, android.webkit.WebView.PrivateAccess privateAccess);

    android.webkit.CookieManager getCookieManager();

    android.webkit.GeolocationPermissions getGeolocationPermissions();

    android.webkit.ServiceWorkerController getServiceWorkerController();

    android.webkit.WebViewFactoryProvider.Statics getStatics();

    android.webkit.TokenBindingService getTokenBindingService();

    android.webkit.TracingController getTracingController();

    android.webkit.WebIconDatabase getWebIconDatabase();

    android.webkit.WebStorage getWebStorage();

    java.lang.ClassLoader getWebViewClassLoader();

    android.webkit.WebViewDatabase getWebViewDatabase(android.content.Context context);

    default android.webkit.PacProcessor getPacProcessor() {
        throw new java.lang.UnsupportedOperationException("Not implemented");
    }

    default android.webkit.PacProcessor createPacProcessor() {
        throw new java.lang.UnsupportedOperationException("Not implemented");
    }
}
