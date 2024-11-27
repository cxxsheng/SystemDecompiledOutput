package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
public class TouchExplorer extends com.android.server.accessibility.BaseEventStreamTransformation implements com.android.server.accessibility.gestures.GestureManifold.Listener {
    private static final float EDGE_SWIPE_HEIGHT_CM = 0.25f;
    private static final int EXIT_GESTURE_DETECTION_TIMEOUT = 2000;
    private static final long LOGGING_FLAGS = 12288;
    private static final float MAX_DRAGGING_ANGLE_COS = 0.52532196f;
    private final com.android.server.accessibility.AccessibilityManagerService mAms;
    private final android.content.Context mContext;
    private final int mDetermineUserIntentTimeout;
    private final com.android.server.accessibility.gestures.EventDispatcher mDispatcher;
    private int mDisplayId;
    private final int mDoubleTapSlop;
    private int mDraggingPointerId;
    private final float mEdgeSwipeHeightPixels;
    private final com.android.server.accessibility.gestures.TouchExplorer.ExitGestureDetectionModeDelayed mExitGestureDetectionModeDelayed;
    private android.graphics.Region mGestureDetectionPassthroughRegion;
    private final com.android.server.accessibility.gestures.GestureManifold mGestureDetector;
    private final android.os.Handler mHandler;
    private final com.android.server.accessibility.gestures.TouchState.ReceivedPointerTracker mReceivedPointerTracker;
    private final com.android.server.accessibility.gestures.TouchExplorer.SendHoverEnterAndMoveDelayed mSendHoverEnterAndMoveDelayed;
    private final com.android.server.accessibility.gestures.TouchExplorer.SendHoverExitDelayed mSendHoverExitDelayed;
    private final com.android.server.accessibility.gestures.TouchExplorer.SendAccessibilityEventDelayed mSendTouchExplorationEndDelayed;
    private final com.android.server.accessibility.gestures.TouchExplorer.SendAccessibilityEventDelayed mSendTouchInteractionEndDelayed;
    private com.android.server.accessibility.gestures.TouchState mState;
    private android.graphics.Region mTouchExplorationPassthroughRegion;
    private final int mTouchSlop;
    private static final java.lang.String LOG_TAG = "TouchExplorer";
    static final boolean DEBUG = android.util.Log.isLoggable(LOG_TAG, 3);

    public TouchExplorer(android.content.Context context, com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService) {
        this(context, accessibilityManagerService, null);
    }

    public TouchExplorer(android.content.Context context, com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService, com.android.server.accessibility.gestures.GestureManifold gestureManifold) {
        this(context, accessibilityManagerService, gestureManifold, new android.os.Handler(context.getMainLooper()));
    }

    @com.android.internal.annotations.VisibleForTesting
    TouchExplorer(android.content.Context context, com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService, com.android.server.accessibility.gestures.GestureManifold gestureManifold, @android.annotation.NonNull android.os.Handler handler) {
        this.mDisplayId = -1;
        this.mContext = context;
        this.mDisplayId = context.getDisplayId();
        this.mAms = accessibilityManagerService;
        this.mState = new com.android.server.accessibility.gestures.TouchState(this.mDisplayId, this.mAms);
        this.mReceivedPointerTracker = this.mState.getReceivedPointerTracker();
        this.mDispatcher = new com.android.server.accessibility.gestures.EventDispatcher(context, this.mAms, super.getNext(), this.mState);
        this.mDetermineUserIntentTimeout = android.view.ViewConfiguration.getDoubleTapTimeout();
        this.mDoubleTapSlop = android.view.ViewConfiguration.get(context).getScaledDoubleTapSlop();
        this.mTouchSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        this.mEdgeSwipeHeightPixels = (this.mContext.getResources().getDisplayMetrics().ydpi / com.android.server.accessibility.gestures.GestureUtils.CM_PER_INCH) * EDGE_SWIPE_HEIGHT_CM;
        this.mHandler = handler;
        this.mExitGestureDetectionModeDelayed = new com.android.server.accessibility.gestures.TouchExplorer.ExitGestureDetectionModeDelayed();
        this.mSendHoverEnterAndMoveDelayed = new com.android.server.accessibility.gestures.TouchExplorer.SendHoverEnterAndMoveDelayed();
        this.mSendHoverExitDelayed = new com.android.server.accessibility.gestures.TouchExplorer.SendHoverExitDelayed();
        this.mSendTouchExplorationEndDelayed = new com.android.server.accessibility.gestures.TouchExplorer.SendAccessibilityEventDelayed(1024, this.mDetermineUserIntentTimeout);
        this.mSendTouchInteractionEndDelayed = new com.android.server.accessibility.gestures.TouchExplorer.SendAccessibilityEventDelayed(2097152, this.mDetermineUserIntentTimeout);
        if (gestureManifold == null) {
            this.mGestureDetector = new com.android.server.accessibility.gestures.GestureManifold(context, this, this.mState, this.mHandler);
        } else {
            this.mGestureDetector = gestureManifold;
        }
        this.mGestureDetectionPassthroughRegion = new android.graphics.Region();
        this.mTouchExplorationPassthroughRegion = new android.graphics.Region();
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void clearEvents(int i) {
        if (i == 4098) {
            clear();
        }
        super.clearEvents(i);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onDestroy() {
        clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clear() {
        android.view.MotionEvent lastReceivedEvent = this.mState.getLastReceivedEvent();
        if (lastReceivedEvent != null) {
            clear(lastReceivedEvent, 33554432);
        }
    }

    private void clear(android.view.MotionEvent motionEvent, int i) {
        if (this.mState.isTouchExploring()) {
            sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
        }
        this.mDraggingPointerId = -1;
        this.mDispatcher.sendUpForInjectedDownPointers(motionEvent, i);
        this.mSendHoverEnterAndMoveDelayed.cancel();
        this.mSendHoverExitDelayed.cancel();
        this.mExitGestureDetectionModeDelayed.cancel();
        this.mSendTouchExplorationEndDelayed.cancel();
        this.mSendTouchInteractionEndDelayed.cancel();
        this.mGestureDetector.clear();
        this.mDispatcher.clear();
        this.mState.clear();
        this.mAms.onTouchInteractionEnd();
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mAms.getTraceManager().isA11yTracingEnabledForTypes(LOGGING_FLAGS)) {
            this.mAms.getTraceManager().logTrace("TouchExplorer.onMotionEvent", LOGGING_FLAGS, "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i);
        }
        if (!motionEvent.isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3)) {
            super.onMotionEvent(motionEvent, motionEvent2, i);
            return;
        }
        try {
            checkForMalformedEvent(motionEvent);
            checkForMalformedEvent(motionEvent2);
            if (DEBUG) {
                android.util.Slog.d(LOG_TAG, "Received event: " + motionEvent + ", policyFlags=0x" + java.lang.Integer.toHexString(i));
                android.util.Slog.d(LOG_TAG, this.mState.toString());
            }
            this.mState.onReceivedMotionEvent(motionEvent, motionEvent2, i);
            if (shouldPerformGestureDetection(motionEvent) && this.mGestureDetector.onMotionEvent(motionEvent, motionEvent2, i)) {
                return;
            }
            if (motionEvent.getActionMasked() == 3) {
                clear(motionEvent, i);
                return;
            }
            if (this.mState.isClear()) {
                handleMotionEventStateClear(motionEvent, motionEvent2, i);
                return;
            }
            if (this.mState.isTouchInteracting()) {
                handleMotionEventStateTouchInteracting(motionEvent, motionEvent2, i);
                return;
            }
            if (this.mState.isTouchExploring()) {
                handleMotionEventStateTouchExploring(motionEvent, motionEvent2, i);
                return;
            }
            if (this.mState.isDragging()) {
                handleMotionEventStateDragging(motionEvent, motionEvent2, i);
                return;
            }
            if (this.mState.isDelegating()) {
                handleMotionEventStateDelegating(motionEvent, motionEvent2, i);
                return;
            }
            if (this.mState.isGestureDetecting()) {
                this.mSendTouchInteractionEndDelayed.cancel();
                if (this.mState.isServiceDetectingGestures()) {
                    this.mAms.sendMotionEventToListeningServices(motionEvent2);
                    return;
                }
                return;
            }
            android.util.Slog.e(LOG_TAG, "Illegal state: " + this.mState);
            clear(motionEvent, i);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(LOG_TAG, "Ignoring malformed event: " + motionEvent.toString(), e);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (this.mAms.getTraceManager().isA11yTracingEnabledForTypes(LOGGING_FLAGS)) {
            this.mAms.getTraceManager().logTrace("TouchExplorer.onAccessibilityEvent", LOGGING_FLAGS, "event=" + accessibilityEvent);
        }
        if (accessibilityEvent.getEventType() == 256) {
            sendsPendingA11yEventsIfNeeded();
        }
        this.mState.onReceivedAccessibilityEvent(accessibilityEvent);
        super.onAccessibilityEvent(accessibilityEvent);
    }

    private void sendsPendingA11yEventsIfNeeded() {
        if (this.mSendHoverExitDelayed.isPending()) {
            return;
        }
        com.android.server.accessibility.Flags.sendA11yEventsBasedOnState();
        if (this.mSendTouchExplorationEndDelayed.isPending()) {
            this.mSendTouchExplorationEndDelayed.cancel();
            this.mDispatcher.sendAccessibilityEvent(1024);
        }
        if (this.mSendTouchInteractionEndDelayed.isPending()) {
            this.mSendTouchInteractionEndDelayed.cancel();
            this.mDispatcher.sendAccessibilityEvent(2097152);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureManifold.Listener
    public void onDoubleTapAndHold(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mAms.getTraceManager().isA11yTracingEnabledForTypes(LOGGING_FLAGS)) {
            this.mAms.getTraceManager().logTrace("TouchExplorer.onDoubleTapAndHold", LOGGING_FLAGS, "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i);
        }
        if (this.mDispatcher.longPressWithTouchEvents(motionEvent, i)) {
            sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
            if (isSendMotionEventsEnabled()) {
                dispatchGesture(new android.accessibilityservice.AccessibilityGestureEvent(18, this.mDisplayId, this.mGestureDetector.getMotionEvents()));
            }
            this.mState.startDelegating();
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureManifold.Listener
    public boolean onDoubleTap(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mAms.getTraceManager().isA11yTracingEnabledForTypes(LOGGING_FLAGS)) {
            this.mAms.getTraceManager().logTrace("TouchExplorer.onDoubleTap", LOGGING_FLAGS, "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i);
        }
        this.mAms.onTouchInteractionEnd();
        this.mSendHoverEnterAndMoveDelayed.cancel();
        this.mSendHoverExitDelayed.cancel();
        if (isSendMotionEventsEnabled()) {
            dispatchGesture(new android.accessibilityservice.AccessibilityGestureEvent(17, this.mDisplayId, this.mGestureDetector.getMotionEvents()));
        }
        if (this.mSendTouchExplorationEndDelayed.isPending()) {
            this.mSendTouchExplorationEndDelayed.forceSendAndRemove();
        }
        this.mDispatcher.sendAccessibilityEvent(2097152);
        this.mSendTouchInteractionEndDelayed.cancel();
        if (this.mAms.performActionOnAccessibilityFocusedItem(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK)) {
            return true;
        }
        android.util.Slog.e(LOG_TAG, "ACTION_CLICK failed. Dispatching motion events to simulate click.");
        if (motionEvent != null && motionEvent2 != null) {
            this.mDispatcher.clickWithTouchEvents(motionEvent, motionEvent2, i);
        }
        return true;
    }

    public void onDoubleTap() {
        onDoubleTap(this.mState.getLastReceivedEvent(), this.mState.getLastReceivedRawEvent(), this.mState.getLastReceivedPolicyFlags());
    }

    public void onDoubleTapAndHold() {
        onDoubleTapAndHold(this.mState.getLastReceivedEvent(), this.mState.getLastReceivedRawEvent(), this.mState.getLastReceivedPolicyFlags());
    }

    @Override // com.android.server.accessibility.gestures.GestureManifold.Listener
    public boolean onGestureStarted() {
        if (this.mAms.getTraceManager().isA11yTracingEnabledForTypes(LOGGING_FLAGS)) {
            this.mAms.getTraceManager().logTrace("TouchExplorer.onGestureStarted", LOGGING_FLAGS);
        }
        this.mSendHoverEnterAndMoveDelayed.cancel();
        this.mSendHoverExitDelayed.cancel();
        this.mExitGestureDetectionModeDelayed.post();
        this.mDispatcher.sendAccessibilityEvent(262144);
        return false;
    }

    @Override // com.android.server.accessibility.gestures.GestureManifold.Listener
    public boolean onGestureCompleted(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
        if (this.mAms.getTraceManager().isA11yTracingEnabledForTypes(LOGGING_FLAGS)) {
            this.mAms.getTraceManager().logTrace("TouchExplorer.onGestureCompleted", LOGGING_FLAGS, "event=" + accessibilityGestureEvent);
        }
        endGestureDetection(true);
        this.mSendTouchInteractionEndDelayed.cancel();
        dispatchGesture(accessibilityGestureEvent);
        return true;
    }

    @Override // com.android.server.accessibility.gestures.GestureManifold.Listener
    public boolean onGestureCancelled(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mAms.getTraceManager().isA11yTracingEnabledForTypes(LOGGING_FLAGS)) {
            this.mAms.getTraceManager().logTrace("TouchExplorer.onGestureCancelled", LOGGING_FLAGS, "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i);
        }
        if (this.mState.isGestureDetecting()) {
            endGestureDetection(motionEvent.getActionMasked() == 1);
            return true;
        }
        if (this.mState.isTouchExploring() && motionEvent.getActionMasked() == 2) {
            int primaryPointerId = 1 << this.mReceivedPointerTracker.getPrimaryPointerId();
            this.mSendHoverEnterAndMoveDelayed.addEvent(motionEvent, this.mState.getLastReceivedEvent());
            this.mSendHoverEnterAndMoveDelayed.forceSendAndRemove();
            this.mSendHoverExitDelayed.cancel();
            this.mDispatcher.sendMotionEvent(motionEvent, 7, motionEvent, primaryPointerId, i);
            return true;
        }
        if (isSendMotionEventsEnabled()) {
            dispatchGesture(new android.accessibilityservice.AccessibilityGestureEvent(0, this.mDisplayId, this.mGestureDetector.getMotionEvents()));
        }
        return false;
    }

    private void handleMotionEventStateClear(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        switch (motionEvent.getActionMasked()) {
            case 0:
                handleActionDown(motionEvent, motionEvent2, i);
                break;
        }
    }

    private void handleActionDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        this.mAms.onTouchInteractionStart();
        this.mSendHoverEnterAndMoveDelayed.cancel();
        this.mSendHoverEnterAndMoveDelayed.clear();
        this.mSendHoverExitDelayed.cancel();
        if (this.mState.isTouchExploring()) {
            sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
        }
        if (this.mState.isClear()) {
            if (!this.mSendHoverEnterAndMoveDelayed.isPending()) {
                int primaryPointerId = 1 << this.mReceivedPointerTracker.getPrimaryPointerId();
                if (this.mState.isServiceDetectingGestures()) {
                    this.mSendHoverEnterAndMoveDelayed.setPointerIdBits(primaryPointerId);
                    this.mSendHoverEnterAndMoveDelayed.setPolicyFlags(i);
                    this.mSendHoverEnterAndMoveDelayed.addEvent(motionEvent, motionEvent2);
                } else {
                    this.mSendHoverEnterAndMoveDelayed.post(motionEvent, motionEvent2, primaryPointerId, i);
                }
            } else {
                this.mSendHoverEnterAndMoveDelayed.addEvent(motionEvent, motionEvent2);
            }
            this.mSendTouchExplorationEndDelayed.forceSendAndRemove();
            this.mSendTouchInteractionEndDelayed.forceSendAndRemove();
            this.mDispatcher.sendAccessibilityEvent(1048576);
            if (this.mTouchExplorationPassthroughRegion.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                this.mState.startDelegating();
                android.view.MotionEvent obtainNoHistory = android.view.MotionEvent.obtainNoHistory(motionEvent);
                this.mDispatcher.sendMotionEvent(obtainNoHistory, obtainNoHistory.getAction(), motionEvent2, -1, i);
                this.mSendHoverEnterAndMoveDelayed.cancel();
            } else if (this.mGestureDetectionPassthroughRegion.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                this.mSendHoverEnterAndMoveDelayed.forceSendAndRemove();
            }
        } else {
            this.mSendTouchInteractionEndDelayed.cancel();
        }
        if (this.mState.isServiceDetectingGestures()) {
            this.mAms.sendMotionEventToListeningServices(motionEvent2);
        }
    }

    private void handleMotionEventStateTouchInteracting(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mSendTouchInteractionEndDelayed.cancel();
                handleActionDown(motionEvent, motionEvent2, i);
                break;
            case 1:
                handleActionUp(motionEvent, motionEvent2, i);
                break;
            case 2:
                handleActionMoveStateTouchInteracting(motionEvent, motionEvent2, i);
                break;
            case 5:
                handleActionPointerDown(motionEvent, motionEvent2, i);
                break;
            case 6:
                if (this.mState.isServiceDetectingGestures()) {
                    this.mAms.sendMotionEventToListeningServices(motionEvent2);
                    break;
                }
                break;
        }
    }

    private void handleMotionEventStateTouchExploring(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        switch (motionEvent.getActionMasked()) {
            case 0:
                handleActionDownStateTouchExploring(motionEvent, motionEvent2, i);
                break;
            case 1:
                handleActionUp(motionEvent, motionEvent2, i);
                break;
            case 2:
                handleActionMoveStateTouchExploring(motionEvent, motionEvent2, i);
                break;
            case 5:
                handleActionPointerDown(motionEvent, motionEvent2, i);
                break;
        }
    }

    private void handleActionPointerDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mSendHoverEnterAndMoveDelayed.isPending()) {
            this.mSendHoverEnterAndMoveDelayed.cancel();
            this.mSendHoverExitDelayed.cancel();
        } else {
            sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
        }
        if (this.mState.isServiceDetectingGestures()) {
            this.mAms.sendMotionEventToListeningServices(motionEvent2);
        }
    }

    private void handleActionMoveStateTouchInteracting(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        int findPointerIndex = motionEvent.findPointerIndex(this.mReceivedPointerTracker.getPrimaryPointerId());
        if (this.mState.isServiceDetectingGestures()) {
            this.mAms.sendMotionEventToListeningServices(motionEvent2);
            this.mSendHoverEnterAndMoveDelayed.addEvent(motionEvent, motionEvent2);
        }
        switch (motionEvent.getPointerCount()) {
            case 1:
                if (this.mSendHoverEnterAndMoveDelayed.isPending()) {
                    this.mSendHoverEnterAndMoveDelayed.addEvent(motionEvent, motionEvent2);
                    break;
                }
                break;
            case 2:
                if (!this.mGestureDetector.isMultiFingerGesturesEnabled() || this.mGestureDetector.isTwoFingerPassthroughEnabled()) {
                    this.mSendHoverEnterAndMoveDelayed.cancel();
                    this.mSendHoverExitDelayed.cancel();
                    if (this.mGestureDetector.isMultiFingerGesturesEnabled() && this.mGestureDetector.isTwoFingerPassthroughEnabled()) {
                        if (findPointerIndex >= 0) {
                            for (int i2 = 0; i2 < motionEvent.getPointerCount(); i2++) {
                                int pointerId = motionEvent.getPointerId(i2);
                                if (!this.mReceivedPointerTracker.isReceivedPointerDown(pointerId)) {
                                    android.util.Slog.e(LOG_TAG, "Invalid pointer id: " + pointerId);
                                }
                                if (java.lang.Math.hypot(this.mReceivedPointerTracker.getReceivedPointerDownX(pointerId) - motionEvent2.getX(i2), this.mReceivedPointerTracker.getReceivedPointerDownY(pointerId) - motionEvent2.getY(i2)) < this.mTouchSlop * 2) {
                                    break;
                                }
                            }
                        }
                    }
                    android.view.MotionEvent obtainNoHistory = android.view.MotionEvent.obtainNoHistory(motionEvent);
                    if (isDraggingGesture(obtainNoHistory)) {
                        if (isSendMotionEventsEnabled()) {
                            dispatchGesture(new android.accessibilityservice.AccessibilityGestureEvent(-1, this.mDisplayId, this.mGestureDetector.getMotionEvents()));
                        }
                        computeDraggingPointerIdIfNeeded(obtainNoHistory);
                        int i3 = 1 << this.mDraggingPointerId;
                        obtainNoHistory.setEdgeFlags(this.mReceivedPointerTracker.getLastReceivedDownEdgeFlags());
                        android.view.MotionEvent computeDownEventForDrag = computeDownEventForDrag(obtainNoHistory);
                        if (computeDownEventForDrag != null) {
                            this.mDispatcher.sendMotionEvent(computeDownEventForDrag, 0, motionEvent2, i3, i);
                            this.mDispatcher.sendMotionEvent(obtainNoHistory, 2, motionEvent2, i3, i);
                        } else {
                            this.mDispatcher.sendMotionEvent(obtainNoHistory, 0, motionEvent2, i3, i);
                        }
                        this.mState.startDragging();
                        break;
                    } else {
                        if (isSendMotionEventsEnabled()) {
                            dispatchGesture(new android.accessibilityservice.AccessibilityGestureEvent(-1, this.mDisplayId, this.mGestureDetector.getMotionEvents()));
                        }
                        this.mState.startDelegating();
                        this.mDispatcher.sendDownForAllNotInjectedPointers(obtainNoHistory, i);
                        break;
                    }
                }
                break;
            default:
                if (this.mGestureDetector.isMultiFingerGesturesEnabled()) {
                    if (this.mGestureDetector.isTwoFingerPassthroughEnabled() && motionEvent.getPointerCount() == 3 && allPointersDownOnBottomEdge(motionEvent)) {
                        if (DEBUG) {
                            android.util.Slog.d(LOG_TAG, "Three-finger edge swipe detected.");
                        }
                        if (isSendMotionEventsEnabled()) {
                            dispatchGesture(new android.accessibilityservice.AccessibilityGestureEvent(-1, this.mDisplayId, this.mGestureDetector.getMotionEvents()));
                        }
                        this.mState.startDelegating();
                        if (this.mState.isTouchExploring()) {
                            this.mDispatcher.sendDownForAllNotInjectedPointers(motionEvent, i);
                            break;
                        } else {
                            this.mDispatcher.sendDownForAllNotInjectedPointersWithOriginalDown(motionEvent, i);
                            break;
                        }
                    }
                } else {
                    if (isSendMotionEventsEnabled()) {
                        dispatchGesture(new android.accessibilityservice.AccessibilityGestureEvent(-1, this.mDisplayId, this.mGestureDetector.getMotionEvents()));
                    }
                    this.mState.startDelegating();
                    this.mDispatcher.sendDownForAllNotInjectedPointers(android.view.MotionEvent.obtainNoHistory(motionEvent), i);
                    break;
                }
                break;
        }
    }

    private void handleActionUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mState.isServiceDetectingGestures() && this.mState.isTouchInteracting()) {
            this.mAms.sendMotionEventToListeningServices(motionEvent2);
        }
        this.mAms.onTouchInteractionEnd();
        int pointerId = 1 << motionEvent.getPointerId(motionEvent.getActionIndex());
        if (this.mSendHoverEnterAndMoveDelayed.isPending()) {
            com.android.server.accessibility.Flags.resetHoverEventTimerOnActionUp();
            this.mSendHoverExitDelayed.post(motionEvent, motionEvent2, pointerId, i);
        } else {
            sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
        }
        if (!this.mSendTouchInteractionEndDelayed.isPending()) {
            this.mSendTouchInteractionEndDelayed.post();
        }
    }

    private void handleActionDownStateTouchExploring(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        this.mSendTouchExplorationEndDelayed.cancel();
        this.mSendTouchInteractionEndDelayed.cancel();
        sendTouchExplorationGestureStartAndHoverEnterIfNeeded(i);
    }

    private void handleActionMoveStateTouchExploring(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        int primaryPointerId = this.mReceivedPointerTracker.getPrimaryPointerId();
        int i2 = 1 << primaryPointerId;
        int findPointerIndex = motionEvent.findPointerIndex(primaryPointerId);
        switch (motionEvent.getPointerCount()) {
            case 1:
                sendTouchExplorationGestureStartAndHoverEnterIfNeeded(i);
                this.mDispatcher.sendMotionEvent(motionEvent, 7, motionEvent2, i2, i);
                break;
            case 2:
                if (!this.mGestureDetector.isMultiFingerGesturesEnabled() || this.mGestureDetector.isTwoFingerPassthroughEnabled()) {
                    if (this.mSendHoverEnterAndMoveDelayed.isPending()) {
                        this.mSendHoverEnterAndMoveDelayed.cancel();
                        this.mSendHoverExitDelayed.cancel();
                    }
                    if (java.lang.Math.hypot(this.mReceivedPointerTracker.getReceivedPointerDownX(primaryPointerId) - motionEvent2.getX(findPointerIndex), this.mReceivedPointerTracker.getReceivedPointerDownY(primaryPointerId) - motionEvent2.getY(findPointerIndex)) > this.mDoubleTapSlop) {
                        handleActionMoveStateTouchInteracting(motionEvent, motionEvent2, i);
                        break;
                    } else {
                        sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
                        break;
                    }
                }
                break;
            default:
                if (!this.mGestureDetector.isMultiFingerGesturesEnabled()) {
                    if (this.mSendHoverEnterAndMoveDelayed.isPending()) {
                        this.mSendHoverEnterAndMoveDelayed.cancel();
                        this.mSendHoverExitDelayed.cancel();
                    } else {
                        sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
                    }
                    handleActionMoveStateTouchInteracting(motionEvent, motionEvent2, i);
                    break;
                }
                break;
        }
    }

    private void handleMotionEventStateDragging(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        int i2;
        if (this.mGestureDetector.isMultiFingerGesturesEnabled() && !this.mGestureDetector.isTwoFingerPassthroughEnabled()) {
        }
        if (motionEvent.findPointerIndex(this.mDraggingPointerId) == -1) {
            android.util.Slog.e(LOG_TAG, "mDraggingPointerId doesn't match any pointers on current event. mDraggingPointerId: " + java.lang.Integer.toString(this.mDraggingPointerId) + ", Event: " + motionEvent);
            this.mDraggingPointerId = -1;
            i2 = 0;
        } else {
            i2 = 1 << this.mDraggingPointerId;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                android.util.Slog.e(LOG_TAG, "Dragging state can be reached only if two pointers are already down");
                clear(motionEvent, i);
                break;
            case 1:
                if (motionEvent.getPointerId(com.android.server.accessibility.gestures.GestureUtils.getActionIndex(motionEvent)) == this.mDraggingPointerId) {
                    this.mDraggingPointerId = -1;
                    this.mDispatcher.sendMotionEvent(motionEvent, 1, motionEvent2, i2, i);
                }
                this.mAms.onTouchInteractionEnd();
                this.mDispatcher.sendAccessibilityEvent(2097152);
                break;
            case 2:
                if (this.mDraggingPointerId != -1) {
                    if (this.mState.isServiceDetectingGestures()) {
                        this.mAms.sendMotionEventToListeningServices(motionEvent2);
                        computeDraggingPointerIdIfNeeded(motionEvent);
                        this.mDispatcher.sendMotionEvent(motionEvent, 2, motionEvent2, i2, i);
                        break;
                    } else {
                        switch (motionEvent.getPointerCount()) {
                            case 1:
                                break;
                            case 2:
                                if (isDraggingGesture(motionEvent)) {
                                    computeDraggingPointerIdIfNeeded(motionEvent);
                                    this.mDispatcher.sendMotionEvent(motionEvent, 2, motionEvent2, i2, i);
                                    break;
                                } else {
                                    this.mState.startDelegating();
                                    this.mDraggingPointerId = -1;
                                    android.view.MotionEvent obtainNoHistory = android.view.MotionEvent.obtainNoHistory(motionEvent);
                                    this.mDispatcher.sendMotionEvent(obtainNoHistory, 1, motionEvent2, i2, i);
                                    this.mDispatcher.sendDownForAllNotInjectedPointers(obtainNoHistory, i);
                                    break;
                                }
                            default:
                                if (this.mState.isServiceDetectingGestures()) {
                                    this.mAms.sendMotionEventToListeningServices(motionEvent2);
                                    break;
                                } else {
                                    this.mState.startDelegating();
                                    this.mDraggingPointerId = -1;
                                    android.view.MotionEvent obtainNoHistory2 = android.view.MotionEvent.obtainNoHistory(motionEvent);
                                    this.mDispatcher.sendMotionEvent(obtainNoHistory2, 1, motionEvent2, i2, i);
                                    this.mDispatcher.sendDownForAllNotInjectedPointers(obtainNoHistory2, i);
                                    break;
                                }
                        }
                    }
                }
                break;
            case 5:
                if (this.mState.isServiceDetectingGestures()) {
                    this.mAms.sendMotionEventToListeningServices(motionEvent2);
                    break;
                } else {
                    this.mState.startDelegating();
                    if (this.mDraggingPointerId != -1) {
                        this.mDispatcher.sendMotionEvent(motionEvent, 1, motionEvent2, i2, i);
                    }
                    this.mDispatcher.sendDownForAllNotInjectedPointers(motionEvent, i);
                    break;
                }
            case 6:
                this.mDraggingPointerId = -1;
                this.mDispatcher.sendMotionEvent(motionEvent, 1, motionEvent2, i2, i);
                break;
        }
    }

    private void handleMotionEventStateDelegating(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        switch (motionEvent.getActionMasked()) {
            case 0:
                android.util.Slog.e(LOG_TAG, "Delegating state can only be reached if there is at least one pointer down!");
                clear(motionEvent, i);
                break;
            case 1:
                this.mDispatcher.sendMotionEvent(motionEvent, motionEvent.getAction(), motionEvent2, -1, i);
                this.mAms.onTouchInteractionEnd();
                this.mDispatcher.clear();
                this.mDispatcher.sendAccessibilityEvent(2097152);
                break;
            default:
                this.mDispatcher.sendMotionEvent(motionEvent, motionEvent.getAction(), motionEvent2, -1, i);
                break;
        }
    }

    private void endGestureDetection(boolean z) {
        this.mAms.onTouchInteractionEnd();
        this.mDispatcher.sendAccessibilityEvent(524288);
        if (z) {
            this.mDispatcher.sendAccessibilityEvent(2097152);
        }
        this.mExitGestureDetectionModeDelayed.cancel();
    }

    private void sendHoverExitAndTouchExplorationGestureEndIfNeeded(int i) {
        android.view.MotionEvent lastInjectedHoverEvent = this.mState.getLastInjectedHoverEvent();
        if (lastInjectedHoverEvent != null && lastInjectedHoverEvent.getActionMasked() != 10) {
            int pointerIdBits = lastInjectedHoverEvent.getPointerIdBits();
            if (!this.mSendTouchExplorationEndDelayed.isPending()) {
                this.mSendTouchExplorationEndDelayed.post();
            }
            this.mDispatcher.sendMotionEvent(lastInjectedHoverEvent, 10, this.mState.getLastReceivedEvent(), pointerIdBits, i);
        }
    }

    private void sendTouchExplorationGestureStartAndHoverEnterIfNeeded(int i) {
        if (!this.mState.isTouchExploring()) {
            this.mDispatcher.sendAccessibilityEvent(512);
        }
        android.view.MotionEvent lastInjectedHoverEvent = this.mState.getLastInjectedHoverEvent();
        if (lastInjectedHoverEvent != null && lastInjectedHoverEvent.getActionMasked() == 10) {
            this.mDispatcher.sendMotionEvent(lastInjectedHoverEvent, 9, this.mState.getLastReceivedEvent(), lastInjectedHoverEvent.getPointerIdBits(), i);
        }
    }

    private boolean isDraggingGesture(android.view.MotionEvent motionEvent) {
        return com.android.server.accessibility.gestures.GestureUtils.isDraggingGesture(this.mReceivedPointerTracker.getReceivedPointerDownX(0), this.mReceivedPointerTracker.getReceivedPointerDownY(0), this.mReceivedPointerTracker.getReceivedPointerDownX(1), this.mReceivedPointerTracker.getReceivedPointerDownY(1), motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1), MAX_DRAGGING_ANGLE_COS);
    }

    private void computeDraggingPointerIdIfNeeded(android.view.MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 2) {
            this.mDraggingPointerId = -1;
            return;
        }
        if (this.mDraggingPointerId != -1 && motionEvent.findPointerIndex(motionEvent.findPointerIndex(this.mDraggingPointerId)) >= 0) {
            return;
        }
        float x = motionEvent.getX(0);
        float y = motionEvent.getY(0);
        int pointerId = motionEvent.getPointerId(0);
        float x2 = motionEvent.getX(1);
        float y2 = motionEvent.getY(1);
        int pointerId2 = motionEvent.getPointerId(1);
        if (getDistanceToClosestEdge(x, y) >= getDistanceToClosestEdge(x2, y2)) {
            pointerId = pointerId2;
        }
        this.mDraggingPointerId = pointerId;
    }

    private float getDistanceToClosestEdge(float f, float f2) {
        long j = this.mContext.getResources().getDisplayMetrics().widthPixels;
        long j2 = this.mContext.getResources().getDisplayMetrics().heightPixels;
        float f3 = j - f;
        if (f >= f3) {
            f = f3;
        }
        if (f > f2) {
            f = f2;
        }
        float f4 = j2 - f2;
        if (f > f4) {
            return f4;
        }
        return f;
    }

    private android.view.MotionEvent computeDownEventForDrag(android.view.MotionEvent motionEvent) {
        if (!this.mState.isTouchExploring() && this.mDraggingPointerId != -1 && motionEvent != null) {
            float receivedPointerDownX = this.mReceivedPointerTracker.getReceivedPointerDownX(this.mDraggingPointerId);
            float receivedPointerDownY = this.mReceivedPointerTracker.getReceivedPointerDownY(this.mDraggingPointerId);
            long receivedPointerDownTime = this.mReceivedPointerTracker.getReceivedPointerDownTime(this.mDraggingPointerId);
            android.view.MotionEvent.PointerCoords[] pointerCoordsArr = {new android.view.MotionEvent.PointerCoords()};
            pointerCoordsArr[0].x = receivedPointerDownX;
            pointerCoordsArr[0].y = receivedPointerDownY;
            android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = {new android.view.MotionEvent.PointerProperties()};
            pointerPropertiesArr[0].id = this.mDraggingPointerId;
            pointerPropertiesArr[0].toolType = 1;
            android.view.MotionEvent obtain = android.view.MotionEvent.obtain(receivedPointerDownTime, receivedPointerDownTime, 0, 1, pointerPropertiesArr, pointerCoordsArr, motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getFlags());
            motionEvent.setDownTime(receivedPointerDownTime);
            return obtain;
        }
        return null;
    }

    private boolean allPointersDownOnBottomEdge(android.view.MotionEvent motionEvent) {
        long j = this.mContext.getResources().getDisplayMetrics().heightPixels;
        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
            float receivedPointerDownY = this.mReceivedPointerTracker.getReceivedPointerDownY(motionEvent.getPointerId(i));
            if (receivedPointerDownY < j - this.mEdgeSwipeHeightPixels) {
                if (DEBUG) {
                    android.util.Slog.d(LOG_TAG, "The pointer is not on the bottom edge" + receivedPointerDownY);
                }
                return false;
            }
        }
        return true;
    }

    public com.android.server.accessibility.gestures.TouchState getState() {
        return this.mState;
    }

    @Override // com.android.server.accessibility.BaseEventStreamTransformation, com.android.server.accessibility.EventStreamTransformation
    public void setNext(com.android.server.accessibility.EventStreamTransformation eventStreamTransformation) {
        this.mDispatcher.setReceiver(eventStreamTransformation);
        super.setNext(eventStreamTransformation);
    }

    public void setServiceHandlesDoubleTap(boolean z) {
        this.mGestureDetector.setServiceHandlesDoubleTap(z);
    }

    public void setMultiFingerGesturesEnabled(boolean z) {
        this.mGestureDetector.setMultiFingerGesturesEnabled(z);
    }

    public void setTwoFingerPassthroughEnabled(boolean z) {
        this.mGestureDetector.setTwoFingerPassthroughEnabled(z);
    }

    public void setGestureDetectionPassthroughRegion(android.graphics.Region region) {
        this.mGestureDetectionPassthroughRegion = region;
    }

    public void setTouchExplorationPassthroughRegion(android.graphics.Region region) {
        this.mTouchExplorationPassthroughRegion = region;
    }

    public void setSendMotionEventsEnabled(boolean z) {
        this.mGestureDetector.setSendMotionEventsEnabled(z);
    }

    public boolean isSendMotionEventsEnabled() {
        return this.mGestureDetector.isSendMotionEventsEnabled();
    }

    public void setServiceDetectsGestures(boolean z) {
        this.mState.setServiceDetectsGestures(z);
    }

    private boolean shouldPerformGestureDetection(android.view.MotionEvent motionEvent) {
        if (this.mState.isServiceDetectingGestures() || this.mState.isDelegating() || this.mState.isDragging()) {
            return false;
        }
        if (motionEvent.getActionMasked() == 0) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            return (this.mTouchExplorationPassthroughRegion.contains(x, y) || this.mGestureDetectionPassthroughRegion.contains(x, y)) ? false : true;
        }
        return true;
    }

    public void requestTouchExploration() {
        android.view.MotionEvent lastReceivedEvent;
        if (DEBUG) {
            android.util.Slog.d(LOG_TAG, "Starting touch explorer from service.");
        }
        if (this.mState.isServiceDetectingGestures() && this.mState.isTouchInteracting()) {
            this.mHandler.removeCallbacks(this.mSendHoverEnterAndMoveDelayed);
            int primaryPointerId = this.mReceivedPointerTracker.getPrimaryPointerId();
            if (primaryPointerId == -1 && (lastReceivedEvent = this.mState.getLastReceivedEvent()) != null) {
                primaryPointerId = lastReceivedEvent.getPointerId(0);
            }
            if (primaryPointerId == -1) {
                android.util.Slog.e(LOG_TAG, "Unable to find a valid pointer for touch exploration.");
                return;
            }
            sendHoverExitAndTouchExplorationGestureEndIfNeeded(primaryPointerId);
            int lastReceivedPolicyFlags = this.mState.getLastReceivedPolicyFlags();
            this.mSendHoverEnterAndMoveDelayed.setPointerIdBits(1 << primaryPointerId);
            this.mSendHoverEnterAndMoveDelayed.setPolicyFlags(lastReceivedPolicyFlags);
            this.mSendHoverEnterAndMoveDelayed.run();
            this.mSendHoverEnterAndMoveDelayed.clear();
            if (this.mReceivedPointerTracker.getReceivedPointerDownCount() == 0) {
                sendHoverExitAndTouchExplorationGestureEndIfNeeded(lastReceivedPolicyFlags);
            }
        }
    }

    public void requestDragging(int i) {
        if (this.mState.isServiceDetectingGestures()) {
            if (i < 0 || i > 32 || !this.mReceivedPointerTracker.isReceivedPointerDown(i)) {
                android.util.Slog.e(LOG_TAG, "Trying to drag with invalid pointer: " + i);
                return;
            }
            if (this.mState.isTouchExploring()) {
                if (this.mSendHoverExitDelayed.isPending()) {
                    this.mSendHoverExitDelayed.forceSendAndRemove();
                }
                if (this.mSendTouchExplorationEndDelayed.isPending()) {
                    this.mSendTouchExplorationEndDelayed.forceSendAndRemove();
                }
            }
            if (!this.mState.isTouchInteracting()) {
                android.util.Slog.e(LOG_TAG, "Error: Trying to drag from " + com.android.server.accessibility.gestures.TouchState.getStateSymbolicName(this.mState.getState()));
                return;
            }
            this.mDraggingPointerId = i;
            if (DEBUG) {
                android.util.Slog.d(LOG_TAG, "Drag requested on pointer " + this.mDraggingPointerId);
            }
            android.view.MotionEvent lastReceivedEvent = this.mState.getLastReceivedEvent();
            android.view.MotionEvent lastReceivedRawEvent = this.mState.getLastReceivedRawEvent();
            if (lastReceivedEvent == null || lastReceivedRawEvent == null) {
                android.util.Slog.e(LOG_TAG, "Unable to start dragging: unable to get last event.");
                return;
            }
            int lastReceivedPolicyFlags = this.mState.getLastReceivedPolicyFlags();
            int i2 = 1 << this.mDraggingPointerId;
            lastReceivedEvent.setEdgeFlags(this.mReceivedPointerTracker.getLastReceivedDownEdgeFlags());
            android.view.MotionEvent computeDownEventForDrag = computeDownEventForDrag(lastReceivedEvent);
            this.mState.startDragging();
            if (computeDownEventForDrag != null) {
                this.mDispatcher.sendMotionEvent(computeDownEventForDrag, 0, lastReceivedRawEvent, i2, lastReceivedPolicyFlags);
                this.mDispatcher.sendMotionEvent(lastReceivedEvent, 2, lastReceivedRawEvent, i2, lastReceivedPolicyFlags);
            } else {
                this.mDispatcher.sendMotionEvent(lastReceivedEvent, 0, lastReceivedRawEvent, i2, lastReceivedPolicyFlags);
            }
        }
    }

    public void requestDelegating() {
        if (this.mState.isServiceDetectingGestures()) {
            if (this.mState.isTouchExploring()) {
                if (this.mSendHoverExitDelayed.isPending()) {
                    this.mSendHoverExitDelayed.forceSendAndRemove();
                }
                if (this.mSendTouchExplorationEndDelayed.isPending()) {
                    this.mSendTouchExplorationEndDelayed.forceSendAndRemove();
                }
            }
            if (!this.mState.isTouchInteracting() && !this.mState.isDragging()) {
                android.util.Slog.e(LOG_TAG, "Error: Trying to delegate from " + com.android.server.accessibility.gestures.TouchState.getStateSymbolicName(this.mState.getState()));
                return;
            }
            android.view.MotionEvent lastReceivedEvent = this.mState.getLastReceivedEvent();
            android.view.MotionEvent lastReceivedRawEvent = this.mState.getLastReceivedRawEvent();
            if (lastReceivedEvent == null || lastReceivedRawEvent == null) {
                android.util.Slog.d(LOG_TAG, "Unable to start delegating: unable to get last received event.");
                return;
            }
            int lastReceivedPolicyFlags = this.mState.getLastReceivedPolicyFlags();
            if (this.mState.isDragging()) {
                com.android.server.accessibility.Flags.fixDragPointerWhenEndingDrag();
                this.mDispatcher.sendMotionEvent(lastReceivedEvent, 1, lastReceivedRawEvent, -1, lastReceivedPolicyFlags);
            }
            this.mState.startDelegating();
            this.mDispatcher.sendDownForAllNotInjectedPointers(lastReceivedEvent, lastReceivedPolicyFlags);
        }
    }

    private final class ExitGestureDetectionModeDelayed implements java.lang.Runnable {
        private ExitGestureDetectionModeDelayed() {
        }

        public void post() {
            com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.postDelayed(this, 2000L);
        }

        public void cancel() {
            com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.accessibility.gestures.TouchExplorer.this.mDispatcher.sendAccessibilityEvent(524288);
            com.android.server.accessibility.gestures.TouchExplorer.this.clear();
        }
    }

    private static void checkForMalformedEvent(android.view.MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() < 0) {
            throw new java.lang.IllegalArgumentException("Invalid pointer count: " + motionEvent.getPointerCount());
        }
        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
            try {
                motionEvent.getPointerId(i);
                float x = motionEvent.getX(i);
                float y = motionEvent.getY(i);
                if (java.lang.Float.isNaN(x) || java.lang.Float.isNaN(y) || x < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || y < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    throw new java.lang.IllegalArgumentException("Invalid coordinates: (" + x + ", " + y + ")");
                }
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("Encountered exception getting details of pointer " + i + " / " + motionEvent.getPointerCount(), e);
            }
        }
    }

    class SendHoverEnterAndMoveDelayed implements java.lang.Runnable {
        private int mPointerIdBits;
        private int mPolicyFlags;
        private final java.lang.String LOG_TAG_SEND_HOVER_DELAYED = "SendHoverEnterAndMoveDelayed";
        private final java.util.List<android.view.MotionEvent> mEvents = new java.util.ArrayList();
        private final java.util.List<android.view.MotionEvent> mRawEvents = new java.util.ArrayList();

        SendHoverEnterAndMoveDelayed() {
        }

        public void post(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i, int i2) {
            cancel();
            addEvent(motionEvent, motionEvent2);
            this.mPointerIdBits = i;
            this.mPolicyFlags = i2;
            com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.postDelayed(this, com.android.server.accessibility.gestures.TouchExplorer.this.mDetermineUserIntentTimeout);
        }

        public void addEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2) {
            this.mEvents.add(android.view.MotionEvent.obtain(motionEvent));
            this.mRawEvents.add(android.view.MotionEvent.obtain(motionEvent2));
        }

        public void cancel() {
            if (isPending()) {
                com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.removeCallbacks(this);
                clear();
            }
        }

        public void repost() {
            if (isPending()) {
                com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.removeCallbacks(this);
                com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.postDelayed(this, com.android.server.accessibility.gestures.TouchExplorer.this.mDetermineUserIntentTimeout);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isPending() {
            return com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.hasCallbacks(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clear() {
            this.mPointerIdBits = -1;
            this.mPolicyFlags = 0;
            for (int size = this.mEvents.size() - 1; size >= 0; size--) {
                this.mEvents.remove(size).recycle();
            }
            for (int size2 = this.mRawEvents.size() - 1; size2 >= 0; size2--) {
                this.mRawEvents.remove(size2).recycle();
            }
        }

        public void forceSendAndRemove() {
            if (isPending()) {
                run();
                cancel();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (com.android.server.accessibility.gestures.TouchExplorer.this.mReceivedPointerTracker.getReceivedPointerDownCount() > 1) {
                android.util.Slog.e(com.android.server.accessibility.gestures.TouchExplorer.LOG_TAG, "Attempted touch exploration with " + com.android.server.accessibility.gestures.TouchExplorer.this.mReceivedPointerTracker.getReceivedPointerDownCount() + " pointers down.");
                return;
            }
            com.android.server.accessibility.gestures.TouchExplorer.this.mDispatcher.sendAccessibilityEvent(512);
            if (com.android.server.accessibility.gestures.TouchExplorer.this.isSendMotionEventsEnabled()) {
                com.android.server.accessibility.gestures.TouchExplorer.this.dispatchGesture(new android.accessibilityservice.AccessibilityGestureEvent(-2, com.android.server.accessibility.gestures.TouchExplorer.this.mState.getLastReceivedEvent().getDisplayId(), com.android.server.accessibility.gestures.TouchExplorer.this.mGestureDetector.getMotionEvents()));
            }
            if (!this.mEvents.isEmpty() && !this.mRawEvents.isEmpty()) {
                com.android.server.accessibility.gestures.TouchExplorer.this.mDispatcher.sendMotionEvent(this.mEvents.get(0), 9, this.mRawEvents.get(0), this.mPointerIdBits, this.mPolicyFlags);
                if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                    android.util.Slog.d("SendHoverEnterAndMoveDelayed", "Injecting motion event: ACTION_HOVER_ENTER");
                }
                int size = this.mEvents.size();
                for (int i = 1; i < size; i++) {
                    com.android.server.accessibility.gestures.TouchExplorer.this.mDispatcher.sendMotionEvent(this.mEvents.get(i), 7, this.mRawEvents.get(i), this.mPointerIdBits, this.mPolicyFlags);
                    if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                        android.util.Slog.d("SendHoverEnterAndMoveDelayed", "Injecting motion event: ACTION_HOVER_MOVE");
                    }
                }
            }
            clear();
        }

        public void setPointerIdBits(int i) {
            this.mPointerIdBits = i;
        }

        public void setPolicyFlags(int i) {
            this.mPolicyFlags = i;
        }
    }

    class SendHoverExitDelayed implements java.lang.Runnable {
        private final java.lang.String LOG_TAG_SEND_HOVER_DELAYED = "SendHoverExitDelayed";
        private int mPointerIdBits;
        private int mPolicyFlags;
        private android.view.MotionEvent mPrototype;
        private android.view.MotionEvent mRawEvent;

        SendHoverExitDelayed() {
        }

        public void post(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i, int i2) {
            cancel();
            this.mPrototype = android.view.MotionEvent.obtain(motionEvent);
            this.mRawEvent = android.view.MotionEvent.obtain(motionEvent2);
            this.mPointerIdBits = i;
            this.mPolicyFlags = i2;
            com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.postDelayed(this, com.android.server.accessibility.gestures.TouchExplorer.this.mDetermineUserIntentTimeout);
        }

        public void cancel() {
            if (isPending()) {
                com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.removeCallbacks(this);
                clear();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isPending() {
            return com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.hasCallbacks(this);
        }

        private void clear() {
            if (this.mPrototype != null) {
                this.mPrototype.recycle();
            }
            if (this.mRawEvent != null) {
                this.mRawEvent.recycle();
            }
            this.mPrototype = null;
            this.mRawEvent = null;
            this.mPointerIdBits = -1;
            this.mPolicyFlags = 0;
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
                android.util.Slog.d("SendHoverExitDelayed", "Injecting motion event: ACTION_HOVER_EXIT");
            }
            com.android.server.accessibility.gestures.TouchExplorer.this.mDispatcher.sendMotionEvent(this.mPrototype, 10, this.mRawEvent, this.mPointerIdBits, this.mPolicyFlags);
            if (!com.android.server.accessibility.gestures.TouchExplorer.this.mSendTouchExplorationEndDelayed.isPending()) {
                com.android.server.accessibility.gestures.TouchExplorer.this.mSendTouchExplorationEndDelayed.cancel();
                com.android.server.accessibility.gestures.TouchExplorer.this.mSendTouchExplorationEndDelayed.post();
            }
            if (com.android.server.accessibility.gestures.TouchExplorer.this.mSendTouchInteractionEndDelayed.isPending()) {
                com.android.server.accessibility.gestures.TouchExplorer.this.mSendTouchInteractionEndDelayed.cancel();
                com.android.server.accessibility.gestures.TouchExplorer.this.mSendTouchInteractionEndDelayed.post();
            }
            clear();
        }
    }

    private class SendAccessibilityEventDelayed implements java.lang.Runnable {
        private final int mDelay;
        private final int mEventType;

        public SendAccessibilityEventDelayed(int i, int i2) {
            this.mEventType = i;
            this.mDelay = i2;
        }

        public void cancel() {
            com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.removeCallbacks(this);
        }

        public void post() {
            com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.postDelayed(this, this.mDelay);
        }

        public boolean isPending() {
            return com.android.server.accessibility.gestures.TouchExplorer.this.mHandler.hasCallbacks(this);
        }

        public void forceSendAndRemove() {
            if (isPending()) {
                run();
                cancel();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.accessibility.gestures.TouchExplorer.this.mDispatcher.sendAccessibilityEvent(this.mEventType);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
        if (DEBUG) {
            android.util.Slog.d(LOG_TAG, "Dispatching gesture event:" + accessibilityGestureEvent.toString());
        }
        this.mAms.onGesture(accessibilityGestureEvent);
    }

    public java.lang.String toString() {
        return "TouchExplorer { mTouchState: " + this.mState + ", mDetermineUserIntentTimeout: " + this.mDetermineUserIntentTimeout + ", mDoubleTapSlop: " + this.mDoubleTapSlop + ", mDraggingPointerId: " + this.mDraggingPointerId + " }";
    }
}
