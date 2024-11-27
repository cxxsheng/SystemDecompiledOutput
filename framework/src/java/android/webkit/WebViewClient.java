package android.webkit;

/* loaded from: classes4.dex */
public class WebViewClient {
    public static final int ERROR_AUTHENTICATION = -4;
    public static final int ERROR_BAD_URL = -12;
    public static final int ERROR_CONNECT = -6;
    public static final int ERROR_FAILED_SSL_HANDSHAKE = -11;
    public static final int ERROR_FILE = -13;
    public static final int ERROR_FILE_NOT_FOUND = -14;
    public static final int ERROR_HOST_LOOKUP = -2;
    public static final int ERROR_IO = -7;
    public static final int ERROR_PROXY_AUTHENTICATION = -5;
    public static final int ERROR_REDIRECT_LOOP = -9;
    public static final int ERROR_TIMEOUT = -8;
    public static final int ERROR_TOO_MANY_REQUESTS = -15;
    public static final int ERROR_UNKNOWN = -1;
    public static final int ERROR_UNSAFE_RESOURCE = -16;
    public static final int ERROR_UNSUPPORTED_AUTH_SCHEME = -3;
    public static final int ERROR_UNSUPPORTED_SCHEME = -10;
    public static final int SAFE_BROWSING_THREAT_BILLING = 4;
    public static final int SAFE_BROWSING_THREAT_MALWARE = 1;
    public static final int SAFE_BROWSING_THREAT_PHISHING = 2;
    public static final int SAFE_BROWSING_THREAT_UNKNOWN = 0;
    public static final int SAFE_BROWSING_THREAT_UNWANTED_SOFTWARE = 3;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SafeBrowsingThreat {
    }

    @java.lang.Deprecated
    public boolean shouldOverrideUrlLoading(android.webkit.WebView webView, java.lang.String str) {
        return false;
    }

    public boolean shouldOverrideUrlLoading(android.webkit.WebView webView, android.webkit.WebResourceRequest webResourceRequest) {
        return shouldOverrideUrlLoading(webView, webResourceRequest.getUrl().toString());
    }

    public void onPageStarted(android.webkit.WebView webView, java.lang.String str, android.graphics.Bitmap bitmap) {
    }

    public void onPageFinished(android.webkit.WebView webView, java.lang.String str) {
    }

    public void onLoadResource(android.webkit.WebView webView, java.lang.String str) {
    }

    public void onPageCommitVisible(android.webkit.WebView webView, java.lang.String str) {
    }

    @java.lang.Deprecated
    public android.webkit.WebResourceResponse shouldInterceptRequest(android.webkit.WebView webView, java.lang.String str) {
        return null;
    }

    public android.webkit.WebResourceResponse shouldInterceptRequest(android.webkit.WebView webView, android.webkit.WebResourceRequest webResourceRequest) {
        return shouldInterceptRequest(webView, webResourceRequest.getUrl().toString());
    }

    @java.lang.Deprecated
    public void onTooManyRedirects(android.webkit.WebView webView, android.os.Message message, android.os.Message message2) {
        message.sendToTarget();
    }

    @java.lang.Deprecated
    public void onReceivedError(android.webkit.WebView webView, int i, java.lang.String str, java.lang.String str2) {
    }

    public void onReceivedError(android.webkit.WebView webView, android.webkit.WebResourceRequest webResourceRequest, android.webkit.WebResourceError webResourceError) {
        if (webResourceRequest.isForMainFrame()) {
            onReceivedError(webView, webResourceError.getErrorCode(), webResourceError.getDescription().toString(), webResourceRequest.getUrl().toString());
        }
    }

    public void onReceivedHttpError(android.webkit.WebView webView, android.webkit.WebResourceRequest webResourceRequest, android.webkit.WebResourceResponse webResourceResponse) {
    }

    public void onFormResubmission(android.webkit.WebView webView, android.os.Message message, android.os.Message message2) {
        message.sendToTarget();
    }

    public void doUpdateVisitedHistory(android.webkit.WebView webView, java.lang.String str, boolean z) {
    }

    public void onReceivedSslError(android.webkit.WebView webView, android.webkit.SslErrorHandler sslErrorHandler, android.net.http.SslError sslError) {
        sslErrorHandler.cancel();
    }

    public void onReceivedClientCertRequest(android.webkit.WebView webView, android.webkit.ClientCertRequest clientCertRequest) {
        clientCertRequest.cancel();
    }

    public void onReceivedHttpAuthRequest(android.webkit.WebView webView, android.webkit.HttpAuthHandler httpAuthHandler, java.lang.String str, java.lang.String str2) {
        httpAuthHandler.cancel();
    }

    public boolean shouldOverrideKeyEvent(android.webkit.WebView webView, android.view.KeyEvent keyEvent) {
        return false;
    }

    public void onUnhandledKeyEvent(android.webkit.WebView webView, android.view.KeyEvent keyEvent) {
        onUnhandledInputEventInternal(webView, keyEvent);
    }

    public void onUnhandledInputEvent(android.webkit.WebView webView, android.view.InputEvent inputEvent) {
        if (inputEvent instanceof android.view.KeyEvent) {
            onUnhandledKeyEvent(webView, (android.view.KeyEvent) inputEvent);
        } else {
            onUnhandledInputEventInternal(webView, inputEvent);
        }
    }

    private void onUnhandledInputEventInternal(android.webkit.WebView webView, android.view.InputEvent inputEvent) {
        android.view.ViewRootImpl viewRootImpl = webView.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.dispatchUnhandledInputEvent(inputEvent);
        }
    }

    public void onScaleChanged(android.webkit.WebView webView, float f, float f2) {
    }

    public void onReceivedLoginRequest(android.webkit.WebView webView, java.lang.String str, java.lang.String str2, java.lang.String str3) {
    }

    public boolean onRenderProcessGone(android.webkit.WebView webView, android.webkit.RenderProcessGoneDetail renderProcessGoneDetail) {
        return false;
    }

    public void onSafeBrowsingHit(android.webkit.WebView webView, android.webkit.WebResourceRequest webResourceRequest, int i, android.webkit.SafeBrowsingResponse safeBrowsingResponse) {
        safeBrowsingResponse.showInterstitial(true);
    }
}
