package android.view;

/* loaded from: classes4.dex */
public abstract class InputEventSender {
    private static final java.lang.String TAG = "InputEventSender";
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private android.os.Handler mHandler;
    private android.view.InputChannel mInputChannel;
    private long mSenderPtr;

    private static native void nativeDispose(long j);

    private static native long nativeInit(java.lang.ref.WeakReference<android.view.InputEventSender> weakReference, android.view.InputChannel inputChannel, android.os.MessageQueue messageQueue);

    private static native boolean nativeSendKeyEvent(long j, int i, android.view.KeyEvent keyEvent);

    private static native boolean nativeSendMotionEvent(long j, int i, android.view.MotionEvent motionEvent);

    public InputEventSender(android.view.InputChannel inputChannel, android.os.Looper looper) {
        if (inputChannel == null) {
            throw new java.lang.IllegalArgumentException("inputChannel must not be null");
        }
        if (looper == null) {
            throw new java.lang.IllegalArgumentException("looper must not be null");
        }
        this.mInputChannel = inputChannel;
        this.mHandler = new android.os.Handler(looper);
        this.mSenderPtr = nativeInit(new java.lang.ref.WeakReference(this), this.mInputChannel, looper.getQueue());
        this.mCloseGuard.open("InputEventSender.dispose");
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
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
        if (this.mSenderPtr != 0) {
            nativeDispose(this.mSenderPtr);
            this.mSenderPtr = 0L;
        }
        this.mHandler = null;
        this.mInputChannel = null;
    }

    public void onInputEventFinished(int i, boolean z) {
    }

    public void onTimelineReported(int i, long j, long j2) {
    }

    public final boolean sendInputEvent(final int i, final android.view.InputEvent inputEvent) {
        if (inputEvent == null) {
            throw new java.lang.IllegalArgumentException("event must not be null");
        }
        if (this.mSenderPtr == 0) {
            android.util.Log.w(TAG, "Attempted to send an input event but the input event sender has already been disposed.");
            return false;
        }
        if (this.mHandler.getLooper().isCurrentThread()) {
            return sendInputEventInternal(i, inputEvent);
        }
        java.util.concurrent.FutureTask futureTask = new java.util.concurrent.FutureTask(new java.util.concurrent.Callable<java.lang.Boolean>() { // from class: android.view.InputEventSender.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public java.lang.Boolean call() throws java.lang.Exception {
                return java.lang.Boolean.valueOf(android.view.InputEventSender.this.sendInputEventInternal(i, inputEvent));
            }
        });
        this.mHandler.post(futureTask);
        try {
            return ((java.lang.Boolean) futureTask.get()).booleanValue();
        } catch (java.lang.InterruptedException e) {
            throw new java.lang.IllegalStateException("Interrupted while sending " + inputEvent + ": " + e);
        } catch (java.util.concurrent.ExecutionException e2) {
            throw new java.lang.IllegalStateException("Couldn't send " + inputEvent + ": " + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendInputEventInternal(int i, android.view.InputEvent inputEvent) {
        if (inputEvent instanceof android.view.KeyEvent) {
            return nativeSendKeyEvent(this.mSenderPtr, i, (android.view.KeyEvent) inputEvent);
        }
        return nativeSendMotionEvent(this.mSenderPtr, i, (android.view.MotionEvent) inputEvent);
    }

    private void dispatchInputEventFinished(int i, boolean z) {
        onInputEventFinished(i, z);
    }

    private void dispatchTimelineReported(int i, long j, long j2) {
        onTimelineReported(i, j, j2);
    }
}
