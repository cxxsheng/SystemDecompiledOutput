package com.android.internal.jank;

/* loaded from: classes4.dex */
class InteractionMonitorDebugOverlay implements android.view.WindowCallbacks {
    private static final int REASON_STILL_RUNNING = -1000;
    private static final java.lang.String TAG = "InteractionMonitorDebug";
    private static final java.lang.String TRACK_NAME = "InteractionJankMonitor";
    private final int mBgColor;
    private final android.graphics.Paint.FontMetrics mDebugFontMetrics;
    private final java.lang.Object mLock;
    private final java.lang.String mPackageName;
    private final double mYOffset;
    private final android.util.SparseIntArray mRunningCujs = new android.util.SparseIntArray();
    private android.os.Handler mHandler = null;
    private com.android.internal.jank.FrameTracker.ViewRootWrapper mViewRoot = null;
    private final android.graphics.Paint mDebugPaint = new android.graphics.Paint();

    InteractionMonitorDebugOverlay(java.lang.Object obj, int i, double d) {
        this.mLock = obj;
        this.mBgColor = i;
        this.mYOffset = d;
        this.mDebugPaint.setAntiAlias(false);
        this.mDebugFontMetrics = new android.graphics.Paint.FontMetrics();
        android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
        this.mPackageName = currentApplication == null ? "null" : currentApplication.getPackageName();
    }

    void dispose() {
        if (this.mViewRoot != null && this.mHandler != null) {
            this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.internal.jank.InteractionMonitorDebugOverlay$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.jank.InteractionMonitorDebugOverlay.this.lambda$dispose$0();
                }
            }, 500L);
            forceRedraw();
        }
        this.mHandler = null;
        this.mViewRoot = null;
        android.os.Trace.asyncTraceForTrackEnd(4096L, TRACK_NAME, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispose$0() {
        this.mViewRoot.removeWindowCallbacks(this);
    }

    private boolean attachViewRootIfNeeded(com.android.internal.jank.InteractionJankMonitor.RunningTracker runningTracker) {
        final com.android.internal.jank.FrameTracker.ViewRootWrapper viewRoot = runningTracker.mTracker.getViewRoot();
        if (this.mViewRoot != null || viewRoot == null) {
            return false;
        }
        android.os.Trace.asyncTraceForTrackBegin(4096L, TRACK_NAME, "DEBUG_OVERLAY_DRAW", 0);
        this.mHandler = runningTracker.mConfig.getHandler();
        this.mViewRoot = viewRoot;
        this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.internal.jank.InteractionMonitorDebugOverlay$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.jank.InteractionMonitorDebugOverlay.this.lambda$attachViewRootIfNeeded$1(viewRoot);
            }
        }, 500L);
        forceRedraw();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attachViewRootIfNeeded$1(com.android.internal.jank.FrameTracker.ViewRootWrapper viewRootWrapper) {
        viewRootWrapper.addWindowCallbacks(this);
    }

    private float getWidthOfLongestCujName(int i) {
        this.mDebugPaint.setTextSize(i);
        float f = 0.0f;
        for (int i2 = 0; i2 < this.mRunningCujs.size(); i2++) {
            float measureText = this.mDebugPaint.measureText(com.android.internal.jank.Cuj.getNameOfCuj(this.mRunningCujs.keyAt(i2)));
            if (measureText > f) {
                f = measureText;
            }
        }
        return f;
    }

    private float getTextHeight(int i) {
        this.mDebugPaint.setTextSize(i);
        this.mDebugPaint.getFontMetrics(this.mDebugFontMetrics);
        return this.mDebugFontMetrics.descent - this.mDebugFontMetrics.ascent;
    }

    private int dipToPx(int i) {
        if (this.mViewRoot != null) {
            return this.mViewRoot.dipToPx(i);
        }
        return i;
    }

    private void forceRedraw() {
        if (this.mViewRoot != null && this.mHandler != null) {
            this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.internal.jank.InteractionMonitorDebugOverlay$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.jank.InteractionMonitorDebugOverlay.this.lambda$forceRedraw$2();
                }
            }, 500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forceRedraw$2() {
        this.mViewRoot.requestInvalidateRootRenderNode();
        this.mViewRoot.getView().invalidate();
    }

    void onTrackerRemoved(int i, int i2, android.util.SparseArray<com.android.internal.jank.InteractionJankMonitor.RunningTracker> sparseArray) {
        boolean z;
        synchronized (this.mLock) {
            this.mRunningCujs.put(i, i2);
            boolean isLoggable = android.util.Log.isLoggable(TAG, 3);
            if (isLoggable) {
                android.util.Log.d(TAG, com.android.internal.jank.Cuj.getNameOfCuj(i) + (i2 == 0 ? " ended" : " cancelled"));
            }
            if (this.mRunningCujs.indexOfValue(-1000) < 0) {
                if (isLoggable) {
                    android.util.Log.d(TAG, "All CUJs ended");
                }
                this.mRunningCujs.clear();
                dispose();
            } else {
                if (this.mViewRoot != null) {
                    for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                        if (this.mViewRoot.equals(sparseArray.valueAt(i3).mTracker.getViewRoot())) {
                            z = false;
                            break;
                        }
                    }
                }
                z = true;
                if (z) {
                    dispose();
                    for (int i4 = 0; i4 < sparseArray.size() && !attachViewRootIfNeeded(sparseArray.valueAt(i4)); i4++) {
                    }
                } else {
                    forceRedraw();
                }
            }
        }
    }

    void onTrackerAdded(int i, com.android.internal.jank.InteractionJankMonitor.RunningTracker runningTracker) {
        if (android.util.Log.isLoggable(TAG, 3)) {
            android.util.Log.d(TAG, com.android.internal.jank.Cuj.getNameOfCuj(i) + " started");
        }
        synchronized (this.mLock) {
            this.mRunningCujs.put(i, -1000);
            attachViewRootIfNeeded(runningTracker);
            forceRedraw();
        }
    }

    @Override // android.view.WindowCallbacks
    public void onWindowSizeIsChanging(android.graphics.Rect rect, boolean z, android.graphics.Rect rect2, android.graphics.Rect rect3) {
    }

    @Override // android.view.WindowCallbacks
    public void onWindowDragResizeStart(android.graphics.Rect rect, boolean z, android.graphics.Rect rect2, android.graphics.Rect rect3) {
    }

    @Override // android.view.WindowCallbacks
    public void onWindowDragResizeEnd() {
    }

    @Override // android.view.WindowCallbacks
    public boolean onContentDrawn(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override // android.view.WindowCallbacks
    public void onRequestDraw(boolean z) {
    }

    @Override // android.view.WindowCallbacks
    public void onPostDraw(android.graphics.RecordingCanvas recordingCanvas) {
        int dipToPx = dipToPx(5);
        int height = recordingCanvas.getHeight();
        int width = recordingCanvas.getWidth();
        int i = (int) (height * this.mYOffset);
        int dipToPx2 = dipToPx(12);
        int dipToPx3 = dipToPx(18);
        float textHeight = getTextHeight(dipToPx3);
        float textHeight2 = getTextHeight(dipToPx2);
        synchronized (this.mLock) {
            float widthOfLongestCujName = getWidthOfLongestCujName(dipToPx3);
            recordingCanvas.translate((int) ((width - widthOfLongestCujName) / 2.0f), i);
            this.mDebugPaint.setColor(this.mBgColor);
            int i2 = -dipToPx;
            float f = dipToPx * 2;
            recordingCanvas.drawRect(i2 * 2, i2, widthOfLongestCujName + f, f + textHeight2 + (this.mRunningCujs.size() * textHeight), this.mDebugPaint);
            this.mDebugPaint.setTextSize(dipToPx2);
            this.mDebugPaint.setColor(-16777216);
            this.mDebugPaint.setStrikeThruText(false);
            recordingCanvas.translate(0.0f, textHeight2);
            recordingCanvas.drawText("package:" + this.mPackageName, 0.0f, 0.0f, this.mDebugPaint);
            this.mDebugPaint.setTextSize(dipToPx3);
            for (int i3 = 0; i3 < this.mRunningCujs.size(); i3++) {
                int valueAt = this.mRunningCujs.valueAt(i3);
                if (valueAt == -1000) {
                    this.mDebugPaint.setColor(-16777216);
                    this.mDebugPaint.setStrikeThruText(false);
                } else if (valueAt == 0) {
                    this.mDebugPaint.setColor(android.graphics.Color.GRAY);
                    this.mDebugPaint.setStrikeThruText(false);
                } else {
                    this.mDebugPaint.setColor(-65536);
                    this.mDebugPaint.setStrikeThruText(true);
                }
                java.lang.String nameOfCuj = com.android.internal.jank.Cuj.getNameOfCuj(this.mRunningCujs.keyAt(i3));
                recordingCanvas.translate(0.0f, textHeight);
                recordingCanvas.drawText(nameOfCuj, 0.0f, 0.0f, this.mDebugPaint);
            }
        }
    }
}
