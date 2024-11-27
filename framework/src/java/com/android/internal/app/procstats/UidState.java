package com.android.internal.app.procstats;

/* loaded from: classes4.dex */
public final class UidState {
    private static final java.lang.String TAG = "ProcessStats";
    private final com.android.internal.app.procstats.DurationsTable mDurations;
    private long mStartTime;
    private final com.android.internal.app.procstats.ProcessStats mStats;
    private long mTotalRunningDuration;
    private long mTotalRunningStartTime;
    private final int mUid;
    private android.util.ArraySet<com.android.internal.app.procstats.ProcessState> mProcesses = new android.util.ArraySet<>();
    private int mCurCombinedState = -1;

    public UidState(com.android.internal.app.procstats.ProcessStats processStats, int i) {
        this.mStats = processStats;
        this.mUid = i;
        this.mDurations = new com.android.internal.app.procstats.DurationsTable(processStats.mTableData);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public com.android.internal.app.procstats.UidState m6774clone() {
        com.android.internal.app.procstats.UidState uidState = new com.android.internal.app.procstats.UidState(this.mStats, this.mUid);
        uidState.mDurations.addDurations(this.mDurations);
        uidState.mCurCombinedState = this.mCurCombinedState;
        uidState.mStartTime = this.mStartTime;
        uidState.mTotalRunningStartTime = this.mTotalRunningStartTime;
        uidState.mTotalRunningDuration = this.mTotalRunningDuration;
        return uidState;
    }

    public void updateCombinedState(int i, long j) {
        if (this.mCurCombinedState != i) {
            updateCombinedState(j);
        }
    }

    public void updateCombinedState(long j) {
        setCombinedStateInner(calcCombinedState(), j);
    }

    private int calcCombinedState() {
        int size = this.mProcesses.size();
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < size; i3++) {
            int combinedState = this.mProcesses.valueAt(i3).getCombinedState();
            int i4 = combinedState % 16;
            if (combinedState != -1 && (i2 == -1 || i4 < i2)) {
                i = combinedState;
                i2 = i4;
            }
        }
        return i;
    }

    private void setCombinedStateInner(int i, long j) {
        if (this.mCurCombinedState != i) {
            if (j >= 0) {
                commitStateTime(j);
                if (i != -1) {
                    if (this.mCurCombinedState == -1) {
                        this.mTotalRunningDuration = 0L;
                    }
                } else {
                    this.mTotalRunningDuration += j - this.mTotalRunningStartTime;
                }
            }
            this.mCurCombinedState = i;
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
    }

    public void resetSafely(long j) {
        this.mDurations.resetTable();
        this.mStartTime = j;
        this.mProcesses.removeIf(new java.util.function.Predicate() { // from class: com.android.internal.app.procstats.UidState$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return com.android.internal.app.procstats.UidState.lambda$resetSafely$0((com.android.internal.app.procstats.ProcessState) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$resetSafely$0(com.android.internal.app.procstats.ProcessState processState) {
        return !processState.isInUse();
    }

    public boolean isInUse() {
        int size = this.mProcesses.size();
        for (int i = 0; i < size; i++) {
            if (this.mProcesses.valueAt(i).isInUse()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPackage(java.lang.String str) {
        int size = this.mProcesses.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.app.procstats.ProcessState valueAt = this.mProcesses.valueAt(i);
            if (android.text.TextUtils.equals(str, valueAt.getName()) && android.text.TextUtils.equals(str, valueAt.getPackage())) {
                return true;
            }
        }
        return false;
    }

    public void add(com.android.internal.app.procstats.UidState uidState) {
        this.mDurations.addDurations(uidState.mDurations);
        this.mTotalRunningDuration += uidState.mTotalRunningDuration;
    }

    void addProcess(com.android.internal.app.procstats.ProcessState processState) {
        this.mProcesses.add(processState);
    }

    void addProcess(com.android.internal.app.procstats.ProcessState processState, long j) {
        this.mProcesses.add(processState);
        setCombinedStateInner(processState.getCombinedState(), j);
    }

    void removeProcess(com.android.internal.app.procstats.ProcessState processState, long j) {
        this.mProcesses.remove(processState);
        setCombinedStateInner(processState.getCombinedState(), j);
    }

    public int getDurationsBucketCount() {
        return this.mDurations.getKeyCount();
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

    public long[] getAggregatedDurationsInStates() {
        long[] jArr = new long[16];
        int durationsBucketCount = getDurationsBucketCount();
        for (int i = 0; i < durationsBucketCount; i++) {
            int keyAt = this.mDurations.getKeyAt(i);
            int idFromKey = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt) % 16;
            jArr[idFromKey] = jArr[idFromKey] + this.mDurations.getValue(keyAt);
        }
        return jArr;
    }

    void writeToParcel(android.os.Parcel parcel, long j) {
        this.mDurations.writeToParcel(parcel);
        parcel.writeLong(getTotalRunningDuration(j));
    }

    boolean readFromParcel(android.os.Parcel parcel) {
        if (!this.mDurations.readFromParcel(parcel)) {
            return false;
        }
        this.mTotalRunningDuration = parcel.readLong();
        return true;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("UidState{").append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this))).append(" ").append(android.os.UserHandle.formatUid(this.mUid)).append("}");
        return sb.toString();
    }

    void dumpState(java.io.PrintWriter printWriter, java.lang.String str, int[] iArr, int[] iArr2, int[] iArr3, long j) {
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
}
