package android.accessibilityservice;

/* loaded from: classes.dex */
public final class TouchInteractionController {
    private static final int MAX_POINTER_COUNT = 32;
    public static final int STATE_CLEAR = 0;
    public static final int STATE_DELEGATING = 4;
    public static final int STATE_DRAGGING = 3;
    public static final int STATE_TOUCH_EXPLORING = 2;
    public static final int STATE_TOUCH_INTERACTING = 1;
    private android.util.ArrayMap<android.accessibilityservice.TouchInteractionController.Callback, java.util.concurrent.Executor> mCallbacks;
    private final int mDisplayId;
    private final java.lang.Object mLock;
    private final android.accessibilityservice.AccessibilityService mService;
    private boolean mServiceDetectsGestures;
    private java.util.Queue<android.view.MotionEvent> mQueuedMotionEvents = new java.util.LinkedList();
    private boolean mStateChangeRequested = false;
    private int mState = 0;

    public interface Callback {
        void onMotionEvent(android.view.MotionEvent motionEvent);

        void onStateChanged(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface State {
    }

    TouchInteractionController(android.accessibilityservice.AccessibilityService accessibilityService, java.lang.Object obj, int i) {
        this.mDisplayId = i;
        this.mLock = obj;
        this.mService = accessibilityService;
    }

    public void registerCallback(java.util.concurrent.Executor executor, android.accessibilityservice.TouchInteractionController.Callback callback) {
        synchronized (this.mLock) {
            if (this.mCallbacks == null) {
                this.mCallbacks = new android.util.ArrayMap<>();
            }
            this.mCallbacks.put(callback, executor);
            if (this.mCallbacks.size() == 1) {
                setServiceDetectsGestures(true);
            }
        }
    }

    public boolean unregisterCallback(android.accessibilityservice.TouchInteractionController.Callback callback) {
        boolean z;
        if (this.mCallbacks == null) {
            return false;
        }
        synchronized (this.mLock) {
            z = this.mCallbacks.remove(callback) != null;
            if (z && this.mCallbacks.size() == 0) {
                setServiceDetectsGestures(false);
            }
        }
        return z;
    }

    public void unregisterAllCallbacks() {
        if (this.mCallbacks != null) {
            synchronized (this.mLock) {
                this.mCallbacks.clear();
                setServiceDetectsGestures(false);
            }
        }
    }

    void onMotionEvent(android.view.MotionEvent motionEvent) {
        if (this.mStateChangeRequested) {
            this.mQueuedMotionEvents.add(motionEvent);
        } else {
            sendEventToAllListeners(motionEvent);
        }
    }

    private void sendEventToAllListeners(final android.view.MotionEvent motionEvent) {
        android.util.ArrayMap arrayMap;
        synchronized (this.mLock) {
            arrayMap = new android.util.ArrayMap(this.mCallbacks);
        }
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            final android.accessibilityservice.TouchInteractionController.Callback callback = (android.accessibilityservice.TouchInteractionController.Callback) arrayMap.keyAt(i);
            java.util.concurrent.Executor executor = (java.util.concurrent.Executor) arrayMap.valueAt(i);
            if (executor != null) {
                executor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.TouchInteractionController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.accessibilityservice.TouchInteractionController.Callback.this.onMotionEvent(motionEvent);
                    }
                });
            } else {
                callback.onMotionEvent(motionEvent);
            }
        }
    }

    void onStateChanged(final int i) {
        android.util.ArrayMap arrayMap;
        this.mState = i;
        synchronized (this.mLock) {
            arrayMap = new android.util.ArrayMap(this.mCallbacks);
        }
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            final android.accessibilityservice.TouchInteractionController.Callback callback = (android.accessibilityservice.TouchInteractionController.Callback) arrayMap.keyAt(i2);
            java.util.concurrent.Executor executor = (java.util.concurrent.Executor) arrayMap.valueAt(i2);
            if (executor != null) {
                executor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.TouchInteractionController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.accessibilityservice.TouchInteractionController.Callback.this.onStateChanged(i);
                    }
                });
            } else {
                callback.onStateChanged(i);
            }
        }
        this.mStateChangeRequested = false;
        while (this.mQueuedMotionEvents.size() > 0) {
            sendEventToAllListeners(this.mQueuedMotionEvents.poll());
        }
    }

    private void setServiceDetectsGestures(boolean z) {
        android.view.accessibility.AccessibilityInteractionClient.getInstance();
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.setServiceDetectsGesturesEnabled(this.mDisplayId, z);
                this.mServiceDetectsGestures = z;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public void requestTouchExploration() {
        validateTransitionRequest();
        android.view.accessibility.AccessibilityInteractionClient.getInstance();
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.requestTouchExploration(this.mDisplayId);
                this.mStateChangeRequested = true;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public void requestDragging(int i) {
        validateTransitionRequest();
        if (i < 0 || i > 32) {
            throw new java.lang.IllegalArgumentException("Invalid pointer id: " + i);
        }
        android.view.accessibility.AccessibilityInteractionClient.getInstance();
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.requestDragging(this.mDisplayId, i);
                this.mStateChangeRequested = true;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public void requestDelegating() {
        validateTransitionRequest();
        android.view.accessibility.AccessibilityInteractionClient.getInstance();
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.requestDelegating(this.mDisplayId);
                this.mStateChangeRequested = true;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public void performClick() {
        android.view.accessibility.AccessibilityInteractionClient.getInstance();
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.onDoubleTap(this.mDisplayId);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public void performLongClickAndStartDrag() {
        android.view.accessibility.AccessibilityInteractionClient.getInstance();
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.getConnectionId());
        if (connection != null) {
            try {
                connection.onDoubleTapAndHold(this.mDisplayId);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    private void validateTransitionRequest() {
        if (!this.mServiceDetectsGestures || this.mCallbacks.size() == 0) {
            throw new java.lang.IllegalStateException("State transitions are not allowed without first adding a callback.");
        }
        if (this.mState == 4 || this.mState == 2) {
            throw new java.lang.IllegalStateException("State transition requests are not allowed in " + stateToString(this.mState));
        }
    }

    public int getMaxPointerCount() {
        return 32;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public int getState() {
        int i;
        synchronized (this.mLock) {
            i = this.mState;
        }
        return i;
    }

    public static java.lang.String stateToString(int i) {
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
            default:
                return "Unknown state: " + i;
        }
    }
}
