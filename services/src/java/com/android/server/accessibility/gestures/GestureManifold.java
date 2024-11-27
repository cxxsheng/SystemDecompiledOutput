package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
public class GestureManifold implements com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener {
    private static final java.lang.String LOG_TAG = "GestureManifold";
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private com.android.server.accessibility.gestures.GestureManifold.Listener mListener;
    private com.android.server.accessibility.gestures.TouchState mState;
    private final java.util.List<com.android.server.accessibility.gestures.GestureMatcher> mGestures = new java.util.ArrayList();
    private boolean mServiceHandlesDoubleTap = false;
    private boolean mSendMotionEventsEnabled = false;
    private final java.util.List<com.android.server.accessibility.gestures.GestureMatcher> mMultiFingerGestures = new java.util.ArrayList();
    private final java.util.List<com.android.server.accessibility.gestures.GestureMatcher> mTwoFingerSwipes = new java.util.ArrayList();
    private java.util.List<android.view.MotionEvent> mEvents = new java.util.ArrayList();
    boolean mMultiFingerGesturesEnabled = false;
    private boolean mTwoFingerPassthroughEnabled = false;

    public interface Listener {
        boolean onDoubleTap(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i);

        void onDoubleTapAndHold(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i);

        boolean onGestureCancelled(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i);

        boolean onGestureCompleted(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent);

        boolean onGestureStarted();
    }

    public GestureManifold(android.content.Context context, com.android.server.accessibility.gestures.GestureManifold.Listener listener, com.android.server.accessibility.gestures.TouchState touchState, android.os.Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        this.mListener = listener;
        this.mState = touchState;
        this.mGestures.add(new com.android.server.accessibility.gestures.MultiTap(context, 2, 17, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.MultiTapAndHold(context, 2, 18, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.SecondFingerMultiTap(context, 2, 17, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 1, 4, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 0, 3, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 2, 1, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 3, 2, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 0, 1, 5, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 0, 2, 9, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 0, 3, 10, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 1, 2, 11, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 1, 3, 12, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 1, 0, 6, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 3, 2, 8, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 3, 0, 15, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 3, 1, 16, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 2, 3, 7, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 2, 0, 13, this));
        this.mGestures.add(new com.android.server.accessibility.gestures.Swipe(context, 2, 1, 14, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTap(this.mContext, 2, 1, 19, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTap(this.mContext, 2, 2, 20, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTapAndHold(this.mContext, 2, 2, 40, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTap(this.mContext, 2, 3, 21, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTapAndHold(this.mContext, 2, 3, 43, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTap(this.mContext, 3, 1, 22, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTap(this.mContext, 3, 2, 23, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTapAndHold(this.mContext, 3, 1, 44, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTapAndHold(this.mContext, 3, 2, 41, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTap(this.mContext, 3, 3, 24, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTapAndHold(this.mContext, 3, 3, 45, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTap(this.mContext, 3, 3, 24, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTap(this.mContext, 4, 1, 37, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTap(this.mContext, 4, 2, 38, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTapAndHold(this.mContext, 4, 2, 42, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerMultiTap(this.mContext, 4, 3, 39, this));
        this.mTwoFingerSwipes.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 2, 3, 26, this));
        this.mTwoFingerSwipes.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 2, 0, 27, this));
        this.mTwoFingerSwipes.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 2, 1, 28, this));
        this.mTwoFingerSwipes.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 2, 2, 25, this));
        this.mMultiFingerGestures.addAll(this.mTwoFingerSwipes);
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 3, 3, 30, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 3, 0, 31, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 3, 1, 32, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 3, 2, 29, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 4, 3, 34, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 4, 0, 35, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 4, 1, 36, this));
        this.mMultiFingerGestures.add(new com.android.server.accessibility.gestures.MultiFingerSwipe(context, 4, 2, 33, this));
    }

    public boolean onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mState.isClear()) {
            if (motionEvent.getActionMasked() != 0) {
                return false;
            }
            clear();
        }
        if (this.mSendMotionEventsEnabled) {
            this.mEvents.add(android.view.MotionEvent.obtainNoHistory(motionEvent2));
        }
        for (com.android.server.accessibility.gestures.GestureMatcher gestureMatcher : this.mGestures) {
            if (gestureMatcher.getState() != 3) {
                if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                    android.util.Slog.d(LOG_TAG, gestureMatcher.toString());
                }
                gestureMatcher.onMotionEvent(motionEvent, motionEvent2, i);
                if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                    android.util.Slog.d(LOG_TAG, gestureMatcher.toString());
                }
                if (gestureMatcher.getState() == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public void clear() {
        java.util.Iterator<com.android.server.accessibility.gestures.GestureMatcher> it = this.mGestures.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        if (this.mEvents != null) {
            while (this.mEvents.size() > 0) {
                this.mEvents.remove(0).recycle();
            }
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener
    public void onStateChanged(int i, int i2, android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i3) {
        if (i2 == 1 && !this.mState.isGestureDetecting()) {
            if (i == 17 || i == 18) {
                if (this.mServiceHandlesDoubleTap) {
                    this.mListener.onGestureStarted();
                    return;
                }
                return;
            }
            this.mListener.onGestureStarted();
            return;
        }
        if (i2 == 2) {
            onGestureCompleted(i, motionEvent, motionEvent2, i3);
            return;
        }
        if (i2 == 3 && this.mState.isGestureDetecting()) {
            java.util.Iterator<com.android.server.accessibility.gestures.GestureMatcher> it = this.mGestures.iterator();
            while (it.hasNext()) {
                if (it.next().getState() == 1) {
                    return;
                }
            }
            if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                android.util.Slog.d(LOG_TAG, "Cancelling.");
            }
            this.mListener.onGestureCancelled(motionEvent, motionEvent2, i3);
        }
    }

    private void onGestureCompleted(int i, android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i2) {
        switch (i) {
            case 17:
                if (this.mServiceHandlesDoubleTap) {
                    this.mListener.onGestureCompleted(new android.accessibilityservice.AccessibilityGestureEvent(i, motionEvent.getDisplayId(), this.mEvents));
                    break;
                } else {
                    this.mListener.onDoubleTap(motionEvent, motionEvent2, i2);
                    break;
                }
            case 18:
                if (this.mServiceHandlesDoubleTap) {
                    this.mListener.onGestureCompleted(new android.accessibilityservice.AccessibilityGestureEvent(i, motionEvent.getDisplayId(), this.mEvents));
                    break;
                } else {
                    this.mListener.onDoubleTapAndHold(motionEvent, motionEvent2, i2);
                    break;
                }
            default:
                this.mListener.onGestureCompleted(new android.accessibilityservice.AccessibilityGestureEvent(i, motionEvent.getDisplayId(), this.mEvents));
                break;
        }
        clear();
    }

    public boolean isMultiFingerGesturesEnabled() {
        return this.mMultiFingerGesturesEnabled;
    }

    public void setMultiFingerGesturesEnabled(boolean z) {
        if (this.mMultiFingerGesturesEnabled != z) {
            this.mMultiFingerGesturesEnabled = z;
            if (z) {
                this.mGestures.addAll(this.mMultiFingerGestures);
            } else {
                this.mGestures.removeAll(this.mMultiFingerGestures);
            }
        }
    }

    public boolean isTwoFingerPassthroughEnabled() {
        return this.mTwoFingerPassthroughEnabled;
    }

    public void setTwoFingerPassthroughEnabled(boolean z) {
        if (this.mTwoFingerPassthroughEnabled != z) {
            this.mTwoFingerPassthroughEnabled = z;
            if (!z) {
                this.mMultiFingerGestures.addAll(this.mTwoFingerSwipes);
                if (this.mMultiFingerGesturesEnabled) {
                    this.mGestures.addAll(this.mTwoFingerSwipes);
                    return;
                }
                return;
            }
            this.mMultiFingerGestures.removeAll(this.mTwoFingerSwipes);
            this.mGestures.removeAll(this.mTwoFingerSwipes);
        }
    }

    public void setServiceHandlesDoubleTap(boolean z) {
        this.mServiceHandlesDoubleTap = z;
    }

    public boolean isServiceHandlesDoubleTapEnabled() {
        return this.mServiceHandlesDoubleTap;
    }

    public void setSendMotionEventsEnabled(boolean z) {
        this.mSendMotionEventsEnabled = z;
        if (!z) {
            while (this.mEvents.size() > 0) {
                this.mEvents.remove(0).recycle();
            }
        }
    }

    public boolean isSendMotionEventsEnabled() {
        return this.mSendMotionEventsEnabled;
    }

    public java.util.List<android.view.MotionEvent> getMotionEvents() {
        return this.mEvents;
    }
}
