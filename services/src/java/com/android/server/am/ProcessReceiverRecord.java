package com.android.server.am;

/* loaded from: classes.dex */
final class ProcessReceiverRecord {
    final com.android.server.am.ProcessRecord mApp;
    private int mCurReceiversSize;
    private final com.android.server.am.ActivityManagerService mService;
    private final android.util.ArraySet<com.android.server.am.BroadcastRecord> mCurReceivers = new android.util.ArraySet<>();
    private final android.util.ArraySet<com.android.server.am.ReceiverList> mReceivers = new android.util.ArraySet<>();

    int numberOfCurReceivers() {
        return this.mCurReceiversSize;
    }

    void incrementCurReceivers() {
        this.mCurReceiversSize++;
    }

    void decrementCurReceivers() {
        this.mCurReceiversSize--;
    }

    @java.lang.Deprecated
    com.android.server.am.BroadcastRecord getCurReceiverAt(int i) {
        return this.mCurReceivers.valueAt(i);
    }

    @java.lang.Deprecated
    boolean hasCurReceiver(com.android.server.am.BroadcastRecord broadcastRecord) {
        return this.mCurReceivers.contains(broadcastRecord);
    }

    @java.lang.Deprecated
    void addCurReceiver(com.android.server.am.BroadcastRecord broadcastRecord) {
        this.mCurReceivers.add(broadcastRecord);
        this.mCurReceiversSize = this.mCurReceivers.size();
    }

    @java.lang.Deprecated
    void removeCurReceiver(com.android.server.am.BroadcastRecord broadcastRecord) {
        this.mCurReceivers.remove(broadcastRecord);
        this.mCurReceiversSize = this.mCurReceivers.size();
    }

    int numberOfReceivers() {
        return this.mReceivers.size();
    }

    com.android.server.am.ReceiverList getReceiverAt(int i) {
        return this.mReceivers.valueAt(i);
    }

    void addReceiver(com.android.server.am.ReceiverList receiverList) {
        this.mReceivers.add(receiverList);
    }

    void removeReceiver(com.android.server.am.ReceiverList receiverList) {
        this.mReceivers.remove(receiverList);
    }

    ProcessReceiverRecord(com.android.server.am.ProcessRecord processRecord) {
        this.mApp = processRecord;
        this.mService = processRecord.mService;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void onCleanupApplicationRecordLocked() {
        for (int size = this.mReceivers.size() - 1; size >= 0; size--) {
            this.mService.removeReceiverLocked(this.mReceivers.valueAt(size));
        }
        this.mReceivers.clear();
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str, long j) {
        if (!this.mCurReceivers.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Current mReceivers:");
            int size = this.mCurReceivers.size();
            for (int i = 0; i < size; i++) {
                printWriter.print(str);
                printWriter.print("  - ");
                printWriter.println(this.mCurReceivers.valueAt(i));
            }
        }
        if (this.mReceivers.size() > 0) {
            printWriter.print(str);
            printWriter.println("mReceivers:");
            int size2 = this.mReceivers.size();
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print(str);
                printWriter.print("  - ");
                printWriter.println(this.mReceivers.valueAt(i2));
            }
        }
    }
}
