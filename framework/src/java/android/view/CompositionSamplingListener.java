package android.view;

/* loaded from: classes4.dex */
public abstract class CompositionSamplingListener {
    private final java.util.concurrent.Executor mExecutor;
    private long mNativeListener = nativeCreate(this);

    private static native long nativeCreate(android.view.CompositionSamplingListener compositionSamplingListener);

    private static native void nativeDestroy(long j);

    private static native void nativeRegister(long j, long j2, int i, int i2, int i3, int i4);

    private static native void nativeUnregister(long j);

    public abstract void onSampleCollected(float f);

    public CompositionSamplingListener(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }

    public void destroy() {
        if (this.mNativeListener == 0) {
            return;
        }
        unregister(this);
        nativeDestroy(this.mNativeListener);
        this.mNativeListener = 0L;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            destroy();
        } finally {
            super.finalize();
        }
    }

    public static void register(android.view.CompositionSamplingListener compositionSamplingListener, int i, android.view.SurfaceControl surfaceControl, android.graphics.Rect rect) {
        if (compositionSamplingListener.mNativeListener == 0) {
            return;
        }
        com.android.internal.util.Preconditions.checkArgument(i == 0, "default display only for now");
        nativeRegister(compositionSamplingListener.mNativeListener, surfaceControl != null ? surfaceControl.mNativeObject : 0L, rect.left, rect.top, rect.right, rect.bottom);
    }

    public static void unregister(android.view.CompositionSamplingListener compositionSamplingListener) {
        if (compositionSamplingListener.mNativeListener == 0) {
            return;
        }
        nativeUnregister(compositionSamplingListener.mNativeListener);
    }

    private static void dispatchOnSampleCollected(final android.view.CompositionSamplingListener compositionSamplingListener, final float f) {
        compositionSamplingListener.mExecutor.execute(new java.lang.Runnable() { // from class: android.view.CompositionSamplingListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.view.CompositionSamplingListener.this.onSampleCollected(f);
            }
        });
    }
}
