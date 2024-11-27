package com.android.internal.view;

/* loaded from: classes5.dex */
public class WebViewCaptureHelper implements com.android.internal.view.ScrollCaptureViewHelper<android.webkit.WebView> {
    private static final java.lang.String TAG = "WebViewScrollCapture";
    private int mOriginScrollX;
    private int mOriginScrollY;
    private final android.graphics.Rect mRequestWebViewLocal = new android.graphics.Rect();
    private final android.graphics.Rect mWebViewBounds = new android.graphics.Rect();

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public /* bridge */ /* synthetic */ void onScrollRequested(android.webkit.WebView webView, android.graphics.Rect rect, android.graphics.Rect rect2, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer consumer) {
        onScrollRequested2(webView, rect, rect2, cancellationSignal, (java.util.function.Consumer<com.android.internal.view.ScrollCaptureViewHelper.ScrollResult>) consumer);
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public boolean onAcceptSession(android.webkit.WebView webView) {
        return webView.isVisibleToUser() && ((float) webView.getContentHeight()) * webView.getScale() > ((float) webView.getHeight());
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForStart(android.webkit.WebView webView, android.graphics.Rect rect) {
        this.mOriginScrollX = webView.getScrollX();
        this.mOriginScrollY = webView.getScrollY();
    }

    /* renamed from: onScrollRequested, reason: avoid collision after fix types in other method */
    public void onScrollRequested2(android.webkit.WebView webView, android.graphics.Rect rect, android.graphics.Rect rect2, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<com.android.internal.view.ScrollCaptureViewHelper.ScrollResult> consumer) {
        int scrollY = webView.getScrollY() - this.mOriginScrollY;
        com.android.internal.view.ScrollCaptureViewHelper.ScrollResult scrollResult = new com.android.internal.view.ScrollCaptureViewHelper.ScrollResult();
        scrollResult.requestedArea = new android.graphics.Rect(rect2);
        scrollResult.availableArea = new android.graphics.Rect();
        scrollResult.scrollDelta = scrollY;
        this.mWebViewBounds.set(0, 0, webView.getWidth(), webView.getHeight());
        if (!webView.isVisibleToUser()) {
            consumer.accept(scrollResult);
        }
        this.mRequestWebViewLocal.set(rect2);
        this.mRequestWebViewLocal.offset(0, -scrollY);
        int constrain = android.util.MathUtils.constrain(this.mRequestWebViewLocal.centerY() - this.mWebViewBounds.centerY(), java.lang.Math.min(0, -webView.getScrollY()), java.lang.Math.max(0, (((int) (webView.getContentHeight() * webView.getScale())) - webView.getHeight()) - webView.getScrollY()));
        webView.scrollBy(this.mOriginScrollX, constrain);
        int scrollY2 = webView.getScrollY() - this.mOriginScrollY;
        this.mRequestWebViewLocal.offset(0, -constrain);
        scrollResult.scrollDelta = scrollY2;
        if (this.mRequestWebViewLocal.intersect(this.mWebViewBounds)) {
            scrollResult.availableArea = new android.graphics.Rect(this.mRequestWebViewLocal);
            scrollResult.availableArea.offset(0, scrollResult.scrollDelta);
        }
        consumer.accept(scrollResult);
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForEnd(android.webkit.WebView webView) {
        webView.scrollTo(this.mOriginScrollX, this.mOriginScrollY);
    }
}
