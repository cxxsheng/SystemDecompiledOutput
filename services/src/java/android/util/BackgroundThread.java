package android.util;

/* loaded from: classes.dex */
public final class BackgroundThread extends android.os.HandlerThread {

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static android.os.Handler sHandler;

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static android.util.HandlerExecutor sHandlerExecutor;

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static android.util.BackgroundThread sInstance;
    private static final java.lang.Object sLock = new java.lang.Object();

    private BackgroundThread() {
        super(android.util.BackgroundThread.class.getName(), 10);
    }

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static void ensureThreadLocked() {
        if (sInstance == null) {
            sInstance = new android.util.BackgroundThread();
            sInstance.start();
            sHandler = new android.os.Handler(sInstance.getLooper());
            sHandlerExecutor = new android.util.HandlerExecutor(sHandler);
        }
    }

    @android.annotation.NonNull
    public static android.util.BackgroundThread get() {
        android.util.BackgroundThread backgroundThread;
        synchronized (sLock) {
            ensureThreadLocked();
            backgroundThread = sInstance;
        }
        return backgroundThread;
    }

    @android.annotation.NonNull
    public static android.os.Handler getHandler() {
        android.os.Handler handler;
        synchronized (sLock) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }

    @android.annotation.NonNull
    public static java.util.concurrent.Executor getExecutor() {
        android.util.HandlerExecutor handlerExecutor;
        synchronized (sLock) {
            ensureThreadLocked();
            handlerExecutor = sHandlerExecutor;
        }
        return handlerExecutor;
    }
}
