package com.android.server;

/* loaded from: classes.dex */
public final class AnimationThread extends com.android.server.ServiceThread {
    private static android.os.Handler sHandler;
    private static com.android.server.AnimationThread sInstance;

    private AnimationThread() {
        super("android.anim", -4, false);
    }

    private static void ensureThreadLocked() {
        if (sInstance == null) {
            sInstance = new com.android.server.AnimationThread();
            sInstance.start();
            sInstance.getLooper().setTraceTag(32L);
            sHandler = com.android.server.ServiceThread.makeSharedHandler(sInstance.getLooper());
        }
    }

    public static com.android.server.AnimationThread get() {
        com.android.server.AnimationThread animationThread;
        synchronized (com.android.server.AnimationThread.class) {
            ensureThreadLocked();
            animationThread = sInstance;
        }
        return animationThread;
    }

    public static android.os.Handler getHandler() {
        android.os.Handler handler;
        synchronized (com.android.server.AnimationThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static void dispose() {
        synchronized (com.android.server.AnimationThread.class) {
            try {
                if (sInstance == null) {
                    return;
                }
                getHandler().runWithScissors(new java.lang.Runnable() { // from class: com.android.server.AnimationThread$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.AnimationThread.lambda$dispose$0();
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
