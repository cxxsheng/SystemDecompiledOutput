package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
class EventDispatcher {
    private static final int CLICK_LOCATION_ACCESSIBILITY_FOCUS = 1;
    private static final int CLICK_LOCATION_LAST_TOUCH_EXPLORED = 2;
    private static final int CLICK_LOCATION_NONE = 0;
    private static final java.lang.String LOG_TAG = "EventDispatcher";
    private final com.android.server.accessibility.AccessibilityManagerService mAms;
    private android.content.Context mContext;
    private int mLongPressingPointerDeltaX;
    private int mLongPressingPointerDeltaY;
    private com.android.server.accessibility.EventStreamTransformation mReceiver;
    private com.android.server.accessibility.gestures.TouchState mState;
    private int mLongPressingPointerId = -1;
    private final android.graphics.Point mTempPoint = new android.graphics.Point();

    EventDispatcher(android.content.Context context, com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService, com.android.server.accessibility.EventStreamTransformation eventStreamTransformation, com.android.server.accessibility.gestures.TouchState touchState) {
        this.mContext = context;
        this.mAms = accessibilityManagerService;
        this.mReceiver = eventStreamTransformation;
        this.mState = touchState;
    }

    public void setReceiver(com.android.server.accessibility.EventStreamTransformation eventStreamTransformation) {
        this.mReceiver = eventStreamTransformation;
    }

    void sendMotionEvent(android.view.MotionEvent motionEvent, int i, android.view.MotionEvent motionEvent2, int i2, int i3) {
        android.view.MotionEvent split;
        long lastInjectedDownEventTime;
        motionEvent.setAction(i);
        if (i2 == -1) {
            split = motionEvent;
        } else {
            try {
                split = motionEvent.split(i2);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.e(LOG_TAG, "sendMotionEvent: Failed to split motion event: " + e);
                return;
            }
        }
        if (i == 0) {
            lastInjectedDownEventTime = split.getEventTime();
        } else {
            lastInjectedDownEventTime = this.mState.getLastInjectedDownEventTime();
        }
        android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = new android.view.MotionEvent.PointerProperties[split.getPointerCount()];
        android.view.MotionEvent.PointerCoords[] pointerCoordsArr = new android.view.MotionEvent.PointerCoords[split.getPointerCount()];
        for (int i4 = 0; i4 < split.getPointerCount(); i4++) {
            android.view.MotionEvent.PointerCoords pointerCoords = new android.view.MotionEvent.PointerCoords();
            split.getPointerCoords(i4, pointerCoords);
            pointerCoordsArr[i4] = pointerCoords;
            android.view.MotionEvent.PointerProperties pointerProperties = new android.view.MotionEvent.PointerProperties();
            split.getPointerProperties(i4, pointerProperties);
            pointerPropertiesArr[i4] = pointerProperties;
        }
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(lastInjectedDownEventTime, split.getEventTime(), split.getAction(), split.getPointerCount(), pointerPropertiesArr, pointerCoordsArr, split.getMetaState(), split.getButtonState(), split.getXPrecision(), split.getYPrecision(), motionEvent2.getDeviceId(), split.getEdgeFlags(), motionEvent2.getSource(), split.getDisplayId(), split.getFlags(), split.getClassification());
        if (this.mLongPressingPointerId >= 0) {
            obtain = offsetEvent(obtain, -this.mLongPressingPointerDeltaX, -this.mLongPressingPointerDeltaY);
        }
        if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
            android.util.Slog.d(LOG_TAG, "Injecting event: " + obtain + ", policyFlags=0x" + java.lang.Integer.toHexString(i3));
        }
        int i5 = i3 | 1073741824;
        if (this.mReceiver != null) {
            this.mReceiver.onMotionEvent(obtain, motionEvent2, i5);
        } else {
            android.util.Slog.e(LOG_TAG, "Error sending event: no receiver specified.");
        }
        this.mState.onInjectedMotionEvent(obtain);
        if (obtain != motionEvent) {
            obtain.recycle();
        }
    }

    void sendAccessibilityEvent(int i) {
        android.view.accessibility.AccessibilityManager accessibilityManager = android.view.accessibility.AccessibilityManager.getInstance(this.mContext);
        if (accessibilityManager.isEnabled()) {
            android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(i);
            obtain.setWindowId(this.mAms.getActiveWindowId());
            accessibilityManager.sendAccessibilityEvent(obtain);
            if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                android.util.Slog.d(LOG_TAG, "Sending accessibility event" + android.view.accessibility.AccessibilityEvent.eventTypeToString(i));
            }
        }
        this.mState.onInjectedAccessibilityEvent(i);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("=========================");
        sb.append("\nDown pointers #");
        sb.append(java.lang.Integer.bitCount(this.mState.getInjectedPointersDown()));
        sb.append(" [ ");
        for (int i = 0; i < 32; i++) {
            if (this.mState.isInjectedPointerDown(i)) {
                sb.append(i);
                sb.append(" ");
            }
        }
        sb.append("]");
        sb.append("\n=========================");
        return sb.toString();
    }

    private android.view.MotionEvent offsetEvent(android.view.MotionEvent motionEvent, int i, int i2) {
        if (i != 0 || i2 != 0) {
            int findPointerIndex = motionEvent.findPointerIndex(this.mLongPressingPointerId);
            int pointerCount = motionEvent.getPointerCount();
            android.view.MotionEvent.PointerProperties[] createArray = android.view.MotionEvent.PointerProperties.createArray(pointerCount);
            android.view.MotionEvent.PointerCoords[] createArray2 = android.view.MotionEvent.PointerCoords.createArray(pointerCount);
            for (int i3 = 0; i3 < pointerCount; i3++) {
                motionEvent.getPointerProperties(i3, createArray[i3]);
                motionEvent.getPointerCoords(i3, createArray2[i3]);
                if (i3 == findPointerIndex) {
                    createArray2[i3].x += i;
                    createArray2[i3].y += i2;
                }
            }
            return android.view.MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), motionEvent.getAction(), motionEvent.getPointerCount(), createArray, createArray2, motionEvent.getMetaState(), motionEvent.getButtonState(), 1.0f, 1.0f, motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getDisplayId(), motionEvent.getFlags());
        }
        return motionEvent;
    }

    private int computeInjectionAction(int i, int i2) {
        switch (i) {
            case 0:
            case 5:
                if (this.mState.getInjectedPointerDownCount() == 0) {
                    return 0;
                }
                return (i2 << 8) | 5;
            case 6:
                if (this.mState.getInjectedPointerDownCount() == 1) {
                    return 1;
                }
                return (i2 << 8) | 6;
            default:
                return i;
        }
    }

    void sendDownForAllNotInjectedPointers(android.view.MotionEvent motionEvent, int i) {
        int pointerCount = motionEvent.getPointerCount();
        int i2 = 0;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            int pointerId = motionEvent.getPointerId(i3);
            if (!this.mState.isInjectedPointerDown(pointerId)) {
                i2 |= 1 << pointerId;
                sendMotionEvent(motionEvent, computeInjectionAction(0, i3), this.mState.getLastReceivedEvent(), i2, i);
            }
        }
    }

    void sendDownForAllNotInjectedPointersWithOriginalDown(android.view.MotionEvent motionEvent, int i) {
        int pointerCount = motionEvent.getPointerCount();
        android.view.MotionEvent computeInjectionDownEvent = computeInjectionDownEvent(motionEvent);
        int i2 = 0;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            int pointerId = motionEvent.getPointerId(i3);
            if (!this.mState.isInjectedPointerDown(pointerId)) {
                int i4 = i2 | (1 << pointerId);
                sendMotionEvent(computeInjectionDownEvent, computeInjectionAction(0, i3), this.mState.getLastReceivedEvent(), i4, i);
                i2 = i4;
            }
        }
    }

    private android.view.MotionEvent computeInjectionDownEvent(android.view.MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        if (pointerCount != this.mState.getReceivedPointerTracker().getReceivedPointerDownCount()) {
            android.util.Slog.w(LOG_TAG, "The pointer count doesn't match the received count.");
            return android.view.MotionEvent.obtain(motionEvent);
        }
        android.view.MotionEvent.PointerCoords[] pointerCoordsArr = new android.view.MotionEvent.PointerCoords[pointerCount];
        android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = new android.view.MotionEvent.PointerProperties[pointerCount];
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = motionEvent.getPointerId(i);
            float receivedPointerDownX = this.mState.getReceivedPointerTracker().getReceivedPointerDownX(pointerId);
            float receivedPointerDownY = this.mState.getReceivedPointerTracker().getReceivedPointerDownY(pointerId);
            pointerCoordsArr[i] = new android.view.MotionEvent.PointerCoords();
            pointerCoordsArr[i].x = receivedPointerDownX;
            pointerCoordsArr[i].y = receivedPointerDownY;
            pointerPropertiesArr[i] = new android.view.MotionEvent.PointerProperties();
            pointerPropertiesArr[i].id = pointerId;
            pointerPropertiesArr[i].toolType = 1;
        }
        return android.view.MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getDownTime(), motionEvent.getAction(), pointerCount, pointerPropertiesArr, pointerCoordsArr, motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getFlags());
    }

    void sendUpForInjectedDownPointers(android.view.MotionEvent motionEvent, int i) {
        int pointerIdBits = motionEvent.getPointerIdBits();
        int pointerCount = motionEvent.getPointerCount();
        for (int i2 = 0; i2 < pointerCount; i2++) {
            int pointerId = motionEvent.getPointerId(i2);
            if (this.mState.isInjectedPointerDown(pointerId)) {
                sendMotionEvent(motionEvent, computeInjectionAction(6, i2), this.mState.getLastReceivedEvent(), pointerIdBits, i);
                pointerIdBits &= ~(1 << pointerId);
            }
        }
    }

    public boolean longPressWithTouchEvents(android.view.MotionEvent motionEvent, int i) {
        android.graphics.Point point = this.mTempPoint;
        if (computeClickLocation(point) == 0 || motionEvent == null) {
            return false;
        }
        int actionIndex = motionEvent.getActionIndex();
        this.mLongPressingPointerId = motionEvent.getPointerId(actionIndex);
        this.mLongPressingPointerDeltaX = ((int) motionEvent.getX(actionIndex)) - point.x;
        this.mLongPressingPointerDeltaY = ((int) motionEvent.getY(actionIndex)) - point.y;
        sendDownForAllNotInjectedPointers(motionEvent, i);
        return true;
    }

    void clear() {
        this.mLongPressingPointerId = -1;
        this.mLongPressingPointerDeltaX = 0;
        this.mLongPressingPointerDeltaY = 0;
    }

    public void clickWithTouchEvents(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        int actionIndex = motionEvent.getActionIndex();
        motionEvent.getPointerId(actionIndex);
        int computeClickLocation = computeClickLocation(this.mTempPoint);
        if (computeClickLocation == 0) {
            android.util.Slog.e(LOG_TAG, "Unable to compute click location.");
            return;
        }
        android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = {new android.view.MotionEvent.PointerProperties()};
        motionEvent.getPointerProperties(actionIndex, pointerPropertiesArr[0]);
        android.view.MotionEvent.PointerCoords[] pointerCoordsArr = {new android.view.MotionEvent.PointerCoords()};
        pointerCoordsArr[0].x = r3.x;
        pointerCoordsArr[0].y = r3.y;
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), 0, 1, pointerPropertiesArr, pointerCoordsArr, 0, 0, 1.0f, 1.0f, motionEvent.getDeviceId(), 0, motionEvent.getSource(), motionEvent.getDisplayId(), motionEvent.getFlags());
        sendActionDownAndUp(obtain, motionEvent2, i, computeClickLocation == 1);
        obtain.recycle();
    }

    private int computeClickLocation(android.graphics.Point point) {
        if (this.mState.getLastInjectedHoverEventForClick() != null) {
            int actionIndex = this.mState.getLastInjectedHoverEventForClick().getActionIndex();
            point.x = (int) this.mState.getLastInjectedHoverEventForClick().getX(actionIndex);
            point.y = (int) this.mState.getLastInjectedHoverEventForClick().getY(actionIndex);
            if (!this.mAms.accessibilityFocusOnlyInActiveWindow() || this.mState.getLastTouchedWindowId() == this.mAms.getActiveWindowId()) {
                return this.mAms.getAccessibilityFocusClickPointInScreen(point) ? 1 : 2;
            }
        }
        return this.mAms.getAccessibilityFocusClickPointInScreen(point) ? 1 : 0;
    }

    private void sendActionDownAndUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i, boolean z) {
        int pointerId = 1 << motionEvent.getPointerId(motionEvent.getActionIndex());
        motionEvent.setTargetAccessibilityFocus(z);
        sendMotionEvent(motionEvent, 0, motionEvent2, pointerId, i);
        motionEvent.setTargetAccessibilityFocus(z);
        sendMotionEvent(motionEvent, 1, motionEvent2, pointerId, i);
    }
}
