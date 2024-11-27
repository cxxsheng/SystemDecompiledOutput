package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
public class TouchState {
    public static final int ALL_POINTER_ID_BITS = -1;
    private static final java.lang.String LOG_TAG = "TouchState";
    public static final int MAX_POINTER_COUNT = 32;
    public static final int STATE_CLEAR = 0;
    public static final int STATE_DELEGATING = 4;
    public static final int STATE_DRAGGING = 3;
    public static final int STATE_GESTURE_DETECTING = 5;
    public static final int STATE_TOUCH_EXPLORING = 2;
    public static final int STATE_TOUCH_INTERACTING = 1;
    private com.android.server.accessibility.AccessibilityManagerService mAms;
    private int mDisplayId;
    private int mInjectedPointersDown;
    private long mLastInjectedDownEventTime;
    private android.view.MotionEvent mLastInjectedHoverEvent;
    private android.view.MotionEvent mLastInjectedHoverEventForClick;
    private android.view.MotionEvent mLastReceivedEvent;
    int mLastReceivedPolicyFlags;
    private android.view.MotionEvent mLastReceivedRawEvent;
    private int mLastTouchedWindowId;
    private int mState = 0;
    private boolean mServiceDetectsGestures = false;
    private boolean mServiceDetectsGesturesRequested = false;
    private final com.android.server.accessibility.gestures.TouchState.ReceivedPointerTracker mReceivedPointerTracker = new com.android.server.accessibility.gestures.TouchState.ReceivedPointerTracker();

    public @interface State {
    }

    public TouchState(int i, com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService) {
        this.mDisplayId = -1;
        this.mDisplayId = i;
        this.mAms = accessibilityManagerService;
    }

    public void clear() {
        setState(0);
        this.mServiceDetectsGestures = this.mServiceDetectsGesturesRequested;
        if (this.mLastReceivedEvent != null) {
            this.mLastReceivedEvent.recycle();
            this.mLastReceivedEvent = null;
        }
        this.mReceivedPointerTracker.clear();
        this.mInjectedPointersDown = 0;
    }

    public void onReceivedMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (isClear() && motionEvent.getActionMasked() == 0) {
            clear();
        }
        if (this.mLastReceivedEvent != null) {
            this.mLastReceivedEvent.recycle();
        }
        if (this.mLastReceivedRawEvent != null) {
            this.mLastReceivedRawEvent.recycle();
        }
        this.mLastReceivedEvent = android.view.MotionEvent.obtain(motionEvent);
        this.mLastReceivedRawEvent = android.view.MotionEvent.obtain(motionEvent2);
        this.mLastReceivedPolicyFlags = i;
        this.mReceivedPointerTracker.onMotionEvent(motionEvent2);
    }

    public void onInjectedMotionEvent(android.view.MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        int pointerId = 1 << motionEvent.getPointerId(motionEvent.getActionIndex());
        switch (actionMasked) {
            case 0:
            case 5:
                this.mInjectedPointersDown |= pointerId;
                this.mLastInjectedDownEventTime = motionEvent.getDownTime();
                break;
            case 1:
            case 6:
                this.mInjectedPointersDown &= ~pointerId;
                if (this.mInjectedPointersDown == 0) {
                    this.mLastInjectedDownEventTime = 0L;
                    break;
                }
                break;
            case 7:
            case 9:
                if (this.mLastInjectedHoverEvent != null) {
                    this.mLastInjectedHoverEvent.recycle();
                }
                this.mLastInjectedHoverEvent = android.view.MotionEvent.obtain(motionEvent);
                break;
            case 10:
                if (this.mLastInjectedHoverEvent != null) {
                    this.mLastInjectedHoverEvent.recycle();
                }
                this.mLastInjectedHoverEvent = android.view.MotionEvent.obtain(motionEvent);
                if (this.mLastInjectedHoverEventForClick != null) {
                    this.mLastInjectedHoverEventForClick.recycle();
                }
                this.mLastInjectedHoverEventForClick = android.view.MotionEvent.obtain(motionEvent);
                break;
        }
        if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
            android.util.Slog.i(LOG_TAG, "Injected pointer:\n" + toString());
        }
    }

    public void onReceivedAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        switch (accessibilityEvent.getEventType()) {
            case 32:
            case 32768:
                if (this.mLastInjectedHoverEventForClick != null) {
                    this.mLastInjectedHoverEventForClick.recycle();
                    this.mLastInjectedHoverEventForClick = null;
                }
                this.mLastTouchedWindowId = -1;
                break;
            case 128:
            case 256:
                this.mLastTouchedWindowId = accessibilityEvent.getWindowId();
                break;
            case 2097152:
                this.mAms.moveNonProxyTopFocusedDisplayToTopIfNeeded();
                break;
        }
    }

    public void onInjectedAccessibilityEvent(int i) {
        switch (i) {
            case 512:
                startTouchExploring();
                break;
            case 1024:
                startTouchInteracting();
                break;
            case 262144:
                startGestureDetecting();
                break;
            case 524288:
                clear();
                break;
            case 1048576:
                startTouchInteracting();
                break;
            case 2097152:
                setState(0);
                break;
        }
    }

    @com.android.server.accessibility.gestures.TouchState.State
    public int getState() {
        return this.mState;
    }

    public void setState(@com.android.server.accessibility.gestures.TouchState.State int i) {
        if (this.mState == i) {
            return;
        }
        if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
            android.util.Slog.i(LOG_TAG, getStateSymbolicName(this.mState) + "->" + getStateSymbolicName(i));
        }
        this.mState = i;
        if (this.mServiceDetectsGestures) {
            this.mAms.onTouchStateChanged(this.mDisplayId, i);
        }
    }

    public boolean isTouchExploring() {
        return this.mState == 2;
    }

    public void startTouchExploring() {
        setState(2);
    }

    public boolean isDelegating() {
        return this.mState == 4;
    }

    public void startDelegating() {
        setState(4);
    }

    public boolean isGestureDetecting() {
        return this.mState == 5;
    }

    public void startGestureDetecting() {
        setState(5);
    }

    public boolean isDragging() {
        return this.mState == 3;
    }

    public void startDragging() {
        setState(3);
    }

    public boolean isTouchInteracting() {
        return this.mState == 1;
    }

    public void startTouchInteracting() {
        setState(1);
    }

    public boolean isClear() {
        return this.mState == 0;
    }

    public java.lang.String toString() {
        return "TouchState { mState: " + getStateSymbolicName(this.mState) + " }";
    }

    public static java.lang.String getStateSymbolicName(int i) {
        switch (i) {
            case 0:
                return "STATE_CLEAR";
            case 1:
                return "STATE_TOUCH_INTERACTING";
            case 2:
                return "STATE_TOUCH_EXPLORING";
            case 3:
                return "STATE_DRAGGING";
            case 4:
                return "STATE_DELEGATING";
            case 5:
                return "STATE_GESTURE_DETECTING";
            default:
                return "Unknown state: " + i;
        }
    }

    public com.android.server.accessibility.gestures.TouchState.ReceivedPointerTracker getReceivedPointerTracker() {
        return this.mReceivedPointerTracker;
    }

    public android.view.MotionEvent getLastReceivedEvent() {
        return this.mLastReceivedEvent;
    }

    public int getLastReceivedPolicyFlags() {
        return this.mLastReceivedPolicyFlags;
    }

    public android.view.MotionEvent getLastReceivedRawEvent() {
        return this.mLastReceivedRawEvent;
    }

    public android.view.MotionEvent getLastInjectedHoverEvent() {
        return this.mLastInjectedHoverEvent;
    }

    public long getLastInjectedDownEventTime() {
        return this.mLastInjectedDownEventTime;
    }

    public int getLastTouchedWindowId() {
        return this.mLastTouchedWindowId;
    }

    public int getInjectedPointerDownCount() {
        return java.lang.Integer.bitCount(this.mInjectedPointersDown);
    }

    public int getInjectedPointersDown() {
        return this.mInjectedPointersDown;
    }

    public boolean isInjectedPointerDown(int i) {
        return ((1 << i) & this.mInjectedPointersDown) != 0;
    }

    public android.view.MotionEvent getLastInjectedHoverEventForClick() {
        return this.mLastInjectedHoverEventForClick;
    }

    public boolean isServiceDetectingGestures() {
        return this.mServiceDetectsGestures;
    }

    public void setServiceDetectsGestures(boolean z) {
        if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
            android.util.Slog.d(LOG_TAG, "serviceDetectsGestures: " + z);
        }
        this.mServiceDetectsGesturesRequested = z;
    }

    class ReceivedPointerTracker {
        private static final java.lang.String LOG_TAG_RECEIVED_POINTER_TRACKER = "ReceivedPointerTracker";
        private int mLastReceivedDownEdgeFlags;
        private int mPrimaryPointerId;
        private final com.android.server.accessibility.gestures.TouchState.PointerDownInfo[] mReceivedPointers = new com.android.server.accessibility.gestures.TouchState.PointerDownInfo[32];
        private int mReceivedPointersDown;

        ReceivedPointerTracker() {
            clear();
        }

        public void clear() {
            this.mReceivedPointersDown = 0;
            this.mPrimaryPointerId = 0;
            for (int i = 0; i < 32; i++) {
                this.mReceivedPointers[i] = com.android.server.accessibility.gestures.TouchState.this.new PointerDownInfo();
            }
        }

        public void onMotionEvent(android.view.MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case 0:
                    handleReceivedPointerDown(motionEvent.getActionIndex(), motionEvent);
                    break;
                case 1:
                    handleReceivedPointerUp(motionEvent.getActionIndex(), motionEvent);
                    break;
                case 5:
                    handleReceivedPointerDown(motionEvent.getActionIndex(), motionEvent);
                    break;
                case 6:
                    handleReceivedPointerUp(motionEvent.getActionIndex(), motionEvent);
                    break;
            }
            if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                android.util.Slog.i(LOG_TAG_RECEIVED_POINTER_TRACKER, "Received pointer:\n" + toString());
            }
        }

        public int getReceivedPointerDownCount() {
            return java.lang.Integer.bitCount(this.mReceivedPointersDown);
        }

        public boolean isReceivedPointerDown(int i) {
            return ((1 << i) & this.mReceivedPointersDown) != 0;
        }

        public float getReceivedPointerDownX(int i) {
            return this.mReceivedPointers[i].mX;
        }

        public float getReceivedPointerDownY(int i) {
            return this.mReceivedPointers[i].mY;
        }

        public long getReceivedPointerDownTime(int i) {
            return this.mReceivedPointers[i].mTime;
        }

        public int getPrimaryPointerId() {
            if (this.mPrimaryPointerId == -1) {
                this.mPrimaryPointerId = findPrimaryPointerId();
            }
            return this.mPrimaryPointerId;
        }

        public int getLastReceivedDownEdgeFlags() {
            return this.mLastReceivedDownEdgeFlags;
        }

        private void handleReceivedPointerDown(int i, android.view.MotionEvent motionEvent) {
            int pointerId = motionEvent.getPointerId(i);
            this.mLastReceivedDownEdgeFlags = motionEvent.getEdgeFlags();
            this.mReceivedPointersDown = (1 << pointerId) | this.mReceivedPointersDown;
            this.mReceivedPointers[pointerId].set(motionEvent.getX(i), motionEvent.getY(i), motionEvent.getEventTime());
            this.mPrimaryPointerId = pointerId;
        }

        private void handleReceivedPointerUp(int i, android.view.MotionEvent motionEvent) {
            int pointerId = motionEvent.getPointerId(i);
            this.mReceivedPointersDown = (~(1 << pointerId)) & this.mReceivedPointersDown;
            this.mReceivedPointers[pointerId].clear();
            if (this.mPrimaryPointerId == pointerId) {
                this.mPrimaryPointerId = -1;
            }
        }

        private int findPrimaryPointerId() {
            int i = this.mReceivedPointersDown;
            int i2 = -1;
            long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            while (i > 0) {
                int numberOfTrailingZeros = java.lang.Integer.numberOfTrailingZeros(i);
                i &= ~(1 << numberOfTrailingZeros);
                long j2 = this.mReceivedPointers[numberOfTrailingZeros].mTime;
                if (j2 < j) {
                    i2 = numberOfTrailingZeros;
                    j = j2;
                }
            }
            return i2;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("=========================");
            sb.append("\nDown pointers #");
            sb.append(getReceivedPointerDownCount());
            sb.append(" [ ");
            for (int i = 0; i < 32; i++) {
                if (isReceivedPointerDown(i)) {
                    sb.append(i);
                    sb.append(" ");
                }
            }
            sb.append("]");
            sb.append("\nPrimary pointer id [ ");
            sb.append(getPrimaryPointerId());
            sb.append(" ]");
            sb.append("\n=========================");
            return sb.toString();
        }
    }

    class PointerDownInfo {
        private long mTime;
        private float mX;
        private float mY;

        PointerDownInfo() {
        }

        public void set(float f, float f2, long j) {
            this.mX = f;
            this.mY = f2;
            this.mTime = j;
        }

        public void clear() {
            this.mX = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            this.mY = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            this.mTime = 0L;
        }
    }
}
