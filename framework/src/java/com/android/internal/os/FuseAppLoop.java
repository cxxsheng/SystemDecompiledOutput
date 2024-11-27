package com.android.internal.os;

/* loaded from: classes4.dex */
public class FuseAppLoop implements android.os.Handler.Callback {
    private static final int ARGS_POOL_SIZE = 50;
    private static final int FUSE_FSYNC = 20;
    private static final int FUSE_GETATTR = 3;
    private static final int FUSE_LOOKUP = 1;
    private static final int FUSE_MAX_WRITE = 131072;
    private static final int FUSE_OK = 0;
    private static final int FUSE_OPEN = 14;
    private static final int FUSE_READ = 15;
    private static final int FUSE_RELEASE = 18;
    private static final int FUSE_WRITE = 16;
    private static final int MIN_INODE = 2;
    public static final int ROOT_INODE = 1;
    private long mInstance;
    private final int mMountPointId;
    private final java.lang.Thread mThread;
    private static final java.lang.String TAG = "FuseAppLoop";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final java.util.concurrent.ThreadFactory sDefaultThreadFactory = new java.util.concurrent.ThreadFactory() { // from class: com.android.internal.os.FuseAppLoop.1
        @Override // java.util.concurrent.ThreadFactory
        public java.lang.Thread newThread(java.lang.Runnable runnable) {
            return new java.lang.Thread(runnable, com.android.internal.os.FuseAppLoop.TAG);
        }
    };
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.SparseArray<com.android.internal.os.FuseAppLoop.CallbackEntry> mCallbackMap = new android.util.SparseArray<>();
    private final com.android.internal.os.FuseAppLoop.BytesMap mBytesMap = new com.android.internal.os.FuseAppLoop.BytesMap();
    private final java.util.LinkedList<com.android.internal.os.FuseAppLoop.Args> mArgsPool = new java.util.LinkedList<>();
    private int mNextInode = 2;

    public static class UnmountedException extends java.lang.Exception {
    }

    native void native_delete(long j);

    native long native_new(int i);

    native void native_replyGetAttr(long j, long j2, long j3, long j4);

    native void native_replyLookup(long j, long j2, long j3, long j4);

    native void native_replyOpen(long j, long j2, long j3);

    native void native_replyRead(long j, long j2, int i, byte[] bArr);

    native void native_replySimple(long j, long j2, int i);

    native void native_replyWrite(long j, long j2, int i);

    native void native_start(long j);

    public FuseAppLoop(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, java.util.concurrent.ThreadFactory threadFactory) {
        this.mMountPointId = i;
        threadFactory = threadFactory == null ? sDefaultThreadFactory : threadFactory;
        this.mInstance = native_new(parcelFileDescriptor.detachFd());
        this.mThread = threadFactory.newThread(new java.lang.Runnable() { // from class: com.android.internal.os.FuseAppLoop$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.os.FuseAppLoop.this.lambda$new$0();
            }
        });
        this.mThread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        native_start(this.mInstance);
        synchronized (this.mLock) {
            native_delete(this.mInstance);
            this.mInstance = 0L;
            this.mBytesMap.clear();
        }
    }

    public int registerCallback(android.os.ProxyFileDescriptorCallback proxyFileDescriptorCallback, android.os.Handler handler) throws com.android.internal.os.FuseUnavailableMountException {
        int i;
        synchronized (this.mLock) {
            java.util.Objects.requireNonNull(proxyFileDescriptorCallback);
            java.util.Objects.requireNonNull(handler);
            com.android.internal.util.Preconditions.checkState(this.mCallbackMap.size() < 2147483645, "Too many opened files.");
            com.android.internal.util.Preconditions.checkArgument(java.lang.Thread.currentThread().getId() != handler.getLooper().getThread().getId(), "Handler must be different from the current thread");
            if (this.mInstance == 0) {
                throw new com.android.internal.os.FuseUnavailableMountException(this.mMountPointId);
            }
            do {
                i = this.mNextInode;
                this.mNextInode++;
                if (this.mNextInode < 0) {
                    this.mNextInode = 2;
                }
            } while (this.mCallbackMap.get(i) != null);
            this.mCallbackMap.put(i, new com.android.internal.os.FuseAppLoop.CallbackEntry(proxyFileDescriptorCallback, new android.os.Handler(handler.getLooper(), this)));
        }
        return i;
    }

    public void unregisterCallback(int i) {
        synchronized (this.mLock) {
            this.mCallbackMap.remove(i);
        }
    }

    public int getMountPointId() {
        return this.mMountPointId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r14v7 */
    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        java.lang.Object obj;
        java.lang.Object obj2;
        com.android.internal.os.FuseAppLoop.Args args = (com.android.internal.os.FuseAppLoop.Args) message.obj;
        com.android.internal.os.FuseAppLoop.CallbackEntry callbackEntry = args.entry;
        long j = args.inode;
        long j2 = args.unique;
        int i = args.size;
        long j3 = args.offset;
        byte[] bArr = args.data;
        try {
            ?? r14 = 0;
            ?? r142 = 0;
            switch (message.what) {
                case 1:
                    long onGetSize = callbackEntry.callback.onGetSize();
                    java.lang.Object obj3 = this.mLock;
                    try {
                        synchronized (obj3) {
                            try {
                                if (this.mInstance == 0) {
                                    obj = obj3;
                                } else {
                                    obj = obj3;
                                    native_replyLookup(this.mInstance, j2, j, onGetSize);
                                }
                                recycleLocked(args);
                                return true;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                r14 = obj3;
                                throw th;
                            }
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                    }
                case 3:
                    long onGetSize2 = callbackEntry.callback.onGetSize();
                    java.lang.Object obj4 = this.mLock;
                    try {
                        synchronized (obj4) {
                            try {
                                if (this.mInstance == 0) {
                                    obj2 = obj4;
                                } else {
                                    obj2 = obj4;
                                    native_replyGetAttr(this.mInstance, j2, j, onGetSize2);
                                }
                                recycleLocked(args);
                                return true;
                            } catch (java.lang.Throwable th3) {
                                th = th3;
                                r142 = obj4;
                                throw th;
                            }
                        }
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                    }
                case 15:
                    int onRead = callbackEntry.callback.onRead(j3, i, bArr);
                    synchronized (this.mLock) {
                        if (this.mInstance != 0) {
                            native_replyRead(this.mInstance, j2, onRead, bArr);
                        }
                        recycleLocked(args);
                    }
                    return true;
                case 16:
                    int onWrite = callbackEntry.callback.onWrite(j3, i, bArr);
                    synchronized (this.mLock) {
                        if (this.mInstance != 0) {
                            native_replyWrite(this.mInstance, j2, onWrite);
                        }
                        recycleLocked(args);
                    }
                    return true;
                case 18:
                    callbackEntry.callback.onRelease();
                    synchronized (this.mLock) {
                        if (this.mInstance != 0) {
                            native_replySimple(this.mInstance, j2, 0);
                        }
                        this.mBytesMap.stopUsing(j);
                        recycleLocked(args);
                    }
                    return true;
                case 20:
                    callbackEntry.callback.onFsync();
                    synchronized (this.mLock) {
                        if (this.mInstance != 0) {
                            native_replySimple(this.mInstance, j2, 0);
                        }
                        recycleLocked(args);
                    }
                    return true;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown FUSE command: " + message.what);
            }
        } catch (java.lang.Exception e) {
            synchronized (this.mLock) {
                android.util.Log.e(TAG, "", e);
                replySimpleLocked(j2, getError(e));
                recycleLocked(args);
                return true;
            }
        }
    }

    private void onCommand(int i, long j, long j2, long j3, int i2, byte[] bArr) {
        com.android.internal.os.FuseAppLoop.Args pop;
        synchronized (this.mLock) {
            try {
                if (this.mArgsPool.size() == 0) {
                    pop = new com.android.internal.os.FuseAppLoop.Args();
                } else {
                    pop = this.mArgsPool.pop();
                }
                pop.unique = j;
                pop.inode = j2;
                pop.offset = j3;
                pop.size = i2;
                pop.data = bArr;
                pop.entry = getCallbackEntryOrThrowLocked(j2);
            } catch (java.lang.Exception e) {
                replySimpleLocked(j, getError(e));
            }
            if (!pop.entry.handler.sendMessage(android.os.Message.obtain(pop.entry.handler, i, 0, 0, pop))) {
                throw new android.system.ErrnoException("onCommand", android.system.OsConstants.EBADF);
            }
        }
    }

    private byte[] onOpen(long j, long j2) {
        com.android.internal.os.FuseAppLoop.CallbackEntry callbackEntryOrThrowLocked;
        synchronized (this.mLock) {
            try {
                callbackEntryOrThrowLocked = getCallbackEntryOrThrowLocked(j2);
            } catch (android.system.ErrnoException e) {
                replySimpleLocked(j, getError(e));
            }
            if (callbackEntryOrThrowLocked.opened) {
                throw new android.system.ErrnoException("onOpen", android.system.OsConstants.EMFILE);
            }
            if (this.mInstance != 0) {
                native_replyOpen(this.mInstance, j, j2);
                callbackEntryOrThrowLocked.opened = true;
                return this.mBytesMap.startUsing(j2);
            }
            return null;
        }
    }

    private static int getError(java.lang.Exception exc) {
        int i;
        if ((exc instanceof android.system.ErrnoException) && (i = ((android.system.ErrnoException) exc).errno) != android.system.OsConstants.ENOSYS) {
            return -i;
        }
        return -android.system.OsConstants.EBADF;
    }

    private com.android.internal.os.FuseAppLoop.CallbackEntry getCallbackEntryOrThrowLocked(long j) throws android.system.ErrnoException {
        com.android.internal.os.FuseAppLoop.CallbackEntry callbackEntry = this.mCallbackMap.get(checkInode(j));
        if (callbackEntry == null) {
            throw new android.system.ErrnoException("getCallbackEntryOrThrowLocked", android.system.OsConstants.ENOENT);
        }
        return callbackEntry;
    }

    private void recycleLocked(com.android.internal.os.FuseAppLoop.Args args) {
        if (this.mArgsPool.size() < 50) {
            this.mArgsPool.add(args);
        }
    }

    private void replySimpleLocked(long j, int i) {
        if (this.mInstance != 0) {
            native_replySimple(this.mInstance, j, i);
        }
    }

    private static int checkInode(long j) {
        com.android.internal.util.Preconditions.checkArgumentInRange(j, 2L, 2147483647L, "checkInode");
        return (int) j;
    }

    private static class CallbackEntry {
        final android.os.ProxyFileDescriptorCallback callback;
        final android.os.Handler handler;
        boolean opened;

        CallbackEntry(android.os.ProxyFileDescriptorCallback proxyFileDescriptorCallback, android.os.Handler handler) {
            this.callback = (android.os.ProxyFileDescriptorCallback) java.util.Objects.requireNonNull(proxyFileDescriptorCallback);
            this.handler = (android.os.Handler) java.util.Objects.requireNonNull(handler);
        }

        long getThreadId() {
            return this.handler.getLooper().getThread().getId();
        }
    }

    private static class BytesMapEntry {
        byte[] bytes;
        int counter;

        private BytesMapEntry() {
            this.counter = 0;
            this.bytes = new byte[131072];
        }
    }

    private static class BytesMap {
        final java.util.Map<java.lang.Long, com.android.internal.os.FuseAppLoop.BytesMapEntry> mEntries;

        private BytesMap() {
            this.mEntries = new java.util.HashMap();
        }

        byte[] startUsing(long j) {
            com.android.internal.os.FuseAppLoop.BytesMapEntry bytesMapEntry = this.mEntries.get(java.lang.Long.valueOf(j));
            if (bytesMapEntry == null) {
                bytesMapEntry = new com.android.internal.os.FuseAppLoop.BytesMapEntry();
                this.mEntries.put(java.lang.Long.valueOf(j), bytesMapEntry);
            }
            bytesMapEntry.counter++;
            return bytesMapEntry.bytes;
        }

        void stopUsing(long j) {
            com.android.internal.os.FuseAppLoop.BytesMapEntry bytesMapEntry = this.mEntries.get(java.lang.Long.valueOf(j));
            java.util.Objects.requireNonNull(bytesMapEntry);
            bytesMapEntry.counter--;
            if (bytesMapEntry.counter <= 0) {
                this.mEntries.remove(java.lang.Long.valueOf(j));
            }
        }

        void clear() {
            this.mEntries.clear();
        }
    }

    private static class Args {
        byte[] data;
        com.android.internal.os.FuseAppLoop.CallbackEntry entry;
        long inode;
        long offset;
        int size;
        long unique;

        private Args() {
        }
    }
}
