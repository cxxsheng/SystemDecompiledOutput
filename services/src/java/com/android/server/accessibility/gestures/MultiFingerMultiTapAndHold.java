package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
public class MultiFingerMultiTapAndHold extends com.android.server.accessibility.gestures.MultiFingerMultiTap {
    public MultiFingerMultiTapAndHold(android.content.Context context, int i, int i2, int i3, com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener stateChangeListener) {
        super(context, i, i2, i3, stateChangeListener);
    }

    @Override // com.android.server.accessibility.gestures.MultiFingerMultiTap, com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        super.onPointerDown(motionEvent, motionEvent2, i);
        if (this.mIsTargetFingerCountReached && this.mCompletedTapCount + 1 == this.mTargetTapCount) {
            completeAfterLongPressTimeout(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.MultiFingerMultiTap, com.android.server.accessibility.gestures.GestureMatcher
    protected void onUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mCompletedTapCount + 1 == this.mTargetTapCount) {
            cancelGesture(motionEvent, motionEvent2, i);
        } else {
            super.onUp(motionEvent, motionEvent2, i);
            cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.MultiFingerMultiTap, com.android.server.accessibility.gestures.GestureMatcher
    public java.lang.String getGestureName() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(this.mTargetFingerCount);
        sb.append("-Finger ");
        if (this.mTargetTapCount == 1) {
            sb.append("Single");
        } else if (this.mTargetTapCount == 2) {
            sb.append("Double");
        } else if (this.mTargetTapCount == 3) {
            sb.append("Triple");
        } else if (this.mTargetTapCount > 3) {
            sb.append(this.mTargetTapCount);
        }
        sb.append(" Tap and hold");
        return sb.toString();
    }
}
