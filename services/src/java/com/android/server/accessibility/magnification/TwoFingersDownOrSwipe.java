package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
final class TwoFingersDownOrSwipe extends com.android.server.accessibility.gestures.GestureMatcher {
    private final int mDetectionDurationMillis;
    private final int mDoubleTapTimeout;
    private android.view.MotionEvent mFirstPointerDown;
    private android.view.MotionEvent mSecondPointerDown;
    private final int mSwipeMinDistance;

    TwoFingersDownOrSwipe(android.content.Context context) {
        super(101, new android.os.Handler(context.getMainLooper()), null);
        this.mDetectionDurationMillis = com.android.server.accessibility.magnification.MagnificationGestureMatcher.getMagnificationMultiTapTimeout(context);
        this.mDoubleTapTimeout = android.view.ViewConfiguration.getDoubleTapTimeout();
        this.mSwipeMinDistance = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        this.mFirstPointerDown = android.view.MotionEvent.obtain(motionEvent);
        cancelAfter(this.mDetectionDurationMillis, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mFirstPointerDown == null) {
            cancelGesture(motionEvent, motionEvent2, i);
        }
        if (motionEvent.getPointerCount() == 2) {
            this.mSecondPointerDown = android.view.MotionEvent.obtain(motionEvent);
            completeAfter(this.mDoubleTapTimeout, motionEvent, motionEvent2, i);
        } else {
            cancelGesture(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onMove(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mFirstPointerDown == null || this.mSecondPointerDown == null) {
            return;
        }
        if (distance(this.mFirstPointerDown, motionEvent) > this.mSwipeMinDistance) {
            completeGesture(motionEvent, motionEvent2, i);
        } else if (distance(this.mSecondPointerDown, motionEvent) > this.mSwipeMinDistance) {
            completeGesture(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public void clear() {
        if (this.mFirstPointerDown != null) {
            this.mFirstPointerDown.recycle();
            this.mFirstPointerDown = null;
        }
        if (this.mSecondPointerDown != null) {
            this.mSecondPointerDown.recycle();
            this.mSecondPointerDown = null;
        }
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected java.lang.String getGestureName() {
        return com.android.server.accessibility.magnification.TwoFingersDownOrSwipe.class.getSimpleName();
    }

    private static double distance(@android.annotation.NonNull android.view.MotionEvent motionEvent, @android.annotation.NonNull android.view.MotionEvent motionEvent2) {
        if (motionEvent2.findPointerIndex(motionEvent.getPointerId(motionEvent.getActionIndex())) < 0) {
            return -1.0d;
        }
        return android.util.MathUtils.dist(motionEvent.getX(r0), motionEvent.getY(r0), motionEvent2.getX(r1), motionEvent2.getY(r1));
    }
}
