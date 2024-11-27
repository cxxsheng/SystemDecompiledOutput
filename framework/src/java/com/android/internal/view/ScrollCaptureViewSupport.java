package com.android.internal.view;

/* loaded from: classes5.dex */
public class ScrollCaptureViewSupport<V extends android.view.View> implements android.view.ScrollCaptureCallback {
    private static final java.lang.String SETTING_CAPTURE_DELAY = "screenshot.scroll_capture_delay";
    private static final long SETTING_CAPTURE_DELAY_DEFAULT = 60;
    private static final java.lang.String TAG = "SCViewSupport";
    private boolean mEnded;
    private final long mPostScrollDelayMillis;
    private final com.android.internal.view.ScrollCaptureViewSupport.ViewRenderer mRenderer = new com.android.internal.view.ScrollCaptureViewSupport.ViewRenderer();
    private boolean mStarted;
    private final com.android.internal.view.ScrollCaptureViewHelper<V> mViewHelper;
    private final java.lang.ref.WeakReference<V> mWeakView;

    ScrollCaptureViewSupport(V v, com.android.internal.view.ScrollCaptureViewHelper<V> scrollCaptureViewHelper) {
        this.mWeakView = new java.lang.ref.WeakReference<>(v);
        this.mViewHelper = scrollCaptureViewHelper;
        this.mPostScrollDelayMillis = android.provider.Settings.Global.getLong(v.getContext().getContentResolver(), SETTING_CAPTURE_DELAY, SETTING_CAPTURE_DELAY_DEFAULT);
        android.util.Log.d(TAG, "screenshot.scroll_capture_delay = " + this.mPostScrollDelayMillis);
    }

    private static int getColorMode(android.view.View view) {
        android.content.Context context = view.getContext();
        int colorMode = view.getViewRootImpl().mWindowAttributes.getColorMode();
        if (!context.getResources().getConfiguration().isScreenWideColorGamut()) {
            return 0;
        }
        return colorMode;
    }

    public static android.graphics.Rect transformFromRequestToContainer(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        android.graphics.Rect rect3 = new android.graphics.Rect(rect2);
        rect3.offset(0, -i);
        rect3.offset(rect.left, rect.top);
        return rect3;
    }

    public static android.graphics.Rect transformFromContainerToRequest(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        android.graphics.Rect rect3 = new android.graphics.Rect(rect2);
        rect3.offset(-rect.left, -rect.top);
        rect3.offset(0, i);
        return rect3;
    }

    public static int computeScrollAmount(android.graphics.Rect rect, android.graphics.Rect rect2) {
        int height = rect.height();
        int i = rect.top;
        int i2 = rect.bottom;
        if (rect2.bottom > i2 && rect2.top > i) {
            if (rect2.height() > height) {
                return 0 + (rect2.top - i);
            }
            return 0 + (rect2.bottom - i2);
        }
        if (rect2.top >= i || rect2.bottom >= i2) {
            return 0;
        }
        if (rect2.height() > height) {
            return 0 - (i2 - rect2.bottom);
        }
        return 0 - (i - rect2.top);
    }

    public static android.view.View findScrollingReferenceView(android.view.ViewGroup viewGroup, int i) {
        viewGroup.getLocalVisibleRect(new android.graphics.Rect());
        int childCount = viewGroup.getChildCount();
        android.view.View view = null;
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = viewGroup.getChildAt(i2);
            if (view != null) {
                if (i < 0) {
                    if (childAt.getTop() >= view.getTop()) {
                    }
                } else if (childAt.getBottom() <= view.getBottom()) {
                }
            }
            view = childAt;
        }
        return view;
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureSearch(android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<android.graphics.Rect> consumer) {
        if (cancellationSignal.isCanceled()) {
            return;
        }
        V v = this.mWeakView.get();
        this.mStarted = false;
        this.mEnded = false;
        if (v != null && v.isVisibleToUser() && this.mViewHelper.onAcceptSession(v)) {
            consumer.accept(this.mViewHelper.onComputeScrollBounds(v));
        } else {
            consumer.accept(null);
        }
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureStart(android.view.ScrollCaptureSession scrollCaptureSession, android.os.CancellationSignal cancellationSignal, java.lang.Runnable runnable) {
        if (cancellationSignal.isCanceled()) {
            return;
        }
        V v = this.mWeakView.get();
        this.mEnded = false;
        this.mStarted = true;
        if (v != null && v.isVisibleToUser()) {
            this.mRenderer.setSurface(scrollCaptureSession.getSurface());
            this.mViewHelper.onPrepareForStart(v, scrollCaptureSession.getScrollBounds());
        }
        runnable.run();
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureImageRequest(android.view.ScrollCaptureSession scrollCaptureSession, final android.os.CancellationSignal cancellationSignal, android.graphics.Rect rect, final java.util.function.Consumer<android.graphics.Rect> consumer) {
        if (cancellationSignal.isCanceled()) {
            android.util.Log.w(TAG, "onScrollCaptureImageRequest: cancelled!");
            return;
        }
        final V v = this.mWeakView.get();
        if (v == null || !v.isVisibleToUser()) {
            consumer.accept(new android.graphics.Rect());
        } else {
            this.mViewHelper.onScrollRequested(v, scrollCaptureSession.getScrollBounds(), rect, cancellationSignal, new java.util.function.Consumer() { // from class: com.android.internal.view.ScrollCaptureViewSupport$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.internal.view.ScrollCaptureViewSupport.this.lambda$onScrollCaptureImageRequest$0(v, cancellationSignal, consumer, (com.android.internal.view.ScrollCaptureViewHelper.ScrollResult) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onScrollResult, reason: merged with bridge method [inline-methods] */
    public void lambda$onScrollCaptureImageRequest$0(final com.android.internal.view.ScrollCaptureViewHelper.ScrollResult scrollResult, final V v, android.os.CancellationSignal cancellationSignal, final java.util.function.Consumer<android.graphics.Rect> consumer) {
        if (cancellationSignal.isCanceled()) {
            android.util.Log.w(TAG, "onScrollCaptureImageRequest: cancelled! skipping render.");
        } else {
            if (scrollResult.availableArea.isEmpty()) {
                consumer.accept(scrollResult.availableArea);
                return;
            }
            final android.graphics.Rect rect = new android.graphics.Rect(scrollResult.availableArea);
            rect.offset(0, -scrollResult.scrollDelta);
            v.postOnAnimationDelayed(new java.lang.Runnable() { // from class: com.android.internal.view.ScrollCaptureViewSupport$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.view.ScrollCaptureViewSupport.this.lambda$onScrollResult$1(scrollResult, v, rect, consumer);
                }
            }, this.mPostScrollDelayMillis);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doCapture, reason: merged with bridge method [inline-methods] */
    public void lambda$onScrollResult$1(com.android.internal.view.ScrollCaptureViewHelper.ScrollResult scrollResult, V v, android.graphics.Rect rect, java.util.function.Consumer<android.graphics.Rect> consumer) {
        int renderView = this.mRenderer.renderView(v, rect);
        if (renderView == 0 || renderView == 1) {
            consumer.accept(new android.graphics.Rect(scrollResult.availableArea));
        } else {
            android.util.Log.e(TAG, "syncAndDraw(): SyncAndDrawResult = " + renderView);
            consumer.accept(new android.graphics.Rect());
        }
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureEnd(java.lang.Runnable runnable) {
        V v = this.mWeakView.get();
        if (this.mStarted && !this.mEnded) {
            if (v != null) {
                this.mViewHelper.onPrepareForEnd(v);
                v.invalidate();
            }
            this.mEnded = true;
            this.mRenderer.destroy();
        }
        runnable.run();
    }

    static final class ViewRenderer {
        private static final float AMBIENT_SHADOW_ALPHA = 0.039f;
        private static final float LIGHT_RADIUS_DP = 800.0f;
        private static final float LIGHT_Z_DP = 400.0f;
        private static final float SPOT_SHADOW_ALPHA = 0.039f;
        private static final java.lang.String TAG = "ViewRenderer";
        private final android.graphics.RenderNode mCaptureRenderNode;
        private android.view.Surface mSurface;
        private final android.graphics.Rect mTempRect = new android.graphics.Rect();
        private final int[] mTempLocation = new int[2];
        private long mLastRenderedSourceDrawingId = -1;
        private final android.graphics.HardwareRenderer mRenderer = new android.graphics.HardwareRenderer();

        ViewRenderer() {
            this.mRenderer.setName("ScrollCapture");
            this.mCaptureRenderNode = new android.graphics.RenderNode("ScrollCaptureRoot");
            this.mRenderer.setContentRoot(this.mCaptureRenderNode);
            this.mRenderer.setOpaque(false);
        }

        public void setSurface(android.view.Surface surface) {
            this.mSurface = surface;
            this.mRenderer.setSurface(surface);
        }

        private boolean updateForView(android.view.View view) {
            if (this.mLastRenderedSourceDrawingId == view.getUniqueDrawingId()) {
                return false;
            }
            this.mLastRenderedSourceDrawingId = view.getUniqueDrawingId();
            return true;
        }

        private void setupLighting(android.view.View view) {
            this.mLastRenderedSourceDrawingId = view.getUniqueDrawingId();
            android.util.DisplayMetrics displayMetrics = view.getResources().getDisplayMetrics();
            view.getLocationOnScreen(this.mTempLocation);
            this.mRenderer.setLightSourceGeometry((displayMetrics.widthPixels / 2.0f) - this.mTempLocation[0], displayMetrics.heightPixels - this.mTempLocation[1], (int) (displayMetrics.density * 400.0f), (int) (displayMetrics.density * LIGHT_RADIUS_DP));
            this.mRenderer.setLightSourceAlpha(0.039f, 0.039f);
        }

        private void updateRootNode(android.view.View view, android.graphics.Rect rect) {
            android.view.View rootView = view.getRootView();
            transformToRoot(view, rect, this.mTempRect);
            this.mCaptureRenderNode.setPosition(0, 0, this.mTempRect.width(), this.mTempRect.height());
            android.graphics.RecordingCanvas beginRecording = this.mCaptureRenderNode.beginRecording();
            beginRecording.enableZ();
            beginRecording.translate(-this.mTempRect.left, -this.mTempRect.top);
            android.graphics.RenderNode updateDisplayListIfDirty = rootView.updateDisplayListIfDirty();
            if (updateDisplayListIfDirty.hasDisplayList()) {
                beginRecording.drawRenderNode(updateDisplayListIfDirty);
            }
            this.mCaptureRenderNode.endRecording();
        }

        public int renderView(android.view.View view, android.graphics.Rect rect) {
            android.graphics.HardwareRenderer.FrameRenderRequest createRenderRequest = this.mRenderer.createRenderRequest();
            createRenderRequest.setVsyncTime(java.lang.System.nanoTime());
            if (updateForView(view)) {
                setupLighting(view);
            }
            view.invalidate();
            updateRootNode(view, rect);
            return createRenderRequest.syncAndDraw();
        }

        public void trimMemory() {
            this.mRenderer.clearContent();
        }

        public void destroy() {
            this.mSurface = null;
            this.mRenderer.destroy();
        }

        private void transformToRoot(android.view.View view, android.graphics.Rect rect, android.graphics.Rect rect2) {
            view.getLocationInWindow(this.mTempLocation);
            rect2.set(rect);
            rect2.offset(this.mTempLocation[0], this.mTempLocation[1]);
        }

        public void setColorMode(int i) {
            this.mRenderer.setColorMode(i);
        }
    }

    public java.lang.String toString() {
        return "ScrollCaptureViewSupport{view=" + this.mWeakView.get() + ", helper=" + this.mViewHelper + '}';
    }
}
