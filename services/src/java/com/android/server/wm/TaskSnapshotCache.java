package com.android.server.wm;

/* loaded from: classes3.dex */
class TaskSnapshotCache extends com.android.server.wm.SnapshotCache<com.android.server.wm.Task> {
    private final com.android.server.wm.AppSnapshotLoader mLoader;

    TaskSnapshotCache(com.android.server.wm.AppSnapshotLoader appSnapshotLoader) {
        super("Task");
        this.mLoader = appSnapshotLoader;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.SnapshotCache
    public void putSnapshot(com.android.server.wm.Task task, android.window.TaskSnapshot taskSnapshot) {
        synchronized (this.mLock) {
            try {
                com.android.server.wm.SnapshotCache.CacheEntry cacheEntry = this.mRunningCache.get(java.lang.Integer.valueOf(task.mTaskId));
                if (cacheEntry != null) {
                    this.mAppIdMap.remove(cacheEntry.topApp);
                }
                com.android.server.wm.ActivityRecord topMostActivity = task.getTopMostActivity();
                this.mAppIdMap.put(topMostActivity, java.lang.Integer.valueOf(task.mTaskId));
                this.mRunningCache.put(java.lang.Integer.valueOf(task.mTaskId), new com.android.server.wm.SnapshotCache.CacheEntry(taskSnapshot, topMostActivity));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    android.window.TaskSnapshot getSnapshot(int i, int i2, boolean z, boolean z2) {
        android.window.TaskSnapshot snapshot = getSnapshot(java.lang.Integer.valueOf(i));
        if (snapshot != null) {
            return snapshot;
        }
        if (!z) {
            return null;
        }
        return tryRestoreFromDisk(i, i2, z2);
    }

    private android.window.TaskSnapshot tryRestoreFromDisk(int i, int i2, boolean z) {
        return this.mLoader.loadTask(i, i2, z);
    }
}
