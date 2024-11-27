package android.os;

/* loaded from: classes3.dex */
public final class Looper {
    private static final java.lang.String TAG = "Looper";
    private static android.os.Looper sMainLooper;
    private static android.os.Looper.Observer sObserver;
    static final java.lang.ThreadLocal<android.os.Looper> sThreadLocal = new java.lang.ThreadLocal<>();
    private boolean mInLoop;
    private android.util.Printer mLogging;
    final android.os.MessageQueue mQueue;
    private boolean mSlowDeliveryDetected;
    private long mSlowDeliveryThresholdMs;
    private long mSlowDispatchThresholdMs;
    final java.lang.Thread mThread = java.lang.Thread.currentThread();
    private long mTraceTag;

    public interface Observer {
        void dispatchingThrewException(java.lang.Object obj, android.os.Message message, java.lang.Exception exc);

        java.lang.Object messageDispatchStarting();

        void messageDispatched(java.lang.Object obj, android.os.Message message);
    }

    public static void prepare() {
        prepare(true);
    }

    private static void prepare(boolean z) {
        if (sThreadLocal.get() != null) {
            throw new java.lang.RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new android.os.Looper(z));
    }

    @java.lang.Deprecated
    public static void prepareMainLooper() {
        prepare(false);
        synchronized (android.os.Looper.class) {
            if (sMainLooper != null) {
                throw new java.lang.IllegalStateException("The main Looper has already been prepared.");
            }
            sMainLooper = myLooper();
        }
    }

    public static android.os.Looper getMainLooper() {
        android.os.Looper looper;
        synchronized (android.os.Looper.class) {
            looper = sMainLooper;
        }
        return looper;
    }

    public static void setMainLooperForTest(android.os.Looper looper) {
        synchronized (android.os.Looper.class) {
            sMainLooper = (android.os.Looper) java.util.Objects.requireNonNull(looper);
        }
    }

    public static void clearMainLooperForTest() {
        synchronized (android.os.Looper.class) {
            sMainLooper = null;
        }
    }

    public static void setObserver(android.os.Looper.Observer observer) {
        sObserver = observer;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c0 A[Catch: Exception -> 0x00b9, all -> 0x0188, TRY_LEAVE, TryCatch #1 {Exception -> 0x00b9, blocks: (B:40:0x00b5, B:42:0x00c0), top: B:39:0x00b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00cb A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean loopOnce(android.os.Looper looper, long j, int i) {
        boolean z;
        long j2;
        long j3;
        android.os.Looper.Observer observer;
        boolean z2;
        boolean z3;
        java.lang.Object obj;
        long uid;
        android.os.Looper.Observer observer2;
        long clearCallingIdentity;
        android.os.Message next = looper.mQueue.next();
        if (next == null) {
            return false;
        }
        android.util.Printer printer = looper.mLogging;
        if (printer != null) {
            printer.println(">>>>> Dispatching to " + next.target + " " + next.callback + ": " + next.what);
        }
        android.os.Looper.Observer observer3 = sObserver;
        long j4 = looper.mTraceTag;
        long j5 = looper.mSlowDispatchThresholdMs;
        long j6 = looper.mSlowDeliveryThresholdMs;
        if (i < 0) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            j2 = j5;
            j3 = j6;
        } else {
            j3 = i;
            j2 = j3;
        }
        try {
            try {
                if (j3 > 0 || z) {
                    observer = observer3;
                    if (next.when > 0) {
                        z2 = true;
                        z3 = j2 <= 0 || z;
                        boolean z4 = !z2 || z3;
                        if (j4 != 0 && android.os.Trace.isTagEnabled(j4)) {
                            android.os.Trace.traceBegin(j4, next.target.getTraceName(next));
                        }
                        long uptimeMillis = !z4 ? android.os.SystemClock.uptimeMillis() : 0L;
                        if (observer != null) {
                            obj = null;
                        } else {
                            obj = observer.messageDispatchStarting();
                        }
                        uid = android.os.ThreadLocalWorkSource.setUid(next.workSourceUid);
                        next.target.dispatchMessage(next);
                        if (observer != null) {
                            observer2 = observer;
                        } else {
                            observer2 = observer;
                            try {
                                observer2.messageDispatched(obj, next);
                            } catch (java.lang.Exception e) {
                                e = e;
                                if (observer2 != null) {
                                    observer2.dispatchingThrewException(obj, next, e);
                                }
                                throw e;
                            }
                        }
                        long uptimeMillis2 = z3 ? android.os.SystemClock.uptimeMillis() : 0L;
                        if (z2) {
                            if (!looper.mSlowDeliveryDetected) {
                                if (showSlowLog(j3, next.when, uptimeMillis, "delivery", next)) {
                                    looper.mSlowDeliveryDetected = true;
                                }
                            } else if (uptimeMillis - next.when <= 10) {
                                android.util.Slog.w(TAG, "Drained");
                                looper.mSlowDeliveryDetected = false;
                            }
                        }
                        if (z3) {
                            showSlowLog(j2, uptimeMillis, uptimeMillis2, "dispatch", next);
                        }
                        if (printer != null) {
                            printer.println("<<<<< Finished to " + next.target + " " + next.callback);
                        }
                        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        if (j != clearCallingIdentity) {
                            android.util.Log.wtf(TAG, "Thread identity changed from 0x" + java.lang.Long.toHexString(j) + " to 0x" + java.lang.Long.toHexString(clearCallingIdentity) + " while dispatching to " + next.target.getClass().getName() + " " + next.callback + " what=" + next.what);
                        }
                        next.recycleUnchecked();
                        return true;
                    }
                } else {
                    observer = observer3;
                }
                next.target.dispatchMessage(next);
                if (observer != null) {
                }
                long uptimeMillis22 = z3 ? android.os.SystemClock.uptimeMillis() : 0L;
                if (z2) {
                }
                if (z3) {
                }
                if (printer != null) {
                }
                clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                if (j != clearCallingIdentity) {
                }
                next.recycleUnchecked();
                return true;
            } finally {
                android.os.ThreadLocalWorkSource.restore(uid);
                if (j4 != 0) {
                    android.os.Trace.traceEnd(j4);
                }
            }
        } catch (java.lang.Exception e2) {
            e = e2;
            observer2 = observer;
        }
        z2 = false;
        if (j2 <= 0) {
        }
        if (z2) {
        }
        if (j4 != 0) {
            android.os.Trace.traceBegin(j4, next.target.getTraceName(next));
        }
        if (!z4) {
        }
        if (observer != null) {
        }
        uid = android.os.ThreadLocalWorkSource.setUid(next.workSourceUid);
    }

    public static void loop() {
        android.os.Looper myLooper = myLooper();
        if (myLooper == null) {
            throw new java.lang.RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        if (myLooper.mInLoop) {
            android.util.Slog.w(TAG, "Loop again would have the queued messages be executed before this one completed.");
        }
        myLooper.mInLoop = true;
        android.os.Binder.clearCallingIdentity();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        int thresholdOverride = getThresholdOverride();
        myLooper.mSlowDeliveryDetected = false;
        while (loopOnce(myLooper, clearCallingIdentity, thresholdOverride)) {
        }
    }

    private static int getThresholdOverride() {
        return android.os.SystemProperties.getInt("log.looper." + android.os.Process.myUid() + android.media.MediaMetrics.SEPARATOR + java.lang.Thread.currentThread().getName() + ".slow", -1);
    }

    private static int getThresholdOverride$ravenwood() {
        return -1;
    }

    private static boolean showSlowLog(long j, long j2, long j3, java.lang.String str, android.os.Message message) {
        long j4 = j3 - j2;
        if (j4 < j) {
            return false;
        }
        android.util.Slog.w(TAG, "Slow " + str + " took " + j4 + "ms " + java.lang.Thread.currentThread().getName() + " h=" + message.target.getClass().getName() + " c=" + message.callback + " m=" + message.what);
        return true;
    }

    public static android.os.Looper myLooper() {
        return sThreadLocal.get();
    }

    public static android.os.MessageQueue myQueue() {
        return myLooper().mQueue;
    }

    private Looper(boolean z) {
        this.mQueue = new android.os.MessageQueue(z);
    }

    public boolean isCurrentThread() {
        return java.lang.Thread.currentThread() == this.mThread;
    }

    public void setMessageLogging(android.util.Printer printer) {
        this.mLogging = printer;
    }

    public void setTraceTag(long j) {
        this.mTraceTag = j;
    }

    public void setSlowLogThresholdMs(long j, long j2) {
        this.mSlowDispatchThresholdMs = j;
        this.mSlowDeliveryThresholdMs = j2;
    }

    public void quit() {
        this.mQueue.quit(false);
    }

    public void quitSafely() {
        this.mQueue.quit(true);
    }

    public java.lang.Thread getThread() {
        return this.mThread;
    }

    public android.os.MessageQueue getQueue() {
        return this.mQueue;
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        printer.println(str + toString());
        this.mQueue.dump(printer, str + "  ", null);
    }

    public void dump(android.util.Printer printer, java.lang.String str, android.os.Handler handler) {
        printer.println(str + toString());
        this.mQueue.dump(printer, str + "  ", handler);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mThread.getName());
        protoOutputStream.write(1112396529666L, this.mThread.getId());
        if (this.mQueue != null) {
            this.mQueue.dumpDebug(protoOutputStream, 1146756268035L);
        }
        protoOutputStream.end(start);
    }

    public java.lang.String toString() {
        return "Looper (" + this.mThread.getName() + ", tid " + this.mThread.getId() + ") {" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + "}";
    }
}
