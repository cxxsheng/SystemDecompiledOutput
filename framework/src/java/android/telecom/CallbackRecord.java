package android.telecom;

/* loaded from: classes3.dex */
class CallbackRecord<T> {
    private final T mCallback;
    private final android.os.Handler mHandler;

    public CallbackRecord(T t, android.os.Handler handler) {
        this.mCallback = t;
        this.mHandler = handler;
    }

    public T getCallback() {
        return this.mCallback;
    }

    public android.os.Handler getHandler() {
        return this.mHandler;
    }
}
