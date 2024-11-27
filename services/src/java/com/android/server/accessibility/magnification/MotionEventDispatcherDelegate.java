package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
class MotionEventDispatcherDelegate {
    private final com.android.server.accessibility.magnification.MotionEventDispatcherDelegate.EventDispatcher mEventDispatcher;
    private long mLastDelegatedDownEventTime;
    private final int mMultiTapMaxDelay;
    private static final java.lang.String TAG = com.android.server.accessibility.magnification.MotionEventDispatcherDelegate.class.getSimpleName();
    private static final boolean DBG = android.util.Log.isLoggable(TAG, 3);

    interface EventDispatcher {
        void dispatchMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i);
    }

    MotionEventDispatcherDelegate(android.content.Context context, com.android.server.accessibility.magnification.MotionEventDispatcherDelegate.EventDispatcher eventDispatcher) {
        this.mEventDispatcher = eventDispatcher;
        this.mMultiTapMaxDelay = android.view.ViewConfiguration.getDoubleTapTimeout() + context.getResources().getInteger(android.R.integer.config_screenBrightnessCapForWearBedtimeMode);
    }

    void sendDelayedMotionEvents(java.util.List<com.android.server.accessibility.magnification.MotionEventInfo> list, long j) {
        if (list == null) {
            return;
        }
        long min = java.lang.Math.min(android.os.SystemClock.uptimeMillis() - j, this.mMultiTapMaxDelay);
        for (com.android.server.accessibility.magnification.MotionEventInfo motionEventInfo : list) {
            motionEventInfo.mEvent.setDownTime(motionEventInfo.mEvent.getDownTime() + min);
            dispatchMotionEvent(motionEventInfo.mEvent, motionEventInfo.mRawEvent, motionEventInfo.mPolicyFlags);
            motionEventInfo.recycle();
        }
    }

    void dispatchMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (motionEvent.getActionMasked() == 0) {
            this.mLastDelegatedDownEventTime = motionEvent.getDownTime();
            if (DBG) {
                android.util.Log.d(TAG, "dispatchMotionEvent mLastDelegatedDownEventTime time = " + this.mLastDelegatedDownEventTime);
            }
        }
        if (DBG) {
            android.util.Log.d(TAG, "dispatchMotionEvent original down time = " + motionEvent.getDownTime());
        }
        motionEvent.setDownTime(this.mLastDelegatedDownEventTime);
        this.mEventDispatcher.dispatchMotionEvent(motionEvent, motionEvent2, i);
    }
}
