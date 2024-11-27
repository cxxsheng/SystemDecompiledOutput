package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public final class GesturesObserver implements com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener {
    private final com.android.server.accessibility.magnification.GesturesObserver.Listener mListener;
    private final java.util.List<com.android.server.accessibility.gestures.GestureMatcher> mGestureMatchers = new java.util.ArrayList();
    private boolean mObserveStarted = false;
    private boolean mProcessMotionEvent = false;
    private int mCancelledMatcherSize = 0;

    public interface Listener {
        void onGestureCancelled(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i);

        void onGestureCompleted(int i, android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i2);
    }

    public GesturesObserver(com.android.server.accessibility.magnification.GesturesObserver.Listener listener, com.android.server.accessibility.gestures.GestureMatcher... gestureMatcherArr) {
        this.mListener = listener;
        for (int i = 0; i < gestureMatcherArr.length; i++) {
            gestureMatcherArr[i].setListener(this);
            this.mGestureMatchers.add(gestureMatcherArr[i]);
        }
    }

    public boolean onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (!this.mObserveStarted) {
            if (motionEvent.getActionMasked() != 0) {
                this.mListener.onGestureCancelled(motionEvent, motionEvent2, i);
                clear();
                return false;
            }
            this.mObserveStarted = true;
        }
        this.mProcessMotionEvent = true;
        for (int i2 = 0; i2 < this.mGestureMatchers.size(); i2++) {
            com.android.server.accessibility.gestures.GestureMatcher gestureMatcher = this.mGestureMatchers.get(i2);
            gestureMatcher.onMotionEvent(motionEvent, motionEvent2, i);
            if (gestureMatcher.getState() == 2) {
                clear();
                this.mProcessMotionEvent = false;
                return true;
            }
        }
        this.mProcessMotionEvent = false;
        return false;
    }

    private void clear() {
        java.util.Iterator<com.android.server.accessibility.gestures.GestureMatcher> it = this.mGestureMatchers.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        this.mCancelledMatcherSize = 0;
        this.mObserveStarted = false;
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener
    public void onStateChanged(int i, int i2, android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i3) {
        if (i2 == 2) {
            this.mListener.onGestureCompleted(i, motionEvent, motionEvent2, i3);
            if (!this.mProcessMotionEvent) {
                clear();
                return;
            }
            return;
        }
        if (i2 == 3) {
            this.mCancelledMatcherSize++;
            if (this.mCancelledMatcherSize == this.mGestureMatchers.size()) {
                this.mListener.onGestureCancelled(motionEvent, motionEvent2, i3);
                clear();
            }
        }
    }
}
