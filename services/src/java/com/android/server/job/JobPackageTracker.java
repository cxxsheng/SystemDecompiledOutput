package com.android.server.job;

/* loaded from: classes2.dex */
public final class JobPackageTracker {
    static final long BATCHING_TIME = 1800000;
    private static final int EVENT_BUFFER_SIZE = 100;
    public static final int EVENT_CMD_MASK = 255;
    public static final int EVENT_NULL = 0;
    public static final int EVENT_START_JOB = 1;
    public static final int EVENT_START_PERIODIC_JOB = 3;
    public static final int EVENT_STOP_JOB = 2;
    public static final int EVENT_STOP_PERIODIC_JOB = 4;
    public static final int EVENT_STOP_REASON_MASK = 65280;
    public static final int EVENT_STOP_REASON_SHIFT = 8;
    static final int NUM_HISTORY = 5;
    private final com.android.internal.util.jobs.RingBufferIndices mEventIndices = new com.android.internal.util.jobs.RingBufferIndices(100);
    private final int[] mEventCmds = new int[100];
    private final long[] mEventTimes = new long[100];
    private final int[] mEventUids = new int[100];
    private final java.lang.String[] mEventTags = new java.lang.String[100];
    private final int[] mEventJobIds = new int[100];
    private final java.lang.String[] mEventReasons = new java.lang.String[100];
    com.android.server.job.JobPackageTracker.DataSet mCurDataSet = new com.android.server.job.JobPackageTracker.DataSet();
    com.android.server.job.JobPackageTracker.DataSet[] mLastDataSets = new com.android.server.job.JobPackageTracker.DataSet[5];

    public void addEvent(int i, int i2, java.lang.String str, int i3, int i4, java.lang.String str2) {
        int add = this.mEventIndices.add();
        this.mEventCmds[add] = i | ((i4 << 8) & EVENT_STOP_REASON_MASK);
        this.mEventTimes[add] = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        this.mEventUids[add] = i2;
        this.mEventTags[add] = str;
        this.mEventJobIds[add] = i3;
        this.mEventReasons[add] = str2;
    }

    static final class PackageEntry {
        int activeCount;
        int activeNesting;
        long activeStartTime;
        int activeTopCount;
        int activeTopNesting;
        long activeTopStartTime;
        boolean hadActive;
        boolean hadActiveTop;
        boolean hadPending;
        long pastActiveTime;
        long pastActiveTopTime;
        long pastPendingTime;
        int pendingCount;
        int pendingNesting;
        long pendingStartTime;
        final android.util.SparseIntArray stopReasons = new android.util.SparseIntArray();

        PackageEntry() {
        }

        public long getActiveTime(long j) {
            long j2 = this.pastActiveTime;
            if (this.activeNesting > 0) {
                return j2 + (j - this.activeStartTime);
            }
            return j2;
        }

        public long getActiveTopTime(long j) {
            long j2 = this.pastActiveTopTime;
            if (this.activeTopNesting > 0) {
                return j2 + (j - this.activeTopStartTime);
            }
            return j2;
        }

        public long getPendingTime(long j) {
            long j2 = this.pastPendingTime;
            if (this.pendingNesting > 0) {
                return j2 + (j - this.pendingStartTime);
            }
            return j2;
        }
    }

    static final class DataSet {
        final android.util.SparseArray<android.util.ArrayMap<java.lang.String, com.android.server.job.JobPackageTracker.PackageEntry>> mEntries;
        int mMaxFgActive;
        int mMaxTotalActive;
        final long mStartClockTime;
        final long mStartElapsedTime;
        final long mStartUptimeTime;
        long mSummedTime;

        public DataSet(com.android.server.job.JobPackageTracker.DataSet dataSet) {
            this.mEntries = new android.util.SparseArray<>();
            this.mStartUptimeTime = dataSet.mStartUptimeTime;
            this.mStartElapsedTime = dataSet.mStartElapsedTime;
            this.mStartClockTime = dataSet.mStartClockTime;
        }

        public DataSet() {
            this.mEntries = new android.util.SparseArray<>();
            this.mStartUptimeTime = com.android.server.job.JobSchedulerService.sUptimeMillisClock.millis();
            this.mStartElapsedTime = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            this.mStartClockTime = com.android.server.job.JobSchedulerService.sSystemClock.millis();
        }

        private com.android.server.job.JobPackageTracker.PackageEntry getOrCreateEntry(int i, java.lang.String str) {
            android.util.ArrayMap<java.lang.String, com.android.server.job.JobPackageTracker.PackageEntry> arrayMap = this.mEntries.get(i);
            if (arrayMap == null) {
                arrayMap = new android.util.ArrayMap<>();
                this.mEntries.put(i, arrayMap);
            }
            com.android.server.job.JobPackageTracker.PackageEntry packageEntry = arrayMap.get(str);
            if (packageEntry == null) {
                com.android.server.job.JobPackageTracker.PackageEntry packageEntry2 = new com.android.server.job.JobPackageTracker.PackageEntry();
                arrayMap.put(str, packageEntry2);
                return packageEntry2;
            }
            return packageEntry;
        }

        public com.android.server.job.JobPackageTracker.PackageEntry getEntry(int i, java.lang.String str) {
            android.util.ArrayMap<java.lang.String, com.android.server.job.JobPackageTracker.PackageEntry> arrayMap = this.mEntries.get(i);
            if (arrayMap == null) {
                return null;
            }
            return arrayMap.get(str);
        }

        long getTotalTime(long j) {
            if (this.mSummedTime > 0) {
                return this.mSummedTime;
            }
            return j - this.mStartUptimeTime;
        }

        void incPending(int i, java.lang.String str, long j) {
            com.android.server.job.JobPackageTracker.PackageEntry orCreateEntry = getOrCreateEntry(i, str);
            if (orCreateEntry.pendingNesting == 0) {
                orCreateEntry.pendingStartTime = j;
                orCreateEntry.pendingCount++;
            }
            orCreateEntry.pendingNesting++;
        }

        void decPending(int i, java.lang.String str, long j) {
            com.android.server.job.JobPackageTracker.PackageEntry orCreateEntry = getOrCreateEntry(i, str);
            if (orCreateEntry.pendingNesting == 1) {
                orCreateEntry.pastPendingTime += j - orCreateEntry.pendingStartTime;
            }
            orCreateEntry.pendingNesting--;
        }

        void incActive(int i, java.lang.String str, long j) {
            com.android.server.job.JobPackageTracker.PackageEntry orCreateEntry = getOrCreateEntry(i, str);
            if (orCreateEntry.activeNesting == 0) {
                orCreateEntry.activeStartTime = j;
                orCreateEntry.activeCount++;
            }
            orCreateEntry.activeNesting++;
        }

        void decActive(int i, java.lang.String str, long j, int i2) {
            com.android.server.job.JobPackageTracker.PackageEntry orCreateEntry = getOrCreateEntry(i, str);
            if (orCreateEntry.activeNesting == 1) {
                orCreateEntry.pastActiveTime += j - orCreateEntry.activeStartTime;
            }
            orCreateEntry.activeNesting--;
            orCreateEntry.stopReasons.put(i2, orCreateEntry.stopReasons.get(i2, 0) + 1);
        }

        void incActiveTop(int i, java.lang.String str, long j) {
            com.android.server.job.JobPackageTracker.PackageEntry orCreateEntry = getOrCreateEntry(i, str);
            if (orCreateEntry.activeTopNesting == 0) {
                orCreateEntry.activeTopStartTime = j;
                orCreateEntry.activeTopCount++;
            }
            orCreateEntry.activeTopNesting++;
        }

        void decActiveTop(int i, java.lang.String str, long j, int i2) {
            com.android.server.job.JobPackageTracker.PackageEntry orCreateEntry = getOrCreateEntry(i, str);
            if (orCreateEntry.activeTopNesting == 1) {
                orCreateEntry.pastActiveTopTime += j - orCreateEntry.activeTopStartTime;
            }
            orCreateEntry.activeTopNesting--;
            orCreateEntry.stopReasons.put(i2, orCreateEntry.stopReasons.get(i2, 0) + 1);
        }

        void finish(com.android.server.job.JobPackageTracker.DataSet dataSet, long j) {
            for (int size = this.mEntries.size() - 1; size >= 0; size--) {
                android.util.ArrayMap<java.lang.String, com.android.server.job.JobPackageTracker.PackageEntry> valueAt = this.mEntries.valueAt(size);
                for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                    com.android.server.job.JobPackageTracker.PackageEntry valueAt2 = valueAt.valueAt(size2);
                    if (valueAt2.activeNesting > 0 || valueAt2.activeTopNesting > 0 || valueAt2.pendingNesting > 0) {
                        com.android.server.job.JobPackageTracker.PackageEntry orCreateEntry = dataSet.getOrCreateEntry(this.mEntries.keyAt(size), valueAt.keyAt(size2));
                        orCreateEntry.activeStartTime = j;
                        orCreateEntry.activeNesting = valueAt2.activeNesting;
                        orCreateEntry.activeTopStartTime = j;
                        orCreateEntry.activeTopNesting = valueAt2.activeTopNesting;
                        orCreateEntry.pendingStartTime = j;
                        orCreateEntry.pendingNesting = valueAt2.pendingNesting;
                        if (valueAt2.activeNesting > 0) {
                            valueAt2.pastActiveTime += j - valueAt2.activeStartTime;
                            valueAt2.activeNesting = 0;
                        }
                        if (valueAt2.activeTopNesting > 0) {
                            valueAt2.pastActiveTopTime += j - valueAt2.activeTopStartTime;
                            valueAt2.activeTopNesting = 0;
                        }
                        if (valueAt2.pendingNesting > 0) {
                            valueAt2.pastPendingTime += j - valueAt2.pendingStartTime;
                            valueAt2.pendingNesting = 0;
                        }
                    }
                }
            }
        }

        void addTo(com.android.server.job.JobPackageTracker.DataSet dataSet, long j) {
            dataSet.mSummedTime += getTotalTime(j);
            for (int size = this.mEntries.size() - 1; size >= 0; size--) {
                android.util.ArrayMap<java.lang.String, com.android.server.job.JobPackageTracker.PackageEntry> valueAt = this.mEntries.valueAt(size);
                for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                    com.android.server.job.JobPackageTracker.PackageEntry valueAt2 = valueAt.valueAt(size2);
                    com.android.server.job.JobPackageTracker.PackageEntry orCreateEntry = dataSet.getOrCreateEntry(this.mEntries.keyAt(size), valueAt.keyAt(size2));
                    orCreateEntry.pastActiveTime += valueAt2.pastActiveTime;
                    orCreateEntry.activeCount += valueAt2.activeCount;
                    orCreateEntry.pastActiveTopTime += valueAt2.pastActiveTopTime;
                    orCreateEntry.activeTopCount += valueAt2.activeTopCount;
                    orCreateEntry.pastPendingTime += valueAt2.pastPendingTime;
                    orCreateEntry.pendingCount += valueAt2.pendingCount;
                    if (valueAt2.activeNesting > 0) {
                        orCreateEntry.pastActiveTime += j - valueAt2.activeStartTime;
                        orCreateEntry.hadActive = true;
                    }
                    if (valueAt2.activeTopNesting > 0) {
                        orCreateEntry.pastActiveTopTime += j - valueAt2.activeTopStartTime;
                        orCreateEntry.hadActiveTop = true;
                    }
                    if (valueAt2.pendingNesting > 0) {
                        orCreateEntry.pastPendingTime += j - valueAt2.pendingStartTime;
                        orCreateEntry.hadPending = true;
                    }
                    for (int size3 = valueAt2.stopReasons.size() - 1; size3 >= 0; size3--) {
                        int keyAt = valueAt2.stopReasons.keyAt(size3);
                        orCreateEntry.stopReasons.put(keyAt, orCreateEntry.stopReasons.get(keyAt, 0) + valueAt2.stopReasons.valueAt(size3));
                    }
                }
            }
            if (this.mMaxTotalActive > dataSet.mMaxTotalActive) {
                dataSet.mMaxTotalActive = this.mMaxTotalActive;
            }
            if (this.mMaxFgActive > dataSet.mMaxFgActive) {
                dataSet.mMaxFgActive = this.mMaxFgActive;
            }
        }

        boolean printDuration(android.util.IndentingPrintWriter indentingPrintWriter, long j, long j2, int i, java.lang.String str) {
            int i2 = (int) (((j2 / j) * 100.0f) + 0.5f);
            if (i2 > 0) {
                indentingPrintWriter.print(i2);
                indentingPrintWriter.print("% ");
                indentingPrintWriter.print(i);
                indentingPrintWriter.print("x ");
                indentingPrintWriter.print(str);
                return true;
            }
            if (i > 0) {
                indentingPrintWriter.print(i);
                indentingPrintWriter.print("x ");
                indentingPrintWriter.print(str);
                return true;
            }
            return false;
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, long j, long j2, int i) {
            com.android.server.job.JobPackageTracker.DataSet dataSet = this;
            int i2 = i;
            long totalTime = dataSet.getTotalTime(j);
            indentingPrintWriter.print(str);
            indentingPrintWriter.print(" at ");
            indentingPrintWriter.print(android.text.format.DateFormat.format("yyyy-MM-dd-HH-mm-ss", dataSet.mStartClockTime).toString());
            indentingPrintWriter.print(" (");
            android.util.TimeUtils.formatDuration(dataSet.mStartElapsedTime, j2, indentingPrintWriter);
            indentingPrintWriter.print(") over ");
            android.util.TimeUtils.formatDuration(totalTime, indentingPrintWriter);
            indentingPrintWriter.println(":");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("Max concurrency: ");
            indentingPrintWriter.print(dataSet.mMaxTotalActive);
            indentingPrintWriter.print(" total, ");
            indentingPrintWriter.print(dataSet.mMaxFgActive);
            indentingPrintWriter.println(" foreground");
            indentingPrintWriter.println();
            int size = dataSet.mEntries.size();
            int i3 = 0;
            while (i3 < size) {
                int keyAt = dataSet.mEntries.keyAt(i3);
                if (i2 == -1 || i2 == android.os.UserHandle.getAppId(keyAt)) {
                    android.util.ArrayMap<java.lang.String, com.android.server.job.JobPackageTracker.PackageEntry> valueAt = dataSet.mEntries.valueAt(i3);
                    int size2 = valueAt.size();
                    int i4 = 0;
                    while (i4 < size2) {
                        com.android.server.job.JobPackageTracker.PackageEntry valueAt2 = valueAt.valueAt(i4);
                        android.os.UserHandle.formatUid(indentingPrintWriter, keyAt);
                        indentingPrintWriter.print(" / ");
                        indentingPrintWriter.print(valueAt.keyAt(i4));
                        indentingPrintWriter.println(":");
                        indentingPrintWriter.increaseIndent();
                        int i5 = size2;
                        int i6 = i4;
                        int i7 = keyAt;
                        android.util.ArrayMap<java.lang.String, com.android.server.job.JobPackageTracker.PackageEntry> arrayMap = valueAt;
                        int i8 = i3;
                        int i9 = size;
                        if (printDuration(indentingPrintWriter, totalTime, valueAt2.getPendingTime(j), valueAt2.pendingCount, "pending")) {
                            indentingPrintWriter.print(" ");
                        }
                        if (printDuration(indentingPrintWriter, totalTime, valueAt2.getActiveTime(j), valueAt2.activeCount, com.android.server.pm.verify.domain.DomainVerificationPersistence.TAG_ACTIVE)) {
                            indentingPrintWriter.print(" ");
                        }
                        printDuration(indentingPrintWriter, totalTime, valueAt2.getActiveTopTime(j), valueAt2.activeTopCount, "active-top");
                        if (valueAt2.pendingNesting > 0 || valueAt2.hadPending) {
                            indentingPrintWriter.print(" (pending)");
                        }
                        if (valueAt2.activeNesting > 0 || valueAt2.hadActive) {
                            indentingPrintWriter.print(" (active)");
                        }
                        if (valueAt2.activeTopNesting > 0 || valueAt2.hadActiveTop) {
                            indentingPrintWriter.print(" (active-top)");
                        }
                        indentingPrintWriter.println();
                        if (valueAt2.stopReasons.size() > 0) {
                            for (int i10 = 0; i10 < valueAt2.stopReasons.size(); i10++) {
                                if (i10 > 0) {
                                    indentingPrintWriter.print(", ");
                                }
                                indentingPrintWriter.print(valueAt2.stopReasons.valueAt(i10));
                                indentingPrintWriter.print("x ");
                                indentingPrintWriter.print(android.app.job.JobParameters.getInternalReasonCodeDescription(valueAt2.stopReasons.keyAt(i10)));
                            }
                            indentingPrintWriter.println();
                        }
                        indentingPrintWriter.decreaseIndent();
                        i4 = i6 + 1;
                        i3 = i8;
                        size = i9;
                        size2 = i5;
                        keyAt = i7;
                        valueAt = arrayMap;
                    }
                }
                i3++;
                dataSet = this;
                i2 = i;
                size = size;
            }
            indentingPrintWriter.decreaseIndent();
        }

        private void printPackageEntryState(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, int i) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, j2);
            protoOutputStream.write(1120986464258L, i);
            protoOutputStream.end(start);
        }

        void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, long j3, int i) {
            long start = protoOutputStream.start(j);
            long totalTime = getTotalTime(j2);
            protoOutputStream.write(1112396529665L, this.mStartClockTime);
            protoOutputStream.write(1112396529666L, j3 - this.mStartElapsedTime);
            protoOutputStream.write(1112396529667L, totalTime);
            int size = this.mEntries.size();
            int i2 = 0;
            while (i2 < size) {
                int keyAt = this.mEntries.keyAt(i2);
                if (i == -1 || i == android.os.UserHandle.getAppId(keyAt)) {
                    android.util.ArrayMap<java.lang.String, com.android.server.job.JobPackageTracker.PackageEntry> valueAt = this.mEntries.valueAt(i2);
                    int size2 = valueAt.size();
                    int i3 = 0;
                    while (i3 < size2) {
                        long start2 = protoOutputStream.start(2246267895812L);
                        com.android.server.job.JobPackageTracker.PackageEntry valueAt2 = valueAt.valueAt(i3);
                        protoOutputStream.write(1120986464257L, keyAt);
                        int i4 = size2;
                        protoOutputStream.write(1138166333442L, valueAt.keyAt(i3));
                        int i5 = i3;
                        int i6 = keyAt;
                        android.util.ArrayMap<java.lang.String, com.android.server.job.JobPackageTracker.PackageEntry> arrayMap = valueAt;
                        int i7 = i2;
                        printPackageEntryState(protoOutputStream, 1146756268035L, valueAt2.getPendingTime(j2), valueAt2.pendingCount);
                        printPackageEntryState(protoOutputStream, 1146756268036L, valueAt2.getActiveTime(j2), valueAt2.activeCount);
                        printPackageEntryState(protoOutputStream, 1146756268037L, valueAt2.getActiveTopTime(j2), valueAt2.activeTopCount);
                        boolean z = true;
                        protoOutputStream.write(1133871366150L, valueAt2.pendingNesting > 0 || valueAt2.hadPending);
                        protoOutputStream.write(1133871366151L, valueAt2.activeNesting > 0 || valueAt2.hadActive);
                        if (valueAt2.activeTopNesting <= 0 && !valueAt2.hadActiveTop) {
                            z = false;
                        }
                        protoOutputStream.write(1133871366152L, z);
                        for (int i8 = 0; i8 < valueAt2.stopReasons.size(); i8++) {
                            long start3 = protoOutputStream.start(2246267895817L);
                            protoOutputStream.write(1159641169921L, valueAt2.stopReasons.keyAt(i8));
                            protoOutputStream.write(1120986464258L, valueAt2.stopReasons.valueAt(i8));
                            protoOutputStream.end(start3);
                        }
                        protoOutputStream.end(start2);
                        i3 = i5 + 1;
                        i2 = i7;
                        keyAt = i6;
                        valueAt = arrayMap;
                        size2 = i4;
                    }
                }
                i2++;
            }
            protoOutputStream.write(1120986464261L, this.mMaxTotalActive);
            protoOutputStream.write(1120986464262L, this.mMaxFgActive);
            protoOutputStream.end(start);
        }
    }

    void rebatchIfNeeded(long j) {
        long totalTime = this.mCurDataSet.getTotalTime(j);
        if (totalTime > 1800000) {
            com.android.server.job.JobPackageTracker.DataSet dataSet = this.mCurDataSet;
            dataSet.mSummedTime = totalTime;
            this.mCurDataSet = new com.android.server.job.JobPackageTracker.DataSet();
            dataSet.finish(this.mCurDataSet, j);
            java.lang.System.arraycopy(this.mLastDataSets, 0, this.mLastDataSets, 1, this.mLastDataSets.length - 1);
            this.mLastDataSets[0] = dataSet;
        }
    }

    public void notePending(com.android.server.job.controllers.JobStatus jobStatus) {
        long millis = com.android.server.job.JobSchedulerService.sUptimeMillisClock.millis();
        jobStatus.madePending = millis;
        rebatchIfNeeded(millis);
        this.mCurDataSet.incPending(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), millis);
    }

    public void noteNonpending(com.android.server.job.controllers.JobStatus jobStatus) {
        long millis = com.android.server.job.JobSchedulerService.sUptimeMillisClock.millis();
        this.mCurDataSet.decPending(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), millis);
        rebatchIfNeeded(millis);
    }

    public void noteActive(com.android.server.job.controllers.JobStatus jobStatus) {
        long millis = com.android.server.job.JobSchedulerService.sUptimeMillisClock.millis();
        jobStatus.madeActive = millis;
        rebatchIfNeeded(millis);
        if (jobStatus.lastEvaluatedBias >= 40) {
            this.mCurDataSet.incActiveTop(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), millis);
        } else {
            this.mCurDataSet.incActive(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), millis);
        }
        addEvent(jobStatus.getJob().isPeriodic() ? 3 : 1, jobStatus.getSourceUid(), jobStatus.getBatteryName(), jobStatus.getJobId(), 0, null);
    }

    public void noteInactive(com.android.server.job.controllers.JobStatus jobStatus, int i, java.lang.String str) {
        long millis = com.android.server.job.JobSchedulerService.sUptimeMillisClock.millis();
        if (jobStatus.lastEvaluatedBias >= 40) {
            this.mCurDataSet.decActiveTop(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), millis, i);
        } else {
            this.mCurDataSet.decActive(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), millis, i);
        }
        rebatchIfNeeded(millis);
        addEvent(jobStatus.getJob().isPeriodic() ? 4 : 2, jobStatus.getSourceUid(), jobStatus.getBatteryName(), jobStatus.getJobId(), i, str);
    }

    public void noteConcurrency(int i, int i2) {
        if (i > this.mCurDataSet.mMaxTotalActive) {
            this.mCurDataSet.mMaxTotalActive = i;
        }
        if (i2 > this.mCurDataSet.mMaxFgActive) {
            this.mCurDataSet.mMaxFgActive = i2;
        }
    }

    public float getLoadFactor(com.android.server.job.controllers.JobStatus jobStatus) {
        int sourceUid = jobStatus.getSourceUid();
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        com.android.server.job.JobPackageTracker.PackageEntry entry = this.mCurDataSet.getEntry(sourceUid, sourcePackageName);
        com.android.server.job.JobPackageTracker.PackageEntry entry2 = this.mLastDataSets[0] != null ? this.mLastDataSets[0].getEntry(sourceUid, sourcePackageName) : null;
        if (entry == null && entry2 == null) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        long millis = com.android.server.job.JobSchedulerService.sUptimeMillisClock.millis();
        long activeTime = entry != null ? 0 + entry.getActiveTime(millis) + entry.getPendingTime(millis) : 0L;
        long totalTime = this.mCurDataSet.getTotalTime(millis);
        if (entry2 != null) {
            activeTime += entry2.getActiveTime(millis) + entry2.getPendingTime(millis);
            totalTime += this.mLastDataSets[0].getTotalTime(millis);
        }
        return activeTime / totalTime;
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter, int i) {
        com.android.server.job.JobPackageTracker.DataSet dataSet;
        long millis = com.android.server.job.JobSchedulerService.sUptimeMillisClock.millis();
        long millis2 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (this.mLastDataSets[0] != null) {
            dataSet = new com.android.server.job.JobPackageTracker.DataSet(this.mLastDataSets[0]);
            this.mLastDataSets[0].addTo(dataSet, millis);
        } else {
            dataSet = new com.android.server.job.JobPackageTracker.DataSet(this.mCurDataSet);
        }
        this.mCurDataSet.addTo(dataSet, millis);
        for (int i2 = 1; i2 < this.mLastDataSets.length; i2++) {
            if (this.mLastDataSets[i2] != null) {
                this.mLastDataSets[i2].dump(indentingPrintWriter, "Historical stats", millis, millis2, i);
                indentingPrintWriter.println();
            }
        }
        dataSet.dump(indentingPrintWriter, "Current stats", millis, millis2, i);
    }

    public void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        com.android.server.job.JobPackageTracker.DataSet dataSet;
        int i2;
        long start = protoOutputStream.start(j);
        long millis = com.android.server.job.JobSchedulerService.sUptimeMillisClock.millis();
        long millis2 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (this.mLastDataSets[0] != null) {
            dataSet = new com.android.server.job.JobPackageTracker.DataSet(this.mLastDataSets[0]);
            this.mLastDataSets[0].addTo(dataSet, millis);
        } else {
            dataSet = new com.android.server.job.JobPackageTracker.DataSet(this.mCurDataSet);
        }
        this.mCurDataSet.addTo(dataSet, millis);
        int i3 = 1;
        while (i3 < this.mLastDataSets.length) {
            if (this.mLastDataSets[i3] == null) {
                i2 = i3;
            } else {
                i2 = i3;
                this.mLastDataSets[i3].dump(protoOutputStream, 2246267895809L, millis, millis2, i);
            }
            i3 = i2 + 1;
        }
        dataSet.dump(protoOutputStream, 1146756268034L, millis, millis2, i);
        protoOutputStream.end(start);
    }

    boolean dumpHistory(android.util.IndentingPrintWriter indentingPrintWriter, int i) {
        int i2;
        java.lang.String str;
        int size = this.mEventIndices.size();
        if (size <= 0) {
            return false;
        }
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Job history:");
        indentingPrintWriter.decreaseIndent();
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        for (int i3 = 0; i3 < size; i3++) {
            int indexOf = this.mEventIndices.indexOf(i3);
            int i4 = this.mEventUids[indexOf];
            if ((i == -1 || i == android.os.UserHandle.getAppId(i4)) && (i2 = this.mEventCmds[indexOf] & 255) != 0) {
                switch (i2) {
                    case 1:
                        str = "  START";
                        break;
                    case 2:
                        str = "   STOP";
                        break;
                    case 3:
                        str = "START-P";
                        break;
                    case 4:
                        str = " STOP-P";
                        break;
                    default:
                        str = "     ??";
                        break;
                }
                android.util.TimeUtils.formatDuration(this.mEventTimes[indexOf] - millis, indentingPrintWriter, 19);
                indentingPrintWriter.print(" ");
                indentingPrintWriter.print(str);
                indentingPrintWriter.print(": #");
                android.os.UserHandle.formatUid(indentingPrintWriter, i4);
                indentingPrintWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                indentingPrintWriter.print(this.mEventJobIds[indexOf]);
                indentingPrintWriter.print(" ");
                indentingPrintWriter.print(this.mEventTags[indexOf]);
                if (i2 == 2 || i2 == 4) {
                    indentingPrintWriter.print(" ");
                    if (this.mEventReasons[indexOf] != null) {
                        indentingPrintWriter.print(this.mEventReasons[indexOf]);
                    } else {
                        indentingPrintWriter.print(android.app.job.JobParameters.getInternalReasonCodeDescription((this.mEventCmds[indexOf] & EVENT_STOP_REASON_MASK) >> 8));
                    }
                }
                indentingPrintWriter.println();
            }
        }
        return true;
    }

    public void dumpHistory(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        int i2;
        int i3 = i;
        int size = this.mEventIndices.size();
        if (size == 0) {
            return;
        }
        long start = protoOutputStream.start(j);
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        int i4 = 0;
        while (i4 < size) {
            int indexOf = this.mEventIndices.indexOf(i4);
            int i5 = this.mEventUids[indexOf];
            if (i3 != -1 && i3 != android.os.UserHandle.getAppId(i5)) {
                i2 = size;
            } else {
                int i6 = this.mEventCmds[indexOf] & 255;
                if (i6 == 0) {
                    i2 = size;
                } else {
                    long start2 = protoOutputStream.start(2246267895809L);
                    protoOutputStream.write(1159641169921L, i6);
                    i2 = size;
                    protoOutputStream.write(1112396529666L, millis - this.mEventTimes[indexOf]);
                    protoOutputStream.write(1120986464259L, i5);
                    protoOutputStream.write(1120986464260L, this.mEventJobIds[indexOf]);
                    protoOutputStream.write(1138166333445L, this.mEventTags[indexOf]);
                    if (i6 == 2 || i6 == 4) {
                        protoOutputStream.write(1159641169926L, (this.mEventCmds[indexOf] & EVENT_STOP_REASON_MASK) >> 8);
                    }
                    protoOutputStream.end(start2);
                }
            }
            i4++;
            i3 = i;
            size = i2;
        }
        protoOutputStream.end(start);
    }
}
