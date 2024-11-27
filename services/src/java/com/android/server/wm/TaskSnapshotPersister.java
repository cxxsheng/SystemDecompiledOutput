package com.android.server.wm;

/* loaded from: classes3.dex */
class TaskSnapshotPersister extends com.android.server.wm.BaseAppSnapshotPersister {

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<java.lang.Integer> mPersistedTaskIdsSinceLastRemoveObsolete;

    TaskSnapshotPersister(com.android.server.wm.SnapshotPersistQueue snapshotPersistQueue, com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
        super(snapshotPersistQueue, persistInfoProvider);
        this.mPersistedTaskIdsSinceLastRemoveObsolete = new android.util.ArraySet<>();
    }

    @Override // com.android.server.wm.BaseAppSnapshotPersister
    void persistSnapshot(int i, int i2, android.window.TaskSnapshot taskSnapshot) {
        synchronized (this.mLock) {
            this.mPersistedTaskIdsSinceLastRemoveObsolete.add(java.lang.Integer.valueOf(i));
            super.persistSnapshot(i, i2, taskSnapshot);
        }
    }

    @Override // com.android.server.wm.BaseAppSnapshotPersister
    void removeSnapshot(int i, int i2) {
        synchronized (this.mLock) {
            this.mPersistedTaskIdsSinceLastRemoveObsolete.remove(java.lang.Integer.valueOf(i));
            super.removeSnapshot(i, i2);
        }
    }

    void removeObsoleteFiles(android.util.ArraySet<java.lang.Integer> arraySet, int[] iArr) {
        if (iArr.length == 0) {
            return;
        }
        synchronized (this.mLock) {
            this.mPersistedTaskIdsSinceLastRemoveObsolete.clear();
            this.mSnapshotPersistQueue.sendToQueueLocked(new com.android.server.wm.TaskSnapshotPersister.RemoveObsoleteFilesQueueItem(arraySet, iArr, this.mPersistInfoProvider));
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class RemoveObsoleteFilesQueueItem extends com.android.server.wm.SnapshotPersistQueue.WriteQueueItem {
        private final android.util.ArraySet<java.lang.Integer> mPersistentTaskIds;
        private final int[] mRunningUserIds;

        @com.android.internal.annotations.VisibleForTesting
        RemoveObsoleteFilesQueueItem(android.util.ArraySet<java.lang.Integer> arraySet, int[] iArr, com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
            super(persistInfoProvider, iArr.length > 0 ? iArr[0] : 0);
            this.mPersistentTaskIds = new android.util.ArraySet<>((android.util.ArraySet) arraySet);
            this.mRunningUserIds = java.util.Arrays.copyOf(iArr, iArr.length);
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        boolean isReady(com.android.server.pm.UserManagerInternal userManagerInternal) {
            for (int length = this.mRunningUserIds.length - 1; length >= 0; length--) {
                if (!userManagerInternal.isUserUnlocked(this.mRunningUserIds[length])) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        void write() {
            android.util.ArraySet arraySet;
            android.os.Trace.traceBegin(32L, "RemoveObsoleteFilesQueueItem");
            synchronized (com.android.server.wm.TaskSnapshotPersister.this.mLock) {
                arraySet = new android.util.ArraySet(com.android.server.wm.TaskSnapshotPersister.this.mPersistedTaskIdsSinceLastRemoveObsolete);
            }
            for (int i : this.mRunningUserIds) {
                java.io.File directory = this.mPersistInfoProvider.getDirectory(i);
                java.lang.String[] list = directory.list();
                if (list != null) {
                    for (java.lang.String str : list) {
                        int taskId = getTaskId(str);
                        if (!this.mPersistentTaskIds.contains(java.lang.Integer.valueOf(taskId)) && !arraySet.contains(java.lang.Integer.valueOf(taskId))) {
                            new java.io.File(directory, str).delete();
                        }
                    }
                }
            }
            android.os.Trace.traceEnd(32L);
        }

        @com.android.internal.annotations.VisibleForTesting
        int getTaskId(java.lang.String str) {
            int lastIndexOf;
            if ((!str.endsWith(".proto") && !str.endsWith(".jpg")) || (lastIndexOf = str.lastIndexOf(46)) == -1) {
                return -1;
            }
            java.lang.String substring = str.substring(0, lastIndexOf);
            if (substring.endsWith("_reduced")) {
                substring = substring.substring(0, substring.length() - "_reduced".length());
            }
            try {
                return java.lang.Integer.parseInt(substring);
            } catch (java.lang.NumberFormatException e) {
                return -1;
            }
        }
    }
}
