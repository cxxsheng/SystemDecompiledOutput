package android.os;

/* loaded from: classes3.dex */
public final class MessageQueue {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "MessageQueue";
    private int mAsyncMessageCount;
    private boolean mBlocked;
    private android.util.SparseArray<android.os.MessageQueue.FileDescriptorRecord> mFileDescriptorRecords;
    private android.os.Message mLast;
    android.os.Message mMessages;
    private int mNextBarrierToken;
    private android.os.MessageQueue.IdleHandler[] mPendingIdleHandlers;
    private final boolean mQuitAllowed;
    private boolean mQuitting;
    private final java.util.ArrayList<android.os.MessageQueue.IdleHandler> mIdleHandlers = new java.util.ArrayList<>();
    private long mPtr = nativeInit();

    public interface IdleHandler {
        boolean queueIdle();
    }

    public interface OnFileDescriptorEventListener {
        public static final int EVENT_ERROR = 4;
        public static final int EVENT_INPUT = 1;
        public static final int EVENT_OUTPUT = 2;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Events {
        }

        int onFileDescriptorEvents(java.io.FileDescriptor fileDescriptor, int i);
    }

    private static native void nativeDestroy(long j);

    private static native long nativeInit();

    private static native boolean nativeIsPolling(long j);

    private native void nativePollOnce(long j, int i);

    private static native void nativeSetFileDescriptorEvents(long j, int i, int i2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeWake(long j);

    MessageQueue(boolean z) {
        this.mQuitAllowed = z;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    private void dispose() {
        if (this.mPtr != 0) {
            nativeDestroy(this.mPtr);
            this.mPtr = 0L;
        }
    }

    public boolean isIdle() {
        boolean z;
        synchronized (this) {
            z = this.mMessages == null || android.os.SystemClock.uptimeMillis() < this.mMessages.when;
        }
        return z;
    }

    public void addIdleHandler(android.os.MessageQueue.IdleHandler idleHandler) {
        if (idleHandler == null) {
            throw new java.lang.NullPointerException("Can't add a null IdleHandler");
        }
        synchronized (this) {
            this.mIdleHandlers.add(idleHandler);
        }
    }

    public void removeIdleHandler(android.os.MessageQueue.IdleHandler idleHandler) {
        synchronized (this) {
            this.mIdleHandlers.remove(idleHandler);
        }
    }

    public boolean isPolling() {
        boolean isPollingLocked;
        synchronized (this) {
            isPollingLocked = isPollingLocked();
        }
        return isPollingLocked;
    }

    private boolean isPollingLocked() {
        return !this.mQuitting && nativeIsPolling(this.mPtr);
    }

    public void addOnFileDescriptorEventListener(java.io.FileDescriptor fileDescriptor, int i, android.os.MessageQueue.OnFileDescriptorEventListener onFileDescriptorEventListener) {
        if (fileDescriptor == null) {
            throw new java.lang.IllegalArgumentException("fd must not be null");
        }
        if (onFileDescriptorEventListener == null) {
            throw new java.lang.IllegalArgumentException("listener must not be null");
        }
        synchronized (this) {
            updateOnFileDescriptorEventListenerLocked(fileDescriptor, i, onFileDescriptorEventListener);
        }
    }

    public void removeOnFileDescriptorEventListener(java.io.FileDescriptor fileDescriptor) {
        if (fileDescriptor == null) {
            throw new java.lang.IllegalArgumentException("fd must not be null");
        }
        synchronized (this) {
            updateOnFileDescriptorEventListenerLocked(fileDescriptor, 0, null);
        }
    }

    private void updateOnFileDescriptorEventListenerLocked(java.io.FileDescriptor fileDescriptor, int i, android.os.MessageQueue.OnFileDescriptorEventListener onFileDescriptorEventListener) {
        int i2;
        int int$ = fileDescriptor.getInt$();
        android.os.MessageQueue.FileDescriptorRecord fileDescriptorRecord = null;
        if (this.mFileDescriptorRecords == null) {
            i2 = -1;
        } else {
            i2 = this.mFileDescriptorRecords.indexOfKey(int$);
            if (i2 >= 0 && (fileDescriptorRecord = this.mFileDescriptorRecords.valueAt(i2)) != null && fileDescriptorRecord.mEvents == i) {
                return;
            }
        }
        if (i != 0) {
            int i3 = i | 4;
            if (fileDescriptorRecord == null) {
                if (this.mFileDescriptorRecords == null) {
                    this.mFileDescriptorRecords = new android.util.SparseArray<>();
                }
                this.mFileDescriptorRecords.put(int$, new android.os.MessageQueue.FileDescriptorRecord(fileDescriptor, i3, onFileDescriptorEventListener));
            } else {
                fileDescriptorRecord.mListener = onFileDescriptorEventListener;
                fileDescriptorRecord.mEvents = i3;
                fileDescriptorRecord.mSeq++;
            }
            nativeSetFileDescriptorEvents(this.mPtr, int$, i3);
            return;
        }
        if (fileDescriptorRecord != null) {
            fileDescriptorRecord.mEvents = 0;
            this.mFileDescriptorRecords.removeAt(i2);
            nativeSetFileDescriptorEvents(this.mPtr, int$, 0);
        }
    }

    private int dispatchEvents(int i, int i2) {
        synchronized (this) {
            android.os.MessageQueue.FileDescriptorRecord fileDescriptorRecord = this.mFileDescriptorRecords.get(i);
            if (fileDescriptorRecord == null) {
                return 0;
            }
            int i3 = fileDescriptorRecord.mEvents;
            int i4 = i2 & i3;
            if (i4 == 0) {
                return i3;
            }
            android.os.MessageQueue.OnFileDescriptorEventListener onFileDescriptorEventListener = fileDescriptorRecord.mListener;
            int i5 = fileDescriptorRecord.mSeq;
            int onFileDescriptorEvents = onFileDescriptorEventListener.onFileDescriptorEvents(fileDescriptorRecord.mDescriptor, i4);
            if (onFileDescriptorEvents != 0) {
                onFileDescriptorEvents |= 4;
            }
            if (onFileDescriptorEvents != i3) {
                synchronized (this) {
                    int indexOfKey = this.mFileDescriptorRecords.indexOfKey(i);
                    if (indexOfKey >= 0 && this.mFileDescriptorRecords.valueAt(indexOfKey) == fileDescriptorRecord && fileDescriptorRecord.mSeq == i5) {
                        fileDescriptorRecord.mEvents = onFileDescriptorEvents;
                        if (onFileDescriptorEvents == 0) {
                            this.mFileDescriptorRecords.removeAt(indexOfKey);
                        }
                    }
                }
            }
            return onFileDescriptorEvents;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00bb, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00bc, code lost:
    
        if (r8 >= r7) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00be, code lost:
    
        r9 = r17.mPendingIdleHandlers[r8];
        r17.mPendingIdleHandlers[r8] = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00c7, code lost:
    
        r0 = r9.queueIdle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00cc, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00cd, code lost:
    
        android.util.Log.wtf(android.os.MessageQueue.TAG, "IdleHandler threw exception", r0);
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00e6, code lost:
    
        r0 = 0;
        r7 = 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    android.os.Message next() {
        android.os.Message message;
        android.os.MessageQueue.IdleHandler idleHandler;
        boolean z;
        android.os.Message message2;
        long j = this.mPtr;
        if (j == 0) {
            return null;
        }
        int i = -1;
        int i2 = 0;
        while (true) {
            if (i2 != 0) {
                android.os.Binder.flushPendingCommands();
            }
            nativePollOnce(j, i2);
            synchronized (this) {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                android.os.Message message3 = this.mMessages;
                if (message3 == null || message3.target != null) {
                    message = null;
                } else {
                    while (true) {
                        message2 = message3.next;
                        if (message2 == null || message2.isAsynchronous()) {
                            break;
                        }
                        message3 = message2;
                    }
                    message = message3;
                    message3 = message2;
                }
                if (message3 == null) {
                    i2 = -1;
                } else if (uptimeMillis < message3.when) {
                    i2 = (int) java.lang.Math.min(message3.when - uptimeMillis, 2147483647L);
                } else {
                    this.mBlocked = false;
                    if (message == null) {
                        this.mMessages = message3.next;
                        if (message3.next == null) {
                            this.mLast = null;
                        }
                    } else {
                        message.next = message3.next;
                        if (message.next == null) {
                            this.mLast = message;
                        }
                    }
                    message3.next = null;
                    message3.markInUse();
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    return message3;
                }
                if (this.mQuitting) {
                    dispose();
                    return null;
                }
                if (i < 0 && (this.mMessages == null || uptimeMillis < this.mMessages.when)) {
                    i = this.mIdleHandlers.size();
                }
                if (i <= 0) {
                    this.mBlocked = true;
                } else {
                    if (this.mPendingIdleHandlers == null) {
                        this.mPendingIdleHandlers = new android.os.MessageQueue.IdleHandler[java.lang.Math.max(i, 4)];
                    }
                    this.mPendingIdleHandlers = (android.os.MessageQueue.IdleHandler[]) this.mIdleHandlers.toArray(this.mPendingIdleHandlers);
                }
            }
        }
        if (!z) {
            synchronized (this) {
                this.mIdleHandlers.remove(idleHandler);
            }
        }
        int i3 = i3 + 1;
    }

    void quit(boolean z) {
        if (!this.mQuitAllowed) {
            throw new java.lang.IllegalStateException("Main thread not allowed to quit.");
        }
        synchronized (this) {
            if (this.mQuitting) {
                return;
            }
            this.mQuitting = true;
            if (z) {
                removeAllFutureMessagesLocked();
            } else {
                removeAllMessagesLocked();
            }
            nativeWake(this.mPtr);
        }
    }

    public int postSyncBarrier() {
        return postSyncBarrier(android.os.SystemClock.uptimeMillis());
    }

    private int postSyncBarrier(long j) {
        synchronized (this) {
            int i = this.mNextBarrierToken;
            this.mNextBarrierToken = i + 1;
            android.os.Message obtain = android.os.Message.obtain();
            obtain.markInUse();
            obtain.when = j;
            obtain.arg1 = i;
            android.os.Message message = null;
            if (android.os.Flags.messageQueueTailTracking() && this.mLast != null && this.mLast.when <= j) {
                this.mLast.next = obtain;
                this.mLast = obtain;
                obtain.next = null;
                return i;
            }
            android.os.Message message2 = this.mMessages;
            if (j != 0) {
                while (message2 != null && message2.when <= j) {
                    message = message2;
                    message2 = message2.next;
                }
            }
            if (message2 == null) {
                this.mLast = obtain;
            }
            if (message != null) {
                obtain.next = message2;
                message.next = obtain;
            } else {
                obtain.next = message2;
                this.mMessages = obtain;
            }
            return i;
        }
    }

    public void removeSyncBarrier(int i) {
        synchronized (this) {
            android.os.Message message = this.mMessages;
            android.os.Message message2 = null;
            while (message != null && (message.target != null || message.arg1 != i)) {
                message2 = message;
                message = message.next;
            }
            if (message == null) {
                throw new java.lang.IllegalStateException("The specified message queue synchronization  barrier token has not been posted or has already been removed.");
            }
            boolean z = false;
            if (message2 != null) {
                message2.next = message.next;
                if (message2.next == null) {
                    this.mLast = message2;
                }
            } else {
                this.mMessages = message.next;
                if (this.mMessages == null) {
                    this.mLast = null;
                }
                if (this.mMessages == null || this.mMessages.target != null) {
                    z = true;
                }
            }
            message.recycleUnchecked();
            if (z && !this.mQuitting) {
                nativeWake(this.mPtr);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00d6 A[Catch: all -> 0x00fd, TryCatch #0 {, blocks: (B:5:0x0005, B:7:0x000b, B:9:0x0010, B:10:0x0036, B:13:0x0038, B:17:0x0048, B:20:0x0050, B:22:0x0054, B:24:0x0058, B:27:0x0061, B:29:0x0068, B:32:0x0072, B:35:0x0077, B:36:0x00d0, B:38:0x00d6, B:40:0x00dd, B:41:0x00e2, B:43:0x0080, B:45:0x0085, B:48:0x008e, B:61:0x009b, B:62:0x009d, B:64:0x00a3, B:66:0x00a8, B:69:0x00b1, B:81:0x00bc, B:84:0x00c4, B:86:0x00cc, B:87:0x00e4, B:88:0x00fc), top: B:4:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00dd A[Catch: all -> 0x00fd, TryCatch #0 {, blocks: (B:5:0x0005, B:7:0x000b, B:9:0x0010, B:10:0x0036, B:13:0x0038, B:17:0x0048, B:20:0x0050, B:22:0x0054, B:24:0x0058, B:27:0x0061, B:29:0x0068, B:32:0x0072, B:35:0x0077, B:36:0x00d0, B:38:0x00d6, B:40:0x00dd, B:41:0x00e2, B:43:0x0080, B:45:0x0085, B:48:0x008e, B:61:0x009b, B:62:0x009d, B:64:0x00a3, B:66:0x00a8, B:69:0x00b1, B:81:0x00bc, B:84:0x00c4, B:86:0x00cc, B:87:0x00e4, B:88:0x00fc), top: B:4:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean enqueueMessage(android.os.Message message, long j) {
        android.os.Message message2;
        android.os.Message message3;
        if (message.target == null) {
            throw new java.lang.IllegalArgumentException("Message must have a target.");
        }
        synchronized (this) {
            if (message.isInUse()) {
                throw new java.lang.IllegalStateException(message + " This message is already in use.");
            }
            boolean z = false;
            if (this.mQuitting) {
                java.lang.IllegalStateException illegalStateException = new java.lang.IllegalStateException(message.target + " sending message to a Handler on a dead thread");
                android.util.Log.w(TAG, illegalStateException.getMessage(), illegalStateException);
                message.recycle();
                return false;
            }
            message.markInUse();
            message.when = j;
            android.os.Message message4 = this.mMessages;
            if (message4 != null && j != 0 && j >= message4.when) {
                boolean z2 = this.mBlocked && message4.target == null && message.isAsynchronous();
                if (!android.os.Flags.messageQueueTailTracking()) {
                    while (true) {
                        message2 = message4.next;
                        if (message2 == null || j < message2.when) {
                            break;
                        }
                        if (!z2 || !message2.isAsynchronous()) {
                            message4 = message2;
                        } else {
                            z2 = false;
                            message4 = message2;
                        }
                    }
                    message.next = message2;
                    message4.next = message;
                    this.mLast = null;
                    z = z2;
                } else if (j >= this.mLast.when) {
                    if (z2 && this.mAsyncMessageCount == 0) {
                        z = true;
                    }
                    message.next = null;
                    this.mLast.next = message;
                    this.mLast = message;
                } else {
                    while (true) {
                        message3 = message4.next;
                        if (message3 == null || j < message3.when) {
                            break;
                        }
                        if (!z2 || !message3.isAsynchronous()) {
                            message4 = message3;
                        } else {
                            z2 = false;
                            message4 = message3;
                        }
                    }
                    if (message3 == null) {
                        this.mLast = message;
                    }
                    message.next = message3;
                    message4.next = message;
                    z = z2;
                }
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount++;
                }
                if (z) {
                    nativeWake(this.mPtr);
                }
                return true;
            }
            message.next = message4;
            this.mMessages = message;
            z = this.mBlocked;
            if (message4 == null) {
                this.mLast = this.mMessages;
            }
            if (message.isAsynchronous()) {
            }
            if (z) {
            }
            return true;
        }
    }

    boolean hasMessages(android.os.Handler handler, int i, java.lang.Object obj) {
        if (handler == null) {
            return false;
        }
        synchronized (this) {
            for (android.os.Message message = this.mMessages; message != null; message = message.next) {
                if (message.target == handler && message.what == i && (obj == null || message.obj == obj)) {
                    return true;
                }
            }
            return false;
        }
    }

    boolean hasEqualMessages(android.os.Handler handler, int i, java.lang.Object obj) {
        if (handler == null) {
            return false;
        }
        synchronized (this) {
            for (android.os.Message message = this.mMessages; message != null; message = message.next) {
                if (message.target == handler && message.what == i && (obj == null || obj.equals(message.obj))) {
                    return true;
                }
            }
            return false;
        }
    }

    boolean hasMessages(android.os.Handler handler, java.lang.Runnable runnable, java.lang.Object obj) {
        if (handler == null) {
            return false;
        }
        synchronized (this) {
            for (android.os.Message message = this.mMessages; message != null; message = message.next) {
                if (message.target == handler && message.callback == runnable && (obj == null || message.obj == obj)) {
                    return true;
                }
            }
            return false;
        }
    }

    boolean hasMessages(android.os.Handler handler) {
        if (handler == null) {
            return false;
        }
        synchronized (this) {
            for (android.os.Message message = this.mMessages; message != null; message = message.next) {
                if (message.target == handler) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002e, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void removeMessages(android.os.Handler handler, int i, java.lang.Object obj) {
        if (handler == null) {
            return;
        }
        synchronized (this) {
            android.os.Message message = this.mMessages;
            while (message != null && message.target == handler && message.what == i && (obj == null || message.obj == obj)) {
                android.os.Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                message = message2;
            }
            while (message != null) {
                android.os.Message message3 = message.next;
                if (message3 != null && message3.target == handler && message3.what == i && (obj == null || message3.obj == obj)) {
                    android.os.Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0032, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void removeEqualMessages(android.os.Handler handler, int i, java.lang.Object obj) {
        if (handler == null) {
            return;
        }
        synchronized (this) {
            android.os.Message message = this.mMessages;
            while (message != null && message.target == handler && message.what == i && (obj == null || obj.equals(message.obj))) {
                android.os.Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                message = message2;
            }
            while (message != null) {
                android.os.Message message3 = message.next;
                if (message3 != null && message3.target == handler && message3.what == i && (obj == null || obj.equals(message3.obj))) {
                    android.os.Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0031, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void removeMessages(android.os.Handler handler, java.lang.Runnable runnable, java.lang.Object obj) {
        if (handler == null || runnable == null) {
            return;
        }
        synchronized (this) {
            android.os.Message message = this.mMessages;
            while (message != null && message.target == handler && message.callback == runnable && (obj == null || message.obj == obj)) {
                android.os.Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                message = message2;
            }
            while (message != null) {
                android.os.Message message3 = message.next;
                if (message3 != null && message3.target == handler && message3.callback == runnable && (obj == null || message3.obj == obj)) {
                    android.os.Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0035, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void removeEqualMessages(android.os.Handler handler, java.lang.Runnable runnable, java.lang.Object obj) {
        if (handler == null || runnable == null) {
            return;
        }
        synchronized (this) {
            android.os.Message message = this.mMessages;
            while (message != null && message.target == handler && message.callback == runnable && (obj == null || obj.equals(message.obj))) {
                android.os.Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                message = message2;
            }
            while (message != null) {
                android.os.Message message3 = message.next;
                if (message3 != null && message3.target == handler && message3.callback == runnable && (obj == null || obj.equals(message3.obj))) {
                    android.os.Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002a, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void removeCallbacksAndMessages(android.os.Handler handler, java.lang.Object obj) {
        if (handler == null) {
            return;
        }
        synchronized (this) {
            android.os.Message message = this.mMessages;
            while (message != null && message.target == handler && (obj == null || message.obj == obj)) {
                android.os.Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                message = message2;
            }
            while (message != null) {
                android.os.Message message3 = message.next;
                if (message3 != null && message3.target == handler && (obj == null || message3.obj == obj)) {
                    android.os.Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002e, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void removeCallbacksAndEqualMessages(android.os.Handler handler, java.lang.Object obj) {
        if (handler == null) {
            return;
        }
        synchronized (this) {
            android.os.Message message = this.mMessages;
            while (message != null && message.target == handler && (obj == null || obj.equals(message.obj))) {
                android.os.Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                message = message2;
            }
            while (message != null) {
                android.os.Message message3 = message.next;
                if (message3 != null && message3.target == handler && (obj == null || obj.equals(message3.obj))) {
                    android.os.Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
    }

    private void removeAllMessagesLocked() {
        android.os.Message message = this.mMessages;
        while (message != null) {
            android.os.Message message2 = message.next;
            message.recycleUnchecked();
            message = message2;
        }
        this.mMessages = null;
        this.mLast = null;
        this.mAsyncMessageCount = 0;
    }

    private void removeAllFutureMessagesLocked() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.os.Message message = this.mMessages;
        if (message == null) {
            return;
        }
        if (message.when > uptimeMillis) {
            removeAllMessagesLocked();
            return;
        }
        while (true) {
            android.os.Message message2 = message.next;
            if (message2 == null) {
                return;
            }
            if (message2.when <= uptimeMillis) {
                message = message2;
            } else {
                message.next = null;
                this.mLast = message;
                while (true) {
                    android.os.Message message3 = message2.next;
                    if (message2.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message2.recycleUnchecked();
                    if (message3 == null) {
                        return;
                    } else {
                        message2 = message3;
                    }
                }
            }
        }
    }

    void dump(android.util.Printer printer, java.lang.String str, android.os.Handler handler) {
        synchronized (this) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            int i = 0;
            for (android.os.Message message = this.mMessages; message != null; message = message.next) {
                if (handler == null || handler == message.target) {
                    printer.println(str + "Message " + i + ": " + message.toString(uptimeMillis));
                }
                i++;
            }
            printer.println(str + "(Total messages: " + i + ", polling=" + isPollingLocked() + ", quitting=" + this.mQuitting + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        synchronized (this) {
            for (android.os.Message message = this.mMessages; message != null; message = message.next) {
                message.dumpDebug(protoOutputStream, 2246267895809L);
            }
            protoOutputStream.write(1133871366146L, isPollingLocked());
            protoOutputStream.write(1133871366147L, this.mQuitting);
        }
        protoOutputStream.end(start);
    }

    private static final class FileDescriptorRecord {
        public final java.io.FileDescriptor mDescriptor;
        public int mEvents;
        public android.os.MessageQueue.OnFileDescriptorEventListener mListener;
        public int mSeq;

        public FileDescriptorRecord(java.io.FileDescriptor fileDescriptor, int i, android.os.MessageQueue.OnFileDescriptorEventListener onFileDescriptorEventListener) {
            this.mDescriptor = fileDescriptor;
            this.mEvents = i;
            this.mListener = onFileDescriptorEventListener;
        }
    }
}
