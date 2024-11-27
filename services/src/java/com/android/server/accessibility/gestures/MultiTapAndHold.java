package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
public class MultiTapAndHold extends com.android.server.accessibility.gestures.MultiTap {
    public MultiTapAndHold(android.content.Context context, int i, int i2, com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener stateChangeListener) {
        super(context, i, i2, stateChangeListener);
    }

    @Override // com.android.server.accessibility.gestures.MultiTap, com.android.server.accessibility.gestures.GestureMatcher
    protected void onDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        super.onDown(motionEvent, motionEvent2, i);
        if (this.mCurrentTaps + 1 == this.mTargetTaps) {
            completeAfterLongPressTimeout(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.MultiTap, com.android.server.accessibility.gestures.GestureMatcher
    protected void onUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        super.onUp(motionEvent, motionEvent2, i);
        cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.MultiTap, com.android.server.accessibility.gestures.GestureMatcher
    public java.lang.String getGestureName() {
        switch (this.mTargetTaps) {
            case 2:
                return "Double Tap and Hold";
            case 3:
                return "Triple Tap and Hold";
            default:
                return java.lang.Integer.toString(this.mTargetTaps) + " Taps and Hold";
        }
    }
}
