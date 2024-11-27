package com.android.server.am;

/* loaded from: classes.dex */
final class ActiveUids {

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private final android.util.SparseArray<com.android.server.am.UidRecord> mActiveUids = new android.util.SparseArray<>();
    private final boolean mPostChangesToAtm;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    private final com.android.server.am.ActivityManagerService mService;

    ActiveUids(com.android.server.am.ActivityManagerService activityManagerService, boolean z) {
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService != null ? activityManagerService.mProcLock : null;
        this.mPostChangesToAtm = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void put(int i, com.android.server.am.UidRecord uidRecord) {
        this.mActiveUids.put(i, uidRecord);
        if (this.mPostChangesToAtm) {
            this.mService.mAtmInternal.onUidActive(i, uidRecord.getCurProcState());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void remove(int i) {
        this.mActiveUids.remove(i);
        if (this.mPostChangesToAtm) {
            this.mService.mAtmInternal.onUidInactive(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void clear() {
        this.mActiveUids.clear();
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.UidRecord get(int i) {
        return this.mActiveUids.get(i);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int size() {
        return this.mActiveUids.size();
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.UidRecord valueAt(int i) {
        return this.mActiveUids.valueAt(i);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int keyAt(int i) {
        return this.mActiveUids.keyAt(i);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int indexOfKey(int i) {
        return this.mActiveUids.indexOfKey(i);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean dump(final java.io.PrintWriter printWriter, java.lang.String str, int i, java.lang.String str2, boolean z) {
        boolean z2 = false;
        for (int i2 = 0; i2 < this.mActiveUids.size(); i2++) {
            com.android.server.am.UidRecord valueAt = this.mActiveUids.valueAt(i2);
            if (str == null || android.os.UserHandle.getAppId(valueAt.getUid()) == i) {
                if (!z2) {
                    if (z) {
                        printWriter.println();
                    }
                    printWriter.print("  ");
                    printWriter.println(str2);
                    z2 = true;
                }
                printWriter.print("    UID ");
                android.os.UserHandle.formatUid(printWriter, valueAt.getUid());
                printWriter.print(": ");
                printWriter.println(valueAt);
                printWriter.print("      curProcState=");
                printWriter.print(valueAt.getCurProcState());
                printWriter.print(" curCapability=");
                android.app.ActivityManager.printCapabilitiesFull(printWriter, valueAt.getCurCapability());
                printWriter.println();
                valueAt.forEachProcess(new java.util.function.Consumer() { // from class: com.android.server.am.ActiveUids$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.am.ActiveUids.lambda$dump$0(printWriter, (com.android.server.am.ProcessRecord) obj);
                    }
                });
            }
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$0(java.io.PrintWriter printWriter, com.android.server.am.ProcessRecord processRecord) {
        printWriter.print("      proc=");
        printWriter.println(processRecord);
    }

    void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.String str, int i, long j) {
        for (int i2 = 0; i2 < this.mActiveUids.size(); i2++) {
            com.android.server.am.UidRecord valueAt = this.mActiveUids.valueAt(i2);
            if (str == null || android.os.UserHandle.getAppId(valueAt.getUid()) == i) {
                valueAt.dumpDebug(protoOutputStream, j);
            }
        }
    }
}
