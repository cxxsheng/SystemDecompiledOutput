package android.os;

/* loaded from: classes3.dex */
public class HandlerExecutor implements java.util.concurrent.Executor {
    private final android.os.Handler mHandler;

    public HandlerExecutor(android.os.Handler handler) {
        this.mHandler = (android.os.Handler) com.android.internal.util.Preconditions.checkNotNull(handler);
    }

    @Override // java.util.concurrent.Executor
    public void execute(java.lang.Runnable runnable) {
        if (!this.mHandler.post(runnable)) {
            throw new java.util.concurrent.RejectedExecutionException(this.mHandler + " is shutting down");
        }
    }
}
