package com.android.server;

/* loaded from: classes.dex */
public final class DisplayThread extends com.android.server.ServiceThread {
    private static android.os.Handler sHandler;
    private static com.android.server.DisplayThread sInstance;

    private DisplayThread() {
        super("android.display", -3, false);
    }

    private static void ensureThreadLocked() {
        if (sInstance == null) {
            sInstance = new com.android.server.DisplayThread();
            sInstance.start();
            sInstance.getLooper().setTraceTag(524288L);
            sHandler = com.android.server.ServiceThread.makeSharedHandler(sInstance.getLooper());
        }
    }

    public static com.android.server.DisplayThread get() {
        com.android.server.DisplayThread displayThread;
        synchronized (com.android.server.DisplayThread.class) {
            ensureThreadLocked();
            displayThread = sInstance;
        }
        return displayThread;
    }

    public static android.os.Handler getHandler() {
        android.os.Handler handler;
        synchronized (com.android.server.DisplayThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static void dispose() {
        synchronized (com.android.server.DisplayThread.class) {
            try {
                if (sInstance == null) {
                    return;
                }
                getHandler().runWithScissors(new java.lang.Runnable() { // from class: com.android.server.DisplayThread$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.DisplayThread.lambda$dispose$0();
                    }
                }, 0L);
                sInstance = null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dispose$0() {
        sInstance.quit();
    }
}
