package com.android.server.wm;

/* loaded from: classes3.dex */
class PersisterQueue {
    private static final boolean DEBUG = false;
    static final com.android.server.wm.PersisterQueue.WriteQueueItem EMPTY_ITEM = new com.android.server.wm.PersisterQueue.WriteQueueItem() { // from class: com.android.server.wm.PersisterQueue$$ExternalSyntheticLambda0
        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void process() {
            com.android.server.wm.PersisterQueue.lambda$static$0();
        }
    };
    private static final long FLUSH_QUEUE = -1;
    private static final long INTER_WRITE_DELAY_MS = 500;
    private static final int MAX_WRITE_QUEUE_LENGTH = 6;
    private static final long PRE_TASK_DELAY_MS = 3000;
    private static final java.lang.String TAG = "PersisterQueue";
    private final long mInterWriteDelayMs;
    private final com.android.server.wm.PersisterQueue.LazyTaskWriterThread mLazyTaskWriterThread;
    private final java.util.ArrayList<com.android.server.wm.PersisterQueue.Listener> mListeners;
    private long mNextWriteTime;
    private final long mPreTaskDelayMs;
    private final java.util.ArrayList<com.android.server.wm.PersisterQueue.WriteQueueItem> mWriteQueue;

    interface Listener {
        void onPreProcessItem(boolean z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$static$0() {
    }

    PersisterQueue() {
        this(500L, 3000L);
    }

    @com.android.internal.annotations.VisibleForTesting
    PersisterQueue(long j, long j2) {
        this.mWriteQueue = new java.util.ArrayList<>();
        this.mListeners = new java.util.ArrayList<>();
        this.mNextWriteTime = 0L;
        if (j < 0 || j2 < 0) {
            throw new java.lang.IllegalArgumentException("Both inter-write delay and pre-task delay need tobe non-negative. inter-write delay: " + j + "ms pre-task delay: " + j2);
        }
        this.mInterWriteDelayMs = j;
        this.mPreTaskDelayMs = j2;
        this.mLazyTaskWriterThread = new com.android.server.wm.PersisterQueue.LazyTaskWriterThread("LazyTaskWriterThread");
    }

    synchronized void startPersisting() {
        if (!this.mLazyTaskWriterThread.isAlive()) {
            this.mLazyTaskWriterThread.start();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void stopPersisting() throws java.lang.InterruptedException {
        if (!this.mLazyTaskWriterThread.isAlive()) {
            return;
        }
        synchronized (this) {
            this.mLazyTaskWriterThread.interrupt();
        }
        this.mLazyTaskWriterThread.join();
    }

    synchronized void addItem(com.android.server.wm.PersisterQueue.WriteQueueItem writeQueueItem, boolean z) {
        try {
            this.mWriteQueue.add(writeQueueItem);
            if (z || this.mWriteQueue.size() > 6) {
                this.mNextWriteTime = -1L;
            } else if (this.mNextWriteTime == 0) {
                this.mNextWriteTime = android.os.SystemClock.uptimeMillis() + this.mPreTaskDelayMs;
            }
            notify();
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    synchronized <T extends com.android.server.wm.PersisterQueue.WriteQueueItem> T findLastItem(java.util.function.Predicate<T> predicate, java.lang.Class<T> cls) {
        for (int size = this.mWriteQueue.size() - 1; size >= 0; size--) {
            com.android.server.wm.PersisterQueue.WriteQueueItem writeQueueItem = this.mWriteQueue.get(size);
            if (cls.isInstance(writeQueueItem)) {
                T cast = cls.cast(writeQueueItem);
                if (predicate.test(cast)) {
                    return cast;
                }
            }
        }
        return null;
    }

    synchronized <T extends com.android.server.wm.PersisterQueue.WriteQueueItem> void updateLastOrAddItem(final T t, boolean z) {
        try {
            java.util.Objects.requireNonNull(t);
            com.android.server.wm.PersisterQueue.WriteQueueItem findLastItem = findLastItem(new java.util.function.Predicate() { // from class: com.android.server.wm.PersisterQueue$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return com.android.server.wm.PersisterQueue.WriteQueueItem.this.matches((com.android.server.wm.PersisterQueue.WriteQueueItem) obj);
                }
            }, t.getClass());
            if (findLastItem == null) {
                addItem(t, z);
            } else {
                findLastItem.updateFrom(t);
            }
            yieldIfQueueTooDeep();
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    synchronized <T extends com.android.server.wm.PersisterQueue.WriteQueueItem> void removeItems(java.util.function.Predicate<T> predicate, java.lang.Class<T> cls) {
        for (int size = this.mWriteQueue.size() - 1; size >= 0; size--) {
            com.android.server.wm.PersisterQueue.WriteQueueItem writeQueueItem = this.mWriteQueue.get(size);
            if (cls.isInstance(writeQueueItem) && predicate.test(cls.cast(writeQueueItem))) {
                this.mWriteQueue.remove(size);
            }
        }
    }

    synchronized void flush() {
        this.mNextWriteTime = -1L;
        notifyAll();
        do {
            try {
                wait();
            } catch (java.lang.InterruptedException e) {
            }
        } while (this.mNextWriteTime == -1);
    }

    void yieldIfQueueTooDeep() {
        boolean z;
        synchronized (this) {
            if (this.mNextWriteTime != -1) {
                z = false;
            } else {
                z = true;
            }
        }
        if (z) {
            java.lang.Thread.yield();
        }
    }

    void addListener(com.android.server.wm.PersisterQueue.Listener listener) {
        this.mListeners.add(listener);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean removeListener(com.android.server.wm.PersisterQueue.Listener listener) {
        return this.mListeners.remove(listener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processNextItem() throws java.lang.InterruptedException {
        com.android.server.wm.PersisterQueue.WriteQueueItem remove;
        synchronized (this) {
            try {
                if (this.mNextWriteTime != -1) {
                    this.mNextWriteTime = android.os.SystemClock.uptimeMillis() + this.mInterWriteDelayMs;
                }
                while (this.mWriteQueue.isEmpty()) {
                    if (this.mNextWriteTime != 0) {
                        this.mNextWriteTime = 0L;
                        notify();
                    }
                    if (java.lang.Thread.currentThread().isInterrupted()) {
                        throw new java.lang.InterruptedException();
                    }
                    wait();
                }
                remove = this.mWriteQueue.remove(0);
                for (long uptimeMillis = android.os.SystemClock.uptimeMillis(); uptimeMillis < this.mNextWriteTime; uptimeMillis = android.os.SystemClock.uptimeMillis()) {
                    wait(this.mNextWriteTime - uptimeMillis);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        remove.process();
    }

    interface WriteQueueItem<T extends com.android.server.wm.PersisterQueue.WriteQueueItem<T>> {
        void process();

        default void updateFrom(T t) {
        }

        default boolean matches(T t) {
            return false;
        }
    }

    private class LazyTaskWriterThread extends java.lang.Thread {
        private LazyTaskWriterThread(java.lang.String str) {
            super(str);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            boolean isEmpty;
            android.os.Process.setThreadPriority(10);
            while (true) {
                try {
                    synchronized (com.android.server.wm.PersisterQueue.this) {
                        isEmpty = com.android.server.wm.PersisterQueue.this.mWriteQueue.isEmpty();
                    }
                    for (int size = com.android.server.wm.PersisterQueue.this.mListeners.size() - 1; size >= 0; size--) {
                        ((com.android.server.wm.PersisterQueue.Listener) com.android.server.wm.PersisterQueue.this.mListeners.get(size)).onPreProcessItem(isEmpty);
                    }
                    com.android.server.wm.PersisterQueue.this.processNextItem();
                } catch (java.lang.InterruptedException e) {
                    android.util.Slog.e(com.android.server.wm.PersisterQueue.TAG, "Persister thread is exiting. Should never happen in prod, butit's OK in tests.");
                    return;
                }
            }
        }
    }
}
