package com.android.server.tare;

/* loaded from: classes2.dex */
final class TareHandlerThread extends android.os.HandlerThread {
    private static android.os.Handler sHandler;
    private static java.util.concurrent.Executor sHandlerExecutor;
    private static com.android.server.tare.TareHandlerThread sInstance;

    private TareHandlerThread() {
        super("tare");
    }

    private static void ensureThreadLocked() {
        if (sInstance == null) {
            sInstance = new com.android.server.tare.TareHandlerThread();
            sInstance.start();
            sInstance.getLooper().setTraceTag(524288L);
            sHandler = new android.os.Handler(sInstance.getLooper());
            sHandlerExecutor = new android.os.HandlerExecutor(sHandler);
        }
    }

    static com.android.server.tare.TareHandlerThread get() {
        synchronized (com.android.server.tare.TareHandlerThread.class) {
            ensureThreadLocked();
        }
        return sInstance;
    }

    public static java.util.concurrent.Executor getExecutor() {
        java.util.concurrent.Executor executor;
        synchronized (com.android.server.tare.TareHandlerThread.class) {
            ensureThreadLocked();
            executor = sHandlerExecutor;
        }
        return executor;
    }

    public static android.os.Handler getHandler() {
        synchronized (com.android.server.tare.TareHandlerThread.class) {
            ensureThreadLocked();
        }
        return sHandler;
    }
}
