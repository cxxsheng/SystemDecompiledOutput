package android.view;

/* loaded from: classes4.dex */
public final class InputQueue {
    private final android.util.LongSparseArray<android.view.InputQueue.ActiveInputEvent> mActiveEventArray = new android.util.LongSparseArray<>(20);
    private final android.util.Pools.Pool<android.view.InputQueue.ActiveInputEvent> mActiveInputEventPool = new android.util.Pools.SimplePool(20);
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private long mPtr = nativeInit(new java.lang.ref.WeakReference(this), android.os.Looper.myQueue());

    public interface Callback {
        void onInputQueueCreated(android.view.InputQueue inputQueue);

        void onInputQueueDestroyed(android.view.InputQueue inputQueue);
    }

    public interface FinishedInputEventCallback {
        void onFinishedInputEvent(java.lang.Object obj, boolean z);
    }

    private static native void nativeDispose(long j);

    private static native long nativeInit(java.lang.ref.WeakReference<android.view.InputQueue> weakReference, android.os.MessageQueue messageQueue);

    private static native long nativeSendKeyEvent(long j, android.view.KeyEvent keyEvent, boolean z);

    private static native long nativeSendMotionEvent(long j, android.view.MotionEvent motionEvent);

    public InputQueue() {
        this.mCloseGuard.open("InputQueue.dispose");
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

    public void dispose(boolean z) {
        if (this.mCloseGuard != null) {
            if (z) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (this.mPtr != 0) {
            nativeDispose(this.mPtr);
            this.mPtr = 0L;
        }
    }

    public long getNativePtr() {
        return this.mPtr;
    }

    public void sendInputEvent(android.view.InputEvent inputEvent, java.lang.Object obj, boolean z, android.view.InputQueue.FinishedInputEventCallback finishedInputEventCallback) {
        long nativeSendMotionEvent;
        android.view.InputQueue.ActiveInputEvent obtainActiveInputEvent = obtainActiveInputEvent(obj, finishedInputEventCallback);
        if (inputEvent instanceof android.view.KeyEvent) {
            nativeSendMotionEvent = nativeSendKeyEvent(this.mPtr, (android.view.KeyEvent) inputEvent, z);
        } else {
            nativeSendMotionEvent = nativeSendMotionEvent(this.mPtr, (android.view.MotionEvent) inputEvent);
        }
        this.mActiveEventArray.put(nativeSendMotionEvent, obtainActiveInputEvent);
    }

    private void finishInputEvent(long j, boolean z) {
        int indexOfKey = this.mActiveEventArray.indexOfKey(j);
        if (indexOfKey >= 0) {
            android.view.InputQueue.ActiveInputEvent valueAt = this.mActiveEventArray.valueAt(indexOfKey);
            this.mActiveEventArray.removeAt(indexOfKey);
            valueAt.mCallback.onFinishedInputEvent(valueAt.mToken, z);
            recycleActiveInputEvent(valueAt);
        }
    }

    private android.view.InputQueue.ActiveInputEvent obtainActiveInputEvent(java.lang.Object obj, android.view.InputQueue.FinishedInputEventCallback finishedInputEventCallback) {
        android.view.InputQueue.ActiveInputEvent acquire = this.mActiveInputEventPool.acquire();
        if (acquire == null) {
            acquire = new android.view.InputQueue.ActiveInputEvent();
        }
        acquire.mToken = obj;
        acquire.mCallback = finishedInputEventCallback;
        return acquire;
    }

    private void recycleActiveInputEvent(android.view.InputQueue.ActiveInputEvent activeInputEvent) {
        activeInputEvent.recycle();
        this.mActiveInputEventPool.release(activeInputEvent);
    }

    private final class ActiveInputEvent {
        public android.view.InputQueue.FinishedInputEventCallback mCallback;
        public java.lang.Object mToken;

        private ActiveInputEvent() {
        }

        public void recycle() {
            this.mToken = null;
            this.mCallback = null;
        }
    }
}
