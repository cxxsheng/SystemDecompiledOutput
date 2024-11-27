package android.webkit;

/* loaded from: classes4.dex */
public class WebChromeClient {

    public interface CustomViewCallback {
        void onCustomViewHidden();
    }

    public void onProgressChanged(android.webkit.WebView webView, int i) {
    }

    public void onReceivedTitle(android.webkit.WebView webView, java.lang.String str) {
    }

    public void onReceivedIcon(android.webkit.WebView webView, android.graphics.Bitmap bitmap) {
    }

    public void onReceivedTouchIconUrl(android.webkit.WebView webView, java.lang.String str, boolean z) {
    }

    public void onShowCustomView(android.view.View view, android.webkit.WebChromeClient.CustomViewCallback customViewCallback) {
    }

    @java.lang.Deprecated
    public void onShowCustomView(android.view.View view, int i, android.webkit.WebChromeClient.CustomViewCallback customViewCallback) {
    }

    public void onHideCustomView() {
    }

    public boolean onCreateWindow(android.webkit.WebView webView, boolean z, boolean z2, android.os.Message message) {
        return false;
    }

    public void onRequestFocus(android.webkit.WebView webView) {
    }

    public void onCloseWindow(android.webkit.WebView webView) {
    }

    public boolean onJsAlert(android.webkit.WebView webView, java.lang.String str, java.lang.String str2, android.webkit.JsResult jsResult) {
        return false;
    }

    public boolean onJsConfirm(android.webkit.WebView webView, java.lang.String str, java.lang.String str2, android.webkit.JsResult jsResult) {
        return false;
    }

    public boolean onJsPrompt(android.webkit.WebView webView, java.lang.String str, java.lang.String str2, java.lang.String str3, android.webkit.JsPromptResult jsPromptResult) {
        return false;
    }

    public boolean onJsBeforeUnload(android.webkit.WebView webView, java.lang.String str, java.lang.String str2, android.webkit.JsResult jsResult) {
        return false;
    }

    @java.lang.Deprecated
    public void onExceededDatabaseQuota(java.lang.String str, java.lang.String str2, long j, long j2, long j3, android.webkit.WebStorage.QuotaUpdater quotaUpdater) {
        quotaUpdater.updateQuota(j);
    }

    @java.lang.Deprecated
    public void onReachedMaxAppCacheSize(long j, long j2, android.webkit.WebStorage.QuotaUpdater quotaUpdater) {
        quotaUpdater.updateQuota(j2);
    }

    public void onGeolocationPermissionsShowPrompt(java.lang.String str, android.webkit.GeolocationPermissions.Callback callback) {
    }

    public void onGeolocationPermissionsHidePrompt() {
    }

    public void onPermissionRequest(android.webkit.PermissionRequest permissionRequest) {
        permissionRequest.deny();
    }

    public void onPermissionRequestCanceled(android.webkit.PermissionRequest permissionRequest) {
    }

    @java.lang.Deprecated
    public boolean onJsTimeout() {
        return true;
    }

    @java.lang.Deprecated
    public void onConsoleMessage(java.lang.String str, int i, java.lang.String str2) {
    }

    public boolean onConsoleMessage(android.webkit.ConsoleMessage consoleMessage) {
        onConsoleMessage(consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId());
        return false;
    }

    public android.graphics.Bitmap getDefaultVideoPoster() {
        return null;
    }

    public android.view.View getVideoLoadingProgressView() {
        return null;
    }

    public void getVisitedHistory(android.webkit.ValueCallback<java.lang.String[]> valueCallback) {
    }

    public boolean onShowFileChooser(android.webkit.WebView webView, android.webkit.ValueCallback<android.net.Uri[]> valueCallback, android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
        return false;
    }

    public static abstract class FileChooserParams {
        public static final int MODE_OPEN = 0;
        public static final int MODE_OPEN_FOLDER = 2;
        public static final int MODE_OPEN_MULTIPLE = 1;
        public static final int MODE_SAVE = 3;

        public abstract android.content.Intent createIntent();

        public abstract java.lang.String[] getAcceptTypes();

        public abstract java.lang.String getFilenameHint();

        public abstract int getMode();

        public abstract java.lang.CharSequence getTitle();

        public abstract boolean isCaptureEnabled();

        public static android.net.Uri[] parseResult(int i, android.content.Intent intent) {
            return android.webkit.WebViewFactory.getProvider().getStatics().parseFileChooserResult(i, intent);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void openFileChooser(android.webkit.ValueCallback<android.net.Uri> valueCallback, java.lang.String str, java.lang.String str2) {
        valueCallback.onReceiveValue(null);
    }
}
