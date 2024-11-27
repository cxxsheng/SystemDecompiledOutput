package com.android.internal.policy;

/* loaded from: classes5.dex */
public class BackdropFrameRenderer extends java.lang.Thread implements android.view.Choreographer.FrameCallback {
    private android.graphics.drawable.Drawable mCaptionBackgroundDrawable;
    private android.view.Choreographer mChoreographer;
    private com.android.internal.policy.DecorView mDecorView;
    private android.graphics.RenderNode mFrameAndBackdropNode;
    private boolean mFullscreen;
    private int mLastCaptionHeight;
    private int mLastContentHeight;
    private int mLastContentWidth;
    private int mLastXOffset;
    private int mLastYOffset;
    private android.graphics.drawable.ColorDrawable mNavigationBarColor;
    private boolean mOldFullscreen;
    private android.view.ThreadedRenderer mRenderer;
    private boolean mReportNextDraw;
    private android.graphics.drawable.Drawable mResizingBackgroundDrawable;
    private android.graphics.drawable.ColorDrawable mStatusBarColor;
    private android.graphics.RenderNode mSystemBarBackgroundNode;
    private android.graphics.drawable.Drawable mUserCaptionBackgroundDrawable;
    private final android.graphics.Rect mTargetRect = new android.graphics.Rect();
    private final android.graphics.Rect mOldTargetRect = new android.graphics.Rect();
    private final android.graphics.Rect mNewTargetRect = new android.graphics.Rect();
    private final android.graphics.Rect mOldSystemBarInsets = new android.graphics.Rect();
    private final android.graphics.Rect mSystemBarInsets = new android.graphics.Rect();
    private final android.graphics.Rect mTmpRect = new android.graphics.Rect();

    public BackdropFrameRenderer(com.android.internal.policy.DecorView decorView, android.view.ThreadedRenderer threadedRenderer, android.graphics.Rect rect, android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, android.graphics.drawable.Drawable drawable3, int i, int i2, boolean z, android.graphics.Insets insets) {
        setName("ResizeFrame");
        this.mRenderer = threadedRenderer;
        onResourcesLoaded(decorView, drawable, drawable2, drawable3, i, i2);
        this.mFrameAndBackdropNode = android.graphics.RenderNode.create("FrameAndBackdropNode", null);
        this.mRenderer.addRenderNode(this.mFrameAndBackdropNode, true);
        this.mTargetRect.set(rect);
        this.mFullscreen = z;
        this.mOldFullscreen = z;
        this.mSystemBarInsets.set(insets.toRect());
        this.mOldSystemBarInsets.set(insets.toRect());
        start();
    }

    void onResourcesLoaded(com.android.internal.policy.DecorView decorView, android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, android.graphics.drawable.Drawable drawable3, int i, int i2) {
        android.graphics.drawable.Drawable drawable4;
        android.graphics.drawable.Drawable drawable5;
        android.graphics.drawable.Drawable drawable6;
        synchronized (this) {
            this.mDecorView = decorView;
            if (drawable != null && drawable.getConstantState() != null) {
                drawable4 = drawable.getConstantState().newDrawable();
            } else {
                drawable4 = null;
            }
            this.mResizingBackgroundDrawable = drawable4;
            if (drawable2 != null && drawable2.getConstantState() != null) {
                drawable5 = drawable2.getConstantState().newDrawable();
            } else {
                drawable5 = null;
            }
            this.mCaptionBackgroundDrawable = drawable5;
            if (drawable3 != null && drawable3.getConstantState() != null) {
                drawable6 = drawable3.getConstantState().newDrawable();
            } else {
                drawable6 = null;
            }
            this.mUserCaptionBackgroundDrawable = drawable6;
            if (this.mCaptionBackgroundDrawable == null) {
                this.mCaptionBackgroundDrawable = this.mResizingBackgroundDrawable;
            }
            if (i != 0) {
                this.mStatusBarColor = new android.graphics.drawable.ColorDrawable(i);
                addSystemBarNodeIfNeeded();
            } else {
                this.mStatusBarColor = null;
            }
            if (i2 != 0) {
                this.mNavigationBarColor = new android.graphics.drawable.ColorDrawable(i2);
                addSystemBarNodeIfNeeded();
            } else {
                this.mNavigationBarColor = null;
            }
        }
    }

    private void addSystemBarNodeIfNeeded() {
        if (this.mSystemBarBackgroundNode != null) {
            return;
        }
        this.mSystemBarBackgroundNode = android.graphics.RenderNode.create("SystemBarBackgroundNode", null);
        this.mRenderer.addRenderNode(this.mSystemBarBackgroundNode, false);
    }

    public void setTargetRect(android.graphics.Rect rect, boolean z, android.graphics.Rect rect2) {
        synchronized (this) {
            this.mFullscreen = z;
            this.mTargetRect.set(rect);
            this.mSystemBarInsets.set(rect2);
            pingRenderLocked(false);
        }
    }

    public void onConfigurationChange() {
        synchronized (this) {
            if (this.mRenderer != null) {
                this.mOldTargetRect.set(0, 0, 0, 0);
                pingRenderLocked(false);
            }
        }
    }

    void releaseRenderer() {
        synchronized (this) {
            if (this.mRenderer != null) {
                this.mRenderer.setContentDrawBounds(0, 0, 0, 0);
                this.mRenderer.removeRenderNode(this.mFrameAndBackdropNode);
                if (this.mSystemBarBackgroundNode != null) {
                    this.mRenderer.removeRenderNode(this.mSystemBarBackgroundNode);
                }
                this.mRenderer = null;
                pingRenderLocked(false);
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            android.os.Looper.prepare();
            synchronized (this) {
                if (this.mRenderer == null) {
                    return;
                }
                this.mChoreographer = android.view.Choreographer.getInstance();
                android.os.Looper.loop();
                synchronized (this) {
                    this.mChoreographer = null;
                    android.view.Choreographer.releaseInstance();
                }
            }
        } finally {
            releaseRenderer();
        }
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long j) {
        synchronized (this) {
            if (this.mRenderer == null) {
                reportDrawIfNeeded();
                android.os.Looper.myLooper().quit();
            } else {
                doFrameUncheckedLocked();
            }
        }
    }

    private void doFrameUncheckedLocked() {
        this.mNewTargetRect.set(this.mTargetRect);
        if (!this.mNewTargetRect.equals(this.mOldTargetRect) || this.mOldFullscreen != this.mFullscreen || !this.mSystemBarInsets.equals(this.mOldSystemBarInsets) || this.mReportNextDraw) {
            this.mOldFullscreen = this.mFullscreen;
            this.mOldTargetRect.set(this.mNewTargetRect);
            this.mOldSystemBarInsets.set(this.mSystemBarInsets);
            redrawLocked(this.mNewTargetRect, this.mFullscreen);
        }
    }

    boolean onContentDrawn(int i, int i2, int i3, int i4) {
        boolean z;
        synchronized (this) {
            z = true;
            boolean z2 = this.mLastContentWidth == 0;
            this.mLastContentWidth = i3;
            this.mLastContentHeight = i4 - this.mLastCaptionHeight;
            this.mLastXOffset = i;
            this.mLastYOffset = i2;
            this.mRenderer.setContentDrawBounds(this.mLastXOffset, this.mLastYOffset, this.mLastXOffset + this.mLastContentWidth, this.mLastYOffset + this.mLastCaptionHeight + this.mLastContentHeight);
            if (!z2 || (this.mLastCaptionHeight == 0 && this.mDecorView.isShowingCaption())) {
                z = false;
            }
        }
        return z;
    }

    void onRequestDraw(boolean z) {
        synchronized (this) {
            this.mReportNextDraw = z;
            this.mOldTargetRect.set(0, 0, 0, 0);
            pingRenderLocked(true);
        }
    }

    private void redrawLocked(android.graphics.Rect rect, boolean z) {
        int captionHeight = this.mDecorView.getCaptionHeight();
        if (captionHeight != 0) {
            this.mLastCaptionHeight = captionHeight;
        }
        if ((this.mLastCaptionHeight == 0 && this.mDecorView.isShowingCaption()) || this.mLastContentWidth == 0 || this.mLastContentHeight == 0) {
            return;
        }
        int i = this.mLastXOffset + rect.left;
        int i2 = this.mLastYOffset + rect.top;
        int width = rect.width();
        int height = rect.height();
        int i3 = i + width;
        int i4 = i2 + height;
        this.mFrameAndBackdropNode.setLeftTopRightBottom(i, i2, i3, i4);
        android.graphics.RecordingCanvas beginRecording = this.mFrameAndBackdropNode.beginRecording(width, height);
        android.graphics.drawable.Drawable drawable = this.mUserCaptionBackgroundDrawable != null ? this.mUserCaptionBackgroundDrawable : this.mCaptionBackgroundDrawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, i3, this.mLastCaptionHeight + i2);
            drawable.draw(beginRecording);
        }
        if (this.mResizingBackgroundDrawable != null) {
            this.mResizingBackgroundDrawable.setBounds(0, this.mLastCaptionHeight, i3, i4);
            this.mResizingBackgroundDrawable.draw(beginRecording);
        }
        this.mFrameAndBackdropNode.endRecording();
        drawColorViews(i, i2, width, height, z);
        this.mRenderer.drawRenderNode(this.mFrameAndBackdropNode);
        reportDrawIfNeeded();
    }

    private void drawColorViews(int i, int i2, int i3, int i4, boolean z) {
        if (this.mSystemBarBackgroundNode == null) {
            return;
        }
        android.graphics.RecordingCanvas beginRecording = this.mSystemBarBackgroundNode.beginRecording(i3, i4);
        int i5 = i + i3;
        this.mSystemBarBackgroundNode.setLeftTopRightBottom(i, i2, i5, i2 + i4);
        int i6 = this.mSystemBarInsets.top;
        if (this.mStatusBarColor != null) {
            this.mStatusBarColor.setBounds(0, 0, i5, i6);
            this.mStatusBarColor.draw(beginRecording);
        }
        if (this.mNavigationBarColor != null && z) {
            com.android.internal.policy.DecorView.getNavigationBarRect(i3, i4, this.mSystemBarInsets, this.mTmpRect, 1.0f);
            this.mNavigationBarColor.setBounds(this.mTmpRect);
            this.mNavigationBarColor.draw(beginRecording);
        }
        this.mSystemBarBackgroundNode.endRecording();
        this.mRenderer.drawRenderNode(this.mSystemBarBackgroundNode);
    }

    private void reportDrawIfNeeded() {
        if (this.mReportNextDraw) {
            if (this.mDecorView.isAttachedToWindow()) {
                this.mDecorView.getViewRootImpl().reportDrawFinish();
            }
            this.mReportNextDraw = false;
        }
    }

    private void pingRenderLocked(boolean z) {
        if (this.mChoreographer != null && !z) {
            this.mChoreographer.postFrameCallback(this);
        } else {
            doFrameUncheckedLocked();
        }
    }

    void setUserCaptionBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        synchronized (this) {
            this.mUserCaptionBackgroundDrawable = drawable;
        }
    }
}
