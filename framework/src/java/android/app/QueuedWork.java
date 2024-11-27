package android.app;

/* loaded from: classes.dex */
public class QueuedWork {
    private static final boolean DEBUG = false;
    private static final long DELAY = 100;
    private static final long MAX_WAIT_TIME_MILLIS = 512;
    private static final java.lang.String LOG_TAG = android.app.QueuedWork.class.getSimpleName();
    private static final java.lang.Object sLock = new java.lang.Object();
    private static java.lang.Object sProcessingWork = new java.lang.Object();
    private static final java.util.LinkedList<java.lang.Runnable> sFinishers = new java.util.LinkedList<>();
    private static android.os.Handler sHandler = null;
    private static java.util.LinkedList<java.lang.Runnable> sWork = new java.util.LinkedList<>();
    private static boolean sCanDelay = true;
    private static final com.android.internal.util.ExponentiallyBucketedHistogram mWaitTimes = new com.android.internal.util.ExponentiallyBucketedHistogram(16);
    private static int mNumWaits = 0;

    private static android.os.Handler getHandler() {
        android.os.Handler handler;
        synchronized (sLock) {
            if (sHandler == null) {
                android.os.HandlerThread handlerThread = new android.os.HandlerThread("queued-work-looper", -2);
                handlerThread.start();
                sHandler = new android.app.QueuedWork.QueuedWorkHandler(handlerThread.getLooper());
            }
            handler = sHandler;
        }
        return handler;
    }

    public static void resetHandler() {
        synchronized (sLock) {
            if (sHandler == null) {
                return;
            }
            sHandler.getLooper().quitSafely();
            sHandler = null;
        }
    }

    private static void handlerRemoveMessages(int i) {
        synchronized (sLock) {
            if (sHandler == null) {
                return;
            }
            getHandler().removeMessages(i);
        }
    }

    public static void addFinisher(java.lang.Runnable runnable) {
        synchronized (sLock) {
            sFinishers.add(runnable);
        }
    }

    public static void removeFinisher(java.lang.Runnable runnable) {
        synchronized (sLock) {
            sFinishers.remove(runnable);
        }
    }

    public static void waitToFinish() {
        java.lang.Runnable poll;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        synchronized (sLock) {
            handlerRemoveMessages(1);
            sCanDelay = false;
        }
        android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
        try {
            processPendingWork();
            while (true) {
                try {
                    synchronized (sLock) {
                        poll = sFinishers.poll();
                    }
                    if (poll == null) {
                        break;
                    } else {
                        poll.run();
                    }
                } catch (java.lang.Throwable th) {
                    sCanDelay = true;
                    throw th;
                }
            }
            sCanDelay = true;
            synchronized (sLock) {
                long currentTimeMillis2 = java.lang.System.currentTimeMillis() - currentTimeMillis;
                if (currentTimeMillis2 > 0) {
                    mWaitTimes.add(java.lang.Long.valueOf(currentTimeMillis2).intValue());
                    mNumWaits++;
                    if (mNumWaits % 1024 == 0 || currentTimeMillis2 > 512) {
                        mWaitTimes.log(LOG_TAG, "waited: ");
                    }
                }
            }
        } finally {
            android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    public static void queue(java.lang.Runnable runnable, boolean z) {
        android.os.Handler handler = getHandler();
        synchronized (sLock) {
            sWork.add(runnable);
            if (z && sCanDelay) {
                handler.sendEmptyMessageDelayed(1, DELAY);
            } else {
                handler.sendEmptyMessage(1);
            }
        }
    }

    public static boolean hasPendingWork() {
        boolean z;
        synchronized (sLock) {
            z = !sWork.isEmpty();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void processPendingWork() {
        java.util.LinkedList<java.lang.Runnable> linkedList;
        synchronized (sProcessingWork) {
            synchronized (sLock) {
                linkedList = sWork;
                sWork = new java.util.LinkedList<>();
                handlerRemoveMessages(1);
            }
            if (linkedList.size() > 0) {
                java.util.Iterator<java.lang.Runnable> it = linkedList.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
            }
        }
    }

    private static class QueuedWorkHandler extends android.os.Handler {
        static final int MSG_RUN = 1;

        QueuedWorkHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (message.what == 1) {
                android.app.QueuedWork.processPendingWork();
            }
        }
    }
}
