package com.android.server.wm;

/* loaded from: classes3.dex */
class MirrorActiveUids {
    private final android.util.SparseIntArray mUidStates = new android.util.SparseIntArray();
    private final android.util.SparseIntArray mNumNonAppVisibleWindowMap = new android.util.SparseIntArray();

    MirrorActiveUids() {
    }

    synchronized void onUidActive(int i, int i2) {
        this.mUidStates.put(i, i2);
    }

    synchronized void onUidInactive(int i) {
        this.mUidStates.delete(i);
    }

    synchronized void onUidProcStateChanged(int i, int i2) {
        int indexOfKey = this.mUidStates.indexOfKey(i);
        if (indexOfKey >= 0) {
            this.mUidStates.setValueAt(indexOfKey, i2);
        }
    }

    synchronized int getUidState(int i) {
        return this.mUidStates.get(i, 20);
    }

    synchronized void onNonAppSurfaceVisibilityChanged(int i, boolean z) {
        try {
            int indexOfKey = this.mNumNonAppVisibleWindowMap.indexOfKey(i);
            int i2 = 1;
            if (indexOfKey >= 0) {
                int valueAt = this.mNumNonAppVisibleWindowMap.valueAt(indexOfKey);
                if (!z) {
                    i2 = -1;
                }
                int i3 = valueAt + i2;
                if (i3 > 0) {
                    this.mNumNonAppVisibleWindowMap.setValueAt(indexOfKey, i3);
                } else {
                    this.mNumNonAppVisibleWindowMap.removeAt(indexOfKey);
                }
            } else if (z) {
                this.mNumNonAppVisibleWindowMap.append(i, 1);
            }
        } finally {
        }
    }

    synchronized boolean hasNonAppVisibleWindow(int i) {
        return this.mNumNonAppVisibleWindowMap.get(i) > 0;
    }

    synchronized void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        try {
            printWriter.print(str + "NumNonAppVisibleWindowUidMap:[");
            for (int size = this.mNumNonAppVisibleWindowMap.size() + (-1); size >= 0; size += -1) {
                printWriter.print(" " + this.mNumNonAppVisibleWindowMap.keyAt(size) + ":" + this.mNumNonAppVisibleWindowMap.valueAt(size));
            }
            printWriter.println("]");
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }
}
