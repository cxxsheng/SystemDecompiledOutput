package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class PowerStatsUidResolver {
    private static final java.lang.String TAG = "PowerStatsUidResolver";
    private final android.util.SparseIntArray mIsolatedUids = new android.util.SparseIntArray();
    private final android.util.SparseIntArray mIsolatedUidRefCounts = new android.util.SparseIntArray();
    private volatile java.util.List<com.android.server.power.stats.PowerStatsUidResolver.Listener> mListeners = java.util.Collections.emptyList();

    public interface Listener {
        void onAfterIsolatedUidRemoved(int i, int i2);

        void onBeforeIsolatedUidRemoved(int i, int i2);

        void onIsolatedUidAdded(int i, int i2);
    }

    public void addListener(com.android.server.power.stats.PowerStatsUidResolver.Listener listener) {
        synchronized (this) {
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mListeners);
            arrayList.add(listener);
            this.mListeners = java.util.Collections.unmodifiableList(arrayList);
        }
    }

    public void removeListener(com.android.server.power.stats.PowerStatsUidResolver.Listener listener) {
        synchronized (this) {
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mListeners);
            arrayList.remove(listener);
            this.mListeners = java.util.Collections.unmodifiableList(arrayList);
        }
    }

    public void noteIsolatedUidAdded(int i, int i2) {
        synchronized (this) {
            this.mIsolatedUids.put(i, i2);
            this.mIsolatedUidRefCounts.put(i, 1);
        }
        java.util.List<com.android.server.power.stats.PowerStatsUidResolver.Listener> list = this.mListeners;
        for (int size = list.size() - 1; size >= 0; size--) {
            list.get(size).onIsolatedUidAdded(i, i2);
        }
    }

    public void noteIsolatedUidRemoved(int i, int i2) {
        synchronized (this) {
            try {
                int i3 = this.mIsolatedUids.get(i, -1);
                if (i3 != i2) {
                    android.util.Slog.wtf(TAG, "Attempt to remove an isolated UID " + i + " with the parent UID " + i2 + ". The registered parent UID is " + i3);
                    return;
                }
                java.util.List<com.android.server.power.stats.PowerStatsUidResolver.Listener> list = this.mListeners;
                for (int size = list.size() - 1; size >= 0; size--) {
                    list.get(size).onBeforeIsolatedUidRemoved(i, i2);
                }
                releaseIsolatedUid(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void retainIsolatedUid(int i) {
        synchronized (this) {
            try {
                int i2 = this.mIsolatedUidRefCounts.get(i, 0);
                if (i2 <= 0) {
                    android.util.Slog.w(TAG, "Attempted to increment ref counted of untracked isolated uid (" + i + ")");
                    return;
                }
                this.mIsolatedUidRefCounts.put(i, i2 + 1);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void releaseIsolatedUid(int i) {
        synchronized (this) {
            try {
                int i2 = this.mIsolatedUidRefCounts.get(i, 0) - 1;
                if (i2 > 0) {
                    this.mIsolatedUidRefCounts.put(i, i2);
                    return;
                }
                int indexOfKey = this.mIsolatedUids.indexOfKey(i);
                if (indexOfKey >= 0) {
                    int valueAt = this.mIsolatedUids.valueAt(indexOfKey);
                    this.mIsolatedUids.removeAt(indexOfKey);
                    this.mIsolatedUidRefCounts.delete(i);
                    java.util.List<com.android.server.power.stats.PowerStatsUidResolver.Listener> list = this.mListeners;
                    for (int size = list.size() - 1; size >= 0; size--) {
                        list.get(size).onAfterIsolatedUidRemoved(i, valueAt);
                    }
                    return;
                }
                android.util.Slog.w(TAG, "Attempted to remove untracked child uid (" + i + ")");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void releaseUidsInRange(int i, int i2) {
        synchronized (this) {
            try {
                int indexOfKey = this.mIsolatedUids.indexOfKey(i);
                int indexOfKey2 = this.mIsolatedUids.indexOfKey(i2);
                if (indexOfKey < 0) {
                    indexOfKey = ~indexOfKey;
                }
                if (indexOfKey2 < 0) {
                    indexOfKey2 = (~indexOfKey2) - 1;
                }
                if (indexOfKey > indexOfKey2) {
                    return;
                }
                android.util.IntArray intArray = new android.util.IntArray(indexOfKey2 - indexOfKey);
                while (indexOfKey <= indexOfKey2) {
                    intArray.add(this.mIsolatedUids.keyAt(indexOfKey));
                    indexOfKey++;
                }
                for (int size = intArray.size() - 1; size >= 0; size--) {
                    releaseIsolatedUid(intArray.get(size));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int mapUid(int i) {
        int i2;
        synchronized (this) {
            i2 = this.mIsolatedUids.get(i, i);
        }
        return i2;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("Currently mapped isolated uids:");
        synchronized (this) {
            try {
                int size = this.mIsolatedUids.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = this.mIsolatedUids.keyAt(i);
                    printWriter.println("  " + keyAt + "->" + this.mIsolatedUids.valueAt(i) + " (ref count = " + this.mIsolatedUidRefCounts.get(keyAt) + ")");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
