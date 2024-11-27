package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public abstract class MagnificationGestureHandler extends com.android.server.accessibility.BaseEventStreamTransformation {
    protected static final boolean DEBUG_ALL = android.util.Log.isLoggable("MagnificationGestureHandler", 3);
    protected static final boolean DEBUG_EVENT_STREAM = DEBUG_ALL | false;
    protected final com.android.server.accessibility.magnification.MagnificationGestureHandler.Callback mCallback;
    private final java.util.Queue<android.view.MotionEvent> mDebugInputEventHistory;
    private final java.util.Queue<android.view.MotionEvent> mDebugOutputEventHistory;
    protected final boolean mDetectShortcutTrigger;
    protected final boolean mDetectSingleFingerTripleTap;
    protected final boolean mDetectTwoFingerTripleTap;
    protected final int mDisplayId;
    protected final java.lang.String mLogTag = getClass().getSimpleName();
    private final com.android.server.accessibility.AccessibilityTraceManager mTrace;

    public interface Callback {
        void onTouchInteractionEnd(int i, int i2);

        void onTouchInteractionStart(int i, int i2);
    }

    public abstract int getMode();

    abstract void handleShortcutTriggered();

    abstract void onMotionEventInternal(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i);

    protected MagnificationGestureHandler(int i, boolean z, boolean z2, boolean z3, com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager, @android.annotation.NonNull com.android.server.accessibility.magnification.MagnificationGestureHandler.Callback callback) {
        this.mDisplayId = i;
        this.mDetectSingleFingerTripleTap = z;
        com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture();
        this.mDetectTwoFingerTripleTap = false;
        this.mDetectShortcutTrigger = z3;
        this.mTrace = accessibilityTraceManager;
        this.mCallback = callback;
        this.mDebugInputEventHistory = DEBUG_EVENT_STREAM ? new java.util.ArrayDeque() : null;
        this.mDebugOutputEventHistory = DEBUG_EVENT_STREAM ? new java.util.ArrayDeque() : null;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (DEBUG_ALL) {
            android.util.Slog.i(this.mLogTag, "onMotionEvent(" + motionEvent + ")");
        }
        if (this.mTrace.isA11yTracingEnabledForTypes(12288L)) {
            this.mTrace.logTrace("MagnificationGestureHandler.onMotionEvent", 12288L, "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i);
        }
        if (DEBUG_EVENT_STREAM) {
            storeEventInto(this.mDebugInputEventHistory, motionEvent);
        }
        if (shouldDispatchTransformedEvent(motionEvent)) {
            dispatchTransformedEvent(motionEvent, motionEvent2, i);
            return;
        }
        onMotionEventInternal(motionEvent, motionEvent2, i);
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mCallback.onTouchInteractionStart(this.mDisplayId, getMode());
        } else if (action == 1 || action == 3) {
            this.mCallback.onTouchInteractionEnd(this.mDisplayId, getMode());
        }
    }

    private boolean shouldDispatchTransformedEvent(android.view.MotionEvent motionEvent) {
        if ((!this.mDetectSingleFingerTripleTap && !this.mDetectTwoFingerTripleTap && !this.mDetectShortcutTrigger) || !motionEvent.isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3)) {
            return true;
        }
        return false;
    }

    final void dispatchTransformedEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (DEBUG_EVENT_STREAM) {
            storeEventInto(this.mDebugOutputEventHistory, motionEvent);
            try {
                super.onMotionEvent(motionEvent, motionEvent2, i);
                return;
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException("Exception downstream following input events: " + this.mDebugInputEventHistory + "\nTransformed into output events: " + this.mDebugOutputEventHistory, e);
            }
        }
        super.onMotionEvent(motionEvent, motionEvent2, i);
    }

    private static void storeEventInto(java.util.Queue<android.view.MotionEvent> queue, android.view.MotionEvent motionEvent) {
        queue.add(android.view.MotionEvent.obtain(motionEvent));
        while (!queue.isEmpty() && motionEvent.getEventTime() - queue.peek().getEventTime() > 5000) {
            queue.remove().recycle();
        }
    }

    public void notifyShortcutTriggered() {
        if (DEBUG_ALL) {
            android.util.Slog.i(this.mLogTag, "notifyShortcutTriggered():");
        }
        if (this.mDetectShortcutTrigger) {
            handleShortcutTriggered();
        }
    }
}
