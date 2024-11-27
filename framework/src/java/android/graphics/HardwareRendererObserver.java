package android.graphics;

/* loaded from: classes.dex */
public final class HardwareRendererObserver {
    private final long[] mFrameMetrics;
    private final android.os.Handler mHandler;
    private final android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener mListener;
    private com.android.internal.util.VirtualRefBasePtr mNativePtr;

    public interface OnFrameMetricsAvailableListener {
        void onFrameMetricsAvailable(int i);
    }

    private static native long nCreateObserver(java.lang.ref.WeakReference<android.graphics.HardwareRendererObserver> weakReference, boolean z);

    private static native int nGetNextBuffer(long j, long[] jArr);

    public HardwareRendererObserver(android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener, long[] jArr, android.os.Handler handler, boolean z) {
        if (handler == null || handler.getLooper() == null) {
            throw new java.lang.NullPointerException("handler and its looper cannot be null");
        }
        if (handler.getLooper().getQueue() == null) {
            throw new java.lang.IllegalStateException("invalid looper, null message queue\n");
        }
        this.mFrameMetrics = jArr;
        this.mHandler = handler;
        this.mListener = onFrameMetricsAvailableListener;
        this.mNativePtr = new com.android.internal.util.VirtualRefBasePtr(nCreateObserver(new java.lang.ref.WeakReference(this), z));
    }

    long getNativeInstance() {
        return this.mNativePtr.get();
    }

    private void notifyDataAvailable() {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.graphics.HardwareRendererObserver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.graphics.HardwareRendererObserver.this.lambda$notifyDataAvailable$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyDataAvailable$0() {
        boolean z = true;
        while (z) {
            int nGetNextBuffer = nGetNextBuffer(this.mNativePtr.get(), this.mFrameMetrics);
            if (nGetNextBuffer >= 0) {
                this.mListener.onFrameMetricsAvailable(nGetNextBuffer);
            } else {
                z = false;
            }
        }
    }

    static boolean invokeDataAvailable(java.lang.ref.WeakReference<android.graphics.HardwareRendererObserver> weakReference) {
        android.graphics.HardwareRendererObserver hardwareRendererObserver = weakReference.get();
        if (hardwareRendererObserver != null) {
            hardwareRendererObserver.notifyDataAvailable();
            return true;
        }
        return false;
    }
}
