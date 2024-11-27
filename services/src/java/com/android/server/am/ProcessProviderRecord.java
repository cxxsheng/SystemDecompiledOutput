package com.android.server.am;

/* loaded from: classes.dex */
final class ProcessProviderRecord {
    final com.android.server.am.ProcessRecord mApp;
    private final com.android.server.am.ActivityManagerService mService;
    private long mLastProviderTime = Long.MIN_VALUE;
    private final android.util.ArrayMap<java.lang.String, com.android.server.am.ContentProviderRecord> mPubProviders = new android.util.ArrayMap<>();
    private final java.util.ArrayList<com.android.server.am.ContentProviderConnection> mConProviders = new java.util.ArrayList<>();

    long getLastProviderTime() {
        return this.mLastProviderTime;
    }

    void setLastProviderTime(long j) {
        this.mLastProviderTime = j;
    }

    boolean hasProvider(java.lang.String str) {
        return this.mPubProviders.containsKey(str);
    }

    com.android.server.am.ContentProviderRecord getProvider(java.lang.String str) {
        return this.mPubProviders.get(str);
    }

    int numberOfProviders() {
        return this.mPubProviders.size();
    }

    com.android.server.am.ContentProviderRecord getProviderAt(int i) {
        return this.mPubProviders.valueAt(i);
    }

    void installProvider(java.lang.String str, com.android.server.am.ContentProviderRecord contentProviderRecord) {
        this.mPubProviders.put(str, contentProviderRecord);
    }

    void removeProvider(java.lang.String str) {
        this.mPubProviders.remove(str);
    }

    void ensureProviderCapacity(int i) {
        this.mPubProviders.ensureCapacity(i);
    }

    int numberOfProviderConnections() {
        return this.mConProviders.size();
    }

    com.android.server.am.ContentProviderConnection getProviderConnectionAt(int i) {
        return this.mConProviders.get(i);
    }

    void addProviderConnection(com.android.server.am.ContentProviderConnection contentProviderConnection) {
        this.mConProviders.add(contentProviderConnection);
    }

    boolean removeProviderConnection(com.android.server.am.ContentProviderConnection contentProviderConnection) {
        return this.mConProviders.remove(contentProviderConnection);
    }

    ProcessProviderRecord(com.android.server.am.ProcessRecord processRecord) {
        this.mApp = processRecord;
        this.mService = processRecord.mService;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean onCleanupApplicationRecordLocked(boolean z) {
        boolean z2 = false;
        for (int size = this.mPubProviders.size() - 1; size >= 0; size--) {
            com.android.server.am.ContentProviderRecord valueAt = this.mPubProviders.valueAt(size);
            if (valueAt.proc == this.mApp) {
                boolean z3 = this.mApp.mErrorState.isBad() || !z;
                boolean removeDyingProviderLocked = this.mService.mCpHelper.removeDyingProviderLocked(this.mApp, valueAt, z3);
                if (!z3 && removeDyingProviderLocked && valueAt.hasConnectionOrHandle()) {
                    z2 = true;
                }
                valueAt.provider = null;
                valueAt.setProcess(null);
            }
        }
        this.mPubProviders.clear();
        if (this.mService.mCpHelper.cleanupAppInLaunchingProvidersLocked(this.mApp, false)) {
            this.mService.mProcessList.noteProcessDiedLocked(this.mApp);
            z2 = true;
        }
        if (!this.mConProviders.isEmpty()) {
            for (int size2 = this.mConProviders.size() - 1; size2 >= 0; size2--) {
                com.android.server.am.ContentProviderConnection contentProviderConnection = this.mConProviders.get(size2);
                contentProviderConnection.provider.connections.remove(contentProviderConnection);
                this.mService.stopAssociationLocked(this.mApp.uid, this.mApp.processName, contentProviderConnection.provider.uid, contentProviderConnection.provider.appInfo.longVersionCode, contentProviderConnection.provider.name, contentProviderConnection.provider.info.processName);
            }
            this.mConProviders.clear();
        }
        return z2;
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str, long j) {
        if (this.mLastProviderTime > 0) {
            printWriter.print(str);
            printWriter.print("lastProviderTime=");
            android.util.TimeUtils.formatDuration(this.mLastProviderTime, j, printWriter);
            printWriter.println();
        }
        if (this.mPubProviders.size() > 0) {
            printWriter.print(str);
            printWriter.println("Published Providers:");
            int size = this.mPubProviders.size();
            for (int i = 0; i < size; i++) {
                printWriter.print(str);
                printWriter.print("  - ");
                printWriter.println(this.mPubProviders.keyAt(i));
                printWriter.print(str);
                printWriter.print("    -> ");
                printWriter.println(this.mPubProviders.valueAt(i));
            }
        }
        if (this.mConProviders.size() > 0) {
            printWriter.print(str);
            printWriter.println("Connected Providers:");
            int size2 = this.mConProviders.size();
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print(str);
                printWriter.print("  - ");
                printWriter.println(this.mConProviders.get(i2).toShortString());
            }
        }
    }
}
