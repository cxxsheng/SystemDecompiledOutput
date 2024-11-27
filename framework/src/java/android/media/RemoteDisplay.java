package android.media;

/* loaded from: classes2.dex */
public final class RemoteDisplay {
    public static final int DISPLAY_ERROR_CONNECTION_DROPPED = 2;
    public static final int DISPLAY_ERROR_UNKOWN = 1;
    public static final int DISPLAY_FLAG_SECURE = 1;
    private final dalvik.system.CloseGuard mGuard = dalvik.system.CloseGuard.get();
    private final android.os.Handler mHandler;
    private final android.media.RemoteDisplay.Listener mListener;
    private final java.lang.String mOpPackageName;
    private long mPtr;

    public interface Listener {
        void onDisplayConnected(android.view.Surface surface, int i, int i2, int i3, int i4);

        void onDisplayDisconnected();

        void onDisplayError(int i);
    }

    private native void nativeDispose(long j);

    private native long nativeListen(java.lang.String str, java.lang.String str2);

    private native void nativePause(long j);

    private native void nativeResume(long j);

    private RemoteDisplay(android.media.RemoteDisplay.Listener listener, android.os.Handler handler, java.lang.String str) {
        this.mListener = listener;
        this.mHandler = handler;
        this.mOpPackageName = str;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public static android.media.RemoteDisplay listen(java.lang.String str, android.media.RemoteDisplay.Listener listener, android.os.Handler handler, java.lang.String str2) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("iface must not be null");
        }
        if (listener == null) {
            throw new java.lang.IllegalArgumentException("listener must not be null");
        }
        if (handler == null) {
            throw new java.lang.IllegalArgumentException("handler must not be null");
        }
        android.media.RemoteDisplay remoteDisplay = new android.media.RemoteDisplay(listener, handler, str2);
        remoteDisplay.startListening(str);
        return remoteDisplay;
    }

    public void dispose() {
        dispose(false);
    }

    public void pause() {
        nativePause(this.mPtr);
    }

    public void resume() {
        nativeResume(this.mPtr);
    }

    private void dispose(boolean z) {
        if (this.mPtr != 0) {
            if (this.mGuard != null) {
                if (z) {
                    this.mGuard.warnIfOpen();
                } else {
                    this.mGuard.close();
                }
            }
            nativeDispose(this.mPtr);
            this.mPtr = 0L;
        }
    }

    private void startListening(java.lang.String str) {
        this.mPtr = nativeListen(str, this.mOpPackageName);
        if (this.mPtr == 0) {
            throw new java.lang.IllegalStateException("Could not start listening for remote display connection on \"" + str + "\"");
        }
        this.mGuard.open("dispose");
    }

    private void notifyDisplayConnected(final android.view.Surface surface, final int i, final int i2, final int i3, final int i4) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.media.RemoteDisplay.1
            @Override // java.lang.Runnable
            public void run() {
                android.media.RemoteDisplay.this.mListener.onDisplayConnected(surface, i, i2, i3, i4);
            }
        });
    }

    private void notifyDisplayDisconnected() {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.media.RemoteDisplay.2
            @Override // java.lang.Runnable
            public void run() {
                android.media.RemoteDisplay.this.mListener.onDisplayDisconnected();
            }
        });
    }

    private void notifyDisplayError(final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.media.RemoteDisplay.3
            @Override // java.lang.Runnable
            public void run() {
                android.media.RemoteDisplay.this.mListener.onDisplayError(i);
            }
        });
    }
}
