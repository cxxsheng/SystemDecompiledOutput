package com.android.server.wm;

/* loaded from: classes3.dex */
class ActivitySnapshotCache extends com.android.server.wm.SnapshotCache<com.android.server.wm.ActivityRecord> {
    ActivitySnapshotCache() {
        super("Activity");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.SnapshotCache
    public void putSnapshot(com.android.server.wm.ActivityRecord activityRecord, android.window.TaskSnapshot taskSnapshot) {
        int identityHashCode = java.lang.System.identityHashCode(activityRecord);
        synchronized (this.mLock) {
            try {
                com.android.server.wm.SnapshotCache.CacheEntry cacheEntry = this.mRunningCache.get(java.lang.Integer.valueOf(identityHashCode));
                if (cacheEntry != null) {
                    this.mAppIdMap.remove(cacheEntry.topApp);
                }
                this.mAppIdMap.put(activityRecord, java.lang.Integer.valueOf(identityHashCode));
                this.mRunningCache.put(java.lang.Integer.valueOf(identityHashCode), new com.android.server.wm.SnapshotCache.CacheEntry(taskSnapshot, activityRecord));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
