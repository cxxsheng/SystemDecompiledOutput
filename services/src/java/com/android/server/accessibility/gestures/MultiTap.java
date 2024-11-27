package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
public class MultiTap extends com.android.server.accessibility.gestures.GestureMatcher {
    public static final int MAX_TAPS = 10;
    float mBaseX;
    float mBaseY;
    int mCurrentTaps;
    int mDoubleTapSlop;
    int mDoubleTapTimeout;
    int mTapTimeout;
    final int mTargetTaps;
    int mTouchSlop;

    public MultiTap(android.content.Context context, int i, int i2, com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener stateChangeListener) {
        super(i2, new android.os.Handler(context.getMainLooper()), stateChangeListener);
        this.mTargetTaps = i;
        this.mDoubleTapSlop = android.view.ViewConfiguration.get(context).getScaledDoubleTapSlop();
        this.mTouchSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        this.mTapTimeout = android.view.ViewConfiguration.getTapTimeout();
        this.mDoubleTapTimeout = android.view.ViewConfiguration.getDoubleTapTimeout();
        clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public void clear() {
        this.mCurrentTaps = 0;
        this.mBaseX = Float.NaN;
        this.mBaseY = Float.NaN;
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelAfterTapTimeout(motionEvent, motionEvent2, i);
        if (java.lang.Float.isNaN(this.mBaseX) && java.lang.Float.isNaN(this.mBaseY)) {
            this.mBaseX = motionEvent.getX();
            this.mBaseY = motionEvent.getY();
        }
        if (!isInsideSlop(motionEvent2, this.mDoubleTapSlop)) {
            cancelGesture(motionEvent, motionEvent2, i);
        }
        this.mBaseX = motionEvent.getX();
        this.mBaseY = motionEvent.getY();
        if (this.mCurrentTaps + 1 == this.mTargetTaps) {
            startGesture(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
        if (!isInsideSlop(motionEvent2, this.mTouchSlop)) {
            cancelGesture(motionEvent, motionEvent2, i);
        }
        if (getState() == 1 || getState() == 0) {
            this.mCurrentTaps++;
            if (this.mCurrentTaps == this.mTargetTaps) {
                completeGesture(motionEvent, motionEvent2, i);
                return;
            } else {
                cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
                return;
            }
        }
        cancelGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onMove(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (!isInsideSlop(motionEvent2, this.mTouchSlop)) {
            cancelGesture(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public java.lang.String getGestureName() {
        switch (this.mTargetTaps) {
            case 2:
                return "Double Tap";
            case 3:
                return "Triple Tap";
            default:
                return java.lang.Integer.toString(this.mTargetTaps) + " Taps";
        }
    }

    private boolean isInsideSlop(android.view.MotionEvent motionEvent, int i) {
        float x = this.mBaseX - motionEvent.getX();
        float y = this.mBaseY - motionEvent.getY();
        return (x == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && y == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) || java.lang.Math.hypot((double) x, (double) y) <= ((double) i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public java.lang.String toString() {
        return super.toString() + ", Taps:" + this.mCurrentTaps + ", mBaseX: " + java.lang.Float.toString(this.mBaseX) + ", mBaseY: " + java.lang.Float.toString(this.mBaseY);
    }
}
