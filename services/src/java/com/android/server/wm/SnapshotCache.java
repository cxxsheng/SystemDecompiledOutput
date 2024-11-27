package com.android.server.wm;

/* loaded from: classes3.dex */
abstract class SnapshotCache<TYPE extends com.android.server.wm.WindowContainer> {
    protected final java.lang.String mName;
    protected final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final android.util.ArrayMap<com.android.server.wm.ActivityRecord, java.lang.Integer> mAppIdMap = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final android.util.ArrayMap<java.lang.Integer, com.android.server.wm.SnapshotCache.CacheEntry> mRunningCache = new android.util.ArrayMap<>();

    abstract void putSnapshot(TYPE type, android.window.TaskSnapshot taskSnapshot);

    SnapshotCache(java.lang.String str) {
        this.mName = str;
    }

    void clearRunningCache() {
        synchronized (this.mLock) {
            this.mRunningCache.clear();
        }
    }

    @android.annotation.Nullable
    final android.window.TaskSnapshot getSnapshot(java.lang.Integer num) {
        synchronized (this.mLock) {
            try {
                com.android.server.wm.SnapshotCache.CacheEntry cacheEntry = this.mRunningCache.get(num);
                if (cacheEntry != null) {
                    return cacheEntry.snapshot;
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onAppRemoved(com.android.server.wm.ActivityRecord activityRecord) {
        synchronized (this.mLock) {
            try {
                java.lang.Integer num = this.mAppIdMap.get(activityRecord);
                if (num != null) {
                    removeRunningEntry(num);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onAppDied(com.android.server.wm.ActivityRecord activityRecord) {
        synchronized (this.mLock) {
            try {
                java.lang.Integer num = this.mAppIdMap.get(activityRecord);
                if (num != null) {
                    removeRunningEntry(num);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onIdRemoved(java.lang.Integer num) {
        removeRunningEntry(num);
    }

    void removeRunningEntry(java.lang.Integer num) {
        synchronized (this.mLock) {
            try {
                com.android.server.wm.SnapshotCache.CacheEntry cacheEntry = this.mRunningCache.get(num);
                if (cacheEntry != null) {
                    this.mAppIdMap.remove(cacheEntry.topApp);
                    this.mRunningCache.remove(num);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        java.lang.String str2 = str + "  ";
        java.lang.String str3 = str2 + "  ";
        printWriter.println(str + "SnapshotCache " + this.mName);
        synchronized (this.mLock) {
            try {
                for (int size = this.mRunningCache.size() - 1; size >= 0; size += -1) {
                    com.android.server.wm.SnapshotCache.CacheEntry valueAt = this.mRunningCache.valueAt(size);
                    printWriter.println(str2 + "Entry token=" + this.mRunningCache.keyAt(size));
                    printWriter.println(str3 + "topApp=" + valueAt.topApp);
                    printWriter.println(str3 + "snapshot=" + valueAt.snapshot);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    static final class CacheEntry {
        final android.window.TaskSnapshot snapshot;
        final com.android.server.wm.ActivityRecord topApp;

        CacheEntry(android.window.TaskSnapshot taskSnapshot, com.android.server.wm.ActivityRecord activityRecord) {
            this.snapshot = taskSnapshot;
            this.topApp = activityRecord;
        }
    }
}
