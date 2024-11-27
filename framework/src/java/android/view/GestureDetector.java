package android.view;

/* loaded from: classes4.dex */
public class GestureDetector {
    private static final int LONG_PRESS = 2;
    private static final int SHOW_PRESS = 1;
    private static final int TAP = 3;
    private boolean mAlwaysInBiggerTapRegion;
    private boolean mAlwaysInTapRegion;
    private float mAmbiguousGestureMultiplier;
    private android.view.GestureDetector.OnContextClickListener mContextClickListener;
    private android.view.MotionEvent mCurrentDownEvent;
    private android.view.MotionEvent mCurrentMotionEvent;
    private boolean mDeferConfirmSingleTap;
    private android.view.GestureDetector.OnDoubleTapListener mDoubleTapListener;
    private int mDoubleTapSlopSquare;
    private int mDoubleTapTouchSlopSquare;
    private float mDownFocusX;
    private float mDownFocusY;
    private final android.os.Handler mHandler;
    private boolean mHasRecordedClassification;
    private boolean mIgnoreNextUpEvent;
    private boolean mInContextClick;
    private boolean mInLongPress;
    private final android.view.InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    private boolean mIsDoubleTapping;
    private boolean mIsLongpressEnabled;
    private float mLastFocusX;
    private float mLastFocusY;
    private final android.view.GestureDetector.OnGestureListener mListener;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private android.view.MotionEvent mPreviousUpEvent;
    private boolean mStillDown;
    private int mTouchSlopSquare;
    private android.view.VelocityTracker mVelocityTracker;
    private static final java.lang.String TAG = android.view.GestureDetector.class.getSimpleName();
    private static final int LONGPRESS_TIMEOUT = android.view.ViewConfiguration.getLongPressTimeout();
    private static final int TAP_TIMEOUT = android.view.ViewConfiguration.getTapTimeout();
    private static final int DOUBLE_TAP_TIMEOUT = android.view.ViewConfiguration.getDoubleTapTimeout();
    private static final int DOUBLE_TAP_MIN_TIME = android.view.ViewConfiguration.getDoubleTapMinTime();

    public interface OnContextClickListener {
        boolean onContextClick(android.view.MotionEvent motionEvent);
    }

    public interface OnDoubleTapListener {
        boolean onDoubleTap(android.view.MotionEvent motionEvent);

        boolean onDoubleTapEvent(android.view.MotionEvent motionEvent);

        boolean onSingleTapConfirmed(android.view.MotionEvent motionEvent);
    }

    public interface OnGestureListener {
        boolean onDown(android.view.MotionEvent motionEvent);

        boolean onFling(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2);

        void onLongPress(android.view.MotionEvent motionEvent);

        boolean onScroll(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2);

        void onShowPress(android.view.MotionEvent motionEvent);

        boolean onSingleTapUp(android.view.MotionEvent motionEvent);
    }

    public static class SimpleOnGestureListener implements android.view.GestureDetector.OnGestureListener, android.view.GestureDetector.OnDoubleTapListener, android.view.GestureDetector.OnContextClickListener {
        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(android.view.MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onLongPress(android.view.MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onScroll(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onFling(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onShowPress(android.view.MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onDown(android.view.MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(android.view.MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTapEvent(android.view.MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(android.view.MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.GestureDetector.OnContextClickListener
        public boolean onContextClick(android.view.MotionEvent motionEvent) {
            return false;
        }
    }

    private class GestureHandler extends android.os.Handler {
        GestureHandler() {
        }

        GestureHandler(android.os.Handler handler) {
            super(handler.getLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.view.GestureDetector.this.mListener.onShowPress(android.view.GestureDetector.this.mCurrentDownEvent);
                    return;
                case 2:
                    android.view.GestureDetector.this.recordGestureClassification(message.arg1);
                    android.view.GestureDetector.this.dispatchLongPress();
                    return;
                case 3:
                    if (android.view.GestureDetector.this.mDoubleTapListener != null) {
                        if (!android.view.GestureDetector.this.mStillDown) {
                            android.view.GestureDetector.this.recordGestureClassification(1);
                            android.view.GestureDetector.this.mDoubleTapListener.onSingleTapConfirmed(android.view.GestureDetector.this.mCurrentDownEvent);
                            return;
                        } else {
                            android.view.GestureDetector.this.mDeferConfirmSingleTap = true;
                            return;
                        }
                    }
                    return;
                default:
                    throw new java.lang.RuntimeException("Unknown message " + message);
            }
        }
    }

    @java.lang.Deprecated
    public GestureDetector(android.view.GestureDetector.OnGestureListener onGestureListener, android.os.Handler handler) {
        this(null, onGestureListener, handler);
    }

    @java.lang.Deprecated
    public GestureDetector(android.view.GestureDetector.OnGestureListener onGestureListener) {
        this(null, onGestureListener, null);
    }

    public GestureDetector(android.content.Context context, android.view.GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public GestureDetector(android.content.Context context, android.view.GestureDetector.OnGestureListener onGestureListener, android.os.Handler handler) {
        this.mInputEventConsistencyVerifier = android.view.InputEventConsistencyVerifier.isInstrumentationEnabled() ? new android.view.InputEventConsistencyVerifier(this, 0) : null;
        if (handler != null) {
            this.mHandler = new android.view.GestureDetector.GestureHandler(handler);
        } else {
            this.mHandler = new android.view.GestureDetector.GestureHandler();
        }
        this.mListener = onGestureListener;
        if (onGestureListener instanceof android.view.GestureDetector.OnDoubleTapListener) {
            setOnDoubleTapListener((android.view.GestureDetector.OnDoubleTapListener) onGestureListener);
        }
        if (onGestureListener instanceof android.view.GestureDetector.OnContextClickListener) {
            setContextClickListener((android.view.GestureDetector.OnContextClickListener) onGestureListener);
        }
        init(context);
    }

    public GestureDetector(android.content.Context context, android.view.GestureDetector.OnGestureListener onGestureListener, android.os.Handler handler, boolean z) {
        this(context, onGestureListener, handler);
    }

    private void init(android.content.Context context) {
        int scaledDoubleTapTouchSlop;
        int i;
        int i2;
        if (this.mListener == null) {
            throw new java.lang.NullPointerException("OnGestureListener must not be null");
        }
        this.mIsLongpressEnabled = true;
        if (context == null) {
            i = android.view.ViewConfiguration.getTouchSlop();
            i2 = android.view.ViewConfiguration.getDoubleTapSlop();
            this.mMinimumFlingVelocity = android.view.ViewConfiguration.getMinimumFlingVelocity();
            this.mMaximumFlingVelocity = android.view.ViewConfiguration.getMaximumFlingVelocity();
            this.mAmbiguousGestureMultiplier = android.view.ViewConfiguration.getAmbiguousGestureMultiplier();
            scaledDoubleTapTouchSlop = i;
        } else {
            android.os.StrictMode.assertConfigurationContext(context, "GestureDetector#init");
            android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(context);
            int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
            scaledDoubleTapTouchSlop = viewConfiguration.getScaledDoubleTapTouchSlop();
            int scaledDoubleTapSlop = viewConfiguration.getScaledDoubleTapSlop();
            this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
            this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
            this.mAmbiguousGestureMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
            i = scaledTouchSlop;
            i2 = scaledDoubleTapSlop;
        }
        this.mTouchSlopSquare = i * i;
        this.mDoubleTapTouchSlopSquare = scaledDoubleTapTouchSlop * scaledDoubleTapTouchSlop;
        this.mDoubleTapSlopSquare = i2 * i2;
    }

    public void setOnDoubleTapListener(android.view.GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mDoubleTapListener = onDoubleTapListener;
    }

    public void setContextClickListener(android.view.GestureDetector.OnContextClickListener onContextClickListener) {
        this.mContextClickListener = onContextClickListener;
    }

    public void setIsLongpressEnabled(boolean z) {
        this.mIsLongpressEnabled = z;
    }

    public boolean isLongpressEnabled() {
        return this.mIsLongpressEnabled;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:141:0x02d2  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        boolean z3;
        int i;
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTouchEvent(motionEvent, 0);
        }
        int action = motionEvent.getAction();
        if (this.mCurrentMotionEvent != null) {
            this.mCurrentMotionEvent.recycle();
        }
        this.mCurrentMotionEvent = android.view.MotionEvent.obtain(motionEvent);
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int i2 = action & 255;
        boolean z4 = i2 == 6;
        int actionIndex = z4 ? motionEvent.getActionIndex() : -1;
        boolean z5 = (motionEvent.getFlags() & 8) != 0;
        int pointerCount = motionEvent.getPointerCount();
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            if (actionIndex != i3) {
                f += motionEvent.getX(i3);
                f2 += motionEvent.getY(i3);
            }
        }
        float f3 = z4 ? pointerCount - 1 : pointerCount;
        float f4 = f / f3;
        float f5 = f2 / f3;
        switch (i2) {
            case 0:
                if (this.mDoubleTapListener != null) {
                    boolean hasMessages = this.mHandler.hasMessages(3);
                    if (hasMessages) {
                        this.mHandler.removeMessages(3);
                    }
                    if (this.mCurrentDownEvent != null && this.mPreviousUpEvent != null && hasMessages && isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, motionEvent)) {
                        this.mIsDoubleTapping = true;
                        recordGestureClassification(2);
                        z2 = this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | false | this.mDoubleTapListener.onDoubleTapEvent(motionEvent);
                        this.mLastFocusX = f4;
                        this.mDownFocusX = f4;
                        this.mLastFocusY = f5;
                        this.mDownFocusY = f5;
                        if (this.mCurrentDownEvent != null) {
                            this.mCurrentDownEvent.recycle();
                        }
                        this.mCurrentDownEvent = android.view.MotionEvent.obtain(motionEvent);
                        this.mAlwaysInTapRegion = true;
                        this.mAlwaysInBiggerTapRegion = true;
                        this.mStillDown = true;
                        this.mInLongPress = false;
                        this.mDeferConfirmSingleTap = false;
                        this.mHasRecordedClassification = false;
                        if (this.mIsLongpressEnabled) {
                            this.mHandler.removeMessages(2);
                            this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(2, 3, 0), this.mCurrentDownEvent.getDownTime() + android.view.ViewConfiguration.getLongPressTimeout());
                        }
                        this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
                        z = z2 | this.mListener.onDown(motionEvent);
                        break;
                    } else {
                        this.mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
                    }
                }
                z2 = false;
                this.mLastFocusX = f4;
                this.mDownFocusX = f4;
                this.mLastFocusY = f5;
                this.mDownFocusY = f5;
                if (this.mCurrentDownEvent != null) {
                }
                this.mCurrentDownEvent = android.view.MotionEvent.obtain(motionEvent);
                this.mAlwaysInTapRegion = true;
                this.mAlwaysInBiggerTapRegion = true;
                this.mStillDown = true;
                this.mInLongPress = false;
                this.mDeferConfirmSingleTap = false;
                this.mHasRecordedClassification = false;
                if (this.mIsLongpressEnabled) {
                }
                this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
                z = z2 | this.mListener.onDown(motionEvent);
                break;
            case 1:
                this.mStillDown = false;
                android.view.MotionEvent obtain = android.view.MotionEvent.obtain(motionEvent);
                if (this.mIsDoubleTapping) {
                    recordGestureClassification(2);
                    z = this.mDoubleTapListener.onDoubleTapEvent(motionEvent) | false;
                } else {
                    if (this.mInLongPress) {
                        this.mHandler.removeMessages(3);
                        this.mInLongPress = false;
                    } else if (this.mAlwaysInTapRegion && !this.mIgnoreNextUpEvent) {
                        recordGestureClassification(1);
                        boolean onSingleTapUp = this.mListener.onSingleTapUp(motionEvent);
                        if (this.mDeferConfirmSingleTap && this.mDoubleTapListener != null) {
                            this.mDoubleTapListener.onSingleTapConfirmed(motionEvent);
                        }
                        z = onSingleTapUp;
                    } else if (!this.mIgnoreNextUpEvent) {
                        android.view.VelocityTracker velocityTracker = this.mVelocityTracker;
                        int pointerId = motionEvent.getPointerId(0);
                        velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                        float yVelocity = velocityTracker.getYVelocity(pointerId);
                        float xVelocity = velocityTracker.getXVelocity(pointerId);
                        if (java.lang.Math.abs(yVelocity) > this.mMinimumFlingVelocity || java.lang.Math.abs(xVelocity) > this.mMinimumFlingVelocity) {
                            z = this.mListener.onFling(this.mCurrentDownEvent, motionEvent, xVelocity, yVelocity);
                        }
                    }
                    z = false;
                }
                if (this.mPreviousUpEvent != null) {
                    this.mPreviousUpEvent.recycle();
                }
                this.mPreviousUpEvent = obtain;
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
                this.mIsDoubleTapping = false;
                this.mDeferConfirmSingleTap = false;
                this.mIgnoreNextUpEvent = false;
                this.mHandler.removeMessages(1);
                this.mHandler.removeMessages(2);
                break;
            case 2:
                if (!this.mInLongPress && !this.mInContextClick) {
                    int classification = motionEvent.getClassification();
                    boolean hasMessages2 = this.mHandler.hasMessages(2);
                    float f6 = this.mLastFocusX - f4;
                    float f7 = this.mLastFocusY - f5;
                    if (this.mIsDoubleTapping) {
                        recordGestureClassification(2);
                        z = this.mDoubleTapListener.onDoubleTapEvent(motionEvent) | false;
                        z3 = hasMessages2;
                    } else if (this.mAlwaysInTapRegion) {
                        int i4 = (int) (f4 - this.mDownFocusX);
                        int i5 = (int) (f5 - this.mDownFocusY);
                        int i6 = (i4 * i4) + (i5 * i5);
                        int i7 = z5 ? 0 : this.mTouchSlopSquare;
                        if (!(hasMessages2 && (classification == 1))) {
                            z3 = hasMessages2;
                        } else {
                            if (i6 <= i7) {
                                z3 = hasMessages2;
                                i = i7;
                            } else {
                                this.mHandler.removeMessages(2);
                                i = i7;
                                z3 = hasMessages2;
                                this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(2, 3, 0), motionEvent.getDownTime() + ((long) (android.view.ViewConfiguration.getLongPressTimeout() * this.mAmbiguousGestureMultiplier)));
                            }
                            i7 = (int) (i * this.mAmbiguousGestureMultiplier * this.mAmbiguousGestureMultiplier);
                        }
                        if (i6 > i7) {
                            recordGestureClassification(5);
                            boolean onScroll = this.mListener.onScroll(this.mCurrentDownEvent, motionEvent, f6, f7);
                            this.mLastFocusX = f4;
                            this.mLastFocusY = f5;
                            this.mAlwaysInTapRegion = false;
                            this.mHandler.removeMessages(3);
                            this.mHandler.removeMessages(1);
                            this.mHandler.removeMessages(2);
                            z = onScroll;
                        } else {
                            z = false;
                        }
                        if (i6 > (z5 ? 0 : this.mDoubleTapTouchSlopSquare)) {
                            this.mAlwaysInBiggerTapRegion = false;
                        }
                    } else {
                        z3 = hasMessages2;
                        if (java.lang.Math.abs(f6) >= 1.0f || java.lang.Math.abs(f7) >= 1.0f) {
                            recordGestureClassification(5);
                            z = this.mListener.onScroll(this.mCurrentDownEvent, motionEvent, f6, f7);
                            this.mLastFocusX = f4;
                            this.mLastFocusY = f5;
                        } else {
                            z = false;
                        }
                    }
                    if ((classification == 2) && z3) {
                        this.mHandler.removeMessages(2);
                        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, 4, 0));
                        break;
                    }
                }
                z = false;
                break;
            case 3:
                cancel();
                z = false;
                break;
            case 4:
            default:
                z = false;
                break;
            case 5:
                this.mLastFocusX = f4;
                this.mDownFocusX = f4;
                this.mLastFocusY = f5;
                this.mDownFocusY = f5;
                cancelTaps();
                z = false;
                break;
            case 6:
                this.mLastFocusX = f4;
                this.mDownFocusX = f4;
                this.mLastFocusY = f5;
                this.mDownFocusY = f5;
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                int actionIndex2 = motionEvent.getActionIndex();
                int pointerId2 = motionEvent.getPointerId(actionIndex2);
                float xVelocity2 = this.mVelocityTracker.getXVelocity(pointerId2);
                float yVelocity2 = this.mVelocityTracker.getYVelocity(pointerId2);
                int i8 = 0;
                while (true) {
                    if (i8 < pointerCount) {
                        if (i8 != actionIndex2) {
                            int pointerId3 = motionEvent.getPointerId(i8);
                            if ((this.mVelocityTracker.getXVelocity(pointerId3) * xVelocity2) + (this.mVelocityTracker.getYVelocity(pointerId3) * yVelocity2) < 0.0f) {
                                this.mVelocityTracker.clear();
                            }
                        }
                        i8++;
                    }
                }
                z = false;
                break;
        }
        if (!z && this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 0);
        }
        return z;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onGenericMotionEvent(motionEvent, 0);
        }
        int actionButton = motionEvent.getActionButton();
        switch (motionEvent.getActionMasked()) {
            case 11:
                if (this.mContextClickListener != null && !this.mInContextClick && !this.mInLongPress && ((actionButton == 32 || actionButton == 2) && this.mContextClickListener.onContextClick(motionEvent))) {
                    this.mInContextClick = true;
                    this.mHandler.removeMessages(2);
                    this.mHandler.removeMessages(3);
                    return true;
                }
                return false;
            case 12:
                if (this.mInContextClick && (actionButton == 32 || actionButton == 2)) {
                    this.mInContextClick = false;
                    this.mIgnoreNextUpEvent = true;
                }
                return false;
            default:
                return false;
        }
    }

    private void cancel() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        this.mIsDoubleTapping = false;
        this.mStillDown = false;
        this.mAlwaysInTapRegion = false;
        this.mAlwaysInBiggerTapRegion = false;
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = false;
        this.mInContextClick = false;
        this.mIgnoreNextUpEvent = false;
    }

    private void cancelTaps() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        this.mIsDoubleTapping = false;
        this.mAlwaysInTapRegion = false;
        this.mAlwaysInBiggerTapRegion = false;
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = false;
        this.mInContextClick = false;
        this.mIgnoreNextUpEvent = false;
    }

    private boolean isConsideredDoubleTap(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, android.view.MotionEvent motionEvent3) {
        if (!this.mAlwaysInBiggerTapRegion) {
            return false;
        }
        long eventTime = motionEvent3.getEventTime() - motionEvent2.getEventTime();
        if (eventTime > DOUBLE_TAP_TIMEOUT || eventTime < DOUBLE_TAP_MIN_TIME) {
            return false;
        }
        int x = ((int) motionEvent.getX()) - ((int) motionEvent3.getX());
        int y = ((int) motionEvent.getY()) - ((int) motionEvent3.getY());
        return (x * x) + (y * y) < ((motionEvent.getFlags() & 8) != 0 ? 0 : this.mDoubleTapSlopSquare);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchLongPress() {
        this.mHandler.removeMessages(3);
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = true;
        this.mListener.onLongPress(this.mCurrentDownEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recordGestureClassification(int i) {
        if (this.mHasRecordedClassification || i == 0) {
            return;
        }
        if (this.mCurrentDownEvent == null || this.mCurrentMotionEvent == null) {
            this.mHasRecordedClassification = true;
        } else {
            com.android.internal.util.FrameworkStatsLog.write(177, getClass().getName(), i, (int) (android.os.SystemClock.uptimeMillis() - this.mCurrentMotionEvent.getDownTime()), (float) java.lang.Math.hypot(this.mCurrentMotionEvent.getRawX() - this.mCurrentDownEvent.getRawX(), this.mCurrentMotionEvent.getRawY() - this.mCurrentDownEvent.getRawY()));
            this.mHasRecordedClassification = true;
        }
    }
}
