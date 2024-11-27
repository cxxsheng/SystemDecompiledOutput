package com.android.internal.app.procstats;

/* loaded from: classes4.dex */
public final class AssociationState {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ProcessStats";
    private static final boolean VALIDATE_TIMES = false;
    private final java.lang.String mName;
    private final com.android.internal.app.procstats.ProcessStats.PackageState mPackageState;
    private com.android.internal.app.procstats.ProcessState mProc;
    private final java.lang.String mProcessName;
    private final com.android.internal.app.procstats.ProcessStats mProcessStats;
    final android.util.ArrayMap<com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceState> mSources = new android.util.ArrayMap<>();
    private int mTotalActiveCount;
    private long mTotalActiveDuration;
    private int mTotalActiveNesting;
    private long mTotalActiveStartUptime;
    private int mTotalCount;
    private long mTotalDuration;
    private int mTotalNesting;
    private long mTotalStartUptime;
    private static final com.android.internal.app.procstats.AssociationState.SourceKey sTmpSourceKey = new com.android.internal.app.procstats.AssociationState.SourceKey(0, (java.lang.String) null, (java.lang.String) null);
    static final java.util.Comparator<android.util.Pair<com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceDumpContainer>> ASSOCIATION_COMPARATOR = new java.util.Comparator() { // from class: com.android.internal.app.procstats.AssociationState$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            return com.android.internal.app.procstats.AssociationState.lambda$static$0((android.util.Pair) obj, (android.util.Pair) obj2);
        }
    };

    public static final class SourceState implements android.os.Parcelable {
        int mActiveCount;
        long mActiveDuration;
        com.android.internal.app.procstats.DurationsTable mActiveDurations;
        int mActiveNesting;
        long mActiveStartUptime;
        private final com.android.internal.app.procstats.AssociationState mAssociationState;
        private com.android.internal.app.procstats.AssociationState.SourceState mCommonSourceState;
        int mCount;
        long mDuration;
        boolean mInTrackingList;
        final com.android.internal.app.procstats.AssociationState.SourceKey mKey;
        int mNesting;
        private final com.android.internal.app.procstats.ProcessStats mProcessStats;
        long mStartUptime;
        private final com.android.internal.app.procstats.ProcessState mTargetProcess;
        long mTrackingUptime;
        int mProcStateSeq = -1;
        int mProcState = -1;
        int mActiveProcState = -1;

        SourceState(com.android.internal.app.procstats.ProcessStats processStats, com.android.internal.app.procstats.AssociationState associationState, com.android.internal.app.procstats.ProcessState processState, com.android.internal.app.procstats.AssociationState.SourceKey sourceKey) {
            this.mProcessStats = processStats;
            this.mAssociationState = associationState;
            this.mTargetProcess = processState;
            this.mKey = sourceKey;
        }

        public com.android.internal.app.procstats.AssociationState getAssociationState() {
            return this.mAssociationState;
        }

        public java.lang.String getProcessName() {
            return this.mKey.mProcess;
        }

        public int getUid() {
            return this.mKey.mUid;
        }

        private com.android.internal.app.procstats.AssociationState.SourceState getCommonSourceState(boolean z) {
            if (this.mCommonSourceState == null && z) {
                this.mCommonSourceState = this.mTargetProcess.getOrCreateSourceState(this.mKey);
            }
            return this.mCommonSourceState;
        }

        public void trackProcState(int i, int i2, long j) {
            com.android.internal.app.procstats.AssociationState.SourceState commonSourceState;
            int i3 = com.android.internal.app.procstats.ProcessState.PROCESS_STATE_TO_STATE[i];
            if (i2 != this.mProcStateSeq) {
                this.mProcStateSeq = i2;
                this.mProcState = i3;
            } else if (i3 < this.mProcState) {
                this.mProcState = i3;
            }
            if (i3 < 12 && !this.mInTrackingList) {
                this.mInTrackingList = true;
                this.mTrackingUptime = j;
                if (this.mAssociationState != null) {
                    this.mProcessStats.mTrackingAssociations.add(this);
                }
            }
            if (this.mAssociationState != null && (commonSourceState = getCommonSourceState(true)) != null) {
                commonSourceState.trackProcState(i, i2, j);
            }
        }

        long start() {
            com.android.internal.app.procstats.AssociationState.SourceState commonSourceState;
            long start = start(-1L);
            if (this.mAssociationState != null && (commonSourceState = getCommonSourceState(true)) != null) {
                commonSourceState.start(start);
            }
            return start;
        }

        long start(long j) {
            this.mNesting++;
            if (this.mNesting == 1) {
                if (j < 0) {
                    j = android.os.SystemClock.uptimeMillis();
                }
                this.mCount++;
                this.mStartUptime = j;
            }
            return j;
        }

        public void stop() {
            com.android.internal.app.procstats.AssociationState.SourceState commonSourceState;
            long stop = stop(-1L);
            if (this.mAssociationState != null && (commonSourceState = getCommonSourceState(false)) != null) {
                commonSourceState.stop(stop);
            }
        }

        long stop(long j) {
            this.mNesting--;
            if (this.mNesting == 0) {
                if (j < 0) {
                    j = android.os.SystemClock.uptimeMillis();
                }
                this.mDuration += j - this.mStartUptime;
                stopTracking(j);
            }
            return j;
        }

        void startActive(long j) {
            com.android.internal.app.procstats.AssociationState.SourceState commonSourceState;
            boolean z = false;
            if (this.mInTrackingList) {
                if (this.mActiveStartUptime == 0) {
                    this.mActiveStartUptime = j;
                    this.mActiveNesting++;
                    this.mActiveCount++;
                    if (this.mAssociationState != null) {
                        this.mAssociationState.mTotalActiveNesting++;
                        if (this.mAssociationState.mTotalActiveNesting == 1) {
                            this.mAssociationState.mTotalActiveCount++;
                            this.mAssociationState.mTotalActiveStartUptime = j;
                        }
                    }
                    z = true;
                } else if (this.mAssociationState == null) {
                    this.mActiveNesting++;
                }
                if (this.mActiveProcState != this.mProcState) {
                    if (this.mActiveProcState != -1) {
                        long j2 = (this.mActiveDuration + j) - this.mActiveStartUptime;
                        this.mActiveStartUptime = j;
                        if (this.mAssociationState != null) {
                            z = true;
                        }
                        if (j2 != 0) {
                            if (this.mActiveDurations == null) {
                                makeDurations();
                            }
                            this.mActiveDurations.addDuration(this.mActiveProcState, j2);
                            this.mActiveDuration = 0L;
                        }
                    }
                    this.mActiveProcState = this.mProcState;
                }
            } else if (this.mAssociationState != null) {
                android.util.Slog.wtf("ProcessStats", "startActive while not tracking: " + this);
            }
            if (this.mAssociationState != null && (commonSourceState = getCommonSourceState(true)) != null && z) {
                commonSourceState.startActive(j);
            }
        }

        void stopActive(long j) {
            boolean z;
            com.android.internal.app.procstats.AssociationState.SourceState commonSourceState;
            if (this.mActiveStartUptime == 0) {
                z = false;
            } else {
                if (!this.mInTrackingList && this.mAssociationState != null) {
                    android.util.Slog.wtf("ProcessStats", "stopActive while not tracking: " + this);
                }
                this.mActiveNesting--;
                long j2 = j - this.mActiveStartUptime;
                this.mActiveStartUptime = (this.mAssociationState != null || this.mActiveNesting == 0) ? 0L : j;
                z = this.mActiveStartUptime == 0;
                if (this.mActiveDurations != null) {
                    this.mActiveDurations.addDuration(this.mActiveProcState, j2);
                } else {
                    this.mActiveDuration += j2;
                }
                if (this.mAssociationState != null) {
                    this.mAssociationState.mTotalActiveNesting--;
                    if (this.mAssociationState.mTotalActiveNesting == 0) {
                        this.mAssociationState.mTotalActiveDuration += j - this.mAssociationState.mTotalActiveStartUptime;
                        this.mAssociationState.mTotalActiveStartUptime = 0L;
                    }
                }
            }
            if (this.mAssociationState != null && (commonSourceState = getCommonSourceState(false)) != null && z) {
                commonSourceState.stopActive(j);
            }
        }

        boolean stopActiveIfNecessary(int i, long j) {
            if (this.mProcStateSeq != i || this.mProcState >= 12) {
                stopActive(j);
                stopTrackingProcState();
                return true;
            }
            return false;
        }

        private void stopTrackingProcState() {
            com.android.internal.app.procstats.AssociationState.SourceState commonSourceState;
            this.mInTrackingList = false;
            this.mProcState = -1;
            if (this.mAssociationState != null && (commonSourceState = getCommonSourceState(false)) != null) {
                commonSourceState.stopTrackingProcState();
            }
        }

        boolean isInUse() {
            return this.mNesting > 0;
        }

        void resetSafely(long j) {
            com.android.internal.app.procstats.AssociationState.SourceState commonSourceState;
            if (isInUse()) {
                this.mCount = 1;
                this.mStartUptime = j;
                this.mDuration = 0L;
                if (this.mActiveStartUptime > 0) {
                    this.mActiveCount = 1;
                    this.mActiveStartUptime = j;
                } else {
                    this.mActiveCount = 0;
                }
                this.mActiveDuration = 0L;
                this.mActiveDurations = null;
            }
            if (this.mAssociationState != null && (commonSourceState = getCommonSourceState(false)) != null) {
                commonSourceState.resetSafely(j);
                this.mCommonSourceState = null;
            }
        }

        void commitStateTime(long j) {
            if (this.mNesting > 0) {
                this.mDuration += j - this.mStartUptime;
                this.mStartUptime = j;
            }
            if (this.mActiveStartUptime > 0) {
                long j2 = j - this.mActiveStartUptime;
                this.mActiveStartUptime = j;
                if (this.mActiveDurations != null) {
                    this.mActiveDurations.addDuration(this.mActiveProcState, j2);
                } else {
                    this.mActiveDuration += j2;
                }
            }
        }

        void makeDurations() {
            this.mActiveDurations = new com.android.internal.app.procstats.DurationsTable(this.mProcessStats.mTableData);
        }

        private void stopTracking(long j) {
            if (this.mAssociationState != null) {
                com.android.internal.app.procstats.AssociationState associationState = this.mAssociationState;
                associationState.mTotalNesting--;
                if (this.mAssociationState.mTotalNesting == 0) {
                    this.mAssociationState.mTotalDuration += j - this.mAssociationState.mTotalStartUptime;
                }
            }
            stopActive(j);
            if (this.mInTrackingList) {
                this.mInTrackingList = false;
                this.mProcState = -1;
                if (this.mAssociationState != null) {
                    java.util.ArrayList<com.android.internal.app.procstats.AssociationState.SourceState> arrayList = this.mProcessStats.mTrackingAssociations;
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        if (arrayList.get(size) == this) {
                            arrayList.remove(size);
                            return;
                        }
                    }
                    android.util.Slog.wtf("ProcessStats", "Stop tracking didn't find in tracking list: " + this);
                }
            }
        }

        void add(com.android.internal.app.procstats.AssociationState.SourceState sourceState) {
            this.mCount += sourceState.mCount;
            this.mDuration += sourceState.mDuration;
            this.mActiveCount += sourceState.mActiveCount;
            if (sourceState.mActiveDuration != 0 || sourceState.mActiveDurations != null) {
                if (this.mActiveDurations != null) {
                    if (sourceState.mActiveDurations != null) {
                        this.mActiveDurations.addDurations(sourceState.mActiveDurations);
                        return;
                    } else {
                        this.mActiveDurations.addDuration(sourceState.mActiveProcState, sourceState.mActiveDuration);
                        return;
                    }
                }
                if (sourceState.mActiveDurations != null) {
                    makeDurations();
                    this.mActiveDurations.addDurations(sourceState.mActiveDurations);
                    if (this.mActiveDuration != 0) {
                        this.mActiveDurations.addDuration(this.mActiveProcState, this.mActiveDuration);
                        this.mActiveDuration = 0L;
                        this.mActiveProcState = -1;
                        return;
                    }
                    return;
                }
                if (this.mActiveDuration != 0) {
                    if (this.mActiveProcState == sourceState.mActiveProcState) {
                        this.mActiveDuration += sourceState.mActiveDuration;
                        return;
                    }
                    makeDurations();
                    this.mActiveDurations.addDuration(this.mActiveProcState, this.mActiveDuration);
                    this.mActiveDurations.addDuration(sourceState.mActiveProcState, sourceState.mActiveDuration);
                    this.mActiveDuration = 0L;
                    this.mActiveProcState = -1;
                    return;
                }
                this.mActiveProcState = sourceState.mActiveProcState;
                this.mActiveDuration = sourceState.mActiveDuration;
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mCount);
            parcel.writeLong(this.mDuration);
            parcel.writeInt(this.mActiveCount);
            if (this.mActiveDurations != null) {
                parcel.writeInt(1);
                this.mActiveDurations.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
                parcel.writeInt(this.mActiveProcState);
                parcel.writeLong(this.mActiveDuration);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        java.lang.String readFromParcel(android.os.Parcel parcel) {
            this.mCount = parcel.readInt();
            this.mDuration = parcel.readLong();
            this.mActiveCount = parcel.readInt();
            if (parcel.readInt() != 0) {
                makeDurations();
                if (!this.mActiveDurations.readFromParcel(parcel)) {
                    return "Duration table corrupt: " + this.mKey + " <- " + toString();
                }
                return null;
            }
            this.mActiveProcState = parcel.readInt();
            this.mActiveDuration = parcel.readLong();
            return null;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
            sb.append("SourceState{").append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this))).append(" ").append(this.mKey.mProcess).append("/").append(this.mKey.mUid);
            if (this.mProcState != -1) {
                sb.append(" ").append(com.android.internal.app.procstats.DumpUtils.STATE_NAMES[this.mProcState]).append(" #").append(this.mProcStateSeq);
            }
            sb.append("}");
            return sb.toString();
        }
    }

    static final class SourceDumpContainer {
        public long mActiveTime;
        public final com.android.internal.app.procstats.AssociationState.SourceState mState;
        public long mTotalTime;

        public SourceDumpContainer(com.android.internal.app.procstats.AssociationState.SourceState sourceState) {
            this.mState = sourceState;
        }
    }

    public static final class SourceKey {
        java.lang.String mPackage;
        java.lang.String mProcess;
        int mUid;

        SourceKey(int i, java.lang.String str, java.lang.String str2) {
            this.mUid = i;
            this.mProcess = str;
            this.mPackage = str2;
        }

        SourceKey(com.android.internal.app.procstats.ProcessStats processStats, android.os.Parcel parcel, int i) {
            this.mUid = parcel.readInt();
            this.mProcess = processStats.readCommonString(parcel, i);
            this.mPackage = processStats.readCommonString(parcel, i);
        }

        void writeToParcel(com.android.internal.app.procstats.ProcessStats processStats, android.os.Parcel parcel) {
            parcel.writeInt(this.mUid);
            processStats.writeCommonString(parcel, this.mProcess);
            processStats.writeCommonString(parcel, this.mPackage);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.internal.app.procstats.AssociationState.SourceKey)) {
                return false;
            }
            com.android.internal.app.procstats.AssociationState.SourceKey sourceKey = (com.android.internal.app.procstats.AssociationState.SourceKey) obj;
            return sourceKey.mUid == this.mUid && java.util.Objects.equals(sourceKey.mProcess, this.mProcess) && java.util.Objects.equals(sourceKey.mPackage, this.mPackage);
        }

        public int hashCode() {
            return (java.lang.Integer.hashCode(this.mUid) ^ (this.mProcess == null ? 0 : this.mProcess.hashCode())) ^ (this.mPackage != null ? this.mPackage.hashCode() * 33 : 0);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
            sb.append("SourceKey{");
            android.os.UserHandle.formatUid(sb, this.mUid);
            sb.append(' ');
            sb.append(this.mProcess);
            sb.append(' ');
            sb.append(this.mPackage);
            sb.append('}');
            return sb.toString();
        }
    }

    public AssociationState(com.android.internal.app.procstats.ProcessStats processStats, com.android.internal.app.procstats.ProcessStats.PackageState packageState, java.lang.String str, java.lang.String str2, com.android.internal.app.procstats.ProcessState processState) {
        this.mProcessStats = processStats;
        this.mPackageState = packageState;
        this.mName = str;
        this.mProcessName = str2;
        this.mProc = processState;
    }

    public int getUid() {
        return this.mPackageState.mUid;
    }

    public java.lang.String getPackage() {
        return this.mPackageState.mPackageName;
    }

    public java.lang.String getProcessName() {
        return this.mProcessName;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public com.android.internal.app.procstats.ProcessState getProcess() {
        return this.mProc;
    }

    public void setProcess(com.android.internal.app.procstats.ProcessState processState) {
        this.mProc = processState;
    }

    public long getTotalDuration(long j) {
        return this.mTotalDuration + (this.mTotalNesting > 0 ? j - this.mTotalStartUptime : 0L);
    }

    public long getActiveDuration(long j) {
        return this.mTotalActiveDuration + (this.mTotalActiveNesting > 0 ? j - this.mTotalActiveStartUptime : 0L);
    }

    public com.android.internal.app.procstats.AssociationState.SourceState startSource(int i, java.lang.String str, java.lang.String str2) {
        com.android.internal.app.procstats.AssociationState.SourceState sourceState;
        synchronized (sTmpSourceKey) {
            sTmpSourceKey.mUid = i;
            sTmpSourceKey.mProcess = str;
            sTmpSourceKey.mPackage = str2;
            sourceState = this.mSources.get(sTmpSourceKey);
        }
        if (sourceState == null) {
            com.android.internal.app.procstats.AssociationState.SourceKey sourceKey = new com.android.internal.app.procstats.AssociationState.SourceKey(i, str, str2);
            sourceState = new com.android.internal.app.procstats.AssociationState.SourceState(this.mProcessStats, this, this.mProc, sourceKey);
            this.mSources.put(sourceKey, sourceState);
        }
        long start = sourceState.start();
        if (start > 0) {
            this.mTotalNesting++;
            if (this.mTotalNesting == 1) {
                this.mTotalCount++;
                this.mTotalStartUptime = start;
            }
        }
        return sourceState;
    }

    public void add(com.android.internal.app.procstats.AssociationState associationState) {
        this.mTotalCount += associationState.mTotalCount;
        this.mTotalDuration += associationState.mTotalDuration;
        this.mTotalActiveCount += associationState.mTotalActiveCount;
        this.mTotalActiveDuration += associationState.mTotalActiveDuration;
        for (int size = associationState.mSources.size() - 1; size >= 0; size--) {
            com.android.internal.app.procstats.AssociationState.SourceKey keyAt = associationState.mSources.keyAt(size);
            com.android.internal.app.procstats.AssociationState.SourceState valueAt = associationState.mSources.valueAt(size);
            com.android.internal.app.procstats.AssociationState.SourceState sourceState = this.mSources.get(keyAt);
            if (sourceState == null) {
                sourceState = new com.android.internal.app.procstats.AssociationState.SourceState(this.mProcessStats, this, this.mProc, keyAt);
                this.mSources.put(keyAt, sourceState);
            }
            sourceState.add(valueAt);
        }
    }

    public boolean isInUse() {
        return this.mTotalNesting > 0;
    }

    public void resetSafely(long j) {
        if (!isInUse()) {
            this.mSources.clear();
            this.mTotalActiveCount = 0;
            this.mTotalCount = 0;
        } else {
            for (int size = this.mSources.size() - 1; size >= 0; size--) {
                com.android.internal.app.procstats.AssociationState.SourceState valueAt = this.mSources.valueAt(size);
                if (valueAt.isInUse()) {
                    valueAt.resetSafely(j);
                } else {
                    this.mSources.removeAt(size);
                }
            }
            this.mTotalCount = 1;
            this.mTotalStartUptime = j;
            if (this.mTotalActiveNesting > 0) {
                this.mTotalActiveCount = 1;
                this.mTotalActiveStartUptime = j;
            } else {
                this.mTotalActiveCount = 0;
            }
        }
        this.mTotalActiveDuration = 0L;
        this.mTotalDuration = 0L;
    }

    public void writeToParcel(com.android.internal.app.procstats.ProcessStats processStats, android.os.Parcel parcel, long j) {
        parcel.writeInt(this.mTotalCount);
        parcel.writeLong(this.mTotalDuration);
        parcel.writeInt(this.mTotalActiveCount);
        parcel.writeLong(this.mTotalActiveDuration);
        int size = this.mSources.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            com.android.internal.app.procstats.AssociationState.SourceKey keyAt = this.mSources.keyAt(i);
            com.android.internal.app.procstats.AssociationState.SourceState valueAt = this.mSources.valueAt(i);
            keyAt.writeToParcel(processStats, parcel);
            valueAt.writeToParcel(parcel, 0);
        }
    }

    public java.lang.String readFromParcel(com.android.internal.app.procstats.ProcessStats processStats, android.os.Parcel parcel, int i) {
        this.mTotalCount = parcel.readInt();
        this.mTotalDuration = parcel.readLong();
        this.mTotalActiveCount = parcel.readInt();
        this.mTotalActiveDuration = parcel.readLong();
        int readInt = parcel.readInt();
        if (readInt < 0 || readInt > 100000) {
            return "Association with bad src count: " + readInt;
        }
        for (int i2 = 0; i2 < readInt; i2++) {
            com.android.internal.app.procstats.AssociationState.SourceKey sourceKey = new com.android.internal.app.procstats.AssociationState.SourceKey(processStats, parcel, i);
            com.android.internal.app.procstats.AssociationState.SourceState sourceState = new com.android.internal.app.procstats.AssociationState.SourceState(this.mProcessStats, this, this.mProc, sourceKey);
            java.lang.String readFromParcel = sourceState.readFromParcel(parcel);
            if (readFromParcel != null) {
                return readFromParcel;
            }
            this.mSources.put(sourceKey, sourceState);
        }
        return null;
    }

    public void commitStateTime(long j) {
        if (isInUse()) {
            for (int size = this.mSources.size() - 1; size >= 0; size--) {
                this.mSources.valueAt(size).commitStateTime(j);
            }
            if (this.mTotalNesting > 0) {
                this.mTotalDuration += j - this.mTotalStartUptime;
                this.mTotalStartUptime = j;
            }
            if (this.mTotalActiveNesting > 0) {
                this.mTotalActiveDuration += j - this.mTotalActiveStartUptime;
                this.mTotalActiveStartUptime = j;
            }
        }
    }

    public boolean hasProcessOrPackage(java.lang.String str) {
        if (this.mProcessName.equals(str)) {
            return true;
        }
        int size = this.mSources.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.app.procstats.AssociationState.SourceKey keyAt = this.mSources.keyAt(i);
            if (str.equals(keyAt.mProcess) || str.equals(keyAt.mPackage)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ int lambda$static$0(android.util.Pair pair, android.util.Pair pair2) {
        int compareTo;
        if (((com.android.internal.app.procstats.AssociationState.SourceDumpContainer) pair.second).mActiveTime != ((com.android.internal.app.procstats.AssociationState.SourceDumpContainer) pair2.second).mActiveTime) {
            return ((com.android.internal.app.procstats.AssociationState.SourceDumpContainer) pair.second).mActiveTime > ((com.android.internal.app.procstats.AssociationState.SourceDumpContainer) pair2.second).mActiveTime ? -1 : 1;
        }
        if (((com.android.internal.app.procstats.AssociationState.SourceDumpContainer) pair.second).mTotalTime != ((com.android.internal.app.procstats.AssociationState.SourceDumpContainer) pair2.second).mTotalTime) {
            return ((com.android.internal.app.procstats.AssociationState.SourceDumpContainer) pair.second).mTotalTime > ((com.android.internal.app.procstats.AssociationState.SourceDumpContainer) pair2.second).mTotalTime ? -1 : 1;
        }
        if (((com.android.internal.app.procstats.AssociationState.SourceKey) pair.first).mUid != ((com.android.internal.app.procstats.AssociationState.SourceKey) pair2.first).mUid) {
            return ((com.android.internal.app.procstats.AssociationState.SourceKey) pair.first).mUid < ((com.android.internal.app.procstats.AssociationState.SourceKey) pair2.first).mUid ? -1 : 1;
        }
        if (((com.android.internal.app.procstats.AssociationState.SourceKey) pair.first).mProcess != ((com.android.internal.app.procstats.AssociationState.SourceKey) pair2.first).mProcess && (compareTo = ((com.android.internal.app.procstats.AssociationState.SourceKey) pair.first).mProcess.compareTo(((com.android.internal.app.procstats.AssociationState.SourceKey) pair2.first).mProcess)) != 0) {
            return compareTo;
        }
        return 0;
    }

    static java.util.ArrayList<android.util.Pair<com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceDumpContainer>> createSortedAssociations(long j, long j2, android.util.ArrayMap<com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceState> arrayMap) {
        int size = arrayMap.size();
        java.util.ArrayList<android.util.Pair<com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceDumpContainer>> arrayList = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            com.android.internal.app.procstats.AssociationState.SourceState valueAt = arrayMap.valueAt(i);
            com.android.internal.app.procstats.AssociationState.SourceDumpContainer sourceDumpContainer = new com.android.internal.app.procstats.AssociationState.SourceDumpContainer(valueAt);
            long j3 = valueAt.mDuration;
            if (valueAt.mNesting > 0) {
                j3 += j - valueAt.mStartUptime;
            }
            sourceDumpContainer.mTotalTime = j3;
            sourceDumpContainer.mActiveTime = dumpTime(null, null, valueAt, j2, j, false, false);
            if (sourceDumpContainer.mActiveTime < 0) {
                sourceDumpContainer.mActiveTime = -sourceDumpContainer.mActiveTime;
            }
            arrayList.add(new android.util.Pair<>(arrayMap.keyAt(i), sourceDumpContainer));
        }
        java.util.Collections.sort(arrayList, ASSOCIATION_COMPARATOR);
        return arrayList;
    }

    public void dumpStats(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, java.lang.String str3, java.util.ArrayList<android.util.Pair<com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceDumpContainer>> arrayList, long j, long j2, java.lang.String str4, boolean z, boolean z2) {
        java.lang.String str5 = str2 + "     ";
        long j3 = this.mTotalActiveDuration;
        if (this.mTotalActiveNesting > 0) {
            j3 += j - this.mTotalActiveStartUptime;
        }
        if (j3 > 0 || this.mTotalActiveCount != 0) {
            printWriter.print(str);
            printWriter.print("Active count ");
            printWriter.print(this.mTotalActiveCount);
            if (z2) {
                printWriter.print(": ");
                android.util.TimeUtils.formatDuration(j3, printWriter);
                printWriter.print(" / ");
            } else {
                printWriter.print(": time ");
            }
            com.android.internal.app.procstats.DumpUtils.printPercent(printWriter, j3 / j2);
            printWriter.println();
        }
        if (z2 && this.mTotalActiveNesting != 0) {
            printWriter.print(str);
            printWriter.print("mTotalActiveNesting=");
            printWriter.print(this.mTotalActiveNesting);
            printWriter.print(" mTotalActiveStartUptime=");
            android.util.TimeUtils.formatDuration(this.mTotalActiveStartUptime, j, printWriter);
            printWriter.println();
        }
        long j4 = this.mTotalDuration;
        if (this.mTotalNesting > 0) {
            j4 += j - this.mTotalStartUptime;
        }
        if (j4 > 0 || this.mTotalCount != 0) {
            printWriter.print(str);
            printWriter.print("Total count ");
            printWriter.print(this.mTotalCount);
            if (z2) {
                printWriter.print(": ");
                android.util.TimeUtils.formatDuration(j4, printWriter);
                printWriter.print(" / ");
            } else {
                printWriter.print(": time ");
            }
            com.android.internal.app.procstats.DumpUtils.printPercent(printWriter, j4 / j2);
            printWriter.println();
        }
        if (z2 && this.mTotalNesting != 0) {
            printWriter.print(str);
            printWriter.print("mTotalNesting=");
            printWriter.print(this.mTotalNesting);
            printWriter.print(" mTotalStartUptime=");
            android.util.TimeUtils.formatDuration(this.mTotalStartUptime, j, printWriter);
            printWriter.println();
        }
        dumpSources(printWriter, str, str2, str5, arrayList, j, j2, str4, z, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01d0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0159  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static void dumpSources(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, java.lang.String str3, java.util.ArrayList<android.util.Pair<com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceDumpContainer>> arrayList, long j, long j2, java.lang.String str4, boolean z, boolean z2) {
        int i;
        java.lang.String str5;
        java.lang.String str6;
        java.lang.String str7;
        com.android.internal.app.procstats.AssociationState.SourceState sourceState;
        java.lang.String str8;
        com.android.internal.app.procstats.AssociationState.SourceDumpContainer sourceDumpContainer;
        int i2;
        com.android.internal.app.procstats.AssociationState.SourceState sourceState2;
        long j3;
        java.util.ArrayList<android.util.Pair<com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceDumpContainer>> arrayList2 = arrayList;
        long j4 = j2;
        java.lang.String str9 = str4;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            com.android.internal.app.procstats.AssociationState.SourceKey sourceKey = arrayList2.get(i3).first;
            com.android.internal.app.procstats.AssociationState.SourceDumpContainer sourceDumpContainer2 = arrayList2.get(i3).second;
            com.android.internal.app.procstats.AssociationState.SourceState sourceState3 = sourceDumpContainer2.mState;
            printWriter.print(str);
            printWriter.print("<- ");
            printWriter.print(sourceKey.mProcess);
            printWriter.print("/");
            android.os.UserHandle.formatUid(printWriter, sourceKey.mUid);
            if (sourceKey.mPackage != null) {
                printWriter.print(" (");
                printWriter.print(sourceKey.mPackage);
                printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            if (str9 == null || str9.equals(sourceKey.mProcess) || str9.equals(sourceKey.mPackage)) {
                printWriter.println(":");
                if (sourceState3.mActiveCount == 0 && sourceState3.mActiveDurations == null) {
                    i = size;
                    if (sourceState3.mActiveDuration == 0 && sourceState3.mActiveStartUptime == 0) {
                        str5 = ": ";
                        str6 = ": time ";
                        str7 = " / ";
                        sourceState = sourceState3;
                        str8 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
                        sourceDumpContainer = sourceDumpContainer2;
                        i2 = i3;
                        printWriter.print(str2);
                        printWriter.print("   Total count ");
                        sourceState2 = sourceState;
                        printWriter.print(sourceState2.mCount);
                        if (!z2) {
                            printWriter.print(str5);
                            android.util.TimeUtils.formatDuration(sourceDumpContainer.mTotalTime, printWriter);
                            printWriter.print(str7);
                        } else {
                            printWriter.print(str6);
                        }
                        j3 = j2;
                        com.android.internal.app.procstats.DumpUtils.printPercent(printWriter, sourceDumpContainer.mTotalTime / j3);
                        if (sourceState2.mNesting > 0) {
                            printWriter.print(" (running");
                            if (z2) {
                                printWriter.print(" nest=");
                                printWriter.print(sourceState2.mNesting);
                            }
                            if (sourceState2.mProcState != -1) {
                                printWriter.print(str7);
                                printWriter.print(com.android.internal.app.procstats.DumpUtils.STATE_NAMES[sourceState2.mProcState]);
                                printWriter.print(" #");
                                printWriter.print(sourceState2.mProcStateSeq);
                            }
                            printWriter.print(str8);
                        }
                        printWriter.println();
                        if (z2) {
                            if (sourceState2.mInTrackingList) {
                                printWriter.print(str2);
                                printWriter.print("   mInTrackingList=");
                                printWriter.println(sourceState2.mInTrackingList);
                            }
                            if (sourceState2.mProcState != -1) {
                                printWriter.print(str2);
                                printWriter.print("   mProcState=");
                                printWriter.print(com.android.internal.app.procstats.DumpUtils.STATE_NAMES[sourceState2.mProcState]);
                                printWriter.print(" mProcStateSeq=");
                                printWriter.println(sourceState2.mProcStateSeq);
                            }
                        }
                    }
                } else {
                    i = size;
                }
                printWriter.print(str2);
                printWriter.print("   Active count ");
                printWriter.print(sourceState3.mActiveCount);
                if (z) {
                    if (z2) {
                        if (sourceState3.mActiveDurations != null) {
                            printWriter.print(" (multi-state)");
                        } else if (sourceState3.mActiveProcState < 0) {
                            printWriter.print(" (*UNKNOWN STATE*)");
                        } else {
                            printWriter.print(" (");
                            printWriter.print(com.android.internal.app.procstats.DumpUtils.STATE_NAMES[sourceState3.mActiveProcState]);
                            printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                        }
                    }
                    if (z2) {
                        printWriter.print(": ");
                        android.util.TimeUtils.formatDuration(sourceDumpContainer2.mActiveTime, printWriter);
                        printWriter.print(" / ");
                    } else {
                        printWriter.print(": time ");
                    }
                    com.android.internal.app.procstats.DumpUtils.printPercent(printWriter, sourceDumpContainer2.mActiveTime / j4);
                    if (sourceState3.mActiveStartUptime != 0) {
                        printWriter.print(" (running)");
                    }
                    printWriter.println();
                    if (sourceState3.mActiveDurations == null) {
                        str5 = ": ";
                        str6 = ": time ";
                        str7 = " / ";
                        sourceState = sourceState3;
                        str8 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
                        sourceDumpContainer = sourceDumpContainer2;
                        i2 = i3;
                    } else {
                        str5 = ": ";
                        str6 = ": time ";
                        str7 = " / ";
                        sourceState = sourceState3;
                        str8 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
                        sourceDumpContainer = sourceDumpContainer2;
                        i2 = i3;
                        dumpTime(printWriter, str3, sourceState3, j2, j, z, z2);
                    }
                } else {
                    str5 = ": ";
                    str6 = ": time ";
                    str7 = " / ";
                    sourceState = sourceState3;
                    str8 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
                    sourceDumpContainer = sourceDumpContainer2;
                    i2 = i3;
                    printWriter.print(str5);
                    dumpActiveDurationSummary(printWriter, sourceState, j2, j, z2);
                }
                printWriter.print(str2);
                printWriter.print("   Total count ");
                sourceState2 = sourceState;
                printWriter.print(sourceState2.mCount);
                if (!z2) {
                }
                j3 = j2;
                com.android.internal.app.procstats.DumpUtils.printPercent(printWriter, sourceDumpContainer.mTotalTime / j3);
                if (sourceState2.mNesting > 0) {
                }
                printWriter.println();
                if (z2) {
                }
            } else {
                printWriter.println();
                j3 = j4;
                i = size;
                i2 = i3;
            }
            i3 = i2 + 1;
            arrayList2 = arrayList;
            str9 = str4;
            j4 = j3;
            size = i;
        }
    }

    static void dumpActiveDurationSummary(java.io.PrintWriter printWriter, com.android.internal.app.procstats.AssociationState.SourceState sourceState, long j, long j2, boolean z) {
        long dumpTime = dumpTime(null, null, sourceState, j, j2, false, false);
        if (dumpTime < 0) {
            dumpTime = -dumpTime;
        }
        if (z) {
            android.util.TimeUtils.formatDuration(dumpTime, printWriter);
            printWriter.print(" / ");
        } else {
            printWriter.print("time ");
        }
        com.android.internal.app.procstats.DumpUtils.printPercent(printWriter, dumpTime / j);
        if (sourceState.mActiveStartUptime > 0) {
            printWriter.print(" (running)");
        }
        printWriter.println();
    }

    static long dumpTime(java.io.PrintWriter printWriter, java.lang.String str, com.android.internal.app.procstats.AssociationState.SourceState sourceState, long j, long j2, boolean z, boolean z2) {
        long j3;
        java.lang.String str2;
        long j4 = 0;
        int i = 0;
        long j5 = 0;
        boolean z3 = false;
        while (i < 16) {
            if (sourceState.mActiveDurations != null) {
                j3 = sourceState.mActiveDurations.getValueForId((byte) i);
            } else {
                j3 = sourceState.mActiveProcState == i ? sourceState.mActiveDuration : j4;
            }
            if (sourceState.mActiveStartUptime != j4 && sourceState.mActiveProcState == i) {
                j3 += j2 - sourceState.mActiveStartUptime;
                z3 = true;
                str2 = " (running)";
            } else {
                str2 = null;
            }
            if (j3 != j4) {
                if (printWriter != null) {
                    printWriter.print(str);
                    printWriter.print(com.android.internal.app.procstats.DumpUtils.STATE_LABELS[i]);
                    printWriter.print(": ");
                    if (z2) {
                        android.util.TimeUtils.formatDuration(j3, printWriter);
                        printWriter.print(" / ");
                    } else {
                        printWriter.print("time ");
                    }
                    com.android.internal.app.procstats.DumpUtils.printPercent(printWriter, j3 / j);
                    if (str2 != null) {
                        printWriter.print(str2);
                    }
                    printWriter.println();
                }
                j5 += j3;
            }
            i++;
            j4 = 0;
        }
        return z3 ? -j5 : j5;
    }

    public void dumpTimesCheckin(java.io.PrintWriter printWriter, java.lang.String str, int i, long j, java.lang.String str2, long j2) {
        int size = this.mSources.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.app.procstats.AssociationState.SourceKey keyAt = this.mSources.keyAt(i2);
            com.android.internal.app.procstats.AssociationState.SourceState valueAt = this.mSources.valueAt(i2);
            printWriter.print("pkgasc");
            printWriter.print(",");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print(j);
            printWriter.print(",");
            printWriter.print(str2);
            printWriter.print(",");
            printWriter.print(keyAt.mProcess);
            printWriter.print(",");
            printWriter.print(keyAt.mUid);
            printWriter.print(",");
            printWriter.print(valueAt.mCount);
            long j3 = valueAt.mDuration;
            if (valueAt.mNesting > 0) {
                j3 += j2 - valueAt.mStartUptime;
            }
            printWriter.print(",");
            printWriter.print(j3);
            printWriter.print(",");
            printWriter.print(valueAt.mActiveCount);
            long j4 = valueAt.mActiveStartUptime != 0 ? j2 - valueAt.mActiveStartUptime : 0L;
            if (valueAt.mActiveDurations != null) {
                int keyCount = valueAt.mActiveDurations.getKeyCount();
                for (int i3 = 0; i3 < keyCount; i3++) {
                    int keyAt2 = valueAt.mActiveDurations.getKeyAt(i3);
                    long value = valueAt.mActiveDurations.getValue(keyAt2);
                    if (keyAt2 == valueAt.mActiveProcState) {
                        value += j4;
                    }
                    byte idFromKey = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt2);
                    printWriter.print(",");
                    com.android.internal.app.procstats.DumpUtils.printArrayEntry(printWriter, com.android.internal.app.procstats.DumpUtils.STATE_TAGS, idFromKey, 1);
                    printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
                    printWriter.print(value);
                }
            } else {
                long j5 = valueAt.mActiveDuration + j4;
                if (j5 != 0) {
                    printWriter.print(",");
                    com.android.internal.app.procstats.DumpUtils.printArrayEntry(printWriter, com.android.internal.app.procstats.DumpUtils.STATE_TAGS, valueAt.mActiveProcState, 1);
                    printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
                    printWriter.print(j5);
                }
            }
            printWriter.println();
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2) {
        int i;
        int i2;
        long j3;
        long j4;
        com.android.internal.app.procstats.AssociationState associationState = this;
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, associationState.mName);
        protoOutputStream.write(1120986464259L, associationState.mTotalCount);
        protoOutputStream.write(1112396529668L, associationState.getTotalDuration(j2));
        if (associationState.mTotalActiveCount != 0) {
            protoOutputStream.write(1120986464261L, associationState.mTotalActiveCount);
            protoOutputStream.write(1112396529670L, associationState.getActiveDuration(j2));
        }
        int size = associationState.mSources.size();
        int i3 = 0;
        while (i3 < size) {
            com.android.internal.app.procstats.AssociationState.SourceKey keyAt = associationState.mSources.keyAt(i3);
            com.android.internal.app.procstats.AssociationState.SourceState valueAt = associationState.mSources.valueAt(i3);
            long start2 = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1138166333442L, keyAt.mProcess);
            protoOutputStream.write(1138166333447L, keyAt.mPackage);
            protoOutputStream.write(1120986464257L, keyAt.mUid);
            protoOutputStream.write(1120986464259L, valueAt.mCount);
            long j5 = valueAt.mDuration;
            if (valueAt.mNesting > 0) {
                j5 += j2 - valueAt.mStartUptime;
            }
            protoOutputStream.write(1112396529668L, j5);
            if (valueAt.mActiveCount != 0) {
                protoOutputStream.write(1120986464261L, valueAt.mActiveCount);
            }
            long j6 = valueAt.mActiveStartUptime != 0 ? j2 - valueAt.mActiveStartUptime : 0L;
            if (valueAt.mActiveDurations != null) {
                int keyCount = valueAt.mActiveDurations.getKeyCount();
                long j7 = start2;
                int i4 = 0;
                while (i4 < keyCount) {
                    int keyAt2 = valueAt.mActiveDurations.getKeyAt(i4);
                    long value = valueAt.mActiveDurations.getValue(keyAt2);
                    if (keyAt2 == valueAt.mActiveProcState) {
                        value += j6;
                    }
                    byte idFromKey = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt2);
                    int i5 = i3;
                    long start3 = protoOutputStream.start(2246267895814L);
                    com.android.internal.app.procstats.DumpUtils.printProto(protoOutputStream, 1159641169921L, com.android.internal.app.procstats.DumpUtils.STATE_PROTO_ENUMS, idFromKey, 1);
                    protoOutputStream.write(1112396529666L, value);
                    protoOutputStream.end(start3);
                    i4++;
                    keyCount = keyCount;
                    i3 = i5;
                    size = size;
                    valueAt = valueAt;
                    j7 = j7;
                }
                i = i3;
                i2 = size;
                j3 = j7;
                j4 = 1120986464261L;
            } else {
                i = i3;
                i2 = size;
                j3 = start2;
                j4 = 1120986464261L;
                long j8 = j6 + valueAt.mActiveDuration;
                if (j8 != 0) {
                    long start4 = protoOutputStream.start(2246267895814L);
                    com.android.internal.app.procstats.DumpUtils.printProto(protoOutputStream, 1159641169921L, com.android.internal.app.procstats.DumpUtils.STATE_PROTO_ENUMS, valueAt.mActiveProcState, 1);
                    protoOutputStream.write(1112396529666L, j8);
                    protoOutputStream.end(start4);
                }
            }
            protoOutputStream.end(j3);
            i3 = i + 1;
            associationState = this;
            size = i2;
        }
        protoOutputStream.end(start);
    }

    public java.lang.String toString() {
        return "AssociationState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.mName + " pkg=" + this.mPackageState.mPackageName + " proc=" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mProc)) + "}";
    }
}
