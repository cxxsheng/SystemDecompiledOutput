package com.android.internal.telephony.util;

/* loaded from: classes5.dex */
public class HandlerExecutor implements java.util.concurrent.Executor {
    private final android.os.Handler mHandler;

    public HandlerExecutor(android.os.Handler handler) {
        if (handler == null) {
            throw new java.lang.NullPointerException();
        }
        this.mHandler = handler;
    }

    @Override // java.util.concurrent.Executor
    public void execute(java.lang.Runnable runnable) {
        if (!this.mHandler.post(runnable)) {
            throw new java.util.concurrent.RejectedExecutionException(this.mHandler + " is shutting down");
        }
    }
}
