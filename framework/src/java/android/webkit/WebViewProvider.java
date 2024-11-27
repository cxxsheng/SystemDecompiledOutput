package android.webkit;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public interface WebViewProvider {

    public interface ScrollDelegate {
        int computeHorizontalScrollOffset();

        int computeHorizontalScrollRange();

        void computeScroll();

        int computeVerticalScrollExtent();

        int computeVerticalScrollOffset();

        int computeVerticalScrollRange();
    }

    void addJavascriptInterface(java.lang.Object obj, java.lang.String str);

    boolean canGoBack();

    boolean canGoBackOrForward(int i);

    boolean canGoForward();

    boolean canZoomIn();

    boolean canZoomOut();

    android.graphics.Picture capturePicture();

    void clearCache(boolean z);

    void clearFormData();

    void clearHistory();

    void clearMatches();

    void clearSslPreferences();

    void clearView();

    android.webkit.WebBackForwardList copyBackForwardList();

    android.print.PrintDocumentAdapter createPrintDocumentAdapter(java.lang.String str);

    android.webkit.WebMessagePort[] createWebMessageChannel();

    void destroy();

    void documentHasImages(android.os.Message message);

    void dumpViewHierarchyWithProperties(java.io.BufferedWriter bufferedWriter, int i);

    void evaluateJavaScript(java.lang.String str, android.webkit.ValueCallback<java.lang.String> valueCallback);

    int findAll(java.lang.String str);

    void findAllAsync(java.lang.String str);

    android.view.View findHierarchyView(java.lang.String str, int i);

    void findNext(boolean z);

    void flingScroll(int i, int i2);

    void freeMemory();

    android.net.http.SslCertificate getCertificate();

    int getContentHeight();

    int getContentWidth();

    android.graphics.Bitmap getFavicon();

    android.webkit.WebView.HitTestResult getHitTestResult();

    java.lang.String[] getHttpAuthUsernamePassword(java.lang.String str, java.lang.String str2);

    java.lang.String getOriginalUrl();

    int getProgress();

    boolean getRendererPriorityWaivedWhenNotVisible();

    int getRendererRequestedPriority();

    float getScale();

    android.webkit.WebViewProvider.ScrollDelegate getScrollDelegate();

    android.webkit.WebSettings getSettings();

    java.lang.String getTitle();

    java.lang.String getTouchIconUrl();

    java.lang.String getUrl();

    android.webkit.WebViewProvider.ViewDelegate getViewDelegate();

    int getVisibleTitleHeight();

    android.webkit.WebChromeClient getWebChromeClient();

    android.webkit.WebViewClient getWebViewClient();

    android.webkit.WebViewRenderProcess getWebViewRenderProcess();

    android.webkit.WebViewRenderProcessClient getWebViewRenderProcessClient();

    android.view.View getZoomControls();

    void goBack();

    void goBackOrForward(int i);

    void goForward();

    void init(java.util.Map<java.lang.String, java.lang.Object> map, boolean z);

    void insertVisualStateCallback(long j, android.webkit.WebView.VisualStateCallback visualStateCallback);

    void invokeZoomPicker();

    boolean isPaused();

    boolean isPrivateBrowsingEnabled();

    void loadData(java.lang.String str, java.lang.String str2, java.lang.String str3);

    void loadDataWithBaseURL(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5);

    void loadUrl(java.lang.String str);

    void loadUrl(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map);

    void notifyFindDialogDismissed();

    void onPause();

    void onResume();

    boolean overlayHorizontalScrollbar();

    boolean overlayVerticalScrollbar();

    boolean pageDown(boolean z);

    boolean pageUp(boolean z);

    void pauseTimers();

    void postMessageToMainFrame(android.webkit.WebMessage webMessage, android.net.Uri uri);

    void postUrl(java.lang.String str, byte[] bArr);

    void reload();

    void removeJavascriptInterface(java.lang.String str);

    void requestFocusNodeHref(android.os.Message message);

    void requestImageRef(android.os.Message message);

    boolean restorePicture(android.os.Bundle bundle, java.io.File file);

    android.webkit.WebBackForwardList restoreState(android.os.Bundle bundle);

    void resumeTimers();

    void savePassword(java.lang.String str, java.lang.String str2, java.lang.String str3);

    boolean savePicture(android.os.Bundle bundle, java.io.File file);

    android.webkit.WebBackForwardList saveState(android.os.Bundle bundle);

    void saveWebArchive(java.lang.String str);

    void saveWebArchive(java.lang.String str, boolean z, android.webkit.ValueCallback<java.lang.String> valueCallback);

    void setCertificate(android.net.http.SslCertificate sslCertificate);

    void setDownloadListener(android.webkit.DownloadListener downloadListener);

    void setFindListener(android.webkit.WebView.FindListener findListener);

    void setHorizontalScrollbarOverlay(boolean z);

    void setHttpAuthUsernamePassword(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4);

    void setInitialScale(int i);

    void setMapTrackballToArrowKeys(boolean z);

    void setNetworkAvailable(boolean z);

    void setPictureListener(android.webkit.WebView.PictureListener pictureListener);

    void setRendererPriorityPolicy(int i, boolean z);

    void setVerticalScrollbarOverlay(boolean z);

    void setWebChromeClient(android.webkit.WebChromeClient webChromeClient);

    void setWebViewClient(android.webkit.WebViewClient webViewClient);

    void setWebViewRenderProcessClient(java.util.concurrent.Executor executor, android.webkit.WebViewRenderProcessClient webViewRenderProcessClient);

    boolean showFindDialog(java.lang.String str, boolean z);

    void stopLoading();

    boolean zoomBy(float f);

    boolean zoomIn();

    boolean zoomOut();

    default void setTextClassifier(android.view.textclassifier.TextClassifier textClassifier) {
    }

    default android.view.textclassifier.TextClassifier getTextClassifier() {
        return android.view.textclassifier.TextClassifier.NO_OP;
    }

    public interface ViewDelegate {
        boolean dispatchKeyEvent(android.view.KeyEvent keyEvent);

        android.view.View findFocus(android.view.View view);

        android.view.accessibility.AccessibilityNodeProvider getAccessibilityNodeProvider();

        android.os.Handler getHandler(android.os.Handler handler);

        void onActivityResult(int i, int i2, android.content.Intent intent);

        void onAttachedToWindow();

        void onConfigurationChanged(android.content.res.Configuration configuration);

        android.view.inputmethod.InputConnection onCreateInputConnection(android.view.inputmethod.EditorInfo editorInfo);

        void onDetachedFromWindow();

        boolean onDragEvent(android.view.DragEvent dragEvent);

        void onDraw(android.graphics.Canvas canvas);

        void onDrawVerticalScrollBar(android.graphics.Canvas canvas, android.graphics.drawable.Drawable drawable, int i, int i2, int i3, int i4);

        void onFinishTemporaryDetach();

        void onFocusChanged(boolean z, int i, android.graphics.Rect rect);

        boolean onGenericMotionEvent(android.view.MotionEvent motionEvent);

        boolean onHoverEvent(android.view.MotionEvent motionEvent);

        void onInitializeAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent);

        void onInitializeAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo);

        boolean onKeyDown(int i, android.view.KeyEvent keyEvent);

        boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent);

        boolean onKeyUp(int i, android.view.KeyEvent keyEvent);

        void onMeasure(int i, int i2);

        void onOverScrolled(int i, int i2, boolean z, boolean z2);

        void onProvideVirtualStructure(android.view.ViewStructure viewStructure);

        void onScrollChanged(int i, int i2, int i3, int i4);

        void onSizeChanged(int i, int i2, int i3, int i4);

        void onStartTemporaryDetach();

        boolean onTouchEvent(android.view.MotionEvent motionEvent);

        boolean onTrackballEvent(android.view.MotionEvent motionEvent);

        void onVisibilityChanged(android.view.View view, int i);

        void onWindowFocusChanged(boolean z);

        void onWindowVisibilityChanged(int i);

        boolean performAccessibilityAction(int i, android.os.Bundle bundle);

        boolean performLongClick();

        void preDispatchDraw(android.graphics.Canvas canvas);

        boolean requestChildRectangleOnScreen(android.view.View view, android.graphics.Rect rect, boolean z);

        boolean requestFocus(int i, android.graphics.Rect rect);

        void setBackgroundColor(int i);

        boolean setFrame(int i, int i2, int i3, int i4);

        void setLayerType(int i, android.graphics.Paint paint);

        void setLayoutParams(android.view.ViewGroup.LayoutParams layoutParams);

        void setOverScrollMode(int i);

        void setScrollBarStyle(int i);

        boolean shouldDelayChildPressedState();

        default void onProvideAutofillVirtualStructure(android.view.ViewStructure viewStructure, int i) {
        }

        default void autofill(android.util.SparseArray<android.view.autofill.AutofillValue> sparseArray) {
        }

        default boolean isVisibleToUserForAutofill(int i) {
            return true;
        }

        default void onProvideContentCaptureStructure(android.view.ViewStructure viewStructure, int i) {
        }

        default void onCreateVirtualViewTranslationRequests(long[] jArr, int[] iArr, java.util.function.Consumer<android.view.translation.ViewTranslationRequest> consumer) {
        }

        default void onVirtualViewTranslationResponses(android.util.LongSparseArray<android.view.translation.ViewTranslationResponse> longSparseArray) {
        }

        default void dispatchCreateViewTranslationRequest(java.util.Map<android.view.autofill.AutofillId, long[]> map, int[] iArr, android.view.translation.TranslationCapability translationCapability, java.util.List<android.view.translation.ViewTranslationRequest> list) {
        }

        default void onMovedToDisplay(int i, android.content.res.Configuration configuration) {
        }

        default boolean onCheckIsTextEditor() {
            return false;
        }

        default android.view.WindowInsets onApplyWindowInsets(android.view.WindowInsets windowInsets) {
            return null;
        }

        default android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
            return null;
        }
    }
}
