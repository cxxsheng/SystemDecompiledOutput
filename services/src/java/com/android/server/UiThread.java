package com.android.server;

/* loaded from: classes.dex */
public final class UiThread extends com.android.server.ServiceThread {
    private static final long SLOW_DELIVERY_THRESHOLD_MS = 200;
    private static final long SLOW_DISPATCH_THRESHOLD_MS = 100;
    private static android.os.Handler sHandler;
    private static com.android.server.UiThread sInstance;

    private UiThread() {
        super("android.ui", -2, false);
    }

    @Override // com.android.server.ServiceThread, android.os.HandlerThread, java.lang.Thread, java.lang.Runnable
    public void run() {
        android.os.Process.setThreadGroup(android.os.Process.myTid(), 5);
        super.run();
    }

    private static void ensureThreadLocked() {
        if (sInstance == null) {
            sInstance = new com.android.server.UiThread();
            sInstance.start();
            android.os.Looper looper = sInstance.getLooper();
            looper.setTraceTag(524288L);
            looper.setSlowLogThresholdMs(SLOW_DISPATCH_THRESHOLD_MS, SLOW_DELIVERY_THRESHOLD_MS);
            sHandler = com.android.server.ServiceThread.makeSharedHandler(sInstance.getLooper());
        }
    }

    public static com.android.server.UiThread get() {
        com.android.server.UiThread uiThread;
        synchronized (com.android.server.UiThread.class) {
            ensureThreadLocked();
            uiThread = sInstance;
        }
        return uiThread;
    }

    public static android.os.Handler getHandler() {
        android.os.Handler handler;
        synchronized (com.android.server.UiThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static void dispose() {
        synchronized (com.android.server.UiThread.class) {
            try {
                if (sInstance == null) {
                    return;
                }
                android.os.Handler handler = getHandler();
                final com.android.server.UiThread uiThread = sInstance;
                java.util.Objects.requireNonNull(uiThread);
                handler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.UiThread$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.UiThread.this.quit();
                    }
                }, 0L);
                sInstance = null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
