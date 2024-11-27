package com.android.server.wm;

/* loaded from: classes3.dex */
class BaseAppSnapshotPersister {
    static final java.lang.String BITMAP_EXTENSION = ".jpg";
    static final java.lang.String LOW_RES_FILE_POSTFIX = "_reduced";
    static final java.lang.String PROTO_EXTENSION = ".proto";
    protected final java.lang.Object mLock;
    protected final com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider mPersistInfoProvider;
    protected final com.android.server.wm.SnapshotPersistQueue mSnapshotPersistQueue;

    interface DirectoryResolver {
        java.io.File getSystemDirectoryForUser(int i);
    }

    BaseAppSnapshotPersister(com.android.server.wm.SnapshotPersistQueue snapshotPersistQueue, com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
        this.mSnapshotPersistQueue = snapshotPersistQueue;
        this.mPersistInfoProvider = persistInfoProvider;
        this.mLock = snapshotPersistQueue.getLock();
    }

    void persistSnapshot(int i, int i2, android.window.TaskSnapshot taskSnapshot) {
        synchronized (this.mLock) {
            this.mSnapshotPersistQueue.sendToQueueLocked(this.mSnapshotPersistQueue.createStoreWriteQueueItem(i, i2, taskSnapshot, this.mPersistInfoProvider));
        }
    }

    void removeSnapshot(int i, int i2) {
        synchronized (this.mLock) {
            this.mSnapshotPersistQueue.sendToQueueLocked(this.mSnapshotPersistQueue.createDeleteWriteQueueItem(i, i2, this.mPersistInfoProvider));
        }
    }

    static class PersistInfoProvider {
        private final java.lang.String mDirName;
        protected final com.android.server.wm.BaseAppSnapshotPersister.DirectoryResolver mDirectoryResolver;
        private final boolean mEnableLowResSnapshots;
        private final float mLowResScaleFactor;
        private final boolean mUse16BitFormat;

        PersistInfoProvider(com.android.server.wm.BaseAppSnapshotPersister.DirectoryResolver directoryResolver, java.lang.String str, boolean z, float f, boolean z2) {
            this.mDirectoryResolver = directoryResolver;
            this.mDirName = str;
            this.mEnableLowResSnapshots = z;
            this.mLowResScaleFactor = f;
            this.mUse16BitFormat = z2;
        }

        @android.annotation.NonNull
        java.io.File getDirectory(int i) {
            return new java.io.File(this.mDirectoryResolver.getSystemDirectoryForUser(i), this.mDirName);
        }

        boolean use16BitFormat() {
            return this.mUse16BitFormat;
        }

        boolean createDirectory(int i) {
            java.io.File directory = getDirectory(i);
            return directory.exists() || directory.mkdir();
        }

        java.io.File getProtoFile(int i, int i2) {
            return new java.io.File(getDirectory(i2), i + com.android.server.wm.BaseAppSnapshotPersister.PROTO_EXTENSION);
        }

        java.io.File getLowResolutionBitmapFile(int i, int i2) {
            return new java.io.File(getDirectory(i2), i + com.android.server.wm.BaseAppSnapshotPersister.LOW_RES_FILE_POSTFIX + com.android.server.wm.BaseAppSnapshotPersister.BITMAP_EXTENSION);
        }

        java.io.File getHighResolutionBitmapFile(int i, int i2) {
            return new java.io.File(getDirectory(i2), i + com.android.server.wm.BaseAppSnapshotPersister.BITMAP_EXTENSION);
        }

        boolean enableLowResSnapshots() {
            return this.mEnableLowResSnapshots;
        }

        float lowResScaleFactor() {
            return this.mLowResScaleFactor;
        }
    }
}
