package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
final class MotionEventInfo {
    public android.view.MotionEvent mEvent;
    public int mPolicyFlags;
    public android.view.MotionEvent mRawEvent;

    static com.android.server.accessibility.magnification.MotionEventInfo obtain(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        return new com.android.server.accessibility.magnification.MotionEventInfo(android.view.MotionEvent.obtain(motionEvent), android.view.MotionEvent.obtain(motionEvent2), i);
    }

    MotionEventInfo(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        this.mEvent = motionEvent;
        this.mRawEvent = motionEvent2;
        this.mPolicyFlags = i;
    }

    void recycle() {
        this.mEvent = recycleAndNullify(this.mEvent);
        this.mRawEvent = recycleAndNullify(this.mRawEvent);
    }

    public java.lang.String toString() {
        return android.view.MotionEvent.actionToString(this.mEvent.getAction()).replace("ACTION_", "");
    }

    private static android.view.MotionEvent recycleAndNullify(@android.annotation.Nullable android.view.MotionEvent motionEvent) {
        if (motionEvent != null) {
            motionEvent.recycle();
            return null;
        }
        return null;
    }
}
