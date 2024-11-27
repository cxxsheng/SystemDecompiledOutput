package com.android.server.am;

/* loaded from: classes.dex */
public final class UidRecord {
    static final int CHANGE_ACTIVE = 4;
    static final int CHANGE_CACHED = 8;
    static final int CHANGE_CAPABILITY = 32;
    static final int CHANGE_GONE = 1;
    static final int CHANGE_IDLE = 2;
    static final int CHANGE_PROCADJ = 64;
    static final int CHANGE_PROCSTATE = Integer.MIN_VALUE;
    static final int CHANGE_UNCACHED = 16;
    private static int[] ORIG_ENUMS = {1, 2, 4, 8, 16, 32, Integer.MIN_VALUE};
    private static int[] PROTO_ENUMS = {0, 1, 2, 3, 4, 5, 6};

    @com.android.internal.annotations.GuardedBy({"networkStateUpdate"})
    long curProcStateSeq;
    volatile boolean hasInternetPermission;

    @com.android.internal.annotations.GuardedBy({"networkStateUpdate"})
    long lastNetworkUpdatedProcStateSeq;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mCurAdj;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mCurAllowList;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mCurCapability;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mCurProcState;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mEphemeral;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mForegroundServices;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mIdle;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private long mLastBackgroundTime;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private long mLastIdleTime;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mLastReportedChange;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mNumProcs;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mProcAdjChanged;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    private final com.android.server.am.ActivityManagerService mService;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mSetAdj;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mSetAllowList;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mSetCapability;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mSetIdle;
    private final int mUid;

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    private boolean mUidIsFrozen;
    volatile long procStateSeqWaitingForNetwork;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mSetProcState = 20;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private android.util.ArraySet<com.android.server.am.ProcessRecord> mProcRecords = new android.util.ArraySet<>();
    final java.lang.Object networkStateLock = new java.lang.Object();
    final com.android.server.am.UidObserverController.ChangeRecord pendingChange = new com.android.server.am.UidObserverController.ChangeRecord();

    public UidRecord(int i, com.android.server.am.ActivityManagerService activityManagerService) {
        this.mUid = i;
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService != null ? activityManagerService.mProcLock : null;
        this.mIdle = true;
        reset();
    }

    int getUid() {
        return this.mUid;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getCurProcState() {
        return this.mCurProcState;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCurProcState(int i) {
        this.mCurProcState = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getSetProcState() {
        return this.mSetProcState;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setSetProcState(int i) {
        this.mSetProcState = i;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void noteProcAdjChanged() {
        this.mProcAdjChanged = true;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void clearProcAdjChanged() {
        this.mProcAdjChanged = false;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean getProcAdjChanged() {
        return this.mProcAdjChanged;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getMinProcAdj() {
        int i = 1001;
        for (int size = this.mProcRecords.size() - 1; size >= 0; size--) {
            int setAdj = this.mProcRecords.valueAt(size).getSetAdj();
            if (setAdj < i) {
                i = setAdj;
            }
        }
        return i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getCurCapability() {
        return this.mCurCapability;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCurCapability(int i) {
        this.mCurCapability = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getSetCapability() {
        return this.mSetCapability;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setSetCapability(int i) {
        this.mSetCapability = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    long getLastBackgroundTime() {
        return this.mLastBackgroundTime;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setLastBackgroundTime(long j) {
        this.mLastBackgroundTime = j;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    long getLastIdleTime() {
        return this.mLastIdleTime;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setLastIdleTime(long j) {
        this.mLastIdleTime = j;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isEphemeral() {
        return this.mEphemeral;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setEphemeral(boolean z) {
        this.mEphemeral = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean hasForegroundServices() {
        return this.mForegroundServices;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setForegroundServices(boolean z) {
        this.mForegroundServices = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isCurAllowListed() {
        return this.mCurAllowList;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCurAllowListed(boolean z) {
        this.mCurAllowList = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isSetAllowListed() {
        return this.mSetAllowList;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setSetAllowListed(boolean z) {
        this.mSetAllowList = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isIdle() {
        return this.mIdle;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setIdle(boolean z) {
        this.mIdle = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isSetIdle() {
        return this.mSetIdle;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setSetIdle(boolean z) {
        this.mSetIdle = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getNumOfProcs() {
        return this.mProcRecords.size();
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    void forEachProcess(java.util.function.Consumer<com.android.server.am.ProcessRecord> consumer) {
        for (int size = this.mProcRecords.size() - 1; size >= 0; size--) {
            consumer.accept(this.mProcRecords.valueAt(size));
        }
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.ProcessRecord getProcessRecordByIndex(int i) {
        return this.mProcRecords.valueAt(i);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.ProcessRecord getProcessInPackage(java.lang.String str) {
        for (int size = this.mProcRecords.size() - 1; size >= 0; size--) {
            com.android.server.am.ProcessRecord valueAt = this.mProcRecords.valueAt(size);
            if (valueAt != null && android.text.TextUtils.equals(valueAt.info.packageName, str)) {
                return valueAt;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    public boolean areAllProcessesFrozen(com.android.server.am.ProcessRecord processRecord) {
        for (int size = this.mProcRecords.size() - 1; size >= 0; size--) {
            com.android.server.am.ProcessRecord valueAt = this.mProcRecords.valueAt(size);
            com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord = valueAt.mOptRecord;
            if (processRecord != valueAt && !processCachedOptimizerRecord.isFrozen()) {
                return false;
            }
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    public boolean areAllProcessesFrozen() {
        return areAllProcessesFrozen(null);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    public void setFrozen(boolean z) {
        this.mUidIsFrozen = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    public boolean isFrozen() {
        return this.mUidIsFrozen;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void addProcess(com.android.server.am.ProcessRecord processRecord) {
        this.mProcRecords.add(processRecord);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void removeProcess(com.android.server.am.ProcessRecord processRecord) {
        this.mProcRecords.remove(processRecord);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setLastReportedChange(int i) {
        this.mLastReportedChange = i;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void reset() {
        setCurProcState(19);
        this.mForegroundServices = false;
        this.mCurCapability = 0;
    }

    public void updateHasInternetPermission() {
        this.hasInternetPermission = android.app.ActivityManager.checkUidPermission("android.permission.INTERNET", this.mUid) == 0;
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mUid);
        protoOutputStream.write(1159641169922L, com.android.server.am.ProcessList.makeProcStateProtoEnum(this.mCurProcState));
        protoOutputStream.write(1133871366147L, this.mEphemeral);
        protoOutputStream.write(1133871366148L, this.mForegroundServices);
        protoOutputStream.write(1133871366149L, this.mCurAllowList);
        android.util.proto.ProtoUtils.toDuration(protoOutputStream, 1146756268038L, this.mLastBackgroundTime, android.os.SystemClock.elapsedRealtime());
        protoOutputStream.write(1133871366151L, this.mIdle);
        if (this.mLastReportedChange != 0) {
            android.util.proto.ProtoUtils.writeBitWiseFlagsToProtoEnum(protoOutputStream, 2259152797704L, this.mLastReportedChange, ORIG_ENUMS, PROTO_ENUMS);
        }
        protoOutputStream.write(1120986464265L, this.mNumProcs);
        long start2 = protoOutputStream.start(1146756268042L);
        protoOutputStream.write(1112396529665L, this.curProcStateSeq);
        protoOutputStream.write(1112396529666L, this.lastNetworkUpdatedProcStateSeq);
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    public java.lang.String toString() {
        boolean z;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("UidRecord{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        android.os.UserHandle.formatUid(sb, this.mUid);
        sb.append(' ');
        sb.append(com.android.server.am.ProcessList.makeProcStateString(this.mCurProcState));
        if (this.mEphemeral) {
            sb.append(" ephemeral");
        }
        if (this.mForegroundServices) {
            sb.append(" fgServices");
        }
        if (this.mCurAllowList) {
            sb.append(" allowlist");
        }
        if (this.mLastBackgroundTime > 0) {
            sb.append(" bg:");
            android.util.TimeUtils.formatDuration(android.os.SystemClock.elapsedRealtime() - this.mLastBackgroundTime, sb);
        }
        if (this.mIdle) {
            sb.append(" idle");
        }
        if (this.mLastReportedChange != 0) {
            sb.append(" change:");
            boolean z2 = true;
            if ((this.mLastReportedChange & 1) == 0) {
                z = false;
            } else {
                sb.append("gone");
                z = true;
            }
            if ((this.mLastReportedChange & 2) != 0) {
                if (z) {
                    sb.append("|");
                }
                sb.append("idle");
                z = true;
            }
            if ((this.mLastReportedChange & 4) != 0) {
                if (z) {
                    sb.append("|");
                }
                sb.append(com.android.server.pm.verify.domain.DomainVerificationPersistence.TAG_ACTIVE);
                z = true;
            }
            if ((this.mLastReportedChange & 8) == 0) {
                z2 = z;
            } else {
                if (z) {
                    sb.append("|");
                }
                sb.append("cached");
            }
            if ((this.mLastReportedChange & 16) != 0) {
                if (z2) {
                    sb.append("|");
                }
                sb.append("uncached");
            }
            if ((this.mLastReportedChange & Integer.MIN_VALUE) != 0) {
                if (z2) {
                    sb.append("|");
                }
                sb.append("procstate");
            }
            if ((this.mLastReportedChange & 64) != 0) {
                if (z2) {
                    sb.append("|");
                }
                sb.append("procadj");
            }
        }
        sb.append(" procs:");
        sb.append(this.mNumProcs);
        sb.append(" seq(");
        sb.append(this.curProcStateSeq);
        sb.append(",");
        sb.append(this.lastNetworkUpdatedProcStateSeq);
        sb.append(")}");
        sb.append(" caps=");
        android.app.ActivityManager.printCapabilitiesSummary(sb, this.mCurCapability);
        return sb.toString();
    }
}
