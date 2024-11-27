package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
class UptimeTimer {
    private final android.os.Handler mHandler;
    private final android.os.HandlerThread mHandlerThread;

    interface Task {
        void cancel();
    }

    UptimeTimer(java.lang.String str) {
        this.mHandlerThread = new android.os.HandlerThread(str);
        this.mHandlerThread.start();
        this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper());
    }

    com.android.server.soundtrigger_middleware.UptimeTimer.Task createTask(@android.annotation.NonNull java.lang.Runnable runnable, long j) {
        java.lang.Object obj = new java.lang.Object();
        com.android.server.soundtrigger_middleware.UptimeTimer.TaskImpl taskImpl = new com.android.server.soundtrigger_middleware.UptimeTimer.TaskImpl(this.mHandler, obj);
        this.mHandler.postDelayed(runnable, obj, j);
        return taskImpl;
    }

    void quit() {
        this.mHandlerThread.quitSafely();
    }

    private static class TaskImpl implements com.android.server.soundtrigger_middleware.UptimeTimer.Task {
        private final android.os.Handler mHandler;
        private final java.lang.Object mToken;

        public TaskImpl(android.os.Handler handler, java.lang.Object obj) {
            this.mHandler = handler;
            this.mToken = obj;
        }

        @Override // com.android.server.soundtrigger_middleware.UptimeTimer.Task
        public void cancel() {
            this.mHandler.removeCallbacksAndMessages(this.mToken);
        }
    }
}
