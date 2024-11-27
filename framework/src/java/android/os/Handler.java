package android.os;

/* loaded from: classes3.dex */
public class Handler {
    private static final boolean FIND_POTENTIAL_LEAKS = false;
    private static android.os.Handler MAIN_THREAD_HANDLER = null;
    private static final java.lang.String TAG = "Handler";
    final boolean mAsynchronous;
    final android.os.Handler.Callback mCallback;
    private final boolean mIsShared;
    final android.os.Looper mLooper;
    android.os.IMessenger mMessenger;
    final android.os.MessageQueue mQueue;

    public interface Callback {
        boolean handleMessage(android.os.Message message);
    }

    public void handleMessage(android.os.Message message) {
    }

    public void dispatchMessage(android.os.Message message) {
        if (message.callback != null) {
            handleCallback(message);
        } else {
            if (this.mCallback != null && this.mCallback.handleMessage(message)) {
                return;
            }
            handleMessage(message);
        }
    }

    @java.lang.Deprecated
    public Handler() {
        this((android.os.Handler.Callback) null, false);
    }

    @java.lang.Deprecated
    public Handler(android.os.Handler.Callback callback) {
        this(callback, false);
    }

    public Handler(android.os.Looper looper) {
        this(looper, null, false);
    }

    public Handler(android.os.Looper looper, android.os.Handler.Callback callback) {
        this(looper, callback, false);
    }

    public Handler(boolean z) {
        this((android.os.Handler.Callback) null, z);
    }

    public Handler(android.os.Handler.Callback callback, boolean z) {
        this.mLooper = android.os.Looper.myLooper();
        if (this.mLooper == null) {
            throw new java.lang.RuntimeException("Can't create handler inside thread " + java.lang.Thread.currentThread() + " that has not called Looper.prepare()");
        }
        this.mQueue = this.mLooper.mQueue;
        this.mCallback = callback;
        this.mAsynchronous = z;
        this.mIsShared = false;
    }

    public Handler(android.os.Looper looper, android.os.Handler.Callback callback, boolean z) {
        this(looper, callback, z, false);
    }

    public Handler(android.os.Looper looper, android.os.Handler.Callback callback, boolean z, boolean z2) {
        this.mLooper = looper;
        this.mQueue = looper.mQueue;
        this.mCallback = callback;
        this.mAsynchronous = z;
        this.mIsShared = z2;
    }

    public static android.os.Handler createAsync(android.os.Looper looper) {
        if (looper == null) {
            throw new java.lang.NullPointerException("looper must not be null");
        }
        return new android.os.Handler(looper, null, true);
    }

    public static android.os.Handler createAsync(android.os.Looper looper, android.os.Handler.Callback callback) {
        if (looper == null) {
            throw new java.lang.NullPointerException("looper must not be null");
        }
        if (callback == null) {
            throw new java.lang.NullPointerException("callback must not be null");
        }
        return new android.os.Handler(looper, callback, true);
    }

    public static android.os.Handler getMain() {
        if (MAIN_THREAD_HANDLER == null) {
            MAIN_THREAD_HANDLER = new android.os.Handler(android.os.Looper.getMainLooper());
        }
        return MAIN_THREAD_HANDLER;
    }

    public static android.os.Handler mainIfNull(android.os.Handler handler) {
        return handler == null ? getMain() : handler;
    }

    public java.lang.String getTraceName(android.os.Message message) {
        if (message.callback instanceof android.os.TraceNameSupplier) {
            return ((android.os.TraceNameSupplier) message.callback).getTraceName();
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(getClass().getName()).append(": ");
        if (message.callback != null) {
            sb.append(message.callback.getClass().getName());
        } else {
            sb.append("#").append(message.what);
        }
        return sb.toString();
    }

    public java.lang.String getMessageName(android.os.Message message) {
        if (message.callback != null) {
            return message.callback.getClass().getName();
        }
        return "0x" + java.lang.Integer.toHexString(message.what);
    }

    public final android.os.Message obtainMessage() {
        return android.os.Message.obtain(this);
    }

    public final android.os.Message obtainMessage(int i) {
        return android.os.Message.obtain(this, i);
    }

    public final android.os.Message obtainMessage(int i, java.lang.Object obj) {
        return android.os.Message.obtain(this, i, obj);
    }

    public final android.os.Message obtainMessage(int i, int i2, int i3) {
        return android.os.Message.obtain(this, i, i2, i3);
    }

    public final android.os.Message obtainMessage(int i, int i2, int i3, java.lang.Object obj) {
        return android.os.Message.obtain(this, i, i2, i3, obj);
    }

    public final boolean post(java.lang.Runnable runnable) {
        return sendMessageDelayed(getPostMessage(runnable), 0L);
    }

    public final boolean postAtTime(java.lang.Runnable runnable, long j) {
        return sendMessageAtTime(getPostMessage(runnable), j);
    }

    public final boolean postAtTime(java.lang.Runnable runnable, java.lang.Object obj, long j) {
        return sendMessageAtTime(getPostMessage(runnable, obj), j);
    }

    public final boolean postDelayed(java.lang.Runnable runnable, long j) {
        return sendMessageDelayed(getPostMessage(runnable), j);
    }

    public final boolean postDelayed(java.lang.Runnable runnable, int i, long j) {
        return sendMessageDelayed(getPostMessage(runnable).setWhat(i), j);
    }

    public final boolean postDelayed(java.lang.Runnable runnable, java.lang.Object obj, long j) {
        return sendMessageDelayed(getPostMessage(runnable, obj), j);
    }

    public final boolean postAtFrontOfQueue(java.lang.Runnable runnable) {
        return sendMessageAtFrontOfQueue(getPostMessage(runnable));
    }

    public final boolean runWithScissors(java.lang.Runnable runnable, long j) {
        if (runnable == null) {
            throw new java.lang.IllegalArgumentException("runnable must not be null");
        }
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("timeout must be non-negative");
        }
        if (android.os.Looper.myLooper() == this.mLooper) {
            runnable.run();
            return true;
        }
        return new android.os.Handler.BlockingRunnable(runnable).postAndWait(this, j);
    }

    public final void removeCallbacks(java.lang.Runnable runnable) {
        this.mQueue.removeMessages(this, runnable, (java.lang.Object) null);
    }

    public final void removeCallbacks(java.lang.Runnable runnable, java.lang.Object obj) {
        this.mQueue.removeMessages(this, runnable, obj);
    }

    public final boolean sendMessage(android.os.Message message) {
        return sendMessageDelayed(message, 0L);
    }

    public final boolean sendEmptyMessage(int i) {
        return sendEmptyMessageDelayed(i, 0L);
    }

    public final boolean sendEmptyMessageDelayed(int i, long j) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        return sendMessageDelayed(obtain, j);
    }

    public final boolean sendEmptyMessageAtTime(int i, long j) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        return sendMessageAtTime(obtain, j);
    }

    public final boolean sendMessageDelayed(android.os.Message message, long j) {
        if (j < 0) {
            j = 0;
        }
        return sendMessageAtTime(message, android.os.SystemClock.uptimeMillis() + j);
    }

    public boolean sendMessageAtTime(android.os.Message message, long j) {
        android.os.MessageQueue messageQueue = this.mQueue;
        if (messageQueue == null) {
            java.lang.RuntimeException runtimeException = new java.lang.RuntimeException(this + " sendMessageAtTime() called with no mQueue");
            android.util.Log.w("Looper", runtimeException.getMessage(), runtimeException);
            return false;
        }
        return enqueueMessage(messageQueue, message, j);
    }

    public final boolean sendMessageAtFrontOfQueue(android.os.Message message) {
        android.os.MessageQueue messageQueue = this.mQueue;
        if (messageQueue == null) {
            java.lang.RuntimeException runtimeException = new java.lang.RuntimeException(this + " sendMessageAtFrontOfQueue() called with no mQueue");
            android.util.Log.w("Looper", runtimeException.getMessage(), runtimeException);
            return false;
        }
        return enqueueMessage(messageQueue, message, 0L);
    }

    public final boolean executeOrSendMessage(android.os.Message message) {
        if (this.mLooper == android.os.Looper.myLooper()) {
            dispatchMessage(message);
            return true;
        }
        return sendMessage(message);
    }

    private boolean enqueueMessage(android.os.MessageQueue messageQueue, android.os.Message message, long j) {
        message.target = this;
        message.workSourceUid = android.os.ThreadLocalWorkSource.getUid();
        if (this.mAsynchronous) {
            message.setAsynchronous(true);
        }
        return messageQueue.enqueueMessage(message, j);
    }

    private java.lang.Object disallowNullArgumentIfShared(java.lang.Object obj) {
        if (this.mIsShared && obj == null) {
            throw new java.lang.IllegalArgumentException("Null argument disallowed for shared handler. Consider creating your own Handler instance.");
        }
        return obj;
    }

    public final void removeMessages(int i) {
        this.mQueue.removeMessages(this, i, (java.lang.Object) null);
    }

    public final void removeMessages(int i, java.lang.Object obj) {
        this.mQueue.removeMessages(this, i, disallowNullArgumentIfShared(obj));
    }

    public final void removeEqualMessages(int i, java.lang.Object obj) {
        this.mQueue.removeEqualMessages(this, i, disallowNullArgumentIfShared(obj));
    }

    public final void removeCallbacksAndMessages(java.lang.Object obj) {
        this.mQueue.removeCallbacksAndMessages(this, disallowNullArgumentIfShared(obj));
    }

    public final void removeCallbacksAndEqualMessages(java.lang.Object obj) {
        this.mQueue.removeCallbacksAndEqualMessages(this, disallowNullArgumentIfShared(obj));
    }

    public final boolean hasMessages(int i) {
        return this.mQueue.hasMessages(this, i, (java.lang.Object) null);
    }

    public final boolean hasMessagesOrCallbacks() {
        return this.mQueue.hasMessages(this);
    }

    public final boolean hasMessages(int i, java.lang.Object obj) {
        return this.mQueue.hasMessages(this, i, obj);
    }

    public final boolean hasEqualMessages(int i, java.lang.Object obj) {
        return this.mQueue.hasEqualMessages(this, i, obj);
    }

    public final boolean hasCallbacks(java.lang.Runnable runnable) {
        return this.mQueue.hasMessages(this, runnable, (java.lang.Object) null);
    }

    public final android.os.Looper getLooper() {
        return this.mLooper;
    }

    public final void dump(android.util.Printer printer, java.lang.String str) {
        printer.println(str + this + " @ " + android.os.SystemClock.uptimeMillis());
        if (this.mLooper == null) {
            printer.println(str + "looper uninitialized");
        } else {
            this.mLooper.dump(printer, str + "  ");
        }
    }

    public final void dumpMine(android.util.Printer printer, java.lang.String str) {
        printer.println(str + this + " @ " + android.os.SystemClock.uptimeMillis());
        if (this.mLooper == null) {
            printer.println(str + "looper uninitialized");
        } else {
            this.mLooper.dump(printer, str + "  ", this);
        }
    }

    public java.lang.String toString() {
        return "Handler (" + getClass().getName() + ") {" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + "}";
    }

    final android.os.IMessenger getIMessenger() {
        synchronized (this.mQueue) {
            if (this.mMessenger != null) {
                return this.mMessenger;
            }
            this.mMessenger = new android.os.Handler.MessengerImpl();
            return this.mMessenger;
        }
    }

    private final class MessengerImpl extends android.os.IMessenger.Stub {
        private MessengerImpl() {
        }

        @Override // android.os.IMessenger
        public void send(android.os.Message message) {
            message.sendingUid = android.os.Binder.getCallingUid();
            android.os.Handler.this.sendMessage(message);
        }
    }

    private static android.os.Message getPostMessage(java.lang.Runnable runnable) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.callback = runnable;
        return obtain;
    }

    private static android.os.Message getPostMessage(java.lang.Runnable runnable, java.lang.Object obj) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.obj = obj;
        obtain.callback = runnable;
        return obtain;
    }

    private static void handleCallback(android.os.Message message) {
        message.callback.run();
    }

    private static final class BlockingRunnable implements java.lang.Runnable {
        private boolean mDone;
        private final java.lang.Runnable mTask;

        public BlockingRunnable(java.lang.Runnable runnable) {
            this.mTask = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mTask.run();
                synchronized (this) {
                    this.mDone = true;
                    notifyAll();
                }
            } catch (java.lang.Throwable th) {
                synchronized (this) {
                    this.mDone = true;
                    notifyAll();
                    throw th;
                }
            }
        }

        public boolean postAndWait(android.os.Handler handler, long j) {
            if (!handler.post(this)) {
                return false;
            }
            synchronized (this) {
                if (j > 0) {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis() + j;
                    while (!this.mDone) {
                        long uptimeMillis2 = uptimeMillis - android.os.SystemClock.uptimeMillis();
                        if (uptimeMillis2 <= 0) {
                            return false;
                        }
                        try {
                            wait(uptimeMillis2);
                        } catch (java.lang.InterruptedException e) {
                        }
                    }
                } else {
                    while (!this.mDone) {
                        try {
                            wait();
                        } catch (java.lang.InterruptedException e2) {
                        }
                    }
                }
                return true;
            }
        }
    }
}
