package com.android.server.am;

/* loaded from: classes.dex */
final class PendingStartActivityUids {
    public static final long INVALID_TIME = 0;
    static final java.lang.String TAG = "ActivityManager";
    private final android.util.SparseArray<android.util.Pair<java.lang.Integer, java.lang.Long>> mPendingUids = new android.util.SparseArray<>();

    PendingStartActivityUids() {
    }

    synchronized boolean add(int i, int i2) {
        if (this.mPendingUids.get(i) != null) {
            return false;
        }
        this.mPendingUids.put(i, new android.util.Pair<>(java.lang.Integer.valueOf(i2), java.lang.Long.valueOf(android.os.SystemClock.elapsedRealtime())));
        return true;
    }

    synchronized void delete(int i, long j) {
        android.util.Pair<java.lang.Integer, java.lang.Long> pair = this.mPendingUids.get(i);
        if (pair != null) {
            if (j < ((java.lang.Long) pair.second).longValue()) {
                android.util.Slog.i(TAG, "updateOomAdj start time is before than pendingPid added, don't delete it");
                return;
            }
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - ((java.lang.Long) pair.second).longValue();
            if (elapsedRealtime >= 1000) {
                android.util.Slog.i(TAG, "PendingStartActivityUids startActivity to updateOomAdj delay:" + elapsedRealtime + "ms, uid:" + i);
            }
            this.mPendingUids.delete(i);
        }
    }

    synchronized long getPendingTopPidTime(int i, int i2) {
        long j;
        android.util.Pair<java.lang.Integer, java.lang.Long> pair = this.mPendingUids.get(i);
        if (pair != null && ((java.lang.Integer) pair.first).intValue() == i2) {
            j = ((java.lang.Long) pair.second).longValue();
        } else {
            j = 0;
        }
        return j;
    }

    synchronized boolean isPendingTopUid(int i) {
        return this.mPendingUids.get(i) != null;
    }
}
