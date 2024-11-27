package com.android.internal.app.procstats;

/* loaded from: classes4.dex */
public final class ProcessState {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_PARCEL = false;
    private static final java.lang.String TAG = "ProcessStats";
    private boolean mActive;
    private long mAvgCachedKillPss;
    private com.android.internal.app.procstats.ProcessState mCommonProcess;
    android.util.ArrayMap<com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceState> mCommonSources;
    private int mCurCombinedState;
    private boolean mDead;
    private final com.android.internal.app.procstats.DurationsTable mDurations;
    private int mLastPssState;
    private long mLastPssTime;
    private long mMaxCachedKillPss;
    private long mMinCachedKillPss;
    private boolean mMultiPackage;
    private final java.lang.String mName;
    private int mNumActiveServices;
    private int mNumCachedKill;
    private int mNumExcessiveCpu;
    private int mNumStartedServices;
    private final java.lang.String mPackage;
    private final com.android.internal.app.procstats.PssTable mPssTable;
    private long mStartTime;
    private int mStateBeforeFrozen;
    private final com.android.internal.app.procstats.ProcessStats mStats;
    private long mTmpTotalTime;
    private long mTotalRunningDuration;
    private final long[] mTotalRunningPss;
    private long mTotalRunningStartTime;
    private final int mUid;
    private final long mVersion;
    public com.android.internal.app.procstats.ProcessState tmpFoundSubProc;
    public int tmpNumInUse;
    static final int[] PROCESS_STATE_TO_STATE = {0, 0, 1, 2, 3, 4, 5, 6, 6, 7, 8, 10, 1, 11, 12, 13, 14, 14, 14, 14};
    public static final java.util.Comparator<com.android.internal.app.procstats.ProcessState> COMPARATOR = new java.util.Comparator<com.android.internal.app.procstats.ProcessState>() { // from class: com.android.internal.app.procstats.ProcessState.1
        @Override // java.util.Comparator
        public int compare(com.android.internal.app.procstats.ProcessState processState, com.android.internal.app.procstats.ProcessState processState2) {
            if (processState.mTmpTotalTime < processState2.mTmpTotalTime) {
                return -1;
            }
            if (processState.mTmpTotalTime > processState2.mTmpTotalTime) {
                return 1;
            }
            return 0;
        }
    };

    static class PssAggr {
        long pss = 0;
        long samples = 0;

        PssAggr() {
        }

        void add(long j, long j2) {
            this.pss = ((long) ((this.pss * this.samples) + (j * j2))) / (this.samples + j2);
            this.samples += j2;
        }
    }

    public ProcessState(com.android.internal.app.procstats.ProcessStats processStats, java.lang.String str, int i, long j, java.lang.String str2) {
        this.mTotalRunningPss = new long[10];
        this.mCurCombinedState = -1;
        this.mStateBeforeFrozen = -1;
        this.mLastPssState = -1;
        this.mStats = processStats;
        this.mName = str2;
        this.mCommonProcess = this;
        this.mPackage = str;
        this.mUid = i;
        this.mVersion = j;
        this.mDurations = new com.android.internal.app.procstats.DurationsTable(processStats.mTableData);
        this.mPssTable = new com.android.internal.app.procstats.PssTable(processStats.mTableData);
    }

    public ProcessState(com.android.internal.app.procstats.ProcessState processState, java.lang.String str, int i, long j, java.lang.String str2, long j2) {
        this.mTotalRunningPss = new long[10];
        this.mCurCombinedState = -1;
        this.mStateBeforeFrozen = -1;
        this.mLastPssState = -1;
        this.mStats = processState.mStats;
        this.mName = str2;
        this.mCommonProcess = processState;
        this.mPackage = str;
        this.mUid = i;
        this.mVersion = j;
        this.mCurCombinedState = processState.mCurCombinedState;
        this.mStartTime = j2;
        if (this.mCurCombinedState != -1) {
            this.mTotalRunningStartTime = j2;
        }
        this.mDurations = new com.android.internal.app.procstats.DurationsTable(processState.mStats.mTableData);
        this.mPssTable = new com.android.internal.app.procstats.PssTable(processState.mStats.mTableData);
    }

    public com.android.internal.app.procstats.ProcessState clone(long j) {
        com.android.internal.app.procstats.ProcessState processState = new com.android.internal.app.procstats.ProcessState(this, this.mPackage, this.mUid, this.mVersion, this.mName, j);
        processState.mDurations.addDurations(this.mDurations);
        processState.mPssTable.copyFrom(this.mPssTable, 10);
        java.lang.System.arraycopy(this.mTotalRunningPss, 0, processState.mTotalRunningPss, 0, 10);
        processState.mTotalRunningDuration = getTotalRunningDuration(j);
        processState.mNumExcessiveCpu = this.mNumExcessiveCpu;
        processState.mNumCachedKill = this.mNumCachedKill;
        processState.mMinCachedKillPss = this.mMinCachedKillPss;
        processState.mAvgCachedKillPss = this.mAvgCachedKillPss;
        processState.mMaxCachedKillPss = this.mMaxCachedKillPss;
        processState.mActive = this.mActive;
        processState.mNumActiveServices = this.mNumActiveServices;
        processState.mNumStartedServices = this.mNumStartedServices;
        return processState;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public com.android.internal.app.procstats.ProcessState getCommonProcess() {
        return this.mCommonProcess;
    }

    public void makeStandalone() {
        this.mCommonProcess = this;
    }

    public java.lang.String getPackage() {
        return this.mPackage;
    }

    public int getUid() {
        return this.mUid;
    }

    public long getVersion() {
        return this.mVersion;
    }

    public boolean isMultiPackage() {
        return this.mMultiPackage;
    }

    public void setMultiPackage(boolean z) {
        this.mMultiPackage = z;
    }

    public int getDurationsBucketCount() {
        return this.mDurations.getKeyCount();
    }

    public void add(com.android.internal.app.procstats.ProcessState processState) {
        this.mDurations.addDurations(processState.mDurations);
        this.mPssTable.mergeStats(processState.mPssTable);
        this.mNumExcessiveCpu += processState.mNumExcessiveCpu;
        if (processState.mNumCachedKill > 0) {
            addCachedKill(processState.mNumCachedKill, processState.mMinCachedKillPss, processState.mAvgCachedKillPss, processState.mMaxCachedKillPss);
        }
        if (processState.mCommonSources != null) {
            if (this.mCommonSources == null) {
                this.mCommonSources = new android.util.ArrayMap<>();
            }
            int size = processState.mCommonSources.size();
            for (int i = 0; i < size; i++) {
                com.android.internal.app.procstats.AssociationState.SourceKey keyAt = processState.mCommonSources.keyAt(i);
                com.android.internal.app.procstats.AssociationState.SourceState sourceState = this.mCommonSources.get(keyAt);
                if (sourceState == null) {
                    sourceState = new com.android.internal.app.procstats.AssociationState.SourceState(this.mStats, null, this, keyAt);
                    this.mCommonSources.put(keyAt, sourceState);
                }
                sourceState.add(processState.mCommonSources.valueAt(i));
            }
        }
    }

    public void resetSafely(long j) {
        this.mDurations.resetTable();
        this.mPssTable.resetTable();
        this.mStartTime = j;
        this.mLastPssState = -1;
        this.mLastPssTime = 0L;
        this.mNumExcessiveCpu = 0;
        this.mNumCachedKill = 0;
        this.mMaxCachedKillPss = 0L;
        this.mAvgCachedKillPss = 0L;
        this.mMinCachedKillPss = 0L;
        if (this.mCommonSources != null) {
            for (int size = this.mCommonSources.size() - 1; size >= 0; size--) {
                com.android.internal.app.procstats.AssociationState.SourceState valueAt = this.mCommonSources.valueAt(size);
                if (valueAt.isInUse()) {
                    valueAt.resetSafely(j);
                } else {
                    this.mCommonSources.removeAt(size);
                }
            }
        }
    }

    public void makeDead() {
        this.mDead = true;
    }

    private void ensureNotDead() {
        if (!this.mDead) {
            return;
        }
        android.util.Slog.w("ProcessStats", "ProcessState dead: name=" + this.mName + " pkg=" + this.mPackage + " uid=" + this.mUid + " common.name=" + this.mCommonProcess.mName);
    }

    public void writeToParcel(android.os.Parcel parcel, long j) {
        parcel.writeInt(this.mMultiPackage ? 1 : 0);
        this.mDurations.writeToParcel(parcel);
        this.mPssTable.writeToParcel(parcel);
        for (int i = 0; i < 10; i++) {
            parcel.writeLong(this.mTotalRunningPss[i]);
        }
        parcel.writeLong(getTotalRunningDuration(j));
        parcel.writeInt(0);
        parcel.writeInt(this.mNumExcessiveCpu);
        parcel.writeInt(this.mNumCachedKill);
        if (this.mNumCachedKill > 0) {
            parcel.writeLong(this.mMinCachedKillPss);
            parcel.writeLong(this.mAvgCachedKillPss);
            parcel.writeLong(this.mMaxCachedKillPss);
        }
        int size = this.mCommonSources != null ? this.mCommonSources.size() : 0;
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.app.procstats.AssociationState.SourceKey keyAt = this.mCommonSources.keyAt(i2);
            com.android.internal.app.procstats.AssociationState.SourceState valueAt = this.mCommonSources.valueAt(i2);
            keyAt.writeToParcel(this.mStats, parcel);
            valueAt.writeToParcel(parcel, 0);
        }
    }

    boolean readFromParcel(android.os.Parcel parcel, int i, boolean z) {
        boolean z2 = parcel.readInt() != 0;
        if (z) {
            this.mMultiPackage = z2;
        }
        if (!this.mDurations.readFromParcel(parcel) || !this.mPssTable.readFromParcel(parcel)) {
            return false;
        }
        for (int i2 = 0; i2 < 10; i2++) {
            this.mTotalRunningPss[i2] = parcel.readLong();
        }
        this.mTotalRunningDuration = parcel.readLong();
        parcel.readInt();
        this.mNumExcessiveCpu = parcel.readInt();
        this.mNumCachedKill = parcel.readInt();
        if (this.mNumCachedKill > 0) {
            this.mMinCachedKillPss = parcel.readLong();
            this.mAvgCachedKillPss = parcel.readLong();
            this.mMaxCachedKillPss = parcel.readLong();
        } else {
            this.mMaxCachedKillPss = 0L;
            this.mAvgCachedKillPss = 0L;
            this.mMinCachedKillPss = 0L;
        }
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mCommonSources = new android.util.ArrayMap<>(readInt);
            for (int i3 = 0; i3 < readInt; i3++) {
                com.android.internal.app.procstats.AssociationState.SourceKey sourceKey = new com.android.internal.app.procstats.AssociationState.SourceKey(this.mStats, parcel, i);
                com.android.internal.app.procstats.AssociationState.SourceState sourceState = new com.android.internal.app.procstats.AssociationState.SourceState(this.mStats, null, this, sourceKey);
                sourceState.readFromParcel(parcel);
                this.mCommonSources.put(sourceKey, sourceState);
            }
        }
        return true;
    }

    public void makeActive() {
        ensureNotDead();
        this.mActive = true;
    }

    public void makeInactive() {
        this.mActive = false;
    }

    public boolean isInUse() {
        return this.mActive || this.mNumActiveServices > 0 || this.mNumStartedServices > 0 || this.mCurCombinedState != -1;
    }

    public boolean isActive() {
        return this.mActive;
    }

    public boolean hasAnyData() {
        return (this.mDurations.getKeyCount() == 0 && this.mCurCombinedState == -1 && this.mPssTable.getKeyCount() == 0 && this.mTotalRunningPss[0] == 0) ? false : true;
    }

    public void onProcessFrozen(long j, android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder> arrayMap) {
        this.mStateBeforeFrozen = this.mCurCombinedState % 16;
        setCombinedState(((this.mCurCombinedState / 16) * 16) + 15, j, arrayMap);
    }

    public void onProcessUnfrozen(long j, android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder> arrayMap) {
        setCombinedState(this.mStateBeforeFrozen + ((this.mCurCombinedState / 16) * 16), j, arrayMap);
    }

    public void setState(int i, int i2, long j, android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder> arrayMap) {
        int i3;
        if (i < 0) {
            i3 = this.mNumStartedServices > 0 ? (i2 * 16) + 9 : -1;
        } else {
            i3 = (i2 * 16) + PROCESS_STATE_TO_STATE[i];
        }
        setCombinedState(i3, j, arrayMap);
    }

    void setCombinedState(int i, long j, android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder> arrayMap) {
        this.mCommonProcess.setCombinedStateIdv(i, j);
        if (this.mCommonProcess.mMultiPackage && arrayMap != null) {
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                pullFixedProc(arrayMap, size).setCombinedStateIdv(i, j);
            }
        }
    }

    void setCombinedStateIdv(int i, long j) {
        ensureNotDead();
        if (!this.mDead && this.mCurCombinedState != i) {
            commitStateTime(j);
            if (i == -1) {
                this.mTotalRunningDuration += j - this.mTotalRunningStartTime;
                this.mTotalRunningStartTime = 0L;
            } else if (this.mCurCombinedState == -1) {
                this.mTotalRunningDuration = 0L;
                this.mTotalRunningStartTime = j;
                for (int i2 = 9; i2 >= 0; i2--) {
                    this.mTotalRunningPss[i2] = 0;
                }
            }
            this.mCurCombinedState = i;
            com.android.internal.app.procstats.UidState uidState = this.mStats.mUidStates.get(this.mUid);
            if (uidState != null) {
                uidState.updateCombinedState(i, j);
            }
        }
    }

    public int getCombinedState() {
        return this.mCurCombinedState;
    }

    public void commitStateTime(long j) {
        if (this.mCurCombinedState != -1) {
            long j2 = j - this.mStartTime;
            if (j2 > 0) {
                this.mDurations.addDuration(this.mCurCombinedState, j2);
            }
            this.mTotalRunningDuration += j - this.mTotalRunningStartTime;
            this.mTotalRunningStartTime = j;
        }
        this.mStartTime = j;
        if (this.mCommonSources != null) {
            for (int size = this.mCommonSources.size() - 1; size >= 0; size--) {
                this.mCommonSources.valueAt(size).commitStateTime(j);
            }
        }
    }

    public void incActiveServices(java.lang.String str) {
        if (this.mCommonProcess != this) {
            this.mCommonProcess.incActiveServices(str);
        }
        this.mNumActiveServices++;
    }

    public void decActiveServices(java.lang.String str) {
        if (this.mCommonProcess != this) {
            this.mCommonProcess.decActiveServices(str);
        }
        this.mNumActiveServices--;
        if (this.mNumActiveServices < 0) {
            android.util.Slog.wtfStack("ProcessStats", "Proc active services underrun: pkg=" + this.mPackage + " uid=" + this.mUid + " proc=" + this.mName + " service=" + str);
            this.mNumActiveServices = 0;
        }
    }

    public void incStartedServices(int i, long j, java.lang.String str) {
        if (this.mCommonProcess != this) {
            this.mCommonProcess.incStartedServices(i, j, str);
        }
        this.mNumStartedServices++;
        if (this.mNumStartedServices == 1 && this.mCurCombinedState == -1) {
            setCombinedStateIdv((i * 16) + 9, j);
        }
    }

    public void decStartedServices(int i, long j, java.lang.String str) {
        if (this.mCommonProcess != this) {
            this.mCommonProcess.decStartedServices(i, j, str);
        }
        this.mNumStartedServices--;
        if (this.mNumStartedServices == 0 && this.mCurCombinedState % 16 == 9) {
            setCombinedStateIdv(-1, j);
        } else if (this.mNumStartedServices < 0) {
            android.util.Slog.wtfStack("ProcessStats", "Proc started services underrun: pkg=" + this.mPackage + " uid=" + this.mUid + " name=" + this.mName);
            this.mNumStartedServices = 0;
        }
    }

    public void addPss(long j, long j2, long j3, boolean z, int i, long j4, android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder> arrayMap) {
        ensureNotDead();
        switch (i) {
            case 0:
                this.mStats.mInternalSinglePssCount++;
                this.mStats.mInternalSinglePssTime += j4;
                break;
            case 1:
                this.mStats.mInternalAllMemPssCount++;
                this.mStats.mInternalAllMemPssTime += j4;
                break;
            case 2:
                this.mStats.mInternalAllPollPssCount++;
                this.mStats.mInternalAllPollPssTime += j4;
                break;
            case 3:
                this.mStats.mExternalPssCount++;
                this.mStats.mExternalPssTime += j4;
                break;
            case 4:
                this.mStats.mExternalSlowPssCount++;
                this.mStats.mExternalSlowPssTime += j4;
                break;
        }
        if (z || this.mLastPssState != this.mCurCombinedState || android.os.SystemClock.uptimeMillis() >= this.mLastPssTime + 30000) {
            this.mLastPssState = this.mCurCombinedState;
            this.mLastPssTime = android.os.SystemClock.uptimeMillis();
            if (this.mCurCombinedState != -1) {
                this.mCommonProcess.mPssTable.mergeStats(this.mCurCombinedState, 1, j, j, j, j2, j2, j2, j3, j3, j3);
                com.android.internal.app.procstats.PssTable.mergeStats(this.mCommonProcess.mTotalRunningPss, 0, 1, j, j, j, j2, j2, j2, j3, j3, j3);
                if (this.mCommonProcess.mMultiPackage && arrayMap != null) {
                    for (int size = arrayMap.size() - 1; size >= 0; size--) {
                        com.android.internal.app.procstats.ProcessState pullFixedProc = pullFixedProc(arrayMap, size);
                        pullFixedProc.mPssTable.mergeStats(this.mCurCombinedState, 1, j, j, j, j2, j2, j2, j3, j3, j3);
                        com.android.internal.app.procstats.PssTable.mergeStats(pullFixedProc.mTotalRunningPss, 0, 1, j, j, j, j2, j2, j2, j3, j3, j3);
                    }
                }
            }
        }
    }

    public void reportExcessiveCpu(android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder> arrayMap) {
        ensureNotDead();
        this.mCommonProcess.mNumExcessiveCpu++;
        if (!this.mCommonProcess.mMultiPackage) {
            return;
        }
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            pullFixedProc(arrayMap, size).mNumExcessiveCpu++;
        }
    }

    private void addCachedKill(int i, long j, long j2, long j3) {
        if (this.mNumCachedKill <= 0) {
            this.mNumCachedKill = i;
            this.mMinCachedKillPss = j;
            this.mAvgCachedKillPss = j2;
            this.mMaxCachedKillPss = j3;
            return;
        }
        if (j < this.mMinCachedKillPss) {
            this.mMinCachedKillPss = j;
        }
        if (j3 > this.mMaxCachedKillPss) {
            this.mMaxCachedKillPss = j3;
        }
        this.mAvgCachedKillPss = (long) (((this.mAvgCachedKillPss * this.mNumCachedKill) + j2) / (this.mNumCachedKill + i));
        this.mNumCachedKill += i;
    }

    public com.android.internal.app.procstats.ProcessState pullFixedProc(java.lang.String str) {
        if (this.mMultiPackage) {
            android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> longSparseArray = this.mStats.mPackages.get(str, this.mUid);
            if (longSparseArray == null) {
                throw new java.lang.IllegalStateException("Didn't find package " + str + " / " + this.mUid);
            }
            com.android.internal.app.procstats.ProcessStats.PackageState packageState = longSparseArray.get(this.mVersion);
            if (packageState == null) {
                throw new java.lang.IllegalStateException("Didn't find package " + str + " / " + this.mUid + " vers " + this.mVersion);
            }
            com.android.internal.app.procstats.ProcessState processState = packageState.mProcesses.get(this.mName);
            if (processState == null) {
                throw new java.lang.IllegalStateException("Didn't create per-package process " + this.mName + " in pkg " + str + " / " + this.mUid + " vers " + this.mVersion);
            }
            return processState;
        }
        return this;
    }

    private com.android.internal.app.procstats.ProcessState pullFixedProc(android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder> arrayMap, int i) {
        com.android.internal.app.procstats.ProcessStats.ProcessStateHolder valueAt = arrayMap.valueAt(i);
        com.android.internal.app.procstats.ProcessState processState = valueAt.state;
        if (this.mDead && processState.mCommonProcess != processState) {
            android.util.Log.wtf("ProcessStats", "Pulling dead proc: name=" + this.mName + " pkg=" + this.mPackage + " uid=" + this.mUid + " common.name=" + this.mCommonProcess.mName);
            processState = this.mStats.getProcessStateLocked(processState.mPackage, processState.mUid, processState.mVersion, processState.mName);
        }
        if (processState.mMultiPackage) {
            android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> longSparseArray = this.mStats.mPackages.get(arrayMap.keyAt(i), processState.mUid);
            if (longSparseArray == null) {
                throw new java.lang.IllegalStateException("No existing package " + arrayMap.keyAt(i) + "/" + processState.mUid + " for multi-proc " + processState.mName);
            }
            com.android.internal.app.procstats.ProcessStats.PackageState packageState = longSparseArray.get(processState.mVersion);
            if (packageState == null) {
                throw new java.lang.IllegalStateException("No existing package " + arrayMap.keyAt(i) + "/" + processState.mUid + " for multi-proc " + processState.mName + " version " + processState.mVersion);
            }
            java.lang.String str = processState.mName;
            processState = packageState.mProcesses.get(processState.mName);
            if (processState == null) {
                throw new java.lang.IllegalStateException("Didn't create per-package process " + str + " in pkg " + packageState.mPackageName + "/" + packageState.mUid);
            }
            valueAt.state = processState;
        }
        return processState;
    }

    public long getTotalRunningDuration(long j) {
        return this.mTotalRunningDuration + (this.mTotalRunningStartTime != 0 ? j - this.mTotalRunningStartTime : 0L);
    }

    public long getDuration(int i, long j) {
        long valueForId = this.mDurations.getValueForId((byte) i);
        if (this.mCurCombinedState == i) {
            return valueForId + (j - this.mStartTime);
        }
        return valueForId;
    }

    public long getPssSampleCount(int i) {
        return this.mPssTable.getValueForId((byte) i, 0);
    }

    public long getPssMinimum(int i) {
        return this.mPssTable.getValueForId((byte) i, 1);
    }

    public long getPssAverage(int i) {
        return this.mPssTable.getValueForId((byte) i, 2);
    }

    public long getPssMaximum(int i) {
        return this.mPssTable.getValueForId((byte) i, 3);
    }

    public long getPssUssMinimum(int i) {
        return this.mPssTable.getValueForId((byte) i, 4);
    }

    public long getPssUssAverage(int i) {
        return this.mPssTable.getValueForId((byte) i, 5);
    }

    public long getPssUssMaximum(int i) {
        return this.mPssTable.getValueForId((byte) i, 6);
    }

    public long getPssRssMinimum(int i) {
        return this.mPssTable.getValueForId((byte) i, 7);
    }

    public long getPssRssAverage(int i) {
        return this.mPssTable.getValueForId((byte) i, 8);
    }

    public long getPssRssMaximum(int i) {
        return this.mPssTable.getValueForId((byte) i, 9);
    }

    com.android.internal.app.procstats.AssociationState.SourceState getOrCreateSourceState(com.android.internal.app.procstats.AssociationState.SourceKey sourceKey) {
        if (this.mCommonSources == null) {
            this.mCommonSources = new android.util.ArrayMap<>();
        }
        com.android.internal.app.procstats.AssociationState.SourceState sourceState = this.mCommonSources.get(sourceKey);
        if (sourceState == null) {
            com.android.internal.app.procstats.AssociationState.SourceState sourceState2 = new com.android.internal.app.procstats.AssociationState.SourceState(this.mStats, null, this, sourceKey);
            this.mCommonSources.put(sourceKey, sourceState2);
            return sourceState2;
        }
        return sourceState;
    }

    public void aggregatePss(com.android.internal.app.procstats.ProcessStats.TotalMemoryUseCollection totalMemoryUseCollection, long j) {
        boolean z;
        boolean z2;
        boolean z3;
        long j2;
        com.android.internal.app.procstats.ProcessState.PssAggr pssAggr = new com.android.internal.app.procstats.ProcessState.PssAggr();
        com.android.internal.app.procstats.ProcessState.PssAggr pssAggr2 = new com.android.internal.app.procstats.ProcessState.PssAggr();
        com.android.internal.app.procstats.ProcessState.PssAggr pssAggr3 = new com.android.internal.app.procstats.ProcessState.PssAggr();
        int i = 0;
        boolean z4 = false;
        while (i < this.mDurations.getKeyCount()) {
            byte idFromKey = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(this.mDurations.getKeyAt(i));
            int i2 = idFromKey % 16;
            int i3 = i;
            long pssSampleCount = getPssSampleCount(idFromKey);
            if (pssSampleCount > 0) {
                long pssAverage = getPssAverage(idFromKey);
                if (i2 <= 5) {
                    pssAggr.add(pssAverage, pssSampleCount);
                } else if (i2 <= 10) {
                    pssAggr2.add(pssAverage, pssSampleCount);
                } else {
                    pssAggr3.add(pssAverage, pssSampleCount);
                }
                z4 = true;
            }
            i = i3 + 1;
        }
        if (!z4) {
            return;
        }
        if (pssAggr.samples < 3 && pssAggr2.samples > 0) {
            pssAggr.add(pssAggr2.pss, pssAggr2.samples);
            z = true;
        } else {
            z = false;
        }
        if (pssAggr.samples < 3 && pssAggr3.samples > 0) {
            pssAggr.add(pssAggr3.pss, pssAggr3.samples);
            z2 = true;
        } else {
            z2 = false;
        }
        if (pssAggr2.samples < 3 && pssAggr3.samples > 0) {
            pssAggr2.add(pssAggr3.pss, pssAggr3.samples);
            z3 = true;
        } else {
            z3 = false;
        }
        if (pssAggr2.samples < 3 && !z && pssAggr.samples > 0) {
            pssAggr2.add(pssAggr.pss, pssAggr.samples);
        }
        if (pssAggr3.samples < 3 && !z3 && pssAggr2.samples > 0) {
            pssAggr3.add(pssAggr2.pss, pssAggr2.samples);
        }
        if (pssAggr3.samples < 3 && !z2 && pssAggr.samples > 0) {
            pssAggr3.add(pssAggr.pss, pssAggr.samples);
        }
        int i4 = 0;
        while (i4 < this.mDurations.getKeyCount()) {
            int keyAt = this.mDurations.getKeyAt(i4);
            byte idFromKey2 = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt);
            long value = this.mDurations.getValue(keyAt);
            if (this.mCurCombinedState == idFromKey2) {
                value += j - this.mStartTime;
            }
            int i5 = idFromKey2 % 16;
            long[] jArr = totalMemoryUseCollection.processStateTime;
            jArr[i5] = jArr[i5] + value;
            long pssSampleCount2 = getPssSampleCount(idFromKey2);
            if (pssSampleCount2 > 0) {
                j2 = getPssAverage(idFromKey2);
            } else if (i5 <= 5) {
                pssSampleCount2 = pssAggr.samples;
                j2 = pssAggr.pss;
            } else if (i5 <= 10) {
                pssSampleCount2 = pssAggr2.samples;
                j2 = pssAggr2.pss;
            } else {
                pssSampleCount2 = pssAggr3.samples;
                j2 = pssAggr3.pss;
            }
            com.android.internal.app.procstats.ProcessState.PssAggr pssAggr4 = pssAggr;
            double d = j2;
            totalMemoryUseCollection.processStatePss[i5] = (long) (((totalMemoryUseCollection.processStatePss[i5] * totalMemoryUseCollection.processStateSamples[i5]) + (pssSampleCount2 * d)) / (totalMemoryUseCollection.processStateSamples[i5] + pssSampleCount2));
            totalMemoryUseCollection.processStateSamples[i5] = (int) (r7[i5] + pssSampleCount2);
            double[] dArr = totalMemoryUseCollection.processStateWeight;
            dArr[i5] = dArr[i5] + (d * value);
            i4++;
            pssAggr = pssAggr4;
            pssAggr2 = pssAggr2;
        }
    }

    public long computeProcessTimeLocked(int[] iArr, int[] iArr2, int[] iArr3, long j) {
        long j2 = 0;
        for (int i : iArr) {
            for (int i2 : iArr2) {
                for (int i3 : iArr3) {
                    j2 += getDuration(((i + i2) * 16) + i3, j);
                }
            }
        }
        this.mTmpTotalTime = j2;
        return j2;
    }

    public void dumpSummary(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, int[] iArr, int[] iArr2, int[] iArr3, long j, long j2) {
        printWriter.print(str);
        printWriter.print("* ");
        if (str2 != null) {
            printWriter.print(str2);
        }
        printWriter.print(this.mName);
        printWriter.print(" / ");
        android.os.UserHandle.formatUid(printWriter, this.mUid);
        printWriter.print(" / v");
        printWriter.print(this.mVersion);
        printWriter.println(":");
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABEL_TOTAL, iArr, iArr2, iArr3, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[0], iArr, iArr2, new int[]{0}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[1], iArr, iArr2, new int[]{1}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[2], iArr, iArr2, new int[]{2}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[4], iArr, iArr2, new int[]{4}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[3], iArr, iArr2, new int[]{3}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[5], iArr, iArr2, new int[]{5}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[6], iArr, iArr2, new int[]{6}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[7], iArr, iArr2, new int[]{7}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[8], iArr, iArr2, new int[]{8}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[9], iArr, iArr2, new int[]{9}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[10], iArr, iArr2, new int[]{10}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[11], iArr, iArr2, new int[]{11}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[12], iArr, iArr2, new int[]{12}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, com.android.internal.app.procstats.DumpUtils.STATE_LABELS[13], iArr, iArr2, new int[]{13}, j, j2, true);
    }

    public void dumpProcessState(java.io.PrintWriter printWriter, java.lang.String str, int[] iArr, int[] iArr2, int[] iArr3, long j) {
        int i;
        long j2;
        java.lang.String str2;
        long j3;
        int i2 = 0;
        long j4 = 0;
        int i3 = -1;
        while (i2 < iArr.length) {
            int i4 = 0;
            int i5 = -1;
            while (i4 < iArr2.length) {
                int i6 = i5;
                int i7 = 0;
                while (i7 < iArr3.length) {
                    int i8 = iArr[i2];
                    int i9 = iArr2[i4];
                    int i10 = ((i8 + i9) * 16) + iArr3[i7];
                    int i11 = i2;
                    int i12 = i4;
                    long valueForId = this.mDurations.getValueForId((byte) i10);
                    if (this.mCurCombinedState != i10) {
                        j2 = j4;
                        str2 = "";
                        j3 = valueForId;
                    } else {
                        j2 = j4;
                        str2 = " (running)";
                        j3 = valueForId + (j - this.mStartTime);
                    }
                    if (j3 == 0) {
                        j4 = j2;
                    } else {
                        printWriter.print(str);
                        if (iArr.length > 1) {
                            com.android.internal.app.procstats.DumpUtils.printScreenLabel(printWriter, i3 != i8 ? i8 : -1);
                            i3 = i8;
                        }
                        if (iArr2.length > 1) {
                            com.android.internal.app.procstats.DumpUtils.printMemLabel(printWriter, i6 != i9 ? i9 : -1, '/');
                            i6 = i9;
                        }
                        printWriter.print(com.android.internal.app.procstats.DumpUtils.STATE_LABELS[iArr3[i7]]);
                        printWriter.print(": ");
                        android.util.TimeUtils.formatDuration(j3, printWriter);
                        printWriter.println(str2);
                        j4 = j2 + j3;
                    }
                    i7++;
                    i2 = i11;
                    i4 = i12;
                }
                i4++;
                i5 = i6;
            }
            i2++;
        }
        if (j4 != 0) {
            printWriter.print(str);
            if (iArr.length <= 1) {
                i = -1;
            } else {
                i = -1;
                com.android.internal.app.procstats.DumpUtils.printScreenLabel(printWriter, -1);
            }
            if (iArr2.length > 1) {
                com.android.internal.app.procstats.DumpUtils.printMemLabel(printWriter, i, '/');
            }
            printWriter.print(com.android.internal.app.procstats.DumpUtils.STATE_LABEL_TOTAL);
            printWriter.print(": ");
            android.util.TimeUtils.formatDuration(j4, printWriter);
            printWriter.println();
        }
    }

    public void dumpPss(java.io.PrintWriter printWriter, java.lang.String str, int[] iArr, int[] iArr2, int[] iArr3, long j) {
        int i;
        int[] iArr4 = iArr;
        int i2 = 0;
        boolean z = false;
        int i3 = -1;
        while (i2 < iArr4.length) {
            int i4 = 0;
            int i5 = -1;
            while (i4 < iArr2.length) {
                int i6 = 0;
                while (i6 < iArr3.length) {
                    int i7 = iArr4[i2];
                    int i8 = iArr2[i4];
                    int key = this.mPssTable.getKey((byte) (((i7 + i8) * 16) + iArr3[i6]));
                    if (key == -1) {
                        i = i2;
                    } else {
                        long[] arrayForKey = this.mPssTable.getArrayForKey(key);
                        int indexFromKey = com.android.internal.app.procstats.SparseMappingTable.getIndexFromKey(key);
                        i = i2;
                        if (!z) {
                            printWriter.print(str);
                            printWriter.print("PSS/USS (");
                            printWriter.print(this.mPssTable.getKeyCount());
                            printWriter.println(" entries):");
                            z = true;
                        }
                        printWriter.print(str);
                        printWriter.print("  ");
                        if (iArr4.length > 1) {
                            com.android.internal.app.procstats.DumpUtils.printScreenLabel(printWriter, i3 != i7 ? i7 : -1);
                            i3 = i7;
                        }
                        if (iArr2.length > 1) {
                            com.android.internal.app.procstats.DumpUtils.printMemLabel(printWriter, i5 != i8 ? i8 : -1, '/');
                            i5 = i8;
                        }
                        printWriter.print(com.android.internal.app.procstats.DumpUtils.STATE_LABELS[iArr3[i6]]);
                        printWriter.print(": ");
                        dumpPssSamples(printWriter, arrayForKey, indexFromKey);
                        printWriter.println();
                    }
                    i6++;
                    iArr4 = iArr;
                    i2 = i;
                }
                i4++;
                iArr4 = iArr;
            }
            i2++;
            iArr4 = iArr;
        }
        long totalRunningDuration = getTotalRunningDuration(j);
        if (totalRunningDuration != 0) {
            printWriter.print(str);
            printWriter.print("Cur time ");
            android.util.TimeUtils.formatDuration(totalRunningDuration, printWriter);
            if (this.mTotalRunningStartTime != 0) {
                printWriter.print(" (running)");
            }
            if (this.mTotalRunningPss[0] != 0) {
                printWriter.print(": ");
                dumpPssSamples(printWriter, this.mTotalRunningPss, 0);
            }
            printWriter.println();
        }
        if (this.mNumExcessiveCpu != 0) {
            printWriter.print(str);
            printWriter.print("Killed for excessive CPU use: ");
            printWriter.print(this.mNumExcessiveCpu);
            printWriter.println(" times");
        }
        if (this.mNumCachedKill != 0) {
            printWriter.print(str);
            printWriter.print("Killed from cached state: ");
            printWriter.print(this.mNumCachedKill);
            printWriter.print(" times from pss ");
            android.util.DebugUtils.printSizeValue(printWriter, this.mMinCachedKillPss * 1024);
            printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            android.util.DebugUtils.printSizeValue(printWriter, this.mAvgCachedKillPss * 1024);
            printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            android.util.DebugUtils.printSizeValue(printWriter, this.mMaxCachedKillPss * 1024);
            printWriter.println();
        }
    }

    public static void dumpPssSamples(java.io.PrintWriter printWriter, long[] jArr, int i) {
        android.util.DebugUtils.printSizeValue(printWriter, jArr[i + 1] * 1024);
        printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        android.util.DebugUtils.printSizeValue(printWriter, jArr[i + 2] * 1024);
        printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        android.util.DebugUtils.printSizeValue(printWriter, jArr[i + 3] * 1024);
        printWriter.print("/");
        android.util.DebugUtils.printSizeValue(printWriter, jArr[i + 4] * 1024);
        printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        android.util.DebugUtils.printSizeValue(printWriter, jArr[i + 5] * 1024);
        printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        android.util.DebugUtils.printSizeValue(printWriter, jArr[i + 6] * 1024);
        printWriter.print("/");
        android.util.DebugUtils.printSizeValue(printWriter, jArr[i + 7] * 1024);
        printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        android.util.DebugUtils.printSizeValue(printWriter, jArr[i + 8] * 1024);
        printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        android.util.DebugUtils.printSizeValue(printWriter, jArr[i + 9] * 1024);
        printWriter.print(" over ");
        printWriter.print(jArr[i + 0]);
    }

    private void dumpProcessSummaryDetails(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, int[] iArr, int[] iArr2, int[] iArr3, long j, long j2, boolean z) {
        com.android.internal.app.procstats.ProcessStats.ProcessDataCollection processDataCollection = new com.android.internal.app.procstats.ProcessStats.ProcessDataCollection(iArr, iArr2, iArr3);
        computeProcessData(processDataCollection, j);
        if ((processDataCollection.totalTime / j2) * 100.0d >= 0.005d || processDataCollection.numPss != 0) {
            if (str != null) {
                printWriter.print(str);
            }
            if (str2 != null) {
                printWriter.print("  ");
                printWriter.print(str2);
                printWriter.print(": ");
            }
            processDataCollection.print(printWriter, j2, z);
            if (str != null) {
                printWriter.println();
            }
        }
    }

    void dumpInternalLocked(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, long j, long j2, boolean z) {
        if (z) {
            printWriter.print(str);
            printWriter.print("myID=");
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            printWriter.print(" mCommonProcess=");
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mCommonProcess)));
            printWriter.print(" mPackage=");
            printWriter.println(this.mPackage);
            if (this.mMultiPackage) {
                printWriter.print(str);
                printWriter.print("mMultiPackage=");
                printWriter.println(this.mMultiPackage);
            }
            if (this != this.mCommonProcess) {
                printWriter.print(str);
                printWriter.print("Common Proc: ");
                printWriter.print(this.mCommonProcess.mName);
                printWriter.print("/");
                printWriter.print(this.mCommonProcess.mUid);
                printWriter.print(" pkg=");
                printWriter.println(this.mCommonProcess.mPackage);
            }
            if (this.mCommonSources != null) {
                printWriter.print(str);
                printWriter.println("Aggregated Association Sources:");
                com.android.internal.app.procstats.AssociationState.dumpSources(printWriter, str + "  ", str + "    ", str + "        ", com.android.internal.app.procstats.AssociationState.createSortedAssociations(j2, j, this.mCommonSources), j2, j, str2, true, z);
            }
        }
        if (this.mActive) {
            printWriter.print(str);
            printWriter.print("mActive=");
            printWriter.println(this.mActive);
        }
        if (this.mDead) {
            printWriter.print(str);
            printWriter.print("mDead=");
            printWriter.println(this.mDead);
        }
        if (this.mNumActiveServices != 0 || this.mNumStartedServices != 0) {
            printWriter.print(str);
            printWriter.print("mNumActiveServices=");
            printWriter.print(this.mNumActiveServices);
            printWriter.print(" mNumStartedServices=");
            printWriter.println(this.mNumStartedServices);
        }
    }

    public void computeProcessData(com.android.internal.app.procstats.ProcessStats.ProcessDataCollection processDataCollection, long j) {
        long j2;
        int i;
        int i2;
        int i3;
        long j3;
        long j4 = 0;
        processDataCollection.totalTime = 0L;
        processDataCollection.maxRss = 0L;
        processDataCollection.avgRss = 0L;
        processDataCollection.minRss = 0L;
        processDataCollection.maxUss = 0L;
        processDataCollection.avgUss = 0L;
        processDataCollection.minUss = 0L;
        processDataCollection.maxPss = 0L;
        processDataCollection.avgPss = 0L;
        processDataCollection.minPss = 0L;
        processDataCollection.numPss = 0L;
        int i4 = 0;
        while (i4 < processDataCollection.screenStates.length) {
            int i5 = 0;
            while (i5 < processDataCollection.memStates.length) {
                int i6 = 0;
                while (i6 < processDataCollection.procStates.length) {
                    int i7 = ((processDataCollection.screenStates[i4] + processDataCollection.memStates[i5]) * 16) + processDataCollection.procStates[i6];
                    processDataCollection.totalTime += getDuration(i7, j);
                    long pssSampleCount = getPssSampleCount(i7);
                    if (pssSampleCount <= j4) {
                        j2 = j4;
                        i = i4;
                        i2 = i5;
                        i3 = i6;
                    } else {
                        long pssMinimum = getPssMinimum(i7);
                        i = i4;
                        long pssAverage = getPssAverage(i7);
                        long pssMaximum = getPssMaximum(i7);
                        long pssUssMinimum = getPssUssMinimum(i7);
                        i2 = i5;
                        i3 = i6;
                        long pssUssAverage = getPssUssAverage(i7);
                        long pssUssMaximum = getPssUssMaximum(i7);
                        long pssRssMinimum = getPssRssMinimum(i7);
                        long pssRssAverage = getPssRssAverage(i7);
                        long pssRssMaximum = getPssRssMaximum(i7);
                        j2 = 0;
                        if (processDataCollection.numPss == 0) {
                            processDataCollection.minPss = pssMinimum;
                            processDataCollection.avgPss = pssAverage;
                            processDataCollection.maxPss = pssMaximum;
                            processDataCollection.minUss = pssUssMinimum;
                            processDataCollection.avgUss = pssUssAverage;
                            processDataCollection.maxUss = pssUssMaximum;
                            processDataCollection.minRss = pssRssMinimum;
                            processDataCollection.avgRss = pssRssAverage;
                            processDataCollection.maxRss = pssRssMaximum;
                            j3 = pssSampleCount;
                        } else {
                            if (pssMinimum < processDataCollection.minPss) {
                                processDataCollection.minPss = pssMinimum;
                            }
                            j3 = pssSampleCount;
                            double d = j3;
                            processDataCollection.avgPss = (long) (((processDataCollection.avgPss * processDataCollection.numPss) + (pssAverage * d)) / (processDataCollection.numPss + j3));
                            if (pssMaximum > processDataCollection.maxPss) {
                                processDataCollection.maxPss = pssMaximum;
                            }
                            if (pssUssMinimum < processDataCollection.minUss) {
                                processDataCollection.minUss = pssUssMinimum;
                            }
                            processDataCollection.avgUss = (long) (((processDataCollection.avgUss * processDataCollection.numPss) + (pssUssAverage * d)) / (processDataCollection.numPss + j3));
                            if (pssUssMaximum > processDataCollection.maxUss) {
                                processDataCollection.maxUss = pssUssMaximum;
                            }
                            if (pssRssMinimum < processDataCollection.minRss) {
                                processDataCollection.minRss = pssRssMinimum;
                            }
                            processDataCollection.avgRss = (long) (((processDataCollection.avgRss * processDataCollection.numPss) + (pssRssAverage * d)) / (processDataCollection.numPss + j3));
                            if (pssRssMaximum > processDataCollection.maxRss) {
                                processDataCollection.maxRss = pssRssMaximum;
                            }
                        }
                        processDataCollection.numPss += j3;
                    }
                    i6 = i3 + 1;
                    i4 = i;
                    j4 = j2;
                    i5 = i2;
                }
                i5++;
            }
            i4++;
        }
    }

    public void dumpCsv(java.io.PrintWriter printWriter, boolean z, int[] iArr, boolean z2, int[] iArr2, boolean z3, int[] iArr3, long j) {
        int[] iArr4 = iArr;
        int[] iArr5 = iArr2;
        int[] iArr6 = iArr3;
        int length = z ? iArr4.length : 1;
        int length2 = z2 ? iArr5.length : 1;
        int length3 = z3 ? iArr6.length : 1;
        int i = 0;
        while (i < length) {
            int i2 = 0;
            while (i2 < length2) {
                int i3 = 0;
                while (i3 < length3) {
                    int i4 = z ? iArr4[i] : 0;
                    int i5 = z2 ? iArr5[i2] : 0;
                    int i6 = z3 ? iArr6[i3] : 0;
                    int length4 = z ? 1 : iArr4.length;
                    int length5 = z2 ? 1 : iArr5.length;
                    int length6 = z3 ? 1 : iArr6.length;
                    int i7 = length;
                    int i8 = length2;
                    long j2 = 0;
                    int i9 = length3;
                    int i10 = 0;
                    while (i10 < length4) {
                        long j3 = j2;
                        int i11 = 0;
                        while (i11 < length5) {
                            int i12 = 0;
                            while (i12 < length6) {
                                j3 += getDuration(((i4 + (z ? 0 : iArr4[i10]) + i5 + (z2 ? 0 : iArr5[i11])) * 16) + i6 + (z3 ? 0 : iArr6[i12]), j);
                                i12++;
                                iArr4 = iArr;
                                iArr5 = iArr2;
                                iArr6 = iArr3;
                                length5 = length5;
                            }
                            i11++;
                            iArr4 = iArr;
                            iArr5 = iArr2;
                            iArr6 = iArr3;
                            length5 = length5;
                        }
                        i10++;
                        iArr4 = iArr;
                        iArr5 = iArr2;
                        iArr6 = iArr3;
                        length5 = length5;
                        j2 = j3;
                    }
                    printWriter.print("\t");
                    printWriter.print(j2);
                    i3++;
                    iArr4 = iArr;
                    iArr5 = iArr2;
                    iArr6 = iArr3;
                    length3 = i9;
                    length = i7;
                    length2 = i8;
                }
                i2++;
                iArr4 = iArr;
                iArr5 = iArr2;
                iArr6 = iArr3;
            }
            i++;
            iArr4 = iArr;
            iArr5 = iArr2;
            iArr6 = iArr3;
        }
    }

    public void dumpPackageProcCheckin(java.io.PrintWriter printWriter, java.lang.String str, int i, long j, java.lang.String str2, long j2) {
        printWriter.print("pkgproc,");
        printWriter.print(str);
        printWriter.print(",");
        printWriter.print(i);
        printWriter.print(",");
        printWriter.print(j);
        printWriter.print(",");
        printWriter.print(com.android.internal.app.procstats.DumpUtils.collapseString(str, str2));
        dumpAllStateCheckin(printWriter, j2);
        printWriter.println();
        if (this.mPssTable.getKeyCount() > 0) {
            printWriter.print("pkgpss,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print(j);
            printWriter.print(",");
            printWriter.print(com.android.internal.app.procstats.DumpUtils.collapseString(str, str2));
            dumpAllPssCheckin(printWriter);
            printWriter.println();
        }
        if (this.mTotalRunningPss[0] != 0) {
            printWriter.print("pkgrun,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print(j);
            printWriter.print(",");
            printWriter.print(com.android.internal.app.procstats.DumpUtils.collapseString(str, str2));
            printWriter.print(",");
            printWriter.print(getTotalRunningDuration(j2));
            printWriter.print(",");
            dumpPssSamplesCheckin(printWriter, this.mTotalRunningPss, 0);
            printWriter.println();
        }
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            printWriter.print("pkgkills,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print(j);
            printWriter.print(",");
            printWriter.print(com.android.internal.app.procstats.DumpUtils.collapseString(str, str2));
            printWriter.print(",");
            printWriter.print(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
            printWriter.print(",");
            printWriter.print(this.mNumExcessiveCpu);
            printWriter.print(",");
            printWriter.print(this.mNumCachedKill);
            printWriter.print(",");
            printWriter.print(this.mMinCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mAvgCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mMaxCachedKillPss);
            printWriter.println();
        }
    }

    public void dumpProcCheckin(java.io.PrintWriter printWriter, java.lang.String str, int i, long j) {
        if (this.mDurations.getKeyCount() > 0) {
            printWriter.print("proc,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            dumpAllStateCheckin(printWriter, j);
            printWriter.println();
        }
        if (this.mPssTable.getKeyCount() > 0) {
            printWriter.print("pss,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            dumpAllPssCheckin(printWriter);
            printWriter.println();
        }
        if (this.mTotalRunningPss[0] != 0) {
            printWriter.print("procrun,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print(getTotalRunningDuration(j));
            printWriter.print(",");
            dumpPssSamplesCheckin(printWriter, this.mTotalRunningPss, 0);
            printWriter.println();
        }
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            printWriter.print("kills,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
            printWriter.print(",");
            printWriter.print(this.mNumExcessiveCpu);
            printWriter.print(",");
            printWriter.print(this.mNumCachedKill);
            printWriter.print(",");
            printWriter.print(this.mMinCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mAvgCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mMaxCachedKillPss);
            printWriter.println();
        }
    }

    public void dumpAllStateCheckin(java.io.PrintWriter printWriter, long j) {
        boolean z = false;
        for (int i = 0; i < this.mDurations.getKeyCount(); i++) {
            int keyAt = this.mDurations.getKeyAt(i);
            byte idFromKey = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt);
            long value = this.mDurations.getValue(keyAt);
            if (this.mCurCombinedState == idFromKey) {
                value += j - this.mStartTime;
                z = true;
            }
            com.android.internal.app.procstats.DumpUtils.printProcStateTagAndValue(printWriter, idFromKey, value);
        }
        if (!z && this.mCurCombinedState != -1) {
            com.android.internal.app.procstats.DumpUtils.printProcStateTagAndValue(printWriter, this.mCurCombinedState, j - this.mStartTime);
        }
    }

    public void dumpAllPssCheckin(java.io.PrintWriter printWriter) {
        int keyCount = this.mPssTable.getKeyCount();
        for (int i = 0; i < keyCount; i++) {
            int keyAt = this.mPssTable.getKeyAt(i);
            byte idFromKey = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt);
            printWriter.print(',');
            com.android.internal.app.procstats.DumpUtils.printProcStateTag(printWriter, idFromKey);
            printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
            dumpPssSamplesCheckin(printWriter, this.mPssTable.getArrayForKey(keyAt), com.android.internal.app.procstats.SparseMappingTable.getIndexFromKey(keyAt));
        }
    }

    public static void dumpPssSamplesCheckin(java.io.PrintWriter printWriter, long[] jArr, int i) {
        printWriter.print(jArr[i + 0]);
        printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 1]);
        printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 2]);
        printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 3]);
        printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 4]);
        printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 5]);
        printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 6]);
        printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 7]);
        printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 8]);
        printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 9]);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ProcessState{").append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this))).append(" ").append(this.mName).append("/").append(this.mUid).append(" pkg=").append(this.mPackage);
        if (this.mMultiPackage) {
            sb.append(" (multi)");
        }
        if (this.mCommonProcess != this) {
            sb.append(" (sub)");
        }
        sb.append("}");
        return sb.toString();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.lang.String str, int i, long j2) {
        long j3;
        long j4;
        int i2;
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, str);
        protoOutputStream.write(1120986464258L, i);
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            long start2 = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1120986464257L, this.mNumExcessiveCpu);
            protoOutputStream.write(1120986464258L, this.mNumCachedKill);
            j3 = start;
            android.util.proto.ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268035L, this.mMinCachedKillPss, this.mAvgCachedKillPss, this.mMaxCachedKillPss);
            protoOutputStream.end(start2);
        } else {
            j3 = start;
        }
        android.util.SparseLongArray sparseLongArray = new android.util.SparseLongArray();
        boolean z = false;
        for (int i3 = 0; i3 < this.mDurations.getKeyCount(); i3++) {
            int keyAt = this.mDurations.getKeyAt(i3);
            byte idFromKey = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt);
            long value = this.mDurations.getValue(keyAt);
            if (this.mCurCombinedState == idFromKey) {
                value += j2 - this.mStartTime;
                z = true;
            }
            sparseLongArray.put(idFromKey, value);
        }
        if (!z && this.mCurCombinedState != -1) {
            sparseLongArray.put(this.mCurCombinedState, j2 - this.mStartTime);
        }
        int i4 = 0;
        while (true) {
            j4 = 2246267895813L;
            if (i4 >= this.mPssTable.getKeyCount()) {
                break;
            }
            int keyAt2 = this.mPssTable.getKeyAt(i4);
            byte idFromKey2 = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt2);
            if (sparseLongArray.indexOfKey(idFromKey2) < 0) {
                i2 = i4;
            } else {
                long start3 = protoOutputStream.start(2246267895813L);
                i2 = i4;
                com.android.internal.app.procstats.DumpUtils.printProcStateTagProto(protoOutputStream, 1159641169921L, 1159641169922L, 1159641169923L, idFromKey2);
                long j5 = sparseLongArray.get(idFromKey2);
                sparseLongArray.delete(idFromKey2);
                protoOutputStream.write(1112396529668L, j5);
                this.mPssTable.writeStatsToProtoForKey(protoOutputStream, keyAt2);
                protoOutputStream.end(start3);
            }
            i4 = i2 + 1;
        }
        int i5 = 0;
        while (i5 < sparseLongArray.size()) {
            long start4 = protoOutputStream.start(j4);
            com.android.internal.app.procstats.DumpUtils.printProcStateTagProto(protoOutputStream, 1159641169921L, 1159641169922L, 1159641169923L, sparseLongArray.keyAt(i5));
            protoOutputStream.write(1112396529668L, sparseLongArray.valueAt(i5));
            protoOutputStream.end(start4);
            i5++;
            j4 = j4;
        }
        long totalRunningDuration = getTotalRunningDuration(j2);
        if (totalRunningDuration > 0) {
            long start5 = protoOutputStream.start(1146756268038L);
            protoOutputStream.write(1112396529668L, totalRunningDuration);
            if (this.mTotalRunningPss[0] != 0) {
                com.android.internal.app.procstats.PssTable.writeStatsToProto(protoOutputStream, this.mTotalRunningPss, 0);
            }
            protoOutputStream.end(start5);
        }
        protoOutputStream.end(j3);
    }

    static void writeCompressedProcessName(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.lang.String str, java.lang.String str2, boolean z) {
        if (z) {
            protoOutputStream.write(j, str);
            return;
        }
        if (android.text.TextUtils.equals(str, str2)) {
            return;
        }
        if (str.startsWith(str2)) {
            int length = str2.length();
            if (str.charAt(length) == ':') {
                protoOutputStream.write(j, str.substring(length));
                return;
            }
        }
        protoOutputStream.write(j, str);
    }

    public void dumpStateDurationToStatsd(int i, com.android.internal.app.procstats.ProcessStats processStats, com.android.internal.app.procstats.StatsEventOutput statsEventOutput) {
        int keyCount = this.mDurations.getKeyCount();
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        long j6 = 0;
        long j7 = 0;
        int i2 = 0;
        long j8 = 0;
        while (i2 < keyCount) {
            int i3 = keyCount;
            int keyAt = this.mDurations.getKeyAt(i2);
            int idFromKey = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt) % 16;
            int i4 = i2;
            long value = this.mDurations.getValue(keyAt);
            switch (idFromKey) {
                case 0:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    j7 += value;
                    j = j;
                    break;
                case 1:
                    j6 += value;
                    break;
                case 2:
                    j2 += value;
                    break;
                case 3:
                    j8 += value;
                    break;
                case 4:
                    j3 += value;
                    break;
                case 5:
                case 6:
                    j4 += value;
                    break;
                case 14:
                    j5 += value;
                    break;
                case 15:
                    j += value;
                    break;
                default:
                    j = j;
                    break;
            }
            i2 = i4 + 1;
            keyCount = i3;
        }
        statsEventOutput.write(i, getUid(), getName(), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(processStats.mTimePeriodStartUptime), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(processStats.mTimePeriodEndUptime), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(processStats.mTimePeriodEndUptime - processStats.mTimePeriodStartUptime), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(j6), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(j8), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(j2), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(j3), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(j4), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(j5), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(j), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(j7));
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x0159 A[LOOP:3: B:70:0x0153->B:72:0x0159, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dumpAggregatedProtoForStatsd(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.lang.String str, int i, long j2, com.android.internal.app.ProcessMap<android.util.ArraySet<com.android.internal.app.procstats.ProcessStats.PackageState>> processMap, android.util.SparseArray<android.util.ArraySet<java.lang.String>> sparseArray) {
        boolean z;
        int i2;
        android.util.proto.ProtoOutputStream protoOutputStream2 = protoOutputStream;
        android.util.SparseLongArray sparseLongArray = new android.util.SparseLongArray();
        boolean z2 = false;
        for (int i3 = 0; i3 < this.mDurations.getKeyCount(); i3++) {
            int keyAt = this.mDurations.getKeyAt(i3);
            byte idFromKey = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt);
            int aggregateCurrentProcessState = com.android.internal.app.procstats.DumpUtils.aggregateCurrentProcessState(idFromKey);
            if (idFromKey % 16 != 9) {
                long value = this.mDurations.getValue(keyAt);
                if (this.mCurCombinedState == idFromKey) {
                    value += j2 - this.mStartTime;
                    z2 = true;
                }
                int indexOfKey = sparseLongArray.indexOfKey(aggregateCurrentProcessState);
                if (indexOfKey >= 0) {
                    sparseLongArray.put(aggregateCurrentProcessState, value + sparseLongArray.valueAt(indexOfKey));
                } else {
                    sparseLongArray.put(aggregateCurrentProcessState, value);
                }
            }
        }
        if (!z2 && this.mCurCombinedState != -1 && this.mCurCombinedState % 16 != 9) {
            int aggregateCurrentProcessState2 = com.android.internal.app.procstats.DumpUtils.aggregateCurrentProcessState(this.mCurCombinedState);
            int indexOfKey2 = sparseLongArray.indexOfKey(aggregateCurrentProcessState2);
            if (indexOfKey2 >= 0) {
                sparseLongArray.put(aggregateCurrentProcessState2, (j2 - this.mStartTime) + sparseLongArray.valueAt(indexOfKey2));
            } else {
                sparseLongArray.put(aggregateCurrentProcessState2, j2 - this.mStartTime);
            }
        }
        android.util.SparseLongArray sparseLongArray2 = new android.util.SparseLongArray();
        android.util.SparseLongArray sparseLongArray3 = new android.util.SparseLongArray();
        for (int i4 = 0; i4 < this.mPssTable.getKeyCount(); i4++) {
            int keyAt2 = this.mPssTable.getKeyAt(i4);
            byte idFromKey2 = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt2);
            int aggregateCurrentProcessState3 = com.android.internal.app.procstats.DumpUtils.aggregateCurrentProcessState(idFromKey2);
            if (sparseLongArray.indexOfKey(aggregateCurrentProcessState3) >= 0) {
                long[] rssMeanAndMax = this.mPssTable.getRssMeanAndMax(keyAt2);
                long valueForId = rssMeanAndMax[0] * this.mDurations.getValueForId(idFromKey2);
                if (sparseLongArray2.indexOfKey(aggregateCurrentProcessState3) >= 0) {
                    sparseLongArray2.put(aggregateCurrentProcessState3, valueForId + sparseLongArray2.get(aggregateCurrentProcessState3));
                } else {
                    sparseLongArray2.put(aggregateCurrentProcessState3, valueForId);
                }
                if (sparseLongArray3.indexOfKey(aggregateCurrentProcessState3) >= 0 && sparseLongArray3.get(aggregateCurrentProcessState3) < rssMeanAndMax[1]) {
                    sparseLongArray3.put(aggregateCurrentProcessState3, rssMeanAndMax[1]);
                } else if (sparseLongArray3.indexOfKey(aggregateCurrentProcessState3) < 0) {
                    sparseLongArray3.put(aggregateCurrentProcessState3, rssMeanAndMax[1]);
                }
            }
        }
        for (int i5 = 0; i5 < sparseLongArray.size(); i5++) {
            int keyAt3 = sparseLongArray.keyAt(i5);
            if (sparseLongArray2.indexOfKey(keyAt3) >= 0) {
                long j3 = sparseLongArray.get(keyAt3);
                sparseLongArray2.put(keyAt3, j3 > 0 ? sparseLongArray2.get(keyAt3) / j3 : sparseLongArray2.get(keyAt3));
            }
        }
        long start = protoOutputStream.start(j);
        java.lang.String str2 = this.mPackage;
        if (!this.mMultiPackage && sparseArray.get(this.mUid).size() <= 1) {
            z = false;
            writeCompressedProcessName(protoOutputStream, 1138166333441L, str, str2, z);
            protoOutputStream2.write(1120986464258L, i);
            i2 = 0;
            while (i2 < sparseLongArray.size()) {
                long start2 = protoOutputStream2.start(2246267895813L);
                int keyAt4 = sparseLongArray.keyAt(i2);
                com.android.internal.app.procstats.DumpUtils.printAggregatedProcStateTagProto(protoOutputStream, 1159641169921L, 1159641169930L, keyAt4);
                protoOutputStream2.write(1112396529668L, sparseLongArray.get(keyAt4));
                android.util.proto.ProtoOutputStream protoOutputStream3 = protoOutputStream2;
                android.util.proto.ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268040L, 0L, 0L, 0L, (int) sparseLongArray2.get(keyAt4), (int) sparseLongArray3.get(keyAt4));
                protoOutputStream3.end(start2);
                i2++;
                start = start;
                protoOutputStream2 = protoOutputStream3;
                sparseLongArray = sparseLongArray;
                sparseLongArray2 = sparseLongArray2;
                sparseLongArray3 = sparseLongArray3;
            }
            this.mStats.dumpFilteredAssociationStatesProtoForProc(protoOutputStream, 2246267895815L, j2, this, sparseArray);
            protoOutputStream2.end(start);
        }
        z = true;
        writeCompressedProcessName(protoOutputStream, 1138166333441L, str, str2, z);
        protoOutputStream2.write(1120986464258L, i);
        i2 = 0;
        while (i2 < sparseLongArray.size()) {
        }
        this.mStats.dumpFilteredAssociationStatesProtoForProc(protoOutputStream, 2246267895815L, j2, this, sparseArray);
        protoOutputStream2.end(start);
    }
}
