package com.android.server.am;

/* loaded from: classes.dex */
public class UidObserverController {
    private static final int SLOW_UID_OBSERVER_THRESHOLD_MS = 20;
    private static final boolean VALIDATE_UID_STATES = true;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mUidChangeDispatchCount;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.os.RemoteCallbackList<android.app.IUidObserver> mUidObservers = new android.os.RemoteCallbackList<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.am.UidObserverController.ChangeRecord> mPendingUidChanges = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.am.UidObserverController.ChangeRecord> mAvailUidChanges = new java.util.ArrayList<>();
    private com.android.server.am.UidObserverController.ChangeRecord[] mActiveUidChanges = new com.android.server.am.UidObserverController.ChangeRecord[5];
    private final java.lang.Runnable mDispatchRunnable = new java.lang.Runnable() { // from class: com.android.server.am.UidObserverController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.am.UidObserverController.this.dispatchUidsChanged();
        }
    };
    private final com.android.server.am.ActiveUids mValidateUids = new com.android.server.am.ActiveUids(null, false);

    UidObserverController(@android.annotation.NonNull android.os.Handler handler) {
        this.mHandler = handler;
    }

    android.os.IBinder register(@android.annotation.NonNull android.app.IUidObserver iUidObserver, int i, int i2, @android.annotation.NonNull java.lang.String str, int i3, @android.annotation.Nullable int[] iArr) {
        android.os.Binder binder = new android.os.Binder("UidObserver-" + str + "-" + java.util.UUID.randomUUID().toString());
        synchronized (this.mLock) {
            this.mUidObservers.register(iUidObserver, new com.android.server.am.UidObserverController.UidObserverRegistration(i3, str, i, i2, android.app.ActivityManager.checkUidPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i3) == 0, iArr, binder));
        }
        return binder;
    }

    void unregister(@android.annotation.NonNull android.app.IUidObserver iUidObserver) {
        synchronized (this.mLock) {
            this.mUidObservers.unregister(iUidObserver);
        }
    }

    final void addUidToObserver(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 80, i, 0, iBinder));
    }

    public final void addUidToObserverImpl(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
        int beginBroadcast = this.mUidObservers.beginBroadcast();
        while (true) {
            int i2 = beginBroadcast - 1;
            if (beginBroadcast <= 0) {
                break;
            }
            com.android.server.am.UidObserverController.UidObserverRegistration uidObserverRegistration = (com.android.server.am.UidObserverController.UidObserverRegistration) this.mUidObservers.getBroadcastCookie(i2);
            if (uidObserverRegistration.getToken().equals(iBinder)) {
                uidObserverRegistration.addUid(i);
                break;
            } else {
                if (i2 == 0) {
                    android.util.Slog.e("ActivityManager", "Unable to find UidObserver by token");
                }
                beginBroadcast = i2;
            }
        }
        this.mUidObservers.finishBroadcast();
    }

    final void removeUidFromObserver(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 81, i, 0, iBinder));
    }

    public final void removeUidFromObserverImpl(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
        int beginBroadcast = this.mUidObservers.beginBroadcast();
        while (true) {
            int i2 = beginBroadcast - 1;
            if (beginBroadcast <= 0) {
                break;
            }
            com.android.server.am.UidObserverController.UidObserverRegistration uidObserverRegistration = (com.android.server.am.UidObserverController.UidObserverRegistration) this.mUidObservers.getBroadcastCookie(i2);
            if (uidObserverRegistration.getToken().equals(iBinder)) {
                uidObserverRegistration.removeUid(i);
                break;
            } else {
                if (i2 == 0) {
                    android.util.Slog.e("ActivityManager", "Unable to find UidObserver by token");
                }
                beginBroadcast = i2;
            }
        }
        this.mUidObservers.finishBroadcast();
    }

    int enqueueUidChange(@android.annotation.Nullable com.android.server.am.UidObserverController.ChangeRecord changeRecord, int i, int i2, int i3, int i4, long j, int i5, boolean z) {
        int i6;
        synchronized (this.mLock) {
            try {
                if (this.mPendingUidChanges.size() == 0) {
                    this.mHandler.post(this.mDispatchRunnable);
                }
                if (changeRecord == null) {
                    changeRecord = getOrCreateChangeRecordLocked();
                }
                if (!changeRecord.isPending) {
                    changeRecord.isPending = true;
                    this.mPendingUidChanges.add(changeRecord);
                } else {
                    i2 = mergeWithPendingChange(i2, changeRecord.change);
                }
                changeRecord.uid = i;
                changeRecord.change = i2;
                changeRecord.procState = i3;
                changeRecord.procAdj = i4;
                changeRecord.procStateSeq = j;
                changeRecord.capability = i5;
                changeRecord.ephemeral = z;
                i6 = changeRecord.change;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i6;
    }

    java.util.ArrayList<com.android.server.am.UidObserverController.ChangeRecord> getPendingUidChangesForTest() {
        return this.mPendingUidChanges;
    }

    com.android.server.am.ActiveUids getValidateUidsForTest() {
        return this.mValidateUids;
    }

    java.lang.Runnable getDispatchRunnableForTest() {
        return this.mDispatchRunnable;
    }

    @com.android.internal.annotations.VisibleForTesting
    static int mergeWithPendingChange(int i, int i2) {
        if ((i & 6) == 0) {
            i |= i2 & 6;
        }
        if ((i & 24) == 0) {
            i |= i2 & 24;
        }
        if ((i & 1) != 0) {
            i &= -13;
        }
        if ((i2 & 32) != 0) {
            i |= 32;
        }
        if ((i2 & Integer.MIN_VALUE) != 0) {
            i |= Integer.MIN_VALUE;
        }
        if ((i2 & 64) != 0) {
            return i | 64;
        }
        return i;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.am.UidObserverController.ChangeRecord getOrCreateChangeRecordLocked() {
        int size = this.mAvailUidChanges.size();
        if (size > 0) {
            return this.mAvailUidChanges.remove(size - 1);
        }
        return new com.android.server.am.UidObserverController.ChangeRecord();
    }

    @com.android.internal.annotations.VisibleForTesting
    void dispatchUidsChanged() {
        int size;
        synchronized (this.mLock) {
            try {
                size = this.mPendingUidChanges.size();
                if (this.mActiveUidChanges.length < size) {
                    this.mActiveUidChanges = new com.android.server.am.UidObserverController.ChangeRecord[size];
                }
                for (int i = 0; i < size; i++) {
                    com.android.server.am.UidObserverController.ChangeRecord changeRecord = this.mPendingUidChanges.get(i);
                    this.mActiveUidChanges[i] = getOrCreateChangeRecordLocked();
                    changeRecord.copyTo(this.mActiveUidChanges[i]);
                    changeRecord.isPending = false;
                }
                this.mPendingUidChanges.clear();
                this.mUidChangeDispatchCount += size;
            } finally {
            }
        }
        int beginBroadcast = this.mUidObservers.beginBroadcast();
        while (true) {
            int i2 = beginBroadcast - 1;
            if (beginBroadcast <= 0) {
                break;
            }
            dispatchUidsChangedForObserver(this.mUidObservers.getBroadcastItem(i2), (com.android.server.am.UidObserverController.UidObserverRegistration) this.mUidObservers.getBroadcastCookie(i2), size);
            beginBroadcast = i2;
        }
        this.mUidObservers.finishBroadcast();
        if (this.mUidObservers.getRegisteredCallbackCount() > 0) {
            for (int i3 = 0; i3 < size; i3++) {
                com.android.server.am.UidObserverController.ChangeRecord changeRecord2 = this.mActiveUidChanges[i3];
                if ((changeRecord2.change & 1) != 0) {
                    this.mValidateUids.remove(changeRecord2.uid);
                } else {
                    com.android.server.am.UidRecord uidRecord = this.mValidateUids.get(changeRecord2.uid);
                    if (uidRecord == null) {
                        uidRecord = new com.android.server.am.UidRecord(changeRecord2.uid, null);
                        this.mValidateUids.put(changeRecord2.uid, uidRecord);
                    }
                    if ((changeRecord2.change & 2) != 0) {
                        uidRecord.setIdle(true);
                    } else if ((changeRecord2.change & 4) != 0) {
                        uidRecord.setIdle(false);
                    }
                    uidRecord.setSetProcState(changeRecord2.procState);
                    uidRecord.setCurProcState(changeRecord2.procState);
                    uidRecord.setSetCapability(changeRecord2.capability);
                    uidRecord.setCurCapability(changeRecord2.capability);
                }
            }
        }
        synchronized (this.mLock) {
            for (int i4 = 0; i4 < size; i4++) {
                try {
                    com.android.server.am.UidObserverController.ChangeRecord changeRecord3 = this.mActiveUidChanges[i4];
                    changeRecord3.isPending = false;
                    this.mAvailUidChanges.add(changeRecord3);
                } finally {
                }
            }
        }
    }

    private void dispatchUidsChangedForObserver(@android.annotation.NonNull android.app.IUidObserver iUidObserver, @android.annotation.NonNull com.android.server.am.UidObserverController.UidObserverRegistration uidObserverRegistration, int i) {
        boolean z;
        int i2;
        if (iUidObserver == null) {
            return;
        }
        boolean z2 = false;
        int i3 = 0;
        while (i3 < i) {
            try {
                com.android.server.am.UidObserverController.ChangeRecord changeRecord = this.mActiveUidChanges[i3];
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                int i4 = changeRecord.change;
                if (uidObserverRegistration.isWatchingUid(changeRecord.uid) && ((android.os.UserHandle.getUserId(changeRecord.uid) == android.os.UserHandle.getUserId(uidObserverRegistration.mUid) || uidObserverRegistration.mCanInteractAcrossUsers) && ((i4 != Integer.MIN_VALUE || (uidObserverRegistration.mWhich & 1) != 0) && (i4 != 64 || (uidObserverRegistration.mWhich & 64) != 0)))) {
                    if ((i4 & 2) != 0) {
                        if ((uidObserverRegistration.mWhich & 4) != 0) {
                            iUidObserver.onUidIdle(changeRecord.uid, changeRecord.ephemeral);
                        }
                    } else if ((i4 & 4) != 0 && (uidObserverRegistration.mWhich & 8) != 0) {
                        iUidObserver.onUidActive(changeRecord.uid);
                    }
                    if ((uidObserverRegistration.mWhich & 16) != 0) {
                        if ((i4 & 8) != 0) {
                            iUidObserver.onUidCachedChanged(changeRecord.uid, true);
                        } else if ((i4 & 16) != 0) {
                            iUidObserver.onUidCachedChanged(changeRecord.uid, z2);
                        }
                    }
                    if ((i4 & 1) != 0) {
                        if ((uidObserverRegistration.mWhich & 2) != 0) {
                            iUidObserver.onUidGone(changeRecord.uid, changeRecord.ephemeral);
                        }
                        if (uidObserverRegistration.mLastProcStates == null) {
                            i2 = 20;
                        } else {
                            uidObserverRegistration.mLastProcStates.delete(changeRecord.uid);
                            i2 = 20;
                        }
                    } else {
                        if ((uidObserverRegistration.mWhich & 1) == 0) {
                            z = z2;
                        } else if (uidObserverRegistration.mCutpoint < 0) {
                            z = true;
                        } else {
                            int i5 = uidObserverRegistration.mLastProcStates.get(changeRecord.uid, -1);
                            if (i5 != -1) {
                                z = (i5 <= uidObserverRegistration.mCutpoint ? true : z2) != (changeRecord.procState <= uidObserverRegistration.mCutpoint ? true : z2) ? true : z2;
                            } else {
                                z = changeRecord.procState != 20 ? true : z2;
                            }
                        }
                        if ((uidObserverRegistration.mWhich & 32) != 0) {
                            z |= (i4 & 32) != 0 ? true : z2;
                        }
                        if (z) {
                            if (uidObserverRegistration.mLastProcStates != null) {
                                uidObserverRegistration.mLastProcStates.put(changeRecord.uid, changeRecord.procState);
                            }
                            i2 = 20;
                            iUidObserver.onUidStateChanged(changeRecord.uid, changeRecord.procState, changeRecord.procStateSeq, changeRecord.capability);
                        } else {
                            i2 = 20;
                        }
                        if ((uidObserverRegistration.mWhich & 64) != 0 && (i4 & 64) != 0) {
                            iUidObserver.onUidProcAdjChanged(changeRecord.uid, changeRecord.procAdj);
                        }
                    }
                    int uptimeMillis2 = (int) (android.os.SystemClock.uptimeMillis() - uptimeMillis);
                    if (uidObserverRegistration.mMaxDispatchTime < uptimeMillis2) {
                        uidObserverRegistration.mMaxDispatchTime = uptimeMillis2;
                    }
                    if (uptimeMillis2 >= i2) {
                        uidObserverRegistration.mSlowDispatchCount++;
                    }
                }
                i3++;
                z2 = false;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
    }

    com.android.server.am.UidRecord getValidateUidRecord(int i) {
        return this.mValidateUids.get(i);
    }

    void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String str) {
        synchronized (this.mLock) {
            try {
                int registeredCallbackCount = this.mUidObservers.getRegisteredCallbackCount();
                boolean z = false;
                for (int i = 0; i < registeredCallbackCount; i++) {
                    com.android.server.am.UidObserverController.UidObserverRegistration uidObserverRegistration = (com.android.server.am.UidObserverController.UidObserverRegistration) this.mUidObservers.getRegisteredCallbackCookie(i);
                    if (str == null || str.equals(uidObserverRegistration.mPkg)) {
                        if (!z) {
                            printWriter.println("  mUidObservers:");
                            z = true;
                        }
                        uidObserverRegistration.dump(printWriter, this.mUidObservers.getRegisteredCallbackItem(i));
                    }
                }
                if (str == null) {
                    printWriter.println();
                    printWriter.print("  mUidChangeDispatchCount=");
                    printWriter.print(this.mUidChangeDispatchCount);
                    printWriter.println();
                    printWriter.println("  Slow UID dispatches:");
                    for (int i2 = 0; i2 < registeredCallbackCount; i2++) {
                        com.android.server.am.UidObserverController.UidObserverRegistration uidObserverRegistration2 = (com.android.server.am.UidObserverController.UidObserverRegistration) this.mUidObservers.getRegisteredCallbackCookie(i2);
                        printWriter.print("    ");
                        printWriter.print(this.mUidObservers.getRegisteredCallbackItem(i2).getClass().getTypeName());
                        printWriter.print(": ");
                        printWriter.print(uidObserverRegistration2.mSlowDispatchCount);
                        printWriter.print(" / Max ");
                        printWriter.print(uidObserverRegistration2.mMaxDispatchTime);
                        printWriter.println("ms");
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dumpDebug(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, @android.annotation.Nullable java.lang.String str) {
        synchronized (this.mLock) {
            try {
                int registeredCallbackCount = this.mUidObservers.getRegisteredCallbackCount();
                for (int i = 0; i < registeredCallbackCount; i++) {
                    com.android.server.am.UidObserverController.UidObserverRegistration uidObserverRegistration = (com.android.server.am.UidObserverController.UidObserverRegistration) this.mUidObservers.getRegisteredCallbackCookie(i);
                    if (str == null || str.equals(uidObserverRegistration.mPkg)) {
                        uidObserverRegistration.dumpDebug(protoOutputStream, 2246267895831L);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean dumpValidateUids(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2, boolean z) {
        return this.mValidateUids.dump(printWriter, str, i, str2, z);
    }

    void dumpValidateUidsProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, @android.annotation.Nullable java.lang.String str, int i, long j) {
        this.mValidateUids.dumpProto(protoOutputStream, str, i, j);
    }

    static final class ChangeRecord {
        public int capability;
        public int change;
        public boolean ephemeral;
        public boolean isPending;
        public int procAdj;
        public int procState;
        public long procStateSeq;
        public int uid;

        ChangeRecord() {
        }

        void copyTo(@android.annotation.NonNull com.android.server.am.UidObserverController.ChangeRecord changeRecord) {
            changeRecord.isPending = this.isPending;
            changeRecord.uid = this.uid;
            changeRecord.change = this.change;
            changeRecord.procState = this.procState;
            changeRecord.procAdj = this.procAdj;
            changeRecord.capability = this.capability;
            changeRecord.ephemeral = this.ephemeral;
            changeRecord.procStateSeq = this.procStateSeq;
        }
    }

    private static final class UidObserverRegistration {
        private static final int[] ORIG_ENUMS = {4, 8, 2, 1, 32, 64};
        private static final int[] PROTO_ENUMS = {3, 4, 2, 1, 6, 7};
        private final boolean mCanInteractAcrossUsers;
        private final int mCutpoint;
        final android.util.SparseIntArray mLastProcStates;
        int mMaxDispatchTime;
        private final java.lang.String mPkg;
        int mSlowDispatchCount;
        private final android.os.IBinder mToken;
        private final int mUid;
        private int[] mUids;
        private final int mWhich;

        UidObserverRegistration(int i, @android.annotation.NonNull java.lang.String str, int i2, int i3, boolean z, @android.annotation.Nullable int[] iArr, @android.annotation.NonNull android.os.IBinder iBinder) {
            this.mUid = i;
            this.mPkg = str;
            this.mWhich = i2;
            this.mCutpoint = i3;
            this.mCanInteractAcrossUsers = z;
            if (iArr != null) {
                this.mUids = (int[]) iArr.clone();
                java.util.Arrays.sort(this.mUids);
            } else {
                this.mUids = null;
            }
            this.mToken = iBinder;
            this.mLastProcStates = i3 >= 0 ? new android.util.SparseIntArray() : null;
        }

        boolean isWatchingUid(int i) {
            return this.mUids == null || java.util.Arrays.binarySearch(this.mUids, i) >= 0;
        }

        void addUid(int i) {
            if (this.mUids == null) {
                return;
            }
            int[] iArr = this.mUids;
            this.mUids = new int[iArr.length + 1];
            boolean z = false;
            for (int i2 = 0; i2 < iArr.length; i2++) {
                if (!z) {
                    if (iArr[i2] < i) {
                        this.mUids[i2] = iArr[i2];
                    } else if (iArr[i2] == i) {
                        this.mUids = iArr;
                        return;
                    } else {
                        this.mUids[i2] = i;
                        this.mUids[i2 + 1] = iArr[i2];
                        z = true;
                    }
                } else {
                    this.mUids[i2 + 1] = iArr[i2];
                }
            }
            if (!z) {
                this.mUids[iArr.length] = i;
            }
        }

        void removeUid(int i) {
            if (this.mUids == null || this.mUids.length == 0) {
                return;
            }
            int[] iArr = this.mUids;
            this.mUids = new int[iArr.length - 1];
            boolean z = false;
            for (int i2 = 0; i2 < iArr.length; i2++) {
                if (!z) {
                    if (iArr[i2] == i) {
                        z = true;
                    } else {
                        if (i2 == iArr.length - 1) {
                            this.mUids = iArr;
                            return;
                        }
                        this.mUids[i2] = iArr[i2];
                    }
                } else {
                    this.mUids[i2 - 1] = iArr[i2];
                }
            }
        }

        android.os.IBinder getToken() {
            return this.mToken;
        }

        void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull android.app.IUidObserver iUidObserver) {
            printWriter.print("    ");
            android.os.UserHandle.formatUid(printWriter, this.mUid);
            printWriter.print(" ");
            printWriter.print(this.mPkg);
            printWriter.print(" ");
            printWriter.print(iUidObserver.getClass().getTypeName());
            printWriter.print(":");
            if ((this.mWhich & 4) != 0) {
                printWriter.print(" IDLE");
            }
            if ((this.mWhich & 8) != 0) {
                printWriter.print(" ACT");
            }
            if ((this.mWhich & 2) != 0) {
                printWriter.print(" GONE");
            }
            if ((this.mWhich & 32) != 0) {
                printWriter.print(" CAP");
            }
            if ((this.mWhich & 1) != 0) {
                printWriter.print(" STATE");
                printWriter.print(" (cut=");
                printWriter.print(this.mCutpoint);
                printWriter.print(")");
            }
            printWriter.println();
            if (this.mLastProcStates != null) {
                int size = this.mLastProcStates.size();
                for (int i = 0; i < size; i++) {
                    printWriter.print("      Last ");
                    android.os.UserHandle.formatUid(printWriter, this.mLastProcStates.keyAt(i));
                    printWriter.print(": ");
                    printWriter.println(this.mLastProcStates.valueAt(i));
                }
            }
        }

        void dumpDebug(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.mUid);
            protoOutputStream.write(1138166333442L, this.mPkg);
            android.util.proto.ProtoUtils.writeBitWiseFlagsToProtoEnum(protoOutputStream, 2259152797699L, this.mWhich, ORIG_ENUMS, PROTO_ENUMS);
            protoOutputStream.write(1120986464260L, this.mCutpoint);
            if (this.mLastProcStates != null) {
                int size = this.mLastProcStates.size();
                for (int i = 0; i < size; i++) {
                    long start2 = protoOutputStream.start(2246267895813L);
                    protoOutputStream.write(1120986464257L, this.mLastProcStates.keyAt(i));
                    protoOutputStream.write(1120986464258L, this.mLastProcStates.valueAt(i));
                    protoOutputStream.end(start2);
                }
            }
            protoOutputStream.end(start);
        }
    }
}
