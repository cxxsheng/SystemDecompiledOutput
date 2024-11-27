package com.android.server.wm;

/* loaded from: classes3.dex */
class SnapshotPersistQueue {
    private static final int COMPRESS_QUALITY = 95;
    private static final long DELAY_MS = 100;
    private static final int MAX_STORE_QUEUE_DEPTH = 2;
    private static final java.lang.String TAG = "WindowManager";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mPaused;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mQueueIdling;
    private boolean mStarted;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayDeque<com.android.server.wm.SnapshotPersistQueue.WriteQueueItem> mWriteQueue = new java.util.ArrayDeque<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayDeque<com.android.server.wm.SnapshotPersistQueue.StoreWriteQueueItem> mStoreQueueItems = new java.util.ArrayDeque<>();
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.lang.Thread mPersister = new java.lang.Thread("TaskSnapshotPersister") { // from class: com.android.server.wm.SnapshotPersistQueue.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            com.android.server.wm.SnapshotPersistQueue.WriteQueueItem writeQueueItem;
            boolean z;
            android.os.Process.setThreadPriority(10);
            while (true) {
                synchronized (com.android.server.wm.SnapshotPersistQueue.this.mLock) {
                    try {
                        if (com.android.server.wm.SnapshotPersistQueue.this.mPaused) {
                            writeQueueItem = null;
                            z = false;
                        } else {
                            writeQueueItem = (com.android.server.wm.SnapshotPersistQueue.WriteQueueItem) com.android.server.wm.SnapshotPersistQueue.this.mWriteQueue.poll();
                            if (writeQueueItem != null) {
                                if (writeQueueItem.isReady(com.android.server.wm.SnapshotPersistQueue.this.mUserManagerInternal)) {
                                    writeQueueItem.onDequeuedLocked();
                                    z = true;
                                } else {
                                    com.android.server.wm.SnapshotPersistQueue.this.mWriteQueue.addLast(writeQueueItem);
                                }
                            }
                            z = false;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (writeQueueItem != null) {
                    if (z) {
                        writeQueueItem.write();
                    }
                    android.os.SystemClock.sleep(com.android.server.wm.SnapshotPersistQueue.DELAY_MS);
                }
                synchronized (com.android.server.wm.SnapshotPersistQueue.this.mLock) {
                    boolean isEmpty = com.android.server.wm.SnapshotPersistQueue.this.mWriteQueue.isEmpty();
                    if (isEmpty || com.android.server.wm.SnapshotPersistQueue.this.mPaused) {
                        try {
                            com.android.server.wm.SnapshotPersistQueue.this.mQueueIdling = isEmpty;
                            com.android.server.wm.SnapshotPersistQueue.this.mLock.wait();
                            com.android.server.wm.SnapshotPersistQueue.this.mQueueIdling = false;
                        } catch (java.lang.InterruptedException e) {
                        }
                    }
                }
            }
        }
    };
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);

    SnapshotPersistQueue() {
    }

    java.lang.Object getLock() {
        return this.mLock;
    }

    void systemReady() {
        start();
    }

    void start() {
        if (!this.mStarted) {
            this.mStarted = true;
            this.mPersister.start();
        }
    }

    void setPaused(boolean z) {
        synchronized (this.mLock) {
            try {
                this.mPaused = z;
                if (!z) {
                    this.mLock.notifyAll();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void waitForQueueEmpty() {
        while (true) {
            synchronized (this.mLock) {
                try {
                    if (this.mWriteQueue.isEmpty() && this.mQueueIdling) {
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            android.os.SystemClock.sleep(DELAY_MS);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int peekQueueSize() {
        int size;
        synchronized (this.mLock) {
            size = this.mWriteQueue.size();
        }
        return size;
    }

    private void addToQueueInternal(com.android.server.wm.SnapshotPersistQueue.WriteQueueItem writeQueueItem, boolean z) {
        this.mWriteQueue.removeFirstOccurrence(writeQueueItem);
        if (z) {
            this.mWriteQueue.addFirst(writeQueueItem);
        } else {
            this.mWriteQueue.addLast(writeQueueItem);
        }
        writeQueueItem.onQueuedLocked();
        ensureStoreQueueDepthLocked();
        if (!this.mPaused) {
            this.mLock.notifyAll();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void sendToQueueLocked(com.android.server.wm.SnapshotPersistQueue.WriteQueueItem writeQueueItem) {
        addToQueueInternal(writeQueueItem, false);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void insertQueueAtFirstLocked(com.android.server.wm.SnapshotPersistQueue.WriteQueueItem writeQueueItem) {
        addToQueueInternal(writeQueueItem, true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void ensureStoreQueueDepthLocked() {
        while (this.mStoreQueueItems.size() > 2) {
            com.android.server.wm.SnapshotPersistQueue.StoreWriteQueueItem poll = this.mStoreQueueItems.poll();
            this.mWriteQueue.remove(poll);
            android.util.Slog.i(TAG, "Queue is too deep! Purged item with index=" + poll.mId);
        }
    }

    void deleteSnapshot(int i, int i2, com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
        java.io.File protoFile = persistInfoProvider.getProtoFile(i, i2);
        java.io.File lowResolutionBitmapFile = persistInfoProvider.getLowResolutionBitmapFile(i, i2);
        if (protoFile.exists()) {
            protoFile.delete();
        }
        if (lowResolutionBitmapFile.exists()) {
            lowResolutionBitmapFile.delete();
        }
        java.io.File highResolutionBitmapFile = persistInfoProvider.getHighResolutionBitmapFile(i, i2);
        if (highResolutionBitmapFile.exists()) {
            highResolutionBitmapFile.delete();
        }
    }

    static abstract class WriteQueueItem {
        protected final com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider mPersistInfoProvider;
        protected final int mUserId;

        abstract void write();

        WriteQueueItem(@android.annotation.NonNull com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider, int i) {
            this.mPersistInfoProvider = persistInfoProvider;
            this.mUserId = i;
        }

        boolean isReady(com.android.server.pm.UserManagerInternal userManagerInternal) {
            return userManagerInternal.isUserUnlocked(this.mUserId);
        }

        void onQueuedLocked() {
        }

        void onDequeuedLocked() {
        }
    }

    com.android.server.wm.SnapshotPersistQueue.StoreWriteQueueItem createStoreWriteQueueItem(int i, int i2, android.window.TaskSnapshot taskSnapshot, com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
        return new com.android.server.wm.SnapshotPersistQueue.StoreWriteQueueItem(i, i2, taskSnapshot, persistInfoProvider);
    }

    class StoreWriteQueueItem extends com.android.server.wm.SnapshotPersistQueue.WriteQueueItem {
        private final int mId;
        private final android.window.TaskSnapshot mSnapshot;

        StoreWriteQueueItem(int i, int i2, android.window.TaskSnapshot taskSnapshot, com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
            super(persistInfoProvider, i2);
            this.mId = i;
            this.mSnapshot = taskSnapshot;
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        @com.android.internal.annotations.GuardedBy({"mLock"})
        void onQueuedLocked() {
            com.android.server.wm.SnapshotPersistQueue.this.mStoreQueueItems.remove(this);
            com.android.server.wm.SnapshotPersistQueue.this.mStoreQueueItems.offer(this);
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        @com.android.internal.annotations.GuardedBy({"mLock"})
        void onDequeuedLocked() {
            com.android.server.wm.SnapshotPersistQueue.this.mStoreQueueItems.remove(this);
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        void write() {
            boolean z;
            if (android.os.Trace.isTagEnabled(32L)) {
                android.os.Trace.traceBegin(32L, "StoreWriteQueueItem#" + this.mId);
            }
            if (!this.mPersistInfoProvider.createDirectory(this.mUserId)) {
                android.util.Slog.e(com.android.server.wm.SnapshotPersistQueue.TAG, "Unable to create snapshot directory for user dir=" + this.mPersistInfoProvider.getDirectory(this.mUserId));
            }
            if (writeProto()) {
                z = false;
            } else {
                z = true;
            }
            if (writeBuffer() ? z : true) {
                com.android.server.wm.SnapshotPersistQueue.this.deleteSnapshot(this.mId, this.mUserId, this.mPersistInfoProvider);
            }
            android.os.Trace.traceEnd(32L);
        }

        boolean writeProto() {
            java.io.FileOutputStream fileOutputStream;
            com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto taskSnapshotProto = new com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto();
            taskSnapshotProto.orientation = this.mSnapshot.getOrientation();
            taskSnapshotProto.rotation = this.mSnapshot.getRotation();
            taskSnapshotProto.taskWidth = this.mSnapshot.getTaskSize().x;
            taskSnapshotProto.taskHeight = this.mSnapshot.getTaskSize().y;
            taskSnapshotProto.insetLeft = this.mSnapshot.getContentInsets().left;
            taskSnapshotProto.insetTop = this.mSnapshot.getContentInsets().top;
            taskSnapshotProto.insetRight = this.mSnapshot.getContentInsets().right;
            taskSnapshotProto.insetBottom = this.mSnapshot.getContentInsets().bottom;
            taskSnapshotProto.letterboxInsetLeft = this.mSnapshot.getLetterboxInsets().left;
            taskSnapshotProto.letterboxInsetTop = this.mSnapshot.getLetterboxInsets().top;
            taskSnapshotProto.letterboxInsetRight = this.mSnapshot.getLetterboxInsets().right;
            taskSnapshotProto.letterboxInsetBottom = this.mSnapshot.getLetterboxInsets().bottom;
            taskSnapshotProto.isRealSnapshot = this.mSnapshot.isRealSnapshot();
            taskSnapshotProto.windowingMode = this.mSnapshot.getWindowingMode();
            taskSnapshotProto.appearance = this.mSnapshot.getAppearance();
            taskSnapshotProto.isTranslucent = this.mSnapshot.isTranslucent();
            taskSnapshotProto.topActivityComponent = this.mSnapshot.getTopActivityComponent().flattenToString();
            taskSnapshotProto.id = this.mSnapshot.getId();
            byte[] byteArray = com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto.toByteArray(taskSnapshotProto);
            java.io.File protoFile = this.mPersistInfoProvider.getProtoFile(this.mId, this.mUserId);
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(protoFile);
            try {
                fileOutputStream = atomicFile.startWrite();
                try {
                    fileOutputStream.write(byteArray);
                    atomicFile.finishWrite(fileOutputStream);
                    return true;
                } catch (java.io.IOException e) {
                    e = e;
                    atomicFile.failWrite(fileOutputStream);
                    android.util.Slog.e(com.android.server.wm.SnapshotPersistQueue.TAG, "Unable to open " + protoFile + " for persisting. " + e);
                    return false;
                }
            } catch (java.io.IOException e2) {
                e = e2;
                fileOutputStream = null;
            }
        }

        boolean writeBuffer() {
            if (com.android.server.wm.AbsAppSnapshotController.isInvalidHardwareBuffer(this.mSnapshot.getHardwareBuffer())) {
                android.util.Slog.e(com.android.server.wm.SnapshotPersistQueue.TAG, "Invalid task snapshot hw buffer, taskId=" + this.mId);
                return false;
            }
            android.graphics.Bitmap wrapHardwareBuffer = android.graphics.Bitmap.wrapHardwareBuffer(this.mSnapshot.getHardwareBuffer(), this.mSnapshot.getColorSpace());
            if (wrapHardwareBuffer == null) {
                android.util.Slog.e(com.android.server.wm.SnapshotPersistQueue.TAG, "Invalid task snapshot hw bitmap");
                return false;
            }
            android.graphics.Bitmap copy = wrapHardwareBuffer.copy(android.graphics.Bitmap.Config.ARGB_8888, false);
            if (copy == null) {
                android.util.Slog.e(com.android.server.wm.SnapshotPersistQueue.TAG, "Bitmap conversion from (config=" + wrapHardwareBuffer.getConfig() + ", isMutable=" + wrapHardwareBuffer.isMutable() + ") to (config=ARGB_8888, isMutable=false) failed.");
                return false;
            }
            java.io.File highResolutionBitmapFile = this.mPersistInfoProvider.getHighResolutionBitmapFile(this.mId, this.mUserId);
            try {
                java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(highResolutionBitmapFile);
                copy.compress(android.graphics.Bitmap.CompressFormat.JPEG, 95, fileOutputStream);
                fileOutputStream.close();
                if (!this.mPersistInfoProvider.enableLowResSnapshots()) {
                    copy.recycle();
                    return true;
                }
                android.graphics.Bitmap createScaledBitmap = android.graphics.Bitmap.createScaledBitmap(copy, (int) (wrapHardwareBuffer.getWidth() * this.mPersistInfoProvider.lowResScaleFactor()), (int) (wrapHardwareBuffer.getHeight() * this.mPersistInfoProvider.lowResScaleFactor()), true);
                copy.recycle();
                java.io.File lowResolutionBitmapFile = this.mPersistInfoProvider.getLowResolutionBitmapFile(this.mId, this.mUserId);
                try {
                    java.io.FileOutputStream fileOutputStream2 = new java.io.FileOutputStream(lowResolutionBitmapFile);
                    createScaledBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 95, fileOutputStream2);
                    fileOutputStream2.close();
                    createScaledBitmap.recycle();
                    return true;
                } catch (java.io.IOException e) {
                    android.util.Slog.e(com.android.server.wm.SnapshotPersistQueue.TAG, "Unable to open " + lowResolutionBitmapFile + " for persisting.", e);
                    return false;
                }
            } catch (java.io.IOException e2) {
                android.util.Slog.e(com.android.server.wm.SnapshotPersistQueue.TAG, "Unable to open " + highResolutionBitmapFile + " for persisting.", e2);
                return false;
            }
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.server.wm.SnapshotPersistQueue.StoreWriteQueueItem storeWriteQueueItem = (com.android.server.wm.SnapshotPersistQueue.StoreWriteQueueItem) obj;
            return this.mId == storeWriteQueueItem.mId && this.mUserId == storeWriteQueueItem.mUserId && this.mPersistInfoProvider == storeWriteQueueItem.mPersistInfoProvider;
        }

        public java.lang.String toString() {
            return "StoreWriteQueueItem{ID=" + this.mId + ", UserId=" + this.mUserId + "}";
        }
    }

    com.android.server.wm.SnapshotPersistQueue.DeleteWriteQueueItem createDeleteWriteQueueItem(int i, int i2, com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
        return new com.android.server.wm.SnapshotPersistQueue.DeleteWriteQueueItem(i, i2, persistInfoProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DeleteWriteQueueItem extends com.android.server.wm.SnapshotPersistQueue.WriteQueueItem {
        private final int mId;

        DeleteWriteQueueItem(int i, int i2, com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
            super(persistInfoProvider, i2);
            this.mId = i;
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        void write() {
            android.os.Trace.traceBegin(32L, "DeleteWriteQueueItem");
            com.android.server.wm.SnapshotPersistQueue.this.deleteSnapshot(this.mId, this.mUserId, this.mPersistInfoProvider);
            android.os.Trace.traceEnd(32L);
        }

        public java.lang.String toString() {
            return "DeleteWriteQueueItem{ID=" + this.mId + ", UserId=" + this.mUserId + "}";
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        com.android.server.wm.SnapshotPersistQueue.WriteQueueItem[] writeQueueItemArr;
        synchronized (this.mLock) {
            writeQueueItemArr = (com.android.server.wm.SnapshotPersistQueue.WriteQueueItem[]) this.mWriteQueue.toArray(new com.android.server.wm.SnapshotPersistQueue.WriteQueueItem[0]);
        }
        if (writeQueueItemArr.length == 0) {
            return;
        }
        printWriter.println(str + "PersistQueue contains:");
        for (int length = writeQueueItemArr.length + (-1); length >= 0; length += -1) {
            printWriter.println(str + "  " + writeQueueItemArr[length] + "");
        }
    }
}
