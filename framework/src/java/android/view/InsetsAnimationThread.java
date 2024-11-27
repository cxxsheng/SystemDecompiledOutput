package android.view;

/* loaded from: classes4.dex */
public class InsetsAnimationThread extends android.os.HandlerThread {
    private static android.os.Handler sHandler;
    private static android.view.InsetsAnimationThread sInstance;

    private InsetsAnimationThread() {
        super("InsetsAnimations");
    }

    private static void ensureThreadLocked() {
        if (sInstance == null) {
            sInstance = new android.view.InsetsAnimationThread();
            sInstance.start();
            sInstance.getLooper().setTraceTag(8L);
            sHandler = new android.os.Handler(sInstance.getLooper());
        }
    }

    public static void release() {
        synchronized (android.view.InsetsAnimationThread.class) {
            if (sInstance == null) {
                return;
            }
            sInstance.getLooper().quitSafely();
            sInstance = null;
            sHandler = null;
        }
    }

    public static android.view.InsetsAnimationThread get() {
        android.view.InsetsAnimationThread insetsAnimationThread;
        synchronized (android.view.InsetsAnimationThread.class) {
            ensureThreadLocked();
            insetsAnimationThread = sInstance;
        }
        return insetsAnimationThread;
    }

    public static android.os.Handler getHandler() {
        android.os.Handler handler;
        synchronized (android.view.InsetsAnimationThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }
}
