package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
class SimpleSwipe extends com.android.server.accessibility.gestures.GestureMatcher {
    private final int mDetectionDurationMillis;
    private android.view.MotionEvent mLastDown;
    private final int mSwipeMinDistance;

    SimpleSwipe(android.content.Context context) {
        super(102, new android.os.Handler(context.getMainLooper()), null);
        this.mSwipeMinDistance = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        this.mDetectionDurationMillis = com.android.server.accessibility.magnification.MagnificationGestureMatcher.getMagnificationMultiTapTimeout(context);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        this.mLastDown = android.view.MotionEvent.obtain(motionEvent);
        cancelAfter(this.mDetectionDurationMillis, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onMove(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (gestureMatched(motionEvent, motionEvent2, i)) {
            completeGesture(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (gestureMatched(motionEvent, motionEvent2, i)) {
            completeGesture(motionEvent, motionEvent2, i);
        } else {
            cancelGesture(motionEvent, motionEvent2, i);
        }
    }

    private boolean gestureMatched(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        return this.mLastDown != null && com.android.server.accessibility.gestures.GestureUtils.distance(this.mLastDown, motionEvent) > ((double) this.mSwipeMinDistance);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public void clear() {
        if (this.mLastDown != null) {
            this.mLastDown.recycle();
        }
        this.mLastDown = null;
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected java.lang.String getGestureName() {
        return getClass().getSimpleName();
    }
}
