package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
public abstract class GestureMatcher {
    public static final int STATE_CLEAR = 0;
    public static final int STATE_GESTURE_CANCELED = 3;
    public static final int STATE_GESTURE_COMPLETED = 2;
    public static final int STATE_GESTURE_STARTED = 1;
    private final int mGestureId;
    private final android.os.Handler mHandler;
    private com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener mListener;

    @com.android.server.accessibility.gestures.GestureMatcher.State
    private int mState = 0;
    protected final com.android.server.accessibility.gestures.GestureMatcher.DelayedTransition mDelayedTransition = new com.android.server.accessibility.gestures.GestureMatcher.DelayedTransition();

    public @interface State {
    }

    public interface StateChangeListener {
        void onStateChanged(int i, int i2, android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i3);
    }

    protected abstract java.lang.String getGestureName();

    protected GestureMatcher(int i, android.os.Handler handler, com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener stateChangeListener) {
        this.mGestureId = i;
        this.mHandler = handler;
        this.mListener = stateChangeListener;
    }

    public void clear() {
        this.mState = 0;
        cancelPendingTransitions();
    }

    public final int getState() {
        return this.mState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setState(@com.android.server.accessibility.gestures.GestureMatcher.State int i, android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i2) {
        this.mState = i;
        cancelPendingTransitions();
        if (this.mListener != null) {
            this.mListener.onStateChanged(this.mGestureId, this.mState, motionEvent, motionEvent2, i2);
        }
    }

    protected final void startGesture(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        setState(1, motionEvent, motionEvent2, i);
    }

    protected final void cancelGesture(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        setState(3, motionEvent, motionEvent2, i);
    }

    protected final void completeGesture(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        setState(2, motionEvent, motionEvent2, i);
    }

    public final void setListener(@android.annotation.NonNull com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener stateChangeListener) {
        this.mListener = stateChangeListener;
    }

    public int getGestureId() {
        return this.mGestureId;
    }

    public final int onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mState == 3 || this.mState == 2) {
            return this.mState;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                onDown(motionEvent, motionEvent2, i);
                break;
            case 1:
                onUp(motionEvent, motionEvent2, i);
                break;
            case 2:
                onMove(motionEvent, motionEvent2, i);
                break;
            case 3:
            case 4:
            default:
                setState(3, motionEvent, motionEvent2, i);
                break;
            case 5:
                onPointerDown(motionEvent, motionEvent2, i);
                break;
            case 6:
                onPointerUp(motionEvent, motionEvent2, i);
                break;
        }
        return this.mState;
    }

    protected void onDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
    }

    protected void onPointerDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
    }

    protected void onMove(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
    }

    protected void onPointerUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
    }

    protected void onUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
    }

    protected void cancelAfterTapTimeout(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelAfter(android.view.ViewConfiguration.getTapTimeout(), motionEvent, motionEvent2, i);
    }

    protected final void cancelAfterDoubleTapTimeout(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelAfter(android.view.ViewConfiguration.getDoubleTapTimeout(), motionEvent, motionEvent2, i);
    }

    protected final void cancelAfter(long j, android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        this.mDelayedTransition.cancel();
        this.mDelayedTransition.post(3, j, motionEvent, motionEvent2, i);
    }

    protected final void cancelPendingTransitions() {
        this.mDelayedTransition.cancel();
    }

    protected final void completeAfterLongPressTimeout(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        completeAfter(android.view.ViewConfiguration.getLongPressTimeout(), motionEvent, motionEvent2, i);
    }

    protected final void completeAfterTapTimeout(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        completeAfter(android.view.ViewConfiguration.getTapTimeout(), motionEvent, motionEvent2, i);
    }

    protected final void completeAfter(long j, android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        this.mDelayedTransition.cancel();
        this.mDelayedTransition.post(2, j, motionEvent, motionEvent2, i);
    }

    protected final void completeAfterDoubleTapTimeout(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        completeAfter(android.view.ViewConfiguration.getDoubleTapTimeout(), motionEvent, motionEvent2, i);
    }

    static java.lang.String getStateSymbolicName(@com.android.server.accessibility.gestures.GestureMatcher.State int i) {
        switch (i) {
            case 0:
                return "STATE_CLEAR";
            case 1:
                return "STATE_GESTURE_STARTED";
            case 2:
                return "STATE_GESTURE_COMPLETED";
            case 3:
                return "STATE_GESTURE_CANCELED";
            default:
                return "Unknown state: " + i;
        }
    }

    public java.lang.String toString() {
        return getGestureName() + ":" + getStateSymbolicName(this.mState);
    }

    protected final class DelayedTransition implements java.lang.Runnable {
        private static final java.lang.String LOG_TAG = "GestureMatcher.DelayedTransition";
        android.view.MotionEvent mEvent;
        int mPolicyFlags;
        android.view.MotionEvent mRawEvent;
        int mTargetState;

        protected DelayedTransition() {
        }

        public void cancel() {
            if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG && isPending()) {
                android.util.Slog.d(LOG_TAG, com.android.server.accessibility.gestures.GestureMatcher.this.getGestureName() + ": canceling delayed transition to " + com.android.server.accessibility.gestures.GestureMatcher.getStateSymbolicName(this.mTargetState));
            }
            com.android.server.accessibility.gestures.GestureMatcher.this.mHandler.removeCallbacks(this);
            recycleEvent();
        }

        public void post(int i, long j, android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i2) {
            recycleEvent();
            this.mTargetState = i;
            if (android.view.accessibility.Flags.copyEventsForGestureDetection()) {
                this.mEvent = motionEvent.copy();
                this.mRawEvent = motionEvent2.copy();
            } else {
                this.mEvent = motionEvent;
                this.mRawEvent = motionEvent2;
            }
            this.mPolicyFlags = i2;
            com.android.server.accessibility.gestures.GestureMatcher.this.mHandler.postDelayed(this, j);
            if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                android.util.Slog.d(LOG_TAG, com.android.server.accessibility.gestures.GestureMatcher.this.getGestureName() + ": posting delayed transition to " + com.android.server.accessibility.gestures.GestureMatcher.getStateSymbolicName(this.mTargetState));
            }
        }

        public boolean isPending() {
            return com.android.server.accessibility.gestures.GestureMatcher.this.mHandler.hasCallbacks(this);
        }

        public void forceSendAndRemove() {
            if (isPending()) {
                run();
                cancel();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                android.util.Slog.d(LOG_TAG, com.android.server.accessibility.gestures.GestureMatcher.this.getGestureName() + ": executing delayed transition to " + com.android.server.accessibility.gestures.GestureMatcher.getStateSymbolicName(this.mTargetState));
            }
            com.android.server.accessibility.gestures.GestureMatcher.this.setState(this.mTargetState, this.mEvent, this.mRawEvent, this.mPolicyFlags);
            recycleEvent();
        }

        private void recycleEvent() {
            if (!android.view.accessibility.Flags.copyEventsForGestureDetection() || this.mEvent == null || this.mRawEvent == null) {
                return;
            }
            this.mEvent.recycle();
            this.mRawEvent.recycle();
            this.mEvent = null;
            this.mRawEvent = null;
        }
    }
}
