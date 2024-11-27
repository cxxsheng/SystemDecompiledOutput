package android.view;

/* loaded from: classes4.dex */
public class BatchedInputEventReceiver extends android.view.InputEventReceiver {
    private final android.view.BatchedInputEventReceiver.BatchedInputRunnable mBatchedInputRunnable;
    private boolean mBatchedInputScheduled;
    private boolean mBatchingEnabled;
    private android.view.Choreographer mChoreographer;
    private final java.lang.Runnable mConsumeBatchedInputEvents;
    private final android.os.Handler mHandler;

    public BatchedInputEventReceiver(android.view.InputChannel inputChannel, android.os.Looper looper, android.view.Choreographer choreographer) {
        super(inputChannel, looper);
        this.mConsumeBatchedInputEvents = new java.lang.Runnable() { // from class: android.view.BatchedInputEventReceiver.1
            @Override // java.lang.Runnable
            public void run() {
                android.view.BatchedInputEventReceiver.this.consumeBatchedInputEvents(-1L);
            }
        };
        this.mBatchedInputRunnable = new android.view.BatchedInputEventReceiver.BatchedInputRunnable();
        this.mChoreographer = choreographer;
        this.mBatchingEnabled = true;
        traceBoolVariable("mBatchingEnabled", this.mBatchingEnabled);
        traceBoolVariable("mBatchedInputScheduled", this.mBatchedInputScheduled);
        this.mHandler = new android.os.Handler(looper);
    }

    @Override // android.view.InputEventReceiver
    public void onBatchedInputEventPending(int i) {
        if (this.mBatchingEnabled) {
            scheduleBatchedInput();
        } else {
            consumeBatchedInputEvents(-1L);
        }
    }

    @Override // android.view.InputEventReceiver
    public void dispose() {
        unscheduleBatchedInput();
        consumeBatchedInputEvents(-1L);
        super.dispose();
    }

    public void setBatchingEnabled(boolean z) {
        if (this.mBatchingEnabled == z) {
            return;
        }
        this.mBatchingEnabled = z;
        traceBoolVariable("mBatchingEnabled", this.mBatchingEnabled);
        this.mHandler.removeCallbacks(this.mConsumeBatchedInputEvents);
        if (!z) {
            unscheduleBatchedInput();
            this.mHandler.post(this.mConsumeBatchedInputEvents);
        }
    }

    protected void doConsumeBatchedInput(long j) {
        if (this.mBatchedInputScheduled) {
            this.mBatchedInputScheduled = false;
            traceBoolVariable("mBatchedInputScheduled", this.mBatchedInputScheduled);
            if (consumeBatchedInputEvents(j) && j != -1) {
                scheduleBatchedInput();
            }
        }
    }

    private void scheduleBatchedInput() {
        if (!this.mBatchedInputScheduled) {
            this.mBatchedInputScheduled = true;
            traceBoolVariable("mBatchedInputScheduled", this.mBatchedInputScheduled);
            this.mChoreographer.postCallback(0, this.mBatchedInputRunnable, null);
        }
    }

    private void unscheduleBatchedInput() {
        if (this.mBatchedInputScheduled) {
            this.mBatchedInputScheduled = false;
            traceBoolVariable("mBatchedInputScheduled", this.mBatchedInputScheduled);
            this.mChoreographer.removeCallbacks(0, this.mBatchedInputRunnable, null);
        }
    }

    private void traceBoolVariable(java.lang.String str, boolean z) {
        android.os.Trace.traceCounter(4L, str, z ? 1 : 0);
    }

    private final class BatchedInputRunnable implements java.lang.Runnable {
        private BatchedInputRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.BatchedInputEventReceiver.this.doConsumeBatchedInput(android.view.BatchedInputEventReceiver.this.mChoreographer.getFrameTimeNanos());
        }
    }

    public static class SimpleBatchedInputEventReceiver extends android.view.BatchedInputEventReceiver {
        protected android.view.BatchedInputEventReceiver.SimpleBatchedInputEventReceiver.InputEventListener mListener;

        public interface InputEventListener {
            boolean onInputEvent(android.view.InputEvent inputEvent);
        }

        public SimpleBatchedInputEventReceiver(android.view.InputChannel inputChannel, android.os.Looper looper, android.view.Choreographer choreographer, android.view.BatchedInputEventReceiver.SimpleBatchedInputEventReceiver.InputEventListener inputEventListener) {
            super(inputChannel, looper, choreographer);
            this.mListener = inputEventListener;
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(android.view.InputEvent inputEvent) {
            try {
                finishInputEvent(inputEvent, this.mListener.onInputEvent(inputEvent));
            } catch (java.lang.Throwable th) {
                finishInputEvent(inputEvent, false);
                throw th;
            }
        }
    }
}
