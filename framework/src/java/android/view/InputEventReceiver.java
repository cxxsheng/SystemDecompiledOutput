package android.view;

/* loaded from: classes4.dex */
public abstract class InputEventReceiver {
    private static final java.lang.String TAG = "InputEventReceiver";
    private android.view.InputChannel mInputChannel;
    private android.os.MessageQueue mMessageQueue;
    private long mReceiverPtr;
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final android.util.SparseIntArray mSeqMap = new android.util.SparseIntArray();

    private static native boolean nativeConsumeBatchedInputEvents(long j, long j2);

    private static native void nativeDispose(long j);

    private static native java.lang.String nativeDump(long j, java.lang.String str);

    private static native void nativeFinishInputEvent(long j, int i, boolean z);

    private static native long nativeInit(java.lang.ref.WeakReference<android.view.InputEventReceiver> weakReference, android.view.InputChannel inputChannel, android.os.MessageQueue messageQueue);

    private static native boolean nativeProbablyHasInput(long j);

    private static native void nativeReportTimeline(long j, int i, long j2, long j3);

    public InputEventReceiver(android.view.InputChannel inputChannel, android.os.Looper looper) {
        if (inputChannel == null) {
            throw new java.lang.IllegalArgumentException("inputChannel must not be null");
        }
        if (looper == null) {
            throw new java.lang.IllegalArgumentException("looper must not be null");
        }
        this.mInputChannel = inputChannel;
        this.mMessageQueue = looper.getQueue();
        this.mReceiverPtr = nativeInit(new java.lang.ref.WeakReference(this), this.mInputChannel, this.mMessageQueue);
        this.mCloseGuard.open("InputEventReceiver.dispose");
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public boolean probablyHasInput() {
        if (this.mReceiverPtr == 0) {
            return false;
        }
        return nativeProbablyHasInput(this.mReceiverPtr);
    }

    public void dispose() {
        dispose(false);
    }

    private void dispose(boolean z) {
        if (this.mCloseGuard != null) {
            if (z) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (this.mReceiverPtr != 0) {
            nativeDispose(this.mReceiverPtr);
            this.mReceiverPtr = 0L;
        }
        if (this.mInputChannel != null) {
            this.mInputChannel.dispose();
            this.mInputChannel = null;
        }
        this.mMessageQueue = null;
        java.lang.ref.Reference.reachabilityFence(this);
    }

    public void onInputEvent(android.view.InputEvent inputEvent) {
        finishInputEvent(inputEvent, false);
    }

    public void onFocusEvent(boolean z) {
    }

    public void onPointerCaptureEvent(boolean z) {
    }

    public void onDragEvent(boolean z, float f, float f2) {
    }

    public void onTouchModeChanged(boolean z) {
    }

    public void onBatchedInputEventPending(int i) {
        consumeBatchedInputEvents(-1L);
    }

    public final void finishInputEvent(android.view.InputEvent inputEvent, boolean z) {
        if (inputEvent == null) {
            throw new java.lang.IllegalArgumentException("event must not be null");
        }
        if (this.mReceiverPtr == 0) {
            android.util.Log.w(TAG, "Attempted to finish an input event but the input event receiver has already been disposed.");
        } else {
            int indexOfKey = this.mSeqMap.indexOfKey(inputEvent.getSequenceNumber());
            if (indexOfKey < 0) {
                android.util.Log.w(TAG, "Attempted to finish an input event that is not in progress.");
            } else {
                int valueAt = this.mSeqMap.valueAt(indexOfKey);
                this.mSeqMap.removeAt(indexOfKey);
                nativeFinishInputEvent(this.mReceiverPtr, valueAt, z);
            }
        }
        inputEvent.recycleIfNeededAfterDispatch();
    }

    public final void reportTimeline(int i, long j, long j2) {
        android.os.Trace.traceBegin(4L, "reportTimeline");
        nativeReportTimeline(this.mReceiverPtr, i, j, j2);
        android.os.Trace.traceEnd(4L);
    }

    public final boolean consumeBatchedInputEvents(long j) {
        if (this.mReceiverPtr == 0) {
            android.util.Log.w(TAG, "Attempted to consume batched input events but the input event receiver has already been disposed.");
            return false;
        }
        return nativeConsumeBatchedInputEvents(this.mReceiverPtr, j);
    }

    public android.os.IBinder getToken() {
        if (this.mInputChannel == null) {
            return null;
        }
        return this.mInputChannel.getToken();
    }

    private java.lang.String getShortDescription(android.view.InputEvent inputEvent) {
        if (inputEvent instanceof android.view.MotionEvent) {
            android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
            return "MotionEvent " + android.view.MotionEvent.actionToString(motionEvent.getAction()) + " deviceId=" + motionEvent.getDeviceId() + " source=0x" + java.lang.Integer.toHexString(motionEvent.getSource()) + " historySize=" + motionEvent.getHistorySize();
        }
        if (inputEvent instanceof android.view.KeyEvent) {
            android.view.KeyEvent keyEvent = (android.view.KeyEvent) inputEvent;
            return "KeyEvent " + android.view.KeyEvent.actionToString(keyEvent.getAction()) + " deviceId=" + keyEvent.getDeviceId();
        }
        android.util.Log.wtf(TAG, "Illegal InputEvent type: " + inputEvent);
        return "InputEvent";
    }

    private void dispatchInputEvent(int i, android.view.InputEvent inputEvent) {
        android.os.Trace.traceBegin(4L, "dispatchInputEvent " + getShortDescription(inputEvent));
        this.mSeqMap.put(inputEvent.getSequenceNumber(), i);
        onInputEvent(inputEvent);
        android.os.Trace.traceEnd(4L);
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + getClass().getName());
        printWriter.println(str + " mInputChannel: " + this.mInputChannel);
        printWriter.println(str + " mSeqMap: " + this.mSeqMap);
        printWriter.println(str + " mReceiverPtr:\n" + nativeDump(this.mReceiverPtr, str + "  "));
    }
}
