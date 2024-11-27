package android.view;

/* loaded from: classes4.dex */
public final class InputApplicationHandle {
    public final long dispatchingTimeoutMillis;
    public final java.lang.String name;
    private long ptr;
    public final android.os.IBinder token;

    private native void nativeDispose();

    public InputApplicationHandle(android.os.IBinder iBinder, java.lang.String str, long j) {
        this.token = iBinder;
        this.name = str;
        this.dispatchingTimeoutMillis = j;
    }

    public InputApplicationHandle(android.view.InputApplicationHandle inputApplicationHandle) {
        this.token = inputApplicationHandle.token;
        this.dispatchingTimeoutMillis = inputApplicationHandle.dispatchingTimeoutMillis;
        this.name = inputApplicationHandle.name;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            nativeDispose();
        } finally {
            super.finalize();
        }
    }
}
