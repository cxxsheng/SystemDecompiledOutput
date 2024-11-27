package com.android.server.accessibility;

/* loaded from: classes.dex */
public class MotionEventInjector extends com.android.server.accessibility.BaseEventStreamTransformation implements android.os.Handler.Callback {
    private static final int EVENT_BUTTON_STATE = 0;
    private static final int EVENT_EDGE_FLAGS = 0;
    private static final int EVENT_FLAGS = 0;
    private static final int EVENT_META_STATE = 0;
    private static final int EVENT_SOURCE = 4098;
    private static final float EVENT_X_PRECISION = 1.0f;
    private static final float EVENT_Y_PRECISION = 1.0f;
    private static final java.lang.String LOG_TAG = "MotionEventInjector";
    private static final int MESSAGE_INJECT_EVENTS = 2;
    private static final int MESSAGE_SEND_MOTION_EVENT = 1;
    private static android.view.MotionEvent.PointerCoords[] sPointerCoords;
    private static android.view.MotionEvent.PointerProperties[] sPointerProps;
    private long mDownTime;
    private final android.os.Handler mHandler;
    private long mLastScheduledEventTime;
    private android.accessibilityservice.GestureDescription.TouchPoint[] mLastTouchPoints;
    private int mNumLastTouchPoints;
    private android.accessibilityservice.IAccessibilityServiceClient mServiceInterfaceForCurrentGesture;
    private final com.android.server.accessibility.AccessibilityTraceManager mTrace;
    private final android.util.SparseArray<java.lang.Boolean> mOpenGesturesInProgress = new android.util.SparseArray<>();
    private android.util.IntArray mSequencesInProgress = new android.util.IntArray(5);
    private boolean mIsDestroyed = false;
    private android.util.SparseIntArray mStrokeIdToPointerId = new android.util.SparseIntArray(5);

    public MotionEventInjector(android.os.Looper looper, com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager) {
        this.mHandler = new android.os.Handler(looper, this);
        this.mTrace = accessibilityTraceManager;
    }

    public MotionEventInjector(android.os.Handler handler, com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager) {
        this.mHandler = handler;
        this.mTrace = accessibilityTraceManager;
    }

    public void injectEvents(java.util.List<android.accessibilityservice.GestureDescription.GestureStep> list, android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i, int i2) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = list;
        obtain.arg2 = iAccessibilityServiceClient;
        obtain.argi1 = i;
        obtain.argi2 = i2;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, obtain));
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mTrace.isA11yTracingEnabledForTypes(12288L)) {
            this.mTrace.logTrace("MotionEventInjector.onMotionEvent", 12288L, "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i);
        }
        if (motionEvent.isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer1) && motionEvent.getActionMasked() == 7 && this.mOpenGesturesInProgress.get(4098, false).booleanValue()) {
            return;
        }
        cancelAnyPendingInjectedEvents();
        sendMotionEventToNext(motionEvent, motionEvent2, i | 131072);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void clearEvents(int i) {
        if (!this.mHandler.hasMessages(1)) {
            this.mOpenGesturesInProgress.put(i, false);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onDestroy() {
        cancelAnyPendingInjectedEvents();
        this.mIsDestroyed = true;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        if (message.what == 2) {
            com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
            injectEventsMainThread((java.util.List) someArgs.arg1, (android.accessibilityservice.IAccessibilityServiceClient) someArgs.arg2, someArgs.argi1, someArgs.argi2);
            someArgs.recycle();
            return true;
        }
        if (message.what != 1) {
            android.util.Slog.e(LOG_TAG, "Unknown message: " + message.what);
            return false;
        }
        android.view.MotionEvent motionEvent = (android.view.MotionEvent) message.obj;
        sendMotionEventToNext(motionEvent, motionEvent, 1073872896);
        if (message.arg1 != 0) {
            notifyService(this.mServiceInterfaceForCurrentGesture, this.mSequencesInProgress.get(0), true);
            this.mSequencesInProgress.remove(0);
        }
        return true;
    }

    private void injectEventsMainThread(java.util.List<android.accessibilityservice.GestureDescription.GestureStep> list, android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i, int i2) {
        if (this.mIsDestroyed) {
            try {
                iAccessibilityServiceClient.onPerformGestureResult(i, false);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error sending status with mIsDestroyed to " + iAccessibilityServiceClient, e);
                return;
            }
        }
        if (getNext() == null) {
            notifyService(iAccessibilityServiceClient, i, false);
            return;
        }
        boolean newGestureTriesToContinueOldOne = newGestureTriesToContinueOldOne(list);
        if (newGestureTriesToContinueOldOne && (iAccessibilityServiceClient != this.mServiceInterfaceForCurrentGesture || !prepareToContinueOldGesture(list))) {
            cancelAnyPendingInjectedEvents();
            notifyService(iAccessibilityServiceClient, i, false);
            return;
        }
        if (!newGestureTriesToContinueOldOne) {
            cancelAnyPendingInjectedEvents();
            cancelAnyGestureInProgress(4098);
        }
        this.mServiceInterfaceForCurrentGesture = iAccessibilityServiceClient;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        java.util.List<android.view.MotionEvent> motionEventsFromGestureSteps = getMotionEventsFromGestureSteps(list, this.mSequencesInProgress.size() == 0 ? uptimeMillis : this.mLastScheduledEventTime);
        if (motionEventsFromGestureSteps.isEmpty()) {
            notifyService(iAccessibilityServiceClient, i, false);
            return;
        }
        this.mSequencesInProgress.add(i);
        int i3 = 0;
        while (i3 < motionEventsFromGestureSteps.size()) {
            android.view.MotionEvent motionEvent = motionEventsFromGestureSteps.get(i3);
            motionEvent.setDisplayId(i2);
            android.os.Message obtainMessage = this.mHandler.obtainMessage(1, i3 == motionEventsFromGestureSteps.size() - 1 ? 1 : 0, 0, motionEvent);
            this.mLastScheduledEventTime = motionEvent.getEventTime();
            this.mHandler.sendMessageDelayed(obtainMessage, java.lang.Math.max(0L, motionEvent.getEventTime() - uptimeMillis));
            i3++;
        }
    }

    private boolean newGestureTriesToContinueOldOne(java.util.List<android.accessibilityservice.GestureDescription.GestureStep> list) {
        if (list.isEmpty()) {
            return false;
        }
        android.accessibilityservice.GestureDescription.GestureStep gestureStep = list.get(0);
        for (int i = 0; i < gestureStep.numTouchPoints; i++) {
            if (!gestureStep.touchPoints[i].mIsStartOfPath) {
                return true;
            }
        }
        return false;
    }

    private boolean prepareToContinueOldGesture(java.util.List<android.accessibilityservice.GestureDescription.GestureStep> list) {
        if (list.isEmpty() || this.mLastTouchPoints == null || this.mNumLastTouchPoints == 0) {
            return false;
        }
        android.accessibilityservice.GestureDescription.GestureStep gestureStep = list.get(0);
        int i = 0;
        for (int i2 = 0; i2 < gestureStep.numTouchPoints; i2++) {
            android.accessibilityservice.GestureDescription.TouchPoint touchPoint = gestureStep.touchPoints[i2];
            if (!touchPoint.mIsStartOfPath) {
                int i3 = this.mStrokeIdToPointerId.get(touchPoint.mContinuedStrokeId, -1);
                if (i3 == -1) {
                    android.util.Slog.w(LOG_TAG, "Can't continue gesture due to unknown continued stroke id in " + touchPoint);
                    return false;
                }
                this.mStrokeIdToPointerId.put(touchPoint.mStrokeId, i3);
                int findPointByStrokeId = findPointByStrokeId(this.mLastTouchPoints, this.mNumLastTouchPoints, touchPoint.mContinuedStrokeId);
                if (findPointByStrokeId < 0) {
                    android.util.Slog.w(LOG_TAG, "Can't continue gesture due continued gesture id of " + touchPoint + " not matching any previous strokes in " + java.util.Arrays.asList(this.mLastTouchPoints));
                    return false;
                }
                if (this.mLastTouchPoints[findPointByStrokeId].mIsEndOfPath || this.mLastTouchPoints[findPointByStrokeId].mX != touchPoint.mX || this.mLastTouchPoints[findPointByStrokeId].mY != touchPoint.mY) {
                    android.util.Slog.w(LOG_TAG, "Can't continue gesture due to points mismatch between " + this.mLastTouchPoints[findPointByStrokeId] + " and " + touchPoint);
                    return false;
                }
                this.mLastTouchPoints[findPointByStrokeId].mStrokeId = touchPoint.mStrokeId;
            }
            i++;
        }
        for (int i4 = 0; i4 < this.mNumLastTouchPoints; i4++) {
            if (!this.mLastTouchPoints[i4].mIsEndOfPath) {
                i--;
            }
        }
        return i == 0;
    }

    private void sendMotionEventToNext(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (getNext() != null) {
            super.onMotionEvent(motionEvent, motionEvent2, i);
            if (motionEvent.getActionMasked() == 0) {
                this.mOpenGesturesInProgress.put(motionEvent.getSource(), true);
            }
            if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
                this.mOpenGesturesInProgress.put(motionEvent.getSource(), false);
            }
        }
    }

    private void cancelAnyGestureInProgress(int i) {
        if (getNext() != null && this.mOpenGesturesInProgress.get(i, false).booleanValue()) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            android.view.MotionEvent obtainMotionEvent = obtainMotionEvent(uptimeMillis, uptimeMillis, 3, getLastTouchPoints(), 1);
            sendMotionEventToNext(obtainMotionEvent, obtainMotionEvent, 1073872896);
            this.mOpenGesturesInProgress.put(i, false);
        }
    }

    private void cancelAnyPendingInjectedEvents() {
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
            cancelAnyGestureInProgress(4098);
            for (int size = this.mSequencesInProgress.size() - 1; size >= 0; size--) {
                notifyService(this.mServiceInterfaceForCurrentGesture, this.mSequencesInProgress.get(size), false);
                this.mSequencesInProgress.remove(size);
            }
        } else if (this.mNumLastTouchPoints != 0) {
            cancelAnyGestureInProgress(4098);
        }
        this.mNumLastTouchPoints = 0;
        this.mStrokeIdToPointerId.clear();
    }

    private void notifyService(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i, boolean z) {
        try {
            iAccessibilityServiceClient.onPerformGestureResult(i, z);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(LOG_TAG, "Error sending motion event injection status to " + this.mServiceInterfaceForCurrentGesture, e);
        }
    }

    private java.util.List<android.view.MotionEvent> getMotionEventsFromGestureSteps(java.util.List<android.accessibilityservice.GestureDescription.GestureStep> list, long j) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.accessibilityservice.GestureDescription.TouchPoint[] lastTouchPoints = getLastTouchPoints();
        for (int i = 0; i < list.size(); i++) {
            android.accessibilityservice.GestureDescription.GestureStep gestureStep = list.get(i);
            int i2 = gestureStep.numTouchPoints;
            if (i2 > lastTouchPoints.length) {
                this.mNumLastTouchPoints = 0;
                arrayList.clear();
                return arrayList;
            }
            appendMoveEventIfNeeded(arrayList, gestureStep.touchPoints, i2, j + gestureStep.timeSinceGestureStart);
            appendUpEvents(arrayList, gestureStep.touchPoints, i2, j + gestureStep.timeSinceGestureStart);
            appendDownEvents(arrayList, gestureStep.touchPoints, i2, j + gestureStep.timeSinceGestureStart);
        }
        return arrayList;
    }

    private android.accessibilityservice.GestureDescription.TouchPoint[] getLastTouchPoints() {
        if (this.mLastTouchPoints == null) {
            int maxStrokeCount = android.accessibilityservice.GestureDescription.getMaxStrokeCount();
            this.mLastTouchPoints = new android.accessibilityservice.GestureDescription.TouchPoint[maxStrokeCount];
            for (int i = 0; i < maxStrokeCount; i++) {
                this.mLastTouchPoints[i] = new android.accessibilityservice.GestureDescription.TouchPoint();
            }
        }
        return this.mLastTouchPoints;
    }

    private void appendMoveEventIfNeeded(java.util.List<android.view.MotionEvent> list, android.accessibilityservice.GestureDescription.TouchPoint[] touchPointArr, int i, long j) {
        android.accessibilityservice.GestureDescription.TouchPoint[] lastTouchPoints = getLastTouchPoints();
        boolean z = false;
        for (int i2 = 0; i2 < i; i2++) {
            int findPointByStrokeId = findPointByStrokeId(lastTouchPoints, this.mNumLastTouchPoints, touchPointArr[i2].mStrokeId);
            if (findPointByStrokeId >= 0) {
                z |= (lastTouchPoints[findPointByStrokeId].mX == touchPointArr[i2].mX && lastTouchPoints[findPointByStrokeId].mY == touchPointArr[i2].mY) ? false : true;
                lastTouchPoints[findPointByStrokeId].copyFrom(touchPointArr[i2]);
            }
        }
        if (z) {
            list.add(obtainMotionEvent(this.mDownTime, j, 2, lastTouchPoints, this.mNumLastTouchPoints));
        }
    }

    private void appendUpEvents(java.util.List<android.view.MotionEvent> list, android.accessibilityservice.GestureDescription.TouchPoint[] touchPointArr, int i, long j) {
        int findPointByStrokeId;
        android.accessibilityservice.GestureDescription.TouchPoint[] lastTouchPoints = getLastTouchPoints();
        for (int i2 = 0; i2 < i; i2++) {
            if (touchPointArr[i2].mIsEndOfPath && (findPointByStrokeId = findPointByStrokeId(lastTouchPoints, this.mNumLastTouchPoints, touchPointArr[i2].mStrokeId)) >= 0) {
                list.add(obtainMotionEvent(this.mDownTime, j, (this.mNumLastTouchPoints == 1 ? 1 : 6) | (findPointByStrokeId << 8), lastTouchPoints, this.mNumLastTouchPoints));
                while (findPointByStrokeId < this.mNumLastTouchPoints - 1) {
                    android.accessibilityservice.GestureDescription.TouchPoint touchPoint = lastTouchPoints[findPointByStrokeId];
                    findPointByStrokeId++;
                    touchPoint.copyFrom(this.mLastTouchPoints[findPointByStrokeId]);
                }
                this.mNumLastTouchPoints--;
                if (this.mNumLastTouchPoints == 0) {
                    this.mStrokeIdToPointerId.clear();
                }
            }
        }
    }

    private void appendDownEvents(java.util.List<android.view.MotionEvent> list, android.accessibilityservice.GestureDescription.TouchPoint[] touchPointArr, int i, long j) {
        int i2;
        android.accessibilityservice.GestureDescription.TouchPoint[] lastTouchPoints = getLastTouchPoints();
        for (int i3 = 0; i3 < i; i3++) {
            if (touchPointArr[i3].mIsStartOfPath) {
                int i4 = this.mNumLastTouchPoints;
                this.mNumLastTouchPoints = i4 + 1;
                lastTouchPoints[i4].copyFrom(touchPointArr[i3]);
                if (this.mNumLastTouchPoints == 1) {
                    i2 = 0;
                } else {
                    i2 = 5;
                }
                if (i2 == 0) {
                    this.mDownTime = j;
                }
                list.add(obtainMotionEvent(this.mDownTime, j, i2 | (i3 << 8), lastTouchPoints, this.mNumLastTouchPoints));
            }
        }
    }

    private android.view.MotionEvent obtainMotionEvent(long j, long j2, int i, android.accessibilityservice.GestureDescription.TouchPoint[] touchPointArr, int i2) {
        if (sPointerCoords == null || sPointerCoords.length < i2) {
            sPointerCoords = new android.view.MotionEvent.PointerCoords[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                sPointerCoords[i3] = new android.view.MotionEvent.PointerCoords();
            }
        }
        if (sPointerProps == null || sPointerProps.length < i2) {
            sPointerProps = new android.view.MotionEvent.PointerProperties[i2];
            for (int i4 = 0; i4 < i2; i4++) {
                sPointerProps[i4] = new android.view.MotionEvent.PointerProperties();
            }
        }
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = this.mStrokeIdToPointerId.get(touchPointArr[i5].mStrokeId, -1);
            if (i6 == -1) {
                i6 = getUnusedPointerId();
                this.mStrokeIdToPointerId.put(touchPointArr[i5].mStrokeId, i6);
            }
            sPointerProps[i5].id = i6;
            sPointerProps[i5].toolType = 0;
            sPointerCoords[i5].clear();
            sPointerCoords[i5].pressure = 1.0f;
            sPointerCoords[i5].size = 1.0f;
            sPointerCoords[i5].x = touchPointArr[i5].mX;
            sPointerCoords[i5].y = touchPointArr[i5].mY;
        }
        return android.view.MotionEvent.obtain(j, j2, i, i2, sPointerProps, sPointerCoords, 0, 0, 1.0f, 1.0f, -1, 0, 4098, 0);
    }

    private static int findPointByStrokeId(android.accessibilityservice.GestureDescription.TouchPoint[] touchPointArr, int i, int i2) {
        for (int i3 = 0; i3 < i; i3++) {
            if (touchPointArr[i3].mStrokeId == i2) {
                return i3;
            }
        }
        return -1;
    }

    private int getUnusedPointerId() {
        int i = 0;
        while (this.mStrokeIdToPointerId.indexOfValue(i) >= 0) {
            i++;
            if (i >= 10) {
                return 10;
            }
        }
        return i;
    }
}
