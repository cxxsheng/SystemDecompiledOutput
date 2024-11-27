package android.view;

/* loaded from: classes4.dex */
public class ScaleGestureDetector {
    private static final int ANCHORED_SCALE_MODE_DOUBLE_TAP = 1;
    private static final int ANCHORED_SCALE_MODE_NONE = 0;
    private static final int ANCHORED_SCALE_MODE_STYLUS = 2;
    private static final float SCALE_FACTOR = 0.5f;
    private static final java.lang.String TAG = "ScaleGestureDetector";
    private static final long TOUCH_STABILIZE_TIME = 128;
    private int mAnchoredScaleMode;
    private float mAnchoredScaleStartX;
    private float mAnchoredScaleStartY;
    private final android.content.Context mContext;
    private float mCurrSpan;
    private float mCurrSpanX;
    private float mCurrSpanY;
    private long mCurrTime;
    private boolean mEventBeforeOrAboveStartingGestureEvent;
    private float mFocusX;
    private float mFocusY;
    private android.view.GestureDetector mGestureDetector;
    private final android.os.Handler mHandler;
    private boolean mInProgress;
    private float mInitialSpan;
    private final android.view.InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    private final android.view.ScaleGestureDetector.OnScaleGestureListener mListener;
    private int mMinSpan;
    private float mPrevSpan;
    private float mPrevSpanX;
    private float mPrevSpanY;
    private long mPrevTime;
    private boolean mQuickScaleEnabled;
    private int mSpanSlop;
    private boolean mStylusScaleEnabled;

    public interface OnScaleGestureListener {
        boolean onScale(android.view.ScaleGestureDetector scaleGestureDetector);

        boolean onScaleBegin(android.view.ScaleGestureDetector scaleGestureDetector);

        void onScaleEnd(android.view.ScaleGestureDetector scaleGestureDetector);
    }

    public static class SimpleOnScaleGestureListener implements android.view.ScaleGestureDetector.OnScaleGestureListener {
        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(android.view.ScaleGestureDetector scaleGestureDetector) {
            return false;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(android.view.ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(android.view.ScaleGestureDetector scaleGestureDetector) {
        }
    }

    public ScaleGestureDetector(android.content.Context context, android.view.ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener) {
        this(context, onScaleGestureListener, null);
    }

    public ScaleGestureDetector(android.content.Context context, android.view.ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener, android.os.Handler handler) {
        this(context, android.view.ViewConfiguration.get(context).getScaledTouchSlop() * 2, android.view.ViewConfiguration.get(context).getScaledMinimumScalingSpan(), handler, onScaleGestureListener);
    }

    public ScaleGestureDetector(android.content.Context context, int i, int i2, android.os.Handler handler, android.view.ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener) {
        this.mAnchoredScaleMode = 0;
        this.mInputEventConsistencyVerifier = android.view.InputEventConsistencyVerifier.isInstrumentationEnabled() ? new android.view.InputEventConsistencyVerifier(this, 0) : null;
        this.mContext = context;
        this.mListener = onScaleGestureListener;
        this.mSpanSlop = i;
        this.mMinSpan = i2;
        this.mHandler = handler;
        int i3 = context.getApplicationInfo().targetSdkVersion;
        if (i3 > 18) {
            setQuickScaleEnabled(true);
        }
        if (i3 > 22) {
            setStylusScaleEnabled(true);
        }
    }

    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        float f;
        float f2;
        float hypot;
        boolean z;
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTouchEvent(motionEvent, 0);
        }
        this.mCurrTime = motionEvent.getEventTime();
        int actionMasked = motionEvent.getActionMasked();
        if (this.mQuickScaleEnabled) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        int pointerCount = motionEvent.getPointerCount();
        boolean z2 = (motionEvent.getButtonState() & 32) != 0;
        boolean z3 = this.mAnchoredScaleMode == 2 && !z2;
        boolean z4 = actionMasked == 1 || actionMasked == 3 || z3;
        float f3 = 0.0f;
        if (actionMasked == 0 || z4) {
            if (this.mInProgress) {
                this.mListener.onScaleEnd(this);
                this.mInProgress = false;
                this.mInitialSpan = 0.0f;
                this.mAnchoredScaleMode = 0;
            } else if (inAnchoredScaleMode() && z4) {
                this.mInProgress = false;
                this.mInitialSpan = 0.0f;
                this.mAnchoredScaleMode = 0;
            }
            if (z4) {
                return true;
            }
        }
        if (!this.mInProgress && this.mStylusScaleEnabled && !inAnchoredScaleMode() && !z4 && z2) {
            this.mAnchoredScaleStartX = motionEvent.getX();
            this.mAnchoredScaleStartY = motionEvent.getY();
            this.mAnchoredScaleMode = 2;
            this.mInitialSpan = 0.0f;
        }
        boolean z5 = actionMasked == 0 || actionMasked == 6 || actionMasked == 5 || z3;
        boolean z6 = actionMasked == 6;
        int actionIndex = z6 ? motionEvent.getActionIndex() : -1;
        int i = z6 ? pointerCount - 1 : pointerCount;
        if (inAnchoredScaleMode()) {
            f2 = this.mAnchoredScaleStartX;
            f = this.mAnchoredScaleStartY;
            if (motionEvent.getY() < f) {
                this.mEventBeforeOrAboveStartingGestureEvent = true;
            } else {
                this.mEventBeforeOrAboveStartingGestureEvent = false;
            }
        } else {
            float f4 = 0.0f;
            float f5 = 0.0f;
            for (int i2 = 0; i2 < pointerCount; i2++) {
                if (actionIndex != i2) {
                    f4 += motionEvent.getX(i2);
                    f5 += motionEvent.getY(i2);
                }
            }
            float f6 = i;
            float f7 = f4 / f6;
            f = f5 / f6;
            f2 = f7;
        }
        float f8 = 0.0f;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            if (actionIndex != i3) {
                f3 += java.lang.Math.abs(motionEvent.getX(i3) - f2);
                f8 += java.lang.Math.abs(motionEvent.getY(i3) - f);
            }
        }
        float f9 = i;
        float f10 = (f3 / f9) * 2.0f;
        float f11 = (f8 / f9) * 2.0f;
        if (inAnchoredScaleMode()) {
            hypot = f11;
        } else {
            hypot = (float) java.lang.Math.hypot(f10, f11);
        }
        boolean z7 = this.mInProgress;
        this.mFocusX = f2;
        this.mFocusY = f;
        if (!inAnchoredScaleMode() && this.mInProgress && (hypot < this.mMinSpan || z5)) {
            this.mListener.onScaleEnd(this);
            this.mInProgress = false;
            this.mInitialSpan = hypot;
        }
        if (z5) {
            this.mCurrSpanX = f10;
            this.mPrevSpanX = f10;
            this.mCurrSpanY = f11;
            this.mPrevSpanY = f11;
            this.mCurrSpan = hypot;
            this.mPrevSpan = hypot;
            this.mInitialSpan = hypot;
        }
        int i4 = inAnchoredScaleMode() ? this.mSpanSlop : this.mMinSpan;
        if (!this.mInProgress && hypot >= i4 && (z7 || java.lang.Math.abs(hypot - this.mInitialSpan) > this.mSpanSlop)) {
            this.mCurrSpanX = f10;
            this.mPrevSpanX = f10;
            this.mCurrSpanY = f11;
            this.mPrevSpanY = f11;
            this.mCurrSpan = hypot;
            this.mPrevSpan = hypot;
            this.mPrevTime = this.mCurrTime;
            this.mInProgress = this.mListener.onScaleBegin(this);
        }
        if (actionMasked == 2) {
            this.mCurrSpanX = f10;
            this.mCurrSpanY = f11;
            this.mCurrSpan = hypot;
            if (this.mInProgress) {
                z = this.mListener.onScale(this);
            } else {
                z = true;
            }
            if (z) {
                this.mPrevSpanX = this.mCurrSpanX;
                this.mPrevSpanY = this.mCurrSpanY;
                this.mPrevSpan = this.mCurrSpan;
                this.mPrevTime = this.mCurrTime;
            }
        }
        return true;
    }

    private boolean inAnchoredScaleMode() {
        return this.mAnchoredScaleMode != 0;
    }

    public void setQuickScaleEnabled(boolean z) {
        this.mQuickScaleEnabled = z;
        if (this.mQuickScaleEnabled && this.mGestureDetector == null) {
            this.mGestureDetector = new android.view.GestureDetector(this.mContext, new android.view.GestureDetector.SimpleOnGestureListener() { // from class: android.view.ScaleGestureDetector.1
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
                public boolean onDoubleTap(android.view.MotionEvent motionEvent) {
                    android.view.ScaleGestureDetector.this.mAnchoredScaleStartX = motionEvent.getX();
                    android.view.ScaleGestureDetector.this.mAnchoredScaleStartY = motionEvent.getY();
                    android.view.ScaleGestureDetector.this.mAnchoredScaleMode = 1;
                    return true;
                }
            }, this.mHandler);
        }
    }

    public boolean isQuickScaleEnabled() {
        return this.mQuickScaleEnabled;
    }

    public void setStylusScaleEnabled(boolean z) {
        this.mStylusScaleEnabled = z;
    }

    public boolean isStylusScaleEnabled() {
        return this.mStylusScaleEnabled;
    }

    public boolean isInProgress() {
        return this.mInProgress;
    }

    public float getFocusX() {
        return this.mFocusX;
    }

    public float getFocusY() {
        return this.mFocusY;
    }

    public float getCurrentSpan() {
        return this.mCurrSpan;
    }

    public float getCurrentSpanX() {
        return this.mCurrSpanX;
    }

    public float getCurrentSpanY() {
        return this.mCurrSpanY;
    }

    public float getPreviousSpan() {
        return this.mPrevSpan;
    }

    public float getPreviousSpanX() {
        return this.mPrevSpanX;
    }

    public float getPreviousSpanY() {
        return this.mPrevSpanY;
    }

    public float getScaleFactor() {
        if (!inAnchoredScaleMode()) {
            if (this.mPrevSpan > 0.0f) {
                return this.mCurrSpan / this.mPrevSpan;
            }
            return 1.0f;
        }
        boolean z = (this.mEventBeforeOrAboveStartingGestureEvent && this.mCurrSpan < this.mPrevSpan) || (!this.mEventBeforeOrAboveStartingGestureEvent && this.mCurrSpan > this.mPrevSpan);
        float abs = java.lang.Math.abs(1.0f - (this.mCurrSpan / this.mPrevSpan)) * 0.5f;
        if (this.mPrevSpan <= this.mSpanSlop) {
            return 1.0f;
        }
        return z ? 1.0f + abs : 1.0f - abs;
    }

    public long getTimeDelta() {
        return this.mCurrTime - this.mPrevTime;
    }

    public long getEventTime() {
        return this.mCurrTime;
    }
}
