package com.android.server.wm;

/* loaded from: classes3.dex */
public final class SurfaceAnimationThread extends com.android.server.ServiceThread {
    private static android.os.Handler sHandler;
    private static com.android.server.wm.SurfaceAnimationThread sInstance;

    private SurfaceAnimationThread() {
        super("android.anim.lf", -4, false);
    }

    private static void ensureThreadLocked() {
        if (sInstance == null) {
            sInstance = new com.android.server.wm.SurfaceAnimationThread();
            sInstance.start();
            sInstance.getLooper().setTraceTag(32L);
            sHandler = com.android.server.ServiceThread.makeSharedHandler(sInstance.getLooper());
        }
    }

    public static com.android.server.wm.SurfaceAnimationThread get() {
        com.android.server.wm.SurfaceAnimationThread surfaceAnimationThread;
        synchronized (com.android.server.wm.SurfaceAnimationThread.class) {
            ensureThreadLocked();
            surfaceAnimationThread = sInstance;
        }
        return surfaceAnimationThread;
    }

    public static android.os.Handler getHandler() {
        android.os.Handler handler;
        synchronized (com.android.server.wm.SurfaceAnimationThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static void dispose() {
        synchronized (com.android.server.wm.SurfaceAnimationThread.class) {
            try {
                if (sInstance == null) {
                    return;
                }
                getHandler().runWithScissors(new java.lang.Runnable() { // from class: com.android.server.wm.SurfaceAnimationThread$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.SurfaceAnimationThread.lambda$dispose$0();
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
