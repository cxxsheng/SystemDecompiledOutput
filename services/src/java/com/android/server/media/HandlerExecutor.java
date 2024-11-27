package com.android.server.media;

/* loaded from: classes2.dex */
public class HandlerExecutor implements java.util.concurrent.Executor {
    private final android.os.Handler mHandler;

    public HandlerExecutor(@android.annotation.NonNull android.os.Handler handler) {
        java.util.Objects.requireNonNull(handler);
        this.mHandler = handler;
    }

    @Override // java.util.concurrent.Executor
    public void execute(java.lang.Runnable runnable) {
        if (!this.mHandler.post(runnable)) {
            throw new java.util.concurrent.RejectedExecutionException(this.mHandler + " is shutting down");
        }
    }
}
