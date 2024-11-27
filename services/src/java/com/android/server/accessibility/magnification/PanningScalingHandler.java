package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
class PanningScalingHandler extends android.view.GestureDetector.SimpleOnGestureListener implements android.view.ScaleGestureDetector.OnScaleGestureListener {
    private final boolean mBlockScroll;
    private final int mDisplayId;
    private boolean mEnable;
    private float mInitialScaleFactor = -1.0f;
    private final com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate mMagnificationDelegate;
    private final float mMaxScale;
    private final float mMinScale;
    private final android.view.ScaleGestureDetector mScaleGestureDetector;
    private boolean mScaling;
    private final float mScalingThreshold;
    private final android.view.GestureDetector mScrollGestureDetector;
    private static final java.lang.String TAG = "PanningScalingHandler";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    interface MagnificationDelegate {
        float getScale(int i);

        boolean processScroll(int i, float f, float f2);

        void setScale(int i, float f);
    }

    PanningScalingHandler(android.content.Context context, float f, float f2, boolean z, @android.annotation.NonNull com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate magnificationDelegate) {
        this.mDisplayId = context.getDisplayId();
        this.mMaxScale = f;
        this.mMinScale = f2;
        this.mBlockScroll = z;
        com.android.server.accessibility.Flags.pinchZoomZeroMinSpan();
        this.mScaleGestureDetector = new android.view.ScaleGestureDetector(context, this, android.os.Handler.getMain());
        this.mScrollGestureDetector = new android.view.GestureDetector(context, this, android.os.Handler.getMain());
        this.mScaleGestureDetector.setQuickScaleEnabled(false);
        this.mMagnificationDelegate = magnificationDelegate;
        android.util.TypedValue typedValue = new android.util.TypedValue();
        context.getResources().getValue(android.R.dimen.config_progressBarCornerRadius, typedValue, false);
        this.mScalingThreshold = typedValue.getFloat();
    }

    void setEnabled(boolean z) {
        clear();
        this.mEnable = z;
    }

    void onTouchEvent(android.view.MotionEvent motionEvent) {
        this.mScaleGestureDetector.onTouchEvent(motionEvent);
        this.mScrollGestureDetector.onTouchEvent(motionEvent);
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onScroll(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
        if (!this.mEnable) {
            return true;
        }
        if (this.mBlockScroll && this.mScaling) {
            return true;
        }
        return this.mMagnificationDelegate.processScroll(this.mDisplayId, f, f2);
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScale(android.view.ScaleGestureDetector scaleGestureDetector) {
        if (DEBUG) {
            android.util.Slog.i(TAG, "onScale: triggered ");
        }
        if (!this.mScaling) {
            if (this.mInitialScaleFactor < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                this.mInitialScaleFactor = scaleGestureDetector.getScaleFactor();
                return false;
            }
            this.mScaling = java.lang.Math.abs(scaleGestureDetector.getScaleFactor() - this.mInitialScaleFactor) > this.mScalingThreshold;
            return this.mScaling;
        }
        float scale = this.mMagnificationDelegate.getScale(this.mDisplayId);
        float scaleFactor = scaleGestureDetector.getScaleFactor() * scale;
        if (scaleFactor > this.mMaxScale && scaleFactor > scale) {
            scaleFactor = this.mMaxScale;
        } else if (scaleFactor < this.mMinScale && scaleFactor < scale) {
            scaleFactor = this.mMinScale;
        }
        if (DEBUG) {
            android.util.Slog.i(TAG, "Scaled content to: " + scaleFactor + "x");
        }
        this.mMagnificationDelegate.setScale(this.mDisplayId, scaleFactor);
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(android.view.ScaleGestureDetector scaleGestureDetector) {
        return this.mEnable;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(android.view.ScaleGestureDetector scaleGestureDetector) {
        clear();
    }

    void clear() {
        this.mInitialScaleFactor = -1.0f;
        this.mScaling = false;
    }

    public java.lang.String toString() {
        return "PanningScalingHandler{mInitialScaleFactor=" + this.mInitialScaleFactor + ", mScaling=" + this.mScaling + '}';
    }
}
