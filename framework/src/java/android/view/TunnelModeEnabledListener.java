package android.view;

/* loaded from: classes4.dex */
public abstract class TunnelModeEnabledListener {
    private final java.util.concurrent.Executor mExecutor;
    private long mNativeListener = nativeCreate(this);

    private static native long nativeCreate(android.view.TunnelModeEnabledListener tunnelModeEnabledListener);

    private static native void nativeDestroy(long j);

    private static native void nativeRegister(long j);

    private static native void nativeUnregister(long j);

    public abstract void onTunnelModeEnabledChanged(boolean z);

    public TunnelModeEnabledListener(java.util.concurrent.Executor executor) {
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

    public static void register(android.view.TunnelModeEnabledListener tunnelModeEnabledListener) {
        if (tunnelModeEnabledListener.mNativeListener == 0) {
            return;
        }
        nativeRegister(tunnelModeEnabledListener.mNativeListener);
    }

    public static void unregister(android.view.TunnelModeEnabledListener tunnelModeEnabledListener) {
        if (tunnelModeEnabledListener.mNativeListener == 0) {
            return;
        }
        nativeUnregister(tunnelModeEnabledListener.mNativeListener);
    }

    public static void dispatchOnTunnelModeEnabledChanged(final android.view.TunnelModeEnabledListener tunnelModeEnabledListener, final boolean z) {
        tunnelModeEnabledListener.mExecutor.execute(new java.lang.Runnable() { // from class: android.view.TunnelModeEnabledListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.view.TunnelModeEnabledListener.this.onTunnelModeEnabledChanged(z);
            }
        });
    }
}
