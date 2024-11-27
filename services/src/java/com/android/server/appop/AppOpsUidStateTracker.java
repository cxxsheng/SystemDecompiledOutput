package com.android.server.appop;

/* loaded from: classes.dex */
interface AppOpsUidStateTracker {

    public interface UidStateChangedCallback {
        void onUidStateChanged(int i, int i2, boolean z);
    }

    void addUidStateChangedCallback(java.util.concurrent.Executor executor, com.android.server.appop.AppOpsUidStateTracker.UidStateChangedCallback uidStateChangedCallback);

    void dumpEvents(java.io.PrintWriter printWriter);

    void dumpUidState(java.io.PrintWriter printWriter, int i, long j);

    int evalMode(int i, int i2, int i3);

    int getUidState(int i);

    boolean isUidInForeground(int i);

    void removeUidStateChangedCallback(com.android.server.appop.AppOpsUidStateTracker.UidStateChangedCallback uidStateChangedCallback);

    void updateAppWidgetVisibility(android.util.SparseArray<java.lang.String> sparseArray, boolean z);

    void updateUidProcState(int i, int i2, int i3);

    static int processStateToUidState(int i) {
        if (i == -1) {
            return com.android.server.am.ProcessList.PREVIOUS_APP_ADJ;
        }
        if (i <= 1) {
            return 100;
        }
        if (i <= 2) {
            return 200;
        }
        if (i <= 3) {
            return 500;
        }
        if (i <= 4) {
            return 400;
        }
        if (i <= 5) {
            return 500;
        }
        if (i <= 11) {
            return 600;
        }
        return com.android.server.am.ProcessList.PREVIOUS_APP_ADJ;
    }
}
