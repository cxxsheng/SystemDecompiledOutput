package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
class MagnificationGesturesObserver implements com.android.server.accessibility.magnification.GesturesObserver.Listener {
    private final com.android.server.accessibility.magnification.MagnificationGesturesObserver.Callback mCallback;

    @android.annotation.Nullable
    private java.util.List<com.android.server.accessibility.magnification.MotionEventInfo> mDelayedEventQueue;
    private final com.android.server.accessibility.magnification.GesturesObserver mGesturesObserver;
    private long mLastDownEventTime = 0;
    private android.view.MotionEvent mLastEvent;
    private static final java.lang.String LOG_TAG = "MagnificationGesturesObserver";

    @android.annotation.SuppressLint({"LongLogTag"})
    private static final boolean DBG = android.util.Log.isLoggable(LOG_TAG, 3);

    interface Callback {
        void onGestureCancelled(long j, java.util.List<com.android.server.accessibility.magnification.MotionEventInfo> list, android.view.MotionEvent motionEvent);

        void onGestureCompleted(int i, long j, java.util.List<com.android.server.accessibility.magnification.MotionEventInfo> list, android.view.MotionEvent motionEvent);

        boolean shouldStopDetection(android.view.MotionEvent motionEvent);
    }

    MagnificationGesturesObserver(@android.annotation.NonNull com.android.server.accessibility.magnification.MagnificationGesturesObserver.Callback callback, com.android.server.accessibility.gestures.GestureMatcher... gestureMatcherArr) {
        this.mGesturesObserver = new com.android.server.accessibility.magnification.GesturesObserver(this, gestureMatcherArr);
        this.mCallback = callback;
    }

    boolean onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (DBG) {
            android.util.Slog.d(LOG_TAG, "DetectGesture: event = " + motionEvent);
        }
        cacheDelayedMotionEvent(motionEvent, motionEvent2, i);
        if (this.mCallback.shouldStopDetection(motionEvent)) {
            notifyDetectionCancel();
            return false;
        }
        if (motionEvent.getActionMasked() == 0) {
            this.mLastDownEventTime = motionEvent.getDownTime();
        }
        return this.mGesturesObserver.onMotionEvent(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.magnification.GesturesObserver.Listener
    public void onGestureCompleted(int i, android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i2) {
        if (DBG) {
            android.util.Slog.d(LOG_TAG, "onGestureCompleted: " + com.android.server.accessibility.magnification.MagnificationGestureMatcher.gestureIdToString(i) + " event = " + motionEvent);
        }
        java.util.List<com.android.server.accessibility.magnification.MotionEventInfo> list = this.mDelayedEventQueue;
        this.mDelayedEventQueue = null;
        this.mCallback.onGestureCompleted(i, this.mLastDownEventTime, list, motionEvent);
        clear();
    }

    @Override // com.android.server.accessibility.magnification.GesturesObserver.Listener
    public void onGestureCancelled(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (DBG) {
            android.util.Slog.d(LOG_TAG, "onGestureCancelled:  event = " + motionEvent);
        }
        notifyDetectionCancel();
    }

    private void notifyDetectionCancel() {
        java.util.List<com.android.server.accessibility.magnification.MotionEventInfo> list = this.mDelayedEventQueue;
        this.mDelayedEventQueue = null;
        this.mCallback.onGestureCancelled(this.mLastDownEventTime, list, this.mLastEvent);
        clear();
    }

    private void clear() {
        if (DBG) {
            android.util.Slog.d(LOG_TAG, "clear:" + this.mDelayedEventQueue);
        }
        recycleLastEvent();
        this.mLastDownEventTime = 0L;
        if (this.mDelayedEventQueue != null) {
            java.util.Iterator<com.android.server.accessibility.magnification.MotionEventInfo> it = this.mDelayedEventQueue.iterator();
            while (it.hasNext()) {
                it.next().recycle();
            }
            this.mDelayedEventQueue.clear();
            this.mDelayedEventQueue = null;
        }
    }

    private void recycleLastEvent() {
        if (this.mLastEvent == null) {
            return;
        }
        this.mLastEvent.recycle();
        this.mLastEvent = null;
    }

    private void cacheDelayedMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        this.mLastEvent = android.view.MotionEvent.obtain(motionEvent);
        com.android.server.accessibility.magnification.MotionEventInfo obtain = com.android.server.accessibility.magnification.MotionEventInfo.obtain(motionEvent, motionEvent2, i);
        if (this.mDelayedEventQueue == null) {
            this.mDelayedEventQueue = new java.util.LinkedList();
        }
        this.mDelayedEventQueue.add(obtain);
    }

    public java.lang.String toString() {
        return "MagnificationGesturesObserver{mDelayedEventQueue=" + this.mDelayedEventQueue + '}';
    }
}
