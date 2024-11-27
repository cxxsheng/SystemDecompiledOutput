package com.android.server.am;

/* loaded from: classes.dex */
final class PendingTempAllowlists {
    private final android.util.SparseArray<com.android.server.am.ActivityManagerService.PendingTempAllowlist> mPendingTempAllowlist = new android.util.SparseArray<>();
    private com.android.server.am.ActivityManagerService mService;

    PendingTempAllowlists(com.android.server.am.ActivityManagerService activityManagerService) {
        this.mService = activityManagerService;
    }

    void put(int i, com.android.server.am.ActivityManagerService.PendingTempAllowlist pendingTempAllowlist) {
        synchronized (this.mPendingTempAllowlist) {
            this.mPendingTempAllowlist.put(i, pendingTempAllowlist);
        }
    }

    void removeAt(int i) {
        synchronized (this.mPendingTempAllowlist) {
            this.mPendingTempAllowlist.removeAt(i);
        }
    }

    com.android.server.am.ActivityManagerService.PendingTempAllowlist get(int i) {
        com.android.server.am.ActivityManagerService.PendingTempAllowlist pendingTempAllowlist;
        synchronized (this.mPendingTempAllowlist) {
            pendingTempAllowlist = this.mPendingTempAllowlist.get(i);
        }
        return pendingTempAllowlist;
    }

    int size() {
        int size;
        synchronized (this.mPendingTempAllowlist) {
            size = this.mPendingTempAllowlist.size();
        }
        return size;
    }

    com.android.server.am.ActivityManagerService.PendingTempAllowlist valueAt(int i) {
        com.android.server.am.ActivityManagerService.PendingTempAllowlist valueAt;
        synchronized (this.mPendingTempAllowlist) {
            valueAt = this.mPendingTempAllowlist.valueAt(i);
        }
        return valueAt;
    }

    int indexOfKey(int i) {
        int indexOfKey;
        synchronized (this.mPendingTempAllowlist) {
            indexOfKey = this.mPendingTempAllowlist.indexOfKey(i);
        }
        return indexOfKey;
    }
}
