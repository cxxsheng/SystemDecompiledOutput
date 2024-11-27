package android.webkit;

/* loaded from: classes4.dex */
public class WebView extends android.widget.AbsoluteLayout implements android.view.ViewTreeObserver.OnGlobalFocusChangeListener, android.view.ViewGroup.OnHierarchyChangeListener, android.view.ViewDebug.HierarchyHandler {
    private static final java.lang.String LOGTAG = "WebView";
    public static final int RENDERER_PRIORITY_BOUND = 1;
    public static final int RENDERER_PRIORITY_IMPORTANT = 2;
    public static final int RENDERER_PRIORITY_WAIVED = 0;
    public static final java.lang.String SCHEME_GEO = "geo:0,0?q=";
    public static final java.lang.String SCHEME_MAILTO = "mailto:";
    public static final java.lang.String SCHEME_TEL = "tel:";
    private static volatile boolean sEnforceThreadChecking = false;
    private android.webkit.WebView.FindListenerDistributor mFindListener;
    private android.webkit.WebViewProvider mProvider;
    private final android.os.Looper mWebViewThread;

    public interface FindListener {
        void onFindResultReceived(int i, int i2, boolean z);
    }

    @java.lang.Deprecated
    public interface PictureListener {
        @java.lang.Deprecated
        void onNewPicture(android.webkit.WebView webView, android.graphics.Picture picture);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RendererPriority {
    }

    public static abstract class VisualStateCallback {
        public abstract void onComplete(long j);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.webkit.WebView> {
        private int mContentHeightId;
        private int mFaviconId;
        private int mOriginalUrlId;
        private int mProgressId;
        private boolean mPropertiesMapped = false;
        private int mRendererPriorityWaivedWhenNotVisibleId;
        private int mRendererRequestedPriorityId;
        private int mTitleId;
        private int mUrlId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mContentHeightId = propertyMapper.mapInt("contentHeight", 0);
            this.mFaviconId = propertyMapper.mapObject("favicon", 0);
            this.mOriginalUrlId = propertyMapper.mapObject("originalUrl", 0);
            this.mProgressId = propertyMapper.mapInt(android.app.Notification.CATEGORY_PROGRESS, 0);
            this.mRendererPriorityWaivedWhenNotVisibleId = propertyMapper.mapBoolean("rendererPriorityWaivedWhenNotVisible", 0);
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            sparseArray.put(0, "waived");
            sparseArray.put(1, "bound");
            sparseArray.put(2, "important");
            java.util.Objects.requireNonNull(sparseArray);
            this.mRendererRequestedPriorityId = propertyMapper.mapIntEnum("rendererRequestedPriority", 0, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mTitleId = propertyMapper.mapObject("title", 0);
            this.mUrlId = propertyMapper.mapObject("url", 0);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.webkit.WebView webView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readInt(this.mContentHeightId, webView.getContentHeight());
            propertyReader.readObject(this.mFaviconId, webView.getFavicon());
            propertyReader.readObject(this.mOriginalUrlId, webView.getOriginalUrl());
            propertyReader.readInt(this.mProgressId, webView.getProgress());
            propertyReader.readBoolean(this.mRendererPriorityWaivedWhenNotVisibleId, webView.getRendererPriorityWaivedWhenNotVisible());
            propertyReader.readIntEnum(this.mRendererRequestedPriorityId, webView.getRendererRequestedPriority());
            propertyReader.readObject(this.mTitleId, webView.getTitle());
            propertyReader.readObject(this.mUrlId, webView.getUrl());
        }
    }

    public class WebViewTransport {
        private android.webkit.WebView mWebview;

        public WebViewTransport() {
        }

        public synchronized void setWebView(android.webkit.WebView webView) {
            this.mWebview = webView;
        }

        public synchronized android.webkit.WebView getWebView() {
            return this.mWebview;
        }
    }

    public static class HitTestResult {

        @java.lang.Deprecated
        public static final int ANCHOR_TYPE = 1;
        public static final int EDIT_TEXT_TYPE = 9;
        public static final int EMAIL_TYPE = 4;
        public static final int GEO_TYPE = 3;

        @java.lang.Deprecated
        public static final int IMAGE_ANCHOR_TYPE = 6;
        public static final int IMAGE_TYPE = 5;
        public static final int PHONE_TYPE = 2;
        public static final int SRC_ANCHOR_TYPE = 7;
        public static final int SRC_IMAGE_ANCHOR_TYPE = 8;
        public static final int UNKNOWN_TYPE = 0;
        private java.lang.String mExtra;
        private int mType = 0;

        @android.annotation.SystemApi
        public HitTestResult() {
        }

        @android.annotation.SystemApi
        public void setType(int i) {
            this.mType = i;
        }

        @android.annotation.SystemApi
        public void setExtra(java.lang.String str) {
            this.mExtra = str;
        }

        public int getType() {
            return this.mType;
        }

        public java.lang.String getExtra() {
            return this.mExtra;
        }
    }

    public WebView(android.content.Context context) {
        this(context, null);
    }

    public WebView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842885);
    }

    public WebView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WebView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, i2, null, false);
    }

    @java.lang.Deprecated
    public WebView(android.content.Context context, android.util.AttributeSet attributeSet, int i, boolean z) {
        this(context, attributeSet, i, 0, null, z);
    }

    protected WebView(android.content.Context context, android.util.AttributeSet attributeSet, int i, java.util.Map<java.lang.String, java.lang.Object> map, boolean z) {
        this(context, attributeSet, i, 0, map, z);
    }

    protected WebView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2, java.util.Map<java.lang.String, java.lang.Object> map, boolean z) {
        super(context, attributeSet, i, i2);
        this.mWebViewThread = android.os.Looper.myLooper();
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        if (getImportantForContentCapture() == 0) {
            setImportantForContentCapture(1);
        }
        if (context == null) {
            throw new java.lang.IllegalArgumentException("Invalid context argument");
        }
        if (this.mWebViewThread == null) {
            throw new java.lang.RuntimeException("WebView cannot be initialized on a thread that has no Looper.");
        }
        sEnforceThreadChecking = context.getApplicationInfo().targetSdkVersion >= 18;
        checkThread();
        ensureProviderCreated();
        this.mProvider.init(map, z);
        android.webkit.CookieSyncManager.setGetInstanceIsAllowed();
    }

    @java.lang.Deprecated
    public void setHorizontalScrollbarOverlay(boolean z) {
    }

    @java.lang.Deprecated
    public void setVerticalScrollbarOverlay(boolean z) {
    }

    @java.lang.Deprecated
    public boolean overlayHorizontalScrollbar() {
        return true;
    }

    @java.lang.Deprecated
    public boolean overlayVerticalScrollbar() {
        return false;
    }

    @java.lang.Deprecated
    public int getVisibleTitleHeight() {
        checkThread();
        return this.mProvider.getVisibleTitleHeight();
    }

    public android.net.http.SslCertificate getCertificate() {
        checkThread();
        return this.mProvider.getCertificate();
    }

    @java.lang.Deprecated
    public void setCertificate(android.net.http.SslCertificate sslCertificate) {
        checkThread();
        this.mProvider.setCertificate(sslCertificate);
    }

    @java.lang.Deprecated
    public void savePassword(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        checkThread();
        this.mProvider.savePassword(str, str2, str3);
    }

    @java.lang.Deprecated
    public void setHttpAuthUsernamePassword(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        checkThread();
        this.mProvider.setHttpAuthUsernamePassword(str, str2, str3, str4);
    }

    @java.lang.Deprecated
    public java.lang.String[] getHttpAuthUsernamePassword(java.lang.String str, java.lang.String str2) {
        checkThread();
        return this.mProvider.getHttpAuthUsernamePassword(str, str2);
    }

    public void destroy() {
        checkThread();
        this.mProvider.destroy();
    }

    @java.lang.Deprecated
    public static void enablePlatformNotifications() {
    }

    @java.lang.Deprecated
    public static void disablePlatformNotifications() {
    }

    public static void freeMemoryForTests() {
        getFactory().getStatics().freeMemoryForTests();
    }

    public void setNetworkAvailable(boolean z) {
        checkThread();
        this.mProvider.setNetworkAvailable(z);
    }

    public android.webkit.WebBackForwardList saveState(android.os.Bundle bundle) {
        checkThread();
        return this.mProvider.saveState(bundle);
    }

    @java.lang.Deprecated
    public boolean savePicture(android.os.Bundle bundle, java.io.File file) {
        checkThread();
        return this.mProvider.savePicture(bundle, file);
    }

    @java.lang.Deprecated
    public boolean restorePicture(android.os.Bundle bundle, java.io.File file) {
        checkThread();
        return this.mProvider.restorePicture(bundle, file);
    }

    public android.webkit.WebBackForwardList restoreState(android.os.Bundle bundle) {
        checkThread();
        return this.mProvider.restoreState(bundle);
    }

    public void loadUrl(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map) {
        checkThread();
        this.mProvider.loadUrl(str, map);
    }

    public void loadUrl(java.lang.String str) {
        checkThread();
        this.mProvider.loadUrl(str);
    }

    public void postUrl(java.lang.String str, byte[] bArr) {
        checkThread();
        if (android.webkit.URLUtil.isNetworkUrl(str)) {
            this.mProvider.postUrl(str, bArr);
        } else {
            this.mProvider.loadUrl(str);
        }
    }

    public void loadData(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        checkThread();
        this.mProvider.loadData(str, str2, str3);
    }

    public void loadDataWithBaseURL(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        checkThread();
        this.mProvider.loadDataWithBaseURL(str, str2, str3, str4, str5);
    }

    public void evaluateJavascript(java.lang.String str, android.webkit.ValueCallback<java.lang.String> valueCallback) {
        checkThread();
        this.mProvider.evaluateJavaScript(str, valueCallback);
    }

    public void saveWebArchive(java.lang.String str) {
        checkThread();
        this.mProvider.saveWebArchive(str);
    }

    public void saveWebArchive(java.lang.String str, boolean z, android.webkit.ValueCallback<java.lang.String> valueCallback) {
        checkThread();
        this.mProvider.saveWebArchive(str, z, valueCallback);
    }

    public void stopLoading() {
        checkThread();
        this.mProvider.stopLoading();
    }

    public void reload() {
        checkThread();
        this.mProvider.reload();
    }

    public boolean canGoBack() {
        checkThread();
        return this.mProvider.canGoBack();
    }

    public void goBack() {
        checkThread();
        this.mProvider.goBack();
    }

    public boolean canGoForward() {
        checkThread();
        return this.mProvider.canGoForward();
    }

    public void goForward() {
        checkThread();
        this.mProvider.goForward();
    }

    public boolean canGoBackOrForward(int i) {
        checkThread();
        return this.mProvider.canGoBackOrForward(i);
    }

    public void goBackOrForward(int i) {
        checkThread();
        this.mProvider.goBackOrForward(i);
    }

    public boolean isPrivateBrowsingEnabled() {
        checkThread();
        return this.mProvider.isPrivateBrowsingEnabled();
    }

    public boolean pageUp(boolean z) {
        checkThread();
        return this.mProvider.pageUp(z);
    }

    public boolean pageDown(boolean z) {
        checkThread();
        return this.mProvider.pageDown(z);
    }

    public void postVisualStateCallback(long j, android.webkit.WebView.VisualStateCallback visualStateCallback) {
        checkThread();
        this.mProvider.insertVisualStateCallback(j, visualStateCallback);
    }

    @java.lang.Deprecated
    public void clearView() {
        checkThread();
        this.mProvider.clearView();
    }

    @java.lang.Deprecated
    public android.graphics.Picture capturePicture() {
        checkThread();
        return this.mProvider.capturePicture();
    }

    @java.lang.Deprecated
    public android.print.PrintDocumentAdapter createPrintDocumentAdapter() {
        checkThread();
        return this.mProvider.createPrintDocumentAdapter("default");
    }

    public android.print.PrintDocumentAdapter createPrintDocumentAdapter(java.lang.String str) {
        checkThread();
        return this.mProvider.createPrintDocumentAdapter(str);
    }

    @android.view.ViewDebug.ExportedProperty(category = android.view.textclassifier.TextClassifier.WIDGET_TYPE_WEBVIEW)
    @java.lang.Deprecated
    public float getScale() {
        checkThread();
        return this.mProvider.getScale();
    }

    public void setInitialScale(int i) {
        checkThread();
        this.mProvider.setInitialScale(i);
    }

    public void invokeZoomPicker() {
        checkThread();
        this.mProvider.invokeZoomPicker();
    }

    public android.webkit.WebView.HitTestResult getHitTestResult() {
        checkThread();
        return this.mProvider.getHitTestResult();
    }

    public void requestFocusNodeHref(android.os.Message message) {
        checkThread();
        this.mProvider.requestFocusNodeHref(message);
    }

    public void requestImageRef(android.os.Message message) {
        checkThread();
        this.mProvider.requestImageRef(message);
    }

    @android.view.ViewDebug.ExportedProperty(category = android.view.textclassifier.TextClassifier.WIDGET_TYPE_WEBVIEW)
    public java.lang.String getUrl() {
        checkThread();
        return this.mProvider.getUrl();
    }

    @android.view.ViewDebug.ExportedProperty(category = android.view.textclassifier.TextClassifier.WIDGET_TYPE_WEBVIEW)
    public java.lang.String getOriginalUrl() {
        checkThread();
        return this.mProvider.getOriginalUrl();
    }

    @android.view.ViewDebug.ExportedProperty(category = android.view.textclassifier.TextClassifier.WIDGET_TYPE_WEBVIEW)
    public java.lang.String getTitle() {
        checkThread();
        return this.mProvider.getTitle();
    }

    public android.graphics.Bitmap getFavicon() {
        checkThread();
        return this.mProvider.getFavicon();
    }

    public java.lang.String getTouchIconUrl() {
        return this.mProvider.getTouchIconUrl();
    }

    public int getProgress() {
        checkThread();
        return this.mProvider.getProgress();
    }

    @android.view.ViewDebug.ExportedProperty(category = android.view.textclassifier.TextClassifier.WIDGET_TYPE_WEBVIEW)
    public int getContentHeight() {
        checkThread();
        return this.mProvider.getContentHeight();
    }

    @android.view.ViewDebug.ExportedProperty(category = android.view.textclassifier.TextClassifier.WIDGET_TYPE_WEBVIEW)
    public int getContentWidth() {
        return this.mProvider.getContentWidth();
    }

    public void pauseTimers() {
        checkThread();
        this.mProvider.pauseTimers();
    }

    public void resumeTimers() {
        checkThread();
        this.mProvider.resumeTimers();
    }

    public void onPause() {
        checkThread();
        this.mProvider.onPause();
    }

    public void onResume() {
        checkThread();
        this.mProvider.onResume();
    }

    public boolean isPaused() {
        return this.mProvider.isPaused();
    }

    @java.lang.Deprecated
    public void freeMemory() {
        checkThread();
        this.mProvider.freeMemory();
    }

    public void clearCache(boolean z) {
        checkThread();
        this.mProvider.clearCache(z);
    }

    public void clearFormData() {
        checkThread();
        this.mProvider.clearFormData();
    }

    public void clearHistory() {
        checkThread();
        this.mProvider.clearHistory();
    }

    public void clearSslPreferences() {
        checkThread();
        this.mProvider.clearSslPreferences();
    }

    public static void clearClientCertPreferences(java.lang.Runnable runnable) {
        getFactory().getStatics().clearClientCertPreferences(runnable);
    }

    public static void startSafeBrowsing(android.content.Context context, android.webkit.ValueCallback<java.lang.Boolean> valueCallback) {
        getFactory().getStatics().initSafeBrowsing(context, valueCallback);
    }

    public static void setSafeBrowsingWhitelist(java.util.List<java.lang.String> list, android.webkit.ValueCallback<java.lang.Boolean> valueCallback) {
        getFactory().getStatics().setSafeBrowsingWhitelist(list, valueCallback);
    }

    public static android.net.Uri getSafeBrowsingPrivacyPolicyUrl() {
        return getFactory().getStatics().getSafeBrowsingPrivacyPolicyUrl();
    }

    public android.webkit.WebBackForwardList copyBackForwardList() {
        checkThread();
        return this.mProvider.copyBackForwardList();
    }

    public void setFindListener(android.webkit.WebView.FindListener findListener) {
        checkThread();
        setupFindListenerIfNeeded();
        this.mFindListener.mUserFindListener = findListener;
    }

    public void findNext(boolean z) {
        checkThread();
        this.mProvider.findNext(z);
    }

    @java.lang.Deprecated
    public int findAll(java.lang.String str) {
        checkThread();
        android.os.StrictMode.noteSlowCall("findAll blocks UI: prefer findAllAsync");
        return this.mProvider.findAll(str);
    }

    public void findAllAsync(java.lang.String str) {
        checkThread();
        this.mProvider.findAllAsync(str);
    }

    @java.lang.Deprecated
    public boolean showFindDialog(java.lang.String str, boolean z) {
        checkThread();
        return this.mProvider.showFindDialog(str, z);
    }

    @java.lang.Deprecated
    public static java.lang.String findAddress(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("addr is null");
        }
        return android.webkit.FindAddress.findAddress(str);
    }

    public static void enableSlowWholeDocumentDraw() {
        getFactory().getStatics().enableSlowWholeDocumentDraw();
    }

    public void clearMatches() {
        checkThread();
        this.mProvider.clearMatches();
    }

    public void documentHasImages(android.os.Message message) {
        checkThread();
        this.mProvider.documentHasImages(message);
    }

    public void setWebViewClient(android.webkit.WebViewClient webViewClient) {
        checkThread();
        this.mProvider.setWebViewClient(webViewClient);
    }

    public android.webkit.WebViewClient getWebViewClient() {
        checkThread();
        return this.mProvider.getWebViewClient();
    }

    public android.webkit.WebViewRenderProcess getWebViewRenderProcess() {
        checkThread();
        return this.mProvider.getWebViewRenderProcess();
    }

    public void setWebViewRenderProcessClient(java.util.concurrent.Executor executor, android.webkit.WebViewRenderProcessClient webViewRenderProcessClient) {
        checkThread();
        this.mProvider.setWebViewRenderProcessClient(executor, webViewRenderProcessClient);
    }

    public void setWebViewRenderProcessClient(android.webkit.WebViewRenderProcessClient webViewRenderProcessClient) {
        checkThread();
        this.mProvider.setWebViewRenderProcessClient(null, webViewRenderProcessClient);
    }

    public android.webkit.WebViewRenderProcessClient getWebViewRenderProcessClient() {
        checkThread();
        return this.mProvider.getWebViewRenderProcessClient();
    }

    public void setDownloadListener(android.webkit.DownloadListener downloadListener) {
        checkThread();
        this.mProvider.setDownloadListener(downloadListener);
    }

    public void setWebChromeClient(android.webkit.WebChromeClient webChromeClient) {
        checkThread();
        this.mProvider.setWebChromeClient(webChromeClient);
    }

    public android.webkit.WebChromeClient getWebChromeClient() {
        checkThread();
        return this.mProvider.getWebChromeClient();
    }

    @java.lang.Deprecated
    public void setPictureListener(android.webkit.WebView.PictureListener pictureListener) {
        checkThread();
        this.mProvider.setPictureListener(pictureListener);
    }

    public void addJavascriptInterface(java.lang.Object obj, java.lang.String str) {
        checkThread();
        this.mProvider.addJavascriptInterface(obj, str);
    }

    public void removeJavascriptInterface(java.lang.String str) {
        checkThread();
        this.mProvider.removeJavascriptInterface(str);
    }

    public android.webkit.WebMessagePort[] createWebMessageChannel() {
        checkThread();
        return this.mProvider.createWebMessageChannel();
    }

    public void postWebMessage(android.webkit.WebMessage webMessage, android.net.Uri uri) {
        checkThread();
        this.mProvider.postMessageToMainFrame(webMessage, uri);
    }

    public android.webkit.WebSettings getSettings() {
        checkThread();
        return this.mProvider.getSettings();
    }

    public static void setWebContentsDebuggingEnabled(boolean z) {
        getFactory().getStatics().setWebContentsDebuggingEnabled(z);
    }

    @java.lang.Deprecated
    public static synchronized android.webkit.PluginList getPluginList() {
        android.webkit.PluginList pluginList;
        synchronized (android.webkit.WebView.class) {
            pluginList = new android.webkit.PluginList();
        }
        return pluginList;
    }

    public static void setDataDirectorySuffix(java.lang.String str) {
        android.webkit.WebViewFactory.setDataDirectorySuffix(str);
    }

    public static void disableWebView() {
        android.webkit.WebViewFactory.disableWebView();
    }

    @java.lang.Deprecated
    public void refreshPlugins(boolean z) {
        checkThread();
    }

    @java.lang.Deprecated
    public void emulateShiftHeld() {
        checkThread();
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    @java.lang.Deprecated
    public void onChildViewAdded(android.view.View view, android.view.View view2) {
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    @java.lang.Deprecated
    public void onChildViewRemoved(android.view.View view, android.view.View view2) {
    }

    @Override // android.view.ViewTreeObserver.OnGlobalFocusChangeListener
    @java.lang.Deprecated
    public void onGlobalFocusChanged(android.view.View view, android.view.View view2) {
    }

    @java.lang.Deprecated
    public void setMapTrackballToArrowKeys(boolean z) {
        checkThread();
        this.mProvider.setMapTrackballToArrowKeys(z);
    }

    public void flingScroll(int i, int i2) {
        checkThread();
        this.mProvider.flingScroll(i, i2);
    }

    @java.lang.Deprecated
    public android.view.View getZoomControls() {
        checkThread();
        return this.mProvider.getZoomControls();
    }

    @java.lang.Deprecated
    public boolean canZoomIn() {
        checkThread();
        return this.mProvider.canZoomIn();
    }

    @java.lang.Deprecated
    public boolean canZoomOut() {
        checkThread();
        return this.mProvider.canZoomOut();
    }

    public void zoomBy(float f) {
        checkThread();
        double d = f;
        if (d < 0.01d) {
            throw new java.lang.IllegalArgumentException("zoomFactor must be greater than 0.01.");
        }
        if (d > 100.0d) {
            throw new java.lang.IllegalArgumentException("zoomFactor must be less than 100.");
        }
        this.mProvider.zoomBy(f);
    }

    public boolean zoomIn() {
        checkThread();
        return this.mProvider.zoomIn();
    }

    public boolean zoomOut() {
        checkThread();
        return this.mProvider.zoomOut();
    }

    @java.lang.Deprecated
    public void debugDump() {
        checkThread();
    }

    @Override // android.view.ViewDebug.HierarchyHandler
    public void dumpViewHierarchyWithProperties(java.io.BufferedWriter bufferedWriter, int i) {
        this.mProvider.dumpViewHierarchyWithProperties(bufferedWriter, i);
    }

    @Override // android.view.ViewDebug.HierarchyHandler
    public android.view.View findHierarchyView(java.lang.String str, int i) {
        return this.mProvider.findHierarchyView(str, i);
    }

    public void setRendererPriorityPolicy(int i, boolean z) {
        this.mProvider.setRendererPriorityPolicy(i, z);
    }

    public int getRendererRequestedPriority() {
        return this.mProvider.getRendererRequestedPriority();
    }

    public boolean getRendererPriorityWaivedWhenNotVisible() {
        return this.mProvider.getRendererPriorityWaivedWhenNotVisible();
    }

    public void setTextClassifier(android.view.textclassifier.TextClassifier textClassifier) {
        this.mProvider.setTextClassifier(textClassifier);
    }

    public android.view.textclassifier.TextClassifier getTextClassifier() {
        return this.mProvider.getTextClassifier();
    }

    public static java.lang.ClassLoader getWebViewClassLoader() {
        return getFactory().getWebViewClassLoader();
    }

    public android.os.Looper getWebViewLooper() {
        return this.mWebViewThread;
    }

    @android.annotation.SystemApi
    public android.webkit.WebViewProvider getWebViewProvider() {
        return this.mProvider;
    }

    @android.annotation.SystemApi
    public class PrivateAccess {
        public PrivateAccess() {
        }

        public int super_getScrollBarStyle() {
            return android.webkit.WebView.super.getScrollBarStyle();
        }

        public void super_scrollTo(int i, int i2) {
            android.webkit.WebView.super.scrollTo(i, i2);
        }

        public void super_computeScroll() {
            android.webkit.WebView.super.computeScroll();
        }

        public boolean super_onHoverEvent(android.view.MotionEvent motionEvent) {
            return android.webkit.WebView.super.onHoverEvent(motionEvent);
        }

        public boolean super_performAccessibilityAction(int i, android.os.Bundle bundle) {
            return android.webkit.WebView.super.performAccessibilityActionInternal(i, bundle);
        }

        public boolean super_performLongClick() {
            return android.webkit.WebView.super.performLongClick();
        }

        public boolean super_setFrame(int i, int i2, int i3, int i4) {
            return android.webkit.WebView.super.setFrame(i, i2, i3, i4);
        }

        public boolean super_dispatchKeyEvent(android.view.KeyEvent keyEvent) {
            return android.webkit.WebView.super.dispatchKeyEvent(keyEvent);
        }

        public boolean super_onGenericMotionEvent(android.view.MotionEvent motionEvent) {
            return android.webkit.WebView.super.onGenericMotionEvent(motionEvent);
        }

        public boolean super_requestFocus(int i, android.graphics.Rect rect) {
            return android.webkit.WebView.super.requestFocus(i, rect);
        }

        public void super_setLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.webkit.WebView.super.setLayoutParams(layoutParams);
        }

        public void super_startActivityForResult(android.content.Intent intent, int i) {
            android.webkit.WebView.super.startActivityForResult(intent, i);
        }

        public android.view.WindowInsets super_onApplyWindowInsets(android.view.WindowInsets windowInsets) {
            return android.webkit.WebView.super.onApplyWindowInsets(windowInsets);
        }

        public void overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            android.webkit.WebView.this.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
        }

        public void awakenScrollBars(int i) {
            android.webkit.WebView.this.awakenScrollBars(i);
        }

        public void awakenScrollBars(int i, boolean z) {
            android.webkit.WebView.this.awakenScrollBars(i, z);
        }

        public float getVerticalScrollFactor() {
            return android.webkit.WebView.this.getVerticalScrollFactor();
        }

        public float getHorizontalScrollFactor() {
            return android.webkit.WebView.this.getHorizontalScrollFactor();
        }

        public void setMeasuredDimension(int i, int i2) {
            android.webkit.WebView.this.setMeasuredDimension(i, i2);
        }

        public void onScrollChanged(int i, int i2, int i3, int i4) {
            android.webkit.WebView.this.onScrollChanged(i, i2, i3, i4);
        }

        public int getHorizontalScrollbarHeight() {
            return android.webkit.WebView.this.getHorizontalScrollbarHeight();
        }

        public void super_onDrawVerticalScrollBar(android.graphics.Canvas canvas, android.graphics.drawable.Drawable drawable, int i, int i2, int i3, int i4) {
            android.webkit.WebView.super.onDrawVerticalScrollBar(canvas, drawable, i, i2, i3, i4);
        }

        public void setScrollXRaw(int i) {
            android.webkit.WebView.this.mScrollX = i;
        }

        public void setScrollYRaw(int i) {
            android.webkit.WebView.this.mScrollY = i;
        }
    }

    void setFindDialogFindListener(android.webkit.WebView.FindListener findListener) {
        checkThread();
        setupFindListenerIfNeeded();
        this.mFindListener.mFindDialogFindListener = findListener;
    }

    void notifyFindDialogDismissed() {
        checkThread();
        this.mProvider.notifyFindDialogDismissed();
    }

    private class FindListenerDistributor implements android.webkit.WebView.FindListener {
        private android.webkit.WebView.FindListener mFindDialogFindListener;
        private android.webkit.WebView.FindListener mUserFindListener;

        private FindListenerDistributor() {
        }

        @Override // android.webkit.WebView.FindListener
        public void onFindResultReceived(int i, int i2, boolean z) {
            if (this.mFindDialogFindListener != null) {
                this.mFindDialogFindListener.onFindResultReceived(i, i2, z);
            }
            if (this.mUserFindListener != null) {
                this.mUserFindListener.onFindResultReceived(i, i2, z);
            }
        }
    }

    private void setupFindListenerIfNeeded() {
        if (this.mFindListener == null) {
            this.mFindListener = new android.webkit.WebView.FindListenerDistributor();
            this.mProvider.setFindListener(this.mFindListener);
        }
    }

    private void ensureProviderCreated() {
        checkThread();
        if (this.mProvider == null) {
            this.mProvider = getFactory().createWebView(this, new android.webkit.WebView.PrivateAccess());
        }
    }

    private static android.webkit.WebViewFactoryProvider getFactory() {
        return android.webkit.WebViewFactory.getProvider();
    }

    private void checkThread() {
        if (this.mWebViewThread != null && android.os.Looper.myLooper() != this.mWebViewThread) {
            java.lang.Throwable th = new java.lang.Throwable("A WebView method was called on thread '" + java.lang.Thread.currentThread().getName() + "'. All WebView methods must be called on the same thread. (Expected Looper " + this.mWebViewThread + " called on " + android.os.Looper.myLooper() + ", FYI main Looper is " + android.os.Looper.getMainLooper() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            android.util.Log.w(LOGTAG, android.util.Log.getStackTraceString(th));
            android.os.StrictMode.onWebViewMethodCalledOnWrongThread(th);
            if (sEnforceThreadChecking) {
                throw new java.lang.RuntimeException(th);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mProvider.getViewDelegate().onAttachedToWindow();
    }

    @Override // android.view.View
    protected void onDetachedFromWindowInternal() {
        this.mProvider.getViewDelegate().onDetachedFromWindow();
        super.onDetachedFromWindowInternal();
    }

    @Override // android.view.View
    public void onMovedToDisplay(int i, android.content.res.Configuration configuration) {
        this.mProvider.getViewDelegate().onMovedToDisplay(i, configuration);
    }

    @Override // android.view.View
    public void setLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        this.mProvider.getViewDelegate().setLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public void setOverScrollMode(int i) {
        super.setOverScrollMode(i);
        ensureProviderCreated();
        this.mProvider.getViewDelegate().setOverScrollMode(i);
    }

    @Override // android.view.View
    public void setScrollBarStyle(int i) {
        this.mProvider.getViewDelegate().setScrollBarStyle(i);
        super.setScrollBarStyle(i);
    }

    @Override // android.view.View
    protected int computeHorizontalScrollRange() {
        return this.mProvider.getScrollDelegate().computeHorizontalScrollRange();
    }

    @Override // android.view.View
    protected int computeHorizontalScrollOffset() {
        return this.mProvider.getScrollDelegate().computeHorizontalScrollOffset();
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        return this.mProvider.getScrollDelegate().computeVerticalScrollRange();
    }

    @Override // android.view.View
    protected int computeVerticalScrollOffset() {
        return this.mProvider.getScrollDelegate().computeVerticalScrollOffset();
    }

    @Override // android.view.View
    protected int computeVerticalScrollExtent() {
        return this.mProvider.getScrollDelegate().computeVerticalScrollExtent();
    }

    @Override // android.view.View
    public void computeScroll() {
        this.mProvider.getScrollDelegate().computeScroll();
    }

    @Override // android.view.View
    public boolean onHoverEvent(android.view.MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTrackballEvent(android.view.MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onTrackballEvent(motionEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().onKeyUp(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().onKeyMultiple(i, i2, keyEvent);
    }

    @Override // android.view.View
    public android.view.accessibility.AccessibilityNodeProvider getAccessibilityNodeProvider() {
        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = this.mProvider.getViewDelegate().getAccessibilityNodeProvider();
        return accessibilityNodeProvider == null ? super.getAccessibilityNodeProvider() : accessibilityNodeProvider;
    }

    @Override // android.widget.AbsoluteLayout, android.view.ViewGroup
    @java.lang.Deprecated
    public boolean shouldDelayChildPressedState() {
        return this.mProvider.getViewDelegate().shouldDelayChildPressedState();
    }

    @Override // android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.webkit.WebView.class.getName();
    }

    @Override // android.view.View
    public void onProvideVirtualStructure(android.view.ViewStructure viewStructure) {
        this.mProvider.getViewDelegate().onProvideVirtualStructure(viewStructure);
    }

    @Override // android.view.View
    public void onProvideAutofillVirtualStructure(android.view.ViewStructure viewStructure, int i) {
        this.mProvider.getViewDelegate().onProvideAutofillVirtualStructure(viewStructure, i);
    }

    @Override // android.view.View
    public void onProvideContentCaptureStructure(android.view.ViewStructure viewStructure, int i) {
        this.mProvider.getViewDelegate().onProvideContentCaptureStructure(viewStructure, i);
    }

    @Override // android.view.View
    public void autofill(android.util.SparseArray<android.view.autofill.AutofillValue> sparseArray) {
        this.mProvider.getViewDelegate().autofill(sparseArray);
    }

    @Override // android.view.View
    public boolean isVisibleToUserForAutofill(int i) {
        return this.mProvider.getViewDelegate().isVisibleToUserForAutofill(i);
    }

    @Override // android.view.View
    public void onCreateVirtualViewTranslationRequests(long[] jArr, int[] iArr, java.util.function.Consumer<android.view.translation.ViewTranslationRequest> consumer) {
        this.mProvider.getViewDelegate().onCreateVirtualViewTranslationRequests(jArr, iArr, consumer);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchCreateViewTranslationRequest(java.util.Map<android.view.autofill.AutofillId, long[]> map, int[] iArr, android.view.translation.TranslationCapability translationCapability, java.util.List<android.view.translation.ViewTranslationRequest> list) {
        super.dispatchCreateViewTranslationRequest(map, iArr, translationCapability, list);
        this.mProvider.getViewDelegate().dispatchCreateViewTranslationRequest(map, iArr, translationCapability, list);
    }

    @Override // android.view.View
    public void onVirtualViewTranslationResponses(android.util.LongSparseArray<android.view.translation.ViewTranslationResponse> longSparseArray) {
        this.mProvider.getViewDelegate().onVirtualViewTranslationResponses(longSparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        this.mProvider.getViewDelegate().onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        this.mProvider.getViewDelegate().onInitializeAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, android.os.Bundle bundle) {
        return this.mProvider.getViewDelegate().performAccessibilityAction(i, bundle);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onDrawVerticalScrollBar(android.graphics.Canvas canvas, android.graphics.drawable.Drawable drawable, int i, int i2, int i3, int i4) {
        this.mProvider.getViewDelegate().onDrawVerticalScrollBar(canvas, drawable, i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        this.mProvider.getViewDelegate().onOverScrolled(i, i2, z, z2);
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.mProvider.getViewDelegate().onWindowVisibilityChanged(i);
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        this.mProvider.getViewDelegate().onDraw(canvas);
    }

    @Override // android.view.View
    public boolean performLongClick() {
        return this.mProvider.getViewDelegate().performLongClick();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        this.mProvider.getViewDelegate().onConfigurationChanged(configuration);
    }

    @Override // android.view.View
    public android.view.inputmethod.InputConnection onCreateInputConnection(android.view.inputmethod.EditorInfo editorInfo) {
        return this.mProvider.getViewDelegate().onCreateInputConnection(editorInfo);
    }

    @Override // android.view.View
    public boolean onDragEvent(android.view.DragEvent dragEvent) {
        return this.mProvider.getViewDelegate().onDragEvent(dragEvent);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(android.view.View view, int i) {
        super.onVisibilityChanged(view, i);
        ensureProviderCreated();
        this.mProvider.getViewDelegate().onVisibilityChanged(view, i);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        this.mProvider.getViewDelegate().onWindowFocusChanged(z);
        super.onWindowFocusChanged(z);
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        this.mProvider.getViewDelegate().onFocusChanged(z, i, rect);
        super.onFocusChanged(z, i, rect);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public boolean setFrame(int i, int i2, int i3, int i4) {
        return this.mProvider.getViewDelegate().setFrame(i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mProvider.getViewDelegate().onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        this.mProvider.getViewDelegate().onScrollChanged(i, i2, i3, i4);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean requestFocus(int i, android.graphics.Rect rect) {
        return this.mProvider.getViewDelegate().requestFocus(i, rect);
    }

    @Override // android.widget.AbsoluteLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mProvider.getViewDelegate().onMeasure(i, i2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(android.view.View view, android.graphics.Rect rect, boolean z) {
        return this.mProvider.getViewDelegate().requestChildRectangleOnScreen(view, rect, z);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.mProvider.getViewDelegate().setBackgroundColor(i);
    }

    @Override // android.view.View
    public void setLayerType(int i, android.graphics.Paint paint) {
        super.setLayerType(i, paint);
        this.mProvider.getViewDelegate().setLayerType(i, paint);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        this.mProvider.getViewDelegate().preDispatchDraw(canvas);
        super.dispatchDraw(canvas);
    }

    @Override // android.view.View
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        this.mProvider.getViewDelegate().onStartTemporaryDetach();
    }

    @Override // android.view.View
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        this.mProvider.getViewDelegate().onFinishTemporaryDetach();
    }

    @Override // android.view.View
    public android.os.Handler getHandler() {
        return this.mProvider.getViewDelegate().getHandler(super.getHandler());
    }

    @Override // android.view.ViewGroup, android.view.View
    public android.view.View findFocus() {
        return this.mProvider.getViewDelegate().findFocus(super.findFocus());
    }

    public static android.content.pm.PackageInfo getCurrentWebViewPackage() {
        android.content.pm.PackageInfo loadedPackageInfo = android.webkit.WebViewFactory.getLoadedPackageInfo();
        if (loadedPackageInfo != null) {
            return loadedPackageInfo;
        }
        if (android.webkit.Flags.updateServiceIpcWrapper()) {
            android.webkit.WebViewUpdateManager webViewUpdateManager = android.webkit.WebViewUpdateManager.getInstance();
            if (webViewUpdateManager == null) {
                return null;
            }
            return webViewUpdateManager.getCurrentWebViewPackage();
        }
        android.webkit.IWebViewUpdateService updateService = android.webkit.WebViewFactory.getUpdateService();
        if (updateService == null) {
            return null;
        }
        try {
            return updateService.getCurrentWebViewPackage();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.View
    public void onActivityResult(int i, int i2, android.content.Intent intent) {
        this.mProvider.getViewDelegate().onActivityResult(i, i2, intent);
    }

    @Override // android.view.View
    public boolean onCheckIsTextEditor() {
        return this.mProvider.getViewDelegate().onCheckIsTextEditor();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        checkThread();
        viewHierarchyEncoder.addProperty("webview:contentHeight", this.mProvider.getContentHeight());
        viewHierarchyEncoder.addProperty("webview:contentWidth", this.mProvider.getContentWidth());
        viewHierarchyEncoder.addProperty("webview:scale", this.mProvider.getScale());
        viewHierarchyEncoder.addProperty("webview:title", this.mProvider.getTitle());
        viewHierarchyEncoder.addProperty("webview:url", this.mProvider.getUrl());
        viewHierarchyEncoder.addProperty("webview:originalUrl", this.mProvider.getOriginalUrl());
    }

    @Override // android.view.View
    public android.view.WindowInsets onApplyWindowInsets(android.view.WindowInsets windowInsets) {
        android.view.WindowInsets onApplyWindowInsets = this.mProvider.getViewDelegate().onApplyWindowInsets(windowInsets);
        return onApplyWindowInsets == null ? super.onApplyWindowInsets(windowInsets) : onApplyWindowInsets;
    }

    @Override // android.view.ViewGroup, android.view.View
    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        android.view.PointerIcon onResolvePointerIcon = this.mProvider.getViewDelegate().onResolvePointerIcon(motionEvent, i);
        if (onResolvePointerIcon != null) {
            return onResolvePointerIcon;
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }
}
