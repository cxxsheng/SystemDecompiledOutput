package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public class WindowMagnificationGestureHandler extends com.android.server.accessibility.magnification.MagnificationGestureHandler {
    private static final float MAX_SCALE = 8.0f;
    private static final float MIN_SCALE = 1.0f;
    private final android.content.Context mContext;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State mCurrentState;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.DelegatingState mDelegatingState;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.DetectingState mDetectingState;
    private final com.android.server.accessibility.magnification.MagnificationConnectionManager mMagnificationConnectionManager;
    private com.android.server.accessibility.magnification.MotionEventDispatcherDelegate mMotionEventDispatcherDelegate;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.PanningScalingGestureState mObservePanningScalingState;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State mPreviousState;
    private final android.graphics.Point mTempPoint;
    private long mTripleTapAndHoldStartedTime;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.ViewportDraggingState mViewportDraggingState;
    private static final boolean DEBUG_STATE_TRANSITIONS = com.android.server.accessibility.magnification.MagnificationGestureHandler.DEBUG_ALL | false;
    private static final boolean DEBUG_DETECTING = com.android.server.accessibility.magnification.MagnificationGestureHandler.DEBUG_ALL | false;

    public WindowMagnificationGestureHandler(android.content.Context context, com.android.server.accessibility.magnification.MagnificationConnectionManager magnificationConnectionManager, com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager, com.android.server.accessibility.magnification.MagnificationGestureHandler.Callback callback, boolean z, boolean z2, boolean z3, int i) {
        super(i, z, z2, z3, accessibilityTraceManager, callback);
        this.mTempPoint = new android.graphics.Point();
        this.mTripleTapAndHoldStartedTime = 0L;
        if (com.android.server.accessibility.magnification.MagnificationGestureHandler.DEBUG_ALL) {
            android.util.Slog.i(this.mLogTag, "WindowMagnificationGestureHandler() , displayId = " + i + ")");
        }
        this.mContext = context;
        this.mMagnificationConnectionManager = magnificationConnectionManager;
        this.mMotionEventDispatcherDelegate = new com.android.server.accessibility.magnification.MotionEventDispatcherDelegate(context, new com.android.server.accessibility.magnification.MotionEventDispatcherDelegate.EventDispatcher() { // from class: com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticLambda0
            @Override // com.android.server.accessibility.magnification.MotionEventDispatcherDelegate.EventDispatcher
            public final void dispatchMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i2) {
                com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.lambda$new$0(motionEvent, motionEvent2, i2);
            }
        });
        this.mDelegatingState = new com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.DelegatingState(this.mMotionEventDispatcherDelegate);
        this.mDetectingState = new com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.DetectingState(context);
        this.mViewportDraggingState = new com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.ViewportDraggingState();
        this.mObservePanningScalingState = new com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.PanningScalingGestureState(new com.android.server.accessibility.magnification.PanningScalingHandler(context, 8.0f, 1.0f, true, new com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate() { // from class: com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.1
            @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
            public boolean processScroll(int i2, float f, float f2) {
                return com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mMagnificationConnectionManager.processScroll(i2, f, f2);
            }

            @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
            public void setScale(int i2, float f) {
                com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mMagnificationConnectionManager.setScale(i2, f);
            }

            @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
            public float getScale(int i2) {
                return com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mMagnificationConnectionManager.getScale(i2);
            }
        }));
        transitionTo(this.mDetectingState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        dispatchTransformedEvent(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    void onMotionEventInternal(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        this.mObservePanningScalingState.mPanningScalingHandler.onTouchEvent(motionEvent);
        this.mCurrentState.onMotionEvent(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void clearEvents(int i) {
        if (i == 4098) {
            resetToDetectState();
        }
        super.clearEvents(i);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onDestroy() {
        if (com.android.server.accessibility.magnification.MagnificationGestureHandler.DEBUG_ALL) {
            android.util.Slog.i(this.mLogTag, "onDestroy(); delayed = " + this.mDetectingState.toString());
        }
        this.mMagnificationConnectionManager.disableWindowMagnification(this.mDisplayId, true);
        resetToDetectState();
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public void handleShortcutTriggered() {
        android.graphics.Point point = this.mTempPoint;
        getScreenSize(this.mTempPoint);
        toggleMagnification(point.x / 2.0f, point.y / 2.0f, 0);
    }

    private void getScreenSize(android.graphics.Point point) {
        this.mContext.getDisplay().getRealSize(point);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public int getMode() {
        return 2;
    }

    private void enableWindowMagnifier(float f, float f2, int i) {
        if (com.android.server.accessibility.magnification.MagnificationGestureHandler.DEBUG_ALL) {
            android.util.Slog.i(this.mLogTag, "enableWindowMagnifier :" + f + ", " + f2 + ", " + i);
        }
        this.mMagnificationConnectionManager.enableWindowMagnification(this.mDisplayId, android.util.MathUtils.constrain(this.mMagnificationConnectionManager.getPersistedScale(this.mDisplayId), 1.0f, 8.0f), f, f2, i);
    }

    private void disableWindowMagnifier() {
        if (com.android.server.accessibility.magnification.MagnificationGestureHandler.DEBUG_ALL) {
            android.util.Slog.i(this.mLogTag, "disableWindowMagnifier()");
        }
        this.mMagnificationConnectionManager.disableWindowMagnification(this.mDisplayId, false);
    }

    private void toggleMagnification(float f, float f2, int i) {
        if (this.mMagnificationConnectionManager.isWindowMagnifierEnabled(this.mDisplayId)) {
            disableWindowMagnifier();
        } else {
            enableWindowMagnifier(f, f2, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTripleTap(android.view.MotionEvent motionEvent) {
        if (DEBUG_DETECTING) {
            android.util.Slog.i(this.mLogTag, "onTripleTap()");
        }
        toggleMagnification(motionEvent.getX(), motionEvent.getY(), 0);
    }

    @com.android.internal.annotations.VisibleForTesting
    void onTripleTapAndHold(android.view.MotionEvent motionEvent) {
        if (DEBUG_DETECTING) {
            android.util.Slog.i(this.mLogTag, "onTripleTapAndHold()");
        }
        this.mViewportDraggingState.mEnabledBeforeDrag = this.mMagnificationConnectionManager.isWindowMagnifierEnabled(this.mDisplayId);
        enableWindowMagnifier(motionEvent.getX(), motionEvent.getY(), 1);
        this.mTripleTapAndHoldStartedTime = android.os.SystemClock.uptimeMillis();
        transitionTo(this.mViewportDraggingState);
    }

    @com.android.internal.annotations.VisibleForTesting
    void releaseTripleTapAndHold() {
        if (!this.mViewportDraggingState.mEnabledBeforeDrag) {
            this.mMagnificationConnectionManager.disableWindowMagnification(this.mDisplayId, true);
        }
        transitionTo(this.mDetectingState);
        if (this.mTripleTapAndHoldStartedTime != 0) {
            logMagnificationTripleTapAndHoldSession(android.os.SystemClock.uptimeMillis() - this.mTripleTapAndHoldStartedTime);
            this.mTripleTapAndHoldStartedTime = 0L;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void logMagnificationTripleTapAndHoldSession(long j) {
        com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logMagnificationTripleTapAndHoldSession(j);
    }

    void resetToDetectState() {
        transitionTo(this.mDetectingState);
    }

    interface State {
        void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i);

        default void clear() {
        }

        default void onEnter() {
        }

        default void onExit() {
        }

        default java.lang.String name() {
            return getClass().getSimpleName();
        }

        static java.lang.String nameOf(@android.annotation.Nullable com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State state) {
            return state != null ? state.name() : "null";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void transitionTo(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State state) {
        if (DEBUG_STATE_TRANSITIONS) {
            java.lang.String str = this.mLogTag;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("state transition: ");
            sb.append((com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State.nameOf(this.mCurrentState) + " -> " + com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State.nameOf(state) + " at " + java.util.Arrays.asList((java.lang.StackTraceElement[]) java.util.Arrays.copyOfRange(new java.lang.RuntimeException().getStackTrace(), 1, 5))).replace(getClass().getName(), ""));
            android.util.Slog.i(str, sb.toString());
        }
        this.mPreviousState = this.mCurrentState;
        if (this.mPreviousState != null) {
            this.mPreviousState.onExit();
        }
        this.mCurrentState = state;
        if (this.mCurrentState != null) {
            this.mCurrentState.onEnter();
        }
    }

    final class PanningScalingGestureState implements com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State {
        private final com.android.server.accessibility.magnification.PanningScalingHandler mPanningScalingHandler;

        PanningScalingGestureState(com.android.server.accessibility.magnification.PanningScalingHandler panningScalingHandler) {
            this.mPanningScalingHandler = panningScalingHandler;
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onEnter() {
            this.mPanningScalingHandler.setEnabled(true);
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onExit() {
            this.mPanningScalingHandler.setEnabled(false);
            com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mMagnificationConnectionManager.persistScale(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDisplayId);
            clear();
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 1 || actionMasked == 3) {
                com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDetectingState);
            }
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void clear() {
            this.mPanningScalingHandler.clear();
        }

        public java.lang.String toString() {
            return "PanningScalingState{mPanningScalingHandler=" + this.mPanningScalingHandler + '}';
        }
    }

    final class DelegatingState implements com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State {
        private final com.android.server.accessibility.magnification.MotionEventDispatcherDelegate mMotionEventDispatcherDelegate;

        DelegatingState(com.android.server.accessibility.magnification.MotionEventDispatcherDelegate motionEventDispatcherDelegate) {
            this.mMotionEventDispatcherDelegate = motionEventDispatcherDelegate;
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            this.mMotionEventDispatcherDelegate.dispatchMotionEvent(motionEvent, motionEvent2, i);
            switch (motionEvent.getActionMasked()) {
                case 1:
                case 3:
                    com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDetectingState);
                    break;
            }
        }
    }

    final class ViewportDraggingState implements com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State {
        boolean mEnabledBeforeDrag;
        private float mLastX = Float.NaN;
        private float mLastY = Float.NaN;

        ViewportDraggingState() {
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            switch (motionEvent.getActionMasked()) {
                case 1:
                case 3:
                    com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.releaseTripleTapAndHold();
                    break;
                case 2:
                    if (!java.lang.Float.isNaN(this.mLastX) && !java.lang.Float.isNaN(this.mLastY)) {
                        com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mMagnificationConnectionManager.moveWindowMagnification(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDisplayId, motionEvent.getX() - this.mLastX, motionEvent.getY() - this.mLastY);
                    }
                    this.mLastX = motionEvent.getX();
                    this.mLastY = motionEvent.getY();
                    break;
            }
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void clear() {
            this.mLastX = Float.NaN;
            this.mLastY = Float.NaN;
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onExit() {
            clear();
        }

        public java.lang.String toString() {
            return "ViewportDraggingState{mLastX=" + this.mLastX + ",mLastY=" + this.mLastY + '}';
        }
    }

    final class DetectingState implements com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State, com.android.server.accessibility.magnification.MagnificationGesturesObserver.Callback {
        private final com.android.server.accessibility.magnification.MagnificationGesturesObserver mGesturesObserver;

        DetectingState(android.content.Context context) {
            int i;
            int i2;
            com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture();
            int i3 = com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDetectSingleFingerTripleTap ? 3 : 1;
            if (com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDetectSingleFingerTripleTap) {
                i = 105;
            } else {
                i = 103;
            }
            com.android.server.accessibility.gestures.MultiTap multiTap = new com.android.server.accessibility.gestures.MultiTap(context, i3, i, null);
            int i4 = com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDetectSingleFingerTripleTap ? 3 : 1;
            if (com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDetectSingleFingerTripleTap) {
                i2 = 106;
            } else {
                i2 = 104;
            }
            this.mGesturesObserver = new com.android.server.accessibility.magnification.MagnificationGesturesObserver(this, new com.android.server.accessibility.magnification.SimpleSwipe(context), multiTap, new com.android.server.accessibility.gestures.MultiTapAndHold(context, i4, i2, null), new com.android.server.accessibility.magnification.TwoFingersDownOrSwipe(context));
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            this.mGesturesObserver.onMotionEvent(motionEvent, motionEvent2, i);
        }

        public java.lang.String toString() {
            return "DetectingState{mGestureTimeoutObserver=" + this.mGesturesObserver + '}';
        }

        @Override // com.android.server.accessibility.magnification.MagnificationGesturesObserver.Callback
        public boolean shouldStopDetection(android.view.MotionEvent motionEvent) {
            if (com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mMagnificationConnectionManager.isWindowMagnifierEnabled(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDisplayId) || com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDetectSingleFingerTripleTap) {
                return false;
            }
            if (com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDetectTwoFingerTripleTap) {
                com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture();
            }
            return true;
        }

        @Override // com.android.server.accessibility.magnification.MagnificationGesturesObserver.Callback
        public void onGestureCompleted(int i, long j, java.util.List<com.android.server.accessibility.magnification.MotionEventInfo> list, android.view.MotionEvent motionEvent) {
            if (com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.DEBUG_DETECTING) {
                android.util.Slog.d(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mLogTag, "onGestureDetected : gesture = " + com.android.server.accessibility.magnification.MagnificationGestureMatcher.gestureIdToString(i));
                android.util.Slog.d(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mLogTag, "onGestureDetected : delayedEventQueue = " + list);
            }
            if (i == 101 && com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mMagnificationConnectionManager.pointersInWindow(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDisplayId, motionEvent) > 0) {
                com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mObservePanningScalingState);
                return;
            }
            if (i == 105) {
                com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.onTripleTap(motionEvent);
            } else if (i == 106) {
                com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.onTripleTapAndHold(motionEvent);
            } else {
                com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mMotionEventDispatcherDelegate.sendDelayedMotionEvents(list, j);
                changeToDelegateStateIfNeed(motionEvent);
            }
        }

        @Override // com.android.server.accessibility.magnification.MagnificationGesturesObserver.Callback
        public void onGestureCancelled(long j, java.util.List<com.android.server.accessibility.magnification.MotionEventInfo> list, android.view.MotionEvent motionEvent) {
            if (com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.DEBUG_DETECTING) {
                android.util.Slog.d(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mLogTag, "onGestureCancelled : delayedEventQueue = " + list);
            }
            com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mMotionEventDispatcherDelegate.sendDelayedMotionEvents(list, j);
            changeToDelegateStateIfNeed(motionEvent);
        }

        private void changeToDelegateStateIfNeed(android.view.MotionEvent motionEvent) {
            if (motionEvent != null && (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3)) {
                return;
            }
            com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.this.mDelegatingState);
        }
    }

    public java.lang.String toString() {
        return "WindowMagnificationGestureHandler{mDetectingState=" + this.mDetectingState + ", mDelegatingState=" + this.mDelegatingState + ", mViewportDraggingState=" + this.mViewportDraggingState + ", mMagnifiedInteractionState=" + this.mObservePanningScalingState + ", mCurrentState=" + com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State.nameOf(this.mCurrentState) + ", mPreviousState=" + com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State.nameOf(this.mPreviousState) + ", mMagnificationConnectionManager=" + this.mMagnificationConnectionManager + ", mDisplayId=" + this.mDisplayId + '}';
    }
}
